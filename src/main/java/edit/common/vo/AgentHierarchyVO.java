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
 * Class AgentHierarchyVO.
 * 
 * @version $Revision$ $Date$
 */
public class AgentHierarchyVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _agentHierarchyPK
     */
    private long _agentHierarchyPK;

    /**
     * keeps track of state for field: _agentHierarchyPK
     */
    private boolean _has_agentHierarchyPK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _contractGroupFK
     */
    private long _contractGroupFK;

    /**
     * keeps track of state for field: _contractGroupFK
     */
    private boolean _has_contractGroupFK;

    /**
     * Field _agentFK
     */
    private long _agentFK;

    private Integer _commissionPhaseID;

    /**
     * keeps track of state for field: _agentFK
     */
    private boolean _has_agentFK;

    /**
     * Field _advancePremium
     */
    private java.math.BigDecimal _advancePremium;

    /**
     * Field _region
     */
    private java.lang.String _region;

    /**
     * Field _agentSnapshotVOList
     */
    private java.util.Vector _agentSnapshotVOList;

    /**
     * Field _agentHierarchyAllocationVOList
     */
    private java.util.Vector _agentHierarchyAllocationVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public AgentHierarchyVO() {
        super();
        _agentSnapshotVOList = new Vector();
        _agentHierarchyAllocationVOList = new Vector();
    } //-- edit.common.vo.AgentHierarchyVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAgentHierarchyAllocationVO
     * 
     * @param vAgentHierarchyAllocationVO
     */
    public void addAgentHierarchyAllocationVO(edit.common.vo.AgentHierarchyAllocationVO vAgentHierarchyAllocationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentHierarchyAllocationVO.setParentVO(this.getClass(), this);
        _agentHierarchyAllocationVOList.addElement(vAgentHierarchyAllocationVO);
    } //-- void addAgentHierarchyAllocationVO(edit.common.vo.AgentHierarchyAllocationVO) 

    /**
     * Method addAgentHierarchyAllocationVO
     * 
     * @param index
     * @param vAgentHierarchyAllocationVO
     */
    public void addAgentHierarchyAllocationVO(int index, edit.common.vo.AgentHierarchyAllocationVO vAgentHierarchyAllocationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentHierarchyAllocationVO.setParentVO(this.getClass(), this);
        _agentHierarchyAllocationVOList.insertElementAt(vAgentHierarchyAllocationVO, index);
    } //-- void addAgentHierarchyAllocationVO(int, edit.common.vo.AgentHierarchyAllocationVO) 

    /**
     * Method addAgentSnapshotVO
     * 
     * @param vAgentSnapshotVO
     */
    public void addAgentSnapshotVO(edit.common.vo.AgentSnapshotVO vAgentSnapshotVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentSnapshotVO.setParentVO(this.getClass(), this);
        _agentSnapshotVOList.addElement(vAgentSnapshotVO);
    } //-- void addAgentSnapshotVO(edit.common.vo.AgentSnapshotVO) 

    /**
     * Method addAgentSnapshotVO
     * 
     * @param index
     * @param vAgentSnapshotVO
     */
    public void addAgentSnapshotVO(int index, edit.common.vo.AgentSnapshotVO vAgentSnapshotVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentSnapshotVO.setParentVO(this.getClass(), this);
        _agentSnapshotVOList.insertElementAt(vAgentSnapshotVO, index);
    } //-- void addAgentSnapshotVO(int, edit.common.vo.AgentSnapshotVO) 

    /**
     * Method enumerateAgentHierarchyAllocationVO
     */
    public java.util.Enumeration enumerateAgentHierarchyAllocationVO()
    {
        return _agentHierarchyAllocationVOList.elements();
    } //-- java.util.Enumeration enumerateAgentHierarchyAllocationVO() 

    /**
     * Method enumerateAgentSnapshotVO
     */
    public java.util.Enumeration enumerateAgentSnapshotVO()
    {
        return _agentSnapshotVOList.elements();
    } //-- java.util.Enumeration enumerateAgentSnapshotVO() 

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
        
        if (obj instanceof AgentHierarchyVO) {
        
            AgentHierarchyVO temp = (AgentHierarchyVO)obj;
            if (this._agentHierarchyPK != temp._agentHierarchyPK)
                return false;
            if (this._has_agentHierarchyPK != temp._has_agentHierarchyPK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._contractGroupFK != temp._contractGroupFK)
                return false;
            if (this._has_contractGroupFK != temp._has_contractGroupFK)
                return false;
            if (this._agentFK != temp._agentFK)
                return false;
            if (this._has_agentFK != temp._has_agentFK)
                return false;

            if (this._advancePremium != null) {
                if (temp._advancePremium == null) return false;
                else if (!(this._advancePremium.equals(temp._advancePremium))) 
                    return false;
            }
            else if (temp._advancePremium != null)
                return false;

            if (this._commissionPhaseID != null) {
                if (temp._commissionPhaseID == null) return false;
                else if (!(this._commissionPhaseID.equals(temp._commissionPhaseID))) 
                    return false;
            }
            else if (temp._commissionPhaseID != null)
                return false;

            if (this._region != null) {
                if (temp._region == null) return false;
                else if (!(this._region.equals(temp._region))) 
                    return false;
            }
            else if (temp._region != null)
                return false;
            if (this._agentSnapshotVOList != null) {
                if (temp._agentSnapshotVOList == null) return false;
                else if (!(this._agentSnapshotVOList.equals(temp._agentSnapshotVOList))) 
                    return false;
            }
            else if (temp._agentSnapshotVOList != null)
                return false;
            if (this._agentHierarchyAllocationVOList != null) {
                if (temp._agentHierarchyAllocationVOList == null) return false;
                else if (!(this._agentHierarchyAllocationVOList.equals(temp._agentHierarchyAllocationVOList))) 
                    return false;
            }
            else if (temp._agentHierarchyAllocationVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAdvancePremiumReturns the value of field
     * 'advancePremium'.
     * 
     * @return the value of field 'advancePremium'.
     */
    public java.math.BigDecimal getAdvancePremium()
    {
        return this._advancePremium;
    } //-- java.math.BigDecimal getAdvancePremium() 

    public Integer getCommissionPhaseID()
    {
        return this._commissionPhaseID;
    } //-- java.math.BigDecimal getAdvancePremium() 


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
     * Method getAgentHierarchyAllocationVO
     * 
     * @param index
     */
    public edit.common.vo.AgentHierarchyAllocationVO getAgentHierarchyAllocationVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentHierarchyAllocationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AgentHierarchyAllocationVO) _agentHierarchyAllocationVOList.elementAt(index);
    } //-- edit.common.vo.AgentHierarchyAllocationVO getAgentHierarchyAllocationVO(int) 

    /**
     * Method getAgentHierarchyAllocationVO
     */
    public edit.common.vo.AgentHierarchyAllocationVO[] getAgentHierarchyAllocationVO()
    {
        int size = _agentHierarchyAllocationVOList.size();
        edit.common.vo.AgentHierarchyAllocationVO[] mArray = new edit.common.vo.AgentHierarchyAllocationVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AgentHierarchyAllocationVO) _agentHierarchyAllocationVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AgentHierarchyAllocationVO[] getAgentHierarchyAllocationVO() 

    /**
     * Method getAgentHierarchyAllocationVOCount
     */
    public int getAgentHierarchyAllocationVOCount()
    {
        return _agentHierarchyAllocationVOList.size();
    } //-- int getAgentHierarchyAllocationVOCount() 

    /**
     * Method getAgentHierarchyPKReturns the value of field
     * 'agentHierarchyPK'.
     * 
     * @return the value of field 'agentHierarchyPK'.
     */
    public long getAgentHierarchyPK()
    {
        return this._agentHierarchyPK;
    } //-- long getAgentHierarchyPK() 

    /**
     * Method getAgentSnapshotVO
     * 
     * @param index
     */
    public edit.common.vo.AgentSnapshotVO getAgentSnapshotVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentSnapshotVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AgentSnapshotVO) _agentSnapshotVOList.elementAt(index);
    } //-- edit.common.vo.AgentSnapshotVO getAgentSnapshotVO(int) 

    /**
     * Method getAgentSnapshotVO
     */
    public edit.common.vo.AgentSnapshotVO[] getAgentSnapshotVO()
    {
        int size = _agentSnapshotVOList.size();
        edit.common.vo.AgentSnapshotVO[] mArray = new edit.common.vo.AgentSnapshotVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AgentSnapshotVO) _agentSnapshotVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AgentSnapshotVO[] getAgentSnapshotVO() 

    /**
     * Method getAgentSnapshotVOCount
     */
    public int getAgentSnapshotVOCount()
    {
        return _agentSnapshotVOList.size();
    } //-- int getAgentSnapshotVOCount() 

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
     * Method getRegionReturns the value of field 'region'.
     * 
     * @return the value of field 'region'.
     */
    public java.lang.String getRegion()
    {
        return this._region;
    } //-- java.lang.String getRegion() 

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
     * Method hasAgentFK
     */
    public boolean hasAgentFK()
    {
        return this._has_agentFK;
    } //-- boolean hasAgentFK() 

    /**
     * Method hasAgentHierarchyPK
     */
    public boolean hasAgentHierarchyPK()
    {
        return this._has_agentHierarchyPK;
    } //-- boolean hasAgentHierarchyPK() 

    /**
     * Method hasContractGroupFK
     */
    public boolean hasContractGroupFK()
    {
        return this._has_contractGroupFK;
    } //-- boolean hasContractGroupFK() 

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
     * Method removeAgentHierarchyAllocationVO
     * 
     * @param index
     */
    public edit.common.vo.AgentHierarchyAllocationVO removeAgentHierarchyAllocationVO(int index)
    {
        java.lang.Object obj = _agentHierarchyAllocationVOList.elementAt(index);
        _agentHierarchyAllocationVOList.removeElementAt(index);
        return (edit.common.vo.AgentHierarchyAllocationVO) obj;
    } //-- edit.common.vo.AgentHierarchyAllocationVO removeAgentHierarchyAllocationVO(int) 

    /**
     * Method removeAgentSnapshotVO
     * 
     * @param index
     */
    public edit.common.vo.AgentSnapshotVO removeAgentSnapshotVO(int index)
    {
        java.lang.Object obj = _agentSnapshotVOList.elementAt(index);
        _agentSnapshotVOList.removeElementAt(index);
        return (edit.common.vo.AgentSnapshotVO) obj;
    } //-- edit.common.vo.AgentSnapshotVO removeAgentSnapshotVO(int) 

    /**
     * Method removeAllAgentHierarchyAllocationVO
     */
    public void removeAllAgentHierarchyAllocationVO()
    {
        _agentHierarchyAllocationVOList.removeAllElements();
    } //-- void removeAllAgentHierarchyAllocationVO() 

    /**
     * Method removeAllAgentSnapshotVO
     */
    public void removeAllAgentSnapshotVO()
    {
        _agentSnapshotVOList.removeAllElements();
    } //-- void removeAllAgentSnapshotVO() 

    /**
     * Method setAdvancePremiumSets the value of field
     * 'advancePremium'.
     * 
     * @param advancePremium the value of field 'advancePremium'.
     */
    public void setAdvancePremium(java.math.BigDecimal advancePremium)
    {
        this._advancePremium = advancePremium;
        
        super.setVoChanged(true);
    } //-- void setAdvancePremium(java.math.BigDecimal) 

    public void setCommissionPhaseID(Integer commissionPhaseID)
    {
        this._commissionPhaseID = commissionPhaseID;
        
        super.setVoChanged(true);
    } //-- void setAdvancePremium(java.math.BigDecimal) 


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
     * Method setAgentHierarchyAllocationVO
     * 
     * @param index
     * @param vAgentHierarchyAllocationVO
     */
    public void setAgentHierarchyAllocationVO(int index, edit.common.vo.AgentHierarchyAllocationVO vAgentHierarchyAllocationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentHierarchyAllocationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAgentHierarchyAllocationVO.setParentVO(this.getClass(), this);
        _agentHierarchyAllocationVOList.setElementAt(vAgentHierarchyAllocationVO, index);
    } //-- void setAgentHierarchyAllocationVO(int, edit.common.vo.AgentHierarchyAllocationVO) 

    /**
     * Method setAgentHierarchyAllocationVO
     * 
     * @param agentHierarchyAllocationVOArray
     */
    public void setAgentHierarchyAllocationVO(edit.common.vo.AgentHierarchyAllocationVO[] agentHierarchyAllocationVOArray)
    {
        //-- copy array
        _agentHierarchyAllocationVOList.removeAllElements();
        for (int i = 0; i < agentHierarchyAllocationVOArray.length; i++) {
            agentHierarchyAllocationVOArray[i].setParentVO(this.getClass(), this);
            _agentHierarchyAllocationVOList.addElement(agentHierarchyAllocationVOArray[i]);
        }
    } //-- void setAgentHierarchyAllocationVO(edit.common.vo.AgentHierarchyAllocationVO) 

    /**
     * Method setAgentHierarchyPKSets the value of field
     * 'agentHierarchyPK'.
     * 
     * @param agentHierarchyPK the value of field 'agentHierarchyPK'
     */
    public void setAgentHierarchyPK(long agentHierarchyPK)
    {
        this._agentHierarchyPK = agentHierarchyPK;
        
        super.setVoChanged(true);
        this._has_agentHierarchyPK = true;
    } //-- void setAgentHierarchyPK(long) 

    /**
     * Method setAgentSnapshotVO
     * 
     * @param index
     * @param vAgentSnapshotVO
     */
    public void setAgentSnapshotVO(int index, edit.common.vo.AgentSnapshotVO vAgentSnapshotVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentSnapshotVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAgentSnapshotVO.setParentVO(this.getClass(), this);
        _agentSnapshotVOList.setElementAt(vAgentSnapshotVO, index);
    } //-- void setAgentSnapshotVO(int, edit.common.vo.AgentSnapshotVO) 

    /**
     * Method setAgentSnapshotVO
     * 
     * @param agentSnapshotVOArray
     */
    public void setAgentSnapshotVO(edit.common.vo.AgentSnapshotVO[] agentSnapshotVOArray)
    {
        //-- copy array
        _agentSnapshotVOList.removeAllElements();
        for (int i = 0; i < agentSnapshotVOArray.length; i++) {
            agentSnapshotVOArray[i].setParentVO(this.getClass(), this);
            _agentSnapshotVOList.addElement(agentSnapshotVOArray[i]);
        }
    } //-- void setAgentSnapshotVO(edit.common.vo.AgentSnapshotVO) 

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
    public static edit.common.vo.AgentHierarchyVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AgentHierarchyVO) Unmarshaller.unmarshal(edit.common.vo.AgentHierarchyVO.class, reader);
    } //-- edit.common.vo.AgentHierarchyVO unmarshal(java.io.Reader) 

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
