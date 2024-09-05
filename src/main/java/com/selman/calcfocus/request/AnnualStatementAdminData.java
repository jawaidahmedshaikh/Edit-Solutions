//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.02.16 at 02:52:28 PM EST 
//


package com.selman.calcfocus.request;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AnnualStatementAdminData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AnnualStatementAdminData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="policyAccumulationValueAtStartDate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="policySurrenderValueAtEndDate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="policyLoanedAccountValueAtStartDate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="faceAmountAtStartDate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="policyAccumulationValueAtEndDate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="policyLoanInterestAccruedAtEndDate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="surrenderChargeAtEndDate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="deathBenefitAtEndDate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="faceAmountAtEndDate" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sumOfPremiumsPaid" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sumOfLoans" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sumOfLoanRepayments" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sumOfWithdrawals" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sumOfRiderCOI" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sumOfCOI" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sumOfPremiumLoads" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="annualStatementMonthlySummary" type="{http://xmlns.calcfocus.com/services/calc}AnnualStatementMonthlySummary" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="annualStatementFaceChangeSummary" type="{http://xmlns.calcfocus.com/services/calc}AnnualStatementFaceChangeSummary" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnnualStatementAdminData", propOrder = {
    "startDate",
    "endDate",
    "policyAccumulationValueAtStartDate",
    "policySurrenderValueAtEndDate",
    "policyLoanedAccountValueAtStartDate",
    "faceAmountAtStartDate",
    "policyAccumulationValueAtEndDate",
    "policyLoanInterestAccruedAtEndDate",
    "surrenderChargeAtEndDate",
    "deathBenefitAtEndDate",
    "faceAmountAtEndDate",
    "sumOfPremiumsPaid",
    "sumOfLoans",
    "sumOfLoanRepayments",
    "sumOfWithdrawals",
    "sumOfRiderCOI",
    "sumOfCOI",
    "sumOfPremiumLoads",
    "annualStatementMonthlySummary",
    "annualStatementFaceChangeSummary"
})
public class AnnualStatementAdminData {

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    protected Double policyAccumulationValueAtStartDate;
    protected Double policySurrenderValueAtEndDate;
    protected Double policyLoanedAccountValueAtStartDate;
    protected Double faceAmountAtStartDate;
    protected Double policyAccumulationValueAtEndDate;
    protected Double policyLoanInterestAccruedAtEndDate;
    protected Double surrenderChargeAtEndDate;
    protected Double deathBenefitAtEndDate;
    protected Double faceAmountAtEndDate;
    protected Double sumOfPremiumsPaid;
    protected Double sumOfLoans;
    protected Double sumOfLoanRepayments;
    protected Double sumOfWithdrawals;
    protected Double sumOfRiderCOI;
    protected Double sumOfCOI;
    protected Double sumOfPremiumLoads;
    protected List<AnnualStatementMonthlySummary> annualStatementMonthlySummary;
    protected List<AnnualStatementFaceChangeSummary> annualStatementFaceChangeSummary;

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the policyAccumulationValueAtStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPolicyAccumulationValueAtStartDate() {
        return policyAccumulationValueAtStartDate;
    }

