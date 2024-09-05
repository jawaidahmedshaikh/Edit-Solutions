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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class CheckEFTCorrespondenceVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Comment describing your root element
     */
    private java.util.Vector _checkEFTDetailVOList;

    /**
     * Field _grossAmountTotal
     */
    private double _grossAmountTotal;

    /**
     * keeps track of state for field: _grossAmountTotal
     */
    private boolean _has_grossAmountTotal;

    /**
     * Field _checkAmountTotal
     */
    private double _checkAmountTotal;

    /**
     * keeps track of state for field: _checkAmountTotal
     */
    private boolean _has_checkAmountTotal;

    /**
     * Field _federalWithholdingTotal
     */
    private double _federalWithholdingTotal;

    /**
     * keeps track of state for field: _federalWithholdingTotal
     */
    private boolean _has_federalWithholdingTotal;

    /**
     * Field _stateWithholdingTotal
     */
    private double _stateWithholdingTotal;

    /**
     * keeps track of state for field: _stateWithholdingTotal
     */
    private boolean _has_stateWithholdingTotal;

    /**
     * Field _numberofTransactions
     */
    private int _numberofTransactions;

    /**
     * keeps track of state for field: _numberofTransactions
     */
    private boolean _has_numberofTransactions;

    /**
     * Field _currentDate
     */
    private java.lang.String _currentDate;


      //----------------/
     //- Constructors -/
    //----------------/

    public CheckEFTCorrespondenceVO() {
        super();
        _checkEFTDetailVOList = new Vector();
    } //-- edit.common.vo.CheckEFTCorrespondenceVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addCheckEFTDetailVO
     * 
     * @param vCheckEFTDetailVO
     */
    public void addCheckEFTDetailVO(edit.common.vo.CheckEFTDetailVO vCheckEFTDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCheckEFTDetailVO.setParentVO(this.getClass(), this);
        _checkEFTDetailVOList.addElement(vCheckEFTDetailVO);
    } //-- void addCheckEFTDetailVO(edit.common.vo.CheckEFTDetailVO) 

    /**
     * Method addCheckEFTDetailVO
     * 
     * @param index
     * @param vCheckEFTDetailVO
     */
    public void addCheckEFTDetailVO(int index, edit.common.vo.CheckEFTDetailVO vCheckEFTDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCheckEFTDetailVO.setParentVO(this.getClass(), this);
        _checkEFTDetailVOList.insertElementAt(vCheckEFTDetailVO, index);
    } //-- void addCheckEFTDetailVO(int, edit.common.vo.CheckEFTDetailVO) 

    /**
     * Method enumerateCheckEFTDetailVO
     */
    public java.util.Enumeration enumerateCheckEFTDetailVO()
    {
        return _checkEFTDetailVOList.elements();
    } //-- java.util.Enumeration enumerateCheckEFTDetailVO() 

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
        
        if (obj instanceof CheckEFTCorrespondenceVO) {
        
            CheckEFTCorrespondenceVO temp = (CheckEFTCorrespondenceVO)obj;
            if (this._checkEFTDetailVOList != null) {
                if (temp._checkEFTDetailVOList == null) return false;
                else if (!(this._checkEFTDetailVOList.equals(temp._checkEFTDetailVOList))) 
                    return false;
            }
            else if (temp._checkEFTDetailVOList != null)
                return false;
            if (this._grossAmountTotal != temp._grossAmountTotal)
                return false;
            if (this._has_grossAmountTotal != temp._has_grossAmountTotal)
                return false;
            if (this._checkAmountTotal != temp._checkAmountTotal)
                return false;
            if (this._has_checkAmountTotal != temp._has_checkAmountTotal)
                return false;
            if (this._federalWithholdingTotal != temp._federalWithholdingTotal)
                return false;
            if (this._has_federalWithholdingTotal != temp._has_federalWithholdingTotal)
                return false;
            if (this._stateWithholdingTotal != temp._stateWithholdingTotal)
                return false;
            if (this._has_stateWithholdingTotal != temp._has_stateWithholdingTotal)
                return false;
            if (this._numberofTransactions != temp._numberofTransactions)
                return false;
            if (this._has_numberofTransactions != temp._has_numberofTransactions)
                return false;
            if (this._currentDate != null) {
                if (temp._currentDate == null) return false;
                else if (!(this._currentDate.equals(temp._currentDate))) 
                    return false;
            }
            else if (temp._currentDate != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCheckAmountTotalReturns the value of field
     * 'checkAmountTotal'.
     * 
     * @return the value of field 'checkAmountTotal'.
     */
    public double getCheckAmountTotal()
    {
        return this._checkAmountTotal;
    } //-- double getCheckAmountTotal() 

    /**
     * Method getCheckEFTDetailVO
     * 
     * @param index
     */
    public edit.common.vo.CheckEFTDetailVO getCheckEFTDetailVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _checkEFTDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.CheckEFTDetailVO) _checkEFTDetailVOList.elementAt(index);
    } //-- edit.common.vo.CheckEFTDetailVO getCheckEFTDetailVO(int) 

    /**
     * Method getCheckEFTDetailVO
     */
    public edit.common.vo.CheckEFTDetailVO[] getCheckEFTDetailVO()
    {
        int size = _checkEFTDetailVOList.size();
        edit.common.vo.CheckEFTDetailVO[] mArray = new edit.common.vo.CheckEFTDetailVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.CheckEFTDetailVO) _checkEFTDetailVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.CheckEFTDetailVO[] getCheckEFTDetailVO() 

    /**
     * Method getCheckEFTDetailVOCount
     */
    public int getCheckEFTDetailVOCount()
    {
        return _checkEFTDetailVOList.size();
    } //-- int getCheckEFTDetailVOCount() 

    /**
     * Method getCurrentDateReturns the value of field
     * 'currentDate'.
     * 
     * @return the value of field 'currentDate'.
     */
    public java.lang.String getCurrentDate()
    {
        return this._currentDate;
    } //-- java.lang.String getCurrentDate() 

    /**
     * Method getFederalWithholdingTotalReturns the value of field
     * 'federalWithholdingTotal'.
     * 
     * @return the value of field 'federalWithholdingTotal'.
     */
    public double getFederalWithholdingTotal()
    {
        return this._federalWithholdingTotal;
    } //-- double getFederalWithholdingTotal() 

    /**
     * Method getGrossAmountTotalReturns the value of field
     * 'grossAmountTotal'.
     * 
     * @return the value of field 'grossAmountTotal'.
     */
    public double getGrossAmountTotal()
    {
        return this._grossAmountTotal;
    } //-- double getGrossAmountTotal() 

    /**
     * Method getNumberofTransactionsReturns the value of field
     * 'numberofTransactions'.
     * 
     * @return the value of field 'numberofTransactions'.
     */
    public int getNumberofTransactions()
    {
        return this._numberofTransactions;
    } //-- int getNumberofTransactions() 

    /**
     * Method getStateWithholdingTotalReturns the value of field
     * 'stateWithholdingTotal'.
     * 
     * @return the value of field 'stateWithholdingTotal'.
     */
    public double getStateWithholdingTotal()
    {
        return this._stateWithholdingTotal;
    } //-- double getStateWithholdingTotal() 

    /**
     * Method hasCheckAmountTotal
     */
    public boolean hasCheckAmountTotal()
    {
        return this._has_checkAmountTotal;
    } //-- boolean hasCheckAmountTotal() 

    /**
     * Method hasFederalWithholdingTotal
     */
    public boolean hasFederalWithholdingTotal()
    {
        return this._has_federalWithholdingTotal;
    } //-- boolean hasFederalWithholdingTotal() 

    /**
     * Method hasGrossAmountTotal
     */
    public boolean hasGrossAmountTotal()
    {
        return this._has_grossAmountTotal;
    } //-- boolean hasGrossAmountTotal() 

    /**
     * Method hasNumberofTransactions
     */
    public boolean hasNumberofTransactions()
    {
        return this._has_numberofTransactions;
    } //-- boolean hasNumberofTransactions() 

    /**
     * Method hasStateWithholdingTotal
     */
    public boolean hasStateWithholdingTotal()
    {
        return this._has_stateWithholdingTotal;
    } //-- boolean hasStateWithholdingTotal() 

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
     * Method removeAllCheckEFTDetailVO
     */
    public void removeAllCheckEFTDetailVO()
    {
        _checkEFTDetailVOList.removeAllElements();
    } //-- void removeAllCheckEFTDetailVO() 

    /**
     * Method removeCheckEFTDetailVO
     * 
     * @param index
     */
    public edit.common.vo.CheckEFTDetailVO removeCheckEFTDetailVO(int index)
    {
        java.lang.Object obj = _checkEFTDetailVOList.elementAt(index);
        _checkEFTDetailVOList.removeElementAt(index);
        return (edit.common.vo.CheckEFTDetailVO) obj;
    } //-- edit.common.vo.CheckEFTDetailVO removeCheckEFTDetailVO(int) 

    /**
     * Method setCheckAmountTotalSets the value of field
     * 'checkAmountTotal'.
     * 
     * @param checkAmountTotal the value of field 'checkAmountTotal'
     */
    public void setCheckAmountTotal(double checkAmountTotal)
    {
        this._checkAmountTotal = checkAmountTotal;
        
        super.setVoChanged(true);
        this._has_checkAmountTotal = true;
    } //-- void setCheckAmountTotal(double) 

    /**
     * Method setCheckEFTDetailVO
     * 
     * @param index
     * @param vCheckEFTDetailVO
     */
    public void setCheckEFTDetailVO(int index, edit.common.vo.CheckEFTDetailVO vCheckEFTDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _checkEFTDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vCheckEFTDetailVO.setParentVO(this.getClass(), this);
        _checkEFTDetailVOList.setElementAt(vCheckEFTDetailVO, index);
    } //-- void setCheckEFTDetailVO(int, edit.common.vo.CheckEFTDetailVO) 

    /**
     * Method setCheckEFTDetailVO
     * 
     * @param checkEFTDetailVOArray
     */
    public void setCheckEFTDetailVO(edit.common.vo.CheckEFTDetailVO[] checkEFTDetailVOArray)
    {
        //-- copy array
        _checkEFTDetailVOList.removeAllElements();
        for (int i = 0; i < checkEFTDetailVOArray.length; i++) {
            checkEFTDetailVOArray[i].setParentVO(this.getClass(), this);
            _checkEFTDetailVOList.addElement(checkEFTDetailVOArray[i]);
        }
    } //-- void setCheckEFTDetailVO(edit.common.vo.CheckEFTDetailVO) 

    /**
     * Method setCurrentDateSets the value of field 'currentDate'.
     * 
     * @param currentDate the value of field 'currentDate'.
     */
    public void setCurrentDate(java.lang.String currentDate)
    {
        this._currentDate = currentDate;
        
        super.setVoChanged(true);
    } //-- void setCurrentDate(java.lang.String) 

    /**
     * Method setFederalWithholdingTotalSets the value of field
     * 'federalWithholdingTotal'.
     * 
     * @param federalWithholdingTotal the value of field
     * 'federalWithholdingTotal'.
     */
    public void setFederalWithholdingTotal(double federalWithholdingTotal)
    {
        this._federalWithholdingTotal = federalWithholdingTotal;
        
        super.setVoChanged(true);
        this._has_federalWithholdingTotal = true;
    } //-- void setFederalWithholdingTotal(double) 

    /**
     * Method setGrossAmountTotalSets the value of field
     * 'grossAmountTotal'.
     * 
     * @param grossAmountTotal the value of field 'grossAmountTotal'
     */
    public void setGrossAmountTotal(double grossAmountTotal)
    {
        this._grossAmountTotal = grossAmountTotal;
        
        super.setVoChanged(true);
        this._has_grossAmountTotal = true;
    } //-- void setGrossAmountTotal(double) 

    /**
     * Method setNumberofTransactionsSets the value of field
     * 'numberofTransactions'.
     * 
     * @param numberofTransactions the value of field
     * 'numberofTransactions'.
     */
    public void setNumberofTransactions(int numberofTransactions)
    {
        this._numberofTransactions = numberofTransactions;
        
        super.setVoChanged(true);
        this._has_numberofTransactions = true;
    } //-- void setNumberofTransactions(int) 

    /**
     * Method setStateWithholdingTotalSets the value of field
     * 'stateWithholdingTotal'.
     * 
     * @param stateWithholdingTotal the value of field
     * 'stateWithholdingTotal'.
     */
    public void setStateWithholdingTotal(double stateWithholdingTotal)
    {
        this._stateWithholdingTotal = stateWithholdingTotal;
        
        super.setVoChanged(true);
        this._has_stateWithholdingTotal = true;
    } //-- void setStateWithholdingTotal(double) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CheckEFTCorrespondenceVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CheckEFTCorrespondenceVO) Unmarshaller.unmarshal(edit.common.vo.CheckEFTCorrespondenceVO.class, reader);
    } //-- edit.common.vo.CheckEFTCorrespondenceVO unmarshal(java.io.Reader) 

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
