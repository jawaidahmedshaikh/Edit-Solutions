//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.02.16 at 02:52:13 PM EST 
//


package com.selman.calcfocus.response;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IllustrationType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="IllustrationType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PointOfSale"/>
 *     &lt;enumeration value="Reillustration"/>
 *     &lt;enumeration value="AtIssue"/>
 *     &lt;enumeration value="Inforce"/>
 *     &lt;enumeration value="AnnualStatement"/>
 *     &lt;enumeration value="GraceInsufficient"/>
 *     &lt;enumeration value="GraceOverloan"/>
 *     &lt;enumeration value="GraceConservation"/>
 *     &lt;enumeration value="Reinstatement"/>
 *     &lt;enumeration value="NonPayment"/>
 *     &lt;enumeration value="RiderExpiry"/>
 *     &lt;enumeration value="InterestBill"/>
 *     &lt;enumeration value="Hypothetical"/>
 *     &lt;enumeration value="Disclosure"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "IllustrationType")
@XmlEnum
public enum IllustrationType {

    @XmlEnumValue("PointOfSale")
    POINT_OF_SALE("PointOfSale"),
    @XmlEnumValue("Reillustration")
    REILLUSTRATION("Reillustration"),
    @XmlEnumValue("AtIssue")
    AT_ISSUE("AtIssue"),
    @XmlEnumValue("Inforce")
    INFORCE("Inforce"),
    @XmlEnumValue("AnnualStatement")
    ANNUAL_STATEMENT("AnnualStatement"),
    @XmlEnumValue("GraceInsufficient")
    GRACE_INSUFFICIENT("GraceInsufficient"),
    @XmlEnumValue("GraceOverloan")
    GRACE_OVERLOAN("GraceOverloan"),
    @XmlEnumValue("GraceConservation")
    GRACE_CONSERVATION("GraceConservation"),
    @XmlEnumValue("Reinstatement")
    REINSTATEMENT("Reinstatement"),
    @XmlEnumValue("NonPayment")
    NON_PAYMENT("NonPayment"),
    @XmlEnumValue("RiderExpiry")
    RIDER_EXPIRY("RiderExpiry"),
    @XmlEnumValue("InterestBill")
    INTEREST_BILL("InterestBill"),
    @XmlEnumValue("Hypothetical")
    HYPOTHETICAL("Hypothetical"),
    @XmlEnumValue("Disclosure")
    DISCLOSURE("Disclosure");
    private final String value;

    IllustrationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static IllustrationType fromValue(String v) {
        for (IllustrationType c: IllustrationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}