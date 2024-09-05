/*
 * User: sdorman
 * Date: Jul 21, 2004
 * Time: 12:32:02 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import client.ClientDetail;
import client.ClientAddress;
import contract.dm.dao.ContractClientDAO;
import edit.common.*;
import edit.common.exceptions.EDITContractException;
import edit.common.exceptions.EDITException;
import edit.common.vo.ClientRoleVO;
import edit.common.vo.ContractClientAllocationVO;
import edit.common.vo.ContractClientVO;
import edit.common.vo.VOObject;
import edit.common.vo.WithholdingVO;
import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityI;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import event.ClientSetup;
import fission.utility.Util;
import java.io.Serializable;
import java.util.*;
import org.hibernate.Session;
import role.ClientRole;
import staging.IStaging;
import staging.StagingContext;

public class ContractClient extends HibernateEntity implements CRUDEntity,
		IStaging {
	public static final String RATEDGENDERCT_MALE = "Male";
	public static final String RATEDGENDERCT_FEMALE = "Female";
	public static final String RATEDGENDERCT_UNISEX = "Unisex";
	public static final String CLASSCT_SMOKER = "smoker";
	public static final String CLASSCT_NONSMOKER = "nonsmoker";
	public static final String CLASSCT_UNISMOKER = "unismoker";
	public static final String ACTIVE_STATUS = "Active";
	public static final String DEATH_STATUS = "Death";
	public static final String DEATH_PENDING_STATUS = "DeathPending";
	public static final String SPOUSAL_TERMINATION_REASON = "SpousalContinuation";
	public static final String OVERRIDE_STATUS_PENDING = "P";

	public static final String RELATIONSHIP_TO_EE_EMPLOYEE = "EE";
	public static final String RELATIONSHIP_TO_EE_SPOUSE = "SP";
	public static final String RELATIONHIP_TO_EE_CHILD = "CD";
	public static final String RELATIONSHIP_TO_EE_DEPENDENT = "DP";
	public static final String RELATIONSHIP_TO_EE_GRANDPARENT = "GP";
	public static final String RELATIONSHIP_TO_EE_PARENT = "PA";
	public static final String RELATIONSHIP_TO_EE_OTHER = "OR";

	private CRUDEntityI crudEntityImpl;
	private ContractClientVO contractClientVO;
	public String classCT = null;
	public String tableRatingCT = null;
	public String originalClassCT = null;
	private Segment segment;
	private ClientRole clientRole;
	private String changeHistoryEffDate;
	private Set<Withholding> withholdings = new HashSet<Withholding>();

	// private Long contractClientPK;
	private Set<ClientSetup> clientSetups = new HashSet<ClientSetup>();
	private Set<ContractClientAllocation> contractClientAllocations = new HashSet<ContractClientAllocation>();
	private Set<QuestionnaireResponse> questionnaireResponses = new HashSet<QuestionnaireResponse>();

	/**
	 * Target database to be used for lookups, etc.
	 */
	public static String DATABASE = SessionHelper.EDITSOLUTIONS;

	/**
	 * Instantiates a ContractClient entity with a default ContractClientVO.
	 */
	public ContractClient() {
		init();
	}

	/**
	 * Instantiates a ContractClient entity with a supplied ContractClientVO.
	 * 
	 * @param contractClientVO
	 */
	public ContractClient(ContractClientVO contractClientVO) {
		init();

		this.contractClientVO = contractClientVO;
	}

	/**
	 * Instantiates a ContractClient entity with a ContractClientVO retrieved
	 * from persistence.
	 * 
	 * @param contractClientPK
	 */
	public ContractClient(long contractClientPK) {
		init();

		crudEntityImpl.load(this, contractClientPK,
				ConnectionFactory.EDITSOLUTIONS_POOL);
	}

	/**
	 * Instantiates a ContractClient entity with a supplied ContractClientVO.
	 * 
	 * @param contractClientVO
	 */
	public ContractClient(ContractClientVO contractClientVO, Segment segment) {
		init();

		this.contractClientVO = contractClientVO;
		this.segment = segment;
	}

	public Long getClientRoleFK() {
		return SessionHelper.getPKValue(contractClientVO.getClientRoleFK());
	}

	// -- long getClientRoleFK()
	public Long getSegmentFK() {
		return SessionHelper.getPKValue(contractClientVO.getSegmentFK());
	}

	// -- long getSegmentFK()
	public void setClientRoleFK(Long clientRoleFK) {
		contractClientVO
				.setClientRoleFK(SessionHelper.getPKValue(clientRoleFK));
	}

	// -- void setClientRoleFK(long)
	public void setSegmentFK(Long segmentFK) {
		contractClientVO.setSegmentFK(SessionHelper.getPKValue(segmentFK));
	}

	public Set<ClientSetup> getClientSetups() {
		return clientSetups;
	}

	public void setClientSetups(Set clientSetups) {
		this.clientSetups = clientSetups;
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public Set<Withholding> getWithholdings() {
		return withholdings;
	}

	/**
	 * Setter.
	 * 
	 * @param withholdings
	 */
	public void setWithholdings(Set<Withholding> withholdings) {
		this.withholdings = withholdings;
	}

	/**
	 * Setter.
	 * 
	 * @param contractClientPK
	 */
	public void setContractClientPK(Long contractClientPK) {
		this.contractClientVO.setContractClientPK(SessionHelper
				.getPKValue(contractClientPK));
	}

	/**
	 * Life contract must generate transactions for Rating change
	 * 
	 * @param change
	 */
	public void checkForComplexChange(Change change) {
		ComplexChange complexChange = new ComplexChange();

		String fieldName = change.getFieldName();

		if (fieldName.equalsIgnoreCase("ClassCT")
				|| fieldName.equalsIgnoreCase("TableRatingCT")) {
			complexChange.createComplexChangeForRating(this, fieldName, change,
					segment, segment.getOperator());
		} else if (fieldName.equalsIgnoreCase("TerminationReasonCT")) {
			complexChange.createComplexChangeForOwnershipChange(this,
					fieldName, change, segment, segment.getOperator());
		}

		contractClientVO.setPendingClassChangeInd("Y");
	}

	/**
	 * @see CRUDEntity#cloneCRUDEntity()
	 * @return
	 */
	public CRUDEntity cloneCRUDEntity() {
		return crudEntityImpl.cloneCRUDEntity(this);
	}

	/**
	 * @see CRUDEntity#delete()
	 * @throws Throwable
	 */
	public void delete() throws Throwable {
		try {
			crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
		} catch (Throwable t) {
			System.out.println(t);

			t.printStackTrace();
		}
	}

	// -- java.lang.String getRatedGenderCT()

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public String getClassCT() {
		return contractClientVO.getClassCT();
	}

	public String getOriginalClassCT() {
		return contractClientVO.getOriginalClassCT();
	}

	/**
	 * Returns the associated ClientDetail.
	 * 
	 * @return
	 */
	public ClientDetail getClientDetail() {
		return this.getClientRole().getClientDetail();
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public ClientRole getClientRole() {
		return clientRole;
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public Long getContractClientPK() {
		return SessionHelper.getPKValue(contractClientVO.getContractClientPK());
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public String getCorrespondenceAddressTypeCT() {
		return contractClientVO.getCorrespondenceAddressTypeCT();
	}

	// -- java.lang.String getCorrespondenceAddressTypeCT()

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public String getDisbursementAddressTypeCT() {
		return contractClientVO.getDisbursementAddressTypeCT();
	}

	// -- java.lang.String getDisbursementAddressTypeCT()

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public EDITDate getEffectiveDate() {
		return SessionHelper.getEDITDate(contractClientVO.getEffectiveDate());
	}

	// -- java.lang.String getEffectiveDate()

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public EDITBigDecimal getFlatExtra() {
		return SessionHelper.getEDITBigDecimal(contractClientVO.getFlatExtra());
	}

	// -- java.math.BigDecimal getFlatExtra()

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public int getFlatExtraAge() {
		return contractClientVO.getFlatExtraAge();
	}

	// -- int getFlatExtraAge()

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public int getFlatExtraDur() {
		return contractClientVO.getFlatExtraDur();
	}

	// -- int getFlatExtraDur()

	/**
	 * Determine the insured contract client
	 * 
	 * @return Insured contract client
	 */
	public static ContractClient getInsuredContractClient(
			ContractClient[] contractClients) {
		ContractClient insuredContractClient = null;

		for (int i = 0; i < contractClients.length; i++) {
			if (contractClients[i].getRoleType().equalsIgnoreCase("Insured")) {
				insuredContractClient = contractClients[i];

				break;
			}
		}

		return insuredContractClient;
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public int getIssueAge() {
		return contractClientVO.getIssueAge();
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public String getOverrideStatus() {
		return contractClientVO.getOverrideStatus();
	}

	// -- java.lang.String getOverrideStatus()

	/**
	 * @see CRUDEntity#getPK()
	 * @return
	 */
	public long getPK() {
		return contractClientVO.getContractClientPK();
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public String getPayorOfCT() {
		return contractClientVO.getPayorOfCT();
	}

	// -- java.lang.String getPayorOfCT()

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public String getPendingClassChangeInd() {
		return contractClientVO.getPendingClassChangeInd();
	}

	// -- java.lang.String getPendingClassChangeInd()

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public EDITBigDecimal getPercentExtra() {
		return SessionHelper.getEDITBigDecimal(contractClientVO
				.getPercentExtra());
	}

	// -- java.math.BigDecimal getPercentExtra()

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public int getPercentExtraAge() {
		return contractClientVO.getPercentExtraAge();
	}

	// -- int getPercentExtraAge()

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public int getPercentExtraDur() {
		return contractClientVO.getPercentExtraDur();
	}

	// -- int getPercentExtraDur()

	/**
	 * The associated ContratClientAllocation. There can be 0 or 1.
	 * 
	 * @return
	 */
	public ContractClientAllocation getPrimaryContractClientAllocation() {
		ContractClientAllocation contractClientAllocation = ContractClientAllocation
				.findBy_ContractClientPK_OverrideStatus(getPK(), "P");

		return contractClientAllocation;
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public String getRatedGenderCT() {
		return contractClientVO.getRatedGenderCT();
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public String getRelationshipToInsuredCT() {
		return contractClientVO.getRelationshipToInsuredCT();
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public String getBeneRelationshipToInsured() {
		return contractClientVO.getBeneRelationshipToInsured();
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public String getRelationshipToEmployeeCT() {
		return contractClientVO.getRelationshipToEmployeeCT();
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public String getAuthorizedSignatureCT() {
		return contractClientVO.getAuthorizedSignatureCT();
	}

	/**
	 * Returns the type of role for this ContractClient - allows for Crud or
	 * Hiberate formats
	 * 
	 * @return roleTypeCT of the ContractClient's ClientRole
	 */
	public String getRoleType() {
		ClientRole clientRole = null;
		long clientRoleFK = this.contractClientVO.getClientRoleFK();

		if (clientRoleFK != 0) {
			clientRole = new ClientRole(this.contractClientVO.getClientRoleFK());
			contractClientVO
					.setParentVO(ClientRoleVO.class, clientRole.getVO());
		} else {
			clientRole = this.getClientRole();
		}

		return clientRole.getRoleTypeCT();
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public Segment getSegment() {
		return segment;
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public String getTableRatingCT() {
		return contractClientVO.getTableRatingCT();
	}

	// -- java.lang.String getTableRatingCT()

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public String getTelephoneAuthorizationCT() {
		return contractClientVO.getTelephoneAuthorizationCT();
	}

	// -- java.lang.String getTelephoneAuthorizationCT()

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public EDITDate getTerminationDate() {
		return SessionHelper.getEDITDate(contractClientVO.getTerminationDate());
	}

	// -- java.lang.String getTerminationDate()

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public String getUnderwritingClassCT() {
		return contractClientVO.getUnderwritingClassCT();
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public String getEmployeeIdentification() {
		return contractClientVO.getEmployeeIdentification();
	}

	/**
	 * Setter.
	 */
	public void setEmployeeIdentification(String employeeIdentification) {
		contractClientVO.setEmployeeIdentification(employeeIdentification);
	}

	/**
	 * Setter.
	 * 
	 * @param maintDateTime
	 */
	public void setMaintDateTime(EDITDateTime maintDateTime) {
		this.contractClientVO.setMaintDateTime(SessionHelper
				.getEDITDateTime(maintDateTime));
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public EDITDateTime getMaintDateTime() {
		return SessionHelper.getEDITDateTime(this.contractClientVO
				.getMaintDateTime());
	}

	/**
	 * Setter.
	 * 
	 * @param operator
	 */
	public void setOperator(String operator) {
		this.contractClientVO.setOperator(operator);
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public String getOperator() {
		return this.contractClientVO.getOperator();
	}

	/**
	 * @see CRUDEntity#getVO()
	 * @return
	 */
	public VOObject getVO() {
		return contractClientVO;
	}

	// -- int getIssueAge()

	/**
	 * True if the terminationDate is > System date.
	 * 
	 * @return
	 */
	public boolean isActive() {
		boolean isActive = true;

		EDITDate terminationDate = new EDITDate(
				contractClientVO.getTerminationDate());

		EDITDate systemDate = new EDITDate();

		if (terminationDate.before(systemDate)) {
			isActive = false;
		}

		return isActive;
	}

	/**
	 * @see CRUDEntity#isNew()
	 * @return
	 */
	public boolean isNew() {
		return crudEntityImpl.isNew(this);
	}

	/**
	 * Determines if the roleType is Payor
	 * 
	 * @return true if Payor, false otherwise
	 */
	public boolean isPayor() {
		String roleType = this.getRoleType();

		if (roleType.equalsIgnoreCase(ClientRole.ROLETYPECT_PAYOR)) {
			return true;
		}

		return false;
	}

	/**
	 * Determines if the roleType is Owner
	 * 
	 * @return true if Owner, false otherwise
	 */
	public boolean isOwner() {
		String roleType = this.getRoleType();

		if (roleType.equalsIgnoreCase(ClientRole.ROLETYPECT_OWNER)) {
			return true;
		}

		return false;
	}

	public void setClientRoleFK(long clientRoleFK) {
		this.contractClientVO.setClientRoleFK(clientRoleFK);
	}

	/**
	 * @see CRUDEntity#save()
	 * @throws EDITContractException
	 */
	public void saveForHibernate() throws EDITContractException {
		try {
			hSave();
		} catch (Throwable t) {
			System.out.println(t);
			t.printStackTrace();
			throw new RuntimeException(t);
		}
	}

	/**
	 * @see CRUDEntity#save()
	 * @throws EDITContractException
	 */
	public void save() throws EDITContractException {
		try {
			if (segment == null) {
				segment = new Segment(
						((ContractClientVO) this.getVO()).getSegmentFK());
			}

			String segmentStatus = segment.getStatus();
			boolean saveChanges = segment.getSaveChangeHistory();
			boolean updateDone = false;

			if (segmentStatus.equalsIgnoreCase(ACTIVE_STATUS) && saveChanges) {
				updateDone = checkForNonFinancialChanges();
			}

			if (!updateDone) {
				crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL,
						false);
			}

			if (contractClientVO.getContractClientAllocationVOCount() > 0) {
				ContractClientAllocationVO[] contractClientAllocationVOs = contractClientVO
						.getContractClientAllocationVO();

				for (int i = 0; i < contractClientAllocationVOs.length; i++) {
					contractClientAllocationVOs[i]
							.setContractClientFK(contractClientVO
									.getContractClientPK());

					ContractClientAllocation contractClientAllocation = new ContractClientAllocation(
							contractClientAllocationVOs[i]);
					contractClientAllocation.save();
				}
			}

			if (contractClientVO.getWithholdingVOCount() > 0) {
				WithholdingVO[] withholdingVOs = contractClientVO
						.getWithholdingVO();

				for (int i = 0; i < withholdingVOs.length; i++) {
					withholdingVOs[i].setContractClientFK(contractClientVO
							.getContractClientPK());

					Withholding withholding = new Withholding(withholdingVOs[i]);
					withholding.save();
				}
			}
		} catch (Throwable t) {
			System.out.println(t);
			t.printStackTrace();
			throw new RuntimeException(t);
		}
	}

	/**
	 * Setter.
	 */
	public void setClassCT(String classCT) {
		contractClientVO.setClassCT(classCT);
	}

	public void setOriginalClassCT(String originalClassCT) {
		contractClientVO.setOriginalClassCT(originalClassCT);
	}

	// -- void setClassCT(java.lang.String)

	/**
	 * Setter.
	 * 
	 * @param clientRole
	 */
	public void setClientRole(ClientRole clientRole) {
		this.clientRole = clientRole;
	}

	/**
	 * Setter.
	 */
	public void setCorrespondenceAddressTypeCT(
			String correspondenceAddressTypeCT) {
		contractClientVO
				.setCorrespondenceAddressTypeCT(correspondenceAddressTypeCT);
	}

	// -- void setCorrespondenceAddressTypeCT(java.lang.String)

	/**
	 * Setter.
	 */
	public void setDisbursementAddressTypeCT(String disbursementAddressTypeCT) {
		contractClientVO
				.setDisbursementAddressTypeCT(disbursementAddressTypeCT);
	}

	// -- void setDisbursementAddressTypeCT(java.lang.String)

	/**
	 * Setter.
	 */
	public void setEffectiveDate(EDITDate effectiveDate) {
		contractClientVO.setEffectiveDate(SessionHelper
				.getEDITDate(effectiveDate));
	}

	// -- void setEffectiveDate(java.lang.String)

	/**
	 * Setter.
	 */
	public void setFlatExtra(EDITBigDecimal flatExtra) {
		contractClientVO.setFlatExtra(SessionHelper
				.getEDITBigDecimal(flatExtra));
	}

	// -- void setFlatExtra(java.math.BigDecimal)

	/**
	 * Setter.
	 */
	public void setFlatExtraAge(int flatExtraAge) {
		contractClientVO.setFlatExtraAge(flatExtraAge);
	}

	// -- void setFlatExtraAge(int)

	/**
	 * Setter.
	 */
	public void setFlatExtraDur(int flatExtraDur) {
		contractClientVO.setFlatExtraDur(flatExtraDur);
	}

	// -- void setFlatExtraDur(int)

	/**
	 * Setter.
	 */
	public void setIssueAge(int issueAge) {
		contractClientVO.setIssueAge(issueAge);
	}

	// -- void setIssueAge(int)

	/**
	 * Setter.
	 */
	public void setOverrideStatus(String overrideStatus) {
		contractClientVO.setOverrideStatus(overrideStatus);
	}

	// -- void setOverrideStatus(java.lang.String)

	/**
	 * Setter.
	 */
	public void setPayorOfCT(String payorOfCT) {
		contractClientVO.setPayorOfCT(payorOfCT);
	}

	// -- void setPayorOfCT(java.lang.String)

	/**
	 * Setter.
	 */
	public void setPendingClassChangeInd(String pendingClassChangeInd) {
		contractClientVO.setPendingClassChangeInd(pendingClassChangeInd);
	}

	// -- void setPendingClassChangeInd(java.lang.String)

	/**
	 * Setter.
	 */
	public void setPercentExtra(EDITBigDecimal percentExtra) {
		contractClientVO.setPercentExtra(SessionHelper
				.getEDITBigDecimal(percentExtra));
	}

	// -- void setPercentExtra(java.math.BigDecimal)

	/**
	 * Setter.
	 */
	public void setPercentExtraAge(int percentExtraAge) {
		contractClientVO.setPercentExtraAge(percentExtraAge);
	}

	// -- void setPercentExtraAge(int)

	/**
	 * Setter.
	 */
	public void setPercentExtraDur(int percentExtraDur) {
		contractClientVO.setPercentExtraDur(percentExtraDur);
	}

	// -- void setPercentExtraDur(int)

	/**
	 * Setter.
	 */
	public void setRatedGenderCT(String ratedGenderCT) {
		contractClientVO.setRatedGenderCT(ratedGenderCT);
	}

	// -- void setRatedGenderCT(java.lang.String)

	/**
	 * Setter.
	 */
	public void setRelationshipToInsuredCT(String relationshipToInsuredCT) {
		contractClientVO.setRelationshipToInsuredCT(relationshipToInsuredCT);
	}

	/**
	 * Setter.
	 */
	public void setBeneRelationshipToInsured(String beneRelationshipToInsured) {
		contractClientVO
				.setBeneRelationshipToInsured(beneRelationshipToInsured);
	}

	/**
	 * Setter.
	 * 
	 * @param relationshipToEmployeeCT
	 */
	public void setRelationshipToEmployeeCT(String relationshipToEmployeeCT) {
		contractClientVO.setRelationshipToEmployeeCT(relationshipToEmployeeCT);
	}

	/**
	 * Setter.
	 * 
	 * @param authorizedSignatureCT
	 */
	public void setAuthorizedSignatureCT(String authorizedSignatureCT) {
		contractClientVO.setAuthorizedSignatureCT(authorizedSignatureCT);
	}

	/**
	 * Setter.
	 * 
	 * @param segment
	 */
	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	/**
	 * Setter.
	 */
	public void setTableRatingCT(String tableRatingCT) {
		contractClientVO.setTableRatingCT(tableRatingCT);
	}

	// -- void setTableRatingCT(java.lang.String)

	/**
	 * Setter.
	 */
	public void setTelephoneAuthorizationCT(String telephoneAuthorizationCT) {
		contractClientVO.setTelephoneAuthorizationCT(telephoneAuthorizationCT);
	}

	// -- void setTelephoneAuthorizationCT(java.lang.String)

	/**
	 * Setter.
	 */
	public void setTerminationDate(EDITDate terminationDate) {
		contractClientVO.setTerminationDate(SessionHelper
				.getEDITDate(terminationDate));
	}

	// -- void setTerminationDate(java.lang.String)

	/**
	 * Setter.
	 */
	public void setTerminationReasonCT(String terminationReason) {
		contractClientVO.setTerminationReasonCT(terminationReason);
	}

	// -- void setTerminationReasonCT(java.lang.String)

	/**
	 * Setter.
	 */
	public void setUnderwritingClassCT(String underwritingClassCT) {
		contractClientVO.setUnderwritingClassCT(underwritingClassCT);
	}

	// -- void setUnderwritingClassCT(java.lang.String)

	/**
	 * @param voObject
	 */
	public void setVO(VOObject voObject) {
		this.contractClientVO = (ContractClientVO) voObject;
	}

	/**
	 * Adds a ContractClientAllocation to the set of children
	 * 
	 * @param contractClientAllocation
	 */
	public void addContractClientAllocation(
			ContractClientAllocation contractClientAllocation) {
		this.getContractClientAllocations().add(contractClientAllocation);

		contractClientAllocation.setContractClient(this);

		SessionHelper.saveOrUpdate(contractClientAllocation,
				ContractClient.DATABASE);
	}

	/**
	 * Removes a ContractClientAllocation from the set of children
	 * 
	 * @param contractClientAllocation
	 */
	public void removeContractClientAllocation(
			ContractClientAllocation contractClientAllocation) {
		this.getContractClientAllocations().remove(contractClientAllocation);

		contractClientAllocation.setContractClient(null);

		SessionHelper.saveOrUpdate(contractClientAllocation,
				ContractClient.DATABASE);
	}

	/**
	 * Returns the associated ContractClientAllocation, if any.
	 * 
	 * @return
	 */
	public ContractClientAllocation getContractClientAllocation() {
		ContractClientAllocation contractClientAllocation = getContractClientAllocations()
				.isEmpty() ? null
				: (ContractClientAllocation) getContractClientAllocations()
						.iterator().next();

		return contractClientAllocation;
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public Set<ContractClientAllocation> getContractClientAllocations() {
		return contractClientAllocations;
	}

	public String getTerminationReasonCT() {
		return this.contractClientVO.getTerminationReasonCT();
	}

	/**
	 * Setter.
	 * 
	 * @param contractClientAllocations
	 */
	public void setContractClientAllocations(
			Set<ContractClientAllocation> contractClientAllocations) {
		this.contractClientAllocations = contractClientAllocations;
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	public Set<QuestionnaireResponse> getQuestionnaireResponses() {
		return questionnaireResponses;
	}

	public void setQuestionnaireResponses(
			Set<QuestionnaireResponse> questionnaireResponses) {
		this.questionnaireResponses = questionnaireResponses;
	}

	/**
	 * Adds a QuestionnaireResponse to the set of children
	 * 
	 * @param questionnaireResponse
	 */
	public void addQuestionnaireResponse(
			QuestionnaireResponse questionnaireResponse) {
		this.getQuestionnaireResponses().add(questionnaireResponse);

		questionnaireResponse.setContractClient(this);

		SessionHelper.saveOrUpdate(questionnaireResponse,
				ContractClient.DATABASE);
	}

	/**
	 * Removes a QuestionnaireResponse from the set of children
	 * 
	 * @param questionnaireResponse
	 */
	public void removeQuestionnaireResponse(
			QuestionnaireResponse questionnaireResponse) {
		this.getQuestionnaireResponses().remove(questionnaireResponse);

		questionnaireResponse.setContractClient(null);

		SessionHelper.saveOrUpdate(questionnaireResponse,
				ContractClient.DATABASE);
	}

	/**
	 * Compare to existing data base table, using the ChangeProcessor. For the
	 * ChangeVOs returned, check for complex Changes on Life contracts, and
	 * generate ChangeHistory for other changes.
	 */
	public boolean checkForNonFinancialChanges() {
		ChangeProcessor changeProcessor = new ChangeProcessor();
		Change[] changes = changeProcessor.checkForChanges(contractClientVO,
				contractClientVO.getVoShouldBeDeleted(),
				ConnectionFactory.EDITSOLUTIONS_POOL, null);
		boolean updateDone = false;

		if (segment == null) {
			segment = this.getSegment();
		}

		if ((changes != null) && (changes.length > 0)) {
			String roleType = this.getRoleType();
			String terminationReason = Util.initString(
					this.getTerminationReasonCT(), "");

			for (int i = 0; i < changes.length; i++) {
				if ((changeHistoryEffDate != null)
						&& !changeHistoryEffDate.equals("")) {
					changes[i].setEffectiveDate(changeHistoryEffDate);
				}

				if (changes[i].getStatus() == (Change.CHANGED)) {
					if ((changes[i].getFieldName().equalsIgnoreCase("ClassCT") || changes[i]
							.getFieldName().equalsIgnoreCase("TableRatingCT"))
							&& (roleType.equalsIgnoreCase("Insured")
									&& segment.getSegmentNameCT()
											.equalsIgnoreCase("Life") && (segment
									.getCurrentRiderNumber() == 0))) {
						checkForComplexChange(changes[i]);
					}
					if ((changes[i].getFieldName()
							.equalsIgnoreCase("TerminationReasonCT"))
							&& ((roleType.equalsIgnoreCase("OWN") || roleType
									.equalsIgnoreCase("SOW")) && terminationReason
									.equalsIgnoreCase("OwnershipChg-Ch/Oth"))) {
						checkForComplexChange(changes[i]);
					} else {
						changeProcessor.processForChanges(changes[i], this,
								segment.getOperator(), roleType,
								segment.getPK());
					}
				} else if (changes[i].getStatus() == (Change.ADDED)) {
					// CONVERT TO HIBERNATE - 09/13/07
					hSave();
					// crudEntityImpl.save(this,
					// ConnectionFactory.EDITSOLUTIONS_POOL, false);
					updateDone = true;

					String property = "ContractClientPK";
					changeProcessor.processForAdd(changes[i], this,
							segment.getOperator(), property, roleType,
							segment.getPK());
				} else if (changes[i].getStatus() == (Change.DELETED)) {
					String property = "ClientRoleFK";
					changeProcessor.processForDelete(changes[i], this,
							segment.getOperator(), property, roleType,
							segment.getPK());
				}
			}
		}

		return updateDone;
	}

	/**
	 * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
	 */
	private final void init() {
		if (contractClientVO == null) {
			contractClientVO = new ContractClientVO();
		}

		if (crudEntityImpl == null) {
			crudEntityImpl = new CRUDEntityImpl();
		}

		if (contractClientAllocations == null) {
			contractClientAllocations = new HashSet<ContractClientAllocation>();
		}

		if (withholdings == null) {
			withholdings = new HashSet<Withholding>();
		}
	}

	/**
	 * Gets the database this object belongs to
	 * 
	 * @return string containing the hibernate database name for this object
	 */
	public String getDatabase() {
		return ContractClient.DATABASE;
	}

	/**
	 * Finds the specified ContactClient with the largest EffectiveDate and OverrideStatus not 'D'
	 * 
	 * @param segment
	 * @param roleTypeCT
	 * @return
	 */
	public static final ContractClient findBy_SegmentPK_RoleTypeCT_MaxEffectiveDate(
			Segment segment, String roleTypeCT) {
		ContractClient contractClient = null;

		String hql = "select cc from ContractClient cc join cc.ClientRole cr where cc.Segment = :segment "
				+ "and cr.RoleTypeCT = :roleTypeCT and cc.OverrideStatus != 'D' and cc.EffectiveDate = "
				+ "(select max(cc2.EffectiveDate) from ContractClient cc2 join cc2.ClientRole cr2 where cc2.Segment = :segment "
				+ "and cr2.RoleTypeCT = :roleTypeCT and cc2.OverrideStatus != 'D')";

		Map<String, Serializable> params = new HashMap<>();

		params.put("segment", segment);
		params.put("roleTypeCT", roleTypeCT);

		List results = SessionHelper.executeHQL(hql, params,
				ContractClient.DATABASE);

		if (!results.isEmpty()) {
			contractClient = (ContractClient) results.get(0);
		}

		if (contractClient == null) {
			hql = "select cc from ContractClient cc join cc.ClientRole cr where cc.SegmentFK = :segmentFK "
					+ "and cr.RoleTypeCT = :roleTypeCT and cc.OverrideStatus != 'D' and cc.EffectiveDate = "
					+ "(select max(cc2.EffectiveDate) from ContractClient cc2 join cc2.ClientRole cr2 where cc2.SegmentFK = :segmentFK "
					+ "and cr2.RoleTypeCT = :roleTypeCT)";

			Map<String, Serializable> params2 = new HashMap<>();
			
			params2.put("segmentFK", segment.getSegmentFK());
			params2.put("roleTypeCT", roleTypeCT);

			List results2 = SessionHelper.executeHQL(hql, params2,
					ContractClient.DATABASE);

			if (!results2.isEmpty()) {
				contractClient = (ContractClient) results2.get(0);
			}
		}

		return contractClient;
	}
	

	/**
	 * Originally from ContractClientDAO.findBySegmentFK
	 * 
	 * @param segmentFK
	 * @return
	 */
	public static ContractClient[] findBy_SegmentFK(Long segmentFK) {
		// deck: add status criteria. Only select records with OverrideStatus of
		// 'P'
		String hql = " select contractClient from ContractClient contractClient"
				+ " where contractClient.SegmentFK = :segmentFK"
				+ " and contractClient.TerminationDate > :currentDate"
				+ " and contractClient.OverrideStatus = :status";

		Map params = new HashMap();

		params.put("segmentFK", segmentFK);
		params.put("currentDate", new EDITDate());
		params.put("status", "P");

		List<ContractClient> results = SessionHelper.executeHQL(hql, params,
				ContractClient.DATABASE);

		return results.toArray(new ContractClient[results.size()]);
	}

	/**
	 * Finder.
	 * 
	 * @param segmentPK
	 * @param roleTypeCT
	 * @param terminationDate
	 * @return
	 */
	public static final ContractClient[] findBy_SegmentPK_RoleTypeCT_TerminationDateGTE(
			long segmentPK, String[] roleTypeCTs, String terminationDate) {
		ContractClientVO[] contractClientVOs = new ContractClientDAO()
				.findBy_SegmentPK_RoleTypeCT_TerminationDateGTE(segmentPK,
						roleTypeCTs, terminationDate);

		return (ContractClient[]) CRUDEntityImpl.mapVOToEntity(
				contractClientVOs, ContractClient.class);
	}

	/**
	 * Finder.
	 * 
	 * @param clientDetail
	 * @return
	 */
	public static ContractClient[] findBy_ClientDetail(ClientDetail clientDetail) {
		String hql = "select cc from ContractClient cc inner join cc.ClientRole cr inner join cr.ClientDetail cd where cd = :clientDetail";

		Map params = new HashMap();

		params.put("clientDetail", clientDetail);

		List<ContractClient> results = SessionHelper.executeHQL(hql, params,
				ContractClient.DATABASE);

		return results.toArray(new ContractClient[results.size()]);
	}

	/**
	 * Finder by PK.
	 * 
	 * @param contractClientPK
	 * @return
	 */
	public static final ContractClient findByPK(Long contractClientPK) {
		return (ContractClient) SessionHelper.get(ContractClient.class,
				contractClientPK, ContractClient.DATABASE);
	}

	/**
	 * Finder.
	 * 
	 * @param clientDetail
	 * @return
	 */
	public static ContractClient[] findBy_ClientDetail_Roles(
			ClientDetail clientDetail) {
		String hql = "select cc from ContractClient cc inner join cc.ClientRole cr inner join cr.ClientDetail cd "
				+ "where cd = :clientDetail"
				+ " and (cr.RoleTypeCT = :annRoleTypeCT"
				+ " or cr.RoleTypeCT = :ownRoleTypeCT"
				+ " or cr.RoleTypeCT = :insRoleTypeCT or cr.RoleTypeCT = :secOwnerRoleTypeCT)"
				+ " and cr.OverrideStatus = :overrideStatus";

		Map params = new HashMap();

		params.put("clientDetail", clientDetail);
		params.put("annRoleTypeCT", ClientRole.ROLETYPECT_ANNUITANT);
		params.put("ownRoleTypeCT", ClientRole.ROLETYPECT_OWNER);
		params.put("insRoleTypeCT", ClientRole.ROLETYPECT_INSURED);
		params.put("secOwnerRoleTypeCT", ClientRole.ROLETYPECT_SECONDARY_OWNER);
		params.put("overrideStatus", "P");

		List<ContractClient> results = SessionHelper.executeHQL(hql, params,
				ContractClient.DATABASE);

		if (results.isEmpty()) {
			// For non-life contracts if the owner is not the client requested,
			// no records will be retrieved, go after the ann role
			hql = "select cc from ContractClient cc inner join cc.ClientRole cr inner join cr.ClientDetail cd "
					+ "where cd = :clientDetail"
					+ " and (cr.RoleTypeCT = :annRoleTypeCT)"
					+ " and cr.OverrideStatus = :overrideStatus";

			params = new HashMap();

			params.put("clientDetail", clientDetail);
			params.put("annRoleTypeCT", ClientRole.ROLETYPECT_ANNUITANT);
			params.put("overrideStatus", "P");

			results = SessionHelper.executeHQL(hql, params,
					SessionHelper.EDITSOLUTIONS);
		}

		return results.toArray(new ContractClient[results.size()]);
	}

	/**
	 * Finder.
	 * 
	 * @param clientDetail
	 * @return
	 */
	public static ContractClient[] findBy_ClientDetail_Roles_ForDeath(
			ClientDetail clientDetail) {
		String hql = "select cc from ContractClient cc inner join cc.ClientRole cr inner join cr.ClientDetail cd "
				+ "where cd = :clientDetail"
				+ " and (cr.RoleTypeCT = :ownRoleTypeCT or cr.RoleTypeCT = :annRoleTypeCT"
				+ " or cr.RoleTypeCT = :insRoleTypeCT or cr.RoleTypeCT = :termInsuredRoleTypeCT or cr.RoleTypeCT = :secOwnerRoleTypeCT)"
				+ " and cr.OverrideStatus = :overrideStatus"
				+ " order by cr.RoleTypeCT asc";

		Map params = new HashMap();

		params.put("clientDetail", clientDetail);
		params.put("ownRoleTypeCT", ClientRole.ROLETYPECT_OWNER);
		params.put("annRoleTypeCT", ClientRole.ROLETYPECT_ANNUITANT);
		params.put("insRoleTypeCT", ClientRole.ROLETYPECT_INSURED);
		params.put("termInsuredRoleTypeCT", ClientRole.ROLETYPECT_TERM_INSURED);
		params.put("secOwnerRoleTypeCT", ClientRole.ROLETYPECT_SECONDARY_OWNER);
		params.put("overrideStatus", "P");
		System.out.println("findBy_ClientDetail_Roles_ForDeath: " + hql);

		List<ContractClient> results = SessionHelper.executeHQL(hql, params,
				ContractClient.DATABASE);

		return results.toArray(new ContractClient[results.size()]);
	}

	/**
	 * Finder.
	 * 
	 * @param editTrxPK
	 * @return
	 */
	public static final ContractClient findBy_EDITTrxPK(Long editTrxPK) {
		ContractClient contractClient = null;

		String hql = "select contractClient " + " from EDITTrx editTrx"
				+ " join editTrx.ClientSetup clientSetup"
				+ " join clientSetup.ContractClient contractClient"
				+ " where editTrx.EDITTrxPK = :editTrxPK";

		Map params = new HashMap();

		params.put("editTrxPK", editTrxPK);

		List results = SessionHelper.executeHQL(hql, params,
				ContractClient.DATABASE);

		if (!results.isEmpty()) {
			contractClient = (ContractClient) results.get(0);
		}

		return contractClient;
	}

	/**
	 * Finder by Segment, RoleTypeCT AND TerminationDate.
	 * 
	 * @param segment
	 * @param roleTypeCT
	 * @param terminationDate
	 * @return
	 */
	public static final ContractClient findBy_SegmentPK_RoleTypeCT_TerminationDateGTE(
			Segment segment, String roleTypeCT, EDITDate terminationDate) {
		ContractClient contractClient = null;

		String hql = " select cc from ContractClient cc join cc.ClientRole cr"
				+ " where cc.Segment = :segment"
				+ " and cr.RoleTypeCT = :roleTypeCT"
				+ " and cc.TerminationDate >= :terminationDate";

		Map params = new HashMap();

		params.put("segment", segment);
		params.put("roleTypeCT", roleTypeCT);
		params.put("terminationDate", terminationDate);

		List results = SessionHelper.executeHQL(hql, params,
				ContractClient.DATABASE);

		if (!results.isEmpty()) {
			contractClient = (ContractClient) results.get(0);
		} else {
			hql = " select cc from ContractClient cc join cc.ClientRole cr"
					+ " where cc.SegmentFK = :segmentFK"
					+ " and cr.RoleTypeCT = :roleTypeCT"
					+ " and cc.TerminationDate >= :terminationDate";

			Map params2 = new HashMap();

			params2.put("segmentFK", segment.getSegmentFK());
			params2.put("roleTypeCT", roleTypeCT);
			params2.put("terminationDate", terminationDate);

			List results2 = SessionHelper.executeHQL(hql, params2,
					ContractClient.DATABASE);

			if (!results2.isEmpty()) {
				contractClient = (ContractClient) results2.get(0);
			}
		}

		return contractClient;
	}

	/**
	 * Finds the specified ContactClient with the largest Termination Date and
	 * that is not '12/31/9999'
	 * 
	 * @param segment
	 * @param roleTypeCT
	 * @return
	 */
	public static final ContractClient findBy_SegmentPK_RoleTypeCT_MaxTerminationDateAndNot12319999(
			Segment segment, String roleTypeCT) {
		ContractClient contractClient = null;

		String hql = "select cc from ContractClient cc join cc.ClientRole cr where cc.Segment = :segment "
				+ "and cr.RoleTypeCT = :roleTypeCT and cc.TerminationDate = "
				+ "(select max(cc2.TerminationDate) from ContractClient cc2 join cc2.ClientRole cr2 where cc2.Segment = :segment "
				+ "and cr2.RoleTypeCT = :roleTypeCT and cc2.TerminationDate != :editDateMaxDate)";

		Map params = new HashMap();

		params.put("segment", segment);
		params.put("roleTypeCT", roleTypeCT);
		params.put("editDateMaxDate", new EDITDate(EDITDate.DEFAULT_MAX_DATE));

		List results = SessionHelper.executeHQL(hql, params,
				ContractClient.DATABASE);

		if (!results.isEmpty()) {
			contractClient = (ContractClient) results.get(0);
		}

		return contractClient;
	}

	/**
	 * Find the owner role
	 * 
	 * @param contractClients
	 * @return
	 */
	public static ContractClient getOwnerContractClient(
			ContractClient[] contractClients) {
		ContractClient ownerContractClient = null;

		for (int i = 0; i < contractClients.length; i++) {
			if (contractClients[i].getRoleType().equalsIgnoreCase("OWN") &&
					!contractClients[i].getOverrideStatus().equalsIgnoreCase("D")) {
				ownerContractClient = contractClients[i];

				break;
			}
		}

		return ownerContractClient;
	}

	/**
	 * Finder.
	 * 
	 * @param clientRole
	 * @return
	 */
	public static final ContractClient[] findByClientRole(ClientRole clientRole) {
		String hql = "select cc from ContractClient cc where cc.ClientRole = :clientRole";

		Map params = new HashMap();

		params.put("clientRole", clientRole);

		List<ContractClient> results = SessionHelper.executeHQL(hql, params,
				ContractClient.DATABASE);

		return results.toArray(new ContractClient[results.size()]);
	}

	/**
	 * Adds a Withholding to the set of children
	 * 
	 * @param withholding
	 */
	public void addWithholding(Withholding withholding) {
		this.getWithholdings().add(withholding);

		withholding.setContractClient(this);

		SessionHelper.saveOrUpdate(withholding, ContractClient.DATABASE);
	}

	/**
	 * Removes a Withholding from the set of children
	 * 
	 * @param withholding
	 */
	public void removeWithholding(Withholding withholding) {
		this.getWithholdings().remove(withholding);

		withholding.setContractClient(null);

		SessionHelper.saveOrUpdate(withholding, ContractClient.DATABASE);
	}

	public Withholding getWithholding() {
		Withholding withholding = getWithholdings().isEmpty() ? null
				: (Withholding) getWithholdings().iterator().next();

		return withholding;
	}

	/**
	 * Adds a ClientSetup to the set of children
	 * 
	 * @param clientSetup
	 */
	public void addClientSetup(ClientSetup clientSetup) {
		this.getClientSetups().add(clientSetup);

		clientSetup.setContractClient(this);

		SessionHelper.saveOrUpdate(clientSetup, ContractClient.DATABASE);
	}

	/**
	 * Removes a ClientSetup from the set of children
	 * 
	 * @param clientSetup
	 */
	public void removeClientSetup(ClientSetup clientSetup) {
		this.getClientSetups().remove(clientSetup);

		clientSetup.setContractClient(null);

		SessionHelper.saveOrUpdate(clientSetup, ContractClient.DATABASE);
	}

	/**
	 * Create the child ContractClientAllocation
	 * 
	 * @param allocationPercent
	 *            percentage of death benefit
	 * @param overrideStatus
	 *            defines primary or override allocation
	 */
	public void createContractClientAllocation(
			EDITBigDecimal allocationPercent, String overrideStatus,
			String splitEqualInd, EDITBigDecimal allocationAmount) {
		// format the contractClientAllocation for the ContractClient
		ContractClientAllocation contractClientAllocation = new ContractClientAllocation();
		contractClientAllocation.setOverrideStatus(overrideStatus);
		contractClientAllocation.setAllocationPercent(allocationPercent);
		contractClientAllocation.setAllocationDollars(allocationAmount);
		contractClientAllocation.setSplitEqual(splitEqualInd);

		this.addContractClientAllocation(contractClientAllocation);
	}

	/**
	 * Finder.
	 * 
	 * @param segmentPK
	 * @param roleTypeCT
	 * @return
	 */
	public static ContractClient findBy_SegmentPK_RoleTypeCT(Long segmentPK,
			String roleTypeCT) {
		ContractClient contractClient = null;

		String hql = " select contractClient"
				+ " from ContractClient contractClient"
				+ " join contractClient.ClientRole clientRole"
				+ " where contractClient.SegmentPK = :segmentPK"
				+ " and clientRole.RoleTypeCT = :roleTypeCT";

		Map params = new HashMap();

		params.put("segmentPK", segmentPK);

		params.put("roleTypeCT", roleTypeCT);

		List results = SessionHelper.executeHQL(hql, params,
				ContractClient.DATABASE);

		if (!results.isEmpty()) {
			contractClient = (ContractClient) results.get(0);
		}

		return contractClient;
	}

	/**
	 * The ContractClients of the specified Segment with the specified
	 * RoleTypeCT.
	 * 
	 * @param segmentPK
	 * @param roleTypeCT
	 * @return
	 */
	public static ContractClient[] findBy_SegmentPK_And_RoleTypeCT(
			Long segmentPK, String roleTypeCT) {
		String hql = " select contractClient" + " from Segment segment "
				+ " join segment.ContractClients contractClient "
				+ " join contractClient.ClientRole clientRole"
				+ " where segment.SegmentPK = :segmentPK"
				+ " and clientRole.RoleTypeCT = :roleTypeCT"
				+ " and contractClient.OverrideStatus <> 'D'";

		Map params = new HashMap();

		params.put("segmentPK", segmentPK);

		params.put("roleTypeCT", roleTypeCT);

		List<ContractClient> results = SessionHelper.executeHQL(hql, params,
				ContractClient.DATABASE);

		return results.toArray(new ContractClient[results.size()]);
	}

	/**
	 * Save the entity using Hibernate
	 */
	public void hSave() {
		// To save the FK's existing in the Segment row alreday the entity must
		// be set here for hibernate to be aware.
		Long pk = this.getClientRoleFK();
		if (pk != null && pk > 0) {
			ClientRole clientRole = ClientRole.findByPK(pk);
			this.setClientRole(clientRole);
		}

		SessionHelper.saveOrUpdate(this, ContractClient.DATABASE);
	}

	/**
	 * Delete the entity using Hibernate
	 */
	public void hDelete() {
		SessionHelper.delete(this, ContractClient.DATABASE);
	}

	/**
	 * 
	 * @param segmentPK
	 * @param clientDetailPK
	 * @return
	 */
	public static final ContractClient findBy_SegmentPK_ClientDetailPK_MaxTerminationDate(
			Long segmentPK, Long clientDetailPK) {
		ContractClient contractClient = null;

		String hql = "select cc from ContractClient cc join cc.ClientRole cr "
				+ "where cc.SegmentFK = :segmentPK "
				+ "and cr.ClientDetailFK = :clientDetailPK "
				+ "and (cc.TerminationDate = :editDateMaxDate "
				+ "or cc.TerminationDate >= :currentDate)"
				+ "and (cr.RoleTypeCT != :annRoleType and cr.RoleTypeCT != :insuredRoleType)";

		Map params = new HashMap();

		params.put("segmentPK", segmentPK);
		params.put("clientDetailPK", clientDetailPK);
		params.put("editDateMaxDate", new EDITDate(EDITDate.DEFAULT_MAX_DATE));
		params.put("currentDate", new EDITDate());
		params.put("annRoleType", "ANN");
		params.put("insuredRoleType", "Insured");

		List results = SessionHelper.executeHQL(hql, params,
				ContractClient.DATABASE);

		if (!results.isEmpty()) {
			contractClient = (ContractClient) results.get(0);
		}

		return contractClient;
	}

	public static ContractClient findBy_PK_ClientRole_ClientDetail(
			Long contractClientPK) {
		ContractClient contractClient = null;

		String hql = "select cc from ContractClient cc inner join cc.ClientRole cr inner join cr.ClientDetail cd where cc.ContractClientPK = :contractClientPK";

		Map params = new HashMap();

		params.put("contractClientPK", contractClientPK);

		List results = SessionHelper.executeHQL(hql, params,
				ContractClient.DATABASE);

		if (!results.isEmpty()) {
			contractClient = (ContractClient) results.get(0);
		}

		return contractClient;
	}

	/**
	 * Find the owner role
	 * 
	 * @param contractClientVOs
	 * @return
	 */
	public ContractClientVO getOwnerContractClient(
			ContractClientVO[] contractClientVOs) {
		ContractClientVO contractClientVO = null;
		EDITDate currentDate = new EDITDate();

		for (int i = 0; i < contractClientVOs.length; i++) {
			int compareValue = contractClientVOs[i].getTerminationDate()
					.compareTo(currentDate.getFormattedDate());

			if (compareValue >= 0) {
				setVO(contractClientVOs[i]);

				String roleType = this.getRoleType();

				if (roleType.equalsIgnoreCase("OWN")) {
					contractClientVO = contractClientVOs[i];

					break;
				}
			}
		}

		return contractClientVO;
	}

	/**
	 * Finds the ContractClient by EDITTrxPK and
	 * ContractClient.ClientRole.RoleTypeCT. It is possible that there are more
	 * than one ContractClient that satisfies this requirement. Therefore, the
	 * ContractClient with the ContractClient.TerminationDate =
	 * EDITDate.DEFAULT_MAX_DATE is used. Additionally, the ClientAddress is
	 * constrained as ClientAddress.TerminationDate = EDITDate.DEFAULT_MAX_DATE
	 * and ClientAddress.AddressTypeCT = "PrimaryAddress".
	 * 
	 * @param editTrxPK
	 *            the driving EDITTrx
	 * @return
	 */
	public static ContractClient findSeparateBy_EDITTrxPK_V1(Long editTrxPK) {
		ContractClient contractClient = null;

		// Modified by Syam Lingamallu to find a ContractClient that is active
		// for the given EDITTrx EffectiveDate.
		// Modifiedy by Syam Lingamallu ... to get ContractClient by ClientRole
		// that is attached to given EDITTrx.ClientSetup.
		String hql = " select contractClient from ContractClient contractClient"
				+ " left join fetch contractClient.Withholdings"
				+ " left join fetch contractClient.ContractClientAllocations"
				+ " join fetch contractClient.ClientSetups clientSetup"
				+ " left join fetch clientSetup.WithholdingOverrides withholdingOverride"
				+ " left join fetch withholdingOverride.Withholding"
				+ " left join fetch clientSetup.ContractClientAllocationOvrds contractClientAllocationOvrd"
				+ " left join fetch contractClientAllocationOvrd.ContractClientAllocation"
				+ " join fetch clientSetup.EDITTrxs editTrx"
				+ " join fetch clientSetup.ClientRole clientRole"
				+ " left join fetch clientRole.Preference"
				+ " left join fetch clientRole.TaxProfile"
				+ " join fetch clientRole.ClientDetail clientDetail"
				+ " left join fetch clientDetail.ClientAddresses clientAddress"
				+ " left join fetch clientDetail.Preferences"
				+ " left join fetch clientDetail.TaxInformations taxInformation"
				+ " left join fetch taxInformation.TaxProfiles"
				+ " where editTrx.EDITTrxPK = :editTrxPK"
				+ " and editTrx.EffectiveDate <= contractClient.TerminationDate"
				+ " and contractClient.OverrideStatus != 'D'";

		EDITMap params = new EDITMap("editTrxPK", editTrxPK);

		Session session = null;

		try {
			session = SessionHelper.getSeparateSession(ContractClient.DATABASE);

			List<ContractClient> results = SessionHelper.executeHQL(session,
					hql, params, 0);

			if (!results.isEmpty()) {
				contractClient = results.get(0);
			}

		} finally {
			if (session != null)
				session.close();
		}

		return contractClient;
	}

	/**
	 * Finds the ContractClient by SegmentPK and
	 * ContractClient.ClientRole.RoleTypeCT. It is possible that there are more
	 * than one ContractClient that satisfies this requirement. Therefore, the
	 * ContractClient with the ContractClient.TerminationDate =
	 * EDITDate.DEFAULT_MAX_DATE is used.
	 * 
	 * @param segmentPK
	 *            the driving Segment
	 * @param roleTypeCT
	 *            the desired ClientRole.RoleTypeCT
	 * @return
	 */
	public static ContractClient[] findSeparateBy_SegmentPK_RoleType_V1(
			Long segmentPK, String roleTypeCT) {
		String hql = " select contractClient from ContractClient contractClient"
				+ " left join fetch contractClient.Withholdings"
				+ " left join fetch contractClient.ContractClientAllocations"
				+ " join fetch contractClient.ClientRole clientRole"
				+ " join fetch clientRole.ClientDetail clientDetail"
				+ " left join fetch clientDetail.Preferences"
				+ " left join fetch clientDetail.ClientAddresses clientAddress"
				+ " left join fetch clientDetail.TaxInformations taxInformation"
				+ " left join fetch taxInformation.TaxProfiles"
				+ " where contractClient.SegmentFK = :segmentPK"
				+ " and clientRole.RoleTypeCT = :roleTypeCT"
				+ " and contractClient.TerminationDate = :terminationDate"
				+ " and contractClient.OverrideStatus != 'D'";

		EDITMap params = new EDITMap("segmentPK", segmentPK).put("roleTypeCT",
				roleTypeCT).put("terminationDate",
				new EDITDate(EDITDate.DEFAULT_MAX_DATE));

		Session session = null;

		List<ContractClient> results = null;

		try {
			session = SessionHelper.getSeparateSession(ContractClient.DATABASE);

			results = SessionHelper.makeUnique(SessionHelper.executeHQL(
					session, hql, params, 0));
		} finally {
			if (session != null)
				session.close();
		}

		return results.toArray(new ContractClient[results.size()]);
	}
	
	/**
	 * Finds the ContractClient by ContractNumber and
	 * ContractClient.ClientRole.RoleTypeCT. It is possible that there are more
	 * than one ContractClient that satisfies this requirement. Therefore, the
	 * ContractClient with the ContractClient.TerminationDate =
	 * EDITDate.DEFAULT_MAX_DATE is used.
	 * 
	 * @param contractNumber
	 *            the driving Segment
	 * @param roleTypeCT
	 *            the desired ClientRole.RoleTypeCT
	 * @return
	 */
	public static ContractClient[] findSeparateBy_ContractNumber_RoleType_V1(
			String contractNumber, String roleTypeCT) {
		String hql = " select contractClient from ContractClient contractClient"
				+ " join fetch contractClient.Segment segment"
				+ " left join fetch contractClient.Withholdings"
				+ " left join fetch contractClient.ContractClientAllocations"
				+ " join fetch contractClient.ClientRole clientRole"
				+ " join fetch clientRole.ClientDetail clientDetail"
				+ " left join fetch clientDetail.Preferences"
				+ " left join fetch clientDetail.ClientAddresses clientAddress"
				+ " left join fetch clientDetail.TaxInformations taxInformation"
				+ " left join fetch taxInformation.TaxProfiles"
				+ " where segment.ContractNumber = :contractNumber"
				+ " and clientRole.RoleTypeCT = :roleTypeCT"
				+ " and contractClient.TerminationDate = :terminationDate"
				+ " and contractClient.OverrideStatus != 'D'";

		EDITMap params = new EDITMap("contractNumber", contractNumber)
				.put("roleTypeCT", roleTypeCT).put("terminationDate", new EDITDate(EDITDate.DEFAULT_MAX_DATE));

		Session session = null;

		List<ContractClient> results = null;

		try {
			session = SessionHelper.getSeparateSession(ContractClient.DATABASE);

			results = SessionHelper.makeUnique(SessionHelper.executeHQL(
					session, hql, params, 0));
		} finally {
			if (session != null)
				session.close();
		}

		return results.toArray(new ContractClient[results.size()]);
	}

	/**
	 * Finds the ContractClient by ContractClientPK and eagerly fetches
	 * Withholdings, ContractClientAllocations, WithholdingOverrides,
	 * ContractClientAllocationOverrides, ClientDetail, ClientAddress,
	 * Preference, TaxInformation and TaxProfile if any exists.
	 * 
	 * @param contractClientPK
	 * @return ContractClient entity
	 */
	public static ContractClient findSeperateByPK_EDITTrxPK_V1(
			Long contractClientPK, Long editTrxPK) {
		ContractClient contractClient = null;

		String hql = " select contractClient from ContractClient contractClient"
				+ " left join fetch contractClient.Withholdings"
				+ " left join fetch contractClient.ContractClientAllocations"
				+ " join fetch contractClient.ClientSetups clientSetup"
				+ " left join fetch clientSetup.WithholdingOverrides withholdingOverride"
				+ " left join fetch withholdingOverride.Withholding"
				+ " left join fetch clientSetup.ContractClientAllocationOvrds contractClientAllocationOvrd"
				+ " left join fetch contractClientAllocationOvrd.ContractClientAllocation"
				+ " join fetch clientSetup.EDITTrxs editTrx"
				+ " join fetch contractClient.ClientRole clientRole"
				+ " left join fetch clientRole.Preference"
				+ " left join fetch clientRole.TaxProfile"
				+ " join fetch clientRole.ClientDetail clientDetail"
				+ " left join fetch clientDetail.ClientAddresses clientAddress"
				+ " left join fetch clientDetail.Preferences"
				+ " join fetch clientDetail.TaxInformations taxInformation"
				+ " left join fetch taxInformation.TaxProfiles"
				+ " where contractClient.ContractClientPK = :contractClientPK"
				+ " and editTrx.EDITTrxPK = :editTrxPK";

		EDITMap params = new EDITMap("contractClientPK", contractClientPK);
		params.put("editTrxPK", editTrxPK);

		Session session = null;

		try {
			session = SessionHelper
					.getSeparateSession(SessionHelper.EDITSOLUTIONS);

			List<ContractClient> results = SessionHelper.executeHQL(session,
					hql, params, 0);

			if (!results.isEmpty()) {
				contractClient = results.get(0);
			}

		} finally {
			if (session != null)
				session.close();
		}

		return contractClient;
	}

	/**
	 * Finds the ContractClient by ContractClientPK and eagerly fetches
	 * Withholdings, ContractClientAllocations, ClientDetail, ClientAddress,
	 * Preference, TaxInformation and TaxProfile if any exists.
	 * 
	 * @param contractClientPK
	 * @return ContractClient entity
	 */
	public static ContractClient findSeperateByPK_V1(Long contractClientPK) {
		ContractClient contractClient = null;

		String hql = " select contractClient from ContractClient contractClient"
				+ " left join fetch contractClient.Withholdings"
				+ " left join fetch contractClient.ContractClientAllocations"
				+ " join fetch contractClient.ClientRole clientRole"
				+ " left join fetch clientRole.Preference"
				+ " left join fetch clientRole.TaxProfile"
				+ " join fetch clientRole.ClientDetail clientDetail"
				+ " left join fetch clientDetail.ClientAddresses clientAddress"
				+ " left join fetch clientDetail.Preferences"
				+ " join fetch clientDetail.TaxInformations taxInformation"
				+ " left join fetch taxInformation.TaxProfiles"
				+ " where contractClient.ContractClientPK = :contractClientPK";

		EDITMap params = new EDITMap("contractClientPK", contractClientPK);

		Session session = null;

		try {
			session = SessionHelper
					.getSeparateSession(SessionHelper.EDITSOLUTIONS);

			List<ContractClient> results = SessionHelper.executeHQL(session,
					hql, params, 0);

			if (!results.isEmpty()) {
				contractClient = results.get(0);
			}

		} finally {
			if (session != null)
				session.close();
		}

		return contractClient;
	}

	/**
	 * The ContractClients with the specified SegmentFK and ClientRoleFK.
	 * 
	 * @param segmentFK
	 * @param clientRoleFK
	 * @return
	 */
	public static ContractClient findBy_SegmentFK_And_ClientRoleFK(
			Long segmentFK, Long clientRoleFK) {
		ContractClient contractClient = null;

		String hql = " select cc from ContractClient cc"
				+ " where cc.SegmentFK = :segmentFK"
				+ " and cc.ClientRoleFK = :clientRoleFK";

		Map params = new HashMap();

		params.put("segmentFK", segmentFK);

		params.put("clientRoleFK", clientRoleFK);

		List<ContractClient> results = SessionHelper.executeHQL(hql, params,
				ContractClient.DATABASE);

		if (!results.isEmpty()) {
			contractClient = results.get(0);
		}

		return contractClient;
	}

	public void set_tableRatingCT(String _tableRatingCT) {
		this.tableRatingCT = _tableRatingCT;
	}

	public String get_tableRatingCT() {
		return tableRatingCT;
	}

	/**
	 * Validates the allocations of the beneficary ContractClients. There are
	 * multiple roles that qualify as beneficiaries but the validation has to be
	 * performed by role. For example, there may be multiple Primary
	 * beneficiaries and multiple Contingents. The Primaries (like a spouse) may
	 * have percent values set and the Contingents (like children) may have
	 * splitEquals. The validation must ensure that the same field is set across
	 * all contract clients with the same role (can't have percent on 1 primary
	 * and splitEqual on another) and it must ensure that the total percent
	 * (within that role) is either 0 or 1.
	 * 
	 * @param beneficiaryContractClients
	 * 
	 * @throws EDITException
	 *             if any of the validations fail
	 */
	public static List validateBeneficiaryAllocations(
			ContractClient[] beneficiaryContractClients) {
		List errorMessages = new ArrayList();

		for (int i = 0; i < ClientRole.BENEFICIARY_ROLES.length; i++) {
			String beneficiaryRole = ClientRole.BENEFICIARY_ROLES[i];

			Set<ContractClient> singleRoleBeneficaryContractClients = new HashSet<ContractClient>();

			for (int j = 0; j < beneficiaryContractClients.length; j++) {
				if (beneficiaryContractClients[j].getClientRole()
						.getRoleTypeCT().equals(beneficiaryRole)) {
					singleRoleBeneficaryContractClients
							.add(beneficiaryContractClients[j]);
				}
			}

			if (singleRoleBeneficaryContractClients.size() > 0) {
				// ContractClients were found for this role, continue with the
				// validation
				String newErrorMessage = validateBeneficiaryAllocationsForSingleRole(singleRoleBeneficaryContractClients);

				if (newErrorMessage != null) {
					errorMessages.add(newErrorMessage);
				}
			}
		}

		return errorMessages;
	}

	/**
	 * Validates the beneficiary's allocations by role. Within a specific
	 * beneficiary role, we need to ensure that only 1 field is set for the
	 * allocation (splitEqual, dollars, or percent) and that the total percent
	 * is either 0 or 1. We do this by role because the Primary Beneficiary may
	 * have percent set which has to total 0 or 1 and there maybe Contingent
	 * Beneficiaries that use splitEquals across all Contingents.
	 * 
	 * @param singleRoleBeneficaryContractClients
	 * 
	 * @throws EDITException
	 *             if any of the validations fail
	 */
	public static String validateBeneficiaryAllocationsForSingleRole(
			Set<ContractClient> singleRoleBeneficaryContractClients) {
		String errorMessage = null;
		Iterator contractClientIterator = singleRoleBeneficaryContractClients
				.iterator();

		ContractClient contractClient = (ContractClient) contractClientIterator
				.next();

		String roleTypeCT = contractClient.getClientRole().getRoleTypeCT();

		if (!hasSameAllocationFieldSet(singleRoleBeneficaryContractClients)) {
			// Error
			errorMessage = "Can Only Set 1 Field - SplitEqual, Percent, or Dollars - Across All Clients With RoleType of ["
					+ roleTypeCT + "]";
		}

		if (isBeneAllocPercentSet(singleRoleBeneficaryContractClients)) {
			if (!hasValidTotalAllocationPercent(singleRoleBeneficaryContractClients)) {
				// Error
				errorMessage = "Total Percent Allocation Must Equal 0 or 1 Across All Clients With a RoleType of ["
						+ roleTypeCT + "]";
			}
		}

		return errorMessage;
	}

	/**
	 * Determines if the set of given contractClients has a valid total
	 * allocation percent. Valid values can be 0 or 1.
	 * 
	 * @param contractClients
	 * 
	 * @return true if the allocation percent total across all given
	 *         ContractClients is equal to 0 or 1, false otherwise
	 */
	public static boolean hasValidTotalAllocationPercent(
			Set<ContractClient> contractClients) {
		EDITBigDecimal totalAllocationPercent = ContractClient
				.calculateTotalAllocationPercent(contractClients);

		if (totalAllocationPercent.isEQ(new EDITBigDecimal("0"))
				|| totalAllocationPercent.isEQ(new EDITBigDecimal("1"))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Calculates the total allocationPercent across all of the given
	 * contractClients
	 * 
	 * @param contractClients
	 * @return
	 */
	public static EDITBigDecimal calculateTotalAllocationPercent(
			Set<ContractClient> contractClients) {
		EDITBigDecimal totalAllocationPercent = new EDITBigDecimal();

		for (Iterator contractClientIterator = contractClients.iterator(); contractClientIterator
				.hasNext();) {
			ContractClient contractClient = (ContractClient) contractClientIterator
					.next();

			ContractClientAllocation contractClientAllocation = contractClient
					.getContractClientAllocation();

			EDITBigDecimal allocationPercent = contractClientAllocation
					.getAllocationPercent();

			totalAllocationPercent = totalAllocationPercent
					.addEditBigDecimal(allocationPercent);
		}

		return totalAllocationPercent;
	}

	/**
	 * Determines whether any of the Beneficiaries has the splitEqual indicator
	 * set turned on.
	 * 
	 * @return true if any of the beneficiaries has the splitEqual indicator
	 *         turned on, false otherwise
	 */
	public static boolean isBeneAllocSplitEqualOn(
			Set<ContractClient> beneficiaryContractClients) {
		boolean splitEqual = false;

		for (Iterator contractClientIterator = beneficiaryContractClients
				.iterator(); contractClientIterator.hasNext();) {
			ContractClient beneficiaryContractClient = (ContractClient) contractClientIterator
					.next();

			ContractClientAllocation contractClientAllocation = beneficiaryContractClient
					.getContractClientAllocation();

			if (contractClientAllocation.getSplitEqual() != null
					&& contractClientAllocation.getSplitEqual().equals("Y")) {
				splitEqual = true;
				break;
			}
		}

		return splitEqual;
	}

	/**
	 * Determines whether any of the beneficiaries has allocation dollars set or
	 * not. If the dollars are not null or zero, they are considered to be
	 * "set".
	 * 
	 * @return true if any of the beneficiaries has an allocation dollar set to
	 *         a non-null or non-zero value, false otherwise
	 */
	public static boolean isBeneAllocDollarsSet(
			Set<ContractClient> beneficiaryContractClients) {
		boolean allocationDollarsSet = false;

		for (Iterator contractClientIterator = beneficiaryContractClients
				.iterator(); contractClientIterator.hasNext();) {
			ContractClient beneficiaryContractClient = (ContractClient) contractClientIterator
					.next();

			ContractClientAllocation contractClientAllocation = beneficiaryContractClient
					.getContractClientAllocation();

			EDITBigDecimal allocationDollars = contractClientAllocation
					.getAllocationDollars();

			if (allocationDollars != null
					&& !allocationDollars
							.isEQ(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR)) {
				allocationDollarsSet = true;
				break;
			}
		}

		return allocationDollarsSet;
	}

	/**
	 * Determines whether any of the beneficiaries has allocation percent set or
	 * not. If the percents are not null or zero, they are considered to be
	 * "set".
	 * 
	 * @return true if any of the beneficiaries has an allocation percent set to
	 *         a non-null or non-zero value, false otherwise
	 */
	public static boolean isBeneAllocPercentSet(
			Set<ContractClient> beneficiaryContractClients) {
		boolean allocationPercentSet = false;

		for (Iterator contractClientIterator = beneficiaryContractClients
				.iterator(); contractClientIterator.hasNext();) {
			ContractClient beneficiaryContractClient = (ContractClient) contractClientIterator
					.next();

			ContractClientAllocation contractClientAllocation = beneficiaryContractClient
					.getContractClientAllocation();

			EDITBigDecimal allocationPercent = contractClientAllocation
					.getAllocationPercent();

			if (allocationPercent != null
					&& !allocationPercent
							.isEQ(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR)) {
				allocationPercentSet = true;
				break;
			}
		}

		return allocationPercentSet;
	}

	/**
	 * Determines if the collection of ContractClients have the same field set
	 * on their ContractClientAllocations. The fields can be splitEquals,
	 * percent, or dollars. If one ContractClient has splitEquals and another
	 * has percent (for example), they do not have the same field set.
	 * 
	 * @true if the set fields are the same field across all ContractClients in
	 *       the collection, false otherwise
	 */
	public static boolean hasSameAllocationFieldSet(
			Set<ContractClient> beneficiaryContractClients) {
		boolean splitEqualOn = ContractClient
				.isBeneAllocSplitEqualOn(beneficiaryContractClients);
		boolean percentOn = ContractClient
				.isBeneAllocPercentSet(beneficiaryContractClients);
		boolean dollarsOn = ContractClient
				.isBeneAllocDollarsSet(beneficiaryContractClients);

		return ContractClientAllocation.hasOneAllocationFieldSet(splitEqualOn,
				percentOn, dollarsOn);
	}

	public StagingContext stage(StagingContext stagingContext, String database) {
		// deck: prevents any staging of contractClients with overrideStatus of 'D'
		if (getOverrideStatus().equals("P")) {
			ClientRole clientRole = null;
			if (this.getClientRole() == null) {
				clientRole = ClientRole.findBy_ClientRolePK(this
						.getClientRoleFK());
			} else {
				clientRole = this.getClientRole();
			}

			ClientDetail clientDetail = clientRole.getClientDetail();
			ClientAddress clientAddress = clientDetail.getPrimaryAddress();
			if (clientAddress == null) {
				clientAddress = clientDetail.getBusinessAddress();
			}

			staging.ContractClient stagingContractClient = new staging.ContractClient();
			stagingContractClient.setRole(clientRole.getRoleTypeCT());
			stagingContractClient.setIssueAge(this.getIssueAge());
			stagingContractClient.setClassCode(this.getClassCT());
			stagingContractClient.setRelationshipToEmployee(this
					.getRelationshipToEmployeeCT());
			stagingContractClient.setUnderwritingClass(this
					.getUnderwritingClassCT());
			stagingContractClient.setRatedGender(this.getRatedGenderCT());
			stagingContractClient.setRelationshipToInsured(this
					.getRelationshipToInsuredCT());
			stagingContractClient.setBeneRelationshipToInsured(this
					.getBeneRelationshipToInsured());
			stagingContractClient.setEmployeeIdentification(this
					.getEmployeeIdentification());
			stagingContractClient.setTerminationDate(this.getTerminationDate());
			stagingContractClient.setLastName(clientDetail.getLastName());
			stagingContractClient.setFirstName(clientDetail.getFirstName());
			stagingContractClient.setMiddleName(clientDetail.getMiddleName());
			stagingContractClient.setClientId(this.getEmployeeIdentification());
			stagingContractClient.setTaxIdentification(clientDetail
					.getTaxIdentification());
			stagingContractClient.setCorporateName(clientDetail
					.getCorporateName());
			stagingContractClient.setGender(clientDetail.getGenderCT());
			stagingContractClient.setDateOfBirth(clientDetail.getBirthDate());
			stagingContractClient.setNamePrefix(clientDetail.getNamePrefix());
			stagingContractClient.setNameSuffix(clientDetail.getNameSuffix());
			stagingContractClient.setDateOfDeath(clientDetail.getDateOfDeath());
			stagingContractClient.setNotificationReceivedDate(clientDetail
					.getNotificationReceivedDate());
			stagingContractClient.setProofOfDeathReceivedDate(clientDetail
					.getProofOfDeathReceivedDate());
			if (clientAddress != null) {
				stagingContractClient.setResidentState(clientAddress
						.getStateCT());
				stagingContractClient.setAddressLine1(clientAddress
						.getAddressLine1());
				stagingContractClient.setAddressLine2(clientAddress
						.getAddressLine2());
				stagingContractClient.setAddressLine3(clientAddress
						.getAddressLine3());
				stagingContractClient.setAddressLine4(clientAddress
						.getAddressLine4());
				stagingContractClient.setCity(clientAddress.getCity());
				stagingContractClient.setState(clientAddress.getStateCT());
				stagingContractClient.setZip(clientAddress.getZipCode());
			}

			ContractClientAllocation contractClientAllocation = this
					.getContractClientAllocation();
			if (contractClientAllocation != null) {
				stagingContractClient.setSplitEqualInd(contractClientAllocation
						.getSplitEqual());
				stagingContractClient
						.setBenePercentage(contractClientAllocation
								.getAllocationPercent());
				stagingContractClient
						.setAllocationDollars(contractClientAllocation
								.getAllocationDollars());
			}

			if (this.getSegment().isBase()) {
				if (!stagingContext.getEventType().equalsIgnoreCase(
						StagingContext.GENERAL_LEDGER_STAGING_EVENT)
						&& !stagingContext.getEventType().equalsIgnoreCase(
								StagingContext.CLIENT_ACCOUNTING_STAGING_EVENT)) {
					stagingContractClient.setSegmentBase(stagingContext
							.getCurrentSegmentBase());
					stagingContext.getCurrentSegmentBase().addContractClient(
							stagingContractClient);
				}
			} else {
				stagingContractClient.setSegmentRider(stagingContext
						.getCurrentSegmentRider());
				stagingContext.getCurrentSegmentRider().addContractClient(
						stagingContractClient);
			}

			stagingContext.setCurrentContractClient(stagingContractClient);

			SessionHelper.saveOrUpdate(stagingContractClient, database);

		} 
	//	else {
	//		System.out.println("OverrideStatus = " + this.getOverrideStatus());
	//	}
		return stagingContext;
	}
}
