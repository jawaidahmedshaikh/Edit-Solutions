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
 * Class BonusStatementVO.
 * 
 * @version $Revision$ $Date$
 */
public class BonusStatementVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _statementDate
     */
    private java.lang.String _statementDate;

    /**
     * Field _participatingAgentInfoVO
     */
    private edit.common.vo.ParticipatingAgentInfoVO _participatingAgentInfoVO;

    /**
     * Field _bonusEarningsVO
     */
    private edit.common.vo.BonusEarningsVO _bonusEarningsVO;

    /**
     * Field _lastStatementAmount
     */
    private java.math.BigDecimal _lastStatementAmount;

    /**
     * Field _lastStatementDate
     */
    private java.lang.String _lastStatementDate;


      //----------------/
     //- Constructors -/
    //----------------/

    public BonusStatementVO() {
        super();
    } //-- edit.common.vo.BonusStatementVO()


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
        
        if (obj instanceof BonusStatementVO) {
        
            BonusStatementVO temp = (BonusStatementVO)obj;
            if (this._statementDate != null) {
                if (temp._statementDate == null) return false;
                else if (!(this._statementDate.equals(temp._statementDate))) 
                    return false;
            }
            else if (temp._statementDate != null)
                return false;
            if (this._participatingAgentInfoVO != null) {
                if (temp._participatingAgentInfoVO == null) return false;
                else if (!(this._participatingAgentInfoVO.equals(temp._participatingAgentInfoVO))) 
                    return false;
            }
            else if (temp._participatingAgentInfoVO != null)
                return false;
            if (this._bonusEarningsVO != null) {
                if (temp._bonusEarningsVO == null) return false;
                else if (!(this._bonusEarningsVO.equals(temp._bonusEarningsVO))) 
                    return false;
            }
            else if (temp._bonusEarningsVO != null)
                return false;
            if (this._lastStatementAmount != null) {
                if (temp._lastStatementAmount == null) return false;
                else if (!(this._lastStatementAmount.equals(temp._lastStatementAmount))) 
                    return false;
            }
            else if (temp._lastStatementAmount != null)
                return false;
            if (this._lastStatementDate != null) {
                if (temp._lastStatementDate == null) return false;
                else if (!(this._lastStatementDate.equals(temp._lastStatementDate))) 
                    return false;
            }
            else if (temp._lastStatementDate != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBonusEarningsVOReturns the value of field
     * 'bonusEarningsVO'.
     * 
     * @return the value of field 'bonusEarningsVO'.
     */
    public edit.common.vo.BonusEarningsVO getBonusEarningsVO()
    {
        return this._bonusEarningsVO;
    } //-- edit.common.vo.BonusEarningsVO getBonusEarningsVO() 

    /**
     * Method getLastStatementAmountReturns the value of field
     * 'lastStatementAmount'.
     * 
     * @return the value of field 'lastStatementAmount'.
     */
    public java.math.BigDecimal getLastStatementAmount()
    {
        return this._lastStatementAmount;
    } //-- java.math.BigDecimal getLastStatementAmount() 

    /**
     * Method getLastStatementDateReturns the value of field
     * 'lastStatementDate'.
     * 
     * @return the value of field 'lastStatementDate'.
     */
    public java.lang.String getLastStatementDate()
    {
        return this._lastStatementDate;
    } //-- java.lang.String getLastStatementDate() 

    /**
     * Method getParticipatingAgentInfoVOReturns the value of field
     * 'participatingAgentInfoVO'.
     * 
     * @return the value of field 'participatingAgentInfoVO'.
     */
    public edit.common.vo.ParticipatingAgentInfoVO getParticipatingAgentInfoVO()
    {
        return this._participatingAgentInfoVO;
    } //-- edit.common.vo.ParticipatingAgentInfoVO getParticipatingAgentInfoVO() 

    /**
     * Method getStatementDateReturns the value of field
     * 'statementDate'.
     * 
     * @return the value of field 'statementDate'.
     */
    public java.lang.String getStatementDate()
    {
        return this._statementDate;
    } //-- java.lang.String getStatementDate() 

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
     * Method setBonusEarningsVOSets the value of field
     * 'bonusEarningsVO'.
     * 
     * @param bonusEarningsVO the value of field 'bonusEarningsVO'.
     */
    public void setBonusEarningsVO(edit.common.vo.BonusEarningsVO bonusEarningsVO)
    {
        this._bonusEarningsVO = bonusEarningsVO;
    } //-- void setBonusEarningsVO(edit.common.vo.BonusEarningsVO) 

    /**
     * Method setLastStatementAmountSets the value of field
     * 'lastStatementAmount'.
     * 
     * @param lastStatementAmount the value of field
     * 'lastStatementAmount'.
     */
    public void setLastStatementAmount(java.math.BigDecimal lastStatementAmount)
    {
        this._lastStatementAmount = lastStatementAmount;
        
        super.setVoChanged(true);
    } //-- void setLastStatementAmount(java.math.BigDecimal) 

    /**
     * Method setLastStatementDateSets the value of field
     * 'lastStatementDate'.
     * 
     * @param lastStatementDate the value of field
     * 'lastStatementDate'.
     */
    public void setLastStatementDate(java.lang.String lastStatementDate)
    {
        this._lastStatementDate = lastStatementDate;
        
        super.setVoChanged(true);
    } //-- void setLastStatementDate(java.lang.String) 

    /**
     * Method setParticipatingAgentInfoVOSets the value of field
     * 'participatingAgentInfoVO'.
     * 
     * @param participatingAgentInfoVO the value of field
     * 'participatingAgentInfoVO'.
     */
    public void setParticipatingAgentInfoVO(edit.common.vo.ParticipatingAgentInfoVO participatingAgentInfoVO)
    {
        this._participatingAgentInfoVO = participatingAgentInfoVO;
    } //-- void setParticipatingAgentInfoVO(edit.common.vo.ParticipatingAgentInfoVO) 

    /**
     * Method setStatementDateSets the value of field
     * 'statementDate'.
     * 
     * @param statementDate the value of field 'statementDate'.
     */
    public void setStatementDate(java.lang.String statementDate)
    {
        this._statementDate = statementDate;
        
        super.setVoChanged(true);
    } //-- void setStatementDate(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BonusStatementVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BonusStatementVO) Unmarshaller.unmarshal(edit.common.vo.BonusStatementVO.class, reader);
    } //-- edit.common.vo.BonusStatementVO unmarshal(java.io.Reader) 

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
