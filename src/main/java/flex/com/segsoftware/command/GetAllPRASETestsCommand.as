package com.segsoftware.command
{
	import com.segsoftware.business.PRASEDelegate;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import com.segsoftware.model.SEGModelLocator;
	import mx.collections.XMLListCollection;
	import com.segsoftware.event.SEGEvent;
	
	/**
	 * Gets all PRASETests available.
	 */ 
	public class GetAllPRASETestsCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get all PRASETests";
		
		override public function executeCommand(event:SEGEvent):void
		{
			SEGModelLocator.getInstance().candidatePRASEDocumentWrapperVOs.removeAll();
			
			SEGModelLocator.getInstance().selectedPRASEDocumentWrapperVOs.removeAll();
			
			new PRASEDelegate(result, fault, null).getAllPRASETests();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var praseTestVOs:XMLList = resultEvent.result.PRASETestVO;
			
			SEGModelLocator.getInstance().praseTestVOs = new XMLListCollection(praseTestVOs);
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}
	}
}