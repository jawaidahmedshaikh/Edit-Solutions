/*
 * User: gfrosti
 * Date: May 18, 2007
 * Time: 2:13:12 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package contract;

import edit.common.*;

import edit.services.db.hibernate.*;

import event.*;

import java.util.*;

import org.hibernate.*;
import staging.IStaging;
import staging.StagingContext;

/**
 * @todo define
 * @author gfrosti
 */
@SuppressWarnings("serial")
public class CommissionPhase extends HibernateEntity implements IStaging
{
    /**
     * The PK.
     */
    private Long commissionPhasePK;
    
    /**
     * The FK to the associated PremiumDue.
     */
    private Long premiumDueFK;

    /**
     * The associated PremiumDue.
     */
    private PremiumDue premiumDue;

    /**
     * @todo define
     */
    private int commissionPhaseID;
    
    /**
     * @todo define
     */ 
    private EDITBigDecimal expectedMonthlyPremium;

    private EDITBigDecimal commissionTarget;
    
    /**
     * @todo define
     */
    private EDITBigDecimal prevCumExpectedMonthlyPrem;


    private EDITBigDecimal priorExpectedMonthlyPremium;

    private EDITDate effectiveDate;

    /**
     * Child entities.
     */
    private Set<CommissionablePremiumHistory> commissionablePremiumHistories = new HashSet<CommissionablePremiumHistory>();

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;

	/** Creates a new instance of CommissionPhase */
    public CommissionPhase()
    {
    }

    /**
     * @see #commissionPhasePK
     * @return Long
     */
    public Long getCommissionPhasePK()
    {
        return commissionPhasePK;
    }

    /**
     * @see #commissionPhasePK
     * @param commissionPhasePK
     */
    public void setCommissionPhasePK(Long commissionPhasePK)
    {
        this.commissionPhasePK = commissionPhasePK;
    }

    /**
     * @see #premiumDueFK
     * @return Long
     */
    public Long getPremiumDueFK()
    {
        return premiumDueFK;
    }

    /**
     * @see #premiumDueFK
     * @param premiumDueFK
     */
    public void setPremiumDueFK(Long premiumDueFK)
    {
        this.premiumDueFK = premiumDueFK;
    }

    /**
     * @see #premiumDue
     * @return
     */
    public PremiumDue getPremiumDue()
    {
        return premiumDue;
    }

    /**
     * @see #premiumDue
     * @param premiumDue
     */
    public void setPremiumDue(PremiumDue premiumDue)
    {
        this.premiumDue = premiumDue;
    }

    /**
     * @see #commissionPhaseID
     * @return int
     */
    public int getCommissionPhaseID()
    {
        return commissionPhaseID;
    }

    /**
     * @see #commissionPhaseID
     * @param commissionPhaseID
     */
    public void setCommissionPhaseID(int commissionPhaseID)
    {
        this.commissionPhaseID = commissionPhaseID;
    }

    /**
     * @see #expectedMonthlyPremium
     * @return EDITBigDecimal
     */
    public EDITBigDecimal getExpectedMonthlyPremium()
    {
        return expectedMonthlyPremium;
    }

    /**
     * @see #expectedMonthlyPremium
     * @param expectedMonthlyPremium
     */
    public void setExpectedMonthlyPremium(EDITBigDecimal expectedMonthlyPremium)
    {
        this.expectedMonthlyPremium = expectedMonthlyPremium;
    }

    public EDITBigDecimal getCommissionTarget()
    {
        return commissionTarget;
    }

    public void setCommissionTarget(EDITBigDecimal commissionTarget) 
    {
        this.commissionTarget = commissionTarget;
    }


    /**
     * @see #prevCumExpectedMonthlyPrem
     * @return EDITBigDecimal
     */
    public EDITBigDecimal getPrevCumExpectedMonthlyPrem()
    {
        return prevCumExpectedMonthlyPrem;
    }

    /**
     * @see #prevCumExpectedMonthlyPrem
     * @param prevCumExpectedMonthlyPrem
     */
    public void setPrevCumExpectedMonthlyPrem(EDITBigDecimal prevCumExpectedMonthlyPrem)
    {
        this.prevCumExpectedMonthlyPrem = prevCumExpectedMonthlyPrem;
    }

    /**
     * @see #priorExpectedMonthlyPremium
     * @return EDITBigDecimal
     */
    public EDITBigDecimal getPriorExpectedMonthlyPremium()
    {
        return priorExpectedMonthlyPremium;
    }

