package agent.dm.composer;

import edit.common.vo.CommissionContractVO;
import edit.common.vo.CommissionLevelVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Sep 25, 2003
 * Time: 2:05:57 PM
 * To change this template use Options | File Templates.
 */
public class CommissionContractComposer extends Composer
{
    private List voInclusionList;

    private CRUD crud;

    public CommissionContractComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public CommissionContractVO compose(long commissionContractPK) throws Exception
    {
        CommissionContractVO commissionContractVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            commissionContractVO = (CommissionContractVO) crud.retrieveVOFromDB(CommissionContractVO.class, commissionContractPK);

            compose(commissionContractVO);
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

        return commissionContractVO;
    }

    public void compose(CommissionContractVO commissionContractVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(CommissionLevelVO.class)) appendCommissionLevelVO(commissionContractVO);

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

    private void appendCommissionLevelVO(CommissionContractVO commissionContractVO) throws Exception
    {
        voInclusionList.remove(CommissionContractVO.class);

        CommissionLevelVO[] commissionLevelVO = (CommissionLevelVO[]) crud.retrieveVOFromDB(CommissionLevelVO.class, CommissionContractVO.class, commissionContractVO.getCommissionContractPK());

        if (commissionLevelVO != null)
        {
            commissionContractVO.setCommissionLevelVO(commissionLevelVO);

            for (int i = 0; i < commissionLevelVO.length; i++)
            {
                new CommissionLevelComposer(voInclusionList).compose(commissionLevelVO[i]);

                commissionLevelVO[i].setParentVO(CommissionContractVO.class, commissionContractVO);
            }
        }

        voInclusionList.add(CommissionContractVO.class);
    }
}
