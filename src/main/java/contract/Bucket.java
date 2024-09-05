/*
 * User: gfrosti
 * Date: Jul 12, 2005
 * Time: 3:50:29 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.hibernate.*;

import event.*;

import java.util.*;

import org.hibernate.Session;

import staging.IStaging;
import staging.StagingContext;
import engine.FilteredFund;
import engine.Fund;



public class Bucket extends HibernateEntity implements IStaging
{
    public static final String BUCKETSOURCECT_TRANSFER = "Transfer";

    private Investment investment;
    private Set annualizedSubBuckets;
    private Set bucketHistories;
    private Set bucketAllocations;
    private BucketVO bucketVO;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public Bucket()
    {
        bucketVO = new BucketVO();

        this.annualizedSubBuckets = new HashSet();

        this.bucketHistories = new HashSet();
    }

    public Bucket(BucketVO bucketVO)
    {
        investment = new Investment(bucketVO.getInvestmentFK());

        this.bucketVO = bucketVO;
    }

    public Set getBucketAllocations()
    {
        return bucketAllocations;
    }

    public void setBucketAllocations(Set bucketAllocations)
    {
        this.bucketAllocations = bucketAllocations;
    }

    public Long getInvestmentFK()
    {
        return SessionHelper.getPKValue(bucketVO.getInvestmentFK());
    }

    public void setInvestmentFK(Long investmentFK)
    {
        bucketVO.setInvestmentFK(SessionHelper.getPKValue(investmentFK));
    }

    public Set getBucketHistories()
    {
        return bucketHistories;
    }

    public void setBucketHistories(Set bucketHistories)
    {
        this.bucketHistories = bucketHistories;
    }

    public Set getAnnualizedSubBuckets()
    {
        return annualizedSubBuckets;
    }

    public void setAnnualizedSubBuckets(Set annualizedSubBuckets)
    {
        this.annualizedSubBuckets = annualizedSubBuckets;
    }

    public Long getBucketPK()
    {
        return SessionHelper.getPKValue(bucketVO.getBucketPK());
    }

    public void setBucketPK(Long bucketPK)
    {
        bucketVO.setBucketPK(SessionHelper.getPKValue(bucketPK));
    }

    public EDITBigDecimal getCumDollars()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getCumDollars());
    }

    public void setCumDollars(EDITBigDecimal cumDollars)
    {
        bucketVO.setCumDollars(SessionHelper.getEDITBigDecimal(cumDollars));
    }

    public EDITBigDecimal getCumUnits()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getCumUnits());
    }

    public void setCumUnits(EDITBigDecimal cumUnits)
    {
        bucketVO.setCumUnits(SessionHelper.getEDITBigDecimal(cumUnits));
    }

    public EDITDate getDepositDate()
    {
        return SessionHelper.getEDITDate(bucketVO.getDepositDate());
    }

    public void setDepositDate(EDITDate depositDate)
    {
        bucketVO.setDepositDate(SessionHelper.getEDITDate(depositDate));
    }

    public EDITBigDecimal getDepositAmount()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getDepositAmount());
    }

    public void setDepositAmount(EDITBigDecimal depositAmount)
    {
        bucketVO.setDepositAmount(SessionHelper.getEDITBigDecimal(depositAmount));
    }

    public EDITDate getLastValuationDate()
    {
        return SessionHelper.getEDITDate(bucketVO.getLastValuationDate());
    }

    public void setLastValuationDate(EDITDate lastValuationDate)
    {
        bucketVO.setLastValuationDate(SessionHelper.getEDITDate(lastValuationDate));
    }

    public EDITBigDecimal getInterestRateOverride()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getInterestRateOverride());
    }

    public void setInterestRateOverride(EDITBigDecimal interestRateOverride)
    {
        bucketVO.setInterestRateOverride(SessionHelper.getEDITBigDecimal(interestRateOverride));
    }

    public EDITBigDecimal getBucketInterestRate()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getBucketInterestRate());
    }

    public void setBucketInterestRate(EDITBigDecimal bucketInterestRate)
    {
        bucketVO.setBucketInterestRate(SessionHelper.getEDITBigDecimal(bucketInterestRate));
    }

    public EDITBigDecimal getPriorBucketRate()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getPriorBucketRate());
    }

    public void setPriorBucketRate(EDITBigDecimal priorBucketRate)
    {
        bucketVO.setPriorBucketRate(SessionHelper.getEDITBigDecimal(priorBucketRate));
    }

    public int getDurationOverride()
    {
        return bucketVO.getDurationOverride();
    }

    public void setDurationOverride(int durationOverride)
    {
        bucketVO.setDurationOverride(durationOverride);
    }

    public EDITBigDecimal getPayoutUnits()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getPayoutUnits());
    }

    public void setPayoutUnits(EDITBigDecimal payoutUnits)
    {
        bucketVO.setPayoutUnits(SessionHelper.getEDITBigDecimal(payoutUnits));
    }

    public EDITBigDecimal getPayoutDollars()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getPayoutDollars());
    }

    public void setPayoutDollars(EDITBigDecimal payoutDollars)
    {
        bucketVO.setPayoutDollars(SessionHelper.getEDITBigDecimal(payoutDollars));
    }

    public EDITDate getRenewalDate()
    {
        return SessionHelper.getEDITDate(bucketVO.getRenewalDate());
    }

    public void setRenewalDate(EDITDate renewalDate)
    {
        bucketVO.setRenewalDate(SessionHelper.getEDITDate(renewalDate));
    }

    public EDITDate getLastRenewalDate()
    {
        return SessionHelper.getEDITDate(bucketVO.getLastRenewalDate());
    }

    public void setLastRenewalDate(EDITDate lastRenewalDate)
    {
        bucketVO.setLastRenewalDate(SessionHelper.getEDITDate(lastRenewalDate));
    }

    public EDITBigDecimal getGuarCumValue()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getGuarCumValue());
    }

    public void setGuarCumValue(EDITBigDecimal guarCumValue)
    {
        bucketVO.setGuarCumValue(SessionHelper.getEDITBigDecimal(guarCumValue));
    }

    public EDITBigDecimal getBonusAmount()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getBonusAmount());
    }

    public void setBonusAmount(EDITBigDecimal bonusAmount)
    {
        bucketVO.setBonusAmount(SessionHelper.getEDITBigDecimal(bonusAmount));
    }

    public EDITBigDecimal getIndexCapRate()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getIndexCapRate());
    }

    public void setIndexCapRate(EDITBigDecimal indexCapRate)
    {
        bucketVO.setIndexCapRate(SessionHelper.getEDITBigDecimal(indexCapRate));
    }

    public EDITBigDecimal getRebalanceAmount()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getRebalanceAmount());
    }

    public void setRebalanceAmount(EDITBigDecimal rebalanceAmount)
    {
        bucketVO.setRebalanceAmount(SessionHelper.getEDITBigDecimal(rebalanceAmount));
    }

    public EDITBigDecimal getDepositAmountGain()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getDepositAmount());
    }

    public void setDepositAmountGain(EDITBigDecimal depositAmountGain)
    {
        bucketVO.setDepositAmountGain(SessionHelper.getEDITBigDecimal(depositAmountGain));
    }

    public EDITBigDecimal getUnearnedInterest()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getUnearnedInterest());
    }

    public void setUnearnedInterest(EDITBigDecimal unearnedInterest)
    {
        bucketVO.setUnearnedInterest(SessionHelper.getEDITBigDecimal(unearnedInterest));
    }

    public EDITBigDecimal getBonusIntRate()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getBonusIntRate());
    }

    public void setBonusIntRate(EDITBigDecimal bonusIntRate)
    {
        bucketVO.setBonusIntRate(SessionHelper.getEDITBigDecimal(bonusIntRate));
    }

    public int getBonusIntRateDur()
    {
        return bucketVO.getBonusIntRateDur();
    }

    public void setBonusIntRateDur(int bonusIntRateDur)
    {
        bucketVO.setBonusIntRateDur(bonusIntRateDur);
    }

    public EDITDate getLockupEndDate()
    {
        return SessionHelper.getEDITDate(bucketVO.getLockupEndDate());
    }

    public void setLockupEndDate(EDITDate lockupEndDate)
    {
        bucketVO.setLockupEndDate(SessionHelper.getEDITDate(lockupEndDate));
    }

    public EDITBigDecimal getLoanCumDollars()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getLoanCumDollars());
    }

    public void setLoanCumDollars(EDITBigDecimal loanCumDollars)
    {
        bucketVO.setLoanCumDollars(SessionHelper.getEDITBigDecimal(loanCumDollars));
    }

    public EDITBigDecimal getLoanPrincipalRemaining()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getLoanPrincipalRemaining());
    }

    public void setLoanPrincipalRemaining(EDITBigDecimal loanPrincipalRemaining)
    {
        bucketVO.setLoanPrincipalRemaining(SessionHelper.getEDITBigDecimal(loanPrincipalRemaining));
    }

    public String getBucketSourceCT()
    {
        return bucketVO.getBucketSourceCT();
    }

    public void setBucketSourceCT(String bucketSourceCT)
    {
        bucketVO.setBucketSourceCT(bucketSourceCT);
    }

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getLoanInterestRate()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getLoanInterestRate());
    } //-- java.math.BigDecimal getLoanInterestRate()

    /**
     * Setter
     * @param loanInterestRate
     */
    public void setLoanInterestRate(EDITBigDecimal loanInterestRate)
    {
        bucketVO.setLoanInterestRate(SessionHelper.getEDITBigDecimal(loanInterestRate));
    } //-- void setLoanInterestRate(java.math.BigDecimal)

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getPreviousLoanInterestRate()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getPreviousLoanInterestRate());
    } //-- java.math.BigDecimal getPreviousLoanInterestRate()

    /**
     * Setter
     * @param previousLoanInterestRate
     */
    public void setPreviousLoanInterestRate(EDITBigDecimal previousLoanInterestRate)
    {
        bucketVO.setPreviousLoanInterestRate(SessionHelper.getEDITBigDecimal(previousLoanInterestRate));
    } //-- void setPreviousLoanInterestRate(java.math.BigDecimal)

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getAccruedLoanInterest()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getAccruedLoanInterest());
    } //-- java.math.BigDecimal getAccruedLoanInterest()

    /**
     * Setter
     * @param accruedLoanInterestRate
     */
    public void setAccruedLoanInterest(EDITBigDecimal accruedLoanInterest)
    {
        bucketVO.setAccruedLoanInterest(SessionHelper.getEDITBigDecimal(accruedLoanInterest));
    } //-- void setAccruedLoanInterestRate(java.math.BigDecimal)

    public EDITBigDecimal getUnearnedLoanInterest()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getUnearnedLoanInterest());
    }

    public void setUnearnedLoanInterest(EDITBigDecimal unearnedLoanInterest)
    {
        bucketVO.setUnearnedLoanInterest(SessionHelper.getEDITBigDecimal(unearnedLoanInterest));
    }

    public EDITDate getInterestPaidThroughDate()
    {
        return SessionHelper.getEDITDate(bucketVO.getInterestPaidThroughDate());
    }

    public void setInterestPaidThroughDate(EDITDate interestPaidThroughDate)
    {
        bucketVO.setInterestPaidThroughDate(SessionHelper.getEDITDate(interestPaidThroughDate));
    }

    public EDITBigDecimal getBilledLoanInterest()
    {
        return SessionHelper.getEDITBigDecimal(bucketVO.getBilledLoanInterest());
    }

    public void setBilledLoanInterest(EDITBigDecimal billedLoanInterest)
    {
        bucketVO.setBilledLoanInterest(SessionHelper.getEDITBigDecimal(billedLoanInterest));
    }

    public Investment getInvestment()
    {
        return investment;
    }

    public void setInvestment(Investment investment)
    {
        this.investment = investment;
    }

    public void setVO(BucketVO bucketVO)
    {
        this.bucketVO = bucketVO;
    }

    public BucketVO getVO()
    {
        return this.bucketVO;
    }

    /**
     * Adds a BucketHistory to the set of children
     * @param bucketHistory
     */
    public void addBucketHistory(BucketHistory bucketHistory)
    {
        this.getBucketHistories().add(bucketHistory);

        bucketHistory.setBucket(this);

        SessionHelper.saveOrUpdate(bucketHistory, Bucket.DATABASE);
    }

    /**
     * Removes a BucketHistory from the set of children
     * @param bucketHistory
     */
    public void removeBucketHistory(BucketHistory bucketHistory)
    {
        this.getBucketHistories().remove(bucketHistory);

        bucketHistory.setBucket(null);

        SessionHelper.saveOrUpdate(bucketHistory, Bucket.DATABASE);
    }

    /**
     * Adds a AnnualizedSubBucket to the set of children
     * @param annualizedSubBucket
     */
    public void addAnnualizedSubBucket(AnnualizedSubBucket annualizedSubBucket)
    {
        this.getAnnualizedSubBuckets().add(annualizedSubBucket);

        annualizedSubBucket.setBucket(this);

        SessionHelper.saveOrUpdate(annualizedSubBucket, Bucket.DATABASE);
    }

    /**
     * Removes a AnnualizedSubBucket from the set of children
     * @param annualizedSubBucket
     */
    public void removeAnnualizedSubBucket(AnnualizedSubBucket annualizedSubBucket)
    {
        this.getAnnualizedSubBuckets().remove(annualizedSubBucket);

        annualizedSubBucket.setBucket(null);

        SessionHelper.saveOrUpdate(annualizedSubBucket, Bucket.DATABASE);
    }

    /**
     * Adds a BucketAllocation to the set of children
     * @param bucketAllocation
     */
    public void addBucketAllocation(BucketAllocation bucketAllocation)
    {
        this.getBucketAllocations().add(bucketAllocation);

        bucketAllocation.setBucket(this);

        SessionHelper.saveOrUpdate(bucketAllocation, Bucket.DATABASE);
    }

    /**
     * Removes a BucketAllocation from the set of children
     * @param bucketAllocation
     */
    public void removeBucketAllocation(BucketAllocation bucketAllocation)
    {
        this.getBucketAllocations().remove(bucketAllocation);

        bucketAllocation.setBucket(null);

        SessionHelper.saveOrUpdate(bucketAllocation, Bucket.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Bucket.DATABASE;
    }

    /**
     * Find by primary key
     *
     * @param bucketPK
     *
     * @return  single Bucket
     */
    public static Bucket findByPK(Long bucketPK)
    {
        return (Bucket) SessionHelper.get(Bucket.class, bucketPK, Bucket.DATABASE);
    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Bucket.DATABASE);
    }

    public void hDelete()
    {
        SessionHelper.delete(this, Bucket.DATABASE);
    }


    public static Bucket[] findBy_SegmentPK_(Long segmentPK)
    {
        String hql = "select bucket from Bucket bucket "
                   + " join fetch bucket.Investment investment"
                   + " where investment.SegmentFK = :segmentFK";

        Map params = new HashMap();
        params.put("segmentFK", segmentPK);

        List<Investment> results = SessionHelper.executeHQL(hql, params, Bucket.DATABASE);

        return results.toArray(new Bucket[results.size()]);        
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        staging.InvestmentBucket stagingInvestmentBucket = new staging.InvestmentBucket();

        stagingInvestmentBucket.setAccruedLoanInterest(this.getAccruedLoanInterest());
        stagingInvestmentBucket.setBonusAmount(this.getBonusAmount());
        stagingInvestmentBucket.setBonusIntRate(this.getBonusIntRate());
        stagingInvestmentBucket.setBonusIntRateDur(this.getBonusIntRateDur());
        stagingInvestmentBucket.setBucketInterestRate(this.getBucketInterestRate());
        stagingInvestmentBucket.setBucketSource(this.getBucketSourceCT());
        stagingInvestmentBucket.setCumDollars(this.getCumDollars());
        stagingInvestmentBucket.setCumUnits(this.getCumUnits());
        stagingInvestmentBucket.setDepositAmount(this.getDepositAmount());
        stagingInvestmentBucket.setDepositAmountGain(this.getDepositAmountGain());
        stagingInvestmentBucket.setDepositDate(this.getDepositDate());
        stagingInvestmentBucket.setDurationOverride(this.getDurationOverride());
        stagingInvestmentBucket.setGuarCumValue(this.getGuarCumValue());
        stagingInvestmentBucket.setIndexCapRate(this.getIndexCapRate());
        stagingInvestmentBucket.setInterestRateOverride(this.getInterestRateOverride());
        stagingInvestmentBucket.setLastRenewalDate(this.getLastRenewalDate());
        stagingInvestmentBucket.setLastValuationDate(this.getLastValuationDate());
        stagingInvestmentBucket.setLoanCumDollars(this.getLoanCumDollars());
        stagingInvestmentBucket.setLoanInterestRate(this.getLoanInterestRate());
        stagingInvestmentBucket.setLoanPrincipalRemaining(this.getLoanPrincipalRemaining());
        stagingInvestmentBucket.setLockupEndDate(this.getLockupEndDate());
        stagingInvestmentBucket.setPayoutDollars(this.getPayoutDollars());
        stagingInvestmentBucket.setPayoutUnits(this.getPayoutUnits());
        stagingInvestmentBucket.setPreviousLoanInterestRate(this.getPreviousLoanInterestRate());
        stagingInvestmentBucket.setPriorBucketRate(this.getPriorBucketRate());
        stagingInvestmentBucket.setRebalanceAmount(this.getRebalanceAmount());
        stagingInvestmentBucket.setRenewalDate(this.getRenewalDate());
        stagingInvestmentBucket.setUnearnedInterest(this.getUnearnedInterest());
        stagingInvestmentBucket.setUnearnedLoanInterest(this.getUnearnedLoanInterest());
        stagingInvestmentBucket.setInterestPaidThroughDate(this.getInterestPaidThroughDate());
        stagingInvestmentBucket.setBilledLoanInterest(this.getBilledLoanInterest());

        stagingInvestmentBucket.setInvestment(stagingContext.getCurrentInvestment());

        stagingContext.getCurrentInvestment().addInvestmentBucket(stagingInvestmentBucket);

        SessionHelper.saveOrUpdate(stagingInvestmentBucket, database);

        return stagingContext;
    }
    
    public static EDITBigDecimal findTargetFieldSum_BySegmentPK(Long segmentPK, String targetFieldName)
    {
          String hql = "select sum(bucket." + targetFieldName + ") from BucketHistory bucketHistory" +
                  	   " join bucketHistory.Bucket bucket" +
                       " join bucketHistory.EDITTrxHistory editTrxHistory" +
                       " join editTrxHistory.EDITTrx editTrx" +
                       " join editTrx.ClientSetup clientSetup" +
                       " join clientSetup.ContractSetup contractSetup" +
                       " where contractSetup.SegmentFK = :segmentPK" +
                       " and editTrx.PendingStatus = 'H' " +
                       " and editTrx.Status in ('N', 'A') ";

          Map params = new EDITMap("segmentPK", segmentPK);

          Session session = null;

          List<Double> results = null;
          
          EDITBigDecimal totalLoanPrinciple = new EDITBigDecimal();

          try
          {
            session = SessionHelper.getSeparateSession(BucketHistory.DATABASE);

            results = SessionHelper.executeHQL(session, hql, params, 0);
          } catch (Exception e) {
        	  System.out.println();
        	  throw e;
          } finally
          {
            if (session != null) session.close();
          }

          if (!results.isEmpty()) {
        	  Object result = results.get(0);

              if (result != null) {
            	  totalLoanPrinciple = new EDITBigDecimal(result.toString());
              }
          } 
        	  
          return totalLoanPrinciple;
    }
    
    public static Bucket[] findBucket_ByTrxType(Long segmentPK, EDITDate effectiveDate, String transationTypeCT)
    {
          String hql = "select distinct bucket from Bucket bucket" + 
          			   " join bucket.BucketHistories bucketHistory" + 
          			   " join bucketHistory.EDITTrxHistory editTrxHistory" +
                       " join editTrxHistory.EDITTrx editTrx" +
                       " join editTrx.ClientSetup clientSetup" +
                       " join clientSetup.ContractSetup contractSetup" +
                       " where contractSetup.SegmentFK = :segmentPK" +
                       " and editTrx.EffectiveDate = :effectiveDate " +
	                   " and editTrx.TransactionTypeCT = :transationTypeCT " + 
                       " and editTrx.Status in ('N', 'A') " +
                       " and editTrx.PendingStatus = 'H' ";
          
          Map params = new EDITMap("segmentPK", segmentPK)
                  .put("effectiveDate", effectiveDate)
          		  .put("transationTypeCT", transationTypeCT);

          Session session = null;

          List<Bucket> results = null;

          try
          {
            session = SessionHelper.getSeparateSession(Bucket.DATABASE);

            results = SessionHelper.executeHQL(session, hql, params, 0);
          } catch (Exception e) {
        	  System.out.println();
        	  throw e;
          } finally
          {
            if (session != null) session.close();
          }

          if (results.isEmpty()) {
              return null;
          } else {
        	  return results.toArray(new Bucket[results.size()]);
          }
    }
}
