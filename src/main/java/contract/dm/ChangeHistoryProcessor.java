/*
 * User: unknown
 * Date: Sep 24, 2001
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package contract.dm;

import contract.dm.dao.DAOFactory;
import edit.common.vo.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The Contract History  processor
 */
public class ChangeHistoryProcessor {


	public static final int CONTRACT = 1;
    private static final String SEGMENT_STATUS_CODE_VALUE       = "Commit";

    public static final String SEGMENT_PK                       = "SegmentPK";
    public static final String SEGMENT_CONTRACT_NUMBER          = "ContractNumber";
    public static final String SEGMENT_CONTRACTGROUP_FK         = "ContractGroupFK";
    public static final String SEGMENT_PRODUCT_STRUCTURE_FK     = "ProductStructureFK";
    public static final String SEGMENT_EFFECTIVE_DATE           = "EffectiveDate";
    public static final String SEGMENT_APPLICATION_SIGNED_DATE  = "ApplicationSignedDate";
    public static final String SEGMENT_PURCHASE_AMOUNT          = "Amount";
    public static final String SEGMENT_QUAL_NONQUAL             = "QualNonQualCT";
    public static final String SEGMENT_EXCHANGE_IND             = "ExchangeInd";
    public static final String SEGMENT_COST_BASIS               = "CostBasis";
    public static final String SEGMENT_RECOVERED_COST_BASIS     = "RecoverdCostBasis";
    public static final String SEGMENT_TERMINATION_DATE         = "TerminationDate";
    public static final String SEGMENT_STATUS_CHANGE_DATE       = "StatusChangeDate";
    public static final String SEGMENT_NAME                     = "SegmentNameCT";
    public static final String SEGMENT_STATUS                   = "SegmentStatusCT";
    public static final String SEGMENT_OPTION_CODE              = "OptionCodeCT";
    public static final String SEGMENT_ISSUE_STATE              = "IssueStateCT";
    public static final String SEGMENT_QUALIFIED_TYPE           = "QualifiedTypeCT";
    public static final String SEGMENT_QUOTE_DATE               = "QuoteDate";
    public static final String SEGMENT_PREMIUM_TAXES            = "Charges";
    public static final String SEGMENT_FRONT_END_LOADS          = "Loads";
    public static final String SEGMENT_FEES                     = "Fees";

    public static final String PAYOUT_PAYMENT_START_DATE      = "PaymentStartDate";
    public static final String PAYOUT_PAYMENT_FREQUENCY       = "PaymentFrequencyCT";
    public static final String PAYOUT_MRD_ELECTION            = "MRDElection";
    public static final String PAYOUT_MRD_AMOUNT              = "MRDAmount";
    public static final String PAYOUT_CERTAIN_DURATION        = "CertainDuration";
    public static final String PAYOUT_POST_JUNE1986_INVESTMNT = "PostJune1986Investment";
    public static final String PAYOUT_PAYMENT_AMOUNT          = "PaymentAmount";
    public static final String PAYOUT_REDUCE_PERCENT_1        = "ReducePercent1";
    public static final String PAYOUT_REDUCE_PERCENT_2        = "ReductPercent2";
    public static final String PAYOUT_TOTAL_EXP_RETURN_AMT    = "TotalExpectedReturnAmount";
    public static final String PAYOUT_FINAL_DISTRIBUTION_AMT  = "FinalDistributionAmount";
    public static final String PAYOUT_EXCLUSION_RATIO         = "ExclusionRatio";
    public static final String PAYOUT_YEARLY_TAXABLE_BENEFIT  = "YearlyTaxableBenefit";
    public static final String PAYOUT_NUM_REMAINING_PYMTS     = "NumberRemainingPayments";
    public static final String PAYOUT_FINAL_PAYMENT_DATE      = "FinalPaymentDate";
    public static final String PAYOUT_LAST_CHECK_DATE         = "LastCheckDate";
    public static final String PAYOUT_NEXT_PAYMENT_DATE       = "NextPaymentDate";
    public static final String PAYOUT_EXCESS_INTEREST         = "ExcesInterest";
    public static final String PAYOUT_EXCESS_INTEREST_METHOD  = "ExcessInterestMethod";
    public static final String PAYOUT_EXCESS_INT_START_DATE   = "ExcessInterestStartDate";

    public static final String CC_CLIENT_ROLE_FK       = "ClientRoleFK";
    public static final String CC_SEGMENT_FK           = "SegmentFK";
    public static final String CC_ISSUE_AGE            = "IssueAge";
    public static final String CC_EFFECTIVE_DATE       = "EffectiveDate";
    public static final String CC_TERMINATION_DATE     = "TerminationDate";

    public static final String CCA_ALLOCATION_PCT      = "AllocationPercent";
    public static final String CCA_OVERRIDE_STATUS     = "OverrideStatus";

