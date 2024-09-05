package contract.batch;

import contract.business.Contract;
import contract.interfaces.CheckInterfaceCmd;
import contract.interfaces.EFTInterfaceCmd;
import contract.interfaces.ValuationInterfaceCmd;
import contract.dm.dao.*;
import edit.common.*;
import edit.common.vo.*;
import edit.services.*;
import fission.utility.Util;

import java.io.Serializable;
import java.util.*;

import event.dm.dao.*;
import event.dm.dao.FastDAO;
import event.*;
import reinsurance.dm.dao.*;
import batch.business.*;

public class BankProcessForFlatFile extends BankProcessor implements Serializable
{

    private StringBuffer checkFileData = new StringBuffer();
    private StringBuffer eftFileData   = new StringBuffer();
    private StringBuffer valuationFileData = new StringBuffer();
    private CheckInterfaceCmd checkInterfaceCmd = null;
    private EFTInterfaceCmd eftInterfaceCmd = null;
    private ValuationInterfaceCmd valuationInterfaceCmd = null;
    private Hashtable checkValues = null;
    private Hashtable eftValues = null;

    private static final boolean runCheckAndEFT = true;
    private static final boolean runValuationExtract = false;

	public BankProcessForFlatFile()  {

        super();
	}

    public void createBankExtracts(String companyName, String contractId, ProductStructureVO[] productStructureVOs) throws Exception
    {

        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_BANK_EXTRACTS).tagBatchStart(Batch.BATCH_JOB_CREATE_BANK_EXTRACTS, "Bank Extract");

        SuspenseVO suspenseVO = null;

        checkInterfaceCmd = new CheckInterfaceCmd();

        eftInterfaceCmd = new EFTInterfaceCmd();

        long[] suspensePKs = null;


        if (contractId != null)
        {
            suspensePKs = new FastDAO().findSuspensePKsForContract(contractId);
        }

        else
        {
            suspensePKs = new FastDAO().findSuspensePKsForBankJob();
        }

