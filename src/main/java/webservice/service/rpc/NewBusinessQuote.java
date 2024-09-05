/*
 * User: sprasad
 * Date: Nov 17, 2006
 * Time: 12:59:22 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package webservice.service.rpc;

import org.apache.axis2.AxisFault;

/**
 * RPC version of the New Business Quote service. The user of this service should
 * pay special attention to the NewBusinessQuoteInput. There are fields defined 
 * within NewBusinessQuote that are optional. However, our experience shows
 * that some SOAP servers have issues with receiving null values. For this 
 * reason, ALL param values are required and can not be null. The service will 
 * simply ignore that values that are not needed for the type of quote desired. 
 * The user is strongly urged to read the javadoc for for the NewBusinessQuoteInput 
 * where proper use is shown with examples. 
 * @see NewBusinessQuoteInput
 */
public interface NewBusinessQuote
{
  /**
   * Generates a quote for New Business returning such information as the
   * finalDistributionAmount and the totalProjectedAnnuity (among others).
   * The service is backed by the a Request/Response Message Exchange Pattern (MEP)
   * where the calling client makes a request, but then is required to wait for a
   * response (blocking).
   * @see NewBusinessQuoteInput
   * @param newBusinessQuoteInput
   * @return
   * @throws AxisFault
   */
  public NewBusinessQuoteOutput getNewBusinessQuote(NewBusinessQuoteInput newBusinessQuoteInput) throws AxisFault;
}
