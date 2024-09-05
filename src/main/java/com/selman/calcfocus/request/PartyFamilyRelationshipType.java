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
 * <p>Java class for PartyFamilyRelationshipType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PartyFamilyRelationshipType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Primary"/>
 *     &lt;enumeration value="Partner"/>
 *     &lt;enumeration value="Child"/>
 *     &lt;enumeration value="Grandchild"/>
 *     &lt;enumeration value="Parent"/>
 *     &lt;enumeration value="Other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PartyFamilyRelationshipType")
@XmlEnum
public enum PartyFamilyRelationshipType {

    @XmlEnumValue("Primary")
    PRIMARY("Primary"),
    @XmlEnumValue("Partner")
    PARTNER("Partner"),
    @XmlEnumValue("Child")
    CHILD("Child"),
    @XmlEnumValue("Grandchild")
    GRANDCHILD("Grandchild"),
    @XmlEnumValue("Parent")
    PARENT("Parent"),
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    PartyFamilyRelationshipType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PartyFamilyRelationshipType fromValue(String v) {
        for (PartyFamilyRelationshipType c: PartyFamilyRelationshipType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
