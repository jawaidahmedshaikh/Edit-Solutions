package com.segsoftware.command
{
	import com.segsoftware.business.ConversionDelegate;
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.utility.Util;
	import com.segsoftware.view.custom.SEGFileUpload;

	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.collections.XMLListCollection;
	import com.segsoftware.control.SEGController;
	import com.segsoftware.model.conversion.ConversionResult;

	
	public class RunConversionCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Run Conversion";
		
		override public function executeCommand(event:SEGEvent):void
		{
			SEGModelLocator.getInstance().conversionResult.notifyMessage("Conversion Running...");
			
			var flatFileName:XML = <FileName>{SEGModelLocator.getInstance().selectedConversionData.fileName}</FileName>;
			
			var conversionTemplatePK:XML = <ConversionTemplatePK>{SEGModelLocator.getInstance().selectedConversionTemplateRun.conversionTemplatePK}</ConversionTemplatePK>;
			
			var jobName:XML = <JobName>{event.formData.jobName}</JobName>;
			
			var genericXMLOnly:XML = <GenericXMLOnly>{event.formData.genericXMLOnly}</GenericXMLOnly>;
			
			var returnSample:XML = <ReturnSample>{event.formData.returnSample}</ReturnSample>;
			
			var recordPRASE:XML = <RecordPRASE>{event.formData.recordPRASE}</RecordPRASE>;			
			
			var numberofGroups:XML = <NumberOfGroups>{event.formData.numberOfGroups}</NumberOfGroups>;
			
			var params:XMLListCollection = Util.convertToXMLListCollection(conversionTemplatePK, flatFileName, jobName, genericXMLOnly, returnSample, numberofGroups, recordPRASE);
			
			new ConversionDelegate(result, fault, params).runConversion();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			SEGModelLocator.getInstance().conversionResult.result = resultEvent.result as XML;
			
			Util.dispatchEvent(SEGController.EVENT_GET_CONVERSION_JOBS);
			
			super.result(resultEvent, EVENT_NAME);	
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}
	}
}
