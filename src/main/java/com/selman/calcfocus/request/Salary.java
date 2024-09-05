//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.03.23 at 11:48:17 AM EDT 
//


package com.selman.calcfocus.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Salary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Salary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="startDateType" type="{http://xmlns.calcfocus.com/services/calc}StartDateType"/>
 *         &lt;element name="startAge" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="startYear" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="endDateType" type="{http://xmlns.calcfocus.com/services/calc}EndDateType"/>
 *         &lt;element name="endAge" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="endYear" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="salaryAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="bonusAmount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="commissionAmount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Salary", propOrder = {
    "startDateType",
    "startAge",
    "startYear",
    "endDateType",
    "endAge",
    "endYear",
    "salaryAmount",
    "bonusAmount",
    "commissionAmount"
})
public class Salary {

    @XmlElement(required = true)
    protected StartDateType startDateType;
    protected Integer startAge;
    protected Integer startYear;
    @XmlElement(required = true)
    protected EndDateType endDateType;
    protected Integer endAge;
    protected Integer endYear;
    protected double salaryAmount;
    protected Double bonusAmount;
    protected Double commissionAmount;

    /**
     * Gets the value of the startDateType property.
     * 
     * @return
     *     possible object is
     *     {@link StartDateType }
     *     
     */
    public StartDateType getStartDateType() {
        return startDateType;
    }

    /**
     * Sets the value of the startDateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link StartDateType }
     *     
     */
    public void setStartDateType(StartDateType value) {
        this.startDateType = value;
    }

    /**
     * Gets the value of the startAge property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStartAge() {
        return startAge;
    }

    /**
     * Sets the value of the startAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStartAge(Integer value) {
        this.startAge = value;
    }

    /**
     * Gets the value of the startYear property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStartYear() {
        return startYear;
    }

    /**
     * Sets the value of the startYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStartYear(Integer value) {
        this.startYear = value;
    }

    /**
     * Gets the value of the endDateType property.
     * 
     * @return
     *     possible object is
     *     {@link EndDateType }
     *     
     */
    public EndDateType getEndDateType() {
        return endDateType;
    }

    /**
     * Sets the value of the endDateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link EndDateType }
     *     
     */
    public void setEndDateType(EndDateType value) {
        this.endDateType = value;
    }

    /**
     * Gets the value of the endAge property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEndAge() {
        return endAge;
    }

    /**
     * Sets the value of the endAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEndAge(Integer value) {
        this.endAge = value;
    }

    /**
     * Gets the value of the endYear property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEndYear() {
        return endYear;
    }

    /**
     * Sets the value of the endYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEndYear(Integer value) {
        this.endYear = value;
    }

    /**
     * Gets the value of the salaryAmount property.
     * 
     */
    public double getSalaryAmount() {
        return salaryAmount;
    }

    /**
     * Sets the value of the salaryAmount property.
     * 
     */
    public void setSalaryAmount(double value) {
        this.salaryAmount = value;
    }

    /**
     * Gets the value of the bonusAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBonusAmount() {
        return bonusAmount;
    }

    /**
     * Sets the value of the bonusAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBonusAmount(Double value) {
        this.bonusAmount = value;
    }

    /**
     * Gets the value of the commissionAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCommissionAmount() {
        return commissionAmount;
    }

    /**
     * Sets the value of the commissionAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCommissionAmount(Double value) {
        this.commissionAmount = value;
    }

}