/*
 * User: gfrosti
 * Date: Aug 12, 2003
 * Time: 12:56:56 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.financial.group.strategy;

import contract.Investment;
import contract.InvestmentAllocation;
import contract.Segment;

import contract.dm.dao.FastDAO;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.exceptions.EDITDeleteException;
import edit.common.exceptions.EDITEventException;
import edit.common.vo.BucketVO;
import edit.common.vo.ClientRoleVO;
import edit.common.vo.ClientSetupVO;
import edit.common.vo.ContractClientAllocationVO;
import edit.common.vo.ContractClientVO;
import edit.common.vo.ContractSetupVO;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.FilteredFundVO;
import edit.common.vo.FundVO;
import edit.common.vo.GroupSetupVO;
import edit.common.vo.InvestmentAllocationOverrideVO;
import edit.common.vo.InvestmentAllocationVO;
import edit.common.vo.InvestmentVO;
import edit.common.vo.PreferenceVO;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.ScheduledEventVO;
import edit.common.vo.SegmentVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.SessionHelper;
import engine.Area;
import engine.AreaValue;
import engine.FilteredFund;
import engine.Fund;
import event.EDITTrx;
import event.InvestmentAllocationOverride;
import event.common.Constants;
import event.dm.dao.DAOFactory;
import event.financial.client.trx.ClientTrx;
import event.financial.contract.trx.ContractEvent;
import fission.utility.DateTimeUtil;
import fission.utility.Util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import role.ClientRole;


public class SaveGroup
{
    private static final String YES = "Y";
    private static final String NO = "N";

    private GroupSetupVO groupSetupVO;
    private EDITTrxVO inEditTrxVO;
    private EDITDate effectiveDate;
    private String transactionTypeCT;
    private String processName;
    private String optionCode;

    public static final String PROCESSNAME_COMMIT = "Commit";

    public SaveGroup(GroupSetupVO groupSetupVO, EDITTrxVO inEditTrxVO,
                     String processName, String optionCode)
    {
        this.groupSetupVO = groupSetupVO;
        this.inEditTrxVO = inEditTrxVO;
        this.transactionTypeCT = inEditTrxVO.getTransactionTypeCT();
        this.processName = processName;
        this.optionCode = optionCode;

        if (inEditTrxVO.getEffectiveDate() == null)
        {
            this.effectiveDate = null;
        }
        else
        {
            this.effectiveDate = new EDITDate(inEditTrxVO.getEffectiveDate());
    }
    }

    public SaveGroup(GroupSetupVO groupSetupVO)
    {
        this.groupSetupVO = groupSetupVO;
    }

    public void build() throws EDITEventException
    {
        ContractSetupVO[] contractSetupVO = groupSetupVO.getContractSetupVO();

        for (int i = 0; i < contractSetupVO.length; i++)
        {
            if (contractSetupVO[i].getClientSetupVOCount() == 0) // if there are no Clients, get Active Clients.
            {
                long[] activeClients = getActiveClients(contractSetupVO[i].getSegmentFK(), null);
                if (activeClients == null)
                {
                    throw new EDITEventException("Proper client role not found for the transaction");
                }

                for (int j = 0; j < activeClients.length; j++)
                {
                    ClientSetupVO clientSetupVO = new ClientSetupVO();
                    clientSetupVO.setContractClientFK(activeClients[j]);

                    ContractClientVO contractClientVO = contract.dm.dao.DAOFactory.getContractClientDAO().findByContractClientPK(activeClients[j], false, new ArrayList())[0];
                    clientSetupVO.setClientRoleFK(contractClientVO.getClientRoleFK());

                    contractSetupVO[i].addClientSetupVO(clientSetupVO);
                }
            }

            ClientSetupVO[] clientSetupVO = getClientSetupVO();

            // Build the EDITTrx for any clientSetupVO which does NOT yet have an EDITTrx
            for (int j = 0; j < clientSetupVO.length; j++)
            {
                if (clientSetupVO[j].getEDITTrxVOCount() == 0) // We only want to build EDITTrx for ClientSetups without an EDITTrx
                {
                    EDITTrxVO currentEDITTrxVO = buildEDITTrxVO(contractSetupVO[i], clientSetupVO[j], null);

                    checkSequenceNumber(currentEDITTrxVO, clientSetupVO);

                    clientSetupVO[j].addEDITTrxVO(currentEDITTrxVO);
                }
                else
                {
                    EDITTrxVO[] editTrxVOs = clientSetupVO[j].getEDITTrxVO();
                    for (int k = 0; k < editTrxVOs.length; k++)
                    {
                        setSequenceNumber(contractSetupVO[i].getSegmentFK(), editTrxVOs[k], null);

                        if (j > 0)
                        {
                            editTrxVOs[k].setSequenceNumber((editTrxVOs[k].getSequenceNumber() + 1));
                        }

                        ClientTrx clientTrx = new ClientTrx(editTrxVOs[k]);
                        if (clientTrx.isCommissionableEvent(editTrxVOs[k].getTransactionTypeCT()))
                        {
                            editTrxVOs[k].setCommissionStatus("P");
                        }
                        else
                        {
                            editTrxVOs[k].setCommissionStatus("N");
                    }
                }
            }
            }

            String transactionType = clientSetupVO[0].getEDITTrxVO(0).getTransactionTypeCT();
            EDITTrxVO editTrxVO = clientSetupVO[0].getEDITTrxVO(0);

            InvestmentAllocationOverrideVO[] investmentAllocOvrdVO = contractSetupVO[i].getInvestmentAllocationOverrideVO();
            long segmentFK = contractSetupVO[i].getSegmentFK();
            if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_PREMIUM) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_FREE_LOOK_TRANSFER) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_TRANSFER) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_WITHDRAWAL) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT))
            {
                if (investmentAllocOvrdVO == null || investmentAllocOvrdVO.length == 0)
                {
                    //create overrides for the loan when it is a max loan otherwise prorata and do nothing
                    if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN))
                    {
                        if (isMaxLoan(editTrxVO))
                        {
                            createIAOverrides(segmentFK, contractSetupVO[i], transactionType,  true);
                        }
                    }
                    else
                    {
                        createIAOverrides(segmentFK, contractSetupVO[i], transactionType, false);
                    }
                }
                else
                {
                    updateIAOverrides(segmentFK, contractSetupVO[i], transactionType, investmentAllocOvrdVO);
                }
            }
            else if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SERIES_TRANSFER))
            {
                updateSTFOverrides(contractSetupVO[i], investmentAllocOvrdVO);
            }
        }
    }

    /**
     * Determines whether the loan EDITTrx is a max loan or not
     *
     * @param editTrxVO         EDITTrx of loan transactionType
     *
     * @return  true if it is a max loan, false otherwise
     */
    private boolean isMaxLoan(EDITTrxVO editTrxVO)
    {
        EDITBigDecimal trxAmount = new EDITBigDecimal(editTrxVO.getTrxAmount());
        EDITBigDecimal trxPercent = new EDITBigDecimal(editTrxVO.getTrxPercent());

        if (trxAmount.isEQ("0") && trxPercent.isEQ("0"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Build
     * @param crud
     * @throws EDITEventException
     */
    // this method is used when trying to build group with in a database transaction.
    public void build(CRUD crud) throws EDITEventException
    {
        ContractSetupVO[] contractSetupVO = groupSetupVO.getContractSetupVO();

        for (int i = 0; i < contractSetupVO.length; i++)
        {
            if (contractSetupVO[i].getClientSetupVOCount() == 0) // if there are no Clients, get Active Clients.
            {
                long[] activeClients = getActiveClients(contractSetupVO[i].getSegmentFK(), crud);
                if (activeClients == null)
                {
                    throw new EDITEventException("Proper client role not found for the transaction");
                }

                for (int j = 0; j < activeClients.length; j++)
                {
                    ClientSetupVO clientSetupVO = new ClientSetupVO();
                    clientSetupVO.setContractClientFK(activeClients[j]);

                    ContractClientVO contractClientVO = contract.dm.dao.DAOFactory.getContractClientDAO().findByContractClientPK(activeClients[j], false, new ArrayList())[0];
                    clientSetupVO.setClientRoleFK(contractClientVO.getClientRoleFK());

                    contractSetupVO[i].addClientSetupVO(clientSetupVO);
                }
            }

            ClientSetupVO[] clientSetupVO = getClientSetupVO();

            // Build the EDITTrx for any clientSetupVO which does NOT yet have an EDITTrx
            for (int j = 0; j < clientSetupVO.length; j++)
            {
                if (clientSetupVO[j].getEDITTrxVOCount() == 0) // We only want to build EDITTrx for ClientSetups without an EDITTrx
                {
                    EDITTrxVO currentEDITTrxVO = buildEDITTrxVO(contractSetupVO[i], clientSetupVO[j], crud);

                    checkSequenceNumber(currentEDITTrxVO, clientSetupVO);

                    clientSetupVO[j].addEDITTrxVO(currentEDITTrxVO);
                }
                else
                {
                    EDITTrxVO[] editTrxVOs = clientSetupVO[j].getEDITTrxVO();
                    for (int k = 0; k < editTrxVOs.length; k++)
                    {
                        setSequenceNumber(contractSetupVO[i].getSegmentFK(), editTrxVOs[k], crud);
                    }
                }
            }

            String transactionType = clientSetupVO[0].getEDITTrxVO(0).getTransactionTypeCT();
            EDITTrxVO editTrxVO = clientSetupVO[0].getEDITTrxVO(0);

            InvestmentAllocationOverrideVO[] investmentAllocOvrdVO = contractSetupVO[i].getInvestmentAllocationOverrideVO();
            long segmentFK = contractSetupVO[i].getSegmentFK();
           
            if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_PREMIUM) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_FREE_LOOK_TRANSFER) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_TRANSFER) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_WITHDRAWAL) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN) ||
                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT))
            {
                if (investmentAllocOvrdVO == null || investmentAllocOvrdVO.length == 0)
                {
                    //create overrides for the loan when it is a max loan otherwise update
                    if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN))
                    {
                        if (isMaxLoan(editTrxVO))
                        {
                            createIAOverrides(segmentFK, contractSetupVO[i], transactionType,  true);
                        }
                    }
                    else
                    {
                        createIAOverrides(segmentFK, contractSetupVO[i], transactionType,  false);
                    }
                }
                else
                {
                    updateIAOverrides(segmentFK, contractSetupVO[i], transactionType, investmentAllocOvrdVO);
                }
            }
            else if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SERIES_TRANSFER))
            {
                updateSTFOverrides(contractSetupVO[i], investmentAllocOvrdVO);
            }
        }
    }

    public ClientTrx[] save(CRUD crud) throws EDITDeleteException
    {
        // Save Blindly
        crud.createOrUpdateVOInDBRecursively(groupSetupVO, false);

        // Now, get the EDITTrx and make sure the Correspondence Logic is invoked.

        ContractSetupVO[] contractSetupVO = groupSetupVO.getContractSetupVO();
        List clientTrxArray = new ArrayList();

        for (int i = 0; i < contractSetupVO.length; i++)
        {
            ClientSetupVO[] clientSetupVO = contractSetupVO[i].getClientSetupVO();

            for (int j = 0; j < clientSetupVO.length; j++)
            {
                EDITTrxVO[] editTrxVO = clientSetupVO[j].getEDITTrxVO();

                for (int k = 0; k < editTrxVO.length; k++)
                {
                    ClientTrx clientTrx = new ClientTrx(editTrxVO[k]);

                    clientTrx.save(crud);

                    clientTrxArray.add(clientTrx);
                }
            }
        }

        return (ClientTrx[])clientTrxArray.toArray(new ClientTrx[clientTrxArray.size()]);
    }

    public ClientTrx[] save(CRUD crud, int executionMode, String cycleDate) throws EDITDeleteException
    {
        // Save Blindly
        crud.createOrUpdateVOInDBRecursively(groupSetupVO, false);

        // Now, get the EDITTrx and make sure the Correspondence Logic is invoked.

        ContractSetupVO[] contractSetupVO = groupSetupVO.getContractSetupVO();
        List clientTrxArray = new ArrayList();

        for (int i = 0; i < contractSetupVO.length; i++)
        {
            ClientSetupVO[] clientSetupVO = contractSetupVO[i].getClientSetupVO();

            for (int j = 0; j < clientSetupVO.length; j++)
            {
                EDITTrxVO[] editTrxVO = clientSetupVO[j].getEDITTrxVO();

                for (int k = 0; k < editTrxVO.length; k++)
                {
                    ClientTrx clientTrx = new ClientTrx(editTrxVO[k]);

                    clientTrx.setCycleDate(cycleDate);

                    clientTrx.setExecutionMode(executionMode);

                    clientTrx.save(crud);

                    clientTrxArray.add(clientTrx);
                }
            }
        }

        return (ClientTrx[])clientTrxArray.toArray(new ClientTrx[clientTrxArray.size()]);
    }

    public void save(int notificationDays, String notificationDaysType)
    {
        CRUD crud = null;

        crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

        try
        {
            // Save Blindly
            crud.createOrUpdateVOInDBRecursively(groupSetupVO, false);

            // Now, get the EDITTrx and make sure the Correspondence Logic is invoked.

            ContractSetupVO[] contractSetupVO = groupSetupVO.getContractSetupVO();

            for (int i = 0; i < contractSetupVO.length; i++)
            {
                if (this.transactionTypeCT.equalsIgnoreCase("PY"))
                {
                    resetHeldTransactions(contractSetupVO[i].getSegmentFK());
                }

                ClientSetupVO[] clientSetupVO = contractSetupVO[i].getClientSetupVO();

                for (int j = 0; j < clientSetupVO.length; j++)
                {
                    EDITTrxVO[] editTrxVO = clientSetupVO[j].getEDITTrxVO();

                    for (int k = 0; k < editTrxVO.length; k++)
                    {
                        ClientTrx clientTrx = new ClientTrx(editTrxVO[k]);

                        clientTrx.save(crud, notificationDays, notificationDaysType);
                    }
                }
            }
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }
    }

    public ClientTrx[]  save()
    {
        CRUD crud = null;

        crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);
        ClientTrx[] clientTrxArray = null;
        try
        {
            clientTrxArray = save(crud);

            for (int i = 0; i < clientTrxArray.length; i++)
            {
                if (clientTrxArray[i].getEDITTrxVO().getTransactionTypeCT().equalsIgnoreCase("PY"))
                {
                    resetHeldTransactions(clientTrxArray[i].getClientSetup().getContractSetup().getSegmentFK().longValue());
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        
            e.printStackTrace();
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return clientTrxArray;
    }

    public GroupSetupVO getGroupSetupVO()
    {
        return groupSetupVO;
    }

    public ContractSetupVO[] getContractSetupVO()
    {
        return groupSetupVO.getContractSetupVO();
    }

    private ClientSetupVO[] getClientSetupVO()
    {
        List clientSetupVOs = new ArrayList();

        ContractSetupVO[] contractSetupVO = groupSetupVO.getContractSetupVO();

        for (int i = 0; i < contractSetupVO.length; i++)
        {
            ClientSetupVO[] clientSetupVO = contractSetupVO[i].getClientSetupVO();

            for (int j = 0; j < clientSetupVO.length; j++)
            {
                clientSetupVOs.add(clientSetupVO[j]);
            }
        }

        return (ClientSetupVO[]) clientSetupVOs.toArray(new ClientSetupVO[clientSetupVOs.size()]);
    }

    private long[] getActiveClients(long segmentPK, CRUD crud)
    {
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        String roleTypeCT = null;
        long[] contractClientPKs = null;

        Segment segment = null;
        if (crud == null)
        {
            segment = Segment.findByPK(segmentPK);
        }
        else
        {
            SegmentVO segmentVO = new contract.dm.dao.FastDAO().findSegmentBySegmentPK(segmentPK, crud);
            segment = new Segment(segmentVO);
        }

        if (transactionTypeCT.equalsIgnoreCase("IS") &&
            segment.getSegmentNameCT().equalsIgnoreCase(Segment.SEGMENTNAMECT_PAYOUT))
        {
            roleTypeCT = ClientRole.ROLETYPECT_OWNER;
        }
        else if (transactionTypeCT.equalsIgnoreCase("IS") ||
            transactionTypeCT.equalsIgnoreCase("MD") ||
                 transactionTypeCT.equalsIgnoreCase("FD") ||
                 transactionTypeCT.equalsIgnoreCase("SB") ||
                 transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_POLICYYEAREND) ||
                 transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE) ||
                 transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_COMPLEXCHANGE) ||
                 transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_BC_DED_AMT))
        {
            roleTypeCT = Constants.RoleType.INSURED;
        }
        else if (transactionTypeCT.equals("PO") ||
                transactionTypeCT.equals("FS") ||
                transactionTypeCT.equals(EDITTrx.TRANSACTIONTYPECT_SURRENDER_OVERLOAN) ||
                transactionTypeCT.equals("WI") ||
                transactionTypeCT.equals("DE") ||
                transactionTypeCT.equals("LS") ||
                transactionTypeCT.equals("SW"))
        {
            roleTypeCT = Constants.RoleType.PAYEE;
        }
        else if (transactionTypeCT.equals("BI") ||
                transactionTypeCT.equals("LP") ||
                transactionTypeCT.equals("LA") ||
                transactionTypeCT.equals("PY"))
        {
            roleTypeCT = Constants.RoleType.PAYOR;
        }
        else
        {
            roleTypeCT = Constants.RoleType.OWNER;
        }

        contractClientPKs = contractLookup.findContractClientPKsBySegmentPKAndRoleType(segmentPK, roleTypeCT);

        if ((contractClientPKs == null) && !(roleTypeCT.equals(Constants.RoleType.OWNER))) // Find the owner if all else fails.
        {
            roleTypeCT = Constants.RoleType.OWNER;

            contractClientPKs = contractLookup.findContractClientPKsBySegmentPKAndRoleType(segmentPK, roleTypeCT); //ECK - here for ownerClient code
        }

        if (contractClientPKs == null && segment.getSegmentFK() != null)
        {
            contractClientPKs = contractLookup.findContractClientPKsBySegmentPKAndRoleType(segment.getSegmentFK().longValue(), roleTypeCT);

            if ((contractClientPKs == null) && !(roleTypeCT.equals(Constants.RoleType.OWNER))) // Find the owner if all else fails.
            {
                roleTypeCT = Constants.RoleType.OWNER;

                contractClientPKs = contractLookup.findContractClientPKsBySegmentPKAndRoleType(segment.getSegmentFK().longValue(), roleTypeCT);
            }
        }

        return contractClientPKs;
    }

    private EDITTrxVO buildEDITTrxVO(ContractSetupVO contractSetupVO, ClientSetupVO clientSetupVO, CRUD crud)
    {
        EDITDate dueDate = null;

        EDITTrxVO editTrxVO = null;

        // Currently, this SaveGroup does not properly handle the saving of multiple EDITTrx (e.g. for group processing). With the introduction of the
        // LapsePending, and Lapse, this has to be addressed. This is not an elegant, long-term solution.

        if (transactionTypeCT.equals("LP") || transactionTypeCT.equals("LA"))
        {
            ContractEvent contractEvent = new ContractEvent();

            long contractClientPK = clientSetupVO.getContractClientFK();
            long segmentPK = contractSetupVO.getSegmentFK();
            EDITDate effectiveDate = null;
            
            if(this.inEditTrxVO.getEffectiveDate()!= null){
                effectiveDate = new EDITDate(this.inEditTrxVO.getEffectiveDate());
            }            
             
            if(effectiveDate == null)
            {
                    if (transactionTypeCT.equals("LP"))
                    {
                        effectiveDate = getLapsePendingTrxEffectiveDate(segmentPK);
                    }
                    else if (transactionTypeCT.equals("LA"))
                    {
                        effectiveDate = getLapseTrxEffectiveDate(segmentPK);
                    }
            }
            int taxYear = new EDITDate().getYear();

            String operator = "System";

            editTrxVO = contractEvent.buildDefaultEDITTrxVO(transactionTypeCT, effectiveDate, taxYear, operator);

            this.effectiveDate = effectiveDate;
            
            editTrxVO.setOriginatingTrxFK(inEditTrxVO.getOriginatingTrxFK());

            setSequenceNumber(segmentPK, editTrxVO, crud);
        }
        else
        {
            editTrxVO = new EDITTrxVO();

            // double policyAmount = groupSetupVO.getContractSetupVO()[0].getPolicyAmount();
            EDITBigDecimal policyAmount = new EDITBigDecimal(groupSetupVO.getContractSetupVO()[0].getPolicyAmount());
            ContractClientAllocationVO[] contractClientAllocationVO = null;

            long contractClientFK = clientSetupVO.getContractClientFK();

            if (clientSetupVO.getContractClientAllocationOvrdVOCount() != 0)
            {
                long contractClientAllocationPK = clientSetupVO.getContractClientAllocationOvrdVO()[0].getContractClientAllocationFK();

                contractClientAllocationVO = contract.dm.dao.DAOFactory.getContractClientAllocationDAO().findByContractClientAllocationPK(contractClientAllocationPK, false, new ArrayList());
            }
            else
            {
                contractClientAllocationVO = contract.dm.dao.DAOFactory.getContractClientAllocationDAO().findByContractClientPKAndOverrideStatus(contractClientFK, "P", false, new ArrayList());
            }

            EDITDate editDate = null;

            ScheduledEventVO[] scheduledEventVO = groupSetupVO.getScheduledEventVO();

            if (scheduledEventVO != null && scheduledEventVO.length != 0)
            {
                dueDate = new EDITDate(scheduledEventVO[0].getStartDate());

                if (transactionTypeCT.equals("PO") && (!processName.equals("Commit")))
                {
                    editDate = calcScheduledEventDates(contractClientFK, dueDate);
                    effectiveDate = editDate;
                }
            }

            if (inEditTrxVO.getEDITTrxHistoryVOCount() > 0)
            {
                editTrxVO.setEDITTrxHistoryVO(inEditTrxVO.getEDITTrxHistoryVO());
            }
            if (inEditTrxVO.getEDITTrxCorrespondenceVOCount() > 0)
            {
                editTrxVO.setEDITTrxCorrespondenceVO(inEditTrxVO.getEDITTrxCorrespondenceVO());
            }

            editTrxVO.setEDITTrxPK(inEditTrxVO.getEDITTrxPK());

            long segmentPK = groupSetupVO.getContractSetupVO()[0].getSegmentFK();

            //set the fields not set in ContractEvent
            editTrxVO.setClientSetupFK(clientSetupVO.getClientSetupPK());
            editTrxVO.setEffectiveDate(inEditTrxVO.getEffectiveDate());

            String status = inEditTrxVO.getStatus();
            if (status == null)
            {
                status = "N";
            }
            editTrxVO.setStatus(status);

            String pendingStatus = inEditTrxVO.getPendingStatus();
            if (pendingStatus == null)
            {
                pendingStatus = "P";
            }
            editTrxVO.setPendingStatus(pendingStatus);
            editTrxVO.setTaxYear(inEditTrxVO.getTaxYear());

            editTrxVO.setDueDate(inEditTrxVO.getDueDate());
            editTrxVO.setTransactionTypeCT(inEditTrxVO.getTransactionTypeCT());
            editTrxVO.setTrxIsRescheduledInd("N");
            editTrxVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());

            ClientTrx clientTrx = new ClientTrx(editTrxVO);
            if (clientTrx.isCommissionableEvent(editTrxVO.getTransactionTypeCT()))
            {
                editTrxVO.setCommissionStatus("P");
            }
            else
            {
                editTrxVO.setCommissionStatus("N");
            }

            String lookBackInd = inEditTrxVO.getLookBackInd();
            if (lookBackInd == null)
            {
                lookBackInd = "N";
                editTrxVO.setLookBackInd("N");
            }
            editTrxVO.setLookBackInd(lookBackInd);

            editTrxVO.setNoCorrespondenceInd(inEditTrxVO.getNoCorrespondenceInd());
            editTrxVO.setNoAccountingInd(inEditTrxVO.getNoAccountingInd());
            editTrxVO.setNoCommissionInd(inEditTrxVO.getNoCommissionInd());
            editTrxVO.setZeroLoadInd(inEditTrxVO.getZeroLoadInd());
            editTrxVO.setNoCheckEFT(inEditTrxVO.getNoCheckEFT());
            editTrxVO.setOperator(inEditTrxVO.getOperator());
            editTrxVO.setAccountingPeriod(inEditTrxVO.getAccountingPeriod());
            editTrxVO.setOriginalAccountingPeriod(inEditTrxVO.getOriginalAccountingPeriod());
            editTrxVO.setOriginatingTrxFK(inEditTrxVO.getOriginatingTrxFK());
            editTrxVO.setAuthorizedSignatureCT(inEditTrxVO.getAuthorizedSignatureCT());
            editTrxVO.setPremiumDueCreatedIndicator(inEditTrxVO.getPremiumDueCreatedIndicator());
            editTrxVO.setBillAmtEditOverrideInd(inEditTrxVO.getBillAmtEditOverrideInd());
            editTrxVO.setSelectedRiderPK(inEditTrxVO.getSelectedRiderPK());

            setSequenceNumber(segmentPK, editTrxVO, crud);


            // double trxAmount = 0;
            EDITBigDecimal trxAmount = new EDITBigDecimal();

            if (contractClientAllocationVO != null)
            {
                // trxAmount = contractClientAllocationVO[0].getAllocationPercent() * policyAmount;
                EDITBigDecimal percent = new EDITBigDecimal(contractClientAllocationVO[0].getAllocationPercent());
                if (!percent.isEQ("0"))
                {
                    trxAmount = policyAmount.multiplyEditBigDecimal(contractClientAllocationVO[0].getAllocationPercent());
                }
                else
                {
                    trxAmount = policyAmount;
                }
            }
            else
            {
                trxAmount = policyAmount;
            }

            editTrxVO.setTrxAmount(trxAmount.getBigDecimal());

            editTrxVO.setTrxPercent(inEditTrxVO.getTrxPercent());
        }

        return editTrxVO;
    }

    /**
     * This method can be used with in a database transaction.
     * @param segmentPK
     * @param editTrxVO
     * @param crud
     */
    private void setSequenceNumber(long segmentPK, EDITTrxVO editTrxVO, CRUD crud)
    {
        // get edit trx for date and trxtype - if any history status, sequence is upped by 1
        // pending also need to be upped
    	
    	// MI/MV trx are always set to sequence of 1
    	if (editTrxVO != null && (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MONTHLIVERSARY) ||
    			editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MONTHLYINTEREST))) {
    		
            editTrxVO.setSequenceNumber(1);

    	} else {

	        EDITTrxVO[] editTrxVOs = new edit.common.vo.EDITTrxVO[0];
	        if (crud == null)
	        {
	            editTrxVOs = DAOFactory.getEDITTrxDAO().findByEffectiveDateAndTrxTypeCT(effectiveDate.getFormattedDate(), transactionTypeCT, segmentPK);
	        }
	        else
	        {
	            editTrxVOs = new event.dm.dao.FastDAO().findEDITTrxByEffectiveDateAndTrxTypeCT(effectiveDate.getFormattedDate(), transactionTypeCT, segmentPK, crud);
	        }
	
	        int sequenceNumber = 0;
	        if (editTrxVOs != null)
	        {
	            for (int i = 0; i < editTrxVOs.length; i++)
	            {
	                if (sequenceNumber <= editTrxVOs[i].getSequenceNumber())
	                    sequenceNumber = editTrxVOs[i].getSequenceNumber();
	            }
	
	            if (sequenceNumber == 0)
	            {
	                sequenceNumber = 1;
	            }
	            editTrxVO.setSequenceNumber(sequenceNumber + 1);
	        }
	
	        else
	        {
	            editTrxVO.setSequenceNumber(1);
	        }
    	}
    }

    public EDITDate calcScheduledEventDates(long contractClientFK, EDITDate startDate)
    {
        SegmentVO[] segmentVO = null;

        if (!processName.equals("Commit"))
        {
            ContractSetupVO contractSetupVO = groupSetupVO.getContractSetupVO(0);
            long segmentKey = contractSetupVO.getSegmentFK();

            segmentVO = contract.dm.dao.DAOFactory.getSegmentDAO().findBySegmentPK(segmentKey, false, new ArrayList());

            effectiveDate = new EDITDate(segmentVO[0].getEffectiveDate());
            optionCode = segmentVO[0].getOptionCodeCT();
        }

        int leadDays = getLeadDays(segmentVO[0], contractClientFK);

        EDITDate ceDate = startDate.subtractDays(leadDays);

        // after commit reschedule of payouts executes this code to get an adjusted effective date
        return ceDate;
    }

    public int getLeadDays(SegmentVO segmentVO, long contractClientFK)
    {
        long productStructurePK = segmentVO.getProductStructureFK();
        String areaCT = segmentVO.getIssueStateCT();
        String qualifierCT = Util.initString(segmentVO.getQualNonQualCT(), "*");
        String grouping = "PAYOUT";

        EDITDate effectiveDate = new EDITDate(segmentVO.getEffectiveDate());
        String field = null;
            long clientRoleFK = contract.dm.dao.DAOFactory.getContractClientDAO().findByContractClientPK(contractClientFK, false, new ArrayList())[0].getClientRoleFK();
            ClientRoleVO[] clientRoleVO = role.dm.dao.DAOFactory.getClientRoleDAO().findByClientRolePK(clientRoleFK, false, new ArrayList());

            long preferenceFK = clientRoleVO[0].getPreferenceFK();
            PreferenceVO[] preferenceVO = client.dm.dao.DAOFactory.getPreferenceDAO().findByPreferencePK(preferenceFK, false, new ArrayList());

        if (preferenceVO == null)
        {
            //  If the Preference does not exist on the ClientRole, get it from the ClientDetail
            //  NOTE: when discovered this error, no one seemed to know why the PreferenceFK (as well as the BankAccountingInformationFK, and
            //  TaxProfileFK) exist on the ClientRole when they are already on the ClientDetail.  It apparently isn't for override capability
            //  since override info is on the Preference table and the code is not written for overrides.  Adding the following line does
            //  give some override status ability but it is not intentional other than to get around a null ptr error.  The proper solution
            //  may be to go to the ClientDetail all the time and remove the fields from the ClientRole.  But no one is willing to commit to
            //  that right now.
            preferenceVO = client.dm.dao.DAOFactory.getPreferenceDAO().findPrimaryByClientDetailPK(clientRoleVO[0].getClientDetailFK(), false, null);
        }

        String disbSource = preferenceVO[0].getDisbursementSourceCT();

        if (disbSource != null && disbSource.equalsIgnoreCase("EFT"))
        {
            field = "LEADDAYSEFT";
        }
        else
            {
            field = "LEADDAYSCHECK";
        }

        int leadDays = 0;

        Area area = new Area(productStructurePK, areaCT, grouping, effectiveDate, qualifierCT);
        if (area != null)
            {
            AreaValue areaValue = area.getAreaValue(field);

            if (areaValue != null)
            {
                leadDays = Integer.parseInt(areaValue.getAreaValue());
            }
        }

        return leadDays;
    }

    /**
     * Defaults the SequenceNumber to 1 unless 2 or more transactions of the same TransactionTypeCT are entered on the
     * same day, upon which the SequenceNumber is incremented for the second transaction and so on.
     * @param currentEDITTrxVO
     * @param clientSetupVO
     */
    private void checkSequenceNumber(EDITTrxVO currentEDITTrxVO, ClientSetupVO[] clientSetupVO)
    {
        for (int i = 0; i < clientSetupVO.length; i++)
        {
            EDITTrxVO[] editTrxVOS = clientSetupVO[i].getEDITTrxVO();

            for (int j = 0; j < editTrxVOS.length; j++)
            {
                if (editTrxVOS[j].getEffectiveDate().equalsIgnoreCase(currentEDITTrxVO.getEffectiveDate()) &&
                    editTrxVOS[j].getTransactionTypeCT().equalsIgnoreCase(currentEDITTrxVO.getTransactionTypeCT()))
                {
                    currentEDITTrxVO.setSequenceNumber(editTrxVOS[j].getSequenceNumber() + 1);
                }
            }
        }
    }

    /**
     * Gets the EffectiveDate from the Life.LapsePendingDate.
     * @param segmentPK
     */
    private EDITDate getLapsePendingTrxEffectiveDate(long segmentPK)
    {
        EDITDate effectiveDate = null;

        effectiveDate = new EDITDate(new FastDAO().findLapsePendingDateBySegmentPK(segmentPK));

        return effectiveDate;
    }

    /**
     * Gets the EffectiveDate from the Life.LapseDate.
     * @param segmentPK
     */
    private EDITDate getLapseTrxEffectiveDate(long segmentPK)
    {
        EDITDate effectiveDate = null;

        effectiveDate = new EDITDate(new FastDAO().findLapseDateBySegmentPK(segmentPK));

        return effectiveDate;
    }

    /**
     * Creates the appropriate InvestmentAllocationOverride records for Hedge Fund Processing
     * @param segmentFK
     * @param contractSetupVO
     * @param transactionType
     */
    private void createIAOverrides(long segmentFK, ContractSetupVO contractSetupVO, String transactionType, boolean isMaxLoan)
    {
        String hfStatus = "A";
        String hfiaIndicator = "N";
        String toFromStatus = "";

        contract.business.Lookup contractLookup = new contract.component.LookupComponent();
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(InvestmentVO.class);
        voInclusionList.add(InvestmentAllocationVO.class);
        voInclusionList.add(BucketVO.class);

        try
        {
            SegmentVO segmentVO = contractLookup.composeSegmentVO(segmentFK, voInclusionList);
            long productStructureFK = segmentVO.getProductStructureFK();

            InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();
            boolean hedgeFundFound = false;

            List hedgeFundList = getHedgeFundList(investmentVOs, transactionType, isMaxLoan);

            if (! hedgeFundList.isEmpty())
            {
                hedgeFundFound = true;
            }

            if (hedgeFundFound)
            {
                // User did not create overrides.  Set the indicator to no for script use
                contractSetupVO.setUserInvestmentOverrideInd(SaveGroup.NO);

                for (int i = 0; i < investmentVOs.length; i++)
                {
                    Investment investment = new Investment(investmentVOs[i]);
                    long filteredFundFK = investment.getFilteredFundFK();
                    FilteredFund filteredFund = FilteredFund.findByPK(new Long(filteredFundFK));
                    Fund fund = filteredFund.getFund();
                    String fundTypeCode = fund.getTypeCodeCT();
                    String loanQualifier = Util.initString(fund.getLoanQualifierCT(), "");

                    if (!transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT) ||
                        (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT) &&
                        !fundTypeCode.equalsIgnoreCase("System")) ||
                        (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT) &&
                         fundTypeCode.equalsIgnoreCase("System") && !loanQualifier.equals("")))
                    {
                        long investmentPK = investment.getPK();

                        InvestmentAllocation investmentAllocation = new InvestmentAllocation(investment.getInvestmentAllocationVOs());

                        long investmentAllocationPK = investmentAllocation.getInvestmentAllocationPKForPrimary(transactionType);

                        if (investmentAllocationPK == 0 && isMaxLoan)
                        {
                            investmentAllocationPK = investmentVOs[i].getInvestmentAllocationVO(0).getInvestmentAllocationPK();
                        }

                        if (investmentAllocationPK != 0)
                        {
                            hfStatus = "A";

                            //Hedge Funds will not be immediately affected, so HFStatus gets set to "N"
                            //All Funds immediately affected by the transaction have HFStatus set to "A"
                            if (hedgeFundList.contains(investmentVOs[i].getFilteredFundFK() + ""))
                            {
                                hfStatus = "N";

                                if (transactionType.equalsIgnoreCase("PY") ||
                                    transactionType.equalsIgnoreCase("FT") ||
                                    transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT))
                                {
                                    ProductStructureVO[] productStructureVO = engineLookup.findProductStructureVOByPK(productStructureFK, false, new ArrayList());
                                    long hedgeFundInterimAccountFK = productStructureVO[0].getHedgeFundInterimAccountFK(); //Points to Fund Table

                                    FilteredFundVO[] filteredFundVOs = engineLookup.composeFilteredFundVOByCoStructurePK_And_FundPK(productStructureFK, hedgeFundInterimAccountFK, new ArrayList());
                                    if (filteredFundVOs != null && filteredFundVOs.length > 0)
                                    {
                                        InvestmentAllocationOverrideVO investmentAllocationOverrideVO =
                                                setupHedgeFundInterimAccount(filteredFundVOs[0],
                                                                             new EDITBigDecimal(investmentAllocation.getAllocationByPK(investmentAllocationPK)),
                                                                             new EDITBigDecimal(),
                                                                             segmentFK, contractSetupVO.getContractSetupPK());

                                        investmentAllocationOverrideVO.setHedgeFundInvestmentFK(investmentPK);

                                        contractSetupVO.addInvestmentAllocationOverrideVO(investmentAllocationOverrideVO);
                                    }
                                }
                            }

                            if (transactionType.equalsIgnoreCase("PY") ||
                                transactionType.equalsIgnoreCase("FT") ||
                                transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT))
                            {
                                toFromStatus = "T";
                            }
                            else if (transactionType.equalsIgnoreCase("WI") ||
                                     transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN))
                            {
                                toFromStatus = "F";
                            }

                            if (shouldCreateIAOverride(investmentVOs[i], transactionType))
                            {
                                InvestmentAllocationOverride investmentAllocationOverride = new InvestmentAllocationOverride(contractSetupVO.getContractSetupPK(),
                                                                                                                          investmentPK,
                                                                                                                           investmentAllocationPK,
                                                                                                                            toFromStatus,
                                                                                                                             hfStatus,
                                                                                                                              hfiaIndicator,
                                                                                                                               "N");

                                contractSetupVO.addInvestmentAllocationOverrideVO(investmentAllocationOverride.getVO());
                            }
                        }
                    }
                }
                
                // Create override allocations for From ('F')
                if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_FREE_LOOK_TRANSFER))
                {
                    createAllocationOverridesForOverrideAllocationsForFreelookTransfer(segmentFK, contractSetupVO);
                }
            }
            // when contract does not have any hedge fund
            else
            {
                if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_FREE_LOOK_TRANSFER))
                {
                    createAllocationOverridesForPrimaryAllocationsForFreelookTransfer(segmentFK, contractSetupVO);
                    createAllocationOverridesForOverrideAllocationsForFreelookTransfer(segmentFK, contractSetupVO);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();
        }
    }

    /**
     * Updates the appropriate InvestmentAllocationOverride records for Hedge Fund Processing
     * @param segmentFK
     * @param contractSetupVO
     * @param transactionType
     * @param invAllocOvrdVOs
     */
    private void updateIAOverrides(long segmentFK, ContractSetupVO contractSetupVO, String transactionType, InvestmentAllocationOverrideVO[] invAllocOvrdVOs)
    {
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();
        event.business.Event eventComponent = new event.component.EventComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(InvestmentVO.class);
        voInclusionList.add(InvestmentAllocationVO.class);

        //  If indicator is not already set and it is not a free look transfer, set it to yes to say that user created overrides (used by script)
        if (contractSetupVO.getUserInvestmentOverrideInd() == null && !transactionType.equals(EDITTrx.TRANSACTIONTYPECT_FREE_LOOK_TRANSFER))
        {
            contractSetupVO.setUserInvestmentOverrideInd(SaveGroup.YES);
        }

        try
        {
            SegmentVO segmentVO = contractLookup.composeSegmentVO(segmentFK, voInclusionList);

            long productStructureFK = segmentVO.getProductStructureFK();

            ProductStructureVO[] productStructureVO = engineLookup.findProductStructureVOByPK(productStructureFK, false, new ArrayList());
            // Only productstructures that deals with hedge funds will have HedgeFundInterimAccountFK set
            // other productstructures will not have HedgeFundInterimAllocationFK.
            FilteredFundVO[] hfiaFilteredFundVO = null;
            long hedgeFundInterimAccountFK = productStructureVO[0].getHedgeFundInterimAccountFK();
            if (hedgeFundInterimAccountFK != 0)
            {
                hfiaFilteredFundVO = engineLookup.composeFilteredFundVOByCoStructurePK_And_FundPK(productStructureFK, hedgeFundInterimAccountFK, new ArrayList());
            }

            InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();
            boolean toHedgeFundFound = false;
            boolean hfiaFound = false;
            boolean fromFundIsHedge = false;
            EDITBigDecimal toHedgeFundAllocPct = new EDITBigDecimal();
            EDITBigDecimal toHedgeFundAllocDollars = new EDITBigDecimal();
            EDITBigDecimal hfiaAllocPct = new EDITBigDecimal();
            EDITBigDecimal hfiaAllocDollars = new EDITBigDecimal();
            long hfiaInvAllocOvrdPK = 0;

            voInclusionList.clear();
            voInclusionList.add(FundVO.class);

            Hashtable hedgeFundAllocations = new Hashtable();

            for (int i = 0; i < invAllocOvrdVOs.length; i++)
            {
                long investmentFK = invAllocOvrdVOs[i].getInvestmentFK();
                String toFromStatus = invAllocOvrdVOs[i].getToFromStatus();
                for (int j = 0; j < investmentVOs.length; j++)
                {
                    if (investmentVOs[j].getInvestmentPK() == investmentFK)
                    {
                        long filteredFundFK = investmentVOs[j].getFilteredFundFK();
                        FilteredFundVO[] filteredFundVO = engineLookup.composeFilteredFundVOByFilteredFundPK(filteredFundFK, voInclusionList);
                        if (filteredFundVO != null && filteredFundVO.length > 0)
                        {
                            FundVO fundVO = (FundVO) filteredFundVO[0].getParentVO(FundVO.class);
                            String fundType = fundVO.getFundType();
                            if (fundType.equalsIgnoreCase("Hedge"))
                            {
                                if (transactionType.equalsIgnoreCase("PY") ||
                                    transactionType.equalsIgnoreCase("FT") ||
                                    transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT) ||
                                    (transactionType.equalsIgnoreCase("TF") && toFromStatus.equalsIgnoreCase("T")))
                                {
                                    toHedgeFundFound = true;
                                    contractSetupVO.getInvestmentAllocationOverrideVO(i).setToFromStatus("T");
                                    InvestmentAllocationVO[] invAllocVOs = investmentVOs[j].getInvestmentAllocationVO();
                                    for (int k = 0; k < invAllocVOs.length; k++)
                                    {
                                        if (invAllocVOs[k].getInvestmentAllocationPK() == invAllocOvrdVOs[i].getInvestmentAllocationFK())
                                        {
                                            EDITBigDecimal[] invAllocValues = new EDITBigDecimal[2];
                                            invAllocValues[0] = new EDITBigDecimal(invAllocVOs[k].getAllocationPercent());
                                            invAllocValues[1] = new EDITBigDecimal(invAllocVOs[k].getDollars());

                                            hedgeFundAllocations.put(investmentVOs[j].getInvestmentPK() + "", invAllocValues);

                                            k = invAllocVOs.length;
                                        }
                                    }
                                }
                                else
                                {
                                    contractSetupVO.getInvestmentAllocationOverrideVO(i).setToFromStatus("F");
                                    if (transactionType.equalsIgnoreCase("TF") || transactionType.equalsIgnoreCase("WI") ||
                                        transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN))
                                    {
                                        fromFundIsHedge = true;
                                    }
                                }

                                contractSetupVO.getInvestmentAllocationOverrideVO(i).setHFStatus("N");
                                contractSetupVO.getInvestmentAllocationOverrideVO(i).setHFIAIndicator("N");
                                contractSetupVO.getInvestmentAllocationOverrideVO(i).setHoldingAccountIndicator("N");
                            }
                            else if (hfiaFilteredFundVO != null && filteredFundFK == hfiaFilteredFundVO[0].getFilteredFundPK())
                            {
                                if (invAllocOvrdVOs[i].getHFIAIndicator() != null &&
                                    invAllocOvrdVOs[i].getHFIAIndicator().equalsIgnoreCase("Y"))
                                {
//                                    hfiaFound = true;
//                                    hfiaInvAllocOvrdPK = invAllocOvrdVOs[i].getInvestmentAllocationOverridePK();
//                                    InvestmentAllocationVO[] invAllocVOs = investmentVOs[j].getInvestmentAllocationVO();
//                                    for (int k = 0; k < invAllocVOs.length; k++)
//                                    {
//                                        if (invAllocVOs[k].getInvestmentAllocationPK() == invAllocOvrdVOs[i].getInvestmentAllocationFK())
//                                        {
//                                            hfiaAllocPct = hfiaAllocPct.addEditBigDecimal(invAllocVOs[k].getAllocationPercent());
//                                            hfiaAllocDollars = hfiaAllocDollars.addEditBigDecimal(invAllocVOs[k].getDollars());
//                                            k = invAllocVOs.length;
//                                        }
//                                    }
                                }
                                else
                                {
                                    contractSetupVO.getInvestmentAllocationOverrideVO(i).setHFStatus("A");
                                    contractSetupVO.getInvestmentAllocationOverrideVO(i).setHFIAIndicator("N");
                                    contractSetupVO.getInvestmentAllocationOverrideVO(i).setHoldingAccountIndicator("N");
                                }
                            }
                        }

                        j = investmentVOs.length;
                    }
                }
            }
            if (fromFundIsHedge)
            {
                for (int i = 0; i < invAllocOvrdVOs.length; i++)
                {
                    if (invAllocOvrdVOs[i].getToFromStatus().equalsIgnoreCase("T"))
                    {
                        contractSetupVO.getInvestmentAllocationOverrideVO(i).setHFStatus("H");
                    }
                }
            }
            else
            {            
                if (toHedgeFundFound)
                {
                    Enumeration hfAllocKeys = hedgeFundAllocations.keys();

                    while (hfAllocKeys.hasMoreElements())
                    {
                        String hfInvestmentPK = (String) hfAllocKeys.nextElement();
                        EDITBigDecimal[] hfInvAllocValues = (EDITBigDecimal[]) hedgeFundAllocations.get(hfInvestmentPK);
                        hfiaAllocPct = new EDITBigDecimal();
                        hfiaAllocDollars = new EDITBigDecimal();
                        for (int i = 0; i < invAllocOvrdVOs.length; i++)
                        {
                            long investmentFK = invAllocOvrdVOs[i].getInvestmentFK();
                            if (invAllocOvrdVOs[i].getHedgeFundInvestmentFK() == Long.parseLong(hfInvestmentPK))
                            {
                                for (int j = 0; j < investmentVOs.length; j++)
                                {
                                    if (investmentVOs[j].getInvestmentPK() == investmentFK)
                                    {
                                        long filteredFundFK = investmentVOs[j].getFilteredFundFK();
                                        if (filteredFundFK == hfiaFilteredFundVO[0].getFilteredFundPK())
                                        {
                                            if (invAllocOvrdVOs[i].getHFIAIndicator() != null &&
                                                invAllocOvrdVOs[i].getHFIAIndicator().equalsIgnoreCase("Y"))
                                            {
                                                hfiaFound = true;
                                                hfiaInvAllocOvrdPK = invAllocOvrdVOs[i].getInvestmentAllocationOverridePK();
                                                InvestmentAllocationVO[] invAllocVOs = investmentVOs[j].getInvestmentAllocationVO();
                                                for (int k = 0; k < invAllocVOs.length; k++)
                                                {
                                                    if (invAllocVOs[k].getInvestmentAllocationPK() == invAllocOvrdVOs[i].getInvestmentAllocationFK())
                                                    {
                                                        hfiaAllocPct = new EDITBigDecimal(invAllocVOs[k].getAllocationPercent());
                                                        hfiaAllocDollars = new EDITBigDecimal(invAllocVOs[k].getDollars());
                                                        k = invAllocVOs.length;
                                                        j = investmentVOs.length;
                                                        i = invAllocOvrdVOs.length;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (hfiaFound)
                        {
                            if (hfiaAllocPct != hfInvAllocValues[0] ||
                                hfiaAllocDollars != hfInvAllocValues[1])
                            {
                                //change the allocation percent/dollars override for the hfia - the hedge fund allocation changed
                                InvestmentAllocationOverrideVO investmentAllocationOverrideVO =
                                        setupHedgeFundInterimAccount(hfiaFilteredFundVO[0],
                                                                      hfInvAllocValues[0],
                                                                       hfInvAllocValues[1],
                                                                        segmentVO.getSegmentPK(),
                                                                         contractSetupVO.getContractSetupPK());

                                for (int i = 0; i < invAllocOvrdVOs.length; i++)
                                {
                                    if (invAllocOvrdVOs[i].getInvestmentAllocationOverridePK() == hfiaInvAllocOvrdPK)
                                    {
                                        invAllocOvrdVOs[i].setInvestmentAllocationFK(investmentAllocationOverrideVO.getInvestmentAllocationFK());
                                        invAllocOvrdVOs[i].setHedgeFundInvestmentFK(Long.parseLong(hfInvestmentPK));
                                        i = invAllocOvrdVOs.length;
                                    }
                                }
                            }
                        }
                        else
                        {
                            //add an hfia investment allocation override - a hedge fund has been added to the transaction
                            InvestmentAllocationOverrideVO investmentAllocationOverrideVO =
                                    setupHedgeFundInterimAccount(hfiaFilteredFundVO[0],
                                                                  hfInvAllocValues[0],
                                                                   hfInvAllocValues[1],
                                                                    segmentVO.getSegmentPK(),
                                                                     contractSetupVO.getContractSetupPK());

                            investmentAllocationOverrideVO.setHedgeFundInvestmentFK(Long.parseLong(hfInvestmentPK));

                            contractSetupVO.addInvestmentAllocationOverrideVO(investmentAllocationOverrideVO);
                        }
                    }
                }
                else if (hfiaFound)
                {
                    //delete the hedge fund interim account override - there is no longer a hedge fund on this transaction
                    for (int i = 0; i < invAllocOvrdVOs.length; i++)
                    {
                        if (invAllocOvrdVOs[i].getInvestmentAllocationOverridePK() == hfiaInvAllocOvrdPK)
                        {
                            contractSetupVO.removeInvestmentAllocationOverrideVO(i);
                            eventComponent.deleteInvestmentAllocationOverride(hfiaInvAllocOvrdPK);
                            i = invAllocOvrdVOs.length;
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
          System.out.println(e);

          e.printStackTrace();
        }
    }
    
    /**
     * Creates allocation overrides for override allocations for FreeLookTransfer trxs only
     *
     * @param segmentFK
     * @param contractSetupVO
     */
    private void createAllocationOverridesForOverrideAllocationsForFreelookTransfer(long segmentFK, ContractSetupVO contractSetupVO)
    {
        // get all the investments that are primary
        Investment[] investmentsWithOverrideAllocations = Investment.findBy_SegmentPK_InvestmentAllocationOverrideStatus(segmentFK, "O");
        
        for (int i = 0; i < investmentsWithOverrideAllocations.length; i++)
        {
            Investment investment = investmentsWithOverrideAllocations[i];
            
            InvestmentAllocation investmentAllocation = null;
            
            if (investment.isFreeLookFund())
            {
                investmentAllocation = investment.getOverrideInvestmentAllocationWith100Percent();
                
                if (investmentAllocation == null)
                {
                    EDITBigDecimal allocationPercent = new EDITBigDecimal("1.0");
                    
                    investmentAllocation = new InvestmentAllocation(investment.getInvestmentPK(), allocationPercent.getBigDecimal(), "O", "Percent");
                   
                    investmentAllocation.setInvestment(investment);

                    SessionHelper.saveOrUpdate(investmentAllocation, SessionHelper.EDITSOLUTIONS);
                }
            
                InvestmentAllocationOverride investmentAllocationOverride = new InvestmentAllocationOverride();
                
                investmentAllocationOverride.setInvestment(investment);
                investmentAllocationOverride.setInvestmentAllocation(investmentAllocation);
                investmentAllocationOverride.setToFromStatus("F");                
                InvestmentAllocationOverrideVO investmentAllocationOverrideVO = investmentAllocationOverride.getVO();
                
                investmentAllocationOverrideVO.setInvestmentFK(investment.getInvestmentPK());
                investmentAllocationOverrideVO.setInvestmentAllocationFK(investmentAllocation.getInvestmentAllocationPK());
            
                contractSetupVO.addInvestmentAllocationOverrideVO(investmentAllocationOverrideVO);
            }
        }
    }

    /**
     * Creates overrides for primary allocs for FreeLookTransfer trxs only
     *
     * @param segmentFK
     * @param contractSetupVO
     */
    private void createAllocationOverridesForPrimaryAllocationsForFreelookTransfer(long segmentFK, ContractSetupVO contractSetupVO)
    {
        // get all the investments that are primary
        Investment[] investmentsWithPrimaryAllocations = Investment.findBy_SegmentPK_InvestmentAllocationOverrideStatus(segmentFK, "P");

        for (int i = 0; i < investmentsWithPrimaryAllocations.length; i++)
        {
            Investment investment = investmentsWithPrimaryAllocations[i];

            Set<InvestmentAllocation> investmentAllocations = investment.getInvestmentAllocations();
            
            InvestmentAllocation investmentAllocation = null;

            for (Iterator<InvestmentAllocation> iterator = investmentAllocations.iterator(); iterator.hasNext();)
            {
                investmentAllocation = iterator.next();
                
                if (investmentAllocation.getOverrideStatus().equalsIgnoreCase("P"))
                {
                    break;
                }
            }
            
            InvestmentAllocationOverride investmentAllocationOverride = new InvestmentAllocationOverride();
            
            investmentAllocationOverride.setInvestment(investment);
            investmentAllocationOverride.setInvestmentAllocation(investmentAllocation);
            investmentAllocationOverride.setToFromStatus("T");
            InvestmentAllocationOverrideVO investmentAllocationOverrideVO = investmentAllocationOverride.getVO();

            investmentAllocationOverrideVO.setInvestmentFK(investment.getInvestmentPK());
            investmentAllocationOverrideVO.setInvestmentAllocationFK(investmentAllocation.getInvestmentAllocationPK());

            contractSetupVO.addInvestmentAllocationOverrideVO(investmentAllocationOverrideVO);
        }
    }
    
    /**
     * Updates the appropriate InvestmentAllocationOverride records for STF Processing
     * @param contractSetupVO
     * @param invAllocOvrdVOs
     */
    private void updateSTFOverrides(ContractSetupVO contractSetupVO, InvestmentAllocationOverrideVO[] invAllocOvrdVOs)
    {
        contractSetupVO.setUserInvestmentOverrideInd(SaveGroup.YES);

        for (int i = 0; i < invAllocOvrdVOs.length; i++)
        {
            if (invAllocOvrdVOs[i].getToFromStatus().equalsIgnoreCase("F"))
            {
                contractSetupVO.getInvestmentAllocationOverrideVO(i).setHFStatus("N");
            }
            else
            {
                contractSetupVO.getInvestmentAllocationOverrideVO(i).setHFStatus("H");
            }

            contractSetupVO.getInvestmentAllocationOverrideVO(i).setHFIAIndicator("N");
            contractSetupVO.getInvestmentAllocationOverrideVO(i).setHoldingAccountIndicator("N");
        }
    }

    /**
     * Adds an Investment record for the Hedge Fund Interim Account
     * @param filteredFundVO
     * @param hfiaAllocationPct
     * @param hfiaAllocationDollars
     * @param segmentFK
     * @param contractSetupPK
     * @return
     * @throws Exception
     */
    private InvestmentAllocationOverrideVO setupHedgeFundInterimAccount(FilteredFundVO filteredFundVO,
                                                                         EDITBigDecimal hfiaAllocationPct,
                                                                          EDITBigDecimal hfiaAllocationDollars,
                                                                           long segmentFK,
                                                                            long contractSetupPK) throws Exception
    {
        boolean hfiaFound = false;
        
        InvestmentAllocationOverrideVO investmentAllocationOverrideVO = new InvestmentAllocationOverrideVO();

        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(InvestmentAllocationVO.class);

        InvestmentVO[] investmentVOs = contractLookup.composeInvestmentVOBySegmentPK(segmentFK, voInclusionList);

        for (int i = 0; i < investmentVOs.length; i++)
        {
            Investment investment = new Investment(investmentVOs[i]);

            if (investment.getFilteredFundFK().longValue() == filteredFundVO.getFilteredFundPK() &&
                investment.getStatus() == null)
            {
                InvestmentAllocation investmentAllocation = new InvestmentAllocation(investment.getInvestmentAllocationVOs());
                long investmentAllocationPK = 0;

                if (shouldUsePercent(hfiaAllocationPct, hfiaAllocationDollars))
                {
                    investmentAllocationPK = investmentAllocation.getPKForAllocationPercent(hfiaAllocationPct.getBigDecimal());
                }
                else
                {
                    investmentAllocationPK = investmentAllocation.getPKForAllocationDollars(hfiaAllocationDollars);
                }

                if (investmentAllocationPK > 0)
                {
                    InvestmentAllocationOverride investmentAllocationOverride = new InvestmentAllocationOverride(contractSetupPK,
                                                                                                                  investment.getPK(),
                                                                                                                   investmentAllocationPK,
                                                                                                                    "T", "A", "Y", "N");
                    investmentAllocationOverrideVO = investmentAllocationOverride.getVO();
                    hfiaFound = true;
                }
                else
                {
                    if (hfiaAllocationPct.isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
                    {
                        investmentAllocation = new InvestmentAllocation(investment.getPK(),
                                                                         hfiaAllocationPct.getBigDecimal(),
                                                                          "O", "Percent");
                    }
                    else
                    {
                        investmentAllocation = new InvestmentAllocation(investment.getPK(),
                                                                         hfiaAllocationDollars.getBigDecimal(),
                                                                          "O", "Dollars");
                    }

                    investmentAllocation.save();
                    long newInvestmentAllocationPK = investmentAllocation.getNewInvestmentAllocationPK();

                    InvestmentAllocationOverride investmentAllocationOverride = new InvestmentAllocationOverride(contractSetupPK,
                                                                                                                  investment.getPK(),
                                                                                                                   newInvestmentAllocationPK,
                                                                                                                    "T", "A", "Y", "N");
                    investmentAllocationOverrideVO = investmentAllocationOverride.getVO();
                    hfiaFound = true;
                }
            }
        }

        if (!hfiaFound)
        {
            Investment investment = new Investment(segmentFK, filteredFundVO.getFilteredFundPK());

            investment.save();

            long newInvestmentPK = investment.getNewInvestmentPK();

            InvestmentAllocation investmentAllocation = null;

            if (hfiaAllocationPct.isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
            {
                investmentAllocation = new InvestmentAllocation(newInvestmentPK,
                                                                 hfiaAllocationPct.getBigDecimal(),
                                                                  "O", "Percent");
            }
            else
            {
                investmentAllocation = new InvestmentAllocation(investment.getPK(),
                                                                 hfiaAllocationDollars.getBigDecimal(),
                                                                  "O", "Dollars");
            }

            investmentAllocation.save();
            long newInvestmentAllocationPK = investmentAllocation.getNewInvestmentAllocationPK();

            InvestmentAllocationOverride investmentAllocationOverride = new InvestmentAllocationOverride(contractSetupPK,
                                                                                                          newInvestmentPK,
                                                                                                           newInvestmentAllocationPK,
                                                                                                            "T", "A", "Y", "N");
            investmentAllocationOverrideVO = investmentAllocationOverride.getVO();
        }

        return investmentAllocationOverrideVO;
    }

    /**
     * Find all Held transactions (pending status = "X") for the given segment and reset the pending status to "P"
     * - they are ready to run because a premium ("PY") transaction is forthcoming.
     * @param segmentPK
     */
    private void resetHeldTransactions(long segmentPK)
    {
        Segment segment = Segment.findByPK(new Long(segmentPK));

        EDITTrx[] editTrxs = EDITTrx.findHeldMDEditTrx(segment);

        if (editTrxs != null)
        {
            for (int i = 0; i < editTrxs.length; i++)
            {
                editTrxs[i].setPendingStatus("P");

                editTrxs[i].saveNonRecursively();

                SessionHelper.evict(editTrxs[i], SessionHelper.EDITSOLUTIONS);
            }
        }
    }

    /**
     * Builds the hedge fund list which contains all the hedge funds that "have money" (??)
     * @param investmentVOs
     * @param transactionType
     * @return
     * @throws Exception
     */
    private List getHedgeFundList(InvestmentVO[] investmentVOs, String transactionType, boolean isMaxLoan) throws Exception
    {
        List hedgeFundList = new ArrayList();

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(FundVO.class);

        for (int i = 0; i < investmentVOs.length; i++)
        {
            long filteredFundFK = investmentVOs[i].getFilteredFundFK();

            FilteredFundVO[] filteredFundVO = engineLookup.composeFilteredFundVOByFilteredFundPK(filteredFundFK, voInclusionList);

            if (filteredFundVO != null && filteredFundVO.length > 0)
            {
                FundVO fundVO = (FundVO) filteredFundVO[0].getParentVO(FundVO.class);
                String fundType = fundVO.getFundType();

                if (fundType.equalsIgnoreCase("Hedge"))
                {
                    if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_WITHDRAWAL) ||
                        transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT) ||
                        isMaxLoan)
                    {
                        if (investmentHasMoney(investmentVOs[i]))
                        {
                            hedgeFundList.add(filteredFundFK + "");
                        }
                    }
                    else
                    {
                        InvestmentAllocationVO[] invAllocVOs = investmentVOs[i].getInvestmentAllocationVO();
                        for (int j = 0; j < invAllocVOs.length; j++)
                        {
                            if (invAllocVOs[j].getOverrideStatus().equalsIgnoreCase("P") &&
                                new EDITBigDecimal(invAllocVOs[j].getAllocationPercent()).isGT(new EDITBigDecimal()))
                            {
                                hedgeFundList.add(filteredFundFK + "");
                                break;
                            }
                        }
                    }
                }
            }
        }

        return hedgeFundList;
    }

    /**
     * Determines if the investment has money by checking for any cumUnits greater than zero in the investment's buckets
     *
     * @param investmentVO          investment to be checked
     *
     * @return  true if even one of the buckets has a cumUnit greater than zero, false otherwise
     */
    private boolean investmentHasMoney(InvestmentVO investmentVO)
    {
        boolean hasMoney = false;

        BucketVO[] bucketVOs = investmentVO.getBucketVO();

        for (int i = 0; i < bucketVOs.length; i++)
        {
            EDITBigDecimal cumUnits = new EDITBigDecimal(bucketVOs[i].getCumUnits());

            if (cumUnits.isGT("0"))
            {
                hasMoney = true;
                break;
            }
        }

        return hasMoney;
    }

    /**
     * Determines if an InvestmentAllocationOverride should be created or not.  If the transaction type is withdrawal
     * and it is a System fund, the override should not be created.  Otherwise, it should be.
     *
     * @param investmentVO              investment to get fund from
     * @param transactionType           type of transaction
     *
     * @return  true, unless the transactionType is withdrawal and it's a System fund
     */
    private boolean shouldCreateIAOverride(InvestmentVO investmentVO, String transactionType)
    {
        boolean shouldCreateIAOverride = true;

        if (transactionType.equals(EDITTrx.TRANSACTIONTYPECT_WITHDRAWAL))
        {
            FilteredFund filteredFund = FilteredFund.findByPK(new Long(investmentVO.getFilteredFundFK()));

            Fund fund = filteredFund.getFund();

            if (fund.getTypeCodeCT().equals("System"))
            {
                shouldCreateIAOverride = false;
            }
        }

        return shouldCreateIAOverride;
    }

    /**
     * Determines whether percent or dollars should be used based on which one has a value.  If both are zero, percent
     * is used.  If percent has a value, percent is used, otherwise dollars is used.
     *
     * @param percent
     * @param dollars
     *
     * @return  true if percent should be used, false otherwise
     */
    private boolean shouldUsePercent(EDITBigDecimal percent, EDITBigDecimal dollars)
    {
        boolean shouldUsePercent = false;

        EDITBigDecimal zero = new EDITBigDecimal();

        if (percent.isEQ(zero) && dollars.isEQ(zero))
        {
            shouldUsePercent = true;
        }
        else if (percent.isGT(zero))
        {
            shouldUsePercent = true;
        }

        return shouldUsePercent;
    }
}
