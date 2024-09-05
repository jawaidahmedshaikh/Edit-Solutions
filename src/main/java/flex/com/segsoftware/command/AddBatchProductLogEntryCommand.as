package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import mx.collections.XMLListCollection;
	import com.segsoftware.utility.Util;
	import com.segsoftware.model.SEGModelBuilder;
	import com.segsoftware.model.SEGModelLocator;
	
	/**
	 * Adds another BatchProductLogVO to the model.
	 */ 
	public class AddBatchProductLogEntryCommand extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{
			var batchProductLogVO:XML = SEGModelLocator.getInstance().newValueObjectVO.BatchProductLogVO[0].copy();
			
			SEGModelLocator.getInstance().batchProductLogVOs.addItem(batchProductLogVO);
			
			delete SEGModelLocator.getInstance().newValueObjectVO.BatchProductLogVO;
		}
	}
}