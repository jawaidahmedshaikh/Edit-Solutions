package com.segsoftware.command
{
	import com.segsoftware.business.*;
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.events.*;
	import com.segsoftware.model.*;
	import mx.collections.XMLListCollection;
	import flash.events.EventDispatcher;
	import mx.logging.*;
	import com.segsoftware.model.scriptProcessor.*;
	import mx.collections.ArrayCollection;

		
	public class GetScriptProcessorResults extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Script Processor Results";
		
		override public function executeCommand(event : SEGEvent): void
		{
			new PRASEDelegate(result, fault, null).getScriptProcessorResults();
		}	 
				
        override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
        {
        	// Empty the current list
        	SEGModelLocator.getInstance().spResults.removeAll();
        	
        	var spResults:ArrayCollection = new ArrayCollection();
        	
        	var spRecordedResults:XMLList = resultEvent.result.SPRecordedResults;
        	
        	var spRecordedResult:XML = spRecordedResults[0] as XML;
        	
        	var spRecordedOperators:XMLList = spRecordedResult.SPRecordedOperator;
			
			for (var i:int = 0; i < spRecordedOperators.length(); i++)
			{
				var spRecordedOperator:SPRecordedOperator = new SPRecordedOperator();
				
				var spRecordedOperatorXML:XML = spRecordedOperators[i] as XML;
				
				spRecordedOperator.unmarshal(spRecordedOperatorXML);
				
				// Add each one directly to SEGModelLocator to trigger a collection change event
				SEGModelLocator.getInstance().spResults.addItem(spRecordedOperator);
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