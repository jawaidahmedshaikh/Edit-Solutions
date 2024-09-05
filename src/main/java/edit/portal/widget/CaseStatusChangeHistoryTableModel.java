/*
 * User: dlataille
 * Date: July 18, 2007
 * Time: 12:20:25 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import fission.global.*;
import fission.utility.*;

import java.util.*;

import group.CaseStatusChangeHistory;

public class CaseStatusChangeHistoryTableModel extends TableModel
{
    public static final String COLUMN_STATUS = "Status";
    public static final String COLUMN_PRIOR_STATUS = "Prior Status";
    public static final String COLUMN_CHG_EFF_DATE = "Change Eff Date";
    public static final String COLUMN_OPERATOR = "Operator";
    public static final String COLUMN_DATE_TIME = "DateTime";

    private static final String[] COLUMN_NAMES = {COLUMN_STATUS, COLUMN_PRIOR_STATUS, COLUMN_CHG_EFF_DATE, COLUMN_OPERATOR, COLUMN_DATE_TIME};

    public CaseStatusChangeHistoryTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        String caseContractGroupPK = Util.initString((String) this.getAppReqBlock().getUserSession().getParameter("activeCasePK"), null);
        if (caseContractGroupPK != null)
        {
            CaseStatusChangeHistory[] caseStatusChangeHistory = CaseStatusChangeHistory.findByContractGroup(new Long(caseContractGroupPK));
            for (int i = 0; i < caseStatusChangeHistory.length; i++)
            {
                CaseStatusChangeHistoryTableRow tableRow = new CaseStatusChangeHistoryTableRow(caseStatusChangeHistory[i]);

                super.addRow(tableRow);
            }
        }
    }
}
