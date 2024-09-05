package com.segsoftware.command
{
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.*;
	import com.segsoftware.utility.Util;
	
	import mx.collections.XMLListCollection;
	import mx.logging.*;
	import mx.rpc.events.*;
		
	public class GetRatedGenderFromCaseProductUnderwritingCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Show All RatedGenderFromCaseProduct";
		
		override public function executeCommand(event : SEGEvent): void
		{
			 
		var selectedBatchContractSetupVO:XML = SEGModelLocator.getInstance().selectedBatchContractSetupVO;
			var BatchContractSetupPK = selectedBatchContractSetupVO.BatchContractSetupPK[0];
			 
			if (BatchContractSetupPK != null)
			{
				var batchContractSetupPK:XML = Util.buildXMLElement("BatchContractSetupPK", BatchContractSetupPK); 
				
				new GroupDelegate(result, fault, Util.convertToXMLListCollection(batchContractSetupPK)).getRatedGenderFromCaseProduct();
			}			
		}	 
				
        override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
        {
        	 BatchContractSetupHelper.clearBatchContractSetup();
        	
        	 
        	var caseProductUnderwritingVOs:XMLList = resultEvent.result.CodeTableVO;
        	
        	if (caseProductUnderwritingVOs.length() > 0)
        	{
        		CodeTable.getInstance().ratedGenderFromCaseProductVOs = new XMLListCollection(caseProductUnderwritingVOs);
        	} else
        	{
        		var foo:Object = CodeTable.getInstance().ratedGenderCodeTableVOs;
        		
        		CodeTable.getInstance().ratedGenderFromCaseProductVOs = CodeTable.getInstance().ratedGenderCodeTableVOs;
        	}
        	
        	
        	super.result(resultEvent, EVENT_NAME);
        }
        
        override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
        {	
        	super.fault(faultEvent, EVENT_NAME);
        	
			trace(faultEvent.message);
        }
	}
}