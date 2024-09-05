package edit.portal.widget;

import contract.Segment;

import edit.portal.widgettoolkit.TableModel;

import edit.portal.widgettoolkit.TableRow;

import fission.global.AppReqBlock;

import fission.utility.Util;

import group.EnrollmentLeadServiceAgent;

import java.util.List;

/**
 * Enrollments can have LeadServicingAgent(s) associated with them.
 * This TableModel will render all of the LeadServicingAgent(s) associated
 * with the currently selected Enrollment.
 */
public class EnrollmentLeadServiceAgentTableModel extends TableModel
{
    public static String COLUMN_NAME = "Name";

    public static String COLUMN_AGENT_NUMBER = "Agent #";

    public static String COLUMN_ROLE = "Role";

    public static String COLUMN_REGION = "Region";

    public static String COLUMN_EFFECTIVE_DATE = "Effective Date";

    public static String COLUMN_TERMINATION_DATE = "Termination Date";

    public EnrollmentLeadServiceAgentTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        initColumnNames();
    }

    protected void buildTableRows()
    {
        EnrollmentLeadServiceAgent[] enrollmentLeadServiceAgents = getEnrollmentLeadServiceAgents();
        
        String selectedRowId = getSelectedRowId();

        for (EnrollmentLeadServiceAgent enrollmentLeadServiceAgent: enrollmentLeadServiceAgents)
        {
            TableRow enrollmentLeadServiceAgentTableRow = new EnrollmentLeadServiceAgentTableRow(enrollmentLeadServiceAgent);
            
            if (enrollmentLeadServiceAgentTableRow.getRowId().equals(selectedRowId)) 
            {
                enrollmentLeadServiceAgentTableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
            }

            getRows().add(enrollmentLeadServiceAgentTableRow);
        }
    }

    /**
     * Inits the columns to be used for this TableModel.
     */
    private void initColumnNames()
    {
        super.getColumnNames().add(COLUMN_NAME);

        super.getColumnNames().add(COLUMN_AGENT_NUMBER);

        super.getColumnNames().add(COLUMN_ROLE);

        super.getColumnNames().add(COLUMN_REGION);

        super.getColumnNames().add(COLUMN_EFFECTIVE_DATE);

        super.getColumnNames().add(COLUMN_TERMINATION_DATE);
    }

    /**
     * This same TableModel can be used from the perspective of the current Enrollment, or the current Segment.
     * For this reason, there are two ways of getting the EnrollmentLeadServiceAgents.
     *
     * 1. Uses the currently selected Enrollment to find all associated EnrollmentLeadServiceAgents.
     * 
     * OR...
     * 
     * 2. Uses the current Segment.
     * 
     * It is assumed that both can't exist within request scope at the same. This should be a safe bet.
     * @return
     */
    private EnrollmentLeadServiceAgent[] getEnrollmentLeadServiceAgents()
    {
        EnrollmentLeadServiceAgent[] enrollmentLeadServiceAgents = null;

        Long enrollmentPK = getEnrollmentPK();
        
        Long segmentPK = getSegmentPK();
        
        if ((enrollmentPK != null) && (segmentPK == null))
        {
            enrollmentLeadServiceAgents = EnrollmentLeadServiceAgent.findBy_EnrollmentPK(enrollmentPK);
        }
        else if ((enrollmentPK == null) && (segmentPK != null)) 
        {
            Segment segment = Segment.findByPK(segmentPK);
            
            enrollmentLeadServiceAgents = segment.getActiveEnrollmentLeadServiceAgents();
        }
        else 
        {
            // Just to be safe, let's double check.
            throw new AssertionError("Didn't find the Enrollment or Segment as Expected.");    
        }
        

        return enrollmentLeadServiceAgents;
    }

    /**
     * Gets the EnrollmentPK in the AppReqBlock. 
     * @return the EnrollmentPK in the AppReqblock or null
     */
    private Long getEnrollmentPK()
    {
        Long enrollmentPK = null;

        if (Util.initString(getAppReqBlock().getReqParm("enrollmentPK"), null) != null)
        {
            enrollmentPK = new Long(getAppReqBlock().getReqParm("enrollmentPK"));
        }

        return enrollmentPK;
    }
    
    /**
     * Gets the SegmentPK in the AppReqBlock. 
     * @return the SegmentPK in the AppReqblock or null
     */    
    private Long getSegmentPK() 
    {
        Long segmentPK = null;
        
        if (Util.initString(getAppReqBlock().getReqParm("segmentPK"), null) != null)
        {
            segmentPK = new Long(getAppReqBlock().getReqParm("segmentPK"));
        }
        
        return segmentPK;
    }
}
