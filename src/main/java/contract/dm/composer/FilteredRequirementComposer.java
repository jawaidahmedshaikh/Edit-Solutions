package contract.dm.composer;

import edit.common.vo.FilteredRequirementVO;
import edit.common.vo.RequirementVO;
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
public class FilteredRequirementComposer extends Composer
{
    private List voInclusionList;

    private CRUD crud;

    public FilteredRequirementComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public FilteredRequirementVO compose(long filteredRequirementPK) throws Exception
    {
        FilteredRequirementVO filteredRequirementVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            filteredRequirementVO = (FilteredRequirementVO) crud.retrieveVOFromDB(FilteredRequirementVO.class, filteredRequirementPK);

            compose(filteredRequirementVO);
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

        return filteredRequirementVO;
    }

    public void compose(FilteredRequirementVO filteredRequirementVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(RequirementVO.class)) associateRequirementVO(filteredRequirementVO);
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

    private void associateRequirementVO(FilteredRequirementVO filteredRequirementVO) throws Exception
    {
        voInclusionList.remove(RequirementVO.class);

        RequirementComposer composer = new RequirementComposer(voInclusionList);

        RequirementVO requirementVO = composer.compose(filteredRequirementVO.getRequirementFK());

        filteredRequirementVO.setParentVO(RequirementVO.class, requirementVO);

        requirementVO.addFilteredRequirementVO(filteredRequirementVO);

        voInclusionList.add(RequirementVO.class);
    }
}
