/*
 * 
 * User: cgleason
 * Date: Oct 16, 2007
 * Time: 9:25:36 AM
 * 
 * (c) 2000 - 2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package edit.portal.widget;

import fission.global.*;
import fission.beans.*;
import fission.utility.*;

import java.util.*;

import edit.portal.widgettoolkit.*;
import edit.common.*;

import javax.servlet.http.*;

import event.*;
import contract.*;

public class QuoteTransactionTableModel extends TableModel
{
    public static final String COLUMN_COMPANY          = "Company";
    public static final String COLUMN_CONTRACT_NUMBER  = "ContractNumber";
    public static final String COLUMN_EFFECTIVE_DATE   = "Effective Date";
    public static final String COLUMN_TRAN_TYPE        = "Tran Type";
    public static final String COLUMN_SEQUENCE         = "Seq";
    public static final String COLUMN_STATUS           = "Status";
    public static final String COLUMN_AMOUNT           = "Amount";
    public static final String COLUMN_COVERAGE_RIDER   = "Coverage/Rider";

    private Segment segment;

    private static final String[] COLUMN_NAMES = {COLUMN_COMPANY, COLUMN_CONTRACT_NUMBER, COLUMN_EFFECTIVE_DATE,
                                                  COLUMN_TRAN_TYPE, COLUMN_SEQUENCE, COLUMN_STATUS, COLUMN_AMOUNT, COLUMN_COVERAGE_RIDER};


    public QuoteTransactionTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

    }

    public  QuoteTransactionTableModel(Segment segment, AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        this.segment = segment;

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

    }

    /**
     * Builds the superset TableModel driven by the Segment
     */
    protected void buildTableRows()
    {
        AppReqBlock appReqBlock = this.getAppReqBlock();
        HttpServletRequest request = appReqBlock.getHttpServletRequest();

        //first get all the edit trx history
        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");

        String productStructureId = Util.initString(quoteMainFormBean.getValue("companyStructureId"), "0");
        String contractNumber = segment.getContractNumber();
        String optionCode = Util.initString(quoteMainFormBean.getValue("optionId"), "");
        EDITTrx[] quoteTransactionTableRows = null;


        quoteTransactionTableRows = EDITTrx.findBy_SegmentPK_PendingStatus_TransactionType(segment.getSegmentPK(), "P");

        populateTransactionRows(quoteTransactionTableRows, new Long(productStructureId), contractNumber, optionCode);
    }

    private void populateTransactionRows(EDITTrx[] quoteTransactionTableRows, Long productStructureId, String contractNumber, String optionCode)
    {
        if (quoteTransactionTableRows != null)
        {
            for (int i = 0; i < quoteTransactionTableRows.length; i++)
            {
                TableRow tableRow = new QuoteTransactionTableRow(quoteTransactionTableRows[i], productStructureId, contractNumber, optionCode);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
