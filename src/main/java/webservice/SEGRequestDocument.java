/*
 * User: sdorman
 * Date: Aug 7, 2007
 * Time: 9:47:22 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package webservice;

import edit.services.db.hibernate.SessionHelper;

import engine.sp.*;

import fission.utility.Util;
import fission.utility.XMLUtil;

import org.dom4j.*;
import org.dom4j.tree.*;

/**
 * A convenience class to handle the request information for services
 * <P>
 * The format of the request is as follows:
 *
 *  <SEGRequestVO>
 * 	    <Service/> The service name (e.g. "Billing")
 * 	    <Operation/> The operation to invoke that is associated with the service (e.g. "getBillGroupNotPaid")
 * 	    <RequestParameters> // repeated for as many name/value pairs as are required.
 * 		    <Name1>Value1</Name1>
 * 		    <Name2>Value2</Name2>
 * 		    <MyXML>
 * 			    <Foo1>
 * 				    <Foo2/>
 * 			    </Foo2>
 * 		    </MyXML>
 * 		    etc.
 * 	    </RequestParameters>
 * </SEGRequestVO>
 */
public class SEGRequestDocument
{

    private Document document;
    private Element rootElement;
    private Element requestParameters;

    private static final String REQUEST_TITLE = "SEGRequestVO";
    private static final String SERVICE = "Service";
    private static final String OPERATION = "Operation";
    private static final String OPERATOR = "Operator";
    private static final String REQUEST_PARAMETERS = "RequestParameters";

    public SEGRequestDocument()
    {
        this.document = new DefaultDocument();
        this.rootElement = new DefaultElement(REQUEST_TITLE);
        this.requestParameters = new DefaultElement(REQUEST_PARAMETERS);

        this.rootElement.add(this.requestParameters);

        this.document.setRootElement(rootElement);
    }

    public Document getDocument()
    {
        return this.document;
    }

    public Element getService()
    {
        return this.rootElement.element(SERVICE);
    }

    public Element getOperation()
    {
        return this.rootElement.element(OPERATION);
    }

    public Element getOperator()
    {
        return this.rootElement.element(OPERATOR);
    }

    public Element getRequestParameters()
    {
        return this.rootElement.element(REQUEST_PARAMETERS);
    }

    public void setService(Element serviceElement)
    {
        serviceElement.setName(SERVICE);

        this.rootElement.add(serviceElement);
    }

    public void setOperation(Element operationElement)
    {
        operationElement.setName(OPERATION);

        this.rootElement.add(operationElement);
    }

    public void setOperator(Element operatorElement)
    {
        operatorElement.setName(OPERATOR);

        this.rootElement.add(operatorElement);
    }

    public void addToRequestParameters(Element element)
    {
        this.requestParameters.add(element);
    }

    /**
     * Returns the "RequestParameters" Element from the specified request Document.
     * @return
     */
    public static Element getRequestParameters(Document requestDocument)
    {
        Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

        return requestParametersElement;
    }

    /**
     * Returns the value of the specified requestParameter within the request Document.
     * @param requestDocument
     * @return the value of the named requestParameter, or null if the Element did not exist
     */
    public static Element getRequestParameter(Document requestDocument, String requestParameterName)
    {
        Element requestParameters = getRequestParameters(requestDocument);

        Element requestParameterElement = requestParameters.element(requestParameterName);

        return requestParameterElement;
    }
    
    /**
     * Returns the value of the specified requestParameter within the request Document.
     * @param requestDocument
     * @return the value of the named requestParameter, or null if the Element did not exist
     */
    public static String getRequestParameterValue(Document requestDocument, String requestParameterName)
    {
        String requestParameterValue = null;

        Element requestParameterElement = getRequestParameter(requestDocument, requestParameterName);

        if (requestParameterElement != null)
        {
            requestParameterValue = requestParameterElement.getText();
        }

        return requestParameterValue;
    }
    
    /**
     * If the <GeneratePRASETest></GeneratePRASETest> flag has been set within the request parameters, a ThreadLocal
     * flag is set that will propagate to PRASE. Within PRASE, this ThreadLocal flag will be
     * detected, and a test case will be dynamically generated within PRASE.
     * @param
     */
    public static void checkForGeneratePRASETest(Document requestDocument)
    {
        String generatePRASETest = Util.initString(getRequestParameterValue(requestDocument, "GeneratePRASETest"), "false");

        if (generatePRASETest.equals("true"))
        {
            SessionHelper.putInThreadLocal(SPTest.CREATE_SP_TEST, SPTest.CREATE_SP_TEST_YES);
        }
    }

    /**
     * If the <RecordPRASE></RecordPRASE> flag has been set within the request parameters, a ThreadLocal
     * flag is set that will propagate to PRASE. Within PRASE, this ThreadLocal flag will be
     * detected, and the PRASE events will be recorded during its run.
     * @param
     */
    public static void checkForRecordPRASE(Document requestDocument)
    {
        String recordPRASE = Util.initString(getRequestParameterValue(requestDocument, "RecordPRASE"), "false");

        if (recordPRASE.equals("true"))
        {
            SessionHelper.putInThreadLocal(SPRecorder.RECORD_SP, SPRecorder.RECORD_SP_YES);
        }
    }
}
