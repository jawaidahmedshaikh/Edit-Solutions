package com.segsoftware.command
{
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.utility.Util;
	import com.segsoftware.model.logging.*;
	
	import flash.events.MouseEvent;
	
	import mx.collections.XMLListCollection;
	import mx.controls.Button;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import com.segsoftware.model.BatchContractSetupHelper;		
	
	public class CreateDefaultBatchContractSetupCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Create default BatchContractSetup";
		
		override public function executeCommand(event:SEGEvent):void
		{	
			// Gather the parameters		
			var selectedGroupContractGroupVO:XML = event.formData.selectedGroupContractGroupVO as XML;
			
			var groupContractGroupPK:String = selectedGroupContractGroupVO.ContractGroupPK;
			
			var operator:String = SEGModelLocator.getInstance().operator;
			
			var parameters:XMLListCollection = new XMLListCollection();
			
			parameters.addItem(Util.buildXMLElement("Operator", operator));
			
			parameters.addItem(Util.buildXMLElement("GroupContractGroupPK", groupContractGroupPK));
					
			// Invoke the service		
			new GroupDelegate(result, fault, parameters).createDefaultBatchContractSetup();
		}
		
        override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
        {			 
        	BatchContractSetupHelper.clearBatchContractSetup();
        	
        	// When someone is creating a new BatchContractSetup, we will render only this one.
        	SEGModelLocator.getInstance().batchContractSetupVOs.removeAll();
        	
        	var batchContractSetupVO:XML = resultEvent.result.BatchContractSetupVO[0];
        	
        	SEGModelLocator.getInstance().batchContractSetupVOs.addItem(batchContractSetupVO);
        	
        	super.result(resultEvent, EVENT_NAME);
        }
        
        override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
        {
        	super.fault(faultEvent, EVENT_NAME);
        	
 			trace(faultEvent.message);
        }			
	}
}