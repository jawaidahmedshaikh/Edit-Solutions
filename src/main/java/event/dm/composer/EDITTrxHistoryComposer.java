package event.dm.composer;

import edit.common.vo.*;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;
import event.dm.dao.DAOFactory;
import event.SegmentHistory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import contract.dm.composer.BucketComposer;
import reinsurance.ReinsuranceHistory;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Aug 15, 2003
 * Time: 2:45:44 PM
 * To change this template use Options | File Templates.
 */
public class EDITTrxHistoryComposer extends Composer
{
    private List voInclusionList;
    private FinancialHistoryVO[] financialHistoryVO;
    private WithholdingHistoryVO[] withholdingHistoryVO;
    private ChargeHistoryVO[] chargeHistoryVO;
    private InSuspenseVO[] inSuspenseVO;
    private BucketHistoryVO[] bucketHistoryVO;
    private Map bucketChargeHistoryVO;
    private CommissionHistoryVO[] commissionHistoryVO;
    private InvestmentHistoryVO[] investmentHistoryVO;

    private CRUD crud;

    public EDITTrxHistoryComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
        this.bucketChargeHistoryVO = new HashMap();
    }

    public EDITTrxHistoryVO compose(long editTrxHistoryPK)
    {
        EDITTrxHistoryVO editTrxHistoryVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            editTrxHistoryVO = (EDITTrxHistoryVO) crud.retrieveVOFromDB(EDITTrxHistoryVO.class, editTrxHistoryPK);

            compose(editTrxHistoryVO);
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

        return editTrxHistoryVO;
    }

    public EDITTrxHistoryVO compose(String quoteDate, long segmentFK) throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVO = DAOFactory.getEDITTrxHistoryDAO().findEDITTrxHistoryVOClosestToQuoteDate(quoteDate, segmentFK);

        if (editTrxHistoryVO != null && editTrxHistoryVO.length > 0)
        {
            this.compose(editTrxHistoryVO[0]);
            return editTrxHistoryVO[0];
        }
        else
        {
            return null;
        }
    }

    public void compose(EDITTrxHistoryVO editTrxHistoryVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(FinancialHistoryVO.class)) appendFinancialgHistoryVO(editTrxHistoryVO);

            if (voInclusionList.contains(WithholdingHistoryVO.class)) appendWithholdingHistoryVO(editTrxHistoryVO);

            if (voInclusionList.contains(ChargeHistoryVO.class)) appendChargeHistoryVO(editTrxHistoryVO);

            if (voInclusionList.contains(InSuspenseVO.class)) appendInSuspenseVO(editTrxHistoryVO);

            if (voInclusionList.contains(BucketHistoryVO.class)) appendBucketHistoryVO(editTrxHistoryVO);

            if (voInclusionList.contains(CommissionHistoryVO.class)) appendCommissionHistoryVO(editTrxHistoryVO);

            if (voInclusionList.contains(InvestmentHistoryVO.class)) appendInvestmentHistoryVO(editTrxHistoryVO);

            if (voInclusionList.contains(EDITTrxVO.class)) associateEDITTrxVO(editTrxHistoryVO);

            if (voInclusionList.contains(ReinsuranceHistoryVO.class)) appendReinsuranceHistoryVO(editTrxHistoryVO);

            if (voInclusionList.contains(SegmentHistoryVO.class)) appendSegmentHistoryVO(editTrxHistoryVO);
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

    private void associateEDITTrxVO(EDITTrxHistoryVO editTrxHistoryVO) throws Exception
    {
        voInclusionList.remove(EDITTrxHistoryVO.class);

        EDITTrxVO editTrxVO = new EDITTrxComposer(voInclusionList).compose(editTrxHistoryVO.getEDITTrxFK());

        editTrxHistoryVO.setParentVO(EDITTrxVO.class, editTrxVO);

        voInclusionList.add(EDITTrxHistoryVO.class);
    }

    public void substituteFinancialHistoryVO(FinancialHistoryVO[] financialHistoryVO)
     {
         this.financialHistoryVO = financialHistoryVO;
     }

    public void substituteWithholdingHistoryVO(WithholdingHistoryVO[] withholdingHistoryVO)
    {
        this.withholdingHistoryVO = withholdingHistoryVO;
    }

    public void substituteChargeHistoryVO(ChargeHistoryVO[] chargeHistoryVO)
    {
        this.chargeHistoryVO = chargeHistoryVO;
    }

    public void substituteInSuspenseVO(InSuspenseVO[] inSuspenseVO)
    {
        this.inSuspenseVO = inSuspenseVO;
    }

    public void substituteBucketHistoryVO(BucketHistoryVO[] bucketHistoryVO)
    {
        this.bucketHistoryVO = bucketHistoryVO;
    }

    public void substituteBucketChargeHistoryVO(BucketChargeHistoryVO[] bucketChargeHistoryVO) throws Exception
    {
        Method parentFKMethod = BucketHistoryVO.class.getMethod("getBucketHistoryFK", null);

        super.populateSubstitutionHashtable(this.bucketChargeHistoryVO, bucketChargeHistoryVO, parentFKMethod);
    }

   private void appendFinancialgHistoryVO(EDITTrxHistoryVO editTrxHistoryVO) throws Exception
    {
        voInclusionList.remove(EDITTrxHistoryVO.class);

         if (financialHistoryVO == null)
        {
            financialHistoryVO = DAOFactory.getFinancialHistoryDAO().findByEditTrxHistoryPK(editTrxHistoryVO.getEDITTrxHistoryPK());
        }

        if (financialHistoryVO != null) editTrxHistoryVO.setFinancialHistoryVO(financialHistoryVO);

        voInclusionList.add(EDITTrxHistoryVO.class);
    }

    private void appendWithholdingHistoryVO(EDITTrxHistoryVO editTrxHistoryVO) throws Exception
    {
        voInclusionList.remove(EDITTrxHistoryVO.class);

        if (withholdingHistoryVO == null) // No overrides
        {
            withholdingHistoryVO = DAOFactory.getWithholdingHistoryDAO().findByEDITTrxHistoryPK(editTrxHistoryVO.getEDITTrxHistoryPK());
        }

        if (withholdingHistoryVO != null) editTrxHistoryVO.setWithholdingHistoryVO(withholdingHistoryVO);

        voInclusionList.add(EDITTrxHistoryVO.class);
    }

    private void appendChargeHistoryVO(EDITTrxHistoryVO editTrxHistoryVO) throws Exception
    {
        voInclusionList.remove(EDITTrxHistoryVO.class);

        if (chargeHistoryVO == null) // No overrides
        {
            chargeHistoryVO = DAOFactory.getChargeHistoryDAO().findByEDITTrxHistoryPK(editTrxHistoryVO.getEDITTrxHistoryPK());
        }

        if (chargeHistoryVO != null) editTrxHistoryVO.setChargeHistoryVO(chargeHistoryVO);

        voInclusionList.add(EDITTrxHistoryVO.class);
    }

    private void appendInSuspenseVO(EDITTrxHistoryVO editTrxHistoryVO) throws Exception
    {
        voInclusionList.remove(EDITTrxHistoryVO.class);

        if (inSuspenseVO == null) // No overrides
        {
            inSuspenseVO = DAOFactory.getInSuspenseDAO().findByEDITTrxHistoryPK(editTrxHistoryVO.getEDITTrxHistoryPK());
        }

        if (inSuspenseVO != null)
        {
            editTrxHistoryVO.setInSuspenseVO(inSuspenseVO);

            for (int i = 0; i < inSuspenseVO.length; i++)
            {
                if (voInclusionList.contains(SuspenseVO.class)) associateSuspenseVO(inSuspenseVO[i]);
            }
        }

        voInclusionList.add(EDITTrxHistoryVO.class);
    }

    private void associateSuspenseVO(InSuspenseVO inSuspenseVO) throws Exception
    {
        voInclusionList.remove(InSuspenseVO.class);

        SuspenseVO suspenseVO = (SuspenseVO) crud.retrieveVOFromDB(SuspenseVO.class, inSuspenseVO.getSuspenseFK());

        inSuspenseVO.setParentVO(SuspenseVO.class, suspenseVO);

        voInclusionList.add(InSuspenseVO.class);
    }

    private void appendBucketHistoryVO(EDITTrxHistoryVO editTrxHistoryVO) throws Exception
    {
        voInclusionList.remove(EDITTrxHistoryVO.class);

        if (bucketHistoryVO == null) // No overrides
        {
            bucketHistoryVO = event.dm.dao.DAOFactory.getBucketHistoryDAO().findByEditTrxHistoryPK(editTrxHistoryVO.getEDITTrxHistoryPK());
        }

        if (bucketHistoryVO != null)
        {
            editTrxHistoryVO.setBucketHistoryVO(bucketHistoryVO);

            for (int i = 0; i < bucketHistoryVO.length; i++)
            {
                if (voInclusionList.contains(BucketChargeHistoryVO.class)) appendBucketChargeHistoryVO(bucketHistoryVO[i]);

                if (voInclusionList.contains(BucketVO.class)) associateBucketVO(bucketHistoryVO[i]);
            }
        }

        voInclusionList.add(EDITTrxHistoryVO.class);
    }

    private void associateBucketVO(BucketHistoryVO bucketHistoryVO) throws Exception
    {
        voInclusionList.remove(BucketHistoryVO.class);

        BucketVO bucketVO = new BucketComposer(voInclusionList).compose(bucketHistoryVO.getBucketFK());

        if (bucketVO != null)
        {
            bucketHistoryVO.setParentVO(BucketVO.class, bucketVO);
        }

        voInclusionList.add(BucketHistoryVO.class);
    }

    private void appendBucketChargeHistoryVO(BucketHistoryVO bucketHistoryVO) throws Exception
    {
        voInclusionList.remove(BucketHistoryVO.class);

        BucketChargeHistoryVO[] bucketChargeHistoryVO = null;

        if (!this.bucketChargeHistoryVO.containsKey(new Long(bucketHistoryVO.getBucketHistoryPK())))
        {
            bucketChargeHistoryVO = (BucketChargeHistoryVO[]) crud.retrieveVOFromDB(BucketChargeHistoryVO.class, BucketHistoryVO.class, bucketHistoryVO.getBucketHistoryPK());
        }
        else
        {
            bucketChargeHistoryVO = (BucketChargeHistoryVO[]) retrieveFromSubstitutionHashtable(this.bucketChargeHistoryVO, BucketChargeHistoryVO.class, new Long(bucketHistoryVO.getBucketHistoryPK()));
        }

        if (bucketChargeHistoryVO != null) bucketHistoryVO.setBucketChargeHistoryVO(bucketChargeHistoryVO);

        voInclusionList.add(BucketHistoryVO.class);
    }

    private void appendCommissionHistoryVO(EDITTrxHistoryVO editTrxHistoryVO) throws Exception
    {
        voInclusionList.remove(EDITTrxHistoryVO.class);

        if (commissionHistoryVO == null)
        {
            commissionHistoryVO = DAOFactory.getCommissionHistoryDAO().findByEDITTrxHistoryPK(editTrxHistoryVO.getEDITTrxHistoryPK());
        }

        if (commissionHistoryVO != null)
        {
            editTrxHistoryVO.setCommissionHistoryVO(commissionHistoryVO);
        }

        voInclusionList.add(EDITTrxHistoryVO.class);
    }

    private void appendInvestmentHistoryVO(EDITTrxHistoryVO editTrxHistoryVO) throws Exception
    {
        voInclusionList.remove(EDITTrxHistoryVO.class);

        if (investmentHistoryVO == null)
        {
            investmentHistoryVO = DAOFactory.getInvestmentHistoryDAO().findByEDITTrxHistoryFK(editTrxHistoryVO.getEDITTrxHistoryPK());
        }


        if (investmentHistoryVO != null)
        {
            for (int i = 0; i < investmentHistoryVO.length; i++)
            {
                InvestmentHistoryComposer composer = new InvestmentHistoryComposer(voInclusionList);

                composer.compose(investmentHistoryVO[i]);
            }

            editTrxHistoryVO.setInvestmentHistoryVO(investmentHistoryVO);
        }

        voInclusionList.add(EDITTrxHistoryVO.class);
    }

    private void appendReinsuranceHistoryVO(EDITTrxHistoryVO editTrxHistoryVO) throws Exception
    {
        voInclusionList.remove(EDITTrxHistoryVO.class);

        ReinsuranceHistory[] reinsuranceHistory = ReinsuranceHistory.findBy_EDITTrxHistoryFK(editTrxHistoryVO.getEDITTrxHistoryPK());

        if (reinsuranceHistory != null)
        {
            for (int i = 0; i < reinsuranceHistory.length; i++)
            {
                ReinsuranceHistoryVO reinsuranceHistoryVO = (ReinsuranceHistoryVO) reinsuranceHistory[i].getVO();

                ReinsuranceHistoryComposer composer = new ReinsuranceHistoryComposer(voInclusionList);

                composer.compose(reinsuranceHistoryVO);

                editTrxHistoryVO.addReinsuranceHistoryVO(reinsuranceHistoryVO);
            }
        }

        voInclusionList.add(EDITTrxHistoryVO.class);
    }

    private void appendSegmentHistoryVO(EDITTrxHistoryVO editTrxHistoryVO) throws Exception
    {
        voInclusionList.remove(EDITTrxHistoryVO.class);

        SegmentHistory[] segmentHistory = SegmentHistory.findBy_EDITTrxHistoryFK(editTrxHistoryVO.getEDITTrxHistoryPK());

        if (segmentHistory != null)
        {
            for (int i = 0; i < segmentHistory.length; i++)
            {
                SegmentHistoryVO segmentHistoryVO = (SegmentHistoryVO) segmentHistory[i].getVO();

                editTrxHistoryVO.addSegmentHistoryVO(segmentHistoryVO);

                segmentHistoryVO.setParentVO(EDITTrxHistoryVO.class, editTrxHistoryVO);
            }
        }

        voInclusionList.add(EDITTrxHistoryVO.class);
    }
}