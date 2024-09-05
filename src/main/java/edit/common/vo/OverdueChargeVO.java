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
 * Class OverdueChargeVO.
 * 
 * @version $Revision$ $Date$
 */
public class OverdueChargeVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _overdueChargePK
     */
    private long _overdueChargePK;

    /**
     * keeps track of state for field: _overdueChargePK
     */
    private boolean _has_overdueChargePK;

    /**
     * Field _EDITTrxFK
     */
    private long _EDITTrxFK;

    /**
     * keeps track of state for field: _EDITTrxFK
     */
    private boolean _has_EDITTrxFK;

    /**
     * Field _overdueCoi
     */
    private java.math.BigDecimal _overdueCoi;

    /**
     * Field _overdueAdmin
     */
    private java.math.BigDecimal _overdueAdmin;

    /**
     * Field _overdueExpense
     */
    private java.math.BigDecimal _overdueExpense;

    /**
     * Field _overdueCollateralization
     */
    private java.math.BigDecimal _overdueCollateralization;

    /**
     * Field _overdueChargeSettledVOList
     */
    private java.util.Vector _overdueChargeSettledVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public OverdueChargeVO() {
        super();
        _overdueChargeSettledVOList = new Vector();
    } //-- edit.common.vo.OverdueChargeVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addOverdueChargeSettledVO
     * 
     * @param vOverdueChargeSettledVO
     */
    public void addOverdueChargeSettledVO(edit.common.vo.OverdueChargeSettledVO vOverdueChargeSettledVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vOverdueChargeSettledVO.setParentVO(this.getClass(), this);
        _overdueChargeSettledVOList.addElement(vOverdueChargeSettledVO);
    } //-- void addOverdueChargeSettledVO(edit.common.vo.OverdueChargeSettledVO) 

    /**
     * Method addOverdueChargeSettledVO
     * 
     * @param index
     * @param vOverdueChargeSettledVO
     */
    public void addOverdueChargeSettledVO(int index, edit.common.vo.OverdueChargeSettledVO vOverdueChargeSettledVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vOverdueChargeSettledVO.setParentVO(this.getClass(), this);
        _overdueChargeSettledVOList.insertElementAt(vOverdueChargeSettledVO, index);
    } //-- void addOverdueChargeSettledVO(int, edit.common.vo.OverdueChargeSettledVO) 

    /**
     * Method enumerateOverdueChargeSettledVO
     */
    public java.util.Enumeration enumerateOverdueChargeSettledVO()
    {
        return _overdueChargeSettledVOList.elements();
    } //-- java.util.Enumeration enumerateOverdueChargeSettledVO() 

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
        
        if (obj instanceof OverdueChargeVO) {
        
            OverdueChargeVO temp = (OverdueChargeVO)obj;
            if (this._overdueChargePK != temp._overdueChargePK)
                return false;
            if (this._has_overdueChargePK != temp._has_overdueChargePK)
                return false;
            if (this._EDITTrxFK != temp._EDITTrxFK)
                return false;
            if (this._has_EDITTrxFK != temp._has_EDITTrxFK)
                return false;
            if (this._overdueCoi != null) {
                if (temp._overdueCoi == null) return false;
                else if (!(this._overdueCoi.equals(temp._overdueCoi))) 
                    return false;
            }
            else if (temp._overdueCoi != null)
                return false;
            if (this._overdueAdmin != null) {
                if (temp._overdueAdmin == null) return false;
                else if (!(this._overdueAdmin.equals(temp._overdueAdmin))) 
                    return false;
            }
            else if (temp._overdueAdmin != null)
                return false;
            if (this._overdueExpense != null) {
                if (temp._overdueExpense == null) return false;
                else if (!(this._overdueExpense.equals(temp._overdueExpense))) 
                    return false;
            }
            else if (temp._overdueExpense != null)
                return false;
            if (this._overdueCollateralization != null) {
                if (temp._overdueCollateralization == null) return false;
                else if (!(this._overdueCollateralization.equals(temp._overdueCollateralization))) 
                    return false;
            }
            else if (temp._overdueCollateralization != null)
                return false;
            if (this._overdueChargeSettledVOList != null) {
                if (temp._overdueChargeSettledVOList == null) return false;
                else if (!(this._overdueChargeSettledVOList.equals(temp._overdueChargeSettledVOList))) 
                    return false;
            }
            else if (temp._overdueChargeSettledVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getEDITTrxFKReturns the value of field 'EDITTrxFK'.
     * 
     * @return the value of field 'EDITTrxFK'.
     */
    public long getEDITTrxFK()
    {
        return this._EDITTrxFK;
    } //-- long getEDITTrxFK() 

    /**
     * Method getOverdueAdminReturns the value of field
     * 'overdueAdmin'.
     * 
     * @return the value of field 'overdueAdmin'.
     */
    public java.math.BigDecimal getOverdueAdmin()
    {
        return this._overdueAdmin;
    } //-- java.math.BigDecimal getOverdueAdmin() 

    /**
     * Method getOverdueChargePKReturns the value of field
     * 'overdueChargePK'.
     * 
     * @return the value of field 'overdueChargePK'.
     */
    public long getOverdueChargePK()
    {
        return this._overdueChargePK;
    } //-- long getOverdueChargePK() 

    /**
     * Method getOverdueChargeSettledVO
     * 
     * @param index
     */
    public edit.common.vo.OverdueChargeSettledVO getOverdueChargeSettledVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _overdueChargeSettledVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.OverdueChargeSettledVO) _overdueChargeSettledVOList.elementAt(index);
    } //-- edit.common.vo.OverdueChargeSettledVO getOverdueChargeSettledVO(int) 

    /**
     * Method getOverdueChargeSettledVO
     */
    public edit.common.vo.OverdueChargeSettledVO[] getOverdueChargeSettledVO()
    {
        int size = _overdueChargeSettledVOList.size();
        edit.common.vo.OverdueChargeSettledVO[] mArray = new edit.common.vo.OverdueChargeSettledVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.OverdueChargeSettledVO) _overdueChargeSettledVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.OverdueChargeSettledVO[] getOverdueChargeSettledVO() 

    /**
     * Method getOverdueChargeSettledVOCount
     */
    public int getOverdueChargeSettledVOCount()
    {
        return _overdueChargeSettledVOList.size();
    } //-- int getOverdueChargeSettledVOCount() 

    /**
     * Method getOverdueCoiReturns the value of field 'overdueCoi'.
     * 
     * @return the value of field 'overdueCoi'.
     */
    public java.math.BigDecimal getOverdueCoi()
    {
        return this._overdueCoi;
    } //-- java.math.BigDecimal getOverdueCoi() 

    /**
     * Method getOverdueCollateralizationReturns the value of field
     * 'overdueCollateralization'.
     * 
     * @return the value of field 'overdueCollateralization'.
     */
    public java.math.BigDecimal getOverdueCollateralization()
    {
        return this._overdueCollateralization;
    } //-- java.math.BigDecimal getOverdueCollateralization() 

    /**
     * Method getOverdueExpenseReturns the value of field
     * 'overdueExpense'.
     * 
     * @return the value of field 'overdueExpense'.
     */
    public java.math.BigDecimal getOverdueExpense()
    {
        return this._overdueExpense;
    } //-- java.math.BigDecimal getOverdueExpense() 

    /**
     * Method hasEDITTrxFK
     */
    public boolean hasEDITTrxFK()
    {
        return this._has_EDITTrxFK;
    } //-- boolean hasEDITTrxFK() 

    /**
     * Method hasOverdueChargePK
     */
    public boolean hasOverdueChargePK()
    {
        return this._has_overdueChargePK;
    } //-- boolean hasOverdueChargePK() 

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
     * Method removeAllOverdueChargeSettledVO
     */
    public void removeAllOverdueChargeSettledVO()
    {
        _overdueChargeSettledVOList.removeAllElements();
    } //-- void removeAllOverdueChargeSettledVO() 

    /**
     * Method removeOverdueChargeSettledVO
     * 
     * @param index
     */
    public edit.common.vo.OverdueChargeSettledVO removeOverdueChargeSettledVO(int index)
    {
        java.lang.Object obj = _overdueChargeSettledVOList.elementAt(index);
        _overdueChargeSettledVOList.removeElementAt(index);
        return (edit.common.vo.OverdueChargeSettledVO) obj;
    } //-- edit.common.vo.OverdueChargeSettledVO removeOverdueChargeSettledVO(int) 

    /**
     * Method setEDITTrxFKSets the value of field 'EDITTrxFK'.
     * 
     * @param EDITTrxFK the value of field 'EDITTrxFK'.
     */
    public void setEDITTrxFK(long EDITTrxFK)
    {
        this._EDITTrxFK = EDITTrxFK;
        
        super.setVoChanged(true);
        this._has_EDITTrxFK = true;
    } //-- void setEDITTrxFK(long) 

    /**
     * Method setOverdueAdminSets the value of field
     * 'overdueAdmin'.
     * 
     * @param overdueAdmin the value of field 'overdueAdmin'.
     */
    public void setOverdueAdmin(java.math.BigDecimal overdueAdmin)
    {
        this._overdueAdmin = overdueAdmin;
        
        super.setVoChanged(true);
    } //-- void setOverdueAdmin(java.math.BigDecimal) 

    /**
     * Method setOverdueChargePKSets the value of field
     * 'overdueChargePK'.
     * 
     * @param overdueChargePK the value of field 'overdueChargePK'.
     */
    public void setOverdueChargePK(long overdueChargePK)
    {
        this._overdueChargePK = overdueChargePK;
        
        super.setVoChanged(true);
        this._has_overdueChargePK = true;
    } //-- void setOverdueChargePK(long) 

    /**
     * Method setOverdueChargeSettledVO
     * 
     * @param index
     * @param vOverdueChargeSettledVO
     */
    public void setOverdueChargeSettledVO(int index, edit.common.vo.OverdueChargeSettledVO vOverdueChargeSettledVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _overdueChargeSettledVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vOverdueChargeSettledVO.setParentVO(this.getClass(), this);
        _overdueChargeSettledVOList.setElementAt(vOverdueChargeSettledVO, index);
    } //-- void setOverdueChargeSettledVO(int, edit.common.vo.OverdueChargeSettledVO) 

    /**
     * Method setOverdueChargeSettledVO
     * 
     * @param overdueChargeSettledVOArray
     */
    public void setOverdueChargeSettledVO(edit.common.vo.OverdueChargeSettledVO[] overdueChargeSettledVOArray)
    {
        //-- copy array
        _overdueChargeSettledVOList.removeAllElements();
        for (int i = 0; i < overdueChargeSettledVOArray.length; i++) {
            overdueChargeSettledVOArray[i].setParentVO(this.getClass(), this);
            _overdueChargeSettledVOList.addElement(overdueChargeSettledVOArray[i]);
        }
    } //-- void setOverdueChargeSettledVO(edit.common.vo.OverdueChargeSettledVO) 

    /**
     * Method setOverdueCoiSets the value of field 'overdueCoi'.
     * 
     * @param overdueCoi the value of field 'overdueCoi'.
     */
    public void setOverdueCoi(java.math.BigDecimal overdueCoi)
    {
        this._overdueCoi = overdueCoi;
        
        super.setVoChanged(true);
    } //-- void setOverdueCoi(java.math.BigDecimal) 

    /**
     * Method setOverdueCollateralizationSets the value of field
     * 'overdueCollateralization'.
     * 
     * @param overdueCollateralization the value of field
     * 'overdueCollateralization'.
     */
    public void setOverdueCollateralization(java.math.BigDecimal overdueCollateralization)
    {
        this._overdueCollateralization = overdueCollateralization;
        
        super.setVoChanged(true);
    } //-- void setOverdueCollateralization(java.math.BigDecimal) 

    /**
     * Method setOverdueExpenseSets the value of field
     * 'overdueExpense'.
     * 
     * @param overdueExpense the value of field 'overdueExpense'.
     */
    public void setOverdueExpense(java.math.BigDecimal overdueExpense)
    {
        this._overdueExpense = overdueExpense;
        
        super.setVoChanged(true);
    } //-- void setOverdueExpense(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.OverdueChargeVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.OverdueChargeVO) Unmarshaller.unmarshal(edit.common.vo.OverdueChargeVO.class, reader);
    } //-- edit.common.vo.OverdueChargeVO unmarshal(java.io.Reader) 

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
