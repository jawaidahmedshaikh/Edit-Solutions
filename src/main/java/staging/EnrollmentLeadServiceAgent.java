/*
 * User: dlataill
 * Date: June 26, 2007
 * Time: 12:29:46 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package staging;

import edit.common.*;
import edit.services.db.hibernate.*;

import java.util.Set;
import java.util.HashSet;

public class EnrollmentLeadServiceAgent extends HibernateEntity
{
    /**
     * Primary key
     */
    private Long enrollmentLeadServiceAgentPK;
    private Long enrollmentFK;
    private EDITDate effectiveDate;
    private EDITDate terminationDate;
    private String role;
    private String region;
    private String agentNumber;
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
    private String taxIdentification;
    private String corporateName;
    private String agentPhoneNbr;

    //  Parents
    private Enrollment enrollment;

    /**
     * Target database for lookups
     */
    public static final String DATABASE = SessionHelper.STAGING;

    /**
     * Constructor
     */
    public EnrollmentLeadServiceAgent()
    {
        init();
    }

    private void init()
    {
    }

    /**
     * Getter.
     * @return
     */
    public Long getEnrollmentLeadServiceAgentPK()
    {
        return enrollmentLeadServiceAgentPK;
    }

    /**
     * Setter.
     * @param enrollmentLeadServiceAgentPK
     */
    public void setEnrollmentLeadServiceAgentPK(Long enrollmentLeadServiceAgentPK)
    {
        this.enrollmentLeadServiceAgentPK = enrollmentLeadServiceAgentPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getEnrollmentFK()
    {
        return enrollmentFK;
    }

    /**
     * Setter.
     * @param enrollmentFK
     */
    public void setEnrollmentFK(Long enrollmentFK)
    {
        this.enrollmentFK = enrollmentFK;
    }

    /**
     * Getter.
     * @return
     */
    public Enrollment getEnrollment()
    {
        return enrollment;
    }

    /**
     * Setter.
     * @param enrollment
     */
    public void setEnrollment(Enrollment enrollment)
    {
        this.enrollment = enrollment;
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
    public String getRegion()
    {
        return region;
    }

    /**
     * Setter.
     * @param region
     */
    public void setRegion(String region)
    {
        this.region = region;
    }

    /**
     * Getter.
     * @return
     */
    public String getAgentNumber()
    {
        return agentNumber;
    }

    /**
     * Setter.
     * @param agentNumber
     */
    public void setAgentNumber(String agentNumber)
    {
        this.agentNumber = agentNumber;
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
    public String getAgentPhoneNbr()
    {
        return agentPhoneNbr;
    }

    /**
     * Setter.
     * @param agentPhoneNbr
     */
    public void setAgentPhoneNbr(String agentPhoneNbr)
    {
        this.agentPhoneNbr = agentPhoneNbr;
    }

    public String getDatabase()
    {
        return EnrollmentLeadServiceAgent.DATABASE;
    }
}
