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
 * Class InvestmentAllocationVO.
 * 
 * @version $Revision$ $Date$
 */
public class InvestmentAllocationVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _investmentAllocationPK
     */
    private long _investmentAllocationPK;

    /**
     * keeps track of state for field: _investmentAllocationPK
     */
    private boolean _has_investmentAllocationPK;

    /**
     * Field _investmentFK
     */
    private long _investmentFK;

    /**
     * keeps track of state for field: _investmentFK
     */
    private boolean _has_investmentFK;

    /**
     * Field _allocationPercent
     */
    private java.math.BigDecimal _allocationPercent;

    /**
     * Field _dollars
     */
    private java.math.BigDecimal _dollars;

    /**
     * Field _units
     */
    private java.math.BigDecimal _units;

    /**
     * Field _overrideStatus
     */
    private java.lang.String _overrideStatus;

    /**
     * Field _investmentAllocationOverrideVOList
     */
    private java.util.Vector _investmentAllocationOverrideVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public InvestmentAllocationVO() {
        super();
        _investmentAllocationOverrideVOList = new Vector();
    } //-- edit.common.vo.InvestmentAllocationVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addInvestmentAllocationOverrideVO
     * 
     * @param vInvestmentAllocationOverrideVO
     */
    public void addInvestmentAllocationOverrideVO(edit.common.vo.InvestmentAllocationOverrideVO vInvestmentAllocationOverrideVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentAllocationOverrideVO.setParentVO(this.getClass(), this);
        _investmentAllocationOverrideVOList.addElement(vInvestmentAllocationOverrideVO);
    } //-- void addInvestmentAllocationOverrideVO(edit.common.vo.InvestmentAllocationOverrideVO) 

    /**
     * Method addInvestmentAllocationOverrideVO
     * 
     * @param index
     * @param vInvestmentAllocationOverrideVO
     */
    public void addInvestmentAllocationOverrideVO(int index, edit.common.vo.InvestmentAllocationOverrideVO vInvestmentAllocationOverrideVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentAllocationOverrideVO.setParentVO(this.getClass(), this);
        _investmentAllocationOverrideVOList.insertElementAt(vInvestmentAllocationOverrideVO, index);
    } //-- void addInvestmentAllocationOverrideVO(int, edit.common.vo.InvestmentAllocationOverrideVO) 

    /**
     * Method enumerateInvestmentAllocationOverrideVO
     */
    public java.util.Enumeration enumerateInvestmentAllocationOverrideVO()
    {
        return _investmentAllocationOverrideVOList.elements();
    } //-- java.util.Enumeration enumerateInvestmentAllocationOverrideVO() 

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
        
        if (obj instanceof InvestmentAllocationVO) {
        
            InvestmentAllocationVO temp = (InvestmentAllocationVO)obj;
            if (this._investmentAllocationPK != temp._investmentAllocationPK)
                return false;
            if (this._has_investmentAllocationPK != temp._has_investmentAllocationPK)
                return false;
            if (this._investmentFK != temp._investmentFK)
                return false;
            if (this._has_investmentFK != temp._has_investmentFK)
                return false;
            if (this._allocationPercent != null) {
                if (temp._allocationPercent == null) return false;
                else if (!(this._allocationPercent.equals(temp._allocationPercent))) 
                    return false;
            }
            else if (temp._allocationPercent != null)
                return false;
            if (this._dollars != null) {
                if (temp._dollars == null) return false;
                else if (!(this._dollars.equals(temp._dollars))) 
                    return false;
            }
            else if (temp._dollars != null)
                return false;
            if (this._units != null) {
                if (temp._units == null) return false;
                else if (!(this._units.equals(temp._units))) 
                    return false;
            }
            else if (temp._units != null)
                return false;
            if (this._overrideStatus != null) {
                if (temp._overrideStatus == null) return false;
                else if (!(this._overrideStatus.equals(temp._overrideStatus))) 
                    return false;
            }
            else if (temp._overrideStatus != null)
                return false;
            if (this._investmentAllocationOverrideVOList != null) {
                if (temp._investmentAllocationOverrideVOList == null) return false;
                else if (!(this._investmentAllocationOverrideVOList.equals(temp._investmentAllocationOverrideVOList))) 
                    return false;
            }
            else if (temp._investmentAllocationOverrideVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAllocationPercentReturns the value of field
     * 'allocationPercent'.
     * 
     * @return the value of field 'allocationPercent'.
     */
    public java.math.BigDecimal getAllocationPercent()
    {
        return this._allocationPercent;
    } //-- java.math.BigDecimal getAllocationPercent() 

    /**
     * Method getDollarsReturns the value of field 'dollars'.
     * 
     * @return the value of field 'dollars'.
     */
    public java.math.BigDecimal getDollars()
    {
        return this._dollars;
    } //-- java.math.BigDecimal getDollars() 

    /**
     * Method getInvestmentAllocationOverrideVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentAllocationOverrideVO getInvestmentAllocationOverrideVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentAllocationOverrideVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.InvestmentAllocationOverrideVO) _investmentAllocationOverrideVOList.elementAt(index);
    } //-- edit.common.vo.InvestmentAllocationOverrideVO getInvestmentAllocationOverrideVO(int) 

    /**
     * Method getInvestmentAllocationOverrideVO
     */
    public edit.common.vo.InvestmentAllocationOverrideVO[] getInvestmentAllocationOverrideVO()
    {
        int size = _investmentAllocationOverrideVOList.size();
        edit.common.vo.InvestmentAllocationOverrideVO[] mArray = new edit.common.vo.InvestmentAllocationOverrideVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.InvestmentAllocationOverrideVO) _investmentAllocationOverrideVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.InvestmentAllocationOverrideVO[] getInvestmentAllocationOverrideVO() 

    /**
     * Method getInvestmentAllocationOverrideVOCount
     */
    public int getInvestmentAllocationOverrideVOCount()
    {
        return _investmentAllocationOverrideVOList.size();
    } //-- int getInvestmentAllocationOverrideVOCount() 

    /**
     * Method getInvestmentAllocationPKReturns the value of field
     * 'investmentAllocationPK'.
     * 
     * @return the value of field 'investmentAllocationPK'.
     */
    public long getInvestmentAllocationPK()
    {
        return this._investmentAllocationPK;
    } //-- long getInvestmentAllocationPK() 

    /**
     * Method getInvestmentFKReturns the value of field
     * 'investmentFK'.
     * 
     * @return the value of field 'investmentFK'.
     */
    public long getInvestmentFK()
    {
        return this._investmentFK;
    } //-- long getInvestmentFK() 

    /**
     * Method getOverrideStatusReturns the value of field
     * 'overrideStatus'.
     * 
     * @return the value of field 'overrideStatus'.
     */
    public java.lang.String getOverrideStatus()
    {
        return this._overrideStatus;
    } //-- java.lang.String getOverrideStatus() 

    /**
     * Method getUnitsReturns the value of field 'units'.
     * 
     * @return the value of field 'units'.
     */
    public java.math.BigDecimal getUnits()
    {
        return this._units;
    } //-- java.math.BigDecimal getUnits() 

    /**
     * Method hasInvestmentAllocationPK
     */
    public boolean hasInvestmentAllocationPK()
    {
        return this._has_investmentAllocationPK;
    } //-- boolean hasInvestmentAllocationPK() 

    /**
     * Method hasInvestmentFK
     */
    public boolean hasInvestmentFK()
    {
        return this._has_investmentFK;
    } //-- boolean hasInvestmentFK() 

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
     * Method removeAllInvestmentAllocationOverrideVO
     */
    public void removeAllInvestmentAllocationOverrideVO()
    {
        _investmentAllocationOverrideVOList.removeAllElements();
    } //-- void removeAllInvestmentAllocationOverrideVO() 

    /**
     * Method removeInvestmentAllocationOverrideVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentAllocationOverrideVO removeInvestmentAllocationOverrideVO(int index)
    {
        java.lang.Object obj = _investmentAllocationOverrideVOList.elementAt(index);
        _investmentAllocationOverrideVOList.removeElementAt(index);
        return (edit.common.vo.InvestmentAllocationOverrideVO) obj;
    } //-- edit.common.vo.InvestmentAllocationOverrideVO removeInvestmentAllocationOverrideVO(int) 

    /**
     * Method setAllocationPercentSets the value of field
     * 'allocationPercent'.
     * 
     * @param allocationPercent the value of field
     * 'allocationPercent'.
     */
    public void setAllocationPercent(java.math.BigDecimal allocationPercent)
    {
        this._allocationPercent = allocationPercent;
        
        super.setVoChanged(true);
    } //-- void setAllocationPercent(java.math.BigDecimal) 

    /**
     * Method setDollarsSets the value of field 'dollars'.
     * 
     * @param dollars the value of field 'dollars'.
     */
    public void setDollars(java.math.BigDecimal dollars)
    {
        this._dollars = dollars;
        
        super.setVoChanged(true);
    } //-- void setDollars(java.math.BigDecimal) 

    /**
     * Method setInvestmentAllocationOverrideVO
     * 
     * @param index
     * @param vInvestmentAllocationOverrideVO
     */
    public void setInvestmentAllocationOverrideVO(int index, edit.common.vo.InvestmentAllocationOverrideVO vInvestmentAllocationOverrideVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentAllocationOverrideVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vInvestmentAllocationOverrideVO.setParentVO(this.getClass(), this);
        _investmentAllocationOverrideVOList.setElementAt(vInvestmentAllocationOverrideVO, index);
    } //-- void setInvestmentAllocationOverrideVO(int, edit.common.vo.InvestmentAllocationOverrideVO) 

    /**
     * Method setInvestmentAllocationOverrideVO
     * 
     * @param investmentAllocationOverrideVOArray
     */
    public void setInvestmentAllocationOverrideVO(edit.common.vo.InvestmentAllocationOverrideVO[] investmentAllocationOverrideVOArray)
    {
        //-- copy array
        _investmentAllocationOverrideVOList.removeAllElements();
        for (int i = 0; i < investmentAllocationOverrideVOArray.length; i++) {
            investmentAllocationOverrideVOArray[i].setParentVO(this.getClass(), this);
            _investmentAllocationOverrideVOList.addElement(investmentAllocationOverrideVOArray[i]);
        }
    } //-- void setInvestmentAllocationOverrideVO(edit.common.vo.InvestmentAllocationOverrideVO) 

    /**
     * Method setInvestmentAllocationPKSets the value of field
     * 'investmentAllocationPK'.
     * 
     * @param investmentAllocationPK the value of field
     * 'investmentAllocationPK'.
     */
    public void setInvestmentAllocationPK(long investmentAllocationPK)
    {
        this._investmentAllocationPK = investmentAllocationPK;
        
        super.setVoChanged(true);
        this._has_investmentAllocationPK = true;
    } //-- void setInvestmentAllocationPK(long) 

    /**
     * Method setInvestmentFKSets the value of field
     * 'investmentFK'.
     * 
     * @param investmentFK the value of field 'investmentFK'.
     */
    public void setInvestmentFK(long investmentFK)
    {
        this._investmentFK = investmentFK;
        
        super.setVoChanged(true);
        this._has_investmentFK = true;
    } //-- void setInvestmentFK(long) 

    /**
     * Method setOverrideStatusSets the value of field
     * 'overrideStatus'.
     * 
     * @param overrideStatus the value of field 'overrideStatus'.
     */
    public void setOverrideStatus(java.lang.String overrideStatus)
    {
        this._overrideStatus = overrideStatus;
        
        super.setVoChanged(true);
    } //-- void setOverrideStatus(java.lang.String) 

    /**
     * Method setUnitsSets the value of field 'units'.
     * 
     * @param units the value of field 'units'.
     */
    public void setUnits(java.math.BigDecimal units)
    {
        this._units = units;
        
        super.setVoChanged(true);
    } //-- void setUnits(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.InvestmentAllocationVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.InvestmentAllocationVO) Unmarshaller.unmarshal(edit.common.vo.InvestmentAllocationVO.class, reader);
    } //-- edit.common.vo.InvestmentAllocationVO unmarshal(java.io.Reader) 

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
