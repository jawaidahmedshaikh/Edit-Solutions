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
	import com.segsoftware.model.conversion.ConversionTemplate;
	
	/**
	 * Updates the TemplateText of the currently selected ConversionTemplateVO.
	 */
	public class UpdateConversionTemplateCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Update ConversionTemplate";
		
		private var conversionTemplateText:SEGTextArea = null;

		override public function executeCommand(event:SEGEvent):void
		{
			var templateName:String = event.formData.templateName;
			
			var templateDescription:String = event.formData.templateDescription;			
				
			var selectedConversionTemplate:ConversionTemplate = SEGModelLocator.getInstance().selectedConversionTemplateDesign;
			
			ConversionTemplate.updateConversionTemplate_V1(selectedConversionTemplate, templateName, templateDescription, SEGModelLocator.getInstance().groupNode);
			
			var params:XMLListCollection = Util.convertToXMLListCollection(selectedConversionTemplate.asXML());
			
			new ConversionDelegate(result, fault, params).updateConversionTemplate(); 			
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