package engine.business;

import org.dom4j.Document;

public interface PRASE
{
    /**
     * The set of all currently supported PRASEDocuments (by name).
     * 
     * @return  SEGResponseVO containing the following structure:
     *
     *  <SEGResponseVO>
     *      <PRASEDocumentVO/> // repeated for each name found
     *  </SEGResponseVO>               
     */
    public Document getAllPRASEDocuments(Document requestDocument);
    
    /**
     * Creates a default BatchContractSetup
     * @param requestDocument           SEGRequestVO containing the following structure:
     *
     *                                  <SEGRequestVO>
     *                                      <RequestParameters>
     *                                          <PRASEDocumentVO></PRASEDocumentVO>
     *                                      </RequestParameters>
     *                                  </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the created BatchContractSetup using the following structure:
     *
     *                                      <SEGResponseVO> // Empty
     *                                      </SEGResponseVO>
     */
    public Document updatePRASEDocument(Document requestDocument);
    
    /**
     * Makes a clone of the specified PRASEDocument.
     * @param requestDocument
     *                                  <SEGRequestVO>
     *                                      <RequestParameters>
     *                                          <PRASEDocumentPK></PRASEDocumentPK>
     *                                          <Description></Description>
     *                                      </RequestParameters>
     *                                  </SEGRequestVO>
     * @return the cloned PRASEDocument:
     *              <SEGResponseVO>
     *                  <PRASEDocumentVO></PRASEDocumentVO>
     *              </SEGResponseVO>
     */
    public Document clonePRASEDocument(Document requestDocument);
    
    /**
     * The list of PRASETests associated with the specified PRASEDocument (via PRASEDocumentPK).
     * @param requestDocument
     *                                  <SEGRequestVO>
     *                                      <RequestParameters>
     *                                          <PRASEDocumentPK></PRASEDocumentPK>
     *                                      </RequestParameters>
     *                                  </SEGRequestVO>
     * @return the cloned PRASEDocument:
     *              <SEGResponseVO>
     *                  <PRASETestVO> // repeats for every PRASETestVO found
     *                      <ProductuStructureVO>
     *                          <CompanyVO>
     *                          </CompanyVO>
     *                      </ProductStructureVO>
     *                  </PRASETestVO>
     *              </SEGResponseVO>
     */
    public Document getAssociatedPRASETests(Document requestDocument);
    
    /**
     * The list of PRASETests associated with the specified PRASEDocument (via PRASEDocumentPK).
     * @param requestDocument
     *                                  <SEGRequestVO>
     *                                      <RequestParameters>
     *                                      </RequestParameters>
     *                                  </SEGRequestVO>
     * @return the cloned PRASEDocument:
     *              <SEGResponseVO>
     *                  <PRASETestVO> // repeats for every PRASETestVO found
     *                      <ProductuStructureVO>
     *                          <CompanyVO>
     *                          </CompanyVO>
     *                      </ProductStructureVO>
     *                  </PRASETestVO>
     *              </SEGResponseVO>
     */
    public Document getAllPRASETests(Document requestDocument);    
    
    /**
     * The list of PRASETests associated with the specified PRASEDocument (via PRASEDocumentPK).
     * @param requestDocument
     *                                  <SEGRequestVO>
     *                                      <RequestParameters>
     *                                      </RequestParameters>
     *                                  </SEGRequestVO>
     * @return the cloned PRASEDocument:
     *              <SEGResponseVO>
     *                  <ProductStructureVO></ProductStructureVO> // repeats for every ProductStructureVO found
     *              </SEGResponseVO>
     */
    public Document getAllProductStructures(Document requestDocument);   
    
    /**
     * The list of PRASEDocuments that have not been associated to the specified PRASETest.
     * @param requestDocument
     *                                  <SEGRequestVO>
     *                                      <RequestParameters>
     *                                          <PRASETestPK></PRASETestPK>
     *                                      </RequestParameters>
     *                                  </SEGRequestVO>
     * @return the cloned PRASEDocument:
     *              <SEGResponseVO>
     *                  <PRASEDocument></PRASEDocument> // repeats for every PRASEDocument found
     *              </SEGResponseVO>
     */
    public Document getCandidatePRASEDocuments(Document requestDocument);       
    
