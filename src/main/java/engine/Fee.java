/*
 * User: sprasad
 * Date: Jan 10, 2005
 * Time: 5:02:57 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.Logging;
import edit.common.vo.*;
import edit.common.*;
import edit.common.exceptions.EDITEngineException;
import engine.dm.dao.FeeDAO;
import engine.sp.*;

import java.util.*;

import businesscalendar.BusinessDay;
import businesscalendar.BusinessCalendar;
import event.Suspense;
import client.ClientAddress;
import logging.*;
import logging.Log;
import fission.utility.*;

public class Fee implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;

    private FeeVO feeVO;

    public static final String DIVISION_FEE_ACCRUAL_TRX_TYPE    = "DFACC";
    public static final String DIVISION_FEE_CASH_TRX_TYPE       = "DFCASH";
    public static final String DIVISION_PURCHASE_TRX_TYPE       = "DPURCH";
    public static final String DIVISION_FEE_OFFSET_TRX_TYPE     = "DFOFF";

    public static final String AUTOMATED_PRICING_TYPE   = "Automated";
    public static final String MANAGED_PRICING_TYPE     = "Managed";
    public static final String HEDGEFUND_PRICING_TYPE   = "HedgeFund";

    public static final String ME_FEE_TYPE          = "MEFee";
    public static final String ADVISORY_FEE_TYPE    = "AdvisoryFee";
    public static final String MANAGEMENT_FEE_TYPE  = "ManagementFee";
    public static final String RVP_FEE_TYPE         = "RVPFee";
    public static final String TRANSFER_FEE_TYPE    = "Transfer";
    public static final String DEATH_FEE_TYPE       = "Death";
    public static final String SURRENDER_FEE_TYPE   = "Surrender";
    public static final String LOAN_FEE_TYPE        = "Loan";
    public static final String GAIN_LOSS_FEE_TYPE   = "GainLoss";
    public static final String TRANSFER_MORT_RESERVE_FEE_TYPE = "TransferMortReserve";
    public static final String PREMIUM_FEE_TYPE     = "Premium";
    public static final String ADMIN_FEE_TYPE       = "AdminFee";
    public static final String COI_FEE_TYPE         = "COI";
    public static final String SVA_FEE_TYPE         = "SVAFee";
    public static final String ADVANCE_TRANSFER_FEE_TYPE = "AdvanceTransfer";

    private FilteredFund filteredFund;

    /**
     * Instantiates a Fee entity with a default FeeVO.
     */
    public Fee()
    {
        init();
    }

    /**
     * Instantiates a Fee entity with a supplied FeeVO.
     *
     * @param feeVO
     */
    public Fee(FeeVO feeVO)
    {
        init();

        this.feeVO = feeVO;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (feeVO == null)
        {
            feeVO = new FeeVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save() throws EDITEngineException
    {
        // releaseInd -- takes two values 'Y' or 'N'
        // when the release check box on the page is checked
        // then the value is set to 'Y' otherwise 'N'

        // statusCT -- takes two values 'N' or 'R'
        // when the fee is saved initially the value is set to 'N' (natural transaction)
        // after the reversal process the value is set to 'R' (reversal transaction)

        // accountingPendingStatus -- takes three values 'P' or 'Y' or 'N'
        // initially when it is saved the without releasing the fee the
        // value is set to 'P' (NR) i.e not released or pending
        // when the fee is released the value is set to 'Y' that means
        // the fee is pending for accounting job.
        // when the accounting job is executed the value is set to 'N'
        // that means no more pending for accounting job.

        String pricingTypeCT = null;
        // when saving the records from the import file, the fee descriptions may not exist.
        if (feeVO.getFeeDescriptionFK() != 0)
        {
            FeeDescriptionVO feeDescriptionVO = getFeeDescription();

            pricingTypeCT = feeDescriptionVO.getPricingTypeCT();

            // if pricingType is 'Automated' the fee should be released by default
            if (AUTOMATED_PRICING_TYPE.equalsIgnoreCase(pricingTypeCT))
            {
                feeVO.setReleaseInd("Y");
            }
        }

        // saving for the first time set default values.
        if (isNew())
        {
            setDefaultValues();

            // calculate only when fee is created.
            setRedemptionDate();
            calculateUnits();
        }
        else
        {
            if (feeVO.getAccountingPendingStatus().equalsIgnoreCase("P"))
            {
                boolean isForRelease = verifyReleaseIndStatus();

                // when the accounting pending status is 'P' allow only to release the Fee.
                if (isForRelease)
                {
                    feeVO.setReleaseDate(new EDITDate().getFormattedDate());
                    feeVO.setAccountingPendingStatus("Y");
                }
                else
                {
                    feeVO.setProcessDateTime(new EDITDateTime().getFormattedDateTime());
                }
            }
            else if (feeVO.getAccountingPendingStatus().equalsIgnoreCase("N"))
            {
                throw new EDITEngineException("Can not modify the Fee - The Account Pending Status is 'N'");
            }
        }

        String transactionTypeCT = feeVO.getTransactionTypeCT();

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        if (transactionTypeCT.equalsIgnoreCase(Fee.DIVISION_PURCHASE_TRX_TYPE))
        {
            long filteredFundFK = feeVO.getFilteredFundFK();
            String effectiveDate = feeVO.getEffectiveDate();

            FilteredFund filteredFund = FilteredFund.findByPK(filteredFundFK);
            String pricingDirection = ((FilteredFundVO) filteredFund.getVO()).getPricingDirection();

            long chargeCodeFK = feeVO.getChargeCodeFK();

            UnitValuesVO[] unitValuesVOs = engineLookup.getUnitValuesByFilteredFundIdDateChargeCode(filteredFundFK,
                                                                                                    effectiveDate,
                                                                                                    pricingDirection,
                                                                                                    chargeCodeFK);

            UnitValuesVO unitValuesVO = null;
            EDITBigDecimal units = null;

            if (unitValuesVOs != null)
            {
                unitValuesVO = unitValuesVOs[0];
                EDITBigDecimal feeAmount = new EDITBigDecimal(feeVO.getTrxAmount());

                units = feeAmount.divideEditBigDecimal(unitValuesVO.getUnitValue());
                units = units.round(7);
                feeVO.setUnits(units.getBigDecimal());
            }
        }

        // always maintenance date time should be updated
        feeVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());

        //set accounting period equal to processDateYYYY/processDateMM if it is null
        if (feeVO.getAccountingPeriod() == null)
        {
            EDITDateTime processDateTime = new EDITDateTime(feeVO.getProcessDateTime());

            EDITDate processDate = processDateTime.getEDITDate();

            feeVO.setAccountingPeriod(DateTimeUtil.buildAccountingPeriod(processDate.getFormattedYear(), processDate.getFormattedMonth()));

//            String[] processDateArray = Util.fastTokenizer(feeVO.getProcessDateTime().substring(0, 10), "/");
//            feeVO.setAccountingPeriod(processDateArray[0] + "/" + processDateArray[1]);
        }

        crudEntityImpl.save(this, ConnectionFactory.ENGINE_POOL, false);

        // a correspondence record needs to be generated for Hedge Funds
        if (Fee.HEDGEFUND_PRICING_TYPE.equalsIgnoreCase(pricingTypeCT))
        {
            generateCorrespondenceRecord();
        }
    }

    /**
     * Saves the fee after the units have been calculated
     * @throws EDITEngineException
     */
    public void saveFeeForUnitUpdate() throws EDITEngineException
    {
        crudEntityImpl.save(this, ConnectionFactory.ENGINE_POOL, false);
    }

    /**
     * Method to save Fee when a Redemption is being processed - can only update the fee
     * on a redemption if the fee has been release (ReleaseInd = 'Y').  All values modified due to
     * the redemption will have already been updated before calling this method (except the
     * MaintDateTime).
     */
    public void saveOnRedemption() throws EDITEngineException
    {
        String pricingTypeCT = null;

        FeeDescriptionVO feeDescriptionVO = getFeeDescription();

        pricingTypeCT = feeDescriptionVO.getPricingTypeCT();

        if (feeVO.getReleaseInd().equalsIgnoreCase("Y"))
        {
            feeVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());

            crudEntityImpl.save(this, ConnectionFactory.ENGINE_POOL, false);

            // a correspondence record needs to be generated for Hedge Funds
            if (Fee.HEDGEFUND_PRICING_TYPE.equalsIgnoreCase(pricingTypeCT))
            {
                generateCorrespondenceRecord();
            }
        }
        else
        {
            throw new EDITEngineException("Cannot Process Redemption - The Fee Has Not Been Released");
        }
    }

    /**
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws EDITEngineException
    {
        if (feeVO.getAccountingPendingStatus().equalsIgnoreCase("N") ||
                feeVO.getAccountingPendingStatus().equalsIgnoreCase("Y"))
        {
            throw new EDITEngineException("Can not delete the Fee - The Account Pending Status is 'N' or 'Y'");
        }

        // verify if any correspondence records exist for this fee. if exists delete correspondence records
        // there should be only one correspondence record associated to fee
        FeeCorrespondence feeCorrespondence = FeeCorrespondence.findByFeePK(getPK());

        if (feeCorrespondence != null)
        {
            feeCorrespondence.delete();
        }

        crudEntityImpl.delete(this, ConnectionFactory.ENGINE_POOL);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return feeVO;
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.feeVO = (FeeVO) voObject;
    }

    /**
     * Getter.
     * @return
     */
    public FilteredFund getFilteredFund()
    {
        return filteredFund;
    }

    public void setFilteredFund(FilteredFund filteredFund)
    {
        this.filteredFund = filteredFund;
    }

    /**
     * Getter.
     * @return
     */
    public Long getFeePK()
    {
        return SessionHelper.getPKValue(feeVO.getFeePK());
    }

    /**
     * Getter.
     * @return
     */
    public Long getFilteredFundFK()
    {
        return SessionHelper.getPKValue(feeVO.getFilteredFundFK());
    }

    /**
     * Getter.
     * @return
     */
    public Long getFeeDescriptionFK()
    {
        return SessionHelper.getPKValue(feeVO.getFeeDescriptionFK());
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return SessionHelper.getEDITDate(feeVO.getEffectiveDate());
    }

    /**
     * Getter.
     * @return
     */
    public EDITDateTime getProcessDateTime()
    {
        return SessionHelper.getEDITDateTime(feeVO.getProcessDateTime());
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getOriginalProcessDate()
    {
        return SessionHelper.getEDITDate(feeVO.getOriginalProcessDate());
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getReleaseDate()
    {
        return SessionHelper.getEDITDate(feeVO.getReleaseDate());
    }

    /**
     * Getter.
     * @return
     */
    public String getReleaseInd()
    {
        return feeVO.getReleaseInd();
    }

    /**
     * Getter.
     * @return
     */
    public String getStatusCT()
    {
        return feeVO.getStatusCT();
    }

    /**
     * Getter.
     * @return
     */
    public String getAccountingPendingStatus()
    {
        return feeVO.getAccountingPendingStatus();
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTrxAmount()
    {
        return SessionHelper.getEDITBigDecimal(feeVO.getTrxAmount());
    }

    /**
     * Getter.
     * @return
     */
    public String getTransactionTypeCT()
    {
        return feeVO.getTransactionTypeCT();
    }

    /**
     * Getter.
     * @return
     */
    public String getContractNumber()
    {
        return feeVO.getContractNumber();
    }

    /**
     * Getter.
     * @return
     */
    public EDITDateTime getMaintDateTime()
    {
        return SessionHelper.getEDITDateTime(feeVO.getMaintDateTime());
    }

    /**
     * Getter.
     * @return
     */
    public String getOperator()
    {
        return feeVO.getOperator();
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAccumulatedTrxAmtReceived()
    {
        return SessionHelper.getEDITBigDecimal(feeVO.getAccumulatedTrxAmtReceived());
    }

    /**
     * Getter.
     * @return
     */
    public String getToFromInd()
    {
        return feeVO.getToFromInd();
    }

    /**
     * Getter.
     * @return
     */
    public Long getChargeCodeFK()
    {
        return SessionHelper.getPKValue(feeVO.getChargeCodeFK());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getUnits()
    {
        return SessionHelper.getEDITBigDecimal(feeVO.getUnits());
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getRedemptionDate()
    {
        return SessionHelper.getEDITDate(feeVO.getRedemptionDate());
    }

    /**
     * Getter.
     * @return
     */
    public String getAccountingPeriod()
    {
        return feeVO.getAccountingPeriod();
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getShares()
    {
        return SessionHelper.getEDITBigDecimal(feeVO.getShares());
    }

    /**
     * Setter.
     * @param feePK
     */
    public void setFeePK(Long feePK)
    {
        feeVO.setFeePK(SessionHelper.getPKValue(feePK));
    }

    /**
     * Setter.
     * @param filteredFundFK
     */
    public void setFilteredFundFK(Long filteredFundFK)
    {
        feeVO.setFilteredFundFK(SessionHelper.getPKValue(filteredFundFK));
    }

    /**
     * Setter.
     * @param feeDescriptionFK
     */
    public void setFeeDescriptionFK(Long feeDescriptionFK)
    {
        feeVO.setFeeDescriptionFK(SessionHelper.getPKValue(feeDescriptionFK));
    }

    /**
     * Setter.
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        feeVO.setEffectiveDate(SessionHelper.getEDITDate(effectiveDate));
    }

    /**
     * Setter.
     * @param processDateTime
     */
    public void setProcessDateTime(EDITDateTime processDateTime)
    {
        feeVO.setProcessDateTime(SessionHelper.getEDITDateTime(processDateTime));
    }

    /**
     * Setter.
     * @param originalProcessDate
     */
    public void setOriginalProcessDate(EDITDate originalProcessDate)
    {
        feeVO.setOriginalProcessDate(SessionHelper.getEDITDate(originalProcessDate));
    }

    /**
     * Setter.
     * @param releaseDate
     */
    public void setReleaseDate(EDITDate releaseDate)
    {
        feeVO.setReleaseDate(SessionHelper.getEDITDate(releaseDate));
    }

    /**
     * Setter.
     * @param releaseInd
     */
    public void setReleaseInd(String releaseInd)
    {
        feeVO.setReleaseInd(releaseInd);
    }

    /**
     * Setter.
     * @param statusCT
     */
    public void setStatusCT(String statusCT)
    {
        feeVO.setStatusCT(statusCT);
    }

    /**
     * Setter.
     * @param accountingPendingStatus
     */
    public void setAccountingPendingStatus(String accountingPendingStatus)
    {
        feeVO.setAccountingPendingStatus(accountingPendingStatus);
    }

    /**
     * Setter.
     * @param trxAmount
     */
    public void setTrxAmount(EDITBigDecimal trxAmount)
    {
        feeVO.setTrxAmount(SessionHelper.getEDITBigDecimal(trxAmount));
    }

    /**
     * Setter.
     * @param transactionTypeCT
     */
    public void setTransactionTypeCT(String transactionTypeCT)
    {
        feeVO.setTransactionTypeCT(transactionTypeCT);
    }

    /**
     * Setter.
     * @param contractNumber
     */
    public void setContractNumber(String contractNumber)
    {
        feeVO.setContractNumber(contractNumber);
    }

    /**
     * Setter.
     * @param maintDateTime
     */
    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        feeVO.setMaintDateTime(SessionHelper.getEDITDateTime(maintDateTime));
    }

    /**
     * Setter.
     * @param operator
     */
    public void setOperator(String operator)
    {
        feeVO.setOperator(operator);
    }

    /**
     * Setter.
     * @param accumTrxAmtReceived
     */
    public void setAccumulatedTrxAmtReceived(EDITBigDecimal accumTrxAmtReceived)
    {
        feeVO.setAccumulatedTrxAmtReceived(SessionHelper.getEDITBigDecimal(accumTrxAmtReceived));
    }

    /**
     * Setter.
     * @param toFromInd
     */
    public void setToFromInd(String toFromInd)
    {
        feeVO.setToFromInd(toFromInd);
    }

    /**
     * Setter.
     * @param shares
     */
    public void setShares(EDITBigDecimal shares)
    {
        feeVO.setShares(SessionHelper.getEDITBigDecimal(shares));
    }

    /**
     * Setter.
     * @param chargeCodeFK
     */
    public void setChargeCodeFK(Long chargeCodeFK)
    {
        feeVO.setChargeCodeFK(SessionHelper.getPKValue(chargeCodeFK));
    }

    /**
     * Setter.
     * @param units
     */
    public void setUnits(EDITBigDecimal units)
    {
        feeVO.setUnits(SessionHelper.getEDITBigDecimal(units));
    }

    /**
     * Setter.
     * @param redemptionDate
     */
    public void setRedemptionDate(EDITDate redemptionDate)
    {
        feeVO.setRedemptionDate(SessionHelper.getEDITDate(redemptionDate));
    }

    /**
     * Setter.
     * @param accountingPeriod
     */
    public void setAccountingPeriod(String accountingPeriod)
    {
        feeVO.setAccountingPeriod(accountingPeriod);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return feeVO.getFeePK();
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#isNew()
     */
    public boolean isNew()
    {
        return crudEntityImpl.isNew(this);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    /**
     * reverses the entry.
     */
    public void reverse() throws EDITEngineException
    {
        if (feeVO.getAccountingPendingStatus().equalsIgnoreCase("N"))
        {
            feeVO.setOriginalProcessDate(feeVO.getProcessDateTime());
            feeVO.setAccountingPendingStatus("Y");
            feeVO.setStatusCT("R");
            feeVO.setProcessDateTime(new EDITDateTime().getFormattedDateTime());
            feeVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());
            crudEntityImpl.save(this, ConnectionFactory.ENGINE_POOL, false);
        }
        else
        {
            throw new EDITEngineException("Can not reverse the Fee - Accounting Pending Status is not 'N'");
        }
    }

    public void associateFeeDescription(FeeDescription feeDescription)
    {
        feeVO.setFeeDescriptionFK(feeDescription.getPK());
    }

    /************************************** Private Methods **************************************/

    /**
     * to get feeDescription details
     */
    public FeeDescriptionVO getFeeDescription()
    {
        FeeDescription feeDescription = FeeDescription.findByPK(feeVO.getFeeDescriptionFK());

        if (feeDescription != null)
        {
            return (FeeDescriptionVO) feeDescription.getVO();
        }
        else
        {
            return null;
        }
    }

    /**
     * sets the default values when the fee created
     */
    private void setDefaultValues()
    {
        // if the effective date is not entered default to current date.
        if (feeVO.getEffectiveDate() == null)
        {
            feeVO.setEffectiveDate(new EDITDate().getFormattedDate());
        }

        feeVO.setRedemptionDate(feeVO.getEffectiveDate());

        // when the fee is relased while saving the fee for first time itself.
        if (feeVO.getReleaseInd().equalsIgnoreCase("Y"))
        {
            feeVO.setReleaseDate(new EDITDate().getFormattedDate());
            feeVO.setAccountingPendingStatus("Y");
        }
        else if (feeVO.getReleaseInd().equalsIgnoreCase("N"))
        {
            // 'P' means fee not released(NR)
            feeVO.setReleaseDate(null);
            feeVO.setAccountingPendingStatus("P");
        }

        feeVO.setProcessDateTime(new EDITDateTime().getFormattedDateTime());
        feeVO.setStatusCT("N");
    }

    /**
     * to check release value and if it is 'Y' then release date and other values are set
     */
    private boolean verifyReleaseIndStatus()
    {
        // when the fee is going to be released...
        // the release box on the page is checked
        boolean isForRelease = false;
        if(feeVO.getReleaseInd().equalsIgnoreCase("Y"))
        {
            // to check the previous value of release column
            Fee feeBeforeSaving = findByPK(getPK());
            String releaseIndPreviousValue = ((FeeVO) feeBeforeSaving.getVO()).getReleaseInd();
            // to make sure that releasedate is set only the first time when the fee is released
            // otherewise the release date gets updated everytime the fee is saved
            if (releaseIndPreviousValue.equalsIgnoreCase("N"))
            {
                isForRelease = true;
            }
        }

        return isForRelease;
    }

    /**
     *  calculates redemptionDate and sets the value to feeVO.
     */
    private void setRedemptionDate()
    {
        String pricingTypeCT = null;

        if (feeVO.getFeeDescriptionFK() != 0)
        {
            FeeDescriptionVO feeDescriptionVO = getFeeDescription();

            pricingTypeCT = feeDescriptionVO.getPricingTypeCT();
        }

        String trxTypeCT = feeVO.getTransactionTypeCT();

        if (!Fee.DIVISION_FEE_CASH_TRX_TYPE.equalsIgnoreCase(trxTypeCT))
        {
            // for pricingTypeCT 'HedgeFund' business rules need to be applied to get redemptionDate
            if (Fee.HEDGEFUND_PRICING_TYPE.equalsIgnoreCase(pricingTypeCT))
            {
                EDITDate redemptionDate = calculateRedemptionDate(new EDITDate(feeVO.getEffectiveDate()));

                feeVO.setRedemptionDate(redemptionDate.getFormattedDate());
            }
        }
    }

    /**
     * calculates redemptionDate after applying business rules
     * this is applicable only to pricingTypeCT of 'HedgeFund' and not DFCASH transactions
     * @param effectiveDate
     * @return
     */
    private EDITDate calculateRedemptionDate(EDITDate effectiveDate)
    {
        long filteredFundPK = feeVO.getFilteredFundFK();

        FilteredFundVO filteredFundVO = CSCache.getCSCache().getFilteredFundVOBy_FilteredFundPK(filteredFundPK);

        String notificationModeCT = filteredFundVO.getDivisionFeesLiquidationModeCT();

        int notificationDays = filteredFundVO.getDivisionFeesLiquidationDays();

        String notificationType = filteredFundVO.getDivFeesLiquidatnDaysTypeCT();

        EDITDate effectiveDateDatePlusNotificationDays = null;

        if (notificationDays > 0)
        {
            if (FilteredFund.BUSINESS_NOTIFICATION_TYPE_CT.equalsIgnoreCase(notificationType))
            {
                BusinessDay busDay = new BusinessCalendar().findNextBusinessDay(effectiveDate, notificationDays);

                effectiveDateDatePlusNotificationDays = busDay.getBusinessDate();
            }
            else if (FilteredFund.CALENDAR_NOTIFICATION_TYPE_CT.equalsIgnoreCase(notificationType))
            {
                effectiveDateDatePlusNotificationDays = effectiveDate.addDays(notificationDays);
            }
        }

        EDITDate redemptionDate = findRedemptionDate(effectiveDateDatePlusNotificationDays, notificationModeCT);

        BusinessDay businessDay = new BusinessCalendar().getBestBusinessDay(redemptionDate);

        redemptionDate = businessDay.getBusinessDate();

        return redemptionDate;
    }

    /**
     * returns correspondence date. This is applicable only to pricingTypeCT of 'HedgeFund'
     * @return
     */
    private EDITDate getCorrespondenceDate()
    {
        String effectiveDate = feeVO.getEffectiveDate();

        EDITDate trxEffectiveDate = new EDITDate(effectiveDate);

        long filteredFundPK = feeVO.getFilteredFundFK();

        FilteredFundVO filteredFundVO = (FilteredFundVO) FilteredFund.findByPK(filteredFundPK).getVO();

        int notificationDays = filteredFundVO.getDivisionFeesLiquidationDays();

        String notificationType = filteredFundVO.getDivFeesLiquidatnDaysTypeCT();

        EDITDate correspondenceDate = null;

        if (notificationDays > 0)
        {
            if (FilteredFund.BUSINESS_NOTIFICATION_TYPE_CT.equalsIgnoreCase(notificationType))
            {
                BusinessDay busDay = new BusinessCalendar().findPreviousBusinessDay(trxEffectiveDate, notificationDays);

                correspondenceDate = busDay.getBusinessDateNew();
            }
            else if (FilteredFund.CALENDAR_NOTIFICATION_TYPE_CT.equalsIgnoreCase(notificationType))
            {
                correspondenceDate = trxEffectiveDate.subtractDays(notificationDays);
            }
        }

        return correspondenceDate;
    }

    /**
     * helper method for the method #calculateRedemptionDate
     * it verifies in to which part of the year the date belongs to
     * depending on 'notificationModeCT' and returns the end date
     * @param date
     * @param notificationModeCT
     * @return
     */
    private EDITDate findRedemptionDate(EDITDate date, String notificationModeCT)
    {
        EDITDate endDate = null;

        if (FilteredFund.MONTHLY_NOTIFICATION_MODE_CT.equalsIgnoreCase(notificationModeCT))
        {
            endDate = date.getEndOfMonthDate();
        }
        else if (FilteredFund.QUARTERLY_NOTIFICATION_MODE_CT.equalsIgnoreCase(notificationModeCT))
        {
            endDate = date.getEndOfQuarterDate();
        }
        else if (FilteredFund.SEMI_ANNUAL_NOTIFICATION_MODE_CT.equalsIgnoreCase(notificationModeCT))
        {
            date.getEndOfSemiAnnualDate();
        }
        else if (FilteredFund.ANNUAL_NOTIFICATION_MODE_CT.equalsIgnoreCase(notificationModeCT))
        {
            endDate = date.getEndOfYearDate();
        }

        return endDate;
    }

    /**
     * Generates correspondence record.
     */
    private void generateCorrespondenceRecord()
    {
        // Generate corrspondence record.
        // if there is no Correspondence record for this Fee
        if (FeeCorrespondence.findByFeePK(getPK()) == null)
        {
            FeeCorrespondenceVO feeCorrespondenceVO = new FeeCorrespondenceVO();

            feeCorrespondenceVO.setFeeCorrespondencePK(0);

            feeCorrespondenceVO.setStatusCT("P");
            EDITDate correspondenceDate = getCorrespondenceDate();
            feeCorrespondenceVO.setCorrespondenceDate(correspondenceDate.getFormattedDate());
            feeCorrespondenceVO.setAddressTypeCT(ClientAddress.CLIENT_PRIMARY_ADDRESS);
            feeCorrespondenceVO.setNotificationAmount(feeVO.getTrxAmount());

            FeeCorrespondence feeCorrespondence = new FeeCorrespondence(feeCorrespondenceVO);

            feeCorrespondence.associateFee(this);

            feeCorrespondence.save();
        }
    }

    /**
     * Calculates units.
     */
    private void calculateUnits()
    {
        String transactionTypeCT = feeVO.getTransactionTypeCT();

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        if (Fee.DIVISION_PURCHASE_TRX_TYPE.equalsIgnoreCase(transactionTypeCT))
        {
            long filteredFundFK = feeVO.getFilteredFundFK();
            String redemptionDate = feeVO.getRedemptionDate();

            FilteredFund filteredFund = FilteredFund.findByPK(filteredFundFK);
            String pricingDirection = ((FilteredFundVO) filteredFund.getVO()).getPricingDirection();

            long chargeCodeFK = feeVO.getChargeCodeFK();

            UnitValuesVO[] unitValuesVOs = engineLookup.getUnitValuesByFilteredFundIdDateChargeCode(filteredFundFK,
                                                                                                 redemptionDate,
                                                                                                 pricingDirection,
                                                                                                 chargeCodeFK);

            UnitValuesVO unitValuesVO = null;
            EDITBigDecimal units = null;

            if (unitValuesVOs != null)
            {
                unitValuesVO = unitValuesVOs[0];
                EDITBigDecimal feeAmount = new EDITBigDecimal(feeVO.getTrxAmount());

                units = feeAmount.divideEditBigDecimal(unitValuesVO.getUnitValue());
                units = units.round(7);
                feeVO.setUnits(units.getBigDecimal());
            }
        }
    }

    /************************************** Static Methods **************************************/

    /**
     * Finder method by PK.
     *
     * @param feePK
     */
    public static final Fee findByPK(long feePK)
    {
        Fee fee = null;

        FeeVO[] feeVOs = new FeeDAO().findByPK(feePK);

        if (feeVOs != null)
        {
            fee = new Fee(feeVOs[0]);
        }

        return fee;
    }

    /**
     * Finder method by feeDescriptionPK.
     * @param feeDescriptionPK
     * @return
     */
    public static final Fee[] findByFeeDescriptionPK(long feeDescriptionPK)
    {
        FeeVO[] feeVOs = new FeeDAO().findByFeeDescriptionPK(feeDescriptionPK);

        return (Fee[]) CRUDEntityImpl.mapVOToEntity(feeVOs, Fee.class);
    }

    /**
     * Finder method by filteredFundPK.
     * @param filteredFundPK
     * @return
     */
    public static final Fee[] findByFilteredFundPK(long filteredFundPK)
    {
        FeeVO[] feeVOs = new FeeDAO().findByFilteredFundPK(filteredFundPK);

        return (Fee[]) CRUDEntityImpl.mapVOToEntity(feeVOs, Fee.class);
    }

    /**
     * Finder method by filteredFundPK and pricingTypeCT and feeRedemption.
     * Returns the fees that satisfy the following condition
     * Fee.TrxAmount - Fee.AccumulatedTrxAmtReceived > 0.
     * @param filteredFundPK
     * @param pricingTypeCT
     * @param feeRedemption
     * @return
     */
    public static final Fee[] findByFilteredFundPK_And_PricingTypeCT_And_FeeRedemption(long filteredFundPK,
                                                                                       String pricingTypeCT,
                                                                                       String feeRedemption,
                                                                                       String transactionTypeCT)
    {
        FeeVO[] feeVOs = new FeeDAO().findByFilteredFundPK_And_PricingTypeCT_And_FeeRedemption_And_TrxTypeCT
                                                                                               (filteredFundPK,
                                                                                               pricingTypeCT,
                                                                                               feeRedemption,
                                                                                               transactionTypeCT);

        // Qualified means where Fee.TrxAmount - Fee.AccumulatedTrxAmtReceived > 0
        FeeVO[] feeVOsQualified = null;
        List feeVOsQualifiedList = new ArrayList();

        if (feeVOs != null)
        {
            EDITBigDecimal trxAmount = new EDITBigDecimal();

            for(int i = 0; i < feeVOs.length; i++)
            {
                trxAmount = new EDITBigDecimal(feeVOs[i].getTrxAmount());
                if (trxAmount.subtractEditBigDecimal(feeVOs[i].getAccumulatedTrxAmtReceived()).isGT("0"))
                {
                    feeVOsQualifiedList.add(feeVOs[i]);
                }
            }
        }

        feeVOsQualified = (FeeVO[]) feeVOsQualifiedList.toArray(new FeeVO[feeVOsQualifiedList.size()]);

        return (Fee[]) CRUDEntityImpl.mapVOToEntity(feeVOsQualified, Fee.class);
    }

    /**
     * Finder method by filteredFundFK and dates (date search is further defined by date type -
     * search can be performed on the EffectiveDate or the ProcessDateTime)
     * @param filteredFundFK
     * @param startDate
     * @param endDate
     * @param dateType
     * @return
     */
    public static final Fee[] findByFilteredFundPK_And_Date(
            long filteredFundFK,
            String startDate,
            String endDate,
            String dateType)
    {
        FeeVO[] feeVOs = new FeeDAO().findByFilteredFundPK_And_Date(filteredFundFK, startDate, endDate, dateType);

        return (Fee[]) CRUDEntityImpl.mapVOToEntity(feeVOs, Fee.class);
    }

    /**
     * Finder method by filteredFundFK and dates (date search is further defined by date type -
     * and chargeCodeFK -- search can be performed on the EffectiveDate or the ProcessDateTime)
     * @param filteredFundFK
     * @param startDate
     * @param endDate
     * @param dateType
     * @param chargeCodeFK
     * @return
     */
    public static final Fee[] findByFilteredFundPKDateChargeCode(
            long filteredFundFK,
            String startDate,
            String endDate,
            String dateType,
            long chargeCodeFK)
    {
        FeeVO[] feeVOs = new FeeDAO().
                    findByFilteredFundPKDateChargeCode(
                        filteredFundFK, startDate, endDate, dateType, chargeCodeFK);

        return (Fee[]) CRUDEntityImpl.mapVOToEntity(feeVOs, Fee.class);
	}

    /**
     * Finder method by filteredFundFK and dates (date search is further defined by date type -
     * and chargeCodeFK and TransactionTypeCT -- search can be performed on the EffectiveDate or the ProcessDateTime)
     * @param filteredFundFK
     * @param startDate
     * @param endDate
     * @param dateType
     * @param chargeCodeFK
     * @param transactionTypeCT
     * @return
     */
    public static final Fee[] findByFilteredFundPK_DateRange_ChargeCodeFK_TransactionTypeCT(
            long filteredFundFK,
            String startDate,
            String endDate,
            String dateType,
            long chargeCodeFK,
            String transactionTypeCT)
    {
        FeeVO[] feeVOs = new FeeDAO().findByFilteredFundPK_DateRange_ChargeCodeFK_TransactionTypeCT(filteredFundFK,
                startDate, endDate, dateType, chargeCodeFK, transactionTypeCT);

        return (Fee[]) CRUDEntityImpl.mapVOToEntity(feeVOs, Fee.class);
	}

    /**
     * Finder method by filteredFundFK and dates (date search is further defined by date type -
     * and chargeCodeFK -- search can be performed on the EffectiveDate or the ProcessDateTime)
     * @param filteredFundFK
     * @param startDate
     * @param endDate
     * @param dateType
     * @param chargeCodeFK
     * @param feeTypeCT
     * @return
     */
    public static final Fee[] findByFilteredFundPKDateChargeCodeAndFeeType(
            long filteredFundFK, 
            String startDate, 
            String endDate, 
            String dateType,
            long chargeCodeFK,
            String feeTypeCT)
    {
        FeeVO[] feeVOs = new FeeDAO().
                    findByFilteredFundPKDateChargeCodeAndFeeType(
                        filteredFundFK, startDate, endDate, dateType, chargeCodeFK, feeTypeCT);

        return (Fee[]) CRUDEntityImpl.mapVOToEntity(feeVOs, Fee.class);
	}
    

    /**
     * Finder method by FeeType and EffectiveDate
     * @param feeType
     * @param runDate
     * @return
     */
    public static final FeeVO[] findByFeeType_Date(String feeType, String runDate)
    {
        return new FeeDAO().findByFeeType_And_Date(feeType, runDate);
    }

    public static final void updateUnits(long filteredFundFK, String pricingDirection) throws Exception
    {
        FeeVO[] feeVOs = new FeeDAO().findFeesForUnitUpdate(filteredFundFK);

        if (feeVOs != null)
        {
            engine.business.Lookup engineLookup = new engine.component.LookupComponent();

            for (int i = 0; i < feeVOs.length; i++)
            {
                String effectiveDate = feeVOs[i].getEffectiveDate();
                UnitValuesVO[] uvs =
                        engineLookup.getUnitValuesByFilteredFundIdDateChargeCode(filteredFundFK,
                                                                                 effectiveDate,
                                                                                 pricingDirection,
                                                                                 feeVOs[i].getChargeCodeFK());
                if (uvs != null)
                {
                    EDITBigDecimal unitValue = new EDITBigDecimal(uvs[0].getUnitValue());
                    EDITBigDecimal fee = new EDITBigDecimal(feeVOs[i].getTrxAmount());
                    EDITBigDecimal units = fee.divideEditBigDecimal(unitValue).round(10);

                    feeVOs[i].setUnits(units.getBigDecimal());

                    new Fee(feeVOs[i]).saveFeeForUnitUpdate();
                }
            }
        }
    }

    /**
     *
     * @param feeAmountsMapByFeePK
     * @param suspensePK
     * @throws EDITEngineException
     */
    public static void  updateFeesAndSuspense(Map feeAmountsMapByFeePK, long suspensePK) throws EDITEngineException
    {
        Iterator feeAmountsIterator = null;

        if (feeAmountsMapByFeePK != null)
        {
            feeAmountsIterator = feeAmountsMapByFeePK.keySet().iterator();
        }

        Fee fee = null;
        FeeVO feeVO = null;
        String key = null;
        long feePK = 0;
        EDITBigDecimal accumulatedAmtReceived = new EDITBigDecimal();
        EDITBigDecimal currentAmtReceived = new EDITBigDecimal();
        EDITBigDecimal totalTrxAmount = new EDITBigDecimal();

        if (feeAmountsIterator != null)
        {
            while (feeAmountsIterator.hasNext())
            {
                key = (String) feeAmountsIterator.next();
                feePK = Long.parseLong(key);
                feeVO = (FeeVO) findByPK(feePK).getVO();

                currentAmtReceived = (EDITBigDecimal) feeAmountsMapByFeePK.get(key);

                if (feeVO != null)
                {
                    accumulatedAmtReceived = new EDITBigDecimal(feeVO.getAccumulatedTrxAmtReceived());
                    accumulatedAmtReceived = accumulatedAmtReceived.addEditBigDecimal(currentAmtReceived);
                    feeVO.setAccumulatedTrxAmtReceived(accumulatedAmtReceived.getBigDecimal());
                    fee = new Fee(feeVO);

                    try
                    {
                        fee.saveOnRedemption();

                        totalTrxAmount = totalTrxAmount.addEditBigDecimal(currentAmtReceived);
                    }
                    catch (EDITEngineException e)
                    {
                        System.out.println(e.getMessage());

                        LogEvent logEvent = new LogEvent(e.getMessage(), e);

                        Logging.getLogger(Logging.GENERAL_EXCEPTION).error(logEvent);

                        Log.logGeneralExceptionToDatabase(null, e);
                    }
                }
            }
        }

        Suspense suspense = Suspense.findByPK(suspensePK);

        EDITBigDecimal suspenseAmount = new EDITBigDecimal(suspense.getAsVO().getSuspenseAmount(), 2);
        if (totalTrxAmount != null)
        {
            suspenseAmount = suspenseAmount.subtractEditBigDecimal(totalTrxAmount);
            if (suspenseAmount.isEQ(new EDITBigDecimal()))
            {
                suspense.setMaintenanceInd("A");
            }
        }

        suspense.setSuspenseAmount(suspenseAmount);

        try
        {
            suspense.save();
        }
        catch (Exception e)
        {
            System.out.println("Suspense Entry Could not be Saved");
            
          System.out.println(e);

            e.printStackTrace();

            throw new EDITEngineException(e.getMessage());
        }
    }
}
