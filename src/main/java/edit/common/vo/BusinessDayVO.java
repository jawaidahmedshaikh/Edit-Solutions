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
 * Class BusinessDayVO.
 * 
 * @version $Revision$ $Date$
 */
public class BusinessDayVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _businessDayPK
     */
    private long _businessDayPK;

    /**
     * keeps track of state for field: _businessDayPK
     */
    private boolean _has_businessDayPK;

    /**
     * Field _businessDate
     */
    private java.lang.String _businessDate;

    /**
     * Field _activeInd
     */
    private java.lang.String _activeInd;


      //----------------/
     //- Constructors -/
    //----------------/

    public BusinessDayVO() {
        super();
    } //-- edit.common.vo.BusinessDayVO()


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
        
        if (obj instanceof BusinessDayVO) {
        
            BusinessDayVO temp = (BusinessDayVO)obj;
            if (this._businessDayPK != temp._businessDayPK)
                return false;
            if (this._has_businessDayPK != temp._has_businessDayPK)
                return false;
            if (this._businessDate != null) {
                if (temp._businessDate == null) return false;
                else if (!(this._businessDate.equals(temp._businessDate))) 
                    return false;
            }
            else if (temp._businessDate != null)
                return false;
            if (this._activeInd != null) {
                if (temp._activeInd == null) return false;
                else if (!(this._activeInd.equals(temp._activeInd))) 
                    return false;
            }
            else if (temp._activeInd != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getActiveIndReturns the value of field 'activeInd'.
     * 
     * @return the value of field 'activeInd'.
     */
    public java.lang.String getActiveInd()
    {
        return this._activeInd;
    } //-- java.lang.String getActiveInd() 

    /**
     * Method getBusinessDateReturns the value of field
     * 'businessDate'.
     * 
     * @return the value of field 'businessDate'.
     */
    public java.lang.String getBusinessDate()
    {
        return this._businessDate;
    } //-- java.lang.String getBusinessDate() 

    /**
     * Method getBusinessDayPKReturns the value of field
     * 'businessDayPK'.
     * 
     * @return the value of field 'businessDayPK'.
     */
    public long getBusinessDayPK()
    {
        return this._businessDayPK;
    } //-- long getBusinessDayPK() 

    /**
     * Method hasBusinessDayPK
     */
    public boolean hasBusinessDayPK()
    {
        return this._has_businessDayPK;
    } //-- boolean hasBusinessDayPK() 

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
     * Method setActiveIndSets the value of field 'activeInd'.
     * 
     * @param activeInd the value of field 'activeInd'.
     */
    public void setActiveInd(java.lang.String activeInd)
    {
        this._activeInd = activeInd;
        
        super.setVoChanged(true);
    } //-- void setActiveInd(java.lang.String) 

    /**
     * Method setBusinessDateSets the value of field
     * 'businessDate'.
     * 
     * @param businessDate the value of field 'businessDate'.
     */
    public void setBusinessDate(java.lang.String businessDate)
    {
        this._businessDate = businessDate;
        
        super.setVoChanged(true);
    } //-- void setBusinessDate(java.lang.String) 

    /**
     * Method setBusinessDayPKSets the value of field
     * 'businessDayPK'.
     * 
     * @param businessDayPK the value of field 'businessDayPK'.
     */
    public void setBusinessDayPK(long businessDayPK)
    {
        this._businessDayPK = businessDayPK;
        
        super.setVoChanged(true);
        this._has_businessDayPK = true;
    } //-- void setBusinessDayPK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BusinessDayVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BusinessDayVO) Unmarshaller.unmarshal(edit.common.vo.BusinessDayVO.class, reader);
    } //-- edit.common.vo.BusinessDayVO unmarshal(java.io.Reader) 

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
