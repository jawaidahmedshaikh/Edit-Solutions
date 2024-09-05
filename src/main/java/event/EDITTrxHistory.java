/*
 * User: gfrosti
 * Date: Feb 12, 2005
 * Time: 2:58:10 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import contract.Segment;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import event.common.TransactionPriorityCache;

import event.dm.dao.*;

import java.util.*;

import org.hibernate.Session;
import reinsurance.ReinsuranceHistory;


public class EDITTrxHistory extends HibernateEntity implements CRUDEntity
{
    public static final String CORRESPONDENCETYPECT_CONFIRM = "Confirm";
    public static final String ACCOUNTINGPENDINGSTATUS_YES = "Y";
    public static final String REALTIMEIND_YES = "Y";
    private CRUDEntityImpl crudEntityImpl;
    private EDITTrxHistoryVO editTrxHistoryVO;
    private Set<FinancialHistory> financialHistories;
    private EDITTrx editTrx;
    private Set<ChargeHistory> chargeHistories;
    private Set<BucketHistory> bucketHistories;
    private Set<WithholdingHistory> withholdingHistories;
    private Set<InSuspense> inSuspenses;
    private Set<CommissionHistory> commissionHistories;
    private Set<InvestmentHistory> investmentHistories;
    private Set<SegmentHistory> segmentHistories;
    private CommissionHistory commissionHistory;
    private Set<CommissionablePremiumHistory> commissionablePremiumHistories;
    private Set<ReinsuranceHistory> reinsuranceHistories;
    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a EDITTrxHistory entity with a default EDITTrxHistoryVO.
     */
    public EDITTrxHistory()
    {
        this.chargeHistories = new HashSet<ChargeHistory>();

        this.investmentHistories = new HashSet<InvestmentHistory>();

        this.bucketHistories = new HashSet<BucketHistory>();

        this.financialHistories = new HashSet<FinancialHistory>();

        this.segmentHistories = new HashSet<SegmentHistory>();

        this.inSuspenses = new HashSet<InSuspense>();

        this.commissionHistories = new HashSet<CommissionHistory>();

        this.commissionablePremiumHistories = new HashSet<CommissionablePremiumHistory>();

        init();
    }

    public EDITTrxHistory(EDITTrx editTrx)
    {
        init();

        this.editTrxHistoryVO.setEDITTrxFK(editTrx.getPK());
    }

    /**
     * Instantiates a EDITTrxHistory entity with a supplied EDITTrxHistoryVO.
     *
     * @param editTrxHistoryVO
     */
    public EDITTrxHistory(EDITTrxHistoryVO editTrxHistoryVO)
    {
        init();

        this.editTrxHistoryVO = editTrxHistoryVO;
    }

    public Long getEDITTrxFK()
    {
        return SessionHelper.getPKValue(editTrxHistoryVO.getEDITTrxFK());
    }
     //-- long getEDITTrxFK() 

    public void setEDITTrxFK(Long EDITTrxFK)
    {
        editTrxHistoryVO.setEDITTrxFK(SessionHelper.getPKValue(EDITTrxFK));
    }
     //-- void setEDITTrxFK(long) 

    public EDITDate getCycleDate()
    {
        return SessionHelper.getEDITDate(editTrxHistoryVO.getCycleDate());
    }

    //-- java.lang.String getCycleDate()
    public Long getEDITTrxHistoryPK()
    {
        return SessionHelper.getPKValue(editTrxHistoryVO.getEDITTrxHistoryPK());
    }

    //-- long getEDITTrxHistoryPK()
    public void setEDITTrxHistoryPK(Long editTrxHistoryPK)
    {
        this.editTrxHistoryVO.setEDITTrxHistoryPK(SessionHelper.getPKValue(editTrxHistoryPK));
    }

    public EDITDateTime getOriginalProcessDateTime()
    {
        return SessionHelper.getEDITDateTime(editTrxHistoryVO.getOriginalProcessDateTime());
    }

    public EDITDate getReleaseDate()
    {
        return SessionHelper.getEDITDate(editTrxHistoryVO.getReleaseDate());
    }

    public EDITDate getReturnDate()
    {
        return SessionHelper.getEDITDate(editTrxHistoryVO.getReturnDate());
    }

    public void setReleaseDate(EDITDate releaseDate)
    {
        editTrxHistoryVO.setReleaseDate(SessionHelper.getEDITDate(releaseDate));
    }

    public void setReturnDate(EDITDate returnDate)
    {
        editTrxHistoryVO.setReturnDate(SessionHelper.getEDITDate(returnDate));
    }
    
    public EDITDate getOriginalCycleDate()
    {
        return SessionHelper.getEDITDate(editTrxHistoryVO.getOriginalCycleDate());
    }
    
    public void setOriginalCycleDate(EDITDate originalCycleDate)
    {
        editTrxHistoryVO.setOriginalCycleDate(SessionHelper.getEDITDate(originalCycleDate));
    }

    public String getRealTimeInd()
    {
        return editTrxHistoryVO.getRealTimeInd();
    }

    //-- java.lang.String getRealTimeInd() 
    public long getProcessID()
    {
        return editTrxHistoryVO.getProcessID();
    }

    //-- long getProcessID() 
    public void setProcessID(long processID)
    {
        editTrxHistoryVO.setProcessID(processID);
    }

    //-- void setProcessID(long) 
    public String getCorrespondenceTypeCT()
    {
        return editTrxHistoryVO.getCorrespondenceTypeCT();
    }

    //-- java.lang.String getCorrespondenceTypeCT()
    public String getControlNumber()
    {
        return editTrxHistoryVO.getControlNumber();
    }

    //-- java.lang.String getControlNumber() 
    public void setControlNumber(String controlNumber)
    {
        editTrxHistoryVO.setControlNumber(controlNumber);
    }

    //-- void setControlNumber(java.lang.String) 
    public String getAccountingPendingStatus()
    {
        return editTrxHistoryVO.getAccountingPendingStatus();
    }

    /**
     * Getter
     * @return  set of investmentHistories
     */
    public Set<InvestmentHistory> getInvestmentHistories()
    {
        return investmentHistories;
    }

    /**
     * Setter
     * @param investmentHistories      set of investmentHistories
     */
    public void setInvestmentHistories(Set<InvestmentHistory> investmentHistories)
    {
        this.investmentHistories = investmentHistories;
    }

    /**
     * Adds a InvestmentHistory to the set of children
     * @param investmentHistory
     */
    public void addInvestmentHistory(InvestmentHistory investmentHistory)
    {
        this.getInvestmentHistories().add(investmentHistory);

        investmentHistory.setEDITTrxHistory(this);

        SessionHelper.saveOrUpdate(investmentHistory, EDITTrxHistory.DATABASE);
    }

    /**
     * Removes a InvestmentHistory from the set of children
     * @param investmentHistory
     */
    public void removeInvestmentHistory(InvestmentHistory investmentHistory)
    {
        this.getInvestmentHistories().remove(investmentHistory);

        investmentHistory.setEDITTrxHistory(null);

        SessionHelper.saveOrUpdate(investmentHistory, EDITTrxHistory.DATABASE);
    }

    /**
     * Removes all investmentHistories from the set of children
     */
    public void removeAllInvestmentHistories()
    {
        for (Iterator iterator = investmentHistories.iterator(); iterator.hasNext();)
        {
            InvestmentHistory investmentHistory = (InvestmentHistory) iterator.next();
            this.removeInvestmentHistory(investmentHistory);
        }
    }

    public String getAddressTypeCT()
    {
        return editTrxHistoryVO.getAddressTypeCT();
    }

    /**
     * Getter
     * @return  set of commissionHistories
     */
    public Set<CommissionHistory> getCommissionHistories()
    {
        return commissionHistories;
    }

    /**
     * Getter
     * @return  commissionHistory
     */
    public CommissionHistory getCommissionHistory()
    {
        return commissionHistory;
    }

    /**
     * Setter
     * @param commissionHistories      set of commissionHistories
     */
    public void setCommissionHistories(Set<CommissionHistory> commissionHistories)
    {
        this.commissionHistories = commissionHistories;
    }

    /**
     * Adds a CommissionHistory to the set of children
     * @param commissionHistory
     */
    public void addCommissionHistory(CommissionHistory commissionHistory)
    {
        this.getCommissionHistories().add(commissionHistory);

        commissionHistory.setEDITTrxHistory(this);

        SessionHelper.saveOrUpdate(commissionHistory, EDITTrxHistory.DATABASE);
    }

    /**
     * Removes a CommissionHistory from the set of children
     * @param commissionHistory
     */
    public void removeCommissionHistory(CommissionHistory commissionHistory)
    {
        this.getCommissionHistories().remove(commissionHistory);

        commissionHistory.setEDITTrxHistory(null);

        SessionHelper.saveOrUpdate(commissionHistory, EDITTrxHistory.DATABASE);
    }

    /**
     * Removes all commissionHistories from the set of children
     */
    public void removeAllCommissionHistories()
    {
        for (Iterator iterator = commissionHistories.iterator(); iterator.hasNext();)
        {
            CommissionHistory commissionHistory = (CommissionHistory) iterator.next();
            this.removeCommissionHistory(commissionHistory);
        }
    }


    /**
     * Getter.
     * @return
     */
    public InSuspense getInSuspense()
    {
        InSuspense inSuspense = getInSuspenses().isEmpty() ? null : (InSuspense) getInSuspenses().iterator().next();

        return inSuspense;
    }

    /**
     * Setter.
     * @param inSuspense
     */
    public void setInSuspense(InSuspense inSuspense)
    {
        getInSuspenses().add(inSuspense);

        inSuspense.setEDITTrxHistory(this);
    }

    /**
     * Getter.
     * @return
     */
    public Set<InSuspense> getInSuspenses()
    {
        return inSuspenses;
    }

    /**
     * Setter.
     * @param inSuspenses
     */
    public void setInSuspenses(Set<InSuspense> inSuspenses)
    {
        this.inSuspenses = inSuspenses;
    }

    /**
     * Getter.
     * @return
     */
    public Set<BucketHistory> getBucketHistories()
    {
        return bucketHistories;
    }

    /**
     * Setter.
     * @param bucketHistories
     */
    public void setBucketHistories(Set<BucketHistory> bucketHistories)
    {
        this.bucketHistories = bucketHistories;
    }

    /**
     * Getter
     * @return  set of chargeHistories
     */
    public Set<ChargeHistory> getChargeHistories()
    {
        return chargeHistories;
    }

    /**
     * Setter
     * @param chargeHistories      set of chargeHistories
     */
    public void setChargeHistories(Set<ChargeHistory> chargeHistories)
    {
        this.chargeHistories = chargeHistories;
    }

    /**
     * Adds a ChargeHistory to the set of children
     * @param chargeHistory
     */
    public void addChargeHistory(ChargeHistory chargeHistory)
    {
        this.getChargeHistories().add(chargeHistory);

        chargeHistory.setEDITTrxHistory(this);

        SessionHelper.saveOrUpdate(chargeHistory, EDITTrxHistory.DATABASE);
    }

    /**
     * Removes a ChargeHistory from the set of children
     * @param chargeHistory
     */
    public void removeChargeHistory(ChargeHistory chargeHistory)
    {
        this.getChargeHistories().remove(chargeHistory);

        chargeHistory.setEDITTrxHistory(null);

        SessionHelper.saveOrUpdate(chargeHistory, EDITTrxHistory.DATABASE);
    }

    /**
     * Removes all chargeHistories from the set of children
     */
    public void removeAllChargeHistories()
    {
        for (Iterator iterator = chargeHistories.iterator(); iterator.hasNext();)
        {
            ChargeHistory chargeHistory = (ChargeHistory) iterator.next();
            this.removeChargeHistory(chargeHistory);
        }
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (editTrxHistoryVO == null)
        {
            editTrxHistoryVO = new EDITTrxHistoryVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save()
    {
        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    /**
     * @throws Throwable
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws Throwable
    {
        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return editTrxHistoryVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return editTrxHistoryVO.getEDITTrxHistoryPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.editTrxHistoryVO = (EDITTrxHistoryVO) voObject;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#isNew()
     */
    public boolean isNew()
    {
        return crudEntityImpl.isNew(this);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    /**
     * Finder.
     *
     * @param editTrxHistoryPK
     */
    public static final EDITTrxHistory findByPK(long editTrxHistoryPK)
    {
        EDITTrxHistory editTrxHistory = null;

        EDITTrxHistoryVO[] editTrxHistoryVOs = new EDITTrxHistoryDAO().findByPK(editTrxHistoryPK);

        if (editTrxHistoryVOs != null)
        {
            editTrxHistory = new EDITTrxHistory(editTrxHistoryVOs[0]);
        }

        return editTrxHistory;
    }

    /**
     * Finder.
     * @param segmentPK
     * @param cycleDate
     * @return
     */
    public static EDITTrxHistory[] findBy_SegmentPK_CycleDateGTE(long segmentPK, String cycleDate)
    {
        EDITTrxHistoryVO[] editTrxHistoryVOs = new EDITTrxHistoryDAO().findBy_SegmentPK_CycleDateGTE(segmentPK, cycleDate);

        return (EDITTrxHistory[]) CRUDEntityImpl.mapVOToEntity(editTrxHistoryVOs, EDITTrxHistory.class);
    }

    /**
     * The associated EDITTrx.
     * @return
     */
    public EDITTrx getEDITTrx()
    {
        return editTrx;
    }

    public void setEDITTrx(EDITTrx editTrx)
    {
        this.editTrx = editTrx;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDateTime getProcessDateTime()
    {
        return SessionHelper.getEDITDateTime(editTrxHistoryVO.getProcessDateTime());
    }

    /**
     * The associated InvestmentHistory, if any.
     * @return
     */
    public InvestmentHistory getInvestmentHistory()
    {
        InvestmentHistory investmentHistory = getInvestmentHistories().isEmpty() ? null : (InvestmentHistory) getInvestmentHistories().iterator().next();

        return investmentHistory;
    }

    /**
     * Setter.
     * @param financialHistory
     */
//    public void setFinancialHistory(FinancialHistory financialHistory)
//    {
//        getFinancialHistories().add(financialHistory);
//
//        financialHistory.setEDITTrxHistory(this);
//    }

    /**
     * Getter
     * @return  set of financialHistories
     */
    public Set<FinancialHistory> getFinancialHistories()
    {
        return financialHistories;
    }

    /**
     * Setter
     * @param financialHistories      set of financialHistories
     */
    public void setFinancialHistories(Set<FinancialHistory> financialHistories)
    {
        this.financialHistories = financialHistories;
    }

    /**
     * Adds a FinancialHistory to the set of children
     * @param financialHistory
     */
    public void addFinancialHistory(FinancialHistory financialHistory)
    {
        this.getFinancialHistories().add(financialHistory);

        financialHistory.setEDITTrxHistory(this);

        SessionHelper.saveOrUpdate(financialHistory, EDITTrxHistory.DATABASE);
    }

    /**
     * Removes a FinancialHistory from the set of children
     * @param financialHistory
     */
    public void removeFinancialHistory(FinancialHistory financialHistory)
    {
        this.getFinancialHistories().remove(financialHistory);

        financialHistory.setEDITTrxHistory(null);

        SessionHelper.saveOrUpdate(financialHistory, EDITTrxHistory.DATABASE);
    }

    /**
     * The associated FinancialHistory, if any.
     * @return
     */
    public FinancialHistory getFinancialHistory()
    {
        Set financialHistories = getFinancialHistories();

        FinancialHistory financialHistory = (financialHistories.isEmpty()) ? null : (FinancialHistory) getFinancialHistories().iterator().next();

        return financialHistory;
    }

    /**
     * The associated EDITTrx.
     * @return
     */
    public FinancialHistory get_FinancialHistory()
    {
//        if (financialHistory == null)
//        {
        FinancialHistory financialHistory = FinancialHistory.findByEDITTrxHistoryPK(editTrxHistoryVO.getEDITTrxHistoryPK());
//        }

        return financialHistory;
    }

    /**
     * Setter.
     * @param segmentHistory
     */
    public void setSegmentHistory(SegmentHistory segmentHistory)
    {
        getSegmentHistories().add(segmentHistory);

        segmentHistory.setEDITTrxHistory(this);
    }

    /**
     * Getter.
     * @return
     */
    public Set<SegmentHistory> getSegmentHistories()
    {
        return segmentHistories;
    }

    /**
     * Setter.
     * @param segmentHistories
     */
    public void setSegmentHistories(Set<SegmentHistory> segmentHistories)
    {
        this.segmentHistories = segmentHistories;
    }

    /**
     * The associated ChargeHistory, if any.
     * @return
     */
    public ChargeHistory getChargeHistory()
    {
        return ChargeHistory.findByEDITTrxHistoryPK(getPK());
    }

    /**
     * The associated WithholdingHistory, if any.
     * @return
     */
    public WithholdingHistory getWithholdingHistory()
    {
        WithholdingHistory withholdingHistory = getWithholdingHistories().isEmpty() ? null : (WithholdingHistory) getWithholdingHistories().iterator().next();

        return withholdingHistory;
    }

    /**
     * Setter.
     * @param withholdingHistory
     */
//    public void setWithholdingHistory(WithholdingHistory withholdingHistory)
//    {
//        getWithholdingHistories().add(withholdingHistory);
//
//        withholdingHistory.setEDITTrxHistory(this);
//    }

    /**
     * Getter
     * @return  set of withholdingHistories
     */
    public Set<WithholdingHistory> getWithholdingHistories()
    {
        return withholdingHistories;
    }

    /**
     * Setter
     * @param withholdingHistories      set of withholdingHistories
     */
    public void setWithholdingHistories(Set<WithholdingHistory> withholdingHistories)
    {
        this.withholdingHistories = withholdingHistories;
    }

    /**
     * Adds a WithholdingHistory to the set of children
     * @param withholdingHistory
     */
    public void addWithholdingHistory(WithholdingHistory withholdingHistory)
    {
        this.getWithholdingHistories().add(withholdingHistory);

        withholdingHistory.setEDITTrxHistory(this);

        SessionHelper.saveOrUpdate(withholdingHistory, EDITTrxHistory.DATABASE);
    }

    /**
     * Removes a WithholdingHistory from the set of children
     * @param withholdingHistory
     */
    public void removeWithholdingHistory(WithholdingHistory withholdingHistory)
    {
        this.getWithholdingHistories().remove(withholdingHistory);

        withholdingHistory.setEDITTrxHistory(null);

        SessionHelper.saveOrUpdate(withholdingHistory, EDITTrxHistory.DATABASE);
    }

    /**
     * Removes all withholdingHistories from the set of children
     */
    public void removeAllWithholdingHistories()
    {
        for (Iterator iterator = withholdingHistories.iterator(); iterator.hasNext();)
    {
            this.removeWithholdingHistory((WithholdingHistory) iterator.next());
        }
    }

    /**
     * Removes all financialHistories from the set of children
     */
    public void removeAllFinancialHistories()
    {
        for (Iterator iterator = financialHistories.iterator(); iterator.hasNext();)
    {
            this.removeFinancialHistory((FinancialHistory) iterator.next());
        }
    }

    /**
     * Finder.
     * @param editTrxPK
     * @return
     */
    public static final EDITTrxHistory findBy_EDITTrxPK(long editTrxPK)
    {
        EDITTrxHistory editTrxHistory = null;

        EDITTrxHistoryVO[] editTrxHistoryVOs = new EDITTrxHistoryDAO().findByEditTrxPK(editTrxPK);

        if (editTrxHistoryVOs != null)
        {
            editTrxHistory = new EDITTrxHistory(editTrxHistoryVOs[0]);
        }

        return editTrxHistory;
    }

    /**
     * Undose the the ChargeCodes for this EDITrxHistory's InvestmentHistories.
     */
    public void undoChargeCodes()
    {
        InvestmentHistory[] investmentHistories = get_InvestmentHistories();

        if (investmentHistories != null)
        {
            for (int i = 0; i < investmentHistories.length; i++)
            {
                InvestmentHistory investmentHistory = investmentHistories[i];

                investmentHistory.undoChargeCode();
            }
        }
    }

    /**
     * The set of child InvestmentHistories for this EDITTrxHistory, if any.
     * This is the CRUD version.
     * @return
     */
    private InvestmentHistory[] get_InvestmentHistories()
    {
        return (InvestmentHistory[]) CRUDEntityImpl.mapVOToEntity(new InvestmentHistoryDAO().findByEDITTrxHistoryPK(getPK()), InvestmentHistory.class);
    }

    /**
     * Setter.
     * @param processDate
     */
    public void setCycleDate(EDITDate processDate)
    {
        this.editTrxHistoryVO.setCycleDate(SessionHelper.getEDITDate(processDate));
    }

    /**
     * Setter.
     * @param editDateTime
     */
    public void setOriginalProcessDateTime(EDITDateTime editDateTime)
    {
        this.editTrxHistoryVO.setOriginalProcessDateTime(SessionHelper.getEDITDateTime(editDateTime));
    }

    /**
     * Setter.
     * @param accountingpendingstatusYes
     */
    public void setAccountingPendingStatus(String accountingpendingstatusYes)
    {
        this.editTrxHistoryVO.setAccountingPendingStatus(accountingpendingstatusYes);
    }

    /**
     * Setter.
     * @param correspondenceTypeCT
     */
    public void setCorrespondenceTypeCT(String correspondenceTypeCT)
    {
        this.editTrxHistoryVO.setCorrespondenceTypeCT(correspondenceTypeCT);
    }

    /**
     * Setter.
     * @param realTimeInd
     */
    public void setRealTimeInd(String realTimeInd)
    {
        this.editTrxHistoryVO.setRealTimeInd(realTimeInd);
    }

    /**
     * Setter.
     * @param addressTypeCT
     */
    public void setAddressTypeCT(String addressTypeCT)
    {
        this.editTrxHistoryVO.setAddressTypeCT(addressTypeCT);
    }

    /**
     * Setter.
     * @param processDateTime
     */
    public void setProcessDateTime(EDITDateTime processDateTime)
    {
        this.editTrxHistoryVO.setProcessDateTime(SessionHelper.getEDITDateTime(processDateTime));
    }

    public Set<CommissionablePremiumHistory> getCommissionablePremiumHistories()
    {
        return commissionablePremiumHistories;
    }

    public void setCommissionablePremiumHistories(Set<CommissionablePremiumHistory> commissionablePremiumHistories)
    {
        this.commissionablePremiumHistories = commissionablePremiumHistories;
    }

    public void addCommissionablePremiumHistory(CommissionablePremiumHistory commissionablePremiumHistory)
    {
        this.commissionablePremiumHistories.add(commissionablePremiumHistory);

        commissionablePremiumHistory.setEDITTrxHistory(this);
    }

    public void removeCommissionablePremiumHistory(CommissionablePremiumHistory commissionablePremiumHistory)
    {
        this.commissionablePremiumHistories.remove(commissionablePremiumHistory);

        commissionablePremiumHistory.setEDITTrxHistory(null);
    }

    /**
     * Builds a child FinancialHistory with the specified defaults.
     * @param grossAmount
     * @param disbursementSourceCT
     * @return
     */
    public FinancialHistory generateFinancialHistory(EDITBigDecimal grossAmount, EDITBigDecimal netAmount, EDITBigDecimal checkAmount, String disbursementSourceCT)
    {
        FinancialHistory financialHistory = new FinancialHistory(this);

        financialHistory.setGrossAmount(grossAmount);

        financialHistory.setNetAmount(netAmount);

        financialHistory.setCheckAmount(checkAmount);

        financialHistory.setDisbursementCT(disbursementSourceCT);

        return financialHistory;
    }

    /**
     * Builds a child CommissionHistory with the specified defaults.
     * @param commissionAmount
     * @param updateStatus
     * @param accountingPendingStatus
     * @param commissionTypeCT
     * @param statementInd
     * @param bonusUpdateStatus
     * @return
     */
    public CommissionHistory generateCommissionHistory(EDITBigDecimal commissionAmount, String updateStatus, String accountingPendingStatus, String commissionTypeCT, String statementInd, String bonusUpdateStatus, String operator)
    {
        CommissionHistory commissionHistory = new CommissionHistory(this);

        commissionHistory.setCommissionAmount(commissionAmount);

        commissionHistory.setUpdateStatus(updateStatus);

        commissionHistory.setAccountingPendingStatus(accountingPendingStatus);

        commissionHistory.setCommissionTypeCT(commissionTypeCT);

        commissionHistory.setStatementInd(statementInd);

        commissionHistory.setOperator(operator);

        commissionHistory.setMaintDateTime(new EDITDateTime());

        //        commissionHistory.setBonusUpdateStatus(bonusUpdateStatus);
        return commissionHistory;
    }

    public void addBucketHistory(BucketHistory bucketHistory)
    {
        getBucketHistories().add(bucketHistory);

        bucketHistory.setEDITTrxHistory(this);
    }

    /**
     * Adder.
     * @param inSuspense
     */
    public void addInSuspense(InSuspense inSuspense)
    {
        getInSuspenses().add(inSuspense);

        inSuspense.setEDITTrxHistory(this);
    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, EDITTrxHistory.DATABASE);
    }

    public void hDelete()
    {
        SessionHelper.delete(this, EDITTrxHistory.DATABASE);
    }

  /**
     * Returns the latest Investment Histories that are marked as 'Final' (InvestmentHistory.FinalPriceStatus = 'F')
     * @return
     */
    public InvestmentHistory[] getInvestmentHistoriesWithFinalPrice()
    {
        List investmentHistoriesWithFinalPrice = new ArrayList();

        Map investmentHistoriesMapByInvestmentPK = new HashMap();

        Iterator iterator = getInvestmentHistories().iterator();

        while(iterator.hasNext())
        {
            InvestmentHistory investmentHistory = (InvestmentHistory) iterator.next();

            Long investmentFK = investmentHistory.getInvestmentFK();

            List investmentHistoriesListByInvestmentPK = null;

            if (investmentHistoriesMapByInvestmentPK.containsKey(investmentFK))
            {
                investmentHistoriesListByInvestmentPK = (List) investmentHistoriesMapByInvestmentPK.get(investmentFK);
            }
            else
            {
                investmentHistoriesListByInvestmentPK = new ArrayList();
            }

            investmentHistoriesListByInvestmentPK.add(investmentHistory);

            investmentHistoriesMapByInvestmentPK.put(investmentFK, investmentHistoriesListByInvestmentPK);
        }

        iterator = investmentHistoriesMapByInvestmentPK.values().iterator();

        while (iterator.hasNext())
        {
            InvestmentHistory investmentHistory = null;

            List investmentHistories = (List) iterator.next();

            if (investmentHistories.size() == 1)
            {
                investmentHistory = (InvestmentHistory) investmentHistories.get(0);
            }
            else
            {
                investmentHistory = getLatestInvestmentHistory((InvestmentHistory[]) investmentHistories.toArray(new InvestmentHistory[investmentHistories.size()]));
            }

            if (investmentHistory.getFinalPriceStatus().equals("F"))
            {
                investmentHistoriesWithFinalPrice.add(investmentHistory);
            }
        }

        return (InvestmentHistory[]) investmentHistoriesWithFinalPrice.toArray(new InvestmentHistory[investmentHistoriesWithFinalPrice.size()]);
    }

    /**
     * Returns latest InvestmentHistory comparing InvestmentHistoryPKs  
     * @param investmentHistories
     * @return
     */
    private InvestmentHistory getLatestInvestmentHistory(InvestmentHistory[] investmentHistories)
    {
        InvestmentHistory investmentHistoryWithMaxInvestmentHistoryPK = investmentHistories[0];

        for (int i = 0; i < investmentHistories.length; i++)
        {
            InvestmentHistory investmentHistory = investmentHistories[i];

            if (investmentHistory.getInvestmentHistoryPK().longValue() >investmentHistoryWithMaxInvestmentHistoryPK.getInvestmentHistoryPK().longValue())
            {
                investmentHistoryWithMaxInvestmentHistoryPK = investmentHistory;
            }
        }

        return investmentHistoryWithMaxInvestmentHistoryPK;
    }

  /**
     * Originally in EDITTrxHistoryDAO.findByEditTrxPK
     * @param editTrxPK
     * @return
     */
    public static EDITTrxHistory[] findBy_EDITTrxPK(Long editTrxPK)
    {
        String hql = " select editTrxHistory from EDITTrxHistory editTrxHistory" +
                    " where editTrxHistory.EDITTrxFK = :editTrxPK";

        Map params = new HashMap();

        params.put("editTrxPK", editTrxPK);

        List results = SessionHelper.executeHQL(hql, params, EDITTrxHistory.DATABASE);

        return (EDITTrxHistory[]) results.toArray(new EDITTrxHistory[results.size()]);
    }

    /**
     * Adds a SegmentHistory to the set of children
     * @param segmentHistory
     */
    public void addSegmentHistory(SegmentHistory segmentHistory)
    {
        this.getSegmentHistories().add(segmentHistory);

        segmentHistory.setEDITTrxHistory(this);

        SessionHelper.saveOrUpdate(segmentHistory, EDITTrxHistory.DATABASE);
    }

    /**
     * Removes a SegmentHistory from the set of children
     * @param segmentHistory
     */
    public void removeSegmentHistory(SegmentHistory segmentHistory)
    {
        this.getSegmentHistories().remove(segmentHistory);

        segmentHistory.setEDITTrxHistory(null);

        SessionHelper.saveOrUpdate(segmentHistory, EDITTrxHistory.DATABASE);
    }

    public void setReinsuranceHistories(Set reinsuranceHistories)
    {
        this.reinsuranceHistories = reinsuranceHistories;
    }

    public Set<ReinsuranceHistory> getReinsuranceHistories()
    {
        return reinsuranceHistories;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return EDITTrxHistory.DATABASE;
    }

    public EDITTrxHistoryVO getAsVO()
    {
        return editTrxHistoryVO;
    }

    /**
     * Finder by PK.
     * @param editTrxHistoryPK
     * @return
     */
    public static final EDITTrxHistory findByPK(Long editTrxHistoryPK)
    {
        return (EDITTrxHistory) SessionHelper.get(EDITTrxHistory.class, editTrxHistoryPK, EDITTrxHistory.DATABASE);
    }

    public static final EDITTrxHistory findBy_EDITTrxFK(Long editTrxFK)
    {
        EDITTrxHistory editTrxHistory = null;

        String hql = " select editTrxHistory" +
                     " from EDITTrxHistory editTrxHistory" +
                     " where editTrxHistory.EDITTrxFK = :editTrxFK";

        Map params = new HashMap();

        params.put("editTrxFK", editTrxFK);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        if (!results.isEmpty())
        {
            editTrxHistory = (EDITTrxHistory) results.get(0);
        }

        return editTrxHistory;
    }

    /**
     * Finds the associated CompanyStructurePK via the path of:
     * EDITTrxHistory.EDITTrx.ClientSetup.ContractSetup.Segment.CompanyStructureFK.
     * @param editTrxHistoryPK
     * @return
     */
    public static Long findCompanyStructurePKBy_EDITTrxHistoryPK(Long editTrxHistoryPK)
    {
        String hql = " select segment.CompanyStructureFK from EDITTrxHistory editTrxHistory" +
                        " join editTrxHistory.EDITTrx editTrx" +
                        " join editTrx.ClientSetup clientSetup" +
                        " join clientSetup.ContractSetup contractSetup" +
                        " join contractSetup.Segment segment" +
                        " where editTrxHistory.EDITTrxHistoryPK = :editTrxHistoryPK";

        Map params = new HashMap();

        params.put("editTrxHistoryPK", editTrxHistoryPK);

        List results = SessionHelper.executeHQL(hql, params, EDITTrxHistory.DATABASE);

        return (Long) results.get(0);
    }

    /**
     * Finds by an EffectiveDate equal to the specified date (joined with EDITTrx, InvestmentHistory, Investment)
     *
     * @param effectiveDate
     * @return
     */
    public static EDITTrxHistory[] findByEffectiveDate_TransactionTypeCT(EDITDate effectiveDate, String[] transactionTypeCTs)
    {
        String hql = " select editTrxHistory from EDITTrxHistory editTrxHistory" +
                     " join editTrxHistory.EDITTrx editTrx" +
                     " where editTrx.Status in ('N','A')" +
                     " and editTrx.PendingStatus = 'H'" +
                     " and editTrx.EffectiveDate = :effectiveDate" +
                     " and editTrx.TransactionTypeCT in (:transactionTypeCTs)";

        Map params = new HashMap();

        params.put("effectiveDate", effectiveDate.getFormattedDate());
        params.put("transactionTypeCTs", transactionTypeCTs);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        return (EDITTrxHistory[]) results.toArray(new EDITTrxHistory[results.size()]);
    }
  /*
	* Finds a Segment (using a separate Hibernate Session) with its Investment-related collection(s) as follows:
   * 
   * Segment
   * Segment.Investment
   * Segment.Investment.FilteredFund.Fund
   * Segment.Investment.InvestmentHistory
   * Segment.Investment.InvestmentHistory.EDITTrxHistory
   * SegmentInvestment.InvestmentHistory.EDITTrxHistory.EDITTrx
   * Segment.Investment.InvestmentHistory.EDITTrxHistory.BucketHistory
   * Segment.Investment.InvestmentHistory.EDITTrxHistory.BucketHistory.Bucket
   * 
   * where the SegmentPK is specified
   * and the 
   * 
   * 
   * @param segment
   * @param currMFEffDate/fromDate
   * @param editTrxFromDate/toDate
   * @param
   * @return
   */
  public static EDITTrxHistory[] findSeparateBy_SegmentPK_V1(Segment segment, EDITDate fromDate, EDITDate toDate, String transactionTypeCT)
  {
    int transactionPriority = TransactionPriorityCache.getInstance().getPriority(transactionTypeCT);

    Long segmentPK = segment.getSegmentPK();

    String hql = " select editTrxHistory" +
                  " from EDITTrxHistory editTrxHistory " +
                  " join fetch editTrxHistory.EDITTrx editTrx" +
                  " join editTrx.ClientSetup clientSetup" +
                  " join clientSetup.ContractSetup contractSetup" +
                  " join fetch editTrxHistory.InvestmentHistories" +
                  " join fetch editTrxHistory.BucketHistories" +

    //                  " join fetch investment.FilteredFunds filteredFund" +
    //                  " join fetch filteredFund.Fund fund" +

                  " where contractSetup.Segment = :segment" +

                  " and editTrx.Status in (:statusN, :statusA)" +

                  " and ((editTrx.EffectiveDate > :fromDate" +
                  " and editTrx.EffectiveDate < :toDate)" +

                  " or (editTrx.EffectiveDate = :toDate" +
                  " and editTrx.TransactionTypeCT in (" +
                  " select transactionPriority.TransactionTypeCT" +
                  " from TransactionPriority transactionPriority" +
                  " where transactionPriority.Priority <= :transactionPriority))" +

                  " or (editTrx.EffectiveDate = :fromDate" +
                  " and editTrx.TransactionTypeCT in (" +
                  " select transactionPriority.TransactionTypeCT" +
                  " from TransactionPriority transactionPriority" +
                  " where transactionPriority.Priority >= :transactionPriority))" +

                  ")" +

                  " and editTrx.PendingStatus = :pendingStatus" +
                  " order by editTrxHistory.EDITTrxHistoryPK asc";

  //                  " and fund.FundType <> :fundType";

        Map params = new EDITMap("segment", segment)
                      .put("fromDate", fromDate)
                      .put("toDate", toDate)
                      .put("transactionPriority", new Integer(transactionPriority))
  //                      .put("fundType", Fund.FUNDTYPE_SYSTEM)
                      .put("statusN", EDITTrx.STATUS_NATURAL)
                      .put("statusA", EDITTrx.STATUS_APPLY)
                      .put("pendingStatus", EDITTrx.PENDINGSTATUS_HISTORY);

        Session session = null;

        List<EDITTrxHistory> results = null;

        try
        {
          session = SessionHelper.getSeparateSession(EDITTrxHistory.DATABASE);

          results = SessionHelper.executeHQL(session, hql, params, 0);
        }
        finally
        {
          if (session != null) session.close();
        }


        return results.toArray(new EDITTrxHistory[results.size()]);


    //    DISTINCT " + EDITTRX_HISTORY_TABLENAME + ".* FROM " + EDITTRX_HISTORY_TABLENAME + ", " +
    //                 EDITTRX_TABLENAME + ", " + CLIENT_SETUP_TABLENAME + ", " + CONTRACT_SETUP_TABLENAME + ", " + transactionPriorityTable +
    //                 " WHERE " + segmentFKCol + " = " + segmentPK +
    //                 " AND " + contractSetupFKCol + " = " + contractSetupPKCol +
    //                 " AND " + clientSetupFKCol + " = " + clientSetupPKCol +
    //                 " AND " + statusCol + "IN ('N', 'A')" +

    //                 " AND " + editTrxFKCol + " = " + editTrxPKCol +

    //                 " AND ((" + effectiveDateCol + " > ?" +
    //                 " AND " + effectiveDateCol + " < ?)" +

    //                 "  OR (" + effectiveDateCol + " = ?" +
    //                 " AND (" + trxPriorityTransactionTypeCTCol + " = " + editTrxTransactionTypeCTCol +
    //                 " AND " + priorityCol + " <= (SELECT " + priorityCol + " FROM " + transactionPriorityTable +
    //                 " WHERE " + trxPriorityTransactionTypeCTCol + " = '" + transactionType + "')))" +

    //                 " OR (" + effectiveDateCol + " = ?" +
    //                 " AND (" + trxPriorityTransactionTypeCTCol + " = " + editTrxTransactionTypeCTCol +
    //                 " AND " + priorityCol + " > (SELECT " + priorityCol + " FROM " + transactionPriorityTable +
    //                 " WHERE " + trxPriorityTransactionTypeCTCol + " = '" + transactionType + "')))" +

//                        ")" +

    //                 " AND " + pendingStatusCol + " = 'H'";
  }


}
