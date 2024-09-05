package event.dm.composer;

import edit.common.vo.TransactionCorrespondenceVO;
import edit.common.vo.TransactionPriorityVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;
import event.dm.dao.DAOFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Aug 15, 2003
 * Time: 2:45:44 PM
 * To change this template use Options | File Templates.
 */
public class TransactionPriorityComposer extends Composer
{
    private List voInclusionList;
    private TransactionCorrespondenceVO[] transactionCorrespondenceVO;

    private CRUD crud;

    public TransactionPriorityComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public TransactionPriorityVO compose(long transactionPriorityPK) throws Exception
    {
        TransactionPriorityVO transactionPriorityVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            transactionPriorityVO = (TransactionPriorityVO) crud.retrieveVOFromDB(TransactionPriorityVO.class, transactionPriorityPK);
            compose(transactionPriorityVO);
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

        return transactionPriorityVO;
    }

    /**
     * Composes the TransactionPriorty record whose TransactionTypeCT matches the transactionTypeCT parameter value
     * @param transactionTypeCT
     * @return
     * @throws Exception
     */
    public TransactionPriorityVO compose(String transactionTypeCT) throws Exception
    {
        TransactionPriorityVO[] trxPriorityVOs = null;

        try
        {
            trxPriorityVOs = DAOFactory.getTransactionPriorityDAO().findByTrxType(transactionTypeCT);
            if (trxPriorityVOs != null && trxPriorityVOs.length > 0)
            {
                compose(trxPriorityVOs[0]);
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

        return trxPriorityVOs[0];
    }

    public void compose(TransactionPriorityVO transactionPriorityVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(TransactionCorrespondenceVO.class)) appendTransactionCorrespondenceVO(transactionPriorityVO);
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

    private void appendTransactionCorrespondenceVO(TransactionPriorityVO transactionPriorityVO) throws Exception
    {
        voInclusionList.remove(TransactionPriorityVO.class);

        if (transactionCorrespondenceVO == null)
        {
            transactionCorrespondenceVO = DAOFactory.getTransactionCorrespondenceDAO().findByTransactionPriorityPK(transactionPriorityVO.getTransactionPriorityPK());
        }

        if (transactionCorrespondenceVO != null) transactionPriorityVO.setTransactionCorrespondenceVO(transactionCorrespondenceVO);

        voInclusionList.add(TransactionPriorityVO.class);
    }
}