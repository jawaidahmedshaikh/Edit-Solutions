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
 * Class RoutingVO.
 * 
 * @version $Revision$ $Date$
 */
public class RoutingVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _routingPK
     */
    private long _routingPK;

    /**
     * keeps track of state for field: _routingPK
     */
    private boolean _has_routingPK;

    /**
     * Field _routingTypeCT
     */
    private java.lang.String _routingTypeCT;

    /**
     * Field _specialHandling
     */
    private java.lang.String _specialHandling;

    /**
     * Field _fromRoleFK
     */
    private long _fromRoleFK;

    /**
     * keeps track of state for field: _fromRoleFK
     */
    private boolean _has_fromRoleFK;

    /**
     * Field _toRoleFK
     */
    private long _toRoleFK;

    /**
     * keeps track of state for field: _toRoleFK
     */
    private boolean _has_toRoleFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public RoutingVO() {
        super();
    } //-- edit.common.vo.RoutingVO()


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
        
        if (obj instanceof RoutingVO) {
        
            RoutingVO temp = (RoutingVO)obj;
            if (this._routingPK != temp._routingPK)
                return false;
            if (this._has_routingPK != temp._has_routingPK)
                return false;
            if (this._routingTypeCT != null) {
                if (temp._routingTypeCT == null) return false;
                else if (!(this._routingTypeCT.equals(temp._routingTypeCT))) 
                    return false;
            }
            else if (temp._routingTypeCT != null)
                return false;
            if (this._specialHandling != null) {
                if (temp._specialHandling == null) return false;
                else if (!(this._specialHandling.equals(temp._specialHandling))) 
                    return false;
            }
            else if (temp._specialHandling != null)
                return false;
            if (this._fromRoleFK != temp._fromRoleFK)
                return false;
            if (this._has_fromRoleFK != temp._has_fromRoleFK)
                return false;
            if (this._toRoleFK != temp._toRoleFK)
                return false;
            if (this._has_toRoleFK != temp._has_toRoleFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getFromRoleFKReturns the value of field 'fromRoleFK'.
     * 
     * @return the value of field 'fromRoleFK'.
     */
    public long getFromRoleFK()
    {
        return this._fromRoleFK;
    } //-- long getFromRoleFK() 

    /**
     * Method getRoutingPKReturns the value of field 'routingPK'.
     * 
     * @return the value of field 'routingPK'.
     */
    public long getRoutingPK()
    {
        return this._routingPK;
    } //-- long getRoutingPK() 

    /**
     * Method getRoutingTypeCTReturns the value of field
     * 'routingTypeCT'.
     * 
     * @return the value of field 'routingTypeCT'.
     */
    public java.lang.String getRoutingTypeCT()
    {
        return this._routingTypeCT;
    } //-- java.lang.String getRoutingTypeCT() 

    /**
     * Method getSpecialHandlingReturns the value of field
     * 'specialHandling'.
     * 
     * @return the value of field 'specialHandling'.
     */
    public java.lang.String getSpecialHandling()
    {
        return this._specialHandling;
    } //-- java.lang.String getSpecialHandling() 

    /**
     * Method getToRoleFKReturns the value of field 'toRoleFK'.
     * 
     * @return the value of field 'toRoleFK'.
     */
    public long getToRoleFK()
    {
        return this._toRoleFK;
    } //-- long getToRoleFK() 

    /**
     * Method hasFromRoleFK
     */
    public boolean hasFromRoleFK()
    {
        return this._has_fromRoleFK;
    } //-- boolean hasFromRoleFK() 

    /**
     * Method hasRoutingPK
     */
    public boolean hasRoutingPK()
    {
        return this._has_routingPK;
    } //-- boolean hasRoutingPK() 

    /**
     * Method hasToRoleFK
     */
    public boolean hasToRoleFK()
    {
        return this._has_toRoleFK;
    } //-- boolean hasToRoleFK() 

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
     * Method setFromRoleFKSets the value of field 'fromRoleFK'.
     * 
     * @param fromRoleFK the value of field 'fromRoleFK'.
     */
    public void setFromRoleFK(long fromRoleFK)
    {
        this._fromRoleFK = fromRoleFK;
        
        super.setVoChanged(true);
        this._has_fromRoleFK = true;
    } //-- void setFromRoleFK(long) 

    /**
     * Method setRoutingPKSets the value of field 'routingPK'.
     * 
     * @param routingPK the value of field 'routingPK'.
     */
    public void setRoutingPK(long routingPK)
    {
        this._routingPK = routingPK;
        
        super.setVoChanged(true);
        this._has_routingPK = true;
    } //-- void setRoutingPK(long) 

    /**
     * Method setRoutingTypeCTSets the value of field
     * 'routingTypeCT'.
     * 
     * @param routingTypeCT the value of field 'routingTypeCT'.
     */
    public void setRoutingTypeCT(java.lang.String routingTypeCT)
    {
        this._routingTypeCT = routingTypeCT;
        
        super.setVoChanged(true);
    } //-- void setRoutingTypeCT(java.lang.String) 

    /**
     * Method setSpecialHandlingSets the value of field
     * 'specialHandling'.
     * 
     * @param specialHandling the value of field 'specialHandling'.
     */
    public void setSpecialHandling(java.lang.String specialHandling)
    {
        this._specialHandling = specialHandling;
        
        super.setVoChanged(true);
    } //-- void setSpecialHandling(java.lang.String) 

    /**
     * Method setToRoleFKSets the value of field 'toRoleFK'.
     * 
     * @param toRoleFK the value of field 'toRoleFK'.
     */
    public void setToRoleFK(long toRoleFK)
    {
        this._toRoleFK = toRoleFK;
        
        super.setVoChanged(true);
        this._has_toRoleFK = true;
    } //-- void setToRoleFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.RoutingVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.RoutingVO) Unmarshaller.unmarshal(edit.common.vo.RoutingVO.class, reader);
    } //-- edit.common.vo.RoutingVO unmarshal(java.io.Reader) 

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
