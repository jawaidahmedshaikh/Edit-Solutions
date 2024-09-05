/*
 * User: gfrosti
 * Date: Oct 3, 2003
 * Time: 2:12:18 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import accounting.AccountingDetail;

import agent.CheckAdjustment;

import contract.AnnualizedSubBucket;
import contract.ContractClientAllocation;
import contract.Deposits;
import contract.Investment;
import contract.InvestmentAllocation;
import contract.Life;
import contract.Segment;

import contract.dm.dao.InvestmentDAO;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.EDITMap;
import edit.common.exceptions.EDITEngineException;
import edit.common.exceptions.EDITEventException;
import edit.common.vo.*;

import edit.services.db.CRUD;
import edit.services.db.CRUDEntityI;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import engine.Area;
import engine.AreaValue;
import engine.ProductStructure;
import engine.Fee;
import engine.FeeDescription;
import engine.FilteredFund;
import engine.Fund;

import event.dm.dao.DAOFactory;
import event.dm.dao.EDITTrxDAO;

import event.financial.client.trx.ClientTrx;
import event.financial.contract.trx.ContractEvent;
import event.financial.group.trx.GroupTrx;
import event.financial.group.strategy.*;

import fission.utility.*;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import staging.*;


public class EDITTrx extends HibernateEntity implements IStaging
{

    public static final String TRANSACTIONTYPECT_ADDRESS_CHANGE         = "ADC";
    public static final String TRANSACTIONTYPECT_ADJUST_UP              = "AU";
    public static final String TRANSACTIONTYPECT_ADJUST_DOWN            = "AD";
    public static final String TRANSACTIONTYPECT_BILL                   = "BI";
    public static final String TRANSACTIONTYPECT_BILLING_CHANGE         = "BC";
    public static final String TRANSACTIONTYPECT_BILLING_CHANGE_DEDUCTION_AMT = "BCDA";
    public static final String TRANSACTIONTYPECT_BONUSCHECK             = "BCK";
    public static final String TRANSACTIONTYPECT_COMMISSIONADJUSTMENT   = "CA";
    public static final String TRANSACTIONTYPECT_COMPLEXCHANGE          = "CC";
    public static final String TRANSACTIONTYPECT_CALENDARYEAREND 		= "CY";
    public static final String TRANSACTIONTYPECT_DEATH                  = "DE";
    public static final String TRANSACTIONTYPECT_DEATHPENDING           = "DP";
    public static final String TRANSACTIONTYPECT_FACEDECREASE           = "FD";
    public static final String TRANSACTIONTYPECT_FACEINCREASE           = "FI";
    public static final String TRANSACTIONTYPECT_FULLSURRENDER          = "FS";
    public static final String TRANSACTIONTYPECT_FREE_LOOK_TRANSFER     = "FT";
    public static final String TRANSACTIONTYPECT_ISSUE                  = "IS";
    public static final String TRANSACTIONTYPECT_LAPSE                  = "LA";
    public static final String TRANSACTIONTYPECT_LOAN_BILL              = "LB";
    public static final String TRANSACTIONTYPECT_LOAN_CAPITALIZATION    = "LC";
    public static final String TRANSACTIONTYPECT_LOAN                   = "LO";
    public static final String TRANSACTIONTYPECT_LAPSE_PENDING          = "LP";
    public static final String TRANSACTIONTYPECT_LOAN_REPAYMENT         = "LR";
    public static final String TRANSACTIONTYPECT_LUMPSUM                = "LS";
    public static final String TRANSACTIONTYPECT_MATURITY               = "MA";
    public static final String TRANSACTIONTYPECT_MODALDEDUCTION         = "MD";
    public static final String TRANSACTIONTYPECT_MONTHLYFEE             = "MF";
    public static final String TRANSACTIONTYPECT_MONTHLY_COLLATERALIZATION = "ML";
    public static final String TRANSACTIONTYPECT_MONTHLYINTEREST        = "MI";
    public static final String TRANSACTIONTYPECT_MONTHLIVERSARY 		= "MV";
    public static final String TRANSACTIONTYPECT_NBREFUND               = "NBR";
    public static final String TRANSACTIONTYPECT_NOTTAKEN               = "NT";
    public static final String TRANSACTIONTYPECT_POLICYYEAREND          = "PE";
    public static final String TRANSACTIONTYPECT_PAYOUT                 = "PO";
    public static final String TRANSACTIONTYPECT_PREMIUM                = "PY";
    public static final String TRANSACTIONTYPECT_RMDCORRESPONDENCE      = "RC";
    public static final String TRANSACTIONTYPECT_REINSURANCE_CHECK      = "RCK";
    public static final String TRANSACTIONTYPECT_RENEWAL                = "RN";
    public static final String TRANSACTIONTYPECT_RMDWITHDRAWAL          = "RW";
    public static final String TRANSACTIONTYPECT_SUBMIT                 = "SB";    // Works exactly like Issue but want to know if it was due to a batch submission
    public static final String TRANSACTIONTYPECT_SUPPLEMENTALLUMPSUM    = "SLS";
    public static final String TRANSACTIONTYPECT_SURRENDER_OVERLOAN     = "SRO";   // Works exactly like FullSurrender but want to know if due to overloan
    public static final String TRANSACTIONTYPECT_STATEMENT              = "ST";
    public static final String TRANSACTIONTYPECT_SYSTEMATIC_WITHDRAWAL  = "SW";
    public static final String TRANSACTIONTYPECT_TRANSFER               = "TF";
    public static final String TRANSACTIONTYPECT_WITHDRAWAL             = "WI";
    public static final String TRANSACTIONTYPECT_RIDER_CLAIM            = "RCL";
    public static final String TRANSACTIONTYPECT_CLAIM_PAYOUT           = "CPO";
    public static final String TRANSACTIONTYPECT_PREMIUM_LOAN           = "PL";
    public static final String TRANSACTIONTYPECT_BC_DED_AMT             = "BCDA";

    public static final String TRANSACTIONTYPECT_SERIES_TRANSFER = "STF";
    public static final String TRANSACTIONTYPECT_HF_TRANSFER_AMT = "HFTA";
    public static final String TRANSACTIONTYPECT_HF_TRANSFER_PCT = "HFTP";
    public static final String TRANSACTIONTYPECT_HF_SERIES_AMT = "HFSA";
    public static final String TRANSACTIONTYPECT_HF_SERIES_PCT = "HFSP";
    public static final String TRANSACTIONTYPECT_HF_DEATH = "HDTH";
    public static final String TRANSACTIONTYPECT_HF_REDEMPTION = "HREM";
    public static final String TRANSACTIONTYPECT_HF_LOAN = "HLOAN";

    public static final String PROCESSNAME_ISSUE = "Issue";
    public static final String PROCESSNAME_SUBMIT = "Submit";
    public static final String PROCESSNAME_RIDER_CLAIM = "RiderClaim";
    public static final String PROCESSNAME_COMMIT = "Commit";

    public static final String[] withdrawalTrxTypes = new String[] {"WI", "HREM", "LO", "HLOAN", "CPO"};
    public static final String[] transferTrxTypes = new String[] {"TF", "HFTA", "HFTP", "STF", "HFSA", "HFSP"};
    public static final String[] premiumTrxTypes = new String[] {"PI", "PY", "VT", "LR", "PL"};
    public static final String[] moneyMoveSurrenderTrxTypes = new String[] {"FS", EDITTrx.TRANSACTIONTYPECT_SURRENDER_OVERLOAN};
    public static final String[] surrenderTrxTypes = new String[] {"FS", "WI", "HREM", EDITTrx.TRANSACTIONTYPECT_SURRENDER_OVERLOAN};
    public static final String[] deathTrxTypes = new String[] {"DE", "HDTH"};
    public static final String[] newIssueTrxTypes = new String[] {"TU"};
    public static final String[] coiTrxTypes = new String[] {"MD"};
    public static final String[] hedgeFundTrxTypes = new String[] {"HFTA","HFTP","HDTH","HREM", "HLOAN", "HFSA", "HFSP"};
    public static final String[] scheduledEventTypes = new String[] {"PO", "DC", "IN", "ST", "BI", "CY", "PE", "SW", "MD", "RW", "MF"};
    public static final String[] fullTerminationTrxTypes = new String[] {"NT", "FS"};
    public static final String[] disbursementDisplayTrxTypes = new String[] {"LS", "SLS", "NT", "PO", "FP", "SW", "WI", "RW",
                                                                             "IW", "FS", "MA", "LO", "CPO", "HDTH", "HREM", "HLOAN", "SRO", "NBR"};
    public static final String TRANSFERTYPE_SERIES_TRANSFER = "SeriesTransfer";
    public static final String TRANSFERTYPE_REDEMPTION_REMOVAL = "RedemptionRemoval";
    public static final String TRANSFERTYPE_SUBSCRIPTION = "Subscription";

    public static final String STATUS_NATURAL = "N";
    public static final String STATUS_APPLY = "A";
    public static final String STATUS_UNDO = "U";
    public static final String STATUS_REVERSAL = "R";

    public static final String PENDINGSTATUS_PENDING = "P";
    public static final String PENDINGSTATUS_HISTORY = "H";
    public static final String PENDINGSTATUS_TERMINATED = "T";

    public static final String REVERSAL_REASON_NONSUFFICIENTFUNDS = "NSF";

    private CRUDEntityI crudEntityImpl;

    private EDITTrxVO editTrxVO;
    private EDITTrxVO originalEditTrxVO;
    private EDITTrxVO hftEditTrxVO;
    private EDITTrxVO[] editTrxVOs;
    private String operator;
    private long segmentPK;

    private ClientSetup clientSetup;
    private CheckAdjustment checkAdjustment;
    private Set<EDITTrxHistory> editTrxHistories;
    private Set deposits;
    private Set annualizedSubBuckets;
    private Set editTrxCorrespondences;
    private Set<OverdueCharge> overdueCharges;
    private Set<OverdueChargeSettled> overdueChargesSettled;
    private Set<AccountingDetail> accountingDetails;
    private Set<PremiumDue> premiumDues;


    public enum RemovalTransaction {MD, FS, DE, NT, WI, SW, CY, PE, MF, TU, FD, LS, SLS, CC, ST, HDTH, ML, LO, CPO, PO, FP,  MA,  HREM, HLOAN, SRO, NBR};


    public enum PartialRemovalTransaction {WI, SW, RW};

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;

    public EDITTrx()
    {
        this.editTrxVO = new EDITTrxVO();
        init();
    }

    /**
     * Constructor
     * NOTE: The CA trx required changes to this constructor.  It is passing in a pk of zero, when initialized through hibernate. 
     * @param editTrxPK
     */
    public EDITTrx(long editTrxPK)
    {
        EDITTrxVO editTrxVO = null;
        if (editTrxPK != 0)
        {
            editTrxVO = DAOFactory.getEDITTrxDAO().findByEDITTrxPK(editTrxPK)[0];
        }
        else
        {
            editTrxVO = new EDITTrxVO();
            editTrxVO.setEDITTrxPK(editTrxPK);
        }

        this.editTrxVO = editTrxVO;

        init();
    }

    public EDITTrx(long editTrxPK, CRUD crud)
    {
        this(DAOFactory.getEDITTrxDAO().findByEDITTrxPK(editTrxPK)[0]);
        init();
    }
    public EDITTrx(EDITTrxVO editTrxVO)
    {
        init();
        this.editTrxVO = editTrxVO;
    }

    public EDITTrx(long origEditTrxPK, long editTrxPK, String operator) throws Exception
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(InvestmentAllocationOverrideVO.class);
        voInclusionList.add(InvestmentVO.class);
        voInclusionList.add(InvestmentAllocationVO.class);
        voInclusionList.add(FilteredFundVO.class);
        voInclusionList.add(FundVO.class);
        voInclusionList.add(SegmentVO.class);

        this.originalEditTrxVO = eventComponent.composeEDITTrxVOByEDITTrxPK(origEditTrxPK, voInclusionList);
        this.hftEditTrxVO = eventComponent.composeEDITTrxVOByEDITTrxPK(editTrxPK, voInclusionList);
        this.operator = operator;

        init();
    }

    public EDITTrx(EDITTrxVO[] editTrxVOs, String operator, long segmentPK)
    {
        init();
        this.editTrxVOs = editTrxVOs;
        this.operator = operator;
        this.segmentPK = segmentPK;
    }

    /**
     * Guarantees that HashSets are properly instantiated.
     */
    private final void init()
    {
        if (editTrxCorrespondences == null)
        {
            editTrxCorrespondences = new HashSet();
        }

        if (editTrxHistories == null)
        {
            editTrxHistories = new HashSet<EDITTrxHistory>();
        }

        if (deposits == null)
        {
            deposits = new HashSet();
        }

        if (annualizedSubBuckets == null)
        {
            annualizedSubBuckets = new HashSet();
        }

        if (overdueCharges == null)
        {
            overdueCharges = new HashSet<OverdueCharge>();
        }
        
        if (overdueChargesSettled == null)
        {
            overdueChargesSettled = new HashSet<OverdueChargeSettled>();
        }        
        
        if (accountingDetails == null)
        {
            accountingDetails = new HashSet<AccountingDetail>();
        }

        if (premiumDues == null)
        {
            premiumDues = new HashSet<PremiumDue>();
        }
    }

    public Set getAnnualizedSubBuckets()
    {
        return annualizedSubBuckets;
    }

    public void setAnnualizedSubBuckets(Set annualizedSubBuckets)
    {
        this.annualizedSubBuckets = annualizedSubBuckets;
    }

    /**
     * Getter.
     * @return
     */
    public Set getDeposits()
    {
        return deposits;
    }

    /**
     * Setter.
     * @param deposits
     */
    public void setDeposits(Set deposits)
    {
        this.deposits = deposits;
    }

    /**
     * Getter.
     * @return
     */
    public EDITTrxHistory getEDITTrxHistory()
    {
        EDITTrxHistory editTrxHistory = getEDITTrxHistories().isEmpty() ? null : (EDITTrxHistory) getEDITTrxHistories().iterator().next();

        return editTrxHistory;
    }

    /**
     * Setter.
     * @param editTrxHistory
     */
    public void setEDITTrxHistory(EDITTrxHistory editTrxHistory)
    {
        getEDITTrxHistories().add(editTrxHistory);
    }
    
    
    /**
     * Getter.
     * @return
     */
    public Set<EDITTrxHistory> getEDITTrxHistories()
    {
        return editTrxHistories;
    }

    /**
     * Setter.
     * @param editTrxHistories
     */
    public void setEDITTrxHistories(Set<EDITTrxHistory> editTrxHistories)
    {
        this.editTrxHistories = editTrxHistories;
    }

    /**
     * Getter.
     * @return
     */
    public Set getEDITTrxCorrespondences()
    {
        return editTrxCorrespondences;
    }

    /**
     * Setter.
     * @param editTrxCorrespondences
     */
    public void setEDITTrxCorrespondences(Set editTrxCorrespondences)
    {
        this.editTrxCorrespondences = editTrxCorrespondences;
    }

    /**
     * Getter.
     * @return
     */
    public Set<OverdueCharge> getOverdueCharges()
    {
        return overdueCharges;
    }

    /**
     * Setter.
     * @param overdueCharges
     */
    public void setOverdueCharges(Set<OverdueCharge> overdueCharges)
    {
        this.overdueCharges = overdueCharges;
    }
    
    /**
     * Setter.
     * @param accountingDetails
     */
    public void setAccountingDetails(Set<AccountingDetail> accountingDetails)
    {
        this.accountingDetails = accountingDetails;
    }

    /**
     * Getter.
     * @return
     */
    public Set<AccountingDetail> getAccountingDetails()
    {
        return accountingDetails;
    }

    /**
     * Setter.
     * @return
     */
    public Set<PremiumDue> getPremiumDues()
    {
        return premiumDues;
    }

    /**
     * Getter.
     * @param premiumDues
     */
    public void setPremiumDues(Set<PremiumDue> premiumDues)
    {
        this.premiumDues = premiumDues;
    }

    /**
     * Getter.
     * @return
     */
    public ClientSetup get_ClientSetup()
    {
        return new ClientSetup(editTrxVO.getClientSetupFK());
    }

    /**
     * Setter.
     * @param clientSetup
     */
    public void setClientSetup(ClientSetup clientSetup)
    {
        this.clientSetup = clientSetup;
    }

    /**
     * Getter.
     * @return
     */
    public String getAccountingPeriod()
    {
        return editTrxVO.getAccountingPeriod();
    }

    //-- java.lang.String getAccountingPeriod()

    /**
     * Getter.
     * @return
     */
    public String getOriginalAccountingPeriod()
    {
        return editTrxVO.getOriginalAccountingPeriod();
    }

    /**
    * Getter.
    * @return
    */
    public String getAdvanceNotificationOverride()
    {
        return editTrxVO.getAdvanceNotificationOverride();
    }

    //-- java.lang.String getAdvanceNotificationOverride()

    /**
    * Getter.
    * @return
    */
    public EDITBigDecimal getBonusCommissionAmount()
    {
        return SessionHelper.getEDITBigDecimal(editTrxVO.getBonusCommissionAmount());
    }

    /**
    * Getter.
    * @return
    */
    public EDITBigDecimal getOriginalAmount()
    {
        return SessionHelper.getEDITBigDecimal(editTrxVO.getOriginalAmount());
    }
    //-- java.math.BigDecimal getOriginalAmount()

    /**
    * Getter.
    * @return
    */
    public Long getClientSetupFK()
    {
        return SessionHelper.getPKValue(editTrxVO.getClientSetupFK());
    }

    //-- long getClientSetupFK()

    /**
    * Getter.
    * @return
    */
    public Long getSelectedRiderPK()
    {
        return SessionHelper.getPKValue(editTrxVO.getSelectedRiderPK());
    }
    //-- long getSelectedRiderPK()



    /**
     * Getter.
     * @return
     */
     public Long getCheckAdjustmentFK()
     {
         return SessionHelper.getPKValue(editTrxVO.getCheckAdjustmentFK());
     }

    /**
    * Getter.
    * @return
    */
    public String getCommissionStatus()
    {
        return editTrxVO.getCommissionStatus();
    }

    //-- java.lang.String getCommissionStatus()

    /**
    * Getter.
    * @return
    */
    public EDITDate getDateContributionExcess()
    {
        return SessionHelper.getEDITDate(editTrxVO.getDateContributionExcess());
    }

    //-- java.lang.String getDateContributionExcess()

    /**
    * Getter.
    * @return
    */
    public EDITDate getDueDate()
    {
        return SessionHelper.getEDITDate(editTrxVO.getDueDate());
    }

    /**
     * Getter.
     * @return
     */
     public Long getTerminationTrxFK()
     {
         return SessionHelper.getPKValue(editTrxVO.getTerminationTrxFK());
     }
     
    //-- java.lang.String getDueDate()

    /**
    * Getter.
    * @return
    */
    public Long getEDITTrxPK()
    {
        return SessionHelper.getPKValue(editTrxVO.getEDITTrxPK());
    }

    //-- long getEDITTrxPK()

    /**
    * Getter.
    * @return
    */
    public EDITBigDecimal getExcessBonusCommissionAmount()
    {
        return SessionHelper.getEDITBigDecimal(editTrxVO.getExcessBonusCommissionAmount());
    }

    //-- java.math.BigDecimal getExcessBonusCommissionAmount()

    /**
    * Getter.
    * @return
    */
    public String getLookBackInd()
    {
        return editTrxVO.getLookBackInd();
    }

    //-- java.lang.String getLookBackInd()

    /**
    * Getter.
    * @return
    */
    public EDITDateTime getMaintDateTime()
    {
        return SessionHelper.getEDITDateTime(editTrxVO.getMaintDateTime());
    }

    //-- java.lang.String getMaintDateTime()

    /**
    * Getter.
    * @return
    */
    public String getNoAccountingInd()
    {
        return editTrxVO.getNoAccountingInd();
    }

    //-- java.lang.String getNoAccountingInd()

    /**
    * Getter.
    * @return
    */
    public String getNoCommissionInd()
    {
        return editTrxVO.getNoCommissionInd();
    }

    //-- java.lang.String getNoCommissionInd()

    /**
    * Getter.
    * @return
    */
    public String getZeroLoadInd()
    {
        return editTrxVO.getZeroLoadInd();
    }

    //-- java.lang.String getZeroLoadInd()

    /**
    * Getter.
    * @return
    */
    public String getNoCorrespondenceInd()
    {
        return editTrxVO.getNoCorrespondenceInd();
    }

    //-- java.lang.String getNoCorrespondenceInd()

    /**
     * Setter
     * @param billAmtEditOverrideInd
     */
    public void setBillAmtEditOverrideInd(String billAmtEditOverrideInd)
    {
        editTrxVO.setBillAmtEditOverrideInd(billAmtEditOverrideInd);
    }
    
    /**
     * Setter
     * @param terminationTrxFK
     */
    public void setTerminationTrxFK(Long terminationTrxFK)
    {
        editTrxVO.setTerminationTrxFK(SessionHelper.getPKValue(terminationTrxFK));
    }

    /**
     * Getter
     * @return
     */
    public String getBillAmtEditOverrideInd()
    {
        return editTrxVO.getBillAmtEditOverrideInd();
    }

    /**
    * Getter.
    * @return
    */
    public EDITBigDecimal getNotificationAmount()
    {
        return SessionHelper.getEDITBigDecimal(editTrxVO.getNotificationAmount());
    }

    //-- java.math.BigDecimal getNotificationAmount()

    /**
    * Getter.
    * @return
    */
    public EDITBigDecimal getNotificationAmountReceived()
    {
        return SessionHelper.getEDITBigDecimal(editTrxVO.getNotificationAmountReceived());
    }

    //-- java.math.BigDecimal getNotificationAmountReceived()

    /**
    * Getter.
    * @return
    */
    public String getOperator()
    {
        return editTrxVO.getOperator();
    }

    //-- java.lang.String getOperator()

    /**
    * Getter.
    * @return
    */
    public Long getOriginatingTrxFK()
    {
        return SessionHelper.getPKValue(editTrxVO.getOriginatingTrxFK());
    }

    //-- long getOriginatingTrxFK()

    /**
    * Getter.
    * @return
    */
    public String getPendingStatus()
    {
        return editTrxVO.getPendingStatus();
    }

    //-- java.lang.String getPendingStatus()

    /**
    * Getter.
    * @return
    */
    public Long getReapplyEDITTrxFK()
    {
        return SessionHelper.getPKValue(editTrxVO.getReapplyEDITTrxFK());
    }

    //-- long getReapplyEDITTrxFK()

    /**
    * Getter.
    * @return
    */
    public String getReinsuranceStatus()
    {
        return editTrxVO.getReinsuranceStatus();
    }

    //-- java.lang.String getReinsuranceStatus()

    /**
    * Getter.
    * @return
    */
    public int getSequenceNumber()
    {
        return editTrxVO.getSequenceNumber();
    }

    //-- int getSequenceNumber()

    /**
    * Getter.
    * @return
    */
    public String getStatus()
    {
        return editTrxVO.getStatus();
    }

    //-- java.lang.String getStatus()

    /**
    * Getter.
    * @return
    */
    public int getTaxYear()
    {
        return editTrxVO.getTaxYear();
    }

    //-- int getTaxYear()

    /**
    * Getter.
    * @return
    */
    public String getTransactionTypeCT()
    {
        return editTrxVO.getTransactionTypeCT();
    }

    //-- java.lang.String getTransactionTypeCT()

    /**
    * Getter.
    * @return
    */
    public String getTransferTypeCT()
    {
        return editTrxVO.getTransferTypeCT();
    }

    //-- java.lang.String getTransferTypeCT()

    /**
     * Getter.
     * @return
     */
    public String getAuthorizedSignatureCT()
    {
        return editTrxVO.getAuthorizedSignatureCT();
    }

    /**
    * Getter.
    * @return
    */
    public String getTrxIsRescheduledInd()
    {
        return editTrxVO.getTrxIsRescheduledInd();
    }

    //-- java.lang.String getTrxIsRescheduledInd()

    /**
     * Getter.
     * @return
     */
    public String getReversalReasonCodeCT()
    {
        return editTrxVO.getReversalReasonCodeCT();
    }

    //-- java.lang.String getTrxIsRescheduledInd()

	/**
     * Getter
     * @return
     */
    public EDITBigDecimal getTrxPercent()
    {
        return SessionHelper.getEDITBigDecimal(editTrxVO.getTrxPercent());
    } //-- java.math.BigDecimal getTrxPercent()
    
    /**
     * Setter.
     * @param accountingPeriod
     */
    public void setAccountingPeriod(String accountingPeriod)
    {
        editTrxVO.setAccountingPeriod(accountingPeriod);
    }

    //-- void setAccountingPeriod(java.lang.String)

    /**
     * Setter.
     * @param originalAccountingPeriod
     */
    public void setOriginalAccountingPeriod(String originalAccountingPeriod)
    {
        editTrxVO.setOriginalAccountingPeriod(originalAccountingPeriod);
    }

    /**
    * Setter.
    * @param advanceNotificationOverride
    */
    public void setAdvanceNotificationOverride(String advanceNotificationOverride)
    {
        editTrxVO.setAdvanceNotificationOverride(advanceNotificationOverride);
    }

    //-- void setAdvanceNotificationOverride(java.lang.String)

    /**
    * Setter.
    * @param bonusCommissionAmount
    */
    public void setBonusCommissionAmount(EDITBigDecimal bonusCommissionAmount)
    {
        editTrxVO.setBonusCommissionAmount(SessionHelper.getEDITBigDecimal(bonusCommissionAmount));
    }

    //-- void setBonusCommissionAmount(java.math.BigDecimal)

    /**
    * Setter.
    * @param clientSetupFK
    */
    public void setClientSetupFK(Long clientSetupFK)
    {
        editTrxVO.setClientSetupFK(SessionHelper.getPKValue(clientSetupFK));
    }

    /**
    * Setter.
    * @param originalAmount 
    */
    public void setOriginalAmount(EDITBigDecimal originalAmount)
    {
        editTrxVO.setOriginalAmount(SessionHelper.getEDITBigDecimal(originalAmount));
    }

    /**
    * Setter.
    * @param selectedRiderPK
    */
    public void setSelectedRiderPK(Long selectedRiderPK)
    {
        editTrxVO.setSelectedRiderPK(SessionHelper.getPKValue(selectedRiderPK));
    }

    //-- void setClientSetupFK(long)

    /**
    * Setter.
    * @param checkAdjustmentFK
    */
    public void setCheckAdjustmentFK(Long checkAdjustmentFK)
    {
        editTrxVO.setCheckAdjustmentFK(SessionHelper.getPKValue(checkAdjustmentFK));
    }


    /**
    * Setter.
    * @param commissionStatus
    */
    public void setCommissionStatus(String commissionStatus)
    {
        editTrxVO.setCommissionStatus(commissionStatus);
    }

    //-- void setCommissionStatus(java.lang.String)

    /**
    * Setter.
    * @param dateContributionExcess
    */
    public void setDateContributionExcess(EDITDate dateContributionExcess)
    {
        editTrxVO.setDateContributionExcess(SessionHelper.getEDITDate(dateContributionExcess));
    }

    //-- void setDateContributionExcess(java.lang.String)

    /**
    * Setter.
    * @param dueDate
    */
    public void setDueDate(EDITDate dueDate)
    {
        editTrxVO.setDueDate(SessionHelper.getEDITDate(dueDate));
    }

    //-- void setDueDate(java.lang.String)

    /**
    * Setter.
    * @param editTrxPK
    */
    public void setEDITTrxPK(Long editTrxPK)
    {
        editTrxVO.setEDITTrxPK(SessionHelper.getPKValue(editTrxPK));
    }

    //-- void setEDITTrxPK(long)

    /**
    * Setter.
    * @param effectiveDate
    */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        editTrxVO.setEffectiveDate(SessionHelper.getEDITDate(effectiveDate));
    }

    //-- void setEffectiveDate(java.lang.String)

    /**
    * Setter.
    * @param excessBonusCommissionAmount
    */
    public void setExcessBonusCommissionAmount(EDITBigDecimal excessBonusCommissionAmount)
    {
        editTrxVO.setExcessBonusCommissionAmount(SessionHelper.getEDITBigDecimal(excessBonusCommissionAmount));
    }

    //-- void setExcessBonusCommissionAmount(java.math.BigDecimal)

    /**
    * Setter.
    * @param lookBackInd
    */
    public void setLookBackInd(String lookBackInd)
    {
        editTrxVO.setLookBackInd(lookBackInd);
    }

    //-- void setLookBackInd(java.lang.String)

    /**
    * Setter.
    * @param maintDateTime
    */
    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        editTrxVO.setMaintDateTime(SessionHelper.getEDITDateTime(maintDateTime));
    }

    //-- void setMaintDateTime(java.lang.String)

    /**
    * Setter.
    * @param noAccountingInd
    */
    public void setNoAccountingInd(String noAccountingInd)
    {
        editTrxVO.setNoAccountingInd(noAccountingInd);
    }

    //-- void setNoAccountingInd(java.lang.String)

    /**
    * Setter.
    * @param noCommissionInd
    */
    public void setNoCommissionInd(String noCommissionInd)
    {
        editTrxVO.setNoCommissionInd(noCommissionInd);
    }

    //-- void setNoCommissionInd(java.lang.String)

    /**
    * Setter.
    * @param zeroLoadInd
    */
    public void setZeroLoadInd(String zeroLoadInd)
    {
        editTrxVO.setZeroLoadInd(zeroLoadInd);
    }

    //-- void setZeroLoadInd(java.lang.String)

    /**
    * Setter.
    * @param noCorrespondenceInd
    */
    public void setNoCorrespondenceInd(String noCorrespondenceInd)
    {
        editTrxVO.setNoCorrespondenceInd(noCorrespondenceInd);
    }

    //-- void setNoCorrespondenceInd(java.lang.String)

    /**
    * Setter.
    * @param notificationAmount
    */
    public void setNotificationAmount(EDITBigDecimal notificationAmount)
    {
        editTrxVO.setNotificationAmount(SessionHelper.getEDITBigDecimal(notificationAmount));
    }

    //-- void setNotificationAmount(java.math.BigDecimal)

    /**
    * Setter.
    * @param notificationAmountReceived
    */
    public void setNotificationAmountReceived(EDITBigDecimal notificationAmountReceived)
    {
        editTrxVO.setNotificationAmountReceived(SessionHelper.getEDITBigDecimal(notificationAmountReceived));
    }

    //-- void setNotificationAmountReceived(java.math.BigDecimal)

    /**
    * Setter.
    * @param operator
    */
    public void setOperator(String operator)
    {
        editTrxVO.setOperator(operator);
    }

    //-- void setOperator(java.lang.String)

    /**
    * Setter.
    * @param originatingTrxFK
    */
    public void setOriginatingTrxFK(Long originatingTrxFK)
    {
        editTrxVO.setOriginatingTrxFK(SessionHelper.getPKValue(originatingTrxFK));
    }

    //-- void setOriginatingTrxFK(long)

    /**
    * Setter.
    * @param pendingStatus
    */
    public void setPendingStatus(String pendingStatus)
    {
        editTrxVO.setPendingStatus(pendingStatus);
    }

    //-- void setPendingStatus(java.lang.String)

    /**
    * Setter.
    * @param reapplyEDITTrxFK
    */
    public void setReapplyEDITTrxFK(Long reapplyEDITTrxFK)
    {
        editTrxVO.setReapplyEDITTrxFK(SessionHelper.getPKValue(reapplyEDITTrxFK));
    }

    //-- void setReapplyEDITTrxFK(long)

    /**
    * Setter.
    * @param reinsuranceStatus
    */
    public void setReinsuranceStatus(String reinsuranceStatus)
    {
        editTrxVO.setReinsuranceStatus(reinsuranceStatus);
    }

    //-- void setReinsuranceStatus(java.lang.String)

    /**
    * Setter.
    * @param sequenceNumber
    */
    public void setSequenceNumber(int sequenceNumber)
    {
        editTrxVO.setSequenceNumber(sequenceNumber);
    }

    //-- void setSequenceNumber(int)

    /**
    * Setter.
    * @param status
    */
    public void setStatus(String status)
    {
        editTrxVO.setStatus(status);
    }

    //-- void setStatus(java.lang.String)

    /**
    * Setter.
    * @param taxYear
    */
    public void setTaxYear(int taxYear)
    {
        editTrxVO.setTaxYear(taxYear);
    }

    //-- void setTaxYear(int)

    /**
    * Setter.
    * @param transactionTypeCT
    */
    public void setTransactionTypeCT(String transactionTypeCT)
    {
        editTrxVO.setTransactionTypeCT(transactionTypeCT);
    }

    //-- void setTransactionTypeCT(java.lang.String)

    /**
    * Setter.
    * @param transferTypeCT
    */
    public void setTransferTypeCT(String transferTypeCT)
    {
        editTrxVO.setTransferTypeCT(transferTypeCT);
    }
    /**
     * Setter.
     * @param authorizedSignatureCT
     */
    public void setAuthorizedSignatureCT(String authorizedSignatureCT)
    {
        editTrxVO.setAuthorizedSignatureCT(authorizedSignatureCT);
    }

    /**
     * Setter.
     * @param reversalReasonCodeCT
     */
    public void setReversalReasonCodeCT(String reversalReasonCodeCT)
    {
        editTrxVO.setReversalReasonCodeCT(reversalReasonCodeCT);
    }

    //-- void setTransferTypeCT(java.lang.String)

    /**
    * Setter.
    * @param trxAmount
    */
    public void setTrxAmount(EDITBigDecimal trxAmount)
    {
        editTrxVO.setTrxAmount(SessionHelper.getEDITBigDecimal(trxAmount));
    }

    /**
     * Setter.
     * @param trxPercent
     */
    public void setTrxPercent(EDITBigDecimal trxPercent)
    {
        editTrxVO.setTrxPercent(SessionHelper.getEDITBigDecimal(trxPercent));
    }

    /**
    * Setter.
    * @param trxIsRescheduledInd
    */
    public void setTrxIsRescheduledInd(String trxIsRescheduledInd)
    {
        editTrxVO.setTrxIsRescheduledInd(trxIsRescheduledInd);
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getInterestProceedsOverride()
    {
        return SessionHelper.getEDITBigDecimal(editTrxVO.getInterestProceedsOverride());
    }

    /**
     * Setter.
     * @param interestProceedsOverride
     */
    public void setInterestProceedsOverride(EDITBigDecimal interestProceedsOverride)
    {
        editTrxVO.setInterestProceedsOverride(SessionHelper.getEDITBigDecimal(interestProceedsOverride));
    }

    /**
     * Getter.
     * @return
     */
    public String getZeroInterestIndicator()
    {
        return editTrxVO.getZeroInterestIndicator();
    }

    /**
     * Setter.
     * @param zeroInterestIndicator
     */
    public void setZeroInterestIndicator(String zeroInterestIndicator)
    {
        editTrxVO.setZeroInterestIndicator(zeroInterestIndicator);
    }

    /**
     * getter
     * @return
     */
    public String getNewPolicyNumber()
    {
        return editTrxVO.getNewPolicyNumber();
    } //-- java.lang.String getNewPolicyNumber()

    /**
     * setter
     * @param newPolicyNumber
     */
    public void setNewPolicyNumber(String newPolicyNumber)
    {
        editTrxVO.setNewPolicyNumber(newPolicyNumber);
    } //-- void setNewPolicyNumber(java.lang.String)

    /**
    * Getter.
    * @return
    */
    public String getNoCheckEFT()
    {
        return editTrxVO.getNoCheckEFT();
    }

    public void setNoCheckEFT(String noCheckEFT)
    {
        editTrxVO.setNoCheckEFT(noCheckEFT);
    }

    /**
     * Getter.
     * @return
     */
    public String getNotTakenOverrideInd()
    {
        return editTrxVO.getNotTakenOverrideInd();
    }
    
    /**
     * Setter.
     * @param notTakenOverrideInd
     */
    public void setNotTakenOverrideInd(String notTakenOverrideInd)
    {
        editTrxVO.setNotTakenOverrideInd(notTakenOverrideInd);
    }


    /**
     * Setter.
     * @param forceoutMinBalInd
     */
    public void setForceoutMinBalInd(String forceoutMinBalInd)
    {
        editTrxVO.setForceoutMinBalInd(forceoutMinBalInd);
    }

    /**
     * Getter.
     * @return
     */
    public String getForceoutMinBalInd()
    {
        return editTrxVO.getForceoutMinBalInd();
    }

    /**
     * Getter
     * @return
     */
    public String getPremiumDueCreatedIndicator()
    {
        return editTrxVO.getPremiumDueCreatedIndicator();
    } //-- java.lang.String getPremiumDueCreatedIndicator()

    /**
     * Setter
     * @param premiumDueCreatedIndicator
     */
    public void setPremiumDueCreatedIndicator(String premiumDueCreatedIndicator)
    {
        editTrxVO.setPremiumDueCreatedIndicator(premiumDueCreatedIndicator);
    } //-- void setPremiumDueCreatedIndicator(java.lang.String)


    /**
     * Returns the contractNumber for this EDITTrx.  Not a simple getter
     * @return  contractNumber
     */
    public String getContractNumber()
    {
        return this.getClientSetup().getContractSetup().getSegment().getContractNumber();
    }

    //-- void setTrxIsRescheduledInd(java.lang.String)
    public void saveHistory() throws Exception
    {
        int length = editTrxVOs.length;
        String beforeValue = null;
        String afterValue = null;
        List changes = new ArrayList();

        for (int i = 0; i < length; i++)
        {
            long editTrxPK = editTrxVOs[i].getEDITTrxPK();
            List voExclusionList = new ArrayList();
            voExclusionList.add(ChargeHistoryVO.class);
            voExclusionList.add(CommissionHistoryVO.class);
            voExclusionList.add(InvestmentHistoryVO.class);
            voExclusionList.add(BucketHistoryVO.class);
            voExclusionList.add(WithholdingHistoryVO.class);
            voExclusionList.add(ReinsuranceHistoryVO.class);
            voExclusionList.add(InSuspenseVO.class);

            EDITTrxVO currentEditTrxVO = DAOFactory.getEDITTrxDAO().findByEDITTrxPK(editTrxPK, true, voExclusionList)[0];

            //check for taxyear change- change value in editTrxVOs
            afterValue = editTrxVOs[i].getTaxYear() + "";
            beforeValue = currentEditTrxVO.getTaxYear() + "";

            if (!beforeValue.equals(afterValue))
            {
                ChangeHistoryVO changeHistoryVO = generateChangeHistory(beforeValue, afterValue, "TaxYear", "EDITTrx", editTrxPK);
                changes.add(changeHistoryVO);
                currentEditTrxVO.setTaxYear(Integer.parseInt(afterValue));
                editTrxVO = currentEditTrxVO;
                saveNonRecursively();
            }

            //now check for cost basis change
            //editTrxVOs has the after value
            FinancialHistoryVO financialHistoryVO = editTrxVOs[i].getEDITTrxHistoryVO()[0].getFinancialHistoryVO()[0];
            FinancialHistoryVO currentFinancialHistoryVO = currentEditTrxVO.getEDITTrxHistoryVO(0).getFinancialHistoryVO(0);

            EDITBigDecimal beforeCostBasis = new EDITBigDecimal(currentFinancialHistoryVO.getCostBasis());
            beforeValue = beforeCostBasis.toString();
            EDITBigDecimal afterCostBasis = new EDITBigDecimal(financialHistoryVO.getCostBasis());
            afterValue = afterCostBasis.toString();

            if (!beforeValue.equals(afterValue))
            {
                ChangeHistoryVO changeHistoryVO = generateChangeHistory(beforeValue, afterValue, "CostBasis", "FinancialHistory", financialHistoryVO.getFinancialHistoryPK());
                changes.add(changeHistoryVO);
                currentFinancialHistoryVO.setCostBasis(financialHistoryVO.getCostBasis());
                FinancialHistory financialHistory = new FinancialHistory(currentFinancialHistoryVO);
                financialHistory.save();

                //now adjust the segment costbasis
                Segment segment = new Segment(segmentPK);
                EDITBigDecimal costBasis = afterCostBasis.subtractEditBigDecimal(beforeCostBasis);
                segment.adjustCostBasis(costBasis);
            }

            //now check for distributionCode change
            beforeValue = currentFinancialHistoryVO.getDistributionCodeCT();
            afterValue = financialHistoryVO.getDistributionCodeCT();

            if (!Util.initString(beforeValue, "").equals(Util.initString(afterValue, "")))
            {
                ChangeHistoryVO changeHistoryVO = generateChangeHistory(beforeValue, afterValue, "DistributionCodeCT", "FinancialHistory", financialHistoryVO.getFinancialHistoryPK());
                changes.add(changeHistoryVO);
                currentFinancialHistoryVO.setDistributionCodeCT(financialHistoryVO.getDistributionCodeCT());
                FinancialHistory financialHistory = new FinancialHistory(currentFinancialHistoryVO);
                financialHistory.save();
            }
        }

        if ((changes != null) && (changes.size() != 0))
        {
            saveToChangeHistory(changes);
        }
    }

    private ChangeHistoryVO generateChangeHistory(String beforeValue, String afterValue, String fieldName, String tableName, long tableKey)
    {
        ChangeHistoryVO changeHistoryVO = new ChangeHistoryVO();
        changeHistoryVO.setChangeHistoryPK(0);
        changeHistoryVO.setParentFK(segmentPK);
        changeHistoryVO.setModifiedRecordFK(tableKey);
        changeHistoryVO.setTableName(tableName);

        String date = new EDITDate().getFormattedDate();
        changeHistoryVO.setEffectiveDate(date);
        changeHistoryVO.setProcessDate(date);
        changeHistoryVO.setFieldName(fieldName);
        changeHistoryVO.setBeforeValue(beforeValue);
        changeHistoryVO.setAfterValue(afterValue);
        changeHistoryVO.setOperator(operator);
        changeHistoryVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());

        if (fieldName.equalsIgnoreCase("TaxYear"))
        {
            changeHistoryVO.setNonFinancialTypeCT("EditTrxChange");
        }
        else
        {
            changeHistoryVO.setNonFinancialTypeCT("FinancialHistChg");
        }

        if (fieldName.equalsIgnoreCase("TaxYear"))
        {
            changeHistoryVO.setNonFinancialTypeCT("EditTrxChange");
        }
        else
        {
            changeHistoryVO.setNonFinancialTypeCT("FinancialHistChg");
        }

        return changeHistoryVO;
    }

    public void save() throws Exception
    {
        CRUD crud = null;

        List voExclusionList = null;

        voExclusionList = new ArrayList();

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            voExclusionList.add(SegmentVO.class);

            voExclusionList.add(ClientRoleVO.class);

            voExclusionList.add(InvestmentVO.class);

            crud.createOrUpdateVOInDBRecursively(editTrxVO, voExclusionList);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (crud != null)
            {
                crud.close();
            }
        }
    }

    /**
     * Saves the EDITTrx record non-recursively (no children will be saved)
     */
    public void saveNonRecursively()
    {
        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            crud.createOrUpdateVOInDB(editTrxVO);
        }
        finally
        {
            if (crud != null)
            {
                crud.close();
            }
        }
    }

    public void saveToChangeHistory(List changes) throws Exception
    {
        contract.business.Contract contractComponent = new contract.component.ContractComponent();

        try
        {
            for (int c = 0; c < changes.size(); c++)
            {
                ChangeHistoryVO changeHistoryVO = (ChangeHistoryVO) changes.get(c);

                contractComponent.saveChangeHistory(changeHistoryVO);
            }
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
            throw e;
        }
    }

    public void delete() throws Exception
    {
        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            crud.deleteVOFromDBRecursively(EDITTrxVO.class, editTrxVO.getEDITTrxPK());
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (crud != null)
            {
                crud.close();
            }

            crud = null;
        }
    }

    public void delete(CRUD crud) throws Exception
    {
        try
        {
            crud.deleteVOFromDBRecursively(EDITTrxVO.class, editTrxVO.getEDITTrxPK());
        }
        catch (Exception e)
        {
            crud.rollbackTransaction();

            System.out.println(e);

            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Deletes "this" object and its associations using Hibernate
     */
    public void hDeleteWithAssociations()
    {
        ClientSetup clientSetup = ClientSetup.findByPK(this.getClientSetupFK());
        ContractSetup contractSetup = ContractSetup.findByPK(clientSetup.getContractSetupFK());
        GroupSetup groupSetup = GroupSetup.findByPK(contractSetup.getGroupSetupFK());

        groupSetup.hDelete();
        contractSetup.hDelete();
        clientSetup.hDelete();

        this.hDelete();
    }

    public EDITTrxVO getAsVO()
    {
        return editTrxVO;
    }

    /**
     * creates freelook transaction group setup
     * @param segmentVO
     */
    public void createFreeLookTransactionGroupSetup(Segment segment)
    {
        ContractEvent contractEvent = new ContractEvent();

        GroupSetupVO groupSetupVO = contractEvent.buildDefaultGroupSetup(new Segment[] { segment });

        String processName = "Freelook";

        String optionCodeCT = segment.getOptionCodeCT();

        ProductStructure productStructure = new ProductStructure(segment.getProductStructureFK().longValue());

        try
        {
            //using hibernate but not completely tested 04-25-06
//            groupSetupVO = createAllocationOverridesForFreeLookTransaction(segment, new GroupSetup(groupSetupVO));
            createAllocationOverridesForFreeLookTransaction(segment, groupSetupVO);

            new GroupTrx().saveGroupSetup(groupSetupVO, this.editTrxVO, processName, optionCodeCT, productStructure.getProductStructurePK().longValue());
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            //            throw new RuntimeException(e); Don't rethrow - client layer not prepared to render this.
        }
    }

    /**
     * Create a Submit Transaction for processing in PRASE
     *
     * @param segment
     * @param operator
     * @param suppressPolicyPages
     * @return
     * @throws Exception
     */
    public static EDITTrx createSubmitTrxGroupSetup(Segment segment, String operator, String suppressPolicyPages) throws EDITEventException
    {
        ContractEvent contractEvent = new ContractEvent();

        GroupSetupVO groupSetupVO = contractEvent.buildDefaultGroupSetup(new Segment[] { segment });

        EDITDate todaysDate = new EDITDate();

        EDITDate effectiveDate = null;

        //  Use the ealier date - either today's date or the contract effective date - for the trx's effective date
        if (todaysDate.before(segment.getEffectiveDate()))
        {
            //  Use todaysDate
            effectiveDate = todaysDate;
        }
        else
        {
            //  Use contract date
            effectiveDate = segment.getEffectiveDate();
        }

        int taxYear = effectiveDate.getYear();

        EDITTrx editTrx = EDITTrx.buildDefaultEDITTrx(EDITTrx.TRANSACTIONTYPECT_SUBMIT, effectiveDate, taxYear, operator);

        if (suppressPolicyPages.equalsIgnoreCase("true"))
        {
            editTrx.setNoCorrespondenceInd("Y");
        }
        else
        {
            editTrx.setNoCorrespondenceInd("N");
        }

        new GroupTrx().saveGroupSetup(groupSetupVO, editTrx.getAsVO(), EDITTrx.PROCESSNAME_SUBMIT,
                segment.getOptionCodeCT(), segment.getProductStructureFK().longValue());

        return editTrx;
    }

 
    /**
     * Create a Billing Change Transaction for processing in PRASE
     *
     * @param segment
     * @param operator
     * @param ccType
     * @return
     * @throws Exception
     */
    public static void createBillingChangeTrxGroupSetup(Segment segment, String operator,
                                                        String ccType, EDITDate trxDate) throws EDITEventException
    {
        ContractEvent contractEvent = new ContractEvent();

        GroupSetupVO groupSetupVO = contractEvent.buildDefaultGroupSetup(new Segment[] { segment });

        int taxYear = trxDate.getYear();
        
        String trxType = EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE;
        
        if (segment.getSegmentNameCT().equalsIgnoreCase(Segment.SEGMENTNAMECT_UL) &&
   		 	(ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_ADD) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_TERM) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_CHANGE) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_CLAIMSTOP) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_CLASSCHANGE) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_DBOCHANGE) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_FACEDECREASE))) 
        {
            trxType = EDITTrx.TRANSACTIONTYPECT_COMPLEXCHANGE;
        }

        EDITTrx editTrx = EDITTrx.buildDefaultEDITTrx(trxType, trxDate, taxYear, operator);

        ContractSetupVO contractSetupVO = groupSetupVO.getContractSetupVO()[0];

        contractSetupVO.setComplexChangeTypeCT(ccType);

        if (ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_ADD) ||
            ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_DELETE))
        {
            contractSetupVO.setComplexChangeNewValue(segment.getRiderNumber() + "");
        }

        try
        {
            new GroupTrx().saveGroupSetup(groupSetupVO, editTrx.getAsVO(), EDITTrx.PROCESSNAME_COMMIT,
                segment.getOptionCodeCT(), segment.getProductStructureFK().longValue());
        }
        catch (EDITEventException e)
        {
        	if (!e.isLogged())
        	{
        		System.out.println(e);
        		e.printStackTrace();
        		e.setLogged(true);
        	}
        }
    }
    
    /**
     * Create a Billing Change Transaction for processing in PRASE
     *
     * @param segment
     * @param operator
     * @param ccType
     * @return
     * @throws Exception
     */
    public static void createBillingChangeTrxGroupSetup(Segment segment, String operator,
    		String ccType, EDITDate trxDate, EDITBigDecimal amount) throws EDITEventException
    {
        ContractEvent contractEvent = new ContractEvent();

        GroupSetupVO groupSetupVO = contractEvent.buildDefaultGroupSetup(new Segment[] { segment });

        int taxYear = trxDate.getYear();
        
        String trxType = EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE;
        
        if (segment.getSegmentNameCT().equalsIgnoreCase(Segment.SEGMENTNAMECT_UL) &&
   		 	(ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_ADD) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_TERM) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_CHANGE) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_CLAIMSTOP) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_CLASSCHANGE) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_DBOCHANGE) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_FACEDECREASE))) 
        {
            trxType = EDITTrx.TRANSACTIONTYPECT_COMPLEXCHANGE;
        }

        EDITTrx editTrx = EDITTrx.buildDefaultEDITTrx(trxType, trxDate, taxYear, operator);
        editTrx.setTrxAmount(amount);
        
        ContractSetupVO contractSetupVO = groupSetupVO.getContractSetupVO()[0];
        contractSetupVO.setComplexChangeTypeCT(ccType);
        contractSetupVO.setPolicyAmount(amount.getBigDecimal());

        try
        {
            new GroupTrx().saveGroupSetup(groupSetupVO, editTrx.getAsVO(), EDITTrx.PROCESSNAME_COMMIT,
                segment.getOptionCodeCT(), segment.getProductStructureFK().longValue());
        }
        catch (EDITEventException e)
        {
        	if (!e.isLogged())
        	{
        		System.out.println(e);
        		e.printStackTrace();
        		e.setLogged(true);
        	}
        }
    }

    /**
      * Create a Billing Change Transaction for processing in PRASE
      *
      * @param segment
      * @param operator
     * @param originatingTrxFK
     * @param ccType
     * @return
     * @throws Exception
     */
    public static void createBillingChangeTrxGroupSetup(Segment segment, String operator, Long originatingTrxFK,
                                                        String ccType, EDITDate trxDate) throws EDITEventException
    {
        ContractEvent contractEvent = new ContractEvent();

        GroupSetupVO groupSetupVO = contractEvent.buildDefaultGroupSetup(new Segment[] { segment });

        int taxYear = trxDate.getYear();
        
        String trxType = EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE;
        
        if (segment.getSegmentNameCT().equalsIgnoreCase(Segment.SEGMENTNAMECT_UL) &&
   		 	(ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_ADD) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_TERM) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_CHANGE) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_CLAIMSTOP) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_CLASSCHANGE) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_DBOCHANGE) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_FACEDECREASE))) 
        {
            trxType = EDITTrx.TRANSACTIONTYPECT_COMPLEXCHANGE;
        }

        EDITTrx editTrx = EDITTrx.buildDefaultEDITTrx(trxType, trxDate, taxYear, operator);
        editTrx.setOriginatingTrxFK(originatingTrxFK);

        ContractSetupVO contractSetupVO = groupSetupVO.getContractSetupVO()[0];

        contractSetupVO.setComplexChangeTypeCT(ccType);

        if (ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_ADD) ||
            ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_DELETE))
        {
            contractSetupVO.setComplexChangeNewValue(segment.getRiderNumber() + "");
        }

        try
        {
            new GroupTrx().saveGroupSetup(groupSetupVO, editTrx.getAsVO(), EDITTrx.PROCESSNAME_COMMIT,
                segment.getOptionCodeCT(), segment.getProductStructureFK().longValue());
        }
        catch (EDITEventException e)
        {
        	if (!e.isLogged())
        	{
        		System.out.println(e);
        		e.printStackTrace();
        		e.setLogged(true);
        	}
        }
    }

    /**
      * Create a Billing Change Transaction for processing in PRASE
      *
      * @param segment
      * @param operator
      * @param ccType
      * @return
      * @throws Exception
      */
     public static void createBillingChangeTrxGroupSetup(Segment segment, String operator,
                                                         String ccType, EDITDate trxDate, int riderNumber) throws EDITEventException
     {
         ContractEvent contractEvent = new ContractEvent();

         GroupSetupVO groupSetupVO = contractEvent.buildDefaultGroupSetup(new Segment[] { segment });

         int taxYear = trxDate.getYear();
         
         String trxType = EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE;
         
         if (segment.getSegmentNameCT().equalsIgnoreCase(Segment.SEGMENTNAMECT_UL) &&
    		 	(ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_ADD) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_TERM) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_CHANGE) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_CLAIMSTOP) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_CLASSCHANGE) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_DBOCHANGE) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_FACEINCREASE) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_FACEDECREASE))) 
         {
             trxType = EDITTrx.TRANSACTIONTYPECT_COMPLEXCHANGE;
             // why is this here!!  removed 20210604 - DE
             Segment riderSegment = Segment.findBy_SegmentFK_RiderNumber(segment.getSegmentPK(), riderNumber);
             if (riderSegment.getOptionCodeCT().equals("ULIncrease")) {
            	 ccType = ContractSetup.COMPLEXCHANGETYPECT_FACEINCREASE;
             }

             //if (ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_ADD)) {
            	// ccType = ContractSetup.COMPLEXCHANGETYPECT_FACEINCREASE;
             //}
         }

         EDITTrx editTrx = EDITTrx.buildDefaultEDITTrx(trxType, trxDate, taxYear, operator);

         ContractSetupVO contractSetupVO = groupSetupVO.getContractSetupVO()[0];

         contractSetupVO.setComplexChangeTypeCT(ccType);

         contractSetupVO.setComplexChangeNewValue(riderNumber + "");

         try
         {
             new GroupTrx().saveGroupSetup(groupSetupVO, editTrx.getAsVO(), EDITTrx.PROCESSNAME_COMMIT,
                 segment.getOptionCodeCT(), segment.getProductStructureFK().longValue());
         }
         catch (EDITEventException e)
         {
             System.out.println(e);
             e.printStackTrace();
         }
     }

    /**
     * Creates and saves a Complex Change Transaction for processing in PRASE
     *
     * @param segment
     * @param operator
     * @param ccType
     * @param effectiveDate
     *
     * @throws EDITEventException
     */
    public static void createComplexChangeTrxGroupSetup(Segment segment, String operator, String ccType, EDITDate effectiveDate) throws EDITEventException
    {
        ContractEvent contractEvent = new ContractEvent();

        GroupSetupVO groupSetupVO = contractEvent.buildDefaultGroupSetup(new Segment[] { segment });

        int taxYear = effectiveDate.getYear();

        EDITTrx editTrx = EDITTrx.buildDefaultEDITTrx(EDITTrx.TRANSACTIONTYPECT_COMPLEXCHANGE, effectiveDate, taxYear, operator);

        ContractSetupVO contractSetupVO = groupSetupVO.getContractSetupVO()[0];

        contractSetupVO.setComplexChangeTypeCT(ccType);

        try
        {
            new GroupTrx().saveGroupSetup(groupSetupVO, editTrx.getAsVO(), EDITTrx.PROCESSNAME_COMMIT,
                segment.getOptionCodeCT(), segment.getProductStructureFK().longValue());
        }
        catch (EDITEventException e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * creates transfer transaction group setup as it relates to COI replenishment
     * @param segmentVO
     */
    public void createTransferTransactionGroupSetup(Segment segment, Investment investment, Long filteredFundFK, EDITBigDecimal transferAmount)
    {
        ContractEvent contractEvent = new ContractEvent();

        GroupSetupVO groupSetupVO = contractEvent.buildDefaultGroupSetup(new Segment[] { segment });

        String processName = "Transfer";

        String optionCodeCT = segment.getOptionCodeCT();

        ProductStructure productStructure = new ProductStructure(segment.getProductStructureFK().longValue());

        try
        {
//            createAllocationOverridesForTransferTransaction(segment, new GroupSetup(groupSetupVO), investment, filteredFundFK, transferAmount);
            createAllocationOverridesForTransferTransaction(segment, groupSetupVO, (InvestmentVO)investment.getVO(), filteredFundFK.longValue(), transferAmount.getBigDecimal());

            new GroupTrx().saveGroupSetup(groupSetupVO, this.editTrxVO, processName, optionCodeCT, productStructure.getProductStructurePK().longValue());
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * creates HFTA transaction group setup as it relates to COI replenishment
     * @param segmentVO
     */
    public void createHFTATransactionGroupSetup(Segment segment, Investment investment, Long filteredFundFK,
                                                EDITBigDecimal transferAmount, int notificationDays, String notificationDaysType)
    {
        ContractEvent contractEvent = new ContractEvent();

        GroupSetupVO groupSetupVO = contractEvent.buildDefaultGroupSetup(new Segment[] { segment});

        String processName = "HFTA";

        String optionCodeCT = segment.getOptionCodeCT();

        ProductStructure productStructure = new ProductStructure(segment.getProductStructureFK().longValue());


        try
        {
//            createAllocationOverridesForTransferTransaction(segment, new GroupSetup(groupSetupVO), investment, filteredFundFK, transferAmount);
            createAllocationOverridesForTransferTransaction(segment, groupSetupVO, (InvestmentVO)investment.getVO(), filteredFundFK.longValue(), transferAmount.getBigDecimal());

            new GroupTrx().saveGroupSetup(groupSetupVO, this.editTrxVO, processName, optionCodeCT, productStructure.getProductStructurePK().longValue(), notificationDays, notificationDaysType);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * creates RMD transaction (RW) group setup
     * @param segmentVO
     */
    public void createRWTransactionGroupSetup(Segment segment, boolean oneTimeTrx, String frequency, String rmdElection)
    {
        ContractEvent contractEvent = new ContractEvent();

        GroupSetupVO groupSetupVO = contractEvent.buildDefaultGroupSetup(new Segment[] { segment });

        ScheduledEventVO scheduledEventVO = new ScheduledEventVO();
        scheduledEventVO.setLastDayOfMonthInd("N");
        scheduledEventVO.setStartDate(this.getEffectiveDate().getFormattedDate());

        if (oneTimeTrx)
        {
            scheduledEventVO.setFrequencyCT("Annual");
            scheduledEventVO.setStopDate(this.getEffectiveDate().getFormattedDate());
        }
        else
        {
            scheduledEventVO.setFrequencyCT(frequency);
            scheduledEventVO.setStopDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE).getFormattedDate());
        }

        if (frequency.equalsIgnoreCase("OneTime"))
        {
            scheduledEventVO.setFrequencyCT("Annual");
            scheduledEventVO.setStopDate(scheduledEventVO.getStartDate());
        }
        else
        {
            if (rmdElection.equalsIgnoreCase("Opt3"))
            {
                scheduledEventVO.setLastDayOfMonthInd("N");
            }
            else
            {
                scheduledEventVO.setLastDayOfMonthInd("Y");
            }
        }

        scheduledEventVO.setCostOfLivingInd("N");
        scheduledEventVO.setLifeContingentCT("NotLifeContingent");

        groupSetupVO.addScheduledEventVO(scheduledEventVO);

        String processName = "RMD";

        String optionCodeCT = segment.getOptionCodeCT();

        ProductStructure productStructure = new ProductStructure(segment.getProductStructureFK().longValue());

        try
        {
            new GroupTrx().saveGroupSetup(groupSetupVO, this.editTrxVO, processName, optionCodeCT, productStructure.getProductStructurePK().longValue());
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            //            throw new RuntimeException(e); Don't rethrow - client layer not prepared to render this.
        }
    }

    /**
     * creates RMD Notification transaction group setup
     * @param segmentVO
     */
    public void createRmdNotificationTransactionGroupSetup(Segment segment)
    {
        ContractEvent contractEvent = new ContractEvent();

        GroupSetupVO groupSetupVO = contractEvent.buildDefaultGroupSetup(new Segment[] { segment });

        String processName = "RMDNotify";

        String optionCodeCT = segment.getOptionCodeCT();

        ProductStructure productStructure = new ProductStructure(segment.getProductStructureFK().longValue());

        try
        {
            new GroupTrx().saveGroupSetup(groupSetupVO, this.editTrxVO, processName, optionCodeCT, productStructure.getProductStructurePK().longValue());
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
    }

    // ********************* THIS METHOD WAS NOT COMPLETELY CONVERTED FROM VOs SINCE SOME OF THE VOs ARE
    //                       DATA MEMBERS AND NO ONE IS QUITE SURE OF WHAT IS BEING DONE HERE ************************
    public void generateRedemptionTrx(EDITDate effectiveDate, EDITBigDecimal trxAmount, long selectedSuspensePK, long selectedFilteredFundPK) throws Exception
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        ClientSetupVO origClientSetupVO = (ClientSetupVO) originalEditTrxVO.getParentVO(ClientSetupVO.class);
        ContractSetupVO origContractSetupVO = (ContractSetupVO) origClientSetupVO.getParentVO(ContractSetupVO.class);
        SegmentVO segmentVO = (SegmentVO) origContractSetupVO.getParentVO(SegmentVO.class);

        ContractSetup origContractSetup = (ContractSetup) SessionHelper.map(origContractSetupVO, EDITTrx.DATABASE);
        Segment segment = (Segment) SessionHelper.map(segmentVO, EDITTrx.DATABASE);
        

        String pendingStatus = hftEditTrxVO.getPendingStatus();
        ClientSetupVO hftClientSetupVO = (ClientSetupVO) hftEditTrxVO.getParentVO(ClientSetupVO.class);
        InvestmentAllocationOverrideVO[] hftInvAllocOvrdVOs = ((ContractSetupVO) hftEditTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class)).getInvestmentAllocationOverrideVO();

        GroupSetup groupSetup = new GroupSetup();
        groupSetup.setGroupSetupPK(new Long(0));

        ContractSetup contractSetup = new ContractSetup();
        contractSetup.setContractSetupPK(new Long(0));
        contractSetup.setGroupSetupFK(new Long(0));
        contractSetup.setSegmentFK(origContractSetup.getSegmentFK());
        contractSetup.setPolicyAmount(Util.roundToNearestCent(trxAmount));

        Suspense suspense = new Suspense(selectedSuspensePK);

        OutSuspense outSuspense = createOutSuspense(trxAmount, suspense);
        contractSetup.addOutSuspense(outSuspense);

        hftClientSetupVO.setClientSetupPK(0);
        hftClientSetupVO.setContractSetupFK(0);
        hftClientSetupVO.removeAllEDITTrxVO();

        //  Map investmentAllocationOverride VOs to entities to pass to method
        InvestmentAllocationOverride[] hftInvAllocOvrds = mapInvestmentAllocationOverrideVOsToEntities(hftInvAllocOvrdVOs);

        addFromInvestmentAllocOvrd(contractSetup, hftInvAllocOvrds);

        createNewHFTEditTrx(effectiveDate, contractSetup, hftEditTrxVO.getEDITTrxPK());

        if (originalEditTrxVO.getTransactionTypeCT().equalsIgnoreCase("WI") || originalEditTrxVO.getTransactionTypeCT().equalsIgnoreCase("FS"))
        {
            //If originating trx is WI or FS, trx to be generated is an HREM
            editTrxVO.setTransactionTypeCT("HREM");
        }
        else if (originalEditTrxVO.getTransactionTypeCT().equalsIgnoreCase("DE"))
        {
            //If originating trx is DE, trx to be generated is an HDTH
            editTrxVO.setTransactionTypeCT("HDTH");
        }
        else
        {
            //All other originating trx will generate an HFTA
            editTrxVO.setTransactionTypeCT("HFTA");

            InvestmentAllocationOverrideVO[] origInvAllocOvrdVOs = ((ContractSetupVO) originalEditTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class)).getInvestmentAllocationOverrideVO();

            if (pendingStatus.equalsIgnoreCase("H"))
            {
                //  Map investmentAllocationOverride VOs to entities to pass to method
                InvestmentAllocationOverride[] origInvAllocOvrds = mapInvestmentAllocationOverrideVOsToEntities(origInvAllocOvrdVOs);
                addToInvestmentAllocOvrd(contractSetup, origInvAllocOvrds, segment, trxAmount);
            }
            else
            {
//                engine.business.Lookup engineLookup = new engine.component.LookupComponent();

//                ProductStructureVO[] productStructureVO = engineLookup.findProductStructureVOByPK(segment.getProductStructureFK(), false, new ArrayList());
                ProductStructure productStructure = ProductStructure.findByPK(segment.getProductStructureFK());

                //Final Price not yet received; Generate HFTA using HFIA as "To" Fund
                Long hedgeFundInterimAccountFK = productStructure.getHedgeFundInterimAccountFK(); //Points to Fund Table

//                FilteredFundVO[] filteredFundVOs = engineLookup.composeFilteredFundVOByCoStructurePK_And_FundPK(segment.getProductStructureFK(), hedgeFundInterimAccountFK, new ArrayList());

                FilteredFund[] filteredFunds = FilteredFund.findBy_ProductStructurePK_FundFK(segment.getProductStructureFK(), hedgeFundInterimAccountFK);

                if ((filteredFunds != null) && (filteredFunds.length > 0))
                {
                    InvestmentAllocationOverride investmentAllocationOverride = setupHedgeFundInterimAccount(filteredFunds[0], contractSetup.getPolicyAmount(), segment.getSegmentPK(), contractSetup.getContractSetupPK(), "Dollars");

                    contractSetup.addInvestmentAllocationOverride(investmentAllocationOverride);
                }
            }
        }

        hftClientSetupVO.addEDITTrxVO(editTrxVO);

        ClientSetup hftClientSetup = (ClientSetup) SessionHelper.map(hftClientSetupVO, EDITTrx.DATABASE);

        contractSetup.addClientSetup(hftClientSetup);

        groupSetup.addContractSetup(contractSetup);

        ProductStructure productStructure = new ProductStructure(segment.getProductStructureFK().longValue());

        GroupTrx groupTrx = new GroupTrx();

        boolean saveSuccessful = groupTrx.saveGroupSetup(groupSetup.getAsVO(), this.editTrxVO, editTrxVO.getTransactionTypeCT(), segmentVO.getOptionCodeCT(), productStructure.getProductStructurePK().longValue());

        if (saveSuccessful)
        {
            EDITBigDecimal pendingSuspenseAmount = new EDITBigDecimal(suspense.getAsVO().getPendingSuspenseAmount(), 2);
            pendingSuspenseAmount = pendingSuspenseAmount.addEditBigDecimal(trxAmount);

            suspense.setPendingSuspenseAmount(pendingSuspenseAmount);

            suspense.save();

            EDITBigDecimal notificationAmountReceived = new EDITBigDecimal(hftEditTrxVO.getNotificationAmountReceived());

            notificationAmountReceived = notificationAmountReceived.addEditBigDecimal(trxAmount);

            hftEditTrxVO.setNotificationAmountReceived(Util.roundToNearestCent(notificationAmountReceived).getBigDecimal());

            eventComponent.saveEDITTrxVONonRecursively(hftEditTrxVO);

//            contract.business.Lookup contractLookup = new contract.component.LookupComponent();

//            InvestmentVO[] investmentVOs = contractLookup.getInvestmentByFilteredFundFKAndSegmentFK(selectedFilteredFundPK, segmentVO.getSegmentPK());
            Investment[] investments = Investment.findBy_FilteredFundFK_SegmentFK(new Long(selectedFilteredFundPK), segment.getSegmentPK());

            Investment investment = null;
            Long chargeCodeFK = new Long(0);

            if (investments != null)
            {
                investment = investments[0];
                chargeCodeFK = investment.getChargeCodeFK();
            }

            EDITBigDecimal feeAmount = trxAmount;

            // effectiveDate of DFCASH transaction is set to originatiing edit trx effectiveDate
            EDITDate effectiveDateForDFCASH = new EDITDate(originalEditTrxVO.getEffectiveDate());

            createDFCASHFeeTrx(selectedFilteredFundPK, feeAmount, effectiveDateForDFCASH, chargeCodeFK.longValue(), false);
        }
    }

    private OutSuspense createOutSuspense(EDITBigDecimal trxAmount, Suspense suspense) throws Exception
    {
        OutSuspense outSuspense = new OutSuspense();
        outSuspense.setOutSuspensePK(new Long(0));
        outSuspense.setSuspenseFK(suspense.getSuspensePK());
        outSuspense.setContractSetupFK(new Long(0));
        outSuspense.setAmount(Util.roundToNearestCent(trxAmount));

        return outSuspense;
    }

    private void addFromInvestmentAllocOvrd(ContractSetup contractSetup, InvestmentAllocationOverride[] hftInvAllocOvrds) throws Exception
    {
//        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

//        List investmentVOInclusionList = new ArrayList();
//        investmentVOInclusionList.add(InvestmentAllocationVO.class);

        for (int i = 0; i < hftInvAllocOvrds.length; i++)
        {
            if (hftInvAllocOvrds[i].getToFromStatus().equalsIgnoreCase("T"))
            {
//                Investment hftInvestment = new Investment(contractLookup.composeInvestmentVOByPK(hftInvAllocOvrds[i].getInvestmentFK(), investmentVOInclusionList));

                Investment hftInvestment = Investment.findByPK_IncludeInvestmentAllocations(hftInvAllocOvrds[i].getInvestmentFK());

                InvestmentAllocation investmentAllocation = (InvestmentAllocation) hftInvestment.getInvestmentAllocations().iterator().next();

                long hftInvAllocPK = investmentAllocation.getPKForAllocationDollars(contractSetup.getPolicyAmount());

                if (hftInvAllocPK == 0)
                {
                    hftInvAllocPK = createNewInvestmentAllocation(hftInvestment, Util.roundToNearestCent(contractSetup.getPolicyAmount()).getBigDecimal());
                }

                InvestmentAllocationOverride newHFTInvAllocOvrd = new InvestmentAllocationOverride();
                newHFTInvAllocOvrd.setInvestmentAllocationOverridePK(new Long(0));
                newHFTInvAllocOvrd.setContractSetupFK(0);
                newHFTInvAllocOvrd.setInvestmentFK(hftInvAllocOvrds[i].getInvestmentFK().longValue());
                newHFTInvAllocOvrd.setInvestmentAllocationFK(hftInvAllocPK);
                newHFTInvAllocOvrd.setHFStatus("A");
                newHFTInvAllocOvrd.setHFIAIndicator("N");
                newHFTInvAllocOvrd.setToFromStatus("F");

                contractSetup.addInvestmentAllocationOverride(newHFTInvAllocOvrd);
            }
        }
    }

    private void addToInvestmentAllocOvrd(ContractSetup contractSetup, InvestmentAllocationOverride[] origInvAllocOvrds,
                                          Segment segment,
                                          EDITBigDecimal trxAmount) throws Exception
    {
        EDITBigDecimal totalHFAllocation = new EDITBigDecimal();

        for (int i = 0; i < origInvAllocOvrds.length; i++)
        {
            if (origInvAllocOvrds[i].getToFromStatus().equalsIgnoreCase("T"))
            {
                Investment investment = origInvAllocOvrds[i].getInvestment();

                FilteredFund filteredFund = FilteredFund.findByPK(investment.getFilteredFundFK());

                Fund fund = filteredFund.getFund(); // but filteredFund does not contain children at this point

                InvestmentAllocationOverride newHFTInvAllocOvrd = new InvestmentAllocationOverride();
                newHFTInvAllocOvrd.setInvestmentAllocationOverridePK(new Long(0));
                newHFTInvAllocOvrd.setContractSetupFK(0);
                newHFTInvAllocOvrd.setInvestmentFK(origInvAllocOvrds[i].getInvestmentFK().longValue());
                newHFTInvAllocOvrd.setInvestmentAllocationFK(origInvAllocOvrds[i].getInvestmentAllocationFK());
                newHFTInvAllocOvrd.setHFStatus("A");
                newHFTInvAllocOvrd.setHFIAIndicator("N");
                newHFTInvAllocOvrd.setToFromStatus("T");

                if (fund.getFundType().equalsIgnoreCase("Hedge"))
                {
                    InvestmentAllocation invAlloc = origInvAllocOvrds[i].getInvestmentAllocation();
                    totalHFAllocation = totalHFAllocation.addEditBigDecimal(invAlloc.getAllocationPercent());
                    newHFTInvAllocOvrd.setHFStatus("N");
                }

                contractSetup.addInvestmentAllocationOverride(newHFTInvAllocOvrd);
            }
        }

        if (totalHFAllocation.isGT(new EDITBigDecimal()))
        {
//            engine.business.Lookup engineLookup = new engine.component.LookupComponent();

//            ProductStructureVO[] productStructureVO = engineLookup.findProductStructureVOByPK(segmentVO.getProductStructureFK(), false, new ArrayList());
            ProductStructure productStructure = ProductStructure.findByPK(segment.getProductStructureFK());

            Long hfiaFK = productStructure.getHedgeFundInterimAccountFK();

            EDITBigDecimal fundDollars = trxAmount;
            fundDollars = fundDollars.multiplyEditBigDecimal(totalHFAllocation);

//            FilteredFundVO[] filteredFundVOs = engineLookup.composeFilteredFundVOByCoStructurePK_And_FundPK(segmentVO.getProductStructureFK(), hfiaFK, new ArrayList());
            FilteredFund[] filteredFunds = FilteredFund.findBy_ProductStructurePK_FundFK(productStructure.getProductStructurePK(), hfiaFK);

            if ((filteredFunds != null) && (filteredFunds.length > 0))
            {
                InvestmentAllocationOverride investmentAllocationOverride = setupHedgeFundInterimAccount(filteredFunds[0], fundDollars, contractSetup.getSegmentFK(), contractSetup.getContractSetupPK(), "Dollars");

                contractSetup.addInvestmentAllocationOverride(investmentAllocationOverride);
            }
        }
    }

    /**
     * Creates a new InvestmentAllocation for the InvestmentAllocationOverride record
     * @param investment
     * @param allocationDollars
     * @return
     * @throws Exception
     */
    private long createNewInvestmentAllocation(Investment investment, BigDecimal allocationDollars) throws Exception
    {
        InvestmentAllocation investmentAllocation = new InvestmentAllocation(investment.getPK(), allocationDollars, "O", "Dollars");

        investmentAllocation.save();

        return investmentAllocation.getNewInvestmentAllocationPK();
    }

    private void createNewHFTEditTrx(EDITDate effectiveDate, ContractSetup contractSetup, long selectedEditTrxPK)
    {
        editTrxVO = new EDITTrxVO();
        editTrxVO.setEDITTrxPK(0);
        editTrxVO.setClientSetupFK(0);
        editTrxVO.setEffectiveDate(effectiveDate.getFormattedDate());
        editTrxVO.setStatus("N");
        editTrxVO.setPendingStatus("P");
        editTrxVO.setSequenceNumber(1);
        editTrxVO.setTaxYear(effectiveDate.getYear());
        editTrxVO.setTrxAmount(contractSetup.getPolicyAmount().getBigDecimal());
        editTrxVO.setOriginatingTrxFK(selectedEditTrxPK);
        editTrxVO.setNoCorrespondenceInd("Y");
        editTrxVO.setNoAccountingInd("N");
        editTrxVO.setNoCommissionInd("N");
        editTrxVO.setZeroLoadInd("N");
        editTrxVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
        editTrxVO.setOperator(operator);
        editTrxVO.setTransferTypeCT("HFReceipt");
        editTrxVO.setPremiumDueCreatedIndicator("N");

    }

    /**
     * Adds an Investment record for the Hedge Fund Interim Account
     * @param filteredFundVO
     * @param hfiaAllocation
     * @param segmentFK
     * @param contractSetupPK
     * @param allocationType
     * @return
     * @throws Exception
     */
    private InvestmentAllocationOverride setupHedgeFundInterimAccount(FilteredFund filteredFund, EDITBigDecimal hfiaAllocation, Long segmentFK, Long contractSetupPK, String allocationType) throws Exception
    {
//        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

//        List voInclusionList = new ArrayList();
//        voInclusionList.add(InvestmentAllocationVO.class);

//        InvestmentVO[] investmentVOs = contractLookup.composeInvestmentVO(segmentFK, null, voInclusionList, editTrxVO.getTransactionTypeCT());
        Investment[] investments = Investment.findBy_SegmentPK_IncludeInvestmentAllocations(segmentFK);

        boolean hfiaFound = false;

        InvestmentAllocationOverride investmentAllocationOverride = null;

        for (int i = 0; i < investments.length; i++)
        {
            Investment investment = investments[i];

            if (investment.getFilteredFundFK().longValue() == filteredFund.getFilteredFundPK().longValue())
            {
                InvestmentAllocation investmentAllocation = investment.getInvestmentAllocation();

                long investmentAllocationPK = 0;

                if (allocationType.equalsIgnoreCase("Percent"))
                {
                    investmentAllocationPK = investmentAllocation.getPKForAllocationPercent(hfiaAllocation.getBigDecimal());
                }
                else
                {
                    investmentAllocationPK = investmentAllocation.getPKForAllocationDollars(hfiaAllocation);
                }

                if (investmentAllocationPK > 0)
                {
                    investmentAllocationOverride = new InvestmentAllocationOverride(contractSetupPK.longValue(), investment.getPK(), investmentAllocationPK, "T", "A", "Y", "N");
                    hfiaFound = true;
                }
                else
                {
                    investmentAllocation = new InvestmentAllocation(investment.getPK(), hfiaAllocation.getBigDecimal(), "O", allocationType);

                    investmentAllocation.save();

                    long newInvestmentAllocationPK = investmentAllocation.getNewInvestmentAllocationPK();

                    investmentAllocationOverride = new InvestmentAllocationOverride(contractSetupPK.longValue(), investment.getPK(), newInvestmentAllocationPK, "T", "A", "Y", "N");
                    hfiaFound = true;
                }
            }
        }

        if (!hfiaFound)
        {
            Investment investment = new Investment(segmentFK.longValue(), filteredFund.getFilteredFundPK().longValue());

            investment.save();

            long newInvestmentPK = investment.getNewInvestmentPK();

            InvestmentAllocation investmentAllocation = null;

            investmentAllocation = new InvestmentAllocation(newInvestmentPK, hfiaAllocation.getBigDecimal(), "O", allocationType);

            investmentAllocation.save();

            long newInvestmentAllocationPK = investmentAllocation.getNewInvestmentAllocationPK();

            investmentAllocationOverride = new InvestmentAllocationOverride(contractSetupPK.longValue(), newInvestmentPK, newInvestmentAllocationPK, "T", "A", "Y", "N");
        }

        return investmentAllocationOverride;
    }

    /**
     * creates allocation overrides for free look transaction
     * @param segment
     * @param groupSetup
     */
    private GroupSetupVO createAllocationOverridesForFreeLookTransaction(Segment segment, GroupSetup groupSetup) throws Exception
    {
        ContractSetup contractSetup = new ContractSetup(groupSetup.getAsVO().getContractSetupVO(0));

//        List voExclusionList = new ArrayList();
//        voExclusionList.add(InvestmentAllocationOverrideVO.class);
//        voExclusionList.add(InvestmentHistoryVO.class);
//        voExclusionList.add(BucketVO.class);

        // get all the investments that are primary
//        investmentVOs = new InvestmentDAO().findBySegmentPKAndInvestmentAllocationOverrideStatus(segmentVO.getSegmentPK(), "P", true, voExclusionList);
        Investment[] investments = Investment.findBy_SegmentPK_InvestmentAllocationOverrideStatus(segment.getSegmentPK(), "P");

        if (investments != null)
        {
            InvestmentAllocation  iaWithOverrideStatus = null;

            for (int i = 0; i < investments.length; i++)
            {
                Investment investment = investments[i];

                Set investmentAllocations = investment.getInvestmentAllocations();

                for (Iterator iterator = investmentAllocations.iterator(); iterator.hasNext();)
                {
                    InvestmentAllocation investmentAllocation = (InvestmentAllocation) iterator.next();

                    if (investmentAllocation.getOverrideStatus().equalsIgnoreCase("P"))
                    {
                        iaWithOverrideStatus = investmentAllocation;

                        break;
                    }
                }

                InvestmentAllocationOverride investmentAllocationOverride = new InvestmentAllocationOverride();

                investmentAllocationOverride.setInvestmentAllocationOverridePK(new Long(0));
                investmentAllocationOverride.setInvestmentFK(investment.getInvestmentPK().longValue());
                investmentAllocationOverride.setInvestmentAllocationFK(iaWithOverrideStatus.getInvestmentAllocationPK().longValue());
                investmentAllocationOverride.setToFromStatus("T");

//                contractSetup.addInvestmentAllocationOverride(investmentAllocationOverride);
                ((ContractSetupVO)contractSetup.getVO()).addInvestmentAllocationOverrideVO((InvestmentAllocationOverrideVO)investmentAllocationOverride.getVO());
            }
        }

        // get all the investments that are with override status 'O'
//        investmentVOs = new InvestmentDAO().findBySegmentPKAndInvestmentAllocationOverrideStatus(segmentVO.getSegmentPK(), "O", true, voExclusionList);
        investments = Investment.findBy_SegmentPK_InvestmentAllocationOverrideStatus(segment.getSegmentPK(), "O");

        if (investments != null)
        {
            for (int i = 0; i < investments.length; i++)
            {
                long freelookFundPK = getFreelookFundPK(segment);

                // to make sure that the fund is freelook fund
                if (investments[i].getFilteredFundFK().longValue() == freelookFundPK)
                {
                    Investment investment = investments[i];

                    InvestmentAllocation investmentAllocation = new InvestmentAllocation(investment.getInvestmentAllocationVOs());
                    long investmentAllocationPK = investmentAllocation.getPKForOverrideAllocationPercent(new BigDecimal(1.0));

                    if (investmentAllocationPK == 0)
                    {
                        //create InvestmentAllocation for 1.0 percent
                        investmentAllocationPK = setupNewInvestmentAllocation(investments[i].getInvestmentPK().longValue(), new EDITBigDecimal("1"));
                    }

                    // create allocation override
                    InvestmentAllocationOverride investmentAllocationOverride = new InvestmentAllocationOverride();

                    investmentAllocationOverride.setInvestmentAllocationOverridePK(new Long(0));
                    investmentAllocationOverride.setInvestmentFK(investments[i].getInvestmentPK().longValue());
                    investmentAllocationOverride.setInvestmentAllocationFK(investmentAllocationPK);
                    investmentAllocationOverride.setToFromStatus("F");

//                    contractSetup.addInvestmentAllocationOverride(investmentAllocationOverride);
                    ((ContractSetupVO)contractSetup.getVO()).addInvestmentAllocationOverrideVO((InvestmentAllocationOverrideVO)investmentAllocationOverride.getVO());

                    i = investments.length;
                }
            }
        }

        return (GroupSetupVO)groupSetup.getAsVO();
    }

    /**
     * Creates allocation overrides for the transfer transaction (TF/HFTA) as it relates to COI replenishment
     * @param segmentVO
     * @param groupSetupVO
     */
    private void createAllocationOverridesForTransferTransaction(Segment segment, GroupSetup groupSetup,
                         Investment fromInvestment, Long filteredFundFK, EDITBigDecimal transferAmount) throws Exception
    {
        ContractSetup contractSetup = (ContractSetup) groupSetup.getContractSetups().iterator().next();

        //Set up the from fund
        long fromInvAllocPK = getInvestmentAllocationPK(fromInvestment, transferAmount);

        InvestmentAllocationOverride fromIAOverride = createInvestmentAllocationOverride(fromInvestment.getInvestmentPK().longValue(), fromInvAllocPK, "F");

        contractSetup.addInvestmentAllocationOverride(fromIAOverride);

        //Set up the to fund
//        contract.business.Lookup contractLookup = new contract.component.LookupComponent();
//        InvestmentVO[] toInvestmentVO = contractLookup.getInvestmentByFilteredFundFKAndSegmentFK(filteredFundFK, segment.getSegmentPK());
        Investment[] investments = Investment.findBy_FilteredFundFK_SegmentFK(filteredFundFK, segment.getSegmentPK());

        if (investments != null)
        {
            long toInvAllocPK = getInvestmentAllocationPK(investments[0], transferAmount);

            InvestmentAllocationOverride toIAOverride = createInvestmentAllocationOverride(investments[0].getInvestmentPK().longValue(), toInvAllocPK, "T");

            contractSetup.addInvestmentAllocationOverride(toIAOverride);
        }
        else
        {
            Investment investment = new Investment(segment.getSegmentPK().longValue(), filteredFundFK.longValue());

            investment.save();

            long toInvestmentPK = investment.getNewInvestmentPK();

            InvestmentAllocation investmentAllocation = new InvestmentAllocation(toInvestmentPK, transferAmount.getBigDecimal(), "O", "Dollars");
            investmentAllocation.save();

            long toInvAllocPK = investmentAllocation.getNewInvestmentAllocationPK();

            InvestmentAllocationOverride toIAOverride = createInvestmentAllocationOverride(toInvestmentPK, toInvAllocPK, "T");

            contractSetup.addInvestmentAllocationOverride(toIAOverride);
        }
    }

    /**
     * Adds a new Investment Allocation record for investment and allocation specified in params
     * @param investmentPK
     * @param allocationPct
     * @throws Exception
     */
    private long setupNewInvestmentAllocation(long investmentPK, EDITBigDecimal allocationPct) throws Exception
    {
        InvestmentAllocation investmentAllocation = new InvestmentAllocation(investmentPK, allocationPct.getBigDecimal(), "O", "Percent");
        investmentAllocation.save();

        return investmentAllocation.getNewInvestmentAllocationPK();
    }

    /**
     * return freelook fund PK
     * @param segmentVO
     * @return
     */
    private long getFreelookFundPK(Segment segment)
    {
        Long productStructurePK = segment.getProductStructureFK();

        String areaCT = segment.getIssueStateCT();
        String grouping = "FREELOOKPROCESS";
        EDITDate effectiveDate = segment.getEffectiveDate();
        String field = "FREELOOKFUND";

        Area area = new Area(productStructurePK.longValue(), areaCT, grouping, effectiveDate, null);

        AreaValue areaValue = area.getAreaValue(field);

        String fundNumber = areaValue.getAreaValue();

        long filteredFundPK = 0;

//        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        FilteredFund filteredFund = null;

        try
        {
//            filteredFunds = engineLookup.getByFundNumber(fundNumber);
            filteredFund = FilteredFund.findByFundNumber(fundNumber);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        if (filteredFund != null)
        {
            filteredFundPK = filteredFund.getFilteredFundPK().longValue();
        }

        return filteredFundPK;
    }

    private long getInvestmentAllocationPK(Investment investment, EDITBigDecimal transferAmount) throws Exception
    {
        InvestmentAllocation investmentAllocation = (InvestmentAllocation) investment.getInvestmentAllocations().iterator().next();

        //Set up the from fund
        long investmentAllocationPK = investmentAllocation.getPKForAllocationDollars(transferAmount);

        if (investmentAllocationPK == 0)
        {
            InvestmentAllocation newInvestmentAllocation = new InvestmentAllocation(
                            investment.getInvestmentPK().longValue(), transferAmount.getBigDecimal(), "O", "Dollars");

            newInvestmentAllocation.save();
            investmentAllocationPK = newInvestmentAllocation.getNewInvestmentAllocationPK();
        }

        return investmentAllocationPK;
    }

    private InvestmentAllocationOverride createInvestmentAllocationOverride(long investmentFK, long investmentAllocationFK, String toFromStatus)
    {
        InvestmentAllocationOverride iaOverride = new InvestmentAllocationOverride();

        iaOverride.setInvestmentAllocationOverridePK(new Long(0));
        iaOverride.setInvestmentFK(investmentFK);
        iaOverride.setInvestmentAllocationFK(investmentAllocationFK);
        iaOverride.setToFromStatus(toFromStatus);

        return iaOverride;
    }

    /**
     * Getter.
     * @return
     */
    public long getPK()
    {
        return editTrxVO.getEDITTrxPK();
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return SessionHelper.getEDITDate(editTrxVO.getEffectiveDate());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTrxAmount()
    {
        return SessionHelper.getEDITBigDecimal(editTrxVO.getTrxAmount());
    }

    public static EDITTrx[] findPendingEditTrx(Segment segment)
    {
        String hql = " select editTrx from EDITTrx editTrx " +
                     " join editTrx.ClientSetup clientSetup" +
                     " join clientSetup.ContractSetup contractSetup" +
                     " join contractSetup.Segment segment" +
                     " where editTrx.PendingStatus in ('P', 'M')" +
                     " and segment = :segment";

        Map params = new HashMap();

        params.put("segment", segment);

        List results =  SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }

    /**
     * getter
     * @return
     */
    public String getTransferUnitsType()
    {
        return editTrxVO.getTransferUnitsType();
    }

    //-- java.lang.String getTransferUnitsType()






    /**
     * setter
     * @param transferUnitsType
     */
    public void setTransferUnitsType(String transferUnitsType)
    {
        editTrxVO.setTransferUnitsType(transferUnitsType);
    }

    //-- void setTransferUnitsType(java.lang.String)

    /**
     * Builds a TransactionType-Neutral version of an EDITTrx(VO). Is is assumed the transaction-specific value will be
     * overlaid.
     * @param transactionTypeCT
     * @param effectiveDate
     * @return
     */
    public static EDITTrx buildDefaultEDITTrx(String transactionTypeCT, EDITDate effectiveDate, int taxYear, String operator)
    {
        EDITTrx editTrx = new EDITTrx();

        editTrx.setEffectiveDate(effectiveDate);
        editTrx.setStatus("N");
        editTrx.setPendingStatus("P");
        editTrx.setSequenceNumber(1);
        editTrx.setTaxYear(taxYear);
        editTrx.setTransactionTypeCT(transactionTypeCT);
        editTrx.setTrxIsRescheduledInd("N");
        editTrx.setCommissionStatus("N");
        editTrx.setLookBackInd("N");
        editTrx.setNoCorrespondenceInd("N");
        editTrx.setNoAccountingInd("N");
        editTrx.setNoCorrespondenceInd("N");
        editTrx.setNoCommissionInd("N");
        editTrx.setZeroLoadInd("N");
        editTrx.setMaintDateTime(new EDITDateTime());
        editTrx.setOperator(operator);
        editTrx.setPremiumDueCreatedIndicator("N");

        return editTrx;
    }

    /**
     * create the death trx for this contract client
     * @param operator
     * @param effectiveDate
     */
    public void createDeathEDITTrx(Segment segment, String operator, EDITDate effectiveDate, Long contractClientFK, Long clientRoleFK)
    {
        String transactionType = "DE";

//        Long masterPK = segment.getMasterFK();

        GroupSetup groupSetup = GroupSetup.initializeGroupSetupThruClientSetup(segment, transactionType, contractClientFK, clientRoleFK);

        int taxYear = effectiveDate.getYear();

        EDITTrx editTrx = buildDefaultEDITTrx(transactionType, effectiveDate, taxYear, operator);

        String processName = "Death";
        String optionCodeCT = segment.getOptionCodeCT();

        ProductStructure productStructure = new ProductStructure(segment.getProductStructureFK().longValue());

        try
        {
            new GroupTrx().saveGroupSetup(groupSetup.getAsVO(), editTrx.getAsVO(), processName, optionCodeCT, productStructure.getProductStructurePK().longValue());
        }
        catch (EDITEventException e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Create a rider claim trx for the specified contract
     * @param operator
     * @param effectiveDate
     * @param authorizedSignatureCT
     */
    public static void createRiderClaimEDITTrx(Segment segment, String operator,
                                               EDITDate effectiveDate, Long contractClientFK, Long clientRoleFK,
                                               String riderName, String careType, EDITDate dateOfDeath, String claimType,
                                               String conditionCT, String authorizedSignatureCT, EDITBigDecimal amountOverride, EDITBigDecimal interestOverride)
    {
        String transactionType = TRANSACTIONTYPECT_RIDER_CLAIM;

        GroupSetupVO groupSetupVO = GroupSetup.initializeGroupSetupVO_ThruClientSetup(segment, transactionType, contractClientFK, clientRoleFK);

        ContractSetupVO contractSetupVO = groupSetupVO.getContractSetupVO(0);
        if (claimType != null)
        {
            contractSetupVO.setClaimStatusCT(claimType);
        }
        else
        {
            if (riderName.equalsIgnoreCase(Segment.OPTIONCODECT_RIDER_CTR5))
            {
                contractSetupVO.setClaimStatusCT(Segment.OPTIONCODECT_RIDER_CTR);
            }
            else
            {
                contractSetupVO.setClaimStatusCT(riderName);
            }
        }
        
        contractSetupVO.setCareTypeCT(careType);
        contractSetupVO.setConditionCT(conditionCT);

        if (dateOfDeath != null)
        {
            contractSetupVO.setDateOfDeath(dateOfDeath.getFormattedDate());
        }

        ClientSetupVO clientSetupVO = contractSetupVO.getClientSetupVO(0);

        contractSetupVO.removeAllClientSetupVO();

        groupSetupVO.removeAllContractSetupVO();

        contractSetupVO.addClientSetupVO(clientSetupVO);
        groupSetupVO.addContractSetupVO(contractSetupVO);

        int taxYear = effectiveDate.getYear();

        EDITTrx editTrx = buildDefaultEDITTrx(transactionType, effectiveDate, taxYear, operator);

        editTrx.setAuthorizedSignatureCT(authorizedSignatureCT);
        if (amountOverride.isGT("0"))
        {
            editTrx.setTrxAmount(amountOverride);
        }
        if (interestOverride.isGT("0"))
        {
            editTrx.setInterestProceedsOverride(interestOverride);
        }

        clientSetupVO.addEDITTrxVO(editTrx.getAsVO());
        
        String processName = PROCESSNAME_RIDER_CLAIM;
        String optionCodeCT = segment.getOptionCodeCT();

        ProductStructure productStructure = new ProductStructure(segment.getProductStructureFK().longValue());

        try
        {
            new GroupTrx().saveGroupSetup(groupSetupVO, editTrx.getAsVO(), processName, optionCodeCT, productStructure.getProductStructurePK().longValue());
        }
        catch (EDITEventException e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * For the contractClient and option selected, create a LumpSum trx.  This trx needs to have a
     * ContractClientAllocationOvrd attached to it.
     * @param segment
     * @param operator
     * @param effectiveDate
     * @param taxYear
     * @param distributionCode
     * @param contractClientAllocation
     * @param contractClientFK
     * @param clientRoleFK
     */
    public void createLumpSumTrx(Segment segment, String operator, EDITDate effectiveDate, int taxYear, String distributionCode, ContractClientAllocation contractClientAllocation, Long contractClientFK, Long clientRoleFK)
    {
        String transactionType = "LS";
//        ContractEvent contractEvent = new ContractEvent();

//        Long masterPK = segment.getMasterFK();

        GroupSetup groupSetup = GroupSetup.initializeGroupSetupThruClientSetup(segment, transactionType, contractClientFK, clientRoleFK);
        groupSetup.setDistributionCodeCT(distributionCode);

        //Create the ContractClientAllocation Override and set into the groupSetup
        ContractClientAllocationOvrd contractClientAllocationOvrd = new ContractClientAllocationOvrd();
        contractClientAllocationOvrd.setContractClientAllocationOvrdPK(new Long(0));
        contractClientAllocationOvrd.setClientSetupFK(new Long(0));
        contractClientAllocationOvrd.setContractClientAllocationFK(new Long(contractClientAllocation.getPK()));

        ContractSetup contractSetup = (ContractSetup) groupSetup.getContractSetups().iterator().next();

        ClientSetup clientSetup = contractSetup.getClientSetup();
        clientSetup.addContractClientAllocationOvrd(contractClientAllocationOvrd);

//        EDITTrxVO editTrxVO = contractEvent.buildDefaultEDITTrx(transactionType, effDateAsString, taxYear, operator);
        buildDefaultEDITTrx(transactionType, effectiveDate, taxYear, operator);

        String processName = "LumpSum";
        String optionCodeCT = segment.getOptionCodeCT();

        ProductStructure productStructure = new ProductStructure(segment.getProductStructureFK().longValue());

        try
        {
            new GroupTrx().saveGroupSetup(groupSetup.getAsVO(), this.editTrxVO, processName, optionCodeCT, productStructure.getProductStructurePK().longValue());
        }
        catch (EDITEventException e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Hibernate Getter.
     * @return
     */
    public ClientSetup getClientSetup()
    {
        return clientSetup;
    }

    /**
     * Getter.
     * @return
     */
    public CheckAdjustment getCheckAdjustment()
    {
        return checkAdjustment;
    }

    /**
     * Setter.
     * @param checkAdjustment
     */
    public void setCheckAdjustment(CheckAdjustment checkAdjustment)
    {
        this.checkAdjustment = checkAdjustment;
    }
    
    public void setVO(EDITTrxVO editTrxVO)
    {
        this.editTrxVO = editTrxVO;
    }

    public void addEDITTrxHistory(EDITTrxHistory editTrxHistory)
    {
        getEDITTrxHistories().add(editTrxHistory);

        editTrxHistory.setEDITTrx(this);
    }


    /**
     * Finder.
     * @param editTrxPK
     * @return
     */
    public static final EDITTrx findBy_PK(Long editTrxPK)
    {
        EDITTrx editTrx = (EDITTrx) SessionHelper.get(EDITTrx.class, editTrxPK, EDITTrx.DATABASE);

        return editTrx;
    }
    
  /**
   * Finder - uses a separate Hibernate Session.
   * @param editTrxPK
   * @return
   */
  public static final EDITTrx findSeparateBy_PK(Long editTrxPK)
  {
      EDITTrx editTrx = null;
        
      String hql = " from EDITTrx editTrx" +
                   " where editTrx.EDITTrxPK = :editTrxPK";
                   
      EDITMap params = new EDITMap("editTrxPK", editTrxPK);                   
  
      Session session = null;
      
      try
      {
        session = SessionHelper.getSeparateSession(EDITTrx.DATABASE);
        
        List<EDITTrx> results = SessionHelper.executeHQL(session, hql, params, 0);
        
        if (!results.isEmpty())
        {
          editTrx = results.get(0);
        }
        
      }
      finally
      {
        if (session != null) session.close();
      }

      return editTrx;
  }    
    
    /**
   * Finds the specified EDITTrx(using a separate Hibernate Session) by PK and fetches:
   * 
   * EDITTrx.ClientSetup.ContractSetup.
   * @param editTrxPK
   * @return
   */
    public static final EDITTrx findSeparateBy_PK_V1(Long editTrxPK)
    {
      EDITTrx editTrx = null;
    
      String hql = " select editTrx from EDITTrx editTrx" +
                  " join fetch editTrx.ClientSetup clientSetup" +
                  " join fetch clientSetup.ContractSetup" +
                  " where editTrx.EDITTrxPK = :editTrxPK";
                  
      EDITMap params = new EDITMap("editTrxPK", editTrxPK);
      
      List<EDITTrx> results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);
      
      if (!results.isEmpty())
      {
        editTrx = results.get(0);
      }
      
      return editTrx;
    }
    
  public static EDITTrx getGreatestLCTrx_UsingCRUD(long segmentPK)
  {
      List voExclusionList = new ArrayList();
      voExclusionList.add(OverdueChargeRemainingVO.class);
      voExclusionList.add(EDITTrxCorrespondenceVO.class);
      voExclusionList.add(ChargeHistoryVO.class);
      voExclusionList.add(BucketHistoryVO.class);
      voExclusionList.add(InvestmentHistoryVO.class);
      voExclusionList.add(CommissionHistoryVO.class);
      voExclusionList.add(WithholdingHistoryVO.class);
      voExclusionList.add(SegmentHistoryVO.class);
      voExclusionList.add(ReinsuranceHistoryVO.class);
      voExclusionList.add(InSuspenseVO.class);

      EDITTrxVO[] editTrxVOs = event.dm.dao.DAOFactory.getEDITTrxDAO().findGreatestBySegmentPK_TrxType_Status(segmentPK, true, voExclusionList);
      EDITTrx editTrx = null;

      if (editTrxVOs != null)
      {
          editTrx = new EDITTrx(editTrxVOs[0]);
      }

      return editTrx;
  }    

    /**
     * Finder.
     * @param segmentPK
     * @param effectiveDate
     * @param cycleDate
     * @param executionMode
     * @return
     */
    public static final EDITTrx[] findBy_SegmentPK_AND_EffectiveDate_GTE_AND_LTE(long segmentPK, EDITDate effectiveDate, EDITDate cycleDate, int executionMode)
    {
        String sql = "select editTrx from EDITTrx editTrx " +
                     " join fetch editTrx.ClientSetup clientSetup" +
                     " join fetch clientSetup.ContractSetup contractSetup" +
                     " where contractSetup.SegmentFK = :segmentFK" +
                     " and (editTrx.EffectiveDate >= :effectiveDate1" +
                     " and (editTrx.EffectiveDate <= :effectiveDate2" +
                     " and editTrx.PendingStatus in ('H', 'L', 'S', 'C', 'F', 'B', 'M')" +
                     " and not editTrx.Status ='U')" +
                     " or (editTrx.EffectiveDate" + ((executionMode == ClientTrx.REALTIME_MODE) ? " <" : " <=") + " :effectiveDate3" +
                     " and editTrx.PendingStatus ='P'))";

        Map params = new HashMap();

        params.put("segmentFK", new Long(segmentPK));
        params.put("effectiveDate1", effectiveDate);
        params.put("effectiveDate2", cycleDate);
        params.put("effectiveDate3", cycleDate);

        List results = SessionHelper.executeHQL(sql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }

    /**
     * Originally in EDITTrxDAO.findBySegmentPK_AND_EffectiveDateLTE_AND_PendingStatus
     * @param segmentPK
     * @param effectiveDate
     * @param pendingStatuses
     * @return
     */
    public static EDITTrx[] findBy_SegmentPK_AND_EffectiveDateLTE_AND_PendingStatus(Long segmentPK, EDITDate effectiveDate, String[] pendingStatuses)
    {
        String hql = "select editTrx from EDITTrx editTrx " +
                     " join fetch editTrx.ClientSetup clientSetup" +
                     " join fetch clientSetup.ContractSetup contractSetup" +
                     " where contractSetup.SegmentFK = :segmentFK" +
                     " and editTrx.PendingStatus in (:pendingStatuses)" +
                     " and editTrx.Status in ('N', 'A')" +
                     " and editTrx.EffectiveDate <= :effectiveDate" +
                     " and editTrx.TransactionTypeCT not in ('MD', 'CK', 'RCK', 'MF')";

        Map params = new HashMap();

        params.put("segmentFK", segmentPK);
        params.put("pendingStatuses", pendingStatuses);
        params.put("effectiveDate", effectiveDate);

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }

    /**
     * Finder.
     * @param segmentPK
     * @param effectiveDate
     * @return
     * @throws Exception
     */
    public static EDITTrx[] findBy_SegmentPK_AND_EffectiveDateGT(long segmentPK, EDITDate effectiveDate) throws Exception
    {
        String hql = "select editTrx from EDITTrx editTrx" +
                     " join editTrx.ClientSetup clientSetup" + " join clientSetup.ContractSetup contractSetup" +
                     " where contractSetup.SegmentFK = :segmentFK" + " and editTrx.EffectiveDate >= :effectiveDate" +
                     " and editTrx.PendingStatus in ('H', 'L')";

        Map params = new HashMap();
        params.put("segmentFK", new Long(segmentPK));
        params.put("effectiveDate", effectiveDate);

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }

    /**
     * Finds the EDITTrx record whose ReapplyEDITTrxFK is equal to the reapplyEDITTrxFK parameter
     * @param reapplyEDITTrxFK
     * @return
     */
    public static EDITTrx findBy_ReapplyEDITTrxFK(Long reapplyEDITTrxFK)
    {
        EDITTrx editTrx = null;

        String hql = " from EDITTrx editTrx" + " where editTrx.ReapplyEDITTrxFK = :reapplyEDITTrxFK";

        Map params = new HashMap();
        params.put("reapplyEDITTrxFK", reapplyEDITTrxFK);

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        if (!results.isEmpty())
        {
            editTrx = (EDITTrx) results.get(0);
        }

        return editTrx;
    }

    /**
   * Finds the editTrx that was spawned by the specified trx
   *
   * Originally in EDITTrxDAO.findSpawnedTrx
   *
   * @param originatingTrxFK
   *
   * @return
   */
  public static EDITTrx[] findBy_OriginatingTrxFK(Long originatingTrxFK)
    {
        String hql = " from EDITTrx editTrx" + " where editTrx.OriginatingTrxFK = :originatingTrxFK";

        Map params = new HashMap();

        params.put("originatingTrxFK", originatingTrxFK);

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }

    /**
     * Remover.
     * @param annualizedSubBucket
     */
    public void remove(AnnualizedSubBucket annualizedSubBucket)
    {
        getAnnualizedSubBuckets().remove(annualizedSubBucket);

        annualizedSubBucket.setEDITTrx(null);
    }

    /**
     * Adder.
     * @param editTrxCorrespondence
     */
    public void addEDITTrxCorrespondence(EDITTrxCorrespondence editTrxCorrespondence)
    {
        getEDITTrxCorrespondences().add(editTrxCorrespondence);

        editTrxCorrespondence.setEDITTrx(this);
    }

    /**
     * Generates the next sequence number for this EDITTrx.
     * If there is already an EDITTrx for of the same TransactionTypeCT and EffectiveDate, the sequence number is
     * incremented by one.
     */
    public void generateSequenceNumber()
    {
        Long segmentPK = getClientSetup().getContractSetup().getSegmentFK();

    EDITTrx[] editTrxs = EDITTrx.findBy_SegmentPK_EffectiveDate_TransactionTypeCT(segmentPK, getEffectiveDate(), getTransactionTypeCT());

        int sequenceNumber = 1;

        if (editTrxs != null)
        {
            sequenceNumber = editTrxs[0].getSequenceNumber() + 1; // EDITTrxs were ordered by SequenceNumber descending.
        }

        setSequenceNumber(sequenceNumber);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return EDITTrx.DATABASE;
    }

    /**
     * Finder. Results are ordered by SequenceNumber decsending.
     * @param segmentPK
     * @param effectiveDate
     * @param transactionTypeCT
     * @return
     */
    public static EDITTrx[] findBy_SegmentPK_EffectiveDate_TransactionTypeCT(Long segmentPK, EDITDate effectiveDate, String transactionTypeCT)
    {
        String hql = " select editTrx from EDITTrx editTrx" +
                    " join editTrx.ClientSetup clientSetup" +
                    " join clientSetup.ContractSetup contractSetup" +

                    " where contractSetup.SegmentFK = :segmentFK" +
                    " and editTrx.EffectiveDate = :effectiveDate" +
                    " and editTrx.TransactionTypeCT = :transactionTypeCT" +
                    " order by editTrx.SequenceNumber desc";

        Map params = new HashMap();

        params.put("segmentFK", segmentPK);
        params.put("effectiveDate", effectiveDate);
        params.put("transactionTypeCT", transactionTypeCT);

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }

    /**
     * Finds EDITTrxs by the specified segmentPK, effectiveDate (>=), and transactionType with a pendingStatus of 'P'
     *
     * @param segmentPK
     * @param transactionTypeCT
     * @param effectiveDate
     *
     * @return  EDITTrx[]
     */
    public static EDITTrx[] findBy_SegmentPK_EffectiveDate_TransactionType_WithPendingStatus(Long segmentPK, EDITDate effectiveDate, String transactionTypeCT)
    {
        String hql = " select editTrx from EDITTrx editTrx" +
                    " join editTrx.ClientSetup clientSetup" +
                    " join clientSetup.ContractSetup contractSetup" +

                    " where contractSetup.SegmentFK = :segmentFK" +
                    " and editTrx.EffectiveDate >= :effectiveDate" +
                    " and editTrx.TransactionTypeCT = :transactionTypeCT" +
                    " and editTrx.PendingStatus = :pendingStatus";

        Map params = new HashMap();

        params.put("segmentFK", segmentPK);
        params.put("effectiveDate", effectiveDate);
        params.put("transactionTypeCT", transactionTypeCT);
        params.put("pendingStatus", "P");

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }
    
    public static EDITTrx[] findBy_ContractNumber(String contractNumber)
    {
        String hql = " select editTrx from EDITTrx editTrx" +
                    " join editTrx.ClientSetup clientSetup" +
                    " join clientSetup.ContractSetup contractSetup" +
                    " join contractSetup.Segment segment" +

                    " where segment.ContractNumber = :contractNumber" +
                    " and editTrx.Status in ('N', 'A')" +
                    " and editTrx.PendingStatus = 'H'" +
                    " order by editTrx.EffectiveDate desc";
;

        Map params = new HashMap();

        params.put("contractNumber", contractNumber);

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }


    /**
     * Finder.
     * @param transactionTypeCT
     * @param effectiveDate
     * @param pendingStatus
     * @return
     */
    public static EDITTrx[] findBy_TransactionTypeCT_AND_EffectiveDateLTE_AND_PendingStatus(String transactionTypeCT, EDITDate effectiveDate, String pendingStatus)
    {
        String hql = " from EDITTrx editTrx" +
                    " where editTrx.TransactionTypeCT = :transactionTypeCT" +
                    " and editTrx.PendingStatus = :pendingStatus" +
                    " and editTrx.EffectiveDate = :effectiveDate";

        Map params = new HashMap();

        params.put("transactionTypeCT", transactionTypeCT);
        params.put("pendingStatus", pendingStatus);
        params.put("effectiveDate", effectiveDate);

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }

    /**
     * Adder.
     * @param deposits
     */
    public void addDeposits(Deposits deposits)
    {
        getDeposits().add(deposits);

        deposits.setEDITTrx(this);
    }

    /**
     * Finder.
     * @param segmentPK
     * @param transactionTypeCT
     * @param fromDate
     * @param toDate
     * @param pendingStatus
     * @return
     */
    public static Double findSumOfFPremiumPadeBy_SegmentPK_BeforeDate(Long segmentPK, EDITDate date)
    {
        String hql = " select sum(editTrx.TrxAmount) from EDITTrx editTrx" +
                    " join editTrx.ClientSetup clientSetup" +
                    " join clientSetup.ContractSetup contractSetup" +
                    " where contractSetup.SegmentFK = :segmentFK" +
                    " and editTrx.TransactionTypeCT = 'PY' " +
                    " and editTrx.PendingStatus = 'H' " +
                    " and editTrx.EffectiveDate < :date";

        Map params = new HashMap();

        params.put("segmentFK", segmentPK);
        params.put("date", date);

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        if (results.get(0) == null) {
            return new Double(0.00);	
        } else {
            return ((Double)results.get(0)).doubleValue();
        }
    }


    /**
     * Finder.
     * @param segmentPK
     * @param transactionTypeCT
     * @param fromDate
     * @param toDate
     * @param pendingStatus
     * @return
     */
    public static int findCountBy_SegmentPK_TransactionTypeCT_EffectiveDate_AND_PendingStatus(Long segmentPK,
                                                                                                    String transactionTypeCT,
                                                                                                    EDITDate fromDate,
                                                                                                    EDITDate toDate,
                                                                                                    String pendingStatus)
    {
        String hql = " select count(editTrx) from EDITTrx editTrx" +
                    " join editTrx.ClientSetup clientSetup" +
                    " join clientSetup.ContractSetup contractSetup" +
                    " where contractSetup.SegmentFK = :segmentFK" +
                    " and editTrx.TransactionTypeCT = :transactionTypeCT" +
                    " and editTrx.PendingStatus = :pendingStatus" +
                    " and editTrx.EffectiveDate >= :fromDate" +
                    " and editTrx.EffectiveDate < :toDate";

        Map params = new HashMap();

        params.put("segmentFK", segmentPK);
        params.put("transactionTypeCT", transactionTypeCT);
        params.put("pendingStatus", pendingStatus);
        params.put("fromDate", fromDate);
        params.put("toDate", toDate);

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return ((Integer)results.get(0)).intValue();
    }

    /**
   * This returns back all EDITTrxVO for a segmentPK where the pending status is not H
   * and the transTypeCT is of a particular type.
   *
   * NOTE: Originally from EDITTrxDAO
   * @param segmentPK
   * @param transactionTypeCT
   *
   * @return
   * @throws Exception
   */
  public static EDITTrx[] findAllNotExecutedBySegmentPK(Long segmentPK, String transactionTypeCT) throws Exception
    {
        String hql = " select editTrx from EDITTrx editTrx" +
                    " join editTrx.ClientSetup clientSetup" +
                    " join clientSetup.ContractSetup contractSetup" +
                    " where contractSetup.SegmentFK = :segmentFK" +
                    " and editTrx.TransactionTypeCT = :transactionTypeCT" +
                    " and editTrx.PendingStatus <> :pendingStatus" +
                    " order by editTrx.EffectiveDate";

        Map params = new HashMap();

        params.put("segmentFK", segmentPK);
        params.put("transactionTypeCT", transactionTypeCT);
        params.put("pendingStatus", "H");      // has not executed

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }

    /**
     * Originally from EventComponent's composeEDITTrxVOByEDITTrxPK
     * @param editTrxPK
     * @return
     */
    public static EDITTrx findBy_EDITTrxPK_IncludeClientSetup_ContractSetup_Segment_Investment(Long editTrxPK)
    {
        EDITTrx editTrx = null;

        String hql = " select editTrx from EDITTrx editTrx" +
                    " join fetch editTrx.ClientSetup clientSetup" +
                    " join fetch clientSetup.ContractSetup contractSetup" +
                    " join fetch contractSetup.Segment segment" +
                    " join fetch segment.Investment investment" +
                    " where editTrx.EDITTrxPK = :editTrxPK";

        Map params = new HashMap();

        params.put("editTrxPK", editTrxPK);

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        if (!results.isEmpty())
        {
            editTrx = (EDITTrx) results.get(0);
        }

        return editTrx;
    }

    /**
     * Originally from EventComponent's composeEDITTrxVOByEDITTrxPK
     * @param editTrxPK
     * @return
     */
    public static EDITTrx findBy_EDITTrxPK_IncludeClientSetup_ContractSetup(Long editTrxPK)
    {
        EDITTrx editTrx = null;

        String hql = " select editTrx from EDITTrx editTrx" +
                    " join fetch editTrx.ClientSetup clientSetup" +
                    " join fetch clientSetup.ContractSetup contractSetup" +
                    " where editTrx.EDITTrxPK = :editTrxPK";

        Map params = new HashMap();

        params.put("editTrxPK", editTrxPK);

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        if (!results.isEmpty())
        {
            editTrx = (EDITTrx) results.get(0);
        }

        return editTrx;
    }

    /**
     * Originally from EDITTrxDAO.findByEffectiveDateAndTrxTypeCT
     * @param effectiveDate
     * @param transactionTypeCT
     * @param segmentPK
     * @return
     */
    public static EDITTrx[] findBy_EffectiveDate_TrxTypeCT(EDITDate effectiveDate, String transactionTypeCT, Long segmentPK)
    {
        String hql = " select editTrx from EDITTrx editTrx" +
                    " join editTrx.ClientSetup clientSetup" +
                    " join clientSetup.ContractSetup contractSetup" +
                    " where contractSetup.SegmentFK = :segmentPK" +
                    " and editTrx.EffectiveDate = :effectiveDate" +
                    " and editTrx.TransactionTypeCT = :transactionTypeCT";

        Map params = new HashMap();

        params.put("segmentPK", segmentPK);
        params.put("effectiveDate", effectiveDate);
        params.put("transactionTypeCT", transactionTypeCT);

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }

    /**
   * Originally in EDITTrxDAO.findTerminatedTrxForContract.  Generalized it so have to pass in pendingStatus
   * @param segmentPK
   * @return
   */
  public static EDITTrx[] findBy_SegmentPK_PendingStatus(Long segmentPK, String pendingStatus)
    {
        String hql = " select editTrx from EDITTrx editTrx" +
                    " join editTrx.ClientSetup clientSetup" +
                    " join clientSetup.ContractSetup contractSetup" +
                    " where contractSetup.SegmentFK = :segmentPK" +
                    " and editTrx.PendingStatus = :pendingStatus";

        Map params = new HashMap();

        params.put("segmentPK", segmentPK);
        params.put("pendingStatus", pendingStatus);

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }

    /**
   * Originally in compose using EDITTrxDAO.findAllBySegmentPK
   * @param segmentPK
   * @return
   * @throws Exception
   */
  public static EDITTrx[] findBy_SegmentPK_IncludeClientSetup_ContractSetup(Long segmentPK)
    {
        String hql = " select editTrx from EDITTrx editTrx" +
                    " join fetch editTrx.ClientSetup clientSetup" +
                    " join fetch clientSetup.ContractSetup contractSetup" +
                    " where contractSetup.SegmentFK = :segmentPK" +
                    " and editTrx.PendingStatus = :pendingStatus" +
                    " ordery by editTrx.EffectiveDate desc";

        Map params = new HashMap();

        params.put("segmentPK", segmentPK);
        params.put("pendingStatus", "H");

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }



    /**
   * Map array of InvestmentAllocationOverrideVOs to array of InvestmentAllocationOverrides
   *
   * @param investmentAllocationOverrideVOs           array of VOs to be mapped
   *
   * @return  Array of InvestmentAllocationOverride
   */
  private InvestmentAllocationOverride[] mapInvestmentAllocationOverrideVOsToEntities(InvestmentAllocationOverrideVO[] investmentAllocationOverrideVOs)
    {
    InvestmentAllocationOverride[] investmentAllocationOverrides = new InvestmentAllocationOverride[investmentAllocationOverrideVOs.length];

        for (int i = 0; i < investmentAllocationOverrideVOs.length; i++)
        {
            investmentAllocationOverrides[i] = new InvestmentAllocationOverride(investmentAllocationOverrideVOs[i]);
        }

        return investmentAllocationOverrides;
    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, EDITTrx.DATABASE);
    }

    public void hDelete()
    {
        SessionHelper.delete(this, EDITTrx.DATABASE);
    }

    // ***************************************

    /**
     * creates freelook transaction group setup
     * @param segmentVO
     */
//    public void createFreeLookTransactionGroupSetup(SegmentVO segmentVO)
//    {
//        ContractEvent contractEvent = new ContractEvent();
//
//        long masterPK = segmentVO.getMasterFK();
//
//        MasterVO masterVO = new MasterComposer(new ArrayList()).compose(masterPK);
//
//        GroupSetupVO groupSetupVO = contractEvent.buildDefaultGroupSetup(masterVO, new SegmentVO[] { segmentVO });
//
//        String processName = "Freelook";
//
//        String optionCodeCT = segmentVO.getOptionCodeCT();
//
//        long productStructurePK = segmentVO.getProductStructureFK();
//
//        try
//        {
//            createAllocationOverridesForFreeLookTransaction(segmentVO, groupSetupVO);
//
//            new GroupTrx().saveGroupSetup(groupSetupVO, this.editTrxVO, processName, optionCodeCT, productStructurePK);
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//
//            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
//
//            //            throw new RuntimeException(e); Don't rethrow - client layer not prepared to render this.
//        }
//    }

    /**
     * creates transfer transaction group setup as it relates to COI replenishment
     * @param segmentVO
     */
    public void createTransferTransactionGroupSetup(Segment segment, InvestmentVO investmentVO, long filteredFundFK, BigDecimal transferAmount)
    {
        ContractEvent contractEvent = new ContractEvent();

        GroupSetupVO groupSetupVO = contractEvent.buildDefaultGroupSetup(new Segment[] { segment });

        String processName = "Transfer";

        String optionCodeCT = segment.getOptionCodeCT();

        long productStructurePK = segment.getProductStructureFK().longValue();

        try
        {
            createAllocationOverridesForTransferTransaction(segment, groupSetupVO, investmentVO, filteredFundFK, transferAmount);

            new GroupTrx().saveGroupSetup(groupSetupVO, this.editTrxVO, processName, optionCodeCT, productStructurePK);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * creates HFTA transaction group setup as it relates to COI replenishment
     * @param segmentVO
     */
    public void createHFTATransactionGroupSetup(Segment segment, InvestmentVO investmentVO, long filteredFundFK, BigDecimal transferAmount, int notificationDays, String notificationDaysType)
    {
        ContractEvent contractEvent = new ContractEvent();

        GroupSetupVO groupSetupVO = contractEvent.buildDefaultGroupSetup(new Segment[] { segment });

        String processName = "HFTA";

        String optionCodeCT = segment.getOptionCodeCT();

        long productStructurePK = segment.getProductStructureFK().longValue();

        try
        {
            createAllocationOverridesForTransferTransaction(segment, groupSetupVO, investmentVO, filteredFundFK, transferAmount);

            new GroupTrx().saveGroupSetup(groupSetupVO, this.editTrxVO, processName, optionCodeCT, productStructurePK, notificationDays, notificationDaysType);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * Creates a loan payment transaction for the given Suspense
     *
     * @param suspense
     * @param cashBatchContract
     *
     * @throws EDITEventException
     */
    public static void createLoanPaymentForSuspense(Suspense suspense, Long cashBatchContractPK, String operator, Segment segment) throws EDITEventException {
    	createForSuspense(suspense, cashBatchContractPK, operator, segment, EDITTrx.TRANSACTIONTYPECT_PREMIUM_LOAN);
    }

    /**
     * Creates a premium transaction for the given Suspense
     *
     * @param suspense
     * @param cashBatchContract
     *
     * @throws EDITEventException
     */
    public static void createPremiumForSuspense(Suspense suspense, Long cashBatchContractPK, String operator, Segment segment) throws EDITEventException {
    	createForSuspense(suspense, cashBatchContractPK, operator, segment, EDITTrx.TRANSACTIONTYPECT_PREMIUM);
    }


    public static void createForSuspense(Suspense suspense, Long cashBatchContractPK, String operator, Segment segment, String transactionTypeCT) throws EDITEventException
    {
        //String transactionTypeCT = EDITTrx.TRANSACTIONTYPECT_PREMIUM;

        int taxYear = segment.getEffectiveDate().getYear();

        //  The premium effective date is the greater date between the Segment's effectiveDate and the current date
        EDITDate currentDate = new EDITDate();
        EDITDate premiumEffectiveDate;
        // use current date for UL
        if (segment.getOptionCodeCT().equals("UL")) {
        		premiumEffectiveDate = currentDate.after(segment.getEffectiveDate()) ? suspense.getEffectiveDate() : segment.getEffectiveDate();
        } else {
        		premiumEffectiveDate = currentDate.after(segment.getEffectiveDate()) ? currentDate : segment.getEffectiveDate();
        }
        		

        GroupSetupVO groupSetupVO = GroupSetup.initializeGroupSetupVO(new Segment[] {segment} );

        EDITTrx editTrx = EDITTrx.buildDefaultEDITTrx(transactionTypeCT, premiumEffectiveDate, taxYear, operator);

        ContractSetupVO contractSetupVO = groupSetupVO.getContractSetupVO()[0];

        OutSuspenseVO outSuspenseVO = OutSuspense.buildDefaultOutSuspenseVO(suspense.getSuspenseAmount(), suspense.getAsVO());

        contractSetupVO.addOutSuspenseVO(outSuspenseVO);
        contractSetupVO.setPolicyAmount(suspense.getSuspenseAmount().getBigDecimal());  // this is used to set the EDITTrx.TrxAmount
        contractSetupVO.setCostBasis(contractSetupVO.getPolicyAmount());

        SaveGroup saveGroup = new SaveGroup(groupSetupVO, editTrx.getAsVO(), EDITTrx.PROCESSNAME_COMMIT, "");

        saveGroup.build();

        ClientTrx[] clientTrxs = saveGroup.save();

        long editTrxPK = clientTrxs[0].getEDITTrxVO().getEDITTrxPK();

        DepositsVO depositsVO = Deposits.buildDefaultDepositsVO(suspense.getAsVO(),
                cashBatchContractPK, segment.getSegmentPK().longValue());

        depositsVO.setEDITTrxFK(editTrxPK);

        new Deposits(depositsVO).save();
    }

    public void generateRedemptionTrx(EDITDate effectiveDate, String trxAmount,
                                      long selectedSuspensePK, long selectedFilteredFundPK, EDITDate feeEffectiveDate) throws Exception
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        ContractSetupVO origContractSetupVO = (ContractSetupVO) originalEditTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class);
        SegmentVO segmentVO = (SegmentVO) origContractSetupVO.getParentVO(SegmentVO.class);

        String pendingStatus = hftEditTrxVO.getPendingStatus();
        ClientSetupVO hftClientSetupVO = (ClientSetupVO) hftEditTrxVO.getParentVO(ClientSetupVO.class);
        InvestmentAllocationOverrideVO[] hftInvAllocOvrdVOs = ((ContractSetupVO) hftEditTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class)).getInvestmentAllocationOverrideVO();

        GroupSetupVO groupSetupVO = new GroupSetupVO();
        groupSetupVO.setGroupSetupPK(0);

        ContractSetupVO contractSetupVO = new ContractSetupVO();
        contractSetupVO.setContractSetupPK(0);
        contractSetupVO.setGroupSetupFK(0);
        contractSetupVO.setSegmentFK(origContractSetupVO.getSegmentFK());
        contractSetupVO.setPolicyAmount(Util.roundToNearestCent(new EDITBigDecimal(trxAmount)).getBigDecimal());

        Suspense suspense = new Suspense(selectedSuspensePK);

        OutSuspenseVO outSuspenseVO = createOutSuspenseVO(trxAmount, suspense.getAsVO());
        contractSetupVO.addOutSuspenseVO(outSuspenseVO);

        hftClientSetupVO.setClientSetupPK(0);
        hftClientSetupVO.setContractSetupFK(0);
        hftClientSetupVO.removeAllEDITTrxVO();
        long haBucketFK = 0;
        if (hftEditTrxVO.getTransactionTypeCT().equalsIgnoreCase("HFTA"))
        {
            EDITTrxHistory editTrxHistory = EDITTrxHistory.findBy_EDITTrxPK(hftEditTrxVO.getEDITTrxPK());
            BucketHistoryVO[] bucketHistoryVOs = DAOFactory.getBucketHistoryDAO().findByEditTrxHistoryPK(editTrxHistory.getEDITTrxHistoryPK().longValue());
            for (int i = 0; i < bucketHistoryVOs.length; i++)
            {
                if (bucketHistoryVOs[i].getHoldingAccountIndicator() != null &&
                    bucketHistoryVOs[i].getHoldingAccountIndicator().equalsIgnoreCase("Y"))
                {
                    haBucketFK = bucketHistoryVOs[i].getBucketFK();
                    break;
                }
            }
        }

        addFromInvestmentAllocOvrd(contractSetupVO, hftInvAllocOvrdVOs, haBucketFK);

        createNewHFTEditTrxVO(effectiveDate, contractSetupVO, hftEditTrxVO.getEDITTrxPK());

        if (originalEditTrxVO.getTransactionTypeCT().equalsIgnoreCase("WI") || originalEditTrxVO.getTransactionTypeCT().equalsIgnoreCase("FS"))
        {
            //If originating trx is WI or FS, trx to be generated is an HREM
            editTrxVO.setTransactionTypeCT("HREM");
        }
        else if (originalEditTrxVO.getTransactionTypeCT().equalsIgnoreCase("DE"))
        {
            //If originating trx is DE, trx to be generated is an HDTH
            editTrxVO.setTransactionTypeCT("HDTH");
        }
        else if (originalEditTrxVO.getTransactionTypeCT().equalsIgnoreCase(TRANSACTIONTYPECT_LOAN))
        {
            //If originating trx is LO, trx to be generated is an HLOAN
            editTrxVO.setTransactionTypeCT(TRANSACTIONTYPECT_HF_LOAN);
        }
        else
        {
            //All other originating trx will generate an HFTA
            editTrxVO.setTransactionTypeCT("HFTA");

            InvestmentAllocationOverrideVO[] origInvAllocOvrdVOs = ((ContractSetupVO) originalEditTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class)).getInvestmentAllocationOverrideVO();

            if (pendingStatus.equalsIgnoreCase("H"))
            {
                addToInvestmentAllocOvrd(contractSetupVO, origInvAllocOvrdVOs, segmentVO);
            }
            else
            {
                engine.business.Lookup engineLookup = new engine.component.LookupComponent();

                ProductStructureVO[] productStructureVO = engineLookup.findProductStructureVOByPK(segmentVO.getProductStructureFK(), false, new ArrayList());

                //Final Price not yet received; Generate HFTA using HFIA as "To" Fund
                long hedgeFundInterimAccountFK = productStructureVO[0].getHedgeFundInterimAccountFK(); //Points to Fund Table

                FilteredFundVO[] filteredFundVOs = engineLookup.composeFilteredFundVOByCoStructurePK_And_FundPK(segmentVO.getProductStructureFK(), hedgeFundInterimAccountFK, new ArrayList());

                if ((filteredFundVOs != null) && (filteredFundVOs.length > 0))
                {
                    InvestmentAllocationOverrideVO investmentAllocationOverrideVO = setupHedgeFundInterimAccount(filteredFundVOs[0],
                                                                                                                 new EDITBigDecimal(contractSetupVO.getPolicyAmount()),
                                                                                                                 segmentVO.getSegmentPK(),
                                                                                                                 contractSetupVO.getContractSetupPK(),
                                                                                                                 "Dollars");

                    contractSetupVO.addInvestmentAllocationOverrideVO(investmentAllocationOverrideVO);
                }
            }
        }

        hftClientSetupVO.addEDITTrxVO(editTrxVO);

        contractSetupVO.addClientSetupVO(hftClientSetupVO);

        groupSetupVO.addContractSetupVO(contractSetupVO);

        GroupTrx groupTrx = new GroupTrx();

        EDITBigDecimal pendingSuspenseAmount = new EDITBigDecimal(suspense.getAsVO().getPendingSuspenseAmount(), 2);
        pendingSuspenseAmount = pendingSuspenseAmount.addEditBigDecimal(trxAmount);

        suspense.setPendingSuspenseAmount(pendingSuspenseAmount);

        suspense.save();

        boolean saveSuccessful = groupTrx.saveGroupSetup(groupSetupVO, editTrxVO, editTrxVO.getTransactionTypeCT(), segmentVO.getOptionCodeCT(), segmentVO.getProductStructureFK());

        if (saveSuccessful)
        {
            EDITBigDecimal notificationAmountReceived = new EDITBigDecimal(hftEditTrxVO.getNotificationAmountReceived());

            notificationAmountReceived = notificationAmountReceived.addEditBigDecimal(new EDITBigDecimal(trxAmount));

            hftEditTrxVO.setNotificationAmountReceived(Util.roundToNearestCent(notificationAmountReceived).getBigDecimal());

            eventComponent.saveEDITTrxVONonRecursively(hftEditTrxVO);

            contract.business.Lookup contractLookup = new contract.component.LookupComponent();

            InvestmentVO[] investmentVOs = contractLookup.getInvestmentByFilteredFundFKAndSegmentFK(selectedFilteredFundPK, segmentVO.getSegmentPK());

            InvestmentVO investmentVO = null;
            long chargeCodeFK = 0;

            if (investmentVOs != null)
            {
                investmentVO = investmentVOs[0];
                chargeCodeFK = investmentVO.getChargeCodeFK();
            }

            EDITBigDecimal feeAmount = new EDITBigDecimal(trxAmount);

            //8/1/2006 - modified logic to use user-entered effective date for DFCASH
            createDFCASHFeeTrx(selectedFilteredFundPK, feeAmount, feeEffectiveDate, chargeCodeFK, false);
        }
    }

    private OutSuspenseVO createOutSuspenseVO(String trxAmount, SuspenseVO suspenseVO) throws Exception
    {
        OutSuspenseVO outSuspenseVO = new OutSuspenseVO();
        outSuspenseVO.setOutSuspensePK(0);
        outSuspenseVO.setSuspenseFK(suspenseVO.getSuspensePK());
        outSuspenseVO.setContractSetupFK(0);
        outSuspenseVO.setAmount(Util.roundToNearestCent(new EDITBigDecimal(trxAmount)).getBigDecimal());

        return outSuspenseVO;
    }

    private void addFromInvestmentAllocOvrd(ContractSetupVO contractSetupVO,
                                            InvestmentAllocationOverrideVO[] hftInvAllocOvrdVOs,
                                            long haBucketFK) throws Exception
    {
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        List investmentVOInclusionList = new ArrayList();
        investmentVOInclusionList.add(InvestmentAllocationVO.class);

        for (int i = 0; i < hftInvAllocOvrdVOs.length; i++)
        {
            if (hftInvAllocOvrdVOs[i].getToFromStatus().equalsIgnoreCase("T"))
            {
                Investment hftInvestment = new Investment(contractLookup.composeInvestmentVOByPK(hftInvAllocOvrdVOs[i].getInvestmentFK(), investmentVOInclusionList));
                InvestmentAllocation investmentAllocation = new InvestmentAllocation(hftInvestment.getInvestmentAllocationVOs());
                long hftInvAllocPK = investmentAllocation.getPKForAllocationDollars(new EDITBigDecimal(contractSetupVO.getPolicyAmount()));

                if (hftInvAllocPK == 0)
                {
                    hftInvAllocPK = createNewInvestmentAllocation(hftInvestment, Util.roundToNearestCent(contractSetupVO.getPolicyAmount()).getBigDecimal());
                }

                InvestmentAllocationOverrideVO newHFTInvAllocOvrdVO = new InvestmentAllocationOverrideVO();
                newHFTInvAllocOvrdVO.setInvestmentAllocationOverridePK(0);
                newHFTInvAllocOvrdVO.setContractSetupFK(0);
                newHFTInvAllocOvrdVO.setInvestmentFK(hftInvAllocOvrdVOs[i].getInvestmentFK());
                newHFTInvAllocOvrdVO.setInvestmentAllocationFK(hftInvAllocPK);
                newHFTInvAllocOvrdVO.setHFStatus(null);
                newHFTInvAllocOvrdVO.setHFIAIndicator("N");
                newHFTInvAllocOvrdVO.setHoldingAccountIndicator("Y");
                newHFTInvAllocOvrdVO.setToFromStatus("F");
                newHFTInvAllocOvrdVO.setBucketFK(haBucketFK);

                contractSetupVO.addInvestmentAllocationOverrideVO(newHFTInvAllocOvrdVO);
            }
        }
    }

    private void addToInvestmentAllocOvrd(ContractSetupVO contractSetupVO,
                                          InvestmentAllocationOverrideVO[] origInvAllocOvrdVOs,
                                          SegmentVO segmentVO) throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        Hashtable hedgeFundAllocations = new Hashtable();

        List investmentVOInclusionList = new ArrayList();
        investmentVOInclusionList.add(InvestmentAllocationVO.class);

        for (int i = 0; i < origInvAllocOvrdVOs.length; i++)
        {
            if (origInvAllocOvrdVOs[i].getToFromStatus().equalsIgnoreCase("T"))
            {
                FundVO fundVO = (FundVO) origInvAllocOvrdVOs[i].getParentVO(InvestmentVO.class).getParentVO(FilteredFundVO.class).getParentVO(FundVO.class);

                Investment hftInvestment = new Investment(contractLookup.composeInvestmentVOByPK(origInvAllocOvrdVOs[i].getInvestmentFK(), investmentVOInclusionList));
//                InvestmentAllocation investmentAllocation = new InvestmentAllocation(hftInvestment.getInvestmentAllocationVOs());
//                long hftInvAllocPK = investmentAllocation.getPKForAllocationDollars(contractSetupVO.getPolicyAmount());
                long hftInvAllocPK = origInvAllocOvrdVOs[i].getInvestmentAllocationFK();

                if (hftInvAllocPK == 0)
                {
                    hftInvAllocPK = createNewInvestmentAllocation(hftInvestment, Util.roundToNearestCent(contractSetupVO.getPolicyAmount()).getBigDecimal());
                }

                InvestmentAllocationOverrideVO newHFTInvAllocOvrdVO = new InvestmentAllocationOverrideVO();
                newHFTInvAllocOvrdVO.setInvestmentAllocationOverridePK(0);
                newHFTInvAllocOvrdVO.setContractSetupFK(0);
                newHFTInvAllocOvrdVO.setInvestmentFK(origInvAllocOvrdVOs[i].getInvestmentFK());
                newHFTInvAllocOvrdVO.setInvestmentAllocationFK(hftInvAllocPK);
                newHFTInvAllocOvrdVO.setHFStatus("A");
                newHFTInvAllocOvrdVO.setHFIAIndicator("N");
                newHFTInvAllocOvrdVO.setToFromStatus("T");

                if (fundVO.getFundType().equalsIgnoreCase("Hedge"))
                {
                    InvestmentAllocationVO[] invAllocVOs = contractLookup.findInvestmentAllocationByInvestmentAllocationPK(origInvAllocOvrdVOs[i].getInvestmentAllocationFK(), false, new ArrayList());
                    for (int k = 0; k < invAllocVOs.length; k++)
                    {
                        EDITBigDecimal[] invAllocValues = new EDITBigDecimal[2];
                        invAllocValues[0] = new EDITBigDecimal(invAllocVOs[k].getAllocationPercent());
                        invAllocValues[1] = new EDITBigDecimal(invAllocVOs[k].getDollars());

                        hedgeFundAllocations.put(invAllocVOs[k].getInvestmentFK() + "", invAllocValues);

                        newHFTInvAllocOvrdVO.setHFStatus("N");

                        k = invAllocVOs.length;
                    }
                }

                contractSetupVO.addInvestmentAllocationOverrideVO(newHFTInvAllocOvrdVO);
            }
        }

        Enumeration hfAllocKeys = hedgeFundAllocations.keys();

        long productStructureFK = segmentVO.getProductStructureFK();

        ProductStructureVO[] productStructureVO = engineLookup.findProductStructureVOByPK(productStructureFK, false, new ArrayList());
        FilteredFundVO[] hfiaFilteredFundVO = engineLookup.composeFilteredFundVOByCoStructurePK_And_FundPK(productStructureFK, productStructureVO[0].getHedgeFundInterimAccountFK(), new ArrayList());

        while (hfAllocKeys.hasMoreElements())
        {
            String hfInvestmentPK = (String) hfAllocKeys.nextElement();
            EDITBigDecimal[] hfInvAllocValues = (EDITBigDecimal[]) hedgeFundAllocations.get(hfInvestmentPK);

            EDITBigDecimal hfiaAllocation = new EDITBigDecimal();
            String allocationType = "";
            //change the allocation percent/dollars override for the hfia - the hedge fund allocation changed
            if (hfInvAllocValues[0].isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
            {
                hfiaAllocation = hfInvAllocValues[0];
                allocationType = "Percent";
            }
            else
            {
                hfiaAllocation = hfInvAllocValues[1];
                allocationType = "Dollars";
            }

            InvestmentAllocationOverrideVO investmentAllocationOverrideVO =
                    setupHedgeFundInterimAccount(hfiaFilteredFundVO[0],
                                                 hfiaAllocation,
                                                 segmentVO.getSegmentPK(),
                                                 contractSetupVO.getContractSetupPK(),
                                                 allocationType);

            investmentAllocationOverrideVO.setHedgeFundInvestmentFK(Long.parseLong(hfInvestmentPK));

            contractSetupVO.addInvestmentAllocationOverrideVO(investmentAllocationOverrideVO);
        }
    }

    private void createNewHFTEditTrxVO(EDITDate effectiveDate, ContractSetupVO contractSetupVO, long selectedEditTrxPK)
    {
        editTrxVO = new EDITTrxVO();
        editTrxVO.setEDITTrxPK(0);
        editTrxVO.setClientSetupFK(0);
        editTrxVO.setEffectiveDate(effectiveDate.getFormattedDate());
        editTrxVO.setStatus("N");
        editTrxVO.setPendingStatus("P");
        editTrxVO.setSequenceNumber(1);
        editTrxVO.setTaxYear(effectiveDate.getYear());
        editTrxVO.setTrxAmount(contractSetupVO.getPolicyAmount());
        editTrxVO.setOriginatingTrxFK(selectedEditTrxPK);
        editTrxVO.setNoCorrespondenceInd("Y");
        editTrxVO.setNoAccountingInd("N");
        editTrxVO.setNoCommissionInd("N");
        editTrxVO.setZeroLoadInd("N");
        editTrxVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
        editTrxVO.setOperator(operator);
        editTrxVO.setTransferTypeCT("HFReceipt");
        editTrxVO.setPremiumDueCreatedIndicator("N");

    }

    /**
     * Adds an Investment record for the Hedge Fund Interim Account
     * @param filteredFundVO
     * @param hfiaAllocation
     * @param segmentFK
     * @param contractSetupPK
     * @param allocationType
     * @return
     * @throws Exception
     */
    private InvestmentAllocationOverrideVO setupHedgeFundInterimAccount(FilteredFundVO filteredFundVO,
                                                                        EDITBigDecimal hfiaAllocation,
                                                                        long segmentFK,
                                                                        long contractSetupPK,
                                                                        String allocationType) throws Exception
    {
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(InvestmentAllocationVO.class);

        InvestmentVO[] investmentVOs = contractLookup.composeInvestmentVO(segmentFK, null, voInclusionList, editTrxVO.getTransactionTypeCT());

        boolean hfiaFound = false;

        InvestmentAllocationOverrideVO investmentAllocationOverrideVO = new InvestmentAllocationOverrideVO();

        for (int i = 0; i < investmentVOs.length; i++)
        {
            Investment investment = new Investment(investmentVOs[i]);

            if (investment.getFilteredFundFK().longValue() == filteredFundVO.getFilteredFundPK())
            {
                InvestmentAllocation investmentAllocation = new InvestmentAllocation(investment.getInvestmentAllocationVOs());

                long investmentAllocationPK = 0;

                if (allocationType.equalsIgnoreCase("Percent"))
                {
                    investmentAllocationPK = investmentAllocation.getPKForAllocationPercent(hfiaAllocation.getBigDecimal());
                }
                else
                {
                    investmentAllocationPK = investmentAllocation.getPKForAllocationDollars(hfiaAllocation);
                }

                if (investmentAllocationPK > 0)
                {
                    InvestmentAllocationOverride investmentAllocationOverride = new InvestmentAllocationOverride(contractSetupPK, investment.getPK(), investmentAllocationPK, "T", "A", "Y", "N");
                    investmentAllocationOverrideVO = investmentAllocationOverride.getVO();
                    hfiaFound = true;
                }
                else
                {
                    investmentAllocation = new InvestmentAllocation(investment.getPK(), hfiaAllocation.getBigDecimal(), "O", allocationType);

                    investmentAllocation.save();

                    long newInvestmentAllocationPK = investmentAllocation.getNewInvestmentAllocationPK();

                    InvestmentAllocationOverride investmentAllocationOverride = new InvestmentAllocationOverride(contractSetupPK, investment.getPK(), newInvestmentAllocationPK, "T", "A", "Y", "N");
                    investmentAllocationOverrideVO = investmentAllocationOverride.getVO();
                    hfiaFound = true;
                }
            }
        }

        if (!hfiaFound)
        {
            Investment investment = new Investment(segmentFK, filteredFundVO.getFilteredFundPK());

            investment.save();

            long newInvestmentPK = investment.getNewInvestmentPK();

            InvestmentAllocation investmentAllocation = null;

            investmentAllocation = new InvestmentAllocation(newInvestmentPK, hfiaAllocation.getBigDecimal(), "O", allocationType);

            investmentAllocation.save();

            long newInvestmentAllocationPK = investmentAllocation.getNewInvestmentAllocationPK();

            InvestmentAllocationOverride investmentAllocationOverride = new InvestmentAllocationOverride(contractSetupPK, newInvestmentPK, newInvestmentAllocationPK, "T", "A", "Y", "N");
            investmentAllocationOverrideVO = investmentAllocationOverride.getVO();
        }

        return investmentAllocationOverrideVO;
    }

    /**
     * creates allocation overrides for free look transaction
     * @param segmentVO
     * @param groupSetupVO
     */
    private void createAllocationOverridesForFreeLookTransaction(Segment segment, GroupSetupVO groupSetupVO) throws Exception
    {
        ContractSetupVO contractSetupVO = groupSetupVO.getContractSetupVO(0);

        InvestmentVO investmentVO = null;

        InvestmentAllocationVO investmentAllocationVO = null;

        InvestmentVO[] investmentVOs = null;

        List voExclusionList = new ArrayList();
        voExclusionList.add(InvestmentAllocationOverrideVO.class);
        voExclusionList.add(InvestmentHistoryVO.class);
        voExclusionList.add(BucketVO.class);

        // get all the investments that are primary
        investmentVOs = new InvestmentDAO().findBySegmentPKAndInvestmentAllocationOverrideStatus(segment.getPK(), "P", true, voExclusionList);

        if (investmentVOs != null)
        {
            for (int i = 0; i < investmentVOs.length; i++)
            {
                investmentVO = investmentVOs[i];

                InvestmentAllocationVO[] investmentAllocationVOs = investmentVO.getInvestmentAllocationVO();

                for (int j = 0; j < investmentAllocationVOs.length; j++)
                {
                    if (investmentAllocationVOs[j].getOverrideStatus().equalsIgnoreCase("P"))
                    {
                        investmentAllocationVO = investmentAllocationVOs[j];
                        j = investmentAllocationVOs.length;
                    }
                }

                InvestmentAllocationOverrideVO investmentAllocationOverrideVO = new InvestmentAllocationOverrideVO();

                investmentAllocationOverrideVO.setInvestmentAllocationOverridePK(0);
                investmentAllocationOverrideVO.setInvestmentFK(investmentVO.getInvestmentPK());
                investmentAllocationOverrideVO.setInvestmentAllocationFK(investmentAllocationVO.getInvestmentAllocationPK());
                investmentAllocationOverrideVO.setToFromStatus("T");

                contractSetupVO.addInvestmentAllocationOverrideVO(investmentAllocationOverrideVO);
            }
        }

        // get all the investments that are with override status 'O'
        investmentVOs = new InvestmentDAO().findBySegmentPKAndInvestmentAllocationOverrideStatus(segment.getPK(), "O", true, voExclusionList);

        if (investmentVOs != null)
        {
            for (int i = 0; i < investmentVOs.length; i++)
            {
                long freelookFundPK = getFreelookFundPK(segment);

                // to make sure that the fund is freelook fund
                if (investmentVOs[i].getFilteredFundFK() == freelookFundPK)
                {
                    Investment investment = new Investment(investmentVOs[i]);

                    InvestmentAllocation investmentAllocation = new InvestmentAllocation(investment.getInvestmentAllocationVOs());
                    long investmentAllocationPK = investmentAllocation.getPKForOverrideAllocationPercent(new BigDecimal(1.0));

                    if (investmentAllocationPK == 0)
                    {
                        //create InvestmentAllocation for 1.0 percent
                        investmentAllocationPK = setupNewInvestmentAllocation(investmentVOs[i].getInvestmentPK(), new EDITBigDecimal("1"));
                    }

                    // create allocation override
                    InvestmentAllocationOverrideVO investmentAllocationOverrideVO = new InvestmentAllocationOverrideVO();

                    investmentAllocationOverrideVO.setInvestmentAllocationOverridePK(0);
                    investmentAllocationOverrideVO.setInvestmentFK(investmentVOs[i].getInvestmentPK());
                    investmentAllocationOverrideVO.setInvestmentAllocationFK(investmentAllocationPK);
                    investmentAllocationOverrideVO.setToFromStatus("F");

                    contractSetupVO.addInvestmentAllocationOverrideVO(investmentAllocationOverrideVO);

                    i = investmentVOs.length;
                }
            }
        }
    }

    /**
     * Creates allocation overrides for the transfer transaction (TF/HFTA) as it relates to COI replenishment
     * @param segmentVO
     * @param groupSetupVO
     */
    private void createAllocationOverridesForTransferTransaction(Segment segment, GroupSetupVO groupSetupVO, InvestmentVO fromInvestmentVO, long filteredFundFK, BigDecimal transferAmount) throws Exception
    {
        ContractSetupVO contractSetupVO = groupSetupVO.getContractSetupVO(0);

        //Set up the from fund
        long fromInvAllocPK = getInvestmentAllocationPK(fromInvestmentVO, transferAmount);

        InvestmentAllocationOverrideVO fromIAOverrideVO = createInvestmentAllocationOverrideVO(fromInvestmentVO.getInvestmentPK(), fromInvAllocPK, "F");

        contractSetupVO.addInvestmentAllocationOverrideVO(fromIAOverrideVO);

        //Set up the to fund
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();
        InvestmentVO[] toInvestmentVO = contractLookup.getInvestmentByFilteredFundFKAndSegmentFK(filteredFundFK, segment.getSegmentPK().longValue());

        if (toInvestmentVO != null)
        {
            long toInvAllocPK = getInvestmentAllocationPK(toInvestmentVO[0], transferAmount);

            InvestmentAllocationOverrideVO toIAOverrideVO = createInvestmentAllocationOverrideVO(toInvestmentVO[0].getInvestmentPK(), toInvAllocPK, "T");

            contractSetupVO.addInvestmentAllocationOverrideVO(toIAOverrideVO);
        }
        else
        {
            Investment investment = new Investment(segment.getSegmentPK().longValue(), filteredFundFK);

            investment.save();

            long toInvestmentPK = investment.getNewInvestmentPK();

            InvestmentAllocation investmentAllocation = new InvestmentAllocation(toInvestmentPK, transferAmount, "O", "Dollars");
            investmentAllocation.save();

            long toInvAllocPK = investmentAllocation.getNewInvestmentAllocationPK();

            InvestmentAllocationOverrideVO toIAOverrideVO = createInvestmentAllocationOverrideVO(toInvestmentPK, toInvAllocPK, "T");

            contractSetupVO.addInvestmentAllocationOverrideVO(toIAOverrideVO);
        }
    }

    /**
     * return freelook fund PK
     * @param segmentVO
     * @return
     */
    private long getFreelookFundPK(SegmentVO segmentVO)
    {
        long productStructurePK = segmentVO.getProductStructureFK();
        String areaCT = segmentVO.getIssueStateCT();
        String qualifierCT = "*";
        String grouping = "FREELOOKPROCESS";
        EDITDate effectiveDate = new EDITDate(segmentVO.getEffectiveDate());
        String field = "FREELOOKFUND";

        Area area = new Area(productStructurePK, areaCT, grouping, effectiveDate, qualifierCT);

        AreaValue areaValue = area.getAreaValue(field);

        AreaValueVO areaValueVO = (AreaValueVO) areaValue.getVO();

        String fundNumber = areaValueVO.getAreaValue();

        long filteredFundPK = 0;

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        FilteredFundVO[] filteredFundVOs = new edit.common.vo.FilteredFundVO[0];

        try
        {
            filteredFundVOs = engineLookup.getByFundNumber(fundNumber);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        if (filteredFundVOs != null)
        {
            filteredFundPK = filteredFundVOs[0].getFilteredFundPK();
        }

        return filteredFundPK;
    }

    /**
     * Finder.
     * @param reinsuranceHistoryPK
     * @return
     */
    public static EDITTrx findBy_ReinsuranceHistoryPK(long reinsuranceHistoryPK)
    {
        EDITTrx editTrx = null;

        EDITTrxVO[] editTrxVOs = new EDITTrxDAO().findBy_ReinsuranceHistoryPK(reinsuranceHistoryPK);

        if (editTrxVOs != null)
        {
            editTrx = new EDITTrx(editTrxVOs[0]);
        }

        return editTrx;
    }

    private long getInvestmentAllocationPK(InvestmentVO investmentVO, BigDecimal transferAmount) throws Exception
    {
        InvestmentAllocation investmentAllocation = new InvestmentAllocation(investmentVO.getInvestmentAllocationVO());

        //Set up the from fund
        long investmentAllocationPK = investmentAllocation.getPKForAllocationDollars(new EDITBigDecimal(transferAmount));

        if (investmentAllocationPK == 0)
        {
            InvestmentAllocation newInvestmentAllocation = new InvestmentAllocation(investmentVO.getInvestmentPK(), transferAmount, "O", "Dollars");
            newInvestmentAllocation.save();
            investmentAllocationPK = newInvestmentAllocation.getNewInvestmentAllocationPK();
        }

        return investmentAllocationPK;
    }

    private InvestmentAllocationOverrideVO createInvestmentAllocationOverrideVO(long investmentFK, long investmentAllocationFK, String toFromStatus)
    {
        InvestmentAllocationOverrideVO iaOverrideVO = new InvestmentAllocationOverrideVO();

        iaOverrideVO.setInvestmentAllocationOverridePK(0);
        iaOverrideVO.setInvestmentFK(investmentFK);
        iaOverrideVO.setInvestmentAllocationFK(investmentAllocationFK);
        iaOverrideVO.setToFromStatus(toFromStatus);

        return iaOverrideVO;
    }

    public void createDFCASHFeeTrx(long selectedFilteredFundPK, EDITBigDecimal feeAmount, EDITDate effectiveDate, long chargeCodeFK, boolean isReversal)
    {
        String feeTypeCT = null;

        // If fee is being created as part of reversal process
        if (isReversal)
        {
            // In case of reversal we will look at the transaction type of the transaction to be reversed to determine fee type.
            if (this.hftEditTrxVO.getTransactionTypeCT().equalsIgnoreCase(TRANSACTIONTYPECT_HF_TRANSFER_AMT))
            {
                feeTypeCT = Fee.TRANSFER_FEE_TYPE;
            }
            else if (this.hftEditTrxVO.getTransactionTypeCT().equalsIgnoreCase(TRANSACTIONTYPECT_HF_DEATH))
            {
                feeTypeCT = Fee.DEATH_FEE_TYPE;
            }
            else if (this.hftEditTrxVO.getTransactionTypeCT().equalsIgnoreCase(TRANSACTIONTYPECT_HF_LOAN))
            {
                feeTypeCT = Fee.LOAN_FEE_TYPE;
            }
            else if (this.hftEditTrxVO.getTransactionTypeCT().equalsIgnoreCase("HREM"))
            {
                feeTypeCT = Fee.SURRENDER_FEE_TYPE;
            }
        }
        else
        {
            String orginalEDITTrxType = originalEditTrxVO.getTransactionTypeCT();

            if (orginalEDITTrxType.equalsIgnoreCase("TF"))
            {
                feeTypeCT = Fee.TRANSFER_FEE_TYPE;
            }
            else if (orginalEDITTrxType.equalsIgnoreCase("DE"))
            {
                feeTypeCT = Fee.DEATH_FEE_TYPE;
            }
            else if (orginalEDITTrxType.equalsIgnoreCase("LO"))
            {
                feeTypeCT = Fee.LOAN_FEE_TYPE;
            }
            else if (orginalEDITTrxType.equalsIgnoreCase("FS") || orginalEDITTrxType.equalsIgnoreCase("WI")
                    || orginalEDITTrxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SURRENDER_OVERLOAN))
            {
                feeTypeCT = Fee.SURRENDER_FEE_TYPE;
            }
        }

        FeeDescription feeDescription = FeeDescription.findByFilteredFundPK_And_FeeTypeCT(selectedFilteredFundPK, feeTypeCT);
        long feeDescriptionPK = 0;

        if (feeDescription != null)
        {
            feeDescriptionPK = ((FeeDescriptionVO) feeDescription.getVO()).getFeeDescriptionPK();
        }

        FeeVO feeVO = new FeeVO();

        feeVO.setFilteredFundFK(selectedFilteredFundPK);
        feeVO.setFeeDescriptionFK(feeDescriptionPK);
        feeVO.setEffectiveDate(effectiveDate.getFormattedDate());
        feeVO.setProcessDateTime(new EDITDateTime().getFormattedDateTime());
        feeVO.setReleaseDate(new EDITDate().getFormattedDate());

        // by default the DCASH are released
        feeVO.setReleaseInd("Y");
        feeVO.setStatusCT("N");

        // when the fee is released the 'AccountingPendingStatus' is 'Y'
        feeVO.setAccountingPendingStatus("Y");
        feeVO.setTrxAmount(feeAmount.getBigDecimal());
        feeVO.setTransactionTypeCT(Fee.DIVISION_FEE_CASH_TRX_TYPE);
        feeVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
        feeVO.setOperator("System");
        if (isReversal)
        {
            feeVO.setToFromInd("T");
        }
        else
        {
            feeVO.setToFromInd("F");
        }
        feeVO.setChargeCodeFK(chargeCodeFK);

        Fee fee = new Fee(feeVO);

        try
        {
            fee.save();
        }
        catch (EDITEngineException e)
        {
            System.out.println(e);

            e.printStackTrace();
        }
    }

    /**
   * Retrieve all held MD transactions (status "X")
   * @param segment
   * @return
   */
  public static EDITTrx[] findHeldMDEditTrx(Segment segment)
     {
         String hql = " select editTrx from EDITTrx editTrx " +
                      " join editTrx.ClientSetup clientSetup" +
                      " join clientSetup.ContractSetup contractSetup" +
                      " join contractSetup.Segment segment" +
                      " where editTrx.PendingStatus in ('X')" +
                      " and segment = :segment";

         Map params = new HashMap();

         params.put("segment", segment);

         List results =  SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

         return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
     }



    /**
     * Retrieve the EDITTrx for the given editTrxPK
     * @param editTrxPK  - The primary key for the EDITTrx that is to be retrieved.
     * @return
     */
    public static EDITTrx findByPK(Long editTrxPK)
    {
        return (EDITTrx) SessionHelper.get(EDITTrx.class, editTrxPK, EDITTrx.DATABASE);
    }

    /**
     * Finder ComplexChange and its Premium Tamra Retest transactions for a given segment and effectiveDate
     * @param segmentPK
     * @param effectiveDate
     * @return
     */
    public static EDITTrxVO[] findPartialTamraRetest_UsingCRUD(long segmentPK, EDITDate effectiveDate)
    {
        //first get the CC trx
        EDITTrxVO[] ccEditTrxVOs = new EDITTrxDAO().findComplexChangeTrx(segmentPK, effectiveDate);
        EDITTrxVO[] editTrxVOs = null;
        if (ccEditTrxVOs == null)
        {
            return ccEditTrxVOs;
        }
        else
        {
            boolean startNew7PayIndicatorSet = Life.hasStartNew7PayIndicator(ccEditTrxVOs[0].getEDITTrxPK());

            if (startNew7PayIndicatorSet)
            {
                editTrxVOs = new EDITTrxDAO().findPartialTamraRetest(segmentPK, new EDITDate(ccEditTrxVOs[0].getEffectiveDate()), effectiveDate);
                editTrxVOs = (EDITTrxVO[])Util.joinArrays(ccEditTrxVOs, editTrxVOs, EDITTrxVO.class);
                return editTrxVOs;
            }
            else
            {
                return null;
            }
        }
    }
    
  /**
   * Finder. The (Hibernate Separate Session) version of EDITTrx.findPartialTamraRetest_UsingCRUD().
   * @param segmentPK
   * @param effectiveDate
   * @return
   */
  public static EDITTrx[] findSeparatePartialTamraRetest(Long segmentPK, EDITDate effectiveDate)
  {
      //first get the CC trx
      EDITTrx[] ccEditTrxs = EDITTrx.findSeparateComplexChangeTrx(segmentPK, effectiveDate);
      
      EDITTrx[] editTrxs = null;
      
      if (ccEditTrxs.length == 0)
      {
          return ccEditTrxs;
      }
      else
      {
         Life life = ccEditTrxs[0].getClientSetup().getContractSetup().getSegment().getLife();
      
          boolean startNew7PayIndicatorSet = life.hasStartNew7PayIndicator();

          if (startNew7PayIndicatorSet)
          {
              editTrxs = findPartialTamraRetest(segmentPK, ccEditTrxs[0].getEffectiveDate(), effectiveDate);
              
              editTrxs = (EDITTrx[])Util.joinArrays(ccEditTrxs, editTrxs, EDITTrx.class);
              
              return editTrxs;
          }
          else
          {
              return null;
          }
      }
  }    

    /**
     * Check the edit trx table for a DeathPending trx for a segment
     * @param segmentPK
     * @return
     */
    public EDITTrx findDPTrxBySegmentPK(long segmentPK)
    {
        EDITTrx editTrx = null;

        EDITDate minDate = new EDITDate(EDITDate.DEFAULT_MIN_DATE);
        EDITDate maxDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);

        EDITTrxVO[] editTrxVOs = new EDITTrxDAO().findBySegment_TransactionTypeCT_EffectiveDate_AND_PendingStatus(segmentPK, "DP", minDate.getFormattedDate(), maxDate.getFormattedDate(), "H");

        if (editTrxVOs != null)
        {
            editTrx = new EDITTrx(editTrxVOs[0]);
        }

        return editTrx;
    }

    /**
   * Finds all Pending editTrx with the specified groupSetupPK
   *
   * @param groupSetupPK
   *
   * @return  array of EDITTrxs
   */
  public static EDITTrx[] findPendingBy_GroupSetupPK_UsingCRUD(long groupSetupPK)
    {
       EDITTrx editTrx = null;

        EDITTrxVO[] editTrxVOs = new EDITTrxDAO().findPendingBy_GroupSetupPK(groupSetupPK);

        return (EDITTrx[]) CRUDEntityImpl.mapVOToEntity(editTrxVOs, EDITTrx.class);

    }

    /**
   * Finds all Pending editTrx with the specified groupSetupPK
   *
   * @param groupSetupPK
   *
   * @return  array of EDITTrxs
   */
  public static EDITTrx[] findPendingBy_GroupSetupPK(Long groupSetupPK)
    {
        String hql = " select editTrx from EDITTrx editTrx " +
                     " join editTrx.ClientSetup clientSetup" +
                     " join clientSetup.ContractSetup contractSetup" +
                     " join contractSetup.GroupSetup groupSetup" +
                     " where editTrx.PendingStatus = :pendingStatus" +
                     " and groupSetup.GroupSetupPK = :groupSetupPK";

        Map params = new HashMap();

        params.put("pendingStatus", "P");
        params.put("groupSetupPK", groupSetupPK);

        List results =  SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }

    /**
     * Determines if the transaction is commissionable as (originally) setup in the TransactionPriority.CommissionableEventInd field.
     * A TransactionPriority.CommissionableEventInd = 'Y' maps to EDITTrx.CommissionStatus = 'P'.
     * @return true if this EDITTrx is commissionable
     */
    public boolean isCommissionable()
    {
        boolean isCommissionableEvent = false;

        if (Util.initString(getCommissionStatus(), "").equalsIgnoreCase("P"))
        {
            isCommissionableEvent = true;
        }

        return isCommissionableEvent;
    }

	/**
	 * Get the editTrx for the contract, with the greatest sequence number
     * @param segment
     * @param effectiveDate
     * @param transactionTypeCT
     * @return
     */
    public static EDITTrx findMaxSequenceByEffectiveDate_TrxType(Segment segment, EDITDate effectiveDate, String transactionTypeCT)
    {
        String hql = " select editTrx from EDITTrx editTrx " +
                     " join editTrx.ClientSetup clientSetup " +
                     " join clientSetup.ContractSetup contractSetup" +
                     " join contractSetup.Segment segment " +
//                     " where editTrx.PendingStatus in ('P', 'M', 'H')" +
                     " where segment = :segment and editTrx.TransactionTypeCT = :trxCode" +
                     " and editTrx.EffectiveDate = :effectiveDate" +
                     " and editTrx.SequenceNumber = (select max(editTrx2.SequenceNumber) from EDITTrx editTrx2 " +
                     " join editTrx2.ClientSetup clientSetup2" +
                     " join clientSetup2.ContractSetup contractSetup2" +
                     " join contractSetup2.Segment segment2" +
//                     " where editTrx2.PendingStatus in ('P', 'M', 'H')" +
                     " where segment2 = :segment and editTrx2.TransactionTypeCT = :trxCode" +
                     " and editTrx2.EffectiveDate = :effectiveDate)";

        Map params = new HashMap();

        params.put("segment", segment);
        params.put("effectiveDate", effectiveDate);
        params.put("trxCode", transactionTypeCT);

        List results =  SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        if (results.isEmpty())
        {
            return null;
        }

        return (EDITTrx) results.get(0);
	}

	/**
     * If one one the qualifiying transactions types is detected, all pending transaction for this contract will be looked
     * at to determine if they must be terminated.
     * @param editTrxVO
     * @param segmentVO
     * @param crud
     */
    public static void checkForTerminatingTransaction(EDITTrxVO editTrxVO, SegmentVO segmentVO, CRUD crud)
    {
        String transactionType = editTrxVO.getTransactionTypeCT();
        String status = editTrxVO.getStatus();
        EDITDate trxEffectiveDate = new EDITDate(editTrxVO.getEffectiveDate());
        boolean terminatingTransactionType = false;

        if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_NOTTAKEN) ||
            transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_FULLSURRENDER) ||
            transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SURRENDER_OVERLOAN) ||
            transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_DEATH))
        {
            terminatingTransactionType = true;
        }
        else if (segmentVO.getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_TERMINATED))
        {
            terminatingTransactionType = true;
            transactionType = EDITTrx.TRANSACTIONTYPECT_CLAIM_PAYOUT;
        }
        else if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_FULLSURRENDER) ||
                 transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_DEATH))
        {
                if (!status.equals("A"))
                {
                    terminatingTransactionType = true;
                }
        }

        if (terminatingTransactionType)
        {
            Segment segment = new Segment(segmentVO);
            try
            {
                segment.updatePendingTransactions(crud, transactionType, trxEffectiveDate);
            }
            catch (Exception e)
            {
              System.out.println(e);

                e.printStackTrace();
            }
        }
    }

    /**
     * Finder all Tamra Retest transactions for a given segment and effectiveDate
     * @param segmentPK
     * @param effectiveDate
     * @return
     */
    public static EDITTrxVO[] findAllTamraRetest_UsingCRUD(long segmentPK, EDITDate effectiveDate)
    {
        EDITTrxVO[] editTrxVOs = new EDITTrxDAO().findAllTamraRetest(segmentPK, effectiveDate);
        return editTrxVOs;
    }


    /**
   * Finder by Segment and transaction pending statuses.
   * @param segment
   * @param pendingStatuses
   * @return Array of EDITTrx objects
   */
  public static final EDITTrx[] findBySegment_And_PendingStatus(Segment segment, String[] pendingStatuses)
    {
        String hql = " select editTrx from EDITTrx editTrx " +
                     " join editTrx.ClientSetup clientSetup" +
                     " join clientSetup.ContractSetup contractSetup" +
                     " join contractSetup.Segment segment" +
                     " where segment = :segment" +
                     " and editTrx.PendingStatus in (:pendingStatuses)";

        Map params = new HashMap();
        params.put("segment", segment);
        params.put("pendingStatuses", pendingStatuses);

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }

    /**
     * Get the vo for the key passed in.  Not able to use mapVOTo Entity - EDITTrx does not implement CRUDEntity at this point.
     * @param editTrxPK
     * @return
     */
    public static EDITTrxVO findByPK_UsingCRUD(long editTrxPK)
    {
        EDITTrxVO editTrxVO = null;

        EDITTrxVO[] editTrxVOs = new EDITTrxDAO().findByEDITTrxPK(editTrxPK);

        if (editTrxVOs != null)
        {
            editTrxVO = editTrxVOs[0];
        }

        return editTrxVO;
    }

  /**
   * Finder.
   * @param bonusCommissionHistory
   * @return
   */
  public static EDITTrx findBy_BonusCommissionHistory(BonusCommissionHistory bonusCommissionHistory)
  {
    EDITTrx editTrx = null;
  
    String hql = " select editTrx" +
                " from BonusCommissionHistory bonusCommissionHistory" +
                " join bonusCommissionHistory.CommissionHistory commissionHistory" +
                " join commissionHistory.EDITTrxHistory editTrxHistory" +
                " join editTrxHistory.EDITTrx editTrx" +
                " where bonusCommissionHistory = :bonusCommissionHistory";
                
    Map params = new HashMap();
    
    params.put("bonusCommissionHistory", bonusCommissionHistory);
    
    List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);
    
    if (!results.isEmpty())
    {
      editTrx = (EDITTrx) results.get(0);
    }
    
    return editTrx;
  }
  
  /**
   * Finder.
   * @param commissionHistory
   * @return
   */
  public static EDITTrx findBy_CommissionHistory(CommissionHistory commissionHistory)
  {
    EDITTrx editTrx = null;

    String hql = " select editTrx" +
                " from CommissionHistory commissionHistory" +
                " join commissionHistory.EDITTrxHistory editTrxHistory" +
                " join editTrxHistory.EDITTrx editTrx" +
                " where commissionHistory = :commissionHistory";

    Map params = new HashMap();

    params.put("commissionHistory", commissionHistory);

    List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

    if (!results.isEmpty())
    {
      editTrx = (EDITTrx) results.get(0);
    }

    return editTrx;
  }

  public static String getOriginatingTrxType(long originatingTrxFK)
  {
      String originatingTrxType = null;
      EDITTrx originatingTrx = EDITTrx.findByPK(new Long(originatingTrxFK));
      if (originatingTrx != null)
      {
          originatingTrxType = originatingTrx.getTransactionTypeCT();
      }
      return originatingTrxType;
  }

    /**
     * Unterminates this EDITTrx.  Does this by simply setting the pendingStatus to Pending
     */
    public void unterminate()
    {
        this.setPendingStatus(EDITTrx.PENDINGSTATUS_PENDING);
    }


    public static EDITTrxVO[] findBySegmentPK_TrxTypes_Status(long segmentPK, String[] trxTypes, boolean includeChildren, List voExclusionList)
    {
        EDITTrxVO[] editTrxVOs = new EDITTrxDAO().findBySegmentPK_TrxTypes_Status(segmentPK, trxTypes, includeChildren, voExclusionList);

        return editTrxVOs;
    }

    String hql = "select editTrx" + 
    " from EDITTrx editTrx" + 
    " join fetch editTrx.ClientSetup clientSetup" + 
    " join fetch clientSetup.ContractSetup contractSetup" + 
    " join fetch contractSetup.GroupSetup groupSetup" + 
    " join fetch contractSetup.Segment" +
    " left join fetch contractSetup.InvestmentAllocationOverrides" + 
    " left join fetch groupSetup.ScheduledEvents" + 
    " left join fetch groupSetup.Charges" + 
    " where editTrx.EDITTrxPK = :editTrxPK";

    public static boolean checkForScheduledEventTrxType(String transactionType)
    {
        boolean scheduledEventTrxFound = false;
        for (int i = 0; i < EDITTrx.scheduledEventTypes.length; i++)
        {
            if (transactionType.equalsIgnoreCase(scheduledEventTypes[i]))
            {
                scheduledEventTrxFound = true;
                break;
            }
        }

        return scheduledEventTrxFound;
    }

    public static boolean checkForPremiumTrxType(String transactionType)
    {
        boolean paymentTrxFound = false;

        for (int i = 0; i < premiumTrxTypes.length; i++)
        {
            if (transactionType.equalsIgnoreCase(premiumTrxTypes[i]))
            {
                paymentTrxFound = true;
                break;
            }
        }

        return paymentTrxFound;
    }

    /**
     * Finds by an EffectiveDate greater than or equal to the specified date, by the transactionTypeCT and by the
     * pendingStatus
     *
     * @param effectiveDate
     * @param transactionTypeCT
     * @param pendingStatus
     * @param segmentPK
     *
     * @return
     */
    public static EDITTrx[] findBy_EffectiveDate_GTorEqual_TransactionType_PendingStatus(EDITDate effectiveDate,
                                                       String transactionTypeCT, String pendingStatus, Long segmentPK)
    {
        String hql = " select editTrx from EDITTrx editTrx" +
                    " join editTrx.ClientSetup clientSetup" +
                    " join clientSetup.ContractSetup contractSetup" +
                    " where contractSetup.SegmentFK = :segmentPK" +
                    " and editTrx.EffectiveDate >= :effectiveDate" +
                    " and editTrx.TransactionTypeCT = :transactionTypeCT" +
                    " and editTrx.PendingStatus = :pendingStatus";

        Map params = new HashMap();

        params.put("segmentPK", segmentPK);
        params.put("effectiveDate", effectiveDate);
        params.put("transactionTypeCT", transactionTypeCT);
        params.put("pendingStatus", pendingStatus);

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }

    /**
     * Finds by an EffectiveDate equal to the specified date, by the transactionTypeCT, pendingStatus, and originatingTrxFK
     *
     * @param transactionTypeCT
     * @param effectiveDate
     * @param pendingStatus
     * @param originatingTrxFK
     * @return
     */
    public static EDITTrx[] findBy_TransactionType_EffectiveDate_PendingStatus_OriginatingTrxFK(String transactionTypeCT, 
                                                       EDITDate effectiveDate, String pendingStatus, Long originatingTrxFK)
    {
        String hql = " select editTrx from EDITTrx editTrx" +
                     " where editTrx.TransactionTypeCT = :transactionTypeCT" +
                     " and editTrx.EffectiveDate = :effectiveDate" +
                     " and editTrx.PendingStatus = :pendingStatus";

        if (originatingTrxFK != null)
        {
            hql = hql + " and editTrx.OriginatingTrxFK = :originatingTrxFK";
        }


        Map params = new HashMap();

        params.put("transactionTypeCT", transactionTypeCT);
        params.put("effectiveDate", effectiveDate);
        params.put("pendingStatus", pendingStatus);

        if (originatingTrxFK != null)
        {
            params.put("originatingTrxFK", originatingTrxFK);
        }

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }

    public static EDITTrx[] findBy_TransactionType_EffectiveDate_PendingStatus_SegmentPK(String transactionTypeCT,
                                                       EDITDate effectiveDate, String pendingStatus, Long segmentPK)
    {
        String hql = " select editTrx from EDITTrx editTrx" +
                     " join editTrx.ClientSetup clientSetup" +
                     " join clientSetup.ContractSetup contractSetup" +
                     " where contractSetup.SegmentFK = :segmentPK" +
                     " and editTrx.TransactionTypeCT = :transactionTypeCT" +
                     " and editTrx.EffectiveDate = :effectiveDate" +
                     " and editTrx.PendingStatus = :pendingStatus";


        Map params = new HashMap();

        params.put("segmentPK", segmentPK);
        params.put("transactionTypeCT", transactionTypeCT);
        params.put("effectiveDate", effectiveDate);
        params.put("pendingStatus", pendingStatus);


        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }


 
  /**
   * True if this TransactionTypeCT is an element of the transaction types
   * defined as a RemovalTransaction.
   * @return true if this EDITTrx is a RemovalTransaction
   */
  public boolean isRemovalTransaction()
  {
    boolean removalTransaction = false;
  
    for (RemovalTransaction currentRemovalTransaction: getRemovalTransactionTypes())
    {
      if (currentRemovalTransaction.toString().equals(getTransactionTypeCT()))
      {
        removalTransaction = true;
        
        break;
      }
    }
    
    return removalTransaction;
  }
      
 /**
   * True if this TransactionTypeCT is an element of the transaction types
   * defined as a RemovalTransaction.
   * @return true if this EDITTrx is a RemovalTransaction
   */
  public boolean isPartialRemovalTransaction()
  {
    boolean partialRemovalTransaction = false;

    for (PartialRemovalTransaction currentPartialRemovalTransaction: getPartialRemovalTransactionTypes())
    {
      if (currentPartialRemovalTransaction.toString().equals(getTransactionTypeCT()))
      {
        partialRemovalTransaction = true;

        break;
      }
    }

    return partialRemovalTransaction;
  }
  
     /**
       * The listing of Patrial Removal Transaction Types (e.g. WI, SW, RW)
       * @return the entire listing of Removal types
       */
      public PartialRemovalTransaction[] getPartialRemovalTransactionTypes()
      {
        return EDITTrx.PartialRemovalTransaction.values();
      }


      /**
       * Finds the net overdueAdmin, overdueCoi, and overdueExpense for this EDITTrx.
       * T(his) EDITTrx must be of type PY (or MD?) or the concept doesn't even apply.
       * For each OverdueCharge (amounts1) it's related OverdueChargeSettled (amounts2)
       * are subtracted from (amounts1).
       * @return
       */
      public OverdueChargeRemaining.OverdueChargeRemainingAmount getOverdueChargeAmounts()
      {
        OverdueChargeRemaining overdueChargeRemaining = new OverdueChargeRemaining(this, OverdueChargeRemaining.SCOPE_EDITTRX_LEVEL);

        overdueChargeRemaining.calculate();

        return overdueChargeRemaining.getOverdueChargeRemainingAmount();
      }

       /**
       * Determines if this transaction can calculate OverdueCharges. OverdueCharges
       * only applies to transactions of type PY or MD.
       * @return true if this transaction is of type PY or MD
       */
      private boolean isValidTransactionTypeCTForOverdueCharges()
      {
        boolean isValid = false;

        String transactionTypeCT = getTransactionTypeCT();

        if (transactionTypeCT.equals(EDITTrx.TRANSACTIONTYPECT_PREMIUM) || transactionTypeCT.equals(EDITTrx.TRANSACTIONTYPECT_MODALDEDUCTION))
        {
          isValid = true;
        }

        return isValid;
      }


      public void find()
      {
        String sql = " select segment from EDITTrx editTrx" +
                      " join editTrx.ClientSetup clientSetup" +
                      " join clientSetup.ContractClient contractClient" +
                      " join fetch contractClient.Segment segment" +
                      " join fetch segment.Investments investment" +
                      " join fetch investment.InvestmentHistories investmentHistory" +
                      " join fetch investmentHistory.EDITTrxHistory editTrxHistory" +
                      " join fetch editTrxHistory.bucketHistories" +
                      " join TransactionPriority transactionPriority" +

                      " where segment.SegmentPK = :segmentPK" +
                      " and editTrx.Status in ('N','A')" +

                      " and ((editTrx.EffectiveDate > :fromDate" +
                      " and editTrx.EffectiveDate < :toDate)" +
                      " or (editTrx.EffectiveDate = :toDate" +

                      " and (transactionPriority.TransactionTypeCT = editTrx.TransactionTypeCT" +
                      " transactionPriority.Priority <= (select transactionPriority.Priority from TransactionPriority transactionPriority" +
                      " where transactionPriority.TransactionTypeCT = :transactionTypeCT)))" +

                      " or editTrx.EffectiveDate = :fromDate" +
                      " and (transactionPriority.TransactionTypeCT = editTrx.TransactionTypeCT" +
                      " and transactionPriority.Priority > (select transactionPriority.Priority from TransactionPriority transactionPriority" +
                      " where transactionPriority.TransactionTypeCT = :transactionTypeCT)))" +
                      " and editTrx.PendingStatus = 'H'";

  }

      /**
       * Adder.
       * @param overdueCharge
       */
      public void add(OverdueCharge overdueCharge)
      {
        getOverdueCharges().add(overdueCharge);

        overdueCharge.setEDITTrx(this);
      }

      /**
       * Adder.
       * @param overdueChargeSettled
       */
      public void add(OverdueChargeSettled overdueChargeSettled)
      {
        getOverdueChargesSettled().add(overdueChargeSettled);

        overdueChargeSettled.setEDITTrx(this);
      }

      /**
       * Hibernate Setter.
       * @param overdueChargesSettled
       */
      public void setOverdueChargesSettled(Set<OverdueChargeSettled> overdueChargesSettled)
      {
        this.overdueChargesSettled = overdueChargesSettled;
      }

      /**
       * Hibernate Getter.
       * @return
       */
      public Set<OverdueChargeSettled> getOverdueChargesSettled()
      {
        return overdueChargesSettled;
      }

      /**
       * The listing of Removal Transaction Types (e.g. MD, FS, CY)
       * @return the entire listing of Removal types
       */
      public RemovalTransaction[] getRemovalTransactionTypes()
      {
        return EDITTrx.RemovalTransaction.values();
      }


  /**
   * Finder. This is the (Separate Hibernate Session) version of EDITTrx.findAllTamraRetest_UsingCRUD()
   * @param segmentPK
   * @param effectiveDate
   * @return
   */
  public static EDITTrx[] findSeparateAllTamraRetest(Long segmentPK, EDITDate effectiveDate)
  {
    String hql = " select editTrx" +
                " from EDITTrx editTrx" +
                " join fetch editTrx.ClientSetup clientSetup" +
                " join fetch clientSetup.ContractSetup contractSetup" +
                " join fetch contractSetup.GroupSetup groupSetup" +
                " join fetch contractSetup.Segment segment" +
                " join fetch segment.Lifes" +
                " join fetch editTrx.EDITTrxHistories editTrxHistory" +
                " join fetch editTrxHistory.FinancialHistories" +

                " where contractSetup.SegmentFK = :segmentPK" +
                " and editTrx.PendingStatus = 'H'" +
                " and editTrx.EffectiveDate <= :effectiveDate" +
                " and editTrx.Status in ('N','A')" +
                " and (editTrx.TransactionTypeCT in ('IS','WI')" +
                " or (editTrx.TransactionTypeCT = 'PY' and groupSetup.PremiumTypeCT != 'Exchange')" +
                " or (editTrx.TransactionTypeCT = 'CC' and contractSetup.ComplexChangeTypeCT = 'FaceIncrease'))" +
                " order by editTrx.EffectiveDate"; 

    EDITMap params = new EDITMap("segmentPK", segmentPK)
                  .put("effectiveDate", effectiveDate);
                  
    Session session = null;
    
    List<EDITTrx> results = null;
    
    try
    {
      results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);
    }
    finally
    {
      if (session != null) session.close();
    }

    return results.toArray(new EDITTrx[results.size()]);
  }
  
  /**
   * Finder. This is the (Hibernate Separate Session) version of EDITTrxDAO.findComplexChangeTrx().
   * @param segmentPK
   * @param effectiveDate
   * @return
   */
  public static EDITTrx[] findSeparateComplexChangeTrx(Long segmentPK, EDITDate effectiveDate)
  {
    String hql = " select editTrx1" +
                " from EDITTrx editTrx1" +
                " join editTrx1.ClientSetup clientSetup1" +
                " join clientSetup1.ContractSetup contractSetup2" +
                " join contractSetup1.GroupSetup groupSetup" +
                " join editTrx1.EDITTrxHistories editTrxHistory1" +
                " join fetch editTrxHistory1.FinancialHistories" +
                " where editTrx1.PendingStatus = 'H'" +
                " and (editTrx1.TransactionTypeCT = 'CC'" +
                " and contractSetup1.ComplexChangeTypeCT = 'FaceIncrease')" +
                " and contractSetup1.SegmentFK = :segmentPK" +
                " and editTrx1.EffectiveDate = " +

                " (select max(editTrx2.EffectiveDate) " +
                " from EDITTrx editTrx2" +
                " join editTrx2.ClientSetup clientSetup2" +
                " join clientSetup2.ContractSetup contractSetup2" +
                " where editTrx2.PendingStatus = 'H'" +
                " and (editTrx2.TransactionTypeCT = 'CC'" +
                " and contractSetup2.ComplexChangeTypeCT = 'FaceIncrease')" +
                " and editTrx2.EffectiveDate <= :effectiveDate" +
                " and contractSetup2.SegmentFK = :segmentPK)";

    EDITMap params = new EDITMap("segmentPK", segmentPK).put("effectiveDate", effectiveDate);
    
    Session session = null;
    
    List<EDITTrx> results = null;
    
    try
    {
      session = SessionHelper.getSeparateSession(EDITTrx.DATABASE);

      results = SessionHelper.executeHQL(session, hql, params, 0);
    }
    finally
    {
      if (session != null) session.close();
    }

    return results.toArray(new EDITTrx[results.size()]);
  }
  
  /**
   * Find all ComplexChange transactions and their Premiums for a given segmentPK and effectiveDate.
   * This is the Hibernate version of EDITTrxDAO.findPartialTamraRetest().
   * @param segmentPK
   * @param effectiveDate
   * @return
   */
  public static EDITTrx[] findPartialTamraRetest(Long segmentPK, EDITDate ccEffectiveDate, EDITDate effectiveDate)
  {
    String hql = " select editTrx" +
                " from EDITTrx editTrx" +
                " join editTrx.ClientSetup clientSetup" +
                " join clientSetup.ContractSetup contractSetup" +
                " join contractSetup.GroupSetup groupSetup" +
                " join editTrx.EDITTrxHistories editTrxHistory" +
                " join fetch editTrxHistory.FinancialHistories" +

                " where editTrx.PendingStatus = 'H'" +
                " and ((editTrx.TransactionTypeCT = 'PY'" +
                " and groupSetup.PremiumTypeCT <> 'Exchange') " +
                " or editTrx.TransactionTypeCT = 'WI')" +
                " and (editTrx.EffectiveDate >= :ccEffectiveDate" +
                " and editTrx.EffectiveDate <= :effectiveDate)" +
                " and contractSetup.SegmentFK = :segmentFK" +
                " order by editTrx.EffectiveDate";

    EDITMap params = new EDITMap("segmentPK", segmentPK)
                    .put("ccEffectiveDate", ccEffectiveDate)
                    .put("effectiveDate", effectiveDate);

    List<EDITTrx> results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

    return results.toArray(new EDITTrx[results.size()]);
  }

  /**
   * Uses a separate Hibernate Session to find the following Structure:
   * 
   * EDITTrx
   * EDITTrx.ClientSetup
   * EDITTrx.ClientSetup.ContractSetup
   * EDITTrx.ClientSetup.ContractSetup.InvestmentAllocationOverride (if exists).
   * @param editTrxPK
   * @return
   */
  public static EDITTrx findSeparateBy_EDITTrxPK_V1(Long editTrxPK)
  {
    EDITTrx editTrx = null;
  
    String hql = " select editTrx" +
                " from EDITTrx editTrx" +
                " join fetch editTrx.ClientSetup clientSetup" +
                " join fetch clientSetup.ContractSetup contractSetup" +
                " left join fetch contractSetup.InvestmentAllocationOverrides" +
                " where editTrx.EDITTrxPK = :editTrxPK";
                
    EDITMap params = new EDITMap("editTrxPK", editTrxPK);

    Session session = null;
    
    try
    {
      session = SessionHelper.getSeparateSession(EDITTrx.DATABASE);
      
      List<EDITTrx> results = SessionHelper.executeHQL(session, hql, params, 0);
      
      if (!results.isEmpty())
      {
        editTrx = results.get(0);
      }
    }
    finally
    {
      if (session != null) session.close();
    }
    
    return editTrx;
  }

  /**
   * Get the editTrx for the contract, with the greatest sequence number
   * @param segment
   * @param effectiveDate
   * @param transactionTypeCT
   * @return
   */
  public static EDITTrx findMaxSequenceByEffectiveDate_TrxType(Segment segment, EDITDate effectiveDate, String transactionTypeCT, boolean updateCache)
  {
      String hql = " select editTrx from EDITTrx editTrx " +
                   " join editTrx.ClientSetup clientSetup " +
                   " join clientSetup.ContractSetup contractSetup" +
                   " join contractSetup.Segment segment " +
//                   " where editTrx.PendingStatus in ('P', 'M', 'H')" +
                   " where segment = :segment and editTrx.TransactionTypeCT = :trxCode" +
                   " and editTrx.EffectiveDate = :effectiveDate" +
                   " and editTrx.SequenceNumber = (select max(editTrx2.SequenceNumber) from EDITTrx editTrx2 " +
                   " join editTrx2.ClientSetup clientSetup2" +
                   " join clientSetup2.ContractSetup contractSetup2" +
                   " join contractSetup2.Segment segment2" +
//                   " where editTrx2.PendingStatus in ('P', 'M', 'H')" +
                   " where segment2 = :segment and editTrx2.TransactionTypeCT = :trxCode" +
                   " and editTrx2.EffectiveDate = :effectiveDate)";
    
      Map params = new HashMap();

      params.put("segment", segment);
      params.put("effectiveDate", effectiveDate.getFormattedDate());
      params.put("trxCode", transactionTypeCT);

      List results =  SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

      if (results.isEmpty())
      {
          return null;
      }

      return (EDITTrx) results.get(0);
  }

    /**
     * Originally in EDITTrxDAO.findBySegmentPK_AND_EffectiveDateLTE_AND_PendingStatus
     * @param segmentPK
     * @param effectiveDate
     * @param pendingStatuses
     * @return
     */
    public static Long[] findBy_Segment_EffectiveDateLTE_PendingStatus(Long segmentPK, EDITDate effectiveDate, String[] pendingStatuses)
    {
        String hql = "select editTrx.EDITTrxPK from EDITTrx editTrx " +
                     " join  editTrx.ClientSetup clientSetup" +
                     " join  clientSetup.ContractSetup contractSetup" +
                     " where contractSetup.SegmentFK = :segmentFK" +
                     " and editTrx.PendingStatus in (:pendingStatuses)" +
                     " and editTrx.Status in ('N', 'A')" +
                     " and editTrx.EffectiveDate <= :effectiveDate" +
                     " order by editTrx.EDITTrxPK asc";

        Map params = new HashMap();

        params.put("segmentFK", segmentPK);
        params.put("pendingStatuses", pendingStatuses);
        params.put("effectiveDate", effectiveDate);

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (Long[]) results.toArray(new Long[results.size()]);
    }

    /**
     * Originally in EDITTrxDAO.findBySegmentPK_AND_EffectiveDateLTE_AND_PendingStatus
     * @param segmentPK
     * @param effectiveDate
     * @param pendingStatuses
     * @return
     */
    public static EDITTrx findBy_Segment_EffectiveDateLTE_PendingStatus_TransactionTypeCT(
    		Long segmentPK, EDITDate effectiveDate)
