package com.segsoftware.command
{
	import com.segsoftware.view.custom.SEGComboBox;
	import com.segsoftware.model.SEGModelLocator;
	import mx.collections.XMLListCollection;
	import com.segsoftware.utility.Util;
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import com.segsoftware.business.ConversionDelegate;

	/**
	 * Deletes the selected ConversionTemplateVO.
	 */
	public class DeleteConversionTemplateCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Delete ConversionTemplate";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var selectedConversionTemplateVO:XML = null;//SEGModelLocator.getInstance().selectedConversionTemplateVO;
			
			var conversionTemplatePK:XML = selectedConversionTemplateVO.ConversionTemplatePK[0];
			
			var params:XMLListCollection = Util.convertToXMLListCollection(conversionTemplatePK);
			
			new ConversionDelegate(result, fault, params).deleteConversionTemplate();	
					
			// result(null, null);
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
 			var conversionTemplatePK:XML = null;//SEGModelLocator.getInstance().selectedConversionTemplateVO.ConversionTemplatePK[0];

			for each (var conversionTemplate:XML in SEGModelLocator.getInstance().conversionTemplates)
			{
				var currentConversionTemplatePK:XML = conversionTemplate.ConversionTemplatePK[0];
				
				if (conversionTemplatePK == currentConversionTemplatePK)
				{
					var indexOfTemplate:int = SEGModelLocator.getInstance().conversionTemplates.getItemIndex(conversionTemplate);
					
					SEGModelLocator.getInstance().conversionTemplates.removeItemAt(indexOfTemplate);
					
					break;
				}
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