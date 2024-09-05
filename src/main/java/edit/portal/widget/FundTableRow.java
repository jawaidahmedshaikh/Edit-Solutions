/*
 * User: sprasad
 * Date: Jun 10, 2005
 * Time: 12:46:42 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;

import engine.FilteredFund;
import engine.Fund;



public class FundTableRow extends TableRow
{
    String filteredFundPK;

    public FundTableRow(String filteredFundPK, String allocationPct)
    {
        super();

        this.filteredFundPK = filteredFundPK;

        populateCellValues(filteredFundPK, allocationPct);
    }

    /**
     * Maps the values of Fund to the TableRow.
     */
    private void populateCellValues(String filteredFundPK, String allocationPct)
    {
        FilteredFund filteredFund = FilteredFund.findByPK(new Long(filteredFundPK));
        Fund fund = filteredFund.getFund();
        String fundName = fund.getName();

        getCellValues().put(FundTableModel.COLUMN_FUND, fundName);
        getCellValues().put(FundTableModel.COLUMN_PERCENT, allocationPct);
    }


    /**
     * Getter.
     * @return
     */
    public String getRowId()
    {
        return this.filteredFundPK;
    }
}
