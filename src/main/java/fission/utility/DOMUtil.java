package fission.utility;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.*;

import engine.common.*;

import org.dom4j.*;
import org.dom4j.io.*;

import java.util.*;
import java.io.*;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.NodeDetail;
import org.custommonkey.xmlunit.XMLUnit;

import org.dom4j.tree.DefaultDocument;

import org.dom4j.tree.DefaultElement;

import org.xml.sax.SAXException;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Nov 29, 2005
 * Time: 11:22:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class DOMUtil
{
    /**
     * Finds all immediate child elements of the specified child name of the specified parent Element.
     * @param childElementPath
     * @param ancestor
     * @return
     */
    public static List getChildren(String childName, Element parent)
    {
        List childElements = new ArrayList();

        int nodeCount = parent.nodeCount();

        for (int i = 0; i < nodeCount; i++)
        {
            Node currentNode = parent.node(i);

            if (currentNode instanceof Element)
            {
                String currentChildName = currentNode.getName();

                if (currentChildName.equals(childName))
                {
                    childElements.add(currentNode);
                }
            }
        }

        return childElements;
    }

    /**
     * Finds all Elements defined by the specified elementPath. e.g. A path of A.B.C would find all C Elements.
     * @param elementPath
     * @return
     */
    public static List getElements(String elementPath, Document document)
    {
        Element rootElement = document.getRootElement();

        List elements = getElements(rootElement, null, elementPath);

        return elements;
    }

    /**
     * Recurses the DOM finding all elements of a specified path name.
     * @param currentElement
     * @param currentElementPath
     * @param targetElementPath
     * @return
     */
    private static List getElements(Element currentElement, String currentElementPath, String targetElementPath)
    {
        List targetElements = new ArrayList();

        String currentElementName = currentElement.getName();

        if (currentElementPath == null)
        {
            currentElementPath = currentElementName;
        }
        else
        {
            currentElementPath = currentElementPath + "." + currentElementName;
        }

        if (currentElementPath.equals(targetElementPath))
        {
            targetElements.add(currentElement);
        }
        else
        {
            for (int i = 0, size = currentElement.nodeCount(); i < size; i++)
            {
                Node node = currentElement.node(i);

                if (node instanceof Element)
                {
                    targetElements.addAll(getElements((Element) node, currentElementPath, targetElementPath));
                }
            }
        }

        return targetElements;
    }

    /**
     * Maps the specified Element to its VO equivalent.
     * @param element
     * @return
     */
    public static VOObject mapElementToVO(Element element)
    {
        String elementName = element.getName();

        VOObject voObject = null;

        try
        {
            voObject = (VOObject) Class.forName("edit.common.vo." + elementName).newInstance();

            VOClass voClass = VOClass.getVOClassMetaData(voObject.getClass());

            int fieldCount = element.nodeCount();

            for (int i = 0; i < fieldCount; i++)
            {
                Node currentField = element.node(i);

                String fieldName = currentField.getName();

                String fieldValue = currentField.getText();

                if (fieldValue.equals(Constants.ScriptKeyword.NULL))
                {
                    fieldValue = null;
                }

                if (fieldName != null)
                {
                    VOMethod voSetter = voClass.getSimpleSetter(fieldName);

                    if (voSetter != null)
                    {
                        Class targetType = voSetter.getTargetFieldType();

                        if (targetType.getName().equalsIgnoreCase("java.math.BigDecimal"))
                        {
                            EDITBigDecimal fieldValueBigDecimal = new EDITBigDecimal(fieldValue);

                            voSetter.getMethod().invoke(voObject, new Object[] { fieldValueBigDecimal.getBigDecimal() });
                        }
                        else
                        {
                            voSetter.getMethod().invoke(voObject, new Object[] { voSetter.getTargetFieldAsObject(fieldValue) });
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return voObject;
    }

    /**
     * Formats and pretty-prints the specified Document to the specified Writer.
     * @param document
     * @param writer
     */
    public static void prettyPrint(Document document, Writer writer)
    {
        XMLWriter xmlWriter = null;

        try
        {
            OutputFormat outformat = OutputFormat.createPrettyPrint();

            outformat.setOmitEncoding(true);

            xmlWriter = new XMLWriter(writer, outformat);

            xmlWriter.write(document);

            xmlWriter.flush();
        }
        catch (IOException e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
    }
    
    /**
     * Converts the specified Document to the specified Writer as
     * a single compact String (not pretty print).
     * @param document
     * @param writer
     */
    public static void compactPrint(Document document, Writer writer)
    {
        XMLWriter xmlWriter = null;

        Element rootElement = document.getRootElement();
        
        compactPrint(rootElement, writer);
    }    
    
    /**
     * Converts the specified Document to the specified Writer as
     * a single compact String (not pretty print).
     * @param elementAsString
     * @param writer
     */
    public static void compactPrint(String elementAsString, Writer writer)
    {
        Document document;

        try
        {
            document = XMLUtil.parse(elementAsString);
        }
        catch (DocumentException e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            throw new RuntimeException(e);    
        }
        
        compactPrint(document, writer);
    }       
    
    /**
     * Converts the specified Element to the specified Writer as
     * a single compact String (not pretty print).
     * @param element
     * @param writer
     */
    public static void compactPrint(Element element, Writer writer)
    {
        XMLWriter xmlWriter = null;

        try
        {
            OutputFormat outformat = OutputFormat.createCompactFormat();

            outformat.setOmitEncoding(true);
            
            outformat.setNewlines(false);
            
            outformat.setNewLineAfterDeclaration(false);
            
            outformat.setSuppressDeclaration(true);
            
            xmlWriter = new XMLWriter(writer, outformat);

            xmlWriter.write(element);

            xmlWriter.flush();
        }
        catch (IOException e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
    }  
    
    /**
     * Builds a DOM4J Document from the specified well-formed XML.
     * @param xmlDocument
     * @return
     */
    public static Document buildDocument(String xmlDocument)
    {
        SAXReader reader = null;
        
        Document document = null;

        try
        {
            document = XMLUtil.parse(xmlDocument);
        }
        catch (Exception e)
        {
          System.out.println(e);

          e.printStackTrace();

          throw new RuntimeException(e);
        }       
        finally
        {
            reader = null;
        }
        
        return document;
    }
    
    /**
     * Generates a difference report between the specified "expected" Element, and the
     * specified "actual" Element. This is a recursive process considering all child
     * Elements as well.
     * child Elements as well.
     * @param expectedDocument
     * @param actualDocument
     * @return 
     *          <DifferenceXML>
     *              <Difference> // Repeated for every difference found between expected Document and the actual Document
     *                  <ExpectedValue></ExpectedValue>
     *                  <ActualValue></ActualValue>
     *                  <Description></Description>
     *                  <XPath></XPath>
     *              </Difference>
     *          </DifferenceXML>
     *          
     *   ... or an empty Document if no differences were found       
     */
    public static Document generateDifferences(Document expectedDocument, Document actualDocument)
    {
        Document differenceVODocument = new DefaultDocument();
        
        Element differenceXML = new DefaultElement("DifferenceXML");
        
        differenceVODocument.setRootElement(differenceXML);
        
        try
        {
            XMLUnit.setIgnoreAttributeOrder(true);

            XMLUnit.setIgnoreComments(true);

            XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);

            XMLUnit.setIgnoreWhitespace(true);

            Diff diff = XMLUnit.compareXML(expectedDocument.asXML(), actualDocument.asXML());

            DetailedDiff detailedDiff = new DetailedDiff(diff);

            List<Difference> allDifferences = detailedDiff.getAllDifferences();

            for (Difference difference: allDifferences)
            {
                // Get the values...                   
                NodeDetail expectedNodeDetail = difference.getControlNodeDetail();

                NodeDetail actualNodeDetail = difference.getTestNodeDetail();
                
                String expectedValue = expectedNodeDetail.getValue();
                
                String actualValue = actualNodeDetail.getValue();

                String description = difference.getDescription();
                
                String xPath = expectedNodeDetail.getXpathLocation(); // Could have used the actualNodeDetail.getXpathLocation() ?

                // Build the Elements...
                Element differenceElement = new DefaultElement("Difference");
                
                Element expectedValueElement = new DefaultElement("ExpectedValue");
                
                expectedValueElement.setText(expectedValue);
                
                Element actualValueElement = new DefaultElement("ActualValue");
                
                actualValueElement.setText(actualValue);
                
                Element descriptionElement = new DefaultElement("Description");
                
                descriptionElement.setText(description);
                
                Element xPathElement = new DefaultElement("XPath");
                
                xPathElement.setText(xPath);
                
                // Combine the Elements...
                differenceElement.add(expectedValueElement);
                
                differenceElement.add(actualValueElement);
                
                differenceElement.add(descriptionElement);
                
                differenceElement.add(xPathElement);
                
                differenceXML.add(differenceElement);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            throw new RuntimeException(e);
        }
        
        return differenceVODocument;
    }
    
    /**
     * Oftentimes we want to compeletely orphan an Element from
     * its existing Document. Child Element(s) will remain attached.
     * @param element
     * @param stripChildren if true, then the specified Element will have its (immediate) children disassociated as well.
     * @return the just-stripped Element as a convenience
     */
    public static Element stripFromDocument(Element element, boolean stripChildren)
    {
        if (element.isRootElement())
        {
            Document document = element.getDocument();
            
            document.setRootElement(null);
        }
        
        element.setDocument(null);
        
        element.setParent(null);
        
        if (stripChildren)
        {
            List<Element> childElements = element.elements();
            
            for (Element childElement:childElements)
            {
                String childElementText = childElement.getText();
                
                if (childElementText.isEmpty())
                {
                    childElement.setParent(null);
                    
                    element.remove(childElement);                    
                }
            }
        }
        
        return element;
    }
}
