/*
 * User: sdorman
 * Date: Jul 29, 2008
 * Time: 8:55:58 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package engine.sp;

import edit.common.*;

import logging.*;

/**
 * Logs the provided information to the specified database log.
 *
 * The information comes from the script via working storage:
 *
 * LogName      - name of the database log where information will be placed (required)
 * LogMessage   - message to be stored in the log (required)
 *
 * The column names and their values are also put in working storage.  The column names must match the exact names
 * defined in the log during setup.  If a column name is defined in the log but not provided via working storage, its 
 * value is set to null.  If a column name is in working storage that was never defined for that log, that column
 * is not recorded and no error is thrown.
 *
 * The Log must be set up in Logging->Setup (from main page) before using this instruction.
 */
public class Loginfo extends Inst
{
    private static final String LOG_NAME = "LogName";
    private static final String LOG_MESSAGE = "LogMessage";


    public void compile(ScriptProcessor aScriptProcessor) throws SPException
    {
        sp = aScriptProcessor;  // Save instance of ScriptProcessor

        // Note: No compiling is required for this instruction
    }

    /**
     * Gets the LogName from working storage and looks up the LogColumns associated with the Log.  Then checks working
     * storage for each LogColumn.ColumnName, putting the name and its value in the log map.  After all of the columns
     * are retrieved and set, logs the whole set of info to the database Log.
     *
     * @param execSP            ScriptProcessor instance
     *
     * @throws SPException
     */
    public void exec(ScriptProcessor execSP) throws SPException
    {
        sp = execSP;

        EDITMap columnInfo = new EDITMap();

        String logName = sp.getWSEntry(LOG_NAME);
        String logMessage = sp.getWSEntry(LOG_MESSAGE);

        //  Find the LogColumns for the given Log
        LogColumn[] logColumns = logging.LogColumn.findBy_LogName(logName);

        //  For each LogColumn defined in the Log, get its corresponding value from working storage and put into
        //  the map
        for (int i = 0; i < logColumns.length; i++)
        {
            LogColumn logColumn = logColumns[i];

            String columnName = logColumn.getColumnName();

            String wsColumnValue = sp.getWSEntry(columnName);

            columnInfo.put(columnName, wsColumnValue);
        }

        //  Log to database
        logging.Log.logToDatabase(logName, logMessage, columnInfo);

        // Increment instruction pointer
        sp.incrementInstPtr();
    }
}