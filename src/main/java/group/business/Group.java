/*
 * User: sdorman
 * Date: Jun 15, 2007
 * Time: 10:05:12 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package group.business;

import contract.AgentHierarchy;
import contract.AgentSnapshot;
import contract.FilteredProduct;
import contract.MasterContract;
import contract.FilteredRequirement;
import contract.Requirement;

import edit.common.EDITDate;
import edit.common.exceptions.EDITCaseException;
import edit.common.exceptions.EDITContractException;
import edit.common.vo.AgentVO;
import edit.common.vo.PlacedAgentBranchVO;

import group.*;

import org.dom4j.Document;


/**
 * Group-based services
 */
public interface Group
{
    /**
     * Saves the specifed ContractGroup and its indirectly associated ClientDetail in
     * a partial state bypassing validation rules.
     * @see group.ContractGroup#buildCaseWithoutValidation(group.ContractGroup, Long)
     * @param caseContractGroup the target ContractGroup
     * @param clientDetailPK the owner
     * @param statusChangeEffectiveDate the effective date of the caseStatus (if it has changed)
     * @param operator the operator who is changing/adding the case
     */
    public void saveCase(ContractGroup caseContractGroup, Long clientDetailPK,
                         String statusChangeEffectiveDate, String origCaseStatus,
                         String operator) throws EDITCaseException;
    
   /**
   * True if a ContractGroup of type "Case" exists by the specified contractGroupNumber, 
   * false otherwise.
   * @param contractGroupNumber
   * @return boolean.
   */
    public boolean contractGroupExists(String contractGroupNumber);


    /**
     * Associates the specified ProductStructure to the specified ContractGroup. If the association
     * already exists, the request is simply ignored.
     * @param productStructurePK
     * @param contractGroupPK
     * @param effectiveDate the EffectiveDate of the generated FilteredProduct
     * @param masterContractNumber the MasterContractNumber for the generated FilteredProduct
     * @throws edit.common.exceptions.EDITContractException
     */
    public void addProductStructureToContractGroup(Long productStructurePK, Long contractGroupPK, 
                                                   EDITDate effectiveDate,
                                                   String operator) throws EDITContractException;

    /**
     * Associates the specified underwriting criteria to the specified Enrollment.  If the exact underwriting
     * is already associated to the Enrollment, the request is ignored.
     * @param areaValuePK
     * @param activeEnrollmentPK
     * @param filteredProductPK
     * @param relToEE
     * @param deptLocFK
     * @param value
     * @param includeOptionalCT
     */
    public void addUnderwriting(Long areaValuePK, 
                                Long activeEnrollmentPK, 
                                Long filteredProductPK,
                                String relToEE,
                                String deptLocFK,
                                String value,
                                String includeOptionalCT) throws EDITCaseException;
    
	/**
	 * 
	 * @param requestDocument
	 * @return
	 */
	public Document getDeductionFrequencyDesc(Document requestDocument);

    /**
     * Removes the selected underwriting criteria from the database
     * @param selectedPKs
     * @throws EDITCaseException
     */
    public void removeUnderwriting(String[] selectedPKs) throws EDITCaseException;

    /**
     * Associates a manual requirement to the specified ContractGroup
     * @param contractGroupPK
     * @param requirement
     * @param filteredRequirement
     * @throws EDITCaseException
     */
    public void addRequirementToContractGroup(Long contractGroupPK, Requirement requirement, FilteredRequirement filteredRequirement) throws EDITCaseException;

    /**
     * Removes the selected Requirement from the specified contractGroup
     * @param contractGroupPK
     * @param selectedRequirementPK
     * @throws EDITCaseException
     */
    public void deleteRequirementFromContractGroup(Long contractGroupPK, Long selectedRequirementPK) throws EDITCaseException;

    /**
     * Updates the contract group requirement with any status or date changes made by the user.
     * @param contractGroupRequirement
     * @throws EDITCaseException
     */
    public void saveRequirement(ContractGroupRequirement contractGroupRequirement) throws EDITCaseException;
    
    /**
     * Removes the association between the specified ProductStructure and
     * the specified ContractGroup via the FilteredProduct entity (the end
     * result is that the FilteredProduct entity is deleted).
     * @param productStructurePK
     * @param contractGroupPK
     */
    public void removeProductStructureFromContractGroup(Long productStructurePK, Long contractGroupPK) throws EDITContractException;
    
    /**
     * Saves the specifed ContractGroup, its associated Case ContractGroup,
     * and its indirectly associated ClientDetail in
     * a partial state bypassing validation rules.
     * @see group.ContractGroup#buildCaseWithoutValidation(group.ContractGroup, Long)
     */
    public void saveGroup(ContractGroup contractGroup, Long caseContractGroupPK, Long clientDetailPK) throws EDITCaseException;

    /**
     * Deletes the specified ContractGroup
     * @param contractGroup
     * @throws EDITCaseException
     */
    public String deleteGroup(ContractGroup contractGroup) throws EDITCaseException;
    
    /**
     * Saves the specified PayrollDeductionSchedule, and its associated Group ContractGroup
     * @param contractGroup
     * @param changeEffectiveDate if supplied, the ChangeHistory.EffectiveDate will be set to this value
     * @throws edit.common.exceptions.EDITCaseException
     */
    public void savePayrollDeductionScheduleUpdate(PayrollDeductionSchedule payrollDeductionSchedule, ContractGroup contractGroup, EDITDate changeEffectiveDate) throws EDITCaseException;

    /**
     * Saves the specified Enrollment and its associated Case ContractGroup
     * @param enrollment
     * @param caseContractGroupPK
     * @throws EDITCaseException
     */
    public void saveEnrollment(Enrollment enrollment, Long caseContractGroupPK) throws EDITCaseException;

    /**
     * Saves the specified ProjectedBusinessByMonth and its associated Enrollment
     * @param projBusByMonth
     * @param enrollmentPK
     * @throws EDITCaseException
     */
    public void saveProjectedBusinessByMonth(ProjectedBusinessByMonth projBusByMonth, Long enrollmentPK) throws EDITCaseException;

    /**
     * Creates a new AgentHierarchy for the specified Case
     * @param contractGroup
     * @param agentVO
     * @param placedAgentBranchVOs
     * @param selectedPlacedAgentPK
     * @throws EDITCaseException
     */
    public void createCaseAgentHierarchy(ContractGroup contractGroup, AgentVO agentVO,
                                       PlacedAgentBranchVO[] placedAgentBranchVOs,
                                       String selectedPlacedAgentPK) throws EDITCaseException;

