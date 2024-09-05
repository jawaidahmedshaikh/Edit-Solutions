package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.utility.Util;
	import com.segsoftware.model.SEGModelLocator;
	import mx.rpc.events.*;
	import mx.collections.XMLListCollection;
	
	
	public class GetClientsByNameTaxIdDOBCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Clients by Name/TaxID/Date of Birth";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var segResponseVO:XML = null;
			
			var clientName:XML = Util.initXML(event.formData.clientName, null, "Name");
			
			var taxId:XML = Util.initXML(event.formData.taxId, null, "TaxID");
			
			var dateOfBirth:XML = Util.initXML(event.formData.dateOfBirth, null, "DateOfBirth");
			
			var groupDelegate:GroupDelegate = new GroupDelegate(null, null, null);
			
			var parameters:XMLListCollection = null;
			
			// Case: Client Name and DOB supplied
			if ((clientName != null) && (dateOfBirth != null))
			{
				parameters = Util.convertToXMLListCollection(clientName, dateOfBirth);
				
				new GroupDelegate(result, fault, parameters).getClientsByNameAndDateOfBirth();
			}
			
			// Case: Client Name supplied
			else if (clientName != null)
			{
				parameters = Util.convertToXMLListCollection(clientName);
				
				new GroupDelegate(result, fault, parameters).getClientsByName();
			}
			
			// Case: TaxId supplied
			else if (taxId != null)
			{
				parameters = Util.convertToXMLListCollection(taxId);
				
				new GroupDelegate(result, fault, parameters).getClientsByTaxID();
			}
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			SEGModelLocator.getInstance().searchedForAppEntryClientDetailVOs.removeAll();
			
			var clientDetailVOs:XMLList = resultEvent.result.ClientDetailVO;
			
			SEGModelLocator.getInstance().searchedForAppEntryClientDetailVOs = new XMLListCollection(clientDetailVOs);			
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}
	}
}