/*
 * User: dlataill
 * Date: Aug 2, 2005
 * Time: 9:43:35 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;
import edit.common.EDITBigDecimal;
import event.Suspense;
import fission.utility.*;


public class CashBatchContractSummaryTableRow extends TableRow
{
    private Suspense suspense;

    private String appliedIcon = "<img src=\"/PORTAL/common/images/blackCheck.gif\">";
    private String voidedIcon = "<img src=\"/PORTAL/common/images/redX.gif\">";
    private String noIcon = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";


    public CashBatchContractSummaryTableRow(Suspense suspense)
    {
        super();

        this.suspense = suspense;

        populateCellValues();
    }

    /**
     * Maps the values of Suspense to the TableRow
     */
    private void populateCellValues()
    {
        String policyNumber = suspense.getUserDefNumber();

        String lastName = Util.initString(suspense.getLastName(), "");
        String firstName = Util.initString(suspense.getFirstName(), "");
        String corporateName = Util.initString(suspense.getCorporateName(), "");
        String name = "";
        if (lastName == null || lastName.equals(""))
        {
            name = corporateName;
        }
        else
        {
            if (firstName == null || firstName.equals(""))
            {
                name = lastName;
            }
            else
            {
                name = lastName + ", " + firstName;
            }
        }

        String amount = suspense.getOriginalAmount().toString();
        String cellValueForAmount = "<script>document.write(formatAsCurrency(" + amount + "))</script>";

        String pendingAmount = suspense.getPendingSuspenseAmount().toString();
        String cellValueForPendingAmt = "<script>document.write(formatAsCurrency(" + pendingAmount + "))</script>";

        String refundAmount = suspense.getRefundAmount().toString();
        String cellValueForRefundAmt = "<script>document.write(formatAsCurrency(" + refundAmount + "))</script>";

        if (suspense.getMaintenanceInd().equalsIgnoreCase("A") ||
            suspense.getPendingSuspenseAmount().isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
        {
            getCellValues().put(CashBatchContractSummaryTableModel.COLUMN_POLICY_NUMBER, appliedIcon + " " + policyNumber);
        }
        else if (suspense.getMaintenanceInd().equalsIgnoreCase("V"))
        {
            getCellValues().put(CashBatchContractSummaryTableModel.COLUMN_POLICY_NUMBER, voidedIcon + " " + policyNumber);
        }
        else
        {
            getCellValues().put(CashBatchContractSummaryTableModel.COLUMN_POLICY_NUMBER, noIcon + policyNumber);
        }

        getCellValues().put(CashBatchContractSummaryTableModel.COLUMN_LAST_NAME, name);
        getCellValues().put(CashBatchContractSummaryTableModel.COLUMN_AMOUNT, cellValueForAmount);
        getCellValues().put(CashBatchContractSummaryTableModel.COLUMN_PENDING_AMOUNT, cellValueForPendingAmt);
        getCellValues().put(CashBatchContractSummaryTableModel.COLUMN_REFUND_AMOUNT, cellValueForRefundAmt);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return suspense.getSuspensePK().toString();
    }
}