    /**
     * Deletes AgentHierarchy from the specified Case.
     * @param contractGroup
     * @param selectedAgentHierarchyPK
     * @throws EDITCaseException
     */
    public void deleteCaseAgentHierarchy(ContractGroup contractGroup, String selectedAgentHierarchyPK) throws EDITCaseException;
    
    /**
     * Saves update(s) to AgentHierarchy.
     * @param agentHierarchy
     * @throws EDITCaseException
     */
    public void saveCaseAgentHierarchyUpdate(AgentHierarchy agentHierarchy) throws EDITCaseException;

    /**
     * Saves update(s) (commission overrides) to AgentSnapshot
     * @param agentShapshot
     * @throws EDITCaseException
     */
    public void saveCaseAgentSnapshotUpdate(AgentSnapshot agentShapshot) throws EDITCaseException;

    public void saveDepartmentLocation(DepartmentLocation departmentLocation, Long activeGroupPK) throws EDITCaseException;

    /**
     * Saves the filteredProduct information  with respect to each MasterContract.
     * @param filteredProductPK
     * @throws EDITCaseException
     */
    public FilteredProduct saveFilteredProductInformation(Long filteredProductPK, int monthWindow) throws EDITCaseException;

     /**
     * Saves the MasterContractInformation found on the specified FilteredProduct.
     * @param filteredProductPK
     * @param masterContractNumber
     * @param masterContractName
     * @param effectiveDate
     * @throws EDITCaseException
     */
    public MasterContract saveMasterContractInformationNew(Long filteredProductPK, Long ContractGroupPK, String masterContractNumber,
                                                         String masterContractName,
                                                         EDITDate masterContractEffectiveDate,EDITDate masterContractTerminationDate, String statusCT,
                                                         boolean noInterimCoverage, String creationOperator) throws EDITCaseException;
    /**
     * Checks to see if the client already exists based on the taxID.  If the client exists, its PK is returned.  If
     * it doesn't exist or more than 1 match is found, null is returned.
     *
     * @param taxID                                 taxID used to determine if the client already exists
     *
     * @return PK of existing clientDetail or null if one doesn't exist
     */
    public Long checkForExistingTaxID(String taxID);

    /**
     * Checks to see if the client already exists.  Checks based on lastname, firstname, and birthDate.
     * If the client exists, its clientDetailPK is returned. If it doesn't exist or more than 1 match is found, null
     * is returned.  If the birthDate is not supplied, returns null.
     *
     * @param lastName                  last name of the client to be found
     * @param firstName                 first name of the client to be found
     * @param birthDate                 birth date of the client can be found.  If null, it is not used to find the client
     *
     * @return  PK of existing clientDetail or null if one doesn't exist
     */
    public Long checkForExistingClient(String lastName, String firstName, EDITDate birthDate);

    



    //  ==================================== SERVICES =============================================================

    public Document getQuote(Document document);
    


    /**
     * Adds a Segment to a ContractGroup.  The Segment that is supplied has minimal information.  The AgentHierarchies
     * from the ContractGroup are "copied" and attached to the Segment.  ContractClients are built and attached to the
     * Segment.
     *
     * @param requestDocument
     *
     *                                  <SEGRequestVO>
     *                                      <RequestParameters>
     *                                          <BeneficiariesEquallySplit>true/false</BeneficiariesEquallySplit>
     *                                          <GeneratePRASETest>true/false</GeneratePRASETest>
     *                                          <SegmentInformationVO>
     *                                              <FilteredProductPK></FilteredProductPK>
     *                                              <ContractNumber></ContractNumber>
     *                                              <DepartmentLocationPK></DepartmentLocationPK>
     *                                              <FaceAmount></FaceAmount>
     *                                              <EffectiveDate></EffectiveDate>
     *                                              <ApplicationSignedDate></ApplicationSignedDate>
     *                                              <ApplicationReceivedDate></ApplicationReceivedDate>
     *                                              <ApplicationSignedStateCT></ApplicationSignedStateCT>
     *                                              <IssueStateCT></IssueStateCT>
     *                                              <IssueStateORInd></IssueStateORInd>
     *                                              <DeathBenefitOptionCT></DeathBenefitOptionCT>
     *                                              <NonForfeitureOptionCT></NonForfeitureOptionCT>
     *                                              <EstateOfTheInsured></EstateOfTheInsured>
     *                                          </SegmentInformationVO>
     *                                          <BatchContractSetupPK>1</BatchContractSetupPK>
     *                                          <Operator>System</Operator>
     *                                          <ClientInformationVO>                  // repeats for each client
     *                                              <ClientDetailPK></ClientDetailPK>
     *                                              <RelationshipToEmployeeCT></RelationshipToEmployeeCT>
     *                                              <EmployeeIdentification></EmployeeIdentification>
     *                                              <QuestionnaireResponseVO>       // repeats for each question
     *                                                  <FilteredQuestionnaireFK></FilteredQuestionnaireFK>
     *                                                  <ResponseCT></ResponseCT>
     *                                              </QuestionnaireResponseVO>
     *                                              <ContractClientInformationVO>      // repeats for each role
     *                                                  <RoleTypeCT></RoleTypeCT>
     *                                                  <ClassCT></ClassCT>            // (some fields in this section will be empty depending on the RoleTypeCT)
     *                                                  <TableRatingCT></TableRatingCT>
     *                                                  <BeneficiaryAllocation></BeneficiaryAllocation>
     *                                                  <BeneficiaryAllocationType></BeneficiaryAllocationType> // comes in as either "PERCENT" or "AMOUNT"
     *                                                  <BeneficiaryRelationshipToInsured></BeneficiaryRelationshipToInsured>
     *                                              </ContractClientInformationVO>
     *                                          </ClientInformationVO>
     *                                          <CandidateRiderVO>                    // repeats for each selected rider
     *                                              <Coverage></Coverage>
     *                                              <Qualifier></Qualifier>
     *                                              <RequiredOptionalCT></RequiredOptionalCT>
     *                                              <EffectiveDate></EffectiveDate>
     *                                              <Units></Units>
     *                                              <FaceAmount></FaceAmount>
     *                                              <EOBMultiple></EOBMultiple>
     *                                              <GIOOption></GIOOption>
     *                                              <ClientInformationVO>
     *                                                  <ClientDetailPK></ClientDetailPK>
     *                                                  <RelationshipToEmployeeCT></RelationshipToEmployeeCT>
     *                                                  <EmployeeIdentification></EmployeeIdentification>
     *                                                  <QuestionnaireResponseVO>
     *                                                      // no QuestionnaireResponseVO information needed
     *                                                  </QuestionnaireResponseVO>
     *                                                  <ContractClientInformationVO>
     *                                                     // no ContractClientInformationVO information needed
     *                                                  </ContractClientInformationVO>
     *                                              </ClientInformationVO>
     *                                          </CandidateRiderVO>
     *                                      </RequestParameters>
     *                                  </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the created Segment using the following structure:
     *
     *                                  <SEGResponseVO>
     *                                      <SegmentVO>
     *                                          ...
     *                                      </SegmentVO>
     *                                      <ResponseMessageVO>
     *                                          ...
     *                                      </ResponseMessageVO>
     *                                  </SEGResponseVO>
     */
    public Document addSegmentToContractGroup(Document requestDocument);

