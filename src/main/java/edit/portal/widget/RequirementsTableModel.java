/*
 * User: sprasad
 * Date: Jun 2, 2005
 * Time: 12:54:00 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import casetracking.CaseRequirement;
import casetracking.usecase.CasetrackingUseCase;
import casetracking.usecase.CasetrackingUseCaseImpl;
import client.ClientDetail;
import contract.FilteredRequirement;
import contract.Requirement;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import engine.ProductStructure;
import fission.global.AppReqBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;



public class RequirementsTableModel extends TableModel
{
    public static final String COLUMN_REQUIREMENT_ID    = "Requirement Id";
    public static final String COLUMN_REQUIREMNT_DESC   = "Requirement Description";
    public static final String COLUMN_STATUS            = "Status";
    public static final String COLUMN_RECEIVED_DATE     = "Received Date";
    public static final String COLUMN_EFFECTIVE_DATE    = "Effective Date";

    private static final String[] COLUMN_NAMES = {COLUMN_REQUIREMENT_ID, COLUMN_REQUIREMNT_DESC, COLUMN_STATUS,
                                                  COLUMN_RECEIVED_DATE, COLUMN_EFFECTIVE_DATE};

    private ProductStructure productStructure;
    private ClientDetail clientDetail;


    public RequirementsTableModel(ProductStructure productStructure,
                                  ClientDetail clientDetail,
                                  AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        this.productStructure = productStructure;

        this.clientDetail = clientDetail;

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        if (clientDetail != null)
        {
            List caseRequirements = new ArrayList();

            FilteredRequirement[] filteredRequirements = FilteredRequirement.findBy_ProductStructure(productStructure);

            // determine if any of the filteredRequirements are associated to clientDetail.
            for(int i = 0; i < filteredRequirements.length; i++)
            {
                CaseRequirement caseRequirement = CaseRequirement.findBy_ClientDetail_And_FilteredRequirement(clientDetail, filteredRequirements[i]);

                if (caseRequirement != null)
                {
                    caseRequirements.add(caseRequirement);
                }
            }

            // if there are no associated caseRequirements at all, build new ones.
            // means there are no associated caseRequirements.
            if (caseRequirements.size() == 0)
            {
                CasetrackingUseCase casetrackingUseCase = new CasetrackingUseCaseImpl();

                casetrackingUseCase.associateNonManualFilteredRequirementsToClient(clientDetail, filteredRequirements);

                caseRequirements = new ArrayList(clientDetail.getCaseRequirements());
            }

            // eithere new caseRequirements or old caseRequiremnts display them.
            for (int i = 0; i < caseRequirements.size(); i++)
            {
                CaseRequirement caseRequirement = (CaseRequirement) caseRequirements.get(i);

                FilteredRequirement filteredRequirement = caseRequirement.getFilteredRequirement();

                Requirement requirement = filteredRequirement.getRequirement();

                TableRow tableRow = new RequirementsTableRow(requirement, caseRequirement);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
