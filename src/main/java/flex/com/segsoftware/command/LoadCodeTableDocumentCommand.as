package com.segsoftware.command
{
	import com.segsoftware.model.SEGModelLocator;
	import mx.rpc.events.*;
	import com.segsoftware.business.CodeTableDelegate;
	import com.segsoftware.event.SEGEvent;
	import mx.collections.XMLListCollection;
	import com.segsoftware.model.CodeTable;
	
	/**
	 * Loads the entire CodeTableDef/CodeTable document into memory.
	 */ 
	public class LoadCodeTableDocumentCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Load CodeTable Document";
		
		override public function executeCommand(event:SEGEvent):void
		{
			new CodeTableDelegate(result, fault, null).getCodeTableDocument();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{			
			var codeTableDefVOs:XMLList = resultEvent.result.CodeTableDefVO;
			
			for each (var codeTableDefVO:XML in codeTableDefVOs)
			{
				CodeTable.getInstance().addCodeTableDef(codeTableDefVO);				
			}
			
			//super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{		
			//super.fault(faultEvent, EVENT_NAME);
				
			trace(faultEvent.message);
		}
	}
}