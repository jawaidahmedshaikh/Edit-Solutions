package com.segsoftware.command
{
	import com.segsoftware.event.*;
	import mx.rpc.events.*;
	import mx.controls.RadioButtonGroup;
	import com.segsoftware.model.SEGModelLocator;
	import mx.collections.XMLListCollection;
	import com.segsoftware.utility.Util;
	import com.segsoftware.business.GroupDelegate;
	
	/**
	 * The way the use case(s) flow for the PayrollDeductionCalendar, the user can
	 * either udpate or delete a PayrollDeductionCalendar entry after it has been
	 * added (a previous step). Whether to update/delete is specified with the request.
	 */ 
	public class UpdateDeletePayrollDeductionCalendars extends SEGCommand
	{
		private var EVENT_NAME:String = "Update/Delete Payroll Deduction Calendars";
		
		/**
		 * Stores the user's request to delete or update the PayrollDeductionCalendar.
		 */ 
		private var updateDelete:String;
		
		/**
		 * The PayrollDeductionCaledendarPK for which to delete or update.
		 */ 
		private var payrollDeductionCalendarPK:XML;
		
		override public function executeCommand(event:SEGEvent):void
		{
			updateDelete = event.formData.UpdateDelete as String;
			
			var selectedPayrollDeductionCalendarVO:XML = SEGModelLocator.getInstance().selectedPayrollDeductionCalendarVO;

			if (selectedPayrollDeductionCalendarVO != null)
			{
				payrollDeductionCalendarPK = selectedPayrollDeductionCalendarVO.PayrollDeductionCalendarPK[0];
	
				var payrollDeductionCodeCT:XML = selectedPayrollDeductionCalendarVO.PayrollDeductionCodeCT[0];			
				
				var parameters:XMLListCollection = null;
				
				if (updateDelete == "update")
				{
					parameters = Util.convertToXMLListCollection(payrollDeductionCalendarPK, payrollDeductionCodeCT);
					
					new GroupDelegate(result, fault, parameters).updatePayrollDeductionCalendar();
				}
				else if (updateDelete == "delete")
				{
					parameters = Util.convertToXMLListCollection(payrollDeductionCalendarPK);				
					
					new GroupDelegate(result, fault, parameters).deletePayrollDeductionCalendar();
				}
			}
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			// Remove the just deleted PayrollDeductionCalendar.
			if (updateDelete == "delete")
			{
				for each (var payrollDeductionCalendarVO:XML in SEGModelLocator.getInstance().payrollDeductionCalendarVOs)
				{
					var currentPayrollDeductionCalendarPK:XML = payrollDeductionCalendarVO.PayrollDeductionCalendarPK[0];
					
					if (currentPayrollDeductionCalendarPK.text()[0] == payrollDeductionCalendarPK.text()[0])
					{
						var index:int = SEGModelLocator.getInstance().payrollDeductionCalendarVOs.getItemIndex(payrollDeductionCalendarVO);
						
						SEGModelLocator.getInstance().payrollDeductionCalendarVOs.removeItemAt(index);						
					}										
				}				
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