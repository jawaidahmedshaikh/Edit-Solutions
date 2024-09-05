/*
 * User: dlataill
 * Date: Jun 9, 2004
 * Time: 11:09:42 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.EDITMap;
import edit.common.vo.ChangeHistoryVO;
import edit.common.vo.DepositsVO;
import edit.common.vo.SuspenseVO;

import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SEGElement;
import edit.services.db.hibernate.SessionHelper;

import event.CashBatchContract;
import event.EDITTrx;
import event.Suspense;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;


public class Deposits extends HibernateEntity implements SEGElement
{
    public static final String DEPOSIT_TYPE_CASH = "Cash";
    public static final String DEPOSIT_TYPE_CASH_WITH_APP = "CashWithApp";
    public static final String DEPOSIT_TYPE_CONTRIBUTION = "Contribution";

    public static final String DEPOSIT_TYPE_QUALIFIED_ROLLOVER = "QualifiedRollover";
    public static final String DEPOSIT_TYPE_REPLACE_QUAL_ROLLOVER = "ReplaceQualRollover";

    private DepositsVO depositsVO;
    private Segment segment;
    private EDITTrx editTrx;
    private Suspense suspense;
    private CashBatchContract cashBatchContract;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public Deposits()
    {
        this.depositsVO = new DepositsVO();
    }

    public Deposits(DepositsVO depositsVO)
    {
        this.depositsVO = depositsVO;
    }

    public Suspense getSuspense()
    {
        return suspense;
    }

    public void setSuspense(Suspense suspense)
    {
        this.suspense = suspense;
    }

    public EDITTrx getEDITTrx()
    {
        return editTrx;
    }

    public void setEDITTrx(EDITTrx editTrx)
    {
        this.editTrx = editTrx;
    }

    public CashBatchContract getCashBatchContract()
    {
        return cashBatchContract;
    }

    public void setCashBatchContract(CashBatchContract cashBatchContract)
    {
        this.cashBatchContract = cashBatchContract;
    }

    public Long getSegmentFK()
    {
        return SessionHelper.getPKValue(depositsVO.getSegmentFK());
    }

    //-- long getSegmentFK()
    public void setSegmentFK(Long segmentFK)
    {
        depositsVO.setSegmentFK(SessionHelper.getPKValue(segmentFK));
    }

    //-- void setSegmentFK(long)

    /**
     * Getter.
     * @return
     */
    public Segment getSegment()
    {
        return segment;
    }

    /**
     * Setter.
     * @param segment
     */
    public void setSegment(Segment segment)
    {
        this.segment = segment;
    }

    public EDITBigDecimal getAmountReceived()
    {
        return SessionHelper.getEDITBigDecimal(depositsVO.getAmountReceived());
    }

    //-- java.math.BigDecimal getAmountReceived() 
    public EDITBigDecimal getAnticipatedAmount()
    {
        return SessionHelper.getEDITBigDecimal(depositsVO.getAnticipatedAmount());
    }

    //-- java.math.BigDecimal getAnticipatedAmount() 
    public Long getCashBatchContractFK()
    {
        return SessionHelper.getPKValue(depositsVO.getCashBatchContractFK());
    }

    //-- long getCashBatchContractFK() 
    public EDITBigDecimal getCostBasis()
    {
        return SessionHelper.getEDITBigDecimal(depositsVO.getCostBasis());
    }

    //-- java.math.BigDecimal getCostBasis() 
    public EDITDate getDateReceived()
    {
        return ((depositsVO.getDateReceived() != null) ? new EDITDate(depositsVO.getDateReceived()) : null);
    }

    //-- java.lang.String getDateReceived() 
    public String getDepositTypeCT()
    {
        return depositsVO.getDepositTypeCT();
    }

    //-- java.lang.String getDepositTypeCT() 
    public Long getDepositsPK()
    {
        return SessionHelper.getPKValue(depositsVO.getDepositsPK());
    }

    //-- long getDepositsPK() 
    public Long getEDITTrxFK()
    {
        return SessionHelper.getPKValue(depositsVO.getEDITTrxFK());
    }

    //-- long getEDITTrxFK() 
    public int getExchangeDuration()
    {
        return depositsVO.getExchangeDuration();
    }

    //-- int getExchangeDuration() 
    public int getExchangeIssueAge()
    {
        return depositsVO.getExchangeIssueAge();
    }

    //-- int getExchangeIssueAge() 
    public EDITDate getExchangePolicyEffectiveDate()
    {
        return ((depositsVO.getExchangePolicyEffectiveDate() != null) ? new EDITDate(depositsVO.getExchangePolicyEffectiveDate()) : null);
    }

    //-- java.lang.String getExchangePolicyEffectiveDate() 
    public String getOldCompany()
    {
        return depositsVO.getOldCompany();
    }

    //-- java.lang.String getOldCompany() 
    public String getOldPolicyNumber()
    {
        return depositsVO.getOldPolicyNumber();
    }

    //-- java.lang.String getOldPolicyNumber() 
    public String getPriorCompanyMECStatusCT()
    {
        return depositsVO.getPriorCompanyMECStatusCT();
    }

    //-- java.lang.String getPriorCompanyMECStatusCT() 
    public Long getSuspenseFK()
    {
        return SessionHelper.getPKValue(depositsVO.getSuspenseFK());
    }

    //-- long getSuspenseFK() 
    public int getTaxYear()
    {
        return depositsVO.getTaxYear();
    }

    //-- int getTaxYear() 
    public void setAmountReceived(EDITBigDecimal amountReceived)
    {
        depositsVO.setAmountReceived(SessionHelper.getEDITBigDecimal(amountReceived));
    }

    //-- void setAmountReceived(java.math.BigDecimal) 
    public void setAnticipatedAmount(EDITBigDecimal anticipatedAmount)
    {
        depositsVO.setAnticipatedAmount(SessionHelper.getEDITBigDecimal(anticipatedAmount));
    }

    //-- void setAnticipatedAmount(java.math.BigDecimal) 
    public void setCashBatchContractFK(Long cashBatchContractFK)
    {
        depositsVO.setCashBatchContractFK(SessionHelper.getPKValue(cashBatchContractFK));
    }

    //-- void setCashBatchContractFK(long) 
    public void setCostBasis(EDITBigDecimal costBasis)
    {
        depositsVO.setCostBasis(SessionHelper.getEDITBigDecimal(costBasis));
    }

    //-- void setCostBasis(java.math.BigDecimal) 
    public void setDateReceived(EDITDate dateReceived)
    {
        depositsVO.setDateReceived((dateReceived != null) ? dateReceived.getFormattedDate() : null);
    }

    //-- void setDateReceived(java.lang.String) 
    public void setDepositTypeCT(String depositTypeCT)
    {
        depositsVO.setDepositTypeCT(depositTypeCT);
    }

    //-- void setDepositTypeCT(java.lang.String) 
    public void setDepositsPK(Long depositsPK)
    {
        depositsVO.setDepositsPK(SessionHelper.getPKValue(depositsPK));
    }

    //-- void setDepositsPK(long) 
    public void setEDITTrxFK(Long EDITTrxFK)
    {
        depositsVO.setEDITTrxFK(SessionHelper.getPKValue(EDITTrxFK));
    }

    //-- void setEDITTrxFK(long) 
    public void setExchangeDuration(int exchangeDuration)
    {
        depositsVO.setExchangeDuration(exchangeDuration);
    }

    //-- void setExchangeDuration(int) 
    public void setExchangeIssueAge(int exchangeIssueAge)
    {
        depositsVO.setExchangeIssueAge(exchangeIssueAge);
    }

    //-- void setExchangeIssueAge(int) 
    public void setExchangePolicyEffectiveDate(EDITDate exchangePolicyEffectiveDate)
    {
        depositsVO.setExchangePolicyEffectiveDate((exchangePolicyEffectiveDate != null) ? exchangePolicyEffectiveDate.getFormattedDate() : null);
    }

    //-- void setExchangePolicyEffectiveDate(java.lang.String) 
    public void setOldCompany(String oldCompany)
    {
        depositsVO.setOldCompany(oldCompany);
    }

    //-- void setOldCompany(java.lang.String) 
    public void setOldPolicyNumber(String oldPolicyNumber)
    {
        depositsVO.setOldPolicyNumber(oldPolicyNumber);
    }

    //-- void setOldPolicyNumber(java.lang.String) 
    public void setPriorCompanyMECStatusCT(String priorCompanyMECStatusCT)
    {
        depositsVO.setPriorCompanyMECStatusCT(priorCompanyMECStatusCT);
    }

    //-- void setPriorCompanyMECStatusCT(java.lang.String) 
    public void setSuspenseFK(Long suspenseFK)
    {
        depositsVO.setSuspenseFK(SessionHelper.getPKValue(suspenseFK));
    }

    //-- void setSuspenseFK(long) 
    public void setTaxYear(int taxYear)
    {
        depositsVO.setTaxYear(taxYear);
    }

    public static DepositsVO buildDefaultDepositsVO(SuspenseVO suspenseVO, Long cashBatchContractPK, long segmentPK)
    {
        DepositsVO depositsVO = new DepositsVO();

        depositsVO.setSuspenseFK(suspenseVO.getSuspensePK());
        depositsVO.setDepositTypeCT(Deposits.DEPOSIT_TYPE_CASH);
        depositsVO.setSegmentFK(segmentPK);
        depositsVO.setAmountReceived(suspenseVO.getSuspenseAmount());
        depositsVO.setDateReceived(suspenseVO.getEffectiveDate());
        if (cashBatchContractPK != null)
        {
            depositsVO.setCashBatchContractFK(cashBatchContractPK.longValue());
        }

        return depositsVO;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Deposits.DATABASE;
    }

    public int delete() throws Exception
    {
        CRUD crud = null;

        int deleteCount = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            crud.deleteVOFromDB(DepositsVO.class, depositsVO.getDepositsPK());

            deleteCount = 1;
        }
        catch (Exception e)
        {
            throw new Exception(e);
        }
        finally
        {
            if (crud != null)
            {
                crud.close();
            }

            crud = null;
        }

        return deleteCount;
    }

    public long save()
    {
        CRUD crud = null;

        long depositsPK = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            depositsPK = crud.createOrUpdateVOInDB(depositsVO);
        }
        finally
        {
            if (crud != null)
            {
                crud.close();
            }

            crud = null;
        }

        return depositsPK;
    }
    
    /**
     * Saves the deposit and generates a Non Financial History
     * Record for the change.
     * @param operator
     * @return
     */
    public void save(String operator)
    {
        String beforeValue = getBeforeValueOfDepositTypeCT();
    
        try
        {
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

            createChangeHistory(beforeValue, operator);
            SessionHelper.saveOrUpdate(this, SessionHelper.EDITSOLUTIONS);

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
            
            System.out.println(e);
            
            e.printStackTrace();
            
            throw new RuntimeException(e);
        }
    }
    
    private String getBeforeValueOfDepositTypeCT()
    {
        String depositTypeCT = null;

        Session session = null;

        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

            String hql = " select deposits from Deposits deposits " +
                     " where deposits.DepositsPK = :depositsPK";

            EDITMap map = new EDITMap("depositsPK", getDepositsPK());

            List<Deposits> results = SessionHelper.executeHQL(session, hql, map, 0);

            depositTypeCT = results.get(0).getDepositTypeCT();
        }
        finally
        {
            if (session != null) session.close();
        }
        
        return depositTypeCT;
    }

    /**
     * Saves the deposit specified by the depositsPK with the new deposit type and generates a NF History
     * Record for the change.
     * @param depositsPK
     * @param depositType
     * @param operator
     * @return
     */
    public String save(Long depositsPK, String depositType, String operator)
    {
        String message = "";

        Deposits deposits = Deposits.findByPK(depositsPK);
        String beforeValue = deposits.getDepositTypeCT();
        String afterValue = depositType;
        
        deposits.setDepositTypeCT(depositType);

        try
        {
            SessionHelper.beginTransaction(Deposits.DATABASE);
            SessionHelper.saveOrUpdate(deposits, Deposits.DATABASE);
            SessionHelper.commitTransaction(Deposits.DATABASE);

            generateChangeHistory(beforeValue, afterValue, "DepositTypeCT", "Deposits",
                                  depositsPK.longValue(), deposits.getSegmentFK().longValue(), operator);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(Deposits.DATABASE);
            message = "Deposit Update Failed - " + e.getMessage();
        }

        return message;
    }

    public static Deposits[] findAllByContractNumber_AmountReceivedGTZero(String contractNumber)
    {
        String hql = "select d from Deposits d join d.Segment s where s.ContractNumber = :contractNumber" +
                     " and d.AmountReceived = :zeroAmount";

        Map params = new HashMap();

        params.put("contractNumber", contractNumber);
        params.put("zeroAmount", new EDITBigDecimal());

        List results = SessionHelper.executeHQL(hql, params, Deposits.DATABASE);

        return (Deposits[]) results.toArray(new Deposits[results.size()]);
    }

    public static Deposits findByPK(Long depositsPK)
    {
        return (Deposits) SessionHelper.get(Deposits.class, depositsPK, Deposits.DATABASE);
	}

    public static Deposits[] findBySuspenseFK(Long suspenseFK)
    {
        String hql = "select d from Deposits d where d.SuspenseFK = :suspenseFK ";

        Map params = new HashMap();

        params.put("suspenseFK", suspenseFK);

        List results = SessionHelper.executeHQL(hql, params, Deposits.DATABASE);

        return (Deposits[]) results.toArray(new Deposits[results.size()]);
    }

    public static Deposits[] findBySegmentFK(Long segmentFK)
    {
        String hql = "select d from Deposits d where d.SegmentFK = :segmentFK ";

        Map params = new HashMap();

        params.put("segmentFK", segmentFK);

        List results = SessionHelper.executeHQL(hql, params, Deposits.DATABASE);

        return (Deposits[]) results.toArray(new Deposits[results.size()]);
    }

    public static DepositsVO[] findBy_SegmentFK(long segmentFK)
    {
        return contract.dm.dao.DAOFactory.getDepositsDAO().findBySegmentFK(segmentFK);
    }

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getPostTEFRAAmount()
    {
        return SessionHelper.getEDITBigDecimal(depositsVO.getPostTEFRAAmount());
    }

    //-- java.math.BigDecimal getPostTEFRAAmount()

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getPostTEFRAGain()
    {
        return SessionHelper.getEDITBigDecimal(depositsVO.getPostTEFRAGain());
    }

    //-- java.math.BigDecimal getPostTEFRAGain()

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getPreTEFRAAmount()
    {
        return SessionHelper.getEDITBigDecimal(depositsVO.getPreTEFRAAmount());
    }

    //-- java.math.BigDecimal getPreTEFRAAmount()

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getPreTEFRAGain()
    {
        return SessionHelper.getEDITBigDecimal(depositsVO.getPreTEFRAGain());
    }

    //-- java.math.BigDecimal getPreTEFRAGain()

    /**
     * Setter
     * @param postTEFRAAmount
     */
    public void setPostTEFRAAmount(EDITBigDecimal postTEFRAAmount)
    {
        depositsVO.setPostTEFRAAmount(SessionHelper.getEDITBigDecimal(postTEFRAAmount));
    }

    //-- void setPostTEFRAAmount(java.math.BigDecimal)

    /**
     * Setter
     * @param postTEFRAGain
     */
    public void setPostTEFRAGain(EDITBigDecimal postTEFRAGain)
    {
        depositsVO.setPostTEFRAGain(SessionHelper.getEDITBigDecimal(postTEFRAGain));
    }

    //-- void setPostTEFRAGain(java.math.BigDecimal)

    /**
     * Setter
     * @param preTEFRAAmount
     */
    public void setPreTEFRAAmount(EDITBigDecimal preTEFRAAmount)
    {
        depositsVO.setPreTEFRAAmount(SessionHelper.getEDITBigDecimal(preTEFRAAmount));
    }

    //-- void setPreTEFRAAmount(java.math.BigDecimal)

    /**
     * Setter
     * @param preTEFRAGain
     */
    public void setPreTEFRAGain(EDITBigDecimal preTEFRAGain)
    {
        depositsVO.setPreTEFRAGain(SessionHelper.getEDITBigDecimal(preTEFRAGain));
    }

    //-- void setPreTEFRAGain(java.math.BigDecimal)

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getExchangeLoanAmount()
    {
        return SessionHelper.getEDITBigDecimal(depositsVO.getExchangeLoanAmount());
    } //-- java.math.BigDecimal getExchangeLoanAmount()

    /**
     * Setter
     * @param exchangeLoanAmount
     */
    public void setExchangeLoanAmount(EDITBigDecimal exchangeLoanAmount)
    {
        depositsVO.setExchangeLoanAmount(SessionHelper.getEDITBigDecimal(exchangeLoanAmount));
    } //-- void setExchangeLoanAmount(java.math.BigDecimal)


	public DepositsVO getDepositsVO()
    {
        return depositsVO;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Deposits.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, Deposits.DATABASE);
    }

    private void generateChangeHistory(String beforeValue, String afterValue,
                                       String fieldName, String tableName,
                                       long tableKey, long segmentFK,
                                       String operator) throws Exception
    {
        ChangeHistoryVO changeHistoryVO = new ChangeHistoryVO();
        changeHistoryVO.setChangeHistoryPK(0);
        changeHistoryVO.setParentFK(segmentFK);
        changeHistoryVO.setModifiedRecordFK(tableKey);
        changeHistoryVO.setTableName(tableName);

        String date = new EDITDate().getFormattedDate();
        changeHistoryVO.setEffectiveDate(date);
        changeHistoryVO.setProcessDate(date);
        changeHistoryVO.setFieldName(fieldName);
        changeHistoryVO.setBeforeValue(beforeValue);
        changeHistoryVO.setAfterValue(afterValue);
        changeHistoryVO.setOperator(operator);
        changeHistoryVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());

        changeHistoryVO.setNonFinancialTypeCT("DepositChange");

        contract.business.Contract contractComponent = new contract.component.ContractComponent();

        try
        {
            contractComponent.saveChangeHistory(changeHistoryVO);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Generates change history record for deposits modification done online.
     * @param beforeValue
     * @param operator
     */
    private void createChangeHistory(String beforeValue, 
                                     String operator)
    {
        String afterValue = this.getDepositTypeCT();
    
        if (beforeValue != null && !beforeValue.equalsIgnoreCase(afterValue))
        {
            ChangeHistory changeHistory = new ChangeHistory();
    
            changeHistory.setParentFK(this.getSegmentFK());
            changeHistory.setModifiedRecordFK(this.getDepositsPK());
            changeHistory.setTableName("Deposits");
    
            changeHistory.setEffectiveDate(new EDITDate());
            changeHistory.setProcessDate(new EDITDate());
            changeHistory.setFieldName("DepositsTypeCT");
            changeHistory.setBeforeValue(beforeValue);
            changeHistory.setAfterValue(afterValue);
            changeHistory.setOperator(operator);
            changeHistory.setMaintDateTime(new EDITDateTime());
    
            changeHistory.setNonFinancialTypeCT("DepositChange");
    
            changeHistory.hSave();
        }
    }

	public static Deposits findByEDITTrxFK(Long editTrxFK)        
	{

		String hql = "select d from Deposits d where d.EDITTrxFK = :editTrxFK ";

        Map params = new HashMap();

        params.put("editTrxFK", editTrxFK);

        List results = SessionHelper.executeHQL(hql, params, Deposits.DATABASE);

        Deposits deposit = null;
        if (!results.isEmpty())
        {
            deposit = (Deposits)results.get(0);
        }
        
        return deposit;
    }
}

