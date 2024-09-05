package com.segsoftware.command
{
	import com.segsoftware.business.PRASEDelegate;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import com.segsoftware.model.SEGModelLocator;
	import mx.collections.XMLListCollection;
	import com.segsoftware.event.SEGEvent;
	
	/**
	 * Gets the list of all PRASEDocuments available within PRASE.
	 */ 
	public class GetAllPRASEDocumentsCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get all PRASEDocuments in PRASE";
		
		override public function executeCommand(event:SEGEvent):void
		{
			new PRASEDelegate(result, fault, null).getAllPRASEDocuments();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			SEGModelLocator.getInstance().praseDocumentVOs = new XMLListCollection(resultEvent.result.PRASEDocumentVO);		
			
			super.result(resultEvent, EVENT_NAME);						
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}
	}
}