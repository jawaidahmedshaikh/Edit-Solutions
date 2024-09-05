package com.segsoftware.command
{
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.utility.Util;
	import mx.rpc.events.*;
	import com.segsoftware.event.*;
	import mx.collections.XMLListCollection;
	
	/**
	 * When setting up a BatchContractSetup, the user will select an EnrollmentType
	 * from the collection of available EnrollmentTypes.
	 */ 
	public class GetCandidateEnrollmentsCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Candidate Enrollments";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var selectedBatchContractSetupVO:XML = SEGModelLocator.getInstance().selectedBatchContractSetupVO;
			
			var batchContractSetupPK:XML = selectedBatchContractSetupVO.BatchContractSetupPK[0];
			
			new GroupDelegate(result, fault, Util.convertToXMLListCollection(batchContractSetupPK)).getCandidateEnrollments();			
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			SEGModelLocator.getInstance().candidateEnrollmentVOs.removeAll();
			
			var candidateEnrollmentsVOs:XMLList = resultEvent.result.EnrollmentVO;
			
			SEGModelLocator.getInstance().candidateEnrollmentVOs = new XMLListCollection(candidateEnrollmentsVOs);			
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}
	}
}