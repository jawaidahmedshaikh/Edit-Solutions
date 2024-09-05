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
 * <p>Java class for LifeInsuranceRecommendationFaceDeterminationRule.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LifeInsuranceRecommendationFaceDeterminationRule">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Maximum"/>
 *     &lt;enumeration value="Average"/>
 *     &lt;enumeration value="Smart"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LifeInsuranceRecommendationFaceDeterminationRule")
@XmlEnum
public enum LifeInsuranceRecommendationFaceDeterminationRule {

    @XmlEnumValue("Maximum")
    MAXIMUM("Maximum"),
    @XmlEnumValue("Average")
    AVERAGE("Average"),
    @XmlEnumValue("Smart")
    SMART("Smart");
    private final String value;

    LifeInsuranceRecommendationFaceDeterminationRule(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LifeInsuranceRecommendationFaceDeterminationRule fromValue(String v) {
        for (LifeInsuranceRecommendationFaceDeterminationRule c: LifeInsuranceRecommendationFaceDeterminationRule.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
