package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.SEGModelBuilder;
	import mx.collections.XMLListCollection;
	import com.segsoftware.utility.Util;
	import com.segsoftware.view.conversion.AddCloneConversionTemplateDialog;
	import com.segsoftware.model.SEGModelLocator;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import com.segsoftware.business.ConversionDelegate;
	import com.segsoftware.model.conversion.ConversionTemplate;
	import com.segsoftware.control.SEGController;
	
	/**
	 * Creates a new ConversionTemplate whether that be a brand new
	 * one, or one based on an existing template.
	 */ 
	public class CreateConversionTemplateCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Create new ConversionTemplate";
		
		private var templateName:String;
		
		override public function executeCommand(event:SEGEvent):void
		{
			templateName = event.formData.templateName;
			
			var templateDescription:String = event.formData.templateDescription;
			
			var conversionTemplate:ConversionTemplate = ConversionTemplate.buildConversionTemplate_V1(templateName, templateDescription, SEGModelLocator.getInstance().groupNode);
			
			var conversionTemplateVO:XML = conversionTemplate.asXML();

			var params:XMLListCollection = Util.convertToXMLListCollection(conversionTemplateVO);

			new ConversionDelegate(result, fault, params).createConversionTemplate();	
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{	
			Util.dispatchEvent(SEGController.EVENT_GET_ALL_CONVERSION_TEMPLATES, <templateName>{templateName}</templateName>);
			
			super.result(resultEvent, eventName, token);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, eventName, token);
			
			trace(faultEvent.message);
		}
	}
}