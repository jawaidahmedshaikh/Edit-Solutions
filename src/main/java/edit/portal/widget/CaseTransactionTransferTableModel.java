/*
 * User: sdorman
 * Date: 1/26/2006
 * Time: 10:39:58
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.services.db.hibernate.*;
import fission.global.*;

import java.util.*;

import engine.*;
import event.*;
import contract.*;



public class CaseTransactionTransferTableModel extends TableModel
{
    public static final String COLUMN_FUND_NAME = "Fund";
    public static final String COLUMN_FROM_TO = "From/To";
    public static final String COLUMN_PERCENT = "Percent";
    public static final String COLUMN_DOLLARS = "Dollars";
    public static final String COLUMN_UNITS = "Units";

    private static final String[] COLUMN_NAMES = {COLUMN_FUND_NAME, COLUMN_FROM_TO, COLUMN_PERCENT, COLUMN_DOLLARS, COLUMN_UNITS};

    public CaseTransactionTransferTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        String selectedEDITTrxPK = new CaseTransactionTableModel(getAppReqBlock()).getSelectedRowId();

        if (selectedEDITTrxPK != null)
        {
            InvestmentAllocationOverride[] investmentAllocationOverrides = InvestmentAllocationOverride.findBy_EDITTrxFK(new Long(selectedEDITTrxPK));

            for (int i = 0; i < investmentAllocationOverrides.length; i++)
            {
                long investmentAllocationFK = investmentAllocationOverrides[i].getInvestmentAllocationFK();

                InvestmentAllocation investmentAllocation = InvestmentAllocation.findByPK(new Long(investmentAllocationFK));


                Fund fund = findFund(investmentAllocation);

                TableRow tableRow = new CaseTransactionTransferTableRow(fund, investmentAllocation, investmentAllocationOverrides[i]);

                    if (tableRow.getRowId().equals(this.getSelectedRowId()))
                    {
                        tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                    }

                    super.addRow(tableRow);
            }
        }
    }

    /**
     * Finds the Fund for a given InvestmentAllocation.  Does this step by step since a query would have to join tables
     * across different databases which is not possible with Hibernate.
     *
     * @param investmentAllocation              the InvestmentAllocation for which the Fund should be found
     *
     * @return  fund for given InvestmentAllocation
     */
    private Fund findFund(InvestmentAllocation investmentAllocation)
    {
        Long investmentFK = investmentAllocation.getInvestmentFK();
        Investment investment = Investment.findByPK(investmentFK);

        Long filteredFundFK = investment.getFilteredFundFK();

        FilteredFund filteredFund = FilteredFund.findByPK(filteredFundFK);

        Long fundFK = filteredFund.getFundFK();

        Fund fund = (Fund) SessionHelper.get(Fund.class,  fundFK, SessionHelper.ENGINE);

        return fund;
    }
}
