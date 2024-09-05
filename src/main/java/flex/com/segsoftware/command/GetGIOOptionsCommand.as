package com.segsoftware.command
{
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.*;
	import com.segsoftware.utility.Util;
	
	import mx.collections.XMLListCollection;
	import mx.logging.*;
	import mx.rpc.events.*;
		
	/**
	 * Gets the available GIO Options for display in the CoverageDialog
	 */
	public class GetGIOOptionsCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get GIO Options";
		
		override public function executeCommand(event : SEGEvent): void
		{
			var relationshipToEmployeeCT:XML = Util.buildXMLElement("RelationshipToEmployeeCT", event.formData.contractClient.getRelationshipToEmployeeCT());
			
			var batchContractSetupPK:XML = SEGModelLocator.getInstance().selectedBatchContractSetupVO.BatchContractSetupPK[0];
			
			var filteredProductPK:XML = Util.buildXMLElement("FilteredProductPK", SEGModelLocator.getInstance().segmentInformationVO.FilteredProductPK);
			
			var params:XMLListCollection = Util.convertToXMLListCollection(batchContractSetupPK, filteredProductPK, relationshipToEmployeeCT);
				
			new GroupDelegate(result, fault, params).getGIOOptions();	
		}	 
				
        override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
        {
        	// Empty the current list
        	SEGModelLocator.getInstance().gioOptions.removeAll();
        	
        	var gioOptions:XMLList = resultEvent.result.GIOOption;
        	
        	//	Add each item one at a time to trigger a collection change event for the CoverageDialog
        	for each (var gioOption:XML in gioOptions)
        	{
        		SEGModelLocator.getInstance().gioOptions.addItem(gioOption);
        	}
        	
        	//	Refresh the collection so the listener knows the collection is complete
        	SEGModelLocator.getInstance().gioOptions.refresh();
        	
        	super.result(resultEvent, EVENT_NAME);
        }
        
        override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
        {	
        	super.fault(faultEvent, EVENT_NAME);
        	
			trace(faultEvent.message);
        }
	}
}