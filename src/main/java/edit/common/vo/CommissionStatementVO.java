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
 * Class CommissionStatementVO.
 * 
 * @version $Revision$ $Date$
 */
public class CommissionStatementVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _agentStatementVOList
     */
    private java.util.Vector _agentStatementVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public CommissionStatementVO() {
        super();
        _agentStatementVOList = new Vector();
    } //-- edit.common.vo.CommissionStatementVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAgentStatementVO
     * 
     * @param vAgentStatementVO
     */
    public void addAgentStatementVO(edit.common.vo.AgentStatementVO vAgentStatementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentStatementVO.setParentVO(this.getClass(), this);
        _agentStatementVOList.addElement(vAgentStatementVO);
    } //-- void addAgentStatementVO(edit.common.vo.AgentStatementVO) 

    /**
     * Method addAgentStatementVO
     * 
     * @param index
     * @param vAgentStatementVO
     */
    public void addAgentStatementVO(int index, edit.common.vo.AgentStatementVO vAgentStatementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentStatementVO.setParentVO(this.getClass(), this);
        _agentStatementVOList.insertElementAt(vAgentStatementVO, index);
    } //-- void addAgentStatementVO(int, edit.common.vo.AgentStatementVO) 

    /**
     * Method enumerateAgentStatementVO
     */
    public java.util.Enumeration enumerateAgentStatementVO()
    {
        return _agentStatementVOList.elements();
    } //-- java.util.Enumeration enumerateAgentStatementVO() 

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
        
        if (obj instanceof CommissionStatementVO) {
        
            CommissionStatementVO temp = (CommissionStatementVO)obj;
            if (this._agentStatementVOList != null) {
                if (temp._agentStatementVOList == null) return false;
                else if (!(this._agentStatementVOList.equals(temp._agentStatementVOList))) 
                    return false;
            }
            else if (temp._agentStatementVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentStatementVO
     * 
     * @param index
     */
    public edit.common.vo.AgentStatementVO getAgentStatementVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentStatementVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AgentStatementVO) _agentStatementVOList.elementAt(index);
    } //-- edit.common.vo.AgentStatementVO getAgentStatementVO(int) 

    /**
     * Method getAgentStatementVO
     */
    public edit.common.vo.AgentStatementVO[] getAgentStatementVO()
    {
        int size = _agentStatementVOList.size();
        edit.common.vo.AgentStatementVO[] mArray = new edit.common.vo.AgentStatementVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AgentStatementVO) _agentStatementVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AgentStatementVO[] getAgentStatementVO() 

    /**
     * Method getAgentStatementVOCount
     */
    public int getAgentStatementVOCount()
    {
        return _agentStatementVOList.size();
    } //-- int getAgentStatementVOCount() 

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
     * Method removeAgentStatementVO
     * 
     * @param index
     */
    public edit.common.vo.AgentStatementVO removeAgentStatementVO(int index)
    {
        java.lang.Object obj = _agentStatementVOList.elementAt(index);
        _agentStatementVOList.removeElementAt(index);
        return (edit.common.vo.AgentStatementVO) obj;
    } //-- edit.common.vo.AgentStatementVO removeAgentStatementVO(int) 

    /**
     * Method removeAllAgentStatementVO
     */
    public void removeAllAgentStatementVO()
    {
        _agentStatementVOList.removeAllElements();
    } //-- void removeAllAgentStatementVO() 

    /**
     * Method setAgentStatementVO
     * 
     * @param index
     * @param vAgentStatementVO
     */
    public void setAgentStatementVO(int index, edit.common.vo.AgentStatementVO vAgentStatementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentStatementVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAgentStatementVO.setParentVO(this.getClass(), this);
        _agentStatementVOList.setElementAt(vAgentStatementVO, index);
    } //-- void setAgentStatementVO(int, edit.common.vo.AgentStatementVO) 

    /**
     * Method setAgentStatementVO
     * 
     * @param agentStatementVOArray
     */
    public void setAgentStatementVO(edit.common.vo.AgentStatementVO[] agentStatementVOArray)
    {
        //-- copy array
        _agentStatementVOList.removeAllElements();
        for (int i = 0; i < agentStatementVOArray.length; i++) {
            agentStatementVOArray[i].setParentVO(this.getClass(), this);
            _agentStatementVOList.addElement(agentStatementVOArray[i]);
        }
    } //-- void setAgentStatementVO(edit.common.vo.AgentStatementVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CommissionStatementVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CommissionStatementVO) Unmarshaller.unmarshal(edit.common.vo.CommissionStatementVO.class, reader);
    } //-- edit.common.vo.CommissionStatementVO unmarshal(java.io.Reader) 

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
