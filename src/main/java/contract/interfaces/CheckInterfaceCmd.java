/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Nov 18, 2002
 * Time: 2:07:03 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package contract.interfaces;

import edit.services.command.Command;
import edit.services.interfaces.AbstractInterface;
import edit.services.*;
import edit.common.vo.*;
import edit.common.*;
import fission.utility.*;

import java.util.*;

import engine.*;
import batch.business.*;


public class CheckInterfaceCmd extends AbstractInterface implements Command {

    private EDITTrxHistoryVO editTrxHistoryVO;
    private ClientDetailVO clientDetailVO;
    private StringBuffer fileData;
    private Hashtable checkValues;
    private final static String oneSpace = " ";

    public void setCheckInformation(EDITTrxHistoryVO editTrxHistoryVO,
                                      ClientDetailVO clientDetailVO,
                                      StringBuffer fileData,
                                      Hashtable checkValues)
    {

        this.editTrxHistoryVO = editTrxHistoryVO;
        this.clientDetailVO = clientDetailVO;
        this.fileData = fileData;
        this.checkValues = checkValues;
    }

    public Object exec()
    {
        EDITTrxVO editTrxVO = (EDITTrxVO)editTrxHistoryVO.getParentVO(EDITTrxVO.class);
        EDITDate trxEffDate = new EDITDate(editTrxVO.getEffectiveDate());
        int trxEffMonth   = trxEffDate.getMonth();
        int trxEffDay     = trxEffDate.getDay();

        EDITDateTime processDateTime = new EDITDateTime(editTrxHistoryVO.getProcessDateTime());
        EDITDate processDate = processDateTime.getEDITDate();

        ClientAddressVO[] clientAddressVOs = clientDetailVO.getClientAddressVO();

        String addressLine1 = "";
        String addressLine2 = "";
        String city         = "";
        String state        = "";
        String zipCode      = "";

        for (int i = 0; i < clientAddressVOs.length; i++)
        {

            String addressType = clientAddressVOs[i].getAddressTypeCT();
            if (addressType.equalsIgnoreCase("PrimaryAddress") &&
                addressLine1.equals("")) {

                addressLine1 = (Util.initString(clientAddressVOs[i].getAddressLine1(), "")).toUpperCase();
                addressLine2 = (Util.initString(clientAddressVOs[i].getAddressLine2(), "")).toUpperCase();
                city         = (Util.initString(clientAddressVOs[i].getCity(), "")).toUpperCase();
                state        = Util.initString(clientAddressVOs[i].getStateCT(), "");
                zipCode      = Util.initString(clientAddressVOs[i].getZipCode(), "");
            }

            if (addressType.equalsIgnoreCase("SecondaryAddress"))
            {
                EDITDate altEffDate = new EDITDate(EDITDate.DEFAULT_MIN_DATE);
                if (clientAddressVOs[i].getEffectiveDate() != null)
                {
                    altEffDate  = new EDITDate(clientAddressVOs[i].getEffectiveDate());
                }
                
                EDITDate altTermDate = new EDITDate(clientAddressVOs[i].getTerminationDate());
                String startDate  = clientAddressVOs[i].getStartDate();
                String stopDate   = clientAddressVOs[i].getStopDate();
                StringTokenizer startDateTokenizer = new StringTokenizer(startDate, EDITDate.DATE_DELIMITER);
                int startDateMonth = Integer.parseInt(startDateTokenizer.nextToken());
                int startDateDay   = Integer.parseInt(startDateTokenizer.nextToken());

                StringTokenizer stopDateTokenizer = new StringTokenizer(stopDate, EDITDate.DATE_DELIMITER);
                int stopDateMonth   = Integer.parseInt(stopDateTokenizer.nextToken());
                int stopDateDay     = Integer.parseInt(stopDateTokenizer.nextToken());


                if (trxEffDate.afterOREqual(altEffDate) &&
                    (altTermDate.equals(new EDITDate(EDITDate.DEFAULT_MAX_DATE)) ||
                     trxEffDate.beforeOREqual(altTermDate)))
                {

                    if (stopDateMonth < startDateMonth)
                    {

                        stopDateMonth = stopDateMonth + 12;
                    }

                    if ((trxEffMonth >= startDateMonth &&
                         trxEffDay   >= startDateDay) &&
                        trxEffMonth <= stopDateMonth)
                    {

                        if (trxEffMonth == stopDateMonth)
                        {

                            if (trxEffDay <= stopDateDay)
                            {

                                addressLine1 = (Util.initString(clientAddressVOs[i].getAddressLine1(), "")).toUpperCase();
                                addressLine2 = (Util.initString(clientAddressVOs[i].getAddressLine2(), "")).toUpperCase();
                                city         = (Util.initString(clientAddressVOs[i].getCity(), "")).toUpperCase();
                                state        = Util.initString(clientAddressVOs[i].getStateCT(), "");
                                zipCode      = Util.initString(clientAddressVOs[i].getZipCode(), "");
                            }
                        }

                        else
                        {

                            addressLine1 = (Util.initString(clientAddressVOs[i].getAddressLine1(), "")).toUpperCase();
                            addressLine2 = (Util.initString(clientAddressVOs[i].getAddressLine2(), "")).toUpperCase();
                            city         = (Util.initString(clientAddressVOs[i].getCity(), "")).toUpperCase();
                            state        = Util.initString(clientAddressVOs[i].getStateCT(), "");
                            zipCode      = Util.initString(clientAddressVOs[i].getZipCode(), "");
                        }
                    }
                }
            }
        }

        String printAs = "";
        String printAs2 = "";
        if (clientDetailVO != null)
        {
            if (clientDetailVO.getPreferenceVOCount() > 0)
            {
                PreferenceVO preferenceVO = clientDetailVO.getPreferenceVO(0);
                printAs = (Util.initString(preferenceVO.getPrintAs(), "")).trim();
                printAs2 = (Util.initString(preferenceVO.getPrintAs2(), "")).trim();
            }
        }

        SegmentVO segmentVO = (SegmentVO)editTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class).getParentVO(SegmentVO.class);
        String companyName = "";
        if (segmentVO != null)
        {
            ProductStructure productStructure = ProductStructure.findByPK(segmentVO.getProductStructureFK());
            Company company = Company.findByPK(productStructure.getCompanyFK());
            companyName = company.getCompanyName();
        }

