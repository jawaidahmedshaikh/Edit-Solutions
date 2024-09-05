/*
 * 
 * User: cgleason
 * Date: Jun 18, 2007
 * Time: 10:36:56 AM
 * 
 * (c) 2000 - 2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package edit.portal.widget;

import fission.global.*;
import fission.utility.Util;

import java.util.*;

import edit.portal.widgettoolkit.*;
import productbuild.*;

public class CloneQuestionnaireTableModel  extends TableModel
{
    public static final String COLUMN_DISPLAY_ORDER             = "Display Order";
    public static final String COLUMN_QUESTIONNAIRE_ID          = "Questionnaire Id";
    public static final String COLUMN_QUESTIONNAIRE_DESCRIPTION = "Questionnaire Description";

    private static final String[] COLUMN_NAMES = {COLUMN_DISPLAY_ORDER, COLUMN_QUESTIONNAIRE_ID, COLUMN_QUESTIONNAIRE_DESCRIPTION};

    private Long productStructurePK;

    public  CloneQuestionnaireTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        productStructurePK = null;

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    public  CloneQuestionnaireTableModel(AppReqBlock appReqBlock, Long productStructurePK)
    {
        super(appReqBlock);

        this.productStructurePK= productStructurePK;

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    
    protected void buildTableRows()
    {
        Questionnaire[] questionnaires = Questionnaire.findAllQuestionnaires();
        String activeQuestionnairePK = Util.initString((String) getAppReqBlock().getHttpServletRequest().getAttribute("activeQuestionnairePK"), "");
        if (questionnaires != null)
        {
            for (int i = 0; i < questionnaires.length; i++)
            {
                Questionnaire questionnaire = questionnaires[i];
                FilteredQuestionnaire filteredQuestionnaire = FilteredQuestionnaire.findByQuestionnaire_ProductStructure(questionnaire.getQuestionnairePK(), productStructurePK);

                TableRow tableRow =   new CloneQuestionnaireTableRow(questionnaire, filteredQuestionnaire);

                if (filteredQuestionnaire != null)
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_ASSOCIATED);
                }

                super.addRow(tableRow);

                if (tableRow.getRowId().equals(activeQuestionnairePK))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }
            }
        }
    }
}
