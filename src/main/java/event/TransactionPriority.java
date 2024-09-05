/*
 * User: cgleason
 * Date: Mar 28, 2005
 * Time: 10:31:26 AM
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

import event.common.*;

import event.dm.dao.*;

import java.util.*;



public class TransactionPriority extends HibernateEntity implements CRUDEntity
{
    private Long transactionPriorityPK;
    private EDITDate effectiveDate;
    private int priority;
    private String transactionTypeCT;
    private String commissionableEventInd;
    private String confirmEventInd;
    private Long hedgeFundInterimAccountFK;
    private String reinsuranceInd;
    private CRUDEntityImpl crudEntityImpl;
    private TransactionPriorityVO transactionPriorityVO;
    private Set transactionCorrespondences;
    
    public static final String BONUS_CHARGEBACK_ALL = "ALL";
    public static final String BONUS_CHARGEBACK_FIRSTYEARONLY = "FYO";
    
    /**
     * Used for transactions that can have Agent Bonus chargebacks.
     * see the following allowed values...
     * @see 
     */
    private String bonusChargebackCT;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;



    /**
     * Instantiates a TransactionPriority entity with a default TransactionPriorityVO.
     */
    public TransactionPriority()
    {
        init();
    }

    /**
     * Instantiates a TransactionPriorityCache entity with a supplied TransactionPriorityVO.
     *
     * @param transactionPriorityVO
     */
    public TransactionPriority(TransactionPriorityVO transactionPriorityVO)
    {
        init();

        this.transactionPriorityVO = transactionPriorityVO;
    }

    public Long getTransactionPriorityPK()
    {
        return SessionHelper.getPKValue(transactionPriorityVO.getTransactionPriorityPK());
    }

    public void setTransactionPriorityPK(Long transactionPriorityPK)
    {
        transactionPriorityVO.setTransactionPriorityPK(SessionHelper.getPKValue(transactionPriorityPK));
    }

    public EDITDate getEffectiveDate()
    {
        return SessionHelper.getEDITDate(transactionPriorityVO.getEffectiveDate());
    }

    public void setEffectiveDate(EDITDate effectiveDate)
    {
        transactionPriorityVO.setEffectiveDate(SessionHelper.getEDITDate(effectiveDate));
    }

    public int getPriority()
    {
        return transactionPriorityVO.getPriority();
    }

    public void setPriority(int priority)
    {
        transactionPriorityVO.setPriority(priority);
    }

    public String getTransactionTypeCT()
    {
        return transactionPriorityVO.getTransactionTypeCT();
    }

    public void setTransactionTypeCT(String transactionTypeCT)
    {
        transactionPriorityVO.setTransactionTypeCT(transactionTypeCT);
    }

    public String getCommissionableEventInd()
    {
        return transactionPriorityVO.getCommissionableEventInd();
    }

    public void setCommissionableEventInd(String commissionableEventInd)
    {
        transactionPriorityVO.setCommissionableEventInd(commissionableEventInd);
    }

    public String getConfirmEventInd()
    {
        return transactionPriorityVO.getConfirmEventInd();
    }

    public void setConfirmEventInd(String confirmEventInd)
    {
        transactionPriorityVO.setConfirmEventInd(confirmEventInd);
    }

    public String getReinsuranceInd()
    {
        return transactionPriorityVO.getReinsuranceInd();
    }

    public void setReinsuranceInd(String reinsuranceInd)
    {
        transactionPriorityVO.setReinsuranceInd(reinsuranceInd);
    }

    public CRUDEntityImpl getCrudEntityImpl()
    {
        return crudEntityImpl;
    }

    public void setCrudEntityImpl(CRUDEntityImpl crudEntityImpl)
    {
        this.crudEntityImpl = crudEntityImpl;
    }

    public TransactionPriorityVO getTransactionPriorityVO()
    {
        return transactionPriorityVO;
    }

    public void setTransactionPriorityVO(TransactionPriorityVO transactionPriorityVO)
    {
        this.transactionPriorityVO = transactionPriorityVO;
    }

    public Set getTransactionCorrespondences()
    {
        return transactionCorrespondences;
    }

    /**
      * Setter.
      * @param editTrxCorrespondences
      */
     public void setTransactionCorrespondences(Set transactionCorrespondences)
     {
         this.transactionCorrespondences = transactionCorrespondences;
     }

    /**
     * Adds a TransactionCorrespondence to the set of children
     * @param transactionCorrespondence
     */
    public void addTransactionCorrespondence(TransactionCorrespondence transactionCorrespondence)
    {
        this.getTransactionCorrespondences().add(transactionCorrespondence);

        transactionCorrespondence.setTransactionPriority(this);

        SessionHelper.saveOrUpdate(transactionCorrespondence, TransactionPriority.DATABASE);
    }

    /**
     * Removes a TransactionCorrespondence from the set of children
     * @param transactionCorrespondence
     */
    public void removeTransactionCorrespondence(TransactionCorrespondence transactionCorrespondence)
    {
        this.getTransactionCorrespondences().remove(transactionCorrespondence);

        transactionCorrespondence.setTransactionPriority(null);

        SessionHelper.saveOrUpdate(transactionCorrespondence, TransactionPriority.DATABASE);
    }


    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (transactionPriorityVO == null)
        {
            transactionPriorityVO = new TransactionPriorityVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }

        if (transactionCorrespondences == null)
        {
            transactionCorrespondences = new HashSet();
        }
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save()
    {
        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);

        TransactionPriorityCache transactionPriorityCache = TransactionPriorityCache.getInstance();

        transactionPriorityCache.reload();
    }

    /**
     * @throws Throwable
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws Throwable
    {
        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);

        TransactionPriorityCache transactionPriorityCache = TransactionPriorityCache.getInstance();

        transactionPriorityCache.reload();
    }

    public TransactionPriorityVO getAsVO()
    {
        return (TransactionPriorityVO) getVO();
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return transactionPriorityVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return transactionPriorityVO.getTransactionPriorityPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.transactionPriorityVO = (TransactionPriorityVO) voObject;
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
     * @param transactionPriorityPK
     */
    public static final TransactionPriority findByPK(long transactionPriorityPK)
    {
        TransactionPriority transactionPriority = null;

        TransactionPriorityVO[] transactionPriorityVOs = new TransactionPriorityDAO().findByPK(transactionPriorityPK);

        if (transactionPriorityVOs != null)
        {
            transactionPriority = new TransactionPriority(transactionPriorityVOs[0]);
        }

        return transactionPriority;
    }

    /**
     * Finder by TransactionType.
     * @param transactionType
     * @return TransactionPriority object.
     */
	public static final TransactionPriority findByTrxType(String transactionType)
    {
        TransactionPriority transactionPriority = null;

        String hql = " select transactionPriority" +
                     " from TransactionPriority transactionPriority" +
                     " where transactionPriority.TransactionTypeCT = :transactionType";

        Map params = new HashMap();
        params.put("transactionType", transactionType);

        List results = SessionHelper.executeHQL(hql, params, TransactionPriority.DATABASE);

        if (!results.isEmpty())
        {
            transactionPriority = (TransactionPriority) results.get(0);
        }

        return transactionPriority;
    }

  /**
   * Finds all TransactionPriority that have a non-null BonusChargeBack value.
   * This finder was originally implemented to suppor the Agent Bonus Update
   * process that needs to know which TransactionTypeCTs accept chargebacks.
   * @return the qualified TransactionPriorities
   */
  public static TransactionPriority[] findBy_BonusChargeback_Not_Null()
  {
    String hql = " from TransactionPriority transactionPriority" +
                  " where transactionPriority.BonusChargebackCT is not null";
                  
    
    List results = SessionHelper.executeHQL(hql, null, TransactionPriority.DATABASE);
    
    return (TransactionPriority[]) results.toArray(new TransactionPriority[results.size()]);
                      
  }
  
  /**
   * Returns the list of TransactionTypeCTs with the specified chargebackInd.
   * @param bonusChargeback the targeted TransactionPriority.BonusChargeback
   * @return
   */
  public static String[] findTransactionTypeCTsBy_BonusChargeback(String bonusChargeback)
  {
    String hql = " select transactionPriority.TransactionTypeCT" +
                " from TransactionPriority transactionPriority" +
                " where transactionPriority.BonusChargebackCT = :bonusChargebackCT";                
                
    Map params = new HashMap();
    
    params.put("bonusChargebackCT", bonusChargeback);
    
    List results = SessionHelper.executeHQL(hql, params, TransactionPriority.DATABASE);
    
    return (String[]) results.toArray(new String[results.size()]);
  }

  public void set_transactionPriorityPK(Long _transactionPriorityPK)
  {
    this.transactionPriorityPK = _transactionPriorityPK;
  }

  public Long get_transactionPriorityPK()
  {
    return transactionPriorityPK;
  }

  public void set_effectiveDate(EDITDate _effectiveDate)
  {
    this.effectiveDate = _effectiveDate;
  }

  public EDITDate get_effectiveDate()
  {
    return effectiveDate;
  }

  public void set_priority(int _priority)
  {
    this.priority = _priority;
  }

  public int get_priority()
  {
    return priority;
  }

  public void set_transactionTypeCT(String _transactionTypeCT)
  {
    this.transactionTypeCT = _transactionTypeCT;
  }

  public String get_transactionTypeCT()
  {
    return transactionTypeCT;
  }

  public void set_commissionableEventInd(String _commissionableEventInd)
  {
    this.commissionableEventInd = _commissionableEventInd;
  }

  public String get_commissionableEventInd()
  {
    return commissionableEventInd;
  }

  public void set_confirmEventInd(String _confirmEventInd)
  {
    this.confirmEventInd = _confirmEventInd;
  }

  public String get_confirmEventInd()
  {
    return confirmEventInd;
  }

  public void setHedgeFundInterimAccountFK(Long hedgeFundInterimAccountFK)
  {
    this.hedgeFundInterimAccountFK = hedgeFundInterimAccountFK;
  }

  public Long getHedgeFundInterimAccountFK()
  {
    return hedgeFundInterimAccountFK;
  }

  public void set_reinsuranceInd(String _reinsuranceInd)
  {
    this.reinsuranceInd = _reinsuranceInd;
  }

  public String get_reinsuranceInd()
  {
    return reinsuranceInd;
  }

  public void setBonusChargebackCT(String chargeBack)
  {
    this.bonusChargebackCT = chargeBack;
  }

  public String getBonusChargebackCT()
  {
    return bonusChargebackCT;
  }
  
  /**
   * Finds the trxTypeCTs that have been flagged as Commissionable.
   * @return
   */
  public static String[] getCommissionableEvents()
  {
    String hql = "select transactionPriority.TransactionTypeCT from TransactionPriority transactionPriority where transactionPriority.CommissionableEventInd = 'Y'";    
    
    List results = SessionHelper.executeHQL(hql, null, TransactionPriority.DATABASE);
    
    return (String[]) results.toArray(new String[results.size()]);
  }

    /**
     * Originally in TransactionPriorityDAO.findByTrxType
     * @param trxType
     * @return
     */
    public static TransactionPriority[] findBy_TrxType(String trxType)
    {
        String hql = " select transactionPriority from TransactionPriority transactionPriority" +
                    " where transactionPriority.TransactionTypeCT = :trxType";

        Map params = new HashMap();

        params.put("trxType", trxType);

        List results = SessionHelper.executeHQL(hql, params, TransactionPriority.DATABASE);

        return (TransactionPriority[]) results.toArray(new TransactionPriority[results.size()]);
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, TransactionPriority.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, TransactionPriority.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return TransactionPriority.DATABASE;
    }

    /**
	 * Finds the TransactionPriority by the specified transactionTypeCT
     *
     * @param transactionTypeCT         specified transactionTypeCT
     * 
     * @return TransactionPriority object
     */
    public static TransactionPriority findByTransactionTypeCT(String transactionTypeCT)
    {
        TransactionPriority transactionPriority = null;

        TransactionPriorityVO[] transactionPriorityVOs = new TransactionPriorityDAO().findByTrxType(transactionTypeCT);

        if (transactionPriorityVOs != null)
        {
            transactionPriority = new TransactionPriority(transactionPriorityVOs[0]);
        }

        return transactionPriority;
    }

    /**
     * Determines if the priority of the first transactionType is less than the priority of the second transactionType
     *
     * @param transactionTypeCT1        first transactionType
     * @param transactionTypeCT2        second transactionType
     *
     * @return true if the first transactionType's priority is less than the second transactionType's priority
     */
    public static boolean priorityIsLessThan(String transactionTypeCT1, String transactionTypeCT2)
    {
        boolean priorityIsGreaterThan = false;

        TransactionPriority transactionPriority1 = TransactionPriority.findByTransactionTypeCT(transactionTypeCT1);
        TransactionPriority transactionPriority2 = TransactionPriority.findByTransactionTypeCT(transactionTypeCT2);

        if (transactionPriority1.getAsVO().getPriority() < transactionPriority2.getAsVO().getPriority())
        {
            priorityIsGreaterThan = true;
        }

        return priorityIsGreaterThan;
    }
}
