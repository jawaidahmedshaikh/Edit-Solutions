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
 * Class ClientSetupVO.
 * 
 * @version $Revision$ $Date$
 */
public class ClientSetupVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _clientSetupPK
     */
    private long _clientSetupPK;

    /**
     * keeps track of state for field: _clientSetupPK
     */
    private boolean _has_clientSetupPK;

    /**
     * Field _contractSetupFK
     */
    private long _contractSetupFK;

    /**
     * keeps track of state for field: _contractSetupFK
     */
    private boolean _has_contractSetupFK;

    /**
     * Field _contractClientFK
     */
    private long _contractClientFK;

    /**
     * keeps track of state for field: _contractClientFK
     */
    private boolean _has_contractClientFK;

    /**
     * Field _clientRoleFK
     */
    private long _clientRoleFK;

    /**
     * keeps track of state for field: _clientRoleFK
     */
    private boolean _has_clientRoleFK;

    /**
     * Field _treatyFK
     */
    private long _treatyFK;

    /**
     * keeps track of state for field: _treatyFK
     */
    private boolean _has_treatyFK;

    /**
     * Field _EDITTrxVOList
     */
    private java.util.Vector _EDITTrxVOList;

    /**
     * Field _withholdingOverrideVOList
     */
    private java.util.Vector _withholdingOverrideVOList;

    /**
     * Field _contractClientAllocationOvrdVOList
     */
    private java.util.Vector _contractClientAllocationOvrdVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ClientSetupVO() {
        super();
        _EDITTrxVOList = new Vector();
        _withholdingOverrideVOList = new Vector();
        _contractClientAllocationOvrdVOList = new Vector();
    } //-- edit.common.vo.ClientSetupVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addContractClientAllocationOvrdVO
     * 
     * @param vContractClientAllocationOvrdVO
     */
    public void addContractClientAllocationOvrdVO(edit.common.vo.ContractClientAllocationOvrdVO vContractClientAllocationOvrdVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractClientAllocationOvrdVO.setParentVO(this.getClass(), this);
        _contractClientAllocationOvrdVOList.addElement(vContractClientAllocationOvrdVO);
    } //-- void addContractClientAllocationOvrdVO(edit.common.vo.ContractClientAllocationOvrdVO) 

    /**
     * Method addContractClientAllocationOvrdVO
     * 
     * @param index
     * @param vContractClientAllocationOvrdVO
     */
    public void addContractClientAllocationOvrdVO(int index, edit.common.vo.ContractClientAllocationOvrdVO vContractClientAllocationOvrdVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractClientAllocationOvrdVO.setParentVO(this.getClass(), this);
        _contractClientAllocationOvrdVOList.insertElementAt(vContractClientAllocationOvrdVO, index);
    } //-- void addContractClientAllocationOvrdVO(int, edit.common.vo.ContractClientAllocationOvrdVO) 

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
     * Method addWithholdingOverrideVO
     * 
     * @param vWithholdingOverrideVO
     */
    public void addWithholdingOverrideVO(edit.common.vo.WithholdingOverrideVO vWithholdingOverrideVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vWithholdingOverrideVO.setParentVO(this.getClass(), this);
        _withholdingOverrideVOList.addElement(vWithholdingOverrideVO);
    } //-- void addWithholdingOverrideVO(edit.common.vo.WithholdingOverrideVO) 

    /**
     * Method addWithholdingOverrideVO
     * 
     * @param index
     * @param vWithholdingOverrideVO
     */
    public void addWithholdingOverrideVO(int index, edit.common.vo.WithholdingOverrideVO vWithholdingOverrideVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vWithholdingOverrideVO.setParentVO(this.getClass(), this);
        _withholdingOverrideVOList.insertElementAt(vWithholdingOverrideVO, index);
    } //-- void addWithholdingOverrideVO(int, edit.common.vo.WithholdingOverrideVO) 

    /**
     * Method enumerateContractClientAllocationOvrdVO
     */
    public java.util.Enumeration enumerateContractClientAllocationOvrdVO()
    {
        return _contractClientAllocationOvrdVOList.elements();
    } //-- java.util.Enumeration enumerateContractClientAllocationOvrdVO() 

    /**
     * Method enumerateEDITTrxVO
     */
    public java.util.Enumeration enumerateEDITTrxVO()
    {
        return _EDITTrxVOList.elements();
    } //-- java.util.Enumeration enumerateEDITTrxVO() 

    /**
     * Method enumerateWithholdingOverrideVO
     */
    public java.util.Enumeration enumerateWithholdingOverrideVO()
    {
        return _withholdingOverrideVOList.elements();
    } //-- java.util.Enumeration enumerateWithholdingOverrideVO() 

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
        
        if (obj instanceof ClientSetupVO) {
        
            ClientSetupVO temp = (ClientSetupVO)obj;
            if (this._clientSetupPK != temp._clientSetupPK)
                return false;
            if (this._has_clientSetupPK != temp._has_clientSetupPK)
                return false;
            if (this._contractSetupFK != temp._contractSetupFK)
                return false;
            if (this._has_contractSetupFK != temp._has_contractSetupFK)
                return false;
            if (this._contractClientFK != temp._contractClientFK)
                return false;
            if (this._has_contractClientFK != temp._has_contractClientFK)
                return false;
            if (this._clientRoleFK != temp._clientRoleFK)
                return false;
            if (this._has_clientRoleFK != temp._has_clientRoleFK)
                return false;
            if (this._treatyFK != temp._treatyFK)
                return false;
            if (this._has_treatyFK != temp._has_treatyFK)
                return false;
            if (this._EDITTrxVOList != null) {
                if (temp._EDITTrxVOList == null) return false;
                else if (!(this._EDITTrxVOList.equals(temp._EDITTrxVOList))) 
                    return false;
            }
            else if (temp._EDITTrxVOList != null)
                return false;
            if (this._withholdingOverrideVOList != null) {
                if (temp._withholdingOverrideVOList == null) return false;
                else if (!(this._withholdingOverrideVOList.equals(temp._withholdingOverrideVOList))) 
                    return false;
            }
            else if (temp._withholdingOverrideVOList != null)
                return false;
            if (this._contractClientAllocationOvrdVOList != null) {
                if (temp._contractClientAllocationOvrdVOList == null) return false;
                else if (!(this._contractClientAllocationOvrdVOList.equals(temp._contractClientAllocationOvrdVOList))) 
                    return false;
            }
            else if (temp._contractClientAllocationOvrdVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getClientRoleFKReturns the value of field
     * 'clientRoleFK'.
     * 
     * @return the value of field 'clientRoleFK'.
     */
    public long getClientRoleFK()
    {
        return this._clientRoleFK;
    } //-- long getClientRoleFK() 

    /**
     * Method getClientSetupPKReturns the value of field
     * 'clientSetupPK'.
     * 
     * @return the value of field 'clientSetupPK'.
     */
    public long getClientSetupPK()
    {
        return this._clientSetupPK;
    } //-- long getClientSetupPK() 

    /**
     * Method getContractClientAllocationOvrdVO
     * 
     * @param index
     */
    public edit.common.vo.ContractClientAllocationOvrdVO getContractClientAllocationOvrdVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractClientAllocationOvrdVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ContractClientAllocationOvrdVO) _contractClientAllocationOvrdVOList.elementAt(index);
    } //-- edit.common.vo.ContractClientAllocationOvrdVO getContractClientAllocationOvrdVO(int) 

    /**
     * Method getContractClientAllocationOvrdVO
     */
    public edit.common.vo.ContractClientAllocationOvrdVO[] getContractClientAllocationOvrdVO()
    {
        int size = _contractClientAllocationOvrdVOList.size();
        edit.common.vo.ContractClientAllocationOvrdVO[] mArray = new edit.common.vo.ContractClientAllocationOvrdVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ContractClientAllocationOvrdVO) _contractClientAllocationOvrdVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ContractClientAllocationOvrdVO[] getContractClientAllocationOvrdVO() 

    /**
     * Method getContractClientAllocationOvrdVOCount
     */
    public int getContractClientAllocationOvrdVOCount()
    {
        return _contractClientAllocationOvrdVOList.size();
    } //-- int getContractClientAllocationOvrdVOCount() 

    /**
     * Method getContractClientFKReturns the value of field
     * 'contractClientFK'.
     * 
     * @return the value of field 'contractClientFK'.
     */
    public long getContractClientFK()
    {
        return this._contractClientFK;
    } //-- long getContractClientFK() 

    /**
     * Method getContractSetupFKReturns the value of field
     * 'contractSetupFK'.
     * 
     * @return the value of field 'contractSetupFK'.
     */
    public long getContractSetupFK()
    {
        return this._contractSetupFK;
    } //-- long getContractSetupFK() 

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
     * Method getTreatyFKReturns the value of field 'treatyFK'.
     * 
     * @return the value of field 'treatyFK'.
     */
    public long getTreatyFK()
    {
        return this._treatyFK;
    } //-- long getTreatyFK() 

    /**
     * Method getWithholdingOverrideVO
     * 
     * @param index
     */
    public edit.common.vo.WithholdingOverrideVO getWithholdingOverrideVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _withholdingOverrideVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.WithholdingOverrideVO) _withholdingOverrideVOList.elementAt(index);
    } //-- edit.common.vo.WithholdingOverrideVO getWithholdingOverrideVO(int) 

    /**
     * Method getWithholdingOverrideVO
     */
    public edit.common.vo.WithholdingOverrideVO[] getWithholdingOverrideVO()
    {
        int size = _withholdingOverrideVOList.size();
        edit.common.vo.WithholdingOverrideVO[] mArray = new edit.common.vo.WithholdingOverrideVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.WithholdingOverrideVO) _withholdingOverrideVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.WithholdingOverrideVO[] getWithholdingOverrideVO() 

    /**
     * Method getWithholdingOverrideVOCount
     */
    public int getWithholdingOverrideVOCount()
    {
        return _withholdingOverrideVOList.size();
    } //-- int getWithholdingOverrideVOCount() 

    /**
     * Method hasClientRoleFK
     */
    public boolean hasClientRoleFK()
    {
        return this._has_clientRoleFK;
    } //-- boolean hasClientRoleFK() 

    /**
     * Method hasClientSetupPK
     */
    public boolean hasClientSetupPK()
    {
        return this._has_clientSetupPK;
    } //-- boolean hasClientSetupPK() 

    /**
     * Method hasContractClientFK
     */
    public boolean hasContractClientFK()
    {
        return this._has_contractClientFK;
    } //-- boolean hasContractClientFK() 

    /**
     * Method hasContractSetupFK
     */
    public boolean hasContractSetupFK()
    {
        return this._has_contractSetupFK;
    } //-- boolean hasContractSetupFK() 

    /**
     * Method hasTreatyFK
     */
    public boolean hasTreatyFK()
    {
        return this._has_treatyFK;
    } //-- boolean hasTreatyFK() 

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
     * Method removeAllContractClientAllocationOvrdVO
     */
    public void removeAllContractClientAllocationOvrdVO()
    {
        _contractClientAllocationOvrdVOList.removeAllElements();
    } //-- void removeAllContractClientAllocationOvrdVO() 

    /**
     * Method removeAllEDITTrxVO
     */
    public void removeAllEDITTrxVO()
    {
        _EDITTrxVOList.removeAllElements();
    } //-- void removeAllEDITTrxVO() 

    /**
     * Method removeAllWithholdingOverrideVO
     */
    public void removeAllWithholdingOverrideVO()
    {
        _withholdingOverrideVOList.removeAllElements();
    } //-- void removeAllWithholdingOverrideVO() 

    /**
     * Method removeContractClientAllocationOvrdVO
     * 
     * @param index
     */
    public edit.common.vo.ContractClientAllocationOvrdVO removeContractClientAllocationOvrdVO(int index)
    {
        java.lang.Object obj = _contractClientAllocationOvrdVOList.elementAt(index);
        _contractClientAllocationOvrdVOList.removeElementAt(index);
        return (edit.common.vo.ContractClientAllocationOvrdVO) obj;
    } //-- edit.common.vo.ContractClientAllocationOvrdVO removeContractClientAllocationOvrdVO(int) 

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
     * Method removeWithholdingOverrideVO
     * 
     * @param index
     */
    public edit.common.vo.WithholdingOverrideVO removeWithholdingOverrideVO(int index)
    {
        java.lang.Object obj = _withholdingOverrideVOList.elementAt(index);
        _withholdingOverrideVOList.removeElementAt(index);
        return (edit.common.vo.WithholdingOverrideVO) obj;
    } //-- edit.common.vo.WithholdingOverrideVO removeWithholdingOverrideVO(int) 

    /**
     * Method setClientRoleFKSets the value of field
     * 'clientRoleFK'.
     * 
     * @param clientRoleFK the value of field 'clientRoleFK'.
     */
    public void setClientRoleFK(long clientRoleFK)
    {
        this._clientRoleFK = clientRoleFK;
        
        super.setVoChanged(true);
        this._has_clientRoleFK = true;
    } //-- void setClientRoleFK(long) 

    /**
     * Method setClientSetupPKSets the value of field
     * 'clientSetupPK'.
     * 
     * @param clientSetupPK the value of field 'clientSetupPK'.
     */
    public void setClientSetupPK(long clientSetupPK)
    {
        this._clientSetupPK = clientSetupPK;
        
        super.setVoChanged(true);
        this._has_clientSetupPK = true;
    } //-- void setClientSetupPK(long) 

    /**
     * Method setContractClientAllocationOvrdVO
     * 
     * @param index
     * @param vContractClientAllocationOvrdVO
     */
    public void setContractClientAllocationOvrdVO(int index, edit.common.vo.ContractClientAllocationOvrdVO vContractClientAllocationOvrdVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractClientAllocationOvrdVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vContractClientAllocationOvrdVO.setParentVO(this.getClass(), this);
        _contractClientAllocationOvrdVOList.setElementAt(vContractClientAllocationOvrdVO, index);
    } //-- void setContractClientAllocationOvrdVO(int, edit.common.vo.ContractClientAllocationOvrdVO) 

    /**
     * Method setContractClientAllocationOvrdVO
     * 
     * @param contractClientAllocationOvrdVOArray
     */
    public void setContractClientAllocationOvrdVO(edit.common.vo.ContractClientAllocationOvrdVO[] contractClientAllocationOvrdVOArray)
    {
        //-- copy array
        _contractClientAllocationOvrdVOList.removeAllElements();
        for (int i = 0; i < contractClientAllocationOvrdVOArray.length; i++) {
            contractClientAllocationOvrdVOArray[i].setParentVO(this.getClass(), this);
            _contractClientAllocationOvrdVOList.addElement(contractClientAllocationOvrdVOArray[i]);
        }
    } //-- void setContractClientAllocationOvrdVO(edit.common.vo.ContractClientAllocationOvrdVO) 

    /**
     * Method setContractClientFKSets the value of field
     * 'contractClientFK'.
     * 
     * @param contractClientFK the value of field 'contractClientFK'
     */
    public void setContractClientFK(long contractClientFK)
    {
        this._contractClientFK = contractClientFK;
        
        super.setVoChanged(true);
        this._has_contractClientFK = true;
    } //-- void setContractClientFK(long) 

    /**
     * Method setContractSetupFKSets the value of field
     * 'contractSetupFK'.
     * 
     * @param contractSetupFK the value of field 'contractSetupFK'.
     */
    public void setContractSetupFK(long contractSetupFK)
    {
        this._contractSetupFK = contractSetupFK;
        
        super.setVoChanged(true);
        this._has_contractSetupFK = true;
    } //-- void setContractSetupFK(long) 

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
     * Method setTreatyFKSets the value of field 'treatyFK'.
     * 
     * @param treatyFK the value of field 'treatyFK'.
     */
    public void setTreatyFK(long treatyFK)
    {
        this._treatyFK = treatyFK;
        
        super.setVoChanged(true);
        this._has_treatyFK = true;
    } //-- void setTreatyFK(long) 

    /**
     * Method setWithholdingOverrideVO
     * 
     * @param index
     * @param vWithholdingOverrideVO
     */
    public void setWithholdingOverrideVO(int index, edit.common.vo.WithholdingOverrideVO vWithholdingOverrideVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _withholdingOverrideVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vWithholdingOverrideVO.setParentVO(this.getClass(), this);
        _withholdingOverrideVOList.setElementAt(vWithholdingOverrideVO, index);
    } //-- void setWithholdingOverrideVO(int, edit.common.vo.WithholdingOverrideVO) 

    /**
     * Method setWithholdingOverrideVO
     * 
     * @param withholdingOverrideVOArray
     */
    public void setWithholdingOverrideVO(edit.common.vo.WithholdingOverrideVO[] withholdingOverrideVOArray)
    {
        //-- copy array
        _withholdingOverrideVOList.removeAllElements();
        for (int i = 0; i < withholdingOverrideVOArray.length; i++) {
            withholdingOverrideVOArray[i].setParentVO(this.getClass(), this);
            _withholdingOverrideVOList.addElement(withholdingOverrideVOArray[i]);
        }
    } //-- void setWithholdingOverrideVO(edit.common.vo.WithholdingOverrideVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ClientSetupVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ClientSetupVO) Unmarshaller.unmarshal(edit.common.vo.ClientSetupVO.class, reader);
    } //-- edit.common.vo.ClientSetupVO unmarshal(java.io.Reader) 

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
