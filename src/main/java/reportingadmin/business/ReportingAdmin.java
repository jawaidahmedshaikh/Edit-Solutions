/*
* ReportingAdmin.java      Version 2.00  05/20/2004
*
* Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
*
* This program is the confidential and proprietary information of
* Systems Engineering Group, LLC and may not be copied in whole or in part
* without the written permission of Systems Engineering Group, LLC.
*/

package reportingadmin.business;

import edit.common.vo.*;
import edit.services.component.ICRUD;

public interface ReportingAdmin extends ICRUD  {

    public IssueReportVO generateIssueReport(long segmentPK, String issueDate);
}
