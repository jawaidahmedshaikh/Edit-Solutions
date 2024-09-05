package com.selman.calcfocus.correspondence.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import com.selman.calcfocus.conversion.ValueConversion;
import com.selman.calcfocus.request.Address;
import com.selman.calcfocus.request.AddressAssociation;
import com.selman.calcfocus.request.AddressType;
import com.selman.calcfocus.request.AnnualStatementAdminData;
import com.selman.calcfocus.request.BaseIndicator;
import com.selman.calcfocus.request.CalculateRequest;
import com.selman.calcfocus.request.Context;
import com.selman.calcfocus.request.Coverage;
import com.selman.calcfocus.request.DeathBenefitOption;
import com.selman.calcfocus.request.Fund;
import com.selman.calcfocus.request.FundSubtype;
import com.selman.calcfocus.request.FundType;
import com.selman.calcfocus.request.Gender;
import com.selman.calcfocus.request.Header;
import com.selman.calcfocus.request.LifetimeFinancialAdviceProjection;
import com.selman.calcfocus.request.OutputInstruction;
import com.selman.calcfocus.request.Party;
import com.selman.calcfocus.request.PointInTime;
import com.selman.calcfocus.request.Policy;
import com.selman.calcfocus.request.PolicyAdminStatus;
import com.selman.calcfocus.request.Relation;
import com.selman.calcfocus.request.ReportType;
import com.selman.calcfocus.request.Role;
import com.selman.calcfocus.request.RoleType;
import com.selman.calcfocus.request.TobaccoUse;
import com.selman.calcfocus.util.BadDataException;
import com.selman.calcfocus.util.CalcFocusUtils;

import oracle.sql.DATE;

public class CalcFocusCorrespondenceRequestBuilder implements RequestBuilder {

	Header header = new Header();
	Context context = new Context();
	Policy policy = new Policy();
	Party party = new Party();
	Address address = new Address();
	CalculateRequest req = new CalculateRequest();
	AnnualStatementAdminData annualStatementAdminData = new AnnualStatementAdminData();
	LifetimeFinancialAdviceProjection lifetimeFinancialAdviceProjection = new LifetimeFinancialAdviceProjection();
	XMLGregorianCalendar now = null;
	Date currentDate;

	public CalcFocusCorrespondenceRequestBuilder(PolicyAndBaseCoverageValues values) throws DatatypeConfigurationException {
		super();
		currentDate = new Date(System.currentTimeMillis());
	    GregorianCalendar gc = new GregorianCalendar();
	    gc.setTime(currentDate);
		now = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		setHeader(values);
		setContext(values);
		setPolicy(values);
		setParty(values);
		setAddress(values);
		for (CoverageValues cvs : values.getRiderCoverageValues()) {
			if (cvs.getPartyAddressValues() != null) {
		        setParty(cvs);
		        //setAddress(cvs);
			}
		}
	}

	@Override
	public void setHeader(PolicyAndBaseCoverageValues values) {
		header.setRefGUID(CalcFocusUtils.getUniqueRequestID(values.getContractNumber(), 
				values.getSegmentBasePK(), currentDate));
		header.setDateTime(now);
		header.setAsync(Boolean.TRUE);
		//header.setAsync(Boolean.FALSE);
		req.setHeader(header);
	}

	/*
	 * processDate is cycleDate. if process date is null, current datetime will be
	 * used. processType should come from ws.CFprocessType
	 */
	@Override
	public void setContext(PolicyAndBaseCoverageValues values) throws DatatypeConfigurationException {

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(values.getEffectiveDate());
		context.setEffectiveDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

		context.setProcessType(values.getCFprocessType());
		context.getReportName().add("Ledger");
		if (values.getReportType().equals("PDF")) {
			context.setReportType(ReportType.PDF);
		}
		context.setResultsWithReport(values.getResultsWithReport());

		req.setContext(context);

	}

