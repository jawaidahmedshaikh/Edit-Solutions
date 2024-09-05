package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.*;
	import mx.collections.XMLListCollection;
		
	/**
	 * The user has selected ClientRoles from the list of "Candidate" 
	 * ClientRoles. The user now wished to deselect these. The 
	 * highlighted "selected" ClientRoles will be removed from 
	 * the selected list and added back to the candidate list.
	 */ 
	public class DeselectCandidateClientRoles extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{
			var highlightedSelectedClientRoleUIVOs:Array = event.formData.highlightedSelectedClientRoleUIVOs;
			
			for each (var highlightedSelectedClientRoleUIVO:XML in highlightedSelectedClientRoleUIVOs)
			{
				transferFromTo(highlightedSelectedClientRoleUIVO);				
			}			
		}
		
		/**
		 * Transfers the "highlighted" selected ClientRole to the "candidate" ClientRole.
		 */
		private function transferFromTo(highlightedSelectedClientRoleUIVO:XML):void
		{	
			//Reset the state - the user may have modified it.
			ClientRoleHelper.resetCodeValues(highlightedSelectedClientRoleUIVO);
			
			// Remove from...
			var selectedClientRoleUIVOs:XMLListCollection = SEGModelLocator.getInstance().selectedClientRoleUIVOs;
			var index:int = selectedClientRoleUIVOs.getItemIndex(highlightedSelectedClientRoleUIVO);
			selectedClientRoleUIVOs.removeItemAt(index);
			
//			var index:int = SEGModelLocator.getInstance().selectedClientRolesHolder.getItemIndex(highlightedSelectedClientRoleUIVO)
//			SEGModelLocator.getInstance().selectedClientRolesHolder.removeItemAt(index);


			// Add to...
			var candidateClientRoleUIVOs:XMLListCollection = SEGModelLocator.getInstance().candidateClientRoleUIVOs;

			candidateClientRoleUIVOs.addItem(highlightedSelectedClientRoleUIVO);
		}		
	}
}