/*
 * User: gfrosti
 * Date: Mar 10, 2004
 * Time: 9:41:00 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.component;

import contract.business.NewBusinessUseCase;
import contract.Deposits;
import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.Logging;
import logging.*;
import event.Suspense;


public class NewBusinessUseCaseComponent implements NewBusinessUseCase
{
    public void accessNewBusiness()
    {
    }

    public void addNewBusinessContract()
    {
    }

    public void updateNewBusinessContract()
    {
    }

    public void deleteNewBusinessContract()
    {
    }

    public void performNewBusinessQuote()
    {
    }

    public void issueNewBusinessContract()
    {
    }

    public void accessCashBatch()
    {
    }

    public void updateCashBatch()
    {
    }

    public void changeKeyInNewBusiness()
    {
    }
    /**
     * sramamurthy businessnotes update method
     */
     public void updateNewBusinessNotes()
     {
     }

    // Security check point.
    public void accessImportNewBusiness()
    {

    }

    public void saveDeposit(Deposits deposits)
    {
        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

        try
        {
            SessionHelper.saveOrUpdate(deposits, SessionHelper.EDITSOLUTIONS);

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch(Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);
        }
        finally
        {
            // SessionHelper.clearSessions(); Don't - downstream code needs the session in-tack.
        }
    }

    public void adjustSuspense(Suspense suspense)
    {
        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
        try
        {
            SessionHelper.saveOrUpdate(suspense, SessionHelper.EDITSOLUTIONS);

            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);
        }
    }

    public void performProposal()
    {        
    }

    public void accessQuestionnaireResponse()
    {

    }

    public void accessClassGenderRatings()
    {

    }

    public void updateClassGenderRatings()
    {

    }

    public void accessRequirements()
    {

    }

    public void updateRequirements()
    {

    }

    public void deleteRequirements()
    {
        
    }
    
    public void accessAgent()
    {

    }

    public void updateAgent()
    {

    }    
}
