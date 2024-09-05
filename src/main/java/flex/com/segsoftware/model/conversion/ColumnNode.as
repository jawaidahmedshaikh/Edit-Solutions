package com.segsoftware.model.conversion
{
	import flash.events.Event;
	import com.segsoftware.event.SEGConversionEvent;
	import com.segsoftware.utility.Util;
	
	/**
	 * A RecordNode is partitioned by its ColumnNode(s). A ColumnNode (e.g.)
	 * will defined the fixed length of the column.
	 */ 
	[Bindable]
	public class ColumnNode extends ConversionNode
	{
		/**
		 * Unspecified dates will often default to some specified min value.
		 */ 
		public static var DEFAULTDATE_MIN:String = "Min";

		/**
		 * Unspecified dates will often default to some specified max value.
		 */ 		
		public static var DEFAULTDATE_MAX:String = "Max";
		
		/**
		 * Unspecified dates will often default to some specified none/null value.
		 */ 		
		public static var DEFAULTDATE_NO:String = "No";		
		
		/**
		 * The name of this node.
		 */  
		private var _name:String;		
		
		/**
		 * The length of this field.
		 */ 
		private var _length:int;
		
		/**
		 * [Almost] without exception, the intial column
		 * will define the record type. For this reason,
		 * it is importing to know which ColumnNode is ordered
		 * first in the list of ColumnNode for its parent RecordNode.
		 */ 
		private var _recordType:Boolean;
		
		/**
		 * When true, flags that this ColumnNode represents a date datatype.
		 */ 
		private var _date:Boolean;
		
		/**
		 * Dates that are not supplied, or are supplied as placed-holders need to 
		 * be represented as a Min, Max, or Null (no) date within our system.
		 */ 
		private var _defaultDate:String;
		
		/**
		 * Needs to match the actual "length" property so
		 * that event handling can single it out.
		 */ 
		public static var PROPERTY_LENGTH:String = "length";
		
		/**
		 * The default name for any RecordNode.
		 */ 
		public static var DEFAULT_COLUMN_NAME:String = "ColumnVO";
		
		public function ColumnNode()
		{

		}
					
		/**
		 * @see #_name
		 */ 
		public function get name():String
		{
			return this._name;
		}
		
		/**
		 * @see #_name
		 */ 
		public function set name(name:String):void
		{
			this._name = name;
		}
				
		/**
		 * @see #_length
		 */ 
		public function get length():int
		{
			return this._length;
		}
		
		/**
		 * @see #_length
		 */ 
		public function set length(length:int):void
		{
			this._length = length;
		}
		
		/**
		 * @see #_recordType
		 */ 
		public function get recordType():Boolean
		{
			return this._recordType;
		}
		
		/**
		 * @see #_recordType
		 */ 
		public function set recordType(recordType:Boolean):void
		{
			this._recordType = recordType;
		}
		
		/**
		 * @see #_date
		 */ 
		public function get date():Boolean
		{
			return this._date;
		}
		
		/**
		 * @see #_date
		 */ 
		public function set date(date:Boolean):void
		{
			this._date = date;
			
			if (date)
			{
				this.defaultDate = DEFAULTDATE_MIN;
			}
			else
			{
				this.defaultDate = null;
			}
		}	
		
		/**
		 * @see #_defaultDate
		 */ 
		public function get defaultDate():String
		{
			return this._defaultDate;
		}
		
		/**
		 * @see #_defaultDate
		 */ 
		public function set defaultDate(defaultDate:String):void
		{
			this._defaultDate = defaultDate;
		}				
		
		override public function marshal():XML
		{	
			var columnNodeXML:XML = <{DEFAULT_COLUMN_NAME}/>;
			
			columnNodeXML.Name = Util.initString(this.name, "");
			
			columnNodeXML.Length = this.length;
			
			columnNodeXML.RecordType = (this.recordType)?"Y":"N";
			
			columnNodeXML.Date = (this.date)?"Y":"N";	
			
			if (this.date && this.defaultDate)
			{
				columnNodeXML.DefaultDate = this.defaultDate;						
			}
				
			return columnNodeXML;	
		}
		
		override public function unmarshal(nodeAsXML:XML):void
		{
			this.name = nodeAsXML["Name"];
			
			this.length = new Number(nodeAsXML["Length"]);		
			
			this.recordType = (nodeAsXML["RecordType"] == "Y")?true:false;
			
			this.date = (nodeAsXML.Date == "Y")?true:false;	
			
			this.defaultDate = nodeAsXML.DefaultDate;		
		}
	}
}