package fission.utility;

import com.unidex.xflat.XflatException;
import com.unidex.xflat.XmlConvert;

import edit.common.exceptions.SEGConversionException;

import edit.services.config.ServicesConfig;

import java.io.Reader;
import java.io.Writer;

public class ConversionUtil
{
    /**
     * Transforms a Flat file with fixed length filed values or delmitier seperated field values 
     * in to a XML format as defined in XFlat. Typical format for XFlat is
     * <?xml version='1.0'?>
     * <XFlat Name="employees_schema" Description="Schema for CSV flat file">
     *      <SequenceDef Name="employees" Description="employees flat file">
     *          <RecordDef Name="employee" FieldSep="," RecSep="\n" MaxOccur="0">
     *              <FieldDef Name="ssn" NullAllowed="No" 
     *                        MinFieldLength="9" MaxFieldLength="11"
     *                        DataType="Integer" MinValue="0"
     *                        QuotedValue="Yes"/>
     *              <FieldDef Name="name" NullAllowed="No"
     *                        QuotedValue="Yes"/>
     *              <FieldDef Name="salary" NullAllowed="No"
     *                        DataType="Float" MinValue="0"
     *                        QuotedValue="Yes"/>
     *          </RecordDef>
     *      </SequenceDef>
     *  </XFlat>
     * @param xflatReader
     * @param flatFileReader
     * @param outputXMLWriter
     * @throws SEGConversionException
     */
    public static final void convertFlatFileToXMLFormat(Reader xflatReader, Reader flatFileReader, Writer outputXMLWriter) throws SEGConversionException
    {
        try
        {
            XmlConvert xmlConvert = new XmlConvert(xflatReader, true);
            
            xmlConvert.flatToXml(flatFileReader, outputXMLWriter);
        }
        catch (XflatException e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            throw new SEGConversionException(e.getMessage());
        }
    }
    
    /**
     * Helper method to get directory for conversion files.
     * @return
     */
    public static String getConversionFilesDirectory()
    {
        String directoryPath = ServicesConfig.getConversionData().getDirectory();

        return directoryPath;
    }

}
