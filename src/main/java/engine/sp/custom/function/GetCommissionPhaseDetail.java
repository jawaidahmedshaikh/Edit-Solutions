/*
 * User: sprasad
 * Date: Jun 26, 2007
 * Time: 12:34:00 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.sp.custom.function;

import contract.*;

import edit.services.db.hibernate.SessionHelper;
import edit.common.*;

import engine.sp.*;

import java.lang.reflect.Array;

import java.util.*;
import java.util.Set;

import org.hibernate.Session;
import fission.utility.*;

/**
 * Custom function to create CommissionPhase detail information and place the data in working storage.
 * 
 * Information received from working storage.
 * 
 * SegmentPK        Segment identifier for which the CommissionPhase detail information requested.
 * CommissionPhase  CommissionPhase identifier (numeric value). 
 * NumberCommPhases Dictates Number of CommissionPhases - This will be placed only when CommissionPhases has value of zero. 
 * 
 * Information placed in working storage.
 * CommissionPhase detail vector.
 * 
 * When the CommissionPhase has non zero value, the corresponding CommissionPhase detail information will be placed
 * in working storage. 
 * 
 * When the CommissionPhase has a value of zero, then looks at NumberCommPhases value in working storage
 * and retrieves all CommissionPhases detail information till NumberCommPhases.
 * Example: CommissionPhase = 0 and NumberCommPhases = 3
 * CommissionPhase detail of CommissionPhase 1,2 and 3 are placed in working storage.
 * 
 * CommissionPhaseID is a numeric value. Always starts with 1 and increments by 1.
 */
public class GetCommissionPhaseDetail implements FunctionCommand
{
    private static final String PARAM_SEGMENTPK = "SegmentPK";
    private static final String PARAM_COMMISSION_PHASE = "CommissionPhase";
    private static final String PARAM_NUMBER_COMM_PHASES = "NumberCommPhases";

    private static final String COMMISSION_PHASE_DETAIL = "CommissionPhaseDetail";
    private static final String PREMIUMDUE_DETAIL = "PremiumDueDetail";

    private ScriptProcessor scriptProcessor;

    /**
     * Constructor.
     * @param scriptProcessor ScriptProcessor
     */
    public GetCommissionPhaseDetail(ScriptProcessor scriptProcessor)
    {
        this.scriptProcessor = scriptProcessor;
    }

    /**
     * Getter.
     * @return
     */
    private ScriptProcessor getScriptProcessor()
    {
        return scriptProcessor;
    }
    
    /**
     * Getter.
     * @return
     */
    private Map<String, String> getWorkingStorage()
    {
      return getScriptProcessor().getWS();
    }
    
    /**
     * Return the working storage value for the specified parameter name.
     * @param paramName
     * @return
     */
    protected String getWSValue(String paramName)
    {
      return getWorkingStorage().get(paramName);
    }
    
    public void exec()
    {
        Long segmentPK = Long.parseLong(getWSValue(PARAM_SEGMENTPK));

        Segment segment = Segment.findByPK(segmentPK);

        Integer commissionPhaseID = Integer.parseInt(getWSValue(PARAM_COMMISSION_PHASE));

        PremiumDue[] premiumDues = null;
        List premiumDueVector = null;

        if (segment.getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_DEATHPENDING))
        {
            premiumDues = findMostCurrent(segmentPK, commissionPhaseID);
        }
        else
        {
            premiumDues = findSeparateBySegmentPK_Active_Future(segmentPK, commissionPhaseID);
    //        if (commissionPhaseID == 0)
    //        {
    //            Integer numberCommPhases = Integer.parseInt(getWSValue(PARAM_NUMBER_COMM_PHASES));
    //
    //            premiumDues = findSeparateBySegmentPK_Active_Future(segmentPK, commissionPhaseID);
    //        }
    //        else
    //        {
    //            premiumDues = findSeperateBySegmentPK_CommissionPhaseID_Latest(segmentPK, commissionPhaseID);
    //        }

            if (premiumDues == null)
            {
                premiumDues = findMostCurrent(segmentPK, commissionPhaseID);
            }
        }

