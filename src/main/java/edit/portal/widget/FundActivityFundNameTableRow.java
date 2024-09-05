package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import edit.common.vo.FundVO;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: sprasad
 * Date: Jan 15, 2008
 * Time: 1:30:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class FundActivityFundNameTableRow extends TableRow
{
    private FundVO fundVO;

    private String rowId;

    private Map columnValues;

    private String rowStatus = TableRow.ROW_STATUS_DEFAULT;

    public FundActivityFundNameTableRow(FundVO fundVO)
    {
        this.fundVO = fundVO;

        columnValues = new HashMap();

        mapColumnValues();
    }

    private void mapColumnValues()
    {
        setRowId(this.fundVO.getFundPK()+"");

        columnValues.put(FundActivityFundNameDoubleTableModel.COLUMN_FUND_NAME, fundVO.getName());
    }

    /**
     * The cell value for the cell column.
     * @param columnName
     * @return
     */
    public String getCellValue(String columnName)
    {
        return (String) columnValues.get(columnName);
    }

    /**
     *
     * @param rowId
     */
    private void setRowId(String rowId)
    {
        this.rowId = rowId;
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return rowId;
    }

   /**
     * @see TableRow#setRowStatus(String)
     */
    public void setRowStatus(String rowStatus)
    {
        this.rowStatus = rowStatus;
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowStatus()
     * @return
     */
    public String getRowStatus()
    {
        return rowStatus;
    }
}
