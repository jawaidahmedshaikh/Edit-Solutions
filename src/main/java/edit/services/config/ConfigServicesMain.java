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
public class ConfigServicesMain implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    private java.util.Vector _configServicesJdbcList;

    private java.util.Vector _configServicesWebserviceList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ConfigServicesMain() {
        super();
        _configServicesJdbcList = new Vector();
        _configServicesWebserviceList = new Vector();
    } //-- edit.services.config.ConfigServicesMain()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * @param vConfigServicesJdbc
    **/
    public void addConfigServicesJdbc(edit.services.config.ConfigServicesJdbc vConfigServicesJdbc)
        throws java.lang.IndexOutOfBoundsException
    {
        _configServicesJdbcList.addElement(vConfigServicesJdbc);
    } //-- void addConfigServicesJdbc(edit.services.config.ConfigServicesJdbc) 

    /**
     * 
     * @param index
     * @param vConfigServicesJdbc
    **/
    public void addConfigServicesJdbc(int index, edit.services.config.ConfigServicesJdbc vConfigServicesJdbc)
        throws java.lang.IndexOutOfBoundsException
    {
        _configServicesJdbcList.insertElementAt(vConfigServicesJdbc, index);
    } //-- void addConfigServicesJdbc(int, edit.services.config.ConfigServicesJdbc) 

    /**
     * 
     * @param vConfigServicesWebservice
    **/
    public void addConfigServicesWebservice(edit.services.config.ConfigServicesWebservice vConfigServicesWebservice)
        throws java.lang.IndexOutOfBoundsException
    {
        _configServicesWebserviceList.addElement(vConfigServicesWebservice);
    } //-- void addConfigServicesWebservice(edit.services.config.ConfigServicesWebservice) 

    /**
     * 
     * @param index
     * @param vConfigServicesWebservice
    **/
    public void addConfigServicesWebservice(int index, edit.services.config.ConfigServicesWebservice vConfigServicesWebservice)
        throws java.lang.IndexOutOfBoundsException
    {
        _configServicesWebserviceList.insertElementAt(vConfigServicesWebservice, index);
    } //-- void addConfigServicesWebservice(int, edit.services.config.ConfigServicesWebservice) 

    /**
    **/
    public java.util.Enumeration enumerateConfigServicesJdbc()
    {
        return _configServicesJdbcList.elements();
    } //-- java.util.Enumeration enumerateConfigServicesJdbc() 

    /**
    **/
    public java.util.Enumeration enumerateConfigServicesWebservice()
    {
        return _configServicesWebserviceList.elements();
    } //-- java.util.Enumeration enumerateConfigServicesWebservice() 

    /**
     * 
     * @param index
    **/
    public edit.services.config.ConfigServicesJdbc getConfigServicesJdbc(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _configServicesJdbcList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.services.config.ConfigServicesJdbc) _configServicesJdbcList.elementAt(index);
    } //-- edit.services.config.ConfigServicesJdbc getConfigServicesJdbc(int) 

    /**
    **/
    public edit.services.config.ConfigServicesJdbc[] getConfigServicesJdbc()
    {
        int size = _configServicesJdbcList.size();
        edit.services.config.ConfigServicesJdbc[] mArray = new edit.services.config.ConfigServicesJdbc[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (ConfigServicesJdbc) _configServicesJdbcList.elementAt(index);
        }
        return mArray;
    } //-- edit.services.config.ConfigServicesJdbc[] getConfigServicesJdbc() 

    /**
    **/
    public int getConfigServicesJdbcCount()
    {
        return _configServicesJdbcList.size();
    } //-- int getConfigServicesJdbcCount() 

    /**
     * 
     * @param index
    **/
    public edit.services.config.ConfigServicesWebservice getConfigServicesWebservice(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _configServicesWebserviceList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.services.config.ConfigServicesWebservice) _configServicesWebserviceList.elementAt(index);
    } //-- edit.services.config.ConfigServicesWebservice getConfigServicesWebservice(int) 

    /**
    **/
    public edit.services.config.ConfigServicesWebservice[] getConfigServicesWebservice()
    {
        int size = _configServicesWebserviceList.size();
        edit.services.config.ConfigServicesWebservice[] mArray = new edit.services.config.ConfigServicesWebservice[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (ConfigServicesWebservice) _configServicesWebserviceList.elementAt(index);
        }
        return mArray;
    } //-- edit.services.config.ConfigServicesWebservice[] getConfigServicesWebservice() 

    /**
    **/
    public int getConfigServicesWebserviceCount()
    {
        return _configServicesWebserviceList.size();
    } //-- int getConfigServicesWebserviceCount() 

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
    public void removeAllConfigServicesJdbc()
    {
        _configServicesJdbcList.removeAllElements();
    } //-- void removeAllConfigServicesJdbc() 

    /**
    **/
    public void removeAllConfigServicesWebservice()
    {
        _configServicesWebserviceList.removeAllElements();
    } //-- void removeAllConfigServicesWebservice() 

    /**
     * 
     * @param index
    **/
    public edit.services.config.ConfigServicesJdbc removeConfigServicesJdbc(int index)
    {
        Object obj = _configServicesJdbcList.elementAt(index);
        _configServicesJdbcList.removeElementAt(index);
        return (edit.services.config.ConfigServicesJdbc) obj;
    } //-- edit.services.config.ConfigServicesJdbc removeConfigServicesJdbc(int) 

    /**
     * 
     * @param index
    **/
    public edit.services.config.ConfigServicesWebservice removeConfigServicesWebservice(int index)
    {
        Object obj = _configServicesWebserviceList.elementAt(index);
        _configServicesWebserviceList.removeElementAt(index);
        return (edit.services.config.ConfigServicesWebservice) obj;
    } //-- edit.services.config.ConfigServicesWebservice removeConfigServicesWebservice(int) 

    /**
     * 
     * @param index
     * @param vConfigServicesJdbc
    **/
    public void setConfigServicesJdbc(int index, edit.services.config.ConfigServicesJdbc vConfigServicesJdbc)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _configServicesJdbcList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _configServicesJdbcList.setElementAt(vConfigServicesJdbc, index);
    } //-- void setConfigServicesJdbc(int, edit.services.config.ConfigServicesJdbc) 

    /**
     * 
     * @param configServicesJdbcArray
    **/
    public void setConfigServicesJdbc(edit.services.config.ConfigServicesJdbc[] configServicesJdbcArray)
    {
        //-- copy array
        _configServicesJdbcList.removeAllElements();
        for (int i = 0; i < configServicesJdbcArray.length; i++) {
            _configServicesJdbcList.addElement(configServicesJdbcArray[i]);
        }
    } //-- void setConfigServicesJdbc(edit.services.config.ConfigServicesJdbc) 

    /**
     * 
     * @param index
     * @param vConfigServicesWebservice
    **/
    public void setConfigServicesWebservice(int index, edit.services.config.ConfigServicesWebservice vConfigServicesWebservice)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _configServicesWebserviceList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _configServicesWebserviceList.setElementAt(vConfigServicesWebservice, index);
    } //-- void setConfigServicesWebservice(int, edit.services.config.ConfigServicesWebservice) 

    /**
     * 
     * @param configServicesWebserviceArray
    **/
    public void setConfigServicesWebservice(edit.services.config.ConfigServicesWebservice[] configServicesWebserviceArray)
    {
        //-- copy array
        _configServicesWebserviceList.removeAllElements();
        for (int i = 0; i < configServicesWebserviceArray.length; i++) {
            _configServicesWebserviceList.addElement(configServicesWebserviceArray[i]);
        }
    } //-- void setConfigServicesWebservice(edit.services.config.ConfigServicesWebservice) 

    /**
     * 
     * @param reader
    **/
    public static edit.services.config.ConfigServicesMain unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.services.config.ConfigServicesMain) Unmarshaller.unmarshal(edit.services.config.ConfigServicesMain.class, reader);
    } //-- edit.services.config.ConfigServicesMain unmarshal(java.io.Reader) 

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
