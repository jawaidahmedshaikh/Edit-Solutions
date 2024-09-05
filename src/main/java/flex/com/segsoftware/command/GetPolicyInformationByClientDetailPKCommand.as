package com.segsoftware.command
{
	import com.segsoftware.event.*;
	import mx.rpc.events.*;
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.utility.Util;
	import com.segsoftware.model.SEGModelBuilder;
	import com.segsoftware.model.SEGModelLocator;
	import mx.collections.XMLListCollection;
	
	/**
	 * When adding a Client during AppEntry, the user may want to see all of the associated
	 * policies and some general information about those policies in order to appropriately
	 * choose the Client.
	 */ 
	public class GetPolicyInformationByClientDetailPKCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Policy Information for ClientDetail";
		
		override public function executeCommand(event:SEGEvent):void
		{
			// Clear any previous entries.
			SEGModelLocator.getInstance().searchedForPolicyInformationVOs.removeAll();
			
			var clientDetailVO:XML = event.formData.selectedAppEntryClientDetailVO as XML;
			
			var clientDetailPK:XML = clientDetailVO.ClientDetailPK[0] as XML;
			
			new GroupDelegate(result, fault, Util.convertToXMLListCollection(clientDetailPK)).getPolicyInformationByClientDetailPK();				
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var policyInformationVOs:XMLList = resultEvent.result.SegmentVO;
			
			SEGModelLocator.getInstance().searchedForPolicyInformationVOs = new XMLListCollection(policyInformationVOs);			
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}
	}
}