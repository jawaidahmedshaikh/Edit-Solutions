/**
 * Report.java   Version 2.0 05/20/2004
 *
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: May 14, 2004
 * Time: 1:12:16 PM
 * <p/>
 * (c) 2000-2004 Systems Engineering Group, LLC. All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential. Any use is
 * subject to the license agreement.
 */

package reportingadmin;

import edit.common.vo.IssueReportVO;
import edit.common.vo.FilteredOnlineReportVO;
import edit.common.vo.OnlineReportVO;
import codetable.component.CodeTableComponent;
import codetable.business.CodeTable;


public abstract class Report
{
    public static final int ISSUE_REPORT = 0;

    public static final int CONTRACT_REPORT = 1;

    public abstract void generateReport();

    public abstract IssueReportVO getReportAsVO();


    public String getFileName(long productStructurePK, String reportTypeCT) throws Exception
    {
        CodeTable codeTableComponent = new CodeTableComponent();

        OnlineReportVO onlineReportVO = codeTableComponent.getOnlineReport(productStructurePK, reportTypeCT);

        return onlineReportVO.getFileName();
    }
}
