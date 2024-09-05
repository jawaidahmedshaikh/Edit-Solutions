package com.segsoftware.model.query
{
	import com.segsoftware.model.SEGMarshallable;
	
	public class QueryColumn implements SEGMarshallable  
	{
	    /**
	     * The name, as defined in the DB, of this column.
	     */
	    private var _name:String;
	
	    /**
	     * The datatype of this column expressed as a String.
	     */
	    private var _type:String;
	
	    /**
	     * The value of this column expressed as a String. It could be null.
	     */
	    private var _value:String;
		
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
		
		/**
		 * @see #_value
		 */ 
		public function get value():String
		{
			return this._value;
		}
		
		/**
		 * @see #_value
		 */ 
		public function set value(value:String):void
		{
			this._value = value;
		}
		
		public function unmarshal(queryColumnXML:XML=null, version:String=null):void
		{
			this.name = queryColumnXML.Name.toString();
			
			this.type = queryColumnXML.Type.toString();
			
			if (queryColumnXML.Value)
			{
				this.value = queryColumnXML.Value.toString();
			}						
		}		
		
		public function marshal(version:String=null):XML
		{
			return null;
		}
	}
}