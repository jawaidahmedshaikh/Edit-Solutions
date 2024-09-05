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
 * Class PasswordMaskVO.
 * 
 * @version $Revision$ $Date$
 */
public class PasswordMaskVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _passwordMaskPK
     */
    private long _passwordMaskPK;

    /**
     * keeps track of state for field: _passwordMaskPK
     */
    private boolean _has_passwordMaskPK;

    /**
     * Field _expirationInDays
     */
    private int _expirationInDays;

    /**
     * keeps track of state for field: _expirationInDays
     */
    private boolean _has_expirationInDays;

    /**
     * Field _restrictRepeats
     */
    private java.lang.String _restrictRepeats;

    /**
     * Field _numberOfRepeatCycles
     */
    private int _numberOfRepeatCycles;

    /**
     * keeps track of state for field: _numberOfRepeatCycles
     */
    private boolean _has_numberOfRepeatCycles;

    /**
     * Field _maskFK
     */
    private long _maskFK;

    /**
     * keeps track of state for field: _maskFK
     */
    private boolean _has_maskFK;

    /**
     * Field _passwordVOList
     */
    private java.util.Vector _passwordVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public PasswordMaskVO() {
        super();
        _passwordVOList = new Vector();
    } //-- edit.common.vo.PasswordMaskVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addPasswordVO
     * 
     * @param vPasswordVO
     */
    public void addPasswordVO(edit.common.vo.PasswordVO vPasswordVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPasswordVO.setParentVO(this.getClass(), this);
        _passwordVOList.addElement(vPasswordVO);
    } //-- void addPasswordVO(edit.common.vo.PasswordVO) 

    /**
     * Method addPasswordVO
     * 
     * @param index
     * @param vPasswordVO
     */
    public void addPasswordVO(int index, edit.common.vo.PasswordVO vPasswordVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPasswordVO.setParentVO(this.getClass(), this);
        _passwordVOList.insertElementAt(vPasswordVO, index);
    } //-- void addPasswordVO(int, edit.common.vo.PasswordVO) 

    /**
     * Method enumeratePasswordVO
     */
    public java.util.Enumeration enumeratePasswordVO()
    {
        return _passwordVOList.elements();
    } //-- java.util.Enumeration enumeratePasswordVO() 

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
        
        if (obj instanceof PasswordMaskVO) {
        
            PasswordMaskVO temp = (PasswordMaskVO)obj;
            if (this._passwordMaskPK != temp._passwordMaskPK)
                return false;
            if (this._has_passwordMaskPK != temp._has_passwordMaskPK)
                return false;
            if (this._expirationInDays != temp._expirationInDays)
                return false;
            if (this._has_expirationInDays != temp._has_expirationInDays)
                return false;
            if (this._restrictRepeats != null) {
                if (temp._restrictRepeats == null) return false;
                else if (!(this._restrictRepeats.equals(temp._restrictRepeats))) 
                    return false;
            }
            else if (temp._restrictRepeats != null)
                return false;
            if (this._numberOfRepeatCycles != temp._numberOfRepeatCycles)
                return false;
            if (this._has_numberOfRepeatCycles != temp._has_numberOfRepeatCycles)
                return false;
            if (this._maskFK != temp._maskFK)
                return false;
            if (this._has_maskFK != temp._has_maskFK)
                return false;
            if (this._passwordVOList != null) {
                if (temp._passwordVOList == null) return false;
                else if (!(this._passwordVOList.equals(temp._passwordVOList))) 
                    return false;
            }
            else if (temp._passwordVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getExpirationInDaysReturns the value of field
     * 'expirationInDays'.
     * 
     * @return the value of field 'expirationInDays'.
     */
    public int getExpirationInDays()
    {
        return this._expirationInDays;
    } //-- int getExpirationInDays() 

    /**
     * Method getMaskFKReturns the value of field 'maskFK'.
     * 
     * @return the value of field 'maskFK'.
     */
    public long getMaskFK()
    {
        return this._maskFK;
    } //-- long getMaskFK() 

    /**
     * Method getNumberOfRepeatCyclesReturns the value of field
     * 'numberOfRepeatCycles'.
     * 
     * @return the value of field 'numberOfRepeatCycles'.
     */
    public int getNumberOfRepeatCycles()
    {
        return this._numberOfRepeatCycles;
    } //-- int getNumberOfRepeatCycles() 

    /**
     * Method getPasswordMaskPKReturns the value of field
     * 'passwordMaskPK'.
     * 
     * @return the value of field 'passwordMaskPK'.
     */
    public long getPasswordMaskPK()
    {
        return this._passwordMaskPK;
    } //-- long getPasswordMaskPK() 

    /**
     * Method getPasswordVO
     * 
     * @param index
     */
    public edit.common.vo.PasswordVO getPasswordVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _passwordVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.PasswordVO) _passwordVOList.elementAt(index);
    } //-- edit.common.vo.PasswordVO getPasswordVO(int) 

    /**
     * Method getPasswordVO
     */
    public edit.common.vo.PasswordVO[] getPasswordVO()
    {
        int size = _passwordVOList.size();
        edit.common.vo.PasswordVO[] mArray = new edit.common.vo.PasswordVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.PasswordVO) _passwordVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.PasswordVO[] getPasswordVO() 

    /**
     * Method getPasswordVOCount
     */
    public int getPasswordVOCount()
    {
        return _passwordVOList.size();
    } //-- int getPasswordVOCount() 

    /**
     * Method getRestrictRepeatsReturns the value of field
     * 'restrictRepeats'.
     * 
     * @return the value of field 'restrictRepeats'.
     */
    public java.lang.String getRestrictRepeats()
    {
        return this._restrictRepeats;
    } //-- java.lang.String getRestrictRepeats() 

    /**
     * Method hasExpirationInDays
     */
    public boolean hasExpirationInDays()
    {
        return this._has_expirationInDays;
    } //-- boolean hasExpirationInDays() 

    /**
     * Method hasMaskFK
     */
    public boolean hasMaskFK()
    {
        return this._has_maskFK;
    } //-- boolean hasMaskFK() 

    /**
     * Method hasNumberOfRepeatCycles
     */
    public boolean hasNumberOfRepeatCycles()
    {
        return this._has_numberOfRepeatCycles;
    } //-- boolean hasNumberOfRepeatCycles() 

    /**
     * Method hasPasswordMaskPK
     */
    public boolean hasPasswordMaskPK()
    {
        return this._has_passwordMaskPK;
    } //-- boolean hasPasswordMaskPK() 

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
     * Method removeAllPasswordVO
     */
    public void removeAllPasswordVO()
    {
        _passwordVOList.removeAllElements();
    } //-- void removeAllPasswordVO() 

    /**
     * Method removePasswordVO
     * 
     * @param index
     */
    public edit.common.vo.PasswordVO removePasswordVO(int index)
    {
        java.lang.Object obj = _passwordVOList.elementAt(index);
        _passwordVOList.removeElementAt(index);
        return (edit.common.vo.PasswordVO) obj;
    } //-- edit.common.vo.PasswordVO removePasswordVO(int) 

    /**
     * Method setExpirationInDaysSets the value of field
     * 'expirationInDays'.
     * 
     * @param expirationInDays the value of field 'expirationInDays'
     */
    public void setExpirationInDays(int expirationInDays)
    {
        this._expirationInDays = expirationInDays;
        
        super.setVoChanged(true);
        this._has_expirationInDays = true;
    } //-- void setExpirationInDays(int) 

    /**
     * Method setMaskFKSets the value of field 'maskFK'.
     * 
     * @param maskFK the value of field 'maskFK'.
     */
    public void setMaskFK(long maskFK)
    {
        this._maskFK = maskFK;
        
        super.setVoChanged(true);
        this._has_maskFK = true;
    } //-- void setMaskFK(long) 

    /**
     * Method setNumberOfRepeatCyclesSets the value of field
     * 'numberOfRepeatCycles'.
     * 
     * @param numberOfRepeatCycles the value of field
     * 'numberOfRepeatCycles'.
     */
    public void setNumberOfRepeatCycles(int numberOfRepeatCycles)
    {
        this._numberOfRepeatCycles = numberOfRepeatCycles;
        
        super.setVoChanged(true);
        this._has_numberOfRepeatCycles = true;
    } //-- void setNumberOfRepeatCycles(int) 

    /**
     * Method setPasswordMaskPKSets the value of field
     * 'passwordMaskPK'.
     * 
     * @param passwordMaskPK the value of field 'passwordMaskPK'.
     */
    public void setPasswordMaskPK(long passwordMaskPK)
    {
        this._passwordMaskPK = passwordMaskPK;
        
        super.setVoChanged(true);
        this._has_passwordMaskPK = true;
    } //-- void setPasswordMaskPK(long) 

    /**
     * Method setPasswordVO
     * 
     * @param index
     * @param vPasswordVO
     */
    public void setPasswordVO(int index, edit.common.vo.PasswordVO vPasswordVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _passwordVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vPasswordVO.setParentVO(this.getClass(), this);
        _passwordVOList.setElementAt(vPasswordVO, index);
    } //-- void setPasswordVO(int, edit.common.vo.PasswordVO) 

    /**
     * Method setPasswordVO
     * 
     * @param passwordVOArray
     */
    public void setPasswordVO(edit.common.vo.PasswordVO[] passwordVOArray)
    {
        //-- copy array
        _passwordVOList.removeAllElements();
        for (int i = 0; i < passwordVOArray.length; i++) {
            passwordVOArray[i].setParentVO(this.getClass(), this);
            _passwordVOList.addElement(passwordVOArray[i]);
        }
    } //-- void setPasswordVO(edit.common.vo.PasswordVO) 

    /**
     * Method setRestrictRepeatsSets the value of field
     * 'restrictRepeats'.
     * 
     * @param restrictRepeats the value of field 'restrictRepeats'.
     */
    public void setRestrictRepeats(java.lang.String restrictRepeats)
    {
        this._restrictRepeats = restrictRepeats;
        
        super.setVoChanged(true);
    } //-- void setRestrictRepeats(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.PasswordMaskVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.PasswordMaskVO) Unmarshaller.unmarshal(edit.common.vo.PasswordMaskVO.class, reader);
    } //-- edit.common.vo.PasswordMaskVO unmarshal(java.io.Reader) 

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
