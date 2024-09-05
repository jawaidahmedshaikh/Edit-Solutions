/*
 * User: sdorman
 * Date: Sep 19, 2006
 * Time: 9:10:26 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package webservice;

import org.dom4j.*;
import org.apache.axiom.om.*;
import org.apache.axis2.client.*;
import org.apache.axis2.*;
import org.apache.axis2.addressing.*;

import fission.utility.*;

/**
 * Utility class to ease the development of web service clients
 */
public class WebClientUtil
{
    private static final String HTTP = "http://";
    private static final String PORT_SEPARATOR = ":";
    private static final String WEB_SERVICE_NAME = "/PORTAL/services/EDITSolutionsWebService";


    /**
     * Sets up the web service message, attaches the specified document, runs the service and returns the results
     *
     * @param documentInput
     *
     * @return results from the web service
     *
     * @throws Exception
     */
    public static org.dom4j.Document process(Document input, String webServiceName, String hostName, String port) throws Exception
    {
        String epr = WebClientUtil.buildEndpointReference(hostName, port);

        Options options = new Options();
        options.setProperty(Constants.Configuration.ENABLE_MTOM, Constants.VALUE_TRUE);
        options.setTo(new EndpointReference(epr));
        options.setAction("process");

        ServiceClient sender = new ServiceClient();
        sender.setOptions(options);

        OMElement payload = WebServiceUtil.buildSOAPRequest("someone@equitrust.com", "someone@segsoftware.com", "Quote", webServiceName, input.asXML());

        OMElement responseElement = sender.sendReceive(payload);

        OMNamespace omNs = responseElement.getNamespace();
        OMElement workFolderElement = responseElement.getFirstChildWithName(new javax.xml.namespace.QName(omNs.getName(), "WorkFolder"));
        OMElement msgFileElement = workFolderElement.getFirstChildWithName(new javax.xml.namespace.QName(omNs.getName(), "MsgFile"));
        String decodedText = WebServiceUtil.decodeSOAPAttachment(msgFileElement.getText());

        org.dom4j.Document result = XMLUtil.parse(decodedText);

        return result;
    }

    private static String buildEndpointReference(String hostName, String port)
    {
        String epr = HTTP + hostName + PORT_SEPARATOR + port + WEB_SERVICE_NAME;

        return epr;
    }
}
