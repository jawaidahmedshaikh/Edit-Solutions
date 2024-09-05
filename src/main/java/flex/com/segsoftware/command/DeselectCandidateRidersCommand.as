package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.events.*;
	import com.segsoftware.model.*;
	
	/**
	 * Moves the Selected CandidateRiderVOs (via ClientSegment) to the Candidate CandidateRiderVOs collection.
	 *  
	 * Not all moves are legal and the user is referred to the class(es) listed below:
	 * 
	 * @see CandidateRiderUtil
	 */ 
	public class DeselectCandidateRidersCommand extends SEGCommand
	{
		override public function executeCommand(event:SEGEvent):void
		{
			var highlightedClientSegments:Array = event.formData.highlightedClientSegments;
			
			for each (var highlightedClientSegment:ClientSegment in highlightedClientSegments)
			{
				CandidateRiderHelper.deselectCandidateRider(highlightedClientSegment);				
			}				
		}
	}
}