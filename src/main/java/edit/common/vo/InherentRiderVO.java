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
 * Class InherentRiderVO.
 * 
 * @version $Revision$ $Date$
 */
public class InherentRiderVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _inherentRiderPK
     */
    private long _inherentRiderPK;

    /**
     * keeps track of state for field: _inherentRiderPK
     */
    private boolean _has_inherentRiderPK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _riderEffectiveDate
     */
    private java.lang.String _riderEffectiveDate;

    /**
     * Field _inherentRiderTypeCT
     */
    private java.lang.String _inherentRiderTypeCT;

    /**
     * Field _riderStatusCT
     */
    private java.lang.String _riderStatusCT;

    /**
     * Field _riderPercent
     */
    private java.math.BigDecimal _riderPercent;

    /**
     * Field _amount
     */
    private java.math.BigDecimal _amount;


      //----------------/
     //- Constructors -/
    //----------------/

    public InherentRiderVO() {
        super();
    } //-- edit.common.vo.InherentRiderVO()


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
        
        if (obj instanceof InherentRiderVO) {
        
            InherentRiderVO temp = (InherentRiderVO)obj;
            if (this._inherentRiderPK != temp._inherentRiderPK)
                return false;
            if (this._has_inherentRiderPK != temp._has_inherentRiderPK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._riderEffectiveDate != null) {
                if (temp._riderEffectiveDate == null) return false;
                else if (!(this._riderEffectiveDate.equals(temp._riderEffectiveDate))) 
                    return false;
            }
            else if (temp._riderEffectiveDate != null)
                return false;
            if (this._inherentRiderTypeCT != null) {
                if (temp._inherentRiderTypeCT == null) return false;
                else if (!(this._inherentRiderTypeCT.equals(temp._inherentRiderTypeCT))) 
                    return false;
            }
            else if (temp._inherentRiderTypeCT != null)
                return false;
            if (this._riderStatusCT != null) {
                if (temp._riderStatusCT == null) return false;
                else if (!(this._riderStatusCT.equals(temp._riderStatusCT))) 
                    return false;
            }
            else if (temp._riderStatusCT != null)
                return false;
            if (this._riderPercent != null) {
                if (temp._riderPercent == null) return false;
                else if (!(this._riderPercent.equals(temp._riderPercent))) 
                    return false;
            }
            else if (temp._riderPercent != null)
                return false;
            if (this._amount != null) {
                if (temp._amount == null) return false;
                else if (!(this._amount.equals(temp._amount))) 
                    return false;
            }
            else if (temp._amount != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAmountReturns the value of field 'amount'.
     * 
     * @return the value of field 'amount'.
     */
    public java.math.BigDecimal getAmount()
    {
        return this._amount;
    } //-- java.math.BigDecimal getAmount() 

    /**
     * Method getInherentRiderPKReturns the value of field
     * 'inherentRiderPK'.
     * 
     * @return the value of field 'inherentRiderPK'.
     */
    public long getInherentRiderPK()
    {
        return this._inherentRiderPK;
    } //-- long getInherentRiderPK() 

    /**
     * Method getInherentRiderTypeCTReturns the value of field
     * 'inherentRiderTypeCT'.
     * 
     * @return the value of field 'inherentRiderTypeCT'.
     */
    public java.lang.String getInherentRiderTypeCT()
    {
        return this._inherentRiderTypeCT;
    } //-- java.lang.String getInherentRiderTypeCT() 

    /**
     * Method getRiderEffectiveDateReturns the value of field
     * 'riderEffectiveDate'.
     * 
     * @return the value of field 'riderEffectiveDate'.
     */
    public java.lang.String getRiderEffectiveDate()
    {
        return this._riderEffectiveDate;
    } //-- java.lang.String getRiderEffectiveDate() 

    /**
     * Method getRiderPercentReturns the value of field
     * 'riderPercent'.
     * 
     * @return the value of field 'riderPercent'.
     */
    public java.math.BigDecimal getRiderPercent()
    {
        return this._riderPercent;
    } //-- java.math.BigDecimal getRiderPercent() 

    /**
     * Method getRiderStatusCTReturns the value of field
     * 'riderStatusCT'.
     * 
     * @return the value of field 'riderStatusCT'.
     */
    public java.lang.String getRiderStatusCT()
    {
        return this._riderStatusCT;
    } //-- java.lang.String getRiderStatusCT() 

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
     * Method hasInherentRiderPK
     */
    public boolean hasInherentRiderPK()
    {
        return this._has_inherentRiderPK;
    } //-- boolean hasInherentRiderPK() 

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
     * Method setAmountSets the value of field 'amount'.
     * 
     * @param amount the value of field 'amount'.
     */
    public void setAmount(java.math.BigDecimal amount)
    {
        this._amount = amount;
        
        super.setVoChanged(true);
    } //-- void setAmount(java.math.BigDecimal) 

    /**
     * Method setInherentRiderPKSets the value of field
     * 'inherentRiderPK'.
     * 
     * @param inherentRiderPK the value of field 'inherentRiderPK'.
     */
    public void setInherentRiderPK(long inherentRiderPK)
    {
        this._inherentRiderPK = inherentRiderPK;
        
        super.setVoChanged(true);
        this._has_inherentRiderPK = true;
    } //-- void setInherentRiderPK(long) 

    /**
     * Method setInherentRiderTypeCTSets the value of field
     * 'inherentRiderTypeCT'.
     * 
     * @param inherentRiderTypeCT the value of field
     * 'inherentRiderTypeCT'.
     */
    public void setInherentRiderTypeCT(java.lang.String inherentRiderTypeCT)
    {
        this._inherentRiderTypeCT = inherentRiderTypeCT;
        
        super.setVoChanged(true);
    } //-- void setInherentRiderTypeCT(java.lang.String) 

    /**
     * Method setRiderEffectiveDateSets the value of field
     * 'riderEffectiveDate'.
     * 
     * @param riderEffectiveDate the value of field
     * 'riderEffectiveDate'.
     */
    public void setRiderEffectiveDate(java.lang.String riderEffectiveDate)
    {
        this._riderEffectiveDate = riderEffectiveDate;
        
        super.setVoChanged(true);
    } //-- void setRiderEffectiveDate(java.lang.String) 

    /**
     * Method setRiderPercentSets the value of field
     * 'riderPercent'.
     * 
     * @param riderPercent the value of field 'riderPercent'.
     */
    public void setRiderPercent(java.math.BigDecimal riderPercent)
    {
        this._riderPercent = riderPercent;
        
        super.setVoChanged(true);
    } //-- void setRiderPercent(java.math.BigDecimal) 

    /**
     * Method setRiderStatusCTSets the value of field
     * 'riderStatusCT'.
     * 
     * @param riderStatusCT the value of field 'riderStatusCT'.
     */
    public void setRiderStatusCT(java.lang.String riderStatusCT)
    {
        this._riderStatusCT = riderStatusCT;
        
        super.setVoChanged(true);
    } //-- void setRiderStatusCT(java.lang.String) 

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
    public static edit.common.vo.InherentRiderVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.InherentRiderVO) Unmarshaller.unmarshal(edit.common.vo.InherentRiderVO.class, reader);
    } //-- edit.common.vo.InherentRiderVO unmarshal(java.io.Reader) 

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
