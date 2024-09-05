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
 * Contains the information needed to create the Separate Account
 * Investment Values By Division Report
 * 
 * @version $Revision$ $Date$
 */
public class SeparateAccountValueDetailsVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _fundNumber
     */
    private java.lang.String _fundNumber;

    /**
     * Field _fundName
     */
    private java.lang.String _fundName;

    /**
     * Field _marketingPackageName
     */
    private java.lang.String _marketingPackageName;

    /**
     * Field _valuationDate
     */
    private java.lang.String _valuationDate;

    /**
     * Field _unitValue
     */
    private java.math.BigDecimal _unitValue;

    /**
     * Field _participantUnits
     */
    private java.math.BigDecimal _participantUnits;

    /**
     * Field _investmentValue
     */
    private java.math.BigDecimal _investmentValue;


      //----------------/
     //- Constructors -/
    //----------------/

    public SeparateAccountValueDetailsVO() {
        super();
    } //-- edit.common.vo.SeparateAccountValueDetailsVO()


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
        
        if (obj instanceof SeparateAccountValueDetailsVO) {
        
            SeparateAccountValueDetailsVO temp = (SeparateAccountValueDetailsVO)obj;
            if (this._fundNumber != null) {
                if (temp._fundNumber == null) return false;
                else if (!(this._fundNumber.equals(temp._fundNumber))) 
                    return false;
            }
            else if (temp._fundNumber != null)
                return false;
            if (this._fundName != null) {
                if (temp._fundName == null) return false;
                else if (!(this._fundName.equals(temp._fundName))) 
                    return false;
            }
            else if (temp._fundName != null)
                return false;
            if (this._marketingPackageName != null) {
                if (temp._marketingPackageName == null) return false;
                else if (!(this._marketingPackageName.equals(temp._marketingPackageName))) 
                    return false;
            }
            else if (temp._marketingPackageName != null)
                return false;
            if (this._valuationDate != null) {
                if (temp._valuationDate == null) return false;
                else if (!(this._valuationDate.equals(temp._valuationDate))) 
                    return false;
            }
            else if (temp._valuationDate != null)
                return false;
            if (this._unitValue != null) {
                if (temp._unitValue == null) return false;
                else if (!(this._unitValue.equals(temp._unitValue))) 
                    return false;
            }
            else if (temp._unitValue != null)
                return false;
            if (this._participantUnits != null) {
                if (temp._participantUnits == null) return false;
                else if (!(this._participantUnits.equals(temp._participantUnits))) 
                    return false;
            }
            else if (temp._participantUnits != null)
                return false;
            if (this._investmentValue != null) {
                if (temp._investmentValue == null) return false;
                else if (!(this._investmentValue.equals(temp._investmentValue))) 
                    return false;
            }
            else if (temp._investmentValue != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getFundNameReturns the value of field 'fundName'.
     * 
     * @return the value of field 'fundName'.
     */
    public java.lang.String getFundName()
    {
        return this._fundName;
    } //-- java.lang.String getFundName() 

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
     * Method getInvestmentValueReturns the value of field
     * 'investmentValue'.
     * 
     * @return the value of field 'investmentValue'.
     */
    public java.math.BigDecimal getInvestmentValue()
    {
        return this._investmentValue;
    } //-- java.math.BigDecimal getInvestmentValue() 

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
     * Method getParticipantUnitsReturns the value of field
     * 'participantUnits'.
     * 
     * @return the value of field 'participantUnits'.
     */
    public java.math.BigDecimal getParticipantUnits()
    {
        return this._participantUnits;
    } //-- java.math.BigDecimal getParticipantUnits() 

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
     * Method setFundNameSets the value of field 'fundName'.
     * 
     * @param fundName the value of field 'fundName'.
     */
    public void setFundName(java.lang.String fundName)
    {
        this._fundName = fundName;
        
        super.setVoChanged(true);
    } //-- void setFundName(java.lang.String) 

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
     * Method setInvestmentValueSets the value of field
     * 'investmentValue'.
     * 
     * @param investmentValue the value of field 'investmentValue'.
     */
    public void setInvestmentValue(java.math.BigDecimal investmentValue)
    {
        this._investmentValue = investmentValue;
        
        super.setVoChanged(true);
    } //-- void setInvestmentValue(java.math.BigDecimal) 

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
     * Method setParticipantUnitsSets the value of field
     * 'participantUnits'.
     * 
     * @param participantUnits the value of field 'participantUnits'
     */
    public void setParticipantUnits(java.math.BigDecimal participantUnits)
    {
        this._participantUnits = participantUnits;
        
        super.setVoChanged(true);
    } //-- void setParticipantUnits(java.math.BigDecimal) 

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
    public static edit.common.vo.SeparateAccountValueDetailsVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SeparateAccountValueDetailsVO) Unmarshaller.unmarshal(edit.common.vo.SeparateAccountValueDetailsVO.class, reader);
    } //-- edit.common.vo.SeparateAccountValueDetailsVO unmarshal(java.io.Reader) 

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
