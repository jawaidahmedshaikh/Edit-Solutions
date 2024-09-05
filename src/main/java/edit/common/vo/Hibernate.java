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
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class Hibernate.
 * 
 * @version $Revision$ $Date$
 */
public class Hibernate extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _entityList
     */
    private java.util.Vector _entityList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Hibernate() {
        super();
        _entityList = new Vector();
    } //-- edit.common.vo.Hibernate()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addEntity
     * 
     * @param vEntity
     */
    public void addEntity(java.lang.String vEntity)
        throws java.lang.IndexOutOfBoundsException
    {
        _entityList.addElement(vEntity);
    } //-- void addEntity(java.lang.String) 

    /**
     * Method addEntity
     * 
     * @param index
     * @param vEntity
     */
    public void addEntity(int index, java.lang.String vEntity)
        throws java.lang.IndexOutOfBoundsException
    {
        _entityList.insertElementAt(vEntity, index);
    } //-- void addEntity(int, java.lang.String) 

    /**
     * Method enumerateEntity
     */
    public java.util.Enumeration enumerateEntity()
    {
        return _entityList.elements();
    } //-- java.util.Enumeration enumerateEntity() 

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
        
        if (obj instanceof Hibernate) {
        
            Hibernate temp = (Hibernate)obj;
            if (this._entityList != null) {
                if (temp._entityList == null) return false;
                else if (!(this._entityList.equals(temp._entityList))) 
                    return false;
            }
            else if (temp._entityList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getEntity
     * 
     * @param index
     */
    public java.lang.String getEntity(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _entityList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (String)_entityList.elementAt(index);
    } //-- java.lang.String getEntity(int) 

    /**
     * Method getEntity
     */
    public java.lang.String[] getEntity()
    {
        int size = _entityList.size();
        java.lang.String[] mArray = new java.lang.String[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (String)_entityList.elementAt(index);
        }
        return mArray;
    } //-- java.lang.String[] getEntity() 

    /**
     * Method getEntityCount
     */
    public int getEntityCount()
    {
        return _entityList.size();
    } //-- int getEntityCount() 

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
     * Method removeAllEntity
     */
    public void removeAllEntity()
    {
        _entityList.removeAllElements();
    } //-- void removeAllEntity() 

    /**
     * Method removeEntity
     * 
     * @param index
     */
    public java.lang.String removeEntity(int index)
    {
        java.lang.Object obj = _entityList.elementAt(index);
        _entityList.removeElementAt(index);
        return (String)obj;
    } //-- java.lang.String removeEntity(int) 

    /**
     * Method setEntity
     * 
     * @param index
     * @param vEntity
     */
    public void setEntity(int index, java.lang.String vEntity)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _entityList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _entityList.setElementAt(vEntity, index);
    } //-- void setEntity(int, java.lang.String) 

    /**
     * Method setEntity
     * 
     * @param entityArray
     */
    public void setEntity(java.lang.String[] entityArray)
    {
        //-- copy array
        _entityList.removeAllElements();
        for (int i = 0; i < entityArray.length; i++) {
            _entityList.addElement(entityArray[i]);
        }
    } //-- void setEntity(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.Hibernate unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.Hibernate) Unmarshaller.unmarshal(edit.common.vo.Hibernate.class, reader);
    } //-- edit.common.vo.Hibernate unmarshal(java.io.Reader) 

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
