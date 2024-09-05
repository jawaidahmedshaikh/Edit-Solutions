package com.segsoftware.command
{
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.events.*;
	import com.segsoftware.model.*;
	import mx.collections.XMLListCollection;
	import com.segsoftware.model.BatchContractSetupHelper;
	import flash.events.EventDispatcher;
	import mx.logging.*;
	import com.segsoftware.utility.Util;
		
	public class GetAllBatchContractSetupsCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Show All BatchContractSetups";
		
		override public function executeCommand(event : SEGEvent): void
		{
			new GroupDelegate(result, fault, null).getAllBatchContractSetups();
		}	 
				
        override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
        {
        	// Clear the current state of BatchContractSetup.
        	BatchContractSetupHelper.clearBatchContractSetup();
        	
        	// Empty the current list
        	SEGModelLocator.getInstance().batchContractSetupVOs.removeAll();
        	
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