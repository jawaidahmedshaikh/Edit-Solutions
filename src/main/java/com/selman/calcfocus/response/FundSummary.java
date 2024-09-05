//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.03.23 at 11:48:47 AM EDT 
//


package com.selman.calcfocus.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for FundSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FundSummary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fundCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fundPrincipal" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="lastPrincipalChangeDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="uncapitalizedCredit" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="uncapitalizedInterest" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="advanceInterestUnearned" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="units" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FundSummary", propOrder = {
    "fundCode",
    "fundPrincipal",
    "lastPrincipalChangeDate",
    "uncapitalizedCredit",
    "uncapitalizedInterest",
    "advanceInterestUnearned",
    "units"
})
public class FundSummary {

    protected String fundCode;
    protected Double fundPrincipal;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lastPrincipalChangeDate;
    protected Double uncapitalizedCredit;
    protected Double uncapitalizedInterest;
    protected Double advanceInterestUnearned;
    protected Double units;

    /**
     * Gets the value of the fundCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFundCode() {
        return fundCode;
    }

    /**
     * Sets the value of the fundCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFundCode(String value) {
        this.fundCode = value;
    }

    /**
     * Gets the value of the fundPrincipal property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFundPrincipal() {
        return fundPrincipal;
    }

    /**
     * Sets the value of the fundPrincipal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFundPrincipal(Double value) {
        this.fundPrincipal = value;
    }

    /**
     * Gets the value of the lastPrincipalChangeDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastPrincipalChangeDate() {
        return lastPrincipalChangeDate;
    }

    /**
     * Sets the value of the lastPrincipalChangeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastPrincipalChangeDate(XMLGregorianCalendar value) {
        this.lastPrincipalChangeDate = value;
    }

    /**
     * Gets the value of the uncapitalizedCredit property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUncapitalizedCredit() {
        return uncapitalizedCredit;
    }

    /**
     * Sets the value of the uncapitalizedCredit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUncapitalizedCredit(Double value) {
        this.uncapitalizedCredit = value;
    }

    /**
     * Gets the value of the uncapitalizedInterest property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUncapitalizedInterest() {
        return uncapitalizedInterest;
    }

    /**
     * Sets the value of the uncapitalizedInterest property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUncapitalizedInterest(Double value) {
        this.uncapitalizedInterest = value;
    }

    /**
     * Gets the value of the advanceInterestUnearned property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAdvanceInterestUnearned() {
        return advanceInterestUnearned;
    }

    /**
     * Sets the value of the advanceInterestUnearned property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAdvanceInterestUnearned(Double value) {
        this.advanceInterestUnearned = value;
    }

    /**
     * Gets the value of the units property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUnits() {
        return units;
    }

    /**
     * Sets the value of the units property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUnits(Double value) {
        this.units = value;
    }

}
