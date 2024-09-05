/*
 * User: dlataille
 * Date: Feb 18, 2008
 * Time: unknown
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
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
import accounting.AccountingDetail;

public class ManualAccountingImportProcessor implements Serializable
{
    /* importCompanies and importCoTranslation must be kept in sync (to match the imports 2 digit company code to the
       EDITSolutions company name) */
    private static String[] importCompanies = {"08"};

    private static String[] importCoTranslation = {"FLA"};

    private static String DEBIT = "1";
    private static String CREDIT = "2";

    public static String DATABASE = "EDITSOLUTIONS";

    /**
     * Updated throughout the batch process for reporting purposes.
     */
    public ManualAccountingImportProcessor()
    {
    }

    /**
     * Imports manual accounting entries from the flat file specified by the company name and date parameters
     */
    public void runManualAccountingImport(EDITDate parameterDate)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_MANUAL_ACCOUNTING_IMPORT).tagBatchStart(Batch.BATCH_JOB_RUN_CHECK_NUMBER_IMPORT, "Manual Accounting Import");

        try
        {
            String[] importLines = readFile(parameterDate);

            if (importLines == null)
            {
                return;
            }

            for (int i = 0; i < importLines.length; i++)
            {
                processManualAccountingImportLines(importLines[i]);

                EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_MANUAL_ACCOUNTING_IMPORT).updateSuccess();
            }
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_MANUAL_ACCOUNTING_IMPORT).updateFailure();

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.out.println(e);
            LogEvent logEvent = new LogEvent("Manual Accounting Import Errored", e);
            Logger logger = Logging.getLogger(Logging.BATCH_JOB);
            logger.error(logEvent);
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_MANUAL_ACCOUNTING_IMPORT).tagBatchStop();
        }
    }

    private String[] readFile(EDITDate parameterDate)
    {
        String importMonth = parameterDate.getFormattedMonth();

        String importDay = parameterDate.getFormattedDay();

        String importYear = parameterDate.getFormattedYear();

        UnitValueImport uvImport = ServicesConfig.getUnitValueImport();
        String uvImportFileDirectory = uvImport.getDirectory();
        String importFileName = uvImportFileDirectory + "VENUSMiscAccounting" + importMonth + importDay + importYear + ".txt";
        File importFile = new File(importFileName);

        String[] importLines = getImportLines(importFile);
        return importLines;
    }

    private void processManualAccountingImportLines(String importLine) throws Exception
    {
        String planCode = importLine.substring(88, 94).trim();

        AccountingDetail accountingDetail = new AccountingDetail();
        accountingDetail.setSource(importLine.substring(0, 1));
        for (int j = 0; j < importCompanies.length; j++)
        {
            if (importLine.substring(1, 3).equalsIgnoreCase(importCompanies[j]))
            {
                accountingDetail.setCompanyName(importCoTranslation[j]);
                break;
            }
        }

        accountingDetail.setAccountNumber(importLine.substring(3, 10));
        if (planCode != "")
        {
            accountingDetail.setContractNumber(importLine.substring(10, 20));
        }
        accountingDetail.setTransactionCode(importLine.substring(20, 26));
        accountingDetail.setAccountName(importLine.substring(26, 51));

        String leftDecimal = importLine.substring(51, 60);
        String rightDecimal = importLine.substring(60, 62);
        accountingDetail.setAmount(new EDITBigDecimal(leftDecimal.trim() + "." + rightDecimal.trim()));

        if (importLine.substring(62, 63).equalsIgnoreCase(DEBIT))
        {
            accountingDetail.setDebitCreditInd("D");
        }
        else
        {
            accountingDetail.setDebitCreditInd("C");
        }

        accountingDetail.setAgentCode(importLine.substring(63, 73));
        accountingDetail.setStateCodeCT(importLine.substring(75, 77));

        String entryMonth = importLine.substring(77, 79);
        String entryDay = importLine.substring(79, 81);
        String entryYear = importLine.substring(81, 85);
        EDITDate entryDate = new EDITDate(entryYear, entryMonth, entryDay);
        accountingDetail.setProcessDate(entryDate);
        accountingDetail.setEffectiveDate(entryDate);

        accountingDetail.setDescription(importLine.substring(85, 88));
        accountingDetail.setVoucherSource(planCode);

        SessionHelper.beginTransaction(DATABASE);
        SessionHelper.saveOrUpdate(accountingDetail, DATABASE);
        SessionHelper.commitTransaction(DATABASE);
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
}
