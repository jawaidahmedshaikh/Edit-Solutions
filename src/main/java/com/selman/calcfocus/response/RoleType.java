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
 * <p>Java class for RoleType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RoleType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PrimaryInsured"/>
 *     &lt;enumeration value="SecondaryInsured"/>
 *     &lt;enumeration value="Owner"/>
 *     &lt;enumeration value="Applicant"/>
 *     &lt;enumeration value="Beneficiary"/>
 *     &lt;enumeration value="Trustee"/>
 *     &lt;enumeration value="Grantor"/>
 *     &lt;enumeration value="Payor"/>
 *     &lt;enumeration value="Agent"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RoleType")
@XmlEnum
public enum RoleType {

    @XmlEnumValue("PrimaryInsured")
    PRIMARY_INSURED("PrimaryInsured"),
    @XmlEnumValue("SecondaryInsured")
    SECONDARY_INSURED("SecondaryInsured"),
    @XmlEnumValue("Owner")
    OWNER("Owner"),
    @XmlEnumValue("Applicant")
    APPLICANT("Applicant"),
    @XmlEnumValue("Beneficiary")
    BENEFICIARY("Beneficiary"),
    @XmlEnumValue("Trustee")
    TRUSTEE("Trustee"),
    @XmlEnumValue("Grantor")
    GRANTOR("Grantor"),
    @XmlEnumValue("Payor")
    PAYOR("Payor"),
    @XmlEnumValue("Agent")
    AGENT("Agent");
    private final String value;

    RoleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RoleType fromValue(String v) {
        for (RoleType c: RoleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
