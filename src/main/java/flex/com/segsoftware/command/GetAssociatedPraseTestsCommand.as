package com.segsoftware.command
{
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.business.PRASEDelegate;
	import com.segsoftware.utility.Util;
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import mx.collections.XMLListCollection;
	
	/**
	 * Retrieves the list of PRASETests associated with the currently
	 * selected PRASEDocument. This might be useful if the user wants to 
	 * delete a PRASEDocument, but should be notified of the PRASETests that
	 * are currently dependent on the PRASEDocument.
	 */ 
	public class GetAssociatedPraseTestsCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get associated PRASETests";
		
		override public function executeCommand(event:SEGEvent):void
		{
			SEGModelLocator.getInstance().associatedPRASETestVOs.removeAll();
			
			var praseDocumentVO:XML = SEGModelLocator.getInstance().selectedPRASEDocumentVO;
			
			var praseDocumentPK:XML = praseDocumentVO.PRASEDocumentPK[0];
			
			new PRASEDelegate(result, fault, Util.convertToXMLListCollection(praseDocumentPK)).getAssociatedPRASETests();
						
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var praseTests:XMLList = resultEvent.result.PRASETestVO;
			
			SEGModelLocator.getInstance().associatedPRASETestVOs = new XMLListCollection(praseTests);
			
			super.result(resultEvent, EVENT_NAME);								
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);			
		}
	}
}