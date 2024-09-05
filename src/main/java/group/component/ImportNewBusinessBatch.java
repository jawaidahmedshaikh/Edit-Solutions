/*
 * User: sdorman
 * Date: Sep 11, 2007
 * Time: 11:52:50 AM
 *
 * (c) 2000-2010 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */
package group.component;

import edit.common.CodeTableWrapper;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.services.db.hibernate.SessionHelper;
import electric.util.holder.booleanInOut;
import electric.util.holder.doubleInOut;
import fission.utility.DateTimeUtil;
import fission.utility.XMLUtil;
import group.BatchContractImportFile;
import group.BatchContractImportRecord;
import group.BatchContractImportRecordLog;
import group.BatchContractSetup;
import group.CaseProductUnderwriting;
import group.DepartmentLocation;
import group.Enrollment;
import group.business.Group;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.hibernate.criterion.Restrictions;

import com.google.common.base.Joiner;

import contract.FilteredProduct;
import productbuild.FilteredQuestionnaire;
import role.ClientRole;
import webservice.SEGRequestDocument;
import webservice.SEGResponseDocument;
import client.ClientAddress;
import client.ClientDetail;
import client.ContactInformation;
import client.TaxInformation;

/**
 * Imports data to create new business contracts in a "batch mode". The import
 * document can contain information for many contracts. Each one is created by
 * building the appropriate request documents and calling the GroupComponent's
 * addClient and addSegmentToContractGroup services.
 * <p/>
 * NOTE: Dates in the import file are in the format of mm/dd/yyyy which is the
 * same format the back-end service expects, therefore, no date translation is
 * needed.
 */
public class ImportNewBusinessBatch {
	private Document importDocument;
	private BatchContractSetup batchContractSetup;
	private String operator;

	// Literal strings for xml tags

	private final static String NEW_BUSINESS_CONTRACTVO = "NewBusinessContractVO";

	private final static String BENEFICIARIES_EQUALLY_SPLIT = "BeneficiariesEquallySplit";

	// BaseSegment fields
	private final static String BASE_SEGMENTVO = "BaseSegmentVO";
	private final static String ISSUE_STATECT = "IssueStateCT";
	private final static String EFFECTIVE_DATE = "EffectiveDate";
	private final static String APPLICATION_SIGNED_DATE = "ApplicationSignedDate";
	private final static String APPLICATION_SIGNED_STATECT = "ApplicationSignedStateCT";
	private final static String DEPARTMENT_CODE = "DepartmentCode";
	private final static String DEDUCTION_AMOUNT_OVERRIDE = "DeductionAmountOverride";
	private final static String DEDUCTION_AMOUNT_EFFECTIVE_DATE = "DeductionAmountEffectiveDate";
	private final static String ANNUAL_PREMIUM = "AnnualPremium";

	// Rider fields
	private final static String RIDER_SEGMENTVO = "RiderSegmentVO";
	private final static String COVERAGE = "Coverage";
	private final static String UNITS = "Units";
	private final static String EOB_MULTIPLE = "EOBMultiple";
	private final static String GIO_OPTION = "GIOOption";
	private final static String GROUP_PLAN = "GroupPlan";
	private final static String OPTION_CODE = "OptionCodeCT";
	private final static String RATED_GENDER_CT = "RatedGenderCT";

	// Life fields
	private final static String LIFEVO = "LifeVO";
	private final static String FACE_AMOUNT = "FaceAmount";
	private final static String DEATH_BENEFIT_OPTIONCT = "DeathBenefitOptionCT";

	// ContractClient fields
	private final static String CONTRACT_CLIENTVO = "ContractClientVO";
	private final static String RELATIONSHIP_TO_EMPLOYEECT = "RelationshipToEmployeeCT";
	private final static String ROLE_TYPECT = "RoleTypeCT";
	private final static String BENE_RELATIONSHIP_TO_EMPLOYEE = "BeneRelationshipToEmployee";
	private final static String BENE_RELATIONSHIP_TO_INSURED = "BeneRelationshipToInsured";
	private final static String EMPLOYEE_IDENTIFICATION = "EmployeeIdentification";

	// ClientDetail fields
	private final static String CLIENT_DETAILVO = "ClientDetailVO";
	private final static String LASTNAME = "LastName";
	private final static String FIRSTNAME = "FirstName";
	private final static String MIDDLENAME = "MiddleName";
	private final static String BIRTHDATE = "BirthDate";
	private final static String TAXID = "TaxIdentification";
	private final static String GENDERCT = "GenderCT";
	private final static String OCCUPATION = "Occupation";

	// ClientAddress fields
	private final static String CLIENT_ADDRESSVO = "ClientAddressVO";
	private final static String ADDRESS_LINE1 = "AddressLine1";
	private final static String ADDRESS_LINE2 = "AddressLine2";
	private final static String CITY = "City";
	private final static String STATECT = "StateCT";
	private final static String COUNTRYCT = "CountryCT";
	private final static String ZIPCODE = "ZipCode";

	// ContactInformation fields
	private final static String CONTACT_INFORMATIONVO = "ContactInformationVO";
	private final static String CONTACTTYPECT = "ContactTypeCT";
	private final static String PHONE_EMAIL = "PhoneEmail";
	private final static String NAME = "Name";

	// ContractClientAllocation fields
	private final static String CONTRACT_CLIENT_ALLOCATIONVO = "ContractClientAllocationVO";
	private final static String ALLOCATION_PERCENT = "AllocationPercent";

	// QuestionnaireResponse fields
	private final static String QUESTIONNAIRE_RESPONSEVO = "QuestionnaireResponseVO";
	private final static String QUESTIONNAIRE_ID = "QuestionnaireId";
	private final static String RESPONSECT = "ResponseCT";

	// Other fields
	private final static String QUESTIONNAIRE_ID_FOR_INSURED_CLASSCT = "Tob"; // TobaccoUsage

	private final static String YES = "Y";
	private final static String NO = "N";

	private final static String INSURED_CLASSCT_SMOKER = "Smoker";
	private final static String INSURED_CLASSCT_NONSMOKER = "NonSmoker";

	// CodeTable table names
	private final static String CODETABLE_RELATIONSHIP_TO_EMPLOYEE = "RELATIONTOEMPLOYEE";
	private final static String CODETABLE_ROLE_TYPE = "LIFERELATIONTYPE";
	private final static String CODETABLE_GENDER = "GENDER";
	private final static String CODETABLE_DEATH_BENEFIT_OPTION = "DEATHBENOPT";
	private final static String CODETABLE_STATE = "STATE";
	private final static String CODETABLE_COUNTRY = "COUNTRY";
	private final static String CODETABLE_RESPONSE = "RESPONSE";
	private final static String CODETABLE_RIDERNAME = "RIDERNAME"; // Coverage for Rider

	// Split equal qualifiers
	private final static String SPLIT_EQUAL_PBE = "PBE";
	private final static String SPLIT_EQUAL_CBE = "CBE";
	private final static String SPLIT_EQUAL_ALL = "ALL";
	private final static String SPLIT_EQUAL_NONE = "NONE";

	// Counters
	private int successCount = 0;
	private int failureCount = 0;
	private int warningCount = 0;
	private int totalContractCount = 0;

	public ImportNewBusinessBatch(Document importDocument, Long batchContractSetupPK, String operator) {
		this.importDocument = importDocument;

		this.batchContractSetup = BatchContractSetup.findByPK(batchContractSetupPK);

		this.operator = operator;
	}

	/**
	 * Executes the batch import process, storing logging information in the
	 * specified database record.
	 * 
	 * @param sess         The database session with which to manipulate
	 *                     <code>importRecord</code>
	 * @param importRecord The database record to attach logging information to
	 */
	public void execute(org.hibernate.Session sess, BatchContractImportFile importRecord) {
		// Get all NewBusinessContractVO elements
		List<SEGResponseDocument> newBusinessContractVOElements = getNewBusinessContractVOElementsFromImportDocument();
		importRecord.setTotalRecords(newBusinessContractVOElements.size());

		// Loop through all NewBusinessContractVO elements, creating 1 contract for each
		// NewBusinessContractVO
		int recNum = 0;
		org.hibernate.Transaction tx = sess.beginTransaction();
		try {
			for (Iterator iterator = newBusinessContractVOElements.iterator(); iterator.hasNext();) {
				recNum += 1;
				Element newBusinessContractVOElement = (Element) iterator.next();

				BatchContractImportRecord contractRecord = new BatchContractImportRecord();
				contractRecord.setLabel(this.getLabelForContractElement(recNum, newBusinessContractVOElement));
				contractRecord.setRecordSequence(recNum);
				contractRecord.setBatchContractImportFileFK(importRecord.getBatchContractImportFilePk());
				contractRecord.setStatus(BatchContractImportFile.STATUS_PENDING);

				SEGResponseDocument responseDocument = createContract(newBusinessContractVOElement);

				SortedSet<BatchContractImportRecordLog> logs = new TreeSet<BatchContractImportRecordLog>();

				for (Object message : responseDocument.getResponseMessages()) {
					Element messageElt = (Element) message;
					String messageType = messageElt.element("MessageType").getText();
					String messageText = messageElt.element("Message").getText();
					BatchContractImportRecordLog log = new BatchContractImportRecordLog();
					log.setCreationTime(new EDITDateTime());
					log.setMessage(messageText);
					log.setBatchContractImportRecord(contractRecord);

					if (messageType.equalsIgnoreCase("error")) {
						log.setStatus(BatchContractImportFile.STATUS_ERROR);
					} else if (messageType.equalsIgnoreCase("warning")) {
						log.setStatus(BatchContractImportFile.STATUS_WARNING);
					} else {
						log.setStatus(BatchContractImportFile.STATUS_SUCCESS);
					}

					logs.add(log);
				}

				if (responseDocument.hasError()) {
					contractRecord.setStatus(BatchContractImportFile.STATUS_ERROR);
				} else if (responseDocument.hasFailure()) {
					contractRecord.setStatus(BatchContractImportFile.STATUS_WARNING);
				} else {
					contractRecord.setStatus(BatchContractImportFile.STATUS_SUCCESS);
				}

				importRecord.setModifiedTime(new EDITDateTime());
				importRecord.setCompletedTime(new EDITDateTime());
				sess.saveOrUpdate(importRecord);
				sess.saveOrUpdate(contractRecord);
				for (BatchContractImportRecordLog logRec : logs) {
					logRec.setBatchContractImportRecord(contractRecord);
					logRec.setBatchContractImportRecordFK(contractRecord.getBatchContractImportRecordPk());
					sess.saveOrUpdate(logRec);
				}
				contractRecord.setBatchContractImportRecordLogs(logs);

				if (recNum % 5 == 0) {
					sess.flush();
					sess.clear();
					tx.commit();

					tx = sess.beginTransaction();
					importRecord = (BatchContractImportFile) sess
							.createCriteria(BatchContractImportFile.class).add(Restrictions
									.eq("batchContractImportFilePk", importRecord.getBatchContractImportFilePk()))
							.uniqueResult();
				}
			}

			importRecord.setStatus(BatchContractImportFile.STATUS_SUCCESS);
			importRecord.setCompletedTime(new EDITDateTime());
			sess.update(importRecord);
			tx.commit();
		} finally {
			if (!tx.wasCommitted() && !tx.wasRolledBack()) {
				tx.rollback();
			}
		}

	}

