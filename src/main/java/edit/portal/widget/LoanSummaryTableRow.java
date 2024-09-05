/*
 * 
 * User: cgleason
 * Date: Oct 16, 2007
 * Time: 9:52:39 AM
 * 
 * (c) 2000 - 2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.*;
import event.*;
import fission.utility.*;
import engine.*;
import contract.*;

public class LoanSummaryTableRow extends TableRow
{
    Bucket loanSummaryRow;


    public LoanSummaryTableRow(Bucket loanSummaryRow)
    {
        this.loanSummaryRow = loanSummaryRow;

        populateCellValues();
    }

    /**
      * Maps the values of History Filter to the TableRow.
      */
     private void populateCellValues()
     {
         getCellValues().put(LoanSummaryTableModel.COLUMN_LOAN_SOURCE, loanSummaryRow.getBucketSourceCT());


         String effectiveDate = loanSummaryRow.getDepositDate() == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(loanSummaryRow.getDepositDate());
         getCellValues().put(LoanSummaryTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate);

         String amount = loanSummaryRow.getLoanPrincipalRemaining().toString();
         String cellValueForAmount = "<script>document.write(formatAsCurrency(" + amount + "))</script>";
         getCellValues().put(LoanSummaryTableModel.COLUMN_PRINCIPAL_REMAINING, cellValueForAmount);

     }



    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return loanSummaryRow.getBucketPK().toString();
    }
}
