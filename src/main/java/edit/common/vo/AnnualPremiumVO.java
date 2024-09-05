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
 * Class AnnualPremiumVO.
 * 
 * @version $Revision$ $Date$
 */
public class AnnualPremiumVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _annualPremiumPK
     */
    private long _annualPremiumPK;

    /**
     * keeps track of state for field: _annualPremiumPK
     */
    private boolean _has_annualPremiumPK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _segmentTypeCT
     */
    private java.lang.String _segmentTypeCT;

    /**
     * Field _annualPremium
     */
    private java.math.BigDecimal _annualPremium;


      //----------------/
     //- Constructors -/
    //----------------/

    public AnnualPremiumVO() {
        super();
    } //-- edit.common.vo.AnnualPremiumVO()


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
        
        if (obj instanceof AnnualPremiumVO) {
        
            AnnualPremiumVO temp = (AnnualPremiumVO)obj;
            if (this._annualPremiumPK != temp._annualPremiumPK)
                return false;
            if (this._has_annualPremiumPK != temp._has_annualPremiumPK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._segmentTypeCT != null) {
                if (temp._segmentTypeCT == null) return false;
                else if (!(this._segmentTypeCT.equals(temp._segmentTypeCT))) 
                    return false;
            }
            else if (temp._segmentTypeCT != null)
                return false;
            if (this._annualPremium != null) {
                if (temp._annualPremium == null) return false;
                else if (!(this._annualPremium.equals(temp._annualPremium))) 
                    return false;
            }
            else if (temp._annualPremium != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAnnualPremiumReturns the value of field
     * 'annualPremium'.
     * 
     * @return the value of field 'annualPremium'.
     */
    public java.math.BigDecimal getAnnualPremium()
    {
        return this._annualPremium;
    } //-- java.math.BigDecimal getAnnualPremium() 

    /**
     * Method getAnnualPremiumPKReturns the value of field
     * 'annualPremiumPK'.
     * 
     * @return the value of field 'annualPremiumPK'.
     */
    public long getAnnualPremiumPK()
    {
        return this._annualPremiumPK;
    } //-- long getAnnualPremiumPK() 

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
     * Method getSegmentTypeCTReturns the value of field
     * 'segmentTypeCT'.
     * 
     * @return the value of field 'segmentTypeCT'.
     */
    public java.lang.String getSegmentTypeCT()
    {
        return this._segmentTypeCT;
    } //-- java.lang.String getSegmentTypeCT() 

    /**
     * Method hasAnnualPremiumPK
     */
    public boolean hasAnnualPremiumPK()
    {
        return this._has_annualPremiumPK;
    } //-- boolean hasAnnualPremiumPK() 

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
     * Method setAnnualPremiumSets the value of field
     * 'annualPremium'.
     * 
     * @param annualPremium the value of field 'annualPremium'.
     */
    public void setAnnualPremium(java.math.BigDecimal annualPremium)
    {
        this._annualPremium = annualPremium;
        
        super.setVoChanged(true);
    } //-- void setAnnualPremium(java.math.BigDecimal) 

    /**
     * Method setAnnualPremiumPKSets the value of field
     * 'annualPremiumPK'.
     * 
     * @param annualPremiumPK the value of field 'annualPremiumPK'.
     */
    public void setAnnualPremiumPK(long annualPremiumPK)
    {
        this._annualPremiumPK = annualPremiumPK;
        
        super.setVoChanged(true);
        this._has_annualPremiumPK = true;
    } //-- void setAnnualPremiumPK(long) 

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
     * Method setSegmentTypeCTSets the value of field
     * 'segmentTypeCT'.
     * 
     * @param segmentTypeCT the value of field 'segmentTypeCT'.
     */
    public void setSegmentTypeCT(java.lang.String segmentTypeCT)
    {
        this._segmentTypeCT = segmentTypeCT;
        
        super.setVoChanged(true);
    } //-- void setSegmentTypeCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AnnualPremiumVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AnnualPremiumVO) Unmarshaller.unmarshal(edit.common.vo.AnnualPremiumVO.class, reader);
    } //-- edit.common.vo.AnnualPremiumVO unmarshal(java.io.Reader) 

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
