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
 * Class TransactionCorrespondenceVO.
 * 
 * @version $Revision$ $Date$
 */
public class TransactionCorrespondenceVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _transactionCorrespondencePK
     */
    private long _transactionCorrespondencePK;

    /**
     * keeps track of state for field: _transactionCorrespondencePK
     */
    private boolean _has_transactionCorrespondencePK;

    /**
     * Field _transactionPriorityFK
     */
    private long _transactionPriorityFK;

    /**
     * keeps track of state for field: _transactionPriorityFK
     */
    private boolean _has_transactionPriorityFK;

    /**
     * Field _correspondenceTypeCT
     */
    private java.lang.String _correspondenceTypeCT;

    /**
     * Field _numberOfDays
     */
    private int _numberOfDays;

    /**
     * keeps track of state for field: _numberOfDays
     */
    private boolean _has_numberOfDays;

    /**
     * Field _priorPostCT
     */
    private java.lang.String _priorPostCT;

    /**
     * Field _transactionTypeQualifierCT
     */
    private java.lang.String _transactionTypeQualifierCT;

    /**
     * Field _includeHistoryIndicator
     */
    private java.lang.String _includeHistoryIndicator;

    /**
     * Field _startDate
     */
    private java.lang.String _startDate;

    /**
     * Field _stopDate
     */
    private java.lang.String _stopDate;

    /**
     * Field _EDITTrxCorrespondenceVOList
     */
    private java.util.Vector _EDITTrxCorrespondenceVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public TransactionCorrespondenceVO() {
        super();
        _EDITTrxCorrespondenceVOList = new Vector();
    } //-- edit.common.vo.TransactionCorrespondenceVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addEDITTrxCorrespondenceVO
     * 
     * @param vEDITTrxCorrespondenceVO
     */
    public void addEDITTrxCorrespondenceVO(edit.common.vo.EDITTrxCorrespondenceVO vEDITTrxCorrespondenceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEDITTrxCorrespondenceVO.setParentVO(this.getClass(), this);
        _EDITTrxCorrespondenceVOList.addElement(vEDITTrxCorrespondenceVO);
    } //-- void addEDITTrxCorrespondenceVO(edit.common.vo.EDITTrxCorrespondenceVO) 

    /**
     * Method addEDITTrxCorrespondenceVO
     * 
     * @param index
     * @param vEDITTrxCorrespondenceVO
     */
    public void addEDITTrxCorrespondenceVO(int index, edit.common.vo.EDITTrxCorrespondenceVO vEDITTrxCorrespondenceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEDITTrxCorrespondenceVO.setParentVO(this.getClass(), this);
        _EDITTrxCorrespondenceVOList.insertElementAt(vEDITTrxCorrespondenceVO, index);
    } //-- void addEDITTrxCorrespondenceVO(int, edit.common.vo.EDITTrxCorrespondenceVO) 

    /**
     * Method enumerateEDITTrxCorrespondenceVO
     */
    public java.util.Enumeration enumerateEDITTrxCorrespondenceVO()
    {
        return _EDITTrxCorrespondenceVOList.elements();
    } //-- java.util.Enumeration enumerateEDITTrxCorrespondenceVO() 

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
        
        if (obj instanceof TransactionCorrespondenceVO) {
        
            TransactionCorrespondenceVO temp = (TransactionCorrespondenceVO)obj;
            if (this._transactionCorrespondencePK != temp._transactionCorrespondencePK)
                return false;
            if (this._has_transactionCorrespondencePK != temp._has_transactionCorrespondencePK)
                return false;
            if (this._transactionPriorityFK != temp._transactionPriorityFK)
                return false;
            if (this._has_transactionPriorityFK != temp._has_transactionPriorityFK)
                return false;
            if (this._correspondenceTypeCT != null) {
                if (temp._correspondenceTypeCT == null) return false;
                else if (!(this._correspondenceTypeCT.equals(temp._correspondenceTypeCT))) 
                    return false;
            }
            else if (temp._correspondenceTypeCT != null)
                return false;
            if (this._numberOfDays != temp._numberOfDays)
                return false;
            if (this._has_numberOfDays != temp._has_numberOfDays)
                return false;
            if (this._priorPostCT != null) {
                if (temp._priorPostCT == null) return false;
                else if (!(this._priorPostCT.equals(temp._priorPostCT))) 
                    return false;
            }
            else if (temp._priorPostCT != null)
                return false;
            if (this._transactionTypeQualifierCT != null) {
                if (temp._transactionTypeQualifierCT == null) return false;
                else if (!(this._transactionTypeQualifierCT.equals(temp._transactionTypeQualifierCT))) 
                    return false;
            }
            else if (temp._transactionTypeQualifierCT != null)
                return false;
            if (this._includeHistoryIndicator != null) {
                if (temp._includeHistoryIndicator == null) return false;
                else if (!(this._includeHistoryIndicator.equals(temp._includeHistoryIndicator))) 
                    return false;
            }
            else if (temp._includeHistoryIndicator != null)
                return false;
            if (this._startDate != null) {
                if (temp._startDate == null) return false;
                else if (!(this._startDate.equals(temp._startDate))) 
                    return false;
            }
            else if (temp._startDate != null)
                return false;
            if (this._stopDate != null) {
                if (temp._stopDate == null) return false;
                else if (!(this._stopDate.equals(temp._stopDate))) 
                    return false;
            }
            else if (temp._stopDate != null)
                return false;
            if (this._EDITTrxCorrespondenceVOList != null) {
                if (temp._EDITTrxCorrespondenceVOList == null) return false;
                else if (!(this._EDITTrxCorrespondenceVOList.equals(temp._EDITTrxCorrespondenceVOList))) 
                    return false;
            }
            else if (temp._EDITTrxCorrespondenceVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCorrespondenceTypeCTReturns the value of field
     * 'correspondenceTypeCT'.
     * 
     * @return the value of field 'correspondenceTypeCT'.
     */
    public java.lang.String getCorrespondenceTypeCT()
    {
        return this._correspondenceTypeCT;
    } //-- java.lang.String getCorrespondenceTypeCT() 

    /**
     * Method getEDITTrxCorrespondenceVO
     * 
     * @param index
     */
    public edit.common.vo.EDITTrxCorrespondenceVO getEDITTrxCorrespondenceVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITTrxCorrespondenceVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.EDITTrxCorrespondenceVO) _EDITTrxCorrespondenceVOList.elementAt(index);
    } //-- edit.common.vo.EDITTrxCorrespondenceVO getEDITTrxCorrespondenceVO(int) 

    /**
     * Method getEDITTrxCorrespondenceVO
     */
    public edit.common.vo.EDITTrxCorrespondenceVO[] getEDITTrxCorrespondenceVO()
    {
        int size = _EDITTrxCorrespondenceVOList.size();
        edit.common.vo.EDITTrxCorrespondenceVO[] mArray = new edit.common.vo.EDITTrxCorrespondenceVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.EDITTrxCorrespondenceVO) _EDITTrxCorrespondenceVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.EDITTrxCorrespondenceVO[] getEDITTrxCorrespondenceVO() 

    /**
     * Method getEDITTrxCorrespondenceVOCount
     */
    public int getEDITTrxCorrespondenceVOCount()
    {
        return _EDITTrxCorrespondenceVOList.size();
    } //-- int getEDITTrxCorrespondenceVOCount() 

    /**
     * Method getIncludeHistoryIndicatorReturns the value of field
     * 'includeHistoryIndicator'.
     * 
     * @return the value of field 'includeHistoryIndicator'.
     */
    public java.lang.String getIncludeHistoryIndicator()
    {
        return this._includeHistoryIndicator;
    } //-- java.lang.String getIncludeHistoryIndicator() 

    /**
     * Method getNumberOfDaysReturns the value of field
     * 'numberOfDays'.
     * 
     * @return the value of field 'numberOfDays'.
     */
    public int getNumberOfDays()
    {
        return this._numberOfDays;
    } //-- int getNumberOfDays() 

    /**
     * Method getPriorPostCTReturns the value of field
     * 'priorPostCT'.
     * 
     * @return the value of field 'priorPostCT'.
     */
    public java.lang.String getPriorPostCT()
    {
        return this._priorPostCT;
    } //-- java.lang.String getPriorPostCT() 

    /**
     * Method getStartDateReturns the value of field 'startDate'.
     * 
     * @return the value of field 'startDate'.
     */
    public java.lang.String getStartDate()
    {
        return this._startDate;
    } //-- java.lang.String getStartDate() 

    /**
     * Method getStopDateReturns the value of field 'stopDate'.
     * 
     * @return the value of field 'stopDate'.
     */
    public java.lang.String getStopDate()
    {
        return this._stopDate;
    } //-- java.lang.String getStopDate() 

    /**
     * Method getTransactionCorrespondencePKReturns the value of
     * field 'transactionCorrespondencePK'.
     * 
     * @return the value of field 'transactionCorrespondencePK'.
     */
    public long getTransactionCorrespondencePK()
    {
        return this._transactionCorrespondencePK;
    } //-- long getTransactionCorrespondencePK() 

    /**
     * Method getTransactionPriorityFKReturns the value of field
     * 'transactionPriorityFK'.
     * 
     * @return the value of field 'transactionPriorityFK'.
     */
    public long getTransactionPriorityFK()
    {
        return this._transactionPriorityFK;
    } //-- long getTransactionPriorityFK() 

    /**
     * Method getTransactionTypeQualifierCTReturns the value of
     * field 'transactionTypeQualifierCT'.
     * 
     * @return the value of field 'transactionTypeQualifierCT'.
     */
    public java.lang.String getTransactionTypeQualifierCT()
    {
        return this._transactionTypeQualifierCT;
    } //-- java.lang.String getTransactionTypeQualifierCT() 

    /**
     * Method hasNumberOfDays
     */
    public boolean hasNumberOfDays()
    {
        return this._has_numberOfDays;
    } //-- boolean hasNumberOfDays() 

    /**
     * Method hasTransactionCorrespondencePK
     */
    public boolean hasTransactionCorrespondencePK()
    {
        return this._has_transactionCorrespondencePK;
    } //-- boolean hasTransactionCorrespondencePK() 

    /**
     * Method hasTransactionPriorityFK
     */
    public boolean hasTransactionPriorityFK()
    {
        return this._has_transactionPriorityFK;
    } //-- boolean hasTransactionPriorityFK() 

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
     * Method removeAllEDITTrxCorrespondenceVO
     */
    public void removeAllEDITTrxCorrespondenceVO()
    {
        _EDITTrxCorrespondenceVOList.removeAllElements();
    } //-- void removeAllEDITTrxCorrespondenceVO() 

    /**
     * Method removeEDITTrxCorrespondenceVO
     * 
     * @param index
     */
    public edit.common.vo.EDITTrxCorrespondenceVO removeEDITTrxCorrespondenceVO(int index)
    {
        java.lang.Object obj = _EDITTrxCorrespondenceVOList.elementAt(index);
        _EDITTrxCorrespondenceVOList.removeElementAt(index);
        return (edit.common.vo.EDITTrxCorrespondenceVO) obj;
    } //-- edit.common.vo.EDITTrxCorrespondenceVO removeEDITTrxCorrespondenceVO(int) 

    /**
     * Method setCorrespondenceTypeCTSets the value of field
     * 'correspondenceTypeCT'.
     * 
     * @param correspondenceTypeCT the value of field
     * 'correspondenceTypeCT'.
     */
    public void setCorrespondenceTypeCT(java.lang.String correspondenceTypeCT)
    {
        this._correspondenceTypeCT = correspondenceTypeCT;
        
        super.setVoChanged(true);
    } //-- void setCorrespondenceTypeCT(java.lang.String) 

    /**
     * Method setEDITTrxCorrespondenceVO
     * 
     * @param index
     * @param vEDITTrxCorrespondenceVO
     */
    public void setEDITTrxCorrespondenceVO(int index, edit.common.vo.EDITTrxCorrespondenceVO vEDITTrxCorrespondenceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITTrxCorrespondenceVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vEDITTrxCorrespondenceVO.setParentVO(this.getClass(), this);
        _EDITTrxCorrespondenceVOList.setElementAt(vEDITTrxCorrespondenceVO, index);
    } //-- void setEDITTrxCorrespondenceVO(int, edit.common.vo.EDITTrxCorrespondenceVO) 

    /**
     * Method setEDITTrxCorrespondenceVO
     * 
     * @param EDITTrxCorrespondenceVOArray
     */
    public void setEDITTrxCorrespondenceVO(edit.common.vo.EDITTrxCorrespondenceVO[] EDITTrxCorrespondenceVOArray)
    {
        //-- copy array
        _EDITTrxCorrespondenceVOList.removeAllElements();
        for (int i = 0; i < EDITTrxCorrespondenceVOArray.length; i++) {
            EDITTrxCorrespondenceVOArray[i].setParentVO(this.getClass(), this);
            _EDITTrxCorrespondenceVOList.addElement(EDITTrxCorrespondenceVOArray[i]);
        }
    } //-- void setEDITTrxCorrespondenceVO(edit.common.vo.EDITTrxCorrespondenceVO) 

    /**
     * Method setIncludeHistoryIndicatorSets the value of field
     * 'includeHistoryIndicator'.
     * 
     * @param includeHistoryIndicator the value of field
     * 'includeHistoryIndicator'.
     */
    public void setIncludeHistoryIndicator(java.lang.String includeHistoryIndicator)
    {
        this._includeHistoryIndicator = includeHistoryIndicator;
        
        super.setVoChanged(true);
    } //-- void setIncludeHistoryIndicator(java.lang.String) 

    /**
     * Method setNumberOfDaysSets the value of field
     * 'numberOfDays'.
     * 
     * @param numberOfDays the value of field 'numberOfDays'.
     */
    public void setNumberOfDays(int numberOfDays)
    {
        this._numberOfDays = numberOfDays;
        
        super.setVoChanged(true);
        this._has_numberOfDays = true;
    } //-- void setNumberOfDays(int) 

    /**
     * Method setPriorPostCTSets the value of field 'priorPostCT'.
     * 
     * @param priorPostCT the value of field 'priorPostCT'.
     */
    public void setPriorPostCT(java.lang.String priorPostCT)
    {
        this._priorPostCT = priorPostCT;
        
        super.setVoChanged(true);
    } //-- void setPriorPostCT(java.lang.String) 

    /**
     * Method setStartDateSets the value of field 'startDate'.
     * 
     * @param startDate the value of field 'startDate'.
     */
    public void setStartDate(java.lang.String startDate)
    {
        this._startDate = startDate;
        
        super.setVoChanged(true);
    } //-- void setStartDate(java.lang.String) 

    /**
     * Method setStopDateSets the value of field 'stopDate'.
     * 
     * @param stopDate the value of field 'stopDate'.
     */
    public void setStopDate(java.lang.String stopDate)
    {
        this._stopDate = stopDate;
        
        super.setVoChanged(true);
    } //-- void setStopDate(java.lang.String) 

    /**
     * Method setTransactionCorrespondencePKSets the value of field
     * 'transactionCorrespondencePK'.
     * 
     * @param transactionCorrespondencePK the value of field
     * 'transactionCorrespondencePK'.
     */
    public void setTransactionCorrespondencePK(long transactionCorrespondencePK)
    {
        this._transactionCorrespondencePK = transactionCorrespondencePK;
        
        super.setVoChanged(true);
        this._has_transactionCorrespondencePK = true;
    } //-- void setTransactionCorrespondencePK(long) 

    /**
     * Method setTransactionPriorityFKSets the value of field
     * 'transactionPriorityFK'.
     * 
     * @param transactionPriorityFK the value of field
     * 'transactionPriorityFK'.
     */
    public void setTransactionPriorityFK(long transactionPriorityFK)
    {
        this._transactionPriorityFK = transactionPriorityFK;
        
        super.setVoChanged(true);
        this._has_transactionPriorityFK = true;
    } //-- void setTransactionPriorityFK(long) 

    /**
     * Method setTransactionTypeQualifierCTSets the value of field
     * 'transactionTypeQualifierCT'.
     * 
     * @param transactionTypeQualifierCT the value of field
     * 'transactionTypeQualifierCT'.
     */
    public void setTransactionTypeQualifierCT(java.lang.String transactionTypeQualifierCT)
    {
        this._transactionTypeQualifierCT = transactionTypeQualifierCT;
        
        super.setVoChanged(true);
    } //-- void setTransactionTypeQualifierCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.TransactionCorrespondenceVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.TransactionCorrespondenceVO) Unmarshaller.unmarshal(edit.common.vo.TransactionCorrespondenceVO.class, reader);
    } //-- edit.common.vo.TransactionCorrespondenceVO unmarshal(java.io.Reader) 

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