    /**
     * @see #priorExpectedMonthlyPremium
     * @param priorExpectedMonthlyPremium
     */
    public void setPriorExpectedMonthlyPremium(EDITBigDecimal priorExpectedMonthlyPremium )
    {
        this.priorExpectedMonthlyPremium = priorExpectedMonthlyPremium;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    /**
     * Setter.
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return CommissionPhase.DATABASE;
    }

    /**
     * @see #commissionablePremiumHistories
     * @param commissionablePremiumHistories
     */
    public void setCommissionablePremiumHistories(Set<CommissionablePremiumHistory> commissionablePremiumHistories)
    {
        this.commissionablePremiumHistories = commissionablePremiumHistories;
    }

    /**
     * @see #commissionablePremiumHistories
     */
    public Set<CommissionablePremiumHistory> getCommissionablePremiumHistories()
    {
        return commissionablePremiumHistories;
    }

    public static CommissionPhase findByPremiumDueFK(Long premiumDueFK)
    {
        CommissionPhase commissionPhase = null;

        String hql = null;

        EDITMap params = new EDITMap();

        hql = " select commissionPhase from CommissionPhase commissionPhase" +
              " where commissionPhase.PremiumDueFK = :premiumDueFK";

        params.put("premiumDueFK", premiumDueFK);

        List<CommissionPhase> results = SessionHelper.executeHQL(hql, params, CommissionPhase.DATABASE);

        if (!results.isEmpty())
        {
            commissionPhase = (CommissionPhase) results.get(0);
        }

        return commissionPhase;
    }


    /**
     * Finds the greatest CommissionPhaseID from all of the segments (base and riders) for the given contract
     * defined by the contractNumber.  There will be at least one commissionPhaseID - which
     * will be found on the base segment.
     *
     * @param contractNumber
     *
     * @return  greatest commissionPhaseID
     */
    public static String findGreatestCommissionPhaseIDByContractNumber(String contractNumber)
    {
        int commPhaseID = 0;
        int commPhaseOvrd = 0;

        EDITMap params = new EDITMap();

        String hql = " select max(segment.CommissionPhaseID)" +
                  " from Segment segment" +
                  " where segment.ContractNumber = :contractNumber";

        params = new EDITMap("contractNumber", contractNumber);

        Session session = null;

        try
        {
            session = SessionHelper.getSeparateSession(CommissionPhase.DATABASE);

            List results = SessionHelper.executeHQL(session, hql, params, 0);

            if (!results.isEmpty())
            {
                commPhaseID = new Integer(results.get(0).toString()).intValue();
            }
        }
        finally
        {
            if (session != null) session.close();
        }

        hql = " select max(segment.CommissionPhaseOverride)" +
              " from Segment segment" +
              " where segment.ContractNumber = :contractNumber";

        try
        {
            session = SessionHelper.getSeparateSession(CommissionPhase.DATABASE);

            List results = SessionHelper.executeHQL(session, hql, params, 0);

            if (!results.isEmpty())
            {
                Object o = results.get(0);
                if (o != null)
                {
                    commPhaseOvrd = new Integer(o.toString()).intValue();
                }
            }
        }
        finally
        {
            if (session != null) session.close();
        }

        if (commPhaseOvrd > commPhaseID)
        {
            commPhaseID = commPhaseOvrd;
        }

        return commPhaseID + "";
    }

    /**
     * Finds one CommissionPhase for each CommissionPhaseID for a given PremiumDueFK.  The max effectiveDate determines
     * which CommissionPhase within a CommissionPhaseID.
     *
     * @param premiumDueFK
     *
     * @return
     */
    public static CommissionPhase[] findByPremiumDueFK_MaxEffectiveDate_ForEachCommissionPhaseID(Long premiumDueFK)
    {
        EDITMap params = new EDITMap();

        String hql = " select commissionPhase from CommissionPhase commissionPhase" +
                     " where commissionPhase.PremiumDueFK = :premiumDueFK" +
                     " and commissionPhase.EffectiveDate IN " +
                     "     (select MAX(cp1.EffectiveDate) from CommissionPhase cp1" +
                     "      where cp1.PremiumDueFK = :premiumDueFK" +
                     "      group by cp1.CommissionPhaseID)";

        params.put("premiumDueFK", premiumDueFK);

        List<CommissionPhase> results = SessionHelper.executeHQL(hql, params, CommissionPhase.DATABASE);

        return (CommissionPhase[]) results.toArray(new CommissionPhase[results.size()]);
    }

    /**
     * Finds the total expectedMonthlyPremium across all CommissionPhaseIDs for a given PremiumDueFK.  For each
     * CommissionPhaseID, only the CommissionPhase with the max effectiveDate is used in the total.
     *
     * @return EDITBigDecimal value of total expectedMonthlyPremiums (1 for each CommissionPhaseID)
     */
    public static EDITBigDecimal findTotalExpectedMonthlyPremium(Long premiumDueFK)
    {
        EDITBigDecimal totalExpectedMonthlyPremium = new EDITBigDecimal("0");

        EDITMap params = new EDITMap();

        String hql = " select commissionPhase from CommissionPhase commissionPhase" +
                     " where commissionPhase.PremiumDueFK = :premiumDueFK" +
                     " and commissionPhase.EffectiveDate IN " +
                     "     (select MAX(cp1.EffectiveDate) from CommissionPhase cp1" +
                     "      where cp1.PremiumDueFK = :premiumDueFK" +
                     "      group by cp1.CommissionPhaseID)";

        params.put("premiumDueFK", premiumDueFK);

        List<CommissionPhase> results = SessionHelper.executeHQL(hql, params, CommissionPhase.DATABASE);
        Iterator it = results.iterator();

        while (it.hasNext())
        {
            CommissionPhase commissionPhase = (CommissionPhase) it.next();
            totalExpectedMonthlyPremium = totalExpectedMonthlyPremium.addEditBigDecimal(commissionPhase.getExpectedMonthlyPremium());
        }

        return totalExpectedMonthlyPremium;
    }

    /**
     * Finds the total prevCumExpectedMonthlyPremium and expectedMonthlyPremium across all CommissionPhaseIDs for a given PremiumDueFK.  For each
     * CommissionPhaseID, only the CommissionPhase with the max effectiveDate is used in the total.
     * 
     * Returning as a map so two values can be returned instead of just one as findTotalExpectedMonthlyPremium does. Since both values
     * are needed, it didn't make sense to do two distinct database calls when both values are retrieved in one. 
     * 
     * @return Map<String, EDITBigDecimal> values of total expectedMonthlyPremiums and prevCumExpectedMonthlyPremium
     */
    public static Map<String, EDITBigDecimal> findTotalExpectedMonthlyPremiums(Long premiumDueFK)
    {
        EDITBigDecimal totalPrevCumExpectedMonthlyPremium = new EDITBigDecimal("0");
        EDITBigDecimal totalExpectedMonthlyPremium = new EDITBigDecimal("0");

        EDITMap params = new EDITMap();

        String hql = " select commissionPhase from CommissionPhase commissionPhase" +
                     " where commissionPhase.PremiumDueFK = :premiumDueFK" +
                     " and commissionPhase.EffectiveDate IN " +
                     "     (select MAX(cp1.EffectiveDate) from CommissionPhase cp1" +
                     "      where cp1.PremiumDueFK = :premiumDueFK" +
                     "      group by cp1.CommissionPhaseID)";

        params.put("premiumDueFK", premiumDueFK);

        List<CommissionPhase> results = SessionHelper.executeHQL(hql, params, CommissionPhase.DATABASE);
        Iterator it = results.iterator();

        while (it.hasNext())
        {
            CommissionPhase commissionPhase = (CommissionPhase) it.next();
            totalPrevCumExpectedMonthlyPremium = totalPrevCumExpectedMonthlyPremium.addEditBigDecimal(commissionPhase.getPrevCumExpectedMonthlyPrem());
            totalExpectedMonthlyPremium = totalExpectedMonthlyPremium.addEditBigDecimal(commissionPhase.getExpectedMonthlyPremium());
        }

        Map<String, EDITBigDecimal> totalExpectedMonthlyPremiums = new HashMap<>();
        totalExpectedMonthlyPremiums.put("totalExpectedMonthlyPremium", totalExpectedMonthlyPremium);
        totalExpectedMonthlyPremiums.put("totalPrevCumExpectedMonthlyPremium", totalPrevCumExpectedMonthlyPremium);
        return totalExpectedMonthlyPremiums;
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        staging.CommissionPhase stagingCommPhase = new staging.CommissionPhase();

        stagingCommPhase.setPremiumDue(stagingContext.getCurrentPremiumDue());
        stagingCommPhase.setCommissionPhaseId(this.commissionPhaseID);
        stagingCommPhase.setExpectedMonthlyPremium(this.expectedMonthlyPremium);
        stagingCommPhase.setEffectiveDate(this.effectiveDate);
        

        stagingContext.getCurrentPremiumDue().addCommissionPhase(stagingCommPhase);

        SessionHelper.saveOrUpdate(stagingCommPhase, database);

        return stagingContext;
    }

    public static CommissionPhase[] findAllByPremiumDueFK(Long premiumDueFK)
    {
        CommissionPhase[] commissionPhases = null;
        EDITMap params = new EDITMap();

        String hql = " select commissionPhase from CommissionPhase commissionPhase" +
                     " where commissionPhase.PremiumDueFK = :premiumDueFK";

        params.put("premiumDueFK", premiumDueFK);

        List<CommissionPhase> results = SessionHelper.executeHQL(hql, params, CommissionPhase.DATABASE);

        if (!results.isEmpty())
        {
            commissionPhases = (CommissionPhase[]) results.toArray(new CommissionPhase[results.size()]);
        }

        return commissionPhases;

    }
}
