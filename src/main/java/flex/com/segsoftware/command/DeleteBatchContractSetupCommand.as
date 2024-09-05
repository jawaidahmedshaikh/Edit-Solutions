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
	
	public class DeleteBatchContractSetupCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Delete BatchContractSetup";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var batchContractSetupPK:XML = SEGModelLocator.getInstance().selectedBatchContractSetupVO.BatchContractSetupPK[0];					
			
			new GroupDelegate(result, fault, Util.convertToXMLListCollection(batchContractSetupPK)).deleteBatchContractSetup();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var responseMessageVOs:XMLListCollection = new XMLListCollection(resultEvent.result.ResponseMessageVO);
			
			if (Util.isSuccess(responseMessageVOs))
			{
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
	}
}