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
// * Date: Jul 9, 2007
// * Time: 12:19:27 PM
// * To change this template use Options | File Templates.
// */
//public class CorrespondenceLayout extends Layout
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
//     * Get the logger message only for Correspondence
//     * @param e
//     * @return
//     */
//    public String format(LoggingEvent e)
//    {
//
//        return e.getMessage().toString();
//
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
