/*
 * User: dlataille
 * Date: Sept 19, 2007
 * Time: 11:43:48 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import contract.Segment;
import edit.portal.widgettoolkit.TableRow;
import edit.portal.widgettoolkit.TableModel;

import fission.global.AppReqBlock;

import java.util.*;



public class RiderDetailTableModel extends TableModel
{

    private Segment[] riders;


    public RiderDetailTableModel(AppReqBlock appReqBlock, String[] columnNames, Segment[] riders)
    {
        super(appReqBlock);

        this.riders = riders;

        getColumnNames().addAll(Arrays.asList(columnNames));
    }

    /**
     * Setter.
     * @param contractClients
     */
    public void buildTableRows()
    {
        for (int i = 0; i < riders.length; i++)
        {
            Segment rider = riders[i];

            TableRow tableRow = new RiderDetailTableRow(rider);

            super.addRow(tableRow);
        }
    }
}
