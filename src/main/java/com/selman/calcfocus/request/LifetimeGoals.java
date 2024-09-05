//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.03.23 at 11:48:17 AM EDT 
//


package com.selman.calcfocus.request;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LifetimeGoals complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LifetimeGoals">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="partyGUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="riskTolerance" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="retirementAge" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="childrenAttendingFeePayingSchool" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="planOnPayingForCollege" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="typeOfCollege" type="{http://xmlns.calcfocus.com/services/calc}TypeOfCollege" minOccurs="0"/>
 *         &lt;element name="monthlyBudgetForInsurance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="annualIncomeGoal" type="{http://xmlns.calcfocus.com/services/calc}AnnualIncomeGoal" minOccurs="0"/>
 *         &lt;element name="annualIncomeInitialGoalAmount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="annualIncomeInitialGoalPercent" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="retirementGoalPriority" type="{http://xmlns.calcfocus.com/services/calc}RetirementGoalPriority" minOccurs="0"/>
 *         &lt;element name="bequest" type="{http://xmlns.calcfocus.com/services/calc}Bequest" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LifetimeGoals", propOrder = {
    "partyGUID",
    "riskTolerance",
    "retirementAge",
    "childrenAttendingFeePayingSchool",
    "planOnPayingForCollege",
    "typeOfCollege",
    "monthlyBudgetForInsurance",
    "annualIncomeGoal",
    "annualIncomeInitialGoalAmount",
    "annualIncomeInitialGoalPercent",
    "retirementGoalPriority",
    "bequest"
})
public class LifetimeGoals {

    @XmlElement(required = true)
    protected String partyGUID;
    protected Integer riskTolerance;
    protected Integer retirementAge;
    protected Boolean childrenAttendingFeePayingSchool;
    protected Boolean planOnPayingForCollege;
    protected TypeOfCollege typeOfCollege;
    protected Double monthlyBudgetForInsurance;
    protected AnnualIncomeGoal annualIncomeGoal;
    protected Double annualIncomeInitialGoalAmount;
    protected Double annualIncomeInitialGoalPercent;
    protected RetirementGoalPriority retirementGoalPriority;
    protected List<Bequest> bequest;

    /**
     * Gets the value of the partyGUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartyGUID() {
        return partyGUID;
    }

    /**
     * Sets the value of the partyGUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartyGUID(String value) {
        this.partyGUID = value;
    }

    /**
     * Gets the value of the riskTolerance property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRiskTolerance() {
        return riskTolerance;
    }

    /**
     * Sets the value of the riskTolerance property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRiskTolerance(Integer value) {
        this.riskTolerance = value;
    }

    /**
     * Gets the value of the retirementAge property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRetirementAge() {
        return retirementAge;
    }

    /**
     * Sets the value of the retirementAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRetirementAge(Integer value) {
        this.retirementAge = value;
    }

    /**
     * Gets the value of the childrenAttendingFeePayingSchool property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isChildrenAttendingFeePayingSchool() {
        return childrenAttendingFeePayingSchool;
    }

    /**
     * Sets the value of the childrenAttendingFeePayingSchool property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setChildrenAttendingFeePayingSchool(Boolean value) {
        this.childrenAttendingFeePayingSchool = value;
    }

    /**
     * Gets the value of the planOnPayingForCollege property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPlanOnPayingForCollege() {
        return planOnPayingForCollege;
    }

    /**
     * Sets the value of the planOnPayingForCollege property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPlanOnPayingForCollege(Boolean value) {
        this.planOnPayingForCollege = value;
    }

    /**
     * Gets the value of the typeOfCollege property.
     * 
     * @return
     *     possible object is
     *     {@link TypeOfCollege }
     *     
     */
    public TypeOfCollege getTypeOfCollege() {
        return typeOfCollege;
    }

    /**
     * Sets the value of the typeOfCollege property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeOfCollege }
     *     
     */
    public void setTypeOfCollege(TypeOfCollege value) {
        this.typeOfCollege = value;
    }

    /**
     * Gets the value of the monthlyBudgetForInsurance property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMonthlyBudgetForInsurance() {
        return monthlyBudgetForInsurance;
    }

    /**
     * Sets the value of the monthlyBudgetForInsurance property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMonthlyBudgetForInsurance(Double value) {
        this.monthlyBudgetForInsurance = value;
    }

    /**
     * Gets the value of the annualIncomeGoal property.
     * 
     * @return
     *     possible object is
     *     {@link AnnualIncomeGoal }
     *     
     */
    public AnnualIncomeGoal getAnnualIncomeGoal() {
        return annualIncomeGoal;
    }

    /**
     * Sets the value of the annualIncomeGoal property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnnualIncomeGoal }
     *     
     */
    public void setAnnualIncomeGoal(AnnualIncomeGoal value) {
        this.annualIncomeGoal = value;
    }

    /**
     * Gets the value of the annualIncomeInitialGoalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAnnualIncomeInitialGoalAmount() {
        return annualIncomeInitialGoalAmount;
    }

    /**
     * Sets the value of the annualIncomeInitialGoalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAnnualIncomeInitialGoalAmount(Double value) {
        this.annualIncomeInitialGoalAmount = value;
    }

    /**
     * Gets the value of the annualIncomeInitialGoalPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAnnualIncomeInitialGoalPercent() {
        return annualIncomeInitialGoalPercent;
    }

    /**
     * Sets the value of the annualIncomeInitialGoalPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAnnualIncomeInitialGoalPercent(Double value) {
        this.annualIncomeInitialGoalPercent = value;
    }

    /**
     * Gets the value of the retirementGoalPriority property.
     * 
     * @return
     *     possible object is
     *     {@link RetirementGoalPriority }
     *     
     */
    public RetirementGoalPriority getRetirementGoalPriority() {
        return retirementGoalPriority;
    }

    /**
     * Sets the value of the retirementGoalPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link RetirementGoalPriority }
     *     
     */
    public void setRetirementGoalPriority(RetirementGoalPriority value) {
        this.retirementGoalPriority = value;
    }

    /**
     * Gets the value of the bequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Bequest }
     * 
     * 
     */
    public List<Bequest> getBequest() {
        if (bequest == null) {
            bequest = new ArrayList<Bequest>();
        }
        return this.bequest;
    }

}
