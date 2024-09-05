package acord.client;

import acord.client.type.MsgType;

import org.apache.axiom.om.OMElement;

public class MsgItem extends MsgType
{
  public MsgItem(String msgTypeCd)
  {
    super(msgTypeCd);
  } 
  
  public OMElement getState()
  {
    OMElement root = super.getState();

    root.setLocalName("MsgItem");

    return root;
  }
}