	/**
	 * Process the imported data and execute the services to add the new contracts
	 * to persistence
	 *
	 * @return responseDocument for the newly added contract
	 */
	public List<SEGResponseDocument> execute() {
		List<SEGResponseDocument> responseDocuments = new ArrayList<SEGResponseDocument>();

		// Get all NewBusinessContractVO elements
		List<SEGResponseDocument> newBusinessContractVOElements = getNewBusinessContractVOElementsFromImportDocument();

		// Loop through all NewBusinessContractVO elements, creating 1 contract for each
		// NewBusinessContractVO
		for (Iterator iterator = newBusinessContractVOElements.iterator(); iterator.hasNext();) {
			Element newBusinessContractVOElement = (Element) iterator.next();

			SEGResponseDocument responseDocument = createContract(newBusinessContractVOElement);

			responseDocuments.add(responseDocument);

			incrementCounts(responseDocument);
		}

		addCountResponse(responseDocuments);

		return responseDocuments;
	}

	/**
	 * Gets a label used to represent the contract
	 * 
	 * @param position                     The position of the XML element in the
	 *                                     file
	 * @param newBusinessContractVOElement The XML element describing the contract
	 *                                     to create
	 * @return A representation of the contract
	 */
	public String getLabelForContractElement(int position, Element newBusinessContractVOElement) {
		// build up a collection of client names for informational purposes
		List clients = newBusinessContractVOElement.selectNodes(".//" + CONTRACT_CLIENTVO);
		SortedSet<String> clientNames = new TreeSet<String>();
		for (Iterator iterator = clients.iterator(); iterator.hasNext();) {
			Element client = (Element) iterator.next();

			// don't fail due to missing elements
			String lastName = "";
			String firstName = "";
			if (client.element(CLIENT_DETAILVO) != null) {
				if (client.element(CLIENT_DETAILVO).element(LASTNAME) != null) {
					lastName = client.element(CLIENT_DETAILVO).element(LASTNAME).getText();
				}

				if (client.element(CLIENT_DETAILVO).element(FIRSTNAME) != null) {
					firstName = client.element(CLIENT_DETAILVO).element(FIRSTNAME).getText();
				}
			}

			clientNames.add(firstName + " " + lastName);
		}
		String combinedNames = Joiner.on(", ").join(clientNames);

		return "Record #" + position + ", Clients: " + combinedNames;
	}

	/**
	 * Creates a contract from the NewBusinessContractVO element
	 *
	 * @param newBusinessContractVOElement Element containing NewBusinessContractVO
	 *                                     that will be processed to create a
	 *                                     contract
	 *
	 * @return responseDocument for the newly added contract
	 */
	public SEGResponseDocument createContract(Element newBusinessContractVOElement) {
		List responseMessages = new ArrayList();

		Group group = new GroupComponent();

		Document requestDocument = buildRequestDocumentForAddSegment(newBusinessContractVOElement, responseMessages);

		// Call the addSegmentToContractGroup service
		Document responseDocument = group.addSegmentToContractGroup(requestDocument);

		SEGResponseDocument segResponseDocument = convertToSEGResponseDocument(responseDocument, responseMessages);

		return segResponseDocument;
	}

	/**
	 * Adds a client to persistence using the addClient service. First checks to see
	 * if the client already exists in persistence. If so, returns the
	 * clientDetailPK. If not, builds the requestDocument and calls the service.
	 * Gets the clientDetailPK from the responseDocument of the service.
	 *
	 * @param clientDetailVOElement
	 * @param responseMessages      List of responses returned from the service.
	 *                              They are returned from this method via the
	 *                              arglist
	 *
	 * @return clientDetailPK of client in persistence. List of responseMessages
	 *         returned via arglist
	 */
	private Long addClient(Element clientDetailVOElement, List responseMessages) {
		Element taxIDElement = clientDetailVOElement.element(TAXID);

		// First check for existing client via TaxID
		Long clientDetailPK = checkForExistingTaxID(taxIDElement);

		// If no client was returned with the specified TaxID, try the name and
		// birthDate
		if (clientDetailPK == null) {
			clientDetailPK = checkForExistingClient(clientDetailVOElement);
		} else {
			// if the client exists, check if they have a birth date entered
			// if not, and a birth date is coming in on the file, update that entry
			ClientDetail client = ClientDetail.findBy_ClientDetailPK(clientDetailPK);
			if (clientDetailVOElement.element(BIRTHDATE) != null) {
				String incomingBirthDate = XMLUtil.getText(clientDetailVOElement.element(BIRTHDATE));
				if (client.getBirthDate() == null && incomingBirthDate != null) {
					SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
					client.setBirthDate(new EDITDate(incomingBirthDate));
					SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
				}
			}
		}

		// If a client still wasn't returned, create a new client
		if (clientDetailPK == null) {
			// Create the input for the service
			Document requestDocument = buildRequestDocumentForAddClient(clientDetailVOElement);

			// XMLUtil.printDocumentToSystemOut(requestDocument.getDocument());

			// Call the service to add the client
			clientDetailPK = callAddClientService(requestDocument, responseMessages);
		}

		return clientDetailPK;
	}

