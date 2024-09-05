/*
 * User: dlataill
 * Date: Nov 24, 2004
 * Time: 2:01:40 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package reporting.interfaces;

import edit.services.interfaces.AbstractInterface;
import edit.services.command.Command;
import edit.services.config.ServicesConfig;
import edit.services.*;
import edit.common.vo.*;
import edit.common.EDITBigDecimal;

import java.util.*;
import java.io.*;

import fission.utility.Util;
import batch.business.*;

import edit.common.EDITDateTime;
import java.util.Calendar;

public class AccountingExtractInterfaceCmd extends AbstractInterface implements Command
{
    private String accountingPeriod = null;
    private StringBuffer fileData;
    private String accountingExtractFile = "AccountingExtract";
    private String exportFile = null;

    public AccountingExtractInterfaceCmd(String accountingPeriod)
    {
        this.accountingPeriod = accountingPeriod;
        exportFile = getExportFile(accountingPeriod);
    }

    public Object exec()
    {
        AccountingDetailVO[] accountingDetailVOs =
                new accounting.dm.composer.VOComposer().
                        composeAccountingDetailVOByAccountingPeriod(accountingPeriod, new ArrayList());

        int fieldLen = 0;
        int j = 0;

        String accountingDetailPK = "";
        String companyName = "";
        String marketingPackageName = "";
        String businessContractName = "";
        String contractNumber = "";
        String qualNonQualCT = "";
        String optionCodeCT = "";
        String transactionCode = "";
        String reversalInd = "";
        String memoCode = "";
        String stateCodeCT = "";
        String accountNumber = "";
        String accountName = "";
        String entryAmount = "";
        String debitCreditInd = "";
        String effectiveDate = "";
        String processDate = "";
        String fundNumber = "";
        String outOfBalanceInd = "";
        String accountingPendingStatus = "";
        String qualifiedTypeCT = "";
        String originalContractNumber = "";
        String accountingProcessDate = "";
        String editTrxFK = "";
        String chargeCode = "";
        String distributionCodeCT = "";
        String reinsurerNumber = "";
        String treatyGroupNumber = "";
        int entryDecimalIndex = 0;

        if (accountingDetailVOs != null)
        {
            try
            {
                for (int i = 0; i < accountingDetailVOs.length; i++)
                {
                    accountingDetailPK = accountingDetailVOs[i].getAccountingDetailPK() + "";
                    companyName = Util.initString(accountingDetailVOs[i].getCompanyName(), "");
                    marketingPackageName = Util.initString(accountingDetailVOs[i].getMarketingPackageName(), "");
                    businessContractName = Util.initString(accountingDetailVOs[i].getBusinessContractName(), "");
                    contractNumber = Util.initString(accountingDetailVOs[i].getContractNumber(), "");
                    qualNonQualCT = Util.initString(accountingDetailVOs[i].getQualNonQualCT(), "");
                    optionCodeCT = Util.initString(accountingDetailVOs[i].getOptionCodeCT(), "");
                    transactionCode = Util.initString(accountingDetailVOs[i].getTransactionCode(), "");
                    reversalInd = Util.initString(accountingDetailVOs[i].getReversalInd(), "");
                    memoCode = Util.initString(accountingDetailVOs[i].getMemoCode(), "");
                    stateCodeCT = Util.initString(accountingDetailVOs[i].getStateCodeCT(), "");
                    accountNumber = Util.initString(accountingDetailVOs[i].getAccountNumber(), "");
                    accountName = Util.initString(accountingDetailVOs[i].getAccountName(), "");
                    entryAmount = new EDITBigDecimal(accountingDetailVOs[i].getAmount()).round(2).toString();
                    entryDecimalIndex = entryAmount.indexOf(("."));
                    if (entryAmount.substring(entryDecimalIndex + 1).length() == 1)
                    {
                        entryAmount = entryAmount + "0";
                    }
                    debitCreditInd = Util.initString(accountingDetailVOs[i].getDebitCreditInd(), "");
                    effectiveDate = Util.initString(accountingDetailVOs[i].getEffectiveDate(), "");
                    processDate = Util.initString(accountingDetailVOs[i].getProcessDate(), "");
                    fundNumber = Util.initString(accountingDetailVOs[i].getFundNumber(), "");
                    outOfBalanceInd = Util.initString(accountingDetailVOs[i].getOutOfBalanceInd(), "");
                    accountingPendingStatus = Util.initString(accountingDetailVOs[i].getAccountingPendingStatus(), "");
                    qualifiedTypeCT = Util.initString(accountingDetailVOs[i].getQualifiedTypeCT(), "");
                    originalContractNumber = Util.initString(accountingDetailVOs[i].getOriginalContractNumber(), "");
                    accountingProcessDate = Util.initString(accountingDetailVOs[i].getAccountingProcessDate(), "");
                    accountingPeriod = Util.initString(accountingDetailVOs[i].getAccountingPeriod(), "");
                    editTrxFK = accountingDetailVOs[i].getEDITTrxFK() + "";
                    chargeCode = Util.initString(accountingDetailVOs[i].getChargeCode(), "");
                    distributionCodeCT = Util.initString(accountingDetailVOs[i].getDistributionCodeCT(), "");
                    reinsurerNumber = Util.initString(accountingDetailVOs[i].getReinsurerNumber(), "");
                    treatyGroupNumber = Util.initString(accountingDetailVOs[i].getTreatyGroupNumber(), "");

                    fileData = new StringBuffer();
                    fileData.append(accountingDetailPK);
                    fileData.append(companyName);
                    fieldLen = companyName.length();
                    if (fieldLen < 15)
                    {
                        for (j = 0; j < 15 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(marketingPackageName);
                    fieldLen = marketingPackageName.length();
                    if (fieldLen < 15)
                    {
                        for (j = 0; j < 15 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(businessContractName);
                    fieldLen = businessContractName.length();
                    if (fieldLen < 15)
                    {
                        for (j = 0; j < 15 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(contractNumber);
                    fieldLen = contractNumber.length();
                    if (fieldLen < 15)
                    {
                        for (j = 0; j < 15 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(qualNonQualCT);
                    fieldLen = qualNonQualCT.length();
                    if (fieldLen < 20)
                    {
                        for (j = 0; j < 20 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(optionCodeCT);
                    fieldLen = optionCodeCT.length();
                    if (fieldLen < 20)
                    {
                        for (j = 0; j < 20 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(transactionCode);
                    fieldLen = transactionCode.length();
                    if (fieldLen < 20)
                    {
                        for (j = 0; j < 20 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(reversalInd);
                    fieldLen = reversalInd.length();
                    if (fieldLen < 1)
                    {
                        fileData.append(" ");
                    }
                    fileData.append(memoCode);
                    fieldLen = memoCode.length();
                    if (fieldLen < 7)
                    {
                        for (j = 0; j < 7 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(stateCodeCT);
                    fieldLen = stateCodeCT.length();
                    if (fieldLen < 20)
                    {
                        for (j = 0; j < 20 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(accountNumber);
                    fieldLen = accountNumber.length();
                    if (fieldLen < 20)
                    {
                        for (j = 0; j < 20 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(accountName);
                    fieldLen = accountName.length();
                    if (fieldLen < 30)
                    {
                        for (j = 0; j < 30 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(entryAmount);
                    fieldLen = entryAmount.length();
                    if (fieldLen < 12)
                    {
                        for (j = 0; j < 12 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(debitCreditInd);
                    fieldLen = debitCreditInd.length();
                    if (fieldLen < 20)
                    {
                        for (j = 0; j < 20 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(effectiveDate);
                    fieldLen = effectiveDate.length();
                    if (fieldLen < 10)
                    {
                        for (j = 0; j < 10 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(processDate);
                    fieldLen = processDate.length();
                    if (fieldLen < 10)
                    {
                        for (j = 0; j < 10 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(fundNumber);
                    fieldLen = fundNumber.length();
                    if (fieldLen < 6)
                    {
                        for (j = 0; j < 6 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(outOfBalanceInd);
                    fieldLen = outOfBalanceInd.length();
                    if (fieldLen < 1)
                    {
                        fileData.append(" ");
                    }
                    fileData.append(accountingPendingStatus);
                    fieldLen = accountingPendingStatus.length();
                    if (fieldLen < 1)
                    {
                        fileData.append(" ");
                    }
                    fileData.append(qualifiedTypeCT);
                    fieldLen = qualifiedTypeCT.length();
                    if (fieldLen < 20)
                    {
                        for (j = 0; j < 20 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(originalContractNumber);
                    fieldLen = originalContractNumber.length();
                    if (fieldLen < 20)
                    {
                        for (j = 0; j < 20 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(accountingProcessDate);
                    fieldLen = accountingProcessDate.length();
                    if (fieldLen < 10)
                    {
                        for (j = 0; j < 10 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(accountingPeriod);
                    fieldLen = accountingPeriod.length();
                    if (fieldLen < 7)
                    {
                        for (j = 0; j < 7 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(editTrxFK);
                    fieldLen = editTrxFK.length();
                    if (fieldLen < 19)
                    {
                        for (j = 0; j < 19 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(chargeCode);
                    fieldLen = chargeCode.length();
                    if (fieldLen < 6)
                    {
                        for (j = 0; j < 6 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(distributionCodeCT);
                    fieldLen = distributionCodeCT.length();
                    if (fieldLen < 20)
                    {
                        for (j = 0; j < 20 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(reinsurerNumber);
                    fieldLen = reinsurerNumber.length();
                    if (fieldLen < 20)
                    {
                        for (j = 0; j < 20 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }
                    fileData.append(treatyGroupNumber);
                    fieldLen = treatyGroupNumber.length();
                    if (fieldLen < 20)
                    {
                        for (j = 0; j < 20 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }

                    fileData.append("\n");

                    writeToFile(fileData.toString());

                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_FLAT).updateSuccess();
                    
                }
            }
            catch (Exception e)
            {
                EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_FLAT).updateSuccess();

              System.out.println(e);

                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Set up the export file
     * @return  File - define name
     */
    private String getExportFile(String accountingPeriod)
    {
    	StringTokenizer s = new StringTokenizer(accountingPeriod, "/"); 
    	
    	String year = s.nextToken();
    	String month = s.nextToken();
    	
    	String accntPeriod = year + month;
    	        
        EDITDateTime currentDateTime = new EDITDateTime();
        
        String currentDate = currentDateTime.getEDITDate().toString();
        
        StringTokenizer st = new StringTokenizer(currentDate, "/");
        
        String currentYear = st.nextToken();
        String currentMonth = st.nextToken();
        String currentDay = st.nextToken();
        
        currentDate = currentMonth + "-" + currentDay + "-" + currentYear;
        
        String time = currentDateTime.getFormattedTime();
        
        StringTokenizer sTime = new StringTokenizer (time, ":");
        
        String hour = sTime.nextToken();
        String min = sTime.nextToken();
        String seconds = sTime.nextToken();
        
        time = hour + "-" + min + "-" + seconds;
        
        Calendar c = Calendar.getInstance();
        int am_pm = c.get(Calendar.AM_PM);
        
        String AM_PM = " ";
        
        if (am_pm == 0){
        	
        	AM_PM = "AM";
        }
        else{
        	
        	AM_PM = "PM";
        }
                        
        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory3");
        
        //Export file name to include accounting period, current date and time. 

        String exportFile = export1.getDirectory() + accountingExtractFile + "_" + accntPeriod + "_" + currentDate + " " + time + " " + AM_PM + ".txt";

        return exportFile;
    }

    private void writeToFile(String data)
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
