package edit.portal.widget;

import contract.FilteredRequirement;
import contract.Requirement;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import engine.ProductStructure;
import fission.global.AppReqBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import agent.Agent;
import agent.AgentRequirement;
import agent.component.AgentUseCaseComponent;
import agent.business.AgentUseCase;

/*
 * User: dlataille
 * Date: Jun 2, 2006
 * Time: 12:54:00 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

public class AgentRequirementsTableModel extends TableModel
{
    public static final String COLUMN_REQUIREMENT_ID    = "Requirement Id";
    public static final String COLUMN_REQUIREMNT_DESC   = "Requirement Description";
    public static final String COLUMN_STATUS            = "Status";
    public static final String COLUMN_RECEIVED_DATE     = "Received Date";
    public static final String COLUMN_EFFECTIVE_DATE    = "Effective Date";

    private List requirementsTableRows;
    private ProductStructure productStructure;
    private Agent agent;

    private String[] COLUMN_NAMES =
    {
        COLUMN_REQUIREMENT_ID, COLUMN_REQUIREMNT_DESC, COLUMN_STATUS, COLUMN_RECEIVED_DATE, COLUMN_EFFECTIVE_DATE
    };

    public AgentRequirementsTableModel(ProductStructure productStructure,
                                       Agent agent,
                                       AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        requirementsTableRows = new ArrayList();

        this.productStructure = productStructure;

        this.agent = agent;
        
        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    public void buildTableRows()
    {
        if (agent != null)
        {
            List agentRequirements = new ArrayList();

            FilteredRequirement[] filteredRequirements = FilteredRequirement.findBy_ProductStructure(productStructure);

            // determine if any of the filteredRequirements are associated with the agent.
            for(int i = 0; i < filteredRequirements.length; i++)
            {
                AgentRequirement agentRequirement = AgentRequirement.findBy_Agent_And_FilteredRequirement(agent, filteredRequirements[i]);

                if (agentRequirement != null)
                {
                    agentRequirements.add(agentRequirement);
                }
            }

            // if there are no associated agentRequirements at all, build new ones.
            if (agentRequirements.size() == 0)
            {
                AgentUseCase agentUseCase = new AgentUseCaseComponent();

                agentUseCase.associateRequirementsToAgent(agent, filteredRequirements);

                agentRequirements = new ArrayList(agent.getAgentRequirements());
            }

            // either new agentRequirements or old agentRequiremnts display them.
            for (int i = 0; i < agentRequirements.size(); i++)
            {
                AgentRequirement agentRequirement = (AgentRequirement) agentRequirements.get(i);

                FilteredRequirement filteredRequirement = agentRequirement.getFilteredRequirement();

                Requirement requirement = filteredRequirement.getRequirement();

                TableRow tableRow = new AgentRequirementsTableRow(requirement, agentRequirement);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
