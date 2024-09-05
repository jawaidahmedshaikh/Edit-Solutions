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
 * Class InvestmentHistoryVO.
 * 
 * @version $Revision$ $Date$
 */
public class InvestmentHistoryVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _investmentHistoryPK
     */
    private long _investmentHistoryPK;

    /**
     * keeps track of state for field: _investmentHistoryPK
     */
    private boolean _has_investmentHistoryPK;

    /**
     * Field _EDITTrxHistoryFK
     */
    private long _EDITTrxHistoryFK;

    /**
     * keeps track of state for field: _EDITTrxHistoryFK
     */
    private boolean _has_EDITTrxHistoryFK;

    /**
     * Field _investmentFK
     */
    private long _investmentFK;

    /**
     * keeps track of state for field: _investmentFK
     */
    private boolean _has_investmentFK;

    /**
     * Field _investmentDollars
     */
    private java.math.BigDecimal _investmentDollars;

    /**
     * Field _investmentUnits
     */
    private java.math.BigDecimal _investmentUnits;

    /**
     * Field _toFromStatus
     */
    private java.lang.String _toFromStatus;

    /**
     * Field _valuationDate
     */
    private java.lang.String _valuationDate;

    /**
     * Field _gainLoss
     */
    private java.math.BigDecimal _gainLoss;

    /**
     * Field _chargeCodeFK
     */
    private long _chargeCodeFK;

    /**
     * keeps track of state for field: _chargeCodeFK
     */
    private boolean _has_chargeCodeFK;

    /**
     * Field _previousChargeCodeFK
     */
    private long _previousChargeCodeFK;

    /**
     * keeps track of state for field: _previousChargeCodeFK
     */
    private boolean _has_previousChargeCodeFK;

    /**
     * Field _finalPriceStatus
     */
    private java.lang.String _finalPriceStatus;


      //----------------/
     //- Constructors -/
    //----------------/

    public InvestmentHistoryVO() {
        super();
    } //-- edit.common.vo.InvestmentHistoryVO()


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
        
        if (obj instanceof InvestmentHistoryVO) {
        
            InvestmentHistoryVO temp = (InvestmentHistoryVO)obj;
            if (this._investmentHistoryPK != temp._investmentHistoryPK)
                return false;
            if (this._has_investmentHistoryPK != temp._has_investmentHistoryPK)
                return false;
            if (this._EDITTrxHistoryFK != temp._EDITTrxHistoryFK)
                return false;
            if (this._has_EDITTrxHistoryFK != temp._has_EDITTrxHistoryFK)
                return false;
            if (this._investmentFK != temp._investmentFK)
                return false;
            if (this._has_investmentFK != temp._has_investmentFK)
                return false;
            if (this._investmentDollars != null) {
                if (temp._investmentDollars == null) return false;
                else if (!(this._investmentDollars.equals(temp._investmentDollars))) 
                    return false;
            }
            else if (temp._investmentDollars != null)
                return false;
            if (this._investmentUnits != null) {
                if (temp._investmentUnits == null) return false;
                else if (!(this._investmentUnits.equals(temp._investmentUnits))) 
                    return false;
            }
            else if (temp._investmentUnits != null)
                return false;
            if (this._toFromStatus != null) {
                if (temp._toFromStatus == null) return false;
                else if (!(this._toFromStatus.equals(temp._toFromStatus))) 
                    return false;
            }
            else if (temp._toFromStatus != null)
                return false;
            if (this._valuationDate != null) {
                if (temp._valuationDate == null) return false;
                else if (!(this._valuationDate.equals(temp._valuationDate))) 
                    return false;
            }
            else if (temp._valuationDate != null)
                return false;
            if (this._gainLoss != null) {
                if (temp._gainLoss == null) return false;
                else if (!(this._gainLoss.equals(temp._gainLoss))) 
                    return false;
            }
            else if (temp._gainLoss != null)
                return false;
            if (this._chargeCodeFK != temp._chargeCodeFK)
                return false;
            if (this._has_chargeCodeFK != temp._has_chargeCodeFK)
                return false;
            if (this._previousChargeCodeFK != temp._previousChargeCodeFK)
                return false;
            if (this._has_previousChargeCodeFK != temp._has_previousChargeCodeFK)
                return false;
            if (this._finalPriceStatus != null) {
                if (temp._finalPriceStatus == null) return false;
                else if (!(this._finalPriceStatus.equals(temp._finalPriceStatus))) 
                    return false;
            }
            else if (temp._finalPriceStatus != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getChargeCodeFKReturns the value of field
     * 'chargeCodeFK'.
     * 
     * @return the value of field 'chargeCodeFK'.
     */
    public long getChargeCodeFK()
    {
        return this._chargeCodeFK;
    } //-- long getChargeCodeFK() 

    /**
     * Method getEDITTrxHistoryFKReturns the value of field
     * 'EDITTrxHistoryFK'.
     * 
     * @return the value of field 'EDITTrxHistoryFK'.
     */
    public long getEDITTrxHistoryFK()
    {
        return this._EDITTrxHistoryFK;
    } //-- long getEDITTrxHistoryFK() 

    /**
     * Method getFinalPriceStatusReturns the value of field
     * 'finalPriceStatus'.
     * 
     * @return the value of field 'finalPriceStatus'.
     */
    public java.lang.String getFinalPriceStatus()
    {
        return this._finalPriceStatus;
    } //-- java.lang.String getFinalPriceStatus() 

    /**
     * Method getGainLossReturns the value of field 'gainLoss'.
     * 
     * @return the value of field 'gainLoss'.
     */
    public java.math.BigDecimal getGainLoss()
    {
        return this._gainLoss;
    } //-- java.math.BigDecimal getGainLoss() 

    /**
     * Method getInvestmentDollarsReturns the value of field
     * 'investmentDollars'.
     * 
     * @return the value of field 'investmentDollars'.
     */
    public java.math.BigDecimal getInvestmentDollars()
    {
        return this._investmentDollars;
    } //-- java.math.BigDecimal getInvestmentDollars() 

    /**
     * Method getInvestmentFKReturns the value of field
     * 'investmentFK'.
     * 
     * @return the value of field 'investmentFK'.
     */
    public long getInvestmentFK()
    {
        return this._investmentFK;
    } //-- long getInvestmentFK() 

    /**
     * Method getInvestmentHistoryPKReturns the value of field
     * 'investmentHistoryPK'.
     * 
     * @return the value of field 'investmentHistoryPK'.
     */
    public long getInvestmentHistoryPK()
    {
        return this._investmentHistoryPK;
    } //-- long getInvestmentHistoryPK() 

    /**
     * Method getInvestmentUnitsReturns the value of field
     * 'investmentUnits'.
     * 
     * @return the value of field 'investmentUnits'.
     */
    public java.math.BigDecimal getInvestmentUnits()
    {
        return this._investmentUnits;
    } //-- java.math.BigDecimal getInvestmentUnits() 

    /**
     * Method getPreviousChargeCodeFKReturns the value of field
     * 'previousChargeCodeFK'.
     * 
     * @return the value of field 'previousChargeCodeFK'.
     */
    public long getPreviousChargeCodeFK()
    {
        return this._previousChargeCodeFK;
    } //-- long getPreviousChargeCodeFK() 

    /**
     * Method getToFromStatusReturns the value of field
     * 'toFromStatus'.
     * 
     * @return the value of field 'toFromStatus'.
     */
    public java.lang.String getToFromStatus()
    {
        return this._toFromStatus;
    } //-- java.lang.String getToFromStatus() 

    /**
     * Method getValuationDateReturns the value of field
     * 'valuationDate'.
     * 
     * @return the value of field 'valuationDate'.
     */
    public java.lang.String getValuationDate()
    {
        return this._valuationDate;
    } //-- java.lang.String getValuationDate() 

    /**
     * Method hasChargeCodeFK
     */
    public boolean hasChargeCodeFK()
    {
        return this._has_chargeCodeFK;
    } //-- boolean hasChargeCodeFK() 

    /**
     * Method hasEDITTrxHistoryFK
     */
    public boolean hasEDITTrxHistoryFK()
    {
        return this._has_EDITTrxHistoryFK;
    } //-- boolean hasEDITTrxHistoryFK() 

    /**
     * Method hasInvestmentFK
     */
    public boolean hasInvestmentFK()
    {
        return this._has_investmentFK;
    } //-- boolean hasInvestmentFK() 

    /**
     * Method hasInvestmentHistoryPK
     */
    public boolean hasInvestmentHistoryPK()
    {
        return this._has_investmentHistoryPK;
    } //-- boolean hasInvestmentHistoryPK() 

    /**
     * Method hasPreviousChargeCodeFK
     */
    public boolean hasPreviousChargeCodeFK()
    {
        return this._has_previousChargeCodeFK;
    } //-- boolean hasPreviousChargeCodeFK() 

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
     * Method setChargeCodeFKSets the value of field
     * 'chargeCodeFK'.
     * 
     * @param chargeCodeFK the value of field 'chargeCodeFK'.
     */
    public void setChargeCodeFK(long chargeCodeFK)
    {
        this._chargeCodeFK = chargeCodeFK;
        
        super.setVoChanged(true);
        this._has_chargeCodeFK = true;
    } //-- void setChargeCodeFK(long) 

    /**
     * Method setEDITTrxHistoryFKSets the value of field
     * 'EDITTrxHistoryFK'.
     * 
     * @param EDITTrxHistoryFK the value of field 'EDITTrxHistoryFK'
     */
    public void setEDITTrxHistoryFK(long EDITTrxHistoryFK)
    {
        this._EDITTrxHistoryFK = EDITTrxHistoryFK;
        
        super.setVoChanged(true);
        this._has_EDITTrxHistoryFK = true;
    } //-- void setEDITTrxHistoryFK(long) 

    /**
     * Method setFinalPriceStatusSets the value of field
     * 'finalPriceStatus'.
     * 
     * @param finalPriceStatus the value of field 'finalPriceStatus'
     */
    public void setFinalPriceStatus(java.lang.String finalPriceStatus)
    {
        this._finalPriceStatus = finalPriceStatus;
        
        super.setVoChanged(true);
    } //-- void setFinalPriceStatus(java.lang.String) 

    /**
     * Method setGainLossSets the value of field 'gainLoss'.
     * 
     * @param gainLoss the value of field 'gainLoss'.
     */
    public void setGainLoss(java.math.BigDecimal gainLoss)
    {
        this._gainLoss = gainLoss;
        
        super.setVoChanged(true);
    } //-- void setGainLoss(java.math.BigDecimal) 

    /**
     * Method setInvestmentDollarsSets the value of field
     * 'investmentDollars'.
     * 
     * @param investmentDollars the value of field
     * 'investmentDollars'.
     */
    public void setInvestmentDollars(java.math.BigDecimal investmentDollars)
    {
        this._investmentDollars = investmentDollars;
        
        super.setVoChanged(true);
    } //-- void setInvestmentDollars(java.math.BigDecimal) 

    /**
     * Method setInvestmentFKSets the value of field
     * 'investmentFK'.
     * 
     * @param investmentFK the value of field 'investmentFK'.
     */
    public void setInvestmentFK(long investmentFK)
    {
        this._investmentFK = investmentFK;
        
        super.setVoChanged(true);
        this._has_investmentFK = true;
    } //-- void setInvestmentFK(long) 

    /**
     * Method setInvestmentHistoryPKSets the value of field
     * 'investmentHistoryPK'.
     * 
     * @param investmentHistoryPK the value of field
     * 'investmentHistoryPK'.
     */
    public void setInvestmentHistoryPK(long investmentHistoryPK)
    {
        this._investmentHistoryPK = investmentHistoryPK;
        
        super.setVoChanged(true);
        this._has_investmentHistoryPK = true;
    } //-- void setInvestmentHistoryPK(long) 

    /**
     * Method setInvestmentUnitsSets the value of field
     * 'investmentUnits'.
     * 
     * @param investmentUnits the value of field 'investmentUnits'.
     */
    public void setInvestmentUnits(java.math.BigDecimal investmentUnits)
    {
        this._investmentUnits = investmentUnits;
        
        super.setVoChanged(true);
    } //-- void setInvestmentUnits(java.math.BigDecimal) 

    /**
     * Method setPreviousChargeCodeFKSets the value of field
     * 'previousChargeCodeFK'.
     * 
     * @param previousChargeCodeFK the value of field
     * 'previousChargeCodeFK'.
     */
    public void setPreviousChargeCodeFK(long previousChargeCodeFK)
    {
        this._previousChargeCodeFK = previousChargeCodeFK;
        
        super.setVoChanged(true);
        this._has_previousChargeCodeFK = true;
    } //-- void setPreviousChargeCodeFK(long) 

    /**
     * Method setToFromStatusSets the value of field
     * 'toFromStatus'.
     * 
     * @param toFromStatus the value of field 'toFromStatus'.
     */
    public void setToFromStatus(java.lang.String toFromStatus)
    {
        this._toFromStatus = toFromStatus;
        
        super.setVoChanged(true);
    } //-- void setToFromStatus(java.lang.String) 

    /**
     * Method setValuationDateSets the value of field
     * 'valuationDate'.
     * 
     * @param valuationDate the value of field 'valuationDate'.
     */
    public void setValuationDate(java.lang.String valuationDate)
    {
        this._valuationDate = valuationDate;
        
        super.setVoChanged(true);
    } //-- void setValuationDate(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.InvestmentHistoryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.InvestmentHistoryVO) Unmarshaller.unmarshal(edit.common.vo.InvestmentHistoryVO.class, reader);
    } //-- edit.common.vo.InvestmentHistoryVO unmarshal(java.io.Reader) 

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
