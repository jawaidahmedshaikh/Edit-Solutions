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
 * <p>Java class for PolicyType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PolicyType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="UniversalLife"/>
 *     &lt;enumeration value="WholeLife"/>
 *     &lt;enumeration value="TermLife"/>
 *     &lt;enumeration value="VariableUniversalLife"/>
 *     &lt;enumeration value="IndexedUniversalLife"/>
 *     &lt;enumeration value="FixedAnnuity"/>
 *     &lt;enumeration value="VariableAnnuity"/>
 *     &lt;enumeration value="IndexedAnnuity"/>
 *     &lt;enumeration value="ImmediateFixedAnnuity"/>
 *     &lt;enumeration value="ImmediateVariableAnnuity"/>
 *     &lt;enumeration value="LongTermDisability"/>
 *     &lt;enumeration value="ShortTermDisability"/>
 *     &lt;enumeration value="Dental"/>
 *     &lt;enumeration value="CriticalIllness"/>
 *     &lt;enumeration value="RetirementInvestments"/>
 *     &lt;enumeration value="PreRetirementInvestments"/>
 *     &lt;enumeration value="Accident"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PolicyType")
@XmlEnum
public enum PolicyType {

    @XmlEnumValue("UniversalLife")
    UNIVERSAL_LIFE("UniversalLife"),
    @XmlEnumValue("WholeLife")
    WHOLE_LIFE("WholeLife"),
    @XmlEnumValue("TermLife")
    TERM_LIFE("TermLife"),
    @XmlEnumValue("VariableUniversalLife")
    VARIABLE_UNIVERSAL_LIFE("VariableUniversalLife"),
    @XmlEnumValue("IndexedUniversalLife")
    INDEXED_UNIVERSAL_LIFE("IndexedUniversalLife"),
    @XmlEnumValue("FixedAnnuity")
    FIXED_ANNUITY("FixedAnnuity"),
    @XmlEnumValue("VariableAnnuity")
    VARIABLE_ANNUITY("VariableAnnuity"),
    @XmlEnumValue("IndexedAnnuity")
    INDEXED_ANNUITY("IndexedAnnuity"),
    @XmlEnumValue("ImmediateFixedAnnuity")
    IMMEDIATE_FIXED_ANNUITY("ImmediateFixedAnnuity"),
    @XmlEnumValue("ImmediateVariableAnnuity")
    IMMEDIATE_VARIABLE_ANNUITY("ImmediateVariableAnnuity"),
    @XmlEnumValue("LongTermDisability")
    LONG_TERM_DISABILITY("LongTermDisability"),
    @XmlEnumValue("ShortTermDisability")
    SHORT_TERM_DISABILITY("ShortTermDisability"),
    @XmlEnumValue("Dental")
    DENTAL("Dental"),
    @XmlEnumValue("CriticalIllness")
    CRITICAL_ILLNESS("CriticalIllness"),
    @XmlEnumValue("RetirementInvestments")
    RETIREMENT_INVESTMENTS("RetirementInvestments"),
    @XmlEnumValue("PreRetirementInvestments")
    PRE_RETIREMENT_INVESTMENTS("PreRetirementInvestments"),
    @XmlEnumValue("Accident")
    ACCIDENT("Accident");
    private final String value;

    PolicyType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PolicyType fromValue(String v) {
        for (PolicyType c: PolicyType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
