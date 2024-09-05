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
public class ClientVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _roleTypeCT
     */
    private java.lang.String _roleTypeCT;

    /**
     * Field _newIssuesEligibilityStatusCT
     */
    private java.lang.String _newIssuesEligibilityStatusCT;

    /**
     * Field _clientDetailVOList
     */
    private java.util.Vector _clientDetailVOList;

    /**
     * Field _contractClientVOList
     */
    private java.util.Vector _contractClientVOList;

    /**
     * Field _clientPK
     */
    private long _clientPK;

    /**
     * keeps track of state for field: _clientPK
     */
    private boolean _has_clientPK;


      //----------------/
     //- Constructors -/
    //----------------/

    public ClientVO() {
        super();
        _clientDetailVOList = new Vector();
        _contractClientVOList = new Vector();
    } //-- edit.common.vo.ClientVO()


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
     * Method addContractClientVO
     * 
     * @param vContractClientVO
     */
    public void addContractClientVO(edit.common.vo.ContractClientVO vContractClientVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractClientVO.setParentVO(this.getClass(), this);
        _contractClientVOList.addElement(vContractClientVO);
    } //-- void addContractClientVO(edit.common.vo.ContractClientVO) 

    /**
     * Method addContractClientVO
     * 
     * @param index
     * @param vContractClientVO
     */
    public void addContractClientVO(int index, edit.common.vo.ContractClientVO vContractClientVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractClientVO.setParentVO(this.getClass(), this);
        _contractClientVOList.insertElementAt(vContractClientVO, index);
    } //-- void addContractClientVO(int, edit.common.vo.ContractClientVO) 

    /**
     * Method enumerateClientDetailVO
     */
    public java.util.Enumeration enumerateClientDetailVO()
    {
        return _clientDetailVOList.elements();
    } //-- java.util.Enumeration enumerateClientDetailVO() 

    /**
     * Method enumerateContractClientVO
     */
    public java.util.Enumeration enumerateContractClientVO()
    {
        return _contractClientVOList.elements();
    } //-- java.util.Enumeration enumerateContractClientVO() 

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
        
        if (obj instanceof ClientVO) {
        
            ClientVO temp = (ClientVO)obj;
            if (this._roleTypeCT != null) {
                if (temp._roleTypeCT == null) return false;
                else if (!(this._roleTypeCT.equals(temp._roleTypeCT))) 
                    return false;
            }
            else if (temp._roleTypeCT != null)
                return false;
            if (this._newIssuesEligibilityStatusCT != null) {
                if (temp._newIssuesEligibilityStatusCT == null) return false;
                else if (!(this._newIssuesEligibilityStatusCT.equals(temp._newIssuesEligibilityStatusCT))) 
                    return false;
            }
            else if (temp._newIssuesEligibilityStatusCT != null)
                return false;
            if (this._clientDetailVOList != null) {
                if (temp._clientDetailVOList == null) return false;
                else if (!(this._clientDetailVOList.equals(temp._clientDetailVOList))) 
                    return false;
            }
            else if (temp._clientDetailVOList != null)
                return false;
            if (this._contractClientVOList != null) {
                if (temp._contractClientVOList == null) return false;
                else if (!(this._contractClientVOList.equals(temp._contractClientVOList))) 
                    return false;
            }
            else if (temp._contractClientVOList != null)
                return false;
            if (this._clientPK != temp._clientPK)
                return false;
            if (this._has_clientPK != temp._has_clientPK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getClientPKReturns the value of field 'clientPK'.
     * 
     * @return the value of field 'clientPK'.
     */
    public long getClientPK()
    {
        return this._clientPK;
    } //-- long getClientPK() 

    /**
     * Method getContractClientVO
     * 
     * @param index
     */
    public edit.common.vo.ContractClientVO getContractClientVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractClientVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ContractClientVO) _contractClientVOList.elementAt(index);
    } //-- edit.common.vo.ContractClientVO getContractClientVO(int) 

    /**
     * Method getContractClientVO
     */
    public edit.common.vo.ContractClientVO[] getContractClientVO()
    {
        int size = _contractClientVOList.size();
        edit.common.vo.ContractClientVO[] mArray = new edit.common.vo.ContractClientVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ContractClientVO) _contractClientVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ContractClientVO[] getContractClientVO() 

    /**
     * Method getContractClientVOCount
     */
    public int getContractClientVOCount()
    {
        return _contractClientVOList.size();
    } //-- int getContractClientVOCount() 

    /**
     * Method getNewIssuesEligibilityStatusCTReturns the value of
     * field 'newIssuesEligibilityStatusCT'.
     * 
     * @return the value of field 'newIssuesEligibilityStatusCT'.
     */
    public java.lang.String getNewIssuesEligibilityStatusCT()
    {
        return this._newIssuesEligibilityStatusCT;
    } //-- java.lang.String getNewIssuesEligibilityStatusCT() 

    /**
     * Method getRoleTypeCTReturns the value of field 'roleTypeCT'.
     * 
     * @return the value of field 'roleTypeCT'.
     */
    public java.lang.String getRoleTypeCT()
    {
        return this._roleTypeCT;
    } //-- java.lang.String getRoleTypeCT() 

    /**
     * Method hasClientPK
     */
    public boolean hasClientPK()
    {
        return this._has_clientPK;
    } //-- boolean hasClientPK() 

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
     * Method removeAllContractClientVO
     */
    public void removeAllContractClientVO()
    {
        _contractClientVOList.removeAllElements();
    } //-- void removeAllContractClientVO() 

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
     * Method removeContractClientVO
     * 
     * @param index
     */
    public edit.common.vo.ContractClientVO removeContractClientVO(int index)
    {
        java.lang.Object obj = _contractClientVOList.elementAt(index);
        _contractClientVOList.removeElementAt(index);
        return (edit.common.vo.ContractClientVO) obj;
    } //-- edit.common.vo.ContractClientVO removeContractClientVO(int) 

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
     * Method setClientPKSets the value of field 'clientPK'.
     * 
     * @param clientPK the value of field 'clientPK'.
     */
    public void setClientPK(long clientPK)
    {
        this._clientPK = clientPK;
        
        super.setVoChanged(true);
        this._has_clientPK = true;
    } //-- void setClientPK(long) 

    /**
     * Method setContractClientVO
     * 
     * @param index
     * @param vContractClientVO
     */
    public void setContractClientVO(int index, edit.common.vo.ContractClientVO vContractClientVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractClientVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vContractClientVO.setParentVO(this.getClass(), this);
        _contractClientVOList.setElementAt(vContractClientVO, index);
    } //-- void setContractClientVO(int, edit.common.vo.ContractClientVO) 

    /**
     * Method setContractClientVO
     * 
     * @param contractClientVOArray
     */
    public void setContractClientVO(edit.common.vo.ContractClientVO[] contractClientVOArray)
    {
        //-- copy array
        _contractClientVOList.removeAllElements();
        for (int i = 0; i < contractClientVOArray.length; i++) {
            contractClientVOArray[i].setParentVO(this.getClass(), this);
            _contractClientVOList.addElement(contractClientVOArray[i]);
        }
    } //-- void setContractClientVO(edit.common.vo.ContractClientVO) 

    /**
     * Method setNewIssuesEligibilityStatusCTSets the value of
     * field 'newIssuesEligibilityStatusCT'.
     * 
     * @param newIssuesEligibilityStatusCT the value of field
     * 'newIssuesEligibilityStatusCT'.
     */
    public void setNewIssuesEligibilityStatusCT(java.lang.String newIssuesEligibilityStatusCT)
    {
        this._newIssuesEligibilityStatusCT = newIssuesEligibilityStatusCT;
        
        super.setVoChanged(true);
    } //-- void setNewIssuesEligibilityStatusCT(java.lang.String) 

    /**
     * Method setRoleTypeCTSets the value of field 'roleTypeCT'.
     * 
     * @param roleTypeCT the value of field 'roleTypeCT'.
     */
    public void setRoleTypeCT(java.lang.String roleTypeCT)
    {
        this._roleTypeCT = roleTypeCT;
        
        super.setVoChanged(true);
    } //-- void setRoleTypeCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ClientVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ClientVO) Unmarshaller.unmarshal(edit.common.vo.ClientVO.class, reader);
    } //-- edit.common.vo.ClientVO unmarshal(java.io.Reader) 

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
