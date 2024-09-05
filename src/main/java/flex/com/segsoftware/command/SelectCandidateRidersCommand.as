package com.segsoftware.command
{
	import com.segsoftware.model.CandidateRiderHelper;
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.events.*;
	
	/**
	 * Moves the selected Candidate CandidateRiderVOs to the Selected CandidateRiderVOs collection.
	 */ 
	public class SelectCandidateRidersCommand extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{
			var highlightedCandidateCandidateRiderVOs:Array = event.formData.highlightedCandidateCandidateRiderVOs;
			
			for each (var highlightedCandidateCandidateRiderVO:XML in highlightedCandidateCandidateRiderVOs)
			{
				CandidateRiderHelper.selectCandidateRider(highlightedCandidateCandidateRiderVO);				
			}
			
			//	Unselect all selected rows
			event.formData.candidateCandidateRiderVOsDataGrid.selectedIndex = -1;		
		}
	}
}