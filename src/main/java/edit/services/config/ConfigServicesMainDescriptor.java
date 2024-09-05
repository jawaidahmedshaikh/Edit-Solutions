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

import org.exolab.castor.mapping.AccessMode;
import org.exolab.castor.mapping.ClassDescriptor;
import org.exolab.castor.mapping.FieldDescriptor;
import org.exolab.castor.xml.*;
import org.exolab.castor.xml.FieldValidator;
import org.exolab.castor.xml.TypeValidator;
import org.exolab.castor.xml.XMLFieldDescriptor;
import org.exolab.castor.xml.handlers.*;
import org.exolab.castor.xml.util.XMLFieldDescriptorImpl;
import org.exolab.castor.xml.validators.*;

/**
 * 
 * @version $Revision$ $Date$
**/
public class ConfigServicesMainDescriptor extends org.exolab.castor.xml.util.XMLClassDescriptorImpl {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    private java.lang.String nsPrefix;

    private java.lang.String nsURI;

    private java.lang.String xmlName;

    private org.exolab.castor.xml.XMLFieldDescriptor identity;


      //----------------/
     //- Constructors -/
    //----------------/

    public ConfigServicesMainDescriptor() {
        super();
        nsURI = "http://www.seg.com/config-services";
        xmlName = "config-services-main";
        XMLFieldDescriptorImpl  desc           = null;
        XMLFieldHandler         handler        = null;
        FieldValidator          fieldValidator = null;
        //-- initialize attribute descriptors
        
        //-- initialize element descriptors
        
        //-- _configServicesJdbcList
        desc = new XMLFieldDescriptorImpl(edit.services.config.ConfigServicesJdbc.class, "_configServicesJdbcList", "config-services-jdbc", NodeType.Element);
        handler = (new XMLFieldHandler() {
            public Object getValue( Object object ) 
                throws IllegalStateException
            {
                ConfigServicesMain target = (ConfigServicesMain) object;
                return target.getConfigServicesJdbc();
            }
            public void setValue( Object object, Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ConfigServicesMain target = (ConfigServicesMain) object;
                    target.addConfigServicesJdbc( (edit.services.config.ConfigServicesJdbc) value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public Object newInstance( Object parent ) {
                return new edit.services.config.ConfigServicesJdbc();
            }
        } );
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://www.seg.com/config-services");
        desc.setMultivalued(true);
        addFieldDescriptor(desc);
        
        //-- validation code for: _configServicesJdbcList
        fieldValidator = new FieldValidator();
        desc.setValidator(fieldValidator);
        
        //-- _configServicesWebserviceList
        desc = new XMLFieldDescriptorImpl(edit.services.config.ConfigServicesWebservice.class, "_configServicesWebserviceList", "config-services-webservice", NodeType.Element);
        handler = (new XMLFieldHandler() {
            public Object getValue( Object object ) 
                throws IllegalStateException
            {
                ConfigServicesMain target = (ConfigServicesMain) object;
                return target.getConfigServicesWebservice();
            }
            public void setValue( Object object, Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                    ConfigServicesMain target = (ConfigServicesMain) object;
                    target.addConfigServicesWebservice( (edit.services.config.ConfigServicesWebservice) value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public Object newInstance( Object parent ) {
                return new edit.services.config.ConfigServicesWebservice();
            }
        } );
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://www.seg.com/config-services");
        desc.setMultivalued(true);
        addFieldDescriptor(desc);
        
        //-- validation code for: _configServicesWebserviceList
        fieldValidator = new FieldValidator();
        desc.setValidator(fieldValidator);
        
    } //-- edit.services.config.ConfigServicesMainDescriptor()


      //-----------/
     //- Methods -/
    //-----------/

    /**
    **/
    public org.exolab.castor.mapping.AccessMode getAccessMode()
    {
        return null;
    } //-- org.exolab.castor.mapping.AccessMode getAccessMode() 

    /**
    **/
    public org.exolab.castor.mapping.ClassDescriptor getExtends()
    {
        return null;
    } //-- org.exolab.castor.mapping.ClassDescriptor getExtends() 

    /**
    **/
    public org.exolab.castor.mapping.FieldDescriptor getIdentity()
    {
        return identity;
    } //-- org.exolab.castor.mapping.FieldDescriptor getIdentity() 

    /**
    **/
    public java.lang.Class getJavaClass()
    {
        return edit.services.config.ConfigServicesMain.class;
    } //-- java.lang.Class getJavaClass() 

    /**
    **/
    public java.lang.String getNameSpacePrefix()
    {
        return nsPrefix;
    } //-- java.lang.String getNameSpacePrefix() 

    /**
    **/
    public java.lang.String getNameSpaceURI()
    {
        return nsURI;
    } //-- java.lang.String getNameSpaceURI() 

    /**
    **/
    public org.exolab.castor.xml.TypeValidator getValidator()
    {
        return this;
    } //-- org.exolab.castor.xml.TypeValidator getValidator() 

    /**
    **/
    public java.lang.String getXMLName()
    {
        return xmlName;
    } //-- java.lang.String getXMLName() 

}
