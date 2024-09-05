//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.19 at 05:41:28 PM EST 
//


package com.selman.calcfocus.response;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PolicySummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PolicySummary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="illustrationType" type="{http://xmlns.calcfocus.com/services/calc}IllustrationType" minOccurs="0"/>
 *         &lt;element name="policyAdminStatus" type="{http://xmlns.calcfocus.com/services/calc}PolicyAdminStatus" minOccurs="0"/>
 *         &lt;element name="policyCurrentDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="effectiveDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="pointInTimeDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="policyNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="issueState" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="issueAge" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="currentYear" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="attainedAge" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="maturityAge" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="policyType" type="{http://xmlns.calcfocus.com/services/calc}PolicyType"/>
 *         &lt;element name="paymentMode" type="{http://xmlns.calcfocus.com/services/calc}PaymentMode" minOccurs="0"/>
 *         &lt;element name="premium" type="{http://xmlns.calcfocus.com/services/calc}Premium" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="premiumSuspense" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="seconaryGuaranteeExpiryDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="expiryDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="maturityDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="deathBenefitOption" type="{http://xmlns.calcfocus.com/services/calc}DeathBenefitOption" minOccurs="0"/>
 *         &lt;element name="definitionOfLifeInsurance" type="{http://xmlns.calcfocus.com/services/calc}DefinitionOfLifeInsurance" minOccurs="0"/>
 *         &lt;element name="billingIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SumOfPremiumPolicyholderPaidForNoLapseGuaranteeTest" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SumOfPremiumRequiredToKeepNoLapseGuaranteeInforce" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="initialLoanPrincipal" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="currentDateValues" type="{http://xmlns.calcfocus.com/services/calc}CurrentDateValues" minOccurs="0"/>
 *         &lt;element name="baseProductSummary" type="{http://xmlns.calcfocus.com/services/calc}BaseProductSummary" minOccurs="0"/>
 *         &lt;element name="baseGuaranteedCashValue" type="{http://xmlns.calcfocus.com/services/calc}Rate" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="coverageSummary" type="{http://xmlns.calcfocus.com/services/calc}CoverageSummary" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="scenarioSummary" type="{http://xmlns.calcfocus.com/services/calc}ScenarioSummary" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="fundSummary" type="{http://xmlns.calcfocus.com/services/calc}FundSummary" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="iRSCycleConsequences" type="{http://xmlns.calcfocus.com/services/calc}IRSCycleConsequences" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="eventCycleConsequences" type="{http://xmlns.calcfocus.com/services/calc}EventCycleConsequences" minOccurs="0"/>
 *         &lt;element name="eventCycleConsequencesCoverage" type="{http://xmlns.calcfocus.com/services/calc}EventCycleConsequencesCoverage" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="monthEndValuation" type="{http://xmlns.calcfocus.com/services/calc}MonthEndValuation" minOccurs="0"/>
 *         &lt;element name="asyncExtract" type="{http://xmlns.calcfocus.com/services/calc}AsyncExtract" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="historicalTransaction" type="{http://xmlns.calcfocus.com/services/calc}HistoricalTransaction" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="lowestDeathBenefitDuringSevenPayPeriod" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="transactionSummary" type="{http://xmlns.calcfocus.com/services/calc}TransactionSummary" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="fundPerformance" type="{http://xmlns.calcfocus.com/services/calc}FundPerformance" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="actuarialValues" type="{http://xmlns.calcfocus.com/services/calc}EndOfYearValues" minOccurs="0"/>
 *         &lt;element name="reserveSummary" type="{http://xmlns.calcfocus.com/services/calc}ReserveSummary" minOccurs="0"/>
 *         &lt;element name="report" type="{http://xmlns.calcfocus.com/services/calc}Report" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PolicySummary", propOrder = {
    "illustrationType",
    "policyAdminStatus",
    "policyCurrentDate",
    "effectiveDate",
    "pointInTimeDate",
    "policyNumber",
    "guid",
    "issueState",
    "issueAge",
    "currentYear",
    "attainedAge",
    "maturityAge",
    "policyType",
    "paymentMode",
    "premium",
    "premiumSuspense",
    "seconaryGuaranteeExpiryDate",
    "expiryDate",
    "maturityDate",
    "deathBenefitOption",
    "definitionOfLifeInsurance",
    "billingIndicator",
    "sumOfPremiumPolicyholderPaidForNoLapseGuaranteeTest",
    "sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce",
    "initialLoanPrincipal",
    "currentDateValues",
    "baseProductSummary",
    "baseGuaranteedCashValue",
    "coverageSummary",
    "scenarioSummary",
    "fundSummary",
    "irsCycleConsequences",
    "eventCycleConsequences",
    "eventCycleConsequencesCoverage",
    "monthEndValuation",
    "asyncExtract",
    "historicalTransaction",
    "lowestDeathBenefitDuringSevenPayPeriod",
    "transactionSummary",
    "fundPerformance",
    "actuarialValues",
    "reserveSummary",
    "report"
})
public class PolicySummary {

