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
 * Class MaskVO.
 * 
 * @version $Revision$ $Date$
 */
public class MaskVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _maskPK
     */
    private long _maskPK;

    /**
     * keeps track of state for field: _maskPK
     */
    private boolean _has_maskPK;

    /**
     * Field _maskTypeCT
     */
    private java.lang.String _maskTypeCT;

    /**
     * Field _minLength
     */
    private int _minLength;

    /**
     * keeps track of state for field: _minLength
     */
    private boolean _has_minLength;

    /**
     * Field _maxLength
     */
    private int _maxLength;

    /**
     * keeps track of state for field: _maxLength
     */
    private boolean _has_maxLength;

    /**
     * Field _mixedCaseCT
     */
    private java.lang.String _mixedCaseCT;

    /**
     * Field _alphaNumericCT
     */
    private java.lang.String _alphaNumericCT;

    /**
     * Field _specialCharsCT
     */
    private java.lang.String _specialCharsCT;

    /**
     * Field _securityProfileFK
     */
    private long _securityProfileFK;

    /**
     * keeps track of state for field: _securityProfileFK
     */
    private boolean _has_securityProfileFK;

    /**
     * Field _operatorVOList
     */
    private java.util.Vector _operatorVOList;

    /**
     * Field _passwordMaskVOList
     */
    private java.util.Vector _passwordMaskVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public MaskVO() {
        super();
        _operatorVOList = new Vector();
        _passwordMaskVOList = new Vector();
    } //-- edit.common.vo.MaskVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addOperatorVO
     * 
     * @param vOperatorVO
     */
    public void addOperatorVO(edit.common.vo.OperatorVO vOperatorVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vOperatorVO.setParentVO(this.getClass(), this);
        _operatorVOList.addElement(vOperatorVO);
    } //-- void addOperatorVO(edit.common.vo.OperatorVO) 

    /**
     * Method addOperatorVO
     * 
     * @param index
     * @param vOperatorVO
     */
    public void addOperatorVO(int index, edit.common.vo.OperatorVO vOperatorVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vOperatorVO.setParentVO(this.getClass(), this);
        _operatorVOList.insertElementAt(vOperatorVO, index);
    } //-- void addOperatorVO(int, edit.common.vo.OperatorVO) 

    /**
     * Method addPasswordMaskVO
     * 
     * @param vPasswordMaskVO
     */
    public void addPasswordMaskVO(edit.common.vo.PasswordMaskVO vPasswordMaskVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPasswordMaskVO.setParentVO(this.getClass(), this);
        _passwordMaskVOList.addElement(vPasswordMaskVO);
    } //-- void addPasswordMaskVO(edit.common.vo.PasswordMaskVO) 

    /**
     * Method addPasswordMaskVO
     * 
     * @param index
     * @param vPasswordMaskVO
     */
    public void addPasswordMaskVO(int index, edit.common.vo.PasswordMaskVO vPasswordMaskVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPasswordMaskVO.setParentVO(this.getClass(), this);
        _passwordMaskVOList.insertElementAt(vPasswordMaskVO, index);
    } //-- void addPasswordMaskVO(int, edit.common.vo.PasswordMaskVO) 

    /**
     * Method enumerateOperatorVO
     */
    public java.util.Enumeration enumerateOperatorVO()
    {
        return _operatorVOList.elements();
    } //-- java.util.Enumeration enumerateOperatorVO() 

    /**
     * Method enumeratePasswordMaskVO
     */
    public java.util.Enumeration enumeratePasswordMaskVO()
    {
        return _passwordMaskVOList.elements();
    } //-- java.util.Enumeration enumeratePasswordMaskVO() 

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
        
        if (obj instanceof MaskVO) {
        
            MaskVO temp = (MaskVO)obj;
            if (this._maskPK != temp._maskPK)
                return false;
            if (this._has_maskPK != temp._has_maskPK)
                return false;
            if (this._maskTypeCT != null) {
                if (temp._maskTypeCT == null) return false;
                else if (!(this._maskTypeCT.equals(temp._maskTypeCT))) 
                    return false;
            }
            else if (temp._maskTypeCT != null)
                return false;
            if (this._minLength != temp._minLength)
                return false;
            if (this._has_minLength != temp._has_minLength)
                return false;
            if (this._maxLength != temp._maxLength)
                return false;
            if (this._has_maxLength != temp._has_maxLength)
                return false;
            if (this._mixedCaseCT != null) {
                if (temp._mixedCaseCT == null) return false;
                else if (!(this._mixedCaseCT.equals(temp._mixedCaseCT))) 
                    return false;
            }
            else if (temp._mixedCaseCT != null)
                return false;
            if (this._alphaNumericCT != null) {
                if (temp._alphaNumericCT == null) return false;
                else if (!(this._alphaNumericCT.equals(temp._alphaNumericCT))) 
                    return false;
            }
            else if (temp._alphaNumericCT != null)
                return false;
            if (this._specialCharsCT != null) {
                if (temp._specialCharsCT == null) return false;
                else if (!(this._specialCharsCT.equals(temp._specialCharsCT))) 
                    return false;
            }
            else if (temp._specialCharsCT != null)
                return false;
            if (this._securityProfileFK != temp._securityProfileFK)
                return false;
            if (this._has_securityProfileFK != temp._has_securityProfileFK)
                return false;
            if (this._operatorVOList != null) {
                if (temp._operatorVOList == null) return false;
                else if (!(this._operatorVOList.equals(temp._operatorVOList))) 
                    return false;
            }
            else if (temp._operatorVOList != null)
                return false;
            if (this._passwordMaskVOList != null) {
                if (temp._passwordMaskVOList == null) return false;
                else if (!(this._passwordMaskVOList.equals(temp._passwordMaskVOList))) 
                    return false;
            }
            else if (temp._passwordMaskVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAlphaNumericCTReturns the value of field
     * 'alphaNumericCT'.
     * 
     * @return the value of field 'alphaNumericCT'.
     */
    public java.lang.String getAlphaNumericCT()
    {
        return this._alphaNumericCT;
    } //-- java.lang.String getAlphaNumericCT() 

    /**
     * Method getMaskPKReturns the value of field 'maskPK'.
     * 
     * @return the value of field 'maskPK'.
     */
    public long getMaskPK()
    {
        return this._maskPK;
    } //-- long getMaskPK() 

    /**
     * Method getMaskTypeCTReturns the value of field 'maskTypeCT'.
     * 
     * @return the value of field 'maskTypeCT'.
     */
    public java.lang.String getMaskTypeCT()
    {
        return this._maskTypeCT;
    } //-- java.lang.String getMaskTypeCT() 

    /**
     * Method getMaxLengthReturns the value of field 'maxLength'.
     * 
     * @return the value of field 'maxLength'.
     */
    public int getMaxLength()
    {
        return this._maxLength;
    } //-- int getMaxLength() 

    /**
     * Method getMinLengthReturns the value of field 'minLength'.
     * 
     * @return the value of field 'minLength'.
     */
    public int getMinLength()
    {
        return this._minLength;
    } //-- int getMinLength() 

    /**
     * Method getMixedCaseCTReturns the value of field
     * 'mixedCaseCT'.
     * 
     * @return the value of field 'mixedCaseCT'.
     */
    public java.lang.String getMixedCaseCT()
    {
        return this._mixedCaseCT;
    } //-- java.lang.String getMixedCaseCT() 

    /**
     * Method getOperatorVO
     * 
     * @param index
     */
    public edit.common.vo.OperatorVO getOperatorVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _operatorVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.OperatorVO) _operatorVOList.elementAt(index);
    } //-- edit.common.vo.OperatorVO getOperatorVO(int) 

    /**
     * Method getOperatorVO
     */
    public edit.common.vo.OperatorVO[] getOperatorVO()
    {
        int size = _operatorVOList.size();
        edit.common.vo.OperatorVO[] mArray = new edit.common.vo.OperatorVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.OperatorVO) _operatorVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.OperatorVO[] getOperatorVO() 

    /**
     * Method getOperatorVOCount
     */
    public int getOperatorVOCount()
    {
        return _operatorVOList.size();
    } //-- int getOperatorVOCount() 

    /**
     * Method getPasswordMaskVO
     * 
     * @param index
     */
    public edit.common.vo.PasswordMaskVO getPasswordMaskVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _passwordMaskVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.PasswordMaskVO) _passwordMaskVOList.elementAt(index);
    } //-- edit.common.vo.PasswordMaskVO getPasswordMaskVO(int) 

    /**
     * Method getPasswordMaskVO
     */
    public edit.common.vo.PasswordMaskVO[] getPasswordMaskVO()
    {
        int size = _passwordMaskVOList.size();
        edit.common.vo.PasswordMaskVO[] mArray = new edit.common.vo.PasswordMaskVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.PasswordMaskVO) _passwordMaskVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.PasswordMaskVO[] getPasswordMaskVO() 

    /**
     * Method getPasswordMaskVOCount
     */
    public int getPasswordMaskVOCount()
    {
        return _passwordMaskVOList.size();
    } //-- int getPasswordMaskVOCount() 

    /**
     * Method getSecurityProfileFKReturns the value of field
     * 'securityProfileFK'.
     * 
     * @return the value of field 'securityProfileFK'.
     */
    public long getSecurityProfileFK()
    {
        return this._securityProfileFK;
    } //-- long getSecurityProfileFK() 

    /**
     * Method getSpecialCharsCTReturns the value of field
     * 'specialCharsCT'.
     * 
     * @return the value of field 'specialCharsCT'.
     */
    public java.lang.String getSpecialCharsCT()
    {
        return this._specialCharsCT;
    } //-- java.lang.String getSpecialCharsCT() 

    /**
     * Method hasMaskPK
     */
    public boolean hasMaskPK()
    {
        return this._has_maskPK;
    } //-- boolean hasMaskPK() 

    /**
     * Method hasMaxLength
     */
    public boolean hasMaxLength()
    {
        return this._has_maxLength;
    } //-- boolean hasMaxLength() 

    /**
     * Method hasMinLength
     */
    public boolean hasMinLength()
    {
        return this._has_minLength;
    } //-- boolean hasMinLength() 

    /**
     * Method hasSecurityProfileFK
     */
    public boolean hasSecurityProfileFK()
    {
        return this._has_securityProfileFK;
    } //-- boolean hasSecurityProfileFK() 

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
     * Method removeAllOperatorVO
     */
    public void removeAllOperatorVO()
    {
        _operatorVOList.removeAllElements();
    } //-- void removeAllOperatorVO() 

    /**
     * Method removeAllPasswordMaskVO
     */
    public void removeAllPasswordMaskVO()
    {
        _passwordMaskVOList.removeAllElements();
    } //-- void removeAllPasswordMaskVO() 

    /**
     * Method removeOperatorVO
     * 
     * @param index
     */
    public edit.common.vo.OperatorVO removeOperatorVO(int index)
    {
        java.lang.Object obj = _operatorVOList.elementAt(index);
        _operatorVOList.removeElementAt(index);
        return (edit.common.vo.OperatorVO) obj;
    } //-- edit.common.vo.OperatorVO removeOperatorVO(int) 

    /**
     * Method removePasswordMaskVO
     * 
     * @param index
     */
    public edit.common.vo.PasswordMaskVO removePasswordMaskVO(int index)
    {
        java.lang.Object obj = _passwordMaskVOList.elementAt(index);
        _passwordMaskVOList.removeElementAt(index);
        return (edit.common.vo.PasswordMaskVO) obj;
    } //-- edit.common.vo.PasswordMaskVO removePasswordMaskVO(int) 

    /**
     * Method setAlphaNumericCTSets the value of field
     * 'alphaNumericCT'.
     * 
     * @param alphaNumericCT the value of field 'alphaNumericCT'.
     */
    public void setAlphaNumericCT(java.lang.String alphaNumericCT)
    {
        this._alphaNumericCT = alphaNumericCT;
        
        super.setVoChanged(true);
    } //-- void setAlphaNumericCT(java.lang.String) 

    /**
     * Method setMaskPKSets the value of field 'maskPK'.
     * 
     * @param maskPK the value of field 'maskPK'.
     */
    public void setMaskPK(long maskPK)
    {
        this._maskPK = maskPK;
        
        super.setVoChanged(true);
        this._has_maskPK = true;
    } //-- void setMaskPK(long) 

    /**
     * Method setMaskTypeCTSets the value of field 'maskTypeCT'.
     * 
     * @param maskTypeCT the value of field 'maskTypeCT'.
     */
    public void setMaskTypeCT(java.lang.String maskTypeCT)
    {
        this._maskTypeCT = maskTypeCT;
        
        super.setVoChanged(true);
    } //-- void setMaskTypeCT(java.lang.String) 

    /**
     * Method setMaxLengthSets the value of field 'maxLength'.
     * 
     * @param maxLength the value of field 'maxLength'.
     */
    public void setMaxLength(int maxLength)
    {
        this._maxLength = maxLength;
        
        super.setVoChanged(true);
        this._has_maxLength = true;
    } //-- void setMaxLength(int) 

    /**
     * Method setMinLengthSets the value of field 'minLength'.
     * 
     * @param minLength the value of field 'minLength'.
     */
    public void setMinLength(int minLength)
    {
        this._minLength = minLength;
        
        super.setVoChanged(true);
        this._has_minLength = true;
    } //-- void setMinLength(int) 

    /**
     * Method setMixedCaseCTSets the value of field 'mixedCaseCT'.
     * 
     * @param mixedCaseCT the value of field 'mixedCaseCT'.
     */
    public void setMixedCaseCT(java.lang.String mixedCaseCT)
    {
        this._mixedCaseCT = mixedCaseCT;
        
        super.setVoChanged(true);
    } //-- void setMixedCaseCT(java.lang.String) 

    /**
     * Method setOperatorVO
     * 
     * @param index
     * @param vOperatorVO
     */
    public void setOperatorVO(int index, edit.common.vo.OperatorVO vOperatorVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _operatorVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vOperatorVO.setParentVO(this.getClass(), this);
        _operatorVOList.setElementAt(vOperatorVO, index);
    } //-- void setOperatorVO(int, edit.common.vo.OperatorVO) 

    /**
     * Method setOperatorVO
     * 
     * @param operatorVOArray
     */
    public void setOperatorVO(edit.common.vo.OperatorVO[] operatorVOArray)
    {
        //-- copy array
        _operatorVOList.removeAllElements();
        for (int i = 0; i < operatorVOArray.length; i++) {
            operatorVOArray[i].setParentVO(this.getClass(), this);
            _operatorVOList.addElement(operatorVOArray[i]);
        }
    } //-- void setOperatorVO(edit.common.vo.OperatorVO) 

    /**
     * Method setPasswordMaskVO
     * 
     * @param index
     * @param vPasswordMaskVO
     */
    public void setPasswordMaskVO(int index, edit.common.vo.PasswordMaskVO vPasswordMaskVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _passwordMaskVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vPasswordMaskVO.setParentVO(this.getClass(), this);
        _passwordMaskVOList.setElementAt(vPasswordMaskVO, index);
    } //-- void setPasswordMaskVO(int, edit.common.vo.PasswordMaskVO) 

    /**
     * Method setPasswordMaskVO
     * 
     * @param passwordMaskVOArray
     */
    public void setPasswordMaskVO(edit.common.vo.PasswordMaskVO[] passwordMaskVOArray)
    {
        //-- copy array
        _passwordMaskVOList.removeAllElements();
        for (int i = 0; i < passwordMaskVOArray.length; i++) {
            passwordMaskVOArray[i].setParentVO(this.getClass(), this);
            _passwordMaskVOList.addElement(passwordMaskVOArray[i]);
        }
    } //-- void setPasswordMaskVO(edit.common.vo.PasswordMaskVO) 

    /**
     * Method setSecurityProfileFKSets the value of field
     * 'securityProfileFK'.
     * 
     * @param securityProfileFK the value of field
     * 'securityProfileFK'.
     */
    public void setSecurityProfileFK(long securityProfileFK)
    {
        this._securityProfileFK = securityProfileFK;
        
        super.setVoChanged(true);
        this._has_securityProfileFK = true;
    } //-- void setSecurityProfileFK(long) 

    /**
     * Method setSpecialCharsCTSets the value of field
     * 'specialCharsCT'.
     * 
     * @param specialCharsCT the value of field 'specialCharsCT'.
     */
    public void setSpecialCharsCT(java.lang.String specialCharsCT)
    {
        this._specialCharsCT = specialCharsCT;
        
        super.setVoChanged(true);
    } //-- void setSpecialCharsCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.MaskVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.MaskVO) Unmarshaller.unmarshal(edit.common.vo.MaskVO.class, reader);
    } //-- edit.common.vo.MaskVO unmarshal(java.io.Reader) 

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
