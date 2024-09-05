package engine;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import org.dom4j.Element;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sprasad
 * Date: Mar 7, 2008
 * Time: 11:35:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class ControlBalanceDetail extends HibernateEntity
{
    private Long controlBalanceDetailPK;
    private Long controlBalanceFK;
    private EDITBigDecimal netAssets;
    private EDITBigDecimal policyOwnerValue;
    private EDITBigDecimal accruedGainLoss;
    private EDITBigDecimal totalAccruedNetPremiums;
    private EDITBigDecimal totalAccruedAdminFees;
    private EDITBigDecimal totalAccruedCOI;
    private EDITBigDecimal totalAccruedReallocations;
    private EDITBigDecimal totalAccruedRRD;
    private EDITBigDecimal totalAccruedSurrenders;
    private EDITBigDecimal totalAccruedContribToMortRsv;
    private EDITBigDecimal totalAccruedMAndE;
    private EDITBigDecimal totalAccruedAdvisoryFees;
    private EDITBigDecimal totalAccruedMgtFees;
    private EDITBigDecimal totalAccruedRVPFees;
    private EDITBigDecimal totalAccruedAdvanceTransfers;
    private EDITBigDecimal mAndEPayableReceivables;
    private EDITBigDecimal advisoryFeePayablesReceivables;
    private EDITBigDecimal mgtFeePayablesReceivables;
    private EDITBigDecimal rvpPayablesReceivables;
    private EDITBigDecimal unitBalance;
    private String accountingPeriod;
    private EDITDate effectiveDate;
    private EDITDate valuationDate;
    private EDITBigDecimal shareBalance;
    private EDITBigDecimal totalCashNetPremium;
    private EDITBigDecimal cashGainLoss;
    private EDITBigDecimal totalCashAdminFees;
    private EDITBigDecimal totalCashCoi;
    private EDITBigDecimal totalCashReallocations;
    private EDITBigDecimal totalCashRRD;
    private EDITBigDecimal totalCashSurrenders;
    private EDITBigDecimal totalCashContribToMortRsv;
    private EDITBigDecimal totalCashMAndE;
    private EDITBigDecimal totalCashAdvisoryFees;
    private EDITBigDecimal totalCashMgmtFees;
    private EDITBigDecimal totalCashSVAFees;
    private EDITBigDecimal totalCashAdvanceTransfers;
    private EDITBigDecimal netCash;
    private EDITBigDecimal sharesPurchased;
    private EDITBigDecimal policyActivity;
    private EDITBigDecimal unitsPurchased;

    private ControlBalance controlBalance;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.ENGINE;

    public ControlBalanceDetail()
    {

    }

    /**
     * Getter.
     * @return
     */
    public Long getControlBalanceDetailPK()
    {
        return controlBalanceDetailPK;
    }

    /**
     * Setter.
     * @param controlBalanceDetailPK
     */
    public void setControlBalanceDetailPK(Long controlBalanceDetailPK)
    {
        this.controlBalanceDetailPK = controlBalanceDetailPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getControlBalanceFK()
    {
        return controlBalanceFK;
    }

    /**
     * Setter.
     * @param controlBalanceFK
     */
    public void setControlBalanceFK(Long controlBalanceFK)
    {
        this.controlBalanceFK = controlBalanceFK;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getNetAssets()
    {
        return netAssets;
    }

    /**
     * Setter.
     * @param netAssets
     */
    public void setNetAssets(EDITBigDecimal netAssets)
    {
        this.netAssets = netAssets;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPolicyOwnerValue()
    {
        return policyOwnerValue;
    }

    /**
     * Setter.
     * @param policyOwnerValue
     */
    public void setPolicyOwnerValue(EDITBigDecimal policyOwnerValue)
    {
        this.policyOwnerValue = policyOwnerValue;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAccruedGainLoss()
    {
        return accruedGainLoss;
    }

    /**
     * Setter.
     * @param accruedGainLoss
     */
    public void setAccruedGainLoss(EDITBigDecimal accruedGainLoss)
    {
        this.accruedGainLoss = accruedGainLoss;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalAccruedNetPremiums()
    {
        return totalAccruedNetPremiums;
    }

    /**
     * Setter.
     * @param totalAccruedNetPremiums
     */
    public void setTotalAccruedNetPremiums(EDITBigDecimal totalAccruedNetPremiums)
    {
        this.totalAccruedNetPremiums = totalAccruedNetPremiums;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalAccruedAdminFees()
    {
        return totalAccruedAdminFees;
    }

    /**
     * Setter.
     * @param totalAccruedAdminFees
     */
    public void setTotalAccruedAdminFees(EDITBigDecimal totalAccruedAdminFees)
    {
        this.totalAccruedAdminFees = totalAccruedAdminFees;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalAccruedCOI()
    {
        return totalAccruedCOI;
    }

    /**
     * Setter.
     * @param totalAccruedCOI
     */
    public void setTotalAccruedCOI(EDITBigDecimal totalAccruedCOI)
    {
        this.totalAccruedCOI = totalAccruedCOI;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalAccruedReallocations()
    {
        return totalAccruedReallocations;
    }

    /**
     * Setter.
     * @param totalAccruedReallocations
     */
    public void setTotalAccruedReallocations(EDITBigDecimal totalAccruedReallocations)
    {
        this.totalAccruedReallocations = totalAccruedReallocations;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalAccruedRRD()
    {
        return totalAccruedRRD;
    }

    /**
     * Setter.
     * @param totalAccruedRRD
     */
    public void setTotalAccruedRRD(EDITBigDecimal totalAccruedRRD)
    {
        this.totalAccruedRRD = totalAccruedRRD;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalAccruedSurrenders()
    {
        return totalAccruedSurrenders;
    }

    /**
     * Setter.
     * @param totalAccruedSurrenders
     */
    public void setTotalAccruedSurrenders(EDITBigDecimal totalAccruedSurrenders)
    {
        this.totalAccruedSurrenders = totalAccruedSurrenders;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalAccruedContribToMortRsv()
    {
        return totalAccruedContribToMortRsv;
    }

    /**
     * Setter.
     * @param totalAccruedContribToMortRsv
     */
    public void setTotalAccruedContribToMortRsv(EDITBigDecimal totalAccruedContribToMortRsv)
    {
        this.totalAccruedContribToMortRsv = totalAccruedContribToMortRsv;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalAccruedMAndE()
    {
        return totalAccruedMAndE;
    }

    /**
     * Setter.
     * @param totalAccruedMAndE
     */
    public void setTotalAccruedMAndE(EDITBigDecimal totalAccruedMAndE)
    {
        this.totalAccruedMAndE = totalAccruedMAndE;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalAccruedAdvisoryFees()
    {
        return totalAccruedAdvisoryFees;
    }

    /**
     * Setter.
     * @param totalAccruedAdvisoryFees
     */
    public void setTotalAccruedAdvisoryFees(EDITBigDecimal totalAccruedAdvisoryFees)
    {
        this.totalAccruedAdvisoryFees = totalAccruedAdvisoryFees;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalAccruedMgtFees()
    {
        return totalAccruedMgtFees;
    }

    /**
     * Setter.
     * @param totalAccruedMgtFees
     */
    public void setTotalAccruedMgtFees(EDITBigDecimal totalAccruedMgtFees)
    {
        this.totalAccruedMgtFees = totalAccruedMgtFees;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalAccruedRVPFees()
    {
        return totalAccruedRVPFees;
    }

    /**
     * Setter.
     * @param totalAccruedRVPFees
     */
    public void setTotalAccruedRVPFees(EDITBigDecimal totalAccruedRVPFees)
    {
        this.totalAccruedRVPFees = totalAccruedRVPFees;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalAccruedAdvanceTransfers()
    {
        return totalAccruedAdvanceTransfers;
    }

    /**
     * Setter.
     * @param totalAccruedAdvanceTransfers
     */
    public void setTotalAccruedAdvanceTransfers(EDITBigDecimal totalAccruedAdvanceTransfers)
    {
        this.totalAccruedAdvanceTransfers = totalAccruedAdvanceTransfers;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getMAndEPayableReceivables()
    {
        return mAndEPayableReceivables;
    }

    /**
     * Setter.
     * @param mAndEPayableReceivables
     */
    public void setMAndEPayableReceivables(EDITBigDecimal mAndEPayableReceivables)
    {
        this.mAndEPayableReceivables = mAndEPayableReceivables;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAdvisoryFeePayablesReceivables()
    {
        return advisoryFeePayablesReceivables;
    }

    /**
     * Setter.
     * @param advisoryFeePayablesReceivables
     */
    public void setAdvisoryFeePayablesReceivables(EDITBigDecimal advisoryFeePayablesReceivables)
    {
        this.advisoryFeePayablesReceivables = advisoryFeePayablesReceivables;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getMgtFeePayablesReceivables()
    {
        return mgtFeePayablesReceivables;
    }

    /**
     * Setter.
     * @param mgtFeePayablesReceivables
     */
    public void setMgtFeePayablesReceivables(EDITBigDecimal mgtFeePayablesReceivables)
    {
        this.mgtFeePayablesReceivables = mgtFeePayablesReceivables;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getRVPPayablesReceivables()
    {
        return rvpPayablesReceivables;
    }

    /**
     * Setter.
     * @param rvpPayablesReceivables
     */
    public void setRVPPayablesReceivables(EDITBigDecimal rvpPayablesReceivables)
    {
        this.rvpPayablesReceivables = rvpPayablesReceivables;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getUnitBalance()
    {
        return unitBalance;
    }

    /**
     * Setter.
     * @param unitBalance
     */
    public void setUnitBalance(EDITBigDecimal unitBalance)
    {
        this.unitBalance = unitBalance;
    }

    /**
     * Getter.
     * @return
     */
    public String getAccountingPeriod()
    {
        return accountingPeriod;
    }

    /**
     * Setter.
     * @param accountingPeriod
     */
    public void setAccountingPeriod(String accountingPeriod)
    {
        this.accountingPeriod = accountingPeriod;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    /**
     * Setter.
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getValuationDate()
    {
        return valuationDate;
    }

    /**
     * Setter.
     * @param valuationDate
     */
    public void setValuationDate(EDITDate valuationDate)
    {
        this.valuationDate = valuationDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getShareBalance()
    {
        return shareBalance;
    }

    /**
     * Setter.
     * @param shareBalance
     */
    public void setShareBalance(EDITBigDecimal shareBalance)
    {
        this.shareBalance = shareBalance;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalCashNetPremium()
    {
        return totalCashNetPremium;
    }

    /**
     * Setter.
     * @param totalCashNetPremium
     */
    public void setTotalCashNetPremium(EDITBigDecimal totalCashNetPremium)
    {
        this.totalCashNetPremium = totalCashNetPremium;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCashGainLoss()
    {
        return cashGainLoss;
    }

    /**
     * Setter.
     * @param cashGainLoss
     */
    public void setCashGainLoss(EDITBigDecimal cashGainLoss)
    {
        this.cashGainLoss = cashGainLoss;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalCashAdminFees()
    {
        return totalCashAdminFees;
    }

    /**
     * Setter.
     * @param totalCashAdminFees
     */
    public void setTotalCashAdminFees(EDITBigDecimal totalCashAdminFees)
    {
        this.totalCashAdminFees = totalCashAdminFees;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalCashCoi()
    {
        return totalCashCoi;
    }

    /**
     * Setter.
     * @param totalCashCoi
     */
    public void setTotalCashCoi(EDITBigDecimal totalCashCoi)
    {
        this.totalCashCoi = totalCashCoi;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalCashReallocations()
    {
        return totalCashReallocations;
    }

    /**
     * Setter.
     * @param totalCashReallocations
     */
    public void setTotalCashReallocations(EDITBigDecimal totalCashReallocations)
    {
        this.totalCashReallocations = totalCashReallocations;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalCashRRD()
    {
        return totalCashRRD;
    }

    /**
     * Setter.
     * @param totalCashRRD
     */
    public void setTotalCashRRD(EDITBigDecimal totalCashRRD)
    {
        this.totalCashRRD = totalCashRRD;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalCashSurrenders()
    {
        return totalCashSurrenders;
    }

    /**
     * Setter.
     * @param totalCashSurrenders
     */
    public void setTotalCashSurrenders(EDITBigDecimal totalCashSurrenders)
    {
        this.totalCashSurrenders = totalCashSurrenders;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalCashContribToMortRsv()
    {
        return totalCashContribToMortRsv;
    }

    /**
     * Setter.
     * @param totalCashContribToMortRsv
     */
    public void setTotalCashContribToMortRsv(EDITBigDecimal totalCashContribToMortRsv)
    {
        this.totalCashContribToMortRsv = totalCashContribToMortRsv;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalCashMAndE()
    {
        return totalCashMAndE;
    }

    /**
     * Setter.
     * @param totalCashMAndE
     */
    public void setTotalCashMAndE(EDITBigDecimal totalCashMAndE)
    {
        this.totalCashMAndE = totalCashMAndE;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalCashAdvisoryFees()
    {
        return totalCashAdvisoryFees;
    }

    /**
     * Setter.
     * @param totalCashAdvisoryFees
     */
    public void setTotalCashAdvisoryFees(EDITBigDecimal totalCashAdvisoryFees)
    {
        this.totalCashAdvisoryFees = totalCashAdvisoryFees;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalCashMgmtFees()
    {
        return totalCashMgmtFees;
    }

    /**
     * Setter.
     * @param totalCashMgmtFees
     */
    public void setTotalCashMgmtFees(EDITBigDecimal totalCashMgmtFees)
    {
        this.totalCashMgmtFees = totalCashMgmtFees;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalCashSVAFees()
    {
        return totalCashSVAFees;
    }

    /**
     * Setter.
     * @param totalCashSVAFees
     */
    public void setTotalCashSVAFees(EDITBigDecimal totalCashSVAFees)
    {
        this.totalCashSVAFees = totalCashSVAFees;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalCashAdvanceTransfers()
    {
        return totalCashAdvanceTransfers;
    }

    /**
     * Setter.
     * @param totalCashAdvanceTransfers
     */
    public void setTotalCashAdvanceTransfers(EDITBigDecimal totalCashAdvanceTransfers)
    {
        this.totalCashAdvanceTransfers = totalCashAdvanceTransfers;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getNetCash()
    {
        return netCash;
    }

    /**
     * Setter.
     * @param netCash
     */
    public void setNetCash(EDITBigDecimal netCash)
    {
        this.netCash = netCash;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getSharesPurchased()
    {
        return sharesPurchased;
    }

    /**
     * Setter.
     * @param sharesPurchased
     */
    public void setSharesPurchased(EDITBigDecimal sharesPurchased)
    {
        this.sharesPurchased = sharesPurchased;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPolicyActivity()
    {
        return policyActivity;
    }

    /**
     * Setter.
     * @param policyActivity
     */
    public void setPolicyActivity(EDITBigDecimal policyActivity)
    {
        this.policyActivity = policyActivity;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getUnitsPurchased()
    {
        return unitsPurchased;
    }

    /**
     * Setter.
     * @param unitsPurchased
     */
    public void setUnitsPurchased(EDITBigDecimal unitsPurchased)
    {
        this.unitsPurchased = unitsPurchased;
    }

    /**
     * Getter.
     * @return
     */
    public ControlBalance getControlBalance()
    {
        return controlBalance;
    }

    /**
     * Setter.
     * @param controlBalance
     */
    public void setControlBalance(ControlBalance controlBalance)
    {
        this.controlBalance = controlBalance;
    }

    /**
     * @see edit.services.db.hibernate.HibernateEntity#hSave()
     */
    public void hSave()
    {
    }

    /**
     * @see edit.services.db.hibernate.HibernateEntity#hDelete()
     */
    public void hDelete()
    {
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ControlBalanceDetail.DATABASE;
    }

    /****************************************************** Finder Methods ************************************************/

    /**
     * Finder By EndingBalanceCycleDate range.
     * @param startCycleDate
     * @param endCycleDate
     * @return
     */
    public static ControlBalanceDetail[] findByEndingBalanceCycleDate_Range(EDITDate startCycleDate,
                                                                      EDITDate endCycleDate)
    {
        String hql = " select controlBalanceDetail" +
                     " from ControlBalanceDetail controlBalanceDetail" +
                     " join fetch controlBalanceDetail.ControlBalance controlBalance" +
                     " where controlBalance.EndingBalanceCycleDate >= :startCycleDate" +
                     " and controlBalance.EndingBalanceCycleDate <= :endCycleDate";

        Map params = new HashMap();

        params.put("startCycleDate", startCycleDate.getFormattedDate());
        params.put("endCycleDate", endCycleDate.getFormattedDate());

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.ENGINE);

        return (ControlBalanceDetail[]) results.toArray(new ControlBalanceDetail[results.size()]);
    }
}
