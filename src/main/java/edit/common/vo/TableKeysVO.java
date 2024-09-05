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
 * Class TableKeysVO.
 * 
 * @version $Revision$ $Date$
 */
public class TableKeysVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _tableKeysPK
     */
    private long _tableKeysPK;

    /**
     * keeps track of state for field: _tableKeysPK
     */
    private boolean _has_tableKeysPK;

    /**
     * Field _tableDefFK
     */
    private long _tableDefFK;

    /**
     * keeps track of state for field: _tableDefFK
     */
    private boolean _has_tableDefFK;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _bandAmount
     */
    private java.math.BigDecimal _bandAmount;

    /**
     * Field _userKey
     */
    private java.lang.String _userKey;

    /**
     * Field _gender
     */
    private java.lang.String _gender;

    /**
     * Field _classType
     */
    private java.lang.String _classType;

    /**
     * Field _state
     */
    private java.lang.String _state;

    /**
     * Field _tableType
     */
    private java.lang.String _tableType;

    /**
     * Field _rateTableVOList
     */
    private java.util.Vector _rateTableVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public TableKeysVO() {
        super();
        _rateTableVOList = new Vector();
    } //-- edit.common.vo.TableKeysVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addRateTableVO
     * 
     * @param vRateTableVO
     */
    public void addRateTableVO(edit.common.vo.RateTableVO vRateTableVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vRateTableVO.setParentVO(this.getClass(), this);
        _rateTableVOList.addElement(vRateTableVO);
    } //-- void addRateTableVO(edit.common.vo.RateTableVO) 

    /**
     * Method addRateTableVO
     * 
     * @param index
     * @param vRateTableVO
     */
    public void addRateTableVO(int index, edit.common.vo.RateTableVO vRateTableVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vRateTableVO.setParentVO(this.getClass(), this);
        _rateTableVOList.insertElementAt(vRateTableVO, index);
    } //-- void addRateTableVO(int, edit.common.vo.RateTableVO) 

    /**
     * Method enumerateRateTableVO
     */
    public java.util.Enumeration enumerateRateTableVO()
    {
        return _rateTableVOList.elements();
    } //-- java.util.Enumeration enumerateRateTableVO() 

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
        
        if (obj instanceof TableKeysVO) {
        
            TableKeysVO temp = (TableKeysVO)obj;
            if (this._tableKeysPK != temp._tableKeysPK)
                return false;
            if (this._has_tableKeysPK != temp._has_tableKeysPK)
                return false;
            if (this._tableDefFK != temp._tableDefFK)
                return false;
            if (this._has_tableDefFK != temp._has_tableDefFK)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._bandAmount != null) {
                if (temp._bandAmount == null) return false;
                else if (!(this._bandAmount.equals(temp._bandAmount))) 
                    return false;
            }
            else if (temp._bandAmount != null)
                return false;
            if (this._userKey != null) {
                if (temp._userKey == null) return false;
                else if (!(this._userKey.equals(temp._userKey))) 
                    return false;
            }
            else if (temp._userKey != null)
                return false;
            if (this._gender != null) {
                if (temp._gender == null) return false;
                else if (!(this._gender.equals(temp._gender))) 
                    return false;
            }
            else if (temp._gender != null)
                return false;
            if (this._classType != null) {
                if (temp._classType == null) return false;
                else if (!(this._classType.equals(temp._classType))) 
                    return false;
            }
            else if (temp._classType != null)
                return false;
            if (this._state != null) {
                if (temp._state == null) return false;
                else if (!(this._state.equals(temp._state))) 
                    return false;
            }
            else if (temp._state != null)
                return false;
            if (this._tableType != null) {
                if (temp._tableType == null) return false;
                else if (!(this._tableType.equals(temp._tableType))) 
                    return false;
            }
            else if (temp._tableType != null)
                return false;
            if (this._rateTableVOList != null) {
                if (temp._rateTableVOList == null) return false;
                else if (!(this._rateTableVOList.equals(temp._rateTableVOList))) 
                    return false;
            }
            else if (temp._rateTableVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBandAmountReturns the value of field 'bandAmount'.
     * 
     * @return the value of field 'bandAmount'.
     */
    public java.math.BigDecimal getBandAmount()
    {
        return this._bandAmount;
    } //-- java.math.BigDecimal getBandAmount() 

    /**
     * Method getClassTypeReturns the value of field 'classType'.
     * 
     * @return the value of field 'classType'.
     */
    public java.lang.String getClassType()
    {
        return this._classType;
    } //-- java.lang.String getClassType() 

    /**
     * Method getEffectiveDateReturns the value of field
     * 'effectiveDate'.
     * 
     * @return the value of field 'effectiveDate'.
     */
    public java.lang.String getEffectiveDate()
    {
        return this._effectiveDate;
    } //-- java.lang.String getEffectiveDate() 

    /**
     * Method getGenderReturns the value of field 'gender'.
     * 
     * @return the value of field 'gender'.
     */
    public java.lang.String getGender()
    {
        return this._gender;
    } //-- java.lang.String getGender() 

    /**
     * Method getRateTableVO
     * 
     * @param index
     */
    public edit.common.vo.RateTableVO getRateTableVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _rateTableVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.RateTableVO) _rateTableVOList.elementAt(index);
    } //-- edit.common.vo.RateTableVO getRateTableVO(int) 

    /**
     * Method getRateTableVO
     */
    public edit.common.vo.RateTableVO[] getRateTableVO()
    {
        int size = _rateTableVOList.size();
        edit.common.vo.RateTableVO[] mArray = new edit.common.vo.RateTableVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.RateTableVO) _rateTableVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.RateTableVO[] getRateTableVO() 

    /**
     * Method getRateTableVOCount
     */
    public int getRateTableVOCount()
    {
        return _rateTableVOList.size();
    } //-- int getRateTableVOCount() 

    /**
     * Method getStateReturns the value of field 'state'.
     * 
     * @return the value of field 'state'.
     */
    public java.lang.String getState()
    {
        return this._state;
    } //-- java.lang.String getState() 

    /**
     * Method getTableDefFKReturns the value of field 'tableDefFK'.
     * 
     * @return the value of field 'tableDefFK'.
     */
    public long getTableDefFK()
    {
        return this._tableDefFK;
    } //-- long getTableDefFK() 

    /**
     * Method getTableKeysPKReturns the value of field
     * 'tableKeysPK'.
     * 
     * @return the value of field 'tableKeysPK'.
     */
    public long getTableKeysPK()
    {
        return this._tableKeysPK;
    } //-- long getTableKeysPK() 

    /**
     * Method getTableTypeReturns the value of field 'tableType'.
     * 
     * @return the value of field 'tableType'.
     */
    public java.lang.String getTableType()
    {
        return this._tableType;
    } //-- java.lang.String getTableType() 

    /**
     * Method getUserKeyReturns the value of field 'userKey'.
     * 
     * @return the value of field 'userKey'.
     */
    public java.lang.String getUserKey()
    {
        return this._userKey;
    } //-- java.lang.String getUserKey() 

    /**
     * Method hasTableDefFK
     */
    public boolean hasTableDefFK()
    {
        return this._has_tableDefFK;
    } //-- boolean hasTableDefFK() 

    /**
     * Method hasTableKeysPK
     */
    public boolean hasTableKeysPK()
    {
        return this._has_tableKeysPK;
    } //-- boolean hasTableKeysPK() 

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
     * Method removeAllRateTableVO
     */
    public void removeAllRateTableVO()
    {
        _rateTableVOList.removeAllElements();
    } //-- void removeAllRateTableVO() 

    /**
     * Method removeRateTableVO
     * 
     * @param index
     */
    public edit.common.vo.RateTableVO removeRateTableVO(int index)
    {
        java.lang.Object obj = _rateTableVOList.elementAt(index);
        _rateTableVOList.removeElementAt(index);
        return (edit.common.vo.RateTableVO) obj;
    } //-- edit.common.vo.RateTableVO removeRateTableVO(int) 

    /**
     * Method setBandAmountSets the value of field 'bandAmount'.
     * 
     * @param bandAmount the value of field 'bandAmount'.
     */
    public void setBandAmount(java.math.BigDecimal bandAmount)
    {
        this._bandAmount = bandAmount;
        
        super.setVoChanged(true);
    } //-- void setBandAmount(java.math.BigDecimal) 

    /**
     * Method setClassTypeSets the value of field 'classType'.
     * 
     * @param classType the value of field 'classType'.
     */
    public void setClassType(java.lang.String classType)
    {
        this._classType = classType;
        
        super.setVoChanged(true);
    } //-- void setClassType(java.lang.String) 

    /**
     * Method setEffectiveDateSets the value of field
     * 'effectiveDate'.
     * 
     * @param effectiveDate the value of field 'effectiveDate'.
     */
    public void setEffectiveDate(java.lang.String effectiveDate)
    {
        this._effectiveDate = effectiveDate;
        
        super.setVoChanged(true);
    } //-- void setEffectiveDate(java.lang.String) 

    /**
     * Method setGenderSets the value of field 'gender'.
     * 
     * @param gender the value of field 'gender'.
     */
    public void setGender(java.lang.String gender)
    {
        this._gender = gender;
        
        super.setVoChanged(true);
    } //-- void setGender(java.lang.String) 

    /**
     * Method setRateTableVO
     * 
     * @param index
     * @param vRateTableVO
     */
    public void setRateTableVO(int index, edit.common.vo.RateTableVO vRateTableVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _rateTableVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vRateTableVO.setParentVO(this.getClass(), this);
        _rateTableVOList.setElementAt(vRateTableVO, index);
    } //-- void setRateTableVO(int, edit.common.vo.RateTableVO) 

    /**
     * Method setRateTableVO
     * 
     * @param rateTableVOArray
     */
    public void setRateTableVO(edit.common.vo.RateTableVO[] rateTableVOArray)
    {
        //-- copy array
        _rateTableVOList.removeAllElements();
        for (int i = 0; i < rateTableVOArray.length; i++) {
            rateTableVOArray[i].setParentVO(this.getClass(), this);
            _rateTableVOList.addElement(rateTableVOArray[i]);
        }
    } //-- void setRateTableVO(edit.common.vo.RateTableVO) 

    /**
     * Method setStateSets the value of field 'state'.
     * 
     * @param state the value of field 'state'.
     */
    public void setState(java.lang.String state)
    {
        this._state = state;
        
        super.setVoChanged(true);
    } //-- void setState(java.lang.String) 

    /**
     * Method setTableDefFKSets the value of field 'tableDefFK'.
     * 
     * @param tableDefFK the value of field 'tableDefFK'.
     */
    public void setTableDefFK(long tableDefFK)
    {
        this._tableDefFK = tableDefFK;
        
        super.setVoChanged(true);
        this._has_tableDefFK = true;
    } //-- void setTableDefFK(long) 

    /**
     * Method setTableKeysPKSets the value of field 'tableKeysPK'.
     * 
     * @param tableKeysPK the value of field 'tableKeysPK'.
     */
    public void setTableKeysPK(long tableKeysPK)
    {
        this._tableKeysPK = tableKeysPK;
        
        super.setVoChanged(true);
        this._has_tableKeysPK = true;
    } //-- void setTableKeysPK(long) 

    /**
     * Method setTableTypeSets the value of field 'tableType'.
     * 
     * @param tableType the value of field 'tableType'.
     */
    public void setTableType(java.lang.String tableType)
    {
        this._tableType = tableType;
        
        super.setVoChanged(true);
    } //-- void setTableType(java.lang.String) 

    /**
     * Method setUserKeySets the value of field 'userKey'.
     * 
     * @param userKey the value of field 'userKey'.
     */
    public void setUserKey(java.lang.String userKey)
    {
        this._userKey = userKey;
        
        super.setVoChanged(true);
    } //-- void setUserKey(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.TableKeysVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.TableKeysVO) Unmarshaller.unmarshal(edit.common.vo.TableKeysVO.class, reader);
    } //-- edit.common.vo.TableKeysVO unmarshal(java.io.Reader) 

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
