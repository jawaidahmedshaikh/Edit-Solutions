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
 * Class OFACConfig.
 * 
 * @version $Revision$ $Date$
 */
public class OFACConfig extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _OFACCheck
     */
    private java.lang.String _OFACCheck;

    /**
     * Field _URL
     */
    private java.lang.String _URL;

    /**
     * Field _OFACMethod
     */
    private java.lang.String _OFACMethod;

    /**
     * Field _OFACConnectAttempts
     */
    private int _OFACConnectAttempts;

    /**
     * keeps track of state for field: _OFACConnectAttempts
     */
    private boolean _has_OFACConnectAttempts;

    /**
     * Field _OFACConnectIntervalTimeInMSecs
     */
    private long _OFACConnectIntervalTimeInMSecs;

    /**
     * keeps track of state for field:
     * _OFACConnectIntervalTimeInMSecs
     */
    private boolean _has_OFACConnectIntervalTimeInMSecs;


      //----------------/
     //- Constructors -/
    //----------------/

    public OFACConfig() {
        super();
    } //-- edit.common.vo.OFACConfig()


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
        
        if (obj instanceof OFACConfig) {
        
            OFACConfig temp = (OFACConfig)obj;
            if (this._OFACCheck != null) {
                if (temp._OFACCheck == null) return false;
                else if (!(this._OFACCheck.equals(temp._OFACCheck))) 
                    return false;
            }
            else if (temp._OFACCheck != null)
                return false;
            if (this._URL != null) {
                if (temp._URL == null) return false;
                else if (!(this._URL.equals(temp._URL))) 
                    return false;
            }
            else if (temp._URL != null)
                return false;
            if (this._OFACMethod != null) {
                if (temp._OFACMethod == null) return false;
                else if (!(this._OFACMethod.equals(temp._OFACMethod))) 
                    return false;
            }
            else if (temp._OFACMethod != null)
                return false;
            if (this._OFACConnectAttempts != temp._OFACConnectAttempts)
                return false;
            if (this._has_OFACConnectAttempts != temp._has_OFACConnectAttempts)
                return false;
            if (this._OFACConnectIntervalTimeInMSecs != temp._OFACConnectIntervalTimeInMSecs)
                return false;
            if (this._has_OFACConnectIntervalTimeInMSecs != temp._has_OFACConnectIntervalTimeInMSecs)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getOFACCheckReturns the value of field 'OFACCheck'.
     * 
     * @return the value of field 'OFACCheck'.
     */
    public java.lang.String getOFACCheck()
    {
        return this._OFACCheck;
    } //-- java.lang.String getOFACCheck() 

    /**
     * Method getOFACConnectAttemptsReturns the value of field
     * 'OFACConnectAttempts'.
     * 
     * @return the value of field 'OFACConnectAttempts'.
     */
    public int getOFACConnectAttempts()
    {
        return this._OFACConnectAttempts;
    } //-- int getOFACConnectAttempts() 

    /**
     * Method getOFACConnectIntervalTimeInMSecsReturns the value of
     * field 'OFACConnectIntervalTimeInMSecs'.
     * 
     * @return the value of field 'OFACConnectIntervalTimeInMSecs'.
     */
    public long getOFACConnectIntervalTimeInMSecs()
    {
        return this._OFACConnectIntervalTimeInMSecs;
    } //-- long getOFACConnectIntervalTimeInMSecs() 

    /**
     * Method getOFACMethodReturns the value of field 'OFACMethod'.
     * 
     * @return the value of field 'OFACMethod'.
     */
    public java.lang.String getOFACMethod()
    {
        return this._OFACMethod;
    } //-- java.lang.String getOFACMethod() 

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
     * Method hasOFACConnectAttempts
     */
    public boolean hasOFACConnectAttempts()
    {
        return this._has_OFACConnectAttempts;
    } //-- boolean hasOFACConnectAttempts() 

    /**
     * Method hasOFACConnectIntervalTimeInMSecs
     */
    public boolean hasOFACConnectIntervalTimeInMSecs()
    {
        return this._has_OFACConnectIntervalTimeInMSecs;
    } //-- boolean hasOFACConnectIntervalTimeInMSecs() 

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
     * Method setOFACCheckSets the value of field 'OFACCheck'.
     * 
     * @param OFACCheck the value of field 'OFACCheck'.
     */
    public void setOFACCheck(java.lang.String OFACCheck)
    {
        this._OFACCheck = OFACCheck;
        
        super.setVoChanged(true);
    } //-- void setOFACCheck(java.lang.String) 

    /**
     * Method setOFACConnectAttemptsSets the value of field
     * 'OFACConnectAttempts'.
     * 
     * @param OFACConnectAttempts the value of field
     * 'OFACConnectAttempts'.
     */
    public void setOFACConnectAttempts(int OFACConnectAttempts)
    {
        this._OFACConnectAttempts = OFACConnectAttempts;
        
        super.setVoChanged(true);
        this._has_OFACConnectAttempts = true;
    } //-- void setOFACConnectAttempts(int) 

    /**
     * Method setOFACConnectIntervalTimeInMSecsSets the value of
     * field 'OFACConnectIntervalTimeInMSecs'.
     * 
     * @param OFACConnectIntervalTimeInMSecs the value of field
     * 'OFACConnectIntervalTimeInMSecs'.
     */
    public void setOFACConnectIntervalTimeInMSecs(long OFACConnectIntervalTimeInMSecs)
    {
        this._OFACConnectIntervalTimeInMSecs = OFACConnectIntervalTimeInMSecs;
        
        super.setVoChanged(true);
        this._has_OFACConnectIntervalTimeInMSecs = true;
    } //-- void setOFACConnectIntervalTimeInMSecs(long) 

    /**
     * Method setOFACMethodSets the value of field 'OFACMethod'.
     * 
     * @param OFACMethod the value of field 'OFACMethod'.
     */
    public void setOFACMethod(java.lang.String OFACMethod)
    {
        this._OFACMethod = OFACMethod;
        
        super.setVoChanged(true);
    } //-- void setOFACMethod(java.lang.String) 

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
    public static edit.common.vo.OFACConfig unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.OFACConfig) Unmarshaller.unmarshal(edit.common.vo.OFACConfig.class, reader);
    } //-- edit.common.vo.OFACConfig unmarshal(java.io.Reader) 

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
