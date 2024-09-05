package com.segsoftware.command
{
	import com.segsoftware.event.*;
	import com.segsoftware.model.SEGModelBuilder;
	import com.segsoftware.model.SEGModelLocator;
	
	/**
	 * Adds an entry to the DaysAddedReason model when working on BatchContractSetup/BatchTransmittedDialog
	 */ 
	public class AddBatchProgressLogEntryCommand extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{
			// A bound entity from the ui.
			var batchProgressLogVO:XML = SEGModelLocator.getInstance().newValueObjectVO.BatchProgressLogVO[0].copy(); // by value 
			
			SEGModelLocator.getInstance().batchProgressLogVOs.addItem(batchProgressLogVO);
			
			delete SEGModelLocator.getInstance().newValueObjectVO.BatchProgressLogVO;
		}
	}
}