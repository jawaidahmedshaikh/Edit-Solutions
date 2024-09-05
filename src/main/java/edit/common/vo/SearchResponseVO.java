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
public class SearchResponseVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _clientDetailFK
     */
    private long _clientDetailFK;

    /**
     * keeps track of state for field: _clientDetailFK
     */
    private boolean _has_clientDetailFK;

    /**
     * Field _clientIdentification
     */
    private java.lang.String _clientIdentification;

    /**
     * Field _taxIdentification
     */
    private java.lang.String _taxIdentification;

    /**
     * Field _clientName
     */
    private java.lang.String _clientName;

    /**
     * Field _clientStatus
     */
    private java.lang.String _clientStatus;

    /**
     * Field _dateOfBirth
     */
    private java.lang.String _dateOfBirth;

    /**
     * Field _contractGroupNumber
     */
    private java.lang.String _contractGroupNumber;
    
    /**
     * Field _searchResponseContractInfoList
     */
    private java.util.Vector _searchResponseContractInfoList;


      //----------------/
     //- Constructors -/
    //----------------/

    public SearchResponseVO() {
        super();
        _searchResponseContractInfoList = new Vector();
    } //-- edit.common.vo.SearchResponseVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addSearchResponseContractInfo
     * 
     * @param vSearchResponseContractInfo
     */
    public void addSearchResponseContractInfo(edit.common.vo.SearchResponseContractInfo vSearchResponseContractInfo)
        throws java.lang.IndexOutOfBoundsException
    {
        _searchResponseContractInfoList.addElement(vSearchResponseContractInfo);
    } //-- void addSearchResponseContractInfo(edit.common.vo.SearchResponseContractInfo) 

    /**
     * Method addSearchResponseContractInfo
     * 
     * @param index
     * @param vSearchResponseContractInfo
     */
    public void addSearchResponseContractInfo(int index, edit.common.vo.SearchResponseContractInfo vSearchResponseContractInfo)
        throws java.lang.IndexOutOfBoundsException
    {
        _searchResponseContractInfoList.insertElementAt(vSearchResponseContractInfo, index);
    } //-- void addSearchResponseContractInfo(int, edit.common.vo.SearchResponseContractInfo) 

    /**
     * Method enumerateSearchResponseContractInfo
     */
    public java.util.Enumeration enumerateSearchResponseContractInfo()
    {
        return _searchResponseContractInfoList.elements();
    } //-- java.util.Enumeration enumerateSearchResponseContractInfo() 

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

    	if (obj instanceof SearchResponseVO) {

    		SearchResponseVO temp = (SearchResponseVO)obj;
    		if (this._clientDetailFK != temp._clientDetailFK)
    			return false;
    		if (this._has_clientDetailFK != temp._has_clientDetailFK)
    			return false;
    		if (this._clientIdentification != null) {
    			if (temp._clientIdentification == null) return false;
    			else if (!(this._clientIdentification.equals(temp._clientIdentification))) 
    				return false;
    		}
    		else if (temp._clientIdentification != null)
    			return false;
    		
    		if (this._taxIdentification != null) {
    			if (temp._taxIdentification == null) return false;
    			else if (!(this._taxIdentification.equals(temp._taxIdentification))) 
    				return false;
    		}
    		else if (temp._taxIdentification != null)
    			return false;
    		
    		if (this._clientName != null) {
    			if (temp._clientName == null) return false;
    			else if (!(this._clientName.equals(temp._clientName))) 
    				return false;
    		}
    		else if (temp._clientName != null)
    			return false;
    		
    		if (this._clientStatus != null) {
    			if (temp._clientStatus == null) return false;
    			else if (!(this._clientStatus.equals(temp._clientStatus))) 
    				return false;
    		}
    		else if (temp._clientStatus != null)
    			return false;
    		
    		if (this._dateOfBirth != null) {
    			if (temp._dateOfBirth == null) return false;
    			else if (!(this._dateOfBirth.equals(temp._dateOfBirth))) 
    				return false;
    		}
    		else if (temp._dateOfBirth != null)
    			return false;
    		
    		if (this._contractGroupNumber != null) {
    			if (temp._contractGroupNumber == null) return false;
    			else if (!(this._contractGroupNumber.equals(temp._contractGroupNumber))) 
    				return false;
    		}
    		else if (temp._contractGroupNumber != null)
    			return false;
    		
    		if (this._searchResponseContractInfoList != null) {
    			if (temp._searchResponseContractInfoList == null) return false;
    			else if (!(this._searchResponseContractInfoList.equals(temp._searchResponseContractInfoList))) 
    				return false;
    		}

    		else if (temp._searchResponseContractInfoList != null)
    			return false;
    		return true;
    	}
    	return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getClientDetailFKReturns the value of field
     * 'clientDetailFK'.
     * 
     * @return the value of field 'clientDetailFK'.
     */
    public long getClientDetailFK()
    {
        return this._clientDetailFK;
    } //-- long getClientDetailFK() 

	/**
     * Method getContractGroupNumber the value of field
     * 'contractGroupNumber'.
     * 
     * @return the value of field 'contractGroupNumber'.
     */
	public java.lang.String getContractGroupNumber() {
		return this._contractGroupNumber;
	}
	
    /**
     * Method getClientIdentificationReturns the value of field
     * 'clientIdentification'.
     * 
     * @return the value of field 'clientIdentification'.
     */
    public java.lang.String getClientIdentification()
    {
        return this._clientIdentification;
    } //-- java.lang.String getClientIdentification() 

    /**
     * Method getClientNameReturns the value of field 'clientName'.
     * 
     * @return the value of field 'clientName'.
     */
    public java.lang.String getClientName()
    {
        return this._clientName;
    } //-- java.lang.String getClientName() 

    /**
     * Method getClientStatusReturns the value of field
     * 'clientStatus'.
     * 
     * @return the value of field 'clientStatus'.
     */
    public java.lang.String getClientStatus()
    {
        return this._clientStatus;
    } //-- java.lang.String getClientStatus() 

    /**
     * Method getDateOfBirthReturns the value of field
     * 'dateOfBirth'.
     * 
     * @return the value of field 'dateOfBirth'.
     */
    public java.lang.String getDateOfBirth()
    {
        return this._dateOfBirth;
    } //-- java.lang.String getDateOfBirth() 

    /**
     * Method getSearchResponseContractInfo
     * 
     * @param index
     */
    public edit.common.vo.SearchResponseContractInfo getSearchResponseContractInfo(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _searchResponseContractInfoList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.SearchResponseContractInfo) _searchResponseContractInfoList.elementAt(index);
    } //-- edit.common.vo.SearchResponseContractInfo getSearchResponseContractInfo(int) 

    /**
     * Method getSearchResponseContractInfo
     */
    public edit.common.vo.SearchResponseContractInfo[] getSearchResponseContractInfo()
    {
        int size = _searchResponseContractInfoList.size();
        edit.common.vo.SearchResponseContractInfo[] mArray = new edit.common.vo.SearchResponseContractInfo[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.SearchResponseContractInfo) _searchResponseContractInfoList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.SearchResponseContractInfo[] getSearchResponseContractInfo() 

    /**
     * Method getSearchResponseContractInfoCount
     */
    public int getSearchResponseContractInfoCount()
    {
        return _searchResponseContractInfoList.size();
    } //-- int getSearchResponseContractInfoCount() 

    /**
     * Method getTaxIdentificationReturns the value of field
     * 'taxIdentification'.
     * 
     * @return the value of field 'taxIdentification'.
     */
    public java.lang.String getTaxIdentification()
    {
        return this._taxIdentification;
    } //-- java.lang.String getTaxIdentification() 

    /**
     * Method hasClientDetailFK
     */
    public boolean hasClientDetailFK()
    {
        return this._has_clientDetailFK;
    } //-- boolean hasClientDetailFK() 

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
     * Method removeAllSearchResponseContractInfo
     */
    public void removeAllSearchResponseContractInfo()
    {
        _searchResponseContractInfoList.removeAllElements();
    } //-- void removeAllSearchResponseContractInfo() 

    /**
     * Method removeSearchResponseContractInfo
     * 
     * @param index
     */
    public edit.common.vo.SearchResponseContractInfo removeSearchResponseContractInfo(int index)
    {
        java.lang.Object obj = _searchResponseContractInfoList.elementAt(index);
        _searchResponseContractInfoList.removeElementAt(index);
        return (edit.common.vo.SearchResponseContractInfo) obj;
    } //-- edit.common.vo.SearchResponseContractInfo removeSearchResponseContractInfo(int) 

    /**
     * Method setClientDetailFKSets the value of field
     * 'clientDetailFK'.
     * 
     * @param clientDetailFK the value of field 'clientDetailFK'.
     */
    public void setClientDetailFK(long clientDetailFK)
    {
        this._clientDetailFK = clientDetailFK;
        
        super.setVoChanged(true);
        this._has_clientDetailFK = true;
    } //-- void setClientDetailFK(long) 

    /**
     * Method setClientIdentificationSets the value of field
     * 'clientIdentification'.
     * 
     * @param clientIdentification the value of field
     * 'clientIdentification'.
     */
    public void setClientIdentification(java.lang.String clientIdentification)
    {
        this._clientIdentification = clientIdentification;
        
        super.setVoChanged(true);
    } //-- void setClientIdentification(java.lang.String) 

    /**
     * Method setContractGroupNumber the value of field
     * 'contractGroupNumber'.
     * 
     * @param contractGroupNumber the value of field
     * 'contractGroupNumber'.
     */
    public void setContractGroupNumber(java.lang.String contractGroupNumber)
    {
        this._contractGroupNumber = contractGroupNumber;
        
        super.setVoChanged(true);
    } //-- void setClientIdentification(java.lang.String) 

    
    /**
     * Method setClientNameSets the value of field 'clientName'.
     * 
     * @param clientName the value of field 'clientName'.
     */
    public void setClientName(java.lang.String clientName)
    {
        this._clientName = clientName;
        
        super.setVoChanged(true);
    } //-- void setClientName(java.lang.String) 

    /**
     * Method setClientStatusSets the value of field
     * 'clientStatus'.
     * 
     * @param clientStatus the value of field 'clientStatus'.
     */
    public void setClientStatus(java.lang.String clientStatus)
    {
        this._clientStatus = clientStatus;
        
        super.setVoChanged(true);
    } //-- void setClientStatus(java.lang.String) 

    /**
     * Method setDateOfBirthSets the value of field 'dateOfBirth'.
     * 
     * @param dateOfBirth the value of field 'dateOfBirth'.
     */
    public void setDateOfBirth(java.lang.String dateOfBirth)
    {
        this._dateOfBirth = dateOfBirth;
        
        super.setVoChanged(true);
    } //-- void setDateOfBirth(java.lang.String) 

    /**
     * Method setSearchResponseContractInfo
     * 
     * @param index
     * @param vSearchResponseContractInfo
     */
    public void setSearchResponseContractInfo(int index, edit.common.vo.SearchResponseContractInfo vSearchResponseContractInfo)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _searchResponseContractInfoList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _searchResponseContractInfoList.setElementAt(vSearchResponseContractInfo, index);
    } //-- void setSearchResponseContractInfo(int, edit.common.vo.SearchResponseContractInfo) 

    /**
     * Method setSearchResponseContractInfo
     * 
     * @param searchResponseContractInfoArray
     */
    public void setSearchResponseContractInfo(edit.common.vo.SearchResponseContractInfo[] searchResponseContractInfoArray)
    {
        //-- copy array
        _searchResponseContractInfoList.removeAllElements();
        for (int i = 0; i < searchResponseContractInfoArray.length; i++) {
            _searchResponseContractInfoList.addElement(searchResponseContractInfoArray[i]);
        }
    } //-- void setSearchResponseContractInfo(edit.common.vo.SearchResponseContractInfo) 

    /**
     * Method setTaxIdentificationSets the value of field
     * 'taxIdentification'.
     * 
     * @param taxIdentification the value of field
     * 'taxIdentification'.
     */
    public void setTaxIdentification(java.lang.String taxIdentification)
    {
        this._taxIdentification = taxIdentification;
        
        super.setVoChanged(true);
    } //-- void setTaxIdentification(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.SearchResponseVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SearchResponseVO) Unmarshaller.unmarshal(edit.common.vo.SearchResponseVO.class, reader);
    } //-- edit.common.vo.SearchResponseVO unmarshal(java.io.Reader) 

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
