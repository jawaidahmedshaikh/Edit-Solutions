package edit.common.vo.user;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 26, 2003
 * Time: 12:21:36 PM
 * To change this template use Options | File Templates.
 */
public class BestMatchRuleStructureVO
{
    private String processName;
    private String eventName;
    private String eventTypeName;
    private String ruleName;

    public String getProcessName()
    {
        return processName;
    }

    public void setProcessName(String processName)
    {
        this.processName = processName;
    }

    public String getEventName()
    {
        return eventName;
    }

    public void setEventName(String eventName)
    {
        this.eventName = eventName;
    }

    public String getEventTypeName()
    {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName)
    {
        this.eventTypeName = eventTypeName;
    }

    public String getRuleName()
    {
        return ruleName;
    }

    public void setRuleName(String ruleName)
    {
        this.ruleName = ruleName;
    }
}
