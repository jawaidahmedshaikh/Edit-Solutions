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

import java.util.*;

import edit.portal.widgettoolkit.*;
import productbuild.*;

public class QuestionnaireTableModel  extends TableModel
{
    public static final String COLUMN_QUESTIONNAIRE_ID          = "Questionnaire Id";
    public static final String COLUMN_QUESTIONNAIRE_DESCRIPTION = "Questionnaire Description";
    public static final String COLUMN_AREA = "Area";

    private static final String[] COLUMN_NAMES = {COLUMN_QUESTIONNAIRE_ID, COLUMN_QUESTIONNAIRE_DESCRIPTION, COLUMN_AREA};

    private Questionnaire questionnaire;

    private Long productStructurePK;

    public  QuestionnaireTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        productStructurePK = null;

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    public  QuestionnaireTableModel(AppReqBlock appReqBlock, Long productStructurePK)
    {
        super(appReqBlock);

        this.productStructurePK= productStructurePK;

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    public QuestionnaireTableModel(AppReqBlock appReqBlock, Questionnaire questionnaire)
    {
        super(appReqBlock);

        this.questionnaire = questionnaire;
        
        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

    }

    protected void buildTableRows()
    {
        Questionnaire[] questionnaires = Questionnaire.findAllQuestionnaires();

        Questionnaire activeQuestionnaire = (Questionnaire) getAppReqBlock().getHttpServletRequest().getAttribute("activeQuestionnaire");

        String selectedQuestionnairePK = "";
        if (activeQuestionnaire != null)
        {
            selectedQuestionnairePK = activeQuestionnaire.getQuestionnairePK().toString();
        }

        if (questionnaires != null)
        {
            for (int i = 0; i < questionnaires.length; i++)
            {
                TableRow tableRow = new QuestionnaireTableRow(questionnaires[i]);

                super.addRow(tableRow);

                if (tableRow.getRowId().equals(selectedQuestionnairePK))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }
            }
        }

        if (productStructurePK != null)
        {
            Questionnaire[] filteredQuestionnaires = Questionnaire.findAllAssociated(productStructurePK);

            if (filteredQuestionnaires != null)
            {
                for (int i = 0; i < filteredQuestionnaires.length; i++)
                {
                    Long filteredQuestionnairePK = filteredQuestionnaires[i].getQuestionnairePK();

                    TableRow tableRow = super.getRow(filteredQuestionnairePK.toString());

                    if (tableRow != null)
                    {
                        tableRow.setRowStatus(TableRow.ROW_STATUS_ASSOCIATED);
                    }
                }
            }
        } // end if
    }
}
