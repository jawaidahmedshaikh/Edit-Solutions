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
 * <p>Java class for AssetSubType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AssetSubType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Plan401k"/>
 *     &lt;enumeration value="Plan403b"/>
 *     &lt;enumeration value="Plan457"/>
 *     &lt;enumeration value="Roth"/>
 *     &lt;enumeration value="Traditional"/>
 *     &lt;enumeration value="Retirement"/>
 *     &lt;enumeration value="NonRetirement"/>
 *     &lt;enumeration value="Other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AssetSubType")
@XmlEnum
public enum AssetSubType {

    @XmlEnumValue("Plan401k")
    PLAN_401_K("Plan401k"),
    @XmlEnumValue("Plan403b")
    PLAN_403_B("Plan403b"),
    @XmlEnumValue("Plan457")
    PLAN_457("Plan457"),
    @XmlEnumValue("Roth")
    ROTH("Roth"),
    @XmlEnumValue("Traditional")
    TRADITIONAL("Traditional"),
    @XmlEnumValue("Retirement")
    RETIREMENT("Retirement"),
    @XmlEnumValue("NonRetirement")
    NON_RETIREMENT("NonRetirement"),
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    AssetSubType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AssetSubType fromValue(String v) {
        for (AssetSubType c: AssetSubType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}