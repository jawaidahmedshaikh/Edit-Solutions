/*
 * User: dlataill
 * Date: Oct 19, 2004
 * Time: 9:16:15 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import engine.FilteredFund;

import engine.Fund;

import engine.ProductStructure;

import engine.sp.CSCache;

import event.*;

import java.util.*;

import org.hibernate.Session;
import staging.IStaging;
import staging.StagingContext;


public class Investment extends HibernateEntity implements CRUDEntity, IStaging
{
    private CRUDEntityImpl crudEntityImpl;
    private InvestmentVO investmentVO;
    private long newInvestmentPK = 0;
    private Segment segment;
    private boolean revertingChargeCode;
    private Set<InvestmentAllocation> investmentAllocations = new HashSet<InvestmentAllocation>();
    private InvestmentAllocationOverride investmentAllocationOverride;
    private Set<Bucket> buckets = new HashSet<Bucket>();
    private Set<InvestmentHistory> investmentHistories = new HashSet<InvestmentHistory>();
    private Set investmentAllocationOverrides = new HashSet<InvestmentHistory>();
    private Set commissionInvestmentHistories = new HashSet<InvestmentHistory>();

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Default constructor
     */
    public Investment()
    {
        init();
    }

    public Investment(InvestmentVO investmentVO)
    {
        init();

        segment = new Segment(investmentVO.getSegmentFK());

        this.investmentVO = investmentVO;
    }

    public Investment(long segmentFK, long filteredFundFK)
    {
        init();

        segment = new Segment(segmentFK);

        this.investmentVO.setInvestmentPK(0);
        this.investmentVO.setSegmentFK(segmentFK);
        this.investmentVO.setFilteredFundFK(filteredFundFK);
    }

    /**
     * Instantiates an Investment entity with a InvestmentVO retrieved from persistence.
     * @param investmentPK
     */
    public Investment(long investmentPK)
    {
        init();

        crudEntityImpl.load(this, investmentPK, ConnectionFactory.EDITSOLUTIONS_POOL);

        InvestmentAllocation[] investmentAllocations = (InvestmentAllocation[]) crudEntityImpl.getChildEntities(this, InvestmentAllocation.class, InvestmentAllocationVO.class, ConnectionFactory.EDITSOLUTIONS_POOL);

        if ((investmentAllocations != null) && (investmentAllocations.length > 0))
        {
            for (int i = 0; i < investmentAllocations.length; i++)
            {
                this.investmentVO.addInvestmentAllocationVO((InvestmentAllocationVO) investmentAllocations[i].getVO());
            }
        }

        segment = new Segment(this.investmentVO.getSegmentFK());
    }

    /**
     * Instantiates an Investment entity with an InvestmentVO and Segment
     * @param investmentVO
     * @param segment
     */
    public Investment(InvestmentVO investmentVO, Segment segment)
    {
        init();
        this.investmentVO = investmentVO;
        this.segment = segment;
    }

    public Set<InvestmentHistory> getInvestmentHistories()
    {
        return investmentHistories;
    }

    public void setInvestmentHistories(Set<InvestmentHistory> investmentHistories)
    {
        this.investmentHistories = investmentHistories;
    }

    public Set<Bucket> getBuckets()
    {
        return buckets;
    }

    public void setBuckets(Set<Bucket> buckets)
    {
        this.buckets = buckets;
    }

    /**
     * Getter.
     * @return
     */
    public InvestmentAllocationOverride getInvestmentAllocationOverride()
    {
        return investmentAllocationOverride;
    }

    /**
     * Setter.
     * @param investmentAllocationOverride
     */
    public void setInvestmentAllocationOverride(InvestmentAllocationOverride investmentAllocationOverride)
    {
        this.investmentAllocationOverride = investmentAllocationOverride;
    }

    /**
     * Getter.
     * @return
     */
    public Long getChargeCodeFK()
    {
        return SessionHelper.getPKValue(investmentVO.getChargeCodeFK());
    }

    //-- long getChargeCodeFK()

    /**
     * Getter.
     * @return
     */
    public String getExcessInterestMethod()
    {
        return investmentVO.getExcessInterestMethod();
    }

    //-- java.lang.String getExcessInterestMethod()

    /**
     * Getter.
     * @return
     */
    public Long getInvestmentPK()
    {
        return SessionHelper.getPKValue(investmentVO.getInvestmentPK());
    }

    //-- long getInvestmentPK()

    /**
     * Getter.
     * @return
     */
    public Long getSegmentFK()
    {
        return SessionHelper.getPKValue(investmentVO.getSegmentFK());
    }

    /**
     * Setter.
     * @param excessInterestMethod
     */
    public void setExcessInterestMethod(String excessInterestMethod)
    {
        investmentVO.setExcessInterestMethod(excessInterestMethod);
    }

    //-- void setExcessInterestMethod(java.lang.String)
    public void setInvestmentPK(Long investmentPK)
    {
        investmentVO.setInvestmentPK(SessionHelper.getPKValue(investmentPK));
    }

    //-- void setInvestmentPK(long)
    public void setSegmentFK(Long segmentFK)
    {
        investmentVO.setSegmentFK(SessionHelper.getPKValue(segmentFK));
    }

    //-- void setSegmentFK(long)

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (investmentVO == null)
        {
            investmentVO = new InvestmentVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }

        investmentAllocations = new HashSet();

        if (investmentAllocationOverrides == null)
        {
            investmentAllocationOverrides = new HashSet();
    }
    }

    public InvestmentAllocationVO[] getInvestmentAllocationVOs()
    {
        return this.investmentVO.getInvestmentAllocationVO();
    }

    public Long getFilteredFundFK()
    {
        return SessionHelper.getPKValue(investmentVO.getFilteredFundFK());
    }

    /**
     * @return
     * @see CRUDEntity#getPK()
     */
    public long getPK()
    {
        return this.investmentVO.getInvestmentPK();
    }

    public long getNewInvestmentPK()
    {
        return this.newInvestmentPK;
    }

    public void saveForHibernate()
    {
        if (revertingChargeCode)
        {
            //CONVERT TO HIBERNATE - 09/13/07
            hSave();
        }
        else
        {
            String segmentStatus = segment.getStatus();
            boolean saveChanges = segment.getSaveChangeHistory();
            boolean updateDone = false;

            if (segmentStatus.equalsIgnoreCase("Active") && saveChanges)
            {
                updateDone = checkForNonFinancialChanges();
            }

            //switch set if update took place already
            if (!updateDone)
            {
                //CONVERT TO HIBERNATE - 09/13/07
                hSave();
            }

            if (investmentVO.getInvestmentAllocationVOCount() > 0)
            {
                InvestmentAllocationVO[] investmentAlloctionVOs = investmentVO.getInvestmentAllocationVO();

                for (int i = 0; i < investmentAlloctionVOs.length; i++)
                {
                    investmentAlloctionVOs[i].setInvestmentFK(investmentVO.getInvestmentPK());

                    InvestmentAllocation investmentAllocation = (InvestmentAllocation) SessionHelper.map(investmentAlloctionVOs[i], DATABASE);
                    investmentAllocation.setInvestment(this);
                    investmentAllocation.saveForHibernate();
                }
            }
        }
    }

    public void save()
    {
        if (revertingChargeCode)
        {
            crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
        }
        else
        {
            String segmentStatus = segment.getStatus();
            boolean saveChanges = segment.getSaveChangeHistory();
            boolean updateDone = false;

            if (segmentStatus.equalsIgnoreCase("Active") && saveChanges)
            {
                updateDone = checkForNonFinancialChanges();
            }

            //switch set if update took place already
            if (!updateDone)
            {
                contract.dm.StorageManager sm = new contract.dm.StorageManager();

                this.newInvestmentPK = sm.saveInvestmentVO(this.investmentVO);
            }

            if (investmentVO.getInvestmentAllocationVOCount() > 0)
            {
                InvestmentAllocationVO[] investmentAlloctionVOs = investmentVO.getInvestmentAllocationVO();

                for (int i = 0; i < investmentAlloctionVOs.length; i++)
                {
                    investmentAlloctionVOs[i].setInvestmentFK(investmentVO.getInvestmentPK());

                    InvestmentAllocation investmentAllocation = new InvestmentAllocation(investmentAlloctionVOs[i], segment);
                    investmentAllocation.save();
                }
            }
        }
    }


    /**
     * @see CRUDEntity#delete()
     */
    public void delete()
    {
        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * @return
     * @see CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return this.investmentVO;
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.investmentVO = (InvestmentVO) voObject;
    }

    /**
     * @return
     * @see CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    /**
     * @return
     * @see CRUDEntity#isNew()
     */
    public boolean isNew()
    {
        return crudEntityImpl.isNew(this);
    }

    private boolean checkForNonFinancialChanges()
    {
        ChangeProcessor changeProcessor = new ChangeProcessor();
        Change[] changes = changeProcessor.checkForChanges(investmentVO, investmentVO.getVoShouldBeDeleted(), ConnectionFactory.EDITSOLUTIONS_POOL, null);
        boolean updateDone = false;

        if (changes != null)
        {
            for (int i = 0; i < changes.length; i++)
            {
                if (changes[i].getStatus() == (Change.CHANGED))
                {
                    changeProcessor.processForChanges(changes[i], this, segment.getOperator(), segment.getPK());
                }
                else if (changes[i].getStatus() == (Change.ADDED))
                {
//                    contract.dm.StorageManager sm = new contract.dm.StorageManager();
//                    this.newInvestmentPK = sm.saveInvestmentVO(this.investmentVO);

                    //CONVERT TO HIBERNATE - 09/13/07
                    hSave();
                    updateDone = true;

                    String property = "InvestmentPK";
                    changeProcessor.processForAdd(changes[i], this, segment.getOperator(), property, segment.getPK());
                }
                else if (changes[i].getStatus() == (Change.DELETED))
                {
                    String property = "FilteredFundFK";
                    changeProcessor.processForDelete(changes[i], this, segment.getOperator(), property, segment.getPK());
                }
            }
        }

        return updateDone;
    }

    /**
     * Setter.
     * @param chargeCodeFK
     */
    public void setChargeCodeFK(Long chargeCodeFK)
    {
        this.investmentVO.setChargeCodeFK(SessionHelper.getPKValue(chargeCodeFK));
    }

    /**
     * Sets the ChargeCodeFK to the specified value.
     * @param chargeCodeFK
     */
    public void revertChargeCodeFK(long chargeCodeFK)
    {
        this.investmentVO.setChargeCodeFK(chargeCodeFK);

        this.revertingChargeCode = true;
    }

    /**
     * Setter.
     * @param segment
     */
    public void setSegment(Segment segment)
    {
        this.segment = segment;
    }

    /**
      * Getter.
      * @return
      */
    public Segment getSegment()
    {
        return segment;
    }

    /**
     * Setter
     * @param filteredFundFK
     */
    public void setFilteredFundFK(Long filteredFundFK)
    {
        this.investmentVO.setFilteredFundFK(SessionHelper.getPKValue(filteredFundFK));
    }

    /**
      * Setter
      * @param investmentAllocation
      */
    public void addInvestmentAllocation(InvestmentAllocation investmentAllocation)
    {
        getInvestmentAllocations().add(investmentAllocation);

        investmentAllocation.setInvestment(this);

//        SessionHelper.saveOrUpdate(investmentAllocation, SessionHelper.EDITSOLUTIONS);
    }

    /**
      * Setter
      * @param investmentAllocationOverride
      */
    public void addInvestmentAllocationOverride(InvestmentAllocationOverride investmentAllocationOverride)
    {
        getInvestmentAllocationOverrides().add(investmentAllocationOverride);

        investmentAllocationOverride.setInvestment(this);
    }
    /**
     * Getter
     * @return
     */
    public InvestmentAllocation getInvestmentAllocation()
    {
        InvestmentAllocation investmentAllocation = getInvestmentAllocations().isEmpty() ? null : (InvestmentAllocation) getInvestmentAllocations().iterator().next();

        return investmentAllocation;
    }

    /**
     * Getter
     * @return
     */
    public Set getInvestmentAllocationOverrides()
    {
        return investmentAllocationOverrides;
    }

    /**
     * Getter.
     * @return
     */
    public Set<InvestmentAllocation> getInvestmentAllocations()
    {
        return investmentAllocations;
    }

    /**
     * Setter.
     * @param investmentAllocations
     */
    public void setInvestmentAllocations(Set<InvestmentAllocation> investmentAllocations)
    {
        this.investmentAllocations = investmentAllocations;
    }

    public void removeInvestmentAllocation(InvestmentAllocation investmentAllocation)
    {
        getInvestmentAllocations().remove(investmentAllocation);

        investmentAllocation.setInvestment(null);

        SessionHelper.saveOrUpdate(investmentAllocation, Investment.DATABASE);
    }

    /**
     * Setter.
     * @param investmentAllocationOverrides
     */
    public void setInvestmentAllocationOverrides(Set investmentAllocationOverrides)
    {
        this.investmentAllocationOverrides = investmentAllocationOverrides;
    }

    /**
     * Setter
     * @param excessInterestCalculationDate
     */
    public void setExcessInterestCalculationDate(EDITDate excessInterestCalculationDate)
    {
        investmentVO.setExcessInterestCalculationDate(excessInterestCalculationDate != null ? excessInterestCalculationDate.getFormattedDate() : null);
    }

    /**
      * Setter
      * @param excessInterestPaymentDate
      */
    public void setExcessInterestPaymentDate(EDITDate excessInterestPaymentDate)
    {
        investmentVO.setExcessInterestPaymentDate(excessInterestPaymentDate != null ? excessInterestPaymentDate.getFormattedDate() : null );
    }

    /**
      * Setter
      * @param excessInterest
      */
    public void setExcessInterest(EDITBigDecimal excessInterest)
    {
        investmentVO.setExcessInterest(excessInterest != null ? excessInterest.getBigDecimal() : null);
    }

    /**
      * Setter
      * @param excessInterestStartDate
      */
    public void setExcessInterestStartDate(EDITDate excessInterestStartDate)
    {
        investmentVO.setExcessInterestStartDate(excessInterestStartDate != null ? excessInterestStartDate.getFormattedDate() : null);
    }

    /**
      * Setter
      * @param assumedInvestmentReturn
      */
    public void setAssumedInvestmentReturn(EDITBigDecimal assumedInvestmentReturn)
    {
        investmentVO.setAssumedInvestmentReturn(assumedInvestmentReturn != null ? assumedInvestmentReturn.getBigDecimal() : null);
    }

    /**
      * Getter
      */
    public EDITDate getExcessInterestCalculationDate()
    {
        return investmentVO.getExcessInterestCalculationDate() == null ? null : new EDITDate(investmentVO.getExcessInterestCalculationDate());
    }

    /**
      * Getter
      */
    public EDITDate getExcessInterestPaymentDate()
    {
        return investmentVO.getExcessInterestPaymentDate() == null ? null : new EDITDate(investmentVO.getExcessInterestPaymentDate());
    }

    /**
      * Getter
      */
    public EDITBigDecimal getExcessInterest()
    {
        return investmentVO.getExcessInterest() == null ? null : new EDITBigDecimal(investmentVO.getExcessInterest());
    }

    /**
      * Getter
      */
    public EDITDate getExcessInterestStartDate()
    {
        return investmentVO.getExcessInterestStartDate() == null ? null : new EDITDate(investmentVO.getExcessInterestStartDate());
    }

    /**
      * Getter
      */
    public EDITBigDecimal getAssumedInvestmentReturn()
    {
        return investmentVO.getAssumedInvestmentReturn() == null ? null : new EDITBigDecimal(investmentVO.getAssumedInvestmentReturn());
    }

	/**
     * Getter.
     * @return
     */
    public String getStatus()
    {
        return investmentVO.getStatus();
    }

	/**
     * Setter.
     * @param status
     */
    public void setStatus(String status)
    {
        investmentVO.setStatus(status);
    }

    public void addInvestmentHistory(InvestmentHistory investmentHistory)
    {
        getInvestmentHistories().add(investmentHistory);

        investmentHistory.setInvestment(this);

        SessionHelper.saveOrUpdate(investmentHistory, Investment.DATABASE);
    }

    public void removeInvestmentHistory(InvestmentHistory investmentHistory)
    {
        getInvestmentHistories().remove(investmentHistory);

        investmentHistory.setInvestment(null);

        SessionHelper.saveOrUpdate(investmentHistory, Investment.DATABASE);
    }

    public void addBucket(Bucket bucket)
    {
        getBuckets().add(bucket);

        bucket.setInvestment(this);

        SessionHelper.saveOrUpdate(bucket, Investment.DATABASE);
    }

    public void removeBucket(Bucket bucket)
    {
        getBuckets().remove(bucket);

        bucket.setInvestment(null);

        SessionHelper.saveOrUpdate(bucket, Investment.DATABASE);
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

        commissionInvestmentHistory.setInvestment(this);

        SessionHelper.saveOrUpdate(commissionInvestmentHistory, Investment.DATABASE);
    }

    /**
     * Removes a CommissionInvestmentHistory from the set of children
     * @param commissionInvestmentHistory
     */
    public void removeCommissionInvestmentHistory(CommissionInvestmentHistory commissionInvestmentHistory)
    {
        this.getCommissionInvestmentHistories().remove(commissionInvestmentHistory);

        commissionInvestmentHistory.setInvestment(null);

        SessionHelper.saveOrUpdate(commissionInvestmentHistory, Investment.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Investment.DATABASE;
    }

    /**
     * Retrieve the Investment for the given investmentPK
     * @param investmentPK  - The primary key for the Investment that is to be retrieved.
     * @return
     */
    public static Investment findByPK(Long investmentPK)
    {
        return (Investment) SessionHelper.get(Investment.class, investmentPK, Investment.DATABASE);
    }

    /**
     * Finds the Investments by the specified segmentPK.  Includes Buckets attached to the Investments
     *
     * @param segmentPK
     *
     * @return
     */
    public static Investment[] findBy_SegmentPK_IncludeBuckets(Long segmentPK)
    {
        String hql = "select investment from Investment investment "
                   + " join fetch investment.Bucket bucket"
                   + " where investment.SegmentFK = :segmentFK";

        Map params = new HashMap();
        params.put("segmentFK", segmentPK);

        List<Investment> results = SessionHelper.executeHQL(hql, params, Investment.DATABASE);

        return results.toArray(new Investment[results.size()]);
    }

    /**
     * Finds the Investments by the specified segmentPK.  Includes InvestmentAllocations attached to the Investments
     *
     * @param segmentPK
     *
     * @return
     */
    public static Investment[] findBy_SegmentPK_IncludeInvestmentAllocations(Long segmentPK)
    {
        String hql = "select investment from Investment investment "
                   + " join fetch investment.InvestmentAllocation investmentAllocation"
                   + " where investment.SegmentFK = :segmentFK";

        Map params = new HashMap();
        params.put("segmentFK", segmentPK);

        List<Investment> results = SessionHelper.executeHQL(hql, params, Investment.DATABASE);

        return results.toArray(new Investment[results.size()]);
    }

    /**
     * Finds the Investment for the specified investmentPK.  Includes InvestmentAllocations attached to the Investments
     *
     * @param investmentPK
     *
     * @return
     */
    public static Investment findByPK_IncludeInvestmentAllocations(Long investmentPK)
    {
        Investment investment = null;

        String hql = "select investment from Investment investment "
                   + " join fetch investment.InvestmentAllocation investmentAllocation"
                   + " where investment.InvestmentPK = :investmentPK";

        Map params = new HashMap();
        params.put("investmentPK", investmentPK);

        List results = SessionHelper.executeHQL(hql, params, Investment.DATABASE);

        if (!results.isEmpty())
        {
            investment = (Investment) results.get(0);
        }

        return investment;
    }

    /**
     * Originally from InvestmentDAO.findBySegmentPKAndInvestmentAllocationOverrideStatus
     *
     * @param segmentPK
     * @param overrideStatus
     *
     * @return
     */
    public static Investment[] findBy_SegmentPK_InvestmentAllocationOverrideStatus(
            Long segmentPK, String overrideStatus)
    {
        String hql = "select investment from Investment investment " +
                     " join investment.InvestmentAllocations investmentAllocation" +
                     " where investment.SegmentFK = :segmentPK" +
                     " and investmentAllocation.OverrideStatus = :overrideStatus";

        Map params = new HashMap();
        params.put("segmentPK", segmentPK);
        params.put("overrideStatus", overrideStatus);

        List<Investment> results = SessionHelper.executeHQL(hql, params, Investment.DATABASE);
        
        results = SessionHelper.makeUnique(results);

        return results.toArray(new Investment[results.size()]);
    }

    /**
     * Originally from InvestmentDAO.findByFilteredFundFKAndSegmentFK
     *
     * @param filteredFundFK
     * @param segmentFK
     * @return
     */
    public static Investment[] findBy_FilteredFundFK_SegmentFK(Long filteredFundFK, Long segmentFK)
    {
        String hql = "select investment from Investment investment " +
                     " where investment.FilteredFundFK = :filteredFundFK" +
                     " and  investment.SegmentFK = :segmentFK";

        Map params = new HashMap();
        params.put("filteredFundFK", filteredFundFK);
        params.put("segmentFK", segmentFK);

        List<Investment> results = SessionHelper.executeHQL(hql, params, Investment.DATABASE);

        return results.toArray(new Investment[results.size()]);
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Investment.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, Investment.DATABASE);
    }

  public void setInvestmentVO(InvestmentVO investmentVO)
  {
    this.investmentVO = investmentVO;
  }

  public InvestmentVO getInvestmentVO()
  {
    return investmentVO;
  }

  /**
   * Determines if the FilteredFund.Fund.TypeCodeCT = 'System'.
   * @return true if the TypeCodeCT = 'System'
   */
  public boolean isSystemFund()
  {
    // Performance is too critical here - need to use engine API.
    FilteredFundVO filteredFundVO = CSCache.getCSCache().getFilteredFundVOBy_FilteredFundPK(getFilteredFundFK().longValue());
    
    FundVO fundVO = CSCache.getCSCache().getFundVOBy_FilteredFundPK(filteredFundVO.getFilteredFundPK());
  
    String typeCodeCT = fundVO.getTypeCodeCT();
    
    return typeCodeCT.equalsIgnoreCase(Fund.FUNDTYPE_SYSTEM);
  }
  
  /**
   * By definition, if it is a "System Fund" [and] it is a "Load Fund", then it
   * qualifies as a "General Account Fund".
   */
  public boolean isGeneralAccountFund()
  {
      return (isSystemFund() && isLoanFund());
  }

  public boolean isLoanFund()
  {
      FilteredFundVO filteredFundVO = CSCache.getCSCache().getFilteredFundVOBy_FilteredFundPK(getFilteredFundFK().longValue());

      FundVO fundVO = CSCache.getCSCache().getFundVOBy_FilteredFundPK(filteredFundVO.getFilteredFundPK());

      String loanQualifierCT = fundVO.getLoanQualifierCT();

      return (loanQualifierCT != null);
  }

    public boolean isHedgeFund()
    {
        FilteredFundVO filteredFundVO = CSCache.getCSCache().getFilteredFundVOBy_FilteredFundPK(getFilteredFundFK().longValue());

        FundVO fundVO = CSCache.getCSCache().getFundVOBy_FilteredFundPK(filteredFundVO.getFilteredFundPK());

        String fundType = fundVO.getFundType();

        return (!fundType.equalsIgnoreCase("Hedge"));
    }
    
    /**
     * Returns true if this investment is free look investment.
     * @return
     */
    public boolean isFreeLookFund()
    {
        boolean isFreeLookFund = false;
        
        ProductStructure productStructure = this.getSegment().getProductStructure();
        
        String areaCT = this.getSegment().getIssueStateCT();
        
        EDITDate effectiveDate = this.getSegment().getEffectiveDate();
        
        FilteredFund freeLookFund = productStructure.getFreeLookFund(areaCT, effectiveDate);
        
        if (freeLookFund != null)
        {
            isFreeLookFund = this.getFilteredFundFK().longValue() == freeLookFund.getFilteredFundPK().longValue();
        }
        
        return isFreeLookFund;
    }
    
    public InvestmentAllocation getOverrideInvestmentAllocationWith100Percent()
    {
        InvestmentAllocation overrideAllocationWith100Percent = null;
        
        for (Iterator<InvestmentAllocation> iterator = this.getInvestmentAllocations().iterator(); iterator.hasNext();)
        {
            InvestmentAllocation investmentAllocation = iterator.next();
            
            EDITBigDecimal allocationPercent = investmentAllocation.getAllocationPercent();
            
            String overrideStatus = investmentAllocation.getOverrideStatus();
            
            if (allocationPercent.isEQ("1") && overrideStatus.equalsIgnoreCase("O"))
            {
                overrideAllocationWith100Percent = investmentAllocation;
                
                break;
            }
        }
        
        return overrideAllocationWith100Percent;
    }

  /**
   * A finder that uses a separate Hibernate Session and builds the following structure:
   * 
   * Investment
   * Investment.InvestmentAllocation (via the InvestmenAllocationOverrides)
   * Investment.Bucket.
   * 
   * @param contractSetup
   * @return
   */
  public static Investment[] findSeparateBy_ContractSetup(ContractSetup contractSetup)
  {
    String hql = " select investment" +
                " from Investment investment" +
                " join fetch investment.InvestmentAllocations investmentAllocation" +
                " join investmentAllocation.InvestmentAllocationOverrides investmentAllocationOverride" +
                " join investmentAllocationOverride.ContractSetup contractSetup" +
                " left join fetch investment.Buckets" + 
                " where contractSetup = :contractSetup";
                
    EDITMap params = new EDITMap("contractSetup", contractSetup);                
    
    List<Investment> results = null;
    
    Session session = null;
    
    try
    {
      session = SessionHelper.getSeparateSession(Investment.DATABASE);
      
      results = SessionHelper.makeUnique(SessionHelper.executeHQL(session, hql, params, 0));
    }
    finally
    {
      if (session != null) session.close();
    }
    
    return results.toArray(new Investment[results.size()]);
  }

  /**
   * A finder that uses a separate Hibernate Session and build the following structure:
   * 
   * Investment
   * Investment.InvestmentAllocation ('P'rimary ones only)
   * Investment.Bucket.
   * @param editTrx
   * @return
   */
  public static Investment[] findSeparateBy_EDITTrx(EDITTrx editTrx)
  {
    String hql = " select investment" +
                " from EDITTrx editTrx" +
                " join editTrx.ClientSetup clientSetup" +
                " join clientSetup.ContractSetup contractSetup" +
                " join contractSetup.Segment segment" +
                " join segment.Investments investment" +
                " left join fetch investment.InvestmentAllocations investmentAllocation" +
                " left join fetch investment.Buckets" +
                " where editTrx = :editTrx";
    
    EDITMap params = new EDITMap("editTrx", editTrx);
    
    List<Investment> results = null;
    
    Session session = null;
    
    try
    {
      session = SessionHelper.getSeparateSession(Investment.DATABASE);
    
      results = SessionHelper.makeUnique(SessionHelper.executeHQL(session, hql, params, 0));
    }
    finally
    {
      if (session != null) session.close();
    }
    
    return results.toArray(new Investment[results.size()]);                
  }

    /**
     * A specialized search to find the [only] Investment [if it exists] that is associated
     * with a GeneralAccount Fund. There should only be one, if any.
     */
  public static Investment findSeparateBy_EDITTrx_V2(EDITTrx editTrx)
  {
      Investment investment = null;
      
      FilteredFund[] generalAccountFilteredFunds = FilteredFund.getSeparateGeneralAccountFilteredFunds();

      if (generalAccountFilteredFunds != null && generalAccountFilteredFunds.length > 0)
      {
          String hql = " select investment" +
                  " from EDITTrx editTrx" +
                  " join editTrx.ClientSetup clientSetup" +
                  " join clientSetup.ContractSetup contractSetup" +
                  " join contractSetup.Segment segment" +
                  " join segment.Investments investment" +
                  " left join fetch investment.Buckets" +
                  " where editTrx = :editTrx" +
                  " and investment.FilteredFundFK in (";

          int length = generalAccountFilteredFunds.length;

          for (int i = 0; i < length; i++)
          {
              FilteredFund filteredFund = generalAccountFilteredFunds[i];

              Long filteredFundFK = filteredFund.getFilteredFundPK();

              hql += filteredFundFK.toString();

              if ( i < (length - 1))
              {
                  hql += ", ";
              }
          }

          hql += ")";

          EDITMap params = new EDITMap("editTrx", editTrx);

          Session session = null;

          try
          {
              session = SessionHelper.getSeparateSession(Investment.DATABASE);

              List<Investment> results = SessionHelper.executeHQL(session, hql, params, 0);

              if (!results.isEmpty())
              {
                  investment = results.get(0); // There should only be one.
              }
          }
          finally
          {
              if (session != null) session.close();
          }
      }

      return investment;
  }

  /**
   * A finder that uses a separate Hibernate Session and build the following structure:
   *
   * Investment
   * Investment.InvestmentAllocation ('P'rimary ones only)
   * Investment.Bucket.
   * @param editTrx
   * @return
   */
  public static Investment[] findSeparateBy_EDITTrx_V3(EDITTrx editTrx)
  {
        String hql = " select investment" +
                    " from EDITTrx editTrx" +
                    " join editTrx.ClientSetup clientSetup" +
                    " join clientSetup.ContractSetup contractSetup" +
                    " join contractSetup.Segment segment" +
                    " join segment.Investments investment" +
                    " left join fetch investment.InvestmentAllocations investmentAllocation" +
                    " left join fetch investment.Buckets bucket" +
                    " where editTrx = :editTrx" +
                    " and (bucket.CumDollars > 0 or bucket.CumUnits > 0)";

        EDITMap params = new EDITMap("editTrx", editTrx);

        Investment[] investments = null;

        List<Investment> results = null;

        Session session = null;

        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

            results = SessionHelper.makeUnique(SessionHelper.executeHQL(session, hql, params, 0));

            if (!results.isEmpty())
            {
                investments = (Investment[])results.toArray(new Investment[results.size()]);
            }
        }
        finally
        {
          if (session != null) session.close();
        }

        return investments;
  }
  
    /**
    * Finder by InvestmentPK eagerly fetches Buckets.
    * @param investmentPK
    * @return
    */
    public static Investment findSeperateBy_InvestmentPK_V1(Long investmentPK)
    {
        String hql = "select investment" +
                     " from Investment investment" +
                     " left join fetch investment.InvestmentAllocations investmentAllocations" +
                     " left join fetch investment.Buckets buckets" +
                     " where investment.InvestmentPK = :investmentPK";
    
        Map params = new HashMap();        
        params.put("investmentPK", investmentPK);
    
        Investment investment = null;
    
        Session session = null;
    
        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);
    
            List<Investment> results = SessionHelper.makeUnique(SessionHelper.executeHQL(session, hql, params, 0));
    
            if (!results.isEmpty())
            {
                investment = results.get(0);
            }
        }
        finally
        {
            if (session != null) session.close();
        }
    
        return investment;
    }
    
    /**
    * Finder by InvestmentPKs.
    * @param investmentPKs
    * @return Array of Investments.
    */
    public static Investment[] findSeperateBy_InvestmentPK_V2(List<Long> investmentAllocationPKs)
    {
        String hql = "select investment" +
                     " from Investment investment" +
                     " join fetch investment.Segment segment" +
                     " left join fetch investment.InvestmentAllocations investmentAllocation" +
                     " left join fetch investment.Buckets buckets" +
                     " where investmentAllocation.InvestmentAllocationPK in (:investmentAllocationPKs)";
    
        Map params = new HashMap();        
        params.put("investmentAllocationPKs", investmentAllocationPKs);
    
        List<Investment> results = new ArrayList<Investment>();
    
        Session session = null;
    
        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);
    
            results = SessionHelper.makeUnique(SessionHelper.executeHQL(session, hql, params, 0));    
        }
        finally
        {
            if (session != null) session.close();
        }
    
        return results.toArray(new Investment[results.size()]);
    }    
    
    /**
    * Finds Investments by SegmentPK and eagerly fetches InvestmentAllocations and Buckets.
    * @param segmentPK
    * @return Array of Investments
    */
    public static Investment[] findSeperateBy_SegmentPK_V1(Long segmentPK)
    {
        String hql = " select investment" +
                     " from Investment investment" +
                     " join fetch investment.Segment segment" +
                     " left join fetch investment.InvestmentAllocations investmentAllocations" +
                     " left join fetch investment.Buckets buckets" +
                     " where investment.SegmentFK = :segmentPK";
    
        Map params = new HashMap();        
        params.put("segmentPK", segmentPK);
    
        Investment[] investments = null;
    
        Session session = null;
    
        List<Investment> results = null;
    
        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);
    
            results = SessionHelper.makeUnique(SessionHelper.executeHQL(session, hql, params, 0));
    
            investments = results.toArray(new Investment[results.size()]);
        }
        finally
        {
            if (session != null) session.close();
        }
    
        return investments;
    }
  
    /**
    * A specialized search to find the [only] Investment [if it exists] that is associated
    * with a GeneralAccount Fund. There should only be one, if any.
    */
    public static Investment findSeperateBy_SegmentPK_V2(Long segmentPK)
    {
      Investment investment = null;
      
      FilteredFund[] generalAccountFilteredFunds = FilteredFund.getSeparateGeneralAccountFilteredFunds();
    
      if (generalAccountFilteredFunds != null && generalAccountFilteredFunds.length > 0)
      {
          String hql = " select investment" +
                       " from Investment investment " +
                       " join fetch investment.Segment segment" +
                       " left join fetch investment.Buckets" +
                       " where investment.SegmentFK = :segmentPK" +
                       " and investment.FilteredFundFK in (";
    
          int length = generalAccountFilteredFunds.length;
    
          for (int i = 0; i < length; i++)
          {
              FilteredFund filteredFund = generalAccountFilteredFunds[i];
    
              Long filteredFundFK = filteredFund.getFilteredFundPK();
    
              hql += filteredFundFK.toString();
    
              if ( i < (length - 1))
              {
                  hql += ", ";
              }
          }
    
          hql += ")";
    
          EDITMap params = new EDITMap("segmentPK", segmentPK);
    
          Session session = null;
    
          try
          {
              session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);
    
              List<Investment> results = SessionHelper.executeHQL(session, hql, params, 0);
    
              if (!results.isEmpty())
              {
                  investment = results.get(0); // There should only be one.
              }
          }
          finally
          {
              if (session != null) session.close();
          }
      }
    
      return investment;
    }
  
    /**
    * A finder that uses a separate Hibernate Session and build the following structure:
    *
    * Investment
    * Investment.InvestmentAllocation ('P'rimary ones only)
    * Investment.Bucket.
    * @param segmentPK
    * @return
    */
    public static Investment[] findSeperateBy_SegmentPK_V3(Long segmentPK)
    {
      String hql = " select investment" +
                   " from Investment investment" +
                   " join fetch investment.Segment segment" +
                   " left join fetch investment.InvestmentAllocations investmentAllocation" +
                   " left join fetch investment.Buckets bucket" +
                   " where investment.SegmentFK = :segmentPK" +
                   " and (bucket.CumDollars > 0 or bucket.CumUnits > 0)";
    
      EDITMap params = new EDITMap("segmentPK", segmentPK);
    
      Investment[] investments = null;
    
      List<Investment> results = null;
    
      Session session = null;
    
      try
      {
          session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);
    
          results = SessionHelper.makeUnique(SessionHelper.executeHQL(session, hql, params, 0));
    
          if (!results.isEmpty())
          {
              investments = (Investment[]) results.toArray(new Investment[results.size()]);
          }
      }
      finally
      {
        if (session != null) session.close();
      }
    
      return investments;
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        staging.Investment stagingInvestment = new staging.Investment();

        FilteredFund filteredFund = FilteredFund.findByPK(new Long(this.investmentVO.getFilteredFundFK()));
        Fund fund = filteredFund.getFund();

        stagingInvestment.setFundType(fund.getFundType());
        stagingInvestment.setFundNumber(filteredFund.getFundNumber());

        stagingInvestment.setSegmentBase(stagingContext.getCurrentSegmentBase());

        stagingContext.getCurrentSegmentBase().addInvestment(stagingInvestment);

        stagingContext.setCurrentInvestment(stagingInvestment);

        SessionHelper.saveOrUpdate(stagingInvestment, database);

        return stagingContext;
    }
}
