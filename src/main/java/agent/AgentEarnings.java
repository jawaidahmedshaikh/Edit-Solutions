package agent;

import edit.common.vo.AgentEarningsVO;
import edit.common.vo.CommissionHistoryVO;
import event.CommissionHistory;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Nov 28, 2003
 * Time: 1:19:09 PM
 * To change this template use Options | File Templates.
 */
public class AgentEarnings
{
    private AgentEarningsVO agentEarningsVO;

    private AgentEarningsImpl agentEarningsImpl;

    public AgentEarnings()
    {
        this.agentEarningsVO = new AgentEarningsVO();

        this.agentEarningsImpl = new AgentEarningsImpl();
    }

    public void generateEarnings(AgentContract agentContract, CommissionHistory[] commissionHistory) throws Exception
    {
        agentEarningsImpl.generateEarnings(this, agentContract, commissionHistory);
    }

    public void generateEarnings(AgentContract agentContract)
    {
        agentEarningsImpl.generateEarnings(this, agentContract);
    }

    public AgentEarningsVO getVO()
    {
        return agentEarningsVO;
    }
}
