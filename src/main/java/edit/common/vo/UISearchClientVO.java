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
 * Class UISearchClientVO.
 * 
 * @version $Revision$ $Date$
 */
public class UISearchClientVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * Field _clientId
     */
    private java.lang.String _clientId;

    /**
     * Field _clientDetailPK
     */
    private long _clientDetailPK;

    /**
     * keeps track of state for field: _clientDetailPK
     */
    private boolean _has_clientDetailPK;


      //----------------/
     //- Constructors -/
    //----------------/

    public UISearchClientVO() {
        super();
    } //-- edit.common.vo.UISearchClientVO()


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
        
        if (obj instanceof UISearchClientVO) {
        
            UISearchClientVO temp = (UISearchClientVO)obj;
            if (this._name != null) {
                if (temp._name == null) return false;
                else if (!(this._name.equals(temp._name))) 
                    return false;
            }
            else if (temp._name != null)
                return false;
            if (this._clientId != null) {
                if (temp._clientId == null) return false;
                else if (!(this._clientId.equals(temp._clientId))) 
                    return false;
            }
            else if (temp._clientId != null)
                return false;
            if (this._clientDetailPK != temp._clientDetailPK)
                return false;
            if (this._has_clientDetailPK != temp._has_clientDetailPK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getClientDetailPKReturns the value of field
     * 'clientDetailPK'.
     * 
     * @return the value of field 'clientDetailPK'.
     */
    public long getClientDetailPK()
    {
        return this._clientDetailPK;
    } //-- long getClientDetailPK() 

    /**
     * Method getClientIdReturns the value of field 'clientId'.
     * 
     * @return the value of field 'clientId'.
     */
    public java.lang.String getClientId()
    {
        return this._clientId;
    } //-- java.lang.String getClientId() 

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
     * Method hasClientDetailPK
     */
    public boolean hasClientDetailPK()
    {
        return this._has_clientDetailPK;
    } //-- boolean hasClientDetailPK() 

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
     * Method setClientDetailPKSets the value of field
     * 'clientDetailPK'.
     * 
     * @param clientDetailPK the value of field 'clientDetailPK'.
     */
    public void setClientDetailPK(long clientDetailPK)
    {
        this._clientDetailPK = clientDetailPK;
        
        super.setVoChanged(true);
        this._has_clientDetailPK = true;
    } //-- void setClientDetailPK(long) 

    /**
     * Method setClientIdSets the value of field 'clientId'.
     * 
     * @param clientId the value of field 'clientId'.
     */
    public void setClientId(java.lang.String clientId)
    {
        this._clientId = clientId;
        
        super.setVoChanged(true);
    } //-- void setClientId(java.lang.String) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.UISearchClientVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.UISearchClientVO) Unmarshaller.unmarshal(edit.common.vo.UISearchClientVO.class, reader);
    } //-- edit.common.vo.UISearchClientVO unmarshal(java.io.Reader) 

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
