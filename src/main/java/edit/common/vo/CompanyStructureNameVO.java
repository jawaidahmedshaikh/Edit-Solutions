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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class CompanyStructureNameVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _companyNameList
     */
    private java.util.Vector _companyNameList;

    /**
     * Field _marketingPackageNameList
     */
    private java.util.Vector _marketingPackageNameList;

    /**
     * Field _groupProductNameList
     */
    private java.util.Vector _groupProductNameList;

    /**
     * Field _areaNameList
     */
    private java.util.Vector _areaNameList;

    /**
     * Field _businessContractNameList
     */
    private java.util.Vector _businessContractNameList;


      //----------------/
     //- Constructors -/
    //----------------/

    public CompanyStructureNameVO() {
        super();
        _companyNameList = new Vector();
        _marketingPackageNameList = new Vector();
        _groupProductNameList = new Vector();
        _areaNameList = new Vector();
        _businessContractNameList = new Vector();
    } //-- edit.common.vo.CompanyStructureNameVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAreaName
     * 
     * @param vAreaName
     */
    public void addAreaName(java.lang.String vAreaName)
        throws java.lang.IndexOutOfBoundsException
    {
        _areaNameList.addElement(vAreaName);
    } //-- void addAreaName(java.lang.String) 

    /**
     * Method addAreaName
     * 
     * @param index
     * @param vAreaName
     */
    public void addAreaName(int index, java.lang.String vAreaName)
        throws java.lang.IndexOutOfBoundsException
    {
        _areaNameList.insertElementAt(vAreaName, index);
    } //-- void addAreaName(int, java.lang.String) 

    /**
     * Method addBusinessContractName
     * 
     * @param vBusinessContractName
     */
    public void addBusinessContractName(java.lang.String vBusinessContractName)
        throws java.lang.IndexOutOfBoundsException
    {
        _businessContractNameList.addElement(vBusinessContractName);
    } //-- void addBusinessContractName(java.lang.String) 

    /**
     * Method addBusinessContractName
     * 
     * @param index
     * @param vBusinessContractName
     */
    public void addBusinessContractName(int index, java.lang.String vBusinessContractName)
        throws java.lang.IndexOutOfBoundsException
    {
        _businessContractNameList.insertElementAt(vBusinessContractName, index);
    } //-- void addBusinessContractName(int, java.lang.String) 

    /**
     * Method addCompanyName
     * 
     * @param vCompanyName
     */
    public void addCompanyName(java.lang.String vCompanyName)
        throws java.lang.IndexOutOfBoundsException
    {
        _companyNameList.addElement(vCompanyName);
    } //-- void addCompanyName(java.lang.String) 

    /**
     * Method addCompanyName
     * 
     * @param index
     * @param vCompanyName
     */
    public void addCompanyName(int index, java.lang.String vCompanyName)
        throws java.lang.IndexOutOfBoundsException
    {
        _companyNameList.insertElementAt(vCompanyName, index);
    } //-- void addCompanyName(int, java.lang.String) 

    /**
     * Method addGroupProductName
     * 
     * @param vGroupProductName
     */
    public void addGroupProductName(java.lang.String vGroupProductName)
        throws java.lang.IndexOutOfBoundsException
    {
        _groupProductNameList.addElement(vGroupProductName);
    } //-- void addGroupProductName(java.lang.String) 

    /**
     * Method addGroupProductName
     * 
     * @param index
     * @param vGroupProductName
     */
    public void addGroupProductName(int index, java.lang.String vGroupProductName)
        throws java.lang.IndexOutOfBoundsException
    {
        _groupProductNameList.insertElementAt(vGroupProductName, index);
    } //-- void addGroupProductName(int, java.lang.String) 

    /**
     * Method addMarketingPackageName
     * 
     * @param vMarketingPackageName
     */
    public void addMarketingPackageName(java.lang.String vMarketingPackageName)
        throws java.lang.IndexOutOfBoundsException
    {
        _marketingPackageNameList.addElement(vMarketingPackageName);
    } //-- void addMarketingPackageName(java.lang.String) 

    /**
     * Method addMarketingPackageName
     * 
     * @param index
     * @param vMarketingPackageName
     */
    public void addMarketingPackageName(int index, java.lang.String vMarketingPackageName)
        throws java.lang.IndexOutOfBoundsException
    {
        _marketingPackageNameList.insertElementAt(vMarketingPackageName, index);
    } //-- void addMarketingPackageName(int, java.lang.String) 

    /**
     * Method enumerateAreaName
     */
    public java.util.Enumeration enumerateAreaName()
    {
        return _areaNameList.elements();
    } //-- java.util.Enumeration enumerateAreaName() 

    /**
     * Method enumerateBusinessContractName
     */
    public java.util.Enumeration enumerateBusinessContractName()
    {
        return _businessContractNameList.elements();
    } //-- java.util.Enumeration enumerateBusinessContractName() 

    /**
     * Method enumerateCompanyName
     */
    public java.util.Enumeration enumerateCompanyName()
    {
        return _companyNameList.elements();
    } //-- java.util.Enumeration enumerateCompanyName() 

    /**
     * Method enumerateGroupProductName
     */
    public java.util.Enumeration enumerateGroupProductName()
    {
        return _groupProductNameList.elements();
    } //-- java.util.Enumeration enumerateGroupProductName() 

    /**
     * Method enumerateMarketingPackageName
     */
    public java.util.Enumeration enumerateMarketingPackageName()
    {
        return _marketingPackageNameList.elements();
    } //-- java.util.Enumeration enumerateMarketingPackageName() 

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
        
        if (obj instanceof CompanyStructureNameVO) {
        
            CompanyStructureNameVO temp = (CompanyStructureNameVO)obj;
            if (this._companyNameList != null) {
                if (temp._companyNameList == null) return false;
                else if (!(this._companyNameList.equals(temp._companyNameList))) 
                    return false;
            }
            else if (temp._companyNameList != null)
                return false;
            if (this._marketingPackageNameList != null) {
                if (temp._marketingPackageNameList == null) return false;
                else if (!(this._marketingPackageNameList.equals(temp._marketingPackageNameList))) 
                    return false;
            }
            else if (temp._marketingPackageNameList != null)
                return false;
            if (this._groupProductNameList != null) {
                if (temp._groupProductNameList == null) return false;
                else if (!(this._groupProductNameList.equals(temp._groupProductNameList))) 
                    return false;
            }
            else if (temp._groupProductNameList != null)
                return false;
            if (this._areaNameList != null) {
                if (temp._areaNameList == null) return false;
                else if (!(this._areaNameList.equals(temp._areaNameList))) 
                    return false;
            }
            else if (temp._areaNameList != null)
                return false;
            if (this._businessContractNameList != null) {
                if (temp._businessContractNameList == null) return false;
                else if (!(this._businessContractNameList.equals(temp._businessContractNameList))) 
                    return false;
            }
            else if (temp._businessContractNameList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAreaName
     * 
     * @param index
     */
    public java.lang.String getAreaName(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _areaNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (String)_areaNameList.elementAt(index);
    } //-- java.lang.String getAreaName(int) 

    /**
     * Method getAreaName
     */
    public java.lang.String[] getAreaName()
    {
        int size = _areaNameList.size();
        java.lang.String[] mArray = new java.lang.String[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (String)_areaNameList.elementAt(index);
        }
        return mArray;
    } //-- java.lang.String[] getAreaName() 

    /**
     * Method getAreaNameCount
     */
    public int getAreaNameCount()
    {
        return _areaNameList.size();
    } //-- int getAreaNameCount() 

    /**
     * Method getBusinessContractName
     * 
     * @param index
     */
    public java.lang.String getBusinessContractName(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _businessContractNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (String)_businessContractNameList.elementAt(index);
    } //-- java.lang.String getBusinessContractName(int) 

    /**
     * Method getBusinessContractName
     */
    public java.lang.String[] getBusinessContractName()
    {
        int size = _businessContractNameList.size();
        java.lang.String[] mArray = new java.lang.String[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (String)_businessContractNameList.elementAt(index);
        }
        return mArray;
    } //-- java.lang.String[] getBusinessContractName() 

    /**
     * Method getBusinessContractNameCount
     */
    public int getBusinessContractNameCount()
    {
        return _businessContractNameList.size();
    } //-- int getBusinessContractNameCount() 

    /**
     * Method getCompanyName
     * 
     * @param index
     */
    public java.lang.String getCompanyName(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _companyNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (String)_companyNameList.elementAt(index);
    } //-- java.lang.String getCompanyName(int) 

    /**
     * Method getCompanyName
     */
    public java.lang.String[] getCompanyName()
    {
        int size = _companyNameList.size();
        java.lang.String[] mArray = new java.lang.String[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (String)_companyNameList.elementAt(index);
        }
        return mArray;
    } //-- java.lang.String[] getCompanyName() 

    /**
     * Method getCompanyNameCount
     */
    public int getCompanyNameCount()
    {
        return _companyNameList.size();
    } //-- int getCompanyNameCount() 

    /**
     * Method getGroupProductName
     * 
     * @param index
     */
    public java.lang.String getGroupProductName(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _groupProductNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (String)_groupProductNameList.elementAt(index);
    } //-- java.lang.String getGroupProductName(int) 

    /**
     * Method getGroupProductName
     */
    public java.lang.String[] getGroupProductName()
    {
        int size = _groupProductNameList.size();
        java.lang.String[] mArray = new java.lang.String[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (String)_groupProductNameList.elementAt(index);
        }
        return mArray;
    } //-- java.lang.String[] getGroupProductName() 

    /**
     * Method getGroupProductNameCount
     */
    public int getGroupProductNameCount()
    {
        return _groupProductNameList.size();
    } //-- int getGroupProductNameCount() 

    /**
     * Method getMarketingPackageName
     * 
     * @param index
     */
    public java.lang.String getMarketingPackageName(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _marketingPackageNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (String)_marketingPackageNameList.elementAt(index);
    } //-- java.lang.String getMarketingPackageName(int) 

    /**
     * Method getMarketingPackageName
     */
    public java.lang.String[] getMarketingPackageName()
    {
        int size = _marketingPackageNameList.size();
        java.lang.String[] mArray = new java.lang.String[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (String)_marketingPackageNameList.elementAt(index);
        }
        return mArray;
    } //-- java.lang.String[] getMarketingPackageName() 

    /**
     * Method getMarketingPackageNameCount
     */
    public int getMarketingPackageNameCount()
    {
        return _marketingPackageNameList.size();
    } //-- int getMarketingPackageNameCount() 

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
     * Method removeAllAreaName
     */
    public void removeAllAreaName()
    {
        _areaNameList.removeAllElements();
    } //-- void removeAllAreaName() 

    /**
     * Method removeAllBusinessContractName
     */
    public void removeAllBusinessContractName()
    {
        _businessContractNameList.removeAllElements();
    } //-- void removeAllBusinessContractName() 

    /**
     * Method removeAllCompanyName
     */
    public void removeAllCompanyName()
    {
        _companyNameList.removeAllElements();
    } //-- void removeAllCompanyName() 

    /**
     * Method removeAllGroupProductName
     */
    public void removeAllGroupProductName()
    {
        _groupProductNameList.removeAllElements();
    } //-- void removeAllGroupProductName() 

    /**
     * Method removeAllMarketingPackageName
     */
    public void removeAllMarketingPackageName()
    {
        _marketingPackageNameList.removeAllElements();
    } //-- void removeAllMarketingPackageName() 

    /**
     * Method removeAreaName
     * 
     * @param index
     */
    public java.lang.String removeAreaName(int index)
    {
        java.lang.Object obj = _areaNameList.elementAt(index);
        _areaNameList.removeElementAt(index);
        return (String)obj;
    } //-- java.lang.String removeAreaName(int) 

    /**
     * Method removeBusinessContractName
     * 
     * @param index
     */
    public java.lang.String removeBusinessContractName(int index)
    {
        java.lang.Object obj = _businessContractNameList.elementAt(index);
        _businessContractNameList.removeElementAt(index);
        return (String)obj;
    } //-- java.lang.String removeBusinessContractName(int) 

    /**
     * Method removeCompanyName
     * 
     * @param index
     */
    public java.lang.String removeCompanyName(int index)
    {
        java.lang.Object obj = _companyNameList.elementAt(index);
        _companyNameList.removeElementAt(index);
        return (String)obj;
    } //-- java.lang.String removeCompanyName(int) 

    /**
     * Method removeGroupProductName
     * 
     * @param index
     */
    public java.lang.String removeGroupProductName(int index)
    {
        java.lang.Object obj = _groupProductNameList.elementAt(index);
        _groupProductNameList.removeElementAt(index);
        return (String)obj;
    } //-- java.lang.String removeGroupProductName(int) 

    /**
     * Method removeMarketingPackageName
     * 
     * @param index
     */
    public java.lang.String removeMarketingPackageName(int index)
    {
        java.lang.Object obj = _marketingPackageNameList.elementAt(index);
        _marketingPackageNameList.removeElementAt(index);
        return (String)obj;
    } //-- java.lang.String removeMarketingPackageName(int) 

    /**
     * Method setAreaName
     * 
     * @param index
     * @param vAreaName
     */
    public void setAreaName(int index, java.lang.String vAreaName)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _areaNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _areaNameList.setElementAt(vAreaName, index);
    } //-- void setAreaName(int, java.lang.String) 

    /**
     * Method setAreaName
     * 
     * @param areaNameArray
     */
    public void setAreaName(java.lang.String[] areaNameArray)
    {
        //-- copy array
        _areaNameList.removeAllElements();
        for (int i = 0; i < areaNameArray.length; i++) {
            _areaNameList.addElement(areaNameArray[i]);
        }
    } //-- void setAreaName(java.lang.String) 

    /**
     * Method setBusinessContractName
     * 
     * @param index
     * @param vBusinessContractName
     */
    public void setBusinessContractName(int index, java.lang.String vBusinessContractName)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _businessContractNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _businessContractNameList.setElementAt(vBusinessContractName, index);
    } //-- void setBusinessContractName(int, java.lang.String) 

    /**
     * Method setBusinessContractName
     * 
     * @param businessContractNameArray
     */
    public void setBusinessContractName(java.lang.String[] businessContractNameArray)
    {
        //-- copy array
        _businessContractNameList.removeAllElements();
        for (int i = 0; i < businessContractNameArray.length; i++) {
            _businessContractNameList.addElement(businessContractNameArray[i]);
        }
    } //-- void setBusinessContractName(java.lang.String) 

    /**
     * Method setCompanyName
     * 
     * @param index
     * @param vCompanyName
     */
    public void setCompanyName(int index, java.lang.String vCompanyName)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _companyNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _companyNameList.setElementAt(vCompanyName, index);
    } //-- void setCompanyName(int, java.lang.String) 

    /**
     * Method setCompanyName
     * 
     * @param companyNameArray
     */
    public void setCompanyName(java.lang.String[] companyNameArray)
    {
        //-- copy array
        _companyNameList.removeAllElements();
        for (int i = 0; i < companyNameArray.length; i++) {
            _companyNameList.addElement(companyNameArray[i]);
        }
    } //-- void setCompanyName(java.lang.String) 

    /**
     * Method setGroupProductName
     * 
     * @param index
     * @param vGroupProductName
     */
    public void setGroupProductName(int index, java.lang.String vGroupProductName)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _groupProductNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _groupProductNameList.setElementAt(vGroupProductName, index);
    } //-- void setGroupProductName(int, java.lang.String) 

    /**
     * Method setGroupProductName
     * 
     * @param groupProductNameArray
     */
    public void setGroupProductName(java.lang.String[] groupProductNameArray)
    {
        //-- copy array
        _groupProductNameList.removeAllElements();
        for (int i = 0; i < groupProductNameArray.length; i++) {
            _groupProductNameList.addElement(groupProductNameArray[i]);
        }
    } //-- void setGroupProductName(java.lang.String) 

    /**
     * Method setMarketingPackageName
     * 
     * @param index
     * @param vMarketingPackageName
     */
    public void setMarketingPackageName(int index, java.lang.String vMarketingPackageName)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _marketingPackageNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _marketingPackageNameList.setElementAt(vMarketingPackageName, index);
    } //-- void setMarketingPackageName(int, java.lang.String) 

    /**
     * Method setMarketingPackageName
     * 
     * @param marketingPackageNameArray
     */
    public void setMarketingPackageName(java.lang.String[] marketingPackageNameArray)
    {
        //-- copy array
        _marketingPackageNameList.removeAllElements();
        for (int i = 0; i < marketingPackageNameArray.length; i++) {
            _marketingPackageNameList.addElement(marketingPackageNameArray[i]);
        }
    } //-- void setMarketingPackageName(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CompanyStructureNameVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CompanyStructureNameVO) Unmarshaller.unmarshal(edit.common.vo.CompanyStructureNameVO.class, reader);
    } //-- edit.common.vo.CompanyStructureNameVO unmarshal(java.io.Reader) 

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
