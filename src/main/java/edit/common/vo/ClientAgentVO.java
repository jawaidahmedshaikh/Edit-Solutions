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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class ClientAgentVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _clientDetailVO
     */
    private edit.common.vo.ClientDetailVO _clientDetailVO;

    /**
     * Field _agentVOList
     */
    private java.util.Vector _agentVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ClientAgentVO() {
        super();
        _agentVOList = new Vector();
    } //-- edit.common.vo.ClientAgentVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAgentVO
     * 
     * @param vAgentVO
     */
    public void addAgentVO(edit.common.vo.AgentVO vAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentVO.setParentVO(this.getClass(), this);
        _agentVOList.addElement(vAgentVO);
    } //-- void addAgentVO(edit.common.vo.AgentVO) 

    /**
     * Method addAgentVO
     * 
     * @param index
     * @param vAgentVO
     */
    public void addAgentVO(int index, edit.common.vo.AgentVO vAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentVO.setParentVO(this.getClass(), this);
        _agentVOList.insertElementAt(vAgentVO, index);
    } //-- void addAgentVO(int, edit.common.vo.AgentVO) 

    /**
     * Method enumerateAgentVO
     */
    public java.util.Enumeration enumerateAgentVO()
    {
        return _agentVOList.elements();
    } //-- java.util.Enumeration enumerateAgentVO() 

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
        
        if (obj instanceof ClientAgentVO) {
        
            ClientAgentVO temp = (ClientAgentVO)obj;
            if (this._clientDetailVO != null) {
                if (temp._clientDetailVO == null) return false;
                else if (!(this._clientDetailVO.equals(temp._clientDetailVO))) 
                    return false;
            }
            else if (temp._clientDetailVO != null)
                return false;
            if (this._agentVOList != null) {
                if (temp._agentVOList == null) return false;
                else if (!(this._agentVOList.equals(temp._agentVOList))) 
                    return false;
            }
            else if (temp._agentVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentVO
     * 
     * @param index
     */
    public edit.common.vo.AgentVO getAgentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AgentVO) _agentVOList.elementAt(index);
    } //-- edit.common.vo.AgentVO getAgentVO(int) 

    /**
     * Method getAgentVO
     */
    public edit.common.vo.AgentVO[] getAgentVO()
    {
        int size = _agentVOList.size();
        edit.common.vo.AgentVO[] mArray = new edit.common.vo.AgentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AgentVO) _agentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AgentVO[] getAgentVO() 

    /**
     * Method getAgentVOCount
     */
    public int getAgentVOCount()
    {
        return _agentVOList.size();
    } //-- int getAgentVOCount() 

    /**
     * Method getClientDetailVOReturns the value of field
     * 'clientDetailVO'.
     * 
     * @return the value of field 'clientDetailVO'.
     */
    public edit.common.vo.ClientDetailVO getClientDetailVO()
    {
        return this._clientDetailVO;
    } //-- edit.common.vo.ClientDetailVO getClientDetailVO() 

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
     * Method removeAgentVO
     * 
     * @param index
     */
    public edit.common.vo.AgentVO removeAgentVO(int index)
    {
        java.lang.Object obj = _agentVOList.elementAt(index);
        _agentVOList.removeElementAt(index);
        return (edit.common.vo.AgentVO) obj;
    } //-- edit.common.vo.AgentVO removeAgentVO(int) 

    /**
     * Method removeAllAgentVO
     */
    public void removeAllAgentVO()
    {
        _agentVOList.removeAllElements();
    } //-- void removeAllAgentVO() 

    /**
     * Method setAgentVO
     * 
     * @param index
     * @param vAgentVO
     */
    public void setAgentVO(int index, edit.common.vo.AgentVO vAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAgentVO.setParentVO(this.getClass(), this);
        _agentVOList.setElementAt(vAgentVO, index);
    } //-- void setAgentVO(int, edit.common.vo.AgentVO) 

    /**
     * Method setAgentVO
     * 
     * @param agentVOArray
     */
    public void setAgentVO(edit.common.vo.AgentVO[] agentVOArray)
    {
        //-- copy array
        _agentVOList.removeAllElements();
        for (int i = 0; i < agentVOArray.length; i++) {
            agentVOArray[i].setParentVO(this.getClass(), this);
            _agentVOList.addElement(agentVOArray[i]);
        }
    } //-- void setAgentVO(edit.common.vo.AgentVO) 

    /**
     * Method setClientDetailVOSets the value of field
     * 'clientDetailVO'.
     * 
     * @param clientDetailVO the value of field 'clientDetailVO'.
     */
    public void setClientDetailVO(edit.common.vo.ClientDetailVO clientDetailVO)
    {
        this._clientDetailVO = clientDetailVO;
    } //-- void setClientDetailVO(edit.common.vo.ClientDetailVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ClientAgentVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ClientAgentVO) Unmarshaller.unmarshal(edit.common.vo.ClientAgentVO.class, reader);
    } //-- edit.common.vo.ClientAgentVO unmarshal(java.io.Reader) 

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
