package com.segsoftware.model.logging
{
	import mx.collections.ArrayCollection;
	import mx.rpc.events.*;
	import mx.collections.XMLListCollection;
	import com.segsoftware.utility.Util;
	import mx.core.UIComponent;
	import mx.managers.PopUpManager;
	import flash.display.DisplayObject;
	import com.segsoftware.view.custom.logging.SEGLogMessageDisplayDialog;
	import com.segsoftware.model.logging.*;
	
	
	
	/**
	 * SEGLog logs all user activity.
	 * 
	 * The following information is somewhat obsolete since we are no longer using Flex's Log mechanism:
	 * 
	 * The SEGLog's root is "com.segsoftware.*", so any log used has to be relative
	 * to that package name. For example:
	 * 
	 * 	Log.getLogger("com.segsoftware.myLog").info("my message");	// an acceptable log
	 *  
	 *  Log.getLogger("com.myLog").info("my message");	// not an acceptable log
	 */
	[Bindable] 
	public class SEGLog extends UIComponent	
	{
		/**
		 * A reference to the singleton.
		 */ 
		//[Bindable]
		public static var segLog:SEGLog;
		
		/**
		 * The log used to track user gesture's (typically the result/fault of any Command object).
		 */ 
		public static var LOG_USER_ACTIVITY:String = "com.segsoftware.userActivity";
		
		/**
		 * The root log of the SEGLog. All other logs must be relative to this
		 * otherwise they log messages will simply be ignored. In reality, this
		 * simply represents the root of a "filter".
		 */ 
		public static var LOG_ROOT_LOGGER:String = "com.segsoftware.*";
		
		/**
		 * Clients to this logger may want to be notified of additions
		 * to the log events.
		 * 
		 * Array of SEGLogEntry objects
		 */ 
		//[Bindable]		
	    public var _segLogEntries:ArrayCollection = new ArrayCollection();
		
		/**
		 * The last event received. The goal is to offer feedback to the user
		 * for their last action. It is possible that this is too course-grained and we 
		 * may want to user finer-grained LogEvents sorted by the individual logs
		 * that generated them.
		 */
		//[Bindable]
	 	public var _lastSEGLogEntry:SEGLogEntry;	
	 	
	 	/**
	 	 * Single message containing specific business information.  This is separate from 
	 	 * the on-going log.  The on-going log tracks are user interaction and the results.
	 	 * The business message is information the user specifically wants to or needs to see
	 	 * at certain times.  It can be cleared on-demand
	 	 */
	 	public var _businessMessage:String;
	 	
	 	
	 	
		/**
		 * Constructor
		 * 
		 * Sets the static segLog variable to this object.  This may seem strange but it is for
		 * a good reason.  Binding for static variables only occurs upon start up of the application.  If
		 * the value for that variable changes (for example, from null to an instance) AFTER the binding 
		 * has been set up, the change does not get picked up by objects bound to it.  The SEGLogButton
		 * is bound to segLog.  It gets created before the main application and it needs the value of
		 * segLog upon creation.  The main application automatically calls SEGLog constructor - we
		 * don't want it creating a new instance there because it is too late.  SEGLogButton would have
		 * already been created with a null and the binding is already set.
		 */ 
		public function SEGLog()
		{
			segLog = this;
		}
		
		public static function getInstance():SEGLog
		{
			/*
			if (segLog == null)
			{
				segLog = new SEGLog();
			}									
			*/
			return segLog;
		}
		
		public static function setInstance(segLogInstance:SEGLog):void
		{
			segLog = segLogInstance;
		}
		
		public function get segLogEntries():ArrayCollection
		{
			return this._segLogEntries;
		}
		
		public function set segLogEntries(segLogEntries:ArrayCollection):void
		{
			this._segLogEntries = segLogEntries;
		}
		
		public function set lastSEGLogEntry(lastSEGLogEntry:SEGLogEntry):void
		{
			this._lastSEGLogEntry = lastSEGLogEntry;
		}
		
		public function get lastSEGLogEntry():SEGLogEntry
		{
			return this._lastSEGLogEntry;
		}
		
		public function set businessMessage(businessMessage:String):void
		{
			this._businessMessage = businessMessage;
		}
		
		public function get businessMessage():String
		{
			return this._businessMessage;
		}
		
		
		/**
		 * Creates a SEGLogEntry for the command just executed and adds each 
		 * responseMessage to it.  The SEGLogEntry is then added to the segLogEntries
		 * list.
		 */
		public function logResultEvent(resultEvent:ResultEvent, className:String, timeStamp:String, eventName:String):void
	    {
			var responseMessages:XMLList = resultEvent.result.ResponseMessageVO;
        	
  		    var responseMessagesCollection:XMLListCollection  = new XMLListCollection(responseMessages);
			
			var segLogEntry:SEGLogEntry = new SEGLogEntry(className, timeStamp, eventName);
			
			//	Add each response message to the segLogEntry
			for (var i:int = 0; i < responseMessagesCollection.length; i++)
			{
				var responseMessage:XML = responseMessagesCollection.getItemAt(i) as XML;
			
				segLogEntry.addLogMessage(responseMessage.Message, responseMessage.MessageType);
	  		}
	  		
	  		//	Set the latest one for display on the log button
			lastSEGLogEntry = segLogEntry;
			
	  		//	Add the SEGLogEntry to the list
			segLogEntries.addItem(segLogEntry);

	  		//	If any of the messages is anything but a success, pop up the log message dialog
	  		if (!Util.isSuccess(responseMessagesCollection))
			{
				popUpMessageDialog();
			}
		}
		
		public function logFaultEvent(faultEvent:FaultEvent, className:String, timeStamp:String, eventName:String):void
		{
			var segLogEntry:SEGLogEntry = new SEGLogEntry(className, timeStamp, eventName);
			
			//	Add the faultEvent's message to the segLogEntry
			segLogEntry.addLogMessage(faultEvent.message.body.toString(), SEGLogMessageType.MESSAGE_TYPE_ERROR);
	  		
	  		//	Set the latest one for display on the log button
			lastSEGLogEntry = segLogEntry;
			
	  		//	Add the SEGLogEntry to the list
			segLogEntries.addItem(segLogEntry);
			
			//	Always pop up the log because faults are always errors
			popUpMessageDialog();
		}
		
		/**
		 * Sets the businessMessage.  Yes, it does the same thing as set but it may
		 * do more at some point and it is more consistent with other logging functions
		 * (like logResultEvent)
		 */
		public function logBusinessMessage(businessMessage:String):void
		{
			this.businessMessage = businessMessage;
		}
		
		/**
		 * Clears the contents of the businessMessage.  Yes, we could just call the
		 * set method but we may need to do more at some point and "clear" was "clearer" :)
		 */
		public function clearBusinessMessage():void
		{
			this.businessMessage = null;
		}
		
		public function clearLog():void
		{
			this._segLogEntries.removeAll();
		}
		
		private function popUpMessageDialog():void
		{
			var segLogMessageDisplayDialog:SEGLogMessageDisplayDialog = 
				PopUpManager.createPopUp(this.parentApplication as DisplayObject, com.segsoftware.view.custom.logging.SEGLogMessageDisplayDialog, true) as SEGLogMessageDisplayDialog;	
						
			PopUpManager.centerPopUp(segLogMessageDisplayDialog);
		}
	}
}