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

	public class GetPayorPolicyBillsCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Bills for Payor Policy";
		
		override public function executeCommand(event : SEGEvent): void
		{
			// Collect data 
			var selectedPayorClientDetailVO:XML = SEGModelLocator.getInstance().selectedPayorClientDetailVO;
			
			var selectedBillGroupVO:XML = SEGModelLocator.getInstance().selectedBillGroupVO;			
			
			if ((selectedPayorClientDetailVO != null) && (selectedBillGroupVO != null))
			{
				var clientDetailPK:XML = selectedPayorClientDetailVO.ClientDetailPK[0];
				
				var billGroupPK:XML = selectedBillGroupVO.BillGroupPK[0];
				
				var parameters:XMLListCollection = Util.convertToXMLListCollection(clientDetailPK, billGroupPK);
				
				new BillingDelegate(result, fault, parameters).getBillsForPayorInBillGroup();
			}
			else
			{
	        	// Clear previous entries
	        	SEGModelLocator.getInstance().batchContractBillVOs.removeAll();				
			}
		}	
				
        override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
        {
        	// Clear previous entries
        	SEGModelLocator.getInstance().batchContractBillVOs.removeAll();
        	
        	var batchContractBillVOs:XMLList = resultEvent.result.BillVO;
        	
        	SEGModelLocator.getInstance().batchContractBillVOs = new XMLListCollection(batchContractBillVOs);
        	
        	super.result(resultEvent, EVENT_NAME);
        }
        
        override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
        {
        	super.fault(faultEvent, EVENT_NAME);
        	
			trace(faultEvent.message);
        }
	}
}