    public void removeSegmentFromContractGroup(Document requestDocument) throws EDITCaseException;

    /**
     * Creates a default BatchContractSetup
     * @param requestDocument           SEGRequestVO containing the following structure:
     *
     *                                  <SEGRequestVO>
     *                                      <RequestParameters>
     *                                          <GroupContractGroupPK>2</GroupContractGroupPK>
     *                                          <Operator>Suzanne</Operator>
     *                                      </RequestParameters>
     *                                  </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the created BatchContractSetup using the following structure:
     *
     *                                      <SEGResponseVO>
     *                                          <BatchContractSetupVO>
     *                                              ...
     *                                              <ContractGroupVO>               //  Group
     *                                                  ...
     *                                                  <ClientRole>
     *                                                     ...
     *                                                     <ClientDetailVO>
     *                                                          ...
     *                                                     </ClientDetailVO>
     *                                                  </ClientRole>
     *                                                  <ContractGroupVO>          //  Case
     *                                                     ...
     *                                                      <ClientRole>
     *                                                          ...
     *                                                          <ClientDetailVO>
     *                                                               ...
     *                                                          </ClientDetailVO>
     *                                                      </ClientRole>
     *                                                  </ContractGroupVO>
     *                                              </ContractGroupVO>
     *                                          </BatchContractSetupVO>
     *                                          <ResponseMessageVO>
     *                                              ...
     *                                          </ResponseMessageVO>
     *                                      </SEGResponseVO>
     * 
     */
    public Document createDefaultBatchContractSetup(Document requestDocument);

    /**
     * Updates the BatchContractSetup object to the database with the information provided.
     *
     * @param requestDocument      SEGRequestVO containing BatchContractSetup information using the following structure:
     *
     *                                      <SEGRequestVO>
     *                                          <RequestParameters>
     *                                              <BatchContractSetupVO>
     *                                                  ...
     *                                              </BatchContractSetupVO>
     *                                              <BatchProductLogVO>             // repeats
     *                                                  ...
     *                                              </BatchProductLogVO>
     *                                              <BatchProgressLogVO>            // repeats
     *                                                  ...
     *                                              </BatchProgressLogVO>
     *                                              <AgentHierarchyPK>3333</AgentHierarchyPK>       // repeats for every candidate AH that will become SelectedAgentHierarchy
     *                                          </RequestParameters>
     *                                      </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the updated BatchContractSetup using the following structure:
     *
     *                                      <SEGResponseVO>
     *                                          <BatchContractSetupVO>
     *                                              ...
     *                                              <ContractGroupVO>               //  Group
     *                                                  ...
     *                                                  <ClientRole>
     *                                                     ...
     *                                                     <ClientDetailVO>
     *                                                          ...
     *                                                     </ClientDetailVO>
     *                                                  </ClientRole>
     *                                                  <ContractGroupVO>          //  Case
     *                                                     ...
     *                                                      <ClientRole>
     *                                                          ...
     *                                                          <ClientDetailVO>
     *                                                               ...
     *                                                          </ClientDetailVO>
     *                                                      </ClientRole>
     *                                                  </ContractGroupVO>
     *                                              </ContractGroupVO>
     *                                              <EnrollmentVO>                //  Candidate enrollments
     *                                                  ...
     *                                              </EnrollmentVO>
     *                                          </BatchContractSetupVO>
     *                                          <ResponseMessageVO>
     *                                              ...
     *                                          </ResponseMessageVO>
     *                                      </SEGResponseVO>
     */
    public Document updateBatchContractSetup(Document requestDocument);

    /**
     * Deletes the BatchContractSetup object to the database with the information provided.
     *
     * @param requestDocument      SEGRequestVO containing BatchContractSetup information using the following structure:
     *
     *                                      <SEGRequestVO>
     *                                          <RequestParameters>
     *                                              <BatchContractSetupPK>
     *                                                  ...
     *                                              </BatchContractSetupPK>
     *                                          </RequestParameters>
     *                                      </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the response message:
     *
     *                                      <SEGResponseVO>
     *                                          <ResponseMessageVO>
     *                                              ...
     *                                          </ResponseMessageVO>
     *                                      </SEGResponseVO>
     */
    public Document deleteBatchContractSetup(Document requestDocument);

    /**
     * Copies(Clones) the BatchContractSetup object to the database with the information provided.
     *
     * @param requestDocument      SEGRequestVO containing BatchContractSetup information using the following structure:
     *
     *                                      <SEGRequestVO>
     *                                          <RequestParameters>
     *                                              <BatchContractSetupPK>
     *                                                  ...
     *                                              </BatchContractSetupPK>
     *                                          </RequestParameters>
     *                                      </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the response message:
     *
     *                                      <SEGResponseVO>
     *                                          <ResponseMessageVO>
     *                                              ...
     *                                          </ResponseMessageVO>
     *                                      </SEGResponseVO>
     */
    public Document copyBatchContractSetup(Document requestDocument);

    /**
     * Dummy method that accepts a request document as an argument.  This method just calls the method that does not
     * accept an argument since it is not needed for the service.  This method is needed to support a standard framework
     * for the user interface.  Its existence may be temporary.
     *
     * @param requestDocument                   document which is ignored
     *
     * @return  response document
     * @see Group#getAllBatchContractSetups()
     */
    public Document getAllBatchContractSetups(Document requestDocument);

