/*
 * User: unknown
 * Date: Jun 9, 2006
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package edit.portal.widget;

import agent.PlacedAgentBranch;

import edit.common.EDITDate;

import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import fission.global.AppReqBlock;
import java.util.Collections;

/**
 *
 * @author gfrosti
 */
public class AgentMoveTableModel extends TableModel
{
    /**
     * Prefix to the Moving From PlacedAgentPK when selected in the AgentMoveFromTableModel.
     */
    public static final String MOVING_FROM = "MOVING_FROM";
    
    /**
     * Prefix to the Moving To PlacedAgentPK when selected in the AgentMoveToTableModel.
     */
    public static final String MOVING_TO = "MOVING_TO";        
    
    // Supports the search feature should the search be by agent number.
    private String agentNumber;
    
    // Supports the search feature should the search be by agent (last) name.
    private String agentName;
    
    // PlacedAgents are always relative a contractCodeCT and must be specified for the searches.
    private String contractCodeCT;
    
    /**
     * Column Heading.
     */
    public static String COLUMN_AGENT_NO = "Agent #";

    /**
     * Column Heading.
     */    
    public static String COLUMN_NAME = "Name";
    
    /**
     * Column Heading.
     */    
    public static String COLUMN_SITUATION = "Situation";
    
    /**
     * Column Heading.
     */    
    public static String COLUMN_REPORT_TO_ID = "Rpt To ID";
        
    /**
     * Every subclass of this TableModel needs to be a "from" or "to" TableModel.
     */
    private String movingFromTo;
    
    /** Creates a new instance of AgentMoveTableModel */
    protected AgentMoveTableModel(AppReqBlock appReqBlock, String agentName, String agentNumber, String contractCodeCT, String movingFromTo)
    {
        super(appReqBlock);

        this.movingFromTo = movingFromTo;
        
        setSearchParams(agentName, agentNumber, contractCodeCT);
        
        populateColumnHeadings();        
    }
    
    /**
     * Subclasses of this TableModel must specifiy the type of table ("from" or "to") they are.
     */
    public String getMovingFromTo()
    {
       return movingFromTo;
    }
    
    /**
     * Populates a table with a PlacedAgent and its Report-Tos found by a specified
     * search parameter of last name or agent number.
     */
    protected void buildTableRows()
    {
        String selectedRowId = getSelectedRowIdFromRequestScope();
        
        PlacedAgentBranch[] placedAgentBranches = new PlacedAgentBranch[0];
        
        EDITDate currentDate = new EDITDate();
        
        if (agentNumberExists())
        {
            placedAgentBranches = PlacedAgentBranch.findBy_AgentNumber_AND_ContractCodeCT_AND_StopDate(getAgentNumber(), getContractCodeCT(), currentDate, 2);
        }
        else if (agentNameExists())
        {
            placedAgentBranches = PlacedAgentBranch.findBy_AgentName_AND_ContractCodeCT_AND_StopDate(getAgentName(), getContractCodeCT(), currentDate, 2);
        }
        
        for (int i = 0; i < placedAgentBranches.length; i++)
        {
            PlacedAgentBranch placedAgentBranch = placedAgentBranches[i];
            
            AgentMoveTableRow agentMoveTableRow = new AgentMoveTableRow(placedAgentBranch, getMovingFromTo());   
            
            if (selectedRowId != null && selectedRowId.equals(agentMoveTableRow.getRowId()))
            {
                agentMoveTableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
            }
            
            getRows().add(agentMoveTableRow);
        }
        
        Collections.sort(getRows());
    }
    
    public String getAgentNumber()
    {
        return agentNumber;
    }
    
    private void setAgentNumber(String agentNumber)
    {
        this.agentNumber = agentNumber;
    }
    
    public String getAgentName()
    {
        return agentName;
    }
    
    private void setAgentName(String agentName)
    {
        this.agentName = agentName;
    }
    
    /**
     * This TableModel is rendered based on the search params of (either)
     * agent number or agent last name with the contractCodeCT. This convenience method sets those
     * values (either, or) if they exist.
     */
    private void setSearchParams(String agentName, String agentNumber, String contractCodeCT)
    {
        setAgentName(agentName);
        
        setAgentNumber(agentNumber);        
        
        setContractCodeCT(contractCodeCT);
    }
    
    /**
     * Convenience method to determine if an agent number has been submitted from the
     * page.
     */
    private boolean agentNumberExists()
    {
        return getAgentNumber() != null;
    }
    
    /**
     * Convenience method to determine if an agent last name has been submitted from the
     * page.
     */
    private boolean agentNameExists()
    {
        return getAgentName() != null;
    }
    
    public String getContractCodeCT()
    {
        return contractCodeCT;
    }
    
    private void setContractCodeCT(String contractCodeCT)
    {
        this.contractCodeCT = contractCodeCT;
    }

    /**
     * Convenience method to establish the column names.
     */
    private void populateColumnHeadings()
    {
        getColumnNames().add(COLUMN_AGENT_NO);
        
        getColumnNames().add(COLUMN_NAME);
        
        getColumnNames().add(COLUMN_SITUATION);
        
        getColumnNames().add(COLUMN_REPORT_TO_ID);        
    }
}