    public static final String WH_FED_WITHHOLDING_TYPE    = "FederalWithholdingTypeCT";
    public static final String WH_FED_WITHHOLDING_AMT     = "FederalWithholdingAmount";
    public static final String WH_FED_WITHHOLDING_PCT     = "FederalWithholdingPercent";
    public static final String WH_STATE_WITHHOLDING_TYPE  = "StateWithholdingTypeCT";
    public static final String WH_STATE_WITHHOLDING_AMT   = "StateWithholdingAmount";
    public static final String WH_STATE_WITHHOLDING_PCT   = "StateWithholdingPercent";
    public static final String WH_CITY_WITHHOLDING_TYPE   = "CityWithholdingTypeCT";
    public static final String WH_CITY_WITHHOLDING_AMT    = "CityWithholdingAmount";
    public static final String WH_CITY_WITHHOLDING_PCT    = "CityWithholdingPercent";
    public static final String WH_COUNTY_WITHHOLDING_TYPE = "CountyWithholdingTypeCT";
    public static final String WH_COUNTY_WITHHOLDING_AMT  = "CountyWithholdingAmount";
    public static final String WH_COUNTY_WITHHOLDING_PCT  = "CountyWithholdingPercent";

    public static final String INVESTMENT_FILTERED_FUND_ID           = "FundFK";
    public static final String INVESTMENT_EXCESS_INT_CALC_DATE       = "ExcessInterestCalculationDate";
    public static final String INVESTMENT_EXCESS_INT_PAY_DATE        = "ExcessInterestPaymentDate";
    public static final String INVESTMENT_EXCESS_INTEREST            = "ExcessInterest";
    public static final String INVESTMENT_EXCESS_INTEREST_METHOD     = "ExcessInterestMethod";
    public static final String INVESTMENT_EXCESS_INTEREST_START_DATE = "ExcessInterestStartDate";
    public static final String INVESTMENT_AIR                        = "AssumedInvestmentReturn";

    public static final String INV_ALLOC_ALLOCATION_PCT  = "AllocationPercent";
    public static final String INV_ALLOC_DOLLARS         = "Dollars";
    public static final String INV_ALLOC_UNITS           = "Units";
    public static final String INV_ALLOC_OVERRIDE_STATUS = "OverrideStatus";
    public static final String INV_ALLOC_TO_FROM_STATUS  = "ToFromStatus";

    public static final String BUCKET_CUM_DOLLARS        = "CumDollars";
    public static final String BUCKET_CUM_UNITS          = "CumUnits";
    public static final String BUCKET_DEPOSIT_DATE       = "DepositDate";
    public static final String BUCKET_DEPOSIT_AMOUNT     = "DepositAmount";
    public static final String BUCKET_LAST_VAL_DATE      = "LastValuationDate";
    public static final String BUCKET_INTEREST_RATE      = "InterestRate";
    public static final String BUCKET_DURATION           = "Duration";
    public static final String BUCKET_PAYOUT_UNITS       = "PayoutUnits";
    public static final String BUCKET_PAYOUT_DOLLARS     = "PayoutDollars";
    public static final String BUCKET_RENEWAL_DATE       = "RenewalDate";

    public static final String BUCKET_ALLOC_ALLOCATION_PCT = "AllocationPercent";
    public static final String BUCKET_ALLOC_DOLLARS        = "Dollars";
    public static final String BUCKET_ALLOC_UNITS          = "Units";

    //Add ScheduleEvent
	Object valueobject            = null;
	long   segmentPK              = 0;
    long   payoutPK               = 0;
	long   investmentPK           = 0;
    long   contractClientPK       = 0;
    long   bucketPK               = 0;
    long   modifiedRecordKey      = 0;
    long   changeHistoryPK        = 0;
    String tableName              = "";
	String fieldName              = "";
    String effectiveDate          = "";
    String processDate            = "";
	String beforeValue            = "";
	String afterValue             = "";
	String operator               = "";
	String maintDateTime          = "";
	List changes                = new ArrayList();
	ChangeHistoryVO changeHistoryVO = new ChangeHistoryVO();

	public Object[] identifyFieldLevelChanges(int fieldCategory,
											   Object valueObject)
											  throws Exception {

		switch (fieldCategory) {
		
			case CONTRACT:
			
				SegmentVO segmentVO = ((SegmentVO)valueObject);

                if ((segmentVO.getSegmentStatusCT()).equalsIgnoreCase(SEGMENT_STATUS_CODE_VALUE)) {

                    return identifySegmentChanges(segmentVO);
				}

                else {
				
					return null;
				}

			default:
			
				return null;
		}												
	
	}
	
