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

import group.*;

public class CaseSetupTableModel extends TableModel
{
    public static final String COLUMN_EFFECTIVE_DATE = "Effective Date";
    public static final String COLUMN_OPTION = "Option";
    public static final String COLUMN_VALUE = "Value";
    public static final String COLUMN_STATE = "State";

    private static final String[] COLUMN_NAMES = {COLUMN_EFFECTIVE_DATE, COLUMN_OPTION, COLUMN_VALUE, COLUMN_STATE};

    public CaseSetupTableModel(AppReqBlock appReqBlock)
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
            CaseSetup[] caseSetups = CaseSetup.findByContractGroup(new Long(caseContractGroupPK));
            for (int i = 0; i < caseSetups.length; i++)
            {
                CaseSetupTableRow tableRow = new CaseSetupTableRow(caseSetups[i]);
                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }
                super.addRow(tableRow);
            }
        }
    }
}
