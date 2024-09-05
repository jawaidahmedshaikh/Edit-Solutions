/*
 * User: gfrosti
 * Date: Oct 12, 2004
 * Time: 4:01:39 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.common.exceptions;

import java.util.*;

public class EDITNestedException extends Exception
{
    private Exception _nestedException;

    public EDITNestedException()
    {
        super();
    }

    public EDITNestedException(String message)
    {
        super(message);
    }

    /**
     * The exception to be wrapped.
     * @param nestedException
     */
    public void setNestedException(Exception nestedException)
    {
        this._nestedException = nestedException;
    }

    public Exception getNestedException()
    {
        return _nestedException;
    }

    /**
     * Builds the ordered nested message list. The 0th message is the current exception's message.
     * @return
     */
    public String[] getMessageList()
    {
        List messageStack = buildMessageList();

        return (String[]) messageStack.toArray(new String[messageStack.size()]);
    }

    /**
     * Recursively assembles the messages from all nested exceptions.
     * @return
     */
    private List buildMessageList()
    {
        List messageStack = new ArrayList();

        messageStack.add(getMessage());

        Exception nestedException = null;

        nestedException = getNestedException();

        if (nestedException instanceof EDITNestedException)
        {
            messageStack.addAll(((EDITNestedException) nestedException).buildMessageList());
        }
        else if (nestedException != null)
        {
            messageStack.add(nestedException.getMessage());
        }

        return messageStack;
    }

    /**
     * Builds the ordered nested stack-trace list.
     * @return
     */
    public String[] getStackList()
    {
        Exception lowestLevelException = getLowestLevelException();

        StackTraceElement[] stackTraceElements = lowestLevelException.getStackTrace();

        return asString(stackTraceElements);
    }

    /**
     * Find the lowest-level nested exception and gathers its stack trace.
     * @return
     */
    private Exception getLowestLevelException()
    {
        Exception lowestLevelException = null;

        lowestLevelException = getNestedException();

        if (lowestLevelException instanceof EDITNestedException)
        {
            lowestLevelException = (((EDITNestedException) lowestLevelException).getLowestLevelException());
        }
        else if (lowestLevelException != null)
        {
            lowestLevelException = _nestedException;
        }
        else
        {
            lowestLevelException = this;
        }


        return lowestLevelException;
    }

    /**
     * Builds String representation of an Exception's stack-trace.
     * @param stackTraceElements
     * @return
     */
    private String[] asString(StackTraceElement[] stackTraceElements)
    {
        List strList = new ArrayList();

        for (int i = 0; i < stackTraceElements.length; i++)
        {
            strList.add(stackTraceElements[i].toString());
        }

        return (String[]) strList.toArray(new String[strList.size()]);
    }
}