    /**
     * Gets all the BatchContractSetups and the ClientRole and ClientDetail for the group and case ContractGroups.
     * Actually, it only gets those whose group ContractGroups have a termination date less than today's date (i.e.
     * that are active).  Cannot add contracts to a terminated group.
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                                      <SEGResponseVO>
     *                                          <BatchContractSetupVO>              // repeats for each BatchContractSetup
     *                                              ...
     *                                              <ContractGroupVO>               //  Group
     *                                                  ...
     *                                                  <ClientRole>
     *                                                     ...
     *                                                     <ClientDetailVO>
     *                                                          ...
     *                                                     </ClientDetailVO>
     *                                                  </ClientRole>
     *                                                  <ContractGroupVO>          //  Case
     *                                                     ...
     *                                                      <ClientRole>
     *                                                          ...
     *                                                          <ClientDetailVO>
     *                                                               ...
     *                                                          </ClientDetailVO>
     *                                                      </ClientRole>
     *                                                  </ContractGroupVO>
     *                                              </ContractGroupVO>
     *                                              <EnrollmentVO>                //  Candidate enrollments
     *                                                  ...
     *                                              </EnrollmentVO>
     *                                          </BatchContractSetupVO>
     *                                          <ResponseMessageVO>
     *                                              ...
     *                                          </ResponseMessageVO>
     *                                      </SEGResponseVO>
     */
    public Document getAllBatchContractSetups();

    /**
     * Finds the ContractGroup that has a ContractGroupTypeCT = 'Group' for a given ContractGroupNumber.  Returns the
     * Group along with its associated Case.
     *
     * @param requestDocument
     *
     *                                          <SEGRequestVO>
     *                                              <RequestParameters>
     *                                                  <ContractGroupNumber>12345</ContractGroupNumber>
     *                                              </RequestParameters>
     *                                          </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                                          <SEGResponseVO>
     *                                               <ContractGroupVO>           // Group - only one expected
     *                                                        ...
     *                                                  <ClientRoleVO>
     *                                                       ...
     *                                                       <ClientDetailVO>
     *                                                           ...
     *                                                       </ClientDetailVO>
     *                                                  </ClientRoleVO>
     *                                                  <ContractGroupVO>           // Case
     *                                                          ...
     *                                                      <ClientDetailVO>
     *                                                          ...
     *                                                      </ClientDetailVO>
     *                                                  </ContractGroupVO>
     *                                              </ContractGroupVO>
     *                                              <ResponseMessageVO>
     *                                                  ...
     *                                              </ResponseMessageVO>
     *                                          </SEGResponseVO>
     */
    public Document getGroupContractGroupByGroupNumber(Document requestDocument);

    /**
     * Finds the ContractGroup that has a ContractGroupTypeCT = 'Group' for a given "GroupName".  The "GroupName" is the
     * CorporateName on the associated ClientDetail.  Returns the Group along with its associated Case and their
     * ClientDetails.
     *
     * @param requestDocument
     *
     *                                          <SEGRequestVO>
     *                                              <RequestParameters>
     *                                                  <GroupName>ABC Enterprises</GroupName>
     *                                              </RequestParameters>
     *                                          </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                                          <SEGResponseVO>
     *                                              <ContractGroupVO>           // Group - repeats for each Group
     *                                                  ...
     *                                                  <ClientRoleVO>
     *                                                       ...
     *                                                       <ClientDetailVO>
     *                                                           ...
     *                                                       </ClientDetailVO>
     *                                                  </ClientRoleVO>
     *                                                  <ContractGroupVO>           // Case
     *                                                          ...
     *                                                      <ClientDetailVO>
     *                                                          ...
     *                                                      </ClientDetailVO>
     *                                                  </ContractGroupVO>
     *                                              </ContractGroupVO>
     *                                              <ResponseMessageVO>
     *                                                  ...
     *                                              </ResponseMessageVO>
     *                                          </SEGResponseVO>
     */
    public Document getGroupContractGroupsByGroupName(Document requestDocument);

    /**
     * Returns all candidate writing agents for a given BatchContractSetup.  By definition, they are candidates if
     * they are attached to the Case ContractGroup but are not SelectedAgentHierarchys on the BatchContractSetup
     *
     * @param requestDocument               SEGRequestVO containing the following structure:
     *
     *                                              <SEGRequestVO>
     *                                                  <RequestParameters>
     *                                                      <BatchContractSetupPK>1</BatchContractSetupPK>
     *                                                  </RequestParameters>
     *                                              </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                                              <SEGResponseVO>
     *                                                  <AgentSnapshotVO>          // Repeats for each writing agent
     *                                                      ...
     *                                                      <AgentHierarchyVO>
     *                                                          ...
     *                                                          <AgentHierarchyAllocationVO>
     *                                                              ...
     *                                                          <AgentHierarchyAllocationVO>
     *                                                      </AgentHierarchyVO>
     *                                                      <PlacedAgentVO>
     *                                                          ...
     *                                                          <AgentContractVO>
     *                                                              ...
     *                                                              <AgentVO>
     *                                                                  ...
     *                                                                  <ClientRoleVO>
     *                                                                      ...
     *                                                                      <ClientDetailVO>
     *                                                                          ...
     *                                                                      </ClientDetailVO>
     *                                                                  </ClientRoleVO>
     *                                                              </AgentVO>
     *                                                          </AgentContractVO>
     *                                                          <PlacedAgentCommissionProfileVO>
     *                                                              ...
     *                                                              <CommissionProfileVO>
     *                                                                  ...
     *                                                              </CommissionProfileVO>
     *                                                          </PlacedAgentCommissionProfileVO>
     *                                                      </PlacedAgentVO>
     *                                                  </AgentSnapshotVO>
     *                                                  <ResponseMessageVO>
     *                                                      ...
     *                                                  </ResponseMessageVO>
     *                                              </SEGResponseVO>
     */
    public Document getCandidateWritingAgents(Document requestDocument);

    /**
     * Returns the writing agents of the SelectedAgentHierarchy for a BatchContractSetup.
     *
     * @param requestDocument               SEGRequestVO containing the following structure:
     *
     *                                              <SEGRequestVO>
     *                                                  <RequestParameters>
     *                                                      <BatchContractSetupPK>1</BatchContractSetupPK>
     *                                                  </RequestParameters>
     *                                              </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                                              <SEGResponseVO>
     *                                                  <AgentSnapshotVO>          // Repeats for each writing agent
     *                                                      ...
     *                                                      <AgentHierarchyVO>
     *                                                          ...
     *                                                          <AgentHierarchyAllocationVO>
     *                                                              ...
     *                                                          <AgentHierarchyAllocationVO>
     *                                                      </AgentHierarchyVO>
     *                                                      <PlacedAgentVO>
     *                                                          ...
     *                                                          <AgentContractVO>
     *                                                              ...
     *                                                              <AgentVO>
     *                                                                  ...
     *                                                                  <ClientRoleVO>
     *                                                                      ...
     *                                                                      <ClientDetailVO>
     *                                                                          ...
     *                                                                      </ClientDetailVO>
     *                                                                  </ClientRoleVO>
     *                                                              </AgentVO>
     *                                                          </AgentContractVO>
     *                                                          <PlacedAgentCommissionProfileVO>
     *                                                              ...
     *                                                              <CommissionProfileVO>
     *                                                                  ...
     *                                                              </CommissionProfileVO>
     *                                                          </PlacedAgentCommissionProfileVO>
     *                                                      </PlacedAgentVO>
     *                                                  </AgentSnapshotVO>
     *                                                  <ResponseMessageVO>
     *                                                      ...
     *                                                  </ResponseMessageVO>
     *                                              </SEGResponseVO>
     */
    public Document getSelectedWritingAgents(Document requestDocument);

