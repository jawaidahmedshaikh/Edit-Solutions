/**
 * User: dlataill
 * Date: Jun 13, 2007
 * Time: 3:47:30 PM
 * <p/>
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import logging.Log;
import logging.LogEvent;
import role.ClientRole;
import staging.PRDStaging;
import staging.StagingContext;
import batch.business.Batch;
import billing.BillSchedule;
import businesscalendar.BusinessCalendar;
import businesscalendar.BusinessDay;
import client.ClientDetail;
import contract.PremiumDue;
import contract.Segment;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.EDITMap;
import edit.common.exceptions.EDITCaseException;
import edit.services.EditServiceLocator;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.Logging;
import engine.Company;
import fission.utility.DateTimeUtil;
import fission.utility.Util;

public class PayrollDeductionSchedule extends HibernateEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String PRD_STANDARD = "Standard";
	public static String PRD_FIXED_SCHEDULE = "Fixed";
	public static String PRD_CUSTOM = "Custom";

	public static String REPORT_CHANGES_ONLY = "Changes";
	public static String REPORT_ALL = "All";

	public static String CHANGE = "C";
	public static String TERMINATED = "T";

	public static String[] ANNUAL_DEDUCTION_MODES = { "01" };
	public static String[] SEMI_ANNUAL_DEDUCTION_MODES = { "02" };
	public static String[] QUARTERLY_DEDUCTION_MODES = { "04" };
	public static String[] NINETHLY_DEDUCTION_MODES = { "09", "18", "36" };
	public static String[] TENTHLY_DEDUCTION_MODES = { "10", "20", "40" };
	public static String[] MONTHLY_DEDUCTION_MODES = { "12", "48" };
	public static String[] SEMI_MONTHLY_DEDUCTION_MODES = { "24" };
	public static String[] BI_WEEKLY_DEDUCTION_MODES = { "26" };
	public static String[] WEEKLY_DEDUCTION_MODES = { "52" };
	
	public static List ANNUAL_DEDUCTION = new ArrayList(Arrays.asList(ANNUAL_DEDUCTION_MODES));
	public static List SEMI_ANNUAL_DEDUCTION = new ArrayList(Arrays.asList(SEMI_ANNUAL_DEDUCTION_MODES));
	public static List QUARTERLY_DEDUCTION = new ArrayList(Arrays.asList(QUARTERLY_DEDUCTION_MODES));
	public static List NINETHLY_DEDUCTION = new ArrayList(Arrays.asList(NINETHLY_DEDUCTION_MODES));
	public static List TENTHLY_DEDUCTION = new ArrayList(Arrays.asList(TENTHLY_DEDUCTION_MODES));
	public static List MONTHLY_DEDUCTION = new ArrayList(Arrays.asList(MONTHLY_DEDUCTION_MODES));
	public static List SEMI_MONTHLY_DEDUCTION = new ArrayList(Arrays.asList(SEMI_MONTHLY_DEDUCTION_MODES));
	public static List BI_WEEKLY_DEDUCTION = new ArrayList(Arrays.asList(BI_WEEKLY_DEDUCTION_MODES));
	public static List WEEKLY_DEDUCTION = new ArrayList(Arrays.asList(WEEKLY_DEDUCTION_MODES));

	private Long payrollDeductionSchedulePK;
	private Long contractGroupFK;
	private ContractGroup contractGroup;
	private String prdTypeCT;
	private int initialLeadDays;
	private int subsequentLeadDays;
	private EDITDate effectiveDate;
	private EDITDate terminationDate;
	private EDITDate lastPRDExtractDate;
	private EDITDate nextPRDExtractDate;
	private EDITDate nextPRDDueDate;
	private EDITDate currentDateThru;
	private String prdConsolidationCT;
	private String reportTypeCT;
	private String sortOptionCT;
	private String summaryCT;
	private String outputTypeCT;
	private EDITDate changeEffectiveDate;
	private String creationOperator;
	private EDITDate creationDate;

	private Set<PayrollDeduction> payrollDeductions;

	/**
	 * The set of dates for which the deduction will occur.
	 */
	private Set<PayrollDeductionCalendar> payrollDeductionCalendars = new HashSet<PayrollDeductionCalendar>();

	public static final String DATABASE = SessionHelper.EDITSOLUTIONS;

	static final void establishModes() {
		int i = 0;

		for (i = 0; i < ANNUAL_DEDUCTION_MODES.length; i++) {
			ANNUAL_DEDUCTION.add(ANNUAL_DEDUCTION_MODES[i]);
		}

		for (i = 0; i < SEMI_ANNUAL_DEDUCTION_MODES.length; i++) {
			SEMI_ANNUAL_DEDUCTION.add(SEMI_ANNUAL_DEDUCTION_MODES[i]);
		}

		for (i = 0; i < QUARTERLY_DEDUCTION_MODES.length; i++) {
			QUARTERLY_DEDUCTION.add(QUARTERLY_DEDUCTION_MODES[i]);
		}

		for (i = 0; i < NINETHLY_DEDUCTION_MODES.length; i++) {
			NINETHLY_DEDUCTION.add(NINETHLY_DEDUCTION_MODES[i]);
		}

		for (i = 0; i < TENTHLY_DEDUCTION_MODES.length; i++) {
			TENTHLY_DEDUCTION.add(TENTHLY_DEDUCTION_MODES[i]);
		}

		for (i = 0; i < MONTHLY_DEDUCTION_MODES.length; i++) {
			MONTHLY_DEDUCTION.add(MONTHLY_DEDUCTION_MODES[i]);
		}

		for (i = 0; i < SEMI_MONTHLY_DEDUCTION_MODES.length; i++) {
			SEMI_MONTHLY_DEDUCTION.add(SEMI_MONTHLY_DEDUCTION_MODES[i]);
		}

		for (i = 0; i < BI_WEEKLY_DEDUCTION_MODES.length; i++) {
			BI_WEEKLY_DEDUCTION.add(BI_WEEKLY_DEDUCTION_MODES[i]);
		}

		for (i = 0; i < WEEKLY_DEDUCTION_MODES.length; i++) {
			WEEKLY_DEDUCTION.add(WEEKLY_DEDUCTION_MODES[i]);
		}
	}

	public PayrollDeductionSchedule() {
		// Set defaults
		terminationDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
	}

	/**
	 * Get PayrollDeductionSchedulePK
	 * 
	 * @return
	 */
	public Long getPayrollDeductionSchedulePK() {
		return payrollDeductionSchedulePK;
	}

	/**
	 * Set PayrollDeductionSchedulePK
	 * 
	 * @param payrollDeductionSchedulePK
	 *            the PK
	 */
	public void setPayrollDeductionSchedulePK(Long payrollDeductionSchedulePK) {
		this.payrollDeductionSchedulePK = payrollDeductionSchedulePK;
	}

	/**
	 * Get ContractGroupFK
	 * 
	 * @return
	 */
	public Long getContractGroupFK() {
		return contractGroupFK;
	}

	/**
	 * Set ContractGroupFK
	 * 
	 * @param contractGroupFK
	 */
	public void setContractGroupFK(Long contractGroupFK) {
		this.contractGroupFK = contractGroupFK;
	}

	/**
	 * Get the ContractGroup (the parent of PayrollDeductionSchedule)
	 * 
	 * @return
	 */
	public ContractGroup getContractGroup() {
		return contractGroup;
	}

	/**
	 * Set ContractGroup (the parent of PayrollDeductionSchedule)
	 * 
	 * @param contractGroup
	 */
	public void setContractGroup(ContractGroup contractGroup) {
		this.contractGroup = contractGroup;
	}

	/**
	 * Get PRDTypeCT
	 * 
	 * @return
	 */
	public String getPRDTypeCT() {
		return prdTypeCT;
	}

	/**
	 * Set PRDTypeCT
	 * 
	 * @param prdTypeCT
	 */
	public void setPRDTypeCT(String prdTypeCT) {
		this.prdTypeCT = prdTypeCT;
	}

	/**
	 * Get InitialLeadDays
	 * 
	 * @return
	 */
	public int getInitialLeadDays() {
		return initialLeadDays;
	}

	/**
	 * Set InitialLeadDays
	 * 
	 * @param initialLeadDays
	 */
	public void setInitialLeadDays(int initialLeadDays) {
		this.initialLeadDays = initialLeadDays;
	}

	/**
	 * Get SubsequentLeadDays
	 * 
	 * @return
	 */
	public int getSubsequentLeadDays() {
		return subsequentLeadDays;
	}

	/**
	 * Set SubsequentLeadDays
	 * 
	 * @param subsequentLeadDays
	 */
	public void setSubsequentLeadDays(int subsequentLeadDays) {
		this.subsequentLeadDays = subsequentLeadDays;
	}

	/**
	 * Get EffectiveDate
	 * 
	 * @return
	 */
	public EDITDate getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * Set EffectiveDate
	 * 
	 * @param effectiveDate
	 */
	public void setEffectiveDate(EDITDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
	 * 
	 * @return
	 */
	public String getUIPRDEffectiveDate() {
		String date = null;

		if (getEffectiveDate() != null) {
			date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this
					.getEffectiveDate());
		}

		return date;
	}

	/**
	 * Convenience method to support UI's need for mm/dd/yyyy.
	 */
	public void setUIPRDEffectiveDate(String uiPRDEffectiveDate) {
		if (uiPRDEffectiveDate != null) {
			setEffectiveDate(DateTimeUtil
					.getEDITDateFromMMDDYYYY(uiPRDEffectiveDate));
		}
	}

	/**
	 * Get TerminationDate
	 * 
	 * @return
	 */
	public EDITDate getTerminationDate() {
		return terminationDate;
	}

	/**
	 * Set TerminationDate
	 * 
	 * @param terminationDate
	 */
	public void setTerminationDate(EDITDate terminationDate) {
		this.terminationDate = terminationDate;
	}

	/**
	 * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
	 * 
	 * @return
	 */
	public String getUIPRDTerminationDate() {
		String date = null;

		if (getTerminationDate() != null) {
			date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this
					.getTerminationDate());
		}

		return date;
	}

	/**
	 * Convenience method to support UI's need for mm/dd/yyyy.
	 */
	public void setUIPRDTerminationDate(String uiPRDTerminationDate) {
		if (uiPRDTerminationDate != null) {
			setTerminationDate(DateTimeUtil
					.getEDITDateFromMMDDYYYY(uiPRDTerminationDate));
		}
	}

	/**
	 * Get LastPRDExtractDate
	 * 
	 * @return
	 */
	public EDITDate getLastPRDExtractDate() {
		return lastPRDExtractDate;
	}

	/**
	 * Set LastPRDExtractDate
	 * 
	 * @param lastPRDExtractDate
	 */
	public void setLastPRDExtractDate(EDITDate lastPRDExtractDate) {
		this.lastPRDExtractDate = lastPRDExtractDate;
	}

	/**
	 * Get NextPRDExtractDate
	 * 
	 * @return
	 */
	public EDITDate getNextPRDExtractDate() {
		return nextPRDExtractDate;
	}

	/**
	 * Set NextPRDExtractDate
	 * 
	 * @param nextPRDExtractDate
	 */
	public void setNextPRDExtractDate(EDITDate nextPRDExtractDate) {
		this.nextPRDExtractDate = nextPRDExtractDate;
	}

	/**
	 * Get NextPRDDueDate
	 * 
	 * @return
	 */
	public EDITDate getNextPRDDueDate() {
		return nextPRDDueDate;
	}

	/**
	 * Set NextPRDDueDate
	 * 
	 * @param nextPRDDueDate
	 */
	public void setNextPRDDueDate(EDITDate nextPRDDueDate) {
		this.nextPRDDueDate = nextPRDDueDate;
	}

	/**
	 * Get currentDateThru
	 * 
	 * @return
	 */
	public EDITDate getCurrentDateThru() {
		return currentDateThru;
	}

	/**
	 * Set currentDateThru
	 * 
	 * @param currentDateThru
	 */
	public void setCurrentDateThru(EDITDate currentDateThru) {
		this.currentDateThru = currentDateThru;
	}

	/**
	 * Get PRDConsolidationCT
	 * 
	 * @return
	 */
	public String getPRDConsolidationCT() {
		return prdConsolidationCT;
	}

	/**
	 * Set PRDConsolidationCT
	 * 
	 * @param prdConsolidation
	 */
	public void setPRDConsolidationCT(String prdConsolidationCT) {
		this.prdConsolidationCT = prdConsolidationCT;
	}

	/**
	 * Get ReportTypeCT
	 * 
	 * @return
	 */
	public String getReportTypeCT() {
		return reportTypeCT;
	}

	/**
	 * Set ReportTypeCT
	 * 
	 * @param reportTypeCT
	 */
	public void setReportTypeCT(String reportTypeCT) {
		this.reportTypeCT = reportTypeCT;
	}

	/**
	 * Get SortOptionCT
	 * 
	 * @param sortOptionCT
	 * @return
	 */
	public String getSortOptionCT() {
		return sortOptionCT;
	}

	/**
	 * Set SortOptionCT
	 * 
	 * @param sortOptionCT
	 */
	public void setSortOptionCT(String sortOptionCT) {
		this.sortOptionCT = sortOptionCT;
	}

	/**
	 * Get SummaryCT
	 * 
	 * @return
	 */
	public String getSummaryCT() {
		return summaryCT;
	}

	/**
	 * Set SummaryCT
	 * 
	 * @param summaryCT
	 */
	public void setSummaryCT(String summaryCT) {
		this.summaryCT = summaryCT;
	}

	/**
	 * Get OutputTypeCT
	 * 
	 * @return
	 */
	public String getOutputTypeCT() {
		return outputTypeCT;
	}

	/**
	 * Set OutputTypeCT
	 * 
	 * @param outputTypeCT
	 */
	public void setOutputTypeCT(String outputTypeCT) {
		this.outputTypeCT = outputTypeCT;
	}

	/**
	 * Get ChangeEffectiveDate
	 * 
	 * @return
	 */
	public EDITDate getChangeEffectiveDate() {
		return changeEffectiveDate;
	}

	/**
	 * Set ChangeEffectiveDate
	 * 
	 * @param changeEffectiveDate
	 */
	public void setChangeEffectiveDate(EDITDate changeEffectiveDate) {
		this.changeEffectiveDate = changeEffectiveDate;
	}

	/**
	 * Get CreationOperator
	 * 
	 * @return
	 */
	public String getCreationOperator() {
		return creationOperator;
	}

	/**
	 * Set CreationOperator
	 * 
	 * @param creationOperator
	 */
	public void setCreationOperator(String creationOperator) {
		this.creationOperator = creationOperator;
	}

	/**
	 * Get CreationDate
	 * 
	 * @return
	 */
	public EDITDate getCreationDate() {
		return creationDate;
	}

	/**
	 * Set CreationDate
	 * 
	 * @param creationDate
	 */
	public void setCreationDate(EDITDate creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Return the set of PayrollDeduction records associated with this
	 * PayrollDeductionSchedule
	 * 
	 * @return
	 */
	public Set<PayrollDeduction> getPayrollDeductions() {
		return payrollDeductions;
	}

	/**
	 * @see #payrollDeductions
	 * @param payrollDeductions
	 */
	public void setPayrollDeductions(Set<PayrollDeduction> payrollDeductions) {
		this.payrollDeductions = payrollDeductions;
	}

	public void addPayrollDeduction(PayrollDeduction payrollDeduction) {
		this.payrollDeductions.add(payrollDeduction);
	}

	/**
	 * Finder.
	 * 
	 * @param contractGroupFK
	 * @return
	 */
	public static PayrollDeductionSchedule findByPayrollDeductionSchedulePK(
			Long payrollDeductionSchedulePK) {
		return (PayrollDeductionSchedule) SessionHelper.get(
				PayrollDeductionSchedule.class, payrollDeductionSchedulePK,
				PayrollDeductionSchedule.DATABASE);
	}

	/**
	 * Finder.
	 * 
	 * @param contractGroup
	 * @return
	 */
	public static PayrollDeductionSchedule findByContractGroup(
			Long contractGroupFK) {
		String hql = " from PayrollDeductionSchedule payrollDeductionSchedule"
				+ " where payrollDeductionSchedule.ContractGroupFK = :contractGroupFK";

		EDITMap params = new EDITMap("contractGroupFK", contractGroupFK);

		List<PayrollDeductionSchedule> results = SessionHelper.executeHQL(hql,
				params, PayrollDeductionSchedule.DATABASE);

		if (results.size() > 0) {
			return (PayrollDeductionSchedule) results.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Calculates the payroll deduction extract and due dates.
	 * 
	 * @param payDedSched
	 * @param billScheduleFK
	 */
	public static void calculateAndSetPRDExtractDates(
			PayrollDeductionSchedule payDedSched, Long billScheduleFK) {
		BillSchedule billSchedule = BillSchedule
				.findBy_BillSchedulePK(billScheduleFK);
		EDITDate lastBillDueDate = null;
		EDITDate firstBillDueDate = null;
		
		if (billSchedule != null) {
			
			lastBillDueDate = billSchedule.getLastBillDueDate();
			firstBillDueDate = billSchedule.getFirstBillDueDate();
		}

		if (payDedSched.getLastPRDExtractDate() == null
				&& lastBillDueDate == null) {
			if (billSchedule != null) {
				if (payDedSched.getPRDTypeCT().equalsIgnoreCase(PRD_CUSTOM)) {
					PayrollDeductionCalendar pdc = PayrollDeductionCalendar
							.findNextPayrollDeductionCalendarBy_PayrollDeductionSchedulePK_PayrollDeductionDate(
									payDedSched.getPayrollDeductionSchedulePK(),
									new EDITDate(EDITDate.DEFAULT_MIN_DATE));
					if (pdc != null) {
						payDedSched.setNextPRDDueDate(pdc
								.getPayrollDeductionDate());
						payDedSched.setNextPRDExtractDate(pdc
								.getPayrollDeductionDate());
						payDedSched.setCurrentDateThru(payDedSched
								.getNextPRDDueDate().addMonths(1)
								.getEndOfMonthDate());
					}
				} else {
					EDITDate nextPRDDueDate = billSchedule
							.getFirstDeductionDate();

					// first get Best BusinessDate for DueDate and then for each
					// subsequent step
					BusinessCalendar bc = new BusinessCalendar();
					BusinessDay bd = bc
							.getBestUnrestictedBusinessDay(nextPRDDueDate);
					nextPRDDueDate = bd.getBusinessDate();

					// Now subtract InitialLeadDays and get the Best Business
					// Day
					nextPRDDueDate = nextPRDDueDate.subtractDays(payDedSched
							.getInitialLeadDays());

					// For STANDARD make sure nextPRDDueDate falls on a Friday
					if (payDedSched.getPRDTypeCT().equalsIgnoreCase(
							PRD_STANDARD)) {
						if (payDedSched.getInitialLeadDays() == 0) {
					        nextPRDDueDate = nextPRDDueDate.subtractDays(14);
						}
						while (!nextPRDDueDate.getDayOfWeek().equals(
								EDITDate.FRIDAY)) {
							nextPRDDueDate = nextPRDDueDate.addDays(1);
						}
					}

					bd = bc.getBestUnrestictedBusinessDay(nextPRDDueDate);
					nextPRDDueDate = bd.getBusinessDate();

					// Subtract 1 day and get the BestBusiness Day
					EDITDate nextPRDExtractDate = nextPRDDueDate
							.subtractDays(1);
					bd = bc.getBestUnrestictedBusinessDay(nextPRDExtractDate);
					nextPRDExtractDate = bd.getBusinessDate();

					payDedSched.setNextPRDDueDate(nextPRDDueDate);
					payDedSched.setNextPRDExtractDate(nextPRDExtractDate);

					if (payDedSched.getPRDTypeCT().equalsIgnoreCase(
							PRD_STANDARD)) {
						// deck: Was adding two months. Now adding 14 days plus
						// 1 month.
						payDedSched.setCurrentDateThru(payDedSched
								.getNextPRDExtractDate().addDays(14)
								.addMonths(1).getEndOfMonthDate());
					} else {
						payDedSched.setCurrentDateThru(payDedSched
								.getNextPRDDueDate().addMonths(1)
								.getEndOfMonthDate());
					}

				}
			}
		} else
		// deck: advance nextPRDDueDate 1 day if of type standard and
		// nextPRDDueDate and nextPRDExtractDate are equal
		if (payDedSched.getNextPRDDueDate() != null && 
				payDedSched.getNextPRDExtractDate() != null && 
				payDedSched.getPRDTypeCT().equalsIgnoreCase(PRD_STANDARD)
				&& payDedSched.getNextPRDDueDate().equals(payDedSched.getNextPRDExtractDate())) {
			
			payDedSched.setNextPRDDueDate(payDedSched.getNextPRDExtractDate().addDays(1));
		}

	}

	public static void updatePRDExtractDates(
			PayrollDeductionSchedule payDedSched,
			EDITDate deductionChangeStartDate) {
		EDITDate nextPRDDueDate = deductionChangeStartDate;
		EDITDate nextPRDExtractDate = payDedSched.getNextPRDExtractDate();
		int leadDays = 0;

		BusinessCalendar bc = new BusinessCalendar();

		// always use the leadDays on the tables for the ExtractDate calc, get
		// best business date
		if (payDedSched.getLastPRDExtractDate() == null) {
			leadDays = (payDedSched.getInitialLeadDays());
		} else {
			leadDays = (payDedSched.getSubsequentLeadDays());
		}

		EDITDate tempDate = nextPRDDueDate.subtractDays(leadDays);
		BusinessDay bd = bc.getBestUnrestictedBusinessDay(tempDate);
		EDITDate nextPRDDueDateBD = bd.getBusinessDate();
		leadDays = 1;

		// Always subtract 1 day here then get the best business day
		nextPRDExtractDate = nextPRDDueDateBD.subtractDays(leadDays);
		bd = bc.getBestUnrestictedBusinessDay(nextPRDExtractDate);
		nextPRDExtractDate = bd.getBusinessDate();

		payDedSched.setNextPRDDueDate(nextPRDDueDate);
		payDedSched.setNextPRDExtractDate(nextPRDExtractDate);
		if (payDedSched.getPRDTypeCT().equalsIgnoreCase(PRD_STANDARD)) {
			payDedSched.setCurrentDateThru(payDedSched.getNextPRDExtractDate().addDays(14)
					.addMonths(1).getEndOfMonthDate());
		} else {
			payDedSched.setCurrentDateThru(payDedSched.getNextPRDDueDate().addMonths(1)
					.getEndOfMonthDate());
		}

	}

	public void createPRDExtract(EDITDate extractDate) throws EDITCaseException {
		EditServiceLocator
				.getSingleton()
				.getBatchAgent()
				.getBatchStat(Batch.BATCH_JOB_CREATE_PRD_EXTRACT)
				.tagBatchStart(Batch.BATCH_JOB_CREATE_PRD_EXTRACT,
						"PRD Extract");

		try {
			Long[] payrollDeductionSchedulePKs = getPayrollDeductionSchedulePKsToProcess(extractDate);

			int payrollDeductionCount = processPayrollDeductions(
					payrollDeductionSchedulePKs, extractDate);

			if (payrollDeductionCount > 0) {
				EditServiceLocator.getSingleton().getBatchAgent()
						.getBatchStat(Batch.BATCH_JOB_CREATE_PRD_EXTRACT)
						.updateSuccess();
			}
		} catch (EDITCaseException e) {
			EditServiceLocator.getSingleton().getBatchAgent()
					.getBatchStat(Batch.BATCH_JOB_CREATE_PRD_EXTRACT)
					.updateFailure();

			Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

			Log.logGeneralExceptionToDatabase(null, e);

			System.out.println(e);

			e.printStackTrace();
		} finally {
			EditServiceLocator.getSingleton().getBatchAgent()
					.getBatchStat(Batch.BATCH_JOB_CREATE_PRD_EXTRACT)
					.tagBatchStop();
		}
	}

	private Long[] getPayrollDeductionSchedulePKsToProcess(EDITDate extractDate) {
		String hql = " select pds from PayrollDeductionSchedule pds"
				+ " where pds.NextPRDExtractDate <= :extractDate";

		EDITMap params = new EDITMap("extractDate", extractDate);

		List<PayrollDeductionSchedule> results = SessionHelper.executeHQL(hql,
				params, PayrollDeductionSchedule.DATABASE);

		List<Long> payrollDeductionSchedulePKs = new ArrayList<Long>();

		for (int i = 0; i < results.size(); i++) {
			PayrollDeductionSchedule payrollDeductionSchedule = results.get(i);

			payrollDeductionSchedulePKs.add(payrollDeductionSchedule
					.getPayrollDeductionSchedulePK());
		}

		return (Long[]) payrollDeductionSchedulePKs
				.toArray(new Long[payrollDeductionSchedulePKs.size()]);
	}

	private int processPayrollDeductions(Long[] payrollDeductionSchedulePKs,
			EDITDate extractDate) throws EDITCaseException {
		int payrollDeductionCount = 0;

		EDITDateTime stagingDate = new EDITDateTime(extractDate.toString()
				+ EDITDateTime.DATE_TIME_DELIMITER
				+ new EDITDateTime().getFormattedTime());

		String reportType = null;

		List payrollDeductionList = null;

		ContractGroup contractGroup = null;

		Segment segment = null;

		try {
			for (int i = 0; i < payrollDeductionSchedulePKs.length; i++) {
				PayrollDeductionSchedule payrollDeductionSchedule = PayrollDeductionSchedule
						.findBy_PK(payrollDeductionSchedulePKs[i]);

				SessionHelper
						.beginTransaction(PayrollDeductionSchedule.DATABASE);

				reportType = Util.initString(
						payrollDeductionSchedule.getReportTypeCT(), "");

				contractGroup = payrollDeductionSchedule.getContractGroup();

				BillSchedule billSchedule = contractGroup.getBillSchedule();

				Set<Segment> segments = contractGroup.getSegments();

				payrollDeductionList = new ArrayList();

				Iterator it = segments.iterator();

				while (it.hasNext()) {
					segment = (Segment) it.next();

					if (segment.getSegmentFK() == null) {
						if (shouldDeductFromPayroll(segment)) {
							PremiumDue[] premiumDues = PremiumDue
									.getPremiumDueForExtract(segment,
											payrollDeductionSchedule);

							if (premiumDues.length > 0) {
								if (!premiumDues[0]
										.getPendingExtractIndicator()
										.equalsIgnoreCase("T")) {
									PayrollDeduction payrollDeduction = createPayrollDeduction(
											premiumDues[0], segment,
											extractDate,
											payrollDeductionSchedule,
											billSchedule);

									payrollDeductionList.add(payrollDeduction);
								}
							}
						}
					}
				}

				Segment[] prevSegments = Segment
						.findBy_PriorContractGroupFK(contractGroup
								.getContractGroupPK());

				if (prevSegments != null) {
					for (int j = 0; j < prevSegments.length; j++) {
						PayrollDeduction payrollDeduction = createZeroPayrollDeduction(
								prevSegments[j], extractDate,
								payrollDeductionSchedule, billSchedule,
								contractGroup);

						payrollDeductionList.add(payrollDeduction);
					}
				}

				payrollDeductionSchedule
						.setLastPRDExtractDate(payrollDeductionSchedule
								.getNextPRDExtractDate());

				EDITDate nextPRDDueDate = payrollDeductionSchedule
						.getNextPRDDueDate();
				EDITDate nextPRDExtractDate = payrollDeductionSchedule
						.getNextPRDExtractDate();
				EDITDate currentDateThru = payrollDeductionSchedule
						.getCurrentDateThru();
				EDITDate nextPRDDueDateBD = null;
				int leadDays = 0;
				BusinessCalendar bc = new BusinessCalendar();
				// deck: fix handling of important PRD dates for type Standard.
				boolean isStandard = false;

				if (payrollDeductionSchedule.getPRDTypeCT().equalsIgnoreCase(
						PRD_STANDARD)
						|| payrollDeductionSchedule.getPRDTypeCT()
								.equalsIgnoreCase(PRD_FIXED_SCHEDULE)) {
					if (payrollDeductionSchedule.getPRDTypeCT()
							.equalsIgnoreCase(PRD_STANDARD)) {
						isStandard = true;
						// the first time a PRD is run as STANDARD make sure
						// nextPRDDueDate advances to the following week.
						/*
						 * if (nextPRDDueDate.getElapsedDays(bc.getNextFriday(
						 * nextPRDDueDate)) < 7 ) { nextPRDDueDate =
						 * bc.getNextFriday(nextPRDDueDate); }
						 */
						nextPRDDueDate = bc.getNextFriday(nextPRDDueDate);
						nextPRDDueDateBD = nextPRDDueDateAsBusinessDate(nextPRDDueDate);

						// standard always subtract 1 from nextPRDDueDate for
						// ExtractDate calc
						leadDays = 1;
					} else if (payrollDeductionSchedule.getPRDTypeCT()
							.equalsIgnoreCase(PRD_FIXED_SCHEDULE)) {
						String deductionMode = getMode(billSchedule
								.getDeductionFrequencyCT());

						nextPRDDueDate = nextPRDDueDate.addMode(deductionMode);
						nextPRDDueDateBD = nextPRDDueDateAsBusinessDate(nextPRDDueDate);

						if (payrollDeductionSchedule.getNextPRDDueDate()
								.getMonth() != nextPRDDueDate.getMonth()) {
							currentDateThru = currentDateThru.addMonths(1)
									.getEndOfMonthDate();
						}

						// always use the leadDays on the tables for the
						// ExtractDate calc, get best business date
						leadDays = (payrollDeductionSchedule
								.getSubsequentLeadDays());
						EDITDate tempDate = nextPRDDueDateBD
								.subtractDays(leadDays);
						BusinessDay bd = bc
								.getBestUnrestictedBusinessDay(tempDate);
						nextPRDDueDateBD = bd.getBusinessDate();
						leadDays = 1;

					}

					// Always subtract 1 day here then get the best business day
					nextPRDExtractDate = nextPRDDueDateBD
							.subtractDays(leadDays);
					BusinessDay bd = bc
							.getBestUnrestictedBusinessDay(nextPRDExtractDate);
					nextPRDExtractDate = bd.getBusinessDate();

					// deck: Use nextPRDExtractDate to check for currentDateThru
					// advance.
					// nextPRDExtractDate = 11/27
					// currentDateThru.getMonth() = 1
					if (isStandard) {
						int nMonth = nextPRDExtractDate.addDays(14).getMonth();
						int cMonth = currentDateThru.getMonth();
						// System.out.println("nMonth: " + nMonth);
						// System.out.println("cMonth: " + cMonth);

						if (nMonth == cMonth) {
							currentDateThru = currentDateThru.addMonths(1)
									.getEndOfMonthDate();
							// System.out.println("Advance currentDateThru to: "
							// + currentDateThru);
						}
					}

				} else if (payrollDeductionSchedule.getPRDTypeCT()
						.equalsIgnoreCase(PRD_CUSTOM)) {
					PayrollDeductionCalendar pdc = PayrollDeductionCalendar
							.findNextPayrollDeductionCalendarBy_PayrollDeductionSchedulePK_PayrollDeductionDate(
									payrollDeductionSchedule
											.getPayrollDeductionSchedulePK(),
									nextPRDDueDate);

					if (pdc != null) {
						nextPRDDueDate = pdc.getPayrollDeductionDate();
						nextPRDExtractDate = nextPRDDueDate;

						currentDateThru = nextPRDDueDate.addMonths(1)
								.getEndOfMonthDate();
					}
				}

				payrollDeductionSchedule.setNextPRDDueDate(nextPRDDueDate);

				payrollDeductionSchedule
						.setNextPRDExtractDate(nextPRDExtractDate);

				payrollDeductionSchedule.setCurrentDateThru(currentDateThru);

				if (payrollDeductionList.size() > 0) {
					payrollDeductionCount += payrollDeductionList.size();

					for (int j = 0; j < payrollDeductionList.size(); j++) {
						payrollDeductionSchedule
								.addPayrollDeduction((PayrollDeduction) payrollDeductionList
										.get(j));
					}
				}

				payrollDeductionSchedule.hSave();

				SessionHelper
						.commitTransaction(PayrollDeductionSchedule.DATABASE);

				if (payrollDeductionList.size() > 0) {
					PRDStaging prdStaging = new PRDStaging(stagingDate);

					prdStaging.stageTables(payrollDeductionSchedule);
				}

				SessionHelper.clearSessions();
			}

			return payrollDeductionCount;
		} catch (Exception e) {
			SessionHelper
					.rollbackTransaction(PayrollDeductionSchedule.DATABASE);

			System.out.println(e);

			e.printStackTrace();

			logErrorToDatabase(e, segment, contractGroup, getNextPRDDueDate());

			throw new EDITCaseException(e.getMessage());
		}
	}

	private void logErrorToDatabase(Exception e, Segment segment,
			ContractGroup groupContractGroup, EDITDate nextPRDDueDate) {
		Company comapny = Company.findByProductStructurePK(segment
				.getProductStructureFK());

		ClientRole groupClientRole = groupContractGroup.getClientRole();

		ClientDetail groupClientDetail = groupClientRole.getClientDetail();

		String groupContractGroupName = groupClientDetail.getCorporateName();

		EDITMap columnInfo = new EDITMap("CompanyName",
				comapny.getCompanyName());
		columnInfo.put("GroupNumber",
				groupContractGroup.getContractGroupNumber());
		columnInfo.put("GroupName", groupContractGroupName);
		columnInfo.put("PRDDueDate", nextPRDDueDate.getFormattedDate());

		logging.Log.logToDatabase(logging.Log.PRD_Extract,
				"PRD Extract Job Errored: " + e.getMessage(), columnInfo);
	}

	private EDITDate nextPRDDueDateAsBusinessDate(EDITDate nextPRDDueDate) {
		BusinessCalendar bc = new BusinessCalendar();

		BusinessDay bd = bc.getBestUnrestictedBusinessDay(nextPRDDueDate);

		EDITDate nextPRDDueDateBD = bd.getBusinessDate();

		return nextPRDDueDateBD;
	}

	public PayrollDeduction createPayrollDeduction(PremiumDue premiumDue,
			Segment segment, EDITDate extractDate,
			PayrollDeductionSchedule payDedSched, BillSchedule billSchedule)
			throws EDITCaseException {
		PayrollDeduction payrollDeduction = new PayrollDeduction();
		payrollDeduction.setPayrollDeductionSchedule(payDedSched);
		payrollDeduction.setSegment(segment);
		payrollDeduction.setDeductionAmount(premiumDue.getDeductionAmount());
		if (premiumDue.getDeductionAmountOverride().isGT("0")) {
			payrollDeduction.setDeductionAmount(premiumDue
					.getDeductionAmountOverride());
		}
		payrollDeduction.setPRDExtractDate(extractDate);
		payrollDeduction.setFirstPayrollDate(billSchedule
				.getFirstDeductionDate());

		String pendingExtractIndicator = premiumDue
				.getPendingExtractIndicator();
		if (pendingExtractIndicator
				.equalsIgnoreCase(PremiumDue.PENDING_EXTRACT_A)) {
			payrollDeduction.setTypeOfChange(null);
		} else if (premiumDue.getDeductionAmount().isGT(new EDITBigDecimal())) {
			payrollDeduction.setTypeOfChange(PayrollDeductionSchedule.CHANGE);
		} else {
			payrollDeduction
					.setTypeOfChange(PayrollDeductionSchedule.TERMINATED);
		}

		segment.addPayrollDeduction(payrollDeduction);

		EDITBigDecimal zero = new EDITBigDecimal();

		if (pendingExtractIndicator
				.equalsIgnoreCase(PremiumDue.PENDING_EXTRACT_P)
				|| pendingExtractIndicator
						.equalsIgnoreCase(PremiumDue.PENDING_EXTRACT_A)
				|| pendingExtractIndicator
						.equalsIgnoreCase(PremiumDue.PENDING_EXTRACT_B)) {
			if (premiumDue.getDeductionAmount().isGT(zero)) {
				premiumDue
						.setPendingExtractIndicator(PremiumDue.PENDING_EXTRACT_A);
			} else if (premiumDue.getDeductionAmount().equals(zero)) {
				premiumDue
						.setPendingExtractIndicator(PremiumDue.PENDING_EXTRACT_T);
			}
		}

		if (pendingExtractIndicator
				.equalsIgnoreCase(PremiumDue.PENDING_EXTRACT_U)) {
			premiumDue.setPendingExtractIndicator(PremiumDue.PENDING_EXTRACT_M);
		}

		try {
			payrollDeduction.hSave();
			premiumDue.hSave();
			segment.hSave();
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			throw new EDITCaseException(e.getMessage());
		}

		return payrollDeduction;
	}

	public PayrollDeduction createZeroPayrollDeduction(Segment segment,
			EDITDate extractDate, PayrollDeductionSchedule payDedSched,
			BillSchedule billSchedule, ContractGroup contractGroup)
			throws EDITCaseException {
		PayrollDeduction payrollDeduction = new PayrollDeduction();
		payrollDeduction.setPayrollDeductionSchedule(payDedSched);
		payrollDeduction.setSegment(segment);
		payrollDeduction.setDeductionAmount(new EDITBigDecimal());
		payrollDeduction.setPRDExtractDate(extractDate);
		payrollDeduction.setFirstPayrollDate(billSchedule
				.getFirstDeductionDate());
		payrollDeduction.setTypeOfChange(PayrollDeductionSchedule.TERMINATED);

		segment.addPayrollDeduction(payrollDeduction);
		segment.setPriorPRDDue("N");

		try {
			payrollDeduction.hSave();
			segment.hSave();
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			logErrorToDatabase(e, segment, contractGroup, getNextPRDDueDate());

			throw new EDITCaseException(e.getMessage());
		}

		return payrollDeduction;
	}

	public String getMode(String deductionFrequency) {
		String mode = null;

		if (ANNUAL_DEDUCTION.contains(deductionFrequency)) {
			mode = EDITDate.ANNUAL_MODE;
		}

		if (SEMI_ANNUAL_DEDUCTION.contains(deductionFrequency)) {
			mode = EDITDate.SEMI_ANNUAL_MODE;
		}

		if (QUARTERLY_DEDUCTION.contains(deductionFrequency)) {
			mode = EDITDate.QUARTERLY_MODE;
		}

		if (MONTHLY_DEDUCTION.contains(deductionFrequency)
				|| NINETHLY_DEDUCTION.contains(deductionFrequency)
				|| TENTHLY_DEDUCTION.contains(deductionFrequency)
				|| SEMI_MONTHLY_DEDUCTION.contains(deductionFrequency)) {
			mode = EDITDate.MONTHLY_MODE;
		}

		if (BI_WEEKLY_DEDUCTION.contains(deductionFrequency)) {
			mode = EDITDate.BIWEEKLY_MODE;
		}

		if (WEEKLY_DEDUCTION.contains(deductionFrequency)) {
			mode = EDITDate.WEEKLY_MODE;
		}

		return mode;
	}

	private boolean shouldDeductFromPayroll(Segment segment) {
		boolean shouldDeductFromPayroll = true;

		BillSchedule billSchedule = segment.getBillSchedule();

		if (!billSchedule.getBillMethodCT().equalsIgnoreCase(
				BillSchedule.BILL_METHOD_LISTBILL)) {
			shouldDeductFromPayroll = false;
		}

		return shouldDeductFromPayroll;
	}

	public String getDatabase() {
		return PayrollDeductionSchedule.DATABASE;
	}

	/**
	 * @see #payrollDeductionCalendars
	 * @param payrollDeductionCalendars
	 */
	public void setPayrollDeductionCalendars(
			Set<PayrollDeductionCalendar> payrollDeductionCalendars) {
		this.payrollDeductionCalendars = payrollDeductionCalendars;
	}

	/**
	 * @see #payrollDeductionCalendars
	 * @return all payrollDeductionCalendars associated with the
	 *         PayrollDeduction
	 */
	public Set<PayrollDeductionCalendar> getPayrollDeductionCalendars() {
		return payrollDeductionCalendars;
	}

	/**
	 * Finder.
	 * 
	 * @param payrollDeductionSchedulePK
	 */
	public static PayrollDeductionSchedule findBy_PK(
			Long payrollDeductionSchedulePK) {
		return (PayrollDeductionSchedule) SessionHelper.get(
				PayrollDeductionSchedule.class, payrollDeductionSchedulePK,
				DATABASE);
	}

	public StagingContext stage(StagingContext stagingContext) {
		staging.PayrollDeductionSchedule stagingPRDSched = new staging.PayrollDeductionSchedule();
		stagingPRDSched.setGroup(stagingContext.getCurrentGroup());
		stagingPRDSched.setStaging(stagingContext.getStaging());
		stagingPRDSched.setPRDType(this.prdTypeCT);
		stagingPRDSched.setInitialLeadDays(this.initialLeadDays);
		stagingPRDSched.setSubsequentLeadDays(this.subsequentLeadDays);
		stagingPRDSched.setEffectiveDate(this.effectiveDate);
		stagingPRDSched.setTerminationDate(this.terminationDate);
		stagingPRDSched.setLastPRDExtractDate(this.lastPRDExtractDate);
		stagingPRDSched.setNextPRDExtractDate(this.nextPRDExtractDate);
		stagingPRDSched.setNextPRDDueDate(this.nextPRDDueDate);
		stagingPRDSched.setCurrentDateThru(this.currentDateThru);
		stagingPRDSched.setPRDConsolidation(this.prdConsolidationCT);
		stagingPRDSched.setReportType(this.reportTypeCT);
		stagingPRDSched.setSortOption(this.sortOptionCT);
		stagingPRDSched.setSummary(this.summaryCT);
		stagingPRDSched.setOutputType(this.outputTypeCT);
		stagingPRDSched.setCreationOperator(this.creationOperator);
		stagingPRDSched.setCreationDate(this.creationDate);

		stagingContext.getStaging()
				.addPayrollDeductionSchedule(stagingPRDSched);
		stagingContext.getCurrentGroup().addPayrollDeductionSchedule(
				stagingPRDSched);

		stagingContext.setCurrentPRDSchedule(stagingPRDSched);

		SessionHelper.saveOrUpdate(stagingPRDSched, SessionHelper.STAGING);

		return stagingContext;
	}
}
