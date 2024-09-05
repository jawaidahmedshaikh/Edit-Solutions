/*
 * User: sdorman
 * Date: 1/31/2006
 * Time: 9:7:58
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;

import event.*;
import contract.*;
import engine.*;
import fission.utility.*;
import group.*;



public class CaseTransactionTableRow extends TableRow
{
    private EDITTrx editTrx;
    private ProductStructure productStructure;
    private Company company;
    private ContractGroup theCase;


    public CaseTransactionTableRow(ProductStructure productStructure, ContractGroup theCase, EDITTrx editTrx)
    {
        super();

        this.editTrx = editTrx;
        this.productStructure = productStructure;
        this.theCase = theCase;
        this.company = productStructure.getCompany();

        populateCellValues();
    }

    /**
     * Builds the Map of cell values with cell name/value pairs.
     */
    private void populateCellValues()
    {
        getCellValues().put(CaseTransactionTableModel.COLUMN_COMPANY_NAME, company.getCompanyName());
        getCellValues().put(CaseTransactionTableModel.COLUMN_CASE_NUMBER, theCase.getContractGroupNumber());
        getCellValues().put(CaseTransactionTableModel.COLUMN_EFFECTIVE_DATE, DateTimeUtil.formatEDITDateAsMMDDYYYY(editTrx.getEffectiveDate()));
        getCellValues().put(CaseTransactionTableModel.COLUMN_SEQUENCE, new Integer(editTrx.getSequenceNumber()).toString());
        getCellValues().put(CaseTransactionTableModel.COLUMN_STATUS, editTrx.getStatus());
        getCellValues().put(CaseTransactionTableModel.COLUMN_TRANSACTION, editTrx.getTransactionTypeCT());
        getCellValues().put(CaseTransactionTableModel.COLUMN_AMOUNT, editTrx.getTrxAmount().toString());
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return editTrx.getEDITTrxPK().toString();
    }
}
