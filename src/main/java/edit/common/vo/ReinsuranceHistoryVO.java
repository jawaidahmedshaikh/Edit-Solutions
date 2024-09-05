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
 * Class ReinsuranceHistoryVO.
 * 
 * @version $Revision$ $Date$
 */
public class ReinsuranceHistoryVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _reinsuranceHistoryPK
     */
    private long _reinsuranceHistoryPK;

    /**
     * keeps track of state for field: _reinsuranceHistoryPK
     */
    private boolean _has_reinsuranceHistoryPK;

    /**
     * Field _EDITTrxHistoryFK
     */
    private long _EDITTrxHistoryFK;

    /**
     * keeps track of state for field: _EDITTrxHistoryFK
     */
    private boolean _has_EDITTrxHistoryFK;

    /**
     * Field _contractTreatyFK
     */
    private long _contractTreatyFK;

    /**
     * keeps track of state for field: _contractTreatyFK
     */
    private boolean _has_contractTreatyFK;

    /**
     * Field _modalPremiumAmount
     */
    private java.math.BigDecimal _modalPremiumAmount;

    /**
     * Field _reinsuranceNAR
     */
    private java.math.BigDecimal _reinsuranceNAR;

    /**
     * Field _updateStatus
     */
    private java.lang.String _updateStatus;

    /**
     * Field _reinsuranceContractPeriodCT
     */
    private java.lang.String _reinsuranceContractPeriodCT;

    /**
     * Field _updateDateTime
     */
    private java.lang.String _updateDateTime;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _accountingPendingStatus
     */
    private java.lang.String _accountingPendingStatus;

    /**
     * Field _reinsuranceTypeCT
     */
    private java.lang.String _reinsuranceTypeCT;

    /**
     * Field _undoRedoStatus
     */
    private java.lang.String _undoRedoStatus;

    /**
     * Field _cededDeathBenefit
     */
    private java.math.BigDecimal _cededDeathBenefit;


      //----------------/
     //- Constructors -/
    //----------------/

    public ReinsuranceHistoryVO() {
        super();
    } //-- edit.common.vo.ReinsuranceHistoryVO()


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
        
        if (obj instanceof ReinsuranceHistoryVO) {
        
            ReinsuranceHistoryVO temp = (ReinsuranceHistoryVO)obj;
            if (this._reinsuranceHistoryPK != temp._reinsuranceHistoryPK)
                return false;
            if (this._has_reinsuranceHistoryPK != temp._has_reinsuranceHistoryPK)
                return false;
            if (this._EDITTrxHistoryFK != temp._EDITTrxHistoryFK)
                return false;
            if (this._has_EDITTrxHistoryFK != temp._has_EDITTrxHistoryFK)
                return false;
            if (this._contractTreatyFK != temp._contractTreatyFK)
                return false;
            if (this._has_contractTreatyFK != temp._has_contractTreatyFK)
                return false;
            if (this._modalPremiumAmount != null) {
                if (temp._modalPremiumAmount == null) return false;
                else if (!(this._modalPremiumAmount.equals(temp._modalPremiumAmount))) 
                    return false;
            }
            else if (temp._modalPremiumAmount != null)
                return false;
            if (this._reinsuranceNAR != null) {
                if (temp._reinsuranceNAR == null) return false;
                else if (!(this._reinsuranceNAR.equals(temp._reinsuranceNAR))) 
                    return false;
            }
            else if (temp._reinsuranceNAR != null)
                return false;
            if (this._updateStatus != null) {
                if (temp._updateStatus == null) return false;
                else if (!(this._updateStatus.equals(temp._updateStatus))) 
                    return false;
            }
            else if (temp._updateStatus != null)
                return false;
            if (this._reinsuranceContractPeriodCT != null) {
                if (temp._reinsuranceContractPeriodCT == null) return false;
                else if (!(this._reinsuranceContractPeriodCT.equals(temp._reinsuranceContractPeriodCT))) 
                    return false;
            }
            else if (temp._reinsuranceContractPeriodCT != null)
                return false;
            if (this._updateDateTime != null) {
                if (temp._updateDateTime == null) return false;
                else if (!(this._updateDateTime.equals(temp._updateDateTime))) 
                    return false;
            }
            else if (temp._updateDateTime != null)
                return false;
            if (this._maintDateTime != null) {
                if (temp._maintDateTime == null) return false;
                else if (!(this._maintDateTime.equals(temp._maintDateTime))) 
                    return false;
            }
            else if (temp._maintDateTime != null)
                return false;
            if (this._operator != null) {
                if (temp._operator == null) return false;
                else if (!(this._operator.equals(temp._operator))) 
                    return false;
            }
            else if (temp._operator != null)
                return false;
            if (this._accountingPendingStatus != null) {
                if (temp._accountingPendingStatus == null) return false;
                else if (!(this._accountingPendingStatus.equals(temp._accountingPendingStatus))) 
                    return false;
            }
            else if (temp._accountingPendingStatus != null)
                return false;
            if (this._reinsuranceTypeCT != null) {
                if (temp._reinsuranceTypeCT == null) return false;
                else if (!(this._reinsuranceTypeCT.equals(temp._reinsuranceTypeCT))) 
                    return false;
            }
            else if (temp._reinsuranceTypeCT != null)
                return false;
            if (this._undoRedoStatus != null) {
                if (temp._undoRedoStatus == null) return false;
                else if (!(this._undoRedoStatus.equals(temp._undoRedoStatus))) 
                    return false;
            }
            else if (temp._undoRedoStatus != null)
                return false;
            if (this._cededDeathBenefit != null) {
                if (temp._cededDeathBenefit == null) return false;
                else if (!(this._cededDeathBenefit.equals(temp._cededDeathBenefit))) 
                    return false;
            }
            else if (temp._cededDeathBenefit != null)
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
     * Method getCededDeathBenefitReturns the value of field
     * 'cededDeathBenefit'.
     * 
     * @return the value of field 'cededDeathBenefit'.
     */
    public java.math.BigDecimal getCededDeathBenefit()
    {
        return this._cededDeathBenefit;
    } //-- java.math.BigDecimal getCededDeathBenefit() 

    /**
     * Method getContractTreatyFKReturns the value of field
     * 'contractTreatyFK'.
     * 
     * @return the value of field 'contractTreatyFK'.
     */
    public long getContractTreatyFK()
    {
        return this._contractTreatyFK;
    } //-- long getContractTreatyFK() 

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
     * Method getMaintDateTimeReturns the value of field
     * 'maintDateTime'.
     * 
     * @return the value of field 'maintDateTime'.
     */
    public java.lang.String getMaintDateTime()
    {
        return this._maintDateTime;
    } //-- java.lang.String getMaintDateTime() 

    /**
     * Method getModalPremiumAmountReturns the value of field
     * 'modalPremiumAmount'.
     * 
     * @return the value of field 'modalPremiumAmount'.
     */
    public java.math.BigDecimal getModalPremiumAmount()
    {
        return this._modalPremiumAmount;
    } //-- java.math.BigDecimal getModalPremiumAmount() 

    /**
     * Method getOperatorReturns the value of field 'operator'.
     * 
     * @return the value of field 'operator'.
     */
    public java.lang.String getOperator()
    {
        return this._operator;
    } //-- java.lang.String getOperator() 

    /**
     * Method getReinsuranceContractPeriodCTReturns the value of
     * field 'reinsuranceContractPeriodCT'.
     * 
     * @return the value of field 'reinsuranceContractPeriodCT'.
     */
    public java.lang.String getReinsuranceContractPeriodCT()
    {
        return this._reinsuranceContractPeriodCT;
    } //-- java.lang.String getReinsuranceContractPeriodCT() 

    /**
     * Method getReinsuranceHistoryPKReturns the value of field
     * 'reinsuranceHistoryPK'.
     * 
     * @return the value of field 'reinsuranceHistoryPK'.
     */
    public long getReinsuranceHistoryPK()
    {
        return this._reinsuranceHistoryPK;
    } //-- long getReinsuranceHistoryPK() 

    /**
     * Method getReinsuranceNARReturns the value of field
     * 'reinsuranceNAR'.
     * 
     * @return the value of field 'reinsuranceNAR'.
     */
    public java.math.BigDecimal getReinsuranceNAR()
    {
        return this._reinsuranceNAR;
    } //-- java.math.BigDecimal getReinsuranceNAR() 

    /**
     * Method getReinsuranceTypeCTReturns the value of field
     * 'reinsuranceTypeCT'.
     * 
     * @return the value of field 'reinsuranceTypeCT'.
     */
    public java.lang.String getReinsuranceTypeCT()
    {
        return this._reinsuranceTypeCT;
    } //-- java.lang.String getReinsuranceTypeCT() 

    /**
     * Method getUndoRedoStatusReturns the value of field
     * 'undoRedoStatus'.
     * 
     * @return the value of field 'undoRedoStatus'.
     */
    public java.lang.String getUndoRedoStatus()
    {
        return this._undoRedoStatus;
    } //-- java.lang.String getUndoRedoStatus() 

    /**
     * Method getUpdateDateTimeReturns the value of field
     * 'updateDateTime'.
     * 
     * @return the value of field 'updateDateTime'.
     */
    public java.lang.String getUpdateDateTime()
    {
        return this._updateDateTime;
    } //-- java.lang.String getUpdateDateTime() 

    /**
     * Method getUpdateStatusReturns the value of field
     * 'updateStatus'.
     * 
     * @return the value of field 'updateStatus'.
     */
    public java.lang.String getUpdateStatus()
    {
        return this._updateStatus;
    } //-- java.lang.String getUpdateStatus() 

    /**
     * Method hasContractTreatyFK
     */
    public boolean hasContractTreatyFK()
    {
        return this._has_contractTreatyFK;
    } //-- boolean hasContractTreatyFK() 

    /**
     * Method hasEDITTrxHistoryFK
     */
    public boolean hasEDITTrxHistoryFK()
    {
        return this._has_EDITTrxHistoryFK;
    } //-- boolean hasEDITTrxHistoryFK() 

    /**
     * Method hasReinsuranceHistoryPK
     */
    public boolean hasReinsuranceHistoryPK()
    {
        return this._has_reinsuranceHistoryPK;
    } //-- boolean hasReinsuranceHistoryPK() 

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
     * Method setCededDeathBenefitSets the value of field
     * 'cededDeathBenefit'.
     * 
     * @param cededDeathBenefit the value of field
     * 'cededDeathBenefit'.
     */
    public void setCededDeathBenefit(java.math.BigDecimal cededDeathBenefit)
    {
        this._cededDeathBenefit = cededDeathBenefit;
        
        super.setVoChanged(true);
    } //-- void setCededDeathBenefit(java.math.BigDecimal) 

    /**
     * Method setContractTreatyFKSets the value of field
     * 'contractTreatyFK'.
     * 
     * @param contractTreatyFK the value of field 'contractTreatyFK'
     */
    public void setContractTreatyFK(long contractTreatyFK)
    {
        this._contractTreatyFK = contractTreatyFK;
        
        super.setVoChanged(true);
        this._has_contractTreatyFK = true;
    } //-- void setContractTreatyFK(long) 

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
     * Method setMaintDateTimeSets the value of field
     * 'maintDateTime'.
     * 
     * @param maintDateTime the value of field 'maintDateTime'.
     */
    public void setMaintDateTime(java.lang.String maintDateTime)
    {
        this._maintDateTime = maintDateTime;
        
        super.setVoChanged(true);
    } //-- void setMaintDateTime(java.lang.String) 

    /**
     * Method setModalPremiumAmountSets the value of field
     * 'modalPremiumAmount'.
     * 
     * @param modalPremiumAmount the value of field
     * 'modalPremiumAmount'.
     */
    public void setModalPremiumAmount(java.math.BigDecimal modalPremiumAmount)
    {
        this._modalPremiumAmount = modalPremiumAmount;
        
        super.setVoChanged(true);
    } //-- void setModalPremiumAmount(java.math.BigDecimal) 

    /**
     * Method setOperatorSets the value of field 'operator'.
     * 
     * @param operator the value of field 'operator'.
     */
    public void setOperator(java.lang.String operator)
    {
        this._operator = operator;
        
        super.setVoChanged(true);
    } //-- void setOperator(java.lang.String) 

    /**
     * Method setReinsuranceContractPeriodCTSets the value of field
     * 'reinsuranceContractPeriodCT'.
     * 
     * @param reinsuranceContractPeriodCT the value of field
     * 'reinsuranceContractPeriodCT'.
     */
    public void setReinsuranceContractPeriodCT(java.lang.String reinsuranceContractPeriodCT)
    {
        this._reinsuranceContractPeriodCT = reinsuranceContractPeriodCT;
        
        super.setVoChanged(true);
    } //-- void setReinsuranceContractPeriodCT(java.lang.String) 

    /**
     * Method setReinsuranceHistoryPKSets the value of field
     * 'reinsuranceHistoryPK'.
     * 
     * @param reinsuranceHistoryPK the value of field
     * 'reinsuranceHistoryPK'.
     */
    public void setReinsuranceHistoryPK(long reinsuranceHistoryPK)
    {
        this._reinsuranceHistoryPK = reinsuranceHistoryPK;
        
        super.setVoChanged(true);
        this._has_reinsuranceHistoryPK = true;
    } //-- void setReinsuranceHistoryPK(long) 

    /**
     * Method setReinsuranceNARSets the value of field
     * 'reinsuranceNAR'.
     * 
     * @param reinsuranceNAR the value of field 'reinsuranceNAR'.
     */
    public void setReinsuranceNAR(java.math.BigDecimal reinsuranceNAR)
    {
        this._reinsuranceNAR = reinsuranceNAR;
        
        super.setVoChanged(true);
    } //-- void setReinsuranceNAR(java.math.BigDecimal) 

    /**
     * Method setReinsuranceTypeCTSets the value of field
     * 'reinsuranceTypeCT'.
     * 
     * @param reinsuranceTypeCT the value of field
     * 'reinsuranceTypeCT'.
     */
    public void setReinsuranceTypeCT(java.lang.String reinsuranceTypeCT)
    {
        this._reinsuranceTypeCT = reinsuranceTypeCT;
        
        super.setVoChanged(true);
    } //-- void setReinsuranceTypeCT(java.lang.String) 

    /**
     * Method setUndoRedoStatusSets the value of field
     * 'undoRedoStatus'.
     * 
     * @param undoRedoStatus the value of field 'undoRedoStatus'.
     */
    public void setUndoRedoStatus(java.lang.String undoRedoStatus)
    {
        this._undoRedoStatus = undoRedoStatus;
        
        super.setVoChanged(true);
    } //-- void setUndoRedoStatus(java.lang.String) 

    /**
     * Method setUpdateDateTimeSets the value of field
     * 'updateDateTime'.
     * 
     * @param updateDateTime the value of field 'updateDateTime'.
     */
    public void setUpdateDateTime(java.lang.String updateDateTime)
    {
        this._updateDateTime = updateDateTime;
        
        super.setVoChanged(true);
    } //-- void setUpdateDateTime(java.lang.String) 

    /**
     * Method setUpdateStatusSets the value of field
     * 'updateStatus'.
     * 
     * @param updateStatus the value of field 'updateStatus'.
     */
    public void setUpdateStatus(java.lang.String updateStatus)
    {
        this._updateStatus = updateStatus;
        
        super.setVoChanged(true);
    } //-- void setUpdateStatus(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ReinsuranceHistoryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ReinsuranceHistoryVO) Unmarshaller.unmarshal(edit.common.vo.ReinsuranceHistoryVO.class, reader);
    } //-- edit.common.vo.ReinsuranceHistoryVO unmarshal(java.io.Reader) 

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