	/**
	 * Determines if the client is defined in the clientDetailVOElement element or
	 * not. The lastName element may exist but their text may be empty which means
	 * the client has not been defined.
	 *
	 * @param clientDetailVOElement ClientDetailVO element containing the lastName
	 *                              field
	 *
	 * @return true if the lastName has been defined in the element, false otherwise
	 */
	private boolean clientIsNotEmpty(Element clientDetailVOElement) {
		Element lastNameElement = clientDetailVOElement.element(LASTNAME);
		if (lastNameElement == null) {

		}

		String lastName = XMLUtil.getText(lastNameElement);

		if (lastName != null) {
			return true;
		} else {
			return false;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Builds //
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Builds the ClientDetail with the information given in the
	 * clientDetailVOElement
	 *
	 * @param clientDetailVOElement Element object containing ClientDetailVO from
	 *                              the import file
	 *
	 * @return newly created ClientDetail object
	 */
	private ClientDetail buildClientDetail(Element clientDetailVOElement) {
		Element lastNameElement = clientDetailVOElement.element(LASTNAME);
		Element firstNameElement = clientDetailVOElement.element(FIRSTNAME);
		Element middleNameElement = clientDetailVOElement.element(MIDDLENAME);

		ClientDetail clientDetail = new ClientDetail();

		clientDetail.setLastName(XMLUtil.getText(lastNameElement));
		clientDetail.setFirstName(XMLUtil.getText(firstNameElement));
		clientDetail.setMiddleName(XMLUtil.getText(middleNameElement));

		clientDetail.setBirthDate(determineDate(clientDetailVOElement.element(BIRTHDATE)));
		clientDetail.setGenderCT(determineGenderCT(clientDetailVOElement.element(GENDERCT)));
		clientDetail.setTaxIdentification(getTaxIdentification(clientDetailVOElement));
		clientDetail.setOccupation(XMLUtil.getText(clientDetailVOElement.element(OCCUPATION)));
		clientDetail.setTrustTypeCT(ClientDetail.TRUSTTYPECT_INDIVIDUAL);

		return clientDetail;
	}

	/**
	 * Builds a new ClientAddress using information in the given
	 * clientDetailVOElement.
	 *
	 * @param clientDetailVOElement Element object containing ClientDetailVO in
	 *                              import file
	 *
	 * @return newly created ClientAddress object or null if ClientAddressVO does
	 *         not exist in import file
	 */
	private ClientAddress buildClientAddress(Element clientDetailVOElement) {
		Element clientAddressVOElement = clientDetailVOElement.element(CLIENT_ADDRESSVO);

		if (clientAddressVOElement != null) {
			ClientAddress clientAddress = new ClientAddress();

			clientAddress.setAddressTypeCT(ClientAddress.CLIENT_PRIMARY_ADDRESS);
			clientAddress.setAddressLine1(XMLUtil.getText(clientAddressVOElement.element(ADDRESS_LINE1)));
			clientAddress.setAddressLine2(XMLUtil.getText(clientAddressVOElement.element(ADDRESS_LINE2)));
			clientAddress.setCity(XMLUtil.getText(clientAddressVOElement.element(CITY)));
			clientAddress.setStateCT(determineStateCT(clientAddressVOElement.element(STATECT)));
			clientAddress.setZipCode(XMLUtil.getText(clientAddressVOElement.element(ZIPCODE)));
			clientAddress.setCountryCT(determineCountryCT(clientAddressVOElement.element(COUNTRYCT)));

			return clientAddress;
		} else {
			return null;
		}
	}

	/**
	 * Builds a default TaxInformation and sets the taxIdType
	 *
	 * @param clientDetailVOElement ClientDetailVO Element from the import file
	 *
	 * @return
	 */
	private TaxInformation buildTaxInformation(Element clientDetailVOElement) {
		String taxIdType = determineTaxIdType(clientDetailVOElement);

		TaxInformation taxInformation = new TaxInformation();

		taxInformation.setTaxIdTypeCT(taxIdType);

		return taxInformation;
	}

	/**
	 * Builds the ContactInformation objects for each ContactInformationVO defined
	 * in the ClientDetailVO Element
	 *
	 * @param clientDetailVOElement Element containing ClientDetailVO from the
	 *                              import file
	 *
	 * @return collection of ContactInformation objects
	 */
	private List buildContactInformations(Element clientDetailVOElement) {
		List contactInformations = new ArrayList();

		List contactInformationElements = clientDetailVOElement.elements(CONTACT_INFORMATIONVO);

		for (Iterator iterator = contactInformationElements.iterator(); iterator.hasNext();) {
			Element contactInformationElement = (Element) iterator.next();

			Element contactTypeCTElement = contactInformationElement.element(CONTACTTYPECT);

			Element phoneEmailElement = contactInformationElement.element(PHONE_EMAIL);

			ContactInformation contactInformation = new ContactInformation();

			contactInformation.setContactTypeCT(XMLUtil.getText(contactTypeCTElement));

			contactInformation.setPhoneEmail(XMLUtil.getText(phoneEmailElement));

			contactInformations.add(contactInformation);
		}

		return contactInformations;
	}

	private List saveContactInformations(Element clientDetailVOElement, Long clientDetailPK)
	{
		List contactInformations = new ArrayList();

		List contactInformationElements = clientDetailVOElement.elements(CONTACT_INFORMATIONVO);

		Element firstNameElement = clientDetailVOElement.element(FIRSTNAME);
		Element lastNameElement = clientDetailVOElement.element(LASTNAME);

		for (Iterator iterator = contactInformationElements.iterator(); iterator.hasNext();)
		{
			Element contactInformationElement = (Element) iterator.next();

			Element contactTypeCTElement = contactInformationElement.element(CONTACTTYPECT);

			Element phoneEmailElement = contactInformationElement.element(PHONE_EMAIL);

			Element nameElement = contactInformationElement.element(NAME);

			ContactInformation contactInformation = new ContactInformation();

			contactInformation.setContactTypeCT(XMLUtil.getText(contactTypeCTElement));

			contactInformation.setPhoneEmail(XMLUtil.getText(phoneEmailElement));

			contactInformation.setName(XMLUtil.getText(firstNameElement) + " " + XMLUtil.getText(lastNameElement));

			contactInformation.setClientDetailFK(clientDetailPK);
			
			contactInformation.save();
			contactInformations.add(contactInformation);
			
		}

		return contactInformations;
	}

	/**
	 * Builds the RequestDocument that will be sent to the addClient service
	 *
	 * @param clientDetailVOElement Element containing ClientDetailVO from import
	 *                              file
	 *
	 * @return SEGRequestDocument containing the correct elements required for the
	 *         addClient service
	 */
	private Document buildRequestDocumentForAddClient(Element clientDetailVOElement) {
		SEGRequestDocument requestDocument = new SEGRequestDocument();

		ClientDetail clientDetail = buildClientDetail(clientDetailVOElement);
		TaxInformation taxInformation = buildTaxInformation(clientDetailVOElement);
		ClientAddress clientAddress = buildClientAddress(clientDetailVOElement);

		List contactInformations = buildContactInformations(clientDetailVOElement);
		clientDetail.setContactInformations(new HashSet(contactInformations));

		Element clientDetailElement = clientDetail.getAsElement(true, true);
		Element taxInformationElement = taxInformation.getAsElement(true, true);

		requestDocument.addToRequestParameters(clientDetailElement);
		requestDocument.addToRequestParameters(taxInformationElement);

		if (clientAddress != null) {
			Element clientAddressElement = clientAddress.getAsElement(true, true);
			requestDocument.addToRequestParameters(clientAddressElement);
		}

		for (Iterator<ContactInformation> iterator = contactInformations.iterator(); iterator.hasNext();) {
			ContactInformation contactInformation = (ContactInformation) iterator.next();
			contactInformation.setName(clientDetail.getFirstName() + " " + clientDetail.getLastName());
			Element contactInformationElement = contactInformation.getAsElement(true, true);
			clientDetailElement.add(contactInformationElement);
		}

		return requestDocument.getDocument();
	}

	private Document buildRequestDocumentForAddSegment(Element newBusinessContractVOElement, List responseMessages) {
		SEGRequestDocument requestDocument = new SEGRequestDocument();

		Element batchContractSetupPKElement = new DefaultElement("BatchContractSetupPK");
		XMLUtil.setText(batchContractSetupPKElement, this.batchContractSetup.getBatchContractSetupPK());

		Element operatorElement = new DefaultElement("Operator");
		XMLUtil.setText(operatorElement, this.operator);

		// Override the equally split indicator if the split appears to be equal
		Element beneficiariesEquallySplitElement = new DefaultElement("BeneficiariesEquallySplit"); // only refers to
																									// PBEs
		Element CBEEquallySplitElement = new DefaultElement("CBEEquallySplit"); // for CBEs
		Element beneficiariesEquallySplitElementFromImport = newBusinessContractVOElement
				.element(BENEFICIARIES_EQUALLY_SPLIT);

		// Pull the splitEqual flag from the import file
		// boolean importSplitEqualFlag = new
		// Boolean(XMLUtil.getText(beneficiariesEquallySplitElementFromImport));

		List<Element> clients = this.getContractClientVOElements(getBaseSegmentVOElement(newBusinessContractVOElement));

		// Get client roles that may require SplitEqual status
		String[] splitEqualBeneRoles = ClientRole.BATCH_IMPORT_BENE_ROLES;
		boolean setPBESplitEqual = false;
		boolean setCBESplitEqual = false;
		String splitEqual = "";

		// For each eligible client role, determine if splitEqual should be set
		for (String splitEqualBeneRole : splitEqualBeneRoles) {
			HashSet<Double> distinctPercentages = new HashSet<Double>();
			double totalAllocPercent = 0;
			List<Element> beneElements = new ArrayList<Element>();
			for (Element clientElt : clients) {
				String roleTypeCT = getRoleTypeCT(clientElt);
				if (roleTypeCT == null || !(roleTypeCT.equals(splitEqualBeneRole)))
					continue;

				beneElements.add(clientElt);
				double currPercentage = getAllocationPercent(clientElt).doubleValue();
				totalAllocPercent += currPercentage;
				distinctPercentages.add(currPercentage);
			}

			// set the split equal indicator if there are multiple benes with the
			// same split percent that doesn't sum to 1, but comes within 1% (e.g. 0.33,
			// 0.33, 0.33)
			// Currently the splitEqual flag sent in from the import file only refers to the
			// PBEs... here we also check CBEs for a similar scenario
			if (splitEqualBeneRole.equalsIgnoreCase(ClientRole.ROLETYPECT_PRIMARYBENEFICIARY)) {
				if (distinctPercentages.size() == 1 && beneElements.size() > 1 && totalAllocPercent != 1
						&& Math.abs(totalAllocPercent - 1) <= 0.011) {
					setPBESplitEqual = true;
					XMLUtil.setText(beneficiariesEquallySplitElement, "true");
				} else {
					setPBESplitEqual = new Boolean(XMLUtil.getText(beneficiariesEquallySplitElementFromImport));
					XMLUtil.setText(beneficiariesEquallySplitElement,
							XMLUtil.getText(beneficiariesEquallySplitElementFromImport));
				}
			} else if (splitEqualBeneRole.equalsIgnoreCase(ClientRole.ROLETYPECT_CONTINGENTBENEFICIARY)) {
				if (distinctPercentages.size() == 1 && beneElements.size() > 1 && totalAllocPercent != 1
						&& Math.abs(totalAllocPercent - 1) <= 0.011) {
					setCBESplitEqual = true;
				}
			}
		}

		if (setPBESplitEqual && setCBESplitEqual) {
			splitEqual = ImportNewBusinessBatch.SPLIT_EQUAL_ALL;
			XMLUtil.setText(CBEEquallySplitElement, "Y");
		} else if (setPBESplitEqual && !setCBESplitEqual) {
			splitEqual = ImportNewBusinessBatch.SPLIT_EQUAL_PBE;
			XMLUtil.setText(CBEEquallySplitElement, "N");
		} else if (!setPBESplitEqual && setCBESplitEqual) {
			splitEqual = ImportNewBusinessBatch.SPLIT_EQUAL_CBE;
			XMLUtil.setText(CBEEquallySplitElement, "Y");
		} else {
			splitEqual = ImportNewBusinessBatch.SPLIT_EQUAL_NONE;
			XMLUtil.setText(CBEEquallySplitElement, "N");
		}

		// Add the VO elements to the requestParameters
		requestDocument.addToRequestParameters(batchContractSetupPKElement);
		requestDocument.addToRequestParameters(operatorElement);
		requestDocument.addToRequestParameters(beneficiariesEquallySplitElement);
		requestDocument.addToRequestParameters(CBEEquallySplitElement);

		// Base Segment
		processBaseSegment(newBusinessContractVOElement, requestDocument);

		// ContractClients
		processContractClients(newBusinessContractVOElement, splitEqual, requestDocument, responseMessages);

		// Riders
		// ECK
		// no riders for A&H
		if (!batchContractSetup.getFilteredProduct().getProductStructure().getGroupProductName().equals("A&H")) {

			Set<String> coverageCodes = processRiderSegments(newBusinessContractVOElement, splitEqual, requestDocument,
					responseMessages);
			// add anything that is required based on the batch setup but not added in the
			// previous step
			this.processRiderSegmentsFromUnderwriting(requestDocument, responseMessages, coverageCodes);
		}

		return requestDocument.getDocument();
	}

	/**
	 * Builds the SegmentInformationVO element for the Base Segment
	 *
	 * @return Element object containing the fully built SegmentInformationVO
	 */
	private Element buildBaseSegmentInformationVO(Element baseSegmentVOElement) {
		Element segmentInformationVO = new DefaultElement("SegmentInformationVO");

		// FilteredProductPK
		Element filteredProductPKElement = new DefaultElement("FilteredProductPK");
		XMLUtil.setText(filteredProductPKElement, this.batchContractSetup.getFilteredProductFK());

		// ContractNumber
		Element contractNumberElement = new DefaultElement("ContractNumber");

		// DepartmentLocationPK
		Element departmentLocationPKElement = new DefaultElement("DepartmentLocationPK");
		XMLUtil.setText(departmentLocationPKElement, getDepartmentLocationPK(baseSegmentVOElement));

		// IssueStateCT
		Element issueStateCTElement = new DefaultElement("IssueStateCT");
		String issueStateOfSegment = determineStateCT(baseSegmentVOElement.element(ISSUE_STATECT));
		String issueStateOfBatch = this.batchContractSetup.getIssueStateCT();
		boolean useBatchState = issueStateOfBatch != null && !issueStateOfBatch.trim().equals("");
		XMLUtil.setText(issueStateCTElement, useBatchState ? issueStateOfBatch : issueStateOfSegment);

		// IssueStateORInd
		Element issueStateORIndElement = new DefaultElement("IssueStateORInd");
		XMLUtil.setText(issueStateORIndElement, useBatchState ? "Y" : "N");

		// EstateOfTheInsured
		Element estateOfTheInsuredElement = new DefaultElement("EstateOfTheInsured");
		XMLUtil.setText(estateOfTheInsuredElement, "N");

		// FaceAmount
		Element faceAmountElement = new DefaultElement("FaceAmount");
		XMLUtil.setText(faceAmountElement, getFaceAmount(baseSegmentVOElement));

		// EffectiveDate
		Element effectiveDateElement = new DefaultElement("EffectiveDate");
		EDITDate effectiveDate = this.batchContractSetup.getEffectiveDate();

		if (effectiveDate == null) {
			// If default not provided on BatchContractSetup, use input value
			effectiveDate = determineDate(baseSegmentVOElement.element(EFFECTIVE_DATE));
		}

		XMLUtil.setText(effectiveDateElement, DateTimeUtil.formatEDITDateAsMMDDYYYY(effectiveDate));

		// ApplicationSignedDate
		Element applicationSignedDateElement = new DefaultElement("ApplicationSignedDate");
		EDITDate applicationSignedDate = this.batchContractSetup.getApplicationSignedDate();

		if (applicationSignedDate == null) {
			// If default not provided on BatchContractSetup, use input value
			applicationSignedDate = determineDate(baseSegmentVOElement.element(APPLICATION_SIGNED_DATE));
		}

		XMLUtil.setText(applicationSignedDateElement, DateTimeUtil.formatEDITDateAsMMDDYYYY(applicationSignedDate));

		// ApplicationReceivedDate
		Element applicationReceivedDateElement = new DefaultElement("ApplicationReceivedDate");
		XMLUtil.setText(applicationReceivedDateElement,
				DateTimeUtil.formatEDITDateAsMMDDYYYY(this.batchContractSetup.getReceiptDate()));

		// ApplicationSignedStateCT
		Element applicationSignedStateElement = new DefaultElement("ApplicationSignedStateCT");
		String applicationSignedStateCT = this.batchContractSetup.getApplicationSignedStateCT();

		if (applicationSignedStateCT == null) {
			// If default not provided on BatchContractSetup, use input value
			applicationSignedStateCT = getApplicationSignedStateCT(baseSegmentVOElement);
		}

		XMLUtil.setText(applicationSignedStateElement, applicationSignedStateCT);

		// DeductionAmountOverride
		// ECK
		Element deductionAmountOverrideElement = new DefaultElement(ImportNewBusinessBatch.DEDUCTION_AMOUNT_OVERRIDE);
		Element annualPremiumElement = new DefaultElement(ImportNewBusinessBatch.ANNUAL_PREMIUM);
		String overrideAmount = "";
		String annualPremium = "";
		// per carrie, use annualPremium for deductionAmountOverride for A&H buiness
		if (batchContractSetup.getFilteredProduct().getProductStructure().getBusinessContractName().equals("A&H")) {
			annualPremium = XMLUtil
					.getText(baseSegmentVOElement.element(ImportNewBusinessBatch.DEDUCTION_AMOUNT_OVERRIDE));
		} else {
			overrideAmount = XMLUtil
					.getText(baseSegmentVOElement.element(ImportNewBusinessBatch.DEDUCTION_AMOUNT_OVERRIDE));
		}
		XMLUtil.setText(annualPremiumElement, annualPremium);
		XMLUtil.setText(deductionAmountOverrideElement, overrideAmount);
		// ECK
		Element ratedGenderElement = new DefaultElement("RatedGenderCT");
		XMLUtil.setText(ratedGenderElement, getRatedGenderCT(baseSegmentVOElement));

		Element groupPlanElement = new DefaultElement("GroupPlan");
		XMLUtil.setText(groupPlanElement, getGroupPlan(baseSegmentVOElement));

		Element optionCodeElement = new DefaultElement("OptionCodeCT");
		XMLUtil.setText(optionCodeElement, getOptionCode(baseSegmentVOElement));

		// DeductionAmountEffectiveDate
		Element deductionAmountEffectiveDateElement = new DefaultElement(
				ImportNewBusinessBatch.DEDUCTION_AMOUNT_EFFECTIVE_DATE);
		EDITDate deductionAmountEffectiveDate = determineDate(
				baseSegmentVOElement.element(DEDUCTION_AMOUNT_EFFECTIVE_DATE));
		if (deductionAmountEffectiveDate == null && overrideAmount != null && overrideAmount != "") {
			XMLUtil.setText(deductionAmountEffectiveDateElement,
					DateTimeUtil.formatEDITDateAsMMDDYYYY(this.batchContractSetup.getEffectiveDate()));
		} else {
			XMLUtil.setText(deductionAmountEffectiveDateElement,
					DateTimeUtil.formatEDITDateAsMMDDYYYY(deductionAmountEffectiveDate));
		}

		// DeathBenefitOptionCT
		Element deathBenefitOptionCTElement = new DefaultElement("DeathBenefitOptionCT");
		XMLUtil.setText(deathBenefitOptionCTElement, getDeathBenefitOptionCT(baseSegmentVOElement));

		// NonForfeitureOptionCT
		Element nonForfeitureOptionCTElement = new DefaultElement("NonForfeitureOptionCT");
		XMLUtil.setText(nonForfeitureOptionCTElement, this.batchContractSetup.getNonForfeitureOptionCT());

		// ExchangeInd
		Element exchangeIndElement = new DefaultElement("ExchangeInd");

		segmentInformationVO.add(filteredProductPKElement);
		segmentInformationVO.add(contractNumberElement);
		segmentInformationVO.add(departmentLocationPKElement);
		segmentInformationVO.add(issueStateCTElement);
		segmentInformationVO.add(issueStateORIndElement);
		segmentInformationVO.add(estateOfTheInsuredElement);
		segmentInformationVO.add(faceAmountElement);
		segmentInformationVO.add(ratedGenderElement);
		segmentInformationVO.add(groupPlanElement);
		segmentInformationVO.add(optionCodeElement);
		segmentInformationVO.add(effectiveDateElement);
		segmentInformationVO.add(applicationSignedDateElement);
		segmentInformationVO.add(applicationReceivedDateElement);
		segmentInformationVO.add(applicationSignedStateElement);
		segmentInformationVO.add(deductionAmountOverrideElement);
		segmentInformationVO.add(annualPremiumElement);
		segmentInformationVO.add(deductionAmountEffectiveDateElement);
		segmentInformationVO.add(deathBenefitOptionCTElement);
		segmentInformationVO.add(nonForfeitureOptionCTElement);

		return segmentInformationVO;
	}

	/**
	 * Builds the ClientInformationVO element
	 *
	 * @param clientDetailPK
	 * @param relationshipToEmployeeCT
	 * @param roleTypeCT
	 *
	 * @return Element object containing the fully built ClientInformationVO
	 */
	private Element buildClientInformationVO(Long clientDetailPK, String relationshipToEmployeeCT, String roleTypeCT,
			String splitEqual, EDITBigDecimal allocationPercent, String beneficiaryRelationshipToEmployee,
			String beneficiaryRelationshipToInsured, String employeeIdentification) {
		Element clientInformationElement = new DefaultElement("ClientInformationVO");

		Element clientDetailPKElement = new DefaultElement("ClientDetailPK");
		XMLUtil.setText(clientDetailPKElement, clientDetailPK);

		Element relationshipToEmployeeCTElement = new DefaultElement("RelationshipToEmployeeCT");
		XMLUtil.setText(relationshipToEmployeeCTElement, relationshipToEmployeeCT);

		Element employeeIdentificationElement = new DefaultElement("EmployeeIdentification");
		XMLUtil.setText(employeeIdentificationElement, employeeIdentification);

		Element contractClientInformationElement = buildContractClientInformationVO(roleTypeCT, splitEqual,
				allocationPercent, beneficiaryRelationshipToEmployee, beneficiaryRelationshipToInsured);

		clientInformationElement.add(clientDetailPKElement);
		clientInformationElement.add(relationshipToEmployeeCTElement);
		clientInformationElement.add(employeeIdentificationElement);
		clientInformationElement.add(contractClientInformationElement);

		return clientInformationElement;
	}

	/**
	 * Builds the QuestionnaireInformationVO for the document that will get passed
	 * to the add segment service
	 *
	 * @param responseCT
	 * @param filteredQuestionnairePK
	 *
	 * @return Element containing the newly built QuestionnaireInformationVO
	 */
	private Element buildQuestionnaireInformationVO(String responseCT, Long filteredQuestionnairePK) {
		Element questionnaireInformationElement = new DefaultElement("QuestionnaireResponseVO");

		Element filteredQuestionnairePKElement = new DefaultElement("FilteredQuestionnaireFK");
		XMLUtil.setText(filteredQuestionnairePKElement, filteredQuestionnairePK);

		Element responseCTElement = new DefaultElement("ResponseCT");
		XMLUtil.setText(responseCTElement, responseCT);

		questionnaireInformationElement.add(filteredQuestionnairePKElement);
		questionnaireInformationElement.add(responseCTElement);

		return questionnaireInformationElement;
	}

	/**
	 * Builds the ContractClientInformationVO element
	 *
	 * @param roleTypeCT
	 * @param allocationPercent
	 *
	 * @return Element object containing the fully built ContractClientInformationVO
	 */
	private Element buildContractClientInformationVO(String roleTypeCT, String splitEqual,
			EDITBigDecimal allocationPercent, String beneficiaryRelationshipToEmployee,
			String beneficiaryRelationshipToInsured) {
		Element contractClientInformationElement = new DefaultElement("ContractClientInformationVO");

		Element roleTypeCTElement = new DefaultElement("RoleTypeCT");
		XMLUtil.setText(roleTypeCTElement, roleTypeCT);

		Element classCTElement = new DefaultElement("ClassCT");

		Element tableRatingCTElement = new DefaultElement("TableRatingCT");

		if ((splitEqual.equalsIgnoreCase(SPLIT_EQUAL_ALL) || splitEqual.equalsIgnoreCase(SPLIT_EQUAL_PBE))
				&& roleTypeCT != null && roleTypeCT.equals(ClientRole.ROLETYPECT_PRIMARYBENEFICIARY)) {
			allocationPercent = new EDITBigDecimal("0");
		}
		if ((splitEqual.equalsIgnoreCase(SPLIT_EQUAL_ALL) || splitEqual.equalsIgnoreCase(SPLIT_EQUAL_CBE))
				&& roleTypeCT != null && roleTypeCT.equals(ClientRole.ROLETYPECT_CONTINGENTBENEFICIARY)) {
			allocationPercent = new EDITBigDecimal("0");
		}

		Element beneficiaryAllocationElement = new DefaultElement("BeneficiaryAllocation");
		XMLUtil.setText(beneficiaryAllocationElement, allocationPercent);

		Element beneficiaryAllocationTypeElement = new DefaultElement("BeneficiaryAllocationType");
		XMLUtil.setText(beneficiaryAllocationTypeElement, "PERCENT");

		Element beneficiaryRelationshipToInsuredElement = new DefaultElement("BeneficiaryRelationshipToInsured");
		XMLUtil.setText(beneficiaryRelationshipToInsuredElement, beneficiaryRelationshipToInsured);

		contractClientInformationElement.add(roleTypeCTElement);
		contractClientInformationElement.add(classCTElement);
		contractClientInformationElement.add(beneficiaryAllocationElement);
		contractClientInformationElement.add(beneficiaryAllocationTypeElement);
		contractClientInformationElement.add(tableRatingCTElement);
		contractClientInformationElement.add(beneficiaryRelationshipToInsuredElement);

		return contractClientInformationElement;
	}

	/**
	 * Builds the CandidateRiderVO element
	 *
	 * @param riderSegmentVOElement Element containing the RiderSegmentVO from the
	 *                              import file
	 * @param responseMessages      messages containing responses from the addClient
	 *                              service
	 *
	 * @return Element object containing the fully built CandidateRiderVO
	 */
	private Element buildCandidateRiderVO(Element riderSegmentVOElement, String splitEqual, List responseMessages) {
		Element candidateRiderElement = new DefaultElement("CandidateRiderVO");

		Element coverageElement = new DefaultElement("Coverage");
		XMLUtil.setText(coverageElement, getCoverage(riderSegmentVOElement));

		Element qualifierElement = new DefaultElement("Qualifier"); // Value is not set because it is not used on
																	// addSegment service

		Element requiredOptionalCTElement = new DefaultElement("RequiredOptionalCT"); // Value is not set because it is
																						// not used on addSegment
																						// service

		Element effectiveDateElement = new DefaultElement("EffectiveDate");
		// If value is not set, addSegment will use base Segment's effectiveDate
		XMLUtil.setText(effectiveDateElement, getEffectiveDate(riderSegmentVOElement));

		Element unitsElement = new DefaultElement("Units");
		XMLUtil.setText(unitsElement, getUnits(riderSegmentVOElement));

		Element faceAmountElement = new DefaultElement("FaceAmount");
		XMLUtil.setText(faceAmountElement, getFaceAmount(riderSegmentVOElement));

		Element eobMultipleElement = new DefaultElement("EOBMultiple");
		XMLUtil.setText(eobMultipleElement, getEOBMultiple(riderSegmentVOElement));

		Element gioOptionElement = new DefaultElement("GIOOption");
		XMLUtil.setText(gioOptionElement, getGIOOption(riderSegmentVOElement));

		Element groupPlan = new DefaultElement("GroupPlan");
		XMLUtil.setText(groupPlan, getGroupPlan(riderSegmentVOElement));

		Element optionCodeCT = new DefaultElement("OptionCodeCT");
		XMLUtil.setText(optionCodeCT, getOptionCode(riderSegmentVOElement));

		Element ratedGender = new DefaultElement("RatedGenderCT");
		XMLUtil.setText(ratedGender, getRatedGenderCT(riderSegmentVOElement));

		candidateRiderElement.add(coverageElement);
		candidateRiderElement.add(qualifierElement);
		candidateRiderElement.add(requiredOptionalCTElement);
		candidateRiderElement.add(effectiveDateElement);
		candidateRiderElement.add(unitsElement);
		candidateRiderElement.add(faceAmountElement);
		candidateRiderElement.add(eobMultipleElement);
		candidateRiderElement.add(gioOptionElement);
		candidateRiderElement.add(ratedGender);
		candidateRiderElement.add(groupPlan);
		candidateRiderElement.add(optionCodeCT);

		List contractClientVOElements = getContractClientVOElements(riderSegmentVOElement);

		// ContractClients are not required for Riders
		if (contractClientVOElements.size() > 0) {
			for (Iterator iterator = contractClientVOElements.iterator(); iterator.hasNext();) {
				Element contractClientVOElement = (Element) iterator.next();

				Element clientInformationVO = processContractClient(contractClientVOElement, splitEqual,
						responseMessages);

				if (clientInformationVO != null) {
					candidateRiderElement.add(clientInformationVO);
				}
			}
		}

		return candidateRiderElement;
	}

	/**
	 * Processes the information from the BaseSegmentVO section of the import file.
	 * Turns that information into the SegmentInformationVO and puts it into the
	 * request parameters of the request document.
	 *
	 * @param newBusinessContractVOElement Element containing the
	 *                                     NewBusinessContractVO in the import file
	 * @param requestDocument              SEGRequestDocument that will be used to
	 *                                     in the add segment service
	 */
	private void processBaseSegment(Element newBusinessContractVOElement, SEGRequestDocument requestDocument) {
		Element baseSegmentVOElement = getBaseSegmentVOElement(newBusinessContractVOElement);

		Element segmentInformationVOElement = buildBaseSegmentInformationVO(baseSegmentVOElement);

		requestDocument.addToRequestParameters(segmentInformationVOElement);
	}

	/**
	 * Processes the information from the RiderSegmentVO sections of the import
	 * file. Turns that information into CandidateRiderVOs and puts them into the
	 * request parameters of the request document.
	 *
	 * @param newBusinessContractVOElement Element containing the
	 *                                     NewBusinessContractVO in the import file
	 * @param requestDocument              SEGRequestDocument that will be used to
	 *                                     in the add segment service
	 * @param responseMessages             messages containing responses from the
	 *                                     addClient service
	 * @return A set of coverages that were created
	 */
	private Set<String> processRiderSegments(Element newBusinessContractVOElement, String splitEqual,
			SEGRequestDocument requestDocument, List responseMessages) {
		List riderSegmentVOElements = getRiderSegmentVOElements(newBusinessContractVOElement);

		Set<String> coverageCodes = new HashSet<String>();
		for (Iterator iterator = riderSegmentVOElements.iterator(); iterator.hasNext();) {
			Element riderSegmentVOElement = (Element) iterator.next();

			Element candidateRiderVOElement = buildCandidateRiderVO(riderSegmentVOElement, splitEqual,
					responseMessages);
			coverageCodes.add(XMLUtil.getText(candidateRiderVOElement.element("Coverage")));

			requestDocument.addToRequestParameters(candidateRiderVOElement);
		}
		return coverageCodes;
	}

	/**
	 * Creates a CandidateRiderVO for any riders required by the case underwriting
	 * 
	 * @param requestDocument
	 * @param responseMessages
	 * @param existingRiderCodes
	 */
	private void processRiderSegmentsFromUnderwriting(SEGRequestDocument requestDocument, List responseMessages,
			Set<String> existingRiderCodes) {
		Enrollment enrollment = batchContractSetup.getEnrollment();

		FilteredProduct filteredProduct = batchContractSetup.getFilteredProduct();

		Set<String> addedQualifiers = new HashSet<String>();
		GroupComponent groupComponent = new GroupComponent();
		List<Element> candidateRiderElements = groupComponent.getCandidateRiderElements(batchContractSetup);
		for (Element elt : candidateRiderElements) {
			// skip anything that isn't required
			if (!XMLUtil.getText(elt.element("RequiredOptionalCT"))
					.equals(CaseProductUnderwriting.REQUIREDOPTIONAL_REQUIRED)) {
				continue;
			}

			String riderCoverage = XMLUtil.getText(elt.element("Coverage"));
			if (!existingRiderCodes.contains(riderCoverage)) {
				requestDocument.addToRequestParameters(elt);
				addedQualifiers.add(XMLUtil.getText(elt.element("Qualifier")));
			}
		}

		if (!addedQualifiers.isEmpty()) {
			SEGResponseDocument doc = new SEGResponseDocument();
			String message = "Added rider(s) " + Joiner.on(", ").join(addedQualifiers)
					+ " from case underwriting setup";
			doc.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, message);
			responseMessages.addAll(doc.getResponseMessages());
		}
	}

	/**
	 * Processes the information from the ContractClientVO sections of the import
	 * file. Turns that information into the ClientInformationVOs and puts them into
	 * the request parameters of the request document.
	 *
	 * @param newBusinessContractVOElement Element containing the
	 *                                     NewBusinessContractVO in the import file
	 * @param requestDocument              SEGRequestDocument that will be used to
	 *                                     in the add segment service
	 * @param responseMessages             messages containing responses from the
	 *                                     addClient service
	 */
	private void processContractClients(Element newBusinessContractVOElement, String splitEqual,
			SEGRequestDocument requestDocument, List responseMessages) {
		Element baseSegmentVOElement = getBaseSegmentVOElement(newBusinessContractVOElement);

		List contractClientVOElements = getContractClientVOElements(baseSegmentVOElement);

		for (Iterator iterator = contractClientVOElements.iterator(); iterator.hasNext();) {
			Element contractClientVOElement = (Element) iterator.next();

			Element clientInformationVO = processContractClient(contractClientVOElement, splitEqual, responseMessages);

			if (clientInformationVO != null) {
				requestDocument.addToRequestParameters(clientInformationVO);
			}

			// If this is an owner, create a default Payor using the owner's information
			String roleTypeCT = this.getRoleTypeCT(contractClientVOElement);

			if (roleTypeCT != null && roleTypeCT.equalsIgnoreCase(ClientRole.ROLETYPECT_OWNER)) {
				createPayorContractClient(contractClientVOElement, splitEqual, responseMessages, requestDocument);
			}
		}
	}

	/**
	 * Processes a single ContractClientVO by adding the client (if necessary) and
	 * building the ClientInformationVO
	 *
	 * @param contractClientVOElement Element containing the ContractClientVO in the
	 *                                import file
	 * @param responseMessages        messages containing responses from the
	 *                                addClient service
	 *
	 * @return the built ClientInformationVO Element or null if the client didn't
	 *         exist and couldn't be added to persistence
	 */
	private Element processContractClient(Element contractClientVOElement, String splitEqual, List responseMessages) {
		Element clientDetailVOElement = getClientDetailVOElement(contractClientVOElement);

		Long clientDetailPK = addClient(clientDetailVOElement, responseMessages);

		if (clientDetailPK != null) {
//ECK		
			String roleTypeCT = getRoleTypeCT(contractClientVOElement);
			// save ContactInformation only if it's for the insured
			//if (roleTypeCT.equals(ClientRole.ROLETYPECT_INSURED)) {
			    saveContactInformations(clientDetailVOElement, clientDetailPK);
			//}

			Element employeeIdentificationElement = contractClientVOElement.element(EMPLOYEE_IDENTIFICATION);
			String employeeIdentification = XMLUtil.getText(employeeIdentificationElement);

			EDITBigDecimal allocationPercent = null;
			String beneficiaryRelationshipToEmployee = null;
			String beneficiaryRelationshipToInsured = null;

			if (roleTypeCT != null && isBeneficiary(roleTypeCT)) {
				allocationPercent = getAllocationPercent(contractClientVOElement);

				Element beneficiaryRelationshipToEmployeeElement = contractClientVOElement
						.element(BENE_RELATIONSHIP_TO_EMPLOYEE);
				Element beneficiaryRelationshipToInsuredElement = contractClientVOElement
						.element(BENE_RELATIONSHIP_TO_INSURED);

				beneficiaryRelationshipToEmployee = XMLUtil.getText(beneficiaryRelationshipToEmployeeElement);
				beneficiaryRelationshipToInsured = XMLUtil.getText(beneficiaryRelationshipToInsuredElement);
			}

			Element clientInformationVO = buildClientInformationVO(clientDetailPK,
					getRelationshipToEmployeeCT(contractClientVOElement), roleTypeCT, splitEqual, allocationPercent,
					beneficiaryRelationshipToEmployee, beneficiaryRelationshipToInsured, employeeIdentification);

			Element questionnaireInformationVOWithInsuredClassInfo = processQuestionnaires(contractClientVOElement,
					clientInformationVO, responseMessages);

			// If the ContractClient is an insured or termInsured, get the classCT from the
			// contractClient's questionnaire (if available)
			// and set it on the clientInformationVO
			if (roleTypeCT != null
					&& (roleTypeCT.equalsIgnoreCase(ClientRole.ROLETYPECT_INSURED)
							|| roleTypeCT.equalsIgnoreCase(ClientRole.ROLETYPECT_TERM_INSURED))
					&& questionnaireInformationVOWithInsuredClassInfo != null) {
				this.setInsuredClass(clientInformationVO, questionnaireInformationVOWithInsuredClassInfo);
			}

			return clientInformationVO;
		} else {
			return null;
		}
	}

	/**
	 * Processes the QuestionnaireResponses by building the
	 * QuestionnaireInformationVOs and adding them to the ClientInformationVO (if a
	 * matching FilteredQuestionnaire is found)
	 *
	 * @param contractClientVOElement Element containing the ContractClientVO in the
	 *                                import file
	 * @param clientInformationVO     Element containing the ClientInformationVO
	 *                                built for the document to be passed to the add
	 *                                segment service. The built questionnaires will
	 *                                be added to it.
	 */
	private Element processQuestionnaires(Element contractClientVOElement, Element clientInformationVO,
			List responseMessages) {
		Element questionnaireInformationVOWithInsuredClassInfo = null;

		List questionnaireResponseVOElements = getQuestionnaireResponseVOElements(contractClientVOElement);

		int numSkipped = 0;
		HashSet<String> skippedNames = new HashSet<String>();

		for (Iterator iterator = questionnaireResponseVOElements.iterator(); iterator.hasNext();) {
			Element questionnaireResponseVOElement = (Element) iterator.next();

			String questionnaireId = getQuestionnaireId(questionnaireResponseVOElement);

			Long filteredQuestionnairePK = determineFilteredQuestionnairePK(questionnaireId);
			String responseCT = determineResponseCT(questionnaireResponseVOElement.element(RESPONSECT));

			// If the questionnaireId did not match up to any existing questionnaires (i.e.
			// filteredQuestionnairePK is null),
			// don't create the questionnaireInformationVO
			if (filteredQuestionnairePK != null) {
				Element questionnaireInformationVO = buildQuestionnaireInformationVO(responseCT,
						filteredQuestionnairePK);
				clientInformationVO.add(questionnaireInformationVO);
			} else {
				numSkipped += 1;
				skippedNames.add(questionnaireId == null ? "(null)" : questionnaireId);
			}

			if (this.questionnaireContainsInsuredClassInfo(questionnaireId)) {
				// not neccessarily added to the client info
				Element questionnaireInformationVO = buildQuestionnaireInformationVO(responseCT,
						filteredQuestionnairePK);
				questionnaireInformationVOWithInsuredClassInfo = questionnaireInformationVO;
			}
		}

		if (numSkipped > 0) {
			SEGResponseDocument doc = new SEGResponseDocument();
			String message = numSkipped + " questionnaires were skipped because they were "
					+ "blank or are not included in the product setup: " + Joiner.on(", ").join(skippedNames);
			doc.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_WARNING, message);
			responseMessages.addAll(doc.getResponseMessages());
		}

		return questionnaireInformationVOWithInsuredClassInfo;
	}

