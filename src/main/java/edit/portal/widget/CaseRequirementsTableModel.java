/*
 * User: dlataille
 * Date: April 30, 2007
 * Time: 1:53:00 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import casetracking.CaseRequirement;
import casetracking.usecase.CasetrackingUseCase;
import casetracking.usecase.CasetrackingUseCaseImpl;
import client.ClientDetail;

import group.ContractGroupRequirement;
import contract.FilteredRequirement;
import contract.Requirement;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import engine.ProductStructure;
import fission.global.AppReqBlock;

import fission.utility.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;



public class CaseRequirementsTableModel extends TableModel
{
    public static final String COLUMN_BATCH_ID          = "Batch Id";
    public static final String COLUMN_REQUIREMENT_ID    = "Requirement Id";
    public static final String COLUMN_REQUIREMNT_DESC   = "Requirement Description";
    public static final String COLUMN_STATUS            = "Status";
    public static final String COLUMN_EFFECTIVE_DATE    = "Effective Date";
    public static final String COLUMN_PRODUCT_STRUCTURE = "Product Structure";

    private static final String[] COLUMN_NAMES = {COLUMN_BATCH_ID, COLUMN_REQUIREMENT_ID, COLUMN_REQUIREMNT_DESC, COLUMN_STATUS, COLUMN_EFFECTIVE_DATE, COLUMN_PRODUCT_STRUCTURE};

    private ProductStructure productStructure = null;
    
    private ClientDetail clientDetail = null;


    public CaseRequirementsTableModel(AppReqBlock appReqBlock)
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
        
        String selectedRowId = Util.initString(getSelectedRowId(), "");
        
        if (activeCasePK != null)
        {
            ContractGroupRequirement[] contractGroupRequirements = ContractGroupRequirement.findBy_ContractGroupPK(new Long(activeCasePK));
            
            for (ContractGroupRequirement contractGroupRequirement:contractGroupRequirements)
            {
                CaseRequirementsTableRow row = new CaseRequirementsTableRow(contractGroupRequirement);  
                
                if (contractGroupRequirement.getContractGroupRequirementPK().toString().equals(selectedRowId))
                {
                    row.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }
                
                super.addRow(row);
            }
        }
    }
}
