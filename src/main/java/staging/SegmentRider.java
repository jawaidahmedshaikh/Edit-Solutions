/*
 * User: dlataille
 * Date: Oct 2, 2007
 * Time: 8:24:59 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package staging;

import edit.common.*;
import edit.services.db.hibernate.*;
import staging.ValueAtIssue;

import java.util.*;

public class SegmentRider extends HibernateEntity
{
    private Long segmentRiderPK;
    private Long segmentBaseFK;
    private EDITDate effectiveDate;
    private EDITDate terminationDate;
    private String segmentStatus;
    private String optionCode;
    private String ratedGenderCT;
    private String underwritingClassCT;
    private String groupPlan;
    private EDITBigDecimal units;
    private EDITBigDecimal faceAmount;
    private EDITDate paidToDate;
    private EDITBigDecimal annualPremium;
    private EDITBigDecimal guarPaidUpTerm;
    private EDITBigDecimal nonGuarPaidUpTerm;
    private EDITBigDecimal oneYearTerm;
    private int ageAtIssue;
    private EDITBigDecimal originalUnits;
    private String segmentName;
    private EDITBigDecimal eobCum;
    private EDITBigDecimal eobMaximum;
    private int multipleFactor;
    private String gioOption;

    private SegmentBase segmentBase;

    private Set<ValueAtIssue> valueAtIssues;
    private Set<ContractClient> contractClients;

    public static final String DATABASE = SessionHelper.STAGING;

    public SegmentRider()
    {
        init();
    }

    private void init()
    {
        valueAtIssues = new HashSet();
        contractClients = new HashSet<ContractClient>();
    }

    /**
     * Getter.
     * @return
     */
    public Long getSegmentRiderPK()
    {
        return segmentRiderPK;
    }

    /**
     * Setter.
     * @param segmentRiderPK
     */
    public void setSegmentRiderPK(Long segmentRiderPK)
    {
        this.segmentRiderPK = segmentRiderPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getSegmentBaseFK()
    {
        return segmentBaseFK;
    }

    /**
     * Setter.
     * @param segmentBaseFK
     */
    public void setSegmentBaseFK(Long segmentBaseFK)
    {
        this.segmentBaseFK = segmentBaseFK;
    }

    public SegmentBase getSegmentBase()
    {
        return segmentBase;
    }

    public void setSegmentBase(SegmentBase segmentBase)
    {
        this.segmentBase = segmentBase;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    /**
     * Setter.
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getTerminationDate()
    {
        return terminationDate;
    }

    /**
     * Setter.
     * @param terminationDate
     */
    public void setTerminationDate(EDITDate terminationDate)
    {
        this.terminationDate = terminationDate;
    }

    /**
     * Getter.
     * @return
     */
    public String getSegmentStatus()
    {
        return segmentStatus;
    }

    /**
     * Setter.
     * @param segmentStatus
     */
    public void setSegmentStatus(String segmentStatus)
    {
        this.segmentStatus = segmentStatus;
    }

    /**
     * Getter.
     * @return
     */
    public String getOptionCode()
    {
        return optionCode;
    }

    /**
     * Setter.
     * @param optionCode
     */
    public void setOptionCode(String optionCode)
    {
        this.optionCode = optionCode;
    }
    
    
    /**
     * Getter.
     * @return
     */
    public String getRatedGenderCT()
    {
        return ratedGenderCT;
    }

    /**
     * Setter.
     * @param ratedGenderCT
     */
    public void setRatedGenderCT(String ratedGenderCT)
    {
        this.ratedGenderCT = ratedGenderCT;
    }
    /**
     * Getter.
     * @return
     */
    public String getUnderwritingClassCT()
    {
        return underwritingClassCT;
    }

    /**
     * Setter.
     * @param underwritingClassCt
     */
    public void setUnderwritingClassCT(String underwritingClassCT)
    {
        this.underwritingClassCT = underwritingClassCT;
    }
     /**
     * Getter.
     * @return
     */
    public String getGroupPlan()
    {
        return groupPlan;
    }

    /**
     * Setter.
     * @param groupPlan
     */
    public void setGroupPlan(String groupPlan)
    {
        this.groupPlan = groupPlan;
    }
    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getUnits()
    {
        return units;
    }

    /**
     * Setter.
     * @param units
     */
    public void setUnits(EDITBigDecimal units)
    {
        this.units = units;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getFaceAmount()
    {
        return faceAmount;
    }

    /**
     * Setter.
     * @param faceAmount
     */
    public void setFaceAmount(EDITBigDecimal faceAmount)
    {
        this.faceAmount = faceAmount;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getPaidToDate()
    {
        return paidToDate;
    }

    /**
     * Setter.
     * @param paidToDate
     */
    public void setPaidToDate(EDITDate paidToDate)
    {
        this.paidToDate = paidToDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAnnualPremium()
    {
        return annualPremium;
    }

    /**
     * Setter.
     * @param annualPremium
     */
    public void setAnnualPremium(EDITBigDecimal annualPremium)
    {
        this.annualPremium = annualPremium;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuarPaidUpTerm()
    {
        return guarPaidUpTerm;
    }

    /**
     * Setter.
     * @param guarPaidUpTerm
     */
    public void setGuarPaidUpTerm(EDITBigDecimal guarPaidUpTerm)
    {
        this.guarPaidUpTerm = guarPaidUpTerm;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getNonGuarPaidUpTerm()
    {
        return nonGuarPaidUpTerm;
    }

    /**
     * Setter.
     * @param nonGuarPdUpTerm
     */
    public void setNonGuarPaidUpTerm(EDITBigDecimal nonGuarPdUpTerm)
    {
        this.nonGuarPaidUpTerm = nonGuarPdUpTerm;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getOneYearTerm()
    {
        return oneYearTerm;
    }

    /**
     * Setter.
     * @param oneYearTerm
     */
    public void setOneYearTerm(EDITBigDecimal oneYearTerm)
    {
        this.oneYearTerm = oneYearTerm;
    }

    /**
     * Getter.
     * @return
     */
    public int getAgeAtIssue()
    {
        return ageAtIssue;
    }

    /**
     * Setter.
     * @param agetAtIssue
     */
    public void setAgeAtIssue(int ageAtIssue)
    {
        this.ageAtIssue = ageAtIssue;
    }

    /**
     * Setter.
     * @param originalUnits
     */
    public void setOriginalUnits(EDITBigDecimal originalUnits)
    {
        this.originalUnits = originalUnits;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getOriginalUnits()
    {
        return originalUnits;
    }

    public String getDatabase()
    {
        return SegmentRider.DATABASE;
    }
    
    /**
     * Setter.
     * @param segmentName
     */
    public void setSegmentName(String segmentName)
    {
        this.segmentName = segmentName;
    }

    /**
     * Getter.
     * @return
     */
    public String getSegmentName()
    {
        return segmentName;
    }

    /**
     * Setter.
     * @param eobCum
     */
    public void setEOBCum(EDITBigDecimal eobCum)
    {
        this.eobCum = eobCum;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getEOBCum()
    {
        return this.eobCum;
    }

    /**
     * Setter.
     * @param eobMaximum
     */
    public void setEOBMaximum(EDITBigDecimal eobMaximum)
    {
        this.eobMaximum = eobMaximum;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getEOBMaximum()
    {
        return this.eobMaximum;
    }

    /**
     * Setter.
     * @param multipleFactor
     */
    public void setMultipleFactor(int multipleFactor)
    {
        this.multipleFactor = multipleFactor;
    }

    /**
     * Getter.
     * @return
     */
    public int getMultipleFactor()
    {
        return this.multipleFactor;
    }

    /**
     * Getter
     * @return
     */
    public String getGIOOption()
    {
        return this.gioOption;
    }

    /**
     * Setter
     * @param GIOOption
     */
    public void setGIOOption(String gioOption)
    {
        this.gioOption = gioOption;
    }

    /**
     * Getter
     * @return  set of valueAtIssues
     */
    public Set<ValueAtIssue> getValueAtIssues()
    {
        return valueAtIssues;
    }

    /**
     * Setter
     * @param valueAtIssues      set of valueAtIssues
     */
    public void setValueAtIssues(Set<ValueAtIssue> valueAtIssues)
    {
        this.valueAtIssues = valueAtIssues;
    }

    /**
     * Adds a ValueAtIssue to the set of children
     * @param valueAtIssue
     */
    public void addValueAtIssue(ValueAtIssue valueAtIssue)
    {
        this.getValueAtIssues().add(valueAtIssue);

        valueAtIssue.setSegmentRider(this);

        SessionHelper.saveOrUpdate(valueAtIssue, SegmentRider.DATABASE);
    }

    /**
     * Getter.
     * @return
     */
    public Set<ContractClient> getContractClients()
    {
        return contractClients;
    }

    /**
     * Setter.
     * @param contractClients
     */
    public void setContractClients(Set<ContractClient> contractClients)
    {
        this.contractClients = contractClients;
    }

    /**
     * Add another contractClient to the current mapped contractClients.
     * @param contractClient
     */
    public void addContractClient(ContractClient contractClient)
    {
        this.contractClients.add(contractClient);
    }

    /**
     * Removes a ValueAtIssue from the set of children
     * @param valueAtIssue
     */
    public void removeValueAtIssue(ValueAtIssue valueAtIssue)
    {
        this.getValueAtIssues().remove(valueAtIssue);

        valueAtIssue.setSegmentRider(null);

        SessionHelper.saveOrUpdate(valueAtIssue, SegmentRider.DATABASE);
    }

}
