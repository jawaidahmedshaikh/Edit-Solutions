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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MonthEndValuation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MonthEndValuation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="commissionTarget" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="minimumPremiumTarget" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="surrenderChargeTarget" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="definitionOfLifeInsurance" type="{http://xmlns.calcfocus.com/services/calc}DefinitionOfLifeInsurance" minOccurs="0"/>
 *         &lt;element name="sevenPayPremium" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="guidelineLevelPremium" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="guidelineSinglePremium" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="statCRVM" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="fITCRVM" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="MonthEndValues" type="{http://xmlns.calcfocus.com/services/calc}MonthEndValues" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MonthEndValuation", propOrder = {
    "commissionTarget",
    "minimumPremiumTarget",
    "surrenderChargeTarget",
    "definitionOfLifeInsurance",
    "sevenPayPremium",
    "guidelineLevelPremium",
    "guidelineSinglePremium",
    "statCRVM",
    "fitcrvm",
    "monthEndValues"
})
public class MonthEndValuation {

    protected Double commissionTarget;
    protected Double minimumPremiumTarget;
    protected Double surrenderChargeTarget;
    protected DefinitionOfLifeInsurance definitionOfLifeInsurance;
    protected Double sevenPayPremium;
    protected Double guidelineLevelPremium;
    protected Double guidelineSinglePremium;
    protected Double statCRVM;
    @XmlElement(name = "fITCRVM")
    protected Double fitcrvm;
    @XmlElement(name = "MonthEndValues")
    protected List<MonthEndValues> monthEndValues;

    /**
     * Gets the value of the commissionTarget property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCommissionTarget() {
        return commissionTarget;
    }

    /**
     * Sets the value of the commissionTarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCommissionTarget(Double value) {
        this.commissionTarget = value;
    }

    /**
     * Gets the value of the minimumPremiumTarget property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMinimumPremiumTarget() {
        return minimumPremiumTarget;
    }

    /**
     * Sets the value of the minimumPremiumTarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMinimumPremiumTarget(Double value) {
        this.minimumPremiumTarget = value;
    }

    /**
     * Gets the value of the surrenderChargeTarget property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSurrenderChargeTarget() {
        return surrenderChargeTarget;
    }

    /**
     * Sets the value of the surrenderChargeTarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSurrenderChargeTarget(Double value) {
        this.surrenderChargeTarget = value;
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
     * Gets the value of the statCRVM property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getStatCRVM() {
        return statCRVM;
    }

    /**
     * Sets the value of the statCRVM property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setStatCRVM(Double value) {
        this.statCRVM = value;
    }

    /**
     * Gets the value of the fitcrvm property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFITCRVM() {
        return fitcrvm;
    }

    /**
     * Sets the value of the fitcrvm property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFITCRVM(Double value) {
        this.fitcrvm = value;
    }

    /**
     * Gets the value of the monthEndValues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monthEndValues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonthEndValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonthEndValues }
     * 
     * 
     */
    public List<MonthEndValues> getMonthEndValues() {
        if (monthEndValues == null) {
            monthEndValues = new ArrayList<MonthEndValues>();
        }
        return this.monthEndValues;
    }

}
