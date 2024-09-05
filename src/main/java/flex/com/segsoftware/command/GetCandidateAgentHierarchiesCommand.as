package com.segsoftware.command
{
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.utility.Util;
	import com.segsoftware.event.*;
	import com.segsoftware.model.*
	import mx.rpc.events.*;
	import mx.collections.XMLListCollection;
	
	public class GetCandidateAgentHierarchiesCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Candidate AgentHierarchies"; 
		
		override public function executeCommand(event : SEGEvent): void
		{
			// Get the params...
			var selectedBatchContractSetupVO:XML = SEGModelLocator.getInstance().selectedBatchContractSetupVO;
			
			if (selectedBatchContractSetupVO)
			{
				var batchContractSetupPK:XML = selectedBatchContractSetupVO.BatchContractSetupPK[0];
				
				var enrollmentPK:XML = <EnrollmentPK>{selectedBatchContractSetupVO.EnrollmentFK[0].toString()}</EnrollmentPK>;
				
				new GroupDelegate(result, fault, Util.convertToXMLListCollection(batchContractSetupPK, enrollmentPK)).getCandidateAgentHierarchies();
			}
		}	
				
        override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
        {        	
        	var candidateAgentHierarchyVOs:XMLList = resultEvent.result.AgentSnapshotVO;
        	
			SEGModelLocator.getInstance().candidateAgentHierarchyVOs = new XMLListCollection(candidateAgentHierarchyVOs);        	
			
			super.result(resultEvent, EVENT_NAME);
        }
        
        override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
        {
        	super.fault(faultEvent, EVENT_NAME);
        	        	
			trace(faultEvent.message);
        }
	}
}