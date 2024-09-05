package acord.client.type;

import acord.client.OMElementParticipant;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;

import webservice.WebServiceUtil;


/**
 * An abstraction of the concept of "Client".
 */
public abstract class PartyType implements OMElementParticipant
{
  /**
   * Coded identification of a party.
   * Must be a URI, like a prefixed Industry code (e.g.
   * urn:duns:xxxxxxx) or an e-mail address (e.g.
   * jack.lang@bestbroker.com) or any URI compliant
   * construct that guarantees the uniqueness of the
   * identifier as a trading partner. Can be refined to the
   * desired granularity, from a corporation to an individual
   * person. Requires flexibility in trading partner code
   * management at receiver�s side as several URI will
   * typically identify the same partner.
   */
  private String partyId;

  /**
   * Coded value for the Party role in the business
   * transaction, e.g. broker.
   * <PartyRoleCd> is an essential piece in party
   * identification. Therefore it is a required element but it
   * can be empty if PartyId suffices to identify the party
   * unambiguously. .
   * The list is extensible to support external standards or
   * custom applications.
   */
  private String partyRoleCd;

  public static final Integer PARTY_TYPE_SENDER = new Integer(0);

  public static final Integer PARTY_TYPE_RECEIVER = new Integer(1);

  /**
   * Constructor.
   * @param partyId
   * @param partyRoleCd
   *
   * @see #partyId
   * @see #partyRoleCd
   * @see
   */
  public PartyType(String partyId, String partyRoleCd)
  {
    this.partyId = partyId;

    this.partyRoleCd = partyRoleCd;
  }
  
  /**
   * Constructor.
   * @param partyOMElement
   */
  public PartyType(OMElement partyOMElement)
  {
    setState(partyOMElement);
  }

  /**
   * Getter.
   * @see #partyId
   * @return
   */
  public String getPartyId()
  {
    return partyId;
  }

  /**
   * @see #partyRoleCd
   * @return
   */
  public String getPartyRoleCd()
  {
    return partyRoleCd;
  }


  public OMElement getState()
  {
    OMElement root = WebServiceUtil.buildOMElement("Party", "", CallType.NAMESPACE_AC);

    OMElement partyIdElement = WebServiceUtil.buildOMElement("PartyId", getPartyId(), CallType.NAMESPACE_AC);

    OMElement partyRoleCdElement = WebServiceUtil.buildOMElement("PartyRole", getPartyRoleCd(), CallType.NAMESPACE_AC);

    root.addChild(partyIdElement);

    root.addChild(partyRoleCdElement);

    return root;
  }


  public void setState(OMElement omElement)
  {
    String namespace = omElement.getNamespace().getName();

    // get the values
    OMElement partyIdOM = omElement.getFirstChildWithName(new QName(namespace, "PartyId"));
    
    OMElement partyRoleCd = omElement.getFirstChildWithName(new QName(namespace, "PartyRoleCd"));
    
    // set the values
    setPartyId(partyIdOM.getText());
    
    setPartyRoleCd(partyRoleCd.getText());
  }

  /**
   * Setter.
   * @see #partyId
   * @param partyId
   */
  public void setPartyId(String partyId)
  {
    this.partyId = partyId;
  }
  
  /**
   * Setter.
   * @see #partyRoleCd
   * @param partyRoleCd
   */
  public void setPartyRoleCd(String partyRoleCd)
  {
    this.partyRoleCd = partyRoleCd;
  }
}
