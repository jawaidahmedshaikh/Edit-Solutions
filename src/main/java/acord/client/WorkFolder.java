package acord.client;

import acord.client.type.CallType;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;

import webservice.WebServiceUtil;


/**
 * Contains the information to access the business message transported and describes an
 * optional list of physical attachment packages related to the business message in some manner.
 */
public class WorkFolder implements OMElementParticipant
{
  /**
  * @see MsgFile
  */
  private MsgFile msgFile;

  private WorkFolder()
  {
  }

  /**
   * Constructor.
   * @param msgFile
   */
  public WorkFolder(MsgFile msgFile)
  {
    this.msgFile = msgFile;
  }
  
  public WorkFolder(OMElement workFolderOMElement)
  {
    setState(workFolderOMElement);
  }

  /**
   * @see #msgFile
   * @param msgFile
   */
  public void setMsgFile(MsgFile msgFile)
  {
    this.msgFile = msgFile;
  }

  /**
   * @see #msgFile
   * @return
   */
  public MsgFile getMsgFile()
  {
    return msgFile;
  }

  public OMElement getState()
  {
    OMElement root = WebServiceUtil.buildOMElement("WorkFolder", "", CallType.NAMESPACE_AC);
    
    OMElement msgFileOM = getMsgFile().getState();
    
    root.addChild(msgFileOM);
    
    return root;
  }

  public void setState(OMElement omElement)
  {
    String namespace = omElement.getNamespace().getName();
    
    // get the values
    OMElement msgFileOM = omElement.getFirstChildWithName(new QName(namespace, "MsgFile"));
    
    // set the values
    setMsgFile(new MsgFile(msgFileOM));
  }
}
