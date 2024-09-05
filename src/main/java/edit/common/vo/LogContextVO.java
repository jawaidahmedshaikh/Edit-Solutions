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
 * Class LogContextVO.
 * 
 * @version $Revision$ $Date$
 */
public class LogContextVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _contextName
     */
    private java.lang.String _contextName;

    /**
     * Field _contextValue
     */
    private java.lang.String _contextValue;


      //----------------/
     //- Constructors -/
    //----------------/

    public LogContextVO() {
        super();
    } //-- edit.common.vo.LogContextVO()


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
        
        if (obj instanceof LogContextVO) {
        
            LogContextVO temp = (LogContextVO)obj;
            if (this._contextName != null) {
                if (temp._contextName == null) return false;
                else if (!(this._contextName.equals(temp._contextName))) 
                    return false;
            }
            else if (temp._contextName != null)
                return false;
            if (this._contextValue != null) {
                if (temp._contextValue == null) return false;
                else if (!(this._contextValue.equals(temp._contextValue))) 
                    return false;
            }
            else if (temp._contextValue != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getContextNameReturns the value of field
     * 'contextName'.
     * 
     * @return the value of field 'contextName'.
     */
    public java.lang.String getContextName()
    {
        return this._contextName;
    } //-- java.lang.String getContextName() 

    /**
     * Method getContextValueReturns the value of field
     * 'contextValue'.
     * 
     * @return the value of field 'contextValue'.
     */
    public java.lang.String getContextValue()
    {
        return this._contextValue;
    } //-- java.lang.String getContextValue() 

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
     * Method setContextNameSets the value of field 'contextName'.
     * 
     * @param contextName the value of field 'contextName'.
     */
    public void setContextName(java.lang.String contextName)
    {
        this._contextName = contextName;
        
        super.setVoChanged(true);
    } //-- void setContextName(java.lang.String) 

    /**
     * Method setContextValueSets the value of field
     * 'contextValue'.
     * 
     * @param contextValue the value of field 'contextValue'.
     */
    public void setContextValue(java.lang.String contextValue)
    {
        this._contextValue = contextValue;
        
        super.setVoChanged(true);
    } //-- void setContextValue(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.LogContextVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.LogContextVO) Unmarshaller.unmarshal(edit.common.vo.LogContextVO.class, reader);
    } //-- edit.common.vo.LogContextVO unmarshal(java.io.Reader) 

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
