package event.dm.composer;

import edit.common.vo.*;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 3, 2003
 * Time: 9:18:47 AM
 * To change this template use Options | File Templates.
 */
public class EDITTrxCorrespondenceComposer extends Composer
{
    private CRUD crud = null;

    private List voInclusionList;

    public EDITTrxCorrespondenceComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public EDITTrxCorrespondenceVO compose(long editTrxCorrespondencePK) throws Exception
    {
        EDITTrxCorrespondenceVO editTrxCorrespondenceVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            editTrxCorrespondenceVO = (EDITTrxCorrespondenceVO) crud.retrieveVOFromDB(EDITTrxCorrespondenceVO.class, editTrxCorrespondencePK);

            compose(editTrxCorrespondenceVO);
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

        return editTrxCorrespondenceVO;
    }

    public void compose(EDITTrxCorrespondenceVO editTrxCorrespondenceVO)
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(TransactionCorrespondenceVO.class)) appendTransactionCorrespondenceVO(editTrxCorrespondenceVO);
            if (voInclusionList.contains(EDITTrxVO.class)) associateEditTrxVO(editTrxCorrespondenceVO);
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
    }

    private void appendTransactionCorrespondenceVO(EDITTrxCorrespondenceVO editTrxCorrespondenceVO) throws Exception
    {
        voInclusionList.remove(EDITTrxCorrespondenceVO.class);

        TransactionCorrespondenceVO transactionCorrespondenceVO = (TransactionCorrespondenceVO) crud.retrieveVOFromDB(TransactionCorrespondenceVO.class, editTrxCorrespondenceVO.getTransactionCorrespondenceFK());

        if (transactionCorrespondenceVO != null)
        {
            editTrxCorrespondenceVO.setParentVO(TransactionCorrespondenceVO.class, transactionCorrespondenceVO);

            transactionCorrespondenceVO.addEDITTrxCorrespondenceVO(editTrxCorrespondenceVO);
        }

        voInclusionList.add(EDITTrxCorrespondenceVO.class);
    }

    private void associateEditTrxVO(EDITTrxCorrespondenceVO editTrxCorrespondenceVO) throws Exception
    {
        voInclusionList.remove(EDITTrxCorrespondenceVO.class);

        EDITTrxVO editTrxVO = (EDITTrxVO) crud.retrieveVOFromDB(EDITTrxVO.class, editTrxCorrespondenceVO.getEDITTrxFK());

        EDITTrxComposer composer = new EDITTrxComposer(voInclusionList);

        composer.compose(editTrxVO);

        editTrxCorrespondenceVO.setParentVO(EDITTrxVO.class, editTrxVO);

        editTrxVO.addEDITTrxCorrespondenceVO(editTrxCorrespondenceVO);

        voInclusionList.add(EDITTrxCorrespondenceVO.class);
    }
}
