package acord.client;

import org.apache.axiom.om.OMElement;


/**
 * SOAP attachment representations have
 */
public interface OMElementParticipant
{
  /**
   * The implementing class is expected to represent its state (as its simple
   * attributes) compatable with an OMElement. If the current (parent) object building
   * its OMElement has child objects which are also OMElementParticipants, then
   * it is expected that the parent will call its child getState() method(s)
   * and add these children to itself.
   * @return the state of the implementing class as an OMElement
   */
  public OMElement getState();
  
  /**
   * The implementing class is to set its simple attributes via the 
   * specified OMElement as well as pass an child OMElements to its 
   * associated children so they may further the composition process.
   * @param omElement
   */
  public void setState(OMElement omElement);
}