	private ChangeHistoryVO[] identifySegmentChanges(SegmentVO segmentVO)
													throws Exception {
	

		segmentPK      = segmentVO.getSegmentPK();
        modifiedRecordKey = segmentPK;
        tableName = "Segment";
        effectiveDate = "";
        processDate = "";
        operator = "";
        maintDateTime = "";

		SegmentVO[] existingSegvo = DAOFactory.getSegmentDAO().findByPK(segmentPK);

		if (!(afterValue = segmentVO.getContractNumber()).equals
		   (beforeValue =  existingSegvo[0].getContractNumber())) {
		
			fieldName = SEGMENT_CONTRACT_NUMBER;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = segmentVO.getProductStructureFK() + "").equals
		   (beforeValue =  existingSegvo[0].getProductStructureFK() + "")) {

			fieldName = SEGMENT_PRODUCT_STRUCTURE_FK;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = segmentVO.getContractGroupFK() + "").equals
		   (beforeValue =  existingSegvo[0].getContractGroupFK() + "")) {

			fieldName = SEGMENT_CONTRACTGROUP_FK;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = segmentVO.getSegmentNameCT()).equals
		   (beforeValue =  existingSegvo[0].getSegmentNameCT())) {

			fieldName = SEGMENT_NAME;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = segmentVO.getEffectiveDate()).equals
		   (beforeValue =  existingSegvo[0].getEffectiveDate())) {
		
			fieldName = SEGMENT_EFFECTIVE_DATE;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = segmentVO.getSegmentStatusCT()).equals
		   (beforeValue =  existingSegvo[0].getSegmentStatusCT())) {

			fieldName = SEGMENT_STATUS;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = segmentVO.getApplicationSignedDate()).equals
           (beforeValue =  existingSegvo[0].getApplicationSignedDate())) {

            fieldName = SEGMENT_APPLICATION_SIGNED_DATE;
			changes = createChangeHistoryRecord();
		}
		
        if (!(afterValue = segmentVO.getOptionCodeCT()).equals
		   (beforeValue =  existingSegvo[0].getOptionCodeCT())) {

			fieldName = SEGMENT_OPTION_CODE;
			changes = createChangeHistoryRecord();
        }

		if (!(afterValue = segmentVO.getIssueStateCT()).equals
		   (beforeValue =  existingSegvo[0].getIssueStateCT())) {
		
			fieldName = SEGMENT_ISSUE_STATE;
			changes = createChangeHistoryRecord();
		}
		
		if (!(afterValue = segmentVO.getQualNonQualCT()).equals
		   (beforeValue =  existingSegvo[0].getQualNonQualCT())) {
		
			fieldName = SEGMENT_QUAL_NONQUAL;
			changes = createChangeHistoryRecord();
		}
		
		if (!(afterValue = segmentVO.getExchangeInd()).equals
		   (beforeValue =  existingSegvo[0].getExchangeInd())) {
		
			fieldName = SEGMENT_EXCHANGE_IND;
			changes = createChangeHistoryRecord();
		}
		
		if (!(afterValue = segmentVO.getCostBasis() + "").equals
		   (beforeValue =  existingSegvo[0].getCostBasis() + "")) {
		
			fieldName = SEGMENT_COST_BASIS;
			changes = createChangeHistoryRecord();
		}
		

		if (!(afterValue = segmentVO.getAmount() + "").equals
		   (beforeValue =  existingSegvo[0].getAmount() + "")) {
		
			fieldName = SEGMENT_PURCHASE_AMOUNT;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = segmentVO.getRecoveredCostBasis() + "").equals
		   (beforeValue =  existingSegvo[0].getRecoveredCostBasis() + "")) {

			fieldName = SEGMENT_RECOVERED_COST_BASIS;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = segmentVO.getTerminationDate()).equals
		   (beforeValue =  existingSegvo[0].getTerminationDate())) {

			fieldName = SEGMENT_TERMINATION_DATE;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = segmentVO.getStatusChangeDate()).equals
		   (beforeValue =  existingSegvo[0].getStatusChangeDate())) {

			fieldName = SEGMENT_STATUS_CHANGE_DATE;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = segmentVO.getQuoteDate()).equals
		   (beforeValue =  existingSegvo[0].getQuoteDate())) {
		  	fieldName = SEGMENT_QUOTE_DATE;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = segmentVO.getCharges() + "").equals
		   (beforeValue =  existingSegvo[0].getCharges() + "")) {

			fieldName = SEGMENT_PREMIUM_TAXES;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = segmentVO.getLoads() + "").equals
		   (beforeValue =  existingSegvo[0].getLoads() + "")) {

			fieldName = SEGMENT_FRONT_END_LOADS;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = segmentVO.getFees() + "").equals
		   (beforeValue =  existingSegvo[0].getFees() + "")) {

			fieldName = SEGMENT_FEES;
			changes = createChangeHistoryRecord();
		}

        PayoutVO[] payoutVO = segmentVO.getPayoutVO();
        PayoutVO[] existingPayvo = DAOFactory.getPayoutDAO().findPayoutBySegmentFK(segmentPK);

		if (!(afterValue = payoutVO[0].getPaymentStartDate() ).equals
		   (beforeValue =  existingPayvo[0].getPaymentStartDate() )) {

			fieldName = PAYOUT_PAYMENT_START_DATE;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = payoutVO[0].getPaymentFrequencyCT()).equals
		   (beforeValue =  existingPayvo[0].getPaymentFrequencyCT())) {

			fieldName = PAYOUT_PAYMENT_FREQUENCY;
			changes = createChangeHistoryRecord();
		}

