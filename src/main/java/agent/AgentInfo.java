package agent;

import edit.common.vo.*;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 1, 2003
 * Time: 11:50:05 AM
 * To change this template use Options | File Templates.
 */
public class AgentInfo
{
    private AgentInfoVO agentInfoVO;

    private AgentInfoImpl agentInfoImpl;

    public static final int CORPORATION = 0;
    public static final int INDIVIDUAL = 1;

    public AgentInfo()
    {
        this.agentInfoImpl = new AgentInfoImpl();
        this.agentInfoVO = new AgentInfoVO();
    }

    public void generateAgentInfo(PlacedAgent placedAgent, String statementDate)
    {
        this.agentInfoImpl.generateAgentInfo(this, placedAgent, statementDate);
    }

    public AgentInfoVO getVO()
    {
        return this.agentInfoVO;
    }
}
