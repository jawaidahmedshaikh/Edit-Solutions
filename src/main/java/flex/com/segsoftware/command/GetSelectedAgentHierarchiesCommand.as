package com.segsoftware.command
{
	import com.segsoftware.model.SEGModelLocator;
	import mx.rpc.events.*;
	import com.segsoftware.utility.Util;
	import com.segsoftware.business.*;
	import com.segsoftware.event.SEGEvent;
	import mx.collections.XMLListCollection;
	
	
	/**
	 * For the currently selected BatchContractSetup, retrieves the list of 
	 * SelectedAgentHierarchies.
	 */ 
	public class GetSelectedAgentHierarchiesCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Selected AgentHierarchies";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var batchContractSetupPK:XML = SEGModelLocator.getInstance().selectedBatchContractSetupVO.BatchContractSetupPK[0];					
			
			new GroupDelegate(result, fault, Util.convertToXMLListCollection(batchContractSetupPK)).getSelectedAgentHierarchies();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var selectedAgentHiearchyVOs:XMLList = resultEvent.result.AgentSnapshotVO;
			
			SEGModelLocator.getInstance().selectedCandidateAgentHierarchyVOs = new XMLListCollection(selectedAgentHiearchyVOs);			
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}	
	}
}