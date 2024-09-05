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
 * Used for Correspondence projections reports
 * 
 * @version $Revision$ $Date$
 */
public class ProjectionSurrenderChargeVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _projectionSurrenderChargePK
     */
    private long _projectionSurrenderChargePK;

    /**
     * keeps track of state for field: _projectionSurrenderChargePK
     */
    private boolean _has_projectionSurrenderChargePK;

    /**
     * Field _duration
     */
    private int _duration;

    /**
     * keeps track of state for field: _duration
     */
    private boolean _has_duration;

    /**
     * Field _rate
     */
    private double _rate;

    /**
     * keeps track of state for field: _rate
     */
    private boolean _has_rate;


      //----------------/
     //- Constructors -/
    //----------------/

    public ProjectionSurrenderChargeVO() {
        super();
    } //-- edit.common.vo.ProjectionSurrenderChargeVO()


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
        
        if (obj instanceof ProjectionSurrenderChargeVO) {
        
            ProjectionSurrenderChargeVO temp = (ProjectionSurrenderChargeVO)obj;
            if (this._projectionSurrenderChargePK != temp._projectionSurrenderChargePK)
                return false;
            if (this._has_projectionSurrenderChargePK != temp._has_projectionSurrenderChargePK)
                return false;
            if (this._duration != temp._duration)
                return false;
            if (this._has_duration != temp._has_duration)
                return false;
            if (this._rate != temp._rate)
                return false;
            if (this._has_rate != temp._has_rate)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getDurationReturns the value of field 'duration'.
     * 
     * @return the value of field 'duration'.
     */
    public int getDuration()
    {
        return this._duration;
    } //-- int getDuration() 

    /**
     * Method getProjectionSurrenderChargePKReturns the value of
     * field 'projectionSurrenderChargePK'.
     * 
     * @return the value of field 'projectionSurrenderChargePK'.
     */
    public long getProjectionSurrenderChargePK()
    {
        return this._projectionSurrenderChargePK;
    } //-- long getProjectionSurrenderChargePK() 

    /**
     * Method getRateReturns the value of field 'rate'.
     * 
     * @return the value of field 'rate'.
     */
    public double getRate()
    {
        return this._rate;
    } //-- double getRate() 

    /**
     * Method hasDuration
     */
    public boolean hasDuration()
    {
        return this._has_duration;
    } //-- boolean hasDuration() 

    /**
     * Method hasProjectionSurrenderChargePK
     */
    public boolean hasProjectionSurrenderChargePK()
    {
        return this._has_projectionSurrenderChargePK;
    } //-- boolean hasProjectionSurrenderChargePK() 

    /**
     * Method hasRate
     */
    public boolean hasRate()
    {
        return this._has_rate;
    } //-- boolean hasRate() 

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
     * Method setDurationSets the value of field 'duration'.
     * 
     * @param duration the value of field 'duration'.
     */
    public void setDuration(int duration)
    {
        this._duration = duration;
        
        super.setVoChanged(true);
        this._has_duration = true;
    } //-- void setDuration(int) 

    /**
     * Method setProjectionSurrenderChargePKSets the value of field
     * 'projectionSurrenderChargePK'.
     * 
     * @param projectionSurrenderChargePK the value of field
     * 'projectionSurrenderChargePK'.
     */
    public void setProjectionSurrenderChargePK(long projectionSurrenderChargePK)
    {
        this._projectionSurrenderChargePK = projectionSurrenderChargePK;
        
        super.setVoChanged(true);
        this._has_projectionSurrenderChargePK = true;
    } //-- void setProjectionSurrenderChargePK(long) 

    /**
     * Method setRateSets the value of field 'rate'.
     * 
     * @param rate the value of field 'rate'.
     */
    public void setRate(double rate)
    {
        this._rate = rate;
        
        super.setVoChanged(true);
        this._has_rate = true;
    } //-- void setRate(double) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ProjectionSurrenderChargeVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ProjectionSurrenderChargeVO) Unmarshaller.unmarshal(edit.common.vo.ProjectionSurrenderChargeVO.class, reader);
    } //-- edit.common.vo.ProjectionSurrenderChargeVO unmarshal(java.io.Reader) 

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
