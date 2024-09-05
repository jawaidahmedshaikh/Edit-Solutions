/*
 * User: dlataill
 * Date: Jul 19, 2005
 * Time: 8:06:47 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.common.EDITBigDecimal;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import edit.portal.common.session.*;
import event.CashBatchContract;
import fission.global.AppReqBlock;

import fission.utility.Util;
import fission.utility.*;

import java.util.*;

import security.Operator;
import engine.ProductStructure;
import engine.Company;

public class CashBatchSummaryTableModel extends TableModel
{
    public static final String COLUMN_BATCH_ID = "Batch ID";
    public static final String COLUMN_GROUP_NUMBER = "Group Number";
    public static final String COLUMN_DUE_DATE = "Due Date";
    public static final String COLUMN_CREATION_DATE = "Creation Date";
    public static final String COLUMN_AMOUNT = "Amount";
    public static final String COLUMN_NUMBER_OF_ITEMS = "# of Items";
    public static final String COLUMN_OPERATOR = "Creation Operator";

    private static final String[] COLUMN_NAMES = {COLUMN_BATCH_ID, COLUMN_GROUP_NUMBER, COLUMN_DUE_DATE,
                                                  COLUMN_CREATION_DATE, COLUMN_AMOUNT, COLUMN_NUMBER_OF_ITEMS, COLUMN_OPERATOR};

    public CashBatchSummaryTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

     public CashBatchSummaryTableModel(AppReqBlock appReqBlock, String selectedCashBatchSummaryPK)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

        super.setSelectedRowId(selectedCashBatchSummaryPK);
    }

    /**
     * Builds the superset TableModel
     */
    public void buildTableRows()
    {
        boolean viewAllSuspense = false;

        EDITBigDecimal batchtotals = new EDITBigDecimal();
        int totalNumberOfItems = 0;

        AppReqBlock appReqBlock = getAppReqBlock();

        ProductStructure[] productStructures = appReqBlock.getUserSession().getProductStucturesForUser();

        if (productStructures != null && productStructures.length > 0)
        {
            Long securityProductStructurePK = ProductStructure.checkForSecurityStructure(productStructures);
            List companiesAllowed = ProductStructure.checkForAuthorizedCompanies(productStructures);

            UserSession userSession = appReqBlock.getUserSession();
            if (securityProductStructurePK > 0L)
            {
                Operator operator = Operator.findByOperatorName(userSession.getUsername());

                if (userSession.userLoggedIn())
                {
                    viewAllSuspense = operator.checkViewAllAuthorization(securityProductStructurePK, "Suspense");
                }
                else
                {
                    viewAllSuspense = true;
                }
            }

            String status = Util.initString(appReqBlock.getReqParm("status"), "");
            if (status.equalsIgnoreCase("Please Select"))
            {
                status = "";
            }

            String month = Util.initString(appReqBlock.getReqParm("month"), "");
            String day = Util.initString(appReqBlock.getReqParm("day"), "");
            String year = Util.initString(appReqBlock.getReqParm("year"), "");

            String filterDate = DateTimeUtil.initDate(month, day, year, "");

            String amount = Util.initString(appReqBlock.getReqParm("filterAmount"), "");
            String operator = Util.initString(appReqBlock.getReqParm("filterOperator"), "");

            CashBatchContract[] cashBatchContracts = null;

            if ((status.equals("") && filterDate.equals("") && amount.equals("") && operator.equals("")) ||
                status.equalsIgnoreCase("All"))
            {
                cashBatchContracts = CashBatchContract.findAll();
            }
            else
            {
                cashBatchContracts = CashBatchContract.findByParameters(status, filterDate, amount, operator, false);
            }

            cashBatchContracts = (CashBatchContract[]) Util.sortObjects(cashBatchContracts, new String[] {"getCreationDate"});

            totalNumberOfItems = cashBatchContracts.length;

            for (int i = cashBatchContracts.length - 1; i >= 0; i--)
            {
                CashBatchContract cashBatchContract = cashBatchContracts[i];

                String companyName = "";

                if (cashBatchContract.getCompanyFK() != null)
                {
                    Company company = Company.findByPK(cashBatchContract.getCompanyFK());
                    companyName = company.getCompanyName();
                }

                //  Commented out next line because we don't have companies during the first phase of the cashBatch import
//                if (companiesAllowed.contains(companyName) || viewAllSuspense)
//                {
                    batchtotals = batchtotals.addEditBigDecimal(cashBatchContract.getAmount());

                    TableRow tableRow = new CashBatchSummaryTableRow(cashBatchContract);

                    if (tableRow.getRowId().equals(this.getSelectedRowId()))
                    {
                        tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                    }

                    super.addRow(tableRow);
//                }
            }
        }

        String[] batchTotals= new String[] {batchtotals.toString(), totalNumberOfItems + ""};
        appReqBlock.getHttpServletRequest().setAttribute("batchTotals", batchTotals);
    }
}
