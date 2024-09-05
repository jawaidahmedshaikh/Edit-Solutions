//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.02.16 at 02:52:13 PM EST 
//


package com.selman.calcfocus.response;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionNoteType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TransactionNoteType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PremiumRejectedDEFRA"/>
 *     &lt;enumeration value="PremiumReducedTAMRA"/>
 *     &lt;enumeration value="PremiumRejectedTAMRA"/>
 *     &lt;enumeration value="PolicyBecameMEC"/>
 *     &lt;enumeration value="UnnecessaryPremiumDetected"/>
 *     &lt;enumeration value="MaximumLoanExceeded"/>
 *     &lt;enumeration value="PolicyLapse"/>
 *     &lt;enumeration value="TAMRALookbackViolation"/>
 *     &lt;enumeration value="PremiumForcedInDEFRA"/>
 *     &lt;enumeration value="PremiumRejectedAdministrative"/>
 *     &lt;enumeration value="PolicyWasReinstated"/>
 *     &lt;enumeration value="PolicyOverloaned"/>
 *     &lt;enumeration value="PremiumForcedOutDEFRA"/>
 *     &lt;enumeration value="DBOChange"/>
 *     &lt;enumeration value="RiderTermination"/>
 *     &lt;enumeration value="PremiumHasChanged"/>
 *     &lt;enumeration value="LumpSumPremiumOccurred"/>
 *     &lt;enumeration value="LoanRepaymentOccurred"/>
 *     &lt;enumeration value="LoanRepaidInFull"/>
 *     &lt;enumeration value="LoanOccurred"/>
 *     &lt;enumeration value="PartialWithdrawalOccurred"/>
 *     &lt;enumeration value="PaymentModeHasChanged"/>
 *     &lt;enumeration value="BaseFaceUpdate"/>
 *     &lt;enumeration value="DividendOptionChanged"/>
 *     &lt;enumeration value="ScheduledFTRPremiumChange"/>
 *     &lt;enumeration value="UnderwritingChangeOccurred"/>
 *     &lt;enumeration value="PremiumPaidToEscapeGrace"/>
 *     &lt;enumeration value="LoanRepaymentToEscapeGrace"/>
 *     &lt;enumeration value="MaximumLoanExceeded"/>
 *     &lt;enumeration value="PartialWithdrawalNotPossible"/>
 *     &lt;enumeration value="PartialWithdrawalGreaterThanFace"/>
 *     &lt;enumeration value="LoanBelowMinimum"/>
 *     &lt;enumeration value="MonthaversaryPremiumRequested"/>
 *     &lt;enumeration value="Refund"/>
 *     &lt;enumeration value="FirstYearAnnualLoanInterest"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TransactionNoteType")
@XmlEnum
public enum TransactionNoteType {

