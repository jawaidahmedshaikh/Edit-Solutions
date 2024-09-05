package conversion;

import edit.common.exceptions.SEGConversionException;

import java.io.BufferedReader;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * The root of any "Grouping". A Grouping contains Records which contain
 * Columns and are used to define and map a Flat file to an equivalent
 * XML document.
 */
public class GroupNode extends ConversionNode
{
    /**
     * Null/Empty dates can be specified by empty spaces. For reasons of 
     * practicality, we will represent these spaces as some other character.
     */ 
    public static String NO_DATE_SPACE_MASK = "*";
        
    /**
     * The default name for any Element represented by this GroupNode.
     */
    public static String DEFAULT_GROUP_NAME = "GroupVO";
    
    /**
     * The ordered list of child RecordNodes of this GroupNode.
     */
    private List<RecordNode> recordNodes = new ArrayList<RecordNode>();
    
    /**
     * All RecordNodes contained by this GroupNode will have
     * the same RecordNode.ColumnNode.length for the initial column (the
     * one that defines the recordType by definition).
     */
    private int recordTypeLength;
    
    /**
     * The one RecordNode identified as the header RecordNode in this GroupNode.
     */
    public RecordNode headerRecordNode;
    
    /**
     * Toggles converting on/off for this GroupNode.
     */
    private boolean convertingStarted;
    
    /**
     * The date format of the underlying flat file.
     */
    private DateFormat dateFormat;
    
    /**
     * Different clients represent the concept of a system minimum date differently. Examples might
     * be 00/00/0000 or 00000000. We need to recognize and convert it to something meaningful to our system.
     * For us, this has been 01/01/1800 which is a legitimate date. It is also possible that a client uses the same
     * date for both a min and max date. In that case, it simply represents a place-holder for some min/max/no date.
     * Nonetheless, we still demand that a min/max/no be defined.
     */ 
    private String minDate;
    
    /**
     * Different clients represent the concept of a system maximum date differently. Examples might
     * be 99/99/9999 or 99999999. We need to recognize and convert it to something meaningful to our system.
     * For us, this has been 12/31/9999 which is a legitimate date. It is also possible that a client uses the same
     * date for both a min and max date. In that case, it simply represents a place-holder for some min/max/no date.
     * Nonetheless, we still demand that a min/max/no be defined.
     */ 
    private String maxDate;
    
    /**
     * Different clients represent the concept of a system no/null date differently. Examples might
     * be 00/00/0000 or 00000000 or '        '. We need to recognize and convert it to something meaningful to our system.
     * For us, this has been null which is a legitimate empty value. It is also possible that a client uses the same
     * date for both a min, max, and no date and simply relies on the coder's understanding of the context. 
     * In that case, it simply represents a place-holder for some min/max/no date.
     * Nonetheless, we still demand that a min/max/no be defined.
     */         
    private String noDate;
    
    /**
     * RecordNodes are identified by a "type" String. They are stored
     * for easy-access by type.
     */
    private Map<String, RecordNode> recordNodesByType = new HashMap<String, RecordNode>();
    
    public GroupNode()
    {
    }

    /**
     * Reads a number of lines from the specified Reader and converts them
     * into XML according to the mapping rules as defined by thie GroupNode (and
     * its child RecordNode(s).ColumnNode(s). 
     * @param flatFileLine
     * @return
     * @throws SEGConversionException
     */
    public void convert(String flatFileLine, Element groupElement) throws SEGConversionException
    {
        RecordNode recordNode = getRecordNode(flatFileLine);   
        
        if (recordNode != null) // This is valid in that GroupNode(s) only define the RecordNode(s) they want - null implies the RecordNode was not defined for this GroupNode
        {
            if (!recordNode.isSkip())
            {
                recordNode.convert(flatFileLine, groupElement);
            }            
        }
    }
    
    /**
     * The appropriate RecordNode needs to be matched with the specified 
     * flatFileLine. Every flatFileLine begins with a column that defines the 
     * associated RecordType. This fact is used to find the matching RecordNode.
     * @param flatFileLine
     * @return
     */
    private RecordNode getRecordNode(String flatFileLine)
    {
        String recordType = getRecordType(flatFileLine);  
        
        if (getRecordNodesByType().isEmpty())
        {
            for (RecordNode recordNode:getRecordNodes())
            {
                String type = recordNode.getType();
                
                getRecordNodesByType().put(type, recordNode);
            }
        }
        
        return getRecordNodesByType().get(recordType);
    }
    
    /**
     * Extract the recordType from the specified flatFileLine.
     * @param flatFileLine
     * @return
     */
    public String getRecordType(String flatFileLine)
    {
        return flatFileLine.substring(0, getRecordTypeLength()).trim();        
    }
    
