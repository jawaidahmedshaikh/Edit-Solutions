package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.model.SEGModelLocator;
	import mx.collections.XMLListCollection;
	import com.segsoftware.utility.Util;
	import com.segsoftware.model.BatchContractSetupHelper;
	import mx.rpc.events.*;
	import com.segsoftware.model.SEGModelBuilder;
	import com.segsoftware.model.logging.*;
	
	/**
	 * Gathers the information to send to the backend to update the BatchContractSetup and
	 * its associations. The information to be sent involves:
	 * 
	 * 1. The BatchContractSetupVO
	 * 2. The BatchProgressLogVOs
	 * 3. The BatchProductLogVOs
	 * 4. The list of AgentHierarchyPKs that are used as a subset of the candidate AgentHierarchies.
	 */ 
	public class UpdateBatchContractSetupCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Update BatchContractSetup";
		
		override public function executeCommand(event:SEGEvent):void
		{			
			// Build the parameters
			var parameters:XMLListCollection = buildParameters();
			
			new GroupDelegate(result, fault, parameters).updateBatchContractSetup();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var responseMessageVOs:XMLListCollection = new XMLListCollection(resultEvent.result.ResponseMessageVO);
			
			if (Util.isSuccess(responseMessageVOs))
			{
				// With a successful commit, set the state back to its default state.
				BatchContractSetupHelper.clearBatchContractSetup();
			
				SEGModelLocator.getInstance().batchContractSetupVOs.removeAll();
			
				SEGModelLocator.getInstance().selectedBatchContractSetupVO = null;
			}
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}
		
		private function buildParameters():XMLListCollection
		{
			// 1. The BatchContractSetupVO
			var selectedBatchContractSetupVOCopy:XML = SEGModelLocator.getInstance().selectedBatchContractSetupVO.copy();
			
			delete selectedBatchContractSetupVOCopy.ContractGroupVO; // remove this VO since it doesn't need to be sent
			
			// 2. The BatchProgressLogVOs.
			var batchProgressLogVOsCopy:XMLListCollection = new XMLListCollection(SEGModelLocator.getInstance().batchProgressLogVOs.copy());
			
			// 3. The BatchProductLogVOs
			var bachProductLogVOsCopy:XMLListCollection = new XMLListCollection(SEGModelLocator.getInstance().batchProductLogVOs.copy());
			
			// 4. The AgentHierarchy PKs
			var agentHierarchyPKsCopy:XMLListCollection = new XMLListCollection(getAgentHierarchyPKs().copy());
			
			// Now populate the paramters collection placing all target XML elements at the same level.
			var parameters:XMLListCollection = new XMLListCollection();
			
			parameters.addItem(selectedBatchContractSetupVOCopy);
			
			for each (var batchProgressLogVO:XML in batchProgressLogVOsCopy)
			{
				parameters.addItem(batchProgressLogVO);
			}
						
			for each (var batchProductLogVO:XML in bachProductLogVOsCopy)
			{
				parameters.addItem(batchProductLogVO);
			}
			
			for each (var agentHierarchyPK:XML in agentHierarchyPKsCopy)
			{
				parameters.addItem(agentHierarchyPK);
			}
			
			return parameters;	
		}
		
		/**
		 * A convenience method to create a list of the AgentHierachyPKs that will be (ultimately) mapped
		 * to the SelectedAgentHierarchy table. The format is that of:
		 * <AgentHierarchyPK>fooPK</AgentHierarchyPK> // repeated
		 */ 
		private function getAgentHierarchyPKs():XMLListCollection
		{
			var selectedCandidateAgentHierarchyVOs:XMLListCollection = SEGModelLocator.getInstance().selectedCandidateAgentHierarchyVOs;
			
			var selectedCandidateAgentHierarchyPKs:XMLListCollection = new XMLListCollection();
			
			for each (var selectedCandidateAgentHierarchyVO:XML in selectedCandidateAgentHierarchyVOs)
			{
				var agentHierarchyFK:String = selectedCandidateAgentHierarchyVO.AgentHierarchyFK[0];
				
				var agentHierarchyFKXML:XML = Util.buildXMLElement("AgentHierarchyPK", agentHierarchyFK);
				
				selectedCandidateAgentHierarchyPKs.addItem(agentHierarchyFKXML);
			}					
			
			return selectedCandidateAgentHierarchyPKs;
		}
		
		/**
		 * Certains values coming from BatchContractSetup are defaulted to BatchSegmentSetup [but]
		 * then can be overridden at the Segment level.
         *
  		 * Those values are defaulted to the BatchSegmentSetupVO with the understanding that they 
		 * may be overridden there. These values are:
		 * 
		 * 1. The selected BatchSegmentVO.
		 * 2. The selected AgentHierarchyVos of the selected BatchSegmentVO.
		 */ 
		private function defaultBatchSegmentVOValues():void
		{
			/*
			// The current state of the BatchContractSegmentVO will serve as our starting point.
			var selectedBatchContractSetupVO:XML = SEGModelLocator.getInstance().selectedBatchContractSetupVO.BatchContractSetupVO[0];
			
			// Reset this in case it has been previously modified.
			var selectedBatchSegmentVO:XML = <SEGEntryVO/>;
			
			var copyOfBatchContractSetupVO:Object = selectedBatchContractSetupVO.copy();
			
			// Add the copy of BatchContractSetupVO to the BatchSegmentVO
			selectedBatchSegmentVO.appendChild(copyOfBatchContractSetupVO);
			
			SEGModelLocator.getInstance().selectedBatchSegmentSetupVO = selectedBatchSegmentVO;	
			*/
		}
	}
}