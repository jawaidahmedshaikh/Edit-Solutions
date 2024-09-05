/*
 * User: gfrosti
 * Date: May 24, 2006
 * Time: 12:37:05 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package logging.component;

import edit.common.exceptions.EDITDeleteException;
import edit.common.exceptions.EDITSaveException;

import edit.services.component.AbstractComponent;

import edit.services.db.hibernate.SessionHelper;

import logging.Log;

import logging.LogColumn;

import logging.business.Logging;

public class LoggingComponent extends AbstractComponent implements Logging
{
    public LoggingComponent()
    {
    }


    /**
     * @param log
     *
     * @throws EDITSaveException
     * @see Logging#saveUpdateLog(Log);
     */
    public void saveUpdateLog(Log log) throws EDITSaveException
    {
        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            // manually called until callback framework is finalized.
            log.onSave();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (EDITSaveException e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     * @param logColumn
     *
     * @throws EDITSaveException
     * @see Logging#saveUpdateLogColumn(Log, LogColumn)
     */
    public void saveUpdateLogColumn(Log log, LogColumn logColumn) throws EDITSaveException
    {
        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            // Commented the following code because adding child to parent is a performance hit in hibernate
            // because hibernate loads all children before adding new child.
//            log.add(logColumn);
            logColumn.setLog(log);

            // manually called until callback framework is finalized.
            logColumn.onSave();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (EDITSaveException e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            throw new RuntimeException(e);
        }
        finally
        {
            SessionHelper.clearSessions();
        }
    }

    /**
     * @param log
     *
     * @throws EDITDeleteException
     * @see Logging#deleteLog(log)
     */
    public void deleteLog(Log log) throws EDITDeleteException
    {
        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            log.hDelete();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (EDITDeleteException e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
    }

    /**
     * @param logColumn
     *
     * @throws EDITDeleteException
     * @see Logging#deleteLog(log)
     */
    public void deleteLogColumn(LogColumn logColumn) throws EDITDeleteException
    {
        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            logColumn.hDelete();

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (EDITDeleteException e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
    }
}
