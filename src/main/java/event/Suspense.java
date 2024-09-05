/*
 * User: gfrosti
 * Date: Oct 3, 2003
 * Time: 2:12:18 PM
*
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import event.dm.dao.*;

import java.util.*;

import contract.*;

import client.*;
import engine.ProductStructure;
import engine.Company;
import engine.common.*;
import security.Operator;
import staging.IStaging;
import staging.StagingContext;
import staging.FinancialActivity;
import org.hibernate.*;

public class Suspense extends HibernateEntity implements CRUDEntity, IStaging
{
    public static final String DIRECTIONCT_REMOVE = "Remove";
    public static final String DIRECTIONCT_APPLY = "Apply";

    public static final String PREMIUMTYPECT_ISSUE = "Issue";
    public static final String PREMIUMTYPECT_CASH = "Cash";
    public static final String PREMIUMTYPECT_LOAN_REPAYMENT = "LoanRepayment";

    public static final String DEPOSITTYPECT_CASH = "Cash";

    public static final String SUSPENSETYPE_REDEMPTION = "Redemption";
    public static final String SUSPENSETYPE_BATCH = "Batch";
    public static final String SUSPENSETYPE_CONTRACT = "Contract";

    public static final String ACCOUNTING_PENDING_IND_YES = "Y";
    public static final String MAINTENANCE_IND_M = "M";
    public static final String MAINTENANCE_IND_R = "R";
    public static final String MAINTENANCE_IND_U = "U";
    public static final String STATUS_IND_N = "N";

    public static final String REASON_CODE_NOCONTRACT = "NoContract";
    public static final String REASON_CODE_TERMINATED = "TerminatedContract";
    public static final String REASON_CODE_PENDINGISSUE =  "PendingIssue";
    public static final String REMOVAL_REASON_REJECTEDBATCH = "RejectedBatch";
    public static final String REMOVAL_REASON_TRANSFER = "Transfer";
    public static final String REMOVAL_REASON_REFUND = "Refund";

    public static final String CONTRACT_PLACED_FROM_DELETE ="Delete";
    public static final String CONTRACT_PLACED_FROM_REJECTEDBATCH = "RejBatch";
    public static final String CONTRACT_PLACED_FROM_INDIVIDUAL = "Individual";
    public static final String CONTRACT_PLACED_FROM_BATCH = "Batch";
    public static final String CONTRACT_PLACED_FROM_NBREFUND = "NBRefund";

    private Set deposits;
    private SuspenseVO suspenseVO;

	private CashBatchContract cashBatchContract;
    private ClientDetail clientDetail;
    private ClientAddress clientAddress;
    private Preference preference;

    private Set inSuspenses;
    private Set outSuspenses;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public Suspense()
    {
        this.suspenseVO = new SuspenseVO();

        this.inSuspenses = new HashSet();

        this.outSuspenses = new HashSet();
    }

    public Suspense(long suspensePK) throws Exception
    {
        this(DAOFactory.getSuspenseDAO().findBySuspensePK(suspensePK)[0]);
    }

    public Suspense(SuspenseVO suspenseVO)
    {
        this.suspenseVO = suspenseVO;
    }

    /**
     * Getter
     * @return  set of inSuspenses
     */
    public Set getInSuspenses()
    {
        return inSuspenses;
    }

    /**
     * Setter
     * @param inSuspenses      set of inSuspenses
     */
    public void setInSuspenses(Set inSuspenses)
    {
        this.inSuspenses = inSuspenses;
    }

    /**
     * Adds a InSuspense to the set of children
     * @param inSuspense
     */
    public void addInSuspense(InSuspense inSuspense)
    {
        this.getInSuspenses().add(inSuspense);

        inSuspense.setSuspense(this);

        SessionHelper.saveOrUpdate(inSuspense, Suspense.DATABASE);
    }

    /**
     * Removes a InSuspense from the set of children
     * "@param inSuspense
     */
    public void removeInSuspense(InSuspense inSuspense)
    {
        this.getInSuspenses().remove(inSuspense);

        inSuspense.setSuspense(null);

        SessionHelper.saveOrUpdate(inSuspense, Suspense.DATABASE);
    }

   /**
     * Get a single InSuspense
     * @return
     */
    public InSuspense getInSuspense()
    {
        InSuspense inSuspense =getInSuspenses().isEmpty() ? null : (InSuspense) getInSuspenses().iterator().next();

        return inSuspense;
    }

    /**
     * Getter
     * @return  set of outSuspenses
     */
    public Set getOutSuspenses()
    {
        return outSuspenses;
    }

    /**
     * Setter
     * @param outSuspenses      set of outSuspenses
     */
    public void setOutSuspenses(Set outSuspenses)
    {
        this.outSuspenses = outSuspenses;
    }

    /**
     * Adds a OutSuspense to the set of children
     * @param outSuspense
     */
    public void addOutSuspense(OutSuspense outSuspense)
    {
        this.getOutSuspenses().add(outSuspense);

        outSuspense.setSuspense(this);

        SessionHelper.saveOrUpdate(outSuspense, Suspense.DATABASE);
    }

    /**
     * Removes a OutSuspense from the set of children
     * @param outSuspense
     */
    public void removeOutSuspense(OutSuspense outSuspense)
	{
        this.getOutSuspenses().remove(outSuspense);

        outSuspense.setSuspense(null);
        SessionHelper.saveOrUpdate(outSuspense, Suspense.DATABASE);
     }

   /**
     * Get a single OutSuspense
     * @return
     */
    public OutSuspense getOutSuspense()
    {
        OutSuspense outSuspense =getOutSuspenses().isEmpty() ? null : (OutSuspense) getOutSuspenses().iterator().next();

        return outSuspense;
    }

    /**
     * Getter.
     * @return
     */
    public CashBatchContract getCashBatchContract()
    {
        return cashBatchContract;
    }

    /**
     * Setter.
     * @param cashBatchContract
     */
    public void setCashBatchContract(CashBatchContract cashBatchContract)
    {
        this.cashBatchContract = cashBatchContract;
    }

    /**
     * Getter
     * @return
     */
    public ClientDetail getClientDetail()
    {
        return clientDetail;
    }

    /**
     * Setter
     * @param clientDetail
     */
    public void setClientDetail(ClientDetail clientDetail)
    {
        this.clientDetail = clientDetail;
    }

    /**
     * Getter
     * @return
     */
    public ClientAddress getClientAddress()
    {
        return clientAddress;
    }

   /**
     * Setter
     * @param clientAddress
     */
    public void setClientAddress(ClientAddress clientAddress)
    {
        this.clientAddress = clientAddress;
    }
  
    /**
     * Getter
     * @return
     */
    public Preference getPreference()
    {
        return preference;
    }

    /**
     * Setter
     * @param preference
     */
    public void setPreference(Preference preference)
    {
        this.preference = preference;
    }

    /**
     * Getter.
     * @return
     */
    public Long getSuspensePK()
    {
        return new Long(suspenseVO.getSuspensePK());
    }

    /**
     * Setter.
     * @param suspensePK
     */
    public void setSuspensePK(Long suspensePK)
    {
        suspenseVO.setSuspensePK(suspensePK.longValue());
    }

    /**
     * Getter
     * @return  set of deposits
     */
    public Set getDeposits()
    {
        return deposits;
    }

    /**
     * Setter
     * @param deposits      set of deposits
     */
    public void setDeposits(Set deposits)
    {
        this.deposits = deposits;
    }

    /**
     * Adds a Deposits to the set of children
     * @param deposits
     */
    public void addDeposits(Deposits deposits)
    {
        this.getDeposits().add(deposits);

        deposits.setSuspense(this);

        SessionHelper.saveOrUpdate(deposits, Suspense.DATABASE);
    }

    /**
     * Removes a Deposits from the set of children
     * @param deposits
     */
    public void removeDeposits(Deposits deposits)
    {
        this.getDeposits().remove(deposits);

        deposits.setSuspense(null);

        SessionHelper.saveOrUpdate(deposits, Suspense.DATABASE);
    }


    public void delete() throws Exception
    {
        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (suspenseVO.getAccountingPendingInd().equals("Y") && suspenseVO.getMaintenanceInd().equals("M"))
            {
                crud.deleteVOFromDBRecursively(SuspenseVO.class, suspenseVO.getSuspensePK());
            }
            else
            {
                suspenseVO.setOriginalAmount(suspenseVO.getSuspenseAmount());

                suspenseVO.setSuspenseAmount(new EDITBigDecimal().getBigDecimal());
                suspenseVO.setAccountingPendingInd("Y");
                suspenseVO.setMaintenanceInd("R");
                suspenseVO.setProcessDate(new EDITDate().getFormattedDate());

                crud.createOrUpdateVOInDB(suspenseVO);
            }
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (crud != null)
            {
                crud.close();
            }
        }
    }

    public void save()
    {
        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            crud.createOrUpdateVOInDB(suspenseVO);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null)
            {
                crud.close();
            }
        }
    }

    public void save(CRUD crud)
     {
         try
         {
             crud.createOrUpdateVOInDB(suspenseVO);
         }
         catch (Exception e)
         {
             System.out.println(e);
             e.printStackTrace();
             throw new RuntimeException(e);
         }
         finally
         {
             if (crud == null)
             {
                 crud.close();
             }
         }
     }


    public SuspenseVO getAsVO()
    {
        return suspenseVO;
    }

    /**
     * Finder by PK.
     * @param suspensePK
     * @return
     */
    public static final Suspense findByPK(long suspensePK)
    {
        Suspense suspense = null;

        SuspenseVO[] suspenseVOs = new SuspenseDAO().findBySuspensePK(suspensePK);

        if (suspenseVOs != null)
        {
            suspense = new Suspense(suspenseVOs[0]);
        }

        return suspense;
    }

    /**
     * Getter.
     * @return
     */
    public long getPK()
    {
        return suspenseVO.getSuspensePK();
    }

    /**
     * Getter.
     * @return
     */
    public String getUserDefNumber()
    {
        return suspenseVO.getUserDefNumber();
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return ((suspenseVO.getEffectiveDate() != null) ? new EDITDate(suspenseVO.getEffectiveDate()) : null);
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getSuspenseAmount()
    {
        return new EDITBigDecimal(suspenseVO.getSuspenseAmount());
    }

    /**
     * Getter.
     * @return
     */
    public EDITDateTime getMaintDateTime()
    {
        return ((suspenseVO.getMaintDateTime() != null) ? new EDITDateTime(suspenseVO.getMaintDateTime()) : null);
    }

    /**
     * Finder.
     * @param contractNumber
     * @return
     */
    public static final Suspense[] findBy_UserDefNumber(String contractNumber)
    {
        SuspenseVO[] suspenseVOs = new SuspenseDAO().findByUserDefNumber(contractNumber);

        return (Suspense[]) CRUDEntityImpl.mapVOToEntity(suspenseVOs, Suspense.class);
    }

    /**
     * @see edit.services.db.CRUDEntity#getVO()
     * @return
     */
    public VOObject getVO()
    {
        return suspenseVO;
    }

    /**
     * @see edit.services.db.CRUDEntity#setVO(edit.common.vo.VOObject)
     * @return
     */
    public void setVO(VOObject voObject)
    {
        this.suspenseVO = (SuspenseVO) voObject;
    }

    /**
     * @see edit.services.db.CRUDEntity#isNew()
     * @return
     */
    public boolean isNew()
    {
        return new CRUDEntityImpl().isNew(this);
    }

    /**
     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
     * @return
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return new CRUDEntityImpl().cloneCRUDEntity(this);
    }

    /**
     * Getter
     * @return
     */
    public String getOriginalContractNumber()
    {
        return suspenseVO.getOriginalContractNumber();
    }

    /**
     * Getter.
     * @return
     */
    public Long getFilteredFundFK()
    {
        return SessionHelper.getPKValue(suspenseVO.getFilteredFundFK());
    }

    /**
     * Getter.
     * @return
     */
    public Long getCashBatchContractFK()
    {
        return SessionHelper.getPKValue(suspenseVO.getCashBatchContractFK());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGrossAmount()
    {
        return new EDITBigDecimal(suspenseVO.getGrossAmount());
    }

    /**
     * Getter.
     * @return
     */
    public String getCheckNumber()
    {
        return suspenseVO.getCheckNumber();
    }

    /**
     * Getter.
     * @return
     */
    public String getPlannedIndCT()
    {
        return suspenseVO.getPlannedIndCT();
    }

    /**
     * Getter.
     * @return
     */
    public String getLastName()
    {
        return suspenseVO.getLastName();
    }

    /**
     * Getter.
     * @return
     */
    public String getFirstName()
    {
        return suspenseVO.getFirstName();
    }

    /**
     * Getter.
     * @return
     */
    public String getExchangeCompany()
    {
        return suspenseVO.getExchangeCompany();
    }

    /**
     * Getter.
     * @return
     */
    public String getExchangePolicy()
    {
        return suspenseVO.getExchangePolicy();
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCostBasis()
    {
        return new EDITBigDecimal(suspenseVO.getCostBasis());
    }

    /**
     * Setter
     * @param suspenseAmount
     */
    public void setSuspenseAmount(EDITBigDecimal suspenseAmount)
    {
        suspenseVO.setSuspenseAmount(suspenseAmount.getBigDecimal());
    }

    /**
     * Getter.
     * @return
     */
    public String getContractPlacedFrom()
    {
        return suspenseVO.getContractPlacedFrom();
    } //-- java.lang.String getContractPlacedFrom()

    /**
     * Getter.
     * @return
     */
    public String getDirectionCT()
    {
        return suspenseVO.getDirectionCT();
    } //-- java.lang.String getDirectionCT()

    /**
     * Getter.
     * @return
     */
    public String getMaintenanceInd()
    {
        return suspenseVO.getMaintenanceInd();
    } //-- java.lang.String getMaintenanceInd()

    /**
     * Getter.
     * @return
     */
    public String getMemoCode()
    {
        return suspenseVO.getMemoCode();
    } //-- java.lang.String getMemoCode()

    /**
     * Getter.
     * @return
     */
    public String getOperator()
    {
        return suspenseVO.getOperator();
    } //-- java.lang.String getOperator()

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getOriginalAmount()
    {
        return new EDITBigDecimal(suspenseVO.getOriginalAmount());
    } //-- java.math.BigDecimal getOriginalAmount()

    /**
     * Getter.
     * @return
     */
    public String getOriginalMemoCode()
    {
        return suspenseVO.getOriginalMemoCode();
    } //-- java.lang.String getOriginalMemoCode()

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPendingSuspenseAmount()
    {
        return new EDITBigDecimal(suspenseVO.getPendingSuspenseAmount());
    } //-- java.math.BigDecimal getPendingSuspenseAmount()


    /**
     * Getter.
     * @return
     */
    public String getAccountingPendingInd()
    {
        return suspenseVO.getAccountingPendingInd();
    }

    /**
     * Getter.
     * @return
     */
    public String getPremiumTypeCT()
    {
        return suspenseVO.getPremiumTypeCT();
    } //-- java.lang.String getPremiumTypeCT()

    /**
     * Getter.
     * @return
     */
    public String getDepositTypeCT()
    {
        return suspenseVO.getDepositTypeCT();
    }

    /**
     * Getter.
     * @return
     */
    public String getReasonCodeCT()
    {
        return suspenseVO.getReasonCodeCT();
    }

    /**
     * Getter.
     * @return
     */
    public String getCorporateName()
    {
        return suspenseVO.getCorporateName();
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getRefundAmount()
    {
        return new EDITBigDecimal(suspenseVO.getRefundAmount());
    }

    /**
     * Getter.
     * @return
     */
    public String getRemovalReason()
    {
        return suspenseVO.getRemovalReason();
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getDateAppliedRemoved()
    {
        return((suspenseVO.getDateAppliedRemoved() != null) ? new EDITDate(suspenseVO.getDateAppliedRemoved()) : null);
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getProcessDate()
    {
        return ((suspenseVO.getProcessDate() != null) ? new EDITDate(suspenseVO.getProcessDate()) : null);
    } //-- java.lang.String getProcessDate()

    /**
     * Getter.
     * @return
     */
    public String getStatus()
    {
        return suspenseVO.getStatus();
    } //-- java.lang.String getStatus()

    /**
     * Getter.
     * @return
     */
    public String getSuspenseType()
    {
        return suspenseVO.getSuspenseType();
    } //-- java.lang.String getSuspenseType()

    public int getTaxYear()
    {
        return suspenseVO.getTaxYear();
    } //-- int getTaxYear()


    /**
      * Getter
      * @return
      */
     public Long getClientDetailFK()
     {
         return SessionHelper.getPKValue(suspenseVO.getClientDetailFK());
     } //-- long getClientDetailFK()

    /**
      * Getter
      * @return
      */
     public Long getClientAddressFK()
     {
         return SessionHelper.getPKValue(suspenseVO.getClientAddressFK());
     } //-- long getClientAddressFK()

    /**
      * Getter
      * @return
      */
     public Long getBankAccountInformationFK()
     {
         return SessionHelper.getPKValue(suspenseVO.getBankAccountInformationFK());
     } //-- long getBankAccountInformationFK()

    /**
      * Getter
      * @return
      */
     public Long getPreferenceFK()
     {
         return SessionHelper.getPKValue(suspenseVO.getPreferenceFK());
     } //-- long getPreferenceFK()


    /**
     * Getter
     * @return
     */
    public String getAddressTypeCT()
    {
        return suspenseVO.getAddressTypeCT();
    } //-- java.lang.String getAddressTypeCT()

    /**
     * Getter
     * @return
     */
    public String getDisbursementSourceCT()
    {
        return suspenseVO.getDisbursementSourceCT();
    } //-- java.lang.String getDisbursementSourceCT()

    /**
     * Getter.
     * @return
     */
    public Long getCompanyFK()
    {
        return SessionHelper.getPKValue(suspenseVO.getCompanyFK());
    }

    /**
     * Setter
     * @param disbursementSourceCT
     */
    public void setDisbursementSourceCT(String disbursementSourceCT)
    {
        suspenseVO.setDisbursementSourceCT(disbursementSourceCT);
    } //-- void setDisbursementSourceCT(java.lang.String)

    /**
     * Setter
     * @param clientDetailFK
     */
    public void setClientDetailFK(Long clientDetailFK)
    {
        suspenseVO.setClientDetailFK(SessionHelper.getPKValue(clientDetailFK));
    } //-- void setClientDetailFK(long)

    /**
     * Setter
     * @param clientAddressFK
     */
    public void setClientAddressFK(Long clientAddressFK)
    {
        suspenseVO.setClientAddressFK(SessionHelper.getPKValue(clientAddressFK));
    } //-- void setClientAddressFK(long)

    /**
     * Setter
     * @param bankAccountInformationFK
     */
    public void setBankAccountInformationFK(Long bankAccountInformationFK)
    {
        suspenseVO.setBankAccountInformationFK(SessionHelper.getPKValue(bankAccountInformationFK));
    } //-- void setBankAccountInformationFK(long)

    /**
     * Setter
     * @param preferenceFK
     */
    public void setPreferenceFK(Long preferenceFK)
    {
        suspenseVO.setPreferenceFK(SessionHelper.getPKValue(preferenceFK));
    } //-- void setPreferenceFK(long)

    /**
     * Setter
     * @param addressTypeCT
     */
    public void setAddressTypeCT(String addressTypeCT)
    {
        suspenseVO.setAddressTypeCT(addressTypeCT);
    } //-- void setAddressTypeCT(java.lang.String)


    /**
     * Setter.
     * @param accountingPendingInd
     */
    public void setAccountingPendingInd(String accountingPendingInd)
    {
        suspenseVO.setAccountingPendingInd(accountingPendingInd);
    } //-- void setAccountingPendingInd(java.lang.String)

    /**
     * Setter.
     * @param contractPlacedFrom
     */
    public void setContractPlacedFrom(String contractPlacedFrom)
    {
        suspenseVO.setContractPlacedFrom(contractPlacedFrom);
    } //-- void setContractPlacedFrom(java.lang.String)

    /**
     * Setter.
     * @param directionCT
     */
    public void setDirectionCT(String directionCT)
    {
        suspenseVO.setDirectionCT(directionCT);
    } //-- void setDirectionCT(java.lang.String)

    /**
     * Setter.
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        suspenseVO.setEffectiveDate((effectiveDate != null) ? effectiveDate.getFormattedDate() : null);
    } //-- void setEffectiveDate(java.lang.String)

    /**
     * Setter.
     * @param maintDateTime
     */
    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        suspenseVO.setMaintDateTime((maintDateTime != null) ? maintDateTime.getFormattedDateTime() : null);
    } //-- void setMaintDateTime(java.lang.String)

    /**
     * Setter.
     * @param maintenanceInd
     */
    public void setMaintenanceInd(String maintenanceInd)
    {
        suspenseVO.setMaintenanceInd(maintenanceInd);
    } //-- void setMaintenanceInd(java.lang.String)

    /**
     * Setter.
     * @param memoCode
     */
    public void setMemoCode(String memoCode)
    {
        suspenseVO.setMemoCode(memoCode);
    } //-- void setMemoCode(java.lang.String)

    /**
     * Setter.
     * @param operator
     */
    public void setOperator(String operator)
    {
        suspenseVO.setOperator(operator);
    } //-- void setOperator(java.lang.String)

    /**
     * Setter.
     * @param originalAmount
     */
    public void setOriginalAmount(EDITBigDecimal originalAmount)
    {
        suspenseVO.setOriginalAmount(originalAmount.getBigDecimal());
    } //-- void setOriginalAmount(java.math.BigDecimal)

    /**
     * Setter.
     * @param originalContractNumber
     */
    public void setOriginalContractNumber(String originalContractNumber)
    {
        suspenseVO.setOriginalContractNumber(originalContractNumber);
    } //-- void setOriginalContractNumber(java.lang.String)

    /**
     * Setter.
     * @param originalMemoCode
     */
    public void setOriginalMemoCode(String originalMemoCode)
    {
        suspenseVO.setOriginalMemoCode(originalMemoCode);
    } //-- void setOriginalMemoCode(java.lang.String)

    /**
     * Setter.
     * @param pendingSuspenseAmount
     */
    public void setPendingSuspenseAmount(EDITBigDecimal pendingSuspenseAmount)
    {
        suspenseVO.setPendingSuspenseAmount(pendingSuspenseAmount.getBigDecimal());
    } //-- void setPendingSuspenseAmount(java.math.BigDecimal)

    /**
     * Setter.
     * @param premiumTypeCT
     */
    public void setPremiumTypeCT(String premiumTypeCT)
    {
        suspenseVO.setPremiumTypeCT(premiumTypeCT);
    } //-- void setPremiumTypeCT(java.lang.String)

    /**
     * Setter.
     * @param depositTypeCT
     */
    public void setDepositTypeCT(String depositTypeCT)
    {
        suspenseVO.setDepositTypeCT(depositTypeCT);
    }

    /**
     * Setter.
     * @param reasonCodeCT
     */
    public void setReasonCodeCT(String reasonCodeCT)
    {
        suspenseVO.setReasonCodeCT(reasonCodeCT);
    }

    /**
     * Setter.
     * @param corporateName
     */
    public void setCorporateName(String corporateName)
    {
        suspenseVO.setCorporateName(corporateName);
    }

    /**
     * Setter.
     * @param refundAmount
     */
    public void setRefundAmount(EDITBigDecimal refundAmount)
    {
        suspenseVO.setRefundAmount(SessionHelper.getEDITBigDecimal(refundAmount));
    }

    /**
     * Setter.
     * @param removalReasonCT
     */
    public void setRemovalReason(String removalReason)
    {
        suspenseVO.setRemovalReason(removalReason);
    }

    /**
     * Setter.
     * @param dateAppliedVoided
     */
    public void setDateAppliedRemoved(EDITDate dateAppliedRemoved)
    {
        suspenseVO.setDateAppliedRemoved((dateAppliedRemoved != null) ? dateAppliedRemoved.getFormattedDate() : null);
    }

    /**
     * Setter.
     * @param processDate
     */
    public void setProcessDate(EDITDate processDate)
    {
        suspenseVO.setProcessDate((processDate != null) ? processDate.getFormattedDate() : null);
    } //-- void setProcessDate(java.lang.String)

    /**
     * Setter.
     * @param status
     */
    public void set_Status(String status)
    {
        suspenseVO.setStatus(status);
    } //-- void setStatus(java.lang.String)

    public void setStatus(String status)
    {
        suspenseVO.setStatus(status);
    }

    /**
     * Setter.
     * @param suspenseType
     */
    public void setSuspenseType(String suspenseType)
    {
        suspenseVO.setSuspenseType(suspenseType);
    } //-- void setSuspenseType(java.lang.String)

    /**
     * Setter.
     * @param taxYear
     */
    public void setTaxYear(int taxYear)
    {
        suspenseVO.setTaxYear(taxYear);
    } //-- void setTaxYear(int)

    /**
     * Setter.
     * @param userDefNumber
     */
    public void setUserDefNumber(String userDefNumber)
    {
        suspenseVO.setUserDefNumber(userDefNumber);
    } //-- void setUserDefNumber(java.lang.String)

    /**
     * Setter
     * @param companyFK
     */
    public void setCompanyFK(Long companyFK)
    {
        suspenseVO.setCompanyFK(SessionHelper.getPKValue(companyFK));
    }

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getPostTEFRAAmount()
    {
        return SessionHelper.getEDITBigDecimal(suspenseVO.getPostTEFRAAmount());
    } //-- java.math.BigDecimal getPostTEFRAAmount()

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getPostTEFRAGain()
    {
        return SessionHelper.getEDITBigDecimal(suspenseVO.getPostTEFRAGain());
    } //-- java.math.BigDecimal getPostTEFRAGain()

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getPreTEFRAAmount()
    {
        return SessionHelper.getEDITBigDecimal(suspenseVO.getPreTEFRAAmount());
    } //-- java.math.BigDecimal getPreTEFRAAmount()

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getPreTEFRAGain()
    {
        return SessionHelper.getEDITBigDecimal(suspenseVO.getPreTEFRAGain());
    } //-- java.math.BigDecimal getPreTEFRAGain()

    /**
     * Setter
     * @param postTEFRAAmount
     */
    public void setPostTEFRAAmount(EDITBigDecimal postTEFRAAmount)
    {
        suspenseVO.setPostTEFRAAmount(SessionHelper.getEDITBigDecimal(postTEFRAAmount));
    } //-- void setPostTEFRAAmount(java.math.BigDecimal)

    /**
     * Setter
     * @param postTEFRAGain
     */
    public void setPostTEFRAGain(EDITBigDecimal postTEFRAGain)
    {
        suspenseVO.setPostTEFRAGain(SessionHelper.getEDITBigDecimal(postTEFRAGain));
    } //-- void setPostTEFRAGain(java.math.BigDecimal)

    /**
     * Setter
     * @param preTEFRAAmount
     */
    public void setPreTEFRAAmount(EDITBigDecimal preTEFRAAmount)
    {
        suspenseVO.setPreTEFRAAmount(SessionHelper.getEDITBigDecimal(preTEFRAAmount));
    } //-- void setPreTEFRAAmount(java.math.BigDecimal)

    /**
     * Setter
     * @param preTEFRAGain
     */
    public void setPreTEFRAGain(EDITBigDecimal preTEFRAGain)
    {
        suspenseVO.setPreTEFRAGain(SessionHelper.getEDITBigDecimal(preTEFRAGain));

    } //-- void setPreTEFRAGain(java.math.BigDecimal)

    /**
     * Setter.
     * @param filteredFundFK
     */
    public void setFilteredFundFK(Long filteredFundFK)
    {
        suspenseVO.setFilteredFundFK(SessionHelper.getPKValue(filteredFundFK));
    }

    /**
     * Setter.
     * @param cashBatchContractFK
     */
    public void setCashBatchContractFK(Long cashBatchContractFK)
    {
        suspenseVO.setCashBatchContractFK(SessionHelper.getPKValue(cashBatchContractFK));
    }

    /**
     * Setter.
     * @param grossAmount
     */
    public void setGrossAmount(EDITBigDecimal grossAmount)
    {
        suspenseVO.setGrossAmount(grossAmount.getBigDecimal());
    }

    /**
     * Setter.
     * @param checkNumber
     */
    public void setCheckNumber(String checkNumber)
    {
        suspenseVO.setCheckNumber(checkNumber);
    }

    /**
     * Setter.
     * @param plannedIndCT
     */
    public void setPlannedIndCT(String plannedIndCT)
    {
        suspenseVO.setPlannedIndCT(plannedIndCT);
    }

    /**
     * Setter.
     * @param lastName
     */
    public void setLastName(String lastName)
    {
        suspenseVO.setLastName(lastName);
    }

    /**
     * Setter.
     * @param firstName
     */
    public void setFirstName(String firstName)
    {
        suspenseVO.setFirstName(firstName);
    }

    /**
     * Setter.
     * @param exchangeCompany
     */
    public void setExchangeCompany(String exchangeCompany)
    {
        suspenseVO.setExchangeCompany(exchangeCompany);
    }

    /**
     * Setter.
     * @param exchangePolicy
     */
    public void setExchangePolicy(String exchangePolicy)
    {
        suspenseVO.setExchangePolicy(exchangePolicy);
    }

    /**
     * Setter.
     * @param costBasis
     */
    public void setCostBasis(EDITBigDecimal costBasis)
    {
        suspenseVO.setCostBasis(costBasis.getBigDecimal());
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Suspense.DATABASE;
    }

    /**
     * Suspense Refund Default Values
     */
    public void setSuspenseRefundDefaults()
    {
        this.setDirectionCT(Suspense.DIRECTIONCT_REMOVE);
        this.setRemovalReason(Suspense.REMOVAL_REASON_REFUND);
        this.setEffectiveDate(new EDITDate());
        this.setAccountingPendingInd(Suspense.ACCOUNTING_PENDING_IND_YES);
        this.setProcessDate(new EDITDate());
        this.setStatus(Suspense.STATUS_IND_N);
        this.setMaintenanceInd(Suspense.MAINTENANCE_IND_M);

        this.setMaintDateTime(new EDITDateTime());
        this.setSuspenseType(Suspense.SUSPENSETYPE_CONTRACT);
        this.setContractPlacedFrom("PremRef");
    }

    public Suspense createApplySuspense(EDITBigDecimal amount, String contractNumber, String operator, String reasonCode, Long companyFK)
    {
        Suspense applySuspense = new Suspense();
        applySuspense.setSuspenseAmount(amount);
        applySuspense.setOriginalAmount(amount);
        applySuspense.setPendingSuspenseAmount(amount);
        applySuspense.setUserDefNumber(contractNumber.toUpperCase());
        applySuspense.setOriginalContractNumber(contractNumber.toUpperCase());
        applySuspense.setOperator(operator);
        applySuspense.setReasonCodeCT(reasonCode);

        applySuspense.setDirectionCT(Suspense.DIRECTIONCT_APPLY);
        applySuspense.setRemovalReason(Suspense.REMOVAL_REASON_TRANSFER);
        applySuspense.setEffectiveDate(new EDITDate());
        applySuspense.setProcessDate(new EDITDate());
        applySuspense.setStatus(Suspense.STATUS_IND_N);
        applySuspense.setPremiumTypeCT(Suspense.PREMIUMTYPECT_CASH);
        applySuspense.setDepositTypeCT(Suspense.DEPOSITTYPECT_CASH);
        applySuspense.setAccountingPendingInd(Suspense.ACCOUNTING_PENDING_IND_YES);
        applySuspense.setMaintenanceInd(Suspense.MAINTENANCE_IND_M);
        applySuspense.setMaintDateTime(new EDITDateTime());
        applySuspense.setSuspenseType(Suspense.SUSPENSETYPE_CONTRACT);
        applySuspense.setContractPlacedFrom(Suspense.CONTRACT_PLACED_FROM_INDIVIDUAL);
        applySuspense.setCompanyFK(companyFK);

      return applySuspense;
    }

    public void setVoidDefaults(Suspense suspense)
    {
        this.setReasonCodeCT(suspense.getReasonCodeCT());
        this.setMaintenanceInd("V");
        this.setAccountingPendingInd("Y");
        this.setDateAppliedRemoved(new EDITDate());
        this.setRemovalReason("Void");
        this.setProcessDate(new EDITDate());
        this.setContractPlacedFrom("Delete");
        this.setSuspenseAmount(new EDITBigDecimal());
    }

    /**
     * Retrieves all Suspense records for a given cash batch contract
     * @param cashBatchContract
     * @return
     */
    public static Suspense[] findAllByCashBatchContract(CashBatchContract cashBatchContract)
    {
        String hql = "select suspense from Suspense suspense where suspense.CashBatchContract = :cashBatchContract";

        Map params = new HashMap();

        params.put("cashBatchContract", cashBatchContract);

        List results = SessionHelper.executeHQL(hql, params, Suspense.DATABASE);

        return (Suspense[]) results.toArray(new Suspense[results.size()]);
    }

    /**
     * Retrieves all Suspense records for a given cash batch contract
     * @return
     */
    public static Suspense[] findAll(Operator operator, ProductStructure[] productStructures)
    {
        String hql = "select suspense from Suspense suspense where suspense.MaintenanceInd = :maintenanceInd" +
                     " order by suspense.EffectiveDate ASC";

        Map params = new HashMap();

        params.put("maintenanceInd", "M");

        List results = SessionHelper.executeHQL(hql, params, Suspense.DATABASE);

        Suspense[] suspenses = (Suspense[]) results.toArray(new Suspense[results.size()]);

        suspenses = filterSuspenseByAuthorization(operator, productStructures, suspenses);

        return suspenses;
    }

    /**
     * Retrieves all Suspense Records for the given user def number (equates to the contract number)
     * where the suspense.premiumTypeCT = 'Issue'
     * @param userDefNumber
     * @return
     */
    public static Suspense[] findByUserDefNumberForIssue(String userDefNumber)
    {
        String hql = " select suspense from Suspense suspense where upper(suspense.UserDefNumber) = :userDefNumber" +
                     " and suspense.PremiumTypeCT = :premiumType" +
                     " and suspense.SuspenseAmount > :zero" +
                     " and suspense.MaintenanceInd = :maintenanceInd";

        Map params = new HashMap();

        params.put("userDefNumber", userDefNumber.toUpperCase());
        params.put("premiumType", "Issue");
        params.put("zero", new EDITBigDecimal("0.00"));
        params.put("maintenanceInd", "M");

        List results = SessionHelper.executeHQL(hql, params, Suspense.DATABASE);

        return (Suspense[]) results.toArray(new Suspense[results.size()]);
    }

    /**
     * Retrieves all Suspense Records for the given user def number (equates to the contract number)
     * where the suspense.premiumTypeCT = 'Issue'
     * @param userDefNumber
     * @return
     */
    public static Suspense[] findByUserDefNumber(String userDefNumber, Operator operator, ProductStructure[] productStructures)
    {
        String hql = "select suspense from Suspense suspense where upper(suspense.UserDefNumber) like upper(:userDefNumber)" +
                     " and suspense.MaintenanceInd = :maintenanceInd" +
                     " order by suspense.EffectiveDate ASC";

        Map params = new HashMap();

        params.put("userDefNumber", userDefNumber);
        params.put("maintenanceInd", "M");

        List results = SessionHelper.executeHQL(hql, params, Suspense.DATABASE);

        Suspense[] suspenses = (Suspense[]) results.toArray(new Suspense[results.size()]);

        suspenses = filterSuspenseByAuthorization(operator, productStructures, suspenses);

        return suspenses;
    }

    /**
     * Retrieves all suspense records for the given user def number (equates to the contract number)
     * where the SuspenseAmount > 0 and the PendingSuspenseAmount = 0;
     * @param userDefNumber
     * @return
     */
    public static Suspense[] findByUserDefNumber_And_SuspenseAmountGTZero(String userDefNumber)
    {
        String hql = "select suspense from Suspense suspense where upper(suspense.UserDefNumber) like upper(:userDefNumber)" +
                     " and suspense.SuspenseAmount > :zero" +
                     " and suspense.PendingSuspenseAmount = :zero" +
                     " and suspense.DirectionCT = :direction" +
                     " and suspense.MaintenanceInd = :maintenanceInd" +
                     " order by suspense.EffectiveDate";

        Map params = new HashMap();

        params.put("userDefNumber", userDefNumber);
        params.put("zero", new EDITBigDecimal());
        params.put("direction", "Apply");
        params.put("maintenanceInd", "M");

        List results = SessionHelper.executeHQL(hql, params, Suspense.DATABASE);

        return (Suspense[]) results.toArray(new Suspense[results.size()]);
    }

    public static Suspense[] findApplyByCashBatchContract_PendingAmountEQZero_SuspenseAmountGTZero(CashBatchContract cashBatchContract)
    {
        String hql = "select suspense from Suspense suspense " +
                     " where suspense.CashBatchContract = :cashBatchContract" +
                     " and suspense.PendingSuspenseAmount = :zero" +
                     " and suspense.SuspenseAmount > :zero" +
                     " and suspense.DirectionCT = :directionCT";

        Map params = new HashMap();

        params.put("cashBatchContract", cashBatchContract);
        params.put("zero", new EDITBigDecimal());
        params.put("directionCT", Suspense.DIRECTIONCT_APPLY);

        List results = SessionHelper.executeHQL(hql, params, Suspense.DATABASE);

        return (Suspense[]) results.toArray(new Suspense[results.size()]);
    }

    /**
     * Finder by PK.
     * @param suspensePK
     * @return
     */
    public static Suspense findByPK(Long suspensePK)
    {
        return (Suspense) SessionHelper.get(Suspense.class, suspensePK, Suspense.DATABASE);
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Suspense.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, Suspense.DATABASE);
    }

    public static Suspense[] filterSuspenseByAuthorization(Operator operator,
                                                           ProductStructure[] productStructures,
                                                           Suspense[] suspenses)
    {
        boolean viewAllSuspense = false;
        List companiesAllowed = new ArrayList();

        List suspensesAllowed = new ArrayList();

        if (productStructures != null && productStructures.length > 0)
        {
            Long securityProductStructurePK = ProductStructure.checkForSecurityStructure(productStructures);
            companiesAllowed = ProductStructure.checkForAuthorizedCompanies(productStructures);

            if (operator != null)
            {
                if (securityProductStructurePK > 0L)
                {
                    viewAllSuspense = operator.checkViewAllAuthorization(securityProductStructurePK, "Suspense");
                }
            }
            else
            {
                viewAllSuspense = true;
            }
        }

        for (int i = 0; i < suspenses.length; i++)
        {
            Company company = null;

            if (suspenses[i].getCompanyFK() != null)
            {
                company = Company.findByPK(suspenses[i].getCompanyFK());
            }
            else
            {
                if (suspenses[i].getUserDefNumber() != null)
                {
                    Segment segment = Segment.findByContractNumber(suspenses[i].getUserDefNumber());
                    if (segment != null)
                    {
                        company = Company.findByProductStructurePK(segment.getProductStructureFK());
                    }
                }
            }

            String companyName = "";

            if (company != null)
            {
                companyName = company.getCompanyName();
            }

            if (companiesAllowed.contains(companyName) || viewAllSuspense)
            {
                suspensesAllowed.add(suspenses[i]);
            }
        }

        suspenses = (Suspense[]) suspensesAllowed.toArray(new Suspense[suspensesAllowed.size()]);

        return suspenses;
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        FinancialActivity financialActivity = new FinancialActivity();
        financialActivity.setEffectiveDate(this.getEffectiveDate());
        financialActivity.setTaxYear(this.getTaxYear());
        financialActivity.setTrxAmount(this.getSuspenseAmount());
        financialActivity.setMaintDateTime(this.getMaintDateTime());
        financialActivity.setOperator(this.getOperator());
        financialActivity.setProcessDate(new EDITDateTime(this.getProcessDate() + " " + EDITDateTime.DEFAULT_MIN_TIME));
        financialActivity.setGrossAmount(this.getSuspenseAmount());
        financialActivity.setNetAmount(this.getSuspenseAmount());
        financialActivity.setCheckAmount(this.getSuspenseAmount());

        financialActivity.setSegmentBase(stagingContext.getCurrentSegmentBase());
        stagingContext.getCurrentSegmentBase().addFinancialActivity(financialActivity);

        SessionHelper.saveOrUpdate(financialActivity, database);

        return stagingContext;
    }

    /**
     * Full termination transactions of 'NT' or 'FS' must update suspense before the trx processes.
     * @param contractNumber
     */
   public EDITBigDecimal updatePendingSuspenseAmount(String contractNumber)
   {
       Suspense[] suspenses = Suspense.findByUserDefNumber_PendingAmount(contractNumber);

       EDITBigDecimal totalRefundAmount = new EDITBigDecimal("0");
       if (suspenses != null)
       {
           SessionHelper.beginTransaction(Suspense.DATABASE);
           for (int i = 0; i < suspenses.length; i++)
           {
               Suspense suspense = suspenses[i];
               EDITBigDecimal suspenseAmount = suspense.getSuspenseAmount();
               suspense.setPendingSuspenseAmount(suspenseAmount);
               suspense.setMaintenanceInd("A");
               totalRefundAmount = totalRefundAmount.addEditBigDecimal(suspenseAmount);
               suspense.hSave();
           }

           SessionHelper.commitTransaction(Suspense.DATABASE);
       }

       return totalRefundAmount;
   }

    /**
     * For full termination transactions of 'NT' or 'FS', update suspenseAmount after the script processing
     * CPO trx uses the same update for a different reason.
     * @param contractNumber
     * @param crud
     */
    public void updateSuspenseAmount_PendingSuspenseAmount(String contractNumber, String transactionType, CRUD crud)
    {
        try
        {
            SuspenseVO[] suspenseVOs = null;
            if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_CLAIM_PAYOUT))
            {
                suspenseVOs = new FastDAO().findByUserDefNumber_PendingSuspenseAmountEQZero(contractNumber,  crud);
            }
            else
            {
                suspenseVOs = new FastDAO().findByUserDefNumber_PendingSuspenseAmount(contractNumber,  crud);
            }

            if (suspenseVOs != null)
            {
                for (int i = 0; i < suspenseVOs.length; i++)
                {
                    SuspenseVO suspenseVO = suspenseVOs[i];
                    suspenseVO.setSuspenseAmount(new EDITBigDecimal("0").getBigDecimal());
                    suspenseVO.setPendingSuspenseAmount(new EDITBigDecimal("0").getBigDecimal());
                    this.suspenseVO = suspenseVO;
                    this.save(crud);
                }
            }
        }
        catch (Exception e)
        {
          System.out.println(e);

          e.printStackTrace();
          new RuntimeException(e);
        }
    }

    /**
     * Retrieves all Suspense Records for the given user def number (equates to the contract number)
     * where the suspense.PendingSuspenseAmount = 0.
     * @param contractNumber
     * @return
     */
    public static Suspense[] findByUserDefNumber_PendingAmount(String contractNumber)
    {
        String hql = "select suspense from Suspense suspense" +
                     " where suspense.UserDefNumber = :contractNumber" +
                     " and suspense.PendingSuspenseAmount = 0" +
                     " and suspense.SuspenseAmount > 0 " +
                     " and suspense.DirectionCT = 'Apply' " +
                     " and suspense.MaintenanceInd != 'V' ";
            
        Map params = new HashMap();

        params.put("contractNumber", contractNumber);

        List results = SessionHelper.executeHQL(hql, params, Suspense.DATABASE);

        Suspense[] suspenses = null;
        if (!results.isEmpty())
        {
            suspenses = (Suspense[]) results.toArray(new Suspense[results.size()]);
        }

        return suspenses;
    }

    /**
     * A convenvience method for the "other" findSuspenseFilterRows method where the specified filterPeriod will determine
     * the ultimate start/stop dates.
     *
     * @param segmentPK
     * @param filterPeriod      One of a set of values defined by the CodeTableDef of "FILTERPERIOD".
     * @return
     * @see #findSuspenseFilterRows(String, String, edit.common.EDITDate, edit.common.EDITDate)
     */
    public static Suspense[] findSuspenseFilterRows(String contractNumber, String operator, String filterPeriod, String reasonCode)
    {
        EDITDate startDate = null;

        EDITDate stopDate = null;

        if (filterPeriod.equalsIgnoreCase("AllPeriods"))
        {
            startDate = new EDITDate(EDITDate.DEFAULT_MIN_DATE);

            stopDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
        }

        else if (filterPeriod.equalsIgnoreCase("PriorWeek"))
        {
            startDate = new EDITDate().subtractDays(7);

            stopDate = new EDITDate();
        }

        else if (filterPeriod.equalsIgnoreCase("PriorMonth"))
        {
            EDITDate currentDate = new EDITDate().subtractMonths(1);

            startDate = new EDITDate(currentDate.getFormattedYear(), currentDate.getFormattedMonth(), "01");

            stopDate = startDate.addMonths(1).subtractDays(1);
        }

        return findSuspenseFilterRows(contractNumber, operator, startDate, stopDate, reasonCode);
    }

    /**
     * SegmentPK, StartDate, and StopDate are always expected. This leaves TransactionTypeCT and ExcludeUndo which yield
     * four fundamental variations of the query. Assuming SegmentPK, StartDate, StopDate are supplied, then:
     * Case 1: TransactionType == null AND ExcludeUndo = false
     * Case 2: TransactionType == null AND ExcludeUndo = true
     * Case 3: TransactionpType != null AND ExcludeUndo = false
     * Case 4: TransactionType != null AND ExcludeUndo = true
     *
     * @param segmentPK   required
     * @param startDate   required
     * @param stopDate    required
     * @param excludeUndo true if Undo EDITTrxs should be excluded
     * @transactionTypeCT null or a valid EDITTrx.TransactionTypeCT
     */
    public static Suspense[] findSuspenseFilterRows(String contractNumber, String operator, EDITDate startDate, EDITDate stopDate, String reasonCode)
    {
        Suspense[] suspenseFilterRows = null;

        Map params = new HashMap();
        params.put("startDate", startDate);
        params.put("stopDate", stopDate);
        params.put("maintenanceInd", "M");

        // Case 1 - Get by dates only
        if (contractNumber == null && operator == null && reasonCode == null)
        {
            String hqlEDITTrxHistory = buildHQLSuspense1();
            suspenseFilterRows = executeHQL(hqlEDITTrxHistory, params);
        }

        // Case 2 - Get for Operator only
        else if (operator != null && reasonCode == null)
        {
            params.put("operator", operator);
            String hqlEDITTrxHistory = buildHQLSuspense2();
            suspenseFilterRows = executeHQL(hqlEDITTrxHistory, params);
        }

        // Case 3  Get for useDefNumber only
        else if (contractNumber != null)
        {
                params.put("contractNumber", contractNumber);
                String hqlEDITTrxHistory = buildHQLSuspense3();
                suspenseFilterRows = executeHQL(hqlEDITTrxHistory, params);
        }

        // Case 4 Get for ReasonCode only
        else if (operator == null && reasonCode != null)
        {
            params.put("reasonCode", reasonCode);
            String hqlEDITTrxHistory = buildHQLSuspense4();
            suspenseFilterRows = executeHQL(hqlEDITTrxHistory, params);
        }

        // Case 5 Get for Operator and ReasonCode
        else if (reasonCode != null && operator != null)
        {
                params.put("reasonCode", reasonCode);
                params.put("operator", operator);
                String hqlEDITTrxHistory = buildHQLSuspense5();
                suspenseFilterRows = executeHQL(hqlEDITTrxHistory, params);
        }

        else
        {
            String message = "Suspense Received Unexpected Query Parameters: [contractNumber = " + contractNumber + "] [operator = " + operator + "] [startDate = " + startDate + "] [stopDate = " + stopDate + "]";

            throw new RuntimeException(message);
        }

        return suspenseFilterRows;
    }

    /**
      * Includes all Suspense for specified start and stop date.
      *
      * @return
      */
     private static String buildHQLSuspense1()
     {
         String hqlSuspense = " from Suspense suspense" +
                 " where suspense.EffectiveDate between :startDate and :stopDate" +
                 " and suspense.MaintenanceInd = :maintenanceInd" +
                 " order by suspense.EffectiveDate ASC";

         return hqlSuspense;
     }

    /**
      * Includes all Suspense for specified operator, start and stop date.
      *
      * @return
      */
     private static String buildHQLSuspense2()
     {
         String hqlSuspense = " from Suspense suspense" +
                 " where suspense.Operator = :operator" +
                 " and suspense.EffectiveDate between :startDate and :stopDate" +
                 " and suspense.MaintenanceInd = :maintenanceInd" +
                 " order by suspense.EffectiveDate ASC";

         return hqlSuspense;
     }

    /**
      * Includes all Suspense for specified contract number, start and stop date.
      *
      * @return
      */
     private static String buildHQLSuspense3()
     {
         String hqlSuspense = " from Suspense suspense" +
                 " where upper(suspense.UserDefNumber) like upper(:contractNumber)" +
                 " and suspense.EffectiveDate between :startDate and :stopDate" +
                 " and suspense.MaintenanceInd = :maintenanceInd" +
                 " order by suspense.EffectiveDate ASC";

         return hqlSuspense;
     }

    /**
      * Includes all Suspense for specified reason.
      *
      * @return
      */
     private static String buildHQLSuspense4()
     {
         String hqlSuspense = " from Suspense suspense" +
                 " where suspense.ReasonCodeCT = :reasonCode" +
                 " and suspense.EffectiveDate between :startDate and :stopDate" +
                 " and suspense.MaintenanceInd = :maintenanceInd"  +
                 " order by suspense.EffectiveDate ASC";

         return hqlSuspense;
     }

    /**
      * Includes all Suspense for specified reason code, operator, start and stop date.
      *
      * @return
      */
     private static String buildHQLSuspense5()
     {
         String hqlSuspense = " from Suspense suspense" +
                 " where suspense.ReasonCodeCT = :reasonCode" +
                 " and suspense.Operator = :operator" +
                 " and suspense.EffectiveDate between :startDate and :stopDate" +
                 " and suspense.MaintenanceInd = :maintenanceInd"  +
                 " order by suspense.EffectiveDate ASC";

         return hqlSuspense;
     }

   /**
     * Executes the specified hql.
     *
     * @param hqlEDITTrxHistory
     * @param hqlChangeHistory
     * @return
     */
    private static Suspense[] executeHQL(String hql, Map params)
    {
        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        Suspense[] suspenseRows = null;

        if (!results.isEmpty())
        {
            suspenseRows = (Suspense[])results.toArray(new Suspense[results.size()]);
        }

        for (int i = 0; i < results.size(); i++)
        {
            Object o = results.get(i);

            SessionHelper.evict(o, SessionHelper.EDITSOLUTIONS);
        }

        return suspenseRows;
    }

    public static Suspense[] findApplyByContractNumber(String contractNumber)
    {
        String hql = "select suspense from Suspense suspense" +
                     " where suspense.UserDefNumber = :contractNumber" +
                     " and suspense.PendingSuspenseAmount = 0" +
                     " and suspense.SuspenseAmount > 0" +
                     " and suspense.DirectionCT = 'Apply'";


        Map params = new HashMap();

        params.put("contractNumber", contractNumber);

        Suspense[] suspenses = null;
     
        Session session = null;

        try
        {
          session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

          List results = SessionHelper.executeHQL(session, hql, params, 0);

          if (!results.isEmpty())
          {
              suspenses = (Suspense[]) results.toArray(new Suspense[results.size()]);
          }
        }
        finally
        {
          if (session != null) session.close();
        }

        return suspenses;
    }

    public static Suspense[] findSuspenseHistory(String contractNumber)
    {
        String hql = "select suspense from Suspense suspense" +
                     " where upper(suspense.UserDefNumber) like upper(:userDefNumber)" +
                     " and suspense.SuspenseAmount = :zero" +
                     " and suspense.DirectionCT = :direction" +
                     " order by suspense.EffectiveDate desc";

        Map params = new HashMap();

        params.put("userDefNumber", contractNumber);
        params.put("zero", new EDITBigDecimal("0"));
        params.put("direction", "Apply");

        List results = SessionHelper.executeHQL(hql, params, Suspense.DATABASE);

        Suspense[] suspenses = null;
        if (!results.isEmpty())
        {
            suspenses = (Suspense[]) results.toArray(new Suspense[results.size()]);
        }

        return suspenses;
    }

    public static EDITBigDecimal sumActiveApply( )
    {
        Object summedAmount = null;
        EDITBigDecimal summedApplyAmount = new EDITBigDecimal();

        String hql = " select sum(SuspenseAmount) from Suspense suspense" +
                     " where suspense.DirectionCT = :direction" +
                     " and (not suspense.MaintenanceInd = :RmaintenanceInd"  +
                     " and not suspense.MaintenanceInd = :VmaintenanceInd)";

        Map params = new HashMap();

        params.put("direction", "Apply");
        params.put("RmaintenanceInd", "R");
        params.put("VmaintenanceInd", "V");


        List results = SessionHelper.executeHQL(hql, params, Suspense.DATABASE);
        if (!results.isEmpty())
        {
           summedAmount = (results.get(0));
            if (summedAmount != null)
            {
                summedApplyAmount = new EDITBigDecimal(summedAmount.toString());
            }
        }

        return summedApplyAmount;
    }

    public static EDITBigDecimal sumAppliesProcessed()
    {
        Object summedAmount = null;

        String hql = " select sum(SuspenseAmount) from Suspense suspense" +
                     " where suspense.DirectionCT = :direction" +
                     " and (suspense.MaintenanceInd = :maintenanceInd"  +
                     " or suspense.RemovalReason = :removalReason)";

        Map params = new HashMap();

        params.put("direction", "Apply");
        params.put("maintenanceInd", "R");
        params.put("removalReason", "Void");

        List results = SessionHelper.executeHQL(hql, params, Suspense.DATABASE);
        EDITBigDecimal summedApplyRemovals = new EDITBigDecimal();
        if (!results.isEmpty())
        {
            summedAmount = (results.get(0));
            if (summedAmount != null)
            {
                summedApplyRemovals = new EDITBigDecimal(summedAmount.toString());
            }
        }

        return summedApplyRemovals;
    }

    public static EDITBigDecimal sumRemovals()
    {
        Object summedAmount = null;

        String hql = " select sum(SuspenseAmount) from Suspense suspense" +
                     " where suspense.DirectionCT = :direction" +
                     " and suspense.ContractPlacedFrom = :contractPlacedFrom"  +
                     " and (suspense.RemovalReason = :rejRemovalReason" +
                     " or suspense.RemovalReason = :refRemovalReason)";

        Map params = new HashMap();

        params.put("direction", DIRECTIONCT_REMOVE);
        params.put("contractPlacedFrom", Suspense.CONTRACT_PLACED_FROM_DELETE);
        params.put("rejRemovalReason", Suspense.REMOVAL_REASON_REJECTEDBATCH);
        params.put("refRemovalReason", Suspense.REMOVAL_REASON_REFUND);

        List results = SessionHelper.executeHQL(hql, params, Suspense.DATABASE);
        EDITBigDecimal summedApplyRemovals = new EDITBigDecimal();
        if (!results.isEmpty())
        {
            summedAmount = (results.get(0));
            if (summedAmount != null)
            {
                summedApplyRemovals = new EDITBigDecimal(summedAmount.toString());
            }
        }

        return summedApplyRemovals;
    }

    public static Suspense findNSFEntry(String contractNumber, EDITBigDecimal suspenseAmount)
    {
        String hql = "select suspense from Suspense suspense" +
                     " where suspense.UserDefNumber = :contractNumber" +
                     " and suspense.SuspenseAmount = :suspenseAmount";


        Map params = new HashMap();

        params.put("contractNumber", contractNumber);
        params.put("suspenseAmount", suspenseAmount);

        List results = SessionHelper.executeHQL(hql, params, Suspense.DATABASE);

        Suspense suspense = null;
        if (!results.isEmpty())
        {
            suspense = (Suspense) results.get(0);
        }

        return suspense;        
    }

    public static Suspense[] findContractSuspenseForBankEFT(Long productStructureFK)
    {

        String hql = "select suspense from Suspense suspense" +
                      " join fetch suspense.InSuspenses inSuspense" +
                      " join fetch inSuspense.EDITTrxHistory editTrxHistory" +
                      " join fetch editTrxHistory.EDITTrx editTrx" +
                      " join fetch editTrx.ClientSetup clientSetup" +
                      " join fetch clientSetup.ContractSetup contractSetup" +
                      " join fetch contractSetup.Segment segment" +
                      " join fetch segment.ContractClients contractClient" +
                      " join fetch contractClient.ClientRole clientRole" +
                      " join fetch clientRole.ClientDetail clientDetail" +
                      " join fetch clientDetail.Preferences preference" +
                      " where suspense.UserDefNumber = segment.ContractNumber" +
                      " and suspense.SuspenseAmount != :suspenseAmount" +
                      " and (suspense.DirectionCT = 'apply' or suspense.DirectionCT = 'remove')" +
                      " and (maintenanceInd != 'd' and maintenanceInd != 'r' and maintenanceInd != 'v')" +
                      " and preference.DisbursementSourceCT = :disbursementSource" +
                      " and preference.PreferenceTypeCT = :preferenceType" +
                      " and clientRole.RoleTypeCT = :ownerRole" +
                      " and segment.ProductStructureFK = :productStructureFK";

        Map params = new HashMap();

        params.put("suspenseAmount", new EDITBigDecimal("0"));
        params.put("disbursementSource", "eft");
        params.put("preferenceType", "disbursement");
        params.put("ownerRole", "OWN");
        params.put("productStructureFK", productStructureFK);

        List results = SessionHelper.executeHQL(hql, params, Suspense.DATABASE);

        Suspense[] suspenses = null;
        if (!results.isEmpty())
        {
            suspenses = (Suspense[]) results.toArray(new Suspense[results.size()]);
        }

        return suspenses;
    }

    public static Suspense[] findCheckSuspenseForBankEFT(Long companyFK)
    {
        String hql = "select suspense from Suspense suspense" +
                     " join fetch suspense.InSuspenses  insuspense" +
                     " join fetch insuspense.EDITTrxHistory editTrxHistory" +
                     " join fetch editTrxHistory.CommissionHistories commissionHistory" +
                     " join fetch commissionHistory.PlacedAgent placedAgent" +
                     " join fetch placedAgent.ClientRole clientRole" +
                     " join fetch clientRole.ClientDetail clientDetail" +
                     " join fetch clientDetail.Preferences preference" +
                     " join fetch clientRole.Agent agent" +
                     " where suspense.UserDefNumber = clientRole.ReferenceID" +
                     " and clientRole.RoleTypeCT = 'agent'" +
                     " and suspense.SuspenseAmount != :suspenseAmount" +
                     " and (suspense.DirectionCT = 'apply' or suspense.DirectionCT = 'remove')" +
                     " and (maintenanceInd != 'd' and maintenanceInd != 'r' and maintenanceInd != 'v')" +
                     " and clientRole.RoleTypeCT = :agentRole" +
                     " and preference.DisbursementSourceCT = :disbursementSource" +
                     " and preference.PreferenceTypeCT = :preferenceType" +
                     " and agent.CompanyFK = :companyFK";


        Map params = new HashMap();

        params.put("suspenseAmount", new EDITBigDecimal("0"));
        params.put("agentRole", "agent");
        params.put("disbursementSource", "eft");
        params.put("preferenceType", "disbursement");
        params.put("companyFK", companyFK);

        List results = SessionHelper.executeHQL(hql, params, Suspense.DATABASE);

        Suspense[] suspenses = null;
        if (!results.isEmpty())
        {
            suspenses = (Suspense[]) results.toArray(new Suspense[results.size()]);
        }

        return suspenses;
    }
}
