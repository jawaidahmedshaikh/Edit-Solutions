package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.SEGModelLocator;
	import mx.rpc.events.*;
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.utility.Util;
	import mx.collections.XMLListCollection;
	import flash.events.Event;
	import mx.events.CollectionEvent;
	
	public class GetCandidateProductStructuresCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Candidate ProductStructures";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var selectedBatchContractSetupVO:XML = SEGModelLocator.getInstance().selectedBatchContractSetupVO;
			
			var batchContractSetupPK:XML = selectedBatchContractSetupVO.BatchContractSetupPK[0];
			
			new GroupDelegate(result, fault, Util.convertToXMLListCollection(batchContractSetupPK)).getCandidateFilteredProducts();				
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var candidateFilteredProductVOs:XMLList = resultEvent.result.FilteredProductVO;
			
			SEGModelLocator.getInstance().candidateFilteredProductVOs = new XMLListCollection(candidateFilteredProductVOs);			
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}
	}
}