/**
 * User: dlataill
 * Date: Feb 18, 2005
 * Time: 2:17:03 PM
 * <p/>
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

public class OutSuspenseComposer extends Composer
{
    private CRUD crud = null;

    private List voInclusionList;

    public OutSuspenseComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public OutSuspenseVO compose(long outSuspensePK) throws Exception
    {
        OutSuspenseVO outSuspenseVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            outSuspenseVO = (OutSuspenseVO) crud.retrieveVOFromDB(OutSuspenseVO.class, outSuspensePK);

            if (outSuspenseVO != null) {

                compose(outSuspenseVO);
            }
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

        return outSuspenseVO;
    }

    public void compose(OutSuspenseVO outSuspenseVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(ContractSetupVO.class)) associateContractSetupVO(outSuspenseVO);

            if (voInclusionList.contains(SuspenseVO.class)) associateSuspenseVO(outSuspenseVO);
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

    private void associateContractSetupVO(OutSuspenseVO outSuspenseVO) throws Exception
    {
        voInclusionList.remove(OutSuspenseVO.class);

        ContractSetupVO contractSetupVO = (ContractSetupVO) crud.retrieveVOFromDB(ContractSetupVO.class, outSuspenseVO.getContractSetupFK());

        ContractSetupComposer composer = new ContractSetupComposer(voInclusionList);

        composer.compose(contractSetupVO);

        outSuspenseVO.setParentVO(ContractSetupVO.class, contractSetupVO);

        contractSetupVO.addOutSuspenseVO(outSuspenseVO);

        voInclusionList.add(OutSuspenseVO.class);
    }

    private void associateSuspenseVO(OutSuspenseVO outSuspenseVO) throws Exception
    {
        voInclusionList.remove(OutSuspenseVO.class);

        SuspenseVO suspenseVO = (SuspenseVO) crud.retrieveVOFromDB(SuspenseVO.class, outSuspenseVO.getSuspenseFK());

        outSuspenseVO.setParentVO(SuspenseVO.class, suspenseVO);

        suspenseVO.addOutSuspenseVO(outSuspenseVO);

        voInclusionList.add(OutSuspenseVO.class);
    }
}
