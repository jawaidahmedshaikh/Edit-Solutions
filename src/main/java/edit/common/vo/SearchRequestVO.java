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
public class SearchRequestVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _productStructurePK
     */
    private long _productStructurePK;

    /**
     * keeps track of state for field: _productStructurePK
     */
    private boolean _has_productStructurePK;

    /**
     * Field _contractNumber
     */
    private java.lang.String _contractNumber;

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * Field _dateOfBirth
     */
    private java.lang.String _dateOfBirth;

    /**
     * Field _taxId
     */
    private java.lang.String _taxId;

    /**
     * Field _agentId
     */
    private java.lang.String _agentId;


      //----------------/
     //- Constructors -/
    //----------------/

    public SearchRequestVO() {
        super();
    } //-- edit.common.vo.SearchRequestVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteProductStructurePK
     */
    public void deleteProductStructurePK()
    {
        this._has_productStructurePK= false;
    } //-- void deleteProductStructurePK() 

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
        
        if (obj instanceof SearchRequestVO) {
        
            SearchRequestVO temp = (SearchRequestVO)obj;
            if (this._productStructurePK != temp._productStructurePK)
                return false;
            if (this._has_productStructurePK != temp._has_productStructurePK)
                return false;
            if (this._contractNumber != null) {
                if (temp._contractNumber == null) return false;
                else if (!(this._contractNumber.equals(temp._contractNumber))) 
                    return false;
            }
            else if (temp._contractNumber != null)
                return false;
            if (this._name != null) {
                if (temp._name == null) return false;
                else if (!(this._name.equals(temp._name))) 
                    return false;
            }
            else if (temp._name != null)
                return false;
            if (this._dateOfBirth != null) {
                if (temp._dateOfBirth == null) return false;
                else if (!(this._dateOfBirth.equals(temp._dateOfBirth))) 
                    return false;
            }
            else if (temp._dateOfBirth != null)
                return false;
            if (this._taxId != null) {
                if (temp._taxId == null) return false;
                else if (!(this._taxId.equals(temp._taxId))) 
                    return false;
            }
            else if (temp._taxId != null)
                return false;
            if (this._agentId != null) {
                if (temp._agentId == null) return false;
                else if (!(this._agentId.equals(temp._agentId))) 
                    return false;
            }
            else if (temp._agentId != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentIdReturns the value of field 'agentId'.
     * 
     * @return the value of field 'agentId'.
     */
    public java.lang.String getAgentId()
    {
        return this._agentId;
    } //-- java.lang.String getAgentId() 

    /**
     * Method getContractNumberReturns the value of field
     * 'contractNumber'.
     * 
     * @return the value of field 'contractNumber'.
     */
    public java.lang.String getContractNumber()
    {
        return this._contractNumber;
    } //-- java.lang.String getContractNumber() 

    /**
     * Method getDateOfBirthReturns the value of field
     * 'dateOfBirth'.
     * 
     * @return the value of field 'dateOfBirth'.
     */
    public java.lang.String getDateOfBirth()
    {
        return this._dateOfBirth;
    } //-- java.lang.String getDateOfBirth() 

    /**
     * Method getNameReturns the value of field 'name'.
     * 
     * @return the value of field 'name'.
     */
    public java.lang.String getName()
    {
        return this._name;
    } //-- java.lang.String getName() 

    /**
     * Method getProductStructurePKReturns the value of field
     * 'productStructurePK'.
     * 
     * @return the value of field 'productStructurePK'.
     */
    public long getProductStructurePK()
    {
        return this._productStructurePK;
    } //-- long getProductStructurePK() 

    /**
     * Method getTaxIdReturns the value of field 'taxId'.
     * 
     * @return the value of field 'taxId'.
     */
    public java.lang.String getTaxId()
    {
        return this._taxId;
    } //-- java.lang.String getTaxId() 

    /**
     * Method hasProductStructurePK
     */
    public boolean hasProductStructurePK()
    {
        return this._has_productStructurePK;
    } //-- boolean hasProductStructurePK() 

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
     * Method setAgentIdSets the value of field 'agentId'.
     * 
     * @param agentId the value of field 'agentId'.
     */
    public void setAgentId(java.lang.String agentId)
    {
        this._agentId = agentId;
        
        super.setVoChanged(true);
    } //-- void setAgentId(java.lang.String) 

    /**
     * Method setContractNumberSets the value of field
     * 'contractNumber'.
     * 
     * @param contractNumber the value of field 'contractNumber'.
     */
    public void setContractNumber(java.lang.String contractNumber)
    {
        this._contractNumber = contractNumber;
        
        super.setVoChanged(true);
    } //-- void setContractNumber(java.lang.String) 

    /**
     * Method setDateOfBirthSets the value of field 'dateOfBirth'.
     * 
     * @param dateOfBirth the value of field 'dateOfBirth'.
     */
    public void setDateOfBirth(java.lang.String dateOfBirth)
    {
        this._dateOfBirth = dateOfBirth;
        
        super.setVoChanged(true);
    } //-- void setDateOfBirth(java.lang.String) 

    /**
     * Method setNameSets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.String name)
    {
        this._name = name;
        
        super.setVoChanged(true);
    } //-- void setName(java.lang.String) 

    /**
     * Method setProductStructurePKSets the value of field
     * 'productStructurePK'.
     * 
     * @param productStructurePK the value of field
     * 'productStructurePK'.
     */
    public void setProductStructurePK(long productStructurePK)
    {
        this._productStructurePK = productStructurePK;
        
        super.setVoChanged(true);
        this._has_productStructurePK = true;
    } //-- void setProductStructurePK(long) 

    /**
     * Method setTaxIdSets the value of field 'taxId'.
     * 
     * @param taxId the value of field 'taxId'.
     */
    public void setTaxId(java.lang.String taxId)
    {
        this._taxId = taxId;
        
        super.setVoChanged(true);
    } //-- void setTaxId(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.SearchRequestVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SearchRequestVO) Unmarshaller.unmarshal(edit.common.vo.SearchRequestVO.class, reader);
    } //-- edit.common.vo.SearchRequestVO unmarshal(java.io.Reader) 

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
