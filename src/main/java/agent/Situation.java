/*
 * User: gfrosti
 * Date: Sep 8, 2004
 * Time: 1:50:02 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */


package agent;

import edit.common.*;
import edit.services.config.ServicesConfig;

public class Situation
{
    private String situationCode;

    private EDITDate startDate;

    private EDITDate stopDate;

    public Situation(PlacedAgent placedAgent)
    {
        this.situationCode = placedAgent.getSituationCode();

        this.startDate = placedAgent.getStartDate();

        this.stopDate = placedAgent.getStopDate();
    }

    /**
     * Situation Code.
     * @return
     */
    public String getSituationCode()
    {
        return situationCode;
    }

    /**
     * Start Date
     * @return
     */
    public EDITDate getStartDate()
    {
        return startDate;
    }

    /**
     * Stop Date.
     * @return the Stop Date or the Max Default Stop Date (9999/99/99) if none specified
     */
    public EDITDate getStopDate()
    {
        return stopDate;
    }

    /**
     * Two Situations overlap if they are associated to the same AgentContract, and their Situation Code is equal, while
     * their Start and Stop dates overlap.
     * @param visitingSituation
     * @return true if the visiting Situation's Code, Start and Stop dates conflict with this Situation
     */
    public boolean situationConflicts(Situation visitingSituation, String visitingAgentNumber, String newAgentNumber)
    {
        boolean situationConflicts = false;

        String visitingSituationCode = visitingSituation.getSituationCode();

        boolean bothSituationsAreNULL = (situationCode == null & visitingSituationCode == null);

        boolean bothSituationsAreNotNULL = (situationCode != null & visitingSituationCode != null);

        if (shouldCheckForOverlappingSituationDates())
        {
            if (bothSituationsAreNULL || (bothSituationsAreNotNULL && situationCode.equalsIgnoreCase(visitingSituationCode)))
            {
                boolean startDateOverlaps = startDateOverlaps(visitingSituation);

                if (newAgentNumber.equalsIgnoreCase(visitingAgentNumber))
                {
                    if (startDateOverlaps)
                    {
                        situationConflicts = true;
                    }
                }
            }
        }

        return situationConflicts;
    }
    
    /**
     * This is a configurable option. Some clients don't care
     * that two Agents are placed under the same AgentContract with 
     * overlapping start/stop dates. Some do. This defaults to true which
     * is a safe(er) assumption, otherwise false if configured to do so.
     * @return
     */
    private boolean shouldCheckForOverlappingSituationDates()
    {
        boolean checkForOverlappingSituationDates = true;
        
        String checkForOverlappingSituationDatesStr = ServicesConfig.getCheckForOverlappingSituationDates();
        
        if (checkForOverlappingSituationDatesStr != null)
        {
            if (checkForOverlappingSituationDatesStr.equals("N"))
            {
                checkForOverlappingSituationDates = false;
            }
        }
        
        return checkForOverlappingSituationDates;
    }

    /**
     * Checks if the StartDate of the visiting Situation is not within the range of the StartDate, StopDate of this
     * Situation.
     * @param visitingSituation
     * @return true if StartDate of the visiting Situation is within the date range, false otherwise
     */
    private boolean startDateOverlaps(Situation visitingSituation)
    {
        boolean startDateOverlaps = false;

        EDITDate visStartDate = visitingSituation.getStartDate();
        EDITDate visStopDate = visitingSituation.getStopDate();

        boolean visStartDateInRange = dateIsInRange(visStartDate);
        boolean visStopDateInRange = dateIsInRange(visStopDate);

        if (visStartDateInRange || visStopDateInRange)
        {
            startDateOverlaps = true;
        }

        return startDateOverlaps;
    }

    /**
     * Evaluates (StopDate <= date <= StartDate).
     * @param date
     * @return true if the date expression is valid
     */
    public boolean dateIsInRange(EDITDate date)
    {
        boolean dateIsInRange = false;

//        dateIsInRange = (startDate.ifLE(date) && stopDate.ifGE(date));
        dateIsInRange = (startDate.before(date) || startDate.equals(date)) && (stopDate.after(date) || stopDate.equals(date));

        return dateIsInRange;
    }
}
