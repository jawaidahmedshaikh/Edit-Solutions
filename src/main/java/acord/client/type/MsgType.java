package acord.client.type;


import acord.client.OMElementParticipant;

import fission.utility.Util;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.util.UUIDGenerator;

import webservice.WebServiceUtil;


/**
 * The identification, type and status of a message.
 */
public class MsgType implements OMElementParticipant
{
  /**
   * The globally unique identifier of a message.
   */
  private String msgId;

  /**
   * The type of business message.
   * Allowed values for P&C: 'AccountingSvcRq',
   * 'BaseSvcRq', 'ClaimsSvcRq', 'ExtensionsSvcRq',
   * 'InsuranceSvcRq', 'SuretySvcRq' or
   * 'AccountingSvcRs', 'BaseSvcRs', 'ClaimsSvcRs',
   * 'ExtensionsSvcRs', 'InsuranceSvcRs', 'SuretySvcRs'*
   * The code values are those of business message names
   * supported by the application or standard identified in the
   * <Application> aggregate.
   * The list is extensible to support external standards or
   * custom applications.
   */
  private String msgTypeCd;
  
  /**
   * Message has been received.
   * 
   * Applies To:
   * PostRs, StatusInRs, StatusOutRq, ListoutRq.
   */
  public static final String MSGTYPECD_RECEIVED = "received";
  
  /**
   * All the documents in the WorkFolder have been unwrapped successfully.
   * 
   * Applies To:
   * PostRs, StatusInRs, StatusOutRq, ListOutRq.
   */
  public static final String MSGTYPECD_UNWRAPPED = "unwrapped";
  
  /**
   * Documents in the WorkFolder have not been unwrapped successfully.
   * 
   * Applies To:
   * PostRs, StatusInRs, StatusOutRq, ListoutRq, CallRs.
   */
  public static final String MSGTYPECD_UNWRAPPING_FAILED = "unwrapping_failed";
  
  /**
   * Unsuccessful validation of the heading business message (referenced by
   * WorkFolder/MsgFile/FileId).
   * 
   * Applies To:
   * PostRs, StatusInRs, StatusOutRq, ListoutRq, CallRs.
   */
  public static final String MSGTYPECD_INVALID_PAYLOAD = "invalid_payload";
  
  /**
   * Message has been delivered and is pending application processing.
   * 
   * Applies To:
   * PostRs, StatusInRs, StatusOutRq, ListOutRq.
   */
  public static final String MSGTYPECD_DELIVERED = "delivered";
  
  /**
   * Message in process by application.
   * 
   * Applies To:
   * StatusInRs, StatusOutRq, ListOutRq.
   */
  public static final String MSGTYPECD_IN_PROCESS = "in_process";
  
  /**
   * Application processing complete.
   * 
   * Applies To:
   * StatusInRs, StatusOutRq, ListOutRq, CallRs.
   */
  public static final String MSGTYPECD_COMPLETED = "completed";
  
  /**
   * Message type is supported by the receiving application and authorized
   * for the sender (id+role) and receiver (id+role) party pair.
   * 
   * Applies To:
   * ListInRs.
   */
  public static final String MSGTYPECD_TYPE_ACCEPTED = "type_accepted";
  
  /**
   * - The sender party (id+role) is not authorized as message origin. 
   * - The receiver party (id+role) is not authorized as message destination.
   * - The application is not authorized. 
   * - The application and message type pair is not authorized for the sender 
   * (id+role) and receiver (id+role) party pair. 
   * - The business message has been altered - the signature of the business 
   * message could not be authenticated.
   * - The business message could not be decrypted.
   * - An attachment has been altered.
   * - The signature of an attachment could not be authenticated.
   * - An attachment could not be decrypted.
   * 
   * Applies To:
   * PostRs, StatusInRs,StatusOutRq, ListInRs, ListOutRq, CallRs
   */
  public static final String MSGTYPECD_SECURITY = "security";
  
  /**
   * The method of attachment communication specified in <AttachmentPackage> 
   * is not supported.
   * 
   * Applies To:
   * PostRs, StatusInRs, StatusOutRq, CallRs.
   */
  public static final String MSGTYPECD_UNSUPPORTED_COMMUNICATION_CHANNEL = "unsupported_communication_channel";
  
  /**
   * Unknown message.
   * 
   * Applies To:
   * StatusInRs, StatusOutRq.
   */
  public static final String MSGTYPECD_UNKNOWN = "unknown";
  
  /**
   * The message receiving queue is out of sequence with the sending queue.
   * 
   * Applies To:
   * ListInRs, ListOutRq.
   */
  public static final String MSGTYPECD_OUT_OF_SEQUENCE = "out_of_sequence";
  
  /**
   * The message receiving queue is in sequence with the sending queue.
   * 
   * Applies To:
   * ListInRs, ListOutRq.
   */
  public static final String MSGTYPECD_IN_SEQUENCE = "in_sequence";

  private MsgType()
  {
    msgId = UUIDGenerator.getUUID();
  }

  /**
   * Constructor.
   * @see #msgTypeCd
   * @param msgTypeCd
   */
  public MsgType(String msgTypeCd)
  {
    this();
  
    this.msgTypeCd = msgTypeCd;
  }
  
  /**
   * Constructor.
   * @param msgItemOMElement
   */
  public MsgType(OMElement msgItemOMElement)
  {
    setState(msgItemOMElement);
  }


  /**
   * @see #msgId
   * @return
   */
  public String getMsgId()
  {
    return msgId;
  }

  /**
   * @see #msgTypeCd
   * @return
   */
  public String getMsgTypeCd()
  {
    return msgTypeCd;
  }


  public OMElement getState()
  {
    OMElement root = WebServiceUtil.buildOMElement("MsgType", "", CallType.NAMESPACE_AC);

    OMElement msgIdElement = WebServiceUtil.buildOMElement("MsgId", getMsgId(), CallType.NAMESPACE_AC);

    OMElement msgTypeCdElement = WebServiceUtil.buildOMElement("MsgTypeCd", getMsgTypeCd(), CallType.NAMESPACE_AC);

    root.addChild(msgIdElement);

    root.addChild(msgTypeCdElement);

    return root;
  }

  public void setState(OMElement omElement)
  {
    String namespace = omElement.getNamespace().getName();
    
    // get the values
    OMElement msgIdOM = omElement.getFirstChildWithName(new QName(namespace, "MsgId"));
    
    OMElement msgTypeCdOM = omElement.getFirstChildWithName(new QName(namespace, "MsgTypeCd"));
    
    // set the values
    setMsgId(msgIdOM.getText());
    
    setMsgTypeCd(msgTypeCdOM.getText());
  }

  /**
   * Setter.
   * @see #msgId
   * @param msgId
   */
  public void setMsgId(String msgId)
  {
    this.msgId = msgId;
  }

  /**
   * Setter.
   * @see #msgTypeCd
   * @param msgTypeCd
   */
  public void setMsgTypeCd(String msgTypeCd)
  {
    this.msgTypeCd = msgTypeCd;
  }
}
