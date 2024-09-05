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
public class BIZSecuredMethodVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _isEditable
     */
    private boolean _isEditable;

    /**
     * keeps track of state for field: _isEditable
     */
    private boolean _has_isEditable;

    /**
     * Field _isAuthorized
     */
    private boolean _isAuthorized;

    /**
     * keeps track of state for field: _isAuthorized
     */
    private boolean _has_isAuthorized;

    /**
     * Field _securedMethodVO
     */
    private edit.common.vo.SecuredMethodVO _securedMethodVO;


      //----------------/
     //- Constructors -/
    //----------------/

    public BIZSecuredMethodVO() {
        super();
    } //-- edit.common.vo.BIZSecuredMethodVO()


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
        
        if (obj instanceof BIZSecuredMethodVO) {
        
            BIZSecuredMethodVO temp = (BIZSecuredMethodVO)obj;
            if (this._isEditable != temp._isEditable)
                return false;
            if (this._has_isEditable != temp._has_isEditable)
                return false;
            if (this._isAuthorized != temp._isAuthorized)
                return false;
            if (this._has_isAuthorized != temp._has_isAuthorized)
                return false;
            if (this._securedMethodVO != null) {
                if (temp._securedMethodVO == null) return false;
                else if (!(this._securedMethodVO.equals(temp._securedMethodVO))) 
                    return false;
            }
            else if (temp._securedMethodVO != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getIsAuthorizedReturns the value of field
     * 'isAuthorized'.
     * 
     * @return the value of field 'isAuthorized'.
     */
    public boolean getIsAuthorized()
    {
        return this._isAuthorized;
    } //-- boolean getIsAuthorized() 

    /**
     * Method getIsEditableReturns the value of field 'isEditable'.
     * 
     * @return the value of field 'isEditable'.
     */
    public boolean getIsEditable()
    {
        return this._isEditable;
    } //-- boolean getIsEditable() 

    /**
     * Method getSecuredMethodVOReturns the value of field
     * 'securedMethodVO'.
     * 
     * @return the value of field 'securedMethodVO'.
     */
    public edit.common.vo.SecuredMethodVO getSecuredMethodVO()
    {
        return this._securedMethodVO;
    } //-- edit.common.vo.SecuredMethodVO getSecuredMethodVO() 

    /**
     * Method hasIsAuthorized
     */
    public boolean hasIsAuthorized()
    {
        return this._has_isAuthorized;
    } //-- boolean hasIsAuthorized() 

    /**
     * Method hasIsEditable
     */
    public boolean hasIsEditable()
    {
        return this._has_isEditable;
    } //-- boolean hasIsEditable() 

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
     * Method setIsAuthorizedSets the value of field
     * 'isAuthorized'.
     * 
     * @param isAuthorized the value of field 'isAuthorized'.
     */
    public void setIsAuthorized(boolean isAuthorized)
    {
        this._isAuthorized = isAuthorized;
        
        super.setVoChanged(true);
        this._has_isAuthorized = true;
    } //-- void setIsAuthorized(boolean) 

    /**
     * Method setIsEditableSets the value of field 'isEditable'.
     * 
     * @param isEditable the value of field 'isEditable'.
     */
    public void setIsEditable(boolean isEditable)
    {
        this._isEditable = isEditable;
        
        super.setVoChanged(true);
        this._has_isEditable = true;
    } //-- void setIsEditable(boolean) 

    /**
     * Method setSecuredMethodVOSets the value of field
     * 'securedMethodVO'.
     * 
     * @param securedMethodVO the value of field 'securedMethodVO'.
     */
    public void setSecuredMethodVO(edit.common.vo.SecuredMethodVO securedMethodVO)
    {
        this._securedMethodVO = securedMethodVO;
    } //-- void setSecuredMethodVO(edit.common.vo.SecuredMethodVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BIZSecuredMethodVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BIZSecuredMethodVO) Unmarshaller.unmarshal(edit.common.vo.BIZSecuredMethodVO.class, reader);
    } //-- edit.common.vo.BIZSecuredMethodVO unmarshal(java.io.Reader) 

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
