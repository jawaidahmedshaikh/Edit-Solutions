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
 * Class BucketAllocationVO.
 * 
 * @version $Revision$ $Date$
 */
public class BucketAllocationVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _bucketAllocationPK
     */
    private long _bucketAllocationPK;

    /**
     * keeps track of state for field: _bucketAllocationPK
     */
    private boolean _has_bucketAllocationPK;

    /**
     * Field _bucketFK
     */
    private long _bucketFK;

    /**
     * keeps track of state for field: _bucketFK
     */
    private boolean _has_bucketFK;

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


      //----------------/
     //- Constructors -/
    //----------------/

    public BucketAllocationVO() {
        super();
    } //-- edit.common.vo.BucketAllocationVO()


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
        
        if (obj instanceof BucketAllocationVO) {
        
            BucketAllocationVO temp = (BucketAllocationVO)obj;
            if (this._bucketAllocationPK != temp._bucketAllocationPK)
                return false;
            if (this._has_bucketAllocationPK != temp._has_bucketAllocationPK)
                return false;
            if (this._bucketFK != temp._bucketFK)
                return false;
            if (this._has_bucketFK != temp._has_bucketFK)
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
     * Method getBucketAllocationPKReturns the value of field
     * 'bucketAllocationPK'.
     * 
     * @return the value of field 'bucketAllocationPK'.
     */
    public long getBucketAllocationPK()
    {
        return this._bucketAllocationPK;
    } //-- long getBucketAllocationPK() 

    /**
     * Method getBucketFKReturns the value of field 'bucketFK'.
     * 
     * @return the value of field 'bucketFK'.
     */
    public long getBucketFK()
    {
        return this._bucketFK;
    } //-- long getBucketFK() 

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
     * Method getUnitsReturns the value of field 'units'.
     * 
     * @return the value of field 'units'.
     */
    public java.math.BigDecimal getUnits()
    {
        return this._units;
    } //-- java.math.BigDecimal getUnits() 

    /**
     * Method hasBucketAllocationPK
     */
    public boolean hasBucketAllocationPK()
    {
        return this._has_bucketAllocationPK;
    } //-- boolean hasBucketAllocationPK() 

    /**
     * Method hasBucketFK
     */
    public boolean hasBucketFK()
    {
        return this._has_bucketFK;
    } //-- boolean hasBucketFK() 

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
     * Method setBucketAllocationPKSets the value of field
     * 'bucketAllocationPK'.
     * 
     * @param bucketAllocationPK the value of field
     * 'bucketAllocationPK'.
     */
    public void setBucketAllocationPK(long bucketAllocationPK)
    {
        this._bucketAllocationPK = bucketAllocationPK;
        
        super.setVoChanged(true);
        this._has_bucketAllocationPK = true;
    } //-- void setBucketAllocationPK(long) 

    /**
     * Method setBucketFKSets the value of field 'bucketFK'.
     * 
     * @param bucketFK the value of field 'bucketFK'.
     */
    public void setBucketFK(long bucketFK)
    {
        this._bucketFK = bucketFK;
        
        super.setVoChanged(true);
        this._has_bucketFK = true;
    } //-- void setBucketFK(long) 

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
    public static edit.common.vo.BucketAllocationVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BucketAllocationVO) Unmarshaller.unmarshal(edit.common.vo.BucketAllocationVO.class, reader);
    } //-- edit.common.vo.BucketAllocationVO unmarshal(java.io.Reader) 

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
