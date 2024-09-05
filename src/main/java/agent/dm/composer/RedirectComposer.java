/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 17, 2003
 * Time: 2:30:29 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package agent.dm.composer;

import edit.common.vo.ClientRoleVO;
import edit.common.vo.RedirectVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;
import role.dm.composer.ClientRoleComposer;

import java.util.List;

public class RedirectComposer extends Composer
{
    private List voInclusionList;

    CRUD crud = null;

    public RedirectComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public RedirectVO compose(long redirectPK) throws Exception
    {
        RedirectVO redirectVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            redirectVO = (RedirectVO) crud.retrieveVOFromDB(RedirectVO.class, redirectPK);

            compose(redirectVO);
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

        return redirectVO;
    }

    public void compose(RedirectVO redirectVO)
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(ClientRoleVO.class)) associateClientRoleVO(redirectVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }
    }

    private void associateClientRoleVO(RedirectVO redirectVO) throws Exception
    {
        if (redirectVO.getClientRoleFK() != 0)
        {
            voInclusionList.remove(RedirectVO.class);

            ClientRoleVO clientRoleVO = new ClientRoleComposer(voInclusionList).compose(redirectVO.getClientRoleFK());

            if (clientRoleVO != null)
            {
                redirectVO.setParentVO(ClientRoleVO.class, clientRoleVO);

                clientRoleVO.addRedirectVO(redirectVO);
            }

            voInclusionList.add(RedirectVO.class);
        }
    }
}
