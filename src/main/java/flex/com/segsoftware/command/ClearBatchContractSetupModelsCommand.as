package com.segsoftware.command
{
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.event.*;
	import com.segsoftware.model.BatchContractSetupHelper;
	
	/**
	 * When selecting a new BatchContractSetup, the existing model(s) backing
	 * the currently selected BatchContractSetup need to be cleared.
	 * Those models are:
	 * 
	 * candidateAgentHierarchies
	 * batchProgressLogs
	 * batchProductLogs
	 */ 
	public class ClearBatchContractSetupModelsCommand extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{
			BatchContractSetupHelper.clearBatchContractSetup();
		}
	}
}