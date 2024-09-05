package com.segsoftware.command
{
	import com.segsoftware.business.ConversionDelegate;
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.SEGModelLocator;
	import com.segsoftware.utility.Util;
	
	import mx.collections.XMLListCollection;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import mx.collections.ArrayCollection;
	import com.segsoftware.model.conversion.ConversionJob;	
	
	/**
	 * Gets all ConversionJobs. 
	 */
	public class GetConversionJobsCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Conversion Jobs";
		
		override public function executeCommand(event:SEGEvent):void
		{
			new ConversionDelegate(result, fault, null).getConversionJobs();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			// Hold onto this temporarily - we are about to lose it when clearing the conversionJobs collection.
			var selectedConversionJob:ConversionJob = SEGModelLocator.getInstance().selectedConversionJob;
			
			SEGModelLocator.getInstance().conversionJobs.removeAll();

			// Set it back
			SEGModelLocator.getInstance().selectedConversionJob = selectedConversionJob;
			
			var conversionJobVOs:XMLList = resultEvent.result.ConversionJobVO;

			// Load the conversion jobs
			for each (var conversionJobXML:XML in conversionJobVOs)
			{
				var conversionJob:ConversionJob = new ConversionJob();
				
				conversionJob.unmarshal(conversionJobXML);
				
				SEGModelLocator.getInstance().conversionJobs.addItem(conversionJob);
			}
			
			super.result(resultEvent, EVENT_NAME);
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME);
			
			trace(faultEvent.message);
		}
	}
}