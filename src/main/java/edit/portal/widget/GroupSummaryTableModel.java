/*
 * User: cgleason
 * Date: Dec 8, 2005
 * Time: 3:28:25 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import fission.global.*;
import fission.utility.*;

import java.util.*;

import contract.*;
import group.*;


public class GroupSummaryTableModel extends TableModel
{
    public static final String COLUMN_GROUP_NUMBER = "Group Number";
    public static final String COLUMN_GROUP_NAME = "Group Name";
    public static final String COLUMN_EFFECTIVE_DATE = "Effective Date";
    public static final String COLUMN_TERMINATION_DATE = "Termination Date";

    private static final String[] COLUMN_NAMES = {COLUMN_GROUP_NUMBER, COLUMN_GROUP_NAME, COLUMN_EFFECTIVE_DATE, COLUMN_TERMINATION_DATE};


    public GroupSummaryTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        String activeCasePK = getAppReqBlock().getUserSession().getParameter("activeCasePK");
        
        if (activeCasePK != null)
        {
            String selectedGroupPK = getAppReqBlock().getUserSession().getParameter("activeGroupPK");
        
            ContractGroup[] groupContractGroups = ContractGroup.findBy_ContractGroupFK_ContractGroupTypeCT(new Long(activeCasePK), ContractGroup.CONTRACTGROUPTYPECT_GROUP);
            
            for (ContractGroup groupContractGroup:groupContractGroups)
            {
                GroupSummaryTableRow row = new GroupSummaryTableRow(groupContractGroup);
                
                if (row.getRowId().equals(selectedGroupPK))
                {
                    row.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }
                
                addRow(row);
            }
        }
    }
}