	@Override
	public void setPolicy(PolicyAndBaseCoverageValues values) throws DatatypeConfigurationException, BadDataException {

		GregorianCalendar calendar = new GregorianCalendar();
		policy.setPolicyGUID(Long.toString(values.getSegmentBasePK()));
		String policyAdminStatus = values.getSegmentStatus();
		policy.setPolicyAdminStatus(PolicyAdminStatus.fromValue(policyAdminStatus));
		policy.setOutputInstruction(OutputInstruction.fromValue(values.getCFoutputInstruction()));

		policy.setPolicyNumber(values.getContractNumber());
		policy.setCompanyCode(values.getCompanyName());
		policy.setIssueState(values.getIssueState());
		policy.setGroupCode(values.getGroupNumber());
		policy.setGroupName(values.getCorporateName());
		policy.setGroupPolicyNumber(values.getMasterContractNumber());
		if (values.getMasterContractEffectiveDate() != null) {
			calendar.setTime(values.getMasterContractEffectiveDate());
			policy.setGroupPolicyEffectiveDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
		}
		// policy.setCarrierAdminSystem("VENUS");
		// TODO
		// need to setup translations
		// Not needed for GUL23 product. Might be needed for other products. Removing
		// for now.
		// policy.setGenderBlend(GenderBlend.EVEN);

		try {
		    policy.setPaymentMode(
				ValueConversion.convertToPaymentMode(values.getBillingMode(), values.getDeductionFrequency()));
		    policy.setModalPremium(ValueConversion.calculateModelPremium(values.getBillType(), values.getEffectiveDate(),
				values.getPremiumDueEffectiveDate(), values.getDeductionAmount(), values.getBillAmount()));
		} catch (BadDataException bda) {
			bda.setPolicy(policy);
			throw bda;
		}

		calendar.setTime(values.getEffectiveDate());
		policy.setEffectiveDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

		policy.getCoverage().add(getCoverage(values, true));
		policy.getCoverage().addAll(getRiderCoverages(values));
		req.getPolicy().add(policy);

	}

	private List<Coverage> getRiderCoverages(PolicyAndBaseCoverageValues values) throws DatatypeConfigurationException, BadDataException {
		List<Coverage> coverages = new ArrayList<>();
		GregorianCalendar calendar = new GregorianCalendar();
		for (RiderCoverageValues riderValues : values.getRiderCoverageValues()) {
			Coverage coverage = new Coverage();
			calendar.setTime(riderValues.getEffectiveDate());
			coverage.setEffectiveDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			coverage.setGUID(Long.toString(riderValues.getSegmentRiderPK()));
			coverage.setBaseIndicator(BaseIndicator.RIDER);
			coverage.setFaceAmount(riderValues.getFaceAmount());
			coverage.setAnnualPremium(riderValues.getAnnualPremium());
			if (riderValues.getTobaccoUse().equals("NonSmoker")) {
				coverage.setTobaccoUse(TobaccoUse.NONSMOKER);
			} else {
				coverage.setTobaccoUse(TobaccoUse.SMOKER);
			}
			coverage.setIssueAge(riderValues.getAgeAtIssue());
			coverage.setLegacyPlanCode(riderValues.getLegacyPlanCode());
			coverage = getRoles(coverage, riderValues);
			coverages.add(coverage);
		}
		return coverages;
	}

