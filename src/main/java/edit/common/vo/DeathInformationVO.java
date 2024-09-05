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
public class DeathInformationVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _residentStateAtDeathCT
     */
    private java.lang.String _residentStateAtDeathCT;

    /**
     * Field _stateOfDeathCT
     */
    private java.lang.String _stateOfDeathCT;

    /**
     * Field _proofOfDeathReceivedDate
     */
    private java.lang.String _proofOfDeathReceivedDate;

    /**
     * Field _dateOfDeath
     */
    private java.lang.String _dateOfDeath;

    /**
     * Field _notificationReceivedDate
     */
    private java.lang.String _notificationReceivedDate;


      //----------------/
     //- Constructors -/
    //----------------/

    public DeathInformationVO() {
        super();
    } //-- edit.common.vo.DeathInformationVO()


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
        
        if (obj instanceof DeathInformationVO) {
        
            DeathInformationVO temp = (DeathInformationVO)obj;
            if (this._residentStateAtDeathCT != null) {
                if (temp._residentStateAtDeathCT == null) return false;
                else if (!(this._residentStateAtDeathCT.equals(temp._residentStateAtDeathCT))) 
                    return false;
            }
            else if (temp._residentStateAtDeathCT != null)
                return false;
            if (this._stateOfDeathCT != null) {
                if (temp._stateOfDeathCT == null) return false;
                else if (!(this._stateOfDeathCT.equals(temp._stateOfDeathCT))) 
                    return false;
            }
            else if (temp._stateOfDeathCT != null)
                return false;
            if (this._proofOfDeathReceivedDate != null) {
                if (temp._proofOfDeathReceivedDate == null) return false;
                else if (!(this._proofOfDeathReceivedDate.equals(temp._proofOfDeathReceivedDate))) 
                    return false;
            }
            else if (temp._proofOfDeathReceivedDate != null)
                return false;
            if (this._dateOfDeath != null) {
                if (temp._dateOfDeath == null) return false;
                else if (!(this._dateOfDeath.equals(temp._dateOfDeath))) 
                    return false;
            }
            else if (temp._dateOfDeath != null)
                return false;
            if (this._notificationReceivedDate != null) {
                if (temp._notificationReceivedDate == null) return false;
                else if (!(this._notificationReceivedDate.equals(temp._notificationReceivedDate))) 
                    return false;
            }
            else if (temp._notificationReceivedDate != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getDateOfDeathReturns the value of field
     * 'dateOfDeath'.
     * 
     * @return the value of field 'dateOfDeath'.
     */
    public java.lang.String getDateOfDeath()
    {
        return this._dateOfDeath;
    } //-- java.lang.String getDateOfDeath() 

    /**
     * Method getNotificationReceivedDateReturns the value of field
     * 'notificationReceivedDate'.
     * 
     * @return the value of field 'notificationReceivedDate'.
     */
    public java.lang.String getNotificationReceivedDate()
    {
        return this._notificationReceivedDate;
    } //-- java.lang.String getNotificationReceivedDate() 

    /**
     * Method getProofOfDeathReceivedDateReturns the value of field
     * 'proofOfDeathReceivedDate'.
     * 
     * @return the value of field 'proofOfDeathReceivedDate'.
     */
    public java.lang.String getProofOfDeathReceivedDate()
    {
        return this._proofOfDeathReceivedDate;
    } //-- java.lang.String getProofOfDeathReceivedDate() 

    /**
     * Method getResidentStateAtDeathCTReturns the value of field
     * 'residentStateAtDeathCT'.
     * 
     * @return the value of field 'residentStateAtDeathCT'.
     */
    public java.lang.String getResidentStateAtDeathCT()
    {
        return this._residentStateAtDeathCT;
    } //-- java.lang.String getResidentStateAtDeathCT() 

    /**
     * Method getStateOfDeathCTReturns the value of field
     * 'stateOfDeathCT'.
     * 
     * @return the value of field 'stateOfDeathCT'.
     */
    public java.lang.String getStateOfDeathCT()
    {
        return this._stateOfDeathCT;
    } //-- java.lang.String getStateOfDeathCT() 

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
     * Method setDateOfDeathSets the value of field 'dateOfDeath'.
     * 
     * @param dateOfDeath the value of field 'dateOfDeath'.
     */
    public void setDateOfDeath(java.lang.String dateOfDeath)
    {
        this._dateOfDeath = dateOfDeath;
        
        super.setVoChanged(true);
    } //-- void setDateOfDeath(java.lang.String) 

    /**
     * Method setNotificationReceivedDateSets the value of field
     * 'notificationReceivedDate'.
     * 
     * @param notificationReceivedDate the value of field
     * 'notificationReceivedDate'.
     */
    public void setNotificationReceivedDate(java.lang.String notificationReceivedDate)
    {
        this._notificationReceivedDate = notificationReceivedDate;
        
        super.setVoChanged(true);
    } //-- void setNotificationReceivedDate(java.lang.String) 

    /**
     * Method setProofOfDeathReceivedDateSets the value of field
     * 'proofOfDeathReceivedDate'.
     * 
     * @param proofOfDeathReceivedDate the value of field
     * 'proofOfDeathReceivedDate'.
     */
    public void setProofOfDeathReceivedDate(java.lang.String proofOfDeathReceivedDate)
    {
        this._proofOfDeathReceivedDate = proofOfDeathReceivedDate;
        
        super.setVoChanged(true);
    } //-- void setProofOfDeathReceivedDate(java.lang.String) 

    /**
     * Method setResidentStateAtDeathCTSets the value of field
     * 'residentStateAtDeathCT'.
     * 
     * @param residentStateAtDeathCT the value of field
     * 'residentStateAtDeathCT'.
     */
    public void setResidentStateAtDeathCT(java.lang.String residentStateAtDeathCT)
    {
        this._residentStateAtDeathCT = residentStateAtDeathCT;
        
        super.setVoChanged(true);
    } //-- void setResidentStateAtDeathCT(java.lang.String) 

    /**
     * Method setStateOfDeathCTSets the value of field
     * 'stateOfDeathCT'.
     * 
     * @param stateOfDeathCT the value of field 'stateOfDeathCT'.
     */
    public void setStateOfDeathCT(java.lang.String stateOfDeathCT)
    {
        this._stateOfDeathCT = stateOfDeathCT;
        
        super.setVoChanged(true);
    } //-- void setStateOfDeathCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.DeathInformationVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.DeathInformationVO) Unmarshaller.unmarshal(edit.common.vo.DeathInformationVO.class, reader);
    } //-- edit.common.vo.DeathInformationVO unmarshal(java.io.Reader) 

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