//        if (!(afterValue = payoutVO[0].getMRDElection()).equals
//		   (beforeValue =  existingPayvo[0].getMRDElection())) {
//
//			fieldName = PAYOUT_MRD_ELECTION;
//			changes = createChangeHistoryRecord();
//		}
//
//        if (!(afterValue = payoutVO[0].getMRDAmount() + "").equals
//		   (beforeValue =  existingPayvo[0].getMRDAmount() + "")) {
//
//			fieldName = PAYOUT_MRD_AMOUNT;
//			changes = createChangeHistoryRecord();
//		}

        if (!(afterValue = payoutVO[0].getCertainDuration() + "").equals
		   (beforeValue =  existingPayvo[0].getCertainDuration() + "")) {

			fieldName = PAYOUT_CERTAIN_DURATION;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = payoutVO[0].getPostJune1986Investment()).equals
		   (beforeValue =  existingPayvo[0].getPostJune1986Investment())) {

			fieldName = PAYOUT_POST_JUNE1986_INVESTMNT;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = payoutVO[0].getPaymentAmount() + "").equals
		   (beforeValue =  existingPayvo[0].getPaymentAmount() + "")) {

			fieldName = PAYOUT_PAYMENT_AMOUNT;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = payoutVO[0].getReducePercent1() + "").equals
		   (beforeValue =  existingPayvo[0].getReducePercent1() + "")) {

			fieldName = PAYOUT_REDUCE_PERCENT_1;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = payoutVO[0].getReducePercent2() + "").equals
		   (beforeValue =  existingPayvo[0].getReducePercent2() + "")) {

			fieldName = PAYOUT_REDUCE_PERCENT_2;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = payoutVO[0].getTotalExpectedReturnAmount() + "").equals
		   (beforeValue =  existingPayvo[0].getTotalExpectedReturnAmount() + "")) {
		
			fieldName = PAYOUT_TOTAL_EXP_RETURN_AMT;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = payoutVO[0].getFinalDistributionAmount() + "").equals
		   (beforeValue =  existingPayvo[0].getFinalDistributionAmount() + "")) {
		
			fieldName = PAYOUT_FINAL_DISTRIBUTION_AMT;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = payoutVO[0].getExclusionRatio() + "").equals
		   (beforeValue =  existingPayvo[0].getExclusionRatio() + "")) {
		
			fieldName = PAYOUT_EXCLUSION_RATIO;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = payoutVO[0].getYearlyTaxableBenefit() + "").equals
		   (beforeValue =  existingPayvo[0].getYearlyTaxableBenefit() + "")) {

			fieldName = PAYOUT_YEARLY_TAXABLE_BENEFIT;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = payoutVO[0].getFinalPaymentDate()).equals
		   (beforeValue =  existingPayvo[0].getFinalPaymentDate())) {
		
			fieldName = PAYOUT_FINAL_PAYMENT_DATE;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = payoutVO[0].getLastCheckDate()).equals
		   (beforeValue =  existingPayvo[0].getLastCheckDate())) {
		
			fieldName = PAYOUT_LAST_CHECK_DATE;
			changes = createChangeHistoryRecord();
		}
		
		if (!(afterValue = payoutVO[0].getNextPaymentDate()).equals
		   (beforeValue =  existingPayvo[0].getNextPaymentDate())) {
		
			fieldName = PAYOUT_NEXT_PAYMENT_DATE;
			changes = createChangeHistoryRecord();
        }

	   	ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();
	   	ContractClientVO[] existingContClientvos = existingSegvo[0].getContractClientVO();

		if (contractClientVOs == null || existingContClientvos == null) {
			;
		}

        else {
		
			for (int i = 0; i < contractClientVOs.length; i++) {
			
				long voContractClientId = contractClientVOs[i].getContractClientPK();
			  
			  	ContractClientVO exContractClientVO = new ContractClientVO();
				
				boolean matchFound = false;
				
				for (int j = 0; j < existingContClientvos.length; j++) {
				
					long existingContractClientId = existingContClientvos[j].getContractClientPK();

					if (voContractClientId == existingContractClientId) {

						matchFound = true;
						
						exContractClientVO = existingContClientvos[j];
						
						break; 
					} 
					else  { 
						continue; 
					} 
				}
				
			  	if (matchFound) {

					changes = identifyContractClientChanges(contractClientVOs[i],
			  									  	    	 exContractClientVO,
													     	  changes);
			  	}
			}										  
		}

	   	InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();
	   	InvestmentVO[] existingInvestmentvos = existingSegvo[0].getInvestmentVO();

		if (investmentVOs == null || existingInvestmentvos == null) {
			;
		}
        else {
		
			for (int i = 0; i < investmentVOs.length; i++) {
			
				long voInvestmentId = investmentVOs[i].getInvestmentPK();
			  
			  	InvestmentVO exInvestmentVO = new InvestmentVO();
				
				boolean matchFound = false;
				
				for (int j = 0; j < existingInvestmentvos.length; j++) {
				
					long existingInvestmentId = existingInvestmentvos[j].getInvestmentPK();

					if (voInvestmentId == existingInvestmentId)  {

						matchFound = true;
						
						exInvestmentVO = existingInvestmentvos [j];
						
						break; 
					} 
					else  { 
						continue; 
					} 
				}
				
			  	if (matchFound) {

				changes = identifyInvestmentChanges(investmentVOs[i],
			  									  	 exInvestmentVO,
													  changes);
			  	}
			}										  
		}

		valueobject = changes.toArray(new ChangeHistoryVO [changes.size()]);
		
		return (ChangeHistoryVO[]) valueobject;
	}

	private List identifyContractClientChanges(ContractClientVO contractClientVO,
                                                  ContractClientVO existingContractClientVO,
                                                   List changes)
                                                throws Exception {

		contractClientPK = contractClientVO.getContractClientPK();
		
		if (!(afterValue = contractClientVO.getClientRoleFK() + "").equals
		   (beforeValue =  existingContractClientVO.getClientRoleFK() + "")) {
		
			fieldName = CC_CLIENT_ROLE_FK;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = contractClientVO.getIssueAge() + "").equals
		   (beforeValue =  existingContractClientVO.getIssueAge() + "")) {
		
			fieldName = CC_ISSUE_AGE;
			changes = createChangeHistoryRecord();
		}
			 
		if (!(afterValue = contractClientVO.getEffectiveDate()).equals
		   (beforeValue = existingContractClientVO.getEffectiveDate())) {
		
			fieldName = CC_EFFECTIVE_DATE;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = contractClientVO.getTerminationDate()).equals
		   (beforeValue = existingContractClientVO.getTerminationDate())) {

			fieldName = CC_TERMINATION_DATE;
			changes = createChangeHistoryRecord();
		}

	   	ContractClientAllocationVO[] contractClientAllocVOs = contractClientVO.getContractClientAllocationVO();
	   	ContractClientAllocationVO[] existingCClientAllocVOs = existingContractClientVO.getContractClientAllocationVO();

        if (contractClientAllocVOs == null || existingCClientAllocVOs == null) {
			;
		}

        else {

            for (int i = 0; i < contractClientAllocVOs.length; i++) {

				long voContractClientAllocId = contractClientAllocVOs[i].getContractClientAllocationPK();

			  	ContractClientAllocationVO exContractClientAllocVO = new ContractClientAllocationVO();

				boolean matchFound = false;

                for (int j = 0; j < existingCClientAllocVOs.length; j++) {

                    long existingCClientAllocId = existingCClientAllocVOs[j].getContractClientAllocationPK();

                    if (voContractClientAllocId == existingCClientAllocId) {

                        matchFound = true;

                        exContractClientAllocVO = existingCClientAllocVOs[j];

                        break;
                    }
                    else  {
                         continue;
                    }
                }

                if (matchFound) {

                    changes = identifyContractClientAllocChanges(contractClientAllocVOs[i],
                                                                  exContractClientAllocVO,
                                                                   changes);
                }
            }
        }

        WithholdingVO[] withholdingVOs = contractClientVO.getWithholdingVO();
	   	WithholdingVO[] existingWithholdingVOs = existingContractClientVO.getWithholdingVO();

        if (withholdingVOs == null || existingWithholdingVOs == null) {
			;
		}

        else {

            for (int i = 0; i < withholdingVOs.length; i++) {

				long voWithholdingId = withholdingVOs[i].getWithholdingPK();

			  	WithholdingVO exWithholdingVO = new WithholdingVO();

				boolean matchFound = false;

                for (int j = 0; j < existingWithholdingVOs.length; j++) {

                    long existingWithholdingId = existingWithholdingVOs[j].getWithholdingPK();

                    if (voWithholdingId == existingWithholdingId) {

                        matchFound = true;

                        exWithholdingVO = existingWithholdingVOs[j];

                        break;
                    }
                    else  {
                         continue;
                    }
                }

                if (matchFound) {

                    changes = identifyWithholdingChanges(withholdingVOs[i],
                                                          exWithholdingVO,
                                                           changes);
                }
            }
        }

        return changes;
	}

    private List identifyContractClientAllocChanges(ContractClientAllocationVO contractClientAllocVO,
                                                       ContractClientAllocationVO existingCClientAllocVO,
                                                        List changes)
                                                     throws Exception {

		if (!(afterValue = contractClientAllocVO.getAllocationPercent() + "").equals
		   (beforeValue =  existingCClientAllocVO.getAllocationPercent() + "")) {

			fieldName = CCA_ALLOCATION_PCT;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = contractClientAllocVO.getOverrideStatus()).equals
		   (beforeValue =  existingCClientAllocVO.getOverrideStatus())) {

			fieldName = CCA_OVERRIDE_STATUS;
			changes = createChangeHistoryRecord();
		}

        return changes;
	}

    private List identifyWithholdingChanges(WithholdingVO withholdingVO,
                                               WithholdingVO existingWithholdingVO,
                                                List changes)
                                             throws Exception {

		if (!(afterValue = withholdingVO.getFederalWithholdingTypeCT()).equals
		   (beforeValue =  existingWithholdingVO.getFederalWithholdingTypeCT())) {

			fieldName = WH_FED_WITHHOLDING_TYPE;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = withholdingVO.getFederalWithholdingAmount() + "").equals
		   (beforeValue =  existingWithholdingVO.getFederalWithholdingAmount() + "")) {

			fieldName = WH_FED_WITHHOLDING_AMT;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = withholdingVO.getFederalWithholdingPercent() + "").equals
           (beforeValue = existingWithholdingVO.getFederalWithholdingPercent() + "")) {

            fieldName = WH_FED_WITHHOLDING_PCT;
            changes = createChangeHistoryRecord();
        }

        if (!(afterValue = withholdingVO.getStateWithholdingTypeCT()).equals
		   (beforeValue =  existingWithholdingVO.getStateWithholdingTypeCT())) {

			fieldName = WH_STATE_WITHHOLDING_TYPE;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = withholdingVO.getStateWithholdingAmount() + "").equals
		   (beforeValue =  existingWithholdingVO.getStateWithholdingAmount() + "")) {

			fieldName = WH_STATE_WITHHOLDING_AMT;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = withholdingVO.getStateWithholdingPercent() + "").equals
           (beforeValue = existingWithholdingVO.getStateWithholdingPercent() + "")) {

            fieldName = WH_STATE_WITHHOLDING_PCT;
            changes = createChangeHistoryRecord();
        }

        if (!(afterValue = withholdingVO.getCityWithholdingTypeCT()).equals
		   (beforeValue =  existingWithholdingVO.getCityWithholdingTypeCT())) {

			fieldName = WH_CITY_WITHHOLDING_TYPE;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = withholdingVO.getCityWithholdingAmount() + "").equals
		   (beforeValue =  existingWithholdingVO.getCityWithholdingAmount() + "")) {

			fieldName = WH_CITY_WITHHOLDING_AMT;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = withholdingVO.getCityWithholdingPercent() + "").equals
           (beforeValue = existingWithholdingVO.getCityWithholdingPercent() + "")) {

            fieldName = WH_CITY_WITHHOLDING_PCT;
            changes = createChangeHistoryRecord();
        }

        if (!(afterValue = withholdingVO.getCountyWithholdingTypeCT()).equals
		   (beforeValue =  existingWithholdingVO.getCountyWithholdingTypeCT())) {

			fieldName = WH_COUNTY_WITHHOLDING_TYPE;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = withholdingVO.getCountyWithholdingAmount() + "").equals
		   (beforeValue =  existingWithholdingVO.getCountyWithholdingAmount() + "")) {

			fieldName = WH_COUNTY_WITHHOLDING_AMT;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = withholdingVO.getCountyWithholdingPercent() + "").equals
           (beforeValue = existingWithholdingVO.getCountyWithholdingPercent() + "")) {

            fieldName = WH_COUNTY_WITHHOLDING_PCT;
            changes = createChangeHistoryRecord();
        }

        return changes;
	}

	private List identifyInvestmentChanges(InvestmentVO investmentVO,
                                              InvestmentVO existingInvestmentVO,
                                               List changes)
                                            throws Exception {
												
		investmentPK = investmentVO.getInvestmentPK();

		if (!(afterValue = investmentVO.getFilteredFundFK() + "").equals
		   (beforeValue =  existingInvestmentVO.getFilteredFundFK() + "")) {
		
			fieldName = INVESTMENT_FILTERED_FUND_ID;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = investmentVO.getExcessInterestCalculationDate()).equals
		   (beforeValue =  existingInvestmentVO.getExcessInterestCalculationDate())) {
		
			fieldName = INVESTMENT_EXCESS_INT_CALC_DATE;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = investmentVO.getExcessInterestPaymentDate()).equals
		   (beforeValue =  existingInvestmentVO.getExcessInterestPaymentDate())) {
		
			fieldName = INVESTMENT_EXCESS_INT_PAY_DATE;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = investmentVO.getExcessInterest() + "").equals
		   (beforeValue =  existingInvestmentVO.getExcessInterest() + "")) {
		
			fieldName = INVESTMENT_EXCESS_INTEREST;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = investmentVO.getExcessInterestMethod()).equals
		   (beforeValue =  existingInvestmentVO.getExcessInterestMethod())) {

			fieldName = INVESTMENT_EXCESS_INTEREST_METHOD;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = investmentVO.getExcessInterestStartDate()).equals
		   (beforeValue =  existingInvestmentVO.getExcessInterestStartDate())) {

			fieldName = INVESTMENT_EXCESS_INTEREST_START_DATE;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = investmentVO.getAssumedInvestmentReturn() + "").equals
		   (beforeValue =  existingInvestmentVO.getAssumedInvestmentReturn() + "")) {

			fieldName = INVESTMENT_AIR;
			changes = createChangeHistoryRecord();
		}

        InvestmentAllocationVO[] investmentAllocVOs = investmentVO.getInvestmentAllocationVO();
	   	InvestmentAllocationVO[] existingInvestmentAllocVOs = existingInvestmentVO.getInvestmentAllocationVO();

        if (investmentAllocVOs == null || existingInvestmentAllocVOs == null) {
			;
		}

        else {

            for (int i = 0; i < investmentAllocVOs.length; i++) {

				long voInvestmentAllocId = investmentAllocVOs[i].getInvestmentAllocationPK();

			  	InvestmentAllocationVO exInvestmentAllocVO = new InvestmentAllocationVO();

				boolean matchFound = false;

                for (int j = 0; j < existingInvestmentAllocVOs.length; j++) {

                    long existingInvestmentAllocId = existingInvestmentAllocVOs[j].getInvestmentAllocationPK();

                    if (voInvestmentAllocId == existingInvestmentAllocId) {

                        matchFound = true;

                        exInvestmentAllocVO = existingInvestmentAllocVOs[j];

                        break;
                    }
                    else  {
                         continue;
                    }
                }

                if (matchFound) {

                    changes = identifyInvestmentAllocChanges(investmentAllocVOs[i],
                                                              exInvestmentAllocVO,
                                                               changes);
                }
            }
        }

        BucketVO[] bucketVOs = investmentVO.getBucketVO();
	   	BucketVO[] existingBucketVOs = existingInvestmentVO.getBucketVO();

        if (bucketVOs == null || existingBucketVOs == null) {
			;
		}

        else {

            for (int i = 0; i < bucketVOs.length; i++) {

				long voBucketId = bucketVOs[i].getBucketPK();

			  	BucketVO exBucketVO = new BucketVO();

				boolean matchFound = false;

                for (int j = 0; j < existingBucketVOs.length; j++) {

                    long existingBucketId = existingBucketVOs[j].getBucketPK();

                    if (voBucketId == existingBucketId) {

                        matchFound = true;

                        exBucketVO = existingBucketVOs[j];

                        break;
                    }
                    else  {
                         continue;
                    }
                }

                if (matchFound) {

                    changes = identifyBucketChanges(bucketVOs[i],
                                                     exBucketVO,
                                                      changes);
                }
            }
        }

        return changes;
	}

    private List identifyInvestmentAllocChanges(InvestmentAllocationVO investmentAllocVO,
                                                   InvestmentAllocationVO existingInvestmentAllocVO,
                                                    List changes)
                                                 throws Exception {

		if (!(afterValue = investmentAllocVO.getAllocationPercent() + "").equals
		   (beforeValue =  existingInvestmentAllocVO.getAllocationPercent() + "")) {

			fieldName = INV_ALLOC_ALLOCATION_PCT;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = investmentAllocVO.getDollars() + "").equals
		   (beforeValue =  existingInvestmentAllocVO.getDollars() + "")) {

			fieldName = INV_ALLOC_DOLLARS;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = investmentAllocVO.getUnits() + "").equals
		   (beforeValue =  existingInvestmentAllocVO.getUnits() + "")) {

			fieldName = INV_ALLOC_UNITS;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = investmentAllocVO.getOverrideStatus()).equals
		   (beforeValue =  existingInvestmentAllocVO.getOverrideStatus())) {

			fieldName = INV_ALLOC_OVERRIDE_STATUS;
			changes = createChangeHistoryRecord();
		}

        return changes;
	}

    private List identifyBucketChanges(BucketVO bucketVO,
                                          BucketVO existingBucketVO,
                                           List changes)
                                        throws Exception {

		if (!(afterValue = bucketVO.getCumDollars() + "").equals
		   (beforeValue =  existingBucketVO.getCumDollars() + "")) {

			fieldName = BUCKET_CUM_DOLLARS;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = bucketVO.getCumUnits() + "").equals
		   (beforeValue =  existingBucketVO.getCumUnits() + "")) {

			fieldName = BUCKET_CUM_UNITS;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = bucketVO.getDepositDate()).equals
		   (beforeValue =  existingBucketVO.getDepositDate())) {

			fieldName = BUCKET_DEPOSIT_DATE;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = bucketVO.getDepositAmount() + "").equals
		   (beforeValue =  existingBucketVO.getDepositAmount() + "")) {

			fieldName = BUCKET_DEPOSIT_AMOUNT;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = bucketVO.getLastValuationDate()).equals
		   (beforeValue =  existingBucketVO.getLastValuationDate())) {

			fieldName = BUCKET_LAST_VAL_DATE;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = bucketVO.getBucketInterestRate() + "").equals
		   (beforeValue =  existingBucketVO.getBucketInterestRate() + "")) {

			fieldName = BUCKET_INTEREST_RATE;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = bucketVO.getDurationOverride() + "").equals
		   (beforeValue =  existingBucketVO.getDurationOverride() + "")) {

			fieldName = BUCKET_DURATION;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = bucketVO.getPayoutUnits() + "").equals
		   (beforeValue =  existingBucketVO.getPayoutUnits() + "")) {

			fieldName = BUCKET_PAYOUT_UNITS;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = bucketVO.getPayoutDollars() + "").equals
		   (beforeValue =  existingBucketVO.getPayoutDollars() + "")) {

			fieldName = BUCKET_PAYOUT_DOLLARS;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = bucketVO.getRenewalDate()).equals
		   (beforeValue =  existingBucketVO.getRenewalDate())) {

			fieldName = BUCKET_RENEWAL_DATE;
			changes = createChangeHistoryRecord();
		}

        BucketAllocationVO[] bucketAllocVOs = bucketVO.getBucketAllocationVO();
	   	BucketAllocationVO[] existingBucketAllocVOs = existingBucketVO.getBucketAllocationVO();

        if (bucketAllocVOs == null || existingBucketAllocVOs == null) {
			;
		}

        else {

            for (int i = 0; i < bucketAllocVOs.length; i++) {

				long voBucketAllocId = bucketAllocVOs[i].getBucketAllocationPK();

			  	BucketAllocationVO exBucketAllocVO = new BucketAllocationVO();

				boolean matchFound = false;

                for (int j = 0; j < existingBucketAllocVOs.length; j++) {

                    long existingBucketAllocId = existingBucketAllocVOs[j].getBucketAllocationPK();

                    if (voBucketAllocId == existingBucketAllocId) {

                        matchFound = true;

                        exBucketAllocVO = existingBucketAllocVOs[j];

                        break;
                    }
                    else  {
                         continue;
                    }
                }

                if (matchFound) {

                    changes = identifyBucketAllocChanges(bucketAllocVOs[i],
                                                          exBucketAllocVO,
                                                           changes);
                }
            }
        }

        return changes;
	}

    private List identifyBucketAllocChanges(BucketAllocationVO bucketAllocVO,
                                               BucketAllocationVO existingBucketAllocVO,
                                                List changes)
                                             throws Exception {

		if (!(afterValue = bucketAllocVO.getAllocationPercent() + "").equals
		   (beforeValue =  existingBucketAllocVO.getAllocationPercent() + "")) {

			fieldName = BUCKET_ALLOC_ALLOCATION_PCT;
			changes = createChangeHistoryRecord();
		}

		if (!(afterValue = bucketAllocVO.getDollars() + "").equals
		   (beforeValue =  existingBucketAllocVO.getDollars() + "")) {

			fieldName = BUCKET_ALLOC_DOLLARS;
			changes = createChangeHistoryRecord();
		}

        if (!(afterValue = bucketAllocVO.getUnits() + "").equals
		   (beforeValue =  existingBucketAllocVO.getUnits() + "")) {

			fieldName = BUCKET_ALLOC_UNITS;
			changes = createChangeHistoryRecord();
		}

        return changes;
	}

 	private  List createChangeHistoryRecord()
											  throws Exception
     {
        changeHistoryVO.setChangeHistoryPK(changeHistoryPK);
        changeHistoryVO.setModifiedRecordFK(modifiedRecordKey);
        changeHistoryVO.setTableName(tableName);
        changeHistoryVO.setEffectiveDate(effectiveDate);
        changeHistoryVO.setProcessDate(processDate);
        changeHistoryVO.setFieldName(fieldName);
        changeHistoryVO.setBeforeValue(beforeValue);
        changeHistoryVO.setAfterValue(afterValue);
        changeHistoryVO.setMaintDateTime(maintDateTime);
        changeHistoryVO.setOperator(operator);

        changes.add(changeHistoryVO);

		return changes;
	}
}