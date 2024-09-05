package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.SEGModelBuilder;
	import com.segsoftware.business.SEGMetaDataDelegate;
	import mx.collections.XMLListCollection;
	import com.segsoftware.utility.Util;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import com.segsoftware.business.PRASEDelegate;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.model.logging.SEGLog;

	/**
	 * In addition to manipulating dynamically generated PRAETests, a user may
	 * wish to create a PRASETest from scratch.
	 */ 
	public class CreatePRASETestCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Create PRASE Test";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var descriptionXML:XML = Util.buildXMLElement("Description", event.formData.description as String);
			
			var effectiveDateXML:XML = Util.buildXMLElement("EffectiveDate", event.formData.effectiveDate as String);
			
			var processXML:XML = Util.buildXMLElement("Process", event.formData.process as String);
			
			var eventXML:XML = Util.buildXMLElement("Event", event.formData.event as String);
			
			var eventTypeXML:XML = Util.buildXMLElement("EventType", event.formData.eventType as String);
			
			var productStructurePKXML:XML = event.formData.productStructurePK as XML;
			
			var params:XMLListCollection = Util.convertToXMLListCollection(descriptionXML,
																			effectiveDateXML,
																			processXML,
																			eventXML,
																			eventTypeXML,
																			productStructurePKXML);
			
			new PRASEDelegate(result, fault, params).createPRASETest();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			SEGModelLocator.getInstance().candidatePRASEDocumentWrapperVOs.removeAll();
			
			SEGModelLocator.getInstance().selectedPRASEDocumentWrapperVOs.removeAll();
			
			var praseTestVOs:XMLList = resultEvent.result.PRASETestVO; // There should only be one, but let's keep it as a List
			
			SEGModelLocator.getInstance().praseTestVOs = new XMLListCollection(praseTestVOs);	
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);	
		}
	}
}