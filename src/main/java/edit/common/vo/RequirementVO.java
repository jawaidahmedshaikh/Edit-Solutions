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
 * Class RequirementVO.
 * 
 * @version $Revision$ $Date$
 */
public class RequirementVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _requirementPK
     */
    private long _requirementPK;

    /**
     * keeps track of state for field: _requirementPK
     */
    private boolean _has_requirementPK;

    /**
     * Field _requirementId
     */
    private java.lang.String _requirementId;

    /**
     * Field _requirementDescription
     */
    private java.lang.String _requirementDescription;

    /**
     * Field _followupDays
     */
    private int _followupDays;

    /**
     * keeps track of state for field: _followupDays
     */
    private boolean _has_followupDays;

    /**
     * Field _allowableStatusCT
     */
    private java.lang.String _allowableStatusCT;

    /**
     * Field _manualInd
     */
    private java.lang.String _manualInd;

    /**
     * Field _agentViewInd
     */
    private java.lang.String _agentViewInd;

    /**
     * Field _updatePolicyDeliveryDateInd
     */
    private java.lang.String _updatePolicyDeliveryDateInd;

    /**
     * Field _finalStatusCT
     */
    private java.lang.String _finalStatusCT;

    /**
     * Field _autoReceipt
     */
    private java.lang.String _autoReceipt;

    /**
     * Field _filteredRequirementVOList
     */
    private java.util.Vector _filteredRequirementVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public RequirementVO() {
        super();
        _filteredRequirementVOList = new Vector();
    } //-- edit.common.vo.RequirementVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addFilteredRequirementVO
     * 
     * @param vFilteredRequirementVO
     */
    public void addFilteredRequirementVO(edit.common.vo.FilteredRequirementVO vFilteredRequirementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredRequirementVO.setParentVO(this.getClass(), this);
        _filteredRequirementVOList.addElement(vFilteredRequirementVO);
    } //-- void addFilteredRequirementVO(edit.common.vo.FilteredRequirementVO) 

    /**
     * Method addFilteredRequirementVO
     * 
     * @param index
     * @param vFilteredRequirementVO
     */
    public void addFilteredRequirementVO(int index, edit.common.vo.FilteredRequirementVO vFilteredRequirementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredRequirementVO.setParentVO(this.getClass(), this);
        _filteredRequirementVOList.insertElementAt(vFilteredRequirementVO, index);
    } //-- void addFilteredRequirementVO(int, edit.common.vo.FilteredRequirementVO) 

    /**
     * Method enumerateFilteredRequirementVO
     */
    public java.util.Enumeration enumerateFilteredRequirementVO()
    {
        return _filteredRequirementVOList.elements();
    } //-- java.util.Enumeration enumerateFilteredRequirementVO() 

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
        
        if (obj instanceof RequirementVO) {
        
            RequirementVO temp = (RequirementVO)obj;
            if (this._requirementPK != temp._requirementPK)
                return false;
            if (this._has_requirementPK != temp._has_requirementPK)
                return false;
            if (this._requirementId != null) {
                if (temp._requirementId == null) return false;
                else if (!(this._requirementId.equals(temp._requirementId))) 
                    return false;
            }
            else if (temp._requirementId != null)
                return false;
            if (this._requirementDescription != null) {
                if (temp._requirementDescription == null) return false;
                else if (!(this._requirementDescription.equals(temp._requirementDescription))) 
                    return false;
            }
            else if (temp._requirementDescription != null)
                return false;
            if (this._followupDays != temp._followupDays)
                return false;
            if (this._has_followupDays != temp._has_followupDays)
                return false;
            if (this._allowableStatusCT != null) {
                if (temp._allowableStatusCT == null) return false;
                else if (!(this._allowableStatusCT.equals(temp._allowableStatusCT))) 
                    return false;
            }
            else if (temp._allowableStatusCT != null)
                return false;
            if (this._manualInd != null) {
                if (temp._manualInd == null) return false;
                else if (!(this._manualInd.equals(temp._manualInd))) 
                    return false;
            }
            else if (temp._manualInd != null)
                return false;
            if (this._agentViewInd != null) {
                if (temp._agentViewInd == null) return false;
                else if (!(this._agentViewInd.equals(temp._agentViewInd))) 
                    return false;
            }
            else if (temp._agentViewInd != null)
                return false;
            if (this._updatePolicyDeliveryDateInd != null) {
                if (temp._updatePolicyDeliveryDateInd == null) return false;
                else if (!(this._updatePolicyDeliveryDateInd.equals(temp._updatePolicyDeliveryDateInd))) 
                    return false;
            }
            else if (temp._updatePolicyDeliveryDateInd != null)
                return false;
            if (this._finalStatusCT != null) {
                if (temp._finalStatusCT == null) return false;
                else if (!(this._finalStatusCT.equals(temp._finalStatusCT))) 
                    return false;
            }
            else if (temp._finalStatusCT != null)
                return false;
            if (this._autoReceipt != null) {
                if (temp._autoReceipt == null) return false;
                else if (!(this._autoReceipt.equals(temp._autoReceipt))) 
                    return false;
            }
            else if (temp._autoReceipt != null)
                return false;
            if (this._filteredRequirementVOList != null) {
                if (temp._filteredRequirementVOList == null) return false;
                else if (!(this._filteredRequirementVOList.equals(temp._filteredRequirementVOList))) 
                    return false;
            }
            else if (temp._filteredRequirementVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentViewIndReturns the value of field
     * 'agentViewInd'.
     * 
     * @return the value of field 'agentViewInd'.
     */
    public java.lang.String getAgentViewInd()
    {
        return this._agentViewInd;
    } //-- java.lang.String getAgentViewInd() 

    /**
     * Method getAllowableStatusCTReturns the value of field
     * 'allowableStatusCT'.
     * 
     * @return the value of field 'allowableStatusCT'.
     */
    public java.lang.String getAllowableStatusCT()
    {
        return this._allowableStatusCT;
    } //-- java.lang.String getAllowableStatusCT() 

    /**
     * Method getAutoReceiptReturns the value of field
     * 'autoReceipt'.
     * 
     * @return the value of field 'autoReceipt'.
     */
    public java.lang.String getAutoReceipt()
    {
        return this._autoReceipt;
    } //-- java.lang.String getAutoReceipt() 

    /**
     * Method getFilteredRequirementVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredRequirementVO getFilteredRequirementVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredRequirementVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.FilteredRequirementVO) _filteredRequirementVOList.elementAt(index);
    } //-- edit.common.vo.FilteredRequirementVO getFilteredRequirementVO(int) 

    /**
     * Method getFilteredRequirementVO
     */
    public edit.common.vo.FilteredRequirementVO[] getFilteredRequirementVO()
    {
        int size = _filteredRequirementVOList.size();
        edit.common.vo.FilteredRequirementVO[] mArray = new edit.common.vo.FilteredRequirementVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.FilteredRequirementVO) _filteredRequirementVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.FilteredRequirementVO[] getFilteredRequirementVO() 

    /**
     * Method getFilteredRequirementVOCount
     */
    public int getFilteredRequirementVOCount()
    {
        return _filteredRequirementVOList.size();
    } //-- int getFilteredRequirementVOCount() 

    /**
     * Method getFinalStatusCTReturns the value of field
     * 'finalStatusCT'.
     * 
     * @return the value of field 'finalStatusCT'.
     */
    public java.lang.String getFinalStatusCT()
    {
        return this._finalStatusCT;
    } //-- java.lang.String getFinalStatusCT() 

    /**
     * Method getFollowupDaysReturns the value of field
     * 'followupDays'.
     * 
     * @return the value of field 'followupDays'.
     */
    public int getFollowupDays()
    {
        return this._followupDays;
    } //-- int getFollowupDays() 

    /**
     * Method getManualIndReturns the value of field 'manualInd'.
     * 
     * @return the value of field 'manualInd'.
     */
    public java.lang.String getManualInd()
    {
        return this._manualInd;
    } //-- java.lang.String getManualInd() 

    /**
     * Method getRequirementDescriptionReturns the value of field
     * 'requirementDescription'.
     * 
     * @return the value of field 'requirementDescription'.
     */
    public java.lang.String getRequirementDescription()
    {
        return this._requirementDescription;
    } //-- java.lang.String getRequirementDescription() 

    /**
     * Method getRequirementIdReturns the value of field
     * 'requirementId'.
     * 
     * @return the value of field 'requirementId'.
     */
    public java.lang.String getRequirementId()
    {
        return this._requirementId;
    } //-- java.lang.String getRequirementId() 

    /**
     * Method getRequirementPKReturns the value of field
     * 'requirementPK'.
     * 
     * @return the value of field 'requirementPK'.
     */
    public long getRequirementPK()
    {
        return this._requirementPK;
    } //-- long getRequirementPK() 

    /**
     * Method getUpdatePolicyDeliveryDateIndReturns the value of
     * field 'updatePolicyDeliveryDateInd'.
     * 
     * @return the value of field 'updatePolicyDeliveryDateInd'.
     */
    public java.lang.String getUpdatePolicyDeliveryDateInd()
    {
        return this._updatePolicyDeliveryDateInd;
    } //-- java.lang.String getUpdatePolicyDeliveryDateInd() 

    /**
     * Method hasFollowupDays
     */
    public boolean hasFollowupDays()
    {
        return this._has_followupDays;
    } //-- boolean hasFollowupDays() 

    /**
     * Method hasRequirementPK
     */
    public boolean hasRequirementPK()
    {
        return this._has_requirementPK;
    } //-- boolean hasRequirementPK() 

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
     * Method removeAllFilteredRequirementVO
     */
    public void removeAllFilteredRequirementVO()
    {
        _filteredRequirementVOList.removeAllElements();
    } //-- void removeAllFilteredRequirementVO() 

    /**
     * Method removeFilteredRequirementVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredRequirementVO removeFilteredRequirementVO(int index)
    {
        java.lang.Object obj = _filteredRequirementVOList.elementAt(index);
        _filteredRequirementVOList.removeElementAt(index);
        return (edit.common.vo.FilteredRequirementVO) obj;
    } //-- edit.common.vo.FilteredRequirementVO removeFilteredRequirementVO(int) 

    /**
     * Method setAgentViewIndSets the value of field
     * 'agentViewInd'.
     * 
     * @param agentViewInd the value of field 'agentViewInd'.
     */
    public void setAgentViewInd(java.lang.String agentViewInd)
    {
        this._agentViewInd = agentViewInd;
        
        super.setVoChanged(true);
    } //-- void setAgentViewInd(java.lang.String) 

    /**
     * Method setAllowableStatusCTSets the value of field
     * 'allowableStatusCT'.
     * 
     * @param allowableStatusCT the value of field
     * 'allowableStatusCT'.
     */
    public void setAllowableStatusCT(java.lang.String allowableStatusCT)
    {
        this._allowableStatusCT = allowableStatusCT;
        
        super.setVoChanged(true);
    } //-- void setAllowableStatusCT(java.lang.String) 

    /**
     * Method setAutoReceiptSets the value of field 'autoReceipt'.
     * 
     * @param autoReceipt the value of field 'autoReceipt'.
     */
    public void setAutoReceipt(java.lang.String autoReceipt)
    {
        this._autoReceipt = autoReceipt;
        
        super.setVoChanged(true);
    } //-- void setAutoReceipt(java.lang.String) 

    /**
     * Method setFilteredRequirementVO
     * 
     * @param index
     * @param vFilteredRequirementVO
     */
    public void setFilteredRequirementVO(int index, edit.common.vo.FilteredRequirementVO vFilteredRequirementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredRequirementVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vFilteredRequirementVO.setParentVO(this.getClass(), this);
        _filteredRequirementVOList.setElementAt(vFilteredRequirementVO, index);
    } //-- void setFilteredRequirementVO(int, edit.common.vo.FilteredRequirementVO) 

    /**
     * Method setFilteredRequirementVO
     * 
     * @param filteredRequirementVOArray
     */
    public void setFilteredRequirementVO(edit.common.vo.FilteredRequirementVO[] filteredRequirementVOArray)
    {
        //-- copy array
        _filteredRequirementVOList.removeAllElements();
        for (int i = 0; i < filteredRequirementVOArray.length; i++) {
            filteredRequirementVOArray[i].setParentVO(this.getClass(), this);
            _filteredRequirementVOList.addElement(filteredRequirementVOArray[i]);
        }
    } //-- void setFilteredRequirementVO(edit.common.vo.FilteredRequirementVO) 

    /**
     * Method setFinalStatusCTSets the value of field
     * 'finalStatusCT'.
     * 
     * @param finalStatusCT the value of field 'finalStatusCT'.
     */
    public void setFinalStatusCT(java.lang.String finalStatusCT)
    {
        this._finalStatusCT = finalStatusCT;
        
        super.setVoChanged(true);
    } //-- void setFinalStatusCT(java.lang.String) 

    /**
     * Method setFollowupDaysSets the value of field
     * 'followupDays'.
     * 
     * @param followupDays the value of field 'followupDays'.
     */
    public void setFollowupDays(int followupDays)
    {
        this._followupDays = followupDays;
        
        super.setVoChanged(true);
        this._has_followupDays = true;
    } //-- void setFollowupDays(int) 

    /**
     * Method setManualIndSets the value of field 'manualInd'.
     * 
     * @param manualInd the value of field 'manualInd'.
     */
    public void setManualInd(java.lang.String manualInd)
    {
        this._manualInd = manualInd;
        
        super.setVoChanged(true);
    } //-- void setManualInd(java.lang.String) 

    /**
     * Method setRequirementDescriptionSets the value of field
     * 'requirementDescription'.
     * 
     * @param requirementDescription the value of field
     * 'requirementDescription'.
     */
    public void setRequirementDescription(java.lang.String requirementDescription)
    {
        this._requirementDescription = requirementDescription;
        
        super.setVoChanged(true);
    } //-- void setRequirementDescription(java.lang.String) 

    /**
     * Method setRequirementIdSets the value of field
     * 'requirementId'.
     * 
     * @param requirementId the value of field 'requirementId'.
     */
    public void setRequirementId(java.lang.String requirementId)
    {
        this._requirementId = requirementId;
        
        super.setVoChanged(true);
    } //-- void setRequirementId(java.lang.String) 

    /**
     * Method setRequirementPKSets the value of field
     * 'requirementPK'.
     * 
     * @param requirementPK the value of field 'requirementPK'.
     */
    public void setRequirementPK(long requirementPK)
    {
        this._requirementPK = requirementPK;
        
        super.setVoChanged(true);
        this._has_requirementPK = true;
    } //-- void setRequirementPK(long) 

    /**
     * Method setUpdatePolicyDeliveryDateIndSets the value of field
     * 'updatePolicyDeliveryDateInd'.
     * 
     * @param updatePolicyDeliveryDateInd the value of field
     * 'updatePolicyDeliveryDateInd'.
     */
    public void setUpdatePolicyDeliveryDateInd(java.lang.String updatePolicyDeliveryDateInd)
    {
        this._updatePolicyDeliveryDateInd = updatePolicyDeliveryDateInd;
        
        super.setVoChanged(true);
    } //-- void setUpdatePolicyDeliveryDateInd(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.RequirementVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.RequirementVO) Unmarshaller.unmarshal(edit.common.vo.RequirementVO.class, reader);
    } //-- edit.common.vo.RequirementVO unmarshal(java.io.Reader) 

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
