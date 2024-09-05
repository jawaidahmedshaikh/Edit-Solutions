package conversion;

import edit.common.EDITDateTime;

import edit.services.db.hibernate.SessionHelper;

import engine.business.Calculator;

import engine.component.CalculatorComponent;

import fission.utility.Util;
import fission.utility.XMLUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import java.util.Stack;

import org.dom4j.Document;
import org.dom4j.Element;

import org.dom4j.tree.DefaultDocument;
import org.dom4j.tree.DefaultElement;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Flat files that have been converted to XML via XFlat now need to be
 * processed.
 * 
 * Since we controlled the creation of the Flat -> XML formation, we know
 * that the XML contains target Element(s) that are [always] immediate children
 * of the root element. This assumption is important, and should it change, then
 * this handler is not, currently, flexible enough to handle it.
 */
public class ConversionHandler extends DefaultHandler
{
    /**
     * The root element will be ignored since we care only about
     * the 'documents' defined within the root document.
     */
    private String rootElementName;
    
    /**
     * After ignoring the 'true' rootElementName, we capture
     * the desired rootElementName (immediate child of the true rootElementName).
     */
    private String targetRootElementName;
    
    /**
     * The job using this handler.
     */
    private ConversionJob conversionJob;
    
    private StringBuilder elementTextAccumulator;
    
    private StringBuilder elementAccumulator;
    
    public ConversionHandler(ConversionJob conversionJob)
    {
        this.conversionJob = conversionJob;        
    }
    
    @Override
    public void startDocument() throws SAXException
    {
        setElementTextAccumulator(new StringBuilder());
        
        setElementAccumulator(new StringBuilder());
        
        super.startDocument();
    }    
    
    @Override
    public void startElement(String uri, String elementName, String qName, Attributes attributes) throws SAXException
    {
        updateElementState(elementName);
        
        if (!isRootElement(elementName))
        {
            getElementAccumulator().append(buildStartElement(elementName));            
        }
        
        super.startElement(uri, elementName, qName, attributes);
    }
    
    @Override
    public void endElement(String uri, String elementName, String qName) throws SAXException
    {
        if (!elementName.equals(getRootElementName()))
        {
            String elementText = getElementTextAccumulator().toString();
            
            getElementAccumulator().append(elementText);
            
            getElementAccumulator().append(buildEndElement(elementName));
        }
        
        if (elementName.equals(getTargetRootElementName()))
        {
            String targetDocument = getElementAccumulator().toString();

            getElementAccumulator().setLength(0);
            
            //TODO You now have the target document and can send it to PRASE. I debate doing it in the handler
            // or doing a callback to the ConversionJob. I am leaning toward the callback.
            
        }
        
        getElementTextAccumulator().setLength(0);
        
        super.endElement(uri, elementName, qName);
    }
    
    /**
     * Determines if the specified elementName matches the rootElementName.
     * @param elementName
     * @return true if the specified elementName matches the rootElementName.
     */
    private boolean isRootElement(String elementName)
    {
        return elementName.equals(getRootElementName());
    }
    
    /**
     * This is a convoluted set checks for something that is very simple.
     * We simply want to ignore the rootElement of this document, and start
     * grabbing the firstLevel child element. e.g. For the below docuemnt,
     * we want to ignore the RootElement and grab everything between the
     * opening and closing of each TargetElement.
     * 
     * <RootElement>
     *  <TargetElement>
     *      <Foo1></Foo1>
     *      <FooN></FooN>
     *  </TargetElement>
     *  <TargetElement>
     *      <Foo1></Foo1>
     *      <FooN></FooN>
     *  </TargetElement>
     *  </RootElement>
     * @param elementName
     * @return
     */
    private void updateElementState(String elementName)
    {
        if (rootElementName == null) // the VERY first element is the root by definition
        {
            setRootElementName(elementName);
        }
        else
        {
            if (getTargetRootElementName() == null)
            {
                setTargetRootElementName(elementName); // The VERY next element is the target root by our definition
            }
        }
    }

    /**
     * @see #rootElementName
     * @param newrootElementName
     */
    public void setRootElementName(String newrootElementName)
    {
        this.rootElementName = newrootElementName;
    }

    /**
     * @sees #rootElementName
     * @return
     */
    public String getRootElementName()
    {
        return rootElementName;
    }

    /**
     * @see #targetRootElementName
     * @param newtargetRootElementName
     */
    public void setTargetRootElementName(String newtargetRootElementName)
    {
        this.targetRootElementName = newtargetRootElementName;
    }

    /**
     * @see #targetRootElementName
     * @return
     */
    public String getTargetRootElementName()
    {
        return targetRootElementName;
    }

    /**
     * @see #conversionJob
     * @param newconversionJob
     */
    public void setConversionJob(ConversionJob newconversionJob)
    {
        this.conversionJob = newconversionJob;
    }

    /**
     * @see #conversionJob
     * @return
     */
    public ConversionJob getConversionJob()
    {
        return conversionJob;
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        getElementTextAccumulator().append(ch, start, length);
        
        super.characters(ch, start, length);
    }


    public void setElementTextAccumulator(StringBuilder newelementTextAccumulator)
    {
        this.elementTextAccumulator = newelementTextAccumulator;
    }

    public StringBuilder getElementTextAccumulator()
    {
        return elementTextAccumulator;
    }

    public void setElementAccumulator(StringBuilder newtargetDocumentAccumulator)
    {
        this.elementAccumulator = newtargetDocumentAccumulator;
    }

    public StringBuilder getElementAccumulator()
    {
        return elementAccumulator;
    }
    
    /**
     * A convenience method that returns the starting element as a String.
     * 
     * e.g. 
     * elementName = FooElementName
     * 
     * <FooElementName>
     * 
     * 
     * @param elementName
     * @return the starting XML element
     */
    private String buildStartElement(String elementName)
    {
        String startElement = "<" + elementName + ">";
        
        return startElement;
    }
    
    /**
     * A convenience method that returns the ending element as a String.
     * 
     * e.g. 
     * elementName = FooElementName
     * 
     * </FooElementName>
     * 
     * 
     * @param elementName
     * @return the ending XML element
     */    
    private String buildEndElement(String elementName)
    {
        String endElement = "</" + elementName + ">";
        
        return endElement;        
    }
}
