package com.segsoftware.command
{
	import com.segsoftware.business.ConversionDelegate;
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.utility.Util;

	import mx.collections.XMLListCollection;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;

	
	/**
	 * Validates the syntax of the TemplateText of the currently selected ConversionTemplate.
	 * Most likely, it is validation the text as valid XFlat syntax or not, although this is
	 * not the only template language we [will] use.
	 */  
	public class ValidateConversionTemplateCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Validate ConversionTemplate";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var selectedConversionTemplateVO:XML = null;//SEGModelLocator.getInstance().selectedConversionTemplateVO;
			
			var params:XMLListCollection = Util.convertToXMLListCollection(selectedConversionTemplateVO);
			
			new ConversionDelegate(result, fault, params).validateConversionTemplate();			
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