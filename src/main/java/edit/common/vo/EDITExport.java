/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.4.3</a>, using an XML
 * Schema.
 * $Id$
 */

package edit.common.vo;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class EDITExport.
 * 
 * @version $Revision$ $Date$
 */
public class EDITExport extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _exportName
     */
    private java.lang.String _exportName;

    /**
     * Field _directory
     */
    private java.lang.String _directory;


      //----------------/
     //- Constructors -/
    //----------------/

    public EDITExport() {
        super();
    } //-- edit.common.vo.EDITExport()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Note: hashCode() has not been overriden
     * 
     * @param obj
     */
    public boolean equals(java.lang.Object obj)
    {
        if ( this == obj )
            return true;
        
        if (super.equals(obj)==false)
            return false;
        
        if (obj instanceof EDITExport) {
        
            EDITExport temp = (EDITExport)obj;
            if (this._exportName != null) {
                if (temp._exportName == null) return false;
                else if (!(this._exportName.equals(temp._exportName))) 
                    return false;
            }
            else if (temp._exportName != null)
                return false;
            if (this._directory != null) {
                if (temp._directory == null) return false;
                else if (!(this._directory.equals(temp._directory))) 
                    return false;
            }
            else if (temp._directory != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getDirectoryReturns the value of field 'directory'.
     * 
     * @return the value of field 'directory'.
     */
    public java.lang.String getDirectory()
    {
        return this._directory;
    } //-- java.lang.String getDirectory() 

    /**
     * Method getExportNameReturns the value of field 'exportName'.
     * 
     * @return the value of field 'exportName'.
     */
    public java.lang.String getExportName()
    {
        return this._exportName;
    } //-- java.lang.String getExportName() 

    /**
     * Method isValid
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * Method marshal
     * 
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * Method marshal
     * 
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Method setDirectorySets the value of field 'directory'.
     * 
     * @param directory the value of field 'directory'.
     */
    public void setDirectory(java.lang.String directory)
    {
        this._directory = directory;
        
        super.setVoChanged(true);
    } //-- void setDirectory(java.lang.String) 

    /**
     * Method setExportNameSets the value of field 'exportName'.
     * 
     * @param exportName the value of field 'exportName'.
     */
    public void setExportName(java.lang.String exportName)
    {
        this._exportName = exportName;
        
        super.setVoChanged(true);
    } //-- void setExportName(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.EDITExport unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.EDITExport) Unmarshaller.unmarshal(edit.common.vo.EDITExport.class, reader);
    } //-- edit.common.vo.EDITExport unmarshal(java.io.Reader) 

    /**
     * Method validate
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
