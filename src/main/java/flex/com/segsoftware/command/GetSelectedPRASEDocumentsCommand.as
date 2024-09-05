package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.business.PRASEDelegate;
	import com.segsoftware.utility.Util;
	import mx.collections.XMLListCollection;
	import com.segsoftware.model.SEGModelBuilder;
		
	/**
	 * Finds the list of PRASEDocuments associated with the currently selected/specified
	 * PRASETest.
	 */ 
	public class GetSelectedPRASEDocumentsCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Selected PRASEDocuments";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var selectedPRASETestVO:XML = SEGModelLocator.getInstance().selectedPRASETestVO;
			
			var praseTestPK:XML = selectedPRASETestVO.PRASETestPK[0];
			
			new PRASEDelegate(result, fault, Util.convertToXMLListCollection(praseTestPK)).getSelectedPRASEDocuments();
		}

		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var praseTestDocumentVOs:XMLList = resultEvent.result.PRASETestDocumentVO;
			
			var selectedPRASEDocumentWrapperVOs:XMLListCollection = new XMLListCollection();
			
			for each (var praseTestDocumentVO:XML in praseTestDocumentVOs)
			{
				var isRoot:String = praseTestDocumentVO.IsRoot[0];
				
				var praseDocumentVO:XML = praseTestDocumentVO.PRASEDocumentVO[0];
				
				var selectedPRASEDocumentWrapperVO:XML = SEGModelBuilder.getInstance().buildPRASEDocumentWrapperVO(praseDocumentVO, isRoot);		
				
				selectedPRASEDocumentWrapperVOs.addItem(selectedPRASEDocumentWrapperVO);
			}
			
			SEGModelLocator.getInstance().selectedPRASEDocumentWrapperVOs = selectedPRASEDocumentWrapperVOs;
			
			super.result(resultEvent, EVENT_NAME);						
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);	
		}
	}
}