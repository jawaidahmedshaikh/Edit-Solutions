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
 * Document to be fed into PRASE for the generation of GAAP
 * Reserves 
 * 
 * @version $Revision$ $Date$
 */
public class TaxExtract5498DetailVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _taxExtractPK
     */
    private long _taxExtractPK;

    /**
     * keeps track of state for field: _taxExtractPK
     */
    private boolean _has_taxExtractPK;

    /**
     * Field _qualNonQualIndicator
     */
    private java.lang.String _qualNonQualIndicator;

    /**
     * Field _qualifiedType
     */
    private java.lang.String _qualifiedType;

    /**
     * Field _marketingPackageName
     */
    private java.lang.String _marketingPackageName;

    /**
     * Field _businessContractName
     */
    private java.lang.String _businessContractName;

    /**
     * Field _ownerDateOfBirth
     */
    private java.lang.String _ownerDateOfBirth;

    /**
     * Field _ownerLastName
     */
    private java.lang.String _ownerLastName;

    /**
     * Field _ownerFirstName
     */
    private java.lang.String _ownerFirstName;

    /**
     * Field _ownerCorporateName
     */
    private java.lang.String _ownerCorporateName;

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
     * Field _state
     */
    private java.lang.String _state;

    /**
     * Field _zip
     */
    private java.lang.String _zip;

    /**
     * Field _taxForm
     */
    private java.lang.String _taxForm;

    /**
     * Field _taxYear
     */
    private java.lang.String _taxYear;

    /**
     * Field _taxIdType
     */
    private java.lang.String _taxIdType;

    /**
     * Field _taxID
     */
    private java.lang.String _taxID;

    /**
     * Field _contractNumber
     */
    private java.lang.String _contractNumber;

    /**
     * Field _accumValue
     */
    private java.lang.String _accumValue;

    /**
     * Field _accumulatedContribution
     */
    private java.lang.String _accumulatedContribution;

    /**
     * Field _accumulatedRollover
     */
    private java.lang.String _accumulatedRollover;

    /**
     * Field _accumulatedROTHConversion
     */
    private java.lang.String _accumulatedROTHConversion;

    /**
     * Field _seventyAndHalfInd
     */
    private java.lang.String _seventyAndHalfInd;


      //----------------/
     //- Constructors -/
    //----------------/

    public TaxExtract5498DetailVO() {
        super();
    } //-- edit.common.vo.TaxExtract5498DetailVO()


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
        
        if (obj instanceof TaxExtract5498DetailVO) {
        
            TaxExtract5498DetailVO temp = (TaxExtract5498DetailVO)obj;
            if (this._taxExtractPK != temp._taxExtractPK)
                return false;
            if (this._has_taxExtractPK != temp._has_taxExtractPK)
                return false;
            if (this._qualNonQualIndicator != null) {
                if (temp._qualNonQualIndicator == null) return false;
                else if (!(this._qualNonQualIndicator.equals(temp._qualNonQualIndicator))) 
                    return false;
            }
            else if (temp._qualNonQualIndicator != null)
                return false;
            if (this._qualifiedType != null) {
                if (temp._qualifiedType == null) return false;
                else if (!(this._qualifiedType.equals(temp._qualifiedType))) 
                    return false;
            }
            else if (temp._qualifiedType != null)
                return false;
            if (this._marketingPackageName != null) {
                if (temp._marketingPackageName == null) return false;
                else if (!(this._marketingPackageName.equals(temp._marketingPackageName))) 
                    return false;
            }
            else if (temp._marketingPackageName != null)
                return false;
            if (this._businessContractName != null) {
                if (temp._businessContractName == null) return false;
                else if (!(this._businessContractName.equals(temp._businessContractName))) 
                    return false;
            }
            else if (temp._businessContractName != null)
                return false;
            if (this._ownerDateOfBirth != null) {
                if (temp._ownerDateOfBirth == null) return false;
                else if (!(this._ownerDateOfBirth.equals(temp._ownerDateOfBirth))) 
                    return false;
            }
            else if (temp._ownerDateOfBirth != null)
                return false;
            if (this._ownerLastName != null) {
                if (temp._ownerLastName == null) return false;
                else if (!(this._ownerLastName.equals(temp._ownerLastName))) 
                    return false;
            }
            else if (temp._ownerLastName != null)
                return false;
            if (this._ownerFirstName != null) {
                if (temp._ownerFirstName == null) return false;
                else if (!(this._ownerFirstName.equals(temp._ownerFirstName))) 
                    return false;
            }
            else if (temp._ownerFirstName != null)
                return false;
            if (this._ownerCorporateName != null) {
                if (temp._ownerCorporateName == null) return false;
                else if (!(this._ownerCorporateName.equals(temp._ownerCorporateName))) 
                    return false;
            }
            else if (temp._ownerCorporateName != null)
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
            if (this._state != null) {
                if (temp._state == null) return false;
                else if (!(this._state.equals(temp._state))) 
                    return false;
            }
            else if (temp._state != null)
                return false;
            if (this._zip != null) {
                if (temp._zip == null) return false;
                else if (!(this._zip.equals(temp._zip))) 
                    return false;
            }
            else if (temp._zip != null)
                return false;
            if (this._taxForm != null) {
                if (temp._taxForm == null) return false;
                else if (!(this._taxForm.equals(temp._taxForm))) 
                    return false;
            }
            else if (temp._taxForm != null)
                return false;
            if (this._taxYear != null) {
                if (temp._taxYear == null) return false;
                else if (!(this._taxYear.equals(temp._taxYear))) 
                    return false;
            }
            else if (temp._taxYear != null)
                return false;
            if (this._taxIdType != null) {
                if (temp._taxIdType == null) return false;
                else if (!(this._taxIdType.equals(temp._taxIdType))) 
                    return false;
            }
            else if (temp._taxIdType != null)
                return false;
            if (this._taxID != null) {
                if (temp._taxID == null) return false;
                else if (!(this._taxID.equals(temp._taxID))) 
                    return false;
            }
            else if (temp._taxID != null)
                return false;
            if (this._contractNumber != null) {
                if (temp._contractNumber == null) return false;
                else if (!(this._contractNumber.equals(temp._contractNumber))) 
                    return false;
            }
            else if (temp._contractNumber != null)
                return false;
            if (this._accumValue != null) {
                if (temp._accumValue == null) return false;
                else if (!(this._accumValue.equals(temp._accumValue))) 
                    return false;
            }
            else if (temp._accumValue != null)
                return false;
            if (this._accumulatedContribution != null) {
                if (temp._accumulatedContribution == null) return false;
                else if (!(this._accumulatedContribution.equals(temp._accumulatedContribution))) 
                    return false;
            }
            else if (temp._accumulatedContribution != null)
                return false;
            if (this._accumulatedRollover != null) {
                if (temp._accumulatedRollover == null) return false;
                else if (!(this._accumulatedRollover.equals(temp._accumulatedRollover))) 
                    return false;
            }
            else if (temp._accumulatedRollover != null)
                return false;
            if (this._accumulatedROTHConversion != null) {
                if (temp._accumulatedROTHConversion == null) return false;
                else if (!(this._accumulatedROTHConversion.equals(temp._accumulatedROTHConversion))) 
                    return false;
            }
            else if (temp._accumulatedROTHConversion != null)
                return false;
            if (this._seventyAndHalfInd != null) {
                if (temp._seventyAndHalfInd == null) return false;
                else if (!(this._seventyAndHalfInd.equals(temp._seventyAndHalfInd))) 
                    return false;
            }
            else if (temp._seventyAndHalfInd != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccumValueReturns the value of field 'accumValue'.
     * 
     * @return the value of field 'accumValue'.
     */
    public java.lang.String getAccumValue()
    {
        return this._accumValue;
    } //-- java.lang.String getAccumValue() 

    /**
     * Method getAccumulatedContributionReturns the value of field
     * 'accumulatedContribution'.
     * 
     * @return the value of field 'accumulatedContribution'.
     */
    public java.lang.String getAccumulatedContribution()
    {
        return this._accumulatedContribution;
    } //-- java.lang.String getAccumulatedContribution() 

    /**
     * Method getAccumulatedROTHConversionReturns the value of
     * field 'accumulatedROTHConversion'.
     * 
     * @return the value of field 'accumulatedROTHConversion'.
     */
    public java.lang.String getAccumulatedROTHConversion()
    {
        return this._accumulatedROTHConversion;
    } //-- java.lang.String getAccumulatedROTHConversion() 

    /**
     * Method getAccumulatedRolloverReturns the value of field
     * 'accumulatedRollover'.
     * 
     * @return the value of field 'accumulatedRollover'.
     */
    public java.lang.String getAccumulatedRollover()
    {
        return this._accumulatedRollover;
    } //-- java.lang.String getAccumulatedRollover() 

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
     * Method getBusinessContractNameReturns the value of field
     * 'businessContractName'.
     * 
     * @return the value of field 'businessContractName'.
     */
    public java.lang.String getBusinessContractName()
    {
        return this._businessContractName;
    } //-- java.lang.String getBusinessContractName() 

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
     * Method getMarketingPackageNameReturns the value of field
     * 'marketingPackageName'.
     * 
     * @return the value of field 'marketingPackageName'.
     */
    public java.lang.String getMarketingPackageName()
    {
        return this._marketingPackageName;
    } //-- java.lang.String getMarketingPackageName() 

    /**
     * Method getOwnerCorporateNameReturns the value of field
     * 'ownerCorporateName'.
     * 
     * @return the value of field 'ownerCorporateName'.
     */
    public java.lang.String getOwnerCorporateName()
    {
        return this._ownerCorporateName;
    } //-- java.lang.String getOwnerCorporateName() 

    /**
     * Method getOwnerDateOfBirthReturns the value of field
     * 'ownerDateOfBirth'.
     * 
     * @return the value of field 'ownerDateOfBirth'.
     */
    public java.lang.String getOwnerDateOfBirth()
    {
        return this._ownerDateOfBirth;
    } //-- java.lang.String getOwnerDateOfBirth() 

    /**
     * Method getOwnerFirstNameReturns the value of field
     * 'ownerFirstName'.
     * 
     * @return the value of field 'ownerFirstName'.
     */
    public java.lang.String getOwnerFirstName()
    {
        return this._ownerFirstName;
    } //-- java.lang.String getOwnerFirstName() 

    /**
     * Method getOwnerLastNameReturns the value of field
     * 'ownerLastName'.
     * 
     * @return the value of field 'ownerLastName'.
     */
    public java.lang.String getOwnerLastName()
    {
        return this._ownerLastName;
    } //-- java.lang.String getOwnerLastName() 

    /**
     * Method getQualNonQualIndicatorReturns the value of field
     * 'qualNonQualIndicator'.
     * 
     * @return the value of field 'qualNonQualIndicator'.
     */
    public java.lang.String getQualNonQualIndicator()
    {
        return this._qualNonQualIndicator;
    } //-- java.lang.String getQualNonQualIndicator() 

    /**
     * Method getQualifiedTypeReturns the value of field
     * 'qualifiedType'.
     * 
     * @return the value of field 'qualifiedType'.
     */
    public java.lang.String getQualifiedType()
    {
        return this._qualifiedType;
    } //-- java.lang.String getQualifiedType() 

    /**
     * Method getSeventyAndHalfIndReturns the value of field
     * 'seventyAndHalfInd'.
     * 
     * @return the value of field 'seventyAndHalfInd'.
     */
    public java.lang.String getSeventyAndHalfInd()
    {
        return this._seventyAndHalfInd;
    } //-- java.lang.String getSeventyAndHalfInd() 

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
     * Method getTaxExtractPKReturns the value of field
     * 'taxExtractPK'.
     * 
     * @return the value of field 'taxExtractPK'.
     */
    public long getTaxExtractPK()
    {
        return this._taxExtractPK;
    } //-- long getTaxExtractPK() 

    /**
     * Method getTaxFormReturns the value of field 'taxForm'.
     * 
     * @return the value of field 'taxForm'.
     */
    public java.lang.String getTaxForm()
    {
        return this._taxForm;
    } //-- java.lang.String getTaxForm() 

    /**
     * Method getTaxIDReturns the value of field 'taxID'.
     * 
     * @return the value of field 'taxID'.
     */
    public java.lang.String getTaxID()
    {
        return this._taxID;
    } //-- java.lang.String getTaxID() 

    /**
     * Method getTaxIdTypeReturns the value of field 'taxIdType'.
     * 
     * @return the value of field 'taxIdType'.
     */
    public java.lang.String getTaxIdType()
    {
        return this._taxIdType;
    } //-- java.lang.String getTaxIdType() 

    /**
     * Method getTaxYearReturns the value of field 'taxYear'.
     * 
     * @return the value of field 'taxYear'.
     */
    public java.lang.String getTaxYear()
    {
        return this._taxYear;
    } //-- java.lang.String getTaxYear() 

    /**
     * Method getZipReturns the value of field 'zip'.
     * 
     * @return the value of field 'zip'.
     */
    public java.lang.String getZip()
    {
        return this._zip;
    } //-- java.lang.String getZip() 

    /**
     * Method hasTaxExtractPK
     */
    public boolean hasTaxExtractPK()
    {
        return this._has_taxExtractPK;
    } //-- boolean hasTaxExtractPK() 

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
     * Method setAccumValueSets the value of field 'accumValue'.
     * 
     * @param accumValue the value of field 'accumValue'.
     */
    public void setAccumValue(java.lang.String accumValue)
    {
        this._accumValue = accumValue;
        
        super.setVoChanged(true);
    } //-- void setAccumValue(java.lang.String) 

    /**
     * Method setAccumulatedContributionSets the value of field
     * 'accumulatedContribution'.
     * 
     * @param accumulatedContribution the value of field
     * 'accumulatedContribution'.
     */
    public void setAccumulatedContribution(java.lang.String accumulatedContribution)
    {
        this._accumulatedContribution = accumulatedContribution;
        
        super.setVoChanged(true);
    } //-- void setAccumulatedContribution(java.lang.String) 

    /**
     * Method setAccumulatedROTHConversionSets the value of field
     * 'accumulatedROTHConversion'.
     * 
     * @param accumulatedROTHConversion the value of field
     * 'accumulatedROTHConversion'.
     */
    public void setAccumulatedROTHConversion(java.lang.String accumulatedROTHConversion)
    {
        this._accumulatedROTHConversion = accumulatedROTHConversion;
        
        super.setVoChanged(true);
    } //-- void setAccumulatedROTHConversion(java.lang.String) 

    /**
     * Method setAccumulatedRolloverSets the value of field
     * 'accumulatedRollover'.
     * 
     * @param accumulatedRollover the value of field
     * 'accumulatedRollover'.
     */
    public void setAccumulatedRollover(java.lang.String accumulatedRollover)
    {
        this._accumulatedRollover = accumulatedRollover;
        
        super.setVoChanged(true);
    } //-- void setAccumulatedRollover(java.lang.String) 

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
     * Method setBusinessContractNameSets the value of field
     * 'businessContractName'.
     * 
     * @param businessContractName the value of field
     * 'businessContractName'.
     */
    public void setBusinessContractName(java.lang.String businessContractName)
    {
        this._businessContractName = businessContractName;
        
        super.setVoChanged(true);
    } //-- void setBusinessContractName(java.lang.String) 

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
     * Method setMarketingPackageNameSets the value of field
     * 'marketingPackageName'.
     * 
     * @param marketingPackageName the value of field
     * 'marketingPackageName'.
     */
    public void setMarketingPackageName(java.lang.String marketingPackageName)
    {
        this._marketingPackageName = marketingPackageName;
        
        super.setVoChanged(true);
    } //-- void setMarketingPackageName(java.lang.String) 

    /**
     * Method setOwnerCorporateNameSets the value of field
     * 'ownerCorporateName'.
     * 
     * @param ownerCorporateName the value of field
     * 'ownerCorporateName'.
     */
    public void setOwnerCorporateName(java.lang.String ownerCorporateName)
    {
        this._ownerCorporateName = ownerCorporateName;
        
        super.setVoChanged(true);
    } //-- void setOwnerCorporateName(java.lang.String) 

    /**
     * Method setOwnerDateOfBirthSets the value of field
     * 'ownerDateOfBirth'.
     * 
     * @param ownerDateOfBirth the value of field 'ownerDateOfBirth'
     */
    public void setOwnerDateOfBirth(java.lang.String ownerDateOfBirth)
    {
        this._ownerDateOfBirth = ownerDateOfBirth;
        
        super.setVoChanged(true);
    } //-- void setOwnerDateOfBirth(java.lang.String) 

    /**
     * Method setOwnerFirstNameSets the value of field
     * 'ownerFirstName'.
     * 
     * @param ownerFirstName the value of field 'ownerFirstName'.
     */
    public void setOwnerFirstName(java.lang.String ownerFirstName)
    {
        this._ownerFirstName = ownerFirstName;
        
        super.setVoChanged(true);
    } //-- void setOwnerFirstName(java.lang.String) 

    /**
     * Method setOwnerLastNameSets the value of field
     * 'ownerLastName'.
     * 
     * @param ownerLastName the value of field 'ownerLastName'.
     */
    public void setOwnerLastName(java.lang.String ownerLastName)
    {
        this._ownerLastName = ownerLastName;
        
        super.setVoChanged(true);
    } //-- void setOwnerLastName(java.lang.String) 

    /**
     * Method setQualNonQualIndicatorSets the value of field
     * 'qualNonQualIndicator'.
     * 
     * @param qualNonQualIndicator the value of field
     * 'qualNonQualIndicator'.
     */
    public void setQualNonQualIndicator(java.lang.String qualNonQualIndicator)
    {
        this._qualNonQualIndicator = qualNonQualIndicator;
        
        super.setVoChanged(true);
    } //-- void setQualNonQualIndicator(java.lang.String) 

    /**
     * Method setQualifiedTypeSets the value of field
     * 'qualifiedType'.
     * 
     * @param qualifiedType the value of field 'qualifiedType'.
     */
    public void setQualifiedType(java.lang.String qualifiedType)
    {
        this._qualifiedType = qualifiedType;
        
        super.setVoChanged(true);
    } //-- void setQualifiedType(java.lang.String) 

    /**
     * Method setSeventyAndHalfIndSets the value of field
     * 'seventyAndHalfInd'.
     * 
     * @param seventyAndHalfInd the value of field
     * 'seventyAndHalfInd'.
     */
    public void setSeventyAndHalfInd(java.lang.String seventyAndHalfInd)
    {
        this._seventyAndHalfInd = seventyAndHalfInd;
        
        super.setVoChanged(true);
    } //-- void setSeventyAndHalfInd(java.lang.String) 

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
     * Method setTaxExtractPKSets the value of field
     * 'taxExtractPK'.
     * 
     * @param taxExtractPK the value of field 'taxExtractPK'.
     */
    public void setTaxExtractPK(long taxExtractPK)
    {
        this._taxExtractPK = taxExtractPK;
        
        super.setVoChanged(true);
        this._has_taxExtractPK = true;
    } //-- void setTaxExtractPK(long) 

    /**
     * Method setTaxFormSets the value of field 'taxForm'.
     * 
     * @param taxForm the value of field 'taxForm'.
     */
    public void setTaxForm(java.lang.String taxForm)
    {
        this._taxForm = taxForm;
        
        super.setVoChanged(true);
    } //-- void setTaxForm(java.lang.String) 

    /**
     * Method setTaxIDSets the value of field 'taxID'.
     * 
     * @param taxID the value of field 'taxID'.
     */
    public void setTaxID(java.lang.String taxID)
    {
        this._taxID = taxID;
        
        super.setVoChanged(true);
    } //-- void setTaxID(java.lang.String) 

    /**
     * Method setTaxIdTypeSets the value of field 'taxIdType'.
     * 
     * @param taxIdType the value of field 'taxIdType'.
     */
    public void setTaxIdType(java.lang.String taxIdType)
    {
        this._taxIdType = taxIdType;
        
        super.setVoChanged(true);
    } //-- void setTaxIdType(java.lang.String) 

    /**
     * Method setTaxYearSets the value of field 'taxYear'.
     * 
     * @param taxYear the value of field 'taxYear'.
     */
    public void setTaxYear(java.lang.String taxYear)
    {
        this._taxYear = taxYear;
        
        super.setVoChanged(true);
    } //-- void setTaxYear(java.lang.String) 

    /**
     * Method setZipSets the value of field 'zip'.
     * 
     * @param zip the value of field 'zip'.
     */
    public void setZip(java.lang.String zip)
    {
        this._zip = zip;
        
        super.setVoChanged(true);
    } //-- void setZip(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.TaxExtract5498DetailVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.TaxExtract5498DetailVO) Unmarshaller.unmarshal(edit.common.vo.TaxExtract5498DetailVO.class, reader);
    } //-- edit.common.vo.TaxExtract5498DetailVO unmarshal(java.io.Reader) 

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