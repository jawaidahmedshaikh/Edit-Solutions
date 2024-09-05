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
public class BIZRoleVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _implication
     */
    private java.lang.String _implication;

    /**
     * Field _roleVO
     */
    private edit.common.vo.RoleVO _roleVO;


      //----------------/
     //- Constructors -/
    //----------------/

    public BIZRoleVO() {
        super();
    } //-- edit.common.vo.BIZRoleVO()


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
        
        if (obj instanceof BIZRoleVO) {
        
            BIZRoleVO temp = (BIZRoleVO)obj;
            if (this._implication != null) {
                if (temp._implication == null) return false;
                else if (!(this._implication.equals(temp._implication))) 
                    return false;
            }
            else if (temp._implication != null)
                return false;
            if (this._roleVO != null) {
                if (temp._roleVO == null) return false;
                else if (!(this._roleVO.equals(temp._roleVO))) 
                    return false;
            }
            else if (temp._roleVO != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getImplicationReturns the value of field
     * 'implication'.
     * 
     * @return the value of field 'implication'.
     */
    public java.lang.String getImplication()
    {
        return this._implication;
    } //-- java.lang.String getImplication() 

    /**
     * Method getRoleVOReturns the value of field 'roleVO'.
     * 
     * @return the value of field 'roleVO'.
     */
    public edit.common.vo.RoleVO getRoleVO()
    {
        return this._roleVO;
    } //-- edit.common.vo.RoleVO getRole()

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
     * Method setImplicationSets the value of field 'implication'.
     * 
     * @param implication the value of field 'implication'.
     */
    public void setImplication(java.lang.String implication)
    {
        this._implication = implication;
        
        super.setVoChanged(true);
    } //-- void setImplication(java.lang.String) 

    /**
     * Method setRoleVOSets the value of field 'roleVO'.
     * 
     * @param roleVO the value of field 'roleVO'.
     */
    public void setRoleVO(edit.common.vo.RoleVO roleVO)
    {
        this._roleVO = roleVO;
    } //-- void setRoleVO(edit.common.vo.RoleVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BIZRoleVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BIZRoleVO) Unmarshaller.unmarshal(edit.common.vo.BIZRoleVO.class, reader);
    } //-- edit.common.vo.BIZRoleVO unmarshal(java.io.Reader) 

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