        premiumDueVector = mapPremiumDueVector(premiumDues);
        if (premiumDueVector != null)
        {
            getScriptProcessor().addWSEntry("PDNumber", premiumDueVector.size() + "");
            getScriptProcessor().addWSVector(PREMIUMDUE_DETAIL, premiumDueVector);
        }
    }

    private List mapPremiumDueVector(PremiumDue[] premiumDues)
    {
        List<Map> premiumDueVector = new ArrayList<Map>();

        for (int i = 0; i < premiumDues.length; i++)
        {
            PremiumDue premiumDue = premiumDues[i];

            Map premiumDueMap = new HashMap();
            premiumDueMap.put("PDPremiumDuePK", premiumDue.getPremiumDuePK().toString());
            if (premiumDue.getEDITTrxFK() != null)
            {
                premiumDueMap.put("PDEDITTrxFK", premiumDue.getEDITTrxFK().toString());
            }
            premiumDueMap.put("PDEffectiveDate", premiumDue.getEffectiveDate().getFormattedDate());
            premiumDueMap.put("PDPendingExtractIndicator", premiumDue.getPendingExtractIndicator());
            premiumDueMap.put("PDBillAmount", premiumDue.getBillAmount().toString());
            premiumDueMap.put("PDAdjustmentAmount", premiumDue.getAdjustmentAmount().toString());
            premiumDueMap.put("PDDeductionAmount", premiumDue.getDeductionAmount().toString());
            premiumDueMap.put("PDPriorBillAmount", premiumDue.getPriorBillAmount().toString());
            premiumDueMap.put("PDNumberOfDeductions", String.valueOf(premiumDue.getNumberOfDeductions()));

            Set<CommissionPhase> commissionPhases = premiumDue.getCommissionPhases();
            premiumDueMap.put("PDNumberCommPhases", commissionPhases.size() + "");
            List<Map<String, String>> commissionPhaseVector = addToWorkingStorage(commissionPhases);
            premiumDueMap.put(COMMISSION_PHASE_DETAIL, commissionPhaseVector);
            premiumDueVector.add(premiumDueMap);
        }

        return premiumDueVector;
    }

    private List<Map<String, String>> addToWorkingStorage(Set<CommissionPhase> commissionPhases)
    {
        List<Map<String, String>> commissionPhaseVector = new ArrayList<>();

        CommissionPhase[] commissionPhasesSorted = (CommissionPhase[])Util.sortObjects(commissionPhases.toArray(), new String[] {"getCommissionPhaseID"});

        for (int i = 0; i < commissionPhasesSorted.length; i++)
        {
            Map<String, String> commissionPhaseMap = new HashMap<>();

            String commissionPhasePK = commissionPhasesSorted[i].getCommissionPhasePK().toString();
            commissionPhaseMap.put("CPDCommissionPhasePK", commissionPhasePK);

            commissionPhaseMap.put("CPDCommissionPhaseID", commissionPhasesSorted[i].getCommissionPhaseID() + "");

            commissionPhaseMap.put("CPDEffectiveDate", commissionPhasesSorted[i].getEffectiveDate().getFormattedDate());

            commissionPhaseMap.put("CPDExpectedMonthlyPremium", commissionPhasesSorted[i].getExpectedMonthlyPremium().toString());

            commissionPhaseMap.put("CPDPrevCumExpectedMonthlyPremium", commissionPhasesSorted[i].getPrevCumExpectedMonthlyPrem().toString());

            commissionPhaseMap.put("CPDPriorExpectedMonthlyPremium", commissionPhasesSorted[i].getPriorExpectedMonthlyPremium().toString());
            
            commissionPhaseMap.put("CPDCommissionTarget", commissionPhasesSorted[i].getCommissionTarget().toString());

            commissionPhaseVector.add(commissionPhaseMap);
        }

        return commissionPhaseVector;
    }

    private PremiumDue[] findSeparateBySegmentPK_Active_Future(Long segmentPK, int commissionPhaseId)
    {
        EDITDate paidToDate = new EDITDate(getWSValue("PaidToDate"));
        
        PremiumDue[] premiumDueActive = new contract.PremiumDue[0];
        if (commissionPhaseId == 0)
        {
            premiumDueActive = findActiveSeparateBySegmentPK(segmentPK, paidToDate);
        }
        else
        {
            premiumDueActive = findActiveBySegmentPKCommissionPhase(segmentPK, paidToDate, commissionPhaseId);
        }

        PremiumDue[] premiumDueFuture = findFutureSeparateBySegmentPK(segmentPK, paidToDate, commissionPhaseId);

        PremiumDue[] premiumDueJoined = (PremiumDue[])Util.joinArrays(premiumDueActive, premiumDueFuture, PremiumDue.class);

        return premiumDueJoined;
    }

    private PremiumDue[] findMostCurrent(Long segmentPK, int commissionPhaseId)
    {
        EDITDate paidToDate = new EDITDate(getWSValue("PaidToDate"));

        PremiumDue[] premiumDueCurrent = new contract.PremiumDue[0];
        if (commissionPhaseId == 0)
        {
            premiumDueCurrent = findMostCurrentBySegmentPK(segmentPK, paidToDate);
        }
        else
        {
            premiumDueCurrent = findMostCurrentBySegmentPKCommissionPhase(segmentPK, paidToDate, commissionPhaseId);
        }

        return premiumDueCurrent;
    }

    /**
     * Get the active PremiumDue with CommissionPhases.  Array used for ease of coding even though one is returned.
     * @param segmentPK
     * @param paidToDate
     * @return
     */
    private PremiumDue[] findActiveSeparateBySegmentPK(Long segmentPK, EDITDate paidToDate)
    {
        PremiumDue[] premiumDues = null;

        String hql = " select premiumDue from PremiumDue premiumDue" +
               " join fetch premiumDue.CommissionPhases commissionPhases" +
               " where premiumDue.SegmentFK = :segmentPK" +
               " and premiumDue.PendingExtractIndicator not in ('R', 'T')" +
               " and premiumDue.EffectiveDate = (select max(premiumDue2.EffectiveDate) from PremiumDue premiumDue2" +
               " join premiumDue2.CommissionPhases commissionPhases2" +
               " where premiumDue2.SegmentFK = :segmentPK" +
               " and premiumDue2.PendingExtractIndicator not in ('R', 'T')" +
               " and premiumDue2.EffectiveDate <= :paidToDate)" +
               " and premiumDue.PremiumDuePK = (select max(premiumDue3.PremiumDuePK) from PremiumDue premiumDue3" +
               " join premiumDue3.CommissionPhases commissionPhases3" +
               " where premiumDue3.SegmentFK = :segmentPK" +
               " and premiumDue3.PendingExtractIndicator not in ('R', 'T')" +
               " and premiumDue3.EffectiveDate = (select max(premiumDue4.EffectiveDate) from PremiumDue premiumDue4" +
               " join premiumDue4.CommissionPhases commissionPhases3" +
               " where premiumDue4.SegmentFK = :segmentPK" +
               " and premiumDue4.PendingExtractIndicator not in ('R', 'T')" +
               " and premiumDue4.EffectiveDate <= :paidToDate))" +
               " order by premiumDue.PremiumDuePK desc";

        Map params = new HashMap();
        params.put("segmentPK", segmentPK);
        params.put("paidToDate", paidToDate);

        Session session = null;

        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

            List<PremiumDue> results = SessionHelper.executeHQL(session, hql, params, 0);

            if (!results.isEmpty())
            {
                List premiumDueList = new ArrayList();
                premiumDueList.add(results.get(0));
                premiumDues = (PremiumDue[])premiumDueList.toArray(new PremiumDue[1]);
            }
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }

        return premiumDues;

    }

    /**
     * Get the active PremiumDue with CommissionPhases.  Array used for ease of coding even though one is returned.
     * @param segmentPK
     * @param paidToDate
     * @return
     */
    private PremiumDue[] findActiveBySegmentPKCommissionPhase(Long segmentPK, EDITDate paidToDate, int commissionPhase)
    {
        PremiumDue[] premiumDues = null;

        String hql = " select premiumDue from PremiumDue premiumDue" +
               " join fetch premiumDue.CommissionPhases commissionPhases" +
               " where premiumDue.SegmentFK = :segmentPK" +
               " and premiumDue.PendingExtractIndicator not in ('R', 'T')" +
               " and commissionPhases.CommissionPhaseID = :commissionPhase"  +
               " and premiumDue.EffectiveDate = (select max(premiumDue2.EffectiveDate) from PremiumDue premiumDue2" +
               " join premiumDue2.CommissionPhases commissionPhases2" +
               " where premiumDue2.SegmentFK = :segmentPK" +
               " and premiumDue2.PendingExtractIndicator not in ('R', 'T')" +
               " and commissionPhases.CommissionPhaseID = :commissionPhase" +
               " and premiumDue2.EffectiveDate <= :paidToDate)" +
               " and premiumDue.PremiumDuePK = (select max(premiumDue3.PremiumDuePK) from PremiumDue premiumDue3" +
               " join premiumDue3.CommissionPhases commissionPhases3" +
               " where premiumDue3.SegmentFK = :segmentPK" +
               " and premiumDue3.PendingExtractIndicator not in ('R', 'T')" +
               " and premiumDue3.EffectiveDate = (select max(premiumDue4.EffectiveDate) from PremiumDue premiumDue4" +
               " join premiumDue4.CommissionPhases commissionPhases3" +
               " where premiumDue4.SegmentFK = :segmentPK" +
               " and premiumDue4.PendingExtractIndicator not in ('R', 'T')" +
               " and premiumDue4.EffectiveDate <= :paidToDate))" +
               " order by premiumDue.PremiumDuePK desc";

        Map params = new HashMap();
        params.put("segmentPK", segmentPK);
        params.put("paidToDate", paidToDate);
        params.put("commissionPhase", commissionPhase);

        Session session = null;

        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

            List<PremiumDue> results = SessionHelper.executeHQL(session, hql, params, 0);

            if (!results.isEmpty())
            {
                List premiumDueList = new ArrayList();
                premiumDueList.add(results.get(0));
                premiumDues = (PremiumDue[])premiumDueList.toArray(new PremiumDue[1]);
            }
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }

        return premiumDues;

    }

    /**
     * Get the most current PremiumDue with CommissionPhases (regardless of status).
     * Array used for ease of coding even though one is returned.
     * @param segmentPK
     * @param paidToDate
     * @return
     */
    private PremiumDue[] findMostCurrentBySegmentPK(Long segmentPK, EDITDate paidToDate)
    {
        PremiumDue[] premiumDues = null;

        String hql = " select premiumDue from PremiumDue premiumDue" +
               " join fetch premiumDue.CommissionPhases commissionPhases" +
               " where premiumDue.SegmentFK = :segmentPK" +
               " and premiumDue.EffectiveDate = (select max(premiumDue2.EffectiveDate) from PremiumDue premiumDue2" +
               " join premiumDue2.CommissionPhases commissionPhases2" +
               " where premiumDue2.SegmentFK = :segmentPK" +
               " and premiumDue2.EffectiveDate <= :paidToDate)" +
               " and premiumDue.PremiumDuePK = (select max(premiumDue3.PremiumDuePK) from PremiumDue premiumDue3" +
               " join premiumDue3.CommissionPhases commissionPhases3" +
               " where premiumDue3.SegmentFK = :segmentPK" +
               " and premiumDue3.EffectiveDate = (select max(premiumDue4.EffectiveDate) from PremiumDue premiumDue4" +
               " join premiumDue4.CommissionPhases commissionPhases3" +
               " where premiumDue4.SegmentFK = :segmentPK" +
               " and premiumDue4.EffectiveDate <= :paidToDate))" +
               " order by premiumDue.PremiumDuePK desc";

        Map params = new HashMap();
        params.put("segmentPK", segmentPK);
        params.put("paidToDate", paidToDate);

        Session session = null;

        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

            List<PremiumDue> results = SessionHelper.executeHQL(session, hql, params, 0);

            if (!results.isEmpty())
            {
                List premiumDueList = new ArrayList();
                premiumDueList.add(results.get(0));
                premiumDues = (PremiumDue[])premiumDueList.toArray(new PremiumDue[1]);
            }
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }

        return premiumDues;

    }

    /**
     * Get the most current PremiumDue with CommissionPhases (regardless of status).
     * Array used for ease of coding even though one is returned.
     * @param segmentPK
     * @param paidToDate
     * @return
     */
    private PremiumDue[] findMostCurrentBySegmentPKCommissionPhase(Long segmentPK, EDITDate paidToDate, int commissionPhase)
    {
        PremiumDue[] premiumDues = null;

        String hql = " select premiumDue from PremiumDue premiumDue" +
               " join fetch premiumDue.CommissionPhases commissionPhases" +
               " where premiumDue.SegmentFK = :segmentPK" +
               " and commissionPhases.CommissionPhaseID = :commissionPhase"  +
               " and premiumDue.EffectiveDate = (select max(premiumDue2.EffectiveDate) from PremiumDue premiumDue2" +
               " join premiumDue2.CommissionPhases commissionPhases2" +
               " where premiumDue2.SegmentFK = :segmentPK" +
               " and commissionPhases.CommissionPhaseID = :commissionPhase" +
               " and premiumDue2.EffectiveDate <= :paidToDate)" +
               " and premiumDue.PremiumDuePK = (select max(premiumDue3.PremiumDuePK) from PremiumDue premiumDue3" +
               " join premiumDue3.CommissionPhases commissionPhases3" +
               " where premiumDue3.SegmentFK = :segmentPK" +
               " and premiumDue3.EffectiveDate = (select max(premiumDue4.EffectiveDate) from PremiumDue premiumDue4" +
               " join premiumDue4.CommissionPhases commissionPhases3" +
               " where premiumDue4.SegmentFK = :segmentPK" +
               " and premiumDue4.EffectiveDate <= :paidToDate))" +
               " order by premiumDue.PremiumDuePK desc";

        Map params = new HashMap();
        params.put("segmentPK", segmentPK);
        params.put("paidToDate", paidToDate);
        params.put("commissionPhase", commissionPhase);

        Session session = null;

        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

            List<PremiumDue> results = SessionHelper.executeHQL(session, hql, params, 0);

            if (!results.isEmpty())
            {
                List premiumDueList = new ArrayList();
                premiumDueList.add(results.get(0));
                premiumDues = (PremiumDue[])premiumDueList.toArray(new PremiumDue[1]);
            }
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }

        return premiumDues;

    }


    /**
     * Get any future dated premium due with CommissionPhases
     * @param segmentPK
     * @param paidToDate
     * @return
     */
   private PremiumDue[] findFutureSeparateBySegmentPK(Long segmentPK, EDITDate paidToDate, int commissionPhase)
    {
        PremiumDue[] premiumDues = null;

        String hql = " select distinct premiumDue from PremiumDue premiumDue" +
                " join fetch premiumDue.CommissionPhases commissionPhases" +
                " where premiumDue.SegmentFK = :segmentPK" +
                " and premiumDue.PendingExtractIndicator not in ('R', 'T')" +
                " and premiumDue.EffectiveDate > :paidToDate";

         if (commissionPhase > 0)
         {
             hql = hql + " and commissionPhases.CommissionPhaseID = :commissionPhase  order by premiumDue.PremiumDuePK asc";
         }
        else
         {
            hql = hql + " order by premiumDue.PremiumDuePK asc";
         }

         Map params = new HashMap();
         params.put("segmentPK", segmentPK);
         params.put("paidToDate", paidToDate);
         if (commissionPhase > 0)
         {
             params.put("commissionPhase", commissionPhase);
         }

        Session session = null;

        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

            List<PremiumDue> results = SessionHelper.executeHQL(session, hql, params, 0);

            if (!results.isEmpty())
            {
                premiumDues = (PremiumDue[])results.toArray(new PremiumDue[results.size()]);
            }
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }

        return premiumDues;

    }

    /**
     * Finds PremiumDue with latest CommissionPhase by SegmentPK and CommissionPhaseID.
     * @param segmentPK
     * @param commissionPhaseID
     * @return
     */
    private PremiumDue[] findSeperateBySegmentPK_CommissionPhaseID_Latest(Long segmentPK, int commissionPhaseID)
    {
    String hql = " select premiumDue from PremiumDue premiumDue" +
                  " join fetch premiumDue.CommissionPhases commissionPhase" +
                  " where premiumDue.SegmentFK = :segmentPK" +
                  " and commissionPhase.CommissionPhaseID = :commissionPhaseID" +
                  " and premiumDue.PendingExtractIndicator not in ('R')" +
                  " and commissionPhase.EffectiveDate = " +
                  " (select max(commissionPhase2.EffectiveDate)  from PremiumDue premiumDue2" +
                  "  join premiumDue2.CommissionPhases commissionPhase2" +
                  "  where premiumDue2.SegmentFK = :segmentPK" +
                  "  and commissionPhase2.CommissionPhaseID = :commissionPhaseID" +
                  "  and premiumDue2.PendingExtractIndicator not in ('R'))";

        Map params = new HashMap();

        params.put("segmentPK", segmentPK);
        params.put("commissionPhaseID", commissionPhaseID);

        PremiumDue[] premiumDues = null;

        Session session = null;

        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

            List<PremiumDue> results = SessionHelper.executeHQL(session, hql, params, 0);

            if (!results.isEmpty())
            {
                premiumDues = results.toArray(new PremiumDue[results.size()]);
            }
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }

        return premiumDues;
    }
    
