//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.03.23 at 11:48:47 AM EDT 
//


package com.selman.calcfocus.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SolveSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SolveSummary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="solveName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="solveValueType" type="{http://xmlns.calcfocus.com/services/calc}SolveValueType" minOccurs="0"/>
 *         &lt;element name="transactionType" type="{http://xmlns.calcfocus.com/services/calc}TransactionType" minOccurs="0"/>
 *         &lt;element name="mortalityAssumption" type="{http://xmlns.calcfocus.com/services/calc}MortalityAssumption" minOccurs="0"/>
 *         &lt;element name="solveSuccessIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="endDateType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mode" type="{http://xmlns.calcfocus.com/services/calc}PaymentMode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SolveSummary", propOrder = {
    "solveName",
    "solveValueType",
    "transactionType",
    "mortalityAssumption",
    "solveSuccessIndicator",
    "value",
    "endDateType",
    "mode"
})
public class SolveSummary {

    protected String solveName;
    protected SolveValueType solveValueType;
    protected TransactionType transactionType;
    protected MortalityAssumption mortalityAssumption;
    protected Boolean solveSuccessIndicator;
    protected Double value;
    protected String endDateType;
    protected PaymentMode mode;

    /**
     * Gets the value of the solveName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSolveName() {
        return solveName;
    }

    /**
     * Sets the value of the solveName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSolveName(String value) {
        this.solveName = value;
    }

    /**
     * Gets the value of the solveValueType property.
     * 
     * @return
     *     possible object is
     *     {@link SolveValueType }
     *     
     */
    public SolveValueType getSolveValueType() {
        return solveValueType;
    }

    /**
     * Sets the value of the solveValueType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SolveValueType }
     *     
     */
    public void setSolveValueType(SolveValueType value) {
        this.solveValueType = value;
    }

    /**
     * Gets the value of the transactionType property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionType }
     *     
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the value of the transactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionType }
     *     
     */
    public void setTransactionType(TransactionType value) {
        this.transactionType = value;
    }

    /**
     * Gets the value of the mortalityAssumption property.
     * 
     * @return
     *     possible object is
     *     {@link MortalityAssumption }
     *     
     */
    public MortalityAssumption getMortalityAssumption() {
        return mortalityAssumption;
    }

    /**
     * Sets the value of the mortalityAssumption property.
     * 
     * @param value
     *     allowed object is
     *     {@link MortalityAssumption }
     *     
     */
    public void setMortalityAssumption(MortalityAssumption value) {
        this.mortalityAssumption = value;
    }

    /**
     * Gets the value of the solveSuccessIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSolveSuccessIndicator() {
        return solveSuccessIndicator;
    }

    /**
     * Sets the value of the solveSuccessIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSolveSuccessIndicator(Boolean value) {
        this.solveSuccessIndicator = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * Gets the value of the endDateType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndDateType() {
        return endDateType;
    }

    /**
     * Sets the value of the endDateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDateType(String value) {
        this.endDateType = value;
    }

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentMode }
     *     
     */
    public PaymentMode getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentMode }
     *     
     */
    public void setMode(PaymentMode value) {
        this.mode = value;
    }

}
