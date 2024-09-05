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
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Contains the information required for generating the hedge fund
 * notification report
 * 
 * @version $Revision$ $Date$
 */
public class HedgeFundNotificationDetailVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _hedgeFundNotificationDetailPK
     */
    private long _hedgeFundNotificationDetailPK;

    /**
     * keeps track of state for field: _hedgeFundNotificationDetailP
     */
    private boolean _has_hedgeFundNotificationDetailPK;

    /**
     * Field _reportingFundName
     */
    private java.lang.String _reportingFundName;

    /**
     * Field _fundNumber
     */
    private java.lang.String _fundNumber;

    /**
     * Field _notificationAmount
     */
    private java.math.BigDecimal _notificationAmount;

    /**
     * Field _contractNumber
     */
    private java.lang.String _contractNumber;

    /**
     * Field _correspondenceEffectiveDate
     */
    private java.lang.String _correspondenceEffectiveDate;

    /**
     * Field _transferType
     */
    private java.lang.String _transferType;

    /**
     * Field _transactionEffectiveDate
     */
    private java.lang.String _transactionEffectiveDate;

    /**
     * Field _externalProductName
     */
    private java.lang.String _externalProductName;

    /**
     * Field _originatingTransactionType
     */
    private java.lang.String _originatingTransactionType;

    /**
     * Field _notificationType
     */
    private java.lang.String _notificationType;

    /**
     * Field _transactionGroup
     */
    private java.lang.String _transactionGroup;

    /**
     * Field _toFromStatus
     */
    private java.lang.String _toFromStatus;

    /**
     * Field _naturalDocVOList
     */
    private java.util.Vector _naturalDocVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public HedgeFundNotificationDetailVO() {
        super();
        _naturalDocVOList = new Vector();
    } //-- edit.common.vo.HedgeFundNotificationDetailVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addNaturalDocVO
     * 
     * @param vNaturalDocVO
     */
    public void addNaturalDocVO(edit.common.vo.NaturalDocVO vNaturalDocVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vNaturalDocVO.setParentVO(this.getClass(), this);
        _naturalDocVOList.addElement(vNaturalDocVO);
    } //-- void addNaturalDocVO(edit.common.vo.NaturalDocVO) 

    /**
     * Method addNaturalDocVO
     * 
     * @param index
     * @param vNaturalDocVO
     */
    public void addNaturalDocVO(int index, edit.common.vo.NaturalDocVO vNaturalDocVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vNaturalDocVO.setParentVO(this.getClass(), this);
        _naturalDocVOList.insertElementAt(vNaturalDocVO, index);
    } //-- void addNaturalDocVO(int, edit.common.vo.NaturalDocVO) 

    /**
     * Method enumerateNaturalDocVO
     */
    public java.util.Enumeration enumerateNaturalDocVO()
    {
        return _naturalDocVOList.elements();
    } //-- java.util.Enumeration enumerateNaturalDocVO() 

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
        
        if (obj instanceof HedgeFundNotificationDetailVO) {
        
            HedgeFundNotificationDetailVO temp = (HedgeFundNotificationDetailVO)obj;
            if (this._hedgeFundNotificationDetailPK != temp._hedgeFundNotificationDetailPK)
                return false;
            if (this._has_hedgeFundNotificationDetailPK != temp._has_hedgeFundNotificationDetailPK)
                return false;
            if (this._reportingFundName != null) {
                if (temp._reportingFundName == null) return false;
                else if (!(this._reportingFundName.equals(temp._reportingFundName))) 
                    return false;
            }
            else if (temp._reportingFundName != null)
                return false;
            if (this._fundNumber != null) {
                if (temp._fundNumber == null) return false;
                else if (!(this._fundNumber.equals(temp._fundNumber))) 
                    return false;
            }
            else if (temp._fundNumber != null)
                return false;
            if (this._notificationAmount != null) {
                if (temp._notificationAmount == null) return false;
                else if (!(this._notificationAmount.equals(temp._notificationAmount))) 
                    return false;
            }
            else if (temp._notificationAmount != null)
                return false;
            if (this._contractNumber != null) {
                if (temp._contractNumber == null) return false;
                else if (!(this._contractNumber.equals(temp._contractNumber))) 
                    return false;
            }
            else if (temp._contractNumber != null)
                return false;
            if (this._correspondenceEffectiveDate != null) {
                if (temp._correspondenceEffectiveDate == null) return false;
                else if (!(this._correspondenceEffectiveDate.equals(temp._correspondenceEffectiveDate))) 
                    return false;
            }
            else if (temp._correspondenceEffectiveDate != null)
                return false;
            if (this._transferType != null) {
                if (temp._transferType == null) return false;
                else if (!(this._transferType.equals(temp._transferType))) 
                    return false;
            }
            else if (temp._transferType != null)
                return false;
            if (this._transactionEffectiveDate != null) {
                if (temp._transactionEffectiveDate == null) return false;
                else if (!(this._transactionEffectiveDate.equals(temp._transactionEffectiveDate))) 
                    return false;
            }
            else if (temp._transactionEffectiveDate != null)
                return false;
            if (this._externalProductName != null) {
                if (temp._externalProductName == null) return false;
                else if (!(this._externalProductName.equals(temp._externalProductName))) 
                    return false;
            }
            else if (temp._externalProductName != null)
                return false;
            if (this._originatingTransactionType != null) {
                if (temp._originatingTransactionType == null) return false;
                else if (!(this._originatingTransactionType.equals(temp._originatingTransactionType))) 
                    return false;
            }
            else if (temp._originatingTransactionType != null)
                return false;
            if (this._notificationType != null) {
                if (temp._notificationType == null) return false;
                else if (!(this._notificationType.equals(temp._notificationType))) 
                    return false;
            }
            else if (temp._notificationType != null)
                return false;
            if (this._transactionGroup != null) {
                if (temp._transactionGroup == null) return false;
                else if (!(this._transactionGroup.equals(temp._transactionGroup))) 
                    return false;
            }
            else if (temp._transactionGroup != null)
                return false;
            if (this._toFromStatus != null) {
                if (temp._toFromStatus == null) return false;
                else if (!(this._toFromStatus.equals(temp._toFromStatus))) 
                    return false;
            }
            else if (temp._toFromStatus != null)
                return false;
            if (this._naturalDocVOList != null) {
                if (temp._naturalDocVOList == null) return false;
                else if (!(this._naturalDocVOList.equals(temp._naturalDocVOList))) 
                    return false;
            }
            else if (temp._naturalDocVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getContractNumberReturns the value of field
     * 'contractNumber'.
     * 
     * @return the value of field 'contractNumber'.
     */
    public java.lang.String getContractNumber()
    {
        return this._contractNumber;
    } //-- java.lang.String getContractNumber() 

    /**
     * Method getCorrespondenceEffectiveDateReturns the value of
     * field 'correspondenceEffectiveDate'.
     * 
     * @return the value of field 'correspondenceEffectiveDate'.
     */
    public java.lang.String getCorrespondenceEffectiveDate()
    {
        return this._correspondenceEffectiveDate;
    } //-- java.lang.String getCorrespondenceEffectiveDate() 

    /**
     * Method getExternalProductNameReturns the value of field
     * 'externalProductName'.
     * 
     * @return the value of field 'externalProductName'.
     */
    public java.lang.String getExternalProductName()
    {
        return this._externalProductName;
    } //-- java.lang.String getExternalProductName() 

    /**
     * Method getFundNumberReturns the value of field 'fundNumber'.
     * 
     * @return the value of field 'fundNumber'.
     */
    public java.lang.String getFundNumber()
    {
        return this._fundNumber;
    } //-- java.lang.String getFundNumber() 

    /**
     * Method getHedgeFundNotificationDetailPKReturns the value of
     * field 'hedgeFundNotificationDetailPK'.
     * 
     * @return the value of field 'hedgeFundNotificationDetailPK'.
     */
    public long getHedgeFundNotificationDetailPK()
    {
        return this._hedgeFundNotificationDetailPK;
    } //-- long getHedgeFundNotificationDetailPK() 

    /**
     * Method getNaturalDocVO
     * 
     * @param index
     */
    public edit.common.vo.NaturalDocVO getNaturalDocVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _naturalDocVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.NaturalDocVO) _naturalDocVOList.elementAt(index);
    } //-- edit.common.vo.NaturalDocVO getNaturalDocVO(int) 

    /**
     * Method getNaturalDocVO
     */
    public edit.common.vo.NaturalDocVO[] getNaturalDocVO()
    {
        int size = _naturalDocVOList.size();
        edit.common.vo.NaturalDocVO[] mArray = new edit.common.vo.NaturalDocVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.NaturalDocVO) _naturalDocVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.NaturalDocVO[] getNaturalDocVO() 

    /**
     * Method getNaturalDocVOCount
     */
    public int getNaturalDocVOCount()
    {
        return _naturalDocVOList.size();
    } //-- int getNaturalDocVOCount() 

    /**
     * Method getNotificationAmountReturns the value of field
     * 'notificationAmount'.
     * 
     * @return the value of field 'notificationAmount'.
     */
    public java.math.BigDecimal getNotificationAmount()
    {
        return this._notificationAmount;
    } //-- java.math.BigDecimal getNotificationAmount() 

    /**
     * Method getNotificationTypeReturns the value of field
     * 'notificationType'.
     * 
     * @return the value of field 'notificationType'.
     */
    public java.lang.String getNotificationType()
    {
        return this._notificationType;
    } //-- java.lang.String getNotificationType() 

    /**
     * Method getOriginatingTransactionTypeReturns the value of
     * field 'originatingTransactionType'.
     * 
     * @return the value of field 'originatingTransactionType'.
     */
    public java.lang.String getOriginatingTransactionType()
    {
        return this._originatingTransactionType;
    } //-- java.lang.String getOriginatingTransactionType() 

    /**
     * Method getReportingFundNameReturns the value of field
     * 'reportingFundName'.
     * 
     * @return the value of field 'reportingFundName'.
     */
    public java.lang.String getReportingFundName()
    {
        return this._reportingFundName;
    } //-- java.lang.String getReportingFundName() 

    /**
     * Method getToFromStatusReturns the value of field
     * 'toFromStatus'.
     * 
     * @return the value of field 'toFromStatus'.
     */
    public java.lang.String getToFromStatus()
    {
        return this._toFromStatus;
    } //-- java.lang.String getToFromStatus() 

    /**
     * Method getTransactionEffectiveDateReturns the value of field
     * 'transactionEffectiveDate'.
     * 
     * @return the value of field 'transactionEffectiveDate'.
     */
    public java.lang.String getTransactionEffectiveDate()
    {
        return this._transactionEffectiveDate;
    } //-- java.lang.String getTransactionEffectiveDate() 

    /**
     * Method getTransactionGroupReturns the value of field
     * 'transactionGroup'.
     * 
     * @return the value of field 'transactionGroup'.
     */
    public java.lang.String getTransactionGroup()
    {
        return this._transactionGroup;
    } //-- java.lang.String getTransactionGroup() 

    /**
     * Method getTransferTypeReturns the value of field
     * 'transferType'.
     * 
     * @return the value of field 'transferType'.
     */
    public java.lang.String getTransferType()
    {
        return this._transferType;
    } //-- java.lang.String getTransferType() 

    /**
     * Method hasHedgeFundNotificationDetailPK
     */
    public boolean hasHedgeFundNotificationDetailPK()
    {
        return this._has_hedgeFundNotificationDetailPK;
    } //-- boolean hasHedgeFundNotificationDetailPK() 

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
     * Method removeAllNaturalDocVO
     */
    public void removeAllNaturalDocVO()
    {
        _naturalDocVOList.removeAllElements();
    } //-- void removeAllNaturalDocVO() 

    /**
     * Method removeNaturalDocVO
     * 
     * @param index
     */
    public edit.common.vo.NaturalDocVO removeNaturalDocVO(int index)
    {
        java.lang.Object obj = _naturalDocVOList.elementAt(index);
        _naturalDocVOList.removeElementAt(index);
        return (edit.common.vo.NaturalDocVO) obj;
    } //-- edit.common.vo.NaturalDocVO removeNaturalDocVO(int) 

    /**
     * Method setContractNumberSets the value of field
     * 'contractNumber'.
     * 
     * @param contractNumber the value of field 'contractNumber'.
     */
    public void setContractNumber(java.lang.String contractNumber)
    {
        this._contractNumber = contractNumber;
        
        super.setVoChanged(true);
    } //-- void setContractNumber(java.lang.String) 

    /**
     * Method setCorrespondenceEffectiveDateSets the value of field
     * 'correspondenceEffectiveDate'.
     * 
     * @param correspondenceEffectiveDate the value of field
     * 'correspondenceEffectiveDate'.
     */
    public void setCorrespondenceEffectiveDate(java.lang.String correspondenceEffectiveDate)
    {
        this._correspondenceEffectiveDate = correspondenceEffectiveDate;
        
        super.setVoChanged(true);
    } //-- void setCorrespondenceEffectiveDate(java.lang.String) 

    /**
     * Method setExternalProductNameSets the value of field
     * 'externalProductName'.
     * 
     * @param externalProductName the value of field
     * 'externalProductName'.
     */
    public void setExternalProductName(java.lang.String externalProductName)
    {
        this._externalProductName = externalProductName;
        
        super.setVoChanged(true);
    } //-- void setExternalProductName(java.lang.String) 

    /**
     * Method setFundNumberSets the value of field 'fundNumber'.
     * 
     * @param fundNumber the value of field 'fundNumber'.
     */
    public void setFundNumber(java.lang.String fundNumber)
    {
        this._fundNumber = fundNumber;
        
        super.setVoChanged(true);
    } //-- void setFundNumber(java.lang.String) 

    /**
     * Method setHedgeFundNotificationDetailPKSets the value of
     * field 'hedgeFundNotificationDetailPK'.
     * 
     * @param hedgeFundNotificationDetailPK the value of field
     * 'hedgeFundNotificationDetailPK'.
     */
    public void setHedgeFundNotificationDetailPK(long hedgeFundNotificationDetailPK)
    {
        this._hedgeFundNotificationDetailPK = hedgeFundNotificationDetailPK;
        
        super.setVoChanged(true);
        this._has_hedgeFundNotificationDetailPK = true;
    } //-- void setHedgeFundNotificationDetailPK(long) 

    /**
     * Method setNaturalDocVO
     * 
     * @param index
     * @param vNaturalDocVO
     */
    public void setNaturalDocVO(int index, edit.common.vo.NaturalDocVO vNaturalDocVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _naturalDocVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vNaturalDocVO.setParentVO(this.getClass(), this);
        _naturalDocVOList.setElementAt(vNaturalDocVO, index);
    } //-- void setNaturalDocVO(int, edit.common.vo.NaturalDocVO) 

    /**
     * Method setNaturalDocVO
     * 
     * @param naturalDocVOArray
     */
    public void setNaturalDocVO(edit.common.vo.NaturalDocVO[] naturalDocVOArray)
    {
        //-- copy array
        _naturalDocVOList.removeAllElements();
        for (int i = 0; i < naturalDocVOArray.length; i++) {
            naturalDocVOArray[i].setParentVO(this.getClass(), this);
            _naturalDocVOList.addElement(naturalDocVOArray[i]);
        }
    } //-- void setNaturalDocVO(edit.common.vo.NaturalDocVO) 

    /**
     * Method setNotificationAmountSets the value of field
     * 'notificationAmount'.
     * 
     * @param notificationAmount the value of field
     * 'notificationAmount'.
     */
    public void setNotificationAmount(java.math.BigDecimal notificationAmount)
    {
        this._notificationAmount = notificationAmount;
        
        super.setVoChanged(true);
    } //-- void setNotificationAmount(java.math.BigDecimal) 

    /**
     * Method setNotificationTypeSets the value of field
     * 'notificationType'.
     * 
     * @param notificationType the value of field 'notificationType'
     */
    public void setNotificationType(java.lang.String notificationType)
    {
        this._notificationType = notificationType;
        
        super.setVoChanged(true);
    } //-- void setNotificationType(java.lang.String) 

    /**
     * Method setOriginatingTransactionTypeSets the value of field
     * 'originatingTransactionType'.
     * 
     * @param originatingTransactionType the value of field
     * 'originatingTransactionType'.
     */
    public void setOriginatingTransactionType(java.lang.String originatingTransactionType)
    {
        this._originatingTransactionType = originatingTransactionType;
        
        super.setVoChanged(true);
    } //-- void setOriginatingTransactionType(java.lang.String) 

    /**
     * Method setReportingFundNameSets the value of field
     * 'reportingFundName'.
     * 
     * @param reportingFundName the value of field
     * 'reportingFundName'.
     */
    public void setReportingFundName(java.lang.String reportingFundName)
    {
        this._reportingFundName = reportingFundName;
        
        super.setVoChanged(true);
    } //-- void setReportingFundName(java.lang.String) 

    /**
     * Method setToFromStatusSets the value of field
     * 'toFromStatus'.
     * 
     * @param toFromStatus the value of field 'toFromStatus'.
     */
    public void setToFromStatus(java.lang.String toFromStatus)
    {
        this._toFromStatus = toFromStatus;
        
        super.setVoChanged(true);
    } //-- void setToFromStatus(java.lang.String) 

    /**
     * Method setTransactionEffectiveDateSets the value of field
     * 'transactionEffectiveDate'.
     * 
     * @param transactionEffectiveDate the value of field
     * 'transactionEffectiveDate'.
     */
    public void setTransactionEffectiveDate(java.lang.String transactionEffectiveDate)
    {
        this._transactionEffectiveDate = transactionEffectiveDate;
        
        super.setVoChanged(true);
    } //-- void setTransactionEffectiveDate(java.lang.String) 

    /**
     * Method setTransactionGroupSets the value of field
     * 'transactionGroup'.
     * 
     * @param transactionGroup the value of field 'transactionGroup'
     */
    public void setTransactionGroup(java.lang.String transactionGroup)
    {
        this._transactionGroup = transactionGroup;
        
        super.setVoChanged(true);
    } //-- void setTransactionGroup(java.lang.String) 

    /**
     * Method setTransferTypeSets the value of field
     * 'transferType'.
     * 
     * @param transferType the value of field 'transferType'.
     */
    public void setTransferType(java.lang.String transferType)
    {
        this._transferType = transferType;
        
        super.setVoChanged(true);
    } //-- void setTransferType(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.HedgeFundNotificationDetailVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.HedgeFundNotificationDetailVO) Unmarshaller.unmarshal(edit.common.vo.HedgeFundNotificationDetailVO.class, reader);
    } //-- edit.common.vo.HedgeFundNotificationDetailVO unmarshal(java.io.Reader) 

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
