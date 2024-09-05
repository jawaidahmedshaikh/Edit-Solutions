package engine.sp.custom.document;

import java.util.Map;

import org.dom4j.Element;

/**
 * With the addition of our Conversion Framework, we require a document
 * to house the raw XML from which data will be extracted and mapped to
 * our underlying database.
 * 
 * As of this writing, the basic struction of the raw XML is always:
 * 
 * <GroupVO>
 *      <RecordVO> // repeated
 *          <ColumnVO></ColumnVO> // repeated
 *      </RecordVO> 
 * </GroupVO>
 * 
 * This document is activated like all others within the activation framework. The
 * one difference is that there are no WS values expected. 
 */
public class ConversionDocument extends PRASEDocBuilder
{
    public static final String ROOT_ELEMENT_NAME = "GroupVO";
    
    public ConversionDocument()
    {
    }
    
    public ConversionDocument(Map<String, String> workingStorage)
    {
        super(workingStorage);
    }
    
    public ConversionDocument(Element groupVOElement)
    {
        this.setRootElement(groupVOElement);
        
        setDocumentBuilt(true);
    }

    public void build()
    {
        // This document is assumed built via the Constructor.                
    }

    public String getRootElementName()
    {
        return ROOT_ELEMENT_NAME;
    }

    public String[] getBuildingParameterNames()
    {
        return new String[0];
    }
}
