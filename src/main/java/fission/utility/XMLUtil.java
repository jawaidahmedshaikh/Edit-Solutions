/*
 * User: sdorman
 * Date: Aug 31, 2006
 * Time: 9:36:46 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package fission.utility;

import org.dom4j.*;
import org.dom4j.io.*;

import java.io.*;

import edit.services.logging.*;
import edit.common.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logging.*;

public class XMLUtil
{
    /**
     * Parses an XML String and converts it to a Document
     *
     * @param xml                   single string containing the xml
     *
     * @return  the xml as a Document object
     *
     * @throws DocumentException
     */
    public static Document parse(String xml) throws DocumentException
    {
        SAXReader reader = null;
        
        Document document = null;

        try
        {
            reader = new SAXReader();
            
            reader.setStripWhitespaceText(true);

            reader.setMergeAdjacentText(true);            

            document = reader.read(new StringReader(xml));
        }
        catch (DocumentException e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            throw e;
        }

        return document;
    }

    /**
     * Pretty prints a document to system.out
     *
     * @param document
     */
    public static void printDocumentToSystemOut(Document document)
    {
        String toPrint = XMLUtil.prettyPrint(document);

        System.out.println(toPrint);
    }

    /**
     * Converts the document into XML in prettyPrint format
     *
     * @param document              Document object
     * @return  xml string
     */
    public static String prettyPrint(Document document)
    {
        Writer writer = new StringWriter();

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setExpandEmptyElements(true);

        XMLWriter xmlWriter = null;

        try
        {
            xmlWriter = new XMLWriter(writer, format);
            xmlWriter.write(document);
            xmlWriter.flush();

            return writer.toString();
        }
        catch (IOException e)
        {
            Logging.getLogger(Logging.GENERAL_EXCEPTION).fatal(new LogEvent(e));

            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                xmlWriter.close();
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }
    }

   /**
   * We often have xml documents that need to be stripped of its
   * declaration <?xml version="1.0" encoding="utf-8" ?> (for example).
   * The Regular Expression that accomplishes this is defined as:
   * [<][?]xml.*?[?][>].
   * @param xmlDoc
   * @return
   */
    public static String parseOutXMLDeclaration(String xmlDoc)
    {
      Pattern p = Pattern.compile("[<][?]xml.*?[?][>]");

      Matcher m = p.matcher(xmlDoc);

      xmlDoc = m.replaceAll("");

      return xmlDoc;
    }

    /**
     * Stupid cheat to get an element from the rootElement of the document
     *
     * @param document              document which contains the element
     * @param elementName           text name of the element to get
     *
     * @return  found element
     */
    public static Element getElementFromRoot(Document document, String elementName)
    {
        Element rootElement = document.getRootElement();

        if (rootElement.getName().equals(elementName))
        {
            return rootElement;
        }
        else
        {
            return rootElement.element(elementName);
        }
    }

    /**
     * Parses the document for display in a JSP page.  When trying to include xml in a page that contains HMTL, the
     * xml tags are parsed out, displaying only the values in the tags.  This method converts the tag characters (< and
     * >), carriage return (\n) and a space (for indentation) to html-readable values.
     *
     * @param document                  document to be pretty-printed and parsed
     *
     * @return  xml string with xml-specific characters replaced with html characters
     */
    public static String parseForJSPDisplay(Document document)
    {
        String xmlString = XMLUtil.prettyPrint(document);

        return XMLUtil.parseForJSPDisplay(xmlString);
    }

    /**
     * Parses the xml string for display in a JSP page.  When trying to include xml in a page that contains HMTL, the
     * xml tags are parsed out, displaying only the values in the tags.  This method converts the tag characters (< and
     * >), carriage return (\n) and a space (for indentation) to html-readable values.
     *
     * @param xmlString                 string containing xml that will be parsed
     *
     * @return  xml string with xml-specific characters replaced with html characters
     */
    public static String parseForJSPDisplay(String xmlString)
    {
        xmlString = Util.replaceSubstring(xmlString, "<", "&lt");
        xmlString = Util.replaceSubstring(xmlString, ">", "&gt");
        xmlString = Util.replaceSubstring(xmlString, " ", "&nbsp");
        xmlString = Util.replaceSubstring(xmlString, "\n", "<br>");

        return xmlString;
    }
    
    /**
     * Finds the List of all child Elements from the specified parent Element
     * with the specified element name.
     * 
     * This is not a recursive get - only immediate children will
     * be returned.
     * 
     * @param parentElement
     * @return the immediate children of the specified name, or an empty List
     */
    public static List<Element> getChildren(String childElementName, Element parentElement)
    {
        List<Element> childElementsByName = new ArrayList<Element>();
        
        int nodeCount = parentElement.nodeCount();
        
        for (int i = 0; i < nodeCount; i++)
        {
            Node currentChildNode = parentElement.node(i);
            
            if (currentChildNode instanceof Element)
            {
                String curentChildNodeName = currentChildNode.getName();
                
                if (curentChildNodeName.equals(childElementName))
                {
                    childElementsByName.add((Element) currentChildNode);
                }
            }
        }
        
        return childElementsByName;
    }

    /**
     * Reads the contents of an xml file and parses it into a Document
     *
     * @param fileName                          absolute path and filename of the file to be read
     *
     * @return Document containing the xml read from the file
     *
     * @throws FileNotFoundException
     * @throws IOException
     * @throws DocumentException
     */
    public static Document readDocumentFromFile(String fileName) throws FileNotFoundException, IOException, DocumentException
    {
        Document document = null;

        File file = new File(fileName);

        StringBuilder xml = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line = null;

        //  Put the file contents into 1 long string to allow parsing into a Document
        while ((line = reader.readLine()) != null)
        {
            xml.append(line);
        }

        reader.close();

        document = XMLUtil.parse(xml.toString());

        return document;
    }

    /**
     * Convenience method to set the text on the element if the element and text are not null.  The setting of null text
     * is not allowed in an XML document
     *
     * @param element                   element whose text will be set
     * @param text                      text to set on the element
     */
    public static void setText(Element element, String text)
    {
        if (element != null && text != null)
        {
            element.setText(text);
        }
    }

    /**
     * Convenience method to set the text on the element if the element and text are not null.  The setting of null text
     * is not allowed in an XML document.  The text is converted to a string before setting.
     *
     * @param element                   element whose text will be set
     * @param text                      text to set on the element
     */
    public static void setText(Element element, Long text)
    {
        if (element != null)
        {
            if (text != null)
            {
                element.setText(text.toString());
            }
        }
    }

    /**
     * Convenience method to set the text on the element if the element and text are not null.  The setting of null text
     * is not allowed in an XML document.  The text is converted to a string before setting.
     *
     * @param element                   element whose text will be set
     * @param text                      text to set on the element
     */
    public static void setText(Element element, EDITBigDecimal text)
    {
        if (element != null)
        {
            if (text != null)
            {
                element.setText(text.toString());
            }
        }
    }

    public static EDITBigDecimal getEDITBigDecimalFromText(Element element)
    {
        EDITBigDecimal editBigDecimal = null;

        String text = element.getText();

        if (Util.isStringNullOrEmpty(text))
        {
            editBigDecimal = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);    
        }
        else
        {
            editBigDecimal = new EDITBigDecimal(text);
        }

        return editBigDecimal;
    }

    /**
     * Gets the text from the Element and returns as a String.  If the text was not set in the element, returns a null
     * instead of an empty string.
     *
     * @param element                       Element which contains the text
     *
     * @return  String containing the text in the Element, null if the text does not exist
     */
    public static String getText(Element element)
    {
        String text = null;

        if (element != null)
        {
            text = element.getText();

            if (text.trim().length() == 0)      // if the text was not set in the element, don't return an empty string, return a null instead
            {
                text = null;
            }
        }

        return text;
    }

    /**
     * Creates an Element object for the given xmlString.
     *
     * @param xmlString             String containing xml
     *
     * @return  Element object containing the xmlString
     */
    public static Element getElementFromXMLString(String xmlString)
    {
        Element element = null;

        try
        {
            Document document = XMLUtil.parse(xmlString);

            element = document.getRootElement();
        }
        catch (Exception e)
        {
            e.printStackTrace();

            System.out.println(e);

            throw new RuntimeException(e);
        }

        return element;
    }



    //          MAIN - for testing and examples only
    public static void main(String[] args) throws Exception
    {
        String fileName = "C:/My Documents/ACORD/xmlCriteriaInput.txt";

        Document document = XMLUtil.readDocumentFromFile(fileName);

        XMLUtil.printDocumentToSystemOut(document);
//        File file = new File(fileName);
//
//        StringBuilder xml = new StringBuilder();
//        BufferedReader reader = new BufferedReader(new FileReader(file));
//
//        String line = null;
//
//        while ((line = reader.readLine()) != null)
//        {
//            xml.append(line);
//        }
//
//        reader.close();
//
//        try
//        {
//            Document document = XMLUtil.parse(xml.toString());
//
//            XMLUtil.printDocumentToSystemOut(document);
//        }
//        catch (Exception e)
//        {
//          System.out.println(e);
//
//            e.printStackTrace();
//        }

    }
}
