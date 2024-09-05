package com.segsoftware.command
{	
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.SEGModelLocator;
	import mx.collections.XMLListCollection;
	import mx.collections.ArrayCollection;
	
	/**
	 * When updating a BatchContractSetup in order to enter the app-entry
	 * phase, the user will select a "SelectedAgentHierarchy" from the set
	 * of "CandidateAgentHierarchies" that were associated to the current
	 * Case-ContractGroup.
	 * 
	 * The operation(s) of selecting/deselecting CandidateAgentHierarchies is
	 * an in-memory operation and does not entail backend services until 
	 * the commit phase.
	 */ 
	public class SelectCandidateAgentHierarchyCommand extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{
			var highlightedCandidateAgentHierarchyVOs:ArrayCollection = new ArrayCollection(event.formData.highlightedCandidateAgentHierarchyVOs);
			
			for each (var agentSnapshotVO:XML in highlightedCandidateAgentHierarchyVOs)
			{
				transferFromTo(agentSnapshotVO);
			}
		}
		
		/**
		 * 
		 * Items selected in the "CandidateAgentHierarchyVOs" are selected to be removed and then appended to the
		 * "SelectedAgentHierarchyVOs" of the current BatchContractSetupVO document. This function removes
		 * the XML element from the "CandidateAgentHierarchyVOs" and then adds it the BatchContractSetup.
		 * 
		 * Having examined the XML, XMLList, and XMLListCollection classes, I can not find an elegate
		 * way of doing this as of this writing. XML has an "appendChild()" function, but no "removeChild()" function as one 
		 * would have expected. Instead, I found the index first and then removed it.
		 */
		private function transferFromTo(agentSnapshotVO:XML):void
		{			
			// Remove from the CandidateAgentHierarchies
			var candidateAgentHierarcyVOs:XMLListCollection = SEGModelLocator.getInstance().candidateAgentHierarchyVOs;

			var agentSnapshotVOIndex:int = candidateAgentHierarcyVOs.getItemIndex(agentSnapshotVO);
			
			var removedAgentSnapshotVO:Object = candidateAgentHierarcyVOs.removeItemAt(agentSnapshotVOIndex);
			
			// Add to the SelectedCandidateAgentHierarchies
			var selectedCandidateAgentHierarchyVOs:XMLListCollection = SEGModelLocator.getInstance().selectedCandidateAgentHierarchyVOs;
			
			selectedCandidateAgentHierarchyVOs.addItem(removedAgentSnapshotVO);			
		}
	}
}