package test.acord.client;

import acord.client.Application;
import acord.client.CallRq;
import acord.client.MsgFile;
import acord.client.MsgItem;
import acord.client.type.MsgType;
import acord.client.Receiver;
import acord.client.Sender;
import acord.client.WorkFolder;

import junit.framework.TestCase;

import org.apache.axiom.om.OMElement;

import org.apache.axis2.addressing.EndpointReference;

import webservice.WebServiceUtil;


public class CallRqTest extends TestCase
{
  private CallRqTestFixture callRqTestFixture;

  public CallRqTest(String sTestName)
  {
    super(sTestName);
  }
  
  protected void setUp() throws Exception
  {
    super.setUp();
    
    callRqTestFixture = new CallRqTestFixture();
  }

  protected void tearDown() throws Exception
  {
    super.tearDown();
  }  

  /**
   * CallRq can represent itself as XML.
   * @throws Exception
   */
  public void testStandardConstructorToXML() throws Exception
  {
    Sender sender = new Sender("sender@foo.com", "fooPartyRoleCd");

    Receiver receiver = new Receiver("receiver@foo.com", "fooPartyRoleCd");

    Application application = new Application("fooApplicationCd");

    MsgItem msgItem = new MsgItem("QuoteSvcRq");

    MsgFile msgFile = new MsgFile(MsgFile.FILE_FORMAT_TEXT_XML, "");

    WorkFolder workFolder = new WorkFolder(msgFile);

    CallRq callRq = new CallRq(sender, receiver, application, msgItem, workFolder);

    OMElement callRqOMElement = callRq.getState();
    
    System.out.println("callRq: " + callRqOMElement);

    // Test to see if the core nested elements are there:
    // e.g.
    // CallRq, MsgItem, WorkFolder, Sender, Receiver, Application
    assertTrue(callRqOMElement.toString().indexOf("<ac:CallRq") >= 0);
    assertTrue(callRqOMElement.toString().indexOf("<ac:MsgItem") >= 0);
    assertTrue(callRqOMElement.toString().indexOf("<ac:WorkFolder") >= 0);
    assertTrue(callRqOMElement.toString().indexOf("<ac:Sender") >= 0);
    assertTrue(callRqOMElement.toString().indexOf("<ac:Receiver") >= 0);
    assertTrue(callRqOMElement.toString().indexOf("<ac:Application") >= 0);
  }
  
  /**
   * The acord.client API needs to be able to de-serialize its state
   * from a supplied OMElement. The OMElement used in this test is the
   * CallRq XML (as OMElement).
   * @throws Exception
   */
  public void testOMElementConstructorToXML() throws Exception
  {
    String callRqXML = callRqTestFixture.getBasicCallRqAsXML().replaceAll("\n", " ");
   
    OMElement callReqOM = callRqTestFixture.getBasicCallRqAsOMElement();
   
    String  generatedCallRqXML = callReqOM.toString();
    
    generatedCallRqXML = generatedCallRqXML.replaceAll("\n"," ");
   
    assertEquals(callRqXML.replaceAll(" ", "").length(), generatedCallRqXML.replaceAll(" ", "").length());
  }
  
  /**
   * The CallRq can send itself as a payload to the targeted SOAP service.
   * @throws Exception
   */
  public void testCallRqSendReceive() throws Exception
  {
    CallRq callRq = new CallRq(callRqTestFixture.getBasicCallRqAsOMElement());
    
    callRq.sendReceive(callRqTestFixture.getEndointReference("localhost", "8988", "EDITSolutionsWebService"), CallRq.TARGET_SOAP_VENDOR_AXIS2);
  }
}
