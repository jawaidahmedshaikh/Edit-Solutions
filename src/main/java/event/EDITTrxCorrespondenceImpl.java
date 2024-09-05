/*
 * User: gfrosti
 * Date: Nov 7, 2003
 * Time: 8:57:27 AM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import edit.common.EDITDate;
import edit.common.vo.*;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.CRUD;
import businesscalendar.BusinessCalendar;
import businesscalendar.BusinessDay;
import contract.Segment;


public class EDITTrxCorrespondenceImpl extends CRUDEntityImpl
{
    protected void save(EDITTrxCorrespondence editTrxCorrespondence, CRUD crud) throws Exception
    {

        if (isNew(editTrxCorrespondence))
        {
            ((EDITTrxCorrespondenceVO) editTrxCorrespondence.getVO()).setStatus("P"); // Pending

            EDITDate correspondenceDate = getCorrespondenceDate(editTrxCorrespondence, 0, null);

            ((EDITTrxCorrespondenceVO) editTrxCorrespondence.getVO()).setCorrespondenceDate(correspondenceDate.getFormattedDate());
        }
        
        super.setCRUD(crud);
        super.save(editTrxCorrespondence, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }
   protected void save(EDITTrxCorrespondence editTrxCorrespondence, int notificationDays, String notificationDaysType, EDITTrxVO editTrxVO, CRUD crud) throws Exception
    {
        if (isNew(editTrxCorrespondence))
        {
            ((EDITTrxCorrespondenceVO) editTrxCorrespondence.getVO()).setStatus("P"); // Pending

            EDITDate correspondenceDate = getCorrespondenceDate(editTrxCorrespondence, notificationDays, notificationDaysType, editTrxVO);

            ((EDITTrxCorrespondenceVO) editTrxCorrespondence.getVO()).setCorrespondenceDate(correspondenceDate.getFormattedDate());
        }

        super.setCRUD(crud);
        super.save(editTrxCorrespondence, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }


    protected void save(EDITTrxCorrespondence editTrxCorrespondence, EDITTrx editTrx, CRUD crud) throws Exception
    {

        if (isNew(editTrxCorrespondence))
        {
            ((EDITTrxCorrespondenceVO) editTrxCorrespondence.getVO()).setStatus("P"); // Pending

            EDITDate correspondenceDate = getCorrespondenceDate(editTrxCorrespondence, 0, null, editTrx);

            ((EDITTrxCorrespondenceVO) editTrxCorrespondence.getVO()).setCorrespondenceDate(correspondenceDate.getFormattedDate());
        }

        super.setCRUD(crud);
        super.save(editTrxCorrespondence, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

   protected void save(EDITTrxCorrespondence editTrxCorrespondence, int notificationDays, String notificationDaysType, EDITTrx editTrx) throws Exception
    {
        if (isNew(editTrxCorrespondence))
        {
            ((EDITTrxCorrespondenceVO) editTrxCorrespondence.getVO()).setStatus("P"); // Pending

            EDITDate correspondenceDate = getCorrespondenceDate(editTrxCorrespondence, notificationDays, notificationDaysType, editTrx);

            ((EDITTrxCorrespondenceVO) editTrxCorrespondence.getVO()).setCorrespondenceDate(correspondenceDate.getFormattedDate());
        }

        super.save(editTrxCorrespondence, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    protected void save(EDITTrxCorrespondence editTrxCorrespondence, int notificationDays, String notificationDaysType) throws Exception
     {
         if (isNew(editTrxCorrespondence))
         {
             ((EDITTrxCorrespondenceVO) editTrxCorrespondence.getVO()).setStatus("P"); // Pending

             EDITDate correspondenceDate = getCorrespondenceDate(editTrxCorrespondence, notificationDays, notificationDaysType);

             ((EDITTrxCorrespondenceVO) editTrxCorrespondence.getVO()).setCorrespondenceDate(correspondenceDate.getFormattedDate());
         }

         super.save(editTrxCorrespondence, ConnectionFactory.EDITSOLUTIONS_POOL, false);
     }


    protected void load(EDITTrxCorrespondence editTrxCorrespondence, long editTrxCorrespondencePK) throws Exception
    {
        super.load(editTrxCorrespondence, editTrxCorrespondencePK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void delete(EDITTrxCorrespondence editTrxCorrespondence) throws Exception
    {
        super.delete(editTrxCorrespondence, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    private EDITDate getCorrespondenceDate(EDITTrxCorrespondence editTrxCorrespondence,
                                           int notificationDays,
                                           String notificationDaysType, 
                                           EDITTrx editTrx) throws Exception
    {
//        EDITTrx editTrx = new EDITTrx(((EDITTrxCorrespondenceVO) editTrxCorrespondence.getVO()).getEDITTrxFK());

        TransactionCorrespondence transactionCorrespondence = new TransactionCorrespondence(((EDITTrxCorrespondenceVO) editTrxCorrespondence.getVO()).getTransactionCorrespondenceFK());

        int numberOfDays = ((TransactionCorrespondenceVO) transactionCorrespondence.getVO()).getNumberOfDays();

        String priorPostCT = ((TransactionCorrespondenceVO) transactionCorrespondence.getVO()).getPriorPostCT();

        EDITDate correspondenceDate = new EDITDate(editTrx.getAsVO().getEffectiveDate());

        if ((editTrx.getAsVO().getTransactionTypeCT().equalsIgnoreCase("HFTA") ||
             editTrx.getAsVO().getTransactionTypeCT().equalsIgnoreCase("HFTP")) &&
            !((TransactionCorrespondenceVO) transactionCorrespondence.getVO()).getCorrespondenceTypeCT().equalsIgnoreCase("HedgeFundFinal") &&
            notificationDays > 0)
        {
            if (notificationDaysType != null && notificationDaysType.equalsIgnoreCase("Business"))
            {
                BusinessCalendar businessCalendar = new BusinessCalendar();
                BusinessDay businessDay = businessCalendar.findPreviousBusinessDay(correspondenceDate, notificationDays);
                correspondenceDate = businessDay.getBusinessDate();
            }
            else
            {
                correspondenceDate = correspondenceDate.subtractDays(notificationDays);
            }
        }
        else
        {
            if (priorPostCT.equals("Prior"))
            {
                correspondenceDate = correspondenceDate.subtractDays(numberOfDays);
            }

            else if (priorPostCT.equals("Post"))
            {
            	if (editTrx.getAsVO().getTransactionTypeCT().equalsIgnoreCase("LP")) {
            		Segment segment = Segment.findBy_EDITTrxPK(editTrx.getEDITTrxPK());
            		
            		if (segment != null) {
	            		String segmentNameCT = segment.getSegmentNameCT();
	            		if (segmentNameCT != null && !segmentNameCT.equalsIgnoreCase("UL")) {
	                        correspondenceDate = correspondenceDate.addDays(numberOfDays);
	            		}
            		}
            	} else {
                    correspondenceDate = correspondenceDate.addDays(numberOfDays);
            	}
            }
        }

        return correspondenceDate;
    }

    private EDITDate getCorrespondenceDate(EDITTrxCorrespondence editTrxCorrespondence,
                                           int notificationDays,
                                           String notificationDaysType) throws Exception
    {
        EDITTrx editTrx = new EDITTrx(((EDITTrxCorrespondenceVO) editTrxCorrespondence.getVO()).getEDITTrxFK());

        TransactionCorrespondence transactionCorrespondence = new TransactionCorrespondence(((EDITTrxCorrespondenceVO) editTrxCorrespondence.getVO()).getTransactionCorrespondenceFK());

        int numberOfDays = ((TransactionCorrespondenceVO) transactionCorrespondence.getVO()).getNumberOfDays();

        String priorPostCT = ((TransactionCorrespondenceVO) transactionCorrespondence.getVO()).getPriorPostCT();

        EDITDate correspondenceDate = new EDITDate(editTrx.getAsVO().getEffectiveDate());

        if ((editTrx.getAsVO().getTransactionTypeCT().equalsIgnoreCase("HFTA") ||
             editTrx.getAsVO().getTransactionTypeCT().equalsIgnoreCase("HFTP")) &&
            !((TransactionCorrespondenceVO) transactionCorrespondence.getVO()).getCorrespondenceTypeCT().equalsIgnoreCase("HedgeFundFinal") &&
            notificationDays > 0)
        {
            if (notificationDaysType != null && notificationDaysType.equalsIgnoreCase("Business"))
            {
                BusinessCalendar businessCalendar = new BusinessCalendar();
                BusinessDay businessDay = businessCalendar.findPreviousBusinessDay(correspondenceDate, notificationDays);
                correspondenceDate = businessDay.getBusinessDate();
            }
            else
            {
                correspondenceDate = correspondenceDate.subtractDays(notificationDays);
            }
        }
        else
        {
            if (priorPostCT.equals("Prior"))
            {
                correspondenceDate = correspondenceDate.subtractDays(numberOfDays);
            }

            else if (priorPostCT.equals("Post"))
            {
                correspondenceDate = correspondenceDate.addDays(numberOfDays);
            }
        }

        return correspondenceDate;
    }

    private EDITDate getCorrespondenceDate(EDITTrxCorrespondence editTrxCorrespondence,
                                           int notificationDays,
                                           String notificationDaysType, EDITTrxVO editTrxVO) throws Exception
    {
//        EDITTrxCorrespondenceVO editTrxCorrespondenceVO = (EDITTrxCorrespondenceVO)editTrxCorrespondence.getVO();
        EDITTrx editTrx = new EDITTrx(editTrxVO);
//        EDITTrx editTrx = new EDITTrx(((EDITTrxCorrespondenceVO) editTrxCorrespondence.getVO()).getEDITTrxFK());

        TransactionCorrespondence transactionCorrespondence = new TransactionCorrespondence(((EDITTrxCorrespondenceVO) editTrxCorrespondence.getVO()).getTransactionCorrespondenceFK());

        int numberOfDays = ((TransactionCorrespondenceVO) transactionCorrespondence.getVO()).getNumberOfDays();

        String priorPostCT = ((TransactionCorrespondenceVO) transactionCorrespondence.getVO()).getPriorPostCT();

        EDITDate correspondenceDate = new EDITDate(editTrx.getAsVO().getEffectiveDate());

        if ((editTrx.getAsVO().getTransactionTypeCT().equalsIgnoreCase("HFTA") ||
             editTrx.getAsVO().getTransactionTypeCT().equalsIgnoreCase("HFTP")) &&
             !((TransactionCorrespondenceVO) transactionCorrespondence.getVO()).getCorrespondenceTypeCT().equalsIgnoreCase("HedgeFundFinal"))
        {
            if (notificationDaysType != null && notificationDaysType.equalsIgnoreCase("Business"))
            {
                BusinessCalendar businessCalendar = new BusinessCalendar();
                BusinessDay businessDay = businessCalendar.findPreviousBusinessDay(correspondenceDate, notificationDays);
                correspondenceDate = businessDay.getBusinessDate();
            }
            else
            {
                correspondenceDate = correspondenceDate.subtractDays(notificationDays);
            }
        }
        else
        {
            if (priorPostCT.equals("Prior"))
            {
                correspondenceDate = correspondenceDate.subtractDays(numberOfDays);
            }

            else if (priorPostCT.equals("Post"))
            {
                correspondenceDate = correspondenceDate.addDays(numberOfDays);
            }
        }

        return correspondenceDate;
    }
}
