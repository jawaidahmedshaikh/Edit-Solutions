/*
 * User: gfrosti
 * Date: May 24, 2006
 * Time: 12:35:45 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package logging.business;

import edit.common.exceptions.EDITDeleteException;

import edit.common.exceptions.EDITSaveException;

import logging.Log;
import logging.LogColumn;

public interface Logging
{
    /**
     * Saves/Updates the specified Log.
     *
     * @param log
     */
    public void saveUpdateLog(Log log) throws EDITSaveException;

    /**
     * Saves/Updates the specified LogColumn associating it to the specified Log
     * if this is a new LogColumn.
     *
     * @param logColumn
     * @param
     *
     * @throws EDITSaveException
     */
    public void saveUpdateLogColumn(Log log, LogColumn logColumn) throws EDITSaveException;

    /**
     * Deletes the specified Log and its associated children.
     *
     * @param log
     *
     * @throws EDITDeleteException
     */
    public void deleteLog(Log log) throws EDITDeleteException;

    /**
     * Deletes the specified LogColumn and any associated
     *
     * @param logColumn
     *
     * @throws EDITDeleteException
     */
    public void deleteLogColumn(LogColumn logColumn) throws EDITDeleteException;
}
