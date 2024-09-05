/*
 * User: dlataille
 * Date: July 18, 2007
 * Time: 12:22:14 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import group.*;
import fission.utility.*;


public class
        CaseSetupTableRow extends TableRow
{

    private CaseSetup caseSetup;


    public CaseSetupTableRow(CaseSetup caseSetup)
    {
        super();

        this.caseSetup = caseSetup;

        populateCellValues();
    }

    /**
     * Maps the values of Master to the TableRow.
     */
    private void populateCellValues()
    {
        String stateCT = caseSetup.getStateCT();

        String caseSetupOptionCT = caseSetup.getCaseSetupOptionCT();

        EDITDate effectiveDate = caseSetup.getEffectiveDate();

        String caseSetupOptionValueCT = caseSetup.getCaseSetupOptionValueCT();

        getCellValues().put(CaseSetupTableModel.COLUMN_EFFECTIVE_DATE, DateTimeUtil.formatEDITDateAsMMDDYYYY(effectiveDate));

        getCellValues().put(CaseSetupTableModel.COLUMN_OPTION, caseSetupOptionCT);

        getCellValues().put(CaseSetupTableModel.COLUMN_VALUE, caseSetupOptionValueCT);

        getCellValues().put(CaseSetupTableModel.COLUMN_STATE, stateCT);

    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return caseSetup.getCaseSetupPK().toString();
    }
}
