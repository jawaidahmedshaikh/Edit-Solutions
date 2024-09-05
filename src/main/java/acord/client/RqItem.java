package acord.client;

import acord.client.type.CallType;
import acord.client.type.MsgType;

import org.apache.axiom.om.OMElement;

import webservice.WebServiceUtil;


public class RqItem extends MsgType
{
  /**
   * 
   */
  private String msgStatusCd;

  public RqItem(String msgTypeCd, String msgStatusCd)
  {
    super(msgTypeCd);
    
    this.msgStatusCd = msgStatusCd;
  }

  public OMElement getState()
  {
    OMElement root = super.getState();

    root.setLocalName("RqItem");
    
    OMElement msgStatusCDElement = WebServiceUtil.buildOMElement("MsgStatusCd", getMsgStatusCd(), CallType.NAMESPACE_AC);

    root.addChild(msgStatusCDElement);

    return root;
  }

  /**
   * Setter.
   * @see #msgStatusCd
   * @param msgStatusCd
   */
  public void setMsgStatusCd(String msgStatusCd)
  {
    this.msgStatusCd = msgStatusCd;
  }

  /**
   * @see #msgStatusCd
   * @return
   */
  public String getMsgStatusCd()
  {
    return msgStatusCd;
  }
}
