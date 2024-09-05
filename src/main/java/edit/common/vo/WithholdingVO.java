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
 * Class WithholdingVO.
 * 
 * @version $Revision$ $Date$
 */
public class WithholdingVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _withholdingPK
     */
    private long _withholdingPK;

    /**
     * keeps track of state for field: _withholdingPK
     */
    private boolean _has_withholdingPK;

    /**
     * Field _contractClientFK
     */
    private long _contractClientFK;

    /**
     * keeps track of state for field: _contractClientFK
     */
    private boolean _has_contractClientFK;

    /**
     * Field _federalWithholdingTypeCT
     */
    private java.lang.String _federalWithholdingTypeCT;

    /**
     * Field _federalWithholdingAmount
     */
    private java.math.BigDecimal _federalWithholdingAmount;

    /**
     * Field _federalWithholdingPercent
     */
    private java.math.BigDecimal _federalWithholdingPercent;

    /**
     * Field _stateWithholdingTypeCT
     */
    private java.lang.String _stateWithholdingTypeCT;

    /**
     * Field _stateWithholdingAmount
     */
    private java.math.BigDecimal _stateWithholdingAmount;

    /**
     * Field _stateWithholdingPercent
     */
    private java.math.BigDecimal _stateWithholdingPercent;

    /**
     * Field _cityWithholdingTypeCT
     */
    private java.lang.String _cityWithholdingTypeCT;

    /**
     * Field _cityWithholdingAmount
     */
    private java.math.BigDecimal _cityWithholdingAmount;

    /**
     * Field _cityWithholdingPercent
     */
    private java.math.BigDecimal _cityWithholdingPercent;

    /**
     * Field _countyWithholdingTypeCT
     */
    private java.lang.String _countyWithholdingTypeCT;

    /**
     * Field _countyWithholdingAmount
     */
    private java.math.BigDecimal _countyWithholdingAmount;

    /**
     * Field _countyWithholdingPercent
     */
    private java.math.BigDecimal _countyWithholdingPercent;

    /**
     * Field _withholdingOverrideVOList
     */
    private java.util.Vector _withholdingOverrideVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public WithholdingVO() {
        super();
        _withholdingOverrideVOList = new Vector();
    } //-- edit.common.vo.WithholdingVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addWithholdingOverrideVO
     * 
     * @param vWithholdingOverrideVO
     */
    public void addWithholdingOverrideVO(edit.common.vo.WithholdingOverrideVO vWithholdingOverrideVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vWithholdingOverrideVO.setParentVO(this.getClass(), this);
        _withholdingOverrideVOList.addElement(vWithholdingOverrideVO);
    } //-- void addWithholdingOverrideVO(edit.common.vo.WithholdingOverrideVO) 

    /**
     * Method addWithholdingOverrideVO
     * 
     * @param index
     * @param vWithholdingOverrideVO
     */
    public void addWithholdingOverrideVO(int index, edit.common.vo.WithholdingOverrideVO vWithholdingOverrideVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vWithholdingOverrideVO.setParentVO(this.getClass(), this);
        _withholdingOverrideVOList.insertElementAt(vWithholdingOverrideVO, index);
    } //-- void addWithholdingOverrideVO(int, edit.common.vo.WithholdingOverrideVO) 

    /**
     * Method enumerateWithholdingOverrideVO
     */
    public java.util.Enumeration enumerateWithholdingOverrideVO()
    {
        return _withholdingOverrideVOList.elements();
    } //-- java.util.Enumeration enumerateWithholdingOverrideVO() 

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
        
        if (obj instanceof WithholdingVO) {
        
            WithholdingVO temp = (WithholdingVO)obj;
            if (this._withholdingPK != temp._withholdingPK)
                return false;
            if (this._has_withholdingPK != temp._has_withholdingPK)
                return false;
            if (this._contractClientFK != temp._contractClientFK)
                return false;
            if (this._has_contractClientFK != temp._has_contractClientFK)
                return false;
            if (this._federalWithholdingTypeCT != null) {
                if (temp._federalWithholdingTypeCT == null) return false;
                else if (!(this._federalWithholdingTypeCT.equals(temp._federalWithholdingTypeCT))) 
                    return false;
            }
            else if (temp._federalWithholdingTypeCT != null)
                return false;
            if (this._federalWithholdingAmount != null) {
                if (temp._federalWithholdingAmount == null) return false;
                else if (!(this._federalWithholdingAmount.equals(temp._federalWithholdingAmount))) 
                    return false;
            }
            else if (temp._federalWithholdingAmount != null)
                return false;
            if (this._federalWithholdingPercent != null) {
                if (temp._federalWithholdingPercent == null) return false;
                else if (!(this._federalWithholdingPercent.equals(temp._federalWithholdingPercent))) 
                    return false;
            }
            else if (temp._federalWithholdingPercent != null)
                return false;
            if (this._stateWithholdingTypeCT != null) {
                if (temp._stateWithholdingTypeCT == null) return false;
                else if (!(this._stateWithholdingTypeCT.equals(temp._stateWithholdingTypeCT))) 
                    return false;
            }
            else if (temp._stateWithholdingTypeCT != null)
                return false;
            if (this._stateWithholdingAmount != null) {
                if (temp._stateWithholdingAmount == null) return false;
                else if (!(this._stateWithholdingAmount.equals(temp._stateWithholdingAmount))) 
                    return false;
            }
            else if (temp._stateWithholdingAmount != null)
                return false;
            if (this._stateWithholdingPercent != null) {
                if (temp._stateWithholdingPercent == null) return false;
                else if (!(this._stateWithholdingPercent.equals(temp._stateWithholdingPercent))) 
                    return false;
            }
            else if (temp._stateWithholdingPercent != null)
                return false;
            if (this._cityWithholdingTypeCT != null) {
                if (temp._cityWithholdingTypeCT == null) return false;
                else if (!(this._cityWithholdingTypeCT.equals(temp._cityWithholdingTypeCT))) 
                    return false;
            }
            else if (temp._cityWithholdingTypeCT != null)
                return false;
            if (this._cityWithholdingAmount != null) {
                if (temp._cityWithholdingAmount == null) return false;
                else if (!(this._cityWithholdingAmount.equals(temp._cityWithholdingAmount))) 
                    return false;
            }
            else if (temp._cityWithholdingAmount != null)
                return false;
            if (this._cityWithholdingPercent != null) {
                if (temp._cityWithholdingPercent == null) return false;
                else if (!(this._cityWithholdingPercent.equals(temp._cityWithholdingPercent))) 
                    return false;
            }
            else if (temp._cityWithholdingPercent != null)
                return false;
            if (this._countyWithholdingTypeCT != null) {
                if (temp._countyWithholdingTypeCT == null) return false;
                else if (!(this._countyWithholdingTypeCT.equals(temp._countyWithholdingTypeCT))) 
                    return false;
            }
            else if (temp._countyWithholdingTypeCT != null)
                return false;
            if (this._countyWithholdingAmount != null) {
                if (temp._countyWithholdingAmount == null) return false;
                else if (!(this._countyWithholdingAmount.equals(temp._countyWithholdingAmount))) 
                    return false;
            }
            else if (temp._countyWithholdingAmount != null)
                return false;
            if (this._countyWithholdingPercent != null) {
                if (temp._countyWithholdingPercent == null) return false;
                else if (!(this._countyWithholdingPercent.equals(temp._countyWithholdingPercent))) 
                    return false;
            }
            else if (temp._countyWithholdingPercent != null)
                return false;
            if (this._withholdingOverrideVOList != null) {
                if (temp._withholdingOverrideVOList == null) return false;
                else if (!(this._withholdingOverrideVOList.equals(temp._withholdingOverrideVOList))) 
                    return false;
            }
            else if (temp._withholdingOverrideVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCityWithholdingAmountReturns the value of field
     * 'cityWithholdingAmount'.
     * 
     * @return the value of field 'cityWithholdingAmount'.
     */
    public java.math.BigDecimal getCityWithholdingAmount()
    {
        return this._cityWithholdingAmount;
    } //-- java.math.BigDecimal getCityWithholdingAmount() 

    /**
     * Method getCityWithholdingPercentReturns the value of field
     * 'cityWithholdingPercent'.
     * 
     * @return the value of field 'cityWithholdingPercent'.
     */
    public java.math.BigDecimal getCityWithholdingPercent()
    {
        return this._cityWithholdingPercent;
    } //-- java.math.BigDecimal getCityWithholdingPercent() 

    /**
     * Method getCityWithholdingTypeCTReturns the value of field
     * 'cityWithholdingTypeCT'.
     * 
     * @return the value of field 'cityWithholdingTypeCT'.
     */
    public java.lang.String getCityWithholdingTypeCT()
    {
        return this._cityWithholdingTypeCT;
    } //-- java.lang.String getCityWithholdingTypeCT() 

    /**
     * Method getContractClientFKReturns the value of field
     * 'contractClientFK'.
     * 
     * @return the value of field 'contractClientFK'.
     */
    public long getContractClientFK()
    {
        return this._contractClientFK;
    } //-- long getContractClientFK() 

    /**
     * Method getCountyWithholdingAmountReturns the value of field
     * 'countyWithholdingAmount'.
     * 
     * @return the value of field 'countyWithholdingAmount'.
     */
    public java.math.BigDecimal getCountyWithholdingAmount()
    {
        return this._countyWithholdingAmount;
    } //-- java.math.BigDecimal getCountyWithholdingAmount() 

    /**
     * Method getCountyWithholdingPercentReturns the value of field
     * 'countyWithholdingPercent'.
     * 
     * @return the value of field 'countyWithholdingPercent'.
     */
    public java.math.BigDecimal getCountyWithholdingPercent()
    {
        return this._countyWithholdingPercent;
    } //-- java.math.BigDecimal getCountyWithholdingPercent() 

    /**
     * Method getCountyWithholdingTypeCTReturns the value of field
     * 'countyWithholdingTypeCT'.
     * 
     * @return the value of field 'countyWithholdingTypeCT'.
     */
    public java.lang.String getCountyWithholdingTypeCT()
    {
        return this._countyWithholdingTypeCT;
    } //-- java.lang.String getCountyWithholdingTypeCT() 

    /**
     * Method getFederalWithholdingAmountReturns the value of field
     * 'federalWithholdingAmount'.
     * 
     * @return the value of field 'federalWithholdingAmount'.
     */
    public java.math.BigDecimal getFederalWithholdingAmount()
    {
        return this._federalWithholdingAmount;
    } //-- java.math.BigDecimal getFederalWithholdingAmount() 

    /**
     * Method getFederalWithholdingPercentReturns the value of
     * field 'federalWithholdingPercent'.
     * 
     * @return the value of field 'federalWithholdingPercent'.
     */
    public java.math.BigDecimal getFederalWithholdingPercent()
    {
        return this._federalWithholdingPercent;
    } //-- java.math.BigDecimal getFederalWithholdingPercent() 

    /**
     * Method getFederalWithholdingTypeCTReturns the value of field
     * 'federalWithholdingTypeCT'.
     * 
     * @return the value of field 'federalWithholdingTypeCT'.
     */
    public java.lang.String getFederalWithholdingTypeCT()
    {
        return this._federalWithholdingTypeCT;
    } //-- java.lang.String getFederalWithholdingTypeCT() 

    /**
     * Method getStateWithholdingAmountReturns the value of field
     * 'stateWithholdingAmount'.
     * 
     * @return the value of field 'stateWithholdingAmount'.
     */
    public java.math.BigDecimal getStateWithholdingAmount()
    {
        return this._stateWithholdingAmount;
    } //-- java.math.BigDecimal getStateWithholdingAmount() 

    /**
     * Method getStateWithholdingPercentReturns the value of field
     * 'stateWithholdingPercent'.
     * 
     * @return the value of field 'stateWithholdingPercent'.
     */
    public java.math.BigDecimal getStateWithholdingPercent()
    {
        return this._stateWithholdingPercent;
    } //-- java.math.BigDecimal getStateWithholdingPercent() 

    /**
     * Method getStateWithholdingTypeCTReturns the value of field
     * 'stateWithholdingTypeCT'.
     * 
     * @return the value of field 'stateWithholdingTypeCT'.
     */
    public java.lang.String getStateWithholdingTypeCT()
    {
        return this._stateWithholdingTypeCT;
    } //-- java.lang.String getStateWithholdingTypeCT() 

    /**
     * Method getWithholdingOverrideVO
     * 
     * @param index
     */
    public edit.common.vo.WithholdingOverrideVO getWithholdingOverrideVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _withholdingOverrideVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.WithholdingOverrideVO) _withholdingOverrideVOList.elementAt(index);
    } //-- edit.common.vo.WithholdingOverrideVO getWithholdingOverrideVO(int) 

    /**
     * Method getWithholdingOverrideVO
     */
    public edit.common.vo.WithholdingOverrideVO[] getWithholdingOverrideVO()
    {
        int size = _withholdingOverrideVOList.size();
        edit.common.vo.WithholdingOverrideVO[] mArray = new edit.common.vo.WithholdingOverrideVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.WithholdingOverrideVO) _withholdingOverrideVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.WithholdingOverrideVO[] getWithholdingOverrideVO() 

    /**
     * Method getWithholdingOverrideVOCount
     */
    public int getWithholdingOverrideVOCount()
    {
        return _withholdingOverrideVOList.size();
    } //-- int getWithholdingOverrideVOCount() 

    /**
     * Method getWithholdingPKReturns the value of field
     * 'withholdingPK'.
     * 
     * @return the value of field 'withholdingPK'.
     */
    public long getWithholdingPK()
    {
        return this._withholdingPK;
    } //-- long getWithholdingPK() 

    /**
     * Method hasContractClientFK
     */
    public boolean hasContractClientFK()
    {
        return this._has_contractClientFK;
    } //-- boolean hasContractClientFK() 

    /**
     * Method hasWithholdingPK
     */
    public boolean hasWithholdingPK()
    {
        return this._has_withholdingPK;
    } //-- boolean hasWithholdingPK() 

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
     * Method removeAllWithholdingOverrideVO
     */
    public void removeAllWithholdingOverrideVO()
    {
        _withholdingOverrideVOList.removeAllElements();
    } //-- void removeAllWithholdingOverrideVO() 

    /**
     * Method removeWithholdingOverrideVO
     * 
     * @param index
     */
    public edit.common.vo.WithholdingOverrideVO removeWithholdingOverrideVO(int index)
    {
        java.lang.Object obj = _withholdingOverrideVOList.elementAt(index);
        _withholdingOverrideVOList.removeElementAt(index);
        return (edit.common.vo.WithholdingOverrideVO) obj;
    } //-- edit.common.vo.WithholdingOverrideVO removeWithholdingOverrideVO(int) 

    /**
     * Method setCityWithholdingAmountSets the value of field
     * 'cityWithholdingAmount'.
     * 
     * @param cityWithholdingAmount the value of field
     * 'cityWithholdingAmount'.
     */
    public void setCityWithholdingAmount(java.math.BigDecimal cityWithholdingAmount)
    {
        this._cityWithholdingAmount = cityWithholdingAmount;
        
        super.setVoChanged(true);
    } //-- void setCityWithholdingAmount(java.math.BigDecimal) 

    /**
     * Method setCityWithholdingPercentSets the value of field
     * 'cityWithholdingPercent'.
     * 
     * @param cityWithholdingPercent the value of field
     * 'cityWithholdingPercent'.
     */
    public void setCityWithholdingPercent(java.math.BigDecimal cityWithholdingPercent)
    {
        this._cityWithholdingPercent = cityWithholdingPercent;
        
        super.setVoChanged(true);
    } //-- void setCityWithholdingPercent(java.math.BigDecimal) 

    /**
     * Method setCityWithholdingTypeCTSets the value of field
     * 'cityWithholdingTypeCT'.
     * 
     * @param cityWithholdingTypeCT the value of field
     * 'cityWithholdingTypeCT'.
     */
    public void setCityWithholdingTypeCT(java.lang.String cityWithholdingTypeCT)
    {
        this._cityWithholdingTypeCT = cityWithholdingTypeCT;
        
        super.setVoChanged(true);
    } //-- void setCityWithholdingTypeCT(java.lang.String) 

    /**
     * Method setContractClientFKSets the value of field
     * 'contractClientFK'.
     * 
     * @param contractClientFK the value of field 'contractClientFK'
     */
    public void setContractClientFK(long contractClientFK)
    {
        this._contractClientFK = contractClientFK;
        
        super.setVoChanged(true);
        this._has_contractClientFK = true;
    } //-- void setContractClientFK(long) 

    /**
     * Method setCountyWithholdingAmountSets the value of field
     * 'countyWithholdingAmount'.
     * 
     * @param countyWithholdingAmount the value of field
     * 'countyWithholdingAmount'.
     */
    public void setCountyWithholdingAmount(java.math.BigDecimal countyWithholdingAmount)
    {
        this._countyWithholdingAmount = countyWithholdingAmount;
        
        super.setVoChanged(true);
    } //-- void setCountyWithholdingAmount(java.math.BigDecimal) 

    /**
     * Method setCountyWithholdingPercentSets the value of field
     * 'countyWithholdingPercent'.
     * 
     * @param countyWithholdingPercent the value of field
     * 'countyWithholdingPercent'.
     */
    public void setCountyWithholdingPercent(java.math.BigDecimal countyWithholdingPercent)
    {
        this._countyWithholdingPercent = countyWithholdingPercent;
        
        super.setVoChanged(true);
    } //-- void setCountyWithholdingPercent(java.math.BigDecimal) 

    /**
     * Method setCountyWithholdingTypeCTSets the value of field
     * 'countyWithholdingTypeCT'.
     * 
     * @param countyWithholdingTypeCT the value of field
     * 'countyWithholdingTypeCT'.
     */
    public void setCountyWithholdingTypeCT(java.lang.String countyWithholdingTypeCT)
    {
        this._countyWithholdingTypeCT = countyWithholdingTypeCT;
        
        super.setVoChanged(true);
    } //-- void setCountyWithholdingTypeCT(java.lang.String) 

    /**
     * Method setFederalWithholdingAmountSets the value of field
     * 'federalWithholdingAmount'.
     * 
     * @param federalWithholdingAmount the value of field
     * 'federalWithholdingAmount'.
     */
    public void setFederalWithholdingAmount(java.math.BigDecimal federalWithholdingAmount)
    {
        this._federalWithholdingAmount = federalWithholdingAmount;
        
        super.setVoChanged(true);
    } //-- void setFederalWithholdingAmount(java.math.BigDecimal) 

    /**
     * Method setFederalWithholdingPercentSets the value of field
     * 'federalWithholdingPercent'.
     * 
     * @param federalWithholdingPercent the value of field
     * 'federalWithholdingPercent'.
     */
    public void setFederalWithholdingPercent(java.math.BigDecimal federalWithholdingPercent)
    {
        this._federalWithholdingPercent = federalWithholdingPercent;
        
        super.setVoChanged(true);
    } //-- void setFederalWithholdingPercent(java.math.BigDecimal) 

    /**
     * Method setFederalWithholdingTypeCTSets the value of field
     * 'federalWithholdingTypeCT'.
     * 
     * @param federalWithholdingTypeCT the value of field
     * 'federalWithholdingTypeCT'.
     */
    public void setFederalWithholdingTypeCT(java.lang.String federalWithholdingTypeCT)
    {
        this._federalWithholdingTypeCT = federalWithholdingTypeCT;
        
        super.setVoChanged(true);
    } //-- void setFederalWithholdingTypeCT(java.lang.String) 

    /**
     * Method setStateWithholdingAmountSets the value of field
     * 'stateWithholdingAmount'.
     * 
     * @param stateWithholdingAmount the value of field
     * 'stateWithholdingAmount'.
     */
    public void setStateWithholdingAmount(java.math.BigDecimal stateWithholdingAmount)
    {
        this._stateWithholdingAmount = stateWithholdingAmount;
        
        super.setVoChanged(true);
    } //-- void setStateWithholdingAmount(java.math.BigDecimal) 

    /**
     * Method setStateWithholdingPercentSets the value of field
     * 'stateWithholdingPercent'.
     * 
     * @param stateWithholdingPercent the value of field
     * 'stateWithholdingPercent'.
     */
    public void setStateWithholdingPercent(java.math.BigDecimal stateWithholdingPercent)
    {
        this._stateWithholdingPercent = stateWithholdingPercent;
        
        super.setVoChanged(true);
    } //-- void setStateWithholdingPercent(java.math.BigDecimal) 

    /**
     * Method setStateWithholdingTypeCTSets the value of field
     * 'stateWithholdingTypeCT'.
     * 
     * @param stateWithholdingTypeCT the value of field
     * 'stateWithholdingTypeCT'.
     */
    public void setStateWithholdingTypeCT(java.lang.String stateWithholdingTypeCT)
    {
        this._stateWithholdingTypeCT = stateWithholdingTypeCT;
        
        super.setVoChanged(true);
    } //-- void setStateWithholdingTypeCT(java.lang.String) 

    /**
     * Method setWithholdingOverrideVO
     * 
     * @param index
     * @param vWithholdingOverrideVO
     */
    public void setWithholdingOverrideVO(int index, edit.common.vo.WithholdingOverrideVO vWithholdingOverrideVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _withholdingOverrideVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vWithholdingOverrideVO.setParentVO(this.getClass(), this);
        _withholdingOverrideVOList.setElementAt(vWithholdingOverrideVO, index);
    } //-- void setWithholdingOverrideVO(int, edit.common.vo.WithholdingOverrideVO) 

    /**
     * Method setWithholdingOverrideVO
     * 
     * @param withholdingOverrideVOArray
     */
    public void setWithholdingOverrideVO(edit.common.vo.WithholdingOverrideVO[] withholdingOverrideVOArray)
    {
        //-- copy array
        _withholdingOverrideVOList.removeAllElements();
        for (int i = 0; i < withholdingOverrideVOArray.length; i++) {
            withholdingOverrideVOArray[i].setParentVO(this.getClass(), this);
            _withholdingOverrideVOList.addElement(withholdingOverrideVOArray[i]);
        }
    } //-- void setWithholdingOverrideVO(edit.common.vo.WithholdingOverrideVO) 

    /**
     * Method setWithholdingPKSets the value of field
     * 'withholdingPK'.
     * 
     * @param withholdingPK the value of field 'withholdingPK'.
     */
    public void setWithholdingPK(long withholdingPK)
    {
        this._withholdingPK = withholdingPK;
        
        super.setVoChanged(true);
        this._has_withholdingPK = true;
    } //-- void setWithholdingPK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.WithholdingVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.WithholdingVO) Unmarshaller.unmarshal(edit.common.vo.WithholdingVO.class, reader);
    } //-- edit.common.vo.WithholdingVO unmarshal(java.io.Reader) 

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
