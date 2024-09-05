/**
 * User: dlataill
 * Date: May 3, 2006
 * Time: 11:16:10 AM
 * <p/>
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package edit.portal.widget;

import fission.global.AppReqBlock;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import edit.common.vo.*;

import java.util.Arrays;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map;

import event.*;

public class QuoteScheduledEventTableModel extends TableModel
{
    public static final String COLUMN_TRANSACTION = "Transaction";
    public static final String COLUMN_FREQUENCY = "Frequency";
    public static final String COLUMN_SEQUENCE = "Sequence";
    public static final String COLUMN_AMT_PCT = "Amount/Percent";
    public static final String COLUMN_START_DATE = "Start Date";
    public static final String COLUMN_STOP_DATE = "Stop Date";
    public static final String COLUMN_COVERAGE = "Coverage";

    private static final String[] COLUMN_NAMES = {COLUMN_TRANSACTION, COLUMN_FREQUENCY, COLUMN_SEQUENCE,
                                                  COLUMN_AMT_PCT, COLUMN_START_DATE, COLUMN_STOP_DATE,
                                                  COLUMN_COVERAGE};

    EDITTrxVO[] editTrxVOs = null;
    String coverage = "";

    public QuoteScheduledEventTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        editTrxVOs = (EDITTrxVO[]) appReqBlock.getHttpSession().getAttribute("pendingTransactions");
        coverage = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean").getValue("optionId");

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));

        buildTableRows();

        super.setRowsPopulated(true);
    }

    private TreeMap sortTransactionsByEffectiveDate(EDITTrxVO[] editTrxVOs)
    {
        TreeMap sortedTransactions = new TreeMap();

        for (int i = 0; i < editTrxVOs.length; i++)
        {
            sortedTransactions.put(editTrxVOs[i].getEffectiveDate() + editTrxVOs[i].getEDITTrxPK(), editTrxVOs[i]);
        }

        return sortedTransactions;
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        if (editTrxVOs != null)
        {
            Map sortedTransactions = sortTransactionsByEffectiveDate(editTrxVOs);

            Iterator it = sortedTransactions.values().iterator();

            while (it.hasNext())
            {
                EDITTrxVO editTrx = (EDITTrxVO) it.next();

                TableRow tableRow = null;
                ScheduledEventVO[] scheduledEventVOs = ((GroupSetupVO)editTrx.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class).getParentVO(GroupSetupVO.class)).getScheduledEventVO();
                if (scheduledEventVOs != null && scheduledEventVOs.length > 0)
                {
                    tableRow = new QuoteScheduledEventTableRow(editTrx, coverage);

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
