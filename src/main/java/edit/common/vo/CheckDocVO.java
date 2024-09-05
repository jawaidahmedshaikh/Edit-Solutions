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
public class CheckDocVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _checkDocPK
     */
    private long _checkDocPK;

    /**
     * keeps track of state for field: _checkDocPK
     */
    private boolean _has_checkDocPK;

    /**
     * Field _placedAgentFK
     */
    private long _placedAgentFK;

    /**
     * keeps track of state for field: _placedAgentFK
     */
    private boolean _has_placedAgentFK;

    /**
     * Field _EDITTrxVOList
     */
    private java.util.Vector _EDITTrxVOList;

    /**
     * Field _clientDetailVOList
     */
    private java.util.Vector _clientDetailVOList;

    /**
     * Field _clientRoleVOList
     */
    private java.util.Vector _clientRoleVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public CheckDocVO() {
        super();
        _EDITTrxVOList = new Vector();
        _clientDetailVOList = new Vector();
        _clientRoleVOList = new Vector();
    } //-- edit.common.vo.CheckDocVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addClientDetailVO
     * 
     * @param vClientDetailVO
     */
    public void addClientDetailVO(edit.common.vo.ClientDetailVO vClientDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientDetailVO.setParentVO(this.getClass(), this);
        _clientDetailVOList.addElement(vClientDetailVO);
    } //-- void addClientDetailVO(edit.common.vo.ClientDetailVO) 

    /**
     * Method addClientDetailVO
     * 
     * @param index
     * @param vClientDetailVO
     */
    public void addClientDetailVO(int index, edit.common.vo.ClientDetailVO vClientDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientDetailVO.setParentVO(this.getClass(), this);
        _clientDetailVOList.insertElementAt(vClientDetailVO, index);
    } //-- void addClientDetailVO(int, edit.common.vo.ClientDetailVO) 

    /**
     * Method addClientRoleVO
     * 
     * @param vClientRoleVO
     */
    public void addClientRoleVO(edit.common.vo.ClientRoleVO vClientRoleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientRoleVO.setParentVO(this.getClass(), this);
        _clientRoleVOList.addElement(vClientRoleVO);
    } //-- void addClientRoleVO(edit.common.vo.ClientRoleVO) 

    /**
     * Method addClientRoleVO
     * 
     * @param index
     * @param vClientRoleVO
     */
    public void addClientRoleVO(int index, edit.common.vo.ClientRoleVO vClientRoleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientRoleVO.setParentVO(this.getClass(), this);
        _clientRoleVOList.insertElementAt(vClientRoleVO, index);
    } //-- void addClientRoleVO(int, edit.common.vo.ClientRoleVO) 

    /**
     * Method addEDITTrxVO
     * 
     * @param vEDITTrxVO
     */
    public void addEDITTrxVO(edit.common.vo.EDITTrxVO vEDITTrxVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEDITTrxVO.setParentVO(this.getClass(), this);
        _EDITTrxVOList.addElement(vEDITTrxVO);
    } //-- void addEDITTrxVO(edit.common.vo.EDITTrxVO) 

    /**
     * Method addEDITTrxVO
     * 
     * @param index
     * @param vEDITTrxVO
     */
    public void addEDITTrxVO(int index, edit.common.vo.EDITTrxVO vEDITTrxVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEDITTrxVO.setParentVO(this.getClass(), this);
        _EDITTrxVOList.insertElementAt(vEDITTrxVO, index);
    } //-- void addEDITTrxVO(int, edit.common.vo.EDITTrxVO) 

    /**
     * Method enumerateClientDetailVO
     */
    public java.util.Enumeration enumerateClientDetailVO()
    {
        return _clientDetailVOList.elements();
    } //-- java.util.Enumeration enumerateClientDetailVO() 

    /**
     * Method enumerateClientRoleVO
     */
    public java.util.Enumeration enumerateClientRoleVO()
    {
        return _clientRoleVOList.elements();
    } //-- java.util.Enumeration enumerateClientRoleVO() 

    /**
     * Method enumerateEDITTrxVO
     */
    public java.util.Enumeration enumerateEDITTrxVO()
    {
        return _EDITTrxVOList.elements();
    } //-- java.util.Enumeration enumerateEDITTrxVO() 

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
        
        if (obj instanceof CheckDocVO) {
        
            CheckDocVO temp = (CheckDocVO)obj;
            if (this._checkDocPK != temp._checkDocPK)
                return false;
            if (this._has_checkDocPK != temp._has_checkDocPK)
                return false;
            if (this._placedAgentFK != temp._placedAgentFK)
                return false;
            if (this._has_placedAgentFK != temp._has_placedAgentFK)
                return false;
            if (this._EDITTrxVOList != null) {
                if (temp._EDITTrxVOList == null) return false;
                else if (!(this._EDITTrxVOList.equals(temp._EDITTrxVOList))) 
                    return false;
            }
            else if (temp._EDITTrxVOList != null)
                return false;
            if (this._clientDetailVOList != null) {
                if (temp._clientDetailVOList == null) return false;
                else if (!(this._clientDetailVOList.equals(temp._clientDetailVOList))) 
                    return false;
            }
            else if (temp._clientDetailVOList != null)
                return false;
            if (this._clientRoleVOList != null) {
                if (temp._clientRoleVOList == null) return false;
                else if (!(this._clientRoleVOList.equals(temp._clientRoleVOList))) 
                    return false;
            }
            else if (temp._clientRoleVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCheckDocPKReturns the value of field 'checkDocPK'.
     * 
     * @return the value of field 'checkDocPK'.
     */
    public long getCheckDocPK()
    {
        return this._checkDocPK;
    } //-- long getCheckDocPK() 

    /**
     * Method getClientDetailVO
     * 
     * @param index
     */
    public edit.common.vo.ClientDetailVO getClientDetailVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ClientDetailVO) _clientDetailVOList.elementAt(index);
    } //-- edit.common.vo.ClientDetailVO getClientDetailVO(int) 

    /**
     * Method getClientDetailVO
     */
    public edit.common.vo.ClientDetailVO[] getClientDetailVO()
    {
        int size = _clientDetailVOList.size();
        edit.common.vo.ClientDetailVO[] mArray = new edit.common.vo.ClientDetailVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ClientDetailVO) _clientDetailVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ClientDetailVO[] getClientDetailVO() 

    /**
     * Method getClientDetailVOCount
     */
    public int getClientDetailVOCount()
    {
        return _clientDetailVOList.size();
    } //-- int getClientDetailVOCount() 

    /**
     * Method getClientRoleVO
     * 
     * @param index
     */
    public edit.common.vo.ClientRoleVO getClientRoleVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientRoleVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ClientRoleVO) _clientRoleVOList.elementAt(index);
    } //-- edit.common.vo.ClientRoleVO getClientRoleVO(int) 

    /**
     * Method getClientRoleVO
     */
    public edit.common.vo.ClientRoleVO[] getClientRoleVO()
    {
        int size = _clientRoleVOList.size();
        edit.common.vo.ClientRoleVO[] mArray = new edit.common.vo.ClientRoleVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ClientRoleVO) _clientRoleVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ClientRoleVO[] getClientRoleVO() 

    /**
     * Method getClientRoleVOCount
     */
    public int getClientRoleVOCount()
    {
        return _clientRoleVOList.size();
    } //-- int getClientRoleVOCount() 

    /**
     * Method getEDITTrxVO
     * 
     * @param index
     */
    public edit.common.vo.EDITTrxVO getEDITTrxVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITTrxVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.EDITTrxVO) _EDITTrxVOList.elementAt(index);
    } //-- edit.common.vo.EDITTrxVO getEDITTrxVO(int) 

    /**
     * Method getEDITTrxVO
     */
    public edit.common.vo.EDITTrxVO[] getEDITTrxVO()
    {
        int size = _EDITTrxVOList.size();
        edit.common.vo.EDITTrxVO[] mArray = new edit.common.vo.EDITTrxVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.EDITTrxVO) _EDITTrxVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.EDITTrxVO[] getEDITTrxVO() 

    /**
     * Method getEDITTrxVOCount
     */
    public int getEDITTrxVOCount()
    {
        return _EDITTrxVOList.size();
    } //-- int getEDITTrxVOCount() 

    /**
     * Method getPlacedAgentFKReturns the value of field
     * 'placedAgentFK'.
     * 
     * @return the value of field 'placedAgentFK'.
     */
    public long getPlacedAgentFK()
    {
        return this._placedAgentFK;
    } //-- long getPlacedAgentFK() 

    /**
     * Method hasCheckDocPK
     */
    public boolean hasCheckDocPK()
    {
        return this._has_checkDocPK;
    } //-- boolean hasCheckDocPK() 

    /**
     * Method hasPlacedAgentFK
     */
    public boolean hasPlacedAgentFK()
    {
        return this._has_placedAgentFK;
    } //-- boolean hasPlacedAgentFK() 

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
     * Method removeAllClientDetailVO
     */
    public void removeAllClientDetailVO()
    {
        _clientDetailVOList.removeAllElements();
    } //-- void removeAllClientDetailVO() 

    /**
     * Method removeAllClientRoleVO
     */
    public void removeAllClientRoleVO()
    {
        _clientRoleVOList.removeAllElements();
    } //-- void removeAllClientRoleVO() 

    /**
     * Method removeAllEDITTrxVO
     */
    public void removeAllEDITTrxVO()
    {
        _EDITTrxVOList.removeAllElements();
    } //-- void removeAllEDITTrxVO() 

    /**
     * Method removeClientDetailVO
     * 
     * @param index
     */
    public edit.common.vo.ClientDetailVO removeClientDetailVO(int index)
    {
        java.lang.Object obj = _clientDetailVOList.elementAt(index);
        _clientDetailVOList.removeElementAt(index);
        return (edit.common.vo.ClientDetailVO) obj;
    } //-- edit.common.vo.ClientDetailVO removeClientDetailVO(int) 

    /**
     * Method removeClientRoleVO
     * 
     * @param index
     */
    public edit.common.vo.ClientRoleVO removeClientRoleVO(int index)
    {
        java.lang.Object obj = _clientRoleVOList.elementAt(index);
        _clientRoleVOList.removeElementAt(index);
        return (edit.common.vo.ClientRoleVO) obj;
    } //-- edit.common.vo.ClientRoleVO removeClientRoleVO(int) 

    /**
     * Method removeEDITTrxVO
     * 
     * @param index
     */
    public edit.common.vo.EDITTrxVO removeEDITTrxVO(int index)
    {
        java.lang.Object obj = _EDITTrxVOList.elementAt(index);
        _EDITTrxVOList.removeElementAt(index);
        return (edit.common.vo.EDITTrxVO) obj;
    } //-- edit.common.vo.EDITTrxVO removeEDITTrxVO(int) 

    /**
     * Method setCheckDocPKSets the value of field 'checkDocPK'.
     * 
     * @param checkDocPK the value of field 'checkDocPK'.
     */
    public void setCheckDocPK(long checkDocPK)
    {
        this._checkDocPK = checkDocPK;
        
        super.setVoChanged(true);
        this._has_checkDocPK = true;
    } //-- void setCheckDocPK(long) 

    /**
     * Method setClientDetailVO
     * 
     * @param index
     * @param vClientDetailVO
     */
    public void setClientDetailVO(int index, edit.common.vo.ClientDetailVO vClientDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vClientDetailVO.setParentVO(this.getClass(), this);
        _clientDetailVOList.setElementAt(vClientDetailVO, index);
    } //-- void setClientDetailVO(int, edit.common.vo.ClientDetailVO) 

    /**
     * Method setClientDetailVO
     * 
     * @param clientDetailVOArray
     */
    public void setClientDetailVO(edit.common.vo.ClientDetailVO[] clientDetailVOArray)
    {
        //-- copy array
        _clientDetailVOList.removeAllElements();
        for (int i = 0; i < clientDetailVOArray.length; i++) {
            clientDetailVOArray[i].setParentVO(this.getClass(), this);
            _clientDetailVOList.addElement(clientDetailVOArray[i]);
        }
    } //-- void setClientDetailVO(edit.common.vo.ClientDetailVO) 

    /**
     * Method setClientRoleVO
     * 
     * @param index
     * @param vClientRoleVO
     */
    public void setClientRoleVO(int index, edit.common.vo.ClientRoleVO vClientRoleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientRoleVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vClientRoleVO.setParentVO(this.getClass(), this);
        _clientRoleVOList.setElementAt(vClientRoleVO, index);
    } //-- void setClientRoleVO(int, edit.common.vo.ClientRoleVO) 

    /**
     * Method setClientRoleVO
     * 
     * @param clientRoleVOArray
     */
    public void setClientRoleVO(edit.common.vo.ClientRoleVO[] clientRoleVOArray)
    {
        //-- copy array
        _clientRoleVOList.removeAllElements();
        for (int i = 0; i < clientRoleVOArray.length; i++) {
            clientRoleVOArray[i].setParentVO(this.getClass(), this);
            _clientRoleVOList.addElement(clientRoleVOArray[i]);
        }
    } //-- void setClientRoleVO(edit.common.vo.ClientRoleVO) 

    /**
     * Method setEDITTrxVO
     * 
     * @param index
     * @param vEDITTrxVO
     */
    public void setEDITTrxVO(int index, edit.common.vo.EDITTrxVO vEDITTrxVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITTrxVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vEDITTrxVO.setParentVO(this.getClass(), this);
        _EDITTrxVOList.setElementAt(vEDITTrxVO, index);
    } //-- void setEDITTrxVO(int, edit.common.vo.EDITTrxVO) 

    /**
     * Method setEDITTrxVO
     * 
     * @param EDITTrxVOArray
     */
    public void setEDITTrxVO(edit.common.vo.EDITTrxVO[] EDITTrxVOArray)
    {
        //-- copy array
        _EDITTrxVOList.removeAllElements();
        for (int i = 0; i < EDITTrxVOArray.length; i++) {
            EDITTrxVOArray[i].setParentVO(this.getClass(), this);
            _EDITTrxVOList.addElement(EDITTrxVOArray[i]);
        }
    } //-- void setEDITTrxVO(edit.common.vo.EDITTrxVO) 

    /**
     * Method setPlacedAgentFKSets the value of field
     * 'placedAgentFK'.
     * 
     * @param placedAgentFK the value of field 'placedAgentFK'.
     */
    public void setPlacedAgentFK(long placedAgentFK)
    {
        this._placedAgentFK = placedAgentFK;
        
        super.setVoChanged(true);
        this._has_placedAgentFK = true;
    } //-- void setPlacedAgentFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CheckDocVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CheckDocVO) Unmarshaller.unmarshal(edit.common.vo.CheckDocVO.class, reader);
    } //-- edit.common.vo.CheckDocVO unmarshal(java.io.Reader) 

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
