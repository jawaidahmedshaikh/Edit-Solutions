package acord.client.type;

import acord.client.Application;
import acord.client.OMElementParticipant;
import acord.client.Receiver;
import acord.client.Sender;
import acord.client.WorkFolder;

import edit.common.EDITDateTime;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;

import webservice.WebServiceUtil;


/**
 * Purpose: to send a single message as a request for immediate response.
 * Process:
 * 1. The client transfers a single request business message to the server
 * 2. The server answers immediately by transferring a single response business message, referring to the
 * request.
 * As the processing mode is synchronous, there is no mechanism to control that sent messages are
 * processed in sequential order at receiver's side.
 */
public abstract class CallType implements OMElementParticipant
{
  public static final OMNamespace NAMESPACE_AC = OMAbstractFactory.getOMFactory().createOMNamespace("http://www.ACORD.org/Standards/AcordMsgSvc/1.4.0", "ac");
  public static final OMNamespace NAMESPACE_DEFAULT = OMAbstractFactory.getOMFactory().createOMNamespace("http://www.ACORD.org/Standards/AcordMsgSvc/Call", "");
  public static final OMNamespace NAMESPACE_XSI = OMAbstractFactory.getOMFactory().createOMNamespace("http://www.w3.org/2001/XMLSchema-instance", "xsi");
  public static final String SCHEMA_LOCATION = "http://www.ACORD.org/Standards/AcordMsgSvc/1.4.0/AcordMsgSvc_v-1-4-0.xsd";
  public static OMAttribute SCHEMA_LOCATION_ATTRIBUTE = null;
  
  static
  {
    SCHEMA_LOCATION_ATTRIBUTE = OMAbstractFactory.getOMFactory().createOMAttribute("xsi:schemaLocation", null, SCHEMA_LOCATION);
  }

  /**
   * @see Sender
   */
  private Sender sender;

  /**
   * @see Receiver
   */
  private Receiver receiver;

  /**
   * @see Application
   */
  private Application application;

  /**
   * @see acord.client.type.MsgType
   */
  private MsgType msgItem;

  /**
   * @see WorkFolder
   */
  private WorkFolder workFolder;

  /**
   * Time stamp of the message, for logging purposes. This
   * is the generation time of the messaging operation,
   * independently of that of the business message that may
   * be transported.
   */
  private EDITDateTime timeStamp;

  protected CallType()
  {
    timeStamp = new EDITDateTime(System.currentTimeMillis());
  }

  public CallType(Sender sender, Receiver receiver, Application application, MsgType msgItem, WorkFolder workFolder)
  {
    this();
    
    this.sender = sender;

    this.receiver = receiver;

    this.application = application;

    this.msgItem = msgItem;

    this.workFolder = workFolder;
  }

  /**
   * @see #sender
   * @param sender
   */
  public void setSender(Sender sender)
  {
    this.sender = sender;
  }

  /**
   * @see #sender
   * @return
   */
  public PartyType getSender()
  {
    return sender;
  }

  /**
   * @see #receiver
   * @param receiver
   */
  public void setReceiver(Receiver receiver)
  {
    this.receiver = receiver;
  }

  /**
   * @see #sender
   * @return
   */
  public Receiver getReceiver()
  {
    return receiver;
  }

  /**
   *
   * @param application
   */
  public void setApplication(Application application)
  {
    this.application = application;
  }

  /**
   * @see #timeStamp
   * @return
   */
  public EDITDateTime getTimeStamp()
  {
    return timeStamp;
  }

  /**
   * @see #application
   * @return
   */
  public Application getApplication()
  {
    return application;
  }

  /**
   * @see #msgItem
   * @param msgItem
   */
  public void setMsgItem(MsgType msgItem)
  {
    this.msgItem = msgItem;
  }

  /**
   * @see #msgItem
   * @return
   */
  public MsgType getMsgItem()
  {
    return msgItem;
  }

  /**
   * @see #workFolder
   * @param workFolder
   */
  public void setWorkFolder(WorkFolder workFolder)
  {
    this.workFolder = workFolder;
  }

  /**
   * @see #workFolder
   * @return
   */
  public WorkFolder getWorkFolder()
  {
    return workFolder;
  }

  public OMElement getState()
  {
    // Establish the root of this element and its namespaces
    OMElement root = WebServiceUtil.buildOMElement("Call", "", NAMESPACE_AC);
    
    root.addAttribute(SCHEMA_LOCATION_ATTRIBUTE);   
    
    root.declareNamespace(NAMESPACE_DEFAULT);
    
    root.declareNamespace(NAMESPACE_XSI);
        
    OMElement senderOMElement = getSender().getState();
    
    OMElement receiverOMElement = getReceiver().getState();
    
    OMElement applicationOMElement = getApplication().getState();
    
    OMElement timeStampOMElement = WebServiceUtil.buildOMElement("TimeStamp", getTimeStamp().toString(), NAMESPACE_AC);

    OMElement msgItemOMElement = null;

    if (getMsgItem() != null)
    {
      msgItemOMElement = getMsgItem().getState();
    }

    OMElement workFolderOMElement = null;

    if (getWorkFolder() != null)
    {
        workFolderOMElement = getWorkFolder().getState();
    }

    root.addChild(senderOMElement);

    root.addChild(receiverOMElement);

    root.addChild(applicationOMElement);

    root.addChild(timeStampOMElement);

    if (msgItemOMElement != null)
    {
      root.addChild(msgItemOMElement);
    }

    if (workFolderOMElement != null)
    {
        root.addChild(workFolderOMElement);
    }

    return root;
  }

  public void setState(OMElement omElement)
  {
    String namespace = omElement.getNamespace().getName();
    
    // get the values
    OMElement timeStampOM = omElement.getFirstChildWithName(new QName(namespace, "TimeStamp"));
    
    OMElement applicationOM = omElement.getFirstChildWithName(new QName(namespace, "Application"));
    
    OMElement msgItemOM = omElement.getFirstChildWithName(new QName(namespace, "MsgItem"));
    
    OMElement receiverOM = omElement.getFirstChildWithName(new QName(namespace, "Receiver"));
    
    OMElement senderOM = omElement.getFirstChildWithName(new QName(namespace, "Sender"));
    
    OMElement workFolderOM = omElement.getFirstChildWithName(new QName(namespace, "WorkFolder"));
    
    // set the values
    setTimeStamp(new EDITDateTime(timeStampOM.getText()));
    
    setApplication(new Application(applicationOM));
    
    setMsgItem(new MsgType(msgItemOM));
    
    setReceiver(new Receiver(receiverOM));
    
    setSender(new Sender(senderOM));

    if (workFolderOM != null)
    {
        setWorkFolder(new WorkFolder(workFolderOM));
    }
  }
  
  /**
   * Setter.
   * @see #timeStamp
   * @param timeStamp
   */
  public void setTimeStamp(EDITDateTime timeStamp)
  {
    this.timeStamp = timeStamp;
  }
}
