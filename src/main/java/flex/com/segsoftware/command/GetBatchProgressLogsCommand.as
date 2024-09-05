package com.segsoftware.command
{
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.utility.Util;
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.events.*;
	import com.segsoftware.model.SEGModelBuilder;
	import mx.collections.XMLListCollection;
	
	public class GetBatchProgressLogsCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get BatchProgressLogs";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var selectedBatchContractSetupVO:XML = SEGModelLocator.getInstance().selectedBatchContractSetupVO;
			
			var batchContractSetupPK:XML = selectedBatchContractSetupVO.BatchContractSetupPK[0];
			
			new GroupDelegate(result, fault, Util.convertToXMLListCollection(batchContractSetupPK)).getBatchProgressLogs();	
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var batchProgressLogVOs:XMLList = resultEvent.result.BatchProgressLogVO;
			
			SEGModelLocator.getInstance().batchProgressLogVOs = new XMLListCollection(batchProgressLogVOs);			
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace("GetBatchProgressLogs.fault");
		}
	}
}