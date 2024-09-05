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
 * Class ContributingProductVO.
 * 
 * @version $Revision$ $Date$
 */
public class ContributingProductVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _contributingProductPK
     */
    private long _contributingProductPK;

    /**
     * keeps track of state for field: _contributingProductPK
     */
    private boolean _has_contributingProductPK;

    /**
     * Field _companyStructureFK
     */
    private long _companyStructureFK;

    /**
     * keeps track of state for field: _companyStructureFK
     */
    private boolean _has_companyStructureFK;

    /**
     * Field _agentGroupFK
     */
    private long _agentGroupFK;

    /**
     * keeps track of state for field: _agentGroupFK
     */
    private boolean _has_agentGroupFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public ContributingProductVO() {
        super();
    } //-- edit.common.vo.ContributingProductVO()


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
        
        if (obj instanceof ContributingProductVO) {
        
            ContributingProductVO temp = (ContributingProductVO)obj;
            if (this._contributingProductPK != temp._contributingProductPK)
                return false;
            if (this._has_contributingProductPK != temp._has_contributingProductPK)
                return false;
            if (this._companyStructureFK != temp._companyStructureFK)
                return false;
            if (this._has_companyStructureFK != temp._has_companyStructureFK)
                return false;
            if (this._agentGroupFK != temp._agentGroupFK)
                return false;
            if (this._has_agentGroupFK != temp._has_agentGroupFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentGroupFKReturns the value of field
     * 'agentGroupFK'.
     * 
     * @return the value of field 'agentGroupFK'.
     */
    public long getAgentGroupFK()
    {
        return this._agentGroupFK;
    } //-- long getAgentGroupFK() 

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
     * Method getContributingProductPKReturns the value of field
     * 'contributingProductPK'.
     * 
     * @return the value of field 'contributingProductPK'.
     */
    public long getContributingProductPK()
    {
        return this._contributingProductPK;
    } //-- long getContributingProductPK() 

    /**
     * Method hasAgentGroupFK
     */
    public boolean hasAgentGroupFK()
    {
        return this._has_agentGroupFK;
    } //-- boolean hasAgentGroupFK() 

    /**
     * Method hasCompanyStructureFK
     */
    public boolean hasCompanyStructureFK()
    {
        return this._has_companyStructureFK;
    } //-- boolean hasCompanyStructureFK() 

    /**
     * Method hasContributingProductPK
     */
    public boolean hasContributingProductPK()
    {
        return this._has_contributingProductPK;
    } //-- boolean hasContributingProductPK() 

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
     * Method setAgentGroupFKSets the value of field
     * 'agentGroupFK'.
     * 
     * @param agentGroupFK the value of field 'agentGroupFK'.
     */
    public void setAgentGroupFK(long agentGroupFK)
    {
        this._agentGroupFK = agentGroupFK;
        
        super.setVoChanged(true);
        this._has_agentGroupFK = true;
    } //-- void setAgentGroupFK(long) 

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
     * Method setContributingProductPKSets the value of field
     * 'contributingProductPK'.
     * 
     * @param contributingProductPK the value of field
     * 'contributingProductPK'.
     */
    public void setContributingProductPK(long contributingProductPK)
    {
        this._contributingProductPK = contributingProductPK;
        
        super.setVoChanged(true);
        this._has_contributingProductPK = true;
    } //-- void setContributingProductPK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ContributingProductVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ContributingProductVO) Unmarshaller.unmarshal(edit.common.vo.ContributingProductVO.class, reader);
    } //-- edit.common.vo.ContributingProductVO unmarshal(java.io.Reader) 

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
