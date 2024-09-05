/*
 * User: sprasad
 * Date: Oct 15, 2007
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package conversion.business;

import org.dom4j.Document;

public interface Conversion
{
    /**
     * @param requestDocument
     *                      <SEGRequestVO>
     *                          <RequestParameters>
     *                              <ConversionTemplateVO>
     *                                  ...
     *                              </ConversionTemplateVO>
     *                          </RequestParameters>
     *                      </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                      <SEGResponseVO>
     *                          <ResponseMessageVO>
     *                              ...
     *                          </ResponseMessageVO>
     *                      </SEGResponseVO>
     */
    public Document createConversionTemplate(Document requestDocument);
    
    /**
     * @param requestDocument
     *                      <SEGRequestVO>
     *                          <RequestParameters>
     *                              <ConversionTemplatePK>
     *                                  PK Value to retrieve
     *                              </ConversionTemplatePK>
     *                          </RequestParameters>
     *                      </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                      <SEGResponseVO>
     *                          <ResponseMessageVO>
     *                              ...
     *                          </ResponseMessageVO>
     *                      </SEGResponseVO>
     */
    public Document getConversionTemplate(Document requestDocument);

    /**
     * @param requestDocument
     *                      <SEGRequestVO>
     *                          <RequestParameters>
     *                              <ConversionTemplateVO>
     *                                  ...
     *                              </ConversionTemplateVO>
     *                          </RequestParameters>
     *                      </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                      <SEGResponseVO>
     *                          <ResponseMessageVO>
     *                              ...
     *                          </ResponseMessageVO>
     *                      </SEGResponseVO>
     */
    public Document updateConversionTemplate(Document requestDocument);
    
    /**
     * @param requestDocument
     *                      <SEGRequestVO>
     *                          <RequestParameters>
     *                              <ConversionTemplatePK>
     *                                  PK Value To Delete
     *                              </ConversionTemplatePK>
     *                          </RequestParameters>
     *                      </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                      <SEGResponseVO>
     *                          <ResponseMessageVO>
     *                              ...
     *                          </ResponseMessageVO>
     *                      </SEGResponseVO>
     */
    public Document deleteConversionTemplate(Document requestDocument);

    /**
     * @param requestDocument
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                      <SEGResponseVO>
     *                          <ResponseMessageVO>
     *                              <ConversionTemplateVO>
     *                                  ...
     *                              </ConversionTemplateVO>
     *                          </ResponseMessageVO>
     *                      </SEGResponseVO>
     * Containing all ConversionTemplateVOs.
     */
    public Document getAllConversionTemplates(Document requestDocument);

    /**
     * @param requestDocument
     *                      <SEGRequestVO>
     *                          <RequestParameters>
     *                              <ConversionTemplatePK>
     *                                  PK Value Of ConversionTemplate To Clone From
     *                              </ConversionTemplatePK>
     *                              <ConversionTemplateVO>
     *                                  ConversionTemplate Name and Description
     *                              </ConversionTemplateVO>
     *                          </RequestParameters>
     *                      </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                      <SEGResponseVO>
     *                          <ResponseMessageVO>
     *                              ...
     *                          </ResponseMessageVO>
     *                      </SEGResponseVO>
     */
    public Document cloneConversionTemplate(Document requestDocument);

    /**
     * @param requestDocument
     *                      <SEGRequestVO>
     *                          <RequestParameters>
     *                              <FileName>
     *                                  ...
     *                              </FileName>
     *                          </RequestParameters>
     *                      </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                      <SEGResponseVO>
     *                          <ResponseMessageVO>
     *                              <SampleConversionDataVO>
     *                                   ...
     *                              </SampleConversionDataVO>
     *                          </ResponseMessageVO>
     *                      </SEGResponseVO>
     */
    public Document loadTestConversionData(Document requestDocument);

    /**
     * @param requestDocument
     *                      <SEGRequestVO>
     *                          <RequestParameters>
     *                              <TemplateText>
     *                                  XFL Template that needs to be used
     *                              </TemplateText>
     *                              <SampleConversionData>
     *                                  ...
     *                              </SampleConversionData>
     *                          </RequestParameters>
     *                      </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                      <SEGResponseVO>
     *                          <ResponseMessageVO>
     *                              <SampleConvertedData>
     *                                  ...
     *                              </SampleConvertedData>
     *                          </ResponseMessageVO>
     *                      </SEGResponseVO>
     */
    public Document testConversionTemplate(Document requestDocument);

    /**
     * @param requestDocument
     *                      <SEGRequestVO>
     *                          <RequestParameters>
     *                              <ConversionTemplatePK>
     *                                  ...
     *                              </ConversionTemplatePK>
     *                          </RequestParameters>
     *                      </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                      <SEGResponseVO>
     *                          <ResponseMessageVO>
     *                              <ConversionJobVO>
     *                                  ...
     *                              </ConversionJobVO>
     *                          </ResponseMessageVO>
     *                      </SEGResponseVO>
     */
    public Document getConversionJobs(Document requestDocument);    
    
    /**
     * @param requestDocument
     *                      <SEGRequestVO>
     *                          <RequestParameters>
     *                              <FileName>
     *                                  ...
     *                              </FileName>
     *                              <ConversionTemplatePK></ConversionTemplatePK>
     *                              <ConversionJobName></ConversionJobName>
     *                              <GenericXMLOnly>Y/N</GenericXMLOnly> // only generate the raw XML
     *                              <ReturnSample>Y/N</ReturnSample> // return a sample of the conversion data if possible
     *                          </RequestParameters>
     *                      </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *                      <SEGResponseVO>
     *                          <ResponseMessageVO>
     *                              //TODO
     *                          </ResponseMessageVO>
     *                      </SEGResponseVO>
     */
    public Document runConversion(Document requestDocument);
    
    /**
     * Retrieves all inputFile meta data for every inputFile in the directory configured
     * to house the raw data conversion files.
     * @param requestDocument
     * @return
     */
    public Document getAllTestFiles(Document requestDocument);
}
