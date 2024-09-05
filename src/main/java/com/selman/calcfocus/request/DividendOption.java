//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.03.23 at 11:48:17 AM EDT 
//


package com.selman.calcfocus.request;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DividendOption.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DividendOption">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Cash"/>
 *     &lt;enumeration value="PaidUpInsurance"/>
 *     &lt;enumeration value="OneYearTerm"/>
 *     &lt;enumeration value="PremiumReduction"/>
 *     &lt;enumeration value="RepayLoanBalance"/>
 *     &lt;enumeration value="RepayLoanInterest"/>
 *     &lt;enumeration value="FlexibleTerm"/>
 *     &lt;enumeration value="AccumulateAtInterest"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DividendOption")
@XmlEnum
public enum DividendOption {

    @XmlEnumValue("Cash")
    CASH("Cash"),
    @XmlEnumValue("PaidUpInsurance")
    PAID_UP_INSURANCE("PaidUpInsurance"),
    @XmlEnumValue("OneYearTerm")
    ONE_YEAR_TERM("OneYearTerm"),
    @XmlEnumValue("PremiumReduction")
    PREMIUM_REDUCTION("PremiumReduction"),
    @XmlEnumValue("RepayLoanBalance")
    REPAY_LOAN_BALANCE("RepayLoanBalance"),
    @XmlEnumValue("RepayLoanInterest")
    REPAY_LOAN_INTEREST("RepayLoanInterest"),
    @XmlEnumValue("FlexibleTerm")
    FLEXIBLE_TERM("FlexibleTerm"),
    @XmlEnumValue("AccumulateAtInterest")
    ACCUMULATE_AT_INTEREST("AccumulateAtInterest");
    private final String value;

    DividendOption(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DividendOption fromValue(String v) {
        for (DividendOption c: DividendOption.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}