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
 * Class BonusCommissionHistoryVO.
 * 
 * @version $Revision$ $Date$
 */
public class BonusCommissionHistoryVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _bonusCommissionHistoryPK
     */
    private long _bonusCommissionHistoryPK;

    /**
     * keeps track of state for field: _bonusCommissionHistoryPK
     */
    private boolean _has_bonusCommissionHistoryPK;

    /**
     * Field _commissionHistoryFK
     */
    private long _commissionHistoryFK;

    /**
     * keeps track of state for field: _commissionHistoryFK
     */
    private boolean _has_commissionHistoryFK;

    /**
     * Field _participatingAgentFK
     */
    private long _participatingAgentFK;

    /**
     * keeps track of state for field: _participatingAgentFK
     */
    private boolean _has_participatingAgentFK;

    /**
     * Field _bonusUpdateStatus
     */
    private java.lang.String _bonusUpdateStatus;

    /**
     * Field _bonusUpdateDateTime
     */
    private java.lang.String _bonusUpdateDateTime;

    /**
     * Field _accountingPendingStatus
     */
    private java.lang.String _accountingPendingStatus;

    /**
     * Field _amount
     */
    private java.math.BigDecimal _amount;

    /**
     * Field _transactionTypeCT
     */
    private java.lang.String _transactionTypeCT;

    /**
     * Field _updateReason
     */
    private java.lang.String _updateReason;


      //----------------/
     //- Constructors -/
    //----------------/

    public BonusCommissionHistoryVO() {
        super();
    } //-- edit.common.vo.BonusCommissionHistoryVO()


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
        
        if (obj instanceof BonusCommissionHistoryVO) {
        
            BonusCommissionHistoryVO temp = (BonusCommissionHistoryVO)obj;
            if (this._bonusCommissionHistoryPK != temp._bonusCommissionHistoryPK)
                return false;
            if (this._has_bonusCommissionHistoryPK != temp._has_bonusCommissionHistoryPK)
                return false;
            if (this._commissionHistoryFK != temp._commissionHistoryFK)
                return false;
            if (this._has_commissionHistoryFK != temp._has_commissionHistoryFK)
                return false;
            if (this._participatingAgentFK != temp._participatingAgentFK)
                return false;
            if (this._has_participatingAgentFK != temp._has_participatingAgentFK)
                return false;
            if (this._bonusUpdateStatus != null) {
                if (temp._bonusUpdateStatus == null) return false;
                else if (!(this._bonusUpdateStatus.equals(temp._bonusUpdateStatus))) 
                    return false;
            }
            else if (temp._bonusUpdateStatus != null)
                return false;
            if (this._bonusUpdateDateTime != null) {
                if (temp._bonusUpdateDateTime == null) return false;
                else if (!(this._bonusUpdateDateTime.equals(temp._bonusUpdateDateTime))) 
                    return false;
            }
            else if (temp._bonusUpdateDateTime != null)
                return false;
            if (this._accountingPendingStatus != null) {
                if (temp._accountingPendingStatus == null) return false;
                else if (!(this._accountingPendingStatus.equals(temp._accountingPendingStatus))) 
                    return false;
            }
            else if (temp._accountingPendingStatus != null)
                return false;
            if (this._amount != null) {
                if (temp._amount == null) return false;
                else if (!(this._amount.equals(temp._amount))) 
                    return false;
            }
            else if (temp._amount != null)
                return false;
            if (this._transactionTypeCT != null) {
                if (temp._transactionTypeCT == null) return false;
                else if (!(this._transactionTypeCT.equals(temp._transactionTypeCT))) 
                    return false;
            }
            else if (temp._transactionTypeCT != null)
                return false;
            if (this._updateReason != null) {
                if (temp._updateReason == null) return false;
                else if (!(this._updateReason.equals(temp._updateReason))) 
                    return false;
            }
            else if (temp._updateReason != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccountingPendingStatusReturns the value of field
     * 'accountingPendingStatus'.
     * 
     * @return the value of field 'accountingPendingStatus'.
     */
    public java.lang.String getAccountingPendingStatus()
    {
        return this._accountingPendingStatus;
    } //-- java.lang.String getAccountingPendingStatus() 

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
     * Method getBonusCommissionHistoryPKReturns the value of field
     * 'bonusCommissionHistoryPK'.
     * 
     * @return the value of field 'bonusCommissionHistoryPK'.
     */
    public long getBonusCommissionHistoryPK()
    {
        return this._bonusCommissionHistoryPK;
    } //-- long getBonusCommissionHistoryPK() 

    /**
     * Method getBonusUpdateDateTimeReturns the value of field
     * 'bonusUpdateDateTime'.
     * 
     * @return the value of field 'bonusUpdateDateTime'.
     */
    public java.lang.String getBonusUpdateDateTime()
    {
        return this._bonusUpdateDateTime;
    } //-- java.lang.String getBonusUpdateDateTime() 

    /**
     * Method getBonusUpdateStatusReturns the value of field
     * 'bonusUpdateStatus'.
     * 
     * @return the value of field 'bonusUpdateStatus'.
     */
    public java.lang.String getBonusUpdateStatus()
    {
        return this._bonusUpdateStatus;
    } //-- java.lang.String getBonusUpdateStatus() 

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
     * Method getParticipatingAgentFKReturns the value of field
     * 'participatingAgentFK'.
     * 
     * @return the value of field 'participatingAgentFK'.
     */
    public long getParticipatingAgentFK()
    {
        return this._participatingAgentFK;
    } //-- long getParticipatingAgentFK() 

    /**
     * Method getTransactionTypeCTReturns the value of field
     * 'transactionTypeCT'.
     * 
     * @return the value of field 'transactionTypeCT'.
     */
    public java.lang.String getTransactionTypeCT()
    {
        return this._transactionTypeCT;
    } //-- java.lang.String getTransactionTypeCT() 

    /**
     * Method getUpdateReasonReturns the value of field
     * 'updateReason'.
     * 
     * @return the value of field 'updateReason'.
     */
    public java.lang.String getUpdateReason()
    {
        return this._updateReason;
    } //-- java.lang.String getUpdateReason() 

    /**
     * Method hasBonusCommissionHistoryPK
     */
    public boolean hasBonusCommissionHistoryPK()
    {
        return this._has_bonusCommissionHistoryPK;
    } //-- boolean hasBonusCommissionHistoryPK() 

    /**
     * Method hasCommissionHistoryFK
     */
    public boolean hasCommissionHistoryFK()
    {
        return this._has_commissionHistoryFK;
    } //-- boolean hasCommissionHistoryFK() 

    /**
     * Method hasParticipatingAgentFK
     */
    public boolean hasParticipatingAgentFK()
    {
        return this._has_participatingAgentFK;
    } //-- boolean hasParticipatingAgentFK() 

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
     * Method setAccountingPendingStatusSets the value of field
     * 'accountingPendingStatus'.
     * 
     * @param accountingPendingStatus the value of field
     * 'accountingPendingStatus'.
     */
    public void setAccountingPendingStatus(java.lang.String accountingPendingStatus)
    {
        this._accountingPendingStatus = accountingPendingStatus;
        
        super.setVoChanged(true);
    } //-- void setAccountingPendingStatus(java.lang.String) 

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
     * Method setBonusCommissionHistoryPKSets the value of field
     * 'bonusCommissionHistoryPK'.
     * 
     * @param bonusCommissionHistoryPK the value of field
     * 'bonusCommissionHistoryPK'.
     */
    public void setBonusCommissionHistoryPK(long bonusCommissionHistoryPK)
    {
        this._bonusCommissionHistoryPK = bonusCommissionHistoryPK;
        
        super.setVoChanged(true);
        this._has_bonusCommissionHistoryPK = true;
    } //-- void setBonusCommissionHistoryPK(long) 

    /**
     * Method setBonusUpdateDateTimeSets the value of field
     * 'bonusUpdateDateTime'.
     * 
     * @param bonusUpdateDateTime the value of field
     * 'bonusUpdateDateTime'.
     */
    public void setBonusUpdateDateTime(java.lang.String bonusUpdateDateTime)
    {
        this._bonusUpdateDateTime = bonusUpdateDateTime;
        
        super.setVoChanged(true);
    } //-- void setBonusUpdateDateTime(java.lang.String) 

    /**
     * Method setBonusUpdateStatusSets the value of field
     * 'bonusUpdateStatus'.
     * 
     * @param bonusUpdateStatus the value of field
     * 'bonusUpdateStatus'.
     */
    public void setBonusUpdateStatus(java.lang.String bonusUpdateStatus)
    {
        this._bonusUpdateStatus = bonusUpdateStatus;
        
        super.setVoChanged(true);
    } //-- void setBonusUpdateStatus(java.lang.String) 

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
     * Method setParticipatingAgentFKSets the value of field
     * 'participatingAgentFK'.
     * 
     * @param participatingAgentFK the value of field
     * 'participatingAgentFK'.
     */
    public void setParticipatingAgentFK(long participatingAgentFK)
    {
        this._participatingAgentFK = participatingAgentFK;
        
        super.setVoChanged(true);
        this._has_participatingAgentFK = true;
    } //-- void setParticipatingAgentFK(long) 

    /**
     * Method setTransactionTypeCTSets the value of field
     * 'transactionTypeCT'.
     * 
     * @param transactionTypeCT the value of field
     * 'transactionTypeCT'.
     */
    public void setTransactionTypeCT(java.lang.String transactionTypeCT)
    {
        this._transactionTypeCT = transactionTypeCT;
        
        super.setVoChanged(true);
    } //-- void setTransactionTypeCT(java.lang.String) 

    /**
     * Method setUpdateReasonSets the value of field
     * 'updateReason'.
     * 
     * @param updateReason the value of field 'updateReason'.
     */
    public void setUpdateReason(java.lang.String updateReason)
    {
        this._updateReason = updateReason;
        
        super.setVoChanged(true);
    } //-- void setUpdateReason(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BonusCommissionHistoryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BonusCommissionHistoryVO) Unmarshaller.unmarshal(edit.common.vo.BonusCommissionHistoryVO.class, reader);
    } //-- edit.common.vo.BonusCommissionHistoryVO unmarshal(java.io.Reader) 

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
