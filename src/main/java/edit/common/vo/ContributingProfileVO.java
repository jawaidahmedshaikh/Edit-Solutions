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
 * Class ContributingProfileVO.
 * 
 * @version $Revision$ $Date$
 */
public class ContributingProfileVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _contributingProfilePK
     */
    private long _contributingProfilePK;

    /**
     * keeps track of state for field: _contributingProfilePK
     */
    private boolean _has_contributingProfilePK;

    /**
     * Field _bonusProgramFK
     */
    private long _bonusProgramFK;

    /**
     * keeps track of state for field: _bonusProgramFK
     */
    private boolean _has_bonusProgramFK;

    /**
     * Field _commissionProfileFK
     */
    private long _commissionProfileFK;

    /**
     * keeps track of state for field: _commissionProfileFK
     */
    private boolean _has_commissionProfileFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public ContributingProfileVO() {
        super();
    } //-- edit.common.vo.ContributingProfileVO()


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
        
        if (obj instanceof ContributingProfileVO) {
        
            ContributingProfileVO temp = (ContributingProfileVO)obj;
            if (this._contributingProfilePK != temp._contributingProfilePK)
                return false;
            if (this._has_contributingProfilePK != temp._has_contributingProfilePK)
                return false;
            if (this._bonusProgramFK != temp._bonusProgramFK)
                return false;
            if (this._has_bonusProgramFK != temp._has_bonusProgramFK)
                return false;
            if (this._commissionProfileFK != temp._commissionProfileFK)
                return false;
            if (this._has_commissionProfileFK != temp._has_commissionProfileFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBonusProgramFKReturns the value of field
     * 'bonusProgramFK'.
     * 
     * @return the value of field 'bonusProgramFK'.
     */
    public long getBonusProgramFK()
    {
        return this._bonusProgramFK;
    } //-- long getBonusProgramFK() 

    /**
     * Method getCommissionProfileFKReturns the value of field
     * 'commissionProfileFK'.
     * 
     * @return the value of field 'commissionProfileFK'.
     */
    public long getCommissionProfileFK()
    {
        return this._commissionProfileFK;
    } //-- long getCommissionProfileFK() 

    /**
     * Method getContributingProfilePKReturns the value of field
     * 'contributingProfilePK'.
     * 
     * @return the value of field 'contributingProfilePK'.
     */
    public long getContributingProfilePK()
    {
        return this._contributingProfilePK;
    } //-- long getContributingProfilePK() 

    /**
     * Method hasBonusProgramFK
     */
    public boolean hasBonusProgramFK()
    {
        return this._has_bonusProgramFK;
    } //-- boolean hasBonusProgramFK() 

    /**
     * Method hasCommissionProfileFK
     */
    public boolean hasCommissionProfileFK()
    {
        return this._has_commissionProfileFK;
    } //-- boolean hasCommissionProfileFK() 

    /**
     * Method hasContributingProfilePK
     */
    public boolean hasContributingProfilePK()
    {
        return this._has_contributingProfilePK;
    } //-- boolean hasContributingProfilePK() 

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
     * Method setBonusProgramFKSets the value of field
     * 'bonusProgramFK'.
     * 
     * @param bonusProgramFK the value of field 'bonusProgramFK'.
     */
    public void setBonusProgramFK(long bonusProgramFK)
    {
        this._bonusProgramFK = bonusProgramFK;
        
        super.setVoChanged(true);
        this._has_bonusProgramFK = true;
    } //-- void setBonusProgramFK(long) 

    /**
     * Method setCommissionProfileFKSets the value of field
     * 'commissionProfileFK'.
     * 
     * @param commissionProfileFK the value of field
     * 'commissionProfileFK'.
     */
    public void setCommissionProfileFK(long commissionProfileFK)
    {
        this._commissionProfileFK = commissionProfileFK;
        
        super.setVoChanged(true);
        this._has_commissionProfileFK = true;
    } //-- void setCommissionProfileFK(long) 

    /**
     * Method setContributingProfilePKSets the value of field
     * 'contributingProfilePK'.
     * 
     * @param contributingProfilePK the value of field
     * 'contributingProfilePK'.
     */
    public void setContributingProfilePK(long contributingProfilePK)
    {
        this._contributingProfilePK = contributingProfilePK;
        
        super.setVoChanged(true);
        this._has_contributingProfilePK = true;
    } //-- void setContributingProfilePK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ContributingProfileVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ContributingProfileVO) Unmarshaller.unmarshal(edit.common.vo.ContributingProfileVO.class, reader);
    } //-- edit.common.vo.ContributingProfileVO unmarshal(java.io.Reader) 

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
