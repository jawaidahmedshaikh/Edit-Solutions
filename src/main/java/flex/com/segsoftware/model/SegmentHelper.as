package com.segsoftware.model
{
	import com.segsoftware.model.encoding.ContractClient;
	import com.segsoftware.utility.Util;
	
	import mx.collections.ArrayCollection;
	import mx.collections.XMLListCollection;
	
	public class SegmentHelper
	{
		/**
		 * It may be necessary to "clear" a SegmentInformationVO - this
		 * is an empty XML element.
		 */ 
		public static var EMPTY_SEGMENTINFORMATIONVO:XML = <SegmentInformationVO/>;
		
		/**
		 * Clears the current AppEntry information (not BatchContractSetup info).
		 * This involves:
		 * 
		 * 1. Clearing the segmentInformationVO
		 * 2. Clearing the appEntryClientDetailVOs
		 * 3. Clearing the selectedAppEntryClientDetailVO
		 * 4. Clearing the candidateClientRoleUIVOs
		 * 5. Clearing the selectedClientRoleUIVOs
		 * 6. TODO Riders?
		 */ 
		public static function clearAppEntry():void
		{
			SEGModelLocator.getInstance().segmentInformationVO = EMPTY_SEGMENTINFORMATIONVO;
			
			SEGModelLocator.getInstance().appEntryClientDetailUIVOs.removeAll();
			
			SEGModelLocator.getInstance().selectedAppEntryClientDetailVO = null;
			
			SEGModelLocator.getInstance().candidateClientRoleUIVOs.removeAll();
			
			SEGModelLocator.getInstance().selectedClientRoleUIVOs.removeAll();	
			
			SEGModelLocator.getInstance().candidateCandidateRiderVOs.removeAll();
			
			SEGModelLocator.getInstance().clientSegments.removeAll();
			
			SEGModelLocator.getInstance().selectedClientRolesHolder.splice(0);
			
			clearBeneficiariesEquallySplit();
			
			//	Force a refresh on the clientSegments because the display won't refresh if the RiderClientDialog 
			//  has been brought up to set one of these clientSegment.  It's a stupid bug in Flex 2 only and this 
			//  is the only way we can find to get around the problem.
			SEGModelLocator.getInstance().clientSegments.refresh();	
		}
		
		/**
		 * Clears the contractNumber, faceAmount, and estateOfTheInsured fields from the 
		 * SegmentInformationVO.   Since clearing these fields will not force an update to 
		 * the screen, we must clear the entire SegmentInformationVO.  The rest of the fields 
		 * are preserved and copied back
		 */
		public static function clearNonDefaultSegmentInformation():void
		{
			//	Preserve existing segmentInformationVO
			var segmentInformationVO:XML = SEGModelLocator.getInstance().segmentInformationVO;
			
			//	Clear out contents
			SEGModelLocator.getInstance().segmentInformationVO = EMPTY_SEGMENTINFORMATIONVO;
			
			var newSegmentInformationVO:XML = <SegmentInformationVO/>;
			
			//	Set fields to the preserved values
			newSegmentInformationVO.FilteredProductPK = segmentInformationVO.FilteredProductPK;
			newSegmentInformationVO.DepartmentLocationPK = segmentInformationVO.DepartmentLocationPK;
			newSegmentInformationVO.IssueStateCT = segmentInformationVO.IssueStateCT;
			newSegmentInformationVO.IssueStateORInd = segmentInformationVO.IssueStateORInd;
			newSegmentInformationVO.EffectiveDate = segmentInformationVO.EffectiveDate;
			newSegmentInformationVO.ApplicationSignedDate = segmentInformationVO.ApplicationSignedDate;
			newSegmentInformationVO.ApplicationReceivedDate = segmentInformationVO.ApplicationReceivedDate;
			newSegmentInformationVO.ApplicationSignedStateCT = segmentInformationVO.ApplicationSignedStateCT;
			newSegmentInformationVO.RatedGenderCT = segmentInformationVO.RatedGenderCT;
			newSegmentInformationVO.UnderwritingClassCT = segmentInformationVO.UnderwritingClassCT;
			newSegmentInformationVO.GroupPlan = segmentInformationVO.GroupPlan;
			newSegmentInformationVO.DeathBenefitOptionCT = segmentInformationVO.DeathBenefitOptionCT;
			newSegmentInformationVO.NonForfeitureOptionCT = segmentInformationVO.NonForfeitureOptionCT;
			newSegmentInformationVO.ExchangeInd = segmentInformationVO.ExchangeInd;
			newSegmentInformationVO.ContractNumber = "";
			newSegmentInformationVO.FaceAmount = "";
			newSegmentInformationVO.EstateOfTheInsured = "N";
			newSegmentInformationVO.DeductionAmountOverride = "";
			newSegmentInformationVO.DeductionAmountEffectiveDate = "";
			
			SEGModelLocator.getInstance().segmentInformationVO = newSegmentInformationVO;
		}
		
		/**
		 * Clears the CheckBox for beneficiaries equally split.  
		 */ 
		public static function clearBeneficiariesEquallySplit():void
		{
			SEGModelLocator.getInstance().splitEquallyCheckBox.selected = false;
		}
		
		/**
		 * To save a new AppEntry, or generate a quote, there is an involved process to build
		 * the Segment, Client, and Rider information.
		 */  
		public static function generateAppEntryRequest(beneficiariesEquallySplit:Boolean = false, generateTestData:Boolean = false):XMLListCollection
		{
			var parameters:XMLListCollection = new XMLListCollection();
			
			// Split the Beneficiaries?
			parameters.addItem(Util.buildXMLElement("BeneficiariesEquallySplit",beneficiariesEquallySplit.toString()));
			
			// Split the Beneficiaries?
			parameters.addItem(Util.buildXMLElement("GeneratePRASETest",generateTestData.toString()));									 
			
			// SegmentInformation
			var segmentInformationVO:XML = SEGModelLocator.getInstance().segmentInformationVO;
			
			segmentInformationVO.IssueStateORInd = "N";
			
			if (segmentInformationVO.IssueStateCT != "")
			{
				segmentInformationVO.IssueStateORInd = "Y";	
			}
			
			parameters.addItem(segmentInformationVO);
			
			// BatchContractSetupPK
			var batchContractSetupPK:XML = SEGModelLocator.getInstance().selectedBatchContractSetupVO.BatchContractSetupPK[0];
			
			parameters.addItem(batchContractSetupPK);
			
			// Operator
			var operator:XML = <Operator>{SEGModelLocator.getInstance().operator}</Operator>;
			
			parameters.addItem(operator);
			
			//	The clients for the base Segment are placed at the same level as the Segment
			//	The clients for the rider Segments are within the riders.  The SEGModelLocator's appEntryClientDetailUIOVOs
			//	contains ALL of the clients regardless of which segment they belong to.  The
			//	SEGModelLocator's clientSegments contain the clients that belong to a particular rider.
			//	To determine which clients belong to the base segment, the riders are built first.  If
			//	the rider's client is found in the clientSegments, it is removed from a temporary collection
			//	of the ContractClients.
			
			//	Put all of the appEntryClientDetailUIVOs and their roles into a temporary collection
			//  of ContractClients
			var baseContractClients:ArrayCollection = SegmentHelper.makeBaseContractClientCollection();
			
			// Riders - first add the ClientInformation to the rider, then add rider to the parameters
			for each (var clientSegment:ClientSegment in SEGModelLocator.getInstance().clientSegments)
			{
				var newCandidateRiderVO:XML = SegmentHelper.buildRider(clientSegment, baseContractClients);
				
				parameters.addItem(newCandidateRiderVO);
			}
			
			//	ClientInformation(s) for the Base Segment
			for each (var baseContractClient:ContractClient in baseContractClients)
			{
				var clientInformationVO:XML = SEGModelBuilder.getInstance().buildClientInformationVO(baseContractClient.appEntryClientDetailUIVO, baseContractClient.clientRoleUIVO);
				
				parameters.addItem(clientInformationVO);
			}	
			
			return parameters;		
		}
		
		/**
		 * Builds a rider by making a copy of the existing rider and optionally adding a 
		 * gio option or a client to it.  The ClientInformation is only added if a ClientSegment.ContractClient exists for 
		 * this rider and the rider's ContractClientAllowed tag specifies that clients are allowed on this rider.
		 * The GIOOption is only added if the rider's GIOSelectionAllowed tag specifies that gio options can be set 
		 * on this rider.
		 * 
		 * @param baseContractClients             collection of ContractClients that belong
		 *                                        to the base segment.  If the rider has a client, that
		 *                                        client is removed from this collection so it won't be
		 *                                        duplicated in the base segment. 
		 */
		private static function buildRider(clientSegment:ClientSegment, baseContractClients:ArrayCollection):XML
		{
			//	Make a copy of the "real" rider (candidateRiderVO) so we don't mistakenly add children to it.
			var newCandidateRiderVO:XML = clientSegment.candidateRiderVO.copy();
				
			if (clientSegment.candidateRiderVO.GIOSelectionAllowed == "Y")
			{
				newCandidateRiderVO.GIOOption = clientSegment.gioOption;
			}
			
			if (clientSegment.candidateRiderVO.ContractClientAllowed == "Y")
			{
				if (clientSegment.contractClientExists())
				{
					//	Add the client for the rider to the copy of the riderVO
					var appEntryClientDetailUIVO:XML = clientSegment.contractClient.appEntryClientDetailUIVO;
					var clientRoleUIVO:XML = clientSegment.contractClient.clientRoleUIVO;
				
					var clientInformationVO:XML = SEGModelBuilder.getInstance().buildClientInformationVO(appEntryClientDetailUIVO, clientRoleUIVO);
				
					newCandidateRiderVO.appendChild(clientInformationVO);
					
					//	Remove the client from the list of base clients 
					removeClientFromBaseList(baseContractClients, clientSegment.contractClient);
				}
			}	
			
			return newCandidateRiderVO;
		}

		/**
		 * Removes a client from the list of base clients if it matches the specified ContractClient.
		 */ 		
		private static function removeClientFromBaseList(baseContractClients:ArrayCollection, riderContractClient:ContractClient):void
		{
			var index:int = -1;
			
			for (var i:int; i < baseContractClients.length; i++)
			{
				var baseContractClient:ContractClient = baseContractClients.getItemAt(i) as ContractClient;
				
				if (riderContractClient.appEntryClientDetailUIVO == baseContractClient.appEntryClientDetailUIVO &&
				    riderContractClient.clientRoleUIVO == baseContractClient.clientRoleUIVO)
				{
					index = i;
					
					break;		    	
			   	}	
			}
			
			if (index > -1)
			{
				baseContractClients.removeItemAt(index);
			}
		}
		
		/**
		 * Builds a temporary collection of ContractClient objects.  This collection is used
		 * to determine which clients are built on the base Segment.  By the time it leaves
		 * this method, it contains all of the appEntryClientDetailUIVOs on SEGModelLocator 
		 * but with one clientRoleUIVO for each ContractClient.  It will be manipulated when 
		 * building the riders.
		 * 
		 * If the rider being built has a client associated with it, that client is 
		 * removed from this temporary list (can't have a contract client belonging to
		 * more than one segment)
		 */
		private static function makeBaseContractClientCollection():ArrayCollection
		{
			var baseContractClients:ArrayCollection = new ArrayCollection();

			var appEntryClientDetailUIVOs:XMLListCollection = SEGModelLocator.getInstance().appEntryClientDetailUIVOs;
			
			for each (var appEntryClientDetailUIVO:XML in appEntryClientDetailUIVOs)
			{
				var clientRoles:XMLListCollection = new XMLListCollection(appEntryClientDetailUIVO.SelectedClientRoleUIVOs[0].ClientRoleUIVO);
				
				for each (var clientRoleUIVO:XML in clientRoles)
				{
					var contractClient:ContractClient = new ContractClient(appEntryClientDetailUIVO, clientRoleUIVO);
					
					baseContractClients.addItem(contractClient);	
				}
			}
			
			return baseContractClients;
		}
	}
}