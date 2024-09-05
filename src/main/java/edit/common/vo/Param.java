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
 * Class Param.
 * 
 * @version $Revision$ $Date$
 */
public class Param extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * Field _value
     */
    private java.lang.String _value;

    /**
     * Field _voChanged
     */
    private boolean _voChanged;

    /**
     * keeps track of state for field: _voChanged
     */
    private boolean _has_voChanged;


      //----------------/
     //- Constructors -/
    //----------------/

    public Param() {
        super();
    } //-- edit.common.vo.Param()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteVoChanged
     */
    public void deleteVoChanged()
    {
        this._has_voChanged= false;
    } //-- void deleteVoChanged() 

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
        
        if (obj instanceof Param) {
        
            Param temp = (Param)obj;
            if (this._name != null) {
                if (temp._name == null) return false;
                else if (!(this._name.equals(temp._name))) 
                    return false;
            }
            else if (temp._name != null)
                return false;
            if (this._value != null) {
                if (temp._value == null) return false;
                else if (!(this._value.equals(temp._value))) 
                    return false;
            }
            else if (temp._value != null)
                return false;
            if (this._voChanged != temp._voChanged)
                return false;
            if (this._has_voChanged != temp._has_voChanged)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getNameReturns the value of field 'name'.
     * 
     * @return the value of field 'name'.
     */
    public java.lang.String getName()
    {
        return this._name;
    } //-- java.lang.String getName() 

    /**
     * Method getValueReturns the value of field 'value'.
     * 
     * @return the value of field 'value'.
     */
    public java.lang.String getValue()
    {
        return this._value;
    } //-- java.lang.String getValue() 

    /**
     * Method getVoChangedReturns the value of field 'voChanged'.
     * 
     * @return the value of field 'voChanged'.
     */
    public boolean getVoChanged()
    {
        return this._voChanged;
    } //-- boolean getVoChanged() 

    /**
     * Method hasVoChanged
     */
    public boolean hasVoChanged()
    {
        return this._has_voChanged;
    } //-- boolean hasVoChanged() 

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
     * Method setNameSets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.String name)
    {
        this._name = name;
        
        this._voChanged = true;
    } //-- void setName(java.lang.String) 

    /**
     * Method setValueSets the value of field 'value'.
     * 
     * @param value the value of field 'value'.
     */
    public void setValue(java.lang.String value)
    {
        this._value = value;
        
        this._voChanged = true;
    } //-- void setValue(java.lang.String) 

    /**
     * Method setVoChangedSets the value of field 'voChanged'.
     * 
     * @param voChanged the value of field 'voChanged'.
     */
    public void setVoChanged(boolean voChanged)
    {
        this._voChanged = voChanged;
        this._has_voChanged = true;
    } //-- void setVoChanged(boolean) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.Param unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.Param) Unmarshaller.unmarshal(edit.common.vo.Param.class, reader);
    } //-- edit.common.vo.Param unmarshal(java.io.Reader) 

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
