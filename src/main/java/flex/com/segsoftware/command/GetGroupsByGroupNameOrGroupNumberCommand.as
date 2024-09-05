package com.segsoftware.command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.event.*;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.utility.Util;
	
	import mx.collections.XMLListCollection;
	import mx.controls.DataGrid;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	public class GetGroupsByGroupNameOrGroupNumberCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Groups by GroupName or GroupNumber";
		
		override public function executeCommand(event:SEGEvent):void
		{	
			// Clear any previous search results
			SEGModelLocator.getInstance().searchedForGroupContractGroupVOs.removeAll();
			
			var groupName:String = Util.initString(event.formData.groupName, null);
			
			var groupNumber:String = Util.initString(event.formData.groupNumber, null);
			
			if (groupName != null)
			{
				var groupNameParameter:XML = Util.buildXMLElement("GroupName", groupName); 
				
				new GroupDelegate(result, fault, Util.convertToXMLListCollection(groupNameParameter)).getGroupContractGroupsByGroupName();
			}
			else if (groupNumber != null) 
			{
				var groupNumberParameter:XML = Util.buildXMLElement("ContractGroupNumber", groupNumber); 
				
				new GroupDelegate(result, fault, Util.convertToXMLListCollection(groupNumberParameter)).getGroupContractGroupByGroupNumber();
			}
		}
		
        override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
        {        	
        	var searchedForGroupContractGroupVOs:XMLList = resultEvent.result.ContractGroupVO;
        	
        	SEGModelLocator.getInstance().searchedForGroupContractGroupVOs = new XMLListCollection(searchedForGroupContractGroupVOs);
        	
        	super.result(resultEvent, EVENT_NAME);
        }
        
        override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
        {        	
        	super.fault(faultEvent, EVENT_NAME);
        	
			trace(faultEvent.message);
        }	
	}
}