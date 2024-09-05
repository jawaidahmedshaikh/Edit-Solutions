 /**
  * Created by IntelliJ IDEA.
  * User: gfrosti
  * Date: Jun 8, 2003
  * Time: 1:27:26 AM
  *
  * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
  * Systems Engineering Group, LLC Propietary and confidential.  Any use
  * subject to the license agreement.
  */
 package engine.sp;

import engine.common.Constants;
import engine.sp.custom.document.PRASEDocBuilder;
import fission.utility.DOMUtil;
import fission.utility.Util;
import fission.utility.XMLUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.tree.DefaultAttribute;
import org.dom4j.tree.DefaultDocument;
import org.dom4j.tree.DefaultElement;

import contract.Segment;
import edit.services.db.hibernate.SessionHelper;


public class SPParams
{
    private Map<String, Element> lastActiveElements = null;

    /**
     * The set of documents built during the SP session.
     */
    private Map<String, Document> documents;

    /**
     * Dynamic queries place their results in the ResultDocVO for
     * access by the scripter. This is the fixed name for the Document.
     */
    public static String RESULTDOCVO = "ResultDocVO";
    public static String SEGMENTDOCVO = "SegmentDocVO";
    public static String RIDERDOCVO = "RiderDocVO";

    /**
     * PRASE instances that are spawned from the currently running PRASE will often
     * need a document that define the name/value pairs to be loaded into the spawned PRASE.
     * Theformat of this Document is as follows:
     * 
     * <WSVO>
     *   <Param>
     *       <Name>FooName1</Name>
     *       <Value>FooValue1</Value>
     *   </Param>
     *   <Param...N>
     *       <Name>FooName...N</Name>
     *       <Value>FooValue...N</Value>
     *   </Param...N>
     * </WSVO>
     * 
     * This is just a "name" since the scripter is expected to build this thing from scratch when needed.
     */
    public static String WSVO = "WSVO";

    public SPParams()
    {
        this.lastActiveElements = new HashMap<String, Element>();

        initDocuments();
    }

