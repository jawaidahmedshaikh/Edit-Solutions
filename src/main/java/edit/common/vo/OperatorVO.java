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
 * Class OperatorVO.
 * 
 * @version $Revision$ $Date$
 */
public class OperatorVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _operatorPK
     */
    private long _operatorPK;

    /**
     * keeps track of state for field: _operatorPK
     */
    private boolean _has_operatorPK;

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * Field _lastName
     */
    private java.lang.String _lastName;

    /**
     * Field _firstName
     */
    private java.lang.String _firstName;

    /**
     * Field _middleInitial
     */
    private java.lang.String _middleInitial;

    /**
     * Field _title
     */
    private java.lang.String _title;

    /**
     * Field _dept
     */
    private java.lang.String _dept;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _maintOperator
     */
    private java.lang.String _maintOperator;

    /**
     * Field _maskFK
     */
    private long _maskFK;

    /**
     * keeps track of state for field: _maskFK
     */
    private boolean _has_maskFK;

    /**
     * Field _EMail
     */
    private java.lang.String _EMail;

    /**
     * Field _lockedIndicator
     */
    private java.lang.String _lockedIndicator;

    /**
     * Field _loggedInIndicator
     */
    private java.lang.String _loggedInIndicator;

    /**
     * Field _terminationDate
     */
    private java.lang.String _terminationDate;

    /**
     * Field _telephoneNumber
     */
    private java.lang.String _telephoneNumber;

    /**
     * Field _telephoneExtension
     */
    private java.lang.String _telephoneExtension;

    /**
     * Field _operatorTypeCT
     */
    private java.lang.String _operatorTypeCT;

    /**
     * Field _applyMax
     */
    private java.math.BigDecimal _applyMax;

    /**
     * Field _removeMax
     */
    private java.math.BigDecimal _removeMax;

    /**
     * Field _transferMax
     */
    private java.math.BigDecimal _transferMax;

    /**
     * Field _operatorRoleVOList
     */
    private java.util.Vector _operatorRoleVOList;

    /**
     * Field _passwordVOList
     */
    private java.util.Vector _passwordVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public OperatorVO() {
        super();
        _operatorRoleVOList = new Vector();
        _passwordVOList = new Vector();
    } //-- edit.common.vo.OperatorVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addOperatorRoleVO
     * 
     * @param vOperatorRoleVO
     */
    public void addOperatorRoleVO(edit.common.vo.OperatorRoleVO vOperatorRoleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vOperatorRoleVO.setParentVO(this.getClass(), this);
        _operatorRoleVOList.addElement(vOperatorRoleVO);
    } //-- void addOperatorRoleVO(edit.common.vo.OperatorRoleVO) 

    /**
     * Method addOperatorRoleVO
     * 
     * @param index
     * @param vOperatorRoleVO
     */
    public void addOperatorRoleVO(int index, edit.common.vo.OperatorRoleVO vOperatorRoleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vOperatorRoleVO.setParentVO(this.getClass(), this);
        _operatorRoleVOList.insertElementAt(vOperatorRoleVO, index);
    } //-- void addOperatorRoleVO(int, edit.common.vo.OperatorRoleVO) 

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
     * Method enumerateOperatorRoleVO
     */
    public java.util.Enumeration enumerateOperatorRoleVO()
    {
        return _operatorRoleVOList.elements();
    } //-- java.util.Enumeration enumerateOperatorRoleVO() 

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
        
        if (obj instanceof OperatorVO) {
        
            OperatorVO temp = (OperatorVO)obj;
            if (this._operatorPK != temp._operatorPK)
                return false;
            if (this._has_operatorPK != temp._has_operatorPK)
                return false;
            if (this._name != null) {
                if (temp._name == null) return false;
                else if (!(this._name.equals(temp._name))) 
                    return false;
            }
            else if (temp._name != null)
                return false;
            if (this._lastName != null) {
                if (temp._lastName == null) return false;
                else if (!(this._lastName.equals(temp._lastName))) 
                    return false;
            }
            else if (temp._lastName != null)
                return false;
            if (this._firstName != null) {
                if (temp._firstName == null) return false;
                else if (!(this._firstName.equals(temp._firstName))) 
                    return false;
            }
            else if (temp._firstName != null)
                return false;
            if (this._middleInitial != null) {
                if (temp._middleInitial == null) return false;
                else if (!(this._middleInitial.equals(temp._middleInitial))) 
                    return false;
            }
            else if (temp._middleInitial != null)
                return false;
            if (this._title != null) {
                if (temp._title == null) return false;
                else if (!(this._title.equals(temp._title))) 
                    return false;
            }
            else if (temp._title != null)
                return false;
            if (this._dept != null) {
                if (temp._dept == null) return false;
                else if (!(this._dept.equals(temp._dept))) 
                    return false;
            }
            else if (temp._dept != null)
                return false;
            if (this._maintDateTime != null) {
                if (temp._maintDateTime == null) return false;
                else if (!(this._maintDateTime.equals(temp._maintDateTime))) 
                    return false;
            }
            else if (temp._maintDateTime != null)
                return false;
            if (this._maintOperator != null) {
                if (temp._maintOperator == null) return false;
                else if (!(this._maintOperator.equals(temp._maintOperator))) 
                    return false;
            }
            else if (temp._maintOperator != null)
                return false;
            if (this._maskFK != temp._maskFK)
                return false;
            if (this._has_maskFK != temp._has_maskFK)
                return false;
            if (this._EMail != null) {
                if (temp._EMail == null) return false;
                else if (!(this._EMail.equals(temp._EMail))) 
                    return false;
            }
            else if (temp._EMail != null)
                return false;
            if (this._lockedIndicator != null) {
                if (temp._lockedIndicator == null) return false;
                else if (!(this._lockedIndicator.equals(temp._lockedIndicator))) 
                    return false;
            }
            else if (temp._lockedIndicator != null)
                return false;
            if (this._loggedInIndicator != null) {
                if (temp._loggedInIndicator == null) return false;
                else if (!(this._loggedInIndicator.equals(temp._loggedInIndicator))) 
                    return false;
            }
            else if (temp._loggedInIndicator != null)
                return false;
            if (this._terminationDate != null) {
                if (temp._terminationDate == null) return false;
                else if (!(this._terminationDate.equals(temp._terminationDate))) 
                    return false;
            }
            else if (temp._terminationDate != null)
                return false;
            if (this._telephoneNumber != null) {
                if (temp._telephoneNumber == null) return false;
                else if (!(this._telephoneNumber.equals(temp._telephoneNumber))) 
                    return false;
            }
            else if (temp._telephoneNumber != null)
                return false;
            if (this._telephoneExtension != null) {
                if (temp._telephoneExtension == null) return false;
                else if (!(this._telephoneExtension.equals(temp._telephoneExtension))) 
                    return false;
            }
            else if (temp._telephoneExtension != null)
                return false;
            if (this._operatorTypeCT != null) {
                if (temp._operatorTypeCT == null) return false;
                else if (!(this._operatorTypeCT.equals(temp._operatorTypeCT))) 
                    return false;
            }
            else if (temp._operatorTypeCT != null)
                return false;
            if (this._applyMax != null) {
                if (temp._applyMax == null) return false;
                else if (!(this._applyMax.equals(temp._applyMax))) 
                    return false;
            }
            else if (temp._applyMax != null)
                return false;
            if (this._removeMax != null) {
                if (temp._removeMax == null) return false;
                else if (!(this._removeMax.equals(temp._removeMax))) 
                    return false;
            }
            else if (temp._removeMax != null)
                return false;
            if (this._transferMax != null) {
                if (temp._transferMax == null) return false;
                else if (!(this._transferMax.equals(temp._transferMax))) 
                    return false;
            }
            else if (temp._transferMax != null)
                return false;
            if (this._operatorRoleVOList != null) {
                if (temp._operatorRoleVOList == null) return false;
                else if (!(this._operatorRoleVOList.equals(temp._operatorRoleVOList))) 
                    return false;
            }
            else if (temp._operatorRoleVOList != null)
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
     * Method getApplyMaxReturns the value of field 'applyMax'.
     * 
     * @return the value of field 'applyMax'.
     */
    public java.math.BigDecimal getApplyMax()
    {
        return this._applyMax;
    } //-- java.math.BigDecimal getApplyMax() 

    /**
     * Method getDeptReturns the value of field 'dept'.
     * 
     * @return the value of field 'dept'.
     */
    public java.lang.String getDept()
    {
        return this._dept;
    } //-- java.lang.String getDept() 

    /**
     * Method getEMailReturns the value of field 'EMail'.
     * 
     * @return the value of field 'EMail'.
     */
    public java.lang.String getEMail()
    {
        return this._EMail;
    } //-- java.lang.String getEMail() 

    /**
     * Method getFirstNameReturns the value of field 'firstName'.
     * 
     * @return the value of field 'firstName'.
     */
    public java.lang.String getFirstName()
    {
        return this._firstName;
    } //-- java.lang.String getFirstName() 

    /**
     * Method getLastNameReturns the value of field 'lastName'.
     * 
     * @return the value of field 'lastName'.
     */
    public java.lang.String getLastName()
    {
        return this._lastName;
    } //-- java.lang.String getLastName() 

    /**
     * Method getLockedIndicatorReturns the value of field
     * 'lockedIndicator'.
     * 
     * @return the value of field 'lockedIndicator'.
     */
    public java.lang.String getLockedIndicator()
    {
        return this._lockedIndicator;
    } //-- java.lang.String getLockedIndicator() 

    /**
     * Method getLoggedInIndicatorReturns the value of field
     * 'loggedInIndicator'.
     * 
     * @return the value of field 'loggedInIndicator'.
     */
    public java.lang.String getLoggedInIndicator()
    {
        return this._loggedInIndicator;
    } //-- java.lang.String getLoggedInIndicator() 

    /**
     * Method getMaintDateTimeReturns the value of field
     * 'maintDateTime'.
     * 
     * @return the value of field 'maintDateTime'.
     */
    public java.lang.String getMaintDateTime()
    {
        return this._maintDateTime;
    } //-- java.lang.String getMaintDateTime() 

    /**
     * Method getMaintOperatorReturns the value of field
     * 'maintOperator'.
     * 
     * @return the value of field 'maintOperator'.
     */
    public java.lang.String getMaintOperator()
    {
        return this._maintOperator;
    } //-- java.lang.String getMaintOperator() 

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
     * Method getMiddleInitialReturns the value of field
     * 'middleInitial'.
     * 
     * @return the value of field 'middleInitial'.
     */
    public java.lang.String getMiddleInitial()
    {
        return this._middleInitial;
    } //-- java.lang.String getMiddleInitial() 

    /**
     * Method getNameReturns the value of field 'name'.
     * 
     * @return the value of field 'name'.
     */
    public java.lang.String getName()
    {
        return this._name;
    } //-- java.lang.String getName() 

    /**
     * Method getOperatorPKReturns the value of field 'operatorPK'.
     * 
     * @return the value of field 'operatorPK'.
     */
    public long getOperatorPK()
    {
        return this._operatorPK;
    } //-- long getOperatorPK() 

    /**
     * Method getOperatorRoleVO
     * 
     * @param index
     */
    public edit.common.vo.OperatorRoleVO getOperatorRoleVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _operatorRoleVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.OperatorRoleVO) _operatorRoleVOList.elementAt(index);
    } //-- edit.common.vo.OperatorRoleVO getOperatorRoleVO(int) 

    /**
     * Method getOperatorRoleVO
     */
    public edit.common.vo.OperatorRoleVO[] getOperatorRoleVO()
    {
        int size = _operatorRoleVOList.size();
        edit.common.vo.OperatorRoleVO[] mArray = new edit.common.vo.OperatorRoleVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.OperatorRoleVO) _operatorRoleVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.OperatorRoleVO[] getOperatorRoleVO() 

    /**
     * Method getOperatorRoleVOCount
     */
    public int getOperatorRoleVOCount()
    {
        return _operatorRoleVOList.size();
    } //-- int getOperatorRoleVOCount() 

    /**
     * Method getOperatorTypeCTReturns the value of field
     * 'operatorTypeCT'.
     * 
     * @return the value of field 'operatorTypeCT'.
     */
    public java.lang.String getOperatorTypeCT()
    {
        return this._operatorTypeCT;
    } //-- java.lang.String getOperatorTypeCT() 

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
     * Method getRemoveMaxReturns the value of field 'removeMax'.
     * 
     * @return the value of field 'removeMax'.
     */
    public java.math.BigDecimal getRemoveMax()
    {
        return this._removeMax;
    } //-- java.math.BigDecimal getRemoveMax() 

    /**
     * Method getTelephoneExtensionReturns the value of field
     * 'telephoneExtension'.
     * 
     * @return the value of field 'telephoneExtension'.
     */
    public java.lang.String getTelephoneExtension()
    {
        return this._telephoneExtension;
    } //-- java.lang.String getTelephoneExtension() 

    /**
     * Method getTelephoneNumberReturns the value of field
     * 'telephoneNumber'.
     * 
     * @return the value of field 'telephoneNumber'.
     */
    public java.lang.String getTelephoneNumber()
    {
        return this._telephoneNumber;
    } //-- java.lang.String getTelephoneNumber() 

    /**
     * Method getTerminationDateReturns the value of field
     * 'terminationDate'.
     * 
     * @return the value of field 'terminationDate'.
     */
    public java.lang.String getTerminationDate()
    {
        return this._terminationDate;
    } //-- java.lang.String getTerminationDate() 

    /**
     * Method getTitleReturns the value of field 'title'.
     * 
     * @return the value of field 'title'.
     */
    public java.lang.String getTitle()
    {
        return this._title;
    } //-- java.lang.String getTitle() 

    /**
     * Method getTransferMaxReturns the value of field
     * 'transferMax'.
     * 
     * @return the value of field 'transferMax'.
     */
    public java.math.BigDecimal getTransferMax()
    {
        return this._transferMax;
    } //-- java.math.BigDecimal getTransferMax() 

    /**
     * Method hasMaskFK
     */
    public boolean hasMaskFK()
    {
        return this._has_maskFK;
    } //-- boolean hasMaskFK() 

    /**
     * Method hasOperatorPK
     */
    public boolean hasOperatorPK()
    {
        return this._has_operatorPK;
    } //-- boolean hasOperatorPK() 

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
     * Method removeAllOperatorRoleVO
     */
    public void removeAllOperatorRoleVO()
    {
        _operatorRoleVOList.removeAllElements();
    } //-- void removeAllOperatorRoleVO() 

    /**
     * Method removeAllPasswordVO
     */
    public void removeAllPasswordVO()
    {
        _passwordVOList.removeAllElements();
    } //-- void removeAllPasswordVO() 

    /**
     * Method removeOperatorRoleVO
     * 
     * @param index
     */
    public edit.common.vo.OperatorRoleVO removeOperatorRoleVO(int index)
    {
        java.lang.Object obj = _operatorRoleVOList.elementAt(index);
        _operatorRoleVOList.removeElementAt(index);
        return (edit.common.vo.OperatorRoleVO) obj;
    } //-- edit.common.vo.OperatorRoleVO removeOperatorRoleVO(int) 

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
     * Method setApplyMaxSets the value of field 'applyMax'.
     * 
     * @param applyMax the value of field 'applyMax'.
     */
    public void setApplyMax(java.math.BigDecimal applyMax)
    {
        this._applyMax = applyMax;
        
        super.setVoChanged(true);
    } //-- void setApplyMax(java.math.BigDecimal) 

    /**
     * Method setDeptSets the value of field 'dept'.
     * 
     * @param dept the value of field 'dept'.
     */
    public void setDept(java.lang.String dept)
    {
        this._dept = dept;
        
        super.setVoChanged(true);
    } //-- void setDept(java.lang.String) 

    /**
     * Method setEMailSets the value of field 'EMail'.
     * 
     * @param EMail the value of field 'EMail'.
     */
    public void setEMail(java.lang.String EMail)
    {
        this._EMail = EMail;
        
        super.setVoChanged(true);
    } //-- void setEMail(java.lang.String) 

    /**
     * Method setFirstNameSets the value of field 'firstName'.
     * 
     * @param firstName the value of field 'firstName'.
     */
    public void setFirstName(java.lang.String firstName)
    {
        this._firstName = firstName;
        
        super.setVoChanged(true);
    } //-- void setFirstName(java.lang.String) 

    /**
     * Method setLastNameSets the value of field 'lastName'.
     * 
     * @param lastName the value of field 'lastName'.
     */
    public void setLastName(java.lang.String lastName)
    {
        this._lastName = lastName;
        
        super.setVoChanged(true);
    } //-- void setLastName(java.lang.String) 

    /**
     * Method setLockedIndicatorSets the value of field
     * 'lockedIndicator'.
     * 
     * @param lockedIndicator the value of field 'lockedIndicator'.
     */
    public void setLockedIndicator(java.lang.String lockedIndicator)
    {
        this._lockedIndicator = lockedIndicator;
        
        super.setVoChanged(true);
    } //-- void setLockedIndicator(java.lang.String) 

    /**
     * Method setLoggedInIndicatorSets the value of field
     * 'loggedInIndicator'.
     * 
     * @param loggedInIndicator the value of field
     * 'loggedInIndicator'.
     */
    public void setLoggedInIndicator(java.lang.String loggedInIndicator)
    {
        this._loggedInIndicator = loggedInIndicator;
        
        super.setVoChanged(true);
    } //-- void setLoggedInIndicator(java.lang.String) 

    /**
     * Method setMaintDateTimeSets the value of field
     * 'maintDateTime'.
     * 
     * @param maintDateTime the value of field 'maintDateTime'.
     */
    public void setMaintDateTime(java.lang.String maintDateTime)
    {
        this._maintDateTime = maintDateTime;
        
        super.setVoChanged(true);
    } //-- void setMaintDateTime(java.lang.String) 

    /**
     * Method setMaintOperatorSets the value of field
     * 'maintOperator'.
     * 
     * @param maintOperator the value of field 'maintOperator'.
     */
    public void setMaintOperator(java.lang.String maintOperator)
    {
        this._maintOperator = maintOperator;
        
        super.setVoChanged(true);
    } //-- void setMaintOperator(java.lang.String) 

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
     * Method setMiddleInitialSets the value of field
     * 'middleInitial'.
     * 
     * @param middleInitial the value of field 'middleInitial'.
     */
    public void setMiddleInitial(java.lang.String middleInitial)
    {
        this._middleInitial = middleInitial;
        
        super.setVoChanged(true);
    } //-- void setMiddleInitial(java.lang.String) 

    /**
     * Method setNameSets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.String name)
    {
        this._name = name;
        
        super.setVoChanged(true);
    } //-- void setName(java.lang.String) 

    /**
     * Method setOperatorPKSets the value of field 'operatorPK'.
     * 
     * @param operatorPK the value of field 'operatorPK'.
     */
    public void setOperatorPK(long operatorPK)
    {
        this._operatorPK = operatorPK;
        
        super.setVoChanged(true);
        this._has_operatorPK = true;
    } //-- void setOperatorPK(long) 

    /**
     * Method setOperatorRoleVO
     * 
     * @param index
     * @param vOperatorRoleVO
     */
    public void setOperatorRoleVO(int index, edit.common.vo.OperatorRoleVO vOperatorRoleVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _operatorRoleVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vOperatorRoleVO.setParentVO(this.getClass(), this);
        _operatorRoleVOList.setElementAt(vOperatorRoleVO, index);
    } //-- void setOperatorRoleVO(int, edit.common.vo.OperatorRoleVO) 

    /**
     * Method setOperatorRoleVO
     * 
     * @param operatorRoleVOArray
     */
    public void setOperatorRoleVO(edit.common.vo.OperatorRoleVO[] operatorRoleVOArray)
    {
        //-- copy array
        _operatorRoleVOList.removeAllElements();
        for (int i = 0; i < operatorRoleVOArray.length; i++) {
            operatorRoleVOArray[i].setParentVO(this.getClass(), this);
            _operatorRoleVOList.addElement(operatorRoleVOArray[i]);
        }
    } //-- void setOperatorRoleVO(edit.common.vo.OperatorRoleVO) 

    /**
     * Method setOperatorTypeCTSets the value of field
     * 'operatorTypeCT'.
     * 
     * @param operatorTypeCT the value of field 'operatorTypeCT'.
     */
    public void setOperatorTypeCT(java.lang.String operatorTypeCT)
    {
        this._operatorTypeCT = operatorTypeCT;
        
        super.setVoChanged(true);
    } //-- void setOperatorTypeCT(java.lang.String) 

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
     * Method setRemoveMaxSets the value of field 'removeMax'.
     * 
     * @param removeMax the value of field 'removeMax'.
     */
    public void setRemoveMax(java.math.BigDecimal removeMax)
    {
        this._removeMax = removeMax;
        
        super.setVoChanged(true);
    } //-- void setRemoveMax(java.math.BigDecimal) 

    /**
     * Method setTelephoneExtensionSets the value of field
     * 'telephoneExtension'.
     * 
     * @param telephoneExtension the value of field
     * 'telephoneExtension'.
     */
    public void setTelephoneExtension(java.lang.String telephoneExtension)
    {
        this._telephoneExtension = telephoneExtension;
        
        super.setVoChanged(true);
    } //-- void setTelephoneExtension(java.lang.String) 

    /**
     * Method setTelephoneNumberSets the value of field
     * 'telephoneNumber'.
     * 
     * @param telephoneNumber the value of field 'telephoneNumber'.
     */
    public void setTelephoneNumber(java.lang.String telephoneNumber)
    {
        this._telephoneNumber = telephoneNumber;
        
        super.setVoChanged(true);
    } //-- void setTelephoneNumber(java.lang.String) 

    /**
     * Method setTerminationDateSets the value of field
     * 'terminationDate'.
     * 
     * @param terminationDate the value of field 'terminationDate'.
     */
    public void setTerminationDate(java.lang.String terminationDate)
    {
        this._terminationDate = terminationDate;
        
        super.setVoChanged(true);
    } //-- void setTerminationDate(java.lang.String) 

    /**
     * Method setTitleSets the value of field 'title'.
     * 
     * @param title the value of field 'title'.
     */
    public void setTitle(java.lang.String title)
    {
        this._title = title;
        
        super.setVoChanged(true);
    } //-- void setTitle(java.lang.String) 

    /**
     * Method setTransferMaxSets the value of field 'transferMax'.
     * 
     * @param transferMax the value of field 'transferMax'.
     */
    public void setTransferMax(java.math.BigDecimal transferMax)
    {
        this._transferMax = transferMax;
        
        super.setVoChanged(true);
    } //-- void setTransferMax(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.OperatorVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.OperatorVO) Unmarshaller.unmarshal(edit.common.vo.OperatorVO.class, reader);
    } //-- edit.common.vo.OperatorVO unmarshal(java.io.Reader) 

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
