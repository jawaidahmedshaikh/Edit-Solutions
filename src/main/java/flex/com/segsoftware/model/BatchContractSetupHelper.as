package com.segsoftware.model
{
	/**
	 * Without a legititate object model, we are still left with the need
	 * to perform some repeatable operations that apply to an "object concept" 
	 * in general.
	 * 
	 * These utility methods are to operate on BatchContractSetup operations [only].
	 */  
	public class BatchContractSetupHelper
	{
		/**
		 * BatchContractSetup and its related entities such as...
		 * 
		 * CandidateAgentHierarchies
		 * SelectedCandidateAgentHierarchies
		 * BatchProgressLogs
		 * BatchProductLogs
		 * 
		 * ... need to be cleared from session from time-to-time. Some obvious
		 * user gestures would include:
		 * 
		 * 1. Having just committed the BatchContractSetup.
		 * 2. Selecting a different BatchContractSetup.
		 * 
		 * This clears the current state of all things related to the 
		 * BatchContractSetup. 
		 * 
		 * NOTE: This does NOT clear the collection of BatchContractSetups.
		 */  
		public static function clearBatchContractSetup():void
		{			
			SEGModelLocator.getInstance().candidateAgentHierarchyVOs.removeAll();
			
			SEGModelLocator.getInstance().selectedCandidateAgentHierarchyVOs.removeAll();
			
			SEGModelLocator.getInstance().batchProgressLogVOs.removeAll();
			
			SEGModelLocator.getInstance().batchProductLogVOs.removeAll();
			
			SEGModelLocator.getInstance().candidateFilteredProductVOs.removeAll();
		}
	}
}