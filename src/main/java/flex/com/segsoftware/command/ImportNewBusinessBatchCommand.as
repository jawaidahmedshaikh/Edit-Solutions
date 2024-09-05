package com.segsoftware.command
{
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.*;
	import com.segsoftware.model.BatchContractSetupHelper;
	import com.segsoftware.utility.Util;
	import com.segsoftware.view.custom.SEGFileUpload;
	
	import flash.events.EventDispatcher;
	import flash.net.FileReference;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	
	import mx.collections.XMLListCollection;
	import mx.rpc.events.*;
	import flash.events.Event;
		
	/**
	 * Instead of manually entering information for the BatchSetup/AppEntry process, users
	 * can import a file that contains the necessary information to do the same thing.
	 */ 
	public class ImportNewBusinessBatchCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Import New Business";
		public static var IMPORT_ISSUED_EVENT:String = "NB_IMPORT_ISSUED";
		
		public static var dispatcher:EventDispatcher = new EventDispatcher();
		
		override public function executeCommand(event:SEGEvent):void
		{			
			if (SEGModelLocator.getInstance().selectedBatchContractSetupVO != null)
			{
				var fileRef:FileReference = event.formData as FileReference;
				GroupDelegate.performBatchImport(fileRef);
				dispatcher.dispatchEvent(new Event(IMPORT_ISSUED_EVENT));
			}
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