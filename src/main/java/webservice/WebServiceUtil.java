/*
 * User: sprasad
 * Date: Sep 7, 2006
 * Time: 3:28:38 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package webservice;

import edit.common.EDITDateTime;
import fission.utility.XMLUtil;

import java.io.Reader;
import java.io.StringReader;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.*;
import org.apache.axiom.om.util.Base64;
import org.apache.axis2.util.UUIDGenerator;
import org.dom4j.Document;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.impl.builder.StAXOMBuilder;


public class WebServiceUtil
{
    public static final String NAMESPACE_URI = "http://www.segsoftware.com/services/EDITSolutionsWebService";
    public static final String NAMESPACE_PREFIX = "seg";

    /**
     * Utility method to decode SOAP attachment.
     * Axis2 uses MTOM optimization to send soap attachments.
     * @param stringToDecode String to decode
     * @return
     */
    public static String decodeSOAPAttachment(String stringToDecode)
    {
        byte[] decodedBytes = Base64.decode(stringToDecode);

        return new String(decodedBytes);
    }

    /**
     * Utility method to build SOAP attachment from simple string.
     * Axis2 uses MTOM optimization to send soap attachments.
     * @param stringToAttach String to make as SOAP attachment
     * @return
     */
    public static OMText makeSOAPAttachment(String stringToAttach)
    {
        DataSource dataSource = new XMLDataSource(stringToAttach);

        DataHandler dataHandler = new DataHandler(dataSource);

        OMText attachmentText =  OMAbstractFactory.getOMFactory().createOMText(dataHandler, true);

        return attachmentText;
    }

    /**
     * Builds soap request message.
     * @param application
     * @param msgType
     * @param textToAttachAsXML
     * @return
     */
    public static OMElement buildSOAPRequest(String sender, String receiver, String application, String msgType, String textToAttachAsXML)
    {
        OMFactory factory = OMAbstractFactory.getOMFactory();

        OMNamespace omNs = factory.createOMNamespace(NAMESPACE_URI, NAMESPACE_PREFIX);

        OMElement payload = factory.createOMElement("CallRq", omNs);

        OMElement senderElement = factory.createOMElement("Sender", omNs);

        OMElement senderPartyElement = factory.createOMElement("PartyId", omNs);

        senderPartyElement.setText(sender);

        OMElement senderRoleElement = factory.createOMElement("PartyRoleCd", omNs);

        senderRoleElement.setText("");

        senderElement.addChild(senderPartyElement);

        senderElement.addChild(senderRoleElement);

        payload.addChild(senderElement);

        OMElement receiverElement = factory.createOMElement("Receiver", omNs);

        OMElement receiverPartyElement = factory.createOMElement("PartyId", omNs);

        receiverPartyElement.setText(receiver);

        OMElement receiverRoleElement = factory.createOMElement("PartyRoleCd", omNs);

        receiverRoleElement.setText("");

        receiverElement.addChild(receiverPartyElement);

        receiverElement.addChild(receiverRoleElement);

        payload.addChild(receiverElement);

        OMElement applicationElement = factory.createOMElement("Application", omNs);

        OMElement applicationCdElement = factory.createOMElement("ApplicationCd", omNs);

        applicationCdElement.setText(application);

        applicationElement.addChild(applicationCdElement);

        payload.addChild(applicationElement);

        OMElement timeStampElement = factory.createOMElement("TimeStamp", omNs);

        timeStampElement.setText(new EDITDateTime().getFormattedDateTime());

        payload.addChild(timeStampElement);

        OMElement msgItemElement = factory.createOMElement("MsgItem", omNs);

        OMElement msgIdElement = factory.createOMElement("MsgId", omNs);

        msgIdElement.setText(UUIDGenerator.getUUID());

        OMElement msgTypeCdElement = factory.createOMElement("MsgTypeCd", omNs);

        msgTypeCdElement.setText(msgType);

        msgItemElement.addChild(msgTypeCdElement);

        msgItemElement.addChild(msgIdElement);

        payload.addChild(msgItemElement);

        OMElement workFolderElement = factory.createOMElement("WorkFolder", omNs);

        OMElement msgFileElement = factory.createOMElement("MsgFile", omNs);

        OMElement fileIdElement = factory.createOMElement("FileId", omNs);

        fileIdElement.setText(UUIDGenerator.getUUID());

        OMElement fileFormatCdElement = factory.createOMElement("FileFormatCd", omNs);

        fileFormatCdElement.setText("text/xml");

        msgFileElement.addChild(makeSOAPAttachment(textToAttachAsXML));

        msgFileElement.addChild(fileIdElement);

        msgFileElement.addChild(fileFormatCdElement);

        workFolderElement.addChild(msgFileElement);

        payload.addChild(workFolderElement);

        return payload;
    }

    /**
     * Builds SOAP response message.
     * @param soapRequest
     * @param msgType
     * @param textToAttachAsXML
     * @return soap response as OMElement
     */
    public static OMElement buildSOAPResponse(OMElement soapRequest, String msgType, String textToAttachAsXML)
    {
        OMFactory factory = OMAbstractFactory.getOMFactory();

        OMNamespace omNs = soapRequest.getNamespace();

        String ns = omNs.getName();

        OMElement soapResponse = factory.createOMElement("CallRs", omNs);

        OMElement senderElement = factory.createOMElement("Sender", omNs);

        OMElement senderPartyElement = factory.createOMElement("PartyId", omNs);

        OMElement requestReceiverElement = soapRequest.getFirstChildWithName(new QName(ns, "Receiver"));

        OMElement requestReceiverPartyIdElement = requestReceiverElement.getFirstChildWithName(new QName(ns, "PartyId"));

        senderPartyElement.setText(requestReceiverPartyIdElement.getText());

        OMElement senderRoleElement = factory.createOMElement("PartyRoleCd", omNs);

        senderRoleElement.setText("");

        senderElement.addChild(senderPartyElement);

        senderElement.addChild(senderRoleElement);

        soapResponse.addChild(senderElement);

        OMElement receiverElement = factory.createOMElement("Receiver", omNs);

        OMElement receiverPartyElement = factory.createOMElement("PartyId", omNs);

        OMElement requestSenderElement = soapRequest.getFirstChildWithName(new QName(ns, "Sender"));

        OMElement requestSenderPartyIdElement = requestSenderElement.getFirstChildWithName(new QName(ns, "PartyId"));

        receiverPartyElement.setText(requestSenderPartyIdElement.getText());

        OMElement receiverRoleElement = factory.createOMElement("PartyRoleCd", omNs);

        receiverRoleElement.setText("");

        receiverElement.addChild(receiverPartyElement);

        receiverElement.addChild(receiverRoleElement);

        soapResponse.addChild(receiverElement);

        OMElement applicationElement = factory.createOMElement("Application", omNs);

        OMElement applicationCdElement = factory.createOMElement("ApplicationCd", omNs);

        OMElement requestApplicationElement = soapRequest.getFirstChildWithName(new QName(ns, "Application"));

        applicationCdElement.setText(requestApplicationElement.getText());

        applicationElement.addChild(applicationCdElement);

        soapResponse.addChild(applicationElement);

        OMElement timeStampElement = factory.createOMElement("TimeStamp", omNs);

        timeStampElement.setText(new EDITDateTime().getFormattedDateTime());

        soapResponse.addChild(timeStampElement);

        OMElement msgItemElement = factory.createOMElement("MsgItem", omNs);

        OMElement msgIdElement = factory.createOMElement("MsgId", omNs);

        OMElement reqMsgItemElement = soapRequest.getFirstChildWithName(new QName(ns, "MsgItem"));

        OMElement reqMsgIdElement = reqMsgItemElement.getFirstChildWithName(new QName(ns, "MsgId"));

        msgIdElement.setText(reqMsgIdElement.getText());

        msgItemElement.addChild(msgIdElement);

        OMElement msgTypeCdElement = factory.createOMElement("MsgTypeCd", omNs);

        msgTypeCdElement.setText(msgType);

        msgItemElement.addChild(msgTypeCdElement);

        soapResponse.addChild(msgItemElement);

        OMElement workFolderElement = factory.createOMElement("WorkFolder", omNs);

        OMElement msgFileElement = factory.createOMElement("MsgFile", omNs);

        OMElement fileIdElement = factory.createOMElement("FileId", omNs);

        fileIdElement.setText(UUIDGenerator.getUUID());

        OMElement fileFormatCdElement = factory.createOMElement("FileFormatCd", omNs);

        fileFormatCdElement.setText("text/xml");

        msgFileElement.addChild(makeSOAPAttachment(textToAttachAsXML));

        msgFileElement.addChild(fileIdElement);

        msgFileElement.addChild(fileFormatCdElement);

        workFolderElement.addChild(msgFileElement);

        soapResponse.addChild(workFolderElement);

        return soapResponse;
    }

    /**
     * Extracts the attachment content from soap request message and decodes it.
     * @return attachment as org.dom4j.Document
     */
    public static Document getAttachment(OMElement soapRequest)
    {
        Document document = null;

        String ns = soapRequest.getNamespace().getName();

        OMElement workFolderElement = soapRequest.getFirstChildWithName(new QName(ns, "WorkFolder"));

        OMElement msgFileElement = workFolderElement.getFirstChildWithName(new QName(ns, "MsgFile"));

        String decodedAttachmentContent = WebServiceUtil.decodeSOAPAttachment(msgFileElement.getText());

        try
        {
            document = XMLUtil.parse(decodedAttachmentContent);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        return document;
    }

    /**
     *
     * @return
     */
    public static OMElement buildSOAPFaultMessage(Throwable e, OMElement soapRequest)
    {
        OMFactory factory = OMAbstractFactory.getOMFactory();

        OMNamespace omNs = soapRequest.getNamespace();

        OMElement fault = factory.createOMElement("Fault", omNs);

        OMElement faultCode = factory.createOMElement("faultcode", null);

        OMElement faultString = factory.createOMElement("faultstring", null);

        OMElement detail = factory.createOMElement("detail", null);

        OMElement faultDetails = factory.createOMElement("FaultDetails", omNs);

        faultDetails.setText(e.getMessage());

        fault.addChild(faultCode);

        fault.addChild(faultString);

        fault.addChild(detail);

        detail.addChild(faultDetails);

        System.out.println("Fault: " + faultDetails);

        return fault;
    }

  /**
   * As with any Element (DOM, SAX, etc.) we expect an element name, a text value, and a
   * namespace when building an Element instance.
   * @param name
   * @param value the element's value as a string
   * @param namespace
   * @return an OMElement with the specified name, text value, and OMNamespace (from pre-defined constants)
   */
  public static OMElement buildOMElement(String name, String value, OMNamespace namespace)
  {
    // Factory
    OMFactory factory = OMAbstractFactory.getSOAP11Factory();

    // Elements
    OMElement omElement = factory.createOMElement(name, namespace);

//    omElement.setBuilder(new org.apache.axiom.soap.impl.builder.StAXSOAPModelBuilder(parser, "1.1"));

    // Text Value
    omElement.setText(value);

    return omElement;
  }

  /**
   * Service method to optimize text and attach to SOAP message.
   * Adds the attachment <code>parent</code> element.
   * At this point of time the optimization technique is MTOM (used in axis2)
   * @param parent
   * @param text
   * @return
   */
  public static OMElement buildSOAPAttachment(OMElement parent, String text)
  {
    // Factory
    OMFactory factory = OMAbstractFactory.getOMFactory();

    DataSource dataSource = new XMLDataSource(text);

    DataHandler dataHandler = new DataHandler(dataSource);

    OMText encodedText =  factory.createOMText(dataHandler, true);

    parent.addChild(encodedText);

    return parent;
  }

  /**
   * Convenience method to convert an xml document
   * into its OMElement equivalent.
   * @param xml
   * @return
   */
  public static OMElement getAsOMElement(String xml)
  {
    OMElement omElement = null;
   
    XMLStreamReader streamReader = null;
 
    try
    {
      Reader reader = new StringReader(xml);
  
      XMLInputFactory xif = XMLInputFactory.newInstance();
    
      streamReader = xif.createXMLStreamReader(reader);
      
      StAXOMBuilder builder = new StAXOMBuilder(streamReader);
  
      omElement = builder.getDocumentElement();
      
      // Build it now otherwise we can't close the stream now.
      omElement.build();
    }
    catch (Exception e)
    {
      System.out.println(e);
      
      e.printStackTrace();
      
      throw new RuntimeException(e);
    }
    finally
    {
      try
      {
        if (streamReader != null) streamReader.close();
      }
      catch (XMLStreamException e)
      {
        throw new RuntimeException(e);
      }
    }
    
    return omElement;
  }  
}
