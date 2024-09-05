package engine.dm.composer;

import edit.common.vo.AreaVO;
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
public class AreaComposer extends Composer
{
    private CRUD crud;

    private List voInclusionList;

    public AreaComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public AreaVO compose(long areaPK) throws Exception
    {
        AreaVO areaVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            areaVO = (AreaVO) crud.retrieveVOFromDB(AreaVO.class, areaPK);

            compose(areaVO);
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

        return areaVO;
    }

    public void compose(AreaVO areaVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);
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
}
