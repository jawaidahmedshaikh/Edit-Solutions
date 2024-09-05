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
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class CommissionHistoryVO.
 * 
 * @version $Revision$ $Date$
 */
public class CommissionHistoryVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _commissionHistoryPK
     */
    private long _commissionHistoryPK;

    /**
     * keeps track of state for field: _commissionHistoryPK
     */
    private boolean _has_commissionHistoryPK;

    /**
     * Field _EDITTrxHistoryFK
     */
    private long _EDITTrxHistoryFK;

    /**
     * keeps track of state for field: _EDITTrxHistoryFK
     */
    private boolean _has_EDITTrxHistoryFK;

    /**
     * Field _sourcePlacedAgentFK
     */
    private long _sourcePlacedAgentFK;

    /**
     * keeps track of state for field: _sourcePlacedAgentFK
     */
    private boolean _has_sourcePlacedAgentFK;

    /**
     * Field _agentSnapshotFK
     */
    private long _agentSnapshotFK;

    /**
     * keeps track of state for field: _agentSnapshotFK
     */
    private boolean _has_agentSnapshotFK;

    /**
     * Field _placedAgentFK
     */
    private long _placedAgentFK;

    /**
     * keeps track of state for field: _placedAgentFK
     */
    private boolean _has_placedAgentFK;

    /**
     * Field _agentGroupFK
     */
    private long _agentGroupFK;

    /**
     * keeps track of state for field: _agentGroupFK
     */
    private boolean _has_agentGroupFK;

    /**
     * Field _commissionAmount
     */
    private java.math.BigDecimal _commissionAmount;

    /**
     * Field _commissionTaxable
     */
    private java.math.BigDecimal _commissionTaxable;

    /**
     * Field _commissionNonTaxable
     */
    private java.math.BigDecimal _commissionNonTaxable;

    /**
     * Field _clientRoleToFK
     */
    private long _clientRoleToFK;

    /**
     * keeps track of state for field: _clientRoleToFK
     */
    private boolean _has_clientRoleToFK;

    /**
     * Field _ADAAmount
     */
    private java.math.BigDecimal _ADAAmount;

    /**
     * Field _expenseAmount
     */
    private java.math.BigDecimal _expenseAmount;

    /**
     * Field _updateStatus
     */
    private java.lang.String _updateStatus;

    /**
     * Field _taxRoleToFK
     */
    private long _taxRoleToFK;

    /**
     * keeps track of state for field: _taxRoleToFK
     */
    private boolean _has_taxRoleToFK;

    /**
     * Field _accountingPendingStatus
     */
    private java.lang.String _accountingPendingStatus;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _commissionRate
     */
    private java.math.BigDecimal _commissionRate;

    /**
     * Field _commissionTypeCT
     */
    private java.lang.String _commissionTypeCT;

    /**
     * Field _updateDateTime
     */
    private java.lang.String _updateDateTime;

    /**
     * Field _statementInd
     */
    private java.lang.String _statementInd;

    /**
     * Field _reduceTaxable
     */
    private java.lang.String _reduceTaxable;

    /**
     * Field _undoRedoStatus
     */
    private java.lang.String _undoRedoStatus;

    /**
     * Field _debitBalanceAmount
     */
    private java.math.BigDecimal _debitBalanceAmount;

    /**
     * Field _commHoldReleaseDate
     */
    private java.lang.String _commHoldReleaseDate;

    /**
     * Field _includedInDebitBalInd
     */
    private java.lang.String _includedInDebitBalInd;

    /**
     * Field _checkTo
     */
    private long _checkTo;

    /**
     * keeps track of state for field: _checkTo
     */
    private boolean _has_checkTo;

    /**
     * Field _groupTypeCT
     */
    private java.lang.String _groupTypeCT;

    /**
     * Field _bonusCommissionHistoryVOList
     */
    private java.util.Vector _bonusCommissionHistoryVOList;

    /**
     * Field _commissionInvestmentHistoryVOList
     */
    private java.util.Vector _commissionInvestmentHistoryVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public CommissionHistoryVO() {
        super();
        _bonusCommissionHistoryVOList = new Vector();
        _commissionInvestmentHistoryVOList = new Vector();
    } //-- edit.common.vo.CommissionHistoryVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addBonusCommissionHistoryVO
     * 
     * @param vBonusCommissionHistoryVO
     */
    public void addBonusCommissionHistoryVO(edit.common.vo.BonusCommissionHistoryVO vBonusCommissionHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBonusCommissionHistoryVO.setParentVO(this.getClass(), this);
        _bonusCommissionHistoryVOList.addElement(vBonusCommissionHistoryVO);
    } //-- void addBonusCommissionHistoryVO(edit.common.vo.BonusCommissionHistoryVO) 

    /**
     * Method addBonusCommissionHistoryVO
     * 
     * @param index
     * @param vBonusCommissionHistoryVO
     */
    public void addBonusCommissionHistoryVO(int index, edit.common.vo.BonusCommissionHistoryVO vBonusCommissionHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBonusCommissionHistoryVO.setParentVO(this.getClass(), this);
        _bonusCommissionHistoryVOList.insertElementAt(vBonusCommissionHistoryVO, index);
    } //-- void addBonusCommissionHistoryVO(int, edit.common.vo.BonusCommissionHistoryVO) 

    /**
     * Method addCommissionInvestmentHistoryVO
     * 
     * @param vCommissionInvestmentHistoryVO
     */
    public void addCommissionInvestmentHistoryVO(edit.common.vo.CommissionInvestmentHistoryVO vCommissionInvestmentHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionInvestmentHistoryVO.setParentVO(this.getClass(), this);
        _commissionInvestmentHistoryVOList.addElement(vCommissionInvestmentHistoryVO);
    } //-- void addCommissionInvestmentHistoryVO(edit.common.vo.CommissionInvestmentHistoryVO) 

    /**
     * Method addCommissionInvestmentHistoryVO
     * 
     * @param index
     * @param vCommissionInvestmentHistoryVO
     */
    public void addCommissionInvestmentHistoryVO(int index, edit.common.vo.CommissionInvestmentHistoryVO vCommissionInvestmentHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionInvestmentHistoryVO.setParentVO(this.getClass(), this);
        _commissionInvestmentHistoryVOList.insertElementAt(vCommissionInvestmentHistoryVO, index);
    } //-- void addCommissionInvestmentHistoryVO(int, edit.common.vo.CommissionInvestmentHistoryVO) 

    /**
     * Method enumerateBonusCommissionHistoryVO
     */
    public java.util.Enumeration enumerateBonusCommissionHistoryVO()
    {
        return _bonusCommissionHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateBonusCommissionHistoryVO() 

    /**
     * Method enumerateCommissionInvestmentHistoryVO
     */
    public java.util.Enumeration enumerateCommissionInvestmentHistoryVO()
    {
        return _commissionInvestmentHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateCommissionInvestmentHistoryVO() 

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
        
        if (obj instanceof CommissionHistoryVO) {
        
            CommissionHistoryVO temp = (CommissionHistoryVO)obj;
            if (this._commissionHistoryPK != temp._commissionHistoryPK)
                return false;
            if (this._has_commissionHistoryPK != temp._has_commissionHistoryPK)
                return false;
            if (this._EDITTrxHistoryFK != temp._EDITTrxHistoryFK)
                return false;
            if (this._has_EDITTrxHistoryFK != temp._has_EDITTrxHistoryFK)
                return false;
            if (this._sourcePlacedAgentFK != temp._sourcePlacedAgentFK)
                return false;
            if (this._has_sourcePlacedAgentFK != temp._has_sourcePlacedAgentFK)
                return false;
            if (this._agentSnapshotFK != temp._agentSnapshotFK)
                return false;
            if (this._has_agentSnapshotFK != temp._has_agentSnapshotFK)
                return false;
            if (this._placedAgentFK != temp._placedAgentFK)
                return false;
            if (this._has_placedAgentFK != temp._has_placedAgentFK)
                return false;
            if (this._agentGroupFK != temp._agentGroupFK)
                return false;
            if (this._has_agentGroupFK != temp._has_agentGroupFK)
                return false;
            if (this._commissionAmount != null) {
                if (temp._commissionAmount == null) return false;
                else if (!(this._commissionAmount.equals(temp._commissionAmount))) 
                    return false;
            }
            else if (temp._commissionAmount != null)
                return false;
            if (this._commissionTaxable != null) {
                if (temp._commissionTaxable == null) return false;
                else if (!(this._commissionTaxable.equals(temp._commissionTaxable))) 
                    return false;
            }
            else if (temp._commissionTaxable != null)
                return false;
            if (this._commissionNonTaxable != null) {
                if (temp._commissionNonTaxable == null) return false;
                else if (!(this._commissionNonTaxable.equals(temp._commissionNonTaxable))) 
                    return false;
            }
            else if (temp._commissionNonTaxable != null)
                return false;
            if (this._clientRoleToFK != temp._clientRoleToFK)
                return false;
            if (this._has_clientRoleToFK != temp._has_clientRoleToFK)
                return false;
            if (this._ADAAmount != null) {
                if (temp._ADAAmount == null) return false;
                else if (!(this._ADAAmount.equals(temp._ADAAmount))) 
                    return false;
            }
            else if (temp._ADAAmount != null)
                return false;
            if (this._expenseAmount != null) {
                if (temp._expenseAmount == null) return false;
                else if (!(this._expenseAmount.equals(temp._expenseAmount))) 
                    return false;
            }
            else if (temp._expenseAmount != null)
                return false;
            if (this._updateStatus != null) {
                if (temp._updateStatus == null) return false;
                else if (!(this._updateStatus.equals(temp._updateStatus))) 
                    return false;
            }
            else if (temp._updateStatus != null)
                return false;
            if (this._taxRoleToFK != temp._taxRoleToFK)
                return false;
            if (this._has_taxRoleToFK != temp._has_taxRoleToFK)
                return false;
            if (this._accountingPendingStatus != null) {
                if (temp._accountingPendingStatus == null) return false;
                else if (!(this._accountingPendingStatus.equals(temp._accountingPendingStatus))) 
                    return false;
            }
            else if (temp._accountingPendingStatus != null)
                return false;
            if (this._operator != null) {
                if (temp._operator == null) return false;
                else if (!(this._operator.equals(temp._operator))) 
                    return false;
            }
            else if (temp._operator != null)
                return false;
            if (this._maintDateTime != null) {
                if (temp._maintDateTime == null) return false;
                else if (!(this._maintDateTime.equals(temp._maintDateTime))) 
                    return false;
            }
            else if (temp._maintDateTime != null)
                return false;
            if (this._commissionRate != null) {
                if (temp._commissionRate == null) return false;
                else if (!(this._commissionRate.equals(temp._commissionRate))) 
                    return false;
            }
            else if (temp._commissionRate != null)
                return false;
            if (this._commissionTypeCT != null) {
                if (temp._commissionTypeCT == null) return false;
                else if (!(this._commissionTypeCT.equals(temp._commissionTypeCT))) 
                    return false;
            }
            else if (temp._commissionTypeCT != null)
                return false;
            if (this._updateDateTime != null) {
                if (temp._updateDateTime == null) return false;
                else if (!(this._updateDateTime.equals(temp._updateDateTime))) 
                    return false;
            }
            else if (temp._updateDateTime != null)
                return false;
            if (this._statementInd != null) {
                if (temp._statementInd == null) return false;
                else if (!(this._statementInd.equals(temp._statementInd))) 
                    return false;
            }
            else if (temp._statementInd != null)
                return false;
            if (this._reduceTaxable != null) {
                if (temp._reduceTaxable == null) return false;
                else if (!(this._reduceTaxable.equals(temp._reduceTaxable))) 
                    return false;
            }
            else if (temp._reduceTaxable != null)
                return false;
            if (this._undoRedoStatus != null) {
                if (temp._undoRedoStatus == null) return false;
                else if (!(this._undoRedoStatus.equals(temp._undoRedoStatus))) 
                    return false;
            }
            else if (temp._undoRedoStatus != null)
                return false;
            if (this._debitBalanceAmount != null) {
                if (temp._debitBalanceAmount == null) return false;
                else if (!(this._debitBalanceAmount.equals(temp._debitBalanceAmount))) 
                    return false;
            }
            else if (temp._debitBalanceAmount != null)
                return false;
            if (this._commHoldReleaseDate != null) {
                if (temp._commHoldReleaseDate == null) return false;
                else if (!(this._commHoldReleaseDate.equals(temp._commHoldReleaseDate))) 
                    return false;
            }
            else if (temp._commHoldReleaseDate != null)
                return false;
            if (this._includedInDebitBalInd != null) {
                if (temp._includedInDebitBalInd == null) return false;
                else if (!(this._includedInDebitBalInd.equals(temp._includedInDebitBalInd))) 
                    return false;
            }
            else if (temp._includedInDebitBalInd != null)
                return false;
            if (this._checkTo != temp._checkTo)
                return false;
            if (this._has_checkTo != temp._has_checkTo)
                return false;
            if (this._groupTypeCT != null) {
                if (temp._groupTypeCT == null) return false;
                else if (!(this._groupTypeCT.equals(temp._groupTypeCT))) 
                    return false;
            }
            else if (temp._groupTypeCT != null)
                return false;
            if (this._bonusCommissionHistoryVOList != null) {
                if (temp._bonusCommissionHistoryVOList == null) return false;
                else if (!(this._bonusCommissionHistoryVOList.equals(temp._bonusCommissionHistoryVOList))) 
                    return false;
            }
            else if (temp._bonusCommissionHistoryVOList != null)
                return false;
            if (this._commissionInvestmentHistoryVOList != null) {
                if (temp._commissionInvestmentHistoryVOList == null) return false;
                else if (!(this._commissionInvestmentHistoryVOList.equals(temp._commissionInvestmentHistoryVOList))) 
                    return false;
            }
            else if (temp._commissionInvestmentHistoryVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getADAAmountReturns the value of field 'ADAAmount'.
     * 
     * @return the value of field 'ADAAmount'.
     */
    public java.math.BigDecimal getADAAmount()
    {
        return this._ADAAmount;
    } //-- java.math.BigDecimal getADAAmount() 

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
     * Method getAgentGroupFKReturns the value of field
     * 'agentGroupFK'.
     * 
     * @return the value of field 'agentGroupFK'.
     */
    public long getAgentGroupFK()
    {
        return this._agentGroupFK;
    } //-- long getAgentGroupFK() 

    /**
     * Method getAgentSnapshotFKReturns the value of field
     * 'agentSnapshotFK'.
     * 
     * @return the value of field 'agentSnapshotFK'.
     */
    public long getAgentSnapshotFK()
    {
        return this._agentSnapshotFK;
    } //-- long getAgentSnapshotFK() 

    /**
     * Method getBonusCommissionHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.BonusCommissionHistoryVO getBonusCommissionHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bonusCommissionHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.BonusCommissionHistoryVO) _bonusCommissionHistoryVOList.elementAt(index);
    } //-- edit.common.vo.BonusCommissionHistoryVO getBonusCommissionHistoryVO(int) 

    /**
     * Method getBonusCommissionHistoryVO
     */
    public edit.common.vo.BonusCommissionHistoryVO[] getBonusCommissionHistoryVO()
    {
        int size = _bonusCommissionHistoryVOList.size();
        edit.common.vo.BonusCommissionHistoryVO[] mArray = new edit.common.vo.BonusCommissionHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.BonusCommissionHistoryVO) _bonusCommissionHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.BonusCommissionHistoryVO[] getBonusCommissionHistoryVO() 

    /**
     * Method getBonusCommissionHistoryVOCount
     */
    public int getBonusCommissionHistoryVOCount()
    {
        return _bonusCommissionHistoryVOList.size();
    } //-- int getBonusCommissionHistoryVOCount() 

    /**
     * Method getCheckToReturns the value of field 'checkTo'.
     * 
     * @return the value of field 'checkTo'.
     */
    public long getCheckTo()
    {
        return this._checkTo;
    } //-- long getCheckTo() 

    /**
     * Method getClientRoleToFKReturns the value of field
     * 'clientRoleToFK'.
     * 
     * @return the value of field 'clientRoleToFK'.
     */
    public long getClientRoleToFK()
    {
        return this._clientRoleToFK;
    } //-- long getClientRoleToFK() 

    /**
     * Method getCommHoldReleaseDateReturns the value of field
     * 'commHoldReleaseDate'.
     * 
     * @return the value of field 'commHoldReleaseDate'.
     */
    public java.lang.String getCommHoldReleaseDate()
    {
        return this._commHoldReleaseDate;
    } //-- java.lang.String getCommHoldReleaseDate() 

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
     * Method getCommissionHistoryPKReturns the value of field
     * 'commissionHistoryPK'.
     * 
     * @return the value of field 'commissionHistoryPK'.
     */
    public long getCommissionHistoryPK()
    {
        return this._commissionHistoryPK;
    } //-- long getCommissionHistoryPK() 

    /**
     * Method getCommissionInvestmentHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionInvestmentHistoryVO getCommissionInvestmentHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionInvestmentHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.CommissionInvestmentHistoryVO) _commissionInvestmentHistoryVOList.elementAt(index);
    } //-- edit.common.vo.CommissionInvestmentHistoryVO getCommissionInvestmentHistoryVO(int) 

    /**
     * Method getCommissionInvestmentHistoryVO
     */
    public edit.common.vo.CommissionInvestmentHistoryVO[] getCommissionInvestmentHistoryVO()
    {
        int size = _commissionInvestmentHistoryVOList.size();
        edit.common.vo.CommissionInvestmentHistoryVO[] mArray = new edit.common.vo.CommissionInvestmentHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.CommissionInvestmentHistoryVO) _commissionInvestmentHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.CommissionInvestmentHistoryVO[] getCommissionInvestmentHistoryVO() 

    /**
     * Method getCommissionInvestmentHistoryVOCount
     */
    public int getCommissionInvestmentHistoryVOCount()
    {
        return _commissionInvestmentHistoryVOList.size();
    } //-- int getCommissionInvestmentHistoryVOCount() 

    /**
     * Method getCommissionNonTaxableReturns the value of field
     * 'commissionNonTaxable'.
     * 
     * @return the value of field 'commissionNonTaxable'.
     */
    public java.math.BigDecimal getCommissionNonTaxable()
    {
        return this._commissionNonTaxable;
    } //-- java.math.BigDecimal getCommissionNonTaxable() 

    /**
     * Method getCommissionRateReturns the value of field
     * 'commissionRate'.
     * 
     * @return the value of field 'commissionRate'.
     */
    public java.math.BigDecimal getCommissionRate()
    {
        return this._commissionRate;
    } //-- java.math.BigDecimal getCommissionRate() 

    /**
     * Method getCommissionTaxableReturns the value of field
     * 'commissionTaxable'.
     * 
     * @return the value of field 'commissionTaxable'.
     */
    public java.math.BigDecimal getCommissionTaxable()
    {
        return this._commissionTaxable;
    } //-- java.math.BigDecimal getCommissionTaxable() 

    /**
     * Method getCommissionTypeCTReturns the value of field
     * 'commissionTypeCT'.
     * 
     * @return the value of field 'commissionTypeCT'.
     */
    public java.lang.String getCommissionTypeCT()
    {
        return this._commissionTypeCT;
    } //-- java.lang.String getCommissionTypeCT() 

    /**
     * Method getDebitBalanceAmountReturns the value of field
     * 'debitBalanceAmount'.
     * 
     * @return the value of field 'debitBalanceAmount'.
     */
    public java.math.BigDecimal getDebitBalanceAmount()
    {
        return this._debitBalanceAmount;
    } //-- java.math.BigDecimal getDebitBalanceAmount() 

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
     * Method getExpenseAmountReturns the value of field
     * 'expenseAmount'.
     * 
     * @return the value of field 'expenseAmount'.
     */
    public java.math.BigDecimal getExpenseAmount()
    {
        return this._expenseAmount;
    } //-- java.math.BigDecimal getExpenseAmount() 

    /**
     * Method getGroupTypeCTReturns the value of field
     * 'groupTypeCT'.
     * 
     * @return the value of field 'groupTypeCT'.
     */
    public java.lang.String getGroupTypeCT()
    {
        return this._groupTypeCT;
    } //-- java.lang.String getGroupTypeCT() 

    /**
     * Method getIncludedInDebitBalIndReturns the value of field
     * 'includedInDebitBalInd'.
     * 
     * @return the value of field 'includedInDebitBalInd'.
     */
    public java.lang.String getIncludedInDebitBalInd()
    {
        return this._includedInDebitBalInd;
    } //-- java.lang.String getIncludedInDebitBalInd() 

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
     * Method getOperatorReturns the value of field 'operator'.
     * 
     * @return the value of field 'operator'.
     */
    public java.lang.String getOperator()
    {
        return this._operator;
    } //-- java.lang.String getOperator() 

    /**
     * Method getPlacedAgentFKReturns the value of field
     * 'placedAgentFK'.
     * 
     * @return the value of field 'placedAgentFK'.
     */
    public long getPlacedAgentFK()
    {
        return this._placedAgentFK;
    } //-- long getPlacedAgentFK() 

    /**
     * Method getReduceTaxableReturns the value of field
     * 'reduceTaxable'.
     * 
     * @return the value of field 'reduceTaxable'.
     */
    public java.lang.String getReduceTaxable()
    {
        return this._reduceTaxable;
    } //-- java.lang.String getReduceTaxable() 

    /**
     * Method getSourcePlacedAgentFKReturns the value of field
     * 'sourcePlacedAgentFK'.
     * 
     * @return the value of field 'sourcePlacedAgentFK'.
     */
    public long getSourcePlacedAgentFK()
    {
        return this._sourcePlacedAgentFK;
    } //-- long getSourcePlacedAgentFK() 

    /**
     * Method getStatementIndReturns the value of field
     * 'statementInd'.
     * 
     * @return the value of field 'statementInd'.
     */
    public java.lang.String getStatementInd()
    {
        return this._statementInd;
    } //-- java.lang.String getStatementInd() 

    /**
     * Method getTaxRoleToFKReturns the value of field
     * 'taxRoleToFK'.
     * 
     * @return the value of field 'taxRoleToFK'.
     */
    public long getTaxRoleToFK()
    {
        return this._taxRoleToFK;
    } //-- long getTaxRoleToFK() 

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
     * Method hasAgentGroupFK
     */
    public boolean hasAgentGroupFK()
    {
        return this._has_agentGroupFK;
    } //-- boolean hasAgentGroupFK() 

    /**
     * Method hasAgentSnapshotFK
     */
    public boolean hasAgentSnapshotFK()
    {
        return this._has_agentSnapshotFK;
    } //-- boolean hasAgentSnapshotFK() 

    /**
     * Method hasCheckTo
     */
    public boolean hasCheckTo()
    {
        return this._has_checkTo;
    } //-- boolean hasCheckTo() 

    /**
     * Method hasClientRoleToFK
     */
    public boolean hasClientRoleToFK()
    {
        return this._has_clientRoleToFK;
    } //-- boolean hasClientRoleToFK() 

    /**
     * Method hasCommissionHistoryPK
     */
    public boolean hasCommissionHistoryPK()
    {
        return this._has_commissionHistoryPK;
    } //-- boolean hasCommissionHistoryPK() 

    /**
     * Method hasEDITTrxHistoryFK
     */
    public boolean hasEDITTrxHistoryFK()
    {
        return this._has_EDITTrxHistoryFK;
    } //-- boolean hasEDITTrxHistoryFK() 

    /**
     * Method hasPlacedAgentFK
     */
    public boolean hasPlacedAgentFK()
    {
        return this._has_placedAgentFK;
    } //-- boolean hasPlacedAgentFK() 

    /**
     * Method hasSourcePlacedAgentFK
     */
    public boolean hasSourcePlacedAgentFK()
    {
        return this._has_sourcePlacedAgentFK;
    } //-- boolean hasSourcePlacedAgentFK() 

    /**
     * Method hasTaxRoleToFK
     */
    public boolean hasTaxRoleToFK()
    {
        return this._has_taxRoleToFK;
    } //-- boolean hasTaxRoleToFK() 

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
     * Method removeAllBonusCommissionHistoryVO
     */
    public void removeAllBonusCommissionHistoryVO()
    {
        _bonusCommissionHistoryVOList.removeAllElements();
    } //-- void removeAllBonusCommissionHistoryVO() 

    /**
     * Method removeAllCommissionInvestmentHistoryVO
     */
    public void removeAllCommissionInvestmentHistoryVO()
    {
        _commissionInvestmentHistoryVOList.removeAllElements();
    } //-- void removeAllCommissionInvestmentHistoryVO() 

    /**
     * Method removeBonusCommissionHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.BonusCommissionHistoryVO removeBonusCommissionHistoryVO(int index)
    {
        java.lang.Object obj = _bonusCommissionHistoryVOList.elementAt(index);
        _bonusCommissionHistoryVOList.removeElementAt(index);
        return (edit.common.vo.BonusCommissionHistoryVO) obj;
    } //-- edit.common.vo.BonusCommissionHistoryVO removeBonusCommissionHistoryVO(int) 

    /**
     * Method removeCommissionInvestmentHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionInvestmentHistoryVO removeCommissionInvestmentHistoryVO(int index)
    {
        java.lang.Object obj = _commissionInvestmentHistoryVOList.elementAt(index);
        _commissionInvestmentHistoryVOList.removeElementAt(index);
        return (edit.common.vo.CommissionInvestmentHistoryVO) obj;
    } //-- edit.common.vo.CommissionInvestmentHistoryVO removeCommissionInvestmentHistoryVO(int) 

    /**
     * Method setADAAmountSets the value of field 'ADAAmount'.
     * 
     * @param ADAAmount the value of field 'ADAAmount'.
     */
    public void setADAAmount(java.math.BigDecimal ADAAmount)
    {
        this._ADAAmount = ADAAmount;
        
        super.setVoChanged(true);
    } //-- void setADAAmount(java.math.BigDecimal) 

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
     * Method setAgentGroupFKSets the value of field
     * 'agentGroupFK'.
     * 
     * @param agentGroupFK the value of field 'agentGroupFK'.
     */
    public void setAgentGroupFK(long agentGroupFK)
    {
        this._agentGroupFK = agentGroupFK;
        
        super.setVoChanged(true);
        this._has_agentGroupFK = true;
    } //-- void setAgentGroupFK(long) 

    /**
     * Method setAgentSnapshotFKSets the value of field
     * 'agentSnapshotFK'.
     * 
     * @param agentSnapshotFK the value of field 'agentSnapshotFK'.
     */
    public void setAgentSnapshotFK(long agentSnapshotFK)
    {
        this._agentSnapshotFK = agentSnapshotFK;
        
        super.setVoChanged(true);
        this._has_agentSnapshotFK = true;
    } //-- void setAgentSnapshotFK(long) 

    /**
     * Method setBonusCommissionHistoryVO
     * 
     * @param index
     * @param vBonusCommissionHistoryVO
     */
    public void setBonusCommissionHistoryVO(int index, edit.common.vo.BonusCommissionHistoryVO vBonusCommissionHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bonusCommissionHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vBonusCommissionHistoryVO.setParentVO(this.getClass(), this);
        _bonusCommissionHistoryVOList.setElementAt(vBonusCommissionHistoryVO, index);
    } //-- void setBonusCommissionHistoryVO(int, edit.common.vo.BonusCommissionHistoryVO) 

    /**
     * Method setBonusCommissionHistoryVO
     * 
     * @param bonusCommissionHistoryVOArray
     */
    public void setBonusCommissionHistoryVO(edit.common.vo.BonusCommissionHistoryVO[] bonusCommissionHistoryVOArray)
    {
        //-- copy array
        _bonusCommissionHistoryVOList.removeAllElements();
        for (int i = 0; i < bonusCommissionHistoryVOArray.length; i++) {
            bonusCommissionHistoryVOArray[i].setParentVO(this.getClass(), this);
            _bonusCommissionHistoryVOList.addElement(bonusCommissionHistoryVOArray[i]);
        }
    } //-- void setBonusCommissionHistoryVO(edit.common.vo.BonusCommissionHistoryVO) 

    /**
     * Method setCheckToSets the value of field 'checkTo'.
     * 
     * @param checkTo the value of field 'checkTo'.
     */
    public void setCheckTo(long checkTo)
    {
        this._checkTo = checkTo;
        
        super.setVoChanged(true);
        this._has_checkTo = true;
    } //-- void setCheckTo(long) 

    /**
     * Method setClientRoleToFKSets the value of field
     * 'clientRoleToFK'.
     * 
     * @param clientRoleToFK the value of field 'clientRoleToFK'.
     */
    public void setClientRoleToFK(long clientRoleToFK)
    {
        this._clientRoleToFK = clientRoleToFK;
        
        super.setVoChanged(true);
        this._has_clientRoleToFK = true;
    } //-- void setClientRoleToFK(long) 

    /**
     * Method setCommHoldReleaseDateSets the value of field
     * 'commHoldReleaseDate'.
     * 
     * @param commHoldReleaseDate the value of field
     * 'commHoldReleaseDate'.
     */
    public void setCommHoldReleaseDate(java.lang.String commHoldReleaseDate)
    {
        this._commHoldReleaseDate = commHoldReleaseDate;
        
        super.setVoChanged(true);
    } //-- void setCommHoldReleaseDate(java.lang.String) 

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
     * Method setCommissionHistoryPKSets the value of field
     * 'commissionHistoryPK'.
     * 
     * @param commissionHistoryPK the value of field
     * 'commissionHistoryPK'.
     */
    public void setCommissionHistoryPK(long commissionHistoryPK)
    {
        this._commissionHistoryPK = commissionHistoryPK;
        
        super.setVoChanged(true);
        this._has_commissionHistoryPK = true;
    } //-- void setCommissionHistoryPK(long) 

    /**
     * Method setCommissionInvestmentHistoryVO
     * 
     * @param index
     * @param vCommissionInvestmentHistoryVO
     */
    public void setCommissionInvestmentHistoryVO(int index, edit.common.vo.CommissionInvestmentHistoryVO vCommissionInvestmentHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionInvestmentHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vCommissionInvestmentHistoryVO.setParentVO(this.getClass(), this);
        _commissionInvestmentHistoryVOList.setElementAt(vCommissionInvestmentHistoryVO, index);
    } //-- void setCommissionInvestmentHistoryVO(int, edit.common.vo.CommissionInvestmentHistoryVO) 

    /**
     * Method setCommissionInvestmentHistoryVO
     * 
     * @param commissionInvestmentHistoryVOArray
     */
    public void setCommissionInvestmentHistoryVO(edit.common.vo.CommissionInvestmentHistoryVO[] commissionInvestmentHistoryVOArray)
    {
        //-- copy array
        _commissionInvestmentHistoryVOList.removeAllElements();
        for (int i = 0; i < commissionInvestmentHistoryVOArray.length; i++) {
            commissionInvestmentHistoryVOArray[i].setParentVO(this.getClass(), this);
            _commissionInvestmentHistoryVOList.addElement(commissionInvestmentHistoryVOArray[i]);
        }
    } //-- void setCommissionInvestmentHistoryVO(edit.common.vo.CommissionInvestmentHistoryVO) 

    /**
     * Method setCommissionNonTaxableSets the value of field
     * 'commissionNonTaxable'.
     * 
     * @param commissionNonTaxable the value of field
     * 'commissionNonTaxable'.
     */
    public void setCommissionNonTaxable(java.math.BigDecimal commissionNonTaxable)
    {
        this._commissionNonTaxable = commissionNonTaxable;
        
        super.setVoChanged(true);
    } //-- void setCommissionNonTaxable(java.math.BigDecimal) 

    /**
     * Method setCommissionRateSets the value of field
     * 'commissionRate'.
     * 
     * @param commissionRate the value of field 'commissionRate'.
     */
    public void setCommissionRate(java.math.BigDecimal commissionRate)
    {
        this._commissionRate = commissionRate;
        
        super.setVoChanged(true);
    } //-- void setCommissionRate(java.math.BigDecimal) 

    /**
     * Method setCommissionTaxableSets the value of field
     * 'commissionTaxable'.
     * 
     * @param commissionTaxable the value of field
     * 'commissionTaxable'.
     */
    public void setCommissionTaxable(java.math.BigDecimal commissionTaxable)
    {
        this._commissionTaxable = commissionTaxable;
        
        super.setVoChanged(true);
    } //-- void setCommissionTaxable(java.math.BigDecimal) 

    /**
     * Method setCommissionTypeCTSets the value of field
     * 'commissionTypeCT'.
     * 
     * @param commissionTypeCT the value of field 'commissionTypeCT'
     */
    public void setCommissionTypeCT(java.lang.String commissionTypeCT)
    {
        this._commissionTypeCT = commissionTypeCT;
        
        super.setVoChanged(true);
    } //-- void setCommissionTypeCT(java.lang.String) 

    /**
     * Method setDebitBalanceAmountSets the value of field
     * 'debitBalanceAmount'.
     * 
     * @param debitBalanceAmount the value of field
     * 'debitBalanceAmount'.
     */
    public void setDebitBalanceAmount(java.math.BigDecimal debitBalanceAmount)
    {
        this._debitBalanceAmount = debitBalanceAmount;
        
        super.setVoChanged(true);
    } //-- void setDebitBalanceAmount(java.math.BigDecimal) 

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
     * Method setExpenseAmountSets the value of field
     * 'expenseAmount'.
     * 
     * @param expenseAmount the value of field 'expenseAmount'.
     */
    public void setExpenseAmount(java.math.BigDecimal expenseAmount)
    {
        this._expenseAmount = expenseAmount;
        
        super.setVoChanged(true);
    } //-- void setExpenseAmount(java.math.BigDecimal) 

    /**
     * Method setGroupTypeCTSets the value of field 'groupTypeCT'.
     * 
     * @param groupTypeCT the value of field 'groupTypeCT'.
     */
    public void setGroupTypeCT(java.lang.String groupTypeCT)
    {
        this._groupTypeCT = groupTypeCT;
        
        super.setVoChanged(true);
    } //-- void setGroupTypeCT(java.lang.String) 

    /**
     * Method setIncludedInDebitBalIndSets the value of field
     * 'includedInDebitBalInd'.
     * 
     * @param includedInDebitBalInd the value of field
     * 'includedInDebitBalInd'.
     */
    public void setIncludedInDebitBalInd(java.lang.String includedInDebitBalInd)
    {
        this._includedInDebitBalInd = includedInDebitBalInd;
        
        super.setVoChanged(true);
    } //-- void setIncludedInDebitBalInd(java.lang.String) 

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
     * Method setPlacedAgentFKSets the value of field
     * 'placedAgentFK'.
     * 
     * @param placedAgentFK the value of field 'placedAgentFK'.
     */
    public void setPlacedAgentFK(long placedAgentFK)
    {
        this._placedAgentFK = placedAgentFK;
        
        super.setVoChanged(true);
        this._has_placedAgentFK = true;
    } //-- void setPlacedAgentFK(long) 

    /**
     * Method setReduceTaxableSets the value of field
     * 'reduceTaxable'.
     * 
     * @param reduceTaxable the value of field 'reduceTaxable'.
     */
    public void setReduceTaxable(java.lang.String reduceTaxable)
    {
        this._reduceTaxable = reduceTaxable;
        
        super.setVoChanged(true);
    } //-- void setReduceTaxable(java.lang.String) 

    /**
     * Method setSourcePlacedAgentFKSets the value of field
     * 'sourcePlacedAgentFK'.
     * 
     * @param sourcePlacedAgentFK the value of field
     * 'sourcePlacedAgentFK'.
     */
    public void setSourcePlacedAgentFK(long sourcePlacedAgentFK)
    {
        this._sourcePlacedAgentFK = sourcePlacedAgentFK;
        
        super.setVoChanged(true);
        this._has_sourcePlacedAgentFK = true;
    } //-- void setSourcePlacedAgentFK(long) 

    /**
     * Method setStatementIndSets the value of field
     * 'statementInd'.
     * 
     * @param statementInd the value of field 'statementInd'.
     */
    public void setStatementInd(java.lang.String statementInd)
    {
        this._statementInd = statementInd;
        
        super.setVoChanged(true);
    } //-- void setStatementInd(java.lang.String) 

    /**
     * Method setTaxRoleToFKSets the value of field 'taxRoleToFK'.
     * 
     * @param taxRoleToFK the value of field 'taxRoleToFK'.
     */
    public void setTaxRoleToFK(long taxRoleToFK)
    {
        this._taxRoleToFK = taxRoleToFK;
        
        super.setVoChanged(true);
        this._has_taxRoleToFK = true;
    } //-- void setTaxRoleToFK(long) 

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
    public static edit.common.vo.CommissionHistoryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CommissionHistoryVO) Unmarshaller.unmarshal(edit.common.vo.CommissionHistoryVO.class, reader);
    } //-- edit.common.vo.CommissionHistoryVO unmarshal(java.io.Reader) 

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
