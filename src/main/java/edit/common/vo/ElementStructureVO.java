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
 * Class ElementStructureVO.
 * 
 * @version $Revision$ $Date$
 */
public class ElementStructureVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _elementStructurePK
     */
    private long _elementStructurePK;

    /**
     * keeps track of state for field: _elementStructurePK
     */
    private boolean _has_elementStructurePK;

    /**
     * Field _elementFK
     */
    private long _elementFK;

    /**
     * keeps track of state for field: _elementFK
     */
    private boolean _has_elementFK;

    /**
     * Field _memoCode
     */
    private java.lang.String _memoCode;

    /**
     * Field _certainPeriod
     */
    private int _certainPeriod;

    /**
     * keeps track of state for field: _certainPeriod
     */
    private boolean _has_certainPeriod;

    /**
     * Field _qualNonQualCT
     */
    private java.lang.String _qualNonQualCT;

    /**
     * Field _fundFK
     */
    private long _fundFK;

    /**
     * keeps track of state for field: _fundFK
     */
    private boolean _has_fundFK;

    /**
     * Field _switchEffectInd
     */
    private java.lang.String _switchEffectInd;

    /**
     * Field _suppressAccountingInd
     */
    private java.lang.String _suppressAccountingInd;

    /**
     * Field _chargeCodeFK
     */
    private long _chargeCodeFK;

    /**
     * keeps track of state for field: _chargeCodeFK
     */
    private boolean _has_chargeCodeFK;

    /**
     * Field _accountVOList
     */
    private java.util.Vector _accountVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ElementStructureVO() {
        super();
        _accountVOList = new Vector();
    } //-- edit.common.vo.ElementStructureVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAccountVO
     * 
     * @param vAccountVO
     */
    public void addAccountVO(edit.common.vo.AccountVO vAccountVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAccountVO.setParentVO(this.getClass(), this);
        _accountVOList.addElement(vAccountVO);
    } //-- void addAccountVO(edit.common.vo.AccountVO) 

    /**
     * Method addAccountVO
     * 
     * @param index
     * @param vAccountVO
     */
    public void addAccountVO(int index, edit.common.vo.AccountVO vAccountVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAccountVO.setParentVO(this.getClass(), this);
        _accountVOList.insertElementAt(vAccountVO, index);
    } //-- void addAccountVO(int, edit.common.vo.AccountVO) 

    /**
     * Method enumerateAccountVO
     */
    public java.util.Enumeration enumerateAccountVO()
    {
        return _accountVOList.elements();
    } //-- java.util.Enumeration enumerateAccountVO() 

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
        
        if (obj instanceof ElementStructureVO) {
        
            ElementStructureVO temp = (ElementStructureVO)obj;
            if (this._elementStructurePK != temp._elementStructurePK)
                return false;
            if (this._has_elementStructurePK != temp._has_elementStructurePK)
                return false;
            if (this._elementFK != temp._elementFK)
                return false;
            if (this._has_elementFK != temp._has_elementFK)
                return false;
            if (this._memoCode != null) {
                if (temp._memoCode == null) return false;
                else if (!(this._memoCode.equals(temp._memoCode))) 
                    return false;
            }
            else if (temp._memoCode != null)
                return false;
            if (this._certainPeriod != temp._certainPeriod)
                return false;
            if (this._has_certainPeriod != temp._has_certainPeriod)
                return false;
            if (this._qualNonQualCT != null) {
                if (temp._qualNonQualCT == null) return false;
                else if (!(this._qualNonQualCT.equals(temp._qualNonQualCT))) 
                    return false;
            }
            else if (temp._qualNonQualCT != null)
                return false;
            if (this._fundFK != temp._fundFK)
                return false;
            if (this._has_fundFK != temp._has_fundFK)
                return false;
            if (this._switchEffectInd != null) {
                if (temp._switchEffectInd == null) return false;
                else if (!(this._switchEffectInd.equals(temp._switchEffectInd))) 
                    return false;
            }
            else if (temp._switchEffectInd != null)
                return false;
            if (this._suppressAccountingInd != null) {
                if (temp._suppressAccountingInd == null) return false;
                else if (!(this._suppressAccountingInd.equals(temp._suppressAccountingInd))) 
                    return false;
            }
            else if (temp._suppressAccountingInd != null)
                return false;
            if (this._chargeCodeFK != temp._chargeCodeFK)
                return false;
            if (this._has_chargeCodeFK != temp._has_chargeCodeFK)
                return false;
            if (this._accountVOList != null) {
                if (temp._accountVOList == null) return false;
                else if (!(this._accountVOList.equals(temp._accountVOList))) 
                    return false;
            }
            else if (temp._accountVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccountVO
     * 
     * @param index
     */
    public edit.common.vo.AccountVO getAccountVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _accountVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AccountVO) _accountVOList.elementAt(index);
    } //-- edit.common.vo.AccountVO getAccountVO(int) 

    /**
     * Method getAccountVO
     */
    public edit.common.vo.AccountVO[] getAccountVO()
    {
        int size = _accountVOList.size();
        edit.common.vo.AccountVO[] mArray = new edit.common.vo.AccountVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AccountVO) _accountVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AccountVO[] getAccountVO() 

    /**
     * Method getAccountVOCount
     */
    public int getAccountVOCount()
    {
        return _accountVOList.size();
    } //-- int getAccountVOCount() 

    /**
     * Method getCertainPeriodReturns the value of field
     * 'certainPeriod'.
     * 
     * @return the value of field 'certainPeriod'.
     */
    public int getCertainPeriod()
    {
        return this._certainPeriod;
    } //-- int getCertainPeriod() 

    /**
     * Method getChargeCodeFKReturns the value of field
     * 'chargeCodeFK'.
     * 
     * @return the value of field 'chargeCodeFK'.
     */
    public long getChargeCodeFK()
    {
        return this._chargeCodeFK;
    } //-- long getChargeCodeFK() 

    /**
     * Method getElementFKReturns the value of field 'elementFK'.
     * 
     * @return the value of field 'elementFK'.
     */
    public long getElementFK()
    {
        return this._elementFK;
    } //-- long getElementFK() 

    /**
     * Method getElementStructurePKReturns the value of field
     * 'elementStructurePK'.
     * 
     * @return the value of field 'elementStructurePK'.
     */
    public long getElementStructurePK()
    {
        return this._elementStructurePK;
    } //-- long getElementStructurePK() 

    /**
     * Method getFundFKReturns the value of field 'fundFK'.
     * 
     * @return the value of field 'fundFK'.
     */
    public long getFundFK()
    {
        return this._fundFK;
    } //-- long getFundFK() 

    /**
     * Method getMemoCodeReturns the value of field 'memoCode'.
     * 
     * @return the value of field 'memoCode'.
     */
    public java.lang.String getMemoCode()
    {
        return this._memoCode;
    } //-- java.lang.String getMemoCode() 

    /**
     * Method getQualNonQualCTReturns the value of field
     * 'qualNonQualCT'.
     * 
     * @return the value of field 'qualNonQualCT'.
     */
    public java.lang.String getQualNonQualCT()
    {
        return this._qualNonQualCT;
    } //-- java.lang.String getQualNonQualCT() 

    /**
     * Method getSuppressAccountingIndReturns the value of field
     * 'suppressAccountingInd'.
     * 
     * @return the value of field 'suppressAccountingInd'.
     */
    public java.lang.String getSuppressAccountingInd()
    {
        return this._suppressAccountingInd;
    } //-- java.lang.String getSuppressAccountingInd() 

    /**
     * Method getSwitchEffectIndReturns the value of field
     * 'switchEffectInd'.
     * 
     * @return the value of field 'switchEffectInd'.
     */
    public java.lang.String getSwitchEffectInd()
    {
        return this._switchEffectInd;
    } //-- java.lang.String getSwitchEffectInd() 

    /**
     * Method hasCertainPeriod
     */
    public boolean hasCertainPeriod()
    {
        return this._has_certainPeriod;
    } //-- boolean hasCertainPeriod() 

    /**
     * Method hasChargeCodeFK
     */
    public boolean hasChargeCodeFK()
    {
        return this._has_chargeCodeFK;
    } //-- boolean hasChargeCodeFK() 

    /**
     * Method hasElementFK
     */
    public boolean hasElementFK()
    {
        return this._has_elementFK;
    } //-- boolean hasElementFK() 

    /**
     * Method hasElementStructurePK
     */
    public boolean hasElementStructurePK()
    {
        return this._has_elementStructurePK;
    } //-- boolean hasElementStructurePK() 

    /**
     * Method hasFundFK
     */
    public boolean hasFundFK()
    {
        return this._has_fundFK;
    } //-- boolean hasFundFK() 

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
     * Method removeAccountVO
     * 
     * @param index
     */
    public edit.common.vo.AccountVO removeAccountVO(int index)
    {
        java.lang.Object obj = _accountVOList.elementAt(index);
        _accountVOList.removeElementAt(index);
        return (edit.common.vo.AccountVO) obj;
    } //-- edit.common.vo.AccountVO removeAccountVO(int) 

    /**
     * Method removeAllAccountVO
     */
    public void removeAllAccountVO()
    {
        _accountVOList.removeAllElements();
    } //-- void removeAllAccountVO() 

    /**
     * Method setAccountVO
     * 
     * @param index
     * @param vAccountVO
     */
    public void setAccountVO(int index, edit.common.vo.AccountVO vAccountVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _accountVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAccountVO.setParentVO(this.getClass(), this);
        _accountVOList.setElementAt(vAccountVO, index);
    } //-- void setAccountVO(int, edit.common.vo.AccountVO) 

    /**
     * Method setAccountVO
     * 
     * @param accountVOArray
     */
    public void setAccountVO(edit.common.vo.AccountVO[] accountVOArray)
    {
        //-- copy array
        _accountVOList.removeAllElements();
        for (int i = 0; i < accountVOArray.length; i++) {
            accountVOArray[i].setParentVO(this.getClass(), this);
            _accountVOList.addElement(accountVOArray[i]);
        }
    } //-- void setAccountVO(edit.common.vo.AccountVO) 

    /**
     * Method setCertainPeriodSets the value of field
     * 'certainPeriod'.
     * 
     * @param certainPeriod the value of field 'certainPeriod'.
     */
    public void setCertainPeriod(int certainPeriod)
    {
        this._certainPeriod = certainPeriod;
        
        super.setVoChanged(true);
        this._has_certainPeriod = true;
    } //-- void setCertainPeriod(int) 

    /**
     * Method setChargeCodeFKSets the value of field
     * 'chargeCodeFK'.
     * 
     * @param chargeCodeFK the value of field 'chargeCodeFK'.
     */
    public void setChargeCodeFK(long chargeCodeFK)
    {
        this._chargeCodeFK = chargeCodeFK;
        
        super.setVoChanged(true);
        this._has_chargeCodeFK = true;
    } //-- void setChargeCodeFK(long) 

    /**
     * Method setElementFKSets the value of field 'elementFK'.
     * 
     * @param elementFK the value of field 'elementFK'.
     */
    public void setElementFK(long elementFK)
    {
        this._elementFK = elementFK;
        
        super.setVoChanged(true);
        this._has_elementFK = true;
    } //-- void setElementFK(long) 

    /**
     * Method setElementStructurePKSets the value of field
     * 'elementStructurePK'.
     * 
     * @param elementStructurePK the value of field
     * 'elementStructurePK'.
     */
    public void setElementStructurePK(long elementStructurePK)
    {
        this._elementStructurePK = elementStructurePK;
        
        super.setVoChanged(true);
        this._has_elementStructurePK = true;
    } //-- void setElementStructurePK(long) 

    /**
     * Method setFundFKSets the value of field 'fundFK'.
     * 
     * @param fundFK the value of field 'fundFK'.
     */
    public void setFundFK(long fundFK)
    {
        this._fundFK = fundFK;
        
        super.setVoChanged(true);
        this._has_fundFK = true;
    } //-- void setFundFK(long) 

    /**
     * Method setMemoCodeSets the value of field 'memoCode'.
     * 
     * @param memoCode the value of field 'memoCode'.
     */
    public void setMemoCode(java.lang.String memoCode)
    {
        this._memoCode = memoCode;
        
        super.setVoChanged(true);
    } //-- void setMemoCode(java.lang.String) 

    /**
     * Method setQualNonQualCTSets the value of field
     * 'qualNonQualCT'.
     * 
     * @param qualNonQualCT the value of field 'qualNonQualCT'.
     */
    public void setQualNonQualCT(java.lang.String qualNonQualCT)
    {
        this._qualNonQualCT = qualNonQualCT;
        
        super.setVoChanged(true);
    } //-- void setQualNonQualCT(java.lang.String) 

    /**
     * Method setSuppressAccountingIndSets the value of field
     * 'suppressAccountingInd'.
     * 
     * @param suppressAccountingInd the value of field
     * 'suppressAccountingInd'.
     */
    public void setSuppressAccountingInd(java.lang.String suppressAccountingInd)
    {
        this._suppressAccountingInd = suppressAccountingInd;
        
        super.setVoChanged(true);
    } //-- void setSuppressAccountingInd(java.lang.String) 

    /**
     * Method setSwitchEffectIndSets the value of field
     * 'switchEffectInd'.
     * 
     * @param switchEffectInd the value of field 'switchEffectInd'.
     */
    public void setSwitchEffectInd(java.lang.String switchEffectInd)
    {
        this._switchEffectInd = switchEffectInd;
        
        super.setVoChanged(true);
    } //-- void setSwitchEffectInd(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ElementStructureVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ElementStructureVO) Unmarshaller.unmarshal(edit.common.vo.ElementStructureVO.class, reader);
    } //-- edit.common.vo.ElementStructureVO unmarshal(java.io.Reader) 

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
