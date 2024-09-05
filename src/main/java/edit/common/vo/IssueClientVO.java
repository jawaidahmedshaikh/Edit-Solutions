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
 * The set of 4 clients (1. lowest level agent, 2. annuitant, 3.
 * owner, and 4. secondary owner) and their primary addresses
 * 
 * @version $Revision$ $Date$
 */
public class IssueClientVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _clientDetailVO
     */
    private edit.common.vo.ClientDetailVO _clientDetailVO;

    /**
     * Field _roleTypeCT
     */
    private java.lang.String _roleTypeCT;

    /**
     * Field _contractClientVO
     */
    private edit.common.vo.ContractClientVO _contractClientVO;

    /**
     * Field _clientPK
     */
    private long _clientPK;

    /**
     * keeps track of state for field: _clientPK
     */
    private boolean _has_clientPK;

    /**
     * Field _agentTypeCT
     */
    private java.lang.String _agentTypeCT;

    /**
     * Field _agentAllocation
     */
    private java.math.BigDecimal _agentAllocation;


      //----------------/
     //- Constructors -/
    //----------------/

    public IssueClientVO() {
        super();
    } //-- edit.common.vo.IssueClientVO()


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
        
        if (obj instanceof IssueClientVO) {
        
            IssueClientVO temp = (IssueClientVO)obj;
            if (this._clientDetailVO != null) {
                if (temp._clientDetailVO == null) return false;
                else if (!(this._clientDetailVO.equals(temp._clientDetailVO))) 
                    return false;
            }
            else if (temp._clientDetailVO != null)
                return false;
            if (this._roleTypeCT != null) {
                if (temp._roleTypeCT == null) return false;
                else if (!(this._roleTypeCT.equals(temp._roleTypeCT))) 
                    return false;
            }
            else if (temp._roleTypeCT != null)
                return false;
            if (this._contractClientVO != null) {
                if (temp._contractClientVO == null) return false;
                else if (!(this._contractClientVO.equals(temp._contractClientVO))) 
                    return false;
            }
            else if (temp._contractClientVO != null)
                return false;
            if (this._clientPK != temp._clientPK)
                return false;
            if (this._has_clientPK != temp._has_clientPK)
                return false;
            if (this._agentTypeCT != null) {
                if (temp._agentTypeCT == null) return false;
                else if (!(this._agentTypeCT.equals(temp._agentTypeCT))) 
                    return false;
            }
            else if (temp._agentTypeCT != null)
                return false;
            if (this._agentAllocation != null) {
                if (temp._agentAllocation == null) return false;
                else if (!(this._agentAllocation.equals(temp._agentAllocation))) 
                    return false;
            }
            else if (temp._agentAllocation != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentAllocationReturns the value of field
     * 'agentAllocation'.
     * 
     * @return the value of field 'agentAllocation'.
     */
    public java.math.BigDecimal getAgentAllocation()
    {
        return this._agentAllocation;
    } //-- java.math.BigDecimal getAgentAllocation() 

    /**
     * Method getAgentTypeCTReturns the value of field
     * 'agentTypeCT'.
     * 
     * @return the value of field 'agentTypeCT'.
     */
    public java.lang.String getAgentTypeCT()
    {
        return this._agentTypeCT;
    } //-- java.lang.String getAgentTypeCT() 

    /**
     * Method getClientDetailVOReturns the value of field
     * 'clientDetailVO'.
     * 
     * @return the value of field 'clientDetailVO'.
     */
    public edit.common.vo.ClientDetailVO getClientDetailVO()
    {
        return this._clientDetailVO;
    } //-- edit.common.vo.ClientDetailVO getClientDetailVO() 

    /**
     * Method getClientPKReturns the value of field 'clientPK'.
     * 
     * @return the value of field 'clientPK'.
     */
    public long getClientPK()
    {
        return this._clientPK;
    } //-- long getClientPK() 

    /**
     * Method getContractClientVOReturns the value of field
     * 'contractClientVO'.
     * 
     * @return the value of field 'contractClientVO'.
     */
    public edit.common.vo.ContractClientVO getContractClientVO()
    {
        return this._contractClientVO;
    } //-- edit.common.vo.ContractClientVO getContractClientVO() 

    /**
     * Method getRoleTypeCTReturns the value of field 'roleTypeCT'.
     * 
     * @return the value of field 'roleTypeCT'.
     */
    public java.lang.String getRoleTypeCT()
    {
        return this._roleTypeCT;
    } //-- java.lang.String getRoleTypeCT() 

    /**
     * Method hasClientPK
     */
    public boolean hasClientPK()
    {
        return this._has_clientPK;
    } //-- boolean hasClientPK() 

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
     * Method setAgentAllocationSets the value of field
     * 'agentAllocation'.
     * 
     * @param agentAllocation the value of field 'agentAllocation'.
     */
    public void setAgentAllocation(java.math.BigDecimal agentAllocation)
    {
        this._agentAllocation = agentAllocation;
        
        super.setVoChanged(true);
    } //-- void setAgentAllocation(java.math.BigDecimal) 

    /**
     * Method setAgentTypeCTSets the value of field 'agentTypeCT'.
     * 
     * @param agentTypeCT the value of field 'agentTypeCT'.
     */
    public void setAgentTypeCT(java.lang.String agentTypeCT)
    {
        this._agentTypeCT = agentTypeCT;
        
        super.setVoChanged(true);
    } //-- void setAgentTypeCT(java.lang.String) 

    /**
     * Method setClientDetailVOSets the value of field
     * 'clientDetailVO'.
     * 
     * @param clientDetailVO the value of field 'clientDetailVO'.
     */
    public void setClientDetailVO(edit.common.vo.ClientDetailVO clientDetailVO)
    {
        this._clientDetailVO = clientDetailVO;
    } //-- void setClientDetailVO(edit.common.vo.ClientDetailVO) 

    /**
     * Method setClientPKSets the value of field 'clientPK'.
     * 
     * @param clientPK the value of field 'clientPK'.
     */
    public void setClientPK(long clientPK)
    {
        this._clientPK = clientPK;
        
        super.setVoChanged(true);
        this._has_clientPK = true;
    } //-- void setClientPK(long) 

    /**
     * Method setContractClientVOSets the value of field
     * 'contractClientVO'.
     * 
     * @param contractClientVO the value of field 'contractClientVO'
     */
    public void setContractClientVO(edit.common.vo.ContractClientVO contractClientVO)
    {
        this._contractClientVO = contractClientVO;
    } //-- void setContractClientVO(edit.common.vo.ContractClientVO) 

    /**
     * Method setRoleTypeCTSets the value of field 'roleTypeCT'.
     * 
     * @param roleTypeCT the value of field 'roleTypeCT'.
     */
    public void setRoleTypeCT(java.lang.String roleTypeCT)
    {
        this._roleTypeCT = roleTypeCT;
        
        super.setVoChanged(true);
    } //-- void setRoleTypeCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.IssueClientVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.IssueClientVO) Unmarshaller.unmarshal(edit.common.vo.IssueClientVO.class, reader);
    } //-- edit.common.vo.IssueClientVO unmarshal(java.io.Reader) 

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
