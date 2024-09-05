package client.dm.composer;

import client.dm.dao.DAOFactory;
import edit.common.vo.PreferenceVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Aug 6, 2003
 * Time: 10:08:47 AM
 * To change this template use Options | File Templates.
 */
public class PreferenceComposer extends Composer
{
    private List voInclusionList = null;

    private CRUD crud = null;

    public PreferenceComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public PreferenceVO compose(long preferencePK) throws Exception
    {
        PreferenceVO preferenceVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            preferenceVO = (PreferenceVO) crud.retrieveVOFromDB(PreferenceVO.class, preferencePK);

            compose(preferenceVO);
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

        return preferenceVO;
    }

    public PreferenceVO composePrimaryPreference(long clientDetailPK) throws Exception
    {
        PreferenceVO[] preferenceVO = null;

        try
        {
            preferenceVO = DAOFactory.getPreferenceDAO().findPrimaryByClientDetailPK(clientDetailPK, false, null);

            if (preferenceVO != null && preferenceVO.length > 0)
            {
                compose(preferenceVO[0]);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw e;
        }

        if (preferenceVO != null && preferenceVO.length > 0)
        {
            return preferenceVO[0];
        }
        else
        {
            return null;
        }
    }

    public void compose(PreferenceVO preferenceVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);
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
}
