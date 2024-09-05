package agent;

import edit.common.EDITDate;
import edit.common.EDITDateTime;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 1, 2003
 * Time: 3:56:39 PM
 * To change this template use Options | File Templates.
 */
public class CommissionStatement
{
    private CommissionStatementImpl commissionStatementImpl;

    public static final String EXPORT_FILE = "CommissionStatementVO";

    public static final String COMMISSION_STATEMENT_BEG_ELEMENT = "<CommissionStatementVO>";

    public static final String COMMSSION_STATEMENT_END_ELEMENT = "</CommissionStatementVO>";

    public CommissionStatement()
    {
        this.commissionStatementImpl = new CommissionStatementImpl();
    }

    public void generateCommissionStatements(String contractCodeCT, 
                                             String paymentModeCT, 
                                             String processDate, 
                                             String outputFileType)
    {
        EDITDateTime stagingDate = new EDITDateTime(processDate.toString() + EDITDateTime.DATE_TIME_DELIMITER + new EDITDateTime().getFormattedTime());

        commissionStatementImpl.generateCommissionStatementByContractCodeCTAndPaymentModeCT(contractCodeCT, paymentModeCT, new EDITDate(processDate), outputFileType, stagingDate);
    }
}
