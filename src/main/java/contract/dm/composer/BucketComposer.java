package contract.dm.composer;

import edit.common.vo.BucketAllocationVO;
import edit.common.vo.BucketVO;
import edit.common.vo.InvestmentVO;
import edit.common.vo.AnnualizedSubBucketVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 7, 2003
 * Time: 3:51:28 PM
 * To change this template use Options | File Templates.
 */
public class BucketComposer extends Composer
{
    private CRUD crud;

    private List voInclusionList;

    public BucketComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public BucketVO compose(long bucketPK) throws Exception
    {
        BucketVO bucketVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            bucketVO = (BucketVO) crud.retrieveVOFromDB(BucketVO.class, bucketPK);

            if (bucketVO != null) {

                compose(bucketVO);
            }
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

        return bucketVO;
    }

    public void compose(BucketVO bucketVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(BucketAllocationVO.class)) appendBucketAllocationVO(bucketVO);

            if (voInclusionList.contains(AnnualizedSubBucketVO.class)) appendAnnualizedSubBucketVO(bucketVO);

            if (voInclusionList.contains(InvestmentVO.class)) associateInvestmentVO(bucketVO);
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

    private void appendBucketAllocationVO(BucketVO bucketVO) throws Exception
    {
        voInclusionList.remove(BucketVO.class);

        BucketAllocationVO[] bucketAllocationVO = (BucketAllocationVO[]) crud.retrieveVOFromDB(BucketAllocationVO.class, BucketVO.class, bucketVO.getBucketPK());

        if (bucketAllocationVO != null)
        {
            bucketVO.setBucketAllocationVO(bucketAllocationVO);

            for (int i = 0; i < bucketAllocationVO.length; i++)
            {
                bucketAllocationVO[i].setParentVO(BucketVO.class, bucketVO);
            }
        }

        voInclusionList.add(BucketVO.class);
    }

    private void appendAnnualizedSubBucketVO(BucketVO bucketVO) throws Exception
    {
        voInclusionList.remove(BucketVO.class);

        AnnualizedSubBucketVO[] annualizedSubBucketVO = (AnnualizedSubBucketVO[]) crud.retrieveVOFromDB(AnnualizedSubBucketVO.class, BucketVO.class, bucketVO.getBucketPK());

        if (annualizedSubBucketVO != null)
        {
            bucketVO.setAnnualizedSubBucketVO(annualizedSubBucketVO);

            for (int i = 0; i < annualizedSubBucketVO.length; i++)
            {
                annualizedSubBucketVO[i].setParentVO(BucketVO.class, bucketVO);
            }
        }

        voInclusionList.add(BucketVO.class);
    }

    private void associateInvestmentVO(BucketVO bucketVO) throws Exception
    {
        voInclusionList.remove(BucketVO.class);

        InvestmentComposer composer = new InvestmentComposer(voInclusionList);

        InvestmentVO investmentVO = composer.compose(bucketVO.getInvestmentFK());

        bucketVO.setParentVO(InvestmentVO.class, investmentVO);

        investmentVO.addBucketVO(bucketVO);

        voInclusionList.add(BucketVO.class);
    }
}
