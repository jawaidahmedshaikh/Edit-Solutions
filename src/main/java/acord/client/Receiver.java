package acord.client;

import acord.client.type.PartyType;

import org.apache.axiom.om.OMElement;

/**
 * The ultimate issuer or consumer of a message, which
 * has a trading relationship with the ultimate sender. By
 * Receiver, we mean the organization that sits at the
 * server side of the messaging service. This can be a
 * Service Provider intermediary provided that processing
 * of the message is performed by the Service Provider.
 */
public class Receiver extends PartyType implements OMElementParticipant
{
  /**
   * Constructor.
   * @param partyId
   * @param partyRoleCd
   */
  public Receiver(String partyId, String partyRoleCd)
  {
    super(partyId, partyRoleCd);
  }
  
  /**
   * Constructor.
   * @param receiverOMElement
   */
  public Receiver(OMElement receiverOMElement)
  {
    super(receiverOMElement);    
  }

  public OMElement getState()
  {
    OMElement root = super.getState();

    root.setLocalName("Receiver");

    return root;
  }
}
