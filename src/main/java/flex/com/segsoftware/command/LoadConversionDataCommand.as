package com.segsoftware.command
{
	import com.segsoftware.business.ConversionDelegate;
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.utility.Util;
	import com.segsoftware.view.custom.SEGFileUpload;

	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.collections.XMLListCollection;
	
	public class LoadConversionDataCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Load Conversion Data";
		
		override public function executeCommand(event:SEGEvent):void
		{
			SEGModelLocator.getInstance().flatFileName = Util.buildXMLElement("FlatFileName", event.formData.fileName);
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			super.result(resultEvent, EVENT_NAME);	
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}
	}
}