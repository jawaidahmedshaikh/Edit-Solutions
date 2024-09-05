package conversion;

import edit.common.exceptions.SEGConversionException;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

import java.util.List;

import org.dom4j.Element;

/**
 * The base class to the set of classes that can convert a Flat file into
 * its XML equivalent based on the mapping rules defined by
 * a NodeA.NodeB.NodeC composition. For example, GroupNode.RecordNode.ColumnNode
 * could be used to map a Flat file into a 
 * <GroupNode>
 *      <RecordNode> // repeating
 *          <ColumnNode></ColumnNode> // repeating
 *      </RecordNode>
 * </GroupNode>
 */
public abstract class ConversionNode
{
    /**
     * The node successfully converted the given flatFileElement.
     */
    public static int CONVERSION_STATUS_SUCCESS = 1;
    
    /**
     * The node could not convert the given flatFileElement because the
     * given element does not belong to the current grouping of lines.
     */
    public static int CONVERSION_STATUS_END_OF_GROUP = 0;
    
    public ConversionNode()
    {
    }
    
    /**
     * Subclasses are to convert their portion of the flat file
     * according to their mapping rules.
     * @param flatFileElement the piece of the flatFileLine that is to be converted by this ConversionNode
     * @param parentElement the Element to which this ConversionNode should attach its result Element to as a child
     * @return the Element form of the converted data
     * @see #CONVERSION_STATUS_SUCCESS
     */
    public abstract void convert(String flatFileElement, Element parentElement) throws SEGConversionException;

    /**
     * Subclasses are to restore their state from the specified Element.
     * This is to be recursive - children of this ConversionNode are to
     * be passed their portion of the document to restore their own state.
     * @param nodeElement
     */
    public abstract void unmarshal(Element nodeElement);
}
