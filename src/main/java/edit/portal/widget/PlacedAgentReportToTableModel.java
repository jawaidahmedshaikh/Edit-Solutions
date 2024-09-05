/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jan 9, 2006
 * Time: 6:24:44 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import agent.AgentGroup;
import agent.PlacedAgent;
import agent.PlacedAgentBranch;
import agent.AgentGroupAssociation;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import edit.portal.widgettoolkit.TableModel;
import edit.services.db.hibernate.SessionHelper;
import fission.global.AppReqBlock;
import fission.utility.Util;

import java.util.*;

public class PlacedAgentReportToTableModel extends TableModel
{
    public static final String COLUMN_AGENT_NUMBER = "Agent #";

    public static final String COLUMN_AGENT_NAME = "Agent Name";

    public static final String COLUMN_REPORT_TO_NUMBER = "Report To #";

    public static final String COLUMN_REPORT_TO_NAME = "Report To Name";

    private AgentGroup agentGroup;

    public PlacedAgentReportToTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        Long agentGroupPK = Util.initLong(appReqBlock.getReqParm("agentGroupPK"), null);

        agentGroup = AgentGroup.findBy_PK(agentGroupPK);

        // Set column names
        getColumnNames().add(COLUMN_AGENT_NUMBER);
        getColumnNames().add(COLUMN_AGENT_NAME);
        getColumnNames().add(COLUMN_REPORT_TO_NUMBER);
        getColumnNames().add(COLUMN_REPORT_TO_NAME);
    }

    /**
     * Builds the appropriate TableRows based on the selection criteria of Agent# or AgentName.
     */
    public void buildTableRows()
    {
        PlacedAgentBranch[] placedAgentBranches = new PlacedAgentBranch[0];

        String contractCodeCT = agentGroup.getContractCodeCT();

        String agentNumber = Util.initString(getAppReqBlock().getReqParm("agentNumber"), null);

        String agentName = Util.initString(getAppReqBlock().getReqParm("agentName"), null);

        AgentGroupAssociation[] agentGroupAssociations = AgentGroupAssociation.findBy_AgentGroup(agentGroup);

        // A little kludgey, but if the AgentNumber and AgentName BOTH exist, then this page is being rendered
        // from the call of a prior page which had these fields [populated] in its form. They are both being submitted.
        if ((agentNumber != null) && (agentName == null))
        {
            placedAgentBranches = PlacedAgentBranch.findBy_AgentId_AND_ContractCodeCT(agentNumber, contractCodeCT, 2);
        }
        if ((agentNumber == null) && (agentName != null))
        {
            placedAgentBranches = PlacedAgentBranch.findBy_AgentName_AND_ContractCodeCT(agentName, contractCodeCT, 2);
        }

        for (int i = 0; i < placedAgentBranches.length; i++)
        {
            PlacedAgentBranch placedAgentBranch = placedAgentBranches[i];

            PlacedAgent writingAgent = placedAgentBranch.getLeaf();

            if (!writingAgentAssociated(writingAgent, agentGroupAssociations))
            {
                PlacedAgent reportToAgent = null;

                if (placedAgentBranch.getPlacedAgents().length > 0)
                {
                    reportToAgent = placedAgentBranch.getPlacedAgent(0);
                }

                PlacedAgentReportToTableRow tableRow = new PlacedAgentReportToTableRow(writingAgent, reportToAgent);

                getRows().add(tableRow);
            }
        }

        Collections.sort(getRows());
    }

    /**
     * Checks to see if the specified writing agent (PlacedAgent) is alread associated with the
     * current AgentGroup in its set of AgentGroupAssocations.
     * @param agentGroupAssociations
     * @return true if the specified writing agent is in the set of AgentGroupAssociations.
     */
    private boolean writingAgentAssociated(PlacedAgent writingAgent, AgentGroupAssociation[] agentGroupAssociations)
    {
        boolean writingAgentAssociated = false;

        for (int i = 0; i < agentGroupAssociations.length; i++)
        {
            AgentGroupAssociation agentGroupAssociation = agentGroupAssociations[i];

            if (agentGroupAssociation.getPlacedAgent().getPlacedAgentPK().equals(writingAgent.getPlacedAgentPK()))
            {
                writingAgentAssociated = true;

                break;
            }
        }

        return writingAgentAssociated;
    }
}
