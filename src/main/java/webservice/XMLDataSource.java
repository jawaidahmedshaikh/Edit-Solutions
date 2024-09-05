/*
 * User: sprasad
 * Date: Sep 11, 2006
 * Time: 3:17:46 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package webservice;

import javax.activation.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Implemenation of javax.activation.DataSource class for XML strings.
 */
public class XMLDataSource implements DataSource
{
    private String string = null;
    private String name = null;

    public XMLDataSource(String xmlData)
    {
        this.name = "";
        this.string = xmlData;
    }

    public XMLDataSource(String name, String xmlData)
    {
        this.name = name;
        this.string = xmlData;
    }

    public InputStream getInputStream() throws IOException
    {
        return new ByteArrayInputStream(string.getBytes());
    }

    public OutputStream getOutputStream() throws IOException
    {
        return null;
    }

    public String getContentType()
    {
        return "text/xml";
    }

    public String getName()
    {
        return name;
    }
}
