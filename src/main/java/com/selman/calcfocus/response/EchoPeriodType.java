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
 * <p>Java class for EchoPeriodType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EchoPeriodType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="StopsOnRiderExerciseDate"/>
 *     &lt;enumeration value="ContinuesAfterRiderExerciseDate"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EchoPeriodType")
@XmlEnum
public enum EchoPeriodType {

    @XmlEnumValue("StopsOnRiderExerciseDate")
    STOPS_ON_RIDER_EXERCISE_DATE("StopsOnRiderExerciseDate"),
    @XmlEnumValue("ContinuesAfterRiderExerciseDate")
    CONTINUES_AFTER_RIDER_EXERCISE_DATE("ContinuesAfterRiderExerciseDate");
    private final String value;

    EchoPeriodType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EchoPeriodType fromValue(String v) {
        for (EchoPeriodType c: EchoPeriodType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
