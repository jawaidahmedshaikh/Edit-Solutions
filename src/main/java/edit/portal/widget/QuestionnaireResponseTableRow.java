/*
 * User: dlataille
 * Date: Aug 30, 2007
 * Time: 8:39:15 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import edit.common.EDITDate;

import contract.QuestionnaireResponse;
import fission.utility.DateTimeUtil;

public class QuestionnaireResponseTableRow extends TableRow
{
    /**
     * The associated QuestionnaireResponse to render in the table row.
     */
    private QuestionnaireResponse questionnaireResponse;

    public QuestionnaireResponseTableRow(QuestionnaireResponse questionnaireResponse)
    {
        super();

        this.questionnaireResponse = questionnaireResponse;

        populateCellValues();
    }

    /**
     * Maps the values of requirement and caseRequirement to the TableRow.
     */
    private void populateCellValues()
    {
        EDITDate effDate = getQuestionnaireResponse().getEffectiveDate();
        EDITDate fuDate = getQuestionnaireResponse().getFollowupDate();

        String effectiveDate = "";
        String followupDate = "";

        if (effDate != null)
        {
            effectiveDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(effDate);
        }

        if (fuDate != null)
        {
            followupDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(fuDate);
        }

        getCellValues().put(QuestionnaireResponseTableModel.COLUMN_QUESTION, getQuestionnaireResponse().getFilteredQuestionnaire().getQuestionnaire().getQuestionnaireDescription());

        getCellValues().put(QuestionnaireResponseTableModel.COLUMN_RESPONSE, getQuestionnaireResponse().getResponseCT());

        getCellValues().put(QuestionnaireResponseTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate);

        getCellValues().put(QuestionnaireResponseTableModel.COLUMN_FOLLOWUP_DATE, followupDate);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return getQuestionnaireResponse().getQuestionnaireResponsePK().toString();
    }

    /**
     * @see #contractGroupRequirement
     * @return
     */
    public QuestionnaireResponse getQuestionnaireResponse()
    {
        return questionnaireResponse;
    }
}
