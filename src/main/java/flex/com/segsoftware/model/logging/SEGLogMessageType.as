package com.segsoftware.model.logging
{
	import mx.controls.Image;
	
	public class SEGLogMessageType
	{
		private var _messageType:String;
		private var _messageLevel:int;
		private var _messageColor:uint;
		private var _messageIcon:Class;
	
	
		public static var MESSAGE_TYPE_SUCCESS:String = "Success"; 
		public static var MESSAGE_TYPE_WARNING:String = "Warning"; 
		public static var MESSAGE_TYPE_ERROR:String = "Error"; 
		
		public static var MESSAGE_LEVEL_SUCCESS:int = 0;
		public static var MESSAGE_LEVEL_WARNING:int = 1;
		public static var MESSAGE_LEVEL_ERROR:int = 2;
		
		public static var MESSAGE_COLOR_SUCCESS:uint = 0x00FF00;	//	Green #00FF00
		public static var MESSAGE_COLOR_WARNING:uint = 0xFFFF00;	//	Yellow #FFFF00
		public static var MESSAGE_COLOR_ERROR:uint   = 0xFF0000;	//	Red #FF0000
		
		[Embed(source="/com/segsoftware/view/assets/icons/bulletSuccess.png")] 
        public static var messageIcon_Success:Class;
	
		[Embed(source="/com/segsoftware/view/assets/icons/bulletWarning.png")] 
        public static var messageIcon_Warning:Class;
        
        [Embed(source="/com/segsoftware/view/assets/icons/bulletError.png")] 
        public static var messageIcon_Error:Class;
        
		public function SEGLogMessageType(messageType:String)
		{
		 	initialize(messageType);
		}
		
		
		
		[Bindable]
		public function set messageType(messageType:String):void
		{
			_messageType = messageType;
		}
		
		public function get messageType():String
		{
			return _messageType;	
		}
		
		[Bindable]
		public function set messageLevel(messageLevel:int):void
		{
			_messageLevel = messageLevel;
		}
		
		public function get messageLevel():int
		{
			return _messageLevel;	
		}
		
		/**
		 * @see #_messageColor
		 */
		[Bindable]
		public function set messageColor(messageColor:uint):void
		{
			_messageColor = messageColor;
		}
		
		/**
		 * @see #_messageColor
		 */
		public function get messageColor():uint
		{
			return _messageColor;
		}
		
		/**
		 * @see #_messageIcon
		 */
		[Bindable]
		public function set messageIcon(messageIcon:Class):void
		{
			_messageIcon = messageIcon;
		}
		
		/**
		 * @see #_messageIcon
		 */
		public function get messageIcon():Class
		{
			return _messageIcon;
		}
		
		
		/**
		 * Determines if this object's message level is higher than the specified one
		 * 
		 * returns true if this object's is higher, false otherwise
		 */
		public function isHigherLevel(segLogMessageType:SEGLogMessageType):Boolean
		{
			var isHigherLevel:Boolean = false;
			
			if (this.messageLevel > segLogMessageType.messageLevel)
			{
				isHigherLevel = true;
			}
			
			return isHigherLevel;
		}
		
		private function initialize(messageType:String):void
		{
			if (messageType == MESSAGE_TYPE_SUCCESS)
			{
				this.messageType = MESSAGE_TYPE_SUCCESS;
				this.messageLevel = MESSAGE_LEVEL_SUCCESS;
				this.messageColor = MESSAGE_COLOR_SUCCESS;
				this.messageIcon = messageIcon_Success;
			}
			else if (messageType == MESSAGE_TYPE_WARNING)
			{
				this.messageType = MESSAGE_TYPE_WARNING;
				this.messageLevel = MESSAGE_LEVEL_WARNING;
				this.messageColor = MESSAGE_COLOR_WARNING;
				this.messageIcon = messageIcon_Warning;
			}
			else if (messageType == MESSAGE_TYPE_ERROR)
			{
				this.messageType = MESSAGE_TYPE_ERROR;
				this.messageLevel = MESSAGE_LEVEL_ERROR;
				this.messageColor = MESSAGE_COLOR_ERROR;
				this.messageIcon = messageIcon_Error;
			}	
		}
		
		/**
		 * Determines the proper messageType for the 
		 */
		private function determineMessageType(messageType:String):String
		{
			if (messageType == MESSAGE_TYPE_SUCCESS)
			{
				return MESSAGE_TYPE_SUCCESS;
			}
			else if (messageType == MESSAGE_TYPE_WARNING)
			{
				return MESSAGE_TYPE_WARNING;
			}
			else if (messageType == MESSAGE_TYPE_ERROR)
			{
				return MESSAGE_TYPE_ERROR;
			}
			else
			{
				return null;
			}	
		}
		
		/**
		 * Determines and sets this object's messageType and messageColor based on the
		 * passed in messageType
		 */
		private function determineMessageTypeAndColor(messageType:String):void
		{
			if (messageType == MESSAGE_TYPE_SUCCESS)
			{
				this.messageType = MESSAGE_TYPE_SUCCESS;
				this.messageColor = MESSAGE_COLOR_SUCCESS;
			}
			else if (messageType == MESSAGE_TYPE_WARNING)
			{
				this.messageType = MESSAGE_TYPE_WARNING;
				this.messageColor = MESSAGE_COLOR_WARNING;
			}
			else if (messageType == MESSAGE_TYPE_ERROR)
			{
				this.messageType = MESSAGE_TYPE_ERROR;
				this.messageColor = MESSAGE_COLOR_ERROR;
			}	
		}
	}
}