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
import java.math.BigDecimal;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class GAAPReservesDetailVO.
 * 
 * @version $Revision$ $Date$
 */
public class GAAPReservesDetailVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _GAAPReservesDetailPK
     */
    private long _GAAPReservesDetailPK;

    /**
     * keeps track of state for field: _GAAPReservesDetailPK
     */
    private boolean _has_GAAPReservesDetailPK;

    /**
     * Field _policyNumber
     */
    private java.lang.String _policyNumber;

    /**
     * Field _policyEffectiveDate
     */
    private java.lang.String _policyEffectiveDate;

    /**
     * Field _businessContract
     */
    private java.lang.String _businessContract;

    /**
     * Field _segmentStatus
     */
    private java.lang.String _segmentStatus;

    /**
     * Field _ownerIssueAge
     */
    private int _ownerIssueAge;

    /**
     * keeps track of state for field: _ownerIssueAge
     */
    private boolean _has_ownerIssueAge;

    /**
     * Field _ownerGender
     */
    private java.lang.String _ownerGender;

    /**
     * Field _jointOwnerIssueAge
     */
    private int _jointOwnerIssueAge;

    /**
     * keeps track of state for field: _jointOwnerIssueAge
     */
    private boolean _has_jointOwnerIssueAge;

    /**
     * Field _jointOwnerGender
     */
    private java.lang.String _jointOwnerGender;

    /**
     * Field _currentInterestRate
     */
    private java.math.BigDecimal _currentInterestRate;

    /**
     * Field _currentInterestRateDuration
     */
    private java.lang.String _currentInterestRateDuration;

    /**
     * Field _guaranteedMinRate
     */
    private java.math.BigDecimal _guaranteedMinRate;

    /**
     * Field _guaranteedMinRateDuration
     */
    private java.math.BigDecimal _guaranteedMinRateDuration;

    /**
     * Field _indexCapRate
     */
    private java.math.BigDecimal _indexCapRate;

    /**
     * Field _indexMinCapRate
     */
    private java.math.BigDecimal _indexMinCapRate;

    /**
     * Field _indexMarginRate
     */
    private java.math.BigDecimal _indexMarginRate;

    /**
     * Field _indexParticipationRate
     */
    private java.math.BigDecimal _indexParticipationRate;

    /**
     * Field _bucketAllocation
     */
    private java.math.BigDecimal _bucketAllocation;

    /**
     * Field _fundNumber
     */
    private java.lang.String _fundNumber;

    /**
     * Field _transactionType
     */
    private java.lang.String _transactionType;

    /**
     * Field _premiumIndicator
     */
    private java.lang.String _premiumIndicator;

    /**
     * Field _marketingPackageName
     */
    private java.lang.String _marketingPackageName;

    /**
     * Field _processDate
     */
    private java.lang.String _processDate;


      //----------------/
     //- Constructors -/
    //----------------/

    public GAAPReservesDetailVO() {
        super();
    } //-- edit.common.vo.GAAPReservesDetailVO()


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
        
        if (obj instanceof GAAPReservesDetailVO) {
        
            GAAPReservesDetailVO temp = (GAAPReservesDetailVO)obj;
            if (this._GAAPReservesDetailPK != temp._GAAPReservesDetailPK)
                return false;
            if (this._has_GAAPReservesDetailPK != temp._has_GAAPReservesDetailPK)
                return false;
            if (this._policyNumber != null) {
                if (temp._policyNumber == null) return false;
                else if (!(this._policyNumber.equals(temp._policyNumber))) 
                    return false;
            }
            else if (temp._policyNumber != null)
                return false;
            if (this._policyEffectiveDate != null) {
                if (temp._policyEffectiveDate == null) return false;
                else if (!(this._policyEffectiveDate.equals(temp._policyEffectiveDate))) 
                    return false;
            }
            else if (temp._policyEffectiveDate != null)
                return false;
            if (this._businessContract != null) {
                if (temp._businessContract == null) return false;
                else if (!(this._businessContract.equals(temp._businessContract))) 
                    return false;
            }
            else if (temp._businessContract != null)
                return false;
            if (this._segmentStatus != null) {
                if (temp._segmentStatus == null) return false;
                else if (!(this._segmentStatus.equals(temp._segmentStatus))) 
                    return false;
            }
            else if (temp._segmentStatus != null)
                return false;
            if (this._ownerIssueAge != temp._ownerIssueAge)
                return false;
            if (this._has_ownerIssueAge != temp._has_ownerIssueAge)
                return false;
            if (this._ownerGender != null) {
                if (temp._ownerGender == null) return false;
                else if (!(this._ownerGender.equals(temp._ownerGender))) 
                    return false;
            }
            else if (temp._ownerGender != null)
                return false;
            if (this._jointOwnerIssueAge != temp._jointOwnerIssueAge)
                return false;
            if (this._has_jointOwnerIssueAge != temp._has_jointOwnerIssueAge)
                return false;
            if (this._jointOwnerGender != null) {
                if (temp._jointOwnerGender == null) return false;
                else if (!(this._jointOwnerGender.equals(temp._jointOwnerGender))) 
                    return false;
            }
            else if (temp._jointOwnerGender != null)
                return false;
            if (this._currentInterestRate != null) {
                if (temp._currentInterestRate == null) return false;
                else if (!(this._currentInterestRate.equals(temp._currentInterestRate))) 
                    return false;
            }
            else if (temp._currentInterestRate != null)
                return false;
            if (this._currentInterestRateDuration != null) {
                if (temp._currentInterestRateDuration == null) return false;
                else if (!(this._currentInterestRateDuration.equals(temp._currentInterestRateDuration))) 
                    return false;
            }
            else if (temp._currentInterestRateDuration != null)
                return false;
            if (this._guaranteedMinRate != null) {
                if (temp._guaranteedMinRate == null) return false;
                else if (!(this._guaranteedMinRate.equals(temp._guaranteedMinRate))) 
                    return false;
            }
            else if (temp._guaranteedMinRate != null)
                return false;
            if (this._guaranteedMinRateDuration != null) {
                if (temp._guaranteedMinRateDuration == null) return false;
                else if (!(this._guaranteedMinRateDuration.equals(temp._guaranteedMinRateDuration))) 
                    return false;
            }
            else if (temp._guaranteedMinRateDuration != null)
                return false;
            if (this._indexCapRate != null) {
                if (temp._indexCapRate == null) return false;
                else if (!(this._indexCapRate.equals(temp._indexCapRate))) 
                    return false;
            }
            else if (temp._indexCapRate != null)
                return false;
            if (this._indexMinCapRate != null) {
                if (temp._indexMinCapRate == null) return false;
                else if (!(this._indexMinCapRate.equals(temp._indexMinCapRate))) 
                    return false;
            }
            else if (temp._indexMinCapRate != null)
                return false;
            if (this._indexMarginRate != null) {
                if (temp._indexMarginRate == null) return false;
                else if (!(this._indexMarginRate.equals(temp._indexMarginRate))) 
                    return false;
            }
            else if (temp._indexMarginRate != null)
                return false;
            if (this._indexParticipationRate != null) {
                if (temp._indexParticipationRate == null) return false;
                else if (!(this._indexParticipationRate.equals(temp._indexParticipationRate))) 
                    return false;
            }
            else if (temp._indexParticipationRate != null)
                return false;
            if (this._bucketAllocation != null) {
                if (temp._bucketAllocation == null) return false;
                else if (!(this._bucketAllocation.equals(temp._bucketAllocation))) 
                    return false;
            }
            else if (temp._bucketAllocation != null)
                return false;
            if (this._fundNumber != null) {
                if (temp._fundNumber == null) return false;
                else if (!(this._fundNumber.equals(temp._fundNumber))) 
                    return false;
            }
            else if (temp._fundNumber != null)
                return false;
            if (this._transactionType != null) {
                if (temp._transactionType == null) return false;
                else if (!(this._transactionType.equals(temp._transactionType))) 
                    return false;
            }
            else if (temp._transactionType != null)
                return false;
            if (this._premiumIndicator != null) {
                if (temp._premiumIndicator == null) return false;
                else if (!(this._premiumIndicator.equals(temp._premiumIndicator))) 
                    return false;
            }
            else if (temp._premiumIndicator != null)
                return false;
            if (this._marketingPackageName != null) {
                if (temp._marketingPackageName == null) return false;
                else if (!(this._marketingPackageName.equals(temp._marketingPackageName))) 
                    return false;
            }
            else if (temp._marketingPackageName != null)
                return false;
            if (this._processDate != null) {
                if (temp._processDate == null) return false;
                else if (!(this._processDate.equals(temp._processDate))) 
                    return false;
            }
            else if (temp._processDate != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBucketAllocationReturns the value of field
     * 'bucketAllocation'.
     * 
     * @return the value of field 'bucketAllocation'.
     */
    public java.math.BigDecimal getBucketAllocation()
    {
        return this._bucketAllocation;
    } //-- java.math.BigDecimal getBucketAllocation() 

    /**
     * Method getBusinessContractReturns the value of field
     * 'businessContract'.
     * 
     * @return the value of field 'businessContract'.
     */
    public java.lang.String getBusinessContract()
    {
        return this._businessContract;
    } //-- java.lang.String getBusinessContract() 

    /**
     * Method getCurrentInterestRateReturns the value of field
     * 'currentInterestRate'.
     * 
     * @return the value of field 'currentInterestRate'.
     */
    public java.math.BigDecimal getCurrentInterestRate()
    {
        return this._currentInterestRate;
    } //-- java.math.BigDecimal getCurrentInterestRate() 

    /**
     * Method getCurrentInterestRateDurationReturns the value of
     * field 'currentInterestRateDuration'.
     * 
     * @return the value of field 'currentInterestRateDuration'.
     */
    public java.lang.String getCurrentInterestRateDuration()
    {
        return this._currentInterestRateDuration;
    } //-- java.lang.String getCurrentInterestRateDuration() 

    /**
     * Method getFundNumberReturns the value of field 'fundNumber'.
     * 
     * @return the value of field 'fundNumber'.
     */
    public java.lang.String getFundNumber()
    {
        return this._fundNumber;
    } //-- java.lang.String getFundNumber() 

    /**
     * Method getGAAPReservesDetailPKReturns the value of field
     * 'GAAPReservesDetailPK'.
     * 
     * @return the value of field 'GAAPReservesDetailPK'.
     */
    public long getGAAPReservesDetailPK()
    {
        return this._GAAPReservesDetailPK;
    } //-- long getGAAPReservesDetailPK() 

    /**
     * Method getGuaranteedMinRateReturns the value of field
     * 'guaranteedMinRate'.
     * 
     * @return the value of field 'guaranteedMinRate'.
     */
    public java.math.BigDecimal getGuaranteedMinRate()
    {
        return this._guaranteedMinRate;
    } //-- java.math.BigDecimal getGuaranteedMinRate() 

    /**
     * Method getGuaranteedMinRateDurationReturns the value of
     * field 'guaranteedMinRateDuration'.
     * 
     * @return the value of field 'guaranteedMinRateDuration'.
     */
    public java.math.BigDecimal getGuaranteedMinRateDuration()
    {
        return this._guaranteedMinRateDuration;
    } //-- java.math.BigDecimal getGuaranteedMinRateDuration() 

    /**
     * Method getIndexCapRateReturns the value of field
     * 'indexCapRate'.
     * 
     * @return the value of field 'indexCapRate'.
     */
    public java.math.BigDecimal getIndexCapRate()
    {
        return this._indexCapRate;
    } //-- java.math.BigDecimal getIndexCapRate() 

    /**
     * Method getIndexMarginRateReturns the value of field
     * 'indexMarginRate'.
     * 
     * @return the value of field 'indexMarginRate'.
     */
    public java.math.BigDecimal getIndexMarginRate()
    {
        return this._indexMarginRate;
    } //-- java.math.BigDecimal getIndexMarginRate() 

    /**
     * Method getIndexMinCapRateReturns the value of field
     * 'indexMinCapRate'.
     * 
     * @return the value of field 'indexMinCapRate'.
     */
    public java.math.BigDecimal getIndexMinCapRate()
    {
        return this._indexMinCapRate;
    } //-- java.math.BigDecimal getIndexMinCapRate() 

    /**
     * Method getIndexParticipationRateReturns the value of field
     * 'indexParticipationRate'.
     * 
     * @return the value of field 'indexParticipationRate'.
     */
    public java.math.BigDecimal getIndexParticipationRate()
    {
        return this._indexParticipationRate;
    } //-- java.math.BigDecimal getIndexParticipationRate() 

    /**
     * Method getJointOwnerGenderReturns the value of field
     * 'jointOwnerGender'.
     * 
     * @return the value of field 'jointOwnerGender'.
     */
    public java.lang.String getJointOwnerGender()
    {
        return this._jointOwnerGender;
    } //-- java.lang.String getJointOwnerGender() 

    /**
     * Method getJointOwnerIssueAgeReturns the value of field
     * 'jointOwnerIssueAge'.
     * 
     * @return the value of field 'jointOwnerIssueAge'.
     */
    public int getJointOwnerIssueAge()
    {
        return this._jointOwnerIssueAge;
    } //-- int getJointOwnerIssueAge() 

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
     * Method getOwnerGenderReturns the value of field
     * 'ownerGender'.
     * 
     * @return the value of field 'ownerGender'.
     */
    public java.lang.String getOwnerGender()
    {
        return this._ownerGender;
    } //-- java.lang.String getOwnerGender() 

    /**
     * Method getOwnerIssueAgeReturns the value of field
     * 'ownerIssueAge'.
     * 
     * @return the value of field 'ownerIssueAge'.
     */
    public int getOwnerIssueAge()
    {
        return this._ownerIssueAge;
    } //-- int getOwnerIssueAge() 

    /**
     * Method getPolicyEffectiveDateReturns the value of field
     * 'policyEffectiveDate'.
     * 
     * @return the value of field 'policyEffectiveDate'.
     */
    public java.lang.String getPolicyEffectiveDate()
    {
        return this._policyEffectiveDate;
    } //-- java.lang.String getPolicyEffectiveDate() 

    /**
     * Method getPolicyNumberReturns the value of field
     * 'policyNumber'.
     * 
     * @return the value of field 'policyNumber'.
     */
    public java.lang.String getPolicyNumber()
    {
        return this._policyNumber;
    } //-- java.lang.String getPolicyNumber() 

    /**
     * Method getPremiumIndicatorReturns the value of field
     * 'premiumIndicator'.
     * 
     * @return the value of field 'premiumIndicator'.
     */
    public java.lang.String getPremiumIndicator()
    {
        return this._premiumIndicator;
    } //-- java.lang.String getPremiumIndicator() 

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
     * Method getSegmentStatusReturns the value of field
     * 'segmentStatus'.
     * 
     * @return the value of field 'segmentStatus'.
     */
    public java.lang.String getSegmentStatus()
    {
        return this._segmentStatus;
    } //-- java.lang.String getSegmentStatus() 

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
     * Method hasGAAPReservesDetailPK
     */
    public boolean hasGAAPReservesDetailPK()
    {
        return this._has_GAAPReservesDetailPK;
    } //-- boolean hasGAAPReservesDetailPK() 

    /**
     * Method hasJointOwnerIssueAge
     */
    public boolean hasJointOwnerIssueAge()
    {
        return this._has_jointOwnerIssueAge;
    } //-- boolean hasJointOwnerIssueAge() 

    /**
     * Method hasOwnerIssueAge
     */
    public boolean hasOwnerIssueAge()
    {
        return this._has_ownerIssueAge;
    } //-- boolean hasOwnerIssueAge() 

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
     * Method setBucketAllocationSets the value of field
     * 'bucketAllocation'.
     * 
     * @param bucketAllocation the value of field 'bucketAllocation'
     */
    public void setBucketAllocation(java.math.BigDecimal bucketAllocation)
    {
        this._bucketAllocation = bucketAllocation;
        
        super.setVoChanged(true);
    } //-- void setBucketAllocation(java.math.BigDecimal) 

    /**
     * Method setBusinessContractSets the value of field
     * 'businessContract'.
     * 
     * @param businessContract the value of field 'businessContract'
     */
    public void setBusinessContract(java.lang.String businessContract)
    {
        this._businessContract = businessContract;
        
        super.setVoChanged(true);
    } //-- void setBusinessContract(java.lang.String) 

    /**
     * Method setCurrentInterestRateSets the value of field
     * 'currentInterestRate'.
     * 
     * @param currentInterestRate the value of field
     * 'currentInterestRate'.
     */
    public void setCurrentInterestRate(java.math.BigDecimal currentInterestRate)
    {
        this._currentInterestRate = currentInterestRate;
        
        super.setVoChanged(true);
    } //-- void setCurrentInterestRate(java.math.BigDecimal) 

    /**
     * Method setCurrentInterestRateDurationSets the value of field
     * 'currentInterestRateDuration'.
     * 
     * @param currentInterestRateDuration the value of field
     * 'currentInterestRateDuration'.
     */
    public void setCurrentInterestRateDuration(java.lang.String currentInterestRateDuration)
    {
        this._currentInterestRateDuration = currentInterestRateDuration;
        
        super.setVoChanged(true);
    } //-- void setCurrentInterestRateDuration(java.lang.String) 

    /**
     * Method setFundNumberSets the value of field 'fundNumber'.
     * 
     * @param fundNumber the value of field 'fundNumber'.
     */
    public void setFundNumber(java.lang.String fundNumber)
    {
        this._fundNumber = fundNumber;
        
        super.setVoChanged(true);
    } //-- void setFundNumber(java.lang.String) 

    /**
     * Method setGAAPReservesDetailPKSets the value of field
     * 'GAAPReservesDetailPK'.
     * 
     * @param GAAPReservesDetailPK the value of field
     * 'GAAPReservesDetailPK'.
     */
    public void setGAAPReservesDetailPK(long GAAPReservesDetailPK)
    {
        this._GAAPReservesDetailPK = GAAPReservesDetailPK;
        
        super.setVoChanged(true);
        this._has_GAAPReservesDetailPK = true;
    } //-- void setGAAPReservesDetailPK(long) 

    /**
     * Method setGuaranteedMinRateSets the value of field
     * 'guaranteedMinRate'.
     * 
     * @param guaranteedMinRate the value of field
     * 'guaranteedMinRate'.
     */
    public void setGuaranteedMinRate(java.math.BigDecimal guaranteedMinRate)
    {
        this._guaranteedMinRate = guaranteedMinRate;
        
        super.setVoChanged(true);
    } //-- void setGuaranteedMinRate(java.math.BigDecimal) 

    /**
     * Method setGuaranteedMinRateDurationSets the value of field
     * 'guaranteedMinRateDuration'.
     * 
     * @param guaranteedMinRateDuration the value of field
     * 'guaranteedMinRateDuration'.
     */
    public void setGuaranteedMinRateDuration(java.math.BigDecimal guaranteedMinRateDuration)
    {
        this._guaranteedMinRateDuration = guaranteedMinRateDuration;
        
        super.setVoChanged(true);
    } //-- void setGuaranteedMinRateDuration(java.math.BigDecimal) 

    /**
     * Method setIndexCapRateSets the value of field
     * 'indexCapRate'.
     * 
     * @param indexCapRate the value of field 'indexCapRate'.
     */
    public void setIndexCapRate(java.math.BigDecimal indexCapRate)
    {
        this._indexCapRate = indexCapRate;
        
        super.setVoChanged(true);
    } //-- void setIndexCapRate(java.math.BigDecimal) 

    /**
     * Method setIndexMarginRateSets the value of field
     * 'indexMarginRate'.
     * 
     * @param indexMarginRate the value of field 'indexMarginRate'.
     */
    public void setIndexMarginRate(java.math.BigDecimal indexMarginRate)
    {
        this._indexMarginRate = indexMarginRate;
        
        super.setVoChanged(true);
    } //-- void setIndexMarginRate(java.math.BigDecimal) 

    /**
     * Method setIndexMinCapRateSets the value of field
     * 'indexMinCapRate'.
     * 
     * @param indexMinCapRate the value of field 'indexMinCapRate'.
     */
    public void setIndexMinCapRate(java.math.BigDecimal indexMinCapRate)
    {
        this._indexMinCapRate = indexMinCapRate;
        
        super.setVoChanged(true);
    } //-- void setIndexMinCapRate(java.math.BigDecimal) 

    /**
     * Method setIndexParticipationRateSets the value of field
     * 'indexParticipationRate'.
     * 
     * @param indexParticipationRate the value of field
     * 'indexParticipationRate'.
     */
    public void setIndexParticipationRate(java.math.BigDecimal indexParticipationRate)
    {
        this._indexParticipationRate = indexParticipationRate;
        
        super.setVoChanged(true);
    } //-- void setIndexParticipationRate(java.math.BigDecimal) 

    /**
     * Method setJointOwnerGenderSets the value of field
     * 'jointOwnerGender'.
     * 
     * @param jointOwnerGender the value of field 'jointOwnerGender'
     */
    public void setJointOwnerGender(java.lang.String jointOwnerGender)
    {
        this._jointOwnerGender = jointOwnerGender;
        
        super.setVoChanged(true);
    } //-- void setJointOwnerGender(java.lang.String) 

    /**
     * Method setJointOwnerIssueAgeSets the value of field
     * 'jointOwnerIssueAge'.
     * 
     * @param jointOwnerIssueAge the value of field
     * 'jointOwnerIssueAge'.
     */
    public void setJointOwnerIssueAge(int jointOwnerIssueAge)
    {
        this._jointOwnerIssueAge = jointOwnerIssueAge;
        
        super.setVoChanged(true);
        this._has_jointOwnerIssueAge = true;
    } //-- void setJointOwnerIssueAge(int) 

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
     * Method setOwnerGenderSets the value of field 'ownerGender'.
     * 
     * @param ownerGender the value of field 'ownerGender'.
     */
    public void setOwnerGender(java.lang.String ownerGender)
    {
        this._ownerGender = ownerGender;
        
        super.setVoChanged(true);
    } //-- void setOwnerGender(java.lang.String) 

    /**
     * Method setOwnerIssueAgeSets the value of field
     * 'ownerIssueAge'.
     * 
     * @param ownerIssueAge the value of field 'ownerIssueAge'.
     */
    public void setOwnerIssueAge(int ownerIssueAge)
    {
        this._ownerIssueAge = ownerIssueAge;
        
        super.setVoChanged(true);
        this._has_ownerIssueAge = true;
    } //-- void setOwnerIssueAge(int) 

    /**
     * Method setPolicyEffectiveDateSets the value of field
     * 'policyEffectiveDate'.
     * 
     * @param policyEffectiveDate the value of field
     * 'policyEffectiveDate'.
     */
    public void setPolicyEffectiveDate(java.lang.String policyEffectiveDate)
    {
        this._policyEffectiveDate = policyEffectiveDate;
        
        super.setVoChanged(true);
    } //-- void setPolicyEffectiveDate(java.lang.String) 

    /**
     * Method setPolicyNumberSets the value of field
     * 'policyNumber'.
     * 
     * @param policyNumber the value of field 'policyNumber'.
     */
    public void setPolicyNumber(java.lang.String policyNumber)
    {
        this._policyNumber = policyNumber;
        
        super.setVoChanged(true);
    } //-- void setPolicyNumber(java.lang.String) 

    /**
     * Method setPremiumIndicatorSets the value of field
     * 'premiumIndicator'.
     * 
     * @param premiumIndicator the value of field 'premiumIndicator'
     */
    public void setPremiumIndicator(java.lang.String premiumIndicator)
    {
        this._premiumIndicator = premiumIndicator;
        
        super.setVoChanged(true);
    } //-- void setPremiumIndicator(java.lang.String) 

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
     * Method setSegmentStatusSets the value of field
     * 'segmentStatus'.
     * 
     * @param segmentStatus the value of field 'segmentStatus'.
     */
    public void setSegmentStatus(java.lang.String segmentStatus)
    {
        this._segmentStatus = segmentStatus;
        
        super.setVoChanged(true);
    } //-- void setSegmentStatus(java.lang.String) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.GAAPReservesDetailVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.GAAPReservesDetailVO) Unmarshaller.unmarshal(edit.common.vo.GAAPReservesDetailVO.class, reader);
    } //-- edit.common.vo.GAAPReservesDetailVO unmarshal(java.io.Reader) 

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
