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
 * Class AnnualizedSubBucketVO.
 * 
 * @version $Revision$ $Date$
 */
public class AnnualizedSubBucketVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _annualizedSubBucketPK
     */
    private long _annualizedSubBucketPK;

    /**
     * keeps track of state for field: _annualizedSubBucketPK
     */
    private boolean _has_annualizedSubBucketPK;

    /**
     * Field _bucketFK
     */
    private long _bucketFK;

    /**
     * keeps track of state for field: _bucketFK
     */
    private boolean _has_bucketFK;

    /**
     * Field _EDITTrxFK
     */
    private long _EDITTrxFK;

    /**
     * keeps track of state for field: _EDITTrxFK
     */
    private boolean _has_EDITTrxFK;

    /**
     * Field _SBEffDate
     */
    private java.lang.String _SBEffDate;

    /**
     * Field _SBCurrentRate
     */
    private java.math.BigDecimal _SBCurrentRate;

    /**
     * Field _SBCurrentEndDate
     */
    private java.lang.String _SBCurrentEndDate;

    /**
     * Field _SBBaseRate
     */
    private java.math.BigDecimal _SBBaseRate;

    /**
     * Field _SBBaseEndDate
     */
    private java.lang.String _SBBaseEndDate;

    /**
     * Field _SBGuarMinRate1
     */
    private java.math.BigDecimal _SBGuarMinRate1;

    /**
     * Field _SBGuarMinEndDate1
     */
    private java.lang.String _SBGuarMinEndDate1;

    /**
     * Field _SBGuarMinRate2
     */
    private java.math.BigDecimal _SBGuarMinRate2;

    /**
     * Field _SBGuarMinEndDate2
     */
    private java.lang.String _SBGuarMinEndDate2;

    /**
     * Field _SBNumberBuckets
     */
    private int _SBNumberBuckets;

    /**
     * keeps track of state for field: _SBNumberBuckets
     */
    private boolean _has_SBNumberBuckets;

    /**
     * Field _SBFundValue
     */
    private java.math.BigDecimal _SBFundValue;


      //----------------/
     //- Constructors -/
    //----------------/

    public AnnualizedSubBucketVO() {
        super();
    } //-- edit.common.vo.AnnualizedSubBucketVO()


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
        
        if (obj instanceof AnnualizedSubBucketVO) {
        
            AnnualizedSubBucketVO temp = (AnnualizedSubBucketVO)obj;
            if (this._annualizedSubBucketPK != temp._annualizedSubBucketPK)
                return false;
            if (this._has_annualizedSubBucketPK != temp._has_annualizedSubBucketPK)
                return false;
            if (this._bucketFK != temp._bucketFK)
                return false;
            if (this._has_bucketFK != temp._has_bucketFK)
                return false;
            if (this._EDITTrxFK != temp._EDITTrxFK)
                return false;
            if (this._has_EDITTrxFK != temp._has_EDITTrxFK)
                return false;
            if (this._SBEffDate != null) {
                if (temp._SBEffDate == null) return false;
                else if (!(this._SBEffDate.equals(temp._SBEffDate))) 
                    return false;
            }
            else if (temp._SBEffDate != null)
                return false;
            if (this._SBCurrentRate != null) {
                if (temp._SBCurrentRate == null) return false;
                else if (!(this._SBCurrentRate.equals(temp._SBCurrentRate))) 
                    return false;
            }
            else if (temp._SBCurrentRate != null)
                return false;
            if (this._SBCurrentEndDate != null) {
                if (temp._SBCurrentEndDate == null) return false;
                else if (!(this._SBCurrentEndDate.equals(temp._SBCurrentEndDate))) 
                    return false;
            }
            else if (temp._SBCurrentEndDate != null)
                return false;
            if (this._SBBaseRate != null) {
                if (temp._SBBaseRate == null) return false;
                else if (!(this._SBBaseRate.equals(temp._SBBaseRate))) 
                    return false;
            }
            else if (temp._SBBaseRate != null)
                return false;
            if (this._SBBaseEndDate != null) {
                if (temp._SBBaseEndDate == null) return false;
                else if (!(this._SBBaseEndDate.equals(temp._SBBaseEndDate))) 
                    return false;
            }
            else if (temp._SBBaseEndDate != null)
                return false;
            if (this._SBGuarMinRate1 != null) {
                if (temp._SBGuarMinRate1 == null) return false;
                else if (!(this._SBGuarMinRate1.equals(temp._SBGuarMinRate1))) 
                    return false;
            }
            else if (temp._SBGuarMinRate1 != null)
                return false;
            if (this._SBGuarMinEndDate1 != null) {
                if (temp._SBGuarMinEndDate1 == null) return false;
                else if (!(this._SBGuarMinEndDate1.equals(temp._SBGuarMinEndDate1))) 
                    return false;
            }
            else if (temp._SBGuarMinEndDate1 != null)
                return false;
            if (this._SBGuarMinRate2 != null) {
                if (temp._SBGuarMinRate2 == null) return false;
                else if (!(this._SBGuarMinRate2.equals(temp._SBGuarMinRate2))) 
                    return false;
            }
            else if (temp._SBGuarMinRate2 != null)
                return false;
            if (this._SBGuarMinEndDate2 != null) {
                if (temp._SBGuarMinEndDate2 == null) return false;
                else if (!(this._SBGuarMinEndDate2.equals(temp._SBGuarMinEndDate2))) 
                    return false;
            }
            else if (temp._SBGuarMinEndDate2 != null)
                return false;
            if (this._SBNumberBuckets != temp._SBNumberBuckets)
                return false;
            if (this._has_SBNumberBuckets != temp._has_SBNumberBuckets)
                return false;
            if (this._SBFundValue != null) {
                if (temp._SBFundValue == null) return false;
                else if (!(this._SBFundValue.equals(temp._SBFundValue))) 
                    return false;
            }
            else if (temp._SBFundValue != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAnnualizedSubBucketPKReturns the value of field
     * 'annualizedSubBucketPK'.
     * 
     * @return the value of field 'annualizedSubBucketPK'.
     */
    public long getAnnualizedSubBucketPK()
    {
        return this._annualizedSubBucketPK;
    } //-- long getAnnualizedSubBucketPK() 

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
     * Method getEDITTrxFKReturns the value of field 'EDITTrxFK'.
     * 
     * @return the value of field 'EDITTrxFK'.
     */
    public long getEDITTrxFK()
    {
        return this._EDITTrxFK;
    } //-- long getEDITTrxFK() 

    /**
     * Method getSBBaseEndDateReturns the value of field
     * 'SBBaseEndDate'.
     * 
     * @return the value of field 'SBBaseEndDate'.
     */
    public java.lang.String getSBBaseEndDate()
    {
        return this._SBBaseEndDate;
    } //-- java.lang.String getSBBaseEndDate() 

    /**
     * Method getSBBaseRateReturns the value of field 'SBBaseRate'.
     * 
     * @return the value of field 'SBBaseRate'.
     */
    public java.math.BigDecimal getSBBaseRate()
    {
        return this._SBBaseRate;
    } //-- java.math.BigDecimal getSBBaseRate() 

    /**
     * Method getSBCurrentEndDateReturns the value of field
     * 'SBCurrentEndDate'.
     * 
     * @return the value of field 'SBCurrentEndDate'.
     */
    public java.lang.String getSBCurrentEndDate()
    {
        return this._SBCurrentEndDate;
    } //-- java.lang.String getSBCurrentEndDate() 

    /**
     * Method getSBCurrentRateReturns the value of field
     * 'SBCurrentRate'.
     * 
     * @return the value of field 'SBCurrentRate'.
     */
    public java.math.BigDecimal getSBCurrentRate()
    {
        return this._SBCurrentRate;
    } //-- java.math.BigDecimal getSBCurrentRate() 

    /**
     * Method getSBEffDateReturns the value of field 'SBEffDate'.
     * 
     * @return the value of field 'SBEffDate'.
     */
    public java.lang.String getSBEffDate()
    {
        return this._SBEffDate;
    } //-- java.lang.String getSBEffDate() 

    /**
     * Method getSBFundValueReturns the value of field
     * 'SBFundValue'.
     * 
     * @return the value of field 'SBFundValue'.
     */
    public java.math.BigDecimal getSBFundValue()
    {
        return this._SBFundValue;
    } //-- java.math.BigDecimal getSBFundValue() 

    /**
     * Method getSBGuarMinEndDate1Returns the value of field
     * 'SBGuarMinEndDate1'.
     * 
     * @return the value of field 'SBGuarMinEndDate1'.
     */
    public java.lang.String getSBGuarMinEndDate1()
    {
        return this._SBGuarMinEndDate1;
    } //-- java.lang.String getSBGuarMinEndDate1() 

    /**
     * Method getSBGuarMinEndDate2Returns the value of field
     * 'SBGuarMinEndDate2'.
     * 
     * @return the value of field 'SBGuarMinEndDate2'.
     */
    public java.lang.String getSBGuarMinEndDate2()
    {
        return this._SBGuarMinEndDate2;
    } //-- java.lang.String getSBGuarMinEndDate2() 

    /**
     * Method getSBGuarMinRate1Returns the value of field
     * 'SBGuarMinRate1'.
     * 
     * @return the value of field 'SBGuarMinRate1'.
     */
    public java.math.BigDecimal getSBGuarMinRate1()
    {
        return this._SBGuarMinRate1;
    } //-- java.math.BigDecimal getSBGuarMinRate1() 

    /**
     * Method getSBGuarMinRate2Returns the value of field
     * 'SBGuarMinRate2'.
     * 
     * @return the value of field 'SBGuarMinRate2'.
     */
    public java.math.BigDecimal getSBGuarMinRate2()
    {
        return this._SBGuarMinRate2;
    } //-- java.math.BigDecimal getSBGuarMinRate2() 

    /**
     * Method getSBNumberBucketsReturns the value of field
     * 'SBNumberBuckets'.
     * 
     * @return the value of field 'SBNumberBuckets'.
     */
    public int getSBNumberBuckets()
    {
        return this._SBNumberBuckets;
    } //-- int getSBNumberBuckets() 

    /**
     * Method hasAnnualizedSubBucketPK
     */
    public boolean hasAnnualizedSubBucketPK()
    {
        return this._has_annualizedSubBucketPK;
    } //-- boolean hasAnnualizedSubBucketPK() 

    /**
     * Method hasBucketFK
     */
    public boolean hasBucketFK()
    {
        return this._has_bucketFK;
    } //-- boolean hasBucketFK() 

    /**
     * Method hasEDITTrxFK
     */
    public boolean hasEDITTrxFK()
    {
        return this._has_EDITTrxFK;
    } //-- boolean hasEDITTrxFK() 

    /**
     * Method hasSBNumberBuckets
     */
    public boolean hasSBNumberBuckets()
    {
        return this._has_SBNumberBuckets;
    } //-- boolean hasSBNumberBuckets() 

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
     * Method setAnnualizedSubBucketPKSets the value of field
     * 'annualizedSubBucketPK'.
     * 
     * @param annualizedSubBucketPK the value of field
     * 'annualizedSubBucketPK'.
     */
    public void setAnnualizedSubBucketPK(long annualizedSubBucketPK)
    {
        this._annualizedSubBucketPK = annualizedSubBucketPK;
        
        super.setVoChanged(true);
        this._has_annualizedSubBucketPK = true;
    } //-- void setAnnualizedSubBucketPK(long) 

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
     * Method setSBBaseEndDateSets the value of field
     * 'SBBaseEndDate'.
     * 
     * @param SBBaseEndDate the value of field 'SBBaseEndDate'.
     */
    public void setSBBaseEndDate(java.lang.String SBBaseEndDate)
    {
        this._SBBaseEndDate = SBBaseEndDate;
        
        super.setVoChanged(true);
    } //-- void setSBBaseEndDate(java.lang.String) 

    /**
     * Method setSBBaseRateSets the value of field 'SBBaseRate'.
     * 
     * @param SBBaseRate the value of field 'SBBaseRate'.
     */
    public void setSBBaseRate(java.math.BigDecimal SBBaseRate)
    {
        this._SBBaseRate = SBBaseRate;
        
        super.setVoChanged(true);
    } //-- void setSBBaseRate(java.math.BigDecimal) 

    /**
     * Method setSBCurrentEndDateSets the value of field
     * 'SBCurrentEndDate'.
     * 
     * @param SBCurrentEndDate the value of field 'SBCurrentEndDate'
     */
    public void setSBCurrentEndDate(java.lang.String SBCurrentEndDate)
    {
        this._SBCurrentEndDate = SBCurrentEndDate;
        
        super.setVoChanged(true);
    } //-- void setSBCurrentEndDate(java.lang.String) 

    /**
     * Method setSBCurrentRateSets the value of field
     * 'SBCurrentRate'.
     * 
     * @param SBCurrentRate the value of field 'SBCurrentRate'.
     */
    public void setSBCurrentRate(java.math.BigDecimal SBCurrentRate)
    {
        this._SBCurrentRate = SBCurrentRate;
        
        super.setVoChanged(true);
    } //-- void setSBCurrentRate(java.math.BigDecimal) 

    /**
     * Method setSBEffDateSets the value of field 'SBEffDate'.
     * 
     * @param SBEffDate the value of field 'SBEffDate'.
     */
    public void setSBEffDate(java.lang.String SBEffDate)
    {
        this._SBEffDate = SBEffDate;
        
        super.setVoChanged(true);
    } //-- void setSBEffDate(java.lang.String) 

    /**
     * Method setSBFundValueSets the value of field 'SBFundValue'.
     * 
     * @param SBFundValue the value of field 'SBFundValue'.
     */
    public void setSBFundValue(java.math.BigDecimal SBFundValue)
    {
        this._SBFundValue = SBFundValue;
        
        super.setVoChanged(true);
    } //-- void setSBFundValue(java.math.BigDecimal) 

    /**
     * Method setSBGuarMinEndDate1Sets the value of field
     * 'SBGuarMinEndDate1'.
     * 
     * @param SBGuarMinEndDate1 the value of field
     * 'SBGuarMinEndDate1'.
     */
    public void setSBGuarMinEndDate1(java.lang.String SBGuarMinEndDate1)
    {
        this._SBGuarMinEndDate1 = SBGuarMinEndDate1;
        
        super.setVoChanged(true);
    } //-- void setSBGuarMinEndDate1(java.lang.String) 

    /**
     * Method setSBGuarMinEndDate2Sets the value of field
     * 'SBGuarMinEndDate2'.
     * 
     * @param SBGuarMinEndDate2 the value of field
     * 'SBGuarMinEndDate2'.
     */
    public void setSBGuarMinEndDate2(java.lang.String SBGuarMinEndDate2)
    {
        this._SBGuarMinEndDate2 = SBGuarMinEndDate2;
        
        super.setVoChanged(true);
    } //-- void setSBGuarMinEndDate2(java.lang.String) 

    /**
     * Method setSBGuarMinRate1Sets the value of field
     * 'SBGuarMinRate1'.
     * 
     * @param SBGuarMinRate1 the value of field 'SBGuarMinRate1'.
     */
    public void setSBGuarMinRate1(java.math.BigDecimal SBGuarMinRate1)
    {
        this._SBGuarMinRate1 = SBGuarMinRate1;
        
        super.setVoChanged(true);
    } //-- void setSBGuarMinRate1(java.math.BigDecimal) 

    /**
     * Method setSBGuarMinRate2Sets the value of field
     * 'SBGuarMinRate2'.
     * 
     * @param SBGuarMinRate2 the value of field 'SBGuarMinRate2'.
     */
    public void setSBGuarMinRate2(java.math.BigDecimal SBGuarMinRate2)
    {
        this._SBGuarMinRate2 = SBGuarMinRate2;
        
        super.setVoChanged(true);
    } //-- void setSBGuarMinRate2(java.math.BigDecimal) 

    /**
     * Method setSBNumberBucketsSets the value of field
     * 'SBNumberBuckets'.
     * 
     * @param SBNumberBuckets the value of field 'SBNumberBuckets'.
     */
    public void setSBNumberBuckets(int SBNumberBuckets)
    {
        this._SBNumberBuckets = SBNumberBuckets;
        
        super.setVoChanged(true);
        this._has_SBNumberBuckets = true;
    } //-- void setSBNumberBuckets(int) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AnnualizedSubBucketVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AnnualizedSubBucketVO) Unmarshaller.unmarshal(edit.common.vo.AnnualizedSubBucketVO.class, reader);
    } //-- edit.common.vo.AnnualizedSubBucketVO unmarshal(java.io.Reader) 

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
