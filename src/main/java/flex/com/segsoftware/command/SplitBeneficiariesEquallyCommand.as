package com.segsoftware.command
{
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.event.SEGEvent;
	import mx.collections.XMLListCollection;
	
	
	
	/**
	 * During AppEntry, the user may have selected more than one Beneficiary 
	 * in the selected Roles. Instead of dictating what the split should be for
	 * each Bene, the user can select that all Benes should be split equally by
	 * passing this indicator to the backend.
	 */ 
	public class SplitBeneficiariesEquallyCommand extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{
			var appEntryClientDetailUIVOs:XMLListCollection = SEGModelLocator.getInstance().appEntryClientDetailUIVOs;
			
			var object:Object = SEGModelLocator.getInstance().appEntryClientDetailUIVOs;
			
			for each (var appEntryClientDetailUIVO:XML in appEntryClientDetailUIVOs)
			{
				for each (var clientRoleUIVO:XML in appEntryClientDetailUIVO.SelectedClientRoleUIVOs.ClientRoleUIVO)
				{
					clientRoleUIVO.BeneficiaryAllocation = "";
					
					clientRoleUIVO.BeneficiaryAllocationType = "";
				}		
			}
		}
	}
}