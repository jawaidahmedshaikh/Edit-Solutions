package acord.client;

import acord.client.type.CallType;

import acord.client.type.MsgType;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;

import org.apache.axis2.client.Options;

import org.apache.axis2.client.ServiceClient;

import webservice.WebServiceUtil;


/**
 * Purpose: to send a single message as a request for immediate response.
 * Process:
 * 1. The client transfers a single request business message to the server
 * 2. The server answers immediately by transferring a single response business message, referring to the
 * request.
 * As the processing mode is synchronous, there is no mechanism to control that sent messages are
 * processed in sequential order at receiver's side.
 */
public class CallRq extends CallType
{
  private OMElement callRqOMElement = null;

  /**
   * Subtle differences are likely between the clients to different
   * SOAP Servers. The client needs to specify which client API they
   * are using.
   */
  public static final int TARGET_SOAP_VENDOR_AXIS2 = 0;

  /**
   * Constructor.
   * @param sender
   * @param receiver
   * @param application
   * @param msgItem
   * @param workFolder
   */
  public CallRq(Sender sender, Receiver receiver, Application application, MsgItem msgItem, WorkFolder workFolder)
  {
    super(sender, receiver, application, msgItem, workFolder);
  }

  /**
   * Constructor.
   * Initializes this CallRq to the state as specified by the OMElement.
   * @param callRqOMElement
   */
  public CallRq(OMElement callRqOMElement)
  {
    setState(callRqOMElement);
  }

  /**
   * @return
   */
  public OMElement getState()
  {
    if (callRqOMElement == null)
    {
      callRqOMElement = super.getState();

      callRqOMElement.setLocalName("CallRq");
    }

    return callRqOMElement;
  }

  /**
   * Generates a payload based on this CallRq and sends it to
   * the specified EndpointReference. Configuration of this
   * send/receive assumes
   * @param endpointReference
   * @param TARGET_SOAP_VENDOR
   * @return the response of this send/receive as either a CallRq or a Fault
   * @see #TARGET_SOAP_VENDOR_AXIS2
   */
  public Object sendReceive(EndpointReference endpointReference, int TARGET_SOAP_VENDOR) throws AxisFault
  {
    CallRs response = null;

    try
    {
      OMElement payload = getState();

      Options options = new Options();

      options.setProperty(Constants.Configuration.ENABLE_MTOM, Constants.VALUE_TRUE);

      options.setTo(endpointReference);

      options.setAction("process");

      ServiceClient sender = new ServiceClient();

      sender.setOptions(options);

      OMElement responseOM = sender.sendReceive(payload);

      System.out.println(responseOM);

      response = new CallRs(responseOM);
    }
    catch (AxisFault e)
    {
      System.out.println(e);

      e.printStackTrace();
      
      // We need to respect the many kinds of faults possible.
      // For now, just defaulting to the "unknown" Fault with
      // a FaultDetail message.
      Fault fault = Fault.FAULT_26;
      
      fault.setFaultDetail(new FaultDetail(e.getMessage()));
      
      e.setDetail(fault.getFaultDetail().getState());
      
      throw e;
    }

    return response;
  }
}
