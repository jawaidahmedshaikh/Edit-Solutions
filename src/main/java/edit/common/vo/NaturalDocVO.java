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
 * Class NaturalDocVO.
 * 
 * @version $Revision$ $Date$
 */
public class NaturalDocVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _naturalDocPK
     */
    private long _naturalDocPK;

    /**
     * keeps track of state for field: _naturalDocPK
     */
    private boolean _has_naturalDocPK;

    /**
     * Comment describing your root element
     */
    private edit.common.vo.BaseSegmentVO _baseSegmentVO;

    /**
     * Comment describing your root element
     */
    private java.util.Vector _riderSegmentVOList;

    /**
     * Comment describing your root element
     */
    private java.util.Vector _supplementalSegmentVOList;

    /**
     * Field _groupSetupVOList
     */
    private java.util.Vector _groupSetupVOList;

    /**
     * Field _investmentAllocationOverrideVOList
     */
    private java.util.Vector _investmentAllocationOverrideVOList;

    /**
     * Field _equityIndexHedgeDetailVOList
     */
    private java.util.Vector _equityIndexHedgeDetailVOList;

    /**
     * Schema to be used to pass the overdue charges remaining
     */
    private java.util.Vector _overdueChargeRemainingVOList;

    /**
     * Field _investmentArrayVOList
     */
    private java.util.Vector _investmentArrayVOList;

    /**
     * Field _suspenseVOList
     */
    private java.util.Vector _suspenseVOList;

    /**
     * Comment describing your root element
     */
    private java.util.Vector _deathInformationVOList;

    /**
     * Comment describing your root element
     */
    private java.util.Vector _tamraRetestVOList;

    /**
     * Field _groupRate
     */
    private java.math.BigDecimal _groupRate;

    /**
     * Field _groupNoParticipants
     */
    private int _groupNoParticipants;

    /**
     * keeps track of state for field: _groupNoParticipants
     */
    private boolean _has_groupNoParticipants;

    /**
     * Field _premiumToDate
     */
    private java.math.BigDecimal _premiumToDate;

    /**
     * Field _premiumYearToDate
     */
    private java.math.BigDecimal _premiumYearToDate;

    /**
     * Field _premiumCalYearToDate
     */
    private java.math.BigDecimal _premiumCalYearToDate;

    /**
     * Field _netWithdrawalsYearToDate
     */
    private java.math.BigDecimal _netWithdrawalsYearToDate;

    /**
     * Field _netWithdrawalsToDate
     */
    private java.math.BigDecimal _netWithdrawalsToDate;

    /**
     * Field _premiumSinceLast7PayDate
     */
    private java.math.BigDecimal _premiumSinceLast7PayDate;

    /**
     * Field _withdrawalsSinceLast7PayDate
     */
    private java.math.BigDecimal _withdrawalsSinceLast7PayDate;

    /**
     * Field _cumInitialPremium
     */
    private java.math.BigDecimal _cumInitialPremium;

    /**
     * Field _cum1035Premium
     */
    private java.math.BigDecimal _cum1035Premium;

    /**
     * Field _numberWithdrawalsPolYearToDate
     */
    private int _numberWithdrawalsPolYearToDate;

    /**
     * keeps track of state for field:
     * _numberWithdrawalsPolYearToDate
     */
    private boolean _has_numberWithdrawalsPolYearToDate;

    /**
     * Field _numberTransfersPolYearToDate
     */
    private int _numberTransfersPolYearToDate;

    /**
     * keeps track of state for field: _numberTransfersPolYearToDate
     */
    private boolean _has_numberTransfersPolYearToDate;

    /**
     * Field _withdrawalsTaxYTD
     */
    private java.math.BigDecimal _withdrawalsTaxYTD;

    /**
     * Field _RMDTaxYTD
     */
    private java.math.BigDecimal _RMDTaxYTD;

    /**
     * Field _priorCYAccumulatedValue
     */
    private java.math.BigDecimal _priorCYAccumulatedValue;

    /**
     * Field _ownersPremiumTaxState
     */
    private java.lang.String _ownersPremiumTaxState;

    /**
     * Field _insuredResidenceState
     */
    private java.lang.String _insuredResidenceState;

    /**
     * Field _insuredGender
     */
    private java.lang.String _insuredGender;

    /**
     * Field _insuredDateOfBirth
     */
    private java.lang.String _insuredDateOfBirth;

    /**
     * Field _insuredIssueAge
     */
    private int _insuredIssueAge;

    /**
     * keeps track of state for field: _insuredIssueAge
     */
    private boolean _has_insuredIssueAge;

    /**
     * Field _insuredClass
     */
    private java.lang.String _insuredClass;

    /**
     * Field _insuredUnderwritingClass
     */
    private java.lang.String _insuredUnderwritingClass;

    /**
     * Field _taxDateOfBirth
     */
    private java.lang.String _taxDateOfBirth;

    /**
     * Field _freeAmount
     */
    private java.math.BigDecimal _freeAmount;

    /**
     * Field _initCYAccumValue
     */
    private java.math.BigDecimal _initCYAccumValue;

    /**
     * Field _cumPremiumTax
     */
    private java.math.BigDecimal _cumPremiumTax;

    /**
     * Field _cumDacTax
     */
    private java.math.BigDecimal _cumDacTax;

    /**
     * Field _cumSalesLoad
     */
    private java.math.BigDecimal _cumSalesLoad;

    /**
     * Field _cumCoi
     */
    private java.math.BigDecimal _cumCoi;

    /**
     * Field _settledOverdueCoi
     */
    private java.math.BigDecimal _settledOverdueCoi;

    /**
     * Field _secondaryAnnuitantIssueAge
     */
    private int _secondaryAnnuitantIssueAge;

    /**
     * keeps track of state for field: _secondaryAnnuitantIssueAge
     */
    private boolean _has_secondaryAnnuitantIssueAge;

    /**
     * Field _secondaryAnnuitantGender
     */
    private java.lang.String _secondaryAnnuitantGender;

    /**
     * Field _secondaryAnnuitantTableRating
     */
    private java.lang.String _secondaryAnnuitantTableRating;

    /**
     * Field _lastLoanCapAmount
     */
    private java.math.BigDecimal _lastLoanCapAmount;

    /**
     * Field _generalAccountArrayVOList
     */
    private java.util.Vector _generalAccountArrayVOList;

    /**
     * Comment describing your root element
     */
    private java.util.Vector _loanSettlementVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public NaturalDocVO() {
        super();
        _riderSegmentVOList = new Vector();
        _supplementalSegmentVOList = new Vector();
        _groupSetupVOList = new Vector();
        _investmentAllocationOverrideVOList = new Vector();
        _equityIndexHedgeDetailVOList = new Vector();
        _overdueChargeRemainingVOList = new Vector();
        _investmentArrayVOList = new Vector();
        _suspenseVOList = new Vector();
        _deathInformationVOList = new Vector();
        _tamraRetestVOList = new Vector();
        _generalAccountArrayVOList = new Vector();
        _loanSettlementVOList = new Vector();
    } //-- edit.common.vo.NaturalDocVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addDeathInformationVO
     * 
     * @param vDeathInformationVO
     */
    public void addDeathInformationVO(edit.common.vo.DeathInformationVO vDeathInformationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vDeathInformationVO.setParentVO(this.getClass(), this);
        _deathInformationVOList.addElement(vDeathInformationVO);
    } //-- void addDeathInformationVO(edit.common.vo.DeathInformationVO) 

    /**
     * Method addDeathInformationVO
     * 
     * @param index
     * @param vDeathInformationVO
     */
    public void addDeathInformationVO(int index, edit.common.vo.DeathInformationVO vDeathInformationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vDeathInformationVO.setParentVO(this.getClass(), this);
        _deathInformationVOList.insertElementAt(vDeathInformationVO, index);
    } //-- void addDeathInformationVO(int, edit.common.vo.DeathInformationVO) 

    /**
     * Method addEquityIndexHedgeDetailVO
     * 
     * @param vEquityIndexHedgeDetailVO
     */
    public void addEquityIndexHedgeDetailVO(edit.common.vo.EquityIndexHedgeDetailVO vEquityIndexHedgeDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEquityIndexHedgeDetailVO.setParentVO(this.getClass(), this);
        _equityIndexHedgeDetailVOList.addElement(vEquityIndexHedgeDetailVO);
    } //-- void addEquityIndexHedgeDetailVO(edit.common.vo.EquityIndexHedgeDetailVO) 

    /**
     * Method addEquityIndexHedgeDetailVO
     * 
     * @param index
     * @param vEquityIndexHedgeDetailVO
     */
    public void addEquityIndexHedgeDetailVO(int index, edit.common.vo.EquityIndexHedgeDetailVO vEquityIndexHedgeDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEquityIndexHedgeDetailVO.setParentVO(this.getClass(), this);
        _equityIndexHedgeDetailVOList.insertElementAt(vEquityIndexHedgeDetailVO, index);
    } //-- void addEquityIndexHedgeDetailVO(int, edit.common.vo.EquityIndexHedgeDetailVO) 

    /**
     * Method addGeneralAccountArrayVO
     * 
     * @param vGeneralAccountArrayVO
     */
    public void addGeneralAccountArrayVO(edit.common.vo.GeneralAccountArrayVO vGeneralAccountArrayVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vGeneralAccountArrayVO.setParentVO(this.getClass(), this);
        _generalAccountArrayVOList.addElement(vGeneralAccountArrayVO);
    } //-- void addGeneralAccountArrayVO(edit.common.vo.GeneralAccountArrayVO) 

    /**
     * Method addGeneralAccountArrayVO
     * 
     * @param index
     * @param vGeneralAccountArrayVO
     */
    public void addGeneralAccountArrayVO(int index, edit.common.vo.GeneralAccountArrayVO vGeneralAccountArrayVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vGeneralAccountArrayVO.setParentVO(this.getClass(), this);
        _generalAccountArrayVOList.insertElementAt(vGeneralAccountArrayVO, index);
    } //-- void addGeneralAccountArrayVO(int, edit.common.vo.GeneralAccountArrayVO) 

    /**
     * Method addGroupSetupVO
     * 
     * @param vGroupSetupVO
     */
    public void addGroupSetupVO(edit.common.vo.GroupSetupVO vGroupSetupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vGroupSetupVO.setParentVO(this.getClass(), this);
        _groupSetupVOList.addElement(vGroupSetupVO);
    } //-- void addGroupSetupVO(edit.common.vo.GroupSetupVO) 

    /**
     * Method addGroupSetupVO
     * 
     * @param index
     * @param vGroupSetupVO
     */
    public void addGroupSetupVO(int index, edit.common.vo.GroupSetupVO vGroupSetupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vGroupSetupVO.setParentVO(this.getClass(), this);
        _groupSetupVOList.insertElementAt(vGroupSetupVO, index);
    } //-- void addGroupSetupVO(int, edit.common.vo.GroupSetupVO) 

    /**
     * Method addInvestmentAllocationOverrideVO
     * 
     * @param vInvestmentAllocationOverrideVO
     */
    public void addInvestmentAllocationOverrideVO(edit.common.vo.InvestmentAllocationOverrideVO vInvestmentAllocationOverrideVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentAllocationOverrideVO.setParentVO(this.getClass(), this);
        _investmentAllocationOverrideVOList.addElement(vInvestmentAllocationOverrideVO);
    } //-- void addInvestmentAllocationOverrideVO(edit.common.vo.InvestmentAllocationOverrideVO) 

    /**
     * Method addInvestmentAllocationOverrideVO
     * 
     * @param index
     * @param vInvestmentAllocationOverrideVO
     */
    public void addInvestmentAllocationOverrideVO(int index, edit.common.vo.InvestmentAllocationOverrideVO vInvestmentAllocationOverrideVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentAllocationOverrideVO.setParentVO(this.getClass(), this);
        _investmentAllocationOverrideVOList.insertElementAt(vInvestmentAllocationOverrideVO, index);
    } //-- void addInvestmentAllocationOverrideVO(int, edit.common.vo.InvestmentAllocationOverrideVO) 

    /**
     * Method addInvestmentArrayVO
     * 
     * @param vInvestmentArrayVO
     */
    public void addInvestmentArrayVO(edit.common.vo.InvestmentArrayVO vInvestmentArrayVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentArrayVO.setParentVO(this.getClass(), this);
        _investmentArrayVOList.addElement(vInvestmentArrayVO);
    } //-- void addInvestmentArrayVO(edit.common.vo.InvestmentArrayVO) 

    /**
     * Method addInvestmentArrayVO
     * 
     * @param index
     * @param vInvestmentArrayVO
     */
    public void addInvestmentArrayVO(int index, edit.common.vo.InvestmentArrayVO vInvestmentArrayVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentArrayVO.setParentVO(this.getClass(), this);
        _investmentArrayVOList.insertElementAt(vInvestmentArrayVO, index);
    } //-- void addInvestmentArrayVO(int, edit.common.vo.InvestmentArrayVO) 

    /**
     * Method addLoanSettlementVO
     * 
     * @param vLoanSettlementVO
     */
    public void addLoanSettlementVO(edit.common.vo.LoanSettlementVO vLoanSettlementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vLoanSettlementVO.setParentVO(this.getClass(), this);
        _loanSettlementVOList.addElement(vLoanSettlementVO);
    } //-- void addLoanSettlementVO(edit.common.vo.LoanSettlementVO) 

    /**
     * Method addLoanSettlementVO
     * 
     * @param index
     * @param vLoanSettlementVO
     */
    public void addLoanSettlementVO(int index, edit.common.vo.LoanSettlementVO vLoanSettlementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vLoanSettlementVO.setParentVO(this.getClass(), this);
        _loanSettlementVOList.insertElementAt(vLoanSettlementVO, index);
    } //-- void addLoanSettlementVO(int, edit.common.vo.LoanSettlementVO) 

    /**
     * Method addOverdueChargeRemainingVO
     * 
     * @param vOverdueChargeRemainingVO
     */
    public void addOverdueChargeRemainingVO(edit.common.vo.OverdueChargeRemainingVO vOverdueChargeRemainingVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vOverdueChargeRemainingVO.setParentVO(this.getClass(), this);
        _overdueChargeRemainingVOList.addElement(vOverdueChargeRemainingVO);
    } //-- void addOverdueChargeRemainingVO(edit.common.vo.OverdueChargeRemainingVO) 

    /**
     * Method addOverdueChargeRemainingVO
     * 
     * @param index
     * @param vOverdueChargeRemainingVO
     */
    public void addOverdueChargeRemainingVO(int index, edit.common.vo.OverdueChargeRemainingVO vOverdueChargeRemainingVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vOverdueChargeRemainingVO.setParentVO(this.getClass(), this);
        _overdueChargeRemainingVOList.insertElementAt(vOverdueChargeRemainingVO, index);
    } //-- void addOverdueChargeRemainingVO(int, edit.common.vo.OverdueChargeRemainingVO) 

    /**
     * Method addRiderSegmentVO
     * 
     * @param vRiderSegmentVO
     */
    public void addRiderSegmentVO(edit.common.vo.RiderSegmentVO vRiderSegmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vRiderSegmentVO.setParentVO(this.getClass(), this);
        _riderSegmentVOList.addElement(vRiderSegmentVO);
    } //-- void addRiderSegmentVO(edit.common.vo.RiderSegmentVO) 

    /**
     * Method addRiderSegmentVO
     * 
     * @param index
     * @param vRiderSegmentVO
     */
    public void addRiderSegmentVO(int index, edit.common.vo.RiderSegmentVO vRiderSegmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vRiderSegmentVO.setParentVO(this.getClass(), this);
        _riderSegmentVOList.insertElementAt(vRiderSegmentVO, index);
    } //-- void addRiderSegmentVO(int, edit.common.vo.RiderSegmentVO) 

    /**
     * Method addSupplementalSegmentVO
     * 
     * @param vSupplementalSegmentVO
     */
    public void addSupplementalSegmentVO(edit.common.vo.SupplementalSegmentVO vSupplementalSegmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSupplementalSegmentVO.setParentVO(this.getClass(), this);
        _supplementalSegmentVOList.addElement(vSupplementalSegmentVO);
    } //-- void addSupplementalSegmentVO(edit.common.vo.SupplementalSegmentVO) 

    /**
     * Method addSupplementalSegmentVO
     * 
     * @param index
     * @param vSupplementalSegmentVO
     */
    public void addSupplementalSegmentVO(int index, edit.common.vo.SupplementalSegmentVO vSupplementalSegmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSupplementalSegmentVO.setParentVO(this.getClass(), this);
        _supplementalSegmentVOList.insertElementAt(vSupplementalSegmentVO, index);
    } //-- void addSupplementalSegmentVO(int, edit.common.vo.SupplementalSegmentVO) 

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
     * Method addTamraRetestVO
     * 
     * @param vTamraRetestVO
     */
    public void addTamraRetestVO(edit.common.vo.TamraRetestVO vTamraRetestVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vTamraRetestVO.setParentVO(this.getClass(), this);
        _tamraRetestVOList.addElement(vTamraRetestVO);
    } //-- void addTamraRetestVO(edit.common.vo.TamraRetestVO) 

    /**
     * Method addTamraRetestVO
     * 
     * @param index
     * @param vTamraRetestVO
     */
    public void addTamraRetestVO(int index, edit.common.vo.TamraRetestVO vTamraRetestVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vTamraRetestVO.setParentVO(this.getClass(), this);
        _tamraRetestVOList.insertElementAt(vTamraRetestVO, index);
    } //-- void addTamraRetestVO(int, edit.common.vo.TamraRetestVO) 

    /**
     * Method enumerateDeathInformationVO
     */
    public java.util.Enumeration enumerateDeathInformationVO()
    {
        return _deathInformationVOList.elements();
    } //-- java.util.Enumeration enumerateDeathInformationVO() 

    /**
     * Method enumerateEquityIndexHedgeDetailVO
     */
    public java.util.Enumeration enumerateEquityIndexHedgeDetailVO()
    {
        return _equityIndexHedgeDetailVOList.elements();
    } //-- java.util.Enumeration enumerateEquityIndexHedgeDetailVO() 

    /**
     * Method enumerateGeneralAccountArrayVO
     */
    public java.util.Enumeration enumerateGeneralAccountArrayVO()
    {
        return _generalAccountArrayVOList.elements();
    } //-- java.util.Enumeration enumerateGeneralAccountArrayVO() 

    /**
     * Method enumerateGroupSetupVO
     */
    public java.util.Enumeration enumerateGroupSetupVO()
    {
        return _groupSetupVOList.elements();
    } //-- java.util.Enumeration enumerateGroupSetupVO() 

    /**
     * Method enumerateInvestmentAllocationOverrideVO
     */
    public java.util.Enumeration enumerateInvestmentAllocationOverrideVO()
    {
        return _investmentAllocationOverrideVOList.elements();
    } //-- java.util.Enumeration enumerateInvestmentAllocationOverrideVO() 

    /**
     * Method enumerateInvestmentArrayVO
     */
    public java.util.Enumeration enumerateInvestmentArrayVO()
    {
        return _investmentArrayVOList.elements();
    } //-- java.util.Enumeration enumerateInvestmentArrayVO() 

    /**
     * Method enumerateLoanSettlementVO
     */
    public java.util.Enumeration enumerateLoanSettlementVO()
    {
        return _loanSettlementVOList.elements();
    } //-- java.util.Enumeration enumerateLoanSettlementVO() 

    /**
     * Method enumerateOverdueChargeRemainingVO
     */
    public java.util.Enumeration enumerateOverdueChargeRemainingVO()
    {
        return _overdueChargeRemainingVOList.elements();
    } //-- java.util.Enumeration enumerateOverdueChargeRemainingVO() 

    /**
     * Method enumerateRiderSegmentVO
     */
    public java.util.Enumeration enumerateRiderSegmentVO()
    {
        return _riderSegmentVOList.elements();
    } //-- java.util.Enumeration enumerateRiderSegmentVO() 

    /**
     * Method enumerateSupplementalSegmentVO
     */
    public java.util.Enumeration enumerateSupplementalSegmentVO()
    {
        return _supplementalSegmentVOList.elements();
    } //-- java.util.Enumeration enumerateSupplementalSegmentVO() 

    /**
     * Method enumerateSuspenseVO
     */
    public java.util.Enumeration enumerateSuspenseVO()
    {
        return _suspenseVOList.elements();
    } //-- java.util.Enumeration enumerateSuspenseVO() 

    /**
     * Method enumerateTamraRetestVO
     */
    public java.util.Enumeration enumerateTamraRetestVO()
    {
        return _tamraRetestVOList.elements();
    } //-- java.util.Enumeration enumerateTamraRetestVO() 

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
        
        if (obj instanceof NaturalDocVO) {
        
            NaturalDocVO temp = (NaturalDocVO)obj;
            if (this._naturalDocPK != temp._naturalDocPK)
                return false;
            if (this._has_naturalDocPK != temp._has_naturalDocPK)
                return false;
            if (this._baseSegmentVO != null) {
                if (temp._baseSegmentVO == null) return false;
                else if (!(this._baseSegmentVO.equals(temp._baseSegmentVO))) 
                    return false;
            }
            else if (temp._baseSegmentVO != null)
                return false;
            if (this._riderSegmentVOList != null) {
                if (temp._riderSegmentVOList == null) return false;
                else if (!(this._riderSegmentVOList.equals(temp._riderSegmentVOList))) 
                    return false;
            }
            else if (temp._riderSegmentVOList != null)
                return false;
            if (this._supplementalSegmentVOList != null) {
                if (temp._supplementalSegmentVOList == null) return false;
                else if (!(this._supplementalSegmentVOList.equals(temp._supplementalSegmentVOList))) 
                    return false;
            }
            else if (temp._supplementalSegmentVOList != null)
                return false;
            if (this._groupSetupVOList != null) {
                if (temp._groupSetupVOList == null) return false;
                else if (!(this._groupSetupVOList.equals(temp._groupSetupVOList))) 
                    return false;
            }
            else if (temp._groupSetupVOList != null)
                return false;
            if (this._investmentAllocationOverrideVOList != null) {
                if (temp._investmentAllocationOverrideVOList == null) return false;
                else if (!(this._investmentAllocationOverrideVOList.equals(temp._investmentAllocationOverrideVOList))) 
                    return false;
            }
            else if (temp._investmentAllocationOverrideVOList != null)
                return false;
            if (this._equityIndexHedgeDetailVOList != null) {
                if (temp._equityIndexHedgeDetailVOList == null) return false;
                else if (!(this._equityIndexHedgeDetailVOList.equals(temp._equityIndexHedgeDetailVOList))) 
                    return false;
            }
            else if (temp._equityIndexHedgeDetailVOList != null)
                return false;
            if (this._overdueChargeRemainingVOList != null) {
                if (temp._overdueChargeRemainingVOList == null) return false;
                else if (!(this._overdueChargeRemainingVOList.equals(temp._overdueChargeRemainingVOList))) 
                    return false;
            }
            else if (temp._overdueChargeRemainingVOList != null)
                return false;
            if (this._investmentArrayVOList != null) {
                if (temp._investmentArrayVOList == null) return false;
                else if (!(this._investmentArrayVOList.equals(temp._investmentArrayVOList))) 
                    return false;
            }
            else if (temp._investmentArrayVOList != null)
                return false;
            if (this._suspenseVOList != null) {
                if (temp._suspenseVOList == null) return false;
                else if (!(this._suspenseVOList.equals(temp._suspenseVOList))) 
                    return false;
            }
            else if (temp._suspenseVOList != null)
                return false;
            if (this._deathInformationVOList != null) {
                if (temp._deathInformationVOList == null) return false;
                else if (!(this._deathInformationVOList.equals(temp._deathInformationVOList))) 
                    return false;
            }
            else if (temp._deathInformationVOList != null)
                return false;
            if (this._tamraRetestVOList != null) {
                if (temp._tamraRetestVOList == null) return false;
                else if (!(this._tamraRetestVOList.equals(temp._tamraRetestVOList))) 
                    return false;
            }
            else if (temp._tamraRetestVOList != null)
                return false;
            if (this._groupRate != null) {
                if (temp._groupRate == null) return false;
                else if (!(this._groupRate.equals(temp._groupRate))) 
                    return false;
            }
            else if (temp._groupRate != null)
                return false;
            if (this._groupNoParticipants != temp._groupNoParticipants)
                return false;
            if (this._has_groupNoParticipants != temp._has_groupNoParticipants)
                return false;
            if (this._premiumToDate != null) {
                if (temp._premiumToDate == null) return false;
                else if (!(this._premiumToDate.equals(temp._premiumToDate))) 
                    return false;
            }
            else if (temp._premiumToDate != null)
                return false;
            if (this._premiumYearToDate != null) {
                if (temp._premiumYearToDate == null) return false;
                else if (!(this._premiumYearToDate.equals(temp._premiumYearToDate))) 
                    return false;
            }
            else if (temp._premiumYearToDate != null)
                return false;
            if (this._premiumCalYearToDate != null) {
                if (temp._premiumCalYearToDate == null) return false;
                else if (!(this._premiumCalYearToDate.equals(temp._premiumCalYearToDate))) 
                    return false;
            }
            else if (temp._premiumCalYearToDate != null)
                return false;
            if (this._netWithdrawalsYearToDate != null) {
                if (temp._netWithdrawalsYearToDate == null) return false;
                else if (!(this._netWithdrawalsYearToDate.equals(temp._netWithdrawalsYearToDate))) 
                    return false;
            }
            else if (temp._netWithdrawalsYearToDate != null)
                return false;
            if (this._netWithdrawalsToDate != null) {
                if (temp._netWithdrawalsToDate == null) return false;
                else if (!(this._netWithdrawalsToDate.equals(temp._netWithdrawalsToDate))) 
                    return false;
            }
            else if (temp._netWithdrawalsToDate != null)
                return false;
            if (this._premiumSinceLast7PayDate != null) {
                if (temp._premiumSinceLast7PayDate == null) return false;
                else if (!(this._premiumSinceLast7PayDate.equals(temp._premiumSinceLast7PayDate))) 
                    return false;
            }
            else if (temp._premiumSinceLast7PayDate != null)
                return false;
            if (this._withdrawalsSinceLast7PayDate != null) {
                if (temp._withdrawalsSinceLast7PayDate == null) return false;
                else if (!(this._withdrawalsSinceLast7PayDate.equals(temp._withdrawalsSinceLast7PayDate))) 
                    return false;
            }
            else if (temp._withdrawalsSinceLast7PayDate != null)
                return false;
            if (this._cumInitialPremium != null) {
                if (temp._cumInitialPremium == null) return false;
                else if (!(this._cumInitialPremium.equals(temp._cumInitialPremium))) 
                    return false;
            }
            else if (temp._cumInitialPremium != null)
                return false;
            if (this._cum1035Premium != null) {
                if (temp._cum1035Premium == null) return false;
                else if (!(this._cum1035Premium.equals(temp._cum1035Premium))) 
                    return false;
            }
            else if (temp._cum1035Premium != null)
                return false;
            if (this._numberWithdrawalsPolYearToDate != temp._numberWithdrawalsPolYearToDate)
                return false;
            if (this._has_numberWithdrawalsPolYearToDate != temp._has_numberWithdrawalsPolYearToDate)
                return false;
            if (this._numberTransfersPolYearToDate != temp._numberTransfersPolYearToDate)
                return false;
            if (this._has_numberTransfersPolYearToDate != temp._has_numberTransfersPolYearToDate)
                return false;
            if (this._withdrawalsTaxYTD != null) {
                if (temp._withdrawalsTaxYTD == null) return false;
                else if (!(this._withdrawalsTaxYTD.equals(temp._withdrawalsTaxYTD))) 
                    return false;
            }
            else if (temp._withdrawalsTaxYTD != null)
                return false;
            if (this._RMDTaxYTD != null) {
                if (temp._RMDTaxYTD == null) return false;
                else if (!(this._RMDTaxYTD.equals(temp._RMDTaxYTD))) 
                    return false;
            }
            else if (temp._RMDTaxYTD != null)
                return false;
            if (this._priorCYAccumulatedValue != null) {
                if (temp._priorCYAccumulatedValue == null) return false;
                else if (!(this._priorCYAccumulatedValue.equals(temp._priorCYAccumulatedValue))) 
                    return false;
            }
            else if (temp._priorCYAccumulatedValue != null)
                return false;
            if (this._ownersPremiumTaxState != null) {
                if (temp._ownersPremiumTaxState == null) return false;
                else if (!(this._ownersPremiumTaxState.equals(temp._ownersPremiumTaxState))) 
                    return false;
            }
            else if (temp._ownersPremiumTaxState != null)
                return false;
            if (this._insuredResidenceState != null) {
                if (temp._insuredResidenceState == null) return false;
                else if (!(this._insuredResidenceState.equals(temp._insuredResidenceState))) 
                    return false;
            }
            else if (temp._insuredResidenceState != null)
                return false;
            if (this._insuredGender != null) {
                if (temp._insuredGender == null) return false;
                else if (!(this._insuredGender.equals(temp._insuredGender))) 
                    return false;
            }
            else if (temp._insuredGender != null)
                return false;
            if (this._insuredDateOfBirth != null) {
                if (temp._insuredDateOfBirth == null) return false;
                else if (!(this._insuredDateOfBirth.equals(temp._insuredDateOfBirth))) 
                    return false;
            }
            else if (temp._insuredDateOfBirth != null)
                return false;
            if (this._insuredIssueAge != temp._insuredIssueAge)
                return false;
            if (this._has_insuredIssueAge != temp._has_insuredIssueAge)
                return false;
            if (this._insuredClass != null) {
                if (temp._insuredClass == null) return false;
                else if (!(this._insuredClass.equals(temp._insuredClass))) 
                    return false;
            }
            else if (temp._insuredClass != null)
                return false;
            if (this._insuredUnderwritingClass != null) {
                if (temp._insuredUnderwritingClass == null) return false;
                else if (!(this._insuredUnderwritingClass.equals(temp._insuredUnderwritingClass))) 
                    return false;
            }
            else if (temp._insuredUnderwritingClass != null)
                return false;
            if (this._taxDateOfBirth != null) {
                if (temp._taxDateOfBirth == null) return false;
                else if (!(this._taxDateOfBirth.equals(temp._taxDateOfBirth))) 
                    return false;
            }
            else if (temp._taxDateOfBirth != null)
                return false;
            if (this._freeAmount != null) {
                if (temp._freeAmount == null) return false;
                else if (!(this._freeAmount.equals(temp._freeAmount))) 
                    return false;
            }
            else if (temp._freeAmount != null)
                return false;
            if (this._initCYAccumValue != null) {
                if (temp._initCYAccumValue == null) return false;
                else if (!(this._initCYAccumValue.equals(temp._initCYAccumValue))) 
                    return false;
            }
            else if (temp._initCYAccumValue != null)
                return false;
            if (this._cumPremiumTax != null) {
                if (temp._cumPremiumTax == null) return false;
                else if (!(this._cumPremiumTax.equals(temp._cumPremiumTax))) 
                    return false;
            }
            else if (temp._cumPremiumTax != null)
                return false;
            if (this._cumDacTax != null) {
                if (temp._cumDacTax == null) return false;
                else if (!(this._cumDacTax.equals(temp._cumDacTax))) 
                    return false;
            }
            else if (temp._cumDacTax != null)
                return false;
            if (this._cumSalesLoad != null) {
                if (temp._cumSalesLoad == null) return false;
                else if (!(this._cumSalesLoad.equals(temp._cumSalesLoad))) 
                    return false;
            }
            else if (temp._cumSalesLoad != null)
                return false;
            if (this._cumCoi != null) {
                if (temp._cumCoi == null) return false;
                else if (!(this._cumCoi.equals(temp._cumCoi))) 
                    return false;
            }
            else if (temp._cumCoi != null)
                return false;
            if (this._settledOverdueCoi != null) {
                if (temp._settledOverdueCoi == null) return false;
                else if (!(this._settledOverdueCoi.equals(temp._settledOverdueCoi))) 
                    return false;
            }
            else if (temp._settledOverdueCoi != null)
                return false;
            if (this._secondaryAnnuitantIssueAge != temp._secondaryAnnuitantIssueAge)
                return false;
            if (this._has_secondaryAnnuitantIssueAge != temp._has_secondaryAnnuitantIssueAge)
                return false;
            if (this._secondaryAnnuitantGender != null) {
                if (temp._secondaryAnnuitantGender == null) return false;
                else if (!(this._secondaryAnnuitantGender.equals(temp._secondaryAnnuitantGender))) 
                    return false;
            }
            else if (temp._secondaryAnnuitantGender != null)
                return false;
            if (this._secondaryAnnuitantTableRating != null) {
                if (temp._secondaryAnnuitantTableRating == null) return false;
                else if (!(this._secondaryAnnuitantTableRating.equals(temp._secondaryAnnuitantTableRating))) 
                    return false;
            }
            else if (temp._secondaryAnnuitantTableRating != null)
                return false;
            if (this._lastLoanCapAmount != null) {
                if (temp._lastLoanCapAmount == null) return false;
                else if (!(this._lastLoanCapAmount.equals(temp._lastLoanCapAmount))) 
                    return false;
            }
            else if (temp._lastLoanCapAmount != null)
                return false;
            if (this._generalAccountArrayVOList != null) {
                if (temp._generalAccountArrayVOList == null) return false;
                else if (!(this._generalAccountArrayVOList.equals(temp._generalAccountArrayVOList))) 
                    return false;
            }
            else if (temp._generalAccountArrayVOList != null)
                return false;
            if (this._loanSettlementVOList != null) {
                if (temp._loanSettlementVOList == null) return false;
                else if (!(this._loanSettlementVOList.equals(temp._loanSettlementVOList))) 
                    return false;
            }
            else if (temp._loanSettlementVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBaseSegmentVOReturns the value of field
     * 'baseSegmentVO'. The field 'baseSegmentVO' has the following
     * description: Comment describing your root element
     * 
     * @return the value of field 'baseSegmentVO'.
     */
    public edit.common.vo.BaseSegmentVO getBaseSegmentVO()
    {
        return this._baseSegmentVO;
    } //-- edit.common.vo.BaseSegmentVO getBaseSegmentVO() 

    /**
     * Method getCum1035PremiumReturns the value of field
     * 'cum1035Premium'.
     * 
     * @return the value of field 'cum1035Premium'.
     */
    public java.math.BigDecimal getCum1035Premium()
    {
        return this._cum1035Premium;
    } //-- java.math.BigDecimal getCum1035Premium() 

    /**
     * Method getCumCoiReturns the value of field 'cumCoi'.
     * 
     * @return the value of field 'cumCoi'.
     */
    public java.math.BigDecimal getCumCoi()
    {
        return this._cumCoi;
    } //-- java.math.BigDecimal getCumCoi() 

    /**
     * Method getCumDacTaxReturns the value of field 'cumDacTax'.
     * 
     * @return the value of field 'cumDacTax'.
     */
    public java.math.BigDecimal getCumDacTax()
    {
        return this._cumDacTax;
    } //-- java.math.BigDecimal getCumDacTax() 

    /**
     * Method getCumInitialPremiumReturns the value of field
     * 'cumInitialPremium'.
     * 
     * @return the value of field 'cumInitialPremium'.
     */
    public java.math.BigDecimal getCumInitialPremium()
    {
        return this._cumInitialPremium;
    } //-- java.math.BigDecimal getCumInitialPremium() 

    /**
     * Method getCumPremiumTaxReturns the value of field
     * 'cumPremiumTax'.
     * 
     * @return the value of field 'cumPremiumTax'.
     */
    public java.math.BigDecimal getCumPremiumTax()
    {
        return this._cumPremiumTax;
    } //-- java.math.BigDecimal getCumPremiumTax() 

    /**
     * Method getCumSalesLoadReturns the value of field
     * 'cumSalesLoad'.
     * 
     * @return the value of field 'cumSalesLoad'.
     */
    public java.math.BigDecimal getCumSalesLoad()
    {
        return this._cumSalesLoad;
    } //-- java.math.BigDecimal getCumSalesLoad() 

    /**
     * Method getDeathInformationVO
     * 
     * @param index
     */
    public edit.common.vo.DeathInformationVO getDeathInformationVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _deathInformationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.DeathInformationVO) _deathInformationVOList.elementAt(index);
    } //-- edit.common.vo.DeathInformationVO getDeathInformationVO(int) 

    /**
     * Method getDeathInformationVO
     */
    public edit.common.vo.DeathInformationVO[] getDeathInformationVO()
    {
        int size = _deathInformationVOList.size();
        edit.common.vo.DeathInformationVO[] mArray = new edit.common.vo.DeathInformationVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.DeathInformationVO) _deathInformationVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.DeathInformationVO[] getDeathInformationVO() 

    /**
     * Method getDeathInformationVOCount
     */
    public int getDeathInformationVOCount()
    {
        return _deathInformationVOList.size();
    } //-- int getDeathInformationVOCount() 

    /**
     * Method getEquityIndexHedgeDetailVO
     * 
     * @param index
     */
    public edit.common.vo.EquityIndexHedgeDetailVO getEquityIndexHedgeDetailVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _equityIndexHedgeDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.EquityIndexHedgeDetailVO) _equityIndexHedgeDetailVOList.elementAt(index);
    } //-- edit.common.vo.EquityIndexHedgeDetailVO getEquityIndexHedgeDetailVO(int) 

    /**
     * Method getEquityIndexHedgeDetailVO
     */
    public edit.common.vo.EquityIndexHedgeDetailVO[] getEquityIndexHedgeDetailVO()
    {
        int size = _equityIndexHedgeDetailVOList.size();
        edit.common.vo.EquityIndexHedgeDetailVO[] mArray = new edit.common.vo.EquityIndexHedgeDetailVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.EquityIndexHedgeDetailVO) _equityIndexHedgeDetailVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.EquityIndexHedgeDetailVO[] getEquityIndexHedgeDetailVO() 

    /**
     * Method getEquityIndexHedgeDetailVOCount
     */
    public int getEquityIndexHedgeDetailVOCount()
    {
        return _equityIndexHedgeDetailVOList.size();
    } //-- int getEquityIndexHedgeDetailVOCount() 

    /**
     * Method getFreeAmountReturns the value of field 'freeAmount'.
     * 
     * @return the value of field 'freeAmount'.
     */
    public java.math.BigDecimal getFreeAmount()
    {
        return this._freeAmount;
    } //-- java.math.BigDecimal getFreeAmount() 

    /**
     * Method getGeneralAccountArrayVO
     * 
     * @param index
     */
    public edit.common.vo.GeneralAccountArrayVO getGeneralAccountArrayVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _generalAccountArrayVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.GeneralAccountArrayVO) _generalAccountArrayVOList.elementAt(index);
    } //-- edit.common.vo.GeneralAccountArrayVO getGeneralAccountArrayVO(int) 

    /**
     * Method getGeneralAccountArrayVO
     */
    public edit.common.vo.GeneralAccountArrayVO[] getGeneralAccountArrayVO()
    {
        int size = _generalAccountArrayVOList.size();
        edit.common.vo.GeneralAccountArrayVO[] mArray = new edit.common.vo.GeneralAccountArrayVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.GeneralAccountArrayVO) _generalAccountArrayVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.GeneralAccountArrayVO[] getGeneralAccountArrayVO() 

    /**
     * Method getGeneralAccountArrayVOCount
     */
    public int getGeneralAccountArrayVOCount()
    {
        return _generalAccountArrayVOList.size();
    } //-- int getGeneralAccountArrayVOCount() 

    /**
     * Method getGroupNoParticipantsReturns the value of field
     * 'groupNoParticipants'.
     * 
     * @return the value of field 'groupNoParticipants'.
     */
    public int getGroupNoParticipants()
    {
        return this._groupNoParticipants;
    } //-- int getGroupNoParticipants() 

    /**
     * Method getGroupRateReturns the value of field 'groupRate'.
     * 
     * @return the value of field 'groupRate'.
     */
    public java.math.BigDecimal getGroupRate()
    {
        return this._groupRate;
    } //-- java.math.BigDecimal getGroupRate() 

    /**
     * Method getGroupSetupVO
     * 
     * @param index
     */
    public edit.common.vo.GroupSetupVO getGroupSetupVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _groupSetupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.GroupSetupVO) _groupSetupVOList.elementAt(index);
    } //-- edit.common.vo.GroupSetupVO getGroupSetupVO(int) 

    /**
     * Method getGroupSetupVO
     */
    public edit.common.vo.GroupSetupVO[] getGroupSetupVO()
    {
        int size = _groupSetupVOList.size();
        edit.common.vo.GroupSetupVO[] mArray = new edit.common.vo.GroupSetupVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.GroupSetupVO) _groupSetupVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.GroupSetupVO[] getGroupSetupVO() 

    /**
     * Method getGroupSetupVOCount
     */
    public int getGroupSetupVOCount()
    {
        return _groupSetupVOList.size();
    } //-- int getGroupSetupVOCount() 

    /**
     * Method getInitCYAccumValueReturns the value of field
     * 'initCYAccumValue'.
     * 
     * @return the value of field 'initCYAccumValue'.
     */
    public java.math.BigDecimal getInitCYAccumValue()
    {
        return this._initCYAccumValue;
    } //-- java.math.BigDecimal getInitCYAccumValue() 

    /**
     * Method getInsuredClassReturns the value of field
     * 'insuredClass'.
     * 
     * @return the value of field 'insuredClass'.
     */
    public java.lang.String getInsuredClass()
    {
        return this._insuredClass;
    } //-- java.lang.String getInsuredClass() 

    /**
     * Method getInsuredDateOfBirthReturns the value of field
     * 'insuredDateOfBirth'.
     * 
     * @return the value of field 'insuredDateOfBirth'.
     */
    public java.lang.String getInsuredDateOfBirth()
    {
        return this._insuredDateOfBirth;
    } //-- java.lang.String getInsuredDateOfBirth() 

    /**
     * Method getInsuredGenderReturns the value of field
     * 'insuredGender'.
     * 
     * @return the value of field 'insuredGender'.
     */
    public java.lang.String getInsuredGender()
    {
        return this._insuredGender;
    } //-- java.lang.String getInsuredGender() 

    /**
     * Method getInsuredIssueAgeReturns the value of field
     * 'insuredIssueAge'.
     * 
     * @return the value of field 'insuredIssueAge'.
     */
    public int getInsuredIssueAge()
    {
        return this._insuredIssueAge;
    } //-- int getInsuredIssueAge() 

    /**
     * Method getInsuredResidenceStateReturns the value of field
     * 'insuredResidenceState'.
     * 
     * @return the value of field 'insuredResidenceState'.
     */
    public java.lang.String getInsuredResidenceState()
    {
        return this._insuredResidenceState;
    } //-- java.lang.String getInsuredResidenceState() 

    /**
     * Method getInsuredUnderwritingClassReturns the value of field
     * 'insuredUnderwritingClass'.
     * 
     * @return the value of field 'insuredUnderwritingClass'.
     */
    public java.lang.String getInsuredUnderwritingClass()
    {
        return this._insuredUnderwritingClass;
    } //-- java.lang.String getInsuredUnderwritingClass() 

    /**
     * Method getInvestmentAllocationOverrideVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentAllocationOverrideVO getInvestmentAllocationOverrideVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentAllocationOverrideVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.InvestmentAllocationOverrideVO) _investmentAllocationOverrideVOList.elementAt(index);
    } //-- edit.common.vo.InvestmentAllocationOverrideVO getInvestmentAllocationOverrideVO(int) 

    /**
     * Method getInvestmentAllocationOverrideVO
     */
    public edit.common.vo.InvestmentAllocationOverrideVO[] getInvestmentAllocationOverrideVO()
    {
        int size = _investmentAllocationOverrideVOList.size();
        edit.common.vo.InvestmentAllocationOverrideVO[] mArray = new edit.common.vo.InvestmentAllocationOverrideVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.InvestmentAllocationOverrideVO) _investmentAllocationOverrideVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.InvestmentAllocationOverrideVO[] getInvestmentAllocationOverrideVO() 

    /**
     * Method getInvestmentAllocationOverrideVOCount
     */
    public int getInvestmentAllocationOverrideVOCount()
    {
        return _investmentAllocationOverrideVOList.size();
    } //-- int getInvestmentAllocationOverrideVOCount() 

    /**
     * Method getInvestmentArrayVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentArrayVO getInvestmentArrayVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentArrayVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.InvestmentArrayVO) _investmentArrayVOList.elementAt(index);
    } //-- edit.common.vo.InvestmentArrayVO getInvestmentArrayVO(int) 

    /**
     * Method getInvestmentArrayVO
     */
    public edit.common.vo.InvestmentArrayVO[] getInvestmentArrayVO()
    {
        int size = _investmentArrayVOList.size();
        edit.common.vo.InvestmentArrayVO[] mArray = new edit.common.vo.InvestmentArrayVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.InvestmentArrayVO) _investmentArrayVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.InvestmentArrayVO[] getInvestmentArrayVO() 

    /**
     * Method getInvestmentArrayVOCount
     */
    public int getInvestmentArrayVOCount()
    {
        return _investmentArrayVOList.size();
    } //-- int getInvestmentArrayVOCount() 

    /**
     * Method getLastLoanCapAmountReturns the value of field
     * 'lastLoanCapAmount'.
     * 
     * @return the value of field 'lastLoanCapAmount'.
     */
    public java.math.BigDecimal getLastLoanCapAmount()
    {
        return this._lastLoanCapAmount;
    } //-- java.math.BigDecimal getLastLoanCapAmount() 

    /**
     * Method getLoanSettlementVO
     * 
     * @param index
     */
    public edit.common.vo.LoanSettlementVO getLoanSettlementVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _loanSettlementVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.LoanSettlementVO) _loanSettlementVOList.elementAt(index);
    } //-- edit.common.vo.LoanSettlementVO getLoanSettlementVO(int) 

    /**
     * Method getLoanSettlementVO
     */
    public edit.common.vo.LoanSettlementVO[] getLoanSettlementVO()
    {
        int size = _loanSettlementVOList.size();
        edit.common.vo.LoanSettlementVO[] mArray = new edit.common.vo.LoanSettlementVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.LoanSettlementVO) _loanSettlementVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.LoanSettlementVO[] getLoanSettlementVO() 

    /**
     * Method getLoanSettlementVOCount
     */
    public int getLoanSettlementVOCount()
    {
        return _loanSettlementVOList.size();
    } //-- int getLoanSettlementVOCount() 

    /**
     * Method getNaturalDocPKReturns the value of field
     * 'naturalDocPK'.
     * 
     * @return the value of field 'naturalDocPK'.
     */
    public long getNaturalDocPK()
    {
        return this._naturalDocPK;
    } //-- long getNaturalDocPK() 

    /**
     * Method getNetWithdrawalsToDateReturns the value of field
     * 'netWithdrawalsToDate'.
     * 
     * @return the value of field 'netWithdrawalsToDate'.
     */
    public java.math.BigDecimal getNetWithdrawalsToDate()
    {
        return this._netWithdrawalsToDate;
    } //-- java.math.BigDecimal getNetWithdrawalsToDate() 

    /**
     * Method getNetWithdrawalsYearToDateReturns the value of field
     * 'netWithdrawalsYearToDate'.
     * 
     * @return the value of field 'netWithdrawalsYearToDate'.
     */
    public java.math.BigDecimal getNetWithdrawalsYearToDate()
    {
        return this._netWithdrawalsYearToDate;
    } //-- java.math.BigDecimal getNetWithdrawalsYearToDate() 

    /**
     * Method getNumberTransfersPolYearToDateReturns the value of
     * field 'numberTransfersPolYearToDate'.
     * 
     * @return the value of field 'numberTransfersPolYearToDate'.
     */
    public int getNumberTransfersPolYearToDate()
    {
        return this._numberTransfersPolYearToDate;
    } //-- int getNumberTransfersPolYearToDate() 

    /**
     * Method getNumberWithdrawalsPolYearToDateReturns the value of
     * field 'numberWithdrawalsPolYearToDate'.
     * 
     * @return the value of field 'numberWithdrawalsPolYearToDate'.
     */
    public int getNumberWithdrawalsPolYearToDate()
    {
        return this._numberWithdrawalsPolYearToDate;
    } //-- int getNumberWithdrawalsPolYearToDate() 

    /**
     * Method getOverdueChargeRemainingVO
     * 
     * @param index
     */
    public edit.common.vo.OverdueChargeRemainingVO getOverdueChargeRemainingVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _overdueChargeRemainingVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.OverdueChargeRemainingVO) _overdueChargeRemainingVOList.elementAt(index);
    } //-- edit.common.vo.OverdueChargeRemainingVO getOverdueChargeRemainingVO(int) 

    /**
     * Method getOverdueChargeRemainingVO
     */
    public edit.common.vo.OverdueChargeRemainingVO[] getOverdueChargeRemainingVO()
    {
        int size = _overdueChargeRemainingVOList.size();
        edit.common.vo.OverdueChargeRemainingVO[] mArray = new edit.common.vo.OverdueChargeRemainingVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.OverdueChargeRemainingVO) _overdueChargeRemainingVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.OverdueChargeRemainingVO[] getOverdueChargeRemainingVO() 

    /**
     * Method getOverdueChargeRemainingVOCount
     */
    public int getOverdueChargeRemainingVOCount()
    {
        return _overdueChargeRemainingVOList.size();
    } //-- int getOverdueChargeRemainingVOCount() 

    /**
     * Method getOwnersPremiumTaxStateReturns the value of field
     * 'ownersPremiumTaxState'.
     * 
     * @return the value of field 'ownersPremiumTaxState'.
     */
    public java.lang.String getOwnersPremiumTaxState()
    {
        return this._ownersPremiumTaxState;
    } //-- java.lang.String getOwnersPremiumTaxState() 

    /**
     * Method getPremiumCalYearToDateReturns the value of field
     * 'premiumCalYearToDate'.
     * 
     * @return the value of field 'premiumCalYearToDate'.
     */
    public java.math.BigDecimal getPremiumCalYearToDate()
    {
        return this._premiumCalYearToDate;
    } //-- java.math.BigDecimal getPremiumCalYearToDate() 

    /**
     * Method getPremiumSinceLast7PayDateReturns the value of field
     * 'premiumSinceLast7PayDate'.
     * 
     * @return the value of field 'premiumSinceLast7PayDate'.
     */
    public java.math.BigDecimal getPremiumSinceLast7PayDate()
    {
        return this._premiumSinceLast7PayDate;
    } //-- java.math.BigDecimal getPremiumSinceLast7PayDate() 

    /**
     * Method getPremiumToDateReturns the value of field
     * 'premiumToDate'.
     * 
     * @return the value of field 'premiumToDate'.
     */
    public java.math.BigDecimal getPremiumToDate()
    {
        return this._premiumToDate;
    } //-- java.math.BigDecimal getPremiumToDate() 

    /**
     * Method getPremiumYearToDateReturns the value of field
     * 'premiumYearToDate'.
     * 
     * @return the value of field 'premiumYearToDate'.
     */
    public java.math.BigDecimal getPremiumYearToDate()
    {
        return this._premiumYearToDate;
    } //-- java.math.BigDecimal getPremiumYearToDate() 

    /**
     * Method getPriorCYAccumulatedValueReturns the value of field
     * 'priorCYAccumulatedValue'.
     * 
     * @return the value of field 'priorCYAccumulatedValue'.
     */
    public java.math.BigDecimal getPriorCYAccumulatedValue()
    {
        return this._priorCYAccumulatedValue;
    } //-- java.math.BigDecimal getPriorCYAccumulatedValue() 

    /**
     * Method getRMDTaxYTDReturns the value of field 'RMDTaxYTD'.
     * 
     * @return the value of field 'RMDTaxYTD'.
     */
    public java.math.BigDecimal getRMDTaxYTD()
    {
        return this._RMDTaxYTD;
    } //-- java.math.BigDecimal getRMDTaxYTD() 

    /**
     * Method getRiderSegmentVO
     * 
     * @param index
     */
    public edit.common.vo.RiderSegmentVO getRiderSegmentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _riderSegmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.RiderSegmentVO) _riderSegmentVOList.elementAt(index);
    } //-- edit.common.vo.RiderSegmentVO getRiderSegmentVO(int) 

    /**
     * Method getRiderSegmentVO
     */
    public edit.common.vo.RiderSegmentVO[] getRiderSegmentVO()
    {
        int size = _riderSegmentVOList.size();
        edit.common.vo.RiderSegmentVO[] mArray = new edit.common.vo.RiderSegmentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.RiderSegmentVO) _riderSegmentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.RiderSegmentVO[] getRiderSegmentVO() 

    /**
     * Method getRiderSegmentVOCount
     */
    public int getRiderSegmentVOCount()
    {
        return _riderSegmentVOList.size();
    } //-- int getRiderSegmentVOCount() 

    /**
     * Method getSecondaryAnnuitantGenderReturns the value of field
     * 'secondaryAnnuitantGender'.
     * 
     * @return the value of field 'secondaryAnnuitantGender'.
     */
    public java.lang.String getSecondaryAnnuitantGender()
    {
        return this._secondaryAnnuitantGender;
    } //-- java.lang.String getSecondaryAnnuitantGender() 

    /**
     * Method getSecondaryAnnuitantIssueAgeReturns the value of
     * field 'secondaryAnnuitantIssueAge'.
     * 
     * @return the value of field 'secondaryAnnuitantIssueAge'.
     */
    public int getSecondaryAnnuitantIssueAge()
    {
        return this._secondaryAnnuitantIssueAge;
    } //-- int getSecondaryAnnuitantIssueAge() 

    /**
     * Method getSecondaryAnnuitantTableRatingReturns the value of
     * field 'secondaryAnnuitantTableRating'.
     * 
     * @return the value of field 'secondaryAnnuitantTableRating'.
     */
    public java.lang.String getSecondaryAnnuitantTableRating()
    {
        return this._secondaryAnnuitantTableRating;
    } //-- java.lang.String getSecondaryAnnuitantTableRating() 

    /**
     * Method getSettledOverdueCoiReturns the value of field
     * 'settledOverdueCoi'.
     * 
     * @return the value of field 'settledOverdueCoi'.
     */
    public java.math.BigDecimal getSettledOverdueCoi()
    {
        return this._settledOverdueCoi;
    } //-- java.math.BigDecimal getSettledOverdueCoi() 

    /**
     * Method getSupplementalSegmentVO
     * 
     * @param index
     */
    public edit.common.vo.SupplementalSegmentVO getSupplementalSegmentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _supplementalSegmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.SupplementalSegmentVO) _supplementalSegmentVOList.elementAt(index);
    } //-- edit.common.vo.SupplementalSegmentVO getSupplementalSegmentVO(int) 

    /**
     * Method getSupplementalSegmentVO
     */
    public edit.common.vo.SupplementalSegmentVO[] getSupplementalSegmentVO()
    {
        int size = _supplementalSegmentVOList.size();
        edit.common.vo.SupplementalSegmentVO[] mArray = new edit.common.vo.SupplementalSegmentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.SupplementalSegmentVO) _supplementalSegmentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.SupplementalSegmentVO[] getSupplementalSegmentVO() 

    /**
     * Method getSupplementalSegmentVOCount
     */
    public int getSupplementalSegmentVOCount()
    {
        return _supplementalSegmentVOList.size();
    } //-- int getSupplementalSegmentVOCount() 

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
     * Method getTamraRetestVO
     * 
     * @param index
     */
    public edit.common.vo.TamraRetestVO getTamraRetestVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _tamraRetestVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.TamraRetestVO) _tamraRetestVOList.elementAt(index);
    } //-- edit.common.vo.TamraRetestVO getTamraRetestVO(int) 

    /**
     * Method getTamraRetestVO
     */
    public edit.common.vo.TamraRetestVO[] getTamraRetestVO()
    {
        int size = _tamraRetestVOList.size();
        edit.common.vo.TamraRetestVO[] mArray = new edit.common.vo.TamraRetestVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.TamraRetestVO) _tamraRetestVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.TamraRetestVO[] getTamraRetestVO() 

    /**
     * Method getTamraRetestVOCount
     */
    public int getTamraRetestVOCount()
    {
        return _tamraRetestVOList.size();
    } //-- int getTamraRetestVOCount() 

    /**
     * Method getTaxDateOfBirthReturns the value of field
     * 'taxDateOfBirth'.
     * 
     * @return the value of field 'taxDateOfBirth'.
     */
    public java.lang.String getTaxDateOfBirth()
    {
        return this._taxDateOfBirth;
    } //-- java.lang.String getTaxDateOfBirth() 

    /**
     * Method getWithdrawalsSinceLast7PayDateReturns the value of
     * field 'withdrawalsSinceLast7PayDate'.
     * 
     * @return the value of field 'withdrawalsSinceLast7PayDate'.
     */
    public java.math.BigDecimal getWithdrawalsSinceLast7PayDate()
    {
        return this._withdrawalsSinceLast7PayDate;
    } //-- java.math.BigDecimal getWithdrawalsSinceLast7PayDate() 

    /**
     * Method getWithdrawalsTaxYTDReturns the value of field
     * 'withdrawalsTaxYTD'.
     * 
     * @return the value of field 'withdrawalsTaxYTD'.
     */
    public java.math.BigDecimal getWithdrawalsTaxYTD()
    {
        return this._withdrawalsTaxYTD;
    } //-- java.math.BigDecimal getWithdrawalsTaxYTD() 

    /**
     * Method hasGroupNoParticipants
     */
    public boolean hasGroupNoParticipants()
    {
        return this._has_groupNoParticipants;
    } //-- boolean hasGroupNoParticipants() 

    /**
     * Method hasInsuredIssueAge
     */
    public boolean hasInsuredIssueAge()
    {
        return this._has_insuredIssueAge;
    } //-- boolean hasInsuredIssueAge() 

    /**
     * Method hasNaturalDocPK
     */
    public boolean hasNaturalDocPK()
    {
        return this._has_naturalDocPK;
    } //-- boolean hasNaturalDocPK() 

    /**
     * Method hasNumberTransfersPolYearToDate
     */
    public boolean hasNumberTransfersPolYearToDate()
    {
        return this._has_numberTransfersPolYearToDate;
    } //-- boolean hasNumberTransfersPolYearToDate() 

    /**
     * Method hasNumberWithdrawalsPolYearToDate
     */
    public boolean hasNumberWithdrawalsPolYearToDate()
    {
        return this._has_numberWithdrawalsPolYearToDate;
    } //-- boolean hasNumberWithdrawalsPolYearToDate() 

    /**
     * Method hasSecondaryAnnuitantIssueAge
     */
    public boolean hasSecondaryAnnuitantIssueAge()
    {
        return this._has_secondaryAnnuitantIssueAge;
    } //-- boolean hasSecondaryAnnuitantIssueAge() 

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
     * Method removeAllDeathInformationVO
     */
    public void removeAllDeathInformationVO()
    {
        _deathInformationVOList.removeAllElements();
    } //-- void removeAllDeathInformationVO() 

    /**
     * Method removeAllEquityIndexHedgeDetailVO
     */
    public void removeAllEquityIndexHedgeDetailVO()
    {
        _equityIndexHedgeDetailVOList.removeAllElements();
    } //-- void removeAllEquityIndexHedgeDetailVO() 

    /**
     * Method removeAllGeneralAccountArrayVO
     */
    public void removeAllGeneralAccountArrayVO()
    {
        _generalAccountArrayVOList.removeAllElements();
    } //-- void removeAllGeneralAccountArrayVO() 

    /**
     * Method removeAllGroupSetupVO
     */
    public void removeAllGroupSetupVO()
    {
        _groupSetupVOList.removeAllElements();
    } //-- void removeAllGroupSetupVO() 

    /**
     * Method removeAllInvestmentAllocationOverrideVO
     */
    public void removeAllInvestmentAllocationOverrideVO()
    {
        _investmentAllocationOverrideVOList.removeAllElements();
    } //-- void removeAllInvestmentAllocationOverrideVO() 

    /**
     * Method removeAllInvestmentArrayVO
     */
    public void removeAllInvestmentArrayVO()
    {
        _investmentArrayVOList.removeAllElements();
    } //-- void removeAllInvestmentArrayVO() 

    /**
     * Method removeAllLoanSettlementVO
     */
    public void removeAllLoanSettlementVO()
    {
        _loanSettlementVOList.removeAllElements();
    } //-- void removeAllLoanSettlementVO() 

    /**
     * Method removeAllOverdueChargeRemainingVO
     */
    public void removeAllOverdueChargeRemainingVO()
    {
        _overdueChargeRemainingVOList.removeAllElements();
    } //-- void removeAllOverdueChargeRemainingVO() 

    /**
     * Method removeAllRiderSegmentVO
     */
    public void removeAllRiderSegmentVO()
    {
        _riderSegmentVOList.removeAllElements();
    } //-- void removeAllRiderSegmentVO() 

    /**
     * Method removeAllSupplementalSegmentVO
     */
    public void removeAllSupplementalSegmentVO()
    {
        _supplementalSegmentVOList.removeAllElements();
    } //-- void removeAllSupplementalSegmentVO() 

    /**
     * Method removeAllSuspenseVO
     */
    public void removeAllSuspenseVO()
    {
        _suspenseVOList.removeAllElements();
    } //-- void removeAllSuspenseVO() 

    /**
     * Method removeAllTamraRetestVO
     */
    public void removeAllTamraRetestVO()
    {
        _tamraRetestVOList.removeAllElements();
    } //-- void removeAllTamraRetestVO() 

    /**
     * Method removeDeathInformationVO
     * 
     * @param index
     */
    public edit.common.vo.DeathInformationVO removeDeathInformationVO(int index)
    {
        java.lang.Object obj = _deathInformationVOList.elementAt(index);
        _deathInformationVOList.removeElementAt(index);
        return (edit.common.vo.DeathInformationVO) obj;
    } //-- edit.common.vo.DeathInformationVO removeDeathInformationVO(int) 

    /**
     * Method removeEquityIndexHedgeDetailVO
     * 
     * @param index
     */
    public edit.common.vo.EquityIndexHedgeDetailVO removeEquityIndexHedgeDetailVO(int index)
    {
        java.lang.Object obj = _equityIndexHedgeDetailVOList.elementAt(index);
        _equityIndexHedgeDetailVOList.removeElementAt(index);
        return (edit.common.vo.EquityIndexHedgeDetailVO) obj;
    } //-- edit.common.vo.EquityIndexHedgeDetailVO removeEquityIndexHedgeDetailVO(int) 

    /**
     * Method removeGeneralAccountArrayVO
     * 
     * @param index
     */
    public edit.common.vo.GeneralAccountArrayVO removeGeneralAccountArrayVO(int index)
    {
        java.lang.Object obj = _generalAccountArrayVOList.elementAt(index);
        _generalAccountArrayVOList.removeElementAt(index);
        return (edit.common.vo.GeneralAccountArrayVO) obj;
    } //-- edit.common.vo.GeneralAccountArrayVO removeGeneralAccountArrayVO(int) 

    /**
     * Method removeGroupSetupVO
     * 
     * @param index
     */
    public edit.common.vo.GroupSetupVO removeGroupSetupVO(int index)
    {
        java.lang.Object obj = _groupSetupVOList.elementAt(index);
        _groupSetupVOList.removeElementAt(index);
        return (edit.common.vo.GroupSetupVO) obj;
    } //-- edit.common.vo.GroupSetupVO removeGroupSetupVO(int) 

    /**
     * Method removeInvestmentAllocationOverrideVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentAllocationOverrideVO removeInvestmentAllocationOverrideVO(int index)
    {
        java.lang.Object obj = _investmentAllocationOverrideVOList.elementAt(index);
        _investmentAllocationOverrideVOList.removeElementAt(index);
        return (edit.common.vo.InvestmentAllocationOverrideVO) obj;
    } //-- edit.common.vo.InvestmentAllocationOverrideVO removeInvestmentAllocationOverrideVO(int) 

    /**
     * Method removeInvestmentArrayVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentArrayVO removeInvestmentArrayVO(int index)
    {
        java.lang.Object obj = _investmentArrayVOList.elementAt(index);
        _investmentArrayVOList.removeElementAt(index);
        return (edit.common.vo.InvestmentArrayVO) obj;
    } //-- edit.common.vo.InvestmentArrayVO removeInvestmentArrayVO(int) 

    /**
     * Method removeLoanSettlementVO
     * 
     * @param index
     */
    public edit.common.vo.LoanSettlementVO removeLoanSettlementVO(int index)
    {
        java.lang.Object obj = _loanSettlementVOList.elementAt(index);
        _loanSettlementVOList.removeElementAt(index);
        return (edit.common.vo.LoanSettlementVO) obj;
    } //-- edit.common.vo.LoanSettlementVO removeLoanSettlementVO(int) 

    /**
     * Method removeOverdueChargeRemainingVO
     * 
     * @param index
     */
    public edit.common.vo.OverdueChargeRemainingVO removeOverdueChargeRemainingVO(int index)
    {
        java.lang.Object obj = _overdueChargeRemainingVOList.elementAt(index);
        _overdueChargeRemainingVOList.removeElementAt(index);
        return (edit.common.vo.OverdueChargeRemainingVO) obj;
    } //-- edit.common.vo.OverdueChargeRemainingVO removeOverdueChargeRemainingVO(int) 

    /**
     * Method removeRiderSegmentVO
     * 
     * @param index
     */
    public edit.common.vo.RiderSegmentVO removeRiderSegmentVO(int index)
    {
        java.lang.Object obj = _riderSegmentVOList.elementAt(index);
        _riderSegmentVOList.removeElementAt(index);
        return (edit.common.vo.RiderSegmentVO) obj;
    } //-- edit.common.vo.RiderSegmentVO removeRiderSegmentVO(int) 

    /**
     * Method removeSupplementalSegmentVO
     * 
     * @param index
     */
    public edit.common.vo.SupplementalSegmentVO removeSupplementalSegmentVO(int index)
    {
        java.lang.Object obj = _supplementalSegmentVOList.elementAt(index);
        _supplementalSegmentVOList.removeElementAt(index);
        return (edit.common.vo.SupplementalSegmentVO) obj;
    } //-- edit.common.vo.SupplementalSegmentVO removeSupplementalSegmentVO(int) 

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
     * Method removeTamraRetestVO
     * 
     * @param index
     */
    public edit.common.vo.TamraRetestVO removeTamraRetestVO(int index)
    {
        java.lang.Object obj = _tamraRetestVOList.elementAt(index);
        _tamraRetestVOList.removeElementAt(index);
        return (edit.common.vo.TamraRetestVO) obj;
    } //-- edit.common.vo.TamraRetestVO removeTamraRetestVO(int) 

    /**
     * Method setBaseSegmentVOSets the value of field
     * 'baseSegmentVO'. The field 'baseSegmentVO' has the following
     * description: Comment describing your root element
     * 
     * @param baseSegmentVO the value of field 'baseSegmentVO'.
     */
    public void setBaseSegmentVO(edit.common.vo.BaseSegmentVO baseSegmentVO)
    {
        this._baseSegmentVO = baseSegmentVO;
    } //-- void setBaseSegmentVO(edit.common.vo.BaseSegmentVO) 

    /**
     * Method setCum1035PremiumSets the value of field
     * 'cum1035Premium'.
     * 
     * @param cum1035Premium the value of field 'cum1035Premium'.
     */
    public void setCum1035Premium(java.math.BigDecimal cum1035Premium)
    {
        this._cum1035Premium = cum1035Premium;
        
        super.setVoChanged(true);
    } //-- void setCum1035Premium(java.math.BigDecimal) 

    /**
     * Method setCumCoiSets the value of field 'cumCoi'.
     * 
     * @param cumCoi the value of field 'cumCoi'.
     */
    public void setCumCoi(java.math.BigDecimal cumCoi)
    {
        this._cumCoi = cumCoi;
        
        super.setVoChanged(true);
    } //-- void setCumCoi(java.math.BigDecimal) 

    /**
     * Method setCumDacTaxSets the value of field 'cumDacTax'.
     * 
     * @param cumDacTax the value of field 'cumDacTax'.
     */
    public void setCumDacTax(java.math.BigDecimal cumDacTax)
    {
        this._cumDacTax = cumDacTax;
        
        super.setVoChanged(true);
    } //-- void setCumDacTax(java.math.BigDecimal) 

    /**
     * Method setCumInitialPremiumSets the value of field
     * 'cumInitialPremium'.
     * 
     * @param cumInitialPremium the value of field
     * 'cumInitialPremium'.
     */
    public void setCumInitialPremium(java.math.BigDecimal cumInitialPremium)
    {
        this._cumInitialPremium = cumInitialPremium;
        
        super.setVoChanged(true);
    } //-- void setCumInitialPremium(java.math.BigDecimal) 

    /**
     * Method setCumPremiumTaxSets the value of field
     * 'cumPremiumTax'.
     * 
     * @param cumPremiumTax the value of field 'cumPremiumTax'.
     */
    public void setCumPremiumTax(java.math.BigDecimal cumPremiumTax)
    {
        this._cumPremiumTax = cumPremiumTax;
        
        super.setVoChanged(true);
    } //-- void setCumPremiumTax(java.math.BigDecimal) 

    /**
     * Method setCumSalesLoadSets the value of field
     * 'cumSalesLoad'.
     * 
     * @param cumSalesLoad the value of field 'cumSalesLoad'.
     */
    public void setCumSalesLoad(java.math.BigDecimal cumSalesLoad)
    {
        this._cumSalesLoad = cumSalesLoad;
        
        super.setVoChanged(true);
    } //-- void setCumSalesLoad(java.math.BigDecimal) 

    /**
     * Method setDeathInformationVO
     * 
     * @param index
     * @param vDeathInformationVO
     */
    public void setDeathInformationVO(int index, edit.common.vo.DeathInformationVO vDeathInformationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _deathInformationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vDeathInformationVO.setParentVO(this.getClass(), this);
        _deathInformationVOList.setElementAt(vDeathInformationVO, index);
    } //-- void setDeathInformationVO(int, edit.common.vo.DeathInformationVO) 

    /**
     * Method setDeathInformationVO
     * 
     * @param deathInformationVOArray
     */
    public void setDeathInformationVO(edit.common.vo.DeathInformationVO[] deathInformationVOArray)
    {
        //-- copy array
        _deathInformationVOList.removeAllElements();
        for (int i = 0; i < deathInformationVOArray.length; i++) {
            deathInformationVOArray[i].setParentVO(this.getClass(), this);
            _deathInformationVOList.addElement(deathInformationVOArray[i]);
        }
    } //-- void setDeathInformationVO(edit.common.vo.DeathInformationVO) 

    /**
     * Method setEquityIndexHedgeDetailVO
     * 
     * @param index
     * @param vEquityIndexHedgeDetailVO
     */
    public void setEquityIndexHedgeDetailVO(int index, edit.common.vo.EquityIndexHedgeDetailVO vEquityIndexHedgeDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _equityIndexHedgeDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vEquityIndexHedgeDetailVO.setParentVO(this.getClass(), this);
        _equityIndexHedgeDetailVOList.setElementAt(vEquityIndexHedgeDetailVO, index);
    } //-- void setEquityIndexHedgeDetailVO(int, edit.common.vo.EquityIndexHedgeDetailVO) 

    /**
     * Method setEquityIndexHedgeDetailVO
     * 
     * @param equityIndexHedgeDetailVOArray
     */
    public void setEquityIndexHedgeDetailVO(edit.common.vo.EquityIndexHedgeDetailVO[] equityIndexHedgeDetailVOArray)
    {
        //-- copy array
        _equityIndexHedgeDetailVOList.removeAllElements();
        for (int i = 0; i < equityIndexHedgeDetailVOArray.length; i++) {
            equityIndexHedgeDetailVOArray[i].setParentVO(this.getClass(), this);
            _equityIndexHedgeDetailVOList.addElement(equityIndexHedgeDetailVOArray[i]);
        }
    } //-- void setEquityIndexHedgeDetailVO(edit.common.vo.EquityIndexHedgeDetailVO) 

    /**
     * Method setFreeAmountSets the value of field 'freeAmount'.
     * 
     * @param freeAmount the value of field 'freeAmount'.
     */
    public void setFreeAmount(java.math.BigDecimal freeAmount)
    {
        this._freeAmount = freeAmount;
        
        super.setVoChanged(true);
    } //-- void setFreeAmount(java.math.BigDecimal) 

    /**
     * Method setGeneralAccountArrayVO
     * 
     * @param index
     * @param vGeneralAccountArrayVO
     */
    public void setGeneralAccountArrayVO(int index, edit.common.vo.GeneralAccountArrayVO vGeneralAccountArrayVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _generalAccountArrayVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vGeneralAccountArrayVO.setParentVO(this.getClass(), this);
        _generalAccountArrayVOList.setElementAt(vGeneralAccountArrayVO, index);
    } //-- void setGeneralAccountArrayVO(int, edit.common.vo.GeneralAccountArrayVO) 

    /**
     * Method setGeneralAccountArrayVO
     * 
     * @param generalAccountArrayVOArray
     */
    public void setGeneralAccountArrayVO(edit.common.vo.GeneralAccountArrayVO[] generalAccountArrayVOArray)
    {
        //-- copy array
        _generalAccountArrayVOList.removeAllElements();
        for (int i = 0; i < generalAccountArrayVOArray.length; i++) {
            generalAccountArrayVOArray[i].setParentVO(this.getClass(), this);
            _generalAccountArrayVOList.addElement(generalAccountArrayVOArray[i]);
        }
    } //-- void setGeneralAccountArrayVO(edit.common.vo.GeneralAccountArrayVO) 

    /**
     * Method setGroupNoParticipantsSets the value of field
     * 'groupNoParticipants'.
     * 
     * @param groupNoParticipants the value of field
     * 'groupNoParticipants'.
     */
    public void setGroupNoParticipants(int groupNoParticipants)
    {
        this._groupNoParticipants = groupNoParticipants;
        
        super.setVoChanged(true);
        this._has_groupNoParticipants = true;
    } //-- void setGroupNoParticipants(int) 

    /**
     * Method setGroupRateSets the value of field 'groupRate'.
     * 
     * @param groupRate the value of field 'groupRate'.
     */
    public void setGroupRate(java.math.BigDecimal groupRate)
    {
        this._groupRate = groupRate;
        
        super.setVoChanged(true);
    } //-- void setGroupRate(java.math.BigDecimal) 

    /**
     * Method setGroupSetupVO
     * 
     * @param index
     * @param vGroupSetupVO
     */
    public void setGroupSetupVO(int index, edit.common.vo.GroupSetupVO vGroupSetupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _groupSetupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vGroupSetupVO.setParentVO(this.getClass(), this);
        _groupSetupVOList.setElementAt(vGroupSetupVO, index);
    } //-- void setGroupSetupVO(int, edit.common.vo.GroupSetupVO) 

    /**
     * Method setGroupSetupVO
     * 
     * @param groupSetupVOArray
     */
    public void setGroupSetupVO(edit.common.vo.GroupSetupVO[] groupSetupVOArray)
    {
        //-- copy array
        _groupSetupVOList.removeAllElements();
        for (int i = 0; i < groupSetupVOArray.length; i++) {
            groupSetupVOArray[i].setParentVO(this.getClass(), this);
            _groupSetupVOList.addElement(groupSetupVOArray[i]);
        }
    } //-- void setGroupSetupVO(edit.common.vo.GroupSetupVO) 

    /**
     * Method setInitCYAccumValueSets the value of field
     * 'initCYAccumValue'.
     * 
     * @param initCYAccumValue the value of field 'initCYAccumValue'
     */
    public void setInitCYAccumValue(java.math.BigDecimal initCYAccumValue)
    {
        this._initCYAccumValue = initCYAccumValue;
        
        super.setVoChanged(true);
    } //-- void setInitCYAccumValue(java.math.BigDecimal) 

    /**
     * Method setInsuredClassSets the value of field
     * 'insuredClass'.
     * 
     * @param insuredClass the value of field 'insuredClass'.
     */
    public void setInsuredClass(java.lang.String insuredClass)
    {
        this._insuredClass = insuredClass;
        
        super.setVoChanged(true);
    } //-- void setInsuredClass(java.lang.String) 

    /**
     * Method setInsuredDateOfBirthSets the value of field
     * 'insuredDateOfBirth'.
     * 
     * @param insuredDateOfBirth the value of field
     * 'insuredDateOfBirth'.
     */
    public void setInsuredDateOfBirth(java.lang.String insuredDateOfBirth)
    {
        this._insuredDateOfBirth = insuredDateOfBirth;
        
        super.setVoChanged(true);
    } //-- void setInsuredDateOfBirth(java.lang.String) 

    /**
     * Method setInsuredGenderSets the value of field
     * 'insuredGender'.
     * 
     * @param insuredGender the value of field 'insuredGender'.
     */
    public void setInsuredGender(java.lang.String insuredGender)
    {
        this._insuredGender = insuredGender;
        
        super.setVoChanged(true);
    } //-- void setInsuredGender(java.lang.String) 

    /**
     * Method setInsuredIssueAgeSets the value of field
     * 'insuredIssueAge'.
     * 
     * @param insuredIssueAge the value of field 'insuredIssueAge'.
     */
    public void setInsuredIssueAge(int insuredIssueAge)
    {
        this._insuredIssueAge = insuredIssueAge;
        
        super.setVoChanged(true);
        this._has_insuredIssueAge = true;
    } //-- void setInsuredIssueAge(int) 

    /**
     * Method setInsuredResidenceStateSets the value of field
     * 'insuredResidenceState'.
     * 
     * @param insuredResidenceState the value of field
     * 'insuredResidenceState'.
     */
    public void setInsuredResidenceState(java.lang.String insuredResidenceState)
    {
        this._insuredResidenceState = insuredResidenceState;
        
        super.setVoChanged(true);
    } //-- void setInsuredResidenceState(java.lang.String) 

    /**
     * Method setInsuredUnderwritingClassSets the value of field
     * 'insuredUnderwritingClass'.
     * 
     * @param insuredUnderwritingClass the value of field
     * 'insuredUnderwritingClass'.
     */
    public void setInsuredUnderwritingClass(java.lang.String insuredUnderwritingClass)
    {
        this._insuredUnderwritingClass = insuredUnderwritingClass;
        
        super.setVoChanged(true);
    } //-- void setInsuredUnderwritingClass(java.lang.String) 

    /**
     * Method setInvestmentAllocationOverrideVO
     * 
     * @param index
     * @param vInvestmentAllocationOverrideVO
     */
    public void setInvestmentAllocationOverrideVO(int index, edit.common.vo.InvestmentAllocationOverrideVO vInvestmentAllocationOverrideVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentAllocationOverrideVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vInvestmentAllocationOverrideVO.setParentVO(this.getClass(), this);
        _investmentAllocationOverrideVOList.setElementAt(vInvestmentAllocationOverrideVO, index);
    } //-- void setInvestmentAllocationOverrideVO(int, edit.common.vo.InvestmentAllocationOverrideVO) 

    /**
     * Method setInvestmentAllocationOverrideVO
     * 
     * @param investmentAllocationOverrideVOArray
     */
    public void setInvestmentAllocationOverrideVO(edit.common.vo.InvestmentAllocationOverrideVO[] investmentAllocationOverrideVOArray)
    {
        //-- copy array
        _investmentAllocationOverrideVOList.removeAllElements();
        for (int i = 0; i < investmentAllocationOverrideVOArray.length; i++) {
            investmentAllocationOverrideVOArray[i].setParentVO(this.getClass(), this);
            _investmentAllocationOverrideVOList.addElement(investmentAllocationOverrideVOArray[i]);
        }
    } //-- void setInvestmentAllocationOverrideVO(edit.common.vo.InvestmentAllocationOverrideVO) 

    /**
     * Method setInvestmentArrayVO
     * 
     * @param index
     * @param vInvestmentArrayVO
     */
    public void setInvestmentArrayVO(int index, edit.common.vo.InvestmentArrayVO vInvestmentArrayVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentArrayVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vInvestmentArrayVO.setParentVO(this.getClass(), this);
        _investmentArrayVOList.setElementAt(vInvestmentArrayVO, index);
    } //-- void setInvestmentArrayVO(int, edit.common.vo.InvestmentArrayVO) 

    /**
     * Method setInvestmentArrayVO
     * 
     * @param investmentArrayVOArray
     */
    public void setInvestmentArrayVO(edit.common.vo.InvestmentArrayVO[] investmentArrayVOArray)
    {
        //-- copy array
        _investmentArrayVOList.removeAllElements();
        for (int i = 0; i < investmentArrayVOArray.length; i++) {
            investmentArrayVOArray[i].setParentVO(this.getClass(), this);
            _investmentArrayVOList.addElement(investmentArrayVOArray[i]);
        }
    } //-- void setInvestmentArrayVO(edit.common.vo.InvestmentArrayVO) 

    /**
     * Method setLastLoanCapAmountSets the value of field
     * 'lastLoanCapAmount'.
     * 
     * @param lastLoanCapAmount the value of field
     * 'lastLoanCapAmount'.
     */
    public void setLastLoanCapAmount(java.math.BigDecimal lastLoanCapAmount)
    {
        this._lastLoanCapAmount = lastLoanCapAmount;
        
        super.setVoChanged(true);
    } //-- void setLastLoanCapAmount(java.math.BigDecimal) 

    /**
     * Method setLoanSettlementVO
     * 
     * @param index
     * @param vLoanSettlementVO
     */
    public void setLoanSettlementVO(int index, edit.common.vo.LoanSettlementVO vLoanSettlementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _loanSettlementVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vLoanSettlementVO.setParentVO(this.getClass(), this);
        _loanSettlementVOList.setElementAt(vLoanSettlementVO, index);
    } //-- void setLoanSettlementVO(int, edit.common.vo.LoanSettlementVO) 

    /**
     * Method setLoanSettlementVO
     * 
     * @param loanSettlementVOArray
     */
    public void setLoanSettlementVO(edit.common.vo.LoanSettlementVO[] loanSettlementVOArray)
    {
        //-- copy array
        _loanSettlementVOList.removeAllElements();
        for (int i = 0; i < loanSettlementVOArray.length; i++) {
            loanSettlementVOArray[i].setParentVO(this.getClass(), this);
            _loanSettlementVOList.addElement(loanSettlementVOArray[i]);
        }
    } //-- void setLoanSettlementVO(edit.common.vo.LoanSettlementVO) 

    /**
     * Method setNaturalDocPKSets the value of field
     * 'naturalDocPK'.
     * 
     * @param naturalDocPK the value of field 'naturalDocPK'.
     */
    public void setNaturalDocPK(long naturalDocPK)
    {
        this._naturalDocPK = naturalDocPK;
        
        super.setVoChanged(true);
        this._has_naturalDocPK = true;
    } //-- void setNaturalDocPK(long) 

    /**
     * Method setNetWithdrawalsToDateSets the value of field
     * 'netWithdrawalsToDate'.
     * 
     * @param netWithdrawalsToDate the value of field
     * 'netWithdrawalsToDate'.
     */
    public void setNetWithdrawalsToDate(java.math.BigDecimal netWithdrawalsToDate)
    {
        this._netWithdrawalsToDate = netWithdrawalsToDate;
        
        super.setVoChanged(true);
    } //-- void setNetWithdrawalsToDate(java.math.BigDecimal) 

    /**
     * Method setNetWithdrawalsYearToDateSets the value of field
     * 'netWithdrawalsYearToDate'.
     * 
     * @param netWithdrawalsYearToDate the value of field
     * 'netWithdrawalsYearToDate'.
     */
    public void setNetWithdrawalsYearToDate(java.math.BigDecimal netWithdrawalsYearToDate)
    {
        this._netWithdrawalsYearToDate = netWithdrawalsYearToDate;
        
        super.setVoChanged(true);
    } //-- void setNetWithdrawalsYearToDate(java.math.BigDecimal) 

    /**
     * Method setNumberTransfersPolYearToDateSets the value of
     * field 'numberTransfersPolYearToDate'.
     * 
     * @param numberTransfersPolYearToDate the value of field
     * 'numberTransfersPolYearToDate'.
     */
    public void setNumberTransfersPolYearToDate(int numberTransfersPolYearToDate)
    {
        this._numberTransfersPolYearToDate = numberTransfersPolYearToDate;
        
        super.setVoChanged(true);
        this._has_numberTransfersPolYearToDate = true;
    } //-- void setNumberTransfersPolYearToDate(int) 

    /**
     * Method setNumberWithdrawalsPolYearToDateSets the value of
     * field 'numberWithdrawalsPolYearToDate'.
     * 
     * @param numberWithdrawalsPolYearToDate the value of field
     * 'numberWithdrawalsPolYearToDate'.
     */
    public void setNumberWithdrawalsPolYearToDate(int numberWithdrawalsPolYearToDate)
    {
        this._numberWithdrawalsPolYearToDate = numberWithdrawalsPolYearToDate;
        
        super.setVoChanged(true);
        this._has_numberWithdrawalsPolYearToDate = true;
    } //-- void setNumberWithdrawalsPolYearToDate(int) 

    /**
     * Method setOverdueChargeRemainingVO
     * 
     * @param index
     * @param vOverdueChargeRemainingVO
     */
    public void setOverdueChargeRemainingVO(int index, edit.common.vo.OverdueChargeRemainingVO vOverdueChargeRemainingVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _overdueChargeRemainingVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vOverdueChargeRemainingVO.setParentVO(this.getClass(), this);
        _overdueChargeRemainingVOList.setElementAt(vOverdueChargeRemainingVO, index);
    } //-- void setOverdueChargeRemainingVO(int, edit.common.vo.OverdueChargeRemainingVO) 

    /**
     * Method setOverdueChargeRemainingVO
     * 
     * @param overdueChargeRemainingVOArray
     */
    public void setOverdueChargeRemainingVO(edit.common.vo.OverdueChargeRemainingVO[] overdueChargeRemainingVOArray)
    {
        //-- copy array
        _overdueChargeRemainingVOList.removeAllElements();
        for (int i = 0; i < overdueChargeRemainingVOArray.length; i++) {
            overdueChargeRemainingVOArray[i].setParentVO(this.getClass(), this);
            _overdueChargeRemainingVOList.addElement(overdueChargeRemainingVOArray[i]);
        }
    } //-- void setOverdueChargeRemainingVO(edit.common.vo.OverdueChargeRemainingVO) 

    /**
     * Method setOwnersPremiumTaxStateSets the value of field
     * 'ownersPremiumTaxState'.
     * 
     * @param ownersPremiumTaxState the value of field
     * 'ownersPremiumTaxState'.
     */
    public void setOwnersPremiumTaxState(java.lang.String ownersPremiumTaxState)
    {
        this._ownersPremiumTaxState = ownersPremiumTaxState;
        
        super.setVoChanged(true);
    } //-- void setOwnersPremiumTaxState(java.lang.String) 

    /**
     * Method setPremiumCalYearToDateSets the value of field
     * 'premiumCalYearToDate'.
     * 
     * @param premiumCalYearToDate the value of field
     * 'premiumCalYearToDate'.
     */
    public void setPremiumCalYearToDate(java.math.BigDecimal premiumCalYearToDate)
    {
        this._premiumCalYearToDate = premiumCalYearToDate;
        
        super.setVoChanged(true);
    } //-- void setPremiumCalYearToDate(java.math.BigDecimal) 

    /**
     * Method setPremiumSinceLast7PayDateSets the value of field
     * 'premiumSinceLast7PayDate'.
     * 
     * @param premiumSinceLast7PayDate the value of field
     * 'premiumSinceLast7PayDate'.
     */
    public void setPremiumSinceLast7PayDate(java.math.BigDecimal premiumSinceLast7PayDate)
    {
        this._premiumSinceLast7PayDate = premiumSinceLast7PayDate;
        
        super.setVoChanged(true);
    } //-- void setPremiumSinceLast7PayDate(java.math.BigDecimal) 

    /**
     * Method setPremiumToDateSets the value of field
     * 'premiumToDate'.
     * 
     * @param premiumToDate the value of field 'premiumToDate'.
     */
    public void setPremiumToDate(java.math.BigDecimal premiumToDate)
    {
        this._premiumToDate = premiumToDate;
        
        super.setVoChanged(true);
    } //-- void setPremiumToDate(java.math.BigDecimal) 

    /**
     * Method setPremiumYearToDateSets the value of field
     * 'premiumYearToDate'.
     * 
     * @param premiumYearToDate the value of field
     * 'premiumYearToDate'.
     */
    public void setPremiumYearToDate(java.math.BigDecimal premiumYearToDate)
    {
        this._premiumYearToDate = premiumYearToDate;
        
        super.setVoChanged(true);
    } //-- void setPremiumYearToDate(java.math.BigDecimal) 

    /**
     * Method setPriorCYAccumulatedValueSets the value of field
     * 'priorCYAccumulatedValue'.
     * 
     * @param priorCYAccumulatedValue the value of field
     * 'priorCYAccumulatedValue'.
     */
    public void setPriorCYAccumulatedValue(java.math.BigDecimal priorCYAccumulatedValue)
    {
        this._priorCYAccumulatedValue = priorCYAccumulatedValue;
        
        super.setVoChanged(true);
    } //-- void setPriorCYAccumulatedValue(java.math.BigDecimal) 

    /**
     * Method setRMDTaxYTDSets the value of field 'RMDTaxYTD'.
     * 
     * @param RMDTaxYTD the value of field 'RMDTaxYTD'.
     */
    public void setRMDTaxYTD(java.math.BigDecimal RMDTaxYTD)
    {
        this._RMDTaxYTD = RMDTaxYTD;
        
        super.setVoChanged(true);
    } //-- void setRMDTaxYTD(java.math.BigDecimal) 

    /**
     * Method setRiderSegmentVO
     * 
     * @param index
     * @param vRiderSegmentVO
     */
    public void setRiderSegmentVO(int index, edit.common.vo.RiderSegmentVO vRiderSegmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _riderSegmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vRiderSegmentVO.setParentVO(this.getClass(), this);
        _riderSegmentVOList.setElementAt(vRiderSegmentVO, index);
    } //-- void setRiderSegmentVO(int, edit.common.vo.RiderSegmentVO) 

    /**
     * Method setRiderSegmentVO
     * 
     * @param riderSegmentVOArray
     */
    public void setRiderSegmentVO(edit.common.vo.RiderSegmentVO[] riderSegmentVOArray)
    {
        //-- copy array
        _riderSegmentVOList.removeAllElements();
        for (int i = 0; i < riderSegmentVOArray.length; i++) {
            riderSegmentVOArray[i].setParentVO(this.getClass(), this);
            _riderSegmentVOList.addElement(riderSegmentVOArray[i]);
        }
    } //-- void setRiderSegmentVO(edit.common.vo.RiderSegmentVO) 

    /**
     * Method setSecondaryAnnuitantGenderSets the value of field
     * 'secondaryAnnuitantGender'.
     * 
     * @param secondaryAnnuitantGender the value of field
     * 'secondaryAnnuitantGender'.
     */
    public void setSecondaryAnnuitantGender(java.lang.String secondaryAnnuitantGender)
    {
        this._secondaryAnnuitantGender = secondaryAnnuitantGender;
        
        super.setVoChanged(true);
    } //-- void setSecondaryAnnuitantGender(java.lang.String) 

    /**
     * Method setSecondaryAnnuitantIssueAgeSets the value of field
     * 'secondaryAnnuitantIssueAge'.
     * 
     * @param secondaryAnnuitantIssueAge the value of field
     * 'secondaryAnnuitantIssueAge'.
     */
    public void setSecondaryAnnuitantIssueAge(int secondaryAnnuitantIssueAge)
    {
        this._secondaryAnnuitantIssueAge = secondaryAnnuitantIssueAge;
        
        super.setVoChanged(true);
        this._has_secondaryAnnuitantIssueAge = true;
    } //-- void setSecondaryAnnuitantIssueAge(int) 

    /**
     * Method setSecondaryAnnuitantTableRatingSets the value of
     * field 'secondaryAnnuitantTableRating'.
     * 
     * @param secondaryAnnuitantTableRating the value of field
     * 'secondaryAnnuitantTableRating'.
     */
    public void setSecondaryAnnuitantTableRating(java.lang.String secondaryAnnuitantTableRating)
    {
        this._secondaryAnnuitantTableRating = secondaryAnnuitantTableRating;
        
        super.setVoChanged(true);
    } //-- void setSecondaryAnnuitantTableRating(java.lang.String) 

    /**
     * Method setSettledOverdueCoiSets the value of field
     * 'settledOverdueCoi'.
     * 
     * @param settledOverdueCoi the value of field
     * 'settledOverdueCoi'.
     */
    public void setSettledOverdueCoi(java.math.BigDecimal settledOverdueCoi)
    {
        this._settledOverdueCoi = settledOverdueCoi;
        
        super.setVoChanged(true);
    } //-- void setSettledOverdueCoi(java.math.BigDecimal) 

    /**
     * Method setSupplementalSegmentVO
     * 
     * @param index
     * @param vSupplementalSegmentVO
     */
    public void setSupplementalSegmentVO(int index, edit.common.vo.SupplementalSegmentVO vSupplementalSegmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _supplementalSegmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vSupplementalSegmentVO.setParentVO(this.getClass(), this);
        _supplementalSegmentVOList.setElementAt(vSupplementalSegmentVO, index);
    } //-- void setSupplementalSegmentVO(int, edit.common.vo.SupplementalSegmentVO) 

    /**
     * Method setSupplementalSegmentVO
     * 
     * @param supplementalSegmentVOArray
     */
    public void setSupplementalSegmentVO(edit.common.vo.SupplementalSegmentVO[] supplementalSegmentVOArray)
    {
        //-- copy array
        _supplementalSegmentVOList.removeAllElements();
        for (int i = 0; i < supplementalSegmentVOArray.length; i++) {
            supplementalSegmentVOArray[i].setParentVO(this.getClass(), this);
            _supplementalSegmentVOList.addElement(supplementalSegmentVOArray[i]);
        }
    } //-- void setSupplementalSegmentVO(edit.common.vo.SupplementalSegmentVO) 

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
     * Method setTamraRetestVO
     * 
     * @param index
     * @param vTamraRetestVO
     */
    public void setTamraRetestVO(int index, edit.common.vo.TamraRetestVO vTamraRetestVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _tamraRetestVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vTamraRetestVO.setParentVO(this.getClass(), this);
        _tamraRetestVOList.setElementAt(vTamraRetestVO, index);
    } //-- void setTamraRetestVO(int, edit.common.vo.TamraRetestVO) 

    /**
     * Method setTamraRetestVO
     * 
     * @param tamraRetestVOArray
     */
    public void setTamraRetestVO(edit.common.vo.TamraRetestVO[] tamraRetestVOArray)
    {
        //-- copy array
        _tamraRetestVOList.removeAllElements();
        for (int i = 0; i < tamraRetestVOArray.length; i++) {
            tamraRetestVOArray[i].setParentVO(this.getClass(), this);
            _tamraRetestVOList.addElement(tamraRetestVOArray[i]);
        }
    } //-- void setTamraRetestVO(edit.common.vo.TamraRetestVO) 

    /**
     * Method setTaxDateOfBirthSets the value of field
     * 'taxDateOfBirth'.
     * 
     * @param taxDateOfBirth the value of field 'taxDateOfBirth'.
     */
    public void setTaxDateOfBirth(java.lang.String taxDateOfBirth)
    {
        this._taxDateOfBirth = taxDateOfBirth;
        
        super.setVoChanged(true);
    } //-- void setTaxDateOfBirth(java.lang.String) 

    /**
     * Method setWithdrawalsSinceLast7PayDateSets the value of
     * field 'withdrawalsSinceLast7PayDate'.
     * 
     * @param withdrawalsSinceLast7PayDate the value of field
     * 'withdrawalsSinceLast7PayDate'.
     */
    public void setWithdrawalsSinceLast7PayDate(java.math.BigDecimal withdrawalsSinceLast7PayDate)
    {
        this._withdrawalsSinceLast7PayDate = withdrawalsSinceLast7PayDate;
        
        super.setVoChanged(true);
    } //-- void setWithdrawalsSinceLast7PayDate(java.math.BigDecimal) 

    /**
     * Method setWithdrawalsTaxYTDSets the value of field
     * 'withdrawalsTaxYTD'.
     * 
     * @param withdrawalsTaxYTD the value of field
     * 'withdrawalsTaxYTD'.
     */
    public void setWithdrawalsTaxYTD(java.math.BigDecimal withdrawalsTaxYTD)
    {
        this._withdrawalsTaxYTD = withdrawalsTaxYTD;
        
        super.setVoChanged(true);
    } //-- void setWithdrawalsTaxYTD(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.NaturalDocVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.NaturalDocVO) Unmarshaller.unmarshal(edit.common.vo.NaturalDocVO.class, reader);
    } //-- edit.common.vo.NaturalDocVO unmarshal(java.io.Reader) 

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