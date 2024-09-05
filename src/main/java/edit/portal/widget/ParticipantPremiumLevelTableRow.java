package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import agent.PremiumLevel;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: dlataille
 * Date: Feb 3, 2006
 * Time: 9:10:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParticipantPremiumLevelTableRow extends TableRow
{
    private String rowStatus;

    private PremiumLevel premiumLevel;
    private Map columnValues;

    public ParticipantPremiumLevelTableRow(PremiumLevel premiumLevel)
    {
        this.premiumLevel = premiumLevel;

        columnValues = new HashMap();

        mapColumnValues();
    }

    /**
     * Maps the values of Deposits to the TableRow
     */
    private void mapColumnValues()
    {
        String issuePremiumLevel = premiumLevel.getIssuePremiumLevel().toString();
        String cellValueForIssPremLvl = "<script>document.write(formatAsCurrency(" + issuePremiumLevel + "))</script>";

        String prodLevelIncrPct = premiumLevel.getProductLevelIncreasePercent().toString();

        String prodLevelIncrAmt = premiumLevel.getProductLevelIncreaseAmount().toString();
        String cellValueForProdLvlIncrAmt = "<script>document.write(formatAsCurrency(" + prodLevelIncrAmt + "))</script>";

        String increaseStopAmt = premiumLevel.getIncreaseStopAmount().toString();
        String cellValueForincrStopAmt = "<script>document.write(formatAsCurrency(" + increaseStopAmt + "))</script>";

        columnValues.put(ParticipantPremiumLevelTableModel.COLUMN_PREMIUM_LEVEL, cellValueForIssPremLvl);
        columnValues.put(ParticipantPremiumLevelTableModel.COLUMN_PROD_LEVEL_INCR_PCT, prodLevelIncrPct);
        columnValues.put(ParticipantPremiumLevelTableModel.COLUMN_PROD_LEVEL_INCR_AMT, cellValueForProdLvlIncrAmt);
        columnValues.put(ParticipantPremiumLevelTableModel.COLUMN_INCR_STOP_AMT, cellValueForincrStopAmt);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return premiumLevel.getPremiumLevelPK().toString();
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
