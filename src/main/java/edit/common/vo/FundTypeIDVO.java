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
 * Class FundTypeIDVO.
 * 
 * @version $Revision$ $Date$
 */
public class FundTypeIDVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _fundTypeIDPK
     */
    private long _fundTypeIDPK;

    /**
     * keeps track of state for field: _fundTypeIDPK
     */
    private boolean _has_fundTypeIDPK;

    /**
     * Field _indexingMethod
     */
    private java.lang.String _indexingMethod;

    /**
     * Field _filteredFundId
     */
    private long _filteredFundId;

    /**
     * keeps track of state for field: _filteredFundId
     */
    private boolean _has_filteredFundId;

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
    private int _guaranteedMinRateDuration;

    /**
     * keeps track of state for field: _guaranteedMinRateDuration
     */
    private boolean _has_guaranteedMinRateDuration;

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


      //----------------/
     //- Constructors -/
    //----------------/

    public FundTypeIDVO() {
        super();
    } //-- edit.common.vo.FundTypeIDVO()


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
        
        if (obj instanceof FundTypeIDVO) {
        
            FundTypeIDVO temp = (FundTypeIDVO)obj;
            if (this._fundTypeIDPK != temp._fundTypeIDPK)
                return false;
            if (this._has_fundTypeIDPK != temp._has_fundTypeIDPK)
                return false;
            if (this._indexingMethod != null) {
                if (temp._indexingMethod == null) return false;
                else if (!(this._indexingMethod.equals(temp._indexingMethod))) 
                    return false;
            }
            else if (temp._indexingMethod != null)
                return false;
            if (this._filteredFundId != temp._filteredFundId)
                return false;
            if (this._has_filteredFundId != temp._has_filteredFundId)
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
            if (this._guaranteedMinRateDuration != temp._guaranteedMinRateDuration)
                return false;
            if (this._has_guaranteedMinRateDuration != temp._has_guaranteedMinRateDuration)
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
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getFilteredFundIdReturns the value of field
     * 'filteredFundId'.
     * 
     * @return the value of field 'filteredFundId'.
     */
    public long getFilteredFundId()
    {
        return this._filteredFundId;
    } //-- long getFilteredFundId() 

    /**
     * Method getFundTypeIDPKReturns the value of field
     * 'fundTypeIDPK'.
     * 
     * @return the value of field 'fundTypeIDPK'.
     */
    public long getFundTypeIDPK()
    {
        return this._fundTypeIDPK;
    } //-- long getFundTypeIDPK() 

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
    public int getGuaranteedMinRateDuration()
    {
        return this._guaranteedMinRateDuration;
    } //-- int getGuaranteedMinRateDuration() 

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
     * Method getIndexingMethodReturns the value of field
     * 'indexingMethod'.
     * 
     * @return the value of field 'indexingMethod'.
     */
    public java.lang.String getIndexingMethod()
    {
        return this._indexingMethod;
    } //-- java.lang.String getIndexingMethod() 

    /**
     * Method hasFilteredFundId
     */
    public boolean hasFilteredFundId()
    {
        return this._has_filteredFundId;
    } //-- boolean hasFilteredFundId() 

    /**
     * Method hasFundTypeIDPK
     */
    public boolean hasFundTypeIDPK()
    {
        return this._has_fundTypeIDPK;
    } //-- boolean hasFundTypeIDPK() 

    /**
     * Method hasGuaranteedMinRateDuration
     */
    public boolean hasGuaranteedMinRateDuration()
    {
        return this._has_guaranteedMinRateDuration;
    } //-- boolean hasGuaranteedMinRateDuration() 

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
     * Method setFilteredFundIdSets the value of field
     * 'filteredFundId'.
     * 
     * @param filteredFundId the value of field 'filteredFundId'.
     */
    public void setFilteredFundId(long filteredFundId)
    {
        this._filteredFundId = filteredFundId;
        
        super.setVoChanged(true);
        this._has_filteredFundId = true;
    } //-- void setFilteredFundId(long) 

    /**
     * Method setFundTypeIDPKSets the value of field
     * 'fundTypeIDPK'.
     * 
     * @param fundTypeIDPK the value of field 'fundTypeIDPK'.
     */
    public void setFundTypeIDPK(long fundTypeIDPK)
    {
        this._fundTypeIDPK = fundTypeIDPK;
        
        super.setVoChanged(true);
        this._has_fundTypeIDPK = true;
    } //-- void setFundTypeIDPK(long) 

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
    public void setGuaranteedMinRateDuration(int guaranteedMinRateDuration)
    {
        this._guaranteedMinRateDuration = guaranteedMinRateDuration;
        
        super.setVoChanged(true);
        this._has_guaranteedMinRateDuration = true;
    } //-- void setGuaranteedMinRateDuration(int) 

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
     * Method setIndexingMethodSets the value of field
     * 'indexingMethod'.
     * 
     * @param indexingMethod the value of field 'indexingMethod'.
     */
    public void setIndexingMethod(java.lang.String indexingMethod)
    {
        this._indexingMethod = indexingMethod;
        
        super.setVoChanged(true);
    } //-- void setIndexingMethod(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.FundTypeIDVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.FundTypeIDVO) Unmarshaller.unmarshal(edit.common.vo.FundTypeIDVO.class, reader);
    } //-- edit.common.vo.FundTypeIDVO unmarshal(java.io.Reader) 

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
