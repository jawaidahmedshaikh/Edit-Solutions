package edit.common.vo;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;

/**
 * Class YearEndValuesVO.
 * 
 * @version $Revision$ $Date$
 */
public class YearEndValuesVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{

	private static final long serialVersionUID = 1L;

	//--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _yearEndValuesPK
     */
    private long _yearEndValuesPK;

    /**
     * keeps track of state for field: _yearEndValuesPK
     */
    private boolean _has_yearEndValuesPK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /*
    private EDITDate yearEndDate;
    private String policyAdminStatus;
    
    private String billMethod;
    private String billingMode;
    private String deductionFrequency;
    private String residentState;
    
    private int termRiderCount;

    private EDITBigDecimal ulFaceAmount;
    
    private EDITBigDecimal termRiderFace;
    
    private EDITBigDecimal modalPremium;
    
    private EDITBigDecimal accumulatedValue;
    private EDITBigDecimal interimInterest;
     * */
    
    /**
     * Field _yearEndDate
     */
    private java.lang.String _yearEndDate;
    
    /**
     * Field _termRiderCount
     */
    private int _termRiderCount;

    /**
     * keeps track of state for field: _termRiderCount
     */
    private boolean _has_termRiderCount;
    
    /**
     * Field _policyAdminStatus
     */
    private java.lang.String _policyAdminStatus;

    private java.lang.String _paymentMethod;
    private java.lang.String _paymentMode;
    private java.lang.String _deductionFrequency;
    private java.lang.String _residentState;
    /**
     * Field _ulFaceAmount
     */
    private java.math.BigDecimal _ulFaceAmount;

    /**
     * Field _termRiderFace
     */
    private java.math.BigDecimal _termRiderFace;

    /**
     * Field _modalPremium
     */
    private java.math.BigDecimal _annualPremium;

    /**
     * Field _accumulatedValue
     */
    private java.math.BigDecimal _accumulatedValue;

    /**
     * Field _interimInterest
     */
    private java.math.BigDecimal _interimInterest;


      //----------------/
     //- Constructors -/
    //----------------/

    public YearEndValuesVO() {
        super();
    }

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
        
