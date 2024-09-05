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
 * Class FeeDescriptionVO.
 * 
 * @version $Revision$ $Date$
 */
public class FeeDescriptionVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _feeDescriptionPK
     */
    private long _feeDescriptionPK;

    /**
     * keeps track of state for field: _feeDescriptionPK
     */
    private boolean _has_feeDescriptionPK;

    /**
     * Field _filteredFundFK
     */
    private long _filteredFundFK;

    /**
     * keeps track of state for field: _filteredFundFK
     */
    private boolean _has_filteredFundFK;

    /**
     * Field _feeTypeCT
     */
    private java.lang.String _feeTypeCT;

    /**
     * Field _feeRedemption
     */
    private java.lang.String _feeRedemption;

    /**
     * Field _pricingTypeCT
     */
    private java.lang.String _pricingTypeCT;

    /**
     * Field _clientDetailFK
     */
    private long _clientDetailFK;

    /**
     * keeps track of state for field: _clientDetailFK
     */
    private boolean _has_clientDetailFK;

    /**
     * Field _feeVOList
     */
    private java.util.Vector _feeVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public FeeDescriptionVO() {
        super();
        _feeVOList = new Vector();
    } //-- edit.common.vo.FeeDescriptionVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addFeeVO
     * 
     * @param vFeeVO
     */
    public void addFeeVO(edit.common.vo.FeeVO vFeeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFeeVO.setParentVO(this.getClass(), this);
        _feeVOList.addElement(vFeeVO);
    } //-- void addFeeVO(edit.common.vo.FeeVO) 

    /**
     * Method addFeeVO
     * 
     * @param index
     * @param vFeeVO
     */
    public void addFeeVO(int index, edit.common.vo.FeeVO vFeeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFeeVO.setParentVO(this.getClass(), this);
        _feeVOList.insertElementAt(vFeeVO, index);
    } //-- void addFeeVO(int, edit.common.vo.FeeVO) 

    /**
     * Method enumerateFeeVO
     */
    public java.util.Enumeration enumerateFeeVO()
    {
        return _feeVOList.elements();
    } //-- java.util.Enumeration enumerateFeeVO() 

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
        
        if (obj instanceof FeeDescriptionVO) {
        
            FeeDescriptionVO temp = (FeeDescriptionVO)obj;
            if (this._feeDescriptionPK != temp._feeDescriptionPK)
                return false;
            if (this._has_feeDescriptionPK != temp._has_feeDescriptionPK)
                return false;
            if (this._filteredFundFK != temp._filteredFundFK)
                return false;
            if (this._has_filteredFundFK != temp._has_filteredFundFK)
                return false;
            if (this._feeTypeCT != null) {
                if (temp._feeTypeCT == null) return false;
                else if (!(this._feeTypeCT.equals(temp._feeTypeCT))) 
                    return false;
            }
            else if (temp._feeTypeCT != null)
                return false;
            if (this._feeRedemption != null) {
                if (temp._feeRedemption == null) return false;
                else if (!(this._feeRedemption.equals(temp._feeRedemption))) 
                    return false;
            }
            else if (temp._feeRedemption != null)
                return false;
            if (this._pricingTypeCT != null) {
                if (temp._pricingTypeCT == null) return false;
                else if (!(this._pricingTypeCT.equals(temp._pricingTypeCT))) 
                    return false;
            }
            else if (temp._pricingTypeCT != null)
                return false;
            if (this._clientDetailFK != temp._clientDetailFK)
                return false;
            if (this._has_clientDetailFK != temp._has_clientDetailFK)
                return false;
            if (this._feeVOList != null) {
                if (temp._feeVOList == null) return false;
                else if (!(this._feeVOList.equals(temp._feeVOList))) 
                    return false;
            }
            else if (temp._feeVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getClientDetailFKReturns the value of field
     * 'clientDetailFK'.
     * 
     * @return the value of field 'clientDetailFK'.
     */
    public long getClientDetailFK()
    {
        return this._clientDetailFK;
    } //-- long getClientDetailFK() 

    /**
     * Method getFeeDescriptionPKReturns the value of field
     * 'feeDescriptionPK'.
     * 
     * @return the value of field 'feeDescriptionPK'.
     */
    public long getFeeDescriptionPK()
    {
        return this._feeDescriptionPK;
    } //-- long getFeeDescriptionPK() 

    /**
     * Method getFeeRedemptionReturns the value of field
     * 'feeRedemption'.
     * 
     * @return the value of field 'feeRedemption'.
     */
    public java.lang.String getFeeRedemption()
    {
        return this._feeRedemption;
    } //-- java.lang.String getFeeRedemption() 

    /**
     * Method getFeeTypeCTReturns the value of field 'feeTypeCT'.
     * 
     * @return the value of field 'feeTypeCT'.
     */
    public java.lang.String getFeeTypeCT()
    {
        return this._feeTypeCT;
    } //-- java.lang.String getFeeTypeCT() 

    /**
     * Method getFeeVO
     * 
     * @param index
     */
    public edit.common.vo.FeeVO getFeeVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _feeVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.FeeVO) _feeVOList.elementAt(index);
    } //-- edit.common.vo.FeeVO getFeeVO(int) 

    /**
     * Method getFeeVO
     */
    public edit.common.vo.FeeVO[] getFeeVO()
    {
        int size = _feeVOList.size();
        edit.common.vo.FeeVO[] mArray = new edit.common.vo.FeeVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.FeeVO) _feeVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.FeeVO[] getFeeVO() 

    /**
     * Method getFeeVOCount
     */
    public int getFeeVOCount()
    {
        return _feeVOList.size();
    } //-- int getFeeVOCount() 

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
     * Method getPricingTypeCTReturns the value of field
     * 'pricingTypeCT'.
     * 
     * @return the value of field 'pricingTypeCT'.
     */
    public java.lang.String getPricingTypeCT()
    {
        return this._pricingTypeCT;
    } //-- java.lang.String getPricingTypeCT() 

    /**
     * Method hasClientDetailFK
     */
    public boolean hasClientDetailFK()
    {
        return this._has_clientDetailFK;
    } //-- boolean hasClientDetailFK() 

    /**
     * Method hasFeeDescriptionPK
     */
    public boolean hasFeeDescriptionPK()
    {
        return this._has_feeDescriptionPK;
    } //-- boolean hasFeeDescriptionPK() 

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
     * Method removeAllFeeVO
     */
    public void removeAllFeeVO()
    {
        _feeVOList.removeAllElements();
    } //-- void removeAllFeeVO() 

    /**
     * Method removeFeeVO
     * 
     * @param index
     */
    public edit.common.vo.FeeVO removeFeeVO(int index)
    {
        java.lang.Object obj = _feeVOList.elementAt(index);
        _feeVOList.removeElementAt(index);
        return (edit.common.vo.FeeVO) obj;
    } //-- edit.common.vo.FeeVO removeFeeVO(int) 

    /**
     * Method setClientDetailFKSets the value of field
     * 'clientDetailFK'.
     * 
     * @param clientDetailFK the value of field 'clientDetailFK'.
     */
    public void setClientDetailFK(long clientDetailFK)
    {
        this._clientDetailFK = clientDetailFK;
        
        super.setVoChanged(true);
        this._has_clientDetailFK = true;
    } //-- void setClientDetailFK(long) 

    /**
     * Method setFeeDescriptionPKSets the value of field
     * 'feeDescriptionPK'.
     * 
     * @param feeDescriptionPK the value of field 'feeDescriptionPK'
     */
    public void setFeeDescriptionPK(long feeDescriptionPK)
    {
        this._feeDescriptionPK = feeDescriptionPK;
        
        super.setVoChanged(true);
        this._has_feeDescriptionPK = true;
    } //-- void setFeeDescriptionPK(long) 

    /**
     * Method setFeeRedemptionSets the value of field
     * 'feeRedemption'.
     * 
     * @param feeRedemption the value of field 'feeRedemption'.
     */
    public void setFeeRedemption(java.lang.String feeRedemption)
    {
        this._feeRedemption = feeRedemption;
        
        super.setVoChanged(true);
    } //-- void setFeeRedemption(java.lang.String) 

    /**
     * Method setFeeTypeCTSets the value of field 'feeTypeCT'.
     * 
     * @param feeTypeCT the value of field 'feeTypeCT'.
     */
    public void setFeeTypeCT(java.lang.String feeTypeCT)
    {
        this._feeTypeCT = feeTypeCT;
        
        super.setVoChanged(true);
    } //-- void setFeeTypeCT(java.lang.String) 

    /**
     * Method setFeeVO
     * 
     * @param index
     * @param vFeeVO
     */
    public void setFeeVO(int index, edit.common.vo.FeeVO vFeeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _feeVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vFeeVO.setParentVO(this.getClass(), this);
        _feeVOList.setElementAt(vFeeVO, index);
    } //-- void setFeeVO(int, edit.common.vo.FeeVO) 

    /**
     * Method setFeeVO
     * 
     * @param feeVOArray
     */
    public void setFeeVO(edit.common.vo.FeeVO[] feeVOArray)
    {
        //-- copy array
        _feeVOList.removeAllElements();
        for (int i = 0; i < feeVOArray.length; i++) {
            feeVOArray[i].setParentVO(this.getClass(), this);
            _feeVOList.addElement(feeVOArray[i]);
        }
    } //-- void setFeeVO(edit.common.vo.FeeVO) 

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
     * Method setPricingTypeCTSets the value of field
     * 'pricingTypeCT'.
     * 
     * @param pricingTypeCT the value of field 'pricingTypeCT'.
     */
    public void setPricingTypeCT(java.lang.String pricingTypeCT)
    {
        this._pricingTypeCT = pricingTypeCT;
        
        super.setVoChanged(true);
    } //-- void setPricingTypeCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.FeeDescriptionVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.FeeDescriptionVO) Unmarshaller.unmarshal(edit.common.vo.FeeDescriptionVO.class, reader);
    } //-- edit.common.vo.FeeDescriptionVO unmarshal(java.io.Reader) 

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
