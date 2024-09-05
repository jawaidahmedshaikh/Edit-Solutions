/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 17, 2003
 * Time: 2:30:29 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package event.dm.composer;

import agent.dm.composer.PlacedAgentComposer;
import edit.common.vo.CommissionHistoryVO;
import edit.common.vo.EDITTrxHistoryVO;
import edit.common.vo.PlacedAgentVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;
import event.dm.dao.DAOFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommissionHistoryComposer extends Composer
{
    private List voInclusionList;

    private CRUD crud;

    /**
     * When reusing this instance with the <CODE>associatePlacedAgentVO</CODE>
     * method, this Map caches the PlacedAgent data that can be reused.
     * If you are not reusing the CommissionHistoryComposer instance,
     * this Map will make no difference to that method or any other.
     */
    private Map mapComposedPlacedAgents = new HashMap();

    public CommissionHistoryComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public CommissionHistoryVO compose(long commissionHistoryPK) throws Exception
    {
        CommissionHistoryVO commissionHistoryVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            commissionHistoryVO = (CommissionHistoryVO) crud.retrieveVOFromDB(CommissionHistoryVO.class, commissionHistoryPK);

            compose(commissionHistoryVO);
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

        return commissionHistoryVO;
    }

    public CommissionHistoryVO[] composeForYTDTotals(long placedAgentPK, String startingDate, String endingDate, String[] trxTypes)
    {
        CommissionHistoryVO[] commissionHistoryVOs = null;

        try
        {
            commissionHistoryVOs = DAOFactory.getCommissionHistoryDAO().findByProcessDateCheckTrx(placedAgentPK, startingDate, endingDate, trxTypes);
            if (commissionHistoryVOs != null)
            {
                for (int c = 0; c < commissionHistoryVOs.length; c++)
                {
                    compose(commissionHistoryVOs[c]);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return commissionHistoryVOs;
    }

    public void compose(CommissionHistoryVO commissionHistoryVO)
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(EDITTrxHistoryVO.class)) associateEDITTrxHistoryVO(commissionHistoryVO);
            if (voInclusionList.contains(PlacedAgentVO.class)) associatePlacedAgentVO(commissionHistoryVO);
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

    /**
     * Associate the EDITTrxHistory info with the commission history.  If the
     * data is cached, use that.
     * @param commissionHistoryVO
     * @throws Exception
     */
    private void associateEDITTrxHistoryVO(CommissionHistoryVO commissionHistoryVO) throws Exception
    {
        voInclusionList.remove(CommissionHistoryVO.class);
        EDITTrxHistoryVO editTrxHistoryVO = new EDITTrxHistoryComposer(voInclusionList).compose(commissionHistoryVO.getEDITTrxHistoryFK());
        commissionHistoryVO.setParentVO(EDITTrxHistoryVO.class, editTrxHistoryVO);
        editTrxHistoryVO.addCommissionHistoryVO(commissionHistoryVO);
        voInclusionList.add(CommissionHistoryVO.class);
    }

    /**
     * Associate the PlacedAgent info with the commission history.  If the
     * data is cached, use that.
     * @param commissionHistoryVO
     * @throws Exception
     */
    private void associatePlacedAgentVO(CommissionHistoryVO commissionHistoryVO) throws Exception
    {
        PlacedAgentVO placedAgentVO = null;
        Long placedAgentKey = new Long(commissionHistoryVO.getPlacedAgentFK());

        if (mapComposedPlacedAgents.containsKey(placedAgentKey))
        {
            // NO NEED TO CLONE THIS ONE SINCE WE DON'T CHANGE IT
            placedAgentVO = (PlacedAgentVO) mapComposedPlacedAgents.get(placedAgentKey);
            commissionHistoryVO.setParentVO(PlacedAgentVO.class, placedAgentVO);
        }
        else
        {
            voInclusionList.remove(CommissionHistoryVO.class);
            placedAgentVO = new PlacedAgentComposer(voInclusionList).
                    compose(commissionHistoryVO.getPlacedAgentFK());

            // THIS WAS COMMENTED FROM BEFORE - KEEP FOR REFERENCE.
            // PlacedAgentVO placedAgentVO = (PlacedAgentVO) new VOHelper().compose(
            //  commissionHistoryVO.getPlacedAgentFK(), PlacedAgentVO.class, voInclusionList);

            commissionHistoryVO.setParentVO(PlacedAgentVO.class, placedAgentVO);
            voInclusionList.add(CommissionHistoryVO.class);

            mapComposedPlacedAgents.put(placedAgentKey, placedAgentVO);
        }
    }
}
