//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.02.16 at 02:52:13 PM EST 
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
 * <p>Java class for ScenarioSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ScenarioSummary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="scenarioName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scenarioDescriptionType" type="{http://xmlns.calcfocus.com/services/calc}ScenarioDescriptionType" minOccurs="0"/>
 *         &lt;element name="interestAssumption" type="{http://xmlns.calcfocus.com/services/calc}InterestAssumption" minOccurs="0"/>
 *         &lt;element name="mortalityAssumption" type="{http://xmlns.calcfocus.com/services/calc}MortalityAssumption" minOccurs="0"/>
 *         &lt;element name="supplementalScenarioIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="deathBenefitOption" type="{http://xmlns.calcfocus.com/services/calc}DeathBenefitOption" minOccurs="0"/>
 *         &lt;element name="definitionOfLifeInsurance" type="{http://xmlns.calcfocus.com/services/calc}DefinitionOfLifeInsurance" minOccurs="0"/>
 *         &lt;element name="lapseYear" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="expiryYear" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="expiryDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="solveValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="tenYearSurrenderCostIndex" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="tenYearNetPaymentCostIndex" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="twentyYearSurrenderCostIndex" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="twentyYearNetPaymentCostIndex" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="accumulationValueAtMaturity" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="unloanedCashValueAtMaturity" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="accumulationValueAtStart" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="unloanedCashValueAtStart" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="accumulationValueAtEndOfFirstYear" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="unloanedCashValueAtEndOfFirstYear" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="endOfYearValues" type="{http://xmlns.calcfocus.com/services/calc}EndOfYearValues" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="beginningOfYearValues" type="{http://xmlns.calcfocus.com/services/calc}BeginningOfYearValues" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="annuityAnnualValues" type="{http://xmlns.calcfocus.com/services/calc}AnnuityAnnualValues" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="baseProductSummary" type="{http://xmlns.calcfocus.com/services/calc}BaseProductSummary" minOccurs="0"/>
 *         &lt;element name="anchoredScenarioSummary" type="{http://xmlns.calcfocus.com/services/calc}ScenarioSummary" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="solveSummary" type="{http://xmlns.calcfocus.com/services/calc}SolveSummary" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScenarioSummary", propOrder = {
    "scenarioName",
    "scenarioDescriptionType",
    "interestAssumption",
    "mortalityAssumption",
    "supplementalScenarioIndicator",
    "deathBenefitOption",
    "definitionOfLifeInsurance",
    "lapseYear",
    "expiryYear",
    "expiryDate",
    "solveValue",
    "tenYearSurrenderCostIndex",
    "tenYearNetPaymentCostIndex",
    "twentyYearSurrenderCostIndex",
    "twentyYearNetPaymentCostIndex",
    "accumulationValueAtMaturity",
    "unloanedCashValueAtMaturity",
    "accumulationValueAtStart",
    "unloanedCashValueAtStart",
    "accumulationValueAtEndOfFirstYear",
    "unloanedCashValueAtEndOfFirstYear",
    "endOfYearValues",
    "beginningOfYearValues",
    "annuityAnnualValues",
    "baseProductSummary",
    "anchoredScenarioSummary",
    "solveSummary"
})
public class ScenarioSummary {

    protected String scenarioName;
    protected ScenarioDescriptionType scenarioDescriptionType;
    protected InterestAssumption interestAssumption;
    protected MortalityAssumption mortalityAssumption;
    protected Boolean supplementalScenarioIndicator;
    protected DeathBenefitOption deathBenefitOption;
    protected DefinitionOfLifeInsurance definitionOfLifeInsurance;
    protected Integer lapseYear;
    protected Integer expiryYear;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar expiryDate;
    protected Double solveValue;
    protected Double tenYearSurrenderCostIndex;
    protected Double tenYearNetPaymentCostIndex;
    protected Double twentyYearSurrenderCostIndex;
    protected Double twentyYearNetPaymentCostIndex;
    protected Double accumulationValueAtMaturity;
    protected Double unloanedCashValueAtMaturity;
    protected Double accumulationValueAtStart;
    protected Double unloanedCashValueAtStart;
    protected Double accumulationValueAtEndOfFirstYear;
    protected Double unloanedCashValueAtEndOfFirstYear;
    protected List<EndOfYearValues> endOfYearValues;
    protected List<BeginningOfYearValues> beginningOfYearValues;
    protected List<AnnuityAnnualValues> annuityAnnualValues;
    protected BaseProductSummary baseProductSummary;
    protected List<ScenarioSummary> anchoredScenarioSummary;
    protected List<SolveSummary> solveSummary;

