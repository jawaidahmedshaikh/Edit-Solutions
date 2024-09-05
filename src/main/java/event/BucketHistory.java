/*
 * User: gfrosti
 * Date: Jun 29, 2005
 * Time: 3:38:34 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import contract.*;

import edit.common.*;
import edit.common.vo.*;
import edit.services.db.hibernate.*;

import java.util.*;

import org.hibernate.Session;


public class BucketHistory extends HibernateEntity
{
    private EDITTrxHistory editTrxHistory;
    private Set bucketChargeHistories;
    private Bucket bucket;

    private BucketHistoryVO bucketHistoryVO;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;
    

    public BucketHistory()
    {
        bucketHistoryVO = new BucketHistoryVO();
    }

    public Long getEDITTrxHistoryFK()
    {
        return SessionHelper.getPKValue(bucketHistoryVO.getEDITTrxHistoryFK());
    }

    public void setEDITTrxHistoryFK(Long editTrxHistoryFK)
    {
        bucketHistoryVO.setEDITTrxHistoryFK(SessionHelper.getPKValue(editTrxHistoryFK));
    }

    public Long getBucketFK()
    {
        return SessionHelper.getPKValue(bucketHistoryVO.getBucketFK());
    }

    public void setBucketFK(Long bucketFK)
    {
        bucketHistoryVO.setBucketFK(SessionHelper.getPKValue(bucketFK));
    }

    /**
     * Getter.
     * @return
     */
    public Long getHedgeFundInvestmentFK()
    {
        return SessionHelper.getPKValue(bucketHistoryVO.getHedgeFundInvestmentFK());
    }

    /**
     * Setter.
     * @param hedgeFundInvestmentFK
     */
    public void setHedgeFundInvestmentFK(Long hedgeFundInvestmentFK)
    {
        bucketHistoryVO.setHedgeFundInvestmentFK(SessionHelper.getPKValue(hedgeFundInvestmentFK));
    }

    public Bucket getBucket()
    {
        return bucket;
    }

    public void setBucket(Bucket bucket)
    {
        this.bucket = bucket;
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
     * Getter
     * @return  set of bucketChargeHistories
     */
    public Set getBucketChargeHistories()
    {
        return bucketChargeHistories;
    }

    /**
     * Setter
     * @param bucketChargeHistories      set of bucketChargeHistories
     */
    public void setBucketChargeHistories(Set bucketChargeHistories)
    {
        this.bucketChargeHistories = bucketChargeHistories;
    }

    /**
     * Adds a BucketChargeHistory to the set of children
     * @param bucketChargeHistory
     */
    public void addBucketChargeHistory(BucketChargeHistory bucketChargeHistory)
    {
        this.getBucketChargeHistories().add(bucketChargeHistory);

        bucketChargeHistory.setBucketHistory(this);

        SessionHelper.saveOrUpdate(bucketChargeHistory, BucketHistory.DATABASE);
    }

    /**
     * Removes a BucketChargeHistory from the set of children
     * @param bucketChargeHistory
     */
    public void removeBucketChargeHistory(BucketChargeHistory bucketChargeHistory)
    {
        this.getBucketChargeHistories().remove(bucketChargeHistory);

        bucketChargeHistory.setBucketHistory(null);

        SessionHelper.saveOrUpdate(bucketChargeHistory, BucketHistory.DATABASE);
    }


    /**
     * Setter.
     * @param editTrxHistory
     */
    public void setEDITTrxHistory(EDITTrxHistory editTrxHistory)
    {
        this.editTrxHistory = editTrxHistory;
    }

    public Long getBucketHistoryPK()
    {
        return SessionHelper.getPKValue(bucketHistoryVO.getBucketHistoryPK());
    }

    public void setBucketHistoryPK(Long bucketHistoryPK)
    {
        bucketHistoryVO.setBucketHistoryPK(SessionHelper.getPKValue(bucketHistoryPK));
    }

    public EDITBigDecimal getDollars()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getDollars());
    }

    public void setDollars(EDITBigDecimal dollars)
    {
        bucketHistoryVO.setDollars(SessionHelper.getEDITBigDecimal(dollars));
    }

    public EDITBigDecimal getUnits()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getUnits());
    }

    public void setUnits(EDITBigDecimal units)
    {
        bucketHistoryVO.setUnits(SessionHelper.getEDITBigDecimal(units));
    }

    public EDITDate getPreviousValuationDate()
    {
        return SessionHelper.getEDITDate(bucketHistoryVO.getPreviousValuationDate());
    }

    public void setPreviousValuationDate(EDITDate previousValuationDate)
    {
        bucketHistoryVO.setPreviousValuationDate(SessionHelper.getEDITDate(previousValuationDate));
    }

    public EDITBigDecimal getPreviousValue()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getPreviousValue());
    }

    public void setPreviousValue(EDITBigDecimal previousValue)
    {
        bucketHistoryVO.setPreviousValue(SessionHelper.getEDITBigDecimal(previousValue));
    }
    
    public EDITBigDecimal getPreviousBucketRate()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getPreviousBucketRate());
    }

    public void setPreviousBucketRate(EDITBigDecimal previousBucketRate)
    {
        bucketHistoryVO.setPreviousBucketRate(SessionHelper.getEDITBigDecimal(previousBucketRate));
    }

    public EDITBigDecimal getPreviousPayoutValue()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getPreviousPayoutValue());
    }

    public void setPreviousPayoutValue(EDITBigDecimal previousPayoutValue)
    {
        bucketHistoryVO.setPreviousPayoutValue(SessionHelper.getEDITBigDecimal(previousPayoutValue));
    }

    public EDITBigDecimal getInterestEarnedGuaranteed()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getInterestEarnedGuaranteed());
    }

    public void setInterestEarnedGuaranteed(EDITBigDecimal interestEarnedGuaranteed)
    {
        bucketHistoryVO.setInterestEarnedGuaranteed(SessionHelper.getEDITBigDecimal(interestEarnedGuaranteed));
    }

    public EDITBigDecimal getInterestEarnedCurrent()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getInterestEarnedCurrent());
    }

    public void setInterestEarnedCurrent(EDITBigDecimal interestEarnedCurrent)
    {
        bucketHistoryVO.setInterestEarnedCurrent(SessionHelper.getEDITBigDecimal(interestEarnedCurrent));
    }

    public EDITBigDecimal getCumDollars()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getCumDollars());
    }

    public void setCumDollars(EDITBigDecimal cumDollars)
    {
        bucketHistoryVO.setCumDollars(SessionHelper.getEDITBigDecimal(cumDollars));
    }

    public EDITBigDecimal getCumUnits()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getCumUnits());
    }

    public void setCumUnits(EDITBigDecimal cumUnits)
    {
        bucketHistoryVO.setCumUnits(SessionHelper.getEDITBigDecimal(cumUnits));
    }

    public String getToFromStatus()
    {
        return bucketHistoryVO.getToFromStatus();
    }

    public void setToFromStatus(String toFromStatus)
    {
        bucketHistoryVO.setToFromStatus(toFromStatus);
    }

    public EDITBigDecimal getBonusInterestEarned()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getBonusInterestEarned());
    }

    public void setBonusInterestEarned(EDITBigDecimal bonusInterestEarned)
    {
        bucketHistoryVO.setBonusInterestEarned(SessionHelper.getEDITBigDecimal(bonusInterestEarned));
    }

    public EDITBigDecimal getPreviousGuaranteeValue()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getPreviousGuaranteeValue());
    }

    public void setPreviousGuaranteeValue(EDITBigDecimal previousGuaranteeValue)
    {
        bucketHistoryVO.setPreviousGuaranteeValue(SessionHelper.getEDITBigDecimal(previousGuaranteeValue));
    }

    public EDITBigDecimal getBonusAmount()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getBonusAmount());
    }

    public void setBonusAmount(EDITBigDecimal bonusAmount)
    {
        bucketHistoryVO.setBonusAmount(SessionHelper.getEDITBigDecimal(bonusAmount));
    }

    public EDITBigDecimal getPriorRebalanceAmount()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getPriorRebalanceAmount());
    }

    public void setPriorRebalanceAmount(EDITBigDecimal priorRebalanceAmount)
    {
        bucketHistoryVO.setPriorRebalanceAmount(SessionHelper.getEDITBigDecimal(priorRebalanceAmount));
    }

    public EDITBigDecimal getBucketLiability()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getBucketLiability());
    }

    public void setBucketLiability(EDITBigDecimal bucketLiability)
    {
        bucketHistoryVO.setBucketLiability(SessionHelper.getEDITBigDecimal(bucketLiability));
    }

    public String getInterimAccountIndicator()
    {
        return bucketHistoryVO.getInterimAccountIndicator();
    }

    public void setInterimAccountIndicator(String interimAccountIndicator)
    {
        bucketHistoryVO.setInterimAccountIndicator(interimAccountIndicator);
    }

    public String getHoldingAccountIndicator()
    {
        return bucketHistoryVO.getHoldingAccountIndicator();
    }

    public void setHoldingAccountIndicator(String holdingAccountIndicator)
    {
        bucketHistoryVO.setHoldingAccountIndicator(holdingAccountIndicator);
    }

    public void setVO(BucketHistoryVO bucketHistoryVO)
    {
        this.bucketHistoryVO = bucketHistoryVO;
    }

	/**
     * getter
     * @return
     */
    public EDITBigDecimal getGuarCumValue()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getGuarCumValue());
    } //-- java.math.BigDecimal getGuarCumValue()

    /**
     * setter
     * @param guarCumValue
     */
    public void setGuarCumValue(EDITBigDecimal guarCumValue)
    {
        bucketHistoryVO.setGuarCumValue(SessionHelper.getEDITBigDecimal(guarCumValue));
    } //-- void setGuarCumValue(java.math.BigDecimal)


    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getLoanPrincipalDollars()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getLoanPrincipalDollars());
    }

    /**
     * Setter.
     * @param loanDollars
     */
    public void setLoanPrincipalDollars(EDITBigDecimal loanPrincipalDollars)
    {
        bucketHistoryVO.setLoanPrincipalDollars(SessionHelper.getEDITBigDecimal(loanPrincipalDollars));
    }

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getLoanInterestDollars()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getLoanInterestDollars());
    } //-- java.math.BigDecimal getLoanInterestDollars()

    /**
     * Setter
     * @param loanInterestDollars
     */
    public void setLoanInterestDollars(EDITBigDecimal loanInterestDollars)
    {
        bucketHistoryVO.setLoanInterestDollars(SessionHelper.getEDITBigDecimal(loanInterestDollars));
    } //-- void setLoanInterestDollars(java.math.BigDecimal)

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPreviousLoanCumDollars()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getPreviousLoanCumDollars());
    }

    /**
     * Setter.
     * @param prevLoanCumDollars
     */
    public void setPreviousLoanCumDollars(EDITBigDecimal previousLoanCumDollars)
    {
        bucketHistoryVO.setPreviousLoanCumDollars(SessionHelper.getEDITBigDecimal(previousLoanCumDollars));
    }

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getPreviousLoanInterestDollars()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getPreviousLoanInterestDollars());
    } //-- java.math.BigDecimal getPreviousLoanInterestDollars()

    /**
     * Setter
     * @param previousLoanInterestDollars
     */
    public void setPreviousLoanInterestDollars(EDITBigDecimal previousLoanInterestDollars)
    {
        bucketHistoryVO.setPreviousLoanInterestDollars(SessionHelper.getEDITBigDecimal(previousLoanInterestDollars));
    } //-- void setPreviousLoanInterestDollars(java.math.BigDecimal)

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPreviousLoanPrincipalRemaining()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getPreviousLoanPrincipalRemaining());
    }

    /**
     * Setter.
     * @param prevLoanPrincRemaining
     */
    public void setPreviousLoanPrincipalRemaining(EDITBigDecimal prevLoanPrincipalRemaining)
    {
        bucketHistoryVO.setPreviousLoanPrincipalRemaining(SessionHelper.getEDITBigDecimal(prevLoanPrincipalRemaining));
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCumLoanDollars()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getCumLoanDollars());
    }

    /**
     * Setter.
     * @param cumLoanDollars
     */
    public void setCumLoanDollars(EDITBigDecimal cumLoanDollars)
    {
        bucketHistoryVO.setCumLoanDollars(SessionHelper.getEDITBigDecimal(cumLoanDollars));
    }

    /**
     * Getter.
     * @return
     */
    public String getLoanDollarsToFromStatus()
    {
        return bucketHistoryVO.getLoanDollarsToFromStatus();
    }

    /**
     * Setter.
     * @param loanDollarstoFromStatus
     */
    public void setLoanDollarsToFromStatus(String loanDollarstoFromStatus)
    {
        bucketHistoryVO.setLoanDollarsToFromStatus(loanDollarstoFromStatus);
    }

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getLoanInterestLiability()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getLoanInterestLiability());
    } //-- java.math.BigDecimal getLoanInterestLiability()

    /**
     * Setter
     * @param loanInterestLiability
     */
    public void setLoanInterestLiability(EDITBigDecimal loanInterestLiability)
    {
        bucketHistoryVO.setLoanInterestLiability(SessionHelper.getEDITBigDecimal(loanInterestLiability));
    } //-- void setLoanInterestLiability(java.math.BigDecimal)

    public EDITBigDecimal getPrevUnearnedLoanInterest()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getPrevUnearnedLoanInterest());
    }

    public void setPrevUnearnedLoanInterest(EDITBigDecimal prevUnearnedLoanInterest)
    {
        bucketHistoryVO.setPrevUnearnedLoanInterest(SessionHelper.getEDITBigDecimal(prevUnearnedLoanInterest));
    }

    public EDITDate getPrevInterestPaidThroughDate()
    {
        return SessionHelper.getEDITDate(bucketHistoryVO.getPrevInterestPaidThroughDate());
    }

    public void setPrevInterestPaidThroughDate(EDITDate prevInterestPaidThroughDate)
    {
        bucketHistoryVO.setPrevInterestPaidThroughDate(SessionHelper.getEDITDate(prevInterestPaidThroughDate));
    }

    public EDITBigDecimal getUnearnedInterestCredit()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getUnearnedInterestCredit());
    }

    public void setUnearnedInterestCredit(EDITBigDecimal unearnedInterestCredit)
    {
        bucketHistoryVO.setUnearnedInterestCredit(SessionHelper.getEDITBigDecimal(unearnedInterestCredit));
    }

    public EDITBigDecimal getOverShortAmount()
    {
        return SessionHelper.getEDITBigDecimal(bucketHistoryVO.getOverShortAmount());
    }

    public void setOverShortAmount(EDITBigDecimal overShortAmount)
    {
        bucketHistoryVO.setOverShortAmount(SessionHelper.getEDITBigDecimal(overShortAmount));
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, BucketHistory.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, BucketHistory.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return BucketHistory.DATABASE;
    }

    /**
     * Originally in BucketHistoryDAO.findByEditTrxHistoryPK
     * @param editTrxHistoryPK
     * @return
     */
    public static BucketHistory[] findBy_EDITTrxHistoryPK(Long editTrxHistoryPK)
    {
        String hql = " select bucketHistory from BucketHistory bucketHistory" +
                    " where bucketHistory.EDITTrxHistoryFK = :editTrxHistoryPK";

        Map params = new HashMap();

        params.put("editTrxHistoryPK", editTrxHistoryPK);

        List results = SessionHelper.executeHQL(hql, params, BucketHistory.DATABASE);

        return (BucketHistory[]) results.toArray(new BucketHistory[results.size()]);
    }
    
    public static EDITBigDecimal findLoanPrincipleSum_ByTrxType(Long segmentPK, EDITDate effectiveDate, String transactionTypeCT)
    {
          String hql = "select sum(bucketHistory.LoanPrincipalDollars) from BucketHistory bucketHistory" +
                       " join bucketHistory.EDITTrxHistory editTrxHistory" +
                       " join editTrxHistory.EDITTrx editTrx" +
                       " join editTrx.ClientSetup clientSetup" +
                       " join clientSetup.ContractSetup contractSetup" +
                       " where contractSetup.SegmentFK = :segmentPK" +
                       " and editTrx.EffectiveDate = :effectiveDate " +
	                   " and editTrx.TransactionTypeCT = :transactionTypeCT " +
	                   " and editTrx.PendingStatus = 'H' " +
	                   " and editTrx.Status in ('N', 'A') ";

          Map params = new EDITMap("segmentPK", segmentPK)
                  .put("effectiveDate", effectiveDate)
          		  .put("transactionTypeCT", transactionTypeCT);

          Session session = null;

          List<Double> results = null;
          
          EDITBigDecimal totalLoanPrincipleDollars = new EDITBigDecimal();

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
            	  totalLoanPrincipleDollars = new EDITBigDecimal(result.toString());
              }
          } 
        	  
          return totalLoanPrincipleDollars;
    }
    
    public static EDITBigDecimal findCumLoanDollarsSum_ByTrxType(Long segmentPK, EDITDate effectiveDate, String transactionTypeCT)
    {
          String hql = "select sum(bucketHistory.CumLoanDollars) from BucketHistory bucketHistory" +
                       " join bucketHistory.EDITTrxHistory editTrxHistory" +
                       " join editTrxHistory.EDITTrx editTrx" +
                       " join editTrx.ClientSetup clientSetup" +
                       " join clientSetup.ContractSetup contractSetup" +
                       " where contractSetup.SegmentFK = :segmentPK" +
                       " and editTrx.EffectiveDate = :effectiveDate " +
	                   " and editTrx.TransactionTypeCT = :transactionTypeCT " +
	                   " and editTrx.PendingStatus = 'H' " +
	                   " and editTrx.Status in ('N', 'A') ";

          Map params = new EDITMap("segmentPK", segmentPK)
                  .put("effectiveDate", effectiveDate)
          		  .put("transactionTypeCT", transactionTypeCT);

          Session session = null;

          List<Double> results = null;
          
          EDITBigDecimal totalCumLoanDollars = new EDITBigDecimal();

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
            	  totalCumLoanDollars = new EDITBigDecimal(result.toString());
              }
          } 
        	  
          return totalCumLoanDollars;
    }
    
    public static BucketHistory[] findBucketHistory_ByTrxTypeEffectiveDate(Long segmentPK, EDITDate effectiveDate, String transactionTypeCT)
    {
          String hql = "select bucketHistory from BucketHistory bucketHistory" +
                       " join bucketHistory.EDITTrxHistory editTrxHistory" +
                       " join editTrxHistory.EDITTrx editTrx" +
                       " join editTrx.ClientSetup clientSetup" +
                       " join clientSetup.ContractSetup contractSetup" +
                       " where contractSetup.SegmentFK = :segmentPK" +
                       " and editTrx.EffectiveDate = :effectiveDate " +
	                   " and editTrx.TransactionTypeCT = :transactionTypeCT " +
	                   " and editTrx.PendingStatus = 'H' " +
	                   " and editTrx.Status in ('N', 'A') ";

          Map params = new EDITMap("segmentPK", segmentPK)
                  .put("effectiveDate", effectiveDate)
          		  .put("transactionTypeCT", transactionTypeCT);

          Session session = null;
          
          List results = SessionHelper.executeHQL(hql, params, BucketHistory.DATABASE);

          return (BucketHistory[]) results.toArray(new BucketHistory[results.size()]);
    }

    public static BucketHistory findBucketHistory_Latest(Long segmentPK)
    {
          String hql = "select bucketHistory from BucketHistory bucketHistory" +
                       " join bucketHistory.EDITTrxHistory editTrxHistory" +
                       " join editTrxHistory.EDITTrx editTrx" +
                       " join editTrx.ClientSetup clientSetup" +
                       " join clientSetup.ContractSetup contractSetup" +
                       " where contractSetup.SegmentFK = :segmentPK" +
	                   //" and editTrx.PendingStatus = 'H' " +
	                  // " and editTrx.Status in ('N', 'A') " +
	                   " order by editTrx.EffectiveDate, editTrx.EDITTrxPK";


          Map params = new EDITMap("segmentPK", segmentPK);

          Session session = null;
          
          List results = SessionHelper.executeHQL(hql, params, BucketHistory.DATABASE);

          return (BucketHistory) results.toArray(new BucketHistory[results.size()])[0];
    }
}
