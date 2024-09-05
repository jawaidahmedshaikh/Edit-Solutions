package com.segsoftware.command
{
	import com.segsoftware.business.*;
	import com.segsoftware.model.SEGModelLocator;
	import mx.rpc.events.*;
	import com.segsoftware.event.*;
	import mx.collections.*;
	import com.segsoftware.utility.*;
	import com.segsoftware.model.logging.*;
	
	/**
	 * The BillGroup can have its StopReasonCT (really BillStopReason) and 
	 * its funds amount adjusted.
	 */ 
	public class UpdateBillGroupCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Update BillGroup";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var billGroupVO:XML = SEGModelLocator.getInstance().selectedBillGroupVO;
			
			var parameters:XMLListCollection = Util.convertToXMLListCollection(billGroupVO);			
			
			new BillingDelegate(result, fault, parameters).updateBillGroup();			
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
        	// Clear previous entries
        	SEGModelLocator.getInstance().billGroupVOs.removeAll();
        	
        	SEGModelLocator.getInstance().payorClientDetailVOs.removeAll();
        	
        	SEGModelLocator.getInstance().batchContractBillVOs.removeAll();
        	
        	SEGModelLocator.getInstance().overUnderAdjustment = null;
        	
        	super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);	
		}		
		
	}
}