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
 * Class ValueAtIssueVO.
 * 
 * @version $Revision$ $Date$
 */
public class ValueAtIssueVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _valueAtIssuePK
     */
    private long _valueAtIssuePK;

    /**
     * keeps track of state for field: _valueAtIssuePK
     */
    private boolean _has_valueAtIssuePK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _guaranteedPremium
     */
    private java.math.BigDecimal _guaranteedPremium;

    /**
     * Field _increaseAmount
     */
    private java.math.BigDecimal _increaseAmount;


      //----------------/
     //- Constructors -/
    //----------------/

    public ValueAtIssueVO() {
        super();
    } //-- edit.common.vo.ValueAtIssueVO()


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
        
        if (obj instanceof ValueAtIssueVO) {
        
            ValueAtIssueVO temp = (ValueAtIssueVO)obj;
            if (this._valueAtIssuePK != temp._valueAtIssuePK)
                return false;
            if (this._has_valueAtIssuePK != temp._has_valueAtIssuePK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._guaranteedPremium != null) {
                if (temp._guaranteedPremium == null) return false;
                else if (!(this._guaranteedPremium.equals(temp._guaranteedPremium))) 
                    return false;
            }
            else if (temp._guaranteedPremium != null)
                return false;
            if (this._increaseAmount != null) {
                if (temp._increaseAmount == null) return false;
                else if (!(this._increaseAmount.equals(temp._increaseAmount))) 
                    return false;
            }
            else if (temp._increaseAmount != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getEffectiveDateReturns the value of field
     * 'effectiveDate'.
     * 
     * @return the value of field 'effectiveDate'.
     */
    public java.lang.String getEffectiveDate()
    {
        return this._effectiveDate;
    } //-- java.lang.String getEffectiveDate() 

    /**
     * Method getGuaranteedPremiumReturns the value of field
     * 'guaranteedPremium'.
     * 
     * @return the value of field 'guaranteedPremium'.
     */
    public java.math.BigDecimal getGuaranteedPremium()
    {
        return this._guaranteedPremium;
    } //-- java.math.BigDecimal getGuaranteedPremium() 

    /**
     * Method getIncreaseAmountReturns the value of field
     * 'increaseAmount'.
     * 
     * @return the value of field 'increaseAmount'.
     */
    public java.math.BigDecimal getIncreaseAmount()
    {
        return this._increaseAmount;
    } //-- java.math.BigDecimal getIncreaseAmount() 

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
     * Method getValueAtIssuePKReturns the value of field
     * 'valueAtIssuePK'.
     * 
     * @return the value of field 'valueAtIssuePK'.
     */
    public long getValueAtIssuePK()
    {
        return this._valueAtIssuePK;
    } //-- long getValueAtIssuePK() 

    /**
     * Method hasSegmentFK
     */
    public boolean hasSegmentFK()
    {
        return this._has_segmentFK;
    } //-- boolean hasSegmentFK() 

    /**
     * Method hasValueAtIssuePK
     */
    public boolean hasValueAtIssuePK()
    {
        return this._has_valueAtIssuePK;
    } //-- boolean hasValueAtIssuePK() 

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
     * Method setEffectiveDateSets the value of field
     * 'effectiveDate'.
     * 
     * @param effectiveDate the value of field 'effectiveDate'.
     */
    public void setEffectiveDate(java.lang.String effectiveDate)
    {
        this._effectiveDate = effectiveDate;
        
        super.setVoChanged(true);
    } //-- void setEffectiveDate(java.lang.String) 

    /**
     * Method setGuaranteedPremiumSets the value of field
     * 'guaranteedPremium'.
     * 
     * @param guaranteedPremium the value of field
     * 'guaranteedPremium'.
     */
    public void setGuaranteedPremium(java.math.BigDecimal guaranteedPremium)
    {
        this._guaranteedPremium = guaranteedPremium;
        
        super.setVoChanged(true);
    } //-- void setGuaranteedPremium(java.math.BigDecimal) 

    /**
     * Method setIncreaseAmountSets the value of field
     * 'increaseAmount'.
     * 
     * @param increaseAmount the value of field 'increaseAmount'.
     */
    public void setIncreaseAmount(java.math.BigDecimal increaseAmount)
    {
        this._increaseAmount = increaseAmount;
        
        super.setVoChanged(true);
    } //-- void setIncreaseAmount(java.math.BigDecimal) 

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
     * Method setValueAtIssuePKSets the value of field
     * 'valueAtIssuePK'.
     * 
     * @param valueAtIssuePK the value of field 'valueAtIssuePK'.
     */
    public void setValueAtIssuePK(long valueAtIssuePK)
    {
        this._valueAtIssuePK = valueAtIssuePK;
        
        super.setVoChanged(true);
        this._has_valueAtIssuePK = true;
    } //-- void setValueAtIssuePK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ValueAtIssueVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ValueAtIssueVO) Unmarshaller.unmarshal(edit.common.vo.ValueAtIssueVO.class, reader);
    } //-- edit.common.vo.ValueAtIssueVO unmarshal(java.io.Reader) 

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
