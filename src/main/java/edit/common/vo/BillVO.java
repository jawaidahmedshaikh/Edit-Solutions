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
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class BillVO.
 * 
 * @version $Revision$ $Date$
 */
public class BillVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _billPK
     */
    private long _billPK;

    /**
     * keeps track of state for field: _billPK
     */
    private boolean _has_billPK;

    /**
     * Field _billGroupFK
     */
    private long _billGroupFK;

    /**
     * keeps track of state for field: _billGroupFK
     */
    private boolean _has_billGroupFK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _billedAmount
     */
    private java.math.BigDecimal _billedAmount;

    /**
     * Field _paidAmount
     */
    private java.math.BigDecimal _paidAmount;

    /**
     * Field _adjustmentAmount
     */
    private java.math.BigDecimal _adjustmentAmount;


      //----------------/
     //- Constructors -/
    //----------------/

    public BillVO() {
        super();
    } //-- edit.common.vo.BillVO()


      //-----------/
     //- Methods -/
    //-----------/

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
        
        if (obj instanceof BillVO) {
        
            BillVO temp = (BillVO)obj;
            if (this._billPK != temp._billPK)
                return false;
            if (this._has_billPK != temp._has_billPK)
                return false;
            if (this._billGroupFK != temp._billGroupFK)
                return false;
            if (this._has_billGroupFK != temp._has_billGroupFK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._billedAmount != null) {
                if (temp._billedAmount == null) return false;
                else if (!(this._billedAmount.equals(temp._billedAmount))) 
                    return false;
            }
            else if (temp._billedAmount != null)
                return false;
            if (this._paidAmount != null) {
                if (temp._paidAmount == null) return false;
                else if (!(this._paidAmount.equals(temp._paidAmount))) 
                    return false;
            }
            else if (temp._paidAmount != null)
                return false;
            if (this._adjustmentAmount != null) {
                if (temp._adjustmentAmount == null) return false;
                else if (!(this._adjustmentAmount.equals(temp._adjustmentAmount))) 
                    return false;
            }
            else if (temp._adjustmentAmount != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAdjustmentAmountReturns the value of field
     * 'adjustmentAmount'.
     * 
     * @return the value of field 'adjustmentAmount'.
     */
    public java.math.BigDecimal getAdjustmentAmount()
    {
        return this._adjustmentAmount;
    } //-- java.math.BigDecimal getAdjustmentAmount() 

    /**
     * Method getBillGroupFKReturns the value of field
     * 'billGroupFK'.
     * 
     * @return the value of field 'billGroupFK'.
     */
    public long getBillGroupFK()
    {
        return this._billGroupFK;
    } //-- long getBillGroupFK() 

    /**
     * Method getBillPKReturns the value of field 'billPK'.
     * 
     * @return the value of field 'billPK'.
     */
    public long getBillPK()
    {
        return this._billPK;
    } //-- long getBillPK() 

    /**
     * Method getBilledAmountReturns the value of field
     * 'billedAmount'.
     * 
     * @return the value of field 'billedAmount'.
     */
    public java.math.BigDecimal getBilledAmount()
    {
        return this._billedAmount;
    } //-- java.math.BigDecimal getBilledAmount() 

    /**
     * Method getPaidAmountReturns the value of field 'paidAmount'.
     * 
     * @return the value of field 'paidAmount'.
     */
    public java.math.BigDecimal getPaidAmount()
    {
        return this._paidAmount;
    } //-- java.math.BigDecimal getPaidAmount() 

    /**
     * Method getSegmentFKReturns the value of field 'segmentFK'.
     * 
     * @return the value of field 'segmentFK'.
     */
    public long getSegmentFK()
    {
        return this._segmentFK;
    } //-- long getSegmentFK() 

    /**
     * Method hasBillGroupFK
     */
    public boolean hasBillGroupFK()
    {
        return this._has_billGroupFK;
    } //-- boolean hasBillGroupFK() 

    /**
     * Method hasBillPK
     */
    public boolean hasBillPK()
    {
        return this._has_billPK;
    } //-- boolean hasBillPK() 

    /**
     * Method hasSegmentFK
     */
    public boolean hasSegmentFK()
    {
        return this._has_segmentFK;
    } //-- boolean hasSegmentFK() 

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
     * Method setAdjustmentAmountSets the value of field
     * 'adjustmentAmount'.
     * 
     * @param adjustmentAmount the value of field 'adjustmentAmount'
     */
    public void setAdjustmentAmount(java.math.BigDecimal adjustmentAmount)
    {
        this._adjustmentAmount = adjustmentAmount;
        
        super.setVoChanged(true);
    } //-- void setAdjustmentAmount(java.math.BigDecimal) 

    /**
     * Method setBillGroupFKSets the value of field 'billGroupFK'.
     * 
     * @param billGroupFK the value of field 'billGroupFK'.
     */
    public void setBillGroupFK(long billGroupFK)
    {
        this._billGroupFK = billGroupFK;
        
        super.setVoChanged(true);
        this._has_billGroupFK = true;
    } //-- void setBillGroupFK(long) 

    /**
     * Method setBillPKSets the value of field 'billPK'.
     * 
     * @param billPK the value of field 'billPK'.
     */
    public void setBillPK(long billPK)
    {
        this._billPK = billPK;
        
        super.setVoChanged(true);
        this._has_billPK = true;
    } //-- void setBillPK(long) 

    /**
     * Method setBilledAmountSets the value of field
     * 'billedAmount'.
     * 
     * @param billedAmount the value of field 'billedAmount'.
     */
    public void setBilledAmount(java.math.BigDecimal billedAmount)
    {
        this._billedAmount = billedAmount;
        
        super.setVoChanged(true);
    } //-- void setBilledAmount(java.math.BigDecimal) 

    /**
     * Method setPaidAmountSets the value of field 'paidAmount'.
     * 
     * @param paidAmount the value of field 'paidAmount'.
     */
    public void setPaidAmount(java.math.BigDecimal paidAmount)
    {
        this._paidAmount = paidAmount;
        
        super.setVoChanged(true);
    } //-- void setPaidAmount(java.math.BigDecimal) 

    /**
     * Method setSegmentFKSets the value of field 'segmentFK'.
     * 
     * @param segmentFK the value of field 'segmentFK'.
     */
    public void setSegmentFK(long segmentFK)
    {
        this._segmentFK = segmentFK;
        
        super.setVoChanged(true);
        this._has_segmentFK = true;
    } //-- void setSegmentFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BillVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BillVO) Unmarshaller.unmarshal(edit.common.vo.BillVO.class, reader);
    } //-- edit.common.vo.BillVO unmarshal(java.io.Reader) 

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
