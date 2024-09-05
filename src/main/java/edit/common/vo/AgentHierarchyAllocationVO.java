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
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class AgentHierarchyAllocationVO.
 * 
 * @version $Revision$ $Date$
 */
public class AgentHierarchyAllocationVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _agentHierarchyAllocationPK
     */
    private long _agentHierarchyAllocationPK;

    /**
     * keeps track of state for field: _agentHierarchyAllocationPK
     */
    private boolean _has_agentHierarchyAllocationPK;

    /**
     * Field _agentHierarchyFK
     */
    private long _agentHierarchyFK;

    /**
     * keeps track of state for field: _agentHierarchyFK
     */
    private boolean _has_agentHierarchyFK;

    /**
     * Field _allocationPercent
     */
    private java.math.BigDecimal _allocationPercent;

    /**
     * Field _startDate
     */
    private java.lang.String _startDate;

    /**
     * Field _stopDate
     */
    private java.lang.String _stopDate;


      //----------------/
     //- Constructors -/
    //----------------/

    public AgentHierarchyAllocationVO() {
        super();
    } //-- edit.common.vo.AgentHierarchyAllocationVO()


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
        
        if (obj instanceof AgentHierarchyAllocationVO) {
        
            AgentHierarchyAllocationVO temp = (AgentHierarchyAllocationVO)obj;
            if (this._agentHierarchyAllocationPK != temp._agentHierarchyAllocationPK)
                return false;
            if (this._has_agentHierarchyAllocationPK != temp._has_agentHierarchyAllocationPK)
                return false;
            if (this._agentHierarchyFK != temp._agentHierarchyFK)
                return false;
            if (this._has_agentHierarchyFK != temp._has_agentHierarchyFK)
                return false;
            if (this._allocationPercent != null) {
                if (temp._allocationPercent == null) return false;
                else if (!(this._allocationPercent.equals(temp._allocationPercent))) 
                    return false;
            }
            else if (temp._allocationPercent != null)
                return false;
            if (this._startDate != null) {
                if (temp._startDate == null) return false;
                else if (!(this._startDate.equals(temp._startDate))) 
                    return false;
            }
            else if (temp._startDate != null)
                return false;
            if (this._stopDate != null) {
                if (temp._stopDate == null) return false;
                else if (!(this._stopDate.equals(temp._stopDate))) 
                    return false;
            }
            else if (temp._stopDate != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentHierarchyAllocationPKReturns the value of
     * field 'agentHierarchyAllocationPK'.
     * 
     * @return the value of field 'agentHierarchyAllocationPK'.
     */
    public long getAgentHierarchyAllocationPK()
    {
        return this._agentHierarchyAllocationPK;
    } //-- long getAgentHierarchyAllocationPK() 

    /**
     * Method getAgentHierarchyFKReturns the value of field
     * 'agentHierarchyFK'.
     * 
     * @return the value of field 'agentHierarchyFK'.
     */
    public long getAgentHierarchyFK()
    {
        return this._agentHierarchyFK;
    } //-- long getAgentHierarchyFK() 

    /**
     * Method getAllocationPercentReturns the value of field
     * 'allocationPercent'.
     * 
     * @return the value of field 'allocationPercent'.
     */
    public java.math.BigDecimal getAllocationPercent()
    {
        return this._allocationPercent;
    } //-- java.math.BigDecimal getAllocationPercent() 

    /**
     * Method getStartDateReturns the value of field 'startDate'.
     * 
     * @return the value of field 'startDate'.
     */
    public java.lang.String getStartDate()
    {
        return this._startDate;
    } //-- java.lang.String getStartDate() 

    /**
     * Method getStopDateReturns the value of field 'stopDate'.
     * 
     * @return the value of field 'stopDate'.
     */
    public java.lang.String getStopDate()
    {
        return this._stopDate;
    } //-- java.lang.String getStopDate() 

    /**
     * Method hasAgentHierarchyAllocationPK
     */
    public boolean hasAgentHierarchyAllocationPK()
    {
        return this._has_agentHierarchyAllocationPK;
    } //-- boolean hasAgentHierarchyAllocationPK() 

    /**
     * Method hasAgentHierarchyFK
     */
    public boolean hasAgentHierarchyFK()
    {
        return this._has_agentHierarchyFK;
    } //-- boolean hasAgentHierarchyFK() 

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
     * Method setAgentHierarchyAllocationPKSets the value of field
     * 'agentHierarchyAllocationPK'.
     * 
     * @param agentHierarchyAllocationPK the value of field
     * 'agentHierarchyAllocationPK'.
     */
    public void setAgentHierarchyAllocationPK(long agentHierarchyAllocationPK)
    {
        this._agentHierarchyAllocationPK = agentHierarchyAllocationPK;
        
        super.setVoChanged(true);
        this._has_agentHierarchyAllocationPK = true;
    } //-- void setAgentHierarchyAllocationPK(long) 

    /**
     * Method setAgentHierarchyFKSets the value of field
     * 'agentHierarchyFK'.
     * 
     * @param agentHierarchyFK the value of field 'agentHierarchyFK'
     */
    public void setAgentHierarchyFK(long agentHierarchyFK)
    {
        this._agentHierarchyFK = agentHierarchyFK;
        
        super.setVoChanged(true);
        this._has_agentHierarchyFK = true;
    } //-- void setAgentHierarchyFK(long) 

    /**
     * Method setAllocationPercentSets the value of field
     * 'allocationPercent'.
     * 
     * @param allocationPercent the value of field
     * 'allocationPercent'.
     */
    public void setAllocationPercent(java.math.BigDecimal allocationPercent)
    {
        this._allocationPercent = allocationPercent;
        
        super.setVoChanged(true);
    } //-- void setAllocationPercent(java.math.BigDecimal) 

    /**
     * Method setStartDateSets the value of field 'startDate'.
     * 
     * @param startDate the value of field 'startDate'.
     */
    public void setStartDate(java.lang.String startDate)
    {
        this._startDate = startDate;
        
        super.setVoChanged(true);
    } //-- void setStartDate(java.lang.String) 

    /**
     * Method setStopDateSets the value of field 'stopDate'.
     * 
     * @param stopDate the value of field 'stopDate'.
     */
    public void setStopDate(java.lang.String stopDate)
    {
        this._stopDate = stopDate;
        
        super.setVoChanged(true);
    } //-- void setStopDate(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AgentHierarchyAllocationVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AgentHierarchyAllocationVO) Unmarshaller.unmarshal(edit.common.vo.AgentHierarchyAllocationVO.class, reader);
    } //-- edit.common.vo.AgentHierarchyAllocationVO unmarshal(java.io.Reader) 

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
