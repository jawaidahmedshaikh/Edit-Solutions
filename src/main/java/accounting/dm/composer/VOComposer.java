/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 16, 2003
 * Time: 12:54:42 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package accounting.dm.composer;

import accounting.dm.dao.DAOFactory;
import edit.common.vo.AccountingDetailVO;
import edit.services.db.CRUD;

import java.util.List;

public class VOComposer
{
    private CRUD crud;

    public AccountingDetailVO[] composeAccountingDetailVOByPendingStatus(String accountingPendingStatus,
                                                                          List voInclusionList) throws Exception
    {
        AccountingDetailVO[] accountingDetailVO = DAOFactory.getAccountingDetailDAO().findByPendingStatus(accountingPendingStatus);

        if (accountingDetailVO != null && !voInclusionList.isEmpty())
        {

        }

        return accountingDetailVO;
    }
    
    public AccountingDetailVO[] composeAccountingDetailVOByAccountingPeriod(String accountingPeriod,
                                                                            List voInclusionList)
    {
        AccountingDetailVO[] accountingDetailVO = DAOFactory.getAccountingDetailDAO().findByAccountingPeriod(accountingPeriod);

        if (accountingDetailVO != null && !voInclusionList.isEmpty())
        {

        }

        return accountingDetailVO;
    }
}
