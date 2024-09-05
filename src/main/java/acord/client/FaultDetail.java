package acord.client;

import acord.client.type.CallType;

import org.apache.axiom.om.OMElement;

import webservice.WebServiceUtil;

public class FaultDetail implements OMElementParticipant
{
  /**
   * Textual description of this FaultDetail.
   */
  private String faultInfo;

  public FaultDetail(String faultInfo)
  {
    this.faultInfo = faultInfo;
  }
  
  public OMElement getState()
  {
    OMElement root = WebServiceUtil.buildOMElement("FaultDetail", "", CallType.NAMESPACE_AC);
    
    root.setText(getFaultInfo());
    
    return root;
  }

  public void setState(OMElement omElement)
  {
    setFaultInfo(omElement.getText());
  }

  /**
   * Setter.
   * @see #faultInfo
   * @param faultInfo
   */
  public void setFaultInfo(String faultInfo)
  {
    this.faultInfo = faultInfo;
  }

  /**
   * Getter.
   * @see #faultInfo
   * @return
   */
  public String getFaultInfo()
  {
    return faultInfo;
  }
}
