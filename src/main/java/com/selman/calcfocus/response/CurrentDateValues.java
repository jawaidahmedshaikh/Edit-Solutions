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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CurrentDateValues complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CurrentDateValues">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="currentDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="premiumSuspense" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="costBasis" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="isMEC" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="sevenPayStartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="sumOfPremiumPaidTAMRA" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sevenPayPremium" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sevenPayInitialAdjustmentValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="guidelineSinglePremium" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="guidelineLevelPremium" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="guidelineLevelPremiumSum" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sumOfPremiumsPaidToDateCurrentYear" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sumOfPremiumChargePaidToDateCurrentYear" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="maxExpenseChargeCurrentYear" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sumOfPremiumPaid" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sumOfLoansCurrentYear" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sumOfLoanRepaymentsCurrentYear" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sumOfWithdrawalsCurrentYear" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sumOfPremiumPolicyholderPaidForNoLapseGuaranteeTest" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="historicalTransaction" type="{http://xmlns.calcfocus.com/services/calc}HistoricalTransaction" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="lowestDeathBenefitDuringSevenPayPeriod" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurrentDateValues", propOrder = {
    "currentDate",
    "premiumSuspense",
    "costBasis",
    "isMEC",
    "sevenPayStartDate",
    "sumOfPremiumPaidTAMRA",
    "sevenPayPremium",
    "sevenPayInitialAdjustmentValue",
    "guidelineSinglePremium",
    "guidelineLevelPremium",
    "guidelineLevelPremiumSum",
    "sumOfPremiumsPaidToDateCurrentYear",
    "sumOfPremiumChargePaidToDateCurrentYear",
    "maxExpenseChargeCurrentYear",
    "sumOfPremiumPaid",
    "sumOfLoansCurrentYear",
    "sumOfLoanRepaymentsCurrentYear",
    "sumOfWithdrawalsCurrentYear",
    "sumOfPremiumPolicyholderPaidForNoLapseGuaranteeTest",
    "sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce",
    "historicalTransaction",
    "lowestDeathBenefitDuringSevenPayPeriod"
})
public class CurrentDateValues {

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar currentDate;
    protected Double premiumSuspense;
    protected Double costBasis;
    protected Boolean isMEC;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar sevenPayStartDate;
    protected Double sumOfPremiumPaidTAMRA;
    protected Double sevenPayPremium;
    protected Double sevenPayInitialAdjustmentValue;
    protected Double guidelineSinglePremium;
    protected Double guidelineLevelPremium;
    protected Double guidelineLevelPremiumSum;
    protected Double sumOfPremiumsPaidToDateCurrentYear;
    protected Double sumOfPremiumChargePaidToDateCurrentYear;
    protected Double maxExpenseChargeCurrentYear;
    protected Double sumOfPremiumPaid;
    protected Double sumOfLoansCurrentYear;
    protected Double sumOfLoanRepaymentsCurrentYear;
    protected Double sumOfWithdrawalsCurrentYear;
    protected Double sumOfPremiumPolicyholderPaidForNoLapseGuaranteeTest;
    protected Double sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce;
    protected List<HistoricalTransaction> historicalTransaction;
    protected Double lowestDeathBenefitDuringSevenPayPeriod;

