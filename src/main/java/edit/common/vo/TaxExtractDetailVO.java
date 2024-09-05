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
public class TaxExtractDetailVO extends edit.common.vo.VOObject  
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
     * Field _transactionType
     */
    private java.lang.String _transactionType;

    /**
     * Field _processDate
     */
    private java.lang.String _processDate;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

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
     * Field _payeeLastName
     */
    private java.lang.String _payeeLastName;

    /**
     * Field _payeeFirstName
     */
    private java.lang.String _payeeFirstName;

    /**
     * Field _payeeCorporateName
     */
    private java.lang.String _payeeCorporateName;

    /**
     * Field _ownerLastName
     */
    private java.lang.String _ownerLastName;

    /**
     * Field _ownerFirstName
     */
    private java.lang.String _ownerFirstName;

    /**
     * Field _netAmount
     */
    private java.lang.String _netAmount;

    /**
     * Field _taxForm
     */
    private java.lang.String _taxForm;

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
     * Field _grossAmount
     */
    private java.lang.String _grossAmount;

    /**
     * Field _taxableBenefit
     */
    private java.lang.String _taxableBenefit;

    /**
     * Field _federalWithholdingAmount
     */
    private java.lang.String _federalWithholdingAmount;

    /**
     * Field _stateWithholdingAmount
     */
    private java.lang.String _stateWithholdingAmount;

    /**
     * Field _localWithholdingAmount
     */
    private java.lang.String _localWithholdingAmount;

    /**
     * Field _accumValue
     */
    private java.lang.String _accumValue;

    /**
     * Field _distributionCode
     */
    private java.lang.String _distributionCode;

    /**
     * Field _contractClientAllocationPct
     */
    private java.lang.String _contractClientAllocationPct;

    /**
     * Field _reduceTaxable
     */
    private java.lang.String _reduceTaxable;

    /**
     * Field _depositType
     */
    private java.lang.String _depositType;

    /**
     * Field _agentNumber
     */
    private java.lang.String _agentNumber;

    /**
     * Field _amount51099R
     */
    private java.lang.String _amount51099R;


      //----------------/
     //- Constructors -/
    //----------------/

    public TaxExtractDetailVO() {
        super();
    } //-- edit.common.vo.TaxExtractDetailVO()


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
        
        if (obj instanceof TaxExtractDetailVO) {
        
            TaxExtractDetailVO temp = (TaxExtractDetailVO)obj;
            if (this._taxExtractPK != temp._taxExtractPK)
                return false;
            if (this._has_taxExtractPK != temp._has_taxExtractPK)
                return false;
            if (this._transactionType != null) {
                if (temp._transactionType == null) return false;
                else if (!(this._transactionType.equals(temp._transactionType))) 
                    return false;
            }
            else if (temp._transactionType != null)
                return false;
            if (this._processDate != null) {
                if (temp._processDate == null) return false;
                else if (!(this._processDate.equals(temp._processDate))) 
                    return false;
            }
            else if (temp._processDate != null)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
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
            if (this._payeeLastName != null) {
                if (temp._payeeLastName == null) return false;
                else if (!(this._payeeLastName.equals(temp._payeeLastName))) 
                    return false;
            }
            else if (temp._payeeLastName != null)
                return false;
            if (this._payeeFirstName != null) {
                if (temp._payeeFirstName == null) return false;
                else if (!(this._payeeFirstName.equals(temp._payeeFirstName))) 
                    return false;
            }
            else if (temp._payeeFirstName != null)
                return false;
            if (this._payeeCorporateName != null) {
                if (temp._payeeCorporateName == null) return false;
                else if (!(this._payeeCorporateName.equals(temp._payeeCorporateName))) 
                    return false;
            }
            else if (temp._payeeCorporateName != null)
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
            if (this._netAmount != null) {
                if (temp._netAmount == null) return false;
                else if (!(this._netAmount.equals(temp._netAmount))) 
                    return false;
            }
            else if (temp._netAmount != null)
                return false;
            if (this._taxForm != null) {
                if (temp._taxForm == null) return false;
                else if (!(this._taxForm.equals(temp._taxForm))) 
                    return false;
            }
            else if (temp._taxForm != null)
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
            if (this._grossAmount != null) {
                if (temp._grossAmount == null) return false;
                else if (!(this._grossAmount.equals(temp._grossAmount))) 
                    return false;
            }
            else if (temp._grossAmount != null)
                return false;
            if (this._taxableBenefit != null) {
                if (temp._taxableBenefit == null) return false;
                else if (!(this._taxableBenefit.equals(temp._taxableBenefit))) 
                    return false;
            }
            else if (temp._taxableBenefit != null)
                return false;
            if (this._federalWithholdingAmount != null) {
                if (temp._federalWithholdingAmount == null) return false;
                else if (!(this._federalWithholdingAmount.equals(temp._federalWithholdingAmount))) 
                    return false;
            }
            else if (temp._federalWithholdingAmount != null)
                return false;
            if (this._stateWithholdingAmount != null) {
                if (temp._stateWithholdingAmount == null) return false;
                else if (!(this._stateWithholdingAmount.equals(temp._stateWithholdingAmount))) 
                    return false;
            }
            else if (temp._stateWithholdingAmount != null)
                return false;
            if (this._localWithholdingAmount != null) {
                if (temp._localWithholdingAmount == null) return false;
                else if (!(this._localWithholdingAmount.equals(temp._localWithholdingAmount))) 
                    return false;
            }
            else if (temp._localWithholdingAmount != null)
                return false;
            if (this._accumValue != null) {
                if (temp._accumValue == null) return false;
                else if (!(this._accumValue.equals(temp._accumValue))) 
                    return false;
            }
            else if (temp._accumValue != null)
                return false;
            if (this._distributionCode != null) {
                if (temp._distributionCode == null) return false;
                else if (!(this._distributionCode.equals(temp._distributionCode))) 
                    return false;
            }
            else if (temp._distributionCode != null)
                return false;
            if (this._contractClientAllocationPct != null) {
                if (temp._contractClientAllocationPct == null) return false;
                else if (!(this._contractClientAllocationPct.equals(temp._contractClientAllocationPct))) 
                    return false;
            }
            else if (temp._contractClientAllocationPct != null)
                return false;
            if (this._reduceTaxable != null) {
                if (temp._reduceTaxable == null) return false;
                else if (!(this._reduceTaxable.equals(temp._reduceTaxable))) 
                    return false;
            }
            else if (temp._reduceTaxable != null)
                return false;
            if (this._depositType != null) {
                if (temp._depositType == null) return false;
                else if (!(this._depositType.equals(temp._depositType))) 
                    return false;
            }
            else if (temp._depositType != null)
                return false;
            if (this._agentNumber != null) {
                if (temp._agentNumber == null) return false;
                else if (!(this._agentNumber.equals(temp._agentNumber))) 
                    return false;
            }
            else if (temp._agentNumber != null)
                return false;
            if (this._amount51099R != null) {
                if (temp._amount51099R == null) return false;
                else if (!(this._amount51099R.equals(temp._amount51099R))) 
                    return false;
            }
            else if (temp._amount51099R != null)
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
     * Method getAgentNumberReturns the value of field
     * 'agentNumber'.
     * 
     * @return the value of field 'agentNumber'.
     */
    public java.lang.String getAgentNumber()
    {
        return this._agentNumber;
    } //-- java.lang.String getAgentNumber() 

    /**
     * Method getAmount51099RReturns the value of field
     * 'amount51099R'.
     * 
     * @return the value of field 'amount51099R'.
     */
    public java.lang.String getAmount51099R()
    {
        return this._amount51099R;
    } //-- java.lang.String getAmount51099R() 

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
     * Method getContractClientAllocationPctReturns the value of
     * field 'contractClientAllocationPct'.
     * 
     * @return the value of field 'contractClientAllocationPct'.
     */
    public java.lang.String getContractClientAllocationPct()
    {
        return this._contractClientAllocationPct;
    } //-- java.lang.String getContractClientAllocationPct() 

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
     * Method getDepositTypeReturns the value of field
     * 'depositType'.
     * 
     * @return the value of field 'depositType'.
     */
    public java.lang.String getDepositType()
    {
        return this._depositType;
    } //-- java.lang.String getDepositType() 

    /**
     * Method getDistributionCodeReturns the value of field
     * 'distributionCode'.
     * 
     * @return the value of field 'distributionCode'.
     */
    public java.lang.String getDistributionCode()
    {
        return this._distributionCode;
    } //-- java.lang.String getDistributionCode() 

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
     * Method getFederalWithholdingAmountReturns the value of field
     * 'federalWithholdingAmount'.
     * 
     * @return the value of field 'federalWithholdingAmount'.
     */
    public java.lang.String getFederalWithholdingAmount()
    {
        return this._federalWithholdingAmount;
    } //-- java.lang.String getFederalWithholdingAmount() 

    /**
     * Method getGrossAmountReturns the value of field
     * 'grossAmount'.
     * 
     * @return the value of field 'grossAmount'.
     */
    public java.lang.String getGrossAmount()
    {
        return this._grossAmount;
    } //-- java.lang.String getGrossAmount() 

    /**
     * Method getLocalWithholdingAmountReturns the value of field
     * 'localWithholdingAmount'.
     * 
     * @return the value of field 'localWithholdingAmount'.
     */
    public java.lang.String getLocalWithholdingAmount()
    {
        return this._localWithholdingAmount;
    } //-- java.lang.String getLocalWithholdingAmount() 

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
     * Method getNetAmountReturns the value of field 'netAmount'.
     * 
     * @return the value of field 'netAmount'.
     */
    public java.lang.String getNetAmount()
    {
        return this._netAmount;
    } //-- java.lang.String getNetAmount() 

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
     * Method getPayeeCorporateNameReturns the value of field
     * 'payeeCorporateName'.
     * 
     * @return the value of field 'payeeCorporateName'.
     */
    public java.lang.String getPayeeCorporateName()
    {
        return this._payeeCorporateName;
    } //-- java.lang.String getPayeeCorporateName() 

    /**
     * Method getPayeeFirstNameReturns the value of field
     * 'payeeFirstName'.
     * 
     * @return the value of field 'payeeFirstName'.
     */
    public java.lang.String getPayeeFirstName()
    {
        return this._payeeFirstName;
    } //-- java.lang.String getPayeeFirstName() 

    /**
     * Method getPayeeLastNameReturns the value of field
     * 'payeeLastName'.
     * 
     * @return the value of field 'payeeLastName'.
     */
    public java.lang.String getPayeeLastName()
    {
        return this._payeeLastName;
    } //-- java.lang.String getPayeeLastName() 

    /**
     * Method getProcessDateReturns the value of field
     * 'processDate'.
     * 
     * @return the value of field 'processDate'.
     */
    public java.lang.String getProcessDate()
    {
        return this._processDate;
    } //-- java.lang.String getProcessDate() 

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
     * Method getReduceTaxableReturns the value of field
     * 'reduceTaxable'.
     * 
     * @return the value of field 'reduceTaxable'.
     */
    public java.lang.String getReduceTaxable()
    {
        return this._reduceTaxable;
    } //-- java.lang.String getReduceTaxable() 

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
     * Method getStateWithholdingAmountReturns the value of field
     * 'stateWithholdingAmount'.
     * 
     * @return the value of field 'stateWithholdingAmount'.
     */
    public java.lang.String getStateWithholdingAmount()
    {
        return this._stateWithholdingAmount;
    } //-- java.lang.String getStateWithholdingAmount() 

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
     * Method getTaxableBenefitReturns the value of field
     * 'taxableBenefit'.
     * 
     * @return the value of field 'taxableBenefit'.
     */
    public java.lang.String getTaxableBenefit()
    {
        return this._taxableBenefit;
    } //-- java.lang.String getTaxableBenefit() 

    /**
     * Method getTransactionTypeReturns the value of field
     * 'transactionType'.
     * 
     * @return the value of field 'transactionType'.
     */
    public java.lang.String getTransactionType()
    {
        return this._transactionType;
    } //-- java.lang.String getTransactionType() 

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
     * Method setAgentNumberSets the value of field 'agentNumber'.
     * 
     * @param agentNumber the value of field 'agentNumber'.
     */
    public void setAgentNumber(java.lang.String agentNumber)
    {
        this._agentNumber = agentNumber;
        
        super.setVoChanged(true);
    } //-- void setAgentNumber(java.lang.String) 

    /**
     * Method setAmount51099RSets the value of field
     * 'amount51099R'.
     * 
     * @param amount51099R the value of field 'amount51099R'.
     */
    public void setAmount51099R(java.lang.String amount51099R)
    {
        this._amount51099R = amount51099R;
        
        super.setVoChanged(true);
    } //-- void setAmount51099R(java.lang.String) 

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
     * Method setContractClientAllocationPctSets the value of field
     * 'contractClientAllocationPct'.
     * 
     * @param contractClientAllocationPct the value of field
     * 'contractClientAllocationPct'.
     */
    public void setContractClientAllocationPct(java.lang.String contractClientAllocationPct)
    {
        this._contractClientAllocationPct = contractClientAllocationPct;
        
        super.setVoChanged(true);
    } //-- void setContractClientAllocationPct(java.lang.String) 

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
     * Method setDepositTypeSets the value of field 'depositType'.
     * 
     * @param depositType the value of field 'depositType'.
     */
    public void setDepositType(java.lang.String depositType)
    {
        this._depositType = depositType;
        
        super.setVoChanged(true);
    } //-- void setDepositType(java.lang.String) 

    /**
     * Method setDistributionCodeSets the value of field
     * 'distributionCode'.
     * 
     * @param distributionCode the value of field 'distributionCode'
     */
    public void setDistributionCode(java.lang.String distributionCode)
    {
        this._distributionCode = distributionCode;
        
        super.setVoChanged(true);
    } //-- void setDistributionCode(java.lang.String) 

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
     * Method setFederalWithholdingAmountSets the value of field
     * 'federalWithholdingAmount'.
     * 
     * @param federalWithholdingAmount the value of field
     * 'federalWithholdingAmount'.
     */
    public void setFederalWithholdingAmount(java.lang.String federalWithholdingAmount)
    {
        this._federalWithholdingAmount = federalWithholdingAmount;
        
        super.setVoChanged(true);
    } //-- void setFederalWithholdingAmount(java.lang.String) 

    /**
     * Method setGrossAmountSets the value of field 'grossAmount'.
     * 
     * @param grossAmount the value of field 'grossAmount'.
     */
    public void setGrossAmount(java.lang.String grossAmount)
    {
        this._grossAmount = grossAmount;
        
        super.setVoChanged(true);
    } //-- void setGrossAmount(java.lang.String) 

    /**
     * Method setLocalWithholdingAmountSets the value of field
     * 'localWithholdingAmount'.
     * 
     * @param localWithholdingAmount the value of field
     * 'localWithholdingAmount'.
     */
    public void setLocalWithholdingAmount(java.lang.String localWithholdingAmount)
    {
        this._localWithholdingAmount = localWithholdingAmount;
        
        super.setVoChanged(true);
    } //-- void setLocalWithholdingAmount(java.lang.String) 

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
     * Method setNetAmountSets the value of field 'netAmount'.
     * 
     * @param netAmount the value of field 'netAmount'.
     */
    public void setNetAmount(java.lang.String netAmount)
    {
        this._netAmount = netAmount;
        
        super.setVoChanged(true);
    } //-- void setNetAmount(java.lang.String) 

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
     * Method setPayeeCorporateNameSets the value of field
     * 'payeeCorporateName'.
     * 
     * @param payeeCorporateName the value of field
     * 'payeeCorporateName'.
     */
    public void setPayeeCorporateName(java.lang.String payeeCorporateName)
    {
        this._payeeCorporateName = payeeCorporateName;
        
        super.setVoChanged(true);
    } //-- void setPayeeCorporateName(java.lang.String) 

    /**
     * Method setPayeeFirstNameSets the value of field
     * 'payeeFirstName'.
     * 
     * @param payeeFirstName the value of field 'payeeFirstName'.
     */
    public void setPayeeFirstName(java.lang.String payeeFirstName)
    {
        this._payeeFirstName = payeeFirstName;
        
        super.setVoChanged(true);
    } //-- void setPayeeFirstName(java.lang.String) 

    /**
     * Method setPayeeLastNameSets the value of field
     * 'payeeLastName'.
     * 
     * @param payeeLastName the value of field 'payeeLastName'.
     */
    public void setPayeeLastName(java.lang.String payeeLastName)
    {
        this._payeeLastName = payeeLastName;
        
        super.setVoChanged(true);
    } //-- void setPayeeLastName(java.lang.String) 

    /**
     * Method setProcessDateSets the value of field 'processDate'.
     * 
     * @param processDate the value of field 'processDate'.
     */
    public void setProcessDate(java.lang.String processDate)
    {
        this._processDate = processDate;
        
        super.setVoChanged(true);
    } //-- void setProcessDate(java.lang.String) 

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
     * Method setReduceTaxableSets the value of field
     * 'reduceTaxable'.
     * 
     * @param reduceTaxable the value of field 'reduceTaxable'.
     */
    public void setReduceTaxable(java.lang.String reduceTaxable)
    {
        this._reduceTaxable = reduceTaxable;
        
        super.setVoChanged(true);
    } //-- void setReduceTaxable(java.lang.String) 

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
     * Method setStateWithholdingAmountSets the value of field
     * 'stateWithholdingAmount'.
     * 
     * @param stateWithholdingAmount the value of field
     * 'stateWithholdingAmount'.
     */
    public void setStateWithholdingAmount(java.lang.String stateWithholdingAmount)
    {
        this._stateWithholdingAmount = stateWithholdingAmount;
        
        super.setVoChanged(true);
    } //-- void setStateWithholdingAmount(java.lang.String) 

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
     * Method setTaxableBenefitSets the value of field
     * 'taxableBenefit'.
     * 
     * @param taxableBenefit the value of field 'taxableBenefit'.
     */
    public void setTaxableBenefit(java.lang.String taxableBenefit)
    {
        this._taxableBenefit = taxableBenefit;
        
        super.setVoChanged(true);
    } //-- void setTaxableBenefit(java.lang.String) 

    /**
     * Method setTransactionTypeSets the value of field
     * 'transactionType'.
     * 
     * @param transactionType the value of field 'transactionType'.
     */
    public void setTransactionType(java.lang.String transactionType)
    {
        this._transactionType = transactionType;
        
        super.setVoChanged(true);
    } //-- void setTransactionType(java.lang.String) 

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
    public static edit.common.vo.TaxExtractDetailVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.TaxExtractDetailVO) Unmarshaller.unmarshal(edit.common.vo.TaxExtractDetailVO.class, reader);
    } //-- edit.common.vo.TaxExtractDetailVO unmarshal(java.io.Reader) 

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
