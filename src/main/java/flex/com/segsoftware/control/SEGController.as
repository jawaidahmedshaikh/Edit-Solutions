package com.segsoftware.control
{
	import com.adobe.cairngorm.control.FrontController;
	import com.segsoftware.command.*;
	import com.segsoftware.command.query.*;

	/**
	 * Registers the set of [all] use-case-level gestures available to the
	 * user as Command objects. The Commands are [typically] dispatched by user 
	 * interaction with the front-end (e.g. clicking a button), although they may 
	 * also by system-generated. The Command objects are then executed by the framework
	 * via the execute() method. The results of the command are [typically] used to 
	 * update the Model which, in turn, updates the view with [typically] binding
	 * variables.
	 */
	public class SEGController extends FrontController 
	{
		/* The set of all actor-driven commands in the system. */
		public static var EVENT_GET_NOT_PAID_BILLED:String = "EVENT_GET_NOT_PAID_BILLED";
		public static var EVENT_GET_BILLGROUP_PAYORS:String = "EVENT_GET_BILLGROUP_PAYORS";
		public static var EVENT_GET_PAYOR_POLICY_BILLS:String = "EVENT_GET_PAYOR_POLICY_BILLS";
		public static var EVENT_ADJUST_BILL_PAID_AMOUNTS:String = "EVENT_ADJUST_BILL_PAID_AMOUNTS";
		
		public static var EVENT_CREATE_DEFAULT_BATCHCONTRACTSETUP:String = "EVENT_CREATE_DEFAULT_BATCHCONTRACTSETUP";
		public static var EVENT_GET_GROUPS_BY_GROUPNAME_OR_GROUPNUMBER:String = "EVENT_GET_GROUPS_BY_GROUPNAME_OR_GROUPNUMBER";
		public static var EVENT_UPDATE_BATCHCONTRACTSETUP:String = "EVENT_UPDATE_BATCHCONTRACTSETUP";
		public static var EVENT_SELECT_CANDIDATE_AGENTHIERARCHY:String = "EVENT_SELECT_CANDIDATE_AGENTHIERARCHY";
		
		public static var EVENT_DESELECT_SELECTEDAGENTHIERARCHY:String = "EVENT_DESELECT_SELECTEDAGENTHIERARCHY";
		public static var EVENT_GET_CLIENTS_BY_NAME_TAXID_DOB:String = "EVENT_GET_CLIENTS_BY_NAME_TAXID_DOB";
		public static var EVENT_ADD_APPENTRY_CLIENTDETAILS:String = "EVENT_ADD_APPENTRY_CLIENTDETAILS";
		public static var EVENT_SHOW_CLIENTROLE_INFORMATION:String = "EVENT_SHOW_CLIENTROLE_INFORMATION";
				
		public static var EVENT_REMOVE_USED_CANDIDATECLIENTROLES:String = "EVENT_REMOVE_USED_CANDIDATECLIENTROLES";
		public static var EVENT_SELECT_CANDIDATECLIENTROLES:String = "EVENT_SELECT_CANDIDATECLIENTROLES";
		public static var EVENT_DESELECT_CANDIDATECLIENTROLES:String = "EVENT_DESELECT_CANDIDATECLIENTROLES";
		public static var EVENT_GET_ALL_BATCHCONTRACTSETUPS:String = "EVENT_GET_ALL_BATCHCONTRACTSETUPS";
		public static var EVENT_LISTRATEDGENDER:String = "EVENT_LISTRATEDGENDER";
		public static var EVENT_GET_CANDIDATE_AGENTHIERARCHIES:String = "EVENT_GET_CANDIDATE_AGENTHIERARCHIES";
		
		public static var EVENT_LOAD_CODETABLEDOCUMENT:String = "EVENT_LOAD_CODETABLEDOCUMENT";
		public static var EVENT_ADD_BATCHPROGRESSLOG_ENTRY:String = "EVENT_ADD_BATCHPROGRESSLOG_ENTRY";
		public static var EVENT_UPDATE_BATCH_TRANSMITTED_DETAILS:String = "EVENT_UPDATE_BATCH_TRANSMITTED_DETAILS";
		public static var EVENT_GET_BATCHPROGRESSLOGS:String = "EVENT_GET_BATCHPROGRESSLOGS";
		
		public static var EVENT_ADD_BATCHPRODUCTLOG_ENTRY:String = "EVENT_ADD_BATCHPRODUCTLOG_ENTRY";
		public static var EVENT_GET_CANDIDATE_PRODUCTSTRUCTURES:String = "EVENT_GET_CANDIDATE_PRODUCTSTRUCTURES";
		public static var EVENT_GET_BATCHPRODUCTLOGS:String = "EVENT_GET_BATCHPRODUCTLOGS";
		public static var EVENT_CLEAR_BATCHCONTRACTSETUPMODELS:String = "EVENT_CLEAR_BATCHCONTRACTSETUPMODELS";
		
		public static var EVENT_INSTANTIATE_VALUEOBJECTVO:String = "EVENT_INSTANTIATE_VALUEOBJECTVO";
		//public static var EVENT_BEGIN_APP_ENTRY:String = "EVENT_BEGIN_APP_ENTRY";
		//public static var EVENT_CLEAR_APPENTRY:String = "EVENT_CLEAR_APPENTRY";
		public static var EVENT_APPENTRY:String = "EVENT_APPENTRY";
		//public static var EVENT_SAVE_APPENTRY:String = "EVENT_SAVE_APPENTRY";
		
		public static var EVENT_GET_SELECTED_AGENTHIERARCHIES:String = "EVENT_GET_SELECTED_AGENTHIERARCHIES";
		//public static var EVENT_REMOVE_INSURED_FROM_SELECTED_ROLES:String = "EVENT_REMOVE_INSURED_FROM_SELECTED_ROLES"
		public static var EVENT_SAVE_QUICK_ADD_CLIENT:String = "EVENT_SAVE_QUICK_ADD_CLIENT";
		public static var EVENT_GET_PAYROLLDEDUCTIONCALENDARS_BY_YEAR_AND_PAYROLLDEDUCTIONSCHEDULEPK:String = "EVENT_GET_PAYROLLDEDUCTIONCALENDARS_BY_YEAR_AND_PAYROLLDEDUCTIONSCHEDULEPK";
		 	
		public static var EVENT_GET_POLICY_INFORMATION_BY_CLIENTDETAILPK:String = "EVENT_GET_POLICY_INFORMATION_BY_CLIENTDETAILPK";
		public static var EVENT_GET_CANDIDATE_ENROLLMENTS:String = "EVENT_GET_CANDIDATE_ENROLLMENTS";
		public static var EVENT_SELECT_CANDIDATERIDERS:String = "EVENT_SELECT_CANDIDATERIDERS";
		
		public static var EVENT_DESELECT_CANDIDATERIDERS:String = "EVENT_DESELECT_CANDIDATERIDERS";
		public static var EVENT_GET_QUOTE:String = "EVENT_GET_QUOTE";
		public static var EVENT_UPDATE_BILLGROUP:String = "EVENT_UPDATE_BILLGROUP";
		public static var EVENT_GET_FILTERED_QUESTIONNAIRES:String = "EVENT_GET_FILTERED_QUESTIONNAIRES";
		
		public static var EVENT_BEGIN_QUICK_ADD_CLIENT:String = "EVENT_BEGIN_QUICK_ADD_CLIENT";
		public static var EVENT_GET_PAYROLLDEDUCTIONCALENDARS:String = "EVENT_GET_PAYROLLDEDUCTIONCALENDARS";
		public static var EVENT_UDPATE_DELETE_PAYROLLDEDUCTIONCALENDARS:String = "EVENT_UDPATE_DELETE_PAYROLLDEDUCTIONCALENDARS";
		public static var EVENT_CREATE_PAYROLLDEDUCTIONCALENDAR:String = "EVENT_CREATE_PAYROLLDEDUCTIONCALENDAR";
		
		public static var EVENT_IMPORT_NEW_BUSINESS_BATCH:String = "EVENT_IMPORT_NEW_BUSINESS_BATCH";
		public static var EVENT_GET_BILLS_FOR_CONTRACT_IN_BILLGROUP:String = "EVENT_GET_BILLS_FOR_CONTRACT_IN_BILLGROUP";
		public static var EVENT_GET_CASE_FILTEREDREQUIREMENTS:String = "EVENT_GET_CASE_FILTEREDREQUIREMENTS";
		public static var EVENT_GET_ALL_CONVERSION_TEMPLATES:String = "EVENT_GET_ALL_CONVERSION_TEMPLATES";
		 	
		public static var EVENT_CREATE_CONVERSION_TEMPLATE:String = "EVENT_CREATE_CONVERSION_TEMPLATE"; 
		public static var EVENT_DELETE_CONVERSION_TEMPLATE:String = "EVENT_DELETE_CONVERSION_TEMPLATE";	
		public static var EVENT_UPDATE_CONVERSION_TEMPLATE:String = "EVENT_UPDATE_CONVERSION_TEMPLATE";
		public static var EVENT_VALIDATE_CONVERSION_TEMPLATE:String = "EVENT_VALIDATE_CONVERSION_TEMPLATE";
		
		public static var EVENT_LOAD_SAMPLE_CONVERSION_DATA:String = "EVENT_LOAD_SAMPLE_CONVERSION_DATA";
		public static var EVENT_TEST_CONVERSION_TEMPLATE:String = "EVENT_TEST_CONVERSION_TEMPLATE";
		public static var EVENT_LOAD_CONVERSION_DATA:String = "EVENT_LOAD_CONVERSION_DATA";

		public static var EVENT_GET_CONVERSION_JOBS:String = "EVENT_GET_CONVERSION_JOBS";
		public static var EVENT_GET_CONVERSION_LOGS:String = "EVENT_GET_CONVERSION_LOGS";
		public static var EVENT_RUN_CONVERSION:String = "EVENT_RUN_CONVERSION";		
		public static var EVENT_FILTER_CODETABLES:String = "EVENT_FILTER_CODETABLES";
		
		public static var EVENT_SPLIT_BENEFICIARIES_EQUALLY:String = "EVENT_SPLIT_BENEFICIARIES_EQUALLY"; 
		public static var EVENT_GET_ALL_PRASEDOCUMENTS:String = "EVENT_GET_ALL_PRASEDOCUMENTS";
		public static var EVENT_UPDATE_PRASE_DOCUMENT:String = "EVENT_UPDATE_PRASE_DOCUMENT";
		public static var EVENT_CLONE_PRASEDOCUMENT:String = "EVENT_CLONE_PRASEDOCUMENT";

		//public static var EVENT_GET_DEDUCTION_FREQUENCY_DESC:String = "EVENT_GET_DEDUCTION_FREQUENCY_DESC";
		
		public static var EVENT_GET_ASSOCIATED_PRASETESTS:String = "EVENT_GET_ASSOCIATED_PRASETESTS";
		public static var EVENT_GET_ALL_PRASETESTS:String = "EVENT_GET_ALL_PRASETESTS";
		public static var EVENT_GET_ALL_PRODUCTSTRUCTURES:String = "EVENT_GET_ALL_PRODUCTSTRUCTURES";
		public static var EVENT_GET_CANDIDATE_PRASEDOCUMENTS:String = "EVENT_GET_CANDIDATE_PRASEDOCUMENTS";
		
		public static var EVENT_GET_SELECTED_PRASEDOCUMENTS:String = "EVENT_GET_SELECTED_PRASEDOCUMENTS";
		public static var EVENT_GET_BATCHCONTRACTSETUPS_BY_PARTIALCONTRACTGROUPNUMBER:String = "EVENT_GET_BATCHCONTRACTSETUPS_BY_PARTIALCONTRACTGROUPNUMBER";
		public static var EVENT_UPDATE_PRASETEST:String = "EVENT_UPDATE_PRASETEST";
		public static var EVENT_SELECT_CANDIDATEPRASEDOCUMENTS:String = "EVENT_SELECT_CANDIDATEPRASEDOCUMENTS";
		
		public static var EVENT_DESELECT_CANDIDATEPRASEDOCUMENTS:String = "EVENT_DESELECT_CANDIDATEPRASEDOCUMENTS";
		public static var EVENT_CREATE_PRASETEST:String = "EVENT_CREATE_PRASETEST";
		public static var EVENT_EDIT_ASSOCIATED_PRASETEST:String = "EVENT_EDIT_ASSOCIATED_PRASETEST";
		public static var EVENT_EDIT_PRASEDOCUMENT:String = "EVENT_EDIT_PRASEDOCUMENT";
		
		public static var EVENT_MARK_ROOT_PRASEDOCUMENT:String = "EVENT_MARK_ROOT_PRASEDOCUMENT";
		public static var EVENT_RUN_PRASETEST:String = "EVENT_RUN_PRASETEST";
		public static var EVENT_SET_ACTUAL_AS_EXPECTED:String = "EVENT_SET_ACTUAL_AS_EXPECTED";
		public static var EVENT_GET_ALL_TEST_FILES:String = "EVENT_GET_ALL_TEST_FILES";
		
		public static var EVENT_GET_SCRIPT_PROCESSOR_RESULTS:String = "EVENT_GET_SCRIPT_PROCESSOR_RESULTS";
		public static var EVENT_EXPORT_RUN_DATA:String = "EVENT_EXPORT_RUN_DATA";
		public static var EVENT_CLEAR_RECORDED_OPERATOR_DATA:String = "EVENT_CLEAR_RECORDED_OPERATOR_DATA";
		public static var EVENT_ADD_NEW_QUERY:String = "EVENT_ADD_NEW_QUERY";
		 	
		public static var EVENT_GET_GIO_OPTIONS:String = "EVENT_GET_GIO_OPTIONS";
		public static var EVENT_SAVE_QUERY:String = "EVENT_SAVE_QUERY";
		public static var EVENT_EXECUTE_QUERY_STATEMENT:String = "EVENT_EXECUTE_QUERY_STATEMENT";
		
		public static var EVENT_DELETE_BATCHCONTRACTSETUP:String = "EVENT_DELETE_BATCHCONTRACTSETUP";
		public static var EVENT_COPY_BATCHCONTRACTSETUP:String = "EVENT_COPY_BATCHCONTRACTSETUP";

		 	
		public function SEGController()
		{
			initializeCommands();			
		}
		
		/**
		 * Register the individual Command objects with the framework.
		 */ 
		public function initializeCommands():void
		{
			addCommand(EVENT_GET_NOT_PAID_BILLED, GetNotPaidBilledCommand);
			addCommand(EVENT_GET_BILLGROUP_PAYORS, GetBillGroupPayorsCommand);
			addCommand(EVENT_GET_PAYOR_POLICY_BILLS, GetPayorPolicyBillsCommand);
			addCommand(EVENT_ADJUST_BILL_PAID_AMOUNTS, AdjustBillPaidAmountsCommand);
			
			addCommand(EVENT_CREATE_DEFAULT_BATCHCONTRACTSETUP, CreateDefaultBatchContractSetupCommand);
			addCommand(EVENT_GET_GROUPS_BY_GROUPNAME_OR_GROUPNUMBER, GetGroupsByGroupNameOrGroupNumberCommand);	
			addCommand(EVENT_UPDATE_BATCHCONTRACTSETUP, UpdateBatchContractSetupCommand);
			addCommand(EVENT_SELECT_CANDIDATE_AGENTHIERARCHY, SelectCandidateAgentHierarchyCommand);		
			
			addCommand(EVENT_DESELECT_SELECTEDAGENTHIERARCHY, DeselectSelectedAgentHierarchyCommand);
			addCommand(EVENT_GET_CLIENTS_BY_NAME_TAXID_DOB, GetClientsByNameTaxIdDOBCommand);
			addCommand(EVENT_ADD_APPENTRY_CLIENTDETAILS, AddAppEntryClientDetailsCommand);	
			addCommand(EVENT_SHOW_CLIENTROLE_INFORMATION, ShowClientRoleInformation);		
			
			addCommand(EVENT_REMOVE_USED_CANDIDATECLIENTROLES, RemoveInsuredFromSelectedRolesCommand);
			addCommand(EVENT_SELECT_CANDIDATECLIENTROLES, SelectCandidateClientRolesCommand);
			addCommand(EVENT_DESELECT_CANDIDATECLIENTROLES, DeselectCandidateClientRoles);	
			addCommand(EVENT_GET_ALL_BATCHCONTRACTSETUPS, GetAllBatchContractSetupsCommand);
			addCommand(EVENT_GET_CANDIDATE_AGENTHIERARCHIES, GetCandidateAgentHierarchiesCommand);	
			
			addCommand(EVENT_LOAD_CODETABLEDOCUMENT, LoadCodeTableDocumentCommand);
			addCommand(EVENT_ADD_BATCHPROGRESSLOG_ENTRY, AddBatchProgressLogEntryCommand);
			addCommand(EVENT_UPDATE_BATCH_TRANSMITTED_DETAILS, UpdateBatchTransmittedDetailsCommand);
			addCommand(EVENT_GET_BATCHPROGRESSLOGS, GetBatchProgressLogsCommand);
			
			addCommand(EVENT_ADD_BATCHPRODUCTLOG_ENTRY, AddBatchProductLogEntryCommand);
			addCommand(EVENT_GET_CANDIDATE_PRODUCTSTRUCTURES, GetCandidateProductStructuresCommand);
			addCommand(EVENT_GET_BATCHPRODUCTLOGS, GetBatchProductLogsCommand);
			addCommand(EVENT_CLEAR_BATCHCONTRACTSETUPMODELS, ClearBatchContractSetupModelsCommand);
			
			addCommand(EVENT_INSTANTIATE_VALUEOBJECTVO, InstantiateValueObjectVOCommand);
			//addCommand(EVENT_BEGIN_APP_ENTRY, BeginAppEntryCommand);
			//addCommand(EVENT_CLEAR_APPENTRY, ClearAppEntryCommand);
			//addCommand(EVENT_SAVE_APPENTRY, SaveAppEntryCommand);
			addCommand(EVENT_APPENTRY, AppEntryCommand);
			addCommand(EVENT_LISTRATEDGENDER, GetRatedGenderFromCaseProductUnderwritingCommand);
			
		//	addCommand(EVENT_GET_DEDUCTION_FREQUENCY_DESC, AppEntryCommand);
			addCommand(EVENT_GET_SELECTED_AGENTHIERARCHIES, GetSelectedAgentHierarchiesCommand);
			//addCommand(EVENT_REMOVE_INSURED_FROM_SELECTED_ROLES, RemoveInsuredFromSelectedRolesCommand);
			addCommand(EVENT_SAVE_QUICK_ADD_CLIENT, SaveQuickAddClientCommand); 
			addCommand(EVENT_GET_PAYROLLDEDUCTIONCALENDARS_BY_YEAR_AND_PAYROLLDEDUCTIONSCHEDULEPK, GetPayrollDeductionCalendarsByYearAndPayrollDeductionSchedulePKCommand);
			
			addCommand(EVENT_GET_POLICY_INFORMATION_BY_CLIENTDETAILPK, GetPolicyInformationByClientDetailPKCommand);	
			addCommand(EVENT_GET_CANDIDATE_ENROLLMENTS, GetCandidateEnrollmentsCommand);
			addCommand(EVENT_SELECT_CANDIDATERIDERS, SelectCandidateRidersCommand);
			
			addCommand(EVENT_DESELECT_CANDIDATERIDERS, DeselectCandidateRidersCommand);
			addCommand(EVENT_GET_QUOTE, GetQuoteCommand);
			addCommand(EVENT_UPDATE_BILLGROUP, UpdateBillGroupCommand);
			addCommand(EVENT_GET_FILTERED_QUESTIONNAIRES, GetFilteredQuestionnairesCommand);
			
			addCommand(EVENT_BEGIN_QUICK_ADD_CLIENT, BeginQuickAddClientCommand);
			addCommand(EVENT_GET_PAYROLLDEDUCTIONCALENDARS, GetPayrollDeductionCalendarsCommand);
			addCommand(EVENT_UDPATE_DELETE_PAYROLLDEDUCTIONCALENDARS, UpdateDeletePayrollDeductionCalendars);
			addCommand(EVENT_CREATE_PAYROLLDEDUCTIONCALENDAR, CreatePayrollDeductionCalendar);
			
			addCommand(EVENT_IMPORT_NEW_BUSINESS_BATCH, ImportNewBusinessBatchCommand);
			addCommand(EVENT_GET_BILLS_FOR_CONTRACT_IN_BILLGROUP, GetBillsForContractInBillGroupCommand);
			addCommand(EVENT_GET_CASE_FILTEREDREQUIREMENTS, GetCaseFilteredRequirementsCommand);
			addCommand(EVENT_GET_ALL_CONVERSION_TEMPLATES, GetAllConversionTemplatesCommand);
			
			addCommand(EVENT_CREATE_CONVERSION_TEMPLATE, CreateConversionTemplateCommand);
			addCommand(EVENT_DELETE_CONVERSION_TEMPLATE, DeleteConversionTemplateCommand);
			addCommand(EVENT_UPDATE_CONVERSION_TEMPLATE, UpdateConversionTemplateCommand);
			addCommand(EVENT_VALIDATE_CONVERSION_TEMPLATE, ValidateConversionTemplateCommand);
			
			addCommand(EVENT_LOAD_SAMPLE_CONVERSION_DATA, LoadSampleConversionDataCommand);
			addCommand(EVENT_TEST_CONVERSION_TEMPLATE, TestConversionTemplateCommand);
			addCommand(EVENT_LOAD_CONVERSION_DATA, LoadConversionDataCommand);

			addCommand(EVENT_GET_CONVERSION_JOBS, GetConversionJobsCommand);
			addCommand(EVENT_GET_CONVERSION_LOGS, GetConversionLogsCommand);
			addCommand(EVENT_RUN_CONVERSION, RunConversionCommand);			
			addCommand(EVENT_FILTER_CODETABLES, GetFilteredCodeTablesCommand);
			
			addCommand(EVENT_SPLIT_BENEFICIARIES_EQUALLY, SplitBeneficiariesEquallyCommand);
			addCommand(EVENT_GET_ALL_PRASEDOCUMENTS, GetAllPRASEDocumentsCommand);
			addCommand(EVENT_UPDATE_PRASE_DOCUMENT, UpdatePRASEDocumentCommand);
			addCommand(EVENT_CLONE_PRASEDOCUMENT, ClonePRASEDocumentCommand);
			
			addCommand(EVENT_GET_ASSOCIATED_PRASETESTS, GetAssociatedPraseTestsCommand);
			addCommand(EVENT_GET_ALL_PRASETESTS, GetAllPRASETestsCommand);
			addCommand(EVENT_GET_ALL_PRODUCTSTRUCTURES, GetAllProductStructuresCommand);
			addCommand(EVENT_GET_CANDIDATE_PRASEDOCUMENTS, GetCandidatePRASEDocumentsCommand);
			
			addCommand(EVENT_GET_SELECTED_PRASEDOCUMENTS, GetSelectedPRASEDocumentsCommand);
			addCommand(EVENT_GET_BATCHCONTRACTSETUPS_BY_PARTIALCONTRACTGROUPNUMBER, GetBatchContractSetupsByPartialContractGroupNumberCommand);
			addCommand(EVENT_UPDATE_PRASETEST, UpdatePRASETestCommand);
			addCommand(EVENT_SELECT_CANDIDATEPRASEDOCUMENTS, SelectCandidatePRASEDocumentsCommand);
			
			addCommand(EVENT_DESELECT_CANDIDATEPRASEDOCUMENTS, DeselectCandidatePRASEDocumentsCommand);
			addCommand(EVENT_CREATE_PRASETEST, CreatePRASETestCommand);
			addCommand(EVENT_EDIT_ASSOCIATED_PRASETEST, EditAssociatedPRASETestCommand);
			addCommand(EVENT_EDIT_PRASEDOCUMENT, EditPRASEDocumentCommand);
			
			addCommand(EVENT_MARK_ROOT_PRASEDOCUMENT, MarkRootPRASEDocumentCommand);
			addCommand(EVENT_RUN_PRASETEST, RunPRASETestCommand);
			addCommand(EVENT_SET_ACTUAL_AS_EXPECTED, SetActualAsExpectedCommand);
			addCommand(EVENT_GET_ALL_TEST_FILES, GetAllTestFilesCommand);
			
			addCommand(EVENT_GET_SCRIPT_PROCESSOR_RESULTS, GetScriptProcessorResults);
			addCommand(EVENT_EXPORT_RUN_DATA, ExportRunData);
			addCommand(EVENT_CLEAR_RECORDED_OPERATOR_DATA, ClearRecordedOperatorData);
			
			addCommand(EVENT_GET_GIO_OPTIONS, GetGIOOptionsCommand);
			addCommand(EVENT_SAVE_QUERY, SaveQueryCommand);
			addCommand(EVENT_EXECUTE_QUERY_STATEMENT, ExecuteQueryStatementCommand);
			
			addCommand(EVENT_DELETE_BATCHCONTRACTSETUP, DeleteBatchContractSetupCommand); 
			addCommand(EVENT_COPY_BATCHCONTRACTSETUP, CopyBatchContractSetupCommand);
		}
	}
}
