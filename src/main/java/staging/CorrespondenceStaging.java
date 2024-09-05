/**
 * User: dlataill
 * Date: Oct 15, 2007
 * Time: 1:31:47 PM
 * <p/>
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package staging;

import edit.common.EDITDateTime;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.vo.*;
import edit.services.config.ServicesConfig;
import edit.services.db.hibernate.SessionHelper;
import group.ContractGroup;
import agent.PlacedAgent;
import contract.AgentHierarchy;
import contract.AgentHierarchyAllocation;
import contract.AgentSnapshot;
import contract.Segment;
import contract.MasterContract;
import client.ClientDetail;
import event.BucketHistory;
import event.EDITTrx;
import event.FinancialHistory;
import role.ClientRole;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.selman.calcfocus.request.AnnualStatementMonthlySummary;

import java.util.Iterator;
import group.ContractGroup;
import role.dm.dao.ClientRoleDAO;

public class CorrespondenceStaging
{
    public static String eventType = "Correspondence";

    private EDITDateTime stagingDate;

    public static final String DATABASE = SessionHelper.STAGING;

    public CorrespondenceStaging(EDITDateTime stagingDate)
    {
        this.stagingDate = stagingDate;
    }

	public boolean stageTables(CorrespondenceDetailVO correspondenceDetailVO)
    {
		boolean isStaged = false;
		
        StagingContext stagingContext = new StagingContext(eventType, stagingDate, correspondenceDetailVO.getCorrespondenceType());
        
        String contractNumber = correspondenceDetailVO.getNaturalDocVO(0).getBaseSegmentVO().getSegmentVO().getContractNumber();

        boolean stageContract = false; 
        
        SegmentBase segmentBase = SegmentBase.findBy_ContractNumber(contractNumber);
        
        if (segmentBase != null)
        {
        	Long stagingPK = segmentBase.getStagingFK();
        	Staging staging = Staging.findBy_StagingPK(stagingPK);
        	String existingEventType = staging.getEventType();
        	String existingCorrType = staging.getCorrespondenceType();

        	if (!existingEventType.equalsIgnoreCase("Correspondence") || 
        			(existingCorrType != null && !existingCorrType.equalsIgnoreCase(stagingContext.getCorrespondenceType())))
        	{
        		stageContract = true;
        	}
        }
        else
        {
        	stageContract = true;
        }
       
        if(stageContract)
        {  

        SessionHelper.beginTransaction(DATABASE);

        Staging staging = Staging.findStagingByDate_EventType_CorrType(stagingDate, eventType, correspondenceDetailVO.getCorrespondenceType());
        
        if (staging == null)
        {
            staging = new Staging();
            staging.stage(stagingContext, DATABASE);
        }
        else
        {
            stagingContext.setStaging(staging);
            stagingContext.setRecordCount(staging.getRecordCount());
        }

        NaturalDocVO naturalDocVO = correspondenceDetailVO.getNaturalDocVO(0);
        Segment segment = new Segment(naturalDocVO.getBaseSegmentVO().getSegmentVO());
        MasterContract masterContract = MasterContract.findByPK(new Long(naturalDocVO.getBaseSegmentVO().getSegmentVO().getMasterContractFK()));
        segment.setMasterContract(masterContract);

        BillSchedule billSchedule = BillSchedule.findByStagingFK(staging.getStagingPK(), segment.getBillScheduleFK(), DATABASE);
        if (billSchedule != null)
        {
            stagingContext.setCurrentBillSchedule(billSchedule);
        }
        else
        {
            billing.BillSchedule billingBillSchedule = billing.BillSchedule.findBy_BillSchedulePK(segment.getBillScheduleFK());
            billingBillSchedule.stage(stagingContext, DATABASE);
        }

        if (segment.getContractGroupFK() != null)
        {
            ContractGroup groupContractGroup = ContractGroup.findBy_ContractGroupPK(segment.getContractGroupFK());
            ContractGroup caseContractGroup = groupContractGroup.getContractGroup();

            Case stagingCase = Case.findByStagingFK_CaseNumber(staging.getStagingPK(), caseContractGroup.getContractGroupNumber(), DATABASE);
            if (stagingCase != null)
            {
                stagingContext.setCurrentCase(stagingCase);
            }
            else
            {
                stagingContext.setContractGroupType("Case");
                caseContractGroup.stage(stagingContext, DATABASE);
            }

            group.BatchContractSetup bcs = group.BatchContractSetup.findByPK(segment.getBatchContractSetupFK());
            group.Enrollment enrollment = bcs.getEnrollment();

            Enrollment stagingEnrollment = Enrollment.findByCaseFK_BegPolDate(stagingContext.getCurrentCase().getCasePK(), enrollment.getBeginningPolicyDate(), DATABASE);
            if (stagingEnrollment == null)
            {
                enrollment.stage(stagingContext, DATABASE);

                group.EnrollmentLeadServiceAgent[] enrollLeadServAgts = group.EnrollmentLeadServiceAgent.findBy_EnrollmentPK(enrollment.getEnrollmentPK());

                for (int i = 0; i < enrollLeadServAgts.length; i++)
                {
                    enrollLeadServAgts[i].stage(stagingContext, DATABASE);
                }
            }

            Group stagingGroup = Group.findByCaseFK_ContractGroupNumber(stagingContext.getCurrentCase().getCasePK(), groupContractGroup.getContractGroupNumber(), DATABASE);
            if (stagingGroup != null)
            {
                stagingContext.setCurrentGroup(stagingGroup);
            }
            else
            {
                stagingContext.setContractGroupType("Group");
                groupContractGroup.stage(stagingContext, DATABASE);
            }
        }

        stagingContext.setSegmentType("Base");
        segment.stage(stagingContext, DATABASE);

        if (naturalDocVO.getRiderSegmentVOCount() > 0)
        {
            RiderSegmentVO[] riderSegmentVOs = naturalDocVO.getRiderSegmentVO();
            for (int i = 0; i < riderSegmentVOs.length; i++)
            {
                stagingContext.setSegmentType("Rider");
                Segment rider = new Segment(riderSegmentVOs[i].getSegmentVO());

                rider.stage(stagingContext, DATABASE);

//                Set<contract.ContractClient> contractClients = rider.getContractClients();
                contract.ContractClient[] cContractClients = contract.ContractClient.findBy_SegmentFK(rider.getSegmentPK());

                for (int j = 0; j < cContractClients.length; j++)
                {
                    cContractClients[j].stage(stagingContext, DATABASE);
                }
            }
        }

        ClientVO[] clientVOs = naturalDocVO.getBaseSegmentVO().getClientVO();

        for (int i = 0; i < clientVOs.length; i++)
        {
            if (clientVOs[i].getContractClientVOCount() > 0)
            {
                contract.ContractClient contractClient = contract.ContractClient.findByPK(clientVOs[i].getContractClientVO(0).getContractClientPK());
                contractClient.stage(stagingContext, DATABASE);
            }
            else
            {
                stagingContext.setCurrentRoleType(clientVOs[i].getRoleTypeCT());
                ClientDetail clientDetail = new ClientDetail(clientVOs[i].getClientDetailVO(0));
                clientDetail.stage(stagingContext, DATABASE);
            }
        }

//        RiderSegmentVO[] riderSegmentVOs = naturalDocVO.getRiderSegmentVO();
//
//        for (int i = 0; i < riderSegmentVOs.length; i++)
//        {
//            RiderSegmentVO riderSegmentVO = riderSegmentVOs[i];
//
//            if (riderSegmentVO.getClientVOCount() > 0)
//            {
//                // There would be only one Client for Rider.
//                ClientVO riderClientVO = riderSegmentVO.getClientVO(0);
//
//                ContractClientVO contractClientVO = riderClientVO.getContractClientVO(0);
//
//                contract.ContractClient contractClient = contract.ContractClient.findByPK(contractClientVO.getContractClientPK());
//
//                contractClient.stage(stagingContext, DATABASE);
//            }
//        }

        GroupSetupVO[] groupSetupVOs = naturalDocVO.getGroupSetupVO();
        EDITTrx statementTrx = null;
        for (int i = 0; i < groupSetupVOs.length; i++)
        {
            EDITTrxVO editTrxVO = groupSetupVOs[i].getContractSetupVO(0).getClientSetupVO(0).getEDITTrxVO(0);

            EDITTrx editTrx = new EDITTrx(editTrxVO);
            editTrx.stage(stagingContext, DATABASE);
            
            if (staging.getCorrespondenceType().equalsIgnoreCase("Stmt") && editTrx.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_STATEMENT)) {
            	statementTrx = editTrx;
            }
        }

        ProjectionsVO[] projectionsVO = correspondenceDetailVO.getProjectionsVO();
        if (projectionsVO != null)
        {
            for (int i = 0; i < projectionsVO.length; i++)
            {
                ProjectionArray projectionArray = new ProjectionArray(projectionsVO[i]);
                projectionArray.stage(stagingContext, DATABASE);
            }
        }

        CalculatedValuesVO[] calculatedValuesVO = correspondenceDetailVO.getCalculatedValuesVO();
        if (calculatedValuesVO != null)
        {
            for (int i = 0; i < calculatedValuesVO.length; i++)
            {
                CalculatedValues calculatedValues = new CalculatedValues(calculatedValuesVO[i]);
                calculatedValues.stage(stagingContext, DATABASE);
            }
        }

        GIOOptionValueVO[] gioOptionValueVOs = correspondenceDetailVO.getGIOOptionValueVO();

        for (int i = 0; i < gioOptionValueVOs.length; i++)
        {
            GIOOptionValueVO gioOptionValueVO = gioOptionValueVOs[i];

            GIOOptionValue gioOptionValue = (GIOOptionValue) SessionHelper.mapVOToHibernateEntity(gioOptionValueVO, SessionHelper.STAGING);

            gioOptionValue.setSegmentBase(stagingContext.getCurrentSegmentBase());

            SessionHelper.saveOrUpdate(gioOptionValue, SessionHelper.STAGING);
        }

        contract.PremiumDue[] premiumDues = contract.PremiumDue.findBySegmentPK_OrderByPK(segment.getSegmentPK());

        for (int i = 0; i < premiumDues.length; i++)
        {
            contract.PremiumDue premiumDue = premiumDues[i];

            premiumDue.stage(stagingContext, DATABASE);

            Set<contract.CommissionPhase> commissionPhases = premiumDue.getCommissionPhases();

            for (contract.CommissionPhase commissionPhase : commissionPhases)
            {
                commissionPhase.stage(stagingContext, DATABASE);
            }
        }
        
        // Include current agent information
        if (stagingContext.getCurrentSegmentBase().getAgents().isEmpty())
        {
            group.EnrollmentLeadServiceAgent[] enrollmentLeadServiceAgents = group.EnrollmentLeadServiceAgent.findBy_Segment_EffectiveDate(segment, new EDITDate()); 

            ClientRoleVO[] clientRoleVOs = new ClientRoleDAO().findByClientRolePK(enrollmentLeadServiceAgents[0].getClientRoleFK(), true, null);
            ClientRoleVO clientRole = clientRoleVOs[0];

            agent.Agent currentAgent = agent.Agent.findBy_PK(clientRole.getAgentFK());
            
            String currentAgentId = clientRole.getReferenceID();
            stagingContext.setCurrentAgentNumber(currentAgentId);
            
            currentAgent.stage(stagingContext, DATABASE);
        }
        
        // calculate values for UL annual statements (ST transactionType) if applicable
        if (statementTrx != null) {
        	
        	staging.AnnualStatement annualStatement = calculateAnnualStatementValues(segment, statementTrx);
        	annualStatement.setSegmentBase(stagingContext.getCurrentSegmentBase());
        	
        	stagingContext.getCurrentSegmentBase().addAnnualStatement(annualStatement);
            stagingContext.setCurrentAnnualStatement(annualStatement);
            SessionHelper.saveOrUpdate(annualStatement, DATABASE);
        }
        
        SessionHelper.saveOrUpdate(stagingContext.getStaging(), DATABASE);

        SessionHelper.commitTransaction(DATABASE);
			
			isStaged = true;
    }
		else
		{
			isStaged = true;
		}

        SessionHelper.clearSession(SessionHelper.STAGING);
		
		return isStaged;
    }
	
	private staging.AnnualStatement calculateAnnualStatementValues(Segment segment, EDITTrx editTrx) {
		
		EDITDate startDate = editTrx.getEffectiveDate().subtractYears(1);
		EDITDate endDate = editTrx.getEffectiveDate();
		
		EDITBigDecimal accumulationValueAtStartDate = new EDITBigDecimal();
		EDITBigDecimal surrenderValueAtStartDate = new EDITBigDecimal();
		if (!startDate.equals(segment.getEffectiveDate())) {
			FinancialHistory[] startDateFinancialHistoriesMI = FinancialHistory.findFinancialHistory_ByTrxType(segment.getSegmentPK(), startDate, "MI");
			if (startDateFinancialHistoriesMI != null && startDateFinancialHistoriesMI.length > 0) {
				accumulationValueAtStartDate = startDateFinancialHistoriesMI[0].getAccumulatedValue();
				surrenderValueAtStartDate = startDateFinancialHistoriesMI[0].getSurrenderValue();
			}
		}
		
		EDITBigDecimal faceAmountAtStartDate = new EDITBigDecimal();
		FinancialHistory[] startDateFinancialHistoriesMV = FinancialHistory.findFinancialHistory_ByTrxType(segment.getSegmentPK(), startDate, "MV");
		if (startDateFinancialHistoriesMV != null && startDateFinancialHistoriesMV.length > 0) {
			faceAmountAtStartDate = startDateFinancialHistoriesMV[0].getPrevTotalFaceAmount();
		}
		
		EDITBigDecimal accumulationValueAtEndDate = new EDITBigDecimal();
		EDITBigDecimal surrenderChargeAtEndDate = new EDITBigDecimal();
		EDITBigDecimal surrenderValueAtEndDate = new EDITBigDecimal();
		EDITBigDecimal deathBenefitAtEndDate = new EDITBigDecimal();
		FinancialHistory[] endDateFinancialHistoriesMI = FinancialHistory.findFinancialHistory_ByTrxType(segment.getSegmentPK(), endDate, "MI");
		if (endDateFinancialHistoriesMI != null && endDateFinancialHistoriesMI.length > 0) {
			accumulationValueAtEndDate = endDateFinancialHistoriesMI[0].getAccumulatedValue();
			surrenderChargeAtEndDate = endDateFinancialHistoriesMI[0].getSurrenderCharge();
			deathBenefitAtEndDate = endDateFinancialHistoriesMI[0].getCurrentDeathBenefit();
			surrenderValueAtEndDate = endDateFinancialHistoriesMI[0].getSurrenderValue();
			
			contract.Bucket[] buckets = contract.Bucket.findBucket_ByTrxType(segment.getSegmentPK(), endDate, "LC");
			if (buckets != null && buckets.length > 0) {
				EDITBigDecimal loanPrincipalRemaining = new EDITBigDecimal();
				for (contract.Bucket bucket : buckets) {
					loanPrincipalRemaining = loanPrincipalRemaining.addEditBigDecimal(bucket.getLoanPrincipalRemaining());
				}
				
				deathBenefitAtEndDate = deathBenefitAtEndDate.subtractEditBigDecimal(loanPrincipalRemaining);
			}
		}
		
		EDITBigDecimal faceAmountAtEndDate = new EDITBigDecimal();
		FinancialHistory[] endDateFinancialHistoriesMV = FinancialHistory.findFinancialHistory_ByTrxType(segment.getSegmentPK(), endDate, "MV");
		if (endDateFinancialHistoriesMV != null && endDateFinancialHistoriesMV.length > 0) {
			faceAmountAtEndDate = endDateFinancialHistoriesMV[0].getPrevTotalFaceAmount();
		}
		
		List<String> transactionTypes = new ArrayList<>();
		transactionTypes.add("PY");
		transactionTypes.add("WP");
		transactionTypes.add("PW");
		transactionTypes.add("WMD");
		
		EDITDate sumStartDate = null;
		if (startDate.equals(segment.getEffectiveDate())) {
			sumStartDate = startDate.subtractYears(1);
		} else {
			sumStartDate = startDate.addDays(1);
		}

		EDITBigDecimal sumOfPremiumsPaid = FinancialHistory.sumTransaction_MultipleTrxTypes(segment.getSegmentPK(), sumStartDate, endDate, "GrossAmount", transactionTypes);
		
		transactionTypes.clear();
		transactionTypes.add("LO");
		EDITBigDecimal sumOfLoans = FinancialHistory.sumTransaction_MultipleTrxTypes(segment.getSegmentPK(), startDate.addDays(1), endDate, "GrossAmount", transactionTypes);

		transactionTypes.clear();
		transactionTypes.add("LR");
		EDITBigDecimal sumOfLoanRepayments = FinancialHistory.sumTransaction_MultipleTrxTypes(segment.getSegmentPK(), startDate.addDays(1), endDate, "GrossAmount", transactionTypes);

		transactionTypes.clear();
		transactionTypes.add("WI");
		EDITBigDecimal sumOfWithdrawals = FinancialHistory.sumTransaction_MultipleTrxTypes(segment.getSegmentPK(), startDate.addDays(1), endDate, "GrossAmount", transactionTypes);

		EDITBigDecimal sumOfRiderCOI = FinancialHistory.sumCharge_ByChargeType(segment.getSegmentPK(), startDate, endDate.subtractDays(1), "RiderCOI");
		EDITBigDecimal sumOfCOI = FinancialHistory.sumCharge_ByChargeType(segment.getSegmentPK(), startDate, endDate.subtractDays(1), "BaseCOI");
		
		EDITBigDecimal loanInterestAtEndDate = new EDITBigDecimal();
		BucketHistory[] bucketHistoryForLC = BucketHistory.findBucketHistory_ByTrxTypeEffectiveDate(segment.getSegmentPK(), endDate, "LC");
		if (bucketHistoryForLC != null && bucketHistoryForLC.length > 0) {
			loanInterestAtEndDate = bucketHistoryForLC[0].getLoanInterestDollars();
		}
		
		AnnualStatement annualStatement = new AnnualStatement();
		annualStatement.setStartDate(startDate);
		annualStatement.setEndDate(endDate);
		annualStatement.setAccumulationValueAtStartDate(accumulationValueAtStartDate);
		annualStatement.setSurrenderValueAtStartDate(surrenderValueAtStartDate);
		annualStatement.setSurrenderValueAtEndDate(surrenderValueAtEndDate);
		annualStatement.setLoanedValueAtStartDate(BucketHistory.findCumLoanDollarsSum_ByTrxType(segment.getSegmentPK(), startDate, "LC"));
		annualStatement.setFaceAmountAtStartDate(faceAmountAtStartDate);
		annualStatement.setAccumulationValueAtEndDate(accumulationValueAtEndDate);
		annualStatement.setLoanInterestAccruedAtEndDate(loanInterestAtEndDate);
		annualStatement.setSurrenderChargeAtEndDate(surrenderChargeAtEndDate);
		annualStatement.setDeathBenefitAtEndDate(deathBenefitAtEndDate);
		annualStatement.setFaceAmountAtEndDate(faceAmountAtEndDate);
		annualStatement.setSumOfPremiumsPaid(sumOfPremiumsPaid);
		annualStatement.setSumOfLoans(sumOfLoans);
		annualStatement.setSumOfLoanRepayments(sumOfLoanRepayments);
		annualStatement.setSumOfWithdrawals(sumOfWithdrawals);
		annualStatement.setSumOfRiderCOI(sumOfRiderCOI);
		annualStatement.setSumOfCOI(sumOfCOI);	
		
		annualStatement = calculateAnnualStatementMonthlySummaries(segment, annualStatement);
		
		return annualStatement;
	}
	
	 private staging.AnnualStatement calculateAnnualStatementMonthlySummaries(Segment segment, staging.AnnualStatement annualStatement) {
		
		 List<String> transactionTypes = new ArrayList<>();
		 FinancialHistory[] endDateFinancialHistoriesMI = null;
		 EDITDate sumStartDate = null;
		 EDITBigDecimal accumulationValueAtEndDate = null;
		 EDITBigDecimal sumOfPremiumsPaid = null;
		 EDITBigDecimal sumOfPremiumsPaidNet = null;
		 EDITBigDecimal sumOfWithdrawals = null;
		 EDITBigDecimal interestEarned = null;
		 EDITBigDecimal sumOfRiderCOI = null;
		 EDITBigDecimal sumOfCOI = null;
		 EDITBigDecimal sumOfAdminFees = null;
		 EDITBigDecimal sumOfPerThousandFee = null;
		 EDITBigDecimal currIntRate = null;
		 EDITBigDecimal sumOfLoans = null;
		 
		 EDITDate finalEndDate = annualStatement.getEndDate();
		 EDITDate currentEndDate = annualStatement.getStartDate().addMonths(1);
		 
		 while(currentEndDate.beforeOREqual(finalEndDate)) {
			 
			 accumulationValueAtEndDate = new EDITBigDecimal();
			 currIntRate = new EDITBigDecimal();
			 endDateFinancialHistoriesMI = FinancialHistory.findFinancialHistory_ByTrxType(segment.getSegmentPK(), currentEndDate, "MI");
			 if (endDateFinancialHistoriesMI != null && endDateFinancialHistoriesMI.length > 0) {
				accumulationValueAtEndDate = endDateFinancialHistoriesMI[0].getAccumulatedValue();
				currIntRate = endDateFinancialHistoriesMI[0].getCurrIntRate();
			 }
			 
			 transactionTypes.clear();
			 transactionTypes.add("PY");
			 transactionTypes.add("WP");
			 transactionTypes.add("PW");
			 transactionTypes.add("WMD");
				
			 if (currentEndDate.subtractMonths(1).equals(segment.getEffectiveDate())) {
				sumStartDate = currentEndDate.subtractMonths(1).subtractYears(1);
			 } else {
				sumStartDate = currentEndDate.subtractMonths(1).addDays(1);
			 }
			
			 sumOfPremiumsPaid = FinancialHistory.sumTransaction_MultipleTrxTypes(segment.getSegmentPK(), sumStartDate, currentEndDate, "GrossAmount", transactionTypes);
			 sumOfPremiumsPaidNet = FinancialHistory.sumTransaction_MultipleTrxTypes(segment.getSegmentPK(), sumStartDate, currentEndDate, "NetAmount", transactionTypes);

			 transactionTypes.clear();
			 transactionTypes.add("WI");
			 sumOfWithdrawals = FinancialHistory.sumTransaction_MultipleTrxTypes(segment.getSegmentPK(), currentEndDate.subtractMonths(1).addDays(1), 
					 currentEndDate, "GrossAmount", transactionTypes);

			 transactionTypes.clear();
			 transactionTypes.add("MI");
			 interestEarned = FinancialHistory.sumTransaction_MultipleTrxTypes(segment.getSegmentPK(), currentEndDate.subtractMonths(1).addDays(1), 
					 currentEndDate, "GrossAmount", transactionTypes);

			 transactionTypes.clear();
			 transactionTypes.add("LO");
			 sumOfLoans = FinancialHistory.sumTransaction_MultipleTrxTypes(segment.getSegmentPK(), currentEndDate.subtractMonths(1).addDays(1), 
					 currentEndDate, "GrossAmount", transactionTypes);

			 transactionTypes.clear();
			 transactionTypes.add("LR");
			 EDITBigDecimal sumOfLoanRepayments = FinancialHistory.sumTransaction_MultipleTrxTypes(segment.getSegmentPK(), currentEndDate.subtractMonths(1).addDays(1), 
					 currentEndDate, "GrossAmount", transactionTypes);

			 sumOfRiderCOI = FinancialHistory.sumCharge_ByChargeType(segment.getSegmentPK(), currentEndDate.subtractMonths(1), 
					 currentEndDate.subtractDays(1), "RiderCOI");
				
			 sumOfCOI = FinancialHistory.sumCharge_ByChargeType(segment.getSegmentPK(), currentEndDate.subtractMonths(1), 
					 currentEndDate.subtractDays(1), "BaseCOI");

			 sumOfAdminFees = FinancialHistory.sumCharge_ByChargeType(segment.getSegmentPK(), currentEndDate.subtractMonths(1), 
					 currentEndDate.subtractDays(1), "AdminExp");
			 
			 staging.AnnualStatementMonthlySummary monthlySummary = new staging.AnnualStatementMonthlySummary();
			 monthlySummary.setEndDate(currentEndDate);
			 monthlySummary.setAccumulationValueAtEndDate(accumulationValueAtEndDate);
			 monthlySummary.setSumOfPremiumsPaid(sumOfPremiumsPaid);
			 monthlySummary.setSumOfWithdrawals(sumOfWithdrawals);
			 monthlySummary.setInterestEarned(interestEarned);
			 monthlySummary.setBaseCOI(sumOfCOI);
			 monthlySummary.setRiderCOI(sumOfRiderCOI);
			 monthlySummary.setMortalityChargeAndRiderCOI(sumOfRiderCOI.addEditBigDecimal(sumOfCOI));
			 monthlySummary.setSumOfAdminFees(sumOfAdminFees);
			 monthlySummary.setInterestRate(currIntRate);
			 monthlySummary.setSumOfLoans(sumOfLoans);
			 monthlySummary.setSumOfLoanRepayments(sumOfLoanRepayments);
			 monthlySummary.setSumOfPremiumLoad(sumOfPremiumsPaid.subtractEditBigDecimal(sumOfPremiumsPaidNet));
			 monthlySummary.setAnnualStatement(annualStatement);
			 
			 annualStatement.addAnnualStatementMonthlySummary(monthlySummary);
			 
			 currentEndDate = currentEndDate.addMonths(1);
		 }
		 
		 return annualStatement;
	}
}
