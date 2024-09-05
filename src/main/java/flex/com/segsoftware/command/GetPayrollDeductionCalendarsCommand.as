package com.segsoftware.command
{
	import com.segsoftware.event.*;
	import mx.rpc.events.*;
	import com.segsoftware.utility.*;
	import mx.collections.XMLListCollection;
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.model.SEGModelLocator;
	
	/**
	 * Users can choose particular days in a calendar year for payroll deductions.
	 * To present previously chosen days within a specified year, we offer
	 * the abiliy to get get PayrollDeductionCalendar(s) by year.
	 * The year is user selected.
	 */ 
	public class GetPayrollDeductionCalendarsCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get PayrollDeductionCalendars";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var payrollDeductionYear:XML = Util.buildXMLElement("PayrollDeductionYear", (event.formData.PayrollDeductionYear as int).toString());
			
			var payrollDeductionSchedulePK:XML = Util.buildXMLElement("PayrollDeductionSchedulePK", SEGModelLocator.getInstance().payrollDeductionSchedulePK); 
			
			var parameters:XMLListCollection = Util.convertToXMLListCollection(payrollDeductionYear, payrollDeductionSchedulePK);
			
			new GroupDelegate(result, fault, parameters).getPayrollDeductionCalendars();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var payrollDeductionCalendarVOs:XMLListCollection = new XMLListCollection(resultEvent.result.PayrollDeductionCalendarVO);
			
			SEGModelLocator.getInstance().payrollDeductionCalendarVOs = payrollDeductionCalendarVOs;			
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);			
		}
	}
}