    /**
     * Returns the Client with the given tax id, including its primary address if exists
     *
     * @param requestDocument               SEGRequestVO containing the following structure:
     *
     *                                          <SEGRequestVO>
     *                                              <RequestParameters>
     *                                                  <TaxID>111223333</TaxID>
     *                                              </RequestParameters>
     *                                          </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                                          <SEGResponseVO>
     *                                              <ClientDetailVO>
     *                                                  ...
     *                                                  <ClientAddressVO>
     *                                                      ...
     *                                                  </ClientAddressVO>
     *                                              </ClientDetailVO>
     *                                              <ResponseMessageVO>
     *                                                  ...
     *                                              </ResponseMessageVO>
     *                                          </SEGResponseVO>
     */
    public Document getClientsByTaxID(Document requestDocument);

    /**
     * Returns the Client with the given name and date of birth, including its primary address if exists
     *
     * @param requestDocument               SEGRequestVO containing the following structure:
     *
     *                                          <SEGRequestVO>
     *                                              <RequestParameters>
     *                                                  <Name>Am</Name>
     *                                                  <DateOfBirth>12/05/1961</DateOfBirth>
     *                                              </RequestParameters>
     *                                          </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                                          <SEGResponseVO>
     *                                              <ClientDetailVO>
     *                                                  ...
     *                                                 <ClientAddressVO>
     *                                                      ...
     *                                                  </ClientAddressVO>
     *                                              </ClientDetailVO>
     *                                              <ResponseMessageVO>
     *                                                  ...
     *                                              </ResponseMessageVO>
     *                                          </SEGResponseVO>
     */
    public Document getClientsByNameAndDateOfBirth(Document requestDocument);

    /**
     * Returns the Client with the given name, including its primary address if exists
     *
     * @param requestDocument               SEGRequestVO containing the following structure:
     *
     *                                          <SEGRequestVO>
     *                                              <RequestParameters>
     *                                                  <Name>Am</Name>
     *                                              </RequestParameters>
     *                                          </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                                          <SEGResponseVO>
     *                                              <ClientDetailVO>
     *                                                  ...
     *                                                  <ClientAddressVO>
     *                                                      ...
     *                                                  </ClientAddressVO>
     *                                              </ClientDetailVO>
     *                                              <ResponseMessageVO>
     *                                                  ...
     *                                              </ResponseMessageVO>
     *                                          </SEGResponseVO>
     */
    public Document getClientsByName(Document requestDocument);

    /**
     * Returns the active the DepartmentLocation objects for the given BatchContractSetup.  The DepartmentLocations are
     * attached to the Group ContractGroup of the BatchContractSetup.  An "active" DepartmentLocation is one whose
     * EffectiveDate is before today's date and whose TerminationDate is after today's date.
     *
     * @param requestDocument               SEGRequestVO containing the following structure:
     *
     *                                              <SEGRequestVO>
     *                                                  <RequestParameters>
     *                                                      <BatchContractSetupPK>1</BatchContractSetupPK>
     *                                                  </RequestParameters>
     *                                              </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                                          <SEGResponseVO>
     *                                              <DepartmentLocationVO>      // repeated for each DepartmentLocation
     *                                                   ...
     *                                              </DepartmentLocationVO>
     *                                              <ResponseMessageVO>
     *                                                  ...
     *                                              </ResponseMessageVO>
     *                                          </SEGResponseVO>
     */
    public Document getActiveDepartmentLocations(Document requestDocument);


   /**
    * Returns all candidate FilteredProducts and their ProductStructures and Companys for a given BatchContractSetup.
    *
    * @param requestDocument               SEGRequestVO containing the following structure:
    *
    *                                              <SEGRequestVO>
    *                                                  <RequestParameters>
    *                                                      <BatchContractSetupPK>1</BatchContractSetupPK>
    *                                                  </RequestParameters>
    *                                              </SEGRequestVO>
    *
    * @return  SEGResponseVO containing the following structure:
    *
    *                                              <SEGResponseVO>
    *                                                   <FilteredProductVO>
    *                                                           ...
    *                                                       <ProductStructureVO>
    *                                                           ...
    *                                                           <CompanyVO>
    *                                                               ...
    *                                                           <CompanyVO>
    *                                                       </ProductStructureVO>
    *                                                   </FilteredProductVO>
    *                                                   <ResponseMessageVO>
    *                                                       ...
    *                                                   </ResponseMessageVO>
    *                                              </SEGResponseVO>
    */
    public Document getCandidateFilteredProducts(Document requestDocument);

   /**
    * Returns the BatchProductLogs for a given BatchContractSetup.
    *
    * @param requestDocument               SEGRequestVO containing the following structure:
    *
    *                                              <SEGRequestVO>
    *                                                  <RequestParameters>
    *                                                      <BatchContractSetupPK>1</BatchContractSetupPK>
    *                                                  </RequestParameters>
    *                                              </SEGRequestVO>
    *
    * @return  SEGResponseVO containing the following structure:
    *
    *                                              <SEGResponseVO>
    *                                                  <BatchProductLogVO>          // Repeats for each found
    *                                                      ...
    *                                                  </BatchProductLogVO>
    *                                                  <ResponseMessageVO>
    *                                                       ...
    *                                                  </ResponseMessageVO>
    *                                              </SEGResponseVO>
    */
    public Document getBatchProductLogs(Document requestDocument);

