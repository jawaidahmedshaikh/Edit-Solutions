/*
 * User: cgleason
 * Date: Apr 09, 2008
 * Time: 9:07:24 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
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


public class FilterSuspenseTableModel extends TableModel
{
    public static final String COLUMN_EFFECTIVE_DATE = "Eff Date";
    public static final String COLUMN_USER_NUMBER    = "User #";
    public static final String COLUMN_LAST_NAME      = "Last Name";
    public static final String COLUMN_AMOUNT         = "Amount";
    public static final String COLUMN_DIRECTION      = "Direction";
    public static final String COLUMN_OPERATOR       = "Operator";
    public static final String COLUMN_BATCH_ID       = "Batch Id";
    public static final String COLUMN_GROUPNUMBER    = "Group #";
    public static final String COLUMN_TRX_TYPE       = "Trx Type";
    public static final String COLUMN_STATUS         = "Status";
    public static final String COLUMN_REASON_CODE    = "Reason Code";

    private static final String[] COLUMN_NAMES = {COLUMN_EFFECTIVE_DATE, COLUMN_USER_NUMBER, COLUMN_LAST_NAME,
                                                  COLUMN_AMOUNT, COLUMN_DIRECTION, COLUMN_OPERATOR, COLUMN_BATCH_ID,
                                                  COLUMN_GROUPNUMBER, COLUMN_TRX_TYPE, COLUMN_STATUS, COLUMN_REASON_CODE};


    private EDITBigDecimal totalSuspenseAmount  = new EDITBigDecimal();
    private EDITBigDecimal premiumSuspense = new EDITBigDecimal();
    private int numberOfSuspenseItems = 0;

    public  FilterSuspenseTableModel(AppReqBlock appReqBlock)
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

        String fromDate = Util.initString(request.getParameter("fromDate"), null);
        String toDate = Util.initString(request.getParameter("toDate"), null);
        String filterPeriod = Util.initString(request.getParameter("filterPeriod"), null);
        String operator = Util.initString(request.getParameter("filterOperator"), null);
        String contractNumber = Util.initString(request.getParameter("filterUserNumber"), null);
        String reasonCode = Util.initString(request.getParameter("filterReasonCode"), null);

        if (fromDate == null && toDate == null && filterPeriod == null && operator == null && contractNumber == null && reasonCode == null)
        {
            fromDate = (String)appReqBlock.getHttpSession().getAttribute("fromDate");
            toDate = (String)appReqBlock.getHttpSession().getAttribute("toDate");
            filterPeriod = (String)appReqBlock.getHttpSession().getAttribute("filterPeriod");
            operator = (String)appReqBlock.getHttpSession().getAttribute("filterOperator");
            contractNumber = (String)appReqBlock.getHttpSession().getAttribute("filterUserNumber");
            reasonCode = (String)appReqBlock.getHttpSession().getAttribute("filterReasonCode");
        }
        else
        {
            appReqBlock.getHttpSession().setAttribute("fromDate", fromDate);
            appReqBlock.getHttpSession().setAttribute("toDate", toDate);
            appReqBlock.getHttpSession().setAttribute("filterPeriod", filterPeriod);
            appReqBlock.getHttpSession().setAttribute("filterOperator", operator);
            appReqBlock.getHttpSession().setAttribute("filterUserNumber", contractNumber);
            appReqBlock.getHttpSession().setAttribute("filterReasonCode", reasonCode);
            appReqBlock.getHttpSession().setAttribute("filterSet", "Y");
        }

        Suspense[] suspenseFilterRows = null;

        if (filterPeriod == null || filterPeriod.equals("0"))
        {
            if (fromDate != null && toDate != null)
            {
                EDITDate startDate = new EDITDate(fromDate);
                EDITDate endDate   = new EDITDate(toDate);
                suspenseFilterRows = Suspense.findSuspenseFilterRows(contractNumber, operator, startDate, endDate, reasonCode);
            }
            else
            {
                SessionBean contractMainSessionBean = appReqBlock.getSessionBean("contractMainSessionBean");
                contractNumber = contractMainSessionBean.getValue("contractId");
                ProductStructure[] productStructures = appReqBlock.getUserSession().getProductStucturesForUser();
                Operator defaultOperator = Operator.findByOperatorName(appReqBlock.getUserSession().getUsername());

                if (!contractNumber.equals(""))
                {
                    suspenseFilterRows = Suspense.findByUserDefNumber(contractNumber, defaultOperator, productStructures);
                }
                else
                {
                    suspenseFilterRows = Suspense.findAll(defaultOperator, productStructures);
                }
            }
        }
        else
        {
            suspenseFilterRows = Suspense.findSuspenseFilterRows(contractNumber, operator, filterPeriod, reasonCode);
        }

        if (suspenseFilterRows == null)
        {
            appReqBlock.getHttpServletRequest().setAttribute("suspenseMessage", "No Suspense Exists For User Def Number");
        }

        populateSuspenseFilterRows(suspenseFilterRows);
    }

    private void populateSuspenseFilterRows(Suspense[] suspenseFilterRows)
    {
        EDITBigDecimal totalSuspenseAmount = new EDITBigDecimal();
        int numberOfSuspenseItems = 0;

        if (suspenseFilterRows != null)
        {
            for (int i = 0; i < suspenseFilterRows.length; i++)
            {
                TableRow tableRow = new FilterSuspenseTableRow(suspenseFilterRows[i]);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);

                //page items
                totalSuspenseAmount = totalSuspenseAmount.addEditBigDecimal(suspenseFilterRows[i].getSuspenseAmount());
            }

            numberOfSuspenseItems = suspenseFilterRows.length;
            setNumberOfSuspenseItems(numberOfSuspenseItems);

            setTotalSuspenseAmount(totalSuspenseAmount);
            accumPremiumSuspense();
        }
    }


    private void accumPremiumSuspense()
    {
        EDITBigDecimal applyAmount = Suspense.sumActiveApply();
        EDITBigDecimal appliesProcessed = Suspense.sumAppliesProcessed();
        EDITBigDecimal applyRemovalAmount = Suspense.sumRemovals();

        premiumSuspense = applyAmount.subtractEditBigDecimal(appliesProcessed.addEditBigDecimal(applyRemovalAmount));
    }

    /**
     * Getter
     * @return
     */
    public int getNumberOfSuspenseItems()
    {
        return numberOfSuspenseItems;
    }

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getTotalSuspenseAmount()
    {
        return totalSuspenseAmount;
    }

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getPremiumSuspense()
    {
        return premiumSuspense;
    }


    /**
     * Setter
     * @param numberOfSuspenseItems
     */
    public void setNumberOfSuspenseItems(int numberOfSuspenseItems)
    {
        this.numberOfSuspenseItems = numberOfSuspenseItems;
    }

    /**
     * Setter
     * @param totalSuspenseAmount
     */
    public void setTotalSuspenseAmount(EDITBigDecimal totalSuspenseAmount)
    {
        this.totalSuspenseAmount = totalSuspenseAmount;
    }

    /**
     * Setter
     * @param totalSuspenseAmount
     */
    public void setPremiumSuspense(EDITBigDecimal premiumSuspense)
    {
        this.premiumSuspense = premiumSuspense;
    }
}
