package com.segsoftware.command
{
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.event.SEGEvent;
	import mx.collections.XMLListCollection;
	import com.segsoftware.business.PRASEDelegate;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	
	/**
	 * Commits all changes to the currently selected PRASETest.
	 */ 
	public class UpdatePRASETestCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Update PRASETest";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var praseTestPK:XML = SEGModelLocator.getInstance().selectedPRASETestVO.PRASETestPK[0];
			
			var selectedPRASEDocumentWrapperVOs:XMLListCollection = SEGModelLocator.getInstance().selectedPRASEDocumentWrapperVOs;
			
			var params:XMLListCollection = new XMLListCollection();
			
			params.addItem(praseTestPK);
			
			// The actual PRASEDocumentVOs we want are contained in a wrapper VO.
			for each (var selectedPRASEDocumentWrapperVO:XML in selectedPRASEDocumentWrapperVOs)
			{
				var praseDocumentVO:XML = selectedPRASEDocumentWrapperVO.PRASEDocumentVO[0];
				
				if (selectedPRASEDocumentWrapperVO.IsRoot[0] == 'Y')
				{
					var rootPRASEDocumentPK:XML = selectedPRASEDocumentWrapperVO.PRASEDocumentVO[0].PRASEDocumentPK[0];
						
					params.addItem(rootPRASEDocumentPK);
				}
				
				params.addItem(praseDocumentVO);
			}

			new PRASEDelegate(result, fault, params).updatePRASETest();			
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