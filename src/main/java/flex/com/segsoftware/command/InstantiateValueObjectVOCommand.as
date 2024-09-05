package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.business.SEGMetaDataDelegate;
	import mx.collections.XMLListCollection;
	import com.segsoftware.utility.Util;
	import com.segsoftware.model.SEGModelLocator;
	import mx.rpc.events.*;
	
	/**
	 * This is expected to be a system-level command that is not 
	 * bound to a specific use case.
	 * 
	 * The user intends to populate a new valueObjectVO (from scratch) and
	 * likely bidirectionally [bind] it to the new valueObjectVO.
	 * 
	 * This extracts the name of the new valueObjectVO to instantiate, calls
	 * the remote service that actually instantiates it, and then associates
	 * it to the SEGModelLocator as a new valueObjectVO. 
	 */
	public class InstantiateValueObjectVOCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Instantiate ValueObjectVO";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var valueObjectVOName:String = event.formData.valueObjectVOName;
			
			var requestParameters:XMLListCollection = Util.convertToXMLListCollection(Util.buildXMLElement("TableName", valueObjectVOName));
			
			new SEGMetaDataDelegate(result, fault, requestParameters).buildDefaultValueObjectVO();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var newValueObjectVO:XML = (resultEvent.result as XML).children()[0];
		
			// The user may instantiate more than one valueObjectVO - they are responsible for knowing
			// how the newValueObjectVO is placed within the XML document.
			SEGModelLocator.getInstance().newValueObjectVO.appendChild(newValueObjectVO);
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);					
		}
	}
}