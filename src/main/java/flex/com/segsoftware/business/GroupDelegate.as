package com.segsoftware.business
{
	import com.adobe.cairngorm.business.ServiceLocator;
	import com.adobe.serialization.json.JSON;
	import com.segsoftware.command.SEGRequest;
	import com.segsoftware.model.SEGModelBuilder;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.model.encoding.BatchContractImport;
	import com.segsoftware.model.encoding.BatchContractImportEntry;
	import com.segsoftware.utility.Util;
	
	import flash.events.Event;
	import flash.net.FileReference;
	
	import mx.collections.ArrayCollection;
	import mx.collections.XMLListCollection;
	import mx.controls.Alert;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.http.HTTPService;
	
	/**
	 * A "Proxy" to the Group services.
	 */ 
	public class GroupDelegate extends SEGDelegate
	{
		function GroupDelegate(result:Function, fault:Function, requestParameters:XMLListCollection)
		{
			super(result, fault, requestParameters);
		}
		
		/**
		 * Executes a batch import from a given source file reference, which 
		 * has already been selected in a file browser by the user.
		 */
		public static function performBatchImport(source:FileReference):void {
			var operator:String = SEGModelLocator.getInstance().operator;
			var setupPk:Number  = Number(SEGModelLocator.getInstance()
				.selectedBatchContractSetupVO.BatchContractSetupPK[0].children()[0]); 			
			
			var url:String = "/api/batch-contract?batchContractSetupPk=" + encodeURIComponent(setupPk.toString()) + "&operator=" + encodeURIComponent(operator);
			Services.postFile(url, source, onComplete, onFailure);
			
			function onFailure(evt:Event):void {
				trace(evt);
			}
			
			function onComplete(evt:Event):void {
				
			}
		}
		
		/**
		 * Loads detail for a specific batch contract record
		 */
		public static function loadBatchImportDetail(inst:BatchContractImport, detailCallback:Function):void {
			var detailUrl:String = "/api/batch-contract/" + 
				inst.batchContractImportFilePk + "?cache_bust=" + new Date().getTime();
			
			Services.issueHttpGet(detailUrl, 
				function(evt:Event):void { // success event
					var entry:Object = JSON.decode(evt.target.data as String);
					detailCallback(new BatchContractImport(entry));
				}, function(evt:Event):void { // failure event
					trace(evt);
					Alert.show(evt.toString(), "Error Retreiving Import Detail");
					detailCallback(null);
				});	
		}
		
		/**
		 * Gets an array of 'condensed' import log information,
		 * containing top-level information but not information about import entries or logs. 
		 */
		public static function loadBatchImportLogs(callback:Function, detailCallback:Function):void {
			function successHandler(e:Event):void {
				var response:String = e.target.data;
				var entries:Object = JSON.decode(response);
				
				var arr:ArrayCollection = new ArrayCollection();
				var pendingrecs:ArrayCollection = new ArrayCollection();
				var length:int = entries.length;
				// 	Loop through backwards to display the newest message first
				for (var i:int = 0; i < length; i++)
				{
					var jObj:Object = entries[i];
					var inst:BatchContractImport = new BatchContractImport(jObj);
					arr.addItem(inst);
					
					// get detail of runs pending completion or runs completed within 1 minute
					if(inst.status == "PENDING" || (inst.completedTime != null && ((new Date()).time - inst.completedTime.time) < 1000 * 60)) {
						pendingrecs.addItem(inst);					
					}
				}
				
				callback(arr);
				
				for each(var pendingRec:BatchContractImport in pendingrecs) { 
					loadBatchImportDetail(pendingRec, detailCallback);
				}
			}
			
			Services.issueHttpGet("/api/batch-contract/?cache_bust=" + new Date().getTime(), successHandler, 
				function(evt:Event):void {
					trace(evt);
					Alert.show(evt.toString(), "Error Retreiving Import Logs");
					callback(null);
				});
		}
		
		/**
		 * Uses the supplied Case Name, Group Name, and Operator to build an "initial"
		 * BatchContractSetup entity. Further details will be supplied
		 * in a subsequent update to this entity. This default entity will contain:
		 * 
		 * BatchID - system generated via appropriate rules
		 * StatusCT - system generated via appriate rules
		 * CreationOperator - supplied with this service request
		 * CreationDate - system generated 
		 * 
		 * @return the newly generated BatchContractSetupPK
		 */
		public function createDefaultBatchContractSetup():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "createDefaultBatchContractSetup", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);			
		}
		
		 /** 
		 * The generated response will contain:
		 * 
		 * <SEGResponseVO>
		 * 	<BatchContractSetupDocumentVO>
		 * 		<ClientDetailVO>
		 * 			<ClientRoleVO/> // Needed to know if this is the Case or Group ClientDetail.
		 * 		</ClientDetailVO>
		 * 		<ClientDetailVO>
		 * 			<ClientRoleVO/> // Needed to know if this is the Case or Group ClientDetail.
		 * 		</ClientDetailVO>
		 * 		<BatchContractSetupVO/>
		 *      <SelectedAgentHierarchyVO/> // repeated
		 *  </BatchContractSetupDocumentVO>
		 * </SEGResponseVO>
		 */ 		  
		public function getAllBatchContractSetups():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getAllBatchContractSetups", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);	
		}
		 	  
		public function getRatedGenderFromCaseProduct():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getRatedGenderFromCaseProduct", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);	
		}
		/**
		 * Finds all Group-ContractGroups by the specified groupName.
		 * The groupName is defined as:
		 * 
		 * ContractGroup.ClientRole.ClientDetail.CorporateName.
		 * 
		 * The SEGResponseVO resembles:
		 * 
		 * <SEGResponseVO>
		 * 	<SEGEntryVO>
		 * 		<ContractGroupVO>	
		 * 			<ClientDetailVO>
		 * 		<ContractGroupVO>		 
		 * 			<ClientDetailVO>
		 * 	</SEGEntryVO>
		 * 	...
		 * 	<SEGEntryVO/>
		 * </SEGResponseVO>
		 * 
		 * It is necessary to know which ContractGroupVO is a 'Case' one, 
		 * and which is the 'Group' one. It is assumed that the coder
		 * will examine ContractGroupVO.ContractGroupTypeCT to determine
		 * this.
		 */
		public function getGroupContractGroupsByGroupName():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getGroupContractGroupsByGroupName", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);
		}
		
		/**
		 * Finds all Group-ContractGroups by the specified groupNumber.
		 * The groupNumber is defined as:
		 * 
		 * ContractGroup.ContractGroupNumber (where ContractGroup.ContractGroupTypeCT = 'Group')
		 * 
		 * The SEGResponseVO resembles:
		 * 
		 * <SEGResponseVO>
		 * 	<SEGEntryVO>
		 * 		<ContractGroupVO>	
		 * 			<ClientDetailVO>
		 * 		<ContractGroupVO>		 
		 * 			<ClientDetailVO>
		 * 	</SEGEntryVO>
		 * 	...
		 * 	<SEGEntryVO/>
		 * </SEGResponseVO>
		 * 
		 * It is necessary to know which ContractGroupVO is a 'Case' one, 
		 * and which is the 'Group' one. It is assumed that the coder
		 * will examine ContractGroupVO.ContractGroupTypeCT to determine
		 * this.
		 */
		public function getGroupContractGroupByGroupNumber():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getGroupContractGroupByGroupNumber", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);
		}		
		
		/**
		 * The BatchContractSetup is associated to a Case-ContractGroup which has
		 * predefined "candidate" AgentHierarchies. The association path looks as follows:
		 * 
		 * BatchContractSetup.ContractGroup(Group).ContractGroup(Case).AgentHierarchies(Candidate).
		 * 
		 * The resulting document from this request is convoluted in that much of the information
		 * is found in the writing PlacedAgent. Recall that the writing PlacedAgent is the PlacedAgent
		 * that has the [highest] hierarchy level in a PlacedAgent branch. This holds true for the
		 * set of AgentSnapshots off of an AgentHierarchy as the AgentSnapshots are basically clones
		 * of PlacedAgents at a moment in time.
		 * 
		 * The document looks as follows:
		 * 
		 * <SEGResponseVO>
		 * 	<SEGEntryVO>
		 * 		<AgentHierarchyVO>
		 * 			<AgentHierarchyAllocationVO/> (split %)
		 * 			<PlacedAgentVO/> (situation code)
		 * 			<CommissionProfileVO/>
		 * 			<AgentVO/> (agent #)
		 * 			<ClientDetailVO/> (agent name)	
		 * 		</AgentHierarchyVO>
		 * 	</SEGEntryVO>
		 *  ...
		 * 	<SEGEntry/>
		 * </SEGResponseVO>
		 */ 
		public function getCandidateAgentHierarchies():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getCandidateWritingAgents", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);			
		}
		
		/**
		 * Core information related to a BatchContractSetup needs to be
		 * updated (regularly?). The information that can be updated is as a
		 * follows:
     	 * <FilteredProductPK>11111</FilteredProductPK>
     	 * <StatusCT>AL</StatusCT>
         * <EffectiveDate>AL</EffectiveDate>
         * <ApplicationReceivedDate>01/01/2007</ApplicationReceivedDate>
         * <ApplicationSignedDate>01/01/2007</ApplicationSignedDate>
         * <ApplicationSignedStateCT>CT</ApplicationSignedStateCT>
         * <IssueStateCT>CT</IssueStateCT>
         * <DeathBenefitOptionCT>Level</DeathBenefitOptionCT>
         * <NonForfeitureOptionCT></NonForfeitureOptionCT>
         * <AgentHierarchyPK>3333</AgentHierarchyPK>// repeated
         * 
         * @return the updated BatchContractSetupVO
		 */  
		public function updateBatchContractSetup():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "updateBatchContractSetup", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);					
		}
		
		/**
		 * Clients that are added to the AppEntry (BatchSegment) need to be found and then applied.
		 * Ultimately, they will end-up as ContractClients against the generated Segment.
		 * 
		 * This finds ClientDetail.ClientAddress rows by partial LastName/CorporateName.
		 * 
		 * The ClientAddress is assumed to be the primary address (if it exists).
		 */ 
		public function getClientsByName():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getClientsByName", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);
		}
		
		/**
		 * Clients that are added to the AppEntry (BatchSegment) need to be found and then applied.
		 * Ultimately, they will end-up as ContractClients against the generated Segment.
		 * 
		 * This finds clients by ClientDetail.TaxIdentification.
		 * 
		 * The ClientAddress is assumed to be the primary address (if it exists).
		 */ 
		public function getClientsByTaxID():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getClientsByTaxID", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);			
		}		
		
		/**
		 * Clients that are added to the AppEntry (BatchSegment) need to be found and then applied.
		 * Ultimately, they will end-up as ContractClients against the generated Segment.
		 * 
		 * The ClientAddress is assumed to be the primary address (if it exists).
		 * 
		 * This finds clients by ClientDetail.Name (last or corporate) and ClientDetail.DOB.
		 */ 
		public function getClientsByNameAndDateOfBirth():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getClientsByNameAndDateOfBirth", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);
		}
		
		/**
		 * Finds all of the BatchProgressLogs by the current BatchContractSetup.
		 */  
		public function getBatchProgressLogs():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getBatchProgressLogs", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);		
		}
		
		/**
		 * Finds all of the BatchProductLogs by the current BatchContractSetup.
		 */  
		public function getBatchProductLogs():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getBatchProductLogs", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);		
		}			

		/**
		 * Gets all filteredProducts from the specified BatchContractSetupPK.
		 */ 		
		public function getCandidateFilteredProducts():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getCandidateFilteredProducts", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);			
		}	
		
		/**
		 * Gets the DepartmentLocations whose Effective/Termination dates support
		 * the current system date.
		 */ 	
		public function getActiveDepartmentLocations():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getActiveDepartmentLocations", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);			
		}
		
		/**
		 * Adds a stripped Segment and its Client related information to a ContractGroup (group).
		 * 
		 * The supplied paramters are:
		 * 
		 * <SegmentInformationVO>
    	 * 		<FilteredProductPK/>
		 *	    <ContractNumber/>
		 *	    <DepartmentLocationPK/>
		 *	    <IssueStateCT/>
		 *	    <FaceAmount/>
 		 *	    <EffectiveDate/>
		 *	    <ApplicationSignedDate/>
		 *	    <ApplicationReceivedDate/>
		 *	    <ApplicationSignedStateCT/>
		 *      <IssueStateCT/>
		 *	    <DeathBenefitOptionCT/>
		 *   	<NonForfeitureOptionCT/>
		 * 		<EstateOfTheInsured/>
		 * 		<DeductionAmountOverride/>
		 * 		<DeductionAmountEffectiveDate/>
		 * </SegmentInformationVO>
		 * 
		 * <BatchContractSetupPK/>
		 * 
		 * <Operator>
		 * 
		 * <RelationshipToEmployeeCT>
		 * 
		 * 	<ClientInformationVO>                  
		 *    <ClientDetailVO/>
		 *    <RelationshipToEmployeeCT/>
		 *    <EmployeeIdentification/>
		 *    <ContractClientInformationVO>      // repeats for each role
		 *        <RoleTypeCT/>
		 *        <ClassCT/>
		 *        <TableRatingCT/>
		 *        <RatedGenderCT/>
		 *        <UnderwritingClassCT/>
		 *        <PrimaryBeneficiaryAllocation/>
		 *        <SecondaryBeneficiaryAllocation/>
		 *    </ContractClientInformationVO>
		 * </ClientInformationVO>
		 */ 
		public function addSegmentToContractGroup():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "addSegmentToContractGroup", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);				
		}
		
		public function getSelectedAgentHierarchies():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getSelectedWritingAgents", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);			
		}
		
		/**
		 * Adds a most basic ClientDetail encompassing a skeletal ClientDetail with a 
		 * skeletal primary ClientAddress.
		 */ 		
		public function quickAddClient():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "addClient", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);					
		}
	
		/**
		 * Returns the collection of PayrollDeductionCalendarVOs for the specified
		 * year and PayrollDeductionSchedulePK.
		 */
		public function getPayrollCalendarDeductionsByYearAndPayrollDeductionSchedulePK():XMLListCollection
		{
			var payrollDeductionCalendarVOs:XMLListCollection = new XMLListCollection();
			
			return payrollDeductionCalendarVOs;				
		}
		
		/**
		 * Finds the following Segment structures by ClientDetailPK.
		 * 
		 * <SegmentVO>
		 * 		<ProductStructureVO/>
		 * 		<ContractClientVO> // repeated
		 * 			<ClientRoleVO/>
		 * 		</ContractClientVO>
		 * </SegmentVO>
		 */ 
		public function getPolicyInformationByClientDetailPK():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getPolicyInformationByClientDetailPK", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);					
		}
		
		/**
		 * The collection of Enrollments by BatchContractSetupPK available.
		 * @param BatchContractSetupPK
		 * @return EnrollmentVO
		 */ 
		public function getCandidateEnrollments():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getCandidateEnrollments", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);				
		}
		
		/**
		 * The set of CandidateRiderVOs that may be used for a specified BatchContractSetupPK.
		 * @param BatchContractSetupPK
		 * @return the list of CandidateRiderVOs
		 */ 	
		public function getCandidateRiders():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getCandidateRiders", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);				
		}
	
		/**
		 * Generates a new business quote using the information that is normally supplied for
		 * the saving of a Segment during AppEntry.
		 */ 	
		public function getQuote():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getQuote", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);				
		}
		
	    /**
     	* Returns all the FilteredQuestionnaires and their Questionnaires for a given ProductStructure.  The are sorted
     	* by the FilteredQuestionnaire's DisplayOrder
     	*/
		public function getFilteredQuestionnaires():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getFilteredQuestionnaires", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);			
		}
		
		/**
		 * Gets all PayrollDeductionCalendars for the specified PayrollDeductionSchedulePK and
		 * Year.
		 */ 
		public function getPayrollDeductionCalendars():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getPayrollDeductionCalendars", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);				
		}
		
		/**
		 * Updates the specified PayrollDeductionCalendar (specified by PayrollDeductionCalendarPK) 
		 * with the specified PayrollDeductionCodeCT (the only editable field for PayrollDeductionCalendar).
		 */ 
		public function updatePayrollDeductionCalendar():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "updatePayrollDeductionCalendar", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);							
		}
		
		/**
		 * Deletes the specified PayrollDeductionCalendar (specified by PayrollDeductionCalendarPK). 
		 */ 
		public function deletePayrollDeductionCalendar():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "deletePayrollDeductionCalendar", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);							
		}	
		
		/**
		 * Uses the specified payrollDeductionCodeCT and payrollDeductionDate to create
		 * a new PayrollDeductionCalendar for the specified PayrollDeductionSchedulePK.
		 * 
		 * The service returns the newly created PayrollDeductionCalendarVO.
		 */ 	
		public function createPayrollDeductionCalendar():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "createPayrollDeductionCalendar", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);			
		}	
		
		/**
		 * Users can avoid manual entry of the BatchContractSetup/AppEntry process and
		 * specify an import file containing the relevant information. That information being:
		 * 
		 * BatchContractSetupPK
		 * Operator
		 * FileName
		 */ 
		public function importNewBusiness():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "importNewBusiness", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);			
		}
		
		/**
		 * The list of FilteredRequirements associated with the ProductStructure of "Case***".
		 */ 
		public function getCaseFilteredRequirements():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getCaseFilteredRequirements", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);			
		}
		
		/**
		 * The list of BatchContractSetups whose ContractGroup.ContractGroupNumber
		 * partially matches the specified PartialContractGroupNumber.
		 */ 
		public function getBatchContractSetupsByPartialContractGroupNumber():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getBatchContractSetupsByPartialContractGroupNumber", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);			
		}		
		
		/**
		 * Finds the GIO options for riders associated to the relationshipToEmployee
		 * 
		 * BatchContractSetupPK
		 * FilteredProductPK
		 * RelationshipToEmployeeCT
		 */ 
		public function getGIOOptions():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getGIOOptions", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);			
		}
		
		/**
		 * Deletes BatchContractSetup
		 * BatchContractSetupPK 
		 */
		public function deleteBatchContractSetup():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "deleteBatchContractSetup", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);
		}
		
		/**
		 * Copies BatchContractSetup
		 * BatchContractSetupPK 
		 */
		public function copyBatchContractSetup():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "copyBatchContractSetup", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);
		}
		
		public function getDeductionFrequencyDesc():void
		{
			var segRequest:SEGRequest = new SEGRequest("Group", "getDeductionFrequencyDesc", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);					
		}
	}
}