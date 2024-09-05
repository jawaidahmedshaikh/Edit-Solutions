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
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class TransactionPriorityVO.
 * 
 * @version $Revision$ $Date$
 */
public class TransactionPriorityVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _transactionPriorityPK
     */
    private long _transactionPriorityPK;

    /**
     * keeps track of state for field: _transactionPriorityPK
     */
    private boolean _has_transactionPriorityPK;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _priority
     */
    private int _priority;

    /**
     * keeps track of state for field: _priority
     */
    private boolean _has_priority;

    /**
     * Field _transactionTypeCT
     */
    private java.lang.String _transactionTypeCT;

    /**
     * Field _commissionableEventInd
     */
    private java.lang.String _commissionableEventInd;

    /**
     * Field _confirmEventInd
     */
    private java.lang.String _confirmEventInd;

    /**
     * Field _reinsuranceInd
     */
    private java.lang.String _reinsuranceInd;

    /**
     * Field _bonusChargebackCT
     */
    private java.lang.String _bonusChargebackCT;

    /**
     * Field _transactionCorrespondenceVOList
     */
    private java.util.Vector _transactionCorrespondenceVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public TransactionPriorityVO() {
        super();
        _transactionCorrespondenceVOList = new Vector();
    } //-- edit.common.vo.TransactionPriorityVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addTransactionCorrespondenceVO
     * 
     * @param vTransactionCorrespondenceVO
     */
    public void addTransactionCorrespondenceVO(edit.common.vo.TransactionCorrespondenceVO vTransactionCorrespondenceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vTransactionCorrespondenceVO.setParentVO(this.getClass(), this);
        _transactionCorrespondenceVOList.addElement(vTransactionCorrespondenceVO);
    } //-- void addTransactionCorrespondenceVO(edit.common.vo.TransactionCorrespondenceVO) 

    /**
     * Method addTransactionCorrespondenceVO
     * 
     * @param index
     * @param vTransactionCorrespondenceVO
     */
    public void addTransactionCorrespondenceVO(int index, edit.common.vo.TransactionCorrespondenceVO vTransactionCorrespondenceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vTransactionCorrespondenceVO.setParentVO(this.getClass(), this);
        _transactionCorrespondenceVOList.insertElementAt(vTransactionCorrespondenceVO, index);
    } //-- void addTransactionCorrespondenceVO(int, edit.common.vo.TransactionCorrespondenceVO) 

    /**
     * Method enumerateTransactionCorrespondenceVO
     */
    public java.util.Enumeration enumerateTransactionCorrespondenceVO()
    {
        return _transactionCorrespondenceVOList.elements();
    } //-- java.util.Enumeration enumerateTransactionCorrespondenceVO() 

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
        
        if (obj instanceof TransactionPriorityVO) {
        
            TransactionPriorityVO temp = (TransactionPriorityVO)obj;
            if (this._transactionPriorityPK != temp._transactionPriorityPK)
                return false;
            if (this._has_transactionPriorityPK != temp._has_transactionPriorityPK)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._priority != temp._priority)
                return false;
            if (this._has_priority != temp._has_priority)
                return false;
            if (this._transactionTypeCT != null) {
                if (temp._transactionTypeCT == null) return false;
                else if (!(this._transactionTypeCT.equals(temp._transactionTypeCT))) 
                    return false;
            }
            else if (temp._transactionTypeCT != null)
                return false;
            if (this._commissionableEventInd != null) {
                if (temp._commissionableEventInd == null) return false;
                else if (!(this._commissionableEventInd.equals(temp._commissionableEventInd))) 
                    return false;
            }
            else if (temp._commissionableEventInd != null)
                return false;
            if (this._confirmEventInd != null) {
                if (temp._confirmEventInd == null) return false;
                else if (!(this._confirmEventInd.equals(temp._confirmEventInd))) 
                    return false;
            }
            else if (temp._confirmEventInd != null)
                return false;
            if (this._reinsuranceInd != null) {
                if (temp._reinsuranceInd == null) return false;
                else if (!(this._reinsuranceInd.equals(temp._reinsuranceInd))) 
                    return false;
            }
            else if (temp._reinsuranceInd != null)
                return false;
            if (this._bonusChargebackCT != null) {
                if (temp._bonusChargebackCT == null) return false;
                else if (!(this._bonusChargebackCT.equals(temp._bonusChargebackCT))) 
                    return false;
            }
            else if (temp._bonusChargebackCT != null)
                return false;
            if (this._transactionCorrespondenceVOList != null) {
                if (temp._transactionCorrespondenceVOList == null) return false;
                else if (!(this._transactionCorrespondenceVOList.equals(temp._transactionCorrespondenceVOList))) 
                    return false;
            }
            else if (temp._transactionCorrespondenceVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBonusChargebackCTReturns the value of field
     * 'bonusChargebackCT'.
     * 
     * @return the value of field 'bonusChargebackCT'.
     */
    public java.lang.String getBonusChargebackCT()
    {
        return this._bonusChargebackCT;
    } //-- java.lang.String getBonusChargebackCT() 

    /**
     * Method getCommissionableEventIndReturns the value of field
     * 'commissionableEventInd'.
     * 
     * @return the value of field 'commissionableEventInd'.
     */
    public java.lang.String getCommissionableEventInd()
    {
        return this._commissionableEventInd;
    } //-- java.lang.String getCommissionableEventInd() 

    /**
     * Method getConfirmEventIndReturns the value of field
     * 'confirmEventInd'.
     * 
     * @return the value of field 'confirmEventInd'.
     */
    public java.lang.String getConfirmEventInd()
    {
        return this._confirmEventInd;
    } //-- java.lang.String getConfirmEventInd() 

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
     * Method getPriorityReturns the value of field 'priority'.
     * 
     * @return the value of field 'priority'.
     */
    public int getPriority()
    {
        return this._priority;
    } //-- int getPriority() 

    /**
     * Method getReinsuranceIndReturns the value of field
     * 'reinsuranceInd'.
     * 
     * @return the value of field 'reinsuranceInd'.
     */
    public java.lang.String getReinsuranceInd()
    {
        return this._reinsuranceInd;
    } //-- java.lang.String getReinsuranceInd() 

    /**
     * Method getTransactionCorrespondenceVO
     * 
     * @param index
     */
    public edit.common.vo.TransactionCorrespondenceVO getTransactionCorrespondenceVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _transactionCorrespondenceVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.TransactionCorrespondenceVO) _transactionCorrespondenceVOList.elementAt(index);
    } //-- edit.common.vo.TransactionCorrespondenceVO getTransactionCorrespondenceVO(int) 

    /**
     * Method getTransactionCorrespondenceVO
     */
    public edit.common.vo.TransactionCorrespondenceVO[] getTransactionCorrespondenceVO()
    {
        int size = _transactionCorrespondenceVOList.size();
        edit.common.vo.TransactionCorrespondenceVO[] mArray = new edit.common.vo.TransactionCorrespondenceVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.TransactionCorrespondenceVO) _transactionCorrespondenceVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.TransactionCorrespondenceVO[] getTransactionCorrespondenceVO() 

    /**
     * Method getTransactionCorrespondenceVOCount
     */
    public int getTransactionCorrespondenceVOCount()
    {
        return _transactionCorrespondenceVOList.size();
    } //-- int getTransactionCorrespondenceVOCount() 

    /**
     * Method getTransactionPriorityPKReturns the value of field
     * 'transactionPriorityPK'.
     * 
     * @return the value of field 'transactionPriorityPK'.
     */
    public long getTransactionPriorityPK()
    {
        return this._transactionPriorityPK;
    } //-- long getTransactionPriorityPK() 

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
     * Method hasPriority
     */
    public boolean hasPriority()
    {
        return this._has_priority;
    } //-- boolean hasPriority() 

    /**
     * Method hasTransactionPriorityPK
     */
    public boolean hasTransactionPriorityPK()
    {
        return this._has_transactionPriorityPK;
    } //-- boolean hasTransactionPriorityPK() 

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
     * Method removeAllTransactionCorrespondenceVO
     */
    public void removeAllTransactionCorrespondenceVO()
    {
        _transactionCorrespondenceVOList.removeAllElements();
    } //-- void removeAllTransactionCorrespondenceVO() 

    /**
     * Method removeTransactionCorrespondenceVO
     * 
     * @param index
     */
    public edit.common.vo.TransactionCorrespondenceVO removeTransactionCorrespondenceVO(int index)
    {
        java.lang.Object obj = _transactionCorrespondenceVOList.elementAt(index);
        _transactionCorrespondenceVOList.removeElementAt(index);
        return (edit.common.vo.TransactionCorrespondenceVO) obj;
    } //-- edit.common.vo.TransactionCorrespondenceVO removeTransactionCorrespondenceVO(int) 

    /**
     * Method setBonusChargebackCTSets the value of field
     * 'bonusChargebackCT'.
     * 
     * @param bonusChargebackCT the value of field
     * 'bonusChargebackCT'.
     */
    public void setBonusChargebackCT(java.lang.String bonusChargebackCT)
    {
        this._bonusChargebackCT = bonusChargebackCT;
        
        super.setVoChanged(true);
    } //-- void setBonusChargebackCT(java.lang.String) 

    /**
     * Method setCommissionableEventIndSets the value of field
     * 'commissionableEventInd'.
     * 
     * @param commissionableEventInd the value of field
     * 'commissionableEventInd'.
     */
    public void setCommissionableEventInd(java.lang.String commissionableEventInd)
    {
        this._commissionableEventInd = commissionableEventInd;
        
        super.setVoChanged(true);
    } //-- void setCommissionableEventInd(java.lang.String) 

    /**
     * Method setConfirmEventIndSets the value of field
     * 'confirmEventInd'.
     * 
     * @param confirmEventInd the value of field 'confirmEventInd'.
     */
    public void setConfirmEventInd(java.lang.String confirmEventInd)
    {
        this._confirmEventInd = confirmEventInd;
        
        super.setVoChanged(true);
    } //-- void setConfirmEventInd(java.lang.String) 

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
     * Method setPrioritySets the value of field 'priority'.
     * 
     * @param priority the value of field 'priority'.
     */
    public void setPriority(int priority)
    {
        this._priority = priority;
        
        super.setVoChanged(true);
        this._has_priority = true;
    } //-- void setPriority(int) 

    /**
     * Method setReinsuranceIndSets the value of field
     * 'reinsuranceInd'.
     * 
     * @param reinsuranceInd the value of field 'reinsuranceInd'.
     */
    public void setReinsuranceInd(java.lang.String reinsuranceInd)
    {
        this._reinsuranceInd = reinsuranceInd;
        
        super.setVoChanged(true);
    } //-- void setReinsuranceInd(java.lang.String) 

    /**
     * Method setTransactionCorrespondenceVO
     * 
     * @param index
     * @param vTransactionCorrespondenceVO
     */
    public void setTransactionCorrespondenceVO(int index, edit.common.vo.TransactionCorrespondenceVO vTransactionCorrespondenceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _transactionCorrespondenceVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vTransactionCorrespondenceVO.setParentVO(this.getClass(), this);
        _transactionCorrespondenceVOList.setElementAt(vTransactionCorrespondenceVO, index);
    } //-- void setTransactionCorrespondenceVO(int, edit.common.vo.TransactionCorrespondenceVO) 

    /**
     * Method setTransactionCorrespondenceVO
     * 
     * @param transactionCorrespondenceVOArray
     */
    public void setTransactionCorrespondenceVO(edit.common.vo.TransactionCorrespondenceVO[] transactionCorrespondenceVOArray)
    {
        //-- copy array
        _transactionCorrespondenceVOList.removeAllElements();
        for (int i = 0; i < transactionCorrespondenceVOArray.length; i++) {
            transactionCorrespondenceVOArray[i].setParentVO(this.getClass(), this);
            _transactionCorrespondenceVOList.addElement(transactionCorrespondenceVOArray[i]);
        }
    } //-- void setTransactionCorrespondenceVO(edit.common.vo.TransactionCorrespondenceVO) 

    /**
     * Method setTransactionPriorityPKSets the value of field
     * 'transactionPriorityPK'.
     * 
     * @param transactionPriorityPK the value of field
     * 'transactionPriorityPK'.
     */
    public void setTransactionPriorityPK(long transactionPriorityPK)
    {
        this._transactionPriorityPK = transactionPriorityPK;
        
        super.setVoChanged(true);
        this._has_transactionPriorityPK = true;
    } //-- void setTransactionPriorityPK(long) 

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
    public static edit.common.vo.TransactionPriorityVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.TransactionPriorityVO) Unmarshaller.unmarshal(edit.common.vo.TransactionPriorityVO.class, reader);
    } //-- edit.common.vo.TransactionPriorityVO unmarshal(java.io.Reader) 

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
