package com.segsoftware.command
{
	import com.segsoftware.business.ConversionDelegate;
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.utility.Util;	
	
	import mx.collections.XMLListCollection;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import com.segsoftware.model.conversion.ConversionTemplate;
	
	/**
	 * Retrieves all of the existing ConversionTemplates.
	 */ 
	public class GetAllConversionTemplatesCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get all ConversionTemplates"; 
		
		private var templateName:XML;
		
		override public function executeCommand(event:SEGEvent):void
		{
			templateName = event.formData as XML;
			
			new ConversionDelegate(result, fault, null).getAllConversionTemplates();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			SEGModelLocator.getInstance().conversionTemplates.removeAll();
			
			var conversionTemplateVOs:XMLList = resultEvent.result.ConversionTemplateVO;
			
			for each (var conversionTemplateVO:XML in conversionTemplateVOs)
			{
				var conversionTemplate:ConversionTemplate = ConversionTemplate.buildConversionTemplate_V2(conversionTemplateVO);
				
				SEGModelLocator.getInstance().conversionTemplates.addItem(conversionTemplate);
			}
			
			if (templateName)
			{
				SEGModelLocator.getInstance().groupDesignUI.setSelectedConversionTemplate(templateName.valueOf());
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