        try
        {
            checkValues = new Hashtable();
            checkValues.put("numberOfCheckRecords", new EDITBigDecimal("0"));
            checkValues.put("checkGrossDollars", new EDITBigDecimal("0"));
            checkValues.put("checkCheckAmtDollars", new EDITBigDecimal("0"));
            checkValues.put("checkFedWithholding", new EDITBigDecimal("0"));
            checkValues.put("checkStateWithholding", new EDITBigDecimal("0"));

            eftValues = new Hashtable();
            eftValues.put("numberOfEFTRecords", new EDITBigDecimal("0"));
            eftValues.put("eftGrossDollars", new EDITBigDecimal("0"));
            eftValues.put("eftCheckAmtDollars", new EDITBigDecimal("0"));
            eftValues.put("eftFedWithholding", new EDITBigDecimal("0"));
            eftValues.put("eftStateWithholding", new EDITBigDecimal("0"));

            if (suspensePKs != null)
            {
                for (int i = 0; i < suspensePKs.length; i++)
                {

                    suspenseVO = super.getSuspense(suspensePKs[i]);

                    InSuspenseVO[] inSuspenseVO = suspenseVO.getInSuspenseVO();

                    String disbSource = null;
                    String disbAddressType = null;

                    String direction = suspenseVO.getDirectionCT();
                    String status = "N";
                    String suppressCheck = "N";

                    EDITTrxHistoryVO editTrxHistoryVO = null;
                    EDITTrxVO editTrxVO = null;
                    FinancialHistoryVO[] financialHistoryVO = null;
                    ClientDetailVO clientDetailVO = null;
                    ProductStructureVO productStructureVO = null;
                    ClientSetupVO clientSetupVO = null;
                    ContractSetupVO contractSetupVO = null;

                    if ((inSuspenseVO != null) && (inSuspenseVO.length > 0))
                    {
                        editTrxHistoryVO = super.getEDITTrxHistory(inSuspenseVO);
                        editTrxVO = (EDITTrxVO) editTrxHistoryVO.getParentVO(EDITTrxVO.class);
                        financialHistoryVO = (FinancialHistoryVO[]) editTrxHistoryVO.getFinancialHistoryVO();

                        disbSource = setDisbursement(suspenseVO, financialHistoryVO[0]);
                        disbAddressType = setAddressType(suspenseVO, editTrxHistoryVO);
                        status = editTrxVO.getStatus();
                        suppressCheck = Util.initString(editTrxVO.getNoCheckEFT(), "N");

                        if (suppressCheck.equalsIgnoreCase("Y"))
                        {
                            super.updateSuspense(suspenseVO);
                        }
                        else
                        {
                            clientDetailVO = determineClient(suspenseVO, editTrxVO, disbAddressType);

                            if (disbSource != null)
                            {
                                if (disbSource.equalsIgnoreCase("Paper"))
                                {
                                createCheckExtractRecord(suspenseVO, editTrxHistoryVO, clientDetailVO);
                                }
                                else if (disbSource.equalsIgnoreCase("EFT"))
                                {
                                createEFTExtractRecord(editTrxHistoryVO, clientDetailVO);
                                }
                            }

                            suspenseVO.setAccountingPendingInd("Y");

                            updateSuspense(suspenseVO);
                        }
                    }
                }

                EDITDate currentDate = new EDITDate();

                try
                {

//                    checkInterfaceCmd.exportExtract(checkFileData, "SEGCHK.prn");
                    StringBuffer checkSB = new StringBuffer();
                    checkSB.append(currentDate);
                    checkSB.append("\n");
                    checkSB.append("Total # Of Check Records: ");
                    EDITBigDecimal numberOfChecks = (EDITBigDecimal)eftValues.get("numberOfCheckRecords");
                    if (numberOfChecks != null)
                    {
                        checkSB.append(numberOfChecks.toString());
                    }
                    checkSB.append("\n");
                    checkSB.append("Check Gross Dollars: ");
                    EDITBigDecimal checkGrossDollars = (EDITBigDecimal)(checkValues.get("checkGrossDollars"));
                    checkSB.append(Util.formatDecimal("###,###,###,##0.00", checkGrossDollars.round(2)));
                    checkSB.append("\n");
                    checkSB.append("Check Check Amount Dollars: ");
                    EDITBigDecimal checkCheckAmtDollars = (EDITBigDecimal)(checkValues.get("checkCheckAmtDollars"));
                    checkSB.append(Util.formatDecimal("###,###,###,##0.00", checkCheckAmtDollars.round(2)));
                    checkSB.append("\n");
                    checkSB.append("Check Federal Withholding: ");
                    EDITBigDecimal checkFedWithholding = (EDITBigDecimal)(checkValues.get("checkFedWithholding"));
                    checkSB.append(Util.formatDecimal("###,###,###,##0.00", checkFedWithholding.round(2)));
                    checkSB.append("\n");
                    checkSB.append("Check State Withholding: ");
                    EDITBigDecimal checkStateWithholding = (EDITBigDecimal)(checkValues.get("checkStateWithholding"));
                    checkSB.append(Util.formatDecimal("###,###,###,##0.00", checkStateWithholding.round(2)));
                    checkInterfaceCmd.exportReconciliation(checkSB, "checkRecon");

//                    eftInterfaceCmd.exportExtract(eftFileData, "SEGEFT.prn");
                    StringBuffer eftSB = new StringBuffer();
                    eftSB.append(currentDate);
                    eftSB.append("\n");
                    eftSB.append("Total # Of EFT Records: ");
                    EDITBigDecimal numberOfEFTRecords = (EDITBigDecimal)eftValues.get("numberOfEFTRecords");
                    eftSB.append(numberOfEFTRecords.toString());
                    eftSB.append("\n");
                    eftSB.append("EFT Gross Dollars: ");
                    EDITBigDecimal eftGrossDollars = (EDITBigDecimal)(eftValues.get("eftGrossDollars"));
                    eftSB.append(Util.formatDecimal("###,###,###,##0.00", eftGrossDollars.round(2)));
                    eftSB.append("\n");
                    eftSB.append("EFT Check Amount Dollars: ");
                    EDITBigDecimal eftCheckAmtDollars = (EDITBigDecimal)(eftValues.get("eftCheckAmtDollars"));
                    eftSB.append(Util.formatDecimal("###,###,###,##0.00", eftCheckAmtDollars.round(2)));
                    eftSB.append("\n");
                    eftSB.append("EFT Federal Withholding: ");
                    EDITBigDecimal eftFedWithholding = (EDITBigDecimal)(eftValues.get("eftFedWithholding"));
                    eftSB.append(Util.formatDecimal("###,###,###,##0.00", eftFedWithholding.round(2)));
                    eftSB.append("\n");
                    eftSB.append("EFT State Withholding: ");
                    EDITBigDecimal eftStateWithholding = (EDITBigDecimal)(eftValues.get("eftStateWithholding"));
                    eftSB.append(Util.formatDecimal("###,###,###,##0.00", eftStateWithholding.round(2)));
                    eftInterfaceCmd.exportReconciliation(eftSB, "eftRecon");


                }

                catch (Exception e)
                {
                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_BANK_EXTRACTS).updateFailure();
                    System.out.println(e);
                    e.printStackTrace();
                }
            }

        }
        catch (Exception e)
        {

            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_BANK_EXTRACTS).updateFailure();
            System.out.println("[* Bank Process *] Failed For SuspensePK [" + suspenseVO.getSuspensePK() + "]");

            System.out.println(e);
            e.printStackTrace();
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_BANK_EXTRACTS).tagBatchStop();
        }
    }

    private void createCheckExtractRecord(SuspenseVO suspenseVO, EDITTrxHistoryVO editTrxHistoryVO, ClientDetailVO clientDetailVO) throws Exception
    {

        checkInterfaceCmd.setCheckInformation(editTrxHistoryVO, clientDetailVO, checkFileData, checkValues);

        try
        {
            checkInterfaceCmd.exec();
        }

        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
//            throw(e);
        }
    }

    private void createEFTExtractRecord(EDITTrxHistoryVO editTrxHistoryVO, ClientDetailVO clientDetailVO) throws Exception
    {

        eftInterfaceCmd.setEFTInformation(editTrxHistoryVO, clientDetailVO, eftFileData, eftValues);

        try
        {
            eftInterfaceCmd.exec();
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw(e);
        }
    }

    private void createValuationExtract(SegmentVO segmentVO) throws Exception {

        valuationInterfaceCmd.setValuationInformation(segmentVO,
                                                       valuationFileData);

        try {

            valuationInterfaceCmd.exec();
        }

        catch (Exception e) {

            System.out.println(e);
        }
    }

    public ClientDetailVO determineClient(SuspenseVO suspenseVO, EDITTrxVO editTrxVO, String disbAddressType) throws Exception
    {
        ClientSetupVO clientSetupVO = null;
        ContractSetupVO contractSetupVO = null;
        SegmentVO segmentVO = null;

        ClientDetailVO clientDetailVO = super.createClientDetailVO(suspenseVO);
        if (editTrxVO != null)
        {
            clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
            contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);

            if (contractSetupVO.getParentVOs() != null)
            {
                segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);
            }

            if (clientSetupVO.getContractClientFK() == 0)
            {
                if (clientDetailVO == null)
                {
                    clientDetailVO = super.getClientDetailVO(clientSetupVO.getClientRoleFK(), disbAddressType, segmentVO);
                }
            }
            else
            {
                // Reinsurer transactions will not have ClientRoleFK
                // Will have only TreatyFK, needs special processsing to get ClientDetail information.
                if (editTrxVO.getTransactionTypeCT().equals(EDITTrx.TRANSACTIONTYPECT_REINSURANCE_CHECK))
                {
                    TreatyVO treatyVO = new TreatyDAO().findBy_PK(clientSetupVO.getTreatyFK())[0];

                    ReinsurerVO reinsurerVO = new ReinsurerDAO().findBy_PK(treatyVO.getReinsurerFK())[0];

                    clientDetailVO = composeClientDetailVO(reinsurerVO.getClientDetailFK(), disbAddressType, segmentVO);
                }
                else
                {
                    if (clientSetupVO.getContractClientFK() == 0)
                    {
                        clientDetailVO = getClientDetailVO(clientSetupVO.getClientRoleFK(), disbAddressType, segmentVO);
                    }
                    else
                    {
                        ContractClientVO contractClientVO = contract.dm.dao.DAOFactory.getContractClientDAO().findByContractClientPK(clientSetupVO.getContractClientFK(), false, new ArrayList())[0];

                        clientDetailVO = getClientDetailVO(contractClientVO.getClientRoleFK(), disbAddressType, segmentVO);
                    }
                }
            }
        }

        return clientDetailVO;
    }
}
