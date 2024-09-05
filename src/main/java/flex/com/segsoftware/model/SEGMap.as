package com.segsoftware.model
{
	import mx.collections.ArrayCollection;
	
	/**
	 * As of this writing, ActionScript does not support the equivalent
	 * of a Map in its collections. This is a crude effort. For example, there is no 
	 * effort to create disperse the hash entries for performance
	 * reasons.
	 */ 
	public class SEGMap
	{
		/**
		 * Stores the list of keys currently in the map.
		 */ 
		private var segKeys:ArrayCollection = new ArrayCollection();
		
		/**
		 * Maps the specified name to the specified value.
		 * 
		 * @return the previous key's value, or null if the mapping did not exist
		 */ 
		public function putValue(keyName:String, keyValue:Object):Object
		{
			var oldKey:SEGKey = getKey(keyName);

			var oldValue:Object = null;

			var newKey:SEGKey = new SEGKey(keyName, keyValue);
			
			if (oldKey)
			{
				oldValue = oldKey.keyValue;
				
				removeValue(keyName);				
			}
			
			segKeys.addItem(newKey);
			
			return oldValue;
		}
		
		/**
		 * Gets the named object from the Map.
		 * 
		 * @return the named object, or null if it can't be found
		 */ 
		public function getValue(keyName:String):Object
		{
			var keyValue:Object = null;
			
			var segKey:SEGKey = getKey(keyName);
			
			if (segKey)
			{
				keyValue = segKey.keyValue;
			}
			
			return keyValue; 
		}
		
		/**
		 * Retuns the named SEGKey, or null if the mapping does not exist.
		 */ 
		private function getKey(keyName:String):SEGKey
		{
			var segKey:SEGKey = null;
			
			for each (var currentSEGKey:SEGKey in segKeys)
			{
				if (currentSEGKey.keyName == keyName)				
				{
					segKey = currentSEGKey;
					
					break;
				}
			}
			
			return segKey;
		}
		
		/**
		 * Removes the named element from the collection.
		 * 
		 * @return the element that was just removed, or null if it there was no such mapping
		 */ 
		public function removeValue(keyName:String):Object
		{
			var oldKeyValue:Object = null;
			
			for each (var segKey:SEGKey in segKeys)
			{
				if (segKey.keyName == keyName)
				{
					oldKeyValue = segKey.keyValue;
					
					removeKey(segKey);
					
					break;
				}
			}	
			
			return oldKeyValue;		
		}
		
		/**
		 * Removes the specified segKey from the collection of segKeys. 
		 */ 
		private function removeKey(segKey:SEGKey):void
		{			
			var keyIndex:int = segKeys.getItemIndex(segKey);
			
			segKeys.removeItemAt(keyIndex);	
		}
		
		/**
		 * True if this SEGMap contains the specified keyName.
		 * 
		 * @return true if this SEGMap contains the specified keyName, false otherwise
		 */ 
		public function containsKey(keyName:String):Boolean
		{
			var keyValue:Object = getValue(keyName);
			
			return (keyValue != null);
		}
		
		/**
		 * The collection of keys currently held by this SEGMap
		 * as Strings.
		 */ 
		public function getKeys():ArrayCollection
		{
			var keyNames:ArrayCollection = new ArrayCollection();
			
			for each (var segKey:SEGKey in segKeys)
			{
				keyNames.addItem(segKey.keyName);
			}
			
			return keyNames;
		}
	}
}