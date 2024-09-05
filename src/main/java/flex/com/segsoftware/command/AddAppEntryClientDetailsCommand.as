package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.SEGModelLocator;
	import mx.collections.XMLListCollection;
	import mx.collections.ArrayCollection;
	import com.segsoftware.model.SEGModelBuilder;
	
	/**
	 * While building each Segment in the AppEntry phase, it is necessary to add 
	 * ContractClients by first identifying the ClientDetail(s).
	 * 
	 * From these ClientDetails, we will ultimately identify the ClientRoles and ContractClients.
	 */ 
	public class AddAppEntryClientDetailsCommand extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{
			var selectedAppEntryClientDetailVOs:Array = event.formData.selectedAppEntryClientDetailVOs;
			
			for each (var selectedAppEntryClientDetailVO:XML in selectedAppEntryClientDetailVOs)
			{
				var appEntryClientDetailUIVO:XML = SEGModelBuilder.getInstance().buildAppEntryClienDetailUIVO(selectedAppEntryClientDetailVO);
				
				SEGModelLocator.getInstance().appEntryClientDetailUIVOs.addItem(appEntryClientDetailUIVO);	
			}		 
		}		
	}
}