package agent;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 7, 2005
 * Time: 11:45:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class ValidateHierarchyReportEntry
{
    private PlacedAgent placedAgent;

    private String message;

    public ValidateHierarchyReportEntry(String message, PlacedAgent placedAgent)
    {
        this.message = message;

        this.placedAgent = placedAgent;
    }

    /**
     * Getter.
     * @return
     */
    public PlacedAgent getPlacedAgent()
    {
        return placedAgent;
    }

    /**
     * Getter.
     * @return
     */
    public String getMessage()
    {
        return message;
    }
}
