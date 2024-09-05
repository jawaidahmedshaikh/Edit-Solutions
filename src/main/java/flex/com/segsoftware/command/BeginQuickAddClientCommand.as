package com.segsoftware.command
{
	import com.segsoftware.model.SEGModelBuilder;
	import com.segsoftware.model.SEGModelLocator;
	import mx.rpc.events.*;
	import com.segsoftware.event.*;
	import mx.collections.*;
	import com.segsoftware.utility.Util;
	import com.segsoftware.business.SEGMetaDataDelegate;
	
	/**
	 * During AppEntry, the user may with to add a minimal ClientDetail to the system.
	 * This begins by creating ClientDetailVO and ClientAddressVO documents to the model
	 * to be saved on the back end.
	 */ 
	public class BeginQuickAddClientCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Initializing page for Client Quick Add";
		
		override public function executeCommand(event:SEGEvent):void
		{
			// Remove any previous entries.
			SEGModelLocator.getInstance().quickAddClientDetailVO = null;
			
			SEGModelLocator.getInstance().quickAddClientAddressVO = null;
			
			SEGModelLocator.getInstance().quickAddTaxInformationVO = null;
			
			var requestParameters:XMLListCollection = Util.convertToXMLListCollection(Util.buildXMLElement("TableName", SEGModelLocator.MODEL_CLIENTDETAIL));
			
			new SEGMetaDataDelegate(clientDetailresult, fault, requestParameters).buildDefaultValueObjectVO();
			
			requestParameters = Util.convertToXMLListCollection(Util.buildXMLElement("TableName", SEGModelLocator.MODEL_CLIENTADDRESS));
			
			new SEGMetaDataDelegate(clientAddressResult, fault, requestParameters).buildDefaultValueObjectVO();		
			
			requestParameters = Util.convertToXMLListCollection(Util.buildXMLElement("TableName", SEGModelLocator.MODEL_TAXINFORMATION));
			
			new SEGMetaDataDelegate(taxInformationResult, fault, requestParameters).buildDefaultValueObjectVO();					
		}
		
		public function clientDetailresult(resultEvent:ResultEvent, eventName:String=null, token:Object=null):void
		{
			var clientDetailVO:XML = resultEvent.result.ClientDetailVO[0];
			
			SEGModelLocator.getInstance().quickAddClientDetailVO = clientDetailVO;
			
			//super.result(resultEvent, EVENT_NAME);		
		}
		
		public function clientAddressResult(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var clientAddressVO:XML = resultEvent.result.ClientAddressVO[0];
			
			SEGModelLocator.getInstance().quickAddClientAddressVO = clientAddressVO;	
			
			//super.result(resultEvent, EVENT_NAME);			
		}	
		
		public function taxInformationResult(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var taxInformationVO:XML = resultEvent.result.TaxInformationVO[0];
			
			SEGModelLocator.getInstance().quickAddTaxInformationVO = taxInformationVO;
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			//super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);	
		}
	}
}