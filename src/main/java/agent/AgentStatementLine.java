package agent;

import edit.common.vo.AgentStatementLineVO;
import edit.common.vo.CommissionHistoryVO;
import event.CommissionHistory;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Nov 26, 2003
 * Time: 3:05:57 PM
 * To change this template use Options | File Templates.
 */
public class AgentStatementLine
{
    private AgentStatementLineVO agentStatementLineVO;

    private AgentStatementLineImpl agentStatementLineImpl;

    public static final String MISC_INCOME = "Misc Income";
    public static final String TRAILS = "Trails";
    public static final String FIRST_YEAR = "First Year";
    public static final String RENEWAL = "Renewal";
    public static final String CHARGE_BACK = "Charge Back";
    public static final String BONUS = "Bonus";

    public AgentStatementLine()
    {
        this.agentStatementLineVO = new AgentStatementLineVO();
        this.agentStatementLineImpl = new AgentStatementLineImpl();
    }

    public void generateStatementLine(PlacedAgent placedAgent, CommissionHistory commissionHistory, String transactionTypeCT) throws Exception
    {
        agentStatementLineImpl.generateStatementLine(this, placedAgent, commissionHistory, transactionTypeCT);
    }

    public AgentStatementLineVO getVO()
    {
        return agentStatementLineVO;
    }
}
