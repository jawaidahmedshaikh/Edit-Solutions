package com.segsoftware.command
{
	import com.segsoftware.model.SEGModelLocator;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import com.segsoftware.business.PRASEDelegate;
	import com.segsoftware.utility.Util;
	import com.segsoftware.event.SEGEvent;
	
	/**
	 * PRASETests that include a Driver, and its associated input documents can be
	 * run for the purpose of comparing previous "expected" results with
	 * the just executed "expected" results.
	 */ 
	public class RunPRASETestCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Run PRASETest";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var selectedPRASETestVO:XML = SEGModelLocator.getInstance().selectedPRASETestVO;
			
			var praseTestPK:XML = selectedPRASETestVO.PRASETestPK[0];
			
			new PRASEDelegate(result, fault, Util.convertToXMLListCollection(praseTestPK)).runPRASETest();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var differenceReportVO:XML = resultEvent.result.DifferenceReportVO[0];
			
			SEGModelLocator.getInstance().actualPRASEOutputXML = differenceReportVO.ActualPRASEOutputXML[0].SPOutputVO[0];
			
			SEGModelLocator.getInstance().differenceXML = differenceReportVO.DifferenceXML[0];
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}
	}
}