/*
 * User: cgleason
 * Date: Apr 09, 2008
 * Time: 9:07:24 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.*;
import edit.common.vo.*;
import edit.services.db.hibernate.*;


import fission.global.*;
import fission.beans.*;
import fission.utility.*;

import javax.servlet.http.*;
import java.util.*;

import contract.*;
import event.*;
import engine.*;
import security.*;


public class CommissionPhaseHistoryTableModel extends TableModel
{
    public static final String COLUMN_COMMISSION_PHASE_ID       = "Commission Phase ID";
    public static final String COLUMN_EFFECTIVE_DATE            = "Effective Date";
    public static final String COLUMN_EXPECTED_MONTHLY_PREMIUM  = "Expected Monthly Premium";
    public static final String COLUMN_AGENT_NUMBER              = "Agent Number";

    private static final String[] COLUMN_NAMES = {COLUMN_COMMISSION_PHASE_ID, COLUMN_EFFECTIVE_DATE, COLUMN_EXPECTED_MONTHLY_PREMIUM, COLUMN_AGENT_NUMBER};



    public  CommissionPhaseHistoryTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Suspense summary rows
     */
    protected void buildTableRows()
    {
        AppReqBlock appReqBlock = this.getAppReqBlock();

        String premiumDuePK = (String)appReqBlock.getHttpServletRequest().getAttribute("selectedPremiumDuePK");

        PremiumDue premiumDue = PremiumDue.findByPK(new Long(premiumDuePK));
        Segment segment = premiumDue.getSegment();
        Set<AgentHierarchy> agentHierarchies = segment.getAgentHierarchies();

        CommissionPhase[] commissionPhases = CommissionPhase.findAllByPremiumDueFK(new Long(premiumDuePK));

        commissionPhases = (CommissionPhase[])Util.sortObjects(commissionPhases, new String[] {"getEffectiveDate"});

        populateCommissionPhaseHistoryRows(commissionPhases, agentHierarchies);
    }

    private void populateCommissionPhaseHistoryRows(CommissionPhase[] commissionPhases, Set<AgentHierarchy> agentHierarchies)
    {
        if (commissionPhases != null)
        {
            for (int i = 0; i < commissionPhases.length; i++)
            {
                Iterator it = agentHierarchies.iterator();

                while (it.hasNext())
                {
                    AgentHierarchy agentHierarchy = (AgentHierarchy) it.next();

                    Set<AgentSnapshot> agentSnapshots = agentHierarchy.getAgentSnapshots();
                    Iterator it2 = agentSnapshots.iterator();
                    while (it2.hasNext())
                    {
                        AgentSnapshot agentSnapshot = (AgentSnapshot) it2.next();
                        String agentNumber = agentSnapshot.getPlacedAgent().getClientRole().getReferenceID();

                        TableRow tableRow = new CommissionPhaseHistoryTableRow(commissionPhases[i], agentNumber);

                        if (tableRow.getRowId().equals(this.getSelectedRowId()))
                        {
                            tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                        }

                        super.addRow(tableRow);
                    }
                }
            }
        }
    }
}
