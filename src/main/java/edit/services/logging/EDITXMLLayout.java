//package edit.services.logging;
//
//import edit.common.vo.*;
//
//import fission.utility.*;
//
//import java.text.*;
//
//import java.util.regex.*;
//
//import logging.*;
//
//import org.apache.logging.log4j.Logger;
//import org.apache.log4j.spi.*;
//
//
///**
// * Created by IntelliJ IDEA.
// * User: gfrosti
// * Date: Apr 9, 2003
// * Time: 12:19:27 PM
// * To change this template use Options | File Templates.
// */
//public class EDITXMLLayout extends Layout
//{
//    public boolean ignoresThrowable()
//    {
//        return false;
//    }
//
//    public void activateOptions()
//    {
//    }
//
//    /**
//     * Formats a LogEvent as XML. The XML output extends the LogEvent data by adding the type of log (error, warning, etc.),
//     * and the name of the log.
//     * @param e
//     * @return
//     */
//    public String format(LoggingEvent e)
//    {
//        LogEvent logEvent = (LogEvent) e.getMessage();
//
//        LogEntryVO logEntryVO = new LogEntryVO();
//
//        String logEntryVOAsXML = null;
//
//        String errorType = e.getLevel().toString();
//
//        String loggerName = e.getLoggerName();
//
//        SimpleDateFormat formatter = new SimpleDateFormat (LogEvent.DATE_TIME_FORMAT);
//
//        logEntryVO.setType(errorType);
//        logEntryVO.setLogName(loggerName);
//        logEntryVO.setTime(formatter.format(logEvent.getDateTime()));
//        logEntryVO.setMessage(logEvent.getMessage());
//        setLogContextEntries(logEntryVO, logEvent.getContextEntries());
//        logEntryVO.setExceptionMessageTrace(logEvent.getExceptionMessageTrace());
//        logEntryVO.setExceptionStackTrace(logEvent.getExceptionStackTraceList());
//        logEntryVO.setContextName(logEvent.getContextName());
//        logEntryVO.setId(logEvent.getId());
//
//        try
//        {
//            logEntryVOAsXML = Util.marshalVO(logEntryVO);
//
//            logEntryVOAsXML = XMLUtil.parseOutXMLDeclaration(logEntryVOAsXML);
//        }
//        catch (Exception e1)
//        {
//            System.out.println(e);
//            e1.printStackTrace();  //To change body of catch statement use Options | File Templates.
//        }
//
//        return logEntryVOAsXML;
//    }
//
//    /**
//     * Maps any name/value context values of the current LogEvent to its corresponding VO.
//     * @param logEntryVO
//     * @param logContexts
//     */
//    private void setLogContextEntries(LogEntryVO logEntryVO, LogEvent.LogContextEntry[] logContexts)
//    {
//        for (int i = 0; i < logContexts.length; i++)
//        {
//            LogEvent.LogContextEntry logContextEntry = logContexts[i];
//
//            LogContextEntryVO logContextEntryVO = logContextEntry.getLogContextEntryVO();
//
//            logEntryVO.addLogContextEntryVO(logContextEntryVO);
//        }
//    }
//
//    public String getContentType()
//    {
//        return "text/plain";
//    }
//}
