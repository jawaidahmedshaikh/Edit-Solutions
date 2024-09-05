/*
 * User: dlataill
 * Date: Nov 18, 2004
 * Time: 1:39:52 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package contract.dm.composer;

import edit.services.db.Composer;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.common.vo.InvestmentAllocationVO;
import edit.common.vo.InvestmentVO;
import edit.common.vo.FilteredFundVO;

import java.util.List;

public class InvestmentAllocationComposer  extends Composer
{
    private List voInclusionList;

    private CRUD crud;

    public InvestmentAllocationComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public InvestmentAllocationVO compose(long investmentAllocationPK) throws Exception
    {
        InvestmentAllocationVO investmentAllocationVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            investmentAllocationVO = (InvestmentAllocationVO) crud.retrieveVOFromDB(InvestmentAllocationVO.class, investmentAllocationPK);

            compose(investmentAllocationVO);
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

        return investmentAllocationVO;
    }

    public void compose(InvestmentAllocationVO investmentAllocationVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(FilteredFundVO.class)) associateInvestmentVO(investmentAllocationVO);
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

    private void associateInvestmentVO(InvestmentAllocationVO investmentAllocationVO) throws Exception
    {
        voInclusionList.remove(InvestmentAllocationVO.class);

        InvestmentVO investmentVO = new InvestmentComposer(voInclusionList).compose(investmentAllocationVO.getInvestmentFK());

        investmentAllocationVO.setParentVO(InvestmentVO.class, investmentVO);

        voInclusionList.add(InvestmentAllocationVO.class);
    }
}
