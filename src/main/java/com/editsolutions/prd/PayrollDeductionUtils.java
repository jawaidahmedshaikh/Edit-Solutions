package com.editsolutions.prd;

import java.text.SimpleDateFormat;
import java.util.Date;

import businesscalendar.BusinessCalendar;
import businesscalendar.BusinessDay;

import com.editsolutions.prd.service.DeductionScheduleDayService;
import com.editsolutions.prd.service.DeductionScheduleDayServiceImpl;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DeductionScheduleDay;
import com.editsolutions.prd.vo.PRDSettings;

import edit.common.EDITDate;

public class PayrollDeductionUtils {

	public static PRDSettings calculateAndSetPRDExtractDates(
			PRDSettings payDedSched) {
		if ((payDedSched.getPrdSetupPK() != null) && payDedSched.getTypeCT().equals("Custom")) {
			return updatePRDCustomExtractDates(payDedSched);
		} 
		return PayrollDeductionUtils.calculateAndSetPRDExtractDates(
				payDedSched, null);
	}

	public static PRDSettings updatePRDCustomExtractDates(
			PRDSettings payDedSched) {
		DeductionScheduleDayService dsds = new DeductionScheduleDayServiceImpl();
		DeductionScheduleDay deductionScheduleDay;
		if (payDedSched.getNextPRDDueDate() != null) {
		     deductionScheduleDay= dsds.getNextDeductionScheduleDay(payDedSched, payDedSched.getNextPRDDueDate());
		} else {
		     deductionScheduleDay = dsds.getFirstDeductionScheduleDay(payDedSched);	
		}
		
		EDITDate nextPRDDueDate;
		EDITDate nextPRDExtractDate;
		try {
		    nextPRDDueDate = getEDITDate(deductionScheduleDay.getPrdDueDate());
		    payDedSched.setLastPRDExtractDate(payDedSched.getNextPRDExtractDate());
		    nextPRDExtractDate = (getEDITDate(deductionScheduleDay.getPrdDueDate())).subtractDays(1);
	    } catch (Exception e) {
			throw new DAOException("Deduction Schedule is empty");
	    }
		int leadDays = 0;

		BusinessCalendar bc = new BusinessCalendar();

		// always use the leadDays on the tables for the ExtractDate calc, get
		// best business date
		if (payDedSched.getLastPRDExtractDate() == null) {
			leadDays = (payDedSched.getFirstDeductionLeadDays());
		} else {
			leadDays = (payDedSched.getSubsequentDays());
		}

		EDITDate tempDate = nextPRDDueDate.subtractDays(leadDays);
		BusinessDay bd = bc.getBestUnrestictedBusinessDay(tempDate);
		EDITDate nextPRDDueDateBD = bd.getBusinessDate();
		leadDays = 1;

		// Always subtract 1 day here then get the best business day
		nextPRDExtractDate = nextPRDDueDateBD.subtractDays(leadDays);
		bd = bc.getBestUnrestictedBusinessDay(nextPRDExtractDate);
		nextPRDExtractDate = bd.getBusinessDate();

		payDedSched.setNextPRDDueDate(getDate(nextPRDDueDate));
		payDedSched.setNextPRDExtractDate(getDate(nextPRDExtractDate));

		payDedSched.setCurrentDateThru(getDate(nextPRDDueDate.addMonths(1).addDays(15)
				.getEndOfMonthDate()));
		
		return payDedSched;
	}

	
	public static PRDSettings updatePRDExtractDates(
			PRDSettings payDedSched,
			Date deductionChangeStartDate) {
		EDITDate nextPRDDueDate = getEDITDate(deductionChangeStartDate);
		payDedSched.setLastPRDExtractDate(payDedSched.getNextPRDExtractDate());
		EDITDate nextPRDExtractDate = getEDITDate(payDedSched.getNextPRDExtractDate());
		int leadDays = 0;

		BusinessCalendar bc = new BusinessCalendar();

		// always use the leadDays on the tables for the ExtractDate calc, get
		// best business date
		if (payDedSched.getLastPRDExtractDate() == null) {
			leadDays = (payDedSched.getFirstDeductionLeadDays());
		} else {
			leadDays = (payDedSched.getSubsequentDays());
		}

		EDITDate tempDate = nextPRDDueDate.subtractDays(leadDays);
		BusinessDay bd = bc.getBestUnrestictedBusinessDay(tempDate);
		EDITDate nextPRDDueDateBD = bd.getBusinessDate();
		leadDays = 1;

		// Always subtract 1 day here then get the best business day
		nextPRDExtractDate = nextPRDDueDateBD.subtractDays(leadDays);
		bd = bc.getBestUnrestictedBusinessDay(nextPRDExtractDate);
		nextPRDExtractDate = bd.getBusinessDate();

		payDedSched.setNextPRDDueDate(getDate(nextPRDDueDate));
		payDedSched.setNextPRDExtractDate(getDate(nextPRDExtractDate));

		payDedSched.setCurrentDateThru(getDate(nextPRDDueDate.addDays(14).addMonths(1)
				.getEndOfMonthDate()));
		
		return payDedSched;
	}

