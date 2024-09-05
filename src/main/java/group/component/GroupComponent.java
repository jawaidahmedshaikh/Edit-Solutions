/*
 * User: sdorman
 * Date: Jun 15, 2007
 * Time: 10:05:12 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package group.component;

import edit.common.CodeTableWrapper;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.exceptions.EDITCaseException;
import edit.common.exceptions.EDITContractException;
import edit.common.exceptions.EDITEventException;
import edit.common.exceptions.EDITException;
import edit.common.vo.AgentVO;
import edit.common.vo.AreaValueVO;
import edit.common.vo.CodeTableVO;
import edit.common.vo.PlacedAgentBranchVO;
import edit.common.vo.PlacedAgentVO;
import edit.common.vo.ValidationVO;
import edit.services.config.ServicesConfig;
import edit.services.db.hibernate.HibernateEntityDifference;
import edit.services.db.hibernate.SessionHelper;
import engine.AreaKey;
import engine.AreaValue;
import engine.Company;
import engine.Fund;
import engine.ProductStructure;
import engine.business.Calculator;
import engine.component.CalculatorComponent;
import engine.sp.SPException;
import engine.sp.SPOutput;
import engine.sp.custom.document.BatchContractSetupDocument;
import engine.sp.custom.document.SegmentDocument;
import event.EDITTrx;
import fission.utility.DateTimeUtil;
import fission.utility.Util;
import fission.utility.UtilFile;
import fission.utility.XMLUtil;
import group.BatchContractSetup;
import group.BatchProductLog;
import group.BatchProgressLog;
import group.CaseProductUnderwriting;
import group.CaseSetup;
import group.CaseStatusChangeHistory;
import group.ContractGroup;
import group.ContractGroupNote;
import group.ContractGroupRequirement;
import group.DepartmentLocation;
import group.Enrollment;
import group.EnrollmentLeadServiceAgent;
import group.EnrollmentState;
import group.PayrollDeductionCalendar;
import group.PayrollDeductionSchedule;
import group.ProjectedBusinessByMonth;
import group.SelectedAgentHierarchy;
import group.business.Group;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.hibernate.classic.Session;

import com.google.common.base.Joiner;

import org.hibernate.exception.ConstraintViolationException;
import productbuild.FilteredQuestionnaire;
import productbuild.Questionnaire;
import role.ClientRole;
import webservice.SEGRequestDocument;
import webservice.SEGResponseDocument;
import agent.Agent;
import agent.AgentContract;
import agent.CommissionProfile;
import agent.PlacedAgent;
import agent.PlacedAgentCommissionProfile;
import billing.BillSchedule;
import client.ClientAddress;
import client.ClientDetail;
import client.TaxInformation;
import codetable.CodeTableDef;
import codetable.dm.dao.CodeTableDAO;
import contract.AgentHierarchy;
import contract.AgentHierarchyAllocation;
import contract.AgentSnapshot;
import contract.CommissionPhase;
import contract.ContractClient;
import contract.ContractClientAllocation;
import contract.FilteredProduct;
import contract.FilteredRequirement;
import contract.Life;
import contract.MasterContract;
import contract.PremiumDue;
import contract.QuestionnaireResponse;
import contract.Requirement;
import contract.Segment;

/**
 * Group-based services
 */
public class GroupComponent implements Group {

	public Document getDeductionFrequencyDesc(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();
		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Found Deduction Frequency");

		// Get the input information from the request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");
		Element batchContractSetupPKElement = requestParametersElement.element("BatchContractSetupPK");
		Long batchContractSetupPK = new Long(batchContractSetupPKElement.getText());
		BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(batchContractSetupPK);
		ContractGroup contractGroup = ContractGroup.findByPK(batchContractSetup.getContractGroupFK());
		BillSchedule billSchedule = BillSchedule.findBy_BillSchedulePK(contractGroup.getBillScheduleFK());
		String deductionFrequencyCT = billSchedule.getDeductionFrequencyCT();
		String billingModeCT = billSchedule.getBillingModeCT();
		// String billType = billSchedule.getBillTypeCT();
		String desc = "";
		if (billingModeCT.equals("Annual")) {
			desc = "Annual";
		} else if (billingModeCT.equals("SemiAnn")) {
			desc = "Semi-Annually";
		} else if (billingModeCT.equals("Quart")) {
			desc = "Quarterly";
		} else if (billingModeCT.equals("Month")) {
			if (deductionFrequencyCT.equals("09")) {
				desc = "Ninethly, once per month";
			} else if (deductionFrequencyCT.equals("10")) {
				desc = "Tenthly, once per month";
			} else if (deductionFrequencyCT.equals("12")) {
				desc = "Monthly, once per month";
			} else if (deductionFrequencyCT.equals("18")) {
				desc = "Ninethly, twice per month";
			} else if (deductionFrequencyCT.equals("20")) {
				desc = "Tenthly, twice per month";
			} else if (deductionFrequencyCT.equals("24")) {
				desc = "Monthly, twice per month";
			} else if (deductionFrequencyCT.equals("36")) {
				desc = "Ninethly, four times per month";
			} else if (deductionFrequencyCT.equals("40")) {
				desc = "Tenthly, four times per month";
			} else if (deductionFrequencyCT.equals("48")) {
				desc = "Monthly, four times per month";
			}
		} else if (billingModeCT.equals("VarMonth")) {
			if (billingModeCT.equals("26")) {
				desc = "Bi-weekly";
			} else if (billingModeCT.equals("52")) {
				desc = "Weekly";
			}
		} else if (billingModeCT.equals("13thly")) {
			if (deductionFrequencyCT.equals("26")) {
				desc = "Thirteenthly, twice per month";
			} else if (deductionFrequencyCT.equals("52")) {
				desc = "Thirteenthly, four times per month";
			}
		}

		Element deductionFrequencyDescElement = new DefaultElement("DeductionFrequencyDesc");

		deductionFrequencyDescElement.setText(desc);
		responseDocument.addToRootElement(deductionFrequencyDescElement);

		return responseDocument.getDocument();

	}

	/**
	 * @param contractGroupNumber
	 * @return
	 * @see contract.business.Contract#contractGroupExists(java.lang.String)
	 */
	public boolean contractGroupExists(String contractGroupNumber) {
		return ContractGroup.contractGroupExists(contractGroupNumber);
	}

