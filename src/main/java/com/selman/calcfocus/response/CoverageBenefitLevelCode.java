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
 * <p>Java class for CoverageBenefitLevelCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CoverageBenefitLevelCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Bronze"/>
 *     &lt;enumeration value="Silver"/>
 *     &lt;enumeration value="Gold"/>
 *     &lt;enumeration value="Platinum"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CoverageBenefitLevelCode")
@XmlEnum
public enum CoverageBenefitLevelCode {

    @XmlEnumValue("Bronze")
    BRONZE("Bronze"),
    @XmlEnumValue("Silver")
    SILVER("Silver"),
    @XmlEnumValue("Gold")
    GOLD("Gold"),
    @XmlEnumValue("Platinum")
    PLATINUM("Platinum");
    private final String value;

    CoverageBenefitLevelCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CoverageBenefitLevelCode fromValue(String v) {
        for (CoverageBenefitLevelCode c: CoverageBenefitLevelCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}