    /**
     * Loads a XML Document into a DOM to serve as the data model for the executing scripts.
     * @param xmlDocument
     */
    public void addDocument(String rootElementName, String xmlDocument)
    {
        try
        {
            Document document = XMLUtil.parse(xmlDocument);

            addDocument(rootElementName, document);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    /**
     * Loads an objectified XML Document into a DOM to serve as the data model for the executing scripts.
     * @param voObject
     */
    public void addDocument(String rootElementName, Object voObject)
    {
        addDocument(rootElementName, Util.marshalVO(voObject));
    }

    //set the document passed into PRASE
    //    public void setDocument(Document document)
    //    {
    //        this.document = document;
    //    }

    /*   Foreach instruction uses this method.  Set lastActiveElements table for the element found, for the first and
      *   subsequent occurrances.  When there are no more occurrances remove the entry from the table.
      */

    public boolean setLastActiveElement(String elementPath, int foreachRequestCount)
    {
        boolean lastActiveSet = false;

        if (foreachRequestCount == 0)
        {
            removeLastActiveElement(elementPath); // reset back to the beginning
        }

        Element currentLastActiveElement = getLastActiveElement(elementPath);

        // if this key is not in the table we have the first one - verify this sometime
        if (foreachRequestCount == 0)
        {
            if (currentLastActiveElement != null)
            {
                lastActiveElements.put(elementPath, currentLastActiveElement);

                lastActiveSet = true;
            }
        }
        else if (foreachRequestCount > 0)
        {
            //when the key is in the table we want the next one
            lastActiveSet = setNextActiveElement(elementPath, currentLastActiveElement);

            if (!lastActiveSet)
            {
                lastActiveElements.remove(elementPath);
            }
        }

        return lastActiveSet;
    }

    /*   ClearLastActiveElement instruction uses this method.
      */

    public void removeLastActiveElement(String elementPath)
    {
        Element element = getLastActiveElement(elementPath);

        if (element != null)
        {
            lastActiveElements.remove(elementPath);
        }
    }

    public boolean setNextActiveElement(String elementPath, Element element)
    {
        Element nextActiveElement = null;

        boolean nextElementFound = false;

        String childElementName = getFieldName(elementPath);

        Element parentElement = element.getParent();

        List childElements = parentElement.elements(childElementName); // A list of Elemements by that name i.e. "InvestmentVO" .. not "...SegmentVO.InvestmentVO"

        Iterator it = childElements.iterator();

        while (it.hasNext())
        {
            Element currentElement = (Element) it.next();

            if (currentElement == element)
            {
                if (it.hasNext())
                {
                    nextActiveElement = (Element) it.next();
                    lastActiveElements.put(elementPath, nextActiveElement);
                    nextElementFound = true;
                }

                break;
            }
        }

        return nextElementFound;
    }

    /**
     * Retrieves the text value from the requested node for push, compare, and if instructions.
     * If neither the element nor the value exists (is null), the value is defaulted to #NULL.
     * @param fieldPath
     * @param fieldName
     * @return
     */
    public String getLastActiveElementValue(String fieldPath, String fieldName)
    {
        String value = null;

        Element element = getLastActiveElement(fieldPath);

        if (element == null)
        {
            //            throw new RuntimeException("SPParms: Field Requested Not Found in Data [ " + fieldPath + "." + fieldName + " ]");
            value = Constants.ScriptKeyword.NULL;
        }
        else
        {
            lastActiveElements.put(fieldPath, element);

            value = getValue(element, fieldName);

            if (value == null || value.equals(""))
            {
                value = Constants.ScriptKeyword.NULL;
            }
        }

        return value;
    }

    public String getValue(Element element, String fieldName)
    {
        String value = null;

        for (int i = 0, size = element.nodeCount(); i < size; i++)
        {
            Node currentNode = element.node(i);

            if (currentNode instanceof Element)
            {
                String currentFieldName = currentNode.getName();

                if (currentFieldName.equals(fieldName))
                {
                    value = currentNode.getText();

                    break;
                }
            }
        }

        return value;
    }

    /**
     * For the specified elementPath path of (e.g.) A.B.C, this creates
     * a new Element C and appends it to the last-active Element B.
     * 
     * It is possible that the Document A does not even exist. In this case,
     * a Document with root Element A is created
     * @param elementPath
     * @return
     */
    public Element addNewElement(String elementPath)
    {
        Element newElement = null;

        String parentPath = getParentPath(elementPath);

        String childElementName = getFieldName(elementPath);

        Document document = getDocumentByPath(elementPath);

        // It's possible this document doesn't even exist. e.g. The
        // script may be trying to create a brand new Element with
        // a brand-new document path.
        if (document == null)
        {
            document = buildDocumentComposition(elementPath);

            newElement = getLastActiveElement(elementPath);
        }

        else
        {
            // Need to make this new child Element the "last active" of this type.
            Element parentElement = getLastActiveElement(parentPath);

            newElement = new DefaultElement(childElementName);

            parentElement.add(newElement);
        }

        lastActiveElements.put(elementPath, newElement);

        return newElement;
    }

    //Use by update instruction the field may or may not exist

    public Element setNewElementValue(String elementPath, String fieldName, String data)
    {
        Element element = null;
        boolean dataFieldNew = false;

        element = getLastActiveElement(elementPath);

        if (element == null)
        {
            throw new RuntimeException("Element Path not found for Update Instruction");
        }

        Element dataElement = element.element(fieldName);

        if (dataElement == null)
        {
            dataElement = new DefaultElement(fieldName);
            dataFieldNew = true;
        }

        dataElement.setText(data);

        if (dataFieldNew)
        {
            element.add(dataElement);
        }

        //the element might not exist in the table
        lastActiveElements.put(elementPath, element);

        //return for output
        return element;
    }

    /**
     * For the last-active Element defined by the specified elementPath, the specified attribute will
     * be created/updated with the specified attribute value. Since the basic DOMs are Element driven (not
     * Attribute driven), we deliberately don't add attribute-level changes to the calculation outputs.
     * @param elementPath
     * @param attributeName
     * @param attributeValue
     */
    public void setNewAttributeValue(String elementPath, String attributeName, String attributeValue)
    {
        Element lastActiveElement = getLastActiveElement(elementPath);

        Attribute attribute = lastActiveElement.attribute(attributeName);

        if (attribute == null)
        {
            attribute = new DefaultAttribute(attributeName, attributeValue);

            lastActiveElement.add(attribute);
        }
        else
        {
            attribute.setText(attributeValue);
        }
    }

    /*   Updates to the lastActiveElements hashtable will take place outside of this method.
      *   This method can be call to verify existence
      */

    public Element getLastActiveElement(String fieldPath)
    {
        Element element = lastActiveElements.get(fieldPath);

        Element parentElement = getLastActiveParent(fieldPath);

        if (element == null)
        {
            element = findFirstElementForPath(fieldPath, parentElement);
        }
        else
        {
            //verify correct parent
            if (parentElement != null)
            {
                if (element.getParent() != parentElement)
                {
                    element = findFirstElementForPath(fieldPath, parentElement);
                }
            }
        }

        return element;
    }

    private Element findFirstElementForPath(String path, Element parentElement)
    {
        Element targetElement = null;

        String childName = getFieldName(path);

        if (parentElement == null)
        {
            // Field name is not expected
            String[] pathTokens = Util.fastTokenizer(path, ".");

            Element rootElement = getDocumentByPath(path).getRootElement();

            if (pathTokens.length == 1)
            {
                if (!rootElement.getName().equals(pathTokens[0]))
                {
                    throw new RuntimeException("Root Element Is [" + rootElement.getName() + "] Not [" + pathTokens[0] + "]");
                }
                else
                {
                    targetElement = rootElement;
                }
            }
            else
            {
                Stack pathStack = new Stack();

                pathStack.addAll(Arrays.asList(pathTokens));

                Collections.reverse(pathStack);

                pathStack.pop();

                targetElement = findFirstElementByTreeWalk(getDocumentByPath(path).getRootElement(), pathStack);
            }
        }
        else
        {
            List childElements = parentElement.selectNodes(childName);

            if (!childElements.isEmpty())
            {
                targetElement = (Element) childElements.get(0);
            }
        }

        if (targetElement != null)
        {
            if (!childName.equalsIgnoreCase(targetElement.getName()))
            {
                return null;
            }
        }

        return targetElement;
    }

    private Element findFirstElementByTreeWalk(Element element, Stack pathStack)
    {
        Element targetElement = null;

        String childElementName = (String) pathStack.pop();

        for (int i = 0, size = element.nodeCount(); i < size; i++)
        {
            Node node = element.node(i);

            if (node instanceof Element)
            {
                if (node.getName().equals(childElementName))
                {
                    if (pathStack.size() == 0)
                    {
                        targetElement = (Element) node;

                        break;
                    }
                    else
                    {
                        targetElement = findFirstElementByTreeWalk((Element) node, pathStack);
                    }
                }
            }

            if (targetElement != null)
            {
                break;
            }
        }

        return targetElement;
    }

    public boolean verifyPathExistence(String fieldPath)
    {
        boolean pathExists = false;

        String childName = getFieldName(fieldPath);
        String parentPath = fieldPath.substring(0, fieldPath.lastIndexOf("."));
        Element element = getLastActiveElement(parentPath);

        if (element == null)
        {
            element = findFirstElementForPath(fieldPath, element);

            if (element != null)
            {
                pathExists = true;
            }
        }
        else
        {
            for (int i = 0, size = element.nodeCount(); i < size; i++)
            {
                Node node = element.node(i);

                if (node instanceof Element)
                {
                    if (node.getName().equals(childName))
                    {
                        pathExists = true;

                        break;
                    }
                }
            }
        }

        return pathExists;
    }

    /**
     * For a path specified by A.B.C, it will
     * return the Element represented by A.B.
     * 
     * The a path specified by A only, it will return null
     * since A is the root and has no parent.
     * @param path
     * @return
     */
    private Element getLastActiveParent(String path)
    {
        Element parentElement = null;

        Document document = getDocumentByPath(path);

        Element rootElement = document.getRootElement();

        String parentPath = getParentPath(path);

        if (!rootElement.getName().equalsIgnoreCase(parentPath))
        {
            parentElement = lastActiveElements.get(parentPath);
        }

        return parentElement;
    }

    private String getParentPath(String elementPath)
    {
        int indexOfDot = elementPath.lastIndexOf('.');

        String parentPath = (indexOfDot > 0)? elementPath.substring(0, indexOfDot): elementPath;

        return parentPath;
    }

    /**
     * For the field path defined by A.B.C.d, returns d - the field name.
     * @param fullyQualifiedFieldPath
     * @return
     */
    public static String getFieldName(String fullyQualifiedFieldPath)
    {
        int indexOfDot = fullyQualifiedFieldPath.lastIndexOf('.');

        String childElementName = fullyQualifiedFieldPath.substring(indexOfDot + 1, fullyQualifiedFieldPath.length());

        return childElementName;
    }

    /**
     * For the element path defined by A.B.C, returns D - the element name.
     * @param fullyQualifiedElementPath
     * @return
     */
    public static String getElementName(String fullyQualifiedElementPath)
    {
        int indexOfDot = fullyQualifiedElementPath.lastIndexOf('.');

        String elementName = fullyQualifiedElementPath.substring(indexOfDot + 1, fullyQualifiedElementPath.length());

        return elementName;
    }

    /**
     * For the field path defined by A.B.C.d, returns A.B.C - the field path.
     * @param fullyQualifiedFieldPath
     * @return
     */
    public static String getFieldPath(String fullyQualifiedFieldPath)
    {
        int indexOfDot = fullyQualifiedFieldPath.lastIndexOf('.');

        String childElementName = fullyQualifiedFieldPath.substring(0, indexOfDot);

        return childElementName;
    }

    //    public String getVOAsXML(String VOName)
    //    {
    //        Node node = null;
    //
    //        Element rootElement = document.getRootElement();
    //
    //        for (int i = 0, size = rootElement.nodeCount(); i < size; i++)
    //        {
    //            node = rootElement.node(i);
    //
    //            if (node instanceof Element)
    //            {
    //                if (node.getName().equalsIgnoreCase("SegmentVO"))
    //                {
    //                    break;
    //                }
    //            }
    //        }
    //
    //        String segmentVOAsXML = node.asXML();
    //
    //        return segmentVOAsXML;
    //    }

    //    public void prettyPrint()
    //    {
    //        try
    //        {
    //            StringWriter xml = new StringWriter();
    //
    //            OutputFormat outformat = OutputFormat.createPrettyPrint();
    //
    //            //            outformat.setEncoding(aEncodingScheme);
    //            outformat.setOmitEncoding(true);
    //
    //            XMLWriter writer = new XMLWriter(xml, outformat);
    //            writer.write(this.document);
    //            writer.flush();
    //
    //            String line = null;
    //
    //            BufferedReader reader = new BufferedReader(new StringReader(xml.toString()));
    //
    //            int count = 0;
    //
    //            while ((line = reader.readLine()) != null)
    //            {
    //                if (line.endsWith("VO>"))
    //                {
    //                    System.out.println(line);
    //
    //                    if (line.indexOf("</") < 0)
    //                    {
    //                        count++;
    //                    }
    //                }
    //            }
    //
    //            System.out.println("\nVO Count: " + count);
    //        }
    //        catch (IOException e)
    //        {
    //            e.printStackTrace(); //To change body of catch statement use Options | File Templates.
    //        }
    //    }

    //    public List parseXML()
    //    {
    //        List textLines = new ArrayList();
    //
    //        try
    //        {
    //            StringWriter xml = new StringWriter();
    //
    //            OutputFormat outformat = OutputFormat.createPrettyPrint();
    //            outformat.setOmitEncoding(true);
    //
    //            XMLWriter writer = new XMLWriter(xml, outformat);
    //            writer.write(this.document);
    //            writer.flush();
    //
    //            String line = null;
    //
    //            BufferedReader reader = new BufferedReader(new StringReader(xml.toString()));
    //
    //            while ((line = reader.readLine()) != null)
    //            {
    //                textLines.add(line);
    //            }
    //        }
    //        catch (IOException e)
    //        {
    //            e.printStackTrace(); //To change body of catch statement use Options | File Templates.
    //
    //            throw new RuntimeException(e);
    //        }
    //
    //        return textLines;
    //    }

    /**
     * Getter. Finds the document stored by the root element name.
     * @return
     */
    public Document getDocumentByName(String rootElementName)
    {
        return getDocuments().get(rootElementName);
    }

    public void clear()
    {
        initDocuments();

        if (lastActiveElements != null)
        {
            lastActiveElements.clear();
        }
    }

    /**
     * Returns the set of elements specified by the childPath.
     * @param childPath
     * @param lastActiveParentPath the parent root-path from which relative child Elements will be found.
     * @return
     */
    Element[] findElementsByPath(String childPath, String lastActiveParentPath) throws SPException
    {
        List childElements = null;

        if (lastActiveParentPath == null)
        {
            childElements = findAllElementsByTreeWalk(getDocumentByPath(childPath).getRootElement(), null, childPath);
        }
        else
        {
            Element lastActiveParentElement = getLastActiveElement(lastActiveParentPath);

            childElements = findChildElements(childPath, lastActiveParentElement);
        }

        return (Element[]) childElements.toArray(new Element[childElements.size()]);
    }

    /**
     * Finds all child elements of the common last-active ancestor.
     * @param childElementPath
     * @param ancestor
     * @return
     */
    private List findChildElements(String childElementPath, Element ancestor)
    {
        List childElements = new ArrayList();

        int nodeCount = ancestor.nodeCount();

        for (int i = 0; i < nodeCount; i++)
        {
            Node currentNode = ancestor.node(i);

            if (currentNode instanceof Element)
            {
                String currentChildPath = getPath((Element) currentNode);

                if (currentChildPath.equals(childElementPath))
                {
                    childElements.add(currentNode);
                }

                childElements.addAll(findChildElements(childElementPath, (Element) currentNode));
            }
        }

        return childElements;
    }

    /**
     * Recurses the DOM finding all elements of a specified path name.
     * @param currentElement
     * @param currentElementPath
     * @param targetElementPath
     * @return
     */
    private List findAllElementsByTreeWalk(Element currentElement, String currentElementPath, String targetElementPath)
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
                    targetElements.addAll(findAllElementsByTreeWalk((Element) node, currentElementPath, targetElementPath));
                }
            }
        }

        return targetElements;
    }

    /**
     * Builds the path (branch) of a given Element. For example, child C would yield a path of "A.B.C".
     * @param element
     * @return
     */
    String getPath(Element element)
    {
        String path = null;

        String parentPath = null;

        String elementName = element.getName();

        Element parentElement = element.getParent();

        if (parentElement != null)
        {
            parentPath = getPath(parentElement);
        }

        if (parentElement == null)
        {
            path = elementName;
        }
        else
        {
            path = parentPath + "." + elementName;
        }

        return path;
    }

    /**
     * Adds a DOM4J Document by DocumentType. If an existing Document of the specified
     * DocumentType exists, then the existing is merged with this new Document. 
     * If the script writer activates the same Document twice (not DocumentType, but
     * the actual Document), no precautions are taken to prevent that.
     * @param rootElementName
     * @param document
     */
    public void addDocument(String rootElementName, Document document)
    {
        //System.out.println("dude: " + document.asXML());

        if (!getDocuments().containsKey(rootElementName))
        {
            getDocuments().put(rootElementName, document);
        }
        else
        {
            Document currentRootDocument = getDocumentByName(rootElementName);

            List<Element> elementsToMerge = document.getRootElement().elements();

            for (Element elementToMerge: elementsToMerge)
            {
                elementToMerge.setParent(null);

                currentRootDocument.getRootElement().add(elementToMerge);
            }
        }
    }

    /**
     * The map of documents as a DOM4J Document.
     * @return
     */
    public Map<String, Document> getDocuments()
    {
        return documents;
    }

    /**
     * Strips the first element of the path and assumes that it is the String name
     * of the required Document.
     * 
     * @param path
     * @return
     */
    public Document getDocumentByPath(String path)
    {
        int indexOfDot = path.indexOf(".");

        String documentNameAsStr = indexOfDot > 0? path.substring(0, path.indexOf(".")): path;

        Document document = getDocumentByName(documentNameAsStr);

        return document;
    }

    /**
     * From the path defined as A.B.C, this method will create
     * a Document of root Element A, and then recursively append
     * child Elements B and C to this newly created root Element A.
     * @param path
     * @return
     */
    private Document buildDocumentComposition(String path)
    {
        Document document = null;

        Element parentElement = null;

        String[] pathTokens = Util.fastTokenizer(path, ".");

        for (String pathToken: pathTokens)
        {
            if (document == null)
            {
                document = DocumentHelper.createDocument();

                parentElement = DocumentHelper.createElement(pathToken);

                document.setRootElement(parentElement);

                getDocuments().put(pathToken, document);
            }
            else
            {
                Element childElement = DocumentHelper.createElement(pathToken);

                parentElement.add(childElement);

                parentElement = childElement;
            }
        }

        return document;
    }

    /**
     * The SPParams object is reused as part of the pooling mechanism
     * with ScriptProcessor. It's state needs to be returned to a neutral
     * state between uses of ScriptProcessor; this includes instantiating 
     * a new (empty) documents collection.
     */
    private void initDocuments()
    {
        documents = new HashMap<String, Document>();

        buildResultDocVO();
    }

    /**
     * Builts the static Document that will hold all results
     * retrieved during dynamic queries (generally from the RetrieveEntity instruction).
     */
    private final void buildResultDocVO()
    {
        Document resultDocVO = new DefaultDocument();

        Element resultDocVORootElement = new DefaultElement(RESULTDOCVO);

        resultDocVO.setRootElement(resultDocVORootElement);

        addDocument(RESULTDOCVO, resultDocVO);
    }

    /**
     * The set of Elements that have actively navigated to by the scripter.
     * There can not be more than one active Element identified by the
     * same unique path at one moment in time. For example, if SegmentVO.InvestmentVO
     * has multiple InvestmentVOs, then the last-active-element key of "SegmentVO.InvestmentVO"
     * can only map to one Element at any moment in time.
     * @return
     */
    public Map<String, Element> getLastActiveElements()
    {
        return this.lastActiveElements;
    }

    /**
     * A horribly named method that determines the last-active-parent
     * from the name [only] of a child Element.
     * 
     * The concept of "purported" comes from the near-zero chance that there
     * are two last-active-parents which have the same child name as the 
     * specified childElementName. This was considered an acceptable risk,
     * but we check for it anyway.
     * @return Element the parent Element of the specified childElementName
     * @throws SPException when there are two last-active-parents with the same childElementName
     */
    public Element getLastActivePurportedParent(String childElementName) throws SPException
    {
        Element parentElement = null;

        Collection<Element> lastActiveElements = getLastActiveElements().values();

        int parentCount = 0;

        for (Element element: lastActiveElements)
        {
            if (!element.elements(childElementName).isEmpty())
            {
                parentElement = element;

                parentCount++;
            }
        }

        if (parentCount > 1)
        {
            throw new SPException("An ambiguous condition was reached - there are two parent Elements with a child Element name of [" + childElementName + "]", SPException.PARAMETER_INVALID_ERROR);
        }

        return parentElement;
    }
    
    public Element getBaseSegment_AsElement() {
    	 PRASEDocBuilder segmentDocument = (PRASEDocBuilder) getDocumentByName(SPParams.SEGMENTDOCVO);
         Element segmentElement = (Element) DOMUtil.getElements("SegmentDocVO.SegmentVO", segmentDocument).get(0);         
         return segmentElement;
    }
    
    public Segment getBaseSegment_AsSegment() {
        Segment segment = (Segment) SessionHelper.mapToHibernateEntity_WithPK(Segment.class, getBaseSegment_AsElement(), SessionHelper.EDITSOLUTIONS);
        return segment;
    }
    
    @SuppressWarnings("unchecked")
	public List<Element> getRiderSegments_AsElements() {
    	
    	PRASEDocBuilder riderDocument = (PRASEDocBuilder) getDocumentByName(SPParams.RIDERDOCVO);
        
		List<Element> riderElements = DOMUtil.getElements("RiderDocVO.SegmentDocVO.SegmentVO", riderDocument);
        
        if (riderElements == null || riderElements.size() == 0) {
      	  riderElements = DOMUtil.getElements("RiderDocVO.SegmentVO", riderDocument);
        }
        
        return riderElements;
    }
    
    public List<Segment> getRiderSegments_AsSegments() {
    	
    	List<Segment> riderSegments = new ArrayList<>();
        List<Element> riderElements = getRiderSegments_AsElements();

        if (riderElements != null && riderElements.size() > 0) {
  		  Element riderElement;
  		  Segment rider;
  		  
      	  for (int x = 0; x < riderElements.size(); x++) {
      	      riderElement = (Element) riderElements.get(x);
      		  rider = (Segment) SessionHelper.mapToHibernateEntity_WithPK(Segment.class, riderElement, SessionHelper.EDITSOLUTIONS);
      		  riderSegments.add(rider); 
      		  
      	  }
        }
        
        return riderSegments;
    }
    
    public List<Element> getAllSegments_AsElements() {
    	List<Element> segmentElements = new ArrayList<>();

    	Element baseSegment = getBaseSegment_AsElement();
    	if (baseSegment != null) {
    		segmentElements.add(baseSegment);
    	}
    	
    	List<Element> riderElements = getRiderSegments_AsElements();
    	
    	if (riderElements != null && riderElements.size() > 0) {
    		segmentElements.addAll(riderElements);
    	}
    	
    	return segmentElements;
    }
    
    public List<Segment> getAllSegments_AsSegments() {
    	List<Segment> segments = new ArrayList<>();

    	Segment baseSegment = getBaseSegment_AsSegment();
    	if (baseSegment != null) {
    		segments.add(baseSegment);
    	}
    	
    	List<Segment> riderSegments = getRiderSegments_AsSegments();
    	
    	if (riderSegments != null && riderSegments.size() > 0) {
    		segments.addAll(riderSegments);
    	}
    	
    	return segments;
    }
}
