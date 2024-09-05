/*
 * User: dlataille
 * Date: Oct 8, 2007
 * Time: 11:29:44 AM

 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package staging;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import edit.common.EDITMap;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

public class Agent extends HibernateEntity
{
    private Long agentPK;
    private Long stagingFK;
    private EDITDate hireDate;
    private EDITDate terminationDate;
    private String agentStatus;
    private String agentType;
    private String department;
    private String region;
    private String branch;
    private String disbursementAddressType;
    private String correspondenceAddressType;
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
    private String clientID;
    private String taxIdentification;
    private String corporateName;
    private EDITBigDecimal splitPercent;
    private EDITBigDecimal advancePercent;
    private EDITBigDecimal recoveryPercent;
    private String servicingAgentIndicator;
    private EDITBigDecimal groupFYEarn;
    private EDITBigDecimal groupRnwlEarn;
    private EDITBigDecimal grpAdvances;
    private EDITBigDecimal indFYEarn;
    private EDITBigDecimal indRnwlEarn;
    private EDITBigDecimal indAdvances;
    private EDITBigDecimal ytdGroupFYEarn;
    private EDITBigDecimal ytdGroupRnwlEarn;
    private EDITBigDecimal ytdGrpAdvances;
    private EDITBigDecimal ytdIndFYEarn;
    private EDITBigDecimal ytdIndRnwlEarn;
    private EDITBigDecimal ytdIndAdvances;
    private EDITBigDecimal commBalance;
    private EDITBigDecimal lifetimeCommBalance;
    private EDITBigDecimal lifetimeAdvanceBalance;
    private String phoneNumber;
    private String situationCode;
    private EDITBigDecimal advances;
    private EDITBigDecimal agentBalance;
    private EDITBigDecimal chargebacks;
    private EDITBigDecimal firstYearEarning;
    private EDITBigDecimal manualAdjustments;
    private EDITBigDecimal outstandingAdvanceBalance;
    private EDITBigDecimal projectedCommissionBalance;
    private EDITBigDecimal recoveries;
    private EDITBigDecimal renewalEarning;
    private EDITBigDecimal ytdGrpAdvancesOnly;
    private EDITBigDecimal ytdGrpChargeback;
    private EDITBigDecimal ytdGrpRecoveries;
    private EDITBigDecimal ytdIndAdvancesOnly;
    private EDITBigDecimal ytdIndChargeback;
    private EDITBigDecimal ytdIndRecoveries;

    private EDITBigDecimal lastStatementAmount;
    private EDITBigDecimal minimumCheckAmount;
    private String holdCheckIndicator;
    private String agentLevel;

    private Staging staging;
    private SegmentBase segmentBase;

    private Set<Accounting> accountings;
    private Set<CommissionActivity> commissionActivities;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.STAGING;

    public Agent()
    {
        this.accountings = new HashSet<Accounting>();
        this.commissionActivities = new HashSet<CommissionActivity>();
    }

    /**
     * Getter.
     * @return
     */
    public Long getAgentPK()
    {
        return agentPK;
    }

    /**
     * Setter.
     * @param agentPK
     */
    public void setAgentPK(Long agentPK)
    {
        this.agentPK = agentPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getStagingFK()
    {
        return stagingFK;
    }

    /**
     * Setter.
     * @param stagingFK
     */
    public void setStagingFK(Long stagingFK)
    {
        this.stagingFK = stagingFK;
    }

    /**
     * Getter.
     * @return
     */
    public Staging getStaging()
    {
        return staging;
    }

    /**
     * Setter.
     * @param staging
     */
    public void setStaging(Staging staging)
    {
        this.staging = staging;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getHireDate()
    {
        return hireDate;
    }

    /**
     * Setter.
     * @param hireDate
     */
    public void setHireDate(EDITDate hireDate)
    {
        this.hireDate = hireDate;
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
    public String getAgentStatus()
    {
        return agentStatus;
    }

    /**
     * Setter.
     * @param agentStatus
     */
    public void setAgentStatus(String agentStatus)
    {
        this.agentStatus = agentStatus;
    }

    /**
     * Getter.
     * @return
     */
    public String getAgentType()
    {
        return agentType;
    }

    /**
     * Setter.
     * @param agentType
     */
    public void setAgentType(String agentType)
    {
        this.agentType = agentType;
    }

    /**
     * Getter.
     * @return
     */
    public String getDepartment()
    {
        return department;
    }

    /**
     * Setter.
     * @param department
     */
    public void setDepartment(String department)
    {
        this.department = department;
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
    public String getBranch()
    {
        return branch;
    }

    /**
     * Setter.
     * @param branch
     */
    public void setBranch(String branch)
    {
        this.branch = branch;
    }

    /**
     * Getter.
     * @return
     */
    public String getDisbursementAddressType()
    {
        return disbursementAddressType;
    }

    /**
     * Setter.
     * @param disbursementAddressType
     */
    public void setDisbursementAddressType(String disbursementAddressType)
    {
        this.disbursementAddressType = disbursementAddressType;
    }

    /**
     * Getter.
     * @return
     */
    public String getCorrespondenceAddressType()
    {
        return correspondenceAddressType;
    }

    /**
     * Setter.
     * @param correspondenceAddressType
     */
    public void setCorrespondenceAddressType(String correspondenceAddressType)
    {
        this.correspondenceAddressType = correspondenceAddressType;
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
     * @param lastNmae
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
    public String getClientID()
    {
        return clientID;
    }

    /**
     * Setter.
     * @param clientID
     */
    public void setClientID(String clientID)
    {
        this.clientID = clientID;
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
    public EDITBigDecimal getSplitPercent()
    {
        return splitPercent;
    }

    /**
     * Setter.
     * @param splitPercent
     */
    public void setSplitPercent(EDITBigDecimal splitPercent)
    {
        this.splitPercent = splitPercent;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAdvancePercent()
    {
        return advancePercent;
    }

    /**
     * Setter.
     * @param advancePercent
     */
    public void setAdvancePercent(EDITBigDecimal advancePercent)
    {
        this.advancePercent = advancePercent;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getRecoveryPercent()
    {
        return recoveryPercent;
    }

    /**
     * Setter.
     * @param recoveryPercent
     */
    public void setRecoveryPercent(EDITBigDecimal recoveryPercent)
    {
        this.recoveryPercent = recoveryPercent;
    }

    /**
     * Getter.
     * @return
     */
    public String getServicingAgentIndicator()
    {
        return servicingAgentIndicator;
    }

    /**
     * Setter.
     * @param servicingAgentIndicator
     */
    public void setServicingAgentIndicator(String servicingAgentIndicator)
    {
        this.servicingAgentIndicator = servicingAgentIndicator;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGroupFYEarn()
    {
        return groupFYEarn;
    }

    /**
     * Setter.
     * @param groupFYEarn
     */
    public void setGroupFYEarn(EDITBigDecimal groupFYEarn)
    {
        this.groupFYEarn = groupFYEarn;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGroupRnwlEarn()
    {
        return groupRnwlEarn;
    }

    /**
     * Setter.
     * @param groupRnwlEarn
     */
    public void setGroupRnwlEarn(EDITBigDecimal groupRnwlEarn)
    {
        this.groupRnwlEarn = groupRnwlEarn;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGrpAdvances()
    {
        return grpAdvances;
    }

    /**
     * Setter.
     * @param grpAdvances
     */
    public void setGrpAdvances(EDITBigDecimal grpAdvances)
    {
        this.grpAdvances = grpAdvances;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getIndFYEarn()
    {
        return indFYEarn;
    }

    /**
     * Setter.
     * @param indFYEarn
     */
    public void setIndFYEarn(EDITBigDecimal indFYEarn)
    {
        this.indFYEarn = indFYEarn;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getIndRnwlEarn()
    {
        return indRnwlEarn;
    }

    /**
     * Setter.
     * @param indRnwlEarn
     */
    public void setIndRnwlEarn(EDITBigDecimal indRnwlEarn)
    {
        this.indRnwlEarn = indRnwlEarn;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getIndAdvances()
    {
        return indAdvances;
    }

    /**
     * Setter.
     * @param indAdvances
     */
    public void setIndAdvances(EDITBigDecimal indAdvances)
    {
        this.indAdvances = indAdvances;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getYTDGroupFYEarn()
    {
        return ytdGroupFYEarn;
    }

    /**
     * Setter.
     * @param ytdGroupFYEarn
     */
    public void setYTDGroupFYEarn(EDITBigDecimal ytdGroupFYEarn)
    {
        this.ytdGroupFYEarn = ytdGroupFYEarn;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getYTDGroupRnwlEarn()
    {
        return ytdGroupRnwlEarn;
    }

    /**
     * Setter.
     * @param ytdGroupRnwlEarn
     */
    public void setYTDGroupRnwlEarn(EDITBigDecimal ytdGroupRnwlEarn)
    {
        this.ytdGroupRnwlEarn = ytdGroupRnwlEarn;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getYTDGrpAdvances()
    {
        return ytdGrpAdvances;
    }

    /**
     * Setter.
     * @param ytdGrpAdvances
     */
    public void setYTDGrpAdvances(EDITBigDecimal ytdGrpAdvances)
    {
        this.ytdGrpAdvances = ytdGrpAdvances;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getYTDIndFYEarn()
    {
        return ytdIndFYEarn;
    }

    /**
     * Setter.
     * @param ytdIndFYEarn
     */
    public void setYTDIndFYEarn(EDITBigDecimal ytdIndFYEarn)
    {
        this.ytdIndFYEarn = ytdIndFYEarn;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getYTDIndRnwlEarn()
    {
        return ytdIndRnwlEarn;
    }

    /**
     * Setter.
     * @param ytdIndRnwlEarn
     */
    public void setYTDIndRnwlEarn(EDITBigDecimal ytdIndRnwlEarn)
    {
        this.ytdIndRnwlEarn = ytdIndRnwlEarn;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getYTDIndAdvances()
    {
        return ytdIndAdvances;
    }

    /**
     * Setter.
     * @param ytdIndAdvances
     */
    public void setYTDIndAdvances(EDITBigDecimal ytdIndAdvances)
    {
        this.ytdIndAdvances = ytdIndAdvances;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCommBalance()
    {
        return commBalance;
    }

    /**
     * Setter.
     * @param commBalance
     */
    public void setCommBalance(EDITBigDecimal commBalance)
    {
        this.commBalance = commBalance;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getLifetimeCommBalance()
    {
        return lifetimeCommBalance;
    }

    /**
     * Setter.
     * @param lifetimeCommBalance
     */
    public void setLifetimeCommBalance(EDITBigDecimal lifetimeCommBalance)
    {
        this.lifetimeCommBalance = lifetimeCommBalance;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getLifetimeAdvanceBalance()
    {
        return lifetimeAdvanceBalance;
    }

    /**
     * Setter.
     * @param lifetimeAdvanceBalance
     */
    public void setLifetimeAdvanceBalance(EDITBigDecimal lifetimeAdvanceBalance)
    {
        this.lifetimeAdvanceBalance = lifetimeAdvanceBalance;
    }

    /**
     * Getter.
     * @return
     */
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * Setter.
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter.
     * @return
     */
    public String getSituationCode()
    {
        return situationCode;
    }

    /**
     * Setter.
     * @param situationCode
     */
    public void setSituationCode(String situationCode)
    {
        this.situationCode = situationCode;
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

    /**
     * Getter.
     * @return
     */
    public Set<CommissionActivity> getCommissionActivities()
    {
        return commissionActivities;
    }

    /**
     * Setter.
     * @param commissionActivities
     */
    public void setCommissionActivities(Set<CommissionActivity> commissionActivities)
    {
        this.commissionActivities = commissionActivities;
    }

    /**
     * Add another commissionActivity to the current mapped commissionActivities.
     * @param commissionActivity
     */
    public void addCommissionActivity(CommissionActivity commissionActivity)
    {
        this.commissionActivities.add(commissionActivity);
    }

    /**
     * Setter.
     * @param segmentBase
     */
    public void setSegmentBase(SegmentBase segmentBase)
    {
        this.segmentBase = segmentBase;
    }

    /**
     * Getter.
     * @return
     */
    public SegmentBase getSegmentBase()
    {
        return segmentBase;
    }

    public String getDatabase()
    {
        return Agent.DATABASE;
    }

    /**
     * Setter.
     * @param advances
     */
    public void setAdvances(EDITBigDecimal advances)
    {
        this.advances = advances;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAdvances()
    {
        return advances;
    }

    /**
     * Setter.
     * @param agentBalance
     */
    public void setAgentBalance(EDITBigDecimal agentBalance)
    {
        this.agentBalance = agentBalance;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAgentBalance()
    {
        return agentBalance;
    }

    /**
     * Setter.
     * @param chargebacks
     */
    public void setChargebacks(EDITBigDecimal chargebacks)
    {
        this.chargebacks = chargebacks;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getChargebacks()
    {
        return chargebacks;
    }

    /**
     * Setter.
     * @param firstYearEarning
     */
    public void setFirstYearEarning(EDITBigDecimal firstYearEarning)
    {
        this.firstYearEarning = firstYearEarning;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getFirstYearEarning()
    {
        return firstYearEarning;
    }

    /**
     * Setter.
     * @param manualAdjustments
     */
    public void setManualAdjustments(EDITBigDecimal manualAdjustments)
    {
        this.manualAdjustments = manualAdjustments;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getManualAdjustments()
    {
        return manualAdjustments;
    }

    /**
     * Setter.
     * @param outstandingAdvanceBalance
     */
    public void setOutstandingAdvanceBalance(EDITBigDecimal outstandingAdvanceBalance)
    {
        this.outstandingAdvanceBalance = outstandingAdvanceBalance;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getOutstandingAdvanceBalance()
    {
        return outstandingAdvanceBalance;
    }

    /**
     * Setter.
     * @param projectedCommissionBalance
     */
    public void setProjectedCommissionBalance(EDITBigDecimal projectedCommissionBalance)
    {
        this.projectedCommissionBalance = projectedCommissionBalance;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getProjectedCommissionBalance()
    {
        return projectedCommissionBalance;
    }

    /**
     * Setter.
     * @param recoveries
     */
    public void setRecoveries(EDITBigDecimal recoveries)
    {
        this.recoveries = recoveries;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getRecoveries()
    {
        return recoveries;
    }

    /**
     * Setter.
     * @param renewalEarning
     */
    public void setRenewalEarning(EDITBigDecimal renewalEarning)
    {
        this.renewalEarning = renewalEarning;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getRenewalEarning()
    {
        return renewalEarning;
    }

    /**
     * Setter.
     * @param ytdGrpAdvancesOnly
     */
    public void setYTDGrpAdvancesOnly(EDITBigDecimal ytdGrpAdvancesOnly)
    {
        this.ytdGrpAdvancesOnly = ytdGrpAdvancesOnly;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getYTDGrpAdvancesOnly()
    {
        return ytdGrpAdvancesOnly;
    }

    /**
     * Setter.
     * @param ytdGrpChargeback
     */
    public void setYTDGrpChargeback(EDITBigDecimal ytdGrpChargeback)
    {
        this.ytdGrpChargeback = ytdGrpChargeback;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getYTDGrpChargeback()
    {
        return ytdGrpChargeback;
    }

    /**
     * Setter.
     * @param ytdGrpRecoveries
     */
    public void setYTDGrpRecoveries(EDITBigDecimal ytdGrpRecoveries)
    {
        this.ytdGrpRecoveries = ytdGrpRecoveries;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getYTDGrpRecoveries()
    {
        return ytdGrpRecoveries;
    }

    /**
     * Setter.
     * @param ytdIndAdvancesOnly
     */
    public void setYTDIndAdvancesOnly(EDITBigDecimal ytdIndAdvancesOnly)
    {
        this.ytdIndAdvancesOnly = ytdIndAdvancesOnly;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getYTDIndAdvancesOnly()
    {
        return ytdIndAdvancesOnly;
    }

    /**
     * Setter.
     * @param ytdIndChargeback
     */
    public void setYTDIndChargeback(EDITBigDecimal ytdIndChargeback)
    {
        this.ytdIndChargeback = ytdIndChargeback;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getYTDIndChargeback()
    {
        return ytdIndChargeback;
    }

    /**
     * Setter.
     * @param ytdIndRecoveries
     */
    public void setYTDIndRecoveries(EDITBigDecimal ytdIndRecoveries)
    {
        this.ytdIndRecoveries = ytdIndRecoveries;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getYTDIndRecoveries()
    {
        return ytdIndRecoveries;
    }

    public void setLastStatementAmount(EDITBigDecimal lastStatementAmount)
    {
        this.lastStatementAmount = lastStatementAmount;
    }

    public EDITBigDecimal getLastStatementAmount()
    {
        return this.lastStatementAmount ;
    }

    public void setMinimumCheckAmount(EDITBigDecimal minimumCheckAmount)
    {
        this.minimumCheckAmount = minimumCheckAmount;
    }

    public EDITBigDecimal getMinimumCheckAmount()
    {
        return this.minimumCheckAmount ;
    }

    public void setHoldCheckIndicator(String holdCheckIndicator)
    {
        this.holdCheckIndicator = holdCheckIndicator;
    }

    public String getHoldCheckIndicator()
    {
        return this.holdCheckIndicator ;
    }

    public void setAgentLevel(String agentLevel)
    {
        this.agentLevel = agentLevel;
    }

    public String getAgentLevel()
    {
        return this.agentLevel;
    }

    public static Agent findByStagingFK_AgentNumber(Long stagingFK, String agentNumber, String database)
    {
        String hql = " select a from Agent a" +
                    " where a.StagingFK = :stagingFK" +
                    " and a.AgentNumber = :agentNumber";

        EDITMap params = new EDITMap();
        params.put("stagingFK", stagingFK);
        params.put("agentNumber", agentNumber);

        List<Agent> results = SessionHelper.executeHQL(hql, params, database);

        if (results.size() > 0)
        {
            return (Agent) results.get(0);
        }
        else
        {
            return null;
        }
    }
}


