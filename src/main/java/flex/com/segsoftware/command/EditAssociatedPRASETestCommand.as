package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import mx.core.Application;
	import com.segsoftware.utility.Util;
	import com.segsoftware.model.SEGModelLocator;
	
	/**
	 * As a convenience, a user can see associated PRASETest(s) of a currently selected
	 * PRASEDocument. Seeing this, the user may wish to edit one of the associated PRASETests.
	 */ 
	public class EditAssociatedPRASETestCommand extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{
			var praseTestVO:XML = event.formData.PRASETestVO;					

			SEGModelLocator.getInstance().praseTestVOs = Util.convertToXMLListCollection(praseTestVO);
			
			SEGModelLocator.getInstance().candidatePRASEDocumentWrapperVOs.removeAll();
			
			SEGModelLocator.getInstance().selectedPRASEDocumentWrapperVOs.removeAll();
			
			Application.application.showTab("praseTestSetupTab");
		}	
	}
}