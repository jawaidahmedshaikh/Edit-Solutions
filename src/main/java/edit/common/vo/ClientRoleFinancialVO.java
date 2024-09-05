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
 * Class ClientRoleFinancialVO.
 * 
 * @version $Revision$ $Date$
 */
public class ClientRoleFinancialVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _clientRoleFinancialPK
     */
    private long _clientRoleFinancialPK;

    /**
     * keeps track of state for field: _clientRoleFinancialPK
     */
    private boolean _has_clientRoleFinancialPK;

    /**
     * Field _clientRoleFK
     */
    private long _clientRoleFK;

    /**
     * keeps track of state for field: _clientRoleFK
     */
    private boolean _has_clientRoleFK;

    /**
     * Field _lastStatementDateTime
     */
    private java.lang.String _lastStatementDateTime;

    /**
     * Field _lastCheckDateTime
     */
    private java.lang.String _lastCheckDateTime;

    /**
     * Field _lastCheckAmount
     */
    private java.math.BigDecimal _lastCheckAmount;

    /**
     * Field _advanceAmt
     */
    private java.math.BigDecimal _advanceAmt;

    /**
     * Field _advRecoveredAmt
     */
    private java.math.BigDecimal _advRecoveredAmt;

    /**
     * Field _commBalance
     */
    private java.math.BigDecimal _commBalance;

    /**
     * Field _firstYearCum
     */
    private java.math.BigDecimal _firstYearCum;

    /**
     * Field _renewalCum
     */
    private java.math.BigDecimal _renewalCum;

    /**
     * Field _NYPrem
     */
    private java.math.BigDecimal _NYPrem;

    /**
     * Field _NYComm
     */
    private java.math.BigDecimal _NYComm;

    /**
     * Field _redirectBalance
     */
    private java.math.BigDecimal _redirectBalance;

    /**
     * Field _IDBAmount
     */
    private java.math.BigDecimal _IDBAmount;

    /**
     * Field _IDBLastValDate
     */
    private java.lang.String _IDBLastValDate;

    /**
     * Field _DBAmount
     */
    private java.math.BigDecimal _DBAmount;

    /**
     * Field _amountTaxableYTD
     */
    private java.math.BigDecimal _amountTaxableYTD;

    /**
     * Field _lastBonusCheckDateTime
     */
    private java.lang.String _lastBonusCheckDateTime;

    /**
     * Field _bonusBalance
     */
    private java.math.BigDecimal _bonusBalance;

    /**
     * Field _statementProducedInd
     */
    private java.lang.String _statementProducedInd;

    /**
     * Field _lastStatementAmount
     */
    private java.math.BigDecimal _lastStatementAmount;

    /**
     * Field _lifetimeCommBalance
     */
    private java.math.BigDecimal _lifetimeCommBalance;

    /**
     * Field _lifetimeAdvanceBalance
     */
    private java.math.BigDecimal _lifetimeAdvanceBalance;

    /**
     * Field _priorLastStatementDateTime
     */
    private java.lang.String _priorLastStatementDateTime;


      //----------------/
     //- Constructors -/
    //----------------/

    public ClientRoleFinancialVO() {
        super();
    } //-- edit.common.vo.ClientRoleFinancialVO()


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
        
        if (obj instanceof ClientRoleFinancialVO) {
        
            ClientRoleFinancialVO temp = (ClientRoleFinancialVO)obj;
            if (this._clientRoleFinancialPK != temp._clientRoleFinancialPK)
                return false;
            if (this._has_clientRoleFinancialPK != temp._has_clientRoleFinancialPK)
                return false;
            if (this._clientRoleFK != temp._clientRoleFK)
                return false;
            if (this._has_clientRoleFK != temp._has_clientRoleFK)
                return false;
            if (this._lastStatementDateTime != null) {
                if (temp._lastStatementDateTime == null) return false;
                else if (!(this._lastStatementDateTime.equals(temp._lastStatementDateTime))) 
                    return false;
            }
            else if (temp._lastStatementDateTime != null)
                return false;
            if (this._lastCheckDateTime != null) {
                if (temp._lastCheckDateTime == null) return false;
                else if (!(this._lastCheckDateTime.equals(temp._lastCheckDateTime))) 
                    return false;
            }
            else if (temp._lastCheckDateTime != null)
                return false;
            if (this._lastCheckAmount != null) {
                if (temp._lastCheckAmount == null) return false;
                else if (!(this._lastCheckAmount.equals(temp._lastCheckAmount))) 
                    return false;
            }
            else if (temp._lastCheckAmount != null)
                return false;
            if (this._advanceAmt != null) {
                if (temp._advanceAmt == null) return false;
                else if (!(this._advanceAmt.equals(temp._advanceAmt))) 
                    return false;
            }
            else if (temp._advanceAmt != null)
                return false;
            if (this._advRecoveredAmt != null) {
                if (temp._advRecoveredAmt == null) return false;
                else if (!(this._advRecoveredAmt.equals(temp._advRecoveredAmt))) 
                    return false;
            }
            else if (temp._advRecoveredAmt != null)
                return false;
            if (this._commBalance != null) {
                if (temp._commBalance == null) return false;
                else if (!(this._commBalance.equals(temp._commBalance))) 
                    return false;
            }
            else if (temp._commBalance != null)
                return false;
            if (this._firstYearCum != null) {
                if (temp._firstYearCum == null) return false;
                else if (!(this._firstYearCum.equals(temp._firstYearCum))) 
                    return false;
            }
            else if (temp._firstYearCum != null)
                return false;
            if (this._renewalCum != null) {
                if (temp._renewalCum == null) return false;
                else if (!(this._renewalCum.equals(temp._renewalCum))) 
                    return false;
            }
            else if (temp._renewalCum != null)
                return false;
            if (this._NYPrem != null) {
                if (temp._NYPrem == null) return false;
                else if (!(this._NYPrem.equals(temp._NYPrem))) 
                    return false;
            }
            else if (temp._NYPrem != null)
                return false;
            if (this._NYComm != null) {
                if (temp._NYComm == null) return false;
                else if (!(this._NYComm.equals(temp._NYComm))) 
                    return false;
            }
            else if (temp._NYComm != null)
                return false;
            if (this._redirectBalance != null) {
                if (temp._redirectBalance == null) return false;
                else if (!(this._redirectBalance.equals(temp._redirectBalance))) 
                    return false;
            }
            else if (temp._redirectBalance != null)
                return false;
            if (this._IDBAmount != null) {
                if (temp._IDBAmount == null) return false;
                else if (!(this._IDBAmount.equals(temp._IDBAmount))) 
                    return false;
            }
            else if (temp._IDBAmount != null)
                return false;
            if (this._IDBLastValDate != null) {
                if (temp._IDBLastValDate == null) return false;
                else if (!(this._IDBLastValDate.equals(temp._IDBLastValDate))) 
                    return false;
            }
            else if (temp._IDBLastValDate != null)
                return false;
            if (this._DBAmount != null) {
                if (temp._DBAmount == null) return false;
                else if (!(this._DBAmount.equals(temp._DBAmount))) 
                    return false;
            }
            else if (temp._DBAmount != null)
                return false;
            if (this._amountTaxableYTD != null) {
                if (temp._amountTaxableYTD == null) return false;
                else if (!(this._amountTaxableYTD.equals(temp._amountTaxableYTD))) 
                    return false;
            }
            else if (temp._amountTaxableYTD != null)
                return false;
            if (this._lastBonusCheckDateTime != null) {
                if (temp._lastBonusCheckDateTime == null) return false;
                else if (!(this._lastBonusCheckDateTime.equals(temp._lastBonusCheckDateTime))) 
                    return false;
            }
            else if (temp._lastBonusCheckDateTime != null)
                return false;
            if (this._bonusBalance != null) {
                if (temp._bonusBalance == null) return false;
                else if (!(this._bonusBalance.equals(temp._bonusBalance))) 
                    return false;
            }
            else if (temp._bonusBalance != null)
                return false;
            if (this._statementProducedInd != null) {
                if (temp._statementProducedInd == null) return false;
                else if (!(this._statementProducedInd.equals(temp._statementProducedInd))) 
                    return false;
            }
            else if (temp._statementProducedInd != null)
                return false;
            if (this._lastStatementAmount != null) {
                if (temp._lastStatementAmount == null) return false;
                else if (!(this._lastStatementAmount.equals(temp._lastStatementAmount))) 
                    return false;
            }
            else if (temp._lastStatementAmount != null)
                return false;
            if (this._lifetimeCommBalance != null) {
                if (temp._lifetimeCommBalance == null) return false;
                else if (!(this._lifetimeCommBalance.equals(temp._lifetimeCommBalance))) 
                    return false;
            }
            else if (temp._lifetimeCommBalance != null)
                return false;
            if (this._lifetimeAdvanceBalance != null) {
                if (temp._lifetimeAdvanceBalance == null) return false;
                else if (!(this._lifetimeAdvanceBalance.equals(temp._lifetimeAdvanceBalance))) 
                    return false;
            }
            else if (temp._lifetimeAdvanceBalance != null)
                return false;
            if (this._priorLastStatementDateTime != null) {
                if (temp._priorLastStatementDateTime == null) return false;
                else if (!(this._priorLastStatementDateTime.equals(temp._priorLastStatementDateTime))) 
                    return false;
            }
            else if (temp._priorLastStatementDateTime != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAdvRecoveredAmtReturns the value of field
     * 'advRecoveredAmt'.
     * 
     * @return the value of field 'advRecoveredAmt'.
     */
    public java.math.BigDecimal getAdvRecoveredAmt()
    {
        return this._advRecoveredAmt;
    } //-- java.math.BigDecimal getAdvRecoveredAmt() 

    /**
     * Method getAdvanceAmtReturns the value of field 'advanceAmt'.
     * 
     * @return the value of field 'advanceAmt'.
     */
    public java.math.BigDecimal getAdvanceAmt()
    {
        return this._advanceAmt;
    } //-- java.math.BigDecimal getAdvanceAmt() 

    /**
     * Method getAmountTaxableYTDReturns the value of field
     * 'amountTaxableYTD'.
     * 
     * @return the value of field 'amountTaxableYTD'.
     */
    public java.math.BigDecimal getAmountTaxableYTD()
    {
        return this._amountTaxableYTD;
    } //-- java.math.BigDecimal getAmountTaxableYTD() 

    /**
     * Method getBonusBalanceReturns the value of field
     * 'bonusBalance'.
     * 
     * @return the value of field 'bonusBalance'.
     */
    public java.math.BigDecimal getBonusBalance()
    {
        return this._bonusBalance;
    } //-- java.math.BigDecimal getBonusBalance() 

    /**
     * Method getClientRoleFKReturns the value of field
     * 'clientRoleFK'.
     * 
     * @return the value of field 'clientRoleFK'.
     */
    public long getClientRoleFK()
    {
        return this._clientRoleFK;
    } //-- long getClientRoleFK() 

    /**
     * Method getClientRoleFinancialPKReturns the value of field
     * 'clientRoleFinancialPK'.
     * 
     * @return the value of field 'clientRoleFinancialPK'.
     */
    public long getClientRoleFinancialPK()
    {
        return this._clientRoleFinancialPK;
    } //-- long getClientRoleFinancialPK() 

    /**
     * Method getCommBalanceReturns the value of field
     * 'commBalance'.
     * 
     * @return the value of field 'commBalance'.
     */
    public java.math.BigDecimal getCommBalance()
    {
        return this._commBalance;
    } //-- java.math.BigDecimal getCommBalance() 

    /**
     * Method getDBAmountReturns the value of field 'DBAmount'.
     * 
     * @return the value of field 'DBAmount'.
     */
    public java.math.BigDecimal getDBAmount()
    {
        return this._DBAmount;
    } //-- java.math.BigDecimal getDBAmount() 

    /**
     * Method getFirstYearCumReturns the value of field
     * 'firstYearCum'.
     * 
     * @return the value of field 'firstYearCum'.
     */
    public java.math.BigDecimal getFirstYearCum()
    {
        return this._firstYearCum;
    } //-- java.math.BigDecimal getFirstYearCum() 

    /**
     * Method getIDBAmountReturns the value of field 'IDBAmount'.
     * 
     * @return the value of field 'IDBAmount'.
     */
    public java.math.BigDecimal getIDBAmount()
    {
        return this._IDBAmount;
    } //-- java.math.BigDecimal getIDBAmount() 

    /**
     * Method getIDBLastValDateReturns the value of field
     * 'IDBLastValDate'.
     * 
     * @return the value of field 'IDBLastValDate'.
     */
    public java.lang.String getIDBLastValDate()
    {
        return this._IDBLastValDate;
    } //-- java.lang.String getIDBLastValDate() 

    /**
     * Method getLastBonusCheckDateTimeReturns the value of field
     * 'lastBonusCheckDateTime'.
     * 
     * @return the value of field 'lastBonusCheckDateTime'.
     */
    public java.lang.String getLastBonusCheckDateTime()
    {
        return this._lastBonusCheckDateTime;
    } //-- java.lang.String getLastBonusCheckDateTime() 

    /**
     * Method getLastCheckAmountReturns the value of field
     * 'lastCheckAmount'.
     * 
     * @return the value of field 'lastCheckAmount'.
     */
    public java.math.BigDecimal getLastCheckAmount()
    {
        return this._lastCheckAmount;
    } //-- java.math.BigDecimal getLastCheckAmount() 

    /**
     * Method getLastCheckDateTimeReturns the value of field
     * 'lastCheckDateTime'.
     * 
     * @return the value of field 'lastCheckDateTime'.
     */
    public java.lang.String getLastCheckDateTime()
    {
        return this._lastCheckDateTime;
    } //-- java.lang.String getLastCheckDateTime() 

    /**
     * Method getLastStatementAmountReturns the value of field
     * 'lastStatementAmount'.
     * 
     * @return the value of field 'lastStatementAmount'.
     */
    public java.math.BigDecimal getLastStatementAmount()
    {
        return this._lastStatementAmount;
    } //-- java.math.BigDecimal getLastStatementAmount() 

    /**
     * Method getLastStatementDateTimeReturns the value of field
     * 'lastStatementDateTime'.
     * 
     * @return the value of field 'lastStatementDateTime'.
     */
    public java.lang.String getLastStatementDateTime()
    {
        return this._lastStatementDateTime;
    } //-- java.lang.String getLastStatementDateTime() 

    /**
     * Method getLifetimeAdvanceBalanceReturns the value of field
     * 'lifetimeAdvanceBalance'.
     * 
     * @return the value of field 'lifetimeAdvanceBalance'.
     */
    public java.math.BigDecimal getLifetimeAdvanceBalance()
    {
        return this._lifetimeAdvanceBalance;
    } //-- java.math.BigDecimal getLifetimeAdvanceBalance() 

    /**
     * Method getLifetimeCommBalanceReturns the value of field
     * 'lifetimeCommBalance'.
     * 
     * @return the value of field 'lifetimeCommBalance'.
     */
    public java.math.BigDecimal getLifetimeCommBalance()
    {
        return this._lifetimeCommBalance;
    } //-- java.math.BigDecimal getLifetimeCommBalance() 

    /**
     * Method getNYCommReturns the value of field 'NYComm'.
     * 
     * @return the value of field 'NYComm'.
     */
    public java.math.BigDecimal getNYComm()
    {
        return this._NYComm;
    } //-- java.math.BigDecimal getNYComm() 

    /**
     * Method getNYPremReturns the value of field 'NYPrem'.
     * 
     * @return the value of field 'NYPrem'.
     */
    public java.math.BigDecimal getNYPrem()
    {
        return this._NYPrem;
    } //-- java.math.BigDecimal getNYPrem() 

    /**
     * Method getPriorLastStatementDateTimeReturns the value of
     * field 'priorLastStatementDateTime'.
     * 
     * @return the value of field 'priorLastStatementDateTime'.
     */
    public java.lang.String getPriorLastStatementDateTime()
    {
        return this._priorLastStatementDateTime;
    } //-- java.lang.String getPriorLastStatementDateTime() 

    /**
     * Method getRedirectBalanceReturns the value of field
     * 'redirectBalance'.
     * 
     * @return the value of field 'redirectBalance'.
     */
    public java.math.BigDecimal getRedirectBalance()
    {
        return this._redirectBalance;
    } //-- java.math.BigDecimal getRedirectBalance() 

    /**
     * Method getRenewalCumReturns the value of field 'renewalCum'.
     * 
     * @return the value of field 'renewalCum'.
     */
    public java.math.BigDecimal getRenewalCum()
    {
        return this._renewalCum;
    } //-- java.math.BigDecimal getRenewalCum() 

    /**
     * Method getStatementProducedIndReturns the value of field
     * 'statementProducedInd'.
     * 
     * @return the value of field 'statementProducedInd'.
     */
    public java.lang.String getStatementProducedInd()
    {
        return this._statementProducedInd;
    } //-- java.lang.String getStatementProducedInd() 

    /**
     * Method hasClientRoleFK
     */
    public boolean hasClientRoleFK()
    {
        return this._has_clientRoleFK;
    } //-- boolean hasClientRoleFK() 

    /**
     * Method hasClientRoleFinancialPK
     */
    public boolean hasClientRoleFinancialPK()
    {
        return this._has_clientRoleFinancialPK;
    } //-- boolean hasClientRoleFinancialPK() 

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
     * Method setAdvRecoveredAmtSets the value of field
     * 'advRecoveredAmt'.
     * 
     * @param advRecoveredAmt the value of field 'advRecoveredAmt'.
     */
    public void setAdvRecoveredAmt(java.math.BigDecimal advRecoveredAmt)
    {
        this._advRecoveredAmt = advRecoveredAmt;
        
        super.setVoChanged(true);
    } //-- void setAdvRecoveredAmt(java.math.BigDecimal) 

    /**
     * Method setAdvanceAmtSets the value of field 'advanceAmt'.
     * 
     * @param advanceAmt the value of field 'advanceAmt'.
     */
    public void setAdvanceAmt(java.math.BigDecimal advanceAmt)
    {
        this._advanceAmt = advanceAmt;
        
        super.setVoChanged(true);
    } //-- void setAdvanceAmt(java.math.BigDecimal) 

    /**
     * Method setAmountTaxableYTDSets the value of field
     * 'amountTaxableYTD'.
     * 
     * @param amountTaxableYTD the value of field 'amountTaxableYTD'
     */
    public void setAmountTaxableYTD(java.math.BigDecimal amountTaxableYTD)
    {
        this._amountTaxableYTD = amountTaxableYTD;
        
        super.setVoChanged(true);
    } //-- void setAmountTaxableYTD(java.math.BigDecimal) 

    /**
     * Method setBonusBalanceSets the value of field
     * 'bonusBalance'.
     * 
     * @param bonusBalance the value of field 'bonusBalance'.
     */
    public void setBonusBalance(java.math.BigDecimal bonusBalance)
    {
        this._bonusBalance = bonusBalance;
        
        super.setVoChanged(true);
    } //-- void setBonusBalance(java.math.BigDecimal) 

    /**
     * Method setClientRoleFKSets the value of field
     * 'clientRoleFK'.
     * 
     * @param clientRoleFK the value of field 'clientRoleFK'.
     */
    public void setClientRoleFK(long clientRoleFK)
    {
        this._clientRoleFK = clientRoleFK;
        
        super.setVoChanged(true);
        this._has_clientRoleFK = true;
    } //-- void setClientRoleFK(long) 

    /**
     * Method setClientRoleFinancialPKSets the value of field
     * 'clientRoleFinancialPK'.
     * 
     * @param clientRoleFinancialPK the value of field
     * 'clientRoleFinancialPK'.
     */
    public void setClientRoleFinancialPK(long clientRoleFinancialPK)
    {
        this._clientRoleFinancialPK = clientRoleFinancialPK;
        
        super.setVoChanged(true);
        this._has_clientRoleFinancialPK = true;
    } //-- void setClientRoleFinancialPK(long) 

    /**
     * Method setCommBalanceSets the value of field 'commBalance'.
     * 
     * @param commBalance the value of field 'commBalance'.
     */
    public void setCommBalance(java.math.BigDecimal commBalance)
    {
        this._commBalance = commBalance;
        
        super.setVoChanged(true);
    } //-- void setCommBalance(java.math.BigDecimal) 

    /**
     * Method setDBAmountSets the value of field 'DBAmount'.
     * 
     * @param DBAmount the value of field 'DBAmount'.
     */
    public void setDBAmount(java.math.BigDecimal DBAmount)
    {
        this._DBAmount = DBAmount;
        
        super.setVoChanged(true);
    } //-- void setDBAmount(java.math.BigDecimal) 

    /**
     * Method setFirstYearCumSets the value of field
     * 'firstYearCum'.
     * 
     * @param firstYearCum the value of field 'firstYearCum'.
     */
    public void setFirstYearCum(java.math.BigDecimal firstYearCum)
    {
        this._firstYearCum = firstYearCum;
        
        super.setVoChanged(true);
    } //-- void setFirstYearCum(java.math.BigDecimal) 

    /**
     * Method setIDBAmountSets the value of field 'IDBAmount'.
     * 
     * @param IDBAmount the value of field 'IDBAmount'.
     */
    public void setIDBAmount(java.math.BigDecimal IDBAmount)
    {
        this._IDBAmount = IDBAmount;
        
        super.setVoChanged(true);
    } //-- void setIDBAmount(java.math.BigDecimal) 

    /**
     * Method setIDBLastValDateSets the value of field
     * 'IDBLastValDate'.
     * 
     * @param IDBLastValDate the value of field 'IDBLastValDate'.
     */
    public void setIDBLastValDate(java.lang.String IDBLastValDate)
    {
        this._IDBLastValDate = IDBLastValDate;
        
        super.setVoChanged(true);
    } //-- void setIDBLastValDate(java.lang.String) 

    /**
     * Method setLastBonusCheckDateTimeSets the value of field
     * 'lastBonusCheckDateTime'.
     * 
     * @param lastBonusCheckDateTime the value of field
     * 'lastBonusCheckDateTime'.
     */
    public void setLastBonusCheckDateTime(java.lang.String lastBonusCheckDateTime)
    {
        this._lastBonusCheckDateTime = lastBonusCheckDateTime;
        
        super.setVoChanged(true);
    } //-- void setLastBonusCheckDateTime(java.lang.String) 

    /**
     * Method setLastCheckAmountSets the value of field
     * 'lastCheckAmount'.
     * 
     * @param lastCheckAmount the value of field 'lastCheckAmount'.
     */
    public void setLastCheckAmount(java.math.BigDecimal lastCheckAmount)
    {
        this._lastCheckAmount = lastCheckAmount;
        
        super.setVoChanged(true);
    } //-- void setLastCheckAmount(java.math.BigDecimal) 

    /**
     * Method setLastCheckDateTimeSets the value of field
     * 'lastCheckDateTime'.
     * 
     * @param lastCheckDateTime the value of field
     * 'lastCheckDateTime'.
     */
    public void setLastCheckDateTime(java.lang.String lastCheckDateTime)
    {
        this._lastCheckDateTime = lastCheckDateTime;
        
        super.setVoChanged(true);
    } //-- void setLastCheckDateTime(java.lang.String) 

    /**
     * Method setLastStatementAmountSets the value of field
     * 'lastStatementAmount'.
     * 
     * @param lastStatementAmount the value of field
     * 'lastStatementAmount'.
     */
    public void setLastStatementAmount(java.math.BigDecimal lastStatementAmount)
    {
        this._lastStatementAmount = lastStatementAmount;
        
        super.setVoChanged(true);
    } //-- void setLastStatementAmount(java.math.BigDecimal) 

    /**
     * Method setLastStatementDateTimeSets the value of field
     * 'lastStatementDateTime'.
     * 
     * @param lastStatementDateTime the value of field
     * 'lastStatementDateTime'.
     */
    public void setLastStatementDateTime(java.lang.String lastStatementDateTime)
    {
        this._lastStatementDateTime = lastStatementDateTime;
        
        super.setVoChanged(true);
    } //-- void setLastStatementDateTime(java.lang.String) 

    /**
     * Method setLifetimeAdvanceBalanceSets the value of field
     * 'lifetimeAdvanceBalance'.
     * 
     * @param lifetimeAdvanceBalance the value of field
     * 'lifetimeAdvanceBalance'.
     */
    public void setLifetimeAdvanceBalance(java.math.BigDecimal lifetimeAdvanceBalance)
    {
        this._lifetimeAdvanceBalance = lifetimeAdvanceBalance;
        
        super.setVoChanged(true);
    } //-- void setLifetimeAdvanceBalance(java.math.BigDecimal) 

    /**
     * Method setLifetimeCommBalanceSets the value of field
     * 'lifetimeCommBalance'.
     * 
     * @param lifetimeCommBalance the value of field
     * 'lifetimeCommBalance'.
     */
    public void setLifetimeCommBalance(java.math.BigDecimal lifetimeCommBalance)
    {
        this._lifetimeCommBalance = lifetimeCommBalance;
        
        super.setVoChanged(true);
    } //-- void setLifetimeCommBalance(java.math.BigDecimal) 

    /**
     * Method setNYCommSets the value of field 'NYComm'.
     * 
     * @param NYComm the value of field 'NYComm'.
     */
    public void setNYComm(java.math.BigDecimal NYComm)
    {
        this._NYComm = NYComm;
        
        super.setVoChanged(true);
    } //-- void setNYComm(java.math.BigDecimal) 

    /**
     * Method setNYPremSets the value of field 'NYPrem'.
     * 
     * @param NYPrem the value of field 'NYPrem'.
     */
    public void setNYPrem(java.math.BigDecimal NYPrem)
    {
        this._NYPrem = NYPrem;
        
        super.setVoChanged(true);
    } //-- void setNYPrem(java.math.BigDecimal) 

    /**
     * Method setPriorLastStatementDateTimeSets the value of field
     * 'priorLastStatementDateTime'.
     * 
     * @param priorLastStatementDateTime the value of field
     * 'priorLastStatementDateTime'.
     */
    public void setPriorLastStatementDateTime(java.lang.String priorLastStatementDateTime)
    {
        this._priorLastStatementDateTime = priorLastStatementDateTime;
        
        super.setVoChanged(true);
    } //-- void setPriorLastStatementDateTime(java.lang.String) 

    /**
     * Method setRedirectBalanceSets the value of field
     * 'redirectBalance'.
     * 
     * @param redirectBalance the value of field 'redirectBalance'.
     */
    public void setRedirectBalance(java.math.BigDecimal redirectBalance)
    {
        this._redirectBalance = redirectBalance;
        
        super.setVoChanged(true);
    } //-- void setRedirectBalance(java.math.BigDecimal) 

    /**
     * Method setRenewalCumSets the value of field 'renewalCum'.
     * 
     * @param renewalCum the value of field 'renewalCum'.
     */
    public void setRenewalCum(java.math.BigDecimal renewalCum)
    {
        this._renewalCum = renewalCum;
        
        super.setVoChanged(true);
    } //-- void setRenewalCum(java.math.BigDecimal) 

    /**
     * Method setStatementProducedIndSets the value of field
     * 'statementProducedInd'.
     * 
     * @param statementProducedInd the value of field
     * 'statementProducedInd'.
     */
    public void setStatementProducedInd(java.lang.String statementProducedInd)
    {
        this._statementProducedInd = statementProducedInd;
        
        super.setVoChanged(true);
    } //-- void setStatementProducedInd(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ClientRoleFinancialVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ClientRoleFinancialVO) Unmarshaller.unmarshal(edit.common.vo.ClientRoleFinancialVO.class, reader);
    } //-- edit.common.vo.ClientRoleFinancialVO unmarshal(java.io.Reader) 

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
