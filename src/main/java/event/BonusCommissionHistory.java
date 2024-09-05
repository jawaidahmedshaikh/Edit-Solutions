/*
 * User: gfrosti
 * Date: Apr 19, 2005
 * Time: 11:39:25 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import agent.*;

import contract.Segment;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;

import edit.common.vo.BonusCommissionHistoryVO;
import edit.common.vo.VOObject;

import edit.services.db.CRUD;
import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import event.dm.dao.BonusCommissionHistoryDAO;

import java.util.*;


public class BonusCommissionHistory extends HibernateEntity implements CRUDEntity
{
    public static final String BONUSUPDATESTATUS_UPDATE = "U";
    public static final String BONUSUPDATESTATUS_HISTORY = "H";
    public static final String ACCOUNTING_PENDING_STATUS_YES = "Y";
    public static final String ACCOUNTING_PENDING_STATUS_NO = "N";
    private CRUDEntityImpl crudEntityImpl;
    private BonusCommissionHistoryVO bonusCommissionHistoryVO;
    private CommissionHistory commissionHistory;
    private ParticipatingAgent participatingAgent;
    private Set appliedPremiumLevels = new HashSet();
    
    /**
     * The BonusCommissionHistory was applicable to an AppliedPremiumLevel.
     */
    public static final String BONUSUPDATESTATUS_BONUSED = "B";
    
    /**
     * The BonusCommissionHistory was not applicable.
     */
    public static final String BONUSUPDATESTATUS_NOT_BONUSED = "N";
    
    /**
     * The BonusCommissionHistory was applicable as a negative amount.
     */
    public static final String BONUSUPDATESTATUS_CHARGEBACK = "C";
    
    /**
     * The amount to be credited (+ amount) or debited (- amount) to the Agent's bonus.
     */
    private EDITBigDecimal amount;
    
    /**
     * The originating transaction type that generated this BonusCommissionHistory.
     */
    private String transactionTypeCT;
    
    /**
     * The reason this BonusCommissionHistory needed to be updated at a later time.
     * See the valid fields below:
     * @see 
     */
    private String updateReason;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public BonusCommissionHistory()
    {
        init();
    }

    /**
     * Instantiates a BonusCommissionHistory entity with a default BonusCommissionHistoryVO.
     * Default values are as follows:
     * - BonusUpdateStatus = 'U'
     * - BonusUpdateDateTime = System DateTime
     */
    public BonusCommissionHistory(String accountingPendingStatus)
    {
        init();

        setBonusUpdateStatus(BonusCommissionHistory.BONUSUPDATESTATUS_HISTORY);

        setAccountingPendingStatus(accountingPendingStatus);
    }

    /**
     * Instantiates a BonusCommissionHistory entity with a supplied BonusCommissionHistoryVO.
     *
     * @param bonusCommissionHistoryVO
     */
    public BonusCommissionHistory(BonusCommissionHistoryVO bonusCommissionHistoryVO)
    {
        init();

        this.bonusCommissionHistoryVO = bonusCommissionHistoryVO;
    }

    public Set getAppliedPremiumLevels()
    {
        return appliedPremiumLevels;
    }

    public void setAppliedPremiumLevels(Set appliedPremiumLevels)
    {
        this.appliedPremiumLevels = appliedPremiumLevels;
    }

    public CommissionHistory getCommissionHistory()
    {
        return commissionHistory;
    }

    public void setCommissionHistory(CommissionHistory commissionHistory)
    {
        this.commissionHistory = commissionHistory;
    }

    /**
     * Adder.
     * @param appliedPremiumLevel
     */
    public void addAppliedPremiumLevel(AppliedPremiumLevel appliedPremiumLevel)
    {
        getAppliedPremiumLevels().add(appliedPremiumLevel);

        appliedPremiumLevel.setBonusCommissionHistory(this);
    }

    public String getAccountingPendingStatus()
    {
        return bonusCommissionHistoryVO.getAccountingPendingStatus();
    }

    public Long getBonusCommissionHistoryPK()
    {
        return SessionHelper.getPKValue(bonusCommissionHistoryVO.getBonusCommissionHistoryPK());
    }

    public Long getCommissionHistoryFK()
    {
        return SessionHelper.getPKValue(bonusCommissionHistoryVO.getCommissionHistoryFK());
    }

    public Long getParticipatingAgentFK()
    {
        return SessionHelper.getPKValue(bonusCommissionHistoryVO.getParticipatingAgentFK());
    }

    public void setBonusCommissionHistoryPK(long bonusCommissionHistoryPK)
    {
        bonusCommissionHistoryVO.setBonusCommissionHistoryPK(bonusCommissionHistoryPK);
    }

    public void setCommissionHistoryFK(Long commissionHistoryFK)
    {
        bonusCommissionHistoryVO.setCommissionHistoryFK(SessionHelper.getPKValue(commissionHistoryFK));
    }

    public void setParticipatingAgentFK(Long participatingAgentFK)
    {
        bonusCommissionHistoryVO.setParticipatingAgentFK(SessionHelper.getPKValue(participatingAgentFK));
    }

    /**
     * Setter.
     *
     * @param bonusUpdateDateTime
     */
    public void setBonusUpdateDateTime(EDITDateTime bonusUpdateDateTime)
    {
        bonusCommissionHistoryVO.setBonusUpdateDateTime(SessionHelper.getEDITDateTime(bonusUpdateDateTime));
    }

    //-- void setBonusUpdateDateTime(java.lang.String)

    /**
     * Setter.
     *
     * @param bonusUpdateStatus
     */
    public void setBonusUpdateStatus(String bonusUpdateStatus)
    {
        bonusCommissionHistoryVO.setBonusUpdateStatus(bonusUpdateStatus);
    }

    //-- void setBonusUpdateStatus(java.lang.String)

    /**
     * Getter.
     *
     * @return
     */
    public EDITDateTime getBonusUpdateDateTime()
    {
        return SessionHelper.getEDITDateTime(bonusCommissionHistoryVO.getBonusUpdateDateTime());
    }

    //-- java.lang.String getBonusUpdateDateTime()

    /**
     * Getter.
     *
     * @return
     */
    public String getBonusUpdateStatus()
    {
        return bonusCommissionHistoryVO.getBonusUpdateStatus();
    }

    //-- java.lang.String getBonusUpdateStatus()

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (bonusCommissionHistoryVO == null)
        {
            bonusCommissionHistoryVO = new BonusCommissionHistoryVO();
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
        updateBonusUpdateDateTime();
        
        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    /**
     * Allows this BonusCommissionHistory to participate in the current trx of the specified CRUD.
     *
     * @param crud
     */
    public void save(CRUD crud)
    {
        crud.createOrUpdateVOInDB(getVO());
    }

    /**
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete()
    {
        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, BonusCommissionHistory.DATABASE);
    }

//    /**
//     * Delete the entity using Hibernate
//     */
//    public void hDelete()
//    {
//        SessionHelper.delete(this, BonusCommissionHistory.DATABASE);
//    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return bonusCommissionHistoryVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return bonusCommissionHistoryVO.getBonusCommissionHistoryPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.bonusCommissionHistoryVO = (BonusCommissionHistoryVO) voObject;
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
     * @param bonusCommissionHistoryPK
     */
    public static final BonusCommissionHistory findByPK(long bonusCommissionHistoryPK)
    {
        BonusCommissionHistory bonusCommissionHistory = null;

        BonusCommissionHistoryVO[] bonusCommissionHistoryVOs = new BonusCommissionHistoryDAO().findByPK(bonusCommissionHistoryPK);

        if (bonusCommissionHistoryVOs != null)
        {
            bonusCommissionHistory = new BonusCommissionHistory(bonusCommissionHistoryVOs[0]);
        }

        return bonusCommissionHistory;
    }

    /**
     * Associates this BonusCommissionHistory with the specified commissionHistory. The modified state is not saved.
     *
     * @param commissionHistory
     */
    public void associate(CommissionHistory commissionHistory)
    {
        this.bonusCommissionHistoryVO.setCommissionHistoryFK(commissionHistory.getPK());
    }

    /**
     * Associates this BonusCommissionHistory with the specified participatingAgent. The modified state is not saved.
     *
     * @param participatingAgent
     */
    public void associate(ParticipatingAgent participatingAgent)
    {
        this.bonusCommissionHistoryVO.setParticipatingAgentFK(participatingAgent.getParticipatingAgentPK().longValue());
    }

    /**
     * Sets the DateTime to be the current System DateTime.
     */
    public void updateBonusUpdateDateTime()
    {
        setBonusUpdateDateTime(new EDITDateTime());
    }

    /**
     * Finder.
     *
     * @param participatingAgentPK
     * @param commissionHistoryPK
     * @return
     */
    public static final BonusCommissionHistory findBy_ParticipatingAgentPK_CommissionHistoryPK_UsingCRUD(long participatingAgentPK, long commissionHistoryPK)
    {
        BonusCommissionHistory bonusCommissionHistory = null;

        BonusCommissionHistoryVO[] bonusCommissionHistoryVOs = new BonusCommissionHistoryDAO().findBy_ParticipatingAgentPK_CommissionHistoryPK(participatingAgentPK, commissionHistoryPK);

        if (bonusCommissionHistoryVOs != null)
        {
            bonusCommissionHistory = new BonusCommissionHistory(bonusCommissionHistoryVOs[0]);
        }

        return bonusCommissionHistory;
    }

    /**
     * Finder.
     *
     * @param participatingAgentPK
     * @param commissionHistoryPK
     * @return
     */
    public static final BonusCommissionHistory findBy_ParticipatingAgentPK_CommissionHistoryPK(Long participatingAgentPK, Long commissionHistoryPK)
    {
        BonusCommissionHistory bonusCommissionHistory = null;

        String hql = "select bonusCommissionHistory from BonusCommissionHistory bonusCommissionHistory " +
                      " join bonusCommissionHistory.ParticipatingAgent participatingAgent " +
                      " join bonusCommissionHistory.CommissionHistory commissionHistory " +
                      " where participatingAgent.ParticipatingAgentPK = :participatingAgentPK" +
                      " and commissionHistory.CommissionHistoryPK = :commissionHistoryPK";

        Map params = new HashMap();

        params.put("participatingAgentPK", participatingAgentPK);
        params.put("commissionHistoryPK", commissionHistoryPK);

        List results = SessionHelper.executeHQL(hql, params, BonusCommissionHistory.DATABASE);

        if (!results.isEmpty())
        {
            bonusCommissionHistory = (BonusCommissionHistory) results.get(0);
        }

        return bonusCommissionHistory;
    }

    /**
     * Finder.
     *
     * @param participatingAgentPK
     * @return
     */
    public static final BonusCommissionHistory[] findBy_ParticipatingAgentPK(long participatingAgentPK)
    {
        return (BonusCommissionHistory[]) CRUDEntityImpl.mapVOToEntity(new BonusCommissionHistoryDAO().findBy_ParticipatingAgentPK(participatingAgentPK), BonusCommissionHistory.class);
    }

    /**
     * Finder.
     *
     * @param participatingAgentPK
     * @param contractCodeCT
     * @param trxTypeCT
     * @return
     */
    public static BonusCommissionHistory[] findBy_ParticipatingAgent_PremiumLevel_LastCheckDateTime_LastStatementDateTime_TrxTypeCT(ParticipatingAgent participatingAgent, PremiumLevel premiumLevel, String trxTypeCT)
    {
        BonusCommissionHistory[] bonusCommissionHistories = null;

        String hql = "select bonusCommissionHistory from BonusCommissionHistory bonusCommissionHistory " +
                      " join  bonusCommissionHistory.AppliedPremiumLevels appliedPremiumLevel " +
                      " join fetch bonusCommissionHistory.ParticipatingAgent participatingAgent " +
                      " join fetch bonusCommissionHistory.CommissionHistory commissionHistory " +
                      " join fetch commissionHistory.EDITTrxHistory editTrxHistory " +
                     " join fetch editTrxHistory.FinancialHistories " +
                      " join fetch editTrxHistory.EDITTrx editTrx " +
                      " join fetch editTrx.ClientSetup clientSetup " +
                      " join fetch clientSetup.ContractSetup contractSetup " +
                      " join fetch contractSetup.Segment segment " +
                      " where editTrx.TransactionTypeCT = :trxType " +
                      " and appliedPremiumLevel.PremiumLevel = :premiumLevel" +
                      " and participatingAgent = :participatingAgent" +
                      " and (bonusCommissionHistory.BonusUpdateDateTime > participatingAgent.LastStatementDateTime " +
                      " or participatingAgent.LastStatementDateTime is null) " +
                      " and (bonusCommissionHistory.BonusUpdateDateTime <= participatingAgent.LastCheckDateTime) " +
                      " and bonusCommissionHistory.BonusUpdateStatus = :status";

        Map params = new HashMap();
        String status = "B";

        params.put("trxType", trxTypeCT);
        params.put("premiumLevel", premiumLevel);
        params.put("participatingAgent", participatingAgent);
        params.put("status", status);

        List results = SessionHelper.executeHQL(hql, params, BonusCommissionHistory.DATABASE);

        if (!results.isEmpty())
        {

        }
        return (BonusCommissionHistory[]) results.toArray(new BonusCommissionHistory[results.size()]);
    }


    /**
     * Th associated CommissionHistory.
     *
     * @return
     */
    public CommissionHistory get_CommissionHistory()
    {
        if (commissionHistory == null)
        {
            commissionHistory = CommissionHistory.findByPK_UsingCRUD(this.bonusCommissionHistoryVO.getCommissionHistoryFK());
//            commissionHistory = CommissionHistory.findByPK(new Long(this.bonusCommissionHistoryVO.getCommissionHistoryFK()));
        }

        return commissionHistory;
    }

    /**
     * Sets the associated ParticipatingAgent.
     *
     * @param participatingAgent
     */
    public void setParticipatingAgent(ParticipatingAgent participatingAgent)
    {
        this.participatingAgent = participatingAgent;
    }

    /**
     * Returns the associated ParticipatingAgent.
     *
     * @return
     */
    public ParticipatingAgent getParticipatingAgent()
    {
        return participatingAgent;
    }

    /**
     * Setter.
     *
     * @param accountingPendingStatus
     */
    public void setAccountingPendingStatus(String accountingPendingStatus)
    {
        this.bonusCommissionHistoryVO.setAccountingPendingStatus(accountingPendingStatus);
    }

    /**
     * Get the "BCK" trx types for statement earnings
     * @param participatingAgent
     * @param trxTypeCT
     * @return
     */
    public static BonusCommissionHistory[] findAllBy_ParticipatingAgent_TrxTypeCT(ParticipatingAgent participatingAgent, String trxTypeCT)
    {
        String hql = " select bonusCommissionHistory from BonusCommissionHistory bonusCommissionHistory " +
                        " join bonusCommissionHistory.ParticipatingAgent participatingAgent " +
                        " join fetch bonusCommissionHistory.CommissionHistory commissionHistory " +
                        " join fetch commissionHistory.EDITTrxHistory editTrxHistory " +
                        " join fetch editTrxHistory.EDITTrx editTrx " +
                        " where editTrx.TransactionTypeCT = :trxType " +
                        " and participatingAgent = :participatingAgent" +
                        " and (bonusCommissionHistory.BonusUpdateDateTime >= participatingAgent.LastStatementDateTime " +
                        " or participatingAgent.LastStatementDateTime is null) " +
                        " and (bonusCommissionHistory.BonusUpdateDateTime <= participatingAgent.LastCheckDateTime)";

        Map params = new HashMap();

        params.put("trxType", trxTypeCT);
        params.put("participatingAgent", participatingAgent);

        List results = SessionHelper.executeHQL(hql, params, BonusCommissionHistory.DATABASE);

        return (BonusCommissionHistory[])results.toArray(new BonusCommissionHistory[results.size()]);
    }

    public static BonusCommissionHistory[] findBy_PremiumLevel_ProductStructure(PremiumLevel premiumLevel, Long productStructurePK, ParticipatingAgent participatingAgent)
    {
        String hql = " select bonusCommissionHistory from BonusCommissionHistory bonusCommissionHistory " +
                      " join bonusCommissionHistory.AppliedPremiumLevels appliedPremiumLevel " +
                      " join appliedPremiumLevel.PremiumLevel premiumLevel " +
                      " join bonusCommissionHistory.ParticipatingAgent participatingAgent " +
                      " join fetch bonusCommissionHistory.CommissionHistory commissionHistory " +
                      " join fetch commissionHistory.EDITTrxHistory editTrxHistory " +
                      " join fetch editTrxHistory.EDITTrx editTrx " +
                      " join fetch editTrxHistory.FinancialHistories " +
                      " join editTrx.ClientSetup clientSetup " +
                      " join clientSetup.ContractSetup contractSetup " +
                      " join contractSetup.Segment segment " +
//                      " where editTrx.TransactionTypeCT = :trxType " +
                      " where premiumLevel = :premiumLevel" +
                      " and segment.ProductStructureFK = :productStructurePK " +
                      " and bonusCommissionHistory.BonusUpdateStatus in ('B', 'C')" +
                      " and participatingAgent = :participatingAgent" +
                      " and (bonusCommissionHistory.BonusUpdateDateTime > participatingAgent.LastStatementDateTime " +
                      " or participatingAgent.LastStatementDateTime is null) " +
                      " and (bonusCommissionHistory.BonusUpdateDateTime <= participatingAgent.LastCheckDateTime)";

        Map params = new HashMap();

//        params.put("trxType", "PY");
        params.put("premiumLevel", premiumLevel);
        params.put("productStructurePK", productStructurePK);
        params.put("participatingAgent", participatingAgent);

        List results = SessionHelper.executeHQL(hql, params, BonusCommissionHistory.DATABASE);

        return (BonusCommissionHistory[])results.toArray(new BonusCommissionHistory[results.size()]);
    }

    public static BonusCommissionHistory[] findByBonusCommissionHistoryPK(Long bonusCommissionHistoryPK)
    {
        String hql = "select bch from BonusCommissionHistory bch where bch.BonusCommissionHistoryPK =: bonusCommissionHistoryPK";

        Map params = new HashMap();

        params.put("bonusCommissionHistoryPK", bonusCommissionHistoryPK);

        List results = SessionHelper.executeHQL(hql, params, BonusCommissionHistory.DATABASE);

        return (BonusCommissionHistory[]) results.toArray(new BonusCommissionHistory[results.size()]);
    }

    /**
     * Finder.
     *
     * @param BonusCommissionHistory
     */
    public static final BonusCommissionHistory findByPK_UsingHibernate(Long bonusCommissionHistoryPK)
    {
        return (BonusCommissionHistory) SessionHelper.get(BonusCommissionHistory.class, bonusCommissionHistoryPK, BonusCommissionHistory.DATABASE);
    }

  public void setAmount(EDITBigDecimal amount)
  {
    this.bonusCommissionHistoryVO.setAmount(SessionHelper.getEDITBigDecimal(amount));
  }

  public EDITBigDecimal getAmount()
  {
    return SessionHelper.getEDITBigDecimal(this.bonusCommissionHistoryVO.getAmount());
  }

  public void setTransactionTypeCT(String transactionTypeCT)
  {
    this.bonusCommissionHistoryVO.setTransactionTypeCT(transactionTypeCT);
  }

  public String getTransactionTypeCT()
  {
    return this.bonusCommissionHistoryVO.getTransactionTypeCT();
  }

  public void setUpdateReason(String updateReason)
  {
    this.bonusCommissionHistoryVO.setUpdateReason(updateReason);
  }

  public String getUpdateReason()
  {
    return this.bonusCommissionHistoryVO.getUpdateReason();
  }

  /**
   * This BonusCommissionHistory (BCH) needs to be either updated, or have an 
   * offsetting BonusCommissionHistory created as it participates in the 
   * Agent Bonus Commission process. The rules are as follows:
   * 1> If the bonusProcess is "Normal":
   * 1.a> If the BCH.TransactionTypeCT = 'PY'
   * 1.a.i>  Change the UpdateReason to BCH.TransactionType + 'N'
   * 1.a.ii> Create an offsetting BCH with the UpdateReason an a negated BCH.Amount.
   * 
   * 1.b> If the BCH.TransactionTypeCT != 'PY', then update the BCH.UpdateReason.
   * 
   * 2> If the bonusProcess is "Reversal":
   * 2.a> If the BCH.TransactionTypeCT != the specified bonusChargebackTransactionTypeCT, then update BCH.UpdateReason
   * 
   * 2.b> If the BCH.TransactionTypeCT = the specified bonusChargebackTransactionTypeCT, then
   * 2.b.i> If the BCH.BonusUpdateStatus == null, then delete the BCH.
   * 2.b.ii> If the BCH.BonusUpdateStatus != null, then
   * 2.b.ii.1> Update the BCH.UpdateReason
   * 2.b.ii.2> Create an offsetting BCH with the UpdateReason nd a negated BCH.Amount.
   * 
   * @param bonusProcess normal or reversal
   * @param bonusChargebackTransactionPriority the driving transactionTypeCT
   * @see AgentBonusContributionStrategy#PROCESSING_REVERSAL
   * @see AgentBonusContributionStrategy#PROCESSING_NORMAL
   */
  public void updateForNotTakenFullSurrender(String bonusProcess, TransactionPriority bonusChargebackTransactionPriority)
  {
    String bonusChargebackTransactionTypeCT = bonusChargebackTransactionPriority.getTransactionTypeCT();
  
    if (!validForFirstYearOnly(bonusChargebackTransactionPriority, bonusProcess))
    {
      return;
    }
  
    String updateReason = getUpdateReason(bonusChargebackTransactionTypeCT, bonusProcess);

    if (bonusProcess.equals(AgentBonusContributionStrategy.PROCESSING_NORMAL))            
    {
      if (getTransactionTypeCT().equals(EDITTrx.TRANSACTIONTYPECT_PREMIUM))
      {
        setUpdateReason(updateReason);

        createOffsettingBonusCommissionHistory(updateReason, bonusChargebackTransactionPriority.getTransactionTypeCT(), bonusProcess);
      }
      else
      {
        setUpdateReason(updateReason);        
      }
    }
    else if (bonusProcess.equals(AgentBonusContributionStrategy.PROCESSING_REVERSAL))
    {
      if (getTransactionTypeCT().equals(EDITTrx.TRANSACTIONTYPECT_PREMIUM))
      {
        setUpdateReason(updateReason);
      }
      else if (getTransactionTypeCT().equals(bonusChargebackTransactionTypeCT))
      {
        if (getBonusUpdateStatus() == null)
        {
          hDelete();
        }
        else
        {
          setUpdateReason(updateReason);

          createOffsettingBonusCommissionHistory(updateReason, bonusChargebackTransactionPriority.getTransactionTypeCT(), bonusProcess);
        }
      }
    }
    
    updateBonusUpdateDateTime(); // This record has been modified.
  }

  /**
   * Creates an offsetting BonusCommissionHistory with a negated 
   * BonusCommissionHistory.Amount and the specified updateReason.
   * @param updateReason the reason the BonusCommissionHistory is being created (e.g. NTR or NTN)
   * @param transactionTypeCT the type of transaction the is creating this offsetting BonusCommissionHistory
   */
  private void createOffsettingBonusCommissionHistory(String updateReason, String transactionTypeCT, String bonusProcess)
  {
    BonusCommissionHistory offsettingBonusCommissionHistory = (BonusCommissionHistory) SessionHelper.shallowCopy(this, BonusCommissionHistory.DATABASE);
    
    EDITBigDecimal offsetAmount = new EDITBigDecimal(getAmount().toString()).negate();
    
    offsettingBonusCommissionHistory.setAmount(offsetAmount);
    
    offsettingBonusCommissionHistory.setUpdateReason(updateReason);

    offsettingBonusCommissionHistory.setTransactionTypeCT(transactionTypeCT);
    
    offsettingBonusCommissionHistory.setBonusUpdateStatus(null);
    
    offsettingBonusCommissionHistory.updateBonusUpdateDateTime();
    
    CommissionHistory ntfsCommissionHistory = null;
    
    if (bonusProcess.equals(AgentBonusContributionStrategy.PROCESSING_NORMAL))
    {
      ntfsCommissionHistory = new AgentBonusContributionStrategy(getParticipatingAgent().getBonusProgram(), getParticipatingAgent()).findCommissionHistoryForNormal_NTFS_Processing(this, transactionTypeCT);
    }
    else if (bonusProcess.equals(AgentBonusContributionStrategy.PROCESSING_REVERSAL))
    {
      ntfsCommissionHistory = new AgentBonusContributionStrategy(getParticipatingAgent().getBonusProgram(), getParticipatingAgent()).findCommissionHistoryForReversal_NTFS_Processing(this, transactionTypeCT);
    }
    
    ntfsCommissionHistory.add(offsettingBonusCommissionHistory);
    
    getParticipatingAgent().add(offsettingBonusCommissionHistory);
  }
  
  /**
   * Convenience method that concatenates the specified transactionTypeCT with
   * a "N" or a "R" depending on the specified bonusProcess of "N"ormal or 
   * "R"eversal. 
   * @param transactionTypeCT
   * @param bonusProcess
   * @return
   */
  public static String getUpdateReason(String transactionTypeCT, String bonusProcess)
  {
    String updateReason = null;
    
    if (bonusProcess.equals(AgentBonusContributionStrategy.PROCESSING_NORMAL))
    {
      updateReason = transactionTypeCT + "N";  
    }
    else if (bonusProcess.equals(AgentBonusContributionStrategy.PROCESSING_REVERSAL))
    {
      updateReason = transactionTypeCT + "R";
    }
    
    return updateReason;
  }

  /**
   * A chargeback for First Year Only (FYO) must have its associated
   * EDITTrx.EffectiveDate < Segment.EffectiveDate + 365.
   * @param bonusChargebackTransactionPriority
   * @return true of the EDITTrx.EffectiveDate for this BonusCommissionHistory is valid
   */
  private boolean validForFirstYearOnly(TransactionPriority bonusChargebackTransactionPriority, String bonusProcess)
  {
    boolean validForFirstYearOnly;
    
    if (bonusChargebackTransactionPriority.getBonusChargebackCT().equals(TransactionPriority.BONUS_CHARGEBACK_FIRSTYEARONLY))
    {
      String trxTypeCT = bonusChargebackTransactionPriority.getTransactionTypeCT();
    
      // Need to CommissionHistory of the original NT or FS transaction. This takes some work.
      CommissionHistory ntfsCommissionHistory = null;
      
      if (bonusProcess.equals(AgentBonusContributionStrategy.PROCESSING_NORMAL))
      {
        ntfsCommissionHistory = new AgentBonusContributionStrategy(getParticipatingAgent().getBonusProgram(), getParticipatingAgent()).findCommissionHistoryForNormal_NTFS_Processing(this, trxTypeCT);
      }
      else if (bonusProcess.equals(AgentBonusContributionStrategy.PROCESSING_REVERSAL))
      {
        ntfsCommissionHistory = new AgentBonusContributionStrategy(getParticipatingAgent().getBonusProgram(), getParticipatingAgent()).findCommissionHistoryForReversal_NTFS_Processing(this, trxTypeCT);
      }
    
      EDITTrx editTrx = EDITTrx.findBy_CommissionHistory(ntfsCommissionHistory);
      
      Segment segment = Segment.findBy_EDITTrx(editTrx);
      
      EDITDate editTrxEffectiveDate = editTrx.getEffectiveDate();
      
      EDITDate segmentEffectiveDate = segment.getEffectiveDate();
      
      EDITDate comparatorEffectiveDate = segmentEffectiveDate.addDays(365);
      
      validForFirstYearOnly = editTrxEffectiveDate.before(comparatorEffectiveDate);
    }
    else
    {
      validForFirstYearOnly = true;
    }
    
    return validForFirstYearOnly;
  }
  
  /**
   * Builds a Hibernate-aware entity using the specified parameters with the UpdateDateTime stamped.
   * @return
   */
  public static BonusCommissionHistory build(EDITBigDecimal amount, String transactionTypeCT, String accountingPendingStatus)
  {
    BonusCommissionHistory bonusCommissionHistory = 
      (BonusCommissionHistory) SessionHelper.newInstance(BonusCommissionHistory.class, BonusCommissionHistory.DATABASE);
      
      bonusCommissionHistory.setAmount(amount);
      
      bonusCommissionHistory.setTransactionTypeCT(transactionTypeCT);

      bonusCommissionHistory.setAccountingPendingStatus(accountingPendingStatus);
      
      bonusCommissionHistory.updateBonusUpdateDateTime();
      
      return bonusCommissionHistory;
  }

  /**
   * Removes this association with its parent ParticiatingAgent and parent
   * CommissionHistory.
   */
  public void hDelete()
  {
    getParticipatingAgent().remove(this);
    
    getCommissionHistory().remove(this);
    
    SessionHelper.delete(this, BonusCommissionHistory.DATABASE);
  }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return BonusCommissionHistory.DATABASE;
    }
}
