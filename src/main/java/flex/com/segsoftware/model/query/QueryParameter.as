package com.segsoftware.model.query
{
	import mx.collections.XMLListCollection;
	import com.segsoftware.model.SEGMarshallable;
	
	/**
	 * Dynamic queries require name/value pairs to represent
	 * the parameters of the query.
	 */ 
	public class QueryParameter implements SEGMarshallable
	{
		/**
		 * The name of the parameter.
		 */ 
		private var _name:String;
		
		/**
		 * The value of the parameter.
		 */ 
		private var _value:String;
		
		public function QueryParameter(name:String=null, value:String=null)
		{
			this._name = name;
			
			this._value = value;
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
		
		public function unmarshal(entityAsXML:XML=null, version:String=null):void
		{
			
		}
		
		/**
		 * <ParameterVO> 
		 * </ParameterVO>
		 */ 
		public function marshal(version:String=null):XML
		{
			var parameter:XML = <QueryParameterVO>
									<Name>{this.name}</Name>
									<Value>{this.value}</Value>
								</QueryParameterVO>;
								
			return parameter;
		}
	}
}