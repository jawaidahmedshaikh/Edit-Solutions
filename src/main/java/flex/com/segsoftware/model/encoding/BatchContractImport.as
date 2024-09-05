package com.segsoftware.model.encoding
{
	import flash.events.Event;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.events.CollectionEvent;
	
	/**
	 * Represents a file imported via the batch import mechanism 
	 */
	[Bindable]
	public class BatchContractImport
	{
		private var _batchContractImportFilePk:uint;
		
		private var _fileMD5Hash:String;
		
		private var _creationTime:Date;
		
		private var _status:String;
		
		private var _sourceFileName:String;
		
		private var _completedTime:Date;
									
		private var _modifiedTime:Date;
		
		private var _message:String;
		
		private var _batchContractSetupFk:uint;
		
		private var _operator:String;
		
		private var _batchContractImportRecords:ArrayCollection;
		
		private var _totalRecords:int;
		
		private var _successReportUrl:String;
		
		private var _errorReportUrl:String;
		
		private var _warningReportUrl:String;
			
		private var _importRecordCache:Dictionary = new Dictionary();
		
		public static var MESSAGE_COLOR_SUCCESS:uint = 0x00FF00;	//	Green #00FF00
		public static var MESSAGE_COLOR_WARNING:uint = 0xFFFF00;	//	Yellow #FFFF00
		public static var MESSAGE_COLOR_ERROR:uint   = 0xFF0000;	//	Red #FF0000
		
		[Embed(source="/com/segsoftware/view/assets/icons/bulletSuccess.png")] 
		public static var messageIcon_Success:Class;
		
		[Embed(source="/com/segsoftware/view/assets/icons/bulletWarning.png")] 
		public static var messageIcon_Warning:Class;
		
		[Embed(source="/com/segsoftware/view/assets/icons/bulletError.png")] 
		public static var messageIcon_Error:Class;
		
		private var _importHierarchy = [
			{ label : "SUCCESS", children : [], icon: messageIcon_Success},
			{ label : "ERROR", children : [], icon: messageIcon_Error},
			{ label : "WARNING", children : [], icon: messageIcon_Warning}
		]

		public function BatchContractImport(obj:Object)
		{
			initialize(obj);
		}
		
		/**
		 * Constructs the import record from a dynamic object
		 */
		public function initialize(obj:Object):void {
			_batchContractImportFilePk = obj.batchContractImportFilePk;
			_sourceFileName = obj.sourceFileName;
			_status = obj.status;
			
			if(obj.completedTime != null) {
				_completedTime = new Date();
				_completedTime.setTime(Date.parse(obj.completedTime));
			}
			if(obj.creationTime != null) {
				_creationTime = new Date();
				_creationTime.setTime(Date.parse(obj.creationTime));
			}
			_totalRecords = obj.totalRecords;
			_successReportUrl = obj.successReportUrl;
			_warningReportUrl = obj.warningReportUrl;
			_errorReportUrl = obj.errorReportUrl;
			_message = obj.message;
			
			// initialize records present in this import file
			if(obj.batchContractImportRecords != null) {
				if(this.batchContractImportRecords == null) {
					batchContractImportRecords = new ArrayCollection();
				}
				
				for each (var record in obj.batchContractImportRecords) {
					var importEntry:BatchContractImportEntry = new BatchContractImportEntry(record)
					if(_importRecordCache[importEntry.recordSequence] == null) {
						this.batchContractImportRecords.addItem(importEntry);
						_importRecordCache[importEntry.recordSequence] = importEntry;
						
						if(importEntry.status == "SUCCESS") {
							_importHierarchy[0].children.push(importEntry);
							_importHierarchy[0].label = "SUCCESS (" + _importHierarchy[0].children.length + " records)";
						} else if(importEntry.status == "WARNING") {
							_importHierarchy[2].children.push(importEntry);
							_importHierarchy[2].label = "WARNING (" + _importHierarchy[2].children.length + " records)";
						} else {
							_importHierarchy[1].children.push(importEntry);
							_importHierarchy[1].label = "ERROR (" + _importHierarchy[1].children.length + " records)";
						}
						dispatchEvent(new Event("importHierarchyChanged"));
						
					} else {
						_importRecordCache[importEntry.recordSequence].initialize(importEntry);
					}
				}
			}
		}

		[Bindable(event="importHierarchyChanged")]
		public function get importHierarachy():Object {
			return this._importHierarchy;
		}
		
		public function get batchContractImportFilePk():uint
		{
			return _batchContractImportFilePk;
		}

		public function set batchContractImportFilePk(value:uint):void
		{
			_batchContractImportFilePk = value;
		}

		public function get fileMD5Hash():String
		{
			return _fileMD5Hash;
		}

		public function set fileMD5Hash(value:String):void
		{
			_fileMD5Hash = value;
		}

		public function get creationTime():Date
		{
			return _creationTime;
		}

		public function set creationTime(value:Date):void
		{
			_creationTime = value;
		}

		public function get status():String
		{
			return _status;
		}

		public function set status(value:String):void
		{
			_status = value;
		}

		public function get sourceFileName():String
		{
			return _sourceFileName;
		}

		public function set sourceFileName(value:String):void
		{
			_sourceFileName = value;
		}

		public function get completedTime():Date
		{
			return _completedTime;
		}

		public function set completedTime(value:Date):void
		{
			_completedTime = value;
		}

		/** Time at which the import record was last changed */
		public function get modifiedTime():Date
		{
			return _modifiedTime;
		}

		/**
		 * @private
		 */
		public function set modifiedTime(value:Date):void
		{
			_modifiedTime = value;
		}

		/** Error or other informational message related to the import */
		public function get message():String
		{
			return _message;
		}

		/**
		 * @private
		 */
		public function set message(value:String):void
		{
			_message = value;
		}

		/** Foreign key to the batch contract setup to import into */
		public function get batchContractSetupFk():uint
		{
			return _batchContractSetupFk;
		}

		/**
		 * @private
		 */
		public function set batchContractSetupFk(value:uint):void
		{
			_batchContractSetupFk = value;
		}

		/** Operator making the request */
		public function get operator():String
		{
			return _operator;
		}

		/**
		 * @private
		 */
		public function set operator(value:String):void
		{
			_operator = value;
		}

		/** The set of records associated with this import file */
		public function get batchContractImportRecords():ArrayCollection
		{
			return _batchContractImportRecords;
		}

		/**
		 * @private
		 */
		[Bindable]
		public function set batchContractImportRecords(value:ArrayCollection):void
		{
			_batchContractImportRecords = value;
			if(_batchContractImportRecords != null) {
				_batchContractImportRecords.addEventListener(CollectionEvent.COLLECTION_CHANGE, function(e:Event) {
					dispatchEvent(new Event("statusMessageChanged"));		
				});
			}
			dispatchEvent(new Event("statusMessageChanged"));
		}

		public function get totalRecords():int
		{
			return _totalRecords;
		}

		public function set totalRecords(value:int):void
		{
			_totalRecords = value;
			dispatchEvent(new Event("statusMessageChanged"));
		}

		[Bindable(event="statusMessageChanged")]
		public function get statusMessage():String {
			if(this.batchContractImportRecords == null && status != "PENDING") {
				return this.totalRecords + " contracts processed";
			} else if(this.batchContractImportRecords == null && status == "PENDING") {
				return this.totalRecords + " contracts pending";
			} else {
				var totalRecordLabel:String = this.totalRecords.toString();
				if(this.totalRecords == 0) {
					totalRecordLabel = "-";	
				}
				return this.batchContractImportRecords.length  + "/" + totalRecordLabel + " contracts processed";
			}
			
		}

		public function get successReportUrl():String
		{
			return _successReportUrl;
		}

		public function set successReportUrl(value:String):void
		{
			_successReportUrl = value;
		}

		public function get errorReportUrl():String
		{
			return _errorReportUrl;
		}

		public function set errorReportUrl(value:String):void
		{
			_errorReportUrl = value;
		}

		public function get warningReportUrl():String
		{
			return _warningReportUrl;
		}

		public function set warningReportUrl(value:String):void
		{
			_warningReportUrl = value;
		}
	}
}