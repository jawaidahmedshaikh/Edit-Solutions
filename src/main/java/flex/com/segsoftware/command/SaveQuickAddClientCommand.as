package com.segsoftware.command
{
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.event.*;
	import mx.rpc.events.*;
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.utility.Util;
	import com.segsoftware.model.SEGModelBuilder;
	import com.segsoftware.model.logging.SEGLog;
	import mx.collections.XMLListCollection;
	
	/**
	 * The user could not find the client they wanted while in the process
	 * of AppEntry. The system allows for the addition of a minimal client
	 * consisting of a ClientDetail and a ClientAddress.
	 */ 
	public class SaveQuickAddClientCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Save QuickAdd Client";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var clientDetailVO:XML = SEGModelLocator.getInstance().quickAddClientDetailVO;
			
			var clientAddressVO:XML = SEGModelLocator.getInstance().quickAddClientAddressVO;
			
			var taxInformationVO:XML = SEGModelLocator.getInstance().quickAddTaxInformationVO;
			
			new GroupDelegate(result, fault, Util.convertToXMLListCollection(clientDetailVO, clientAddressVO, taxInformationVO)).quickAddClient();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var responseMessageVOs:XMLListCollection = new XMLListCollection(resultEvent.result.ResponseMessageVO);
			
			if (Util.isSuccess(responseMessageVOs))
			{
				var clientDetailVO:XML = resultEvent.result.ClientDetailVO[0];
				
				// Add the result to the search results for the user
				SEGModelLocator.getInstance().searchedForAppEntryClientDetailVOs.removeAll();
				
				if (clientDetailVO != null) 
				{
					// A clientDetail was saved, add it to the model
					SEGModelLocator.getInstance().searchedForAppEntryClientDetailVOs.addItem(clientDetailVO);			
				}
				
				// Set page state to disabled
				SEGModelLocator.getInstance().clientSelectDialog.currentState = "";
			}
			else
			{
				//	Set page state to enabled
				SEGModelLocator.getInstance().clientSelectDialog.currentState = "quickAdd";
			}
			
			// Clear the newValueObjectVOs.
			delete SEGModelLocator.getInstance().newValueObjectVO.ClientDetailVO;
			
			delete SEGModelLocator.getInstance().newValueObjectVO.ClientAddressVO;
			
			delete SEGModelLocator.getInstance().newValueObjectVO.TaxInformationVO;			
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			//	Set page state to enabled.  Even though it's a "bad" error, it may just be an "unexpected" one on the back end
			SEGModelLocator.getInstance().clientSelectDialog.currentState = "quickAdd";
			
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}
	}
}