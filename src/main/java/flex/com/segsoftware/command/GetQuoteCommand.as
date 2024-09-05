package com.segsoftware.command
{
	import com.segsoftware.event.*;
	import mx.rpc.events.*;
	import mx.collections.XMLListCollection;
	import com.segsoftware.model.*;
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.model.logging.*;
	
	/**
	 * Before saving a new policy in AppEntry, the user may wish to generate a quote with the 
	 * available information.
	 */ 
	public class GetQuoteCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Quote";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var parameters:XMLListCollection = SegmentHelper.generateAppEntryRequest();
			
			new GroupDelegate(result, fault, parameters).getQuote();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var annualPremium:String = resultEvent.result.AnnualPremium[0];
			
			var deductionAmount:String = resultEvent.result.DeductionAmount[0];
			
			var quoteVO:XML = SEGModelBuilder.getInstance().buildQuoteVO(annualPremium, deductionAmount);
			
			SEGModelLocator.getInstance().quoteVO = quoteVO;
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}
	}
}