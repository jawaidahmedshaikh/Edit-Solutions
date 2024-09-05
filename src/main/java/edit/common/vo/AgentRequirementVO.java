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
 * Class AgentRequirementVO.
 * 
 * @version $Revision$ $Date$
 */
public class AgentRequirementVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _agentRequirementPK
     */
    private long _agentRequirementPK;

    /**
     * keeps track of state for field: _agentRequirementPK
     */
    private boolean _has_agentRequirementPK;

    /**
     * Field _agentFK
     */
    private long _agentFK;

    /**
     * keeps track of state for field: _agentFK
     */
    private boolean _has_agentFK;

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
     * Field _receivedDate
     */
    private java.lang.String _receivedDate;


      //----------------/
     //- Constructors -/
    //----------------/

    public AgentRequirementVO() {
        super();
    } //-- edit.common.vo.AgentRequirementVO()


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
        
        if (obj instanceof AgentRequirementVO) {
        
            AgentRequirementVO temp = (AgentRequirementVO)obj;
            if (this._agentRequirementPK != temp._agentRequirementPK)
                return false;
            if (this._has_agentRequirementPK != temp._has_agentRequirementPK)
                return false;
            if (this._agentFK != temp._agentFK)
                return false;
            if (this._has_agentFK != temp._has_agentFK)
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
            if (this._receivedDate != null) {
                if (temp._receivedDate == null) return false;
                else if (!(this._receivedDate.equals(temp._receivedDate))) 
                    return false;
            }
            else if (temp._receivedDate != null)
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
     * Method getAgentRequirementPKReturns the value of field
     * 'agentRequirementPK'.
     * 
     * @return the value of field 'agentRequirementPK'.
     */
    public long getAgentRequirementPK()
    {
        return this._agentRequirementPK;
    } //-- long getAgentRequirementPK() 

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
     * Method hasAgentFK
     */
    public boolean hasAgentFK()
    {
        return this._has_agentFK;
    } //-- boolean hasAgentFK() 

    /**
     * Method hasAgentRequirementPK
     */
    public boolean hasAgentRequirementPK()
    {
        return this._has_agentRequirementPK;
    } //-- boolean hasAgentRequirementPK() 

    /**
     * Method hasFilteredRequirementFK
     */
    public boolean hasFilteredRequirementFK()
    {
        return this._has_filteredRequirementFK;
    } //-- boolean hasFilteredRequirementFK() 

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
     * Method setAgentRequirementPKSets the value of field
     * 'agentRequirementPK'.
     * 
     * @param agentRequirementPK the value of field
     * 'agentRequirementPK'.
     */
    public void setAgentRequirementPK(long agentRequirementPK)
    {
        this._agentRequirementPK = agentRequirementPK;
        
        super.setVoChanged(true);
        this._has_agentRequirementPK = true;
    } //-- void setAgentRequirementPK(long) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AgentRequirementVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AgentRequirementVO) Unmarshaller.unmarshal(edit.common.vo.AgentRequirementVO.class, reader);
    } //-- edit.common.vo.AgentRequirementVO unmarshal(java.io.Reader) 

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
