/*
 * User: dlataille
 * Date: Oct 2, 2007
 * Time: 8:41:02 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package staging;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import edit.common.EDITMap;
import edit.common.EDITDate;
import edit.common.EDITBigDecimal;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class ContractClient extends HibernateEntity
{
    private Long contractClientPK;
    private Long segmentBaseFK;
    private String role;
    private int issueAge;
    private String classCode;
    private String relationshipToEmployee;
    private String underwritingClass;
    private String ratedGender;
    private String residentState;
    private String lastName;
    private String firstName;
    private String middleName;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String city;
    private String state;
    private String zip;
    private String clientId;
    private String taxIdentification;
    private String corporateName;
    private String gender;
    private EDITDate dateOfBirth;
    private String splitEqualInd;
    private EDITBigDecimal benePercentage;
    private EDITBigDecimal allocationDollars;
    private String relationshipToInsured;
    private String namePrefix;
    private String nameSuffix;
    private String beneRelationshipToInsured;
    private EDITDate dateOfDeath;
    private EDITDate proofOfDeathReceivedDate;
    private EDITDate notificationReceivedDate;
    private String employeeIdentification;
    private EDITDate terminationDate;

    private Long segmentRiderFK;

    private SegmentBase segmentBase;

    private SegmentRider segmentRider;

    private Set<Accounting> accountings;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.STAGING;


    public ContractClient()
    {
        this.accountings = new HashSet<Accounting>();
    }

    /**
     * Getter.
     * @return
     */
    public Long getContractClientPK()
    {
        return contractClientPK;
    }

    /**
     * Setter.
     * @param contractClientPK
     */
    public void setContractClientPK(Long contractClientPK)
    {
        this.contractClientPK = contractClientPK;
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

    /**
     * Getter.
     * @return
     */
    public Long getSegmentRiderFK()
    {
        return segmentRiderFK;
    }

    /**
     * Setter.
     * @param segmentBaseFK
     */
    public void setSegmentRiderFK(Long segmentRiderFK)
    {
        this.segmentRiderFK = segmentRiderFK;
    }

    /**
     * Returns the parent (base) segmentBase
     * @return
     */
    public SegmentBase getSegmentBase()
    {
        return segmentBase;
    }

    /**
     * Sets the parent (base) SegmentBase
     * @param segmentBase
     */
    public void setSegmentBase(SegmentBase segmentBase)
    {
        this.segmentBase = segmentBase;
    }

    /**
     * Returns the segmentRider
     * @return
     */
    public SegmentRider getSegmentRider()
    {
        return segmentRider;
    }

    /**
     * Sets the SegmentRider
     * @param segmentBase
     */
    public void setSegmentRider(SegmentRider segmentRider)
    {
        this.segmentRider = segmentRider;
    }

    /**
     * Getter.
     * @return
     */
    public String getRole()
    {
        return role;
    }

    /**
     * Setter.
     * @param role
     */
    public void setRole(String role)
    {
        this.role = role;
    }

    /**
     * Getter.
     * @return
     */
    public int getIssueAge()
    {
        return issueAge;
    }

    /**
     * Setter.
     * @param issueAge
     */
    public void setIssueAge(int issueAge)
    {
        this.issueAge = issueAge;
    }

    /**
     * Getter.
     * @return
     */
    public String getClassCode()
    {
        return classCode;
    }

    /**
     * Setter.
     * @param classCode
     */
    public void setClassCode(String classCode)
    {
        this.classCode = classCode;
    }

    /**
     * Getter.
     * @return
     */
    public String getRelationshipToEmployee()
    {
        return relationshipToEmployee;
    }

    /**
     * Setter.
     * @param relationshipToEmployee
     */
    public void setRelationshipToEmployee(String relationshipToEmployee)
    {
        this.relationshipToEmployee = relationshipToEmployee;
    }

    /**
     * Getter.
     * @return
     */
    public String getUnderwritingClass()
    {
        return underwritingClass;
    }

    /**
     * Setter.
     * @param underwritingClass
     */
    public void setUnderwritingClass(String underwritingClass)
    {
        this.underwritingClass = underwritingClass;
    }

    /**
     * Getter.
     * @return
     */
    public String getRatedGender()
    {
        return ratedGender;
    }

    /**
     * Setter.
     * @param ratedGender
     */
    public void setRatedGender(String ratedGender)
    {
        this.ratedGender = ratedGender;
    }

    /**
     * Getter.
     * @return
     */
    public String getResidentState()
    {
        return residentState;
    }

    /**
     * Setter.
     * @param residentState
     */
    public void setResidentState(String residentState)
    {
        this.residentState = residentState;
    }

    /**
     * Getter.
     * @return
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Setter.
     * @param lastName
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * Getter.
     * @return
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Setter.
     * @param firstName
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Getter.
     * @return
     */
    public String getMiddleName()
    {
        return middleName;
    }

    /**
     * Setter.
     * @param middleName
     */
    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    /**
     * Getter.
     * @return
     */
    public String getAddressLine1()
    {
        return addressLine1;
    }

    /**
     * Setter.
     * @param addressLine1
     */
    public void setAddressLine1(String addressLine1)
    {
        this.addressLine1 = addressLine1;
    }

    /**
     * Getter.
     * @return
     */
    public String getAddressLine2()
    {
        return addressLine2;
    }

    /**
     * Setter.
     * @param addressLine2
     */
    public void setAddressLine2(String addressLine2)
    {
        this.addressLine2 = addressLine2;
    }

    /**
     * Getter.
     * @return
     */
    public String getAddressLine3()
    {
        return addressLine3;
    }

    /**
     * Setter.
     * @param addressLine3
     */
    public void setAddressLine3(String addressLine3)
    {
        this.addressLine3 = addressLine3;
    }

    /**
     * Getter.
     * @return
     */
    public String getAddressLine4()
    {
        return addressLine4;
    }

    /**
     * Setter.
     * @param addressLine4
     */
    public void setAddressLine4(String addressLine4)
    {
        this.addressLine4 = addressLine4;
    }

    /**
     * Getter.
     * @return
     */
    public String getCity()
    {
        return city;
    }

    /**
     * Setter.
     * @param city
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * Getter.
     * @return
     */
    public String getState()
    {
        return state;
    }

    /**
     * Setter.
     * @param state
     */
    public void setState(String state)
    {
        this.state = state;
    }

    /**
     * Getter.
     * @return
     */
    public String getZip()
    {
        return zip;
    }

    /**
     * Setter.
     * @param zip
     */
    public void setZip(String zip)
    {
        this.zip = zip;
    }

    /**
     * Getter.
     * @return
     */
    public String getClientId()
    {
        return clientId;
    }

    /**
     * Setter.
     * @param clientId
     */
    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    /**
     * Getter.
     * @return
     */
    public String getTaxIdentification()
    {
        return taxIdentification;
    }

    /**
     * Setter.
     * @param taxIdentification
     */
    public void setTaxIdentification(String taxIdentification)
    {
        this.taxIdentification = taxIdentification;
    }

    /**
     * Getter.
     * @return
     */
    public String getCorporateName()
    {
        return corporateName;
    }

    /**
     * Setter.
     * @param corporateName
     */
    public void setCorporateName(String corporateName)
    {
        this.corporateName = corporateName;
    }

    /**
     * Getter.
     * @return
     */
    public String getGender()
    {
        return gender;
    }

    /**
     * Setter.
     * @param gender
     */
    public void setGender(String gender)
    {
        this.gender = gender;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getDateOfBirth()
    {
        return dateOfBirth;
    }

    /**
     * Setter.
     * @param dateOfBirth
     */
    public void setDateOfBirth(EDITDate dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Getter.
     * @return
     */
    public String getSplitEqualInd()
    {
        return splitEqualInd;
    }

    /**
     * Setter.
     * @param splitEqualInd
     */
    public void setSplitEqualInd(String splitEqualInd)
    {
        this.splitEqualInd = splitEqualInd;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getBenePercentage()
    {
        return benePercentage;
    }

    /**
     * Setter.
     * @param benePercentage
     */
    public void setBenePercentage(EDITBigDecimal benePercentage)
    {
        this.benePercentage = benePercentage;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAllocationDollars()
    {
        return allocationDollars;
    }

    /**
     * Setter.
     * @param allocationDollars
     */
    public void setAllocationDollars(EDITBigDecimal allocationDollars)
    {
        this.allocationDollars = allocationDollars;
    }

    /**
     * Getter.
     * @return
     */
    public String getRelationshipToInsured()
    {
        return relationshipToInsured;
    }

    /**
     * Setter.
     * @param relationshipToInsured
     */
    public void setRelationshipToInsured(String relationshipToInsured)
    {
        this.relationshipToInsured = relationshipToInsured;
    }

    /**
     * Getter.
     * @return
     */
    public String getNamePrefix()
    {
        return namePrefix;
    }

    /**
     * Setter.
     * @param namePrefix
     */
    public void setNamePrefix(String namePrefix)
    {
        this.namePrefix = namePrefix;
    }

    /**
     * Getter.
     * @return
     */
    public String getNameSuffix()
    {
        return nameSuffix;
    }

    /**
     * Setter.
     * @param nameSuffix
     */
    public void setNameSuffix(String nameSuffix)
    {
        this.nameSuffix = nameSuffix;
    }

    /**
     * Getter.
     * @return
     */
    public String getBeneRelationshipToInsured()
    {
        return beneRelationshipToInsured;
    }

    /**
     * Setter.
     * @param beneRelationshipToInsured
     */
    public void setBeneRelationshipToInsured(String beneRelationshipToInsured)
    {
        this.beneRelationshipToInsured = beneRelationshipToInsured;
    }

    /**
     * Getter.
     * @return
     */
    public Set<Accounting> getAccountings()
    {
        return accountings;
    }

    /**
     * Setter.
     * @param accountings
     */
    public void setAccountings(Set<Accounting> accountings)
    {
        this.accountings = accountings;
    }

    /**
     * Add another accounting to the current mapped accountings.
     * @param accounting
     */
    public void addAccounting(Accounting accounting)
    {
        this.accountings.add(accounting);
    }

    public String getDatabase()
    {
        return ContractClient.DATABASE;
    }
    
    /**
     * Setter.
     * @param dateOfDeath
     */
    public void setDateOfDeath(EDITDate dateOfDeath)
    {
        this.dateOfDeath = dateOfDeath;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getDateOfDeath()
    {
        return dateOfDeath;
    }

    /**
     * Setter.
     * @param proofOfDeathReceivedDate
     */
    public void setProofOfDeathReceivedDate(EDITDate proofOfDeathReceivedDate)
    {
        this.proofOfDeathReceivedDate = proofOfDeathReceivedDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getProofOfDeathReceivedDate()
    {
        return proofOfDeathReceivedDate;
    }
    
    /**
     * Setter.
     * @param notificationReceivedDate
     */
    public void setNotificationReceivedDate(EDITDate notificationReceivedDate)
    {
        this.notificationReceivedDate = notificationReceivedDate;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getNotificationReceivedDate()
    {
        return notificationReceivedDate;
    }

    /**
     * Setter.
     * @param employeeIdentification
     */
    public void setEmployeeIdentification(String employeeIdentification)
    {
        this.employeeIdentification = employeeIdentification;
    }

    /**
     * Setter
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
    public EDITDate getTerminationDate()
    {
        return terminationDate;
    }

    /**
     * Getter.
     * @return
     */
    public String getEmployeeIdentification()
    {
        return employeeIdentification;
    }

    public static ContractClient[] findBySegmentBaseFK(Long segmentBaseFK, String database)
    {
        String hql = " select sb from SegmentBase sb" +
                    " where sb.SegmentBaseFK = :segmentBaseFK";

        EDITMap params = new EDITMap();
        params.put("segmentBaseFK", segmentBaseFK);

        List<ContractClient> results = SessionHelper.executeHQL(hql, params, database);

        return results.toArray(new ContractClient[results.size()]);
    }
}
