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
public class AgentSearchResponseVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Comment describing your root element
     */
    private java.util.Vector _clientAgentVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public AgentSearchResponseVO() {
        super();
        _clientAgentVOList = new Vector();
    } //-- edit.common.vo.AgentSearchResponseVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addClientAgentVO
     * 
     * @param vClientAgentVO
     */
    public void addClientAgentVO(edit.common.vo.ClientAgentVO vClientAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientAgentVO.setParentVO(this.getClass(), this);
        _clientAgentVOList.addElement(vClientAgentVO);
    } //-- void addClientAgentVO(edit.common.vo.ClientAgentVO) 

    /**
     * Method addClientAgentVO
     * 
     * @param index
     * @param vClientAgentVO
     */
    public void addClientAgentVO(int index, edit.common.vo.ClientAgentVO vClientAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientAgentVO.setParentVO(this.getClass(), this);
        _clientAgentVOList.insertElementAt(vClientAgentVO, index);
    } //-- void addClientAgentVO(int, edit.common.vo.ClientAgentVO) 

    /**
     * Method enumerateClientAgentVO
     */
    public java.util.Enumeration enumerateClientAgentVO()
    {
        return _clientAgentVOList.elements();
    } //-- java.util.Enumeration enumerateClientAgentVO() 

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
        
        if (obj instanceof AgentSearchResponseVO) {
        
            AgentSearchResponseVO temp = (AgentSearchResponseVO)obj;
            if (this._clientAgentVOList != null) {
                if (temp._clientAgentVOList == null) return false;
                else if (!(this._clientAgentVOList.equals(temp._clientAgentVOList))) 
                    return false;
            }
            else if (temp._clientAgentVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getClientAgentVO
     * 
     * @param index
     */
    public edit.common.vo.ClientAgentVO getClientAgentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientAgentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ClientAgentVO) _clientAgentVOList.elementAt(index);
    } //-- edit.common.vo.ClientAgentVO getClientAgentVO(int) 

    /**
     * Method getClientAgentVO
     */
    public edit.common.vo.ClientAgentVO[] getClientAgentVO()
    {
        int size = _clientAgentVOList.size();
        edit.common.vo.ClientAgentVO[] mArray = new edit.common.vo.ClientAgentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ClientAgentVO) _clientAgentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ClientAgentVO[] getClientAgentVO() 

    /**
     * Method getClientAgentVOCount
     */
    public int getClientAgentVOCount()
    {
        return _clientAgentVOList.size();
    } //-- int getClientAgentVOCount() 

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
     * Method removeAllClientAgentVO
     */
    public void removeAllClientAgentVO()
    {
        _clientAgentVOList.removeAllElements();
    } //-- void removeAllClientAgentVO() 

    /**
     * Method removeClientAgentVO
     * 
     * @param index
     */
    public edit.common.vo.ClientAgentVO removeClientAgentVO(int index)
    {
        java.lang.Object obj = _clientAgentVOList.elementAt(index);
        _clientAgentVOList.removeElementAt(index);
        return (edit.common.vo.ClientAgentVO) obj;
    } //-- edit.common.vo.ClientAgentVO removeClientAgentVO(int) 

    /**
     * Method setClientAgentVO
     * 
     * @param index
     * @param vClientAgentVO
     */
    public void setClientAgentVO(int index, edit.common.vo.ClientAgentVO vClientAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientAgentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vClientAgentVO.setParentVO(this.getClass(), this);
        _clientAgentVOList.setElementAt(vClientAgentVO, index);
    } //-- void setClientAgentVO(int, edit.common.vo.ClientAgentVO) 

    /**
     * Method setClientAgentVO
     * 
     * @param clientAgentVOArray
     */
    public void setClientAgentVO(edit.common.vo.ClientAgentVO[] clientAgentVOArray)
    {
        //-- copy array
        _clientAgentVOList.removeAllElements();
        for (int i = 0; i < clientAgentVOArray.length; i++) {
            clientAgentVOArray[i].setParentVO(this.getClass(), this);
            _clientAgentVOList.addElement(clientAgentVOArray[i]);
        }
    } //-- void setClientAgentVO(edit.common.vo.ClientAgentVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AgentSearchResponseVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AgentSearchResponseVO) Unmarshaller.unmarshal(edit.common.vo.AgentSearchResponseVO.class, reader);
    } //-- edit.common.vo.AgentSearchResponseVO unmarshal(java.io.Reader) 

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
