package com.segsoftware.model.conversion
{
	import flash.events.EventDispatcher;
	import flash.events.Event;
	
	/**
	 * Abstracted this concept since the "result" of running a conversion
	 * can be different things at different times. In the case of converting
	 * a flat-file to generic XML, the results are just the "generic" XML.
	 * In the case of converting a flat-file to generic XML to it's actual
	 * business-document equivalent, it could be anything.
	 * 
	 * As of this writing, we can expect two distinct types of output:
	 * 
	 * 1. GenericXML. In this case, we would expect to see:
	 * <GenericXML>
	 * 		<Group>...</Group>
	 * 		<Group>...</Group>
	 * </GenericXML>
	 * 2. ConvertedXML
	 * <ConvertedXML>
	 * 		<FooConvertedStructure1>...</FooConvertedStructure1>
	 * 		<FooConvertedStructure2>...</FooConvertedStructure2>
	 * </ConvertedXML>
	 * 
	 * The important thing is that the outer-document is of a limited set.
	 */ 
	public class ConversionResult extends EventDispatcher 
	{			
		public static var ELEMENT_NAME_GENERIC_XML:String = "GenericXML";
		
		public static var ELEMENT_NAME_CONVERTED_XML:String = "ConvertedXML"; 
		
		public static var ELEMENT_NAME_MESSAGE_XML:String = "MessageXML";
		
		public static var ELEMENT_NAMES:Array = [ELEMENT_NAME_CONVERTED_XML,ELEMENT_NAME_GENERIC_XML, ELEMENT_NAME_MESSAGE_XML];
		
		public static var EVENT_CONVERSION_RESULT_UPDATED:String = "conversionResultUpdated";
		
		/**
		 * The returned output of the conversion request (not the whole thing, just the 
		 * sample if requested).
		 */ 
		private var _result:XML;
		
		/**
		 * The "structure" of the result.
		 * @see #RESULT_TYPE_GENERIC_XML
		 */ 
		private var _resultType:int;
		
		public function ConversionResult()
		{

		}
		
		/**
		 * @see #_result
		 */ 
		public function get result():XML
		{
			return this._result;
		}
		
		/**
		 * @see #_result 
		 */ 
		public function set result(result:XML):void
		{
			this._result = result;
			
			this.dispatchEvent(new Event(EVENT_CONVERSION_RESULT_UPDATED));			
		}
		
		/**
		 * Considers the type of the result and formats a response document
		 * as is appropriate.
		 */ 
		[Bindable("conversionResultUpdated")] 
		public function get asXMLList():XMLList
		{
			var resultAsXMLList:XMLList = null;
			
			if (result)
			{	
				// Determine which result-type exists in the result by blinding searching.
				for each (var elementName:String in ELEMENT_NAMES)
				{
					resultAsXMLList = result[elementName];
					
					if (resultAsXMLList.length() > 0)
					{
						break;
					}
				}
			}
			
			return resultAsXMLList;
		}
		
		/**
		 * There are times when we render messages (only) and not conversion results.
		 */ 
		public function notifyMessage(message:String):void
		{			
			result = <SEGResponseVO><MessageXML>{message}</MessageXML></SEGResponseVO>;
			
			this.dispatchEvent(new Event(EVENT_CONVERSION_RESULT_UPDATED));
		}
		
		/**
		 * A conversion update has been received and we want to notify listeners.
		 */ 
		public function notifyUpdate(result:XML):void
		{
			this.result = result;
						
			this.dispatchEvent(new Event(EVENT_CONVERSION_RESULT_UPDATED));
		}
	}
}