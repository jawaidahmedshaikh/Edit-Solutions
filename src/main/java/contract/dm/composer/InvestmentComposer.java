package contract.dm.composer;

import edit.common.vo.*;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.dm.composer.FilteredFundComposer;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Aug 4, 2003
 * Time: 2:16:36 PM
 * To change this template use Options | File Templates.
 */
public class InvestmentComposer extends Composer
{
    private List voInclusionList;

    private InvestmentAllocationVO[] investmentAllocationVO;

    private BucketVO[] bucketVO;

    private Map bucketAllocationVO;

    private CRUD crud;

    public InvestmentComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
        this.bucketAllocationVO = new HashMap();
    }

    public InvestmentVO compose(long investmentPK) throws Exception
    {
        InvestmentVO investmentVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            investmentVO = (InvestmentVO) crud.retrieveVOFromDB(InvestmentVO.class, investmentPK);

            compose(investmentVO);
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

        return investmentVO;
    }

    public void compose(InvestmentVO investmentVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(InvestmentAllocationVO.class)) appendInvestmentAllocationVO(investmentVO);

            if (voInclusionList.contains(BucketVO.class)) appendBucketVO(investmentVO);

            if (voInclusionList.contains(FilteredFundVO.class)) associateFilteredFundVO(investmentVO);

            if (voInclusionList.contains(SegmentVO.class)) associateSegmentVO(investmentVO);
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

    private void associateFilteredFundVO(InvestmentVO investmentVO) throws Exception
    {
        voInclusionList.remove(InvestmentVO.class);

        FilteredFundVO filteredFundVO = new FilteredFundComposer(voInclusionList).compose(investmentVO.getFilteredFundFK());

        investmentVO.setParentVO(FilteredFundVO.class, filteredFundVO);

        voInclusionList.add(InvestmentVO.class);
    }

    private void associateSegmentVO(InvestmentVO investmentVO) throws Exception
    {
        voInclusionList.remove(InvestmentVO.class);

        SegmentVO segmentVO = new SegmentComposer(voInclusionList).compose(investmentVO.getSegmentFK());

        investmentVO.setParentVO(SegmentVO.class, segmentVO);

        voInclusionList.add(InvestmentVO.class);
    }

    public void substituteInvestmentAllocationVO(InvestmentAllocationVO[] investmentAllocationVO)
    {
        this.investmentAllocationVO = investmentAllocationVO;
    }

    public void substituteBucketVO(BucketVO[] bucketVO)
    {
        this.bucketVO = bucketVO;
    }

    public void substituteBucketAllocationVO(BucketAllocationVO[] bucketAllocationVO) throws Exception
    {
        Method parentFKMethod = BucketAllocationVO.class.getMethod("getBucketFK", null);

        super.populateSubstitutionHashtable(this.bucketAllocationVO, bucketAllocationVO, parentFKMethod);
    }

    private void appendInvestmentAllocationVO(InvestmentVO investmentVO) throws Exception
    {
        voInclusionList.remove(InvestmentVO.class);

        if (investmentAllocationVO == null) // No overrides
        {
            investmentAllocationVO = (InvestmentAllocationVO[]) crud.retrieveVOFromDB(InvestmentAllocationVO.class, InvestmentVO.class, investmentVO.getInvestmentPK());
        }

        if (investmentAllocationVO != null) investmentVO.setInvestmentAllocationVO(investmentAllocationVO);

        voInclusionList.add(InvestmentVO.class);
    }

    private void appendBucketVO(InvestmentVO investmentVO) throws Exception
    {
        voInclusionList.remove(InvestmentVO.class);

        if (bucketVO == null) // No overrides
        {
            bucketVO = (BucketVO[]) crud.retrieveVOFromDB(BucketVO.class, InvestmentVO.class, investmentVO.getInvestmentPK());
        }

        if (bucketVO != null)
        {
            for (int i = 0; i < bucketVO.length; i++)
            {
                bucketVO[i].setParentVO(InvestmentVO.class, investmentVO);
                new BucketComposer(voInclusionList).compose(bucketVO[i]);
                investmentVO.addBucketVO(bucketVO[i]);
            }
        }

        voInclusionList.add(InvestmentVO.class);
    }
}
