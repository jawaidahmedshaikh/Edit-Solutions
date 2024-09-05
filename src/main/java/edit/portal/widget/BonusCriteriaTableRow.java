package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import agent.BonusCriteria;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: dlataille
 * Date: Feb 2, 2006
 * Time: 1:11:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class BonusCriteriaTableRow extends TableRow
{
    private String rowStatus;

    private BonusCriteria bonusCriteria;
    private Map columnValues;

    public BonusCriteriaTableRow(BonusCriteria bonusCriteria)
    {
        this.bonusCriteria = bonusCriteria;

        columnValues = new HashMap();

        mapColumnValues();
    }

    /**
     * Maps the values of Deposits to the TableRow
     */
    private void mapColumnValues()
    {
        String bonusAmount = bonusCriteria.getBonusAmount().toString();
        String cellValueForBonusAmount = "<script>document.write(formatAsCurrency(" + bonusAmount + "))</script>";

        String excessBonusAmount = bonusCriteria.getExcessBonusAmount().toString();
        String cellValueForExcessBonusAmount = "<script>document.write(formatAsCurrency(" + excessBonusAmount + "))</script>";

        String bonusBasisPoint = bonusCriteria.getBonusBasisPoint().toString();

        String excessBonusBasisPoint = bonusCriteria.getExcessBonusBasisPoint().toString();

        columnValues.put(BonusCriteriaTableModel.COLUMN_BONUS_AMOUNT, cellValueForBonusAmount);
        columnValues.put(BonusCriteriaTableModel.COLUMN_EXCESS_BONUS_AMOUNT, cellValueForExcessBonusAmount);
        columnValues.put(BonusCriteriaTableModel.COLUMN_BONUS_BASIS_POINTS, bonusBasisPoint);
        columnValues.put(BonusCriteriaTableModel.COLUMN_EXCESS_BONUS_BASIS_POINTS, excessBonusBasisPoint);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return bonusCriteria.getBonusCriteriaPK().toString();
    }

    /**
     * Returns the cell value for the given cell column
     * @param columnName
     * @return
     */
    public Object getCellValue(String columnName)
    {
        return columnValues.get(columnName);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#setRowStatus(String)
     * @param rowStatus
     */
    public void setRowStatus(String rowStatus)
    {
        this.rowStatus = rowStatus;
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowStatus();
     * @return
     */
    public String getRowStatus()
    {
        return rowStatus;
    }
}
