package com.segsoftware.command
{
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.event.*;
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.*;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.model.SegmentHelper;
	import com.segsoftware.model.logging.*;
	import com.segsoftware.utility.Util;
	
	import mx.collections.Sort;
	import mx.collections.SortField;
	import mx.collections.XMLListCollection;
	import mx.rpc.events.*;
	
	/**
	 * While making app entries, the user may wish to clear all current
	 * selections while allowing default information to still be made available.
	 * 
	 */ 
	public class AppEntryCommand extends SEGCommand
	{
		/**
		 * The user wants to save the AppEntry and retain all 
		 * client information EXCEPT for the insured.
		 */ 
		public static var COMMAND_MODE_SAVE_NEXT_NEW_INSURED:int = 0;
		
		/**
		 * The user saves the AppEntry and clears everything on 
		 * the AppEntry page readying for the next in the current BatchContractSetup.
		 */ 
		public static var COMMAND_MODE_SAVE_NEXT:int = 1; 
		
		public static var COMMAND_MODE_SKIP:int = 2;
		
		public static var COMMAND_MODE_QUOTE:int = 3;
		
		public static var COMMAND_MODE_SET_DEFAULTS:int = 4;
		
		private var commandMode:int;
		

		
	    override public function executeCommand(event:SEGEvent):void
		{
			commandMode = event.formData.commandMode as int;
			
			if (commandMode == AppEntryCommand.COMMAND_MODE_SAVE_NEXT_NEW_INSURED)
			{
				executeSaveNewInsured(event);
			}
			else if (commandMode == AppEntryCommand.COMMAND_MODE_SAVE_NEXT)
			{
				executeSaveNext(event);
			}
			else if (commandMode == AppEntryCommand.COMMAND_MODE_SKIP)
			{
				executeSkip(event);
			}
			else if (commandMode == AppEntryCommand.COMMAND_MODE_QUOTE)
			{
				executeGetQuote(event);
			}
			else if (commandMode == AppEntryCommand.COMMAND_MODE_SET_DEFAULTS)
			{
				setAppEntryDefaults();
			}
		}
		
		//////////////// Executes /////////////////////////////
		
		private function executeSaveNewInsured(event:SEGEvent):void
		{
			SEGLog.getInstance().clearBusinessMessage();
			
			var beneficiariesEquallySplit:Boolean = event.formData.BeneficiariesEquallySplit;
			
			var generateTestData:Boolean = event.formData.GeneratePRASETest;					

			var parameters:XMLListCollection = SegmentHelper.generateAppEntryRequest(beneficiariesEquallySplit, generateTestData);
		
			new GroupDelegate(resultSaveNewInsured, fault, parameters).addSegmentToContractGroup();
			
		}
		
		private function removeInsuredRoleFromHolder():void 
		{ 
			var index:int = SEGModelLocator.getInstance().selectedClientRolesHolder.indexOf("Insured");
			if (index >= 0) {
				SEGModelLocator.getInstance().selectedClientRolesHolder.splice(index, 1);
			}
		}
		
		private function executeSaveNext(event:SEGEvent):void
		{
			SEGLog.getInstance().clearBusinessMessage();
			
			var beneficiariesEquallySplit:Boolean = event.formData.BeneficiariesEquallySplit;
			
			var generateTestData:Boolean = event.formData.GeneratePRASETest;					
			
			var parameters:XMLListCollection = SegmentHelper.generateAppEntryRequest(beneficiariesEquallySplit, generateTestData);
		
			new GroupDelegate(resultSaveNext, fault, parameters).addSegmentToContractGroup();
		}
		
		private function executeSkip(event:SEGEvent):void
		{
			SegmentHelper.clearAppEntry();
			
			setAppEntryDefaults();
		}
		
		private function executeGetQuote(event:SEGEvent):void
		{
			var generateTestData:Boolean = event.formData.GeneratePRASETest;
				
			var parameters:XMLListCollection = SegmentHelper.generateAppEntryRequest(false, generateTestData);
			
			new GroupDelegate(resultGetQuote, fault, parameters).getQuote();
		}
		
		private function executeGetDepartmentLocations():void
		{
			var batchContractSetupPK:XML = SEGModelLocator.getInstance().selectedBatchContractSetupVO.BatchContractSetupPK[0];
			
			new GroupDelegate(resultGetDepartmentLocations, fault, Util.convertToXMLListCollection(batchContractSetupPK)).getActiveDepartmentLocations();	
		}

		private function executeGetDeductionFrequencyDesc():void
		{
			var batchContractSetupPK:XML = SEGModelLocator.getInstance().selectedBatchContractSetupVO.BatchContractSetupPK[0];
			
			new GroupDelegate(resultGetDeductionFrequencyDesc, fault, Util.convertToXMLListCollection(batchContractSetupPK)).getDeductionFrequencyDesc();	
		}
		
		
		private function executeGetCandidateRiders():void
		{
			var selectedBatchContractSetupVO:XML = SEGModelLocator.getInstance().selectedBatchContractSetupVO;
			
			var batchContractSetupPK:XML = selectedBatchContractSetupVO.BatchContractSetupPK[0];
			
			new GroupDelegate(resultGetCandidateRiders, fault, Util.convertToXMLListCollection(batchContractSetupPK)).getCandidateRiders();				
		}
		
		
		/////////////////// Results //////////////////////////////////
		
		public function resultSaveNewInsured(resultEvent:ResultEvent, token:Object=null):void
		{
			var responseMessageVOs:XMLListCollection = new XMLListCollection(resultEvent.result.ResponseMessageVO);
			
			if (Util.isNotError(responseMessageVOs))
			{
				logBusinessMessage(responseMessageVOs);
				
				SegmentHelper.clearNonDefaultSegmentInformation();
				
				clearAllSelectedRolesExceptOwnerAndPayor();
				
				SegmentHelper.clearBeneficiariesEquallySplit();
				
				executeGetCandidateRiders();
   			    removeInsuredRoleFromHolder();
			}
			
			super.result(resultEvent, "Save & New Insured");
		}
		
		public function resultSaveNext(resultEvent:ResultEvent, token:Object=null):void
		{
			var responseMessageVOs:XMLListCollection = new XMLListCollection(resultEvent.result.ResponseMessageVO);
			
			if (Util.isNotError(responseMessageVOs))
			{
				logBusinessMessage(responseMessageVOs);
				
				SegmentHelper.clearAppEntry();	
				
				setAppEntryDefaults();				
			}
			
			super.result(resultEvent, "Save & Next");
		}
		
		public function resultGetQuote(resultEvent:ResultEvent, token:Object=null):void
		{
			var responseMessageVOs:XMLListCollection = new XMLListCollection(resultEvent.result.ResponseMessageVO);
			
			var annualPremium:String = resultEvent.result.AnnualPremium[0];
			
			var deductionAmount:String = resultEvent.result.DeductionAmount[0];
			
			var quoteVO:XML = SEGModelBuilder.getInstance().buildQuoteVO(annualPremium, deductionAmount);
			
			SEGModelLocator.getInstance().quoteVO = quoteVO;
			
			super.result(resultEvent, "Get Quote");
		}

		public function resultGetDeductionFrequencyDesc(resultEvent:ResultEvent, token:Object=null):void
		{
			var deductionFrequencyDesc:String = resultEvent.result.DeductionFrequencyDesc[0];
			SEGModelLocator.getInstance().deductionFrequencyDesc = deductionFrequencyDesc;
		}

		public function resultGetDepartmentLocations(resultEvent:ResultEvent, token:Object=null):void
		{
			// Clear the existing ones first.
			SEGModelLocator.getInstance().departmentLocationVOs.removeAll();
			
			var departmentLocationVOsList:XMLList = resultEvent.result.DepartmentLocationVO;
			
			SEGModelLocator.getInstance().departmentLocationVOs = new XMLListCollection(departmentLocationVOsList);
			var nameSort:Sort = new Sort();
			nameSort.fields = [new SortField('DeptLocCode', true)];
			SEGModelLocator.getInstance().departmentLocationVOs.sort = nameSort;
			SEGModelLocator.getInstance().departmentLocationVOs.refresh();
			
			
		}
		
		
		public function resultGetCandidateRiders(resultEvent:ResultEvent, token:Object=null):void
		{
			SEGModelLocator.getInstance().candidateCandidateRiderVOs.removeAll();
			
			SEGModelLocator.getInstance().clientSegments.removeAll();
			
			//	Force a refresh on the clientSegments because the display won't refresh if the RiderClientDialog 
			//  has been brought up to set one of these clientSegment.  It's a stupid bug in Flex 2 only and this 
			//  is the only way we can find to get around the problem.
			SEGModelLocator.getInstance().clientSegments.refresh();
			
			var candidateRiderVOs:XMLList = resultEvent.result.CandidateRiderVO;
			
			for each (var candidateRiderVO:XML in candidateRiderVOs)
			{
				CandidateRiderHelper.sortByRequiredOptionalCT(candidateRiderVO);
			}
			
			Util.sort(SEGModelLocator.getInstance().candidateCandidateRiderVOs, "Coverage");
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, "AppEntryCommand Event");
			
			trace(faultEvent.message);	
		}
		
		/**
		 * After committing an App in AppEntry, the user may wish to keep ALL of the existing
		 * information EXCEPT for the insured. This removes the insured from the list
		 * of selected Roles.
		 */  
		 /*
		private function removeInsuredFromSelectedRoles():void
		{
			var selectedClientRoleUIVOs:XMLListCollection = SEGModelLocator.getInstance().selectedClientRoleUIVOs;
			
			for each (var selectedClientRoleUIVO:XML in selectedClientRoleUIVOs)
			{
				var roleTypeCT:String = selectedClientRoleUIVO.RoleTypeCode;
				
				if (ClientRoleHelper.isInsured(roleTypeCT))
				{
					var indexOfSelectedClientRoleUIVO:int = selectedClientRoleUIVOs.getItemIndex(selectedClientRoleUIVO);
					
					selectedClientRoleUIVOs.removeItemAt(indexOfSelectedClientRoleUIVO);
					
					//Reset the state - the user may have modified it.
					ClientRoleHelper.resetCodeValues(selectedClientRoleUIVO);

					// And now add it back to the source collection.					
					SEGModelLocator.getInstance().candidateClientRoleUIVOs.addItem(selectedClientRoleUIVO);
				}
			}	
		}
		*/
		
		/**
		 * Clears all of the selected roles for all of the clients except the roles of Owner and Payor.
		 */
		private function clearAllSelectedRolesExceptOwnerAndPayor():void
		{
			//	Clear the roles for each client in the model
			var appEntryClientDetailVOs:XMLListCollection = SEGModelLocator.getInstance().appEntryClientDetailUIVOs;
			
			for each (var appEntryClientDetailVO:XML in appEntryClientDetailVOs)
			{
				var selectedClientRoleUIVOs:XMLList = appEntryClientDetailVO.SelectedClientRoleUIVOs.ClientRoleUIVO;
			
				var selectedClientRoleUIVOsCollection:XMLListCollection = new XMLListCollection(selectedClientRoleUIVOs);
				
				var candidateClientRoleUIVOs:XMLList = appEntryClientDetailVO.CandidateClientRoleUIVOs.ClientRoleUIVO;
			
				var candidateClientRoleUIVOsCollection:XMLListCollection = new XMLListCollection(candidateClientRoleUIVOs);
				
				removeAllRolesExceptOwnerAndPayor(selectedClientRoleUIVOsCollection, candidateClientRoleUIVOsCollection, appEntryClientDetailVO);
			}
			
			//	Now clear the selectedClientRoleUIVOs that are displayed on the screen
			//	(for the currently selected client)
			removeAllRolesExceptOwnerAndPayor(SEGModelLocator.getInstance().selectedClientRoleUIVOs, SEGModelLocator.getInstance().candidateClientRoleUIVOs,
											  SEGModelLocator.getInstance().selectedAppEntryClientDetailVO);				
		}
		
		/**
		 * Loops through the collection of clientRoleUIVOs and removes them from the collection
		 * if the roleType is not Owner or Payor
		 */ 
		private function removeAllRolesExceptOwnerAndPayor(selectedClientRoleUIVOsCollection:XMLListCollection, candidateClientRoleUIVOsCollection:XMLListCollection, appEntryClientDetailVO:XML):void
		{
			var length:int = selectedClientRoleUIVOsCollection.length;
		
			// 	Loop through backwards to avoid messing up the indexes when removing collection items
			for (var i:int = length-1; i >= 0; i--)
			{
				var selectedClientRoleUIVO:XML = selectedClientRoleUIVOsCollection[i];
								
				var roleTypeCT:String = selectedClientRoleUIVO.RoleTypeCode;
				
				if ( !(ClientRoleHelper.isOwner(roleTypeCT)) && !(ClientRoleHelper.isPayor(roleTypeCT)) )
				{
					//	It is anything other than the owner or payor, remove it from the selected list
					selectedClientRoleUIVOsCollection.removeItemAt(i);
					
					//Reset the state - the user may have modified it.
					ClientRoleHelper.resetCodeValues(selectedClientRoleUIVO);
					
					// Now add it back to the candidate list					
					candidateClientRoleUIVOsCollection.addItem(selectedClientRoleUIVO);
				}
			}	
		}
		
		private function setAppEntryDefaults():void
		{
			var batchContractSetupPK:XML = SEGModelLocator.getInstance().selectedBatchContractSetupVO.BatchContractSetupPK[0];
			
			var filteredProductFK:String = SEGModelLocator.getInstance().selectedBatchContractSetupVO.FilteredProductFK;
			
			var deathBenefitOptionCT:String = SEGModelLocator.getInstance().selectedBatchContractSetupVO.DeathBenefitOptionCT;
			
			var nonForfeitureOptionCT:String = SEGModelLocator.getInstance().selectedBatchContractSetupVO.NonForfeitureOptionCT;		
			
			var effectiveDate:String = SEGModelLocator.getInstance().selectedBatchContractSetupVO.EffectiveDate;
			
			var applicationSignedDate:String = SEGModelLocator.getInstance().selectedBatchContractSetupVO.ApplicationSignedDate;	
			
			var applicationReceivedDate:String = SEGModelLocator.getInstance().selectedBatchContractSetupVO.ApplicationReceivedDate;
			
			var applicationSignedStateCT:String = SEGModelLocator.getInstance().selectedBatchContractSetupVO.ApplicationSignedStateCT;	
			
			var ratedGenderCT:String = SEGModelLocator.getInstance().selectedBatchContractSetupVO.RatedGenderCT;
			
			var underwritingClass:String = SEGModelLocator.getInstance().selectedBatchContractSetupVO.UnderwritingClassCT;
			
			var groupPlan:String = SEGModelLocator.getInstance().selectedBatchContractSetupVO.GroupPlan;
			
			var issueStateCT:String = SEGModelLocator.getInstance().selectedBatchContractSetupVO.IssueStateCT;	

			var deductionFrequencyDesc:String = SEGModelLocator.getInstance().selectedBatchContractSetupVO.deductionFrequencyDesc;	

			var scheduledPremium:String = SEGModelLocator.getInstance().selectedBatchContractSetupVO.scheduledPremium;	

			var exchangeInd:String = SEGModelLocator.getInstance().selectedBatchContractSetupVO.ExchangeInd;	
			var mecStatusCT:String = SEGModelLocator.getInstance().selectedBatchContractSetupVO.mecStatusCT;	

			
			// Build SegmentInformationVO
			var segmentInformationVO:XML = SEGModelBuilder.getInstance().buildSegmentInformationVO(filteredProductFK,
																									deathBenefitOptionCT,
																									nonForfeitureOptionCT,
																									effectiveDate,
																									applicationSignedDate,
																									applicationReceivedDate,
																									applicationSignedStateCT,
																									ratedGenderCT,
																									groupPlan,
																									underwritingClass,
																									issueStateCT,
																									exchangeInd,
																									scheduledPremium,
																									mecStatusCT
			);
			SEGModelLocator.getInstance().segmentInformationVO = segmentInformationVO;		
			
			executeGetDepartmentLocations();
			executeGetDeductionFrequencyDesc();
			
			executeGetCandidateRiders();
		}
		
		/**
		 * Finds the business message in the collection of responseMessages and logs
		 * it. For successful saves, the first message in the collection is the business
		 * message (specifies the name of the contract that was saved).
		 */
		private function logBusinessMessage(responseMessageVOs:XMLListCollection):void
		{
			var responseMessage:XML = responseMessageVOs.getItemAt(0) as XML;
			
			SEGLog.getInstance().logBusinessMessage(responseMessage.Message);	
		}
	}
}
