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
 * <p>Java class for ClassCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ClassCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="LevelA"/>
 *     &lt;enumeration value="LevelB"/>
 *     &lt;enumeration value="LevelC"/>
 *     &lt;enumeration value="Executives"/>
 *     &lt;enumeration value="NonExecutives"/>
 *     &lt;enumeration value="FullTime"/>
 *     &lt;enumeration value="PartTime"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ClassCode")
@XmlEnum
public enum ClassCode {

    @XmlEnumValue("LevelA")
    LEVEL_A("LevelA"),
    @XmlEnumValue("LevelB")
    LEVEL_B("LevelB"),
    @XmlEnumValue("LevelC")
    LEVEL_C("LevelC"),
    @XmlEnumValue("Executives")
    EXECUTIVES("Executives"),
    @XmlEnumValue("NonExecutives")
    NON_EXECUTIVES("NonExecutives"),
    @XmlEnumValue("FullTime")
    FULL_TIME("FullTime"),
    @XmlEnumValue("PartTime")
    PART_TIME("PartTime");
    private final String value;

    ClassCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ClassCode fromValue(String v) {
        for (ClassCode c: ClassCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
