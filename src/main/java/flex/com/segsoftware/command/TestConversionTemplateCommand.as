package com.segsoftware.command
{
	import com.segsoftware.business.ConversionDelegate;
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.utility.Util;
	import com.segsoftware.view.custom.SEGTextArea;
	
	import mx.collections.XMLListCollection;	
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	
	public class TestConversionTemplateCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Test ConversionTemplate";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var conversionTemplatePK:Number = SEGModelLocator.getInstance().selectedConversionTemplateRun.conversionTemplatePK;

			var conversionFileName:String = SEGModelLocator.getInstance().selectedConversionData.fileName;
			
			var params:XMLListCollection = new XMLListCollection();
			
			params.addItem(Util.buildXMLElement("ConversionTemplatePK", conversionTemplatePK.toString()));
			
			params.addItem(Util.buildXMLElement("FileName", conversionFileName));
			
			new ConversionDelegate(result, fault, params).testConversionTemplate();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var sampleConvertedData:XML = resultEvent.result.SampleConvertedData[0];
			
			//SEGModelLocator.getInstance().sampleConvertedData = sampleConvertedData;
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}
	}
}