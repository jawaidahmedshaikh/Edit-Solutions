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
 * Class ContractClientAllocationOvrdVO.
 * 
 * @version $Revision$ $Date$
 */
public class ContractClientAllocationOvrdVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _contractClientAllocationOvrdPK
     */
    private long _contractClientAllocationOvrdPK;

    /**
     * keeps track of state for field:
     * _contractClientAllocationOvrdPK
     */
    private boolean _has_contractClientAllocationOvrdPK;

    /**
     * Field _clientSetupFK
     */
    private long _clientSetupFK;

    /**
     * keeps track of state for field: _clientSetupFK
     */
    private boolean _has_clientSetupFK;

    /**
     * Field _contractClientAllocationFK
     */
    private long _contractClientAllocationFK;

    /**
     * keeps track of state for field: _contractClientAllocationFK
     */
    private boolean _has_contractClientAllocationFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public ContractClientAllocationOvrdVO() {
        super();
    } //-- edit.common.vo.ContractClientAllocationOvrdVO()


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
        
        if (obj instanceof ContractClientAllocationOvrdVO) {
        
            ContractClientAllocationOvrdVO temp = (ContractClientAllocationOvrdVO)obj;
            if (this._contractClientAllocationOvrdPK != temp._contractClientAllocationOvrdPK)
                return false;
            if (this._has_contractClientAllocationOvrdPK != temp._has_contractClientAllocationOvrdPK)
                return false;
            if (this._clientSetupFK != temp._clientSetupFK)
                return false;
            if (this._has_clientSetupFK != temp._has_clientSetupFK)
                return false;
            if (this._contractClientAllocationFK != temp._contractClientAllocationFK)
                return false;
            if (this._has_contractClientAllocationFK != temp._has_contractClientAllocationFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getClientSetupFKReturns the value of field
     * 'clientSetupFK'.
     * 
     * @return the value of field 'clientSetupFK'.
     */
    public long getClientSetupFK()
    {
        return this._clientSetupFK;
    } //-- long getClientSetupFK() 

    /**
     * Method getContractClientAllocationFKReturns the value of
     * field 'contractClientAllocationFK'.
     * 
     * @return the value of field 'contractClientAllocationFK'.
     */
    public long getContractClientAllocationFK()
    {
        return this._contractClientAllocationFK;
    } //-- long getContractClientAllocationFK() 

    /**
     * Method getContractClientAllocationOvrdPKReturns the value of
     * field 'contractClientAllocationOvrdPK'.
     * 
     * @return the value of field 'contractClientAllocationOvrdPK'.
     */
    public long getContractClientAllocationOvrdPK()
    {
        return this._contractClientAllocationOvrdPK;
    } //-- long getContractClientAllocationOvrdPK() 

    /**
     * Method hasClientSetupFK
     */
    public boolean hasClientSetupFK()
    {
        return this._has_clientSetupFK;
    } //-- boolean hasClientSetupFK() 

    /**
     * Method hasContractClientAllocationFK
     */
    public boolean hasContractClientAllocationFK()
    {
        return this._has_contractClientAllocationFK;
    } //-- boolean hasContractClientAllocationFK() 

    /**
     * Method hasContractClientAllocationOvrdPK
     */
    public boolean hasContractClientAllocationOvrdPK()
    {
        return this._has_contractClientAllocationOvrdPK;
    } //-- boolean hasContractClientAllocationOvrdPK() 

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
     * Method setClientSetupFKSets the value of field
     * 'clientSetupFK'.
     * 
     * @param clientSetupFK the value of field 'clientSetupFK'.
     */
    public void setClientSetupFK(long clientSetupFK)
    {
        this._clientSetupFK = clientSetupFK;
        
        super.setVoChanged(true);
        this._has_clientSetupFK = true;
    } //-- void setClientSetupFK(long) 

    /**
     * Method setContractClientAllocationFKSets the value of field
     * 'contractClientAllocationFK'.
     * 
     * @param contractClientAllocationFK the value of field
     * 'contractClientAllocationFK'.
     */
    public void setContractClientAllocationFK(long contractClientAllocationFK)
    {
        this._contractClientAllocationFK = contractClientAllocationFK;
        
        super.setVoChanged(true);
        this._has_contractClientAllocationFK = true;
    } //-- void setContractClientAllocationFK(long) 

    /**
     * Method setContractClientAllocationOvrdPKSets the value of
     * field 'contractClientAllocationOvrdPK'.
     * 
     * @param contractClientAllocationOvrdPK the value of field
     * 'contractClientAllocationOvrdPK'.
     */
    public void setContractClientAllocationOvrdPK(long contractClientAllocationOvrdPK)
    {
        this._contractClientAllocationOvrdPK = contractClientAllocationOvrdPK;
        
        super.setVoChanged(true);
        this._has_contractClientAllocationOvrdPK = true;
    } //-- void setContractClientAllocationOvrdPK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ContractClientAllocationOvrdVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ContractClientAllocationOvrdVO) Unmarshaller.unmarshal(edit.common.vo.ContractClientAllocationOvrdVO.class, reader);
    } //-- edit.common.vo.ContractClientAllocationOvrdVO unmarshal(java.io.Reader) 

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
