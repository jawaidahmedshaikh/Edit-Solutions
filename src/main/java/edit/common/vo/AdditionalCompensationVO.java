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
 * Class AdditionalCompensationVO.
 * 
 * @version $Revision$ $Date$
 */
public class AdditionalCompensationVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _additionalCompensationPK
     */
    private long _additionalCompensationPK;

    /**
     * keeps track of state for field: _additionalCompensationPK
     */
    private boolean _has_additionalCompensationPK;

    /**
     * Field _agentContractFK
     */
    private long _agentContractFK;

    /**
     * keeps track of state for field: _agentContractFK
     */
    private boolean _has_agentContractFK;

    /**
     * Field _ADATypeCT
     */
    private java.lang.String _ADATypeCT;

    /**
     * Field _annualizedMax
     */
    private java.math.BigDecimal _annualizedMax;

    /**
     * Field _expenseAllowanceStatus
     */
    private java.lang.String _expenseAllowanceStatus;

    /**
     * Field _bonusCommissionStatus
     */
    private java.lang.String _bonusCommissionStatus;

    /**
     * Field _NY91PercentStatus
     */
    private java.lang.String _NY91PercentStatus;


      //----------------/
     //- Constructors -/
    //----------------/

    public AdditionalCompensationVO() {
        super();
    } //-- edit.common.vo.AdditionalCompensationVO()


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
        
        if (obj instanceof AdditionalCompensationVO) {
        
            AdditionalCompensationVO temp = (AdditionalCompensationVO)obj;
            if (this._additionalCompensationPK != temp._additionalCompensationPK)
                return false;
            if (this._has_additionalCompensationPK != temp._has_additionalCompensationPK)
                return false;
            if (this._agentContractFK != temp._agentContractFK)
                return false;
            if (this._has_agentContractFK != temp._has_agentContractFK)
                return false;
            if (this._ADATypeCT != null) {
                if (temp._ADATypeCT == null) return false;
                else if (!(this._ADATypeCT.equals(temp._ADATypeCT))) 
                    return false;
            }
            else if (temp._ADATypeCT != null)
                return false;
            if (this._annualizedMax != null) {
                if (temp._annualizedMax == null) return false;
                else if (!(this._annualizedMax.equals(temp._annualizedMax))) 
                    return false;
            }
            else if (temp._annualizedMax != null)
                return false;
            if (this._expenseAllowanceStatus != null) {
                if (temp._expenseAllowanceStatus == null) return false;
                else if (!(this._expenseAllowanceStatus.equals(temp._expenseAllowanceStatus))) 
                    return false;
            }
            else if (temp._expenseAllowanceStatus != null)
                return false;
            if (this._bonusCommissionStatus != null) {
                if (temp._bonusCommissionStatus == null) return false;
                else if (!(this._bonusCommissionStatus.equals(temp._bonusCommissionStatus))) 
                    return false;
            }
            else if (temp._bonusCommissionStatus != null)
                return false;
            if (this._NY91PercentStatus != null) {
                if (temp._NY91PercentStatus == null) return false;
                else if (!(this._NY91PercentStatus.equals(temp._NY91PercentStatus))) 
                    return false;
            }
            else if (temp._NY91PercentStatus != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getADATypeCTReturns the value of field 'ADATypeCT'.
     * 
     * @return the value of field 'ADATypeCT'.
     */
    public java.lang.String getADATypeCT()
    {
        return this._ADATypeCT;
    } //-- java.lang.String getADATypeCT() 

    /**
     * Method getAdditionalCompensationPKReturns the value of field
     * 'additionalCompensationPK'.
     * 
     * @return the value of field 'additionalCompensationPK'.
     */
    public long getAdditionalCompensationPK()
    {
        return this._additionalCompensationPK;
    } //-- long getAdditionalCompensationPK() 

    /**
     * Method getAgentContractFKReturns the value of field
     * 'agentContractFK'.
     * 
     * @return the value of field 'agentContractFK'.
     */
    public long getAgentContractFK()
    {
        return this._agentContractFK;
    } //-- long getAgentContractFK() 

    /**
     * Method getAnnualizedMaxReturns the value of field
     * 'annualizedMax'.
     * 
     * @return the value of field 'annualizedMax'.
     */
    public java.math.BigDecimal getAnnualizedMax()
    {
        return this._annualizedMax;
    } //-- java.math.BigDecimal getAnnualizedMax() 

    /**
     * Method getBonusCommissionStatusReturns the value of field
     * 'bonusCommissionStatus'.
     * 
     * @return the value of field 'bonusCommissionStatus'.
     */
    public java.lang.String getBonusCommissionStatus()
    {
        return this._bonusCommissionStatus;
    } //-- java.lang.String getBonusCommissionStatus() 

    /**
     * Method getExpenseAllowanceStatusReturns the value of field
     * 'expenseAllowanceStatus'.
     * 
     * @return the value of field 'expenseAllowanceStatus'.
     */
    public java.lang.String getExpenseAllowanceStatus()
    {
        return this._expenseAllowanceStatus;
    } //-- java.lang.String getExpenseAllowanceStatus() 

    /**
     * Method getNY91PercentStatusReturns the value of field
     * 'NY91PercentStatus'.
     * 
     * @return the value of field 'NY91PercentStatus'.
     */
    public java.lang.String getNY91PercentStatus()
    {
        return this._NY91PercentStatus;
    } //-- java.lang.String getNY91PercentStatus() 

    /**
     * Method hasAdditionalCompensationPK
     */
    public boolean hasAdditionalCompensationPK()
    {
        return this._has_additionalCompensationPK;
    } //-- boolean hasAdditionalCompensationPK() 

    /**
     * Method hasAgentContractFK
     */
    public boolean hasAgentContractFK()
    {
        return this._has_agentContractFK;
    } //-- boolean hasAgentContractFK() 

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
     * Method setADATypeCTSets the value of field 'ADATypeCT'.
     * 
     * @param ADATypeCT the value of field 'ADATypeCT'.
     */
    public void setADATypeCT(java.lang.String ADATypeCT)
    {
        this._ADATypeCT = ADATypeCT;
        
        super.setVoChanged(true);
    } //-- void setADATypeCT(java.lang.String) 

    /**
     * Method setAdditionalCompensationPKSets the value of field
     * 'additionalCompensationPK'.
     * 
     * @param additionalCompensationPK the value of field
     * 'additionalCompensationPK'.
     */
    public void setAdditionalCompensationPK(long additionalCompensationPK)
    {
        this._additionalCompensationPK = additionalCompensationPK;
        
        super.setVoChanged(true);
        this._has_additionalCompensationPK = true;
    } //-- void setAdditionalCompensationPK(long) 

    /**
     * Method setAgentContractFKSets the value of field
     * 'agentContractFK'.
     * 
     * @param agentContractFK the value of field 'agentContractFK'.
     */
    public void setAgentContractFK(long agentContractFK)
    {
        this._agentContractFK = agentContractFK;
        
        super.setVoChanged(true);
        this._has_agentContractFK = true;
    } //-- void setAgentContractFK(long) 

    /**
     * Method setAnnualizedMaxSets the value of field
     * 'annualizedMax'.
     * 
     * @param annualizedMax the value of field 'annualizedMax'.
     */
    public void setAnnualizedMax(java.math.BigDecimal annualizedMax)
    {
        this._annualizedMax = annualizedMax;
        
        super.setVoChanged(true);
    } //-- void setAnnualizedMax(java.math.BigDecimal) 

    /**
     * Method setBonusCommissionStatusSets the value of field
     * 'bonusCommissionStatus'.
     * 
     * @param bonusCommissionStatus the value of field
     * 'bonusCommissionStatus'.
     */
    public void setBonusCommissionStatus(java.lang.String bonusCommissionStatus)
    {
        this._bonusCommissionStatus = bonusCommissionStatus;
        
        super.setVoChanged(true);
    } //-- void setBonusCommissionStatus(java.lang.String) 

    /**
     * Method setExpenseAllowanceStatusSets the value of field
     * 'expenseAllowanceStatus'.
     * 
     * @param expenseAllowanceStatus the value of field
     * 'expenseAllowanceStatus'.
     */
    public void setExpenseAllowanceStatus(java.lang.String expenseAllowanceStatus)
    {
        this._expenseAllowanceStatus = expenseAllowanceStatus;
        
        super.setVoChanged(true);
    } //-- void setExpenseAllowanceStatus(java.lang.String) 

    /**
     * Method setNY91PercentStatusSets the value of field
     * 'NY91PercentStatus'.
     * 
     * @param NY91PercentStatus the value of field
     * 'NY91PercentStatus'.
     */
    public void setNY91PercentStatus(java.lang.String NY91PercentStatus)
    {
        this._NY91PercentStatus = NY91PercentStatus;
        
        super.setVoChanged(true);
    } //-- void setNY91PercentStatus(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AdditionalCompensationVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AdditionalCompensationVO) Unmarshaller.unmarshal(edit.common.vo.AdditionalCompensationVO.class, reader);
    } //-- edit.common.vo.AdditionalCompensationVO unmarshal(java.io.Reader) 

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
