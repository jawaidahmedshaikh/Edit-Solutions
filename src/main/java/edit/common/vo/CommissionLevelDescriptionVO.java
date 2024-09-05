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
 * Class CommissionLevelDescriptionVO.
 * 
 * @version $Revision$ $Date$
 */
public class CommissionLevelDescriptionVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _commissionLevelDescriptionPK
     */
    private long _commissionLevelDescriptionPK;

    /**
     * keeps track of state for field: _commissionLevelDescriptionPK
     */
    private boolean _has_commissionLevelDescriptionPK;

    /**
     * Field _commissionLevel
     */
    private java.lang.String _commissionLevel;

    /**
     * Field _commissionLevelVOList
     */
    private java.util.Vector _commissionLevelVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public CommissionLevelDescriptionVO() {
        super();
        _commissionLevelVOList = new Vector();
    } //-- edit.common.vo.CommissionLevelDescriptionVO()


      //-----------/
     //- Methods -/
    //-----------/

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
        
        if (obj instanceof CommissionLevelDescriptionVO) {
        
            CommissionLevelDescriptionVO temp = (CommissionLevelDescriptionVO)obj;
            if (this._commissionLevelDescriptionPK != temp._commissionLevelDescriptionPK)
                return false;
            if (this._has_commissionLevelDescriptionPK != temp._has_commissionLevelDescriptionPK)
                return false;
            if (this._commissionLevel != null) {
                if (temp._commissionLevel == null) return false;
                else if (!(this._commissionLevel.equals(temp._commissionLevel))) 
                    return false;
            }
            else if (temp._commissionLevel != null)
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
     * Method getCommissionLevelReturns the value of field
     * 'commissionLevel'.
     * 
     * @return the value of field 'commissionLevel'.
     */
    public java.lang.String getCommissionLevel()
    {
        return this._commissionLevel;
    } //-- java.lang.String getCommissionLevel() 

    /**
     * Method getCommissionLevelDescriptionPKReturns the value of
     * field 'commissionLevelDescriptionPK'.
     * 
     * @return the value of field 'commissionLevelDescriptionPK'.
     */
    public long getCommissionLevelDescriptionPK()
    {
        return this._commissionLevelDescriptionPK;
    } //-- long getCommissionLevelDescriptionPK() 

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
     * Method hasCommissionLevelDescriptionPK
     */
    public boolean hasCommissionLevelDescriptionPK()
    {
        return this._has_commissionLevelDescriptionPK;
    } //-- boolean hasCommissionLevelDescriptionPK() 

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
     * Method setCommissionLevelSets the value of field
     * 'commissionLevel'.
     * 
     * @param commissionLevel the value of field 'commissionLevel'.
     */
    public void setCommissionLevel(java.lang.String commissionLevel)
    {
        this._commissionLevel = commissionLevel;
        
        super.setVoChanged(true);
    } //-- void setCommissionLevel(java.lang.String) 

    /**
     * Method setCommissionLevelDescriptionPKSets the value of
     * field 'commissionLevelDescriptionPK'.
     * 
     * @param commissionLevelDescriptionPK the value of field
     * 'commissionLevelDescriptionPK'.
     */
    public void setCommissionLevelDescriptionPK(long commissionLevelDescriptionPK)
    {
        this._commissionLevelDescriptionPK = commissionLevelDescriptionPK;
        
        super.setVoChanged(true);
        this._has_commissionLevelDescriptionPK = true;
    } //-- void setCommissionLevelDescriptionPK(long) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CommissionLevelDescriptionVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CommissionLevelDescriptionVO) Unmarshaller.unmarshal(edit.common.vo.CommissionLevelDescriptionVO.class, reader);
    } //-- edit.common.vo.CommissionLevelDescriptionVO unmarshal(java.io.Reader) 

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