    /**
     * Gets the value of the currentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCurrentDate() {
        return currentDate;
    }

    /**
     * Sets the value of the currentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCurrentDate(XMLGregorianCalendar value) {
        this.currentDate = value;
    }

    /**
     * Gets the value of the premiumSuspense property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPremiumSuspense() {
        return premiumSuspense;
    }

    /**
     * Sets the value of the premiumSuspense property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPremiumSuspense(Double value) {
        this.premiumSuspense = value;
    }

    /**
     * Gets the value of the costBasis property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCostBasis() {
        return costBasis;
    }

    /**
     * Sets the value of the costBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCostBasis(Double value) {
        this.costBasis = value;
    }

    /**
     * Gets the value of the isMEC property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsMEC() {
        return isMEC;
    }

    /**
     * Sets the value of the isMEC property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsMEC(Boolean value) {
        this.isMEC = value;
    }

    /**
     * Gets the value of the sevenPayStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSevenPayStartDate() {
        return sevenPayStartDate;
    }

    /**
     * Sets the value of the sevenPayStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSevenPayStartDate(XMLGregorianCalendar value) {
        this.sevenPayStartDate = value;
    }

    /**
     * Gets the value of the sumOfPremiumPaidTAMRA property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSumOfPremiumPaidTAMRA() {
        return sumOfPremiumPaidTAMRA;
    }

    /**
     * Sets the value of the sumOfPremiumPaidTAMRA property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSumOfPremiumPaidTAMRA(Double value) {
        this.sumOfPremiumPaidTAMRA = value;
    }

    /**
     * Gets the value of the sevenPayPremium property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSevenPayPremium() {
        return sevenPayPremium;
    }

    /**
     * Sets the value of the sevenPayPremium property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSevenPayPremium(Double value) {
        this.sevenPayPremium = value;
    }

    /**
     * Gets the value of the sevenPayInitialAdjustmentValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSevenPayInitialAdjustmentValue() {
        return sevenPayInitialAdjustmentValue;
    }

    /**
     * Sets the value of the sevenPayInitialAdjustmentValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSevenPayInitialAdjustmentValue(Double value) {
        this.sevenPayInitialAdjustmentValue = value;
    }

    /**
     * Gets the value of the guidelineSinglePremium property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getGuidelineSinglePremium() {
        return guidelineSinglePremium;
    }

    /**
     * Sets the value of the guidelineSinglePremium property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setGuidelineSinglePremium(Double value) {
        this.guidelineSinglePremium = value;
    }

    /**
     * Gets the value of the guidelineLevelPremium property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getGuidelineLevelPremium() {
        return guidelineLevelPremium;
    }

    /**
     * Sets the value of the guidelineLevelPremium property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setGuidelineLevelPremium(Double value) {
        this.guidelineLevelPremium = value;
    }

    /**
     * Gets the value of the guidelineLevelPremiumSum property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getGuidelineLevelPremiumSum() {
        return guidelineLevelPremiumSum;
    }

    /**
     * Sets the value of the guidelineLevelPremiumSum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setGuidelineLevelPremiumSum(Double value) {
        this.guidelineLevelPremiumSum = value;
    }

    /**
     * Gets the value of the sumOfPremiumsPaidToDateCurrentYear property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSumOfPremiumsPaidToDateCurrentYear() {
        return sumOfPremiumsPaidToDateCurrentYear;
    }

    /**
     * Sets the value of the sumOfPremiumsPaidToDateCurrentYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSumOfPremiumsPaidToDateCurrentYear(Double value) {
        this.sumOfPremiumsPaidToDateCurrentYear = value;
    }

    /**
     * Gets the value of the sumOfPremiumChargePaidToDateCurrentYear property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSumOfPremiumChargePaidToDateCurrentYear() {
        return sumOfPremiumChargePaidToDateCurrentYear;
    }

    /**
     * Sets the value of the sumOfPremiumChargePaidToDateCurrentYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSumOfPremiumChargePaidToDateCurrentYear(Double value) {
        this.sumOfPremiumChargePaidToDateCurrentYear = value;
    }

    /**
     * Gets the value of the maxExpenseChargeCurrentYear property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMaxExpenseChargeCurrentYear() {
        return maxExpenseChargeCurrentYear;
    }

    /**
     * Sets the value of the maxExpenseChargeCurrentYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMaxExpenseChargeCurrentYear(Double value) {
        this.maxExpenseChargeCurrentYear = value;
    }

    /**
     * Gets the value of the sumOfPremiumPaid property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSumOfPremiumPaid() {
        return sumOfPremiumPaid;
    }

    /**
     * Sets the value of the sumOfPremiumPaid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSumOfPremiumPaid(Double value) {
        this.sumOfPremiumPaid = value;
    }

    /**
     * Gets the value of the sumOfLoansCurrentYear property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSumOfLoansCurrentYear() {
        return sumOfLoansCurrentYear;
    }

    /**
     * Sets the value of the sumOfLoansCurrentYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSumOfLoansCurrentYear(Double value) {
        this.sumOfLoansCurrentYear = value;
    }

    /**
     * Gets the value of the sumOfLoanRepaymentsCurrentYear property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSumOfLoanRepaymentsCurrentYear() {
        return sumOfLoanRepaymentsCurrentYear;
    }

    /**
     * Sets the value of the sumOfLoanRepaymentsCurrentYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSumOfLoanRepaymentsCurrentYear(Double value) {
        this.sumOfLoanRepaymentsCurrentYear = value;
    }

    /**
     * Gets the value of the sumOfWithdrawalsCurrentYear property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSumOfWithdrawalsCurrentYear() {
        return sumOfWithdrawalsCurrentYear;
    }

    /**
     * Sets the value of the sumOfWithdrawalsCurrentYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSumOfWithdrawalsCurrentYear(Double value) {
        this.sumOfWithdrawalsCurrentYear = value;
    }

    /**
     * Gets the value of the sumOfPremiumPolicyholderPaidForNoLapseGuaranteeTest property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSumOfPremiumPolicyholderPaidForNoLapseGuaranteeTest() {
        return sumOfPremiumPolicyholderPaidForNoLapseGuaranteeTest;
    }

    /**
     * Sets the value of the sumOfPremiumPolicyholderPaidForNoLapseGuaranteeTest property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSumOfPremiumPolicyholderPaidForNoLapseGuaranteeTest(Double value) {
        this.sumOfPremiumPolicyholderPaidForNoLapseGuaranteeTest = value;
    }

    /**
     * Gets the value of the sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSumOfPremiumRequiredToKeepNoLapseGuaranteeInforce() {
        return sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce;
    }

    /**
     * Sets the value of the sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSumOfPremiumRequiredToKeepNoLapseGuaranteeInforce(Double value) {
        this.sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce = value;
    }

    /**
     * Gets the value of the historicalTransaction property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the historicalTransaction property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHistoricalTransaction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HistoricalTransaction }
     * 
     * 
     */
    public List<HistoricalTransaction> getHistoricalTransaction() {
        if (historicalTransaction == null) {
            historicalTransaction = new ArrayList<HistoricalTransaction>();
        }
        return this.historicalTransaction;
    }

    /**
     * Gets the value of the lowestDeathBenefitDuringSevenPayPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLowestDeathBenefitDuringSevenPayPeriod() {
        return lowestDeathBenefitDuringSevenPayPeriod;
    }

    /**
     * Sets the value of the lowestDeathBenefitDuringSevenPayPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLowestDeathBenefitDuringSevenPayPeriod(Double value) {
        this.lowestDeathBenefitDuringSevenPayPeriod = value;
    }

}