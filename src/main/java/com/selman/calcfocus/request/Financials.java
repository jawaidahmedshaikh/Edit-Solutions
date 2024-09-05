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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Financials complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Financials">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="employment" type="{http://xmlns.calcfocus.com/services/calc}Employment" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="income" type="{http://xmlns.calcfocus.com/services/calc}Income" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asset" type="{http://xmlns.calcfocus.com/services/calc}Asset" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="debt" type="{http://xmlns.calcfocus.com/services/calc}Debt" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="existingInsurancePolicy" type="{http://xmlns.calcfocus.com/services/calc}InsurancePolicySummary" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Financials", propOrder = {
    "employment",
    "income",
    "asset",
    "debt",
    "existingInsurancePolicy"
})
public class Financials {

    protected List<Employment> employment;
    protected List<Income> income;
    protected List<Asset> asset;
    protected List<Debt> debt;
    protected List<InsurancePolicySummary> existingInsurancePolicy;

    /**
     * Gets the value of the employment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the employment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmployment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Employment }
     * 
     * 
     */
    public List<Employment> getEmployment() {
        if (employment == null) {
            employment = new ArrayList<Employment>();
        }
        return this.employment;
    }

    /**
     * Gets the value of the income property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the income property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncome().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Income }
     * 
     * 
     */
    public List<Income> getIncome() {
        if (income == null) {
            income = new ArrayList<Income>();
        }
        return this.income;
    }

    /**
     * Gets the value of the asset property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asset property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsset().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Asset }
     * 
     * 
     */
    public List<Asset> getAsset() {
        if (asset == null) {
            asset = new ArrayList<Asset>();
        }
        return this.asset;
    }

    /**
     * Gets the value of the debt property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the debt property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDebt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Debt }
     * 
     * 
     */
    public List<Debt> getDebt() {
        if (debt == null) {
            debt = new ArrayList<Debt>();
        }
        return this.debt;
    }

    /**
     * Gets the value of the existingInsurancePolicy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the existingInsurancePolicy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExistingInsurancePolicy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InsurancePolicySummary }
     * 
     * 
     */
    public List<InsurancePolicySummary> getExistingInsurancePolicy() {
        if (existingInsurancePolicy == null) {
            existingInsurancePolicy = new ArrayList<InsurancePolicySummary>();
        }
        return this.existingInsurancePolicy;
    }

}