	private Coverage getCoverage(PolicyAndBaseCoverageValues values, boolean isBaseSegment)
			throws DatatypeConfigurationException {

		Coverage coverage = new Coverage();
		GregorianCalendar calendar = new GregorianCalendar();

		coverage.setGUID(Long.toString(values.getSegmentBasePK()));
		coverage.setFaceAmount(values.getFaceAmount());
		coverage.setProductCode(values.getProductCode());
//		planCode = segmentVO.getOptionCodeCT();
//		    coverage.setLegacyPlanCode(planCode);

		if (isBaseSegment) {
			coverage.setBaseIndicator(BaseIndicator.BASE);
		} else {
			coverage.setBaseIndicator(BaseIndicator.RIDER);
		}

		coverage.setIssueAge(values.getAgeAtIssue());

		if (values.getTobaccoUse() != null) {
			if (values.tobaccoUse.equals("NonSmoker")) {
				coverage.setTobaccoUse(TobaccoUse.NONSMOKER);
			} else {
				coverage.setTobaccoUse(TobaccoUse.SMOKER);
			}
		}

		// Is there only one lifeVO?
		if (isBaseSegment) {
			String deathBenefitOption = null;
			if (values.getDeathBenefitOption().toLowerCase().equals("level")) {
				deathBenefitOption = "Level";
			} else {
				deathBenefitOption = "Increasing";
			}
			coverage.setDeathBenefitOption(DeathBenefitOption.fromValue(deathBenefitOption));
		}

		calendar.setTime(values.getEffectiveDate());
		coverage.setEffectiveDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

		coverage = getRoles(coverage, values);

		if ((policy.getPolicyAdminStatus() != null) &&!policy.getPolicyAdminStatus().equals(PolicyAdminStatus.ACTIVE)) {
			PointInTime pointInTime = new PointInTime();

			// TODO
			pointInTime.setEffectiveDate(null);
			pointInTime.setCostBasis(0.00);

//			calendar.setTime(
//					new Date(new Date(vv.getBillScheduleVO().getNextBillDueDate()).getTimeInMilliseconds()));
			pointInTime.setPaidToDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

//			if (vv.isAllowMEC()) {
//				pointInTime.setIsMEC(YesNo.YES);
//			} else {
//				pointInTime.setIsMEC(YesNo.NO);
//			}

			// Do I get first LifeVO?
//			calendar.setTime(new Date(new Date(vv.getLifeVO().getTamraStartDate()).getTimeInMilliseconds()));
			pointInTime.setSevenPayStartDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

			// missing in vv
			pointInTime.setSumOfPremiumPaidTAMRA(0.00);
			pointInTime.setSumOfPremiumPaidDEFRA(0.00);

			/*
			 * pointInTime.setSumOfPremiumPaidTAMRADurationOne(vv.
			 * getSumOfPremiumPaidTAMRADurationOne());
			 * pointInTime.setSumOfPremiumPaidTAMRADurationTwo(vv.
			 * getSumOfPremiumPaidTAMRADurationTwo());
			 * pointInTime.setSumOfPremiumPaidTAMRADurationThree(vv.
			 * getSumOfPremiumPaidTAMRADurationThree());
			 * pointInTime.setSumOfPremiumPaidTAMRADurationFour(vv.
			 * getSumOfPremiumPaidTAMRADurationFour());
			 * pointInTime.setSumOfPremiumPaidTAMRADurationFive(vv.
			 * getSumOfPremiumPaidTAMRADurationFive());
			 * pointInTime.setSumOfPremiumPaidTAMRADurationSix(vv.
			 * getSumOfPremiumPaidTAMRADurationSix());
			 * pointInTime.setSumOfPremiumPaidTAMRADurationSeven(vv.
			 * getSumOfPremiumPaidTAMRADurationSeven());
			 */

//			pointInTime.setSevenPayPremium(vv.getLifeVO().getTamra().doubleValue());

			// LifeVO missing specified field - TamraInitAdjValue????
			pointInTime.setSevenPayInitialAdjustmentValue(0.00);
//			pointInTime.setGuidelineSinglePremium(vv.getLifeVO().getGuidelineSinglePremium().doubleValue());
//			pointInTime.setGuidelineLevelPremium(vv.getLifeVO().getGuidelineLevelPremium().doubleValue());
//			pointInTime.setLowestDeathBenefitDuringSevenPayPeriod(vv.getLifeVO().getFaceAmount().doubleValue());

			// TODO
			pointInTime.setUnnecessaryPremiumIndicator(false);
			pointInTime.setBenefitIncreaseIndicator(false);
			pointInTime.setSumOfPremiumPolicyholderPaidForNoLapseGuaranteeTest(0.0);

			// pointInTime.setSumOfPremiumRequiredToKeepNoLapseGuaranteeInforce(
			// vv.getSumOfPremiumRequiredToKeepNoLapseGuaranteeInforce());

			Fund fund = new Fund();
			fund.setType(FundType.FIXED);
			fund.setSubtype(FundSubtype.INVESTMENT);
			// fund.setPrincipal(vv.getPrincipal());
			;
			fund.setLastPrincipalChangeDate(pointInTime.getEffectiveDate());
			// fund.setCurrentYearUncapitalizedInterest(vv.getCurrentYearUncapitalizedInterest());
			// fund.setCurrentYearUncapitalizedCredit(vv.getCurrentYearUncapitalizedCredit());

			pointInTime.getFund().add(fund);
			policy.setPointInTime(pointInTime);
		}

		// CoverageUpdates coverageUpdates = new CoverageUpdates();

		return coverage;

	}

