package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.events.ResultEvent;
	import com.segsoftware.utility.Util;
	import com.segsoftware.model.SEGModelLocator;

	/**
	 * Moves the selected PRASEDocumentWrapperVOs from the "selected" side 
	 * to the "candidate" side via the backing collections.
	 */ 
	public class DeselectCandidatePRASEDocumentsCommand extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{
			var highlightedSelectedPRASEDocumentWrapperVOs:Array = event.formData.highlightedSelectedPRASEDocumentWrapperVOs as Array;

			for each (var highlightedSelectedPRASEDocumentWrapperVO:XML in highlightedSelectedPRASEDocumentWrapperVOs)
			{
				Util.moveItemFromTo(highlightedSelectedPRASEDocumentWrapperVO, 
									SEGModelLocator.getInstance().selectedPRASEDocumentWrapperVOs,
									SEGModelLocator.getInstance().candidatePRASEDocumentWrapperVOs);
			}		
		}
	}
}