package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import mx.collections.XMLListCollection;
	import com.segsoftware.model.SEGModelLocator;
	
	/**
	 * The user may have modified:
	 * 
	 * 1. Some fields on the BatchContracSetup.
	 * 2. Added/modified BatchProgressLog entries.
	 * 3. Addes/modified BatchProductLog entries.
	 */
	public class UpdateBatchTransmittedDetailsCommand extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{	
			var selectedBatchContractSetupVO:XML = SEGModelLocator.getInstance().selectedBatchContractSetupVO;
			
			var batchProgressLogVOs:XMLListCollection = SEGModelLocator.getInstance().batchProgressLogVOs;
			
			var batchProductLogVOs:XMLListCollection = SEGModelLocator.getInstance().batchProductLogVOs;
			
			trace("TODO UpdateBatchTransmittedDetailsCommand");
		}
	}
}