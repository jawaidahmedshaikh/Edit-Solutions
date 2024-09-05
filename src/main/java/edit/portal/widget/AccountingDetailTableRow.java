package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import edit.common.vo.AccountingDetailVO;

import java.util.Map;
import java.util.HashMap;

import accounting.AccountingDetail;

/**
 * Created by IntelliJ IDEA.
 * User: sprasad
 * Date: Apr 9, 2008
 * Time: 4:30:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccountingDetailTableRow extends TableRow
{
    private Map columnValues;

    private String rowStatus = TableRow.ROW_STATUS_DEFAULT;

    private AccountingDetailVO accountingDetailVO;

    public AccountingDetailTableRow(AccountingDetailVO accountingDetailVO)
    {
        this.accountingDetailVO = accountingDetailVO;

        this.columnValues = new HashMap();

        mapColumnValues();
    }

    public String getCellValue(String columnName)
    {
        return (String) columnValues.get(columnName);
    }

    public String getRowId()
    {
        return accountingDetailVO.getAccountingDetailPK()+"";
    }

    public String getRowStatus()
    {
        return rowStatus;
    }

    public void setRowStatus(String rowStatus)
    {
        this.rowStatus = rowStatus;
    }

    private void mapColumnValues()
    {
        columnValues.put(AccountingDetailTableModel.COLUMN_CONTRACTNUMBER, accountingDetailVO.getContractNumber());

        columnValues.put(AccountingDetailTableModel.COLUMN_EFFECTIVEDATE, accountingDetailVO.getEffectiveDate());

        columnValues.put(AccountingDetailTableModel.COLUMN_TRANSACTIONTYPE, accountingDetailVO.getTransactionCode());

        columnValues.put(AccountingDetailTableModel.COLUMN_AMOUNT, "<script>document.write(formatAsCurrency(" + accountingDetailVO.getAmount().toString() + "))</script>");

        columnValues.put(AccountingDetailTableModel.COLUMN_ACCOUNT, accountingDetailVO.getAccountNumber());

        columnValues.put(AccountingDetailTableModel.COLUMN_DEBITCREDITIND, accountingDetailVO.getDebitCreditInd());
    }
}
