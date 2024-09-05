/*
 * User: dlataille
 * Date: Aug 15, 2007
 * Time: 10:58:00 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableModel;
import edit.common.EDITDate;
import fission.global.AppReqBlock;
import fission.utility.Util;

import java.util.*;

import group.ContractGroup;
import group.PayrollDeductionSchedule;
import group.PayrollDeduction;

public class PRDReportTableModel extends TableModel
{
    public static final String COLUMN_CONTRACT_NUMBER   = "Contract Number";
    public static final String COLUMN_EXTRACT_DATE      = "Extract Date";
    public static final String COLUMN_DEDUCTION_AMOUNT  = "Deduction Amount";

    private static final String[] COLUMN_NAMES = {COLUMN_CONTRACT_NUMBER, COLUMN_EXTRACT_DATE, COLUMN_DEDUCTION_AMOUNT};

    public PRDReportTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        String selectedPDPK = getAppReqBlock().getFormBean().getValue("selectedPDPK");

        if (selectedPDPK != null && !selectedPDPK.equals("") && !selectedPDPK.equals("null"))
        {
            EDITDate extractDate = PayrollDeduction.findByPK(new Long(selectedPDPK)).getPRDExtractDate();

            String groupNumber = Util.initString(getAppReqBlock().getFormBean().getValue("selectedGroupNumber"), "");

            ContractGroup groupContractGroup = ContractGroup.findBy_ContractGroupNumber_ContractGroupTypeCT(groupNumber, ContractGroup.CONTRACTGROUPTYPECT_GROUP);

            Set<PayrollDeductionSchedule> pdsSet = groupContractGroup.getPayrollDeductionSchedules();

            Iterator it = pdsSet.iterator();

            while (it.hasNext())
            {
                PayrollDeductionSchedule pds = (PayrollDeductionSchedule) it.next();

                Set<PayrollDeduction> payrollDeductions = pds.getPayrollDeductions();

                Iterator it2 = payrollDeductions.iterator();

                while (it2.hasNext())
                {
                    PayrollDeduction pd = (PayrollDeduction) it2.next();

                    if (pd.getPRDExtractDate().equals(extractDate))
                    {
                        PRDReportTableRow row = new PRDReportTableRow(pd);

                        addRow(row);
                    }
                }
            }
        }
    }
}
