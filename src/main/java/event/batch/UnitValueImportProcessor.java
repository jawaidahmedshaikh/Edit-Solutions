/*
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */
package event.batch;

import batch.business.*;
import edit.common.*;
import edit.common.exceptions.*;
import edit.common.vo.*;
import edit.services.config.*;
import edit.services.logging.*;
import edit.services.*;
import engine.*;
import engine.util.*;
import logging.*;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.math.*;
import java.util.*;

import fission.utility.*;
import security.Operator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import logging.LogEvent;
import org.apache.logging.log4j.Logger;

/**
 * <p>
 *  The fund number that is on the input file can be one of two
 * things now (March 2005): the actual filtered fund number, or a client fund
 * number.  The customer did not want to change layout of this file or do
 * transactions.
 * <p>
 * The Client Fund Number represents an alias to a charge code that a customer
 * can use because of historical reasons.  It is stored optionally on the
 * ChargeCode table and hence corresponds to a combination of filtered fund
 * number and charge code number.
 * <p>
 * The <CODE>routine getCorrectFilteredFundVOAndSetMatchingChargeCodeVOs()</CODE>
 * will try to find a charge code row that has its client fund number match it.
 * If it does find it, it sets the currentChargeCodeVO as well as returning
 * the filtered fund VO. The ChargeCodeVO is needed for the chargeCodeFK for
 * any unit values being created.  That is, if the input file specifieds a
 * ClientFundNumber then any unit values that are created will have that Client
 * Fund Number's filtered fund FK and charge code FK filled in.  Otherwise they
 * are created only with the filtered fund FK filled in.
 * <p>
 * This is a little more involved than necessary because of mixing filtered
 * fund numbers with numbers that refer to charge codes.
 */
public class UnitValueImportProcessor implements Serializable
{

    /**
     * used to lookup the filtered fund and charge code by client fund number
     */
    private TransformChargeCodes transformChargeCodes = new TransformChargeCodes();

    /**
     * keep one copy - used to do some lookups
     */
    private engine.business.Lookup engineLookup = new engine.component.LookupComponent();

    /**
     * Every time we do a lookup for filteredFund, we reset this.
     * This is the chargecodeVO associated with the Client fund number or null.
     */
    /**
     * The business specification is changed (10/11/2005) and multiple chargeCodes might
     * exist with same ClientFundNumber and for every matching chargedCode entry that is found
     * for input file a UnitValue record has to be created.
     */
    private ChargeCodeVO[] matchingChargeCodeVOs = null;

    public static final String RECORD_TYPE_NAV2 = "NAV2";
    public static final String RECORD_TYPE_UVAL = "UVAL";

    /**
     * Updated throughout the batch process for reporting purposes.
     */
    public UnitValueImportProcessor()
    {
    }

