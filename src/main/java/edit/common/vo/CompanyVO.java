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
 * Class CompanyVO.
 * 
 * @version $Revision$ $Date$
 */
public class CompanyVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _companyPK
     */
    private long _companyPK;

    /**
     * keeps track of state for field: _companyPK
     */
    private boolean _has_companyPK;

    /**
     * Field _companyName
     */
    private java.lang.String _companyName;

    /**
     * Field _policyNumberPrefix
     */
    private java.lang.String _policyNumberPrefix;

    /**
     * Field _policyNumberSuffix
     */
    private java.lang.String _policyNumberSuffix;
    
    /**
     * Field _policyNumberSequenceNumber
     */
    private int _policyNumberSequenceNumber;

    /**
     * keeps track of state for field: _policyNumberSequenceNumber
     */
    private boolean _has_policyNumberSequenceNumber;

    /**
     * Field _policySequenceLength
     */
    private int _policySequenceLength;

    /**
     * keeps track of state for field: _policySequenceLength
     */
    private boolean _has_policySequenceLength;

    /**
     * Field _billingCompanyNumber
     */
    private java.lang.String _billingCompanyNumber;


      //----------------/
     //- Constructors -/
    //----------------/

    public CompanyVO() {
        super();
    } //-- edit.common.vo.CompanyVO()


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
        
        if (obj instanceof CompanyVO) {
        
            CompanyVO temp = (CompanyVO)obj;
            if (this._companyPK != temp._companyPK)
                return false;
            if (this._has_companyPK != temp._has_companyPK)
                return false;
            if (this._companyName != null) {
                if (temp._companyName == null) return false;
                else if (!(this._companyName.equals(temp._companyName))) 
                    return false;
            }
            else if (temp._companyName != null)
                return false;
            if (this._policyNumberPrefix != null) {
                if (temp._policyNumberPrefix == null) return false;
                else if (!(this._policyNumberPrefix.equals(temp._policyNumberPrefix))) 
                    return false;
            }
            else if (temp._policyNumberPrefix != null)
                return false;
            
            if (this._policyNumberSuffix != null) {
                if (temp._policyNumberSuffix == null) return false;
                else if (!(this._policyNumberSuffix.equals(temp._policyNumberSuffix))) 
                    return false;
            }
            else if (temp._policyNumberSuffix != null)
                return false;
            
            if (this._policyNumberSequenceNumber != temp._policyNumberSequenceNumber)
                return false;
            if (this._has_policyNumberSequenceNumber != temp._has_policyNumberSequenceNumber)
                return false;
            if (this._policySequenceLength != temp._policySequenceLength)
                return false;
            if (this._has_policySequenceLength != temp._has_policySequenceLength)
                return false;
            if (this._billingCompanyNumber != null) {
                if (temp._billingCompanyNumber == null) return false;
                else if (!(this._billingCompanyNumber.equals(temp._billingCompanyNumber))) 
                    return false;
            }
            else if (temp._billingCompanyNumber != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBillingCompanyNumberReturns the value of field
     * 'billingCompanyNumber'.
     * 
     * @return the value of field 'billingCompanyNumber'.
     */
    public java.lang.String getBillingCompanyNumber()
    {
        return this._billingCompanyNumber;
    } //-- java.lang.String getBillingCompanyNumber() 

    /**
     * Method getCompanyNameReturns the value of field
     * 'companyName'.
     * 
     * @return the value of field 'companyName'.
     */
    public java.lang.String getCompanyName()
    {
        return this._companyName;
    } //-- java.lang.String getCompanyName() 

    /**
     * Method getCompanyPKReturns the value of field 'companyPK'.
     * 
     * @return the value of field 'companyPK'.
     */
    public long getCompanyPK()
    {
        return this._companyPK;
    } //-- long getCompanyPK() 

    /**
     * Method getPolicyNumberPrefixReturns the value of field
     * 'policyNumberPrefix'.
     * 
     * @return the value of field 'policyNumberPrefix'.
     */
    public java.lang.String getPolicyNumberPrefix()
    {
        return this._policyNumberPrefix;
    } //-- java.lang.String getPolicyNumberPrefix() 

    public java.lang.String getPolicyNumberSuffix()
    {
        return this._policyNumberSuffix;
    }
    
    /**
     * Method getPolicyNumberSequenceNumberReturns the value of
     * field 'policyNumberSequenceNumber'.
     * 
     * @return the value of field 'policyNumberSequenceNumber'.
     */
    public int getPolicyNumberSequenceNumber()
    {
        return this._policyNumberSequenceNumber;
    } //-- int getPolicyNumberSequenceNumber() 

    /**
     * Method getPolicySequenceLengthReturns the value of field
     * 'policySequenceLength'.
     * 
     * @return the value of field 'policySequenceLength'.
     */
    public int getPolicySequenceLength()
    {
        return this._policySequenceLength;
    } //-- int getPolicySequenceLength() 

    /**
     * Method hasCompanyPK
     */
    public boolean hasCompanyPK()
    {
        return this._has_companyPK;
    } //-- boolean hasCompanyPK() 

    /**
     * Method hasPolicyNumberSequenceNumber
     */
    public boolean hasPolicyNumberSequenceNumber()
    {
        return this._has_policyNumberSequenceNumber;
    } //-- boolean hasPolicyNumberSequenceNumber() 

    /**
     * Method hasPolicySequenceLength
     */
    public boolean hasPolicySequenceLength()
    {
        return this._has_policySequenceLength;
    } //-- boolean hasPolicySequenceLength() 

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
     * Method setBillingCompanyNumberSets the value of field
     * 'billingCompanyNumber'.
     * 
     * @param billingCompanyNumber the value of field
     * 'billingCompanyNumber'.
     */
    public void setBillingCompanyNumber(java.lang.String billingCompanyNumber)
    {
        this._billingCompanyNumber = billingCompanyNumber;
        
        super.setVoChanged(true);
    } //-- void setBillingCompanyNumber(java.lang.String) 

    /**
     * Method setCompanyNameSets the value of field 'companyName'.
     * 
     * @param companyName the value of field 'companyName'.
     */
    public void setCompanyName(java.lang.String companyName)
    {
        this._companyName = companyName;
        
        super.setVoChanged(true);
    } //-- void setCompanyName(java.lang.String) 

    /**
     * Method setCompanyPKSets the value of field 'companyPK'.
     * 
     * @param companyPK the value of field 'companyPK'.
     */
    public void setCompanyPK(long companyPK)
    {
        this._companyPK = companyPK;
        
        super.setVoChanged(true);
        this._has_companyPK = true;
    } //-- void setCompanyPK(long) 

    /**
     * Method setPolicyNumberPrefixSets the value of field
     * 'policyNumberPrefix'.
     * 
     * @param policyNumberPrefix the value of field
     * 'policyNumberPrefix'.
     */
    public void setPolicyNumberPrefix(java.lang.String policyNumberPrefix)
    {
        this._policyNumberPrefix = policyNumberPrefix;
        
        super.setVoChanged(true);
    } //-- void setPolicyNumberPrefix(java.lang.String) 

    public void setPolicyNumberSuffix(java.lang.String policyNumberSuffix)
    {
        this._policyNumberSuffix = policyNumberSuffix;
        
        super.setVoChanged(true);
    }
    
    /**
     * Method setPolicyNumberSequenceNumberSets the value of field
     * 'policyNumberSequenceNumber'.
     * 
     * @param policyNumberSequenceNumber the value of field
     * 'policyNumberSequenceNumber'.
     */
    public void setPolicyNumberSequenceNumber(int policyNumberSequenceNumber)
    {
        this._policyNumberSequenceNumber = policyNumberSequenceNumber;
        
        super.setVoChanged(true);
        this._has_policyNumberSequenceNumber = true;
    } //-- void setPolicyNumberSequenceNumber(int) 

    /**
     * Method setPolicySequenceLengthSets the value of field
     * 'policySequenceLength'.
     * 
     * @param policySequenceLength the value of field
     * 'policySequenceLength'.
     */
    public void setPolicySequenceLength(int policySequenceLength)
    {
        this._policySequenceLength = policySequenceLength;
        
        super.setVoChanged(true);
        this._has_policySequenceLength = true;
    } //-- void setPolicySequenceLength(int) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CompanyVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CompanyVO) Unmarshaller.unmarshal(edit.common.vo.CompanyVO.class, reader);
    } //-- edit.common.vo.CompanyVO unmarshal(java.io.Reader) 

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
