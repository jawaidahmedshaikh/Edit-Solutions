package com.segsoftware.command
{
	import com.segsoftware.business.BillingDelegate;
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.utility.Util;
	import com.segsoftware.view.billing.Billing;
    import com.segsoftware.model.OverUnderAdjustment;	

	
	import mx.collections.XMLListCollection;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import com.segsoftware.model.SEGModelBuilder;
	import com.segsoftware.business.SEGMetaDataDelegate;
	import mx.controls.DataGrid;
	import mx.events.ListEvent;
	

	public class AdjustBillPaidAmountsCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Adjust Bill Paid Amounts";
		
		private var billGroupDataGrid:DataGrid;
		
		private var billDataGrid:DataGrid;
		
		private var payorDataGrid:DataGrid;
		
		override public function executeCommand(event : SEGEvent): void
		{	
			// Get the data
			var batchContractBillVOs:XMLListCollection = SEGModelLocator.getInstance().batchContractBillVOs;
			
			// We are [not] discriminating between BillVOs that changed, and those that did not. We
			// are submitting [all] of the BillVOs for the current Payor.
			var parameters:XMLListCollection = new XMLListCollection();
			
			for each (var billVO:XML in batchContractBillVOs)
			{
				var billPK:XML = billVO.BillPK[0];
				
				var billedAmount:XML = billVO.BilledAmount[0];
				
				var paidAmount:XML = billVO.PaidAmount[0];
				
				var adjustmentVO:XML = SEGModelBuilder.getInstance().buildAdjustmentVO(billPK, billedAmount, paidAmount);
				
				parameters.addItem(adjustmentVO);
			}
			
			new BillingDelegate(result, fault, parameters).adjustBillPaidAmounts();
		}	
				
        override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
        {	
        	// O.K. This is a little tricky. We want to re-render the current state of the Billing page
        	// with the updated values. Originally, I just reloaded the data (the BillGroups, Payors, etc)
        	// and used the dataGrid.selectedIndex = foo. However, this did not visually update the dataGrid
        	// and I could not resolve this. Instead, I am opting to manually adjust the PaidAmount fields
        	// for the current collections.
        	//updateSelectedPayor();
        	
        	if (SEGModelLocator.getInstance().billingAdjustMode == SEGModelLocator.BILLING_ADJUST_MODE_BY_PAYOR)
        	{
	        	adjustByPayor();	
        	}
        	else if (SEGModelLocator.getInstance().billingAdjustMode == SEGModelLocator.BILLING_ADJUST_MODE_BY_SEGMENT)
        	{
        		adjustBySegment();	
        	}
        	
        	super.result(resultEvent, EVENT_NAME);
        }
        
        override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
        {
        	super.fault(faultEvent, EVENT_NAME);
        	
			trace(faultEvent.message);
        }
        
        /**
        * Sums up across payor-bills to modify the front-end display.
        */ 
        private function adjustByPayor():void
        {
			var payorTotalBilledAmount:Number = new Number("0.00");
			
			var payorTotalPaidAmount:Number = new Number("0.00");
        	
        	// Sum the BillVOs and set into the selected Payor's TotalBilledAmount.
        	for each (var billVO:XML in SEGModelLocator.getInstance().batchContractBillVOs)
        	{
        		var billBilledAmount:Number = new Number(billVO.BilledAmount[0]);
        		
        		var billPaidAmount:Number = new Number(billVO.PaidAmount[0]);
        		
        		payorTotalBilledAmount += billBilledAmount;
        		
        		payorTotalPaidAmount += billPaidAmount;
        	}
        	
    		SEGModelLocator.getInstance().selectedPayorClientDetailVO.TotalBilledAmount[0] = Util.getCurrencyFormatter().format(payorTotalBilledAmount.toString());        		
    		
    		SEGModelLocator.getInstance().selectedPayorClientDetailVO.TotalPaidAmount[0] = Util.getCurrencyFormatter().format(payorTotalPaidAmount.toString());        	
			
			// Reset
			payorTotalBilledAmount = new Number("0.00");
			
			payorTotalPaidAmount = new Number("0.00");	
			
			var billGroupTotalBilledAmount:Number = new Number("0.00");
			
			var billGroupTotalPaidAmount:Number = new Number("0.00");
			
			// Sum the Payor's TotalPaidAmount and set into the selected BillGroup.
			for each (var payorClientDetailVO:XML in SEGModelLocator.getInstance().payorClientDetailVOs)
			{
				payorTotalBilledAmount = new Number(Util.unformatCurrency(payorClientDetailVO.TotalBilledAmount[0]));        		
        		
        		payorTotalPaidAmount = new Number(Util.unformatCurrency(payorClientDetailVO.TotalPaidAmount[0]));
        		
        		billGroupTotalBilledAmount += payorTotalBilledAmount;
        		
        		billGroupTotalPaidAmount += payorTotalPaidAmount;
			}
			
    		SEGModelLocator.getInstance().selectedBillGroupVO.TotalBilledAmount[0] = Util.getCurrencyFormatter().format(billGroupTotalBilledAmount.toString());								        		
    		
    		SEGModelLocator.getInstance().selectedBillGroupVO.TotalPaidAmount[0] = Util.getCurrencyFormatter().format(billGroupTotalPaidAmount.toString());
    		
    		// Update the OverUnderAdjustment with the selectedBillGroupVO's new numbers
        	SEGModelLocator.getInstance().overUnderAdjustment = new OverUnderAdjustment(); 											        	
        }
        
        /**
        * Sums up across segment-bills to modify the front-end display.
        */ 
        private function adjustBySegment():void
        {
			var billGroupTotalBilledAmount:Number = new Number("0.00");
			
			var billGroupTotalPaidAmount:Number = new Number("0.00");
        	
        	// Sum the BillVOs and set into the selected Payor's TotalBilledAmount.
        	for each (var billVO:XML in SEGModelLocator.getInstance().batchContractBillVOs)
        	{
        		var billBilledAmount:Number = new Number(Util.unformatCurrency(billVO.BilledAmount[0]));
        		
        		var billPaidAmount:Number = new Number(Util.unformatCurrency(billVO.PaidAmount[0]));
        		
        		billGroupTotalBilledAmount += billBilledAmount;
        		
        		billGroupTotalPaidAmount += billPaidAmount;
        	}
			
    		SEGModelLocator.getInstance().selectedBillGroupVO.TotalBilledAmount[0] = Util.getCurrencyFormatter().format(billGroupTotalBilledAmount.toString());								        		
    		
    		SEGModelLocator.getInstance().selectedBillGroupVO.TotalPaidAmount[0] = Util.getCurrencyFormatter().format(billGroupTotalPaidAmount.toString());
    		
    		// Update the OverUnderAdjustment with the selectedBillGroupVO's new numbers
        	SEGModelLocator.getInstance().overUnderAdjustment = new OverUnderAdjustment(); 												        	
        }        
	}
}