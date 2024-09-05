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
 * <p>Java class for DebtType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DebtType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Mortgage"/>
 *     &lt;enumeration value="CreditCard"/>
 *     &lt;enumeration value="StudentLoans"/>
 *     &lt;enumeration value="CarLoan"/>
 *     &lt;enumeration value="Other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DebtType")
@XmlEnum
public enum DebtType {

    @XmlEnumValue("Mortgage")
    MORTGAGE("Mortgage"),
    @XmlEnumValue("CreditCard")
    CREDIT_CARD("CreditCard"),
    @XmlEnumValue("StudentLoans")
    STUDENT_LOANS("StudentLoans"),
    @XmlEnumValue("CarLoan")
    CAR_LOAN("CarLoan"),
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    DebtType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DebtType fromValue(String v) {
        for (DebtType c: DebtType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
