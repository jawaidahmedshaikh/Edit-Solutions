package com.segsoftware.command
{
	import com.segsoftware.business.ConversionDelegate;
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import com.segsoftware.model.SEGModelLocator;
	import mx.collections.XMLListCollection;
	import com.segsoftware.model.conversion.ConversionData;
	
	/**
	 * Gets information about all of the files available to supply Test or Conversion
	 * data for the conversion.
	 */ 
	public class GetAllTestFilesCommand extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{
			new ConversionDelegate(result, fault, null).getAllTestFiles();			
		}
		
        override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void 
		{
			var conversionDataFileVOs:XMLList = resultEvent.result.ConversionDataFileVO;
			
			for each (var conversionDataFileXML:XML in conversionDataFileVOs)
			{
				var conversionData:ConversionData = new ConversionData();
				
				conversionData.unmarshal(conversionDataFileXML);
				
				SEGModelLocator.getInstance().conversionDatas.addItem(conversionData);
			}
			
			super.result(resultEvent, eventName, token);	
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, eventName, token);
		}
	}
}