/*
 * AgentMoveSelectedTableModel.java
 *
 * Created on June 13, 2006, 10:59 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edit.portal.widget;

import edit.portal.widgettoolkit.TableModel;
import fission.global.AppReqBlock;
import java.util.Collections;


/**
 * The user is to have selected an Agent-Move-From PlacedAgent and an Agent-Move-To PlacedAgent.
 * This TableModel is to maintain the selection of the two PlacedAgents.
 * @author gfrosti
 */
public class AgentMoveSelectedTableModel extends TableModel
{
    // The rowId from the AgentMoveFromTableModel.
    private String placedAgentFromSelectedRowId;
    
    // The rowId from the AgentMoveToTableModel.
    private String placedAgentToSelectedRowId;
    
    /**
     * Column heading for Agent.AgentNumber.
     */
    public static final String COLUMN_AGENT_NUMBER = " Agent #";
    
    /**
     * Column heading for ClientDetail.LastName or ClientDetail.CorporateName.
     */
    public static final String COLUMN_AGENT_NAME = "Agent Name";
    
    /**
     * Column heading for CommissionProfile.CommissionLevelCT.
     */
    public static final String COLUMN_COMMISSION_LEVEL = "Comm Level";
    
    /**
     * Column heading for CommissionProfile.CommissionOptionCT.
     */
    public static final String COLUMN_COMMISSION_OPTION = "Comm Option";
    
    /** Creates a new instance of AgentMoveSelectedTableModel */
    public AgentMoveSelectedTableModel(AppReqBlock appReqBlock, String placedAgentFromSelectedRowId, String placedAgentToSelectedRowId)
    {
        super(appReqBlock);
        
        this.placedAgentFromSelectedRowId = placedAgentFromSelectedRowId;
        
        this.placedAgentToSelectedRowId = placedAgentToSelectedRowId;
        
        populateColumnHeadings();
    }
    
    /**
     * The "From" PlacedAgent row id.
     */
    public String getPlacedAgentFromSelectedRowId()
    {
        return placedAgentFromSelectedRowId;
    }
    
    /**
     * The "To" PlacedAgent row id.
     */
    public String getPlacedAgentToSelectedRowId()
    {
        return placedAgentToSelectedRowId;
    }
    
    /**
     * Builds, at most, two entries. One row for the PlacedAgent-From TableModel, and another
     * from the PlaceAgent-To TableModel. If both are available to render, then the
     * "From" is always rendered first.
     */
    protected void buildTableRows()
    {
        if (getPlacedAgentFromSelectedRowId() != null)
        {
            AgentMoveSelectedTableRow agentMoveSelectedTableRow = new AgentMoveSelectedTableRow(getPlacedAgentFromSelectedRowId());
            
            getRows().add(agentMoveSelectedTableRow);
        }
        
        if (getPlacedAgentToSelectedRowId() != null)
        {
            AgentMoveSelectedTableRow agentMoveSelectedTableRow = new AgentMoveSelectedTableRow(getPlacedAgentToSelectedRowId());
            
            getRows().add(agentMoveSelectedTableRow);
        }
        
        Collections.sort(getRows());
    }
 
    /**
     * Populates the columns headings.
     * @see COLUMN_AGENT_NUMBER
     * @see COLUMN_AGENT_NAME
     * @see COLUMN_COMMISSION_LEVEL
     * @see COLUMN_COMMISSION_OPTION
     */
    public void populateColumnHeadings()
    {
        getColumnNames().add(COLUMN_AGENT_NUMBER);
        
        getColumnNames().add(COLUMN_AGENT_NAME);
        
        getColumnNames().add(COLUMN_COMMISSION_LEVEL);
        
        getColumnNames().add(COLUMN_COMMISSION_OPTION);        
    }
    
}
