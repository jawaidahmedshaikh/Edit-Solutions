//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.03.23 at 11:48:47 AM EDT 
//


package com.selman.calcfocus.response;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AnnualIncomeGoal.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AnnualIncomeGoal">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SpecifiedAmountAcrossAllProducts"/>
 *     &lt;enumeration value="FixedPercentageOfLumpSumInvestment"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AnnualIncomeGoal")
@XmlEnum
public enum AnnualIncomeGoal {

    @XmlEnumValue("SpecifiedAmountAcrossAllProducts")
    SPECIFIED_AMOUNT_ACROSS_ALL_PRODUCTS("SpecifiedAmountAcrossAllProducts"),
    @XmlEnumValue("FixedPercentageOfLumpSumInvestment")
    FIXED_PERCENTAGE_OF_LUMP_SUM_INVESTMENT("FixedPercentageOfLumpSumInvestment");
    private final String value;

    AnnualIncomeGoal(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AnnualIncomeGoal fromValue(String v) {
        for (AnnualIncomeGoal c: AnnualIncomeGoal.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
