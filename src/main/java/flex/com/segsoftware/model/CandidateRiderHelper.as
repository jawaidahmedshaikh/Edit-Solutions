package com.segsoftware.model
{
	/**
	 * During AppEntry, the user will select riders for the Segment as
	 * "CandidateRiderVOs".
	 */ 
	public class CandidateRiderHelper
	{
		/**
		 * Determines whether a candidate rider received from the back end should
		 * be automatically "selected" or not.  If the "RequiredOptionalCT" is Required,
		 * it means that this rider must be generated for the soon-to-be-created contract.
		 * Therefore, the required riders are moved over to the selectedCandidateRider list.
		 * 
		 * The move is performed by the same function that is called when the user hit the
		 * "move from candidate to selected" button. 
		 * 
		 * Technically, the candidateRider is not "moved" since the candidate rider list 
		 * always shows all candidates (a change made when additional riders were added to 
		 * the system).
		 */ 
		public static function sortByRequiredOptionalCT(candidateRiderVO:XML):void
		{
			var requiredOptionalCT:String = candidateRiderVO.RequiredOptionalCT[0];
			
			if (requiredOptionalCT == "Required")
			{
				selectCandidateRider(candidateRiderVO);
			}
			
			SEGModelLocator.getInstance().candidateCandidateRiderVOs.addItem(candidateRiderVO);					
		}
		
		/**
		 * CandidateRiderVO.RequiredOptionalCT = "Required" can [never] be deselected.
		 * This checks the RequiredOptionalCT and prevents "Required" ones from being 
		 * deselected.
		 * UPDATE: We removed this requirement per request of Vision.
		 */ 
		public static function deselectCandidateRider(clientSegment:ClientSegment):void
		{
			var requiredOptionalCT:String = clientSegment.candidateRiderVO.RequiredOptionalCT[0];
			
			//if (requiredOptionalCT != CodeTable.CODETABLENAME_REQUIREDOPTIONAL_REQUIRED)
			//{
			var index:int = SEGModelLocator.getInstance().clientSegments.getItemIndex(clientSegment);
				
			// Remove its corresponding ClientSegment 
			SEGModelLocator.getInstance().clientSegments.removeItemAt(index);
			//}
			
			//	Force a refresh on the clientSegments because the display won't refresh if the RiderClientDialog 
			//  has been brought up to set one of these clientSegment.  It's a stupid bug in Flex 2 only and this 
			//  is the only way we can find to get around the problem.
			SEGModelLocator.getInstance().clientSegments.refresh();	
		}	
		
		/**
		 * "Candidate" CandidateRiders can be moved over to the "Selected" CandidateRider collection.
		 */ 
		public static function selectCandidateRider(candidateRiderVO:XML):void
		{
			var newCandidateRiderVO:XML = candidateRiderVO.copy();
			
			// Create ClientSegment object to allow association of a client and gio option to the rider
			var clientSegment:ClientSegment = new ClientSegment();
				
			clientSegment.candidateRiderVO = newCandidateRiderVO;
			
			SEGModelLocator.getInstance().clientSegments.addItem(clientSegment);	
			
			//	Force a refresh on the clientSegments because the display won't refresh if the RiderClientDialog 
			//  has been brought up to set one of these clientSegment.  It's a stupid bug in Flex 2 only and this 
			//  is the only way we can find to get around the problem.
			SEGModelLocator.getInstance().clientSegments.refresh();	
		}
	}
}