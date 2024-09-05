package edit.services.logging;

import edit.common.vo.*;
import edit.services.config.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.CronTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.message.SimpleMessage;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Apr 9, 2003
 * Time: 12:15:03 PM
 * To change this template use Options | File Templates.
 */
public class Logging
{
    private static final int NUM_BACKUPS = 10; // Basically limitless
    private static final int MAX_FILE_SIZE = 2000 * 1024; // 2 Meg files
//    private static final Level ERROR_LEVEL = Level.DEBUG;
    public static final String VALIDATE_UNSPECIFIED = "VALIDATE.UNSPECIFIED";
    public static final String VALIDATE_CLIENT_SAVE = "VALIDATE.CLIENT.SAVE";
    public static final String VALIDATE_TRANSACTION_SAVE = "VALIDATE.TRANSACTION.SAVE";
    public static final String VALIDATE_CONTRACT_SAVE = "VALIDATE.CONTRACT.SAVE";
    public static final String VALIDATE_AGENT_SAVE = "VALIDATE.AGENT.SAVE";
    public static final String EXECUTE_SCRIPT = "EXECUTE.SCRIPT";
    public static final String EXECUTE_TRANSACTION = "EXECUTE.TRANSACTION";
    public static final String DB_RECORD_DELETION = "DB.RECORD.DELETION";
    public static final String GENERAL_EXCEPTION = "GENERAL.EXCEPTION";
    public static final String OFAC = "OFAC";
    public static final String BATCH_JOB = "BATCH.JOB";
    public static final String ACCOUNTING = "ACCOUNTING";
    public static final String PRD_BATCH = "PRD.BATCH";

//    // Initialize Loggers
//    static
//    {
//        Map rollingFileAppenders = new HashMap();
//
//        try
//        {
//            final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
//            final Configuration config = ctx.getConfiguration();
//
//
//            PatternLayout layout = PatternLayout.newBuilder().withConfiguration(config).withPattern(PatternLayout.SIMPLE_CONVERSION_PATTERN).build();
//
//            EDITLog[] editLogs = ServicesConfig.getEditServicesConfig().getEDITLog();
//
////            rootLogger.setLevel(ERROR_LEVEL);  // Assumed to be configured in the log4jbackup.properties file.
//            SizeBasedTriggeringPolicy policy = SizeBasedTriggeringPolicy.createPolicy("10MB");
//            DefaultRolloverStrategy strategy = DefaultRolloverStrategy.createStrategy("20", "1", null, null, null, false, config);
//            String daily = "0 0 12 1/1 * ? *";
//            for (int i = 0; i < editLogs.length; i++)
//            {
//                String file = editLogs[i].getFile();
//                String logName = editLogs[i].getLogName();
//                RollingFileAppender.Builder builder  = null;
//
//                if (!rollingFileAppenders.containsKey(file))
//                {
//                    builder = RollingFileAppender.newBuilder();
//                    builder.withFileName(file);
//                    builder.setName(logName);
//                    builder.withFilePattern("rolling-%d{MM-dd-yy}.log.gz");
//                    builder.withPolicy(CronTriggeringPolicy.createPolicy(config, Boolean.TRUE.toString(), daily));
//                    builder.withStrategy(strategy);
//                    builder.setLayout(layout);
//                    builder.setConfiguration(config);
//                }
//
//                RollingFileAppender raf = builder.build();
//                raf.start();
//                config.addAppender(raf);
//                ctx.updateLoggers();
//                for (int j = 0; j < 100; j++) {
//                    raf.append(asLogEvent("This is a debug message: {}"+ j, Level.DEBUG));
//                }
//            }
//
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//            e.printStackTrace(); //To change body of catch statement use Options | File Templates.
//            throw new RuntimeException(e);
//        }
//    }
    private static LogEvent asLogEvent(String message, Level level) {
        return new Log4jLogEvent.Builder().setLoggerName("fileLog").setMarker(null)
                .setLevel(level)
                .setMessage(new SimpleMessage(message)).setTimeMillis(System.currentTimeMillis()).build();
    }
    public static Logger getLogger(String loggerName)
    {
        return LogManager.getLogger(loggerName);
    }
}
