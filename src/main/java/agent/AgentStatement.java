package agent;

import edit.common.vo.AgentStatementVO;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.File;

import staging.CommissionStaging;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Nov 26, 2003
 * Time: 2:38:21 PM
 * To change this template use Options | File Templates.
 */
public class AgentStatement
{
    private AgentStatementVO agentStatementVO;

    private AgentStatementImpl agentStatementImpl;

    private List agentStatementLines;

    private AgentEarnings agentEarnings;

    private AgentInfo agentInfo;

    private boolean agentStatementVOIsComposed;

    public AgentStatement()
    {
        this.agentStatementImpl = new AgentStatementImpl();
        this.agentStatementVO = new AgentStatementVO();
        this.agentStatementLines = new ArrayList();
        this.agentEarnings = new AgentEarnings();
        this.agentInfo = new AgentInfo();
    }

    /**
     * Builds statement at the AgentContractLevel
     * @param agentContract
     * @throws Exception
     */
    public boolean generateStatement(AgentContract agentContract, 
                                     String outputFileType, 
                                     EDITDate processDate,
                                     EDITDateTime stagingDate,
                                     File exportFile,
                                     Set<Long> updatedClientRolePks,
                                     HashMap<String, CommissionStaging> stagingInstances)
    {
        return agentStatementImpl.generateStatement(this, agentContract, outputFileType, 
        		processDate, stagingDate, exportFile, updatedClientRolePks, stagingInstances);
    }

    protected void addAgentStatementLine(AgentStatementLine agentStatementLine)
    {
        this.agentStatementLines.add(agentStatementLine);
    }

    protected AgentStatementLine[] getAgentStatementLine()
    {
        return (AgentStatementLine[]) agentStatementLines.toArray(new AgentStatementLine[agentStatementLines.size()]);
    }

    protected void setAgentEarnings(AgentEarnings agentEarnings)
    {
        this.agentEarnings = agentEarnings;
    }

    protected void setLastStatementAmount(EDITBigDecimal lastStatementAmount)
    {
        this.agentStatementVO.setLastStatementAmount(lastStatementAmount.getBigDecimal());
    }

    protected void setLastStatementDate(String lastStatementDate)
    {
        this.agentStatementVO.setLastStatementDate(lastStatementDate);
    }

    protected AgentEarnings getAgentEarnings()
    {
        return this.agentEarnings;
    }

    protected void setAgentInfo(AgentInfo agentInfo)
    {
        this.agentInfo = agentInfo;
    }

    protected AgentInfo getAgentInfo()
    {
        return this.agentInfo;
    }

    public AgentStatementVO getVO()
    {
        // Careful not to return a composed version
        if (agentStatementVOIsComposed)
        {
            agentStatementVO.clearCollections();

            agentStatementVOIsComposed = false;
        }

        return agentStatementVO;
    }

    public AgentStatementVO composeVO() throws Exception
    {
        if (!agentStatementVOIsComposed)
        {
            agentStatementImpl.composeAgentStatementVO(this);

            agentStatementVOIsComposed = true;
        }

        return agentStatementVO;
    }
}
