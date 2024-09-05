package conversion;

import java.io.PrintWriter;
import java.util.List;

import org.dom4j.Element;

/**
 * Conversions involve a ConversionTemplate that defines the flat-file to XML conversion rules, and a 
 * flat-file to convert.
 */
public interface ConversionListener
{
    /**
     * The callback method the ConversionTemplate will use for a successful flat-file to Group(ing) convesion.
     * @param conversionTemplate a convenience reference to the ConversionTemplate performing the flat-file to raw XML conversion
     * @param groupElement the flat-file to XML as GroupVO.RecordVO.ColumnVO
     * @see ConversionTemplate
     */
    public void conversionResult(ConversionTemplate conversionTemplate, List<String> groupFlatFileLines, Element groupElement);
    
    /**
     * The callback method the ConversionTemplate 
     * @param conversionTemplate a convenience reference to the ConversionTemplate performing the flat-file to raw XML conversion
     * @param groupFlatFileLines the set of flat-file lines used to create the group(ing) up until the fault occured
     * @param e the Exception that aborted the conversion process
     */
    public void conversionFault(ConversionTemplate conversionTemplate, List<String> groupFlatFileLines, Exception e);

	public void conversionResult(ConversionTemplate conversionTemplate,	String xmlFilePath, PrintWriter out) throws Exception;
}
