package com.segsoftware.model.encoding
{
	/**
	 * Represents a log entry describing a record in an import file
	 */
	[Bindable]
	public class BatchContractImportEntryLog
	{
		private var _status:String;
		private var _creationTime:Date;
		private var _message:String;
		
		private var _label:String;
		
		[Embed(source="/com/segsoftware/view/assets/icons/bulletSuccess.png")] 
		public static var messageIcon_Success:Class;
		
		[Embed(source="/com/segsoftware/view/assets/icons/bulletWarning.png")] 
		public static var messageIcon_Warning:Class;
		
		[Embed(source="/com/segsoftware/view/assets/icons/bulletError.png")] 
		public static var messageIcon_Error:Class;
		
		private var _icon:Class;
		
		/**
		 * Creates the import record from a dynamic object
		 */
		public function BatchContractImportEntryLog(obj:Object)
		{
			initialize(obj);
		}
		
		/**
		 * Sets the properties of this object based on those of a dyanamic instance 
		 */
		public function initialize(obj:Object) {
			status = obj.status;
			message = obj.message;
			if(obj.creationTime != null) {
				this._creationTime = new Date();
				this._creationTime.setTime(Date.parse(obj.creationTime));
			}
		}

		public function get status():String
		{
			return _status;
		}

		public function set status(value:String):void
		{
			_status = value;
			if(_status == "SUCCESS") {
				icon = messageIcon_Success;
			} else if(_status == "WARNING") {
				icon = messageIcon_Warning;
			} else {
				icon = messageIcon_Error;
			}
		}

		public function get creationTime():Date
		{
			return _creationTime;
		}

		public function set creationTime(value:Date):void
		{
			_creationTime = value;
		}

		public function get message():String
		{
			return _message;
		}

		public function set message(value:String):void
		{
			_message = value;
			label = value;
		}

		public function get icon():Class
		{
			return _icon;
		}

		public function set icon(value:Class):void
		{
			_icon = value;
		}

		public function get label():String
		{
			return _label;
		}

		public function set label(value:String):void
		{
			_label = value;
		}


	}
}