/*
 * User: gfrosti
 * Date: Jun 10, 2004
 * Time: 1:55:44 PM

 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package billing;

import edit.common.*;
import edit.common.vo.AreaValueVO;
import edit.common.exceptions.EDITCaseException;
import edit.services.db.hibernate.*;
import edit.services.EditServiceLocator;
import edit.services.logging.Logging;

import java.util.*;

import contract.*;
import contract.ChangeHistory;
import contract.PremiumDue;
import contract.CommissionPhase;
import fission.utility.DateTimeUtil;
import group.*;
import group.PayrollDeductionSchedule;
import staging.*;
import engine.ProductStructure;
import engine.AreaKey;
import engine.AreaValue;
import engine.FilteredAreaValue;
import batch.business.Batch;
import logging.LogEvent;
import logging.Log;
import businesscalendar.*;
import client.ClientDetail;
import contract.ContractClient;
import engine.Company;
import role.ClientRole;
import edit.common.vo.BillScheduleVO;


/**
 * BillSchedule can be applied at the ContractGroup level or the Segment level (directly).
 * This may change in the future where BillSchedule as applied [only] at the ContractGroup
 * level as long as all Segments are associated with a ContractGroup. Sub-ContractGroups
 * that have BillSchedule defined will override the BillSchedule associated at any higher level.
 */
public class BillSchedule extends HibernateEntity implements IStaging
{
    public static String AREA_GROUPING_BILLING = "BILLING";
    public static String AREA_FIELD_LEADDAYS = "LEADDAYS";
    public static String AREA_FIELD_EFTLEADDAYS = "EFTLEADDAYS";

    public static final String THIRTEENTHLY = "13thly";
    public static final String ANNUAL = "Annual";
    public static final String MONTHLY = "Month";
    public static final String QUARTERLY = "Quart";
    public static final String SEMI_ANNUAL = "SemiAnn";
    public static final String VARIABLE_MONTHLY = "VarMonth";

    public static final String BILL_METHOD_LISTBILL = "List";
    public static final String BILL_METHOD_DIRECT_BILL = "Direct";
    public static final String BILL_METHOD_EFT = "EFT";

    public static final String BILL_TYPE_INDIVUAL = "INDIV";
    public static final String BILL_TYPE_GROUP = "GRP";
    
    public static final String BILLSCHEDULE_STATUS_ACTIVE = "Active";

    /**
     * The PK.
     */ 
    private Long billSchedulePK;

    private boolean processSegment;

//    /**
//     * The (optional) associated ContractGroupFK.
//     */
//    private Long contractGroupFK;
//
//    /**
//     * The (optional) associated SegmentFK.
//     */
//    private Long segmentFK;
    
    /**
     * @todo define
     */
    private String billingCompanyCT;
    
    /**
     * Due date of the first bill. Other dates will be set relative to this.
     */
    private EDITDate firstBillDueDate;
    
    /**
     * It's the number of days prior to the bill's due date that the bill is generated.
     */
    private int leadDaysOR;
    
    /**
     * The day of the week the bill is generated.
     */
    private String weekDayCT;
    
    /**
     * The date this bill takes effect.
     */
    private EDITDate effectiveDate;
    
    /**
     * The date this bill is no longer in effect.
     */
    private EDITDate terminationDate;
    
    /**
     * @todo define
     */
    private String statusCT;
   
    /**
     * @todo define
     */ 
    private String sortOptionCT;
    
    /**
     * @todo define
     */ 
    private String billingConsolidationCT;
    
    /**
     * @todo define
     */ 
    private String socialSecurityMaskCT;
    
    /**
     * @todo define
     */
    private int numberOfCopiesGroup;
    
    /**
     * @todo define
     */
    private int numberOfCopiesAgent;
    
    /**
     * @todo define
     */
    private EDITDate nextBillExtractDate;

    /**
     * @todo define
     */
    private EDITDate nextBillDueDate;

    /**
     * @todo define
     */
    private EDITDate lastBillDueDate;

    /**
     * @todo define
     */
    private String billingModeCT;

    /**
     * A bill classification. Examples are Bill, IndividualBill, FamilyBill, etc.
     */
    private String billTypeCT;

    /**
     * Security Operator's Name (login name) of the operator who created this object
     */
    private String creationOperator;

    /**
     * Date this object was created
     */
    private EDITDate creationDate;

    /**
     * Currently, this is the built name of the creation operator using the LastName and FirstName of the Security Operator
     * The format is LastName, FirstName
     */
    private String repName;

    /**
     * Phone number from Security's Operator table for the creationOperator
     */
    private String repPhoneNumber;

    /**
     * Billing method for the given schedule - examples are List, Direct, EFT
     */
    private String billMethodCT;

    /**
     * First Month for which billing will be skipped.
     */
    private String skipMonthStart1CT;

    /**
     * Used in conjunction with skipMonthStart1CT - the number of months for which billing will be skipped.
     */
    private String skipNumberOfMonths1CT;

    /**
     * Second Month for which billing will be skipped.
     */
    private String skipMonthStart2CT;

    /**
     * Used in conjunction with skipMonthStart2CT - the number of months for which billing will be skipped.
     */
    private String skipNumberOfMonths2CT;

    /**
     * Third Month for which billing will be skipped.
     */
    private String skipMonthStart3CT;

    /**
     * Used in conjunction with skipMonthStart3CT - the number of months for which billing will be skipped.
     */
    private String skipNumberOfMonths3CT;

    /**
     * Used in setting up the PRDDue and Extract dates on PayrollDeductionSchedule
     */
    private EDITDate firstDeductionDate;

    /**
     * @todo
     */
    private EDITDate lastDeductionDate;

    /**
     * Used in calculating PRDDue and Extract dates for payroll deduction
     */
    private String deductionFrequencyCT;

    /**
     * @todo
     */
    private EDITBigDecimal requiredPremiumAmount;
    
    /**
     * @todo
     */
    private EDITDate transitionPeriodEndDate;
    private EDITDate billChangeStartDate;
    private EDITDate varMonthDedChangeStartDate;
    private EDITDate changeEffectiveDate;
    private EDITDate lastPremiumChangeStartDate;
    private EDITDate bcPriorNextBillDueDate;
    private int eftDraftDay;
    private String eftDraftDayStartMonthCT;

    //  Children
    private Set<BillGroup> billGroups;
    private Set<Segment> segments;
    private Set<ContractGroup> contractGroups;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;

    /**
     * Instantiates a BillSchedule entity.
     */
    public BillSchedule()
    {
        init();
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (billGroups == null)
        {
            billGroups = new HashSet<BillGroup>();
        }

        if (segments == null)
        {
            segments = new HashSet<Segment>();
        }

        if (contractGroups == null)
        {
            contractGroups = new HashSet<ContractGroup>();
        }
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, BillSchedule.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, BillSchedule.DATABASE);
    }

    public Long getBillSchedulePK()
    {
        return this.billSchedulePK;
    }

    public void setBillSchedulePK(Long billSchedulePK)
    {
        this.billSchedulePK = billSchedulePK;
    }