    /**
     * Sets the value of the policyAccumulationValueAtStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPolicyAccumulationValueAtStartDate(Double value) {
        this.policyAccumulationValueAtStartDate = value;
    }

    /**
     * Gets the value of the policySurrenderValueAtEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPolicySurrenderValueAtEndDate() {
        return policySurrenderValueAtEndDate;
    }

    /**
     * Sets the value of the policySurrenderValueAtEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPolicySurrenderValueAtEndDate(Double value) {
        this.policySurrenderValueAtEndDate = value;
    }

    /**
     * Gets the value of the policyLoanedAccountValueAtStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPolicyLoanedAccountValueAtStartDate() {
        return policyLoanedAccountValueAtStartDate;
    }

    /**
     * Sets the value of the policyLoanedAccountValueAtStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPolicyLoanedAccountValueAtStartDate(Double value) {
        this.policyLoanedAccountValueAtStartDate = value;
    }

    /**
     * Gets the value of the faceAmountAtStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFaceAmountAtStartDate() {
        return faceAmountAtStartDate;
    }

    /**
     * Sets the value of the faceAmountAtStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFaceAmountAtStartDate(Double value) {
        this.faceAmountAtStartDate = value;
    }

    /**
     * Gets the value of the policyAccumulationValueAtEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPolicyAccumulationValueAtEndDate() {
        return policyAccumulationValueAtEndDate;
    }

    /**
     * Sets the value of the policyAccumulationValueAtEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPolicyAccumulationValueAtEndDate(Double value) {
        this.policyAccumulationValueAtEndDate = value;
    }

    /**
     * Gets the value of the policyLoanInterestAccruedAtEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPolicyLoanInterestAccruedAtEndDate() {
        return policyLoanInterestAccruedAtEndDate;
    }

    /**
     * Sets the value of the policyLoanInterestAccruedAtEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPolicyLoanInterestAccruedAtEndDate(Double value) {
        this.policyLoanInterestAccruedAtEndDate = value;
    }

    /**
     * Gets the value of the surrenderChargeAtEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSurrenderChargeAtEndDate() {
        return surrenderChargeAtEndDate;
    }

    /**
     * Sets the value of the surrenderChargeAtEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSurrenderChargeAtEndDate(Double value) {
        this.surrenderChargeAtEndDate = value;
    }

    /**
     * Gets the value of the deathBenefitAtEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDeathBenefitAtEndDate() {
        return deathBenefitAtEndDate;
    }

    /**
     * Sets the value of the deathBenefitAtEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDeathBenefitAtEndDate(Double value) {
        this.deathBenefitAtEndDate = value;
    }

    /**
     * Gets the value of the faceAmountAtEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFaceAmountAtEndDate() {
        return faceAmountAtEndDate;
    }

    /**
     * Sets the value of the faceAmountAtEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFaceAmountAtEndDate(Double value) {
        this.faceAmountAtEndDate = value;
    }

    /**
     * Gets the value of the sumOfPremiumsPaid property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSumOfPremiumsPaid() {
        return sumOfPremiumsPaid;
    }

    /**
     * Sets the value of the sumOfPremiumsPaid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSumOfPremiumsPaid(Double value) {
        this.sumOfPremiumsPaid = value;
    }

    /**
     * Gets the value of the sumOfLoans property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSumOfLoans() {
        return sumOfLoans;
    }

    /**
     * Sets the value of the sumOfLoans property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSumOfLoans(Double value) {
        this.sumOfLoans = value;
    }

    /**
     * Gets the value of the sumOfLoanRepayments property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSumOfLoanRepayments() {
        return sumOfLoanRepayments;
    }

    /**
     * Sets the value of the sumOfLoanRepayments property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSumOfLoanRepayments(Double value) {
        this.sumOfLoanRepayments = value;
    }

    /**
     * Gets the value of the sumOfWithdrawals property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSumOfWithdrawals() {
        return sumOfWithdrawals;
    }

    /**
     * Sets the value of the sumOfWithdrawals property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSumOfWithdrawals(Double value) {
        this.sumOfWithdrawals = value;
    }

    /**
     * Gets the value of the sumOfRiderCOI property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSumOfRiderCOI() {
        return sumOfRiderCOI;
    }

    /**
     * Sets the value of the sumOfRiderCOI property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSumOfRiderCOI(Double value) {
        this.sumOfRiderCOI = value;
    }

    /**
     * Gets the value of the sumOfCOI property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSumOfCOI() {
        return sumOfCOI;
    }

    /**
     * Sets the value of the sumOfCOI property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSumOfCOI(Double value) {
        this.sumOfCOI = value;
    }

    /**
     * Gets the value of the sumOfPremiumLoads property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSumOfPremiumLoads() {
        return sumOfPremiumLoads;
    }

    /**
     * Sets the value of the sumOfPremiumLoads property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSumOfPremiumLoads(Double value) {
        this.sumOfPremiumLoads = value;
    }

    /**
     * Gets the value of the annualStatementMonthlySummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the annualStatementMonthlySummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnnualStatementMonthlySummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AnnualStatementMonthlySummary }
     * 
     * 
     */
    public List<AnnualStatementMonthlySummary> getAnnualStatementMonthlySummary() {
        if (annualStatementMonthlySummary == null) {
            annualStatementMonthlySummary = new ArrayList<AnnualStatementMonthlySummary>();
        }
        return this.annualStatementMonthlySummary;
    }

    /**
     * Gets the value of the annualStatementFaceChangeSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the annualStatementFaceChangeSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnnualStatementFaceChangeSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AnnualStatementFaceChangeSummary }
     * 
     * 
     */
    public List<AnnualStatementFaceChangeSummary> getAnnualStatementFaceChangeSummary() {
        if (annualStatementFaceChangeSummary == null) {
            annualStatementFaceChangeSummary = new ArrayList<AnnualStatementFaceChangeSummary>();
        }
        return this.annualStatementFaceChangeSummary;
    }

}