    /**
     * Gets the value of the scenarioName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScenarioName() {
        return scenarioName;
    }

    /**
     * Sets the value of the scenarioName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScenarioName(String value) {
        this.scenarioName = value;
    }

    /**
     * Gets the value of the scenarioDescriptionType property.
     * 
     * @return
     *     possible object is
     *     {@link ScenarioDescriptionType }
     *     
     */
    public ScenarioDescriptionType getScenarioDescriptionType() {
        return scenarioDescriptionType;
    }

    /**
     * Sets the value of the scenarioDescriptionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScenarioDescriptionType }
     *     
     */
    public void setScenarioDescriptionType(ScenarioDescriptionType value) {
        this.scenarioDescriptionType = value;
    }

    /**
     * Gets the value of the interestAssumption property.
     * 
     * @return
     *     possible object is
     *     {@link InterestAssumption }
     *     
     */
    public InterestAssumption getInterestAssumption() {
        return interestAssumption;
    }

    /**
     * Sets the value of the interestAssumption property.
     * 
     * @param value
     *     allowed object is
     *     {@link InterestAssumption }
     *     
     */
    public void setInterestAssumption(InterestAssumption value) {
        this.interestAssumption = value;
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
     * Gets the value of the supplementalScenarioIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSupplementalScenarioIndicator() {
        return supplementalScenarioIndicator;
    }

    /**
     * Sets the value of the supplementalScenarioIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSupplementalScenarioIndicator(Boolean value) {
        this.supplementalScenarioIndicator = value;
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
     * Gets the value of the lapseYear property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLapseYear() {
        return lapseYear;
    }

    /**
     * Sets the value of the lapseYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLapseYear(Integer value) {
        this.lapseYear = value;
    }

    /**
     * Gets the value of the expiryYear property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getExpiryYear() {
        return expiryYear;
    }

    /**
     * Sets the value of the expiryYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setExpiryYear(Integer value) {
        this.expiryYear = value;
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
     * Gets the value of the solveValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSolveValue() {
        return solveValue;
    }

    /**
     * Sets the value of the solveValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSolveValue(Double value) {
        this.solveValue = value;
    }

    /**
     * Gets the value of the tenYearSurrenderCostIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTenYearSurrenderCostIndex() {
        return tenYearSurrenderCostIndex;
    }

    /**
     * Sets the value of the tenYearSurrenderCostIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTenYearSurrenderCostIndex(Double value) {
        this.tenYearSurrenderCostIndex = value;
    }

    /**
     * Gets the value of the tenYearNetPaymentCostIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTenYearNetPaymentCostIndex() {
        return tenYearNetPaymentCostIndex;
    }

    /**
     * Sets the value of the tenYearNetPaymentCostIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTenYearNetPaymentCostIndex(Double value) {
        this.tenYearNetPaymentCostIndex = value;
    }

    /**
     * Gets the value of the twentyYearSurrenderCostIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTwentyYearSurrenderCostIndex() {
        return twentyYearSurrenderCostIndex;
    }

    /**
     * Sets the value of the twentyYearSurrenderCostIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTwentyYearSurrenderCostIndex(Double value) {
        this.twentyYearSurrenderCostIndex = value;
    }

    /**
     * Gets the value of the twentyYearNetPaymentCostIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTwentyYearNetPaymentCostIndex() {
        return twentyYearNetPaymentCostIndex;
    }

    /**
     * Sets the value of the twentyYearNetPaymentCostIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTwentyYearNetPaymentCostIndex(Double value) {
        this.twentyYearNetPaymentCostIndex = value;
    }

    /**
     * Gets the value of the accumulationValueAtMaturity property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAccumulationValueAtMaturity() {
        return accumulationValueAtMaturity;
    }

    /**
     * Sets the value of the accumulationValueAtMaturity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAccumulationValueAtMaturity(Double value) {
        this.accumulationValueAtMaturity = value;
    }

    /**
     * Gets the value of the unloanedCashValueAtMaturity property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUnloanedCashValueAtMaturity() {
        return unloanedCashValueAtMaturity;
    }

    /**
     * Sets the value of the unloanedCashValueAtMaturity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUnloanedCashValueAtMaturity(Double value) {
        this.unloanedCashValueAtMaturity = value;
    }

    /**
     * Gets the value of the accumulationValueAtStart property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAccumulationValueAtStart() {
        return accumulationValueAtStart;
    }

    /**
     * Sets the value of the accumulationValueAtStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAccumulationValueAtStart(Double value) {
        this.accumulationValueAtStart = value;
    }

    /**
     * Gets the value of the unloanedCashValueAtStart property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUnloanedCashValueAtStart() {
        return unloanedCashValueAtStart;
    }

    /**
     * Sets the value of the unloanedCashValueAtStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUnloanedCashValueAtStart(Double value) {
        this.unloanedCashValueAtStart = value;
    }

    /**
     * Gets the value of the accumulationValueAtEndOfFirstYear property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAccumulationValueAtEndOfFirstYear() {
        return accumulationValueAtEndOfFirstYear;
    }

    /**
     * Sets the value of the accumulationValueAtEndOfFirstYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAccumulationValueAtEndOfFirstYear(Double value) {
        this.accumulationValueAtEndOfFirstYear = value;
    }

    /**
     * Gets the value of the unloanedCashValueAtEndOfFirstYear property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUnloanedCashValueAtEndOfFirstYear() {
        return unloanedCashValueAtEndOfFirstYear;
    }

    /**
     * Sets the value of the unloanedCashValueAtEndOfFirstYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUnloanedCashValueAtEndOfFirstYear(Double value) {
        this.unloanedCashValueAtEndOfFirstYear = value;
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
     * Gets the value of the beginningOfYearValues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the beginningOfYearValues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBeginningOfYearValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BeginningOfYearValues }
     * 
     * 
     */
    public List<BeginningOfYearValues> getBeginningOfYearValues() {
        if (beginningOfYearValues == null) {
            beginningOfYearValues = new ArrayList<BeginningOfYearValues>();
        }
        return this.beginningOfYearValues;
    }

    /**
     * Gets the value of the annuityAnnualValues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the annuityAnnualValues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnnuityAnnualValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AnnuityAnnualValues }
     * 
     * 
     */
    public List<AnnuityAnnualValues> getAnnuityAnnualValues() {
        if (annuityAnnualValues == null) {
            annuityAnnualValues = new ArrayList<AnnuityAnnualValues>();
        }
        return this.annuityAnnualValues;
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
     * Gets the value of the anchoredScenarioSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the anchoredScenarioSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnchoredScenarioSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ScenarioSummary }
     * 
     * 
     */
    public List<ScenarioSummary> getAnchoredScenarioSummary() {
        if (anchoredScenarioSummary == null) {
            anchoredScenarioSummary = new ArrayList<ScenarioSummary>();
        }
        return this.anchoredScenarioSummary;
    }

    /**
     * Gets the value of the solveSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the solveSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSolveSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SolveSummary }
     * 
     * 
     */
    public List<SolveSummary> getSolveSummary() {
        if (solveSummary == null) {
            solveSummary = new ArrayList<SolveSummary>();
        }
        return this.solveSummary;
    }

}