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
	 * Gets the subset of PRASEDocuments from the universe of all PRASEDocuments
	 * that have not been associated with the currently selected PRASETest.
	 */ 
	public class GetCandidatePRASEDocumentsCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Candidate PRASEDocuments";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var selectedPRASETestVO:XML = SEGModelLocator.getInstance().selectedPRASETestVO;
			
			var praseTestPK:XML = selectedPRASETestVO.PRASETestPK[0];
			
			new PRASEDelegate(result, fault, Util.convertToXMLListCollection(praseTestPK)).getCandidatePRASEDocuments();
		}

		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var candidatePRASEDocumentVOs:XMLList = resultEvent.result.PRASEDocumentVO;
			
			var candidatePRASEDocumentWrapperVOs:XMLListCollection = new XMLListCollection();
			
			for each (var candidatePRASEDocumentVO:XML in candidatePRASEDocumentVOs)
			{
				var candidatePRASEDocumentWrapperVO:XML = SEGModelBuilder.getInstance().buildPRASEDocumentWrapperVO(candidatePRASEDocumentVO);
				
				candidatePRASEDocumentWrapperVOs.addItem(candidatePRASEDocumentWrapperVO);
			}
			
			SEGModelLocator.getInstance().candidatePRASEDocumentWrapperVOs = candidatePRASEDocumentWrapperVOs;
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);	
		}

	}
}