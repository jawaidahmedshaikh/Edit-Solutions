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
 * Class ContractGroupVO.
 * 
 * @version $Revision$ $Date$
 */
public class ContractGroupVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _contractGroupPK
     */
    private long _contractGroupPK;

    /**
     * keeps track of state for field: _contractGroupPK
     */
    private boolean _has_contractGroupPK;

    /**
     * Field _contractGroupFK
     */
    private long _contractGroupFK;

    /**
     * keeps track of state for field: _contractGroupFK
     */
    private boolean _has_contractGroupFK;

    /**
     * Field _contractGroupNumber
     */
    private java.lang.String _contractGroupNumber;
    private java.lang.String _groupID;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _terminationDate
     */
    private java.lang.String _terminationDate;

    /**
     * Field _caseAssociationCode
     */
    private java.lang.String _caseAssociationCode;

    /**
     * Field _contractGroupTypeCT
     */
    private java.lang.String _contractGroupTypeCT;

    /**
     * Field _groupStatusCT
     */
    private java.lang.String _groupStatusCT;

    /**
     * Field _caseTypeCT
     */
    private java.lang.String _caseTypeCT;

    /**
     * Field _caseStatusCT
     */
    private java.lang.String _caseStatusCT;

    /**
     * Field _domicileStateCT
     */
    private java.lang.String _domicileStateCT;

    /**
     * Field _creationDate
     */
    private java.lang.String _creationDate;

    /**
     * Field _creationOperator
     */
    private java.lang.String _creationOperator;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _contractRequirementsGenerated
     */
    private java.lang.String _contractRequirementsGenerated;

    /**
     * Field _masterContractNumber
     */
    private java.lang.String _masterContractNumber;

    /**
     * Field _groupTrustStateCT
     */
    private java.lang.String _groupTrustStateCT;

    /**
     * Field _requirementNotifyDayCT
     */
    private java.lang.String _requirementNotifyDayCT;

    /**
     * Field _contractGroupVOList
     */
    private java.util.Vector _contractGroupVOList;

    /**
     * Field _payrollDeductionScheduleVOList
     */
    private java.util.Vector _payrollDeductionScheduleVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ContractGroupVO() {
        super();
        _contractGroupVOList = new Vector();
        _payrollDeductionScheduleVOList = new Vector();
    } //-- edit.common.vo.ContractGroupVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addContractGroupVO
     * 
     * @param vContractGroupVO
     */
    public void addContractGroupVO(edit.common.vo.ContractGroupVO vContractGroupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractGroupVO.setParentVO(this.getClass(), this);
        _contractGroupVOList.addElement(vContractGroupVO);
    } //-- void addContractGroupVO(edit.common.vo.ContractGroupVO) 

    /**
     * Method addContractGroupVO
     * 
     * @param index
     * @param vContractGroupVO
     */
    public void addContractGroupVO(int index, edit.common.vo.ContractGroupVO vContractGroupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractGroupVO.setParentVO(this.getClass(), this);
        _contractGroupVOList.insertElementAt(vContractGroupVO, index);
    } //-- void addContractGroupVO(int, edit.common.vo.ContractGroupVO) 

    /**
     * Method addPayrollDeductionScheduleVO
     * 
     * @param vPayrollDeductionScheduleVO
     */
    public void addPayrollDeductionScheduleVO(edit.common.vo.PayrollDeductionScheduleVO vPayrollDeductionScheduleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPayrollDeductionScheduleVO.setParentVO(this.getClass(), this);
        _payrollDeductionScheduleVOList.addElement(vPayrollDeductionScheduleVO);
    } //-- void addPayrollDeductionScheduleVO(edit.common.vo.PayrollDeductionScheduleVO) 

    /**
     * Method addPayrollDeductionScheduleVO
     * 
     * @param index
     * @param vPayrollDeductionScheduleVO
     */
    public void addPayrollDeductionScheduleVO(int index, edit.common.vo.PayrollDeductionScheduleVO vPayrollDeductionScheduleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPayrollDeductionScheduleVO.setParentVO(this.getClass(), this);
        _payrollDeductionScheduleVOList.insertElementAt(vPayrollDeductionScheduleVO, index);
    } //-- void addPayrollDeductionScheduleVO(int, edit.common.vo.PayrollDeductionScheduleVO) 

    /**
     * Method enumerateContractGroupVO
     */
    public java.util.Enumeration enumerateContractGroupVO()
    {
        return _contractGroupVOList.elements();
    } //-- java.util.Enumeration enumerateContractGroupVO() 

    /**
     * Method enumeratePayrollDeductionScheduleVO
     */
    public java.util.Enumeration enumeratePayrollDeductionScheduleVO()
    {
        return _payrollDeductionScheduleVOList.elements();
    } //-- java.util.Enumeration enumeratePayrollDeductionScheduleVO() 

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
        
        if (obj instanceof ContractGroupVO) {
        
            ContractGroupVO temp = (ContractGroupVO)obj;
            if (this._contractGroupPK != temp._contractGroupPK)
                return false;
            if (this._has_contractGroupPK != temp._has_contractGroupPK)
                return false;
            if (this._contractGroupFK != temp._contractGroupFK)
                return false;
            if (this._has_contractGroupFK != temp._has_contractGroupFK)
                return false;
            if (this._contractGroupNumber != null) {
                if (temp._contractGroupNumber == null) return false;
                else if (!(this._contractGroupNumber.equals(temp._contractGroupNumber))) 
                    return false;
            }
            else if (temp._contractGroupNumber != null)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._terminationDate != null) {
                if (temp._terminationDate == null) return false;
                else if (!(this._terminationDate.equals(temp._terminationDate))) 
                    return false;
            }
            else if (temp._terminationDate != null)
                return false;
            if (this._caseAssociationCode != null) {
                if (temp._caseAssociationCode == null) return false;
                else if (!(this._caseAssociationCode.equals(temp._caseAssociationCode))) 
                    return false;
            }
            else if (temp._caseAssociationCode != null)
                return false;
            if (this._contractGroupTypeCT != null) {
                if (temp._contractGroupTypeCT == null) return false;
                else if (!(this._contractGroupTypeCT.equals(temp._contractGroupTypeCT))) 
                    return false;
            }
            else if (temp._contractGroupTypeCT != null)
                return false;
            if (this._groupStatusCT != null) {
                if (temp._groupStatusCT == null) return false;
                else if (!(this._groupStatusCT.equals(temp._groupStatusCT))) 
                    return false;
            }
            else if (temp._groupStatusCT != null)
                return false;
            if (this._caseTypeCT != null) {
                if (temp._caseTypeCT == null) return false;
                else if (!(this._caseTypeCT.equals(temp._caseTypeCT))) 
                    return false;
            }
            else if (temp._caseTypeCT != null)
                return false;
            if (this._caseStatusCT != null) {
                if (temp._caseStatusCT == null) return false;
                else if (!(this._caseStatusCT.equals(temp._caseStatusCT))) 
                    return false;
            }
            else if (temp._caseStatusCT != null)
                return false;
            if (this._domicileStateCT != null) {
                if (temp._domicileStateCT == null) return false;
                else if (!(this._domicileStateCT.equals(temp._domicileStateCT))) 
                    return false;
            }
            else if (temp._domicileStateCT != null)
                return false;
            if (this._creationDate != null) {
                if (temp._creationDate == null) return false;
                else if (!(this._creationDate.equals(temp._creationDate))) 
                    return false;
            }
            else if (temp._creationDate != null)
                return false;
            if (this._creationOperator != null) {
                if (temp._creationOperator == null) return false;
                else if (!(this._creationOperator.equals(temp._creationOperator))) 
                    return false;
            }
            else if (temp._creationOperator != null)
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
            if (this._contractRequirementsGenerated != null) {
                if (temp._contractRequirementsGenerated == null) return false;
                else if (!(this._contractRequirementsGenerated.equals(temp._contractRequirementsGenerated))) 
                    return false;
            }
            else if (temp._contractRequirementsGenerated != null)
                return false;
            if (this._masterContractNumber != null) {
                if (temp._masterContractNumber == null) return false;
                else if (!(this._masterContractNumber.equals(temp._masterContractNumber))) 
                    return false;
            }
            else if (temp._masterContractNumber != null)
                return false;
            if (this._groupTrustStateCT != null) {
                if (temp._groupTrustStateCT == null) return false;
                else if (!(this._groupTrustStateCT.equals(temp._groupTrustStateCT))) 
                    return false;
            }
            else if (temp._groupTrustStateCT != null)
                return false;
            if (this._requirementNotifyDayCT != null) {
                if (temp._requirementNotifyDayCT == null) return false;
                else if (!(this._requirementNotifyDayCT.equals(temp._requirementNotifyDayCT))) 
                    return false;
            }
            else if (temp._requirementNotifyDayCT != null)
                return false;
            if (this._contractGroupVOList != null) {
                if (temp._contractGroupVOList == null) return false;
                else if (!(this._contractGroupVOList.equals(temp._contractGroupVOList))) 
                    return false;
            }
            else if (temp._contractGroupVOList != null)
                return false;
            if (this._payrollDeductionScheduleVOList != null) {
                if (temp._payrollDeductionScheduleVOList == null) return false;
                else if (!(this._payrollDeductionScheduleVOList.equals(temp._payrollDeductionScheduleVOList))) 
                    return false;
            }
            else if (temp._payrollDeductionScheduleVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCaseAssociationCodeReturns the value of field
     * 'caseAssociationCode'.
     * 
     * @return the value of field 'caseAssociationCode'.
     */
    public java.lang.String getCaseAssociationCode()
    {
        return this._caseAssociationCode;
    } //-- java.lang.String getCaseAssociationCode() 

    /**
     * Method getCaseStatusCTReturns the value of field
     * 'caseStatusCT'.
     * 
     * @return the value of field 'caseStatusCT'.
     */
    public java.lang.String getCaseStatusCT()
    {
        return this._caseStatusCT;
    } //-- java.lang.String getCaseStatusCT() 

    /**
     * Method getCaseTypeCTReturns the value of field 'caseTypeCT'.
     * 
     * @return the value of field 'caseTypeCT'.
     */
    public java.lang.String getCaseTypeCT()
    {
        return this._caseTypeCT;
    } //-- java.lang.String getCaseTypeCT() 

    /**
     * Method getContractGroupFKReturns the value of field
     * 'contractGroupFK'.
     * 
     * @return the value of field 'contractGroupFK'.
     */
    public long getContractGroupFK()
    {
        return this._contractGroupFK;
    } //-- long getContractGroupFK() 

    /**
     * Method getContractGroupNumberReturns the value of field
     * 'contractGroupNumber'.
     * 
     * @return the value of field 'contractGroupNumber'.
     */
    public java.lang.String getContractGroupNumber()
    {
        return this._contractGroupNumber;
    } //-- java.lang.String getContractGroupNumber() 
    
    

    public java.lang.String getGroupID() {
		return _groupID;
	}


	public void setGroupID(java.lang.String groupID) {
		this._groupID = groupID;
	}


	/**
     * Method getContractGroupPKReturns the value of field
     * 'contractGroupPK'.
     * 
     * @return the value of field 'contractGroupPK'.
     */
    public long getContractGroupPK()
    {
        return this._contractGroupPK;
    } //-- long getContractGroupPK() 

    /**
     * Method getContractGroupTypeCTReturns the value of field
     * 'contractGroupTypeCT'.
     * 
     * @return the value of field 'contractGroupTypeCT'.
     */
    public java.lang.String getContractGroupTypeCT()
    {
        return this._contractGroupTypeCT;
    } //-- java.lang.String getContractGroupTypeCT() 

    /**
     * Method getContractGroupVO
     * 
     * @param index
     */
    public edit.common.vo.ContractGroupVO getContractGroupVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractGroupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ContractGroupVO) _contractGroupVOList.elementAt(index);
    } //-- edit.common.vo.ContractGroupVO getContractGroupVO(int) 

    /**
     * Method getContractGroupVO
     */
    public edit.common.vo.ContractGroupVO[] getContractGroupVO()
    {
        int size = _contractGroupVOList.size();
        edit.common.vo.ContractGroupVO[] mArray = new edit.common.vo.ContractGroupVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ContractGroupVO) _contractGroupVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ContractGroupVO[] getContractGroupVO() 

    /**
     * Method getContractGroupVOCount
     */
    public int getContractGroupVOCount()
    {
        return _contractGroupVOList.size();
    } //-- int getContractGroupVOCount() 

    /**
     * Method getContractRequirementsGeneratedReturns the value of
     * field 'contractRequirementsGenerated'.
     * 
     * @return the value of field 'contractRequirementsGenerated'.
     */
    public java.lang.String getContractRequirementsGenerated()
    {
        return this._contractRequirementsGenerated;
    } //-- java.lang.String getContractRequirementsGenerated() 

    /**
     * Method getCreationDateReturns the value of field
     * 'creationDate'.
     * 
     * @return the value of field 'creationDate'.
     */
    public java.lang.String getCreationDate()
    {
        return this._creationDate;
    } //-- java.lang.String getCreationDate() 

    /**
     * Method getCreationOperatorReturns the value of field
     * 'creationOperator'.
     * 
     * @return the value of field 'creationOperator'.
     */
    public java.lang.String getCreationOperator()
    {
        return this._creationOperator;
    } //-- java.lang.String getCreationOperator() 

    /**
     * Method getDomicileStateCTReturns the value of field
     * 'domicileStateCT'.
     * 
     * @return the value of field 'domicileStateCT'.
     */
    public java.lang.String getDomicileStateCT()
    {
        return this._domicileStateCT;
    } //-- java.lang.String getDomicileStateCT() 

    /**
     * Method getEffectiveDateReturns the value of field
     * 'effectiveDate'.
     * 
     * @return the value of field 'effectiveDate'.
     */
    public java.lang.String getEffectiveDate()
    {
        return this._effectiveDate;
    } //-- java.lang.String getEffectiveDate() 

    /**
     * Method getGroupStatusCTReturns the value of field
     * 'groupStatusCT'.
     * 
     * @return the value of field 'groupStatusCT'.
     */
    public java.lang.String getGroupStatusCT()
    {
        return this._groupStatusCT;
    } //-- java.lang.String getGroupStatusCT() 

    /**
     * Method getGroupTrustStateCTReturns the value of field
     * 'groupTrustStateCT'.
     * 
     * @return the value of field 'groupTrustStateCT'.
     */
    public java.lang.String getGroupTrustStateCT()
    {
        return this._groupTrustStateCT;
    } //-- java.lang.String getGroupTrustStateCT() 

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
     * Method getMasterContractNumberReturns the value of field
     * 'masterContractNumber'.
     * 
     * @return the value of field 'masterContractNumber'.
     */
    public java.lang.String getMasterContractNumber()
    {
        return this._masterContractNumber;
    } //-- java.lang.String getMasterContractNumber() 

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
     * Method getPayrollDeductionScheduleVO
     * 
     * @param index
     */
    public edit.common.vo.PayrollDeductionScheduleVO getPayrollDeductionScheduleVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _payrollDeductionScheduleVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.PayrollDeductionScheduleVO) _payrollDeductionScheduleVOList.elementAt(index);
    } //-- edit.common.vo.PayrollDeductionScheduleVO getPayrollDeductionScheduleVO(int) 

    /**
     * Method getPayrollDeductionScheduleVO
     */
    public edit.common.vo.PayrollDeductionScheduleVO[] getPayrollDeductionScheduleVO()
    {
        int size = _payrollDeductionScheduleVOList.size();
        edit.common.vo.PayrollDeductionScheduleVO[] mArray = new edit.common.vo.PayrollDeductionScheduleVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.PayrollDeductionScheduleVO) _payrollDeductionScheduleVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.PayrollDeductionScheduleVO[] getPayrollDeductionScheduleVO() 

    /**
     * Method getPayrollDeductionScheduleVOCount
     */
    public int getPayrollDeductionScheduleVOCount()
    {
        return _payrollDeductionScheduleVOList.size();
    } //-- int getPayrollDeductionScheduleVOCount() 

    /**
     * Method getRequirementNotifyDayCTReturns the value of field
     * 'requirementNotifyDayCT'.
     * 
     * @return the value of field 'requirementNotifyDayCT'.
     */
    public java.lang.String getRequirementNotifyDayCT()
    {
        return this._requirementNotifyDayCT;
    } //-- java.lang.String getRequirementNotifyDayCT() 

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
     * Method hasContractGroupFK
     */
    public boolean hasContractGroupFK()
    {
        return this._has_contractGroupFK;
    } //-- boolean hasContractGroupFK() 

    /**
     * Method hasContractGroupPK
     */
    public boolean hasContractGroupPK()
    {
        return this._has_contractGroupPK;
    } //-- boolean hasContractGroupPK() 

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
     * Method removeAllContractGroupVO
     */
    public void removeAllContractGroupVO()
    {
        _contractGroupVOList.removeAllElements();
    } //-- void removeAllContractGroupVO() 

    /**
     * Method removeAllPayrollDeductionScheduleVO
     */
    public void removeAllPayrollDeductionScheduleVO()
    {
        _payrollDeductionScheduleVOList.removeAllElements();
    } //-- void removeAllPayrollDeductionScheduleVO() 

    /**
     * Method removeContractGroupVO
     * 
     * @param index
     */
    public edit.common.vo.ContractGroupVO removeContractGroupVO(int index)
    {
        java.lang.Object obj = _contractGroupVOList.elementAt(index);
        _contractGroupVOList.removeElementAt(index);
        return (edit.common.vo.ContractGroupVO) obj;
    } //-- edit.common.vo.ContractGroupVO removeContractGroupVO(int) 

    /**
     * Method removePayrollDeductionScheduleVO
     * 
     * @param index
     */
    public edit.common.vo.PayrollDeductionScheduleVO removePayrollDeductionScheduleVO(int index)
    {
        java.lang.Object obj = _payrollDeductionScheduleVOList.elementAt(index);
        _payrollDeductionScheduleVOList.removeElementAt(index);
        return (edit.common.vo.PayrollDeductionScheduleVO) obj;
    } //-- edit.common.vo.PayrollDeductionScheduleVO removePayrollDeductionScheduleVO(int) 

    /**
     * Method setCaseAssociationCodeSets the value of field
     * 'caseAssociationCode'.
     * 
     * @param caseAssociationCode the value of field
     * 'caseAssociationCode'.
     */
    public void setCaseAssociationCode(java.lang.String caseAssociationCode)
    {
        this._caseAssociationCode = caseAssociationCode;
        
        super.setVoChanged(true);
    } //-- void setCaseAssociationCode(java.lang.String) 

    /**
     * Method setCaseStatusCTSets the value of field
     * 'caseStatusCT'.
     * 
     * @param caseStatusCT the value of field 'caseStatusCT'.
     */
    public void setCaseStatusCT(java.lang.String caseStatusCT)
    {
        this._caseStatusCT = caseStatusCT;
        
        super.setVoChanged(true);
    } //-- void setCaseStatusCT(java.lang.String) 

    /**
     * Method setCaseTypeCTSets the value of field 'caseTypeCT'.
     * 
     * @param caseTypeCT the value of field 'caseTypeCT'.
     */
    public void setCaseTypeCT(java.lang.String caseTypeCT)
    {
        this._caseTypeCT = caseTypeCT;
        
        super.setVoChanged(true);
    } //-- void setCaseTypeCT(java.lang.String) 

    /**
     * Method setContractGroupFKSets the value of field
     * 'contractGroupFK'.
     * 
     * @param contractGroupFK the value of field 'contractGroupFK'.
     */
    public void setContractGroupFK(long contractGroupFK)
    {
        this._contractGroupFK = contractGroupFK;
        
        super.setVoChanged(true);
        this._has_contractGroupFK = true;
    } //-- void setContractGroupFK(long) 

    /**
     * Method setContractGroupNumberSets the value of field
     * 'contractGroupNumber'.
     * 
     * @param contractGroupNumber the value of field
     * 'contractGroupNumber'.
     */
    public void setContractGroupNumber(java.lang.String contractGroupNumber)
    {
        this._contractGroupNumber = contractGroupNumber;
        
        super.setVoChanged(true);
    } //-- void setContractGroupNumber(java.lang.String) 

    /**
     * Method setContractGroupPKSets the value of field
     * 'contractGroupPK'.
     * 
     * @param contractGroupPK the value of field 'contractGroupPK'.
     */
    public void setContractGroupPK(long contractGroupPK)
    {
        this._contractGroupPK = contractGroupPK;
        
        super.setVoChanged(true);
        this._has_contractGroupPK = true;
    } //-- void setContractGroupPK(long) 

    /**
     * Method setContractGroupTypeCTSets the value of field
     * 'contractGroupTypeCT'.
     * 
     * @param contractGroupTypeCT the value of field
     * 'contractGroupTypeCT'.
     */
    public void setContractGroupTypeCT(java.lang.String contractGroupTypeCT)
    {
        this._contractGroupTypeCT = contractGroupTypeCT;
        
        super.setVoChanged(true);
    } //-- void setContractGroupTypeCT(java.lang.String) 

    /**
     * Method setContractGroupVO
     * 
     * @param index
     * @param vContractGroupVO
     */
    public void setContractGroupVO(int index, edit.common.vo.ContractGroupVO vContractGroupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractGroupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vContractGroupVO.setParentVO(this.getClass(), this);
        _contractGroupVOList.setElementAt(vContractGroupVO, index);
    } //-- void setContractGroupVO(int, edit.common.vo.ContractGroupVO) 

    /**
     * Method setContractGroupVO
     * 
     * @param contractGroupVOArray
     */
    public void setContractGroupVO(edit.common.vo.ContractGroupVO[] contractGroupVOArray)
    {
        //-- copy array
        _contractGroupVOList.removeAllElements();
        for (int i = 0; i < contractGroupVOArray.length; i++) {
            contractGroupVOArray[i].setParentVO(this.getClass(), this);
            _contractGroupVOList.addElement(contractGroupVOArray[i]);
        }
    } //-- void setContractGroupVO(edit.common.vo.ContractGroupVO) 

    /**
     * Method setContractRequirementsGeneratedSets the value of
     * field 'contractRequirementsGenerated'.
     * 
     * @param contractRequirementsGenerated the value of field
     * 'contractRequirementsGenerated'.
     */
    public void setContractRequirementsGenerated(java.lang.String contractRequirementsGenerated)
    {
        this._contractRequirementsGenerated = contractRequirementsGenerated;
        
        super.setVoChanged(true);
    } //-- void setContractRequirementsGenerated(java.lang.String) 

    /**
     * Method setCreationDateSets the value of field
     * 'creationDate'.
     * 
     * @param creationDate the value of field 'creationDate'.
     */
    public void setCreationDate(java.lang.String creationDate)
    {
        this._creationDate = creationDate;
        
        super.setVoChanged(true);
    } //-- void setCreationDate(java.lang.String) 

    /**
     * Method setCreationOperatorSets the value of field
     * 'creationOperator'.
     * 
     * @param creationOperator the value of field 'creationOperator'
     */
    public void setCreationOperator(java.lang.String creationOperator)
    {
        this._creationOperator = creationOperator;
        
        super.setVoChanged(true);
    } //-- void setCreationOperator(java.lang.String) 

    /**
     * Method setDomicileStateCTSets the value of field
     * 'domicileStateCT'.
     * 
     * @param domicileStateCT the value of field 'domicileStateCT'.
     */
    public void setDomicileStateCT(java.lang.String domicileStateCT)
    {
        this._domicileStateCT = domicileStateCT;
        
        super.setVoChanged(true);
    } //-- void setDomicileStateCT(java.lang.String) 

    /**
     * Method setEffectiveDateSets the value of field
     * 'effectiveDate'.
     * 
     * @param effectiveDate the value of field 'effectiveDate'.
     */
    public void setEffectiveDate(java.lang.String effectiveDate)
    {
        this._effectiveDate = effectiveDate;
        
        super.setVoChanged(true);
    } //-- void setEffectiveDate(java.lang.String) 

    /**
     * Method setGroupStatusCTSets the value of field
     * 'groupStatusCT'.
     * 
     * @param groupStatusCT the value of field 'groupStatusCT'.
     */
    public void setGroupStatusCT(java.lang.String groupStatusCT)
    {
        this._groupStatusCT = groupStatusCT;
        
        super.setVoChanged(true);
    } //-- void setGroupStatusCT(java.lang.String) 

    /**
     * Method setGroupTrustStateCTSets the value of field
     * 'groupTrustStateCT'.
     * 
     * @param groupTrustStateCT the value of field
     * 'groupTrustStateCT'.
     */
    public void setGroupTrustStateCT(java.lang.String groupTrustStateCT)
    {
        this._groupTrustStateCT = groupTrustStateCT;
        
        super.setVoChanged(true);
    } //-- void setGroupTrustStateCT(java.lang.String) 

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
     * Method setMasterContractNumberSets the value of field
     * 'masterContractNumber'.
     * 
     * @param masterContractNumber the value of field
     * 'masterContractNumber'.
     */
    public void setMasterContractNumber(java.lang.String masterContractNumber)
    {
        this._masterContractNumber = masterContractNumber;
        
        super.setVoChanged(true);
    } //-- void setMasterContractNumber(java.lang.String) 

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
     * Method setPayrollDeductionScheduleVO
     * 
     * @param index
     * @param vPayrollDeductionScheduleVO
     */
    public void setPayrollDeductionScheduleVO(int index, edit.common.vo.PayrollDeductionScheduleVO vPayrollDeductionScheduleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _payrollDeductionScheduleVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vPayrollDeductionScheduleVO.setParentVO(this.getClass(), this);
        _payrollDeductionScheduleVOList.setElementAt(vPayrollDeductionScheduleVO, index);
    } //-- void setPayrollDeductionScheduleVO(int, edit.common.vo.PayrollDeductionScheduleVO) 

    /**
     * Method setPayrollDeductionScheduleVO
     * 
     * @param payrollDeductionScheduleVOArray
     */
    public void setPayrollDeductionScheduleVO(edit.common.vo.PayrollDeductionScheduleVO[] payrollDeductionScheduleVOArray)
    {
        //-- copy array
        _payrollDeductionScheduleVOList.removeAllElements();
        for (int i = 0; i < payrollDeductionScheduleVOArray.length; i++) {
            payrollDeductionScheduleVOArray[i].setParentVO(this.getClass(), this);
            _payrollDeductionScheduleVOList.addElement(payrollDeductionScheduleVOArray[i]);
        }
    } //-- void setPayrollDeductionScheduleVO(edit.common.vo.PayrollDeductionScheduleVO) 

    /**
     * Method setRequirementNotifyDayCTSets the value of field
     * 'requirementNotifyDayCT'.
     * 
     * @param requirementNotifyDayCT the value of field
     * 'requirementNotifyDayCT'.
     */
    public void setRequirementNotifyDayCT(java.lang.String requirementNotifyDayCT)
    {
        this._requirementNotifyDayCT = requirementNotifyDayCT;
        
        super.setVoChanged(true);
    } //-- void setRequirementNotifyDayCT(java.lang.String) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ContractGroupVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ContractGroupVO) Unmarshaller.unmarshal(edit.common.vo.ContractGroupVO.class, reader);
    } //-- edit.common.vo.ContractGroupVO unmarshal(java.io.Reader) 

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
