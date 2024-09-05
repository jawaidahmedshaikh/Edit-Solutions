package contract.dm.composer;

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
public class RequirementComposer extends Composer
{
    private List voInclusionList;

    private CRUD crud;

    public RequirementComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public RequirementVO compose(long requirementPK) throws Exception
    {
        RequirementVO requirementVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            requirementVO = (RequirementVO) crud.retrieveVOFromDB(RequirementVO.class, requirementPK);

            compose(requirementVO);
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

        return requirementVO;
    }

    public void compose(RequirementVO requirementVO) throws Exception
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
