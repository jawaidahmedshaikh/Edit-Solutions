package com.segsoftware.model.conversion
{
	import flash.system.System;
	import mx.formatters.NumberFormatter;
	import com.segsoftware.utility.Util;
	
	/**
	 * The execution of a ConversionTemplate against a selected Flat File
	 * defines a ConversionJob. It's biggest asset it to capture a run
	 * time for a conversion. Conversions can take a long time and projections
	 * are required.
	 */ 
	public class ConversionJob
	{
		/**
		 * PK.
		 */ 
		private var _conversionJobPK:Number;
		
		/**
		 * FK.
		 */ 
		private var _conversionTemplateFK:Number;
		
		/**
		 * The timestamp of the batch job start.
		 */ 
		private var _startTime:Date;
		
		/**
		 * The timestamp of the batch job end.
		 */ 
		private var _endTime:Date;	
		
		/**
		 * A user-defined name for this job. Typically, it will
		 * default to the name of the ConversionTemplate.
		 */ 
		private var _jobName:String;	
		
		/**
		 * The flat file to be converted.
		 */ 
		private var _fileName:String;
		
		public function ConversionJob()
		{
			
		}
		
		/**
		 * @see #_conversionJobPK
		 */ 
		public function get conversionJobPK():Number
		{
			return this._conversionJobPK;
		}
		
		/**
		 * @see #_conversionJobPK
		 */ 
		public function set conversionJobPK(conversionJobPK:Number):void
		{
			this._conversionJobPK = conversionJobPK;
		}
		
		/**
		 * @see #_conversionTemplateFK
		 */ 
		public function get conversionTemplateFK():Number
		{
			return this._conversionTemplateFK;		
		}
		
		/**
		 * @see #_conversionTemplateFK
		 */ 
		public function set conversionTemplateFK(conversionTemplateFK:Number):void
		{
			this._conversionTemplateFK = conversionTemplateFK;
		}
		
		/**
		 * @see #_startTime
		 */ 
		public function get startTime():Date
		{
			return this._startTime;
		}
		
		/**
		 * @see #_startTime
		 */ 
		public function set startTime(startTime:Date):void
		{
			this._startTime = startTime;
		}
		
		/**
		 * @see #_endTime
		 */ 
		public function get endTime():Date
		{
			return this._endTime;
		}
		
		/**
		 * @see #_endTime
		 */ 
		public function set endTime(endTime:Date):void
		{
			this._endTime = endTime;
		}
		
		/**
		 * @see #_jobName
		 */ 
		public function get jobName():String
		{
			return this._jobName;
		}
		
		/**
		 * @see #_jobName
		 */ 
		public function set jobName(jobName:String):void
		{
			this._jobName = jobName;
		}
		
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
		 * Calcutes the total time difference between start and end times in millis.
		 */ 
		public function get totalTime():int
		{
			var diffInMillis:Number = endTime.time - startTime.time;
	
			return diffInMillis;
		}
		
		/**
		 * Restores the state of this entity from the specified xml.
		 */ 
		public function unmarshal(xml:XML):void
		{
			this.conversionJobPK = xml.ConversionJobPK;
			
			this.conversionTemplateFK = xml.ConversionTemplateFK;

			this.jobName = xml.JobName;
			
			this.fileName = xml.FileName;	

			var startTimeString:String = xml.StartTime;
				
			this.startTime = new Date(startTimeString);		

			var endTimeString:String = xml.EndTime;

			this.endTime = new Date(endTimeString);			
		}
	}
}