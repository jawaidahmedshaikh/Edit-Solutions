package conversion;

import edit.common.EDITDate;

import edit.common.exceptions.SEGConversionException;

import engine.common.Constants;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

import java.text.ParseException;

import java.util.Date;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * A child of a RecordNode. Each ColumnNode corresponds to 
 * a length-specified portion of a flat file record.
 */
public class ColumnNode extends ConversionNode
{

    /**
     * Unspecified dates will often default to some specified min value.
     */
    public static String DEFAULTDATE_MIN = "Min";
    /**
     * Unspecified dates will often default to some specified max value.
     */
    public static String DEFAULTDATE_MAX = "Max";
    /**
     * Unspecified dates will often default to some specified none/null value.
     */
    public static String DEFAULTDATE_NO = "No";
    public static final String DEFAULT_COLUMN_NAME = "ColumnVO";
    /**
     * The fixed length of this ColumnNode in the list of
     * ColumnNodes for the containing RecordNode.
     */
    private int length;
    /**
     * Flagged true when this ColumnNode defines the recordType of the 
     * parent RecordNode. It is always the 1st ColumnNode of any RecordNode by definition,
     * though we will explicitly set this instead of making it a calculated value.
     */
    private boolean recordType;
    /**
     * The name of this ColumnNode (e.g. EffectiveDate).
     */
    private String name;
    /**
     * The parent RecordNode.
     */
    private RecordNode recordNode;
    /**
     * When true, specifies that the data represented by this node is a date datatype.
     */
    private boolean date;
    /**
     * Dates that are not supplied, or are supplied as placed-holders need to 
     * be represented as a Min, Max, or Null (no) date within our system.
     */
    private String defaultDate;

    public ColumnNode()
    {
    }

    /**
     * @see #name
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @see #name
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     * @see #length
     * @param length
     */
    public void setLength(int length)
    {
        this.length = length;
    }

    /**
     * @see #length
     * @return
     */
    public int getLength()
    {
        return length;
    }

    /**
     * @see #date
     * @param date
     */
    public void setDate(boolean date)
    {
        this.date = date;
    }

    /**
     * @see #date
     * @return
     */
    public boolean isDate()
    {
        return date;
    }

    /**
     * Extracts the "name" and "length" attributes from the specified nodeElement.
     * @param nodeElement
     */
    public void unmarshal(Element nodeElement)
    {
        // Name
        Element nameElement = nodeElement.element("Name");

        setName(nameElement.getText());

        // RecordType
        Element recordTypeElement = nodeElement.element("RecordType");

        setRecordType((recordTypeElement.getText().equals("Y")) ? true : false);

        // Length
        Element lengthElement = nodeElement.element("Length");

        setLength(new Integer(lengthElement.getText()).intValue());

        // Date
        Element dateElement = nodeElement.element("Date");

        setDate((dateElement.getText().equals("Y")) ? true : false);

        // DefaultDate - optional Element
        Element defaultDateElement = nodeElement.element("DefaultDate");

        if (defaultDateElement != null)
        {
            setDefaultDate(defaultDateElement.getText());
        }
    }

    /**
     * Uses the conversion rules defined by this ColumnNode to
     * build a Column Element. The Column Value will be set to #NULL if
     * the columnValue is null, or an empty String.
     * @param columnValue
     * @return
     */
    public void convert(String columnValue, Element recordElement) throws SEGConversionException
    {
        try
        {
            // Column Element
            Element columnElement = new DefaultElement(DEFAULT_COLUMN_NAME);

            // Column Name
            Element columnNameElement = new DefaultElement("Name");

            columnNameElement.setText(getName());

            // Column Value
            Element columnValueElement = new DefaultElement("Value");

            if (isDate())
            {
                columnValue = getColumnDateValue(columnValue);
            }

            if (columnValue != null)
            {
                columnValue = columnValue.trim();

                if (columnValue.length() == 0)
                {
                    columnValue = Constants.ScriptKeyword.NULL;
                }
            }
            else
            {
                columnValue = Constants.ScriptKeyword.NULL;
            }

            columnValueElement.setText(columnValue.trim());

            // Combine
            columnElement.add(columnNameElement);

            columnElement.add(columnValueElement);

            recordElement.add(columnElement);
        }
        catch (ParseException e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new SEGConversionException(e);
        }
    }

