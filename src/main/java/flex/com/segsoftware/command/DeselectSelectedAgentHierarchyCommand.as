package com.segsoftware.command
{
	import mx.collections.XMLListCollection;
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.SEGModelLocator;
	import mx.collections.ArrayCollection;

	
	/**
	 * The user can deselect any "Candidate" AgentHiearchies that were previously 
	 * selected while working on the current BatchContractSetup. 
	 * This is an in-memory operation and does require back-end services until
	 * the final commit.
	 */ 
	public class DeselectSelectedAgentHierarchyCommand extends SEGCommand
	{	
		override public function executeCommand(event:SEGEvent):void
		{
			var highlightedSelectedAgentHierarchyVOs:ArrayCollection = new ArrayCollection(event.formData.highlightedSelectedAgentHierarchyVOs);
			
			for each (var agentSnapshotVO:XML in highlightedSelectedAgentHierarchyVOs)
			{
				transferFromTo(agentSnapshotVO);
			}
		}
		
		/**
		 * Moves every specified segEntryVO from the set of selectedAgentHierarchyVOs
		 * to the set of candidateAgentHierarchyVOs.
		 */  
		private function transferFromTo(agentSnapshotVO:XML):void
		{
			// Remove from the SelectedCandidateAgentHierarchyVOs
			var selectedCandidateAgentHierarchyVOs:XMLListCollection = SEGModelLocator.getInstance().selectedCandidateAgentHierarchyVOs;
			
			var indexOfAgentSnapshotVO:int = selectedCandidateAgentHierarchyVOs.getItemIndex(agentSnapshotVO);
			
			var removedAgentSnapshotVO:XML = selectedCandidateAgentHierarchyVOs.removeItemAt(indexOfAgentSnapshotVO) as XML;
			
			// Add it to the CandidateAgentHierarchyVOs.
			var candidateAgentHierarchyVOs:XMLListCollection = SEGModelLocator.getInstance().candidateAgentHierarchyVOs;
			
			candidateAgentHierarchyVOs.addItem(removedAgentSnapshotVO);
		}
	}
}