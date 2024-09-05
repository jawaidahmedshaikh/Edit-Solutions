//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.03.23 at 11:48:47 AM EDT 
//


package com.selman.calcfocus.response;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FutureLifeProjectionPolicySummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FutureLifeProjectionPolicySummary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="productCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="allocationPercentage" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="averageAnnualNonStochasticIncome" type="{http://xmlns.calcfocus.com/services/calc}YearValue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FutureLifeProjectionPolicySummary", propOrder = {
    "productCode",
    "allocationPercentage",
    "averageAnnualNonStochasticIncome"
})
public class FutureLifeProjectionPolicySummary {

    @XmlElement(required = true)
    protected String productCode;
    protected Double allocationPercentage;
    protected List<YearValue> averageAnnualNonStochasticIncome;

    /**
     * Gets the value of the productCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Sets the value of the productCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductCode(String value) {
        this.productCode = value;
    }

    /**
     * Gets the value of the allocationPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAllocationPercentage() {
        return allocationPercentage;
    }

    /**
     * Sets the value of the allocationPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAllocationPercentage(Double value) {
        this.allocationPercentage = value;
    }

    /**
     * Gets the value of the averageAnnualNonStochasticIncome property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the averageAnnualNonStochasticIncome property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAverageAnnualNonStochasticIncome().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link YearValue }
     * 
     * 
     */
    public List<YearValue> getAverageAnnualNonStochasticIncome() {
        if (averageAnnualNonStochasticIncome == null) {
            averageAnnualNonStochasticIncome = new ArrayList<YearValue>();
        }
        return this.averageAnnualNonStochasticIncome;
    }

}
