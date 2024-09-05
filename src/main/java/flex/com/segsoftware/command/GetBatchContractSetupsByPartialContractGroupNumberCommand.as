package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import com.segsoftware.utility.Util;
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.model.SEGModelLocator;
	import mx.collections.XMLListCollection;
	import com.segsoftware.model.logging.*;
	
	/**
	 * There could be thousands of BatchContractSetups. The user may want to filter
	 * them by a partial ContractGroupNumber. They still need to come back sorted by 
	 * ContractGroupNumber asc/ReceiptDate desc
	 */ 
	public class GetBatchContractSetupsByPartialContractGroupNumberCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Filter BatchContractSetups";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var partialContractGroupNumber:XML = Util.buildXMLElement("PartialContractGroupNumber", event.formData.PartialContractGroupNumber);
			
			new GroupDelegate(result, fault, Util.convertToXMLListCollection(partialContractGroupNumber)).getBatchContractSetupsByPartialContractGroupNumber();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var batchContractSetupVOs:XMLList = resultEvent.result.BatchContractSetupVO;
			
			SEGModelLocator.getInstance().batchContractSetupVOs = new XMLListCollection(batchContractSetupVOs);
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);	
		}
	}
}