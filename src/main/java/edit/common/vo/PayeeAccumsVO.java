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
public class PayeeAccumsVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _amountPaidToDate
     */
    private double _amountPaidToDate;

    /**
     * keeps track of state for field: _amountPaidToDate
     */
    private boolean _has_amountPaidToDate;

    /**
     * Field _amountPaidYTD
     */
    private double _amountPaidYTD;

    /**
     * keeps track of state for field: _amountPaidYTD
     */
    private boolean _has_amountPaidYTD;

    /**
     * Field _federalWithholdingYTD
     */
    private double _federalWithholdingYTD;

    /**
     * keeps track of state for field: _federalWithholdingYTD
     */
    private boolean _has_federalWithholdingYTD;

    /**
     * Field _stateWithholdingYTD
     */
    private double _stateWithholdingYTD;

    /**
     * keeps track of state for field: _stateWithholdingYTD
     */
    private boolean _has_stateWithholdingYTD;

    /**
     * Field _cityWithholdingYTD
     */
    private double _cityWithholdingYTD;

    /**
     * keeps track of state for field: _cityWithholdingYTD
     */
    private boolean _has_cityWithholdingYTD;

    /**
     * Field _countyWithholdingYTD
     */
    private double _countyWithholdingYTD;

    /**
     * keeps track of state for field: _countyWithholdingYTD
     */
    private boolean _has_countyWithholdingYTD;

    /**
     * Field _clientFK
     */
    private long _clientFK;

    /**
     * keeps track of state for field: _clientFK
     */
    private boolean _has_clientFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public PayeeAccumsVO() {
        super();
    } //-- edit.common.vo.PayeeAccumsVO()


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
        
        if (obj instanceof PayeeAccumsVO) {
        
            PayeeAccumsVO temp = (PayeeAccumsVO)obj;
            if (this._amountPaidToDate != temp._amountPaidToDate)
                return false;
            if (this._has_amountPaidToDate != temp._has_amountPaidToDate)
                return false;
            if (this._amountPaidYTD != temp._amountPaidYTD)
                return false;
            if (this._has_amountPaidYTD != temp._has_amountPaidYTD)
                return false;
            if (this._federalWithholdingYTD != temp._federalWithholdingYTD)
                return false;
            if (this._has_federalWithholdingYTD != temp._has_federalWithholdingYTD)
                return false;
            if (this._stateWithholdingYTD != temp._stateWithholdingYTD)
                return false;
            if (this._has_stateWithholdingYTD != temp._has_stateWithholdingYTD)
                return false;
            if (this._cityWithholdingYTD != temp._cityWithholdingYTD)
                return false;
            if (this._has_cityWithholdingYTD != temp._has_cityWithholdingYTD)
                return false;
            if (this._countyWithholdingYTD != temp._countyWithholdingYTD)
                return false;
            if (this._has_countyWithholdingYTD != temp._has_countyWithholdingYTD)
                return false;
            if (this._clientFK != temp._clientFK)
                return false;
            if (this._has_clientFK != temp._has_clientFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAmountPaidToDateReturns the value of field
     * 'amountPaidToDate'.
     * 
     * @return the value of field 'amountPaidToDate'.
     */
    public double getAmountPaidToDate()
    {
        return this._amountPaidToDate;
    } //-- double getAmountPaidToDate() 

    /**
     * Method getAmountPaidYTDReturns the value of field
     * 'amountPaidYTD'.
     * 
     * @return the value of field 'amountPaidYTD'.
     */
    public double getAmountPaidYTD()
    {
        return this._amountPaidYTD;
    } //-- double getAmountPaidYTD() 

    /**
     * Method getCityWithholdingYTDReturns the value of field
     * 'cityWithholdingYTD'.
     * 
     * @return the value of field 'cityWithholdingYTD'.
     */
    public double getCityWithholdingYTD()
    {
        return this._cityWithholdingYTD;
    } //-- double getCityWithholdingYTD() 

    /**
     * Method getClientFKReturns the value of field 'clientFK'.
     * 
     * @return the value of field 'clientFK'.
     */
    public long getClientFK()
    {
        return this._clientFK;
    } //-- long getClientFK() 

    /**
     * Method getCountyWithholdingYTDReturns the value of field
     * 'countyWithholdingYTD'.
     * 
     * @return the value of field 'countyWithholdingYTD'.
     */
    public double getCountyWithholdingYTD()
    {
        return this._countyWithholdingYTD;
    } //-- double getCountyWithholdingYTD() 

    /**
     * Method getFederalWithholdingYTDReturns the value of field
     * 'federalWithholdingYTD'.
     * 
     * @return the value of field 'federalWithholdingYTD'.
     */
    public double getFederalWithholdingYTD()
    {
        return this._federalWithholdingYTD;
    } //-- double getFederalWithholdingYTD() 

    /**
     * Method getStateWithholdingYTDReturns the value of field
     * 'stateWithholdingYTD'.
     * 
     * @return the value of field 'stateWithholdingYTD'.
     */
    public double getStateWithholdingYTD()
    {
        return this._stateWithholdingYTD;
    } //-- double getStateWithholdingYTD() 

    /**
     * Method hasAmountPaidToDate
     */
    public boolean hasAmountPaidToDate()
    {
        return this._has_amountPaidToDate;
    } //-- boolean hasAmountPaidToDate() 

    /**
     * Method hasAmountPaidYTD
     */
    public boolean hasAmountPaidYTD()
    {
        return this._has_amountPaidYTD;
    } //-- boolean hasAmountPaidYTD() 

    /**
     * Method hasCityWithholdingYTD
     */
    public boolean hasCityWithholdingYTD()
    {
        return this._has_cityWithholdingYTD;
    } //-- boolean hasCityWithholdingYTD() 

    /**
     * Method hasClientFK
     */
    public boolean hasClientFK()
    {
        return this._has_clientFK;
    } //-- boolean hasClientFK() 

    /**
     * Method hasCountyWithholdingYTD
     */
    public boolean hasCountyWithholdingYTD()
    {
        return this._has_countyWithholdingYTD;
    } //-- boolean hasCountyWithholdingYTD() 

    /**
     * Method hasFederalWithholdingYTD
     */
    public boolean hasFederalWithholdingYTD()
    {
        return this._has_federalWithholdingYTD;
    } //-- boolean hasFederalWithholdingYTD() 

    /**
     * Method hasStateWithholdingYTD
     */
    public boolean hasStateWithholdingYTD()
    {
        return this._has_stateWithholdingYTD;
    } //-- boolean hasStateWithholdingYTD() 

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
     * Method setAmountPaidToDateSets the value of field
     * 'amountPaidToDate'.
     * 
     * @param amountPaidToDate the value of field 'amountPaidToDate'
     */
    public void setAmountPaidToDate(double amountPaidToDate)
    {
        this._amountPaidToDate = amountPaidToDate;
        
        super.setVoChanged(true);
        this._has_amountPaidToDate = true;
    } //-- void setAmountPaidToDate(double) 

    /**
     * Method setAmountPaidYTDSets the value of field
     * 'amountPaidYTD'.
     * 
     * @param amountPaidYTD the value of field 'amountPaidYTD'.
     */
    public void setAmountPaidYTD(double amountPaidYTD)
    {
        this._amountPaidYTD = amountPaidYTD;
        
        super.setVoChanged(true);
        this._has_amountPaidYTD = true;
    } //-- void setAmountPaidYTD(double) 

    /**
     * Method setCityWithholdingYTDSets the value of field
     * 'cityWithholdingYTD'.
     * 
     * @param cityWithholdingYTD the value of field
     * 'cityWithholdingYTD'.
     */
    public void setCityWithholdingYTD(double cityWithholdingYTD)
    {
        this._cityWithholdingYTD = cityWithholdingYTD;
        
        super.setVoChanged(true);
        this._has_cityWithholdingYTD = true;
    } //-- void setCityWithholdingYTD(double) 

    /**
     * Method setClientFKSets the value of field 'clientFK'.
     * 
     * @param clientFK the value of field 'clientFK'.
     */
    public void setClientFK(long clientFK)
    {
        this._clientFK = clientFK;
        
        super.setVoChanged(true);
        this._has_clientFK = true;
    } //-- void setClientFK(long) 

    /**
     * Method setCountyWithholdingYTDSets the value of field
     * 'countyWithholdingYTD'.
     * 
     * @param countyWithholdingYTD the value of field
     * 'countyWithholdingYTD'.
     */
    public void setCountyWithholdingYTD(double countyWithholdingYTD)
    {
        this._countyWithholdingYTD = countyWithholdingYTD;
        
        super.setVoChanged(true);
        this._has_countyWithholdingYTD = true;
    } //-- void setCountyWithholdingYTD(double) 

    /**
     * Method setFederalWithholdingYTDSets the value of field
     * 'federalWithholdingYTD'.
     * 
     * @param federalWithholdingYTD the value of field
     * 'federalWithholdingYTD'.
     */
    public void setFederalWithholdingYTD(double federalWithholdingYTD)
    {
        this._federalWithholdingYTD = federalWithholdingYTD;
        
        super.setVoChanged(true);
        this._has_federalWithholdingYTD = true;
    } //-- void setFederalWithholdingYTD(double) 

    /**
     * Method setStateWithholdingYTDSets the value of field
     * 'stateWithholdingYTD'.
     * 
     * @param stateWithholdingYTD the value of field
     * 'stateWithholdingYTD'.
     */
    public void setStateWithholdingYTD(double stateWithholdingYTD)
    {
        this._stateWithholdingYTD = stateWithholdingYTD;
        
        super.setVoChanged(true);
        this._has_stateWithholdingYTD = true;
    } //-- void setStateWithholdingYTD(double) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.PayeeAccumsVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.PayeeAccumsVO) Unmarshaller.unmarshal(edit.common.vo.PayeeAccumsVO.class, reader);
    } //-- edit.common.vo.PayeeAccumsVO unmarshal(java.io.Reader) 

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