    /**
     * The list of PRASEDocuments that have been associated to the specified PRASETest.
     * @param requestDocument
     *                                  <SEGRequestVO>
     *                                      <RequestParameters>
     *                                          <PRASETestPK></PRASETestPK>
     *                                      </RequestParameters>
     *                                  </SEGRequestVO>
     * @return the cloned PRASEDocument:
     *              <SEGResponseVO>
     *                  <PRASEDocument></PRASEDocument> // repeats for every PRASEDocument found
     *              </SEGResponseVO>
     */
    public Document getSelectedPRASEDocuments(Document requestDocument);
    
    /**
     * Builds a PRASETest from scratch using the specified parameters.
     * @param requestDocument
     *                                  <SEGRequestVO>
     *                                      <RequestParameters>
     *                                          <Description></Description>
     *                                          <EffectiveDate></EffectiveDate>
     *                                          <Process></Process>
     *                                          <Event></Event>
     *                                          <EventType></EventType>
     *                                          <ProductStructurePK></ProductStructurePK>
     *                                      </RequestParameters>
     *                                  </SEGRequestVO>
     * @return the new PRASETest:
     *              <SEGResponseVO>
     *                  <PRASETestVO></PRASETestVO>
     *              </SEGResponseVO>
     */    
    public Document createPRASETest(Document requestDocument);
 
 
    /**
     * Updates information about the specified PRASETest most notably the
     * list of PRASEDocuments that are associated with PRASETest.
     * @param requestDocument
     *                                  <SEGRequestVO>
     *                                      <RequestParameters>
     *                                         <PRASETestVO>
     *                                         
     *                                      </RequestParameters>
     *                                  </SEGRequestVO>
     * @return the new PRASETest:
     *              <SEGResponseVO>
     *                  <PRASETestVO></PRASETestVO>
     *              </SEGResponseVO>
     */    
    public Document updatePRASETest(Document requestDocument); 
    
    
    /**
     * Runs the PRASETest against ScriptProcessor.
     * @ param
     *                                  <SEGRequestVO>
     *                                      <RequestParameters>
     *                                         <PRASETestPK></PRASETestPK>
     *                                      </RequestParameters>
     *                                  </SEGRequestVO>
     *      
     * @ return the results of a comparison between the expected and actual results 
     *              <SEGResponseVO>
     *                  <ExtraElement></ExtraElement> One for each additional Element found in the actual results
     *                  <MissingElement></MissingElement> One for each missing Element not found in the actual results
     *                  <ValueDifference> One for each named value in SPOutput that differs from the expected
     *                      <Name></Name>
     *                      <Value></Value>
     *                  </ValueDifference>
     *               </SEGResponseVO>   
     */     
    public Document runPRASETest(Document requestDocument);
    
    /**
     * Updates the PRASETest.ExpectedResultsXML.
     * @ param
     *                                  <SEGRequestVO>
     *                                      <RequestParameters>
     *                                         <PRASETestVO></PRASETestVO>
     *                                      </RequestParameters>
     *                                  </SEGRequestVO>
     *      
     * @ return none
     */     
    public Document updatePRASETestExpectedResults(Document requestDocument);

    /**
     * Gets the recorded results from running script processor
     *
     * @param requestDocument
     * @return
     */
    public Document getScriptProcessorResults(Document requestDocument);

    /**
     * Exports the script processor recorded results for a specific run to an xml file in the config file's export
     * directory.
     *
     * @param requestDocument           document containing request parameters for the operator and run to be exported
     *
     * @return response document containing success/error messages only
     */
    public Document exportRecordedRunData(Document requestDocument);

    /**
     * Clears the recorded results for a specific operator
     *
     * @param requestDocument           document containing request parameters for the operator to be removed
     *
     * @return  response document containing success/error messages only
     */
    public Document clearSPRecordedOperator(Document requestDocument);
}
