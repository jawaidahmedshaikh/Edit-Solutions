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
 * Class PlacedAgentCommissionProfileVO.
 * 
 * @version $Revision$ $Date$
 */
public class PlacedAgentCommissionProfileVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _placedAgentCommissionProfilePK
     */
    private long _placedAgentCommissionProfilePK;

    /**
     * keeps track of state for field:
     * _placedAgentCommissionProfilePK
     */
    private boolean _has_placedAgentCommissionProfilePK;

    /**
     * Field _commissionProfileFK
     */
    private long _commissionProfileFK;

    /**
     * keeps track of state for field: _commissionProfileFK
     */
    private boolean _has_commissionProfileFK;

    /**
     * Field _placedAgentFK
     */
    private long _placedAgentFK;

    /**
     * keeps track of state for field: _placedAgentFK
     */
    private boolean _has_placedAgentFK;

    /**
     * Field _startDate
     */
    private java.lang.String _startDate;

    /**
     * Field _stopDate
     */
    private java.lang.String _stopDate;


      //----------------/
     //- Constructors -/
    //----------------/

    public PlacedAgentCommissionProfileVO() {
        super();
    } //-- edit.common.vo.PlacedAgentCommissionProfileVO()


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
        
        if (obj instanceof PlacedAgentCommissionProfileVO) {
        
            PlacedAgentCommissionProfileVO temp = (PlacedAgentCommissionProfileVO)obj;
            if (this._placedAgentCommissionProfilePK != temp._placedAgentCommissionProfilePK)
                return false;
            if (this._has_placedAgentCommissionProfilePK != temp._has_placedAgentCommissionProfilePK)
                return false;
            if (this._commissionProfileFK != temp._commissionProfileFK)
                return false;
            if (this._has_commissionProfileFK != temp._has_commissionProfileFK)
                return false;
            if (this._placedAgentFK != temp._placedAgentFK)
                return false;
            if (this._has_placedAgentFK != temp._has_placedAgentFK)
                return false;
            if (this._startDate != null) {
                if (temp._startDate == null) return false;
                else if (!(this._startDate.equals(temp._startDate))) 
                    return false;
            }
            else if (temp._startDate != null)
                return false;
            if (this._stopDate != null) {
                if (temp._stopDate == null) return false;
                else if (!(this._stopDate.equals(temp._stopDate))) 
                    return false;
            }
            else if (temp._stopDate != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getPlacedAgentCommissionProfilePKReturns the value of
     * field 'placedAgentCommissionProfilePK'.
     * 
     * @return the value of field 'placedAgentCommissionProfilePK'.
     */
    public long getPlacedAgentCommissionProfilePK()
    {
        return this._placedAgentCommissionProfilePK;
    } //-- long getPlacedAgentCommissionProfilePK() 

    /**
     * Method getPlacedAgentFKReturns the value of field
     * 'placedAgentFK'.
     * 
     * @return the value of field 'placedAgentFK'.
     */
    public long getPlacedAgentFK()
    {
        return this._placedAgentFK;
    } //-- long getPlacedAgentFK() 

    /**
     * Method getStartDateReturns the value of field 'startDate'.
     * 
     * @return the value of field 'startDate'.
     */
    public java.lang.String getStartDate()
    {
        return this._startDate;
    } //-- java.lang.String getStartDate() 

    /**
     * Method getStopDateReturns the value of field 'stopDate'.
     * 
     * @return the value of field 'stopDate'.
     */
    public java.lang.String getStopDate()
    {
        return this._stopDate;
    } //-- java.lang.String getStopDate() 

    /**
     * Method hasCommissionProfileFK
     */
    public boolean hasCommissionProfileFK()
    {
        return this._has_commissionProfileFK;
    } //-- boolean hasCommissionProfileFK() 

    /**
     * Method hasPlacedAgentCommissionProfilePK
     */
    public boolean hasPlacedAgentCommissionProfilePK()
    {
        return this._has_placedAgentCommissionProfilePK;
    } //-- boolean hasPlacedAgentCommissionProfilePK() 

    /**
     * Method hasPlacedAgentFK
     */
    public boolean hasPlacedAgentFK()
    {
        return this._has_placedAgentFK;
    } //-- boolean hasPlacedAgentFK() 

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
     * Method setPlacedAgentCommissionProfilePKSets the value of
     * field 'placedAgentCommissionProfilePK'.
     * 
     * @param placedAgentCommissionProfilePK the value of field
     * 'placedAgentCommissionProfilePK'.
     */
    public void setPlacedAgentCommissionProfilePK(long placedAgentCommissionProfilePK)
    {
        this._placedAgentCommissionProfilePK = placedAgentCommissionProfilePK;
        
        super.setVoChanged(true);
        this._has_placedAgentCommissionProfilePK = true;
    } //-- void setPlacedAgentCommissionProfilePK(long) 

    /**
     * Method setPlacedAgentFKSets the value of field
     * 'placedAgentFK'.
     * 
     * @param placedAgentFK the value of field 'placedAgentFK'.
     */
    public void setPlacedAgentFK(long placedAgentFK)
    {
        this._placedAgentFK = placedAgentFK;
        
        super.setVoChanged(true);
        this._has_placedAgentFK = true;
    } //-- void setPlacedAgentFK(long) 

    /**
     * Method setStartDateSets the value of field 'startDate'.
     * 
     * @param startDate the value of field 'startDate'.
     */
    public void setStartDate(java.lang.String startDate)
    {
        this._startDate = startDate;
        
        super.setVoChanged(true);
    } //-- void setStartDate(java.lang.String) 

    /**
     * Method setStopDateSets the value of field 'stopDate'.
     * 
     * @param stopDate the value of field 'stopDate'.
     */
    public void setStopDate(java.lang.String stopDate)
    {
        this._stopDate = stopDate;
        
        super.setVoChanged(true);
    } //-- void setStopDate(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.PlacedAgentCommissionProfileVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.PlacedAgentCommissionProfileVO) Unmarshaller.unmarshal(edit.common.vo.PlacedAgentCommissionProfileVO.class, reader);
    } //-- edit.common.vo.PlacedAgentCommissionProfileVO unmarshal(java.io.Reader) 

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
