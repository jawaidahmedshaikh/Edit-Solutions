package acord.client;

import acord.client.type.PartyType;

import org.apache.axiom.om.OMElement;


/**
 * The ultimate issuer of a message.
 * This is backed by the concept of Sender.
 *
 * By Sender, we mean the organization that sits at the client
 * side of the messaging service.
 */
public class Sender extends PartyType
{
  /**
   * Constructor.
   * @param partyId
   * @param partyRoleCd
   */
  public Sender(String partyId, String partyRoleCd)
  {
    super(partyId, partyRoleCd);
  }
  
  /**
   * Constructor.
   * @param senderOMElement
   */
  public Sender(OMElement senderOMElement)
  {
    super(senderOMElement);
  }

  public OMElement getState()
  {
    OMElement root = super.getState();

    root.setLocalName("Sender");

    return root;
  }
}
