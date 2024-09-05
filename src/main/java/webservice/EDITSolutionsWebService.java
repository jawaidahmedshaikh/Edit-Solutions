/*
 * User: sprasad
 * Date: Aug 15, 2006
 * Time: 3:42:01 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package webservice;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;

/**
 * Gateway class for all soap requests.
 */
public class EDITSolutionsWebService
{
    /**
     * Receives all soap requests
     * @param soapRequest
     * @return
     * @throws AxisFault
     */
    public OMElement process(OMElement soapRequest) throws AxisFault
    {
        ServiceDispatcher serviceDispacther = new ServiceDispatcher(soapRequest);

        OMElement soapResponse =  serviceDispacther.dispatch();

        return soapResponse;
    }
}