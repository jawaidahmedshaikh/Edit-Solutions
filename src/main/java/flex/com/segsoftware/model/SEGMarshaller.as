package com.segsoftware.model
{
	import mx.collections.ArrayCollection;
	import com.segsoftware.utility.Util;
	import com.segsoftware.view.custom.SEGFileUpload;
	
	/**
	 * Marshals/Unmarshals a SEGEntity from a Flex ActionScript object
	 * to its XML equivalent.
	 */ 
	public class SEGMarshaller
	{
		/**
		 * Singleton.
		 */ 
		private static var segMarshaller:SEGMarshaller;
		
		/**
		 * Maintains a dynamic collection of the SEGClasses that have participated
		 * in marshalling/unmarshalling.
		 */ 
		private var segClasses:Object = new Object();
		
		private function getSEGClass(segEntity:SEGEntity):SEGClass
		{
			var className:String = SEGClass.getClassName(segEntity);	
			
			var segClass:SEGClass = null;
			
			if ((segClass = segClasses[className]) == null)
			{
				segClass = new SEGClass(segEntity);
				
				segClasses[className] = segClass;				
			}
			
			return segClass;
		}
		
		/**
		 * Marshals the specified entity.
		 */ 
		public function marshal(segEntity:SEGEntity=null):XML
		{
			var segClass:SEGClass = getSEGClass(segEntity);	
			
			var segEntityVOName:String = Util.firstCharToUpperCase(segClass.shortName) + "VO";
			
			var segEntityVO:XML = <{segEntityVOName}/>;
			
			for each (var segField:SEGField in segClass.segFields)
			{
				var fieldName:String = Util.firstCharToUpperCase(segField.name);
				
				var fieldValue:String = Util.initString(toStringValue(segEntity, segField), "");
				
				var fieldVO:XML = <{fieldName}>{fieldValue}</{fieldName}>;
				
				segEntityVO.appendChild(fieldVO);
			}

			return segEntityVO;					
		}
		
		/**
		 * Unmarshals the specified segEntityVO to the specified segEntity.
		 */ 
		public function unmarshal(segEntityVO:XML, segEntity:SEGEntity):void
		{
			var segClass:SEGClass = getSEGClass(segEntity);
			
			for each (var segField:SEGField in segClass.segFields)
			{
				var fieldName:String = Util.firstCharToUpperCase(segField.name);
				
				var fieldValue:String = segEntityVO[fieldName];
				
				if (fieldValue)
				{
					if (segField.type == SEGField.FIELD_TYPE_DATE)
					{
						segEntity[segField.name] = new Date(fieldValue);
					}
					else if (segField.type == SEGField.FIELD_TYPE_NUMBER)
					{
						segEntity[segField.name] = new Number(fieldValue);
					}
					else if (segField.type == SEGField.FIELD_TYPE_BOOLEAN)
					{
						segEntity[segField.name] = new Boolean(fieldValue);
					} 
					else
					{
						segEntity[segField.name] = fieldValue as String;
					}
				}
				
				var fieldVO:XML = <{fieldName}>{fieldValue}</{fieldName}>;
				
				segEntityVO.appendChild(fieldVO);
			}			
		}
		
		/**
		 * Converts the target field value of the specified segEnity
		 * to its String equivalent. If the field type is "Date", then
		 * the final String version is formatted to mm/dd/yyyy.
		 * 
		 * @return the String value of the target field, or null
		 */ 	
		public function toStringValue(segEntity:SEGEntity, segField:SEGField):String
		{
			var stringValue:String = null;
			
			var value:Object = segEntity[segField.name];
			
			if (value)
			{
				if (segField.type == SEGField.FIELD_TYPE_DATE)
				{
					stringValue = Util.formatDate1(value as Date);
				}
				else
				{
					stringValue = value.toString();
					//	Odd - noticed that Booleans that are true, come back as "true", but false come back as null
					//	This is contrary to the documentation for Boolean.toString().
				}
			}			
			
			return stringValue;
		}		
		
		/**
		 * Access the singleton.
		 */ 
		public static function getInstance():SEGMarshaller
		{
			if (!segMarshaller)			
			{
				segMarshaller = new SEGMarshaller()
			}
			
			return segMarshaller;
		}
	}
}