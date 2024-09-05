package test.acord.client;


import acord.client.CallRq;

import acord.client.CallRs;

import acord.client.MsgFile;
import acord.client.WorkFolder;

import acord.client.type.MsgType;

import edit.common.EDITDateTime;

import junit.framework.TestCase;

import org.apache.axiom.om.OMElement;

public class CallRsTest extends TestCase
{
  private CallRqTestFixture callRqTestFixture;  

  protected void setUp() throws Exception
  {
    super.setUp();
    
    callRqTestFixture = new CallRqTestFixture();
  }

  protected void tearDown() throws Exception
  {
    super.tearDown();
  }
  
  public CallRsTest(String sTestName)
  {
    super(sTestName);
  }
  
  /**
   * Much of the information from the original CallRq is repeated in the
   * CallRs. Sender, Receiver, Application are pure repeats. The RqItem
   * repeats the CallRq's MsgItem MsgId and MsgTypeCD (as validation of the
   * message) and it adds a MsgStatusCd to know if the message request was
   * actually completed (for example). This tests that the original CallRq
   * is respected while new information is added.
   * @throws Exception
   */
  public void testCallRqConstructor() throws Exception
  {
    CallRq callRq = new CallRq(callRqTestFixture.getBasicCallRqAsOMElement());
    
    WorkFolder workFolder = new WorkFolder(new MsgFile(MsgFile.FILE_FORMAT_TEXT_XML, ""));
    
    CallRs callRs = new CallRs(callRq, MsgType.MSGTYPECD_COMPLETED, workFolder);
    
    OMElement callRsAsOMElement = callRs.getState();
    
    System.out.println(callRsAsOMElement);
  }
}