        FinancialHistoryVO financialHistoryVO = editTrxHistoryVO.getFinancialHistoryVO(0);
        EDITBigDecimal grossAmount = new EDITBigDecimal(financialHistoryVO.getGrossAmount());
        EDITBigDecimal checkAmount = new EDITBigDecimal(financialHistoryVO.getCheckAmount());

        WithholdingHistoryVO withholdingHistoryVO = null;
        EDITBigDecimal fedWithholding = new EDITBigDecimal();
        EDITBigDecimal stateWithholding = new EDITBigDecimal();

        if (editTrxHistoryVO.getWithholdingHistoryVOCount() > 0)
        {
            withholdingHistoryVO = editTrxHistoryVO.getWithholdingHistoryVO(0);
            fedWithholding = new EDITBigDecimal(withholdingHistoryVO.getFederalWithholdingAmount());
            stateWithholding = new EDITBigDecimal(withholdingHistoryVO.getStateWithholdingAmount());
        }

        EDITBigDecimal checkGrossDollars = ((EDITBigDecimal)checkValues.get("checkGrossDollars"));
        checkGrossDollars = checkGrossDollars.addEditBigDecimal(grossAmount);
        checkValues.put("checkGrossDollars", checkGrossDollars);

        EDITBigDecimal checkFedWithholding = ((EDITBigDecimal)checkValues.get("checkFedWithholding"));
        checkFedWithholding = checkFedWithholding.addEditBigDecimal(fedWithholding);
        checkValues.put("checkFedWithholding", checkFedWithholding);

        EDITBigDecimal checkStateWithholding = ((EDITBigDecimal)checkValues.get("checkStateWithholding"));
        checkStateWithholding =  checkStateWithholding.addEditBigDecimal(stateWithholding);
        checkValues.put("checkStateWithholding", checkStateWithholding);

        EDITBigDecimal checkCheckAmtDollars = ((EDITBigDecimal)checkValues.get("checkCheckAmtDollars"));
        checkCheckAmtDollars =  checkCheckAmtDollars.addEditBigDecimal(checkAmount);
        checkValues.put("checkCheckAmtDollars", checkCheckAmtDollars);

