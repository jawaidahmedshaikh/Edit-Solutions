/*
 * User: cgleason
 * Date: Apr 14, 2008
 * Time: 9:28:31 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.CodeTableWrapper;

import fission.utility.*;
import contract.*;
import event.*;

import java.util.*;

public class SuspenseTransferTableRow extends TableRow
{
    String suspenseTransferRow;


    public SuspenseTransferTableRow(String suspenseTransferRow)
    {
        this.suspenseTransferRow = suspenseTransferRow;
        populateCellValues();
    }

   /**
     * Maps the values of cloudLand to the TableRow.
     */
    private void populateCellValues()
    {
        StringTokenizer tokens = new StringTokenizer(suspenseTransferRow, "_");

        String rowId = tokens.nextToken();
        String contractNumber = tokens.nextToken();
        String transferAmount = tokens.nextToken();

        String cellValueForTransferAmount = "<script>document.write(formatAsCurrency(" + transferAmount + "))</script>";

        getCellValues().put(SuspenseTransferTableModel.COLUMN_CONTRACT_NUMBER, contractNumber);
        getCellValues().put(SuspenseTransferTableModel.COLUMN_AMOUNT, cellValueForTransferAmount);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        StringTokenizer tokens = new StringTokenizer(suspenseTransferRow, "_");

        String rowId = tokens.nextToken();
        return rowId;
    }
}
