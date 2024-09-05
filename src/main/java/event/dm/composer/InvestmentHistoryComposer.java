/**
 * User: dlataill
 * Date: Feb 4, 2005
 * Time: 8:56:01 AM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package event.dm.composer;

import edit.services.db.Composer;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.services.db.CRUD;
import edit.common.vo.*;

import java.util.List;

import contract.dm.composer.InvestmentComposer;

public class InvestmentHistoryComposer extends Composer
{
    private List voInclusionList;

    private CRUD crud;

    public InvestmentHistoryComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public InvestmentHistoryVO compose(long investmentHistoryPK)
    {
        InvestmentHistoryVO investmentHistoryVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            investmentHistoryVO = (InvestmentHistoryVO) crud.retrieveVOFromDB(InvestmentHistoryVO.class, investmentHistoryPK);

            compose(investmentHistoryVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return investmentHistoryVO;
    }

    public void compose(InvestmentHistoryVO investmentHistoryVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(InvestmentVO.class)) associateInvestmentVO(investmentHistoryVO);
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

    private void associateInvestmentVO(InvestmentHistoryVO investmentHistoryVO) throws Exception
    {
        voInclusionList.remove(InvestmentHistoryVO.class);

        InvestmentVO investmentVO = new InvestmentComposer(voInclusionList).compose(investmentHistoryVO.getInvestmentFK());

        investmentHistoryVO.setParentVO(InvestmentVO.class, investmentVO);

        voInclusionList.add(InvestmentHistoryVO.class);
    }
}
