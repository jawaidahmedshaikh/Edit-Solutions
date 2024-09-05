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
 * Class GroupSetupVO.
 * 
 * @version $Revision$ $Date$
 */
public class GroupSetupVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _groupSetupPK
     */
    private long _groupSetupPK;

    /**
     * keeps track of state for field: _groupSetupPK
     */
    private boolean _has_groupSetupPK;

    /**
     * Field _groupTypeCT
     */
    private java.lang.String _groupTypeCT;

    /**
     * Field _groupKey
     */
    private java.lang.String _groupKey;

    /**
     * Field _memoCode
     */
    private java.lang.String _memoCode;

    /**
     * Field _premiumTypeCT
     */
    private java.lang.String _premiumTypeCT;

    /**
     * Field _grossNetStatusCT
     */
    private java.lang.String _grossNetStatusCT;

    /**
     * Field _distributionCodeCT
     */
    private java.lang.String _distributionCodeCT;

    /**
     * Field _groupAmount
     */
    private java.math.BigDecimal _groupAmount;

    /**
     * Field _groupPercent
     */
    private java.math.BigDecimal _groupPercent;

    /**
     * Field _employerContribution
     */
    private java.math.BigDecimal _employerContribution;

    /**
     * Field _employeeContribution
     */
    private java.math.BigDecimal _employeeContribution;

    /**
     * Field _withdrawalTypeCT
     */
    private java.lang.String _withdrawalTypeCT;

    /**
     * Field _chargeVOList
     */
    private java.util.Vector _chargeVOList;

    /**
     * Field _contractSetupVOList
     */
    private java.util.Vector _contractSetupVOList;

    /**
     * Field _scheduledEventVOList
     */
    private java.util.Vector _scheduledEventVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public GroupSetupVO() {
        super();
        _chargeVOList = new Vector();
        _contractSetupVOList = new Vector();
        _scheduledEventVOList = new Vector();
    } //-- edit.common.vo.GroupSetupVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addChargeVO
     * 
     * @param vChargeVO
     */
    public void addChargeVO(edit.common.vo.ChargeVO vChargeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vChargeVO.setParentVO(this.getClass(), this);
        _chargeVOList.addElement(vChargeVO);
    } //-- void addChargeVO(edit.common.vo.ChargeVO) 

    /**
     * Method addChargeVO
     * 
     * @param index
     * @param vChargeVO
     */
    public void addChargeVO(int index, edit.common.vo.ChargeVO vChargeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vChargeVO.setParentVO(this.getClass(), this);
        _chargeVOList.insertElementAt(vChargeVO, index);
    } //-- void addChargeVO(int, edit.common.vo.ChargeVO) 

    /**
     * Method addContractSetupVO
     * 
     * @param vContractSetupVO
     */
    public void addContractSetupVO(edit.common.vo.ContractSetupVO vContractSetupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractSetupVO.setParentVO(this.getClass(), this);
        _contractSetupVOList.addElement(vContractSetupVO);
    } //-- void addContractSetupVO(edit.common.vo.ContractSetupVO) 

    /**
     * Method addContractSetupVO
     * 
     * @param index
     * @param vContractSetupVO
     */
    public void addContractSetupVO(int index, edit.common.vo.ContractSetupVO vContractSetupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractSetupVO.setParentVO(this.getClass(), this);
        _contractSetupVOList.insertElementAt(vContractSetupVO, index);
    } //-- void addContractSetupVO(int, edit.common.vo.ContractSetupVO) 

    /**
     * Method addScheduledEventVO
     * 
     * @param vScheduledEventVO
     */
    public void addScheduledEventVO(edit.common.vo.ScheduledEventVO vScheduledEventVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vScheduledEventVO.setParentVO(this.getClass(), this);
        _scheduledEventVOList.addElement(vScheduledEventVO);
    } //-- void addScheduledEventVO(edit.common.vo.ScheduledEventVO) 

    /**
     * Method addScheduledEventVO
     * 
     * @param index
     * @param vScheduledEventVO
     */
    public void addScheduledEventVO(int index, edit.common.vo.ScheduledEventVO vScheduledEventVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vScheduledEventVO.setParentVO(this.getClass(), this);
        _scheduledEventVOList.insertElementAt(vScheduledEventVO, index);
    } //-- void addScheduledEventVO(int, edit.common.vo.ScheduledEventVO) 

    /**
     * Method enumerateChargeVO
     */
    public java.util.Enumeration enumerateChargeVO()
    {
        return _chargeVOList.elements();
    } //-- java.util.Enumeration enumerateChargeVO() 

    /**
     * Method enumerateContractSetupVO
     */
    public java.util.Enumeration enumerateContractSetupVO()
    {
        return _contractSetupVOList.elements();
    } //-- java.util.Enumeration enumerateContractSetupVO() 

    /**
     * Method enumerateScheduledEventVO
     */
    public java.util.Enumeration enumerateScheduledEventVO()
    {
        return _scheduledEventVOList.elements();
    } //-- java.util.Enumeration enumerateScheduledEventVO() 

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
        
        if (obj instanceof GroupSetupVO) {
        
            GroupSetupVO temp = (GroupSetupVO)obj;
            if (this._groupSetupPK != temp._groupSetupPK)
                return false;
            if (this._has_groupSetupPK != temp._has_groupSetupPK)
                return false;
            if (this._groupTypeCT != null) {
                if (temp._groupTypeCT == null) return false;
                else if (!(this._groupTypeCT.equals(temp._groupTypeCT))) 
                    return false;
            }
            else if (temp._groupTypeCT != null)
                return false;
            if (this._groupKey != null) {
                if (temp._groupKey == null) return false;
                else if (!(this._groupKey.equals(temp._groupKey))) 
                    return false;
            }
            else if (temp._groupKey != null)
                return false;
            if (this._memoCode != null) {
                if (temp._memoCode == null) return false;
                else if (!(this._memoCode.equals(temp._memoCode))) 
                    return false;
            }
            else if (temp._memoCode != null)
                return false;
            if (this._premiumTypeCT != null) {
                if (temp._premiumTypeCT == null) return false;
                else if (!(this._premiumTypeCT.equals(temp._premiumTypeCT))) 
                    return false;
            }
            else if (temp._premiumTypeCT != null)
                return false;
            if (this._grossNetStatusCT != null) {
                if (temp._grossNetStatusCT == null) return false;
                else if (!(this._grossNetStatusCT.equals(temp._grossNetStatusCT))) 
                    return false;
            }
            else if (temp._grossNetStatusCT != null)
                return false;
            if (this._distributionCodeCT != null) {
                if (temp._distributionCodeCT == null) return false;
                else if (!(this._distributionCodeCT.equals(temp._distributionCodeCT))) 
                    return false;
            }
            else if (temp._distributionCodeCT != null)
                return false;
            if (this._groupAmount != null) {
                if (temp._groupAmount == null) return false;
                else if (!(this._groupAmount.equals(temp._groupAmount))) 
                    return false;
            }
            else if (temp._groupAmount != null)
                return false;
            if (this._groupPercent != null) {
                if (temp._groupPercent == null) return false;
                else if (!(this._groupPercent.equals(temp._groupPercent))) 
                    return false;
            }
            else if (temp._groupPercent != null)
                return false;
            if (this._employerContribution != null) {
                if (temp._employerContribution == null) return false;
                else if (!(this._employerContribution.equals(temp._employerContribution))) 
                    return false;
            }
            else if (temp._employerContribution != null)
                return false;
            if (this._employeeContribution != null) {
                if (temp._employeeContribution == null) return false;
                else if (!(this._employeeContribution.equals(temp._employeeContribution))) 
                    return false;
            }
            else if (temp._employeeContribution != null)
                return false;
            if (this._withdrawalTypeCT != null) {
                if (temp._withdrawalTypeCT == null) return false;
                else if (!(this._withdrawalTypeCT.equals(temp._withdrawalTypeCT))) 
                    return false;
            }
            else if (temp._withdrawalTypeCT != null)
                return false;
            if (this._chargeVOList != null) {
                if (temp._chargeVOList == null) return false;
                else if (!(this._chargeVOList.equals(temp._chargeVOList))) 
                    return false;
            }
            else if (temp._chargeVOList != null)
                return false;
            if (this._contractSetupVOList != null) {
                if (temp._contractSetupVOList == null) return false;
                else if (!(this._contractSetupVOList.equals(temp._contractSetupVOList))) 
                    return false;
            }
            else if (temp._contractSetupVOList != null)
                return false;
            if (this._scheduledEventVOList != null) {
                if (temp._scheduledEventVOList == null) return false;
                else if (!(this._scheduledEventVOList.equals(temp._scheduledEventVOList))) 
                    return false;
            }
            else if (temp._scheduledEventVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getChargeVO
     * 
     * @param index
     */
    public edit.common.vo.ChargeVO getChargeVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _chargeVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ChargeVO) _chargeVOList.elementAt(index);
    } //-- edit.common.vo.ChargeVO getChargeVO(int) 

    /**
     * Method getChargeVO
     */
    public edit.common.vo.ChargeVO[] getChargeVO()
    {
        int size = _chargeVOList.size();
        edit.common.vo.ChargeVO[] mArray = new edit.common.vo.ChargeVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ChargeVO) _chargeVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ChargeVO[] getChargeVO() 

    /**
     * Method getChargeVOCount
     */
    public int getChargeVOCount()
    {
        return _chargeVOList.size();
    } //-- int getChargeVOCount() 

    /**
     * Method getContractSetupVO
     * 
     * @param index
     */
    public edit.common.vo.ContractSetupVO getContractSetupVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractSetupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ContractSetupVO) _contractSetupVOList.elementAt(index);
    } //-- edit.common.vo.ContractSetupVO getContractSetupVO(int) 

    /**
     * Method getContractSetupVO
     */
    public edit.common.vo.ContractSetupVO[] getContractSetupVO()
    {
        int size = _contractSetupVOList.size();
        edit.common.vo.ContractSetupVO[] mArray = new edit.common.vo.ContractSetupVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ContractSetupVO) _contractSetupVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ContractSetupVO[] getContractSetupVO() 

    /**
     * Method getContractSetupVOCount
     */
    public int getContractSetupVOCount()
    {
        return _contractSetupVOList.size();
    } //-- int getContractSetupVOCount() 

    /**
     * Method getDistributionCodeCTReturns the value of field
     * 'distributionCodeCT'.
     * 
     * @return the value of field 'distributionCodeCT'.
     */
    public java.lang.String getDistributionCodeCT()
    {
        return this._distributionCodeCT;
    } //-- java.lang.String getDistributionCodeCT() 

    /**
     * Method getEmployeeContributionReturns the value of field
     * 'employeeContribution'.
     * 
     * @return the value of field 'employeeContribution'.
     */
    public java.math.BigDecimal getEmployeeContribution()
    {
        return this._employeeContribution;
    } //-- java.math.BigDecimal getEmployeeContribution() 

    /**
     * Method getEmployerContributionReturns the value of field
     * 'employerContribution'.
     * 
     * @return the value of field 'employerContribution'.
     */
    public java.math.BigDecimal getEmployerContribution()
    {
        return this._employerContribution;
    } //-- java.math.BigDecimal getEmployerContribution() 

    /**
     * Method getGrossNetStatusCTReturns the value of field
     * 'grossNetStatusCT'.
     * 
     * @return the value of field 'grossNetStatusCT'.
     */
    public java.lang.String getGrossNetStatusCT()
    {
        return this._grossNetStatusCT;
    } //-- java.lang.String getGrossNetStatusCT() 

    /**
     * Method getGroupAmountReturns the value of field
     * 'groupAmount'.
     * 
     * @return the value of field 'groupAmount'.
     */
    public java.math.BigDecimal getGroupAmount()
    {
        return this._groupAmount;
    } //-- java.math.BigDecimal getGroupAmount() 

    /**
     * Method getGroupKeyReturns the value of field 'groupKey'.
     * 
     * @return the value of field 'groupKey'.
     */
    public java.lang.String getGroupKey()
    {
        return this._groupKey;
    } //-- java.lang.String getGroupKey() 

    /**
     * Method getGroupPercentReturns the value of field
     * 'groupPercent'.
     * 
     * @return the value of field 'groupPercent'.
     */
    public java.math.BigDecimal getGroupPercent()
    {
        return this._groupPercent;
    } //-- java.math.BigDecimal getGroupPercent() 

    /**
     * Method getGroupSetupPKReturns the value of field
     * 'groupSetupPK'.
     * 
     * @return the value of field 'groupSetupPK'.
     */
    public long getGroupSetupPK()
    {
        return this._groupSetupPK;
    } //-- long getGroupSetupPK() 

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
     * Method getMemoCodeReturns the value of field 'memoCode'.
     * 
     * @return the value of field 'memoCode'.
     */
    public java.lang.String getMemoCode()
    {
        return this._memoCode;
    } //-- java.lang.String getMemoCode() 

    /**
     * Method getPremiumTypeCTReturns the value of field
     * 'premiumTypeCT'.
     * 
     * @return the value of field 'premiumTypeCT'.
     */
    public java.lang.String getPremiumTypeCT()
    {
        return this._premiumTypeCT;
    } //-- java.lang.String getPremiumTypeCT() 

    /**
     * Method getScheduledEventVO
     * 
     * @param index
     */
    public edit.common.vo.ScheduledEventVO getScheduledEventVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _scheduledEventVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ScheduledEventVO) _scheduledEventVOList.elementAt(index);
    } //-- edit.common.vo.ScheduledEventVO getScheduledEventVO(int) 

    /**
     * Method getScheduledEventVO
     */
    public edit.common.vo.ScheduledEventVO[] getScheduledEventVO()
    {
        int size = _scheduledEventVOList.size();
        edit.common.vo.ScheduledEventVO[] mArray = new edit.common.vo.ScheduledEventVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ScheduledEventVO) _scheduledEventVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ScheduledEventVO[] getScheduledEventVO() 

    /**
     * Method getScheduledEventVOCount
     */
    public int getScheduledEventVOCount()
    {
        return _scheduledEventVOList.size();
    } //-- int getScheduledEventVOCount() 

    /**
     * Method getWithdrawalTypeCTReturns the value of field
     * 'withdrawalTypeCT'.
     * 
     * @return the value of field 'withdrawalTypeCT'.
     */
    public java.lang.String getWithdrawalTypeCT()
    {
        return this._withdrawalTypeCT;
    } //-- java.lang.String getWithdrawalTypeCT() 

    /**
     * Method hasGroupSetupPK
     */
    public boolean hasGroupSetupPK()
    {
        return this._has_groupSetupPK;
    } //-- boolean hasGroupSetupPK() 

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
     * Method removeAllChargeVO
     */
    public void removeAllChargeVO()
    {
        _chargeVOList.removeAllElements();
    } //-- void removeAllChargeVO() 

    /**
     * Method removeAllContractSetupVO
     */
    public void removeAllContractSetupVO()
    {
        _contractSetupVOList.removeAllElements();
    } //-- void removeAllContractSetupVO() 

    /**
     * Method removeAllScheduledEventVO
     */
    public void removeAllScheduledEventVO()
    {
        _scheduledEventVOList.removeAllElements();
    } //-- void removeAllScheduledEventVO() 

    /**
     * Method removeChargeVO
     * 
     * @param index
     */
    public edit.common.vo.ChargeVO removeChargeVO(int index)
    {
        java.lang.Object obj = _chargeVOList.elementAt(index);
        _chargeVOList.removeElementAt(index);
        return (edit.common.vo.ChargeVO) obj;
    } //-- edit.common.vo.ChargeVO removeChargeVO(int) 

    /**
     * Method removeContractSetupVO
     * 
     * @param index
     */
    public edit.common.vo.ContractSetupVO removeContractSetupVO(int index)
    {
        java.lang.Object obj = _contractSetupVOList.elementAt(index);
        _contractSetupVOList.removeElementAt(index);
        return (edit.common.vo.ContractSetupVO) obj;
    } //-- edit.common.vo.ContractSetupVO removeContractSetupVO(int) 

    /**
     * Method removeScheduledEventVO
     * 
     * @param index
     */
    public edit.common.vo.ScheduledEventVO removeScheduledEventVO(int index)
    {
        java.lang.Object obj = _scheduledEventVOList.elementAt(index);
        _scheduledEventVOList.removeElementAt(index);
        return (edit.common.vo.ScheduledEventVO) obj;
    } //-- edit.common.vo.ScheduledEventVO removeScheduledEventVO(int) 

    /**
     * Method setChargeVO
     * 
     * @param index
     * @param vChargeVO
     */
    public void setChargeVO(int index, edit.common.vo.ChargeVO vChargeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _chargeVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vChargeVO.setParentVO(this.getClass(), this);
        _chargeVOList.setElementAt(vChargeVO, index);
    } //-- void setChargeVO(int, edit.common.vo.ChargeVO) 

    /**
     * Method setChargeVO
     * 
     * @param chargeVOArray
     */
    public void setChargeVO(edit.common.vo.ChargeVO[] chargeVOArray)
    {
        //-- copy array
        _chargeVOList.removeAllElements();
        for (int i = 0; i < chargeVOArray.length; i++) {
            chargeVOArray[i].setParentVO(this.getClass(), this);
            _chargeVOList.addElement(chargeVOArray[i]);
        }
    } //-- void setChargeVO(edit.common.vo.ChargeVO) 

    /**
     * Method setContractSetupVO
     * 
     * @param index
     * @param vContractSetupVO
     */
    public void setContractSetupVO(int index, edit.common.vo.ContractSetupVO vContractSetupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractSetupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vContractSetupVO.setParentVO(this.getClass(), this);
        _contractSetupVOList.setElementAt(vContractSetupVO, index);
    } //-- void setContractSetupVO(int, edit.common.vo.ContractSetupVO) 

    /**
     * Method setContractSetupVO
     * 
     * @param contractSetupVOArray
     */
    public void setContractSetupVO(edit.common.vo.ContractSetupVO[] contractSetupVOArray)
    {
        //-- copy array
        _contractSetupVOList.removeAllElements();
        for (int i = 0; i < contractSetupVOArray.length; i++) {
            contractSetupVOArray[i].setParentVO(this.getClass(), this);
            _contractSetupVOList.addElement(contractSetupVOArray[i]);
        }
    } //-- void setContractSetupVO(edit.common.vo.ContractSetupVO) 

    /**
     * Method setDistributionCodeCTSets the value of field
     * 'distributionCodeCT'.
     * 
     * @param distributionCodeCT the value of field
     * 'distributionCodeCT'.
     */
    public void setDistributionCodeCT(java.lang.String distributionCodeCT)
    {
        this._distributionCodeCT = distributionCodeCT;
        
        super.setVoChanged(true);
    } //-- void setDistributionCodeCT(java.lang.String) 

    /**
     * Method setEmployeeContributionSets the value of field
     * 'employeeContribution'.
     * 
     * @param employeeContribution the value of field
     * 'employeeContribution'.
     */
    public void setEmployeeContribution(java.math.BigDecimal employeeContribution)
    {
        this._employeeContribution = employeeContribution;
        
        super.setVoChanged(true);
    } //-- void setEmployeeContribution(java.math.BigDecimal) 

    /**
     * Method setEmployerContributionSets the value of field
     * 'employerContribution'.
     * 
     * @param employerContribution the value of field
     * 'employerContribution'.
     */
    public void setEmployerContribution(java.math.BigDecimal employerContribution)
    {
        this._employerContribution = employerContribution;
        
        super.setVoChanged(true);
    } //-- void setEmployerContribution(java.math.BigDecimal) 

    /**
     * Method setGrossNetStatusCTSets the value of field
     * 'grossNetStatusCT'.
     * 
     * @param grossNetStatusCT the value of field 'grossNetStatusCT'
     */
    public void setGrossNetStatusCT(java.lang.String grossNetStatusCT)
    {
        this._grossNetStatusCT = grossNetStatusCT;
        
        super.setVoChanged(true);
    } //-- void setGrossNetStatusCT(java.lang.String) 

    /**
     * Method setGroupAmountSets the value of field 'groupAmount'.
     * 
     * @param groupAmount the value of field 'groupAmount'.
     */
    public void setGroupAmount(java.math.BigDecimal groupAmount)
    {
        this._groupAmount = groupAmount;
        
        super.setVoChanged(true);
    } //-- void setGroupAmount(java.math.BigDecimal) 

    /**
     * Method setGroupKeySets the value of field 'groupKey'.
     * 
     * @param groupKey the value of field 'groupKey'.
     */
    public void setGroupKey(java.lang.String groupKey)
    {
        this._groupKey = groupKey;
        
        super.setVoChanged(true);
    } //-- void setGroupKey(java.lang.String) 

    /**
     * Method setGroupPercentSets the value of field
     * 'groupPercent'.
     * 
     * @param groupPercent the value of field 'groupPercent'.
     */
    public void setGroupPercent(java.math.BigDecimal groupPercent)
    {
        this._groupPercent = groupPercent;
        
        super.setVoChanged(true);
    } //-- void setGroupPercent(java.math.BigDecimal) 

    /**
     * Method setGroupSetupPKSets the value of field
     * 'groupSetupPK'.
     * 
     * @param groupSetupPK the value of field 'groupSetupPK'.
     */
    public void setGroupSetupPK(long groupSetupPK)
    {
        this._groupSetupPK = groupSetupPK;
        
        super.setVoChanged(true);
        this._has_groupSetupPK = true;
    } //-- void setGroupSetupPK(long) 

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
     * Method setMemoCodeSets the value of field 'memoCode'.
     * 
     * @param memoCode the value of field 'memoCode'.
     */
    public void setMemoCode(java.lang.String memoCode)
    {
        this._memoCode = memoCode;
        
        super.setVoChanged(true);
    } //-- void setMemoCode(java.lang.String) 

    /**
     * Method setPremiumTypeCTSets the value of field
     * 'premiumTypeCT'.
     * 
     * @param premiumTypeCT the value of field 'premiumTypeCT'.
     */
    public void setPremiumTypeCT(java.lang.String premiumTypeCT)
    {
        this._premiumTypeCT = premiumTypeCT;
        
        super.setVoChanged(true);
    } //-- void setPremiumTypeCT(java.lang.String) 

    /**
     * Method setScheduledEventVO
     * 
     * @param index
     * @param vScheduledEventVO
     */
    public void setScheduledEventVO(int index, edit.common.vo.ScheduledEventVO vScheduledEventVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _scheduledEventVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vScheduledEventVO.setParentVO(this.getClass(), this);
        _scheduledEventVOList.setElementAt(vScheduledEventVO, index);
    } //-- void setScheduledEventVO(int, edit.common.vo.ScheduledEventVO) 

    /**
     * Method setScheduledEventVO
     * 
     * @param scheduledEventVOArray
     */
    public void setScheduledEventVO(edit.common.vo.ScheduledEventVO[] scheduledEventVOArray)
    {
        //-- copy array
        _scheduledEventVOList.removeAllElements();
        for (int i = 0; i < scheduledEventVOArray.length; i++) {
            scheduledEventVOArray[i].setParentVO(this.getClass(), this);
            _scheduledEventVOList.addElement(scheduledEventVOArray[i]);
        }
    } //-- void setScheduledEventVO(edit.common.vo.ScheduledEventVO) 

    /**
     * Method setWithdrawalTypeCTSets the value of field
     * 'withdrawalTypeCT'.
     * 
     * @param withdrawalTypeCT the value of field 'withdrawalTypeCT'
     */
    public void setWithdrawalTypeCT(java.lang.String withdrawalTypeCT)
    {
        this._withdrawalTypeCT = withdrawalTypeCT;
        
        super.setVoChanged(true);
    } //-- void setWithdrawalTypeCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.GroupSetupVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.GroupSetupVO) Unmarshaller.unmarshal(edit.common.vo.GroupSetupVO.class, reader);
    } //-- edit.common.vo.GroupSetupVO unmarshal(java.io.Reader) 

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
