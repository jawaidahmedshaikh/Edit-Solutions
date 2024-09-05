/*
 * User: gfrosti
 * Date: Mar 4, 2003
 * Time: 4:07:38 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.common.exceptions;

import edit.common.EDITList;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;


/**
 * This class contains can contain a list messages in the case where multiple
 * validation errors need to be represented.
 */
public class EDITException extends Exception
{
    /**
     * The ordered list of messages.
     */
    private EDITList messageList = new EDITList();
    
    private boolean logged;


    public EDITException()
    {
        super();
    }

    public EDITException(String message)
    {
        super(message);

        getMessageList().addTo(message);
    }

    /**
     * The List of validation messages. The user of this class can add a
     * message to the message list by calling:
     * getMessageList().add("the new message").
     *
     * @return List
     */
    public EDITList getMessageList()
    {
        return messageList;
    }

    /**
     * Converts a stackTrace to a String
     *
     * @param throwableObject           object of type Throwable
     *
     * @return  string containing the stack trace
     */
    public static String getStackTrace(Throwable throwableObject)
    {
        final Writer result = new StringWriter();

        final PrintWriter printWriter = new PrintWriter(result);

        throwableObject.printStackTrace(printWriter);

        return result.toString();
    }
    
    /**
     * Set to true if this exception has been logged by some logging framework.
     * @see #logged
     * @param logged
     */
    public void setLogged(boolean logged)
    {
      this.logged = logged;
    }

    /**
     * True if this Exception has been logged by the logging framework.
     * @see #logged
     * @return
     */
    public boolean isLogged()
    {
      return logged;
    }
}
