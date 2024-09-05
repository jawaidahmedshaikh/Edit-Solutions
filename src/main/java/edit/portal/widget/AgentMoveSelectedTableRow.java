/*
 * AgentMoveSelectedTableRow.java
 *
 * Created on June 13, 2006, 1:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edit.portal.widget;

import agent.PlacedAgent;
import edit.portal.widgettoolkit.TableRow;

import fission.utility.Util;

/**
 *
 * @author gfrosti
 */
public class AgentMoveSelectedTableRow extends TableRow implements Comparable
{
    // The selected PlacedAgent - it's either the "from" or "to" PlacedAgent.
    private PlacedAgent placedAgent;
    
    public static final Integer FROM_PLACEDAGENT = new Integer(0);
    
    public static final Integer TO_PLACEDAGENT = new Integer(1);
    
    // The id of the selected "from" or "to" PlacedAgent.
    private String selectedRowId;
    
    /** Creates a new instance of AgentMoveSelectedTableRow */
    public AgentMoveSelectedTableRow(String selectedRowId)
    {
        this.selectedRowId = selectedRowId;
        
        this.placedAgent = findPlacedAgent();
        
        populateCellValues();
    }
    
    /**
     * The "from" or "to" selected id from the corresponding AgentMove-From/To-TableModel.
     * @see FROM_PLACEDAGENT
     * @see TO_PLACEDAGENT
     */
    public String getSelectedRowId()
    {
        return selectedRowId;
    }
    
    /**
     * The PlacedAgentPK.
     */
    public String getRowId()
    {
        return parseForPlacedAgentPK();
    }
    
    /**
     * Specific to the UI rendering of the AgentMoveSelectTableModel. This
     * checks the prefix of the Constructor-specified selectedRowId to
     * determine if the PlacedAgent is a "from" or "to" PlacedAgent.
     */
    public Integer getPlacedAgentType()
    {
        Integer placedAgentType = null;
        
        if (getSelectedRowId().startsWith(AgentMoveTableModel.MOVING_FROM))
        {
            placedAgentType = FROM_PLACEDAGENT;
        }
        else if (getSelectedRowId().startsWith(AgentMoveTableModel.MOVING_TO))
        {
            placedAgentType = TO_PLACEDAGENT;
        }
        
        return placedAgentType;
    }
    
    /**
     * Sorted by the type of PlacedAgent where FROM_PLACEDAGENT > TO_PLACEDAGENT.
     * @see FROM_PLACEDAGENT
     * @see TO_PLACEDAGENT
     */
    public int compareTo(Object o)
    {
        AgentMoveSelectedTableRow visitingTableRow = (AgentMoveSelectedTableRow) o;
        
        return visitingTableRow.getPlacedAgentType().compareTo(getPlacedAgentType());
    }
    
    /**
     * The PlacedAgentPK is embedded in a FOO:PlacedAgentPK String. This
     * tokenizes on ":" returning the PK.
     */
    private String parseForPlacedAgentPK()
    {
        return parseForPlacedAgentPK(getSelectedRowId());
    }
    
    /**
     * The PlacedAgentPK is embedded in a FOO:PlacedAgentPK String. This
     * tokenizes on ":" returning the PK.
     */    
    public static String parseForPlacedAgentPK(String selectedRowId)
    {
        String[] tokens = Util.fastTokenizer(selectedRowId, ":");;
        
        return tokens[1];        
    }

    /**
     * Sets the desired column values of AgentMoveSelectedTableModel driven by
     * the selected PlacedAgentPK.
     */
    private void populateCellValues()
    {
        String agentNumber = placedAgent.getAgentContract().getAgent().getAgentNumber();
        
        String agentName = placedAgent.getClientRole().getClientDetail().getName();
        
        String commissionLevelCT = placedAgent.getActivePlacedAgentCommissionProfile().getCommissionProfile().getCommissionLevelCT();
        
        String commissionOption = placedAgent.getActivePlacedAgentCommissionProfile().getCommissionProfile().getCommissionOptionCT();
        
        getCellValues().put(AgentMoveSelectedTableModel.COLUMN_AGENT_NAME, agentNumber);
        
        getCellValues().put(AgentMoveSelectedTableModel.COLUMN_AGENT_NUMBER, agentName);
        
        getCellValues().put(AgentMoveSelectedTableModel.COLUMN_COMMISSION_LEVEL, commissionLevelCT);
        
        getCellValues().put(AgentMoveSelectedTableModel.COLUMN_COMMISSION_OPTION, commissionOption);
    }

    /**
     * Finds the PlacedAgent specified by the selected row id.
     */
    private PlacedAgent findPlacedAgent()
    {
        Long placedAgentPK = new Long(parseForPlacedAgentPK());
        
        return PlacedAgent.findBy_PK(placedAgentPK);
    }
}
