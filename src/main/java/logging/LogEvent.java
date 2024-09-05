/*
 * User: gfrosti
 * Date: Oct 18, 2004
 * Time: 11:18:28 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package logging;

import edit.common.exceptions.*;
import edit.common.vo.*;

import java.util.*;


public class LogEvent
{
    public static final String DATE_TIME_FORMAT = "yyyy/MM/dd hh:mm:ss";
    private static long nextId = System.currentTimeMillis();
    private Date dateTime;
    private String message;
    private Throwable e;
    private List contextEntries = new ArrayList();
    private String contextName;
    private long id;

    public LogEvent(String message, Throwable e)
    {
        this.message = message;
        this.e = e;
        id = getNextId();

        buildDateTime();
    }

    public LogEvent(String message, EDITNestedException e)
    {
        this(message, (Exception) e);
    }

    public LogEvent(String message)
    {
        this(message, null);
    }

    public LogEvent(Throwable e)
    {
        this(e.getMessage(), e);
    }

    /**
     * System generated identifier for this log event.
     * @return
     */
    public long getId()
    {
        return id;
    }

    private static long getNextId()
    {
        nextId++;

        return nextId;
    }

    /**
     * The log message.
     * @return
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Returns the message(s) of exception e, and its own
     * [possible] nested exception list makes-up the remainder of the list.
     * @return
     */
    public String[] getExceptionMessageTrace()
    {
        List messages = new ArrayList();

        if (e != null)
        {
            if (isNestedException())
            {
                messages.addAll(Arrays.asList(((EDITNestedException) e).getMessageList()));
            }
            else
            {
                messages.add(e.getMessage());
            }
        }

        return (String[]) messages.toArray(new String[messages.size()]);
    }

    /**
     * The orginating exception, if any.
     * @return
     */
    public Throwable getException()
    {
        return e;
    }

    /**
     * Returns the stack-trace, or the nested stack-trace if the exception was of the type EDITNestedExcepion.
     * @return
     */
    public String[] getExceptionStackTraceList()
    {
        String[] stackTraceList = new String[0];

        if (e != null)
        {
            if (isNestedException())
            {
                stackTraceList = ((EDITNestedException) e).getStackList();
            }
            else
            {
                stackTraceList = asString(e.getStackTrace());
            }
        }

        return stackTraceList;
    }

    /**
     * Builds date/time stamp.
     */
    private final void buildDateTime()
    {
        dateTime = Calendar.getInstance().getTime();
    }

    /**
     * The date/time stamp of this log entry.
     * @return
     */
    public Date getDateTime()
    {
        return dateTime;
    }

    /**
     * true if the exception e is of type EDITNestedException.
     * @return
     */
    public boolean isNestedException()
    {
        boolean isNestedException = false;

        isNestedException = (e instanceof EDITNestedException);

        return isNestedException;
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

    /**
     * Beyond the basic message, a logged event may wish to supply name/value pairs to supply contextual information
     * about the event.
     * @param name
     * @param value
     */
    public void addToContext(String name, String value)
    {
        LogContextEntry logContextEntry = new LogContextEntry();

        logContextEntry.setEntryName(name);

        logContextEntry.setEntryValue(value);

        contextEntries.add(logContextEntry);
    }

    /**
     * The (optional) set of name/value pairs that supply contextual information about the event. The LogContextEntry entries
     * are returned in the order that they were added.
     * @return
     */
    public LogContextEntry[] getContextEntries()
    {
        return (LogContextEntry[]) contextEntries.toArray(new LogContextEntry[contextEntries.size()]);
    }

    public String getContextName()
    {
        return contextName;
    }

    public void setContextName(String contextName)
    {
        this.contextName = contextName;
    }

  public String toString()
  {
    return "LogEvent.Message [" + getMessage() + "]";
  }

  /**
   * Stores a name/value pair as an entry to the set of LogContextEntries to this LogEvent.
   */
    public class LogContextEntry
    {
        private LogContextEntryVO logContextEntryVO;

        private LogContextEntry()
        {
            this.logContextEntryVO = new LogContextEntryVO();
        }

        /**
         * Getter.
         * @return
         */
        public String getEntryName()
        {
            return logContextEntryVO.getEntryName();
        }

        /**
         * Getter.
         * @return
         */
        public String getEntryValue()
        {
            return logContextEntryVO.getEntryValue();
        }

        /**
         * Setter.
         * @param entryName
         */
        public void setEntryName(String entryName)
        {
            this.logContextEntryVO.setEntryName(entryName);
        }

        /**
         * Setter.
         * @param entryValue
         */
        public void setEntryValue(String entryValue)
        {
            this.logContextEntryVO.setEntryValue(entryValue);
        }

        /**
         * Getter.
         * @return
         */
        public LogContextEntryVO getLogContextEntryVO()
        {
            return this.logContextEntryVO;
        }
    }
    
}
