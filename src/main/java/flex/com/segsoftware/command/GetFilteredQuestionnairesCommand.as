package com.segsoftware.command
{
	import com.segsoftware.model.SEGModelLocator;
	import mx.collections.XMLListCollection;
	import com.segsoftware.utility.Util;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import com.segsoftware.event.SEGEvent;	
	import com.segsoftware.business.GroupDelegate;
	import com.segsoftware.model.SEGModelBuilder;
	
	/**
	 * While users are adding clients during AppEntry, they can answer questions about
	 * the client via product defined questionnaires.
	 */ 
	public class GetFilteredQuestionnairesCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Filtered Questionnaires";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var stateCT:XML = Util.buildXMLElement("StateCT", SEGModelLocator.getInstance().segmentInformationVO.ApplicationSignedStateCT);
			
			var filteredProductPK:XML = Util.buildXMLElement("FilteredProductPK", SEGModelLocator.getInstance().segmentInformationVO.FilteredProductPK);
						
			var parameters:XMLListCollection = Util.convertToXMLListCollection(stateCT, filteredProductPK);
			
			new GroupDelegate(result, fault, parameters).getFilteredQuestionnaires();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			// Remove previous entries
			delete SEGModelLocator.getInstance().selectedAppEntryClientDetailVO.QuestionnaireResponseVO;
			
        	var filteredQuestionnaireVOs:XMLList = resultEvent.result.FilteredQuestionnaireVO;
        	
        	for each (var filteredQuestionnaireVO:XML in filteredQuestionnaireVOs)
        	{
        		var questionnaireResponseVO:XML = SEGModelBuilder.getInstance().buildQuestionnaireResponseVO(filteredQuestionnaireVO);
        		
        		var selectedAppEntryClientDetailVO:XML = SEGModelLocator.getInstance().selectedAppEntryClientDetailVO;
        		
        		selectedAppEntryClientDetailVO.appendChild(questionnaireResponseVO);
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