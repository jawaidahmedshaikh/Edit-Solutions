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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TransactionSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionSummary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transactionType" type="{http://xmlns.calcfocus.com/services/calc}TransactionType" minOccurs="0"/>
 *         &lt;element name="transactionNote" type="{http://xmlns.calcfocus.com/services/calc}TransactionNoteType" minOccurs="0"/>
 *         &lt;element name="ScenarioName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scenarioType" type="{http://xmlns.calcfocus.com/services/calc}ScenarioType" minOccurs="0"/>
 *         &lt;element name="attainedAge" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="effectiveDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="variableFundTransaction" type="{http://xmlns.calcfocus.com/services/calc}VariableFundTransaction" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="accountingEntry" type="{http://xmlns.calcfocus.com/services/calc}AccountingEntry" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="commissionableAmount" type="{http://xmlns.calcfocus.com/services/calc}CommissionableAmount" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="amountRequested" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="amountAccepted" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="transactionNotes" type="{http://xmlns.calcfocus.com/services/calc}TransactionNoteType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="noteDescription" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="dOLIMECValues" type="{http://xmlns.calcfocus.com/services/calc}DOLIMECEndOfYearValues" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionSummary", propOrder = {
    "transactionType",
    "transactionNote",
    "scenarioName",
    "scenarioType",
    "attainedAge",
    "effectiveDate",
    "description",
    "variableFundTransaction",
    "accountingEntry",
    "commissionableAmount",
    "amountRequested",
    "amountAccepted",
    "transactionNotes",
    "noteDescription",
    "dolimecValues"
})
public class TransactionSummary {

    protected TransactionType transactionType;
    protected TransactionNoteType transactionNote;
    @XmlElement(name = "ScenarioName")
    protected String scenarioName;
    protected ScenarioType scenarioType;
    protected Integer attainedAge;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar effectiveDate;
    protected String description;
    protected List<VariableFundTransaction> variableFundTransaction;
    protected List<AccountingEntry> accountingEntry;
    protected List<CommissionableAmount> commissionableAmount;
    protected Double amountRequested;
    protected Double amountAccepted;
    protected List<TransactionNoteType> transactionNotes;
    protected List<String> noteDescription;
    @XmlElement(name = "dOLIMECValues")
    protected DOLIMECEndOfYearValues dolimecValues;

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
     * Gets the value of the transactionNote property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionNoteType }
     *     
     */
    public TransactionNoteType getTransactionNote() {
        return transactionNote;
    }

    /**
     * Sets the value of the transactionNote property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionNoteType }
     *     
     */
    public void setTransactionNote(TransactionNoteType value) {
        this.transactionNote = value;
    }

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
     * Gets the value of the scenarioType property.
     * 
     * @return
     *     possible object is
     *     {@link ScenarioType }
     *     
     */
    public ScenarioType getScenarioType() {
        return scenarioType;
    }

    /**
     * Sets the value of the scenarioType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScenarioType }
     *     
     */
    public void setScenarioType(ScenarioType value) {
        this.scenarioType = value;
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
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the variableFundTransaction property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the variableFundTransaction property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVariableFundTransaction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VariableFundTransaction }
     * 
     * 
     */
    public List<VariableFundTransaction> getVariableFundTransaction() {
        if (variableFundTransaction == null) {
            variableFundTransaction = new ArrayList<VariableFundTransaction>();
        }
        return this.variableFundTransaction;
    }

    /**
     * Gets the value of the accountingEntry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accountingEntry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccountingEntry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AccountingEntry }
     * 
     * 
     */
    public List<AccountingEntry> getAccountingEntry() {
        if (accountingEntry == null) {
            accountingEntry = new ArrayList<AccountingEntry>();
        }
        return this.accountingEntry;
    }

    /**
     * Gets the value of the commissionableAmount property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the commissionableAmount property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommissionableAmount().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommissionableAmount }
     * 
     * 
     */
    public List<CommissionableAmount> getCommissionableAmount() {
        if (commissionableAmount == null) {
            commissionableAmount = new ArrayList<CommissionableAmount>();
        }
        return this.commissionableAmount;
    }

    /**
     * Gets the value of the amountRequested property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAmountRequested() {
        return amountRequested;
    }

    /**
     * Sets the value of the amountRequested property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAmountRequested(Double value) {
        this.amountRequested = value;
    }

    /**
     * Gets the value of the amountAccepted property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAmountAccepted() {
        return amountAccepted;
    }

    /**
     * Sets the value of the amountAccepted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAmountAccepted(Double value) {
        this.amountAccepted = value;
    }

    /**
     * Gets the value of the transactionNotes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transactionNotes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransactionNotes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransactionNoteType }
     * 
     * 
     */
    public List<TransactionNoteType> getTransactionNotes() {
        if (transactionNotes == null) {
            transactionNotes = new ArrayList<TransactionNoteType>();
        }
        return this.transactionNotes;
    }

    /**
     * Gets the value of the noteDescription property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the noteDescription property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNoteDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNoteDescription() {
        if (noteDescription == null) {
            noteDescription = new ArrayList<String>();
        }
        return this.noteDescription;
    }

    /**
     * Gets the value of the dolimecValues property.
     * 
     * @return
     *     possible object is
     *     {@link DOLIMECEndOfYearValues }
     *     
     */
    public DOLIMECEndOfYearValues getDOLIMECValues() {
        return dolimecValues;
    }

    /**
     * Sets the value of the dolimecValues property.
     * 
     * @param value
     *     allowed object is
     *     {@link DOLIMECEndOfYearValues }
     *     
     */
    public void setDOLIMECValues(DOLIMECEndOfYearValues value) {
        this.dolimecValues = value;
    }

}