        EDITBigDecimal numberOfCheckRecords = ((EDITBigDecimal)checkValues.get("numberOfCheckRecords"));
        numberOfCheckRecords = numberOfCheckRecords.addEditBigDecimal("1");
        checkValues.put("numberOfCheckRecords", numberOfCheckRecords);

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
            String firstName  = clientDetailVO.getFirstName();
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

        fieldLen = addressLine1.length();
        numSpaces = 35 - fieldLen;
        fileData.append(addressLine1);
        for (int s = 1; s <= numSpaces; s++)
        {
            fileData.append(oneSpace);
        }

        fieldLen = addressLine2.length();
        numSpaces = 35 - fieldLen;
        fileData.append(addressLine2);
        for (int s = 1; s <= numSpaces; s++)
        {
            fileData.append(oneSpace);
        }

        fieldLen = city.length();
        numSpaces = 35 - fieldLen;
        fileData.append(city);
        for (int s = 1; s <= numSpaces; s++)
        {
            fileData.append(oneSpace);
        }

        fieldLen = state.length();
        numSpaces = 2 - fieldLen;
        fileData.append(state);
        for (int s = 1; s <= numSpaces; s++)
        {
            fileData.append(oneSpace);
        }

        fieldLen = zipCode.length();
        if (zipCode.length() > 9)
        {
            zipCode = zipCode.substring(0, 9);
            numSpaces = 0;
        }
        else
        {
            numSpaces = 9 - fieldLen;
        }

        fileData.append(zipCode);
        for (int s = 1; s <= numSpaces; s++)
        {
            fileData.append(oneSpace);
        }

        String formattedDollars = Util.formatDecimal("###,###,##0.00", grossAmount);
        fieldLen = formattedDollars.length();
        numSpaces = 15 - fieldLen;
        fileData.append(formattedDollars);
        for (int s = 1; s <= numSpaces; s++)
        {
            fileData.append(oneSpace);
        }

        formattedDollars = Util.formatDecimal("###,###,##0.00", fedWithholding);
        fieldLen = formattedDollars.length();
        numSpaces = 15 - fieldLen;
        fileData.append(formattedDollars);
        for (int s = 1; s <= numSpaces; s++)
        {
            fileData.append(oneSpace);
        }

        formattedDollars = Util.formatDecimal("###,###,##0.00", stateWithholding);
        fieldLen = formattedDollars.length();
        numSpaces = 15 - fieldLen;
        fileData.append(formattedDollars);
        for (int s = 1; s <= numSpaces; s++)
        {
            fileData.append(oneSpace);
        }

        formattedDollars = Util.formatDecimal("###,###,##0.00", checkAmount);
        fieldLen = formattedDollars.length();
        numSpaces = 15 - fieldLen;
        fileData.append(formattedDollars);
        for (int s = 1; s <= numSpaces; s++)
        {
            fileData.append(oneSpace);
        }

        fieldLen = (clientDetailVO.getTaxIdentification()).length();
        numSpaces = 10 - fieldLen;
        fileData.append(clientDetailVO.getTaxIdentification());
        for (int s = 1; s <= numSpaces; s++)
        {
            fileData.append(oneSpace);
        }


        fileData.append(trxEffDate.getFormattedDate());
        fileData.append(processDate.getFormattedDate());

        fieldLen = contractNumber.length();
        fieldLen += (editTrxVO.getSequenceNumber() + "").length();
        numSpaces = 18 - fieldLen;
        fileData.append(contractNumber.trim().toUpperCase());
        fileData.append(editTrxVO.getSequenceNumber());
        for (int s = 1; s <= numSpaces; s++)
        {
            fileData.append(oneSpace);
        }

        try
        {
            exportExtract(fileData, "SEGCHK.prn");
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_BANK_EXTRACTS).updateFailure();
            System.out.println(e);
            e.printStackTrace();
        }

        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_BANK_EXTRACTS).updateSuccess();

        return null;
    }

    public void exportExtract(StringBuffer fileData, String fileName) throws Exception {

        super.exportData2x(fileData.toString(), fileName);
    }

    public void exportReconciliation(StringBuffer reconData, String fileName) throws Exception {

        super.exportData(reconData.toString(), fileName);
    }
}
