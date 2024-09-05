//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.04.26 at 10:39:22 PM EDT 
//


package com.selman.calcfocus.request;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountingAccountType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AccountingAccountType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Cash"/>
 *     &lt;enumeration value="FederalPremiumTax"/>
 *     &lt;enumeration value="LoanDisbursement"/>
 *     &lt;enumeration value="LoanIncrease"/>
 *     &lt;enumeration value="PremiumCharge"/>
 *     &lt;enumeration value="PremiumFirstYear"/>
 *     &lt;enumeration value="PremiumIncome"/>
 *     &lt;enumeration value="PremiumRenewal"/>
 *     &lt;enumeration value="PremiumSuspense"/>
 *     &lt;enumeration value="StatePremiumTax"/>
 *     &lt;enumeration value="Separate"/>
 *     &lt;enumeration value="CapitalizedLoanInterest"/>
 *     &lt;enumeration value="COI"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AccountingAccountType")
@XmlEnum
public enum AccountingAccountType {

    @XmlEnumValue("Cash")
    CASH("Cash"),
    @XmlEnumValue("FederalPremiumTax")
    FEDERAL_PREMIUM_TAX("FederalPremiumTax"),
    @XmlEnumValue("LoanDisbursement")
    LOAN_DISBURSEMENT("LoanDisbursement"),
    @XmlEnumValue("LoanIncrease")
    LOAN_INCREASE("LoanIncrease"),
    @XmlEnumValue("PremiumCharge")
    PREMIUM_CHARGE("PremiumCharge"),
    @XmlEnumValue("PremiumFirstYear")
    PREMIUM_FIRST_YEAR("PremiumFirstYear"),
    @XmlEnumValue("PremiumIncome")
    PREMIUM_INCOME("PremiumIncome"),
    @XmlEnumValue("PremiumRenewal")
    PREMIUM_RENEWAL("PremiumRenewal"),
    @XmlEnumValue("PremiumSuspense")
    PREMIUM_SUSPENSE("PremiumSuspense"),
    @XmlEnumValue("StatePremiumTax")
    STATE_PREMIUM_TAX("StatePremiumTax"),
    @XmlEnumValue("Separate")
    SEPARATE("Separate"),
    @XmlEnumValue("CapitalizedLoanInterest")
    CAPITALIZED_LOAN_INTEREST("CapitalizedLoanInterest"),
    COI("COI");
    private final String value;

    AccountingAccountType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AccountingAccountType fromValue(String v) {
        for (AccountingAccountType c: AccountingAccountType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
