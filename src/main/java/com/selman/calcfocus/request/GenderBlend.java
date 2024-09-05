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
 * <p>Java class for GenderBlend.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GenderBlend">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="Even"/>
 *     &lt;enumeration value="HighM"/>
 *     &lt;enumeration value="HighF"/>
 *     &lt;enumeration value="AllM"/>
 *     &lt;enumeration value="AllF"/>
 *     &lt;enumeration value="MedHighF"/>
 *     &lt;enumeration value="MedHighM"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GenderBlend")
@XmlEnum
public enum GenderBlend {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Even")
    EVEN("Even"),
    @XmlEnumValue("HighM")
    HIGH_M("HighM"),
    @XmlEnumValue("HighF")
    HIGH_F("HighF"),
    @XmlEnumValue("AllM")
    ALL_M("AllM"),
    @XmlEnumValue("AllF")
    ALL_F("AllF"),
    @XmlEnumValue("MedHighF")
    MED_HIGH_F("MedHighF"),
    @XmlEnumValue("MedHighM")
    MED_HIGH_M("MedHighM");
    private final String value;

    GenderBlend(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GenderBlend fromValue(String v) {
        for (GenderBlend c: GenderBlend.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}