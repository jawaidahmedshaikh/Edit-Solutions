//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.02.16 at 02:52:28 PM EST 
//


package com.selman.calcfocus.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SimpleRateDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SimpleRateDefinition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="durationStart" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="durationEnd" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SimpleRateDefinition", propOrder = {
    "rate",
    "durationStart",
    "durationEnd"
})
public class SimpleRateDefinition {

    protected Double rate;
    protected Integer durationStart;
    protected Integer durationEnd;

    /**
     * Gets the value of the rate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRate() {
        return rate;
    }

    /**
     * Sets the value of the rate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRate(Double value) {
        this.rate = value;
    }

    /**
     * Gets the value of the durationStart property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDurationStart() {
        return durationStart;
    }

    /**
     * Sets the value of the durationStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDurationStart(Integer value) {
        this.durationStart = value;
    }

    /**
     * Gets the value of the durationEnd property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDurationEnd() {
        return durationEnd;
    }

    /**
     * Sets the value of the durationEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDurationEnd(Integer value) {
        this.durationEnd = value;
    }

}
