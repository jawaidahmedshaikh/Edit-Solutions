package com.segsoftware.command
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.adobe.cairngorm.commands.ICommand;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.business.BillingDelegate;
	import mx.controls.DataGrid;
	import com.segsoftware.event.*;
	import com.segsoftware.utility.Util;
	import mx.collections.XMLListCollection;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import com.segsoftware.model.OverUnderAdjustment;	

	
	public class GetBillGroupPayorsCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Bill Group Payors";
		
		override public function executeCommand(event : SEGEvent): void
		{
			// Get the data
			var selectedBillGroupVO:XML = SEGModelLocator.getInstance().selectedBillGroupVO;
			
			var billGroupPK:XML = selectedBillGroupVO.BillGroupPK[0];
			
			var parameters:XMLListCollection = Util.convertToXMLListCollection(billGroupPK);
			
			new BillingDelegate(result, fault, parameters).getPayorsOfBillGroup(); 
		}	
				
        override public function result(result:ResultEvent, eventName:String, token:Object=null):void
        {
        	// Clear previous entries
        	SEGModelLocator.getInstance().payorClientDetailVOs.removeAll();
        	
        	SEGModelLocator.getInstance().batchContractBillVOs.removeAll();
        	
        	// Establish the OverUnderAdjustment
        	SEGModelLocator.getInstance().overUnderAdjustment = new OverUnderAdjustment();      	
        	
        	var resultEvent:ResultEvent = result as ResultEvent;
        	
        	var payorClientDetailVOs:XMLList = resultEvent.result.ClientDetailVO;
        	
        	SEGModelLocator.getInstance().payorClientDetailVOs = new XMLListCollection(payorClientDetailVOs);
        	
        	Util.sort(SEGModelLocator.getInstance().payorClientDetailVOs, "LastName"); 
        	
        	super.result(resultEvent, EVENT_NAME);
        }
        
        override public function fault(error:FaultEvent, eventName:String, token:Object=null):void
        {
        	var faultEvent:FaultEvent = error as FaultEvent;
        	
        	super.fault(faultEvent, EVENT_NAME);
        	
			trace(faultEvent.message);
        }
	}
}