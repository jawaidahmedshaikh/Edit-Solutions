package edit.portal.widget;

import agent.Agent;

import client.ClientDetail;

import edit.portal.widgettoolkit.TableRow;

import fission.utility.DateTimeUtil;

import group.EnrollmentLeadServiceAgent;

import role.ClientRole;

/**
 * Wraps EnrollmentLeadServiceAgent and renders its properties as a TableRow.
 */
public class EnrollmentLeadServiceAgentTableRow extends TableRow
{
    /**
     * The wrapped EnrollmentLeadServiceAgent.
     */
    private EnrollmentLeadServiceAgent enrollmentLeadServiceAgent;
    
    public EnrollmentLeadServiceAgentTableRow(EnrollmentLeadServiceAgent enrollmentLeadServiceAgent)
    {
        this.enrollmentLeadServiceAgent = enrollmentLeadServiceAgent;      
        
        initCellValues();
    }

    /**
     * The EnrollmentLeadServiceAgent.EnrollmentLeadServicePK.
     * @return
     */
    public String getRowId()
    {
        return getEnrollmentLeadServiceAgent().getEnrollmentLeadServiceAgentPK().toString();
    }

    /**
     * @see #enrollmentLeadServiceAgent
     * @return
     */
    public EnrollmentLeadServiceAgent getEnrollmentLeadServiceAgent()
    {
        return enrollmentLeadServiceAgent;
    }

    /**
     * Maps the cell names to the cell values for this row.
     */
    private void initCellValues()
    {
        ClientRole clientRole = getEnrollmentLeadServiceAgent().getClientRole();
        
        ClientDetail clientDetail = clientRole.getClientDetail();
   
        super.getCellValues().put(EnrollmentLeadServiceAgentTableModel.COLUMN_NAME, clientDetail.getPrettyName());
        
        super.getCellValues().put(EnrollmentLeadServiceAgentTableModel.COLUMN_AGENT_NUMBER, clientRole.getReferenceID());
        
        super.getCellValues().put(EnrollmentLeadServiceAgentTableModel.COLUMN_ROLE, clientRole.getRoleTypeCT());
        
        super.getCellValues().put(EnrollmentLeadServiceAgentTableModel.COLUMN_REGION, getEnrollmentLeadServiceAgent().getRegionCT());
        
        super.getCellValues().put(EnrollmentLeadServiceAgentTableModel.COLUMN_EFFECTIVE_DATE, DateTimeUtil.formatYYYYMMDDToMMDDYYYY(getEnrollmentLeadServiceAgent().getEffectiveDate().getFormattedDate()));
        
        super.getCellValues().put(EnrollmentLeadServiceAgentTableModel.COLUMN_TERMINATION_DATE, DateTimeUtil.formatYYYYMMDDToMMDDYYYY(getEnrollmentLeadServiceAgent().getTerminationDate().getFormattedDate()));
    }
}
