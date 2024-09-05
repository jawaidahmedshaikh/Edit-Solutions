package event.dm.composer;

import edit.common.vo.*;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Aug 14, 2003
 * Time: 9:59:13 AM
 * To change this template use Options | File Templates.
 */
public class GroupSetupComposer extends Composer
{
    private List voInclusionList;
    private ScheduledEventVO[] scheduledEventVO;
    private ChargeVO[] chargeVO;
    private OutSuspenseVO[] outSuspenseVO;

    private ContractSetupVO[] contractSetupVO;
    private Map investmentAllocationOverrideVO;

    private Map clientSetupVO;
    private Map withholdingOverrideVO;
    private Map contractClientAllocationOverrideVO;

    private Map editTrxVO;

    private CRUD crud;

    public GroupSetupComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
        this.investmentAllocationOverrideVO = new HashMap();
        this.clientSetupVO = new HashMap();
        this.withholdingOverrideVO = new HashMap();
        this.contractClientAllocationOverrideVO = new HashMap();
        this.editTrxVO = new HashMap();
    }

    public GroupSetupVO compose(long groupSetupPK) throws Exception
    {
        GroupSetupVO groupSetupVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            groupSetupVO = (GroupSetupVO) crud.retrieveVOFromDB(GroupSetupVO.class, groupSetupPK);

            compose(groupSetupVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return groupSetupVO;
    }

    public GroupSetupVO compose(GroupSetupVO groupSetupVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(ScheduledEventVO.class)) appendScheduledEventVO(groupSetupVO);
            if (voInclusionList.contains(ChargeVO.class)) appendChargeVO(groupSetupVO);
            if (voInclusionList.contains(ContractSetupVO.class)) appendContractSetupVO(groupSetupVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return groupSetupVO;
    }

    public void subsituteScheduledEventVO(ScheduledEventVO[] scheduledEventVO)
    {
        this.scheduledEventVO = scheduledEventVO;
    }

    public void substituteChargeVO(ChargeVO[] chargeVO)
    {
        this.chargeVO = chargeVO;
    }

    public void substituteContractSetupVO(ContractSetupVO[] contractSetupVO)
    {
        this.contractSetupVO = contractSetupVO;
    }

//    public void substituteBucketAllocationOverrideVO(BucketAllocationOverrideVO[] bucketAllocationOverrideVO) throws Exception
//    {
//        Method parentFKMethod = BucketAllocationOverrideVO.class.getMethod("getContractSetupFK", null);
//
//        populateSubstitutionHashtable(this.bucketAllocationOverrideVO, bucketAllocationOverrideVO, parentFKMethod);
//    }

    public void substituteInvestmentAllocationOverrideVO(InvestmentAllocationOverrideVO[] investmentAllocationOverrideVO) throws Exception
    {
        Method parentFKMethod = InvestmentAllocationOverrideVO.class.getMethod("getContractSetupFK", null);

        populateSubstitutionHashtable(this.investmentAllocationOverrideVO, investmentAllocationOverrideVO, parentFKMethod);
    }

    public void substituteClientSetupVO(ClientSetupVO[] clientSetupVO) throws Exception
    {
        Method parentFKMethod = ClientSetupVO.class.getMethod("getContractSetupFK", null);

        populateSubstitutionHashtable(this.clientSetupVO, clientSetupVO, parentFKMethod);
    }

    public void subsituteWithholdingOverrideVO(WithholdingOverrideVO[] withholdingOverrideVO) throws Exception
    {
        Method parentFKMethod = WithholdingOverrideVO.class.getMethod("getClientSetupFK", null);

        populateSubstitutionHashtable(this.withholdingOverrideVO, withholdingOverrideVO, parentFKMethod);
    }

    public void substituteContractClientAllocationOverrideVO(ContractClientAllocationOvrdVO[] contractClientAllocationOverrideVO) throws Exception
    {
        Method parentFKMethod = ContractClientAllocationOvrdVO.class.getMethod("getClientSetupFK", null);

        populateSubstitutionHashtable(this.contractClientAllocationOverrideVO, contractClientAllocationOverrideVO, parentFKMethod);
    }

    public void substituteEDITTrxVO(EDITTrxVO[] editTrxVO) throws Exception
    {
        Method parentFKMethod = EDITTrxVO.class.getMethod("getClientSetupFK", null);

        populateSubstitutionHashtable(this.editTrxVO, editTrxVO, parentFKMethod);
    }

    private void appendChargeVO(GroupSetupVO groupSetupVO) throws Exception
    {
        voInclusionList.remove(GroupSetupVO.class);

        if (chargeVO == null) // No overrides
        {
            chargeVO = (ChargeVO[]) crud.retrieveVOFromDB(ChargeVO.class, GroupSetupVO.class, groupSetupVO.getGroupSetupPK());
        }

        if (chargeVO != null) groupSetupVO.setChargeVO(chargeVO);

        voInclusionList.add(GroupSetupVO.class);
    }

    private void appendOutSuspenseVO(ContractSetupVO contractSetupVO) throws Exception
    {
        voInclusionList.remove(ContractSetupVO.class);

        if (outSuspenseVO == null) // No overrides
        {
            outSuspenseVO = (OutSuspenseVO[]) crud.retrieveVOFromDB(OutSuspenseVO.class, ContractSetupVO.class, contractSetupVO.getContractSetupPK());
        }

        if (outSuspenseVO != null)
        {
            contractSetupVO.setOutSuspenseVO(outSuspenseVO);

            for (int i = 0; i < outSuspenseVO.length; i++)
            {
                if (voInclusionList.contains(SuspenseVO.class)) associateSuspenseVO(outSuspenseVO[i]);
            }
        }

        voInclusionList.add(ContractSetupVO.class);
    }

    private void associateSuspenseVO(OutSuspenseVO outSuspenseVO) throws Exception
    {
        voInclusionList.remove(OutSuspenseVO.class);

        SuspenseVO suspenseVO = (SuspenseVO) crud.retrieveVOFromDB(SuspenseVO.class, outSuspenseVO.getSuspenseFK());

        if (suspenseVO != null) outSuspenseVO.setParentVO(SuspenseVO.class, suspenseVO);

        voInclusionList.add(OutSuspenseVO.class);
    }

    private void appendContractSetupVO(GroupSetupVO groupSetupVO) throws Exception
    {
        voInclusionList.remove(GroupSetupVO.class);

        if (contractSetupVO == null) // No overrides
        {
            contractSetupVO = (ContractSetupVO[]) crud.retrieveVOFromDB(ContractSetupVO.class, GroupSetupVO.class, groupSetupVO.getGroupSetupPK());
        }

        if (contractSetupVO != null)
        {
            groupSetupVO.setContractSetupVO(contractSetupVO);

            for (int i = 0; i < contractSetupVO.length; i++)
            {
                if (voInclusionList.contains(OutSuspenseVO.class)) appendOutSuspenseVO(contractSetupVO[i]);
                if (voInclusionList.contains(InvestmentAllocationOverrideVO.class)) appendInvestmentAllocationOverrideVO(contractSetupVO[i]);
                if (voInclusionList.contains(ClientSetupVO.class)) appendClientSetupVO(contractSetupVO[i]);
            }
        }

        voInclusionList.add(GroupSetupVO.class);
    }


    private void appendInvestmentAllocationOverrideVO(ContractSetupVO contractSetupVO) throws Exception
    {
        voInclusionList.remove(ContractSetupVO.class);

        InvestmentAllocationOverrideVO[] investmentAllocationOverrideVO = null;

        if (!this.investmentAllocationOverrideVO.containsKey(new Long(contractSetupVO.getContractSetupPK()))) // No overrides
        {
            investmentAllocationOverrideVO = (InvestmentAllocationOverrideVO[]) crud.retrieveVOFromDB(InvestmentAllocationOverrideVO.class, ContractSetupVO.class, contractSetupVO.getContractSetupPK());
        }
//        else
//        {
//            investmentAllocationOverrideVO = (InvestmentAllocationOverrideVO[]) retrieveFromSubstitutionHashtable(this.investmentAllocationOverrideVO, BucketAllocationOverrideVO.class, new Long(contractSetupVO.getContractSetupPK()));
//        }

        if (investmentAllocationOverrideVO != null) contractSetupVO.setInvestmentAllocationOverrideVO(investmentAllocationOverrideVO);

        voInclusionList.add(ContractSetupVO.class);
    }

    private void appendClientSetupVO(ContractSetupVO contractSetupVO) throws Exception
    {
        voInclusionList.remove(ContractSetupVO.class);

        ClientSetupVO[] clientSetupVO = null;

        if (!this.clientSetupVO.containsKey(new Long(contractSetupVO.getContractSetupPK()))) // No overrides
        {
            clientSetupVO = (ClientSetupVO[]) crud.retrieveVOFromDB(ClientSetupVO.class, ContractSetupVO.class, contractSetupVO.getContractSetupPK());
        }
        else
        {
            clientSetupVO = (ClientSetupVO[]) retrieveFromSubstitutionHashtable(this.clientSetupVO, ClientSetupVO.class, new Long(contractSetupVO.getContractSetupPK()));
        }

        if (clientSetupVO != null)
        {
            contractSetupVO.setClientSetupVO(clientSetupVO);

            for (int i = 0; i < clientSetupVO.length; i++)
            {
                if (voInclusionList.contains(WithholdingOverrideVO.class)) appendWitholdingOverrideVO(clientSetupVO[i]);
                if (voInclusionList.contains(ContractClientAllocationOvrdVO.class)) appendContractClientAllocationOverrideVO(clientSetupVO[i]);
                if (voInclusionList.contains(EDITTrxVO.class)) appendEDITTrxVO(clientSetupVO[i]);
            }
        }

        voInclusionList.add(ContractSetupVO.class);
    }

    private void appendWitholdingOverrideVO(ClientSetupVO clientSetupVO) throws Exception
    {
        voInclusionList.remove(ClientSetupVO.class);

        WithholdingOverrideVO[] withholdingOverrideVO = null;

        if (!this.withholdingOverrideVO.containsKey(new Long(clientSetupVO.getClientSetupPK()))) // No overrides
        {
            withholdingOverrideVO = (WithholdingOverrideVO[]) crud.retrieveVOFromDB(WithholdingOverrideVO.class, ClientSetupVO.class, clientSetupVO.getClientSetupPK());
        }
        else
        {
            withholdingOverrideVO = (WithholdingOverrideVO[]) retrieveFromSubstitutionHashtable(this.withholdingOverrideVO, WithholdingOverrideVO.class, new Long(clientSetupVO.getClientSetupPK()));
        }

        if (withholdingOverrideVO != null) clientSetupVO.setWithholdingOverrideVO(withholdingOverrideVO);

        voInclusionList.add(ClientSetupVO.class);
    }

    private void appendContractClientAllocationOverrideVO(ClientSetupVO clientSetupVO) throws Exception
    {
        voInclusionList.remove(ClientSetupVO.class);

        ContractClientAllocationOvrdVO[] contractClientAllocationOverrideVO = null;

        if (!this.contractClientAllocationOverrideVO.containsKey(new Long(clientSetupVO.getClientSetupPK()))) // No overrides
        {
            contractClientAllocationOverrideVO = (ContractClientAllocationOvrdVO[]) crud.retrieveVOFromDB(ContractClientAllocationOvrdVO.class, ClientSetupVO.class, clientSetupVO.getClientSetupPK());
        }
        else
        {
            contractClientAllocationOverrideVO = (ContractClientAllocationOvrdVO[]) retrieveFromSubstitutionHashtable(this.contractClientAllocationOverrideVO, ContractClientAllocationOvrdVO.class, new Long(clientSetupVO.getClientSetupPK()));
        }

        if (contractClientAllocationOverrideVO != null) clientSetupVO.setContractClientAllocationOvrdVO(contractClientAllocationOverrideVO);

        voInclusionList.add(ClientSetupVO.class);
    }

    private void appendEDITTrxVO(ClientSetupVO clientSetupVO) throws Exception
    {
        voInclusionList.remove(ClientSetupVO.class);

        EDITTrxVO[] editTrxVO = null;

        if (!this.editTrxVO.containsKey(new Long(clientSetupVO.getClientSetupPK()))) // No overrides
        {
            editTrxVO = (EDITTrxVO[]) crud.retrieveVOFromDB(EDITTrxVO.class, ClientSetupVO.class, clientSetupVO.getClientSetupPK());
        }
        else
        {
            editTrxVO = (EDITTrxVO[]) retrieveFromSubstitutionHashtable(this.editTrxVO, EDITTrxVO.class, new Long(clientSetupVO.getClientSetupPK()));
        }

        if (editTrxVO != null)
        {
            clientSetupVO.setEDITTrxVO(editTrxVO);

            for (int i = 0; i < editTrxVO.length; i++)
            {
                if (voInclusionList.contains(EDITTrxCorrespondenceVO.class))
                {
                    appendEDITTrxCorrespondenceVO(editTrxVO[i]);
                }
                if (voInclusionList.contains(EDITTrxHistoryVO.class))
                {
                    appendEDITTrxHistoryVO(editTrxVO[i]);
                }
            }
        }

        voInclusionList.add(ClientSetupVO.class);
    }

    private void appendEDITTrxCorrespondenceVO(EDITTrxVO editTrxVO) throws Exception
    {
        voInclusionList.remove(EDITTrxVO.class);

        EDITTrxCorrespondenceVO[] editTrxCorrespondenceVO = (EDITTrxCorrespondenceVO[]) crud.retrieveVOFromDB(EDITTrxCorrespondenceVO.class, EDITTrxVO.class, editTrxVO.getEDITTrxPK());

        if (editTrxCorrespondenceVO != null)
        {
            editTrxVO.setEDITTrxCorrespondenceVO(editTrxCorrespondenceVO);

            for (int i = 0; i < editTrxCorrespondenceVO.length; i++)
            {
                editTrxCorrespondenceVO[i].setParentVO(EDITTrxVO.class, editTrxVO);
            }
        }

        voInclusionList.add(EDITTrxVO.class);
    }

    private void appendEDITTrxHistoryVO(EDITTrxVO editTrxVO) throws Exception
    {
        voInclusionList.remove(EDITTrxVO.class);

        EDITTrxHistoryVO[] editTrxHistoryVO = (EDITTrxHistoryVO[]) crud.retrieveVOFromDB(EDITTrxHistoryVO.class, EDITTrxVO.class, editTrxVO.getEDITTrxPK());

        if (editTrxHistoryVO != null)
        {
            editTrxVO.setEDITTrxHistoryVO(editTrxHistoryVO);

            for (int i = 0; i < editTrxHistoryVO.length; i++)
            {
                editTrxHistoryVO[i].setParentVO(EDITTrxVO.class, editTrxVO);
                new EDITTrxHistoryComposer(voInclusionList).compose(editTrxHistoryVO[i]);
            }
        }

        voInclusionList.add(EDITTrxVO.class);
    }
    private void appendScheduledEventVO(GroupSetupVO groupSetupVO) throws Exception
    {
        voInclusionList.remove(GroupSetupVO.class);

        if (scheduledEventVO == null) // No overrides
        {
            scheduledEventVO = (ScheduledEventVO[]) crud.retrieveVOFromDB(ScheduledEventVO.class, GroupSetupVO.class, groupSetupVO.getGroupSetupPK());
        }

        if (scheduledEventVO != null) groupSetupVO.setScheduledEventVO(scheduledEventVO);

        voInclusionList.add(GroupSetupVO.class);
    }
}
