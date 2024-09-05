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
 * Class ContractClientVO.
 * 
 * @version $Revision$ $Date$
 */
public class ContractClientVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _contractClientPK
     */
    private long _contractClientPK;

    /**
     * keeps track of state for field: _contractClientPK
     */
    private boolean _has_contractClientPK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _clientRoleFK
     */
    private long _clientRoleFK;

    /**
     * keeps track of state for field: _clientRoleFK
     */
    private boolean _has_clientRoleFK;

    /**
     * Field _issueAge
     */
    private int _issueAge;

    /**
     * keeps track of state for field: _issueAge
     */
    private boolean _has_issueAge;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _terminationDate
     */
    private java.lang.String _terminationDate;

    /**
     * Field _overrideStatus
     */
    private java.lang.String _overrideStatus;

    /**
     * Field _relationshipToInsuredCT
     */
    private java.lang.String _relationshipToInsuredCT;

    /**
     * Field _beneRelationshipToInsured
     */
    private java.lang.String _beneRelationshipToInsured;

    /**
     * Field _telephoneAuthorizationCT
     */
    private java.lang.String _telephoneAuthorizationCT;

    /**
     * Field _classCT
     */
    private java.lang.String _classCT;

    private java.lang.String _originalClassCT;

    /**
     * Field _flatExtra
     */
    private java.math.BigDecimal _flatExtra;

    /**
     * Field _flatExtraAge
     */
    private int _flatExtraAge;

    /**
     * keeps track of state for field: _flatExtraAge
     */
    private boolean _has_flatExtraAge;

    /**
     * Field _flatExtraDur
     */
    private int _flatExtraDur;

    /**
     * keeps track of state for field: _flatExtraDur
     */
    private boolean _has_flatExtraDur;

    /**
     * Field _percentExtra
     */
    private java.math.BigDecimal _percentExtra;

    /**
     * Field _percentExtraAge
     */
    private int _percentExtraAge;

    /**
     * keeps track of state for field: _percentExtraAge
     */
    private boolean _has_percentExtraAge;

    /**
     * Field _percentExtraDur
     */
    private int _percentExtraDur;

    /**
     * keeps track of state for field: _percentExtraDur
     */
    private boolean _has_percentExtraDur;

    /**
     * Field _tableRatingCT
     */
    private java.lang.String _tableRatingCT;

    /**
     * Field _disbursementAddressTypeCT
     */
    private java.lang.String _disbursementAddressTypeCT;

    /**
     * Field _correspondenceAddressTypeCT
     */
    private java.lang.String _correspondenceAddressTypeCT;

    /**
     * Field _payorOfCT
     */
    private java.lang.String _payorOfCT;

    /**
     * Field _pendingClassChangeInd
     */
    private java.lang.String _pendingClassChangeInd;

    /**
     * Field _ratedGenderCT
     */
    private java.lang.String _ratedGenderCT;

    /**
     * Field _underwritingClassCT
     */
    private java.lang.String _underwritingClassCT;

    /**
     * Field _terminationReasonCT
     */
    private java.lang.String _terminationReasonCT;

    /**
     * Field _contractGroupFK
     */
    private long _contractGroupFK;

    /**
     * keeps track of state for field: _contractGroupFK
     */
    private boolean _has_contractGroupFK;

    /**
     * Field _relationshipToEmployeeCT
     */
    private java.lang.String _relationshipToEmployeeCT;

    /**
     * Field _authorizedSignatureCT
     */
    private java.lang.String _authorizedSignatureCT;

    /**
     * Field _employeeIdentification
     */
    private java.lang.String _employeeIdentification;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _contractClientAllocationVOList
     */
    private java.util.Vector _contractClientAllocationVOList;

    /**
     * Field _withholdingVOList
     */
    private java.util.Vector _withholdingVOList;

    /**
     * Field _clientSetupVOList
     */
    private java.util.Vector _clientSetupVOList;

    /**
     * Field _questionnaireResponseVOList
     */
    private java.util.Vector _questionnaireResponseVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ContractClientVO() {
        super();
        _contractClientAllocationVOList = new Vector();
        _withholdingVOList = new Vector();
        _clientSetupVOList = new Vector();
        _questionnaireResponseVOList = new Vector();
    } //-- edit.common.vo.ContractClientVO()


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
     * Method addContractClientAllocationVO
     * 
     * @param vContractClientAllocationVO
     */
    public void addContractClientAllocationVO(edit.common.vo.ContractClientAllocationVO vContractClientAllocationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractClientAllocationVO.setParentVO(this.getClass(), this);
        _contractClientAllocationVOList.addElement(vContractClientAllocationVO);
    } //-- void addContractClientAllocationVO(edit.common.vo.ContractClientAllocationVO) 

    /**
     * Method addContractClientAllocationVO
     * 
     * @param index
     * @param vContractClientAllocationVO
     */
    public void addContractClientAllocationVO(int index, edit.common.vo.ContractClientAllocationVO vContractClientAllocationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractClientAllocationVO.setParentVO(this.getClass(), this);
        _contractClientAllocationVOList.insertElementAt(vContractClientAllocationVO, index);
    } //-- void addContractClientAllocationVO(int, edit.common.vo.ContractClientAllocationVO) 

    /**
     * Method addQuestionnaireResponseVO
     * 
     * @param vQuestionnaireResponseVO
     */
    public void addQuestionnaireResponseVO(edit.common.vo.QuestionnaireResponseVO vQuestionnaireResponseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vQuestionnaireResponseVO.setParentVO(this.getClass(), this);
        _questionnaireResponseVOList.addElement(vQuestionnaireResponseVO);
    } //-- void addQuestionnaireResponseVO(edit.common.vo.QuestionnaireResponseVO) 

    /**
     * Method addQuestionnaireResponseVO
     * 
     * @param index
     * @param vQuestionnaireResponseVO
     */
    public void addQuestionnaireResponseVO(int index, edit.common.vo.QuestionnaireResponseVO vQuestionnaireResponseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vQuestionnaireResponseVO.setParentVO(this.getClass(), this);
        _questionnaireResponseVOList.insertElementAt(vQuestionnaireResponseVO, index);
    } //-- void addQuestionnaireResponseVO(int, edit.common.vo.QuestionnaireResponseVO) 

    /**
     * Method addWithholdingVO
     * 
     * @param vWithholdingVO
     */
    public void addWithholdingVO(edit.common.vo.WithholdingVO vWithholdingVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vWithholdingVO.setParentVO(this.getClass(), this);
        _withholdingVOList.addElement(vWithholdingVO);
    } //-- void addWithholdingVO(edit.common.vo.WithholdingVO) 

    /**
     * Method addWithholdingVO
     * 
     * @param index
     * @param vWithholdingVO
     */
    public void addWithholdingVO(int index, edit.common.vo.WithholdingVO vWithholdingVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vWithholdingVO.setParentVO(this.getClass(), this);
        _withholdingVOList.insertElementAt(vWithholdingVO, index);
    } //-- void addWithholdingVO(int, edit.common.vo.WithholdingVO) 

    /**
     * Method enumerateClientSetupVO
     */
    public java.util.Enumeration enumerateClientSetupVO()
    {
        return _clientSetupVOList.elements();
    } //-- java.util.Enumeration enumerateClientSetupVO() 

    /**
     * Method enumerateContractClientAllocationVO
     */
    public java.util.Enumeration enumerateContractClientAllocationVO()
    {
        return _contractClientAllocationVOList.elements();
    } //-- java.util.Enumeration enumerateContractClientAllocationVO() 

    /**
     * Method enumerateQuestionnaireResponseVO
     */
    public java.util.Enumeration enumerateQuestionnaireResponseVO()
    {
        return _questionnaireResponseVOList.elements();
    } //-- java.util.Enumeration enumerateQuestionnaireResponseVO() 

    /**
     * Method enumerateWithholdingVO
     */
    public java.util.Enumeration enumerateWithholdingVO()
    {
        return _withholdingVOList.elements();
    } //-- java.util.Enumeration enumerateWithholdingVO() 

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
        
        if (obj instanceof ContractClientVO) {
        
            ContractClientVO temp = (ContractClientVO)obj;
            if (this._contractClientPK != temp._contractClientPK)
                return false;
            if (this._has_contractClientPK != temp._has_contractClientPK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._clientRoleFK != temp._clientRoleFK)
                return false;
            if (this._has_clientRoleFK != temp._has_clientRoleFK)
                return false;
            if (this._issueAge != temp._issueAge)
                return false;
            if (this._has_issueAge != temp._has_issueAge)
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
            if (this._overrideStatus != null) {
                if (temp._overrideStatus == null) return false;
                else if (!(this._overrideStatus.equals(temp._overrideStatus))) 
                    return false;
            }
            else if (temp._overrideStatus != null)
                return false;
            if (this._relationshipToInsuredCT != null) {
                if (temp._relationshipToInsuredCT == null) return false;
                else if (!(this._relationshipToInsuredCT.equals(temp._relationshipToInsuredCT))) 
                    return false;
            }
            else if (temp._relationshipToInsuredCT != null)
                return false;
            if (this._beneRelationshipToInsured != null) {
                if (temp._beneRelationshipToInsured == null) return false;
                else if (!(this._beneRelationshipToInsured.equals(temp._beneRelationshipToInsured))) 
                    return false;
            }
            else if (temp._beneRelationshipToInsured != null)
                return false;
            if (this._telephoneAuthorizationCT != null) {
                if (temp._telephoneAuthorizationCT == null) return false;
                else if (!(this._telephoneAuthorizationCT.equals(temp._telephoneAuthorizationCT))) 
                    return false;
            }
            else if (temp._telephoneAuthorizationCT != null)
                return false;
            if (this._classCT != null) {
                if (temp._classCT == null) return false;
                else if (!(this._classCT.equals(temp._classCT))) 
                    return false;
            }
            else if (temp._classCT != null)
                return false;
            if (this._flatExtra != null) {
                if (temp._flatExtra == null) return false;
                else if (!(this._flatExtra.equals(temp._flatExtra))) 
                    return false;
            }
            else if (temp._flatExtra != null)
                return false;
            if (this._flatExtraAge != temp._flatExtraAge)
                return false;
            if (this._has_flatExtraAge != temp._has_flatExtraAge)
                return false;
            if (this._flatExtraDur != temp._flatExtraDur)
                return false;
            if (this._has_flatExtraDur != temp._has_flatExtraDur)
                return false;
            if (this._percentExtra != null) {
                if (temp._percentExtra == null) return false;
                else if (!(this._percentExtra.equals(temp._percentExtra))) 
                    return false;
            }
            else if (temp._percentExtra != null)
                return false;
            if (this._percentExtraAge != temp._percentExtraAge)
                return false;
            if (this._has_percentExtraAge != temp._has_percentExtraAge)
                return false;
            if (this._percentExtraDur != temp._percentExtraDur)
                return false;
            if (this._has_percentExtraDur != temp._has_percentExtraDur)
                return false;
            if (this._tableRatingCT != null) {
                if (temp._tableRatingCT == null) return false;
                else if (!(this._tableRatingCT.equals(temp._tableRatingCT))) 
                    return false;
            }
            else if (temp._tableRatingCT != null)
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
            if (this._payorOfCT != null) {
                if (temp._payorOfCT == null) return false;
                else if (!(this._payorOfCT.equals(temp._payorOfCT))) 
                    return false;
            }
            else if (temp._payorOfCT != null)
                return false;
            if (this._pendingClassChangeInd != null) {
                if (temp._pendingClassChangeInd == null) return false;
                else if (!(this._pendingClassChangeInd.equals(temp._pendingClassChangeInd))) 
                    return false;
            }
            else if (temp._pendingClassChangeInd != null)
                return false;
            if (this._ratedGenderCT != null) {
                if (temp._ratedGenderCT == null) return false;
                else if (!(this._ratedGenderCT.equals(temp._ratedGenderCT))) 
                    return false;
            }
            else if (temp._ratedGenderCT != null)
                return false;
            if (this._underwritingClassCT != null) {
                if (temp._underwritingClassCT == null) return false;
                else if (!(this._underwritingClassCT.equals(temp._underwritingClassCT))) 
                    return false;
            }
            else if (temp._underwritingClassCT != null)
                return false;
            if (this._terminationReasonCT != null) {
                if (temp._terminationReasonCT == null) return false;
                else if (!(this._terminationReasonCT.equals(temp._terminationReasonCT))) 
                    return false;
            }
            else if (temp._terminationReasonCT != null)
                return false;
            if (this._contractGroupFK != temp._contractGroupFK)
                return false;
            if (this._has_contractGroupFK != temp._has_contractGroupFK)
                return false;
            if (this._relationshipToEmployeeCT != null) {
                if (temp._relationshipToEmployeeCT == null) return false;
                else if (!(this._relationshipToEmployeeCT.equals(temp._relationshipToEmployeeCT))) 
                    return false;
            }
            else if (temp._relationshipToEmployeeCT != null)
                return false;
            if (this._authorizedSignatureCT != null) {
                if (temp._authorizedSignatureCT == null) return false;
                else if (!(this._authorizedSignatureCT.equals(temp._authorizedSignatureCT))) 
                    return false;
            }
            else if (temp._authorizedSignatureCT != null)
                return false;
            if (this._employeeIdentification != null) {
                if (temp._employeeIdentification == null) return false;
                else if (!(this._employeeIdentification.equals(temp._employeeIdentification))) 
                    return false;
            }
            else if (temp._employeeIdentification != null)
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
            if (this._contractClientAllocationVOList != null) {
                if (temp._contractClientAllocationVOList == null) return false;
                else if (!(this._contractClientAllocationVOList.equals(temp._contractClientAllocationVOList))) 
                    return false;
            }
            else if (temp._contractClientAllocationVOList != null)
                return false;
            if (this._withholdingVOList != null) {
                if (temp._withholdingVOList == null) return false;
                else if (!(this._withholdingVOList.equals(temp._withholdingVOList))) 
                    return false;
            }
            else if (temp._withholdingVOList != null)
                return false;
            if (this._clientSetupVOList != null) {
                if (temp._clientSetupVOList == null) return false;
                else if (!(this._clientSetupVOList.equals(temp._clientSetupVOList))) 
                    return false;
            }
            else if (temp._clientSetupVOList != null)
                return false;
            if (this._questionnaireResponseVOList != null) {
                if (temp._questionnaireResponseVOList == null) return false;
                else if (!(this._questionnaireResponseVOList.equals(temp._questionnaireResponseVOList))) 
                    return false;
            }
            else if (temp._questionnaireResponseVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAuthorizedSignatureCTReturns the value of field
     * 'authorizedSignatureCT'.
     * 
     * @return the value of field 'authorizedSignatureCT'.
     */
    public java.lang.String getAuthorizedSignatureCT()
    {
        return this._authorizedSignatureCT;
    } //-- java.lang.String getAuthorizedSignatureCT() 

    /**
     * Method getBeneRelationshipToInsuredReturns the value of
     * field 'beneRelationshipToInsured'.
     * 
     * @return the value of field 'beneRelationshipToInsured'.
     */
    public java.lang.String getBeneRelationshipToInsured()
    {
        return this._beneRelationshipToInsured;
    } //-- java.lang.String getBeneRelationshipToInsured() 

    /**
     * Method getClassCTReturns the value of field 'classCT'.
     * 
     * @return the value of field 'classCT'.
     */
    public java.lang.String getClassCT()
    {
        return this._classCT;
    } //-- java.lang.String getClassCT() 

    public java.lang.String getOriginalClassCT()
    {
        return this._originalClassCT;
    } //-- java.lang.String getOriginalClassCT() 

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
     * Method getContractClientAllocationVO
     * 
     * @param index
     */
    public edit.common.vo.ContractClientAllocationVO getContractClientAllocationVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractClientAllocationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ContractClientAllocationVO) _contractClientAllocationVOList.elementAt(index);
    } //-- edit.common.vo.ContractClientAllocationVO getContractClientAllocationVO(int) 

    /**
     * Method getContractClientAllocationVO
     */
    public edit.common.vo.ContractClientAllocationVO[] getContractClientAllocationVO()
    {
        int size = _contractClientAllocationVOList.size();
        edit.common.vo.ContractClientAllocationVO[] mArray = new edit.common.vo.ContractClientAllocationVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ContractClientAllocationVO) _contractClientAllocationVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ContractClientAllocationVO[] getContractClientAllocationVO() 

    /**
     * Method getContractClientAllocationVOCount
     */
    public int getContractClientAllocationVOCount()
    {
        return _contractClientAllocationVOList.size();
    } //-- int getContractClientAllocationVOCount() 

    /**
     * Method getContractClientPKReturns the value of field
     * 'contractClientPK'.
     * 
     * @return the value of field 'contractClientPK'.
     */
    public long getContractClientPK()
    {
        return this._contractClientPK;
    } //-- long getContractClientPK() 

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
     * Method getEmployeeIdentificationReturns the value of field
     * 'employeeIdentification'.
     * 
     * @return the value of field 'employeeIdentification'.
     */
    public java.lang.String getEmployeeIdentification()
    {
        return this._employeeIdentification;
    } //-- java.lang.String getEmployeeIdentification() 

    /**
     * Method getFlatExtraReturns the value of field 'flatExtra'.
     * 
     * @return the value of field 'flatExtra'.
     */
    public java.math.BigDecimal getFlatExtra()
    {
        return this._flatExtra;
    } //-- java.math.BigDecimal getFlatExtra() 

    /**
     * Method getFlatExtraAgeReturns the value of field
     * 'flatExtraAge'.
     * 
     * @return the value of field 'flatExtraAge'.
     */
    public int getFlatExtraAge()
    {
        return this._flatExtraAge;
    } //-- int getFlatExtraAge() 

    /**
     * Method getFlatExtraDurReturns the value of field
     * 'flatExtraDur'.
     * 
     * @return the value of field 'flatExtraDur'.
     */
    public int getFlatExtraDur()
    {
        return this._flatExtraDur;
    } //-- int getFlatExtraDur() 

    /**
     * Method getIssueAgeReturns the value of field 'issueAge'.
     * 
     * @return the value of field 'issueAge'.
     */
    public int getIssueAge()
    {
        return this._issueAge;
    } //-- int getIssueAge() 

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
     * Method getOverrideStatusReturns the value of field
     * 'overrideStatus'.
     * 
     * @return the value of field 'overrideStatus'.
     */
    public java.lang.String getOverrideStatus()
    {
        return this._overrideStatus;
    } //-- java.lang.String getOverrideStatus() 

    /**
     * Method getPayorOfCTReturns the value of field 'payorOfCT'.
     * 
     * @return the value of field 'payorOfCT'.
     */
    public java.lang.String getPayorOfCT()
    {
        return this._payorOfCT;
    } //-- java.lang.String getPayorOfCT() 

    /**
     * Method getPendingClassChangeIndReturns the value of field
     * 'pendingClassChangeInd'.
     * 
     * @return the value of field 'pendingClassChangeInd'.
     */
    public java.lang.String getPendingClassChangeInd()
    {
        return this._pendingClassChangeInd;
    } //-- java.lang.String getPendingClassChangeInd() 

    /**
     * Method getPercentExtraReturns the value of field
     * 'percentExtra'.
     * 
     * @return the value of field 'percentExtra'.
     */
    public java.math.BigDecimal getPercentExtra()
    {
        return this._percentExtra;
    } //-- java.math.BigDecimal getPercentExtra() 

    /**
     * Method getPercentExtraAgeReturns the value of field
     * 'percentExtraAge'.
     * 
     * @return the value of field 'percentExtraAge'.
     */
    public int getPercentExtraAge()
    {
        return this._percentExtraAge;
    } //-- int getPercentExtraAge() 

    /**
     * Method getPercentExtraDurReturns the value of field
     * 'percentExtraDur'.
     * 
     * @return the value of field 'percentExtraDur'.
     */
    public int getPercentExtraDur()
    {
        return this._percentExtraDur;
    } //-- int getPercentExtraDur() 

    /**
     * Method getQuestionnaireResponseVO
     * 
     * @param index
     */
    public edit.common.vo.QuestionnaireResponseVO getQuestionnaireResponseVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _questionnaireResponseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.QuestionnaireResponseVO) _questionnaireResponseVOList.elementAt(index);
    } //-- edit.common.vo.QuestionnaireResponseVO getQuestionnaireResponseVO(int) 

    /**
     * Method getQuestionnaireResponseVO
     */
    public edit.common.vo.QuestionnaireResponseVO[] getQuestionnaireResponseVO()
    {
        int size = _questionnaireResponseVOList.size();
        edit.common.vo.QuestionnaireResponseVO[] mArray = new edit.common.vo.QuestionnaireResponseVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.QuestionnaireResponseVO) _questionnaireResponseVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.QuestionnaireResponseVO[] getQuestionnaireResponseVO() 

    /**
     * Method getQuestionnaireResponseVOCount
     */
    public int getQuestionnaireResponseVOCount()
    {
        return _questionnaireResponseVOList.size();
    } //-- int getQuestionnaireResponseVOCount() 

    /**
     * Method getRatedGenderCTReturns the value of field
     * 'ratedGenderCT'.
     * 
     * @return the value of field 'ratedGenderCT'.
     */
    public java.lang.String getRatedGenderCT()
    {
        return this._ratedGenderCT;
    } //-- java.lang.String getRatedGenderCT() 

    /**
     * Method getRelationshipToEmployeeCTReturns the value of field
     * 'relationshipToEmployeeCT'.
     * 
     * @return the value of field 'relationshipToEmployeeCT'.
     */
    public java.lang.String getRelationshipToEmployeeCT()
    {
        return this._relationshipToEmployeeCT;
    } //-- java.lang.String getRelationshipToEmployeeCT() 

    /**
     * Method getRelationshipToInsuredCTReturns the value of field
     * 'relationshipToInsuredCT'.
     * 
     * @return the value of field 'relationshipToInsuredCT'.
     */
    public java.lang.String getRelationshipToInsuredCT()
    {
        return this._relationshipToInsuredCT;
    } //-- java.lang.String getRelationshipToInsuredCT() 

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
     * Method getTableRatingCTReturns the value of field
     * 'tableRatingCT'.
     * 
     * @return the value of field 'tableRatingCT'.
     */
    public java.lang.String getTableRatingCT()
    {
        return this._tableRatingCT;
    } //-- java.lang.String getTableRatingCT() 

    /**
     * Method getTelephoneAuthorizationCTReturns the value of field
     * 'telephoneAuthorizationCT'.
     * 
     * @return the value of field 'telephoneAuthorizationCT'.
     */
    public java.lang.String getTelephoneAuthorizationCT()
    {
        return this._telephoneAuthorizationCT;
    } //-- java.lang.String getTelephoneAuthorizationCT() 

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
     * Method getTerminationReasonCTReturns the value of field
     * 'terminationReasonCT'.
     * 
     * @return the value of field 'terminationReasonCT'.
     */
    public java.lang.String getTerminationReasonCT()
    {
        return this._terminationReasonCT;
    } //-- java.lang.String getTerminationReasonCT() 

    /**
     * Method getUnderwritingClassCTReturns the value of field
     * 'underwritingClassCT'.
     * 
     * @return the value of field 'underwritingClassCT'.
     */
    public java.lang.String getUnderwritingClassCT()
    {
        return this._underwritingClassCT;
    } //-- java.lang.String getUnderwritingClassCT() 

    /**
     * Method getWithholdingVO
     * 
     * @param index
     */
    public edit.common.vo.WithholdingVO getWithholdingVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _withholdingVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.WithholdingVO) _withholdingVOList.elementAt(index);
    } //-- edit.common.vo.WithholdingVO getWithholdingVO(int) 

    /**
     * Method getWithholdingVO
     */
    public edit.common.vo.WithholdingVO[] getWithholdingVO()
    {
        int size = _withholdingVOList.size();
        edit.common.vo.WithholdingVO[] mArray = new edit.common.vo.WithholdingVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.WithholdingVO) _withholdingVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.WithholdingVO[] getWithholdingVO() 

    /**
     * Method getWithholdingVOCount
     */
    public int getWithholdingVOCount()
    {
        return _withholdingVOList.size();
    } //-- int getWithholdingVOCount() 

    /**
     * Method hasClientRoleFK
     */
    public boolean hasClientRoleFK()
    {
        return this._has_clientRoleFK;
    } //-- boolean hasClientRoleFK() 

    /**
     * Method hasContractClientPK
     */
    public boolean hasContractClientPK()
    {
        return this._has_contractClientPK;
    } //-- boolean hasContractClientPK() 

    /**
     * Method hasContractGroupFK
     */
    public boolean hasContractGroupFK()
    {
        return this._has_contractGroupFK;
    } //-- boolean hasContractGroupFK() 

    /**
     * Method hasFlatExtraAge
     */
    public boolean hasFlatExtraAge()
    {
        return this._has_flatExtraAge;
    } //-- boolean hasFlatExtraAge() 

    /**
     * Method hasFlatExtraDur
     */
    public boolean hasFlatExtraDur()
    {
        return this._has_flatExtraDur;
    } //-- boolean hasFlatExtraDur() 

    /**
     * Method hasIssueAge
     */
    public boolean hasIssueAge()
    {
        return this._has_issueAge;
    } //-- boolean hasIssueAge() 

    /**
     * Method hasPercentExtraAge
     */
    public boolean hasPercentExtraAge()
    {
        return this._has_percentExtraAge;
    } //-- boolean hasPercentExtraAge() 

    /**
     * Method hasPercentExtraDur
     */
    public boolean hasPercentExtraDur()
    {
        return this._has_percentExtraDur;
    } //-- boolean hasPercentExtraDur() 

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
     * Method removeAllContractClientAllocationVO
     */
    public void removeAllContractClientAllocationVO()
    {
        _contractClientAllocationVOList.removeAllElements();
    } //-- void removeAllContractClientAllocationVO() 

    /**
     * Method removeAllQuestionnaireResponseVO
     */
    public void removeAllQuestionnaireResponseVO()
    {
        _questionnaireResponseVOList.removeAllElements();
    } //-- void removeAllQuestionnaireResponseVO() 

    /**
     * Method removeAllWithholdingVO
     */
    public void removeAllWithholdingVO()
    {
        _withholdingVOList.removeAllElements();
    } //-- void removeAllWithholdingVO() 

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
     * Method removeContractClientAllocationVO
     * 
     * @param index
     */
    public edit.common.vo.ContractClientAllocationVO removeContractClientAllocationVO(int index)
    {
        java.lang.Object obj = _contractClientAllocationVOList.elementAt(index);
        _contractClientAllocationVOList.removeElementAt(index);
        return (edit.common.vo.ContractClientAllocationVO) obj;
    } //-- edit.common.vo.ContractClientAllocationVO removeContractClientAllocationVO(int) 

    /**
     * Method removeQuestionnaireResponseVO
     * 
     * @param index
     */
    public edit.common.vo.QuestionnaireResponseVO removeQuestionnaireResponseVO(int index)
    {
        java.lang.Object obj = _questionnaireResponseVOList.elementAt(index);
        _questionnaireResponseVOList.removeElementAt(index);
        return (edit.common.vo.QuestionnaireResponseVO) obj;
    } //-- edit.common.vo.QuestionnaireResponseVO removeQuestionnaireResponseVO(int) 

    /**
     * Method removeWithholdingVO
     * 
     * @param index
     */
    public edit.common.vo.WithholdingVO removeWithholdingVO(int index)
    {
        java.lang.Object obj = _withholdingVOList.elementAt(index);
        _withholdingVOList.removeElementAt(index);
        return (edit.common.vo.WithholdingVO) obj;
    } //-- edit.common.vo.WithholdingVO removeWithholdingVO(int) 

    /**
     * Method setAuthorizedSignatureCTSets the value of field
     * 'authorizedSignatureCT'.
     * 
     * @param authorizedSignatureCT the value of field
     * 'authorizedSignatureCT'.
     */
    public void setAuthorizedSignatureCT(java.lang.String authorizedSignatureCT)
    {
        this._authorizedSignatureCT = authorizedSignatureCT;
        
        super.setVoChanged(true);
    } //-- void setAuthorizedSignatureCT(java.lang.String) 

    /**
     * Method setBeneRelationshipToInsuredSets the value of field
     * 'beneRelationshipToInsured'.
     * 
     * @param beneRelationshipToInsured the value of field
     * 'beneRelationshipToInsured'.
     */
    public void setBeneRelationshipToInsured(java.lang.String beneRelationshipToInsured)
    {
        this._beneRelationshipToInsured = beneRelationshipToInsured;
        
        super.setVoChanged(true);
    } //-- void setBeneRelationshipToInsured(java.lang.String) 

    /**
     * Method setClassCTSets the value of field 'classCT'.
     * 
     * @param classCT the value of field 'classCT'.
     */
    public void setClassCT(java.lang.String classCT)
    {
        this._classCT = classCT;
        
        super.setVoChanged(true);
    } //-- void setClassCT(java.lang.String) 

    public void setOriginalClassCT(java.lang.String originalClassCT)
    {
        this._originalClassCT = originalClassCT;
        
        super.setVoChanged(true);
    } //-- void setClassCT(java.lang.String) 

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
     * Method setContractClientAllocationVO
     * 
     * @param index
     * @param vContractClientAllocationVO
     */
    public void setContractClientAllocationVO(int index, edit.common.vo.ContractClientAllocationVO vContractClientAllocationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractClientAllocationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vContractClientAllocationVO.setParentVO(this.getClass(), this);
        _contractClientAllocationVOList.setElementAt(vContractClientAllocationVO, index);
    } //-- void setContractClientAllocationVO(int, edit.common.vo.ContractClientAllocationVO) 

    /**
     * Method setContractClientAllocationVO
     * 
     * @param contractClientAllocationVOArray
     */
    public void setContractClientAllocationVO(edit.common.vo.ContractClientAllocationVO[] contractClientAllocationVOArray)
    {
        //-- copy array
        _contractClientAllocationVOList.removeAllElements();
        for (int i = 0; i < contractClientAllocationVOArray.length; i++) {
            contractClientAllocationVOArray[i].setParentVO(this.getClass(), this);
            _contractClientAllocationVOList.addElement(contractClientAllocationVOArray[i]);
        }
    } //-- void setContractClientAllocationVO(edit.common.vo.ContractClientAllocationVO) 

    /**
     * Method setContractClientPKSets the value of field
     * 'contractClientPK'.
     * 
     * @param contractClientPK the value of field 'contractClientPK'
     */
    public void setContractClientPK(long contractClientPK)
    {
        this._contractClientPK = contractClientPK;
        
        super.setVoChanged(true);
        this._has_contractClientPK = true;
    } //-- void setContractClientPK(long) 

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
     * Method setEmployeeIdentificationSets the value of field
     * 'employeeIdentification'.
     * 
     * @param employeeIdentification the value of field
     * 'employeeIdentification'.
     */
    public void setEmployeeIdentification(java.lang.String employeeIdentification)
    {
        this._employeeIdentification = employeeIdentification;
        
        super.setVoChanged(true);
    } //-- void setEmployeeIdentification(java.lang.String) 

    /**
     * Method setFlatExtraSets the value of field 'flatExtra'.
     * 
     * @param flatExtra the value of field 'flatExtra'.
     */
    public void setFlatExtra(java.math.BigDecimal flatExtra)
    {
        this._flatExtra = flatExtra;
        
        super.setVoChanged(true);
    } //-- void setFlatExtra(java.math.BigDecimal) 

    /**
     * Method setFlatExtraAgeSets the value of field
     * 'flatExtraAge'.
     * 
     * @param flatExtraAge the value of field 'flatExtraAge'.
     */
    public void setFlatExtraAge(int flatExtraAge)
    {
        this._flatExtraAge = flatExtraAge;
        
        super.setVoChanged(true);
        this._has_flatExtraAge = true;
    } //-- void setFlatExtraAge(int) 

    /**
     * Method setFlatExtraDurSets the value of field
     * 'flatExtraDur'.
     * 
     * @param flatExtraDur the value of field 'flatExtraDur'.
     */
    public void setFlatExtraDur(int flatExtraDur)
    {
        this._flatExtraDur = flatExtraDur;
        
        super.setVoChanged(true);
        this._has_flatExtraDur = true;
    } //-- void setFlatExtraDur(int) 

    /**
     * Method setIssueAgeSets the value of field 'issueAge'.
     * 
     * @param issueAge the value of field 'issueAge'.
     */
    public void setIssueAge(int issueAge)
    {
        this._issueAge = issueAge;
        
        super.setVoChanged(true);
        this._has_issueAge = true;
    } //-- void setIssueAge(int) 

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
     * Method setOverrideStatusSets the value of field
     * 'overrideStatus'.
     * 
     * @param overrideStatus the value of field 'overrideStatus'.
     */
    public void setOverrideStatus(java.lang.String overrideStatus)
    {
        this._overrideStatus = overrideStatus;
        
        super.setVoChanged(true);
    } //-- void setOverrideStatus(java.lang.String) 

    /**
     * Method setPayorOfCTSets the value of field 'payorOfCT'.
     * 
     * @param payorOfCT the value of field 'payorOfCT'.
     */
    public void setPayorOfCT(java.lang.String payorOfCT)
    {
        this._payorOfCT = payorOfCT;
        
        super.setVoChanged(true);
    } //-- void setPayorOfCT(java.lang.String) 

    /**
     * Method setPendingClassChangeIndSets the value of field
     * 'pendingClassChangeInd'.
     * 
     * @param pendingClassChangeInd the value of field
     * 'pendingClassChangeInd'.
     */
    public void setPendingClassChangeInd(java.lang.String pendingClassChangeInd)
    {
        this._pendingClassChangeInd = pendingClassChangeInd;
        
        super.setVoChanged(true);
    } //-- void setPendingClassChangeInd(java.lang.String) 

    /**
     * Method setPercentExtraSets the value of field
     * 'percentExtra'.
     * 
     * @param percentExtra the value of field 'percentExtra'.
     */
    public void setPercentExtra(java.math.BigDecimal percentExtra)
    {
        this._percentExtra = percentExtra;
        
        super.setVoChanged(true);
    } //-- void setPercentExtra(java.math.BigDecimal) 

    /**
     * Method setPercentExtraAgeSets the value of field
     * 'percentExtraAge'.
     * 
     * @param percentExtraAge the value of field 'percentExtraAge'.
     */
    public void setPercentExtraAge(int percentExtraAge)
    {
        this._percentExtraAge = percentExtraAge;
        
        super.setVoChanged(true);
        this._has_percentExtraAge = true;
    } //-- void setPercentExtraAge(int) 

    /**
     * Method setPercentExtraDurSets the value of field
     * 'percentExtraDur'.
     * 
     * @param percentExtraDur the value of field 'percentExtraDur'.
     */
    public void setPercentExtraDur(int percentExtraDur)
    {
        this._percentExtraDur = percentExtraDur;
        
        super.setVoChanged(true);
        this._has_percentExtraDur = true;
    } //-- void setPercentExtraDur(int) 

    /**
     * Method setQuestionnaireResponseVO
     * 
     * @param index
     * @param vQuestionnaireResponseVO
     */
    public void setQuestionnaireResponseVO(int index, edit.common.vo.QuestionnaireResponseVO vQuestionnaireResponseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _questionnaireResponseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vQuestionnaireResponseVO.setParentVO(this.getClass(), this);
        _questionnaireResponseVOList.setElementAt(vQuestionnaireResponseVO, index);
    } //-- void setQuestionnaireResponseVO(int, edit.common.vo.QuestionnaireResponseVO) 

    /**
     * Method setQuestionnaireResponseVO
     * 
     * @param questionnaireResponseVOArray
     */
    public void setQuestionnaireResponseVO(edit.common.vo.QuestionnaireResponseVO[] questionnaireResponseVOArray)
    {
        //-- copy array
        _questionnaireResponseVOList.removeAllElements();
        for (int i = 0; i < questionnaireResponseVOArray.length; i++) {
            questionnaireResponseVOArray[i].setParentVO(this.getClass(), this);
            _questionnaireResponseVOList.addElement(questionnaireResponseVOArray[i]);
        }
    } //-- void setQuestionnaireResponseVO(edit.common.vo.QuestionnaireResponseVO) 

    /**
     * Method setRatedGenderCTSets the value of field
     * 'ratedGenderCT'.
     * 
     * @param ratedGenderCT the value of field 'ratedGenderCT'.
     */
    public void setRatedGenderCT(java.lang.String ratedGenderCT)
    {
        this._ratedGenderCT = ratedGenderCT;
        
        super.setVoChanged(true);
    } //-- void setRatedGenderCT(java.lang.String) 

    /**
     * Method setRelationshipToEmployeeCTSets the value of field
     * 'relationshipToEmployeeCT'.
     * 
     * @param relationshipToEmployeeCT the value of field
     * 'relationshipToEmployeeCT'.
     */
    public void setRelationshipToEmployeeCT(java.lang.String relationshipToEmployeeCT)
    {
        this._relationshipToEmployeeCT = relationshipToEmployeeCT;
        
        super.setVoChanged(true);
    } //-- void setRelationshipToEmployeeCT(java.lang.String) 

    /**
     * Method setRelationshipToInsuredCTSets the value of field
     * 'relationshipToInsuredCT'.
     * 
     * @param relationshipToInsuredCT the value of field
     * 'relationshipToInsuredCT'.
     */
    public void setRelationshipToInsuredCT(java.lang.String relationshipToInsuredCT)
    {
        this._relationshipToInsuredCT = relationshipToInsuredCT;
        
        super.setVoChanged(true);
    } //-- void setRelationshipToInsuredCT(java.lang.String) 

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
     * Method setTableRatingCTSets the value of field
     * 'tableRatingCT'.
     * 
     * @param tableRatingCT the value of field 'tableRatingCT'.
     */
    public void setTableRatingCT(java.lang.String tableRatingCT)
    {
        this._tableRatingCT = tableRatingCT;
        
        super.setVoChanged(true);
    } //-- void setTableRatingCT(java.lang.String) 

    /**
     * Method setTelephoneAuthorizationCTSets the value of field
     * 'telephoneAuthorizationCT'.
     * 
     * @param telephoneAuthorizationCT the value of field
     * 'telephoneAuthorizationCT'.
     */
    public void setTelephoneAuthorizationCT(java.lang.String telephoneAuthorizationCT)
    {
        this._telephoneAuthorizationCT = telephoneAuthorizationCT;
        
        super.setVoChanged(true);
    } //-- void setTelephoneAuthorizationCT(java.lang.String) 

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
     * Method setTerminationReasonCTSets the value of field
     * 'terminationReasonCT'.
     * 
     * @param terminationReasonCT the value of field
     * 'terminationReasonCT'.
     */
    public void setTerminationReasonCT(java.lang.String terminationReasonCT)
    {
        this._terminationReasonCT = terminationReasonCT;
        
        super.setVoChanged(true);
    } //-- void setTerminationReasonCT(java.lang.String) 

    /**
     * Method setUnderwritingClassCTSets the value of field
     * 'underwritingClassCT'.
     * 
     * @param underwritingClassCT the value of field
     * 'underwritingClassCT'.
     */
    public void setUnderwritingClassCT(java.lang.String underwritingClassCT)
    {
        this._underwritingClassCT = underwritingClassCT;
        
        super.setVoChanged(true);
    } //-- void setUnderwritingClassCT(java.lang.String) 

    /**
     * Method setWithholdingVO
     * 
     * @param index
     * @param vWithholdingVO
     */
    public void setWithholdingVO(int index, edit.common.vo.WithholdingVO vWithholdingVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _withholdingVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vWithholdingVO.setParentVO(this.getClass(), this);
        _withholdingVOList.setElementAt(vWithholdingVO, index);
    } //-- void setWithholdingVO(int, edit.common.vo.WithholdingVO) 

    /**
     * Method setWithholdingVO
     * 
     * @param withholdingVOArray
     */
    public void setWithholdingVO(edit.common.vo.WithholdingVO[] withholdingVOArray)
    {
        //-- copy array
        _withholdingVOList.removeAllElements();
        for (int i = 0; i < withholdingVOArray.length; i++) {
            withholdingVOArray[i].setParentVO(this.getClass(), this);
            _withholdingVOList.addElement(withholdingVOArray[i]);
        }
    } //-- void setWithholdingVO(edit.common.vo.WithholdingVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ContractClientVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ContractClientVO) Unmarshaller.unmarshal(edit.common.vo.ContractClientVO.class, reader);
    } //-- edit.common.vo.ContractClientVO unmarshal(java.io.Reader) 

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
