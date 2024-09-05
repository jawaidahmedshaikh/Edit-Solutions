/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 17, 2003
 * Time: 2:42:13 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package agent.dm.composer;

import edit.common.vo.CommissionContractVO;
import edit.common.vo.CommissionLevelDescriptionVO;
import edit.common.vo.CommissionLevelVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

public class CommissionLevelComposer extends Composer
{
    private List voInclusionList;

    private CRUD crud;

    public CommissionLevelComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public CommissionLevelVO compose(long commissionLevelPK) throws Exception
    {
        CommissionLevelVO commissionLevelVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            commissionLevelVO = (CommissionLevelVO) crud.retrieveVOFromDB(CommissionLevelVO.class, commissionLevelPK);

            compose(commissionLevelVO);
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

        return commissionLevelVO;
    }

    public void compose(CommissionLevelVO commissionLevelVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(CommissionLevelDescriptionVO.class)) associateCommissionLevelDescriptionVO(commissionLevelVO);
            if (voInclusionList.contains(CommissionContractVO.class)) associateCommissionContractVO(commissionLevelVO);
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

    private void associateCommissionContractVO(CommissionLevelVO commissionLevelVO) throws Exception
    {
        if (commissionLevelVO.getCommissionContractFK() != 0)
        {
            voInclusionList.remove(CommissionLevelVO.class);

            CommissionContractVO commissionContractVO = new CommissionContractComposer(voInclusionList).compose(commissionLevelVO.getCommissionContractFK());

            if (commissionContractVO != null)
            {
                commissionLevelVO.setParentVO(CommissionContractVO.class, commissionContractVO);

                commissionContractVO.addCommissionLevelVO(commissionLevelVO);
            }

            voInclusionList.add(CommissionLevelVO.class);
        }
    }

    private void associateCommissionLevelDescriptionVO(CommissionLevelVO commissionLevelVO) throws Exception
    {
        if (commissionLevelVO.getCommissionLevelDescriptionFK() != 0)
        {
            voInclusionList.remove(CommissionLevelVO.class);

            CommissionLevelDescriptionVO commissionLevelDescriptionVO = (CommissionLevelDescriptionVO) crud.retrieveVOFromDB(CommissionLevelDescriptionVO.class, commissionLevelVO.getCommissionLevelDescriptionFK());

            if (commissionLevelDescriptionVO != null)
            {
                commissionLevelDescriptionVO.addCommissionLevelVO(commissionLevelVO);

                commissionLevelVO.setParentVO(CommissionLevelDescriptionVO.class, commissionLevelDescriptionVO);
            }

            voInclusionList.add(CommissionLevelVO.class);
        }
    }
}
