/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.9.3</a>, using an
 * XML Schema.
 * $Id$
 */

package edit.services.config;

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
public class ConfigServicesWebservice implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    private java.lang.String _urn;

    private java.lang.String _object;

    private java.lang.String _type;

    private java.lang.String _scope;


      //----------------/
     //- Constructors -/
    //----------------/

    public ConfigServicesWebservice() {
        super();
    } //-- edit.services.config.ConfigServicesWebservice()


      //-----------/
     //- Methods -/
    //-----------/

    /**
    **/
    public java.lang.String getObject()
    {
        return this._object;
    } //-- java.lang.String getObject() 

    /**
    **/
    public java.lang.String getScope()
    {
        return this._scope;
    } //-- java.lang.String getScope() 

    /**
    **/
    public java.lang.String getType()
    {
        return this._type;
    } //-- java.lang.String getType() 

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
     * @param object
    **/
    public void setObject(java.lang.String object)
    {
        this._object = object;
    } //-- void setObject(java.lang.String) 

    /**
     * 
     * @param scope
    **/
    public void setScope(java.lang.String scope)
    {
        this._scope = scope;
    } //-- void setScope(java.lang.String) 

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
    public static edit.services.config.ConfigServicesWebservice unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.services.config.ConfigServicesWebservice) Unmarshaller.unmarshal(edit.services.config.ConfigServicesWebservice.class, reader);
    } //-- edit.services.config.ConfigServicesWebservice unmarshal(java.io.Reader) 

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
