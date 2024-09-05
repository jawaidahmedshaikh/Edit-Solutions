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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class CommissionVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _commissionPK
     */
    private long _commissionPK;

    /**
     * keeps track of state for field: _commissionPK
     */
    private boolean _has_commissionPK;

    /**
     * Field _allocationPercent
     */
    private java.math.BigDecimal _allocationPercent;

    /**
     * Comment describing your root element
     */
    private java.util.Vector _agentSnapshotDetailVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public CommissionVO() {
        super();
        _agentSnapshotDetailVOList = new Vector();
    } //-- edit.common.vo.CommissionVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAgentSnapshotDetailVO
     * 
     * @param vAgentSnapshotDetailVO
     */
    public void addAgentSnapshotDetailVO(edit.common.vo.AgentSnapshotDetailVO vAgentSnapshotDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentSnapshotDetailVO.setParentVO(this.getClass(), this);
        _agentSnapshotDetailVOList.addElement(vAgentSnapshotDetailVO);
    } //-- void addAgentSnapshotDetailVO(edit.common.vo.AgentSnapshotDetailVO) 

    /**
     * Method addAgentSnapshotDetailVO
     * 
     * @param index
     * @param vAgentSnapshotDetailVO
     */
    public void addAgentSnapshotDetailVO(int index, edit.common.vo.AgentSnapshotDetailVO vAgentSnapshotDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentSnapshotDetailVO.setParentVO(this.getClass(), this);
        _agentSnapshotDetailVOList.insertElementAt(vAgentSnapshotDetailVO, index);
    } //-- void addAgentSnapshotDetailVO(int, edit.common.vo.AgentSnapshotDetailVO) 

    /**
     * Method enumerateAgentSnapshotDetailVO
     */
    public java.util.Enumeration enumerateAgentSnapshotDetailVO()
    {
        return _agentSnapshotDetailVOList.elements();
    } //-- java.util.Enumeration enumerateAgentSnapshotDetailVO() 

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
        
        if (obj instanceof CommissionVO) {
        
            CommissionVO temp = (CommissionVO)obj;
            if (this._commissionPK != temp._commissionPK)
                return false;
            if (this._has_commissionPK != temp._has_commissionPK)
                return false;
            if (this._allocationPercent != null) {
                if (temp._allocationPercent == null) return false;
                else if (!(this._allocationPercent.equals(temp._allocationPercent))) 
                    return false;
            }
            else if (temp._allocationPercent != null)
                return false;
            if (this._agentSnapshotDetailVOList != null) {
                if (temp._agentSnapshotDetailVOList == null) return false;
                else if (!(this._agentSnapshotDetailVOList.equals(temp._agentSnapshotDetailVOList))) 
                    return false;
            }
            else if (temp._agentSnapshotDetailVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentSnapshotDetailVO
     * 
     * @param index
     */
    public edit.common.vo.AgentSnapshotDetailVO getAgentSnapshotDetailVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentSnapshotDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AgentSnapshotDetailVO) _agentSnapshotDetailVOList.elementAt(index);
    } //-- edit.common.vo.AgentSnapshotDetailVO getAgentSnapshotDetailVO(int) 

    /**
     * Method getAgentSnapshotDetailVO
     */
    public edit.common.vo.AgentSnapshotDetailVO[] getAgentSnapshotDetailVO()
    {
        int size = _agentSnapshotDetailVOList.size();
        edit.common.vo.AgentSnapshotDetailVO[] mArray = new edit.common.vo.AgentSnapshotDetailVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AgentSnapshotDetailVO) _agentSnapshotDetailVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AgentSnapshotDetailVO[] getAgentSnapshotDetailVO() 

    /**
     * Method getAgentSnapshotDetailVOCount
     */
    public int getAgentSnapshotDetailVOCount()
    {
        return _agentSnapshotDetailVOList.size();
    } //-- int getAgentSnapshotDetailVOCount() 

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
     * Method getCommissionPKReturns the value of field
     * 'commissionPK'.
     * 
     * @return the value of field 'commissionPK'.
     */
    public long getCommissionPK()
    {
        return this._commissionPK;
    } //-- long getCommissionPK() 

    /**
     * Method hasCommissionPK
     */
    public boolean hasCommissionPK()
    {
        return this._has_commissionPK;
    } //-- boolean hasCommissionPK() 

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
     * Method removeAgentSnapshotDetailVO
     * 
     * @param index
     */
    public edit.common.vo.AgentSnapshotDetailVO removeAgentSnapshotDetailVO(int index)
    {
        java.lang.Object obj = _agentSnapshotDetailVOList.elementAt(index);
        _agentSnapshotDetailVOList.removeElementAt(index);
        return (edit.common.vo.AgentSnapshotDetailVO) obj;
    } //-- edit.common.vo.AgentSnapshotDetailVO removeAgentSnapshotDetailVO(int) 

    /**
     * Method removeAllAgentSnapshotDetailVO
     */
    public void removeAllAgentSnapshotDetailVO()
    {
        _agentSnapshotDetailVOList.removeAllElements();
    } //-- void removeAllAgentSnapshotDetailVO() 

    /**
     * Method setAgentSnapshotDetailVO
     * 
     * @param index
     * @param vAgentSnapshotDetailVO
     */
    public void setAgentSnapshotDetailVO(int index, edit.common.vo.AgentSnapshotDetailVO vAgentSnapshotDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentSnapshotDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAgentSnapshotDetailVO.setParentVO(this.getClass(), this);
        _agentSnapshotDetailVOList.setElementAt(vAgentSnapshotDetailVO, index);
    } //-- void setAgentSnapshotDetailVO(int, edit.common.vo.AgentSnapshotDetailVO) 

    /**
     * Method setAgentSnapshotDetailVO
     * 
     * @param agentSnapshotDetailVOArray
     */
    public void setAgentSnapshotDetailVO(edit.common.vo.AgentSnapshotDetailVO[] agentSnapshotDetailVOArray)
    {
        //-- copy array
        _agentSnapshotDetailVOList.removeAllElements();
        for (int i = 0; i < agentSnapshotDetailVOArray.length; i++) {
            agentSnapshotDetailVOArray[i].setParentVO(this.getClass(), this);
            _agentSnapshotDetailVOList.addElement(agentSnapshotDetailVOArray[i]);
        }
    } //-- void setAgentSnapshotDetailVO(edit.common.vo.AgentSnapshotDetailVO) 

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
     * Method setCommissionPKSets the value of field
     * 'commissionPK'.
     * 
     * @param commissionPK the value of field 'commissionPK'.
     */
    public void setCommissionPK(long commissionPK)
    {
        this._commissionPK = commissionPK;
        
        super.setVoChanged(true);
        this._has_commissionPK = true;
    } //-- void setCommissionPK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CommissionVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CommissionVO) Unmarshaller.unmarshal(edit.common.vo.CommissionVO.class, reader);
    } //-- edit.common.vo.CommissionVO unmarshal(java.io.Reader) 

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
