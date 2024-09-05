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
 * Schema to be used to pass the overdue charges remaining
 * 
 * @version $Revision$ $Date$
 */
public class OverdueChargeRemainingVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _overdueChargePK
     */
    private long _overdueChargePK;

    /**
     * keeps track of state for field: _overdueChargePK
     */
    private boolean _has_overdueChargePK;

    /**
     * Field _remainingCoi
     */
    private java.math.BigDecimal _remainingCoi;

    /**
     * Field _remainingAdmin
     */
    private java.math.BigDecimal _remainingAdmin;

    /**
     * Field _remainingExpense
     */
    private java.math.BigDecimal _remainingExpense;

    /**
     * Field _overdueCollateralization
     */
    private java.math.BigDecimal _overdueCollateralization;


      //----------------/
     //- Constructors -/
    //----------------/

    public OverdueChargeRemainingVO() {
        super();
    } //-- edit.common.vo.OverdueChargeRemainingVO()


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
        
        if (obj instanceof OverdueChargeRemainingVO) {
        
            OverdueChargeRemainingVO temp = (OverdueChargeRemainingVO)obj;
            if (this._overdueChargePK != temp._overdueChargePK)
                return false;
            if (this._has_overdueChargePK != temp._has_overdueChargePK)
                return false;
            if (this._remainingCoi != null) {
                if (temp._remainingCoi == null) return false;
                else if (!(this._remainingCoi.equals(temp._remainingCoi))) 
                    return false;
            }
            else if (temp._remainingCoi != null)
                return false;
            if (this._remainingAdmin != null) {
                if (temp._remainingAdmin == null) return false;
                else if (!(this._remainingAdmin.equals(temp._remainingAdmin))) 
                    return false;
            }
            else if (temp._remainingAdmin != null)
                return false;
            if (this._remainingExpense != null) {
                if (temp._remainingExpense == null) return false;
                else if (!(this._remainingExpense.equals(temp._remainingExpense))) 
                    return false;
            }
            else if (temp._remainingExpense != null)
                return false;
            if (this._overdueCollateralization != null) {
                if (temp._overdueCollateralization == null) return false;
                else if (!(this._overdueCollateralization.equals(temp._overdueCollateralization))) 
                    return false;
            }
            else if (temp._overdueCollateralization != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getOverdueChargePKReturns the value of field
     * 'overdueChargePK'.
     * 
     * @return the value of field 'overdueChargePK'.
     */
    public long getOverdueChargePK()
    {
        return this._overdueChargePK;
    } //-- long getOverdueChargePK() 

    /**
     * Method getOverdueCollateralizationReturns the value of field
     * 'overdueCollateralization'.
     * 
     * @return the value of field 'overdueCollateralization'.
     */
    public java.math.BigDecimal getOverdueCollateralization()
    {
        return this._overdueCollateralization;
    } //-- java.math.BigDecimal getOverdueCollateralization() 

    /**
     * Method getRemainingAdminReturns the value of field
     * 'remainingAdmin'.
     * 
     * @return the value of field 'remainingAdmin'.
     */
    public java.math.BigDecimal getRemainingAdmin()
    {
        return this._remainingAdmin;
    } //-- java.math.BigDecimal getRemainingAdmin() 

    /**
     * Method getRemainingCoiReturns the value of field
     * 'remainingCoi'.
     * 
     * @return the value of field 'remainingCoi'.
     */
    public java.math.BigDecimal getRemainingCoi()
    {
        return this._remainingCoi;
    } //-- java.math.BigDecimal getRemainingCoi() 

    /**
     * Method getRemainingExpenseReturns the value of field
     * 'remainingExpense'.
     * 
     * @return the value of field 'remainingExpense'.
     */
    public java.math.BigDecimal getRemainingExpense()
    {
        return this._remainingExpense;
    } //-- java.math.BigDecimal getRemainingExpense() 

    /**
     * Method hasOverdueChargePK
     */
    public boolean hasOverdueChargePK()
    {
        return this._has_overdueChargePK;
    } //-- boolean hasOverdueChargePK() 

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
     * Method setOverdueChargePKSets the value of field
     * 'overdueChargePK'.
     * 
     * @param overdueChargePK the value of field 'overdueChargePK'.
     */
    public void setOverdueChargePK(long overdueChargePK)
    {
        this._overdueChargePK = overdueChargePK;
        
        super.setVoChanged(true);
        this._has_overdueChargePK = true;
    } //-- void setOverdueChargePK(long) 

    /**
     * Method setOverdueCollateralizationSets the value of field
     * 'overdueCollateralization'.
     * 
     * @param overdueCollateralization the value of field
     * 'overdueCollateralization'.
     */
    public void setOverdueCollateralization(java.math.BigDecimal overdueCollateralization)
    {
        this._overdueCollateralization = overdueCollateralization;
        
        super.setVoChanged(true);
    } //-- void setOverdueCollateralization(java.math.BigDecimal) 

    /**
     * Method setRemainingAdminSets the value of field
     * 'remainingAdmin'.
     * 
     * @param remainingAdmin the value of field 'remainingAdmin'.
     */
    public void setRemainingAdmin(java.math.BigDecimal remainingAdmin)
    {
        this._remainingAdmin = remainingAdmin;
        
        super.setVoChanged(true);
    } //-- void setRemainingAdmin(java.math.BigDecimal) 

    /**
     * Method setRemainingCoiSets the value of field
     * 'remainingCoi'.
     * 
     * @param remainingCoi the value of field 'remainingCoi'.
     */
    public void setRemainingCoi(java.math.BigDecimal remainingCoi)
    {
        this._remainingCoi = remainingCoi;
        
        super.setVoChanged(true);
    } //-- void setRemainingCoi(java.math.BigDecimal) 

    /**
     * Method setRemainingExpenseSets the value of field
     * 'remainingExpense'.
     * 
     * @param remainingExpense the value of field 'remainingExpense'
     */
    public void setRemainingExpense(java.math.BigDecimal remainingExpense)
    {
        this._remainingExpense = remainingExpense;
        
        super.setVoChanged(true);
    } //-- void setRemainingExpense(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.OverdueChargeRemainingVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.OverdueChargeRemainingVO) Unmarshaller.unmarshal(edit.common.vo.OverdueChargeRemainingVO.class, reader);
    } //-- edit.common.vo.OverdueChargeRemainingVO unmarshal(java.io.Reader) 

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
