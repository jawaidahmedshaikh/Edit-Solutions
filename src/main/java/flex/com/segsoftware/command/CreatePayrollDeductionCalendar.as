package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.events.*;
	import com.segsoftware.utility.Util;
	import mx.collections.XMLListCollection;
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.model.SEGModelLocator;
	
	/**
	 * Users can add new entries to the PRD Calendar as PayrollDeductionCalendar entries.
	 */ 
	public class CreatePayrollDeductionCalendar extends SEGCommand
	{
		private var EVENT_NAME:String = "Create PayrollDeductionCalendar";
		
		private var payrollDeductionYear:String = null; 
		
		private var payrollDeductionSchedulePK:XML = null;
		
		override public function executeCommand(event:SEGEvent):void
		{
			// Create the PayrollDeductionCalendar
			var payrollDeductionCodeCT:XML = Util.buildXMLElement("PayrollDeductionCodeCT", event.formData.PayrollDeductionCodeVO.Code[0]);
			
			var payrollDeductionDate:Date = event.formData.PayrollDeductionDate as Date;
			
			var payrollDeductionDateXML:XML = Util.buildXMLElement("PayrollDeductionDate", Util.formatDate1(payrollDeductionDate));
			
			payrollDeductionSchedulePK = Util.buildXMLElement("PayrollDeductionSchedulePK", SEGModelLocator.getInstance().payrollDeductionSchedulePK);
			
			var parameters1:XMLListCollection = Util.convertToXMLListCollection(payrollDeductionCodeCT, payrollDeductionDateXML, payrollDeductionSchedulePK);
			
			payrollDeductionYear = payrollDeductionDate.getFullYear().toString();
			
			new GroupDelegate(result1, fault, parameters1).createPayrollDeductionCalendar();
		}
		
		public function result1(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			// Retrieve the updated list			
			var payrollDeductionYearXML:XML = Util.buildXMLElement("PayrollDeductionYear", payrollDeductionYear);			
			
			var parameters2:XMLListCollection = Util.convertToXMLListCollection(payrollDeductionSchedulePK, payrollDeductionYearXML);
			
			new GroupDelegate(result2, fault, parameters2).getPayrollDeductionCalendars();
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		public function result2(resultEvent:ResultEvent, eventName:String, token:Object=null):void
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