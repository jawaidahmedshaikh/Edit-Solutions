/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Nov 18, 2002
 * Time: 4:07:00 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package contract.interfaces;

import edit.common.*;
import edit.common.vo.*;
import edit.services.command.Command;
import edit.services.interfaces.AbstractInterface;
import edit.services.*;
import edit.services.db.hibernate.*;
import fission.utility.Util;

import java.util.Hashtable;

import engine.*;
import batch.business.*;

public class EFTInterfaceCmd  extends AbstractInterface implements Command
{

    private EDITTrxHistoryVO editTrxHistoryVO;
    private ClientDetailVO clientDetailVO;
    private StringBuffer fileData;
    private Hashtable eftValues;
    private final static String oneSpace = " ";

    public void setEFTInformation(EDITTrxHistoryVO editTrxHistoryVO,
                                    ClientDetailVO clientDetailVO,
                                    StringBuffer fileData,
                                    Hashtable eftValues)
    {

        this.editTrxHistoryVO = editTrxHistoryVO;
        this.clientDetailVO = clientDetailVO;
        this.fileData = fileData;
        this.eftValues = eftValues;
    }

    public Object exec()
    {
        EDITTrxVO editTrxVO = (EDITTrxVO)editTrxHistoryVO.getParentVO(EDITTrxVO.class);
        EDITDate trxEffDate = new EDITDate(editTrxVO.getEffectiveDate());

        EDITDateTime processDateTime = new EDITDateTime(editTrxHistoryVO.getProcessDateTime());
        EDITDate processDate = processDateTime.getEDITDate();

        String accountType = "";
        String bankRoutingNumber = "";
        String bankAccountNumber = "";
        String printAs = "";
        String printAs2 = "";

        if (clientDetailVO != null)
        {
            if (clientDetailVO.getPreferenceVOCount() > 0)
            {
                PreferenceVO preferenceVO = clientDetailVO.getPreferenceVO(0);
                if (preferenceVO.getOverrideStatus().equalsIgnoreCase("P"))
                {
                    printAs = (Util.initString(preferenceVO.getPrintAs(), "")).trim();
                    printAs2 = (Util.initString(preferenceVO.getPrintAs2(), "")).trim();
                    accountType = Util.initString(preferenceVO.getBankAccountTypeCT(), "");
                    bankRoutingNumber = Util.initString(preferenceVO.getBankRoutingNumber(), "");
                    bankAccountNumber = Util.initString(preferenceVO.getBankAccountNumber(), "");
                }
            }
        }


        SegmentVO segmentVO = (SegmentVO)editTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class).getParentVO(SegmentVO.class);
        String companyName = "";
        if (segmentVO != null)
        {
            Company company = Company.findByProductStructurePK(segmentVO.getProductStructureFK());
            companyName = company.getCompanyName();
        }

        FinancialHistoryVO financialHistoryVO = editTrxHistoryVO.getFinancialHistoryVO(0);
        EDITBigDecimal grossAmount = new EDITBigDecimal(financialHistoryVO.getGrossAmount());
        EDITBigDecimal checkAmount = new EDITBigDecimal(financialHistoryVO.getCheckAmount());

        EDITBigDecimal fedWithholding = new EDITBigDecimal();
        EDITBigDecimal stateWithholding = new EDITBigDecimal();
        if (editTrxHistoryVO.getWithholdingHistoryVOCount() > 0)
        {
            WithholdingHistoryVO withholdingHistoryVO = editTrxHistoryVO.getWithholdingHistoryVO(0);

                fedWithholding = new EDITBigDecimal(withholdingHistoryVO.getFederalWithholdingAmount());
                stateWithholding = new EDITBigDecimal(withholdingHistoryVO.getStateWithholdingAmount());
        }

        EDITBigDecimal eftGrossDollars = ((EDITBigDecimal)eftValues.get("eftGrossDollars"));
        eftGrossDollars = eftGrossDollars.addEditBigDecimal(grossAmount);
        eftValues.put("eftGrossDollars", eftGrossDollars);

        EDITBigDecimal eftFedWithholding = ((EDITBigDecimal)eftValues.get("eftFedWithholding"));
        eftFedWithholding =  eftFedWithholding.addEditBigDecimal(fedWithholding);
        eftValues.put("eftFedWithholding", eftFedWithholding);

        EDITBigDecimal eftStateWithholding = ((EDITBigDecimal)eftValues.get("eftStateWithholding"));
        eftStateWithholding = eftStateWithholding.addEditBigDecimal(stateWithholding);
        eftValues.put("eftStateWithholding", eftStateWithholding);

        EDITBigDecimal eftCheckAmtDollars = ((EDITBigDecimal)eftValues.get("eftCheckAmtDollars"));
        eftCheckAmtDollars = eftCheckAmtDollars.addEditBigDecimal(checkAmount);
        eftValues.put("eftCheckAmtDollars", eftCheckAmtDollars);

        EDITBigDecimal numberOfEFTRecords = ((EDITBigDecimal)eftValues.get("numberOfEFTRecords"));
        numberOfEFTRecords = numberOfEFTRecords.addEditBigDecimal("1");
        eftValues.put("numberOfEFTRecords", numberOfEFTRecords);

        if (fileData.length() > 0)
        {
            fileData.append("\n");
        }