    /**
    * Returns the BatchProgressLogs for a given BatchContractSetup.
    *
    * @param requestDocument               SEGRequestVO containing the following structure:
    *
    *                                              <SEGRequestVO>
    *                                                  <RequestParameters>
    *                                                      <BatchContractSetupPK>1</BatchContractSetupPK>
    *                                                  </RequestParameters>
    *                                              </SEGRequestVO>
    *
    * @return  SEGResponseVO containing the following structure:
    *
    *                                              <SEGResponseVO>
    *                                                  <BatchProgressLogVO>          // Repeats for each found
    *                                                      ...
    *                                                  </BatchProgressLogVO>
    *                                                  <ResponseMessageVO>
    *                                                      ...
    *                                                  </ResponseMessageVO>
    *                                              </SEGResponseVO>
    */
    public Document getBatchProgressLogs(Document requestDocument);

   /**
    * Saves the given BatchProductLog
    *
    * @param requestDocument               SEGRequestVO containing the following structure:
    *
    *                                              <SEGRequestVO>
    *                                                  <RequestParameters>
    *                                                      <BatchProductLogVO>
    *                                                           ...
    *                                                      </BatchProductLogVO>
    *                                                  </RequestParameters>
    *                                              </SEGRequestVO>
    *
    * @return  SEGResponseVO containing the saved object in the following structure:
    *
    *                                               <SEGResponseVO>
    *                                                   <BatchProductLogVO>
    *                                                       ...
    *                                                   </BatchProductLogVO>
    *                                                   <ResponseMessageVO>
    *                                                       ...
    *                                                   </ResponseMessageVO>
    *                                               </SEGResponseVO>
    */
    public Document saveBatchProductLog(Document requestDocument);

   /**
    * Saves the given BatchProgressLog
    *
    * @param requestDocument               SEGRequestVO containing the following structure:
    *
    *                                              <SEGRequestVO>
    *                                                  <RequestParameters>
    *                                                      <BatchProgressLogVO>
    *                                                           ...
    *                                                      </BatchProgressLogVO>
    *                                                  </RequestParameters>
    *                                              </SEGRequestVO>
    *
    * @return  SEGResponseVO containing the saved object in the following structure:
    *
    *                                               <SEGResponseVO>
    *                                                   <BatchProgressLogVO>
    *                                                       ...
    *                                                   </BatchProgressLogVO>
    *                                                   <ResponseMessageVO>
    *                                                       ...
    *                                                   </ResponseMessageVO>
    *                                               </SEGResponseVO>
    */
    public Document saveBatchProgressLog(Document requestDocument);

   /**
    * Adds a client
    *
    * @param requestDocument               SEGRequestVO containing the following structure:
    *
    *                                              <SEGRequestVO>
    *                                                  <RequestParameters>
    *                                                      <ClientDetailVO>
    *                                                           ...
    *                                                      </ClientDetailVO>
    *                                                      <ClientAddressVO>
    *                                                           ...
    *                                                      </ClientAddressVO>
    *                                                  </RequestParameters>
    *                                              </SEGRequestVO>
    *
    * @return  SEGResponseVO containing the saved object in the following structure:
    *
    *                                              <SEGResponseVO>
    *                                                  <ClientDetailVO>
    *                                                      ...
    *                                                       <ClientAddressVO>
    *                                                           ...
    *                                                       </ClientAddressVO>
    *                                                   </ClientDetailVO>
    *                                                   <ResponseMessageVO>
    *                                                       ...
    *                                                   </ResponseMessageVO>
    *                                              </SEGResponseVO>
    */
    public Document addClient(Document requestDocument);

   /**
    * Returns the candidate Riders for a given BatchContractSetup
    *
    * @param requestDocument               SEGRequestVO containing the following structure:
    *
    *                                              <SEGRequestVO>
    *                                                  <RequestParameters>
    *                                                      <BatchContractSetupPK></BatchContractSetupPK>
    *                                                  </RequestParameters>
    *                                              </SEGRequestVO>
    *
    * @return  SEGResponseVO containing the saved object in the following structure:
    *
    *                                              <SEGResponseVO>
    *                                                  <CandidateRiderVO>       // repeats
    *                                                      <Coverage></Coverage>
    *                                                      <Qualifier></Qualifier>
    *                                                      <RequiredOptionalCT></RequiredOptionalCT>
    *                                                      <EffectiveDate></EffectiveDate>
    *                                                      <Units></Units>
    *                                                      <FaceAmount></FaceAmount>
    *                                                      <EOBMultiple></EOBMultiple>
    *                                                      <GIOOption></GIOOption>
    *                                                  </CandidateRiderVO>
    *                                                  <ResponseMessageVO>
    *                                                      ...
    *                                                  </ResponseMessageVO>
    *                                              </SEGResponseVO>
    */
    public Document getCandidateRiders(Document requestDocument);

   /**
    * Returns all candidate Enrollments for a given BatchContractSetup
    *
    * @param requestDocument               SEGRequestVO containing the following structure:
    *
    *                                              <SEGRequestVO>
    *                                                  <RequestParameters>
    *                                                      <BatchContractSetupPK></BatchContractSetupPK>
    *                                                  </RequestParameters>
    *                                              </SEGRequestVO>
    *
    * @return  SEGResponseVO containing the saved object in the following structure:
    *
    *                                              <SEGResponseVO>
    *                                                  <EnrollmentVO>       // repeats
    *                                                      ...
    *                                                  </EnrollmentVO>
    *                                                  <ResponseMessageVO>
    *                                                      ...
    *                                                  </ResponseMessageVO>
    *                                              </SEGResponseVO>
    */
    public Document getCandidateEnrollments(Document requestDocument);

    /**
     * Returns all Segments for the specified ClientDetailPK.
     * 
     * @param requestDocument               SEGRequestVO containing the following structure:
     *
     *                                              <SEGRequestVO>
     *                                                  <RequestParameters>
     *                                                      <ClientDetailPK></ClientDetailPK>
     *                                                  </RequestParameters>
     *                                              </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the saved object in the following structure:
     *
     *                                              <SEGResponseVO>
     *                                                  <SegmentVO>      // repeats
     *                                                      <ContractClientVO>
     *                                                          <ClientRoleVO/>
     *                                                       </ContractClientVO>
     *                                                       <ProductStructureVO/>
     *                                                  </SegmentVO>
     *                                                  <ResponseMessageVO>
     *                                                      ...
     *                                                  </ResponseMessageVO>
     *                                              </SEGResponseVO>
     */
    public Document getPolicyInformationByClientDetailPK(Document requestDocument);

