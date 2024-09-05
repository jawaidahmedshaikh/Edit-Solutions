package acord.client;

import acord.client.type.CallType;

import org.apache.axiom.om.OMElement;

import webservice.WebServiceUtil;


public class Application implements OMElementParticipant
{
  /**
   * Identification code of the application.
   * By default the code identifies one of the ACORD
   * standard segments. The values for ACORD are:
   * Reinsurance: 'Jv-Ins-Reinsurance'
   * Property & Casualty: 'ACORD-PC-Surety'
   * Life & Annuity: 'ACORD-TXLife'
   * Document Repository Interface: 'ACORDRepository'
   * The list is extensible to support external standards or
   * custom applications.
   */
  private String applicationCd;

  /**
   * Constructor.
   * @see #applicationCd
   * @param applicationCd
   */
  public Application(String applicationCd)
  {
    this.applicationCd = applicationCd;
  }
  
  /**
   * 
   * @param applicationOMElement
   */
  public Application(OMElement applicationOMElement)
  {
    setState(applicationOMElement);
  }

  /**
   * @see #applicationCd
   * @return
   */
  public String getApplicationCd()
  {
    return applicationCd;
  }

  /**
   * @see #applicationCd
   * @param applicationCd
   */
  public void setApplicationCd(String applicationCd)
  {
    this.applicationCd = applicationCd;
  }

  public OMElement getState()
  {
    OMElement root = WebServiceUtil.buildOMElement("Application", "", CallType.NAMESPACE_AC);

    OMElement applicationCdElement = WebServiceUtil.buildOMElement("ApplicationCd", getApplicationCd(), CallType.NAMESPACE_AC);

    root.addChild(applicationCdElement);

    return root;
  }

  public void setState(OMElement omElement)
  {
    setApplicationCd(omElement.getText());
  }
}
