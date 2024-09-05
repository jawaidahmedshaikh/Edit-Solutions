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
 * Class SecurityProfileVO.
 * 
 * @version $Revision$ $Date$
 */
public class SecurityProfileVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _securityProfilePK
     */
    private long _securityProfilePK;

    /**
     * keeps track of state for field: _securityProfilePK
     */
    private boolean _has_securityProfilePK;

    /**
     * Field _sessionTimeoutInMinutes
     */
    private int _sessionTimeoutInMinutes;

    /**
     * keeps track of state for field: _sessionTimeoutInMinutes
     */
    private boolean _has_sessionTimeoutInMinutes;

    /**
     * Field _maxLoginAttempts
     */
    private int _maxLoginAttempts;

    /**
     * keeps track of state for field: _maxLoginAttempts
     */
    private boolean _has_maxLoginAttempts;

    /**
     * Field _maskVOList
     */
    private java.util.Vector _maskVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public SecurityProfileVO() {
        super();
        _maskVOList = new Vector();
    } //-- edit.common.vo.SecurityProfileVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addMaskVO
     * 
     * @param vMaskVO
     */
    public void addMaskVO(edit.common.vo.MaskVO vMaskVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vMaskVO.setParentVO(this.getClass(), this);
        _maskVOList.addElement(vMaskVO);
    } //-- void addMaskVO(edit.common.vo.MaskVO) 

    /**
     * Method addMaskVO
     * 
     * @param index
     * @param vMaskVO
     */
    public void addMaskVO(int index, edit.common.vo.MaskVO vMaskVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vMaskVO.setParentVO(this.getClass(), this);
        _maskVOList.insertElementAt(vMaskVO, index);
    } //-- void addMaskVO(int, edit.common.vo.MaskVO) 

    /**
     * Method enumerateMaskVO
     */
    public java.util.Enumeration enumerateMaskVO()
    {
        return _maskVOList.elements();
    } //-- java.util.Enumeration enumerateMaskVO() 

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
        
        if (obj instanceof SecurityProfileVO) {
        
            SecurityProfileVO temp = (SecurityProfileVO)obj;
            if (this._securityProfilePK != temp._securityProfilePK)
                return false;
            if (this._has_securityProfilePK != temp._has_securityProfilePK)
                return false;
            if (this._sessionTimeoutInMinutes != temp._sessionTimeoutInMinutes)
                return false;
            if (this._has_sessionTimeoutInMinutes != temp._has_sessionTimeoutInMinutes)
                return false;
            if (this._maxLoginAttempts != temp._maxLoginAttempts)
                return false;
            if (this._has_maxLoginAttempts != temp._has_maxLoginAttempts)
                return false;
            if (this._maskVOList != null) {
                if (temp._maskVOList == null) return false;
                else if (!(this._maskVOList.equals(temp._maskVOList))) 
                    return false;
            }
            else if (temp._maskVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getMaskVO
     * 
     * @param index
     */
    public edit.common.vo.MaskVO getMaskVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _maskVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.MaskVO) _maskVOList.elementAt(index);
    } //-- edit.common.vo.MaskVO getMaskVO(int) 

    /**
     * Method getMaskVO
     */
    public edit.common.vo.MaskVO[] getMaskVO()
    {
        int size = _maskVOList.size();
        edit.common.vo.MaskVO[] mArray = new edit.common.vo.MaskVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.MaskVO) _maskVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.MaskVO[] getMaskVO() 

    /**
     * Method getMaskVOCount
     */
    public int getMaskVOCount()
    {
        return _maskVOList.size();
    } //-- int getMaskVOCount() 

    /**
     * Method getMaxLoginAttemptsReturns the value of field
     * 'maxLoginAttempts'.
     * 
     * @return the value of field 'maxLoginAttempts'.
     */
    public int getMaxLoginAttempts()
    {
        return this._maxLoginAttempts;
    } //-- int getMaxLoginAttempts() 

    /**
     * Method getSecurityProfilePKReturns the value of field
     * 'securityProfilePK'.
     * 
     * @return the value of field 'securityProfilePK'.
     */
    public long getSecurityProfilePK()
    {
        return this._securityProfilePK;
    } //-- long getSecurityProfilePK() 

    /**
     * Method getSessionTimeoutInMinutesReturns the value of field
     * 'sessionTimeoutInMinutes'.
     * 
     * @return the value of field 'sessionTimeoutInMinutes'.
     */
    public int getSessionTimeoutInMinutes()
    {
        return this._sessionTimeoutInMinutes;
    } //-- int getSessionTimeoutInMinutes() 

    /**
     * Method hasMaxLoginAttempts
     */
    public boolean hasMaxLoginAttempts()
    {
        return this._has_maxLoginAttempts;
    } //-- boolean hasMaxLoginAttempts() 

    /**
     * Method hasSecurityProfilePK
     */
    public boolean hasSecurityProfilePK()
    {
        return this._has_securityProfilePK;
    } //-- boolean hasSecurityProfilePK() 

    /**
     * Method hasSessionTimeoutInMinutes
     */
    public boolean hasSessionTimeoutInMinutes()
    {
        return this._has_sessionTimeoutInMinutes;
    } //-- boolean hasSessionTimeoutInMinutes() 

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
     * Method removeAllMaskVO
     */
    public void removeAllMaskVO()
    {
        _maskVOList.removeAllElements();
    } //-- void removeAllMaskVO() 

    /**
     * Method removeMaskVO
     * 
     * @param index
     */
    public edit.common.vo.MaskVO removeMaskVO(int index)
    {
        java.lang.Object obj = _maskVOList.elementAt(index);
        _maskVOList.removeElementAt(index);
        return (edit.common.vo.MaskVO) obj;
    } //-- edit.common.vo.MaskVO removeMaskVO(int) 

    /**
     * Method setMaskVO
     * 
     * @param index
     * @param vMaskVO
     */
    public void setMaskVO(int index, edit.common.vo.MaskVO vMaskVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _maskVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vMaskVO.setParentVO(this.getClass(), this);
        _maskVOList.setElementAt(vMaskVO, index);
    } //-- void setMaskVO(int, edit.common.vo.MaskVO) 

    /**
     * Method setMaskVO
     * 
     * @param maskVOArray
     */
    public void setMaskVO(edit.common.vo.MaskVO[] maskVOArray)
    {
        //-- copy array
        _maskVOList.removeAllElements();
        for (int i = 0; i < maskVOArray.length; i++) {
            maskVOArray[i].setParentVO(this.getClass(), this);
            _maskVOList.addElement(maskVOArray[i]);
        }
    } //-- void setMaskVO(edit.common.vo.MaskVO) 

    /**
     * Method setMaxLoginAttemptsSets the value of field
     * 'maxLoginAttempts'.
     * 
     * @param maxLoginAttempts the value of field 'maxLoginAttempts'
     */
    public void setMaxLoginAttempts(int maxLoginAttempts)
    {
        this._maxLoginAttempts = maxLoginAttempts;
        
        super.setVoChanged(true);
        this._has_maxLoginAttempts = true;
    } //-- void setMaxLoginAttempts(int) 

    /**
     * Method setSecurityProfilePKSets the value of field
     * 'securityProfilePK'.
     * 
     * @param securityProfilePK the value of field
     * 'securityProfilePK'.
     */
    public void setSecurityProfilePK(long securityProfilePK)
    {
        this._securityProfilePK = securityProfilePK;
        
        super.setVoChanged(true);
        this._has_securityProfilePK = true;
    } //-- void setSecurityProfilePK(long) 

    /**
     * Method setSessionTimeoutInMinutesSets the value of field
     * 'sessionTimeoutInMinutes'.
     * 
     * @param sessionTimeoutInMinutes the value of field
     * 'sessionTimeoutInMinutes'.
     */
    public void setSessionTimeoutInMinutes(int sessionTimeoutInMinutes)
    {
        this._sessionTimeoutInMinutes = sessionTimeoutInMinutes;
        
        super.setVoChanged(true);
        this._has_sessionTimeoutInMinutes = true;
    } //-- void setSessionTimeoutInMinutes(int) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.SecurityProfileVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SecurityProfileVO) Unmarshaller.unmarshal(edit.common.vo.SecurityProfileVO.class, reader);
    } //-- edit.common.vo.SecurityProfileVO unmarshal(java.io.Reader) 

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
