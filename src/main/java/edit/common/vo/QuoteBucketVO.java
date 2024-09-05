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
 * Class QuoteBucketVO.
 * 
 * @version $Revision$ $Date$
 */
public class QuoteBucketVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _quoteBucketPK
     */
    private long _quoteBucketPK;

    /**
     * keeps track of state for field: _quoteBucketPK
     */
    private boolean _has_quoteBucketPK;

    /**
     * Field _bucketFK
     */
    private long _bucketFK;

    /**
     * keeps track of state for field: _bucketFK
     */
    private boolean _has_bucketFK;

    /**
     * Field _beginningSandP
     */
    private java.math.BigDecimal _beginningSandP;

    /**
     * Field _currentIndexCapRate
     */
    private java.math.BigDecimal _currentIndexCapRate;

    /**
     * Field _participationRate
     */
    private java.math.BigDecimal _participationRate;

    /**
     * Field _margin
     */
    private java.math.BigDecimal _margin;

    /**
     * Field _interestEarnedCurrent
     */
    private java.math.BigDecimal _interestEarnedCurrent;

    /**
     * Field _bonusInterestEarned
     */
    private java.math.BigDecimal _bonusInterestEarned;

    /**
     * Field _availableLoan
     */
    private java.math.BigDecimal _availableLoan;

    /**
     * Field _unlockedLoanAmount
     */
    private java.math.BigDecimal _unlockedLoanAmount;

    /**
     * Field _unearnedLoanInterest
     */
    private java.math.BigDecimal _unearnedLoanInterest;


      //----------------/
     //- Constructors -/
    //----------------/

    public QuoteBucketVO() {
        super();
    } //-- edit.common.vo.QuoteBucketVO()


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
        
        if (obj instanceof QuoteBucketVO) {
        
            QuoteBucketVO temp = (QuoteBucketVO)obj;
            if (this._quoteBucketPK != temp._quoteBucketPK)
                return false;
            if (this._has_quoteBucketPK != temp._has_quoteBucketPK)
                return false;
            if (this._bucketFK != temp._bucketFK)
                return false;
            if (this._has_bucketFK != temp._has_bucketFK)
                return false;
            if (this._beginningSandP != null) {
                if (temp._beginningSandP == null) return false;
                else if (!(this._beginningSandP.equals(temp._beginningSandP))) 
                    return false;
            }
            else if (temp._beginningSandP != null)
                return false;
            if (this._currentIndexCapRate != null) {
                if (temp._currentIndexCapRate == null) return false;
                else if (!(this._currentIndexCapRate.equals(temp._currentIndexCapRate))) 
                    return false;
            }
            else if (temp._currentIndexCapRate != null)
                return false;
            if (this._participationRate != null) {
                if (temp._participationRate == null) return false;
                else if (!(this._participationRate.equals(temp._participationRate))) 
                    return false;
            }
            else if (temp._participationRate != null)
                return false;
            if (this._margin != null) {
                if (temp._margin == null) return false;
                else if (!(this._margin.equals(temp._margin))) 
                    return false;
            }
            else if (temp._margin != null)
                return false;
            if (this._interestEarnedCurrent != null) {
                if (temp._interestEarnedCurrent == null) return false;
                else if (!(this._interestEarnedCurrent.equals(temp._interestEarnedCurrent))) 
                    return false;
            }
            else if (temp._interestEarnedCurrent != null)
                return false;
            if (this._bonusInterestEarned != null) {
                if (temp._bonusInterestEarned == null) return false;
                else if (!(this._bonusInterestEarned.equals(temp._bonusInterestEarned))) 
                    return false;
            }
            else if (temp._bonusInterestEarned != null)
                return false;
            if (this._availableLoan != null) {
                if (temp._availableLoan == null) return false;
                else if (!(this._availableLoan.equals(temp._availableLoan))) 
                    return false;
            }
            else if (temp._availableLoan != null)
                return false;
            if (this._unlockedLoanAmount != null) {
                if (temp._unlockedLoanAmount == null) return false;
                else if (!(this._unlockedLoanAmount.equals(temp._unlockedLoanAmount))) 
                    return false;
            }
            else if (temp._unlockedLoanAmount != null)
                return false;
            if (this._unearnedLoanInterest != null) {
                if (temp._unearnedLoanInterest == null) return false;
                else if (!(this._unearnedLoanInterest.equals(temp._unearnedLoanInterest))) 
                    return false;
            }
            else if (temp._unearnedLoanInterest != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAvailableLoanReturns the value of field
     * 'availableLoan'.
     * 
     * @return the value of field 'availableLoan'.
     */
    public java.math.BigDecimal getAvailableLoan()
    {
        return this._availableLoan;
    } //-- java.math.BigDecimal getAvailableLoan() 

    /**
     * Method getBeginningSandPReturns the value of field
     * 'beginningSandP'.
     * 
     * @return the value of field 'beginningSandP'.
     */
    public java.math.BigDecimal getBeginningSandP()
    {
        return this._beginningSandP;
    } //-- java.math.BigDecimal getBeginningSandP() 

    /**
     * Method getBonusInterestEarnedReturns the value of field
     * 'bonusInterestEarned'.
     * 
     * @return the value of field 'bonusInterestEarned'.
     */
    public java.math.BigDecimal getBonusInterestEarned()
    {
        return this._bonusInterestEarned;
    } //-- java.math.BigDecimal getBonusInterestEarned() 

    /**
     * Method getBucketFKReturns the value of field 'bucketFK'.
     * 
     * @return the value of field 'bucketFK'.
     */
    public long getBucketFK()
    {
        return this._bucketFK;
    } //-- long getBucketFK() 

    /**
     * Method getCurrentIndexCapRateReturns the value of field
     * 'currentIndexCapRate'.
     * 
     * @return the value of field 'currentIndexCapRate'.
     */
    public java.math.BigDecimal getCurrentIndexCapRate()
    {
        return this._currentIndexCapRate;
    } //-- java.math.BigDecimal getCurrentIndexCapRate() 

    /**
     * Method getInterestEarnedCurrentReturns the value of field
     * 'interestEarnedCurrent'.
     * 
     * @return the value of field 'interestEarnedCurrent'.
     */
    public java.math.BigDecimal getInterestEarnedCurrent()
    {
        return this._interestEarnedCurrent;
    } //-- java.math.BigDecimal getInterestEarnedCurrent() 

    /**
     * Method getMarginReturns the value of field 'margin'.
     * 
     * @return the value of field 'margin'.
     */
    public java.math.BigDecimal getMargin()
    {
        return this._margin;
    } //-- java.math.BigDecimal getMargin() 

    /**
     * Method getParticipationRateReturns the value of field
     * 'participationRate'.
     * 
     * @return the value of field 'participationRate'.
     */
    public java.math.BigDecimal getParticipationRate()
    {
        return this._participationRate;
    } //-- java.math.BigDecimal getParticipationRate() 

    /**
     * Method getQuoteBucketPKReturns the value of field
     * 'quoteBucketPK'.
     * 
     * @return the value of field 'quoteBucketPK'.
     */
    public long getQuoteBucketPK()
    {
        return this._quoteBucketPK;
    } //-- long getQuoteBucketPK() 

    /**
     * Method getUnearnedLoanInterestReturns the value of field
     * 'unearnedLoanInterest'.
     * 
     * @return the value of field 'unearnedLoanInterest'.
     */
    public java.math.BigDecimal getUnearnedLoanInterest()
    {
        return this._unearnedLoanInterest;
    } //-- java.math.BigDecimal getUnearnedLoanInterest() 

    /**
     * Method getUnlockedLoanAmountReturns the value of field
     * 'unlockedLoanAmount'.
     * 
     * @return the value of field 'unlockedLoanAmount'.
     */
    public java.math.BigDecimal getUnlockedLoanAmount()
    {
        return this._unlockedLoanAmount;
    } //-- java.math.BigDecimal getUnlockedLoanAmount() 

    /**
     * Method hasBucketFK
     */
    public boolean hasBucketFK()
    {
        return this._has_bucketFK;
    } //-- boolean hasBucketFK() 

    /**
     * Method hasQuoteBucketPK
     */
    public boolean hasQuoteBucketPK()
    {
        return this._has_quoteBucketPK;
    } //-- boolean hasQuoteBucketPK() 

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
     * Method setAvailableLoanSets the value of field
     * 'availableLoan'.
     * 
     * @param availableLoan the value of field 'availableLoan'.
     */
    public void setAvailableLoan(java.math.BigDecimal availableLoan)
    {
        this._availableLoan = availableLoan;
        
        super.setVoChanged(true);
    } //-- void setAvailableLoan(java.math.BigDecimal) 

    /**
     * Method setBeginningSandPSets the value of field
     * 'beginningSandP'.
     * 
     * @param beginningSandP the value of field 'beginningSandP'.
     */
    public void setBeginningSandP(java.math.BigDecimal beginningSandP)
    {
        this._beginningSandP = beginningSandP;
        
        super.setVoChanged(true);
    } //-- void setBeginningSandP(java.math.BigDecimal) 

    /**
     * Method setBonusInterestEarnedSets the value of field
     * 'bonusInterestEarned'.
     * 
     * @param bonusInterestEarned the value of field
     * 'bonusInterestEarned'.
     */
    public void setBonusInterestEarned(java.math.BigDecimal bonusInterestEarned)
    {
        this._bonusInterestEarned = bonusInterestEarned;
        
        super.setVoChanged(true);
    } //-- void setBonusInterestEarned(java.math.BigDecimal) 

    /**
     * Method setBucketFKSets the value of field 'bucketFK'.
     * 
     * @param bucketFK the value of field 'bucketFK'.
     */
    public void setBucketFK(long bucketFK)
    {
        this._bucketFK = bucketFK;
        
        super.setVoChanged(true);
        this._has_bucketFK = true;
    } //-- void setBucketFK(long) 

    /**
     * Method setCurrentIndexCapRateSets the value of field
     * 'currentIndexCapRate'.
     * 
     * @param currentIndexCapRate the value of field
     * 'currentIndexCapRate'.
     */
    public void setCurrentIndexCapRate(java.math.BigDecimal currentIndexCapRate)
    {
        this._currentIndexCapRate = currentIndexCapRate;
        
        super.setVoChanged(true);
    } //-- void setCurrentIndexCapRate(java.math.BigDecimal) 

    /**
     * Method setInterestEarnedCurrentSets the value of field
     * 'interestEarnedCurrent'.
     * 
     * @param interestEarnedCurrent the value of field
     * 'interestEarnedCurrent'.
     */
    public void setInterestEarnedCurrent(java.math.BigDecimal interestEarnedCurrent)
    {
        this._interestEarnedCurrent = interestEarnedCurrent;
        
        super.setVoChanged(true);
    } //-- void setInterestEarnedCurrent(java.math.BigDecimal) 

    /**
     * Method setMarginSets the value of field 'margin'.
     * 
     * @param margin the value of field 'margin'.
     */
    public void setMargin(java.math.BigDecimal margin)
    {
        this._margin = margin;
        
        super.setVoChanged(true);
    } //-- void setMargin(java.math.BigDecimal) 

    /**
     * Method setParticipationRateSets the value of field
     * 'participationRate'.
     * 
     * @param participationRate the value of field
     * 'participationRate'.
     */
    public void setParticipationRate(java.math.BigDecimal participationRate)
    {
        this._participationRate = participationRate;
        
        super.setVoChanged(true);
    } //-- void setParticipationRate(java.math.BigDecimal) 

    /**
     * Method setQuoteBucketPKSets the value of field
     * 'quoteBucketPK'.
     * 
     * @param quoteBucketPK the value of field 'quoteBucketPK'.
     */
    public void setQuoteBucketPK(long quoteBucketPK)
    {
        this._quoteBucketPK = quoteBucketPK;
        
        super.setVoChanged(true);
        this._has_quoteBucketPK = true;
    } //-- void setQuoteBucketPK(long) 

    /**
     * Method setUnearnedLoanInterestSets the value of field
     * 'unearnedLoanInterest'.
     * 
     * @param unearnedLoanInterest the value of field
     * 'unearnedLoanInterest'.
     */
    public void setUnearnedLoanInterest(java.math.BigDecimal unearnedLoanInterest)
    {
        this._unearnedLoanInterest = unearnedLoanInterest;
        
        super.setVoChanged(true);
    } //-- void setUnearnedLoanInterest(java.math.BigDecimal) 

    /**
     * Method setUnlockedLoanAmountSets the value of field
     * 'unlockedLoanAmount'.
     * 
     * @param unlockedLoanAmount the value of field
     * 'unlockedLoanAmount'.
     */
    public void setUnlockedLoanAmount(java.math.BigDecimal unlockedLoanAmount)
    {
        this._unlockedLoanAmount = unlockedLoanAmount;
        
        super.setVoChanged(true);
    } //-- void setUnlockedLoanAmount(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.QuoteBucketVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.QuoteBucketVO) Unmarshaller.unmarshal(edit.common.vo.QuoteBucketVO.class, reader);
    } //-- edit.common.vo.QuoteBucketVO unmarshal(java.io.Reader) 

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
