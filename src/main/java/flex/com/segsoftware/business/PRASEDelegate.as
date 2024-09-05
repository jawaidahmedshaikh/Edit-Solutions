package com.segsoftware.business
{
	import mx.collections.XMLListCollection;
	import com.segsoftware.command.SEGRequest;
	
	/**
	 * The suite of services offered by PRASE (really ScriptProcessor) to be exposed
	 * as web service(s).
	 */ 
	public class PRASEDelegate extends SEGDelegate
	{
		public function PRASEDelegate(resultFunction:Function, faultFunction:Function, requestParameters:XMLListCollection)
		{
			super(resultFunction, faultFunction, requestParameters);
		}	
		
		/**
		 * Gets the list of all PRASEDocuments within PRASE.
		 */
		public function getAllPRASEDocuments():void
		{
			var segRequest:SEGRequest = new SEGRequest("PRASE", "getAllPRASEDocuments", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);
		}	
	
		/**
		 * Updates the specified PRASEDocument.
		 * @param PRASEDocumentVO
		 */ 	
		public function updatePRASEDocument():void
		{
			var segRequest:SEGRequest = new SEGRequest("PRASE", "updatePRASEDocument", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);			
		}	

		/**
		 * Makes a clone of the specified PRASEDocument from the specified PRASEDocumentPK. 
		 * @param PRASEDocumentPK
		 * @return the cloned PRASEDocument
		 */ 		
		public function clonePRASEDocument():void
		{
			var segRequest:SEGRequest = new SEGRequest("PRASE", "clonePRASEDocument", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);							
		}	
		
		/**
		 * Gets all the PRASETests of the specified PRASEDocumentPK.
		 * @param PRASEDocumentPK
		 * @return all PRASETestVOs associated with the specified PRASEDocumentPK
		 */ 
		public function getAssociatedPRASETests():void
		{
			var segRequest:SEGRequest = new SEGRequest("PRASE", "getAssociatedPRASETests", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);										
		}
		
		/**
		 * Gets all available PRASETests.
		 * @param
		 * @return all PRASETestVOs
		 */ 
		public function getAllPRASETests():void
		{
			var segRequest:SEGRequest = new SEGRequest("PRASE", "getAllPRASETests", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);			
		}
		
		/**
		 * Gets all available ProductStructures and their associated Company.
		 * @param
		 * @return all PRASETestVO.CompanyVO
		 */ 
		public function getAllProductStructures():void
		{
			var segRequest:SEGRequest = new SEGRequest("PRASE", "getAllProductStructures", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);			
		}	
		
		/**
		 * The set of PRASEDocuments that have not been associated with the 
		 * specified PRASETest.
		 * @param PRASETestPK
		 * @return PRASEDocuments that are not yet associated with the specifie PRASETestPK
		 */ 
		public function getCandidatePRASEDocuments():void
		{
			var segRequest:SEGRequest = new SEGRequest("PRASE", "getCandidatePRASEDocuments", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);			
		}	
		
		/**
		 * The set of PRASEDocuments that have been associated with the 
		 * specified PRASETest.
		 * @param PRASETestPK
		 * @return PRASEDocuments that are not yet associated with the specifie PRASETestPK
		 */ 
		public function getSelectedPRASEDocuments():void
		{
			var segRequest:SEGRequest = new SEGRequest("PRASE", "getSelectedPRASEDocuments", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);			
		}	
	
		/**
		 * Creates a new PRASETest from the specified parameters.
		 * @param 
		 * 		<Desecription></Description>
		 * 		<EffectiveDate></EffectiveDate>
		 * 		<Process></Process>
		 * 		<Event></Event>
		 * 		<EventType></EventType>
		 * 		<ProductStructureFK></ProductStructureFK>
		 */ 	
		public function createPRASETest():void
		{
			var segRequest:SEGRequest = new SEGRequest("PRASE", "createPRASETest", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);				
		}
		
		/**
		 * Updates the set of PRASEDocuments to be associated with the
		 * specified PRASETest(PK).
		 * 
		 * @param
		 * 		<PRASETestPK></PRASETestPK>
		 * 		<PRASEDocumentVO></PRASEDocumentVO> // Repeated for each one available
		 * 	    <PRASEDocumentPK></PRASEDocumentPK> // The [one] PRASEDocumentVO that is to serve as the root document
		 */ 
		public function updatePRASETest():void
		{
			var segRequest:SEGRequest = new SEGRequest("PRASE", "updatePRASETest", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);			
		}
		
		/**
		 * Runs the PRASETest against ScriptProcessor.
		 * @ param
		 * 	<PRASETestPK></PRASETestPK>
		 * 	
		 * @ return the results of a comparison between the expected and actual results 
		 * 	<ExtraElement></ExtraElement> One for each additional Element found in the actual results
		 *  <MissingElement></MissingElement> One for each missing Element not found in the actual results
		 *  <ValueDifference> One for each named value in SPOutput that differs from the expected
		 * 		<Name></Name>
		 * 		<Value></Value>
		 *   </ValueDifference>
		 */ 
		public function runPRASETest():void
		{
			var segRequest:SEGRequest = new SEGRequest("PRASE", "runPRASETest", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);			
		}
		
		/**
		 * Modifies the specified PRASETest with a new PRASETest.ExpectedPRASEOutputXML.
		 * @ param
		 * 	<PRASETestVO></PRASETestVO> // Containing the new ExpectedPRASEOutputXML
		 * 	
		 * @ return none
		 */ 
		public function updatePRASETestExpectedResults():void
		{
			var segRequest:SEGRequest = new SEGRequest("PRASE", "updatePRASETestExpectedResults", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);			
		}	
		
		/**
		 * Gets the recorded script processor results
		 */
		public function getScriptProcessorResults():void
		{
			var segRequest:SEGRequest = new SEGRequest("PRASE", "getScriptProcessorResults", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest);	
		}	
		
		/**
		 * Exports the recorded run data to an xml file in the config file's export directory
		 */
		public function exportRunData():void
		{
			var segRequest:SEGRequest = new SEGRequest("PRASE", "exportRecordedRunData", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest); 
		}
		
		/**
		 * Clears the SPRecordedOperator object for the selected operator from the script processor results
		 */
		public function clearSPRecordedOperator():void
		{
			var segRequest:SEGRequest = new SEGRequest("PRASE", "clearSPRecordedOperator", resultFunction, faultFunction, requestParameters);
			
			Services.dispatchServiceRequest(segRequest); 
		}
	}
}