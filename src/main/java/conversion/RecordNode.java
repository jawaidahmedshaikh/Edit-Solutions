package conversion;

import edit.common.exceptions.SEGConversionException;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * A child of a GroupNode. Each RecordNode corresponds to 
 * a single flat file record.
 */
public class RecordNode extends ConversionNode
{
    /**
     * The default name for any Element converted with this RecordNode.
     */
    public static final String DEFAULT_RECORD_NAME = "RecordVO";
    
    /**
     * Then true, this RecordNode will not be included in the generated XML.
     */
    private boolean skip;
    
    /**
     * The list of child ColumnNode of this RecordNode.
     */
    private List<ColumnNode> columnNodes = new ArrayList<ColumnNode>();
    
    /**
     * True if this RecordNode has been identified as the "header".
     */
    private boolean header;
    
    /**
     * The parent groupNode.
     */
    private GroupNode groupNode;
    
    /**
     * A user-defined value that uniquely defines the "type" of this RecordNode within its GroupNode.
     */
    private String type;
    
    public RecordNode()
    {
    }

    /**
     * Looks to extract the "name", "type" and "maxOccurs" properties for this RecordNode.
     * It then continues with the child ColumnNodes.
     * @param nodeElement
     */
    public void unmarshal(Element nodeElement)
    {   
        // Skip
        Element skipElement = nodeElement.element("Skip");
        
        setSkip((skipElement.getText().equals("Y"))?true:false);
        
        // IsHeader
        Element isHeaderElement = nodeElement.element("Header");
        
        setHeader((isHeaderElement.getText().equals("Y"))?true:false);
        
        // Type
        Element typeElement = nodeElement.element("Type");
        
        setType(typeElement.getText());
        
        // ColumnNodes
        List<Element> columnNodeElements = nodeElement.elements("ColumnVO");
        
        for (Element columnNodeElement:columnNodeElements)
        {
            ColumnNode columnNode = new ColumnNode();
            
            columnNode.setRecordNode(this);
            
            columnNode.unmarshal(columnNodeElement);
            
            getColumnNodes().add(columnNode);
        }
    }

    /**
     * @see #columnNodes
     * @return
     */
    public List<ColumnNode> getColumnNodes()
    {
        return columnNodes;
    }

    /**
     * Uses the conversion rules defined by this RecordNode to create
     * a Record Element.
     * @param flatFileLine
     * @return
     */
    public void convert(String flatFileLine, Element groupElement) throws SEGConversionException
    {
        Element recordElement = new DefaultElement(DEFAULT_RECORD_NAME);
        
        int flatFileLineLength = flatFileLine.length();
        
        int columnIndexBegin = 0;
        
        int columnIndexEnd = 0;

        for (ColumnNode columnNode:getColumnNodes())
        {
            if (columnIndexBegin >= flatFileLineLength)
            {
                // Flat-file lines may be cut short (they are not even padded with empty spaces - we accept this.
                break;                
            }
            else
            {
                columnIndexEnd += columnNode.getLength();

                // If the flat-file line does not end with padded spaces, then we can rely on absolute fixed column lengths.
                columnIndexEnd = (columnIndexEnd > flatFileLineLength)?flatFileLineLength:columnIndexEnd;

                String columnValue = flatFileLine.substring(columnIndexBegin, columnIndexEnd);
                
                columnNode.convert(columnValue, recordElement);
                
                columnIndexBegin += columnNode.getLength();                   
            }
        }
        
        groupElement.add(recordElement);                
    }

    /**
     * @see #header 
     * @param header
     */
    public void setHeader(boolean header)
    {
        this.header = header;
    }

    /**
     * @see #header
     * @return
     */
    public boolean isHeader()
    {
        return header;
    }

    public Element marshal()
    {
        return null;
    }

    /**
     * [Assuming] this RecordNode is the header RecordNode, we identify
     * if the specified flatFileLine's recordType (the 1st column of the flatFileLine)
     * @param flatFileLine
     * @return
     */
    boolean isHeaderRecord(String flatFileLine)
    {
        return false;
    }

    /**
     * @see #skip
     * @param skip
     */
    public void setSkip(boolean skip)
    {
        this.skip = skip;
    }

    /**
     * @see #skip
     * @return
     */
    public boolean isSkip()
    {
        return skip;
    }
    
    /**
     * @see #type
     * @param type
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * @see #type
     * @return
     */
    public String getType()
    {
        return type;
    }

    public Element getElement()
    {
        return null;
    }

    /**
     * @see #groupNode
     * @param groupNode
     */
    public void setGroupNode(GroupNode groupNode)
    {
        this.groupNode = groupNode;
    }

    /**
     * @see #groupNode
     * @return
     */
    public GroupNode getGroupNode()
    {
        return groupNode;
    }
}
