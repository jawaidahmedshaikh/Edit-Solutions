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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class UIAgentHierarchyVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _agentHierarchyAllocationVO
     */
    private edit.common.vo.AgentHierarchyAllocationVO _agentHierarchyAllocationVO;

    /**
     * Field _agentHierarchyVO
     */
    private edit.common.vo.AgentHierarchyVO _agentHierarchyVO;

    /**
     * Field _agentVO
     */
    private edit.common.vo.AgentVO _agentVO;

    /**
     * Field _coverage
     */
    private java.lang.String _coverage;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _segmentEffectiveDate;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

      //----------------/
     //- Constructors -/
    //----------------/

    public UIAgentHierarchyVO() {
        super();
    } //-- edit.common.vo.UIAgentHierarchyVO()


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
        
        if (obj instanceof UIAgentHierarchyVO) {
        
            UIAgentHierarchyVO temp = (UIAgentHierarchyVO)obj;
            if (this._agentHierarchyAllocationVO != null) {
                if (temp._agentHierarchyAllocationVO == null) return false;
                else if (!(this._agentHierarchyAllocationVO.equals(temp._agentHierarchyAllocationVO))) 
                    return false;
            }
            else if (temp._agentHierarchyAllocationVO != null)
                return false;
            if (this._agentHierarchyVO != null) {
                if (temp._agentHierarchyVO == null) return false;
                else if (!(this._agentHierarchyVO.equals(temp._agentHierarchyVO))) 
                    return false;
            }
            else if (temp._agentHierarchyVO != null)
                return false;
            if (this._agentVO != null) {
                if (temp._agentVO == null) return false;
                else if (!(this._agentVO.equals(temp._agentVO))) 
                    return false;
            }
            else if (temp._agentVO != null)
                return false;
            if (this._coverage != null) {
                if (temp._coverage == null) return false;
                else if (!(this._coverage.equals(temp._coverage))) 
                    return false;
            }
            else if (temp._coverage != null)
                return false;
            if (this._segmentEffectiveDate != null) {
                if (temp._segmentEffectiveDate == null) return false;
                else if (!(this._segmentEffectiveDate.equals(temp._segmentEffectiveDate))) 
                    return false;
            }
            else if (temp._segmentEffectiveDate != null)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentHierarchyAllocationVOReturns the value of
     * field 'agentHierarchyAllocationVO'.
     * 
     * @return the value of field 'agentHierarchyAllocationVO'.
     */
    public edit.common.vo.AgentHierarchyAllocationVO getAgentHierarchyAllocationVO()
    {
        return this._agentHierarchyAllocationVO;
    } //-- edit.common.vo.AgentHierarchyAllocationVO getAgentHierarchyAllocationVO() 

    /**
     * Method getAgentHierarchyVOReturns the value of field
     * 'agentHierarchyVO'.
     * 
     * @return the value of field 'agentHierarchyVO'.
     */
    public edit.common.vo.AgentHierarchyVO getAgentHierarchyVO()
    {
        return this._agentHierarchyVO;
    } //-- edit.common.vo.AgentHierarchyVO getAgentHierarchyVO() 

    /**
     * Method getAgentVOReturns the value of field 'agentVO'.
     * 
     * @return the value of field 'agentVO'.
     */
    public edit.common.vo.AgentVO getAgentVO()
    {
        return this._agentVO;
    } //-- edit.common.vo.AgentVO getAgentVO() 

    /**
     * Method getCoverageReturns the value of field 'coverage'.
     * 
     * @return the value of field 'coverage'.
     */
    public java.lang.String getCoverage()
    {
        return this._coverage;
    } //-- java.lang.String getCoverage() 

    public java.lang.String getSegmentEffectiveDate()
    {
        return this._segmentEffectiveDate;
    }
    
    public long getSegmentFK()
    {
        return this._segmentFK;
    }
    
    public boolean hasSegmentFK()
    {
        return this._has_segmentFK;
    }
    
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
     * Method setAgentHierarchyAllocationVOSets the value of field
     * 'agentHierarchyAllocationVO'.
     * 
     * @param agentHierarchyAllocationVO the value of field
     * 'agentHierarchyAllocationVO'.
     */
    public void setAgentHierarchyAllocationVO(edit.common.vo.AgentHierarchyAllocationVO agentHierarchyAllocationVO)
    {
        this._agentHierarchyAllocationVO = agentHierarchyAllocationVO;
    } //-- void setAgentHierarchyAllocationVO(edit.common.vo.AgentHierarchyAllocationVO) 

    /**
     * Method setAgentHierarchyVOSets the value of field
     * 'agentHierarchyVO'.
     * 
     * @param agentHierarchyVO the value of field 'agentHierarchyVO'
     */
    public void setAgentHierarchyVO(edit.common.vo.AgentHierarchyVO agentHierarchyVO)
    {
        this._agentHierarchyVO = agentHierarchyVO;
    } //-- void setAgentHierarchyVO(edit.common.vo.AgentHierarchyVO) 

    /**
     * Method setAgentVOSets the value of field 'agentVO'.
     * 
     * @param agentVO the value of field 'agentVO'.
     */
    public void setAgentVO(edit.common.vo.AgentVO agentVO)
    {
        this._agentVO = agentVO;
    } //-- void setAgentVO(edit.common.vo.AgentVO) 

    /**
     * Method setCoverageSets the value of field 'coverage'.
     * 
     * @param coverage the value of field 'coverage'.
     */
    public void setCoverage(java.lang.String coverage)
    {
        this._coverage = coverage;
        
        super.setVoChanged(true);
    } //-- void setCoverage(java.lang.String) 

    public void setSegmentEffectiveDate(java.lang.String segmentEffectiveDate)
    {
        this._segmentEffectiveDate = segmentEffectiveDate;
        
        super.setVoChanged(true);
    }
    
    public void setSegmentFK(long segmentFK)
    {
        this._segmentFK = segmentFK;
        
        super.setVoChanged(true);
        this._has_segmentFK = true;
    }
    
    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.UIAgentHierarchyVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.UIAgentHierarchyVO) Unmarshaller.unmarshal(edit.common.vo.UIAgentHierarchyVO.class, reader);
    } //-- edit.common.vo.UIAgentHierarchyVO unmarshal(java.io.Reader) 

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
