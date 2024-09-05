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
 * <p>Java class for OutputInstruction.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OutputInstruction">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MoneyPurchase"/>
 *     &lt;enumeration value="AnnualStatement"/>
 *     &lt;enumeration value="Illustration"/>
 *     &lt;enumeration value="ReturnSolveValueOnly"/>
 *     &lt;enumeration value="LevelMaturityPremium"/>
 *     &lt;enumeration value="TransactionSummary"/>
 *     &lt;enumeration value="CycleUpToCurrentDate"/>
 *     &lt;enumeration value="IRSCycleConsequences"/>
 *     &lt;enumeration value="EventCycleConsequences"/>
 *     &lt;enumeration value="PolicyConversion"/>
 *     &lt;enumeration value="MonthEndValuation"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OutputInstruction")
@XmlEnum
public enum OutputInstruction {

    @XmlEnumValue("MoneyPurchase")
    MONEY_PURCHASE("MoneyPurchase"),
    @XmlEnumValue("AnnualStatement")
    ANNUAL_STATEMENT("AnnualStatement"),
    @XmlEnumValue("Illustration")
    ILLUSTRATION("Illustration"),
    @XmlEnumValue("ReturnSolveValueOnly")
    RETURN_SOLVE_VALUE_ONLY("ReturnSolveValueOnly"),
    @XmlEnumValue("LevelMaturityPremium")
    LEVEL_MATURITY_PREMIUM("LevelMaturityPremium"),
    @XmlEnumValue("TransactionSummary")
    TRANSACTION_SUMMARY("TransactionSummary"),
    @XmlEnumValue("CycleUpToCurrentDate")
    CYCLE_UP_TO_CURRENT_DATE("CycleUpToCurrentDate"),
    @XmlEnumValue("IRSCycleConsequences")
    IRS_CYCLE_CONSEQUENCES("IRSCycleConsequences"),
    @XmlEnumValue("EventCycleConsequences")
    EVENT_CYCLE_CONSEQUENCES("EventCycleConsequences"),
    @XmlEnumValue("PolicyConversion")
    POLICY_CONVERSION("PolicyConversion"),
    @XmlEnumValue("MonthEndValuation")
    MONTH_END_VALUATION("MonthEndValuation");
    private final String value;

    OutputInstruction(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OutputInstruction fromValue(String v) {
        for (OutputInstruction c: OutputInstruction.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
