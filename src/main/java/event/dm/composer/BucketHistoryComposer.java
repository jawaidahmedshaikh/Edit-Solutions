package event.dm.composer;

import edit.services.db.Composer;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.common.vo.EDITTrxHistoryVO;
import edit.common.vo.BucketHistoryVO;
import edit.common.vo.BucketVO;

import java.util.List;

import contract.dm.composer.BucketComposer;

/**
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Jul 14, 2004
 * Time: 11:03:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class BucketHistoryComposer extends Composer
{
    private List voInclusionList;

    private CRUD crud;

    public BucketHistoryComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public void compose(BucketHistoryVO bucketHistoryVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(EDITTrxHistoryVO.class)) associateEDITTrxHistoryVO(bucketHistoryVO);
            if (voInclusionList.contains(BucketVO.class)) associateBucketVO(bucketHistoryVO);
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

    private void associateEDITTrxHistoryVO(BucketHistoryVO bucketHistoryVO) throws Exception
    {
        voInclusionList.remove(BucketHistoryVO.class);

        EDITTrxHistoryVO editTrxHistoryVO = new EDITTrxHistoryComposer(voInclusionList).compose(bucketHistoryVO.getEDITTrxHistoryFK());

        bucketHistoryVO.setParentVO(EDITTrxHistoryVO.class, editTrxHistoryVO);

        editTrxHistoryVO.addBucketHistoryVO(bucketHistoryVO);

        voInclusionList.add(BucketHistoryVO.class);
    }

    private void associateBucketVO(BucketHistoryVO bucketHistoryVO) throws Exception
    {
        voInclusionList.remove(BucketHistoryVO.class);

        BucketVO bucketVO = new BucketComposer(voInclusionList).compose(bucketHistoryVO.getBucketFK());

        bucketHistoryVO.setParentVO(BucketVO.class, bucketVO);

        bucketVO.addBucketHistoryVO(bucketHistoryVO);

        voInclusionList.add(BucketHistoryVO.class);
    }
}
