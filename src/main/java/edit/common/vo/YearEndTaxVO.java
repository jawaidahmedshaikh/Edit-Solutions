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
public class YearEndTaxVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _contractNumber
     */
    private java.lang.String _contractNumber;

    /**
     * Field _companyName
     */
    private java.lang.String _companyName;

    /**
     * Field _clientLastName
     */
    private java.lang.String _clientLastName;

    /**
     * Field _clientFirstName
     */
    private java.lang.String _clientFirstName;

    /**
     * Field _clientMiddleName
     */
    private java.lang.String _clientMiddleName;

    /**
     * Field _clientAddressLine1
     */
    private java.lang.String _clientAddressLine1;

    /**
     * Field _clientAddressLine2
     */
    private java.lang.String _clientAddressLine2;

    /**
     * Field _clientAddressLine3
     */
    private java.lang.String _clientAddressLine3;

    /**
     * Field _clientAddressLine4
     */
    private java.lang.String _clientAddressLine4;

    /**
     * Field _county
     */
    private java.lang.String _county;

    /**
     * Field _city
     */
    private java.lang.String _city;

    /**
     * Field _state
     */
    private java.lang.String _state;

    /**
     * Field _country
     */
    private java.lang.String _country;

    /**
     * Field _zipCode
     */
    private java.lang.String _zipCode;

    /**
     * Field _filingStatus
     */
    private java.lang.String _filingStatus;

    /**
     * Field _fromDate
     */
    private java.lang.String _fromDate;

    /**
     * Field _toDate
     */
    private java.lang.String _toDate;

    /**
     * Field _taxYear
     */
    private int _taxYear;

    /**
     * keeps track of state for field: _taxYear
     */
    private boolean _has_taxYear;

    /**
     * Field _taxQualifier
     */
    private java.lang.String _taxQualifier;

    /**
     * Comment describing your root element
     */
    private java.util.Vector _taxFormVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public YearEndTaxVO() {
        super();
        _taxFormVOList = new Vector();
    } //-- edit.common.vo.YearEndTaxVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addTaxFormVO
     * 
     * @param vTaxFormVO
     */
    public void addTaxFormVO(edit.common.vo.TaxFormVO vTaxFormVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vTaxFormVO.setParentVO(this.getClass(), this);
        _taxFormVOList.addElement(vTaxFormVO);
    } //-- void addTaxFormVO(edit.common.vo.TaxFormVO) 

    /**
     * Method addTaxFormVO
     * 
     * @param index
     * @param vTaxFormVO
     */
    public void addTaxFormVO(int index, edit.common.vo.TaxFormVO vTaxFormVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vTaxFormVO.setParentVO(this.getClass(), this);
        _taxFormVOList.insertElementAt(vTaxFormVO, index);
    } //-- void addTaxFormVO(int, edit.common.vo.TaxFormVO) 

    /**
     * Method enumerateTaxFormVO
     */
    public java.util.Enumeration enumerateTaxFormVO()
    {
        return _taxFormVOList.elements();
    } //-- java.util.Enumeration enumerateTaxFormVO() 

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
        
        if (obj instanceof YearEndTaxVO) {
        
            YearEndTaxVO temp = (YearEndTaxVO)obj;
            if (this._contractNumber != null) {
                if (temp._contractNumber == null) return false;
                else if (!(this._contractNumber.equals(temp._contractNumber))) 
                    return false;
            }
            else if (temp._contractNumber != null)
                return false;
            if (this._companyName != null) {
                if (temp._companyName == null) return false;
                else if (!(this._companyName.equals(temp._companyName))) 
                    return false;
            }
            else if (temp._companyName != null)
                return false;
            if (this._clientLastName != null) {
                if (temp._clientLastName == null) return false;
                else if (!(this._clientLastName.equals(temp._clientLastName))) 
                    return false;
            }
            else if (temp._clientLastName != null)
                return false;
            if (this._clientFirstName != null) {
                if (temp._clientFirstName == null) return false;
                else if (!(this._clientFirstName.equals(temp._clientFirstName))) 
                    return false;
            }
            else if (temp._clientFirstName != null)
                return false;
            if (this._clientMiddleName != null) {
                if (temp._clientMiddleName == null) return false;
                else if (!(this._clientMiddleName.equals(temp._clientMiddleName))) 
                    return false;
            }
            else if (temp._clientMiddleName != null)
                return false;
            if (this._clientAddressLine1 != null) {
                if (temp._clientAddressLine1 == null) return false;
                else if (!(this._clientAddressLine1.equals(temp._clientAddressLine1))) 
                    return false;
            }
            else if (temp._clientAddressLine1 != null)
                return false;
            if (this._clientAddressLine2 != null) {
                if (temp._clientAddressLine2 == null) return false;
                else if (!(this._clientAddressLine2.equals(temp._clientAddressLine2))) 
                    return false;
            }
            else if (temp._clientAddressLine2 != null)
                return false;
            if (this._clientAddressLine3 != null) {
                if (temp._clientAddressLine3 == null) return false;
                else if (!(this._clientAddressLine3.equals(temp._clientAddressLine3))) 
                    return false;
            }
            else if (temp._clientAddressLine3 != null)
                return false;
            if (this._clientAddressLine4 != null) {
                if (temp._clientAddressLine4 == null) return false;
                else if (!(this._clientAddressLine4.equals(temp._clientAddressLine4))) 
                    return false;
            }
            else if (temp._clientAddressLine4 != null)
                return false;
            if (this._county != null) {
                if (temp._county == null) return false;
                else if (!(this._county.equals(temp._county))) 
                    return false;
            }
            else if (temp._county != null)
                return false;
            if (this._city != null) {
                if (temp._city == null) return false;
                else if (!(this._city.equals(temp._city))) 
                    return false;
            }
            else if (temp._city != null)
                return false;
            if (this._state != null) {
                if (temp._state == null) return false;
                else if (!(this._state.equals(temp._state))) 
                    return false;
            }
            else if (temp._state != null)
                return false;
            if (this._country != null) {
                if (temp._country == null) return false;
                else if (!(this._country.equals(temp._country))) 
                    return false;
            }
            else if (temp._country != null)
                return false;
            if (this._zipCode != null) {
                if (temp._zipCode == null) return false;
                else if (!(this._zipCode.equals(temp._zipCode))) 
                    return false;
            }
            else if (temp._zipCode != null)
                return false;
            if (this._filingStatus != null) {
                if (temp._filingStatus == null) return false;
                else if (!(this._filingStatus.equals(temp._filingStatus))) 
                    return false;
            }
            else if (temp._filingStatus != null)
                return false;
            if (this._fromDate != null) {
                if (temp._fromDate == null) return false;
                else if (!(this._fromDate.equals(temp._fromDate))) 
                    return false;
            }
            else if (temp._fromDate != null)
                return false;
            if (this._toDate != null) {
                if (temp._toDate == null) return false;
                else if (!(this._toDate.equals(temp._toDate))) 
                    return false;
            }
            else if (temp._toDate != null)
                return false;
            if (this._taxYear != temp._taxYear)
                return false;
            if (this._has_taxYear != temp._has_taxYear)
                return false;
            if (this._taxQualifier != null) {
                if (temp._taxQualifier == null) return false;
                else if (!(this._taxQualifier.equals(temp._taxQualifier))) 
                    return false;
            }
            else if (temp._taxQualifier != null)
                return false;
            if (this._taxFormVOList != null) {
                if (temp._taxFormVOList == null) return false;
                else if (!(this._taxFormVOList.equals(temp._taxFormVOList))) 
                    return false;
            }
            else if (temp._taxFormVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCityReturns the value of field 'city'.
     * 
     * @return the value of field 'city'.
     */
    public java.lang.String getCity()
    {
        return this._city;
    } //-- java.lang.String getCity() 

    /**
     * Method getClientAddressLine1Returns the value of field
     * 'clientAddressLine1'.
     * 
     * @return the value of field 'clientAddressLine1'.
     */
    public java.lang.String getClientAddressLine1()
    {
        return this._clientAddressLine1;
    } //-- java.lang.String getClientAddressLine1() 

    /**
     * Method getClientAddressLine2Returns the value of field
     * 'clientAddressLine2'.
     * 
     * @return the value of field 'clientAddressLine2'.
     */
    public java.lang.String getClientAddressLine2()
    {
        return this._clientAddressLine2;
    } //-- java.lang.String getClientAddressLine2() 

    /**
     * Method getClientAddressLine3Returns the value of field
     * 'clientAddressLine3'.
     * 
     * @return the value of field 'clientAddressLine3'.
     */
    public java.lang.String getClientAddressLine3()
    {
        return this._clientAddressLine3;
    } //-- java.lang.String getClientAddressLine3() 

    /**
     * Method getClientAddressLine4Returns the value of field
     * 'clientAddressLine4'.
     * 
     * @return the value of field 'clientAddressLine4'.
     */
    public java.lang.String getClientAddressLine4()
    {
        return this._clientAddressLine4;
    } //-- java.lang.String getClientAddressLine4() 

    /**
     * Method getClientFirstNameReturns the value of field
     * 'clientFirstName'.
     * 
     * @return the value of field 'clientFirstName'.
     */
    public java.lang.String getClientFirstName()
    {
        return this._clientFirstName;
    } //-- java.lang.String getClientFirstName() 

    /**
     * Method getClientLastNameReturns the value of field
     * 'clientLastName'.
     * 
     * @return the value of field 'clientLastName'.
     */
    public java.lang.String getClientLastName()
    {
        return this._clientLastName;
    } //-- java.lang.String getClientLastName() 

    /**
     * Method getClientMiddleNameReturns the value of field
     * 'clientMiddleName'.
     * 
     * @return the value of field 'clientMiddleName'.
     */
    public java.lang.String getClientMiddleName()
    {
        return this._clientMiddleName;
    } //-- java.lang.String getClientMiddleName() 

    /**
     * Method getCompanyNameReturns the value of field
     * 'companyName'.
     * 
     * @return the value of field 'companyName'.
     */
    public java.lang.String getCompanyName()
    {
        return this._companyName;
    } //-- java.lang.String getCompanyName() 

    /**
     * Method getContractNumberReturns the value of field
     * 'contractNumber'.
     * 
     * @return the value of field 'contractNumber'.
     */
    public java.lang.String getContractNumber()
    {
        return this._contractNumber;
    } //-- java.lang.String getContractNumber() 

    /**
     * Method getCountryReturns the value of field 'country'.
     * 
     * @return the value of field 'country'.
     */
    public java.lang.String getCountry()
    {
        return this._country;
    } //-- java.lang.String getCountry() 

    /**
     * Method getCountyReturns the value of field 'county'.
     * 
     * @return the value of field 'county'.
     */
    public java.lang.String getCounty()
    {
        return this._county;
    } //-- java.lang.String getCounty() 

    /**
     * Method getFilingStatusReturns the value of field
     * 'filingStatus'.
     * 
     * @return the value of field 'filingStatus'.
     */
    public java.lang.String getFilingStatus()
    {
        return this._filingStatus;
    } //-- java.lang.String getFilingStatus() 

    /**
     * Method getFromDateReturns the value of field 'fromDate'.
     * 
     * @return the value of field 'fromDate'.
     */
    public java.lang.String getFromDate()
    {
        return this._fromDate;
    } //-- java.lang.String getFromDate() 

    /**
     * Method getStateReturns the value of field 'state'.
     * 
     * @return the value of field 'state'.
     */
    public java.lang.String getState()
    {
        return this._state;
    } //-- java.lang.String getState() 

    /**
     * Method getTaxFormVO
     * 
     * @param index
     */
    public edit.common.vo.TaxFormVO getTaxFormVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _taxFormVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.TaxFormVO) _taxFormVOList.elementAt(index);
    } //-- edit.common.vo.TaxFormVO getTaxFormVO(int) 

    /**
     * Method getTaxFormVO
     */
    public edit.common.vo.TaxFormVO[] getTaxFormVO()
    {
        int size = _taxFormVOList.size();
        edit.common.vo.TaxFormVO[] mArray = new edit.common.vo.TaxFormVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.TaxFormVO) _taxFormVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.TaxFormVO[] getTaxFormVO() 

    /**
     * Method getTaxFormVOCount
     */
    public int getTaxFormVOCount()
    {
        return _taxFormVOList.size();
    } //-- int getTaxFormVOCount() 

    /**
     * Method getTaxQualifierReturns the value of field
     * 'taxQualifier'.
     * 
     * @return the value of field 'taxQualifier'.
     */
    public java.lang.String getTaxQualifier()
    {
        return this._taxQualifier;
    } //-- java.lang.String getTaxQualifier() 

    /**
     * Method getTaxYearReturns the value of field 'taxYear'.
     * 
     * @return the value of field 'taxYear'.
     */
    public int getTaxYear()
    {
        return this._taxYear;
    } //-- int getTaxYear() 

    /**
     * Method getToDateReturns the value of field 'toDate'.
     * 
     * @return the value of field 'toDate'.
     */
    public java.lang.String getToDate()
    {
        return this._toDate;
    } //-- java.lang.String getToDate() 

    /**
     * Method getZipCodeReturns the value of field 'zipCode'.
     * 
     * @return the value of field 'zipCode'.
     */
    public java.lang.String getZipCode()
    {
        return this._zipCode;
    } //-- java.lang.String getZipCode() 

    /**
     * Method hasTaxYear
     */
    public boolean hasTaxYear()
    {
        return this._has_taxYear;
    } //-- boolean hasTaxYear() 

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
     * Method removeAllTaxFormVO
     */
    public void removeAllTaxFormVO()
    {
        _taxFormVOList.removeAllElements();
    } //-- void removeAllTaxFormVO() 

    /**
     * Method removeTaxFormVO
     * 
     * @param index
     */
    public edit.common.vo.TaxFormVO removeTaxFormVO(int index)
    {
        java.lang.Object obj = _taxFormVOList.elementAt(index);
        _taxFormVOList.removeElementAt(index);
        return (edit.common.vo.TaxFormVO) obj;
    } //-- edit.common.vo.TaxFormVO removeTaxFormVO(int) 

    /**
     * Method setCitySets the value of field 'city'.
     * 
     * @param city the value of field 'city'.
     */
    public void setCity(java.lang.String city)
    {
        this._city = city;
        
        super.setVoChanged(true);
    } //-- void setCity(java.lang.String) 

    /**
     * Method setClientAddressLine1Sets the value of field
     * 'clientAddressLine1'.
     * 
     * @param clientAddressLine1 the value of field
     * 'clientAddressLine1'.
     */
    public void setClientAddressLine1(java.lang.String clientAddressLine1)
    {
        this._clientAddressLine1 = clientAddressLine1;
        
        super.setVoChanged(true);
    } //-- void setClientAddressLine1(java.lang.String) 

    /**
     * Method setClientAddressLine2Sets the value of field
     * 'clientAddressLine2'.
     * 
     * @param clientAddressLine2 the value of field
     * 'clientAddressLine2'.
     */
    public void setClientAddressLine2(java.lang.String clientAddressLine2)
    {
        this._clientAddressLine2 = clientAddressLine2;
        
        super.setVoChanged(true);
    } //-- void setClientAddressLine2(java.lang.String) 

    /**
     * Method setClientAddressLine3Sets the value of field
     * 'clientAddressLine3'.
     * 
     * @param clientAddressLine3 the value of field
     * 'clientAddressLine3'.
     */
    public void setClientAddressLine3(java.lang.String clientAddressLine3)
    {
        this._clientAddressLine3 = clientAddressLine3;
        
        super.setVoChanged(true);
    } //-- void setClientAddressLine3(java.lang.String) 

    /**
     * Method setClientAddressLine4Sets the value of field
     * 'clientAddressLine4'.
     * 
     * @param clientAddressLine4 the value of field
     * 'clientAddressLine4'.
     */
    public void setClientAddressLine4(java.lang.String clientAddressLine4)
    {
        this._clientAddressLine4 = clientAddressLine4;
        
        super.setVoChanged(true);
    } //-- void setClientAddressLine4(java.lang.String) 

    /**
     * Method setClientFirstNameSets the value of field
     * 'clientFirstName'.
     * 
     * @param clientFirstName the value of field 'clientFirstName'.
     */
    public void setClientFirstName(java.lang.String clientFirstName)
    {
        this._clientFirstName = clientFirstName;
        
        super.setVoChanged(true);
    } //-- void setClientFirstName(java.lang.String) 

    /**
     * Method setClientLastNameSets the value of field
     * 'clientLastName'.
     * 
     * @param clientLastName the value of field 'clientLastName'.
     */
    public void setClientLastName(java.lang.String clientLastName)
    {
        this._clientLastName = clientLastName;
        
        super.setVoChanged(true);
    } //-- void setClientLastName(java.lang.String) 

    /**
     * Method setClientMiddleNameSets the value of field
     * 'clientMiddleName'.
     * 
     * @param clientMiddleName the value of field 'clientMiddleName'
     */
    public void setClientMiddleName(java.lang.String clientMiddleName)
    {
        this._clientMiddleName = clientMiddleName;
        
        super.setVoChanged(true);
    } //-- void setClientMiddleName(java.lang.String) 

    /**
     * Method setCompanyNameSets the value of field 'companyName'.
     * 
     * @param companyName the value of field 'companyName'.
     */
    public void setCompanyName(java.lang.String companyName)
    {
        this._companyName = companyName;
        
        super.setVoChanged(true);
    } //-- void setCompanyName(java.lang.String) 

    /**
     * Method setContractNumberSets the value of field
     * 'contractNumber'.
     * 
     * @param contractNumber the value of field 'contractNumber'.
     */
    public void setContractNumber(java.lang.String contractNumber)
    {
        this._contractNumber = contractNumber;
        
        super.setVoChanged(true);
    } //-- void setContractNumber(java.lang.String) 

    /**
     * Method setCountrySets the value of field 'country'.
     * 
     * @param country the value of field 'country'.
     */
    public void setCountry(java.lang.String country)
    {
        this._country = country;
        
        super.setVoChanged(true);
    } //-- void setCountry(java.lang.String) 

    /**
     * Method setCountySets the value of field 'county'.
     * 
     * @param county the value of field 'county'.
     */
    public void setCounty(java.lang.String county)
    {
        this._county = county;
        
        super.setVoChanged(true);
    } //-- void setCounty(java.lang.String) 

    /**
     * Method setFilingStatusSets the value of field
     * 'filingStatus'.
     * 
     * @param filingStatus the value of field 'filingStatus'.
     */
    public void setFilingStatus(java.lang.String filingStatus)
    {
        this._filingStatus = filingStatus;
        
        super.setVoChanged(true);
    } //-- void setFilingStatus(java.lang.String) 

    /**
     * Method setFromDateSets the value of field 'fromDate'.
     * 
     * @param fromDate the value of field 'fromDate'.
     */
    public void setFromDate(java.lang.String fromDate)
    {
        this._fromDate = fromDate;
        
        super.setVoChanged(true);
    } //-- void setFromDate(java.lang.String) 

    /**
     * Method setStateSets the value of field 'state'.
     * 
     * @param state the value of field 'state'.
     */
    public void setState(java.lang.String state)
    {
        this._state = state;
        
        super.setVoChanged(true);
    } //-- void setState(java.lang.String) 

    /**
     * Method setTaxFormVO
     * 
     * @param index
     * @param vTaxFormVO
     */
    public void setTaxFormVO(int index, edit.common.vo.TaxFormVO vTaxFormVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _taxFormVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vTaxFormVO.setParentVO(this.getClass(), this);
        _taxFormVOList.setElementAt(vTaxFormVO, index);
    } //-- void setTaxFormVO(int, edit.common.vo.TaxFormVO) 

    /**
     * Method setTaxFormVO
     * 
     * @param taxFormVOArray
     */
    public void setTaxFormVO(edit.common.vo.TaxFormVO[] taxFormVOArray)
    {
        //-- copy array
        _taxFormVOList.removeAllElements();
        for (int i = 0; i < taxFormVOArray.length; i++) {
            taxFormVOArray[i].setParentVO(this.getClass(), this);
            _taxFormVOList.addElement(taxFormVOArray[i]);
        }
    } //-- void setTaxFormVO(edit.common.vo.TaxFormVO) 

    /**
     * Method setTaxQualifierSets the value of field
     * 'taxQualifier'.
     * 
     * @param taxQualifier the value of field 'taxQualifier'.
     */
    public void setTaxQualifier(java.lang.String taxQualifier)
    {
        this._taxQualifier = taxQualifier;
        
        super.setVoChanged(true);
    } //-- void setTaxQualifier(java.lang.String) 

    /**
     * Method setTaxYearSets the value of field 'taxYear'.
     * 
     * @param taxYear the value of field 'taxYear'.
     */
    public void setTaxYear(int taxYear)
    {
        this._taxYear = taxYear;
        
        super.setVoChanged(true);
        this._has_taxYear = true;
    } //-- void setTaxYear(int) 

    /**
     * Method setToDateSets the value of field 'toDate'.
     * 
     * @param toDate the value of field 'toDate'.
     */
    public void setToDate(java.lang.String toDate)
    {
        this._toDate = toDate;
        
        super.setVoChanged(true);
    } //-- void setToDate(java.lang.String) 

    /**
     * Method setZipCodeSets the value of field 'zipCode'.
     * 
     * @param zipCode the value of field 'zipCode'.
     */
    public void setZipCode(java.lang.String zipCode)
    {
        this._zipCode = zipCode;
        
        super.setVoChanged(true);
    } //-- void setZipCode(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.YearEndTaxVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.YearEndTaxVO) Unmarshaller.unmarshal(edit.common.vo.YearEndTaxVO.class, reader);
    } //-- edit.common.vo.YearEndTaxVO unmarshal(java.io.Reader) 

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
