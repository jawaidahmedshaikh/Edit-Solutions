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
 * Class PreferenceVO.
 * 
 * @version $Revision$ $Date$
 */
public class PreferenceVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _preferencePK
     */
    private long _preferencePK;

    /**
     * keeps track of state for field: _preferencePK
     */
    private boolean _has_preferencePK;

    /**
     * Field _clientDetailFK
     */
    private long _clientDetailFK;

    /**
     * keeps track of state for field: _clientDetailFK
     */
    private boolean _has_clientDetailFK;

    /**
     * Field _printAs
     */
    private java.lang.String _printAs;

    /**
     * Field _printAs2
     */
    private java.lang.String _printAs2;

    /**
     * Field _disbursementSourceCT
     */
    private java.lang.String _disbursementSourceCT;

    /**
     * Field _paymentModeCT
     */
    private java.lang.String _paymentModeCT;

    /**
     * Field _minimumCheck
     */
    private java.math.BigDecimal _minimumCheck;

    /**
     * Field _overrideStatus
     */
    private java.lang.String _overrideStatus;

    /**
     * Field _bankAccountNumber
     */
    private java.lang.String _bankAccountNumber;

    /**
     * Field _bankRoutingNumber
     */
    private java.lang.String _bankRoutingNumber;

    /**
     * Field _bankAccountTypeCT
     */
    private java.lang.String _bankAccountTypeCT;

    /**
     * Field _bankName
     */
    private java.lang.String _bankName;

    /**
     * Field _bankAddressLine1
     */
    private java.lang.String _bankAddressLine1;

    /**
     * Field _bankAddressLine2
     */
    private java.lang.String _bankAddressLine2;

    /**
     * Field _bankCity
     */
    private java.lang.String _bankCity;

    /**
     * Field _bankStateCT
     */
    private java.lang.String _bankStateCT;

    /**
     * Field _bankZipCode
     */
    private java.lang.String _bankZipCode;

    /**
     * Field _preferenceTypeCT
     */
    private java.lang.String _preferenceTypeCT;

    /**
     * Field _suspenseVOList
     */
    private java.util.Vector _suspenseVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public PreferenceVO() {
        super();
        _suspenseVOList = new Vector();
    } //-- edit.common.vo.PreferenceVO()


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
        
        if (obj instanceof PreferenceVO) {
        
            PreferenceVO temp = (PreferenceVO)obj;
            if (this._preferencePK != temp._preferencePK)
                return false;
            if (this._has_preferencePK != temp._has_preferencePK)
                return false;
            if (this._clientDetailFK != temp._clientDetailFK)
                return false;
            if (this._has_clientDetailFK != temp._has_clientDetailFK)
                return false;
            if (this._printAs != null) {
                if (temp._printAs == null) return false;
                else if (!(this._printAs.equals(temp._printAs))) 
                    return false;
            }
            else if (temp._printAs != null)
                return false;
            if (this._printAs2 != null) {
                if (temp._printAs2 == null) return false;
                else if (!(this._printAs2.equals(temp._printAs2))) 
                    return false;
            }
            else if (temp._printAs2 != null)
                return false;
            if (this._disbursementSourceCT != null) {
                if (temp._disbursementSourceCT == null) return false;
                else if (!(this._disbursementSourceCT.equals(temp._disbursementSourceCT))) 
                    return false;
            }
            else if (temp._disbursementSourceCT != null)
                return false;
            if (this._paymentModeCT != null) {
                if (temp._paymentModeCT == null) return false;
                else if (!(this._paymentModeCT.equals(temp._paymentModeCT))) 
                    return false;
            }
            else if (temp._paymentModeCT != null)
                return false;
            if (this._minimumCheck != null) {
                if (temp._minimumCheck == null) return false;
                else if (!(this._minimumCheck.equals(temp._minimumCheck))) 
                    return false;
            }
            else if (temp._minimumCheck != null)
                return false;
            if (this._overrideStatus != null) {
                if (temp._overrideStatus == null) return false;
                else if (!(this._overrideStatus.equals(temp._overrideStatus))) 
                    return false;
            }
            else if (temp._overrideStatus != null)
                return false;
            if (this._bankAccountNumber != null) {
                if (temp._bankAccountNumber == null) return false;
                else if (!(this._bankAccountNumber.equals(temp._bankAccountNumber))) 
                    return false;
            }
            else if (temp._bankAccountNumber != null)
                return false;
            if (this._bankRoutingNumber != null) {
                if (temp._bankRoutingNumber == null) return false;
                else if (!(this._bankRoutingNumber.equals(temp._bankRoutingNumber))) 
                    return false;
            }
            else if (temp._bankRoutingNumber != null)
                return false;
            if (this._bankAccountTypeCT != null) {
                if (temp._bankAccountTypeCT == null) return false;
                else if (!(this._bankAccountTypeCT.equals(temp._bankAccountTypeCT))) 
                    return false;
            }
            else if (temp._bankAccountTypeCT != null)
                return false;
            if (this._bankName != null) {
                if (temp._bankName == null) return false;
                else if (!(this._bankName.equals(temp._bankName))) 
                    return false;
            }
            else if (temp._bankName != null)
                return false;
            if (this._bankAddressLine1 != null) {
                if (temp._bankAddressLine1 == null) return false;
                else if (!(this._bankAddressLine1.equals(temp._bankAddressLine1))) 
                    return false;
            }
            else if (temp._bankAddressLine1 != null)
                return false;
            if (this._bankAddressLine2 != null) {
                if (temp._bankAddressLine2 == null) return false;
                else if (!(this._bankAddressLine2.equals(temp._bankAddressLine2))) 
                    return false;
            }
            else if (temp._bankAddressLine2 != null)
                return false;
            if (this._bankCity != null) {
                if (temp._bankCity == null) return false;
                else if (!(this._bankCity.equals(temp._bankCity))) 
                    return false;
            }
            else if (temp._bankCity != null)
                return false;
            if (this._bankStateCT != null) {
                if (temp._bankStateCT == null) return false;
                else if (!(this._bankStateCT.equals(temp._bankStateCT))) 
                    return false;
            }
            else if (temp._bankStateCT != null)
                return false;
            if (this._bankZipCode != null) {
                if (temp._bankZipCode == null) return false;
                else if (!(this._bankZipCode.equals(temp._bankZipCode))) 
                    return false;
            }
            else if (temp._bankZipCode != null)
                return false;
            if (this._preferenceTypeCT != null) {
                if (temp._preferenceTypeCT == null) return false;
                else if (!(this._preferenceTypeCT.equals(temp._preferenceTypeCT))) 
                    return false;
            }
            else if (temp._preferenceTypeCT != null)
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
     * Method getBankAccountNumberReturns the value of field
     * 'bankAccountNumber'.
     * 
     * @return the value of field 'bankAccountNumber'.
     */
    public java.lang.String getBankAccountNumber()
    {
        return this._bankAccountNumber;
    } //-- java.lang.String getBankAccountNumber() 

    /**
     * Method getBankAccountTypeCTReturns the value of field
     * 'bankAccountTypeCT'.
     * 
     * @return the value of field 'bankAccountTypeCT'.
     */
    public java.lang.String getBankAccountTypeCT()
    {
        return this._bankAccountTypeCT;
    } //-- java.lang.String getBankAccountTypeCT() 

    /**
     * Method getBankAddressLine1Returns the value of field
     * 'bankAddressLine1'.
     * 
     * @return the value of field 'bankAddressLine1'.
     */
    public java.lang.String getBankAddressLine1()
    {
        return this._bankAddressLine1;
    } //-- java.lang.String getBankAddressLine1() 

    /**
     * Method getBankAddressLine2Returns the value of field
     * 'bankAddressLine2'.
     * 
     * @return the value of field 'bankAddressLine2'.
     */
    public java.lang.String getBankAddressLine2()
    {
        return this._bankAddressLine2;
    } //-- java.lang.String getBankAddressLine2() 

    /**
     * Method getBankCityReturns the value of field 'bankCity'.
     * 
     * @return the value of field 'bankCity'.
     */
    public java.lang.String getBankCity()
    {
        return this._bankCity;
    } //-- java.lang.String getBankCity() 

    /**
     * Method getBankNameReturns the value of field 'bankName'.
     * 
     * @return the value of field 'bankName'.
     */
    public java.lang.String getBankName()
    {
        return this._bankName;
    } //-- java.lang.String getBankName() 

    /**
     * Method getBankRoutingNumberReturns the value of field
     * 'bankRoutingNumber'.
     * 
     * @return the value of field 'bankRoutingNumber'.
     */
    public java.lang.String getBankRoutingNumber()
    {
        return this._bankRoutingNumber;
    } //-- java.lang.String getBankRoutingNumber() 

    /**
     * Method getBankStateCTReturns the value of field
     * 'bankStateCT'.
     * 
     * @return the value of field 'bankStateCT'.
     */
    public java.lang.String getBankStateCT()
    {
        return this._bankStateCT;
    } //-- java.lang.String getBankStateCT() 

    /**
     * Method getBankZipCodeReturns the value of field
     * 'bankZipCode'.
     * 
     * @return the value of field 'bankZipCode'.
     */
    public java.lang.String getBankZipCode()
    {
        return this._bankZipCode;
    } //-- java.lang.String getBankZipCode() 

    /**
     * Method getClientDetailFKReturns the value of field
     * 'clientDetailFK'.
     * 
     * @return the value of field 'clientDetailFK'.
     */
    public long getClientDetailFK()
    {
        return this._clientDetailFK;
    } //-- long getClientDetailFK() 

    /**
     * Method getDisbursementSourceCTReturns the value of field
     * 'disbursementSourceCT'.
     * 
     * @return the value of field 'disbursementSourceCT'.
     */
    public java.lang.String getDisbursementSourceCT()
    {
        return this._disbursementSourceCT;
    } //-- java.lang.String getDisbursementSourceCT() 

    /**
     * Method getMinimumCheckReturns the value of field
     * 'minimumCheck'.
     * 
     * @return the value of field 'minimumCheck'.
     */
    public java.math.BigDecimal getMinimumCheck()
    {
        return this._minimumCheck;
    } //-- java.math.BigDecimal getMinimumCheck() 

    /**
     * Method getOverrideStatusReturns the value of field
     * 'overrideStatus'.
     * 
     * @return the value of field 'overrideStatus'.
     */
    public java.lang.String getOverrideStatus()
    {
        return this._overrideStatus;
    } //-- java.lang.String getOverrideStatus() 

    /**
     * Method getPaymentModeCTReturns the value of field
     * 'paymentModeCT'.
     * 
     * @return the value of field 'paymentModeCT'.
     */
    public java.lang.String getPaymentModeCT()
    {
        return this._paymentModeCT;
    } //-- java.lang.String getPaymentModeCT() 

    /**
     * Method getPreferencePKReturns the value of field
     * 'preferencePK'.
     * 
     * @return the value of field 'preferencePK'.
     */
    public long getPreferencePK()
    {
        return this._preferencePK;
    } //-- long getPreferencePK() 

    /**
     * Method getPreferenceTypeCTReturns the value of field
     * 'preferenceTypeCT'.
     * 
     * @return the value of field 'preferenceTypeCT'.
     */
    public java.lang.String getPreferenceTypeCT()
    {
        return this._preferenceTypeCT;
    } //-- java.lang.String getPreferenceTypeCT() 

    /**
     * Method getPrintAsReturns the value of field 'printAs'.
     * 
     * @return the value of field 'printAs'.
     */
    public java.lang.String getPrintAs()
    {
        return this._printAs;
    } //-- java.lang.String getPrintAs() 

    /**
     * Method getPrintAs2Returns the value of field 'printAs2'.
     * 
     * @return the value of field 'printAs2'.
     */
    public java.lang.String getPrintAs2()
    {
        return this._printAs2;
    } //-- java.lang.String getPrintAs2() 

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
     * Method hasClientDetailFK
     */
    public boolean hasClientDetailFK()
    {
        return this._has_clientDetailFK;
    } //-- boolean hasClientDetailFK() 

    /**
     * Method hasPreferencePK
     */
    public boolean hasPreferencePK()
    {
        return this._has_preferencePK;
    } //-- boolean hasPreferencePK() 

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
     * Method setBankAccountNumberSets the value of field
     * 'bankAccountNumber'.
     * 
     * @param bankAccountNumber the value of field
     * 'bankAccountNumber'.
     */
    public void setBankAccountNumber(java.lang.String bankAccountNumber)
    {
        this._bankAccountNumber = bankAccountNumber;
        
        super.setVoChanged(true);
    } //-- void setBankAccountNumber(java.lang.String) 

    /**
     * Method setBankAccountTypeCTSets the value of field
     * 'bankAccountTypeCT'.
     * 
     * @param bankAccountTypeCT the value of field
     * 'bankAccountTypeCT'.
     */
    public void setBankAccountTypeCT(java.lang.String bankAccountTypeCT)
    {
        this._bankAccountTypeCT = bankAccountTypeCT;
        
        super.setVoChanged(true);
    } //-- void setBankAccountTypeCT(java.lang.String) 

    /**
     * Method setBankAddressLine1Sets the value of field
     * 'bankAddressLine1'.
     * 
     * @param bankAddressLine1 the value of field 'bankAddressLine1'
     */
    public void setBankAddressLine1(java.lang.String bankAddressLine1)
    {
        this._bankAddressLine1 = bankAddressLine1;
        
        super.setVoChanged(true);
    } //-- void setBankAddressLine1(java.lang.String) 

    /**
     * Method setBankAddressLine2Sets the value of field
     * 'bankAddressLine2'.
     * 
     * @param bankAddressLine2 the value of field 'bankAddressLine2'
     */
    public void setBankAddressLine2(java.lang.String bankAddressLine2)
    {
        this._bankAddressLine2 = bankAddressLine2;
        
        super.setVoChanged(true);
    } //-- void setBankAddressLine2(java.lang.String) 

    /**
     * Method setBankCitySets the value of field 'bankCity'.
     * 
     * @param bankCity the value of field 'bankCity'.
     */
    public void setBankCity(java.lang.String bankCity)
    {
        this._bankCity = bankCity;
        
        super.setVoChanged(true);
    } //-- void setBankCity(java.lang.String) 

    /**
     * Method setBankNameSets the value of field 'bankName'.
     * 
     * @param bankName the value of field 'bankName'.
     */
    public void setBankName(java.lang.String bankName)
    {
        this._bankName = bankName;
        
        super.setVoChanged(true);
    } //-- void setBankName(java.lang.String) 

    /**
     * Method setBankRoutingNumberSets the value of field
     * 'bankRoutingNumber'.
     * 
     * @param bankRoutingNumber the value of field
     * 'bankRoutingNumber'.
     */
    public void setBankRoutingNumber(java.lang.String bankRoutingNumber)
    {
        this._bankRoutingNumber = bankRoutingNumber;
        
        super.setVoChanged(true);
    } //-- void setBankRoutingNumber(java.lang.String) 

    /**
     * Method setBankStateCTSets the value of field 'bankStateCT'.
     * 
     * @param bankStateCT the value of field 'bankStateCT'.
     */
    public void setBankStateCT(java.lang.String bankStateCT)
    {
        this._bankStateCT = bankStateCT;
        
        super.setVoChanged(true);
    } //-- void setBankStateCT(java.lang.String) 

    /**
     * Method setBankZipCodeSets the value of field 'bankZipCode'.
     * 
     * @param bankZipCode the value of field 'bankZipCode'.
     */
    public void setBankZipCode(java.lang.String bankZipCode)
    {
        this._bankZipCode = bankZipCode;
        
        super.setVoChanged(true);
    } //-- void setBankZipCode(java.lang.String) 

    /**
     * Method setClientDetailFKSets the value of field
     * 'clientDetailFK'.
     * 
     * @param clientDetailFK the value of field 'clientDetailFK'.
     */
    public void setClientDetailFK(long clientDetailFK)
    {
        this._clientDetailFK = clientDetailFK;
        
        super.setVoChanged(true);
        this._has_clientDetailFK = true;
    } //-- void setClientDetailFK(long) 

    /**
     * Method setDisbursementSourceCTSets the value of field
     * 'disbursementSourceCT'.
     * 
     * @param disbursementSourceCT the value of field
     * 'disbursementSourceCT'.
     */
    public void setDisbursementSourceCT(java.lang.String disbursementSourceCT)
    {
        this._disbursementSourceCT = disbursementSourceCT;
        
        super.setVoChanged(true);
    } //-- void setDisbursementSourceCT(java.lang.String) 

    /**
     * Method setMinimumCheckSets the value of field
     * 'minimumCheck'.
     * 
     * @param minimumCheck the value of field 'minimumCheck'.
     */
    public void setMinimumCheck(java.math.BigDecimal minimumCheck)
    {
        this._minimumCheck = minimumCheck;
        
        super.setVoChanged(true);
    } //-- void setMinimumCheck(java.math.BigDecimal) 

    /**
     * Method setOverrideStatusSets the value of field
     * 'overrideStatus'.
     * 
     * @param overrideStatus the value of field 'overrideStatus'.
     */
    public void setOverrideStatus(java.lang.String overrideStatus)
    {
        this._overrideStatus = overrideStatus;
        
        super.setVoChanged(true);
    } //-- void setOverrideStatus(java.lang.String) 

    /**
     * Method setPaymentModeCTSets the value of field
     * 'paymentModeCT'.
     * 
     * @param paymentModeCT the value of field 'paymentModeCT'.
     */
    public void setPaymentModeCT(java.lang.String paymentModeCT)
    {
        this._paymentModeCT = paymentModeCT;
        
        super.setVoChanged(true);
    } //-- void setPaymentModeCT(java.lang.String) 

    /**
     * Method setPreferencePKSets the value of field
     * 'preferencePK'.
     * 
     * @param preferencePK the value of field 'preferencePK'.
     */
    public void setPreferencePK(long preferencePK)
    {
        this._preferencePK = preferencePK;
        
        super.setVoChanged(true);
        this._has_preferencePK = true;
    } //-- void setPreferencePK(long) 

    /**
     * Method setPreferenceTypeCTSets the value of field
     * 'preferenceTypeCT'.
     * 
     * @param preferenceTypeCT the value of field 'preferenceTypeCT'
     */
    public void setPreferenceTypeCT(java.lang.String preferenceTypeCT)
    {
        this._preferenceTypeCT = preferenceTypeCT;
        
        super.setVoChanged(true);
    } //-- void setPreferenceTypeCT(java.lang.String) 

    /**
     * Method setPrintAsSets the value of field 'printAs'.
     * 
     * @param printAs the value of field 'printAs'.
     */
    public void setPrintAs(java.lang.String printAs)
    {
        this._printAs = printAs;
        
        super.setVoChanged(true);
    } //-- void setPrintAs(java.lang.String) 

    /**
     * Method setPrintAs2Sets the value of field 'printAs2'.
     * 
     * @param printAs2 the value of field 'printAs2'.
     */
    public void setPrintAs2(java.lang.String printAs2)
    {
        this._printAs2 = printAs2;
        
        super.setVoChanged(true);
    } //-- void setPrintAs2(java.lang.String) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.PreferenceVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.PreferenceVO) Unmarshaller.unmarshal(edit.common.vo.PreferenceVO.class, reader);
    } //-- edit.common.vo.PreferenceVO unmarshal(java.io.Reader) 

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
