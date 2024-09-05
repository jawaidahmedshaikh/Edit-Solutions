package com.segsoftware.model.conversion
{
	/**
	 * Represents the File to be converted and its related data
	 * both input and output.
	 */ 
	public class ConversionData
	{
		/**
		 * The name of the file that contains the raw (flat file)
		 * information to convert.
		 */
		private var _fileName:String;
		
		/**
		 * The size (in bytes) of the represented file.
		 */ 
		private var _fileSize:int;
		
		/**
		 * @see #_fileName
		 */ 
		public function get fileName():String
		{
			return this._fileName;
		}
		
		/**
		 * @see #_fileName
		 */ 
		public function set fileName(fileName:String):void
		{
			this._fileName = fileName;
		}
		
		/**
		 * @see #_fileSize
		 */ 
		public function get fileSize():int
		{
			return this._fileSize;
		}
		
		/**
		 * @see #_fileSize
		 */ 
		public function set fileSize(fileSize:int):void
		{
			this._fileSize = fileSize;
		}
		
		public function unmarshal(conversionDataXML:XML):void
		{
			this.fileName = conversionDataXML["FileName"].valueOf();
			
			this.fileSize = new Number(conversionDataXML["FileSize"].valueOf());			
		}
	}
}