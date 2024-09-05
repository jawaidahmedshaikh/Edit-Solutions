/*
 * User: dlataill
 * Date: Jul 18, 2005
 * Time: 12:42:31 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import edit.services.db.hibernate.*;

import java.util.*;

import fission.utility.Util;
import billing.*;


public class CashBatchContract extends HibernateEntity
{
	private String batchRecordType;
    private Long cashBatchContractPK;
    private String batchID;
    private EDITDate creationDate;
    private String creationOperator;
    private EDITBigDecimal amount;
    private Integer totalBatchItems;
    private String accountingPendingIndicator;
    private String releaseIndicator;
    private String groupNumber;
    private EDITDate dueDate;
    private Long companyFK;

    private Set<Suspense> suspenses;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;
    
    public final static String RELEASE_INDICATOR_RELEASE = "R";
    public final static String RELEASE_INDICATOR_PENDING = "P";
    public final static String RELEASE_INDICATOR_COMPLETE = "C";
    public final static String RELEASE_INDICATOR_VOID = "V";

    public final static String ACCOUNTING_PENDING_INDICATOR_YES = "Y";
    public final static String ACCOUNTING_PENDING_INDICATOR_NO = "N";


    public CashBatchContract()
    {
        init();
    }

    private void init()
    {
        suspenses = new HashSet<Suspense>();
    }

    /**
     * Getter
     * @return  set of suspenses
     */
    public Set getSuspenses()
    {
        return suspenses;
    }

    /**
     * Setter
     * @param suspenses      set of suspenses
     */
    public void setSuspenses(Set suspenses)
    {
        this.suspenses = suspenses;
    }

    /**
     * Adds a Suspense to the set of children
     * @param suspense
     */
    public void addSuspense(Suspense suspense)
    {
        this.getSuspenses().add(suspense);

        suspense.setCashBatchContract(this);

        SessionHelper.saveOrUpdate(suspense, CashBatchContract.DATABASE);
    }

    /**
     * Removes a Suspense from the set of children
     * @param suspense
     */
    public void removeSuspense(Suspense suspense)
    {
        this.getSuspenses().remove(suspense);

        suspense.setCashBatchContract(null);

        SessionHelper.saveOrUpdate(suspense, CashBatchContract.DATABASE);
    }

    


    public String getBatchRecordType() {
		return batchRecordType;
	}

	public void setBatchRecordType(String batchRecordType) {
		this.batchRecordType = batchRecordType;
	}

	public Long getCashBatchContractPK()
    {
        return cashBatchContractPK;
    }

    public void setCashBatchContractPK(Long cashBatchContractPK)
    {
        this.cashBatchContractPK = cashBatchContractPK;
    }

    public String getBatchID()
    {
        return batchID;
    }

    public void setBatchID(String batchID)
    {
        this.batchID = batchID;
    }

    public EDITDate getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(EDITDate creationDate)
    {
        this.creationDate = creationDate;
    }

    public String getCreationOperator()
    {
        return creationOperator;
    }

    public void setCreationOperator(String creationOperator)
    {
        this.creationOperator = creationOperator;
    }

    public EDITBigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(EDITBigDecimal amount)
    {
        this.amount = amount;
    }

    public Integer getTotalBatchItems()
    {
        return totalBatchItems;
    }

    public void setTotalBatchItems(Integer totalBatchItems)
    {
        this.totalBatchItems = totalBatchItems;
    }

    public String getAccountingPendingIndicator()
    {
        return accountingPendingIndicator;
    }

    public void setAccountingPendingIndicator(String accountingPendingIndicator)
    {
        this.accountingPendingIndicator = accountingPendingIndicator;
    }

    public String getReleaseIndicator()
    {
        return releaseIndicator;
    }

    public void setReleaseIndicator(String releaseIndicator)
    {
        this.releaseIndicator = releaseIndicator;
    }

    public String getGroupNumber()
    {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber)
    {
        this.groupNumber = groupNumber;
    }

    public EDITDate getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(EDITDate dueDate)
    {
        this.dueDate = dueDate;
    }

    /**
     * Getter.
     * @return
     */
    public Long getCompanyFK()
    {
        return companyFK;
    }

    /**
     * Setter.
     * @param companyFK
     */
    public void setCompanyFK(Long companyFK)
    {
        this.companyFK = companyFK;
    }

    /**
     * Returns the suspense record count for this cash batch.
     * @return
     */
    public int getSuspenseCount()
    {
        String hql = "select count(*) from Suspense suspense where suspense.CashBatchContract = :cashBatchContract";

        Map params = new HashMap();

        params.put("cashBatchContract", this);

        return SessionHelper.executeHQLForCount(hql, params, CashBatchContract.DATABASE);
    }

    /**
     * Deletes the suspense record from collection.
     * @param suspense - The suspense record to be removed from the collection
     */
    public void deleteSuspense(Suspense suspense)
    {
        for (Iterator iterator = this.suspenses.iterator(); iterator.hasNext();)
        {
            Suspense suspFromCollection = (Suspense) iterator.next();

            if (suspense.getSuspensePK().longValue() == suspFromCollection.getSuspensePK().longValue())
            {
                iterator.remove();
            }
        }
    }

    /**
     * Calculates the remaining batch amount by summing the total of all of the Suspense's suspenseAmounts.
     *
     * @return  total amount remaining in the suspenses
     */
    public EDITBigDecimal calculateRemainingBatchAmount()
    {
        EDITBigDecimal remainingAmount = new EDITBigDecimal();

        Set<Suspense> suspenses = this.getSuspenses();

        if (suspenses != null)
        {
            for (Iterator iterator = suspenses.iterator(); iterator.hasNext();)
            {
                Suspense suspense = (Suspense) iterator.next();

                remainingAmount = remainingAmount.addEditBigDecimal(suspense.getSuspenseAmount());
            }
        }

        return remainingAmount;
    }

    /**
     * Increases the amount by the given suspenseAmount.  Amount is the total of all the child Suspense.suspenseAmounts.
     * This method is handy when adding suspenses to the CashBatchContract
     *
     * @param suspenseAmount
     */
    public void increaseAmount(EDITBigDecimal suspenseAmount)
    {
        this.amount = this.amount.addEditBigDecimal(suspenseAmount);
    }

    /**
     * Decreases the amount by the given suspenseAmount.  Amount is the total of all the child Suspense.suspenseAmounts.
     * This method is handy when removing suspenses from the CashBatchContract.
     *
     * @param suspenseAmount
     */
    public void decreaseAmount(EDITBigDecimal suspenseAmount)
    {
        this.amount = this.amount.subtractEditBigDecimal(suspenseAmount);
    }

    /**
     * Increases the totalBatchItems count by the amount given in increaseAmount
     *
     * @param increaseAmount
     */
    public void increaseTotalBatchItems(int increaseAmount)
    {
        this.totalBatchItems = this.totalBatchItems + increaseAmount;
    }

    /**
     * Decreases the totalBatchItems count by the amount given in decreaseAmount
     *
     * @param decreaseAmount
     */
    public void decreaseTotalBatchItems(int decreaseAmount)
    {
        this.totalBatchItems = this.totalBatchItems - decreaseAmount;
    }


    /**
     * Finder.
     * @return
     */
    public static final CashBatchContract[] findAll()
    {
        String hql = "select cbc from CashBatchContract cbc" +
                     " where cbc.ReleaseIndicator = :statusP" +
                     " or cbc.ReleaseIndicator = :statusR";

        Map params = new TreeMap();

        params.put("statusP", RELEASE_INDICATOR_PENDING);
        params.put("statusR", RELEASE_INDICATOR_RELEASE);

        List results = SessionHelper.executeHQL(hql, params, CashBatchContract.DATABASE);

        return (CashBatchContract[]) results.toArray(new CashBatchContract[results.size()]);
    }

    /**
     * Finder.
     * @return
     */
    public static final CashBatchContract[] findByParameters(String status,
                                                             String filterDate,
                                                             String amount,
                                                             String operator,
                                                             boolean cache)
    {
        String hql = null;
        Map params = new TreeMap();

        if (!status.equals(""))
        {
            if (status.equalsIgnoreCase("Pending"))
            {
                status = RELEASE_INDICATOR_PENDING;
            }
            else
            {
                status = RELEASE_INDICATOR_RELEASE;
            }

            hql = "select cbc from CashBatchContract cbc where cbc.ReleaseIndicator = :status";

            params.put("status", status);

        }
        else if (!filterDate.equalsIgnoreCase(""))
        {
            hql = "select cbc from CashBatchContract cbc where cbc.CreationDate = :creationDate" +
                         " and not cbc.ReleaseIndicator = :status";

            params.put("creationDate", new EDITDate(filterDate));
            params.put("status", RELEASE_INDICATOR_COMPLETE);

        }
        else if (Util.isANumber(amount) && new EDITBigDecimal(amount).isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
        {
            hql = "select cbc from CashBatchContract cbc where cbc.Amount = :amount" +
                         " and not cbc.ReleaseIndicator = :status";

            params.put("amount", new EDITBigDecimal(amount));
            params.put("status", RELEASE_INDICATOR_COMPLETE);

        }
        else
        {
            hql = "select cbc from CashBatchContract cbc where cbc.CreationOperator = :operator" +
                         " and not cbc.ReleaseIndicator = :status";

            params.put("operator", operator);
            params.put("status", RELEASE_INDICATOR_COMPLETE);

        }

            List results = SessionHelper.executeHQL(hql, params, CashBatchContract.DATABASE);

            return (CashBatchContract[]) results.toArray(new CashBatchContract[results.size()]);
    }

    /**
     * Finder.
     * @param batchId
     * @return
     */
    public static final CashBatchContract[] findByBatchId(String batchId)
    {
        String hql = "select cbc from CashBatchContract cbc where cbc.BatchID = :batchId";

        Map params = new TreeMap();

        params.put("batchId", batchId);

        List results = SessionHelper.executeHQL(hql, params, CashBatchContract.DATABASE);

        return (CashBatchContract[]) results.toArray(new CashBatchContract[results.size()]);
    }

    /**
     * Finder.
     * @param accountingPendingInd
     * @return
     */
    public static final CashBatchContract[] findByAccountingPendingIndicator(String accountingPendingInd)
    {
        String hql = "select cbc from CashBatchContract cbc where cbc.AccountingPendingIndicator = :accountingPendingInd";

        Map params = new TreeMap();

        params.put("accountingPendingInd", accountingPendingInd);

        List results = SessionHelper.executeHQL(hql, params, CashBatchContract.DATABASE);

        return (CashBatchContract[]) results.toArray(new CashBatchContract[results.size()]);
    }

    public static final CashBatchContract[] findReleasedByAccountingPendingIndicator(String accountingPendingInd)
    {
        String hql = "select cbc from CashBatchContract cbc where cbc.AccountingPendingIndicator = :accountingPendingInd" +
                     " and cbc.ReleaseIndicator = :releaseIndicator";

        Map params = new TreeMap();

        params.put("accountingPendingInd", accountingPendingInd);
        params.put("releaseIndicator", RELEASE_INDICATOR_RELEASE);

        List results = SessionHelper.executeHQL(hql, params, CashBatchContract.DATABASE);

        return (CashBatchContract[]) results.toArray(new CashBatchContract[results.size()]);
    }

    public static final List<CashBatchContract> findByReleasedIndicator(String releaseIndicator)
    {
        String hql = " select cbc from CashBatchContract cbc" +
                     " where cbc.ReleaseIndicator = :releaseIndicator";

        Map params = new HashMap();

        params.put("releaseIndicator", releaseIndicator);

        return SessionHelper.executeHQL(hql, params, CashBatchContract.DATABASE);

        //return (CashBatchContract[]) results.toArray(new CashBatchContract[results.size()]);
    }

    /**
     * Finder by PK.
     * @param cashBatchContractPK
     * @return
     */
    public static final CashBatchContract findByPK(Long cashBatchContractPK)
    {
        return (CashBatchContract) SessionHelper.get(CashBatchContract.class, cashBatchContractPK, CashBatchContract.DATABASE);
    }

    /**
     * Gets the BillGroup that corresponds to this CashBatchContract.  Uses the groupNumber to match against the Group
     * ContractGroup's number and the dueDate to match agains the BillGroup's dueDate.
     *
     * @return  BillGroup that goes to this CashBatchContract
     */
    public BillGroup getAssociatedBillGroup()
    {
        return BillGroup.findByDueDate_GroupNumber(this.getDueDate(), this.getGroupNumber());
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, CashBatchContract.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, CashBatchContract.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return CashBatchContract.DATABASE;
    }
}
