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
public class FinancialActivityTotalsVO extends edit.common.vo.VOObject  
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
     * Field _transactionType
     */
    private java.lang.String _transactionType;

    /**
     * Field _totalDollars
     */
    private double _totalDollars;

    /**
     * keeps track of state for field: _totalDollars
     */
    private boolean _has_totalDollars;

    /**
     * Field _totalUnits
     */
    private double _totalUnits;

    /**
     * keeps track of state for field: _totalUnits
     */
    private boolean _has_totalUnits;

    /**
     * Field _totalGainLoss
     */
    private double _totalGainLoss;

    /**
     * keeps track of state for field: _totalGainLoss
     */
    private boolean _has_totalGainLoss;


      //----------------/
     //- Constructors -/
    //----------------/

    public FinancialActivityTotalsVO() {
        super();
    } //-- edit.common.vo.FinancialActivityTotalsVO()


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
        
        if (obj instanceof FinancialActivityTotalsVO) {
        
            FinancialActivityTotalsVO temp = (FinancialActivityTotalsVO)obj;
            if (this._companyName != null) {
                if (temp._companyName == null) return false;
                else if (!(this._companyName.equals(temp._companyName))) 
                    return false;
            }
            else if (temp._companyName != null)
                return false;
            if (this._transactionType != null) {
                if (temp._transactionType == null) return false;
                else if (!(this._transactionType.equals(temp._transactionType))) 
                    return false;
            }
            else if (temp._transactionType != null)
                return false;
            if (this._totalDollars != temp._totalDollars)
                return false;
            if (this._has_totalDollars != temp._has_totalDollars)
                return false;
            if (this._totalUnits != temp._totalUnits)
                return false;
            if (this._has_totalUnits != temp._has_totalUnits)
                return false;
            if (this._totalGainLoss != temp._totalGainLoss)
                return false;
            if (this._has_totalGainLoss != temp._has_totalGainLoss)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getTotalDollarsReturns the value of field
     * 'totalDollars'.
     * 
     * @return the value of field 'totalDollars'.
     */
    public double getTotalDollars()
    {
        return this._totalDollars;
    } //-- double getTotalDollars() 

    /**
     * Method getTotalGainLossReturns the value of field
     * 'totalGainLoss'.
     * 
     * @return the value of field 'totalGainLoss'.
     */
    public double getTotalGainLoss()
    {
        return this._totalGainLoss;
    } //-- double getTotalGainLoss() 

    /**
     * Method getTotalUnitsReturns the value of field 'totalUnits'.
     * 
     * @return the value of field 'totalUnits'.
     */
    public double getTotalUnits()
    {
        return this._totalUnits;
    } //-- double getTotalUnits() 

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
     * Method hasTotalDollars
     */
    public boolean hasTotalDollars()
    {
        return this._has_totalDollars;
    } //-- boolean hasTotalDollars() 

    /**
     * Method hasTotalGainLoss
     */
    public boolean hasTotalGainLoss()
    {
        return this._has_totalGainLoss;
    } //-- boolean hasTotalGainLoss() 

    /**
     * Method hasTotalUnits
     */
    public boolean hasTotalUnits()
    {
        return this._has_totalUnits;
    } //-- boolean hasTotalUnits() 

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
     * Method setTotalDollarsSets the value of field
     * 'totalDollars'.
     * 
     * @param totalDollars the value of field 'totalDollars'.
     */
    public void setTotalDollars(double totalDollars)
    {
        this._totalDollars = totalDollars;
        
        super.setVoChanged(true);
        this._has_totalDollars = true;
    } //-- void setTotalDollars(double) 

    /**
     * Method setTotalGainLossSets the value of field
     * 'totalGainLoss'.
     * 
     * @param totalGainLoss the value of field 'totalGainLoss'.
     */
    public void setTotalGainLoss(double totalGainLoss)
    {
        this._totalGainLoss = totalGainLoss;
        
        super.setVoChanged(true);
        this._has_totalGainLoss = true;
    } //-- void setTotalGainLoss(double) 

    /**
     * Method setTotalUnitsSets the value of field 'totalUnits'.
     * 
     * @param totalUnits the value of field 'totalUnits'.
     */
    public void setTotalUnits(double totalUnits)
    {
        this._totalUnits = totalUnits;
        
        super.setVoChanged(true);
        this._has_totalUnits = true;
    } //-- void setTotalUnits(double) 

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
    public static edit.common.vo.FinancialActivityTotalsVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.FinancialActivityTotalsVO) Unmarshaller.unmarshal(edit.common.vo.FinancialActivityTotalsVO.class, reader);
    } //-- edit.common.vo.FinancialActivityTotalsVO unmarshal(java.io.Reader) 

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
