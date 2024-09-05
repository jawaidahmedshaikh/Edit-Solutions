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
 * Class RealTimeActivityVO.
 * 
 * @version $Revision$ $Date$
 */
public class RealTimeActivityVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _realTimeActivityPK
     */
    private long _realTimeActivityPK;

    /**
     * keeps track of state for field: _realTimeActivityPK
     */
    private boolean _has_realTimeActivityPK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _statusInd
     */
    private java.lang.String _statusInd;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _amount
     */
    private java.math.BigDecimal _amount;

    /**
     * Field _message
     */
    private java.lang.String _message;

    /**
     * Field _transactionTypeCT
     */
    private java.lang.String _transactionTypeCT;


      //----------------/
     //- Constructors -/
    //----------------/

    public RealTimeActivityVO() {
        super();
    } //-- edit.common.vo.RealTimeActivityVO()


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
        
        if (obj instanceof RealTimeActivityVO) {
        
            RealTimeActivityVO temp = (RealTimeActivityVO)obj;
            if (this._realTimeActivityPK != temp._realTimeActivityPK)
                return false;
            if (this._has_realTimeActivityPK != temp._has_realTimeActivityPK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._statusInd != null) {
                if (temp._statusInd == null) return false;
                else if (!(this._statusInd.equals(temp._statusInd))) 
                    return false;
            }
            else if (temp._statusInd != null)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._amount != null) {
                if (temp._amount == null) return false;
                else if (!(this._amount.equals(temp._amount))) 
                    return false;
            }
            else if (temp._amount != null)
                return false;
            if (this._message != null) {
                if (temp._message == null) return false;
                else if (!(this._message.equals(temp._message))) 
                    return false;
            }
            else if (temp._message != null)
                return false;
            if (this._transactionTypeCT != null) {
                if (temp._transactionTypeCT == null) return false;
                else if (!(this._transactionTypeCT.equals(temp._transactionTypeCT))) 
                    return false;
            }
            else if (temp._transactionTypeCT != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAmountReturns the value of field 'amount'.
     * 
     * @return the value of field 'amount'.
     */
    public java.math.BigDecimal getAmount()
    {
        return this._amount;
    } //-- java.math.BigDecimal getAmount() 

    /**
     * Method getEffectiveDateReturns the value of field
     * 'effectiveDate'.
     * 
     * @return the value of field 'effectiveDate'.
     */
    public java.lang.String getEffectiveDate()
    {
        return this._effectiveDate;
    } //-- java.lang.String getEffectiveDate() 

    /**
     * Method getMessageReturns the value of field 'message'.
     * 
     * @return the value of field 'message'.
     */
    public java.lang.String getMessage()
    {
        return this._message;
    } //-- java.lang.String getMessage() 

    /**
     * Method getRealTimeActivityPKReturns the value of field
     * 'realTimeActivityPK'.
     * 
     * @return the value of field 'realTimeActivityPK'.
     */
    public long getRealTimeActivityPK()
    {
        return this._realTimeActivityPK;
    } //-- long getRealTimeActivityPK() 

    /**
     * Method getSegmentFKReturns the value of field 'segmentFK'.
     * 
     * @return the value of field 'segmentFK'.
     */
    public long getSegmentFK()
    {
        return this._segmentFK;
    } //-- long getSegmentFK() 

    /**
     * Method getStatusIndReturns the value of field 'statusInd'.
     * 
     * @return the value of field 'statusInd'.
     */
    public java.lang.String getStatusInd()
    {
        return this._statusInd;
    } //-- java.lang.String getStatusInd() 

    /**
     * Method getTransactionTypeCTReturns the value of field
     * 'transactionTypeCT'.
     * 
     * @return the value of field 'transactionTypeCT'.
     */
    public java.lang.String getTransactionTypeCT()
    {
        return this._transactionTypeCT;
    } //-- java.lang.String getTransactionTypeCT() 

    /**
     * Method hasRealTimeActivityPK
     */
    public boolean hasRealTimeActivityPK()
    {
        return this._has_realTimeActivityPK;
    } //-- boolean hasRealTimeActivityPK() 

    /**
     * Method hasSegmentFK
     */
    public boolean hasSegmentFK()
    {
        return this._has_segmentFK;
    } //-- boolean hasSegmentFK() 

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
     * Method setAmountSets the value of field 'amount'.
     * 
     * @param amount the value of field 'amount'.
     */
    public void setAmount(java.math.BigDecimal amount)
    {
        this._amount = amount;
        
        super.setVoChanged(true);
    } //-- void setAmount(java.math.BigDecimal) 

    /**
     * Method setEffectiveDateSets the value of field
     * 'effectiveDate'.
     * 
     * @param effectiveDate the value of field 'effectiveDate'.
     */
    public void setEffectiveDate(java.lang.String effectiveDate)
    {
        this._effectiveDate = effectiveDate;
        
        super.setVoChanged(true);
    } //-- void setEffectiveDate(java.lang.String) 

    /**
     * Method setMessageSets the value of field 'message'.
     * 
     * @param message the value of field 'message'.
     */
    public void setMessage(java.lang.String message)
    {
        this._message = message;
        
        super.setVoChanged(true);
    } //-- void setMessage(java.lang.String) 

    /**
     * Method setRealTimeActivityPKSets the value of field
     * 'realTimeActivityPK'.
     * 
     * @param realTimeActivityPK the value of field
     * 'realTimeActivityPK'.
     */
    public void setRealTimeActivityPK(long realTimeActivityPK)
    {
        this._realTimeActivityPK = realTimeActivityPK;
        
        super.setVoChanged(true);
        this._has_realTimeActivityPK = true;
    } //-- void setRealTimeActivityPK(long) 

    /**
     * Method setSegmentFKSets the value of field 'segmentFK'.
     * 
     * @param segmentFK the value of field 'segmentFK'.
     */
    public void setSegmentFK(long segmentFK)
    {
        this._segmentFK = segmentFK;
        
        super.setVoChanged(true);
        this._has_segmentFK = true;
    } //-- void setSegmentFK(long) 

    /**
     * Method setStatusIndSets the value of field 'statusInd'.
     * 
     * @param statusInd the value of field 'statusInd'.
     */
    public void setStatusInd(java.lang.String statusInd)
    {
        this._statusInd = statusInd;
        
        super.setVoChanged(true);
    } //-- void setStatusInd(java.lang.String) 

    /**
     * Method setTransactionTypeCTSets the value of field
     * 'transactionTypeCT'.
     * 
     * @param transactionTypeCT the value of field
     * 'transactionTypeCT'.
     */
    public void setTransactionTypeCT(java.lang.String transactionTypeCT)
    {
        this._transactionTypeCT = transactionTypeCT;
        
        super.setVoChanged(true);
    } //-- void setTransactionTypeCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.RealTimeActivityVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.RealTimeActivityVO) Unmarshaller.unmarshal(edit.common.vo.RealTimeActivityVO.class, reader);
    } //-- edit.common.vo.RealTimeActivityVO unmarshal(java.io.Reader) 

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
