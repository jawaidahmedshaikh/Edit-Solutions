package agent;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 7, 2005
 * Time: 11:42:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class ValidateHierarchyReport
{
    private String reportName;
    private String reportDescription;
    private List reportEntries = new ArrayList();
    private Integer reportIdentifier;

    public ValidateHierarchyReport(String reportName, String reportDescription, Integer reportIdentifier)
    {
        this.reportName = reportName;

        this.reportDescription = reportDescription;

        this.reportIdentifier = reportIdentifier;
    }

    /**
     * True if there were errors in this report.
     * @return
     */
    public boolean getHasErrors()
    {
        return (getReportEntries().size() > 0);
    }

    /**
     * Getter.
     * @return
     */
    public Integer getReportIdentifier()
    {
        return reportIdentifier;
    }

    /**
     * Getter.
     * @return
     */
    public String getReportDescription()
    {
        return reportDescription;
    }

    /**
     * Adder.
     * @param message
     * @param placedAgent
     */
    public void addReportEntry(String message, PlacedAgent placedAgent)
    {
        this.reportEntries.add(new ValidateHierarchyReportEntry(message, placedAgent));
    }

    /**
     * The list of error entries, if any.
     * @return
     */
    public List getReportEntries()
    {
        return reportEntries;
    }

    /**
     * Getter.
     * @return
     */
    public String getReportName()
    {
        return reportName;
    }

    /**
     * Setter.
     * @param reportName
     */
    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }
}
