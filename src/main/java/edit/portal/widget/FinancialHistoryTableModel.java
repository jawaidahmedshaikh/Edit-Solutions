package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.*;


import fission.global.*;
import fission.beans.*;
import fission.utility.*;

import javax.servlet.http.*;
import java.util.*;

import contract.*;
import event.*;
import engine.*;
import security.*;


public class FinancialHistoryTableModel extends TableModel
{
    public static final String COLUMN_EFFECTIVE_DATE = "Eff Date";
    public static final String COLUMN_TRANSACTION_TYPE = "Transaction Type";
    public static final String COLUMN_STATUS = "Status";
    public static final String COLUMN_GROSS_AMOUNT = "Gross Amt ";
    public static final String COLUMN_NET_AMOUNT = "Net Amt";
    public static final String COLUMN_ACCUMULATED_VALUE = "Accum Value";
    public static final String COLUMN_SURRENDER_CHARGE = "Surrender Chg";
    public static final String COLUMN_SURRENDER_VALUE = "Surrender Value";
    
    private static final String[] COLUMN_NAMES = {COLUMN_EFFECTIVE_DATE, COLUMN_TRANSACTION_TYPE, COLUMN_STATUS, COLUMN_GROSS_AMOUNT,
    		COLUMN_NET_AMOUNT, COLUMN_ACCUMULATED_VALUE, COLUMN_SURRENDER_CHARGE, COLUMN_SURRENDER_VALUE};

    public  FinancialHistoryTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Suspense summary rows
     */
    protected void buildTableRows()
    {
        AppReqBlock appReqBlock = this.getAppReqBlock();
        HttpServletRequest request = appReqBlock.getHttpServletRequest();

        SessionBean contractMainSessionBean = appReqBlock.getSessionBean("contractMainSessionBean");
        String contractNumber = contractMainSessionBean.getValue("contractId");
        
        String filterTransaction = Util.initString(request.getParameter("filterTransaction"), null);
        String filterDisplayUndo = Util.initString(request.getParameter("filterDisplayUndo"), "false");
        
        FinancialHistory[] financialHistoryRows = null;
        
        if (filterTransaction == null) {
        	if (filterDisplayUndo.equalsIgnoreCase("true")) {
        		financialHistoryRows = FinancialHistory.findAllByContractNumber(contractNumber);
        	} else {
        		financialHistoryRows = FinancialHistory.findActiveByContractNumber(contractNumber);
        	}
        } else {
        	if (filterDisplayUndo.equalsIgnoreCase("true")) {
        		financialHistoryRows = FinancialHistory.findAllByContractNumberAndTransactionType(contractNumber, filterTransaction);
        	} else {
        		financialHistoryRows = FinancialHistory.findActiveByContractNumberAndTransactionType(contractNumber, filterTransaction);
        	}
        }

        populateFinancialHistoryRows(financialHistoryRows);
    }

    private void populateFinancialHistoryRows(FinancialHistory[] financialHistoryRows)
    {
        if (financialHistoryRows != null)
        {
            for (int i = 0; i < financialHistoryRows.length; i++)
            {
                TableRow tableRow = new FinancialHistoryTableRow(financialHistoryRows[i]);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
