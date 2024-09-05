package com.segsoftware.business
{
	import mx.collections.XMLListCollection;
	import com.segsoftware.command.SEGRequest;
	
	/**
	 * Provides functionality for converting flat files or external file
	 * structures to canonical forms recognized by our system. It is expected
	 * that this will be most useful for converting flat files using the XFlat language.
	 */ 
	public class ConversionDelegate extends SEGDelegate
	{
		public function ConversionDelegate(resultFunction:Function, faultFunction:Function, requestParameters:XMLListCollection)
		{
			super(resultFunction, faultFunction, requestParameters);
		}
		
		/**
		 * Saves new ConversionTemplate.
		 */
		public function createConversionTemplate():void
		{
			var segRequest:SEGRequest = new SEGRequest("Conversion", "createConversionTemplate", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);
		}		
		
		/**
		 * Deletes the ConversionTemplate as specified by
		 * ConversionTemplatePK contained in the params.
		 */ 
		public function deleteConversionTemplate():void
		{
			var segRequest:SEGRequest = new SEGRequest("Conversion", "deleteConversionTemplate", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);						
		}
		
		/**
		 * Updates the specified ConversionTemplate (most likely the TemplateText).
		 */ 
		public function updateConversionTemplate():void
		{
			var segRequest:SEGRequest = new SEGRequest("Conversion", "updateConversionTemplate", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);			
		}
		
		/**
		 * Validates the TemplateText (most likely against the XFlat syntax) for the 
		 * specified ConversionTemplateVO.
		 */  
		public function validateConversionTemplate():void
		{
			var segRequest:SEGRequest = new SEGRequest("Conversion", "validateConversionTemplate", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);
		}
		
		/**
		 * Clones new ConversionTemplate.
		 */
		public function cloneConversionTemplate():void
		{
			var segRequest:SEGRequest = new SEGRequest("Conversion", "cloneConversionTemplate", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);
		}		
		
		/**
		 * Displays all ConversionTemplates.
		 */
		public function getAllConversionTemplates():void
		{
			var segRequest:SEGRequest = new SEGRequest("Conversion", "getAllConversionTemplates", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);
		}
		
		/**
		 * Displays sample data for testing ConversionTemplate.
		 */
		public function loadSampleConversionData():void
		{
			var segRequest:SEGRequest = new SEGRequest("Conversion", "loadTestConversionData", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);
		}
		
		/**
		 * Verifies ConversionTemplate against sample data supplied.
		 * @param <ConversionTemplatePK></ConversionTemplatePK>
		 * @param <sampleConversionData></sampleConversionData>
		 */
		public function testConversionTemplate():void
		{
			var segRequest:SEGRequest = new SEGRequest("Conversion", "testConversionTemplate", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);
		}
		
		/**
		 * Retrieves ConversionJobs for selected ConversionTemplate.
		 */
		public function getConversionJobs():void
		{
			var segRequest:SEGRequest = new SEGRequest("Conversion", "getConversionJobs", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);
		}
		
		/**
		 * Retrieves ConversionLogs for selected ConversionJob.
		 */
		public function getConversionLogs():void
		{
			var segRequest:SEGRequest = new SEGRequest("Conversion", "getConversionLogs", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);
		}
		
		public function runConversion():void
		{
			var segRequest:SEGRequest = new SEGRequest("Conversion", "runConversion", resultFunction, faultFunction, requestParameters);			

			Services.dispatchServiceRequest(segRequest);			
		}
		
		/**
		 * Gets all the Files in the directory specified for supplying Conversion test data.
		 */ 
		public function getAllTestFiles():void
		{
			var segRequest:SEGRequest = new SEGRequest("Conversion", "getAllTestFiles", resultFunction, faultFunction, requestParameters);			

			Services.dispatchServiceRequest(segRequest);							
		}
	}
}