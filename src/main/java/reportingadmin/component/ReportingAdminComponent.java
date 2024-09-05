/*
 * ReportingAdminComponent.java      Version 2.0  05/18/2004
 *
 * (c) 2000-2004 Systems Engineering Group, LLC. All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential. Any use is
 * subject to the license agreement.
 */

package reportingadmin.component;

import edit.common.vo.*;
import edit.services.component.AbstractComponent;
import reportingadmin.business.*;
import reportingadmin.IssueReport;

import java.util.List;

/**
 * The Client Engine request controller
 */
public class ReportingAdminComponent extends AbstractComponent implements ReportingAdmin {

    public ReportingAdminComponent() {

    }


   public int deleteVO(Class voClass, long primaryKey, boolean recursively) throws Exception
    {
        return 0;
    }


    public Object retrieveVO(Class voClass, long primaryKey, boolean recursively, List voInclusionList) throws Exception
    {
        return null;
    }
    
    public long createOrUpdateVO(Object valueObject, boolean recursively) throws Exception{ return 0;}
    public String[] findVOs() { return null; };

    /**
     * For the selected contract, an issue report will be generated
     * @param segmentPK
     * @return IssueReportVO
     */
    public IssueReportVO generateIssueReport(long segmentPK, String issueDate)
    {
        IssueReport issueReport = new IssueReport(segmentPK, issueDate);

        issueReport.generateReport();

        return issueReport.getReportAsVO();
    }
}







