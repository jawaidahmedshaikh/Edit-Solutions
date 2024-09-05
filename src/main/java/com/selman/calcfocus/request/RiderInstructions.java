//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.03.23 at 11:48:17 AM EDT 
//


package com.selman.calcfocus.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RiderInstructions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RiderInstructions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="faceDeterminationRule" type="{http://xmlns.calcfocus.com/services/calc}FaceDeterminationRule"/>
 *         &lt;element name="faceInputValueRule" type="{http://xmlns.calcfocus.com/services/calc}FaceInputValueRule" minOccurs="0"/>
 *         &lt;element name="multipleOfParentFace" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="inputValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiderInstructions", propOrder = {
    "faceDeterminationRule",
    "faceInputValueRule",
    "multipleOfParentFace",
    "inputValue"
})
public class RiderInstructions {

    @XmlElement(required = true)
    protected FaceDeterminationRule faceDeterminationRule;
    protected FaceInputValueRule faceInputValueRule;
    protected Double multipleOfParentFace;
    protected Double inputValue;

    /**
     * Gets the value of the faceDeterminationRule property.
     * 
     * @return
     *     possible object is
     *     {@link FaceDeterminationRule }
     *     
     */
    public FaceDeterminationRule getFaceDeterminationRule() {
        return faceDeterminationRule;
    }

    /**
     * Sets the value of the faceDeterminationRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link FaceDeterminationRule }
     *     
     */
    public void setFaceDeterminationRule(FaceDeterminationRule value) {
        this.faceDeterminationRule = value;
    }

    /**
     * Gets the value of the faceInputValueRule property.
     * 
     * @return
     *     possible object is
     *     {@link FaceInputValueRule }
     *     
     */
    public FaceInputValueRule getFaceInputValueRule() {
        return faceInputValueRule;
    }

    /**
     * Sets the value of the faceInputValueRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link FaceInputValueRule }
     *     
     */
    public void setFaceInputValueRule(FaceInputValueRule value) {
        this.faceInputValueRule = value;
    }

    /**
     * Gets the value of the multipleOfParentFace property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMultipleOfParentFace() {
        return multipleOfParentFace;
    }

    /**
     * Sets the value of the multipleOfParentFace property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMultipleOfParentFace(Double value) {
        this.multipleOfParentFace = value;
    }

    /**
     * Gets the value of the inputValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getInputValue() {
        return inputValue;
    }

    /**
     * Sets the value of the inputValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setInputValue(Double value) {
        this.inputValue = value;
    }

}
