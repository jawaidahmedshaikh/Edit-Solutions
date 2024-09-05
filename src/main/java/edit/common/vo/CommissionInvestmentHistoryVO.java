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
 * Class CommissionInvestmentHistoryVO.
 * 
 * @version $Revision$ $Date$
 */
public class CommissionInvestmentHistoryVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _commissionInvestmentHistoryPK
     */
    private long _commissionInvestmentHistoryPK;

    /**
     * keeps track of state for field: _commissionInvestmentHistoryP
     */
    private boolean _has_commissionInvestmentHistoryPK;

    /**
     * Field _commissionHistoryFK
     */
    private long _commissionHistoryFK;

    /**
     * keeps track of state for field: _commissionHistoryFK
     */
    private boolean _has_commissionHistoryFK;

    /**
     * Field _investmentFK
     */
    private long _investmentFK;

    /**
     * keeps track of state for field: _investmentFK
     */
    private boolean _has_investmentFK;

    /**
     * Field _commissionAmount
     */
    private java.math.BigDecimal _commissionAmount;


      //----------------/
     //- Constructors -/
    //----------------/

    public CommissionInvestmentHistoryVO() {
        super();
    } //-- edit.common.vo.CommissionInvestmentHistoryVO()


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
        
        if (obj instanceof CommissionInvestmentHistoryVO) {
        
            CommissionInvestmentHistoryVO temp = (CommissionInvestmentHistoryVO)obj;
            if (this._commissionInvestmentHistoryPK != temp._commissionInvestmentHistoryPK)
                return false;
            if (this._has_commissionInvestmentHistoryPK != temp._has_commissionInvestmentHistoryPK)
                return false;
            if (this._commissionHistoryFK != temp._commissionHistoryFK)
                return false;
            if (this._has_commissionHistoryFK != temp._has_commissionHistoryFK)
                return false;
            if (this._investmentFK != temp._investmentFK)
                return false;
            if (this._has_investmentFK != temp._has_investmentFK)
                return false;
            if (this._commissionAmount != null) {
                if (temp._commissionAmount == null) return false;
                else if (!(this._commissionAmount.equals(temp._commissionAmount))) 
                    return false;
            }
            else if (temp._commissionAmount != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCommissionAmountReturns the value of field
     * 'commissionAmount'.
     * 
     * @return the value of field 'commissionAmount'.
     */
    public java.math.BigDecimal getCommissionAmount()
    {
        return this._commissionAmount;
    } //-- java.math.BigDecimal getCommissionAmount() 

    /**
     * Method getCommissionHistoryFKReturns the value of field
     * 'commissionHistoryFK'.
     * 
     * @return the value of field 'commissionHistoryFK'.
     */
    public long getCommissionHistoryFK()
    {
        return this._commissionHistoryFK;
    } //-- long getCommissionHistoryFK() 

    /**
     * Method getCommissionInvestmentHistoryPKReturns the value of
     * field 'commissionInvestmentHistoryPK'.
     * 
     * @return the value of field 'commissionInvestmentHistoryPK'.
     */
    public long getCommissionInvestmentHistoryPK()
    {
        return this._commissionInvestmentHistoryPK;
    } //-- long getCommissionInvestmentHistoryPK() 

    /**
     * Method getInvestmentFKReturns the value of field
     * 'investmentFK'.
     * 
     * @return the value of field 'investmentFK'.
     */
    public long getInvestmentFK()
    {
        return this._investmentFK;
    } //-- long getInvestmentFK() 

    /**
     * Method hasCommissionHistoryFK
     */
    public boolean hasCommissionHistoryFK()
    {
        return this._has_commissionHistoryFK;
    } //-- boolean hasCommissionHistoryFK() 

    /**
     * Method hasCommissionInvestmentHistoryPK
     */
    public boolean hasCommissionInvestmentHistoryPK()
    {
        return this._has_commissionInvestmentHistoryPK;
    } //-- boolean hasCommissionInvestmentHistoryPK() 

    /**
     * Method hasInvestmentFK
     */
    public boolean hasInvestmentFK()
    {
        return this._has_investmentFK;
    } //-- boolean hasInvestmentFK() 

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
     * Method setCommissionAmountSets the value of field
     * 'commissionAmount'.
     * 
     * @param commissionAmount the value of field 'commissionAmount'
     */
    public void setCommissionAmount(java.math.BigDecimal commissionAmount)
    {
        this._commissionAmount = commissionAmount;
        
        super.setVoChanged(true);
    } //-- void setCommissionAmount(java.math.BigDecimal) 

    /**
     * Method setCommissionHistoryFKSets the value of field
     * 'commissionHistoryFK'.
     * 
     * @param commissionHistoryFK the value of field
     * 'commissionHistoryFK'.
     */
    public void setCommissionHistoryFK(long commissionHistoryFK)
    {
        this._commissionHistoryFK = commissionHistoryFK;
        
        super.setVoChanged(true);
        this._has_commissionHistoryFK = true;
    } //-- void setCommissionHistoryFK(long) 

    /**
     * Method setCommissionInvestmentHistoryPKSets the value of
     * field 'commissionInvestmentHistoryPK'.
     * 
     * @param commissionInvestmentHistoryPK the value of field
     * 'commissionInvestmentHistoryPK'.
     */
    public void setCommissionInvestmentHistoryPK(long commissionInvestmentHistoryPK)
    {
        this._commissionInvestmentHistoryPK = commissionInvestmentHistoryPK;
        
        super.setVoChanged(true);
        this._has_commissionInvestmentHistoryPK = true;
    } //-- void setCommissionInvestmentHistoryPK(long) 

    /**
     * Method setInvestmentFKSets the value of field
     * 'investmentFK'.
     * 
     * @param investmentFK the value of field 'investmentFK'.
     */
    public void setInvestmentFK(long investmentFK)
    {
        this._investmentFK = investmentFK;
        
        super.setVoChanged(true);
        this._has_investmentFK = true;
    } //-- void setInvestmentFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CommissionInvestmentHistoryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CommissionInvestmentHistoryVO) Unmarshaller.unmarshal(edit.common.vo.CommissionInvestmentHistoryVO.class, reader);
    } //-- edit.common.vo.CommissionInvestmentHistoryVO unmarshal(java.io.Reader) 

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
