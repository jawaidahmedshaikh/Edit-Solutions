/*
 * User: dlataille
 * Date: May 2, 2007
 * Time: 8:45:00 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import edit.common.EDITDate;
import fission.global.AppReqBlock;
import fission.utility.Util;

import java.util.*;

import group.ContractGroup;
import group.PayrollDeductionSchedule;
import group.PayrollDeduction;



public class CaseHistoryTableModel extends TableModel
{
    public static final String COLUMN_EFFECTIVE_DATE    = "Effective Date";
    public static final String COLUMN_TRANSACTION_TYPE  = "Transaction Type";
    public static final String COLUMN_GROUP_NAME        = "Group Name";

    private static final String[] COLUMN_NAMES = {COLUMN_EFFECTIVE_DATE, COLUMN_TRANSACTION_TYPE, COLUMN_GROUP_NAME};

    public CaseHistoryTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        String selectedPDPK = (String) getAppReqBlock().getHttpServletRequest().getAttribute("selectedPDPK");

        String activeCasePK = getAppReqBlock().getUserSession().getParameter("activeCasePK");

        String fromDate = Util.initString((String) getAppReqBlock().getHttpServletRequest().getAttribute("fromDate"), "");
        String toDate = Util.initString((String) getAppReqBlock().getHttpServletRequest().getAttribute("toDate"), "");

        if (activeCasePK != null && !fromDate.equals(""))
        {
            EDITDate edFromDate = new EDITDate(fromDate);
            EDITDate edToDate = new EDITDate(toDate);

            String selectedGroupNumber = getAppReqBlock().getReqParm("selectedGroupNumber");

            ContractGroup groupContractGroup = ContractGroup.findBy_ContractGroupNumber_ContractGroupTypeCT(selectedGroupNumber, ContractGroup.CONTRACTGROUPTYPECT_GROUP);

            Set<PayrollDeductionSchedule> pdsSet = groupContractGroup.getPayrollDeductionSchedules();

            Iterator it = pdsSet.iterator();

            Hashtable pdHashtable = new Hashtable();

            while (it.hasNext())
            {
                PayrollDeductionSchedule pds = (PayrollDeductionSchedule) it.next();

                Set<PayrollDeduction> payrollDeductions = pds.getPayrollDeductions();

                Iterator it2 = payrollDeductions.iterator();

                while (it2.hasNext())
                {
                    PayrollDeduction pd = (PayrollDeduction) it2.next();

                    if (pd.getPRDExtractDate().afterOREqual(edFromDate) &&
                        pd.getPRDExtractDate().beforeOREqual(edToDate))
                    {
                        if (!pdHashtable.containsKey(pd.getPRDExtractDate().getFormattedDate() + "_" + groupContractGroup.getContractGroupNumber()))
                        {
                            pdHashtable.put(pd.getPRDExtractDate().getFormattedDate() + "_" + groupContractGroup.getContractGroupNumber(), pd);
                        }
                    }
                }
            }

            Enumeration pdEnum = pdHashtable.keys();

            while (pdEnum.hasMoreElements())
            {
                PayrollDeduction pd = (PayrollDeduction) pdHashtable.get((String) pdEnum.nextElement());

                CaseHistoryTableRow row = new CaseHistoryTableRow(pd, groupContractGroup);

                if (row.getRowId().equals(selectedPDPK))
                {
                    row.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                addRow(row);
            }
        }
    }
}
