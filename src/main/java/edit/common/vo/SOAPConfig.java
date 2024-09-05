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
 * Class SOAPConfig.
 * 
 * @version $Revision$ $Date$
 */
public class SOAPConfig extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _URL
     */
    private java.lang.String _URL;

    /**
     * Field _adapterClass
     */
    private java.lang.String _adapterClass;


      //----------------/
     //- Constructors -/
    //----------------/

    public SOAPConfig() {
        super();
    } //-- edit.common.vo.SOAPConfig()


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
        
        if (obj instanceof SOAPConfig) {
        
            SOAPConfig temp = (SOAPConfig)obj;
            if (this._URL != null) {
                if (temp._URL == null) return false;
                else if (!(this._URL.equals(temp._URL))) 
                    return false;
            }
            else if (temp._URL != null)
                return false;
            if (this._adapterClass != null) {
                if (temp._adapterClass == null) return false;
                else if (!(this._adapterClass.equals(temp._adapterClass))) 
                    return false;
            }
            else if (temp._adapterClass != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAdapterClassReturns the value of field
     * 'adapterClass'.
     * 
     * @return the value of field 'adapterClass'.
     */
    public java.lang.String getAdapterClass()
    {
        return this._adapterClass;
    } //-- java.lang.String getAdapterClass() 

    /**
     * Method getURLReturns the value of field 'URL'.
     * 
     * @return the value of field 'URL'.
     */
    public java.lang.String getURL()
    {
        return this._URL;
    } //-- java.lang.String getURL() 

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
     * Method setAdapterClassSets the value of field
     * 'adapterClass'.
     * 
     * @param adapterClass the value of field 'adapterClass'.
     */
    public void setAdapterClass(java.lang.String adapterClass)
    {
        this._adapterClass = adapterClass;
        
        super.setVoChanged(true);
    } //-- void setAdapterClass(java.lang.String) 

    /**
     * Method setURLSets the value of field 'URL'.
     * 
     * @param URL the value of field 'URL'.
     */
    public void setURL(java.lang.String URL)
    {
        this._URL = URL;
        
        super.setVoChanged(true);
    } //-- void setURL(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.SOAPConfig unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SOAPConfig) Unmarshaller.unmarshal(edit.common.vo.SOAPConfig.class, reader);
    } //-- edit.common.vo.SOAPConfig unmarshal(java.io.Reader) 

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
