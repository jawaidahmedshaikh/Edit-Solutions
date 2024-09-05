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
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class AgentVO.
 * 
 * @version $Revision$ $Date$
 */
public class AgentVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _agentPK
     */
    private long _agentPK;

    /**
     * keeps track of state for field: _agentPK
     */
    private boolean _has_agentPK;

    /**
     * Field _hireDate
     */
    private java.lang.String _hireDate;

    /**
     * Field _terminationDate
     */
    private java.lang.String _terminationDate;

    /**
     * Field _agentStatusCT
     */
    private java.lang.String _agentStatusCT;

    /**
     * Field _agentTypeCT
     */
    private java.lang.String _agentTypeCT;

    /**
     * Field _withholdingStatus
     */
    private java.lang.String _withholdingStatus;

    /**
     * Field _department
     */
    private java.lang.String _department;

    /**
     * Field _region
     */
    private java.lang.String _region;

    /**
     * Field _branch
     */
    private java.lang.String _branch;

    /**
     * Field _intDebitBalStatusCT
     */
    private java.lang.String _intDebitBalStatusCT;

    /**
     * Field _holdCommStatus
     */
    private java.lang.String _holdCommStatus;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _disbursementAddressTypeCT
     */
    private java.lang.String _disbursementAddressTypeCT;

    /**
     * Field _correspondenceAddressTypeCT
     */
    private java.lang.String _correspondenceAddressTypeCT;

    /**
     * Field _agentNumber
     */
    private java.lang.String _agentNumber;

    /**
     * Field _companyFK
     */
    private long _companyFK;

    /**
     * keeps track of state for field: _companyFK
     */
    private boolean _has_companyFK;

    /**
     * Field _agentContractVOList
     */
    private java.util.Vector _agentContractVOList;

    /**
     * Field _agentLicenseVOList
     */
    private java.util.Vector _agentLicenseVOList;

    /**
     * Field _agentNoteVOList
     */
    private java.util.Vector _agentNoteVOList;

    /**
     * Field _checkAdjustmentVOList
     */
    private java.util.Vector _checkAdjustmentVOList;

    /**
     * Field _redirectVOList
     */
    private java.util.Vector _redirectVOList;

    /**
     * Field _vestingVOList
     */
    private java.util.Vector _vestingVOList;

    /**
     * Field _agentRequirementVOList
     */
    private java.util.Vector _agentRequirementVOList;

    /**
     * Field _clientRoleVOList
     */
    private java.util.Vector _clientRoleVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public AgentVO() {
        super();
        _agentContractVOList = new Vector();
        _agentLicenseVOList = new Vector();
        _agentNoteVOList = new Vector();
        _checkAdjustmentVOList = new Vector();
        _redirectVOList = new Vector();
        _vestingVOList = new Vector();
        _agentRequirementVOList = new Vector();
        _clientRoleVOList = new Vector();
    } //-- edit.common.vo.AgentVO()


      //-----------/
     //- Methods -/
    //-----------/

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
     * Method addAgentNoteVO
     * 
     * @param vAgentNoteVO
     */
    public void addAgentNoteVO(edit.common.vo.AgentNoteVO vAgentNoteVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentNoteVO.setParentVO(this.getClass(), this);
        _agentNoteVOList.addElement(vAgentNoteVO);
    } //-- void addAgentNoteVO(edit.common.vo.AgentNoteVO) 

    /**
     * Method addAgentNoteVO
     * 
     * @param index
     * @param vAgentNoteVO
     */
    public void addAgentNoteVO(int index, edit.common.vo.AgentNoteVO vAgentNoteVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentNoteVO.setParentVO(this.getClass(), this);
        _agentNoteVOList.insertElementAt(vAgentNoteVO, index);
    } //-- void addAgentNoteVO(int, edit.common.vo.AgentNoteVO) 

    /**
     * Method addAgentRequirementVO
     * 
     * @param vAgentRequirementVO
     */
    public void addAgentRequirementVO(edit.common.vo.AgentRequirementVO vAgentRequirementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentRequirementVO.setParentVO(this.getClass(), this);
        _agentRequirementVOList.addElement(vAgentRequirementVO);
    } //-- void addAgentRequirementVO(edit.common.vo.AgentRequirementVO) 

    /**
     * Method addAgentRequirementVO
     * 
     * @param index
     * @param vAgentRequirementVO
     */
    public void addAgentRequirementVO(int index, edit.common.vo.AgentRequirementVO vAgentRequirementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentRequirementVO.setParentVO(this.getClass(), this);
        _agentRequirementVOList.insertElementAt(vAgentRequirementVO, index);
    } //-- void addAgentRequirementVO(int, edit.common.vo.AgentRequirementVO) 

    /**
     * Method addCheckAdjustmentVO
     * 
     * @param vCheckAdjustmentVO
     */
    public void addCheckAdjustmentVO(edit.common.vo.CheckAdjustmentVO vCheckAdjustmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCheckAdjustmentVO.setParentVO(this.getClass(), this);
        _checkAdjustmentVOList.addElement(vCheckAdjustmentVO);
    } //-- void addCheckAdjustmentVO(edit.common.vo.CheckAdjustmentVO) 

    /**
     * Method addCheckAdjustmentVO
     * 
     * @param index
     * @param vCheckAdjustmentVO
     */
    public void addCheckAdjustmentVO(int index, edit.common.vo.CheckAdjustmentVO vCheckAdjustmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCheckAdjustmentVO.setParentVO(this.getClass(), this);
        _checkAdjustmentVOList.insertElementAt(vCheckAdjustmentVO, index);
    } //-- void addCheckAdjustmentVO(int, edit.common.vo.CheckAdjustmentVO) 

    /**
     * Method addClientRoleVO
     * 
     * @param vClientRoleVO
     */
    public void addClientRoleVO(edit.common.vo.ClientRoleVO vClientRoleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientRoleVO.setParentVO(this.getClass(), this);
        _clientRoleVOList.addElement(vClientRoleVO);
    } //-- void addClientRoleVO(edit.common.vo.ClientRoleVO) 

    /**
     * Method addClientRoleVO
     * 
     * @param index
     * @param vClientRoleVO
     */
    public void addClientRoleVO(int index, edit.common.vo.ClientRoleVO vClientRoleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientRoleVO.setParentVO(this.getClass(), this);
        _clientRoleVOList.insertElementAt(vClientRoleVO, index);
    } //-- void addClientRoleVO(int, edit.common.vo.ClientRoleVO) 

    /**
     * Method addRedirectVO
     * 
     * @param vRedirectVO
     */
    public void addRedirectVO(edit.common.vo.RedirectVO vRedirectVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vRedirectVO.setParentVO(this.getClass(), this);
        _redirectVOList.addElement(vRedirectVO);
    } //-- void addRedirectVO(edit.common.vo.RedirectVO) 

    /**
     * Method addRedirectVO
     * 
     * @param index
     * @param vRedirectVO
     */
    public void addRedirectVO(int index, edit.common.vo.RedirectVO vRedirectVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vRedirectVO.setParentVO(this.getClass(), this);
        _redirectVOList.insertElementAt(vRedirectVO, index);
    } //-- void addRedirectVO(int, edit.common.vo.RedirectVO) 

    /**
     * Method addVestingVO
     * 
     * @param vVestingVO
     */
    public void addVestingVO(edit.common.vo.VestingVO vVestingVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vVestingVO.setParentVO(this.getClass(), this);
        _vestingVOList.addElement(vVestingVO);
    } //-- void addVestingVO(edit.common.vo.VestingVO) 

    /**
     * Method addVestingVO
     * 
     * @param index
     * @param vVestingVO
     */
    public void addVestingVO(int index, edit.common.vo.VestingVO vVestingVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vVestingVO.setParentVO(this.getClass(), this);
        _vestingVOList.insertElementAt(vVestingVO, index);
    } //-- void addVestingVO(int, edit.common.vo.VestingVO) 

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
     * Method enumerateAgentNoteVO
     */
    public java.util.Enumeration enumerateAgentNoteVO()
    {
        return _agentNoteVOList.elements();
    } //-- java.util.Enumeration enumerateAgentNoteVO() 

    /**
     * Method enumerateAgentRequirementVO
     */
    public java.util.Enumeration enumerateAgentRequirementVO()
    {
        return _agentRequirementVOList.elements();
    } //-- java.util.Enumeration enumerateAgentRequirementVO() 

    /**
     * Method enumerateCheckAdjustmentVO
     */
    public java.util.Enumeration enumerateCheckAdjustmentVO()
    {
        return _checkAdjustmentVOList.elements();
    } //-- java.util.Enumeration enumerateCheckAdjustmentVO() 

    /**
     * Method enumerateClientRoleVO
     */
    public java.util.Enumeration enumerateClientRoleVO()
    {
        return _clientRoleVOList.elements();
    } //-- java.util.Enumeration enumerateClientRoleVO() 

    /**
     * Method enumerateRedirectVO
     */
    public java.util.Enumeration enumerateRedirectVO()
    {
        return _redirectVOList.elements();
    } //-- java.util.Enumeration enumerateRedirectVO() 

    /**
     * Method enumerateVestingVO
     */
    public java.util.Enumeration enumerateVestingVO()
    {
        return _vestingVOList.elements();
    } //-- java.util.Enumeration enumerateVestingVO() 

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
        
        if (obj instanceof AgentVO) {
        
            AgentVO temp = (AgentVO)obj;
            if (this._agentPK != temp._agentPK)
                return false;
            if (this._has_agentPK != temp._has_agentPK)
                return false;
            if (this._hireDate != null) {
                if (temp._hireDate == null) return false;
                else if (!(this._hireDate.equals(temp._hireDate))) 
                    return false;
            }
            else if (temp._hireDate != null)
                return false;
            if (this._terminationDate != null) {
                if (temp._terminationDate == null) return false;
                else if (!(this._terminationDate.equals(temp._terminationDate))) 
                    return false;
            }
            else if (temp._terminationDate != null)
                return false;
            if (this._agentStatusCT != null) {
                if (temp._agentStatusCT == null) return false;
                else if (!(this._agentStatusCT.equals(temp._agentStatusCT))) 
                    return false;
            }
            else if (temp._agentStatusCT != null)
                return false;
            if (this._agentTypeCT != null) {
                if (temp._agentTypeCT == null) return false;
                else if (!(this._agentTypeCT.equals(temp._agentTypeCT))) 
                    return false;
            }
            else if (temp._agentTypeCT != null)
                return false;
            if (this._withholdingStatus != null) {
                if (temp._withholdingStatus == null) return false;
                else if (!(this._withholdingStatus.equals(temp._withholdingStatus))) 
                    return false;
            }
            else if (temp._withholdingStatus != null)
                return false;
            if (this._department != null) {
                if (temp._department == null) return false;
                else if (!(this._department.equals(temp._department))) 
                    return false;
            }
            else if (temp._department != null)
                return false;
            if (this._region != null) {
                if (temp._region == null) return false;
                else if (!(this._region.equals(temp._region))) 
                    return false;
            }
            else if (temp._region != null)
                return false;
            if (this._branch != null) {
                if (temp._branch == null) return false;
                else if (!(this._branch.equals(temp._branch))) 
                    return false;
            }
            else if (temp._branch != null)
                return false;
            if (this._intDebitBalStatusCT != null) {
                if (temp._intDebitBalStatusCT == null) return false;
                else if (!(this._intDebitBalStatusCT.equals(temp._intDebitBalStatusCT))) 
                    return false;
            }
            else if (temp._intDebitBalStatusCT != null)
                return false;
            if (this._holdCommStatus != null) {
                if (temp._holdCommStatus == null) return false;
                else if (!(this._holdCommStatus.equals(temp._holdCommStatus))) 
                    return false;
            }
            else if (temp._holdCommStatus != null)
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
            if (this._disbursementAddressTypeCT != null) {
                if (temp._disbursementAddressTypeCT == null) return false;
                else if (!(this._disbursementAddressTypeCT.equals(temp._disbursementAddressTypeCT))) 
                    return false;
            }
            else if (temp._disbursementAddressTypeCT != null)
                return false;
            if (this._correspondenceAddressTypeCT != null) {
                if (temp._correspondenceAddressTypeCT == null) return false;
                else if (!(this._correspondenceAddressTypeCT.equals(temp._correspondenceAddressTypeCT))) 
                    return false;
            }
            else if (temp._correspondenceAddressTypeCT != null)
                return false;
            if (this._agentNumber != null) {
                if (temp._agentNumber == null) return false;
                else if (!(this._agentNumber.equals(temp._agentNumber))) 
                    return false;
            }
            else if (temp._agentNumber != null)
                return false;
            if (this._companyFK != temp._companyFK)
                return false;
            if (this._has_companyFK != temp._has_companyFK)
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
            if (this._agentNoteVOList != null) {
                if (temp._agentNoteVOList == null) return false;
                else if (!(this._agentNoteVOList.equals(temp._agentNoteVOList))) 
                    return false;
            }
            else if (temp._agentNoteVOList != null)
                return false;
            if (this._checkAdjustmentVOList != null) {
                if (temp._checkAdjustmentVOList == null) return false;
                else if (!(this._checkAdjustmentVOList.equals(temp._checkAdjustmentVOList))) 
                    return false;
            }
            else if (temp._checkAdjustmentVOList != null)
                return false;
            if (this._redirectVOList != null) {
                if (temp._redirectVOList == null) return false;
                else if (!(this._redirectVOList.equals(temp._redirectVOList))) 
                    return false;
            }
            else if (temp._redirectVOList != null)
                return false;
            if (this._vestingVOList != null) {
                if (temp._vestingVOList == null) return false;
                else if (!(this._vestingVOList.equals(temp._vestingVOList))) 
                    return false;
            }
            else if (temp._vestingVOList != null)
                return false;
            if (this._agentRequirementVOList != null) {
                if (temp._agentRequirementVOList == null) return false;
                else if (!(this._agentRequirementVOList.equals(temp._agentRequirementVOList))) 
                    return false;
            }
            else if (temp._agentRequirementVOList != null)
                return false;
            if (this._clientRoleVOList != null) {
                if (temp._clientRoleVOList == null) return false;
                else if (!(this._clientRoleVOList.equals(temp._clientRoleVOList))) 
                    return false;
            }
            else if (temp._clientRoleVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getAgentNoteVO
     * 
     * @param index
     */
    public edit.common.vo.AgentNoteVO getAgentNoteVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentNoteVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AgentNoteVO) _agentNoteVOList.elementAt(index);
    } //-- edit.common.vo.AgentNoteVO getAgentNoteVO(int) 

    /**
     * Method getAgentNoteVO
     */
    public edit.common.vo.AgentNoteVO[] getAgentNoteVO()
    {
        int size = _agentNoteVOList.size();
        edit.common.vo.AgentNoteVO[] mArray = new edit.common.vo.AgentNoteVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AgentNoteVO) _agentNoteVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AgentNoteVO[] getAgentNoteVO() 

    /**
     * Method getAgentNoteVOCount
     */
    public int getAgentNoteVOCount()
    {
        return _agentNoteVOList.size();
    } //-- int getAgentNoteVOCount() 

    /**
     * Method getAgentNumberReturns the value of field
     * 'agentNumber'.
     * 
     * @return the value of field 'agentNumber'.
     */
    public java.lang.String getAgentNumber()
    {
        return this._agentNumber;
    } //-- java.lang.String getAgentNumber() 

    /**
     * Method getAgentPKReturns the value of field 'agentPK'.
     * 
     * @return the value of field 'agentPK'.
     */
    public long getAgentPK()
    {
        return this._agentPK;
    } //-- long getAgentPK() 

    /**
     * Method getAgentRequirementVO
     * 
     * @param index
     */
    public edit.common.vo.AgentRequirementVO getAgentRequirementVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentRequirementVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AgentRequirementVO) _agentRequirementVOList.elementAt(index);
    } //-- edit.common.vo.AgentRequirementVO getAgentRequirementVO(int) 

    /**
     * Method getAgentRequirementVO
     */
    public edit.common.vo.AgentRequirementVO[] getAgentRequirementVO()
    {
        int size = _agentRequirementVOList.size();
        edit.common.vo.AgentRequirementVO[] mArray = new edit.common.vo.AgentRequirementVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AgentRequirementVO) _agentRequirementVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AgentRequirementVO[] getAgentRequirementVO() 

    /**
     * Method getAgentRequirementVOCount
     */
    public int getAgentRequirementVOCount()
    {
        return _agentRequirementVOList.size();
    } //-- int getAgentRequirementVOCount() 

    /**
     * Method getAgentStatusCTReturns the value of field
     * 'agentStatusCT'.
     * 
     * @return the value of field 'agentStatusCT'.
     */
    public java.lang.String getAgentStatusCT()
    {
        return this._agentStatusCT;
    } //-- java.lang.String getAgentStatusCT() 

    /**
     * Method getAgentTypeCTReturns the value of field
     * 'agentTypeCT'.
     * 
     * @return the value of field 'agentTypeCT'.
     */
    public java.lang.String getAgentTypeCT()
    {
        return this._agentTypeCT;
    } //-- java.lang.String getAgentTypeCT() 

    /**
     * Method getBranchReturns the value of field 'branch'.
     * 
     * @return the value of field 'branch'.
     */
    public java.lang.String getBranch()
    {
        return this._branch;
    } //-- java.lang.String getBranch() 

    /**
     * Method getCheckAdjustmentVO
     * 
     * @param index
     */
    public edit.common.vo.CheckAdjustmentVO getCheckAdjustmentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _checkAdjustmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.CheckAdjustmentVO) _checkAdjustmentVOList.elementAt(index);
    } //-- edit.common.vo.CheckAdjustmentVO getCheckAdjustmentVO(int) 

    /**
     * Method getCheckAdjustmentVO
     */
    public edit.common.vo.CheckAdjustmentVO[] getCheckAdjustmentVO()
    {
        int size = _checkAdjustmentVOList.size();
        edit.common.vo.CheckAdjustmentVO[] mArray = new edit.common.vo.CheckAdjustmentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.CheckAdjustmentVO) _checkAdjustmentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.CheckAdjustmentVO[] getCheckAdjustmentVO() 

    /**
     * Method getCheckAdjustmentVOCount
     */
    public int getCheckAdjustmentVOCount()
    {
        return _checkAdjustmentVOList.size();
    } //-- int getCheckAdjustmentVOCount() 

    /**
     * Method getClientRoleVO
     * 
     * @param index
     */
    public edit.common.vo.ClientRoleVO getClientRoleVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientRoleVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ClientRoleVO) _clientRoleVOList.elementAt(index);
    } //-- edit.common.vo.ClientRoleVO getClientRoleVO(int) 

    /**
     * Method getClientRoleVO
     */
    public edit.common.vo.ClientRoleVO[] getClientRoleVO()
    {
        int size = _clientRoleVOList.size();
        edit.common.vo.ClientRoleVO[] mArray = new edit.common.vo.ClientRoleVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ClientRoleVO) _clientRoleVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ClientRoleVO[] getClientRoleVO() 

    /**
     * Method getClientRoleVOCount
     */
    public int getClientRoleVOCount()
    {
        return _clientRoleVOList.size();
    } //-- int getClientRoleVOCount() 

    /**
     * Method getCompanyFKReturns the value of field 'companyFK'.
     * 
     * @return the value of field 'companyFK'.
     */
    public long getCompanyFK()
    {
        return this._companyFK;
    } //-- long getCompanyFK() 

    /**
     * Method getCorrespondenceAddressTypeCTReturns the value of
     * field 'correspondenceAddressTypeCT'.
     * 
     * @return the value of field 'correspondenceAddressTypeCT'.
     */
    public java.lang.String getCorrespondenceAddressTypeCT()
    {
        return this._correspondenceAddressTypeCT;
    } //-- java.lang.String getCorrespondenceAddressTypeCT() 

    /**
     * Method getDepartmentReturns the value of field 'department'.
     * 
     * @return the value of field 'department'.
     */
    public java.lang.String getDepartment()
    {
        return this._department;
    } //-- java.lang.String getDepartment() 

    /**
     * Method getDisbursementAddressTypeCTReturns the value of
     * field 'disbursementAddressTypeCT'.
     * 
     * @return the value of field 'disbursementAddressTypeCT'.
     */
    public java.lang.String getDisbursementAddressTypeCT()
    {
        return this._disbursementAddressTypeCT;
    } //-- java.lang.String getDisbursementAddressTypeCT() 

    /**
     * Method getHireDateReturns the value of field 'hireDate'.
     * 
     * @return the value of field 'hireDate'.
     */
    public java.lang.String getHireDate()
    {
        return this._hireDate;
    } //-- java.lang.String getHireDate() 

    /**
     * Method getHoldCommStatusReturns the value of field
     * 'holdCommStatus'.
     * 
     * @return the value of field 'holdCommStatus'.
     */
    public java.lang.String getHoldCommStatus()
    {
        return this._holdCommStatus;
    } //-- java.lang.String getHoldCommStatus() 

    /**
     * Method getIntDebitBalStatusCTReturns the value of field
     * 'intDebitBalStatusCT'.
     * 
     * @return the value of field 'intDebitBalStatusCT'.
     */
    public java.lang.String getIntDebitBalStatusCT()
    {
        return this._intDebitBalStatusCT;
    } //-- java.lang.String getIntDebitBalStatusCT() 

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
     * Method getRedirectVO
     * 
     * @param index
     */
    public edit.common.vo.RedirectVO getRedirectVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _redirectVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.RedirectVO) _redirectVOList.elementAt(index);
    } //-- edit.common.vo.RedirectVO getRedirectVO(int) 

    /**
     * Method getRedirectVO
     */
    public edit.common.vo.RedirectVO[] getRedirectVO()
    {
        int size = _redirectVOList.size();
        edit.common.vo.RedirectVO[] mArray = new edit.common.vo.RedirectVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.RedirectVO) _redirectVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.RedirectVO[] getRedirectVO() 

    /**
     * Method getRedirectVOCount
     */
    public int getRedirectVOCount()
    {
        return _redirectVOList.size();
    } //-- int getRedirectVOCount() 

    /**
     * Method getRegionReturns the value of field 'region'.
     * 
     * @return the value of field 'region'.
     */
    public java.lang.String getRegion()
    {
        return this._region;
    } //-- java.lang.String getRegion() 

    /**
     * Method getTerminationDateReturns the value of field
     * 'terminationDate'.
     * 
     * @return the value of field 'terminationDate'.
     */
    public java.lang.String getTerminationDate()
    {
        return this._terminationDate;
    } //-- java.lang.String getTerminationDate() 

    /**
     * Method getVestingVO
     * 
     * @param index
     */
    public edit.common.vo.VestingVO getVestingVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _vestingVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.VestingVO) _vestingVOList.elementAt(index);
    } //-- edit.common.vo.VestingVO getVestingVO(int) 

    /**
     * Method getVestingVO
     */
    public edit.common.vo.VestingVO[] getVestingVO()
    {
        int size = _vestingVOList.size();
        edit.common.vo.VestingVO[] mArray = new edit.common.vo.VestingVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.VestingVO) _vestingVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.VestingVO[] getVestingVO() 

    /**
     * Method getVestingVOCount
     */
    public int getVestingVOCount()
    {
        return _vestingVOList.size();
    } //-- int getVestingVOCount() 

    /**
     * Method getWithholdingStatusReturns the value of field
     * 'withholdingStatus'.
     * 
     * @return the value of field 'withholdingStatus'.
     */
    public java.lang.String getWithholdingStatus()
    {
        return this._withholdingStatus;
    } //-- java.lang.String getWithholdingStatus() 

    /**
     * Method hasAgentPK
     */
    public boolean hasAgentPK()
    {
        return this._has_agentPK;
    } //-- boolean hasAgentPK() 

    /**
     * Method hasCompanyFK
     */
    public boolean hasCompanyFK()
    {
        return this._has_companyFK;
    } //-- boolean hasCompanyFK() 

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
     * Method removeAgentNoteVO
     * 
     * @param index
     */
    public edit.common.vo.AgentNoteVO removeAgentNoteVO(int index)
    {
        java.lang.Object obj = _agentNoteVOList.elementAt(index);
        _agentNoteVOList.removeElementAt(index);
        return (edit.common.vo.AgentNoteVO) obj;
    } //-- edit.common.vo.AgentNoteVO removeAgentNoteVO(int) 

    /**
     * Method removeAgentRequirementVO
     * 
     * @param index
     */
    public edit.common.vo.AgentRequirementVO removeAgentRequirementVO(int index)
    {
        java.lang.Object obj = _agentRequirementVOList.elementAt(index);
        _agentRequirementVOList.removeElementAt(index);
        return (edit.common.vo.AgentRequirementVO) obj;
    } //-- edit.common.vo.AgentRequirementVO removeAgentRequirementVO(int) 

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
     * Method removeAllAgentNoteVO
     */
    public void removeAllAgentNoteVO()
    {
        _agentNoteVOList.removeAllElements();
    } //-- void removeAllAgentNoteVO() 

    /**
     * Method removeAllAgentRequirementVO
     */
    public void removeAllAgentRequirementVO()
    {
        _agentRequirementVOList.removeAllElements();
    } //-- void removeAllAgentRequirementVO() 

    /**
     * Method removeAllCheckAdjustmentVO
     */
    public void removeAllCheckAdjustmentVO()
    {
        _checkAdjustmentVOList.removeAllElements();
    } //-- void removeAllCheckAdjustmentVO() 

    /**
     * Method removeAllClientRoleVO
     */
    public void removeAllClientRoleVO()
    {
        _clientRoleVOList.removeAllElements();
    } //-- void removeAllClientRoleVO() 

    /**
     * Method removeAllRedirectVO
     */
    public void removeAllRedirectVO()
    {
        _redirectVOList.removeAllElements();
    } //-- void removeAllRedirectVO() 

    /**
     * Method removeAllVestingVO
     */
    public void removeAllVestingVO()
    {
        _vestingVOList.removeAllElements();
    } //-- void removeAllVestingVO() 

    /**
     * Method removeCheckAdjustmentVO
     * 
     * @param index
     */
    public edit.common.vo.CheckAdjustmentVO removeCheckAdjustmentVO(int index)
    {
        java.lang.Object obj = _checkAdjustmentVOList.elementAt(index);
        _checkAdjustmentVOList.removeElementAt(index);
        return (edit.common.vo.CheckAdjustmentVO) obj;
    } //-- edit.common.vo.CheckAdjustmentVO removeCheckAdjustmentVO(int) 

    /**
     * Method removeClientRoleVO
     * 
     * @param index
     */
    public edit.common.vo.ClientRoleVO removeClientRoleVO(int index)
    {
        java.lang.Object obj = _clientRoleVOList.elementAt(index);
        _clientRoleVOList.removeElementAt(index);
        return (edit.common.vo.ClientRoleVO) obj;
    } //-- edit.common.vo.ClientRoleVO removeClientRoleVO(int) 

    /**
     * Method removeRedirectVO
     * 
     * @param index
     */
    public edit.common.vo.RedirectVO removeRedirectVO(int index)
    {
        java.lang.Object obj = _redirectVOList.elementAt(index);
        _redirectVOList.removeElementAt(index);
        return (edit.common.vo.RedirectVO) obj;
    } //-- edit.common.vo.RedirectVO removeRedirectVO(int) 

    /**
     * Method removeVestingVO
     * 
     * @param index
     */
    public edit.common.vo.VestingVO removeVestingVO(int index)
    {
        java.lang.Object obj = _vestingVOList.elementAt(index);
        _vestingVOList.removeElementAt(index);
        return (edit.common.vo.VestingVO) obj;
    } //-- edit.common.vo.VestingVO removeVestingVO(int) 

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
     * Method setAgentNoteVO
     * 
     * @param index
     * @param vAgentNoteVO
     */
    public void setAgentNoteVO(int index, edit.common.vo.AgentNoteVO vAgentNoteVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentNoteVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAgentNoteVO.setParentVO(this.getClass(), this);
        _agentNoteVOList.setElementAt(vAgentNoteVO, index);
    } //-- void setAgentNoteVO(int, edit.common.vo.AgentNoteVO) 

    /**
     * Method setAgentNoteVO
     * 
     * @param agentNoteVOArray
     */
    public void setAgentNoteVO(edit.common.vo.AgentNoteVO[] agentNoteVOArray)
    {
        //-- copy array
        _agentNoteVOList.removeAllElements();
        for (int i = 0; i < agentNoteVOArray.length; i++) {
            agentNoteVOArray[i].setParentVO(this.getClass(), this);
            _agentNoteVOList.addElement(agentNoteVOArray[i]);
        }
    } //-- void setAgentNoteVO(edit.common.vo.AgentNoteVO) 

    /**
     * Method setAgentNumberSets the value of field 'agentNumber'.
     * 
     * @param agentNumber the value of field 'agentNumber'.
     */
    public void setAgentNumber(java.lang.String agentNumber)
    {
        this._agentNumber = agentNumber;
        
        super.setVoChanged(true);
    } //-- void setAgentNumber(java.lang.String) 

    /**
     * Method setAgentPKSets the value of field 'agentPK'.
     * 
     * @param agentPK the value of field 'agentPK'.
     */
    public void setAgentPK(long agentPK)
    {
        this._agentPK = agentPK;
        
        super.setVoChanged(true);
        this._has_agentPK = true;
    } //-- void setAgentPK(long) 

    /**
     * Method setAgentRequirementVO
     * 
     * @param index
     * @param vAgentRequirementVO
     */
    public void setAgentRequirementVO(int index, edit.common.vo.AgentRequirementVO vAgentRequirementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentRequirementVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAgentRequirementVO.setParentVO(this.getClass(), this);
        _agentRequirementVOList.setElementAt(vAgentRequirementVO, index);
    } //-- void setAgentRequirementVO(int, edit.common.vo.AgentRequirementVO) 

    /**
     * Method setAgentRequirementVO
     * 
     * @param agentRequirementVOArray
     */
    public void setAgentRequirementVO(edit.common.vo.AgentRequirementVO[] agentRequirementVOArray)
    {
        //-- copy array
        _agentRequirementVOList.removeAllElements();
        for (int i = 0; i < agentRequirementVOArray.length; i++) {
            agentRequirementVOArray[i].setParentVO(this.getClass(), this);
            _agentRequirementVOList.addElement(agentRequirementVOArray[i]);
        }
    } //-- void setAgentRequirementVO(edit.common.vo.AgentRequirementVO) 

    /**
     * Method setAgentStatusCTSets the value of field
     * 'agentStatusCT'.
     * 
     * @param agentStatusCT the value of field 'agentStatusCT'.
     */
    public void setAgentStatusCT(java.lang.String agentStatusCT)
    {
        this._agentStatusCT = agentStatusCT;
        
        super.setVoChanged(true);
    } //-- void setAgentStatusCT(java.lang.String) 

    /**
     * Method setAgentTypeCTSets the value of field 'agentTypeCT'.
     * 
     * @param agentTypeCT the value of field 'agentTypeCT'.
     */
    public void setAgentTypeCT(java.lang.String agentTypeCT)
    {
        this._agentTypeCT = agentTypeCT;
        
        super.setVoChanged(true);
    } //-- void setAgentTypeCT(java.lang.String) 

    /**
     * Method setBranchSets the value of field 'branch'.
     * 
     * @param branch the value of field 'branch'.
     */
    public void setBranch(java.lang.String branch)
    {
        this._branch = branch;
        
        super.setVoChanged(true);
    } //-- void setBranch(java.lang.String) 

    /**
     * Method setCheckAdjustmentVO
     * 
     * @param index
     * @param vCheckAdjustmentVO
     */
    public void setCheckAdjustmentVO(int index, edit.common.vo.CheckAdjustmentVO vCheckAdjustmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _checkAdjustmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vCheckAdjustmentVO.setParentVO(this.getClass(), this);
        _checkAdjustmentVOList.setElementAt(vCheckAdjustmentVO, index);
    } //-- void setCheckAdjustmentVO(int, edit.common.vo.CheckAdjustmentVO) 

    /**
     * Method setCheckAdjustmentVO
     * 
     * @param checkAdjustmentVOArray
     */
    public void setCheckAdjustmentVO(edit.common.vo.CheckAdjustmentVO[] checkAdjustmentVOArray)
    {
        //-- copy array
        _checkAdjustmentVOList.removeAllElements();
        for (int i = 0; i < checkAdjustmentVOArray.length; i++) {
            checkAdjustmentVOArray[i].setParentVO(this.getClass(), this);
            _checkAdjustmentVOList.addElement(checkAdjustmentVOArray[i]);
        }
    } //-- void setCheckAdjustmentVO(edit.common.vo.CheckAdjustmentVO) 

    /**
     * Method setClientRoleVO
     * 
     * @param index
     * @param vClientRoleVO
     */
    public void setClientRoleVO(int index, edit.common.vo.ClientRoleVO vClientRoleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientRoleVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vClientRoleVO.setParentVO(this.getClass(), this);
        _clientRoleVOList.setElementAt(vClientRoleVO, index);
    } //-- void setClientRoleVO(int, edit.common.vo.ClientRoleVO) 

    /**
     * Method setClientRoleVO
     * 
     * @param clientRoleVOArray
     */
    public void setClientRoleVO(edit.common.vo.ClientRoleVO[] clientRoleVOArray)
    {
        //-- copy array
        _clientRoleVOList.removeAllElements();
        for (int i = 0; i < clientRoleVOArray.length; i++) {
            clientRoleVOArray[i].setParentVO(this.getClass(), this);
            _clientRoleVOList.addElement(clientRoleVOArray[i]);
        }
    } //-- void setClientRoleVO(edit.common.vo.ClientRoleVO) 

    /**
     * Method setCompanyFKSets the value of field 'companyFK'.
     * 
     * @param companyFK the value of field 'companyFK'.
     */
    public void setCompanyFK(long companyFK)
    {
        this._companyFK = companyFK;
        
        super.setVoChanged(true);
        this._has_companyFK = true;
    } //-- void setCompanyFK(long) 

    /**
     * Method setCorrespondenceAddressTypeCTSets the value of field
     * 'correspondenceAddressTypeCT'.
     * 
     * @param correspondenceAddressTypeCT the value of field
     * 'correspondenceAddressTypeCT'.
     */
    public void setCorrespondenceAddressTypeCT(java.lang.String correspondenceAddressTypeCT)
    {
        this._correspondenceAddressTypeCT = correspondenceAddressTypeCT;
        
        super.setVoChanged(true);
    } //-- void setCorrespondenceAddressTypeCT(java.lang.String) 

    /**
     * Method setDepartmentSets the value of field 'department'.
     * 
     * @param department the value of field 'department'.
     */
    public void setDepartment(java.lang.String department)
    {
        this._department = department;
        
        super.setVoChanged(true);
    } //-- void setDepartment(java.lang.String) 

    /**
     * Method setDisbursementAddressTypeCTSets the value of field
     * 'disbursementAddressTypeCT'.
     * 
     * @param disbursementAddressTypeCT the value of field
     * 'disbursementAddressTypeCT'.
     */
    public void setDisbursementAddressTypeCT(java.lang.String disbursementAddressTypeCT)
    {
        this._disbursementAddressTypeCT = disbursementAddressTypeCT;
        
        super.setVoChanged(true);
    } //-- void setDisbursementAddressTypeCT(java.lang.String) 

    /**
     * Method setHireDateSets the value of field 'hireDate'.
     * 
     * @param hireDate the value of field 'hireDate'.
     */
    public void setHireDate(java.lang.String hireDate)
    {
        this._hireDate = hireDate;
        
        super.setVoChanged(true);
    } //-- void setHireDate(java.lang.String) 

    /**
     * Method setHoldCommStatusSets the value of field
     * 'holdCommStatus'.
     * 
     * @param holdCommStatus the value of field 'holdCommStatus'.
     */
    public void setHoldCommStatus(java.lang.String holdCommStatus)
    {
        this._holdCommStatus = holdCommStatus;
        
        super.setVoChanged(true);
    } //-- void setHoldCommStatus(java.lang.String) 

    /**
     * Method setIntDebitBalStatusCTSets the value of field
     * 'intDebitBalStatusCT'.
     * 
     * @param intDebitBalStatusCT the value of field
     * 'intDebitBalStatusCT'.
     */
    public void setIntDebitBalStatusCT(java.lang.String intDebitBalStatusCT)
    {
        this._intDebitBalStatusCT = intDebitBalStatusCT;
        
        super.setVoChanged(true);
    } //-- void setIntDebitBalStatusCT(java.lang.String) 

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
     * Method setRedirectVO
     * 
     * @param index
     * @param vRedirectVO
     */
    public void setRedirectVO(int index, edit.common.vo.RedirectVO vRedirectVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _redirectVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vRedirectVO.setParentVO(this.getClass(), this);
        _redirectVOList.setElementAt(vRedirectVO, index);
    } //-- void setRedirectVO(int, edit.common.vo.RedirectVO) 

    /**
     * Method setRedirectVO
     * 
     * @param redirectVOArray
     */
    public void setRedirectVO(edit.common.vo.RedirectVO[] redirectVOArray)
    {
        //-- copy array
        _redirectVOList.removeAllElements();
        for (int i = 0; i < redirectVOArray.length; i++) {
            redirectVOArray[i].setParentVO(this.getClass(), this);
            _redirectVOList.addElement(redirectVOArray[i]);
        }
    } //-- void setRedirectVO(edit.common.vo.RedirectVO) 

    /**
     * Method setRegionSets the value of field 'region'.
     * 
     * @param region the value of field 'region'.
     */
    public void setRegion(java.lang.String region)
    {
        this._region = region;
        
        super.setVoChanged(true);
    } //-- void setRegion(java.lang.String) 

    /**
     * Method setTerminationDateSets the value of field
     * 'terminationDate'.
     * 
     * @param terminationDate the value of field 'terminationDate'.
     */
    public void setTerminationDate(java.lang.String terminationDate)
    {
        this._terminationDate = terminationDate;
        
        super.setVoChanged(true);
    } //-- void setTerminationDate(java.lang.String) 

    /**
     * Method setVestingVO
     * 
     * @param index
     * @param vVestingVO
     */
    public void setVestingVO(int index, edit.common.vo.VestingVO vVestingVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _vestingVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vVestingVO.setParentVO(this.getClass(), this);
        _vestingVOList.setElementAt(vVestingVO, index);
    } //-- void setVestingVO(int, edit.common.vo.VestingVO) 

    /**
     * Method setVestingVO
     * 
     * @param vestingVOArray
     */
    public void setVestingVO(edit.common.vo.VestingVO[] vestingVOArray)
    {
        //-- copy array
        _vestingVOList.removeAllElements();
        for (int i = 0; i < vestingVOArray.length; i++) {
            vestingVOArray[i].setParentVO(this.getClass(), this);
            _vestingVOList.addElement(vestingVOArray[i]);
        }
    } //-- void setVestingVO(edit.common.vo.VestingVO) 

    /**
     * Method setWithholdingStatusSets the value of field
     * 'withholdingStatus'.
     * 
     * @param withholdingStatus the value of field
     * 'withholdingStatus'.
     */
    public void setWithholdingStatus(java.lang.String withholdingStatus)
    {
        this._withholdingStatus = withholdingStatus;
        
        super.setVoChanged(true);
    } //-- void setWithholdingStatus(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AgentVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AgentVO) Unmarshaller.unmarshal(edit.common.vo.AgentVO.class, reader);
    } //-- edit.common.vo.AgentVO unmarshal(java.io.Reader) 

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
