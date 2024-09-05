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
 * Class AgentInfoVO.
 * 
 * @version $Revision$ $Date$
 */
public class AgentInfoVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _firstName
     */
    private java.lang.String _firstName;

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * Field _nameSuffix
     */
    private java.lang.String _nameSuffix;

    /**
     * Field _agentNumber
     */
    private java.lang.String _agentNumber;

    /**
     * Field _address1
     */
    private java.lang.String _address1;

    /**
     * Field _address2
     */
    private java.lang.String _address2;

    /**
     * Field _address3
     */
    private java.lang.String _address3;

    /**
     * Field _address4
     */
    private java.lang.String _address4;

    /**
     * Field _city
     */
    private java.lang.String _city;

    /**
     * Field _state
     */
    private java.lang.String _state;

    /**
     * Field _zip
     */
    private java.lang.String _zip;

    /**
     * Field _agentType
     */
    private java.lang.String _agentType;

    /**
     * Field _reportTo
     */
    private java.lang.String _reportTo;

    /**
     * Field _disbursementSource
     */
    private java.lang.String _disbursementSource;

    /**
     * Field _paymentMode
     */
    private java.lang.String _paymentMode;

    /**
     * Field _redirectName
     */
    private java.lang.String _redirectName;

    /**
     * Field _redirectNumber
     */
    private java.lang.String _redirectNumber;

    /**
     * Field _redirectAddress1
     */
    private java.lang.String _redirectAddress1;

    /**
     * Field _redirectAddress2
     */
    private java.lang.String _redirectAddress2;

    /**
     * Field _redirectAddress3
     */
    private java.lang.String _redirectAddress3;

    /**
     * Field _redirectAddress4
     */
    private java.lang.String _redirectAddress4;

    /**
     * Field _redirectCity
     */
    private java.lang.String _redirectCity;

    /**
     * Field _redirectState
     */
    private java.lang.String _redirectState;

    /**
     * Field _redirectZip
     */
    private java.lang.String _redirectZip;


      //----------------/
     //- Constructors -/
    //----------------/

    public AgentInfoVO() {
        super();
    } //-- edit.common.vo.AgentInfoVO()


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
        
        if (obj instanceof AgentInfoVO) {
        
            AgentInfoVO temp = (AgentInfoVO)obj;
            if (this._firstName != null) {
                if (temp._firstName == null) return false;
                else if (!(this._firstName.equals(temp._firstName))) 
                    return false;
            }
            else if (temp._firstName != null)
                return false;
            if (this._name != null) {
                if (temp._name == null) return false;
                else if (!(this._name.equals(temp._name))) 
                    return false;
            }
            else if (temp._name != null)
                return false;
            if (this._nameSuffix != null) {
                if (temp._nameSuffix == null) return false;
                else if (!(this._nameSuffix.equals(temp._nameSuffix))) 
                    return false;
            }
            else if (temp._nameSuffix != null)
                return false;
            if (this._agentNumber != null) {
                if (temp._agentNumber == null) return false;
                else if (!(this._agentNumber.equals(temp._agentNumber))) 
                    return false;
            }
            else if (temp._agentNumber != null)
                return false;
            if (this._address1 != null) {
                if (temp._address1 == null) return false;
                else if (!(this._address1.equals(temp._address1))) 
                    return false;
            }
            else if (temp._address1 != null)
                return false;
            if (this._address2 != null) {
                if (temp._address2 == null) return false;
                else if (!(this._address2.equals(temp._address2))) 
                    return false;
            }
            else if (temp._address2 != null)
                return false;
            if (this._address3 != null) {
                if (temp._address3 == null) return false;
                else if (!(this._address3.equals(temp._address3))) 
                    return false;
            }
            else if (temp._address3 != null)
                return false;
            if (this._address4 != null) {
                if (temp._address4 == null) return false;
                else if (!(this._address4.equals(temp._address4))) 
                    return false;
            }
            else if (temp._address4 != null)
                return false;
            if (this._city != null) {
                if (temp._city == null) return false;
                else if (!(this._city.equals(temp._city))) 
                    return false;
            }
            else if (temp._city != null)
                return false;
            if (this._state != null) {
                if (temp._state == null) return false;
                else if (!(this._state.equals(temp._state))) 
                    return false;
            }
            else if (temp._state != null)
                return false;
            if (this._zip != null) {
                if (temp._zip == null) return false;
                else if (!(this._zip.equals(temp._zip))) 
                    return false;
            }
            else if (temp._zip != null)
                return false;
            if (this._agentType != null) {
                if (temp._agentType == null) return false;
                else if (!(this._agentType.equals(temp._agentType))) 
                    return false;
            }
            else if (temp._agentType != null)
                return false;
            if (this._reportTo != null) {
                if (temp._reportTo == null) return false;
                else if (!(this._reportTo.equals(temp._reportTo))) 
                    return false;
            }
            else if (temp._reportTo != null)
                return false;
            if (this._disbursementSource != null) {
                if (temp._disbursementSource == null) return false;
                else if (!(this._disbursementSource.equals(temp._disbursementSource))) 
                    return false;
            }
            else if (temp._disbursementSource != null)
                return false;
            if (this._paymentMode != null) {
                if (temp._paymentMode == null) return false;
                else if (!(this._paymentMode.equals(temp._paymentMode))) 
                    return false;
            }
            else if (temp._paymentMode != null)
                return false;
            if (this._redirectName != null) {
                if (temp._redirectName == null) return false;
                else if (!(this._redirectName.equals(temp._redirectName))) 
                    return false;
            }
            else if (temp._redirectName != null)
                return false;
            if (this._redirectNumber != null) {
                if (temp._redirectNumber == null) return false;
                else if (!(this._redirectNumber.equals(temp._redirectNumber))) 
                    return false;
            }
            else if (temp._redirectNumber != null)
                return false;
            if (this._redirectAddress1 != null) {
                if (temp._redirectAddress1 == null) return false;
                else if (!(this._redirectAddress1.equals(temp._redirectAddress1))) 
                    return false;
            }
            else if (temp._redirectAddress1 != null)
                return false;
            if (this._redirectAddress2 != null) {
                if (temp._redirectAddress2 == null) return false;
                else if (!(this._redirectAddress2.equals(temp._redirectAddress2))) 
                    return false;
            }
            else if (temp._redirectAddress2 != null)
                return false;
            if (this._redirectAddress3 != null) {
                if (temp._redirectAddress3 == null) return false;
                else if (!(this._redirectAddress3.equals(temp._redirectAddress3))) 
                    return false;
            }
            else if (temp._redirectAddress3 != null)
                return false;
            if (this._redirectAddress4 != null) {
                if (temp._redirectAddress4 == null) return false;
                else if (!(this._redirectAddress4.equals(temp._redirectAddress4))) 
                    return false;
            }
            else if (temp._redirectAddress4 != null)
                return false;
            if (this._redirectCity != null) {
                if (temp._redirectCity == null) return false;
                else if (!(this._redirectCity.equals(temp._redirectCity))) 
                    return false;
            }
            else if (temp._redirectCity != null)
                return false;
            if (this._redirectState != null) {
                if (temp._redirectState == null) return false;
                else if (!(this._redirectState.equals(temp._redirectState))) 
                    return false;
            }
            else if (temp._redirectState != null)
                return false;
            if (this._redirectZip != null) {
                if (temp._redirectZip == null) return false;
                else if (!(this._redirectZip.equals(temp._redirectZip))) 
                    return false;
            }
            else if (temp._redirectZip != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAddress1Returns the value of field 'address1'.
     * 
     * @return the value of field 'address1'.
     */
    public java.lang.String getAddress1()
    {
        return this._address1;
    } //-- java.lang.String getAddress1() 

    /**
     * Method getAddress2Returns the value of field 'address2'.
     * 
     * @return the value of field 'address2'.
     */
    public java.lang.String getAddress2()
    {
        return this._address2;
    } //-- java.lang.String getAddress2() 

    /**
     * Method getAddress3Returns the value of field 'address3'.
     * 
     * @return the value of field 'address3'.
     */
    public java.lang.String getAddress3()
    {
        return this._address3;
    } //-- java.lang.String getAddress3() 

    /**
     * Method getAddress4Returns the value of field 'address4'.
     * 
     * @return the value of field 'address4'.
     */
    public java.lang.String getAddress4()
    {
        return this._address4;
    } //-- java.lang.String getAddress4() 

    /**
     * Method getAgentNumberReturns the value of field
     * 'agentNumber'.
     * 
     * @return the value of field 'agentNumber'.
     */
    public java.lang.String getAgentNumber()
    {
        return this._agentNumber;
    } //-- java.lang.String getAgentNumber() 

    /**
     * Method getAgentTypeReturns the value of field 'agentType'.
     * 
     * @return the value of field 'agentType'.
     */
    public java.lang.String getAgentType()
    {
        return this._agentType;
    } //-- java.lang.String getAgentType() 

    /**
     * Method getCityReturns the value of field 'city'.
     * 
     * @return the value of field 'city'.
     */
    public java.lang.String getCity()
    {
        return this._city;
    } //-- java.lang.String getCity() 

    /**
     * Method getDisbursementSourceReturns the value of field
     * 'disbursementSource'.
     * 
     * @return the value of field 'disbursementSource'.
     */
    public java.lang.String getDisbursementSource()
    {
        return this._disbursementSource;
    } //-- java.lang.String getDisbursementSource() 

    /**
     * Method getFirstNameReturns the value of field 'firstName'.
     * 
     * @return the value of field 'firstName'.
     */
    public java.lang.String getFirstName()
    {
        return this._firstName;
    } //-- java.lang.String getFirstName() 

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
     * Method getNameSuffixReturns the value of field 'nameSuffix'.
     * 
     * @return the value of field 'nameSuffix'.
     */
    public java.lang.String getNameSuffix()
    {
        return this._nameSuffix;
    } //-- java.lang.String getNameSuffix() 

    /**
     * Method getPaymentModeReturns the value of field
     * 'paymentMode'.
     * 
     * @return the value of field 'paymentMode'.
     */
    public java.lang.String getPaymentMode()
    {
        return this._paymentMode;
    } //-- java.lang.String getPaymentMode() 

    /**
     * Method getRedirectAddress1Returns the value of field
     * 'redirectAddress1'.
     * 
     * @return the value of field 'redirectAddress1'.
     */
    public java.lang.String getRedirectAddress1()
    {
        return this._redirectAddress1;
    } //-- java.lang.String getRedirectAddress1() 

    /**
     * Method getRedirectAddress2Returns the value of field
     * 'redirectAddress2'.
     * 
     * @return the value of field 'redirectAddress2'.
     */
    public java.lang.String getRedirectAddress2()
    {
        return this._redirectAddress2;
    } //-- java.lang.String getRedirectAddress2() 

    /**
     * Method getRedirectAddress3Returns the value of field
     * 'redirectAddress3'.
     * 
     * @return the value of field 'redirectAddress3'.
     */
    public java.lang.String getRedirectAddress3()
    {
        return this._redirectAddress3;
    } //-- java.lang.String getRedirectAddress3() 

    /**
     * Method getRedirectAddress4Returns the value of field
     * 'redirectAddress4'.
     * 
     * @return the value of field 'redirectAddress4'.
     */
    public java.lang.String getRedirectAddress4()
    {
        return this._redirectAddress4;
    } //-- java.lang.String getRedirectAddress4() 

    /**
     * Method getRedirectCityReturns the value of field
     * 'redirectCity'.
     * 
     * @return the value of field 'redirectCity'.
     */
    public java.lang.String getRedirectCity()
    {
        return this._redirectCity;
    } //-- java.lang.String getRedirectCity() 

    /**
     * Method getRedirectNameReturns the value of field
     * 'redirectName'.
     * 
     * @return the value of field 'redirectName'.
     */
    public java.lang.String getRedirectName()
    {
        return this._redirectName;
    } //-- java.lang.String getRedirectName() 

    /**
     * Method getRedirectNumberReturns the value of field
     * 'redirectNumber'.
     * 
     * @return the value of field 'redirectNumber'.
     */
    public java.lang.String getRedirectNumber()
    {
        return this._redirectNumber;
    } //-- java.lang.String getRedirectNumber() 

    /**
     * Method getRedirectStateReturns the value of field
     * 'redirectState'.
     * 
     * @return the value of field 'redirectState'.
     */
    public java.lang.String getRedirectState()
    {
        return this._redirectState;
    } //-- java.lang.String getRedirectState() 

    /**
     * Method getRedirectZipReturns the value of field
     * 'redirectZip'.
     * 
     * @return the value of field 'redirectZip'.
     */
    public java.lang.String getRedirectZip()
    {
        return this._redirectZip;
    } //-- java.lang.String getRedirectZip() 

    /**
     * Method getReportToReturns the value of field 'reportTo'.
     * 
     * @return the value of field 'reportTo'.
     */
    public java.lang.String getReportTo()
    {
        return this._reportTo;
    } //-- java.lang.String getReportTo() 

    /**
     * Method getStateReturns the value of field 'state'.
     * 
     * @return the value of field 'state'.
     */
    public java.lang.String getState()
    {
        return this._state;
    } //-- java.lang.String getState() 

    /**
     * Method getZipReturns the value of field 'zip'.
     * 
     * @return the value of field 'zip'.
     */
    public java.lang.String getZip()
    {
        return this._zip;
    } //-- java.lang.String getZip() 

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
     * Method setAddress1Sets the value of field 'address1'.
     * 
     * @param address1 the value of field 'address1'.
     */
    public void setAddress1(java.lang.String address1)
    {
        this._address1 = address1;
        
        super.setVoChanged(true);
    } //-- void setAddress1(java.lang.String) 

    /**
     * Method setAddress2Sets the value of field 'address2'.
     * 
     * @param address2 the value of field 'address2'.
     */
    public void setAddress2(java.lang.String address2)
    {
        this._address2 = address2;
        
        super.setVoChanged(true);
    } //-- void setAddress2(java.lang.String) 

    /**
     * Method setAddress3Sets the value of field 'address3'.
     * 
     * @param address3 the value of field 'address3'.
     */
    public void setAddress3(java.lang.String address3)
    {
        this._address3 = address3;
        
        super.setVoChanged(true);
    } //-- void setAddress3(java.lang.String) 

    /**
     * Method setAddress4Sets the value of field 'address4'.
     * 
     * @param address4 the value of field 'address4'.
     */
    public void setAddress4(java.lang.String address4)
    {
        this._address4 = address4;
        
        super.setVoChanged(true);
    } //-- void setAddress4(java.lang.String) 

    /**
     * Method setAgentNumberSets the value of field 'agentNumber'.
     * 
     * @param agentNumber the value of field 'agentNumber'.
     */
    public void setAgentNumber(java.lang.String agentNumber)
    {
        this._agentNumber = agentNumber;
        
        super.setVoChanged(true);
    } //-- void setAgentNumber(java.lang.String) 

    /**
     * Method setAgentTypeSets the value of field 'agentType'.
     * 
     * @param agentType the value of field 'agentType'.
     */
    public void setAgentType(java.lang.String agentType)
    {
        this._agentType = agentType;
        
        super.setVoChanged(true);
    } //-- void setAgentType(java.lang.String) 

    /**
     * Method setCitySets the value of field 'city'.
     * 
     * @param city the value of field 'city'.
     */
    public void setCity(java.lang.String city)
    {
        this._city = city;
        
        super.setVoChanged(true);
    } //-- void setCity(java.lang.String) 

    /**
     * Method setDisbursementSourceSets the value of field
     * 'disbursementSource'.
     * 
     * @param disbursementSource the value of field
     * 'disbursementSource'.
     */
    public void setDisbursementSource(java.lang.String disbursementSource)
    {
        this._disbursementSource = disbursementSource;
        
        super.setVoChanged(true);
    } //-- void setDisbursementSource(java.lang.String) 

    /**
     * Method setFirstNameSets the value of field 'firstName'.
     * 
     * @param firstName the value of field 'firstName'.
     */
    public void setFirstName(java.lang.String firstName)
    {
        this._firstName = firstName;
        
        super.setVoChanged(true);
    } //-- void setFirstName(java.lang.String) 

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
     * Method setNameSuffixSets the value of field 'nameSuffix'.
     * 
     * @param nameSuffix the value of field 'nameSuffix'.
     */
    public void setNameSuffix(java.lang.String nameSuffix)
    {
        this._nameSuffix = nameSuffix;
        
        super.setVoChanged(true);
    } //-- void setNameSuffix(java.lang.String) 

    /**
     * Method setPaymentModeSets the value of field 'paymentMode'.
     * 
     * @param paymentMode the value of field 'paymentMode'.
     */
    public void setPaymentMode(java.lang.String paymentMode)
    {
        this._paymentMode = paymentMode;
        
        super.setVoChanged(true);
    } //-- void setPaymentMode(java.lang.String) 

    /**
     * Method setRedirectAddress1Sets the value of field
     * 'redirectAddress1'.
     * 
     * @param redirectAddress1 the value of field 'redirectAddress1'
     */
    public void setRedirectAddress1(java.lang.String redirectAddress1)
    {
        this._redirectAddress1 = redirectAddress1;
        
        super.setVoChanged(true);
    } //-- void setRedirectAddress1(java.lang.String) 

    /**
     * Method setRedirectAddress2Sets the value of field
     * 'redirectAddress2'.
     * 
     * @param redirectAddress2 the value of field 'redirectAddress2'
     */
    public void setRedirectAddress2(java.lang.String redirectAddress2)
    {
        this._redirectAddress2 = redirectAddress2;
        
        super.setVoChanged(true);
    } //-- void setRedirectAddress2(java.lang.String) 

    /**
     * Method setRedirectAddress3Sets the value of field
     * 'redirectAddress3'.
     * 
     * @param redirectAddress3 the value of field 'redirectAddress3'
     */
    public void setRedirectAddress3(java.lang.String redirectAddress3)
    {
        this._redirectAddress3 = redirectAddress3;
        
        super.setVoChanged(true);
    } //-- void setRedirectAddress3(java.lang.String) 

    /**
     * Method setRedirectAddress4Sets the value of field
     * 'redirectAddress4'.
     * 
     * @param redirectAddress4 the value of field 'redirectAddress4'
     */
    public void setRedirectAddress4(java.lang.String redirectAddress4)
    {
        this._redirectAddress4 = redirectAddress4;
        
        super.setVoChanged(true);
    } //-- void setRedirectAddress4(java.lang.String) 

    /**
     * Method setRedirectCitySets the value of field
     * 'redirectCity'.
     * 
     * @param redirectCity the value of field 'redirectCity'.
     */
    public void setRedirectCity(java.lang.String redirectCity)
    {
        this._redirectCity = redirectCity;
        
        super.setVoChanged(true);
    } //-- void setRedirectCity(java.lang.String) 

    /**
     * Method setRedirectNameSets the value of field
     * 'redirectName'.
     * 
     * @param redirectName the value of field 'redirectName'.
     */
    public void setRedirectName(java.lang.String redirectName)
    {
        this._redirectName = redirectName;
        
        super.setVoChanged(true);
    } //-- void setRedirectName(java.lang.String) 

    /**
     * Method setRedirectNumberSets the value of field
     * 'redirectNumber'.
     * 
     * @param redirectNumber the value of field 'redirectNumber'.
     */
    public void setRedirectNumber(java.lang.String redirectNumber)
    {
        this._redirectNumber = redirectNumber;
        
        super.setVoChanged(true);
    } //-- void setRedirectNumber(java.lang.String) 

    /**
     * Method setRedirectStateSets the value of field
     * 'redirectState'.
     * 
     * @param redirectState the value of field 'redirectState'.
     */
    public void setRedirectState(java.lang.String redirectState)
    {
        this._redirectState = redirectState;
        
        super.setVoChanged(true);
    } //-- void setRedirectState(java.lang.String) 

    /**
     * Method setRedirectZipSets the value of field 'redirectZip'.
     * 
     * @param redirectZip the value of field 'redirectZip'.
     */
    public void setRedirectZip(java.lang.String redirectZip)
    {
        this._redirectZip = redirectZip;
        
        super.setVoChanged(true);
    } //-- void setRedirectZip(java.lang.String) 

    /**
     * Method setReportToSets the value of field 'reportTo'.
     * 
     * @param reportTo the value of field 'reportTo'.
     */
    public void setReportTo(java.lang.String reportTo)
    {
        this._reportTo = reportTo;
        
        super.setVoChanged(true);
    } //-- void setReportTo(java.lang.String) 

    /**
     * Method setStateSets the value of field 'state'.
     * 
     * @param state the value of field 'state'.
     */
    public void setState(java.lang.String state)
    {
        this._state = state;
        
        super.setVoChanged(true);
    } //-- void setState(java.lang.String) 

    /**
     * Method setZipSets the value of field 'zip'.
     * 
     * @param zip the value of field 'zip'.
     */
    public void setZip(java.lang.String zip)
    {
        this._zip = zip;
        
        super.setVoChanged(true);
    } //-- void setZip(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AgentInfoVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AgentInfoVO) Unmarshaller.unmarshal(edit.common.vo.AgentInfoVO.class, reader);
    } //-- edit.common.vo.AgentInfoVO unmarshal(java.io.Reader) 

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
