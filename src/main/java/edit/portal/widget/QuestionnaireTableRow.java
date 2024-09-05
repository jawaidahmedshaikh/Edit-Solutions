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

public class QuestionnaireTableRow extends TableRow
{
    private Questionnaire questionnaire;

    public QuestionnaireTableRow(Questionnaire questionnaire)
    {
        super();

        this.questionnaire = questionnaire;

        populateCellValues();
    }

    /**
     * Set data into the page
     */
    private void populateCellValues()
    {
        getCellValues().put(QuestionnaireTableModel.COLUMN_QUESTIONNAIRE_ID, questionnaire.getQuestionnaireId());

        getCellValues().put(QuestionnaireTableModel.COLUMN_QUESTIONNAIRE_DESCRIPTION, questionnaire.getQuestionnaireDescription());

        getCellValues().put(QuestionnaireTableModel.COLUMN_AREA, questionnaire.getAreaCT());
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
