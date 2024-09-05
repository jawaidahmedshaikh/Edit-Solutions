package com.segsoftware.command
{
	import com.segsoftware.model.SEGModelLocator;
	import mx.formatters.DateFormatter;
	import com.segsoftware.event.*;
	import mx.rpc.events.*;
	import mx.collections.*;
	import com.segsoftware.utility.Util;
	import com.segsoftware.business.*;
	
	public class GetPayrollDeductionCalendarsByYearAndPayrollDeductionSchedulePKCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get PayrollDeductionCalendars by Year";
		
		override public function executeCommand(event:SEGEvent):void
		{			
			var requestParameters:XMLListCollection = Util.convertToXMLListCollection(Util.buildXMLElement("TableName", "PayrollDeductionCalendar"));
			
			new SEGMetaDataDelegate(result, fault, requestParameters).buildDefaultValueObjectVO();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var payrollDeductionCalendarVO:XML = (resultEvent.result as XML).children()[0];
			
			for (var i:int = 0; i <=  11; i++)
			{
				var copyOfPayrollDeductionCalendarVO1:XML = payrollDeductionCalendarVO.copy();
				
				var copyOfPayrollDeductionCalendarVO2:XML = payrollDeductionCalendarVO.copy();
				
				var newDate1:Date = new Date(2007, i, 3);
				
				var newDate2:Date = new Date(2007, i, 5);
				
				var dateFormatter:DateFormatter = new DateFormatter();
				
				dateFormatter.formatString ="MM/DD/YYYY";
				
				var dateAsString1:String = dateFormatter.format(newDate1);
				
				var dateAsString2:String = dateFormatter.format(newDate2);
				
				copyOfPayrollDeductionCalendarVO1.PayrollDeductionDate = dateAsString1;
				
				copyOfPayrollDeductionCalendarVO2.PayrollDeductionDate = dateAsString2;				
				
				SEGModelLocator.getInstance().payrollDeductionCalendarVOs.addItem(copyOfPayrollDeductionCalendarVO1);
				
				SEGModelLocator.getInstance().payrollDeductionCalendarVOs.addItem(copyOfPayrollDeductionCalendarVO2);
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