    /**
     * A series of rules to determine if this GroupNode should continue
     * processing the received flat-file lines.
     * @param flatFileLine
     * @return
     */
    private boolean continueConverting(String flatFileLine)
    {
        boolean continueConverting = false;
        
        if (!isConvertingStarted()) // no brainer - 1st record and should be a header record
        {
            continueConverting = true;
        }
        
        return continueConverting;
    }
    

    /**
     * Extracts the "name" of this GroupNode and then
     * continues with the children RecordNodes.
     * @param nodeElement
     */
    public void unmarshal(Element nodeElement)
    {
        // RecordTypeLength
        Element recordTypeLengthElement = nodeElement.element("RecordTypeLength");
        
        setRecordTypeLength(new Integer(recordTypeLengthElement.getText()).intValue());
        
        // DateFormat
        Element dateFormatElement = nodeElement.element("DateFormat");
        
        DateFormat dateFormat = new SimpleDateFormat(dateFormatElement.getText());
        
        setDateFormat(dateFormat);
        
        // MaxDate
        Element maxDateElement = nodeElement.element("MaxDate");
        
        setMaxDate(maxDateElement.getText());
        
        // MinDate
        Element minDateElement = nodeElement.element("MinDate");
        
        setMinDate(minDateElement.getText());
        
        // NoDate
        Element noDateElement = nodeElement.element("NoDate");
        
        setNoDate(noDateElement.getText());
        
        // RecordNodes
        List<Element> recordNodeElements = nodeElement.elements("RecordVO");
        
        for (Element recordNodeElement:recordNodeElements)
        {
            RecordNode recordNode = new RecordNode();
            
            recordNode.setGroupNode(this);
            
            recordNode.unmarshal(recordNodeElement);       
            
            getRecordNodes().add(recordNode);
        }
    }
    
    /**
     * The number of associated child RecordNodes of this GroupNode.
     * @return
     */
    public int getRecordNodeCount()
    {
        return getRecordNodes().size();
    }

    /**
     * @see #recordNodes
     * @return
     */
    public List<RecordNode> getRecordNodes()
    {
        return recordNodes;
    }

    public Element marshal()
    {
        return null;
    }

    /**
     * @see #recordTypeLength
     * @param recordTypeLength
     */
    public void setRecordTypeLength(int recordTypeLength)
    {
        this.recordTypeLength = recordTypeLength;
    }

    /**
     * @see #recordTypeLength
     * @return
     */
    public int getRecordTypeLength()
    {
        return recordTypeLength;
    }

    /**
     * @see #convertingStarted
     * @param convertingStarted
     */
    private void setConvertingStarted(boolean convertingStarted)
    {
        this.convertingStarted = convertingStarted;
    }

    /**
     * @see #convertingStarted
     * @return
     */
    private boolean isConvertingStarted()
    {
        return convertingStarted;
    }

    /**
     * @see #recordNodesByType
     * @return
     */
    private Map<String, RecordNode> getRecordNodesByType()
    {
        return recordNodesByType;
    }

    /**
     * Gets the one header RecordNode in the list of RecordNodes
     * for this GroupNode.
     * @return
     */
    public RecordNode getHeaderRecordNode()
    {
        if (this.headerRecordNode ==  null)
        {
            for (RecordNode recordNode:getRecordNodes())
            {
                if (recordNode.isHeader())
                {
                    this.headerRecordNode = recordNode;
                    
                    break;
                }
            }
        }
        
        return this.headerRecordNode;
    }

    /**
     * @see #dateFormat
     * @param dateFormat
     */
    public void setDateFormat(DateFormat dateFormat)
    {
        this.dateFormat = dateFormat;
    }

    /**
     * @see #dateFormat
     * @return
     */
    public DateFormat getDateFormat()
    {
        return dateFormat;
    }

    /**
     * @see #minDate
     * @param minDate
     */
    public void setMinDate(String minDate)
    {
        this.minDate = minDate;
    }

    /**
     * @see #minDate 
     * @return
     */
    public String getMinDate()
    {
        return minDate;
    }

    /**
     * @see #maxDate
     * @param maxDate
     */
    public void setMaxDate(String maxDate)
    {
        this.maxDate = maxDate;
    }

    /**
     * @see #maxDate
     * @return
     */
    public String getMaxDate()
    {
        return maxDate;
    }

    /**
     * @see #noDate
     * @param noDate
     */
    public void setNoDate(String noDate)
    {
        this.noDate = noDate;
    }

    /**
     * @see #noDate
     * @return
     */
    public String getNoDate()
    {
        return noDate;
    }

    /**
     * True if the specified value matches the user-defined value that represents a
     * max date AND 
     
     * @param columnValue
     * @return
     */
    boolean isMaxDate(String columnValue)
    {
        return false;
    }
}
