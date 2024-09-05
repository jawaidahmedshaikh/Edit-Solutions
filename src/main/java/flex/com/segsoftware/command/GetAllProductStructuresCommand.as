package com.segsoftware.command
{
	import com.segsoftware.business.PRASEDelegate;
	import com.segsoftware.model.SEGModelLocator;
	import mx.rpc.events.ResultEvent;
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.events.FaultEvent;
	import mx.collections.XMLListCollection;
	
	/**
	 * Gets all available ProductStructureVOs and their associated CompanyVOs.
	 */ 
	public class GetAllProductStructuresCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get all available ProductStructureVOs"; 
		
		override public function executeCommand(event:SEGEvent):void
		{	
			if (SEGModelLocator.getInstance().productStructureVOs.length == 0)
			{
				new PRASEDelegate(result, fault, null).getAllProductStructures();
			}
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var productStructureVOs:XMLList = resultEvent.result.ProductStructureVO;
			
			SEGModelLocator.getInstance().productStructureVOs = new XMLListCollection(productStructureVOs);			
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);	
		}
	}
}