/*
 * AgentMoveTableRow.java
 *
 * Created on June 12, 2006, 10:21 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edit.portal.widget;

import agent.PlacedAgent;
import agent.PlacedAgentBranch;
import edit.portal.widgettoolkit.TableRow;

/**
 *
 * @author gfrosti
 */
public class AgentMoveTableRow extends TableRow implements Comparable
{
    private PlacedAgentBranch placedAgentBranch;
    
    /**
     * This TableRow can participate in the AgentMoveFromTableModel or the AgentMoveToTableModel.
     * @see MOVING_FROM
     * @see MOVING_TO
     */
    private String movingFromTo;
    
    /**
     * Creates a new instance of AgentMoveTableRow . It is expected
     * that there will be two PlacedAgents - one for target agent,
     * and the other for its report-to.
     */    
    public AgentMoveTableRow(PlacedAgentBranch placedAgentBranch, String movingFromTo)
    {
        this.placedAgentBranch = placedAgentBranch;
        
        this.movingFromTo = movingFromTo;
        
        populateCellValues();
    }
    
    /**
     * The type of TableModel this TableRow is participating in. This can
     * be the AgentMoveFromTableModel and AgentMoveToTableModel.
     * @see MOVING_FROM
     * @see MOVING_TO
     */
    public String getMovingFromTo()
    {
        return movingFromTo;
    }
    
    /**
     * There will be more than one summary on the target screen that could have the writing agent's PK
     * has the selected PK. It is necessary to preface this PK with "MOVING_FROM:".
     */
    public String getRowId()
    {
        return getMovingFromTo() + ":" +  getWritingAgent().getPlacedAgentPK().toString();               
    }
    
    /**
     * Uses two PlacedAgents - the target and its report-to (if any).
     */
    private void populateCellValues()
    {
        PlacedAgent writingAgent = getWritingAgent();
        
        PlacedAgent reportToPlacedAgent = getReportToAgent();
        
        if (hasReportTo())
        {   
            getCellValues().put(AgentMoveTableModel.COLUMN_REPORT_TO_ID, reportToPlacedAgent.getAgentContract().getAgent().getAgentNumber());
        }
        
        getCellValues().put(AgentMoveTableModel.COLUMN_SITUATION, writingAgent.getSituation().getSituationCode());
        
        getCellValues().put(AgentMoveTableModel.COLUMN_AGENT_NO, writingAgent.getAgentContract().getAgent().getAgentNumber());
        
        getCellValues().put(AgentMoveTableModel.COLUMN_NAME, writingAgent.getClientRole().getClientDetail().getName());
    }
    
    /**
     * Convenience method. Most likely used for sorting.
     */
    public String getAgentName()
    {
        return (String) getCellValue(AgentMoveTableModel.COLUMN_NAME);
    }
    
    /**
     * True if the specified PlacedAgentBranch has (at least) two PlacedAgents suggesting
     * that is, in fact, a report-to agent.
     */
    private boolean hasReportTo()
    {
        return placedAgentBranch.getPlacedAgents().length > 1;        
    }

    /**
     * Compares by Agent name.
     */
    public int compareTo(Object agentMoveTableRow)
    {
        AgentMoveTableRow visitingTableRow = (AgentMoveTableRow) agentMoveTableRow;
        
        return getAgentName().compareTo(visitingTableRow.getAgentName());
    }
    
    /**
     * In a PlacedAgentBranch of two element, the Writing-Agent is the 1st element, while
     * the Report-To is the 0th element.
     */
    private PlacedAgent getWritingAgent()
    {
        PlacedAgent writingAgent = null;
        
        if (hasReportTo())
        {
            writingAgent = placedAgentBranch.getPlacedAgent(1);
        }
        else
        {
            writingAgent = placedAgentBranch.getPlacedAgent(0);
        }
        
        return writingAgent;
    }
    
    /**
     * In a PlacedAgentBranch of two elements, the Report-To is the 0th element.
     */
    private PlacedAgent getReportToAgent()
    {   
        PlacedAgent reportToPlacedAgent = null;
        
        if (hasReportTo())
        {
            reportToPlacedAgent = placedAgentBranch.getPlacedAgent(0);
        }        
        
        return reportToPlacedAgent;
    }
}