//    /**
//     * @see #contractGroupFK
//     * @return Long
//     */
//    public Long getContractGroupFK()
//    {
//        return contractGroupFK;
//    }
//
//    /**
//     * @see #contractGroupFK
//     * @param contractGroupFK
//     */
//    public void setContractGroupFK(Long contractGroupFK)
//    {
//        this.contractGroupFK = contractGroupFK;
//    }

    /**
     * @see #billingCompanyCT
     * @return String
     */
    public String getBillingCompanyCT()
    {
        return billingCompanyCT;
    }

    /**
     * @see #billingCompanyCT
     * @param billingCompanyCT
     */
    public void setBillingCompanyCT(String billingCompanyCT)
    {
        this.billingCompanyCT = billingCompanyCT;
    }

    /**
     * @see #firstBillDueDate
     * @return EDITDate
     */
    public EDITDate getFirstBillDueDate()
    {
        return firstBillDueDate;
    }

    /**
     * @see #firstBillDueDate
     * @param firstBillDueDate
     */
    public void setFirstBillDueDate(EDITDate firstBillDueDate)
    {
        this.firstBillDueDate = firstBillDueDate;
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIFirstBillDueDate()
    {
        String date = null;

        if (getFirstBillDueDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getFirstBillDueDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIFirstBillDueDate(String uiFirstBillDueDate)
    {
        if (uiFirstBillDueDate != null)
        {
            setFirstBillDueDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiFirstBillDueDate));
        }
    }

    /**
     * @see #leadDaysOR
     * @return int
     */
    public int getLeadDaysOR()
    {
        return leadDaysOR;
    }

    /**
     * @see #leadDaysOR
     * @param leadDaysOR
     */
    public void setLeadDaysOR(int leadDaysOR)
    {
        this.leadDaysOR = leadDaysOR;
    }

    /**
     * @see #weekDayCT
     * @return String
     */
    public String getWeekDayCT()
    {
        return weekDayCT;
    }

    /**
     * @see #weekDayCT
     * @param weekDayCT
     */
    public void setWeekDayCT(String weekDayCT)
    {
        this.weekDayCT = weekDayCT;
    }

    /**
     * @see #effectiveDate
     * @return EDITDate
     */
    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    /**
     * @see #effectiveDate
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIBillEffectiveDate()
    {
        String date = null;

        if (getEffectiveDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getEffectiveDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIBillEffectiveDate(String uiBillEffectiveDate)
    {
        if (uiBillEffectiveDate != null)
        {
            setEffectiveDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiBillEffectiveDate));
        }
    }

    /**
     * @see #terminationDate
     * @return EDITDate
     */
    public EDITDate getTerminationDate()
    {
        return terminationDate;
    }

    /**
     * @see #terminationDate
     * @param terminationDate
     */
    public void setTerminationDate(EDITDate terminationDate)
    {
        this.terminationDate = terminationDate;
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIBillTerminationDate()
    {
        String date = null;

        if (getTerminationDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getTerminationDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIBillTerminationDate(String uiBillTerminationDate)
    {
        if (uiBillTerminationDate != null)
        {
            setFirstBillDueDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiBillTerminationDate));
        }
    }

    /**
     * @see #statusCT
     * @return String
     */
    public String getStatusCT()
    {
        return statusCT;
    }

    /**
     * @see #statusCT
     * @param statusCT
     */
    public void setStatusCT(String statusCT)
    {
        this.statusCT = statusCT;
    }

    /**
     * @see #sortOptionCT
     * @return String
     */
    public String getSortOptionCT()
    {
        return sortOptionCT;
    }

    /**
     * @see #sortOptionCT
     * @param sortOptionCT
     */
    public void setSortOptionCT(String sortOptionCT)
    {
        this.sortOptionCT = sortOptionCT;
    }

    /**
     * @see #billingConsolidationCT
     * @return String
     */
    public String getBillingConsolidationCT()
    {
        return billingConsolidationCT;
    }

    /**
     * @see #billingConsolidationCT
     * @param billingConsolidationCT
     */
    public void setBillingConsolidationCT(String billingConsolidationCT)
    {
        this.billingConsolidationCT = billingConsolidationCT;
    }

    /**
     * @see #socialSecurityMaskCT
     * @return String
     */
    public String getSocialSecurityMaskCT()
    {
        return socialSecurityMaskCT;
    }

    /**
     * @see #socialSecurityMaskCT
     * @param socialSecurityMaskCT
     */
    public void setSocialSecurityMaskCT(String socialSecurityMaskCT)
    {
        this.socialSecurityMaskCT = socialSecurityMaskCT;
    }

    /**
     * @see #numberOfCopiesGroup
     * @return int
     */
    public int getNumberOfCopiesGroup()
    {
        return numberOfCopiesGroup;
    }

    /**
     * @see #numberOfCopiesGroup
     * @param numberOfCopiesGroup
     */
    public void setNumberOfCopiesGroup(int numberOfCopiesGroup)
    {
        this.numberOfCopiesGroup = numberOfCopiesGroup;
    }

    /**
     * @see #numberOfCopiesAgent
     * @return int
     */
    public int getNumberOfCopiesAgent()
    {
        return numberOfCopiesAgent;
    }

    /**
     * @see #numberOfCopiesAgent
     * @param numberOfCopiesAgent
     */
    public void setNumberOfCopiesAgent(int numberOfCopiesAgent)
    {
        this.numberOfCopiesAgent = numberOfCopiesAgent;
    }

    /**
     * @see #billingModeCT
     * @return String
     */
    public String getBillingModeCT()
    {
        return billingModeCT;
    }

    /**
     * @see #billingModeCT
     * @param billingModeCT
     */
    public void setBillingModeCT(String billingModeCT)
    {
        this.billingModeCT = billingModeCT;
    }

    /**
     * @see #billTypeCT
     * @return String
     */
    public String getBillTypeCT()
    {
        return billTypeCT;
    }

    /**
     * @see #billTypeCT
     * @param billTypeCT
     */
    public void setBillTypeCT(String billTypeCT)
    {
        this.billTypeCT = billTypeCT;
    }

    /**
     * @see #creationOperator
     * @return String
     */
    public String getCreationOperator()
    {
        return creationOperator;
    }

    /**
     * @see #creationOperator
     * @param creationOperator
     */
    public void setCreationOperator(String creationOperator)
    {
        this.creationOperator = creationOperator;
    }

    /**
     * @see #creationDate
     * @return EDITDate
     */
    public EDITDate getCreationDate()
    {
        return creationDate;
    }

    /**
     * @see #creationDate
     * @param creationDate
     */
    public void setCreationDate(EDITDate creationDate)
    {
        this.creationDate = creationDate;
    }

    /**
     * @see #repName
     * @return String
     */
    public String getRepName()
    {
        return repName;
    }

    /**
     * @see #repName
     * @param repName
     */
    public void setRepName(String repName)
    {
        this.repName = repName;
    }

    /**
     * @see #repPhoneNumber
     * @return String
     */
    public String getRepPhoneNumber()
    {
        return repPhoneNumber;
    }

    /**
     * @see #repPhoneNumber
     * @param repPhoneNumber
     */
    public void setRepPhoneNumber(String repPhoneNumber)
    {
        this.repPhoneNumber = repPhoneNumber;
    }

    /**
     * @see #billMethodCT
     * @return
     */
    public String getBillMethodCT()
    {
        return billMethodCT;
    }

    /**
     * @see #billMethodCT
     * @param billMethodCT
     */
    public void setBillMethodCT(String billMethodCT)
    {
        this.billMethodCT = billMethodCT;
    }

    /**
     * @see #skipMonthStart1CT
     * @return
     */
    public String getSkipMonthStart1CT()
    {
        return skipMonthStart1CT;
    }

    /**
     * @see #skipMonthStart1CT
     * @param skipMonthStart1CT
     */
    public void setSkipMonthStart1CT(String skipMonthStart1CT)
    {
        this.skipMonthStart1CT = skipMonthStart1CT;
    }

    /**
     * @see #skipNumberOfMonths1CT
     * @return
     */
    public String getSkipNumberOfMonths1CT()
    {
        return skipNumberOfMonths1CT;
    }

    /**
     * @see #skipNumberOfMonths1CT
     * @param skipNumberOfMonths1CT
     */
    public void setSkipNumberOfMonths1CT(String skipNumberOfMonths1CT)
    {
        this.skipNumberOfMonths1CT = skipNumberOfMonths1CT;
    }

    /**
     * @see #skipMonthStart2CT
     * @return
     */
    public String getSkipMonthStart2CT()
    {
        return skipMonthStart2CT;
    }

    /**
     * @see #skipMonthStart2CT
     * @param skipMonthStart2CT
     */
    public void setSkipMonthStart2CT(String skipMonthStart2CT)
    {
        this.skipMonthStart2CT = skipMonthStart2CT;
    }

    /**
     * @see #skipNumberOfMonths2CT
     * @return
     */
    public String getSkipNumberOfMonths2CT()
    {
        return skipNumberOfMonths2CT;
    }

    /**
     * @see #skipNumberOfMonths2CT
     * @param skipNumberOfMonths2CT
     */
    public void setSkipNumberOfMonths2CT(String skipNumberOfMonths2CT)
    {
        this.skipNumberOfMonths2CT = skipNumberOfMonths2CT;
    }

    /**
     * @see #skipMonthStart3CT
     * @return
     */
    public String getSkipMonthStart3CT()
    {
        return skipMonthStart3CT;
    }

    /**
     * @see #skipMonthStart3CT
     * @param skipMonthStart3CT
     */
    public void setSkipMonthStart3CT(String skipMonthStart3CT)
    {
        this.skipMonthStart3CT = skipMonthStart3CT;
    }

    /**
     * @see #skipNumberOfMonths3CT
     * @return
     */
    public String getSkipNumberOfMonths3CT()
    {
        return skipNumberOfMonths3CT;
    }

    /**
     * @see #skipNumberOfMonths3CT
     * @param skipNumberOfMonths3CT
     */
    public void setSkipNumberOfMonths3CT(String skipNumberOfMonths3CT)
    {
        this.skipNumberOfMonths3CT = skipNumberOfMonths3CT;
    }

    /**
     * @see #nextBillExtractDate
     * @return
     */
    public EDITDate getNextBillExtractDate()
    {
        return nextBillExtractDate;
    }

    /**
     * @see #nextBillExtractDate
     * @param nextBillExtractDate
     */
    public void setNextBillExtractDate(EDITDate nextBillExtractDate)
    {
        this.nextBillExtractDate = nextBillExtractDate;
    }

    /**
     * @see #nextBillDueDate
     * @return
     */
    public EDITDate getNextBillDueDate()
    {
        return nextBillDueDate;
    }

    /**
     * @see #nextBillDueDate
     * @param nextBillDueDate
     */
    public void setNextBillDueDate(EDITDate nextBillDueDate)
    {
        this.nextBillDueDate = nextBillDueDate;
    }

    /**
     * @see #lastBillDueDate
     * @return
     */
    public EDITDate getLastBillDueDate()
    {
        return lastBillDueDate;
    }

    /**
     * @see #lastBillDueDate
     * @param lastBillDueDate
     */
    public void setLastBillDueDate(EDITDate lastBillDueDate)
    {
        this.lastBillDueDate = lastBillDueDate;
    }

    /**
     * @see #firstDeductionDate
     * @return
     */
    public EDITDate getFirstDeductionDate()
    {
        return firstDeductionDate;
    }

    /**
     * @see #firstDeductionDate
     * @param firstDeductionDate
     */
    public void setFirstDeductionDate(EDITDate firstDeductionDate)
    {
        this.firstDeductionDate = firstDeductionDate;
    }

    /**
     * @see #lastDeductionDate
     * @return
     */
    public EDITDate getLastDeductionDate()
    {
        return lastDeductionDate;
    }

    /**
     * @see #lastDeductionDate
     * @param lastDeductionDate
     */
    public void setLastDeductionDate(EDITDate lastDeductionDate)
    {
        this.lastDeductionDate = lastDeductionDate;
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIFirstDeductionDate()
    {
        String date = null;

        if (getFirstDeductionDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getFirstDeductionDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIFirstDeductionDate(String uiFirstDeductionDate)
    {
        if (uiFirstDeductionDate != null)
        {
            setFirstDeductionDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiFirstDeductionDate));
        }
    }

    /**
     * @see #deductionFrequencyCT
     * @return
     */
    public String getDeductionFrequencyCT()
    {
        return deductionFrequencyCT;
    }

    /**
     * @see #deductionFrequencyCT
     * @param deductionFrequencyCT
     */
    public void setDeductionFrequencyCT(String deductionFrequencyCT)
    {
        this.deductionFrequencyCT = deductionFrequencyCT;
    }
    
    /**
     * @see #requiredPremiumAmount
     * @param requiredPremiumAmount
     */
    public void setRequiredPremiumAmount(EDITBigDecimal requiredPremiumAmount)
    {
        this.requiredPremiumAmount = requiredPremiumAmount;
    }

    /**
     * @sett #requiredPremiumAmount
     * @return
     */
    public EDITBigDecimal getRequiredPremiumAmount()
    {
        return this.requiredPremiumAmount;
    }

    /**
     * @sett transitionPeriodEndDate
     * @param transitionPeriodEndDate
     */
    public void setTransitionPeriodEndDate(EDITDate transitionPeriodEndDate)
    {
        this.transitionPeriodEndDate = transitionPeriodEndDate;
    }

    /**
     * @see #billChangeStartDate
     * @return EDITDate
     */
    public EDITDate getBillChangeStartDate()
    {
        return billChangeStartDate;
    }

    /**
     * @see #billChangeStartDate
     * @param billChangeStartDate
     */
    public void setBillChangeStartDate(EDITDate billChangeStartDate)
    {
        this.billChangeStartDate = billChangeStartDate;
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIBillChangeStartDate()
    {
        String date = null;

        if (getBillChangeStartDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getBillChangeStartDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIBillChangeStartDate(String uiBillChangeStartDate)
    {
        if (uiBillChangeStartDate != null)
        {
            setBillChangeStartDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiBillChangeStartDate));
        }
    }

    /**
     * @see #varMonthDedChangeStartDate
     * @return EDITDate
     */
    public EDITDate getVarMonthDedChangeStartDate()
    {
        return varMonthDedChangeStartDate;
    }

    /**
     * @see #varMonthDedChangeStartDate
     * @param varMonthDedChangeStartDate
     */
    public void setVarMonthDedChangeStartDate(EDITDate varMonthDedChangeStartDate)
    {
        this.varMonthDedChangeStartDate = varMonthDedChangeStartDate;
    }


    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIVarMonthDedChangeStartDate()
    {
        String date = null;

        if (getVarMonthDedChangeStartDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getVarMonthDedChangeStartDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIVarMonthDedChangeStartDate(String uiVarMonthDedChangeStartDate)
    {
        if (uiVarMonthDedChangeStartDate != null)
        {
            setVarMonthDedChangeStartDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiVarMonthDedChangeStartDate));
        }
    }

    /**
     * @see #changeEffectiveDate
     * @return EDITDate
     */
    public EDITDate getChangeEffectiveDate()
    {
        return changeEffectiveDate;
    }

    /**
     * @see #changeEffectiveDate
     * @param changeEffectiveDate
     */
    public void setChangeEffectiveDate(EDITDate changeEffectiveDate)
    {
        this.changeEffectiveDate = changeEffectiveDate;
    }

    /**
     * @see #lastPremiumChangeStartDate
     * @return EDITDate
     */
    public EDITDate getLastPremiumChangeStartDate()
    {
        return lastPremiumChangeStartDate;
    }

    /**
     * @see #lastPremiumChangeStartDate
     * @param lastPremiumChangeStartDate
     */
    public void setLastPremiumChangeStartDate(EDITDate lastPremiumChangeStartDate)
    {
        this.lastPremiumChangeStartDate = lastPremiumChangeStartDate;
    }

    /**
     * Convenience (bean) method to support UI's need for dd/mm/yyyy format.
     * @return
     */
    public String getUIChangeEffectiveDate()
    {
        String date = null;

        if (getChangeEffectiveDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getChangeEffectiveDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIChangeEffectiveDate(String uiChangeEffectiveDate)
    {
        if (uiChangeEffectiveDate != null)
        {
            setChangeEffectiveDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiChangeEffectiveDate));
        }
    }

    /**
     * @see #transitionPeriodEndDate
     * @return
     */
    public EDITDate getTransitionPeriodEndDate()
    {
        return this.transitionPeriodEndDate;
    }

    /**
     * Getter.
     * @return
     */
    public int getEFTDraftDay()
    {
        return eftDraftDay;
    }

    /**
     * Setter.
     * @param eftDraftDay
     */
    public void setEFTDraftDay(int eftDraftDay)
    {
        this.eftDraftDay = eftDraftDay;
    }

    /**
     * Getter.
     * @return
     */
    public String getEFTDraftDayStartMonthCT()
    {
        return eftDraftDayStartMonthCT;
    }

    /**
     * Setter.
     * @param eftDraftDayStartMonthCT
     */
    public void setEFTDraftDayStartMonthCT(String eftDraftDayStartMonthCT)
    {
        this.eftDraftDayStartMonthCT = eftDraftDayStartMonthCT;
    }

    /**
     * @see #billGroups
     * @return
     */
    public Set<BillGroup> getBillGroups()
    {
        return billGroups;
    }

    /**
     * @see #billGroups
     * @param billGroups
     */
    public void setBillGroups(Set<BillGroup> billGroups)
    {
        this.billGroups = billGroups;
    }

    public void addBillGroup(BillGroup billGroup)
    {
        this.billGroups.add(billGroup);

        billGroup.setBillSchedule(this);
    }

    public void removeBillGroup(BillGroup billGroup)
    {
        this.billGroups.remove(billGroup);

        billGroup.setBillSchedule(null);
    }

     /**
      * Get a single BillGroup
      * @return
      */
     public BillGroup getBillGroup()
     {
         BillGroup billGroup =getBillGroups().isEmpty() ? null : (BillGroup) getBillGroups().iterator().next();

         return billGroup;
     }   

    /**
     * @see #segments
     * @return
     */
    public Set<Segment> getSegments()
    {
        return segments;
    }

    /**
     * @see #segments
     * @param segments
     */
    public void setSegments(Set<Segment> segments)
    {
        this.segments = segments;
    }

    public void addSegment(Segment segment)
    {
        this.segments.add(segment);

        segment.setBillSchedule(this);
    }

    public void removeSegment(Segment segment)
    {
        this.segments.remove(segment);

        segment.setBillSchedule(null);
    }

    /**
     * @see #contractGroups
     * @return
     */
    public Set<ContractGroup> getContractGroups()
    {
        return contractGroups;
    }

    /**
     * @see #contractGroups
     * @param contractGroups
     */
    public void setContractGroups(Set<ContractGroup> contractGroups)
    {
        this.contractGroups = contractGroups;
    }

    public void addContractGroup(ContractGroup contractGroup)
    {
        this.contractGroups.add(contractGroup);

        contractGroup.setBillSchedule(this);

        SessionHelper.saveOrUpdate(contractGroup, BillSchedule.DATABASE);
    }

    public void removeContractGroup(ContractGroup contractGroup)
    {
        this.contractGroups.remove(contractGroup);

        contractGroup.setBillSchedule(null);
    }

    public String getDatabase()
    {
        return BillSchedule.DATABASE;
    }

    public static void calculateAndSetBillScheduleDates(BillSchedule billSchedule, int leadDays, Set<PayrollDeductionSchedule> payDedScheds)
    {
        EDITDate lastPRDExtractDate = null;
        Iterator it = payDedScheds.iterator();
        if (it.hasNext())
        {
            PayrollDeductionSchedule payDedSched = (PayrollDeductionSchedule) it.next();
            lastPRDExtractDate = payDedSched.getLastPRDExtractDate();
        }

        if (billSchedule.getLastBillDueDate() == null && lastPRDExtractDate == null)
        {
            billSchedule.setNextBillDueDate(billSchedule.getFirstBillDueDate());
            billSchedule.setNextBillExtractDate(billSchedule.getNextBillDueDate().subtractDays(leadDays));

            it = payDedScheds.iterator();
            if (it.hasNext())
            {
                PayrollDeductionSchedule payDedSched = (PayrollDeductionSchedule) it.next();
                PayrollDeductionSchedule.calculateAndSetPRDExtractDates(payDedSched, billSchedule.getBillSchedulePK());
            }
        }
    }

    /**
     * Looks up the AreaValue for a billing change based on the grouping, field, productStructure, and issueState.
     *
     * @return  AreaValue matching the criteria
     */
    public int getValueForBillingLeadDays(BillSchedule billSchedule)
    {
        int leadDays = 0;

        if (billSchedule.getLeadDaysOR() > 0)
        {
            leadDays = billSchedule.getLeadDaysOR();
        }
        else
        {
            ProductStructure productStructure = null;

            if (billSchedule.getBillMethodCT().equalsIgnoreCase(BillSchedule.BILL_METHOD_LISTBILL))
            {
                productStructure = ProductStructure.findBy_CompanyName("Case")[0];
            }
            else
            {
                Segment[] segment = Segment.findBy_BillScheduleFK(billSchedule.getBillSchedulePK());
                if (segment.length > 0)
                {
                    productStructure = segment[0].getProductStructure();
                }
            }

            if (productStructure != null)
            {
                AreaValue[] areaValues = null;
                if (billSchedule.getBillMethodCT().equalsIgnoreCase(BillSchedule.BILL_METHOD_EFT))
                {
                    areaValues = AreaValue.findBy_ProductStructurePK_Grouping_Field(productStructure.getPK(),BillSchedule.AREA_GROUPING_BILLING, BillSchedule.AREA_FIELD_EFTLEADDAYS);
                }
                else
                {
                    areaValues = AreaValue.findBy_ProductStructurePK_Grouping_Field(productStructure.getPK(),BillSchedule.AREA_GROUPING_BILLING, BillSchedule.AREA_FIELD_LEADDAYS);
                }

                if (areaValues != null)
                {
                    leadDays = Integer.parseInt(areaValues[0].getAreaValue());
                }
            }
        }

        return leadDays;
    }

    /**
     * Finder.
     * @param billSchedulePK
     * @return
     */
    public static BillSchedule findBy_BillSchedulePK(Long billSchedulePK)
    {
        return (BillSchedule) SessionHelper.get(BillSchedule.class, billSchedulePK, BillSchedule.DATABASE);
    }
    
    /**
     * Finder.
     * @param segmentPK
     * @return
     */
    public static BillSchedule findBy_SegmentPK(Long segmentPK)
    {
    	Segment segment = (Segment) SessionHelper.get(Segment.class, segmentPK, BillSchedule.DATABASE);
    	
    	if (segment != null && segment.getBillScheduleFK() != null) {
    		return (BillSchedule) SessionHelper.get(BillSchedule.class, segment.getBillScheduleFK(), BillSchedule.DATABASE);
    	} else {
    		return null;
    	}
    }


    /**
     * Calculated the number of dedections within the month of the current BillSchedule.LastDeductionDate
     * based on the specified prdFrequency. Additionally, it calculated the "next" LastDeductionDate
     * as determined by the current BillSchedule.LastDeductionDate and the specified prdFrequency.
     * @param prdFrequency
     * @return
     */
    public PRDDeduction calculateNumberOfDeductions(BillSchedule billSchedule)
    {
        EDITDate lastDeductionDate = billSchedule.getLastDeductionDate();
        if (lastDeductionDate == null)
        {
            lastDeductionDate = billSchedule.getFirstDeductionDate();
        }

        PRDDeduction prdDeduction = new PRDDeduction(lastDeductionDate, Integer.parseInt(billSchedule.getDeductionFrequencyCT()));

        if (lastDeductionDate != null)
        {
            prdDeduction.calculate();
        }

        return prdDeduction;
    }

    public void createListBillExtract(EDITDate extractDate)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_BILL_EXTRACT).tagBatchStart(Batch.BATCH_JOB_CREATE_BILL_EXTRACT, "Bill Extract");

        Long[] billSchedulePKs = getBillSchedulePKsToProcess(extractDate);

        int billCount = processBills(billSchedulePKs, extractDate);

        if (billCount > 0)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_BILL_EXTRACT).updateSuccess();
        }

        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_BILL_EXTRACT).tagBatchStop();
    }

    public Long[] getBillSchedulePKsToProcess(EDITDate extractDate)
    {
        BillSchedule[] billSchedules = null;

        String hql = " select bs from BillSchedule bs" +
                     " where bs.NextBillExtractDate <= :extractDate " +
                     " and bs.TerminationDate > :extractDate ";

        EDITMap params = new EDITMap("extractDate", extractDate);

        List<BillSchedule> results = SessionHelper.executeHQL(hql, params, BillSchedule.DATABASE);

        List<Long> billSchedulePKs = new ArrayList<Long>();

        for (int i = 0; i < results.size(); i++) 
        {
            BillSchedule billSchedule = results.get(i);
            
            billSchedulePKs.add(billSchedule.getBillSchedulePK());
        }

        return (Long[]) billSchedulePKs.toArray(new Long[billSchedulePKs.size()]);
    }

    public static Long[] findByCompany(String companyName)
    {
        String hql = null;
        EDITMap params = new EDITMap();
        if (!companyName.equalsIgnoreCase("All"))
        {
            hql = " select bs.BillSchedulePK from BillSchedule bs" +
                  " where bs.BillingCompanyCT = :companyName";

            params.put("companyName", companyName);
        }
        else
        {
            hql = "select bs.BillSchedulePK from BillSchedule bs";
        }

         List results = SessionHelper.executeHQL(hql, params, BillSchedule.DATABASE);

        return (Long[]) results.toArray(new Long[results.size()]);       
        
    }
    
    public static Long[] findByCompany(String companyName, EDITDate date)
    {
        String hql = null;
        EDITMap params = new EDITMap();
        if (!companyName.equalsIgnoreCase("All"))
        {
            hql = " select bs.BillSchedulePK from BillSchedule bs" +
                  " where bs.BillingCompanyCT = :companyName" +
                  " and bs.TerminationDate >= :date ";

            params.put("companyName", companyName);
            params.put("date", date);
        }
        else
        {
            hql = "select bs.BillSchedulePK from BillSchedule bs" +
            	  " and bs.TerminationDate >= :date ";
            
            params.put("date", date);
        }

         List results = SessionHelper.executeHQL(hql, params, BillSchedule.DATABASE);

        return (Long[]) results.toArray(new Long[results.size()]);       
    }
   
    public static BillSchedule[] findByExtractDate_Role_Source(EDITDate extractDate, String companyName)
    {
        BillSchedule[] billSchedules = null;
        String hql = " select distinct bs from BillSchedule bs" +
                     " join fetch bs.Segments segment" +
                     " join fetch segment.Lifes life" +
                     " join fetch segment.ContractClients contractClient" +
                     " join fetch contractClient.ClientRole clientRole" +
                     " join fetch clientRole.ClientDetail clientDetail" +
                     " join fetch clientDetail.Preferences preference" +
                     " join fetch bs.BillGroups billGroup" +
                     " join fetch billGroup.Bills bill" +
                     " where billGroup.ExtractDate  <= :extractDate" +
                     " and (billGroup.DueDate >= life.PaidToDate)" +
                     " and (bill.PaidAmount = :zero or bill.PaidAmount is null)" +
                     " and bs.BillingCompanyCT = :companyName" +
                     " and bs.BillMethodCT = :eftMethod" +
                     " and clientRole.RoleTypeCT = :payorRole" +
                     " and preference.DisbursementSourceCT = :disbursementSource" +
                     " and preference.PreferenceTypeCT = :preferenceType";

        EDITMap params = new EDITMap("extractDate", extractDate);
        params.put("zero", new EDITBigDecimal("0"));
        params.put("companyName", companyName);
        params.put("eftMethod", "EFT");
        params.put("payorRole", "POR");
        params.put("disbursementSource", "eft");
        params.put("preferenceType", "billing");

        List<BillSchedule> results = SessionHelper.executeHQL(hql, params, BillSchedule.DATABASE);

        if (!results.isEmpty())
        {
            billSchedules = results.toArray(new BillSchedule[results.size()]);
        }

        return billSchedules;
    }


   
    public static BillSchedule[] findByExtractDate_Role_Source_For_Group_EFT(EDITDate extractDate, String companyName)
    {
        BillSchedule[] billSchedules = null;
        String hql = " select distinct bs from BillSchedule bs" +
                     " join fetch bs.Segments segment" +
                     " join fetch segment.Lifes life" +
                     " join fetch segment.ContractClients contractClient" +
                     " join fetch contractClient.ClientRole clientRole" +
                     " join fetch clientRole.ClientDetail clientDetail" +
                     " join fetch clientDetail.Preferences preference" +
                    // " join fetch bs.BillGroups billGroup" +
                    // " join fetch billGroup.Bills bill" +
                    // " where billGroup.ExtractDate  <= :extractDate" +
                    // " and (billGroup.DueDate >= life.PaidToDate)" +
//                     " and (bill.PaidAmount = :zero or bill.PaidAmount is null)" +
  //                   " where (bill.PaidAmount = :zero or bill.PaidAmount is null)" +
                     " where bs.BillingCompanyCT = :companyName" +
                     " and bs.BillMethodCT = :eftMethod" +
                     " and bs.TerminationDate > :extractDate " +
                     " and clientRole.RoleTypeCT = :payorRole" +
                     " and preference.DisbursementSourceCT = :disbursementSource" +
                     " and preference.PreferenceTypeCT = :preferenceType" +
                     " and segment.SegmentStatusCT in ('Active', 'Submitted', 'IssuePendingPremium')";

//        EDITMap params = new EDITMap("extractDate", extractDate);
        EDITMap params = new EDITMap();
        //params.put("zero", new EDITBigDecimal("0"));
        params.put("companyName", companyName);
        params.put("eftMethod", "EFT");
        params.put("extractDate", extractDate);
        params.put("payorRole", "POR");
        params.put("disbursementSource", "eft");
        params.put("preferenceType", "billing");

        List<BillSchedule> results = SessionHelper.executeHQL(hql, params, BillSchedule.DATABASE);

        if (!results.isEmpty())
        {
            billSchedules = results.toArray(new BillSchedule[results.size()]);
        }

        return billSchedules;
    }


    private int processBills(Long[] billSchedulePKs, EDITDate extractDate)
    {
        int billCount = 0;

        String jobTime = new EDITDateTime().getFormattedTime();
        EDITDateTime stagingDate = new EDITDateTime(extractDate.toString() + " " + jobTime);
        String billingMode = null;
        String numberOfDeductions = null;
        EDITDate lastDeductionDate = null;
        EDITBigDecimal billAmount = new EDITBigDecimal();

        List billGroupList = null;
        List billList = null;

        boolean shouldProcessBill = true;

        for (int i = 0; i < billSchedulePKs.length; i++)
        {
            BillSchedule billSchedule = BillSchedule.findBy_BillSchedulePK(billSchedulePKs[i]);

            if (billSchedule.getNextBillDueDate() != null)
            {
                SessionHelper.beginTransaction(BillSchedule.DATABASE);

                billingMode = billSchedule.getBillingModeCT();

                shouldProcessBill = billSchedule.isValidNextBillDueDate(billSchedule.getNextBillDueDate());

                processSegment = false;
                if (shouldProcessBill)
                {
                    billList = new ArrayList();
                    billGroupList = new ArrayList();

                    if (billSchedule.getBillingModeCT().equalsIgnoreCase(VARIABLE_MONTHLY))
                    {
                        PRDDeduction prdDeduction = calculateNumberOfDeductions(billSchedule);
                        numberOfDeductions = prdDeduction.getNumberOfDeductions() + "";
                        lastDeductionDate = prdDeduction.getNextLastDeductionDate();
                        billSchedule.setLastDeductionDate(lastDeductionDate);
                    }

                    BillGroup billGroup = new BillGroup();
                    billGroup.setExtractDate(billSchedule.getNextBillExtractDate());
                    billGroup.setDueDate(billSchedule.getNextBillDueDate());
                    billGroup.setTotalBilledAmount(new EDITBigDecimal());
                    billGroup.setTotalPaidAmount(new EDITBigDecimal());
                    billGroup.setBillSchedule(billSchedule);
                    // deck

                    boolean billIsMultipleMonths = checkBillingMode(billingMode);

                    Set<Segment> segments = billSchedule.getSegments();

                    Iterator it2 = segments.iterator();

                    while (it2.hasNext())
                    {
                        Segment segment = (Segment) it2.next();
                        /*if (segment.getOptionCodeCT().equals("UL")) {
                        	System.out.println("UL Policy");
                            System.out.println("segment: " + segment.getSegmentPK() + " " + segment.getContractNumber());
                        } else {

                            // deck:  We do not want Lapse contracts to produce bills in Staging.
                            // add more status.

                            System.out.println("segment: " + segment.getSegmentPK() + " " + segment.getContractNumber());
                            if (segment.getContractNumber().equals("VC00001500")) {
                            	System.out.println("Stop Here");
                            }
                        }*/
                        if ((segment.getSegmentFK() == null) && 
                        		!segment.getStatus().equals("Death") &&
                        		!segment.getStatus().equals("DeathPending") &&
                        		!segment.getStatus().equals("DeclinedMed") &&
                        		!segment.getStatus().equals("DeclineElig") &&
                        		!segment.getStatus().equals("DeclineReq") &&
                        		!segment.getStatus().equals("Frozen") &&
                        		!segment.getStatus().equals("FrozenPending") &&
                        		!segment.getStatus().equals("LTC") &&
                        		!segment.getStatus().equals("NotTaken") &&
                        		!segment.getStatus().equals("PayorPremiumWaiver") &&
                        		!segment.getStatus().equals("Postponed") &&
                        		!segment.getStatus().equals("Surrendered") &&
                        		!segment.getStatus().equals("Terminated") &&
                        		!segment.getStatus().equals("Expired") &&
                        		!segment.getStatus().equals("Waiver") &&
                        		!segment.getStatus().equals("Withdrawn") &&
                        		!segment.getStatus().equals("ReducedPaidUp") &&
                        		!segment.getStatus().equals("PUTerm") &&
                        		!segment.getStatus().equals("Matured") &&
                        		!segment.getStatus().equals("Incomplete") &&
                        		!segment.getStatus().equals("SubmitPend") &&
                        		!segment.getStatus().equals("EOB") && 
                        		!segment.getStatus().equals("Lapse"))
                        {
                            ContractGroup groupContractGroup = segment.getContractGroup();
                            ContractGroup caseContractGroup = groupContractGroup.getContractGroup();
                            
                            // For UL
                            boolean isUL = false;
                            boolean isInGracePeriod = false;
                            if (segment.getProductStructure().getBusinessContractName().equals("UL")) {
                                isInGracePeriod = isInGracePeriod(billSchedule, segment);
                                isUL = true;	
                       			/*System.out.println("===========================================");
                                System.out.println("segemntPK: " + segment.getSegmentPK());
                                System.out.println("contractNumber: " + segment.getContractNumber());
                       			System.out.println("===========================================");*/
                            }

                            processSegment = true;

                            try
                            {
                            	if (isUL && isInGracePeriod) {
                            		//if (isInGracePeriod) {
                            		    billAmount = billSchedule.getRequiredPremiumAmount(); 	
                            		//} 
                            		    /*else {
                            			System.out.println("BillSchedule: " + billSchedule.getBillSchedulePK());
                            			System.out.println("nextBillDueDate: " + billSchedule.getNextBillDueDate());
                            			System.out.println("nextBillExtractDate: " + billSchedule.getNextBillExtractDate());
                            			System.out.println("nextBillingMode: " + billSchedule.getBillingModeCT());
                            			System.out.println("nextBillingType: " + billSchedule.getBillTypeCT());
                            			System.out.println("nextBillingMethod: " + billSchedule.getBillMethodCT());
                            			PremiumDue[] pds = PremiumDue.findBySegmentPK(segment.getSegmentPK());
                                        System.out.println("");
                                        System.out.println("PDs");
                            			for (int j = 0; j < pds.length; j++) {
                            				System.out.println("effectDate: " + pds[i].getEffectiveDate());
                            				System.out.println("billAmount: " + pds[i].getBillAmount());
                            				System.out.println("pendingExtractIndicator: " + pds[i].getPendingExtractIndicator());
                            				
                            			}
                            			System.out.println("End PDs");
                            			PremiumDue pd1 = PremiumDue.findBySegment_LatestEffectiveDate(segment);

                            			
                            		}*/
                            		
                            	} else if (billIsMultipleMonths) {

                                    billAmount = getBillAmtForMultipleMonthBill(segment, billSchedule);

                                    if (billSchedule.getBillTypeCT().equalsIgnoreCase(BILL_TYPE_GROUP) ||
                                        (billSchedule.getBillTypeCT().equalsIgnoreCase(BILL_TYPE_INDIVUAL) && billAmount.isGT("0")))
                                    {
                                        Bill bill = createBill(segment, extractDate, billSchedule, billGroup, billAmount);

                                        if (!billSchedule.getBillMethodCT().equalsIgnoreCase(BillSchedule.BILL_METHOD_EFT))
                                        {
                                            billList.add(bill);
                                        }
                                    }
                                }
                                else
                                {
                                    PremiumDue[] premiumDues = checkContractEffDate(segment, billSchedule);

                                    if (premiumDues.length > 0)
                                    {
                                        if (premiumDues[0].getDeductionAmount().isGT("0") ||
                                            premiumDues[0].getBillAmount().isGT("0"))
                                        {
//                                            processSegment = true;
                                            if (!billSchedule.getBillingModeCT().equalsIgnoreCase(VARIABLE_MONTHLY))
                                            {
                                                numberOfDeductions = premiumDues[0].getNumberOfDeductions() + "";
                                            }

                                            Bill bill = createBill(numberOfDeductions, premiumDues[0], segment, extractDate, billSchedule, billGroup);

                                            billAmount = bill.getBilledAmount();
                                            
                                            //Only paper bills will go to staging
                                            if (!billSchedule.getBillMethodCT().equalsIgnoreCase(BillSchedule.BILL_METHOD_EFT))
                                            {
                                                billList.add(bill);
                                            }
                                        }
                                    }
                                }
                            }

                            catch (Exception e)
                            {
                                logError(e, billSchedule, caseContractGroup, groupContractGroup, segment);
                            }
                        }
                    }

                    if (processSegment)
                    {
                        billGroupList.add(billGroup);
                        billSchedule.addBillGroup(billGroup);
                    }
                }

                if (shouldProcessBill)
                {
                    billSchedule.setLastBillDueDate(billSchedule.getNextBillDueDate());
                }

                EDITDate nextBillDueDate = billSchedule.getNextBillDueDate();
                EDITDate nextBillExtractDate = billSchedule.getNextBillExtractDate();

                billingMode = getMode(billingMode);
                nextBillDueDate = nextBillDueDate.addMode(billingMode);

                if (billSchedule.getBillingModeCT().equalsIgnoreCase(VARIABLE_MONTHLY))
                {
                    nextBillDueDate = EDITDate.getFirstWeekdayOfMonth(nextBillDueDate.getYear(), nextBillDueDate.getMonth(), billSchedule.getWeekDayCT());
                }

                billSchedule.setNextBillDueDate(nextBillDueDate);

                int leadDays = leadDays = getValueForBillingLeadDays(billSchedule);

                if (billSchedule.getBillMethodCT().equalsIgnoreCase(BillSchedule.BILL_METHOD_EFT))
                {
                    BusinessCalendar businessCalendar = new BusinessCalendar();
                    if (leadDays == 0) {
                    	leadDays = 1;
                    }
                    BusinessDay businessDay = businessCalendar.findPreviousBusinessDay(nextBillDueDate, leadDays);

                    nextBillExtractDate = businessDay.getBusinessDate();
                }
                else
                {
                    nextBillExtractDate = nextBillDueDate.subtractDays(leadDays);
                }

                billSchedule.setNextBillExtractDate(nextBillExtractDate);

                billSchedule.hSave();

                SessionHelper.commitTransaction(BillSchedule.DATABASE);

                if (billList != null && !billList.isEmpty())
                {
                    billCount += billList.size();
                    
                    BillStaging billStaging = new BillStaging(stagingDate);

                    billStaging.stageTables(billSchedule, billGroupList);
                    
                    if (billSchedule.getBillMethodCT().equalsIgnoreCase(BillSchedule.BILL_METHOD_EFT) ||
                        billSchedule.getBillMethodCT().equalsIgnoreCase(BillSchedule.BILL_METHOD_DIRECT_BILL))
                    {
                    	if (billAmount != null && billAmount.isGT(new EDITBigDecimal())) 
                    	{
                    		EDITBigDecimal newRequiredPremium = billSchedule.getRequiredPremiumAmount().addEditBigDecimal(billAmount);
                    	
                    		SessionHelper.beginTransaction(BillSchedule.DATABASE);
                    		
                    		billSchedule.setRequiredPremiumAmount(newRequiredPremium);
                    		
                    		billSchedule.hSave();

                            SessionHelper.commitTransaction(BillSchedule.DATABASE);
                    	}                        
                    }
                }

                SessionHelper.clearSessions();
            }
        }

        return billCount;
    }
    
    private boolean isInGracePeriod(BillSchedule billSchedule, Segment segment) {
    	if ((billSchedule.getBillingModeCT().equals(BILL_METHOD_DIRECT_BILL)) && 
    			(segment.get_Life().getLapsePendingDate().before(new EDITDate()))) {
    	    return true;	
    	}
    	return false;
    }

    private boolean checkBillingMode(String billingMode)
    {
        boolean billIsMultipleMonths = false;
        billingMode = getMode(billingMode);
        if (billingMode.equalsIgnoreCase(EDITDate.ANNUAL_MODE) ||
            billingMode.equalsIgnoreCase(EDITDate.BIMONTHLY_MODE) ||
            billingMode.equalsIgnoreCase(EDITDate.QUARTERLY_MODE) ||
            billingMode.equalsIgnoreCase(EDITDate.SEMI_ANNUAL_MODE))
        {
            billIsMultipleMonths = true;
        }

        return billIsMultipleMonths;
    }

    private EDITBigDecimal getBillAmtForMultipleMonthBill(Segment segment, BillSchedule billSchedule)
    {
        EDITBigDecimal billAmount = new EDITBigDecimal();

    	if (segment.getSegmentNameCT() != null && segment.getSegmentNameCT().equalsIgnoreCase(Segment.SEGMENTNAMECT_UL)) {
    		    		
	        EDITDate nextBillDueDate = billSchedule.getNextBillDueDate();
            PremiumDue[] premiumDues = checkContractEffDate(segment, nextBillDueDate);

            if (premiumDues != null && premiumDues.length > 0)
            {
            	billAmount = premiumDues[0].getBillAmount();            
            }
    		
    	} else {
	
	        EDITDate nextBillDueDate = billSchedule.getNextBillDueDate();
	        String billingMode = getMode(billSchedule.getBillingModeCT());
	        EDITDate endingBillDate = nextBillDueDate.addMode(billingMode);
		
	        while (nextBillDueDate.before(endingBillDate))
	        {
	            PremiumDue[] premiumDues = checkContractEffDate(segment, nextBillDueDate);
	
	            if (premiumDues.length > 0)
	            {
	                EDITBigDecimal deductionAmountForPremiumDue = premiumDues[0].getDeductionAmount();
	                EDITBigDecimal billAmountForPremiumDue = premiumDues[0].getBillAmount();
	
	                if (premiumDues[0].getDeductionAmountOverride().isGT("0"))
	                {
	                    deductionAmountForPremiumDue = premiumDues[0].getDeductionAmountOverride();
	                }
	
	                if (premiumDues[0].getBillAmountOverride().isGT("0"))
	                {
	                    billAmountForPremiumDue = premiumDues[0].getBillAmountOverride();
	                }
	
	                if (deductionAmountForPremiumDue.isGT("0") ||
	                    billAmountForPremiumDue.isGT("0"))
	                {
	                    processSegment = true;
	
	                    Set<CommissionPhase> commissionPhases = premiumDues[0].getCommissionPhases();
	                    Iterator it = commissionPhases.iterator();
	                    while (it.hasNext())
	                    {
	                        CommissionPhase commissionPhase = (CommissionPhase) it.next();
	                        billAmount = billAmount.addEditBigDecimal(commissionPhase.getExpectedMonthlyPremium());
	                    }
	
	                    //add adjustment to the bill amount for the proper pending extract indicator
	                    String pendingExtractIndicator = premiumDues[0].getPendingExtractIndicator();
	                    if (pendingExtractIndicator.equalsIgnoreCase(PremiumDue.PENDING_EXTRACT_U) ||
	                        pendingExtractIndicator.equalsIgnoreCase(PremiumDue.PENDING_EXTRACT_M))
	                    {
	//                        billAmount = billAmount.addEditBigDecimal(premiumDues[0].getAdjustmentAmount());
	
	                        if (pendingExtractIndicator.equalsIgnoreCase(PremiumDue.PENDING_EXTRACT_M))
	                        {
	                            premiumDues[0].setPendingExtractIndicator(PremiumDue.PENDING_EXTRACT_A);
	                        }
	                        else
	                        {
	                            premiumDues[0].setPendingExtractIndicator(PremiumDue.PENDING_EXTRACT_B);
	                        }
	
	                        SessionHelper.saveOrUpdate(premiumDues[0], BillSchedule.DATABASE);
	                    }
	                }
	            }
	
	            nextBillDueDate = nextBillDueDate.addMonths(1);
	        }
    	}

        return billAmount;
    }

    /**
     * The qualifying PremiumDue records must have its effectiveDate <+ the date passed in, nextBillDueDAte.
     * At the same time if the month and year of the Premium Due effectiveDate match the month and year of
     * the date passed must be picked up.  Using the last day of the month for the nextBillDueDate satifies
     * both these requirement.
     * @param segment
     * @param nextBillDueDate
     * @return
     */
    private PremiumDue[] checkContractEffDate(Segment segment, BillSchedule billSchedule)
    {
        EDITDate endOfMonthDate = calculatedEndOFMonthDate(billSchedule);

        PremiumDue[] premiumDues = PremiumDue.getPremiumDueForBill(segment, endOfMonthDate);

        return premiumDues;
    }

    private PremiumDue[] checkContractEffDate(Segment segment, EDITDate nextBillDueDate)
    {
        String billMode = getMode(BillSchedule.MONTHLY);

        EDITDate endOfMonthDate = (nextBillDueDate.addMode(billMode)).subtractDays(1);

        PremiumDue[] premiumDues = PremiumDue.getPremiumDueForBill(segment, endOfMonthDate);

        return premiumDues;
    }

    public EDITDate calculatedEndOFMonthDate(BillSchedule billSchedule)
    {
        EDITDate nextBillDueDate = billSchedule.getNextBillDueDate();
        EDITDate endOfMonthDate = null;
        if (billSchedule.getBillingModeCT().equalsIgnoreCase(BillSchedule.VARIABLE_MONTHLY))
        {
            endOfMonthDate = nextBillDueDate;
        }
        else
        {
            String billMode = billSchedule.getBillingModeCT();
            billMode = getMode(billMode);

            endOfMonthDate = (nextBillDueDate.addMode(billMode)).subtractDays(1);
        }

        return endOfMonthDate;
    }


    public Bill createBill(String numberOfDeductions,
                               PremiumDue premiumDue,
                               Segment segment,
                               EDITDate extractDate,
                               BillSchedule billSchedule,
                               BillGroup billGroup) throws EDITCaseException
    {
        EDITBigDecimal billAmount = new EDITBigDecimal();

        EDITBigDecimal deductionAmountForPremiumDue = premiumDue.getDeductionAmount();

        if (premiumDue.getDeductionAmountOverride().isGT("0"))
        {
            deductionAmountForPremiumDue = premiumDue.getDeductionAmountOverride();
        }

        if (deductionAmountForPremiumDue.isGT("0"))
        {
            billAmount = deductionAmountForPremiumDue.multiplyEditBigDecimal(new EDITBigDecimal(numberOfDeductions)).round(2);
        }
        else
        {
            EDITBigDecimal billAmountForPremiumDue = premiumDue.getBillAmount();

            if (premiumDue.getBillAmountOverride().isGT("0"))
            {
                billAmountForPremiumDue = premiumDue.getBillAmountOverride();
            }

            billAmount = billAmountForPremiumDue;

            //add adjustment to the bill amount for the proper pending extract indicator
            String pendingExtractIndicator = premiumDue.getPendingExtractIndicator();
            if (pendingExtractIndicator.equalsIgnoreCase(PremiumDue.PENDING_EXTRACT_U) ||
                pendingExtractIndicator.equalsIgnoreCase(PremiumDue.PENDING_EXTRACT_M))
            {
                billAmount = billAmount.addEditBigDecimal(premiumDue.getAdjustmentAmount());

                if (pendingExtractIndicator.equalsIgnoreCase(PremiumDue.PENDING_EXTRACT_M))
                {
                    premiumDue.setPendingExtractIndicator(PremiumDue.PENDING_EXTRACT_A);
                }
                else
                {
                    premiumDue.setPendingExtractIndicator(PremiumDue.PENDING_EXTRACT_B);
                }

                SessionHelper.saveOrUpdate(premiumDue, BillSchedule.DATABASE);
            }
        }

        Bill bill = new Bill();
        bill.setBilledAmount(billAmount);
        bill.setPaidAmount(new EDITBigDecimal());

        segment.addBill(bill);      // attaches Segment to Bill

        billGroup.addBill(bill);    // attaches BillGroup to Bill and updates the BillGroup's totalBilledAmount

        return bill;
    }

    public Bill createBill(Segment segment, EDITDate extractDate, BillSchedule billSchedule, BillGroup billGroup, EDITBigDecimal billAmount) throws EDITCaseException
    {
        Bill bill = new Bill();
        bill.setBilledAmount(billAmount);
        bill.setPaidAmount(new EDITBigDecimal());

        segment.addBill(bill);      // attaches Segment to Bill

        billGroup.addBill(bill);    // attaches BillGroup to Bill and updates the BillGroup's totalBilledAmount

        return bill;
    }

    public String getMode(String billingMode)
    {
        String mode = null;

        if (billingMode.equalsIgnoreCase(ANNUAL))
        {
            mode = EDITDate.ANNUAL_MODE;
        }
        else if (billingMode.equalsIgnoreCase(SEMI_ANNUAL))
        {
            mode = EDITDate.SEMI_ANNUAL_MODE;
        }
        else if (billingMode.equalsIgnoreCase(QUARTERLY))
        {
            mode = EDITDate.QUARTERLY_MODE;
        }
        else if (billingMode.equalsIgnoreCase(MONTHLY) ||
                 billingMode.equalsIgnoreCase(VARIABLE_MONTHLY))
        {
            mode = EDITDate.MONTHLY_MODE;
        }
        else if (billingMode.equalsIgnoreCase(THIRTEENTHLY))
        {
            mode = EDITDate.THIRTEENTHLY_MODE;
        }

        return mode;
    }

    /**
     * Creates a BillSchedule for individual billing based on the existing BillSchedule (which should be a List Bill)
     *
     * @param billMethodCT
     *
     * @return  newly created BillSchedule
     */
    public BillSchedule createIndividualBill(String billMethodCT)
    {
        BillSchedule individualBillSchedule = (BillSchedule) SessionHelper.shallowCopy(this, BillSchedule.DATABASE);

        individualBillSchedule.setBillMethodCT(billMethodCT );
        individualBillSchedule.setBillTypeCT(BillSchedule.BILL_TYPE_INDIVUAL);

        return individualBillSchedule;
    }

    /**
     * Determines if there are any conflicts between the specified date and
     * the set of defined in SkipMonthStart#CT(s) and the SkipNumberOfMonths#CT(s).
     *
     * For example:
     *
     * If the specified date is 01/02/2007, and there are two SkipMonth(s)/SkipNumber(s) defined as:
     *
     * SkipMonthStart1CT = January and SkipNumberOfMonths1CT = 1
     * SkipMonthStart2CT = July and SkipNumberOfMonths2CT = 2
     *
     * Then the months to skip would be January, July and August.
     * Since the specfied date of 01/02/2007 equates to the month of January, then the
     * specified date is [not] a valid NextBillDueDate.
     *
     * @param date the date for which to test againt the set of SkipMonthStart#CT(s) and the SkipNumberOfMonths#CT(s)
     * @return false if there are no conflicts with the specified date, true otherwise
     */
    public boolean isValidNextBillDueDate(EDITDate date)
    {
        boolean validNextBillDueDate = true;

        String testMonth = date.getMonthName();

        if (getSkipMonths().contains(testMonth))
        {
            validNextBillDueDate = false;
        }

        return validNextBillDueDate;
    }

    /**
     * Determines the List of months that should be skipped based on the current values
     * of SkipMonthStart#CT(s) and the SkipNumberOfMonths#CT(s)
     * @return
     */
    private List<String> getSkipMonths()
    {
        List<String> skipMonths = new ArrayList<String>();

        Map<String, Long> skipMonthNumbers = new HashMap<String, Long>();

        List<String> months = Arrays.asList(EDITDate.MONTH_NAMES);

        if (getSkipMonthStart1CT() != null)
        {
            skipMonthNumbers.put(getSkipMonthStart1CT(), new Long(getSkipNumberOfMonths1CT()));
        }

        if (getSkipMonthStart2CT() != null)
        {
            skipMonthNumbers.put(getSkipMonthStart2CT(), new Long(getSkipNumberOfMonths2CT()));
        }

        if (getSkipMonthStart3CT() != null)
        {
            skipMonthNumbers.put(getSkipMonthStart3CT(), new Long(getSkipNumberOfMonths3CT()));
        }

        for (String skipMonth:skipMonthNumbers.keySet())
        {
            int monthIndex = months.indexOf(skipMonth);

            Long skipNumber = skipMonthNumbers.get(skipMonth);

            for (int i = 0; i < skipNumber.intValue(); i++)
            {
                if ((monthIndex + i) > 11)
                {
                    skipMonths.add(months.get((monthIndex + i) - 12));
                }
                else
                {
                    skipMonths.add(months.get(monthIndex + i));
                }
            }
        }

        return skipMonths;
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        staging.BillSchedule stagingBillSchedule = new staging.BillSchedule();
        stagingBillSchedule.setStaging(stagingContext.getStaging());
        stagingBillSchedule.setBillingCompany(this.billingCompanyCT);
        stagingBillSchedule.setFirstBillDueDate(this.firstBillDueDate);
        stagingBillSchedule.setLeadDaysOR(this.leadDaysOR);
        stagingBillSchedule.setWeekDay(this.weekDayCT);
        stagingBillSchedule.setEffectiveDate(this.effectiveDate);
        stagingBillSchedule.setTerminationDate(this.terminationDate);
        stagingBillSchedule.setStatus(this.statusCT);
        stagingBillSchedule.setSortOption(this.sortOptionCT);
        stagingBillSchedule.setBillingConsolidation(this.billingConsolidationCT);
        stagingBillSchedule.setSocialSecurityMask(this.socialSecurityMaskCT);
        stagingBillSchedule.setNumberOfCopiesGroup(this.numberOfCopiesGroup);
        stagingBillSchedule.setNumberOfCopiesAgent(this.numberOfCopiesAgent);
        stagingBillSchedule.setNextBillExtractDate(this.nextBillExtractDate);
        stagingBillSchedule.setNextBillDueDate(this.nextBillDueDate);
        stagingBillSchedule.setLastBillDueDate(this.lastBillDueDate);
        stagingBillSchedule.setBillingMode(this.billingModeCT);
        stagingBillSchedule.setBillType(this.billTypeCT);
        stagingBillSchedule.setCreationOperator(this.creationOperator);
        stagingBillSchedule.setCreationDate(this.creationDate);
        stagingBillSchedule.setRepName(this.repName);
        stagingBillSchedule.setRepPhoneNumber(this.repPhoneNumber);
        stagingBillSchedule.setBillMethod(this.billMethodCT);
        stagingBillSchedule.setSkipMonthStart1(this.skipMonthStart1CT);
        stagingBillSchedule.setSkipNumberOfMonths1(this.skipNumberOfMonths1CT);
        stagingBillSchedule.setSkipMonthStart2(this.skipMonthStart2CT);
        stagingBillSchedule.setSkipNumberOfMonths2(this.skipNumberOfMonths2CT);
        stagingBillSchedule.setSkipMonthStart3(this.skipMonthStart3CT);
        stagingBillSchedule.setSkipNumberOfMonths3(this.skipNumberOfMonths3CT);
        stagingBillSchedule.setFirstDeductionDate(this.firstDeductionDate);
        stagingBillSchedule.setDeductionFrequency(this.deductionFrequencyCT);
        stagingBillSchedule.setLastDeductionDate(this.lastDeductionDate);
        stagingBillSchedule.setEFTDraftDay(this.eftDraftDay);
        stagingBillSchedule.setEFTDraftDayStartMonth(this.eftDraftDayStartMonthCT);
        stagingBillSchedule.setRequiredPremiumAmount(this.requiredPremiumAmount);
        stagingBillSchedule.setTransitionPeriodEndDate(this.transitionPeriodEndDate);
        stagingBillSchedule.setESBillScheduleKey(this.billSchedulePK);

//        stagingContext.getStaging().addBillSchedule(stagingBillSchedule);
        stagingBillSchedule.setStaging(stagingContext.getStaging());
        stagingContext.setCurrentBillSchedule(stagingBillSchedule);

        SessionHelper.saveOrUpdate(stagingBillSchedule, database);

        return stagingContext;
    }

    private void logError(Exception e, BillSchedule billSchedule, ContractGroup caseContractGroup,
                          ContractGroup groupContractGroup, Segment segment)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_BILL_EXTRACT).updateFailure();

        logToDatabase(e, billSchedule, caseContractGroup, groupContractGroup, segment);
    }

    private void logToDatabase(Exception e, BillSchedule billSchedule, ContractGroup caseContractGroup, ContractGroup groupContractGroup, Segment segment)
    {
        String message = e.getMessage();
        String groupContractGroupName="";
        String groupNumber ="";
        String contractNumber="";
        String payorName ="";
        EDITMap columnInfo = new EDITMap();


        Company company = Company.findByProductStructurePK(segment.getProductStructureFK());
        ClientRole clientRole = caseContractGroup.getClientRole();
        ClientDetail clientDetail = clientRole.getClientDetail();
        String contractGroupName = clientDetail.getCorporateName();

        if(billSchedule.getBillMethodCT().equalsIgnoreCase(BILL_METHOD_LISTBILL))
        {
            groupNumber = groupContractGroup.getContractGroupNumber();
            ClientRole groupClientRole = groupContractGroup.getClientRole();
            ClientDetail groupClientDetail = groupClientRole.getClientDetail();
            groupContractGroupName = groupClientDetail.getCorporateName();
        }

        else if(billSchedule.getBillMethodCT().equals(BILL_METHOD_DIRECT_BILL)|| billSchedule.getBillMethodCT().equals(BILL_METHOD_EFT))
        {
           ContractClient contractClient = segment.getPayorContractClient();
           ClientRole payorClientRole = contractClient.getClientRole();
           ClientDetail payorClientDetail = payorClientRole.getClientDetail();
           payorName = payorClientDetail.getPrettyName();
           contractNumber = segment.getContractNumber();
        }
    

        if (message == null)
        {
            message = "Bill Job Failed on Case " + caseContractGroup.getContractGroupNumber() + ", Group " + groupContractGroup.getContractGroupNumber();
        }

        columnInfo.put("CompanyName", company.getCompanyName());
        columnInfo.put("CaseNumber", caseContractGroup.getContractGroupNumber());
        columnInfo.put("CaseName", contractGroupName);
        columnInfo.put("GroupNumber", groupNumber);
        columnInfo.put("GroupName", groupContractGroupName);
        columnInfo.put("BillDueDate", billSchedule.getNextBillDueDate());
        columnInfo.put("ContractNumber", contractNumber);
        columnInfo.put("PayorName", payorName);

        Log.logToDatabase(Log.BILLING, message, columnInfo);
    }
    
    /**
     * Get all current/past BillSchedules, as well as ChangeHistory records associated with BillScheduleFK changes, for a ContractGroup
     */
    public Map<Long, ChangeHistory> getBillScheduleHistory()
	{
		Map<Long, ChangeHistory> billScheduleHistoryMap = new LinkedHashMap<>();
		
		ContractGroup contractGroup = ContractGroup.findBy_BillSchedulePK(this.billSchedulePK);
		
		// Get current billScheduleFK and add to map
		String keyToSearch = Long.toString(contractGroup.getBillScheduleFK());
		
		billScheduleHistoryMap.put(Long.parseLong(keyToSearch), null);
		
		ChangeHistory[] changeHistories = null;
		
		boolean continueSearch = true;
		
		// Search ChangeHistory for BillScheduleFK changes and add those FKs/ChangeHistories to the map
		while(continueSearch)
		{
			changeHistories = ChangeHistory.findByModifiedRecordKey_AfterValue(contractGroup.getContractGroupPK(), keyToSearch);
			
			if (changeHistories == null || changeHistories.length == 0)
			{
				continueSearch = false;
				break;
			}
			else if (changeHistories != null && changeHistories.length > 0) 
			{
				continueSearch = false; 
				
				for (ChangeHistory changeHistory : changeHistories)
				{
					if (changeHistory.getModifiedRecordFK().equals(contractGroup.getContractGroupPK()) && changeHistory.getFieldName().equalsIgnoreCase("BillScheduleFK"))
					{
						keyToSearch = changeHistory.getBeforeValue();
						
						continueSearch = true; 
						
						if (!billScheduleHistoryMap.containsKey(keyToSearch))
						{
							billScheduleHistoryMap.put(Long.parseLong(keyToSearch), changeHistory);
						}
					}
				}
			}
		}
		
		return billScheduleHistoryMap;
	}
    
    
    /**
     * Used to determine if updates to a BillSchedule record require the writing of a new record.
     * *Note the method of same-name in BillScheduleVO.java 
     */
    public boolean requiresNewBillScheduleRecord(BillScheduleVO existingBillSchedule)
    {    	
    	String existingBillingMode = existingBillSchedule.getBillingModeCT();
    	String existingBillMethod = existingBillSchedule.getBillMethodCT();
    	String existingDeductionFreq = existingBillSchedule.getDeductionFrequencyCT();
    	String existingSkipNumber1 = existingBillSchedule.getSkipNumberOfMonths1CT();
    	String existingSkipNumber2 = existingBillSchedule.getSkipNumberOfMonths2CT();
    	String existingSkipNumber3 = existingBillSchedule.getSkipNumberOfMonths3CT();

    	String strExistingBillChangeStartDate = existingBillSchedule.getBillChangeStartDate();
    	EDITDate existingBillChangeStartDate = null;
    	
    	if(strExistingBillChangeStartDate != null)
    	{
    		existingBillChangeStartDate = new EDITDate(strExistingBillChangeStartDate);
    	}

    	// Check for billingModeCT changes
    	if (this.billingModeCT != null)
    	{
    		if (existingBillingMode != null && !this.billingModeCT.equalsIgnoreCase(existingBillingMode))
    		{
    			return true;
    		}
    		else if (existingBillingMode == null)
    		{
    			return true;
    		}
    	}
    	else 
    	{
    		if (existingBillingMode != null)
    		{
    			return true;
    		}
    	}
    	
    	// Check for billMethodCT changes
    	if (this.billMethodCT != null)
    	{
    		if (existingBillMethod != null && !this.billMethodCT.equalsIgnoreCase(existingBillMethod))
    		{
    			return true;
    		}
    		else if (existingBillMethod == null)
    		{
    			return true;
    		}
    	}
    	else 
    	{
    		if (existingBillMethod != null)
    		{
    			return true;
    		}
    	}
    	
    	// Check for deductionFrequencyCT changes
    	if (this.deductionFrequencyCT != null)
    	{
    		if (existingDeductionFreq != null && !this.deductionFrequencyCT.equalsIgnoreCase(existingDeductionFreq))
    		{
    			return true;
    		}
    		else if (existingDeductionFreq == null)
    		{
    			return true;
    		}
    	}
    	else 
    	{
    		if (existingDeductionFreq != null)
    		{
    			return true;
    		}
    	}
    	
    	// Check for skipNumberOfMonths1CT changes
    	if (this.skipNumberOfMonths1CT != null)
    	{
    		if (existingSkipNumber1 != null && !this.skipNumberOfMonths1CT.equalsIgnoreCase(existingSkipNumber1))
    		{
    			return true;
    		}
    		else if (existingSkipNumber1 == null)
    		{
    			return true;
    		}
    	}
    	else 
    	{
    		if (existingSkipNumber1 != null)
    		{
    			return true;
    		}
    	}
    	
    	// Check for skipNumberOfMonths2CT changes
    	if (this.skipNumberOfMonths2CT != null)
    	{
    		if (existingSkipNumber2 != null && !this.skipNumberOfMonths2CT.equalsIgnoreCase(existingSkipNumber2))
    		{
    			return true;
    		}
    		else if (existingSkipNumber2 == null)
    		{
    			return true;
    		}
    	}
    	else 
    	{
    		if (existingSkipNumber2 != null)
    		{
    			return true;
    		}
    	}

    	// Check for skipNumberOfMonths3CT changes
    	if (this.skipNumberOfMonths3CT != null)
    	{
    		if (existingSkipNumber3 != null && !this.skipNumberOfMonths3CT.equalsIgnoreCase(existingSkipNumber3))
    		{
    			return true;
    		}
    		else if (existingSkipNumber3 == null)
    		{
    			return true;
    		}
    	}
    	else 
    	{
    		if (existingSkipNumber3 != null)
    		{
    			return true;
    		}
    	}
    	
    	// Check for billChangeStartDate changes
    	if (this.billChangeStartDate != null)
    	{
    		if (existingBillChangeStartDate != null && !this.billChangeStartDate.equals(existingBillChangeStartDate))
    		{
    			return true;
    		}
    		else if (existingBillChangeStartDate == null)
    		{
    			return true;
    		}
    	}
    	else 
    	{
    		if (existingBillChangeStartDate != null)
    		{
    			return true;
    		}
    	}

    	return false;
    }
    
    private boolean isPopulated(String stringToCheck)
    {
    	if (stringToCheck != null && !stringToCheck.equalsIgnoreCase(""))
    	{
    		return true;
    	}
    	else 
    	{
    		return false;
    	}
    }
    
    /**
     * Update this billSchedule to match the fields of a supplied source BillScheduleVO 
     */
    public void copyBillScheduleFields(BillScheduleVO sourceBillScheduleVO)
    {
    	this.setBillingCompanyCT(sourceBillScheduleVO.getBillingCompanyCT());
        this.setFirstBillDueDate(isPopulated(sourceBillScheduleVO.getFirstBillDueDate()) ? new EDITDate(sourceBillScheduleVO.getFirstBillDueDate()) : null);
        this.setLeadDaysOR(sourceBillScheduleVO.getLeadDaysOR());
        this.setWeekDayCT(sourceBillScheduleVO.getWeekDayCT());
        this.setEffectiveDate(isPopulated(sourceBillScheduleVO.getEffectiveDate()) ? new EDITDate(sourceBillScheduleVO.getEffectiveDate()) : null);
        this.setTerminationDate(isPopulated(sourceBillScheduleVO.getTerminationDate()) ? new EDITDate(sourceBillScheduleVO.getTerminationDate()) : null);
        this.setStatusCT(sourceBillScheduleVO.getStatusCT());
        this.setSortOptionCT(sourceBillScheduleVO.getSortOptionCT());
        this.setBillingConsolidationCT(sourceBillScheduleVO.getBillingConsolidationCT());	
        this.setSocialSecurityMaskCT(sourceBillScheduleVO.getSocialSecurityMaskCT());	
        this.setNumberOfCopiesGroup(sourceBillScheduleVO.getNumberOfCopiesGroup());	
        this.setBillChangeStartDate(isPopulated(sourceBillScheduleVO.getBillChangeStartDate()) ? new EDITDate(sourceBillScheduleVO.getBillChangeStartDate()) : null);
        this.setNumberOfCopiesAgent(sourceBillScheduleVO.getNumberOfCopiesAgent());	
        this.setNextBillExtractDate(isPopulated(sourceBillScheduleVO.getNextBillExtractDate()) ? new EDITDate(sourceBillScheduleVO.getNextBillExtractDate()) : null);	
        this.setNextBillDueDate(isPopulated(sourceBillScheduleVO.getNextBillDueDate()) ? new EDITDate(sourceBillScheduleVO.getNextBillDueDate()) : null);	
        this.setLastBillDueDate(isPopulated(sourceBillScheduleVO.getLastBillDueDate()) ? new EDITDate(sourceBillScheduleVO.getLastBillDueDate()) : null);	
        this.setBillTypeCT(sourceBillScheduleVO.getBillTypeCT());	
        this.setCreationOperator(sourceBillScheduleVO.getCreationOperator());	
        this.setCreationDate(isPopulated(sourceBillScheduleVO.getCreationDate()) ? new EDITDate(sourceBillScheduleVO.getCreationDate()) : null);	
        this.setRepName(sourceBillScheduleVO.getRepName());	
        this.setRepPhoneNumber(sourceBillScheduleVO.getRepPhoneNumber());	
        this.setSkipMonthStart1CT(sourceBillScheduleVO.getSkipMonthStart1CT());	
        this.setSkipMonthStart2CT(sourceBillScheduleVO.getSkipMonthStart2CT());	
        this.setSkipMonthStart3CT(sourceBillScheduleVO.getSkipMonthStart3CT());	
        this.setFirstDeductionDate(isPopulated(sourceBillScheduleVO.getFirstDeductionDate()) ? new EDITDate(sourceBillScheduleVO.getFirstDeductionDate()) : null);	
        this.setLastDeductionDate(isPopulated(sourceBillScheduleVO.getLastDeductionDate()) ? new EDITDate(sourceBillScheduleVO.getLastDeductionDate()) : null);	
        this.setRequiredPremiumAmount(new EDITBigDecimal(sourceBillScheduleVO.getRequiredPremiumAmount()));	
        this.setTransitionPeriodEndDate(isPopulated(sourceBillScheduleVO.getTransitionPeriodEndDate()) ? new EDITDate(sourceBillScheduleVO.getTransitionPeriodEndDate()) : null);	
        this.setVarMonthDedChangeStartDate(isPopulated(sourceBillScheduleVO.getVarMonthDedChangeStartDate()) ? new EDITDate(sourceBillScheduleVO.getVarMonthDedChangeStartDate()) : null);	
        this.setChangeEffectiveDate(isPopulated(sourceBillScheduleVO.getChangeEffectiveDate()) ? new EDITDate(sourceBillScheduleVO.getChangeEffectiveDate()) : null);	
        this.setLastPremiumChangeStartDate(isPopulated(sourceBillScheduleVO.getLastPremiumChangeStartDate()) ? new EDITDate(sourceBillScheduleVO.getLastPremiumChangeStartDate()) : null);	
        this.setEFTDraftDay(sourceBillScheduleVO.getEFTDraftDay());	
        this.setEFTDraftDayStartMonthCT(sourceBillScheduleVO.getEFTDraftDayStartMonthCT());
        this.setBillingModeCT(sourceBillScheduleVO.getBillingModeCT());
        this.setBillMethodCT(sourceBillScheduleVO.getBillMethodCT());
        this.setSkipNumberOfMonths1CT(sourceBillScheduleVO.getSkipNumberOfMonths1CT());
        this.setSkipNumberOfMonths2CT(sourceBillScheduleVO.getSkipNumberOfMonths2CT());
        this.setSkipNumberOfMonths3CT(sourceBillScheduleVO.getSkipNumberOfMonths3CT());
        this.setDeductionFrequencyCT(sourceBillScheduleVO.getDeductionFrequencyCT());
        this.setBillChangeStartDate(isPopulated(sourceBillScheduleVO.getBillChangeStartDate()) ? new EDITDate(sourceBillScheduleVO.getBillChangeStartDate()) : null);
    }
    
    /**
     * Update this billSchedule to match the fields of a supplied source BillSchedule 
     */
    public void copyBillScheduleFields(BillSchedule sourceBillSchedule)
    {
    	this.setBillingCompanyCT(sourceBillSchedule.getBillingCompanyCT());
        this.setFirstBillDueDate(sourceBillSchedule.getFirstBillDueDate());
        this.setLeadDaysOR(sourceBillSchedule.getLeadDaysOR());
        this.setWeekDayCT(sourceBillSchedule.getWeekDayCT());
        this.setEffectiveDate(sourceBillSchedule.getEffectiveDate());
        this.setTerminationDate(sourceBillSchedule.getTerminationDate());
        this.setStatusCT(sourceBillSchedule.getStatusCT());
        this.setSortOptionCT(sourceBillSchedule.getSortOptionCT());
        this.setBillingConsolidationCT(sourceBillSchedule.getBillingConsolidationCT());	
        this.setSocialSecurityMaskCT(sourceBillSchedule.getSocialSecurityMaskCT());	
        this.setNumberOfCopiesGroup(sourceBillSchedule.getNumberOfCopiesGroup());	
        this.setBillChangeStartDate(sourceBillSchedule.getBillChangeStartDate());
        this.setNumberOfCopiesAgent(sourceBillSchedule.getNumberOfCopiesAgent());	
        this.setNextBillExtractDate(sourceBillSchedule.getNextBillExtractDate());	
        this.setNextBillDueDate(sourceBillSchedule.getNextBillDueDate());	
        this.setLastBillDueDate(sourceBillSchedule.getLastBillDueDate());	
        this.setBillingModeCT(sourceBillSchedule.getBillingModeCT());	
        this.setBillTypeCT(sourceBillSchedule.getBillTypeCT());	
        this.setCreationOperator(sourceBillSchedule.getCreationOperator());	
        this.setCreationDate(sourceBillSchedule.getCreationDate());	
        this.setRepName(sourceBillSchedule.getRepName());	
        this.setRepPhoneNumber(sourceBillSchedule.getRepPhoneNumber());	
        this.setBillMethodCT(sourceBillSchedule.getBillMethodCT());	
        this.setSkipMonthStart1CT(sourceBillSchedule.getSkipMonthStart1CT());	
        this.setSkipNumberOfMonths1CT(sourceBillSchedule.getSkipNumberOfMonths1CT());	
        this.setSkipMonthStart2CT(sourceBillSchedule.getSkipMonthStart2CT());	
        this.setSkipNumberOfMonths2CT(sourceBillSchedule.getSkipNumberOfMonths2CT());	
        this.setSkipMonthStart3CT(sourceBillSchedule.getSkipMonthStart3CT());	
        this.setSkipNumberOfMonths3CT(sourceBillSchedule.getSkipNumberOfMonths3CT());	
        this.setFirstDeductionDate(sourceBillSchedule.getFirstDeductionDate());	
        this.setDeductionFrequencyCT(sourceBillSchedule.getDeductionFrequencyCT());	
        this.setLastDeductionDate(sourceBillSchedule.getLastDeductionDate());	
        this.setRequiredPremiumAmount(sourceBillSchedule.getRequiredPremiumAmount());	
        this.setTransitionPeriodEndDate(sourceBillSchedule.getTransitionPeriodEndDate());	
        this.setBillChangeStartDate(sourceBillSchedule.getBillChangeStartDate());	
        this.setVarMonthDedChangeStartDate(sourceBillSchedule.getVarMonthDedChangeStartDate());	
        this.setChangeEffectiveDate(sourceBillSchedule.getChangeEffectiveDate());	
        this.setLastPremiumChangeStartDate(sourceBillSchedule.getLastPremiumChangeStartDate());	
        this.setEFTDraftDay(sourceBillSchedule.getEFTDraftDay());	
        this.setEFTDraftDayStartMonthCT(sourceBillSchedule.getEFTDraftDayStartMonthCT());
    }
}