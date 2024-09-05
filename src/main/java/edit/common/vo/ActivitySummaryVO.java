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
 * Used for Statement Correspondence
 * 
 * @version $Revision$ $Date$
 */
public class ActivitySummaryVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _activitySummaryPK
     */
    private long _activitySummaryPK;

    /**
     * keeps track of state for field: _activitySummaryPK
     */
    private boolean _has_activitySummaryPK;

    /**
     * Field _totalPayments
     */
    private java.math.BigDecimal _totalPayments;

    /**
     * Field _interestEarned
     */
    private java.math.BigDecimal _interestEarned;

    /**
     * Field _totalWithdrawals
     */
    private java.math.BigDecimal _totalWithdrawals;

    /**
     * Field _totalCharges
     */
    private java.math.BigDecimal _totalCharges;


      //----------------/
     //- Constructors -/
    //----------------/

    public ActivitySummaryVO() {
        super();
    } //-- edit.common.vo.ActivitySummaryVO()


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
        
        if (obj instanceof ActivitySummaryVO) {
        
            ActivitySummaryVO temp = (ActivitySummaryVO)obj;
            if (this._activitySummaryPK != temp._activitySummaryPK)
                return false;
            if (this._has_activitySummaryPK != temp._has_activitySummaryPK)
                return false;
            if (this._totalPayments != null) {
                if (temp._totalPayments == null) return false;
                else if (!(this._totalPayments.equals(temp._totalPayments))) 
                    return false;
            }
            else if (temp._totalPayments != null)
                return false;
            if (this._interestEarned != null) {
                if (temp._interestEarned == null) return false;
                else if (!(this._interestEarned.equals(temp._interestEarned))) 
                    return false;
            }
            else if (temp._interestEarned != null)
                return false;
            if (this._totalWithdrawals != null) {
                if (temp._totalWithdrawals == null) return false;
                else if (!(this._totalWithdrawals.equals(temp._totalWithdrawals))) 
                    return false;
            }
            else if (temp._totalWithdrawals != null)
                return false;
            if (this._totalCharges != null) {
                if (temp._totalCharges == null) return false;
                else if (!(this._totalCharges.equals(temp._totalCharges))) 
                    return false;
            }
            else if (temp._totalCharges != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getActivitySummaryPKReturns the value of field
     * 'activitySummaryPK'.
     * 
     * @return the value of field 'activitySummaryPK'.
     */
    public long getActivitySummaryPK()
    {
        return this._activitySummaryPK;
    } //-- long getActivitySummaryPK() 

    /**
     * Method getInterestEarnedReturns the value of field
     * 'interestEarned'.
     * 
     * @return the value of field 'interestEarned'.
     */
    public java.math.BigDecimal getInterestEarned()
    {
        return this._interestEarned;
    } //-- java.math.BigDecimal getInterestEarned() 

    /**
     * Method getTotalChargesReturns the value of field
     * 'totalCharges'.
     * 
     * @return the value of field 'totalCharges'.
     */
    public java.math.BigDecimal getTotalCharges()
    {
        return this._totalCharges;
    } //-- java.math.BigDecimal getTotalCharges() 

    /**
     * Method getTotalPaymentsReturns the value of field
     * 'totalPayments'.
     * 
     * @return the value of field 'totalPayments'.
     */
    public java.math.BigDecimal getTotalPayments()
    {
        return this._totalPayments;
    } //-- java.math.BigDecimal getTotalPayments() 

    /**
     * Method getTotalWithdrawalsReturns the value of field
     * 'totalWithdrawals'.
     * 
     * @return the value of field 'totalWithdrawals'.
     */
    public java.math.BigDecimal getTotalWithdrawals()
    {
        return this._totalWithdrawals;
    } //-- java.math.BigDecimal getTotalWithdrawals() 

    /**
     * Method hasActivitySummaryPK
     */
    public boolean hasActivitySummaryPK()
    {
        return this._has_activitySummaryPK;
    } //-- boolean hasActivitySummaryPK() 

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
     * Method setActivitySummaryPKSets the value of field
     * 'activitySummaryPK'.
     * 
     * @param activitySummaryPK the value of field
     * 'activitySummaryPK'.
     */
    public void setActivitySummaryPK(long activitySummaryPK)
    {
        this._activitySummaryPK = activitySummaryPK;
        
        super.setVoChanged(true);
        this._has_activitySummaryPK = true;
    } //-- void setActivitySummaryPK(long) 

    /**
     * Method setInterestEarnedSets the value of field
     * 'interestEarned'.
     * 
     * @param interestEarned the value of field 'interestEarned'.
     */
    public void setInterestEarned(java.math.BigDecimal interestEarned)
    {
        this._interestEarned = interestEarned;
        
        super.setVoChanged(true);
    } //-- void setInterestEarned(java.math.BigDecimal) 

    /**
     * Method setTotalChargesSets the value of field
     * 'totalCharges'.
     * 
     * @param totalCharges the value of field 'totalCharges'.
     */
    public void setTotalCharges(java.math.BigDecimal totalCharges)
    {
        this._totalCharges = totalCharges;
        
        super.setVoChanged(true);
    } //-- void setTotalCharges(java.math.BigDecimal) 

    /**
     * Method setTotalPaymentsSets the value of field
     * 'totalPayments'.
     * 
     * @param totalPayments the value of field 'totalPayments'.
     */
    public void setTotalPayments(java.math.BigDecimal totalPayments)
    {
        this._totalPayments = totalPayments;
        
        super.setVoChanged(true);
    } //-- void setTotalPayments(java.math.BigDecimal) 

    /**
     * Method setTotalWithdrawalsSets the value of field
     * 'totalWithdrawals'.
     * 
     * @param totalWithdrawals the value of field 'totalWithdrawals'
     */
    public void setTotalWithdrawals(java.math.BigDecimal totalWithdrawals)
    {
        this._totalWithdrawals = totalWithdrawals;
        
        super.setVoChanged(true);
    } //-- void setTotalWithdrawals(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ActivitySummaryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ActivitySummaryVO) Unmarshaller.unmarshal(edit.common.vo.ActivitySummaryVO.class, reader);
    } //-- edit.common.vo.ActivitySummaryVO unmarshal(java.io.Reader) 

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
