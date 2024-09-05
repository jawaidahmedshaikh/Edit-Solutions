/*
 * User: sprasad
 * Date: Feb 4, 2005
 * Time: 10:48:21 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.batch;

import batch.business.*;

import edit.common.*;

import edit.common.exceptions.*;

import edit.common.vo.*;

import edit.services.*;

import edit.services.config.*;

import edit.services.logging.*;

import engine.*;

import engine.util.*;

import fission.utility.*;

import logging.*;

import org.apache.logging.log4j.Logger;

import java.io.*;

import java.util.*;



public class CashClearanceImportProcessor
{
    private static final String PREMIUM_FEE_TYPE = "Premium";
    private static final String TRANSFER_FEE_TYPE = "Transfer";
    private static final String SURRENDER_FEE_TYPE = "Surrender";
    private static final String COI_FEE_TYPE = "COI";
    private static final String ADMIN_FEE_FEE_TYPE = "AdminFee";
    private static final String LOAN_FEE_TYPE = "Loan";
    private static final String LOAN_INT_FEE_TYPE = "LoanInt";
    private static final String DEATH_FEE_TYPE = "Death";
    private static final String ADVANCE_TRANSFER_FEE_TYPE = "AdvanceTransfer";
    private static final String MANAGEMENT_FEE_FEE_TYPE = "ManagementFee";
    private static final String M_AND_E_FEE_TYPE = "MEFee";
    private static final String GAIN_LOSS_FEE_TYPE = "GainLoss";
    private static final String GAIN_LOSS_FOR_GAIN_LOSS = "GainLossForGainLoss";
    private static final String MANUAL_ENTRY = "Manual";

    private TransformChargeCodes transformChargeCodes = new TransformChargeCodes();

    // does lazy-read of the data
    private engine.business.Lookup engineLookup = new engine.component.LookupComponent();
    private engine.business.Lookup calcLookup = new engine.component.LookupComponent();

    private String exportFile = null;
    private String manualCashEntriesFile = "ManualCashClearance";
    private StringBuffer fileData;

    public void importCashClearanceValues(String importDate)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_IMPORT_CASH_CLEARANCE_VALUES).tagBatchStart(Batch.BATCH_JOB_IMPORT_CASH_CLEARANCE_VALUES, "Cash Clearance Value");

        try
        {
            EDITDate editImportDate = new EDITDate(importDate);

            String month = editImportDate.getFormattedMonth();

            String day = editImportDate.getFormattedDay();

            String year = editImportDate.getFormattedYear();

            CashClearanceImport cashClearanceImport = ServicesConfig.getCashClearanceImport();

            String importDirectory = cashClearanceImport.getDirectory();

            String fileName = importDirectory + "CC" + year + month + day + ".dat";

            String jobParameterDate = DateTimeUtil.initDate(month, day, year, null);

            File importFile = new File(fileName);

            String[] importLines = getCashClearanceImportLines(importFile);

            if (importLines != null)
            {
                String importLine = null;
                String recordType = null;
                String fundNumberFromFile = null;
                String feeAmount = null;
                EDITBigDecimal edFeeAmount = new EDITBigDecimal();
                String feeTypeCT = null;
                String toFromInd = null;
                boolean gainLossForGainLoss = false;

                //Get date from header line (1st line in file)
                importLine = importLines[0];
                String headerDate = importLine.substring(9, 17);
                String feeTrxEffectiveDate = DateTimeUtil.initDate(headerDate.substring(0, 2), headerDate.substring(2, 4), headerDate.substring(4), null);

                // First line is HEADER record and last line is TRAILER record.
                // ignore HEADER record and TRAILER record.
                for (int i = 1; i < (importLines.length - 1); i++)
                {
                    importLine = importLines[i];

                    recordType = importLine.substring(12, 16);

                    // ignore records of type 'CASH' this is the starting record for
                    // each filtered fund transactions
                    if (!recordType.equalsIgnoreCase("CASH"))
                    {
                        fundNumberFromFile = importLine.substring(0, 4);

                        feeAmount = importLine.substring(16, 31).trim();
                        edFeeAmount = new EDITBigDecimal(feeAmount);
                        if (edFeeAmount.isGT("0"))
                        {
                            toFromInd = "T";
                        }
                        else
                        {
                            toFromInd = "F";
                            // make feeamount always positive.
                            edFeeAmount = edFeeAmount.multiplyEditBigDecimal("-1");
                        }

                        gainLossForGainLoss = false;

                        feeTypeCT = getFeeTypeCT(recordType);

                        if (feeTypeCT.equalsIgnoreCase(GAIN_LOSS_FOR_GAIN_LOSS))
                        {
                            gainLossForGainLoss = true;
                            feeTypeCT = GAIN_LOSS_FEE_TYPE;
                        }

                        // This will figure out the right FilteredFundVO in case
                        // the fund number is a "Client" fund number.
                        // The ChargeCodeVO is filled in if it was a client fund.
                        Object[] objs = getFilteredFundVOAndChargeCodeVO(fundNumberFromFile);
                        FilteredFundVO filteredFundVO = (FilteredFundVO) objs[0];
                        ChargeCodeVO chargeCodeVO = (ChargeCodeVO) objs[1];

                        // has to have this filtered fund already existing in the system
                        // if it does not find one do nothing i.e do not add record.
                        if (filteredFundVO != null)
                        {
                            if (feeTypeCT != MANUAL_ENTRY)
                            {
                                long filteredFundPK = filteredFundVO.getFilteredFundPK();

                                FeeDescriptionVO feeDescriptionVO = engineLookup.findFeeDescriptionBy_FilteredFundPK_And_FeeTypeCT(filteredFundPK, feeTypeCT);

                                long feeDescriptionPK = 0;

                                if (feeDescriptionVO != null)
                                {
                                    feeDescriptionPK = feeDescriptionVO.getFeeDescriptionPK();
                                }

                                FeeVO feeVO = new FeeVO();
                                feeVO.setFeePK(0);
                                feeVO.setFilteredFundFK(filteredFundPK);
                                feeVO.setFeeDescriptionFK(feeDescriptionPK);
                                feeVO.setEffectiveDate(feeTrxEffectiveDate);
                                feeVO.setProcessDateTime(feeTrxEffectiveDate);
                                feeVO.setOriginalProcessDate(null);

                                // all fees coming from the import files are automated
                                // that means all the fees should be released by default.
                                feeVO.setReleaseInd("Y");
                                feeVO.setReleaseDate(jobParameterDate);
                                feeVO.setStatusCT("N");

                                // when the fee is released AccountPendingStatus is 'Y'
                                // for more information see comments in Fee.save() method
                                feeVO.setAccountingPendingStatus("Y");
                                feeVO.setTrxAmount(edFeeAmount.getBigDecimal());
                                feeVO.setTransactionTypeCT("DFCASH");
                                feeVO.setContractNumber(null);
                                feeVO.setToFromInd(toFromInd);
                                feeVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
                                feeVO.setOperator("System");

                                if (chargeCodeVO != null)
                                {
                                    // they used a client fund number so we were
                                    // able to determine the charge code
                                    feeVO.setChargeCodeFK(chargeCodeVO.getChargeCodePK());
                                }

                                Fee fee = new Fee(feeVO);

                                try
                                {
                                    fee.save();

                                    if (gainLossForGainLoss)
                                    {
                                        EDITBigDecimal trxAmount = new EDITBigDecimal(feeVO.getTrxAmount()).multiplyEditBigDecimal("-1");

                                        feeVO.setFeePK(0);
                                        feeVO.setTransactionTypeCT("DFACC");
                                        feeVO.setTrxAmount(trxAmount.getBigDecimal());
                                        Fee fee2 = new Fee(feeVO);
                                        fee2.save();
                                    }

                                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_IMPORT_CASH_CLEARANCE_VALUES).updateSuccess();
                                }
                                catch (EDITEngineException e)
                                {
                                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_IMPORT_CASH_CLEARANCE_VALUES).updateFailure();

                                    System.out.println(e);

                                    e.printStackTrace();
                                }
                            }
                            else
                            {
                                if (exportFile == null)
                                {
                                    exportFile = getExportFile();
                                }

                                fileData = new StringBuffer();
                                fileData.append(fundNumberFromFile);
                                fileData.append(",");
                                fileData.append(feeTrxEffectiveDate);
                                fileData.append(",");
                                fileData.append("DFCASH");  //Transaction Type
                                fileData.append(",");
                                fileData.append(feeTypeCT);
                                fileData.append(",");
                                fileData.append(toFromInd); //To/From Indicator
                                fileData.append(",");
                                fileData.append(new EDITBigDecimal(feeAmount).toString());
                                fileData.append(",");
                                fileData.append(chargeCodeVO.getChargeCode());
                                fileData.append(",");
                                fileData.append("Y"); //Accounting Pending Indicator

                                fileData.append("\n");

                                appendToFile(fileData.toString());
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_IMPORT_CASH_CLEARANCE_VALUES).updateFailure();

          System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            LogEvent logEvent = new LogEvent("Cash Clearance Import Errored", e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_IMPORT_CASH_CLEARANCE_VALUES).tagBatchStop();
        }
    }

    /**
     * Using the fund number passed in from the file, do a lookup of
     * the FilteredFundVO and the ChargeCodeVO.  If the fund number
     * passed in is a Client fund number, then both VOs will be found.
     * If however it equals a SEG number then the ChargeCodeVO passed
     * back will be null.
     * @param fundNumberFromFile
     * @return array of FilteredFundVO and ChargeCodeVO
     */
    private Object[] getFilteredFundVOAndChargeCodeVO(String fundNumberFromFile)
    {
        // Should be only one VO if there but db does
        // not enforce setting unique client fund number.
        ChargeCodeVO[] chargeCodeVOs = transformChargeCodes.getChargeCodeVOsForClientFundNumber(fundNumberFromFile);

        // we will return an array of these two
        FilteredFundVO filteredFundVO = null;
        ChargeCodeVO chargeCodeVO = null;

        if (chargeCodeVOs == null)
        {
            // not using a client fund
            FilteredFundVO[] filteredFundVOs = engineLookup.getByFundNumber(fundNumberFromFile);

            if ((filteredFundVOs != null) && (filteredFundVOs.length > 0))
            {
                filteredFundVO = filteredFundVOs[0];
            }
        }
        else
        {
            long filteredFundFK = chargeCodeVOs[0].getFilteredFundFK();

            FilteredFundVO[] filteredFundVOs = calcLookup.findFilteredFundByPK(filteredFundFK, false, null);

            if ((filteredFundVOs != null) && (filteredFundVOs.length > 0))
            {
                filteredFundVO = filteredFundVOs[0];
                chargeCodeVO = chargeCodeVOs[0];
            }
        }

        return new Object[] { filteredFundVO, chargeCodeVO };
    }

    private String[] getCashClearanceImportLines(File importFile)
    {
        boolean isFileEmpty = false;

        List importLines = new ArrayList();

        String importLine = null;

        try
        {
            FileReader fr = new FileReader(importFile);

            BufferedReader reader = new BufferedReader(fr);

            while ((importLine = reader.readLine()) != null)
            {
                importLines.add(importLine);
            }

            reader.close();

            isFileEmpty = false;
        }
        catch (Exception e)
        {
            isFileEmpty = true;

            System.out.println(e);

            e.printStackTrace();
        }

        if (isFileEmpty)
        {
            return null;
        }
        else
        {
            return (String[]) importLines.toArray(new String[importLines.size()]);
        }
    }

    private String getFeeTypeCT(String recordType)
    {
        String feeTypeCT = null;

        if (recordType.equalsIgnoreCase("PREM"))
        {
            feeTypeCT = PREMIUM_FEE_TYPE;
        }
        else if (recordType.equalsIgnoreCase("RALL"))
        {
            feeTypeCT = TRANSFER_FEE_TYPE;
        }
        else if (recordType.equalsIgnoreCase("SURR"))
        {
            feeTypeCT = SURRENDER_FEE_TYPE;
        }
        else if (recordType.equalsIgnoreCase("COIN"))
        {
            feeTypeCT = COI_FEE_TYPE;
        }
        else if (recordType.equalsIgnoreCase("ADMF"))
        {
            feeTypeCT = ADMIN_FEE_FEE_TYPE;
        }
        else if (recordType.equalsIgnoreCase("PLNS"))
        {
            feeTypeCT = LOAN_FEE_TYPE;
        }
        else if (recordType.equalsIgnoreCase("LINT"))
        {
            feeTypeCT = LOAN_INT_FEE_TYPE;
        }
        else if (recordType.equalsIgnoreCase("RDTH"))
        {
            feeTypeCT = DEATH_FEE_TYPE;
        }
        else if (recordType.equalsIgnoreCase("ADMT"))
        {
            feeTypeCT = ADVANCE_TRANSFER_FEE_TYPE;
        }
        else if (recordType.equalsIgnoreCase("IMFS"))
        {
            feeTypeCT = MANAGEMENT_FEE_FEE_TYPE;
        }
        else if (recordType.equalsIgnoreCase("M&EX"))
        {
            feeTypeCT = M_AND_E_FEE_TYPE;
        }
        else if (recordType.equalsIgnoreCase("DPPD"))
        {
            feeTypeCT = GAIN_LOSS_FEE_TYPE;
        }
        else if (recordType.equalsIgnoreCase("DWPD"))
        {
            feeTypeCT = GAIN_LOSS_FOR_GAIN_LOSS;
        }
        else if (recordType.equalsIgnoreCase("MANL"))
        {
            feeTypeCT = MANUAL_ENTRY;
        }

        return feeTypeCT;
    }

    /**
     * Set up the export file
     * @return  File - define name
     */
    private String getExportFile()
    {
        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

        String exportFile = export1.getDirectory() + manualCashEntriesFile + "_" +  + System.currentTimeMillis() + ".txt";

        return exportFile;
    }

    private void appendToFile(String data)
    {
        BufferedWriter bw = null;

        try
        {
            bw = new BufferedWriter(new FileWriter(exportFile, true));

            bw.write(data);

            bw.flush();

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                bw.close();
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
