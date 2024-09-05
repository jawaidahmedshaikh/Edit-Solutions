package edit.portal.widget;

import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import edit.common.vo.AccountingDetailVO;
import fission.global.AppReqBlock;
import fission.utility.Util;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import accounting.AccountingDetail;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: sprasad
 * Date: Apr 9, 2008
 * Time: 4:29:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccountingDetailTableModel extends TableModel
{
    public static final String COLUMN_CONTRACTNUMBER = "Contract #";
    public static final String COLUMN_EFFECTIVEDATE = "Eff Date";
    public static final String COLUMN_TRANSACTIONTYPE = "Trx Type";
    public static final String COLUMN_AMOUNT = "Amount";
    public static final String COLUMN_ACCOUNT = "Account";
    public static final String COLUMN_DEBITCREDITIND = "DR/CR";

    private final String[] COLUMN_NAMES =
    {
        COLUMN_CONTRACTNUMBER, COLUMN_EFFECTIVEDATE, COLUMN_TRANSACTIONTYPE, COLUMN_AMOUNT, COLUMN_ACCOUNT, COLUMN_DEBITCREDITIND
    };

    public AccountingDetailTableModel(AppReqBlock appReqBlock) throws Exception
    {
        super(appReqBlock);
        
        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    protected void buildTableRows()
    {
        accounting.business.Accounting accountingComponent = new accounting.component.AccountingComponent();
        
        AccountingDetailVO[] accountingDetailVOs = null;

        try
        {
            accountingDetailVOs = accountingComponent.composeAccountingDetailByPendingStatus("Y", new ArrayList());
        }
        catch (Exception e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            throw new RuntimeException(e);
        }

        if (accountingDetailVOs != null)
        {
            accountingDetailVOs = (AccountingDetailVO[]) Util.sortObjects(accountingDetailVOs, new String[] {"getContractNumber"});            

            for (int i = 0; i < accountingDetailVOs.length; i++)
            {
                AccountingDetailVO accountingDetailVO = accountingDetailVOs[i];

                TableRow accountingDetailAccountSummaryTableRow = new AccountingDetailTableRow(accountingDetailVO);

                if (accountingDetailAccountSummaryTableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    accountingDetailAccountSummaryTableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(accountingDetailAccountSummaryTableRow);
            }
        }
    }
}
