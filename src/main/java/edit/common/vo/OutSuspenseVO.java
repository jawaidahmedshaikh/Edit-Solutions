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
 * Class OutSuspenseVO.
 * 
 * @version $Revision$ $Date$
 */
public class OutSuspenseVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _outSuspensePK
     */
    private long _outSuspensePK;

    /**
     * keeps track of state for field: _outSuspensePK
     */
    private boolean _has_outSuspensePK;

    /**
     * Field _contractSetupFK
     */
    private long _contractSetupFK;

    /**
     * keeps track of state for field: _contractSetupFK
     */
    private boolean _has_contractSetupFK;

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

    public OutSuspenseVO() {
        super();
    } //-- edit.common.vo.OutSuspenseVO()


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
        
        if (obj instanceof OutSuspenseVO) {
        
            OutSuspenseVO temp = (OutSuspenseVO)obj;
            if (this._outSuspensePK != temp._outSuspensePK)
                return false;
            if (this._has_outSuspensePK != temp._has_outSuspensePK)
                return false;
            if (this._contractSetupFK != temp._contractSetupFK)
                return false;
            if (this._has_contractSetupFK != temp._has_contractSetupFK)
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
     * Method getContractSetupFKReturns the value of field
     * 'contractSetupFK'.
     * 
     * @return the value of field 'contractSetupFK'.
     */
    public long getContractSetupFK()
    {
        return this._contractSetupFK;
    } //-- long getContractSetupFK() 

    /**
     * Method getOutSuspensePKReturns the value of field
     * 'outSuspensePK'.
     * 
     * @return the value of field 'outSuspensePK'.
     */
    public long getOutSuspensePK()
    {
        return this._outSuspensePK;
    } //-- long getOutSuspensePK() 

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
     * Method hasContractSetupFK
     */
    public boolean hasContractSetupFK()
    {
        return this._has_contractSetupFK;
    } //-- boolean hasContractSetupFK() 

    /**
     * Method hasOutSuspensePK
     */
    public boolean hasOutSuspensePK()
    {
        return this._has_outSuspensePK;
    } //-- boolean hasOutSuspensePK() 

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
     * Method setContractSetupFKSets the value of field
     * 'contractSetupFK'.
     * 
     * @param contractSetupFK the value of field 'contractSetupFK'.
     */
    public void setContractSetupFK(long contractSetupFK)
    {
        this._contractSetupFK = contractSetupFK;
        
        super.setVoChanged(true);
        this._has_contractSetupFK = true;
    } //-- void setContractSetupFK(long) 

    /**
     * Method setOutSuspensePKSets the value of field
     * 'outSuspensePK'.
     * 
     * @param outSuspensePK the value of field 'outSuspensePK'.
     */
    public void setOutSuspensePK(long outSuspensePK)
    {
        this._outSuspensePK = outSuspensePK;
        
        super.setVoChanged(true);
        this._has_outSuspensePK = true;
    } //-- void setOutSuspensePK(long) 

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
    public static edit.common.vo.OutSuspenseVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.OutSuspenseVO) Unmarshaller.unmarshal(edit.common.vo.OutSuspenseVO.class, reader);
    } //-- edit.common.vo.OutSuspenseVO unmarshal(java.io.Reader) 

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
