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
 * <p>Java class for FutureLifeProjectionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FutureLifeProjectionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Stochastic"/>
 *     &lt;enumeration value="DefaultInputs"/>
 *     &lt;enumeration value="PoorInitialInvestmentPerformance"/>
 *     &lt;enumeration value="PoorLateInvestmentPerformance"/>
 *     &lt;enumeration value="HighInflation"/>
 *     &lt;enumeration value="EarlyDeath"/>
 *     &lt;enumeration value="LateDeath"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FutureLifeProjectionType")
@XmlEnum
public enum FutureLifeProjectionType {

    @XmlEnumValue("Stochastic")
    STOCHASTIC("Stochastic"),
    @XmlEnumValue("DefaultInputs")
    DEFAULT_INPUTS("DefaultInputs"),
    @XmlEnumValue("PoorInitialInvestmentPerformance")
    POOR_INITIAL_INVESTMENT_PERFORMANCE("PoorInitialInvestmentPerformance"),
    @XmlEnumValue("PoorLateInvestmentPerformance")
    POOR_LATE_INVESTMENT_PERFORMANCE("PoorLateInvestmentPerformance"),
    @XmlEnumValue("HighInflation")
    HIGH_INFLATION("HighInflation"),
    @XmlEnumValue("EarlyDeath")
    EARLY_DEATH("EarlyDeath"),
    @XmlEnumValue("LateDeath")
    LATE_DEATH("LateDeath");
    private final String value;

    FutureLifeProjectionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FutureLifeProjectionType fromValue(String v) {
        for (FutureLifeProjectionType c: FutureLifeProjectionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
