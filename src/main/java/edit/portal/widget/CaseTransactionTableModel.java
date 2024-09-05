/*
 * User: sdorman
 * Date: 1/31/2006
 * Time: 9:7:49 
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;

import engine.ProductStructure;

import event.EDITTrx;
import event.GroupSetup;

import fission.global.AppReqBlock;

import java.util.Arrays;

import group.*;


public class CaseTransactionTableModel extends TableModel
{
    public static final String COLUMN_COMPANY_NAME = "Product";
    public static final String COLUMN_CASE_NUMBER = "Case Number";
    public static final String COLUMN_EFFECTIVE_DATE = "Effective Date";
    public static final String COLUMN_SEQUENCE = "Seq";
    public static final String COLUMN_STATUS = "Status";
    public static final String COLUMN_TRANSACTION = "Transaction";
    public static final String COLUMN_AMOUNT = "Amount";

    private static final String[] COLUMN_NAMES = {COLUMN_COMPANY_NAME, COLUMN_CASE_NUMBER, COLUMN_EFFECTIVE_DATE,
                                                  COLUMN_SEQUENCE, COLUMN_STATUS, COLUMN_TRANSACTION, COLUMN_AMOUNT};

//    private Long casePK;
//    private String caseNumber;

    public CaseTransactionTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        ContractGroup caseDetail = (ContractGroup) getAppReqBlock().getHttpServletRequest().getAttribute("caseDetail");

        String caseNumber = caseDetail.getContractGroupNumber();

//        GroupSetup[] groupSetups = GroupSetup.findBy_GroupKey(caseNumber);
        GroupSetup[] groupSetups = GroupSetup.findBy_GroupKey("RK0217aa");     // dummy for testing

        if (groupSetups != null)
        {
            for (int i = 0; i < groupSetups.length; i++)
            {
//                Case theCase = Case.findByPK(new Long((String) getAppReqBlock().getHttpServletRequest().getAttribute("casePK")));
                ContractGroup theCase = ContractGroup.findBy_ContractGroupNumber(caseNumber);

                ProductStructure productStructure = theCase.getFilteredProducts().iterator().next().getProductStructure();

                EDITTrx[] editTrxs = EDITTrx.findPendingBy_GroupSetupPK(groupSetups[i].getGroupSetupPK());

                if (editTrxs != null)
                {
                    for (int j = 0; j < editTrxs.length; j++)
                     {
                        TableRow tableRow = new CaseTransactionTableRow(productStructure, theCase, editTrxs[j]);

                        if (tableRow.getRowId().equals(this.getSelectedRowId()))
                        {
                            tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                        }

                        super.addRow(tableRow);
                    }
                }
            }
        }
    }
}
