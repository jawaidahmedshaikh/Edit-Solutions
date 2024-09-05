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
 * Class NonFinancialEntity.
 * 
 * @version $Revision$ $Date$
 */
public class NonFinancialEntity extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _className
     */
    private java.lang.String _className;

    /**
     * Field _changeEffectiveDate
     */
    private java.lang.String _changeEffectiveDate;

    /**
     * Field _ignoreFieldList
     */
    private java.util.Vector _ignoreFieldList;


      //----------------/
     //- Constructors -/
    //----------------/

    public NonFinancialEntity() {
        super();
        _ignoreFieldList = new Vector();
    } //-- edit.common.vo.NonFinancialEntity()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addIgnoreField
     * 
     * @param vIgnoreField
     */
    public void addIgnoreField(java.lang.String vIgnoreField)
        throws java.lang.IndexOutOfBoundsException
    {
        _ignoreFieldList.addElement(vIgnoreField);
    } //-- void addIgnoreField(java.lang.String) 

    /**
     * Method addIgnoreField
     * 
     * @param index
     * @param vIgnoreField
     */
    public void addIgnoreField(int index, java.lang.String vIgnoreField)
        throws java.lang.IndexOutOfBoundsException
    {
        _ignoreFieldList.insertElementAt(vIgnoreField, index);
    } //-- void addIgnoreField(int, java.lang.String) 

    /**
     * Method enumerateIgnoreField
     */
    public java.util.Enumeration enumerateIgnoreField()
    {
        return _ignoreFieldList.elements();
    } //-- java.util.Enumeration enumerateIgnoreField() 

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
        
        if (obj instanceof NonFinancialEntity) {
        
            NonFinancialEntity temp = (NonFinancialEntity)obj;
            if (this._className != null) {
                if (temp._className == null) return false;
                else if (!(this._className.equals(temp._className))) 
                    return false;
            }
            else if (temp._className != null)
                return false;
            if (this._changeEffectiveDate != null) {
                if (temp._changeEffectiveDate == null) return false;
                else if (!(this._changeEffectiveDate.equals(temp._changeEffectiveDate))) 
                    return false;
            }
            else if (temp._changeEffectiveDate != null)
                return false;
            if (this._ignoreFieldList != null) {
                if (temp._ignoreFieldList == null) return false;
                else if (!(this._ignoreFieldList.equals(temp._ignoreFieldList))) 
                    return false;
            }
            else if (temp._ignoreFieldList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getChangeEffectiveDateReturns the value of field
     * 'changeEffectiveDate'.
     * 
     * @return the value of field 'changeEffectiveDate'.
     */
    public java.lang.String getChangeEffectiveDate()
    {
        return this._changeEffectiveDate;
    } //-- java.lang.String getChangeEffectiveDate() 

    /**
     * Method getClassNameReturns the value of field 'className'.
     * 
     * @return the value of field 'className'.
     */
    public java.lang.String getClassName()
    {
        return this._className;
    } //-- java.lang.String getClassName() 

    /**
     * Method getIgnoreField
     * 
     * @param index
     */
    public java.lang.String getIgnoreField(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _ignoreFieldList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (String)_ignoreFieldList.elementAt(index);
    } //-- java.lang.String getIgnoreField(int) 

    /**
     * Method getIgnoreField
     */
    public java.lang.String[] getIgnoreField()
    {
        int size = _ignoreFieldList.size();
        java.lang.String[] mArray = new java.lang.String[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (String)_ignoreFieldList.elementAt(index);
        }
        return mArray;
    } //-- java.lang.String[] getIgnoreField() 

    /**
     * Method getIgnoreFieldCount
     */
    public int getIgnoreFieldCount()
    {
        return _ignoreFieldList.size();
    } //-- int getIgnoreFieldCount() 

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
     * Method removeAllIgnoreField
     */
    public void removeAllIgnoreField()
    {
        _ignoreFieldList.removeAllElements();
    } //-- void removeAllIgnoreField() 

    /**
     * Method removeIgnoreField
     * 
     * @param index
     */
    public java.lang.String removeIgnoreField(int index)
    {
        java.lang.Object obj = _ignoreFieldList.elementAt(index);
        _ignoreFieldList.removeElementAt(index);
        return (String)obj;
    } //-- java.lang.String removeIgnoreField(int) 

    /**
     * Method setChangeEffectiveDateSets the value of field
     * 'changeEffectiveDate'.
     * 
     * @param changeEffectiveDate the value of field
     * 'changeEffectiveDate'.
     */
    public void setChangeEffectiveDate(java.lang.String changeEffectiveDate)
    {
        this._changeEffectiveDate = changeEffectiveDate;
        
        super.setVoChanged(true);
    } //-- void setChangeEffectiveDate(java.lang.String) 

    /**
     * Method setClassNameSets the value of field 'className'.
     * 
     * @param className the value of field 'className'.
     */
    public void setClassName(java.lang.String className)
    {
        this._className = className;
        
        super.setVoChanged(true);
    } //-- void setClassName(java.lang.String) 

    /**
     * Method setIgnoreField
     * 
     * @param index
     * @param vIgnoreField
     */
    public void setIgnoreField(int index, java.lang.String vIgnoreField)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _ignoreFieldList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _ignoreFieldList.setElementAt(vIgnoreField, index);
    } //-- void setIgnoreField(int, java.lang.String) 

    /**
     * Method setIgnoreField
     * 
     * @param ignoreFieldArray
     */
    public void setIgnoreField(java.lang.String[] ignoreFieldArray)
    {
        //-- copy array
        _ignoreFieldList.removeAllElements();
        for (int i = 0; i < ignoreFieldArray.length; i++) {
            _ignoreFieldList.addElement(ignoreFieldArray[i]);
        }
    } //-- void setIgnoreField(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.NonFinancialEntity unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.NonFinancialEntity) Unmarshaller.unmarshal(edit.common.vo.NonFinancialEntity.class, reader);
    } //-- edit.common.vo.NonFinancialEntity unmarshal(java.io.Reader) 

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
