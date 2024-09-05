/*
 * 
 * User: cgleason
 * Date: Jun 18, 2007
 * Time: 10:53:45 AM
 * 
 * (c) 2000 - 2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import productbuild.*;

public class CloneQuestionnaireTableRow extends TableRow
{
    private FilteredQuestionnaire filteredQuestionnaire;
    private Questionnaire questionnaire;

    public CloneQuestionnaireTableRow(FilteredQuestionnaire filteredQuestionnaire)
    {
        super();

        this.filteredQuestionnaire = filteredQuestionnaire;

        populateCellValues();
    }

    public CloneQuestionnaireTableRow(Questionnaire questionnaire, FilteredQuestionnaire filteredQuestionnaire)
    {
        super();

        this.questionnaire = questionnaire;
        this.filteredQuestionnaire = filteredQuestionnaire;

        populateCellValues();
    }

    /**
     * Set data into the page
     */
    private void populateCellValues()
    {
        if (filteredQuestionnaire != null)
        {
            getCellValues().put(CloneQuestionnaireTableModel.COLUMN_DISPLAY_ORDER, filteredQuestionnaire.getDisplayOrder());
        }

        getCellValues().put(CloneQuestionnaireTableModel.COLUMN_QUESTIONNAIRE_ID, questionnaire.getQuestionnaireId());

        getCellValues().put(CloneQuestionnaireTableModel.COLUMN_QUESTIONNAIRE_DESCRIPTION, questionnaire.getQuestionnaireDescription());
    }

  /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return questionnaire.getQuestionnairePK().toString();
    }
}
