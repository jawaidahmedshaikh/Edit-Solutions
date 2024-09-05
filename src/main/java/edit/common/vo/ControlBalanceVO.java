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
 * Class ControlBalanceVO.
 * 
 * @version $Revision$ $Date$
 */
public class ControlBalanceVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _controlBalancePK
     */
    private long _controlBalancePK;

    /**
     * keeps track of state for field: _controlBalancePK
     */
    private boolean _has_controlBalancePK;

    /**
     * Field _productFilteredFundStructureFK
     */
    private long _productFilteredFundStructureFK;

    /**
     * keeps track of state for field:
     * _productFilteredFundStructureFK
     */
    private boolean _has_productFilteredFundStructureFK;

    /**
     * Field _endingDollarBalance
     */
    private java.math.BigDecimal _endingDollarBalance;

    /**
     * Field _endingUnitBalance
     */
    private java.math.BigDecimal _endingUnitBalance;

    /**
     * Field _endingBalanceCycleDate
     */
    private java.lang.String _endingBalanceCycleDate;

    /**
     * Field _endingShareBalance
     */
    private java.math.BigDecimal _endingShareBalance;

    /**
     * Field _chargeCodeFK
     */
    private long _chargeCodeFK;

    /**
     * keeps track of state for field: _chargeCodeFK
     */
    private boolean _has_chargeCodeFK;

    /**
     * Field _DFCASHEndingShareBalance
     */
    private java.math.BigDecimal _DFCASHEndingShareBalance;

    /**
     * Field _controlBalanceDetailVOList
     */
    private java.util.Vector _controlBalanceDetailVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ControlBalanceVO() {
        super();
        _controlBalanceDetailVOList = new Vector();
    } //-- edit.common.vo.ControlBalanceVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addControlBalanceDetailVO
     * 
     * @param vControlBalanceDetailVO
     */
    public void addControlBalanceDetailVO(edit.common.vo.ControlBalanceDetailVO vControlBalanceDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vControlBalanceDetailVO.setParentVO(this.getClass(), this);
        _controlBalanceDetailVOList.addElement(vControlBalanceDetailVO);
    } //-- void addControlBalanceDetailVO(edit.common.vo.ControlBalanceDetailVO) 

    /**
     * Method addControlBalanceDetailVO
     * 
     * @param index
     * @param vControlBalanceDetailVO
     */
    public void addControlBalanceDetailVO(int index, edit.common.vo.ControlBalanceDetailVO vControlBalanceDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vControlBalanceDetailVO.setParentVO(this.getClass(), this);
        _controlBalanceDetailVOList.insertElementAt(vControlBalanceDetailVO, index);
    } //-- void addControlBalanceDetailVO(int, edit.common.vo.ControlBalanceDetailVO) 

    /**
     * Method enumerateControlBalanceDetailVO
     */
    public java.util.Enumeration enumerateControlBalanceDetailVO()
    {
        return _controlBalanceDetailVOList.elements();
    } //-- java.util.Enumeration enumerateControlBalanceDetailVO() 

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
        
        if (obj instanceof ControlBalanceVO) {
        
            ControlBalanceVO temp = (ControlBalanceVO)obj;
            if (this._controlBalancePK != temp._controlBalancePK)
                return false;
            if (this._has_controlBalancePK != temp._has_controlBalancePK)
                return false;
            if (this._productFilteredFundStructureFK != temp._productFilteredFundStructureFK)
                return false;
            if (this._has_productFilteredFundStructureFK != temp._has_productFilteredFundStructureFK)
                return false;
            if (this._endingDollarBalance != null) {
                if (temp._endingDollarBalance == null) return false;
                else if (!(this._endingDollarBalance.equals(temp._endingDollarBalance))) 
                    return false;
            }
            else if (temp._endingDollarBalance != null)
                return false;
            if (this._endingUnitBalance != null) {
                if (temp._endingUnitBalance == null) return false;
                else if (!(this._endingUnitBalance.equals(temp._endingUnitBalance))) 
                    return false;
            }
            else if (temp._endingUnitBalance != null)
                return false;
            if (this._endingBalanceCycleDate != null) {
                if (temp._endingBalanceCycleDate == null) return false;
                else if (!(this._endingBalanceCycleDate.equals(temp._endingBalanceCycleDate))) 
                    return false;
            }
            else if (temp._endingBalanceCycleDate != null)
                return false;
            if (this._endingShareBalance != null) {
                if (temp._endingShareBalance == null) return false;
                else if (!(this._endingShareBalance.equals(temp._endingShareBalance))) 
                    return false;
            }
            else if (temp._endingShareBalance != null)
                return false;
            if (this._chargeCodeFK != temp._chargeCodeFK)
                return false;
            if (this._has_chargeCodeFK != temp._has_chargeCodeFK)
                return false;
            if (this._DFCASHEndingShareBalance != null) {
                if (temp._DFCASHEndingShareBalance == null) return false;
                else if (!(this._DFCASHEndingShareBalance.equals(temp._DFCASHEndingShareBalance))) 
                    return false;
            }
            else if (temp._DFCASHEndingShareBalance != null)
                return false;
            if (this._controlBalanceDetailVOList != null) {
                if (temp._controlBalanceDetailVOList == null) return false;
                else if (!(this._controlBalanceDetailVOList.equals(temp._controlBalanceDetailVOList))) 
                    return false;
            }
            else if (temp._controlBalanceDetailVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getControlBalanceDetailVO
     * 
     * @param index
     */
    public edit.common.vo.ControlBalanceDetailVO getControlBalanceDetailVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _controlBalanceDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ControlBalanceDetailVO) _controlBalanceDetailVOList.elementAt(index);
    } //-- edit.common.vo.ControlBalanceDetailVO getControlBalanceDetailVO(int) 

    /**
     * Method getControlBalanceDetailVO
     */
    public edit.common.vo.ControlBalanceDetailVO[] getControlBalanceDetailVO()
    {
        int size = _controlBalanceDetailVOList.size();
        edit.common.vo.ControlBalanceDetailVO[] mArray = new edit.common.vo.ControlBalanceDetailVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ControlBalanceDetailVO) _controlBalanceDetailVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ControlBalanceDetailVO[] getControlBalanceDetailVO() 

    /**
     * Method getControlBalanceDetailVOCount
     */
    public int getControlBalanceDetailVOCount()
    {
        return _controlBalanceDetailVOList.size();
    } //-- int getControlBalanceDetailVOCount() 

    /**
     * Method getControlBalancePKReturns the value of field
     * 'controlBalancePK'.
     * 
     * @return the value of field 'controlBalancePK'.
     */
    public long getControlBalancePK()
    {
        return this._controlBalancePK;
    } //-- long getControlBalancePK() 

    /**
     * Method getDFCASHEndingShareBalanceReturns the value of field
     * 'DFCASHEndingShareBalance'.
     * 
     * @return the value of field 'DFCASHEndingShareBalance'.
     */
    public java.math.BigDecimal getDFCASHEndingShareBalance()
    {
        return this._DFCASHEndingShareBalance;
    } //-- java.math.BigDecimal getDFCASHEndingShareBalance() 

    /**
     * Method getEndingBalanceCycleDateReturns the value of field
     * 'endingBalanceCycleDate'.
     * 
     * @return the value of field 'endingBalanceCycleDate'.
     */
    public java.lang.String getEndingBalanceCycleDate()
    {
        return this._endingBalanceCycleDate;
    } //-- java.lang.String getEndingBalanceCycleDate() 

    /**
     * Method getEndingDollarBalanceReturns the value of field
     * 'endingDollarBalance'.
     * 
     * @return the value of field 'endingDollarBalance'.
     */
    public java.math.BigDecimal getEndingDollarBalance()
    {
        return this._endingDollarBalance;
    } //-- java.math.BigDecimal getEndingDollarBalance() 

    /**
     * Method getEndingShareBalanceReturns the value of field
     * 'endingShareBalance'.
     * 
     * @return the value of field 'endingShareBalance'.
     */
    public java.math.BigDecimal getEndingShareBalance()
    {
        return this._endingShareBalance;
    } //-- java.math.BigDecimal getEndingShareBalance() 

    /**
     * Method getEndingUnitBalanceReturns the value of field
     * 'endingUnitBalance'.
     * 
     * @return the value of field 'endingUnitBalance'.
     */
    public java.math.BigDecimal getEndingUnitBalance()
    {
        return this._endingUnitBalance;
    } //-- java.math.BigDecimal getEndingUnitBalance() 

    /**
     * Method getProductFilteredFundStructureFKReturns the value of
     * field 'productFilteredFundStructureFK'.
     * 
     * @return the value of field 'productFilteredFundStructureFK'.
     */
    public long getProductFilteredFundStructureFK()
    {
        return this._productFilteredFundStructureFK;
    } //-- long getProductFilteredFundStructureFK() 

    /**
     * Method hasChargeCodeFK
     */
    public boolean hasChargeCodeFK()
    {
        return this._has_chargeCodeFK;
    } //-- boolean hasChargeCodeFK() 

    /**
     * Method hasControlBalancePK
     */
    public boolean hasControlBalancePK()
    {
        return this._has_controlBalancePK;
    } //-- boolean hasControlBalancePK() 

    /**
     * Method hasProductFilteredFundStructureFK
     */
    public boolean hasProductFilteredFundStructureFK()
    {
        return this._has_productFilteredFundStructureFK;
    } //-- boolean hasProductFilteredFundStructureFK() 

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
     * Method removeAllControlBalanceDetailVO
     */
    public void removeAllControlBalanceDetailVO()
    {
        _controlBalanceDetailVOList.removeAllElements();
    } //-- void removeAllControlBalanceDetailVO() 

    /**
     * Method removeControlBalanceDetailVO
     * 
     * @param index
     */
    public edit.common.vo.ControlBalanceDetailVO removeControlBalanceDetailVO(int index)
    {
        java.lang.Object obj = _controlBalanceDetailVOList.elementAt(index);
        _controlBalanceDetailVOList.removeElementAt(index);
        return (edit.common.vo.ControlBalanceDetailVO) obj;
    } //-- edit.common.vo.ControlBalanceDetailVO removeControlBalanceDetailVO(int) 

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
     * Method setControlBalanceDetailVO
     * 
     * @param index
     * @param vControlBalanceDetailVO
     */
    public void setControlBalanceDetailVO(int index, edit.common.vo.ControlBalanceDetailVO vControlBalanceDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _controlBalanceDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vControlBalanceDetailVO.setParentVO(this.getClass(), this);
        _controlBalanceDetailVOList.setElementAt(vControlBalanceDetailVO, index);
    } //-- void setControlBalanceDetailVO(int, edit.common.vo.ControlBalanceDetailVO) 

    /**
     * Method setControlBalanceDetailVO
     * 
     * @param controlBalanceDetailVOArray
     */
    public void setControlBalanceDetailVO(edit.common.vo.ControlBalanceDetailVO[] controlBalanceDetailVOArray)
    {
        //-- copy array
        _controlBalanceDetailVOList.removeAllElements();
        for (int i = 0; i < controlBalanceDetailVOArray.length; i++) {
            controlBalanceDetailVOArray[i].setParentVO(this.getClass(), this);
            _controlBalanceDetailVOList.addElement(controlBalanceDetailVOArray[i]);
        }
    } //-- void setControlBalanceDetailVO(edit.common.vo.ControlBalanceDetailVO) 

    /**
     * Method setControlBalancePKSets the value of field
     * 'controlBalancePK'.
     * 
     * @param controlBalancePK the value of field 'controlBalancePK'
     */
    public void setControlBalancePK(long controlBalancePK)
    {
        this._controlBalancePK = controlBalancePK;
        
        super.setVoChanged(true);
        this._has_controlBalancePK = true;
    } //-- void setControlBalancePK(long) 

    /**
     * Method setDFCASHEndingShareBalanceSets the value of field
     * 'DFCASHEndingShareBalance'.
     * 
     * @param DFCASHEndingShareBalance the value of field
     * 'DFCASHEndingShareBalance'.
     */
    public void setDFCASHEndingShareBalance(java.math.BigDecimal DFCASHEndingShareBalance)
    {
        this._DFCASHEndingShareBalance = DFCASHEndingShareBalance;
        
        super.setVoChanged(true);
    } //-- void setDFCASHEndingShareBalance(java.math.BigDecimal) 

    /**
     * Method setEndingBalanceCycleDateSets the value of field
     * 'endingBalanceCycleDate'.
     * 
     * @param endingBalanceCycleDate the value of field
     * 'endingBalanceCycleDate'.
     */
    public void setEndingBalanceCycleDate(java.lang.String endingBalanceCycleDate)
    {
        this._endingBalanceCycleDate = endingBalanceCycleDate;
        
        super.setVoChanged(true);
    } //-- void setEndingBalanceCycleDate(java.lang.String) 

    /**
     * Method setEndingDollarBalanceSets the value of field
     * 'endingDollarBalance'.
     * 
     * @param endingDollarBalance the value of field
     * 'endingDollarBalance'.
     */
    public void setEndingDollarBalance(java.math.BigDecimal endingDollarBalance)
    {
        this._endingDollarBalance = endingDollarBalance;
        
        super.setVoChanged(true);
    } //-- void setEndingDollarBalance(java.math.BigDecimal) 

    /**
     * Method setEndingShareBalanceSets the value of field
     * 'endingShareBalance'.
     * 
     * @param endingShareBalance the value of field
     * 'endingShareBalance'.
     */
    public void setEndingShareBalance(java.math.BigDecimal endingShareBalance)
    {
        this._endingShareBalance = endingShareBalance;
        
        super.setVoChanged(true);
    } //-- void setEndingShareBalance(java.math.BigDecimal) 

    /**
     * Method setEndingUnitBalanceSets the value of field
     * 'endingUnitBalance'.
     * 
     * @param endingUnitBalance the value of field
     * 'endingUnitBalance'.
     */
    public void setEndingUnitBalance(java.math.BigDecimal endingUnitBalance)
    {
        this._endingUnitBalance = endingUnitBalance;
        
        super.setVoChanged(true);
    } //-- void setEndingUnitBalance(java.math.BigDecimal) 

    /**
     * Method setProductFilteredFundStructureFKSets the value of
     * field 'productFilteredFundStructureFK'.
     * 
     * @param productFilteredFundStructureFK the value of field
     * 'productFilteredFundStructureFK'.
     */
    public void setProductFilteredFundStructureFK(long productFilteredFundStructureFK)
    {
        this._productFilteredFundStructureFK = productFilteredFundStructureFK;
        
        super.setVoChanged(true);
        this._has_productFilteredFundStructureFK = true;
    } //-- void setProductFilteredFundStructureFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ControlBalanceVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ControlBalanceVO) Unmarshaller.unmarshal(edit.common.vo.ControlBalanceVO.class, reader);
    } //-- edit.common.vo.ControlBalanceVO unmarshal(java.io.Reader) 

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
