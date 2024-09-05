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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class EditErrorVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _baseParameter
     */
    private java.lang.String _baseParameter;

    /**
     * Field _baseParameterValue
     */
    private java.lang.String _baseParameterValue;

    /**
     * Field _crossParameter
     */
    private java.lang.String _crossParameter;

    /**
     * Field _crossParameterValue
     */
    private java.lang.String _crossParameterValue;


      //----------------/
     //- Constructors -/
    //----------------/

    public EditErrorVO() {
        super();
    } //-- edit.common.vo.EditErrorVO()


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
        
        if (obj instanceof EditErrorVO) {
        
            EditErrorVO temp = (EditErrorVO)obj;
            if (this._baseParameter != null) {
                if (temp._baseParameter == null) return false;
                else if (!(this._baseParameter.equals(temp._baseParameter))) 
                    return false;
            }
            else if (temp._baseParameter != null)
                return false;
            if (this._baseParameterValue != null) {
                if (temp._baseParameterValue == null) return false;
                else if (!(this._baseParameterValue.equals(temp._baseParameterValue))) 
                    return false;
            }
            else if (temp._baseParameterValue != null)
                return false;
            if (this._crossParameter != null) {
                if (temp._crossParameter == null) return false;
                else if (!(this._crossParameter.equals(temp._crossParameter))) 
                    return false;
            }
            else if (temp._crossParameter != null)
                return false;
            if (this._crossParameterValue != null) {
                if (temp._crossParameterValue == null) return false;
                else if (!(this._crossParameterValue.equals(temp._crossParameterValue))) 
                    return false;
            }
            else if (temp._crossParameterValue != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBaseParameterReturns the value of field
     * 'baseParameter'.
     * 
     * @return the value of field 'baseParameter'.
     */
    public java.lang.String getBaseParameter()
    {
        return this._baseParameter;
    } //-- java.lang.String getBaseParameter() 

    /**
     * Method getBaseParameterValueReturns the value of field
     * 'baseParameterValue'.
     * 
     * @return the value of field 'baseParameterValue'.
     */
    public java.lang.String getBaseParameterValue()
    {
        return this._baseParameterValue;
    } //-- java.lang.String getBaseParameterValue() 

    /**
     * Method getCrossParameterReturns the value of field
     * 'crossParameter'.
     * 
     * @return the value of field 'crossParameter'.
     */
    public java.lang.String getCrossParameter()
    {
        return this._crossParameter;
    } //-- java.lang.String getCrossParameter() 

    /**
     * Method getCrossParameterValueReturns the value of field
     * 'crossParameterValue'.
     * 
     * @return the value of field 'crossParameterValue'.
     */
    public java.lang.String getCrossParameterValue()
    {
        return this._crossParameterValue;
    } //-- java.lang.String getCrossParameterValue() 

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
     * Method setBaseParameterSets the value of field
     * 'baseParameter'.
     * 
     * @param baseParameter the value of field 'baseParameter'.
     */
    public void setBaseParameter(java.lang.String baseParameter)
    {
        this._baseParameter = baseParameter;
        
        super.setVoChanged(true);
    } //-- void setBaseParameter(java.lang.String) 

    /**
     * Method setBaseParameterValueSets the value of field
     * 'baseParameterValue'.
     * 
     * @param baseParameterValue the value of field
     * 'baseParameterValue'.
     */
    public void setBaseParameterValue(java.lang.String baseParameterValue)
    {
        this._baseParameterValue = baseParameterValue;
        
        super.setVoChanged(true);
    } //-- void setBaseParameterValue(java.lang.String) 

    /**
     * Method setCrossParameterSets the value of field
     * 'crossParameter'.
     * 
     * @param crossParameter the value of field 'crossParameter'.
     */
    public void setCrossParameter(java.lang.String crossParameter)
    {
        this._crossParameter = crossParameter;
        
        super.setVoChanged(true);
    } //-- void setCrossParameter(java.lang.String) 

    /**
     * Method setCrossParameterValueSets the value of field
     * 'crossParameterValue'.
     * 
     * @param crossParameterValue the value of field
     * 'crossParameterValue'.
     */
    public void setCrossParameterValue(java.lang.String crossParameterValue)
    {
        this._crossParameterValue = crossParameterValue;
        
        super.setVoChanged(true);
    } //-- void setCrossParameterValue(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.EditErrorVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.EditErrorVO) Unmarshaller.unmarshal(edit.common.vo.EditErrorVO.class, reader);
    } //-- edit.common.vo.EditErrorVO unmarshal(java.io.Reader) 

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
