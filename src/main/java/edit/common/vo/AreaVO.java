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
 * Class AreaVO.
 * 
 * @version $Revision$ $Date$
 */
public class AreaVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _areaPK
     */
    private long _areaPK;

    /**
     * keeps track of state for field: _areaPK
     */
    private boolean _has_areaPK;

    /**
     * Field _payoutLeadDaysCheck
     */
    private int _payoutLeadDaysCheck;

    /**
     * keeps track of state for field: _payoutLeadDaysCheck
     */
    private boolean _has_payoutLeadDaysCheck;

    /**
     * Field _payoutLeadDaysEFT
     */
    private int _payoutLeadDaysEFT;

    /**
     * keeps track of state for field: _payoutLeadDaysEFT
     */
    private boolean _has_payoutLeadDaysEFT;

    /**
     * Field _freeLookDaysNB
     */
    private int _freeLookDaysNB;

    /**
     * keeps track of state for field: _freeLookDaysNB
     */
    private boolean _has_freeLookDaysNB;

    /**
     * Field _statementModeCT
     */
    private java.lang.String _statementModeCT;

    /**
     * Field _lookBackDays
     */
    private int _lookBackDays;

    /**
     * keeps track of state for field: _lookBackDays
     */
    private boolean _has_lookBackDays;

    /**
     * Field _freeLookAgeNB
     */
    private int _freeLookAgeNB;

    /**
     * keeps track of state for field: _freeLookAgeNB
     */
    private boolean _has_freeLookAgeNB;

    /**
     * Field _freeLookAgeBasedNB
     */
    private int _freeLookAgeBasedNB;

    /**
     * keeps track of state for field: _freeLookAgeBasedNB
     */
    private boolean _has_freeLookAgeBasedNB;

    /**
     * Field _freeLookDaysInternal
     */
    private int _freeLookDaysInternal;

    /**
     * keeps track of state for field: _freeLookDaysInternal
     */
    private boolean _has_freeLookDaysInternal;

    /**
     * Field _freeLookAgeInternal
     */
    private int _freeLookAgeInternal;

    /**
     * keeps track of state for field: _freeLookAgeInternal
     */
    private boolean _has_freeLookAgeInternal;

    /**
     * Field _freeLookAgeBasedInternal
     */
    private int _freeLookAgeBasedInternal;

    /**
     * keeps track of state for field: _freeLookAgeBasedInternal
     */
    private boolean _has_freeLookAgeBasedInternal;

    /**
     * Field _freeLookDaysExternal
     */
    private int _freeLookDaysExternal;

    /**
     * keeps track of state for field: _freeLookDaysExternal
     */
    private boolean _has_freeLookDaysExternal;

    /**
     * Field _freeLookAgeExternal
     */
    private int _freeLookAgeExternal;

    /**
     * keeps track of state for field: _freeLookAgeExternal
     */
    private boolean _has_freeLookAgeExternal;

    /**
     * Field _freeLookAgeBasedExternal
     */
    private int _freeLookAgeBasedExternal;

    /**
     * keeps track of state for field: _freeLookAgeBasedExternal
     */
    private boolean _has_freeLookAgeBasedExternal;

    /**
     * Field _areaCT
     */
    private java.lang.String _areaCT;

    /**
     * Field _freeLookTypeCT
     */
    private java.lang.String _freeLookTypeCT;

    /**
     * Field _overrideStatus
     */
    private java.lang.String _overrideStatus;

    /**
     * Field _licenseSolicitGuideline
     */
    private int _licenseSolicitGuideline;

    /**
     * keeps track of state for field: _licenseSolicitGuideline
     */
    private boolean _has_licenseSolicitGuideline;

    /**
     * Field _totalPremPercentAllowed
     */
    private java.math.BigDecimal _totalPremPercentAllowed;

    /**
     * Field _EFTBillingLeadDays
     */
    private int _EFTBillingLeadDays;

    /**
     * keeps track of state for field: _EFTBillingLeadDays
     */
    private boolean _has_EFTBillingLeadDays;

    /**
     * Field _creditCardBillingLeadDays
     */
    private int _creditCardBillingLeadDays;

    /**
     * keeps track of state for field: _creditCardBillingLeadDays
     */
    private boolean _has_creditCardBillingLeadDays;

    /**
     * Field _directBillBillingLeadDays
     */
    private int _directBillBillingLeadDays;

    /**
     * keeps track of state for field: _directBillBillingLeadDays
     */
    private boolean _has_directBillBillingLeadDays;

    /**
     * Field _lapsePendGraceDays
     */
    private int _lapsePendGraceDays;

    /**
     * keeps track of state for field: _lapsePendGraceDays
     */
    private boolean _has_lapsePendGraceDays;

    /**
     * Field _lapseGraceDays
     */
    private int _lapseGraceDays;

    /**
     * keeps track of state for field: _lapseGraceDays
     */
    private boolean _has_lapseGraceDays;

    /**
     * Field _filteredAreaVOList
     */
    private java.util.Vector _filteredAreaVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public AreaVO() {
        super();
        _filteredAreaVOList = new Vector();
    } //-- edit.common.vo.AreaVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addFilteredAreaVO
     * 
     * @param vFilteredAreaVO
     */
    public void addFilteredAreaVO(edit.common.vo.FilteredAreaVO vFilteredAreaVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredAreaVO.setParentVO(this.getClass(), this);
        _filteredAreaVOList.addElement(vFilteredAreaVO);
    } //-- void addFilteredAreaVO(edit.common.vo.FilteredAreaVO) 

    /**
     * Method addFilteredAreaVO
     * 
     * @param index
     * @param vFilteredAreaVO
     */
    public void addFilteredAreaVO(int index, edit.common.vo.FilteredAreaVO vFilteredAreaVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredAreaVO.setParentVO(this.getClass(), this);
        _filteredAreaVOList.insertElementAt(vFilteredAreaVO, index);
    } //-- void addFilteredAreaVO(int, edit.common.vo.FilteredAreaVO) 

    /**
     * Method enumerateFilteredAreaVO
     */
    public java.util.Enumeration enumerateFilteredAreaVO()
    {
        return _filteredAreaVOList.elements();
    } //-- java.util.Enumeration enumerateFilteredAreaVO() 

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
        
        if (obj instanceof AreaVO) {
        
            AreaVO temp = (AreaVO)obj;
            if (this._areaPK != temp._areaPK)
                return false;
            if (this._has_areaPK != temp._has_areaPK)
                return false;
            if (this._payoutLeadDaysCheck != temp._payoutLeadDaysCheck)
                return false;
            if (this._has_payoutLeadDaysCheck != temp._has_payoutLeadDaysCheck)
                return false;
            if (this._payoutLeadDaysEFT != temp._payoutLeadDaysEFT)
                return false;
            if (this._has_payoutLeadDaysEFT != temp._has_payoutLeadDaysEFT)
                return false;
            if (this._freeLookDaysNB != temp._freeLookDaysNB)
                return false;
            if (this._has_freeLookDaysNB != temp._has_freeLookDaysNB)
                return false;
            if (this._statementModeCT != null) {
                if (temp._statementModeCT == null) return false;
                else if (!(this._statementModeCT.equals(temp._statementModeCT))) 
                    return false;
            }
            else if (temp._statementModeCT != null)
                return false;
            if (this._lookBackDays != temp._lookBackDays)
                return false;
            if (this._has_lookBackDays != temp._has_lookBackDays)
                return false;
            if (this._freeLookAgeNB != temp._freeLookAgeNB)
                return false;
            if (this._has_freeLookAgeNB != temp._has_freeLookAgeNB)
                return false;
            if (this._freeLookAgeBasedNB != temp._freeLookAgeBasedNB)
                return false;
            if (this._has_freeLookAgeBasedNB != temp._has_freeLookAgeBasedNB)
                return false;
            if (this._freeLookDaysInternal != temp._freeLookDaysInternal)
                return false;
            if (this._has_freeLookDaysInternal != temp._has_freeLookDaysInternal)
                return false;
            if (this._freeLookAgeInternal != temp._freeLookAgeInternal)
                return false;
            if (this._has_freeLookAgeInternal != temp._has_freeLookAgeInternal)
                return false;
            if (this._freeLookAgeBasedInternal != temp._freeLookAgeBasedInternal)
                return false;
            if (this._has_freeLookAgeBasedInternal != temp._has_freeLookAgeBasedInternal)
                return false;
            if (this._freeLookDaysExternal != temp._freeLookDaysExternal)
                return false;
            if (this._has_freeLookDaysExternal != temp._has_freeLookDaysExternal)
                return false;
            if (this._freeLookAgeExternal != temp._freeLookAgeExternal)
                return false;
            if (this._has_freeLookAgeExternal != temp._has_freeLookAgeExternal)
                return false;
            if (this._freeLookAgeBasedExternal != temp._freeLookAgeBasedExternal)
                return false;
            if (this._has_freeLookAgeBasedExternal != temp._has_freeLookAgeBasedExternal)
                return false;
            if (this._areaCT != null) {
                if (temp._areaCT == null) return false;
                else if (!(this._areaCT.equals(temp._areaCT))) 
                    return false;
            }
            else if (temp._areaCT != null)
                return false;
            if (this._freeLookTypeCT != null) {
                if (temp._freeLookTypeCT == null) return false;
                else if (!(this._freeLookTypeCT.equals(temp._freeLookTypeCT))) 
                    return false;
            }
            else if (temp._freeLookTypeCT != null)
                return false;
            if (this._overrideStatus != null) {
                if (temp._overrideStatus == null) return false;
                else if (!(this._overrideStatus.equals(temp._overrideStatus))) 
                    return false;
            }
            else if (temp._overrideStatus != null)
                return false;
            if (this._licenseSolicitGuideline != temp._licenseSolicitGuideline)
                return false;
            if (this._has_licenseSolicitGuideline != temp._has_licenseSolicitGuideline)
                return false;
            if (this._totalPremPercentAllowed != null) {
                if (temp._totalPremPercentAllowed == null) return false;
                else if (!(this._totalPremPercentAllowed.equals(temp._totalPremPercentAllowed))) 
                    return false;
            }
            else if (temp._totalPremPercentAllowed != null)
                return false;
            if (this._EFTBillingLeadDays != temp._EFTBillingLeadDays)
                return false;
            if (this._has_EFTBillingLeadDays != temp._has_EFTBillingLeadDays)
                return false;
            if (this._creditCardBillingLeadDays != temp._creditCardBillingLeadDays)
                return false;
            if (this._has_creditCardBillingLeadDays != temp._has_creditCardBillingLeadDays)
                return false;
            if (this._directBillBillingLeadDays != temp._directBillBillingLeadDays)
                return false;
            if (this._has_directBillBillingLeadDays != temp._has_directBillBillingLeadDays)
                return false;
            if (this._lapsePendGraceDays != temp._lapsePendGraceDays)
                return false;
            if (this._has_lapsePendGraceDays != temp._has_lapsePendGraceDays)
                return false;
            if (this._lapseGraceDays != temp._lapseGraceDays)
                return false;
            if (this._has_lapseGraceDays != temp._has_lapseGraceDays)
                return false;
            if (this._filteredAreaVOList != null) {
                if (temp._filteredAreaVOList == null) return false;
                else if (!(this._filteredAreaVOList.equals(temp._filteredAreaVOList))) 
                    return false;
            }
            else if (temp._filteredAreaVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAreaCTReturns the value of field 'areaCT'.
     * 
     * @return the value of field 'areaCT'.
     */
    public java.lang.String getAreaCT()
    {
        return this._areaCT;
    } //-- java.lang.String getAreaCT() 

    /**
     * Method getAreaPKReturns the value of field 'areaPK'.
     * 
     * @return the value of field 'areaPK'.
     */
    public long getAreaPK()
    {
        return this._areaPK;
    } //-- long getAreaPK() 

    /**
     * Method getCreditCardBillingLeadDaysReturns the value of
     * field 'creditCardBillingLeadDays'.
     * 
     * @return the value of field 'creditCardBillingLeadDays'.
     */
    public int getCreditCardBillingLeadDays()
    {
        return this._creditCardBillingLeadDays;
    } //-- int getCreditCardBillingLeadDays() 

    /**
     * Method getDirectBillBillingLeadDaysReturns the value of
     * field 'directBillBillingLeadDays'.
     * 
     * @return the value of field 'directBillBillingLeadDays'.
     */
    public int getDirectBillBillingLeadDays()
    {
        return this._directBillBillingLeadDays;
    } //-- int getDirectBillBillingLeadDays() 

    /**
     * Method getEFTBillingLeadDaysReturns the value of field
     * 'EFTBillingLeadDays'.
     * 
     * @return the value of field 'EFTBillingLeadDays'.
     */
    public int getEFTBillingLeadDays()
    {
        return this._EFTBillingLeadDays;
    } //-- int getEFTBillingLeadDays() 

    /**
     * Method getFilteredAreaVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredAreaVO getFilteredAreaVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredAreaVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.FilteredAreaVO) _filteredAreaVOList.elementAt(index);
    } //-- edit.common.vo.FilteredAreaVO getFilteredAreaVO(int) 

    /**
     * Method getFilteredAreaVO
     */
    public edit.common.vo.FilteredAreaVO[] getFilteredAreaVO()
    {
        int size = _filteredAreaVOList.size();
        edit.common.vo.FilteredAreaVO[] mArray = new edit.common.vo.FilteredAreaVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.FilteredAreaVO) _filteredAreaVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.FilteredAreaVO[] getFilteredAreaVO() 

    /**
     * Method getFilteredAreaVOCount
     */
    public int getFilteredAreaVOCount()
    {
        return _filteredAreaVOList.size();
    } //-- int getFilteredAreaVOCount() 

    /**
     * Method getFreeLookAgeBasedExternalReturns the value of field
     * 'freeLookAgeBasedExternal'.
     * 
     * @return the value of field 'freeLookAgeBasedExternal'.
     */
    public int getFreeLookAgeBasedExternal()
    {
        return this._freeLookAgeBasedExternal;
    } //-- int getFreeLookAgeBasedExternal() 

    /**
     * Method getFreeLookAgeBasedInternalReturns the value of field
     * 'freeLookAgeBasedInternal'.
     * 
     * @return the value of field 'freeLookAgeBasedInternal'.
     */
    public int getFreeLookAgeBasedInternal()
    {
        return this._freeLookAgeBasedInternal;
    } //-- int getFreeLookAgeBasedInternal() 

    /**
     * Method getFreeLookAgeBasedNBReturns the value of field
     * 'freeLookAgeBasedNB'.
     * 
     * @return the value of field 'freeLookAgeBasedNB'.
     */
    public int getFreeLookAgeBasedNB()
    {
        return this._freeLookAgeBasedNB;
    } //-- int getFreeLookAgeBasedNB() 

    /**
     * Method getFreeLookAgeExternalReturns the value of field
     * 'freeLookAgeExternal'.
     * 
     * @return the value of field 'freeLookAgeExternal'.
     */
    public int getFreeLookAgeExternal()
    {
        return this._freeLookAgeExternal;
    } //-- int getFreeLookAgeExternal() 

    /**
     * Method getFreeLookAgeInternalReturns the value of field
     * 'freeLookAgeInternal'.
     * 
     * @return the value of field 'freeLookAgeInternal'.
     */
    public int getFreeLookAgeInternal()
    {
        return this._freeLookAgeInternal;
    } //-- int getFreeLookAgeInternal() 

    /**
     * Method getFreeLookAgeNBReturns the value of field
     * 'freeLookAgeNB'.
     * 
     * @return the value of field 'freeLookAgeNB'.
     */
    public int getFreeLookAgeNB()
    {
        return this._freeLookAgeNB;
    } //-- int getFreeLookAgeNB() 

    /**
     * Method getFreeLookDaysExternalReturns the value of field
     * 'freeLookDaysExternal'.
     * 
     * @return the value of field 'freeLookDaysExternal'.
     */
    public int getFreeLookDaysExternal()
    {
        return this._freeLookDaysExternal;
    } //-- int getFreeLookDaysExternal() 

    /**
     * Method getFreeLookDaysInternalReturns the value of field
     * 'freeLookDaysInternal'.
     * 
     * @return the value of field 'freeLookDaysInternal'.
     */
    public int getFreeLookDaysInternal()
    {
        return this._freeLookDaysInternal;
    } //-- int getFreeLookDaysInternal() 

    /**
     * Method getFreeLookDaysNBReturns the value of field
     * 'freeLookDaysNB'.
     * 
     * @return the value of field 'freeLookDaysNB'.
     */
    public int getFreeLookDaysNB()
    {
        return this._freeLookDaysNB;
    } //-- int getFreeLookDaysNB() 

    /**
     * Method getFreeLookTypeCTReturns the value of field
     * 'freeLookTypeCT'.
     * 
     * @return the value of field 'freeLookTypeCT'.
     */
    public java.lang.String getFreeLookTypeCT()
    {
        return this._freeLookTypeCT;
    } //-- java.lang.String getFreeLookTypeCT() 

    /**
     * Method getLapseGraceDaysReturns the value of field
     * 'lapseGraceDays'.
     * 
     * @return the value of field 'lapseGraceDays'.
     */
    public int getLapseGraceDays()
    {
        return this._lapseGraceDays;
    } //-- int getLapseGraceDays() 

    /**
     * Method getLapsePendGraceDaysReturns the value of field
     * 'lapsePendGraceDays'.
     * 
     * @return the value of field 'lapsePendGraceDays'.
     */
    public int getLapsePendGraceDays()
    {
        return this._lapsePendGraceDays;
    } //-- int getLapsePendGraceDays() 

    /**
     * Method getLicenseSolicitGuidelineReturns the value of field
     * 'licenseSolicitGuideline'.
     * 
     * @return the value of field 'licenseSolicitGuideline'.
     */
    public int getLicenseSolicitGuideline()
    {
        return this._licenseSolicitGuideline;
    } //-- int getLicenseSolicitGuideline() 

    /**
     * Method getLookBackDaysReturns the value of field
     * 'lookBackDays'.
     * 
     * @return the value of field 'lookBackDays'.
     */
    public int getLookBackDays()
    {
        return this._lookBackDays;
    } //-- int getLookBackDays() 

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
     * Method getPayoutLeadDaysCheckReturns the value of field
     * 'payoutLeadDaysCheck'.
     * 
     * @return the value of field 'payoutLeadDaysCheck'.
     */
    public int getPayoutLeadDaysCheck()
    {
        return this._payoutLeadDaysCheck;
    } //-- int getPayoutLeadDaysCheck() 

    /**
     * Method getPayoutLeadDaysEFTReturns the value of field
     * 'payoutLeadDaysEFT'.
     * 
     * @return the value of field 'payoutLeadDaysEFT'.
     */
    public int getPayoutLeadDaysEFT()
    {
        return this._payoutLeadDaysEFT;
    } //-- int getPayoutLeadDaysEFT() 

    /**
     * Method getStatementModeCTReturns the value of field
     * 'statementModeCT'.
     * 
     * @return the value of field 'statementModeCT'.
     */
    public java.lang.String getStatementModeCT()
    {
        return this._statementModeCT;
    } //-- java.lang.String getStatementModeCT() 

    /**
     * Method getTotalPremPercentAllowedReturns the value of field
     * 'totalPremPercentAllowed'.
     * 
     * @return the value of field 'totalPremPercentAllowed'.
     */
    public java.math.BigDecimal getTotalPremPercentAllowed()
    {
        return this._totalPremPercentAllowed;
    } //-- java.math.BigDecimal getTotalPremPercentAllowed() 

    /**
     * Method hasAreaPK
     */
    public boolean hasAreaPK()
    {
        return this._has_areaPK;
    } //-- boolean hasAreaPK() 

    /**
     * Method hasCreditCardBillingLeadDays
     */
    public boolean hasCreditCardBillingLeadDays()
    {
        return this._has_creditCardBillingLeadDays;
    } //-- boolean hasCreditCardBillingLeadDays() 

    /**
     * Method hasDirectBillBillingLeadDays
     */
    public boolean hasDirectBillBillingLeadDays()
    {
        return this._has_directBillBillingLeadDays;
    } //-- boolean hasDirectBillBillingLeadDays() 

    /**
     * Method hasEFTBillingLeadDays
     */
    public boolean hasEFTBillingLeadDays()
    {
        return this._has_EFTBillingLeadDays;
    } //-- boolean hasEFTBillingLeadDays() 

    /**
     * Method hasFreeLookAgeBasedExternal
     */
    public boolean hasFreeLookAgeBasedExternal()
    {
        return this._has_freeLookAgeBasedExternal;
    } //-- boolean hasFreeLookAgeBasedExternal() 

    /**
     * Method hasFreeLookAgeBasedInternal
     */
    public boolean hasFreeLookAgeBasedInternal()
    {
        return this._has_freeLookAgeBasedInternal;
    } //-- boolean hasFreeLookAgeBasedInternal() 

    /**
     * Method hasFreeLookAgeBasedNB
     */
    public boolean hasFreeLookAgeBasedNB()
    {
        return this._has_freeLookAgeBasedNB;
    } //-- boolean hasFreeLookAgeBasedNB() 

    /**
     * Method hasFreeLookAgeExternal
     */
    public boolean hasFreeLookAgeExternal()
    {
        return this._has_freeLookAgeExternal;
    } //-- boolean hasFreeLookAgeExternal() 

    /**
     * Method hasFreeLookAgeInternal
     */
    public boolean hasFreeLookAgeInternal()
    {
        return this._has_freeLookAgeInternal;
    } //-- boolean hasFreeLookAgeInternal() 

    /**
     * Method hasFreeLookAgeNB
     */
    public boolean hasFreeLookAgeNB()
    {
        return this._has_freeLookAgeNB;
    } //-- boolean hasFreeLookAgeNB() 

    /**
     * Method hasFreeLookDaysExternal
     */
    public boolean hasFreeLookDaysExternal()
    {
        return this._has_freeLookDaysExternal;
    } //-- boolean hasFreeLookDaysExternal() 

    /**
     * Method hasFreeLookDaysInternal
     */
    public boolean hasFreeLookDaysInternal()
    {
        return this._has_freeLookDaysInternal;
    } //-- boolean hasFreeLookDaysInternal() 

    /**
     * Method hasFreeLookDaysNB
     */
    public boolean hasFreeLookDaysNB()
    {
        return this._has_freeLookDaysNB;
    } //-- boolean hasFreeLookDaysNB() 

    /**
     * Method hasLapseGraceDays
     */
    public boolean hasLapseGraceDays()
    {
        return this._has_lapseGraceDays;
    } //-- boolean hasLapseGraceDays() 

    /**
     * Method hasLapsePendGraceDays
     */
    public boolean hasLapsePendGraceDays()
    {
        return this._has_lapsePendGraceDays;
    } //-- boolean hasLapsePendGraceDays() 

    /**
     * Method hasLicenseSolicitGuideline
     */
    public boolean hasLicenseSolicitGuideline()
    {
        return this._has_licenseSolicitGuideline;
    } //-- boolean hasLicenseSolicitGuideline() 

    /**
     * Method hasLookBackDays
     */
    public boolean hasLookBackDays()
    {
        return this._has_lookBackDays;
    } //-- boolean hasLookBackDays() 

    /**
     * Method hasPayoutLeadDaysCheck
     */
    public boolean hasPayoutLeadDaysCheck()
    {
        return this._has_payoutLeadDaysCheck;
    } //-- boolean hasPayoutLeadDaysCheck() 

    /**
     * Method hasPayoutLeadDaysEFT
     */
    public boolean hasPayoutLeadDaysEFT()
    {
        return this._has_payoutLeadDaysEFT;
    } //-- boolean hasPayoutLeadDaysEFT() 

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
     * Method removeAllFilteredAreaVO
     */
    public void removeAllFilteredAreaVO()
    {
        _filteredAreaVOList.removeAllElements();
    } //-- void removeAllFilteredAreaVO() 

    /**
     * Method removeFilteredAreaVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredAreaVO removeFilteredAreaVO(int index)
    {
        java.lang.Object obj = _filteredAreaVOList.elementAt(index);
        _filteredAreaVOList.removeElementAt(index);
        return (edit.common.vo.FilteredAreaVO) obj;
    } //-- edit.common.vo.FilteredAreaVO removeFilteredAreaVO(int) 

    /**
     * Method setAreaCTSets the value of field 'areaCT'.
     * 
     * @param areaCT the value of field 'areaCT'.
     */
    public void setAreaCT(java.lang.String areaCT)
    {
        this._areaCT = areaCT;
        
        super.setVoChanged(true);
    } //-- void setAreaCT(java.lang.String) 

    /**
     * Method setAreaPKSets the value of field 'areaPK'.
     * 
     * @param areaPK the value of field 'areaPK'.
     */
    public void setAreaPK(long areaPK)
    {
        this._areaPK = areaPK;
        
        super.setVoChanged(true);
        this._has_areaPK = true;
    } //-- void setAreaPK(long) 

    /**
     * Method setCreditCardBillingLeadDaysSets the value of field
     * 'creditCardBillingLeadDays'.
     * 
     * @param creditCardBillingLeadDays the value of field
     * 'creditCardBillingLeadDays'.
     */
    public void setCreditCardBillingLeadDays(int creditCardBillingLeadDays)
    {
        this._creditCardBillingLeadDays = creditCardBillingLeadDays;
        
        super.setVoChanged(true);
        this._has_creditCardBillingLeadDays = true;
    } //-- void setCreditCardBillingLeadDays(int) 

    /**
     * Method setDirectBillBillingLeadDaysSets the value of field
     * 'directBillBillingLeadDays'.
     * 
     * @param directBillBillingLeadDays the value of field
     * 'directBillBillingLeadDays'.
     */
    public void setDirectBillBillingLeadDays(int directBillBillingLeadDays)
    {
        this._directBillBillingLeadDays = directBillBillingLeadDays;
        
        super.setVoChanged(true);
        this._has_directBillBillingLeadDays = true;
    } //-- void setDirectBillBillingLeadDays(int) 

    /**
     * Method setEFTBillingLeadDaysSets the value of field
     * 'EFTBillingLeadDays'.
     * 
     * @param EFTBillingLeadDays the value of field
     * 'EFTBillingLeadDays'.
     */
    public void setEFTBillingLeadDays(int EFTBillingLeadDays)
    {
        this._EFTBillingLeadDays = EFTBillingLeadDays;
        
        super.setVoChanged(true);
        this._has_EFTBillingLeadDays = true;
    } //-- void setEFTBillingLeadDays(int) 

    /**
     * Method setFilteredAreaVO
     * 
     * @param index
     * @param vFilteredAreaVO
     */
    public void setFilteredAreaVO(int index, edit.common.vo.FilteredAreaVO vFilteredAreaVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredAreaVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vFilteredAreaVO.setParentVO(this.getClass(), this);
        _filteredAreaVOList.setElementAt(vFilteredAreaVO, index);
    } //-- void setFilteredAreaVO(int, edit.common.vo.FilteredAreaVO) 

    /**
     * Method setFilteredAreaVO
     * 
     * @param filteredAreaVOArray
     */
    public void setFilteredAreaVO(edit.common.vo.FilteredAreaVO[] filteredAreaVOArray)
    {
        //-- copy array
        _filteredAreaVOList.removeAllElements();
        for (int i = 0; i < filteredAreaVOArray.length; i++) {
            filteredAreaVOArray[i].setParentVO(this.getClass(), this);
            _filteredAreaVOList.addElement(filteredAreaVOArray[i]);
        }
    } //-- void setFilteredAreaVO(edit.common.vo.FilteredAreaVO) 

    /**
     * Method setFreeLookAgeBasedExternalSets the value of field
     * 'freeLookAgeBasedExternal'.
     * 
     * @param freeLookAgeBasedExternal the value of field
     * 'freeLookAgeBasedExternal'.
     */
    public void setFreeLookAgeBasedExternal(int freeLookAgeBasedExternal)
    {
        this._freeLookAgeBasedExternal = freeLookAgeBasedExternal;
        
        super.setVoChanged(true);
        this._has_freeLookAgeBasedExternal = true;
    } //-- void setFreeLookAgeBasedExternal(int) 

    /**
     * Method setFreeLookAgeBasedInternalSets the value of field
     * 'freeLookAgeBasedInternal'.
     * 
     * @param freeLookAgeBasedInternal the value of field
     * 'freeLookAgeBasedInternal'.
     */
    public void setFreeLookAgeBasedInternal(int freeLookAgeBasedInternal)
    {
        this._freeLookAgeBasedInternal = freeLookAgeBasedInternal;
        
        super.setVoChanged(true);
        this._has_freeLookAgeBasedInternal = true;
    } //-- void setFreeLookAgeBasedInternal(int) 

    /**
     * Method setFreeLookAgeBasedNBSets the value of field
     * 'freeLookAgeBasedNB'.
     * 
     * @param freeLookAgeBasedNB the value of field
     * 'freeLookAgeBasedNB'.
     */
    public void setFreeLookAgeBasedNB(int freeLookAgeBasedNB)
    {
        this._freeLookAgeBasedNB = freeLookAgeBasedNB;
        
        super.setVoChanged(true);
        this._has_freeLookAgeBasedNB = true;
    } //-- void setFreeLookAgeBasedNB(int) 

    /**
     * Method setFreeLookAgeExternalSets the value of field
     * 'freeLookAgeExternal'.
     * 
     * @param freeLookAgeExternal the value of field
     * 'freeLookAgeExternal'.
     */
    public void setFreeLookAgeExternal(int freeLookAgeExternal)
    {
        this._freeLookAgeExternal = freeLookAgeExternal;
        
        super.setVoChanged(true);
        this._has_freeLookAgeExternal = true;
    } //-- void setFreeLookAgeExternal(int) 

    /**
     * Method setFreeLookAgeInternalSets the value of field
     * 'freeLookAgeInternal'.
     * 
     * @param freeLookAgeInternal the value of field
     * 'freeLookAgeInternal'.
     */
    public void setFreeLookAgeInternal(int freeLookAgeInternal)
    {
        this._freeLookAgeInternal = freeLookAgeInternal;
        
        super.setVoChanged(true);
        this._has_freeLookAgeInternal = true;
    } //-- void setFreeLookAgeInternal(int) 

    /**
     * Method setFreeLookAgeNBSets the value of field
     * 'freeLookAgeNB'.
     * 
     * @param freeLookAgeNB the value of field 'freeLookAgeNB'.
     */
    public void setFreeLookAgeNB(int freeLookAgeNB)
    {
        this._freeLookAgeNB = freeLookAgeNB;
        
        super.setVoChanged(true);
        this._has_freeLookAgeNB = true;
    } //-- void setFreeLookAgeNB(int) 

    /**
     * Method setFreeLookDaysExternalSets the value of field
     * 'freeLookDaysExternal'.
     * 
     * @param freeLookDaysExternal the value of field
     * 'freeLookDaysExternal'.
     */
    public void setFreeLookDaysExternal(int freeLookDaysExternal)
    {
        this._freeLookDaysExternal = freeLookDaysExternal;
        
        super.setVoChanged(true);
        this._has_freeLookDaysExternal = true;
    } //-- void setFreeLookDaysExternal(int) 

    /**
     * Method setFreeLookDaysInternalSets the value of field
     * 'freeLookDaysInternal'.
     * 
     * @param freeLookDaysInternal the value of field
     * 'freeLookDaysInternal'.
     */
    public void setFreeLookDaysInternal(int freeLookDaysInternal)
    {
        this._freeLookDaysInternal = freeLookDaysInternal;
        
        super.setVoChanged(true);
        this._has_freeLookDaysInternal = true;
    } //-- void setFreeLookDaysInternal(int) 

    /**
     * Method setFreeLookDaysNBSets the value of field
     * 'freeLookDaysNB'.
     * 
     * @param freeLookDaysNB the value of field 'freeLookDaysNB'.
     */
    public void setFreeLookDaysNB(int freeLookDaysNB)
    {
        this._freeLookDaysNB = freeLookDaysNB;
        
        super.setVoChanged(true);
        this._has_freeLookDaysNB = true;
    } //-- void setFreeLookDaysNB(int) 

    /**
     * Method setFreeLookTypeCTSets the value of field
     * 'freeLookTypeCT'.
     * 
     * @param freeLookTypeCT the value of field 'freeLookTypeCT'.
     */
    public void setFreeLookTypeCT(java.lang.String freeLookTypeCT)
    {
        this._freeLookTypeCT = freeLookTypeCT;
        
        super.setVoChanged(true);
    } //-- void setFreeLookTypeCT(java.lang.String) 

    /**
     * Method setLapseGraceDaysSets the value of field
     * 'lapseGraceDays'.
     * 
     * @param lapseGraceDays the value of field 'lapseGraceDays'.
     */
    public void setLapseGraceDays(int lapseGraceDays)
    {
        this._lapseGraceDays = lapseGraceDays;
        
        super.setVoChanged(true);
        this._has_lapseGraceDays = true;
    } //-- void setLapseGraceDays(int) 

    /**
     * Method setLapsePendGraceDaysSets the value of field
     * 'lapsePendGraceDays'.
     * 
     * @param lapsePendGraceDays the value of field
     * 'lapsePendGraceDays'.
     */
    public void setLapsePendGraceDays(int lapsePendGraceDays)
    {
        this._lapsePendGraceDays = lapsePendGraceDays;
        
        super.setVoChanged(true);
        this._has_lapsePendGraceDays = true;
    } //-- void setLapsePendGraceDays(int) 

    /**
     * Method setLicenseSolicitGuidelineSets the value of field
     * 'licenseSolicitGuideline'.
     * 
     * @param licenseSolicitGuideline the value of field
     * 'licenseSolicitGuideline'.
     */
    public void setLicenseSolicitGuideline(int licenseSolicitGuideline)
    {
        this._licenseSolicitGuideline = licenseSolicitGuideline;
        
        super.setVoChanged(true);
        this._has_licenseSolicitGuideline = true;
    } //-- void setLicenseSolicitGuideline(int) 

    /**
     * Method setLookBackDaysSets the value of field
     * 'lookBackDays'.
     * 
     * @param lookBackDays the value of field 'lookBackDays'.
     */
    public void setLookBackDays(int lookBackDays)
    {
        this._lookBackDays = lookBackDays;
        
        super.setVoChanged(true);
        this._has_lookBackDays = true;
    } //-- void setLookBackDays(int) 

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
     * Method setPayoutLeadDaysCheckSets the value of field
     * 'payoutLeadDaysCheck'.
     * 
     * @param payoutLeadDaysCheck the value of field
     * 'payoutLeadDaysCheck'.
     */
    public void setPayoutLeadDaysCheck(int payoutLeadDaysCheck)
    {
        this._payoutLeadDaysCheck = payoutLeadDaysCheck;
        
        super.setVoChanged(true);
        this._has_payoutLeadDaysCheck = true;
    } //-- void setPayoutLeadDaysCheck(int) 

    /**
     * Method setPayoutLeadDaysEFTSets the value of field
     * 'payoutLeadDaysEFT'.
     * 
     * @param payoutLeadDaysEFT the value of field
     * 'payoutLeadDaysEFT'.
     */
    public void setPayoutLeadDaysEFT(int payoutLeadDaysEFT)
    {
        this._payoutLeadDaysEFT = payoutLeadDaysEFT;
        
        super.setVoChanged(true);
        this._has_payoutLeadDaysEFT = true;
    } //-- void setPayoutLeadDaysEFT(int) 

    /**
     * Method setStatementModeCTSets the value of field
     * 'statementModeCT'.
     * 
     * @param statementModeCT the value of field 'statementModeCT'.
     */
    public void setStatementModeCT(java.lang.String statementModeCT)
    {
        this._statementModeCT = statementModeCT;
        
        super.setVoChanged(true);
    } //-- void setStatementModeCT(java.lang.String) 

    /**
     * Method setTotalPremPercentAllowedSets the value of field
     * 'totalPremPercentAllowed'.
     * 
     * @param totalPremPercentAllowed the value of field
     * 'totalPremPercentAllowed'.
     */
    public void setTotalPremPercentAllowed(java.math.BigDecimal totalPremPercentAllowed)
    {
        this._totalPremPercentAllowed = totalPremPercentAllowed;
        
        super.setVoChanged(true);
    } //-- void setTotalPremPercentAllowed(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AreaVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AreaVO) Unmarshaller.unmarshal(edit.common.vo.AreaVO.class, reader);
    } //-- edit.common.vo.AreaVO unmarshal(java.io.Reader) 

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
