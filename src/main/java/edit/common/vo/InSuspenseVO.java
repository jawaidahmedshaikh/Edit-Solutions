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
 * Class InSuspenseVO.
 * 
 * @version $Revision$ $Date$
 */
public class InSuspenseVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _inSuspensePK
     */
    private long _inSuspensePK;

    /**
     * keeps track of state for field: _inSuspensePK
     */
    private boolean _has_inSuspensePK;

    /**
     * Field _EDITTrxHistoryFK
     */
    private long _EDITTrxHistoryFK;

    /**
     * keeps track of state for field: _EDITTrxHistoryFK
     */
    private boolean _has_EDITTrxHistoryFK;

    /**
     * Field _suspenseFK
     */
    private long _suspenseFK;

    /**
     * keeps track of state for field: _suspenseFK
     */
    private boolean _has_suspenseFK;

    /**
     * Field _amount
     */
    private java.math.BigDecimal _amount;


      //----------------/
     //- Constructors -/
    //----------------/

    public InSuspenseVO() {
        super();
    } //-- edit.common.vo.InSuspenseVO()


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
        
        if (obj instanceof InSuspenseVO) {
        
            InSuspenseVO temp = (InSuspenseVO)obj;
            if (this._inSuspensePK != temp._inSuspensePK)
                return false;
            if (this._has_inSuspensePK != temp._has_inSuspensePK)
                return false;
            if (this._EDITTrxHistoryFK != temp._EDITTrxHistoryFK)
                return false;
            if (this._has_EDITTrxHistoryFK != temp._has_EDITTrxHistoryFK)
                return false;
            if (this._suspenseFK != temp._suspenseFK)
                return false;
            if (this._has_suspenseFK != temp._has_suspenseFK)
                return false;
            if (this._amount != null) {
                if (temp._amount == null) return false;
                else if (!(this._amount.equals(temp._amount))) 
                    return false;
            }
            else if (temp._amount != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAmountReturns the value of field 'amount'.
     * 
     * @return the value of field 'amount'.
     */
    public java.math.BigDecimal getAmount()
    {
        return this._amount;
    } //-- java.math.BigDecimal getAmount() 

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
     * Method getInSuspensePKReturns the value of field
     * 'inSuspensePK'.
     * 
     * @return the value of field 'inSuspensePK'.
     */
    public long getInSuspensePK()
    {
        return this._inSuspensePK;
    } //-- long getInSuspensePK() 

    /**
     * Method getSuspenseFKReturns the value of field 'suspenseFK'.
     * 
     * @return the value of field 'suspenseFK'.
     */
    public long getSuspenseFK()
    {
        return this._suspenseFK;
    } //-- long getSuspenseFK() 

    /**
     * Method hasEDITTrxHistoryFK
     */
    public boolean hasEDITTrxHistoryFK()
    {
        return this._has_EDITTrxHistoryFK;
    } //-- boolean hasEDITTrxHistoryFK() 

    /**
     * Method hasInSuspensePK
     */
    public boolean hasInSuspensePK()
    {
        return this._has_inSuspensePK;
    } //-- boolean hasInSuspensePK() 

    /**
     * Method hasSuspenseFK
     */
    public boolean hasSuspenseFK()
    {
        return this._has_suspenseFK;
    } //-- boolean hasSuspenseFK() 

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
     * Method setAmountSets the value of field 'amount'.
     * 
     * @param amount the value of field 'amount'.
     */
    public void setAmount(java.math.BigDecimal amount)
    {
        this._amount = amount;
        
        super.setVoChanged(true);
    } //-- void setAmount(java.math.BigDecimal) 

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
     * Method setInSuspensePKSets the value of field
     * 'inSuspensePK'.
     * 
     * @param inSuspensePK the value of field 'inSuspensePK'.
     */
    public void setInSuspensePK(long inSuspensePK)
    {
        this._inSuspensePK = inSuspensePK;
        
        super.setVoChanged(true);
        this._has_inSuspensePK = true;
    } //-- void setInSuspensePK(long) 

    /**
     * Method setSuspenseFKSets the value of field 'suspenseFK'.
     * 
     * @param suspenseFK the value of field 'suspenseFK'.
     */
    public void setSuspenseFK(long suspenseFK)
    {
        this._suspenseFK = suspenseFK;
        
        super.setVoChanged(true);
        this._has_suspenseFK = true;
    } //-- void setSuspenseFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.InSuspenseVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.InSuspenseVO) Unmarshaller.unmarshal(edit.common.vo.InSuspenseVO.class, reader);
    } //-- edit.common.vo.InSuspenseVO unmarshal(java.io.Reader) 

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
