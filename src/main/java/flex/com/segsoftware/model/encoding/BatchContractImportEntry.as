package com.segsoftware.model.encoding
{
	import mx.collections.ArrayCollection;

	/**
	 * Represents an entry in the batch contract import file
	 */
	[Bindable]
	public class BatchContractImportEntry
	{
		private var _label:String;
		private var _status:String;
		private var _recordSequence:int;
		private var _message:String;
		private var _batchContractImportRecordLogs:ArrayCollection;
		private var _children:ArrayCollection;
		
		/**
		 * Creates an import record from a dynamic representation
		 */
		public function BatchContractImportEntry(obj:Object)
		{
			initialize(obj);
		}
		
		/**
		 * Constructs the import record from a dynamic object
		 */
		public function initialize(obj:Object):void {
			this._label = obj.label;
			this._status = obj.status;
			this._message = obj.message;
			this._recordSequence = obj.recordSequence;
			
			// initialize log entries about this record
			if(obj.batchContractImportRecordLogs != null) {
				var logs:ArrayCollection = new ArrayCollection();
				for each(var entry in obj.batchContractImportRecordLogs) {
					logs.addItem(new BatchContractImportEntryLog(entry));
				}
				this.batchContractImportRecordLogs = logs;
			}
		}

		public function get label():String
		{
			return _label;
		}

		public function set label(value:String):void
		{
			_label = value;
		}
		
		public function get children():ArrayCollection { 
			return this._batchContractImportRecordLogs;
		}
		
		public function set children(val:ArrayCollection) {
			this._batchContractImportRecordLogs = val;
		}

		public function get status():String
		{
			return _status;
		}

		public function set status(value:String):void
		{
			_status = value;
		}

		public function get recordSequence():int
		{
			return _recordSequence;
		}

		public function set recordSequence(value:int):void
		{
			_recordSequence = value;
		}

		public function get message():String
		{
			return _message;
		}

		public function set message(value:String):void
		{
			_message = value;
		}

		public function get batchContractImportRecordLogs():ArrayCollection
		{
			return _batchContractImportRecordLogs;
		}

		public function set batchContractImportRecordLogs(value:ArrayCollection):void
		{
			_batchContractImportRecordLogs = value;
			children = value;
		}


	}
}