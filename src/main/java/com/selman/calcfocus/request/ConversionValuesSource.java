//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.02.16 at 02:52:28 PM EST 
//


package com.selman.calcfocus.request;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConversionValuesSource.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ConversionValuesSource">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CalcFocus"/>
 *     &lt;enumeration value="LegacyApplication"/>
 *     &lt;enumeration value="TargetApplication"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ConversionValuesSource")
@XmlEnum
public enum ConversionValuesSource {

    @XmlEnumValue("CalcFocus")
    CALC_FOCUS("CalcFocus"),
    @XmlEnumValue("LegacyApplication")
    LEGACY_APPLICATION("LegacyApplication"),
    @XmlEnumValue("TargetApplication")
    TARGET_APPLICATION("TargetApplication");
    private final String value;

    ConversionValuesSource(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ConversionValuesSource fromValue(String v) {
        for (ConversionValuesSource c: ConversionValuesSource.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
