package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.ClientSegment;
	import com.segsoftware.model.SEGModelLocator;
	
	import mx.collections.XMLListCollection;
	
	/**
	 * From the set of Candidate ClientRoles, the user will establish the "selected" 
	 * Candidate ClientRoles. It is from this selected list that ContractClients will
	 * be built. 
	 */ 
	public class SelectCandidateClientRolesCommand extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{
			var highlightedCandidateClientRoleUIVOs:Array = event.formData.highlightedCandidateClientRoleUIVOs;
			
			for each (var highlightedCandidateClientRoleUIVO:XML in highlightedCandidateClientRoleUIVOs)
			{
				transferFromTo(highlightedCandidateClientRoleUIVO);				
			}
		}
		
		/**
		 * Transfers the "highlighted" candidate ClientRole to the "selected" ClientRole.
		 */
		private function transferFromTo(highlightedCandidateClientRoleUIVO:XML):void
		{
			// Remove from...
			var candidateClientRoleUIVOs:XMLListCollection = SEGModelLocator.getInstance().candidateClientRoleUIVOs;
			var index:int = candidateClientRoleUIVOs.getItemIndex(highlightedCandidateClientRoleUIVO);
			candidateClientRoleUIVOs.removeItemAt(index);

			// Add to...
			var selectedClientRoleUIVOs:XMLListCollection = SEGModelLocator.getInstance().selectedClientRoleUIVOs;
			selectedClientRoleUIVOs.addItem(highlightedCandidateClientRoleUIVO);
		}
	}
}