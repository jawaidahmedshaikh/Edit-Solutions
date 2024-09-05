package event.dm.composer;

import edit.services.db.Composer;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.common.vo.*;

import java.util.List;

import event.dm.dao.DAOFactory;

/**
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Aug 3, 2004
 * Time: 7:40:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContractSetupComposer extends Composer
{
    private List voInclusionList;
    private CRUD crud;
    private OutSuspenseVO[] outSuspenseVO;
    private InvestmentAllocationOverrideVO[] investmentAllocationOverrideVO;

    public ContractSetupComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public ContractSetupVO compose(long contractSetupPK) throws Exception
    {
        ContractSetupVO contractSetupVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            contractSetupVO = (ContractSetupVO) crud.retrieveVOFromDB(ContractSetupVO.class, contractSetupPK);

            compose(contractSetupVO);
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

        return contractSetupVO;
    }

    public ContractSetupVO compose(ContractSetupVO contractSetupVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(ClientSetupVO.class)) appendClientSetupVO(contractSetupVO);
            if (voInclusionList.contains(GroupSetupVO.class)) associateGroupSetupVO(contractSetupVO);
            if (voInclusionList.contains(OutSuspenseVO.class)) appendOutSuspenseVO(contractSetupVO);
            if (voInclusionList.contains(InvestmentAllocationOverrideVO.class)) appendInvestmentAllocationOverrideVO(contractSetupVO);

            if (voInclusionList.contains(SegmentVO.class) &&
                contractSetupVO.getSegmentFK() > 0)
            {
                associateSegmentVO(contractSetupVO);
            }
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

        return contractSetupVO;
    }

    private void appendClientSetupVO(ContractSetupVO contractSetupVO) throws Exception
    {
        voInclusionList.remove(ContractSetupVO.class);

        ClientSetupVO[] clientSetupVO = null;

        clientSetupVO = (ClientSetupVO[]) crud.retrieveVOFromDB(ClientSetupVO.class, ContractSetupVO.class, contractSetupVO.getContractSetupPK());

        if (clientSetupVO != null)
        {
            for (int i = 0; i < clientSetupVO.length; i++)
            {
                ClientSetupComposer composer = new ClientSetupComposer(voInclusionList);

                composer.compose(clientSetupVO[i]);

                contractSetupVO.addClientSetupVO(clientSetupVO[i]);
            }
        }

        voInclusionList.add(ClientSetupVO.class);
    }

    private void associateGroupSetupVO(ContractSetupVO contractSetupVO) throws Exception
    {
        voInclusionList.remove(ContractSetupVO.class);

        GroupSetupVO groupSetupVO = new GroupSetupComposer(voInclusionList).compose(contractSetupVO.getGroupSetupFK());

        contractSetupVO.setParentVO(GroupSetupVO.class, groupSetupVO);

        groupSetupVO.addContractSetupVO(contractSetupVO);

        voInclusionList.add(ContractSetupVO.class);
    }

    private void appendOutSuspenseVO(ContractSetupVO contractSetupVO) throws Exception
    {
        voInclusionList.remove(ContractSetupVO.class);

        if (outSuspenseVO == null)
        {
            outSuspenseVO = DAOFactory.getOutSuspenseDAO().findByContractSetupPK(contractSetupVO.getContractSetupPK());
        }

        if (outSuspenseVO != null)
        {
            if (voInclusionList.contains(SuspenseVO.class))
            {
                for (int i = 0; i < outSuspenseVO.length; i++)
                {
                    associateSuspenseVO(outSuspenseVO[i]);
                }
            }
            contractSetupVO.setOutSuspenseVO(outSuspenseVO);
        }

         voInclusionList.add(ContractSetupVO.class);
    }

    private void associateSegmentVO(ContractSetupVO contractSetupVO) throws Exception
    {
        voInclusionList.remove(ContractSetupVO.class);

        SegmentVO segmentVO = new contract.dm.composer.VOComposer().composeSegmentVO(contractSetupVO.getSegmentFK(), voInclusionList);

        contractSetupVO.setParentVO(SegmentVO.class, segmentVO);

        voInclusionList.add(ContractSetupVO.class);
    }

    private void associateSuspenseVO(OutSuspenseVO outSuspenseVO) throws Exception
    {
        voInclusionList.remove(OutSuspenseVO.class);

        SuspenseVO suspenseVO = (SuspenseVO) crud.retrieveVOFromDB(SuspenseVO.class, outSuspenseVO.getSuspenseFK());

        outSuspenseVO.setParentVO(SuspenseVO.class, suspenseVO);

        voInclusionList.add(OutSuspenseVO.class);
    }

   private void appendInvestmentAllocationOverrideVO(ContractSetupVO contractSetupVO)  throws Exception
   {
       voInclusionList.remove(ContractSetupVO.class);

        if (investmentAllocationOverrideVO == null)
        {
            investmentAllocationOverrideVO = DAOFactory.getInvestmentAllocationOverrideDAO().findByContractSetupPK(contractSetupVO.getContractSetupPK());
        }

        if (investmentAllocationOverrideVO != null)
        {
            for (int i = 0; i < investmentAllocationOverrideVO.length; i++)
            {
                InvestmentAllocationOverrideComposer composer = new InvestmentAllocationOverrideComposer(voInclusionList);

                composer.compose(investmentAllocationOverrideVO[i]);

                contractSetupVO.addInvestmentAllocationOverrideVO(investmentAllocationOverrideVO[i]);
            }
        }

         voInclusionList.add(ContractSetupVO.class);
   }
}
