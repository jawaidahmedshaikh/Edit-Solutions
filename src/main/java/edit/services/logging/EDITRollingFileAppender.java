//package edit.services.logging;
//
//import org.apache.logging.log4j.core.Layout;
//import org.apache.logging.log4j.core.appender.RollingFileAppender;
//
//import java.io.IOException;
//
///**
// * Created by IntelliJ IDEA.
// * User: gfrosti
// * Date: Apr 14, 2003
// * Time: 10:56:03 AM
// * To change this template use Options | File Templates.
// */
//public class EDITRollingFileAppender extends RollingFileAppender.Builder
//{
//    public EDITRollingFileAppender(Layout theLayout, String theFilename) throws IOException
//    {
//        super(theLayout, theFilename);
//    }
//
//    public void writeHeader()
//    {
//        super.writeHeader();
//
//        System.out.println("Writing the header");
//    }
//
//    public void writeFooter()
//    {
//        super.writeFooter();
//
//        System.out.println("Writing the Footer");
//    }
//}
