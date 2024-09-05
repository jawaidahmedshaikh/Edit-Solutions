/*
 * User: sprasad
 * Date: Jun 10, 2005
 * Time: 12:46:30 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import fission.global.AppReqBlock;
import fission.utility.Util;

import java.util.*;



public class FundTableModel extends TableModel
{
    public static final String COLUMN_FUND = "Fund";
    public static final String COLUMN_PERCENT = "Percent";

    private static final String[] COLUMN_NAMES = {COLUMN_FUND, COLUMN_PERCENT};


    public FundTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }


    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        Map fundMap = (Map) this.getAppReqBlock().getHttpSession().getAttribute("casetracking.supplementalContract.fundMap");

        if (fundMap != null)
        {
            Iterator iterator = fundMap.keySet().iterator();

            while (iterator.hasNext())
            {
                String filteredFundPK   = (String) iterator.next();
                String allocationPct    = (String) fundMap.get(filteredFundPK);

                TableRow fundTableRow = new FundTableRow(filteredFundPK, allocationPct);

                if (fundTableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    fundTableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(fundTableRow);
            }
        }
    }

    /**
     * Updates the fund table with new values.
     */
    public void updateFundTable()
    {
        AppReqBlock appReqBlock = this.getAppReqBlock();

        String filteredFundPK = Util.initString(appReqBlock.getReqParm("filteredFundPK"),"0");
        String allocationPct = Util.initString(appReqBlock.getReqParm("allocationPercent"), "0");
        String activeFilteredFundPK = appReqBlock.getReqParm("activeFilteredFundPK");

        Map fundMap = (Map) appReqBlock.getHttpSession().getAttribute("casetracking.supplementalContract.fundMap");

        if (fundMap == null)
        {
            fundMap = new TreeMap();
        }

        // when trying to change the fund for existing entry
        if (!activeFilteredFundPK.equals("0") && !activeFilteredFundPK.equals(filteredFundPK))
        {
            fundMap.remove(activeFilteredFundPK);
        }

        fundMap.put(filteredFundPK, allocationPct);

        appReqBlock.getHttpSession().setAttribute("casetracking.supplementalContract.fundMap", fundMap);
    }

    /**
     * Displays Row details
     */
    public void showFundDetails()
    {
        AppReqBlock appReqBlock = this.getAppReqBlock();

        Map fundMap = (Map) appReqBlock.getHttpSession().getAttribute("casetracking.supplementalContract.fundMap");

        String filteredFundPK = getSelectedRowId();

        String allocationPct = (String) fundMap.get(filteredFundPK);

        appReqBlock.getHttpServletRequest().setAttribute("activeFilteredFundPK", filteredFundPK);
        appReqBlock.getHttpServletRequest().setAttribute("activeFundAllocationPct", allocationPct);
    }

    /**
     * Clears the session attribute
     */
    public void clearFundMap()
    {
        this.getAppReqBlock().getHttpSession().removeAttribute("casetracking.supplementalContract.fundMap");
    }
}
