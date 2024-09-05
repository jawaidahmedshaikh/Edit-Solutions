package test.acord.client;

import org.apache.axiom.om.OMElement;

import org.apache.axis2.addressing.EndpointReference;

import webservice.WebServiceUtil;

public class CallRqTestFixture
{
  

  public CallRqTestFixture()
  {
  }

  public void setUp()
  {
  }

  public void tearDown()
  {
  }
  
  public OMElement getBasicCallRqAsOMElement()
  {
    return WebServiceUtil.getAsOMElement(getBasicCallRqAsXML());
  }
  
  /**
   * Convenience method to build a standard CallRq document.
   * @return
   */
  public String getBasicCallRqAsXML()
  {
    String callRqXML = "<ac:CallRq xmlns=\"http://www.ACORD.org/Standards/AcordMsgSvc/Call\" xmlns:ac=\"http://www.ACORD.org/Standards/AcordMsgSvc/1.4.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" + 
    "xsi:schemaLocation=\"http://www.ACORD.org/Standards/AcordMsgSvc/1.4.0/AcordMsgSvc_v-1-4-0.xsd\">\n" + 
    "<ac:Sender>\n" + 
    "<ac:PartyId>urn:duns:123456789</ac:PartyId>\n" + 
    "<ac:PartyRoleCd>Agent</ac:PartyRoleCd>\n" + 
    "</ac:Sender>\n" + 
    "<ac:Receiver>\n" + 
    "<ac:PartyId>urn:duns:912345678</ac:PartyId>\n" + 
    "<ac:PartyRoleCd/>\n" + 
    "</ac:Receiver>\n" + 
    "<ac:Application>\n" + 
    "<ac:ApplicationCd>ACORD-PC-Surety</ac:ApplicationCd>\n" + 
    "<ac:SchemaVersion>http://www.ACORD.org/standards/PC_Surety/ACORD1.4.0/xml/</ac:SchemaVersion>\n" + 
    "</ac:Application>\n" + 
    "<ac:TimeStamp>2006/09/20 11:32:10</ac:TimeStamp>\n" + 
    "<ac:MsgItem>\n" + 
    "<ac:MsgId>f81d4fae-7dec-11d0-a765-00a0c91e6bf9</ac:MsgId>\n" + 
    "<ac:MsgTypeCd>InsuranceSvcRq</ac:MsgTypeCd>\n" + 
    "</ac:MsgItem>\n" + 
    "<ac:WorkFolder>\n" + 
    "<ac:MsgFile>\n" + 
    "<ac:FileId>cid:A01EFAE7-5490-43D0-AB6B-DAEF1671CDCC</ac:FileId>\n" + 
    "<ac:FileFormatCd>text/xml</ac:FileFormatCd>\n" + 
    "</ac:MsgFile>\n" + 
    "</ac:WorkFolder>\n" + 
    "</ac:CallRq>";    
    
    return callRqXML;
  }  
  
  /**
   * Assumes the format of:
   * http://host:port/PORTAL/services/serviceName
   * ... where host, port, and serviceName are specified.
   * @param host
   * @param port
   * @param serviceName
   * @return
   */
  public EndpointReference getEndointReference(String host, String port, String serviceName)
  {
    String ref = "http://" + host + ":" + port + "/PORTAL/services/" + serviceName;
    
    return new EndpointReference(ref);
  }
}
