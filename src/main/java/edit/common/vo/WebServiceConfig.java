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
 * Class WebServiceConfig.
 * 
 * @version $Revision$ $Date$
 */
public class WebServiceConfig extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _host
     */
    private java.lang.String _host;

    /**
     * Field _port
     */
    private int _port;

    /**
     * keeps track of state for field: _port
     */
    private boolean _has_port;


      //----------------/
     //- Constructors -/
    //----------------/

    public WebServiceConfig() {
        super();
    } //-- edit.common.vo.WebServiceConfig()


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
        
        if (obj instanceof WebServiceConfig) {
        
            WebServiceConfig temp = (WebServiceConfig)obj;
            if (this._host != null) {
                if (temp._host == null) return false;
                else if (!(this._host.equals(temp._host))) 
                    return false;
            }
            else if (temp._host != null)
                return false;
            if (this._port != temp._port)
                return false;
            if (this._has_port != temp._has_port)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getHostReturns the value of field 'host'.
     * 
     * @return the value of field 'host'.
     */
    public java.lang.String getHost()
    {
        return this._host;
    } //-- java.lang.String getHost() 

    /**
     * Method getPortReturns the value of field 'port'.
     * 
     * @return the value of field 'port'.
     */
    public int getPort()
    {
        return this._port;
    } //-- int getPort() 

    /**
     * Method hasPort
     */
    public boolean hasPort()
    {
        return this._has_port;
    } //-- boolean hasPort() 

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
     * Method setHostSets the value of field 'host'.
     * 
     * @param host the value of field 'host'.
     */
    public void setHost(java.lang.String host)
    {
        this._host = host;
        
        super.setVoChanged(true);
    } //-- void setHost(java.lang.String) 

    /**
     * Method setPortSets the value of field 'port'.
     * 
     * @param port the value of field 'port'.
     */
    public void setPort(int port)
    {
        this._port = port;
        
        super.setVoChanged(true);
        this._has_port = true;
    } //-- void setPort(int) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.WebServiceConfig unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.WebServiceConfig) Unmarshaller.unmarshal(edit.common.vo.WebServiceConfig.class, reader);
    } //-- edit.common.vo.WebServiceConfig unmarshal(java.io.Reader) 

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
