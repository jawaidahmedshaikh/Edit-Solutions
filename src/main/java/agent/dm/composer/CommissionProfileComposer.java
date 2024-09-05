/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 17, 2003
 * Time: 2:30:29 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package agent.dm.composer;

import edit.common.vo.CommissionProfileVO;
import edit.common.vo.PlacedAgentCommissionProfileVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

public class CommissionProfileComposer extends Composer
{
    private List voInclusionList;

    private CRUD crud;

    public CommissionProfileComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public CommissionProfileVO compose(long commissionProfilePK) throws Exception
    {
        CommissionProfileVO commissionProfileVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            commissionProfileVO = (CommissionProfileVO) crud.retrieveVOFromDB(CommissionProfileVO.class, commissionProfilePK);

            compose(commissionProfileVO);
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

        return commissionProfileVO;
    }

    public CommissionProfileVO compose(CommissionProfileVO commissionProfileVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(PlacedAgentCommissionProfileVO.class)) appendPlacedAgentCommissionProfileVO(commissionProfileVO);
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

        return commissionProfileVO;
    }

    private void appendPlacedAgentCommissionProfileVO(CommissionProfileVO commissionProfileVO) throws Exception
    {
        voInclusionList.remove(CommissionProfileVO.class);

        PlacedAgentCommissionProfileVO[] placedAgentCommissionProfileVO = (PlacedAgentCommissionProfileVO[]) crud.retrieveVOFromDB(PlacedAgentCommissionProfileVO.class, CommissionProfileVO.class, commissionProfileVO.getCommissionProfilePK());

        if (placedAgentCommissionProfileVO != null)
        {
            commissionProfileVO.setPlacedAgentCommissionProfileVO(placedAgentCommissionProfileVO);

            for (int i = 0; i < placedAgentCommissionProfileVO.length; i++)
            {
                placedAgentCommissionProfileVO[i].setParentVO(CommissionProfileVO.class, commissionProfileVO);
            }
        }

        voInclusionList.add(CommissionProfileVO.class);
    }
}
