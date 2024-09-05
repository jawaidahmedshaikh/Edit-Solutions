package com.segsoftware.command.query
{
	import com.segsoftware.command.SEGCommand;
	import com.segsoftware.model.query.QueryStatement;
	import com.segsoftware.business.QueryDelegate;
	import mx.collections.XMLListCollection;
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import com.segsoftware.model.query.QueryResult;

	/**
	 * User has specified a hql and database to execute against.
	 * This class should never be modified for any specific business context.
	 * It is specifically used by the QueryStatement object to execute and gather results.
	 */ 
	public class ExecuteQueryStatementCommand extends SEGCommand
	{
		private var EVENT_NAME:String = "Execute Query"; 

		private var queryStatement:QueryStatement;
		
		override public function executeCommand(event:SEGEvent):void
		{			
			queryStatement = event.formData as QueryStatement;				
	
			var params:XMLListCollection = new XMLListCollection();
			
			params.addItem(queryStatement.marshal());
	
			new QueryDelegate(result, fault, params).executeQuery();
		}
		
		override public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void
		{
			var queryResultVO:XML = resultEvent.result.QueryResultVO[0];
			
			var queryResult:QueryResult = new QueryResult();
			
			if (queryStatement.marshalAsRows == true)
			{
				queryResult.unmarshal(queryResultVO, QueryResult.UNMARSHAL_AS_ROWS);
			}
			else
			{
				queryResult.unmarshal(queryResultVO, QueryResult.UNMARSHAL_AS_COMPOSITION);
			}
			
			queryStatement.queryResult = queryResult;
			
			// Invoke the callback if available
			if (queryStatement.resultsCallbackFunction)
			{
				queryStatement.resultsCallbackFunction.call(null, queryStatement);
			}
			
			super.result(resultEvent, EVENT_NAME, token);			
		}
		
		override public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void
		{
			super.fault(faultEvent, EVENT_NAME, token); 
			
			trace(faultEvent.message);
		}			
	}
}