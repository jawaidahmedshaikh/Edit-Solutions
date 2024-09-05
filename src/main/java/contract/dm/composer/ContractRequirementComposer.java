package contract.dm.composer;

import edit.common.vo.ContractRequirementVO;
import edit.common.vo.FilteredRequirementVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Aug 4, 2003
 * Time: 2:07:19 PM
 * To change this template use Options | File Templates.
 */
public class ContractRequirementComposer extends Composer
{
    private List voInclusionList;

    private CRUD crud;

    public ContractRequirementComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public ContractRequirementVO compose(long contractRequirementPK) throws Exception
    {
        ContractRequirementVO contractRequirementVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            contractRequirementVO = (ContractRequirementVO) crud.retrieveVOFromDB(ContractRequirementVO.class, contractRequirementPK);

            compose(contractRequirementVO);
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

        return contractRequirementVO;
    }

    public ContractRequirementVO compose(ContractRequirementVO contractRequirementVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(FilteredRequirementVO.class)) associateFilteredRequirementVO(contractRequirementVO);
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
        return contractRequirementVO;
    }

    private void associateFilteredRequirementVO(ContractRequirementVO contractRequirementVO) throws Exception
    {
        voInclusionList.remove(FilteredRequirementVO.class);

        FilteredRequirementComposer composer = new FilteredRequirementComposer(voInclusionList);

        FilteredRequirementVO filteredRequirementVO = composer.compose(contractRequirementVO.getFilteredRequirementFK());

        contractRequirementVO.setParentVO(FilteredRequirementVO.class, filteredRequirementVO);

        filteredRequirementVO.addContractRequirementVO(contractRequirementVO);

        voInclusionList.add(FilteredRequirementVO.class);
    }
}
