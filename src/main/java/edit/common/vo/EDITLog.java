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
 * Class EDITLog.
 * 
 * @version $Revision$ $Date$
 */
public class EDITLog extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _logName
     */
    private java.lang.String _logName;

    /**
     * Field _file
     */
    private java.lang.String _file;


      //----------------/
     //- Constructors -/
    //----------------/

    public EDITLog() {
        super();
    } //-- edit.common.vo.EDITLog()


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
        
        if (obj instanceof EDITLog) {
        
            EDITLog temp = (EDITLog)obj;
            if (this._logName != null) {
                if (temp._logName == null) return false;
                else if (!(this._logName.equals(temp._logName))) 
                    return false;
            }
            else if (temp._logName != null)
                return false;
            if (this._file != null) {
                if (temp._file == null) return false;
                else if (!(this._file.equals(temp._file))) 
                    return false;
            }
            else if (temp._file != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getFileReturns the value of field 'file'.
     * 
     * @return the value of field 'file'.
     */
    public java.lang.String getFile()
    {
        return this._file;
    } //-- java.lang.String getFile() 

    /**
     * Method getLogNameReturns the value of field 'logName'.
     * 
     * @return the value of field 'logName'.
     */
    public java.lang.String getLogName()
    {
        return this._logName;
    } //-- java.lang.String getLogName() 

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
     * Method setFileSets the value of field 'file'.
     * 
     * @param file the value of field 'file'.
     */
    public void setFile(java.lang.String file)
    {
        this._file = file;
        
        super.setVoChanged(true);
    } //-- void setFile(java.lang.String) 

    /**
     * Method setLogNameSets the value of field 'logName'.
     * 
     * @param logName the value of field 'logName'.
     */
    public void setLogName(java.lang.String logName)
    {
        this._logName = logName;
        
        super.setVoChanged(true);
    } //-- void setLogName(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.EDITLog unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.EDITLog) Unmarshaller.unmarshal(edit.common.vo.EDITLog.class, reader);
    } //-- edit.common.vo.EDITLog unmarshal(java.io.Reader) 

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