//    /**
//     * Finder by SegmentPK and Number of Commission Phases to search for.
//     * @param segmentPK
//     * @param numberCommPhases
//     * @return
//     */
//    private PremiumDue[] findBySegmentPK_NumberCommPhases(Long segmentPK, int numberCommPhases)
//    {
//        List<CommissionPhase> commissionPhases = new ArrayList<CommissionPhase>();
//
//        // CommissionPhaseIDs will have non zero values.
//        for (int commissionPhaseID = 1; commissionPhaseID <= numberCommPhases; commissionPhaseID++)
//        {
//            CommissionPhase commissionPhase = findSeperateBySegmentPK_CommissionPhaseID_Latest(segmentPK, commissionPhaseID);
//
//            commissionPhases.add(commissionPhase);
//        }
//
//        return commissionPhases.toArray(new CommissionPhase[commissionPhases.size()]);
//    }

    public static void main(String[] args)
    {
        ScriptProcessor sp = new ScriptProcessorImpl();

        long segmentPK = 1236889511667L;
        sp.addWSEntry(PARAM_SEGMENTPK, segmentPK + "");
        sp.addWSEntry(PARAM_COMMISSION_PHASE, "0");

        GetCommissionPhaseDetail getComm = new GetCommissionPhaseDetail(sp);
        getComm.exec();
        System.out.println("done");
    }
}
