package engine.dm.composer;

import edit.common.vo.AreaVO;
import edit.common.vo.FilteredAreaVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 7, 2003
 * Time: 4:04:56 PM
 * To change this template use Options | File Templates.
 */
public class FilteredAreaComposer extends Composer
{
    private CRUD crud;

    private List voInclusionList;

    public FilteredAreaComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public FilteredAreaVO compose(long filteredAreaPK) throws Exception
    {
        FilteredAreaVO filteredAreaVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            filteredAreaVO = (FilteredAreaVO) crud.retrieveVOFromDB(FilteredAreaVO.class, filteredAreaPK);

            compose(filteredAreaVO);
        }
        catch(Exception e)
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

        return filteredAreaVO;
    }

    public void compose(FilteredAreaVO filteredAreaVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            if (voInclusionList.contains(AreaVO.class)) associateAreaVO(filteredAreaVO);
        }
        catch(Exception e)
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
    }

    private void associateAreaVO(FilteredAreaVO filteredAreaVO) throws Exception
    {
        voInclusionList.remove(FilteredAreaVO.class);

        if (filteredAreaVO.getAreaFK() != 0)
        {
            AreaVO areaVO = new AreaComposer(voInclusionList).compose(filteredAreaVO.getAreaFK());

            areaVO.addFilteredAreaVO(filteredAreaVO);

            filteredAreaVO.setParentVO(AreaVO.class, areaVO);
        }

        voInclusionList.add(FilteredAreaVO.class);
    }
}
