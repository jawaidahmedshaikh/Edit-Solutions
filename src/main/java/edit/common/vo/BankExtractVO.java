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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class BankExtractVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _companyName
     */
    private java.lang.String _companyName;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _lastName
     */
    private java.lang.String _lastName;

    /**
     * Field _firstName
     */
    private java.lang.String _firstName;

    /**
     * Field _middleName
     */
    private java.lang.String _middleName;

    /**
     * Field _addressLine1
     */
    private java.lang.String _addressLine1;

    /**
     * Field _addressLine2
     */
    private java.lang.String _addressLine2;

    /**
     * Field _addressLine3
     */
    private java.lang.String _addressLine3;

    /**
     * Field _addressLine4
     */
    private java.lang.String _addressLine4;

    /**
     * Field _city
     */
    private java.lang.String _city;

    /**
     * Field _county
     */
    private java.lang.String _county;

    /**
     * Field _stateCT
     */
    private long _stateCT;

    /**
     * keeps track of state for field: _stateCT
     */
    private boolean _has_stateCT;

    /**
     * Field _countryCT
     */
    private long _countryCT;

    /**
     * keeps track of state for field: _countryCT
     */
    private boolean _has_countryCT;

    /**
     * Field _zipCode
     */
    private java.lang.String _zipCode;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _bankAccountNumber
     */
    private java.lang.String _bankAccountNumber;

    /**
     * Field _bankRoutingNumber
     */
    private java.lang.String _bankRoutingNumber;

    /**
     * Field _amount
     */
    private long _amount;

    /**
     * keeps track of state for field: _amount
     */
    private boolean _has_amount;


      //----------------/
     //- Constructors -/
    //----------------/

    public BankExtractVO() {
        super();
    } //-- edit.common.vo.BankExtractVO()


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
        
        if (obj instanceof BankExtractVO) {
        
            BankExtractVO temp = (BankExtractVO)obj;
            if (this._companyName != null) {
                if (temp._companyName == null) return false;
                else if (!(this._companyName.equals(temp._companyName))) 
                    return false;
            }
            else if (temp._companyName != null)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._lastName != null) {
                if (temp._lastName == null) return false;
                else if (!(this._lastName.equals(temp._lastName))) 
                    return false;
            }
            else if (temp._lastName != null)
                return false;
            if (this._firstName != null) {
                if (temp._firstName == null) return false;
                else if (!(this._firstName.equals(temp._firstName))) 
                    return false;
            }
            else if (temp._firstName != null)
                return false;
            if (this._middleName != null) {
                if (temp._middleName == null) return false;
                else if (!(this._middleName.equals(temp._middleName))) 
                    return false;
            }
            else if (temp._middleName != null)
                return false;
            if (this._addressLine1 != null) {
                if (temp._addressLine1 == null) return false;
                else if (!(this._addressLine1.equals(temp._addressLine1))) 
                    return false;
            }
            else if (temp._addressLine1 != null)
                return false;
            if (this._addressLine2 != null) {
                if (temp._addressLine2 == null) return false;
                else if (!(this._addressLine2.equals(temp._addressLine2))) 
                    return false;
            }
            else if (temp._addressLine2 != null)
                return false;
            if (this._addressLine3 != null) {
                if (temp._addressLine3 == null) return false;
                else if (!(this._addressLine3.equals(temp._addressLine3))) 
                    return false;
            }
            else if (temp._addressLine3 != null)
                return false;
            if (this._addressLine4 != null) {
                if (temp._addressLine4 == null) return false;
                else if (!(this._addressLine4.equals(temp._addressLine4))) 
                    return false;
            }
            else if (temp._addressLine4 != null)
                return false;
            if (this._city != null) {
                if (temp._city == null) return false;
                else if (!(this._city.equals(temp._city))) 
                    return false;
            }
            else if (temp._city != null)
                return false;
            if (this._county != null) {
                if (temp._county == null) return false;
                else if (!(this._county.equals(temp._county))) 
                    return false;
            }
            else if (temp._county != null)
                return false;
            if (this._stateCT != temp._stateCT)
                return false;
            if (this._has_stateCT != temp._has_stateCT)
                return false;
            if (this._countryCT != temp._countryCT)
                return false;
            if (this._has_countryCT != temp._has_countryCT)
                return false;
            if (this._zipCode != null) {
                if (temp._zipCode == null) return false;
                else if (!(this._zipCode.equals(temp._zipCode))) 
                    return false;
            }
            else if (temp._zipCode != null)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._bankAccountNumber != null) {
                if (temp._bankAccountNumber == null) return false;
                else if (!(this._bankAccountNumber.equals(temp._bankAccountNumber))) 
                    return false;
            }
            else if (temp._bankAccountNumber != null)
                return false;
            if (this._bankRoutingNumber != null) {
                if (temp._bankRoutingNumber == null) return false;
                else if (!(this._bankRoutingNumber.equals(temp._bankRoutingNumber))) 
                    return false;
            }
            else if (temp._bankRoutingNumber != null)
                return false;
            if (this._amount != temp._amount)
                return false;
            if (this._has_amount != temp._has_amount)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAddressLine1Returns the value of field
     * 'addressLine1'.
     * 
     * @return the value of field 'addressLine1'.
     */
    public java.lang.String getAddressLine1()
    {
        return this._addressLine1;
    } //-- java.lang.String getAddressLine1() 

    /**
     * Method getAddressLine2Returns the value of field
     * 'addressLine2'.
     * 
     * @return the value of field 'addressLine2'.
     */
    public java.lang.String getAddressLine2()
    {
        return this._addressLine2;
    } //-- java.lang.String getAddressLine2() 

    /**
     * Method getAddressLine3Returns the value of field
     * 'addressLine3'.
     * 
     * @return the value of field 'addressLine3'.
     */
    public java.lang.String getAddressLine3()
    {
        return this._addressLine3;
    } //-- java.lang.String getAddressLine3() 

    /**
     * Method getAddressLine4Returns the value of field
     * 'addressLine4'.
     * 
     * @return the value of field 'addressLine4'.
     */
    public java.lang.String getAddressLine4()
    {
        return this._addressLine4;
    } //-- java.lang.String getAddressLine4() 

    /**
     * Method getAmountReturns the value of field 'amount'.
     * 
     * @return the value of field 'amount'.
     */
    public long getAmount()
    {
        return this._amount;
    } //-- long getAmount() 

    /**
     * Method getBankAccountNumberReturns the value of field
     * 'bankAccountNumber'.
     * 
     * @return the value of field 'bankAccountNumber'.
     */
    public java.lang.String getBankAccountNumber()
    {
        return this._bankAccountNumber;
    } //-- java.lang.String getBankAccountNumber() 

    /**
     * Method getBankRoutingNumberReturns the value of field
     * 'bankRoutingNumber'.
     * 
     * @return the value of field 'bankRoutingNumber'.
     */
    public java.lang.String getBankRoutingNumber()
    {
        return this._bankRoutingNumber;
    } //-- java.lang.String getBankRoutingNumber() 

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
     * Method getCountryCTReturns the value of field 'countryCT'.
     * 
     * @return the value of field 'countryCT'.
     */
    public long getCountryCT()
    {
        return this._countryCT;
    } //-- long getCountryCT() 

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
     * Method getEffectiveDateReturns the value of field
     * 'effectiveDate'.
     * 
     * @return the value of field 'effectiveDate'.
     */
    public java.lang.String getEffectiveDate()
    {
        return this._effectiveDate;
    } //-- java.lang.String getEffectiveDate() 

    /**
     * Method getFirstNameReturns the value of field 'firstName'.
     * 
     * @return the value of field 'firstName'.
     */
    public java.lang.String getFirstName()
    {
        return this._firstName;
    } //-- java.lang.String getFirstName() 

    /**
     * Method getLastNameReturns the value of field 'lastName'.
     * 
     * @return the value of field 'lastName'.
     */
    public java.lang.String getLastName()
    {
        return this._lastName;
    } //-- java.lang.String getLastName() 

    /**
     * Method getMiddleNameReturns the value of field 'middleName'.
     * 
     * @return the value of field 'middleName'.
     */
    public java.lang.String getMiddleName()
    {
        return this._middleName;
    } //-- java.lang.String getMiddleName() 

    /**
     * Method getSegmentFKReturns the value of field 'segmentFK'.
     * 
     * @return the value of field 'segmentFK'.
     */
    public long getSegmentFK()
    {
        return this._segmentFK;
    } //-- long getSegmentFK() 

    /**
     * Method getStateCTReturns the value of field 'stateCT'.
     * 
     * @return the value of field 'stateCT'.
     */
    public long getStateCT()
    {
        return this._stateCT;
    } //-- long getStateCT() 

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
     * Method hasAmount
     */
    public boolean hasAmount()
    {
        return this._has_amount;
    } //-- boolean hasAmount() 

    /**
     * Method hasCountryCT
     */
    public boolean hasCountryCT()
    {
        return this._has_countryCT;
    } //-- boolean hasCountryCT() 

    /**
     * Method hasSegmentFK
     */
    public boolean hasSegmentFK()
    {
        return this._has_segmentFK;
    } //-- boolean hasSegmentFK() 

    /**
     * Method hasStateCT
     */
    public boolean hasStateCT()
    {
        return this._has_stateCT;
    } //-- boolean hasStateCT() 

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
     * Method setAddressLine1Sets the value of field
     * 'addressLine1'.
     * 
     * @param addressLine1 the value of field 'addressLine1'.
     */
    public void setAddressLine1(java.lang.String addressLine1)
    {
        this._addressLine1 = addressLine1;
        
        super.setVoChanged(true);
    } //-- void setAddressLine1(java.lang.String) 

    /**
     * Method setAddressLine2Sets the value of field
     * 'addressLine2'.
     * 
     * @param addressLine2 the value of field 'addressLine2'.
     */
    public void setAddressLine2(java.lang.String addressLine2)
    {
        this._addressLine2 = addressLine2;
        
        super.setVoChanged(true);
    } //-- void setAddressLine2(java.lang.String) 

    /**
     * Method setAddressLine3Sets the value of field
     * 'addressLine3'.
     * 
     * @param addressLine3 the value of field 'addressLine3'.
     */
    public void setAddressLine3(java.lang.String addressLine3)
    {
        this._addressLine3 = addressLine3;
        
        super.setVoChanged(true);
    } //-- void setAddressLine3(java.lang.String) 

    /**
     * Method setAddressLine4Sets the value of field
     * 'addressLine4'.
     * 
     * @param addressLine4 the value of field 'addressLine4'.
     */
    public void setAddressLine4(java.lang.String addressLine4)
    {
        this._addressLine4 = addressLine4;
        
        super.setVoChanged(true);
    } //-- void setAddressLine4(java.lang.String) 

    /**
     * Method setAmountSets the value of field 'amount'.
     * 
     * @param amount the value of field 'amount'.
     */
    public void setAmount(long amount)
    {
        this._amount = amount;
        
        super.setVoChanged(true);
        this._has_amount = true;
    } //-- void setAmount(long) 

    /**
     * Method setBankAccountNumberSets the value of field
     * 'bankAccountNumber'.
     * 
     * @param bankAccountNumber the value of field
     * 'bankAccountNumber'.
     */
    public void setBankAccountNumber(java.lang.String bankAccountNumber)
    {
        this._bankAccountNumber = bankAccountNumber;
        
        super.setVoChanged(true);
    } //-- void setBankAccountNumber(java.lang.String) 

    /**
     * Method setBankRoutingNumberSets the value of field
     * 'bankRoutingNumber'.
     * 
     * @param bankRoutingNumber the value of field
     * 'bankRoutingNumber'.
     */
    public void setBankRoutingNumber(java.lang.String bankRoutingNumber)
    {
        this._bankRoutingNumber = bankRoutingNumber;
        
        super.setVoChanged(true);
    } //-- void setBankRoutingNumber(java.lang.String) 

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
     * Method setCountryCTSets the value of field 'countryCT'.
     * 
     * @param countryCT the value of field 'countryCT'.
     */
    public void setCountryCT(long countryCT)
    {
        this._countryCT = countryCT;
        
        super.setVoChanged(true);
        this._has_countryCT = true;
    } //-- void setCountryCT(long) 

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
     * Method setEffectiveDateSets the value of field
     * 'effectiveDate'.
     * 
     * @param effectiveDate the value of field 'effectiveDate'.
     */
    public void setEffectiveDate(java.lang.String effectiveDate)
    {
        this._effectiveDate = effectiveDate;
        
        super.setVoChanged(true);
    } //-- void setEffectiveDate(java.lang.String) 

    /**
     * Method setFirstNameSets the value of field 'firstName'.
     * 
     * @param firstName the value of field 'firstName'.
     */
    public void setFirstName(java.lang.String firstName)
    {
        this._firstName = firstName;
        
        super.setVoChanged(true);
    } //-- void setFirstName(java.lang.String) 

    /**
     * Method setLastNameSets the value of field 'lastName'.
     * 
     * @param lastName the value of field 'lastName'.
     */
    public void setLastName(java.lang.String lastName)
    {
        this._lastName = lastName;
        
        super.setVoChanged(true);
    } //-- void setLastName(java.lang.String) 

    /**
     * Method setMiddleNameSets the value of field 'middleName'.
     * 
     * @param middleName the value of field 'middleName'.
     */
    public void setMiddleName(java.lang.String middleName)
    {
        this._middleName = middleName;
        
        super.setVoChanged(true);
    } //-- void setMiddleName(java.lang.String) 

    /**
     * Method setSegmentFKSets the value of field 'segmentFK'.
     * 
     * @param segmentFK the value of field 'segmentFK'.
     */
    public void setSegmentFK(long segmentFK)
    {
        this._segmentFK = segmentFK;
        
        super.setVoChanged(true);
        this._has_segmentFK = true;
    } //-- void setSegmentFK(long) 

    /**
     * Method setStateCTSets the value of field 'stateCT'.
     * 
     * @param stateCT the value of field 'stateCT'.
     */
    public void setStateCT(long stateCT)
    {
        this._stateCT = stateCT;
        
        super.setVoChanged(true);
        this._has_stateCT = true;
    } //-- void setStateCT(long) 

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
    public static edit.common.vo.BankExtractVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BankExtractVO) Unmarshaller.unmarshal(edit.common.vo.BankExtractVO.class, reader);
    } //-- edit.common.vo.BankExtractVO unmarshal(java.io.Reader) 

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