	/**
	 * @param productStructurePK
	 * @param contractGroupPK
	 * @param effectiveDate
	 * @see #addProductStructureToContractGroup(Long, Long, EDITDate, String)
	 */
	public void addProductStructureToContractGroup(Long productStructurePK, Long contractGroupPK,
			EDITDate effectiveDate, String operator) throws EDITContractException {
		ContractGroup contractGroup = ContractGroup.findBy_ContractGroupPK(contractGroupPK);

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			FilteredProduct.build(productStructurePK, contractGroup);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITContractException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	/**
	 * Associates the specified underwriting criteria to the specified Enrollment.
	 * If the exact underwriting is already associated to the Enrollment, the
	 * request is ignored.
	 *
	 * @param areaValuePK
	 * @param activeEnrollmentPK
	 * @param filteredProductPK
	 * @param relToEE
	 * @param deptLocFK
	 * @param value
	 * @param includeOptionalCT
	 */
	public void addUnderwriting(Long areaValuePK, Long activeEnrollmentPK, Long filteredProductPK, String relToEE,
			String deptLocFK, String value, String includeOptionalCT) throws EDITCaseException {
		Enrollment enrollment = Enrollment.findByPK(activeEnrollmentPK);

		FilteredProduct filteredProduct = FilteredProduct.findByPK(filteredProductPK);

		Long departmentLocationFK = null;

		if (deptLocFK != null) {
			departmentLocationFK = new Long(deptLocFK);
		}

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			AreaValue[] areaValue = AreaValue.findBy_AreaValuePK(areaValuePK);

			AreaKey areaKey = AreaKey.findBy_AreaKeyPK(((AreaValueVO) areaValue[0].getVO()).getAreaKeyFK());
			if (areaKey.getField().equalsIgnoreCase(AreaKey.FIELD_ALLOWRIDERS) && includeOptionalCT == null) {
				throw new Exception("Incl/Opt Value Must Be Selected For ALLOWRIDERS");
			} else {
				CaseProductUnderwriting caseProductUnderwriting = CaseProductUnderwriting.build(areaValuePK, enrollment,
						filteredProduct, relToEE, departmentLocationFK, value, includeOptionalCT);

				SessionHelper.saveOrUpdate(caseProductUnderwriting, SessionHelper.EDITSOLUTIONS);
				SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
			}
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	/**
	 * Removes the selected underwriting criteria from the database
	 *
	 * @param selectedPKs
	 * @throws EDITCaseException
	 */
	public void removeUnderwriting(String[] selectedPKs) throws EDITCaseException {
		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			for (int i = 0; i < selectedPKs.length; i++) {
				CaseProductUnderwriting caseProductUnderwriting = CaseProductUnderwriting
						.findByPK(new Long(selectedPKs[i]));

				SessionHelper.delete(caseProductUnderwriting, SessionHelper.EDITSOLUTIONS);
			}

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	/**
	 * Associates a manual requirement to the specified ContractGroup
	 *
	 * @param contractGroupPK
	 * @param requirement
	 * @param filteredRequirement
	 * @throws EDITCaseException
	 */
	public void addRequirementToContractGroup(Long contractGroupPK, Requirement requirement,
			FilteredRequirement filteredRequirement) throws EDITCaseException {
		ContractGroup contractGroup = ContractGroup.findBy_ContractGroupPK(contractGroupPK);

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			ContractGroupRequirement contractGroupRequirement = ContractGroupRequirement
					.buildNewRequirement(contractGroup, requirement, filteredRequirement);

			contractGroup.addContractGroupRequirement(contractGroupRequirement);

			SessionHelper.saveOrUpdate(contractGroup, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	/**
	 * Removes the selected Requirement from the specified contractGroup
	 *
	 * @param contractGroupPK
	 * @param selectedRequirementPK
	 * @throws EDITCaseException
	 */
	public void deleteRequirementFromContractGroup(Long contractGroupPK, Long selectedRequirementPK)
			throws EDITCaseException {
		ContractGroupRequirement contractGroupRequirement = ContractGroupRequirement
				.findBy_ContractGroupRequirementPK(selectedRequirementPK);

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			SessionHelper.delete(contractGroupRequirement, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	/**
	 * Updates the contract group requirement with any status or date changes made
	 * by the user.
	 *
	 * @param contractGroupRequirement
	 * @throws EDITCaseException
	 */
	public void saveRequirement(ContractGroupRequirement contractGroupRequirement) throws EDITCaseException {
		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			SessionHelper.saveOrUpdate(contractGroupRequirement, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	/**
	 * @param productStructurePK
	 * @param contractGroupPK
	 * @see #removeProductStructureFromContractGroup(Long, Long)
	 */
	public void removeProductStructureFromContractGroup(Long productStructurePK, Long contractGroupPK)
			throws EDITContractException {
		FilteredProduct filteredProduct = FilteredProduct.findBy_ProductStructurePK_ContractGroupPK(productStructurePK,
				contractGroupPK);

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			SessionHelper.delete(filteredProduct, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITContractException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	/**
	 * @param caseContractGroup
	 * @param clientDetailPK
	 * @see #saveCase(ContractGroup, Long, String, String, String)
	 */
	public void saveCase(ContractGroup caseContractGroup, Long clientDetailPK, String statusChangeEffectiveDate,
			String origCaseStatus, String operator) throws EDITCaseException {
		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			ContractGroup.buildCaseWithoutValidation(caseContractGroup, clientDetailPK);

			SessionHelper.saveOrUpdate(caseContractGroup, SessionHelper.EDITSOLUTIONS);

			if (statusChangeEffectiveDate != null) {
				CaseStatusChangeHistory.buildHistoryRecord(caseContractGroup, new EDITDate(statusChangeEffectiveDate),
						origCaseStatus, operator);
			}

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	/**
	 * @param groupContractGroup
	 * @param caseContractGroupPK
	 * @param clientDetailPK
	 * @see #saveGroup(ContractGroup, Long, Long)
	 */
	public void saveGroup(ContractGroup groupContractGroup, Long caseContractGroupPK, Long clientDetailPK)
			throws EDITCaseException {
		ContractGroup caseContractGroup = ContractGroup.findBy_ContractGroupPK(caseContractGroupPK);

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			ContractGroup.buildGroupWithoutValidation(groupContractGroup, caseContractGroup, clientDetailPK);

			SessionHelper.saveOrUpdate(groupContractGroup, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	/**
	 * Deletes the specified ContractGroup, updates the associated Case
	 * ContractGroup
	 *
	 * @param contractGroup
	 * @throws EDITCaseException
	 */
	public String deleteGroup(ContractGroup groupContractGroup) throws EDITCaseException {
		String message = "Group Successfully Deleted";
		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			ContractGroup caseContractGroup = groupContractGroup.getContractGroup();

			caseContractGroup.removeContractGroup(groupContractGroup);

			SessionHelper.delete(groupContractGroup, SessionHelper.EDITSOLUTIONS);

			ClientRole clientRole = groupContractGroup.getClientRole();

			SessionHelper.delete(clientRole, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}

		return message;
	}

	public void savePayrollDeductionScheduleUpdate(PayrollDeductionSchedule payrollDeductionSchedule,
			ContractGroup contractGroup, EDITDate changeEffectiveDate) throws EDITCaseException {
		try {
			if (changeEffectiveDate != null) // flag the non-financial framework to use this date instead of the System
												// date
			{
				SessionHelper.putInThreadLocal(HibernateEntityDifference.CHANGE_EFFECTIVE_DATE, changeEffectiveDate);
			}

			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			payrollDeductionSchedule.setContractGroup(contractGroup);
			PayrollDeductionSchedule.calculateAndSetPRDExtractDates(payrollDeductionSchedule,
					contractGroup.getBillScheduleFK());

			SessionHelper.saveOrUpdate(payrollDeductionSchedule, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	/**
	 * Saves an Enrollment to persistence. To save an Enrollment, all other
	 * Enrollments must have their EndingPolicyDate adjusted to be one day prior to
	 * the next Enrollment's BeginningPolicyDate.
	 *
	 * @param enrollment          new Enrollment object to be saved
	 * @param caseContractGroupPK parent case ContractGroup PK
	 * @throws EDITCaseException
	 */
	public void saveEnrollment(Enrollment enrollment, Long caseContractGroupPK) throws EDITCaseException {
		ContractGroup contractGroup = ContractGroup.findBy_ContractGroupPK(caseContractGroupPK);

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			contractGroup.addEnrollment(enrollment);

			// Get all of them
			Set<Enrollment> enrollments = contractGroup.getEnrollments();

			// Sort them by BeginningPolicyDate
			Enrollment[] arrayOfEnrollments = (Enrollment[]) enrollments.toArray(new Enrollment[enrollments.size()]);

			Enrollment[] sortedEnrollments = (Enrollment[]) Util.sortObjects(arrayOfEnrollments,
					new String[] { "getBeginningPolicyDate" });

			// Adjust the EndingPolicyDate
			for (int i = 0; i < sortedEnrollments.length; i++) {
				Enrollment sortedEnrollment = sortedEnrollments[i];

				// If it's the last Enrollment, set the EndingPolicyDate to null (i.e. it is
				// unset)
				// Else, set the EndingPolicyDate to 1 day less than the next Enrollment's
				// BeginningPolicyDate
				if (i == sortedEnrollments.length - 1) {
					sortedEnrollment.setEndingPolicyDate(null);
				} else if (sortedEnrollment.getEndingPolicyDate() == null) {
					sortedEnrollment
							.setEndingPolicyDate(sortedEnrollments[i + 1].getBeginningPolicyDate().subtractDays(1));
				}
			}

			// Save the ContractGroup so all Enrollments get saved
			SessionHelper.saveOrUpdate(contractGroup, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	public void saveProjectedBusinessByMonth(ProjectedBusinessByMonth projectedBusinessByMonth, Long enrollmentPK)
			throws EDITCaseException {
		Enrollment enrollment = Enrollment.findByPK(enrollmentPK);

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			if (projectedBusinessByMonth.getClosedDate() == null) {
				projectedBusinessByMonth.setClosedDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));
			}
			projectedBusinessByMonth.setEnrollment(enrollment);

			SessionHelper.saveOrUpdate(projectedBusinessByMonth, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	public void createCaseAgentHierarchy(ContractGroup contractGroup, AgentVO agentVO,
			PlacedAgentBranchVO[] placedAgentBranchVOs, String selectedPlacedAgentPK) throws EDITCaseException {
		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			AgentHierarchy agentHierarchy = new AgentHierarchy();
			agentHierarchy.setAgent(new Agent(agentVO));

			contractGroup.addAgentHierarchy(agentHierarchy);

			AgentHierarchyAllocation agentHierarchyAllocation = new AgentHierarchyAllocation();
			agentHierarchyAllocation.setAllocationPercent(new EDITBigDecimal("1"));
			agentHierarchyAllocation.setStartDate(contractGroup.getEffectiveDate());
			agentHierarchyAllocation.setStopDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));

			agentHierarchy.addAgentHierarchyAllocation(agentHierarchyAllocation);
			for (int p = 0; p < placedAgentBranchVOs.length; p++) {
				int paCount = placedAgentBranchVOs[p].getPlacedAgentVOCount();
				int paIndex = paCount - 1; // we are looking for the writing PlacedAgent - it's the last agent in the
											// array when sorted by HierarchyLevel Asc.

				if ((placedAgentBranchVOs[p].getPlacedAgentVO(paIndex).getPlacedAgentPK() + "")
						.equals(selectedPlacedAgentPK)) {
					PlacedAgentVO[] placedAgentVOs = placedAgentBranchVOs[p].getPlacedAgentVO();

					for (int z = 0; z < placedAgentVOs.length; z++) {
						PlacedAgent placedAgent = new PlacedAgent(placedAgentVOs[z]);

						AgentSnapshot agentSnapshot = new AgentSnapshot();
						agentSnapshot.setPlacedAgent(placedAgent);
						agentSnapshot.setHierarchyLevel(placedAgentVOs[z].getHierarchyLevel());
						agentSnapshot.setServicingAgentIndicator("N");

						agentHierarchy.addAgentSnapshot(agentSnapshot);
					}

					break;
				}
			}

			SessionHelper.saveOrUpdate(contractGroup, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	/**
	 * Deletes AgentHierarchy from the specified Case.
	 *
	 * @param contractGroup
	 * @param selectedAgentHierarchyPK
	 * @throws EDITCaseException
	 */
	public void deleteCaseAgentHierarchy(ContractGroup contractGroup, String selectedAgentHierarchyPK)
			throws EDITCaseException {
		AgentHierarchy agentHierarchy = AgentHierarchy.findByPK(new Long(selectedAgentHierarchyPK));

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			contractGroup.removeAgentHierarchy(agentHierarchy);

			agentHierarchy.deleteAgentSnapshots();
			agentHierarchy.deleteAgentHierarchyAllocations();
			agentHierarchy.hDelete();

			SessionHelper.saveOrUpdate(contractGroup, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	/**
	 * Saves update(s) to AgentHierarchy.
	 *
	 * @param agentHierarchy
	 * @throws EDITCaseException
	 */
	public void saveCaseAgentHierarchyUpdate(AgentHierarchy agentHierarchy) throws EDITCaseException {
		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			SessionHelper.saveOrUpdate(agentHierarchy, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	/**
	 * Saves update(s) - commission overrides - to AgentSnapshot.
	 *
	 * @param agentSnapshot
	 * @throws EDITCaseException
	 */
	public void saveCaseAgentSnapshotUpdate(AgentSnapshot agentSnapshot) throws EDITCaseException {
		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			if (agentSnapshot.getCommissionProfileFK() != null) {
				CommissionProfile commissionProfile = CommissionProfile
						.findBy_PK(agentSnapshot.getCommissionProfileFK());
				agentSnapshot.setCommissionProfile(commissionProfile);
			}

			SessionHelper.saveOrUpdate(agentSnapshot, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	public void saveDepartmentLocation(DepartmentLocation departmentLocation, Long activeGroupPK)
			throws EDITCaseException {
		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			if (departmentLocation.getContractGroupFK() != null) {
				SessionHelper.saveOrUpdate(departmentLocation, SessionHelper.EDITSOLUTIONS);
			} else {
				ContractGroup contractGroup = ContractGroup.findBy_ContractGroupPK(activeGroupPK);

				contractGroup.addDepartmentLocation(departmentLocation);

				SessionHelper.saveOrUpdate(contractGroup, SessionHelper.EDITSOLUTIONS);
			}

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	/**
	 * Saves the MasterContractInformation found on the specified FilteredProduct.
	 *
	 * @param filteredProductPK
	 * @param masterContractNumber
	 * @param effectiveDate
	 * @param terminationDate
	 * @throws EDITCaseException
	 */
	public FilteredProduct saveFilteredProductInformation(Long filteredProductPK, int monthWindow)
			throws EDITCaseException {
		FilteredProduct filteredProduct = FilteredProduct.findByPK(filteredProductPK);

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			SessionHelper.saveOrUpdate(filteredProduct, SessionHelper.EDITSOLUTIONS);
			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();

			return filteredProduct;
		}
	}

	public MasterContract saveMasterContractInformationNew(Long filteredProductPK, Long contractGroupPK,
			String masterContractNumber, String masterContractName, EDITDate masterContractEffectiveDate,
			EDITDate masterContractTerminationDate, String stateCT, boolean noInterimCoverage, String creationOperator)
			throws EDITCaseException {

		SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
		MasterContract masterContract = (MasterContract) SessionHelper.newInstance(MasterContract.class,
				SessionHelper.EDITSOLUTIONS);
		FilteredProduct filteredProduct = FilteredProduct.findByPK(filteredProductPK);

		try {

			if (masterContractEffectiveDate != null) {
				masterContract.setMasterContractEffectiveDate(masterContractEffectiveDate);
			}
			if (masterContractTerminationDate != null) {
				masterContract.setMasterContractTerminationDate(masterContractTerminationDate);
			} else {
				masterContract.setMasterContractTerminationDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));
			}

			masterContract.setFilteredProduct(filteredProduct);
			masterContract.setMasterContractNumber(masterContractNumber);
			masterContract.setMasterContractName(masterContractName);
			masterContract.setStateCT(stateCT);
			masterContract.setCreationOperator(creationOperator);
			masterContract.setCreationDate(new EDITDate());
			masterContract.setNoInterimCoverage(noInterimCoverage);
			SessionHelper.saveOrUpdate(masterContract, SessionHelper.EDITSOLUTIONS);
			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();

			return masterContract;
		}
	}
	// ==================================== SERVICES
	// =============================================================

	/**
	 * @param requestDocument
	 * @return
	 * @see Group#getQuote(org.dom4j.Document)
	 */
	public Document getQuote(Document requestDocument) {
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Long batchContractSetupPK = this.getBatchContractSetupPKFromRequest(requestParametersElement);

		BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(batchContractSetupPK);

		// Load data into the segment
		Segment segment;

		try {
			// SegmentVOElement for SegmentDocument
			segment = buildSegment(batchContractSetup, requestParametersElement);

			Element segmentVOElement = segment.getAsElement();

			Element lifeVOElement = segment.getLife().getAsElement();

			segmentVOElement.add(lifeVOElement);

			// ClientVO Elements for ClientDocument (for each ContractClient built)
			List<Element> clientVOElements = new ArrayList<Element>();

			Set<ContractClient> contractClients = segment.getContractClients();

			for (ContractClient contractClient : contractClients) {
				// Get the elements
				Element clientVOElement = new DefaultElement("ClientVO");

				Element contractClientVOElement = contractClient.getAsElement();

				Element clientDetailVOElement = contractClient.getClientRole().getClientDetail().getAsElement();

				ClientAddress clientAddress = contractClient.getClientRole().getClientDetail().getPrimaryAddress();

				Element clientAddressVOElement = null;

				if (clientAddress != null) {
					clientAddressVOElement = clientAddress.getAsElement();
				}

				Element roleTypeCTElement = new DefaultElement("RoleTypeCT");

				roleTypeCTElement.setText(contractClient.getClientRole().getRoleTypeCT());

				// Attach the elements
				if (clientAddressVOElement != null) {
					clientDetailVOElement.add(clientAddressVOElement);
				}

				clientVOElement.add(clientDetailVOElement);

				clientVOElement.add(contractClientVOElement);

				clientVOElement.add(roleTypeCTElement);

				clientVOElements.add(clientVOElement);
			}

			// SegmentElementVOs for RiderDocument
			Set riders = segment.getSegments();

			List<Element> riderVOElements = new ArrayList<Element>();

			for (Object riderSegment : riders) {
				Element riderSegmentVOElement = ((Segment) riderSegment).getAsElement();

				riderVOElements.add(riderSegmentVOElement);
			}

			// Now get the quote
			Calculator calcuator = new CalculatorComponent();

			// Calculate the quote
			SPOutput spOutput = calcuator.processScript(SegmentDocument.ROOT_ELEMENT_NAME, segmentVOElement,
					clientVOElements, riderVOElements, batchContractSetup.getContractGroupFK(), "NBQuote", // Process
					"*", // Event
					"*", // EventType
					new EDITDate(), // Today's date - always
					segment.getProductStructureFK(), false);

			List<ValidationVO> validationVOs = spOutput.getValidationVOs();

			for (ValidationVO validationVO : validationVOs) {
				responseDocument.addResponseMessage(validationVO);
			}

			List<Element> calculationOutputs = spOutput.getCalculationOutputs();

			String annualPremium = "";

			String deductionAmount = "";

			for (Element calculationOutput : calculationOutputs) {
				String elementName = calculationOutput.getName();

				if (elementName.equals("SegmentVO")) {
					annualPremium = calculationOutput.element("AnnualPremium").getText();
				} else if (elementName.equals("PremiumDueVO")) {
					deductionAmount = calculationOutput.element("DeductionAmount").getText();
				}
			}

			Element annualPremiumElement = new DefaultElement("AnnualPremium");

			annualPremiumElement.setText(annualPremium);

			Element deductionAmountElement = new DefaultElement("DeductionAmount");

			deductionAmountElement.setText(deductionAmount);

			responseDocument.addToRootElement(annualPremiumElement);

			responseDocument.addToRootElement(deductionAmountElement);

			if (!spOutput.hasHardEdits()) {
				if (!spOutput.hasWarningEdits()) {
					responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
							"Quote successfully calculated.");
				}
			}
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR,
					Util.initString(e.getMessage(), "N/A"));

			responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, "Quote failed to calculate.");
		}

		return responseDocument.getDocument();
	}

	/**
	 * @see Group#addSegmentToContractGroup(org.dom4j.Document)
	 */
	public Document addSegmentToContractGroup(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Long batchContractSetupPK = this.getBatchContractSetupPKFromRequest(requestParametersElement);

		Segment segment = null;

		boolean inputOK = false;

		String clientNames = "";
		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(batchContractSetupPK);
			ContractGroup contractGroup = batchContractSetup.getContractGroup();

			// Load data into the segment
			segment = buildSegment(batchContractSetup, requestParametersElement);

			// build up a collection of client names for informational purposes
			HashSet<String> names = new HashSet<String>();
			for (ContractClient client : segment.getContractClients()) {
				if (client.getClientDetail() != null) {
					names.add(("[" + client.getClientDetail().getLastName() + ", "
							+ client.getClientDetail().getFirstName() + "]").toUpperCase());
				}
			}
			clientNames = Joiner.on(", ").join(names);

			// Set the billSchedule to be the same as the ContractGroup's
			segment.setBillSchedule(contractGroup.getBillSchedule());

			// Attach the segment to the ContractGroup
			contractGroup.addSegment(segment);

			// Attach Segment to MasterContract
			MasterContract masterContract = MasterContract.findbySegmentDetails(segment);
			if (masterContract != null) {
				masterContract.addSegment(segment);
			}

			inputOK = validateSegmentInput(batchContractSetup, segment, contractGroup, requestParametersElement,
					responseDocument);

			if (inputOK) {
				// Wait until the last possible moment to set the contractNumber. This reduces
				// timing problems when
				// reading and changing the Company's policyNumberSequenceNumber (which is used
				// to auto generate
				// the contractNumber).
				setContractNumber(segment, requestParametersElement);

				// SAVE
				SessionHelper.saveOrUpdate(contractGroup, SessionHelper.EDITSOLUTIONS);

				SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

				responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Contract "
						+ segment.getContractNumber() + " added to ContractGroup with clients: " + clientNames);
			} else {
				// Send back errors
				responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR,
						"Unable to save Segment for clients " + clientNames + ". Invalid input. No changes committed.");
			}
		} catch (ConstraintViolationException cve) {
//           cve.printStackTrace();
		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {

				System.out.println("Duplicate Contract Failure");
				SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
				inputOK = false;
			} else {

				System.out.println(e);
				e.printStackTrace();
				responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());
				responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR,
						"Unable to save Segment for clients " + clientNames + ". Invalid input. No changes committed.");

				SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

				inputOK = false;
			}
		} finally {
			SessionHelper.clearSessions();
		}

		// Build the submit transaction and run it real-time
		// Note: this is purposely done outside of the above Hibernate session. The
		// contract had to be added without
		// errors before getting here. The submit transaction uses CRUD.

		if (inputOK) {
			try {
				EDITTrx editTrx = buildRunAndSaveSubmitTransaction(segment, responseDocument);
				/*
				 * // Carrie will update premiumDue record with EDITTrxPK in the scripts. if
				 * ((segment.getPremiumDues() != null) && (segment.getPremiumDues().size() > 0))
				 * { try { PremiumDue premiumDue =
				 * (PremiumDue)segment.getPremiumDues().toArray()[0];
				 * premiumDue.setEDITTrxFK(editTrx.getEDITTrxPK());
				 * SessionHelper.saveOrUpdate(premiumDue, SessionHelper.EDITSOLUTIONS);
				 * SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS); } catch
				 * (Exception e) {
				 * SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS); } }
				 */

			} catch (Exception e) {
				responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR,
						"Error saving Submit transaction");
				responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());
			}

			segment = Segment.findByContractNumber(segment.getContractNumber());

			// Generate response
			responseDocument.addToRootElement(segment.getAsElement(true, true));
		}

		return responseDocument.getDocument();
	}

	/**
	 * Removes a Segment from a ContractGroup. The Segment becomes an "Individual"
	 * Segment. It retains the key to the ContractGroup for historical purposes but
	 * it is no longer a child of the group.
	 *
	 * @param requestDocument SEGRequestVO containing the following structure:
	 *
	 *                        <SEGRequestVO> <RequestParameters>
	 *                        <ContractGroupPK>2</ContractGroupPK>
	 *                        <SegmentPK>2</SegmentPK> </RequestParameters>
	 *                        </SEGRequestVO>
	 */
	public void removeSegmentFromContractGroup(Document requestDocument) throws EDITCaseException {
//        //  Get information from request document
//        Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");
//
//        Element contractGroupPKElement = requestParametersElement.element("ContractGroupPK");
//        Element segmentPKElement = requestParametersElement.element("SegmentPK");
//
//        Long contractGroupPK = new Long(contractGroupPKElement.getText());
//        Long segmentPK = new Long(segmentPKElement.getText());
//
//        ContractGroup contractGroup = ContractGroup.findByPK(contractGroupPK);
//        Segment segment  = Segment.findByPK(segmentPK);
//
//        try
//        {
//            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
//
//            BillSchedule segmentBillSchedule = createSegmentBillSchedule(contractGroup);
//
//            segment.setBillSchedule(segmentBillSchedule);
//
//            segment.setMemberOfContractGroup(Segment.MEMBEROFCONTRACTGROUP_NO);
//
//            //  SAVE
//            SessionHelper.saveOrUpdate(segment, SessionHelper.EDITSOLUTIONS);
//
//            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//
//            e.printStackTrace();
//
//            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
//
//            throw new EDITCaseException(e.toString());
//        }
//        finally
//        {
//            SessionHelper.clearSessions();
//        }
	}

	/**
	 * @see Group#createDefaultBatchContractSetup(org.dom4j.Document)
	 */
	public Document createDefaultBatchContractSetup(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();
		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"BatchContractSetup successfully created and saved");

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Element groupContractGroupPKElement = requestParametersElement.element("GroupContractGroupPK");
		Element operatorElement = requestParametersElement.element("Operator");

		Long groupContractGroupPK = new Long(groupContractGroupPKElement.getText());
		String operator = operatorElement.getText();

		ContractGroup groupContractGroup = ContractGroup.findByPK(groupContractGroupPK);

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			// Build the default BatchContractSetup
			BatchContractSetup batchContractSetup = BatchContractSetup.buildDefault(groupContractGroup);

			// Set BatchContractSetup with request information
			batchContractSetup.setCreationOperator(operator);

			// SAVE
			SessionHelper.saveOrUpdate(batchContractSetup, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

			SessionHelper.clearSessions();

			// After 12/8 delivery it was erroring on commiting BatchContractSetup because
			// the BatchContractSetup does
			// not have ContractGroupFK ... not sure why it is happening now but it was not
			// having FK value.
			batchContractSetup = BatchContractSetup.findByPK(batchContractSetup.getBatchContractSetupPK());

			// Build response document
			Element batchContractSetupElement = this.getBatchContractSetupResponseElement(batchContractSetup);

			responseDocument.addToRootElement(batchContractSetupElement);
		} catch (Exception e) {
			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
		} finally {
			SessionHelper.clearSessions();
		}

		return responseDocument.getDocument();
	}

	/**
	 * @see Group#updateBatchContractSetup(org.dom4j.Document)
	 */
	public Document updateBatchContractSetup(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get input information
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Element batchContractSetupElement = requestParametersElement.element("BatchContractSetupVO");

		List batchProductLogElements = requestParametersElement.elements("BatchProductLogVO");
		List batchProgressLogElements = requestParametersElement.elements("BatchProgressLogVO");
		List agentHierarchyPKElements = requestParametersElement.elements("AgentHierarchyPK");

		// Get the BatchContractSetup and its children and save to database
		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			BatchContractSetup batchContractSetup = (BatchContractSetup) SessionHelper.mapToHibernateEntity(
					BatchContractSetup.class, batchContractSetupElement, SessionHelper.EDITSOLUTIONS, true);

			// If the batchContractSetup is already in the database, remove all of its
			// children. The children will
			// be replaced with the requestDocument information. At this time, it was more
			// efficient to remove all
			// the children than to get them and do a field-to-field comparison to see if
			// they are different. If
			// the number of rows of children objects gets very long, it may be more
			// efficient to go the other way.
			if (SessionHelper.isPersisted(batchContractSetup)) {
				batchContractSetup.removeAllSelectedAgentHierarchies();
			}

			// If the batchID has not been set yet (it hasn't on a newly created one),
			// generate it. Don't want to
			// generate it again if this is more than the first time this batchContractSetup
			// has been updated.
			if (batchContractSetup.getBatchID() == null) {
				batchContractSetup.autoGenerateBatchID();
			}

			// Load the BatchProductLogs
			for (Iterator iterator = batchProductLogElements.iterator(); iterator.hasNext();) {
				Element batchProductLogElement = (Element) iterator.next();

				BatchProductLog batchProductLog = (BatchProductLog) SessionHelper.mapToHibernateEntity(
						BatchProductLog.class, batchProductLogElement, SessionHelper.EDITSOLUTIONS, true);

				if (!SessionHelper.isPersisted(batchProductLog)) {
					batchContractSetup.addBatchProductLog(batchProductLog);
				}
			}

			// Load the BatchProgressLogs
			for (Iterator iterator = batchProgressLogElements.iterator(); iterator.hasNext();) {
				Element batchProgressLogElement = (Element) iterator.next();

				Element filteredRequirementFKElement = batchProgressLogElement.element("FilteredRequirementFK");

				// The FilteredRequirementFK was "there" but Hibernate is not aware of this
				// assocociation at this point
				// in time. We remove the FK and then associate the FilteredRequirement entity
				// in a Hibernate-appropriate way later on.
				filteredRequirementFKElement.detach();

				FilteredRequirement filteredRequirement = FilteredRequirement
						.findByPK(new Long(filteredRequirementFKElement.getText()));

				BatchProgressLog batchProgressLog = (BatchProgressLog) SessionHelper.mapToHibernateEntity(
						BatchProgressLog.class, batchProgressLogElement, SessionHelper.EDITSOLUTIONS, true);

				if (!SessionHelper.isPersisted(batchProgressLog)) {
					// This [is] a bidirectional association.
					batchContractSetup.addBatchProgressLog(batchProgressLog);

					// This is [not] a bidirectional association.
					batchProgressLog.setFilteredRequirement(filteredRequirement);
				}

				// Save a new ContractGroupRequirement off of Case-ContractGroup if it doesn't
				// already exist.
				if (!batchContractSetup.getCaseContractGroup().filteredRequirementExists(filteredRequirement)) {
					ContractGroupRequirement contractGroupRequirement = ContractGroupRequirement.buildNewRequirement(
							batchContractSetup, batchContractSetup.getCaseContractGroup(),
							batchProgressLog.getFilteredRequirement());

					SessionHelper.saveOrUpdate(contractGroupRequirement, contractGroupRequirement.getDatabase());
				}
			}

			// Create the SelectedAgentHierarchy objects
			for (Iterator iterator = agentHierarchyPKElements.iterator(); iterator.hasNext();) {
				Element agentHierarchyPKElement = (Element) iterator.next();

				Long agentHierarchyPK = new Long(agentHierarchyPKElement.getText());

				AgentHierarchy agentHierarchy = AgentHierarchy.findByPK(agentHierarchyPK);

				SelectedAgentHierarchy selectedAgentHierarchy = new SelectedAgentHierarchy();

				// This is [not] a bidirectional association.
				selectedAgentHierarchy.setAgentHierarchy(agentHierarchy);

				// This [is] a bidirectional association.
				batchContractSetup.addSelectedAgentHierarchy(selectedAgentHierarchy);
			}

			boolean validationOK = validateBatchContractSetup(batchContractSetup, responseDocument);

			if (validationOK) {
				// SAVE
				SessionHelper.saveOrUpdate(batchContractSetup, SessionHelper.EDITSOLUTIONS);

				SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

				// Build responseDocument
				Element batchContractSetupResponseElement = this
						.getBatchContractSetupResponseElement(batchContractSetup);

				responseDocument.addToRootElement(batchContractSetupResponseElement);

				responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
						"BatchContractSetup and its children successfully saved");
			} else {
				responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR,
						"Error during validation.  BatchContractSetup not saved.");
			}
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR,
					Util.initString(e.getMessage(), "N/A"));

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
		} finally {
			SessionHelper.clearSessions();
		}

		return responseDocument.getDocument();
	}

	/**
	 * @param requestDocument
	 * @return
	 * @see group.Group#copyBatchContractSetup
	 */
	public Document copyBatchContractSetup(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Element batchContractSetupPKElement = requestParametersElement.element("BatchContractSetupPK");

		Element operatorElement = requestParametersElement.element("Operator");
		String operator = operatorElement.getText();

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			Long batchContractSetupPK = new Long(batchContractSetupPKElement.getText());

			BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(batchContractSetupPK);

			BatchContractSetup clonedBatchContractSetup = (BatchContractSetup) SessionHelper
					.shallowCopy(batchContractSetup, SessionHelper.EDITSOLUTIONS);

			clonedBatchContractSetup.setCreationDate(new EDITDate());
			clonedBatchContractSetup.setCreationOperator(operator);

			ContractGroup contractGroup = batchContractSetup.getContractGroup();
			FilteredProduct filteredProduct = batchContractSetup.getFilteredProduct();
			Enrollment enrollment = batchContractSetup.getEnrollment();

			clonedBatchContractSetup.setContractGroup(contractGroup);
			clonedBatchContractSetup.setFilteredProduct(filteredProduct);
			clonedBatchContractSetup.setEnrollment(enrollment);

			clonedBatchContractSetup.autoGenerateBatchID();

			SessionHelper.saveOrUpdate(clonedBatchContractSetup, SessionHelper.EDITSOLUTIONS);

			// Add necessary children
			Set<SelectedAgentHierarchy> selectedAgentHierarchies = batchContractSetup.getSelectedAgentHierarchies();

			for (SelectedAgentHierarchy selectedAgentHierarchy : selectedAgentHierarchies) {
				SelectedAgentHierarchy clonedSelectedAgentHierarchy = (SelectedAgentHierarchy) SessionHelper
						.shallowCopy(selectedAgentHierarchy, SessionHelper.EDITSOLUTIONS);

				clonedSelectedAgentHierarchy.setBatchContractSetup(clonedBatchContractSetup);

				clonedSelectedAgentHierarchy.setAgentHierarchy(selectedAgentHierarchy.getAgentHierarchy());

				SessionHelper.saveOrUpdate(selectedAgentHierarchy, SessionHelper.EDITSOLUTIONS);
			}

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

			responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
					"BatchContractSetup successfully deleted");
		} catch (Exception e) {
			e.printStackTrace();

			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
		} finally {
			SessionHelper.clearSessions();
		}

		return responseDocument.getDocument();
	}

	/**
	 * @param requestDocument
	 * @return
	 * @see group.Group#deleteBatchContractSetup
	 */
	public Document deleteBatchContractSetup(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Element batchContractSetupPKElement = requestParametersElement.element("BatchContractSetupPK");

		try {
			Long batchContractSetupPK = new Long(batchContractSetupPKElement.getText());

			BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(batchContractSetupPK);

			Segment[] segments = Segment.findBy_BatchContractSetupFK(batchContractSetupPK);

			if (segments.length > 0) {
				responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR,
						"BatchContractSetup could not be deleted because associated Segments exist");
			} else {
				SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

				// The ContractGroupRequirement hangs off of 2 parents 1) BatchContractSetup and
				// 2)ContractGroup
				// We can't simply delete ContractGroupRequirements ... we need to disassociate
				// from
				// BatchContractSetup before deleting.
				batchContractSetup.detachContractGroupRequirements();

				// DELETE
				SessionHelper.delete(batchContractSetup, SessionHelper.EDITSOLUTIONS);

				SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

				responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
						"BatchContractSetup successfully deleted");
			}
		} catch (Exception e) {
			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
		} finally {
			SessionHelper.clearSessions();
		}

		return responseDocument.getDocument();
	}

	/**
	 * @see group.business.Group#getAllBatchContractSetups(org.dom4j.Document)
	 */
	public Document getAllBatchContractSetups(Document requestDocument) {
		return this.getAllBatchContractSetups();
	}

	/**
	 * @see group.business.Group#getAllBatchContractSetups()
	 */
	public Document getAllBatchContractSetups() {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		BatchContractSetup[] batchContractSetups = BatchContractSetup.findAllWithActiveGroups();

		if (batchContractSetups != null) {
			for (int i = 0; i < batchContractSetups.length; i++) {
				BatchContractSetup batchContractSetup = batchContractSetups[i];

				Element batchContractSetupResponseElement = this
						.getBatchContractSetupResponseElement(batchContractSetup);

				responseDocument.addToRootElement(batchContractSetupResponseElement);
			}
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "All BatchContractSetups found");

		return responseDocument.getDocument();
	}

	public Document getRatedGenderFromCaseProduct(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();
		BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(
				new Long(SEGRequestDocument.getRequestParameterValue(requestDocument, "BatchContractSetupPK")));

		CaseProductUnderwriting[] ratedGenders = CaseProductUnderwriting
				.findByEnrollmentFK_FilteredProductFK_Grouping_Field_RelationshipToEmployeeCT(
						batchContractSetup.getEnrollmentFK(), batchContractSetup.getFilteredProductFK(),
						CaseProductUnderwriting.GROUPING_CASEOTHER, CaseProductUnderwriting.FIELD_RATEDGENDER, null);

		if (ratedGenders != null) {
			for (int i = 0; i < ratedGenders.length; i++) {
				String ratedGenderString = ratedGenders[i].getValue();

				CodeTableVO codeTableVO = new CodeTableDAO().findByTableNameAndCode("RATEDGENDER",
						ratedGenderString)[0];

				Element codeTableVOElement = Util.getVOAsElement(codeTableVO);

				responseDocument.addToRootElement(codeTableVOElement);
			}
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"All RatedGenderFromCaseProduct found");

		return responseDocument.getDocument();
	}

	/**
	 * @see Group#getGroupContractGroupByGroupNumber(org.dom4j.Document)
	 */
	public Document getGroupContractGroupByGroupNumber(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get info from requestDocument
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Element contractGroupNumberElement = requestParametersElement.element("ContractGroupNumber");

		String contractGroupNumber = contractGroupNumberElement.getText();

		// Find the ContractGroups and ClientDetails
		ContractGroup groupContractGroup = ContractGroup
				.findBy_ContractGroupNumber_ContractGroupTypeCT(contractGroupNumber, "Group");

		// Put group and case information into responseDocument
		if (groupContractGroup != null) {
			ContractGroup caseContractGroup = groupContractGroup.getContractGroup();

			Element groupContractGroupElement = getContractGroupElement(groupContractGroup);
			Element caseContractGroupElement = getContractGroupElement(caseContractGroup);

			groupContractGroupElement.add(caseContractGroupElement);

			responseDocument.addToRootElement(groupContractGroupElement);
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"Group found by group number: " + contractGroupNumber);

		return responseDocument.getDocument();
	}

	/**
	 * @see Group#getGroupContractGroupsByGroupName(org.dom4j.Document)
	 */
	public Document getGroupContractGroupsByGroupName(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get info from requestDocument
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Element groupNameElement = requestParametersElement.element("GroupName");

		String groupName = groupNameElement.getText();

		// Find the ContractGroups and ClientDetails
		ContractGroup[] groupContractGroups = ContractGroup.findBy_GroupName_ContractGroupTypeCT(groupName, "Group");

		if (groupContractGroups != null) {
			for (int i = 0; i < groupContractGroups.length; i++) {
				ContractGroup groupContractGroup = groupContractGroups[i];

				ContractGroup caseContractGroup = groupContractGroup.getContractGroup();

				Element groupContractGroupElement = getContractGroupElement(groupContractGroup);
				Element caseContractGroupElement = getContractGroupElement(caseContractGroup);

				groupContractGroupElement.add(caseContractGroupElement);

				responseDocument.addToRootElement(groupContractGroupElement);
			}
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"Group found by group name: " + groupName);

		return responseDocument.getDocument();
	}

	/**
	 * @see Group#getCandidateWritingAgents(org.dom4j.Document)
	 */
	public Document getCandidateWritingAgents(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		BatchContractSetup batchContractSetup = this.getBatchContractSetupFromRequest(requestParametersElement);

		Enrollment enrollment = Enrollment
				.findByPK(new Long(SEGRequestDocument.getRequestParameterValue(requestDocument, "EnrollmentPK")));

		// Find the candidate AgentHierarchies and add to the rootElement
		Set<AgentHierarchy> candidateAgentHierarchies = batchContractSetup
				.getCandidateAgentHierarchies(enrollment.getBeginningPolicyDate());

		if (candidateAgentHierarchies != null) {
			for (Iterator iterator = candidateAgentHierarchies.iterator(); iterator.hasNext();) {
				AgentHierarchy agentHierarchy = (AgentHierarchy) iterator.next();

				Element agentHierarchyElement = getWritingAgentSnapshotElement(agentHierarchy);

				responseDocument.addToRootElement(agentHierarchyElement);
			}
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"Candidate writing agents found for BatchContractSetup");

		return responseDocument.getDocument();
	}

	/**
	 * @see Group#getSelectedWritingAgents(org.dom4j.Document)
	 */
	public Document getSelectedWritingAgents(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Element batchContractSetupPKElement = requestParametersElement.element("BatchContractSetupPK");

		BatchContractSetup batchContractSetup = this.getBatchContractSetupFromRequest(requestParametersElement);

		// Find the candidate AgentHierarchies and add to the rootElement
		Set<SelectedAgentHierarchy> selectedAgentHierarchies = batchContractSetup.getSelectedAgentHierarchies();

		if (selectedAgentHierarchies != null) {
			for (Iterator iterator = selectedAgentHierarchies.iterator(); iterator.hasNext();) {
				SelectedAgentHierarchy selectedAgentHierarchy = (SelectedAgentHierarchy) iterator.next();

				AgentHierarchy agentHierarchy = AgentHierarchy.findByPK(selectedAgentHierarchy.getAgentHierarchyFK());

				Element agentHierarchyElement = getWritingAgentSnapshotElement(agentHierarchy);

				responseDocument.addToRootElement(agentHierarchyElement);
			}
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"Selected writing agents found for BatchContractSetup");

		return responseDocument.getDocument();
	}

	/**
	 * @see Group#getClientsByTaxID(org.dom4j.Document)
	 */
	public Document getClientsByTaxID(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Element taxIDElement = requestParametersElement.element("TaxID");

		String taxID = taxIDElement.getText();

		ClientDetail[] clientDetails = ClientDetail.findBy_TaxIdentification(taxID);

		// Put information into response
		if (clientDetails != null) {
			for (int i = 0; i < clientDetails.length; i++) {
				ClientDetail clientDetail = clientDetails[i];

				Element clientDetailElement = getClientDetailElement(clientDetail);

				responseDocument.addToRootElement(clientDetailElement);
			}
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"Clients found for tax id: " + taxID);

		return responseDocument.getDocument();
	}

	/**
	 * @see Group#getClientsByNameAndDateOfBirth(org.dom4j.Document)
	 */
	public Document getClientsByNameAndDateOfBirth(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Element nameElement = requestParametersElement.element("Name");
		Element dateOfBirthElement = requestParametersElement.element("DateOfBirth");

		String name = nameElement.getText();
		EDITDate dateOfBirth = DateTimeUtil.getEDITDateFromMMDDYYYY(dateOfBirthElement.getText());

		ClientDetail[] clientDetails = ClientDetail.findBy_PartialName_BirthDate(name, dateOfBirth);

		// Put information into response
		if (clientDetails != null) {
			for (int i = 0; i < clientDetails.length; i++) {
				ClientDetail clientDetail = clientDetails[i];

				Element clientDetailElement = getClientDetailElement(clientDetail);

				responseDocument.addToRootElement(clientDetailElement);
			}
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"Clients found for name: " + name + ", birth date: " + dateOfBirth.getFormattedDate());

		return responseDocument.getDocument();
	}

	/**
	 * @see Group#getClientsByName(org.dom4j.Document)
	 */
	public Document getClientsByName(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Element nameElement = requestParametersElement.element("Name");

		String name = nameElement.getText();

		ClientDetail[] clientDetails = ClientDetail.findBy_PartialName(name);

		// Put information into response
		if (clientDetails != null) {
			for (int i = 0; i < clientDetails.length; i++) {
				ClientDetail clientDetail = clientDetails[i];

				Element clientDetailElement = getClientDetailElement(clientDetail);

				responseDocument.addToRootElement(clientDetailElement);
			}
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"Clients found for name: " + name);

		return responseDocument.getDocument();
	}

	/**
	 * @see group.business.Group#getActiveDepartmentLocations(org.dom4j.Document)
	 */
	public Document getActiveDepartmentLocations(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Long batchContractSetupPK = this.getBatchContractSetupPKFromRequest(requestParametersElement);

		DepartmentLocation[] departmentLocations = DepartmentLocation
				.findActiveByBatchContractSetupPK(batchContractSetupPK);

		// Put information into response
		if (departmentLocations != null) {
			for (int i = 0; i < departmentLocations.length; i++) {
				DepartmentLocation departmentLocation = departmentLocations[i];

				Element departmentLocationElement = departmentLocation.getAsElement(true, true);

				responseDocument.addToRootElement(departmentLocationElement);
			}
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"Active DepartmentLocations found for BatchContractSetup");

		return responseDocument.getDocument();
	}

	/**
	 * @see Group#getCandidateFilteredProducts(org.dom4j.Document)
	 */
	public Document getCandidateFilteredProducts(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		BatchContractSetup batchContractSetup = this.getBatchContractSetupFromRequest(requestParametersElement);

		// Get the filteredProducts from the Case
		ContractGroup caseContractGroup = batchContractSetup.getCaseContractGroup();

		Set<FilteredProduct> filteredProducts = caseContractGroup.getFilteredProducts();

		if (filteredProducts != null) {
			// For each filteredProduct, put the ProductStructure and its Company into
			// response elements
			for (Iterator iterator = filteredProducts.iterator(); iterator.hasNext();) {
				FilteredProduct filteredProduct = (FilteredProduct) iterator.next();

				ProductStructure productStructure = filteredProduct.getProductStructure();

				Company company = productStructure.getCompany();

				Element filteredProductElement = filteredProduct.getAsElement(true, true);
				Element productStructureElement = productStructure.getAsElement(true, true);

				productStructureElement.add(company.getAsElement(true, true));
				filteredProductElement.add(productStructureElement);

				responseDocument.addToRootElement(filteredProductElement);
			}
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"Candidate FilteredProducts found for BatchContractSetup");

		return responseDocument.getDocument();
	}

	///////////////////////// ******************* START - BOGUS SERVICES FOR DEMO -
	///////////////////////// SHOULD BE REMOVED
	///////////////////////// ********************///////////////////////
	public Document getCandidateFunds(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Element productStructurePKElement = requestParametersElement.element("ProductStructurePK");

		Long productStructurePK = new Long(productStructurePKElement.getText());

		Fund[] funds = Fund.findBy_ProductStructure(productStructurePK);

		for (int i = 0; i < funds.length; i++) {
			Fund fund = funds[i];

			Element fundElement = fund.getAsElement(true, true);

			responseDocument.addToRootElement(fundElement);
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Candidate Funds found");

		return responseDocument.getDocument();
	}

	public Document getProductStructures() {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		ProductStructure[] productStructures = ProductStructure.find_All();

		for (int i = 0; i < productStructures.length; i++) {
			ProductStructure productStructure = productStructures[i];

			Element productStructureElement = productStructure.getAsElement(true, true);

			responseDocument.addToRootElement(productStructureElement);
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"Candidate ProductStructures found");

		return responseDocument.getDocument();
	}

	public Document getProductStructures(Document requestDocument) {
		return this.getProductStructures();
	}

	///////////// ****************************** END - BOGUS SERVICES
	///////////// **********************************************////////////////////////

	/**
	 * @see Group#getBatchProductLogs(org.dom4j.Document)
	 */
	public Document getBatchProductLogs(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Long batchContractSetupPK = this.getBatchContractSetupPKFromRequest(requestParametersElement);

		BatchProductLog[] batchProductLogs = BatchProductLog.findByBatchContractSetupPK(batchContractSetupPK);

		// Put found items into responseDocument
		if (batchProductLogs != null) {
			for (int i = 0; i < batchProductLogs.length; i++) {
				BatchProductLog batchProductLog = batchProductLogs[i];

				responseDocument.addToRootElement(batchProductLog.getAsElement(true, true));
			}
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"BatchProductLogs found for BatchContractSetup");

		return responseDocument.getDocument();
	}

	/**
	 * @see Group#getBatchProgressLogs(org.dom4j.Document)
	 */
	public Document getBatchProgressLogs(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Long batchContractSetupPK = this.getBatchContractSetupPKFromRequest(requestParametersElement);

		BatchProgressLog[] batchProgressLogs = BatchProgressLog.findByBatchContractSetupPK(batchContractSetupPK);

		// Put found items into responseDocument
		if (batchProgressLogs != null) {
			for (int i = 0; i < batchProgressLogs.length; i++) {
				BatchProgressLog batchProgressLog = batchProgressLogs[i];

				responseDocument.addToRootElement(batchProgressLog.getAsElement(true, true));
			}
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"BatchProgressLogs found for BatchContractSetup");

		return responseDocument.getDocument();
	}

	/**
	 * @see Group#saveBatchProductLog(org.dom4j.Document)
	 */
	public Document saveBatchProductLog(Document requestDocument) {
		// Initializes response
		SEGResponseDocument responseDocument = new SEGResponseDocument();
		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"BatchProductLog successfully saved");

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Element batchProductLogElement = requestParametersElement.element("BatchProductLogVO");

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			BatchProductLog batchProductLog = (BatchProductLog) SessionHelper.mapToHibernateEntity(
					BatchProductLog.class, batchProductLogElement, SessionHelper.EDITSOLUTIONS, true);

			// SAVE
			SessionHelper.saveOrUpdate(batchProductLog, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

			responseDocument.addToRootElement(batchProductLog.getAsElement(true, true));
		} catch (Exception e) {
			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
		} finally {
			SessionHelper.clearSessions();
		}

		return responseDocument.getDocument();
	}

	/**
	 * @see Group#saveBatchProgressLog(org.dom4j.Document)
	 */
	public Document saveBatchProgressLog(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();
		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"BatchProductLog successfully saved");

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Element batchProgressLogElement = requestParametersElement.element("BatchProgressLogVO");

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			BatchProgressLog batchProgressLog = (BatchProgressLog) SessionHelper.mapToHibernateEntity(
					BatchProgressLog.class, batchProgressLogElement, SessionHelper.EDITSOLUTIONS, true);

			// SAVE
			SessionHelper.saveOrUpdate(batchProgressLog, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

			responseDocument.addToRootElement(batchProgressLog.getAsElement(true, true));
		} catch (Exception e) {
			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
		} finally {
			SessionHelper.clearSessions();
		}

		return responseDocument.getDocument();
	}

	/**
	 * @see Group#addClient(org.dom4j.Document)
	 */
	public Document addClient(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Element clientDetailElement = requestParametersElement.element("ClientDetailVO");
		Element clientAddressElement = requestParametersElement.element("ClientAddressVO");
		Element taxInformationElement = requestParametersElement.element("TaxInformationVO");

		ClientDetail clientDetail = null;
		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			clientDetail = createClientDetail(clientDetailElement, taxInformationElement); // throws error if taxID or
																							// client name already exist

			ClientAddress clientAddress = createClientAddress(clientAddressElement);

			if (clientAddress != null) {
				clientDetail.addClientAddress(clientAddress);
			}

			boolean validationOK = validateClient(clientDetail, responseDocument);
			String clientIdentifier = "[" + (clientDetail == null ? "unknown" : clientDetail.getLastName()) + ", "
					+ (clientDetail == null ? "unknown" : clientDetail.getFirstName()) + "]";
			if (validationOK) {
				// SAVE
				SessionHelper.saveOrUpdate(clientDetail, SessionHelper.EDITSOLUTIONS);

				SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

				responseDocument.addToRootElement(getClientDetailElement(clientDetail));

				responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
						"Client " + clientIdentifier + " successfully validated");
			} else {
				responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR,
						"Error during validation." + "  Client " + clientIdentifier + " not saved.");
			}
		} catch (Exception e) {
			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
		} finally {
			SessionHelper.clearSessions();
		}

		return responseDocument.getDocument();
	}

