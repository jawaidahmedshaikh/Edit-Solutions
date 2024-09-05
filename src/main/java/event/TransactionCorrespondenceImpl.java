package event;

import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Nov 7, 2003
 * Time: 9:32:48 AM
 * To change this template use Options | File Templates.
 */
public class TransactionCorrespondenceImpl extends CRUDEntityImpl
{
    protected void load(TransactionCorrespondence transactionCorrespondence, long transactionCorrespondencePK) throws Exception
    {
        super.load(transactionCorrespondence, transactionCorrespondencePK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void save(TransactionCorrespondence transactionCorrespondence) throws Exception
    {
        super.save(transactionCorrespondence, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    protected void delete(TransactionCorrespondence transactionCorrespondence) throws Exception
    {
        super.delete(transactionCorrespondence, ConnectionFactory.EDITSOLUTIONS_POOL);
    }
}
