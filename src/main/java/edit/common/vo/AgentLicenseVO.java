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
 * Class AgentLicenseVO.
 * 
 * @version $Revision$ $Date$
 */
public class AgentLicenseVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _agentLicensePK
     */
    private long _agentLicensePK;

    /**
     * keeps track of state for field: _agentLicensePK
     */
    private boolean _has_agentLicensePK;

    /**
     * Field _agentFK
     */
    private long _agentFK;

    /**
     * keeps track of state for field: _agentFK
     */
    private boolean _has_agentFK;

    /**
     * Field _NASD
     */
    private java.lang.String _NASD;

    /**
     * Field _NASDEffectiveDate
     */
    private java.lang.String _NASDEffectiveDate;

    /**
     * Field _NASDRenewalDate
     */
    private java.lang.String _NASDRenewalDate;

    /**
     * Field _stateCT
     */
    private java.lang.String _stateCT;

    /**
     * Field _residentCT
     */
    private java.lang.String _residentCT;

    /**
     * Field _stateNASD
     */
    private java.lang.String _stateNASD;

    /**
     * Field _productTypeCT
     */
    private java.lang.String _productTypeCT;

    /**
     * Field _licenseNumber
     */
    private java.lang.String _licenseNumber;

    /**
     * Field _licenseTypeCT
     */
    private java.lang.String _licenseTypeCT;

    /**
     * Field _renewTermStatusCT
     */
    private java.lang.String _renewTermStatusCT;

    /**
     * Field _statusCT
     */
    private java.lang.String _statusCT;

    /**
     * Field _licEffDate
     */
    private java.lang.String _licEffDate;

    /**
     * Field _licExpDate
     */
    private java.lang.String _licExpDate;

    /**
     * Field _licTermDate
     */
    private java.lang.String _licTermDate;

    /**
     * Field _errorOmmissStatus
     */
    private java.lang.String _errorOmmissStatus;

    /**
     * Field _errorOmmissExpDate
     */
    private java.lang.String _errorOmmissExpDate;


      //----------------/
     //- Constructors -/
    //----------------/

    public AgentLicenseVO() {
        super();
    } //-- edit.common.vo.AgentLicenseVO()


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
        
        if (obj instanceof AgentLicenseVO) {
        
            AgentLicenseVO temp = (AgentLicenseVO)obj;
            if (this._agentLicensePK != temp._agentLicensePK)
                return false;
            if (this._has_agentLicensePK != temp._has_agentLicensePK)
                return false;
            if (this._agentFK != temp._agentFK)
                return false;
            if (this._has_agentFK != temp._has_agentFK)
                return false;
            if (this._NASD != null) {
                if (temp._NASD == null) return false;
                else if (!(this._NASD.equals(temp._NASD))) 
                    return false;
            }
            else if (temp._NASD != null)
                return false;
            if (this._NASDEffectiveDate != null) {
                if (temp._NASDEffectiveDate == null) return false;
                else if (!(this._NASDEffectiveDate.equals(temp._NASDEffectiveDate))) 
                    return false;
            }
            else if (temp._NASDEffectiveDate != null)
                return false;
            if (this._NASDRenewalDate != null) {
                if (temp._NASDRenewalDate == null) return false;
                else if (!(this._NASDRenewalDate.equals(temp._NASDRenewalDate))) 
                    return false;
            }
            else if (temp._NASDRenewalDate != null)
                return false;
            if (this._stateCT != null) {
                if (temp._stateCT == null) return false;
                else if (!(this._stateCT.equals(temp._stateCT))) 
                    return false;
            }
            else if (temp._stateCT != null)
                return false;
            if (this._residentCT != null) {
                if (temp._residentCT == null) return false;
                else if (!(this._residentCT.equals(temp._residentCT))) 
                    return false;
            }
            else if (temp._residentCT != null)
                return false;
            if (this._stateNASD != null) {
                if (temp._stateNASD == null) return false;
                else if (!(this._stateNASD.equals(temp._stateNASD))) 
                    return false;
            }
            else if (temp._stateNASD != null)
                return false;
            if (this._productTypeCT != null) {
                if (temp._productTypeCT == null) return false;
                else if (!(this._productTypeCT.equals(temp._productTypeCT))) 
                    return false;
            }
            else if (temp._productTypeCT != null)
                return false;
            if (this._licenseNumber != null) {
                if (temp._licenseNumber == null) return false;
                else if (!(this._licenseNumber.equals(temp._licenseNumber))) 
                    return false;
            }
            else if (temp._licenseNumber != null)
                return false;
            if (this._licenseTypeCT != null) {
                if (temp._licenseTypeCT == null) return false;
                else if (!(this._licenseTypeCT.equals(temp._licenseTypeCT))) 
                    return false;
            }
            else if (temp._licenseTypeCT != null)
                return false;
            if (this._renewTermStatusCT != null) {
                if (temp._renewTermStatusCT == null) return false;
                else if (!(this._renewTermStatusCT.equals(temp._renewTermStatusCT))) 
                    return false;
            }
            else if (temp._renewTermStatusCT != null)
                return false;
            if (this._statusCT != null) {
                if (temp._statusCT == null) return false;
                else if (!(this._statusCT.equals(temp._statusCT))) 
                    return false;
            }
            else if (temp._statusCT != null)
                return false;
            if (this._licEffDate != null) {
                if (temp._licEffDate == null) return false;
                else if (!(this._licEffDate.equals(temp._licEffDate))) 
                    return false;
            }
            else if (temp._licEffDate != null)
                return false;
            if (this._licExpDate != null) {
                if (temp._licExpDate == null) return false;
                else if (!(this._licExpDate.equals(temp._licExpDate))) 
                    return false;
            }
            else if (temp._licExpDate != null)
                return false;
            if (this._licTermDate != null) {
                if (temp._licTermDate == null) return false;
                else if (!(this._licTermDate.equals(temp._licTermDate))) 
                    return false;
            }
            else if (temp._licTermDate != null)
                return false;
            if (this._errorOmmissStatus != null) {
                if (temp._errorOmmissStatus == null) return false;
                else if (!(this._errorOmmissStatus.equals(temp._errorOmmissStatus))) 
                    return false;
            }
            else if (temp._errorOmmissStatus != null)
                return false;
            if (this._errorOmmissExpDate != null) {
                if (temp._errorOmmissExpDate == null) return false;
                else if (!(this._errorOmmissExpDate.equals(temp._errorOmmissExpDate))) 
                    return false;
            }
            else if (temp._errorOmmissExpDate != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentFKReturns the value of field 'agentFK'.
     * 
     * @return the value of field 'agentFK'.
     */
    public long getAgentFK()
    {
        return this._agentFK;
    } //-- long getAgentFK() 

    /**
     * Method getAgentLicensePKReturns the value of field
     * 'agentLicensePK'.
     * 
     * @return the value of field 'agentLicensePK'.
     */
    public long getAgentLicensePK()
    {
        return this._agentLicensePK;
    } //-- long getAgentLicensePK() 

    /**
     * Method getErrorOmmissExpDateReturns the value of field
     * 'errorOmmissExpDate'.
     * 
     * @return the value of field 'errorOmmissExpDate'.
     */
    public java.lang.String getErrorOmmissExpDate()
    {
        return this._errorOmmissExpDate;
    } //-- java.lang.String getErrorOmmissExpDate() 

    /**
     * Method getErrorOmmissStatusReturns the value of field
     * 'errorOmmissStatus'.
     * 
     * @return the value of field 'errorOmmissStatus'.
     */
    public java.lang.String getErrorOmmissStatus()
    {
        return this._errorOmmissStatus;
    } //-- java.lang.String getErrorOmmissStatus() 

    /**
     * Method getLicEffDateReturns the value of field 'licEffDate'.
     * 
     * @return the value of field 'licEffDate'.
     */
    public java.lang.String getLicEffDate()
    {
        return this._licEffDate;
    } //-- java.lang.String getLicEffDate() 

    /**
     * Method getLicExpDateReturns the value of field 'licExpDate'.
     * 
     * @return the value of field 'licExpDate'.
     */
    public java.lang.String getLicExpDate()
    {
        return this._licExpDate;
    } //-- java.lang.String getLicExpDate() 

    /**
     * Method getLicTermDateReturns the value of field
     * 'licTermDate'.
     * 
     * @return the value of field 'licTermDate'.
     */
    public java.lang.String getLicTermDate()
    {
        return this._licTermDate;
    } //-- java.lang.String getLicTermDate() 

    /**
     * Method getLicenseNumberReturns the value of field
     * 'licenseNumber'.
     * 
     * @return the value of field 'licenseNumber'.
     */
    public java.lang.String getLicenseNumber()
    {
        return this._licenseNumber;
    } //-- java.lang.String getLicenseNumber() 

    /**
     * Method getLicenseTypeCTReturns the value of field
     * 'licenseTypeCT'.
     * 
     * @return the value of field 'licenseTypeCT'.
     */
    public java.lang.String getLicenseTypeCT()
    {
        return this._licenseTypeCT;
    } //-- java.lang.String getLicenseTypeCT() 

    /**
     * Method getNASDReturns the value of field 'NASD'.
     * 
     * @return the value of field 'NASD'.
     */
    public java.lang.String getNASD()
    {
        return this._NASD;
    } //-- java.lang.String getNASD() 

    /**
     * Method getNASDEffectiveDateReturns the value of field
     * 'NASDEffectiveDate'.
     * 
     * @return the value of field 'NASDEffectiveDate'.
     */
    public java.lang.String getNASDEffectiveDate()
    {
        return this._NASDEffectiveDate;
    } //-- java.lang.String getNASDEffectiveDate() 

    /**
     * Method getNASDRenewalDateReturns the value of field
     * 'NASDRenewalDate'.
     * 
     * @return the value of field 'NASDRenewalDate'.
     */
    public java.lang.String getNASDRenewalDate()
    {
        return this._NASDRenewalDate;
    } //-- java.lang.String getNASDRenewalDate() 

    /**
     * Method getProductTypeCTReturns the value of field
     * 'productTypeCT'.
     * 
     * @return the value of field 'productTypeCT'.
     */
    public java.lang.String getProductTypeCT()
    {
        return this._productTypeCT;
    } //-- java.lang.String getProductTypeCT() 

    /**
     * Method getRenewTermStatusCTReturns the value of field
     * 'renewTermStatusCT'.
     * 
     * @return the value of field 'renewTermStatusCT'.
     */
    public java.lang.String getRenewTermStatusCT()
    {
        return this._renewTermStatusCT;
    } //-- java.lang.String getRenewTermStatusCT() 

    /**
     * Method getResidentCTReturns the value of field 'residentCT'.
     * 
     * @return the value of field 'residentCT'.
     */
    public java.lang.String getResidentCT()
    {
        return this._residentCT;
    } //-- java.lang.String getResidentCT() 

    /**
     * Method getStateCTReturns the value of field 'stateCT'.
     * 
     * @return the value of field 'stateCT'.
     */
    public java.lang.String getStateCT()
    {
        return this._stateCT;
    } //-- java.lang.String getStateCT() 

    /**
     * Method getStateNASDReturns the value of field 'stateNASD'.
     * 
     * @return the value of field 'stateNASD'.
     */
    public java.lang.String getStateNASD()
    {
        return this._stateNASD;
    } //-- java.lang.String getStateNASD() 

    /**
     * Method getStatusCTReturns the value of field 'statusCT'.
     * 
     * @return the value of field 'statusCT'.
     */
    public java.lang.String getStatusCT()
    {
        return this._statusCT;
    } //-- java.lang.String getStatusCT() 

    /**
     * Method hasAgentFK
     */
    public boolean hasAgentFK()
    {
        return this._has_agentFK;
    } //-- boolean hasAgentFK() 

    /**
     * Method hasAgentLicensePK
     */
    public boolean hasAgentLicensePK()
    {
        return this._has_agentLicensePK;
    } //-- boolean hasAgentLicensePK() 

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
     * Method setAgentFKSets the value of field 'agentFK'.
     * 
     * @param agentFK the value of field 'agentFK'.
     */
    public void setAgentFK(long agentFK)
    {
        this._agentFK = agentFK;
        
        super.setVoChanged(true);
        this._has_agentFK = true;
    } //-- void setAgentFK(long) 

    /**
     * Method setAgentLicensePKSets the value of field
     * 'agentLicensePK'.
     * 
     * @param agentLicensePK the value of field 'agentLicensePK'.
     */
    public void setAgentLicensePK(long agentLicensePK)
    {
        this._agentLicensePK = agentLicensePK;
        
        super.setVoChanged(true);
        this._has_agentLicensePK = true;
    } //-- void setAgentLicensePK(long) 

    /**
     * Method setErrorOmmissExpDateSets the value of field
     * 'errorOmmissExpDate'.
     * 
     * @param errorOmmissExpDate the value of field
     * 'errorOmmissExpDate'.
     */
    public void setErrorOmmissExpDate(java.lang.String errorOmmissExpDate)
    {
        this._errorOmmissExpDate = errorOmmissExpDate;
        
        super.setVoChanged(true);
    } //-- void setErrorOmmissExpDate(java.lang.String) 

    /**
     * Method setErrorOmmissStatusSets the value of field
     * 'errorOmmissStatus'.
     * 
     * @param errorOmmissStatus the value of field
     * 'errorOmmissStatus'.
     */
    public void setErrorOmmissStatus(java.lang.String errorOmmissStatus)
    {
        this._errorOmmissStatus = errorOmmissStatus;
        
        super.setVoChanged(true);
    } //-- void setErrorOmmissStatus(java.lang.String) 

    /**
     * Method setLicEffDateSets the value of field 'licEffDate'.
     * 
     * @param licEffDate the value of field 'licEffDate'.
     */
    public void setLicEffDate(java.lang.String licEffDate)
    {
        this._licEffDate = licEffDate;
        
        super.setVoChanged(true);
    } //-- void setLicEffDate(java.lang.String) 

    /**
     * Method setLicExpDateSets the value of field 'licExpDate'.
     * 
     * @param licExpDate the value of field 'licExpDate'.
     */
    public void setLicExpDate(java.lang.String licExpDate)
    {
        this._licExpDate = licExpDate;
        
        super.setVoChanged(true);
    } //-- void setLicExpDate(java.lang.String) 

    /**
     * Method setLicTermDateSets the value of field 'licTermDate'.
     * 
     * @param licTermDate the value of field 'licTermDate'.
     */
    public void setLicTermDate(java.lang.String licTermDate)
    {
        this._licTermDate = licTermDate;
        
        super.setVoChanged(true);
    } //-- void setLicTermDate(java.lang.String) 

    /**
     * Method setLicenseNumberSets the value of field
     * 'licenseNumber'.
     * 
     * @param licenseNumber the value of field 'licenseNumber'.
     */
    public void setLicenseNumber(java.lang.String licenseNumber)
    {
        this._licenseNumber = licenseNumber;
        
        super.setVoChanged(true);
    } //-- void setLicenseNumber(java.lang.String) 

    /**
     * Method setLicenseTypeCTSets the value of field
     * 'licenseTypeCT'.
     * 
     * @param licenseTypeCT the value of field 'licenseTypeCT'.
     */
    public void setLicenseTypeCT(java.lang.String licenseTypeCT)
    {
        this._licenseTypeCT = licenseTypeCT;
        
        super.setVoChanged(true);
    } //-- void setLicenseTypeCT(java.lang.String) 

    /**
     * Method setNASDSets the value of field 'NASD'.
     * 
     * @param NASD the value of field 'NASD'.
     */
    public void setNASD(java.lang.String NASD)
    {
        this._NASD = NASD;
        
        super.setVoChanged(true);
    } //-- void setNASD(java.lang.String) 

    /**
     * Method setNASDEffectiveDateSets the value of field
     * 'NASDEffectiveDate'.
     * 
     * @param NASDEffectiveDate the value of field
     * 'NASDEffectiveDate'.
     */
    public void setNASDEffectiveDate(java.lang.String NASDEffectiveDate)
    {
        this._NASDEffectiveDate = NASDEffectiveDate;
        
        super.setVoChanged(true);
    } //-- void setNASDEffectiveDate(java.lang.String) 

    /**
     * Method setNASDRenewalDateSets the value of field
     * 'NASDRenewalDate'.
     * 
     * @param NASDRenewalDate the value of field 'NASDRenewalDate'.
     */
    public void setNASDRenewalDate(java.lang.String NASDRenewalDate)
    {
        this._NASDRenewalDate = NASDRenewalDate;
        
        super.setVoChanged(true);
    } //-- void setNASDRenewalDate(java.lang.String) 

    /**
     * Method setProductTypeCTSets the value of field
     * 'productTypeCT'.
     * 
     * @param productTypeCT the value of field 'productTypeCT'.
     */
    public void setProductTypeCT(java.lang.String productTypeCT)
    {
        this._productTypeCT = productTypeCT;
        
        super.setVoChanged(true);
    } //-- void setProductTypeCT(java.lang.String) 

    /**
     * Method setRenewTermStatusCTSets the value of field
     * 'renewTermStatusCT'.
     * 
     * @param renewTermStatusCT the value of field
     * 'renewTermStatusCT'.
     */
    public void setRenewTermStatusCT(java.lang.String renewTermStatusCT)
    {
        this._renewTermStatusCT = renewTermStatusCT;
        
        super.setVoChanged(true);
    } //-- void setRenewTermStatusCT(java.lang.String) 

    /**
     * Method setResidentCTSets the value of field 'residentCT'.
     * 
     * @param residentCT the value of field 'residentCT'.
     */
    public void setResidentCT(java.lang.String residentCT)
    {
        this._residentCT = residentCT;
        
        super.setVoChanged(true);
    } //-- void setResidentCT(java.lang.String) 

    /**
     * Method setStateCTSets the value of field 'stateCT'.
     * 
     * @param stateCT the value of field 'stateCT'.
     */
    public void setStateCT(java.lang.String stateCT)
    {
        this._stateCT = stateCT;
        
        super.setVoChanged(true);
    } //-- void setStateCT(java.lang.String) 

    /**
     * Method setStateNASDSets the value of field 'stateNASD'.
     * 
     * @param stateNASD the value of field 'stateNASD'.
     */
    public void setStateNASD(java.lang.String stateNASD)
    {
        this._stateNASD = stateNASD;
        
        super.setVoChanged(true);
    } //-- void setStateNASD(java.lang.String) 

    /**
     * Method setStatusCTSets the value of field 'statusCT'.
     * 
     * @param statusCT the value of field 'statusCT'.
     */
    public void setStatusCT(java.lang.String statusCT)
    {
        this._statusCT = statusCT;
        
        super.setVoChanged(true);
    } //-- void setStatusCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AgentLicenseVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AgentLicenseVO) Unmarshaller.unmarshal(edit.common.vo.AgentLicenseVO.class, reader);
    } //-- edit.common.vo.AgentLicenseVO unmarshal(java.io.Reader) 

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
