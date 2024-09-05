/**
 * User: dlataill
 * Date: Sep 8, 2006
 * Time: 12:33:50 PM
 * <p/>
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package engine;

import edit.common.*;
import edit.common.vo.FeeVO;
import fission.utility.Util;

public class FeeFilterRow
{
    private String accountingPendingStatus;
    private EDITDate processDate;
    private EDITDate effectiveDate;
    private EDITDate releaseDate;
    private String transactionTypeCT;
    private String status;
    private EDITBigDecimal amount;
    private String chargeCode;

    private String key;

    public FeeFilterRow(Fee fee)
    {
        buildFeeRow(fee);

        establishKey(fee.getClass(), new Long(((FeeVO) fee.getVO()).getFeePK()));
    }

    /**
     * A compound key of "PK:ClassName"
     *
     * @return String where the format is "PK:ClassName"
     */
    public String getKey()
    {
        return key;
    }

    /**
     * Builds a composite key for this row as "ClassName:PK".
     *
     * @return
     */
    private void establishKey(Class theClass, Long pk)
    {
        key = Util.getClassName(Util.getFullyQualifiedClassName(theClass)) + "_" + pk.toString();
    }

    /**
     * Returns the PK part of the "ClassName:PK" key.
     *
     * @param key
     * @return
     */
    public static Long getPKFromKey(String key)
    {
        String[] tokens = Util.fastTokenizer(key, "_");

        return new Long(tokens[1]);
    }

    /**
     * Returns the ClassName part of the "PK:ClassName" key.
     *
     * @param key
     * @return
     */
    public static String getClassNameFromKey(String key)
    {
        String[] tokens = Util.fastTokenizer(key, "_");

        return tokens[0];
    }

    /**
     * Getter.
     * @return
     */
    public String getAccountingPendingStatus()
    {
        return accountingPendingStatus;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getProcessDate()
    {
        return processDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getReleaseDate()
    {
        return releaseDate;
    }

    /**
     * Getter.
     * @return
     */
    public String getTransactionTypeCT()
    {
        return transactionTypeCT;
    }

    /**
     * Getter.
     * @return
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAmount()
    {
        return amount;
    }

    /**
     * Getter.
     * @return
     */
    public String getChargeCode()
    {
        return chargeCode;
    }

    private void buildFeeRow(Fee fee)
    {
        accountingPendingStatus = ((FeeVO) fee.getVO()).getAccountingPendingStatus();
        processDate = new EDITDateTime(((FeeVO) fee.getVO()).getProcessDateTime()).getEDITDate();
        effectiveDate = new EDITDate(((FeeVO) fee.getVO()).getEffectiveDate());

        if ((((FeeVO) fee.getVO()).getReleaseDate()) != null)
        {
            releaseDate = new EDITDate(((FeeVO) fee.getVO()).getReleaseDate());    
        }
        
        transactionTypeCT = ((FeeVO) fee.getVO()).getTransactionTypeCT();
        status = ((FeeVO) fee.getVO()).getStatusCT();

        amount = new EDITBigDecimal(((FeeVO) fee.getVO()).getTrxAmount());
        chargeCode = Util.initString(((FeeVO) fee.getVO()).getChargeCodeFK() + "", "0");
    }
}

