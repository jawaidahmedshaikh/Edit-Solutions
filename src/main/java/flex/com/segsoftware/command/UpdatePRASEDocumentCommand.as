package com.segsoftware.command
{
	import com.segsoftware.business.PRASEDelegate;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.view.custom.SEGFileUpload;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import com.segsoftware.utility.Util;
	
	/**
	 * Commits any changes made to the currently selected PRASEDocument. 
	 */
	public class UpdatePRASEDocumentCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Update PRASEDocument";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var praseDocument:XML = SEGModelLocator.getInstance().selectedPRASEDocumentVO;
			
			new PRASEDelegate(result, fault, Util.convertToXMLListCollection(praseDocument)).updatePRASEDocument();			
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