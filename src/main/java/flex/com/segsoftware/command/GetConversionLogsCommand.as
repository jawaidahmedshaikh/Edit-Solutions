package com.segsoftware.command
{
	import com.segsoftware.business.ConversionDelegate;
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.utility.Util;
	
	import mx.collections.XMLListCollection;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import com.segsoftware.model.conversion.ConversionJob;
		
	public class GetConversionLogsCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Conversion Logs";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var selectedConversionJob:ConversionJob = SEGModelLocator.getInstance().selectedConversionJob;

			var selectedConversionJobPK:XML = null;//selectedConversionJob.conversionJobPK;

			var params:XMLListCollection = Util.convertToXMLListCollection(selectedConversionJobPK);
			
			new ConversionDelegate(result, fault, params).getConversionLogs();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var conversionLogVOs:XMLList = resultEvent.result.ConversionLogVO;

			SEGModelLocator.getInstance().conversionLogVOs = new XMLListCollection(conversionLogVOs);
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}
	}
}