	public static PRDSettings calculateAndSetPRDExtractDates(
			PRDSettings payDedSched, Date extractDate) {
		EDITDate nextPRDExtractDate = null;
		EDITDate nextPRDDueDate = null;

		//if (payDedSched.getLastPRDExtractDate() == null) {
			if (extractDate == null) {
				nextPRDDueDate = PayrollDeductionUtils
						.getEDITDate(payDedSched.getFirstDeductionDate());
			} else {
				nextPRDDueDate = PayrollDeductionUtils.getEDITDate(
						extractDate).addDays(1);
			}

			// first get Best BusinessDate for DueDate and then for each
			// subsequent step
			BusinessCalendar bc = new BusinessCalendar();
			BusinessDay bd = bc.getBestUnrestictedBusinessDay(nextPRDDueDate);
			nextPRDDueDate = bd.getBusinessDate();

			// Now subtract InitialLeadDays and get the Best Business
			// Day
			nextPRDDueDate = nextPRDDueDate.subtractDays(payDedSched
					.getFirstDeductionLeadDays());

			// For STANDARD make sure nextPRDDueDate falls on a Friday
			if (payDedSched.getTypeCT().equals("Standard")) {
			    while (!nextPRDDueDate.getDayOfWeek().equals(EDITDate.FRIDAY)) {
				    nextPRDDueDate = nextPRDDueDate.addDays(1);
			    }
			}

			bd = bc.getBestUnrestictedBusinessDay(nextPRDDueDate);
			nextPRDDueDate = bd.getBusinessDate();

			// Subtract 1 day and get the BestBusiness Day
			nextPRDExtractDate = nextPRDDueDate.subtractDays(1);
			bd = bc.getBestUnrestictedBusinessDay(nextPRDExtractDate);
			nextPRDExtractDate = bd.getBusinessDate();

			payDedSched.setNextPRDDueDate(new java.sql.Date(nextPRDDueDate
					.getTimeInMilliseconds()));
			payDedSched.setNextPRDExtractDate(new java.sql.Date(
					nextPRDExtractDate.getTimeInMilliseconds()));

			// deck: Was adding two months. Now adding 14 days plus
			// 1 month.
			payDedSched.setCurrentDateThru(new java.sql.Date(nextPRDExtractDate
					.addDays(14).addMonths(1).getEndOfMonthDate()
					.getTimeInMilliseconds()));

		//}
		// deck: advance nextPRDDueDate 1 day if of type standard and
		// nextPRDDueDate and nextPRDExtractDate are equal
		/*
		 * if (payDedSched.getPRDTypeCT().equalsIgnoreCase(PRD_STANDARD) &&
		 * payDedSched.getNextPRDDueDate().equals(
		 * payDedSched.getNextPRDExtractDate())) {
		 * payDedSched.setNextPRDDueDate(payDedSched.getNextPRDExtractDate()
		 * .addDays(1)); }
		 */
		return payDedSched;

	}

	public static java.sql.Date getDate(EDITDate date) {
		return new java.sql.Date(date.getTimeInMilliseconds());
	}

	public static EDITDate getEDITDate(Date date) {
		SimpleDateFormat formmater = new SimpleDateFormat("yyyy/MM/dd");
		String dateString = formmater.format(date);
		return new EDITDate(dateString);
	}

}
