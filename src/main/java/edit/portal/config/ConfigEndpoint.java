/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.9.3</a>, using an
 * XML Schema.
 * $Id$
 */

package edit.portal.config;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import org.exolab.castor.xml.*;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.DocumentHandler;

/**
 * 
 * @version $Revision$ $Date$
**/
public class ConfigEndpoint implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    private java.lang.String _url;

    private java.lang.String _type;

    private java.lang.String _urn;

    private java.lang.String _location;


      //----------------/
     //- Constructors -/
    //----------------/

    public ConfigEndpoint() {
        super();
    } //-- edit.portal.config.ConfigEndpoint()


      //-----------/
     //- Methods -/
    //-----------/

    /**
    **/
    public java.lang.String getLocation()
    {
        return this._location;
    } //-- java.lang.String getLocation() 

    /**
    **/
    public java.lang.String getType()
    {
        return this._type;
    } //-- java.lang.String getType() 

    /**
    **/
    public java.lang.String getUrl()
    {
        return this._url;
    } //-- java.lang.String getUrl() 

    /**
    **/
    public java.lang.String getUrn()
    {
        return this._urn;
    } //-- java.lang.String getUrn() 

    /**
    **/
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
     * 
     * @param out
    **/
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * 
     * @param handler
    **/
    public void marshal(org.xml.sax.DocumentHandler handler)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.DocumentHandler) 

    /**
     * 
     * @param location
    **/
    public void setLocation(java.lang.String location)
    {
        this._location = location;
    } //-- void setLocation(java.lang.String) 

    /**
     * 
     * @param type
    **/
    public void setType(java.lang.String type)
    {
        this._type = type;
    } //-- void setType(java.lang.String) 

    /**
     * 
     * @param url
    **/
    public void setUrl(java.lang.String url)
    {
        this._url = url;
    } //-- void setUrl(java.lang.String) 

    /**
     * 
     * @param urn
    **/
    public void setUrn(java.lang.String urn)
    {
        this._urn = urn;
    } //-- void setUrn(java.lang.String) 

    /**
     * 
     * @param reader
    **/
    public static edit.portal.config.ConfigEndpoint unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.portal.config.ConfigEndpoint) Unmarshaller.unmarshal(edit.portal.config.ConfigEndpoint.class, reader);
    } //-- edit.portal.config.ConfigEndpoint unmarshal(java.io.Reader) 

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
