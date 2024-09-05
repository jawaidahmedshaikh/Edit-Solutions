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
 * The rates retrieved from PRASE for a given investment.
 * 
 * @version $Revision$ $Date$
 */
public class RatesVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _filteredFundFK
     */
    private long _filteredFundFK;

    /**
     * keeps track of state for field: _filteredFundFK
     */
    private boolean _has_filteredFundFK;

    /**
     * Field _guaranteedRate
     */
    private java.math.BigDecimal _guaranteedRate;

    /**
     * Field _bonusRate
     */
    private java.math.BigDecimal _bonusRate;

    /**
     * Field _initialGuaranteePeriod
     */
    private int _initialGuaranteePeriod;

    /**
     * keeps track of state for field: _initialGuaranteePeriod
     */
    private boolean _has_initialGuaranteePeriod;

    /**
     * Field _minimumCap
     */
    private java.math.BigDecimal _minimumCap;

    /**
     * Field _participationRate
     */
    private java.math.BigDecimal _participationRate;

    /**
     * Field _indexMargin
     */
    private java.math.BigDecimal _indexMargin;

    /**
     * Field _fundType
     */
    private java.lang.String _fundType;

    /**
     * Field _indexingMethod
     */
    private java.lang.String _indexingMethod;

    /**
     * Field _indexAtIssue
     */
    private java.math.BigDecimal _indexAtIssue;

    /**
     * Field _capRateGuar
     */
    private int _capRateGuar;

    /**
     * keeps track of state for field: _capRateGuar
     */
    private boolean _has_capRateGuar;


      //----------------/
     //- Constructors -/
    //----------------/

    public RatesVO() {
        super();
    } //-- edit.common.vo.RatesVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteInitialGuaranteePeriod
     */
    public void deleteInitialGuaranteePeriod()
    {
        this._has_initialGuaranteePeriod= false;
    } //-- void deleteInitialGuaranteePeriod() 

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
        
        if (obj instanceof RatesVO) {
        
            RatesVO temp = (RatesVO)obj;
            if (this._filteredFundFK != temp._filteredFundFK)
                return false;
            if (this._has_filteredFundFK != temp._has_filteredFundFK)
                return false;
            if (this._guaranteedRate != null) {
                if (temp._guaranteedRate == null) return false;
                else if (!(this._guaranteedRate.equals(temp._guaranteedRate))) 
                    return false;
            }
            else if (temp._guaranteedRate != null)
                return false;
            if (this._bonusRate != null) {
                if (temp._bonusRate == null) return false;
                else if (!(this._bonusRate.equals(temp._bonusRate))) 
                    return false;
            }
            else if (temp._bonusRate != null)
                return false;
            if (this._initialGuaranteePeriod != temp._initialGuaranteePeriod)
                return false;
            if (this._has_initialGuaranteePeriod != temp._has_initialGuaranteePeriod)
                return false;
            if (this._minimumCap != null) {
                if (temp._minimumCap == null) return false;
                else if (!(this._minimumCap.equals(temp._minimumCap))) 
                    return false;
            }
            else if (temp._minimumCap != null)
                return false;
            if (this._participationRate != null) {
                if (temp._participationRate == null) return false;
                else if (!(this._participationRate.equals(temp._participationRate))) 
                    return false;
            }
            else if (temp._participationRate != null)
                return false;
            if (this._indexMargin != null) {
                if (temp._indexMargin == null) return false;
                else if (!(this._indexMargin.equals(temp._indexMargin))) 
                    return false;
            }
            else if (temp._indexMargin != null)
                return false;
            if (this._fundType != null) {
                if (temp._fundType == null) return false;
                else if (!(this._fundType.equals(temp._fundType))) 
                    return false;
            }
            else if (temp._fundType != null)
                return false;
            if (this._indexingMethod != null) {
                if (temp._indexingMethod == null) return false;
                else if (!(this._indexingMethod.equals(temp._indexingMethod))) 
                    return false;
            }
            else if (temp._indexingMethod != null)
                return false;
            if (this._indexAtIssue != null) {
                if (temp._indexAtIssue == null) return false;
                else if (!(this._indexAtIssue.equals(temp._indexAtIssue))) 
                    return false;
            }
            else if (temp._indexAtIssue != null)
                return false;
            if (this._capRateGuar != temp._capRateGuar)
                return false;
            if (this._has_capRateGuar != temp._has_capRateGuar)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBonusRateReturns the value of field 'bonusRate'.
     * 
     * @return the value of field 'bonusRate'.
     */
    public java.math.BigDecimal getBonusRate()
    {
        return this._bonusRate;
    } //-- java.math.BigDecimal getBonusRate() 

    /**
     * Method getCapRateGuarReturns the value of field
     * 'capRateGuar'.
     * 
     * @return the value of field 'capRateGuar'.
     */
    public int getCapRateGuar()
    {
        return this._capRateGuar;
    } //-- int getCapRateGuar() 

    /**
     * Method getFilteredFundFKReturns the value of field
     * 'filteredFundFK'.
     * 
     * @return the value of field 'filteredFundFK'.
     */
    public long getFilteredFundFK()
    {
        return this._filteredFundFK;
    } //-- long getFilteredFundFK() 

    /**
     * Method getFundTypeReturns the value of field 'fundType'.
     * 
     * @return the value of field 'fundType'.
     */
    public java.lang.String getFundType()
    {
        return this._fundType;
    } //-- java.lang.String getFundType() 

    /**
     * Method getGuaranteedRateReturns the value of field
     * 'guaranteedRate'.
     * 
     * @return the value of field 'guaranteedRate'.
     */
    public java.math.BigDecimal getGuaranteedRate()
    {
        return this._guaranteedRate;
    } //-- java.math.BigDecimal getGuaranteedRate() 

    /**
     * Method getIndexAtIssueReturns the value of field
     * 'indexAtIssue'.
     * 
     * @return the value of field 'indexAtIssue'.
     */
    public java.math.BigDecimal getIndexAtIssue()
    {
        return this._indexAtIssue;
    } //-- java.math.BigDecimal getIndexAtIssue() 

    /**
     * Method getIndexMarginReturns the value of field
     * 'indexMargin'.
     * 
     * @return the value of field 'indexMargin'.
     */
    public java.math.BigDecimal getIndexMargin()
    {
        return this._indexMargin;
    } //-- java.math.BigDecimal getIndexMargin() 

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
     * Method getInitialGuaranteePeriodReturns the value of field
     * 'initialGuaranteePeriod'.
     * 
     * @return the value of field 'initialGuaranteePeriod'.
     */
    public int getInitialGuaranteePeriod()
    {
        return this._initialGuaranteePeriod;
    } //-- int getInitialGuaranteePeriod() 

    /**
     * Method getMinimumCapReturns the value of field 'minimumCap'.
     * 
     * @return the value of field 'minimumCap'.
     */
    public java.math.BigDecimal getMinimumCap()
    {
        return this._minimumCap;
    } //-- java.math.BigDecimal getMinimumCap() 

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
     * Method hasCapRateGuar
     */
    public boolean hasCapRateGuar()
    {
        return this._has_capRateGuar;
    } //-- boolean hasCapRateGuar() 

    /**
     * Method hasFilteredFundFK
     */
    public boolean hasFilteredFundFK()
    {
        return this._has_filteredFundFK;
    } //-- boolean hasFilteredFundFK() 

    /**
     * Method hasInitialGuaranteePeriod
     */
    public boolean hasInitialGuaranteePeriod()
    {
        return this._has_initialGuaranteePeriod;
    } //-- boolean hasInitialGuaranteePeriod() 

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
     * Method setBonusRateSets the value of field 'bonusRate'.
     * 
     * @param bonusRate the value of field 'bonusRate'.
     */
    public void setBonusRate(java.math.BigDecimal bonusRate)
    {
        this._bonusRate = bonusRate;
        
        super.setVoChanged(true);
    } //-- void setBonusRate(java.math.BigDecimal) 

    /**
     * Method setCapRateGuarSets the value of field 'capRateGuar'.
     * 
     * @param capRateGuar the value of field 'capRateGuar'.
     */
    public void setCapRateGuar(int capRateGuar)
    {
        this._capRateGuar = capRateGuar;
        
        super.setVoChanged(true);
        this._has_capRateGuar = true;
    } //-- void setCapRateGuar(int) 

    /**
     * Method setFilteredFundFKSets the value of field
     * 'filteredFundFK'.
     * 
     * @param filteredFundFK the value of field 'filteredFundFK'.
     */
    public void setFilteredFundFK(long filteredFundFK)
    {
        this._filteredFundFK = filteredFundFK;
        
        super.setVoChanged(true);
        this._has_filteredFundFK = true;
    } //-- void setFilteredFundFK(long) 

    /**
     * Method setFundTypeSets the value of field 'fundType'.
     * 
     * @param fundType the value of field 'fundType'.
     */
    public void setFundType(java.lang.String fundType)
    {
        this._fundType = fundType;
        
        super.setVoChanged(true);
    } //-- void setFundType(java.lang.String) 

    /**
     * Method setGuaranteedRateSets the value of field
     * 'guaranteedRate'.
     * 
     * @param guaranteedRate the value of field 'guaranteedRate'.
     */
    public void setGuaranteedRate(java.math.BigDecimal guaranteedRate)
    {
        this._guaranteedRate = guaranteedRate;
        
        super.setVoChanged(true);
    } //-- void setGuaranteedRate(java.math.BigDecimal) 

    /**
     * Method setIndexAtIssueSets the value of field
     * 'indexAtIssue'.
     * 
     * @param indexAtIssue the value of field 'indexAtIssue'.
     */
    public void setIndexAtIssue(java.math.BigDecimal indexAtIssue)
    {
        this._indexAtIssue = indexAtIssue;
        
        super.setVoChanged(true);
    } //-- void setIndexAtIssue(java.math.BigDecimal) 

    /**
     * Method setIndexMarginSets the value of field 'indexMargin'.
     * 
     * @param indexMargin the value of field 'indexMargin'.
     */
    public void setIndexMargin(java.math.BigDecimal indexMargin)
    {
        this._indexMargin = indexMargin;
        
        super.setVoChanged(true);
    } //-- void setIndexMargin(java.math.BigDecimal) 

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
     * Method setInitialGuaranteePeriodSets the value of field
     * 'initialGuaranteePeriod'.
     * 
     * @param initialGuaranteePeriod the value of field
     * 'initialGuaranteePeriod'.
     */
    public void setInitialGuaranteePeriod(int initialGuaranteePeriod)
    {
        this._initialGuaranteePeriod = initialGuaranteePeriod;
        
        super.setVoChanged(true);
        this._has_initialGuaranteePeriod = true;
    } //-- void setInitialGuaranteePeriod(int) 

    /**
     * Method setMinimumCapSets the value of field 'minimumCap'.
     * 
     * @param minimumCap the value of field 'minimumCap'.
     */
    public void setMinimumCap(java.math.BigDecimal minimumCap)
    {
        this._minimumCap = minimumCap;
        
        super.setVoChanged(true);
    } //-- void setMinimumCap(java.math.BigDecimal) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.RatesVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.RatesVO) Unmarshaller.unmarshal(edit.common.vo.RatesVO.class, reader);
    } //-- edit.common.vo.RatesVO unmarshal(java.io.Reader) 

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
