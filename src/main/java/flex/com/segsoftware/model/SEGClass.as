package com.segsoftware.model
{
	import flash.utils.*;
	
	import mx.collections.ArrayCollection;
	
	/**
	 * Represents metadata about the specified SEGEntity.
	 */ 
	public class SEGClass
	{
		/**
		 * The fully qualified name of the represented SEGEntity.
		 */ 
		private var _name:String;
		
		/**
		 * The unqualified name of the represented SEGEntity;
		 */ 
		private var _shortName:String;
		
		/**
		 * The collection of fields (typically represented as getter/setters)
		 * for the target SEGEntity.
		 */ 
		private var _segFields:ArrayCollection = new ArrayCollection();
		
		public function SEGClass(segEntity:SEGEntity)
		{
			buildMetaData(segEntity);
		}
		
		/**
		 * @see #_segFields
		 */ 
		public function get segFields():ArrayCollection
		{
			return this._segFields;	
		}
		
		/**
		 * @see #_segFields
		 */ 
		public function set segFields(segFields:ArrayCollection):void
		{
			this._segFields = segFields;
		}
		
		/**
		 * Identities the name of the specified SEGEntity, and collects
		 * meta data about each field of the SEGEntity via
		 * SEGField representations.
		 */ 
		private function buildMetaData(segEntity:SEGEntity):void
		{
			var classInfo:XML = flash.utils.describeType(segEntity);	
			
			name = classInfo.@name;
			
			shortName = name.substr(name.indexOf("::") + 2, name.length);
			
			var accessors:XMLList = classInfo.accessor;
			
			for each (var accessor:XML in accessors)
			{
				var segField:SEGField = new SEGField(accessor);	
				
				this.segFields.addItem(segField);			
			}
		}
		
		/**
		 * Convenience method for flash.utils.describeType(segEntity).@name.	
		 */  
		public static function getClassName(segEntity:SEGEntity):String
		{
			var className:String = flash.utils.describeType(segEntity).@name;	
			
			return className;		
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
		 * @see #_shortName
		 */ 
		public function get shortName():String
		{
			return this._shortName;
		}
		
		/**
		 * @see #_shortName
		 */ 
		public function set shortName(shortName:String):void
		{
			this._shortName = shortName;
		}	
		
		/**
		 * Gets the package name for a given className
		 */
		public static function getPackageName(className:String):String
		{
			return ClassPackageConstant.getInstance().getPackageName(className);
		}	
		
		/**
		 * Gets the fully qualified name for the given className.  The
		 * fully qualified name is the packageName plus the className
		 */
		public static function getFullyQualifiedClassName(className:String):String
		{
			return ClassPackageConstant.getInstance().getFullyQualifiedClassName(className);	
		}
	}
}