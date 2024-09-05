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
 * Class CommissionLevelVO.
 * 
 * @version $Revision$ $Date$
 */
public class CommissionLevelVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _commissionLevelPK
     */
    private long _commissionLevelPK;

    /**
     * keeps track of state for field: _commissionLevelPK
     */
    private boolean _has_commissionLevelPK;

    /**
     * Field _commissionContractFK
     */
    private long _commissionContractFK;

    /**
     * keeps track of state for field: _commissionContractFK
     */
    private boolean _has_commissionContractFK;

    /**
     * Field _commissionLevelDescriptionFK
     */
    private long _commissionLevelDescriptionFK;

    /**
     * keeps track of state for field: _commissionLevelDescriptionFK
     */
    private boolean _has_commissionLevelDescriptionFK;

    /**
     * Field _commissionProfileVOList
     */
    private java.util.Vector _commissionProfileVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public CommissionLevelVO() {
        super();
        _commissionProfileVOList = new Vector();
    } //-- edit.common.vo.CommissionLevelVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addCommissionProfileVO
     * 
     * @param vCommissionProfileVO
     */
    public void addCommissionProfileVO(edit.common.vo.CommissionProfileVO vCommissionProfileVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionProfileVO.setParentVO(this.getClass(), this);
        _commissionProfileVOList.addElement(vCommissionProfileVO);
    } //-- void addCommissionProfileVO(edit.common.vo.CommissionProfileVO) 

    /**
     * Method addCommissionProfileVO
     * 
     * @param index
     * @param vCommissionProfileVO
     */
    public void addCommissionProfileVO(int index, edit.common.vo.CommissionProfileVO vCommissionProfileVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionProfileVO.setParentVO(this.getClass(), this);
        _commissionProfileVOList.insertElementAt(vCommissionProfileVO, index);
    } //-- void addCommissionProfileVO(int, edit.common.vo.CommissionProfileVO) 

    /**
     * Method enumerateCommissionProfileVO
     */
    public java.util.Enumeration enumerateCommissionProfileVO()
    {
        return _commissionProfileVOList.elements();
    } //-- java.util.Enumeration enumerateCommissionProfileVO() 

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
        
        if (obj instanceof CommissionLevelVO) {
        
            CommissionLevelVO temp = (CommissionLevelVO)obj;
            if (this._commissionLevelPK != temp._commissionLevelPK)
                return false;
            if (this._has_commissionLevelPK != temp._has_commissionLevelPK)
                return false;
            if (this._commissionContractFK != temp._commissionContractFK)
                return false;
            if (this._has_commissionContractFK != temp._has_commissionContractFK)
                return false;
            if (this._commissionLevelDescriptionFK != temp._commissionLevelDescriptionFK)
                return false;
            if (this._has_commissionLevelDescriptionFK != temp._has_commissionLevelDescriptionFK)
                return false;
            if (this._commissionProfileVOList != null) {
                if (temp._commissionProfileVOList == null) return false;
                else if (!(this._commissionProfileVOList.equals(temp._commissionProfileVOList))) 
                    return false;
            }
            else if (temp._commissionProfileVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCommissionContractFKReturns the value of field
     * 'commissionContractFK'.
     * 
     * @return the value of field 'commissionContractFK'.
     */
    public long getCommissionContractFK()
    {
        return this._commissionContractFK;
    } //-- long getCommissionContractFK() 

    /**
     * Method getCommissionLevelDescriptionFKReturns the value of
     * field 'commissionLevelDescriptionFK'.
     * 
     * @return the value of field 'commissionLevelDescriptionFK'.
     */
    public long getCommissionLevelDescriptionFK()
    {
        return this._commissionLevelDescriptionFK;
    } //-- long getCommissionLevelDescriptionFK() 

    /**
     * Method getCommissionLevelPKReturns the value of field
     * 'commissionLevelPK'.
     * 
     * @return the value of field 'commissionLevelPK'.
     */
    public long getCommissionLevelPK()
    {
        return this._commissionLevelPK;
    } //-- long getCommissionLevelPK() 

    /**
     * Method getCommissionProfileVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionProfileVO getCommissionProfileVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionProfileVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.CommissionProfileVO) _commissionProfileVOList.elementAt(index);
    } //-- edit.common.vo.CommissionProfileVO getCommissionProfileVO(int) 

    /**
     * Method getCommissionProfileVO
     */
    public edit.common.vo.CommissionProfileVO[] getCommissionProfileVO()
    {
        int size = _commissionProfileVOList.size();
        edit.common.vo.CommissionProfileVO[] mArray = new edit.common.vo.CommissionProfileVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.CommissionProfileVO) _commissionProfileVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.CommissionProfileVO[] getCommissionProfileVO() 

    /**
     * Method getCommissionProfileVOCount
     */
    public int getCommissionProfileVOCount()
    {
        return _commissionProfileVOList.size();
    } //-- int getCommissionProfileVOCount() 

    /**
     * Method hasCommissionContractFK
     */
    public boolean hasCommissionContractFK()
    {
        return this._has_commissionContractFK;
    } //-- boolean hasCommissionContractFK() 

    /**
     * Method hasCommissionLevelDescriptionFK
     */
    public boolean hasCommissionLevelDescriptionFK()
    {
        return this._has_commissionLevelDescriptionFK;
    } //-- boolean hasCommissionLevelDescriptionFK() 

    /**
     * Method hasCommissionLevelPK
     */
    public boolean hasCommissionLevelPK()
    {
        return this._has_commissionLevelPK;
    } //-- boolean hasCommissionLevelPK() 

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
     * Method removeAllCommissionProfileVO
     */
    public void removeAllCommissionProfileVO()
    {
        _commissionProfileVOList.removeAllElements();
    } //-- void removeAllCommissionProfileVO() 

    /**
     * Method removeCommissionProfileVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionProfileVO removeCommissionProfileVO(int index)
    {
        java.lang.Object obj = _commissionProfileVOList.elementAt(index);
        _commissionProfileVOList.removeElementAt(index);
        return (edit.common.vo.CommissionProfileVO) obj;
    } //-- edit.common.vo.CommissionProfileVO removeCommissionProfileVO(int) 

    /**
     * Method setCommissionContractFKSets the value of field
     * 'commissionContractFK'.
     * 
     * @param commissionContractFK the value of field
     * 'commissionContractFK'.
     */
    public void setCommissionContractFK(long commissionContractFK)
    {
        this._commissionContractFK = commissionContractFK;
        
        super.setVoChanged(true);
        this._has_commissionContractFK = true;
    } //-- void setCommissionContractFK(long) 

    /**
     * Method setCommissionLevelDescriptionFKSets the value of
     * field 'commissionLevelDescriptionFK'.
     * 
     * @param commissionLevelDescriptionFK the value of field
     * 'commissionLevelDescriptionFK'.
     */
    public void setCommissionLevelDescriptionFK(long commissionLevelDescriptionFK)
    {
        this._commissionLevelDescriptionFK = commissionLevelDescriptionFK;
        
        super.setVoChanged(true);
        this._has_commissionLevelDescriptionFK = true;
    } //-- void setCommissionLevelDescriptionFK(long) 

    /**
     * Method setCommissionLevelPKSets the value of field
     * 'commissionLevelPK'.
     * 
     * @param commissionLevelPK the value of field
     * 'commissionLevelPK'.
     */
    public void setCommissionLevelPK(long commissionLevelPK)
    {
        this._commissionLevelPK = commissionLevelPK;
        
        super.setVoChanged(true);
        this._has_commissionLevelPK = true;
    } //-- void setCommissionLevelPK(long) 

    /**
     * Method setCommissionProfileVO
     * 
     * @param index
     * @param vCommissionProfileVO
     */
    public void setCommissionProfileVO(int index, edit.common.vo.CommissionProfileVO vCommissionProfileVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionProfileVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vCommissionProfileVO.setParentVO(this.getClass(), this);
        _commissionProfileVOList.setElementAt(vCommissionProfileVO, index);
    } //-- void setCommissionProfileVO(int, edit.common.vo.CommissionProfileVO) 

    /**
     * Method setCommissionProfileVO
     * 
     * @param commissionProfileVOArray
     */
    public void setCommissionProfileVO(edit.common.vo.CommissionProfileVO[] commissionProfileVOArray)
    {
        //-- copy array
        _commissionProfileVOList.removeAllElements();
        for (int i = 0; i < commissionProfileVOArray.length; i++) {
            commissionProfileVOArray[i].setParentVO(this.getClass(), this);
            _commissionProfileVOList.addElement(commissionProfileVOArray[i]);
        }
    } //-- void setCommissionProfileVO(edit.common.vo.CommissionProfileVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CommissionLevelVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CommissionLevelVO) Unmarshaller.unmarshal(edit.common.vo.CommissionLevelVO.class, reader);
    } //-- edit.common.vo.CommissionLevelVO unmarshal(java.io.Reader) 

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
