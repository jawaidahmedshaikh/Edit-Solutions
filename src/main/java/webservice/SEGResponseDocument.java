/*
 * User: sdorman
 * Date: Aug 7, 2007
 * Time: 9:47:22 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package webservice;

import edit.common.vo.ValidationVO;

import engine.sp.ValidateInst;

import fission.utility.Util;

import org.dom4j.*;
import org.dom4j.tree.*;

import java.util.*;


/**
 * A convenience class to handle the response information for services
 * <P>
 * The format of the request is as follows:
 *
 *  <SEGResponseVO>
 *      <BatchProductLogVO>    // or some other response data
 *          ...
 *      </BatchProductLogVO>
 *      <ResponseMessageVO>
 *          ...
 *      </ResponseMessageVO>
 *  </SEGResponseVO>
 */
public class SEGResponseDocument
{
    private Document document;
    private Element rootElement;

    public static final String MESSAGE_TYPE_ERROR = "Error";
    public static final String MESSAGE_TYPE_WARNING = "Warning";
    public static final String MESSAGE_TYPE_SUCCESS = "Success";
    
    private static final String RESPONSE_TITLE = "SEGResponseVO";
    private static final String RESPONSE_MESSAGE_TITLE = "ResponseMessageVO";
    private static final String MESSAGE_TYPE_TITLE = "MessageType";
    private static final String MESSAGE_TITLE = "Message";

    public SEGResponseDocument()
    {
        this.document = new DefaultDocument();
        this.rootElement = new DefaultElement(RESPONSE_TITLE);

        this.document.setRootElement(rootElement);
    }

    /**
     * Constructor that takes an existing document.  This is helpful when executing services which return a Document
     * and a SEGResponseDocument is needed.
     *
     * @param document
     */
    public SEGResponseDocument(Document document)
    {
        this.document = document;
        this.rootElement = document.getRootElement();
    }

    public void addToRootElement(Element element)
    {
        rootElement.add(element);
    }

    public Document getDocument()
    {
        return this.document;
    }

    /**
     * Adds a responseMessage to the rootElement
     * @param messageType
     * @param message
     */
    public void addResponseMessage(String messageType, String message)
    {
        message = Util.initString(message, "N/A");
        
        Element responseMessageElement = new DefaultElement(RESPONSE_MESSAGE_TITLE);

        Element messageTypeElement = new DefaultElement(MESSAGE_TYPE_TITLE);
        Element messageElement = new DefaultElement(MESSAGE_TITLE);

        messageTypeElement.setText(messageType);
        messageElement.setText(message);

        responseMessageElement.add(messageTypeElement);
        responseMessageElement.add(messageElement);

        this.addToRootElement(responseMessageElement);
    }

    /**
     * Adds a responseMessage Element to the document.  If the parent of the responseMessageElement is not null, it
     * is set to null before being added.  An element can not have 2 parents.
     * @param responseMessageElement
     */
    public void addResponseMessage(Element responseMessageElement)
    {
        if (responseMessageElement.getParent() != null)
        {
            responseMessageElement.setParent(null);
        }

        this.addToRootElement(responseMessageElement);
    }

    /**
     * Adds a responseMessage to teh rootElement when the response
     * is driven by validation responses from PRASE. The warning/error messages from
     * PRASE than the default messages used by this Response Document, so so mapping
     * is required.
     * @param validationVO
     */
    public void addResponseMessage(ValidationVO validationVO)
    {
        String messageType = null;
        
        String severity = validationVO.getSeverity();
        
        if (severity.equals(ValidateInst.SEVERITY_HARD))
        {
            messageType = MESSAGE_TYPE_ERROR;
        }
        else if (severity.equals(ValidateInst.SEVERITY_WARNING))
        {
            messageType = MESSAGE_TYPE_WARNING;
        }
        
        addResponseMessage(messageType, validationVO.getMessage());
    }

    /**
     * Clears existing responseMessages from the rootElement
     */
    public void clearResponseMessages()
    {
        List responseElements = rootElement.elements(RESPONSE_MESSAGE_TITLE);

        for (Iterator iterator = responseElements.iterator(); iterator.hasNext();)
        {
            Element responseElement = (Element) iterator.next();

            rootElement.remove(responseElement);
        }
    }

    /**
     * Replaces any existing message(s)
     * @param messageType
     * @param message
     */
    public void setResponseMessage(String messageType, String message)
    {
        this.clearResponseMessages();

        this.addResponseMessage(messageType, message);
    }

    /**
     * Returns all of the response messages
     * @return
     */
    public List getResponseMessages()
    {
        List responseMessages = rootElement.elements(RESPONSE_MESSAGE_TITLE);

        return responseMessages;
    }

    /**
     * Determines if the responseMessages contain a failure (non-success) or not
     *
     * @return false if no failures found, true otherwise
     */
    public boolean hasFailure()
    {
        boolean hasFailure = false;

        List responseMessages = this.getResponseMessages();

        for (Iterator iterator = responseMessages.iterator(); iterator.hasNext();)
        {
            Element responseMessageElement = (Element) iterator.next();

            Element messageType = responseMessageElement.element(MESSAGE_TYPE_TITLE);

            if (! messageType.getText().equals(MESSAGE_TYPE_SUCCESS))
            {
                hasFailure = true;
                break;
            }
        }

        return hasFailure;
    }

    /**
     * Determines if the responseMessages contain an error or not.  This is different from hasFailure() which checks
     * for non-successes (i.e. warnings as well as errors).
     *
     * @return false if no errors found, true otherwise
     */
    public boolean hasError()
    {
        boolean hasError = false;

        List responseMessages = this.getResponseMessages();

        for (Iterator iterator = responseMessages.iterator(); iterator.hasNext();)
        {
            Element responseMessageElement = (Element) iterator.next();

            Element messageType = responseMessageElement.element(MESSAGE_TYPE_TITLE);

            if (messageType.getText().equals(MESSAGE_TYPE_ERROR))
            {
                hasError = true;
                break;
            }
        }

        return hasError;
    }
}
