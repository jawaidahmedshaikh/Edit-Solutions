package com.segsoftware.command
{	
	import com.segsoftware.command.SEGCommand;
	import com.segsoftware.business.BillingDelegate;	
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import com.segsoftware.model.SEGModelLocator;

	import mx.collections.XMLListCollection;	
	
	public class GetNotPaidBilledCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Bills Not Paid";
		
		override public function executeCommand(event : SEGEvent): void
		{
			new BillingDelegate(result, fault, null).getBillGroupsNotPaid();
		}	
				
        override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
        {
        	// Clear previous entries
        	SEGModelLocator.getInstance().billGroupVOs.removeAll();
        	
        	SEGModelLocator.getInstance().payorClientDetailVOs.removeAll();
        	
        	SEGModelLocator.getInstance().batchContractBillVOs.removeAll();
        	
        	SEGModelLocator.getInstance().overUnderAdjustment = null;
        	
        	var billGroupVOs:XMLList = resultEvent.result.BillGroupVO;
        	
        	for each (var billGroupVO:XML in billGroupVOs)
        	{
        		SEGModelLocator.getInstance().billGroupVOs.addItem(billGroupVO);
        	}
        	
			//SEGModelLocator.getInstance().billGroupVOs = new XMLListCollection(billGroupVOs);        	
			
			super.result(resultEvent, EVENT_NAME);
        }
        
        override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
        {
        	super.fault(faultEvent, EVENT_NAME);
        	
			trace(faultEvent.message);
        }	    		
	}
}