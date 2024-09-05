package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.*;
	
	import mx.collections.XMLListCollection;
	
	
	/**
	 * After committing an App in AppEntry, the user may wish to keep ALL of the existing
	 * information EXCEPT for the insured. This removes the insured from the list
	 * of selected Roles.
	 */  
	public class RemoveInsuredFromSelectedRolesCommand extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{
			var candidateClientRoleUIVOs:XMLListCollection = SEGModelLocator.getInstance().candidateClientRoleUIVOs;
			//var selectedClientRolesHolder:XMLListCollection = SEGModelLocator.getInstance().selectedClientRolesHolder;
			//for each (var selectedClientRole:XML in selectedClientRolesHolder) 
			//{ 
			 //   var index:int = candidateClientRoleUIVOs.getItemIndex(selectedClientRole);
			
			    //var removeRoleTypeCT:String = selectedClientRole.RoleTypeCode;
				//if (index >= 0) {
			     //   candidateClientRoleUIVOs.removeItemAt(index);
				//}
			//}
		}
	}
}