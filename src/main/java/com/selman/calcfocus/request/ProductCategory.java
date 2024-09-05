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
 * <p>Java class for ProductCategory.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProductCategory">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Annuity"/>
 *     &lt;enumeration value="Life"/>
 *     &lt;enumeration value="Dental"/>
 *     &lt;enumeration value="Disability"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ProductCategory")
@XmlEnum
public enum ProductCategory {

    @XmlEnumValue("Annuity")
    ANNUITY("Annuity"),
    @XmlEnumValue("Life")
    LIFE("Life"),
    @XmlEnumValue("Dental")
    DENTAL("Dental"),
    @XmlEnumValue("Disability")
    DISABILITY("Disability");
    private final String value;

    ProductCategory(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProductCategory fromValue(String v) {
        for (ProductCategory c: ProductCategory.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
