package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.utility.Util;
	import mx.collections.XMLListCollection;
	import mx.collections.ListCollectionView;
	import com.segsoftware.model.SEGModelLocator;
	
	/**
	 * Moves the selected PRASEDocuments from the "Candidate" list to the "Selected" list.
	 */ 
	public class SelectCandidatePRASEDocumentsCommand extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{
			var highlightedCandidatePRASEDocumentWrapperVOs:Array = event.formData.highlightedCandidatePRASEDocumentWrapperVOs as Array;

			for each (var highlightedCandidatePRASEDocumentWrapperVO:XML in highlightedCandidatePRASEDocumentWrapperVOs)
			{
				Util.moveItemFromTo(highlightedCandidatePRASEDocumentWrapperVO, 
									SEGModelLocator.getInstance().candidatePRASEDocumentWrapperVOs,
									SEGModelLocator.getInstance().selectedPRASEDocumentWrapperVOs);
			}			
		}
	}
}