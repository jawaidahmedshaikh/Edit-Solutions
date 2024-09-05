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

public class QuoteTransactionTableRow extends TableRow
{
    EDITTrx transactionRow;

    Long productStructureId;

    String contractNumber;

    String optionCode;

    public QuoteTransactionTableRow(EDITTrx quoteTransactionTableRow, Long productStructureId, String contractNumber, String optionCode)
    {
        this.transactionRow = quoteTransactionTableRow;

        this.productStructureId = productStructureId;

        this.contractNumber = contractNumber;

        this.optionCode = optionCode;

        populateCellValues();
    }

    /**
      * Maps the values of History Filter to the TableRow.
      */
     private void populateCellValues()
     {
         Company company = Company.findByProductStructurePK(productStructureId);
         getCellValues().put(QuoteTransactionTableModel.COLUMN_COMPANY, company.getCompanyName());

         getCellValues().put(QuoteTransactionTableModel.COLUMN_CONTRACT_NUMBER, contractNumber);

         String effectiveDate = transactionRow.getEffectiveDate() == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(transactionRow.getEffectiveDate());
         getCellValues().put(QuoteTransactionTableModel.COLUMN_EFFECTIVE_DATE, effectiveDate);

         getCellValues().put(QuoteTransactionTableModel.COLUMN_TRAN_TYPE, CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("TRXTYPE", transactionRow.getTransactionTypeCT()));

         String trxAmount = transactionRow.getTrxAmount().toString();
         String cellValueForTrxAmount = "<script>document.write(formatAsCurrency(" + trxAmount + "))</script>";

         getCellValues().put(QuoteTransactionTableModel.COLUMN_SEQUENCE, transactionRow.getSequenceNumber() + "");
         getCellValues().put(QuoteTransactionTableModel.COLUMN_STATUS, Util.initString(transactionRow.getStatus(), ""));
         getCellValues().put(QuoteTransactionTableModel.COLUMN_AMOUNT, cellValueForTrxAmount);
         getCellValues().put(QuoteTransactionTableModel.COLUMN_COVERAGE_RIDER, optionCode);
     }



    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return transactionRow.getEDITTrxPK().toString();
    }
}
