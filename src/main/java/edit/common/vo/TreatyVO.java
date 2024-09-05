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
 * Class TreatyVO.
 * 
 * @version $Revision$ $Date$
 */
public class TreatyVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _treatyPK
     */
    private long _treatyPK;

    /**
     * keeps track of state for field: _treatyPK
     */
    private boolean _has_treatyPK;

    /**
     * Field _treatyGroupFK
     */
    private long _treatyGroupFK;

    /**
     * keeps track of state for field: _treatyGroupFK
     */
    private boolean _has_treatyGroupFK;

    /**
     * Field _reinsurerFK
     */
    private long _reinsurerFK;

    /**
     * keeps track of state for field: _reinsurerFK
     */
    private boolean _has_reinsurerFK;

    /**
     * Field _startDate
     */
    private java.lang.String _startDate;

    /**
     * Field _stopDate
     */
    private java.lang.String _stopDate;

    /**
     * Field _settlementPeriod
     */
    private int _settlementPeriod;

    /**
     * keeps track of state for field: _settlementPeriod
     */
    private boolean _has_settlementPeriod;

    /**
     * Field _paymentModeCT
     */
    private java.lang.String _paymentModeCT;

    /**
     * Field _calculationModeCT
     */
    private java.lang.String _calculationModeCT;

    /**
     * Field _retentionAmount
     */
    private java.math.BigDecimal _retentionAmount;

    /**
     * Field _lastCheckDate
     */
    private java.lang.String _lastCheckDate;

    /**
     * Field _poolPercentage
     */
    private java.math.BigDecimal _poolPercentage;

    /**
     * Field _reinsurerBalance
     */
    private java.math.BigDecimal _reinsurerBalance;

    /**
     * Field _status
     */
    private java.lang.String _status;

    /**
     * Field _clientSetupVOList
     */
    private java.util.Vector _clientSetupVOList;

    /**
     * Field _contractTreatyVOList
     */
    private java.util.Vector _contractTreatyVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public TreatyVO() {
        super();
        _clientSetupVOList = new Vector();
        _contractTreatyVOList = new Vector();
    } //-- edit.common.vo.TreatyVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addClientSetupVO
     * 
     * @param vClientSetupVO
     */
    public void addClientSetupVO(edit.common.vo.ClientSetupVO vClientSetupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientSetupVO.setParentVO(this.getClass(), this);
        _clientSetupVOList.addElement(vClientSetupVO);
    } //-- void addClientSetupVO(edit.common.vo.ClientSetupVO) 

    /**
     * Method addClientSetupVO
     * 
     * @param index
     * @param vClientSetupVO
     */
    public void addClientSetupVO(int index, edit.common.vo.ClientSetupVO vClientSetupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientSetupVO.setParentVO(this.getClass(), this);
        _clientSetupVOList.insertElementAt(vClientSetupVO, index);
    } //-- void addClientSetupVO(int, edit.common.vo.ClientSetupVO) 

    /**
     * Method addContractTreatyVO
     * 
     * @param vContractTreatyVO
     */
    public void addContractTreatyVO(edit.common.vo.ContractTreatyVO vContractTreatyVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractTreatyVO.setParentVO(this.getClass(), this);
        _contractTreatyVOList.addElement(vContractTreatyVO);
    } //-- void addContractTreatyVO(edit.common.vo.ContractTreatyVO) 

    /**
     * Method addContractTreatyVO
     * 
     * @param index
     * @param vContractTreatyVO
     */
    public void addContractTreatyVO(int index, edit.common.vo.ContractTreatyVO vContractTreatyVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractTreatyVO.setParentVO(this.getClass(), this);
        _contractTreatyVOList.insertElementAt(vContractTreatyVO, index);
    } //-- void addContractTreatyVO(int, edit.common.vo.ContractTreatyVO) 

    /**
     * Method enumerateClientSetupVO
     */
    public java.util.Enumeration enumerateClientSetupVO()
    {
        return _clientSetupVOList.elements();
    } //-- java.util.Enumeration enumerateClientSetupVO() 

    /**
     * Method enumerateContractTreatyVO
     */
    public java.util.Enumeration enumerateContractTreatyVO()
    {
        return _contractTreatyVOList.elements();
    } //-- java.util.Enumeration enumerateContractTreatyVO() 

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
        
        if (obj instanceof TreatyVO) {
        
            TreatyVO temp = (TreatyVO)obj;
            if (this._treatyPK != temp._treatyPK)
                return false;
            if (this._has_treatyPK != temp._has_treatyPK)
                return false;
            if (this._treatyGroupFK != temp._treatyGroupFK)
                return false;
            if (this._has_treatyGroupFK != temp._has_treatyGroupFK)
                return false;
            if (this._reinsurerFK != temp._reinsurerFK)
                return false;
            if (this._has_reinsurerFK != temp._has_reinsurerFK)
                return false;
            if (this._startDate != null) {
                if (temp._startDate == null) return false;
                else if (!(this._startDate.equals(temp._startDate))) 
                    return false;
            }
            else if (temp._startDate != null)
                return false;
            if (this._stopDate != null) {
                if (temp._stopDate == null) return false;
                else if (!(this._stopDate.equals(temp._stopDate))) 
                    return false;
            }
            else if (temp._stopDate != null)
                return false;
            if (this._settlementPeriod != temp._settlementPeriod)
                return false;
            if (this._has_settlementPeriod != temp._has_settlementPeriod)
                return false;
            if (this._paymentModeCT != null) {
                if (temp._paymentModeCT == null) return false;
                else if (!(this._paymentModeCT.equals(temp._paymentModeCT))) 
                    return false;
            }
            else if (temp._paymentModeCT != null)
                return false;
            if (this._calculationModeCT != null) {
                if (temp._calculationModeCT == null) return false;
                else if (!(this._calculationModeCT.equals(temp._calculationModeCT))) 
                    return false;
            }
            else if (temp._calculationModeCT != null)
                return false;
            if (this._retentionAmount != null) {
                if (temp._retentionAmount == null) return false;
                else if (!(this._retentionAmount.equals(temp._retentionAmount))) 
                    return false;
            }
            else if (temp._retentionAmount != null)
                return false;
            if (this._lastCheckDate != null) {
                if (temp._lastCheckDate == null) return false;
                else if (!(this._lastCheckDate.equals(temp._lastCheckDate))) 
                    return false;
            }
            else if (temp._lastCheckDate != null)
                return false;
            if (this._poolPercentage != null) {
                if (temp._poolPercentage == null) return false;
                else if (!(this._poolPercentage.equals(temp._poolPercentage))) 
                    return false;
            }
            else if (temp._poolPercentage != null)
                return false;
            if (this._reinsurerBalance != null) {
                if (temp._reinsurerBalance == null) return false;
                else if (!(this._reinsurerBalance.equals(temp._reinsurerBalance))) 
                    return false;
            }
            else if (temp._reinsurerBalance != null)
                return false;
            if (this._status != null) {
                if (temp._status == null) return false;
                else if (!(this._status.equals(temp._status))) 
                    return false;
            }
            else if (temp._status != null)
                return false;
            if (this._clientSetupVOList != null) {
                if (temp._clientSetupVOList == null) return false;
                else if (!(this._clientSetupVOList.equals(temp._clientSetupVOList))) 
                    return false;
            }
            else if (temp._clientSetupVOList != null)
                return false;
            if (this._contractTreatyVOList != null) {
                if (temp._contractTreatyVOList == null) return false;
                else if (!(this._contractTreatyVOList.equals(temp._contractTreatyVOList))) 
                    return false;
            }
            else if (temp._contractTreatyVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCalculationModeCTReturns the value of field
     * 'calculationModeCT'.
     * 
     * @return the value of field 'calculationModeCT'.
     */
    public java.lang.String getCalculationModeCT()
    {
        return this._calculationModeCT;
    } //-- java.lang.String getCalculationModeCT() 

    /**
     * Method getClientSetupVO
     * 
     * @param index
     */
    public edit.common.vo.ClientSetupVO getClientSetupVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientSetupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ClientSetupVO) _clientSetupVOList.elementAt(index);
    } //-- edit.common.vo.ClientSetupVO getClientSetupVO(int) 

    /**
     * Method getClientSetupVO
     */
    public edit.common.vo.ClientSetupVO[] getClientSetupVO()
    {
        int size = _clientSetupVOList.size();
        edit.common.vo.ClientSetupVO[] mArray = new edit.common.vo.ClientSetupVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ClientSetupVO) _clientSetupVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ClientSetupVO[] getClientSetupVO() 

    /**
     * Method getClientSetupVOCount
     */
    public int getClientSetupVOCount()
    {
        return _clientSetupVOList.size();
    } //-- int getClientSetupVOCount() 

    /**
     * Method getContractTreatyVO
     * 
     * @param index
     */
    public edit.common.vo.ContractTreatyVO getContractTreatyVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractTreatyVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ContractTreatyVO) _contractTreatyVOList.elementAt(index);
    } //-- edit.common.vo.ContractTreatyVO getContractTreatyVO(int) 

    /**
     * Method getContractTreatyVO
     */
    public edit.common.vo.ContractTreatyVO[] getContractTreatyVO()
    {
        int size = _contractTreatyVOList.size();
        edit.common.vo.ContractTreatyVO[] mArray = new edit.common.vo.ContractTreatyVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ContractTreatyVO) _contractTreatyVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ContractTreatyVO[] getContractTreatyVO() 

    /**
     * Method getContractTreatyVOCount
     */
    public int getContractTreatyVOCount()
    {
        return _contractTreatyVOList.size();
    } //-- int getContractTreatyVOCount() 

    /**
     * Method getLastCheckDateReturns the value of field
     * 'lastCheckDate'.
     * 
     * @return the value of field 'lastCheckDate'.
     */
    public java.lang.String getLastCheckDate()
    {
        return this._lastCheckDate;
    } //-- java.lang.String getLastCheckDate() 

    /**
     * Method getPaymentModeCTReturns the value of field
     * 'paymentModeCT'.
     * 
     * @return the value of field 'paymentModeCT'.
     */
    public java.lang.String getPaymentModeCT()
    {
        return this._paymentModeCT;
    } //-- java.lang.String getPaymentModeCT() 

    /**
     * Method getPoolPercentageReturns the value of field
     * 'poolPercentage'.
     * 
     * @return the value of field 'poolPercentage'.
     */
    public java.math.BigDecimal getPoolPercentage()
    {
        return this._poolPercentage;
    } //-- java.math.BigDecimal getPoolPercentage() 

    /**
     * Method getReinsurerBalanceReturns the value of field
     * 'reinsurerBalance'.
     * 
     * @return the value of field 'reinsurerBalance'.
     */
    public java.math.BigDecimal getReinsurerBalance()
    {
        return this._reinsurerBalance;
    } //-- java.math.BigDecimal getReinsurerBalance() 

    /**
     * Method getReinsurerFKReturns the value of field
     * 'reinsurerFK'.
     * 
     * @return the value of field 'reinsurerFK'.
     */
    public long getReinsurerFK()
    {
        return this._reinsurerFK;
    } //-- long getReinsurerFK() 

    /**
     * Method getRetentionAmountReturns the value of field
     * 'retentionAmount'.
     * 
     * @return the value of field 'retentionAmount'.
     */
    public java.math.BigDecimal getRetentionAmount()
    {
        return this._retentionAmount;
    } //-- java.math.BigDecimal getRetentionAmount() 

    /**
     * Method getSettlementPeriodReturns the value of field
     * 'settlementPeriod'.
     * 
     * @return the value of field 'settlementPeriod'.
     */
    public int getSettlementPeriod()
    {
        return this._settlementPeriod;
    } //-- int getSettlementPeriod() 

    /**
     * Method getStartDateReturns the value of field 'startDate'.
     * 
     * @return the value of field 'startDate'.
     */
    public java.lang.String getStartDate()
    {
        return this._startDate;
    } //-- java.lang.String getStartDate() 

    /**
     * Method getStatusReturns the value of field 'status'.
     * 
     * @return the value of field 'status'.
     */
    public java.lang.String getStatus()
    {
        return this._status;
    } //-- java.lang.String getStatus() 

    /**
     * Method getStopDateReturns the value of field 'stopDate'.
     * 
     * @return the value of field 'stopDate'.
     */
    public java.lang.String getStopDate()
    {
        return this._stopDate;
    } //-- java.lang.String getStopDate() 

    /**
     * Method getTreatyGroupFKReturns the value of field
     * 'treatyGroupFK'.
     * 
     * @return the value of field 'treatyGroupFK'.
     */
    public long getTreatyGroupFK()
    {
        return this._treatyGroupFK;
    } //-- long getTreatyGroupFK() 

    /**
     * Method getTreatyPKReturns the value of field 'treatyPK'.
     * 
     * @return the value of field 'treatyPK'.
     */
    public long getTreatyPK()
    {
        return this._treatyPK;
    } //-- long getTreatyPK() 

    /**
     * Method hasReinsurerFK
     */
    public boolean hasReinsurerFK()
    {
        return this._has_reinsurerFK;
    } //-- boolean hasReinsurerFK() 

    /**
     * Method hasSettlementPeriod
     */
    public boolean hasSettlementPeriod()
    {
        return this._has_settlementPeriod;
    } //-- boolean hasSettlementPeriod() 

    /**
     * Method hasTreatyGroupFK
     */
    public boolean hasTreatyGroupFK()
    {
        return this._has_treatyGroupFK;
    } //-- boolean hasTreatyGroupFK() 

    /**
     * Method hasTreatyPK
     */
    public boolean hasTreatyPK()
    {
        return this._has_treatyPK;
    } //-- boolean hasTreatyPK() 

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
     * Method removeAllClientSetupVO
     */
    public void removeAllClientSetupVO()
    {
        _clientSetupVOList.removeAllElements();
    } //-- void removeAllClientSetupVO() 

    /**
     * Method removeAllContractTreatyVO
     */
    public void removeAllContractTreatyVO()
    {
        _contractTreatyVOList.removeAllElements();
    } //-- void removeAllContractTreatyVO() 

    /**
     * Method removeClientSetupVO
     * 
     * @param index
     */
    public edit.common.vo.ClientSetupVO removeClientSetupVO(int index)
    {
        java.lang.Object obj = _clientSetupVOList.elementAt(index);
        _clientSetupVOList.removeElementAt(index);
        return (edit.common.vo.ClientSetupVO) obj;
    } //-- edit.common.vo.ClientSetupVO removeClientSetupVO(int) 

    /**
     * Method removeContractTreatyVO
     * 
     * @param index
     */
    public edit.common.vo.ContractTreatyVO removeContractTreatyVO(int index)
    {
        java.lang.Object obj = _contractTreatyVOList.elementAt(index);
        _contractTreatyVOList.removeElementAt(index);
        return (edit.common.vo.ContractTreatyVO) obj;
    } //-- edit.common.vo.ContractTreatyVO removeContractTreatyVO(int) 

    /**
     * Method setCalculationModeCTSets the value of field
     * 'calculationModeCT'.
     * 
     * @param calculationModeCT the value of field
     * 'calculationModeCT'.
     */
    public void setCalculationModeCT(java.lang.String calculationModeCT)
    {
        this._calculationModeCT = calculationModeCT;
        
        super.setVoChanged(true);
    } //-- void setCalculationModeCT(java.lang.String) 

    /**
     * Method setClientSetupVO
     * 
     * @param index
     * @param vClientSetupVO
     */
    public void setClientSetupVO(int index, edit.common.vo.ClientSetupVO vClientSetupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientSetupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vClientSetupVO.setParentVO(this.getClass(), this);
        _clientSetupVOList.setElementAt(vClientSetupVO, index);
    } //-- void setClientSetupVO(int, edit.common.vo.ClientSetupVO) 

    /**
     * Method setClientSetupVO
     * 
     * @param clientSetupVOArray
     */
    public void setClientSetupVO(edit.common.vo.ClientSetupVO[] clientSetupVOArray)
    {
        //-- copy array
        _clientSetupVOList.removeAllElements();
        for (int i = 0; i < clientSetupVOArray.length; i++) {
            clientSetupVOArray[i].setParentVO(this.getClass(), this);
            _clientSetupVOList.addElement(clientSetupVOArray[i]);
        }
    } //-- void setClientSetupVO(edit.common.vo.ClientSetupVO) 

    /**
     * Method setContractTreatyVO
     * 
     * @param index
     * @param vContractTreatyVO
     */
    public void setContractTreatyVO(int index, edit.common.vo.ContractTreatyVO vContractTreatyVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractTreatyVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vContractTreatyVO.setParentVO(this.getClass(), this);
        _contractTreatyVOList.setElementAt(vContractTreatyVO, index);
    } //-- void setContractTreatyVO(int, edit.common.vo.ContractTreatyVO) 

    /**
     * Method setContractTreatyVO
     * 
     * @param contractTreatyVOArray
     */
    public void setContractTreatyVO(edit.common.vo.ContractTreatyVO[] contractTreatyVOArray)
    {
        //-- copy array
        _contractTreatyVOList.removeAllElements();
        for (int i = 0; i < contractTreatyVOArray.length; i++) {
            contractTreatyVOArray[i].setParentVO(this.getClass(), this);
            _contractTreatyVOList.addElement(contractTreatyVOArray[i]);
        }
    } //-- void setContractTreatyVO(edit.common.vo.ContractTreatyVO) 

    /**
     * Method setLastCheckDateSets the value of field
     * 'lastCheckDate'.
     * 
     * @param lastCheckDate the value of field 'lastCheckDate'.
     */
    public void setLastCheckDate(java.lang.String lastCheckDate)
    {
        this._lastCheckDate = lastCheckDate;
        
        super.setVoChanged(true);
    } //-- void setLastCheckDate(java.lang.String) 

    /**
     * Method setPaymentModeCTSets the value of field
     * 'paymentModeCT'.
     * 
     * @param paymentModeCT the value of field 'paymentModeCT'.
     */
    public void setPaymentModeCT(java.lang.String paymentModeCT)
    {
        this._paymentModeCT = paymentModeCT;
        
        super.setVoChanged(true);
    } //-- void setPaymentModeCT(java.lang.String) 

    /**
     * Method setPoolPercentageSets the value of field
     * 'poolPercentage'.
     * 
     * @param poolPercentage the value of field 'poolPercentage'.
     */
    public void setPoolPercentage(java.math.BigDecimal poolPercentage)
    {
        this._poolPercentage = poolPercentage;
        
        super.setVoChanged(true);
    } //-- void setPoolPercentage(java.math.BigDecimal) 

    /**
     * Method setReinsurerBalanceSets the value of field
     * 'reinsurerBalance'.
     * 
     * @param reinsurerBalance the value of field 'reinsurerBalance'
     */
    public void setReinsurerBalance(java.math.BigDecimal reinsurerBalance)
    {
        this._reinsurerBalance = reinsurerBalance;
        
        super.setVoChanged(true);
    } //-- void setReinsurerBalance(java.math.BigDecimal) 

    /**
     * Method setReinsurerFKSets the value of field 'reinsurerFK'.
     * 
     * @param reinsurerFK the value of field 'reinsurerFK'.
     */
    public void setReinsurerFK(long reinsurerFK)
    {
        this._reinsurerFK = reinsurerFK;
        
        super.setVoChanged(true);
        this._has_reinsurerFK = true;
    } //-- void setReinsurerFK(long) 

    /**
     * Method setRetentionAmountSets the value of field
     * 'retentionAmount'.
     * 
     * @param retentionAmount the value of field 'retentionAmount'.
     */
    public void setRetentionAmount(java.math.BigDecimal retentionAmount)
    {
        this._retentionAmount = retentionAmount;
        
        super.setVoChanged(true);
    } //-- void setRetentionAmount(java.math.BigDecimal) 

    /**
     * Method setSettlementPeriodSets the value of field
     * 'settlementPeriod'.
     * 
     * @param settlementPeriod the value of field 'settlementPeriod'
     */
    public void setSettlementPeriod(int settlementPeriod)
    {
        this._settlementPeriod = settlementPeriod;
        
        super.setVoChanged(true);
        this._has_settlementPeriod = true;
    } //-- void setSettlementPeriod(int) 

    /**
     * Method setStartDateSets the value of field 'startDate'.
     * 
     * @param startDate the value of field 'startDate'.
     */
    public void setStartDate(java.lang.String startDate)
    {
        this._startDate = startDate;
        
        super.setVoChanged(true);
    } //-- void setStartDate(java.lang.String) 

    /**
     * Method setStatusSets the value of field 'status'.
     * 
     * @param status the value of field 'status'.
     */
    public void setStatus(java.lang.String status)
    {
        this._status = status;
        
        super.setVoChanged(true);
    } //-- void setStatus(java.lang.String) 

    /**
     * Method setStopDateSets the value of field 'stopDate'.
     * 
     * @param stopDate the value of field 'stopDate'.
     */
    public void setStopDate(java.lang.String stopDate)
    {
        this._stopDate = stopDate;
        
        super.setVoChanged(true);
    } //-- void setStopDate(java.lang.String) 

    /**
     * Method setTreatyGroupFKSets the value of field
     * 'treatyGroupFK'.
     * 
     * @param treatyGroupFK the value of field 'treatyGroupFK'.
     */
    public void setTreatyGroupFK(long treatyGroupFK)
    {
        this._treatyGroupFK = treatyGroupFK;
        
        super.setVoChanged(true);
        this._has_treatyGroupFK = true;
    } //-- void setTreatyGroupFK(long) 

    /**
     * Method setTreatyPKSets the value of field 'treatyPK'.
     * 
     * @param treatyPK the value of field 'treatyPK'.
     */
    public void setTreatyPK(long treatyPK)
    {
        this._treatyPK = treatyPK;
        
        super.setVoChanged(true);
        this._has_treatyPK = true;
    } //-- void setTreatyPK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.TreatyVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.TreatyVO) Unmarshaller.unmarshal(edit.common.vo.TreatyVO.class, reader);
    } //-- edit.common.vo.TreatyVO unmarshal(java.io.Reader) 

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
