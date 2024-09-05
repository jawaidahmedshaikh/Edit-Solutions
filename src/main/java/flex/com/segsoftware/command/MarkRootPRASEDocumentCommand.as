package com.segsoftware.command
{
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.event.SEGEvent;
	import mx.collections.XMLListCollection;
	
	/**
	 * While setting-up the "selected" PRASEDocuments for a selected PRASETest,
	 * only one of the Documents can be Root.
	 * This guarantees that the "just" checked PRASEDocument (to be root) is the 
	 * only document flagged as such.
	 */ 
	public class MarkRootPRASEDocumentCommand extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{
			// 1. Mark all the "selected" PRASEDocumentWrapperVOs as IsRoot = N.
			
			var selectedPRASEDocumentWrapperVOs:XMLListCollection = SEGModelLocator.getInstance().selectedPRASEDocumentWrapperVOs;
			
			for each (var currentSelectedPRASEDocumentWrapperVO:XML in selectedPRASEDocumentWrapperVOs)
			{
				currentSelectedPRASEDocumentWrapperVO.IsRoot[0] = 'N';
			}
			
			// 2. Now mark the currently "just" checked PRASEDocumentWrapperVO as IsRoot = 'Y';
			
			var selectedPRASEDocumentWrapperVO:XML = SEGModelLocator.getInstance().selectedPRASEDocumentWrapperVO;
			
			selectedPRASEDocumentWrapperVO.IsRoot[0] = 'Y';
			
			SEGModelLocator.getInstance().selectedPRASEDocumentWrapperVOs.refresh();
		}
	}
}