//    		Long segmentPK, EDITDate effectiveDate, String[] pendingStatuses, String[] transactionTypeCTs)
    {
        String hql = "select editTrx from EDITTrx editTrx " +
                     " join  editTrx.ClientSetup clientSetup" +
                     " join  clientSetup.ContractSetup contractSetup" +
                     " where contractSetup.SegmentFK = :segmentFK" +
                     " and editTrx.PendingStatus = 'H' " +
                     " and editTrx.TransactionTypeCT in ('DE', 'LA', 'MA', 'FS', 'CPO')" +
                     " and editTrx.Status in ('N', 'A')" +
                     " and editTrx.EffectiveDate <= :effectiveDate" +
                     " order by editTrx.EDITTrxPK desc";

        Map params = new HashMap();

        params.put("segmentFK", segmentPK);
    //    params.put("pendingStatuses", pendingStatuses);
      //  params.put("transactionTypeCT", transactionTypeCTs);
        params.put("effectiveDate", effectiveDate);

        EDITTrx editTrx = null;

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        if (!results.isEmpty())
        {
            editTrx = (EDITTrx) results.get(0);
        }

        return editTrx;
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        FinancialActivity financialActivity = new FinancialActivity();
        financialActivity.setEffectiveDate(this.getEffectiveDate());
        financialActivity.setTaxYear(this.getTaxYear());
        financialActivity.setTrxAmount(this.getTrxAmount());
        financialActivity.setDueDate(this.getDueDate());
        financialActivity.setTransactionType(this.getTransactionTypeCT());
        financialActivity.setNoCorrespondenceInd(this.getNoCorrespondenceInd());
        financialActivity.setNoAccountingInd(this.getNoAccountingInd());
        financialActivity.setNoCommissionInd(this.getNoCommissionInd());
        financialActivity.setMaintDateTime(this.getMaintDateTime());
        financialActivity.setOperator(this.getOperator());

        EDITTrxHistory[] editTrxHistory = EDITTrxHistory.findBy_EDITTrxPK(this.getEDITTrxPK());
        if (editTrxHistory.length > 0)
        {
            financialActivity.setProcessDate(editTrxHistory[0].getProcessDateTime());

            FinancialHistory[] financialHistory = FinancialHistory.findBy_EDITTrxHistoryPK(editTrxHistory[0].getEDITTrxHistoryPK());
            if (financialHistory.length > 0)
            {
                financialActivity.setGrossAmount(financialHistory[0].getGrossAmount());
                financialActivity.setNetAmount(financialHistory[0].getNetAmount());
                financialActivity.setCheckAmount(financialHistory[0].getCheckAmount());
                financialActivity.setFreeAmount(financialHistory[0].getFreeAmount());
                financialActivity.setTaxableBenefit(financialHistory[0].getTaxableBenefit());
                financialActivity.setDisbursementSource(financialHistory[0].getDisbursementSourceCT());
                financialActivity.setCommissionableAmount(financialHistory[0].getCommissionableAmount());
                financialActivity.setMaxCommissionAmount(financialHistory[0].getMaxCommissionAmount());
                financialActivity.setCostBasis(financialHistory[0].getCostBasis());
                financialActivity.setAccumulatedValue(financialHistory[0].getAccumulatedValue());
                financialActivity.setSurrenderValue(financialHistory[0].getSurrenderValue());
            }

            ChargeHistory[] chargeHistory = ChargeHistory.findBy_EDITTrxHistoryPK(editTrxHistory[0].getEDITTrxHistoryPK());
            for (int i = 0; i < chargeHistory.length; i++)
            {
                AdjustmentHistory adjustmentHistory = new AdjustmentHistory();
                adjustmentHistory.setFinancialActivity(financialActivity);
                adjustmentHistory.setChargeAmount(chargeHistory[i].getChargeAmount());
                adjustmentHistory.setChargeType(chargeHistory[i].getChargeTypeCT());
                financialActivity.addAdjustmentHistory(adjustmentHistory);
            }

            BucketHistory[] bucketHistories = BucketHistory.findBy_EDITTrxHistoryPK(editTrxHistory[0].getEDITTrxHistoryPK());
            for (int i = 0; i < bucketHistories.length; i++)
            {
                contract.Bucket bucket = bucketHistories[i].getBucket();

                staging.Bucket stagingBucket = new staging.Bucket();
                stagingBucket.setFinancialActivity(financialActivity);
                stagingBucket.setDepositDate(bucket.getDepositDate());
                stagingBucket.setDepositAmount(bucket.getDepositAmount());
                stagingBucket.setBucketInterestRate(bucket.getBucketInterestRate());
                stagingBucket.setUnearnedInterest(bucket.getUnearnedInterest());
                stagingBucket.setLoanPrincipalRemaining(bucket.getLoanPrincipalRemaining());
                stagingBucket.setBucketSource(bucket.getBucketSourceCT());
                stagingBucket.setLoanInterestRate(bucket.getLoanInterestRate());
                stagingBucket.setAccruedLoanInterest(bucket.getAccruedLoanInterest());
                stagingBucket.setLoanPrincipalDollars(bucketHistories[i].getLoanPrincipalDollars());
                stagingBucket.setLoanInterestDollars(bucketHistories[i].getLoanInterestDollars());
                stagingBucket.setUnearnedLoanInterest(bucket.getUnearnedLoanInterest());
                stagingBucket.setInterestPaidThroughDate(bucket.getInterestPaidThroughDate());
                stagingBucket.setBilledLoanInterest(bucket.getBilledLoanInterest());
                financialActivity.addBucket(stagingBucket);
            }
        }

        ClientSetup clientSetup = ClientSetup.findByPK(this.getClientSetupFK());
        ContractSetup contractSetup = clientSetup.getContractSetup();
        financialActivity.setComplexChangeType(contractSetup.getComplexChangeTypeCT());

        financialActivity.setSegmentBase(stagingContext.getCurrentSegmentBase());
        
        if (!this.getTransactionTypeCT().equalsIgnoreCase("CK") && !this.getTransactionTypeCT().equalsIgnoreCase("CA"))
        {
            stagingContext.getCurrentSegmentBase().addFinancialActivity(financialActivity);
        }
        stagingContext.setCurrentFinancialActivity(financialActivity);

        SessionHelper.saveOrUpdate(financialActivity, database);

        return stagingContext;
    }

    //ECK
    public void createBCTrxForNewRiders(Segment segment, Segment newRiderSegment) throws EDITEventException
    {
        EDITDate effectiveDate = newRiderSegment.getEffectiveDate();
        String ccType = "RiderAdd";

         String trxType = EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE;
         
         if (segment.getSegmentNameCT().equalsIgnoreCase(Segment.SEGMENTNAMECT_UL) &&
    		 	(ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_ADD) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_TERM) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_RIDER_CHANGE) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_CLAIMSTOP) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_CLASSCHANGE) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_DBOCHANGE) ||
				ccType.equalsIgnoreCase(ContractSetup.COMPLEXCHANGETYPECT_FACEDECREASE))) 
         {
             trxType = EDITTrx.TRANSACTIONTYPECT_COMPLEXCHANGE;
             // What is this all about?  Removed on 20210604.
             if (newRiderSegment.getOptionCodeCT().equals("ULIncrease")) {
            	 ccType = ContractSetup.COMPLEXCHANGETYPECT_FACEINCREASE;
             }
         }

        EDITTrx bcTrx = EDITTrx.findByTrxType_EffDate_CCType(segment.getSegmentPK(), effectiveDate, trxType, ccType);

        if (bcTrx == null)
        {
            createBillingChangeTrxGroupSetup(segment, segment.getOperator(), ccType, effectiveDate, newRiderSegment.getRiderNumber());
        }
    }

   /**
    * Check for the existence of the same day BC trx
    * @param segment
    * @param effectiveDate
    * @param complexChangeType
    * @return
    */
    public static EDITTrx findByTrxType_EffDate_CCType(Long segmentPK, EDITDate effectiveDate, String trxType, String complexChangeType)
    {
        EDITTrx editTrx = null;
        String hql = "select editTrx from EDITTrx editTrx " +
                     " join fetch editTrx.ClientSetup clientSetup" +
                     " join fetch clientSetup.ContractSetup contractSetup" +
                     " where contractSetup.SegmentFK = :segmentPK" +
                     " and contractSetup.ComplexChangeTypeCT = :complexChangeType" +
                     " and editTrx.TransactionTypeCT = :trxType" +
                     " and editTrx.PendingStatus = :pendingStatus" +
                     " and editTrx.Status in ('N', 'A')" +
                     " and editTrx.EffectiveDate = :effectiveDate";

        Map params = new HashMap();

        params.put("segmentPK", segmentPK);
        params.put("complexChangeType", complexChangeType);
        params.put("trxType", trxType);
        params.put("pendingStatus", "P");
        params.put("effectiveDate", effectiveDate);

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        if (!results.isEmpty())
        {
            editTrx = (EDITTrx) results.get(0);
        }

        return editTrx;
    }

    /**
   * Originally in EDITTrxDAO.findTerminatedTrxForContract.  Generalized it so have to pass in pendingStatus
   * @param segmentPK
   * @return
   */
  public static EDITTrx[] findBy_SegmentPK_PendingStatus_TransactionType(Long segmentPK, String pendingStatus)
    {
        String hql = " select editTrx from EDITTrx editTrx" +
                    " join editTrx.ClientSetup clientSetup" +
                    " join clientSetup.ContractSetup contractSetup" +
                    " where contractSetup.SegmentFK = :segmentPK" +
                    " and editTrx.PendingStatus = :pendingStatus" +
                    " and editTrx.TransactionTypeCT in ('SB', 'IS', 'BC', 'BCDA', 'NBR', 'PY', 'ADC', 'CC')";

        Map params = new HashMap();

        params.put("segmentPK", segmentPK);
        params.put("pendingStatus", pendingStatus);

        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }

   public static EDITTrx[] findBy_TransactionType_SegmentPK(String transactionTypeCT, Long segmentPK)
    {
        String hql = " select editTrx from EDITTrx editTrx" +
                     " join editTrx.ClientSetup clientSetup" +
                     " join clientSetup.ContractSetup contractSetup" +
                     " where contractSetup.SegmentFK = :segmentPK" +
                     " and editTrx.TransactionTypeCT = :transactionTypeCT" +
                     " and not editTrx.Status = 'R'";

        Map params = new HashMap();

        params.put("segmentPK", segmentPK);
        params.put("transactionTypeCT", transactionTypeCT);

        Session session = null;

        EDITTrx[] editTrxs = null;
        try
        {
            session = SessionHelper.getSeparateSession(EDITTrx.DATABASE);

            List<EDITTrx> results = SessionHelper.executeHQL(session, hql, params, 0);
            if (!results.isEmpty())
            {
                editTrxs = (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
            }
        }
    finally
    {
      if (session != null) session.close();
    }

        return editTrxs;
    }

   public static EDITTrx[] findBy_TransactionType_SegmentPK_PendingStatus(String transactionTypeCT, Long segmentPK, String pendingStatus)
    {
        String hql = " select editTrx from EDITTrx editTrx" +
                     " join editTrx.ClientSetup clientSetup" +
                     " join clientSetup.ContractSetup contractSetup" +
                     " where contractSetup.SegmentFK = :segmentPK" +
                     " and editTrx.PendingStatus = :pendingStatus" +
                     " and editTrx.TransactionTypeCT = :transactionTypeCT";

        Map params = new HashMap();

        params.put("segmentPK", segmentPK);
        params.put("pendingStatus", pendingStatus);
        params.put("transactionTypeCT", transactionTypeCT);

        EDITTrx[] editTrxs = null;
        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

            if (!results.isEmpty())
            {
                editTrxs = (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
            }

        return editTrxs;
    }


   public static EDITTrx findBy_SuspensePK(Long suspensePK)
    {
        String hql = " select editTrx from EDITTrx editTrx" +
                     " join editTrx.EDITTrxHistories editTrxHistories" +
                     " join editTrxHistories.InSuspenses inSuspenses" +
                     " where inSuspenses.SuspenseFK = :suspensePK";

        Map params = new HashMap();

        params.put("suspensePK", suspensePK);

        EDITTrx editTrx = null;
        List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

            if (!results.isEmpty())
            {
                editTrx = (EDITTrx) results.get(0);
            }

        return editTrx;
    }
   
   public static EDITTrx findBy_FinancialHistoryPK(Long financialHistoryPK)
   {
       String hql = " select editTrx from EDITTrx editTrx" +
                    " join editTrx.EDITTrxHistories editTrxHistories" +
                    " join editTrxHistories.FinancialHistories financialHistories" +
                    " where financialHistories.FinancialHistoryPK = :financialHistoryPK";

       Map params = new HashMap();

       params.put("financialHistoryPK", financialHistoryPK);

       EDITTrx editTrx = null;
       List results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

           if (!results.isEmpty())
           {
               editTrx = (EDITTrx) results.get(0);
           }

       return editTrx;
   }

	public static Long[] findByDateRangeOfDueDate(EDITDate fromDate, EDITDate toDate)
    {
    String hql = " select editTrx.EDITTrxPK" +
                " from EDITTrx editTrx" +
                " where editTrx.PendingStatus = 'P'" +
                " and editTrx.TransactionTypeCT = 'PO'" +
                " and editTrx.DueDate >= :fromDate" +
                " and editTrx.DueDate <= :toDate";

    EDITMap params = new EDITMap("fromDate", fromDate)
                    .put("toDate", toDate);

    List<Long> results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

    return results.toArray(new Long[results.size()]);

    }

    public static Long[] findByDateRangeOfEffectiveDate(EDITDate fromDate, EDITDate toDate)
    {

        String hql = " select editTrx.EDITTrxPK" +
                    " from EDITTrx editTrx" +
                    " where editTrx.PendingStatus = 'P'" +
                    " and editTrx.TransactionTypeCT = 'PO'" +
                    " and editTrx.EffectiveDate >= :fromDate" +
                    " and editTrx.EffectiveDate <= :toDate";

        EDITMap params = new EDITMap("fromDate", fromDate)
                        .put("toDate", toDate);

        List<Long> results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        return results.toArray(new Long[results.size()]);
	}

    public static EDITTrx[] findByDate_TrxType_PDCreatedInd_PendStatus(EDITDate cycleDate)
    {
        List<EDITTrx> editTrxs = new ArrayList<EDITTrx>();

        String hql = " select distinct editTrx from EDITTrx editTrx" +
                     " where editTrx.EffectiveDate > :cycleDate" +
                     " and editTrx.PendingStatus = 'P'" +
                     " and editTrx.PremiumDueCreatedIndicator = 'N'" +
                     " and (editTrx.TransactionTypeCT = 'FI'" +
                     " or editTrx.TransactionTypeCT = 'MA'" +
                     " or editTrx.TransactionTypeCT = 'BC'" +
                     " or editTrx.TransactionTypeCT = 'BCDA')";


        Map params = new HashMap();

        params.put("cycleDate", cycleDate);

        List<EDITTrx> results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }
    
    public static EDITTrx[] findByActive_EffectiveDate_TrxType_SortedDesc(Long segmentFK, EDITDate effectiveDate, String transactionTypeCT)
    {
        List<EDITTrx> editTrxs = new ArrayList<EDITTrx>();

        String hql = " select distinct editTrx from EDITTrx editTrx" +
	        		 " join fetch editTrx.ClientSetup clientSetup" +
	                 " join fetch clientSetup.ContractSetup contractSetup" +
	                 " where contractSetup.SegmentFK = :segmentFK" +
                     " and editTrx.EffectiveDate < :effectiveDate" +
                     " and editTrx.PendingStatus = 'H'" +
                     " and editTrx.Status in ('N', 'A') " +
                     " and editTrx.TransactionTypeCT = :transactionTypeCT" +
                     " order by editTrx.EffectiveDate desc, editTrx.SequenceNumber desc";


        Map params = new HashMap();

        params.put("segmentFK", segmentFK);
        params.put("effectiveDate", effectiveDate);
        params.put("transactionTypeCT", transactionTypeCT);

        List<EDITTrx> results = SessionHelper.executeHQL(hql, params, EDITTrx.DATABASE);

        return (EDITTrx[]) results.toArray(new EDITTrx[results.size()]);
    }
}
