/*
 * User: sprasad
 * Date: Aug 16, 2006
 * Time: 2:56:38 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package webservice;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;

import javax.xml.namespace.QName;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Dispatcher class for soap service.
 */
public class ServiceDispatcher
{
    public static final String NEW_BUSINESS_QUOTE_SERVICE_NAME = "NewBusinessQuote";
    public static final String GET_INTEREST_RATES_SERVICE_NAME = "GetInterestRates";
    public static final String GET_CURRENT_CLIENT_ADDRESS_SERVICE_NAME = "GetCurrentClientAddress";

    private OMElement soapRequest;

    /**
     * Place holder for service names and corresponding service classes.
     */
    public static Map servicesMap;

    static
    {
        // This is temporary solution, later this might be replaced with database table or configuration file
        // where we can map service names to service classes.
        // This would be helpful if in future we add more services, we can add a new entry in the appropriate place
        // when the service is ready for use.
        // or other alternate would be put service name as fully qualified class name.
        servicesMap = new HashMap();
        servicesMap.put(NEW_BUSINESS_QUOTE_SERVICE_NAME, "webservice.service.NewBusinessQuote");
        servicesMap.put(GET_INTEREST_RATES_SERVICE_NAME, "webservice.service.GetInterestRates");
        servicesMap.put(GET_CURRENT_CLIENT_ADDRESS_SERVICE_NAME, "webservice.service.GetCurrentClientAddress");
    }

    /**
     * Constructor
     * @param soapRequest
     */
    public ServiceDispatcher(OMElement soapRequest)
    {
        this.soapRequest = soapRequest;
    }

    /**
     * Dispatches the request to corresponding services interpreting the soap message.
     * @return
     * @throws AxisFault
     */
    public OMElement dispatch() throws AxisFault
    {
        OMElement soapResponse = null;

        try
        {
            String ns = soapRequest.getNamespace().getName();

            OMElement msgItemElement = soapRequest.getFirstChildWithName(new QName(ns, "MsgItem"));

            OMElement msgTypeCdElement = msgItemElement.getFirstChildWithName(new QName(ns, "MsgTypeCd"));

            String serviceName = msgTypeCdElement.getText();

            soapResponse =  invokeService(serviceName);
        }
        // Not sure whether we should be throwing runtime exceptions and let axis take care of it
        // or should we manually build response message.
        // If we leave to axis the axis throws AxisFault exception to the client.
        catch (Throwable e)
        {
          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new AxisFault(e);
        }

        return soapResponse;
    }

    /**
     * Invokes/initiates the corresponding service.
     * @param serviceName
     * @return
     * @throws Exception
     */
    private OMElement invokeService(String serviceName) throws Exception
    {
        String fullyQualifiedServiceClassName = getServiceClassName(serviceName);

        OMElement soapResponse = null;

        Object serviceObject = null;

        Method method = null;

        try
        {
            Class serviceClass = Class.forName(fullyQualifiedServiceClassName);

            serviceObject = serviceClass.newInstance();

            method = serviceClass.getMethod("execute", new Class[] {OMElement.class});

            soapResponse = (OMElement) method.invoke(serviceObject, new Object[] {soapRequest});
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw e;
        }

        return soapResponse;
    }

    /**
     * Helper method to find the corresponding service class for given service name.
     * @param serviceName
     * @return
     */
    private String getServiceClassName(String serviceName)
    {
        String fullyQualifiedServiceClassName = null;

        if (NEW_BUSINESS_QUOTE_SERVICE_NAME.equals(serviceName)
                || GET_INTEREST_RATES_SERVICE_NAME.equals(serviceName)
                || GET_CURRENT_CLIENT_ADDRESS_SERVICE_NAME.equals(serviceName))
        {
            fullyQualifiedServiceClassName = (String) servicesMap.get(serviceName);
        }
        else
        {
            throw new RuntimeException("A Service With Given Service Name Does Not Exist.");
        }

        return fullyQualifiedServiceClassName;
    }
}