    protected IllustrationType illustrationType;
    protected PolicyAdminStatus policyAdminStatus;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar policyCurrentDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar effectiveDate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar pointInTimeDate;
    protected String policyNumber;
    @XmlElement(name = "GUID")
    protected String guid;
    protected String issueState;
    protected Integer issueAge;
    protected Integer currentYear;
    protected Integer attainedAge;
    protected Integer maturityAge;
    @XmlElement(required = true)
    protected PolicyType policyType;
    protected PaymentMode paymentMode;
    protected List<Premium> premium;
    protected Double premiumSuspense;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar seconaryGuaranteeExpiryDate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar expiryDate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar maturityDate;
    protected DeathBenefitOption deathBenefitOption;
    protected DefinitionOfLifeInsurance definitionOfLifeInsurance;
    protected Boolean billingIndicator;
    @XmlElement(name = "SumOfPremiumPolicyholderPaidForNoLapseGuaranteeTest")
    protected Double sumOfPremiumPolicyholderPaidForNoLapseGuaranteeTest;
    @XmlElement(name = "SumOfPremiumRequiredToKeepNoLapseGuaranteeInforce")
    protected Double sumOfPremiumRequiredToKeepNoLapseGuaranteeInforce;
    protected Double initialLoanPrincipal;
    protected CurrentDateValues currentDateValues;
    protected BaseProductSummary baseProductSummary;
    protected List<Rate> baseGuaranteedCashValue;
    protected List<CoverageSummary> coverageSummary;
    protected List<ScenarioSummary> scenarioSummary;
    protected List<FundSummary> fundSummary;
    @XmlElement(name = "iRSCycleConsequences")
    protected List<IRSCycleConsequences> irsCycleConsequences;
    protected EventCycleConsequences eventCycleConsequences;
    protected List<EventCycleConsequencesCoverage> eventCycleConsequencesCoverage;
    protected MonthEndValuation monthEndValuation;
    protected List<AsyncExtract> asyncExtract;
    protected List<HistoricalTransaction> historicalTransaction;
    protected Double lowestDeathBenefitDuringSevenPayPeriod;
    protected List<TransactionSummary> transactionSummary;
    protected List<FundPerformance> fundPerformance;
    protected EndOfYearValues actuarialValues;
    protected ReserveSummary reserveSummary;
    protected Report report;

    /**
     * Gets the value of the illustrationType property.
     * 
     * @return
     *     possible object is
     *     {@link IllustrationType }
     *     
     */
    public IllustrationType getIllustrationType() {
        return illustrationType;
    }

    /**
     * Sets the value of the illustrationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link IllustrationType }
     *     
     */
    public void setIllustrationType(IllustrationType value) {
        this.illustrationType = value;
    }

