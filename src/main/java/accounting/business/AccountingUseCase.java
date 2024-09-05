package accounting.business;

import edit.services.component.IUseCase;
import event.CashBatchContract;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 10, 2004
 * Time: 9:19:16 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AccountingUseCase extends IUseCase 
{
    public void accessAccounting();

    public void accessJournalAdjustment();
}
