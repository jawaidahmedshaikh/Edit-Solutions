/*
 * User: sprasad
 * Date: Jun 8, 2006
 * Time: 12:15:19 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import fission.global.AppReqBlock;
import fission.utility.*;
import logging.Log;
import logging.LogColumn;
import logging.LogEntry;

import java.util.*;
import java.math.BigDecimal;

public class LogDetailTableModel extends TableModel
{
    private Log log;

    private String pageNumber;

    private String pageDirection;

    public static final String COLUMN_CREATION_DATE_TIME = "Creation Date Time";

    public static final String COLUMN_LOG_MESSAGE = " Log Message";

    public LogDetailTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        init(appReqBlock);

        setColumnNamesAndProperties();
    }

    /**
     * Default to "RIGHT" if no pageDirection can be found in the parameters.
     * @param reqParm
     * @return
     */
    private String getPageDirection(String pageDirection)
    {
        if (pageDirection == null)
        {
            pageDirection = "RIGHT";
        }

        return pageDirection;
    }

    /**
     * Takes the current pageNumber and the specified pageDirection.
     * If pageDirection == RIGHT, then the pageNumber is upped.
     * If pageDirection == LEFT, then the pageNumber is downed unless it
     * is currently 1, in which it is unchanged since 1 is a starting page.
     * @param reqParm
     * @return
     */
    private String getPageNumber(String pageNumberStr, String pageDirection)
    {
        int localPageNumber = 0;
        
        // Default to "1" if a page number has not yet been specified.
        if (pageNumberStr == null)
        {
            pageNumberStr = "1";
            
            localPageNumber = 1;
        }
        else
        {
            localPageNumber = Integer.parseInt(pageNumberStr);

            if (pageDirection.equals("RIGHT"))
            {
                localPageNumber++;
            }
            else
            {
                if (pageDirection.equals("LEFT"))
                {
                    if (localPageNumber > 1)
                    {
                        localPageNumber--;
                    }

                }
            }
        }
        
        return Integer.toString(localPageNumber);
    }

    private void init(AppReqBlock appReqBlock)
    {
        Long logPK = new Long(appReqBlock.getReqParm("logPK"));

        log = Log.findByPK_V2(logPK);

        pageDirection = getPageDirection(appReqBlock.getReqParm("pageDirection"));

        pageNumber = getPageNumber(appReqBlock.getReqParm("pageNumber"), pageDirection);

        // Store on the front-end
        appReqBlock.putInRequestScope("pageNumber", new Integer(pageNumber));
    }

    public void setColumnNamesAndProperties()
    {
        Set logColumns = log.getLogColumns();

        //  Sort log columns by sequence number
        LogColumn[] logColumnArray = (LogColumn[]) logColumns.toArray(new LogColumn[logColumns.size()]);

        logColumnArray = (LogColumn[]) Util.sortObjects(logColumnArray, new String[]{"getSequence"});

        populateColumnNames(logColumnArray);

        populateColumnWidths(logColumnArray);

        populateColumnRenderings(logColumnArray);
    }

    /**
     * The set of columns for the TableModel:
     */
    private void populateColumnNames(LogColumn[] logColumnArray)
    {
        super.getColumnNames().add(COLUMN_CREATION_DATE_TIME);

        super.getColumnNames().add(COLUMN_LOG_MESSAGE);

        //  The rest of the columns
        for (int i = 0; i < logColumnArray.length; i++)
        {
            super.getColumnNames().add(logColumnArray[i].getColumnLabel());
        }
    }

    /**
     * The requested widths of each cell in pixels.
     *
     * @see TableModel#hasCellWidthOverrides
     */
    private void populateColumnWidths(LogColumn[] logColumnArray)
    {
        super.getCellWidths().put(COLUMN_CREATION_DATE_TIME, new BigDecimal(12));

        super.getCellWidths().put(COLUMN_LOG_MESSAGE, new BigDecimal(12));

        BigDecimal cellWidth = new BigDecimal((100 - 24) / logColumnArray.length);        // evenly space the rest of the columns

        //  The rest of the columns
        for (int i = 0; i < logColumnArray.length; i++)
        {
            super.getCellWidths().put(logColumnArray[i].getColumnLabel(), cellWidth);
        }
    }

    /**
     * When in active mode, the columns should be rendered as a text field.
     */
    private void populateColumnRenderings(LogColumn[] logColumnArray)
    {
        super.getCellRenderings().put(COLUMN_CREATION_DATE_TIME, TableModel.RENDER_CELL_AS_TEXT);

        super.getCellRenderings().put(COLUMN_LOG_MESSAGE, TableModel.RENDER_CELL_AS_TEXT);

        //  The rest of the columns
        for (int i = 0; i < logColumnArray.length; i++)
        {
            super.getCellRenderings().put(logColumnArray[i].getColumnLabel(), TableModel.RENDER_CELL_AS_TEXT);
        }
    }

    public void buildTableRows()
    {
        LogEntry[] logEntries = LogEntry.findBy_LogFK_V1(log.getLogPK(), Integer.parseInt(pageNumber ) - 1, 50); // zero indexed - 50 at a time

        
        
        for (int i = 0; i < logEntries.length; i++)
        {
            TableRow logDetailTableRow = new LogDetailTableRow(logEntries[i]);

            super.getRows().add(logDetailTableRow);
        }
    }
}
