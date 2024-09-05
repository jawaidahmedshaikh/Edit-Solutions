/*
 * User: sdorman
 * Date: Jun 21, 2007
 * Time: 11:17:23 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package event;

import edit.common.*;
import edit.services.db.hibernate.*;
import contract.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class CommissionablePremiumHistory extends HibernateEntity
{
    private Long commissionablePremiumHistoryPK;

    private int duration;
    private EDITBigDecimal commissionablePremium;

    //  Parents
    private EDITTrxHistory editTrxHistory;
    private CommissionPhase commissionPhase;
    
    /**
     * For CRUD compatibility with VOs. 
     */
    private Long editTrxHistoryFK;
    
    /**
     * For CRUD compatability with VOs.
     */
    private Long commissionPhaseFK;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public CommissionablePremiumHistory()
    {

    }

    public Long getCommissionablePremiumHistoryPK()
    {
        return commissionablePremiumHistoryPK;
    }

    public void setCommissionablePremiumHistoryPK(Long commissionablePremiumHistoryPK)
    {
        this.commissionablePremiumHistoryPK = commissionablePremiumHistoryPK;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public EDITBigDecimal getCommissionablePremium()
    {
        return commissionablePremium;
    }

    public void setCommissionablePremium(EDITBigDecimal commissionablePremium)
    {
        this.commissionablePremium = commissionablePremium;
    }

    public CommissionPhase getCommissionPhase()
    {
        return commissionPhase;
    }

    public void setCommissionPhase(CommissionPhase commissionPhase)
    {
        this.commissionPhase = commissionPhase;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return CommissionablePremiumHistory.DATABASE;
    }

    public void setEDITTrxHistory(EDITTrxHistory editTrxHistory)
    {
        this.editTrxHistory = editTrxHistory;
    }

    public EDITTrxHistory getEDITTrxHistory()
    {
        return editTrxHistory;
    }

    public void setEDITTrxHistoryFK(Long editTrxHistoryFK)
    {
        this.editTrxHistoryFK = editTrxHistoryFK;
    }

    public Long getEDITTrxHistoryFK()
    {
        return editTrxHistoryFK;
    }

    public void setCommissionPhaseFK(Long commissionPhaseFK)
    {
        this.commissionPhaseFK = commissionPhaseFK;
    }

    public Long getCommissionPhaseFK()
    {
        return commissionPhaseFK;
    }

    public static final CommissionablePremiumHistory[] findBy_EDITTrxHistoryFK(Long editTrxHistoryFK)
    {
        CommissionablePremiumHistory[] commissionablePremiumHistory = null;

        String hql = " select commissionablePremiumHistory" +
                     " from CommissionablePremiumHistory commissionablePremiumHistory" +
                     " where commissionablePremiumHistory.EDITTrxHistoryFK = :editTrxHistoryFK";

        Map params = new HashMap();

        params.put("editTrxHistoryFK", editTrxHistoryFK);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        if (!results.isEmpty())
        {
            commissionablePremiumHistory = (CommissionablePremiumHistory[]) results.toArray(new CommissionablePremiumHistory[results.size()]);
        }

        return commissionablePremiumHistory;
    }
}