	public Coverage getRoles(Coverage coverage, CoverageValues values) {
		List<RoleValues> roleValues;
	    roleValues = values.getRoleValues();	

		for (RoleValues roleValue : roleValues) {
			Role role = new Role();
			role.setPartyGUID(Long.toString(roleValue.getSegmentBaseFK()));

			if (roleValue.getRole().equals("Insured")) {
				role.setRoleType(RoleType.PRIMARY_INSURED);
			} else if (roleValue.getRole().equals("TermInsured")) {
				role.setRoleType(RoleType.PRIMARY_INSURED);
			    role.setRelationToPrimaryInsured(Relation.PARTNER);;
			} else if (roleValue.getRole().equals("OWN")) {
				role.setRoleType(RoleType.OWNER);
			} else if (roleValue.getRole().equals("POR")) {
				role.setRoleType(RoleType.PAYOR);
			}
			coverage.getRole().add(role);
		}
		return coverage;

	}

	@Override
	public void setParty(CoverageValues values) throws DatatypeConfigurationException {
		GregorianCalendar calendar = new GregorianCalendar();
		Party party = new Party();
	    party.setGUID(Long.toString(values.getPartyAddressValues().getSegmentBaseFK()));
		party.setFirstName(values.getPartyAddressValues().getFirstName());
		party.setLastName(values.getPartyAddressValues().getLastName());
		if (values.getPartyAddressValues().getGender().equals("Female")) {
			party.setGender(Gender.FEMALE);
		} else {
			party.setGender(Gender.MALE);
		}
		calendar.setTime(values.getPartyAddressValues().getDob());
		party.setDateOfBirth(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
		if (values.getPartyAddressValues().getTobaccoUse() != null) {
			if (values.getPartyAddressValues().getTobaccoUse().equals("NonSmoker")) {
				party.setTobaccoUse(TobaccoUse.NONSMOKER);
			} else {
				party.setTobaccoUse(TobaccoUse.SMOKER);
			}
		}
		if (values instanceof PolicyAndBaseCoverageValues) {
		    //if (values.getPartyAddressValues().getSegmentBaseFK() != null) {
		        AddressAssociation addressAssociation = new AddressAssociation();
		        addressAssociation.setAddressType(AddressType.MAILING);
		        addressAssociation.setAddressGUID(Long.toString(values.getPartyAddressValues().getSegmentBaseFK()));
		        party.getAddressAssociation().add(addressAssociation);
		    //}
		} 

		req.getParty().add(party);

	}

	@Override
	public void setAddress(CoverageValues values) {
		Address address = new Address();
		address.setLine1(values.getPartyAddressValues().getLine1());
		address.setLine2(values.getPartyAddressValues().getLine2());
		address.setLine3(values.getPartyAddressValues().getLine3());
		address.setCity(values.getPartyAddressValues().getCity());
		address.setState(values.getPartyAddressValues().getState());
		address.setZip(values.getPartyAddressValues().getZip());
		address.setAddressGUID(Long.toString(values.getPartyAddressValues().getSegmentBaseFK()));

		req.getAddress().add(address);

	}

	@Override
	public void setAnnualStatementAdminData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLifetimeFinancialAdviceProjection() {
		// TODO Auto-generated method stub

	}

	public CalculateRequest getReq() {
		return req;
	}

	public void setReq(CalculateRequest req) {
		this.req = req;
	}

}
