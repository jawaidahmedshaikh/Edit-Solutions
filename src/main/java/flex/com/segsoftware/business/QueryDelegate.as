package com.segsoftware.business
{
	import mx.collections.XMLListCollection;
	import com.segsoftware.command.SEGRequest;
	
	/**
	 * Provides functionality for creating, editing, and running
	 * predefined HQL (SQL) based queries in the system.
	 */ 
	public class QueryDelegate extends SEGDelegate
	{
		public function QueryDelegate(resultFunction:Function, faultFunction:Function, requestParameters:XMLListCollection)
		{
			super(resultFunction, faultFunction, requestParameters);
		}
		
		/**
		 * Saves new ConversionTemplate.
		 */
		public function saveQuery():void
		{
			var segRequest:SEGRequest = new SEGRequest("Query", "saveQuery", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);
		}		
		
		/**
		 * Executes a dynamical hql statement.
		 */
		public function executeQuery():void
		{
			var segRequest:SEGRequest = new SEGRequest("Query", "executeQuery", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);
		}						
	}
}