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
 * Class AgentEarningsVO.
 * 
 * @version $Revision$ $Date$
 */
public class AgentEarningsVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _taxableIncome
     */
    private java.math.BigDecimal _taxableIncome;

    /**
     * Field _taxableIncomeYTD
     */
    private java.math.BigDecimal _taxableIncomeYTD;

    /**
     * Field _positivePolicyEarnings
     */
    private java.math.BigDecimal _positivePolicyEarnings;

    /**
     * Field _negativePolicyEarnings
     */
    private java.math.BigDecimal _negativePolicyEarnings;

    /**
     * Field _netPolicyEarnings
     */
    private java.math.BigDecimal _netPolicyEarnings;


      //----------------/
     //- Constructors -/
    //----------------/

    public AgentEarningsVO() {
        super();
    } //-- edit.common.vo.AgentEarningsVO()


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
        
        if (obj instanceof AgentEarningsVO) {
        
            AgentEarningsVO temp = (AgentEarningsVO)obj;
            if (this._taxableIncome != null) {
                if (temp._taxableIncome == null) return false;
                else if (!(this._taxableIncome.equals(temp._taxableIncome))) 
                    return false;
            }
            else if (temp._taxableIncome != null)
                return false;
            if (this._taxableIncomeYTD != null) {
                if (temp._taxableIncomeYTD == null) return false;
                else if (!(this._taxableIncomeYTD.equals(temp._taxableIncomeYTD))) 
                    return false;
            }
            else if (temp._taxableIncomeYTD != null)
                return false;
            if (this._positivePolicyEarnings != null) {
                if (temp._positivePolicyEarnings == null) return false;
                else if (!(this._positivePolicyEarnings.equals(temp._positivePolicyEarnings))) 
                    return false;
            }
            else if (temp._positivePolicyEarnings != null)
                return false;
            if (this._negativePolicyEarnings != null) {
                if (temp._negativePolicyEarnings == null) return false;
                else if (!(this._negativePolicyEarnings.equals(temp._negativePolicyEarnings))) 
                    return false;
            }
            else if (temp._negativePolicyEarnings != null)
                return false;
            if (this._netPolicyEarnings != null) {
                if (temp._netPolicyEarnings == null) return false;
                else if (!(this._netPolicyEarnings.equals(temp._netPolicyEarnings))) 
                    return false;
            }
            else if (temp._netPolicyEarnings != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getNegativePolicyEarningsReturns the value of field
     * 'negativePolicyEarnings'.
     * 
     * @return the value of field 'negativePolicyEarnings'.
     */
    public java.math.BigDecimal getNegativePolicyEarnings()
    {
        return this._negativePolicyEarnings;
    } //-- java.math.BigDecimal getNegativePolicyEarnings() 

    /**
     * Method getNetPolicyEarningsReturns the value of field
     * 'netPolicyEarnings'.
     * 
     * @return the value of field 'netPolicyEarnings'.
     */
    public java.math.BigDecimal getNetPolicyEarnings()
    {
        return this._netPolicyEarnings;
    } //-- java.math.BigDecimal getNetPolicyEarnings() 

    /**
     * Method getPositivePolicyEarningsReturns the value of field
     * 'positivePolicyEarnings'.
     * 
     * @return the value of field 'positivePolicyEarnings'.
     */
    public java.math.BigDecimal getPositivePolicyEarnings()
    {
        return this._positivePolicyEarnings;
    } //-- java.math.BigDecimal getPositivePolicyEarnings() 

    /**
     * Method getTaxableIncomeReturns the value of field
     * 'taxableIncome'.
     * 
     * @return the value of field 'taxableIncome'.
     */
    public java.math.BigDecimal getTaxableIncome()
    {
        return this._taxableIncome;
    } //-- java.math.BigDecimal getTaxableIncome() 

    /**
     * Method getTaxableIncomeYTDReturns the value of field
     * 'taxableIncomeYTD'.
     * 
     * @return the value of field 'taxableIncomeYTD'.
     */
    public java.math.BigDecimal getTaxableIncomeYTD()
    {
        return this._taxableIncomeYTD;
    } //-- java.math.BigDecimal getTaxableIncomeYTD() 

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
     * Method setNegativePolicyEarningsSets the value of field
     * 'negativePolicyEarnings'.
     * 
     * @param negativePolicyEarnings the value of field
     * 'negativePolicyEarnings'.
     */
    public void setNegativePolicyEarnings(java.math.BigDecimal negativePolicyEarnings)
    {
        this._negativePolicyEarnings = negativePolicyEarnings;
        
        super.setVoChanged(true);
    } //-- void setNegativePolicyEarnings(java.math.BigDecimal) 

    /**
     * Method setNetPolicyEarningsSets the value of field
     * 'netPolicyEarnings'.
     * 
     * @param netPolicyEarnings the value of field
     * 'netPolicyEarnings'.
     */
    public void setNetPolicyEarnings(java.math.BigDecimal netPolicyEarnings)
    {
        this._netPolicyEarnings = netPolicyEarnings;
        
        super.setVoChanged(true);
    } //-- void setNetPolicyEarnings(java.math.BigDecimal) 

    /**
     * Method setPositivePolicyEarningsSets the value of field
     * 'positivePolicyEarnings'.
     * 
     * @param positivePolicyEarnings the value of field
     * 'positivePolicyEarnings'.
     */
    public void setPositivePolicyEarnings(java.math.BigDecimal positivePolicyEarnings)
    {
        this._positivePolicyEarnings = positivePolicyEarnings;
        
        super.setVoChanged(true);
    } //-- void setPositivePolicyEarnings(java.math.BigDecimal) 

    /**
     * Method setTaxableIncomeSets the value of field
     * 'taxableIncome'.
     * 
     * @param taxableIncome the value of field 'taxableIncome'.
     */
    public void setTaxableIncome(java.math.BigDecimal taxableIncome)
    {
        this._taxableIncome = taxableIncome;
        
        super.setVoChanged(true);
    } //-- void setTaxableIncome(java.math.BigDecimal) 

    /**
     * Method setTaxableIncomeYTDSets the value of field
     * 'taxableIncomeYTD'.
     * 
     * @param taxableIncomeYTD the value of field 'taxableIncomeYTD'
     */
    public void setTaxableIncomeYTD(java.math.BigDecimal taxableIncomeYTD)
    {
        this._taxableIncomeYTD = taxableIncomeYTD;
        
        super.setVoChanged(true);
    } //-- void setTaxableIncomeYTD(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AgentEarningsVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AgentEarningsVO) Unmarshaller.unmarshal(edit.common.vo.AgentEarningsVO.class, reader);
    } //-- edit.common.vo.AgentEarningsVO unmarshal(java.io.Reader) 

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
