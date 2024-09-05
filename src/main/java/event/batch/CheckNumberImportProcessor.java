/*
 * User: dlataille
 * Date: Feb 8, 2008
 * Time: unknown
 *
 * (c) 2000-2008Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */
package event.batch;

import batch.business.*;
import edit.common.*;
import edit.common.exceptions.*;
import edit.common.vo.*;
import edit.services.config.*;
import edit.services.logging.*;
import edit.services.*;
import edit.services.db.hibernate.SessionHelper;
import engine.*;
import engine.util.*;
import logging.*;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.math.*;
import java.util.*;

import fission.utility.*;
import security.Operator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import logging.LogEvent;
import org.apache.logging.log4j.Logger;
import event.EDITTrxHistory;
import event.FinancialHistory;
import event.CommissionHistory;
import contract.Segment;
import agent.PlacedAgent;

public class CheckNumberImportProcessor implements Serializable
{
    /**
     * Updated throughout the batch process for reporting purposes.
     */
    public CheckNumberImportProcessor()
    {
    }

    /**
     * Imports check numbers from the flat file specified by the company name and date parameters
     */
    public void runCheckNumberImport(EDITDate parameterDate, String companyName)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_CHECK_NUMBER_IMPORT).tagBatchStart(Batch.BATCH_JOB_RUN_CHECK_NUMBER_IMPORT, "Check Numbers");

        try
        {
            String[] importLines = readFile(parameterDate, companyName);

            if (importLines == null)
            {
                return;
            }

            CheckNumberImportVO[] checkNumberImportVOs =
                    transformRawInputIntoCheckNumberImportVOs(importLines);

            if (checkNumberImportVOs == null)
            {
                return;
            }

            processCheckNumberImportVOs(checkNumberImportVOs);
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_CHECK_NUMBER_IMPORT).updateFailure();

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.out.println(e);
            LogEvent logEvent = new LogEvent("Check Number Import Errored", e);
            Logger logger = Logging.getLogger(Logging.BATCH_JOB);
            logger.error(logEvent);
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_CHECK_NUMBER_IMPORT).tagBatchStop();
        }
    }

    private String[] readFile(EDITDate parameterDate, String companyName)
    {
        String importMonth = parameterDate.getFormattedMonth();

        String importDay = parameterDate.getFormattedDay();

        String importYear = parameterDate.getFormattedYear();

        UnitValueImport uvImport = ServicesConfig.getUnitValueImport();
        String uvImportFileDirectory = uvImport.getDirectory();
        String importFileName = uvImportFileDirectory + companyName + "CHECKS" + importYear + "-" + importMonth + "-" + importDay + ".txt";
        File importFile = new File(importFileName);

        String[] importLines = getImportLines(importFile);
        return importLines;
    }

    private CheckNumberImportVO[] transformRawInputIntoCheckNumberImportVOs(String[] importLines) throws EDITEngineException
    {
        CheckNumberImportVO[] checkNumberImportVOs = null;

        if (importLines != null)
        {
            checkNumberImportVOs = new CheckNumberImportVO[importLines.length];
        }

        for (int i = 0; i < importLines.length; i++)
        {
            CheckNumberImportVO checkNumberImportVO = new CheckNumberImportVO();
            checkNumberImportVO.setControlNumber(importLines[i].substring(1, 12));
            checkNumberImportVO.setCheckAmount(new EDITBigDecimal(importLines[i].substring(12, 27)).getBigDecimal());

            String processDay = importLines[i].substring(32, 34);
            String processMonth = importLines[i].substring(34, 36);
            String processYear = importLines[i].substring(36, 40);

            checkNumberImportVO.setProcessDate(processYear + EDITDate.DATE_DELIMITER + processMonth + EDITDate.DATE_DELIMITER + processDay);
            checkNumberImportVO.setReferenceId(importLines[i].substring(77, 87));

            checkNumberImportVOs[i] = checkNumberImportVO;
        }

        return checkNumberImportVOs;
    }


    private void processCheckNumberImportVOs(CheckNumberImportVO[] checkNumberImportVOs) throws Exception
    {
        for (int i = 0; i < checkNumberImportVOs.length; i++)
        {
            EDITBigDecimal checkAmount = new EDITBigDecimal(checkNumberImportVOs[i].getCheckAmount());
            EDITDate processDate = new EDITDate(checkNumberImportVOs[i].getProcessDate());
            String referenceId = checkNumberImportVOs[i].getReferenceId().trim();
            String controlNumber = checkNumberImportVOs[i].getControlNumber().trim();

            EDITTrxHistory editTrxHistory = findAppropriateHistoryEntry(checkAmount, processDate, referenceId);

            if (editTrxHistory != null)
            {
                SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
                editTrxHistory.setControlNumber(controlNumber);
                SessionHelper.saveOrUpdate(editTrxHistory, SessionHelper.EDITSOLUTIONS);
                SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

                EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_CHECK_NUMBER_IMPORT).updateSuccess();
            }
            else
            {
                EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_CHECK_NUMBER_IMPORT).updateFailure();

                LogEvent logEvent = new LogEvent("Check Number Import Errored - Check Not Found (" + controlNumber + ")");
                Logger logger = Logging.getLogger(Logging.BATCH_JOB);
                logger.error(logEvent);
            }
        }
    }

    private String[] getImportLines(File importFile)
    {
        try
        {
            List importLines = new ArrayList();

            FileReader fr = new FileReader(importFile);

            BufferedReader reader = new BufferedReader(fr);

            String importLine = null;

            while ((importLine = reader.readLine()) != null)
            {
                importLines.add(importLine);
            }

            reader.close();

            return (String[]) importLines.toArray(new String[importLines.size()]);
        }
        catch (Exception e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            System.out.println(e);

            return null;
        }
    }
    
    private EDITTrxHistory findAppropriateHistoryEntry(EDITBigDecimal checkAmount,
                                                       EDITDate processDate,
                                                       String referenceId)
    {
        EDITTrxHistory editTrxHistory = null;

        /* ReferenceId could be either a contract number or an agent number.
           Before we can process the import record, we need to determine which it is */
        Segment segment = Segment.findByContractNumber(referenceId);
        if (segment != null)
        {
            FinancialHistory[] financialHistories = FinancialHistory.findBySegment_Date_CheckAmount(segment.getSegmentPK(), processDate, checkAmount);
            for (int i = 0; i < financialHistories.length; i++)
            {
                editTrxHistory = financialHistories[i].getEDITTrxHistory();
                if (editTrxHistory.getControlNumber() == null)
                {
                    break;
                }
            }
        }
        else
        {
            PlacedAgent[] placedAgents = PlacedAgent.findByAgentNumber(referenceId);
            CommissionHistory[] commissionHistories = CommissionHistory.findByPlacedAgent_Date_CheckAmount(placedAgents, processDate, checkAmount);
            for (int i = 0; i < commissionHistories.length; i++)
            {
                editTrxHistory = commissionHistories[i].getEDITTrxHistory();
                if (editTrxHistory.getControlNumber() == null)
                {
                    break;
                }
            }
        }

        return editTrxHistory;
    }
}