	/**
	 * @see Group#getCandidateRiders(org.dom4j.Document)
	 */
	public Document getCandidateRiders(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		BatchContractSetup batchContractSetup = this.getBatchContractSetupFromRequest(requestParametersElement);

		for (Element elt : getCandidateRiderElements(batchContractSetup)) {
			responseDocument.addToRootElement(elt);
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"Candidate Riders found for BatchContractSetup");

		return responseDocument.getDocument();
	}

	/**
	 * Obtains a list of rider elements that should be associated with the supplied
	 * batch based on the case underwriting.
	 *
	 * @param batchContractSetup The batch setup to guide rider creation
	 * @return The set of rider elements that are required
	 */
	public List<Element> getCandidateRiderElements(BatchContractSetup batchContractSetup) {
		List<Element> elements = new ArrayList<Element>();
		Enrollment enrollment = batchContractSetup.getEnrollment();

		FilteredProduct filteredProduct = batchContractSetup.getFilteredProduct();

		CaseProductUnderwriting[] caseProductUnderwritings = CaseProductUnderwriting
				.findByEnrollment_Grouping_Field_Value(enrollment, CaseProductUnderwriting.GROUPING_CASERIDERS,
						CaseProductUnderwriting.FIELD_ALLOWRIDERS, CaseProductUnderwriting.VALUE_YES, filteredProduct);

		for (int i = 0; i < caseProductUnderwritings.length; i++) {
			CaseProductUnderwriting caseProductUnderwriting = caseProductUnderwritings[i];

			Element candidateRiderElement = buildCandidateRiderElement(caseProductUnderwriting);
			elements.add(candidateRiderElement);
		}
		return elements;
	}

	/**
	 * @see Group#getCandidateEnrollments(org.dom4j.Document)
	 */
	public Document getCandidateEnrollments(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		BatchContractSetup batchContractSetup = getBatchContractSetupFromRequest(requestParametersElement);

		ContractGroup caseContractGroup = batchContractSetup.getCaseContractGroup();

		Set<Enrollment> enrollments = caseContractGroup.getEnrollments();

		for (Iterator iterator = enrollments.iterator(); iterator.hasNext();) {
			Enrollment enrollment = (Enrollment) iterator.next();

			Element enrollmentElement = enrollment.getAsElement(true, true);

			responseDocument.addToRootElement(enrollmentElement);
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"Candidate Enrollments found for BatchContractSetup");

		return responseDocument.getDocument();
	}

	/**
	 * @param requestDocument
	 * @return
	 * @see #getPolicyInformationByClientDetailPK(Document)
	 */
	public Document getPolicyInformationByClientDetailPK(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Element clientDetailPKElement = requestParametersElement.element("ClientDetailPK");

		String clientDetailPK = clientDetailPKElement.getText();

		Segment[] segments = Segment.findBy_ClientDetailPK_V1(new Long(clientDetailPK));

		// Put information into response document
		for (Segment segment : segments) {
			ProductStructure productStucture = ProductStructure.findByPK(segment.getProductStructureFK());

			for (ContractClient contractClient : segment.getContractClients()) {
				ClientRole clientRole = contractClient.getClientRole();

				// Get the Elements
				Element segmentElement = segment.getAsElement();

				Element productStructureElement = productStucture.getAsElement();

				Element contractClientElement = contractClient.getAsElement();

				Element clientRoleElement = clientRole.getAsElement();

				// Associate the Elements
				segmentElement.add(productStructureElement);

				segmentElement.add(contractClientElement);

				contractClientElement.add(clientRoleElement);

				responseDocument.addToRootElement(segmentElement);
			}
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"Policy information found for Client");

		return responseDocument.getDocument();
	}

	/**
	 * @see Group#getFilteredQuestionnaires(org.dom4j.Document)
	 */
	public Document getFilteredQuestionnaires(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Element filteredProductPKPKElement = requestParametersElement.element("FilteredProductPK");
		Element stateCTElement = requestParametersElement.element("StateCT");

		Long filteredProductPK = new Long(filteredProductPKPKElement.getText());
		String stateCT = stateCTElement.getText();

		FilteredProduct filteredProduct = FilteredProduct.findByPK(filteredProductPK);

		Long productStructurePK = filteredProduct.getProductStructureFK();

		FilteredQuestionnaire[] filteredQuestionnaires = FilteredQuestionnaire
				.findByProductStructure_AreaCT_SortedByDisplayOrder(productStructurePK, stateCT);

		// Put found items into responseDocument
		if (filteredQuestionnaires != null) {
			for (int i = 0; i < filteredQuestionnaires.length; i++) {
				FilteredQuestionnaire filteredQuestionnaire = filteredQuestionnaires[i];

				Questionnaire questionnaire = filteredQuestionnaire.getQuestionnaire();

				Element filteredQuestionnaireElement = filteredQuestionnaire.getAsElement(true, true);

				Element questionnaireElement = questionnaire.getAsElement(true, true);

				filteredQuestionnaireElement.add(questionnaireElement);

				responseDocument.addToRootElement(filteredQuestionnaireElement);
			}
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"FilteredQuestionnaires found for FilteredProduct and StateCT: " + stateCT);

		return responseDocument.getDocument();
	}

