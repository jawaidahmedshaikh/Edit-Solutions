package com.segsoftware.business
{
	import mx.collections.XMLListCollection;
	import com.segsoftware.command.SEGRequest;
	
	public class CodeTableDelegate extends SEGDelegate
	{
		function CodeTableDelegate(result:Function, fault:Function, parameters:XMLListCollection)
		{
			super(result, fault, parameters);
		}
		
		/**
		 * Gets the entire CodeTable document.
		 */ 
		public function getCodeTableDocument():void
		{
			var segRequest:SEGRequest = new SEGRequest("CodeTable", "getCodeTableDocument", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);
		}
		
		/**
		 * ????
		 */ 
		public function getFilterCodeTableDocument():void
		{
			var segRequest:SEGRequest = new SEGRequest("CodeTable", "getFilteredCodeTableDocument", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);
		}		
	}
}