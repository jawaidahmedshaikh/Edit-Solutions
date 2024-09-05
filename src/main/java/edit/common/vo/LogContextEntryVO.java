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
 * Class LogContextEntryVO.
 * 
 * @version $Revision$ $Date$
 */
public class LogContextEntryVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _entryName
     */
    private java.lang.String _entryName;

    /**
     * Field _entryValue
     */
    private java.lang.String _entryValue;


      //----------------/
     //- Constructors -/
    //----------------/

    public LogContextEntryVO() {
        super();
    } //-- edit.common.vo.LogContextEntryVO()


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
        
        if (obj instanceof LogContextEntryVO) {
        
            LogContextEntryVO temp = (LogContextEntryVO)obj;
            if (this._entryName != null) {
                if (temp._entryName == null) return false;
                else if (!(this._entryName.equals(temp._entryName))) 
                    return false;
            }
            else if (temp._entryName != null)
                return false;
            if (this._entryValue != null) {
                if (temp._entryValue == null) return false;
                else if (!(this._entryValue.equals(temp._entryValue))) 
                    return false;
            }
            else if (temp._entryValue != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getEntryNameReturns the value of field 'entryName'.
     * 
     * @return the value of field 'entryName'.
     */
    public java.lang.String getEntryName()
    {
        return this._entryName;
    } //-- java.lang.String getEntryName() 

    /**
     * Method getEntryValueReturns the value of field 'entryValue'.
     * 
     * @return the value of field 'entryValue'.
     */
    public java.lang.String getEntryValue()
    {
        return this._entryValue;
    } //-- java.lang.String getEntryValue() 

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
     * Method setEntryNameSets the value of field 'entryName'.
     * 
     * @param entryName the value of field 'entryName'.
     */
    public void setEntryName(java.lang.String entryName)
    {
        this._entryName = entryName;
        
        super.setVoChanged(true);
    } //-- void setEntryName(java.lang.String) 

    /**
     * Method setEntryValueSets the value of field 'entryValue'.
     * 
     * @param entryValue the value of field 'entryValue'.
     */
    public void setEntryValue(java.lang.String entryValue)
    {
        this._entryValue = entryValue;
        
        super.setVoChanged(true);
    } //-- void setEntryValue(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.LogContextEntryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.LogContextEntryVO) Unmarshaller.unmarshal(edit.common.vo.LogContextEntryVO.class, reader);
    } //-- edit.common.vo.LogContextEntryVO unmarshal(java.io.Reader) 

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