    /**
     * Gets the value of the policyAdminStatus property.
     * 
     * @return
     *     possible object is
     *     {@link PolicyAdminStatus }
     *     
     */
    public PolicyAdminStatus getPolicyAdminStatus() {
        return policyAdminStatus;
    }

    /**
     * Sets the value of the policyAdminStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolicyAdminStatus }
     *     
     */
    public void setPolicyAdminStatus(PolicyAdminStatus value) {
        this.policyAdminStatus = value;
    }

    /**
     * Gets the value of the policyCurrentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPolicyCurrentDate() {
        return policyCurrentDate;
    }

    /**
     * Sets the value of the policyCurrentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPolicyCurrentDate(XMLGregorianCalendar value) {
        this.policyCurrentDate = value;
    }

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
    }

    /**
     * Gets the value of the pointInTimeDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPointInTimeDate() {
        return pointInTimeDate;
    }

    /**
     * Sets the value of the pointInTimeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPointInTimeDate(XMLGregorianCalendar value) {
        this.pointInTimeDate = value;
    }

    /**
     * Gets the value of the policyNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyNumber() {
        return policyNumber;
    }

    /**
     * Sets the value of the policyNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyNumber(String value) {
        this.policyNumber = value;
    }

    /**
     * Gets the value of the guid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGUID() {
        return guid;
    }

    /**
     * Sets the value of the guid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGUID(String value) {
        this.guid = value;
    }

    /**
     * Gets the value of the issueState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssueState() {
        return issueState;
    }

    /**
     * Sets the value of the issueState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssueState(String value) {
        this.issueState = value;
    }

    /**
     * Gets the value of the issueAge property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIssueAge() {
        return issueAge;
    }

    /**
     * Sets the value of the issueAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIssueAge(Integer value) {
        this.issueAge = value;
    }

    /**
     * Gets the value of the currentYear property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCurrentYear() {
        return currentYear;
    }

    /**
     * Sets the value of the currentYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCurrentYear(Integer value) {
        this.currentYear = value;
    }

    /**
     * Gets the value of the attainedAge property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAttainedAge() {
        return attainedAge;
    }

    /**
     * Sets the value of the attainedAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAttainedAge(Integer value) {
        this.attainedAge = value;
    }

    /**
     * Gets the value of the maturityAge property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaturityAge() {
        return maturityAge;
    }

    /**
     * Sets the value of the maturityAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaturityAge(Integer value) {
        this.maturityAge = value;
    }

    /**
     * Gets the value of the policyType property.
     * 
     * @return
     *     possible object is
     *     {@link PolicyType }
     *     
     */
    public PolicyType getPolicyType() {
        return policyType;
    }

    /**
     * Sets the value of the policyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolicyType }
     *     
     */
    public void setPolicyType(PolicyType value) {
        this.policyType = value;
    }

    /**
     * Gets the value of the paymentMode property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentMode }
     *     
     */
    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    /**
     * Sets the value of the paymentMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentMode }
     *     
     */
    public void setPaymentMode(PaymentMode value) {
        this.paymentMode = value;
    }

