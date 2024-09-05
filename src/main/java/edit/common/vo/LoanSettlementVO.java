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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class LoanSettlementVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _loanInterestLiabilityPaid
     */
    private java.math.BigDecimal _loanInterestLiabilityPaid;

    /**
     * Field _loanPrincipalDollars
     */
    private java.math.BigDecimal _loanPrincipalDollars;

    /**
     * Field _loanInterestDollars
     */
    private java.math.BigDecimal _loanInterestDollars;

    /**
     * Field _dollars
     */
    private java.math.BigDecimal _dollars;


      //----------------/
     //- Constructors -/
    //----------------/

    public LoanSettlementVO() {
        super();
    } //-- edit.common.vo.LoanSettlementVO()


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
        
        if (obj instanceof LoanSettlementVO) {
        
            LoanSettlementVO temp = (LoanSettlementVO)obj;
            if (this._loanInterestLiabilityPaid != null) {
                if (temp._loanInterestLiabilityPaid == null) return false;
                else if (!(this._loanInterestLiabilityPaid.equals(temp._loanInterestLiabilityPaid))) 
                    return false;
            }
            else if (temp._loanInterestLiabilityPaid != null)
                return false;
            if (this._loanPrincipalDollars != null) {
                if (temp._loanPrincipalDollars == null) return false;
                else if (!(this._loanPrincipalDollars.equals(temp._loanPrincipalDollars))) 
                    return false;
            }
            else if (temp._loanPrincipalDollars != null)
                return false;
            if (this._loanInterestDollars != null) {
                if (temp._loanInterestDollars == null) return false;
                else if (!(this._loanInterestDollars.equals(temp._loanInterestDollars))) 
                    return false;
            }
            else if (temp._loanInterestDollars != null)
                return false;
            if (this._dollars != null) {
                if (temp._dollars == null) return false;
                else if (!(this._dollars.equals(temp._dollars))) 
                    return false;
            }
            else if (temp._dollars != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getDollarsReturns the value of field 'dollars'.
     * 
     * @return the value of field 'dollars'.
     */
    public java.math.BigDecimal getDollars()
    {
        return this._dollars;
    } //-- java.math.BigDecimal getDollars() 

    /**
     * Method getLoanInterestDollarsReturns the value of field
     * 'loanInterestDollars'.
     * 
     * @return the value of field 'loanInterestDollars'.
     */
    public java.math.BigDecimal getLoanInterestDollars()
    {
        return this._loanInterestDollars;
    } //-- java.math.BigDecimal getLoanInterestDollars() 

    /**
     * Method getLoanInterestLiabilityPaidReturns the value of
     * field 'loanInterestLiabilityPaid'.
     * 
     * @return the value of field 'loanInterestLiabilityPaid'.
     */
    public java.math.BigDecimal getLoanInterestLiabilityPaid()
    {
        return this._loanInterestLiabilityPaid;
    } //-- java.math.BigDecimal getLoanInterestLiabilityPaid() 

    /**
     * Method getLoanPrincipalDollarsReturns the value of field
     * 'loanPrincipalDollars'.
     * 
     * @return the value of field 'loanPrincipalDollars'.
     */
    public java.math.BigDecimal getLoanPrincipalDollars()
    {
        return this._loanPrincipalDollars;
    } //-- java.math.BigDecimal getLoanPrincipalDollars() 

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
     * Method setDollarsSets the value of field 'dollars'.
     * 
     * @param dollars the value of field 'dollars'.
     */
    public void setDollars(java.math.BigDecimal dollars)
    {
        this._dollars = dollars;
        
        super.setVoChanged(true);
    } //-- void setDollars(java.math.BigDecimal) 

    /**
     * Method setLoanInterestDollarsSets the value of field
     * 'loanInterestDollars'.
     * 
     * @param loanInterestDollars the value of field
     * 'loanInterestDollars'.
     */
    public void setLoanInterestDollars(java.math.BigDecimal loanInterestDollars)
    {
        this._loanInterestDollars = loanInterestDollars;
        
        super.setVoChanged(true);
    } //-- void setLoanInterestDollars(java.math.BigDecimal) 

    /**
     * Method setLoanInterestLiabilityPaidSets the value of field
     * 'loanInterestLiabilityPaid'.
     * 
     * @param loanInterestLiabilityPaid the value of field
     * 'loanInterestLiabilityPaid'.
     */
    public void setLoanInterestLiabilityPaid(java.math.BigDecimal loanInterestLiabilityPaid)
    {
        this._loanInterestLiabilityPaid = loanInterestLiabilityPaid;
        
        super.setVoChanged(true);
    } //-- void setLoanInterestLiabilityPaid(java.math.BigDecimal) 

    /**
     * Method setLoanPrincipalDollarsSets the value of field
     * 'loanPrincipalDollars'.
     * 
     * @param loanPrincipalDollars the value of field
     * 'loanPrincipalDollars'.
     */
    public void setLoanPrincipalDollars(java.math.BigDecimal loanPrincipalDollars)
    {
        this._loanPrincipalDollars = loanPrincipalDollars;
        
        super.setVoChanged(true);
    } //-- void setLoanPrincipalDollars(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.LoanSettlementVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.LoanSettlementVO) Unmarshaller.unmarshal(edit.common.vo.LoanSettlementVO.class, reader);
    } //-- edit.common.vo.LoanSettlementVO unmarshal(java.io.Reader) 

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
