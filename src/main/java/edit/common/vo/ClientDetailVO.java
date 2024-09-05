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
 * Class ClientDetailVO.
 * 
 * @version $Revision$ $Date$
 */
public class ClientDetailVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _clientDetailPK
     */
    private long _clientDetailPK;

    /**
     * keeps track of state for field: _clientDetailPK
     */
    private boolean _has_clientDetailPK;

    /**
     * Field _clientIdentification
     */
    private java.lang.String _clientIdentification;

    /**
     * Field _taxIdentification
     */
    private java.lang.String _taxIdentification;

    /**
     * Field _lastName
     */
    private java.lang.String _lastName;

    /**
     * Field _firstName
     */
    private java.lang.String _firstName;

    /**
     * Field _middleName
     */
    private java.lang.String _middleName;

    /**
     * Field _namePrefix
     */
    private java.lang.String _namePrefix;

    /**
     * Field _nameSuffix
     */
    private java.lang.String _nameSuffix;

    /**
     * Field _corporateName
     */
    private java.lang.String _corporateName;

    /**
     * Field _birthDate
     */
    private java.lang.String _birthDate;

    /**
     * Field _mothersMaidenName
     */
    private java.lang.String _mothersMaidenName;

    /**
     * Field _occupation
     */
    private java.lang.String _occupation;

    /**
     * Field _dateOfDeath
     */
    private java.lang.String _dateOfDeath;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _genderCT
     */
    private java.lang.String _genderCT;

    /**
     * Field _trustTypeCT
     */
    private java.lang.String _trustTypeCT;

    /**
     * Field _statusCT
     */
    private java.lang.String _statusCT;

    /**
     * Field _privacyInd
     */
    private java.lang.String _privacyInd;

    /**
     * Field _lastOFACCheckDate
     */
    private java.lang.String _lastOFACCheckDate;

    /**
     * Field _stateOfDeathCT
     */
    private java.lang.String _stateOfDeathCT;

    /**
     * Field _residentStateAtDeathCT
     */
    private java.lang.String _residentStateAtDeathCT;

    /**
     * Field _proofOfDeathReceivedDate
     */
    private java.lang.String _proofOfDeathReceivedDate;

    /**
     * Field _caseTrackingProcess
     */
    private java.lang.String _caseTrackingProcess;

    /**
     * Field _notificationReceivedDate
     */
    private java.lang.String _notificationReceivedDate;

    /**
     * Field _overrideStatus
     */
    private java.lang.String _overrideStatus;

    /**
     * Field _SICCodeCT
     */
    private java.lang.String _SICCodeCT;

    /**
     * Field _companyFK
     */
    private long _companyFK;

    /**
     * keeps track of state for field: _companyFK
     */
    private boolean _has_companyFK;

    /**
     * Field _clientAddressVOList
     */
    private java.util.Vector _clientAddressVOList;

    /**
     * Field _preferenceVOList
     */
    private java.util.Vector _preferenceVOList;

    /**
     * Field _reinsurerVOList
     */
    private java.util.Vector _reinsurerVOList;

    /**
     * Field _taxInformationVOList
     */
    private java.util.Vector _taxInformationVOList;

    /**
     * Field _clientRoleVOList
     */
    private java.util.Vector _clientRoleVOList;

    /**
     * Field _suspenseVOList
     */
    private java.util.Vector _suspenseVOList;

    /**
     * Field _contactInformationVOList
     */
    private java.util.Vector _contactInformationVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ClientDetailVO() {
        super();
        _clientAddressVOList = new Vector();
        _preferenceVOList = new Vector();
        _reinsurerVOList = new Vector();
        _taxInformationVOList = new Vector();
        _clientRoleVOList = new Vector();
        _suspenseVOList = new Vector();
        _contactInformationVOList = new Vector();
    } //-- edit.common.vo.ClientDetailVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addClientAddressVO
     * 
     * @param vClientAddressVO
     */
    public void addClientAddressVO(edit.common.vo.ClientAddressVO vClientAddressVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientAddressVO.setParentVO(this.getClass(), this);
        _clientAddressVOList.addElement(vClientAddressVO);
    } //-- void addClientAddressVO(edit.common.vo.ClientAddressVO) 

    /**
     * Method addClientAddressVO
     * 
     * @param index
     * @param vClientAddressVO
     */
    public void addClientAddressVO(int index, edit.common.vo.ClientAddressVO vClientAddressVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientAddressVO.setParentVO(this.getClass(), this);
        _clientAddressVOList.insertElementAt(vClientAddressVO, index);
    } //-- void addClientAddressVO(int, edit.common.vo.ClientAddressVO) 

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
     * Method addContactInformationVO
     * 
     * @param vContactInformationVO
     */
    public void addContactInformationVO(edit.common.vo.ContactInformationVO vContactInformationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContactInformationVO.setParentVO(this.getClass(), this);
        _contactInformationVOList.addElement(vContactInformationVO);
    } //-- void addContactInformationVO(edit.common.vo.ContactInformationVO) 

    /**
     * Method addContactInformationVO
     * 
     * @param index
     * @param vContactInformationVO
     */
    public void addContactInformationVO(int index, edit.common.vo.ContactInformationVO vContactInformationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContactInformationVO.setParentVO(this.getClass(), this);
        _contactInformationVOList.insertElementAt(vContactInformationVO, index);
    } //-- void addContactInformationVO(int, edit.common.vo.ContactInformationVO) 

    /**
     * Method addPreferenceVO
     * 
     * @param vPreferenceVO
     */
    public void addPreferenceVO(edit.common.vo.PreferenceVO vPreferenceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPreferenceVO.setParentVO(this.getClass(), this);
        _preferenceVOList.addElement(vPreferenceVO);
    } //-- void addPreferenceVO(edit.common.vo.PreferenceVO) 

    /**
     * Method addPreferenceVO
     * 
     * @param index
     * @param vPreferenceVO
     */
    public void addPreferenceVO(int index, edit.common.vo.PreferenceVO vPreferenceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPreferenceVO.setParentVO(this.getClass(), this);
        _preferenceVOList.insertElementAt(vPreferenceVO, index);
    } //-- void addPreferenceVO(int, edit.common.vo.PreferenceVO) 

    /**
     * Method addReinsurerVO
     * 
     * @param vReinsurerVO
     */
    public void addReinsurerVO(edit.common.vo.ReinsurerVO vReinsurerVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vReinsurerVO.setParentVO(this.getClass(), this);
        _reinsurerVOList.addElement(vReinsurerVO);
    } //-- void addReinsurerVO(edit.common.vo.ReinsurerVO) 

    /**
     * Method addReinsurerVO
     * 
     * @param index
     * @param vReinsurerVO
     */
    public void addReinsurerVO(int index, edit.common.vo.ReinsurerVO vReinsurerVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vReinsurerVO.setParentVO(this.getClass(), this);
        _reinsurerVOList.insertElementAt(vReinsurerVO, index);
    } //-- void addReinsurerVO(int, edit.common.vo.ReinsurerVO) 

    /**
     * Method addSuspenseVO
     * 
     * @param vSuspenseVO
     */
    public void addSuspenseVO(edit.common.vo.SuspenseVO vSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSuspenseVO.setParentVO(this.getClass(), this);
        _suspenseVOList.addElement(vSuspenseVO);
    } //-- void addSuspenseVO(edit.common.vo.SuspenseVO) 

    /**
     * Method addSuspenseVO
     * 
     * @param index
     * @param vSuspenseVO
     */
    public void addSuspenseVO(int index, edit.common.vo.SuspenseVO vSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSuspenseVO.setParentVO(this.getClass(), this);
        _suspenseVOList.insertElementAt(vSuspenseVO, index);
    } //-- void addSuspenseVO(int, edit.common.vo.SuspenseVO) 

    /**
     * Method addTaxInformationVO
     * 
     * @param vTaxInformationVO
     */
    public void addTaxInformationVO(edit.common.vo.TaxInformationVO vTaxInformationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vTaxInformationVO.setParentVO(this.getClass(), this);
        _taxInformationVOList.addElement(vTaxInformationVO);
    } //-- void addTaxInformationVO(edit.common.vo.TaxInformationVO) 

    /**
     * Method addTaxInformationVO
     * 
     * @param index
     * @param vTaxInformationVO
     */
    public void addTaxInformationVO(int index, edit.common.vo.TaxInformationVO vTaxInformationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vTaxInformationVO.setParentVO(this.getClass(), this);
        _taxInformationVOList.insertElementAt(vTaxInformationVO, index);
    } //-- void addTaxInformationVO(int, edit.common.vo.TaxInformationVO) 

    /**
     * Method enumerateClientAddressVO
     */
    public java.util.Enumeration enumerateClientAddressVO()
    {
        return _clientAddressVOList.elements();
    } //-- java.util.Enumeration enumerateClientAddressVO() 

    /**
     * Method enumerateClientRoleVO
     */
    public java.util.Enumeration enumerateClientRoleVO()
    {
        return _clientRoleVOList.elements();
    } //-- java.util.Enumeration enumerateClientRoleVO() 

    /**
     * Method enumerateContactInformationVO
     */
    public java.util.Enumeration enumerateContactInformationVO()
    {
        return _contactInformationVOList.elements();
    } //-- java.util.Enumeration enumerateContactInformationVO() 

    /**
     * Method enumeratePreferenceVO
     */
    public java.util.Enumeration enumeratePreferenceVO()
    {
        return _preferenceVOList.elements();
    } //-- java.util.Enumeration enumeratePreferenceVO() 

    /**
     * Method enumerateReinsurerVO
     */
    public java.util.Enumeration enumerateReinsurerVO()
    {
        return _reinsurerVOList.elements();
    } //-- java.util.Enumeration enumerateReinsurerVO() 

    /**
     * Method enumerateSuspenseVO
     */
    public java.util.Enumeration enumerateSuspenseVO()
    {
        return _suspenseVOList.elements();
    } //-- java.util.Enumeration enumerateSuspenseVO() 

    /**
     * Method enumerateTaxInformationVO
     */
    public java.util.Enumeration enumerateTaxInformationVO()
    {
        return _taxInformationVOList.elements();
    } //-- java.util.Enumeration enumerateTaxInformationVO() 

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
        
        if (obj instanceof ClientDetailVO) {
        
            ClientDetailVO temp = (ClientDetailVO)obj;
            if (this._clientDetailPK != temp._clientDetailPK)
                return false;
            if (this._has_clientDetailPK != temp._has_clientDetailPK)
                return false;
            if (this._clientIdentification != null) {
                if (temp._clientIdentification == null) return false;
                else if (!(this._clientIdentification.equals(temp._clientIdentification))) 
                    return false;
            }
            else if (temp._clientIdentification != null)
                return false;
            if (this._taxIdentification != null) {
                if (temp._taxIdentification == null) return false;
                else if (!(this._taxIdentification.equals(temp._taxIdentification))) 
                    return false;
            }
            else if (temp._taxIdentification != null)
                return false;
            if (this._lastName != null) {
                if (temp._lastName == null) return false;
                else if (!(this._lastName.equals(temp._lastName))) 
                    return false;
            }
            else if (temp._lastName != null)
                return false;
            if (this._firstName != null) {
                if (temp._firstName == null) return false;
                else if (!(this._firstName.equals(temp._firstName))) 
                    return false;
            }
            else if (temp._firstName != null)
                return false;
            if (this._middleName != null) {
                if (temp._middleName == null) return false;
                else if (!(this._middleName.equals(temp._middleName))) 
                    return false;
            }
            else if (temp._middleName != null)
                return false;
            if (this._namePrefix != null) {
                if (temp._namePrefix == null) return false;
                else if (!(this._namePrefix.equals(temp._namePrefix))) 
                    return false;
            }
            else if (temp._namePrefix != null)
                return false;
            if (this._nameSuffix != null) {
                if (temp._nameSuffix == null) return false;
                else if (!(this._nameSuffix.equals(temp._nameSuffix))) 
                    return false;
            }
            else if (temp._nameSuffix != null)
                return false;
            if (this._corporateName != null) {
                if (temp._corporateName == null) return false;
                else if (!(this._corporateName.equals(temp._corporateName))) 
                    return false;
            }
            else if (temp._corporateName != null)
                return false;
            if (this._birthDate != null) {
                if (temp._birthDate == null) return false;
                else if (!(this._birthDate.equals(temp._birthDate))) 
                    return false;
            }
            else if (temp._birthDate != null)
                return false;
            if (this._mothersMaidenName != null) {
                if (temp._mothersMaidenName == null) return false;
                else if (!(this._mothersMaidenName.equals(temp._mothersMaidenName))) 
                    return false;
            }
            else if (temp._mothersMaidenName != null)
                return false;
            if (this._occupation != null) {
                if (temp._occupation == null) return false;
                else if (!(this._occupation.equals(temp._occupation))) 
                    return false;
            }
            else if (temp._occupation != null)
                return false;
            if (this._dateOfDeath != null) {
                if (temp._dateOfDeath == null) return false;
                else if (!(this._dateOfDeath.equals(temp._dateOfDeath))) 
                    return false;
            }
            else if (temp._dateOfDeath != null)
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
            if (this._genderCT != null) {
                if (temp._genderCT == null) return false;
                else if (!(this._genderCT.equals(temp._genderCT))) 
                    return false;
            }
            else if (temp._genderCT != null)
                return false;
            if (this._trustTypeCT != null) {
                if (temp._trustTypeCT == null) return false;
                else if (!(this._trustTypeCT.equals(temp._trustTypeCT))) 
                    return false;
            }
            else if (temp._trustTypeCT != null)
                return false;
            if (this._statusCT != null) {
                if (temp._statusCT == null) return false;
                else if (!(this._statusCT.equals(temp._statusCT))) 
                    return false;
            }
            else if (temp._statusCT != null)
                return false;
            if (this._privacyInd != null) {
                if (temp._privacyInd == null) return false;
                else if (!(this._privacyInd.equals(temp._privacyInd))) 
                    return false;
            }
            else if (temp._privacyInd != null)
                return false;
            if (this._lastOFACCheckDate != null) {
                if (temp._lastOFACCheckDate == null) return false;
                else if (!(this._lastOFACCheckDate.equals(temp._lastOFACCheckDate))) 
                    return false;
            }
            else if (temp._lastOFACCheckDate != null)
                return false;
            if (this._stateOfDeathCT != null) {
                if (temp._stateOfDeathCT == null) return false;
                else if (!(this._stateOfDeathCT.equals(temp._stateOfDeathCT))) 
                    return false;
            }
            else if (temp._stateOfDeathCT != null)
                return false;
            if (this._residentStateAtDeathCT != null) {
                if (temp._residentStateAtDeathCT == null) return false;
                else if (!(this._residentStateAtDeathCT.equals(temp._residentStateAtDeathCT))) 
                    return false;
            }
            else if (temp._residentStateAtDeathCT != null)
                return false;
            if (this._proofOfDeathReceivedDate != null) {
                if (temp._proofOfDeathReceivedDate == null) return false;
                else if (!(this._proofOfDeathReceivedDate.equals(temp._proofOfDeathReceivedDate))) 
                    return false;
            }
            else if (temp._proofOfDeathReceivedDate != null)
                return false;
            if (this._caseTrackingProcess != null) {
                if (temp._caseTrackingProcess == null) return false;
                else if (!(this._caseTrackingProcess.equals(temp._caseTrackingProcess))) 
                    return false;
            }
            else if (temp._caseTrackingProcess != null)
                return false;
            if (this._notificationReceivedDate != null) {
                if (temp._notificationReceivedDate == null) return false;
                else if (!(this._notificationReceivedDate.equals(temp._notificationReceivedDate))) 
                    return false;
            }
            else if (temp._notificationReceivedDate != null)
                return false;
            if (this._overrideStatus != null) {
                if (temp._overrideStatus == null) return false;
                else if (!(this._overrideStatus.equals(temp._overrideStatus))) 
                    return false;
            }
            else if (temp._overrideStatus != null)
                return false;
            if (this._SICCodeCT != null) {
                if (temp._SICCodeCT == null) return false;
                else if (!(this._SICCodeCT.equals(temp._SICCodeCT))) 
                    return false;
            }
            else if (temp._SICCodeCT != null)
                return false;
            if (this._companyFK != temp._companyFK)
                return false;
            if (this._has_companyFK != temp._has_companyFK)
                return false;
            if (this._clientAddressVOList != null) {
                if (temp._clientAddressVOList == null) return false;
                else if (!(this._clientAddressVOList.equals(temp._clientAddressVOList))) 
                    return false;
            }
            else if (temp._clientAddressVOList != null)
                return false;
            if (this._preferenceVOList != null) {
                if (temp._preferenceVOList == null) return false;
                else if (!(this._preferenceVOList.equals(temp._preferenceVOList))) 
                    return false;
            }
            else if (temp._preferenceVOList != null)
                return false;
            if (this._reinsurerVOList != null) {
                if (temp._reinsurerVOList == null) return false;
                else if (!(this._reinsurerVOList.equals(temp._reinsurerVOList))) 
                    return false;
            }
            else if (temp._reinsurerVOList != null)
                return false;
            if (this._taxInformationVOList != null) {
                if (temp._taxInformationVOList == null) return false;
                else if (!(this._taxInformationVOList.equals(temp._taxInformationVOList))) 
                    return false;
            }
            else if (temp._taxInformationVOList != null)
                return false;
            if (this._clientRoleVOList != null) {
                if (temp._clientRoleVOList == null) return false;
                else if (!(this._clientRoleVOList.equals(temp._clientRoleVOList))) 
                    return false;
            }
            else if (temp._clientRoleVOList != null)
                return false;
            if (this._suspenseVOList != null) {
                if (temp._suspenseVOList == null) return false;
                else if (!(this._suspenseVOList.equals(temp._suspenseVOList))) 
                    return false;
            }
            else if (temp._suspenseVOList != null)
                return false;
            if (this._contactInformationVOList != null) {
                if (temp._contactInformationVOList == null) return false;
                else if (!(this._contactInformationVOList.equals(temp._contactInformationVOList))) 
                    return false;
            }
            else if (temp._contactInformationVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBirthDateReturns the value of field 'birthDate'.
     * 
     * @return the value of field 'birthDate'.
     */
    public java.lang.String getBirthDate()
    {
        return this._birthDate;
    } //-- java.lang.String getBirthDate() 

    /**
     * Method getCaseTrackingProcessReturns the value of field
     * 'caseTrackingProcess'.
     * 
     * @return the value of field 'caseTrackingProcess'.
     */
    public java.lang.String getCaseTrackingProcess()
    {
        return this._caseTrackingProcess;
    } //-- java.lang.String getCaseTrackingProcess() 

    /**
     * Method getClientAddressVO
     * 
     * @param index
     */
    public edit.common.vo.ClientAddressVO getClientAddressVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientAddressVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ClientAddressVO) _clientAddressVOList.elementAt(index);
    } //-- edit.common.vo.ClientAddressVO getClientAddressVO(int) 

    /**
     * Method getClientAddressVO
     */
    public edit.common.vo.ClientAddressVO[] getClientAddressVO()
    {
        int size = _clientAddressVOList.size();
        edit.common.vo.ClientAddressVO[] mArray = new edit.common.vo.ClientAddressVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ClientAddressVO) _clientAddressVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ClientAddressVO[] getClientAddressVO() 

    /**
     * Method getClientAddressVOCount
     */
    public int getClientAddressVOCount()
    {
        return _clientAddressVOList.size();
    } //-- int getClientAddressVOCount() 

    /**
     * Method getClientDetailPKReturns the value of field
     * 'clientDetailPK'.
     * 
     * @return the value of field 'clientDetailPK'.
     */
    public long getClientDetailPK()
    {
        return this._clientDetailPK;
    } //-- long getClientDetailPK() 

    /**
     * Method getClientIdentificationReturns the value of field
     * 'clientIdentification'.
     * 
     * @return the value of field 'clientIdentification'.
     */
    public java.lang.String getClientIdentification()
    {
        return this._clientIdentification;
    } //-- java.lang.String getClientIdentification() 

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
     * Method getContactInformationVO
     * 
     * @param index
     */
    public edit.common.vo.ContactInformationVO getContactInformationVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contactInformationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ContactInformationVO) _contactInformationVOList.elementAt(index);
    } //-- edit.common.vo.ContactInformationVO getContactInformationVO(int) 

    /**
     * Method getContactInformationVO
     */
    public edit.common.vo.ContactInformationVO[] getContactInformationVO()
    {
        int size = _contactInformationVOList.size();
        edit.common.vo.ContactInformationVO[] mArray = new edit.common.vo.ContactInformationVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ContactInformationVO) _contactInformationVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ContactInformationVO[] getContactInformationVO() 

    /**
     * Method getContactInformationVOCount
     */
    public int getContactInformationVOCount()
    {
        return _contactInformationVOList.size();
    } //-- int getContactInformationVOCount() 

    /**
     * Method getCorporateNameReturns the value of field
     * 'corporateName'.
     * 
     * @return the value of field 'corporateName'.
     */
    public java.lang.String getCorporateName()
    {
        return this._corporateName;
    } //-- java.lang.String getCorporateName() 

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
     * Method getFirstNameReturns the value of field 'firstName'.
     * 
     * @return the value of field 'firstName'.
     */
    public java.lang.String getFirstName()
    {
        return this._firstName;
    } //-- java.lang.String getFirstName() 

    /**
     * Method getGenderCTReturns the value of field 'genderCT'.
     * 
     * @return the value of field 'genderCT'.
     */
    public java.lang.String getGenderCT()
    {
        return this._genderCT;
    } //-- java.lang.String getGenderCT() 

    /**
     * Method getLastNameReturns the value of field 'lastName'.
     * 
     * @return the value of field 'lastName'.
     */
    public java.lang.String getLastName()
    {
        return this._lastName;
    } //-- java.lang.String getLastName() 

    /**
     * Method getLastOFACCheckDateReturns the value of field
     * 'lastOFACCheckDate'.
     * 
     * @return the value of field 'lastOFACCheckDate'.
     */
    public java.lang.String getLastOFACCheckDate()
    {
        return this._lastOFACCheckDate;
    } //-- java.lang.String getLastOFACCheckDate() 

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
     * Method getMiddleNameReturns the value of field 'middleName'.
     * 
     * @return the value of field 'middleName'.
     */
    public java.lang.String getMiddleName()
    {
        return this._middleName;
    } //-- java.lang.String getMiddleName() 

    /**
     * Method getMothersMaidenNameReturns the value of field
     * 'mothersMaidenName'.
     * 
     * @return the value of field 'mothersMaidenName'.
     */
    public java.lang.String getMothersMaidenName()
    {
        return this._mothersMaidenName;
    } //-- java.lang.String getMothersMaidenName() 

    /**
     * Method getNamePrefixReturns the value of field 'namePrefix'.
     * 
     * @return the value of field 'namePrefix'.
     */
    public java.lang.String getNamePrefix()
    {
        return this._namePrefix;
    } //-- java.lang.String getNamePrefix() 

    /**
     * Method getNameSuffixReturns the value of field 'nameSuffix'.
     * 
     * @return the value of field 'nameSuffix'.
     */
    public java.lang.String getNameSuffix()
    {
        return this._nameSuffix;
    } //-- java.lang.String getNameSuffix() 

    /**
     * Method getNotificationReceivedDateReturns the value of field
     * 'notificationReceivedDate'.
     * 
     * @return the value of field 'notificationReceivedDate'.
     */
    public java.lang.String getNotificationReceivedDate()
    {
        return this._notificationReceivedDate;
    } //-- java.lang.String getNotificationReceivedDate() 

    /**
     * Method getOccupationReturns the value of field 'occupation'.
     * 
     * @return the value of field 'occupation'.
     */
    public java.lang.String getOccupation()
    {
        return this._occupation;
    } //-- java.lang.String getOccupation() 

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
     * Method getPreferenceVO
     * 
     * @param index
     */
    public edit.common.vo.PreferenceVO getPreferenceVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _preferenceVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.PreferenceVO) _preferenceVOList.elementAt(index);
    } //-- edit.common.vo.PreferenceVO getPreferenceVO(int) 

    /**
     * Method getPreferenceVO
     */
    public edit.common.vo.PreferenceVO[] getPreferenceVO()
    {
        int size = _preferenceVOList.size();
        edit.common.vo.PreferenceVO[] mArray = new edit.common.vo.PreferenceVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.PreferenceVO) _preferenceVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.PreferenceVO[] getPreferenceVO() 

    /**
     * Method getPreferenceVOCount
     */
    public int getPreferenceVOCount()
    {
        return _preferenceVOList.size();
    } //-- int getPreferenceVOCount() 

    /**
     * Method getPrivacyIndReturns the value of field 'privacyInd'.
     * 
     * @return the value of field 'privacyInd'.
     */
    public java.lang.String getPrivacyInd()
    {
        return this._privacyInd;
    } //-- java.lang.String getPrivacyInd() 

    /**
     * Method getProofOfDeathReceivedDateReturns the value of field
     * 'proofOfDeathReceivedDate'.
     * 
     * @return the value of field 'proofOfDeathReceivedDate'.
     */
    public java.lang.String getProofOfDeathReceivedDate()
    {
        return this._proofOfDeathReceivedDate;
    } //-- java.lang.String getProofOfDeathReceivedDate() 

    /**
     * Method getReinsurerVO
     * 
     * @param index
     */
    public edit.common.vo.ReinsurerVO getReinsurerVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _reinsurerVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ReinsurerVO) _reinsurerVOList.elementAt(index);
    } //-- edit.common.vo.ReinsurerVO getReinsurerVO(int) 

    /**
     * Method getReinsurerVO
     */
    public edit.common.vo.ReinsurerVO[] getReinsurerVO()
    {
        int size = _reinsurerVOList.size();
        edit.common.vo.ReinsurerVO[] mArray = new edit.common.vo.ReinsurerVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ReinsurerVO) _reinsurerVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ReinsurerVO[] getReinsurerVO() 

    /**
     * Method getReinsurerVOCount
     */
    public int getReinsurerVOCount()
    {
        return _reinsurerVOList.size();
    } //-- int getReinsurerVOCount() 

    /**
     * Method getResidentStateAtDeathCTReturns the value of field
     * 'residentStateAtDeathCT'.
     * 
     * @return the value of field 'residentStateAtDeathCT'.
     */
    public java.lang.String getResidentStateAtDeathCT()
    {
        return this._residentStateAtDeathCT;
    } //-- java.lang.String getResidentStateAtDeathCT() 

    /**
     * Method getSICCodeCTReturns the value of field 'SICCodeCT'.
     * 
     * @return the value of field 'SICCodeCT'.
     */
    public java.lang.String getSICCodeCT()
    {
        return this._SICCodeCT;
    } //-- java.lang.String getSICCodeCT() 

    /**
     * Method getStateOfDeathCTReturns the value of field
     * 'stateOfDeathCT'.
     * 
     * @return the value of field 'stateOfDeathCT'.
     */
    public java.lang.String getStateOfDeathCT()
    {
        return this._stateOfDeathCT;
    } //-- java.lang.String getStateOfDeathCT() 

    /**
     * Method getStatusCTReturns the value of field 'statusCT'.
     * 
     * @return the value of field 'statusCT'.
     */
    public java.lang.String getStatusCT()
    {
        return this._statusCT;
    } //-- java.lang.String getStatusCT() 

    /**
     * Method getSuspenseVO
     * 
     * @param index
     */
    public edit.common.vo.SuspenseVO getSuspenseVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _suspenseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.SuspenseVO) _suspenseVOList.elementAt(index);
    } //-- edit.common.vo.SuspenseVO getSuspenseVO(int) 

    /**
     * Method getSuspenseVO
     */
    public edit.common.vo.SuspenseVO[] getSuspenseVO()
    {
        int size = _suspenseVOList.size();
        edit.common.vo.SuspenseVO[] mArray = new edit.common.vo.SuspenseVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.SuspenseVO) _suspenseVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.SuspenseVO[] getSuspenseVO() 

    /**
     * Method getSuspenseVOCount
     */
    public int getSuspenseVOCount()
    {
        return _suspenseVOList.size();
    } //-- int getSuspenseVOCount() 

    /**
     * Method getTaxIdentificationReturns the value of field
     * 'taxIdentification'.
     * 
     * @return the value of field 'taxIdentification'.
     */
    public java.lang.String getTaxIdentification()
    {
        return this._taxIdentification;
    } //-- java.lang.String getTaxIdentification() 

    /**
     * Method getTaxInformationVO
     * 
     * @param index
     */
    public edit.common.vo.TaxInformationVO getTaxInformationVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _taxInformationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.TaxInformationVO) _taxInformationVOList.elementAt(index);
    } //-- edit.common.vo.TaxInformationVO getTaxInformationVO(int) 

    /**
     * Method getTaxInformationVO
     */
    public edit.common.vo.TaxInformationVO[] getTaxInformationVO()
    {
        int size = _taxInformationVOList.size();
        edit.common.vo.TaxInformationVO[] mArray = new edit.common.vo.TaxInformationVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.TaxInformationVO) _taxInformationVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.TaxInformationVO[] getTaxInformationVO() 

    /**
     * Method getTaxInformationVOCount
     */
    public int getTaxInformationVOCount()
    {
        return _taxInformationVOList.size();
    } //-- int getTaxInformationVOCount() 

    /**
     * Method getTrustTypeCTReturns the value of field
     * 'trustTypeCT'.
     * 
     * @return the value of field 'trustTypeCT'.
     */
    public java.lang.String getTrustTypeCT()
    {
        return this._trustTypeCT;
    } //-- java.lang.String getTrustTypeCT() 

    /**
     * Method hasClientDetailPK
     */
    public boolean hasClientDetailPK()
    {
        return this._has_clientDetailPK;
    } //-- boolean hasClientDetailPK() 

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
     * Method removeAllClientAddressVO
     */
    public void removeAllClientAddressVO()
    {
        _clientAddressVOList.removeAllElements();
    } //-- void removeAllClientAddressVO() 

    /**
     * Method removeAllClientRoleVO
     */
    public void removeAllClientRoleVO()
    {
        _clientRoleVOList.removeAllElements();
    } //-- void removeAllClientRoleVO() 

    /**
     * Method removeAllContactInformationVO
     */
    public void removeAllContactInformationVO()
    {
        _contactInformationVOList.removeAllElements();
    } //-- void removeAllContactInformationVO() 

    /**
     * Method removeAllPreferenceVO
     */
    public void removeAllPreferenceVO()
    {
        _preferenceVOList.removeAllElements();
    } //-- void removeAllPreferenceVO() 

    /**
     * Method removeAllReinsurerVO
     */
    public void removeAllReinsurerVO()
    {
        _reinsurerVOList.removeAllElements();
    } //-- void removeAllReinsurerVO() 

    /**
     * Method removeAllSuspenseVO
     */
    public void removeAllSuspenseVO()
    {
        _suspenseVOList.removeAllElements();
    } //-- void removeAllSuspenseVO() 

    /**
     * Method removeAllTaxInformationVO
     */
    public void removeAllTaxInformationVO()
    {
        _taxInformationVOList.removeAllElements();
    } //-- void removeAllTaxInformationVO() 

    /**
     * Method removeClientAddressVO
     * 
     * @param index
     */
    public edit.common.vo.ClientAddressVO removeClientAddressVO(int index)
    {
        java.lang.Object obj = _clientAddressVOList.elementAt(index);
        _clientAddressVOList.removeElementAt(index);
        return (edit.common.vo.ClientAddressVO) obj;
    } //-- edit.common.vo.ClientAddressVO removeClientAddressVO(int) 

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
     * Method removeContactInformationVO
     * 
     * @param index
     */
    public edit.common.vo.ContactInformationVO removeContactInformationVO(int index)
    {
        java.lang.Object obj = _contactInformationVOList.elementAt(index);
        _contactInformationVOList.removeElementAt(index);
        return (edit.common.vo.ContactInformationVO) obj;
    } //-- edit.common.vo.ContactInformationVO removeContactInformationVO(int) 

    /**
     * Method removePreferenceVO
     * 
     * @param index
     */
    public edit.common.vo.PreferenceVO removePreferenceVO(int index)
    {
        java.lang.Object obj = _preferenceVOList.elementAt(index);
        _preferenceVOList.removeElementAt(index);
        return (edit.common.vo.PreferenceVO) obj;
    } //-- edit.common.vo.PreferenceVO removePreferenceVO(int) 

    /**
     * Method removeReinsurerVO
     * 
     * @param index
     */
    public edit.common.vo.ReinsurerVO removeReinsurerVO(int index)
    {
        java.lang.Object obj = _reinsurerVOList.elementAt(index);
        _reinsurerVOList.removeElementAt(index);
        return (edit.common.vo.ReinsurerVO) obj;
    } //-- edit.common.vo.ReinsurerVO removeReinsurerVO(int) 

    /**
     * Method removeSuspenseVO
     * 
     * @param index
     */
    public edit.common.vo.SuspenseVO removeSuspenseVO(int index)
    {
        java.lang.Object obj = _suspenseVOList.elementAt(index);
        _suspenseVOList.removeElementAt(index);
        return (edit.common.vo.SuspenseVO) obj;
    } //-- edit.common.vo.SuspenseVO removeSuspenseVO(int) 

    /**
     * Method removeTaxInformationVO
     * 
     * @param index
     */
    public edit.common.vo.TaxInformationVO removeTaxInformationVO(int index)
    {
        java.lang.Object obj = _taxInformationVOList.elementAt(index);
        _taxInformationVOList.removeElementAt(index);
        return (edit.common.vo.TaxInformationVO) obj;
    } //-- edit.common.vo.TaxInformationVO removeTaxInformationVO(int) 

    /**
     * Method setBirthDateSets the value of field 'birthDate'.
     * 
     * @param birthDate the value of field 'birthDate'.
     */
    public void setBirthDate(java.lang.String birthDate)
    {
        this._birthDate = birthDate;
        
        super.setVoChanged(true);
    } //-- void setBirthDate(java.lang.String) 

    /**
     * Method setCaseTrackingProcessSets the value of field
     * 'caseTrackingProcess'.
     * 
     * @param caseTrackingProcess the value of field
     * 'caseTrackingProcess'.
     */
    public void setCaseTrackingProcess(java.lang.String caseTrackingProcess)
    {
        this._caseTrackingProcess = caseTrackingProcess;
        
        super.setVoChanged(true);
    } //-- void setCaseTrackingProcess(java.lang.String) 

    /**
     * Method setClientAddressVO
     * 
     * @param index
     * @param vClientAddressVO
     */
    public void setClientAddressVO(int index, edit.common.vo.ClientAddressVO vClientAddressVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientAddressVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vClientAddressVO.setParentVO(this.getClass(), this);
        _clientAddressVOList.setElementAt(vClientAddressVO, index);
    } //-- void setClientAddressVO(int, edit.common.vo.ClientAddressVO) 

    /**
     * Method setClientAddressVO
     * 
     * @param clientAddressVOArray
     */
    public void setClientAddressVO(edit.common.vo.ClientAddressVO[] clientAddressVOArray)
    {
        //-- copy array
        _clientAddressVOList.removeAllElements();
        for (int i = 0; i < clientAddressVOArray.length; i++) {
            clientAddressVOArray[i].setParentVO(this.getClass(), this);
            _clientAddressVOList.addElement(clientAddressVOArray[i]);
        }
    } //-- void setClientAddressVO(edit.common.vo.ClientAddressVO) 

    /**
     * Method setClientDetailPKSets the value of field
     * 'clientDetailPK'.
     * 
     * @param clientDetailPK the value of field 'clientDetailPK'.
     */
    public void setClientDetailPK(long clientDetailPK)
    {
        this._clientDetailPK = clientDetailPK;
        
        super.setVoChanged(true);
        this._has_clientDetailPK = true;
    } //-- void setClientDetailPK(long) 

    /**
     * Method setClientIdentificationSets the value of field
     * 'clientIdentification'.
     * 
     * @param clientIdentification the value of field
     * 'clientIdentification'.
     */
    public void setClientIdentification(java.lang.String clientIdentification)
    {
        this._clientIdentification = clientIdentification;
        
        super.setVoChanged(true);
    } //-- void setClientIdentification(java.lang.String) 

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
     * Method setContactInformationVO
     * 
     * @param index
     * @param vContactInformationVO
     */
    public void setContactInformationVO(int index, edit.common.vo.ContactInformationVO vContactInformationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contactInformationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vContactInformationVO.setParentVO(this.getClass(), this);
        _contactInformationVOList.setElementAt(vContactInformationVO, index);
    } //-- void setContactInformationVO(int, edit.common.vo.ContactInformationVO) 

    /**
     * Method setContactInformationVO
     * 
     * @param contactInformationVOArray
     */
    public void setContactInformationVO(edit.common.vo.ContactInformationVO[] contactInformationVOArray)
    {
        //-- copy array
        _contactInformationVOList.removeAllElements();
        for (int i = 0; i < contactInformationVOArray.length; i++) {
            contactInformationVOArray[i].setParentVO(this.getClass(), this);
            _contactInformationVOList.addElement(contactInformationVOArray[i]);
        }
    } //-- void setContactInformationVO(edit.common.vo.ContactInformationVO) 

    /**
     * Method setCorporateNameSets the value of field
     * 'corporateName'.
     * 
     * @param corporateName the value of field 'corporateName'.
     */
    public void setCorporateName(java.lang.String corporateName)
    {
        this._corporateName = corporateName;
        
        super.setVoChanged(true);
    } //-- void setCorporateName(java.lang.String) 

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
     * Method setFirstNameSets the value of field 'firstName'.
     * 
     * @param firstName the value of field 'firstName'.
     */
    public void setFirstName(java.lang.String firstName)
    {
        this._firstName = firstName;
        
        super.setVoChanged(true);
    } //-- void setFirstName(java.lang.String) 

    /**
     * Method setGenderCTSets the value of field 'genderCT'.
     * 
     * @param genderCT the value of field 'genderCT'.
     */
    public void setGenderCT(java.lang.String genderCT)
    {
        this._genderCT = genderCT;
        
        super.setVoChanged(true);
    } //-- void setGenderCT(java.lang.String) 

    /**
     * Method setLastNameSets the value of field 'lastName'.
     * 
     * @param lastName the value of field 'lastName'.
     */
    public void setLastName(java.lang.String lastName)
    {
        this._lastName = lastName;
        
        super.setVoChanged(true);
    } //-- void setLastName(java.lang.String) 

    /**
     * Method setLastOFACCheckDateSets the value of field
     * 'lastOFACCheckDate'.
     * 
     * @param lastOFACCheckDate the value of field
     * 'lastOFACCheckDate'.
     */
    public void setLastOFACCheckDate(java.lang.String lastOFACCheckDate)
    {
        this._lastOFACCheckDate = lastOFACCheckDate;
        
        super.setVoChanged(true);
    } //-- void setLastOFACCheckDate(java.lang.String) 

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
     * Method setMiddleNameSets the value of field 'middleName'.
     * 
     * @param middleName the value of field 'middleName'.
     */
    public void setMiddleName(java.lang.String middleName)
    {
        this._middleName = middleName;
        
        super.setVoChanged(true);
    } //-- void setMiddleName(java.lang.String) 

    /**
     * Method setMothersMaidenNameSets the value of field
     * 'mothersMaidenName'.
     * 
     * @param mothersMaidenName the value of field
     * 'mothersMaidenName'.
     */
    public void setMothersMaidenName(java.lang.String mothersMaidenName)
    {
        this._mothersMaidenName = mothersMaidenName;
        
        super.setVoChanged(true);
    } //-- void setMothersMaidenName(java.lang.String) 

    /**
     * Method setNamePrefixSets the value of field 'namePrefix'.
     * 
     * @param namePrefix the value of field 'namePrefix'.
     */
    public void setNamePrefix(java.lang.String namePrefix)
    {
        this._namePrefix = namePrefix;
        
        super.setVoChanged(true);
    } //-- void setNamePrefix(java.lang.String) 

    /**
     * Method setNameSuffixSets the value of field 'nameSuffix'.
     * 
     * @param nameSuffix the value of field 'nameSuffix'.
     */
    public void setNameSuffix(java.lang.String nameSuffix)
    {
        this._nameSuffix = nameSuffix;
        
        super.setVoChanged(true);
    } //-- void setNameSuffix(java.lang.String) 

    /**
     * Method setNotificationReceivedDateSets the value of field
     * 'notificationReceivedDate'.
     * 
     * @param notificationReceivedDate the value of field
     * 'notificationReceivedDate'.
     */
    public void setNotificationReceivedDate(java.lang.String notificationReceivedDate)
    {
        this._notificationReceivedDate = notificationReceivedDate;
        
        super.setVoChanged(true);
    } //-- void setNotificationReceivedDate(java.lang.String) 

    /**
     * Method setOccupationSets the value of field 'occupation'.
     * 
     * @param occupation the value of field 'occupation'.
     */
    public void setOccupation(java.lang.String occupation)
    {
        this._occupation = occupation;
        
        super.setVoChanged(true);
    } //-- void setOccupation(java.lang.String) 

    /**
     * Method setOperatorSets the value of field 'operator'.
     * 
     * @param operator the value of field 'operator'.
     */
    public void setOperator(java.lang.String operator)
    {
    	if (operator != null && operator.length() > 10) {
    	      operator = operator.substring(0, 10);
    	}
    	
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
     * Method setPreferenceVO
     * 
     * @param index
     * @param vPreferenceVO
     */
    public void setPreferenceVO(int index, edit.common.vo.PreferenceVO vPreferenceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _preferenceVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vPreferenceVO.setParentVO(this.getClass(), this);
        _preferenceVOList.setElementAt(vPreferenceVO, index);
    } //-- void setPreferenceVO(int, edit.common.vo.PreferenceVO) 

    /**
     * Method setPreferenceVO
     * 
     * @param preferenceVOArray
     */
    public void setPreferenceVO(edit.common.vo.PreferenceVO[] preferenceVOArray)
    {
        //-- copy array
        _preferenceVOList.removeAllElements();
        for (int i = 0; i < preferenceVOArray.length; i++) {
            preferenceVOArray[i].setParentVO(this.getClass(), this);
            _preferenceVOList.addElement(preferenceVOArray[i]);
        }
    } //-- void setPreferenceVO(edit.common.vo.PreferenceVO) 

    /**
     * Method setPrivacyIndSets the value of field 'privacyInd'.
     * 
     * @param privacyInd the value of field 'privacyInd'.
     */
    public void setPrivacyInd(java.lang.String privacyInd)
    {
        this._privacyInd = privacyInd;
        
        super.setVoChanged(true);
    } //-- void setPrivacyInd(java.lang.String) 

    /**
     * Method setProofOfDeathReceivedDateSets the value of field
     * 'proofOfDeathReceivedDate'.
     * 
     * @param proofOfDeathReceivedDate the value of field
     * 'proofOfDeathReceivedDate'.
     */
    public void setProofOfDeathReceivedDate(java.lang.String proofOfDeathReceivedDate)
    {
        this._proofOfDeathReceivedDate = proofOfDeathReceivedDate;
        
        super.setVoChanged(true);
    } //-- void setProofOfDeathReceivedDate(java.lang.String) 

    /**
     * Method setReinsurerVO
     * 
     * @param index
     * @param vReinsurerVO
     */
    public void setReinsurerVO(int index, edit.common.vo.ReinsurerVO vReinsurerVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _reinsurerVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vReinsurerVO.setParentVO(this.getClass(), this);
        _reinsurerVOList.setElementAt(vReinsurerVO, index);
    } //-- void setReinsurerVO(int, edit.common.vo.ReinsurerVO) 

    /**
     * Method setReinsurerVO
     * 
     * @param reinsurerVOArray
     */
    public void setReinsurerVO(edit.common.vo.ReinsurerVO[] reinsurerVOArray)
    {
        //-- copy array
        _reinsurerVOList.removeAllElements();
        for (int i = 0; i < reinsurerVOArray.length; i++) {
            reinsurerVOArray[i].setParentVO(this.getClass(), this);
            _reinsurerVOList.addElement(reinsurerVOArray[i]);
        }
    } //-- void setReinsurerVO(edit.common.vo.ReinsurerVO) 

    /**
     * Method setResidentStateAtDeathCTSets the value of field
     * 'residentStateAtDeathCT'.
     * 
     * @param residentStateAtDeathCT the value of field
     * 'residentStateAtDeathCT'.
     */
    public void setResidentStateAtDeathCT(java.lang.String residentStateAtDeathCT)
    {
        this._residentStateAtDeathCT = residentStateAtDeathCT;
        
        super.setVoChanged(true);
    } //-- void setResidentStateAtDeathCT(java.lang.String) 

    /**
     * Method setSICCodeCTSets the value of field 'SICCodeCT'.
     * 
     * @param SICCodeCT the value of field 'SICCodeCT'.
     */
    public void setSICCodeCT(java.lang.String SICCodeCT)
    {
        this._SICCodeCT = SICCodeCT;
        
        super.setVoChanged(true);
    } //-- void setSICCodeCT(java.lang.String) 

    /**
     * Method setStateOfDeathCTSets the value of field
     * 'stateOfDeathCT'.
     * 
     * @param stateOfDeathCT the value of field 'stateOfDeathCT'.
     */
    public void setStateOfDeathCT(java.lang.String stateOfDeathCT)
    {
        this._stateOfDeathCT = stateOfDeathCT;
        
        super.setVoChanged(true);
    } //-- void setStateOfDeathCT(java.lang.String) 

    /**
     * Method setStatusCTSets the value of field 'statusCT'.
     * 
     * @param statusCT the value of field 'statusCT'.
     */
    public void setStatusCT(java.lang.String statusCT)
    {
        this._statusCT = statusCT;
        
        super.setVoChanged(true);
    } //-- void setStatusCT(java.lang.String) 

    /**
     * Method setSuspenseVO
     * 
     * @param index
     * @param vSuspenseVO
     */
    public void setSuspenseVO(int index, edit.common.vo.SuspenseVO vSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _suspenseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vSuspenseVO.setParentVO(this.getClass(), this);
        _suspenseVOList.setElementAt(vSuspenseVO, index);
    } //-- void setSuspenseVO(int, edit.common.vo.SuspenseVO) 

    /**
     * Method setSuspenseVO
     * 
     * @param suspenseVOArray
     */
    public void setSuspenseVO(edit.common.vo.SuspenseVO[] suspenseVOArray)
    {
        //-- copy array
        _suspenseVOList.removeAllElements();
        for (int i = 0; i < suspenseVOArray.length; i++) {
            suspenseVOArray[i].setParentVO(this.getClass(), this);
            _suspenseVOList.addElement(suspenseVOArray[i]);
        }
    } //-- void setSuspenseVO(edit.common.vo.SuspenseVO) 

    /**
     * Method setTaxIdentificationSets the value of field
     * 'taxIdentification'.
     * 
     * @param taxIdentification the value of field
     * 'taxIdentification'.
     */
    public void setTaxIdentification(java.lang.String taxIdentification)
    {
        this._taxIdentification = taxIdentification;
        
        super.setVoChanged(true);
    } //-- void setTaxIdentification(java.lang.String) 

    /**
     * Method setTaxInformationVO
     * 
     * @param index
     * @param vTaxInformationVO
     */
    public void setTaxInformationVO(int index, edit.common.vo.TaxInformationVO vTaxInformationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _taxInformationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vTaxInformationVO.setParentVO(this.getClass(), this);
        _taxInformationVOList.setElementAt(vTaxInformationVO, index);
    } //-- void setTaxInformationVO(int, edit.common.vo.TaxInformationVO) 

    /**
     * Method setTaxInformationVO
     * 
     * @param taxInformationVOArray
     */
    public void setTaxInformationVO(edit.common.vo.TaxInformationVO[] taxInformationVOArray)
    {
        //-- copy array
        _taxInformationVOList.removeAllElements();
        for (int i = 0; i < taxInformationVOArray.length; i++) {
            taxInformationVOArray[i].setParentVO(this.getClass(), this);
            _taxInformationVOList.addElement(taxInformationVOArray[i]);
        }
    } //-- void setTaxInformationVO(edit.common.vo.TaxInformationVO) 

    /**
     * Method setTrustTypeCTSets the value of field 'trustTypeCT'.
     * 
     * @param trustTypeCT the value of field 'trustTypeCT'.
     */
    public void setTrustTypeCT(java.lang.String trustTypeCT)
    {
        this._trustTypeCT = trustTypeCT;
        
        super.setVoChanged(true);
    } //-- void setTrustTypeCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ClientDetailVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ClientDetailVO) Unmarshaller.unmarshal(edit.common.vo.ClientDetailVO.class, reader);
    } //-- edit.common.vo.ClientDetailVO unmarshal(java.io.Reader) 

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
