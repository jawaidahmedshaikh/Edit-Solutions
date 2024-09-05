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
public class ConfigServicesJdbc implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    private int _poolSize;

    /**
     * keeps track of state for field: _poolSize
    **/
    private boolean _has_poolSize;

    private java.lang.String _poolName;

    private java.lang.String _url;

    private java.lang.String _driver;

    private java.lang.String _username;

    private java.lang.String _password;


      //----------------/
     //- Constructors -/
    //----------------/

    public ConfigServicesJdbc() {
        super();
    } //-- edit.services.config.ConfigServicesJdbc()


      //-----------/
     //- Methods -/
    //-----------/

    /**
    **/
    public java.lang.String getDriver()
    {
        return this._driver;
    } //-- java.lang.String getDriver() 

    /**
    **/
    public java.lang.String getPassword()
    {
        return this._password;
    } //-- java.lang.String getPassword() 

    /**
    **/
    public java.lang.String getPoolName()
    {
        return this._poolName;
    } //-- java.lang.String getPoolName() 

    /**
    **/
    public int getPoolSize()
    {
        return this._poolSize;
    } //-- int getPoolSize() 

    /**
    **/
    public java.lang.String getUrl()
    {
        return this._url;
    } //-- java.lang.String getUrl() 

    /**
    **/
    public java.lang.String getUsername()
    {
        return this._username;
    } //-- java.lang.String getUsername() 

    /**
    **/
    public boolean hasPoolSize()
    {
        return this._has_poolSize;
    } //-- boolean hasPoolSize() 

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
     * @param driver
    **/
    public void setDriver(java.lang.String driver)
    {
        this._driver = driver;
    } //-- void setDriver(java.lang.String) 

    /**
     * 
     * @param password
    **/
    public void setPassword(java.lang.String password)
    {
        this._password = password;
    } //-- void setPassword(java.lang.String) 

    /**
     * 
     * @param poolName
    **/
    public void setPoolName(java.lang.String poolName)
    {
        this._poolName = poolName;
    } //-- void setPoolName(java.lang.String) 

    /**
     * 
     * @param poolSize
    **/
    public void setPoolSize(int poolSize)
    {
        this._poolSize = poolSize;
        this._has_poolSize = true;
    } //-- void setPoolSize(int) 

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
     * @param username
    **/
    public void setUsername(java.lang.String username)
    {
        this._username = username;
    } //-- void setUsername(java.lang.String) 

    /**
     * 
     * @param reader
    **/
    public static edit.services.config.ConfigServicesJdbc unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.services.config.ConfigServicesJdbc) Unmarshaller.unmarshal(edit.services.config.ConfigServicesJdbc.class, reader);
    } //-- edit.services.config.ConfigServicesJdbc unmarshal(java.io.Reader) 

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
