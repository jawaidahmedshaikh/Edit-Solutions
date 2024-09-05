package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.SEGModelLocator;
	import mx.collections.XMLListCollection;
	import com.segsoftware.utility.Util;
	import mx.core.Application;

	/**
	 * As a convenience, a user who has selected a "selected" PRASEDocument 
	 * has the option of editing that document.
	 */ 
	public class EditPRASEDocumentCommand extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{
			var praseDocumentWrapperVO:XML = event.formData.PRASEDocumentWrapperVO;
			
			var praseDocumentVO:XML = praseDocumentWrapperVO.PRASEDocumentVO[0];
			
			SEGModelLocator.getInstance().praseDocumentVOs = Util.convertToXMLListCollection(praseDocumentVO);
			
    		SEGModelLocator.getInstance().selectedPRASEDocumentVO = null;
			
			Application.application.showTab("praseTestDocumentTab");
		}
	}
}