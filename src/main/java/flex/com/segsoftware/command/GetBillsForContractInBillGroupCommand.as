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
		
	/**
	 * Users working with a BillGroup (ListBill) can adjust the bills in two modes. One mode
	 * is by Payor where Bills by Payor are rendered for adjustment. The other is Bills by Contract
	 * in which this command supports.
	 */ 
	public class GetBillsForContractInBillGroupCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Bills For Contract in Bill Group";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var selectedBillGroupVO:XML = SEGModelLocator.getInstance().selectedBillGroupVO;
			
			if (selectedBillGroupVO != null)
			{
				var billGroupPK:XML = selectedBillGroupVO.BillGroupPK[0];
				
				var parameters:XMLListCollection = Util.convertToXMLListCollection(billGroupPK);
				
				new BillingDelegate(result, fault, parameters).getBillsForContractInBillGroup();			
			}
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
        	// Clear previous entries
        	SEGModelLocator.getInstance().batchContractBillVOs.removeAll();
        	
        	// Establish the OverUnderAdjustment
        	SEGModelLocator.getInstance().overUnderAdjustment = new OverUnderAdjustment();  
        	
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