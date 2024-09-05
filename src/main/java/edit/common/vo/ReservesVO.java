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
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class ReservesVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _reservesType
     */
    private java.lang.String _reservesType;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _companyStructureFK
     */
    private long _companyStructureFK;

    /**
     * keeps track of state for field: _companyStructureFK
     */
    private boolean _has_companyStructureFK;

    /**
     * Field _optionCode
     */
    private java.lang.String _optionCode;

    /**
     * Field _statReserveAmount
     */
    private double _statReserveAmount;

    /**
     * keeps track of state for field: _statReserveAmount
     */
    private boolean _has_statReserveAmount;

    /**
     * Field _taxReserveAmount
     */
    private double _taxReserveAmount;

    /**
     * keeps track of state for field: _taxReserveAmount
     */
    private boolean _has_taxReserveAmount;

    /**
     * Field _GAAPReserveAmount
     */
    private double _GAAPReserveAmount;

    /**
     * keeps track of state for field: _GAAPReserveAmount
     */
    private boolean _has_GAAPReserveAmount;


      //----------------/
     //- Constructors -/
    //----------------/

    public ReservesVO() {
        super();
    } //-- edit.common.vo.ReservesVO()


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
        
        if (obj instanceof ReservesVO) {
        
            ReservesVO temp = (ReservesVO)obj;
            if (this._reservesType != null) {
                if (temp._reservesType == null) return false;
                else if (!(this._reservesType.equals(temp._reservesType))) 
                    return false;
            }
            else if (temp._reservesType != null)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._companyStructureFK != temp._companyStructureFK)
                return false;
            if (this._has_companyStructureFK != temp._has_companyStructureFK)
                return false;
            if (this._optionCode != null) {
                if (temp._optionCode == null) return false;
                else if (!(this._optionCode.equals(temp._optionCode))) 
                    return false;
            }
            else if (temp._optionCode != null)
                return false;
            if (this._statReserveAmount != temp._statReserveAmount)
                return false;
            if (this._has_statReserveAmount != temp._has_statReserveAmount)
                return false;
            if (this._taxReserveAmount != temp._taxReserveAmount)
                return false;
            if (this._has_taxReserveAmount != temp._has_taxReserveAmount)
                return false;
            if (this._GAAPReserveAmount != temp._GAAPReserveAmount)
                return false;
            if (this._has_GAAPReserveAmount != temp._has_GAAPReserveAmount)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCompanyStructureFKReturns the value of field
     * 'companyStructureFK'.
     * 
     * @return the value of field 'companyStructureFK'.
     */
    public long getCompanyStructureFK()
    {
        return this._companyStructureFK;
    } //-- long getCompanyStructureFK() 

    /**
     * Method getGAAPReserveAmountReturns the value of field
     * 'GAAPReserveAmount'.
     * 
     * @return the value of field 'GAAPReserveAmount'.
     */
    public double getGAAPReserveAmount()
    {
        return this._GAAPReserveAmount;
    } //-- double getGAAPReserveAmount() 

    /**
     * Method getOptionCodeReturns the value of field 'optionCode'.
     * 
     * @return the value of field 'optionCode'.
     */
    public java.lang.String getOptionCode()
    {
        return this._optionCode;
    } //-- java.lang.String getOptionCode() 

    /**
     * Method getReservesTypeReturns the value of field
     * 'reservesType'.
     * 
     * @return the value of field 'reservesType'.
     */
    public java.lang.String getReservesType()
    {
        return this._reservesType;
    } //-- java.lang.String getReservesType() 

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
     * Method getStatReserveAmountReturns the value of field
     * 'statReserveAmount'.
     * 
     * @return the value of field 'statReserveAmount'.
     */
    public double getStatReserveAmount()
    {
        return this._statReserveAmount;
    } //-- double getStatReserveAmount() 

    /**
     * Method getTaxReserveAmountReturns the value of field
     * 'taxReserveAmount'.
     * 
     * @return the value of field 'taxReserveAmount'.
     */
    public double getTaxReserveAmount()
    {
        return this._taxReserveAmount;
    } //-- double getTaxReserveAmount() 

    /**
     * Method hasCompanyStructureFK
     */
    public boolean hasCompanyStructureFK()
    {
        return this._has_companyStructureFK;
    } //-- boolean hasCompanyStructureFK() 

    /**
     * Method hasGAAPReserveAmount
     */
    public boolean hasGAAPReserveAmount()
    {
        return this._has_GAAPReserveAmount;
    } //-- boolean hasGAAPReserveAmount() 

    /**
     * Method hasSegmentFK
     */
    public boolean hasSegmentFK()
    {
        return this._has_segmentFK;
    } //-- boolean hasSegmentFK() 

    /**
     * Method hasStatReserveAmount
     */
    public boolean hasStatReserveAmount()
    {
        return this._has_statReserveAmount;
    } //-- boolean hasStatReserveAmount() 

    /**
     * Method hasTaxReserveAmount
     */
    public boolean hasTaxReserveAmount()
    {
        return this._has_taxReserveAmount;
    } //-- boolean hasTaxReserveAmount() 

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
     * Method setCompanyStructureFKSets the value of field
     * 'companyStructureFK'.
     * 
     * @param companyStructureFK the value of field
     * 'companyStructureFK'.
     */
    public void setCompanyStructureFK(long companyStructureFK)
    {
        this._companyStructureFK = companyStructureFK;
        
        super.setVoChanged(true);
        this._has_companyStructureFK = true;
    } //-- void setCompanyStructureFK(long) 

    /**
     * Method setGAAPReserveAmountSets the value of field
     * 'GAAPReserveAmount'.
     * 
     * @param GAAPReserveAmount the value of field
     * 'GAAPReserveAmount'.
     */
    public void setGAAPReserveAmount(double GAAPReserveAmount)
    {
        this._GAAPReserveAmount = GAAPReserveAmount;
        
        super.setVoChanged(true);
        this._has_GAAPReserveAmount = true;
    } //-- void setGAAPReserveAmount(double) 

    /**
     * Method setOptionCodeSets the value of field 'optionCode'.
     * 
     * @param optionCode the value of field 'optionCode'.
     */
    public void setOptionCode(java.lang.String optionCode)
    {
        this._optionCode = optionCode;
        
        super.setVoChanged(true);
    } //-- void setOptionCode(java.lang.String) 

    /**
     * Method setReservesTypeSets the value of field
     * 'reservesType'.
     * 
     * @param reservesType the value of field 'reservesType'.
     */
    public void setReservesType(java.lang.String reservesType)
    {
        this._reservesType = reservesType;
        
        super.setVoChanged(true);
    } //-- void setReservesType(java.lang.String) 

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
     * Method setStatReserveAmountSets the value of field
     * 'statReserveAmount'.
     * 
     * @param statReserveAmount the value of field
     * 'statReserveAmount'.
     */
    public void setStatReserveAmount(double statReserveAmount)
    {
        this._statReserveAmount = statReserveAmount;
        
        super.setVoChanged(true);
        this._has_statReserveAmount = true;
    } //-- void setStatReserveAmount(double) 

    /**
     * Method setTaxReserveAmountSets the value of field
     * 'taxReserveAmount'.
     * 
     * @param taxReserveAmount the value of field 'taxReserveAmount'
     */
    public void setTaxReserveAmount(double taxReserveAmount)
    {
        this._taxReserveAmount = taxReserveAmount;
        
        super.setVoChanged(true);
        this._has_taxReserveAmount = true;
    } //-- void setTaxReserveAmount(double) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ReservesVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ReservesVO) Unmarshaller.unmarshal(edit.common.vo.ReservesVO.class, reader);
    } //-- edit.common.vo.ReservesVO unmarshal(java.io.Reader) 

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
