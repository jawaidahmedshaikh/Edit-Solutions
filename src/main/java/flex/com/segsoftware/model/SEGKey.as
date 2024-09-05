package com.segsoftware.model
{
	import mx.collections.ArrayCollection;
	
	/**
	 * Participates in a strong composition with SEGMap as element(s) of SEGMap.
	 * An instance of SEGMap will contain 0 or more SEGKey(s) in an effort
	 * to mimic a typical Map implementation (without vigorous optimization or
	 * threading controls).
	 */ 
	public class SEGKey
	{
		/**
		 * A unique key name.
		 */ 
		private var _keyName:String;
		
		/**
		 * The value associated with this named key.
		 */ 
		private var _keyValue:Object;	
		
		public function SEGKey(keyName:String, keyValue:Object)
		{
			this.keyName = keyName;
			
			this.keyValue = keyValue;
		}	
		
		/**
		 * @see #_keyName
		 */ 
		public function set keyName(keyName:String):void
		{
			_keyName = keyName;
		}
		
		/**
		 * @see #_keyName
		 */ 
		public function get keyName():String
		{
			return _keyName;
		}
		
		/**
		 * @see #_keyValue
		 */ 
		public function set keyValue(keyValue:Object):void
		{
			_keyValue = keyValue;
		}
		
		/**
		 * @see #_keyValue
		 */ 
		public function get keyValue():Object
		{
			return _keyValue;	
		}
	}
}