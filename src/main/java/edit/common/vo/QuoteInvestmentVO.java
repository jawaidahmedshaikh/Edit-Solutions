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
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class QuoteInvestmentVO.
 * 
 * @version $Revision$ $Date$
 */
public class QuoteInvestmentVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _quoteInvestmentPK
     */
    private long _quoteInvestmentPK;

    /**
     * keeps track of state for field: _quoteInvestmentPK
     */
    private boolean _has_quoteInvestmentPK;

    /**
     * Field _investmentVO
     */
    private edit.common.vo.InvestmentVO _investmentVO;

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
     * Field _unitValueDate
     */
    private java.lang.String _unitValueDate;

    /**
     * Field _unitValue
     */
    private java.math.BigDecimal _unitValue;

    /**
     * Field _quoteBucketVOList
     */
    private java.util.Vector _quoteBucketVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public QuoteInvestmentVO() {
        super();
        _quoteBucketVOList = new Vector();
    } //-- edit.common.vo.QuoteInvestmentVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addQuoteBucketVO
     * 
     * @param vQuoteBucketVO
     */
    public void addQuoteBucketVO(edit.common.vo.QuoteBucketVO vQuoteBucketVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vQuoteBucketVO.setParentVO(this.getClass(), this);
        _quoteBucketVOList.addElement(vQuoteBucketVO);
    } //-- void addQuoteBucketVO(edit.common.vo.QuoteBucketVO) 

    /**
     * Method addQuoteBucketVO
     * 
     * @param index
     * @param vQuoteBucketVO
     */
    public void addQuoteBucketVO(int index, edit.common.vo.QuoteBucketVO vQuoteBucketVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vQuoteBucketVO.setParentVO(this.getClass(), this);
        _quoteBucketVOList.insertElementAt(vQuoteBucketVO, index);
    } //-- void addQuoteBucketVO(int, edit.common.vo.QuoteBucketVO) 

    /**
     * Method enumerateQuoteBucketVO
     */
    public java.util.Enumeration enumerateQuoteBucketVO()
    {
        return _quoteBucketVOList.elements();
    } //-- java.util.Enumeration enumerateQuoteBucketVO() 

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
        
        if (obj instanceof QuoteInvestmentVO) {
        
            QuoteInvestmentVO temp = (QuoteInvestmentVO)obj;
            if (this._quoteInvestmentPK != temp._quoteInvestmentPK)
                return false;
            if (this._has_quoteInvestmentPK != temp._has_quoteInvestmentPK)
                return false;
            if (this._investmentVO != null) {
                if (temp._investmentVO == null) return false;
                else if (!(this._investmentVO.equals(temp._investmentVO))) 
                    return false;
            }
            else if (temp._investmentVO != null)
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
            if (this._unitValueDate != null) {
                if (temp._unitValueDate == null) return false;
                else if (!(this._unitValueDate.equals(temp._unitValueDate))) 
                    return false;
            }
            else if (temp._unitValueDate != null)
                return false;
            if (this._unitValue != null) {
                if (temp._unitValue == null) return false;
                else if (!(this._unitValue.equals(temp._unitValue))) 
                    return false;
            }
            else if (temp._unitValue != null)
                return false;
            if (this._quoteBucketVOList != null) {
                if (temp._quoteBucketVOList == null) return false;
                else if (!(this._quoteBucketVOList.equals(temp._quoteBucketVOList))) 
                    return false;
            }
            else if (temp._quoteBucketVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getInvestmentVOReturns the value of field
     * 'investmentVO'.
     * 
     * @return the value of field 'investmentVO'.
     */
    public edit.common.vo.InvestmentVO getInvestmentVO()
    {
        return this._investmentVO;
    } //-- edit.common.vo.InvestmentVO getInvestmentVO() 

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
     * Method getQuoteBucketVO
     * 
     * @param index
     */
    public edit.common.vo.QuoteBucketVO getQuoteBucketVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _quoteBucketVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.QuoteBucketVO) _quoteBucketVOList.elementAt(index);
    } //-- edit.common.vo.QuoteBucketVO getQuoteBucketVO(int) 

    /**
     * Method getQuoteBucketVO
     */
    public edit.common.vo.QuoteBucketVO[] getQuoteBucketVO()
    {
        int size = _quoteBucketVOList.size();
        edit.common.vo.QuoteBucketVO[] mArray = new edit.common.vo.QuoteBucketVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.QuoteBucketVO) _quoteBucketVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.QuoteBucketVO[] getQuoteBucketVO() 

    /**
     * Method getQuoteBucketVOCount
     */
    public int getQuoteBucketVOCount()
    {
        return _quoteBucketVOList.size();
    } //-- int getQuoteBucketVOCount() 

    /**
     * Method getQuoteInvestmentPKReturns the value of field
     * 'quoteInvestmentPK'.
     * 
     * @return the value of field 'quoteInvestmentPK'.
     */
    public long getQuoteInvestmentPK()
    {
        return this._quoteInvestmentPK;
    } //-- long getQuoteInvestmentPK() 

    /**
     * Method getUnitValueReturns the value of field 'unitValue'.
     * 
     * @return the value of field 'unitValue'.
     */
    public java.math.BigDecimal getUnitValue()
    {
        return this._unitValue;
    } //-- java.math.BigDecimal getUnitValue() 

    /**
     * Method getUnitValueDateReturns the value of field
     * 'unitValueDate'.
     * 
     * @return the value of field 'unitValueDate'.
     */
    public java.lang.String getUnitValueDate()
    {
        return this._unitValueDate;
    } //-- java.lang.String getUnitValueDate() 

    /**
     * Method hasQuoteInvestmentPK
     */
    public boolean hasQuoteInvestmentPK()
    {
        return this._has_quoteInvestmentPK;
    } //-- boolean hasQuoteInvestmentPK() 

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
     * Method removeAllQuoteBucketVO
     */
    public void removeAllQuoteBucketVO()
    {
        _quoteBucketVOList.removeAllElements();
    } //-- void removeAllQuoteBucketVO() 

    /**
     * Method removeQuoteBucketVO
     * 
     * @param index
     */
    public edit.common.vo.QuoteBucketVO removeQuoteBucketVO(int index)
    {
        java.lang.Object obj = _quoteBucketVOList.elementAt(index);
        _quoteBucketVOList.removeElementAt(index);
        return (edit.common.vo.QuoteBucketVO) obj;
    } //-- edit.common.vo.QuoteBucketVO removeQuoteBucketVO(int) 

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
     * Method setInvestmentVOSets the value of field
     * 'investmentVO'.
     * 
     * @param investmentVO the value of field 'investmentVO'.
     */
    public void setInvestmentVO(edit.common.vo.InvestmentVO investmentVO)
    {
        this._investmentVO = investmentVO;
    } //-- void setInvestmentVO(edit.common.vo.InvestmentVO) 

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
     * Method setQuoteBucketVO
     * 
     * @param index
     * @param vQuoteBucketVO
     */
    public void setQuoteBucketVO(int index, edit.common.vo.QuoteBucketVO vQuoteBucketVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _quoteBucketVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vQuoteBucketVO.setParentVO(this.getClass(), this);
        _quoteBucketVOList.setElementAt(vQuoteBucketVO, index);
    } //-- void setQuoteBucketVO(int, edit.common.vo.QuoteBucketVO) 

    /**
     * Method setQuoteBucketVO
     * 
     * @param quoteBucketVOArray
     */
    public void setQuoteBucketVO(edit.common.vo.QuoteBucketVO[] quoteBucketVOArray)
    {
        //-- copy array
        _quoteBucketVOList.removeAllElements();
        for (int i = 0; i < quoteBucketVOArray.length; i++) {
            quoteBucketVOArray[i].setParentVO(this.getClass(), this);
            _quoteBucketVOList.addElement(quoteBucketVOArray[i]);
        }
    } //-- void setQuoteBucketVO(edit.common.vo.QuoteBucketVO) 

    /**
     * Method setQuoteInvestmentPKSets the value of field
     * 'quoteInvestmentPK'.
     * 
     * @param quoteInvestmentPK the value of field
     * 'quoteInvestmentPK'.
     */
    public void setQuoteInvestmentPK(long quoteInvestmentPK)
    {
        this._quoteInvestmentPK = quoteInvestmentPK;
        
        super.setVoChanged(true);
        this._has_quoteInvestmentPK = true;
    } //-- void setQuoteInvestmentPK(long) 

    /**
     * Method setUnitValueSets the value of field 'unitValue'.
     * 
     * @param unitValue the value of field 'unitValue'.
     */
    public void setUnitValue(java.math.BigDecimal unitValue)
    {
        this._unitValue = unitValue;
        
        super.setVoChanged(true);
    } //-- void setUnitValue(java.math.BigDecimal) 

    /**
     * Method setUnitValueDateSets the value of field
     * 'unitValueDate'.
     * 
     * @param unitValueDate the value of field 'unitValueDate'.
     */
    public void setUnitValueDate(java.lang.String unitValueDate)
    {
        this._unitValueDate = unitValueDate;
        
        super.setVoChanged(true);
    } //-- void setUnitValueDate(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.QuoteInvestmentVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.QuoteInvestmentVO) Unmarshaller.unmarshal(edit.common.vo.QuoteInvestmentVO.class, reader);
    } //-- edit.common.vo.QuoteInvestmentVO unmarshal(java.io.Reader) 

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
