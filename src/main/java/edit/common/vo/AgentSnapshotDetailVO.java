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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class AgentSnapshotDetailVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _agentGroupFK
     */
    private long _agentGroupFK;

    /**
     * keeps track of state for field: _agentGroupFK
     */
    private boolean _has_agentGroupFK;

    /**
     * Field _agentSnapshotDetailPK
     */
    private long _agentSnapshotDetailPK;

    /**
     * keeps track of state for field: _agentSnapshotDetailPK
     */
    private boolean _has_agentSnapshotDetailPK;

    /**
     * Field _agentSnapshotPK
     */
    private long _agentSnapshotPK;

    /**
     * keeps track of state for field: _agentSnapshotPK
     */
    private boolean _has_agentSnapshotPK;

    /**
     * Field _placedAgentFK
     */
    private long _placedAgentFK;

    /**
     * keeps track of state for field: _placedAgentFK
     */
    private boolean _has_placedAgentFK;

    /**
     * Field _commissionOverrideAmount
     */
    private java.math.BigDecimal _commissionOverrideAmount;

    /**
     * Field _commissionOverridePercent
     */
    private java.math.BigDecimal _commissionOverridePercent;

    /**
     * Field _totalCommissionsPaid
     */
    private java.math.BigDecimal _totalCommissionsPaid;

    /**
     * Field _advanceAmount
     */
    private java.math.BigDecimal _advanceAmount;

    /**
     * Field _advanceRecovery
     */
    private java.math.BigDecimal _advanceRecovery;

    /**
     * Field _advancePercent
     */
    private java.math.BigDecimal _advancePercent;

    /**
     * Field _recoveryPercent
     */
    private java.math.BigDecimal _recoveryPercent;

    /**
     * Field _agentVOList
     */
    private java.util.Vector _agentVOList;

    /**
     * Field _agentContractVOList
     */
    private java.util.Vector _agentContractVOList;

    /**
     * Field _agentLicenseVOList
     */
    private java.util.Vector _agentLicenseVOList;

    /**
     * Field _commissionProfileVOList
     */
    private java.util.Vector _commissionProfileVOList;

    /**
     * Field _additionalCompensationVOList
     */
    private java.util.Vector _additionalCompensationVOList;

    /**
     * Field _clientDetailVOList
     */
    private java.util.Vector _clientDetailVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public AgentSnapshotDetailVO() {
        super();
        _agentVOList = new Vector();
        _agentContractVOList = new Vector();
        _agentLicenseVOList = new Vector();
        _commissionProfileVOList = new Vector();
        _additionalCompensationVOList = new Vector();
        _clientDetailVOList = new Vector();
    } //-- edit.common.vo.AgentSnapshotDetailVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAdditionalCompensationVO
     * 
     * @param vAdditionalCompensationVO
     */
    public void addAdditionalCompensationVO(edit.common.vo.AdditionalCompensationVO vAdditionalCompensationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAdditionalCompensationVO.setParentVO(this.getClass(), this);
        _additionalCompensationVOList.addElement(vAdditionalCompensationVO);
    } //-- void addAdditionalCompensationVO(edit.common.vo.AdditionalCompensationVO) 

    /**
     * Method addAdditionalCompensationVO
     * 
     * @param index
     * @param vAdditionalCompensationVO
     */
    public void addAdditionalCompensationVO(int index, edit.common.vo.AdditionalCompensationVO vAdditionalCompensationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAdditionalCompensationVO.setParentVO(this.getClass(), this);
        _additionalCompensationVOList.insertElementAt(vAdditionalCompensationVO, index);
    } //-- void addAdditionalCompensationVO(int, edit.common.vo.AdditionalCompensationVO) 

    /**
     * Method addAgentContractVO
     * 
     * @param vAgentContractVO
     */
    public void addAgentContractVO(edit.common.vo.AgentContractVO vAgentContractVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentContractVO.setParentVO(this.getClass(), this);
        _agentContractVOList.addElement(vAgentContractVO);
    } //-- void addAgentContractVO(edit.common.vo.AgentContractVO) 

    /**
     * Method addAgentContractVO
     * 
     * @param index
     * @param vAgentContractVO
     */
    public void addAgentContractVO(int index, edit.common.vo.AgentContractVO vAgentContractVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentContractVO.setParentVO(this.getClass(), this);
        _agentContractVOList.insertElementAt(vAgentContractVO, index);
    } //-- void addAgentContractVO(int, edit.common.vo.AgentContractVO) 

    /**
     * Method addAgentLicenseVO
     * 
     * @param vAgentLicenseVO
     */
    public void addAgentLicenseVO(edit.common.vo.AgentLicenseVO vAgentLicenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentLicenseVO.setParentVO(this.getClass(), this);
        _agentLicenseVOList.addElement(vAgentLicenseVO);
    } //-- void addAgentLicenseVO(edit.common.vo.AgentLicenseVO) 

    /**
     * Method addAgentLicenseVO
     * 
     * @param index
     * @param vAgentLicenseVO
     */
    public void addAgentLicenseVO(int index, edit.common.vo.AgentLicenseVO vAgentLicenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentLicenseVO.setParentVO(this.getClass(), this);
        _agentLicenseVOList.insertElementAt(vAgentLicenseVO, index);
    } //-- void addAgentLicenseVO(int, edit.common.vo.AgentLicenseVO) 

    /**
     * Method addAgentVO
     * 
     * @param vAgentVO
     */
    public void addAgentVO(edit.common.vo.AgentVO vAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentVO.setParentVO(this.getClass(), this);
        _agentVOList.addElement(vAgentVO);
    } //-- void addAgentVO(edit.common.vo.AgentVO) 

    /**
     * Method addAgentVO
     * 
     * @param index
     * @param vAgentVO
     */
    public void addAgentVO(int index, edit.common.vo.AgentVO vAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentVO.setParentVO(this.getClass(), this);
        _agentVOList.insertElementAt(vAgentVO, index);
    } //-- void addAgentVO(int, edit.common.vo.AgentVO) 

    /**
     * Method addClientDetailVO
     * 
     * @param vClientDetailVO
     */
    public void addClientDetailVO(edit.common.vo.ClientDetailVO vClientDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientDetailVO.setParentVO(this.getClass(), this);
        _clientDetailVOList.addElement(vClientDetailVO);
    } //-- void addClientDetailVO(edit.common.vo.ClientDetailVO) 

    /**
     * Method addClientDetailVO
     * 
     * @param index
     * @param vClientDetailVO
     */
    public void addClientDetailVO(int index, edit.common.vo.ClientDetailVO vClientDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientDetailVO.setParentVO(this.getClass(), this);
        _clientDetailVOList.insertElementAt(vClientDetailVO, index);
    } //-- void addClientDetailVO(int, edit.common.vo.ClientDetailVO) 

    /**
     * Method addCommissionProfileVO
     * 
     * @param vCommissionProfileVO
     */
    public void addCommissionProfileVO(edit.common.vo.CommissionProfileVO vCommissionProfileVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionProfileVO.setParentVO(this.getClass(), this);
        _commissionProfileVOList.addElement(vCommissionProfileVO);
    } //-- void addCommissionProfileVO(edit.common.vo.CommissionProfileVO) 

    /**
     * Method addCommissionProfileVO
     * 
     * @param index
     * @param vCommissionProfileVO
     */
    public void addCommissionProfileVO(int index, edit.common.vo.CommissionProfileVO vCommissionProfileVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionProfileVO.setParentVO(this.getClass(), this);
        _commissionProfileVOList.insertElementAt(vCommissionProfileVO, index);
    } //-- void addCommissionProfileVO(int, edit.common.vo.CommissionProfileVO) 

    /**
     * Method enumerateAdditionalCompensationVO
     */
    public java.util.Enumeration enumerateAdditionalCompensationVO()
    {
        return _additionalCompensationVOList.elements();
    } //-- java.util.Enumeration enumerateAdditionalCompensationVO() 

    /**
     * Method enumerateAgentContractVO
     */
    public java.util.Enumeration enumerateAgentContractVO()
    {
        return _agentContractVOList.elements();
    } //-- java.util.Enumeration enumerateAgentContractVO() 

    /**
     * Method enumerateAgentLicenseVO
     */
    public java.util.Enumeration enumerateAgentLicenseVO()
    {
        return _agentLicenseVOList.elements();
    } //-- java.util.Enumeration enumerateAgentLicenseVO() 

    /**
     * Method enumerateAgentVO
     */
    public java.util.Enumeration enumerateAgentVO()
    {
        return _agentVOList.elements();
    } //-- java.util.Enumeration enumerateAgentVO() 

    /**
     * Method enumerateClientDetailVO
     */
    public java.util.Enumeration enumerateClientDetailVO()
    {
        return _clientDetailVOList.elements();
    } //-- java.util.Enumeration enumerateClientDetailVO() 

    /**
     * Method enumerateCommissionProfileVO
     */
    public java.util.Enumeration enumerateCommissionProfileVO()
    {
        return _commissionProfileVOList.elements();
    } //-- java.util.Enumeration enumerateCommissionProfileVO() 

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
        
        if (obj instanceof AgentSnapshotDetailVO) {
        
            AgentSnapshotDetailVO temp = (AgentSnapshotDetailVO)obj;
            if (this._agentGroupFK != temp._agentGroupFK)
                return false;
            if (this._has_agentGroupFK != temp._has_agentGroupFK)
                return false;
            if (this._agentSnapshotDetailPK != temp._agentSnapshotDetailPK)
                return false;
            if (this._has_agentSnapshotDetailPK != temp._has_agentSnapshotDetailPK)
                return false;
            if (this._agentSnapshotPK != temp._agentSnapshotPK)
                return false;
            if (this._has_agentSnapshotPK != temp._has_agentSnapshotPK)
                return false;
            if (this._placedAgentFK != temp._placedAgentFK)
                return false;
            if (this._has_placedAgentFK != temp._has_placedAgentFK)
                return false;
            if (this._commissionOverrideAmount != null) {
                if (temp._commissionOverrideAmount == null) return false;
                else if (!(this._commissionOverrideAmount.equals(temp._commissionOverrideAmount))) 
                    return false;
            }
            else if (temp._commissionOverrideAmount != null)
                return false;
            if (this._commissionOverridePercent != null) {
                if (temp._commissionOverridePercent == null) return false;
                else if (!(this._commissionOverridePercent.equals(temp._commissionOverridePercent))) 
                    return false;
            }
            else if (temp._commissionOverridePercent != null)
                return false;
            if (this._totalCommissionsPaid != null) {
                if (temp._totalCommissionsPaid == null) return false;
                else if (!(this._totalCommissionsPaid.equals(temp._totalCommissionsPaid))) 
                    return false;
            }
            else if (temp._totalCommissionsPaid != null)
                return false;
            if (this._advanceAmount != null) {
                if (temp._advanceAmount == null) return false;
                else if (!(this._advanceAmount.equals(temp._advanceAmount))) 
                    return false;
            }
            else if (temp._advanceAmount != null)
                return false;
            if (this._advanceRecovery != null) {
                if (temp._advanceRecovery == null) return false;
                else if (!(this._advanceRecovery.equals(temp._advanceRecovery))) 
                    return false;
            }
            else if (temp._advanceRecovery != null)
                return false;
            if (this._advancePercent != null) {
                if (temp._advancePercent == null) return false;
                else if (!(this._advancePercent.equals(temp._advancePercent))) 
                    return false;
            }
            else if (temp._advancePercent != null)
                return false;
            if (this._recoveryPercent != null) {
                if (temp._recoveryPercent == null) return false;
                else if (!(this._recoveryPercent.equals(temp._recoveryPercent))) 
                    return false;
            }
            else if (temp._recoveryPercent != null)
                return false;
            if (this._agentVOList != null) {
                if (temp._agentVOList == null) return false;
                else if (!(this._agentVOList.equals(temp._agentVOList))) 
                    return false;
            }
            else if (temp._agentVOList != null)
                return false;
            if (this._agentContractVOList != null) {
                if (temp._agentContractVOList == null) return false;
                else if (!(this._agentContractVOList.equals(temp._agentContractVOList))) 
                    return false;
            }
            else if (temp._agentContractVOList != null)
                return false;
            if (this._agentLicenseVOList != null) {
                if (temp._agentLicenseVOList == null) return false;
                else if (!(this._agentLicenseVOList.equals(temp._agentLicenseVOList))) 
                    return false;
            }
            else if (temp._agentLicenseVOList != null)
                return false;
            if (this._commissionProfileVOList != null) {
                if (temp._commissionProfileVOList == null) return false;
                else if (!(this._commissionProfileVOList.equals(temp._commissionProfileVOList))) 
                    return false;
            }
            else if (temp._commissionProfileVOList != null)
                return false;
            if (this._additionalCompensationVOList != null) {
                if (temp._additionalCompensationVOList == null) return false;
                else if (!(this._additionalCompensationVOList.equals(temp._additionalCompensationVOList))) 
                    return false;
            }
            else if (temp._additionalCompensationVOList != null)
                return false;
            if (this._clientDetailVOList != null) {
                if (temp._clientDetailVOList == null) return false;
                else if (!(this._clientDetailVOList.equals(temp._clientDetailVOList))) 
                    return false;
            }
            else if (temp._clientDetailVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAdditionalCompensationVO
     * 
     * @param index
     */
    public edit.common.vo.AdditionalCompensationVO getAdditionalCompensationVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _additionalCompensationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AdditionalCompensationVO) _additionalCompensationVOList.elementAt(index);
    } //-- edit.common.vo.AdditionalCompensationVO getAdditionalCompensationVO(int) 

    /**
     * Method getAdditionalCompensationVO
     */
    public edit.common.vo.AdditionalCompensationVO[] getAdditionalCompensationVO()
    {
        int size = _additionalCompensationVOList.size();
        edit.common.vo.AdditionalCompensationVO[] mArray = new edit.common.vo.AdditionalCompensationVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AdditionalCompensationVO) _additionalCompensationVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AdditionalCompensationVO[] getAdditionalCompensationVO() 

    /**
     * Method getAdditionalCompensationVOCount
     */
    public int getAdditionalCompensationVOCount()
    {
        return _additionalCompensationVOList.size();
    } //-- int getAdditionalCompensationVOCount() 

    /**
     * Method getAdvanceAmountReturns the value of field
     * 'advanceAmount'.
     * 
     * @return the value of field 'advanceAmount'.
     */
    public java.math.BigDecimal getAdvanceAmount()
    {
        return this._advanceAmount;
    } //-- java.math.BigDecimal getAdvanceAmount() 

    /**
     * Method getAdvancePercentReturns the value of field
     * 'advancePercent'.
     * 
     * @return the value of field 'advancePercent'.
     */
    public java.math.BigDecimal getAdvancePercent()
    {
        return this._advancePercent;
    } //-- java.math.BigDecimal getAdvancePercent() 

    /**
     * Method getAdvanceRecoveryReturns the value of field
     * 'advanceRecovery'.
     * 
     * @return the value of field 'advanceRecovery'.
     */
    public java.math.BigDecimal getAdvanceRecovery()
    {
        return this._advanceRecovery;
    } //-- java.math.BigDecimal getAdvanceRecovery() 

    /**
     * Method getAgentContractVO
     * 
     * @param index
     */
    public edit.common.vo.AgentContractVO getAgentContractVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentContractVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AgentContractVO) _agentContractVOList.elementAt(index);
    } //-- edit.common.vo.AgentContractVO getAgentContractVO(int) 

    /**
     * Method getAgentContractVO
     */
    public edit.common.vo.AgentContractVO[] getAgentContractVO()
    {
        int size = _agentContractVOList.size();
        edit.common.vo.AgentContractVO[] mArray = new edit.common.vo.AgentContractVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AgentContractVO) _agentContractVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AgentContractVO[] getAgentContractVO() 

    /**
     * Method getAgentContractVOCount
     */
    public int getAgentContractVOCount()
    {
        return _agentContractVOList.size();
    } //-- int getAgentContractVOCount() 

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
     * Method getAgentLicenseVO
     * 
     * @param index
     */
    public edit.common.vo.AgentLicenseVO getAgentLicenseVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentLicenseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AgentLicenseVO) _agentLicenseVOList.elementAt(index);
    } //-- edit.common.vo.AgentLicenseVO getAgentLicenseVO(int) 

    /**
     * Method getAgentLicenseVO
     */
    public edit.common.vo.AgentLicenseVO[] getAgentLicenseVO()
    {
        int size = _agentLicenseVOList.size();
        edit.common.vo.AgentLicenseVO[] mArray = new edit.common.vo.AgentLicenseVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AgentLicenseVO) _agentLicenseVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AgentLicenseVO[] getAgentLicenseVO() 

    /**
     * Method getAgentLicenseVOCount
     */
    public int getAgentLicenseVOCount()
    {
        return _agentLicenseVOList.size();
    } //-- int getAgentLicenseVOCount() 

    /**
     * Method getAgentSnapshotDetailPKReturns the value of field
     * 'agentSnapshotDetailPK'.
     * 
     * @return the value of field 'agentSnapshotDetailPK'.
     */
    public long getAgentSnapshotDetailPK()
    {
        return this._agentSnapshotDetailPK;
    } //-- long getAgentSnapshotDetailPK() 

    /**
     * Method getAgentSnapshotPKReturns the value of field
     * 'agentSnapshotPK'.
     * 
     * @return the value of field 'agentSnapshotPK'.
     */
    public long getAgentSnapshotPK()
    {
        return this._agentSnapshotPK;
    } //-- long getAgentSnapshotPK() 

    /**
     * Method getAgentVO
     * 
     * @param index
     */
    public edit.common.vo.AgentVO getAgentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AgentVO) _agentVOList.elementAt(index);
    } //-- edit.common.vo.AgentVO getAgentVO(int) 

    /**
     * Method getAgentVO
     */
    public edit.common.vo.AgentVO[] getAgentVO()
    {
        int size = _agentVOList.size();
        edit.common.vo.AgentVO[] mArray = new edit.common.vo.AgentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AgentVO) _agentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AgentVO[] getAgentVO() 

    /**
     * Method getAgentVOCount
     */
    public int getAgentVOCount()
    {
        return _agentVOList.size();
    } //-- int getAgentVOCount() 

    /**
     * Method getClientDetailVO
     * 
     * @param index
     */
    public edit.common.vo.ClientDetailVO getClientDetailVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ClientDetailVO) _clientDetailVOList.elementAt(index);
    } //-- edit.common.vo.ClientDetailVO getClientDetailVO(int) 

    /**
     * Method getClientDetailVO
     */
    public edit.common.vo.ClientDetailVO[] getClientDetailVO()
    {
        int size = _clientDetailVOList.size();
        edit.common.vo.ClientDetailVO[] mArray = new edit.common.vo.ClientDetailVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ClientDetailVO) _clientDetailVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ClientDetailVO[] getClientDetailVO() 

    /**
     * Method getClientDetailVOCount
     */
    public int getClientDetailVOCount()
    {
        return _clientDetailVOList.size();
    } //-- int getClientDetailVOCount() 

    /**
     * Method getCommissionOverrideAmountReturns the value of field
     * 'commissionOverrideAmount'.
     * 
     * @return the value of field 'commissionOverrideAmount'.
     */
    public java.math.BigDecimal getCommissionOverrideAmount()
    {
        return this._commissionOverrideAmount;
    } //-- java.math.BigDecimal getCommissionOverrideAmount() 

    /**
     * Method getCommissionOverridePercentReturns the value of
     * field 'commissionOverridePercent'.
     * 
     * @return the value of field 'commissionOverridePercent'.
     */
    public java.math.BigDecimal getCommissionOverridePercent()
    {
        return this._commissionOverridePercent;
    } //-- java.math.BigDecimal getCommissionOverridePercent() 

    /**
     * Method getCommissionProfileVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionProfileVO getCommissionProfileVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionProfileVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.CommissionProfileVO) _commissionProfileVOList.elementAt(index);
    } //-- edit.common.vo.CommissionProfileVO getCommissionProfileVO(int) 

    /**
     * Method getCommissionProfileVO
     */
    public edit.common.vo.CommissionProfileVO[] getCommissionProfileVO()
    {
        int size = _commissionProfileVOList.size();
        edit.common.vo.CommissionProfileVO[] mArray = new edit.common.vo.CommissionProfileVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.CommissionProfileVO) _commissionProfileVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.CommissionProfileVO[] getCommissionProfileVO() 

    /**
     * Method getCommissionProfileVOCount
     */
    public int getCommissionProfileVOCount()
    {
        return _commissionProfileVOList.size();
    } //-- int getCommissionProfileVOCount() 

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
     * Method getRecoveryPercentReturns the value of field
     * 'recoveryPercent'.
     * 
     * @return the value of field 'recoveryPercent'.
     */
    public java.math.BigDecimal getRecoveryPercent()
    {
        return this._recoveryPercent;
    } //-- java.math.BigDecimal getRecoveryPercent() 

    /**
     * Method getTotalCommissionsPaidReturns the value of field
     * 'totalCommissionsPaid'.
     * 
     * @return the value of field 'totalCommissionsPaid'.
     */
    public java.math.BigDecimal getTotalCommissionsPaid()
    {
        return this._totalCommissionsPaid;
    } //-- java.math.BigDecimal getTotalCommissionsPaid() 

    /**
     * Method hasAgentGroupFK
     */
    public boolean hasAgentGroupFK()
    {
        return this._has_agentGroupFK;
    } //-- boolean hasAgentGroupFK() 

    /**
     * Method hasAgentSnapshotDetailPK
     */
    public boolean hasAgentSnapshotDetailPK()
    {
        return this._has_agentSnapshotDetailPK;
    } //-- boolean hasAgentSnapshotDetailPK() 

    /**
     * Method hasAgentSnapshotPK
     */
    public boolean hasAgentSnapshotPK()
    {
        return this._has_agentSnapshotPK;
    } //-- boolean hasAgentSnapshotPK() 

    /**
     * Method hasPlacedAgentFK
     */
    public boolean hasPlacedAgentFK()
    {
        return this._has_placedAgentFK;
    } //-- boolean hasPlacedAgentFK() 

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
     * Method removeAdditionalCompensationVO
     * 
     * @param index
     */
    public edit.common.vo.AdditionalCompensationVO removeAdditionalCompensationVO(int index)
    {
        java.lang.Object obj = _additionalCompensationVOList.elementAt(index);
        _additionalCompensationVOList.removeElementAt(index);
        return (edit.common.vo.AdditionalCompensationVO) obj;
    } //-- edit.common.vo.AdditionalCompensationVO removeAdditionalCompensationVO(int) 

    /**
     * Method removeAgentContractVO
     * 
     * @param index
     */
    public edit.common.vo.AgentContractVO removeAgentContractVO(int index)
    {
        java.lang.Object obj = _agentContractVOList.elementAt(index);
        _agentContractVOList.removeElementAt(index);
        return (edit.common.vo.AgentContractVO) obj;
    } //-- edit.common.vo.AgentContractVO removeAgentContractVO(int) 

    /**
     * Method removeAgentLicenseVO
     * 
     * @param index
     */
    public edit.common.vo.AgentLicenseVO removeAgentLicenseVO(int index)
    {
        java.lang.Object obj = _agentLicenseVOList.elementAt(index);
        _agentLicenseVOList.removeElementAt(index);
        return (edit.common.vo.AgentLicenseVO) obj;
    } //-- edit.common.vo.AgentLicenseVO removeAgentLicenseVO(int) 

    /**
     * Method removeAgentVO
     * 
     * @param index
     */
    public edit.common.vo.AgentVO removeAgentVO(int index)
    {
        java.lang.Object obj = _agentVOList.elementAt(index);
        _agentVOList.removeElementAt(index);
        return (edit.common.vo.AgentVO) obj;
    } //-- edit.common.vo.AgentVO removeAgentVO(int) 

    /**
     * Method removeAllAdditionalCompensationVO
     */
    public void removeAllAdditionalCompensationVO()
    {
        _additionalCompensationVOList.removeAllElements();
    } //-- void removeAllAdditionalCompensationVO() 

    /**
     * Method removeAllAgentContractVO
     */
    public void removeAllAgentContractVO()
    {
        _agentContractVOList.removeAllElements();
    } //-- void removeAllAgentContractVO() 

    /**
     * Method removeAllAgentLicenseVO
     */
    public void removeAllAgentLicenseVO()
    {
        _agentLicenseVOList.removeAllElements();
    } //-- void removeAllAgentLicenseVO() 

    /**
     * Method removeAllAgentVO
     */
    public void removeAllAgentVO()
    {
        _agentVOList.removeAllElements();
    } //-- void removeAllAgentVO() 

    /**
     * Method removeAllClientDetailVO
     */
    public void removeAllClientDetailVO()
    {
        _clientDetailVOList.removeAllElements();
    } //-- void removeAllClientDetailVO() 

    /**
     * Method removeAllCommissionProfileVO
     */
    public void removeAllCommissionProfileVO()
    {
        _commissionProfileVOList.removeAllElements();
    } //-- void removeAllCommissionProfileVO() 

    /**
     * Method removeClientDetailVO
     * 
     * @param index
     */
    public edit.common.vo.ClientDetailVO removeClientDetailVO(int index)
    {
        java.lang.Object obj = _clientDetailVOList.elementAt(index);
        _clientDetailVOList.removeElementAt(index);
        return (edit.common.vo.ClientDetailVO) obj;
    } //-- edit.common.vo.ClientDetailVO removeClientDetailVO(int) 

    /**
     * Method removeCommissionProfileVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionProfileVO removeCommissionProfileVO(int index)
    {
        java.lang.Object obj = _commissionProfileVOList.elementAt(index);
        _commissionProfileVOList.removeElementAt(index);
        return (edit.common.vo.CommissionProfileVO) obj;
    } //-- edit.common.vo.CommissionProfileVO removeCommissionProfileVO(int) 

    /**
     * Method setAdditionalCompensationVO
     * 
     * @param index
     * @param vAdditionalCompensationVO
     */
    public void setAdditionalCompensationVO(int index, edit.common.vo.AdditionalCompensationVO vAdditionalCompensationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _additionalCompensationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAdditionalCompensationVO.setParentVO(this.getClass(), this);
        _additionalCompensationVOList.setElementAt(vAdditionalCompensationVO, index);
    } //-- void setAdditionalCompensationVO(int, edit.common.vo.AdditionalCompensationVO) 

    /**
     * Method setAdditionalCompensationVO
     * 
     * @param additionalCompensationVOArray
     */
    public void setAdditionalCompensationVO(edit.common.vo.AdditionalCompensationVO[] additionalCompensationVOArray)
    {
        //-- copy array
        _additionalCompensationVOList.removeAllElements();
        for (int i = 0; i < additionalCompensationVOArray.length; i++) {
            additionalCompensationVOArray[i].setParentVO(this.getClass(), this);
            _additionalCompensationVOList.addElement(additionalCompensationVOArray[i]);
        }
    } //-- void setAdditionalCompensationVO(edit.common.vo.AdditionalCompensationVO) 

    /**
     * Method setAdvanceAmountSets the value of field
     * 'advanceAmount'.
     * 
     * @param advanceAmount the value of field 'advanceAmount'.
     */
    public void setAdvanceAmount(java.math.BigDecimal advanceAmount)
    {
        this._advanceAmount = advanceAmount;
        
        super.setVoChanged(true);
    } //-- void setAdvanceAmount(java.math.BigDecimal) 

    /**
     * Method setAdvancePercentSets the value of field
     * 'advancePercent'.
     * 
     * @param advancePercent the value of field 'advancePercent'.
     */
    public void setAdvancePercent(java.math.BigDecimal advancePercent)
    {
        this._advancePercent = advancePercent;
        
        super.setVoChanged(true);
    } //-- void setAdvancePercent(java.math.BigDecimal) 

    /**
     * Method setAdvanceRecoverySets the value of field
     * 'advanceRecovery'.
     * 
     * @param advanceRecovery the value of field 'advanceRecovery'.
     */
    public void setAdvanceRecovery(java.math.BigDecimal advanceRecovery)
    {
        this._advanceRecovery = advanceRecovery;
        
        super.setVoChanged(true);
    } //-- void setAdvanceRecovery(java.math.BigDecimal) 

    /**
     * Method setAgentContractVO
     * 
     * @param index
     * @param vAgentContractVO
     */
    public void setAgentContractVO(int index, edit.common.vo.AgentContractVO vAgentContractVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentContractVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAgentContractVO.setParentVO(this.getClass(), this);
        _agentContractVOList.setElementAt(vAgentContractVO, index);
    } //-- void setAgentContractVO(int, edit.common.vo.AgentContractVO) 

    /**
     * Method setAgentContractVO
     * 
     * @param agentContractVOArray
     */
    public void setAgentContractVO(edit.common.vo.AgentContractVO[] agentContractVOArray)
    {
        //-- copy array
        _agentContractVOList.removeAllElements();
        for (int i = 0; i < agentContractVOArray.length; i++) {
            agentContractVOArray[i].setParentVO(this.getClass(), this);
            _agentContractVOList.addElement(agentContractVOArray[i]);
        }
    } //-- void setAgentContractVO(edit.common.vo.AgentContractVO) 

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
     * Method setAgentLicenseVO
     * 
     * @param index
     * @param vAgentLicenseVO
     */
    public void setAgentLicenseVO(int index, edit.common.vo.AgentLicenseVO vAgentLicenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentLicenseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAgentLicenseVO.setParentVO(this.getClass(), this);
        _agentLicenseVOList.setElementAt(vAgentLicenseVO, index);
    } //-- void setAgentLicenseVO(int, edit.common.vo.AgentLicenseVO) 

    /**
     * Method setAgentLicenseVO
     * 
     * @param agentLicenseVOArray
     */
    public void setAgentLicenseVO(edit.common.vo.AgentLicenseVO[] agentLicenseVOArray)
    {
        //-- copy array
        _agentLicenseVOList.removeAllElements();
        for (int i = 0; i < agentLicenseVOArray.length; i++) {
            agentLicenseVOArray[i].setParentVO(this.getClass(), this);
            _agentLicenseVOList.addElement(agentLicenseVOArray[i]);
        }
    } //-- void setAgentLicenseVO(edit.common.vo.AgentLicenseVO) 

    /**
     * Method setAgentSnapshotDetailPKSets the value of field
     * 'agentSnapshotDetailPK'.
     * 
     * @param agentSnapshotDetailPK the value of field
     * 'agentSnapshotDetailPK'.
     */
    public void setAgentSnapshotDetailPK(long agentSnapshotDetailPK)
    {
        this._agentSnapshotDetailPK = agentSnapshotDetailPK;
        
        super.setVoChanged(true);
        this._has_agentSnapshotDetailPK = true;
    } //-- void setAgentSnapshotDetailPK(long) 

    /**
     * Method setAgentSnapshotPKSets the value of field
     * 'agentSnapshotPK'.
     * 
     * @param agentSnapshotPK the value of field 'agentSnapshotPK'.
     */
    public void setAgentSnapshotPK(long agentSnapshotPK)
    {
        this._agentSnapshotPK = agentSnapshotPK;
        
        super.setVoChanged(true);
        this._has_agentSnapshotPK = true;
    } //-- void setAgentSnapshotPK(long) 

    /**
     * Method setAgentVO
     * 
     * @param index
     * @param vAgentVO
     */
    public void setAgentVO(int index, edit.common.vo.AgentVO vAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAgentVO.setParentVO(this.getClass(), this);
        _agentVOList.setElementAt(vAgentVO, index);
    } //-- void setAgentVO(int, edit.common.vo.AgentVO) 

    /**
     * Method setAgentVO
     * 
     * @param agentVOArray
     */
    public void setAgentVO(edit.common.vo.AgentVO[] agentVOArray)
    {
        //-- copy array
        _agentVOList.removeAllElements();
        for (int i = 0; i < agentVOArray.length; i++) {
            agentVOArray[i].setParentVO(this.getClass(), this);
            _agentVOList.addElement(agentVOArray[i]);
        }
    } //-- void setAgentVO(edit.common.vo.AgentVO) 

    /**
     * Method setClientDetailVO
     * 
     * @param index
     * @param vClientDetailVO
     */
    public void setClientDetailVO(int index, edit.common.vo.ClientDetailVO vClientDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vClientDetailVO.setParentVO(this.getClass(), this);
        _clientDetailVOList.setElementAt(vClientDetailVO, index);
    } //-- void setClientDetailVO(int, edit.common.vo.ClientDetailVO) 

    /**
     * Method setClientDetailVO
     * 
     * @param clientDetailVOArray
     */
    public void setClientDetailVO(edit.common.vo.ClientDetailVO[] clientDetailVOArray)
    {
        //-- copy array
        _clientDetailVOList.removeAllElements();
        for (int i = 0; i < clientDetailVOArray.length; i++) {
            clientDetailVOArray[i].setParentVO(this.getClass(), this);
            _clientDetailVOList.addElement(clientDetailVOArray[i]);
        }
    } //-- void setClientDetailVO(edit.common.vo.ClientDetailVO) 

    /**
     * Method setCommissionOverrideAmountSets the value of field
     * 'commissionOverrideAmount'.
     * 
     * @param commissionOverrideAmount the value of field
     * 'commissionOverrideAmount'.
     */
    public void setCommissionOverrideAmount(java.math.BigDecimal commissionOverrideAmount)
    {
        this._commissionOverrideAmount = commissionOverrideAmount;
        
        super.setVoChanged(true);
    } //-- void setCommissionOverrideAmount(java.math.BigDecimal) 

    /**
     * Method setCommissionOverridePercentSets the value of field
     * 'commissionOverridePercent'.
     * 
     * @param commissionOverridePercent the value of field
     * 'commissionOverridePercent'.
     */
    public void setCommissionOverridePercent(java.math.BigDecimal commissionOverridePercent)
    {
        this._commissionOverridePercent = commissionOverridePercent;
        
        super.setVoChanged(true);
    } //-- void setCommissionOverridePercent(java.math.BigDecimal) 

    /**
     * Method setCommissionProfileVO
     * 
     * @param index
     * @param vCommissionProfileVO
     */
    public void setCommissionProfileVO(int index, edit.common.vo.CommissionProfileVO vCommissionProfileVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionProfileVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vCommissionProfileVO.setParentVO(this.getClass(), this);
        _commissionProfileVOList.setElementAt(vCommissionProfileVO, index);
    } //-- void setCommissionProfileVO(int, edit.common.vo.CommissionProfileVO) 

    /**
     * Method setCommissionProfileVO
     * 
     * @param commissionProfileVOArray
     */
    public void setCommissionProfileVO(edit.common.vo.CommissionProfileVO[] commissionProfileVOArray)
    {
        //-- copy array
        _commissionProfileVOList.removeAllElements();
        for (int i = 0; i < commissionProfileVOArray.length; i++) {
            commissionProfileVOArray[i].setParentVO(this.getClass(), this);
            _commissionProfileVOList.addElement(commissionProfileVOArray[i]);
        }
    } //-- void setCommissionProfileVO(edit.common.vo.CommissionProfileVO) 

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
     * Method setRecoveryPercentSets the value of field
     * 'recoveryPercent'.
     * 
     * @param recoveryPercent the value of field 'recoveryPercent'.
     */
    public void setRecoveryPercent(java.math.BigDecimal recoveryPercent)
    {
        this._recoveryPercent = recoveryPercent;
        
        super.setVoChanged(true);
    } //-- void setRecoveryPercent(java.math.BigDecimal) 

    /**
     * Method setTotalCommissionsPaidSets the value of field
     * 'totalCommissionsPaid'.
     * 
     * @param totalCommissionsPaid the value of field
     * 'totalCommissionsPaid'.
     */
    public void setTotalCommissionsPaid(java.math.BigDecimal totalCommissionsPaid)
    {
        this._totalCommissionsPaid = totalCommissionsPaid;
        
        super.setVoChanged(true);
    } //-- void setTotalCommissionsPaid(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AgentSnapshotDetailVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AgentSnapshotDetailVO) Unmarshaller.unmarshal(edit.common.vo.AgentSnapshotDetailVO.class, reader);
    } //-- edit.common.vo.AgentSnapshotDetailVO unmarshal(java.io.Reader) 

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
