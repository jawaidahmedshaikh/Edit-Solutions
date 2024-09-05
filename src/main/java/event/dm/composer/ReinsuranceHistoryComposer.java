/**
 * User: dlataill
 * Date: Sep 26, 2005
 * Time: 2:18:40 PM
 * <p/>
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package event.dm.composer;

import edit.services.db.Composer;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.common.vo.*;

import java.util.List;

public class ReinsuranceHistoryComposer extends Composer
{
    private List voInclusionList;

    private CRUD crud;

    public ReinsuranceHistoryComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public ReinsuranceHistoryVO compose(long reinsuranceHistoryPK) throws Exception
    {
        ReinsuranceHistoryVO reinsuranceHistoryVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            reinsuranceHistoryVO = (ReinsuranceHistoryVO) crud.retrieveVOFromDB(ReinsuranceHistoryVO.class, reinsuranceHistoryPK);

            compose(reinsuranceHistoryVO);
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

        return reinsuranceHistoryVO;
    }

    public void compose(ReinsuranceHistoryVO reinsuranceHistoryVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(EDITTrxHistoryVO.class)) associateEDITTrxHistoryVO(reinsuranceHistoryVO);

            if (voInclusionList.contains(ContractTreatyVO.class)) associateContractTreatyVO(reinsuranceHistoryVO);
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

    /**
     * Associate the EDITTrxHistory info with the reinsurance history.  If the
     * data is cached, use that.
     * @param reinsuranceHistoryVO
     * @throws Exception
     */
    private void associateEDITTrxHistoryVO(ReinsuranceHistoryVO reinsuranceHistoryVO) throws Exception
    {
        voInclusionList.remove(ReinsuranceHistoryVO.class);
        EDITTrxHistoryVO editTrxHistoryVO = new EDITTrxHistoryComposer(voInclusionList).compose(reinsuranceHistoryVO.getEDITTrxHistoryFK());
        reinsuranceHistoryVO.setParentVO(EDITTrxHistoryVO.class, editTrxHistoryVO);
        editTrxHistoryVO.addReinsuranceHistoryVO(reinsuranceHistoryVO);
        voInclusionList.add(ReinsuranceHistoryVO.class);
    }

    /**
     * Associate the ContractTreaty info with the reinsurance history.
     * @param reinsuranceHistoryVO
     * @throws Exception
     */
    private void associateContractTreatyVO(ReinsuranceHistoryVO reinsuranceHistoryVO) throws Exception
    {
        voInclusionList.remove(ReinsuranceHistoryVO.class);
        try
        {
            ContractTreatyVO contractTreatyVO = (ContractTreatyVO) crud.retrieveVOFromDB(ContractTreatyVO.class, reinsuranceHistoryVO.getContractTreatyFK());

            composeContractTreaty(contractTreatyVO);

            reinsuranceHistoryVO.setParentVO(ContractTreatyVO.class, contractTreatyVO);
            contractTreatyVO.addReinsuranceHistoryVO(reinsuranceHistoryVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            voInclusionList.add(ReinsuranceHistoryVO.class);

            if (crud != null) crud.close();

            crud = null;
        }
    }

    private void composeContractTreaty(ContractTreatyVO contractTreatyVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(TreatyVO.class)) associateTreatyVO(contractTreatyVO);
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

    /**
     * Associate the Treaty info with the contract treaty.
     * @param contractTreatyVO
     * @throws Exception
     */
    private void associateTreatyVO(ContractTreatyVO contractTreatyVO) throws Exception
    {
        voInclusionList.remove(ContractTreatyVO.class);
        try
        {
            TreatyVO treatyVO = (TreatyVO) crud.retrieveVOFromDB(TreatyVO.class, contractTreatyVO.getTreatyFK());

            composeTreaty(treatyVO);

            contractTreatyVO.setParentVO(TreatyVO.class, treatyVO);
            treatyVO.addContractTreatyVO(contractTreatyVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            voInclusionList.add(ContractTreatyVO.class);

            if (crud != null) crud.close();

            crud = null;
        }
    }

    private void composeTreaty(TreatyVO treatyVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(TreatyGroupVO.class)) associateTreatyGroupVO(treatyVO);
            if (voInclusionList.contains(ReinsurerVO.class)) associateReinsurerVO(treatyVO);
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

    /**
     * Associate the TreatyGroup info with the treaty.
     * @param treatyVO
     * @throws Exception
     */
    private void associateTreatyGroupVO(TreatyVO treatyVO) throws Exception
    {
        voInclusionList.remove(TreatyVO.class);
        try
        {
            TreatyGroupVO treatyGroupVO = (TreatyGroupVO) crud.retrieveVOFromDB(TreatyGroupVO.class, treatyVO.getTreatyGroupFK());

            treatyVO.setParentVO(TreatyGroupVO.class, treatyGroupVO);
            treatyGroupVO.addTreatyVO(treatyVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            voInclusionList.add(TreatyVO.class);
        }
    }

    /**
     * Associate the Reinsurer info with the Treaty.
     * @param treatyVO
     * @throws Exception
     */
    private void associateReinsurerVO(TreatyVO treatyVO) throws Exception
    {
        voInclusionList.remove(TreatyVO.class);
        try
        {
            ReinsurerVO reinsurerVO = (ReinsurerVO) crud.retrieveVOFromDB(ReinsurerVO.class, treatyVO.getReinsurerFK());

            treatyVO.setParentVO(ReinsurerVO.class, reinsurerVO);
            reinsurerVO.addTreatyVO(treatyVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            voInclusionList.add(TreatyVO.class);
        }
    }
}
