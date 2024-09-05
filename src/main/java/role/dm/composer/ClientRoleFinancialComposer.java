package role.dm.composer;

import edit.common.vo.ClientRoleFinancialVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 6, 2003
 * Time: 1:07:19 PM
 * To change this template use Options | File Templates.
 */
public class ClientRoleFinancialComposer extends Composer
{
    private CRUD crud;

    private List voInclusionList;

    public ClientRoleFinancialComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public ClientRoleFinancialVO composeClientRoleFinancialByClientRoleFK(long clientRoleFK) throws Exception
    {
        ClientRoleFinancialVO[] clientRoleFinancialVO = role.dm.dao.DAOFactory.getClientRoleFinancialDAO().findByClientRolePK(clientRoleFK);
        if (clientRoleFinancialVO != null && clientRoleFinancialVO.length > 0)
        {
            compose(clientRoleFinancialVO[0]);
            return clientRoleFinancialVO[0];
        }

        return null;
    }

    public ClientRoleFinancialVO compose(long clientRoleFinancialPK) throws Exception
    {
        ClientRoleFinancialVO clientRoleFinancialVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            clientRoleFinancialVO = (ClientRoleFinancialVO) crud.retrieveVOFromDB(ClientRoleFinancialVO.class, clientRoleFinancialPK);

            compose(clientRoleFinancialVO);
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

        return clientRoleFinancialVO;
    }

    public void compose(ClientRoleFinancialVO clientRoleFinancialVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);
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
    }
}