        if (obj instanceof YearEndValuesVO) {
        
            YearEndValuesVO temp = (YearEndValuesVO)obj;
            if (this._yearEndValuesPK != temp._yearEndValuesPK)
                return false;
            if (this._has_yearEndValuesPK != temp._has_yearEndValuesPK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            
            if (this._termRiderCount != temp._termRiderCount)
                return false;
            if (this._has_termRiderCount != temp._has_termRiderCount)
                return false;
            if (this._yearEndDate != null) {
                if (temp._yearEndDate == null) return false;
                else if (!(this._yearEndDate.equals(temp._yearEndDate))) 
                    return false;
            }
            else if (temp._yearEndDate != null)
                return false;
            
            if (this._policyAdminStatus != null) {
                if (temp._policyAdminStatus == null) return false;
                else if (!(this._policyAdminStatus.equals(temp._policyAdminStatus))) 
                    return false;
            }
            else if (temp._policyAdminStatus != null)
                return false;
             
             if (this._paymentMethod != null) {
                 if (temp._paymentMethod == null) return false;
                 else if (!(this._paymentMethod.equals(temp._paymentMethod))) 
                     return false;
             }
             else if (temp._paymentMethod != null)
                 return false;
             
             if (this._paymentMode != null) {
                 if (temp._paymentMode == null) return false;
                 else if (!(this._paymentMode.equals(temp._paymentMode))) 
                     return false;
             }
             else if (temp._paymentMode != null)
                 return false;
             
             if (this._deductionFrequency != null) {
                 if (temp._deductionFrequency == null) return false;
                 else if (!(this._deductionFrequency.equals(temp._deductionFrequency))) 
                     return false;
             }
             else if (temp._deductionFrequency != null)
                 return false;
             
             if (this._residentState != null) {
                 if (temp._residentState == null) return false;
                 else if (!(this._residentState.equals(temp._residentState))) 
                     return false;
             }
             else if (temp._residentState != null)
                 return false;
             
            if (this._ulFaceAmount != null) {
                if (temp._ulFaceAmount == null) return false;
                else if (!(this._ulFaceAmount.equals(temp._ulFaceAmount))) 
                    return false;
            }
            else if (temp._ulFaceAmount != null)
                return false;
            if (this._termRiderFace != null) {
                if (temp._termRiderFace == null) return false;
                else if (!(this._termRiderFace.equals(temp._termRiderFace))) 
                    return false;
            }
            else if (temp._termRiderFace != null)
                return false;
            if (this._annualPremium != null) {
                if (temp._annualPremium == null) return false;
                else if (!(this._annualPremium.equals(temp._annualPremium))) 
                    return false;
            }
            else if (temp._annualPremium != null)
                return false;
            if (this._accumulatedValue != null) {
                if (temp._accumulatedValue == null) return false;
                else if (!(this._accumulatedValue.equals(temp._accumulatedValue))) 
                    return false;
            }
            else if (temp._accumulatedValue != null)
                return false;
            if (this._interimInterest != null) {
                if (temp._interimInterest == null) return false;
                else if (!(this._interimInterest.equals(temp._interimInterest))) 
                    return false;
            }
            else if (temp._interimInterest != null)
                return false;
            
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getModalPremiumReturns the value of field
     * 'modalPremium'.
     * 
     * @return the value of field 'modalPremium'.
     */
    public java.math.BigDecimal getAnnualPremium()
    {
        return this._annualPremium;
    } //-- java.math.BigDecimal getModalPremium() 

    /**
     * Method getInterimInterestReturns the value of field
     * 'interimInterest'.
     * 
     * @return the value of field 'interimInterest'.
     */
    public java.math.BigDecimal getInterimInterest()
    {
        return this._interimInterest;
    } //-- java.math.BigDecimal getInterimInterest() 

    /**
     * Method getAccumulatedValueReturns the value of field
     * 'accumulatedValue'.
     * 
     * @return the value of field 'accumulatedValue'.
     */
    public java.math.BigDecimal getAccumulatedValue()
    {
        return this._accumulatedValue;
    } //-- java.math.BigDecimal getAccumulatedValue() 

    /**
     * Method getYearEndDateReturns the value of field
     * 'yearEndDate'.
     * 
     * @return the value of field 'yearEndDate'.
     */
    public java.lang.String getYearEndDate()
    {
        return this._yearEndDate;
    } //-- java.lang.String getYearEndDate() 

    /**
     * Method getTermRiderCountReturns the value of field
     * 'termRiderCount'.
     * 
     * @return the value of field 'termRiderCount'.
     */
    public int getTermRiderCount()
    {
        return this._termRiderCount;
    } //-- int getTermRiderCount() 

    /**
     * Method getPolicyAdminStatus Returns the value of field
     * 'policyAdminStatus'.
     * 
     * @return the value of field 'policyAdminStatus'.
     */
    public java.lang.String getPolicyAdminStatus()
    {
        return this._policyAdminStatus;
    } 
    
    public java.lang.String getPaymentMethod()
    {
        return this._paymentMethod;
    } 
    
    public java.lang.String getPaymentMode()
    {
        return this._paymentMode;
    } 
    
    public java.lang.String getDeductionFrequency()
    {
        return this._deductionFrequency;
    } 
    
    /**
     * Method getResidentState returns the value of field
     * 'residentState'.
     * 
     * @return the value of field 'residentState'.
     */
    public java.lang.String getResidentState()
    {
        return this._residentState;
    } 

    /**
     * Method getYearEndValuesPKReturns the value of field
     * 'yearEndValuesPK'.
     * 
     * @return the value of field 'yearEndValuesPK'.
     */
    public long getYearEndValuesPK()
    {
        return this._yearEndValuesPK;
    } //-- long getYearEndValuesPK() 

    /**
     * Method getULFaceAmountReturns the value of field
     * 'ulFaceAmount'.
     * 
     * @return the value of field 'ulFaceAmount'.
     */
    public java.math.BigDecimal getULFaceAmount()
    {
        return this._ulFaceAmount;
    } //-- java.math.BigDecimal getULFaceAmount() 

    /**
     * Method getTermRiderFaceReturns the value of field
     * 'termRiderFace'.
     * 
     * @return the value of field 'termRiderFace'.
     */
    public java.math.BigDecimal getTermRiderFace()
    {
        return this._termRiderFace;
    } //-- java.math.BigDecimal getTermRiderFace() 

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
     * Method hasTermRiderCount
     */
    public boolean hasTermRiderCount()
    {
        return this._has_termRiderCount;
    } //-- boolean hasTermRiderCount() 

    /**
     * Method hasYearEndValuesPK
     */
    public boolean hasYearEndValuesPK()
    {
        return this._has_yearEndValuesPK;
    } //-- boolean hasYearEndValuesPK() 

    /**
     * Method hasSegmentFK
     */
    public boolean hasSegmentFK()
    {
        return this._has_segmentFK;
    } //-- boolean hasSegmentFK() 

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
     * Method setModalPremiumSets the value of field
     * 'modalPremium'.
     * 
     * @param modalPremium the value of field 'modalPremium'
     */
    public void setAnnualPremium(java.math.BigDecimal annualPremium)
    {
        this._annualPremium = annualPremium;
        
        super.setVoChanged(true);
    } //-- void setModalPremium(java.math.BigDecimal) 

    /**
     * Method setInterimInterestSets the value of field
     * 'interimInterest'.
     * 
     * @param interimInterest the value of field
     * 'interimInterest'.
     */
    public void setInterimInterest(java.math.BigDecimal interimInterest)
    {
        this._interimInterest = interimInterest;
        
        super.setVoChanged(true);
    } //-- void setInterimInterest(java.math.BigDecimal) 

    /**
     * Method setAccumulatedValueSets the value of field
     * 'accumulatedValue'.
     * 
     * @param accumulatedValue the value of field
     * 'accumulatedValue'.
     */
    public void setAccumulatedValue(java.math.BigDecimal accumulatedValue)
    {
        this._accumulatedValue = accumulatedValue;
        
        super.setVoChanged(true);
    } //-- void setAccumulatedValue(java.math.BigDecimal) 

    /**
     * Method setYearEndDateSets the value of field
     * 'yearEndDate'.
     * 
     * @param yearEndDate the value of field 'yearEndDate'.
     */
    public void setYearEndDate(java.lang.String yearEndDate)
    {
        this._yearEndDate = yearEndDate;
        
        super.setVoChanged(true);
    } //-- void setYearEndDate(java.lang.String) 

    /**
     * Method setTermRiderCountSets the value of field
     * 'termRiderCount'.
     * 
     * @param termRiderCount the value of field
     * 'termRiderCount'.
     */
    public void setTermRiderCount(int termRiderCount)
    {
        this._termRiderCount = termRiderCount;
        
        super.setVoChanged(true);
        this._has_termRiderCount = true;
    } //-- void setTermRiderCount(int) 

    /**
     * Method setPolicyAdminStatusSets the value of field
     * 'policyAdminStatus'.
     * 
     * @param policyAdminStatus the value of field
     * 'policyAdminStatus'.
     */
    public void setPolicyAdminStatus(java.lang.String policyAdminStatus)
    {
        this._policyAdminStatus = policyAdminStatus;
        
        super.setVoChanged(true);
    } 
    
    public void setPaymentMethod(java.lang.String paymentMethod)
    {
        this._paymentMethod = paymentMethod;
        
        super.setVoChanged(true);
    } 
    
    public void setPaymentMode(java.lang.String paymentMode)
    {
        this._paymentMode = paymentMode;
        
        super.setVoChanged(true);
    } 
    
    public void setDeductionFrequency(java.lang.String deductionFrequency)
    {
        this._deductionFrequency = deductionFrequency;
        
        super.setVoChanged(true);
    } 
    
    public void setResidentState(java.lang.String residentState)
    {
        this._residentState = residentState;
        
        super.setVoChanged(true);
    } 

    /**
     * Method setYearEndValuesPKSets the value of field
     * 'yearEndValuesPK'.
     * 
     * @param yearEndValuesPK the value of field 'yearEndValuesPK'.
     */
    public void setYearEndValuesPK(long yearEndValuesPK)
    {
        this._yearEndValuesPK = yearEndValuesPK;
        
        super.setVoChanged(true);
        this._has_yearEndValuesPK = true;
    } //-- void setYearEndValuesPK(long) 

    /**
     * Method setULFaceAmountSets the value of field
     * 'ulFaceAmount'.
     * 
     * @param ulFaceAmount the value of field 'ulFaceAmount'.
     */
    public void setULFaceAmount(java.math.BigDecimal ulFaceAmount)
    {
        this._ulFaceAmount = ulFaceAmount;
        
        super.setVoChanged(true);
    } //-- void setULFaceAmount(java.math.BigDecimal) 

    /**
     * Method setTermRiderFaceSets the value of field
     * 'termRiderFace'.
     * 
     * @param termRiderFace the value of field
     * 'termRiderFace'.
     */
    public void setTermRiderFace(java.math.BigDecimal termRiderFace)
    {
        this._termRiderFace = termRiderFace;
        
        super.setVoChanged(true);
    } //-- void setTermRiderFace(java.math.BigDecimal) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.YearEndValuesVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.YearEndValuesVO) Unmarshaller.unmarshal(edit.common.vo.YearEndValuesVO.class, reader);
    } //-- edit.common.vo.YearEndValuesVO unmarshal(java.io.Reader) 

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
