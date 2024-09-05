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
 * Class ChargeCodeVO.
 * 
 * @version $Revision$ $Date$
 */
public class ChargeCodeVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _chargeCodePK
     */
    private long _chargeCodePK;

    /**
     * keeps track of state for field: _chargeCodePK
     */
    private boolean _has_chargeCodePK;

    /**
     * Field _filteredFundFK
     */
    private long _filteredFundFK;

    /**
     * keeps track of state for field: _filteredFundFK
     */
    private boolean _has_filteredFundFK;

    /**
     * Field _chargeCode
     */
    private java.lang.String _chargeCode;

    /**
     * Field _clientFundNumber
     */
    private java.lang.String _clientFundNumber;

    /**
     * Field _newIssuesIndicatorCT
     */
    private java.lang.String _newIssuesIndicatorCT;

    /**
     * Field _accumulatedPremium
     */
    private java.math.BigDecimal _accumulatedPremium;

    /**
     * Field _unitValuesVOList
     */
    private java.util.Vector _unitValuesVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ChargeCodeVO() {
        super();
        _unitValuesVOList = new Vector();
    } //-- edit.common.vo.ChargeCodeVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addUnitValuesVO
     * 
     * @param vUnitValuesVO
     */
    public void addUnitValuesVO(edit.common.vo.UnitValuesVO vUnitValuesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vUnitValuesVO.setParentVO(this.getClass(), this);
        _unitValuesVOList.addElement(vUnitValuesVO);
    } //-- void addUnitValuesVO(edit.common.vo.UnitValuesVO) 

    /**
     * Method addUnitValuesVO
     * 
     * @param index
     * @param vUnitValuesVO
     */
    public void addUnitValuesVO(int index, edit.common.vo.UnitValuesVO vUnitValuesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vUnitValuesVO.setParentVO(this.getClass(), this);
        _unitValuesVOList.insertElementAt(vUnitValuesVO, index);
    } //-- void addUnitValuesVO(int, edit.common.vo.UnitValuesVO) 

    /**
     * Method enumerateUnitValuesVO
     */
    public java.util.Enumeration enumerateUnitValuesVO()
    {
        return _unitValuesVOList.elements();
    } //-- java.util.Enumeration enumerateUnitValuesVO() 

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
        
        if (obj instanceof ChargeCodeVO) {
        
            ChargeCodeVO temp = (ChargeCodeVO)obj;
            if (this._chargeCodePK != temp._chargeCodePK)
                return false;
            if (this._has_chargeCodePK != temp._has_chargeCodePK)
                return false;
            if (this._filteredFundFK != temp._filteredFundFK)
                return false;
            if (this._has_filteredFundFK != temp._has_filteredFundFK)
                return false;
            if (this._chargeCode != null) {
                if (temp._chargeCode == null) return false;
                else if (!(this._chargeCode.equals(temp._chargeCode))) 
                    return false;
            }
            else if (temp._chargeCode != null)
                return false;
            if (this._clientFundNumber != null) {
                if (temp._clientFundNumber == null) return false;
                else if (!(this._clientFundNumber.equals(temp._clientFundNumber))) 
                    return false;
            }
            else if (temp._clientFundNumber != null)
                return false;
            if (this._newIssuesIndicatorCT != null) {
                if (temp._newIssuesIndicatorCT == null) return false;
                else if (!(this._newIssuesIndicatorCT.equals(temp._newIssuesIndicatorCT))) 
                    return false;
            }
            else if (temp._newIssuesIndicatorCT != null)
                return false;
            if (this._accumulatedPremium != null) {
                if (temp._accumulatedPremium == null) return false;
                else if (!(this._accumulatedPremium.equals(temp._accumulatedPremium))) 
                    return false;
            }
            else if (temp._accumulatedPremium != null)
                return false;
            if (this._unitValuesVOList != null) {
                if (temp._unitValuesVOList == null) return false;
                else if (!(this._unitValuesVOList.equals(temp._unitValuesVOList))) 
                    return false;
            }
            else if (temp._unitValuesVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccumulatedPremiumReturns the value of field
     * 'accumulatedPremium'.
     * 
     * @return the value of field 'accumulatedPremium'.
     */
    public java.math.BigDecimal getAccumulatedPremium()
    {
        return this._accumulatedPremium;
    } //-- java.math.BigDecimal getAccumulatedPremium() 

    /**
     * Method getChargeCodeReturns the value of field 'chargeCode'.
     * 
     * @return the value of field 'chargeCode'.
     */
    public java.lang.String getChargeCode()
    {
        return this._chargeCode;
    } //-- java.lang.String getChargeCode() 

    /**
     * Method getChargeCodePKReturns the value of field
     * 'chargeCodePK'.
     * 
     * @return the value of field 'chargeCodePK'.
     */
    public long getChargeCodePK()
    {
        return this._chargeCodePK;
    } //-- long getChargeCodePK() 

    /**
     * Method getClientFundNumberReturns the value of field
     * 'clientFundNumber'.
     * 
     * @return the value of field 'clientFundNumber'.
     */
    public java.lang.String getClientFundNumber()
    {
        return this._clientFundNumber;
    } //-- java.lang.String getClientFundNumber() 

    /**
     * Method getFilteredFundFKReturns the value of field
     * 'filteredFundFK'.
     * 
     * @return the value of field 'filteredFundFK'.
     */
    public long getFilteredFundFK()
    {
        return this._filteredFundFK;
    } //-- long getFilteredFundFK() 

    /**
     * Method getNewIssuesIndicatorCTReturns the value of field
     * 'newIssuesIndicatorCT'.
     * 
     * @return the value of field 'newIssuesIndicatorCT'.
     */
    public java.lang.String getNewIssuesIndicatorCT()
    {
        return this._newIssuesIndicatorCT;
    } //-- java.lang.String getNewIssuesIndicatorCT() 

    /**
     * Method getUnitValuesVO
     * 
     * @param index
     */
    public edit.common.vo.UnitValuesVO getUnitValuesVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _unitValuesVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.UnitValuesVO) _unitValuesVOList.elementAt(index);
    } //-- edit.common.vo.UnitValuesVO getUnitValuesVO(int) 

    /**
     * Method getUnitValuesVO
     */
    public edit.common.vo.UnitValuesVO[] getUnitValuesVO()
    {
        int size = _unitValuesVOList.size();
        edit.common.vo.UnitValuesVO[] mArray = new edit.common.vo.UnitValuesVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.UnitValuesVO) _unitValuesVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.UnitValuesVO[] getUnitValuesVO() 

    /**
     * Method getUnitValuesVOCount
     */
    public int getUnitValuesVOCount()
    {
        return _unitValuesVOList.size();
    } //-- int getUnitValuesVOCount() 

    /**
     * Method hasChargeCodePK
     */
    public boolean hasChargeCodePK()
    {
        return this._has_chargeCodePK;
    } //-- boolean hasChargeCodePK() 

    /**
     * Method hasFilteredFundFK
     */
    public boolean hasFilteredFundFK()
    {
        return this._has_filteredFundFK;
    } //-- boolean hasFilteredFundFK() 

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
     * Method removeAllUnitValuesVO
     */
    public void removeAllUnitValuesVO()
    {
        _unitValuesVOList.removeAllElements();
    } //-- void removeAllUnitValuesVO() 

    /**
     * Method removeUnitValuesVO
     * 
     * @param index
     */
    public edit.common.vo.UnitValuesVO removeUnitValuesVO(int index)
    {
        java.lang.Object obj = _unitValuesVOList.elementAt(index);
        _unitValuesVOList.removeElementAt(index);
        return (edit.common.vo.UnitValuesVO) obj;
    } //-- edit.common.vo.UnitValuesVO removeUnitValuesVO(int) 

    /**
     * Method setAccumulatedPremiumSets the value of field
     * 'accumulatedPremium'.
     * 
     * @param accumulatedPremium the value of field
     * 'accumulatedPremium'.
     */
    public void setAccumulatedPremium(java.math.BigDecimal accumulatedPremium)
    {
        this._accumulatedPremium = accumulatedPremium;
        
        super.setVoChanged(true);
    } //-- void setAccumulatedPremium(java.math.BigDecimal) 

    /**
     * Method setChargeCodeSets the value of field 'chargeCode'.
     * 
     * @param chargeCode the value of field 'chargeCode'.
     */
    public void setChargeCode(java.lang.String chargeCode)
    {
        this._chargeCode = chargeCode;
        
        super.setVoChanged(true);
    } //-- void setChargeCode(java.lang.String) 

    /**
     * Method setChargeCodePKSets the value of field
     * 'chargeCodePK'.
     * 
     * @param chargeCodePK the value of field 'chargeCodePK'.
     */
    public void setChargeCodePK(long chargeCodePK)
    {
        this._chargeCodePK = chargeCodePK;
        
        super.setVoChanged(true);
        this._has_chargeCodePK = true;
    } //-- void setChargeCodePK(long) 

    /**
     * Method setClientFundNumberSets the value of field
     * 'clientFundNumber'.
     * 
     * @param clientFundNumber the value of field 'clientFundNumber'
     */
    public void setClientFundNumber(java.lang.String clientFundNumber)
    {
        this._clientFundNumber = clientFundNumber;
        
        super.setVoChanged(true);
    } //-- void setClientFundNumber(java.lang.String) 

    /**
     * Method setFilteredFundFKSets the value of field
     * 'filteredFundFK'.
     * 
     * @param filteredFundFK the value of field 'filteredFundFK'.
     */
    public void setFilteredFundFK(long filteredFundFK)
    {
        this._filteredFundFK = filteredFundFK;
        
        super.setVoChanged(true);
        this._has_filteredFundFK = true;
    } //-- void setFilteredFundFK(long) 

    /**
     * Method setNewIssuesIndicatorCTSets the value of field
     * 'newIssuesIndicatorCT'.
     * 
     * @param newIssuesIndicatorCT the value of field
     * 'newIssuesIndicatorCT'.
     */
    public void setNewIssuesIndicatorCT(java.lang.String newIssuesIndicatorCT)
    {
        this._newIssuesIndicatorCT = newIssuesIndicatorCT;
        
        super.setVoChanged(true);
    } //-- void setNewIssuesIndicatorCT(java.lang.String) 

    /**
     * Method setUnitValuesVO
     * 
     * @param index
     * @param vUnitValuesVO
     */
    public void setUnitValuesVO(int index, edit.common.vo.UnitValuesVO vUnitValuesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _unitValuesVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vUnitValuesVO.setParentVO(this.getClass(), this);
        _unitValuesVOList.setElementAt(vUnitValuesVO, index);
    } //-- void setUnitValuesVO(int, edit.common.vo.UnitValuesVO) 

    /**
     * Method setUnitValuesVO
     * 
     * @param unitValuesVOArray
     */
    public void setUnitValuesVO(edit.common.vo.UnitValuesVO[] unitValuesVOArray)
    {
        //-- copy array
        _unitValuesVOList.removeAllElements();
        for (int i = 0; i < unitValuesVOArray.length; i++) {
            unitValuesVOArray[i].setParentVO(this.getClass(), this);
            _unitValuesVOList.addElement(unitValuesVOArray[i]);
        }
    } //-- void setUnitValuesVO(edit.common.vo.UnitValuesVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ChargeCodeVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ChargeCodeVO) Unmarshaller.unmarshal(edit.common.vo.ChargeCodeVO.class, reader);
    } //-- edit.common.vo.ChargeCodeVO unmarshal(java.io.Reader) 

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
