package com.segsoftware.model
{
	import com.segsoftware.utility.Util;
	
	public class SEGField
	{
		public static var FIELD_TYPE_STRING:String = "String";
		
		public static var FIELD_TYPE_DATE:String = "Date";
		
		public static var FIELD_TYPE_NUMBER:String = "Number";
		
		public static var FIELD_TYPE_BOOLEAN:String = "Boolean";
		
		/**
		 * @see #_name
		 */ 
		private var _name:String;
		 
		/**
		 * @see #_type
		 */ 
		private var _type:String;
		
		public function SEGField(segFieldXML:XML)
		{
			this.name = segFieldXML.@name;
			
			this.type = segFieldXML.@type;
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
		 * @see #_type
		 */ 
		public function get type():String
		{
			return this._type;
		}
		
		/**
		 * @see #_type
		 */ 
		public function set type(type:String):void
		{
			this._type = type;
		}
	}
}