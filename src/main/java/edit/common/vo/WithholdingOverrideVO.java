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
 * Class WithholdingOverrideVO.
 * 
 * @version $Revision$ $Date$
 */
public class WithholdingOverrideVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _withholdingOverridePK
     */
    private long _withholdingOverridePK;

    /**
     * keeps track of state for field: _withholdingOverridePK
     */
    private boolean _has_withholdingOverridePK;

    /**
     * Field _clientSetupFK
     */
    private long _clientSetupFK;

    /**
     * keeps track of state for field: _clientSetupFK
     */
    private boolean _has_clientSetupFK;

    /**
     * Field _withholdingFK
     */
    private long _withholdingFK;

    /**
     * keeps track of state for field: _withholdingFK
     */
    private boolean _has_withholdingFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public WithholdingOverrideVO() {
        super();
    } //-- edit.common.vo.WithholdingOverrideVO()


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
        
        if (obj instanceof WithholdingOverrideVO) {
        
            WithholdingOverrideVO temp = (WithholdingOverrideVO)obj;
            if (this._withholdingOverridePK != temp._withholdingOverridePK)
                return false;
            if (this._has_withholdingOverridePK != temp._has_withholdingOverridePK)
                return false;
            if (this._clientSetupFK != temp._clientSetupFK)
                return false;
            if (this._has_clientSetupFK != temp._has_clientSetupFK)
                return false;
            if (this._withholdingFK != temp._withholdingFK)
                return false;
            if (this._has_withholdingFK != temp._has_withholdingFK)
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
     * Method getWithholdingFKReturns the value of field
     * 'withholdingFK'.
     * 
     * @return the value of field 'withholdingFK'.
     */
    public long getWithholdingFK()
    {
        return this._withholdingFK;
    } //-- long getWithholdingFK() 

    /**
     * Method getWithholdingOverridePKReturns the value of field
     * 'withholdingOverridePK'.
     * 
     * @return the value of field 'withholdingOverridePK'.
     */
    public long getWithholdingOverridePK()
    {
        return this._withholdingOverridePK;
    } //-- long getWithholdingOverridePK() 

    /**
     * Method hasClientSetupFK
     */
    public boolean hasClientSetupFK()
    {
        return this._has_clientSetupFK;
    } //-- boolean hasClientSetupFK() 

    /**
     * Method hasWithholdingFK
     */
    public boolean hasWithholdingFK()
    {
        return this._has_withholdingFK;
    } //-- boolean hasWithholdingFK() 

    /**
     * Method hasWithholdingOverridePK
     */
    public boolean hasWithholdingOverridePK()
    {
        return this._has_withholdingOverridePK;
    } //-- boolean hasWithholdingOverridePK() 

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
     * Method setWithholdingFKSets the value of field
     * 'withholdingFK'.
     * 
     * @param withholdingFK the value of field 'withholdingFK'.
     */
    public void setWithholdingFK(long withholdingFK)
    {
        this._withholdingFK = withholdingFK;
        
        super.setVoChanged(true);
        this._has_withholdingFK = true;
    } //-- void setWithholdingFK(long) 

    /**
     * Method setWithholdingOverridePKSets the value of field
     * 'withholdingOverridePK'.
     * 
     * @param withholdingOverridePK the value of field
     * 'withholdingOverridePK'.
     */
    public void setWithholdingOverridePK(long withholdingOverridePK)
    {
        this._withholdingOverridePK = withholdingOverridePK;
        
        super.setVoChanged(true);
        this._has_withholdingOverridePK = true;
    } //-- void setWithholdingOverridePK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.WithholdingOverrideVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.WithholdingOverrideVO) Unmarshaller.unmarshal(edit.common.vo.WithholdingOverrideVO.class, reader);
    } //-- edit.common.vo.WithholdingOverrideVO unmarshal(java.io.Reader) 

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
