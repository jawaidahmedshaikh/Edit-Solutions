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
 * <p>Java class for EndDateType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EndDateType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SpecifiedDate"/>
 *     &lt;enumeration value="MaturityDate"/>
 *     &lt;enumeration value="NumberOfEvents"/>
 *     &lt;enumeration value="NumberOfYears"/>
 *     &lt;enumeration value="EndAge"/>
 *     &lt;enumeration value="EndYear"/>
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="Solve"/>
 *     &lt;enumeration value="NextAnniversary"/>
 *     &lt;enumeration value="Age85"/>
 *     &lt;enumeration value="Age100"/>
 *     &lt;enumeration value="PaidUpDate"/>
 *     &lt;enumeration value="NextMonthaversary"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EndDateType")
@XmlEnum
public enum EndDateType {

    @XmlEnumValue("SpecifiedDate")
    SPECIFIED_DATE("SpecifiedDate"),
    @XmlEnumValue("MaturityDate")
    MATURITY_DATE("MaturityDate"),
    @XmlEnumValue("NumberOfEvents")
    NUMBER_OF_EVENTS("NumberOfEvents"),
    @XmlEnumValue("NumberOfYears")
    NUMBER_OF_YEARS("NumberOfYears"),
    @XmlEnumValue("EndAge")
    END_AGE("EndAge"),
    @XmlEnumValue("EndYear")
    END_YEAR("EndYear"),
    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Solve")
    SOLVE("Solve"),
    @XmlEnumValue("NextAnniversary")
    NEXT_ANNIVERSARY("NextAnniversary"),
    @XmlEnumValue("Age85")
    AGE_85("Age85"),
    @XmlEnumValue("Age100")
    AGE_100("Age100"),
    @XmlEnumValue("PaidUpDate")
    PAID_UP_DATE("PaidUpDate"),
    @XmlEnumValue("NextMonthaversary")
    NEXT_MONTHAVERSARY("NextMonthaversary");
    private final String value;

    EndDateType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EndDateType fromValue(String v) {
        for (EndDateType c: EndDateType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
