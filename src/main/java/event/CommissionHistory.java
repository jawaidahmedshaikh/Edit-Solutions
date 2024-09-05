/*
 * User: gfrosti
 * Date: Mar 1, 2005
 * Time: 4:21:56 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import agent.PlacedAgent;
import contract.AgentHierarchyAllocation;
import contract.AgentSnapshot;
import edit.common.*;
import edit.common.vo.CommissionHistoryVO;
import edit.common.vo.VOObject;
import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.*;
import event.dm.dao.CommissionHistoryDAO;

import java.util.*;

import org.hibernate.Session;

import staging.IStaging;
import staging.StagingContext;
import staging.FinancialActivity;
import staging.CommissionActivity;
import role.ClientRole;


public class CommissionHistory extends HibernateEntity implements CRUDEntity, IStaging
{
    public static final String COMMISSIONTYPECT_CHARGEBACK = "ChargeBack";
    public static final String COMMISSIONTYPECT_BONUSCHECK = "BonusCheck";
    public static final String COMMISSIONTYPECT_ADJUSTMENT = "Adjustment";
    public static final String COMMISSIONTYPECT_COMM_ADJ = "Comm Adj";
    public static final String COMMISSIONTYPECT_ADVANCE = "Advance";
    public static final String COMMISSIONTYPECT_ADVANCE_ADJUSTMENT = "AdvanceAdjustment";
    public static final String COMMISSIONTYPECT_ADVANCE_RECOVERY = "AdvanceRecovery";
    public static final String COMMISSIONTYPECT_ADVANCE_CHARGEBACK = "AdvanceChargeback";
    public static final String COMMISSIONTYPECT_ADVANCE_CHARGEBACK_REVERSAL = "AdvChargeBackRev";
    public static final String COMMISSIONTYPECT_RECOVERY_CHARGEBACK = "RecoveryChargeback";
    public static final String COMMISSIONTYPECT_NEGATIVE_EARNINGS = "NegativeEarnings";
    public static final String COMMISSIONTYPECT_CHARGEBACK_REVERSAL = "ChargeBackRev";
    public static final String COMMISSIONTYPECT_FIRST_YEAR = "FirstYear";
    public static final String COMMISSIONTYPECT_FIRST_YEAR_NEG_EARN = "FYNegEarn";
    public static final String COMMISSIONTYPECT_RENEWAL = "Renewal";
    public static final String COMMISSIONTYPECT_RNWL_NEG_EARN = "RNWLNegEarn";
	public static final String COMMISSIONTYPECT_CHARGEBACK_REV = "ChargeBackRev";
    public static final String COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK = "TermAdvChargeBack";
    public static final String COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK_REVERSAL = "TermAdvChargeBackRev";

    public static final String GROUPTYPECT_GROUP = "Group";
    public static final String GROUPTYPECT_INDIVIDUAL = "Individual";

    public static final String BONUSUPDATESTATUS_YES = "Y";

    public static final String UPDATESTATUS_D = "D";
    public static final String UPDATESTATUS_L = "L";
    public static final String UPDATESTATUS_U = "U";

    private CRUDEntityImpl crudEntityImpl;
    private CommissionHistoryVO commissionHistoryVO;
    private EDITTrxHistory editTrxHistory;
    private Set commissionInvestmentHistories;
    private Set bonusCommissionHistories;
    private PlacedAgent placedAgent;
    private AgentSnapshot agentSnapshot;
    
    /**
     * The original writing agent for this CommissionHistory.
     */
    private PlacedAgent sourcePlacedAgent;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;



    /**
     * Instantiates a CommissionHistory entity with a default CommissionHistoryVO.
     */
    public CommissionHistory()
    {
        init();
    }

    public CommissionHistory(EDITTrxHistory editTrxHistory)
    {
        init();

        this.commissionHistoryVO.setEDITTrxHistoryFK(editTrxHistory.getPK());
    }

    /**
     * Instantiates a CommissionHistory entity with a supplied CommissionHistoryVO.
     *
     * @param commissionHistoryVO
     */
    public CommissionHistory(CommissionHistoryVO commissionHistoryVO)
    {
        init();

        this.commissionHistoryVO = commissionHistoryVO;
    }

    public Set getBonusCommissionHistories()
    {
        return bonusCommissionHistories;
    }

    public void setBonusCommissionHistories(Set bonusCommissionHistories)
    {
        this.bonusCommissionHistories = bonusCommissionHistories;
    }

    public Long getEDITTrxHistoryFK()
    {
        return SessionHelper.getPKValue(commissionHistoryVO.getEDITTrxHistoryFK());
    }

    //-- long getEDITTrxHistoryFK()
    public void setEDITTrxHistoryFK(Long EDITTrxHistoryFK)
    {
        commissionHistoryVO.setEDITTrxHistoryFK(SessionHelper.getPKValue(EDITTrxHistoryFK));
    }

    //-- void setEDITTrxHistoryFK(long)
    public void setEDITTrxHistory(EDITTrxHistory editTrxHistory)
    {
        this.editTrxHistory = editTrxHistory;
    }

    public void setCommissionHistoryPK(Long commissionHistoryPK)
    {
        this.commissionHistoryVO.setCommissionHistoryPK(SessionHelper.getPKValue(commissionHistoryPK));
    }

    public Long getCommissionHistoryPK()
    {
        return SessionHelper.getPKValue(commissionHistoryVO.getCommissionHistoryPK());
    }

    public EDITBigDecimal getADAAmount()
    {
        return SessionHelper.getEDITBigDecimal(commissionHistoryVO.getADAAmount());
    }

    //-- java.math.BigDecimal getADAAmount() 
    public String getAccountingPendingStatus()
    {
        return commissionHistoryVO.getAccountingPendingStatus();
    }

    //-- java.lang.String getAccountingPendingStatus() 
    public EDITBigDecimal getCommissionNonTaxable()
    {
        return SessionHelper.getEDITBigDecimal(commissionHistoryVO.getCommissionNonTaxable());
    }

    //-- java.math.BigDecimal getCommissionNonTaxable() 
    public EDITBigDecimal getCommissionRate()
    {
        return SessionHelper.getEDITBigDecimal(commissionHistoryVO.getCommissionRate());
    }

    //-- java.math.BigDecimal getCommissionRate() 
    public EDITBigDecimal getCommissionTaxable()
    {
        return SessionHelper.getEDITBigDecimal(commissionHistoryVO.getCommissionTaxable());
    }

    //-- java.math.BigDecimal getCommissionTaxable() 
    public EDITBigDecimal getExpenseAmount()
    {
        return SessionHelper.getEDITBigDecimal(commissionHistoryVO.getExpenseAmount());
    }

    public EDITDateTime getMaintDateTime()
    {
        return SessionHelper.getEDITDateTime(commissionHistoryVO.getMaintDateTime());
    }

    public String getOperator()
    {
        return commissionHistoryVO.getOperator();
    }

    //-- java.lang.String getOperator() 
    public String getReduceTaxable()
    {
        return commissionHistoryVO.getReduceTaxable();
    }

    //-- java.lang.String getReduceTaxable() 
    public String getStatementInd()
    {
        return commissionHistoryVO.getStatementInd();
    }

    //-- java.lang.String getStatementInd() 
    public String getUndoRedoStatus()
    {
        return commissionHistoryVO.getUndoRedoStatus();
    }

    public EDITDateTime getUpdateDateTime()
    {
        return SessionHelper.getEDITDateTime(commissionHistoryVO.getUpdateDateTime());
    }

    //-- java.lang.String getUpdateDateTime() 
    public String getUpdateStatus()
    {
        return commissionHistoryVO.getUpdateStatus();
    }

    //-- java.lang.String getUpdateStatus() 
    public void setADAAmount(EDITBigDecimal ADAAmount)
    {
        commissionHistoryVO.setADAAmount(SessionHelper.getEDITBigDecimal(ADAAmount));
    }

    //-- void setADAAmount(java.math.BigDecimal) 
    public void setCommissionNonTaxable(EDITBigDecimal commissionNonTaxable)
    {
        commissionHistoryVO.setCommissionNonTaxable(SessionHelper.getEDITBigDecimal(commissionNonTaxable));
    }

    //-- void setCommissionNonTaxable(java.math.BigDecimal) 
    public void setCommissionRate(EDITBigDecimal commissionRate)
    {
        commissionHistoryVO.setCommissionRate(SessionHelper.getEDITBigDecimal(commissionRate));
    }

    //-- void setCommissionRate(java.math.BigDecimal) 
    public void setCommissionTaxable(EDITBigDecimal commissionTaxable)
    {
        commissionHistoryVO.setCommissionTaxable(SessionHelper.getEDITBigDecimal(commissionTaxable));
    }

    //-- void setCommissionTaxable(java.math.BigDecimal) 
    public void setExpenseAmount(EDITBigDecimal expenseAmount)
    {
        commissionHistoryVO.setExpenseAmount(SessionHelper.getEDITBigDecimal(expenseAmount));
    }

    //-- void setExpenseAmount(java.math.BigDecimal) 
    public void setReduceTaxable(String reduceTaxable)
    {
        commissionHistoryVO.setReduceTaxable(reduceTaxable);
    }

    //-- void setReduceTaxable(java.lang.String) 
    public void setUndoRedoStatus(String undoRedoStatus)
    {
        commissionHistoryVO.setUndoRedoStatus(undoRedoStatus);
    }

    public void setUpdateDateTime(EDITDateTime updateDateTime)
    {
        commissionHistoryVO.setUpdateDateTime(SessionHelper.getEDITDateTime(updateDateTime));
    }

    //-- void setUpdateDateTime(java.lang.String)

    public Long getCheckTo()
    {
        return SessionHelper.getPKValue(commissionHistoryVO.getCheckTo());
    }

    public void setCheckTo(Long checkTo)
    {
        commissionHistoryVO.setCheckTo(SessionHelper.getPKValue(checkTo));
    }

    public String getGroupTypeCT()
    {
        return commissionHistoryVO.getGroupTypeCT();
    }
    
    public void setGroupTypeCT(String groupTypeCT)
    {
        commissionHistoryVO.setGroupTypeCT(groupTypeCT);
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (commissionHistoryVO == null)
        {
            commissionHistoryVO = new CommissionHistoryVO();
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
        return commissionHistoryVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return commissionHistoryVO.getCommissionHistoryPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.commissionHistoryVO = (CommissionHistoryVO) voObject;
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
     * @param commissionHistoryPK
     */
    public static final CommissionHistory findByPK_UsingCRUD(long commissionHistoryPK)

    {
        CommissionHistory commissionHistory = null;

        CommissionHistoryVO[] commissionHistoryVOs = new CommissionHistoryDAO().findByPK(commissionHistoryPK);

        if (commissionHistoryVOs != null)
        {
            commissionHistory = new CommissionHistory(commissionHistoryVOs[0]);
        }

        return commissionHistory;
    }

    /**
     * Finder.
     * @param placedAgentPK
     * @param bonusUpdateStatus
     * @return
     */
    public static final CommissionHistory[] findBy_PlacedAgentPK_AND_BonusUpdateStatus(long placedAgentPK, String bonusUpdateStatus)
    {
        CommissionHistoryVO[] commissionHistoryVOs = new CommissionHistoryDAO().findByPlacedAgentPKAndBonusUpdateStatus(placedAgentPK, bonusUpdateStatus);

        return (CommissionHistory[]) CRUDEntityImpl.mapVOToEntity(commissionHistoryVOs, CommissionHistory.class);
    }

    /**
     * True if the CommissionTypeCT = "ChargeBack".
     * @return
     */
    public boolean isChargeBack()
    {
        boolean isChargeBack = false;

        if (getCommissionTypeCT().equals(CommissionHistory.COMMISSIONTYPECT_CHARGEBACK) ||
            getCommissionTypeCT().equals(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK))
        {
            isChargeBack = true;
        }

        return isChargeBack;
    }

    /**
     * Getter.
     * @return
     */
    public String getCommissionTypeCT()
    {
        return commissionHistoryVO.getCommissionTypeCT();
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCommissionAmount()
    {
        return SessionHelper.getEDITBigDecimal(commissionHistoryVO.getCommissionAmount());
    }

    public EDITBigDecimal getDebitBalanceAmount()
    {
        return SessionHelper.getEDITBigDecimal(commissionHistoryVO.getDebitBalanceAmount());
    } //-- java.math.BigDecimal getDebitBalanceAmount()

    /**
     * Getter
     * @return
     */
    public String getIncludedInDebitBalInd()
    {
        return commissionHistoryVO.getIncludedInDebitBalInd();
    } //-- java.lang.String getIncludedInDebitBalInd()


    /**
     * The associated FinancialHistory.
     * @return
     */
    public FinancialHistory getFinancialHistory()
    {
        return getEDITTrxHistory().getFinancialHistory();
    }

    /**
     * Getter.
     * @return
     */
    public EDITTrxHistory getEDITTrxHistory()
    {
        return editTrxHistory;
    }

    /**
     * Setter.
     * @param commissionAmount
     */
    public void setCommissionAmount(EDITBigDecimal commissionAmount)
    {
        this.commissionHistoryVO.setCommissionAmount(SessionHelper.getEDITBigDecimal(commissionAmount));
    }
 
    /**
     * Setter.
     * @param updateStatus
     */
    public void setUpdateStatus(String updateStatus)
    {
        this.commissionHistoryVO.setUpdateStatus(updateStatus);
    }

    /**
     * Setter.
     * @param accountingPendingStatus
     */
    public void setAccountingPendingStatus(String accountingPendingStatus)
    {
        this.commissionHistoryVO.setAccountingPendingStatus(accountingPendingStatus);
    }

    /**
     * Setter.
     * @param commissionTypeCT
     */
    public void setCommissionTypeCT(String commissionTypeCT)
    {
        this.commissionHistoryVO.setCommissionTypeCT(commissionTypeCT);
    }

    /**
     * Setter.
     * @param statementInd
     */
    public void setStatementInd(String statementInd)
    {
        this.commissionHistoryVO.setStatementInd(statementInd);
    }

    /**
     * Setter
     * @param debitBalanceAmount
     */
    public void setDebitBalanceAmount(EDITBigDecimal debitBalanceAmount)
    {
        commissionHistoryVO.setDebitBalanceAmount(SessionHelper.getEDITBigDecimal(debitBalanceAmount));
    }

    /**
     * Setter
     * @param includedInDebitBalInd
     */
    public void setIncludedInDebitBalInd(String includedInDebitBalInd)
    {
        commissionHistoryVO.setIncludedInDebitBalInd(includedInDebitBalInd);
    } //-- void setIncludedInDebitBalInd(java.lang.String)

    /**
     * The associated PlacedAgent.
     * @return
     */
    public PlacedAgent get_PlacedAgent()
    {
        return new PlacedAgent(commissionHistoryVO.getPlacedAgentFK());
    }

    /**
     * Getter.
     * @return
     */
    public PlacedAgent getPlacedAgent()
    {
        return placedAgent;
    }

    /**
     * Setter.
     * @param placedAgent
     */
    public void setPlacedAgent(PlacedAgent placedAgent)
    {
        this.placedAgent = placedAgent;
    }

    /**
     * The associated EDITTrxHistory
     * @return
     */
    public EDITTrxHistory get_EDITTrxHistory()
    {
        editTrxHistory = EDITTrxHistory.findByPK(this.commissionHistoryVO.getEDITTrxHistoryFK());

        return editTrxHistory;
    }



    /**
     * Associates the PlacedAgent.
     * @param placedAgent
     */
    public void associate(PlacedAgent placedAgent)
    {
        this.commissionHistoryVO.setPlacedAgentFK(placedAgent.getPK());
    }

    /**
     * Setter.
     * @param operator
     */
    public void setOperator(String operator)
    {
        this.commissionHistoryVO.setOperator(operator);
    }

    /**
     * Setter.
     * @param editDateTime
     */
    public void setMaintDateTime(EDITDateTime editDateTime)
    {
    	if(editDateTime == null) {
    		this.commissionHistoryVO.setMaintDateTime(null);
    	} else {
    		this.commissionHistoryVO.setMaintDateTime(editDateTime.getFormattedDateTime());
    	}
    }

    /**
     * Getter
     * @return  set of commissionInvestmentHistories
     */
    public Set getCommissionInvestmentHistories()
    {
        return commissionInvestmentHistories;
    }

    /**
     * Setter
     * @param commissionInvestmentHistories      set of commissionInvestmentHistories
     */
    public void setCommissionInvestmentHistories(Set commissionInvestmentHistories)
    {
        this.commissionInvestmentHistories = commissionInvestmentHistories;
    }

    /**
     * Adds a CommissionInvestmentHistory to the set of children
     * @param commissionInvestmentHistory
     */
    public void addCommissionInvestmentHistory(CommissionInvestmentHistory commissionInvestmentHistory)
    {
        this.getCommissionInvestmentHistories().add(commissionInvestmentHistory);

        commissionInvestmentHistory.setCommissionHistory(this);

        SessionHelper.saveOrUpdate(commissionInvestmentHistory, CommissionHistory.DATABASE);
    }

    /**
     * Removes a CommissionInvestmentHistory from the set of children
     * @param commissionInvestmentHistory
     */
    public void removeCommissionInvestmentHistory(CommissionInvestmentHistory commissionInvestmentHistory)
    {
        this.getCommissionInvestmentHistories().remove(commissionInvestmentHistory);

        commissionInvestmentHistory.setCommissionHistory(null);

        SessionHelper.saveOrUpdate(commissionInvestmentHistory, CommissionHistory.DATABASE);
    }


    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, CommissionHistory.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, CommissionHistory.DATABASE);
    }

    public void add(BonusCommissionHistory bonusCommissionHistory)
    {
        getBonusCommissionHistories().add(bonusCommissionHistory);

        bonusCommissionHistory.setCommissionHistory(this);
    }

    /**
     * Setter.
     * @param placedAgentFK
     */
    public void setPlacedAgentFK(Long placedAgentFK)
    {
        commissionHistoryVO.setPlacedAgentFK(SessionHelper.getPKValue(placedAgentFK));
    }

    /**
     * Getter.
     * @return
     */
    public Long getPlacedAgentFK()
    {
        return SessionHelper.getPKValue(commissionHistoryVO.getPlacedAgentFK());
    }

    /**
     * Setter.
     * @param sourcePlacedAgentFK
     */
    public void setSourcePlacedAgentFK(Long sourcePlacedAgentFK)
    {
        commissionHistoryVO.setSourcePlacedAgentFK(SessionHelper.getPKValue(sourcePlacedAgentFK));
    }

    /**
     * Getter.
     * @return
     */
    public Long getSourcePlacedAgentFK()
    {
        return SessionHelper.getPKValue(commissionHistoryVO.getSourcePlacedAgentFK());
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getCommHoldReleaseDate()
    {
        return SessionHelper.getEDITDate(commissionHistoryVO.getCommHoldReleaseDate());
    }

    /**
     * Setter.
     * @param commHoldReleaseDate
     */
    public void setCommHoldReleaseDate(EDITDate commHoldReleaseDate)
    {
        commissionHistoryVO.setCommHoldReleaseDate(SessionHelper.getEDITDate(commHoldReleaseDate));
    }

  /**
   * Removes the specified BonusCommissionHistory as a child entity of this
   * CommissionHistory. Additionally, the BonusCommissionHistory's reference
   * to this CommissionHistory is removed.
   * @param bonusCommissionHistory
   * 
   */
  public void remove(BonusCommissionHistory bonusCommissionHistory)
  {
    getBonusCommissionHistories().remove(bonusCommissionHistory);
    
    bonusCommissionHistory.setCommissionHistory(null);
  }

    /**
   * Setter.
   * @param agentSnapshotFK
   */
    public void setAgentSnapshotFK(Long agentSnapshotFK) {
        
      this.commissionHistoryVO.setAgentSnapshotFK(SessionHelper.getPKValue(agentSnapshotFK));        
    }
    
    /**
   * Getter.
   * @return
   */
    public Long getAgentSnapshotFK()
    {
      return SessionHelper.getPKValue(commissionHistoryVO.getAgentSnapshotFK());
    }

  /**
   * Setter.
   * @param agentSnapshot
   */
  public void setAgentSnapshot(AgentSnapshot agentSnapshot)
  {
    this.agentSnapshot = agentSnapshot;
  }

  /**
   * Getter.
   * @return
   */
  public AgentSnapshot getAgentSnapshot()
  {
    return agentSnapshot;
  }

  /**
   * Setter.
   * @param sourcePlacedAgent
   * @see #sourcePlacedAgent
   */
  public void setSourcePlacedAgent(PlacedAgent sourcePlacedAgent)
  {
    this.sourcePlacedAgent = sourcePlacedAgent;
  }

  /**
   * Getter.
   * @see #sourcePlacedAgent
   * @return
   */
  public PlacedAgent getSourcePlacedAgent()
  {
    return sourcePlacedAgent;
  }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return CommissionHistory.DATABASE;
    }

    /**
     * Retrieves CommissionHistory records for give segment and update status.
     * @param segmentPK
     * @param updateStatus
     * @return
     */
    public static final CommissionHistory[] findBySegmentPK_UpdateStatus(Long segmentPK, String updateStatus)
    {
        String hql = " select commissionHistory.* " +
                     " from CommissionHistory commissionHistory" +
                     " join commissionHistory.EDITTrxHistory editTrxHistory" +
                     " join editTrxHistory.EDITTrx editTrx" +
                     " join editTrx.ClientSetup clientSetup" +
                     " join clientSetup.ContractSetup contractSetup" +
                     " where contractSetup.SegmentFK = :segmentPK" +
                     " and commissionHistory.UpdateStatus = :updateStatus";

        Map params = new HashMap();

        params.put("segmentPK", segmentPK);
        params.put("updateStatus", updateStatus);

        List results = SessionHelper.executeHQL(hql, params, CommissionHistory.DATABASE);

        return (CommissionHistory[]) results.toArray(new CommissionHistory[results.size()]);
    }

	public static CommissionHistory[] findByPlacedAgentPKUpdateDateTime(long placedAgentPK, EDITDateTime lastCheckDateTime)
    {
        CommissionHistoryVO[] commissionHistoryVOs = new CommissionHistoryDAO().findByPlacedAgentPK_UpdateDateTime(placedAgentPK, lastCheckDateTime);

        return (CommissionHistory[]) CRUDEntityImpl.mapVOToEntity(commissionHistoryVOs, CommissionHistory.class);
	}

  /**
   * Finder.
   * @param agentSnapshotPK
   * @return
   */
  public static CommissionHistory[] findBy_AgentSnapshotPK(long agentSnapshotPK)
  {
    CommissionHistoryVO[] commissionHistoryVOs = new CommissionHistoryDAO().findBy_AgentSnapshotPK(agentSnapshotPK);
    
    return (CommissionHistory[]) CRUDEntityImpl.mapVOToEntity(commissionHistoryVOs, CommissionHistory.class);    
  }

  /**
   * Sums the CommissionHistory.CommissionAmount for the specified AgentSnapshot and specified TransactionTypeCTs across
   * all CommissionHistories associated with the AgentSnapshot. CommissionAmounts where the CommissionHistory.CommissionTypeCT is [not] "Chargeback" .
   * @param agentSnapshot
   * @param trxTypeCTs
   * @return
   */
  public static EDITBigDecimal findSeparateTotalPositiveCommissionsPaidBy_AgentSnapshot_CommissionableEvents(AgentSnapshot agentSnapshot, String[] trxTypeCTs)
  {
    EDITBigDecimal totalCommissionsPaid = new EDITBigDecimal("0.00");

    String hql = " select sum(commissionHistory.CommissionAmount)" +
                " from CommissionHistory commissionHistory" +
                " join commissionHistory.AgentSnapshot agentSnapshot" +
                " join commissionHistory.EDITTrxHistory editTrxHistory" +
                " join editTrxHistory.EDITTrx editTrx" +

                " where editTrx.TransactionTypeCT in (";

                for(int i = 0; i < trxTypeCTs.length; i++)
                {
                  hql += "'" + trxTypeCTs[i] + "'";

                  if (i < trxTypeCTs.length - 1)
                  {
                    hql += ", ";
                  }
                }

                hql += ")" +

                " and agentSnapshot = :agentSnapshot" +
                " and (commissionHistory.CommissionTypeCT <> :chgBackCommTypeCT" +
                " and commissionHistory.CommissionTypeCT <> :recoveryChgBackCommTypeCT" +
                " and commissionHistory.CommissionTypeCT <> :fyNegEarningCommTypeCT" +
                " and commissionHistory.CommissionTypeCT <> :RNWLNegEarnCommTypeCT" +
                " and commissionHistory.CommissionTypeCT <> :chgBackRevCommTypeCT" +
                " and commissionHistory.CommissionTypeCT <> :termAdvChgBackCommTypeCT" +
                " and commissionHistory.CommissionTypeCT <> :termAdvChgBackRevCommTypeCT" +
                " and commissionHistory.CommissionTypeCT <> :advanceChargeback)";

    EDITMap params = new EDITMap("agentSnapshot", agentSnapshot)
                      .put("chgBackCommTypeCT", COMMISSIONTYPECT_CHARGEBACK);

    params.put("recoveryChgBackCommTypeCT", COMMISSIONTYPECT_RECOVERY_CHARGEBACK);
    params.put("fyNegEarningCommTypeCT", COMMISSIONTYPECT_FIRST_YEAR_NEG_EARN);
    params.put("RNWLNegEarnCommTypeCT", COMMISSIONTYPECT_RNWL_NEG_EARN);
    params.put("chgBackRevCommTypeCT", COMMISSIONTYPECT_CHARGEBACK_REV);
    params.put("termAdvChgBackCommTypeCT", COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK);
    params.put("termAdvChgBackRevCommTypeCT", COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK_REVERSAL);
    params.put("advanceChargeback", COMMISSIONTYPECT_ADVANCE_CHARGEBACK);

    Session session = null;

      try
      {
        session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

        List<Double> results = SessionHelper.executeHQL(session, hql, params, 0);

        if (!results.isEmpty())
        {
          Object result = results.get(0);

          if (result != null)
          {
            totalCommissionsPaid = new EDITBigDecimal(result.toString());
          }
        }
      }
      finally
      {
        if (session != null) session.close();
      }

      return totalCommissionsPaid;
  }
  
  /**
   * Sums the CommissionHistory.CommissionAmount for the specified AgentSnapshot and specified TransactionTypeCTs across
   * all CommissionHistories associated with the AgentSnapshot. CommissionAmounts where the CommissionHistory.CommissionTypeCT [is] "Chargeback" OR "TermAdvChargbeck".
   * @param agentSnapshot
   * @param trxTypeCTs
   * @return
   */
  public static EDITBigDecimal findSeparateTotalNegativeCommissionsPaidBy_AgentSnapshot_CommissionableEvents(AgentSnapshot agentSnapshot, String[] trxTypeCTs)
  {
    EDITBigDecimal totalCommissionsPaid = new EDITBigDecimal("0.00");

    String hql = " select commissionHistory from CommissionHistory commissionHistory" +
                " join commissionHistory.AgentSnapshot agentSnapshot" +
                " join commissionHistory.EDITTrxHistory editTrxHistory" +
                " join editTrxHistory.EDITTrx editTrx" +

                " where editTrx.TransactionTypeCT in (";

                for(int i = 0; i < trxTypeCTs.length; i++)
                {
                  hql += "'" + trxTypeCTs[i] + "'";

                  if (i < trxTypeCTs.length - 1)
                  {
                    hql += ", ";
                  }
                }

                hql += ")" +

                " and agentSnapshot = :agentSnapshot" +
                " and (commissionHistory.CommissionTypeCT = :chargebackCommTypeCT" +
                " or commissionHistory.CommissionTypeCT = :termAdvChargebackCommTypeCT" +
                " or commissionHistory.CommissionTypeCT = :recoveryChgBackCommTypeCT" +
                " or commissionHistory.CommissionTypeCT = :fyNegEarningCommTypeCT" +
                " or commissionHistory.CommissionTypeCT = :RNWLNegEarnCommTypeCT" +
                " or commissionHistory.CommissionTypeCT = :advanceChargeback)";

    Map params = new HashMap();

    params.put("agentSnapshot", agentSnapshot);
    params.put("chargebackCommTypeCT", COMMISSIONTYPECT_CHARGEBACK);
    params.put("termAdvChargebackCommTypeCT", COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK);
    params.put("recoveryChgBackCommTypeCT", COMMISSIONTYPECT_RECOVERY_CHARGEBACK);
    params.put("fyNegEarningCommTypeCT", COMMISSIONTYPECT_FIRST_YEAR_NEG_EARN);
    params.put("RNWLNegEarnCommTypeCT", COMMISSIONTYPECT_RNWL_NEG_EARN);
    params.put("advanceChargeback", COMMISSIONTYPECT_ADVANCE_CHARGEBACK);

    Session session = null;

    try
    {
      session = SessionHelper.getSeparateSession(CommissionHistory.DATABASE);

        List results = SessionHelper.executeHQL(session, hql, params, 0);

        if (!results.isEmpty())
        {
            for (int i = 0; i < results.size(); i++)
            {
                CommissionHistory commissionHistory = (CommissionHistory) results.get(i);
                EDITTrxHistory editTrxHistory = commissionHistory.getEDITTrxHistory();
                EDITTrx editTrx = editTrxHistory.getEDITTrx();

                if (!editTrx.isRemovalTransaction() && !editTrx.isPartialRemovalTransaction())
                {
                    totalCommissionsPaid = totalCommissionsPaid.addEditBigDecimal(commissionHistory.getCommissionAmount());
                }
            }
        }
    }
    finally
    {
        if (session != null) session.close();
    }

    return totalCommissionsPaid;
  }

    /**
     * Sums the CommissionHistory.CommissionAmount for the specified AgentSnapshot and specified TransactionTypeCTs across
     * all CommissionHistories associated with the AgentSnapshot. CommissionAmounts where the CommissionHistory.CommissionTypeCT is [not] "Chargeback" .
     * @param agentSnapshot
     * @param trxTypeCTs
     * @return
     */
    public static CommissionHistory[] findByPlacedAgent_Date_CheckAmount(PlacedAgent[] placedAgents, EDITDate processDate, EDITBigDecimal checkAmount)
    {

        String hql = " select commissionHistory from CommissionHistory commissionHistory" +
                  " join commissionHistory.EDITTrxHistory editTrxHistory" +
                  " where commissionHistory.PlacedAgentFK in (";

                  for(int i = 0; i < placedAgents.length; i++)
                  {
                    hql += placedAgents[i].getPlacedAgentPK();

                    if (i < placedAgents.length - 1)
                    {
                      hql += ", ";
                    }
                  }

                  hql += ")" +

                  " and commissionHistory.commissionAmount = :checkAmount" +
                  " and (editTrxHistory.ProcessDateTime >= :minProcessDateTime" +
                  " and editTrxHistory.ProcessDateTime <= :maxProcessDateTime)";

        EDITMap params = new EDITMap("checkAmount", checkAmount)
                .put("minProcessDateTime", new EDITDateTime(processDate + " " + EDITDateTime.DEFAULT_MIN_TIME))
                .put("maxProcessDateTime", new EDITDateTime(processDate + " " + EDITDateTime.DEFAULT_MAX_TIME));

        Session session = null;

        List<CommissionHistory> results = null;

        try
        {
            session = SessionHelper.getSeparateSession(CommissionHistory.DATABASE);

            results = SessionHelper.executeHQL(session, hql, params, 0);

        }
        finally
        {
            if (session != null) session.close();
        }

        return results.toArray(new CommissionHistory[results.size()]);
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        CommissionActivity commissionActivity = new CommissionActivity();
        commissionActivity.setFinancialActivity(stagingContext.getCurrentFinancialActivity());
        commissionActivity.setAgent(stagingContext.getCurrentAgent());
        commissionActivity.setCommissionAmount(this.getCommissionAmount());
        commissionActivity.setCommissionTaxable(this.getCommissionTaxable());
        commissionActivity.setCommissionNonTaxable(this.getCommissionNonTaxable());
        commissionActivity.setCommissionRate(this.getCommissionRate());
        commissionActivity.setCommissionType(this.getCommissionTypeCT());
        commissionActivity.setStatementInd(this.getStatementInd());
        commissionActivity.setReduceTaxable(this.getReduceTaxable());
        commissionActivity.setDebitBalanceAmount(this.getDebitBalanceAmount());
        commissionActivity.setIncludedInDebitBalInd(this.getIncludedInDebitBalInd());
        commissionActivity.setForceoutMinBalInd(stagingContext.getForceoutMinBalInd());
        
        if(stagingContext.getCurrentAgentSnapshot() != null) {
	        commissionActivity.setAdvancePercent(stagingContext.getCurrentAgentSnapshot().getAdvancePercent());
	        commissionActivity.setRecoveryPercent(stagingContext.getCurrentAgentSnapshot().getRecoveryPercent());
        }
        
        if(stagingContext.getCurrentAgentHierarchyAllocation() != null)
        {
        	commissionActivity.setAllocationPercent(stagingContext.getCurrentAgentHierarchyAllocation().getAllocationPercent());
        }        

        PlacedAgent placedAgent = PlacedAgent.findBy_PK(this.getPlacedAgentFK());
        ClientRole clientRole = placedAgent.getClientRole();
        commissionActivity.setAgentNumber(clientRole.getReferenceID());

        stagingContext.getCurrentAgent().addCommissionActivity(commissionActivity);
        stagingContext.getCurrentFinancialActivity().addCommissionActivity(commissionActivity);

        SessionHelper.saveOrUpdate(commissionActivity, database);
        return stagingContext;
    }
}
