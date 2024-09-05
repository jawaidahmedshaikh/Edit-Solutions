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
 * Class CommissionContractVO.
 * 
 * @version $Revision$ $Date$
 */
public class CommissionContractVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _commissionContractPK
     */
    private long _commissionContractPK;

    /**
     * keeps track of state for field: _commissionContractPK
     */
    private boolean _has_commissionContractPK;

    /**
     * Field _contractCode
     */
    private java.lang.String _contractCode;

    /**
     * Field _agentContractVOList
     */
    private java.util.Vector _agentContractVOList;

    /**
     * Field _commissionLevelVOList
     */
    private java.util.Vector _commissionLevelVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public CommissionContractVO() {
        super();
        _agentContractVOList = new Vector();
        _commissionLevelVOList = new Vector();
    } //-- edit.common.vo.CommissionContractVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAgentContractVO
     * 
     * @param vAgentContractVO
     */
    public void addAgentContractVO(edit.common.vo.AgentContractVO vAgentContractVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentContractVO.setParentVO(this.getClass(), this);
        _agentContractVOList.addElement(vAgentContractVO);
    } //-- void addAgentContractVO(edit.common.vo.AgentContractVO) 

    /**
     * Method addAgentContractVO
     * 
     * @param index
     * @param vAgentContractVO
     */
    public void addAgentContractVO(int index, edit.common.vo.AgentContractVO vAgentContractVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentContractVO.setParentVO(this.getClass(), this);
        _agentContractVOList.insertElementAt(vAgentContractVO, index);
    } //-- void addAgentContractVO(int, edit.common.vo.AgentContractVO) 

    /**
     * Method addCommissionLevelVO
     * 
     * @param vCommissionLevelVO
     */
    public void addCommissionLevelVO(edit.common.vo.CommissionLevelVO vCommissionLevelVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionLevelVO.setParentVO(this.getClass(), this);
        _commissionLevelVOList.addElement(vCommissionLevelVO);
    } //-- void addCommissionLevelVO(edit.common.vo.CommissionLevelVO) 

    /**
     * Method addCommissionLevelVO
     * 
     * @param index
     * @param vCommissionLevelVO
     */
    public void addCommissionLevelVO(int index, edit.common.vo.CommissionLevelVO vCommissionLevelVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionLevelVO.setParentVO(this.getClass(), this);
        _commissionLevelVOList.insertElementAt(vCommissionLevelVO, index);
    } //-- void addCommissionLevelVO(int, edit.common.vo.CommissionLevelVO) 

    /**
     * Method enumerateAgentContractVO
     */
    public java.util.Enumeration enumerateAgentContractVO()
    {
        return _agentContractVOList.elements();
    } //-- java.util.Enumeration enumerateAgentContractVO() 

    /**
     * Method enumerateCommissionLevelVO
     */
    public java.util.Enumeration enumerateCommissionLevelVO()
    {
        return _commissionLevelVOList.elements();
    } //-- java.util.Enumeration enumerateCommissionLevelVO() 

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
        
        if (obj instanceof CommissionContractVO) {
        
            CommissionContractVO temp = (CommissionContractVO)obj;
            if (this._commissionContractPK != temp._commissionContractPK)
                return false;
            if (this._has_commissionContractPK != temp._has_commissionContractPK)
                return false;
            if (this._contractCode != null) {
                if (temp._contractCode == null) return false;
                else if (!(this._contractCode.equals(temp._contractCode))) 
                    return false;
            }
            else if (temp._contractCode != null)
                return false;
            if (this._agentContractVOList != null) {
                if (temp._agentContractVOList == null) return false;
                else if (!(this._agentContractVOList.equals(temp._agentContractVOList))) 
                    return false;
            }
            else if (temp._agentContractVOList != null)
                return false;
            if (this._commissionLevelVOList != null) {
                if (temp._commissionLevelVOList == null) return false;
                else if (!(this._commissionLevelVOList.equals(temp._commissionLevelVOList))) 
                    return false;
            }
            else if (temp._commissionLevelVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentContractVO
     * 
     * @param index
     */
    public edit.common.vo.AgentContractVO getAgentContractVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentContractVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AgentContractVO) _agentContractVOList.elementAt(index);
    } //-- edit.common.vo.AgentContractVO getAgentContractVO(int) 

    /**
     * Method getAgentContractVO
     */
    public edit.common.vo.AgentContractVO[] getAgentContractVO()
    {
        int size = _agentContractVOList.size();
        edit.common.vo.AgentContractVO[] mArray = new edit.common.vo.AgentContractVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AgentContractVO) _agentContractVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AgentContractVO[] getAgentContractVO() 

    /**
     * Method getAgentContractVOCount
     */
    public int getAgentContractVOCount()
    {
        return _agentContractVOList.size();
    } //-- int getAgentContractVOCount() 

    /**
     * Method getCommissionContractPKReturns the value of field
     * 'commissionContractPK'.
     * 
     * @return the value of field 'commissionContractPK'.
     */
    public long getCommissionContractPK()
    {
        return this._commissionContractPK;
    } //-- long getCommissionContractPK() 

    /**
     * Method getCommissionLevelVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionLevelVO getCommissionLevelVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionLevelVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.CommissionLevelVO) _commissionLevelVOList.elementAt(index);
    } //-- edit.common.vo.CommissionLevelVO getCommissionLevelVO(int) 

    /**
     * Method getCommissionLevelVO
     */
    public edit.common.vo.CommissionLevelVO[] getCommissionLevelVO()
    {
        int size = _commissionLevelVOList.size();
        edit.common.vo.CommissionLevelVO[] mArray = new edit.common.vo.CommissionLevelVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.CommissionLevelVO) _commissionLevelVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.CommissionLevelVO[] getCommissionLevelVO() 

    /**
     * Method getCommissionLevelVOCount
     */
    public int getCommissionLevelVOCount()
    {
        return _commissionLevelVOList.size();
    } //-- int getCommissionLevelVOCount() 

    /**
     * Method getContractCodeReturns the value of field
     * 'contractCode'.
     * 
     * @return the value of field 'contractCode'.
     */
    public java.lang.String getContractCode()
    {
        return this._contractCode;
    } //-- java.lang.String getContractCode() 

    /**
     * Method hasCommissionContractPK
     */
    public boolean hasCommissionContractPK()
    {
        return this._has_commissionContractPK;
    } //-- boolean hasCommissionContractPK() 

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
     * Method removeAgentContractVO
     * 
     * @param index
     */
    public edit.common.vo.AgentContractVO removeAgentContractVO(int index)
    {
        java.lang.Object obj = _agentContractVOList.elementAt(index);
        _agentContractVOList.removeElementAt(index);
        return (edit.common.vo.AgentContractVO) obj;
    } //-- edit.common.vo.AgentContractVO removeAgentContractVO(int) 

    /**
     * Method removeAllAgentContractVO
     */
    public void removeAllAgentContractVO()
    {
        _agentContractVOList.removeAllElements();
    } //-- void removeAllAgentContractVO() 

    /**
     * Method removeAllCommissionLevelVO
     */
    public void removeAllCommissionLevelVO()
    {
        _commissionLevelVOList.removeAllElements();
    } //-- void removeAllCommissionLevelVO() 

    /**
     * Method removeCommissionLevelVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionLevelVO removeCommissionLevelVO(int index)
    {
        java.lang.Object obj = _commissionLevelVOList.elementAt(index);
        _commissionLevelVOList.removeElementAt(index);
        return (edit.common.vo.CommissionLevelVO) obj;
    } //-- edit.common.vo.CommissionLevelVO removeCommissionLevelVO(int) 

    /**
     * Method setAgentContractVO
     * 
     * @param index
     * @param vAgentContractVO
     */
    public void setAgentContractVO(int index, edit.common.vo.AgentContractVO vAgentContractVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentContractVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAgentContractVO.setParentVO(this.getClass(), this);
        _agentContractVOList.setElementAt(vAgentContractVO, index);
    } //-- void setAgentContractVO(int, edit.common.vo.AgentContractVO) 

    /**
     * Method setAgentContractVO
     * 
     * @param agentContractVOArray
     */
    public void setAgentContractVO(edit.common.vo.AgentContractVO[] agentContractVOArray)
    {
        //-- copy array
        _agentContractVOList.removeAllElements();
        for (int i = 0; i < agentContractVOArray.length; i++) {
            agentContractVOArray[i].setParentVO(this.getClass(), this);
            _agentContractVOList.addElement(agentContractVOArray[i]);
        }
    } //-- void setAgentContractVO(edit.common.vo.AgentContractVO) 

    /**
     * Method setCommissionContractPKSets the value of field
     * 'commissionContractPK'.
     * 
     * @param commissionContractPK the value of field
     * 'commissionContractPK'.
     */
    public void setCommissionContractPK(long commissionContractPK)
    {
        this._commissionContractPK = commissionContractPK;
        
        super.setVoChanged(true);
        this._has_commissionContractPK = true;
    } //-- void setCommissionContractPK(long) 

    /**
     * Method setCommissionLevelVO
     * 
     * @param index
     * @param vCommissionLevelVO
     */
    public void setCommissionLevelVO(int index, edit.common.vo.CommissionLevelVO vCommissionLevelVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionLevelVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vCommissionLevelVO.setParentVO(this.getClass(), this);
        _commissionLevelVOList.setElementAt(vCommissionLevelVO, index);
    } //-- void setCommissionLevelVO(int, edit.common.vo.CommissionLevelVO) 

    /**
     * Method setCommissionLevelVO
     * 
     * @param commissionLevelVOArray
     */
    public void setCommissionLevelVO(edit.common.vo.CommissionLevelVO[] commissionLevelVOArray)
    {
        //-- copy array
        _commissionLevelVOList.removeAllElements();
        for (int i = 0; i < commissionLevelVOArray.length; i++) {
            commissionLevelVOArray[i].setParentVO(this.getClass(), this);
            _commissionLevelVOList.addElement(commissionLevelVOArray[i]);
        }
    } //-- void setCommissionLevelVO(edit.common.vo.CommissionLevelVO) 

    /**
     * Method setContractCodeSets the value of field
     * 'contractCode'.
     * 
     * @param contractCode the value of field 'contractCode'.
     */
    public void setContractCode(java.lang.String contractCode)
    {
        this._contractCode = contractCode;
        
        super.setVoChanged(true);
    } //-- void setContractCode(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CommissionContractVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CommissionContractVO) Unmarshaller.unmarshal(edit.common.vo.CommissionContractVO.class, reader);
    } //-- edit.common.vo.CommissionContractVO unmarshal(java.io.Reader) 

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
