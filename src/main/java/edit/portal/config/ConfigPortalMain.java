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
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.*;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.DocumentHandler;

/**
 * Comment describing your root element
 * @version $Revision$ $Date$
**/
public class ConfigPortalMain implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    private java.util.Vector _configEndpointList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ConfigPortalMain() {
        super();
        _configEndpointList = new Vector();
    } //-- edit.portal.config.ConfigPortalMain()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * @param vConfigEndpoint
    **/
    public void addConfigEndpoint(edit.portal.config.ConfigEndpoint vConfigEndpoint)
        throws java.lang.IndexOutOfBoundsException
    {
        _configEndpointList.addElement(vConfigEndpoint);
    } //-- void addConfigEndpoint(edit.portal.config.ConfigEndpoint) 

    /**
     * 
     * @param index
     * @param vConfigEndpoint
    **/
    public void addConfigEndpoint(int index, edit.portal.config.ConfigEndpoint vConfigEndpoint)
        throws java.lang.IndexOutOfBoundsException
    {
        _configEndpointList.insertElementAt(vConfigEndpoint, index);
    } //-- void addConfigEndpoint(int, edit.portal.config.ConfigEndpoint) 

    /**
    **/
    public java.util.Enumeration enumerateConfigEndpoint()
    {
        return _configEndpointList.elements();
    } //-- java.util.Enumeration enumerateConfigEndpoint() 

    /**
     * 
     * @param index
    **/
    public edit.portal.config.ConfigEndpoint getConfigEndpoint(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _configEndpointList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.portal.config.ConfigEndpoint) _configEndpointList.elementAt(index);
    } //-- edit.portal.config.ConfigEndpoint getConfigEndpoint(int) 

    /**
    **/
    public edit.portal.config.ConfigEndpoint[] getConfigEndpoint()
    {
        int size = _configEndpointList.size();
        edit.portal.config.ConfigEndpoint[] mArray = new edit.portal.config.ConfigEndpoint[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (ConfigEndpoint) _configEndpointList.elementAt(index);
        }
        return mArray;
    } //-- edit.portal.config.ConfigEndpoint[] getConfigEndpoint() 

    /**
    **/
    public int getConfigEndpointCount()
    {
        return _configEndpointList.size();
    } //-- int getConfigEndpointCount() 

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
    **/
    public void removeAllConfigEndpoint()
    {
        _configEndpointList.removeAllElements();
    } //-- void removeAllConfigEndpoint() 

    /**
     * 
     * @param index
    **/
    public edit.portal.config.ConfigEndpoint removeConfigEndpoint(int index)
    {
        Object obj = _configEndpointList.elementAt(index);
        _configEndpointList.removeElementAt(index);
        return (edit.portal.config.ConfigEndpoint) obj;
    } //-- edit.portal.config.ConfigEndpoint removeConfigEndpoint(int) 

    /**
     * 
     * @param index
     * @param vConfigEndpoint
    **/
    public void setConfigEndpoint(int index, edit.portal.config.ConfigEndpoint vConfigEndpoint)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _configEndpointList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _configEndpointList.setElementAt(vConfigEndpoint, index);
    } //-- void setConfigEndpoint(int, edit.portal.config.ConfigEndpoint) 

    /**
     * 
     * @param configEndpointArray
    **/
    public void setConfigEndpoint(edit.portal.config.ConfigEndpoint[] configEndpointArray)
    {
        //-- copy array
        _configEndpointList.removeAllElements();
        for (int i = 0; i < configEndpointArray.length; i++) {
            _configEndpointList.addElement(configEndpointArray[i]);
        }
    } //-- void setConfigEndpoint(edit.portal.config.ConfigEndpoint) 

    /**
     * 
     * @param reader
    **/
    public static edit.portal.config.ConfigPortalMain unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.portal.config.ConfigPortalMain) Unmarshaller.unmarshal(edit.portal.config.ConfigPortalMain.class, reader);
    } //-- edit.portal.config.ConfigPortalMain unmarshal(java.io.Reader) 

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
