package acord.client;

import acord.client.type.CallType;

import javax.xml.namespace.QName;
import javax.activation.DataSource;
import javax.activation.DataHandler;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMText;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMContainer;
import org.apache.axis2.util.UUIDGenerator;

import webservice.WebServiceUtil;
import webservice.XMLDataSource;


/**
 * Information to access the business message
 * transported.
 */
public class MsgFile implements OMElementParticipant
{
  /**
   * The identifier of the file or object that is the business message.
   * This value must be a URI of the form cid:* when referring to a MIME part.
   * It is auto-generated upon instantiating this MsgFile.
   */
  private String fileId;

  /**
   * Specifies the type & subtype of message file format.
   * Type and subtype must be separated by a '/' as
   * specified in section 5.1 of RFC 2045. For an xml
   * message: 'text/xml'
   */
  private String fileFormatCd;

  /**
   * Holds the content of the soap attachment identified by <code>fileId</code>
   * The value must be an XML string at this point of time. This attachment holds
   * information that is necessary for executing corresponding soap operation.
   * At this point of time we are using MTOM to optimize the soap attachment.
   */
  private String fileContent;

  /**
   * Convenience constant for the text/xml format.
   */
  public static final String FILE_FORMAT_TEXT_XML = "text/xml";

  /**
   * Constructor.
   * @see #fileFormatCd
   * @see #fileId
   */
  private MsgFile()
  {
    this.fileId = UUIDGenerator.getUUID();
  }

  /**
   * Constructor.
   * @see #fileFormatCd
   * @see #fileId
   * @param fileFormatCd
   */
  public MsgFile(String fileFormatCd, String fileContent)
  {
    this();

    this.fileFormatCd = fileFormatCd;

    this.fileContent = fileContent;
  }
  
  /**
   * Constructor.
   * @param msgFileOMElement
   */
  public MsgFile(OMElement msgFileOMElement)
  {
    setState(msgFileOMElement);
  }

  /**
   * Getter.
   * @see #getFileId
   * @return
   */
  public String getFileId()
  {
    return fileId;
  }

  /**
   * Getter.
   * @return
   */
  public String getFileContent()
  {
    return fileContent;
  }

  /**
   * Getter.
   * @see #getFileFormatCd
   * @return
   */
  public String getFileFormatCd()
  {
    return fileFormatCd;
  }

  public OMElement getState()
  {
    OMElement root = WebServiceUtil.buildOMElement("MsgFile", "", CallType.NAMESPACE_AC);
    
    OMElement fileFormatCdOM = WebServiceUtil.buildOMElement("FileFormatCd", getFileFormatCd(), CallType.NAMESPACE_AC);
    
    OMElement fileIdOM = WebServiceUtil.buildOMElement("FileId", getFileId(), CallType.NAMESPACE_AC);

//    root.addChild(WebServiceUtil.makeSOAPAttachment(getFileContent()));

      DataSource ds = new XMLDataSource(getFileContent());

      DataHandler dh = new DataHandler(ds);

      OMText textData = OMAbstractFactory.getOMFactory().createOMText(dh, true);

      root.addChild(textData);

    root.addChild(fileIdOM);

    root.addChild(fileFormatCdOM);

    return root;
  }

  public void setState(OMElement omElement)
  {
    String namespace = omElement.getNamespace().getName();

    // There is bug in axis2 that is changing the structure of SOAPMessage when we use MTOM optimization
    // This will be done when set enableMTOM to true either on client side or server side.
    // What happening is
      // Original Structure of SOAP Message
      // <CallRq>
      //   ...
      //   ...
      //    <WorkFolder>
      //      <MsgFile>
      //        Attachment Content
      //        <FileId></FileId>
      //        <FileFormatCd></FileFormatCd>
      //     </MsgFile>
      //    </WorkFolder>
      // </CallRq>

    // When axis2 receives this message and enableMTOM to true
    // it is changing the stucture like following.
      // <CallRq>
      //   ...
      //   ...
      //    <WorkFolder>
      //      <MsgFile>Attachment Content</MsgFile>
      //      <FileId></FileId>
      //      <FileFormatCd></FileFormatCd>
      //    </WorkFolder>
      // </CallRq>

     // Note: The <MsgFile> is closed immediately after the attachment content.

      // On the server side we always enableMTOM i.e. set to true.
      // But we do not know on the client side.
      // If MTOM is enabled, then get fileId and fildFormate from WorkFolder element
      // otherwise from MsgFile element.

    OMElement workFolderOM = (OMElement) omElement.getParent();

    // get the values
    OMElement fileFormatCdOM = omElement.getFirstChildWithName(new QName(namespace, "FileFormatCd"));

    // get the values
    OMElement fileIdOM = omElement.getFirstChildWithName(new QName(namespace, "FileId"));

    if (fileFormatCdOM == null)
    {
        fileFormatCdOM = workFolderOM.getFirstChildWithName(new QName(namespace, "FileFormatCd"));
    }

    if (fileIdOM == null)
    {
        fileIdOM = workFolderOM.getFirstChildWithName(new QName(namespace, "FileId"));
    }

      // set the values
    setFileFormatCd(fileFormatCdOM.getText());

    setFileId(fileIdOM.getText());

    setFileContent(omElement.getText());
  }
  
  /**
   * Setter.
   * @see #fileId
   * @param fileId
   */
  public void setFileId(String fileId)
  {
    this.fileId = fileId;
  }

  /**
   * Setter.
   * @see #fileFormatCd
   * @param fileFormatCd
   */
  public void setFileFormatCd(String fileFormatCd)
  {
    this.fileFormatCd = fileFormatCd;
  }

  /**
   * Setter.
   * @param fileContent
   */
  public void setFileContent(String fileContent)
  {
      this.fileContent = fileContent;
  }
}
