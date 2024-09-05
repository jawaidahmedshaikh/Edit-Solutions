/*
 * User: dlataille
 * Date: Aug 30, 2007
 * Time: 8:38:00 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import contract.QuestionnaireResponse;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import fission.global.AppReqBlock;

import fission.utility.Util;

import java.util.Arrays;

public class QuestionnaireResponseTableModel extends TableModel
{
    public static final String COLUMN_QUESTION = "Question";
    public static final String COLUMN_RESPONSE = "Response";
    public static final String COLUMN_EFFECTIVE_DATE = "Effective Date";
    public static final String COLUMN_FOLLOWUP_DATE = "Followup Date";

    private static final String[] COLUMN_NAMES = {COLUMN_QUESTION, COLUMN_RESPONSE, COLUMN_EFFECTIVE_DATE, COLUMN_FOLLOWUP_DATE};

    public QuestionnaireResponseTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        String contractClientPK = getAppReqBlock().getReqParm("selectedContractClientPK");
        
        String selectedRowId = Util.initString(getSelectedRowId(), "");
        
        if (contractClientPK != null && !contractClientPK.equals(""))
        {
            QuestionnaireResponse[] questionnaireResponses = QuestionnaireResponse.findBy_ContractClientFK(new Long(contractClientPK));
            
            for (QuestionnaireResponse questionnaireResponse:questionnaireResponses)
            {
                QuestionnaireResponseTableRow row = new QuestionnaireResponseTableRow(questionnaireResponse);
                
                if (questionnaireResponse.getQuestionnaireResponsePK().toString().equals(selectedRowId))
                {
                    row.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }
                
                super.addRow(row);
            }
        }
    }
}