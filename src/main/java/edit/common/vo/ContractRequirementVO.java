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
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class ContractRequirementVO.
 * 
 * @version $Revision$ $Date$
 */
public class ContractRequirementVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _contractRequirementPK
     */
    private long _contractRequirementPK;

    /**
     * keeps track of state for field: _contractRequirementPK
     */
    private boolean _has_contractRequirementPK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _filteredRequirementFK
     */
    private long _filteredRequirementFK;

    /**
     * keeps track of state for field: _filteredRequirementFK
     */
    private boolean _has_filteredRequirementFK;

    /**
     * Field _requirementStatusCT
     */
    private java.lang.String _requirementStatusCT;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _followupDate
     */
    private java.lang.String _followupDate;

    /**
     * Field _freeFormDescription
     */
    private java.lang.String _freeFormDescription;

    /**
     * Field _receivedDate
     */
    private java.lang.String _receivedDate;

    /**
     * Field _executedDate
     */
    private java.lang.String _executedDate;

    /**
     * Field _requirementInformation
     */
    private java.lang.String _requirementInformation;


      //----------------/
     //- Constructors -/
    //----------------/

    public ContractRequirementVO() {
        super();
    } //-- edit.common.vo.ContractRequirementVO()


      //-----------/
     //- Methods -/
    //-----------/

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
        
        if (obj instanceof ContractRequirementVO) {
        
            ContractRequirementVO temp = (ContractRequirementVO)obj;
            if (this._contractRequirementPK != temp._contractRequirementPK)
                return false;
            if (this._has_contractRequirementPK != temp._has_contractRequirementPK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._filteredRequirementFK != temp._filteredRequirementFK)
                return false;
            if (this._has_filteredRequirementFK != temp._has_filteredRequirementFK)
                return false;
            if (this._requirementStatusCT != null) {
                if (temp._requirementStatusCT == null) return false;
                else if (!(this._requirementStatusCT.equals(temp._requirementStatusCT))) 
                    return false;
            }
            else if (temp._requirementStatusCT != null)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._followupDate != null) {
                if (temp._followupDate == null) return false;
                else if (!(this._followupDate.equals(temp._followupDate))) 
                    return false;
            }
            else if (temp._followupDate != null)
                return false;
            if (this._freeFormDescription != null) {
                if (temp._freeFormDescription == null) return false;
                else if (!(this._freeFormDescription.equals(temp._freeFormDescription))) 
                    return false;
            }
            else if (temp._freeFormDescription != null)
                return false;
            if (this._receivedDate != null) {
                if (temp._receivedDate == null) return false;
                else if (!(this._receivedDate.equals(temp._receivedDate))) 
                    return false;
            }
            else if (temp._receivedDate != null)
                return false;
            if (this._executedDate != null) {
                if (temp._executedDate == null) return false;
                else if (!(this._executedDate.equals(temp._executedDate))) 
                    return false;
            }
            else if (temp._executedDate != null)
                return false;
            if (this._requirementInformation != null) {
                if (temp._requirementInformation == null) return false;
                else if (!(this._requirementInformation.equals(temp._requirementInformation))) 
                    return false;
            }
            else if (temp._requirementInformation != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getContractRequirementPKReturns the value of field
     * 'contractRequirementPK'.
     * 
     * @return the value of field 'contractRequirementPK'.
     */
    public long getContractRequirementPK()
    {
        return this._contractRequirementPK;
    } //-- long getContractRequirementPK() 

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
     * Method getExecutedDateReturns the value of field
     * 'executedDate'.
     * 
     * @return the value of field 'executedDate'.
     */
    public java.lang.String getExecutedDate()
    {
        return this._executedDate;
    } //-- java.lang.String getExecutedDate() 

    /**
     * Method getFilteredRequirementFKReturns the value of field
     * 'filteredRequirementFK'.
     * 
     * @return the value of field 'filteredRequirementFK'.
     */
    public long getFilteredRequirementFK()
    {
        return this._filteredRequirementFK;
    } //-- long getFilteredRequirementFK() 

    /**
     * Method getFollowupDateReturns the value of field
     * 'followupDate'.
     * 
     * @return the value of field 'followupDate'.
     */
    public java.lang.String getFollowupDate()
    {
        return this._followupDate;
    } //-- java.lang.String getFollowupDate() 

    /**
     * Method getFreeFormDescriptionReturns the value of field
     * 'freeFormDescription'.
     * 
     * @return the value of field 'freeFormDescription'.
     */
    public java.lang.String getFreeFormDescription()
    {
        return this._freeFormDescription;
    } //-- java.lang.String getFreeFormDescription() 

    /**
     * Method getReceivedDateReturns the value of field
     * 'receivedDate'.
     * 
     * @return the value of field 'receivedDate'.
     */
    public java.lang.String getReceivedDate()
    {
        return this._receivedDate;
    } //-- java.lang.String getReceivedDate() 

    /**
     * Method getRequirementInformationReturns the value of field
     * 'requirementInformation'.
     * 
     * @return the value of field 'requirementInformation'.
     */
    public java.lang.String getRequirementInformation()
    {
        return this._requirementInformation;
    } //-- java.lang.String getRequirementInformation() 

    /**
     * Method getRequirementStatusCTReturns the value of field
     * 'requirementStatusCT'.
     * 
     * @return the value of field 'requirementStatusCT'.
     */
    public java.lang.String getRequirementStatusCT()
    {
        return this._requirementStatusCT;
    } //-- java.lang.String getRequirementStatusCT() 

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
     * Method hasContractRequirementPK
     */
    public boolean hasContractRequirementPK()
    {
        return this._has_contractRequirementPK;
    } //-- boolean hasContractRequirementPK() 

    /**
     * Method hasFilteredRequirementFK
     */
    public boolean hasFilteredRequirementFK()
    {
        return this._has_filteredRequirementFK;
    } //-- boolean hasFilteredRequirementFK() 

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
     * Method setContractRequirementPKSets the value of field
     * 'contractRequirementPK'.
     * 
     * @param contractRequirementPK the value of field
     * 'contractRequirementPK'.
     */
    public void setContractRequirementPK(long contractRequirementPK)
    {
        this._contractRequirementPK = contractRequirementPK;
        
        super.setVoChanged(true);
        this._has_contractRequirementPK = true;
    } //-- void setContractRequirementPK(long) 

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
     * Method setExecutedDateSets the value of field
     * 'executedDate'.
     * 
     * @param executedDate the value of field 'executedDate'.
     */
    public void setExecutedDate(java.lang.String executedDate)
    {
        this._executedDate = executedDate;
        
        super.setVoChanged(true);
    } //-- void setExecutedDate(java.lang.String) 

    /**
     * Method setFilteredRequirementFKSets the value of field
     * 'filteredRequirementFK'.
     * 
     * @param filteredRequirementFK the value of field
     * 'filteredRequirementFK'.
     */
    public void setFilteredRequirementFK(long filteredRequirementFK)
    {
        this._filteredRequirementFK = filteredRequirementFK;
        
        super.setVoChanged(true);
        this._has_filteredRequirementFK = true;
    } //-- void setFilteredRequirementFK(long) 

    /**
     * Method setFollowupDateSets the value of field
     * 'followupDate'.
     * 
     * @param followupDate the value of field 'followupDate'.
     */
    public void setFollowupDate(java.lang.String followupDate)
    {
        this._followupDate = followupDate;
        
        super.setVoChanged(true);
    } //-- void setFollowupDate(java.lang.String) 

    /**
     * Method setFreeFormDescriptionSets the value of field
     * 'freeFormDescription'.
     * 
     * @param freeFormDescription the value of field
     * 'freeFormDescription'.
     */
    public void setFreeFormDescription(java.lang.String freeFormDescription)
    {
        this._freeFormDescription = freeFormDescription;
        
        super.setVoChanged(true);
    } //-- void setFreeFormDescription(java.lang.String) 

    /**
     * Method setReceivedDateSets the value of field
     * 'receivedDate'.
     * 
     * @param receivedDate the value of field 'receivedDate'.
     */
    public void setReceivedDate(java.lang.String receivedDate)
    {
        this._receivedDate = receivedDate;
        
        super.setVoChanged(true);
    } //-- void setReceivedDate(java.lang.String) 

    /**
     * Method setRequirementInformationSets the value of field
     * 'requirementInformation'.
     * 
     * @param requirementInformation the value of field
     * 'requirementInformation'.
     */
    public void setRequirementInformation(java.lang.String requirementInformation)
    {
        this._requirementInformation = requirementInformation;
        
        super.setVoChanged(true);
    } //-- void setRequirementInformation(java.lang.String) 

    /**
     * Method setRequirementStatusCTSets the value of field
     * 'requirementStatusCT'.
     * 
     * @param requirementStatusCT the value of field
     * 'requirementStatusCT'.
     */
    public void setRequirementStatusCT(java.lang.String requirementStatusCT)
    {
        this._requirementStatusCT = requirementStatusCT;
        
        super.setVoChanged(true);
    } //-- void setRequirementStatusCT(java.lang.String) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ContractRequirementVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ContractRequirementVO) Unmarshaller.unmarshal(edit.common.vo.ContractRequirementVO.class, reader);
    } //-- edit.common.vo.ContractRequirementVO unmarshal(java.io.Reader) 

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
