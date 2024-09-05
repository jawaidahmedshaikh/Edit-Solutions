/*
 * User: sprasad
 * Date: Apr 10, 2006
 * Time: 9:43:12 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.batch;

import client.ClientAddress;
import client.ClientDetail;
import client.TaxInformation;
import contract.Deposits;
import contract.Segment;
import edit.common.*;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.TaxExtract5498DetailVO;
import edit.services.db.CRUD;
import edit.services.db.hibernate.*;
import edit.services.logging.Logging;
import edit.services.*;
import engine.ProductStructure;
import event.dm.dao.FastDAO;
import fission.utility.*;
import fission.utility.XMLUtil;

import logging.*;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import batch.business.*;

public class TaxExtract5498Processor extends TaxExtractProcessor
{
    private static final String TAX_FORM_5498 = "5498";

    // Number of months in 70.5 years
    private static final int MONTHS_IN_SEVENTY_AND_HALF_YEARS = 846;

    // Qualified Types for 5498 Tax Extraction.
    private static final String[] QUALIFIED_TYPES = new String[] { Segment.QUALIFIEDTYPECT_IRA,
                                                                   Segment.QUALIFIEDTYPECT_SEP,
                                                                   Segment.QUALIFIEDTYPECT_SIMPLE,
                                                                   Segment.QUALIFIEDTYPECT_ROTH };

    // Contribution type deposits.
    private static final String[] DEPOSIT_TYPES_CONTRIBUTIONS = new String[] { Deposits.DEPOSIT_TYPE_CASH,
                                                                               Deposits.DEPOSIT_TYPE_CASH_WITH_APP,
                                                                               Deposits.DEPOSIT_TYPE_CONTRIBUTION };

    // Rollover type deposits.
    private static final String[] DEPOSIT_TYPES_ROLLOVERS = new String[] { Deposits.DEPOSIT_TYPE_QUALIFIED_ROLLOVER,
                                                                           Deposits.DEPOSIT_TYPE_REPLACE_QUAL_ROLLOVER };

    private static final String DEFAULT_INFO_NOTAVAILABLE = "NotAvailable";

    private static final String SEVENTY_AND_HALF_IND_YES     = "Y";
    private static final String SEVENTY_AND_HALF_IND_NO      = "N";
    private static final String SEVENTY_AND_HALF_IND_UNKNOWN = "U";


    public TaxExtract5498Processor(ProductStructureVO[] productStructureVOs)
    {
        super();
        this.setupFieldNamesForRounding();
        this.setProductStructures(productStructureVOs);
    }

    /**
     * Processes and calculates some field values for the qualifying records that satisfy the input parameters.
     * And generates XML file or TSV file depending on the input.
     * @param startDate
     * @param endDate
     * @param taxYear
     * @param fileType
     * @throws Exception
     */
    public void extract(String startDate, String endDate, String taxYear, String fileType) throws Exception
    {
        FastDAO fastDAO = new FastDAO();

        List segmentPKs = new ArrayList();

        for (int i = 0; i < productStructureVOs.length; i++)
        {
            segmentPKs.addAll(fastDAO.getTE5498UniqueSegmentPKsByProductStructureAndSegmentNameAndQualifiedTypes(productStructureVOs[i].getProductStructurePK(),
                                                                                                                 Segment.OPTIONCODECT_DEFERRED_ANNUITY,
                                                                                                                 taxYear,
                                                                                                                 QUALIFIED_TYPES));
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_TAX_RESERVES_EXTRACTS).updateSuccess();
        }

        File exportFile = getExportFile("5498", fileType);

        if (fileType.equalsIgnoreCase("X"))
        {
            insertStartTaxExtracts(exportFile, "<TaxExtractsVO>");
        }

        for (int i = 0; i < segmentPKs.size(); i++)
        {
            Long segmentPK = (Long) segmentPKs.get(i);

            processSegment(startDate, endDate, segmentPK, taxYear, exportFile, fileType);

            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_TAX_RESERVES_EXTRACTS).updateSuccess();
        }

        if (fileType.equalsIgnoreCase("X"))
        {
            insertEndTaxExtracts(exportFile, "</TaxExtractsVO>");
        }
    }

    /**
     * Processes each segment.
     * @param startDate
     * @param endDate
     * @param segmentPK
     * @param taxYear
     * @param fileType
     * @throws Exception
     */
    private void processSegment(String startDate, String endDate, Long segmentPK, String taxYear, File exportFile, String fileType) throws Exception
    {
        FastDAO fastDAO = new FastDAO();

        Segment segment = Segment.findByPK(segmentPK);

        try
        {
            TaxExtract5498DetailVO taxExtract5498DetailVO = new TaxExtract5498DetailVO();

            taxExtract5498DetailVO.setTaxExtractPK(CRUD.getNextAvailableKey());
            taxExtract5498DetailVO.setQualNonQualIndicator(segment.getQualNonQualCT());
            taxExtract5498DetailVO.setQualifiedType(segment.getQualifiedTypeCT());

            ProductStructure productStructure = ProductStructure.findByPK(segment.getProductStructureFK());

            taxExtract5498DetailVO.setMarketingPackageName(productStructure.getMarketingPackageName());
            taxExtract5498DetailVO.setBusinessContractName(productStructure.getBusinessContractName());

            populateClientInformation(segment, taxExtract5498DetailVO);

            taxExtract5498DetailVO.setTaxForm(TaxExtract5498Processor.TAX_FORM_5498);
            taxExtract5498DetailVO.setTaxYear(taxYear);
            taxExtract5498DetailVO.setContractNumber(segment.getContractNumber());

            EDITBigDecimal accumValue = fastDAO.getAccumulatedValueForCalendarYearEndTransactionBy_SegmentPK_TaxYear(segmentPK.longValue(), taxYear);

            taxExtract5498DetailVO.setAccumValue(accumValue.toString());

            EDITBigDecimal accumulatedContributions = fastDAO.getTotalAmountReceivedForPremiumTransactionsBy_SegmentPK_TaxYear_DepositTypes_DateRange(segmentPK.longValue(),
                                                                                                                                                   taxYear,
                                                                                                                                                   DEPOSIT_TYPES_CONTRIBUTIONS,
                                                                                                                                                   startDate,
                                                                                                                                                   endDate);

            taxExtract5498DetailVO.setAccumulatedContribution(accumulatedContributions.toString());

            EDITBigDecimal accumulatedRollover = fastDAO.getTotalAmountReceivedForPremiumTransactionsBy_SegmentPK_TaxYear_DepositTypes_DateRange(segmentPK.longValue(),
                                                                                                                                                   taxYear,
                                                                                                                                                   DEPOSIT_TYPES_ROLLOVERS,
                                                                                                                                                   startDate,
                                                                                                                                                   endDate);


            taxExtract5498DetailVO.setAccumulatedRollover(accumulatedRollover.toString());

            EDITBigDecimal accumulatedROTHConversion = fastDAO.getROTHConversionForComplexChangeTransactionBy_SegmentPK_TaxYear(segmentPK.longValue(),
                                                                                                                               taxYear);

            taxExtract5498DetailVO.setAccumulatedROTHConversion(accumulatedROTHConversion.toString());

            taxExtract5498DetailVO.setSeventyAndHalfInd(getSeventyAndHalfIndAndBirthDate(segment, new EDITDate(endDate).getYear(), taxExtract5498DetailVO));

            exportTaxExtract(taxExtract5498DetailVO, exportFile, fileType);

            SessionHelper.closeSessions();
            taxExtract5498DetailVO = null;
            segment = null;
        }
        catch (Throwable e)
        {
            System.out.println(e);
            e.printStackTrace();

            LogEvent logEvent = new LogEvent("Error in 5498 Tax Extract. Contract Number = " + segment.getContractNumber(), e);
            Logger logger = Logging.getLogger(Logging.BATCH_JOB);
            logger.error(logEvent);

            logErrorToDatabase(e, segment.getContractNumber());
        }
    }

    /**
     * Determines the sevenyAndHalf Indicator based on the following rules.
     * If Contract is SpousalContinuation use previous owner's Date of Birth for calculations
     * other wise use current owner's Date of Birth.
     * @param segment
     * @param year
     * @return
     */
    private String getSeventyAndHalfIndAndBirthDate(Segment segment, int year, TaxExtract5498DetailVO taxExtract5498DetailVO)
    {
        String seventyAndHalfInd = SEVENTY_AND_HALF_IND_UNKNOWN;

        EDITDate dateToBeUsedInCalulation = null;

        if (segment.isSpousalContinuation())
        {
            ClientDetail immediatePreviousOwner = segment.getImmediatePreviousOwner();

            if (immediatePreviousOwner != null)
            {
                EDITDate previousOwnerBirthDate = immediatePreviousOwner.getBirthDate();

                if (previousOwnerBirthDate != null)
                {
                    dateToBeUsedInCalulation = previousOwnerBirthDate;

                    taxExtract5498DetailVO.setOwnerDateOfBirth(DateTimeUtil.formatEDITDateAsMMDDYYYY(previousOwnerBirthDate));
                }
            }
        }
        else
        {
            ClientDetail currentOwner = segment.getCurrentOwner();

            if (currentOwner != null)
            {
                EDITDate currentOwnerBirthDate = currentOwner.getBirthDate();

                if (currentOwnerBirthDate != null)
                {
                    dateToBeUsedInCalulation = currentOwnerBirthDate;

                    taxExtract5498DetailVO.setOwnerDateOfBirth(DateTimeUtil.formatEDITDateAsMMDDYYYY(currentOwnerBirthDate));
                }
            }
        }

        if (dateToBeUsedInCalulation != null)
        {
            dateToBeUsedInCalulation = dateToBeUsedInCalulation.addMonths(TaxExtract5498Processor.MONTHS_IN_SEVENTY_AND_HALF_YEARS);

            if (dateToBeUsedInCalulation.getYear() <= year)
            {
                seventyAndHalfInd = SEVENTY_AND_HALF_IND_YES;
            }
            else
            {
                seventyAndHalfInd = SEVENTY_AND_HALF_IND_NO;
            }
        }


        return seventyAndHalfInd;
    }

    /**
     * Populates Client and ClientAddress information.
     * @param segment
     * @param taxExtract5498DetailVO
     */
    private void populateClientInformation(Segment segment, TaxExtract5498DetailVO taxExtract5498DetailVO)
    {
        ClientDetail owner = null;

        try
        {
            owner = segment.getCurrentOwner();
//            EDITDate birthDate = owner.getBirthDate();
//            taxExtract5498DetailVO.setOwnerDateOfBirth(birthDate == null ? null : birthDate.getDateAsMMDDYYYY());
            taxExtract5498DetailVO.setOwnerLastName(owner.getLastName());
            taxExtract5498DetailVO.setOwnerFirstName(owner.getFirstName());
            taxExtract5498DetailVO.setOwnerCorporateName(owner.getCorporateName());
            Set taxInformations = owner.getTaxInformations();
            if (taxInformations != null && !taxInformations.isEmpty())
            {
                // Always there would be only one TaxInformation.
                TaxInformation taxInformation = (TaxInformation) taxInformations.iterator().next();
                taxExtract5498DetailVO.setTaxIdType(taxInformation.getTaxIdTypeCT());
            }
            taxExtract5498DetailVO.setTaxID(owner.getTaxIdentification());
        }
        catch (Throwable e)
        {
            // when exception happens most probably when contract does not have owner contract client
            // or owner contract client's effective date is null, stub out information and log the event.
            taxExtract5498DetailVO.setOwnerDateOfBirth(DEFAULT_INFO_NOTAVAILABLE);
            taxExtract5498DetailVO.setOwnerLastName(DEFAULT_INFO_NOTAVAILABLE);
            taxExtract5498DetailVO.setOwnerFirstName(DEFAULT_INFO_NOTAVAILABLE);
            taxExtract5498DetailVO.setOwnerCorporateName(DEFAULT_INFO_NOTAVAILABLE);
            taxExtract5498DetailVO.setTaxIdType(DEFAULT_INFO_NOTAVAILABLE);
            taxExtract5498DetailVO.setTaxIdType(DEFAULT_INFO_NOTAVAILABLE);

            System.out.println(e);
            e.printStackTrace();

            LogEvent logEvent = new LogEvent("Error in 5498 Tax Extract. Owner Not Available. Contract Number = " + segment.getContractNumber(), e);
            Logger logger = Logging.getLogger(Logging.BATCH_JOB);
            logger.error(logEvent);
        }

        ClientAddress ownerPrimaryAddress = null;

        try
        {
            ownerPrimaryAddress = owner.getPrimaryAddress();
            taxExtract5498DetailVO.setAddressLine1(ownerPrimaryAddress.getAddressLine1());
            taxExtract5498DetailVO.setAddressLine2(ownerPrimaryAddress.getAddressLine2());
            taxExtract5498DetailVO.setAddressLine3(ownerPrimaryAddress.getAddressLine3());
            taxExtract5498DetailVO.setAddressLine4(ownerPrimaryAddress.getAddressLine4());
            taxExtract5498DetailVO.setCity(ownerPrimaryAddress.getCity());
            taxExtract5498DetailVO.setState(ownerPrimaryAddress.getStateCT());
            taxExtract5498DetailVO.setZip(ownerPrimaryAddress.getZipCode());
        }
        catch (Throwable e)
        {
            // when the exception happens most probably when owner does not have primary address,
            // stub out the information and log the event.
            taxExtract5498DetailVO.setAddressLine1(DEFAULT_INFO_NOTAVAILABLE);
            taxExtract5498DetailVO.setAddressLine2(DEFAULT_INFO_NOTAVAILABLE);
            taxExtract5498DetailVO.setAddressLine3(DEFAULT_INFO_NOTAVAILABLE);
            taxExtract5498DetailVO.setAddressLine4(DEFAULT_INFO_NOTAVAILABLE);
            taxExtract5498DetailVO.setCity(DEFAULT_INFO_NOTAVAILABLE);
            taxExtract5498DetailVO.setState(DEFAULT_INFO_NOTAVAILABLE);
            taxExtract5498DetailVO.setZip(DEFAULT_INFO_NOTAVAILABLE);

            System.out.println(e);
            e.printStackTrace();

            LogEvent logEvent = new LogEvent("Error in 5498 Tax Extract. Owner Primary Address Not Available. Contract Number = " + segment.getContractNumber(), e);
            Logger logger = Logging.getLogger(Logging.BATCH_JOB);
            logger.error(logEvent);
        }

        ownerPrimaryAddress = null;
        owner = null;
    }

    /**
     * Write extract record to the export file
     *
     * @param taxExtract5498DetailVO
     * @param exportFile
     * @throws Exception
     */
    private void exportTaxExtract(TaxExtract5498DetailVO taxExtract5498DetailVO, File exportFile, String fileType) throws Exception
    {
        if (fileType.equalsIgnoreCase("X"))
        {
            String parsedXML = roundDollarFields(taxExtract5498DetailVO);
            
            parsedXML = XMLUtil.parseOutXMLDeclaration(parsedXML);
          
            appendToFile(exportFile, parsedXML);
        }
        else
        {
            outputTSV(exportFile, taxExtract5498DetailVO);
        }
    }

    private void outputTSV(File exportFile, TaxExtract5498DetailVO taxExtract5498DetailVO) throws Exception
    {
        StringBuffer fileData = new StringBuffer();
        fileData.append(taxExtract5498DetailVO.getTaxExtractPK());
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getQualNonQualIndicator(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getQualifiedType(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getMarketingPackageName(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getBusinessContractName(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getOwnerDateOfBirth(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getOwnerLastName(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getOwnerFirstName(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getOwnerCorporateName(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getAddressLine1(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getAddressLine2(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getAddressLine3(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getAddressLine4(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getCity(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getState(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getZip(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getTaxForm(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getTaxYear(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getTaxIdType(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getTaxID(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getContractNumber(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getAccumValue(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getAccumulatedContribution(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getAccumulatedRollover(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getAccumulatedROTHConversion(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtract5498DetailVO.getSeventyAndHalfInd(), ""));
        fileData.append("\t");
        fileData.append("\n");

        appendToFile(exportFile, fileData.toString());
    }

    /**
     * List field names for rounding
     *
     */
    private void setupFieldNamesForRounding()
    {
        List fieldNames = new ArrayList();

        this.fieldNames = (String[]) fieldNames.toArray(new String[fieldNames.size()]);
    }

    private void logErrorToDatabase(Throwable e, String contractNumber)
    {
        EDITMap columnInfo = new EDITMap ("ProcessDate", new EDITDate().getFormattedDate());
        columnInfo.put("ContractNumber", contractNumber);
        columnInfo.put("AgentNumber", "N/A");
        columnInfo.put("TaxReportType", TAX_FORM_5498);

        Log.logToDatabase(Log.YEAR_END_TAX_REPORTING, "Error in 5498 Tax Extract: " + e.getMessage(), columnInfo);
    }
}
