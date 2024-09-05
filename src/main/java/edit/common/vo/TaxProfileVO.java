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
 * Class TaxProfileVO.
 * 
 * @version $Revision$ $Date$
 */
public class TaxProfileVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _taxProfilePK
     */
    private long _taxProfilePK;

    /**
     * keeps track of state for field: _taxProfilePK
     */
    private boolean _has_taxProfilePK;

    /**
     * Field _taxInformationFK
     */
    private long _taxInformationFK;

    /**
     * keeps track of state for field: _taxInformationFK
     */
    private boolean _has_taxInformationFK;

    /**
     * Field _taxFilingStatusCT
     */
    private java.lang.String _taxFilingStatusCT;

    /**
     * Field _exemptions
     */
    private java.lang.String _exemptions;

    /**
     * Field _taxIndicatorCT
     */
    private java.lang.String _taxIndicatorCT;

    /**
     * Field _ficaIndicator
     */
    private java.lang.String _ficaIndicator;

    /**
     * Field _overrideStatus
     */
    private java.lang.String _overrideStatus;


      //----------------/
     //- Constructors -/
    //----------------/

    public TaxProfileVO() {
        super();
    } //-- edit.common.vo.TaxProfileVO()


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
        
        if (obj instanceof TaxProfileVO) {
        
            TaxProfileVO temp = (TaxProfileVO)obj;
            if (this._taxProfilePK != temp._taxProfilePK)
                return false;
            if (this._has_taxProfilePK != temp._has_taxProfilePK)
                return false;
            if (this._taxInformationFK != temp._taxInformationFK)
                return false;
            if (this._has_taxInformationFK != temp._has_taxInformationFK)
                return false;
            if (this._taxFilingStatusCT != null) {
                if (temp._taxFilingStatusCT == null) return false;
                else if (!(this._taxFilingStatusCT.equals(temp._taxFilingStatusCT))) 
                    return false;
            }
            else if (temp._taxFilingStatusCT != null)
                return false;
            if (this._exemptions != null) {
                if (temp._exemptions == null) return false;
                else if (!(this._exemptions.equals(temp._exemptions))) 
                    return false;
            }
            else if (temp._exemptions != null)
                return false;
            if (this._taxIndicatorCT != null) {
                if (temp._taxIndicatorCT == null) return false;
                else if (!(this._taxIndicatorCT.equals(temp._taxIndicatorCT))) 
                    return false;
            }
            else if (temp._taxIndicatorCT != null)
                return false;
            if (this._ficaIndicator != null) {
                if (temp._ficaIndicator == null) return false;
                else if (!(this._ficaIndicator.equals(temp._ficaIndicator))) 
                    return false;
            }
            else if (temp._ficaIndicator != null)
                return false;
            if (this._overrideStatus != null) {
                if (temp._overrideStatus == null) return false;
                else if (!(this._overrideStatus.equals(temp._overrideStatus))) 
                    return false;
            }
            else if (temp._overrideStatus != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getExemptionsReturns the value of field 'exemptions'.
     * 
     * @return the value of field 'exemptions'.
     */
    public java.lang.String getExemptions()
    {
        return this._exemptions;
    } //-- java.lang.String getExemptions() 

    /**
     * Method getFicaIndicatorReturns the value of field
     * 'ficaIndicator'.
     * 
     * @return the value of field 'ficaIndicator'.
     */
    public java.lang.String getFicaIndicator()
    {
        return this._ficaIndicator;
    } //-- java.lang.String getFicaIndicator() 

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
     * Method getTaxFilingStatusCTReturns the value of field
     * 'taxFilingStatusCT'.
     * 
     * @return the value of field 'taxFilingStatusCT'.
     */
    public java.lang.String getTaxFilingStatusCT()
    {
        return this._taxFilingStatusCT;
    } //-- java.lang.String getTaxFilingStatusCT() 

    /**
     * Method getTaxIndicatorCTReturns the value of field
     * 'taxIndicatorCT'.
     * 
     * @return the value of field 'taxIndicatorCT'.
     */
    public java.lang.String getTaxIndicatorCT()
    {
        return this._taxIndicatorCT;
    } //-- java.lang.String getTaxIndicatorCT() 

    /**
     * Method getTaxInformationFKReturns the value of field
     * 'taxInformationFK'.
     * 
     * @return the value of field 'taxInformationFK'.
     */
    public long getTaxInformationFK()
    {
        return this._taxInformationFK;
    } //-- long getTaxInformationFK() 

    /**
     * Method getTaxProfilePKReturns the value of field
     * 'taxProfilePK'.
     * 
     * @return the value of field 'taxProfilePK'.
     */
    public long getTaxProfilePK()
    {
        return this._taxProfilePK;
    } //-- long getTaxProfilePK() 

    /**
     * Method hasTaxInformationFK
     */
    public boolean hasTaxInformationFK()
    {
        return this._has_taxInformationFK;
    } //-- boolean hasTaxInformationFK() 

    /**
     * Method hasTaxProfilePK
     */
    public boolean hasTaxProfilePK()
    {
        return this._has_taxProfilePK;
    } //-- boolean hasTaxProfilePK() 

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
     * Method setExemptionsSets the value of field 'exemptions'.
     * 
     * @param exemptions the value of field 'exemptions'.
     */
    public void setExemptions(java.lang.String exemptions)
    {
        this._exemptions = exemptions;
        
        super.setVoChanged(true);
    } //-- void setExemptions(java.lang.String) 

    /**
     * Method setFicaIndicatorSets the value of field
     * 'ficaIndicator'.
     * 
     * @param ficaIndicator the value of field 'ficaIndicator'.
     */
    public void setFicaIndicator(java.lang.String ficaIndicator)
    {
        this._ficaIndicator = ficaIndicator;
        
        super.setVoChanged(true);
    } //-- void setFicaIndicator(java.lang.String) 

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
     * Method setTaxFilingStatusCTSets the value of field
     * 'taxFilingStatusCT'.
     * 
     * @param taxFilingStatusCT the value of field
     * 'taxFilingStatusCT'.
     */
    public void setTaxFilingStatusCT(java.lang.String taxFilingStatusCT)
    {
        this._taxFilingStatusCT = taxFilingStatusCT;
        
        super.setVoChanged(true);
    } //-- void setTaxFilingStatusCT(java.lang.String) 

    /**
     * Method setTaxIndicatorCTSets the value of field
     * 'taxIndicatorCT'.
     * 
     * @param taxIndicatorCT the value of field 'taxIndicatorCT'.
     */
    public void setTaxIndicatorCT(java.lang.String taxIndicatorCT)
    {
        this._taxIndicatorCT = taxIndicatorCT;
        
        super.setVoChanged(true);
    } //-- void setTaxIndicatorCT(java.lang.String) 

    /**
     * Method setTaxInformationFKSets the value of field
     * 'taxInformationFK'.
     * 
     * @param taxInformationFK the value of field 'taxInformationFK'
     */
    public void setTaxInformationFK(long taxInformationFK)
    {
        this._taxInformationFK = taxInformationFK;
        
        super.setVoChanged(true);
        this._has_taxInformationFK = true;
    } //-- void setTaxInformationFK(long) 

    /**
     * Method setTaxProfilePKSets the value of field
     * 'taxProfilePK'.
     * 
     * @param taxProfilePK the value of field 'taxProfilePK'.
     */
    public void setTaxProfilePK(long taxProfilePK)
    {
        this._taxProfilePK = taxProfilePK;
        
        super.setVoChanged(true);
        this._has_taxProfilePK = true;
    } //-- void setTaxProfilePK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.TaxProfileVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.TaxProfileVO) Unmarshaller.unmarshal(edit.common.vo.TaxProfileVO.class, reader);
    } //-- edit.common.vo.TaxProfileVO unmarshal(java.io.Reader) 

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