    /**
     * Returns all the FilteredQuestionnaires and their Questionnaires for a given ProductStructure.  The are sorted
     * by the FilteredQuestionnaire's DisplayOrder
     *
     * @param requestDocument               SEGRequestVO containing the following structure:
     *
     *                                              <SEGRequestVO>
     *                                                  <RequestParameters>
     *                                                      <ProductStructurePK></ProductStructurePK>
     *                                                  </RequestParameters>
     *                                              </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the saved object in the following structure:
     *
     *                                              <SEGResponseVO>
     *                                                  <FilteredQuestionnaireVO>       // repeats
     *                                                      ...
     *                                                      <QuestionnaireVO>
     *                                                          ...
     *                                                       </QuestionnaireVO>
     *                                                  </FilteredQuestionnaireVO>
     *                                                  <ResponseMessageVO>
     *                                                      ...
     *                                                  </ResponseMessageVO>
     *                                              </SEGResponseVO>
     */
    public Document getFilteredQuestionnaires(Document requestDocument);
    
    /**
     * Returns all the FilteredQuestionnaires and their Questionnaires for a given ProductStructure.  The are sorted
     * by the FilteredQuestionnaire's DisplayOrder
     *
     * @param requestDocument               SEGRequestVO containing the following structure:
     *
     *                                              <SEGRequestVO>
     *                                                  <RequestParameters>
     *                                                      <PayrollDeductionSchedulePK></PayrollDeductionSchedulePK>
     *                                                      <PayrollDeductionYear></PayrollDeductionYear>
     *                                                  </RequestParameters>
     *                                              </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the saved object in the following structure:
     *
     *                                              <SEGResponseVO>
     *                                                  <PayrollDeductionCalendarVO></PayrollDeductionCalendarVO> // repeats
     *                                                  <ResponseMessageVO>
     *                                                      ...
     *                                                  </ResponseMessageVO>
     *                                              </SEGResponseVO>
     */    
    public Document getPayrollDeductionCalendars(Document requestDocument);
    
    
    /**
     * Updates the PayrollDeductionCalendar's PayrollDeductionCodeCT.
     *
     * @param requestDocument               SEGRequestVO containing the following structure:
     *
     *                                              <SEGRequestVO>
     *                                                  <RequestParameters>
     *                                                      <PayrollDeductionSchedulePK></PayrollDeductionSchedulePK>
     *                                                      <PayrollDeductionCodeCT></PayrollDeductionCodeCT>
     *                                                  </RequestParameters>
     *                                              </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the saved object in the following structure:
     *
     *                                              <SEGResponseVO>
     *                                                  // nothing
     *                                                  <ResponseMessageVO>
     *                                                      ...
     *                                                  </ResponseMessageVO>
     *                                              </SEGResponseVO>
     */       
    public Document updatePayrollDeductionCalendar(Document requestDocument);
    
    /**
     * Deletes the specified PayrollDeductionCalendar.
     * 
     * @param requestDocument               SEGRequestVO containing the following structure:
     *
     *                                              <SEGRequestVO>
     *                                                  <RequestParameters>
     *                                                      <PayrollDeductionSchedulePK></PayrollDeductionSchedulePK>
     *                                                  </RequestParameters>
     *                                              </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the saved object in the following structure:
     *
     *                                              <SEGResponseVO>
     *                                                  // nothing
     *                                                  <ResponseMessageVO>
     *                                                      ...
     *                                                  </ResponseMessageVO>
     *                                              </SEGResponseVO>
     */        
    public Document deletePayrollDeductionCalendar(Document requestDocument);
    
    
    /**
     * Creates a new PayrollDeductionCalendar from the specified PayrollDeductionSchedulePK,
     * PayrollDeductionDate, and PayrollDeductionCodeCT.
     * 
     * @param requestDocument               SEGRequestVO containing the following structure:
     *
     *                                              <SEGRequestVO>
     *                                                  <RequestParameters>
     *                                                      <PayrollDeductionSchedulePK></PayrollDeductionSchedulePK>
     *                                                      <PayrollDeductionDate></PayrollDeductionDate>
     *                                                      <PayrollDeductionCodeCT></PayrollDeductionCodeCT>
     *                                                  </RequestParameters>
     *                                              </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the saved object in the following structure:
     *
     *                                              <SEGResponseVO>
     *                                                  // nothing
     *                                                  <ResponseMessageVO>
     *                                                      ...
     *                                                  </ResponseMessageVO>
     *                                              </SEGResponseVO>
     */        
    public Document createPayrollDeductionCalendar(Document requestDocument);

    /**
     * Adds segments to a ContractGroup using information in an xml file (import).
     *
     * @param requestDocument
     *
     *                                  <SEGRequestVO>
     *                                      <RequestParameters>
     *                                          <BatchContractSetupPK>1</BatchContractSetupPK>
     *                                          <Operator>System</Operator>
     *                                          <ImportFileName>import.xml</ImportFileName>   // path is gotten from EDITServicesConfig
     *                                      </RequestParameters>
     *                                  </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the created Segments using the following structure:
     *
     *                                  <SEGResponseVO>
     *                                      <SEGResponseVO>             // repeats for each contract that was added
     *                                          <SegmentVO>
     *                                              ...
     *                                          </SegmentVO>
     *                                          <ResponseMessageVO>
     *                                              ...
     *                                          </ResponseMessageVO>
     *                                      </SEGResponseVO>
     *                                      <ResponseMessageVO>
     *                                          ...
     *                                      </ResponseMessageVO>
     *                                  </SEGResponseVO>
     */
    public Document importNewBusiness(Document requestDocument);
    
    /**
     * Gets the list of FilteredRequirements associated with the ProductStructure of "Case****".
     *
     * @param requestDocument
     *
     *                                  <SEGRequestVO>
     *                                      </RequestParameters>
     *                                  </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the created Segments using the following structure:
     *
     *                                  <SEGResponseVO>
     *                                      <SEGResponseVO>             
     *                                          <FilteredRequirementVO> // repeats for each contract that was added
     *                                              <RequirementVO>
     *                                              </RequirementVO>
     *                                          </FilteredRequirementVO>
     *                                      </SEGResponseVO>
     *                                      <ResponseMessageVO>
     *                                          ...
     *                                      </ResponseMessageVO>
     *                                  </SEGResponseVO>
     */
    public Document getCaseFilteredRequirements(Document requestDocument);