    /**
     * Imports new unit values from the flat file specified in the EDITServicesConfig.xml file for the given mmddyy param
     */
    public void importUnitValues(String importDate)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_IMPORT_UNIT_VALUES).tagBatchStart(Batch.BATCH_JOB_IMPORT_UNIT_VALUES, "Unit Value");

        // Split this up into separate methods to make it easier to test
        // see commented testing code at bottom

        try
        {
            String[] uvImportLines = readFile(importDate);

            if (uvImportLines == null)
            {
                return;
            }

            // The first line has control information on it
            String currentPriceDate = uvImportLines[0].substring(9, 17);
            String currentPriceMonth = currentPriceDate.substring(0, 2);
            String currentPriceDay = currentPriceDate.substring(2, 4);
            String currentPriceYear = currentPriceDate.substring(4);

            String uvEffDate = DateTimeUtil.initDate(currentPriceMonth, currentPriceDay, currentPriceYear, null);

            UnitValueUpdateVO[] unitValueUpdateVOs =
                    transformRawInputIntoUnitValueUpdateVOs(uvImportLines, uvEffDate);

            if (unitValueUpdateVOs == null)
            {
                return;
            }

            processUnitValueUpdateVOs(unitValueUpdateVOs, uvEffDate);
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_IMPORT_UNIT_VALUES).updateFailure();

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.out.println(e);
            LogEvent logEvent = new LogEvent("Unit Value Import Errored", e);
            Logger logger = Logging.getLogger(Logging.BATCH_JOB);
            logger.error(logEvent);
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_IMPORT_UNIT_VALUES).tagBatchStop();
        }
    }

    private String[] readFile(String importDate)
    {
        EDITDate editImportDate = new EDITDate(importDate);

        String uvImportMonth = editImportDate.getFormattedMonth();

        String uvImportDay = editImportDate.getFormattedDay();

        String uvImportYear = editImportDate.getFormattedYear();

        UnitValueImport uvImport = ServicesConfig.getUnitValueImport();
        String uvImportFileDirectory = uvImport.getDirectory();
        String uvFileName = uvImportFileDirectory + "VL" + uvImportYear.substring(2, 4) + uvImportMonth + uvImportDay + ".dat";
        File importFile = new File(uvFileName);

        String[] uvImportLines = getUnitValueImportLines(importFile);
        return uvImportLines;
    }

    private UnitValueUpdateVO[] transformRawInputIntoUnitValueUpdateVOs(String[] uvImportLines,
                                                                        String uvEffDate) throws EDITEngineException
    {
        UnitValueUpdateVO[] unitValueUpdateVOs = null;

        if (uvImportLines != null)
        {
            //Subtract 2 from uvImportLines.length - we will not include the HEADER or TRAILER records
            unitValueUpdateVOs = new UnitValueUpdateVO[uvImportLines.length - 2];
        }

        for (int i = 1; i < (uvImportLines.length - 1); i++)
        {
            UnitValueUpdateVO uvUpdateVO = new UnitValueUpdateVO();
            uvUpdateVO.setFundIndicator(uvImportLines[i].substring(0, 4));
            uvUpdateVO.setRecordType(uvImportLines[i].substring(4, 8));

            if (uvImportLines[i].substring(4, 8).equalsIgnoreCase("RATE"))
            {
                String dividendRate = removeLeadingSpaces(uvImportLines[i].substring(9, 22));
                String stCapGainRate = removeLeadingSpaces(uvImportLines[i].substring(24, 37));
                String ltCapGainRate = removeLeadingSpaces(uvImportLines[i].substring(39, 52));

                RateRecord rateRecord = new RateRecord();
                rateRecord.setDividendRate(new BigDecimal(dividendRate));
                rateRecord.setSTCapGainRate(new BigDecimal(stCapGainRate));
                rateRecord.setLTCapGainRate(new BigDecimal(ltCapGainRate));
                uvUpdateVO.addRateRecord(rateRecord);
            }
            else
            {
                String priceString = removeLeadingSpaces(uvImportLines[i].substring(8, 19));
                String netInvestFactors = removeLeadingSpaces(uvImportLines[i].substring(20, 36));
                String units = removeLeadingSpaces(uvImportLines[i].substring(37, 53));
                String netAssets = removeLeadingSpaces(uvImportLines[i].substring(54, 69));
                String fees = removeLeadingSpaces(uvImportLines[i].substring(69, 80));
                String feesPayable = removeLeadingSpaces(uvImportLines[i].substring(80));

                DetailRecord detailRecord = new DetailRecord();
                detailRecord.setPrice(new BigDecimal(priceString));
                detailRecord.setNetInvestFactors(new BigDecimal(netInvestFactors));
                detailRecord.setUnits(new BigDecimal(units));
                detailRecord.setNetAssets(new BigDecimal(netAssets));
                detailRecord.setFees(new BigDecimal(fees));
                detailRecord.setFeesPayable(new BigDecimal(feesPayable));
                uvUpdateVO.addDetailRecord(detailRecord);

                String recordType = uvImportLines[i].substring(4, 8);
                String fundNumberOrClientFundNumber = uvImportLines[i].substring(0, 4);

                if (RECORD_TYPE_NAV2.equalsIgnoreCase(recordType) || RECORD_TYPE_UVAL.equalsIgnoreCase(recordType))
                {
                    EDITBigDecimal trxAmount = new EDITBigDecimal(fees);

                    if (trxAmount.isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
                    {
                        // create Fee record
                        createDFACCTransaction(fundNumberOrClientFundNumber, recordType, uvEffDate, trxAmount);
                    }
                }
            }

            unitValueUpdateVOs[i - 1] = uvUpdateVO;
        }
        return unitValueUpdateVOs;
    }


    private void processUnitValueUpdateVOs(UnitValueUpdateVO[] unitValueUpdateVOs,
                                           String uvEffDate) throws Exception
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(FundVO.class);

        UnitValuesVO[] unitValuesVOs = null;

        String previousFundNumber = "";

        FilteredFundVO[] filteredFundVO = null;

        for (int i = 0; i < unitValueUpdateVOs.length; i++)
        {
            // the fund number on the file may be the actual one or the Client Fund Number
            String fundNumberOrClientFundNumber = unitValueUpdateVOs[i].getFundIndicator();

            if (! fundNumberOrClientFundNumber.equals(previousFundNumber) &&
                ! previousFundNumber.equals(""))
            {
               //save previous UV record to the database before continuing
                saveUnitValuesVOs(unitValuesVOs, filteredFundVO[0]);
                unitValuesVOs = null;
                // will create a new one to use
                // there can be multiple input rows for the same unit value
            }

            previousFundNumber = fundNumberOrClientFundNumber;

            // getRealFundNumberAndSetMatchingChargeCodeVOs() resets the matchingChargeCodeVOs but we will
            // do it explicitly here just to be very clear
            this.matchingChargeCodeVOs = null;

            String realFundNumber = getRealFundNumberAndSetMatchingChargeCodeVOs(fundNumberOrClientFundNumber);

            filteredFundVO = this.engineLookup.composeFilteredFundVOByFundNumber(realFundNumber, voInclusionList);

            if ((filteredFundVO != null) && (filteredFundVO.length > 0))
            {
                FundVO fundVO = (FundVO) filteredFundVO[0].getParentVO(FundVO.class);

                if (unitValuesVOs == null) // create a brand new unit values vo to massage
                {
                    unitValuesVOs = createNewUnitValuesVOs(filteredFundVO[0].getFilteredFundPK(),
                                                          fundVO.getFundType(),
                                                          uvEffDate);
                }

                if (unitValueUpdateVOs[i].getDetailRecordCount() > 0)
                {
                    populateUnitValuesVOFromDetailRecord(unitValuesVOs,
                            unitValueUpdateVOs[i].getRecordType(),
                            unitValueUpdateVOs[i].getDetailRecord(0));
                }
                else
                {
                    for (int j = 0; j < unitValuesVOs.length; j++)
                    {
                        unitValuesVOs[j].setDividendRate(unitValueUpdateVOs[i].getRateRecord(0).getDividendRate());
                    }
                }
            }

            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_IMPORT_UNIT_VALUES).updateSuccess();
        }

        //save last UV record to the database here.
        saveUnitValuesVOs(unitValuesVOs, filteredFundVO[0]);
    }

    private String removeLeadingSpaces(String stringToTrim)
    {
        boolean trimLeadingSpaces = false;

        if (stringToTrim.startsWith(" "))
        {
            trimLeadingSpaces = true;
        }

        while (trimLeadingSpaces)
        {
            stringToTrim = stringToTrim.substring(1);

            if (!stringToTrim.startsWith(" "))
            {
                trimLeadingSpaces = false;
            }
        }

        return stringToTrim;
    }

    private UnitValuesVO[] createNewUnitValuesVOs(long filteredFundPK,
                                                 String fundType,
                                                 String uvEffDate)
    {
        List unitValuesVOs = new ArrayList();
        UnitValuesVO unitValuesVO = null;

        // even if matchingChargeCodeVOs are not found create one unitValueVO with out chargeCodeFK
        if (this.matchingChargeCodeVOs == null)
        {
            unitValuesVO = new UnitValuesVO();

            unitValuesVO.setEffectiveDate(uvEffDate);
            unitValuesVO.setFilteredFundFK(filteredFundPK);

            if (fundType.equalsIgnoreCase("Hedge"))
            {
                unitValuesVO.setUpdateStatus("Hedge");
            }

            unitValuesVOs.add(unitValuesVO);
        }
        else if (this.matchingChargeCodeVOs.length > 0)
        {
            // create UnitValue for each matching ChargeCode found.
            for (int i = 0; i < this.matchingChargeCodeVOs.length; i++)
            {
                unitValuesVO = new UnitValuesVO();
                unitValuesVO.setEffectiveDate(uvEffDate);
                unitValuesVO.setFilteredFundFK(filteredFundPK);

                unitValuesVO.setChargeCodeFK(this.matchingChargeCodeVOs[i].getChargeCodePK());

                if (fundType.equalsIgnoreCase("Hedge"))
                {
                    unitValuesVO.setUpdateStatus("Hedge");
                }

                unitValuesVOs.add(unitValuesVO);
            }
        }

        return (UnitValuesVO[]) unitValuesVOs.toArray(new UnitValuesVO[unitValuesVOs.size()]);
    }

    private void populateUnitValuesVOFromDetailRecord(UnitValuesVO[] unitValuesVOs, String recordType, DetailRecord detailRecord)
    {
        UnitValuesVO unitValuesVO = null;

        for (int i = 0; i < unitValuesVOs.length; i++)
        {
            unitValuesVO = unitValuesVOs[i];

            if (recordType.equalsIgnoreCase("NAV1"))
            {
                unitValuesVO.setNetAssetValue1(detailRecord.getPrice());
                unitValuesVO.setNAV1Assets(detailRecord.getNetAssets());
            }
            else if (recordType.equalsIgnoreCase("NAV2"))
            {
                unitValuesVO.setNetAssetValue2(detailRecord.getPrice());
                unitValuesVO.setMutualFundAssets(detailRecord.getNetAssets());
                unitValuesVO.setNAV2Assets(detailRecord.getNetAssets());
            }
            else
            {
                unitValuesVO.setUnitValue(detailRecord.getPrice());
                unitValuesVO.setUVALAssets(detailRecord.getNetAssets());
            }
        }
    }

    private String[] getUnitValueImportLines(File importFile)
    {
        try
        {
            List importLines = new ArrayList();

            FileReader fr = new FileReader(importFile);

            BufferedReader reader = new BufferedReader(fr);

            String uvImportLine = null;

            while ((uvImportLine = reader.readLine()) != null)
            {
                importLines.add(uvImportLine);
            }

            reader.close();

            return (String[]) importLines.toArray(new String[importLines.size()]);
        }
        catch (Exception e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            System.out.println(e);

            return null;
        }
    }

    /**
     * Creates Fee record from the import file.
     *
     * @param recordType
     * @param uvEffDate
     * @param fees
     * @return
     * @throws EDITEngineException
     */
    private boolean createDFACCTransaction(String fundNumber, String recordType, String uvEffDate, EDITBigDecimal trxAmount) throws EDITEngineException
    {
        // to verify whether Fee record is inserted in to the system or not
        boolean isDFACCTransactionCreated = false;
        String feeTypeCT = "";

        if (RECORD_TYPE_NAV2.equalsIgnoreCase(recordType))
        {
            feeTypeCT = Fee.MANAGEMENT_FEE_TYPE;
        }
        else if (RECORD_TYPE_UVAL.equalsIgnoreCase(recordType))
        {
            feeTypeCT = Fee.ME_FEE_TYPE;
        }

        // the Fund Number may be the actual one or may be the Client Fund Number
        FilteredFundVO filteredFundVO =
                getCorrectFilteredFundVOAndSetMatchingChargeCodeVOs(fundNumber);

        // has to have this filtered fund already existing in the system
        // if it does not find one do nothing i.e do not add record.
        if (filteredFundVO != null)
        {
            long filteredFundPK = filteredFundVO.getFilteredFundPK();

            FeeDescriptionVO feeDescriptionVO =
                    this.engineLookup.findFeeDescriptionBy_FilteredFundPK_And_FeeTypeCT(filteredFundPK, feeTypeCT);

            FeeVO feeVO = new FeeVO();

            feeVO.setFilteredFundFK(filteredFundPK);

            if (feeDescriptionVO != null)
            {
                feeVO.setFeeDescriptionFK(feeDescriptionVO.getFeeDescriptionPK());
            }

            feeVO.setEffectiveDate(uvEffDate);
            feeVO.setProcessDateTime(new EDITDateTime().getFormattedDateTime());
            feeVO.setOriginalProcessDate(null);

            // all fees coming from the import files are automated
            // that means all the fees should be released by default.
            feeVO.setReleaseDate(new EDITDate().getFormattedDate());
            feeVO.setReleaseInd("Y");
            feeVO.setStatusCT("N");

            // when the fee is released AccountPendingStatus is 'Y'
            // for more information see comments in Fee.save() method
            feeVO.setAccountingPendingStatus("Y");
            feeVO.setTrxAmount(trxAmount.getBigDecimal());
            feeVO.setTransactionTypeCT(Fee.DIVISION_FEE_ACCRUAL_TRX_TYPE);
            feeVO.setContractNumber(null);
            feeVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
            feeVO.setOperator(Operator.OPERATOR_SYSTEM);
            feeVO.setToFromInd("F");

            if(this.matchingChargeCodeVOs != null && matchingChargeCodeVOs.length > 0)
            {
                // was using a client fund number ... set it in the Fee
                long chargeCodeFK = this.matchingChargeCodeVOs[0].getChargeCodePK();
                feeVO.setChargeCodeFK(chargeCodeFK);
            }

            Fee fee = new Fee(feeVO);

            try
            {
                fee.save();

                isDFACCTransactionCreated = true;
            }
            catch (EDITEngineException e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw e;
            }
        }

        return isDFACCTransactionCreated;
    }

    /**
     * The fund number in the input file may be the plain old filtered fund
     * number... or it may be the Client's fund number which corresponds
     * uniquely to a charge code in the system (filtered fund plus charge code).
     * This first looks up by assuming it is the Client Fund Number.  If it
     * isn't found, then it reverts to the Filtered Fund Number.
     * <P>
     * This also initializes the matchingChargeCodeVOs to null and then
     * sets it if the fundNumberOrClientFundNumber is actually a Client Fund
     * Number.
     *
     * @param fundNumberOrClientFundNumber
     * @return
     */
    FilteredFundVO getCorrectFilteredFundVOAndSetMatchingChargeCodeVOs(String fundNumberOrClientFundNumber)
    {
        // initialize this
        this.matchingChargeCodeVOs = null;

        ChargeCodeVO[] chargeCodeVOs =
                transformChargeCodes.
                getChargeCodeVOsForClientFundNumber(fundNumberOrClientFundNumber);

        if (chargeCodeVOs == null)
        {
            return getFilteredFundVOByFundNumber(fundNumberOrClientFundNumber);
        }
        else
        {
            // found a charge code - set the current one
            this.matchingChargeCodeVOs = chargeCodeVOs;

            long filteredFundPK = chargeCodeVOs[0].getFilteredFundFK();
            return getFilteredFundVOByFundPK(filteredFundPK);
        }
    }

    /**
     * Takes in a Fund number or a Client fund number
     * -- the input file can have either there - and returns back the real fund number.
     *
     * @param fundNumberOrClientFundNumber
     * @return
     */
    private String getRealFundNumberAndSetMatchingChargeCodeVOs(String fundNumberOrClientFundNumber)
    {
        FilteredFundVO targetFilteredFund =
                getCorrectFilteredFundVOAndSetMatchingChargeCodeVOs(fundNumberOrClientFundNumber);
        String realFundNumber = targetFilteredFund.getFundNumber();
        return realFundNumber;
    }

    FilteredFundVO getFilteredFundVOByFundNumber(String fundNumber)
    {
        FilteredFundVO[] filteredFundVOs = engineLookup.getByFundNumber(fundNumber);

        FilteredFundVO filteredFundVO = null;

        if (filteredFundVOs != null)
        {
            // the fund number is unique in the system, so only one corresponding record is returned.
            filteredFundVO = filteredFundVOs[0];
        }
        return filteredFundVO;
    }

    FilteredFundVO getFilteredFundVOByFundPK(long filteredFundPK)
    {
        FilteredFundVO[] filteredFundVOs = engineLookup.findFilteredFundByPK(filteredFundPK);

        if (filteredFundVOs != null)
        {
            return filteredFundVOs[0];
        }
        else
        {
            return null;
        }
    }

    /**
     * saves unit values to database.
     * @param unitValuesVOs
     */
    private void saveUnitValuesVOs(UnitValuesVO[] unitValuesVOs, FilteredFundVO filteredFundVO) throws Exception
    {
        engine.business.Calculator calcComponent = new engine.component.CalculatorComponent();

        for (int i = 0; i < unitValuesVOs.length; i++)
        {
            UnitValuesVO unitValuesVO = unitValuesVOs[i];

            calcComponent.createOrUpdateVONonRecursive(unitValuesVO);
        }

        Fee.updateUnits(filteredFundVO.getFilteredFundPK(), filteredFundVO.getPricingDirection());
    }

    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////// TESTING /////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    //    public static void main(String[] args) throws Exception
    //    {
    //        UnitValueImportProcessor uvip = new UnitValueImportProcessor();
    //
    //        UnitValueUpdateVO[] uvuVOs = new UnitValueUpdateVO[2];
    //
    //        // make a rate record for fund joe
    //        UnitValueUpdateVO uvuVO = new UnitValueUpdateVO();
    //        uvuVO.setFundIndicator("joe");
    //        edit.common.vo.RateRecord[] recs = new edit.common.vo.RateRecord[1];
    //        edit.common.vo.RateRecord rate = new edit.common.vo.RateRecord();
    //        rate.setDividendRate(new BigDecimal("2.71828"));
    //        recs[0] = rate;
    //        uvuVO.setRateRecord(recs);
    //        uvuVOs[0] = uvuVO;
    //
    //        // make a detail record with unit value for joe
    //        UnitValueUpdateVO uvuVO2 = new UnitValueUpdateVO();
    //        uvuVO2.setFundIndicator("joe");
    //        edit.common.vo.DetailRecord[] details = new edit.common.vo.DetailRecord[1];
    //        edit.common.vo.DetailRecord detail = new edit.common.vo.DetailRecord();
    //        detail.setPrice(new BigDecimal("3.1415926"));   /// THIS IS THE UNIT VALUE
    //        details[0] = detail;
    //        uvuVO2.setDetailRecord(details);
    //        uvuVO2.setRecordType("UNITVALUES");  // THIS ISN'T RIGHT BUT WILL WORK
    //        uvuVOs[1] = uvuVO2;
    //
    //        uvip.processUnitValueUpdateVOs(uvuVOs, "2005/04/02");
    //    }

}
