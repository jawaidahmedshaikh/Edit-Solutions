package com.segsoftware.command
{
	import com.segsoftware.model.SEGModelBuilder;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.event.*;
	import mx.rpc.events.*;
	import mx.collections.ArrayCollection;
	import com.segsoftware.business.*;
	import com.segsoftware.utility.Util;
	import mx.collections.XMLListCollection;
	
	
	/**
	 * During BatchTransmitted/BatchProgressLog entry, the user will need to 
	 * specify a FilteredRequirement from the set of candidate ones. The 
	 * candidate ones are driven by the ProductStructure associated with
	 * "Case***".
	 */ 
	public class GetCaseFilteredRequirementsCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Case FilteredRequirements";
		
		override public function executeCommand(event:SEGEvent):void
		{
			new GroupDelegate(result, fault, null).getCaseFilteredRequirements();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			SEGModelLocator.getInstance().caseFilteredRequirementVOs.removeAll();
			
			var caseFilteredRequirementVOs:XMLList = resultEvent.result.FilteredRequirementVO;
			
			SEGModelLocator.getInstance().caseFilteredRequirementVOs = new XMLListCollection(caseFilteredRequirementVOs);						
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}
	}
}