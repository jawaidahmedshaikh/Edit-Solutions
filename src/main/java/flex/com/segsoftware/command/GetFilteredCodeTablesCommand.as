package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import com.segsoftware.model.SEGModelLocator;
	import mx.collections.XMLListCollection;
	import com.segsoftware.utility.Util;
	import com.segsoftware.business.CodeTableDelegate;
	import com.segsoftware.model.CodeTable;
	
	/**
	 * While on the AppEntry page, the user can change the FilteredProduct requiring
	 * that all CodeTable dropdowns be re-rendered as Filtered CodeTable values.
	 */ 
	public class GetFilteredCodeTablesCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Get Filtered CodeTables";
		
		override public function executeCommand(event:SEGEvent):void
		{
			var segmentInformationVO:XML = SEGModelLocator.getInstance().segmentInformationVO;	
			
			var filteredProductPK:XML = null;
			
			var productKeyVO:XML = event.formData.productKeyVO as XML;
			
			// The following if/else... represents three scenarios depending
			// on whether the user is on the BatchContractGroup page or the BatchSegment (AppEntry) page.
			// Modify this code at your own peril!!!!
			if (!productKeyVO && segmentInformationVO)
			{
				filteredProductPK = segmentInformationVO.FilteredProductPK[0];	
			}
			else if (productKeyVO)
			{
				filteredProductPK = productKeyVO.FilteredProductPK[0];
			}
			else
			{
				filteredProductPK = <FilteredProductPK>{SEGModelLocator.getInstance().selectedBatchContractSetupVO.FilteredProductFK[0].toString()}</FilteredProductPK>;
			}
			
			if (Util.initString(filteredProductPK.toString(), null) != null)
			{
				var params:XMLListCollection = Util.convertToXMLListCollection(filteredProductPK);
				
				new CodeTableDelegate(result, fault, params).getFilterCodeTableDocument()				
			}
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var codeTableDefVOs:XMLList = resultEvent.result.CodeTableDefVO;
			
			for each (var codeTableDefVO:XML in codeTableDefVOs)
			{
				CodeTable.getInstance().addFilteredCodeTableDef(codeTableDefVO);				
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