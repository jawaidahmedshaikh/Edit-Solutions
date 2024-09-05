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
 * Class ContractClientAllocationVO.
 * 
 * @version $Revision$ $Date$
 */
public class ContractClientAllocationVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _contractClientAllocationPK
     */
    private long _contractClientAllocationPK;

    /**
     * keeps track of state for field: _contractClientAllocationPK
     */
    private boolean _has_contractClientAllocationPK;

    /**
     * Field _contractClientFK
     */
    private long _contractClientFK;

    /**
     * keeps track of state for field: _contractClientFK
     */
    private boolean _has_contractClientFK;

    /**
     * Field _allocationPercent
     */
    private java.math.BigDecimal _allocationPercent;

    /**
     * Field _overrideStatus
     */
    private java.lang.String _overrideStatus;

    /**
     * Field _allocationDollars
     */
    private java.math.BigDecimal _allocationDollars;

    /**
     * Field _splitEqual
     */
    private java.lang.String _splitEqual;

    /**
     * Field _contractClientAllocationOvrdVOList
     */
    private java.util.Vector _contractClientAllocationOvrdVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ContractClientAllocationVO() {
        super();
        _contractClientAllocationOvrdVOList = new Vector();
    } //-- edit.common.vo.ContractClientAllocationVO()


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
     * Method enumerateContractClientAllocationOvrdVO
     */
    public java.util.Enumeration enumerateContractClientAllocationOvrdVO()
    {
        return _contractClientAllocationOvrdVOList.elements();
    } //-- java.util.Enumeration enumerateContractClientAllocationOvrdVO() 

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
        
        if (obj instanceof ContractClientAllocationVO) {
        
            ContractClientAllocationVO temp = (ContractClientAllocationVO)obj;
            if (this._contractClientAllocationPK != temp._contractClientAllocationPK)
                return false;
            if (this._has_contractClientAllocationPK != temp._has_contractClientAllocationPK)
                return false;
            if (this._contractClientFK != temp._contractClientFK)
                return false;
            if (this._has_contractClientFK != temp._has_contractClientFK)
                return false;
            if (this._allocationPercent != null) {
                if (temp._allocationPercent == null) return false;
                else if (!(this._allocationPercent.equals(temp._allocationPercent))) 
                    return false;
            }
            else if (temp._allocationPercent != null)
                return false;
            if (this._overrideStatus != null) {
                if (temp._overrideStatus == null) return false;
                else if (!(this._overrideStatus.equals(temp._overrideStatus))) 
                    return false;
            }
            else if (temp._overrideStatus != null)
                return false;
            if (this._allocationDollars != null) {
                if (temp._allocationDollars == null) return false;
                else if (!(this._allocationDollars.equals(temp._allocationDollars))) 
                    return false;
            }
            else if (temp._allocationDollars != null)
                return false;
            if (this._splitEqual != null) {
                if (temp._splitEqual == null) return false;
                else if (!(this._splitEqual.equals(temp._splitEqual))) 
                    return false;
            }
            else if (temp._splitEqual != null)
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
     * Method getAllocationDollarsReturns the value of field
     * 'allocationDollars'.
     * 
     * @return the value of field 'allocationDollars'.
     */
    public java.math.BigDecimal getAllocationDollars()
    {
        return this._allocationDollars;
    } //-- java.math.BigDecimal getAllocationDollars() 

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
     * Method getContractClientAllocationPKReturns the value of
     * field 'contractClientAllocationPK'.
     * 
     * @return the value of field 'contractClientAllocationPK'.
     */
    public long getContractClientAllocationPK()
    {
        return this._contractClientAllocationPK;
    } //-- long getContractClientAllocationPK() 

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
     * Method getOverrideStatusReturns the value of field
     * 'overrideStatus'.
     * 
     * @return the value of field 'overrideStatus'.
     */
    public java.lang.String getOverrideStatus()
    {
        return this._overrideStatus;
    } //-- java.lang.String getOverrideStatus() 

    /**
     * Method getSplitEqualReturns the value of field 'splitEqual'.
     * 
     * @return the value of field 'splitEqual'.
     */
    public java.lang.String getSplitEqual()
    {
        return this._splitEqual;
    } //-- java.lang.String getSplitEqual() 

    /**
     * Method hasContractClientAllocationPK
     */
    public boolean hasContractClientAllocationPK()
    {
        return this._has_contractClientAllocationPK;
    } //-- boolean hasContractClientAllocationPK() 

    /**
     * Method hasContractClientFK
     */
    public boolean hasContractClientFK()
    {
        return this._has_contractClientFK;
    } //-- boolean hasContractClientFK() 

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
     * Method setAllocationDollarsSets the value of field
     * 'allocationDollars'.
     * 
     * @param allocationDollars the value of field
     * 'allocationDollars'.
     */
    public void setAllocationDollars(java.math.BigDecimal allocationDollars)
    {
        this._allocationDollars = allocationDollars;
        
        super.setVoChanged(true);
    } //-- void setAllocationDollars(java.math.BigDecimal) 

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
     * Method setContractClientAllocationPKSets the value of field
     * 'contractClientAllocationPK'.
     * 
     * @param contractClientAllocationPK the value of field
     * 'contractClientAllocationPK'.
     */
    public void setContractClientAllocationPK(long contractClientAllocationPK)
    {
        this._contractClientAllocationPK = contractClientAllocationPK;
        
        super.setVoChanged(true);
        this._has_contractClientAllocationPK = true;
    } //-- void setContractClientAllocationPK(long) 

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
     * Method setOverrideStatusSets the value of field
     * 'overrideStatus'.
     * 
     * @param overrideStatus the value of field 'overrideStatus'.
     */
    public void setOverrideStatus(java.lang.String overrideStatus)
    {
        this._overrideStatus = overrideStatus;
        
        super.setVoChanged(true);
    } //-- void setOverrideStatus(java.lang.String) 

    /**
     * Method setSplitEqualSets the value of field 'splitEqual'.
     * 
     * @param splitEqual the value of field 'splitEqual'.
     */
    public void setSplitEqual(java.lang.String splitEqual)
    {
        this._splitEqual = splitEqual;
        
        super.setVoChanged(true);
    } //-- void setSplitEqual(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ContractClientAllocationVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ContractClientAllocationVO) Unmarshaller.unmarshal(edit.common.vo.ContractClientAllocationVO.class, reader);
    } //-- edit.common.vo.ContractClientAllocationVO unmarshal(java.io.Reader) 

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
