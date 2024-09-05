/**

 * User: dlataill

 * Date: Nov 12, 2004

 * Time: 11:13:53 AM

 *

 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved

 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is

 * subject to the license agreement. 

 */
package event.dm.composer;

import edit.services.db.Composer;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.common.vo.*;

import java.util.List;

import contract.dm.composer.InvestmentComposer;
import contract.dm.composer.InvestmentAllocationComposer;

/**
 * User: dlataill
 * Date: Nov 12, 2004
 * Time: 11:13:53 AM
 * <p/>
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
public class InvestmentAllocationOverrideComposer extends Composer
{
    private List voInclusionList;
    private CRUD crud;

    public InvestmentAllocationOverrideComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public InvestmentAllocationOverrideVO compose(InvestmentAllocationOverrideVO investmentAllocationOverrideVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(ContractSetupVO.class)) associateContractSetupVO(investmentAllocationOverrideVO);
            if (voInclusionList.contains(InvestmentVO.class)) associateInvestmentVO(investmentAllocationOverrideVO);
            if (voInclusionList.contains(InvestmentAllocationVO.class)) associateInvestmentAllocationVO(investmentAllocationOverrideVO);
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

        return investmentAllocationOverrideVO;
    }

    private void associateContractSetupVO(InvestmentAllocationOverrideVO investmentAllocationOverrideVO) throws Exception
    {
        voInclusionList.remove(InvestmentAllocationOverrideVO.class);

        ContractSetupVO contractSetupVO = new ContractSetupComposer(voInclusionList).compose(investmentAllocationOverrideVO.getContractSetupFK());

        investmentAllocationOverrideVO.setParentVO(ContractSetupVO.class, contractSetupVO);

        contractSetupVO.addInvestmentAllocationOverrideVO(investmentAllocationOverrideVO);

        voInclusionList.add(InvestmentAllocationOverrideVO.class);
    }

    private void associateInvestmentVO(InvestmentAllocationOverrideVO investmentAllocationOverrideVO) throws Exception
    {
        voInclusionList.remove(InvestmentAllocationOverrideVO.class);

        InvestmentVO investmentVO = new InvestmentComposer(voInclusionList).compose(investmentAllocationOverrideVO.getInvestmentFK());

        investmentAllocationOverrideVO.setParentVO(InvestmentVO.class, investmentVO);

        investmentVO.addInvestmentAllocationOverrideVO(investmentAllocationOverrideVO);

        voInclusionList.add(InvestmentAllocationOverrideVO.class);
    }

    private void associateInvestmentAllocationVO(InvestmentAllocationOverrideVO investmentAllocationOverrideVO) throws Exception
    {
        voInclusionList.remove(InvestmentAllocationOverrideVO.class);

        InvestmentAllocationVO investmentAllocationVO = new InvestmentAllocationComposer(voInclusionList).compose(investmentAllocationOverrideVO.getInvestmentAllocationFK());

        investmentAllocationOverrideVO.setParentVO(InvestmentAllocationVO.class, investmentAllocationVO);

        investmentAllocationVO.addInvestmentAllocationOverrideVO(investmentAllocationOverrideVO);

        voInclusionList.add(InvestmentAllocationOverrideVO.class);
    }
}
