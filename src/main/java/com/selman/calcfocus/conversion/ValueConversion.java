package com.selman.calcfocus.conversion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.selman.calcfocus.request.PaymentMode;
import com.selman.calcfocus.util.BadDataException;

public class ValueConversion {
	
	/*
	 * HCV ***G0G*** M40 ***G2G*** M50 ***G3G*** M80 ***G4G*** M100 ***G5G*** SPL
	 * ***G6G*** XCV ***G7G*** (NULL) ***G9G***
	 */
	public static String getLegacyPlanCode(String ratedGender) {
		if (ratedGender == null) {
			return "***G7G***";
		}
		String legacyPlanCode = null;
		switch (ratedGender) {
		case "HCV":
			legacyPlanCode = "***G0G***";
			break;
		case "M40":
			legacyPlanCode = "***G2G***";
			break;
		case "M50":
			legacyPlanCode = "***G3G***";
			break;
		case "M80":
			legacyPlanCode = "***G4G***";
			break;
		case "M100":
			legacyPlanCode = "***G5G***";
			break;
		case "SPL":
			legacyPlanCode = "***G6G***";
			break;
		case "XCV":
			legacyPlanCode = "***G7G***";
			break;
		default:
			legacyPlanCode = "***G9G***";
		}
		return legacyPlanCode;

	}

	public static String getTransactionRequestType(String transactionType, String complexChangeType) {
		String translatedType = null;
		switch (transactionType) {
		case "SB":
			translatedType = "Activate";
			break;
		case "IS":
			translatedType = "Activate";
			break;
		case "MV":
			translatedType = "Monthaversary";
			break;
		case "PY":
			translatedType = "Premium";
			break;
		case "LO":
			translatedType = "Loan";
			break;
		case "LR":
			translatedType = "LoanRepayment";
			break;
		case "WI":
			translatedType = "PartialSurrender";
			break;
		case "CC":
			translatedType = complexChangeType;
			/*
			 * if (complexChangeType.equals("DBOption")) { translatedType =
			 * "PartialSurrender"; } else if (complexChangeType.equals("Rateclass")) {
			 * translatedType = "PartialSurrender"; } else if
			 * (complexChangeType.equals("ClaimStop")) { translatedType = "ClaimStop"; }
			 */
			break;
		case "MA":
			translatedType = "Maturity";
			break;
		default:
			translatedType = null;
		}
		return translatedType;
	}
	
	/*
	 * if ComplexChangeType = 'FaceDecrease' or OptionCodeCT = 'ULIncrease' or 'FI' then
	 * use originalUnits
	 * If ComplexChangeType is a 'RiderChange' but not an Increase
	 * 
	 */

	public static Double calculateFaceValue(String optionCodeCT, BigDecimal originalUnits, BigDecimal units,
			String transactionTypeCT, String complexChangeType, boolean isDrivingSegment) {
		BigDecimal faceValue = new BigDecimal(0.00);
		if (transactionTypeCT.equals("CC") && ((complexChangeType != null) && complexChangeType.equals("FaceDecrease"))
				&& (optionCodeCT.equals("UL") || optionCodeCT.equals("ULIncrease") || optionCodeCT.equals("FI"))) {
			faceValue = originalUnits.multiply(new BigDecimal(1000));

		} else {
			if (transactionTypeCT.equals("CC") && complexChangeType.equals("RiderChange")
					&& (!optionCodeCT.equals("UL") && !optionCodeCT.equals("ULIncrease") && !optionCodeCT.equals("FI"))
					&& isDrivingSegment) {
				faceValue = originalUnits.multiply(new BigDecimal(1000));
			} else {

				faceValue = units.multiply(new BigDecimal(1000));
			}

		}
		return faceValue.doubleValue();
	}

	public static Double calculateModelPremium(String billType, Date effectiveDate, Date premiumDueEffectiveDate,
			Double premiumDueDeductionAmount, Double premiumDueBillAmount) {
		Double deductionAmount = 0.00;
		// adding a year to effectiveDate for comparison to premiumDueEffective dates, since we want future PDs - per Carrie
	    int year = effectiveDate.getYear();
	    year =+ year + 1;
	    effectiveDate.setYear(year);
		if (premiumDueEffectiveDate.before(effectiveDate) || premiumDueEffectiveDate.equals(effectiveDate)) {
			if (billType.equals("GRP")) {
				deductionAmount = premiumDueDeductionAmount;
			} else if (billType.equals("INDIV")) {
				deductionAmount = premiumDueBillAmount;
			}
		}
		return deductionAmount;
	}

	/*
	 * public static PaymentMode convertToPaymentMode(BillScheduleVO billScheduleVO)
	 * { return convertToPaymentMode(billScheduleVO.getBillingModeCT(),
	 * billScheduleVO.getDeductionFrequencyCT()); }
	 */

	public static PaymentMode convertToPaymentMode(String billingMode, String deductionFrequencyString) {

		// Error check
		List<String> badDataMessages = new ArrayList<>();
		if (billingMode == null) {
			badDataMessages.add("Missing billing Mode");
		}
		if (badDataMessages.size() > 0) {
			throw new BadDataException(badDataMessages);
		}

		String translatedCode = null;
		// For direct bill
		// Annual => A
		// SemiAnn => S
		// Quart => Q
		// All others =>M
		if (deductionFrequencyString == null) {
			switch (billingMode) {
			case "Annual":
				translatedCode = "Annual";
			case "Quart":
				translatedCode = "Quarterly";
				break;
			case "SemiAnn":
				translatedCode = "SemiAnnual";
				break;
			default:
				translatedCode = "Monthly";
			}
		} else {

			int deductionFrequency = Integer.parseInt(deductionFrequencyString);
			switch (billingMode) {
			case "13thly":
				switch (deductionFrequency) {
				case 13:
					translatedCode = "FourWeekly";
					break;
				case 26:
					translatedCode = "Biweekly";
					break;
				case 52:
					translatedCode = "Weekly";
					break;
				}
				break;
			case "Annual":
				translatedCode = "Annual";
				break;
			case "Month":
				switch (deductionFrequency) {
				case 9:
					translatedCode = "Ninethly";
					break;
				case 10:
					translatedCode = "Tenthly";
					break;
				case 12:
					translatedCode = "Monthly";
					break;
				case 18:
					translatedCode = "NinethlySemi";
					break;
				case 20:
					translatedCode = "TenthlySemi";
					break;
				case 24:
					translatedCode = "Semimonthly";
					break;
				case 36:
					translatedCode = "NinethlyFourTime";
					break;
				case 40:
					translatedCode = "TenthlyFourTime";
					break;
				case 48:
					translatedCode = "FortyEightAYear";
					break;
				default:
					translatedCode = "Monthly";
					break;
				}
				break;
			case "Quart":
				translatedCode = "Quarterly";
				break;
			case "SemiAnn":
				translatedCode = "SemiAnnual";
				break;
			case "VarMonth":
				translatedCode = deductionFrequency == 26 ? "Biweekly" : "Weekly";
				break;
			default:
				translatedCode = "";
			}
		}

		return PaymentMode.fromValue(translatedCode);

	}

}
