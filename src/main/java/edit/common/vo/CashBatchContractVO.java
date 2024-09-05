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
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class CashBatchContractVO.
 * 
 * @version $Revision$ $Date$
 */
public class CashBatchContractVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _cashBatchContractPK
     */
    private long _cashBatchContractPK;

    /**
     * keeps track of state for field: _cashBatchContractPK
     */
    private boolean _has_cashBatchContractPK;

    /**
     * Field _amount
     */
    private java.math.BigDecimal _amount;

    /**
     * Field _batchID
     */
    private java.lang.String _batchID;

    /**
     * Field _creationDate
     */
    private java.util.Date _creationDate;

    /**
     * Field _creationOperator
     */
    private java.lang.String _creationOperator;

    /**
     * Field _totalBatchItems
     */
    private int _totalBatchItems;

    /**
     * keeps track of state for field: _totalBatchItems
     */
    private boolean _has_totalBatchItems;

    /**
     * Field _accountingPendingIndicator
     */
    private java.lang.String _accountingPendingIndicator;

    /**
     * Field _releaseIndicator
     */
    private java.lang.String _releaseIndicator;

    /**
     * Field _companyFK
     */
    private long _companyFK;

    /**
     * keeps track of state for field: _companyFK
     */
    private boolean _has_companyFK;

    /**
     * Field _suspenseVOList
     */
    private java.util.Vector _suspenseVOList;
    
    private java.lang.String _batchRecordType;


      //----------------/
     //- Constructors -/
    //----------------/

    public CashBatchContractVO() {
        super();
        _suspenseVOList = new Vector();
    } //-- edit.common.vo.CashBatchContractVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addSuspenseVO
     * 
     * @param vSuspenseVO
     */
    public void addSuspenseVO(edit.common.vo.SuspenseVO vSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSuspenseVO.setParentVO(this.getClass(), this);
        _suspenseVOList.addElement(vSuspenseVO);
    } //-- void addSuspenseVO(edit.common.vo.SuspenseVO) 

    /**
     * Method addSuspenseVO
     * 
     * @param index
     * @param vSuspenseVO
     */
    public void addSuspenseVO(int index, edit.common.vo.SuspenseVO vSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSuspenseVO.setParentVO(this.getClass(), this);
        _suspenseVOList.insertElementAt(vSuspenseVO, index);
    } //-- void addSuspenseVO(int, edit.common.vo.SuspenseVO) 

    /**
     * Method enumerateSuspenseVO
     */
    public java.util.Enumeration enumerateSuspenseVO()
    {
        return _suspenseVOList.elements();
    } //-- java.util.Enumeration enumerateSuspenseVO() 

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
        
        if (obj instanceof CashBatchContractVO) {
        
            CashBatchContractVO temp = (CashBatchContractVO)obj;
            if (this._cashBatchContractPK != temp._cashBatchContractPK)
                return false;
            if (this._has_cashBatchContractPK != temp._has_cashBatchContractPK)
                return false;
            if (this._amount != null) {
                if (temp._amount == null) return false;
                else if (!(this._amount.equals(temp._amount))) 
                    return false;
            }
            else if (temp._amount != null)
                return false;
            if (this._batchID != null) {
                if (temp._batchID == null) return false;
                else if (!(this._batchID.equals(temp._batchID))) 
                    return false;
            }
            else if (temp._batchID != null)
                return false;
            if (this._creationDate != null) {
                if (temp._creationDate == null) return false;
                else if (!(this._creationDate.equals(temp._creationDate))) 
                    return false;
            }
            else if (temp._creationDate != null)
                return false;
            if (this._creationOperator != null) {
                if (temp._creationOperator == null) return false;
                else if (!(this._creationOperator.equals(temp._creationOperator))) 
                    return false;
            }
            else if (temp._creationOperator != null)
                return false;
            if (this._totalBatchItems != temp._totalBatchItems)
                return false;
            if (this._has_totalBatchItems != temp._has_totalBatchItems)
                return false;
            if (this._accountingPendingIndicator != null) {
                if (temp._accountingPendingIndicator == null) return false;
                else if (!(this._accountingPendingIndicator.equals(temp._accountingPendingIndicator))) 
                    return false;
            }
            else if (temp._accountingPendingIndicator != null)
                return false;
            if (this._releaseIndicator != null) {
                if (temp._releaseIndicator == null) return false;
                else if (!(this._releaseIndicator.equals(temp._releaseIndicator))) 
                    return false;
            }
            else if (temp._releaseIndicator != null)
                return false;
            if (this._batchRecordType != null) {
                if (temp._batchRecordType == null) return false;
                else if (!(this._batchRecordType.equals(temp._batchRecordType))) 
                    return false;
            }
            else if (temp._batchRecordType != null)
                return false;
            if (this._companyFK != temp._companyFK)
                return false;
            if (this._has_companyFK != temp._has_companyFK)
                return false;
            if (this._suspenseVOList != null) {
                if (temp._suspenseVOList == null) return false;
                else if (!(this._suspenseVOList.equals(temp._suspenseVOList))) 
                    return false;
            }
            else if (temp._suspenseVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccountingPendingIndicatorReturns the value of
     * field 'accountingPendingIndicator'.
     * 
     * @return the value of field 'accountingPendingIndicator'.
     */
    public java.lang.String getAccountingPendingIndicator()
    {
        return this._accountingPendingIndicator;
    } //-- java.lang.String getAccountingPendingIndicator() 

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
     * Method getBatchIDReturns the value of field 'batchID'.
     * 
     * @return the value of field 'batchID'.
     */
    public java.lang.String getBatchID()
    {
        return this._batchID;
    } //-- java.lang.String getBatchID() 

    /**
     * Method getCashBatchContractPKReturns the value of field
     * 'cashBatchContractPK'.
     * 
     * @return the value of field 'cashBatchContractPK'.
     */
    public long getCashBatchContractPK()
    {
        return this._cashBatchContractPK;
    } //-- long getCashBatchContractPK() 

    /**
     * Method getCompanyFKReturns the value of field 'companyFK'.
     * 
     * @return the value of field 'companyFK'.
     */
    public long getCompanyFK()
    {
        return this._companyFK;
    } //-- long getCompanyFK() 

    /**
     * Method getCreationDateReturns the value of field
     * 'creationDate'.
     * 
     * @return the value of field 'creationDate'.
     */
    public java.util.Date getCreationDate()
    {
        return this._creationDate;
    } //-- java.util.Date getCreationDate() 

    /**
     * Method getCreationOperatorReturns the value of field
     * 'creationOperator'.
     * 
     * @return the value of field 'creationOperator'.
     */
    public java.lang.String getCreationOperator()
    {
        return this._creationOperator;
    } //-- java.lang.String getCreationOperator() 

    /**
     * Method getReleaseIndicatorReturns the value of field
     * 'releaseIndicator'.
     * 
     * @return the value of field 'releaseIndicator'.
     */
    public java.lang.String getReleaseIndicator()
    {
        return this._releaseIndicator;
    } //-- java.lang.String getReleaseIndicator() 

    public java.lang.String getBatchRecordType()
    {
        return this._batchRecordType;
    } //-- java.lang.String geBatchRecordType() 


    /**
     * Method getSuspenseVO
     * 
     * @param index
     */
    public edit.common.vo.SuspenseVO getSuspenseVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _suspenseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.SuspenseVO) _suspenseVOList.elementAt(index);
    } //-- edit.common.vo.SuspenseVO getSuspenseVO(int) 

    /**
     * Method getSuspenseVO
     */
    public edit.common.vo.SuspenseVO[] getSuspenseVO()
    {
        int size = _suspenseVOList.size();
        edit.common.vo.SuspenseVO[] mArray = new edit.common.vo.SuspenseVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.SuspenseVO) _suspenseVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.SuspenseVO[] getSuspenseVO() 

    /**
     * Method getSuspenseVOCount
     */
    public int getSuspenseVOCount()
    {
        return _suspenseVOList.size();
    } //-- int getSuspenseVOCount() 

    /**
     * Method getTotalBatchItemsReturns the value of field
     * 'totalBatchItems'.
     * 
     * @return the value of field 'totalBatchItems'.
     */
    public int getTotalBatchItems()
    {
        return this._totalBatchItems;
    } //-- int getTotalBatchItems() 

    /**
     * Method hasCashBatchContractPK
     */
    public boolean hasCashBatchContractPK()
    {
        return this._has_cashBatchContractPK;
    } //-- boolean hasCashBatchContractPK() 

    /**
     * Method hasCompanyFK
     */
    public boolean hasCompanyFK()
    {
        return this._has_companyFK;
    } //-- boolean hasCompanyFK() 

    /**
     * Method hasTotalBatchItems
     */
    public boolean hasTotalBatchItems()
    {
        return this._has_totalBatchItems;
    } //-- boolean hasTotalBatchItems() 

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
     * Method removeAllSuspenseVO
     */
    public void removeAllSuspenseVO()
    {
        _suspenseVOList.removeAllElements();
    } //-- void removeAllSuspenseVO() 

    /**
     * Method removeSuspenseVO
     * 
     * @param index
     */
    public edit.common.vo.SuspenseVO removeSuspenseVO(int index)
    {
        java.lang.Object obj = _suspenseVOList.elementAt(index);
        _suspenseVOList.removeElementAt(index);
        return (edit.common.vo.SuspenseVO) obj;
    } //-- edit.common.vo.SuspenseVO removeSuspenseVO(int) 

    /**
     * Method setAccountingPendingIndicatorSets the value of field
     * 'accountingPendingIndicator'.
     * 
     * @param accountingPendingIndicator the value of field
     * 'accountingPendingIndicator'.
     */
    public void setAccountingPendingIndicator(java.lang.String accountingPendingIndicator)
    {
        this._accountingPendingIndicator = accountingPendingIndicator;
        
        super.setVoChanged(true);
    } //-- void setAccountingPendingIndicator(java.lang.String) 

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
     * Method setBatchIDSets the value of field 'batchID'.
     * 
     * @param batchID the value of field 'batchID'.
     */
    public void setBatchID(java.lang.String batchID)
    {
        this._batchID = batchID;
        
        super.setVoChanged(true);
    } //-- void setBatchID(java.lang.String) 

    /**
     * Method setCashBatchContractPKSets the value of field
     * 'cashBatchContractPK'.
     * 
     * @param cashBatchContractPK the value of field
     * 'cashBatchContractPK'.
     */
    public void setCashBatchContractPK(long cashBatchContractPK)
    {
        this._cashBatchContractPK = cashBatchContractPK;
        
        super.setVoChanged(true);
        this._has_cashBatchContractPK = true;
    } //-- void setCashBatchContractPK(long) 

    /**
     * Method setCompanyFKSets the value of field 'companyFK'.
     * 
     * @param companyFK the value of field 'companyFK'.
     */
    public void setCompanyFK(long companyFK)
    {
        this._companyFK = companyFK;
        
        super.setVoChanged(true);
        this._has_companyFK = true;
    } //-- void setCompanyFK(long) 

    /**
     * Method setCreationDateSets the value of field
     * 'creationDate'.
     * 
     * @param creationDate the value of field 'creationDate'.
     */
    public void setCreationDate(java.util.Date creationDate)
    {
        this._creationDate = creationDate;
        
        super.setVoChanged(true);
    } //-- void setCreationDate(java.util.Date) 

    /**
     * Method setCreationOperatorSets the value of field
     * 'creationOperator'.
     * 
     * @param creationOperator the value of field 'creationOperator'
     */
    public void setCreationOperator(java.lang.String creationOperator)
    {
        this._creationOperator = creationOperator;
        
        super.setVoChanged(true);
    } //-- void setCreationOperator(java.lang.String) 

    /**
     * Method setReleaseIndicatorSets the value of field
     * 'releaseIndicator'.
     * 
     * @param releaseIndicator the value of field 'releaseIndicator'
     */
    public void setReleaseIndicator(java.lang.String releaseIndicator)
    {
        this._releaseIndicator = releaseIndicator;
        
        super.setVoChanged(true);
    } //-- void setReleaseIndicator(java.lang.String) 

    public void setBatchRecordType(java.lang.String batchRecordType)
    {
        this._batchRecordType = batchRecordType;
        
        super.setVoChanged(true);
    } //-- void setReleaseIndicator(java.lang.String) 

    /**
     * Method setSuspenseVO
     * 
     * @param index
     * @param vSuspenseVO
     */
    public void setSuspenseVO(int index, edit.common.vo.SuspenseVO vSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _suspenseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vSuspenseVO.setParentVO(this.getClass(), this);
        _suspenseVOList.setElementAt(vSuspenseVO, index);
    } //-- void setSuspenseVO(int, edit.common.vo.SuspenseVO) 

    /**
     * Method setSuspenseVO
     * 
     * @param suspenseVOArray
     */
    public void setSuspenseVO(edit.common.vo.SuspenseVO[] suspenseVOArray)
    {
        //-- copy array
        _suspenseVOList.removeAllElements();
        for (int i = 0; i < suspenseVOArray.length; i++) {
            suspenseVOArray[i].setParentVO(this.getClass(), this);
            _suspenseVOList.addElement(suspenseVOArray[i]);
        }
    } //-- void setSuspenseVO(edit.common.vo.SuspenseVO) 

    /**
     * Method setTotalBatchItemsSets the value of field
     * 'totalBatchItems'.
     * 
     * @param totalBatchItems the value of field 'totalBatchItems'.
     */
    public void setTotalBatchItems(int totalBatchItems)
    {
        this._totalBatchItems = totalBatchItems;
        
        super.setVoChanged(true);
        this._has_totalBatchItems = true;
    } //-- void setTotalBatchItems(int) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CashBatchContractVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CashBatchContractVO) Unmarshaller.unmarshal(edit.common.vo.CashBatchContractVO.class, reader);
    } //-- edit.common.vo.CashBatchContractVO unmarshal(java.io.Reader) 

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
