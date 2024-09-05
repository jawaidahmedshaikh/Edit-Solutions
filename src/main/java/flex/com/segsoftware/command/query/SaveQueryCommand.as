package com.segsoftware.command.query
{
	import com.segsoftware.business.QueryDelegate;
	import com.segsoftware.command.SEGCommand;
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.query.*;
	
	import mx.collections.XMLListCollection;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	/**
	 * User wishes to add a new Query to the system.
	 */ 
	public class SaveQueryCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Save Query"; 

		private var callingComponent:Object = null;
		
		override public function executeCommand(event:SEGEvent):void
		{
			this.callingComponent = event.caller;
			
			var query:Query = event.formData as Query;
			
			var queryXML:XML = query.marshal();
			
			var parameters:XMLListCollection = new XMLListCollection();
			
			parameters.addItem(queryXML);
			
			new QueryDelegate(result, fault, parameters).saveQuery();		
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			super.result(resultEvent, EVENT_NAME, token);	
			
			this.callingComponent.updateAfterSave();																							
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME, token); 
			
			trace(faultEvent.message);
		}	
	}
}