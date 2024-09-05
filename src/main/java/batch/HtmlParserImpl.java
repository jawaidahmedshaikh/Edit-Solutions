package batch;

import com.sun.jdmk.comm.*;
import batch.business.*;
import fission.utility.Util;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 4, 2005
 * Time: 11:33:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class HtmlParserImpl implements HtmlParserImplMBean, HtmlParser
{
    /**
     * @see #parseRequest(String)
     * @param string
     * @return
     */
    public String parseRequest(String string)
    {
        return null;
    }

    /**
     * The HtmlAdapterServer wants to return a full HTML page as the response. Find the
     * SEG-specific message within the HTML text and return only that.
     * @param string
     * @return the SEG-specific response message
     */
    public String parsePage(String string)
    {
        String segMessage = Util.initString(string, "");

        int beginIndex = string.indexOf(Batch.SEG_MARKER_BEGIN);

        if (beginIndex >= 0) // We only want to reformat SEG response pages (contain <seg> tags).
        {
            int endIndex = string.indexOf(Batch.SEG_MARKER_END);

            int offset = Batch.SEG_MARKER_BEGIN.length();

            segMessage = string.substring(beginIndex + offset, endIndex);
        }

        return segMessage;
    }
}
