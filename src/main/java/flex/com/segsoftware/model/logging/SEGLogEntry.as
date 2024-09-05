package com.segsoftware.model.logging
{
	import mx.collections.ArrayCollection;
	
	
	/**
	 * A single entry in the log.  An entry contains a message and a messageType.  
	 * The messageType is a level of success or failure.  The messageColor is the color associated 
	 * with the messageType.
	 */ 
	public class SEGLogEntry
	{
		private var _command:String;
		private var _timeStamp:String;
		private var _eventName:String;
		private var _segLogMessages:ArrayCollection = new ArrayCollection();	// array of SEGLogMessages
		
		/**
		 * The SEGLogMessage within segLogMessages that has the highest level of alert
		 */ 
		private var _highestLevelSEGLogMessage:SEGLogMessage;
		
	
		/**
		 * Constructor
		 */
		public function SEGLogEntry(command:String, timeStamp:String, eventName:String)
		{
			this.command = command;
			this.timeStamp = timeStamp;
			this.eventName = eventName;
		}
		
		public function addLogMessage(message:String, messageType:String):void
		{
			var segLogMessage:SEGLogMessage = new SEGLogMessage(message, messageType);
			
			this._segLogMessages.addItem(segLogMessage);
			
			determineHighestLevelSEGLogMessage(segLogMessage);
		}
		
		
		/**
		 * @see #_command
		 */ 
		[Bindable]
		public function set command(command:String):void
		{
			_command = command;
		}

		/**
		 * @see #_command
		 */ 		
		public function get command():String
		{
			return _command;	
		}
		
		/**
		 * @see #_timeStamp
		 */ 
		[Bindable]
		public function set timeStamp(timeStamp:String):void
		{
			_timeStamp = timeStamp;
		}

		/**
		 * @see #_timeStamp
		 */ 		
		public function get timeStamp():String
		{
			return _timeStamp;	
		}
		
		/**
		 * @see #_eventName
		 */ 
		[Bindable]
		public function set eventName(eventName:String):void
		{
			_eventName = eventName;
		}

		/**
		 * @see #_eventName
		 */ 		
		public function get eventName():String
		{
			return _eventName;	
		}
		
		public function get segLogMessages():ArrayCollection
		{
			return this._segLogMessages;
		}
		
		[Bindable]
		public function set segLogMessages(segLogMessages:ArrayCollection):void
		{
			this._segLogMessages = segLogMessages;
		}
		
		public function get highestLevelSEGLogMessage():SEGLogMessage
		{
			return this._highestLevelSEGLogMessage;
		}
		
		[Bindable]
		public function set highestLevelSEGLogMessage(highestLevelSEGLogMessage:SEGLogMessage):void
		{
			this._highestLevelSEGLogMessage = highestLevelSEGLogMessage;
		}
		
		/**
		 * Determines whether the specified segLogMessage has a higher level of alert than the currently
		 * set highestLevelSEGLogMessage.  If it does, the highestLevelSEGLogMessage is replaced
		 * with the specified segLogMessage.
		 */
		private function determineHighestLevelSEGLogMessage(segLogMessage:SEGLogMessage):void
		{
			if (this.highestLevelSEGLogMessage == null)
			{
				//	highestLevelSEGLogMessage hasn't been set yet, set it now
				this.highestLevelSEGLogMessage = segLogMessage;	
			}
			else
			{
				if (segLogMessage.isHigherLevel(this.highestLevelSEGLogMessage))
				{
					this.highestLevelSEGLogMessage = segLogMessage;	
				}
			}	
		}
	}
}