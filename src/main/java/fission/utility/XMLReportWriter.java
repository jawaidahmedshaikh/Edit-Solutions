package fission.utility;

import edit.common.vo.*;

import java.io.*;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: May 6, 2005
 * Time: 11:10:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class XMLReportWriter
{
    private static final String PROCESSING_INSTRUCTION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final int BUFFER_SIZE = 64 * 1024; // 64K
    private BufferedWriter writer;
    private String rootEndTag;
    private String fileName;
    private final int MOD_COUNT = 100;
    private int writeCount;

    /**
     * It is often the case that the xml output being generated does not exist as child entities within a containing
     * VOObject. In such a case, a containing VOObject can be specified. This utility creates <foo></foo> containing
     * tags for the content for the Foo.class specified.
     * @param rootVOClass
     */
    public XMLReportWriter(Class rootVOClass, String fileName)
    {
        this.fileName = fileName;

        buildWriter();

        writeString(PROCESSING_INSTRUCTION);

        writeString("\n");

        writeRootStartTag(rootVOClass);

        writeString("\n");
    }

    /**
     * The writer for outputting the XML text.
     */
    private void buildWriter()
    {
        File file = buildExportFile();

        try
        {
            writer = new BufferedWriter(new FileWriter(file), BUFFER_SIZE);
        }
        catch (IOException e)
        {
          System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    /**
     * Getter.
     * @return
     */
    private String getFileName()
    {
        return fileName;
    }

    /**
     * The export file built from the export directory specified in the configuration file and the specified file name.
     * A time stamp is appended to the file name.
     * @return
     */
    private File buildExportFile()
    {
        File file = new File(getFileName());

        return file;
    }

    /**
     * Creates the <foo></foo> tags for the specified VOObject.
     */
    private void writeRootStartTag(Class rootVOClass)
    {
        String rootStartTag = null;

        if (rootVOClass != null)
        {
            String packageName = rootVOClass.getPackage().getName();

            String className = rootVOClass.getName().substring(packageName.length() + 1);

            rootStartTag = "<" + className + ">";

            rootEndTag = "</" + className + ">";
        }
        else
        {
            rootStartTag = "";

            rootEndTag = "";
        }

        writeString(rootStartTag);
    }

    /**
     * Writes VO.
     * @param voObject
     */
    public void writeVO(VOObject voObject)
    {
        if (voObject != null)
        {
            String voAsXML = Util.marshalVO(voObject);

            voAsXML = tidy(voAsXML);

            writeString(voAsXML);
        }
    }

    /**
     * Writes String.
     * @param s
     */
    private void writeString(String s)
    {
        try
        {
            writer.write(s);

            writer.flush();

            updateWriteCount();

            if (shouldInvokeGC())
            {
                System.gc();
            }
        }
        catch (IOException e)
        {
            close();

          System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    /**
     * Properly formats the XML.
     * @param text
     * @return
     */
    private String tidy(String text)
    {
        text = XMLUtil.parseOutXMLDeclaration(text);

        text = text.trim() + "\n";

        return text;
    }

    /**
     * Closes
     */
    public void close() throws RuntimeException
    {
        try
        {
            if (writer != null)
            {
                writer.write(rootEndTag);

                writer.flush();
            }
        }
        catch (IOException e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (writer != null)
                {
                    writer.close();

                    writer = null;
                }
            }
            catch (IOException e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }
    }

    /**
     * True if the WRITE_COUNT % MOD_COUNT = 0;
     * @return
     */
    private boolean shouldInvokeGC()
    {
        boolean shouldInvokeGC = ((getWriteCount() % MOD_COUNT) == 0);

        return shouldInvokeGC;
    }

    /**
     * Ups the writeCount by 1.
     */
    private void updateWriteCount()
    {
        writeCount++;
    }

    /**
     * Getter.
     * @return
     */
    private int getWriteCount()
    {
        return writeCount;
    }
}
