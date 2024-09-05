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
 * Class IgnoreAgentLicensingEdits.
 * 
 * @version $Revision$ $Date$
 */
public class IgnoreAgentLicensingEdits extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _companyStructurePKList
     */
    private java.util.Vector _companyStructurePKList;


      //----------------/
     //- Constructors -/
    //----------------/

    public IgnoreAgentLicensingEdits() {
        super();
        _companyStructurePKList = new Vector();
    } //-- edit.common.vo.IgnoreAgentLicensingEdits()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addCompanyStructurePK
     * 
     * @param vCompanyStructurePK
     */
    public void addCompanyStructurePK(java.lang.String vCompanyStructurePK)
        throws java.lang.IndexOutOfBoundsException
    {
        _companyStructurePKList.addElement(vCompanyStructurePK);
    } //-- void addCompanyStructurePK(java.lang.String) 

    /**
     * Method addCompanyStructurePK
     * 
     * @param index
     * @param vCompanyStructurePK
     */
    public void addCompanyStructurePK(int index, java.lang.String vCompanyStructurePK)
        throws java.lang.IndexOutOfBoundsException
    {
        _companyStructurePKList.insertElementAt(vCompanyStructurePK, index);
    } //-- void addCompanyStructurePK(int, java.lang.String) 

    /**
     * Method enumerateCompanyStructurePK
     */
    public java.util.Enumeration enumerateCompanyStructurePK()
    {
        return _companyStructurePKList.elements();
    } //-- java.util.Enumeration enumerateCompanyStructurePK() 

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
        
        if (obj instanceof IgnoreAgentLicensingEdits) {
        
            IgnoreAgentLicensingEdits temp = (IgnoreAgentLicensingEdits)obj;
            if (this._companyStructurePKList != null) {
                if (temp._companyStructurePKList == null) return false;
                else if (!(this._companyStructurePKList.equals(temp._companyStructurePKList))) 
                    return false;
            }
            else if (temp._companyStructurePKList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCompanyStructurePK
     * 
     * @param index
     */
    public java.lang.String getCompanyStructurePK(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _companyStructurePKList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (String)_companyStructurePKList.elementAt(index);
    } //-- java.lang.String getCompanyStructurePK(int) 

    /**
     * Method getCompanyStructurePK
     */
    public java.lang.String[] getCompanyStructurePK()
    {
        int size = _companyStructurePKList.size();
        java.lang.String[] mArray = new java.lang.String[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (String)_companyStructurePKList.elementAt(index);
        }
        return mArray;
    } //-- java.lang.String[] getCompanyStructurePK() 

    /**
     * Method getCompanyStructurePKCount
     */
    public int getCompanyStructurePKCount()
    {
        return _companyStructurePKList.size();
    } //-- int getCompanyStructurePKCount() 

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
     * Method removeAllCompanyStructurePK
     */
    public void removeAllCompanyStructurePK()
    {
        _companyStructurePKList.removeAllElements();
    } //-- void removeAllCompanyStructurePK() 

    /**
     * Method removeCompanyStructurePK
     * 
     * @param index
     */
    public java.lang.String removeCompanyStructurePK(int index)
    {
        java.lang.Object obj = _companyStructurePKList.elementAt(index);
        _companyStructurePKList.removeElementAt(index);
        return (String)obj;
    } //-- java.lang.String removeCompanyStructurePK(int) 

    /**
     * Method setCompanyStructurePK
     * 
     * @param index
     * @param vCompanyStructurePK
     */
    public void setCompanyStructurePK(int index, java.lang.String vCompanyStructurePK)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _companyStructurePKList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _companyStructurePKList.setElementAt(vCompanyStructurePK, index);
    } //-- void setCompanyStructurePK(int, java.lang.String) 

    /**
     * Method setCompanyStructurePK
     * 
     * @param companyStructurePKArray
     */
    public void setCompanyStructurePK(java.lang.String[] companyStructurePKArray)
    {
        //-- copy array
        _companyStructurePKList.removeAllElements();
        for (int i = 0; i < companyStructurePKArray.length; i++) {
            _companyStructurePKList.addElement(companyStructurePKArray[i]);
        }
    } //-- void setCompanyStructurePK(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.IgnoreAgentLicensingEdits unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.IgnoreAgentLicensingEdits) Unmarshaller.unmarshal(edit.common.vo.IgnoreAgentLicensingEdits.class, reader);
    } //-- edit.common.vo.IgnoreAgentLicensingEdits unmarshal(java.io.Reader) 

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
