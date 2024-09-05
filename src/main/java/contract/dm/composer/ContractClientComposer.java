package contract.dm.composer;

import edit.common.vo.ClientRoleVO;
import edit.common.vo.ContractClientAllocationVO;
import edit.common.vo.ContractClientVO;
import edit.common.vo.WithholdingVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

import role.dm.composer.ClientRoleComposer;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Aug 4, 2003
 * Time: 2:13:13 PM
 * To change this template use Options | File Templates.
 */
public class ContractClientComposer extends Composer
{
    private List voInclusionList;

    private ContractClientAllocationVO[] contractClientAllocationVO;
    private WithholdingVO[] withholdingVO;

    private CRUD crud;

    public ContractClientComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public ContractClientVO compose(long contractClientPK)
    {
        ContractClientVO contractClientVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            contractClientVO = (ContractClientVO) crud.retrieveVOFromDB(ContractClientVO.class, contractClientPK);

            compose(contractClientVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return contractClientVO;
    }

    public void compose(ContractClientVO contractClientVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(ContractClientAllocationVO.class)) appendContractClientAllocationVO(contractClientVO);

            if (voInclusionList.contains(WithholdingVO.class)) appendWithholdingVO(contractClientVO);

            if (voInclusionList.contains(ClientRoleVO.class)) associateClientRoleVO(contractClientVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw e;
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }
    }

    public void substituteContractClientAllocationVO(ContractClientAllocationVO[] contractClientAllocationVO)
    {
        this.contractClientAllocationVO = contractClientAllocationVO;
    }

    public void substituteWithholdingVO(WithholdingVO[] withholdingVO)
    {
        this.withholdingVO = withholdingVO;
    }

    private void appendContractClientAllocationVO(ContractClientVO contractClientVO) throws Exception
    {
        voInclusionList.remove(ContractClientVO.class);

        if (contractClientAllocationVO == null) // No overrides
        {
            contractClientAllocationVO = (ContractClientAllocationVO[]) crud.retrieveVOFromDB(ContractClientAllocationVO.class, ContractClientVO.class, contractClientVO.getContractClientPK());
        }

        if (contractClientAllocationVO != null) contractClientVO.setContractClientAllocationVO(contractClientAllocationVO);

        voInclusionList.add(ContractClientVO.class);
    }

    private void appendWithholdingVO(ContractClientVO contractClientVO) throws Exception
    {
        voInclusionList.remove(ContractClientVO.class);

        if (withholdingVO == null) // No overridees
        {
            withholdingVO = (WithholdingVO[]) crud.retrieveVOFromDB(WithholdingVO.class, ContractClientVO.class, contractClientVO.getContractClientPK());
        }

        if (withholdingVO != null) contractClientVO.setWithholdingVO(withholdingVO);

        voInclusionList.add(ContractClientVO.class);
    }

    private void associateClientRoleVO(ContractClientVO contractClientVO) throws Exception
    {
        voInclusionList.remove(ContractClientVO.class);

        ClientRoleVO clientRoleVO = new ClientRoleComposer(voInclusionList).compose(contractClientVO.getClientRoleFK());

        contractClientVO.setParentVO(ClientRoleVO.class, clientRoleVO);

        voInclusionList.add(ContractClientVO.class);
    }
}