        int fieldLen = companyName.length();
        int numSpaces = 15 - fieldLen;
        fileData.append(companyName.toUpperCase());
        for (int s = 1; s <= numSpaces; s++)
        {
            fileData.append(oneSpace);
        }

        String contractNumber = "";
        if (segmentVO != null)
        {
            contractNumber = segmentVO.getContractNumber();
        }

        fieldLen = contractNumber.length();
        numSpaces = 15 - fieldLen;
        fileData.append(contractNumber.toUpperCase());
        for (int s = 1; s <= numSpaces; s++)
        {
            fileData.append(oneSpace);
        }

        if (printAs.equals(""))
        {
            fieldLen = 0;
            String firstName = clientDetailVO.getFirstName();
            String middleName = clientDetailVO.getMiddleName();
            String lastName   = clientDetailVO.getLastName();
            if (firstName != null)
            {
                fieldLen = firstName.trim().length();
                fieldLen += 1;
                fileData.append(firstName.trim().toUpperCase());
                fileData.append(" ");
            }

            if (middleName != null)
            {
                fieldLen += middleName.trim().length();
                fieldLen += 1;
                fileData.append(middleName.trim().toUpperCase());
                fileData.append(" ");
            }

            if (lastName != null)
            {
                fieldLen += lastName.trim().length();
                fileData.append(lastName.trim().toUpperCase());
            }
        }

        else
        {
            fieldLen = printAs.length();
            if (fieldLen > 60)
            {

                printAs = printAs.substring(0, printAs.length() - 10);
                fieldLen = 60;
            }

            fileData.append(printAs.toUpperCase());
        }

        numSpaces = 60 - fieldLen;
        for (int s = 1; s <= numSpaces; s++)
        {

            fileData.append(oneSpace);
        }

        fieldLen = printAs2.length();
        if (fieldLen > 60)
        {

            printAs2 = printAs2.substring(0, printAs2.length() - 10);
            fieldLen = 60;
        }

        numSpaces = 60 - fieldLen;
        fileData.append(printAs2.toUpperCase());
        for (int s = 1; s <= numSpaces; s++)
        {

            fileData.append(oneSpace);
        }

        fieldLen = bankRoutingNumber.length();;
        numSpaces = 9 - fieldLen;
        fileData.append(bankRoutingNumber);
        for (int s = 1; s <= numSpaces; s++) {

            fileData.append(oneSpace);
        }

        fieldLen = bankAccountNumber.length();;
        numSpaces = 17 - fieldLen;
        fileData.append(bankAccountNumber);
        for (int s = 1; s <= numSpaces; s++) {

            fileData.append(oneSpace);
        }

        fieldLen = accountType.length();;
        numSpaces = 1 - fieldLen;
        fileData.append(accountType);
        for (int s = 1; s <= numSpaces; s++) {

            fileData.append(oneSpace);
        }

        String formattedDollars = Util.formatDecimal("###,###,##0.00", grossAmount.round(2));
        fieldLen = formattedDollars.length();
        numSpaces = 15 - fieldLen;
        fileData.append(formattedDollars);
        for (int s = 1; s <= numSpaces; s++) {

            fileData.append(oneSpace);
        }

        formattedDollars = Util.formatDecimal("###,###,##0.00", fedWithholding.round(2));
        fieldLen = formattedDollars.length();
        numSpaces = 15 - fieldLen;
        fileData.append(formattedDollars);
        for (int s = 1; s <= numSpaces; s++) {

            fileData.append(oneSpace);
        }

        formattedDollars = Util.formatDecimal("###,###,##0.00", stateWithholding.round(2));
        fieldLen = formattedDollars.length();
        numSpaces = 15 - fieldLen;
        fileData.append(formattedDollars);
        for (int s = 1; s <= numSpaces; s++) {

            fileData.append(oneSpace);
        }

        formattedDollars = Util.formatDecimal("###,###,##0.00", checkAmount.round(2));
        fieldLen = formattedDollars.length();
        numSpaces = 15 - fieldLen;
        fileData.append(formattedDollars);
        for (int s = 1; s <= numSpaces; s++) {

            fileData.append(oneSpace);
        }

        fieldLen = (clientDetailVO.getTaxIdentification()).length();
        numSpaces = 10 - fieldLen;
        fileData.append(clientDetailVO.getTaxIdentification());
        for (int s = 1; s <= numSpaces; s++) {

            fileData.append(oneSpace);
        }

        fileData.append(trxEffDate.getFormattedDate());
        fileData.append(processDate.getFormattedDate());

        fieldLen = contractNumber.length();
        fieldLen += (editTrxVO.getSequenceNumber() + "").length();
        numSpaces = 18 - fieldLen;
        fileData.append(contractNumber.trim().toUpperCase());
        fileData.append(editTrxVO.getSequenceNumber());
        for (int s = 1; s <= numSpaces; s++) {

            fileData.append(oneSpace);
        }

        try
        {
            exportExtract(fileData, "SEGEFT.prn");
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_BANK_EXTRACTS).updateFailure();
            System.out.println(e);
            e.printStackTrace();
        }

        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_BANK_EXTRACTS).updateSuccess();

        SessionHelper.closeSessions();

        return null;
    }

    public void exportExtract(StringBuffer fileData, String fileName) throws Exception {

        super.exportData2x(fileData.toString(), fileName);
    }

    public void exportReconciliation(StringBuffer reconData, String fileName) throws Exception {

        super.exportData(reconData.toString(), fileName);
    }
}
