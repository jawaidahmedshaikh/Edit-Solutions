/*
 * User: sdorman
 * Date: Apr 25, 2008
 * Time: 12:45:46 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package group;

import agent.Agent;

import client.ClientDetail;
import client.ClientAddress;
import client.ContactInformation;

import contract.Segment;

import edit.common.*;
import edit.common.exceptions.EDITCaseException;

import edit.services.db.hibernate.*;

import fission.utility.DateTimeUtil;

import java.util.*;

import role.*;
import staging.IStaging;
import staging.StagingContext;



public class EnrollmentLeadServiceAgent extends HibernateEntity implements IStaging
{
    /**
     * Primary key
     */
    private Long enrollmentLeadServiceAgentPK;

    private EDITDate effectiveDate;
    private EDITDate terminationDate;
    private String regionCT;

    //  Parents
    private Enrollment enrollment;
    private Long enrollmentFK;
    private ClientRole clientRole;
    private Long clientRoleFK;

    /**
     * Target database for lookups
     */
    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;



    /**
     * Constructor
     */
    public EnrollmentLeadServiceAgent()
    {
        this.terminationDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
    }

    public Long getEnrollmentLeadServiceAgentPK()
    {
        return enrollmentLeadServiceAgentPK;
    }

    public void setEnrollmentLeadServiceAgentPK(Long enrollmentLeadServiceAgentPK)
    {
        this.enrollmentLeadServiceAgentPK = enrollmentLeadServiceAgentPK;
    }

    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    public EDITDate getTerminationDate()
    {
        return terminationDate;
    }

    public void setTerminationDate(EDITDate terminationDate)
    {
        this.terminationDate = terminationDate;
    }

    public String getRegionCT()
    {
        return regionCT;
    }

    public void setRegionCT(String regionCT)
    {
        this.regionCT = regionCT;
    }

    public Enrollment getEnrollment()
    {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment)
    {
        this.enrollment = enrollment;
    }

    public Long getEnrollmentFK()
    {
        return this.enrollmentFK;
    }

    public void setEnrollmentFK(Long enrollmentFK)
    {
        this.enrollmentFK = enrollmentFK;
    }

    public ClientRole getClientRole()
    {
        return clientRole;
    }

    public void setClientRole(ClientRole clientRole)
    {
        this.clientRole = clientRole;
    }

    public Long getClientRoleFK()
    {
        return this.clientRoleFK;
    }

    public void setClientRoleFK(Long clientRoleFK)
    {
        this.clientRoleFK = clientRoleFK;
    }

    public String getDatabase()
    {
        return EnrollmentLeadServiceAgent.DATABASE;
    }
    
    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     * @return
     */
    public String getUIEffectiveDate()
    {
        String date = null;

        if (getEffectiveDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getEffectiveDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUIEffectiveDate(String uiEffectiveDate)
    {
        setEffectiveDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiEffectiveDate));
    }    
    
    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     * @return
     */
    public String getUITerminationDate()
    {
        String date = null;

        if (getTerminationDate() != null)
        {
            date = DateTimeUtil.formatEDITDateAsMMDDYYYY(this.getTerminationDate());
        }

        return date;
    }

    /**
     * Convenience method to support UI's need for mm/dd/yyyy.
     */
    public void setUITerminationDate(String uiTerminationDate)
    {
        setTerminationDate(DateTimeUtil.getEDITDateFromMMDDYYYY(uiTerminationDate));
    }     

    /**
     * Finds the EnrollmentLeadServiceAgent with the given primary key
     *
     * @param enrollmentPK
     *
     * @return
     */
    public static EnrollmentLeadServiceAgent findByPK(Long enrollmentLeadServiceAgentPK)
    {
        String hql = " select enrollmentLeadServiceAgent from EnrollmentLeadServiceAgent enrollmentLeadServiceAgent" +
                     " where enrollmentLeadServiceAgent.EnrollmentLeadServiceAgentPK = :enrollmentLeadServiceAgentPK";

        EDITMap params = new EDITMap();

        params.put("enrollmentLeadServiceAgentPK", enrollmentLeadServiceAgentPK);

        List<EnrollmentLeadServiceAgent> results = SessionHelper.executeHQL(hql, params, EnrollmentLeadServiceAgent.DATABASE);

        if (results.size() > 0)
        {
            return (EnrollmentLeadServiceAgent) results.get(0);
        }
        else
        {
            return null;
        }
    }

    /**
     * Finds all EnrollmentLeadServiceAgents
     *
     * @return array of Enrollment objects
     */
    public static EnrollmentLeadServiceAgent[] findAll()
    {
        String hql = " from EnrollmentLeadServiceAgent enrollmentLeadServiceAgent";

        EDITMap params = new EDITMap();

        List<EnrollmentLeadServiceAgent> results = SessionHelper.executeHQL(hql, params, EnrollmentLeadServiceAgent.DATABASE);

        return results.toArray(new EnrollmentLeadServiceAgent[results.size()]);
    }

    /**
     * Finds all EnrollmentLeadServiceAgents for a given enrollmentFK
     *
     * @param enrollmentFK
     * @return
     */
    public static EnrollmentLeadServiceAgent[] findByEnrollmentFK(Long enrollmentFK)
    {
        String hql = " select enrollmentLeadServiceAgent from EnrollmentLeadServiceAgent enrollmentLeadServiceAgent" +
                     " where enrollmentLeadServiceAgent.EnrollmentFK = :enrollmentFK";

        EDITMap params = new EDITMap();

        params.put("enrollmentFK", enrollmentFK);

        List<EnrollmentLeadServiceAgent> results = SessionHelper.executeHQL(hql, params, EnrollmentLeadServiceAgent.DATABASE);

        return results.toArray(new EnrollmentLeadServiceAgent[results.size()]);
    }

    /**
     * Finder. Sorts by EffectiveDate desc.
     * @param enrollmentPK
     * @return
     */
    public static EnrollmentLeadServiceAgent[] findBy_EnrollmentPK(Long enrollmentPK)
    {
        String hql = " from EnrollmentLeadServiceAgent enrollmentLeadServiceAgent" +
                     " where enrollmentLeadServiceAgent.EnrollmentFK = :enrollmentPK" +
                     " order by enrollmentLeadServiceAgent.EffectiveDate desc";
        
        Map params = new EDITMap("enrollmentPK", enrollmentPK);
        
        List<EnrollmentLeadServiceAgent> results = SessionHelper.executeHQL(hql, params, DATABASE);
        
        return results.toArray(new EnrollmentLeadServiceAgent[results.size()]);
    }

    /**
     * Builder method using the specified regionCT, effectiveDate, terminationDate. The associated
     * ClientRole and Enrollment are not established
     * @param regionCT
     * @param effectiveDate
     * @param terminationDate
     * @return
     */
    public static EnrollmentLeadServiceAgent build_v1(String regionCT, 
                                                      EDITDate effectiveDate, 
                                                      EDITDate terminationDate) throws EDITCaseException
    {
        EnrollmentLeadServiceAgent enrollmentLeadServiceAgent = new EnrollmentLeadServiceAgent();
        
        enrollmentLeadServiceAgent.setRegionCT(regionCT);
        
        enrollmentLeadServiceAgent.setEffectiveDate(effectiveDate);
        
        enrollmentLeadServiceAgent.setTerminationDate(terminationDate);
        
        boolean datesAreValid = enrollmentLeadServiceAgent.validateEffectiveDateTerminationDate();
        
        if (!datesAreValid) 
        {
            throw new EDITCaseException("Effective Date Must be <= Termination Date");    
        }
        
        return enrollmentLeadServiceAgent;
    }

    /**
     * Evaluates (StopDate <= date <= StartDate).
     * @param date
     * @return true if the date expression is valid
     */
    public boolean dateIsInRange(EDITDate date)
    {
        boolean dateIsInRange = false;

        dateIsInRange = (getEffectiveDate().before(date) || getEffectiveDate().equals(date)) && (getTerminationDate().after(date) || getTerminationDate().equals(date));

        return dateIsInRange;
    }

    /**
     * The EffectiveDate must be <= TerminationDate.
     * @return true if the EffectiveDate <= TerminationDate
     */
    private boolean validateEffectiveDateTerminationDate() 
    {
        return (getEffectiveDate().beforeOREqual(getTerminationDate()));
    }

    /**
     * Finder. Finds all EnrollmentLeadServiceAgent for the specified Segment where
     * EnrollmentLeadServiceAgent.EffectiveDate <= specifiedDate < EnrollmentLeadServiceAgent.TerminationDate.
     * @param segment
     * @param editDate
     * @return the active EnrollmentLeadServiceAgents or null
     */
    public static EnrollmentLeadServiceAgent[] findBy_Segment_EffectiveDate(Segment segment, 
                                                                            EDITDate editDate) 
    {
        String hql = " select enrollmentLeadServiceAgent" +
                    " from EnrollmentLeadServiceAgent enrollmentLeadServiceAgent" +
                    " join enrollmentLeadServiceAgent.Enrollment enrollment" +
                    " join enrollment.BatchContractSetups batchContractSetup" + 
                    " join batchContractSetup.Segments segment" +
                    " where segment = :segment" +
                    " and enrollmentLeadServiceAgent.EffectiveDate <= :editDate" + 
                    " and :editDate < enrollmentLeadServiceAgent.TerminationDate";
        
        Map params = new EDITMap("segment", segment).put("editDate", editDate);
        
        List<EnrollmentLeadServiceAgent> results = SessionHelper.executeHQL(hql, params, DATABASE);
        
        return results.toArray(new EnrollmentLeadServiceAgent[results.size()]);
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        ClientRole clientRole = this.getClientRole();
        ClientDetail clientDetail = clientRole.getClientDetail();
        Set<ClientAddress> clientAddresses = clientDetail.getClientAddresses();

        staging.EnrollmentLeadServiceAgent stagingEnrollmentLeadServiceAgent = new staging.EnrollmentLeadServiceAgent();

        stagingEnrollmentLeadServiceAgent.setEnrollment(stagingContext.getCurrentEnrollment());
        stagingEnrollmentLeadServiceAgent.setEffectiveDate(this.effectiveDate);
        stagingEnrollmentLeadServiceAgent.setTerminationDate(this.terminationDate);
        stagingEnrollmentLeadServiceAgent.setRole(clientRole.getRoleTypeCT());
        stagingEnrollmentLeadServiceAgent.setRegion(this.regionCT);
        stagingEnrollmentLeadServiceAgent.setAgentNumber(clientRole.getReferenceID());
        stagingEnrollmentLeadServiceAgent.setLastName(clientDetail.getLastName());
        stagingEnrollmentLeadServiceAgent.setFirstName(clientDetail.getFirstName());
        stagingEnrollmentLeadServiceAgent.setMiddleName(clientDetail.getMiddleName());
        stagingEnrollmentLeadServiceAgent.setCorporateName(clientDetail.getCorporateName());
        stagingEnrollmentLeadServiceAgent.setTaxIdentification(clientDetail.getTaxIdentification());

        ContactInformation contactInformation = clientDetail.getPrimaryContactInformation();
        if (contactInformation != null)
        {
            stagingEnrollmentLeadServiceAgent.setAgentPhoneNbr(contactInformation.getPhoneEmail());
        }

        Iterator it = clientAddresses.iterator();
        while (it.hasNext())
        {
            ClientAddress clientAddress = (ClientAddress) it.next();

            if (clientAddress.getAddressTypeCT().equalsIgnoreCase(ClientAddress.CLIENT_PRIMARY_ADDRESS) &&
                clientAddress.getTerminationDate().equals(new EDITDate(EDITDate.DEFAULT_MAX_DATE)))
            {
                stagingEnrollmentLeadServiceAgent.setAddressLine1(clientAddress.getAddressLine1());
                stagingEnrollmentLeadServiceAgent.setAddressLine2(clientAddress.getAddressLine2());
                stagingEnrollmentLeadServiceAgent.setAddressLine3(clientAddress.getAddressLine3());
                stagingEnrollmentLeadServiceAgent.setAddressLine4(clientAddress.getAddressLine4());
                stagingEnrollmentLeadServiceAgent.setCity(clientAddress.getCity());
                stagingEnrollmentLeadServiceAgent.setState(clientAddress.getStateCT());
                stagingEnrollmentLeadServiceAgent.setZip(clientAddress.getZipCode());
            }
        }

        stagingContext.getCurrentEnrollment().addEnrollmentLeadServiceAgent(stagingEnrollmentLeadServiceAgent);

        SessionHelper.saveOrUpdate(stagingEnrollmentLeadServiceAgent, database);

        return stagingContext;
    }
}