    @XmlEnumValue("PremiumRejectedDEFRA")
    PREMIUM_REJECTED_DEFRA("PremiumRejectedDEFRA"),
    @XmlEnumValue("PremiumReducedTAMRA")
    PREMIUM_REDUCED_TAMRA("PremiumReducedTAMRA"),
    @XmlEnumValue("PremiumRejectedTAMRA")
    PREMIUM_REJECTED_TAMRA("PremiumRejectedTAMRA"),
    @XmlEnumValue("PolicyBecameMEC")
    POLICY_BECAME_MEC("PolicyBecameMEC"),
    @XmlEnumValue("UnnecessaryPremiumDetected")
    UNNECESSARY_PREMIUM_DETECTED("UnnecessaryPremiumDetected"),
    @XmlEnumValue("MaximumLoanExceeded")
    MAXIMUM_LOAN_EXCEEDED("MaximumLoanExceeded"),
    @XmlEnumValue("PolicyLapse")
    POLICY_LAPSE("PolicyLapse"),
    @XmlEnumValue("TAMRALookbackViolation")
    TAMRA_LOOKBACK_VIOLATION("TAMRALookbackViolation"),
    @XmlEnumValue("PremiumForcedInDEFRA")
    PREMIUM_FORCED_IN_DEFRA("PremiumForcedInDEFRA"),
    @XmlEnumValue("PremiumRejectedAdministrative")
    PREMIUM_REJECTED_ADMINISTRATIVE("PremiumRejectedAdministrative"),
    @XmlEnumValue("PolicyWasReinstated")
    POLICY_WAS_REINSTATED("PolicyWasReinstated"),
    @XmlEnumValue("PolicyOverloaned")
    POLICY_OVERLOANED("PolicyOverloaned"),
    @XmlEnumValue("PremiumForcedOutDEFRA")
    PREMIUM_FORCED_OUT_DEFRA("PremiumForcedOutDEFRA"),
    @XmlEnumValue("DBOChange")
    DBO_CHANGE("DBOChange"),
    @XmlEnumValue("RiderTermination")
    RIDER_TERMINATION("RiderTermination"),
    @XmlEnumValue("PremiumHasChanged")
    PREMIUM_HAS_CHANGED("PremiumHasChanged"),
    @XmlEnumValue("LumpSumPremiumOccurred")
    LUMP_SUM_PREMIUM_OCCURRED("LumpSumPremiumOccurred"),
    @XmlEnumValue("LoanRepaymentOccurred")
    LOAN_REPAYMENT_OCCURRED("LoanRepaymentOccurred"),
    @XmlEnumValue("LoanRepaidInFull")
    LOAN_REPAID_IN_FULL("LoanRepaidInFull"),
    @XmlEnumValue("LoanOccurred")
    LOAN_OCCURRED("LoanOccurred"),
    @XmlEnumValue("PartialWithdrawalOccurred")
    PARTIAL_WITHDRAWAL_OCCURRED("PartialWithdrawalOccurred"),
    @XmlEnumValue("PaymentModeHasChanged")
    PAYMENT_MODE_HAS_CHANGED("PaymentModeHasChanged"),
    @XmlEnumValue("BaseFaceUpdate")
    BASE_FACE_UPDATE("BaseFaceUpdate"),
    @XmlEnumValue("DividendOptionChanged")
    DIVIDEND_OPTION_CHANGED("DividendOptionChanged"),
    @XmlEnumValue("ScheduledFTRPremiumChange")
    SCHEDULED_FTR_PREMIUM_CHANGE("ScheduledFTRPremiumChange"),
    @XmlEnumValue("UnderwritingChangeOccurred")
    UNDERWRITING_CHANGE_OCCURRED("UnderwritingChangeOccurred"),
    @XmlEnumValue("PremiumPaidToEscapeGrace")
    PREMIUM_PAID_TO_ESCAPE_GRACE("PremiumPaidToEscapeGrace"),
    @XmlEnumValue("LoanRepaymentToEscapeGrace")
    LOAN_REPAYMENT_TO_ESCAPE_GRACE("LoanRepaymentToEscapeGrace"),
    @XmlEnumValue("PartialWithdrawalNotPossible")
    PARTIAL_WITHDRAWAL_NOT_POSSIBLE("PartialWithdrawalNotPossible"),
    @XmlEnumValue("PartialWithdrawalGreaterThanFace")
    PARTIAL_WITHDRAWAL_GREATER_THAN_FACE("PartialWithdrawalGreaterThanFace"),
    @XmlEnumValue("LoanBelowMinimum")
    LOAN_BELOW_MINIMUM("LoanBelowMinimum"),
    @XmlEnumValue("MonthaversaryPremiumRequested")
    MONTHAVERSARY_PREMIUM_REQUESTED("MonthaversaryPremiumRequested"),
    @XmlEnumValue("Refund")
    REFUND("Refund"),
    @XmlEnumValue("FirstYearAnnualLoanInterest")
    FIRST_YEAR_ANNUAL_LOAN_INTEREST("FirstYearAnnualLoanInterest");
    private final String value;

    TransactionNoteType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TransactionNoteType fromValue(String v) {
        for (TransactionNoteType c: TransactionNoteType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}