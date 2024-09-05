/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Nov 18, 2002
 * Time: 2:07:03 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package accounting.interfaces;

import edit.common.vo.AccountEffectVO;
import edit.common.vo.AccountingExtractVO;
import edit.common.vo.SegmentVO;
import edit.common.EDITBigDecimal;
import edit.services.command.Command;
import edit.services.interfaces.AbstractInterface;
import fission.utility.Util;

import java.io.FileWriter;

public class AccountingInterfaceCmd extends AbstractInterface implements Command {

    private SegmentVO segmentVO;
    private AccountEffectVO accountEffectVO;
    private StringBuffer fileData;

    private String companyName;
    private String businessContract;
    private String transactionCode;
    private String memoCode;
    private String effectiveDate;
    private String processDate;
    private final static String oneSpace = " ";

    public void setAccountingInformation(SegmentVO segmentVO,
                                          AccountEffectVO accountEffectVO,
                                           String companyName,
                                            String businessContract,
                                             String transactionCode,
                                              String memoCode,
                                               String effectiveDate,
                                                String processDate,
                                                 StringBuffer fileData){

        this.segmentVO = segmentVO;
        this.accountEffectVO = accountEffectVO;
        this.fileData = fileData;

        this.companyName = Util.initString(companyName, "");
        this.businessContract = Util.initString(businessContract, "");
        this.transactionCode = Util.initString(transactionCode, "");
        this.memoCode = Util.initString(memoCode, "");
        this.effectiveDate = effectiveDate;
        this.processDate = processDate;
    }

    public Object exec()
    {
        if (fileData.length() > 0) {

            fileData.append("\n");
        }

        int fieldLen = companyName.length();
        int numSpaces = 15 - fieldLen;
        int s = 0;
        fileData.append(companyName.toUpperCase());
        for (s = 1; s <= numSpaces; s++) {

            fileData.append(oneSpace);
        }

        String contractNumber = "";
        if (segmentVO != null)
        {
            fieldLen = (segmentVO.getContractNumber()).length();
            contractNumber = segmentVO.getContractNumber();
        }
        else
        {
            fieldLen = 0;
        }
        numSpaces = 15 - fieldLen;
        fileData.append(contractNumber.toUpperCase());
        for (s = 1; s <= numSpaces; s++) {

            fileData.append(oneSpace);
        }

        fieldLen = businessContract.length();
        numSpaces = 15 - fieldLen;
        fileData.append(businessContract.toUpperCase());
        for (s = 1; s <= numSpaces; s++) {

            fileData.append(oneSpace);
        }

        fieldLen = transactionCode.length();
        numSpaces = 20 - fieldLen;
        fileData.append(transactionCode);
        for (s = 1; s <= numSpaces; s++) {

            fileData.append(oneSpace);
        }

        fieldLen = memoCode.length();
        numSpaces = 7 - fieldLen;
        fileData.append(memoCode.toUpperCase());
        for (s = 1; s <= numSpaces; s++) {

            fileData.append(oneSpace);
        }

        String issueStateCT = "  ";
        if (segmentVO != null)
        {
            issueStateCT = segmentVO.getIssueStateCT();
        }
        fileData.append(issueStateCT);

        fieldLen = (accountEffectVO.getAccountNumber()).length();
        numSpaces = 20 - fieldLen;
        fileData.append(accountEffectVO.getAccountNumber());
        for (s = 1; s <= numSpaces; s++) {

            fileData.append(oneSpace);
        }

        String formattedDollars = Util.formatDecimal("#########0.00", Util.roundDollars(
                                                                      new EDITBigDecimal(accountEffectVO.getAccountAmount())
                                                                                    )
                                                    );
        fieldLen = formattedDollars.length();
        numSpaces = 13 - fieldLen;
        fileData.append(formattedDollars);
        for (s = 1; s <= numSpaces; s++) {

            fileData.append(oneSpace);
        }

        fieldLen = (accountEffectVO.getAccountEffect()).length();
        numSpaces = 1 - fieldLen;
        if (fieldLen > 1)
        {
            fileData.append(accountEffectVO.getAccountEffect().subSequence(0,1));
        }
        else
        {
            fileData.append(accountEffectVO.getAccountEffect());
        }
        for (s = 1; s <= numSpaces; s++) {

            fileData.append(oneSpace);
        }

        fileData.append(effectiveDate);
        fileData.append(processDate);
        // This is where the Agent Number should go (11 bytes)
        fileData.append("           ");

        return null;
    }

    public void exportExtract(StringBuffer fileData, String fileName) throws Exception {

        super.exportData2x(fileData.toString(), fileName);
    }

    public void exportVOs(AccountingExtractVO accountingExtractVO, String fileName) throws Exception
    {
            FileWriter fw = new FileWriter(exportDirectory1 + fileName);

            accountingExtractVO.marshal(fw);

            fw.close();
     }
}
