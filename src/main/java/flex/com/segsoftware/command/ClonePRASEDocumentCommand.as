package com.segsoftware.command
{
	import com.segsoftware.view.custom.SEGComboBox;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.business.PRASEDelegate;
	import com.segsoftware.utility.Util;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import com.segsoftware.event.SEGEvent;
	import mx.collections.XMLListCollection;
	import com.segsoftware.model.logging.SEGLog;

	/**
	 * Creating a PRASEDocument from scratch could be quite intensive as a large
	 * document may have many dozens of fields. A user is more likely to want to
	 * clone an existing PRASEDocument, and then make some basic changes.
	 */ 
	public class ClonePRASEDocumentCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Clone PRASE Document";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var praseDocumentPKXML:XML = SEGModelLocator.getInstance().selectedPRASEDocumentVO.PRASEDocumentPK[0];
			
			var descriptionXML:XML = Util.buildXMLElement("Description", event.formData.descriptionText as String);
			
			var params:XMLListCollection = Util.convertToXMLListCollection(praseDocumentPKXML, descriptionXML);
			
			new PRASEDelegate(result, fault, params).clonePRASEDocument();	
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var praseDocumentVO:XML = resultEvent.result.PRASEDocumentVO[0];
			
			SEGModelLocator.getInstance().praseDocumentVOs.addItem(praseDocumentVO);
			
			super.result(resultEvent, EVENT_NAME); 					
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);			
		}
	}
}