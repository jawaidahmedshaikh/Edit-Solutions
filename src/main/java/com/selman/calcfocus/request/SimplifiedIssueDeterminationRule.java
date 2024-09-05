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
 * <p>Java class for SimplifiedIssueDeterminationRule.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SimplifiedIssueDeterminationRule">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="KnockoutQuestions"/>
 *     &lt;enumeration value="KnockoutQuestionsPlusHealthScore"/>
 *     &lt;enumeration value="HealthScore"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SimplifiedIssueDeterminationRule")
@XmlEnum
public enum SimplifiedIssueDeterminationRule {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("KnockoutQuestions")
    KNOCKOUT_QUESTIONS("KnockoutQuestions"),
    @XmlEnumValue("KnockoutQuestionsPlusHealthScore")
    KNOCKOUT_QUESTIONS_PLUS_HEALTH_SCORE("KnockoutQuestionsPlusHealthScore"),
    @XmlEnumValue("HealthScore")
    HEALTH_SCORE("HealthScore");
    private final String value;

    SimplifiedIssueDeterminationRule(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SimplifiedIssueDeterminationRule fromValue(String v) {
        for (SimplifiedIssueDeterminationRule c: SimplifiedIssueDeterminationRule.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}