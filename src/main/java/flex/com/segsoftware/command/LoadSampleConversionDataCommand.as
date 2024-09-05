package com.segsoftware.command
{
	import com.segsoftware.business.ConversionDelegate;
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.utility.Util;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.collections.XMLListCollection;
	import com.segsoftware.view.custom.SEGFileUpload;
	import com.segsoftware.model.conversion.ConversionData;
	
	public class LoadSampleConversionDataCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Load Sample Conversion Data";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var flatFileName:String = SEGModelLocator.getInstance().selectedConversionData.fileName;
			
			var params:XMLListCollection = Util.convertToXMLListCollection(flatFileName);

			new ConversionDelegate(result, fault, params).loadSampleConversionData();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var sampleConversionData:String = resultEvent.result.SampleConversionDataVO[0];

			//SEGModelLocator.getInstance().sampleConversionData = sampleConversionData;
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}
	}
}
