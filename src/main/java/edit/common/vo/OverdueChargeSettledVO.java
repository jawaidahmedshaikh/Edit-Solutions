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
 * Class OverdueChargeSettledVO.
 * 
 * @version $Revision$ $Date$
 */
public class OverdueChargeSettledVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _overdueChargeSettledPK
     */
    private long _overdueChargeSettledPK;

    /**
     * keeps track of state for field: _overdueChargeSettledPK
     */
    private boolean _has_overdueChargeSettledPK;

    /**
     * Field _EDITTrxFK
     */
    private long _EDITTrxFK;

    /**
     * keeps track of state for field: _EDITTrxFK
     */
    private boolean _has_EDITTrxFK;

    /**
     * Field _overdueChargeFK
     */
    private long _overdueChargeFK;

    /**
     * keeps track of state for field: _overdueChargeFK
     */
    private boolean _has_overdueChargeFK;

    /**
     * Field _settledCoi
     */
    private java.math.BigDecimal _settledCoi;

    /**
     * Field _settledAdmin
     */
    private java.math.BigDecimal _settledAdmin;

    /**
     * Field _settledExpense
     */
    private java.math.BigDecimal _settledExpense;

    /**
     * Field _settledCollateralization
     */
    private java.math.BigDecimal _settledCollateralization;


      //----------------/
     //- Constructors -/
    //----------------/

    public OverdueChargeSettledVO() {
        super();
    } //-- edit.common.vo.OverdueChargeSettledVO()


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
        
        if (obj instanceof OverdueChargeSettledVO) {
        
            OverdueChargeSettledVO temp = (OverdueChargeSettledVO)obj;
            if (this._overdueChargeSettledPK != temp._overdueChargeSettledPK)
                return false;
            if (this._has_overdueChargeSettledPK != temp._has_overdueChargeSettledPK)
                return false;
            if (this._EDITTrxFK != temp._EDITTrxFK)
                return false;
            if (this._has_EDITTrxFK != temp._has_EDITTrxFK)
                return false;
            if (this._overdueChargeFK != temp._overdueChargeFK)
                return false;
            if (this._has_overdueChargeFK != temp._has_overdueChargeFK)
                return false;
            if (this._settledCoi != null) {
                if (temp._settledCoi == null) return false;
                else if (!(this._settledCoi.equals(temp._settledCoi))) 
                    return false;
            }
            else if (temp._settledCoi != null)
                return false;
            if (this._settledAdmin != null) {
                if (temp._settledAdmin == null) return false;
                else if (!(this._settledAdmin.equals(temp._settledAdmin))) 
                    return false;
            }
            else if (temp._settledAdmin != null)
                return false;
            if (this._settledExpense != null) {
                if (temp._settledExpense == null) return false;
                else if (!(this._settledExpense.equals(temp._settledExpense))) 
                    return false;
            }
            else if (temp._settledExpense != null)
                return false;
            if (this._settledCollateralization != null) {
                if (temp._settledCollateralization == null) return false;
                else if (!(this._settledCollateralization.equals(temp._settledCollateralization))) 
                    return false;
            }
            else if (temp._settledCollateralization != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getEDITTrxFKReturns the value of field 'EDITTrxFK'.
     * 
     * @return the value of field 'EDITTrxFK'.
     */
    public long getEDITTrxFK()
    {
        return this._EDITTrxFK;
    } //-- long getEDITTrxFK() 

    /**
     * Method getOverdueChargeFKReturns the value of field
     * 'overdueChargeFK'.
     * 
     * @return the value of field 'overdueChargeFK'.
     */
    public long getOverdueChargeFK()
    {
        return this._overdueChargeFK;
    } //-- long getOverdueChargeFK() 

    /**
     * Method getOverdueChargeSettledPKReturns the value of field
     * 'overdueChargeSettledPK'.
     * 
     * @return the value of field 'overdueChargeSettledPK'.
     */
    public long getOverdueChargeSettledPK()
    {
        return this._overdueChargeSettledPK;
    } //-- long getOverdueChargeSettledPK() 

    /**
     * Method getSettledAdminReturns the value of field
     * 'settledAdmin'.
     * 
     * @return the value of field 'settledAdmin'.
     */
    public java.math.BigDecimal getSettledAdmin()
    {
        return this._settledAdmin;
    } //-- java.math.BigDecimal getSettledAdmin() 

    /**
     * Method getSettledCoiReturns the value of field 'settledCoi'.
     * 
     * @return the value of field 'settledCoi'.
     */
    public java.math.BigDecimal getSettledCoi()
    {
        return this._settledCoi;
    } //-- java.math.BigDecimal getSettledCoi() 

    /**
     * Method getSettledCollateralizationReturns the value of field
     * 'settledCollateralization'.
     * 
     * @return the value of field 'settledCollateralization'.
     */
    public java.math.BigDecimal getSettledCollateralization()
    {
        return this._settledCollateralization;
    } //-- java.math.BigDecimal getSettledCollateralization() 

    /**
     * Method getSettledExpenseReturns the value of field
     * 'settledExpense'.
     * 
     * @return the value of field 'settledExpense'.
     */
    public java.math.BigDecimal getSettledExpense()
    {
        return this._settledExpense;
    } //-- java.math.BigDecimal getSettledExpense() 

    /**
     * Method hasEDITTrxFK
     */
    public boolean hasEDITTrxFK()
    {
        return this._has_EDITTrxFK;
    } //-- boolean hasEDITTrxFK() 

    /**
     * Method hasOverdueChargeFK
     */
    public boolean hasOverdueChargeFK()
    {
        return this._has_overdueChargeFK;
    } //-- boolean hasOverdueChargeFK() 

    /**
     * Method hasOverdueChargeSettledPK
     */
    public boolean hasOverdueChargeSettledPK()
    {
        return this._has_overdueChargeSettledPK;
    } //-- boolean hasOverdueChargeSettledPK() 

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
     * Method setEDITTrxFKSets the value of field 'EDITTrxFK'.
     * 
     * @param EDITTrxFK the value of field 'EDITTrxFK'.
     */
    public void setEDITTrxFK(long EDITTrxFK)
    {
        this._EDITTrxFK = EDITTrxFK;
        
        super.setVoChanged(true);
        this._has_EDITTrxFK = true;
    } //-- void setEDITTrxFK(long) 

    /**
     * Method setOverdueChargeFKSets the value of field
     * 'overdueChargeFK'.
     * 
     * @param overdueChargeFK the value of field 'overdueChargeFK'.
     */
    public void setOverdueChargeFK(long overdueChargeFK)
    {
        this._overdueChargeFK = overdueChargeFK;
        
        super.setVoChanged(true);
        this._has_overdueChargeFK = true;
    } //-- void setOverdueChargeFK(long) 

    /**
     * Method setOverdueChargeSettledPKSets the value of field
     * 'overdueChargeSettledPK'.
     * 
     * @param overdueChargeSettledPK the value of field
     * 'overdueChargeSettledPK'.
     */
    public void setOverdueChargeSettledPK(long overdueChargeSettledPK)
    {
        this._overdueChargeSettledPK = overdueChargeSettledPK;
        
        super.setVoChanged(true);
        this._has_overdueChargeSettledPK = true;
    } //-- void setOverdueChargeSettledPK(long) 

    /**
     * Method setSettledAdminSets the value of field
     * 'settledAdmin'.
     * 
     * @param settledAdmin the value of field 'settledAdmin'.
     */
    public void setSettledAdmin(java.math.BigDecimal settledAdmin)
    {
        this._settledAdmin = settledAdmin;
        
        super.setVoChanged(true);
    } //-- void setSettledAdmin(java.math.BigDecimal) 

    /**
     * Method setSettledCoiSets the value of field 'settledCoi'.
     * 
     * @param settledCoi the value of field 'settledCoi'.
     */
    public void setSettledCoi(java.math.BigDecimal settledCoi)
    {
        this._settledCoi = settledCoi;
        
        super.setVoChanged(true);
    } //-- void setSettledCoi(java.math.BigDecimal) 

    /**
     * Method setSettledCollateralizationSets the value of field
     * 'settledCollateralization'.
     * 
     * @param settledCollateralization the value of field
     * 'settledCollateralization'.
     */
    public void setSettledCollateralization(java.math.BigDecimal settledCollateralization)
    {
        this._settledCollateralization = settledCollateralization;
        
        super.setVoChanged(true);
    } //-- void setSettledCollateralization(java.math.BigDecimal) 

    /**
     * Method setSettledExpenseSets the value of field
     * 'settledExpense'.
     * 
     * @param settledExpense the value of field 'settledExpense'.
     */
    public void setSettledExpense(java.math.BigDecimal settledExpense)
    {
        this._settledExpense = settledExpense;
        
        super.setVoChanged(true);
    } //-- void setSettledExpense(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.OverdueChargeSettledVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.OverdueChargeSettledVO) Unmarshaller.unmarshal(edit.common.vo.OverdueChargeSettledVO.class, reader);
    } //-- edit.common.vo.OverdueChargeSettledVO unmarshal(java.io.Reader) 

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
