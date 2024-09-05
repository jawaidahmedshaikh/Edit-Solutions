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
 * <p>Java class for NonStochasticFutureLifeProjection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NonStochasticFutureLifeProjection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="futureLifeProjectionType" type="{http://xmlns.calcfocus.com/services/calc}FutureLifeProjectionType" minOccurs="0"/>
 *         &lt;element name="deathAge" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="endOfYearValues" type="{http://xmlns.calcfocus.com/services/calc}EndOfYearValues" maxOccurs="unbounded"/>
 *         &lt;element name="fundPerformance" type="{http://xmlns.calcfocus.com/services/calc}FundPerformance" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="desiredIncome" type="{http://xmlns.calcfocus.com/services/calc}YearValue" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="desiredBequest" type="{http://xmlns.calcfocus.com/services/calc}YearValue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NonStochasticFutureLifeProjection", propOrder = {
    "futureLifeProjectionType",
    "deathAge",
    "endOfYearValues",
    "fundPerformance",
    "desiredIncome",
    "desiredBequest"
})
public class NonStochasticFutureLifeProjection {

    protected FutureLifeProjectionType futureLifeProjectionType;
    protected int deathAge;
    @XmlElement(required = true)
    protected List<EndOfYearValues> endOfYearValues;
    protected List<FundPerformance> fundPerformance;
    protected List<YearValue> desiredIncome;
    protected List<YearValue> desiredBequest;

    /**
     * Gets the value of the futureLifeProjectionType property.
     * 
     * @return
     *     possible object is
     *     {@link FutureLifeProjectionType }
     *     
     */
    public FutureLifeProjectionType getFutureLifeProjectionType() {
        return futureLifeProjectionType;
    }

    /**
     * Sets the value of the futureLifeProjectionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FutureLifeProjectionType }
     *     
     */
    public void setFutureLifeProjectionType(FutureLifeProjectionType value) {
        this.futureLifeProjectionType = value;
    }

    /**
     * Gets the value of the deathAge property.
     * 
     */
    public int getDeathAge() {
        return deathAge;
    }

    /**
     * Sets the value of the deathAge property.
     * 
     */
    public void setDeathAge(int value) {
        this.deathAge = value;
    }

    /**
     * Gets the value of the endOfYearValues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the endOfYearValues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEndOfYearValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EndOfYearValues }
     * 
     * 
     */
    public List<EndOfYearValues> getEndOfYearValues() {
        if (endOfYearValues == null) {
            endOfYearValues = new ArrayList<EndOfYearValues>();
        }
        return this.endOfYearValues;
    }

    /**
     * Gets the value of the fundPerformance property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fundPerformance property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFundPerformance().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FundPerformance }
     * 
     * 
     */
    public List<FundPerformance> getFundPerformance() {
        if (fundPerformance == null) {
            fundPerformance = new ArrayList<FundPerformance>();
        }
        return this.fundPerformance;
    }

    /**
     * Gets the value of the desiredIncome property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the desiredIncome property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDesiredIncome().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link YearValue }
     * 
     * 
     */
    public List<YearValue> getDesiredIncome() {
        if (desiredIncome == null) {
            desiredIncome = new ArrayList<YearValue>();
        }
        return this.desiredIncome;
    }

    /**
     * Gets the value of the desiredBequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the desiredBequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDesiredBequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link YearValue }
     * 
     * 
     */
    public List<YearValue> getDesiredBequest() {
        if (desiredBequest == null) {
            desiredBequest = new ArrayList<YearValue>();
        }
        return this.desiredBequest;
    }

}