    /**
     * If the column represents a Date, then there is some logic involved to determine
     * the proper value.
     * 1. If the column value is that of the user-defined default MAX value, then we set it to our EDITDate.DEFAULT_MAX_DATE.
     * 2. If the column value is that of the user-defined default MIN value, then we set it to our EDITDate.DEFAULT_MIN_DATE.
     * 3. If the column value is that of the user-defined default NO value, then we set it to NULL.
     * 4. Otherwise, the column value is assumed to be a valid Date/EDITDate.
     * @param columnValue
     * @return
     * @throws ParseException
     */
    private String getColumnDateValue(String columnValue) throws ParseException
    {
        String columnValueResult = null;

        GroupNode groupNode = getRecordNode().getGroupNode();

        if (isMaxDate(columnValue))
        {
            columnValueResult = EDITDate.DEFAULT_MAX_DATE;
        }
        else if (isMinDate(columnValue))
        {
            columnValueResult = EDITDate.DEFAULT_MIN_DATE;
        }
        else if (isNoDate(columnValue))
        {
            columnValueResult = null;
        }
        else
        {
            Date date = groupNode.getDateFormat().parse(columnValue);

            EDITDate editDate = new EDITDate(date.getTime());

            columnValueResult = editDate.getFormattedDate();
        }

        return columnValueResult;
    }

    public Element marshal()
    {
        return null;
    }

    /**
     * @see #recordType
     * @param recordType
     */
    public void setRecordType(boolean recordType)
    {
        this.recordType = recordType;
    }

    /**
     * @see #recordType
     * @return
     */
    public boolean isRecordType()
    {
        return recordType;
    }

    /**
     * @see #recordNode
     * @param recordNode
     */
    public void setRecordNode(RecordNode recordNode)
    {
        this.recordNode = recordNode;
    }

    /**
     * @see #recordNode
     * @return
     */
    public RecordNode getRecordNode()
    {
        return recordNode;
    }

    /**
     * @see #defaultDate
     * @param defaultDate
     */
    public void setDefaultDate(String defaultDate)
    {
        this.defaultDate = defaultDate;
    }

    /**
     * @see #defaultDate
     * @return
     */
    public String getDefaultDate()
    {
        return defaultDate;
    }

    /**
     * True if the specified columnValue should be considered a default Max date.
     * @param columnValue
     * @return
     */
    private boolean isMaxDate(String columnValue)
    {
        boolean maxDate = false;
        ;

        GroupNode groupNode = getRecordNode().getGroupNode();

        if (isDate() && getDefaultDate().equals(DEFAULTDATE_MAX))
        {
            if (columnValue.equals(groupNode.getMaxDate()))
            {
                maxDate = true;
            }
        }

        return maxDate;
    }

    /**
     * True if the specified columnValue should be considered a default Min date.
     * @param columnValue
     * @return
     */
    private boolean isMinDate(String columnValue)
    {
        boolean minDate = false;

        GroupNode groupNode = getRecordNode().getGroupNode();

        if (isDate() && getDefaultDate().equals(DEFAULTDATE_MIN))
        {
            if (columnValue.equals(groupNode.getMinDate()))
            {
                minDate = true;
            }
        }

        return minDate;
    }

    /**
     * True if the specified columnValue should be considered a default No date (ultimately null).
     * @param columnValue
     * @return
     */
    private boolean isNoDate(String columnValue)
    {
        boolean noDate = false;
        ;

        GroupNode groupNode = getRecordNode().getGroupNode();

        if (isDate() && getDefaultDate().equals(DEFAULTDATE_NO))
        {
            if (columnValue.equals(groupNode.getNoDate()))
            {
                noDate = true;
            }
        }

        return noDate;
    }
}
