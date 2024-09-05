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
 * <p>Java class for ProductOfferReason.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProductOfferReason">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AttainedAge"/>
 *     &lt;enumeration value="LifeEvent"/>
 *     &lt;enumeration value="ProductFeature"/>
 *     &lt;enumeration value="GuaranteedOrSimplifiedIssue"/>
 *     &lt;enumeration value="BatchOffer"/>
 *     &lt;enumeration value="LifeNeedsAnalysis"/>
 *     &lt;enumeration value="RetirementProjection"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ProductOfferReason")
@XmlEnum
public enum ProductOfferReason {

    @XmlEnumValue("AttainedAge")
    ATTAINED_AGE("AttainedAge"),
    @XmlEnumValue("LifeEvent")
    LIFE_EVENT("LifeEvent"),
    @XmlEnumValue("ProductFeature")
    PRODUCT_FEATURE("ProductFeature"),
    @XmlEnumValue("GuaranteedOrSimplifiedIssue")
    GUARANTEED_OR_SIMPLIFIED_ISSUE("GuaranteedOrSimplifiedIssue"),
    @XmlEnumValue("BatchOffer")
    BATCH_OFFER("BatchOffer"),
    @XmlEnumValue("LifeNeedsAnalysis")
    LIFE_NEEDS_ANALYSIS("LifeNeedsAnalysis"),
    @XmlEnumValue("RetirementProjection")
    RETIREMENT_PROJECTION("RetirementProjection");
    private final String value;

    ProductOfferReason(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProductOfferReason fromValue(String v) {
        for (ProductOfferReason c: ProductOfferReason.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
