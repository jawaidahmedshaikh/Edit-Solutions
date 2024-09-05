package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.business.PRASEDelegate;
	import com.segsoftware.utility.Util;

	/**
	 * The user may wish to run a PRASETest and then set the actual results (just generated)
	 * to be the expected results. Next time the PRASETest is run, the actual results will
	 * be compared to these expected results.
	 */ 
	public class SetActualAsExpectedCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Set PRASETest Actual Results to Expected Results";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var selectedPRASETestVO:XML = SEGModelLocator.getInstance().selectedPRASETestVO;
			
			var praseTestPK:XML = selectedPRASETestVO.PRASETestPK[0];			
			
			var expectedPRASEOutputXML:XML = (selectedPRASETestVO.ExpectedPRASEOutputXML[0] as XML).copy();
			
			var spOutputVOString:String = expectedPRASEOutputXML;
			
			var spOutputVOXML:XML = new XML(spOutputVOString);
			
			new PRASEDelegate(result, fault, Util.convertToXMLListCollection(praseTestPK, spOutputVOXML)).updatePRASETestExpectedResults();			
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			super.result(resultEvent, EVENT_NAME);			
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}
	}
}