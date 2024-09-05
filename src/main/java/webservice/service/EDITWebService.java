/*
 * User: sprasad
 * Date: Aug 16, 2006
 * Time: 3:32:19 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package webservice.service;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;

public interface EDITWebService
{
    public OMElement execute(OMElement request) throws AxisFault;
}
