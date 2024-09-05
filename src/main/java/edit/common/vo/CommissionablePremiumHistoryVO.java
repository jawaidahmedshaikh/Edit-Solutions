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
import java.math.BigDecimal;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class CommissionablePremiumHistoryVO.
 * 
 * @version $Revision$ $Date$
 */
public class CommissionablePremiumHistoryVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _commissionablePremiumHistoryPK
     */
    private long _commissionablePremiumHistoryPK;

    /**
     * keeps track of state for field:
     * _commissionablePremiumHistoryPK
     */
    private boolean _has_commissionablePremiumHistoryPK;

    /**
     * Field _EDITTrxHistoryFK
     */
    private long _EDITTrxHistoryFK;

    /**
     * keeps track of state for field: _EDITTrxHistoryFK
     */
    private boolean _has_EDITTrxHistoryFK;

    /**
     * Field _commissionPhaseFK
     */
    private long _commissionPhaseFK;

    /**
     * keeps track of state for field: _commissionPhaseFK
     */
    private boolean _has_commissionPhaseFK;

    /**
     * Field _duration
     */
    private int _duration;

    /**
     * keeps track of state for field: _duration
     */
    private boolean _has_duration;

    /**
     * Field _commissionablePremium
     */
    private java.math.BigDecimal _commissionablePremium;


      //----------------/
     //- Constructors -/
    //----------------/

    public CommissionablePremiumHistoryVO() {
        super();
    } //-- edit.common.vo.CommissionablePremiumHistoryVO()


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
        
        if (obj instanceof CommissionablePremiumHistoryVO) {
        
            CommissionablePremiumHistoryVO temp = (CommissionablePremiumHistoryVO)obj;
            if (this._commissionablePremiumHistoryPK != temp._commissionablePremiumHistoryPK)
                return false;
            if (this._has_commissionablePremiumHistoryPK != temp._has_commissionablePremiumHistoryPK)
                return false;
            if (this._EDITTrxHistoryFK != temp._EDITTrxHistoryFK)
                return false;
            if (this._has_EDITTrxHistoryFK != temp._has_EDITTrxHistoryFK)
                return false;
            if (this._commissionPhaseFK != temp._commissionPhaseFK)
                return false;
            if (this._has_commissionPhaseFK != temp._has_commissionPhaseFK)
                return false;
            if (this._duration != temp._duration)
                return false;
            if (this._has_duration != temp._has_duration)
                return false;
            if (this._commissionablePremium != null) {
                if (temp._commissionablePremium == null) return false;
                else if (!(this._commissionablePremium.equals(temp._commissionablePremium))) 
                    return false;
            }
            else if (temp._commissionablePremium != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCommissionPhaseFKReturns the value of field
     * 'commissionPhaseFK'.
     * 
     * @return the value of field 'commissionPhaseFK'.
     */
    public long getCommissionPhaseFK()
    {
        return this._commissionPhaseFK;
    } //-- long getCommissionPhaseFK() 

    /**
     * Method getCommissionablePremiumReturns the value of field
     * 'commissionablePremium'.
     * 
     * @return the value of field 'commissionablePremium'.
     */
    public java.math.BigDecimal getCommissionablePremium()
    {
        return this._commissionablePremium;
    } //-- java.math.BigDecimal getCommissionablePremium() 

    /**
     * Method getCommissionablePremiumHistoryPKReturns the value of
     * field 'commissionablePremiumHistoryPK'.
     * 
     * @return the value of field 'commissionablePremiumHistoryPK'.
     */
    public long getCommissionablePremiumHistoryPK()
    {
        return this._commissionablePremiumHistoryPK;
    } //-- long getCommissionablePremiumHistoryPK() 

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
     * Method getEDITTrxHistoryFKReturns the value of field
     * 'EDITTrxHistoryFK'.
     * 
     * @return the value of field 'EDITTrxHistoryFK'.
     */
    public long getEDITTrxHistoryFK()
    {
        return this._EDITTrxHistoryFK;
    } //-- long getEDITTrxHistoryFK() 

    /**
     * Method hasCommissionPhaseFK
     */
    public boolean hasCommissionPhaseFK()
    {
        return this._has_commissionPhaseFK;
    } //-- boolean hasCommissionPhaseFK() 

    /**
     * Method hasCommissionablePremiumHistoryPK
     */
    public boolean hasCommissionablePremiumHistoryPK()
    {
        return this._has_commissionablePremiumHistoryPK;
    } //-- boolean hasCommissionablePremiumHistoryPK() 

    /**
     * Method hasDuration
     */
    public boolean hasDuration()
    {
        return this._has_duration;
    } //-- boolean hasDuration() 

    /**
     * Method hasEDITTrxHistoryFK
     */
    public boolean hasEDITTrxHistoryFK()
    {
        return this._has_EDITTrxHistoryFK;
    } //-- boolean hasEDITTrxHistoryFK() 

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
     * Method setCommissionPhaseFKSets the value of field
     * 'commissionPhaseFK'.
     * 
     * @param commissionPhaseFK the value of field
     * 'commissionPhaseFK'.
     */
    public void setCommissionPhaseFK(long commissionPhaseFK)
    {
        this._commissionPhaseFK = commissionPhaseFK;
        
        super.setVoChanged(true);
        this._has_commissionPhaseFK = true;
    } //-- void setCommissionPhaseFK(long) 

    /**
     * Method setCommissionablePremiumSets the value of field
     * 'commissionablePremium'.
     * 
     * @param commissionablePremium the value of field
     * 'commissionablePremium'.
     */
    public void setCommissionablePremium(java.math.BigDecimal commissionablePremium)
    {
        this._commissionablePremium = commissionablePremium;
        
        super.setVoChanged(true);
    } //-- void setCommissionablePremium(java.math.BigDecimal) 

    /**
     * Method setCommissionablePremiumHistoryPKSets the value of
     * field 'commissionablePremiumHistoryPK'.
     * 
     * @param commissionablePremiumHistoryPK the value of field
     * 'commissionablePremiumHistoryPK'.
     */
    public void setCommissionablePremiumHistoryPK(long commissionablePremiumHistoryPK)
    {
        this._commissionablePremiumHistoryPK = commissionablePremiumHistoryPK;
        
        super.setVoChanged(true);
        this._has_commissionablePremiumHistoryPK = true;
    } //-- void setCommissionablePremiumHistoryPK(long) 

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
     * Method setEDITTrxHistoryFKSets the value of field
     * 'EDITTrxHistoryFK'.
     * 
     * @param EDITTrxHistoryFK the value of field 'EDITTrxHistoryFK'
     */
    public void setEDITTrxHistoryFK(long EDITTrxHistoryFK)
    {
        this._EDITTrxHistoryFK = EDITTrxHistoryFK;
        
        super.setVoChanged(true);
        this._has_EDITTrxHistoryFK = true;
    } //-- void setEDITTrxHistoryFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CommissionablePremiumHistoryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CommissionablePremiumHistoryVO) Unmarshaller.unmarshal(edit.common.vo.CommissionablePremiumHistoryVO.class, reader);
    } //-- edit.common.vo.CommissionablePremiumHistoryVO unmarshal(java.io.Reader) 

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