    /**
     * Gets all the BatchContractSetups and the ClientRole and ClientDetail for the group and case ContractGroups
     * for the specified partial ContractGroup.ContractGroupNumber.
     * Actually, it only gets those whose group ContractGroups have a termination date less than today's date (i.e.
     * that are active).  Cannot add contracts to a terminated group.
     *
     * @param
     *                                  <SEGRequestVO>
     *                                      <RequestParameters>
     *                                          <PartialContractGroupNumber></PartialContractGroupNumber>
     *                                      </RequestParameters>
     *                                  </SEGRequestVO>
     *
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                                      <SEGResponseVO>
     *                                          <BatchContractSetupVO>              // repeats for each BatchContractSetup
     *                                              ...
     *                                              <ContractGroupVO>               //  Group
     *                                                  ...
     *                                                  <ClientRole>
     *                                                     ...
     *                                                     <ClientDetailVO>
     *                                                          ...
     *                                                     </ClientDetailVO>
     *                                                  </ClientRole>
     *                                                  <ContractGroupVO>          //  Case
     *                                                     ...
     *                                                      <ClientRole>
     *                                                          ...
     *                                                          <ClientDetailVO>
     *                                                               ...
     *                                                          </ClientDetailVO>
     *                                                      </ClientRole>
     *                                                  </ContractGroupVO>
     *                                              </ContractGroupVO>
     *                                              <EnrollmentVO>                //  Candidate enrollments
     *                                                  ...
     *                                              </EnrollmentVO>
     *                                          </BatchContractSetupVO>
     *                                          <ResponseMessageVO>
     *                                              ...
     *                                          </ResponseMessageVO>
     */
    public Document getBatchContractSetupsByPartialContractGroupNumber(Document requestDocument);

    /**
     * Gets the GIOOptions associated with a CaseUnderwriting
     *
     * @param
     *                                  <SEGRequestVO>
     *                                      <RequestParameters>
     *                                          <BatchContractSetupPK></BatchContractSetupPK>
     *                                          <FilteredProductPK></FilteredProductPK>
     *                                          <RelationshipToEmployeeCT></RelationshipToEmployeeCT>
     *                                      </RequestParameters>
     *                                  </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                                      <SEGResponseVO>
     *                                          <GIOOption></GIOOption>              // repeats for each GIOOption
     *                                          <ResponseMessageVO>
     *                                              ...
     *                                          </ResponseMessageVO>
     *                                      </SEGResponseVO>
     */
    public Document getGIOOptions(Document requestDocument);

    /**
     * Updates the contract group note with any data changes made by the user.
     * @param contractGroupNote
     */
    public void saveContractGroupNote(ContractGroupNote contractGroupNote, Long contractGroupPK)  throws EDITCaseException;

    /**
     * Delete the selected note
     * @param contractGroupNotePK
     * @throws EDITCaseException
     */
    public void deleteContractGroupNote(Long contractGroupNotePK)  throws EDITCaseException;

    /**
     * Delete an existing enrollment if not being used by a BatchContractSetup
     * @param enrollmentPK
     * @return
     * @throws EDITCaseException
     */
    public String deleteEnrollment(Long enrollmentPK)  throws EDITCaseException;

    /**
     * Delete requested ProjectedBusiness row
     * @param projectedBusinessPK
     * @throws EDITCaseException
     */
    public void deleteProjectedBusiness(Long projectedBusinessPK)  throws EDITCaseException;

    /**
     * Save the enrollmentState row just created
     * @param enrollmentState
     * @param enrollmentPK
     * @throws EDITCaseException
     */
    public void saveEnrollmentState(EnrollmentState enrollmentState, Long enrollmentPK)  throws EDITCaseException;

    /**
     * Delete Selected EnrollmentState
     * @param enrollmentStatePK
     * @return
     * @throws EDITCaseException
     */
    public String deleteEnrollmentState(Long enrollmentStatePK) throws EDITCaseException;
    
    /**
     * Saves the specified enrollmentLeadServiceAgent along with associations of Enrollment via
     * the specified enrollmentPK and agentPK, clientDetailPK to be determined by the specified roleTypeCT and
     * referenceID. A ClientRole is considered unique by the roleTypeCT/referenceID combination. If
     * a ClientRole currently exists with the roleTypeCT/referenceID combination, that that one is
     * used, otherwise a new ClientRole is created using the specified values.
     * 
     * Before the EnrollmentLeadServiceAgent can be saved, it must also be verified that the Effective/Termination
     * dates do not conflict with any EnrollmentLeadServiceAgents of the same RoleTypeCT for the same Agent.
     * @param regionCT
     * @param effectiveDate
     * @param terminationDate
     * @param enrollmentPK
     * @param agentPK
     * @param clientDetailPK
     * @param roleTypeCT
     * @param referenceID
     * @exception EDITCaseException if unable to save due to overlapping effective/termination dates
     */
    public void saveEnrollmentLeadServiceAgent(String regionCT, EDITDate effectiveDate, EDITDate terminationDate, 
                                     Long enrollmentPK, Long agentPK, Long clientDetailPK, String roleTypeCT, 
                                     String referenceID) throws EDITCaseException;     
                                     
    /**
     * Deletes the EnrollmentLeadServiceAgent by the specified PK.
     * @param enrollmentLeadServiceAgentPK
     */
    public void deleteEnrollmentLeadServiceAgent(Long enrollmentLeadServiceAgentPK);   
    
    /**
     * Updates the specified EnrollmentLeadServiceAgent with the specified information.
     * 
     * Before the EnrollmentLeadServiceAgent can be updated, it must also be verified that the Effective/Termination
     * dates do not conflict with any EnrollmentLeadServiceAgents of the same RoleTypeCT for the same Agent.
     * @param enrollmentLeadServiceAgentPK
     * @param regionCT
     * @param effectiveDate
     * @param terminationDate
     * @throws EDITCaseException if the effectiveDate/terminationDate conflict with an existing EnrollmentLeadServiceAgent
     */
    public void updateEnrollmentLeadServiceAgent(Long enrollmentLeadServiceAgentPK, 
                                       String regionCT, EDITDate effectiveDate, 
                                       EDITDate terminationDate) throws EDITCaseException;

    public Document getCandidateFunds(Document requestDocument);
    public Document getProductStructures(Document requestDocument);
    public Document getProductStructures();

    /**
     * Deletes the requested Case and all it Children
     * @param casePK
     * @return
     */
    public String deleteCaseAndChildren(Long contractGroupPK)  throws EDITCaseException;

    /**
     * Save the CaseSetup - child of ContractGroup
     * @param caseSetup
     * @param contractGroupPK
     * @throws EDITCaseException
     */
    public void saveCaseSetup(CaseSetup caseSetup, Long contractGroupPK) throws EDITCaseException;

    /**
     * Delete the selected CaseSetup
     * @param caseSetupPK
     * @throws EDITCaseException
     */
    public void deleteCaseSetup(Long caseSetupPK) throws EDITCaseException;

}
