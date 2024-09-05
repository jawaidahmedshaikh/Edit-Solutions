/*
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.batch;

import batch.business.*;
import contract.dm.dao.DAOFactory;
import contract.Segment;
import contract.ContractClient;
import edit.common.*;
import edit.common.vo.*;
import edit.services.*;
import edit.services.config.*;
import edit.services.logging.*;
import event.dm.composer.*;
import event.dm.composer.VOComposer;
import event.dm.dao.FastDAO;
import event.EDITTrx;
import fission.utility.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import logging.*;

import org.apache.logging.log4j.Logger;

import role.dm.composer.*;
import reinsurance.dm.dao.TreatyDAO;
import reinsurance.dm.dao.ReinsurerDAO;
import client.dm.composer.ClientDetailComposer;
import client.*;
import staging.CheckStaging;


public class BankProcessor implements Serializable
{
    private static final String SEPERATOR_FOR_PRIMARY_OWNER_AND_SECONDARY_OWNER = "&";
    private static final String OUTPUT_FILE_TYPE_FLAT = "Flat";
    private static final String OUTPUT_FILE_TYPE_XML = "XML";
    private static final String OUTPUT_FILE_TYPE_STAGING = "Staging";

    public File checkExportFile;
    public File eftExportFile;

    public BankProcessor()
    {
        super();
    }

    public void createBankExtracts(String companyName, String contractId, String outputFileType)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_BANK_EXTRACTS).tagBatchStart(Batch.BATCH_JOB_CREATE_BANK_EXTRACTS, "Bank Extract");

        ProductStructureVO[] productStructureVOs = new engine.component.LookupComponent().getProductStructureVOs(companyName);


        try
        {
            if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_XML) ||
                outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_STAGING))
            {
                bankJobForXML(companyName, contractId, productStructureVOs, outputFileType);
            }
            else
            {
                bankJobForFlatFile(companyName, contractId, productStructureVOs);
            }
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();

            LogEvent logEvent = new LogEvent("Bank Extracts Errored", e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);

            //  Log error to database
            EDITMap columnInfo = new EDITMap("UserDefNumber", "N/A");
            columnInfo.put("ProcessDate", new EDITDate().getFormattedDate());
            columnInfo.put("ContractNumber", contractId);

            Log.logToDatabase(Log.BANK, "Bank Extracts Errored: " + e.getMessage(), columnInfo);
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_BANK_EXTRACTS).tagBatchStop();
        }
    }

    private void bankJobForXML(String companyName, String contractId, 
                               ProductStructureVO[] productStructureVOs, String outputFileType) throws Exception
    {
        if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_XML))
        {
            checkExportFile = getCheckExportFile();
            insertStartCheckEFT(checkExportFile);

            eftExportFile = getEFTExportFile();
            insertStartCheckEFT(eftExportFile);
        }

        if (validContractNumber(contractId))
        {
            processRequestForSingleContract(contractId, outputFileType);
        }
        else
        {
            processRequestForAllCompanies(companyName, productStructureVOs, outputFileType);
        }

        if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_XML))
        {
            insertEndCheckEFT(checkExportFile);
            insertEndCheckEFT(eftExportFile);
        }
    }
    
    /**
     * If the specified contractNumber is "" (empty String) or NULL (predefined
     * constant), then we assume the contractNumber is [not] valid.
     * @param contractNumber
     * @return true as long as contractNumber is not "" or NULL
     */
    private boolean validContractNumber(String contractNumber)
    {
        boolean validContractNumber = true;
        
        if (contractNumber == null)
        {
            validContractNumber = false;
        }
        else if (contractNumber.trim().equals(""))
        {
            validContractNumber = false;
        }
        else if (contractNumber.trim().equalsIgnoreCase(Batch.NULL))
        {
            validContractNumber = false;
        }
        
        return validContractNumber;
    }


    private void bankJobForFlatFile(String companyName, String contractId, ProductStructureVO[] productStructureVOs) throws Exception
    {
        BankProcessForFlatFile bankProcessForFlatFile = new BankProcessForFlatFile();

        bankProcessForFlatFile.createBankExtracts(companyName, contractId, productStructureVOs);
    }


    public String processRequestForSingleContract(String contractId, String outputFileType)
    {
        return "*** Bank Extracts Completed WITHOUT ERRORS ***";
    }

    /**
     * Get all the SuspenseFKs from the InSuspense table records. From these keys get the  suspense info to  determine
     * if the data qualifies for check/eft creation.
     * @param productStructureVOs
     * @return
     * @throws Exception
     */
    private void processRequestForAllCompanies(String companyName, ProductStructureVO[] productStructureVOs,
                                               String outputFileType) throws Exception
    {
        EDITDateTime stagingDate = new EDITDateTime();

        long[] suspenseFKs = new FastDAO().findSuspensePKsForBankExtract();

        CheckEFTDetailVO checkEFTDetailVO = null;

        if (suspenseFKs != null)
        {
            for (int i = 0; i < suspenseFKs.length; i++)
            {
                try
                {
                    checkEFTDetailVO = new CheckEFTDetailVO();

                    SuspenseVO suspenseVO = getSuspense(suspenseFKs[i]);

                    InSuspenseVO[] inSuspenseVO = suspenseVO.getInSuspenseVO();

                    String disbSource = Util.initString(suspenseVO.getDisbursementSourceCT(), "");
                    String disbAddressType = null;

                    String direction = suspenseVO.getDirectionCT();
                    String status = "N";
                    String suppressCheck = "N";

                    EDITTrxHistoryVO editTrxHistoryVO = null;
                    EDITTrxVO editTrxVO = null;
                    FinancialHistoryVO[] financialHistoryVO = null;
                    SegmentVO segmentVO = null;
                    ClientDetailVO clientDetailVO = null;
                    ProductStructureVO productStructureVO = null;
                    ClientSetupVO clientSetupVO = null;
                    ContractSetupVO contractSetupVO = null;

                    if ((inSuspenseVO != null) && (inSuspenseVO.length > 0))
                    {
                        editTrxHistoryVO = getEDITTrxHistory(inSuspenseVO);
                        editTrxVO = (EDITTrxVO) editTrxHistoryVO.getParentVO(EDITTrxVO.class);
                        financialHistoryVO = (FinancialHistoryVO[]) editTrxHistoryVO.getFinancialHistoryVO();

                        disbSource = setDisbursement(suspenseVO, financialHistoryVO[0]);
                        disbAddressType = setAddressType(suspenseVO, editTrxHistoryVO);
                        status = editTrxVO.getStatus();
                        suppressCheck = Util.initString(editTrxVO.getNoCheckEFT(), "N");
                    }

                    if (direction != null)
                    {
                        if (((direction.equalsIgnoreCase("Apply") && status.equalsIgnoreCase("R") && (disbSource.equalsIgnoreCase("Check") || disbSource.equalsIgnoreCase("EFT"))) ||
                             (direction.equalsIgnoreCase("Remove") && (status.equalsIgnoreCase("N") || status.equalsIgnoreCase("A")))) &&
                            (!suspenseVO.getMaintenanceInd().equalsIgnoreCase("D") && !suspenseVO.getMaintenanceInd().equalsIgnoreCase("R")))
                        {
                            if (suppressCheck.equalsIgnoreCase("Y"))
                            {
                                updateSuspense(suspenseVO);
                            }
                            else
                            {
                                clientDetailVO = createClientDetailVO(suspenseVO);
                                if (editTrxVO != null)
                                {
                                    clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
                                    contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);

                                    if (contractSetupVO.getParentVOs() != null)
                                    {
                                        segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);
                                    }

                                    // Reinsurer transactions will not have ClientRoleFK
                                    // Will have only TreatyFK, needs special processsing to get ClientDetail information.
                                    if (editTrxVO.getTransactionTypeCT().equals(EDITTrx.TRANSACTIONTYPECT_REINSURANCE_CHECK))
                                    {
                                        TreatyVO treatyVO = new TreatyDAO().findBy_PK(clientSetupVO.getTreatyFK())[0];

                                        ReinsurerVO reinsurerVO = new ReinsurerDAO().findBy_PK(treatyVO.getReinsurerFK())[0];

                                        if (clientDetailVO == null)
                                        {
                                            clientDetailVO = composeClientDetailVO(reinsurerVO.getClientDetailFK(), disbAddressType, segmentVO);
                                        }
                                    }
                                    else
                                    {
                                        if (clientSetupVO.getContractClientFK() == 0)
                                        {
                                            if (clientDetailVO == null)
                                            {
                                                clientDetailVO = getClientDetailVO(clientSetupVO.getClientRoleFK(), disbAddressType, segmentVO);
                                            }
                                        }
                                        else
                                        {
                                            ContractClientVO contractClientVO = DAOFactory.getContractClientDAO().findByContractClientPK(clientSetupVO.getContractClientFK(), false, new ArrayList())[0];

                                            productStructureVO = matchProductStructureKeys(productStructureVOs, segmentVO.getProductStructureFK());

                                            if ((disbAddressType == null) || disbAddressType.equals(""))
                                            {
                                                disbAddressType = "PrimaryAddress";
                                            }

                                            if (clientDetailVO == null)
                                            {
                                                clientDetailVO = getClientDetailVO(contractClientVO.getClientRoleFK(), disbAddressType, segmentVO);
                                            }
                                        }
                                    }
                                }

                                checkEFTDetailVO.addSuspenseVO(suspenseVO);

                                if (editTrxHistoryVO != null)
                                {
                                    checkEFTDetailVO.addEDITTrxHistoryVO(editTrxHistoryVO);
                                }

                                if (segmentVO != null)
                                {
                                    checkEFTDetailVO.addSegmentVO(segmentVO);
                                }

                                if (productStructureVO != null)
                                {
                                    checkEFTDetailVO.addProductStructureVO(productStructureVO);
                                }

                                if (clientDetailVO != null)
                                {
                                    checkEFTDetailVO.addClientDetailVO(clientDetailVO);
                                }

                                if ((disbSource == null) || disbSource.equals("") || disbSource.equalsIgnoreCase("Paper"))
                                {
                                    if (clientDetailVO != null && clientDetailVO.getClientAddressVOCount() > 0)
                                    {
                                        if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_XML))
                                        {
                                            exportCheck(checkEFTDetailVO, checkExportFile);
                                        }

                                        updateSuspense(suspenseVO);

                                        if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_STAGING))
                                        {
                                            CheckStaging checkStaging = new CheckStaging(companyName, stagingDate);
                                            checkStaging.stageTables(checkEFTDetailVO);
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("[* Bank Process *] Failed For SuspensePK [" + suspenseFKs[i] + "] - Proper Address Not Found");
                                    }
                                }
                                else
                                {
                                    if (outputFileType.equalsIgnoreCase(OUTPUT_FILE_TYPE_XML))
                                    {
                                        exportEFT(checkEFTDetailVO, eftExportFile);
                                    }

                                    updateSuspense(suspenseVO);
                                }
                            }

                            editTrxHistoryVO = null;
                            editTrxVO = null;
                            financialHistoryVO = null;
                            clientSetupVO = null;
                            contractSetupVO = null;
                            segmentVO = null;
                            clientDetailVO = null;
                            checkEFTDetailVO = null;
                        }
                    }

                    inSuspenseVO = null;
                    suspenseVO = null;

                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_BANK_EXTRACTS).updateSuccess();
                }
                catch (Exception e)
                {
                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_BANK_EXTRACTS).updateFailure();

                    System.out.println("[* Bank Process *] Failed For SuspensePK [" + suspenseFKs[i] + "]");

                    System.out.println(e);

                    e.printStackTrace();
                }
            }

            suspenseFKs = null;
        }
    }


    public SuspenseVO getSuspense(long suspenseFK) throws Exception
    {
        //get fluffy suspenseVO
        List voInclusionList = new ArrayList();
        voInclusionList.add(InSuspenseVO.class);

        SuspenseVO suspenseVO = new VOComposer().composeSuspenseVO(suspenseFK, voInclusionList);

        return suspenseVO;
    }

    public EDITTrxHistoryVO getEDITTrxHistory(InSuspenseVO[] inSuspenseVO)
    {
        //The following is needed to build a fluffy editTrxHistoryVO from the key in the inSuspenseVO
        List voInclusionList = new ArrayList();
        voInclusionList.add(WithholdingHistoryVO.class);
        voInclusionList.add(FinancialHistoryVO.class);
        voInclusionList.add(CommissionHistoryVO.class);
        voInclusionList.add(ChargeHistoryVO.class);
        voInclusionList.add(EDITTrxVO.class);
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ClientRoleVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(GroupSetupVO.class);
        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(PayoutVO.class);
        voInclusionList.add(ContractClientVO.class);

        EDITTrxHistoryVO editTrxHistoryVO = new EDITTrxHistoryComposer(voInclusionList).compose(inSuspenseVO[0].getEDITTrxHistoryFK());

        return editTrxHistoryVO;
    }

    public String setDisbursement(SuspenseVO suspenseVO, FinancialHistoryVO financialHistoryVO)
    {
        String disbSource = Util.initString(suspenseVO.getDisbursementSourceCT(), "");

        String disbursementSource = Util.initString(financialHistoryVO.getDisbursementSourceCT(), "");
        if (!disbursementSource.equals("") && disbSource.equals(""))
        {
            disbSource = disbursementSource;
        }

        return disbSource;
    }

    public String setAddressType(SuspenseVO suspenseVO, EDITTrxHistoryVO editTrxHistoryVO)
    {
        String disbAddressType = Util.initString(suspenseVO.getAddressTypeCT(), "");

        String addressType = Util.initString(editTrxHistoryVO.getAddressTypeCT(), "");

        if (addressType.equals("") && disbAddressType.equals(""))
        {
            disbAddressType = "PrimaryAddress";
        }
        else
        {
            disbAddressType = addressType;
        }

        return disbAddressType;
    }


    public ClientDetailVO createClientDetailVO(SuspenseVO suspenseVO)
    {
        ClientDetailVO clientDetailVO =null;
        ClientDetail clientDetail = null;
        long clientDetailPK = suspenseVO.getClientDetailFK();
        Preference preference = null;
        ClientAddress clientAddress = null;

        if (clientDetailPK != 0)
        {
            clientDetail = (ClientDetail)ClientDetail.findByPK(clientDetailPK);
            if (clientDetail != null)
            {
                clientDetailVO = (ClientDetailVO)clientDetail.getVO();

                PreferenceVO preferenceVO = createPreferenceVO(suspenseVO, clientDetailVO);

                if (preferenceVO != null)
                {
                    clientDetailVO.addPreferenceVO(preferenceVO);
                }

                String addressType = suspenseVO.getAddressTypeCT();
                if (suspenseVO.getClientAddressFK() != 0)
                {
                    clientAddress = ClientAddress.findByPK(suspenseVO.getClientAddressFK());
                }
                else if (addressType != null)
                {
                    clientAddress = ClientAddress.findByClientDetailPK_And_AddressTypeCT(clientDetailPK, addressType);
                }
                else
                {
                    clientAddress = ClientAddress.findCurrentAddress(new Long(clientDetailPK));
                }

                if (clientAddress != null)
                {
                    clientDetailVO.addClientAddressVO((ClientAddressVO)clientAddress.getVO());
                }
            }
        }

        return clientDetailVO;
    }

    /**
     * Creates the PreferenceVO and sets PrintAs as per following business rules.
     * If the PrintAs from Preference of Suspense entry is blank then PrintAs field is populated
     * from Preference of corresponding Client record of Suspense entry.
     * @param suspenseVO
     * @param clientDetailVO
     * @return
     */
    private PreferenceVO createPreferenceVO(SuspenseVO suspenseVO, ClientDetailVO clientDetailVO)
    {
        PreferenceVO preferenceVO = null;
        Preference preference = null;
        String printAs = null;
        boolean overridePreferenceFound = false;

        if (suspenseVO.getPreferenceFK() != 0)
        {
            preference = Preference.findByPK(suspenseVO.getPreferenceFK());

            preferenceVO = (PreferenceVO) preference.getVO();

            overridePreferenceFound = true;

            printAs = preferenceVO.getPrintAs();
        }

        // If Suspense.Preference.PrintAs is blank ...get the Preference from corresponding client record of Suspense entry.
        if (printAs == null)
        {
            preference = Preference.findByClientDetailFK(new Long(suspenseVO.getClientDetailFK()));

            if (preference != null)
            {
                if (!overridePreferenceFound)
                {
                    preferenceVO = (PreferenceVO) preference.getVO();
                }

                printAs = ((PreferenceVO) preference.getVO()).getPrintAs();
            }
        }

        // If either Client does not have preference or If ClientDetail.Preference.PrintAs is blank
        if (printAs == null)
        {
            if (clientDetailVO.getTrustTypeCT().equals(ClientDetail.TRUSTTYPECT_INDIVIDUAL))
            {
                printAs = clientDetailVO.getFirstName() + " " + clientDetailVO.getLastName();
            }
            else
            {
                printAs = clientDetailVO.getCorporateName();
            }
        }

        if (preferenceVO != null)
        {
            preferenceVO.setPrintAs(printAs);
        }

        return preferenceVO;
    }

    private String roundDollarFields(CheckEFTDetailVO checkEFTDetailVO)
    {
        String[] fieldNames = setupFieldNamesForRounding();

        String voToXML = Util.roundDollarTextFields(checkEFTDetailVO, fieldNames);

        return voToXML;
    }

    private ProductStructureVO matchProductStructureKeys(ProductStructureVO[] productStructureVOs, long productStructureFK)
    {
        ProductStructureVO productStructureVO = null;

        for (int i = 0; i < productStructureVOs.length; i++)
        {
            if (productStructureFK == productStructureVOs[i].getProductStructurePK())
            {
                productStructureVO = productStructureVOs[i];

                break;
            }
        }

        return productStructureVO;
    }

    /**
     * Utility method to compose ClientDetailVO and to have only ClientAddress that is of disbAddressType
     * @param clientDetailPK
     * @param disbAddressType
     * @return
     * @throws Exception
     */
    public ClientDetailVO composeClientDetailVO(long clientDetailPK, String disbAddressType, SegmentVO segmentVO) throws Exception
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(PreferenceVO.class);
        voInclusionList.add(TaxInformationVO.class);
        voInclusionList.add(ClientAddressVO.class);

        ClientDetailVO clientDetailVO = new ClientDetailComposer(voInclusionList).compose(clientDetailPK);

        return getDisbursementAddress(clientDetailVO, disbAddressType, segmentVO);
    }

    /**
     * Utility method to compose ClientDetailVO by ClientRole and to have only ClientAddress that is of disbAddressType
     * @param clientRolePK
     * @param disbAddressType
     * @return
     * @throws Exception
     */
    public ClientDetailVO getClientDetailVO(long clientRolePK, String disbAddressType, SegmentVO segmentVO) throws Exception
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(ClientDetailVO.class);
        voInclusionList.add(PreferenceVO.class);
        voInclusionList.add(TaxInformationVO.class);
        voInclusionList.add(ClientAddressVO.class);

        ClientRoleVO clientRoleVO = new ClientRoleComposer(voInclusionList).compose(clientRolePK);

        ClientDetailVO clientDetailVO = (ClientDetailVO) clientRoleVO.getParentVO(ClientDetailVO.class);

        return getDisbursementAddress(clientDetailVO, disbAddressType, segmentVO);
    }

    /**
     * Utility method to have ClientAddress of passed in AddressType
     * @param clientDetailVO
     * @param disbAddressType
     * @return
     * @throws Exception
     */
    private ClientDetailVO getDisbursementAddress(ClientDetailVO clientDetailVO, String disbAddressType, SegmentVO segmentVO) throws Exception
    {
        ClientAddressVO[] clientAddressVOs = clientDetailVO.getClientAddressVO();

        ClientAddressVO disbursementAddress = null;

        if (clientAddressVOs != null)
        {
            EDITDate currentDate = new EDITDate();

            if (disbAddressType.equals(""))
            {
                disbAddressType = "PrimaryAddress";
            }
            
            if (disbAddressType.equalsIgnoreCase("PrimaryAddress"))
            {
                disbursementAddress = checkForSecondaryAddress(clientAddressVOs, currentDate);
            }

            if (disbursementAddress == null)
            {
                for (int i = 0; i < clientAddressVOs.length; i++)
                {
                    if (clientAddressVOs[i].getAddressTypeCT().equals(disbAddressType))
                    {
                        String effectiveDateString = clientAddressVOs[i].getEffectiveDate();
                        String terminateDateString = clientAddressVOs[i].getTerminationDate();

                        if (effectiveDateString != null)
                        {
                            if (terminateDateString != null)
                            {
                                EDITDate effectiveDate = new EDITDate(effectiveDateString);
                                EDITDate terminationDate = new EDITDate(terminateDateString);

                                if ( (currentDate.after(effectiveDate) || currentDate.equals(effectiveDate))  &&
                                     (currentDate.before(terminationDate) || currentDate.equals(terminationDate)) )
                                {
                                    disbursementAddress = clientAddressVOs[i];
                                }
                            }
                        }

                        // the effectivedate for address is not a compulsory field and need not be entered
                        // so if you find the address of 'disbAddressType' with the effective date 'Null'
                        // for the corresponding client continue the process and extract the check transaction
                        else
                        {
                            disbursementAddress = clientAddressVOs[i];
                        }
                    }
                }
            }
        }

        clientDetailVO.removeAllClientAddressVO();

        if (disbursementAddress != null)
        {
            clientDetailVO.addClientAddressVO(disbursementAddress);
        }

        removeNonPrimaryBankAccountInformationAndPreference(clientDetailVO);

        updatePreferenceInCheckEFTDetailVO(clientDetailVO, segmentVO);

        return clientDetailVO;
    }

    private ClientAddressVO checkForSecondaryAddress(ClientAddressVO[] clientAddressVOs, EDITDate currentDate) throws Exception
    {
        ClientAddressVO secondaryAddress = null;

        for (int i = 0; i < clientAddressVOs.length; i++)
        {
            if (clientAddressVOs[i].getAddressTypeCT().equals("SecondaryAddress"))
            {
                EDITDate effectiveDate = null;
                EDITDate terminationDate = null;
                if (clientAddressVOs[i].getEffectiveDate() != null)
                {
                    effectiveDate = new EDITDate(clientAddressVOs[i].getEffectiveDate());
                }

                if (clientAddressVOs[i].getTerminationDate() != null)
                {
                    terminationDate = new EDITDate(clientAddressVOs[i].getTerminationDate());
                }

                if ( (effectiveDate == null || (currentDate.after(effectiveDate) || currentDate.equals(effectiveDate))) &&
                     (terminationDate == null || (currentDate.before(terminationDate) || currentDate.equals(terminationDate))) )
                {
                    String startDate = clientAddressVOs[i].getStartDate();
                    String stopDate = clientAddressVOs[i].getStopDate();

                    if (((currentDate.getFormattedMonth() + EDITDate.DATE_DELIMITER + currentDate.getFormattedDay()).compareTo(startDate) >= 0) && ((currentDate.getFormattedMonth() + EDITDate.DATE_DELIMITER + currentDate.getFormattedDay()).compareTo(stopDate) <= 0))
                    {
                        secondaryAddress = clientAddressVOs[i];

                        break;
                    }
                }
            }
        }

        return secondaryAddress;
    }

    /**
     * Updates the Preference.PrintAs as per the following rules.
     * If Preference.PrintAs is null
     *   If Segment.ContractTypeCT is 'Joint'
     *      Preference.PrintAs = Primary Owner FirstName LastName + Secondary Owner FirstName LastName
     *   Otherwise
     *      Preference.PrintAs = Primary Owner FirstName LastName
     * The length is restricted to 70 characters because the length of Preference.PrintAs field length is 70.
     * When the Segment.ContractTypeCT is 'Joint' do not move CorporateName.
     * If the ClientDetail does not have Preference don't do anything.
     * @param clientDetailVO
     * @param segmentVO
     */
    private void updatePreferenceInCheckEFTDetailVO(ClientDetailVO clientDetailVO, SegmentVO segmentVO)
    {
        if (clientDetailVO.getPreferenceVO().length > 0)
        {
            PreferenceVO preferenceVO = clientDetailVO.getPreferenceVO(0);

            String printAs = preferenceVO.getPrintAs();

            if (printAs == null)
            {
                if (segmentVO != null)
                {
                    String contractTypeCT = segmentVO.getContractTypeCT();

                    if (contractTypeCT != null && contractTypeCT.equals(Segment.CONTRACTTYPECT_JOINT))
                    {
                        Segment segment = Segment.findByPK(new Long(segmentVO.getSegmentPK()));

                        printAs = clientDetailVO.getFirstName() + " " + clientDetailVO.getLastName();

                        ContractClient secondaryOwnerCC = segment.getSecondaryOwner();

                        if (secondaryOwnerCC != null)
                        {
                            ClientDetail secondaryOwner = secondaryOwnerCC.getClientRole().getClientDetail();

                            printAs = printAs + " " + SEPERATOR_FOR_PRIMARY_OWNER_AND_SECONDARY_OWNER + " " +
                                    secondaryOwner.getFirstName() + " " + secondaryOwner.getLastName();
                        }
                    }
                    else
                    {
                        if (clientDetailVO.getCorporateName() == null)
                        {
                            printAs = clientDetailVO.getFirstName() + " " + clientDetailVO.getLastName();
                        }
                        else
                        {
                            printAs = clientDetailVO.getCorporateName();
                        }
                    }

                    if (printAs.length() > 70)
                    {
                        printAs = printAs.substring(0, 70);
                    }
                }
                else
                {
                    if (clientDetailVO.getTrustTypeCT().equals(ClientDetail.TRUSTTYPECT_INDIVIDUAL))
                    {
                        printAs = clientDetailVO.getFirstName() + " " + clientDetailVO.getLastName();
                    }
                    else
                    {
                        printAs = clientDetailVO.getCorporateName();
                    }
                }

                preferenceVO.setPrintAs(printAs);
            }
        }
    }

    /**
     * Hold on to Primary Bank Account Information and Preference only.
     * That is with BankAccountInformationVO.OverrideStatus = 'P' and Preference.OverrideStatus='P'
     * @param clientDetailVO
     */
    private void removeNonPrimaryBankAccountInformationAndPreference(ClientDetailVO clientDetailVO)
    {
        PreferenceVO[] preferenceVOs = clientDetailVO.getPreferenceVO();

        clientDetailVO.removeAllPreferenceVO();

        for (int i = 0; i < preferenceVOs.length; i++)
        {
            PreferenceVO preferenceVO = preferenceVOs[i];

            if (preferenceVO.getOverrideStatus().equals(Preference.PRIMARY))
            {
                clientDetailVO.addPreferenceVO(preferenceVO);

                // At any given point of time a client will have only one Preference with OverrideStatus of 'P'
                // no need to loop thru.
                break;
            }
        }
    }

    public void updateSuspense(SuspenseVO suspenseVO) throws Exception
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        if (suspenseVO.getRemovalReason() != null && suspenseVO.getRemovalReason().equalsIgnoreCase("Refund"))
        {
            suspenseVO.setMaintenanceInd("R");
            suspenseVO.setDateAppliedRemoved(new EDITDate().getFormattedDate());
        }
        else
        {
            suspenseVO.setMaintenanceInd("D");
        }

        eventComponent.createOrUpdateVO(suspenseVO, false);
    }

    private File getCheckExportFile()
    {
        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

        File exportFile = new File(export1.getDirectory() + "SEGCHECKS_" + System.currentTimeMillis() + ".xml");

        return exportFile;
    }

    private File getEFTExportFile()
    {
        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

        File exportFile = new File(export1.getDirectory() + "SEGEFTS_" + System.currentTimeMillis() + ".xml");

        return exportFile;
    }

    private void exportCheck(CheckEFTDetailVO checkEFTDetailVO, File checkExportFile) throws Exception
    {
        String parsedXML = roundDollarFields(checkEFTDetailVO);

        parsedXML = XMLUtil.parseOutXMLDeclaration(parsedXML);

        appendToFile(checkExportFile, parsedXML);
    }

    private void exportEFT(CheckEFTDetailVO checkEFTDetailVO, File eftExportFile)
    {
        String parsedXML = null;

        try
        {
            parsedXML = roundDollarFields(checkEFTDetailVO);

            parsedXML = XMLUtil.parseOutXMLDeclaration(parsedXML);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        appendToFile(eftExportFile, parsedXML);
    }

    private void appendToFile(File exportFile, String data)
    {
        BufferedWriter bw = null;

        try
        {
            bw = new BufferedWriter(new FileWriter(exportFile, true));

            bw.write(data);

            bw.flush();
        }
        catch (IOException e)
        {
          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (bw != null) bw.close();
            }
            catch (IOException e)
            {
              System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

    }

    private void insertStartCheckEFT(File exportFile) throws Exception
    {
        appendToFile(exportFile, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        appendToFile(exportFile, "<CheckEFTCorrespondenceVO>\n");
    }

    private void insertEndCheckEFT(File exportFile)
    {
        appendToFile(exportFile, "\n</CheckEFTCorrespondenceVO>");
    }

    private String[] setupFieldNamesForRounding()
    {
        List fieldNames = new ArrayList();

        fieldNames.add("SuspenseVO.SuspenseAmount");
        fieldNames.add("SuspenseVO.OriginalAmount");
        fieldNames.add("SuspenseVO.PendingSuspenseAmount");
        fieldNames.add("InSuspenseVO.Amount");
        fieldNames.add("SegmentVO.Amount");
        fieldNames.add("SegmentVO.CostBasis");
        fieldNames.add("SegmentVO.RecoveredCostBasis");
        fieldNames.add("SegmentVO.Charges");
        fieldNames.add("SegmentVO.Loads");
        fieldNames.add("SegmentVO.Fees");
        fieldNames.add("SegmentVO.FreeAmountRemaining");
        fieldNames.add("SegmentVO.FreeAmount");
        fieldNames.add("FinancialHistoryVO.GrossAmount");
        fieldNames.add("FinancialHistoryVO.NetAmount");
        fieldNames.add("FinancialHistoryVO.CheckAmount");
        fieldNames.add("FinancialHistoryVO.FreeAmount");
        fieldNames.add("FinancialHistoryVO.TaxBenefit");
        fieldNames.add("FinancialHistoryVO.Liability");
        fieldNames.add("FinancialHistoryVO.CommissionableAmount");
        fieldNames.add("FinancialHistoryVO.MaxCommissionAmount");
        fieldNames.add("FinancialHistoryVO.CostBasis");
        fieldNames.add("FinancialHistoryVO.AccumulatedValue");
        fieldNames.add("FinancialHistoryVO.SurrenderValue");
        fieldNames.add("CheckEFTCorrespondenceVO.GrossAmountTotal");
        fieldNames.add("CheckEFTCorrespondenceVO.CheckAmountTotal");
        fieldNames.add("CheckEFTCorrespondenceVO.FederalWithholdingTotal");
        fieldNames.add("CheckEFTCorrespondenceVO.StateWithholdingTotal");

        return (String[]) fieldNames.toArray(new String[fieldNames.size()]);
    }
}
