package com.segsoftware.command
{
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.business.GroupDelegate;
	import mx.rpc.events.*;
	import com.segsoftware.utility.Util;
	import com.segsoftware.event.*;
	import mx.collections.XMLListCollection;
	
	/**
	 * For the current BatchContractSetup, gets the existing BatchProductLogVOs.
	 */  
	public class GetBatchProductLogsCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get existing BatchProductLogVOs";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var selectedBatchContractSetupVO:XML = SEGModelLocator.getInstance().selectedBatchContractSetupVO;
			
			var batchContractSetupPK:XML = selectedBatchContractSetupVO.BatchContractSetupPK[0];
			
			new GroupDelegate(result, fault, Util.convertToXMLListCollection(batchContractSetupPK)).getBatchProductLogs();		
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var batchProductLogVOs:XMLList = resultEvent.result.BatchProductLogVO;
			
			SEGModelLocator.getInstance().batchProductLogVOs = new XMLListCollection(batchProductLogVOs);			
			
			super.result(resultEvent, EVENT_NAME);
		}
	}
}