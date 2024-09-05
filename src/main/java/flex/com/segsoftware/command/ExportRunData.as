package com.segsoftware.command
{
	import com.segsoftware.business.*;
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.events.*;
	import com.segsoftware.model.*;
	import flash.events.EventDispatcher;
	import mx.logging.*;
	import com.segsoftware.utility.Util;
	import mx.collections.XMLListCollection;

		
	public class ExportRunData extends SEGCommand
	{
		private var EVENT_NAME:String = "Export Run Data";
		
		override public function executeCommand(event : SEGEvent): void
		{
			var operator:String = event.formData.SPRecordedOperator.operator;
			
			var runInformation:String = event.formData.SPRecordedRun.runInformation;			
				
			var parameters:XMLListCollection = new XMLListCollection();
			
			parameters.addItem(Util.buildXMLElement("Operator", operator));
			
			parameters.addItem(Util.buildXMLElement("RunInformation", runInformation));
			
			new PRASEDelegate(result, fault, parameters).exportRunData();
		}	 
				
        override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
        {
        	//	No data will be returned, just messages for the log
        	super.result(resultEvent, EVENT_NAME);
        }
        
        override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
        {	
        	super.fault(faultEvent, EVENT_NAME);
        	
			trace(faultEvent.message);
        }
    }
}