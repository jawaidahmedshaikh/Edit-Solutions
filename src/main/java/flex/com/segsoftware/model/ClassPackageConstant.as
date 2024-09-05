package com.segsoftware.model
{
	import flash.utils.describeType;

	/**	
	 * Provides a mapping of the class names and package names.  
	 * 
	 * This class is purposely limited to package scope.  The caller should use 
	 * SEGClass instead of calling this class directly.
	 * 
	 * To map a class, simply add the class and its corresponding package name in
	 * the constructor.
	 */ 
	class ClassPackageConstant
	{
		/**
		 * Singleton object
		 */
		private static var classPackageConstant:ClassPackageConstant;

		/**
		 * Map containing the className as the key and the packageName as the value
		 */
		private var map:SEGMap = new SEGMap();
		
		
		
		/**
		 * Constructor
		 */
		function ClassPackageConstant()
		{
			//	Add your mappings here:
			
			map.putValue("Query", "com.segsoftware.model.query"); 
			map.putValue("AreaKey","com.segsoftware.model.engine");
			map.putValue("AreaValue", "com.segsoftware.model.engine");
			map.putValue("Company", "com.segsoftware.model.engine");
			map.putValue("ProductStructure", "com.segsoftware.model.engine");		
		}
		
		/**
		 * Singleton
		 */
		static function getInstance():ClassPackageConstant 
		{
		  	if ( classPackageConstant == null )
		  	{
		  		classPackageConstant = new ClassPackageConstant();
		  	}
		  		
		  	return classPackageConstant;
		}		
		
		/**
		 * Returns the package name for the given className
		 */
		function getPackageName(className:String):String
		{
			if (map.containsKey(className))
			{
				return map.getValue(className) as String;	
			}
			else
			{
				return null;
			}
		}
		
		/**
		 * Returns the fully qualified className for the given className.
		 * The fully qualified name is the packageName plus the className.
		 */
		function getFullyQualifiedClassName(className:String):String
		{
			return getPackageName(className) + "." + className;
		}
	}
}