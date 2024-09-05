package com.segsoftware.command
{
	import com.segsoftware.event.*;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.utility.Util;
	
	import mx.collections.XMLListCollection;
	
	/**
	 * The user selects an AppEntryClientDetailUIVO. We need to display the CandidateClientRoleUIVOs and the
	 * SelectedClientRoleUIVOs which are nested in the selected AppEntryClientDetailUIVO.
	 * 
	 * I can't find any way of Binding these nested ClientDetailUIVOs which is likely to be expected. I
	 * just don't think you can Bind to children Elements deep into a document.
	 * 
	 * We can manually map the ClientDetailUIVOs to collections in the model to support Binding.
	 */  
	public class ShowClientRoleInformation extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{
			var selectedAppEntryClientDetailUIVO:XML = SEGModelLocator.getInstance().selectedAppEntryClientDetailVO;
			
			// Dereference to avoid changing the previously selected AppEntryClientDetailUIVO.
			var candidateClientRoleUIVOs:XMLList = selectedAppEntryClientDetailUIVO.child("CandidateClientRoleUIVOs").children();
			
			var selectedClientRoleUIVOs:XMLList = selectedAppEntryClientDetailUIVO.child("SelectedClientRoleUIVOs").children();			
			
			SEGModelLocator.getInstance().candidateClientRoleUIVOs = new XMLListCollection(candidateClientRoleUIVOs); 

			
			// use custom sort from java
        	//Util.sort(SEGModelLocator.getInstance().candidateClientRoleUIVOs, "RoleTypeDesc"); 
			
			SEGModelLocator.getInstance().selectedClientRoleUIVOs = new XMLListCollection(selectedClientRoleUIVOs);
			
			Util.sort(SEGModelLocator.getInstance().selectedClientRoleUIVOs, "RoleTypeDesc"); 			
//			removeSelectedRoles();
		}

//		public function removeSelectedRoles():void {
//			for each (var selectedRole:XML in SEGModelLocator.getInstance().selectedClientRolesHolder) {
//				var ccru:XMLListCollection = SEGModelLocator.getInstance().candidateClientRoleUIVOs;
 //  			    for each (var candidateRole:XML in SEGModelLocator.getInstance().candidateClientRoleUIVOs) {
//					if (selectedRole.RoleTypeDesc[0] == candidateRole.RoleTypeDesc[0]) {
//					   var index:int = SEGModelLocator.getInstance().candidateClientRoleUIVOs.getItemIndex(candidateRole);
//					   SEGModelLocator.getInstance().candidateClientRoleUIVOs.removeItemAt(index);

//					}

//				}
//			}
//		}	
	}
}