package acord.client;

import acord.client.type.CallType;

import org.apache.axiom.om.OMElement;

/**
 * Purpose: to send a single message as a request for immediate response.
 * Process:
 * 1. The client transfers a single request business message to the server
 * 2. The server answers immediately by transferring a single response business message, referring to the
 * request.
 * As the processing mode is synchronous, there is no mechanism to control that sent messages are
 * processed in sequential order at receivers side.
 */
public class CallRs extends CallType
{
  /**
   * @see RqItem
   */
  private RqItem rqItem;

  /**
   * Constructor.
   * @param sender
   * @param receiver
   * @param application
   * @param rqItem
   * @param workFolder
   */
  public CallRs(Sender sender, Receiver receiver, Application application, RqItem rqItem, WorkFolder workFolder)
  {
    super(sender, receiver, application, null, workFolder);
    
    setRqItem(rqItem);
  }
  
  /**
   * Constructor. The CallRs mirrors (largely) the CallRq. Specifically:
   * 1. The CallRq.Sender becomes the CallRs.Receiver.
   * 2. The CallRq.Receiver becomed the CallRs.Sender.
   * 3. The CallRq.Application is the same as CallRs.Application.
   * 4. The CallRs's msgStatusCd simply adds this field to the CallRq.MsgItem in 
   * order to form the CallRs.RqItem.
   * @param callRq
   * @param msgStatusCd
   * @param workFolder
   */
  public CallRs(CallRq callRq, String msgStatusCd, WorkFolder workFolder)
  {
    this(new Sender(callRq.getReceiver().getPartyId(), callRq.getReceiver().getPartyRoleCd()), new Receiver(callRq.getSender().getPartyId(), callRq.getSender().getPartyRoleCd()), callRq.getApplication(), new RqItem(callRq.getMsgItem().getMsgTypeCd(), msgStatusCd), workFolder);
  }
  
  /**
   * Constructor.
   * Initializes this CallRs to the state as specified by the OMElement.
   * @param callRsOMElement
   */
  public CallRs(OMElement callRsOMElement)
  {
    setState(callRsOMElement);
  }

  public OMElement getState()
  {
    OMElement root = super.getState();

    root.setLocalName("CallRs");
    
    OMElement rqItemOMElement = getRqItem().getState();
    
    root.addChild(rqItemOMElement);

    return root;
  }
  
  public void setState(OMElement omElement)
  {
    super.setState(omElement);
  }

  /**
   * Setter.
   * @see #rqItem
   * @param rqItem
   */
  public void setRqItem(RqItem rqItem)
  {
    this.rqItem = rqItem;
  }

  /**
   * Getter.
   * @see #rqItem
   * @return
   */
  public RqItem getRqItem()
  {
    return rqItem;
  }
}
