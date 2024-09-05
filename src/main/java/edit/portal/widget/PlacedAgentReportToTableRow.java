package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import agent.PlacedAgent;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jan 10, 2006
 * Time: 10:05:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class PlacedAgentReportToTableRow extends TableRow implements Comparable
{
    private PlacedAgent writingAgent;

    private PlacedAgent reportToAgent;

    private String rowStatus;

    public PlacedAgentReportToTableRow(PlacedAgent writingAgent, PlacedAgent reportToAgent)
    {
        this.writingAgent = writingAgent;

        this.reportToAgent = reportToAgent;
    }

    /**
     * @see interface#getRowId() 
     * @return
     */
    public String getRowId()
    {
        return writingAgent.getPlacedAgentPK().toString();
    }

    /**
     * @see interface#getCellValue(String)
     * @param columnName
     * @return
     */
    public Object getCellValue(String columnName)
    {
        String cellValue = null;

        if (columnName.equals(PlacedAgentReportToTableModel.COLUMN_AGENT_NUMBER))
        {
            cellValue = writingAgent.getAgentContract().getAgent().getAgentNumber();
        }
        else if (columnName.equals(PlacedAgentReportToTableModel.COLUMN_AGENT_NAME))
        {
            cellValue = writingAgent.getClientRole().getClientDetail().getName();
        }
        else if (columnName.equals(PlacedAgentReportToTableModel.COLUMN_REPORT_TO_NUMBER))
        {
            if (reportToAgent != null)
            {
                cellValue = reportToAgent.getAgentContract().getAgent().getAgentNumber();
            }
            else
            {
                cellValue = "&nbsp;";
            }
        }
        else if (columnName.equals(PlacedAgentReportToTableModel.COLUMN_REPORT_TO_NAME))
        {
            if (reportToAgent != null)
            {
                cellValue = reportToAgent.getClientRole().getClientDetail().getName();
            }
        }

        return cellValue;
    }

    /**
     * @see interface#setRowStatus(String)
     * @param rowStatus
     */
    public void setRowStatus(String rowStatus)
    {
        this.rowStatus = rowStatus;
    }

    /**
     * @see interface#getRowStatus()
     * @return
     */
    public String getRowStatus()
    {
        return this.rowStatus;
    }

    /**
     * Convenience method (used for sorting) to get the name of the writing agent represented by this table row.
     * @return
     */
    public String getWritingAgentName()
    {
        return writingAgent.getAgentContract().getAgent().getAgentName();
    }

    /**
     * Alphabetical compare based on the Agent's name.
     * @param o
     * @return
     */
    public int compareTo(Object o)
    {
        PlacedAgentReportToTableRow visitingTableRow = (PlacedAgentReportToTableRow) o;

        String visitingName = visitingTableRow.getWritingAgentName();

        String thisName = getWritingAgentName();

        return thisName.compareTo(visitingName);
    }
}
