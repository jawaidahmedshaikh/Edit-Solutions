package com.segsoftware.model.logging
{
	public class SEGLogMessage
	{
		/**
		 * The text message 
		 */ 
		
		private var _message:String;
		
		/**
		 * The messageType 
		 */ 
		private var _segLogMessageType:SEGLogMessageType;
	
	
		public function SEGLogMessage(message:String, messageType:String)
		{
			this.message = message;
			
			this.segLogMessageType = new SEGLogMessageType(messageType);
		}
		
		/**
		 * @see #_message
		 */ 
		
		[Bindable]
		public function set message(message:String):void
		{
			_message = message;
		}

		/**
		 * @see #_message
		 */ 		
		public function get message():String
		{
			return _message;	
		}
		
		[Bindable]
		public function set segLogMessageType(segLogMessageType:SEGLogMessageType):void
		{
			_segLogMessageType = segLogMessageType;
		}

		/**
		 * @see #_segLogMessageType
		 */ 		
		public function get segLogMessageType():SEGLogMessageType
		{
			return _segLogMessageType;	
		}
		
		/**
		 * Determines if this object's message level is higher than the specified one
		 * 
		 * returns true if this object's is higher, false otherwise
		 */
		public function isHigherLevel(segLogMessage:SEGLogMessage):Boolean
		{
			var isHigherLevel:Boolean = false;
			
			if (this.segLogMessageType.isHigherLevel(segLogMessage.segLogMessageType))
			{
				isHigherLevel = true;
			}
			
			return isHigherLevel;
		}
	}
}