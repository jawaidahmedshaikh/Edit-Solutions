/*
 * User: dlataill
 * Date: Aug 29, 2005
 * Time: 2:10:47 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import edit.common.EDITBigDecimal;
import edit.common.vo.*;
import edit.services.db.hibernate.*;
import fission.utility.*;


public class OutSuspense extends HibernateEntity
{
    private Long outSuspensePK;
    private Long contractSetupFK;
    private Long suspenseFK;
    private EDITBigDecimal amount;

    private ContractSetup contractSetup;
    private Suspense suspense;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Getter.
     * @return
     */
    public Suspense getSuspense()
    {
        return suspense;
    }

    /**
     * Setter.
     * @param suspense
     */
    public void setSuspense(Suspense suspense)
    {
        this.suspense = suspense;
    }

    /**
     * Getter.
     * @return
     */
    public Long getSuspenseFK()
    {
        return suspenseFK;
    }

    /**
     * Setter.
     * @param suspenseFK
     */
    public void setSuspenseFK(Long suspenseFK)
    {
        this.suspenseFK = suspenseFK;
    }

    /**
     * Getter.
     * @return
     */
    public ContractSetup getContractSetup()
    {
        return contractSetup;
    }

    /**
     * Setter.
     * @param contractSetup
     */
    public void setContractSetup(ContractSetup contractSetup)
    {
        this.contractSetup = contractSetup;
    }

    /**
     * Getter.
     * @return
     */
    public Long getContractSetupFK()
    {
        return contractSetupFK;
    }

    /**
     * Setter.
     * @param contractSetupFK
     */
    public void setContractSetupFK(Long contractSetupFK)
    {
        this.contractSetupFK = contractSetupFK;
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
     * Setter.
     * @param amount
     */
    public void setAmount(EDITBigDecimal amount)
    {
        this.amount = amount;
    }

    /**
     * Getter.
     * @return
     */
    public Long getOutSuspensePK()
    {
        return outSuspensePK;
    }

    /**
     * Setter.
     * @param outSuspensePK
     */
    public void setOutSuspensePK(Long outSuspensePK)
    {
        this.outSuspensePK = outSuspensePK;
    }

    public static OutSuspense buildDefaultOutSuspense(EDITBigDecimal trxAmount, Suspense suspense)
    {
        OutSuspense outSuspense = new OutSuspense();

        outSuspense.setSuspenseFK(suspense.getSuspensePK());
        outSuspense.setAmount(Util.roundToNearestCent(trxAmount));

        return outSuspense;
    }

    public static OutSuspenseVO buildDefaultOutSuspenseVO(EDITBigDecimal trxAmount, SuspenseVO suspenseVO)
    {
        OutSuspenseVO outSuspenseVO = new OutSuspenseVO();
        outSuspenseVO.setOutSuspensePK(0);
        outSuspenseVO.setSuspenseFK(suspenseVO.getSuspensePK());
        outSuspenseVO.setContractSetupFK(0);
        outSuspenseVO.setAmount(Util.roundToNearestCent(trxAmount.getBigDecimal()).getBigDecimal());

        return outSuspenseVO;
    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, OutSuspense.DATABASE);
    }

    public void hDelete()
    {
        SessionHelper.delete(this, OutSuspense.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return OutSuspense.DATABASE;
    }
}