	/**
	 * Creates a default Payor ContractClient using the information from the
	 * contractClientVOElement. Turns the element's information into a
	 * ClientInformationVO and puts it on the SEGRequestDocument.
	 *
	 * @param contractClientVOElement Element containing the ContractClientVO from
	 *                                the import file
	 * @param responseMessages        messages returned from the addClient service
	 * @param requestDocument         built document that will be used as input to
	 *                                the add segment service
	 */
	private void createPayorContractClient(Element contractClientVOElement, String splitEqual, List responseMessages,
			SEGRequestDocument requestDocument) {
		if (contractClientVOElement != null) {
			Element payorContractClientVOElement = contractClientVOElement;

			Element roleTypeElement = this.getRoleTypeElement(payorContractClientVOElement);

			XMLUtil.setText(roleTypeElement, ClientRole.ROLETYPECT_PAYOR);

			Element clientInformationVO = processContractClient(payorContractClientVOElement, splitEqual,
					responseMessages);

			if (clientInformationVO != null) {
				requestDocument.addToRequestParameters(clientInformationVO);
			}
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	// Get elements from importDocument //
	//////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Gets all the NewBusinessContractVO elements from the importDocument
	 *
	 * @return
	 */
	private List getNewBusinessContractVOElementsFromImportDocument() {
		Element rootElement = this.importDocument.getRootElement();

		List newBusinessContractVOElements = rootElement.elements(NEW_BUSINESS_CONTRACTVO);

		return newBusinessContractVOElements;
	}

	/**
	 * Gets the BaseSegmentVO element from the NewBusinessContractVO Element
	 *
	 * @return
	 */
	private Element getBaseSegmentVOElement(Element newBusinessContractVOElement) {
		Element baseSegmentVOElement = newBusinessContractVOElement.element(BASE_SEGMENTVO);

		return baseSegmentVOElement;
	}

	/**
	 * Gets the RiderSegmentVO elements from the NewBusinessContractVO Element
	 *
	 * @return
	 */
	private List getRiderSegmentVOElements(Element newBusinessContractVOElement) {
		Element baseSegmentVOElement = newBusinessContractVOElement.element(BASE_SEGMENTVO);

		List riderSegmentVOElements = baseSegmentVOElement.elements(RIDER_SEGMENTVO);

		return riderSegmentVOElements;
	}

	/**
	 * Gets the ContractClientVO elements from the given Element
	 *
	 * @return contractClientVOElements
	 */
	private List getContractClientVOElements(Element element) {
		List contractClientVOElements = element.elements(CONTRACT_CLIENTVO);

		return contractClientVOElements;
	}

	/**
	 * Gets the ClientDetailVO element from the given ContractClientVO element
	 *
	 * @return clientDetailVOElement
	 */
	private Element getClientDetailVOElement(Element contractClientVOElement) {
		Element clientDetailVOElement = contractClientVOElement.element(CLIENT_DETAILVO);

		return clientDetailVOElement;
	}

	/**
	 * Gets all the QuestionnaireResponseVO elements from the
	 * contractClientVOElement
	 *
	 * @param contractClientVOElement Element containing ContractClientVO element
	 *                                from import file
	 *
	 * @return Element containing the QuestionnaireResponseVO from the import file
	 */
	private List getQuestionnaireResponseVOElements(Element contractClientVOElement) {
		List questionnaireResponseVOElements = contractClientVOElement.elements(QUESTIONNAIRE_RESPONSEVO);

		return questionnaireResponseVOElements;
	}

	private List getContactInformationVOElements(Element contractClientVOElement) {
		List contactInformationVOElements = contractClientVOElement.elements(CONTACT_INFORMATIONVO);

		return contactInformationVOElements;
	}

	/**
	 * Gets the FaceAmount from the segment info in the import file
	 *
	 * @param segmentVOElement Element containing BaseSegmentVO or RiderSegmentVO in
	 *                         import file
	 * 
	 * @return String of faceAmount
	 */
	private String getFaceAmount(Element segmentVOElement) {
		Element lifeVOElement = segmentVOElement.element(LIFEVO);

		Element faceAmountElement = lifeVOElement.element(FACE_AMOUNT);

		return XMLUtil.getText(faceAmountElement);
	}

	/**
	 * Gets the Element containing the application signed state from the given
	 * element
	 *
	 * @param baseSegmentVOElement Element containing the BaseSegmentVO from the
	 *                             import file
	 *
	 * @return Element containing the ApplicationSignedState
	 */
	private Element getApplicationSignedStateElement(Element baseSegmentVOElement) {
		Element signedStateElement = baseSegmentVOElement.element(APPLICATION_SIGNED_STATECT);

		return signedStateElement;
	}

	/**
	 * Gets the Element containing the Death Benefit Option from the given element
	 *
	 * @param baseSegmentVOElement Element containing the BaseSegmentVO from the
	 *                             import file
	 *
	 * @return Element containing the deathBenefitOption
	 */
	private Element getDeathBenefitOptionElement(Element baseSegmentVOElement) {
		Element deathBenefitOptionElement = baseSegmentVOElement.element(DEATH_BENEFIT_OPTIONCT);

		return deathBenefitOptionElement;
	}

	/**
	 * Gets the Element containing the roleType from the ContractClientVO element in
	 * the import file
	 *
	 * @param contractClientVOElement
	 *
	 * @return Element containing the roleType
	 */
	private Element getRoleTypeElement(Element contractClientVOElement) {
		Element roleTypeElement = contractClientVOElement.element(ROLE_TYPECT);

		return roleTypeElement;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Helper methods //
	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Calls the addClient service and passes the given requestDocument. Gets the
	 * clientDetailPK from the response and returns it.
	 *
	 * @param requestDocument
	 * @param responseMessages messages containing responses from the addClient
	 *                         service
	 *
	 * @return PK of newly created clientDetail. The responseMessages from the
	 *         service are also sent back via the arg list
	 */
	private Long callAddClientService(Document requestDocument, List responseMessages) {
		Group groupComponent = new GroupComponent();
		Document responseDocument = groupComponent.addClient(requestDocument);

		SEGResponseDocument segResponseDocument = new SEGResponseDocument(responseDocument);

		// Get the responseMessages so they can be returned to the caller
		responseMessages.addAll(segResponseDocument.getResponseMessages());

		if (responseErrored(responseDocument)) {
			return null;
		} else {
			// Get the ClientDetailPK from the response document
			Element rootElement = responseDocument.getRootElement();

			Element clientDetailElement = rootElement.element("ClientDetailVO");

			Element clientDetailPKElement = clientDetailElement.element("ClientDetailPK");

			String clientDetailPKString = XMLUtil.getText(clientDetailPKElement);

			Long clientDetailPK = new Long(clientDetailPKString);

			return clientDetailPK;
		}
	}

	/**
	 * Gets the appropriate relationshipToEmployeeCT for a given
	 * contractClientVOElement
	 *
	 * @param contractClientVOElement Element containing the ContractClientVO in the
	 *                                import file
	 *
	 * @return String for relationshipToEmployeeCT
	 */
	private String getRelationshipToEmployeeCT(Element contractClientVOElement) {
		Element relationshipToEmployeeCTElement = contractClientVOElement.element(RELATIONSHIP_TO_EMPLOYEECT);

		String relationshipToEmployeeCT = XMLUtil.getText(relationshipToEmployeeCTElement);

		return relationshipToEmployeeCT;
	}

	/**
	 * Gets the roleTypeCT from the contractClientVOElement. At this time, we are
	 * expecting the roleType in the importDocument to be an exact match to our CTs.
	 * Therefore, we just return it back.
	 *
	 * @param contractClientVOElement Element containing the ContractClientVO in the
	 *                                import file
	 *
	 * @return String for roleTypeCT
	 */
	private String getRoleTypeCT(Element contractClientVOElement) {
		Element roleTypeCTElement = getRoleTypeElement(contractClientVOElement);

		String roleTypeCT = XMLUtil.getText(roleTypeCTElement);

		return roleTypeCT;
	}

	/**
	 * Gets the appropriate deathBenefitOptionCT from the importDocument or from the
	 * BatchContractSetup
	 *
	 * @param baseSegmentVOElement
	 *
	 * @return
	 */
	String getDeathBenefitOptionCT(Element baseSegmentVOElement) {
		Element deathBenefitOptionElement = getDeathBenefitOptionElement(baseSegmentVOElement);

		String deathBenefitOptionCT = determineDeathBenefitOptionCT(deathBenefitOptionElement);

		if (deathBenefitOptionCT == null) {
			// If not provided on input, use BatchContractSetup's
			deathBenefitOptionCT = this.batchContractSetup.getDeathBenefitOptionCT();
		}

		return deathBenefitOptionCT;
	}

	/**
	 * Gets the appropriate applicationSignedStateCT from the importDocument or from
	 * the BatchContractSetup
	 *
	 * @param baseSegmentVOElement
	 *
	 * @return
	 */
	String getApplicationSignedStateCT(Element baseSegmentVOElement) {
		Element applicationSignedStateElement = getApplicationSignedStateElement(baseSegmentVOElement);

		String applicationSignedStateCT = determineStateCT(applicationSignedStateElement);

		if (applicationSignedStateCT == null) {
			// If not provided on input, use BatchContractSetup's
			applicationSignedStateCT = this.batchContractSetup.getApplicationSignedStateCT();
		}

		return applicationSignedStateCT;
	}

	/**
	 * Gets the QuestionnaireId from the questionnaireResponseVOElement in the
	 * import file
	 *
	 * @param questionnaireResponseVOElement Element containing the
	 *                                       QuestionnaireResponseVO in the import
	 *                                       file
	 *
	 * @return description String
	 */
	String getQuestionnaireId(Element questionnaireResponseVOElement) {
		Element questionnaireIdElement = questionnaireResponseVOElement.element(QUESTIONNAIRE_ID);

		String questionnaireId = XMLUtil.getText(questionnaireIdElement);

		return questionnaireId;
	}

	/**
	 * Maps gender into a representation consistent with the code tables.
	 *
	 * @param genderElement Element containing the gender information
	 *
	 * @return CodeTable code for the gender
	 */
	private String determineGenderCT(Element genderElement) {
		String genderCT = null;

		if (genderElement != null) {
			String gender = XMLUtil.getText(genderElement);

			if (gender != null && gender.toLowerCase().startsWith("m")) {
				genderCT = "Male";
			} else if (gender != null && gender.toLowerCase().startsWith("f")) {
				genderCT = "Female";
			}
		}

		return genderCT;
	}

	/**
	 * Determines the responseCT for the given Response element. At this time, we
	 * are expecting the response in the importDocument to be an exact match to our
	 * CTs. Therefore, we just return it back.
	 *
	 * @param responseElement
	 *
	 * @return CodeTable code for the response
	 */
	private String determineResponseCT(Element responseElement) {
		String responseCT = XMLUtil.getText(responseElement);

		return responseCT;
	}

	/**
	 * Determines the filteredQuestionnairePK based on the questionnaireId in the
	 * import file and the productStructure defined in the batchContractSetup
	 *
	 * @param questionnaireId
	 *
	 * @return primary key of the found filteredQuestionnaire, null if not found
	 */
	private Long determineFilteredQuestionnairePK(String questionnaireId) {
		Long filteredQuestionnairePK = null;

		FilteredQuestionnaire filteredQuestionnaire = FilteredQuestionnaire.findByQuestionnaireId_ProductStructure(
				questionnaireId, this.batchContractSetup.getFilteredProduct().getProductStructureFK());

		if (filteredQuestionnaire != null) {
			filteredQuestionnairePK = filteredQuestionnaire.getFilteredQuestionnairePK();
		}

		return filteredQuestionnairePK;
	}

	/**
	 * Determines the code for the deathBenefitOption. At this time, we are
	 * expecting the deathBenefitOption in the importDocument to be an exact match
	 * to our CTs. Therefore, we just return it back.
	 *
	 * @param deathBenefitOptionElement Element containing the deathBenefitOption
	 *                                  information
	 *
	 * @return
	 */
	private String determineDeathBenefitOptionCT(Element deathBenefitOptionElement) {
		String deathBenefitOptionCT = XMLUtil.getText(deathBenefitOptionElement);

		return deathBenefitOptionCT;
	}

	/**
	 * Determines the code for the state. At this time, we are expecting the state
	 * in the importDocument to be an exact match to our CTs. Therefore, we just
	 * return it back.
	 *
	 * @param stateElement
	 *
	 * @return
	 */
	String determineStateCT(Element stateElement) {
		String stateCT = XMLUtil.getText(stateElement);

		return stateCT;
	}

	/**
	 * Determines the code for the country. At this time, we are expecting the
	 * country in the importDocument to be an exact match to our CTs. Therefore, we
	 * just return it back.
	 *
	 * @param countryElement
	 *
	 * @return
	 */
	String determineCountryCT(Element countryElement) {
		String countryCT = XMLUtil.getText(countryElement);

		return countryCT;
	}

	/**
	 * A convenience method to get the tax identification from the ClientDetailVO
	 * element.
	 *
	 * @param clientDetailVOElement ClientDetailVO Element containing the tax id
	 *                              Element
	 *
	 * @return tax identification (may be null)
	 */
	private String getTaxIdentification(Element clientDetailVOElement) {
		return XMLUtil.getText(clientDetailVOElement.element(TAXID));
	}

	/**
	 * Determines the correct value of the taxIdType. If the tax id is provided, the
	 * type is SocialSecurityNumber. If it is not provided, the type is
	 * TINNotAvailable.
	 *
	 * @param clientDetailVOElement ClientDetailVO Element from the import file
	 *
	 * @return correct taxIdType
	 */
	private String determineTaxIdType(Element clientDetailVOElement) {
		String ssn = getTaxIdentification(clientDetailVOElement);

		if (ssn == null) {
			return TaxInformation.TAX_ID_TYPE_TIN_NOT_AVAILABLE;
		} else {
			return TaxInformation.TAX_ID_TYPE_SSN;
		}
	}

	/**
	 * Creates an EDITDate object from the date in the given element. The date in
	 * the Element is in the format of mm/dd/yyyy and EDITDate expects yyyy/mm/dd.
	 *
	 * @param dateElement
	 *
	 * @return null if the Element contains no date
	 */
	private EDITDate determineDate(Element dateElement) {
		EDITDate editDate = null;

		String dateString = XMLUtil.getText(dateElement);

		if (dateString != null) {
			editDate = DateTimeUtil.getEDITDateFromMMDDYYYY(dateString);
		}

		return editDate;
	}

	/**
	 * Check to see if the client already exists based on the taxID. Gets the taxID
	 * from the Element.
	 *
	 * @param taxIDElement
	 *
	 * @return PK of existing clientDetail or null if one doesn't exist
	 */
	private Long checkForExistingTaxID(Element taxIDElement) {
		String taxID = XMLUtil.getText(taxIDElement);

		return new GroupComponent().checkForExistingTaxID(taxID);
	}

	/**
	 * Check to see if the client already exists. Checks based on lastname,
	 * firstname, and birthDate. If the birthDate doesn't exist, just checks
	 * lastName and firstName. If the client exists, its PK is returned. If it
	 * doesn't exist or more than 1 match is found, null is returned
	 *
	 * @param clientDetailVOElement
	 *
	 * @return PK of existing clientDetail or null if one doesn't exist
	 */
	private Long checkForExistingClient(Element clientDetailVOElement) {
		String lastName = XMLUtil.getText(clientDetailVOElement.element(LASTNAME));
		String firstName = XMLUtil.getText(clientDetailVOElement.element(FIRSTNAME));

		Element birthDateElement = clientDetailVOElement.element(BIRTHDATE);

		EDITDate birthDate = determineDate(birthDateElement);

		return new GroupComponent().checkForExistingClient(lastName, firstName, birthDate);
	}

	/**
	 * Converts the Document to a SEGResponseDocument and adds the responseMessages
	 *
	 * @param document         response Document from saving the contract
	 * @param responseMessages response message from doing things before the
	 *                         contract save (like saving a client)
	 *
	 * @return fully built SEGResponseDocument
	 */
	private SEGResponseDocument convertToSEGResponseDocument(Document document, List responseMessages) {
		SEGResponseDocument segResponseDocument = new SEGResponseDocument(document);

		// Want to put the contract's responseMessages after the "other"
		// responseMessages (usually the client's)
		// So get them and then clear them from the document
		List contractResponseMessages = segResponseDocument.getResponseMessages();

		segResponseDocument.clearResponseMessages();

		// Add the "other" responseMessages to the document
		for (Iterator iterator = responseMessages.iterator(); iterator.hasNext();) {
			Element responseMessageElement = (Element) iterator.next();
			segResponseDocument.addResponseMessage(responseMessageElement);
		}

		// Now add the contract responseMessages to the document
		for (Iterator iterator = contractResponseMessages.iterator(); iterator.hasNext();) {
			Element responseMessageElement = (Element) iterator.next();
			segResponseDocument.addResponseMessage(responseMessageElement);
		}

		// System.out.println("================= new SEGResponseDocument");
		// XMLUtil.printDocumentToSystemOut(segResponseDocument.getDocument());

		return segResponseDocument;
	}

	/**
	 * Increments the counts for the number of contracts processed, how many were
	 * successful and how many failed
	 *
	 * @param responseDocument
	 */
	private void incrementCounts(SEGResponseDocument responseDocument) {
		if (responseDocument.hasError()) {
			this.failureCount++;
		} else if (responseDocument.hasFailure()) {
			this.warningCount++;
		} else {
			this.successCount++;
		}

		this.totalContractCount++;
	}

	/**
	 * Adds a message containing count information to the list of responseDocuments
	 *
	 * @param responseDocuments
	 */
	private void addCountResponse(List responseDocuments) {
		SEGResponseDocument responseDocument = new SEGResponseDocument();

		String message = "Import - Total contracts processed: " + this.totalContractCount + "=> Successes: "
				+ this.successCount + ", Success w/ Warnings: " + this.warningCount + ", Failures: "
				+ this.failureCount;

		if (this.failureCount > 0) {
			responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_WARNING, message);
		} else {
			responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, message);
		}

		responseDocuments.add(0, responseDocument);
	}

	/**
	 * Determines if the questionnaireId is the one needed to set the insured class
	 *
	 * @param questionnaireId
	 *
	 * @return true if the questionnaireDescription is the one needed to set the
	 *         insured's classCT, false otherwise
	 */
	private boolean questionnaireContainsInsuredClassInfo(String questionnaireId) {
		if (questionnaireId != null && questionnaireId.equalsIgnoreCase(QUESTIONNAIRE_ID_FOR_INSURED_CLASSCT)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets the insuredClass on the insuredClientInformationVO using the response in
	 * the questionnaireInformationVO. If the response is yes, the insuredClass is
	 * set to smoker. If it is no, it is set to non-smoker. We don't expect any
	 * response other than yes or no. If it is, that is an error so the insuredClass
	 * will not get set.
	 *
	 * @param insuredClientInformationVO
	 * @param questionnaireInformationVO
	 */
	private void setInsuredClass(Element insuredClientInformationVO, Element questionnaireInformationVO) {
		Element responseCTElement = questionnaireInformationVO.element("ResponseCT");

		String responseCT = XMLUtil.getText(responseCTElement);

		Element contractClientInformationElement = insuredClientInformationVO.element("ContractClientInformationVO");

		Element classCTElement = contractClientInformationElement.element("ClassCT");

		if (responseIsYes(responseCT)) {
			XMLUtil.setText(classCTElement, INSURED_CLASSCT_SMOKER);
		} else if (responseIsNo(responseCT)) {
			XMLUtil.setText(classCTElement, INSURED_CLASSCT_NONSMOKER);
		}
	}

	/**
	 * Indicates if a response indicates a "yes" value.
	 * 
	 * @param content The content to check for "yes"
	 * @return Returns true for "yes", "Yes", "Y", or "y". False otherwise.
	 */
	private boolean responseIsYes(String content) {
		if (content == null)
			return false;

		return content.equalsIgnoreCase("yes") || content.equalsIgnoreCase("y");
	}

	/**
	 * Indicates if a response indicates a "no" value.
	 * 
	 * @param content The content to check for "no"
	 * @return Returns true for "no", "No", "N", or "n". False otherwise.
	 */
	private boolean responseIsNo(String content) {
		if (content == null)
			return false;

		return content.equalsIgnoreCase("no") || content.equalsIgnoreCase("n");
	}

	/**
	 * Convenience method to determine if the response document's response messages
	 * contain an error
	 *
	 * @param document
	 *
	 * @return
	 */
	private boolean responseErrored(Document document) {
		SEGResponseDocument responseDocument = new SEGResponseDocument(document);

		return responseDocument.hasError();
	}

	/**
	 * Determines the departmentLocationPK by finding the DepartmentLocation whose
	 * DeptLocCode equals the import file's DepartmentCode
	 *
	 * @param baseSegmentVOElement Element containing BaseSegmentVO in import file
	 *
	 * @return String equivalent of the DepartmentLocationPK, null if not found
	 */
	private String getDepartmentLocationPK(Element baseSegmentVOElement) {
		String deptCode = XMLUtil.getText(baseSegmentVOElement.element(DEPARTMENT_CODE));

		DepartmentLocation departmentLocation = DepartmentLocation
				.findBy_ContractGroupFKAndDeptLocCode(this.batchContractSetup.getContractGroupFK(), deptCode);

		if (departmentLocation == null) {
			return null;
		} else {
			return departmentLocation.getDepartmentLocationPK().toString();
		}
	}

	/**
	 * Gets the rider coverage from the rider element. NOTE: The addSegment service
	 * expects the Coverage to be a description, not a codeTable code (RIDERNAME).
	 * The import file contains the code so we get the description and return it.
	 *
	 * @param riderSegmentVOElement Element containing RiderSegmentVO in import file
	 *
	 * @return A string representing the content of the coverage element, or null if
	 *         no coverage element exists
	 */
	private String getCoverage(Element riderSegmentVOElement) {
		Element coverageElement = riderSegmentVOElement.element(COVERAGE);
		if (coverageElement == null)
			return null;

		String coverage = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("RIDERNAME",
				coverageElement.getText());

		return coverage;
	}

	/**
	 * Gets the effective date from the rider element, or null
	 *
	 * @param riderSegmentVOElement Element containing RiderSegmentVO in import file
	 *
	 * @return A string representing the effective date, or null if no element
	 *         exists
	 */
	private String getEffectiveDate(Element riderSegmentVOElement) {
		Element effectiveDate = riderSegmentVOElement.element(EFFECTIVE_DATE);
		if (effectiveDate == null)
			return null;
		return effectiveDate.getText();
	}

	/**
	 * Gets the units from the rider element
	 *
	 * @param riderSegmentVOElement Element containing RiderSegmentVO in import file
	 *
	 * @return
	 */
	private String getUnits(Element riderSegmentVOElement) {
		Element unitsElement = riderSegmentVOElement.element(UNITS);

		String units = XMLUtil.getText(unitsElement);

		return units;
	}

	/**
	 * Gets the eobMultiple from the rider element
	 *
	 * @param riderSegmentVOElement Element containing RiderSegmentVO in import file
	 *
	 * @return
	 */
	private String getEOBMultiple(Element riderSegmentVOElement) {
		Element eobMultipleElement = riderSegmentVOElement.element(EOB_MULTIPLE);

		String eobMultiple = XMLUtil.getText(eobMultipleElement);

		return eobMultiple;
	}

	/**
	 * Gets the eobMultiple from the rider element
	 *
	 * @param riderSegmentVOElement Element containing RiderSegmentVO in import file
	 *
	 * @return
	 */
	private String getGIOOption(Element riderSegmentVOElement) {
		Element gioOptionElement = riderSegmentVOElement.element(GIO_OPTION);

		String gioOption = XMLUtil.getText(gioOptionElement);

		return gioOption;
	}

	private String getOptionCode(Element riderSegmentVOElement) {
		Element elt = riderSegmentVOElement.element(OPTION_CODE);
		String value = XMLUtil.getText(elt);
		return value;
	}

	/** Retrieves the group plan from the XML import file */
	private String getGroupPlan(Element riderSegmentVOElement) {
		Element elt = riderSegmentVOElement.element(GROUP_PLAN);
		String value = XMLUtil.getText(elt);
		return value;
	}

	/** Retrieves the group plan from the XML import file */
	private String getRatedGenderCT(Element riderSegmentVOElement) {
		Element elt = riderSegmentVOElement.element(RATED_GENDER_CT);
		String value = XMLUtil.getText(elt);
		return value;
	}

	/**
	 * Determines if the specified roleTypeCT is a beneficiary (any kind) or not
	 *
	 * @return true if this roleTypeCT is a beneficiary, false otherwise
	 */
	private boolean isBeneficiary(String roleTypeCT) {
		String[] beneficiaryRoles = ClientRole.BENEFICIARY_ROLES;

		for (int i = 0; i < beneficiaryRoles.length; i++) {
			if (roleTypeCT.equalsIgnoreCase(beneficiaryRoles[i])) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets the AllocationPercent as an EDITBigDecimal from the ContractClientVO in
	 * the import file
	 *
	 * @param contractClientVOElement Element containing the ContractClientVO in the
	 *                                import file
	 *
	 * @return EDITBigDecimal containing the allocation percent
	 */
	private EDITBigDecimal getAllocationPercent(Element contractClientVOElement) {
		EDITBigDecimal allocationPercent = null;

		Element contractClientAllocationVOElement = contractClientVOElement.element(CONTRACT_CLIENT_ALLOCATIONVO);

		Element allocationPercentElement = contractClientAllocationVOElement.element(ALLOCATION_PERCENT);

		String allocationPercentString = XMLUtil.getText(allocationPercentElement);

		if (allocationPercentString != null && !allocationPercentString.equals("")) {
			allocationPercent = new EDITBigDecimal(allocationPercentString);
		}

		return allocationPercent;
	}
}