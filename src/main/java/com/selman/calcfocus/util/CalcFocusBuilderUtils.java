package com.selman.calcfocus.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import com.selman.calcfocus.request.Address;
import com.selman.calcfocus.request.AddressAssociation;
import com.selman.calcfocus.request.AddressType;
import com.selman.calcfocus.request.Gender;
import com.selman.calcfocus.request.Party;
import com.selman.calcfocus.request.PointInTime;
import com.selman.calcfocus.request.RoleType;
import com.selman.calcfocus.request.TobaccoUse;

import contract.CommissionPhase;
import contract.PremiumDue;
import edit.common.EDITDate;
import edit.common.vo.ClientAddressVO;
import edit.common.vo.ClientDetailVO;
import edit.common.vo.ClientRoleVO;
import edit.common.vo.ContractClientVO;
import event.EDITTrx;

public class CalcFocusBuilderUtils {
	
	/*
	 * Let sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce = MAPAccum. First of
	 * all, I assume we can ignore this field or let it be passed as zero if
	 * Life.MapEndDate <= pointInTime. They might actually prefer high values if
	 * Life.MapEndDate <= pointInTime.
	 * 
	 * When Life.MapEndDate > pointInTime, the steps to find this value are: 1) Find
	 * the most recent PremiumDue record with PremiumDue.EffectiveDate on or prior
	 * to the pointInTime. This logic should be similar to other logic out there to
	 * find PremiumDue records. Do not include those with
	 * PremiumDue.PendingExtractIndicator = R. If there are multiple on the given
	 * effective date, sort those by PremiumDuePK and pick the largest (most
	 * recent.) 2) Gather the CommissionPhase records associated with this
	 * PremiumDue record. 3) In particular, we need EffectiveDate,
	 * ExpectedMonthlyPremium and PrevCumExpectedMonthlyPremium from these records.
	 * 4) Discard any for which CommissionPhase.EffectiveDate >
	 * PremiumDue.EffectiveDate. 5) For all others, let MAPAccum = sum over the
	 * CommissionPhases for: PrevCumExpectedMonthlyPremium + (ExpectedMonthlyPremium
	 * x # of full months between PremiumDue.EffectiveDate and pointInTime.)
	 * 
	 * Please let me know if this raises further questions.
	 * 
	 */
	public static Double getSumOfPremiumRequiredToKeepNoLapseGuaranteeInforce(PremiumDue premiumDue,
			Date pointInTimeEffectiveDate) {
		Set<CommissionPhase> commissionPhases = premiumDue.getCommissionPhases();
		int elapsedMonths = premiumDue.getEffectiveDate()
				.getElapsedMonths(new EDITDate(pointInTimeEffectiveDate.getTime()));
		Double sum = new Double(0.00);
		for (CommissionPhase commissionPhase : commissionPhases) {
			if (commissionPhase.getEffectiveDate().beforeOREqual(premiumDue.getEffectiveDate())) {
				sum = sum + commissionPhase.getPrevCumExpectedMonthlyPrem().doubleValue()
						+ commissionPhase.getExpectedMonthlyPremium()
								.multiplyEditBigDecimal(BigDecimal.valueOf(elapsedMonths)).doubleValue();

			}
		}

		return sum;
	}
	

	public static RoleType getRoleType(String role) {
		if (role.equals("Insured")) {
			return RoleType.PRIMARY_INSURED;
		} else if (role.equals("OWN")) {
			return RoleType.OWNER;
		} else if (role.equals("POR")) {
			return RoleType.PAYOR;
		} else if (role.equals("AgentServicing")) {
			return RoleType.AGENT;
		}
		return null;
	}

	public static Party getParty(ClientRoleVO clientRoleVO) throws DatatypeConfigurationException {
		Party party = new Party();
		party.setGUID(Long.toString(clientRoleVO.getClientRolePK()));
		ClientDetailVO clientDetailVO = (ClientDetailVO) clientRoleVO.getParentVO(ClientDetailVO.class);

		EDITDate dob = new EDITDate(clientDetailVO.getBirthDate());
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(new Date(dob.getTimeInMilliseconds()));
		party.setDateOfBirth(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
		party.setFirstName(clientDetailVO.getFirstName());
		party.setLastName(clientDetailVO.getLastName());
		party.setGender(Gender.fromValue(clientDetailVO.getGenderCT()));
		party.getAddressAssociation().addAll(CalcFocusBuilderUtils.getAddressAssociation(clientDetailVO));
		return party;
	}

	public static List<AddressAssociation> getAddressAssociation(ClientDetailVO clientDetailVO) {
		List<AddressAssociation> addressAssociations = new ArrayList<>();
		ClientAddressVO[] clientAddressVOs = clientDetailVO.getClientAddressVO();
		for (ClientAddressVO clientAddressVO : clientAddressVOs) {
			AddressAssociation addressAssociation = new AddressAssociation();
			addressAssociation.setAddressGUID(Long.toString(clientAddressVO.getClientAddressPK()));
			if (clientAddressVO.getAddressTypeCT().equals("Mailing")) {
				addressAssociation.setAddressType(AddressType.MAILING);
			} else if (clientAddressVO.getAddressTypeCT().equals("Residence")) {
				addressAssociation.setAddressType(AddressType.RESIDENCE);
			} else if (clientAddressVO.getAddressTypeCT().equals("PrimaryAddress")) {
				addressAssociation.setAddressType(AddressType.MAILING);
			}
			if (addressAssociation.getAddressType() != null) {
				addressAssociations.add(addressAssociation);
			}
		}
		return addressAssociations;
	}

	public static Address getAddress(ClientRoleVO clientRoleVO) throws DatatypeConfigurationException {
		// get Party from Owner
		ClientDetailVO clientDetailVO = (ClientDetailVO) clientRoleVO.getParentVO(ClientDetailVO.class);
		ClientAddressVO clientAddressVO = clientDetailVO.getClientAddressVO(0);
		Address address = new Address();
		address.setAddressGUID(Long.toString(clientAddressVO.getClientAddressPK()));
		address.setLine1("HIDDEN");
		address.setLine2("HIDDEN");
		address.setLine3("HIDDEN");
		address.setState(clientAddressVO.getStateCT());
		address.setZip(clientAddressVO.getZipCode());
		return address;

	}

	public static TobaccoUse getTobaccoUse(ContractClientVO contractClientVO) {
		if (contractClientVO.getClassCT() == null) {
			return TobaccoUse.SMOKER_NONDISTINCT;
		} else if (contractClientVO.getClassCT().equals("NonSmoker")) {
			return TobaccoUse.NONSMOKER;
		} else if (contractClientVO.getClassCT().equals("Smoker")) {
			return TobaccoUse.SMOKER;
		}
		return TobaccoUse.SMOKER_NONDISTINCT;
	}

}
