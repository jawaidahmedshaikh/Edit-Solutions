package edit.services.command;

import fission.utility.XMLUtil;

import java.io.StringReader;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * External requests can use the well-defined structure of the 
 * SEGRequestVO which assumes the following form:
 * 
 * <SEGRequestVO>
 *      <Service></Service>
 *      <Operation></Operation>
 *      <RequestParameters></RequestParameters>
 * </SEGRequestVO>
 * 
 * This class performs common operations against this XML structure.
 */
public class SEGRequest
{
    /**
     * The name of the Element that contains the service name.
     */
    public static final String SERVICE_NAME = "Service";
    
    /**
     * The name of the Element that contains the service operation.
     */
    public static final String OPERATION_NAME = "Operation";

    /**
     * The name of the Element that contains the operator (user) calling the service.
     */
    public static final String OPERATOR = "Operator";

    /**
     * The name of the Element that contains the request parameters.
     */
    public static final String REQUEST_PARAMETERS_NAME = "RequestParametersName";

    /**
     * The incoming request in the form of XML.
     */
    private Document segRequestVODocument;
    
    public SEGRequest(String segRequestVOAsXML)
    {
        convertToDOM(segRequestVOAsXML);
    }
    
    /**
     * The name of the request service.
     * 
     * @return
     */
    public String getServiceName()
    {
        List<Element> results = XMLUtil.getChildren(SERVICE_NAME, getSegRequestVODocument().getRootElement());
        
        Element serviceNameElement = results.get(0);
        
        return serviceNameElement.getText();
    }
    
    /**
     * The targeted operation of the specified service.
     * @return
     */
    public String getOperationName()
    {
        List<Element> results = XMLUtil.getChildren(OPERATION_NAME, getSegRequestVODocument().getRootElement());
        
        Element serviceNameElement = results.get(0);
        
        return serviceNameElement.getText();        
    }

    /**
     * The operator calling the service.
     * @return
     */
    public String getOperatorName()
    {
        List<Element> results = XMLUtil.getChildren(OPERATOR, getSegRequestVODocument().getRootElement());

        Element operatorNameElement = results.get(0);

        return operatorNameElement.getText();
    }

    /**
     * Converts the specified XML as String to its Document
     * equivalent.
     * 
     * @param segRequestVOAsXML
     */
    private final void convertToDOM(String segRequestVOAsXML)
    {
        try
        {
            this.segRequestVODocument = XMLUtil.parse(segRequestVOAsXML);
        }
        catch (DocumentException e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            throw new RuntimeException(e);
        }
    }

    /**
     * @see #segRequestVODocument
     * @return
     */
    public Document getSegRequestVODocument()
    {
        return segRequestVODocument;
    }
}