	/**
	 * @param requestDocument
	 * @return
	 * @see Group#getPayrollDeductionCalendars(Document)
	 */
	public Document getPayrollDeductionCalendars(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		try {
			// Get information from request document
			Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

			Element payrollDeductionSchedulePKElement = requestParametersElement.element("PayrollDeductionSchedulePK");

			Element payrollDeductionYearElement = requestParametersElement.element("PayrollDeductionYear");

			Long payrollDeductionSchedulePK = new Long(payrollDeductionSchedulePKElement.getText());

			int year = new Integer(payrollDeductionYearElement.getText()).intValue();

			PayrollDeductionCalendar[] payrollDeductionCalendars = PayrollDeductionCalendar
					.findBy_PayrollDeductionSchedulePK_Year(payrollDeductionSchedulePK, year);

			for (PayrollDeductionCalendar payrollDeductionCalendar : payrollDeductionCalendars) {
				Element payrollDeductionCalendarElement = payrollDeductionCalendar.getAsElement(true, true);

				responseDocument.addToRootElement(payrollDeductionCalendarElement);
			}

			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
					"Successfully processed [getPayrollDeductionCalendars()]");

		} catch (Exception e) {
			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			System.out.println(e);

			e.printStackTrace();

			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR,
					"Unexepected error while processing [getPayrollDeductionCalendars()][" + e.getMessage() + "]");
		}

		return responseDocument.getDocument();
	}

	/**
	 * @param requestDocument
	 * @return
	 * @see Group#updatePayrollDeductionCalendar(Document)
	 */
	public Document updatePayrollDeductionCalendar(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		try {
			// Get information from request document
			Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

			// PayrollDeductionCalendarPK
			Element payrollDeductionCalendarPKElement = requestParametersElement.element("PayrollDeductionCalendarPK");

			Long payrollDeductionCalendarPK = new Long(payrollDeductionCalendarPKElement.getText());

			// PayrollDeductionCodeCT
			Element payrollDeductionCodeCTElement = requestParametersElement.element("PayrollDeductionCodeCT");

			String payrollDeductionCodeCT = payrollDeductionCodeCTElement.getText();

			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			PayrollDeductionCalendar payrollDeductionCalendar = PayrollDeductionCalendar
					.findBy_PK(payrollDeductionCalendarPK);

			payrollDeductionCalendar.setPayrollDeductionCodeCT(payrollDeductionCodeCT);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
					"Successfully updated PayrollDeductionCalendar");
		} catch (Exception e) {
			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			System.out.println(e);

			e.printStackTrace();

			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR,
					"Unexepected error while udpating PayrollDeductionCalendar [" + e.getMessage() + "]");
		}

		return responseDocument.getDocument();
	}

	/**
	 * @param requestDocument
	 * @return
	 * @see Group#createPayrollDeductionCalendar(Document)
	 */
	public Document createPayrollDeductionCalendar(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		try {
			// Get information from request document
			Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

			// PayrollDeductionSchedulePK
			Element payrollDeductionSchedulePKElement = requestParametersElement.element("PayrollDeductionSchedulePK");

			Long payrollDeductionSchedulePK = new Long(payrollDeductionSchedulePKElement.getText());

			// PayrollDeductionCodeCT
			Element payrollDeductionCodeCTElement = requestParametersElement.element("PayrollDeductionCodeCT");

			String payrollDeductionCodeCT = payrollDeductionCodeCTElement.getText();

			// PayrollDeductionDate
			Element payrollDeductionDateElement = requestParametersElement.element("PayrollDeductionDate");

			EDITDate payrollDeductionDate = DateTimeUtil.getEDITDateFromMMDDYYYY(payrollDeductionDateElement.getText());

			// Save it.
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			PayrollDeductionSchedule payrollDeductionSchedule = PayrollDeductionSchedule
					.findBy_PK(payrollDeductionSchedulePK);

			PayrollDeductionCalendar payrollDeductionCalendar = new PayrollDeductionCalendar();

			payrollDeductionCalendar.setPayrollDeductionCodeCT(payrollDeductionCodeCT);

			payrollDeductionCalendar.setPayrollDeductionDate(payrollDeductionDate);

			// NOTE: I am purposely avoiding any adder() method to avoid the inherent
			// performance penalty of the adders().
			payrollDeductionCalendar.setPayrollDeductionSchedule(payrollDeductionSchedule);

			payrollDeductionCalendar.setPayrollDeductionCodeCT(payrollDeductionCodeCT);

			payrollDeductionCalendar.hSave();

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
					"Successfully created a new PayrollDeductionCalendar");
		} catch (Exception e) {
			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			System.out.println(e);

			e.printStackTrace();

			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR,
					"Unexepected error while creating the PayrollDeductionCalendar [" + e.getMessage() + "]");
		}

		return responseDocument.getDocument();
	}

	/**
	 * @param requestDocument
	 * @return
	 * @see Group#deletePayrollDeductionCalendar(Document)
	 */
	public Document deletePayrollDeductionCalendar(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		try {
			// Get information from request document
			Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

			Element payrollDeductionCalendarPKElement = requestParametersElement.element("PayrollDeductionCalendarPK");

			Long payrollDeductionCalendarPK = new Long(payrollDeductionCalendarPKElement.getText());

			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			PayrollDeductionCalendar payrollDeductionCalendar = PayrollDeductionCalendar
					.findBy_PK(payrollDeductionCalendarPK);

			payrollDeductionCalendar.delete();

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
					"Successfully deleted PayrollDeductionCalendar");
		} catch (Exception e) {
			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			System.out.println(e);

			e.printStackTrace();

			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR,
					"Unexepected error while deleting PayrollDeductionCalendar [" + e.getMessage() + "]");
		}

		return responseDocument.getDocument();
	}

	/**
	 * @param requestDocument
	 * @return
	 * @see group#importNewBusiness(Document)
	 */
	public Document importNewBusiness(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get information from request document
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		Long batchContractSetupPK = getBatchContractSetupPKFromRequest(requestParametersElement);

		Element importFileNameElement = requestParametersElement.element("ImportFileName");

		Element operatorElement = requestParametersElement.element("Operator");

		String importFileName = importFileNameElement.getText();

		String importPath = ServicesConfig.getUnitValueImport().getDirectory();

		String absoluteImportFileName = importPath + UtilFile.DIRECTORY_DELIMITER + importFileName;

		try {
			Document importDocument = XMLUtil.readDocumentFromFile(absoluteImportFileName);

			ImportNewBusinessBatch importNewBusinessBatch = new ImportNewBusinessBatch(importDocument,
					batchContractSetupPK, operatorElement.getText());

			List<SEGResponseDocument> importResponseDocuments = importNewBusinessBatch.execute();

			boolean hasError = false;
			boolean hasWarning = false;
			for (SEGResponseDocument responseDoc : importResponseDocuments) {
				hasError = hasError || responseDoc.hasError();
				// this ridiculous thing is how we need to check for warnings -- "failures" are
				// both errors and warnings
				hasWarning = hasWarning || (!responseDoc.hasError() && responseDoc.hasFailure());
			}
			if (hasError) {
				responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR,
						"Import completed, but with errors");
			} else if (hasWarning) {
				responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_WARNING,
						"Import completed with warnings");
			} else {
				responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
						"Import successfully executed");
			}

			loadImportResponseDocumentsToResponseDocument(responseDocument, importResponseDocuments);
		} catch (FileNotFoundException fileNotFoundException) {
			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR,
					fileNotFoundException.getMessage());

			fileNotFoundException.printStackTrace();
		} catch (IOException ioException) {
			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, ioException.getMessage());

			ioException.printStackTrace();
		} catch (DocumentException documentException) {
			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, documentException.getMessage());

			documentException.printStackTrace();
		} catch (Exception exception) {
			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR,
					"Error processing import information: " + exception.toString());

			exception.printStackTrace();
		}

		return responseDocument.getDocument();
	}

	/**
	 * @param document
	 * @return
	 * @see group#getCaseFilteredRequirements(Document)
	 */
	public Document getCaseFilteredRequirements(Document document) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		try {
			ProductStructure productStructure = ProductStructure.findBy_CompanyName_MPN_GPN_AN_BCN("Case", "*", "*",
					"*", "*");

			FilteredRequirement[] filteredRequirements = FilteredRequirement
					.findBy_ProductStructurePK_V1(productStructure.getProductStructurePK());

			for (FilteredRequirement filteredRequirement : filteredRequirements) {
				Element filteredRequirementElement = filteredRequirement.getAsElement(true, true);

				Requirement requirement = filteredRequirement.getRequirement();

				Element requirementElement = requirement.getAsElement(true, true);

				filteredRequirementElement.add(requirementElement);

				responseDocument.addToRootElement(filteredRequirementElement);
			}

			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
					"Successfully retrieved [Case FilteredRequirements]");

		} catch (Exception e) {
			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			System.out.println(e);

			e.printStackTrace();

			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR,
					"Unexepected error while retrieving [Case FilteredRequirements][" + e.getMessage() + "]");
		}

		return responseDocument.getDocument();
	}

	/**
	 * @param requestDocument
	 * @return
	 * @see group#getGIOOptions(Document)
	 */
	public Document getGIOOptions(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		// Get info from request
		Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

		String relationshipToEmployeeCT = requestParametersElement.element("RelationshipToEmployeeCT").getText();

		Long batchContractSetupPK = this.getBatchContractSetupPKFromRequest(requestParametersElement);

		String filteredProductPKString = requestParametersElement.element("FilteredProductPK").getText();

		FilteredProduct filteredProduct = FilteredProduct.findByPK(new Long(filteredProductPKString));

		// Get necessary objects
		BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(batchContractSetupPK);

		Enrollment enrollment = batchContractSetup.getEnrollment();

		// Get the gioOptions from the CaseProductUnderwriting
		CaseProductUnderwriting[] gioCaseProductUnderwritings = CaseProductUnderwriting
				.findByEnrollment_FilteredProduct_Grouping_Field_RelationshipToEmployeeCT(enrollment, filteredProduct,
						CaseProductUnderwriting.GROUPING_CASERIDERS, CaseProductUnderwriting.FIELD_GIOOPTDATES,
						relationshipToEmployeeCT);

		// Add value to responseDocument
		for (int i = 0; i < gioCaseProductUnderwritings.length; i++) {
			CaseProductUnderwriting gioCaseProductUnderwriting = gioCaseProductUnderwritings[i];

			Element gioOptionElement = new DefaultElement("GIOOption");
			gioOptionElement.setText(gioCaseProductUnderwriting.getValue());

			responseDocument.addToRootElement(gioOptionElement);
		}

		responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
				"Successfully determined GIO options");

		return responseDocument.getDocument();
	}

	public void saveContractGroupNote(ContractGroupNote contractGroupNote, Long contractGroupPK)
			throws EDITCaseException {
		try {
			ContractGroup contractGroup = ContractGroup.findByPK(contractGroupPK);
			contractGroupNote.setContractGroup(contractGroup);
			contractGroupNote.setMaintDateTime(new EDITDateTime());

			if (contractGroupNote.getSequence() == 0) {
				int sequence = contractGroupNote.calculateSequence(contractGroup);
				contractGroupNote.setSequence(sequence);
			}

			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			SessionHelper.saveOrUpdate(contractGroupNote, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	public void deleteContractGroupNote(Long contractGroupNotePK) throws EDITCaseException {
		ContractGroupNote contractGroupNote = ContractGroupNote.findByPK(new Long(contractGroupNotePK));

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
			contractGroupNote.hDelete();
			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	/**
	 * @see group.busi#getBatchContractSetupsByPartialContractGroupNumber(Document)
	 */
	public Document getBatchContractSetupsByPartialContractGroupNumber(Document requestDocument) {
		// Initialize response
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		String partialContractGroupNumber = SEGRequestDocument.getRequestParameterValue(requestDocument,
				"PartialContractGroupNumber");

		BatchContractSetup[] batchContractSetups = BatchContractSetup
				.findAllWithActiveGroupsBy_PartialContractGroupNumber(partialContractGroupNumber);

		if (batchContractSetups != null) {
			for (int i = 0; i < batchContractSetups.length; i++) {
				BatchContractSetup batchContractSetup = batchContractSetups[i];

				Element batchContractSetupResponseElement = this
						.getBatchContractSetupResponseElement(batchContractSetup);

				responseDocument.addToRootElement(batchContractSetupResponseElement);
			}
		}

		if (batchContractSetups.length == 0) {
			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_WARNING,
					"No BatchContractSetups by partial GroupNumber found");
		} else {
			responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS,
					"BatchContractSetups by partial GroupNumber found");
		}

		return responseDocument.getDocument();
	}

	/**
	 * @see group#checkForExistingTaxID(String)
	 */
	public Long checkForExistingTaxID(String taxID) {
		// If the taxID is one of the default taxIDs (which more than 1 client may
		// have), treat it as if the client
		// doesn't exist - return null. We do this separate check because we may have
		// only 1 client with the default
		// id and that would "appear" as an existing client.
		if (taxID != null) {
			if (taxID.equals(ClientDetail.DEFAULT_TAXID_SSN) || taxID.equals(ClientDetail.DEFAULT_TAXID)) {
				return null;
			}

			ClientDetail clientDetails[] = ClientDetail.findBy_TaxId(taxID);

			// If the client exists, return the pk. If more than one exists, return null
			// treat it as if the client doesn't
			// exist - return null.
			if (clientDetails != null && clientDetails.length == 1) {
				// Found one, use it
				return clientDetails[0].getClientDetailPK();
			}
			{
				// Found none or more than one
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * @see group#checkForExistingClient(String, String, edit.common.EDITDate)
	 */
	public Long checkForExistingClient(String lastName, String firstName, EDITDate birthDate) {
		ClientDetail[] clientDetails = null;

		if (birthDate == null) {
			// No birthDate available, don't do a lookup, return null
			return null;
		} else {
			clientDetails = ClientDetail.findByLastName_FirstName_BirthDate(lastName, firstName, birthDate);
		}

		// If the client exists, return the pk. If more than one exists, return null to
		// force the creation of
		// a new client (yes, another one because we don't know which one if there is
		// more than one)
		if ((clientDetails != null) && (clientDetails.length == 1)) {
			// Found one, use it
			return clientDetails[0].getClientDetailPK();
		} else {
			// Found none or more than one
			return null;
		}
	}

	// ============================================ PRIVATE METHODS
	// ==================================================

	private ClientDetail checkForExistingTaxIDForAGivenTrustType(String taxID, String trustTypeCT, String taxIdTypeCT) {
		// If the taxID is one of the default taxIDs (which more than 1 client may
		// have), treat it as if the client
		// doesn't exist - return null. We do this separate check because we may have
		// only 1 client with the default
		// id and that would "appear" as an existing client.
		if (taxID != null) {
			if (taxID.equals(ClientDetail.DEFAULT_TAXID_SSN) || taxID.equals(ClientDetail.DEFAULT_TAXID)) {
				return null;
			}

			ClientDetail clientDetails[] = ClientDetail.findBy_TaxIdentification_And_TrustTypeCT(taxID, trustTypeCT,
					taxIdTypeCT);

			// If the client exists, return the pk. If more than one exists, return null
			// treat it as if the client doesn't
			// exist - return null.
			if (clientDetails != null && clientDetails.length == 1) {
				// Found one, use it
				return clientDetails[0];
			}
			{
				// Found none or more than one
				return null;
			}
		} else {
			return null;
		}
	}

	private Segment buildSegment(BatchContractSetup batchContractSetup, Element requestParametersElement)
			throws EDITCaseException {
		Element segmentInformationElement = requestParametersElement.element("SegmentInformationVO");
		Element operatorElement = requestParametersElement.element("Operator");
		List clientInformationElements = requestParametersElement.elements("ClientInformationVO");
		List candidateRiderElements = requestParametersElement.elements("CandidateRiderVO");
		FilteredProduct filteredProduct = FilteredProduct
				.findByPK(Long.valueOf(segmentInformationElement.element("FilteredProductPK").getText()));

		Boolean beneficiariesEquallySplit = new Boolean(
				requestParametersElement.element("BeneficiariesEquallySplit").getText());

		String CBEEquallySplit = "N";

		if (requestParametersElement.element("CBEEquallySplit") != null) {
			CBEEquallySplit = requestParametersElement.element("CBEEquallySplit").getText();
		}

		String operator = operatorElement.getText();

		// Create the Segment
		Segment segment;
		if (filteredProduct.getProductStructure().getBusinessContractName().equals("UL")) {
			segment = new Segment(Segment.SEGMENTNAMECT_UL, Segment.OPTIONCODECT_UNIVERSAL_LIFE,
				Segment.SEGMENTSTATUSCT_SUBMIT_PEND);
		} else if (filteredProduct.getProductStructure().getBusinessContractName().equals("A&H")) {
			String optionCodeCT;
			if (segmentInformationElement.element("OptionCodeCT") == null) {
			     optionCodeCT = segmentInformationElement.element("GroupPlan").getText().split(" - ")[0];	
			} else {
			     optionCodeCT = segmentInformationElement.element("OptionCodeCT").getText();	
			}
			segment = new Segment(Segment.SEGMENTNAMECT_AH, optionCodeCT,
					Segment.SEGMENTSTATUSCT_SUBMIT_PEND);
		} else {
			segment = new Segment(Segment.SEGMENTNAMECT_TRADITIONAL, Segment.OPTIONCODECT_TRADITIONAL_LIFE,
				Segment.SEGMENTSTATUSCT_SUBMIT_PEND);
		}

		// Attach the BatchContractSetup to the Segment
		segment.setBatchContractSetup(batchContractSetup);

		// Attach the ContractGroup which is the same one off of the BatchContractSetup.
		segment.setContractGroup(batchContractSetup.getContractGroup());

		// Load the data into the Segment
		loadSegmentData(segment, segmentInformationElement, operator);

		// Build the agentHierarchies from the BatchContractSetup and attach to the
		// Segment
		buildSegmentAgentHierarchies(segment, batchContractSetup);

		String contractNumber = getContractNumberFromRequestParameters(requestParametersElement);

		// for UL
		if (filteredProduct.getProductStructure().getBusinessContractName().equals("UL")) {
			segment.addPremiumDue(createSegmentPremiumDue(segmentInformationElement, batchContractSetup));
		}
//ECK
		// Build riders
		if (!filteredProduct.getProductStructure().getBusinessContractName().equals("A&H")) {
		    buildRiders(segment, candidateRiderElements, beneficiariesEquallySplit, CBEEquallySplit,
				filteredProduct.getProductStructure().getBusinessContractName());
		}

		// Build ContractClients and attach to base Segment
		buildContractClients(segment, clientInformationElements, beneficiariesEquallySplit, CBEEquallySplit,
				contractNumber, operator);

		// Change BatchContractSetup's numberOfContracts to reflect the newly created
		// contract
		batchContractSetup.increaseNumberOfContractsCount();

		return segment;
	}

	/**
	 * Builds a new set of AgentHierarchies for the Segment using the same values as
	 * the BatchContractSetup's AgentHierarchies
	 *
	 * @param segment
	 * @param batchContractSetup
	 */
	private void buildSegmentAgentHierarchies(Segment segment, BatchContractSetup batchContractSetup) {
		Set<AgentHierarchy> segmentAgentHierarchies = new HashSet<AgentHierarchy>();

		// Get all of the BatchContractSetup's AgentHierarchies
		Set<AgentHierarchy> agentHierarchies = batchContractSetup.getActualAgentHierarchies();

		// For each AgentHierarchy, create a new one with all of the same values,
		// overlay BatchContractSetup values and add to the list
		for (Iterator iterator = agentHierarchies.iterator(); iterator.hasNext();) {
			AgentHierarchy agentHierarchy = (AgentHierarchy) iterator.next();

			AgentHierarchy segmentAgentHierarchy = agentHierarchy.deepCopy();

			segmentAgentHierarchy.setSegment(segment);

			segmentAgentHierarchies.add(segmentAgentHierarchy);
		}

		// Attach the AgentHierarchies to the Segment
		segment.setAgentHierarchies(segmentAgentHierarchies);
	}

	/**
	 * Builds the contractClients for each Client and their roles. If none of the
	 * clients have a Payor role, a Payor contractClient is created using the
	 * Owner's information.
	 *
	 * @param segment
	 * @param clientInformationElements
	 * @param beneficiariesEquallySplit
	 * @param contractNumber
	 * @param operator
	 */
	private void buildContractClients(Segment segment, List clientInformationElements,
			boolean beneficiariesEquallySplit, String CBEEquallySplit, String contractNumber, String operator) {
		boolean payorExists = false;
		ClientDetail ownerClientDetail = null;
		Element ownerContractClientInformationElement = null;
		String ownerRelationshipToEmployeeCT = null;
		String ownerEmployeeIdentification = null;
		List ownerTemplateQuestionnaireResponses = null;

		// look for an LT/OIR rider if this is base segment, may be necessary if a
		// TermInsured client is found
		Segment ltRider = null;

		// ECK
		if (!segment.getProductStructure().getBusinessContractName().equals("A&H")) {
			
	        for (Segment rider : segment.getSegments()) {
	        	if (rider.getOptionCodeCT().equals(Segment.OPTIONCODECT_RIDER_LEVEL_TERM_BENEFIT) ||
	        			rider.getOptionCodeCT().equals(Segment.OPTIONCODECT_RIDER_OTHER_INSURED_TERM)) {
	                ltRider = rider;
	                break;
	            }
	        }
	        
		/*	
			
			for (Segment rider : segment.getSegments()) {
				if (rider.getOptionCodeCT().equals(Segment.OPTIONCODECT_RIDER_LEVEL_TERM_BENEFIT)
						|| rider.getOptionCodeCT().equals(Segment.OPTIONCODECT_RIDER_OTHER_INSURED_TERM)) {
					ltRider = rider;
					break;
				}
			}

			// if not base segment, check if this is the LT/OIR rider segment
			if ((ltRider == null) && ((segment.getOptionCodeCT() != null)) 
					&& (segment.getOptionCodeCT().equals(Segment.OPTIONCODECT_RIDER_LEVEL_TERM_BENEFIT)
					|| segment.getOptionCodeCT().equals(Segment.OPTIONCODECT_RIDER_OTHER_INSURED_TERM))) {
				ltRider = segment;
		}
			*/
		}

		for (Iterator iterator = clientInformationElements.iterator(); iterator.hasNext();) {
			Element clientInformationElement = (Element) iterator.next();

			List questionnaireInformationElements = clientInformationElement.elements("QuestionnaireResponseVO");
			List contractClientInformationElements = clientInformationElement.elements("ContractClientInformationVO");

			String clientDetailPKString = clientInformationElement.element("ClientDetailPK").getText();
			String relationshipToEmployeeCT = XMLUtil
					.getText(clientInformationElement.element("RelationshipToEmployeeCT"));
			String employeeIdentification = XMLUtil.getText(clientInformationElement.element("EmployeeIdentification"));

			Long clientDetailPK = new Long(clientDetailPKString);

			ClientDetail clientDetail = ClientDetail.findByPK(clientDetailPK);

			// Build the QuestionnaireResponse objects from the element information. These
			// objects will then be
			// copied and attached to each new ContractClient
			List templateQuestionnaireResponses = buildQuestionnaireResponses(questionnaireInformationElements);

			for (Iterator iterator1 = contractClientInformationElements.iterator(); iterator1.hasNext();) {
				Element contractClientInformationElement = (Element) iterator1.next();

				// Keep track of payor and owner information
				// If the Payor has not been defined for any of the clients, we will need to
				// create one
				// based on the Owner's information
				if (isRoleTypeOf(ClientRole.ROLETYPECT_PAYOR, contractClientInformationElement)) {
					payorExists = true;
				} else if (isRoleTypeOf(ClientRole.ROLETYPECT_OWNER, contractClientInformationElement)) {
					ownerClientDetail = clientDetail;
					ownerContractClientInformationElement = contractClientInformationElement;
					ownerRelationshipToEmployeeCT = relationshipToEmployeeCT;
					ownerEmployeeIdentification = employeeIdentification;
					ownerTemplateQuestionnaireResponses = templateQuestionnaireResponses;
				}

				// connect TermInsured clients to LT rider
				String roleTypeCT = contractClientInformationElement.element("RoleTypeCT").getText();

				Segment clientSegment = segment;
				if (roleTypeCT.equals(ClientRole.ROLETYPECT_TERM_INSURED) && ltRider == null) {
					throw new RuntimeException("A " + ClientRole.ROLETYPECT_TERM_INSURED + " client was found, but no "
							+ Segment.OPTIONCODECT_RIDER_LEVEL_TERM_BENEFIT + " or "
							+ Segment.OPTIONCODECT_RIDER_OTHER_INSURED_TERM + " rider!");
				} else if (roleTypeCT.equals(ClientRole.ROLETYPECT_TERM_INSURED)) {
					clientSegment = ltRider;
				}

				buildContractClient(clientSegment, clientDetail, contractClientInformationElement, roleTypeCT,
						relationshipToEmployeeCT, employeeIdentification, templateQuestionnaireResponses,
						beneficiariesEquallySplit, CBEEquallySplit, contractNumber, operator);
			}
		}

		// If a Payor was not defined in the input, create a Payor using the owner
		// information. If the owner does not
		// exist, let the validation handle the errors (owners must exist and the check
		// is done in validation)
		if (!payorExists && ownerClientDetail != null) {
			createDefaultPayor(segment, ownerClientDetail, ownerContractClientInformationElement,
					ownerRelationshipToEmployeeCT, ownerEmployeeIdentification, ownerTemplateQuestionnaireResponses,
					beneficiariesEquallySplit, CBEEquallySplit, contractNumber, operator);
		}
	}

	/**
	 * Builds a single ContractClient and adds it to the ClientRole and Segment
	 *
	 * @param segment
	 * @param clientDetail
	 * @param contractClientInformationElement
	 * @param relationshipToEmployeeCT
	 * @param employeeIdentification
	 * @param templateQuestionnaireResponses
	 * @param beneficiariesEquallySplit
	 * @param contractNumber
	 * @param operator
	 */
	private void buildContractClient(Segment segment, ClientDetail clientDetail,
			Element contractClientInformationElement, String roleTypeCT, String relationshipToEmployeeCT,
			String employeeIdentification, List templateQuestionnaireResponses, boolean beneficiariesEquallySplit,
			String CBEEquallySplit, String contractNumber, String operator) {
		ClientRole clientRole = ClientRole.findBy_ClientDetail_RoleTypeCT_ReferenceID(clientDetail, roleTypeCT,
				contractNumber);

		if (clientRole == null) {
			clientRole = new ClientRole(roleTypeCT, ClientRole.PRIMARY_OVERRIDESTATUS, clientDetail);
			clientRole.setReferenceID(contractNumber);
		}

		ContractClient contractClient = new ContractClient();

		setDefaultContractClientData(segment, contractClient);

		contractClient.setOperator(operator);

		contractClient.setRelationshipToEmployeeCT(relationshipToEmployeeCT);
		contractClient.setEmployeeIdentification(employeeIdentification);

		if (clientRole.isBeneficiary()) {
			buildBeneficiaryContractClient(contractClient, roleTypeCT, beneficiariesEquallySplit, CBEEquallySplit,
					contractClientInformationElement);
		} else if (roleTypeCT.equalsIgnoreCase(ClientRole.ROLETYPECT_INSURED)
				|| roleTypeCT.equalsIgnoreCase(ClientRole.ROLETYPECT_TERM_INSURED)) {
			buildInsuredContractClient(contractClient, contractClientInformationElement, segment);
		}

		// Copy the template QuestionnaireResponses and attach to the contractClient
		copyQuestionnaireResponsesAndAddToContractClient(contractClient, templateQuestionnaireResponses);

		clientRole.addContractClient(contractClient);

		segment.addContractClient(contractClient);

		// Save ClientRole
		SessionHelper.saveOrUpdate(clientRole, SessionHelper.EDITSOLUTIONS);
	}

	/**
	 * Builds a ContractClient with one of the beneficiary roles
	 *
	 * @param contractClient
	 * @param beneficiariesEquallySplit
	 * @param contractClientInformationElement
	 */
	private void buildBeneficiaryContractClient(ContractClient contractClient, String roleTypeCT,
			boolean beneficiariesEquallySplit, String CBEEquallySplit, Element contractClientInformationElement) {
		EDITBigDecimal beneficiaryAllocation = XMLUtil
				.getEDITBigDecimalFromText(contractClientInformationElement.element("BeneficiaryAllocation"));
		String beneficiaryAllocationType = XMLUtil
				.getText(contractClientInformationElement.element("BeneficiaryAllocationType"));
		String beneficiaryRelationshipToInsured = XMLUtil
				.getText(contractClientInformationElement.element("BeneficiaryRelationshipToInsured"));

		ContractClientAllocation contractClientAllocation = new ContractClientAllocation();

		// Always set the allocation values and the split indicator. Validation for only
		// selecting one or the other
		// will occur during the validation phase.
		if (beneficiaryAllocationType != null) {
			if (beneficiaryAllocationType.equals("PERCENT")) {
				contractClientAllocation.setAllocationPercent(beneficiaryAllocation);
			} else if (beneficiaryAllocationType.equals("AMOUNT")) {
				contractClientAllocation.setAllocationDollars(beneficiaryAllocation);
			}
		}

		if (roleTypeCT != null && roleTypeCT.equalsIgnoreCase("CBE")) {
			if (CBEEquallySplit.equalsIgnoreCase("Y")) {
				contractClientAllocation.setSplitEqual("Y");
			} else {
				contractClientAllocation.setSplitEqual("N");
			}
		} else {
			contractClientAllocation.setSplitEqual(beneficiariesEquallySplit ? "Y" : "N");
		}

		contractClientAllocation.setOverrideStatus(ContractClientAllocation.OVERRIDESTATUS_PRIMARY);

		contractClient.setBeneRelationshipToInsured(beneficiaryRelationshipToInsured);

		contractClient.addContractClientAllocation(contractClientAllocation);
	}

	/**
	 * Builds a ContractClient with a role of Insured
	 *
	 * @param contractClient
	 */
	private void buildInsuredContractClient(ContractClient contractClient, Element contractClientInformationElement,
			Segment segment) {
		String classCT = XMLUtil.getText(contractClientInformationElement.element("ClassCT"));
		String tableRatingCT = XMLUtil.getText(contractClientInformationElement.element("TableRatingCT"));

		contractClient.setClassCT(classCT);

		contractClient.setTableRatingCT(tableRatingCT);

		// We need the base segment to get product underwritings. If this segment is a
		// rider, get its base (parent), otherwise
		// use this segment
		Segment baseSegment = segment.getSegment();

		if (baseSegment == null) // segment is a base
		{
			baseSegment = segment;
		}

		Enrollment enrollment = baseSegment.getBatchContractSetup().getEnrollment();

		ContractGroup groupContractGroup = baseSegment.getContractGroup();
		Long caseContractGroupFK = groupContractGroup.getContractGroupFK();
		FilteredProduct filteredProduct = FilteredProduct
				.findBy_ProductStructurePK_ContractGroupPK(baseSegment.getProductStructureFK(), caseContractGroupFK);

		// per Carrie, ratedGenderCT and underwrtingClassCT can be null for UL
		if (!filteredProduct.getProductStructure().getBusinessContractName().equals("UL")) {

			// Get the ratedGender from the CaseProductUnderwriting and set it in the
			// contractClient
			CaseProductUnderwriting[] ratedGenderCaseProductUnderwritings = CaseProductUnderwriting
					.findByEnrollment_FilteredProduct_Grouping_Field_RelationshipToEmployeeCT(enrollment,
							filteredProduct, CaseProductUnderwriting.GROUPING_CASEOTHER,
							CaseProductUnderwriting.FIELD_RATEDGENDER, null);

			if (ratedGenderCaseProductUnderwritings.length > 0) {
				contractClient.setRatedGenderCT(ratedGenderCaseProductUnderwritings[0].getValue());
			}

			// Get the underwritingClass from the CaseProductUnderwriting and set it in the
			// contractClient
			CaseProductUnderwriting[] underwritingClassCaseProductUnderwritings = CaseProductUnderwriting
					.findByEnrollment_FilteredProduct_Grouping_Field_RelationshipToEmployeeCT(enrollment,
							filteredProduct, CaseProductUnderwriting.GROUPING_CASEOTHER,
							CaseProductUnderwriting.FIELD_UNDERWRITING_CLASS, null);

			if (underwritingClassCaseProductUnderwritings.length > 0) {
				contractClient.setUnderwritingClassCT(underwritingClassCaseProductUnderwritings[0].getValue());
			}
		}
	}

	/**
	 * Builds the rider segments and attaches them to the base segment. NOTE: the
	 * contractNumber is not set on the rider here because it hasn't been determined
	 * yet. See GroupComponent.setContractNumber().
	 *
	 * @param segment
	 * @param candidateRiderElements
	 * @param beneficiariesEquallySplit
	 */
	private void buildRiders(Segment segment, List candidateRiderElements, boolean beneficiariesEquallySplit,
			String CBEEquallySplit, String productType) throws EDITCaseException {
		int riderNumber = 1;

		for (Iterator iterator = candidateRiderElements.iterator(); iterator.hasNext();) {
			Element candidateRiderElement = (Element) iterator.next();

			List clientInformationElements = candidateRiderElement.elements("ClientInformationVO");
			String ratedGenderCT = null;
			String originalStateCT = null;
			String underwritingClassCT = null;
			String groupPlan = null;
			String optionCodeCT = null;
			String coverage = candidateRiderElement.element("Coverage").getText();
			String qualifier = candidateRiderElement.element("Qualifier").getText();
			String requiredOptionalCT = candidateRiderElement.element("RequiredOptionalCT").getText();
			String effectiveDateString = candidateRiderElement.element("EffectiveDate").getText();
			String unitsString = candidateRiderElement.element("Units").getText();
			String faceAmountString = candidateRiderElement.element("FaceAmount").getText();
			String eobMultipleString = candidateRiderElement.element("EOBMultiple").getText();
			String gioOption = candidateRiderElement.element("GIOOption").getText();
			if (candidateRiderElement.element("RatedGenderCT") != null) {
				ratedGenderCT = candidateRiderElement.element("RatedGenderCT").getText();
			}
			if (candidateRiderElement.element("OrignalStateCT") != null) {
				originalStateCT = candidateRiderElement.element("OriginalStateCT").getText();
			}
			if (candidateRiderElement.element("UnderwritingClassCT") != null) {
				underwritingClassCT = candidateRiderElement.element("UnderwritingClassCT").getText();
			}
			if (candidateRiderElement.element("GroupPlan") != null) {
				groupPlan = candidateRiderElement.element("GroupPlan").getText();
			}
			if ((candidateRiderElement.element("OptionCodeCT") != null) &&
			 (!candidateRiderElement.element("OptionCodeCT").getText().isEmpty())) {
				optionCodeCT = candidateRiderElement.element("OptionCodeCT").getText();
			}
			CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

			String coverageCode = codeTableWrapper.getCodeByCodeTableNameAndCodeDesc("RIDERNAME", coverage);
//ECK
			Segment rider;
			if (productType.equals("A&H")) {
				rider = new Segment(Segment.SEGMENTNAMECT_AH, segment.getOptionCodeCT(),
					Segment.SEGMENTSTATUSCT_PENDING);
			} else if (productType.equals("UL")) {
				rider = new Segment(Segment.SEGMENTNAMECT_UL, coverageCode, Segment.SEGMENTSTATUSCT_PENDING);
			} else {
				rider = new Segment(Segment.SEGMENTNAMECT_TRADITIONAL, coverageCode, Segment.SEGMENTSTATUSCT_PENDING);
			}

			rider.setRiderNumber(riderNumber++);

			rider.setProductStructureFK(segment.getProductStructureFK());

			rider.setSegment(segment);

			EDITDate effectiveDate = DateTimeUtil.getEDITDateFromMMDDYYYY(effectiveDateString);

			if (effectiveDate == null) {
				rider.setEffectiveDate(segment.getEffectiveDate());
			} else {
				rider.setEffectiveDate(effectiveDate);
			}

			rider.setUnits(Util.initEDITBigDecimal(unitsString, null));
			rider.setAmount(Util.initEDITBigDecimal(faceAmountString, null));
			rider.setEOBMultiple(Util.initInt(eobMultipleString, 0));
			rider.setGIOOption(Util.initString(gioOption, null));
			rider.setRatedGenderCT(Util.initString(ratedGenderCT, null));
			rider.setOriginalStateCT(Util.initString(originalStateCT, null));
			rider.setUnderwritingClassCT(Util.initString(underwritingClassCT, null));
			rider.setGroupPlan(Util.initString(groupPlan, null));
//			rider.setOptionCodeCT(Util.initString(optionCodeCT, null));
			rider.setCreationOperator(segment.getOperator());

			rider.setOperator(segment.getOperator());

			// for UL allow nulls
			if (!productType.equals("UL") && !productType.equals("A&H")) {
			    rider.defaultGroupRatedGenUnderwriting("CASERIDERS");
			}

			// Attach the rider to the base segment. Be sure to do this before creating the
			// ContractClients
			// because they need the base segment information.
			segment.addRiderSegment(rider);

			// Build the ContractClients and attach to the rider
			buildContractClients(rider, clientInformationElements, beneficiariesEquallySplit, CBEEquallySplit,
					segment.getContractNumber(), segment.getOperator());
		}
	}

	/**
	 * Creates a new BillSchedule (with BillSkips) for the Segment using the exact
	 * same set of values as the ContractGroup's BillSchedule
	 *
	 * @param contractGroup
	 * @return new BillSchedule object
	 */
	private BillSchedule createSegmentBillSchedule(ContractGroup contractGroup) {
		BillSchedule segmentBillSchedule = (BillSchedule) SessionHelper.shallowCopy(contractGroup.getBillSchedule(),
				SessionHelper.EDITSOLUTIONS);

		return segmentBillSchedule;
	}

	private PremiumDue createSegmentPremiumDue(Element segmentInformationElement,
			BatchContractSetup batchContractSetup) {
		PremiumDue premiumDue = new PremiumDue();
		CommissionPhase commissionPhase = new CommissionPhase();
		commissionPhase.setCommissionPhaseID(1);
		commissionPhase.setCommissionTarget(new EDITBigDecimal("0.00"));
		String effectiveDateString = segmentInformationElement.element("EffectiveDate").getText();
		EDITDate effectiveDate = DateTimeUtil.getEDITDateFromMMDDYYYY(effectiveDateString);
		commissionPhase.setEffectiveDate(effectiveDate);
		commissionPhase.setExpectedMonthlyPremium(new EDITBigDecimal("0.00"));
		commissionPhase.setPriorExpectedMonthlyPremium(new EDITBigDecimal("0.00"));

		premiumDue.addCommissionPhase(commissionPhase);
		premiumDue.setEffectiveDate(effectiveDate);
		premiumDue.setPendingExtractIndicator(PremiumDue.PENDING_EXTRACT_P);
		BillSchedule billSchedule = batchContractSetup.getContractGroup().getBillSchedule();
		String billTypeCT = billSchedule.getBillTypeCT();
		String billingModeCT = billSchedule.getBillingModeCT();
		String deductionFrequencyCT = billSchedule.getDeductionFrequencyCT();
		// ECK

		String scheduledPremiumString = segmentInformationElement.elementText("ScheduledPremium");
		EDITBigDecimal scheduledPremium = new EDITBigDecimal(scheduledPremiumString);
		if (billTypeCT.equals("GRP")) {
			if (billingModeCT.equals("Annual") || billingModeCT.equals("SemiAnn") || billingModeCT.equals("Quart")) {
				premiumDue.setBillAmount(scheduledPremium);
				premiumDue.setDeductionAmount(scheduledPremium);
				premiumDue.setNumberOfDeductions(1);
			} else if (billingModeCT.equals("Month")) {
				if (deductionFrequencyCT.equals("09") || deductionFrequencyCT.equals("10")
						|| deductionFrequencyCT.equals("12")) {
					premiumDue.setBillAmount(scheduledPremium);
					premiumDue.setDeductionAmount(scheduledPremium);
					premiumDue.setNumberOfDeductions(1);
				} else if (deductionFrequencyCT.equals("18") || deductionFrequencyCT.equals("20")
						|| deductionFrequencyCT.equals("24")) {
					premiumDue.setBillAmount(scheduledPremium.multiplyEditBigDecimal(new BigDecimal(2.00)));
					premiumDue.setDeductionAmount(scheduledPremium);
					premiumDue.setNumberOfDeductions(2);
				} else if (deductionFrequencyCT.equals("36") || deductionFrequencyCT.equals("40")
						|| deductionFrequencyCT.equals("48")) {
					premiumDue.setBillAmount(scheduledPremium.multiplyEditBigDecimal(new BigDecimal(4.00)));
					premiumDue.setDeductionAmount(scheduledPremium);
					premiumDue.setNumberOfDeductions(4);
				}
			} else if (billingModeCT.equals("VarMonth")) {
				premiumDue.setBillAmount(new EDITBigDecimal("0.00"));
				premiumDue.setDeductionAmount(scheduledPremium);
				premiumDue.setNumberOfDeductions(0);
			} else if (billingModeCT.equals("13thly")) {
				if (deductionFrequencyCT.equals("26")) {
					premiumDue.setBillAmount(scheduledPremium.multiplyEditBigDecimal(new BigDecimal(2.00)));
					premiumDue.setDeductionAmount(scheduledPremium);
					premiumDue.setNumberOfDeductions(2);
				} else if (deductionFrequencyCT.equals("52")) {
					premiumDue.setBillAmount(scheduledPremium.multiplyEditBigDecimal(new BigDecimal(4.00)));
					premiumDue.setDeductionAmount(scheduledPremium);
					premiumDue.setNumberOfDeductions(4);
				}
			}
		} else if (billTypeCT.equals("INDIV")) {
			premiumDue.setBillAmount(scheduledPremium);
			premiumDue.setDeductionAmount(new EDITBigDecimal("0.00"));
			premiumDue.setNumberOfDeductions(0);

		}
		commissionPhase.setPremiumDue(premiumDue);

		return premiumDue;
	}

	/**
	 * Loads the defaults and request values for a Segment.
	 *
	 * @param segment                   partial segment whose fields will be filled
	 *                                  in
	 * @param segmentInformationElement
	 * @param operator                  current operator (user) of the system
	 */
	private void loadSegmentData(Segment segment, Element segmentInformationElement, String operator)
			throws EDITCaseException {
		setSegmentInformation(segment, segmentInformationElement);

		setDefaultSegmentData(segment); // must do 2nd since effectiveDate needs to be set in Segment first

		segment.setCreationOperator(operator);
		segment.setOperator(operator);

		if (segment.isLifeProduct()) {
			loadLifeData(segment, segmentInformationElement);
		}
	}

	private void setSegmentInformation(Segment segment, Element segmentInformationElement) throws EDITCaseException {
		String ratedGenderCTString = null;
		String underwritingClassString = null;
		String groupPlanString = null;
		String optionCodeString = null;
		String originalStateCTString = null;
		EDITBigDecimal annualPremium = null;
		String departmentLocationPKString = segmentInformationElement.element("DepartmentLocationPK").getText();
		String applicationSignedStateCTString = segmentInformationElement.element("ApplicationSignedStateCT").getText();
		if (segmentInformationElement.element("RatedGenderCT") != null) {
			ratedGenderCTString = segmentInformationElement.element("RatedGenderCT").getText();
		}
		if (segmentInformationElement.element("UnderwritingClassCT") != null) {
			underwritingClassString = segmentInformationElement.element("UnderwritingClassCT").getText();
		}
		if (segmentInformationElement.element("GroupPlan") != null) {
			groupPlanString = segmentInformationElement.element("GroupPlan").getText();
		}
		if ((segmentInformationElement.element("OptionCodeCT") != null) && 
				(!segmentInformationElement.element("OptionCodeCT").getText().trim().isEmpty())) {
			optionCodeString = segmentInformationElement.element("OptionCodeCT").getText();
		}
		if ((segmentInformationElement.element("OriginalStateCT") != null) && 
		 (!segmentInformationElement.element("OriginalStateCT").getText().isEmpty()))  {
			originalStateCTString = segmentInformationElement.element("OriginalStateCT").getText();
		}
		if (segmentInformationElement.element("AnnualPremium") != null) {
			annualPremium = XMLUtil.getEDITBigDecimalFromText(segmentInformationElement.element("AnnualPremium"));
		}
		String applicationSignedDateString = segmentInformationElement.element("ApplicationSignedDate").getText();
		String issueStateCTString = segmentInformationElement.element("IssueStateCT").getText();
		String issueStateORIndString = segmentInformationElement.element("IssueStateORInd").getText();
		String estateOfTheInsured = segmentInformationElement.element("EstateOfTheInsured").getText();
		String deductionAmountEffectiveDateString = segmentInformationElement.element("DeductionAmountEffectiveDate")
				.getText();

		String effectiveDateString = segmentInformationElement.element("EffectiveDate").getText();
		EDITDate effectiveDate = DateTimeUtil.getEDITDateFromMMDDYYYY(effectiveDateString);

		EDITDate applicationReceivedDate = segment.getBatchContractSetup().getReceiptDate();
		EDITDate applicationSignedDate = DateTimeUtil.getEDITDateFromMMDDYYYY(applicationSignedDateString);

		EDITBigDecimal deductionAmountOverride = XMLUtil
				.getEDITBigDecimalFromText(segmentInformationElement.element("DeductionAmountOverride"));
		EDITDate deductionAmountEffectiveDate = DateTimeUtil
				.getEDITDateFromMMDDYYYY(deductionAmountEffectiveDateString);

		String filteredProductPKString = segmentInformationElement.element("FilteredProductPK").getText();
		FilteredProduct filteredProduct = FilteredProduct.findByPK(new Long(filteredProductPKString));
		Long productStructurePK = filteredProduct.getProductStructureFK();

		segment.setProductStructureFK(productStructurePK);

		Long departmentLocationPK = Util.initLong(departmentLocationPKString, null);

		if (departmentLocationPK != null) {
			DepartmentLocation departmentLocation = DepartmentLocation
					.findBy_DepartmentLocationPK(departmentLocationPK);
			segment.setDepartmentLocation(departmentLocation);
		}

		segment.setEffectiveDate(effectiveDate);
		segment.setApplicationReceivedDate(applicationReceivedDate);
		segment.setApplicationSignedDate(applicationSignedDate);
		segment.setApplicationSignedStateCT(applicationSignedStateCTString);
		segment.setRatedGenderCT(Util.initString(ratedGenderCTString, null));
		segment.setGroupPlan(Util.initString(groupPlanString, null));
		if ((optionCodeString != null) && !optionCodeString.isEmpty()) {
		    segment.setOptionCodeCT(Util.initString(optionCodeString, null));
		}
		segment.setUnderwritingClassCT(Util.initString(underwritingClassString, null));
		
		if (!filteredProduct.getProductStructure().getBusinessContractName().equals("UL") &&
			!filteredProduct.getProductStructure().getBusinessContractName().equals("A&H")) {
			segment.defaultGroupRatedGenUnderwriting("CASEBASE");
		}
		
		segment.setIssueStateCT(issueStateCTString);
		segment.setOriginalStateCT(originalStateCTString);
		segment.setIssueStateORInd(issueStateORIndString);
		segment.setEstateOfTheInsured(estateOfTheInsured);
		segment.setDeductionAmountOverride(deductionAmountOverride);
		segment.setDeductionAmountEffectiveDate(deductionAmountEffectiveDate);
		
		// for UL
		if (filteredProduct.getProductStructure().getBusinessContractName().equals("UL")) {
			String exchangeIndString = segmentInformationElement.element("ExchangeInd").getText();
			if ((exchangeIndString != null) && (exchangeIndString.trim().isEmpty())) {
				exchangeIndString = "N";
			}
			segment.setExchangeInd(exchangeIndString);
		}
		
		// for A&H
		if (filteredProduct.getProductStructure().getBusinessContractName().equalsIgnoreCase("A&H")) {
			if (annualPremium != null) {
				segment.setAnnualPremium(annualPremium);
			}
		}

	}

	private void setDefaultSegmentData(Segment segment) {
		segment.setWaiveFreeLookIndicator(Segment.INDICATOR_YES);

		segment.setDateInEffect(segment.getEffectiveDate());
		segment.setLastAnniversaryDate(segment.getEffectiveDate());
	}

	/**
	 * Loads the defaults and request values for a Life segment
	 *
	 * @param segment
	 * @param segmentInformationElement
	 */
	private void loadLifeData(Segment segment, Element segmentInformationElement) {
		String deathBenefitOptionCT = XMLUtil.getText(segmentInformationElement.element("DeathBenefitOptionCT"));
		String nonForfeitureOptionCT = XMLUtil.getText(segmentInformationElement.element("NonForfeitureOptionCT"));
		EDITBigDecimal faceAmount = XMLUtil.getEDITBigDecimalFromText(segmentInformationElement.element("FaceAmount"));

		// for UL
		String mecStatusCTString = XMLUtil.getText(segmentInformationElement.element("mecStatusCT"));
		if ((mecStatusCTString != null) && (mecStatusCTString.trim().isEmpty())) {
			mecStatusCTString = "N";
		}

		Life life = new Life();

		setDefaultLifeData(life);

		life.setDeathBenefitOptionCT(deathBenefitOptionCT);
		life.setNonForfeitureOptionCT(nonForfeitureOptionCT);
		life.setFaceAmount(faceAmount);

		// for UL
		String filteredProductPKString = segmentInformationElement.element("FilteredProductPK").getText();
		FilteredProduct filteredProduct = FilteredProduct.findByPK(new Long(filteredProductPKString));
		if (filteredProduct.getProductStructure().getBusinessContractName().equals("UL")) {
			life.setMECStatusCT(mecStatusCTString);
		}
		segment.setLife(life);
	}

	/**
	 * Sets default values for Life
	 *
	 * @param life
	 */
	private void setDefaultLifeData(Life life) {
		life.setGuidelineSinglePremium(new EDITBigDecimal());
		life.setGuidelineLevelPremium(new EDITBigDecimal());
		life.setTamra(new EDITBigDecimal());
		life.setMECGuidelineLevelPremium(new EDITBigDecimal());
		life.setMECGuidelineSinglePremium(new EDITBigDecimal());
		life.setCumGuidelineLevelPremium(new EDITBigDecimal());
		life.setMaximumNetAmountAtRisk(new EDITBigDecimal());
		life.setDueAndUnpaid(new EDITBigDecimal());
	}

	private void setDefaultContractClientData(Segment segment, ContractClient contractClient) {
		contractClient.setEffectiveDate(segment.getEffectiveDate());

		contractClient.setTerminationDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));
		contractClient.setOverrideStatus(ContractClient.OVERRIDE_STATUS_PENDING);
		contractClient.setFlatExtra(new EDITBigDecimal());
		contractClient.setPercentExtra(new EDITBigDecimal());
		contractClient.setFlatExtraAge(0);
		contractClient.setFlatExtraDur(0);
		contractClient.setPercentExtraAge(0);
		contractClient.setPercentExtraDur(0);

		contractClient.setMaintDateTime(new EDITDateTime());
	}

	/**
	 * Creates the ContractGroup element with its corresponding ClientRole and
	 * ClientDetail
	 *
	 * @param contractGroup contractGroup to make an element of
	 * @return Element containing the ContractGroup and its corresponding ClientRole
	 *         and ClientDetail
	 */
	private Element getContractGroupElement(ContractGroup contractGroup) {
		ClientRole clientRole = contractGroup.getClientRole();
		ClientDetail clientDetail = clientRole.getClientDetail();

		Element contractGroupElement = contractGroup.getAsElement(true, true);
		Element clientRoleElement = clientRole.getAsElement(true, true);
		Element clientDetailElement = clientDetail.getAsElement(true, true);

		clientRoleElement.add(clientDetailElement);
		contractGroupElement.add(clientRoleElement);

		return contractGroupElement;
	}

	/**
	 * Builds the AgentHierarchy element which contains the
	 * AgentHierarchyAllocations, AgentSnapshots, PlacedAgent, etc.
	 *
	 * @param agentHierarchy
	 * @return complete element
	 * @see Group#getCandidateAgentHierarchies(org.dom4j.Document)
	 */
	private Element getWritingAgentSnapshotElement(AgentHierarchy agentHierarchy) {
		// Get the entities...

		// AgentHierarchy
		Element agentHierarchyElement = agentHierarchy.getAsElement(true, true);

		// AgentHierarchyAllocation
		AgentHierarchyAllocation agentHierarchyAllocation = agentHierarchy.getAgentHierarchyAllocations().iterator()
				.next();

		Element agentHierarchyAllocationElement = agentHierarchyAllocation.getAsElement(true, true);

		// AgentSnapshot
		AgentSnapshot writingAgentSnapshot = AgentSnapshot
				.findBy_AgentHierarchyPK_V1(agentHierarchy.getAgentHierarchyPK());

		Element writingAgentSnapshotElement = writingAgentSnapshot.getAsElement(true, true);

		// PlacedAgent
		PlacedAgent placedAgent = writingAgentSnapshot.getPlacedAgent();

		Element placedAgentElement = placedAgent.getAsElement(true, true);

		// PlacedAgentCommissionProfile
		PlacedAgentCommissionProfile placedAgentCommissionProfile = placedAgent.getPlacedAgentCommissionProfiles()
				.iterator().next();

		Element placedAgentCommissionProfileElement = placedAgentCommissionProfile.getAsElement(true, true);

		// CommissionProfile
		CommissionProfile commissionProfile = placedAgentCommissionProfile.getCommissionProfile();

		Element commissionProfileElement = commissionProfile.getAsElement(true, true);

		// AgentContract
		AgentContract agentContract = placedAgent.getAgentContract();

		Element agentContractElement = agentContract.getAsElement(true, true);

		// Agent
		Agent agent = agentContract.getAgent();

		Element agentElement = agent.getAsElement(true, true);

		// ClientRole
		ClientRole clientRole = placedAgent.getClientRole();

		Element clientRoleElement = clientRole.getAsElement(true, true);

		// ClientDetail
		ClientDetail clientDetail = clientRole.getClientDetail();

		Element clientDetailElement = clientDetail.getAsElement(true, true);

		// Perform the associations ...
		writingAgentSnapshotElement.add(agentHierarchyElement);

		agentHierarchyElement.add(agentHierarchyAllocationElement);

		writingAgentSnapshotElement.add(placedAgentElement);

		placedAgentElement.add(agentContractElement);

		agentContractElement.add(agentElement);

		agentElement.add(clientRoleElement);

		clientRoleElement.add(clientDetailElement);

		placedAgentElement.add(placedAgentCommissionProfileElement);

		placedAgentCommissionProfileElement.add(commissionProfileElement);

		return writingAgentSnapshotElement;
	}

	/**
	 * Builds an Element containing a ClientDetail and its corresponding primary
	 * ClientAddress (if exists)
	 *
	 * @param clientDetail ClientDetail to create element out of
	 * @return Element containing a ClientDetail and its primary ClientAddress (or
	 *         nothing if primary address doesn't exist)
	 */
	private Element getClientDetailElement(ClientDetail clientDetail) {
		Element clientDetailElement = clientDetail.getAsElement(true, true);

		ClientAddress primaryClientAddress = clientDetail.getPrimaryAddress();

		if (primaryClientAddress != null) {
			Element primaryClientAddressElement = primaryClientAddress.getAsElement(true, true);

			clientDetailElement.add(primaryClientAddressElement);
		}

		return clientDetailElement;
	}

	/**
	 * Builds the response element for a BatchContractSetup and its Group, Case, and
	 * candidate enrollments
	 *
	 * @param batchContractSetup object to make the response element for
	 * @return a fully structured response element for BatchContractSetup
	 */
	private Element getBatchContractSetupResponseElement(BatchContractSetup batchContractSetup) {
		Element batchContractSetupResponseElement = batchContractSetup.getAsElement(true, true);

		// Get the group and case as elements
		Element groupContractGroupElement = getContractGroupElement(batchContractSetup.getGroupContractGroup());
		Element caseContractGroupElement = getContractGroupElement(batchContractSetup.getCaseContractGroup());

		groupContractGroupElement.add(caseContractGroupElement);
		batchContractSetupResponseElement.add(groupContractGroupElement);

		// Get candidate enrollments as elements
		List candidateEnrollmentElements = getCandidateEnrollmentElements(batchContractSetup);

		for (Iterator iterator = candidateEnrollmentElements.iterator(); iterator.hasNext();) {
			Element candidateEnrollmentElement = (Element) iterator.next();

			batchContractSetupResponseElement.add(candidateEnrollmentElement);
		}

		Element billScheduleElement = getBillScheduleElement(batchContractSetup.getGroupContractGroup());
		if (billScheduleElement != null) {
			groupContractGroupElement.add(billScheduleElement);
		}

		return batchContractSetupResponseElement;
	}

	private Element getBillScheduleElement(ContractGroup groupContractGroup) {
		BillSchedule billSchedule = groupContractGroup.getBillSchedule();

		if (billSchedule != null) {
			return billSchedule.getAsElement(true, true);
		} else {
			return null;
		}
	}

	private Element buildCandidateRiderElement(CaseProductUnderwriting caseProductUnderwriting) {
		Element candidateRiderElement = new DefaultElement("CandidateRiderVO");

		Element coverageElement = new DefaultElement("Coverage");
		Element qualifierElement = new DefaultElement("Qualifier");
		Element requiredOptionalCTElement = new DefaultElement("RequiredOptionalCT");
		Element effectiveDateElement = new DefaultElement("EffectiveDate");
		Element unitsElement = new DefaultElement("Units");
		Element faceAmountElement = new DefaultElement("FaceAmount");
		Element ratedGenderCTElement = new DefaultElement("RatedGenderCT");
		Element originalStateCTElement = new DefaultElement("originalStateCT");
		Element underwritingClassElement = new DefaultElement("UnderwritingClassCT");
		Element groupPlanElement = new DefaultElement("GroupPlan");
		Element optionCodeElement = new DefaultElement("OptionCodeCT");
		Element eobMultipleElement = new DefaultElement("EOBMultiple");
		Element gioOptionElement = new DefaultElement("GIOOption");
		Element allowableRoleElement = new DefaultElement("AllowableRole");
		Element contractClientAllowedElement = new DefaultElement("ContractClientAllowed");
		Element gioSelectionAllowedElement = new DefaultElement("GIOSelectionAllowed");
		Element allowRiderClientSelectionElement = new DefaultElement("AllowRiderClientSelection");

		qualifierElement.setText(caseProductUnderwriting.getQualifier());
		requiredOptionalCTElement.setText(caseProductUnderwriting.getRequiredOptionalCT());
		XMLUtil.setText(allowableRoleElement,
				Segment.getAllowableRoleForRiderType(caseProductUnderwriting.getQualifier()));

		effectiveDateElement.setText("");
		unitsElement.setText("");
		faceAmountElement.setText("");
		eobMultipleElement.setText("");
		gioOptionElement.setText("");
		ratedGenderCTElement.setText("");
		originalStateCTElement.setText("");
		underwritingClassElement.setText("");
		groupPlanElement.setText("");
//		optionCodeElement.setText("");

		CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

		String coverageCodeDesc = codeTableWrapper.getCodeDescByCodeTableNameAndCode("RIDERNAME",
				caseProductUnderwriting.getQualifier());
		String coverageCode = codeTableWrapper.getCodeByCodeTableNameAndCodeDesc("RIDERNAME", coverageCodeDesc);

		coverageElement.setText(coverageCodeDesc);

		XMLUtil.setText(contractClientAllowedElement, Segment.getContractClientAllowedForRiderType(coverageCode));
		XMLUtil.setText(gioSelectionAllowedElement, Segment.getGIOSelectionAllowedForRiderType(coverageCode));
		XMLUtil.setText(allowRiderClientSelectionElement,
				Segment.getAllowRiderClientSelectionForRiderType(coverageCode));

		candidateRiderElement.add(coverageElement);
		candidateRiderElement.add(qualifierElement);
		candidateRiderElement.add(requiredOptionalCTElement);
		candidateRiderElement.add(effectiveDateElement);
		candidateRiderElement.add(unitsElement);
		candidateRiderElement.add(faceAmountElement);
		candidateRiderElement.add(ratedGenderCTElement);
		candidateRiderElement.add(originalStateCTElement);
		candidateRiderElement.add(underwritingClassElement);
		candidateRiderElement.add(groupPlanElement);
		candidateRiderElement.add(optionCodeElement);
		candidateRiderElement.add(eobMultipleElement);
		candidateRiderElement.add(gioOptionElement);
		candidateRiderElement.add(allowableRoleElement);
		candidateRiderElement.add(contractClientAllowedElement);
		candidateRiderElement.add(gioSelectionAllowedElement);
		candidateRiderElement.add(allowRiderClientSelectionElement);

		return candidateRiderElement;
	}

	private Long getBatchContractSetupPKFromRequest(Element requestParametersElement) {
		Element batchContractSetupPKElement = requestParametersElement.element("BatchContractSetupPK");

		Long batchContractSetupPK = new Long(batchContractSetupPKElement.getText());

		return batchContractSetupPK;
	}

	private BatchContractSetup getBatchContractSetupFromRequest(Element requestParametersElement) {
		BatchContractSetup batchContractSetup = BatchContractSetup
				.findByPK(getBatchContractSetupPKFromRequest(requestParametersElement));

		return batchContractSetup;
	}

	/**
	 * Builds, runs and saves the submit transaction. Adds any script messages to
	 * the response document
	 *
	 * @param segment
	 * @param responseDocument
	 */
	private EDITTrx buildRunAndSaveSubmitTransaction(Segment segment, SEGResponseDocument responseDocument) {
		EDITTrx editTrx = null;
		try {
			editTrx = EDITTrx.createSubmitTrxGroupSetup(segment, segment.getOperator(), "true");
		} catch (EDITEventException e) {
			ValidationVO[] validationVOs = e.getValidationVO();

			if (validationVOs != null && validationVOs.length > 0) {
				convertValidationVOsToSEGResponseMessages(validationVOs, responseDocument);
			} else if (!e.getMessageList().isEmpty()) {
				for (Object message : e.getMessageList()) {
					responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, (String) message);
				}
			}
		}
		return editTrx;
	}

	private List getCandidateEnrollmentElements(BatchContractSetup batchContractSetup) {
		List candidateEnrollmentElements = new ArrayList();

		ContractGroup caseContractGroup = batchContractSetup.getCaseContractGroup();

		Set<Enrollment> enrollments = caseContractGroup.getEnrollments();

		for (Iterator iterator = enrollments.iterator(); iterator.hasNext();) {
			Enrollment enrollment = (Enrollment) iterator.next();

			Element enrollmentElement = enrollment.getAsElement(true, true);

			candidateEnrollmentElements.add(enrollmentElement);
		}

		return candidateEnrollmentElements;
	}

	/**
	 * Build set of QuestionnaireResponses using the FilteredQuestionnairePKs and
	 * their responseCTs.
	 *
	 * @param questionnaireInformationElements
	 * @return List of QuestionnaireResponse objects
	 */
	private List buildQuestionnaireResponses(List questionnaireInformationElements) {
		List questionnaireResponses = new ArrayList();

		for (Iterator iterator = questionnaireInformationElements.iterator(); iterator.hasNext();) {
			Element questionnaireInformationElement = (Element) iterator.next();

			Element filteredQuestionnairePKElement = questionnaireInformationElement.element("FilteredQuestionnaireFK");
			Element responseCTElement = questionnaireInformationElement.element("ResponseCT");

			String filteredQuestionnairePKString = filteredQuestionnairePKElement.getText();
			String responseCT = responseCTElement.getText();

			Long filteredQuestionnairePK = new Long(filteredQuestionnairePKString);

			FilteredQuestionnaire filteredQuestionnaire = FilteredQuestionnaire.findByPK(filteredQuestionnairePK);

			QuestionnaireResponse questionnaireResponse = new QuestionnaireResponse(filteredQuestionnaire, responseCT);

			questionnaireResponses.add(questionnaireResponse);
		}

		return questionnaireResponses;
	}

	private void copyQuestionnaireResponsesAndAddToContractClient(ContractClient contractClient,
			List templateQuestionnaireResponses) {
		for (Iterator iterator = templateQuestionnaireResponses.iterator(); iterator.hasNext();) {
			QuestionnaireResponse questionnaireResponse = (QuestionnaireResponse) iterator.next();

			QuestionnaireResponse newQuestionnaireResponse = questionnaireResponse.deepCopy();

			contractClient.addQuestionnaireResponse(newQuestionnaireResponse);
		}
	}

	/**
	 * Loads the list of importResponseDocuments into the responseDocument. Result
	 * is one outer SEGResponseVO which contains Elements of many
	 * ResponseMessageVOs.
	 *
	 * @param responseDocument
	 * @param importResponseDocuments
	 */
	private void loadImportResponseDocumentsToResponseDocument(SEGResponseDocument responseDocument,
			List importResponseDocuments) {
		for (Iterator iterator = importResponseDocuments.iterator(); iterator.hasNext();) {
			SEGResponseDocument importResponseDocument = (SEGResponseDocument) iterator.next();

			List responseMessagesForOneContract = importResponseDocument.getResponseMessages();

			for (Iterator iterator1 = responseMessagesForOneContract.iterator(); iterator1.hasNext();) {
				Element responseMessageElement = (Element) iterator1.next();

				responseDocument.addResponseMessage(responseMessageElement);
			}
		}
	}

	/**
	 * Validates the input when adding a Segment to a ContractGroup. If any errors
	 * are found, adds the errors to the responseDocument
	 *
	 * @param batchContractSetup
	 * @param segment
	 * @param responseDocument
	 * @return false if any errors are found, true otherwise
	 */
	private boolean validateSegmentInput(BatchContractSetup batchContractSetup, Segment segment,
			ContractGroup groupContractGroup, Element requestParametersElement, SEGResponseDocument responseDocument) {
		boolean isValid = true;

		List errorMessages = new ArrayList();

		validateDistinct(segment, errorMessages);

		validateExistence(batchContractSetup.getReceiptDate(), "Batch Transmittal ReceiptDate", errorMessages);
		validateExistence(batchContractSetup.getEnrollmentMethodCT(), "Batch Transmittal Enrollment Method",
				errorMessages);
		validateExistence(batchContractSetup.getApplicationTypeCT(), "Batch Transmittal Application Type",
				errorMessages);

		validateExistence(segment.getApplicationSignedDate(), "Application Signed Date", errorMessages);
		validateExistence(segment.getApplicationSignedStateCT(), "Application Signed State", errorMessages);
		validateExistence(segment.getEffectiveDate(), "Segment Effective Date", errorMessages);
		
		validateContractClients(segment, errorMessages);
		
		if (segment != null && segment.getSegmentNameCT() != null && !segment.getSegmentNameCT().equalsIgnoreCase(Segment.SEGMENTNAMECT_AH)) {
			validateExistence(segment.getLife().getFaceAmount(), "Face Amount", errorMessages);
			validateRiders(segment, errorMessages);
		}

		validateBeneficiaryAllocations(segment, errorMessages);

		validateGroupIsActive(groupContractGroup, segment, errorMessages);

		validateBilling(groupContractGroup, errorMessages);

		validateDepartmentLocation(batchContractSetup, segment, errorMessages);

		validateEnrollment(batchContractSetup, segment, errorMessages);

		// If there were any error messages set, then the input is not valid and the
		// messages must be placed as
		// ResponseMessages in the responseDocument
		if (!errorMessages.isEmpty()) {
			isValid = false;
			logErrorsToResponseDocument(errorMessages, responseDocument);
		}

		return isValid;
	}

	/**
	 * Validates that the segment is not already present in the database. This is
	 * performed on the basis of app signed date, effective date, insured tax ID,
	 * and option codes.
	 *
	 * @param segment       the record to validate the uniqueness of
	 * @param errorMessages Error message collection to append to whenever the
	 *                      segment is non-distinct
	 */
	private void validateDistinct(Segment segment, List errorMessages) {
		String ownerTaxId = "(not found)";
		String insuredFirstName = "(not found)";
		EDITDate insuredDOB = null;
		for (ContractClient client : segment.getContractClients()) {
			if (client.getRoleType().equals("OWN")) {
				ownerTaxId = client.getClientDetail().getTaxIdentification();
			}
			if (client.getRoleType().equals("Insured")) {
				insuredFirstName = client.getClientDetail().getFirstName();
				insuredDOB = client.getClientDetail().getBirthDate();
			}
		}

		// retrieve all option codes
		org.hibernate.Session sess = SessionHelper.getSession(SessionHelper.EDITSOLUTIONS);

		@SuppressWarnings("unchecked")
		List<Object[]> foundOptionCodes = sess.createQuery("select s.OptionCodeCT, rider.OptionCodeCT from Segment s "
				+ " left join s.Segments rider "
				+ " where exists (from ContractClient ownClient join ownClient.ClientRole "
				+ "ownRole with ownRole.RoleTypeCT = 'OWN' join ownRole.ClientDetail ownDetail "
				+ "where ownDetail.TaxIdentification = :taxId and ownClient.Segment = s) "
				+ " and exists (from ContractClient insClient join insClient.ClientRole insRole with insRole.RoleTypeCT = 'Insured' "
				+ " join insRole.ClientDetail insuredDetail "
				+ " where insuredDetail.BirthDate = :insuredDOB and insuredDetail.FirstName = :insuredFirstName and insClient.Segment = s) "
				+ " and s.ApplicationSignedDate = :appSignedDate and s.EffectiveDate = :effectiveDate and s.SegmentStatusCT != 'Frozen'")
				.setParameter("appSignedDate", segment.getApplicationSignedDate())
				.setParameter("effectiveDate", segment.getEffectiveDate()).setParameter("taxId", ownerTaxId)
				.setParameter("insuredDOB", insuredDOB).setParameter("insuredFirstName", insuredFirstName).list();

		if (foundOptionCodes != null) {
			// option codes of the found contract
			HashSet<String> foundCodeSet = new HashSet<String>();
			for (Object[] codeSet : foundOptionCodes) {
				foundCodeSet.add(codeSet[0].toString());
				if (codeSet[1] != null)
					foundCodeSet.add(codeSet[1].toString());
			}

			// option codes of the incoming contract
			HashSet<String> incomingCodeSet = new HashSet<String>();
			incomingCodeSet.add(segment.getOptionCodeCT());
			for (Segment rider : segment.getSegments()) {
				incomingCodeSet.add(rider.getOptionCodeCT());
			}

			if (foundCodeSet.equals(incomingCodeSet)) {
				errorMessages.add("A non-frozen contract with the same signed date, effective date, "
						+ "owner tax ID, insured & DOB, and riders already exists!");
			}
		}
	}

	/**
	 * Validates riders on the segment record.
	 *
	 * @param segment       the record to validate riders for
	 * @param errorMessages A reference to append validation errors to if found
	 */
	private void validateRiders(Segment segment, List errorMessages) {
		if (segment.getOptionCodeCT().isEmpty()) {
			errorMessages.add("Blank or unknown option code assigned to segment with app. signed date "
					+ segment.getApplicationSignedDate().toString());
		}

		if (segment.getSegments() != null) {
			for (Segment rider : segment.getSegments()) {
				if (rider.getOptionCodeCT().isEmpty()) {
					errorMessages.add("Blank or unknown rider option code assigned to segment with app. signed date "
							+ segment.getApplicationSignedDate().toString());
				} else if (rider.getOptionCodeCT().equals("LT") && rider.getContractClient() == null) {
					// make sure one of the clients is a TermInsured
					boolean foundTermInsured = false;
					for (ContractClient client : segment.getContractClients()) {
						if (client.getRoleType().equals("TermInsured")) {
							foundTermInsured = true;
							break;
						}
					}

					if (!foundTermInsured) {
						errorMessages.add("There is an LT rider on the policy but no client has role 'TermInsured'");
					}
				}
			}
		}
	}

	/**
	 * Validates the Contract Clients.
	 *
	 * @param segment
	 * @param errorMessages
	 */
	private void validateContractClients(Segment segment, List errorMessages) {
		// Check the existence of Owner and Payor
		validateExistence(segment.getContractClients(ClientRole.ROLETYPECT_OWNER), "Owner Client ", errorMessages);
		validateExistence(segment.getContractClients(ClientRole.ROLETYPECT_PAYOR), "Payor Client", errorMessages);

		// Check existence of and fields of the Insured
		validateInsuredContractClients(segment, ClientRole.ROLETYPECT_INSURED, errorMessages);
		validateMultiplePayorContractClients(segment, errorMessages);
		validateMultipleOwnerContractClients(segment, errorMessages);
		// Check fields of the TermInsured which would only be on riders
		Set<Segment> riders = segment.getSegments();

		// make sure the beneficiary relationships are valid by
		// building up a collection of relations and checking each client
		CodeTableWrapper ctWrapper = CodeTableWrapper.getSingleton();
		CodeTableVO[] relationEntries = ctWrapper.getCodeTableEntries("LIFERELATIONTYPE");
		Set<String> relationCodes = new HashSet<String>();
		for (CodeTableVO entry : relationEntries) {
			relationCodes.add(entry.getCode());
		}

		Set<String> unrecognizedCodes = new HashSet<String>();
		for (ContractClient client : segment.getContractClients()) {

			if (!relationCodes.contains(client.getRoleType())) {
				unrecognizedCodes.add(client.getRoleType());
			}
		}
		if (!unrecognizedCodes.isEmpty()) {
			String message = "Unrecognized relation codes: " + Joiner.on(", ").join(unrecognizedCodes);
			errorMessages.add(message);
		}

		for (Iterator segmentIterator = riders.iterator(); segmentIterator.hasNext();) {
			Segment rider = (Segment) segmentIterator.next();

			validateInsuredContractClients(rider, ClientRole.ROLETYPECT_TERM_INSURED, errorMessages);
		}
	}

	/**
	 * Validates the existence of Multiple Owners.
	 *
	 * @param segment
	 * @param errorMessages
	 */
	private void validateMultipleOwnerContractClients(Segment segment, List errorMessages) {

		ContractClient[] ownerContractClients = segment.getContractClients(ClientRole.ROLETYPECT_OWNER);

		// check to make sure multilple owner's exists
		if (ownerContractClients.length > 1) {
			errorMessages.add("Multiple Owners Not Allowed");

		}
	}

	/**
	 * Validates the existence of Multiple Payor's.
	 *
	 * @param segment
	 * @param errorMessages
	 */
	private void validateMultiplePayorContractClients(Segment segment, List errorMessages) {

		ContractClient[] payorContractClients = segment.getContractClients(ClientRole.ROLETYPECT_PAYOR);

		// check to make sure Multiple Payor's exists
		if (payorContractClients.length > 1) {
			errorMessages.add("Multiple Payors Not Allowed");
		}
	}

	/**
	 * Validates the existence of certain fields for "insured" contract clients.
	 * "Insured" can be Insured or TermInsured roleTypes (for now, there may be more
	 * in the future).
	 *
	 * @param segment
	 * @param roleTypeCT
	 * @param errorMessages
	 */
	private void validateInsuredContractClients(Segment segment, String roleTypeCT, List errorMessages) {
		String errorMessageStart = roleTypeCT + "'s ";

		ContractClient[] insuredContractClients = segment.getContractClients(roleTypeCT);

		// For roleTypes of Insured only, check to make sure at least one Insured exists
		if (roleTypeCT.equalsIgnoreCase(ClientRole.ROLETYPECT_INSURED)) {
			validateExistence(insuredContractClients, "Insured Client", errorMessages);
		}

		for (int i = 0; i < insuredContractClients.length; i++) {
			String insuredIdentifier;
			if (insuredContractClients[i].getClientDetail() != null) {
				insuredIdentifier = "[" + insuredContractClients[i].getClientDetail().getLastName() + ", "
						+ insuredContractClients[i].getClientDetail().getFirstName() + "]";
			} else {
				insuredIdentifier = "[EID " + insuredContractClients[i].getEmployeeIdentification() + "]";
			}
			String messageStart = errorMessageStart + insuredIdentifier + " ";

			validateExistence(insuredContractClients[i].getClientDetail().getGenderCT(), messageStart + "Gender",
					errorMessages);

			// ECK
			// commenting out temporarily for UL
			/*
			validateExistence(insuredContractClients[i].getRatedGenderCT(), messageStart + "Rated Gender",
					errorMessages);
			validateExistence(insuredContractClients[i].getUnderwritingClassCT(), messageStart + "Underwriting Class",
					errorMessages);
			*/
			if (!isValueSet(insuredContractClients[i].getClassCT())) {
				errorMessages.add(messageStart + "Class was not set. Verify the questionnaire setup and responses.");
			}
			validateExistence(insuredContractClients[i].getClientDetail().getBirthDate(), messageStart + "Birth Date",
					errorMessages);
			validateExistence(insuredContractClients[i].getRelationshipToEmployeeCT(),
					messageStart + "Relationship To Employee", errorMessages);
		}
	}

	/**
	 * Validates the existence of the object. If the object is a String, checks to
	 * be sure it has a value (is not an empty string). If an array, checks to make
	 * sure it has contents. If the object does not exist, logs the error message.
	 *
	 * @param object
	 * @param errorFieldName
	 * @param errorMessages
	 * @return
	 */
	private boolean validateExistence(Object object, String errorFieldName, List errorMessages) {
		boolean exists = isValueSet(object);
		if (!exists) {
			errorMessages.add(errorFieldName + " does not exist.");
		}

		return exists;
	}

	/**
	 * Determines if a value was set for an object of arbitrary type
	 *
	 * @param object the object to check for some value
	 * @return True if the instance is a non-empty string, array of non-zero length,
	 *         or otherwise non-null.
	 */
	private boolean isValueSet(Object object) {
		boolean exists = true;

		if (object instanceof String) {
			exists = Util.valueExists(object.toString());
		} else if (object instanceof Object[]) {
			Object[] array = (Object[]) object;

			if (!(array.length > 0)) {
				exists = false;
			}
		} else if (object instanceof EDITBigDecimal) {
			EDITBigDecimal editBigDecimal = (EDITBigDecimal) object;

			exists = Util.valueExists(editBigDecimal);
		} else {
			exists = Util.valueExists(object);
		}

		return exists;
	}

	/**
	 * Puts the contents of the errorMessages list into the responseDocument's
	 * response messages.
	 *
	 * @param errorMessages
	 * @param responseDocument
	 */
	private void logErrorsToResponseDocument(List errorMessages, SEGResponseDocument responseDocument) {
		for (Iterator iterator = errorMessages.iterator(); iterator.hasNext();) {
			String errorMessage = (String) iterator.next();

			responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, errorMessage);
		}
	}

	/**
	 * Sets the contractNumber on the segment. If the contractNumber was supplied in
	 * the request parameters, that number is used. If not, a number is generated.
	 * <p>
	 * NOTE: The number is generated using a sequence number on the Company table.
	 * Company is in the Engine database which means it must have its own hibernate
	 * session to increase the sequence number. There is a possible timing problem
	 * if 2 requests read the sequence number and generated contractNumbers at the
	 * same time . We wait until just before the save of the segment to generate the
	 * contractNumber to reduce possible conflicts.
	 *
	 * @param segment                  Segment whose contractNumber will be set
	 * @param requestParametersElement Element containing the request parameters
	 *                                 (which contain the contractNumber)
	 * @throws EDITCaseException if the contractNumber already exists in persistence
	 */
	private void setContractNumber(Segment segment, Element requestParametersElement) throws EDITCaseException {
		String contractNumber = getContractNumberFromRequestParameters(requestParametersElement);

		// Generate contractNumber
		Company company = Company.findByProductStructurePK(segment.getProductStructureFK());

		if (contractNumber == null) {
			// User did not supply a contract number, generate one
			segment.autoGenerateGroupContractNumber(company);
		} else {
			// User supplied a contract number, use it
			if (Segment.contractNumberExists(contractNumber)) {
				throw new EDITCaseException("Contract number " + contractNumber + " already exists.");
			} else {
				segment.setContractNumber(contractNumber);
			}
		}

		// Set the riders to have the same contractNumber
		Set<Segment> riders = segment.getSegments();

		for (Iterator iterator = riders.iterator(); iterator.hasNext();) {
			Segment rider = (Segment) iterator.next();

			rider.setContractNumber(segment.getContractNumber());
		}
	}

	/**
	 * Checks the validity of the beneficiary allocations. First checks to see if
	 * allocation amounts were set as well as the split equals indicator (they must
	 * pick one or the other). Then checks that the total beneficiary allocation is
	 * either 0 or 1 (0% or 100%) OR the split equally indicator has been set.
	 * <p>
	 * They either don't know the allocation (which is why 0 is allowed) or, if they
	 * entered anything, the total must equal 100%.
	 *
	 * @param segment
	 * @param errorMessages
	 */
	private void validateBeneficiaryAllocations(Segment segment, List errorMessages) {
		ContractClient[] beneficiaryContractClients = segment.getBeneficiaryContractClients();

		List newErrorMessages = ContractClient.validateBeneficiaryAllocations(beneficiaryContractClients);

		if (!newErrorMessages.isEmpty()) {
			Iterator it = newErrorMessages.iterator();

			while (it.hasNext()) {
				errorMessages.add(it.next());
			}
		}
	}

	/**
	 * Gets the beneficiary contract clients from the memberInfoVOsElement and
	 * converts them to ContractClient objects with ClientRole objects.
	 *
	 * @param memberInfoVOsElement Element containing the ContractClient,
	 *                             ClientRole, and ContractClientAllocation
	 *                             information
	 * @return collection of ContractClients with ClientRoles where the
	 *         ClientRole.RoleType is a beneficiary type.
	 */
	private Set<ContractClient> getBeneficiariesAsContractClientObjects(Element memberInfoVOsElement) {
		Set<ContractClient> beneficiaryContractClients = new HashSet<ContractClient>();

		List<Element> contractClientElements = memberInfoVOsElement.selectNodes("//MemberInfoVO/ContractClientVO");

		for (Iterator elementIterator = contractClientElements.iterator(); elementIterator.hasNext();) {
			Element contractClientElement = (Element) elementIterator.next();

			Element clientRoleElement = contractClientElement.element("ClientRoleVO");

			Element contractClientAllocationElement = contractClientElement.element("ContractClientAllocationVO");

			ClientRole clientRole = (ClientRole) SessionHelper.mapToHibernateEntity(ClientRole.class, clientRoleElement,
					ClientRole.DATABASE, true);

			if (clientRole.isBeneficiary()) {
				ContractClient contractClient = (ContractClient) SessionHelper.mapToHibernateEntity(
						ContractClient.class, contractClientElement, ContractClient.DATABASE, true);

				ContractClientAllocation contractClientAllocation = (ContractClientAllocation) SessionHelper
						.mapToHibernateEntity(ContractClientAllocation.class, contractClientAllocationElement,
								ContractClientAllocation.DATABASE, true);

				contractClient.setClientRole(clientRole);

				contractClient.addContractClientAllocation(contractClientAllocation);

				beneficiaryContractClients.add(contractClient);
			}
		}

		return beneficiaryContractClients;
	}

	/**
	 * Validates the the group ContractGroup is active during the contract's
	 * application signed date. The Segment's applicationSignedDate cannot be later
	 * than the group ContractGroup's terminationDate.
	 * <p>
	 * Groups can have future dated termination dates, therefore, this check is
	 * necessary.
	 *
	 * @param groupContractGroup
	 * @param segment
	 * @param errorMessages
	 */
	private void validateGroupIsActive(ContractGroup groupContractGroup, Segment segment, List errorMessages) {
		EDITDate applicationSignedDate = segment.getApplicationSignedDate();

		EDITDate terminationDate = groupContractGroup.getTerminationDate();

		// If the terminationDate is null, the group has not been terminated, no need to
		// check
		// If the applicationSignedDate is null, that's an error (which has been
		// validated in validateSegmentInput), no need to check
		// Only check if both fields are not null
		if ((applicationSignedDate != null) && (terminationDate != null)) {
			if (applicationSignedDate.after(terminationDate)) {
				errorMessages.add("The Application Signed Date [" + applicationSignedDate.getFormattedDate()
						+ "] is later than the Group's Termination Date [" + terminationDate.getFormattedDate() + "]");
			}
		}
	}

	/**
	 * Validates the existence of certain billing information for the given group
	 * ContractGroup. First checks to see if a BillSchedule even exists. If it does,
	 * then validates the existence of values for certain fields. If any of these
	 * does not exist, an error message is added the the errorMessages List.
	 *
	 * @param groupContractGroup
	 * @param errorMessages
	 */
	private void validateBilling(ContractGroup groupContractGroup, List errorMessages) {
		BillSchedule billSchedule = BillSchedule.findBy_BillSchedulePK(groupContractGroup.getBillScheduleFK());

		if ((billSchedule == null)) {
			errorMessages.add("A BillSchedule does not exist for the selected Group ContractGroup ["
					+ groupContractGroup.getContractGroupNumber() + "]");
		} else {
			// A BillSchedule exists, now check for the existence values for certain fields
			validateExistence(billSchedule.getBillingCompanyCT(), "Billing Company", errorMessages);
			validateExistence(billSchedule.getFirstBillDueDate(), "First Bill Due Date", errorMessages);
			validateExistence(billSchedule.getFirstDeductionDate(), "First Deduction Date", errorMessages);
			validateExistence(billSchedule.getDeductionFrequencyCT(), "Deduction Frequency", errorMessages);
			validateExistence(billSchedule.getBillingModeCT(), "Billing Frequency", errorMessages);
		}
	}

	/**
	 * Validates the setting of the DepartmentLocation on the Segment if active
	 * DepartmentLocations are available for the BatchContractSetup
	 *
	 * @param batchContractSetup
	 * @param segment
	 * @param errorMessages
	 */
	private void validateDepartmentLocation(BatchContractSetup batchContractSetup, Segment segment,
			List errorMessages) {
		DepartmentLocation[] departmentLocations = DepartmentLocation
				.findActiveByBatchContractSetupPK(batchContractSetup.getBatchContractSetupPK());

		if (departmentLocations.length > 0) {
			// Active DepartmentLocations exist for this BatchContractSetup, one must be
			// selected for the Segment
			if (segment.getDepartmentLocation() == null) {
				errorMessages.add("A DepartmentLocation must be selected.");
			}
		}
	}

	/**
	 * Validates the Enrollment information. The contract's effective date must be
	 * within the range of valid policy dates in the Enrollment.
	 * <p>
	 * If the Segment.EffectiveDate is null, no validation attempt is made.
	 *
	 * @param batchContractSetup
	 * @param segment
	 * @param errorMessages
	 */
	private void validateEnrollment(BatchContractSetup batchContractSetup, Segment segment, List errorMessages) {
		Enrollment enrollment = Enrollment.findByPK(batchContractSetup.getEnrollmentFK());

		if (enrollment == null) {
			errorMessages.add("An Enrollment must be selected.");
		} else {
			if ((segment.getEffectiveDate() != null) && (!enrollment.isPolicyDateInRange(segment.getEffectiveDate()))) {
				// Contract effective date is not within the range of valid policy dates for the
				// selected Enrollment
				errorMessages.add(
						"Contract effective date is not within the Enrollment's valid policy dates: BeginningPolicyDate ["
								+ enrollment.getBeginningPolicyDate() + "], EndingPolicyDate ["
								+ enrollment.getEndingPolicyDate() + "]");
			}
		}
	}

	/**
	 * Gets the contract number from the request parameters. If the user didn't
	 * supply the contract number, then the element will contain "". This method
	 * will return a null for that situation.
	 *
	 * @param requestParametersElement
	 * @return contract number supplied by the user or null if not supplied
	 */
	private String getContractNumberFromRequestParameters(Element requestParametersElement) {
		Element segmentInformationElement = requestParametersElement.element("SegmentInformationVO");

		String contractNumber = segmentInformationElement.element("ContractNumber").getText();

		if (contractNumber.trim().length() > 0) {
			return contractNumber;
		} else {
			return null;
		}
	}

	public String deleteEnrollment(Long enrollmentPK) throws EDITCaseException {
		BatchContractSetup batchContractSetup = BatchContractSetup.findByEnrollmentPK(enrollmentPK);

		String responseMessage = null;

		if (batchContractSetup == null) {
			try {
				SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
				CaseProductUnderwriting[] caseProductUnderwritings = CaseProductUnderwriting
						.findByEnrollmentFK(enrollmentPK);
				if (caseProductUnderwritings != null) {
					for (int i = 0; i < caseProductUnderwritings.length; i++) {
						CaseProductUnderwriting caseProductUnderwriting = caseProductUnderwritings[i];
						SessionHelper.delete(caseProductUnderwriting, SessionHelper.EDITSOLUTIONS);
					}
				}

				Enrollment enrollment = Enrollment.findByPK(enrollmentPK);
				SessionHelper.delete(enrollment, SessionHelper.EDITSOLUTIONS);

				SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

				responseMessage = "Delete of Enrollment  was successful.";

			} catch (Exception e) {
				System.out.println(e);

				e.printStackTrace();

				SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

				throw new EDITCaseException(e.getMessage());
			} finally {
				SessionHelper.clearSessions();
			}
		} else {
			responseMessage = "Delete not allowed, Enrollment being used by a BatchContractSetup";
		}

		return responseMessage;
	}

	/**
	 * Determines if the the contractClientInformationElement's RoleTypeCT matches
	 * the specified roleTypeCT
	 *
	 * @param roleTypeCT
	 * @param contractClientInformationElement
	 * @return true if the roleTypes match, false otherwise
	 */
	private boolean isRoleTypeOf(String roleTypeCT, Element contractClientInformationElement) {
		String elementRoleTypeCT = contractClientInformationElement.element("RoleTypeCT").getText();

		if (elementRoleTypeCT.equals(roleTypeCT)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Creates a default Payor contractClient when Payor information hasn't been
	 * supplied in the input. Uses the Owner's information to build the Payor.
	 *
	 * @param segment
	 * @param ownerClientDetail
	 * @param ownerContractClientInformationElement
	 * @param ownerRelationshipToEmployeeCT
	 * @param ownerEmployeeIdentification
	 * @param ownerTemplateQuestionnaireResponses
	 * @param beneficiariesEquallySplit
	 * @param contractNumber
	 * @param operator
	 */
	private void createDefaultPayor(Segment segment, ClientDetail ownerClientDetail,
			Element ownerContractClientInformationElement, String ownerRelationshipToEmployeeCT,
			String ownerEmployeeIdentification, List ownerTemplateQuestionnaireResponses,
			boolean beneficiariesEquallySplit, String CBEEquallySplit, String contractNumber, String operator) {
		// Create a Payor contractClientInformationElement that has the same values as
		// the owner's
		Element payorContractClientInformationElement = ownerContractClientInformationElement.createCopy();

		// Change the roleType to Payor
		payorContractClientInformationElement.element("RoleTypeCT").setText(ClientRole.ROLETYPECT_PAYOR);

		buildContractClient(segment, ownerClientDetail, payorContractClientInformationElement,
				ClientRole.ROLETYPECT_PAYOR, ownerRelationshipToEmployeeCT, ownerEmployeeIdentification,
				ownerTemplateQuestionnaireResponses, beneficiariesEquallySplit, CBEEquallySplit, contractNumber,
				operator);
	}

	/**
	 * Creates a ClientAddress with the given information in the
	 * clientAddressElement.
	 *
	 * @param clientAddressElement
	 * @return newly created ClientAddress object
	 */
	private ClientAddress createClientAddress(Element clientAddressElement) {
		ClientAddress clientAddress = null;

		if (clientAddressElement != null) {
			clientAddress = (ClientAddress) SessionHelper.mapToHibernateEntity(ClientAddress.class,
					clientAddressElement, SessionHelper.EDITSOLUTIONS, true);

			clientAddress.setAddressDefaults(clientAddress.getClientAddressPK());
			clientAddress.setAddressTypeCT(ClientAddress.CLIENT_PRIMARY_ADDRESS);
		}

		return clientAddress;
	}

	/**
	 * Creates a ClientDetail with the given information in the clientDetailElement.
	 * Checks to ensure that the taxID and client do not already exist.
	 * Additionally, it associates the specified TaxInformation as well as a default
	 *
	 * @param clientDetailElement
	 * @return newly created ClientDetail
	 * @throws EDITException if the taxID or client already existed
	 */
	private ClientDetail createClientDetail(Element clientDetailElement, Element taxInformationElement)
			throws EDITException {
		checkForExistingTaxID(clientDetailElement, taxInformationElement); // throws error if taxID already exists

		// No errors, go ahead and create it
		ClientDetail clientDetail = (ClientDetail) SessionHelper.mapToHibernateEntity(ClientDetail.class,
				clientDetailElement, SessionHelper.EDITSOLUTIONS, true);

		// Remove any dashes that may have been added by the user
		clientDetail.stripTaxIdentificationOfDashes();

		clientDetail.setDefaults();

		TaxInformation taxInformation = (TaxInformation) SessionHelper.mapToHibernateEntity(TaxInformation.class,
				taxInformationElement, SessionHelper.EDITSOLUTIONS, true);

		clientDetail.setDefaultTaxes(taxInformation);

		return clientDetail;
	}

	/**
	 * Checks to see if the client already exists based on taxID. This method gets
	 * the taxID from the element and throws an error if the client is found.
	 *
	 * @param clientDetailElement Element containing the taxID
	 * @throws EDITException
	 */
	private void checkForExistingTaxID(Element clientDetailElement, Element taxInformationElement)
			throws EDITException {
		String taxID = XMLUtil.getText(clientDetailElement.element("TaxIdentification"));

		if (taxID != null) {
			// Remove any dashes that may have been added by the user or auto generated by
			// the user interface
			taxID = Util.stripString(taxID, ClientDetail.TAXID_DASH);
		}

		String trustTypeCT = XMLUtil.getText(clientDetailElement.element("TrustTypeCT"));

		String taxIdTypeCT = XMLUtil.getText(taxInformationElement.element("TaxIdTypeCT"));

		ClientDetail clientDetail = checkForExistingTaxIDForAGivenTrustType(taxID, trustTypeCT, taxIdTypeCT);

		if (clientDetail != null) {
			throw new EDITException("TaxID " + taxID + " already exists");
		}
	}

	/**
	 * Check to see if the client already exists. Checks based on lastname,
	 * firstname, and birthDate. If the client exists, an error is thrown. If it
	 * doesn't exist or more than 1 match is found, there is no error. If the
	 * birthDate is not supplied, there is no error.
	 *
	 * @param clientDetailElement Element containing the ClientDetail object
	 * @throws EDITException if the client exists and there is only 1.
	 */
	private void checkForExistingClient(Element clientDetailElement) throws EDITException {
		String lastName = XMLUtil.getText(clientDetailElement.element("LastName"));
		String firstName = XMLUtil.getText(clientDetailElement.element("FirstName"));
		String birthDateString = XMLUtil.getText(clientDetailElement.element("BirthDate"));

		EDITDate birthDate = DateTimeUtil.getEDITDateFromMMDDYYYY(birthDateString);

		Long clientDetailPK = checkForExistingClient(lastName, firstName, birthDate);

		// If the client exists (and only one exists), throw an error.
		// If more than one exists, let it go. A new client will end up getting created
		// which is what we want
		// (yes, another one because we don't know which one to use if there is more
		// than one)
		if (clientDetailPK != null) {
			// One client exists, throw an error
			throw new EDITException("Client already exists: LastName = " + lastName + ", FirstName = " + firstName
					+ ", BirthDate = " + birthDateString);
		}
	}

	/**
	 * Validate the contents of the BatchContractSetup before saving it. The
	 * validation is actually done in a script. Any error messages are added to the
	 * response document.
	 *
	 * @param batchContractSetup
	 * @return true if the batchContractSetup validated successfully, false
	 *         otherwise
	 */
	private boolean validateBatchContractSetup(BatchContractSetup batchContractSetup,
			SEGResponseDocument responseDocument) {
		boolean validationOK = true;

		BatchContractSetupDocument batchContractSetupDocument = new BatchContractSetupDocument(batchContractSetup);

		Calculator calculator = new CalculatorComponent();

		String processName = "BatchContractSave";
		String event = "*";
		String eventType = "*";
		EDITDate effectiveDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
		boolean abortOnHardValidationError = false;

		Long productKey = batchContractSetup.getFilteredProduct().getProductStructure().getProductStructurePK();

		SPOutput spOutput = null;

		try {
			spOutput = calculator.processScriptWithDocument(batchContractSetupDocument, processName, event, eventType,
					effectiveDate.getFormattedDate(), productKey, abortOnHardValidationError);
		} catch (SPException e) {
			validationOK = false;

			responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());
		}

		// It got this far, get any messages from the script and put them into the
		// responseDocument
		List<ValidationVO> validationVOs = spOutput.getHardEdits();

		if (validationVOs.size() > 0) {
			validationOK = false;

			convertValidationVOsToSEGResponseMessages(validationVOs, responseDocument);
		}

		return validationOK;
	}

	/**
	 * Converts the contents of a list of ValidationVOs to ResponseMessages in a
	 * SEGResponseDocument.
	 *
	 * @param validationVOs
	 * @param responseDocument
	 */
	private void convertValidationVOsToSEGResponseMessages(List<ValidationVO> validationVOs,
			SEGResponseDocument responseDocument) {
		for (Iterator iterator = validationVOs.iterator(); iterator.hasNext();) {
			ValidationVO validationVO = (ValidationVO) iterator.next();

//            validationVO.getInstructionName();  //Validatestringinequality
//            validationVO.getMessage();  // Enrollment Method required
//            validationVO.getSeverity();     // H
//            validationVO.getReporting();    // Y
//            validationVO.getStack();        // actual:[#NULL] + expected:[#NULL] express:[!=]

			String message = validationVO.getInstructionName() + ": " + validationVO.getMessage() + " - "
					+ validationVO.getStack();

			String messageType = null;

			if (validationVO.getSeverity().equals("H")) {
				messageType = SEGResponseDocument.MESSAGE_TYPE_ERROR;
			} else if (validationVO.getSeverity().equals("W")) {
				messageType = SEGResponseDocument.MESSAGE_TYPE_WARNING;
			}

			responseDocument.addResponseMessage(messageType, message);
		}
	}

	/**
	 * Overloaded method to accept an array of ValidationVO objects. It simply
	 * converts the array to a List and calls the list method.
	 *
	 * @param validationVOs
	 * @param responseDocument
	 */
	private void convertValidationVOsToSEGResponseMessages(ValidationVO[] validationVOs,
			SEGResponseDocument responseDocument) {
		List<ValidationVO> validationVOList = new ArrayList<ValidationVO>();

		for (int i = 0; i < validationVOs.length; i++) {
			ValidationVO validationVO = validationVOs[i];

			validationVOList.add(validationVO);
		}

		convertValidationVOsToSEGResponseMessages(validationVOList, responseDocument);
	}

	public void deleteProjectedBusiness(Long projectedBusinessPK) throws EDITCaseException {
		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			ProjectedBusinessByMonth projectedBusiness = ProjectedBusinessByMonth.findByPK(projectedBusinessPK);

			SessionHelper.delete(projectedBusiness, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	public void saveEnrollmentState(EnrollmentState enrollmentState, Long enrollmentPK) throws EDITCaseException {
		Enrollment enrollment = Enrollment.findByPK(enrollmentPK);

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			enrollmentState.setEnrollment(enrollment);

			SessionHelper.saveOrUpdate(enrollmentState, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	public String deleteEnrollmentState(Long enrollmentStatePK) throws EDITCaseException {
		String responseMessage = null;

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			EnrollmentState enrollmentState = EnrollmentState.findByPK(enrollmentStatePK);
			SessionHelper.delete(enrollmentState, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);

			responseMessage = "Delete of Enrollment State was successful.";

		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}

		return responseMessage;
	}

	/**
	 * @param regionCT
	 * @param effectiveDate
	 * @param terminationDate
	 * @param enrollmentPK
	 * @param agentPK
	 * @param clientDetailPK
	 * @param roleTypeCT
	 * @param referenceID
	 * @see #saveLeadServiceAgent(String, EDITDate, EDITDate, Long, Long, String,
	 *      String)
	 */
	public void saveEnrollmentLeadServiceAgent(String regionCT, EDITDate effectiveDate, EDITDate terminationDate,
			Long enrollmentPK, Long agentPK, Long clientDetailPK, String roleTypeCT, String referenceID)
			throws EDITCaseException {
		try {
			EnrollmentLeadServiceAgent enrollmentLeadServiceAgent = EnrollmentLeadServiceAgent.build_v1(regionCT,
					effectiveDate, terminationDate);

			SessionHelper.beginTransaction(enrollmentLeadServiceAgent.getDatabase());

			Enrollment enrollment = Enrollment.findByPK(enrollmentPK);

			Agent agent = Agent.findBy_PK(agentPK);

			ClientDetail clientDetail = ClientDetail.findByPK(clientDetailPK);

			enrollment.saveEnrollmentLeadServiceAgent(enrollmentLeadServiceAgent, agent, clientDetail, roleTypeCT,
					referenceID);

			// This entity is always new.
			SessionHelper.save(enrollmentLeadServiceAgent, enrollmentLeadServiceAgent.getDatabase());

			// This entity may be new or just an update.
			SessionHelper.saveOrUpdate(enrollmentLeadServiceAgent.getClientRole(),
					enrollmentLeadServiceAgent.getDatabase());

			SessionHelper.commitTransaction(enrollmentLeadServiceAgent.getDatabase());
		} catch (EDITCaseException e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw e;
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	/**
	 * @param enrollmentLeadServiceAgentPK
	 * @see #deleteEnrollmentLeadServiceAgent(Long)
	 */
	public void deleteEnrollmentLeadServiceAgent(Long enrollmentLeadServiceAgentPK) {
		EnrollmentLeadServiceAgent enrollmentLeadServiceAgent = EnrollmentLeadServiceAgent
				.findByPK(enrollmentLeadServiceAgentPK);

		SessionHelper.beginTransaction(enrollmentLeadServiceAgent.getDatabase());

		SessionHelper.delete(enrollmentLeadServiceAgent, enrollmentLeadServiceAgent.getDatabase());

		SessionHelper.commitTransaction(enrollmentLeadServiceAgent.getDatabase());
	}

	/**
	 * @param enrollmentLeadServiceAgentPK
	 * @param regionCT
	 * @param effectiveDate
	 * @param terminationDate
	 * @see #updateEnrollmentLeadServiceAgent(Long, String, EDITDate, EDITDate)
	 */
	public void updateEnrollmentLeadServiceAgent(Long enrollmentLeadServiceAgentPK, String regionCT,
			EDITDate effectiveDate, EDITDate terminationDate) throws EDITCaseException {
		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			EnrollmentLeadServiceAgent enrollmentLeadServiceAgent = EnrollmentLeadServiceAgent
					.findByPK(enrollmentLeadServiceAgentPK);

			Enrollment enrollment = enrollmentLeadServiceAgent.getEnrollment();

			enrollment.updateEnrollmentLeadServiceAgent(enrollmentLeadServiceAgent, regionCT, effectiveDate,
					terminationDate);

			SessionHelper.saveOrUpdate(enrollmentLeadServiceAgent, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (EDITCaseException e) {
			System.out.println(e);

			e.printStackTrace();

			throw e;
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			throw new EDITCaseException(e.getMessage());
		}
	}

	/**
	 * Validate the contents of ClientDetail (and its children) before saving it.
	 * The validation is actually done in a script. Any error messages are added to
	 * the response document.
	 *
	 * @param clientDetail     object to be validated
	 * @param responseDocument document where validation messages will be added
	 * @return true if the clientDetail validated successfully, false otherwise
	 */
	private boolean validateClient(ClientDetail clientDetail, SEGResponseDocument responseDocument) {
		boolean validationOK = true;

		SPOutput spOutput = null;

		try {
			spOutput = clientDetail.validateClient();
		} catch (Exception e) {
			validationOK = false;

			responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, e.getMessage());
		}

		// It got this far, get any messages from the script and put them into the
		// responseDocument
		List<ValidationVO> validationVOs = spOutput.getHardEdits();

		if (validationVOs.size() > 0) {
			validationOK = false;

			convertValidationVOsToSEGResponseMessages(validationVOs, responseDocument);
		}

		return validationOK;
	}

	public String deleteCaseAndChildren(Long contractGroupPK) throws EDITCaseException {
		String message = "Case Successfully Deleted";

		ContractGroup contractGroup = ContractGroup.findByPK(contractGroupPK);

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			SessionHelper.delete(contractGroup, SessionHelper.EDITSOLUTIONS);

			ClientRole clientRole = contractGroup.getClientRole();

			SessionHelper.delete(clientRole, SessionHelper.EDITSOLUTIONS);

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		}

		return message;
	}

	public void saveCaseSetup(CaseSetup caseSetup, Long contractGroupPK) throws EDITCaseException {
		try {
			ContractGroup contractGroup = ContractGroup.findByPK(contractGroupPK);
			caseSetup.setContractGroup(contractGroup);

			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);

			caseSetup.hSave();

			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}

	public void deleteCaseSetup(Long caseSetupPK) throws EDITCaseException {
		CaseSetup caseSetup = CaseSetup.findByCaseSetupPK(new Long(caseSetupPK));

		try {
			SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
			caseSetup.hDelete();
			SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

			throw new EDITCaseException(e.getMessage());
		} finally {
			SessionHelper.clearSessions();
		}
	}
}
