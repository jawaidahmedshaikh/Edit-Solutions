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
 * Class ClientRoleVO.
 * 
 * @version $Revision$ $Date$
 */
public class ClientRoleVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _clientRolePK
     */
    private long _clientRolePK;

    /**
     * keeps track of state for field: _clientRolePK
     */
    private boolean _has_clientRolePK;

    /**
     * Field _clientDetailFK
     */
    private long _clientDetailFK;

    /**
     * keeps track of state for field: _clientDetailFK
     */
    private boolean _has_clientDetailFK;

    /**
     * Field _preferenceFK
     */
    private long _preferenceFK;

    /**
     * keeps track of state for field: _preferenceFK
     */
    private boolean _has_preferenceFK;

    /**
     * Field _taxProfileFK
     */
    private long _taxProfileFK;

    /**
     * keeps track of state for field: _taxProfileFK
     */
    private boolean _has_taxProfileFK;

    /**
     * Field _roleTypeCT
     */
    private java.lang.String _roleTypeCT;

    /**
     * Field _overrideStatus
     */
    private java.lang.String _overrideStatus;

    /**
     * Field _newIssuesEligibilityStatusCT
     */
    private java.lang.String _newIssuesEligibilityStatusCT;

    /**
     * Field _newIssuesEligibilityStartDate
     */
    private java.lang.String _newIssuesEligibilityStartDate;

    /**
     * Field _referenceID
     */
    private java.lang.String _referenceID;

    /**
     * Field _agentFK
     */
    private long _agentFK;

    /**
     * keeps track of state for field: _agentFK
     */
    private boolean _has_agentFK;

    /**
     * Field _clientRoleFinancialVOList
     */
    private java.util.Vector _clientRoleFinancialVOList;

    /**
     * Field _clientSetupVOList
     */
    private java.util.Vector _clientSetupVOList;

    /**
     * Field _contractClientVOList
     */
    private java.util.Vector _contractClientVOList;

    /**
     * Field _redirectVOList
     */
    private java.util.Vector _redirectVOList;

    /**
     * Field _placedAgentVOList
     */
    private java.util.Vector _placedAgentVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ClientRoleVO() {
        super();
        _clientRoleFinancialVOList = new Vector();
        _clientSetupVOList = new Vector();
        _contractClientVOList = new Vector();
        _redirectVOList = new Vector();
        _placedAgentVOList = new Vector();
    } //-- edit.common.vo.ClientRoleVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addClientRoleFinancialVO
     * 
     * @param vClientRoleFinancialVO
     */
    public void addClientRoleFinancialVO(edit.common.vo.ClientRoleFinancialVO vClientRoleFinancialVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientRoleFinancialVO.setParentVO(this.getClass(), this);
        _clientRoleFinancialVOList.addElement(vClientRoleFinancialVO);
    } //-- void addClientRoleFinancialVO(edit.common.vo.ClientRoleFinancialVO) 

    /**
     * Method addClientRoleFinancialVO
     * 
     * @param index
     * @param vClientRoleFinancialVO
     */
    public void addClientRoleFinancialVO(int index, edit.common.vo.ClientRoleFinancialVO vClientRoleFinancialVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientRoleFinancialVO.setParentVO(this.getClass(), this);
        _clientRoleFinancialVOList.insertElementAt(vClientRoleFinancialVO, index);
    } //-- void addClientRoleFinancialVO(int, edit.common.vo.ClientRoleFinancialVO) 

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
     * Method addContractClientVO
     * 
     * @param vContractClientVO
     */
    public void addContractClientVO(edit.common.vo.ContractClientVO vContractClientVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractClientVO.setParentVO(this.getClass(), this);
        _contractClientVOList.addElement(vContractClientVO);
    } //-- void addContractClientVO(edit.common.vo.ContractClientVO) 

    /**
     * Method addContractClientVO
     * 
     * @param index
     * @param vContractClientVO
     */
    public void addContractClientVO(int index, edit.common.vo.ContractClientVO vContractClientVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractClientVO.setParentVO(this.getClass(), this);
        _contractClientVOList.insertElementAt(vContractClientVO, index);
    } //-- void addContractClientVO(int, edit.common.vo.ContractClientVO) 

    /**
     * Method addPlacedAgentVO
     * 
     * @param vPlacedAgentVO
     */
    public void addPlacedAgentVO(edit.common.vo.PlacedAgentVO vPlacedAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPlacedAgentVO.setParentVO(this.getClass(), this);
        _placedAgentVOList.addElement(vPlacedAgentVO);
    } //-- void addPlacedAgentVO(edit.common.vo.PlacedAgentVO) 

    /**
     * Method addPlacedAgentVO
     * 
     * @param index
     * @param vPlacedAgentVO
     */
    public void addPlacedAgentVO(int index, edit.common.vo.PlacedAgentVO vPlacedAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPlacedAgentVO.setParentVO(this.getClass(), this);
        _placedAgentVOList.insertElementAt(vPlacedAgentVO, index);
    } //-- void addPlacedAgentVO(int, edit.common.vo.PlacedAgentVO) 

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
     * Method enumerateClientRoleFinancialVO
     */
    public java.util.Enumeration enumerateClientRoleFinancialVO()
    {
        return _clientRoleFinancialVOList.elements();
    } //-- java.util.Enumeration enumerateClientRoleFinancialVO() 

    /**
     * Method enumerateClientSetupVO
     */
    public java.util.Enumeration enumerateClientSetupVO()
    {
        return _clientSetupVOList.elements();
    } //-- java.util.Enumeration enumerateClientSetupVO() 

    /**
     * Method enumerateContractClientVO
     */
    public java.util.Enumeration enumerateContractClientVO()
    {
        return _contractClientVOList.elements();
    } //-- java.util.Enumeration enumerateContractClientVO() 

    /**
     * Method enumeratePlacedAgentVO
     */
    public java.util.Enumeration enumeratePlacedAgentVO()
    {
        return _placedAgentVOList.elements();
    } //-- java.util.Enumeration enumeratePlacedAgentVO() 

    /**
     * Method enumerateRedirectVO
     */
    public java.util.Enumeration enumerateRedirectVO()
    {
        return _redirectVOList.elements();
    } //-- java.util.Enumeration enumerateRedirectVO() 

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
        
        if (obj instanceof ClientRoleVO) {
        
            ClientRoleVO temp = (ClientRoleVO)obj;
            if (this._clientRolePK != temp._clientRolePK)
                return false;
            if (this._has_clientRolePK != temp._has_clientRolePK)
                return false;
            if (this._clientDetailFK != temp._clientDetailFK)
                return false;
            if (this._has_clientDetailFK != temp._has_clientDetailFK)
                return false;
            if (this._preferenceFK != temp._preferenceFK)
                return false;
            if (this._has_preferenceFK != temp._has_preferenceFK)
                return false;
            if (this._taxProfileFK != temp._taxProfileFK)
                return false;
            if (this._has_taxProfileFK != temp._has_taxProfileFK)
                return false;
            if (this._roleTypeCT != null) {
                if (temp._roleTypeCT == null) return false;
                else if (!(this._roleTypeCT.equals(temp._roleTypeCT))) 
                    return false;
            }
            else if (temp._roleTypeCT != null)
                return false;
            if (this._overrideStatus != null) {
                if (temp._overrideStatus == null) return false;
                else if (!(this._overrideStatus.equals(temp._overrideStatus))) 
                    return false;
            }
            else if (temp._overrideStatus != null)
                return false;
            if (this._newIssuesEligibilityStatusCT != null) {
                if (temp._newIssuesEligibilityStatusCT == null) return false;
                else if (!(this._newIssuesEligibilityStatusCT.equals(temp._newIssuesEligibilityStatusCT))) 
                    return false;
            }
            else if (temp._newIssuesEligibilityStatusCT != null)
                return false;
            if (this._newIssuesEligibilityStartDate != null) {
                if (temp._newIssuesEligibilityStartDate == null) return false;
                else if (!(this._newIssuesEligibilityStartDate.equals(temp._newIssuesEligibilityStartDate))) 
                    return false;
            }
            else if (temp._newIssuesEligibilityStartDate != null)
                return false;
            if (this._referenceID != null) {
                if (temp._referenceID == null) return false;
                else if (!(this._referenceID.equals(temp._referenceID))) 
                    return false;
            }
            else if (temp._referenceID != null)
                return false;
            if (this._agentFK != temp._agentFK)
                return false;
            if (this._has_agentFK != temp._has_agentFK)
                return false;
            if (this._clientRoleFinancialVOList != null) {
                if (temp._clientRoleFinancialVOList == null) return false;
                else if (!(this._clientRoleFinancialVOList.equals(temp._clientRoleFinancialVOList))) 
                    return false;
            }
            else if (temp._clientRoleFinancialVOList != null)
                return false;
            if (this._clientSetupVOList != null) {
                if (temp._clientSetupVOList == null) return false;
                else if (!(this._clientSetupVOList.equals(temp._clientSetupVOList))) 
                    return false;
            }
            else if (temp._clientSetupVOList != null)
                return false;
            if (this._contractClientVOList != null) {
                if (temp._contractClientVOList == null) return false;
                else if (!(this._contractClientVOList.equals(temp._contractClientVOList))) 
                    return false;
            }
            else if (temp._contractClientVOList != null)
                return false;
            if (this._redirectVOList != null) {
                if (temp._redirectVOList == null) return false;
                else if (!(this._redirectVOList.equals(temp._redirectVOList))) 
                    return false;
            }
            else if (temp._redirectVOList != null)
                return false;
            if (this._placedAgentVOList != null) {
                if (temp._placedAgentVOList == null) return false;
                else if (!(this._placedAgentVOList.equals(temp._placedAgentVOList))) 
                    return false;
            }
            else if (temp._placedAgentVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentFKReturns the value of field 'agentFK'.
     * 
     * @return the value of field 'agentFK'.
     */
    public long getAgentFK()
    {
        return this._agentFK;
    } //-- long getAgentFK() 

    /**
     * Method getClientDetailFKReturns the value of field
     * 'clientDetailFK'.
     * 
     * @return the value of field 'clientDetailFK'.
     */
    public long getClientDetailFK()
    {
        return this._clientDetailFK;
    } //-- long getClientDetailFK() 

    /**
     * Method getClientRoleFinancialVO
     * 
     * @param index
     */
    public edit.common.vo.ClientRoleFinancialVO getClientRoleFinancialVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientRoleFinancialVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ClientRoleFinancialVO) _clientRoleFinancialVOList.elementAt(index);
    } //-- edit.common.vo.ClientRoleFinancialVO getClientRoleFinancialVO(int) 

    /**
     * Method getClientRoleFinancialVO
     */
    public edit.common.vo.ClientRoleFinancialVO[] getClientRoleFinancialVO()
    {
        int size = _clientRoleFinancialVOList.size();
        edit.common.vo.ClientRoleFinancialVO[] mArray = new edit.common.vo.ClientRoleFinancialVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ClientRoleFinancialVO) _clientRoleFinancialVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ClientRoleFinancialVO[] getClientRoleFinancialVO() 

    /**
     * Method getClientRoleFinancialVOCount
     */
    public int getClientRoleFinancialVOCount()
    {
        return _clientRoleFinancialVOList.size();
    } //-- int getClientRoleFinancialVOCount() 

    /**
     * Method getClientRolePKReturns the value of field
     * 'clientRolePK'.
     * 
     * @return the value of field 'clientRolePK'.
     */
    public long getClientRolePK()
    {
        return this._clientRolePK;
    } //-- long getClientRolePK() 

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
     * Method getContractClientVO
     * 
     * @param index
     */
    public edit.common.vo.ContractClientVO getContractClientVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractClientVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ContractClientVO) _contractClientVOList.elementAt(index);
    } //-- edit.common.vo.ContractClientVO getContractClientVO(int) 

    /**
     * Method getContractClientVO
     */
    public edit.common.vo.ContractClientVO[] getContractClientVO()
    {
        int size = _contractClientVOList.size();
        edit.common.vo.ContractClientVO[] mArray = new edit.common.vo.ContractClientVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ContractClientVO) _contractClientVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ContractClientVO[] getContractClientVO() 

    /**
     * Method getContractClientVOCount
     */
    public int getContractClientVOCount()
    {
        return _contractClientVOList.size();
    } //-- int getContractClientVOCount() 

    /**
     * Method getNewIssuesEligibilityStartDateReturns the value of
     * field 'newIssuesEligibilityStartDate'.
     * 
     * @return the value of field 'newIssuesEligibilityStartDate'.
     */
    public java.lang.String getNewIssuesEligibilityStartDate()
    {
        return this._newIssuesEligibilityStartDate;
    } //-- java.lang.String getNewIssuesEligibilityStartDate() 

    /**
     * Method getNewIssuesEligibilityStatusCTReturns the value of
     * field 'newIssuesEligibilityStatusCT'.
     * 
     * @return the value of field 'newIssuesEligibilityStatusCT'.
     */
    public java.lang.String getNewIssuesEligibilityStatusCT()
    {
        return this._newIssuesEligibilityStatusCT;
    } //-- java.lang.String getNewIssuesEligibilityStatusCT() 

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
     * Method getPlacedAgentVO
     * 
     * @param index
     */
    public edit.common.vo.PlacedAgentVO getPlacedAgentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _placedAgentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.PlacedAgentVO) _placedAgentVOList.elementAt(index);
    } //-- edit.common.vo.PlacedAgentVO getPlacedAgentVO(int) 

    /**
     * Method getPlacedAgentVO
     */
    public edit.common.vo.PlacedAgentVO[] getPlacedAgentVO()
    {
        int size = _placedAgentVOList.size();
        edit.common.vo.PlacedAgentVO[] mArray = new edit.common.vo.PlacedAgentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.PlacedAgentVO) _placedAgentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.PlacedAgentVO[] getPlacedAgentVO() 

    /**
     * Method getPlacedAgentVOCount
     */
    public int getPlacedAgentVOCount()
    {
        return _placedAgentVOList.size();
    } //-- int getPlacedAgentVOCount() 

    /**
     * Method getPreferenceFKReturns the value of field
     * 'preferenceFK'.
     * 
     * @return the value of field 'preferenceFK'.
     */
    public long getPreferenceFK()
    {
        return this._preferenceFK;
    } //-- long getPreferenceFK() 

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
     * Method getReferenceIDReturns the value of field
     * 'referenceID'.
     * 
     * @return the value of field 'referenceID'.
     */
    public java.lang.String getReferenceID()
    {
        return this._referenceID;
    } //-- java.lang.String getReferenceID() 

    /**
     * Method getRoleTypeCTReturns the value of field 'roleTypeCT'.
     * 
     * @return the value of field 'roleTypeCT'.
     */
    public java.lang.String getRoleTypeCT()
    {
        return this._roleTypeCT;
    } //-- java.lang.String getRoleTypeCT() 

    /**
     * Method getTaxProfileFKReturns the value of field
     * 'taxProfileFK'.
     * 
     * @return the value of field 'taxProfileFK'.
     */
    public long getTaxProfileFK()
    {
        return this._taxProfileFK;
    } //-- long getTaxProfileFK() 

    /**
     * Method hasAgentFK
     */
    public boolean hasAgentFK()
    {
        return this._has_agentFK;
    } //-- boolean hasAgentFK() 

    /**
     * Method hasClientDetailFK
     */
    public boolean hasClientDetailFK()
    {
        return this._has_clientDetailFK;
    } //-- boolean hasClientDetailFK() 

    /**
     * Method hasClientRolePK
     */
    public boolean hasClientRolePK()
    {
        return this._has_clientRolePK;
    } //-- boolean hasClientRolePK() 

    /**
     * Method hasPreferenceFK
     */
    public boolean hasPreferenceFK()
    {
        return this._has_preferenceFK;
    } //-- boolean hasPreferenceFK() 

    /**
     * Method hasTaxProfileFK
     */
    public boolean hasTaxProfileFK()
    {
        return this._has_taxProfileFK;
    } //-- boolean hasTaxProfileFK() 

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
     * Method removeAllClientRoleFinancialVO
     */
    public void removeAllClientRoleFinancialVO()
    {
        _clientRoleFinancialVOList.removeAllElements();
    } //-- void removeAllClientRoleFinancialVO() 

    /**
     * Method removeAllClientSetupVO
     */
    public void removeAllClientSetupVO()
    {
        _clientSetupVOList.removeAllElements();
    } //-- void removeAllClientSetupVO() 

    /**
     * Method removeAllContractClientVO
     */
    public void removeAllContractClientVO()
    {
        _contractClientVOList.removeAllElements();
    } //-- void removeAllContractClientVO() 

    /**
     * Method removeAllPlacedAgentVO
     */
    public void removeAllPlacedAgentVO()
    {
        _placedAgentVOList.removeAllElements();
    } //-- void removeAllPlacedAgentVO() 

    /**
     * Method removeAllRedirectVO
     */
    public void removeAllRedirectVO()
    {
        _redirectVOList.removeAllElements();
    } //-- void removeAllRedirectVO() 

    /**
     * Method removeClientRoleFinancialVO
     * 
     * @param index
     */
    public edit.common.vo.ClientRoleFinancialVO removeClientRoleFinancialVO(int index)
    {
        java.lang.Object obj = _clientRoleFinancialVOList.elementAt(index);
        _clientRoleFinancialVOList.removeElementAt(index);
        return (edit.common.vo.ClientRoleFinancialVO) obj;
    } //-- edit.common.vo.ClientRoleFinancialVO removeClientRoleFinancialVO(int) 

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
     * Method removeContractClientVO
     * 
     * @param index
     */
    public edit.common.vo.ContractClientVO removeContractClientVO(int index)
    {
        java.lang.Object obj = _contractClientVOList.elementAt(index);
        _contractClientVOList.removeElementAt(index);
        return (edit.common.vo.ContractClientVO) obj;
    } //-- edit.common.vo.ContractClientVO removeContractClientVO(int) 

    /**
     * Method removePlacedAgentVO
     * 
     * @param index
     */
    public edit.common.vo.PlacedAgentVO removePlacedAgentVO(int index)
    {
        java.lang.Object obj = _placedAgentVOList.elementAt(index);
        _placedAgentVOList.removeElementAt(index);
        return (edit.common.vo.PlacedAgentVO) obj;
    } //-- edit.common.vo.PlacedAgentVO removePlacedAgentVO(int) 

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
     * Method setAgentFKSets the value of field 'agentFK'.
     * 
     * @param agentFK the value of field 'agentFK'.
     */
    public void setAgentFK(long agentFK)
    {
        this._agentFK = agentFK;
        
        super.setVoChanged(true);
        this._has_agentFK = true;
    } //-- void setAgentFK(long) 

    /**
     * Method setClientDetailFKSets the value of field
     * 'clientDetailFK'.
     * 
     * @param clientDetailFK the value of field 'clientDetailFK'.
     */
    public void setClientDetailFK(long clientDetailFK)
    {
        this._clientDetailFK = clientDetailFK;
        
        super.setVoChanged(true);
        this._has_clientDetailFK = true;
    } //-- void setClientDetailFK(long) 

    /**
     * Method setClientRoleFinancialVO
     * 
     * @param index
     * @param vClientRoleFinancialVO
     */
    public void setClientRoleFinancialVO(int index, edit.common.vo.ClientRoleFinancialVO vClientRoleFinancialVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientRoleFinancialVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vClientRoleFinancialVO.setParentVO(this.getClass(), this);
        _clientRoleFinancialVOList.setElementAt(vClientRoleFinancialVO, index);
    } //-- void setClientRoleFinancialVO(int, edit.common.vo.ClientRoleFinancialVO) 

    /**
     * Method setClientRoleFinancialVO
     * 
     * @param clientRoleFinancialVOArray
     */
    public void setClientRoleFinancialVO(edit.common.vo.ClientRoleFinancialVO[] clientRoleFinancialVOArray)
    {
        //-- copy array
        _clientRoleFinancialVOList.removeAllElements();
        for (int i = 0; i < clientRoleFinancialVOArray.length; i++) {
            clientRoleFinancialVOArray[i].setParentVO(this.getClass(), this);
            _clientRoleFinancialVOList.addElement(clientRoleFinancialVOArray[i]);
        }
    } //-- void setClientRoleFinancialVO(edit.common.vo.ClientRoleFinancialVO) 

    /**
     * Method setClientRolePKSets the value of field
     * 'clientRolePK'.
     * 
     * @param clientRolePK the value of field 'clientRolePK'.
     */
    public void setClientRolePK(long clientRolePK)
    {
        this._clientRolePK = clientRolePK;
        
        super.setVoChanged(true);
        this._has_clientRolePK = true;
    } //-- void setClientRolePK(long) 

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
     * Method setContractClientVO
     * 
     * @param index
     * @param vContractClientVO
     */
    public void setContractClientVO(int index, edit.common.vo.ContractClientVO vContractClientVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractClientVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vContractClientVO.setParentVO(this.getClass(), this);
        _contractClientVOList.setElementAt(vContractClientVO, index);
    } //-- void setContractClientVO(int, edit.common.vo.ContractClientVO) 

    /**
     * Method setContractClientVO
     * 
     * @param contractClientVOArray
     */
    public void setContractClientVO(edit.common.vo.ContractClientVO[] contractClientVOArray)
    {
        //-- copy array
        _contractClientVOList.removeAllElements();
        for (int i = 0; i < contractClientVOArray.length; i++) {
            contractClientVOArray[i].setParentVO(this.getClass(), this);
            _contractClientVOList.addElement(contractClientVOArray[i]);
        }
    } //-- void setContractClientVO(edit.common.vo.ContractClientVO) 

    /**
     * Method setNewIssuesEligibilityStartDateSets the value of
     * field 'newIssuesEligibilityStartDate'.
     * 
     * @param newIssuesEligibilityStartDate the value of field
     * 'newIssuesEligibilityStartDate'.
     */
    public void setNewIssuesEligibilityStartDate(java.lang.String newIssuesEligibilityStartDate)
    {
        this._newIssuesEligibilityStartDate = newIssuesEligibilityStartDate;
        
        super.setVoChanged(true);
    } //-- void setNewIssuesEligibilityStartDate(java.lang.String) 

    /**
     * Method setNewIssuesEligibilityStatusCTSets the value of
     * field 'newIssuesEligibilityStatusCT'.
     * 
     * @param newIssuesEligibilityStatusCT the value of field
     * 'newIssuesEligibilityStatusCT'.
     */
    public void setNewIssuesEligibilityStatusCT(java.lang.String newIssuesEligibilityStatusCT)
    {
        this._newIssuesEligibilityStatusCT = newIssuesEligibilityStatusCT;
        
        super.setVoChanged(true);
    } //-- void setNewIssuesEligibilityStatusCT(java.lang.String) 

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
     * Method setPlacedAgentVO
     * 
     * @param index
     * @param vPlacedAgentVO
     */
    public void setPlacedAgentVO(int index, edit.common.vo.PlacedAgentVO vPlacedAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _placedAgentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vPlacedAgentVO.setParentVO(this.getClass(), this);
        _placedAgentVOList.setElementAt(vPlacedAgentVO, index);
    } //-- void setPlacedAgentVO(int, edit.common.vo.PlacedAgentVO) 

    /**
     * Method setPlacedAgentVO
     * 
     * @param placedAgentVOArray
     */
    public void setPlacedAgentVO(edit.common.vo.PlacedAgentVO[] placedAgentVOArray)
    {
        //-- copy array
        _placedAgentVOList.removeAllElements();
        for (int i = 0; i < placedAgentVOArray.length; i++) {
            placedAgentVOArray[i].setParentVO(this.getClass(), this);
            _placedAgentVOList.addElement(placedAgentVOArray[i]);
        }
    } //-- void setPlacedAgentVO(edit.common.vo.PlacedAgentVO) 

    /**
     * Method setPreferenceFKSets the value of field
     * 'preferenceFK'.
     * 
     * @param preferenceFK the value of field 'preferenceFK'.
     */
    public void setPreferenceFK(long preferenceFK)
    {
        this._preferenceFK = preferenceFK;
        
        super.setVoChanged(true);
        this._has_preferenceFK = true;
    } //-- void setPreferenceFK(long) 

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
     * Method setReferenceIDSets the value of field 'referenceID'.
     * 
     * @param referenceID the value of field 'referenceID'.
     */
    public void setReferenceID(java.lang.String referenceID)
    {
        this._referenceID = referenceID;
        
        super.setVoChanged(true);
    } //-- void setReferenceID(java.lang.String) 

    /**
     * Method setRoleTypeCTSets the value of field 'roleTypeCT'.
     * 
     * @param roleTypeCT the value of field 'roleTypeCT'.
     */
    public void setRoleTypeCT(java.lang.String roleTypeCT)
    {
        this._roleTypeCT = roleTypeCT;
        
        super.setVoChanged(true);
    } //-- void setRoleTypeCT(java.lang.String) 

    /**
     * Method setTaxProfileFKSets the value of field
     * 'taxProfileFK'.
     * 
     * @param taxProfileFK the value of field 'taxProfileFK'.
     */
    public void setTaxProfileFK(long taxProfileFK)
    {
        this._taxProfileFK = taxProfileFK;
        
        super.setVoChanged(true);
        this._has_taxProfileFK = true;
    } //-- void setTaxProfileFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ClientRoleVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ClientRoleVO) Unmarshaller.unmarshal(edit.common.vo.ClientRoleVO.class, reader);
    } //-- edit.common.vo.ClientRoleVO unmarshal(java.io.Reader) 

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
