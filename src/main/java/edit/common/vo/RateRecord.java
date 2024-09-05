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
 * Class RateRecord.
 * 
 * @version $Revision$ $Date$
 */
public class RateRecord extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _dividendRate
     */
    private java.math.BigDecimal _dividendRate;

    /**
     * Field _STCapGainRate
     */
    private java.math.BigDecimal _STCapGainRate;

    /**
     * Field _LTCapGainRate
     */
    private java.math.BigDecimal _LTCapGainRate;


      //----------------/
     //- Constructors -/
    //----------------/

    public RateRecord() {
        super();
    } //-- edit.common.vo.RateRecord()


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
        
        if (obj instanceof RateRecord) {
        
            RateRecord temp = (RateRecord)obj;
            if (this._dividendRate != null) {
                if (temp._dividendRate == null) return false;
                else if (!(this._dividendRate.equals(temp._dividendRate))) 
                    return false;
            }
            else if (temp._dividendRate != null)
                return false;
            if (this._STCapGainRate != null) {
                if (temp._STCapGainRate == null) return false;
                else if (!(this._STCapGainRate.equals(temp._STCapGainRate))) 
                    return false;
            }
            else if (temp._STCapGainRate != null)
                return false;
            if (this._LTCapGainRate != null) {
                if (temp._LTCapGainRate == null) return false;
                else if (!(this._LTCapGainRate.equals(temp._LTCapGainRate))) 
                    return false;
            }
            else if (temp._LTCapGainRate != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getDividendRateReturns the value of field
     * 'dividendRate'.
     * 
     * @return the value of field 'dividendRate'.
     */
    public java.math.BigDecimal getDividendRate()
    {
        return this._dividendRate;
    } //-- java.math.BigDecimal getDividendRate() 

    /**
     * Method getLTCapGainRateReturns the value of field
     * 'LTCapGainRate'.
     * 
     * @return the value of field 'LTCapGainRate'.
     */
    public java.math.BigDecimal getLTCapGainRate()
    {
        return this._LTCapGainRate;
    } //-- java.math.BigDecimal getLTCapGainRate() 

    /**
     * Method getSTCapGainRateReturns the value of field
     * 'STCapGainRate'.
     * 
     * @return the value of field 'STCapGainRate'.
     */
    public java.math.BigDecimal getSTCapGainRate()
    {
        return this._STCapGainRate;
    } //-- java.math.BigDecimal getSTCapGainRate() 

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
     * Method setDividendRateSets the value of field
     * 'dividendRate'.
     * 
     * @param dividendRate the value of field 'dividendRate'.
     */
    public void setDividendRate(java.math.BigDecimal dividendRate)
    {
        this._dividendRate = dividendRate;
        
        super.setVoChanged(true);
    } //-- void setDividendRate(java.math.BigDecimal) 

    /**
     * Method setLTCapGainRateSets the value of field
     * 'LTCapGainRate'.
     * 
     * @param LTCapGainRate the value of field 'LTCapGainRate'.
     */
    public void setLTCapGainRate(java.math.BigDecimal LTCapGainRate)
    {
        this._LTCapGainRate = LTCapGainRate;
        
        super.setVoChanged(true);
    } //-- void setLTCapGainRate(java.math.BigDecimal) 

    /**
     * Method setSTCapGainRateSets the value of field
     * 'STCapGainRate'.
     * 
     * @param STCapGainRate the value of field 'STCapGainRate'.
     */
    public void setSTCapGainRate(java.math.BigDecimal STCapGainRate)
    {
        this._STCapGainRate = STCapGainRate;
        
        super.setVoChanged(true);
    } //-- void setSTCapGainRate(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.RateRecord unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.RateRecord) Unmarshaller.unmarshal(edit.common.vo.RateRecord.class, reader);
    } //-- edit.common.vo.RateRecord unmarshal(java.io.Reader) 

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
