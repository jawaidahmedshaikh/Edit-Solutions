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
 * Class ContractSetupVO.
 * 
 * @version $Revision$ $Date$
 */
public class ContractSetupVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _contractSetupPK
     */
    private long _contractSetupPK;

    /**
     * keeps track of state for field: _contractSetupPK
     */
    private boolean _has_contractSetupPK;

    /**
     * Field _groupSetupFK
     */
    private long _groupSetupFK;

    /**
     * keeps track of state for field: _groupSetupFK
     */
    private boolean _has_groupSetupFK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _policyAmount
     */
    private java.math.BigDecimal _policyAmount;

    /**
     * Field _deathStatusCT
     */
    private java.lang.String _deathStatusCT;

    /**
     * Field _costBasis
     */
    private java.math.BigDecimal _costBasis;

    /**
     * Field _amountReceived
     */
    private java.math.BigDecimal _amountReceived;

    /**
     * Field _claimStatusCT
     */
    private java.lang.String _claimStatusCT;

    /**
     * Field _complexChangeTypeCT
     */
    private java.lang.String _complexChangeTypeCT;

    /**
     * Field _complexChangeNewValue
     */
    private java.lang.String _complexChangeNewValue;

    /**
     * Field _suppressDecreaseFaceInd
     */
    private java.lang.String _suppressDecreaseFaceInd;

    /**
     * Field _userInvestmentOverrideInd
     */
    private java.lang.String _userInvestmentOverrideInd;

    /**
     * Field _dateOfDeath
     */
    private java.lang.String _dateOfDeath;

    /**
     * Field _careTypeCT
     */
    private java.lang.String _careTypeCT;

    /**
     * Field _conditionCT
     */
    private java.lang.String _conditionCT;

    /**
     * Field _outSuspenseVOList
     */
    private java.util.Vector _outSuspenseVOList;

    /**
     * Field _clientSetupVOList
     */
    private java.util.Vector _clientSetupVOList;

    /**
     * Field _investmentAllocationOverrideVOList
     */
    private java.util.Vector _investmentAllocationOverrideVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ContractSetupVO() {
        super();
        _outSuspenseVOList = new Vector();
        _clientSetupVOList = new Vector();
        _investmentAllocationOverrideVOList = new Vector();
    } //-- edit.common.vo.ContractSetupVO()


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
     * Method addInvestmentAllocationOverrideVO
     * 
     * @param vInvestmentAllocationOverrideVO
     */
    public void addInvestmentAllocationOverrideVO(edit.common.vo.InvestmentAllocationOverrideVO vInvestmentAllocationOverrideVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentAllocationOverrideVO.setParentVO(this.getClass(), this);
        _investmentAllocationOverrideVOList.addElement(vInvestmentAllocationOverrideVO);
    } //-- void addInvestmentAllocationOverrideVO(edit.common.vo.InvestmentAllocationOverrideVO) 

    /**
     * Method addInvestmentAllocationOverrideVO
     * 
     * @param index
     * @param vInvestmentAllocationOverrideVO
     */
    public void addInvestmentAllocationOverrideVO(int index, edit.common.vo.InvestmentAllocationOverrideVO vInvestmentAllocationOverrideVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentAllocationOverrideVO.setParentVO(this.getClass(), this);
        _investmentAllocationOverrideVOList.insertElementAt(vInvestmentAllocationOverrideVO, index);
    } //-- void addInvestmentAllocationOverrideVO(int, edit.common.vo.InvestmentAllocationOverrideVO) 

    /**
     * Method addOutSuspenseVO
     * 
     * @param vOutSuspenseVO
     */
    public void addOutSuspenseVO(edit.common.vo.OutSuspenseVO vOutSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vOutSuspenseVO.setParentVO(this.getClass(), this);
        _outSuspenseVOList.addElement(vOutSuspenseVO);
    } //-- void addOutSuspenseVO(edit.common.vo.OutSuspenseVO) 

    /**
     * Method addOutSuspenseVO
     * 
     * @param index
     * @param vOutSuspenseVO
     */
    public void addOutSuspenseVO(int index, edit.common.vo.OutSuspenseVO vOutSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vOutSuspenseVO.setParentVO(this.getClass(), this);
        _outSuspenseVOList.insertElementAt(vOutSuspenseVO, index);
    } //-- void addOutSuspenseVO(int, edit.common.vo.OutSuspenseVO) 

    /**
     * Method enumerateClientSetupVO
     */
    public java.util.Enumeration enumerateClientSetupVO()
    {
        return _clientSetupVOList.elements();
    } //-- java.util.Enumeration enumerateClientSetupVO() 

    /**
     * Method enumerateInvestmentAllocationOverrideVO
     */
    public java.util.Enumeration enumerateInvestmentAllocationOverrideVO()
    {
        return _investmentAllocationOverrideVOList.elements();
    } //-- java.util.Enumeration enumerateInvestmentAllocationOverrideVO() 

    /**
     * Method enumerateOutSuspenseVO
     */
    public java.util.Enumeration enumerateOutSuspenseVO()
    {
        return _outSuspenseVOList.elements();
    } //-- java.util.Enumeration enumerateOutSuspenseVO() 

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
        
        if (obj instanceof ContractSetupVO) {
        
            ContractSetupVO temp = (ContractSetupVO)obj;
            if (this._contractSetupPK != temp._contractSetupPK)
                return false;
            if (this._has_contractSetupPK != temp._has_contractSetupPK)
                return false;
            if (this._groupSetupFK != temp._groupSetupFK)
                return false;
            if (this._has_groupSetupFK != temp._has_groupSetupFK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._policyAmount != null) {
                if (temp._policyAmount == null) return false;
                else if (!(this._policyAmount.equals(temp._policyAmount))) 
                    return false;
            }
            else if (temp._policyAmount != null)
                return false;
            if (this._deathStatusCT != null) {
                if (temp._deathStatusCT == null) return false;
                else if (!(this._deathStatusCT.equals(temp._deathStatusCT))) 
                    return false;
            }
            else if (temp._deathStatusCT != null)
                return false;
            if (this._costBasis != null) {
                if (temp._costBasis == null) return false;
                else if (!(this._costBasis.equals(temp._costBasis))) 
                    return false;
            }
            else if (temp._costBasis != null)
                return false;
            if (this._amountReceived != null) {
                if (temp._amountReceived == null) return false;
                else if (!(this._amountReceived.equals(temp._amountReceived))) 
                    return false;
            }
            else if (temp._amountReceived != null)
                return false;
            if (this._claimStatusCT != null) {
                if (temp._claimStatusCT == null) return false;
                else if (!(this._claimStatusCT.equals(temp._claimStatusCT))) 
                    return false;
            }
            else if (temp._claimStatusCT != null)
                return false;
            if (this._complexChangeTypeCT != null) {
                if (temp._complexChangeTypeCT == null) return false;
                else if (!(this._complexChangeTypeCT.equals(temp._complexChangeTypeCT))) 
                    return false;
            }
            else if (temp._complexChangeTypeCT != null)
                return false;
            if (this._complexChangeNewValue != null) {
                if (temp._complexChangeNewValue == null) return false;
                else if (!(this._complexChangeNewValue.equals(temp._complexChangeNewValue))) 
                    return false;
            }
            else if (temp._complexChangeNewValue != null)
                return false;
            if (this._suppressDecreaseFaceInd != null) {
                if (temp._suppressDecreaseFaceInd == null) return false;
                else if (!(this._suppressDecreaseFaceInd.equals(temp._suppressDecreaseFaceInd))) 
                    return false;
            }
            else if (temp._suppressDecreaseFaceInd != null)
                return false;
            if (this._userInvestmentOverrideInd != null) {
                if (temp._userInvestmentOverrideInd == null) return false;
                else if (!(this._userInvestmentOverrideInd.equals(temp._userInvestmentOverrideInd))) 
                    return false;
            }
            else if (temp._userInvestmentOverrideInd != null)
                return false;
            if (this._dateOfDeath != null) {
                if (temp._dateOfDeath == null) return false;
                else if (!(this._dateOfDeath.equals(temp._dateOfDeath))) 
                    return false;
            }
            else if (temp._dateOfDeath != null)
                return false;
            if (this._careTypeCT != null) {
                if (temp._careTypeCT == null) return false;
                else if (!(this._careTypeCT.equals(temp._careTypeCT))) 
                    return false;
            }
            else if (temp._careTypeCT != null)
                return false;
            if (this._conditionCT != null) {
                if (temp._conditionCT == null) return false;
                else if (!(this._conditionCT.equals(temp._conditionCT))) 
                    return false;
            }
            else if (temp._conditionCT != null)
                return false;
            if (this._outSuspenseVOList != null) {
                if (temp._outSuspenseVOList == null) return false;
                else if (!(this._outSuspenseVOList.equals(temp._outSuspenseVOList))) 
                    return false;
            }
            else if (temp._outSuspenseVOList != null)
                return false;
            if (this._clientSetupVOList != null) {
                if (temp._clientSetupVOList == null) return false;
                else if (!(this._clientSetupVOList.equals(temp._clientSetupVOList))) 
                    return false;
            }
            else if (temp._clientSetupVOList != null)
                return false;
            if (this._investmentAllocationOverrideVOList != null) {
                if (temp._investmentAllocationOverrideVOList == null) return false;
                else if (!(this._investmentAllocationOverrideVOList.equals(temp._investmentAllocationOverrideVOList))) 
                    return false;
            }
            else if (temp._investmentAllocationOverrideVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAmountReceivedReturns the value of field
     * 'amountReceived'.
     * 
     * @return the value of field 'amountReceived'.
     */
    public java.math.BigDecimal getAmountReceived()
    {
        return this._amountReceived;
    } //-- java.math.BigDecimal getAmountReceived() 

    /**
     * Method getCareTypeCTReturns the value of field 'careTypeCT'.
     * 
     * @return the value of field 'careTypeCT'.
     */
    public java.lang.String getCareTypeCT()
    {
        return this._careTypeCT;
    } //-- java.lang.String getCareTypeCT() 

    /**
     * Method getClaimStatusCTReturns the value of field
     * 'claimStatusCT'.
     * 
     * @return the value of field 'claimStatusCT'.
     */
    public java.lang.String getClaimStatusCT()
    {
        return this._claimStatusCT;
    } //-- java.lang.String getClaimStatusCT() 

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
     * Method getComplexChangeNewValueReturns the value of field
     * 'complexChangeNewValue'.
     * 
     * @return the value of field 'complexChangeNewValue'.
     */
    public java.lang.String getComplexChangeNewValue()
    {
        return this._complexChangeNewValue;
    } //-- java.lang.String getComplexChangeNewValue() 

    /**
     * Method getComplexChangeTypeCTReturns the value of field
     * 'complexChangeTypeCT'.
     * 
     * @return the value of field 'complexChangeTypeCT'.
     */
    public java.lang.String getComplexChangeTypeCT()
    {
        return this._complexChangeTypeCT;
    } //-- java.lang.String getComplexChangeTypeCT() 

    /**
     * Method getConditionCTReturns the value of field
     * 'conditionCT'.
     * 
     * @return the value of field 'conditionCT'.
     */
    public java.lang.String getConditionCT()
    {
        return this._conditionCT;
    } //-- java.lang.String getConditionCT() 

    /**
     * Method getContractSetupPKReturns the value of field
     * 'contractSetupPK'.
     * 
     * @return the value of field 'contractSetupPK'.
     */
    public long getContractSetupPK()
    {
        return this._contractSetupPK;
    } //-- long getContractSetupPK() 

    /**
     * Method getCostBasisReturns the value of field 'costBasis'.
     * 
     * @return the value of field 'costBasis'.
     */
    public java.math.BigDecimal getCostBasis()
    {
        return this._costBasis;
    } //-- java.math.BigDecimal getCostBasis() 

    /**
     * Method getDateOfDeathReturns the value of field
     * 'dateOfDeath'.
     * 
     * @return the value of field 'dateOfDeath'.
     */
    public java.lang.String getDateOfDeath()
    {
        return this._dateOfDeath;
    } //-- java.lang.String getDateOfDeath() 

    /**
     * Method getDeathStatusCTReturns the value of field
     * 'deathStatusCT'.
     * 
     * @return the value of field 'deathStatusCT'.
     */
    public java.lang.String getDeathStatusCT()
    {
        return this._deathStatusCT;
    } //-- java.lang.String getDeathStatusCT() 

    /**
     * Method getGroupSetupFKReturns the value of field
     * 'groupSetupFK'.
     * 
     * @return the value of field 'groupSetupFK'.
     */
    public long getGroupSetupFK()
    {
        return this._groupSetupFK;
    } //-- long getGroupSetupFK() 

    /**
     * Method getInvestmentAllocationOverrideVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentAllocationOverrideVO getInvestmentAllocationOverrideVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentAllocationOverrideVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.InvestmentAllocationOverrideVO) _investmentAllocationOverrideVOList.elementAt(index);
    } //-- edit.common.vo.InvestmentAllocationOverrideVO getInvestmentAllocationOverrideVO(int) 

    /**
     * Method getInvestmentAllocationOverrideVO
     */
    public edit.common.vo.InvestmentAllocationOverrideVO[] getInvestmentAllocationOverrideVO()
    {
        int size = _investmentAllocationOverrideVOList.size();
        edit.common.vo.InvestmentAllocationOverrideVO[] mArray = new edit.common.vo.InvestmentAllocationOverrideVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.InvestmentAllocationOverrideVO) _investmentAllocationOverrideVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.InvestmentAllocationOverrideVO[] getInvestmentAllocationOverrideVO() 

    /**
     * Method getInvestmentAllocationOverrideVOCount
     */
    public int getInvestmentAllocationOverrideVOCount()
    {
        return _investmentAllocationOverrideVOList.size();
    } //-- int getInvestmentAllocationOverrideVOCount() 

    /**
     * Method getOutSuspenseVO
     * 
     * @param index
     */
    public edit.common.vo.OutSuspenseVO getOutSuspenseVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _outSuspenseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.OutSuspenseVO) _outSuspenseVOList.elementAt(index);
    } //-- edit.common.vo.OutSuspenseVO getOutSuspenseVO(int) 

    /**
     * Method getOutSuspenseVO
     */
    public edit.common.vo.OutSuspenseVO[] getOutSuspenseVO()
    {
        int size = _outSuspenseVOList.size();
        edit.common.vo.OutSuspenseVO[] mArray = new edit.common.vo.OutSuspenseVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.OutSuspenseVO) _outSuspenseVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.OutSuspenseVO[] getOutSuspenseVO() 

    /**
     * Method getOutSuspenseVOCount
     */
    public int getOutSuspenseVOCount()
    {
        return _outSuspenseVOList.size();
    } //-- int getOutSuspenseVOCount() 

    /**
     * Method getPolicyAmountReturns the value of field
     * 'policyAmount'.
     * 
     * @return the value of field 'policyAmount'.
     */
    public java.math.BigDecimal getPolicyAmount()
    {
        return this._policyAmount;
    } //-- java.math.BigDecimal getPolicyAmount() 

    /**
     * Method getSegmentFKReturns the value of field 'segmentFK'.
     * 
     * @return the value of field 'segmentFK'.
     */
    public long getSegmentFK()
    {
        return this._segmentFK;
    } //-- long getSegmentFK() 

    /**
     * Method getSuppressDecreaseFaceIndReturns the value of field
     * 'suppressDecreaseFaceInd'.
     * 
     * @return the value of field 'suppressDecreaseFaceInd'.
     */
    public java.lang.String getSuppressDecreaseFaceInd()
    {
        return this._suppressDecreaseFaceInd;
    } //-- java.lang.String getSuppressDecreaseFaceInd() 

    /**
     * Method getUserInvestmentOverrideIndReturns the value of
     * field 'userInvestmentOverrideInd'.
     * 
     * @return the value of field 'userInvestmentOverrideInd'.
     */
    public java.lang.String getUserInvestmentOverrideInd()
    {
        return this._userInvestmentOverrideInd;
    } //-- java.lang.String getUserInvestmentOverrideInd() 

    /**
     * Method hasContractSetupPK
     */
    public boolean hasContractSetupPK()
    {
        return this._has_contractSetupPK;
    } //-- boolean hasContractSetupPK() 

    /**
     * Method hasGroupSetupFK
     */
    public boolean hasGroupSetupFK()
    {
        return this._has_groupSetupFK;
    } //-- boolean hasGroupSetupFK() 

    /**
     * Method hasSegmentFK
     */
    public boolean hasSegmentFK()
    {
        return this._has_segmentFK;
    } //-- boolean hasSegmentFK() 

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
     * Method removeAllInvestmentAllocationOverrideVO
     */
    public void removeAllInvestmentAllocationOverrideVO()
    {
        _investmentAllocationOverrideVOList.removeAllElements();
    } //-- void removeAllInvestmentAllocationOverrideVO() 

    /**
     * Method removeAllOutSuspenseVO
     */
    public void removeAllOutSuspenseVO()
    {
        _outSuspenseVOList.removeAllElements();
    } //-- void removeAllOutSuspenseVO() 

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
     * Method removeInvestmentAllocationOverrideVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentAllocationOverrideVO removeInvestmentAllocationOverrideVO(int index)
    {
        java.lang.Object obj = _investmentAllocationOverrideVOList.elementAt(index);
        _investmentAllocationOverrideVOList.removeElementAt(index);
        return (edit.common.vo.InvestmentAllocationOverrideVO) obj;
    } //-- edit.common.vo.InvestmentAllocationOverrideVO removeInvestmentAllocationOverrideVO(int) 

    /**
     * Method removeOutSuspenseVO
     * 
     * @param index
     */
    public edit.common.vo.OutSuspenseVO removeOutSuspenseVO(int index)
    {
        java.lang.Object obj = _outSuspenseVOList.elementAt(index);
        _outSuspenseVOList.removeElementAt(index);
        return (edit.common.vo.OutSuspenseVO) obj;
    } //-- edit.common.vo.OutSuspenseVO removeOutSuspenseVO(int) 

    /**
     * Method setAmountReceivedSets the value of field
     * 'amountReceived'.
     * 
     * @param amountReceived the value of field 'amountReceived'.
     */
    public void setAmountReceived(java.math.BigDecimal amountReceived)
    {
        this._amountReceived = amountReceived;
        
        super.setVoChanged(true);
    } //-- void setAmountReceived(java.math.BigDecimal) 

    /**
     * Method setCareTypeCTSets the value of field 'careTypeCT'.
     * 
     * @param careTypeCT the value of field 'careTypeCT'.
     */
    public void setCareTypeCT(java.lang.String careTypeCT)
    {
        this._careTypeCT = careTypeCT;
        
        super.setVoChanged(true);
    } //-- void setCareTypeCT(java.lang.String) 

    /**
     * Method setClaimStatusCTSets the value of field
     * 'claimStatusCT'.
     * 
     * @param claimStatusCT the value of field 'claimStatusCT'.
     */
    public void setClaimStatusCT(java.lang.String claimStatusCT)
    {
        this._claimStatusCT = claimStatusCT;
        
        super.setVoChanged(true);
    } //-- void setClaimStatusCT(java.lang.String) 

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
     * Method setComplexChangeNewValueSets the value of field
     * 'complexChangeNewValue'.
     * 
     * @param complexChangeNewValue the value of field
     * 'complexChangeNewValue'.
     */
    public void setComplexChangeNewValue(java.lang.String complexChangeNewValue)
    {
        this._complexChangeNewValue = complexChangeNewValue;
        
        super.setVoChanged(true);
    } //-- void setComplexChangeNewValue(java.lang.String) 

    /**
     * Method setComplexChangeTypeCTSets the value of field
     * 'complexChangeTypeCT'.
     * 
     * @param complexChangeTypeCT the value of field
     * 'complexChangeTypeCT'.
     */
    public void setComplexChangeTypeCT(java.lang.String complexChangeTypeCT)
    {
        this._complexChangeTypeCT = complexChangeTypeCT;
        
        super.setVoChanged(true);
    } //-- void setComplexChangeTypeCT(java.lang.String) 

    /**
     * Method setConditionCTSets the value of field 'conditionCT'.
     * 
     * @param conditionCT the value of field 'conditionCT'.
     */
    public void setConditionCT(java.lang.String conditionCT)
    {
        this._conditionCT = conditionCT;
        
        super.setVoChanged(true);
    } //-- void setConditionCT(java.lang.String) 

    /**
     * Method setContractSetupPKSets the value of field
     * 'contractSetupPK'.
     * 
     * @param contractSetupPK the value of field 'contractSetupPK'.
     */
    public void setContractSetupPK(long contractSetupPK)
    {
        this._contractSetupPK = contractSetupPK;
        
        super.setVoChanged(true);
        this._has_contractSetupPK = true;
    } //-- void setContractSetupPK(long) 

    /**
     * Method setCostBasisSets the value of field 'costBasis'.
     * 
     * @param costBasis the value of field 'costBasis'.
     */
    public void setCostBasis(java.math.BigDecimal costBasis)
    {
        this._costBasis = costBasis;
        
        super.setVoChanged(true);
    } //-- void setCostBasis(java.math.BigDecimal) 

    /**
     * Method setDateOfDeathSets the value of field 'dateOfDeath'.
     * 
     * @param dateOfDeath the value of field 'dateOfDeath'.
     */
    public void setDateOfDeath(java.lang.String dateOfDeath)
    {
        this._dateOfDeath = dateOfDeath;
        
        super.setVoChanged(true);
    } //-- void setDateOfDeath(java.lang.String) 

    /**
     * Method setDeathStatusCTSets the value of field
     * 'deathStatusCT'.
     * 
     * @param deathStatusCT the value of field 'deathStatusCT'.
     */
    public void setDeathStatusCT(java.lang.String deathStatusCT)
    {
        this._deathStatusCT = deathStatusCT;
        
        super.setVoChanged(true);
    } //-- void setDeathStatusCT(java.lang.String) 

    /**
     * Method setGroupSetupFKSets the value of field
     * 'groupSetupFK'.
     * 
     * @param groupSetupFK the value of field 'groupSetupFK'.
     */
    public void setGroupSetupFK(long groupSetupFK)
    {
        this._groupSetupFK = groupSetupFK;
        
        super.setVoChanged(true);
        this._has_groupSetupFK = true;
    } //-- void setGroupSetupFK(long) 

    /**
     * Method setInvestmentAllocationOverrideVO
     * 
     * @param index
     * @param vInvestmentAllocationOverrideVO
     */
    public void setInvestmentAllocationOverrideVO(int index, edit.common.vo.InvestmentAllocationOverrideVO vInvestmentAllocationOverrideVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentAllocationOverrideVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vInvestmentAllocationOverrideVO.setParentVO(this.getClass(), this);
        _investmentAllocationOverrideVOList.setElementAt(vInvestmentAllocationOverrideVO, index);
    } //-- void setInvestmentAllocationOverrideVO(int, edit.common.vo.InvestmentAllocationOverrideVO) 

    /**
     * Method setInvestmentAllocationOverrideVO
     * 
     * @param investmentAllocationOverrideVOArray
     */
    public void setInvestmentAllocationOverrideVO(edit.common.vo.InvestmentAllocationOverrideVO[] investmentAllocationOverrideVOArray)
    {
        //-- copy array
        _investmentAllocationOverrideVOList.removeAllElements();
        for (int i = 0; i < investmentAllocationOverrideVOArray.length; i++) {
            investmentAllocationOverrideVOArray[i].setParentVO(this.getClass(), this);
            _investmentAllocationOverrideVOList.addElement(investmentAllocationOverrideVOArray[i]);
        }
    } //-- void setInvestmentAllocationOverrideVO(edit.common.vo.InvestmentAllocationOverrideVO) 

    /**
     * Method setOutSuspenseVO
     * 
     * @param index
     * @param vOutSuspenseVO
     */
    public void setOutSuspenseVO(int index, edit.common.vo.OutSuspenseVO vOutSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _outSuspenseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vOutSuspenseVO.setParentVO(this.getClass(), this);
        _outSuspenseVOList.setElementAt(vOutSuspenseVO, index);
    } //-- void setOutSuspenseVO(int, edit.common.vo.OutSuspenseVO) 

    /**
     * Method setOutSuspenseVO
     * 
     * @param outSuspenseVOArray
     */
    public void setOutSuspenseVO(edit.common.vo.OutSuspenseVO[] outSuspenseVOArray)
    {
        //-- copy array
        _outSuspenseVOList.removeAllElements();
        for (int i = 0; i < outSuspenseVOArray.length; i++) {
            outSuspenseVOArray[i].setParentVO(this.getClass(), this);
            _outSuspenseVOList.addElement(outSuspenseVOArray[i]);
        }
    } //-- void setOutSuspenseVO(edit.common.vo.OutSuspenseVO) 

    /**
     * Method setPolicyAmountSets the value of field
     * 'policyAmount'.
     * 
     * @param policyAmount the value of field 'policyAmount'.
     */
    public void setPolicyAmount(java.math.BigDecimal policyAmount)
    {
        this._policyAmount = policyAmount;
        
        super.setVoChanged(true);
    } //-- void setPolicyAmount(java.math.BigDecimal) 

    /**
     * Method setSegmentFKSets the value of field 'segmentFK'.
     * 
     * @param segmentFK the value of field 'segmentFK'.
     */
    public void setSegmentFK(long segmentFK)
    {
        this._segmentFK = segmentFK;
        
        super.setVoChanged(true);
        this._has_segmentFK = true;
    } //-- void setSegmentFK(long) 

    /**
     * Method setSuppressDecreaseFaceIndSets the value of field
     * 'suppressDecreaseFaceInd'.
     * 
     * @param suppressDecreaseFaceInd the value of field
     * 'suppressDecreaseFaceInd'.
     */
    public void setSuppressDecreaseFaceInd(java.lang.String suppressDecreaseFaceInd)
    {
        this._suppressDecreaseFaceInd = suppressDecreaseFaceInd;
        
        super.setVoChanged(true);
    } //-- void setSuppressDecreaseFaceInd(java.lang.String) 

    /**
     * Method setUserInvestmentOverrideIndSets the value of field
     * 'userInvestmentOverrideInd'.
     * 
     * @param userInvestmentOverrideInd the value of field
     * 'userInvestmentOverrideInd'.
     */
    public void setUserInvestmentOverrideInd(java.lang.String userInvestmentOverrideInd)
    {
        this._userInvestmentOverrideInd = userInvestmentOverrideInd;
        
        super.setVoChanged(true);
    } //-- void setUserInvestmentOverrideInd(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ContractSetupVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ContractSetupVO) Unmarshaller.unmarshal(edit.common.vo.ContractSetupVO.class, reader);
    } //-- edit.common.vo.ContractSetupVO unmarshal(java.io.Reader) 

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
