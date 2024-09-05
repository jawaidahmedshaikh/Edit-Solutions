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
 * Class EDITReport.
 * 
 * @version $Revision$ $Date$
 */
public class EDITReport extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _reportName
     */
    private java.lang.String _reportName;

    /**
     * Field _localDirectory
     */
    private java.lang.String _localDirectory;

    /**
     * Field _webDirectory
     */
    private java.lang.String _webDirectory;


      //----------------/
     //- Constructors -/
    //----------------/

    public EDITReport() {
        super();
    } //-- edit.common.vo.EDITReport()


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
        
        if (obj instanceof EDITReport) {
        
            EDITReport temp = (EDITReport)obj;
            if (this._reportName != null) {
                if (temp._reportName == null) return false;
                else if (!(this._reportName.equals(temp._reportName))) 
                    return false;
            }
            else if (temp._reportName != null)
                return false;
            if (this._localDirectory != null) {
                if (temp._localDirectory == null) return false;
                else if (!(this._localDirectory.equals(temp._localDirectory))) 
                    return false;
            }
            else if (temp._localDirectory != null)
                return false;
            if (this._webDirectory != null) {
                if (temp._webDirectory == null) return false;
                else if (!(this._webDirectory.equals(temp._webDirectory))) 
                    return false;
            }
            else if (temp._webDirectory != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getLocalDirectoryReturns the value of field
     * 'localDirectory'.
     * 
     * @return the value of field 'localDirectory'.
     */
    public java.lang.String getLocalDirectory()
    {
        return this._localDirectory;
    } //-- java.lang.String getLocalDirectory() 

    /**
     * Method getReportNameReturns the value of field 'reportName'.
     * 
     * @return the value of field 'reportName'.
     */
    public java.lang.String getReportName()
    {
        return this._reportName;
    } //-- java.lang.String getReportName() 

    /**
     * Method getWebDirectoryReturns the value of field
     * 'webDirectory'.
     * 
     * @return the value of field 'webDirectory'.
     */
    public java.lang.String getWebDirectory()
    {
        return this._webDirectory;
    } //-- java.lang.String getWebDirectory() 

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
     * Method setLocalDirectorySets the value of field
     * 'localDirectory'.
     * 
     * @param localDirectory the value of field 'localDirectory'.
     */
    public void setLocalDirectory(java.lang.String localDirectory)
    {
        this._localDirectory = localDirectory;
        
        super.setVoChanged(true);
    } //-- void setLocalDirectory(java.lang.String) 

    /**
     * Method setReportNameSets the value of field 'reportName'.
     * 
     * @param reportName the value of field 'reportName'.
     */
    public void setReportName(java.lang.String reportName)
    {
        this._reportName = reportName;
        
        super.setVoChanged(true);
    } //-- void setReportName(java.lang.String) 

    /**
     * Method setWebDirectorySets the value of field
     * 'webDirectory'.
     * 
     * @param webDirectory the value of field 'webDirectory'.
     */
    public void setWebDirectory(java.lang.String webDirectory)
    {
        this._webDirectory = webDirectory;
        
        super.setVoChanged(true);
    } //-- void setWebDirectory(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.EDITReport unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.EDITReport) Unmarshaller.unmarshal(edit.common.vo.EDITReport.class, reader);
    } //-- edit.common.vo.EDITReport unmarshal(java.io.Reader) 

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