    /**
     * Gets the value of the premium property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the premium property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPremium().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Premium }
     * 
     * 
     */
    public List<Premium> getPremium() {
        if (premium == null) {
            premium = new ArrayList<Premium>();
        }
        return this.premium;
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
     * Gets the value of the seconaryGuaranteeExpiryDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSeconaryGuaranteeExpiryDate() {
        return seconaryGuaranteeExpiryDate;
    }

    /**
     * Sets the value of the seconaryGuaranteeExpiryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSeconaryGuaranteeExpiryDate(XMLGregorianCalendar value) {
        this.seconaryGuaranteeExpiryDate = value;
    }

    /**
     * Gets the value of the expiryDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the value of the expiryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpiryDate(XMLGregorianCalendar value) {
        this.expiryDate = value;
    }

    /**
     * Gets the value of the maturityDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMaturityDate() {
        return maturityDate;
    }

    /**
     * Sets the value of the maturityDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMaturityDate(XMLGregorianCalendar value) {
        this.maturityDate = value;
    }

    /**
     * Gets the value of the deathBenefitOption property.
     * 
     * @return
     *     possible object is
     *     {@link DeathBenefitOption }
     *     
     */
    public DeathBenefitOption getDeathBenefitOption() {
        return deathBenefitOption;
    }

    /**
     * Sets the value of the deathBenefitOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeathBenefitOption }
     *     
     */
    public void setDeathBenefitOption(DeathBenefitOption value) {
        this.deathBenefitOption = value;
    }

    /**
     * Gets the value of the definitionOfLifeInsurance property.
     * 
     * @return
     *     possible object is
     *     {@link DefinitionOfLifeInsurance }
     *     
     */
    public DefinitionOfLifeInsurance getDefinitionOfLifeInsurance() {
        return definitionOfLifeInsurance;
    }

    /**
     * Sets the value of the definitionOfLifeInsurance property.
     * 
     * @param value
     *     allowed object is
     *     {@link DefinitionOfLifeInsurance }
     *     
     */
    public void setDefinitionOfLifeInsurance(DefinitionOfLifeInsurance value) {
        this.definitionOfLifeInsurance = value;
    }

    /**
     * Gets the value of the billingIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBillingIndicator() {
        return billingIndicator;
    }

    /**
     * Sets the value of the billingIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBillingIndicator(Boolean value) {
        this.billingIndicator = value;
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
     * Gets the value of the initialLoanPrincipal property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getInitialLoanPrincipal() {
        return initialLoanPrincipal;
    }

    /**
     * Sets the value of the initialLoanPrincipal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setInitialLoanPrincipal(Double value) {
        this.initialLoanPrincipal = value;
    }

    /**
     * Gets the value of the currentDateValues property.
     * 
     * @return
     *     possible object is
     *     {@link CurrentDateValues }
     *     
     */
    public CurrentDateValues getCurrentDateValues() {
        return currentDateValues;
    }

    /**
     * Sets the value of the currentDateValues property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrentDateValues }
     *     
     */
    public void setCurrentDateValues(CurrentDateValues value) {
        this.currentDateValues = value;
    }

    /**
     * Gets the value of the baseProductSummary property.
     * 
     * @return
     *     possible object is
     *     {@link BaseProductSummary }
     *     
     */
    public BaseProductSummary getBaseProductSummary() {
        return baseProductSummary;
    }

    /**
     * Sets the value of the baseProductSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link BaseProductSummary }
     *     
     */
    public void setBaseProductSummary(BaseProductSummary value) {
        this.baseProductSummary = value;
    }

    /**
     * Gets the value of the baseGuaranteedCashValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the baseGuaranteedCashValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBaseGuaranteedCashValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Rate }
     * 
     * 
     */
    public List<Rate> getBaseGuaranteedCashValue() {
        if (baseGuaranteedCashValue == null) {
            baseGuaranteedCashValue = new ArrayList<Rate>();
        }
        return this.baseGuaranteedCashValue;
    }

    /**
     * Gets the value of the coverageSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the coverageSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCoverageSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CoverageSummary }
     * 
     * 
     */
    public List<CoverageSummary> getCoverageSummary() {
        if (coverageSummary == null) {
            coverageSummary = new ArrayList<CoverageSummary>();
        }
        return this.coverageSummary;
    }

    /**
     * Gets the value of the scenarioSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scenarioSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScenarioSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ScenarioSummary }
     * 
     * 
     */
    public List<ScenarioSummary> getScenarioSummary() {
        if (scenarioSummary == null) {
            scenarioSummary = new ArrayList<ScenarioSummary>();
        }
        return this.scenarioSummary;
    }

    /**
     * Gets the value of the fundSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fundSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFundSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FundSummary }
     * 
     * 
     */
    public List<FundSummary> getFundSummary() {
        if (fundSummary == null) {
            fundSummary = new ArrayList<FundSummary>();
        }
        return this.fundSummary;
    }

    /**
     * Gets the value of the irsCycleConsequences property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the irsCycleConsequences property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIRSCycleConsequences().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IRSCycleConsequences }
     * 
     * 
     */
    public List<IRSCycleConsequences> getIRSCycleConsequences() {
        if (irsCycleConsequences == null) {
            irsCycleConsequences = new ArrayList<IRSCycleConsequences>();
        }
        return this.irsCycleConsequences;
    }

    /**
     * Gets the value of the eventCycleConsequences property.
     * 
     * @return
     *     possible object is
     *     {@link EventCycleConsequences }
     *     
     */
    public EventCycleConsequences getEventCycleConsequences() {
        return eventCycleConsequences;
    }

    /**
     * Sets the value of the eventCycleConsequences property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventCycleConsequences }
     *     
     */
    public void setEventCycleConsequences(EventCycleConsequences value) {
        this.eventCycleConsequences = value;
    }

    /**
     * Gets the value of the eventCycleConsequencesCoverage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eventCycleConsequencesCoverage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEventCycleConsequencesCoverage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EventCycleConsequencesCoverage }
     * 
     * 
     */
    public List<EventCycleConsequencesCoverage> getEventCycleConsequencesCoverage() {
        if (eventCycleConsequencesCoverage == null) {
            eventCycleConsequencesCoverage = new ArrayList<EventCycleConsequencesCoverage>();
        }
        return this.eventCycleConsequencesCoverage;
    }

    /**
     * Gets the value of the monthEndValuation property.
     * 
     * @return
     *     possible object is
     *     {@link MonthEndValuation }
     *     
     */
    public MonthEndValuation getMonthEndValuation() {
        return monthEndValuation;
    }

    /**
     * Sets the value of the monthEndValuation property.
     * 
     * @param value
     *     allowed object is
     *     {@link MonthEndValuation }
     *     
     */
    public void setMonthEndValuation(MonthEndValuation value) {
        this.monthEndValuation = value;
    }

    /**
     * Gets the value of the asyncExtract property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asyncExtract property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsyncExtract().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AsyncExtract }
     * 
     * 
     */
    public List<AsyncExtract> getAsyncExtract() {
        if (asyncExtract == null) {
            asyncExtract = new ArrayList<AsyncExtract>();
        }
        return this.asyncExtract;
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

    /**
     * Gets the value of the transactionSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transactionSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransactionSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransactionSummary }
     * 
     * 
     */
    public List<TransactionSummary> getTransactionSummary() {
        if (transactionSummary == null) {
            transactionSummary = new ArrayList<TransactionSummary>();
        }
        return this.transactionSummary;
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
     * Gets the value of the actuarialValues property.
     * 
     * @return
     *     possible object is
     *     {@link EndOfYearValues }
     *     
     */
    public EndOfYearValues getActuarialValues() {
        return actuarialValues;
    }

    /**
     * Sets the value of the actuarialValues property.
     * 
     * @param value
     *     allowed object is
     *     {@link EndOfYearValues }
     *     
     */
    public void setActuarialValues(EndOfYearValues value) {
        this.actuarialValues = value;
    }

    /**
     * Gets the value of the reserveSummary property.
     * 
     * @return
     *     possible object is
     *     {@link ReserveSummary }
     *     
     */
    public ReserveSummary getReserveSummary() {
        return reserveSummary;
    }

    /**
     * Sets the value of the reserveSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReserveSummary }
     *     
     */
    public void setReserveSummary(ReserveSummary value) {
        this.reserveSummary = value;
    }

    /**
     * Gets the value of the report property.
     * 
     * @return
     *     possible object is
     *     {@link Report }
     *     
     */
    public Report getReport() {
        return report;
    }

    /**
     * Sets the value of the report property.
     * 
     * @param value
     *     allowed object is
     *     {@link Report }
     *     
     */
    public void setReport(Report value) {
        this.report = value;
    }

}
