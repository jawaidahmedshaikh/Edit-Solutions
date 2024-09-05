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
 * Class AccountEffectVO.
 *
 * @version $Revision$ $Date$
 */
public class AccountEffectVO extends edit.common.vo.VOObject
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _accountNumber
     */
    private java.lang.String _accountNumber;

    /**
     * Field _accountName
     */
    private java.lang.String _accountName;

    /**
     * Field _accountEffect
     */
    private java.lang.String _accountEffect;

    /**
     * Field _accountAmount
     */
    private java.math.BigDecimal _accountAmount;


      //----------------/
     //- Constructors -/
    //----------------/

    public AccountEffectVO() {
        super();
    } //-- edit.common.vo.AccountEffectVO()


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

        if (obj instanceof AccountEffectVO) {

            AccountEffectVO temp = (AccountEffectVO)obj;
            if (this._accountNumber != null) {
                if (temp._accountNumber == null) return false;
                else if (!(this._accountNumber.equals(temp._accountNumber)))
                    return false;
            }
            else if (temp._accountNumber != null)
                return false;
            if (this._accountName != null) {
                if (temp._accountName == null) return false;
                else if (!(this._accountName.equals(temp._accountName)))
                    return false;
            }
            else if (temp._accountName != null)
                return false;
            if (this._accountEffect != null) {
                if (temp._accountEffect == null) return false;
                else if (!(this._accountEffect.equals(temp._accountEffect)))
                    return false;
            }
            else if (temp._accountEffect != null)
                return false;
            if (this._accountAmount != null) {
                if (temp._accountAmount == null) return false;
                else if (!(this._accountAmount.equals(temp._accountAmount)))
                    return false;
            }
            else if (temp._accountAmount != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object)

    /**
     * Method getAccountAmountReturns the value of field
     * 'accountAmount'.
     *
     * @return the value of field 'accountAmount'.
     */
    public java.math.BigDecimal getAccountAmount()
    {
        return this._accountAmount;
    } //-- java.math.BigDecimal getAccountAmount()

    /**
     * Method getAccountEffectReturns the value of field
     * 'accountEffect'.
     *
     * @return the value of field 'accountEffect'.
     */
    public java.lang.String getAccountEffect()
    {
        return this._accountEffect;
    } //-- java.lang.String getAccountEffect()

    /**
     * Method getAccountNameReturns the value of field
     * 'accountName'.
     *
     * @return the value of field 'accountName'.
     */
    public java.lang.String getAccountName()
    {
        return this._accountName;
    } //-- java.lang.String getAccountName()

    /**
     * Method getAccountNumberReturns the value of field
     * 'accountNumber'.
     *
     * @return the value of field 'accountNumber'.
     */
    public java.lang.String getAccountNumber()
    {
        return this._accountNumber;
    } //-- java.lang.String getAccountNumber()

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
     * Method setAccountAmountSets the value of field
     * 'accountAmount'.
     *
     * @param accountAmount the value of field 'accountAmount'.
     */
    public void setAccountAmount(java.math.BigDecimal accountAmount)
    {
        this._accountAmount = accountAmount;

        super.setVoChanged(true);
    } //-- void setAccountAmount(java.math.BigDecimal)

    /**
     * Method setAccountEffectSets the value of field
     * 'accountEffect'.
     *
     * @param accountEffect the value of field 'accountEffect'.
     */
    public void setAccountEffect(java.lang.String accountEffect)
    {
        this._accountEffect = accountEffect;

        super.setVoChanged(true);
    } //-- void setAccountEffect(java.lang.String)

    /**
     * Method setAccountNameSets the value of field 'accountName'.
     *
     * @param accountName the value of field 'accountName'.
     */
    public void setAccountName(java.lang.String accountName)
    {
        this._accountName = accountName;

        super.setVoChanged(true);
    } //-- void setAccountName(java.lang.String)

    /**
     * Method setAccountNumberSets the value of field
     * 'accountNumber'.
     *
     * @param accountNumber the value of field 'accountNumber'.
     */
    public void setAccountNumber(java.lang.String accountNumber)
    {
        this._accountNumber = accountNumber;

        super.setVoChanged(true);
    } //-- void setAccountNumber(java.lang.String)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static edit.common.vo.AccountEffectVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AccountEffectVO) Unmarshaller.unmarshal(edit.common.vo.AccountEffectVO.class, reader);
    } //-- edit.common.vo.AccountEffectVO unmarshal(java.io.Reader)

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
