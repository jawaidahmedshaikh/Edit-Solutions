package com.selman.calcfocus.correspondence.builder;

import java.util.Date;

public class PartyAddressValues {
	Long contractClientFK;
	Long segmentBaseFK;
	String firstName;
	String lastName;
	Date dob;
	String gender;
	String tobaccoUse;
	String partyFamilyRelationshipType;

	String addressGUID;
	String line1;
	String line2;
	String line3;
	String city;
	String state;
	String zip;

	public Long getContractClientFK() {
		return contractClientFK;
	}
	public void setContractClientFK(Long contractClientFK) {
		this.contractClientFK = contractClientFK;
	}
	
	public Long getSegmentBaseFK() {
		return segmentBaseFK;
	}
	public void setSegmentBaseFK(Long segmentBaseFK) {
		this.segmentBaseFK = segmentBaseFK;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTobaccoUse() {
		return tobaccoUse;
	}
	public void setTobaccoUse(String tobaccoUse) {
		this.tobaccoUse = tobaccoUse;
	}
	public String getPartyFamilyRelationshipType() {
		return partyFamilyRelationshipType;
	}
	public void setPartyFamilyRelationshipType(String partyFamilyRelationshipType) {
		this.partyFamilyRelationshipType = partyFamilyRelationshipType;
	}
	public String getAddressGUID() {
		return addressGUID;
	}
	public void setAddressGUID(String addressGUID) {
		this.addressGUID = addressGUID;
	}
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getLine3() {
		return line3;
	}
	public void setLine3(String line3) {
		this.line3 = line3;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	

}
