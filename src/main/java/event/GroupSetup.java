/*
 * User: dlataill
 * Date: Feb 25, 2004
 * Time: 7:57:11 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import contract.*;

import edit.common.*;
import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import event.dm.dao.*;

import java.util.*;

import org.hibernate.Session;

import role.*;
import group.*;


public class GroupSetup extends HibernateEntity
{
    public static final String WITHDRAWALTYPECT_SUPPCONTRACT = "SuppContract";

    private GroupSetupVO groupSetupVO;
    private Set<ContractSetup> contractSetups;
    private Set<ScheduledEvent> scheduledEvents;

    private Set<Charge> charges;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;



    public GroupSetup()
    {
        this.groupSetupVO = new GroupSetupVO();
        init();
    }

    public GroupSetup(long groupSetupPK)
    {
        this(DAOFactory.getGroupSetupDAO().findByGroupSetupPK(groupSetupPK));
        init();
    }

    public GroupSetup(GroupSetupVO groupSetupVO)
    {
        this.groupSetupVO = groupSetupVO;
        init();
    }

    private final void init()
    {
        contractSetups = new HashSet<ContractSetup>();
        scheduledEvents = new HashSet<ScheduledEvent>();
        charges = new HashSet<Charge>();
    }

    /**
     * Getter
     * @return  set of charges
     */
    public Set<Charge> getCharges()
    {
        return charges;
    }

    /**
     * Setter
     * @param charges      set of charges
     */
    public void setCharges(Set<Charge> charges)
    {
        this.charges = charges;
    }

    /**
     * Adds a Charge to the set of children
     * @param charge
     */
    public void addCharge(Charge charge)
    {
        this.getCharges().add(charge);

        charge.setGroupSetup(this);

        SessionHelper.saveOrUpdate(charge, GroupSetup.DATABASE);
    }

    /**
     * Removes a Charge from the set of children
     * @param charge
     */
    public void removeCharge(Charge charge)
    {
        this.getCharges().remove(charge);

        charge.setGroupSetup(null);

        SessionHelper.saveOrUpdate(charge, GroupSetup.DATABASE);
    }

    /**
     * Getter
     * @return  set of contractSetups
     */
    public Set<ContractSetup> getContractSetups()
    {
        return contractSetups;
    }

    /**
     * Setter
     * @param contractSetups      set of contractSetups
     */
    public void setContractSetups(Set<ContractSetup> contractSetups)
    {
        this.contractSetups = contractSetups;
    }

    /**
     * Adds a ContractSetup to the set of children
     * @param contractSetup
     */
    public void addContractSetup(ContractSetup contractSetup)
    {
        this.getContractSetups().add(contractSetup);

        contractSetup.setGroupSetup(this);

        SessionHelper.saveOrUpdate(contractSetup, GroupSetup.DATABASE);
    }

    /**
     * Removes a ContractSetup from the set of children
     * @param contractSetup
     */
    public void removeContractSetup(ContractSetup contractSetup)
    {
        this.getContractSetups().remove(contractSetup);

        contractSetup.setGroupSetup(null);

        SessionHelper.saveOrUpdate(this, GroupSetup.DATABASE);
    }

    /**
     * Getter
     * @return  set of scheduledEvents
     */
    public Set<ScheduledEvent> getScheduledEvents()
    {
        return scheduledEvents;
    }

    /**
     * Setter
     * @param scheduledEvents      set of scheduledEvents
     */
    public void setScheduledEvents(Set<ScheduledEvent> scheduledEvents)
    {
        this.scheduledEvents = scheduledEvents;
    }

    /**
     * Adds a ScheduledEvent to the set of children
     * @param scheduledEvent
     */
    public void addScheduledEvent(ScheduledEvent scheduledEvent)
    {
        this.getScheduledEvents().add(scheduledEvent);

        scheduledEvent.setGroupSetup(this);

        SessionHelper.saveOrUpdate(scheduledEvent, GroupSetup.DATABASE);
    }

    /**
     * Removes a ScheduledEvent from the set of children
     * @param scheduledEvent
     */
    public void removeScheduledEvent(ScheduledEvent scheduledEvent)
    {
        this.getScheduledEvents().remove(scheduledEvent);

        scheduledEvent.setGroupSetup(null);

        SessionHelper.saveOrUpdate(scheduledEvent, GroupSetup.DATABASE);
    }

   public ScheduledEvent getScheduledEvent()
    {
        ScheduledEvent scheduledEvent = getScheduledEvents().isEmpty() ? null : (ScheduledEvent) getScheduledEvents().iterator().next();

        return scheduledEvent;
    }

    /**
     * Setter.
     * @param groupSetupPK
     */
    public void setGroupSetupPK(Long groupSetupPK)
    {
        groupSetupVO.setGroupSetupPK(SessionHelper.getPKValue(groupSetupPK));
    }

    /**
     * Getter.
     * @return
     */
    public Long getGroupSetupPK()
    {
        return SessionHelper.getPKValue(groupSetupVO.getGroupSetupPK());
    }

    /**
     * Getter.
     * @return
     */
    public String getDistributionCodeCT()
    {
        return groupSetupVO.getDistributionCodeCT();
    }

    //-- java.lang.String getDistributionCodeCT() 

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getEmployeeContribution()
    {
        return SessionHelper.getEDITBigDecimal(groupSetupVO.getEmployeeContribution());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getEmployerContribution()
    {
        return SessionHelper.getEDITBigDecimal(groupSetupVO.getEmployerContribution());
    }

    /**
    * Getter.
    * @return
    */
    public String getGrossNetStatusCT()
    {
        return groupSetupVO.getGrossNetStatusCT();
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGroupAmount()
    {
        return SessionHelper.getEDITBigDecimal(groupSetupVO.getGroupAmount());
    }

    /**
     * Getter.
     * @return
     */
    public String getGroupKey()
    {
        return groupSetupVO.getGroupKey();
    }

    //-- java.lang.String getGroupKey() 

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGroupPercent()
    {
        return SessionHelper.getEDITBigDecimal(groupSetupVO.getGroupPercent());
    }

    /**
     * Getter.
     * @return
     */
    public String getGroupTypeCT()
    {
        return groupSetupVO.getGroupTypeCT();
    }

    //-- java.lang.String getGroupTypeCT() 

    /**
     * Getter.
     * @return
     */
    public String getMemoCode()
    {
        return groupSetupVO.getMemoCode();
    }

    //-- java.lang.String getMemoCode() 

    /**
     * Getter.
     * @return
     */
    public String getPremiumTypeCT()
    {
        return groupSetupVO.getPremiumTypeCT();
    }

    //-- java.lang.String getPremiumTypeCT() 

    /**
     * Getter.
     * @return
     */
    public String getWithdrawalTypeCT()
    {
        return groupSetupVO.getWithdrawalTypeCT();
    }

    //-- java.lang.String getWithdrawalTypeCT() 

    /**
     * Setter.
     * @return
     */
    public void setDistributionCodeCT(String distributionCodeCT)
    {
        groupSetupVO.setDistributionCodeCT(distributionCodeCT);
    }

    //-- void setDistributionCodeCT(java.lang.String) 

    /**
     * Setter.
     * @return
     */
    public void setEmployeeContribution(EDITBigDecimal employeeContribution)
    {
        groupSetupVO.setEmployeeContribution(SessionHelper.getEDITBigDecimal(employeeContribution));
    }

    /**
     * Setter.
     * @return
     */
    public void setEmployerContribution(EDITBigDecimal employerContribution)
    {
        groupSetupVO.setEmployerContribution(SessionHelper.getEDITBigDecimal(employerContribution));
    }

    /**
     * Setter.
     * @return
     */
    public void setGrossNetStatusCT(String grossNetStatusCT)
    {
        groupSetupVO.setGrossNetStatusCT(grossNetStatusCT);
    }

    //-- void setGrossNetStatusCT(java.lang.String) 

    /**
     * Setter.
     * @return
     */
    public void setGroupAmount(EDITBigDecimal groupAmount)
    {
        groupSetupVO.setGroupAmount(SessionHelper.getEDITBigDecimal(groupAmount));
    }

    /**
     * Setter.
     * @return
     */
    public void setGroupKey(String groupKey)
    {
        groupSetupVO.setGroupKey(groupKey);
    }

    //-- void setGroupKey(java.lang.String) 

    /**
     * Setter.
     * @return
     */
    public void setGroupPercent(EDITBigDecimal groupPercent)
    {
        groupSetupVO.setGroupPercent(SessionHelper.getEDITBigDecimal(groupPercent));
    }

    /**
     * Setter.
     * @return
     */
    public void setGroupTypeCT(String groupTypeCT)
    {
        groupSetupVO.setGroupTypeCT(groupTypeCT);
    }

    //-- void setGroupTypeCT(java.lang.String) 

    /**
     * Setter.
     * @return
     */
    public void setMemoCode(String memoCode)
    {
        groupSetupVO.setMemoCode(memoCode);
    }

    //-- void setMemoCode(java.lang.String) 

    /**
     * Setter.
     * @return
     */
    public void setPremiumTypeCT(String premiumTypeCT)
    {
        groupSetupVO.setPremiumTypeCT(premiumTypeCT);
    }

    //-- void setPremiumTypeCT(java.lang.String) 

    /**
     * Setter.
     * @return
     */
    public void setWithdrawalTypeCT(String withdrawalTypeCT)
    {
        groupSetupVO.setWithdrawalTypeCT(withdrawalTypeCT);
    }

    //-- void setWithdrawalTypeCT(java.lang.String) 
    public void delete() throws Exception
    {
        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            crud.deleteVOFromDBRecursively(GroupSetupVO.class, groupSetupVO.getGroupSetupPK());
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (crud != null)
            {
                crud.close();
            }

            crud = null;
        }
    }

    public GroupSetupVO getAsVO()
    {
        return groupSetupVO;
    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, GroupSetup.DATABASE);
    }

    public void hDelete()
    {
        SessionHelper.delete(this, GroupSetup.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return GroupSetup.DATABASE;
    }

    public void save()
    {
        CRUD crud = null;

        List voExclusionList = null;

        voExclusionList = new ArrayList();

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            voExclusionList.add(SegmentVO.class);

            voExclusionList.add(ClientRoleVO.class);

            voExclusionList.add(InvestmentVO.class);

            crud.createOrUpdateVOInDBRecursively(groupSetupVO);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
            
            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null)
            {
                crud.close();
            }

            crud = null;
        }
    }


    /**
     * Finder.
     * @param commissionHistoryPK
     * @return
     * @throws Exception
     */
    public static final GroupSetupVO[] findGroupSetupByCommissionHistoryPK(long commissionHistoryPK) throws Exception
    {
        return DAOFactory.getGroupSetupDAO().findByCommissionHistoryPK(commissionHistoryPK);
    }

    /**
     * Finder.
     * @param editTrxPK
     * @return
     */
    public static final GroupSetup findBy_EDITTrxPK(long editTrxPK)
    {
        GroupSetup groupSetup = null;

        GroupSetupVO[] groupSetupVOs = new GroupSetupDAO().findByEditTrxPK(editTrxPK);

        if (groupSetupVOs != null)
        {
            groupSetup = new GroupSetup(groupSetupVOs[0]);
        }

        return groupSetup;
    }

    /**
     * Finds all GroupSetups with a groupTypeCT of grouping.  Grouping groupTypes can be Case or ListBill
     *
     * @return  array of GroupSetups
     */
    public static final GroupSetup[] findBy_GroupTypeCT_OfGroup_UsingCRUD()
    {
        GroupSetupVO[] groupSetupVOs = new GroupSetupDAO().findByGroupTypeCT_OfGroup();

        return (GroupSetup[]) CRUDEntityImpl.mapVOToEntity(groupSetupVOs, GroupSetup.class);
    }

    /**
     * Retrieve the GroupSetup for the given groupSetupPK
     * @param groupSetupPK  - The primary key for the GroupSetup that is to be retrieved.
     * @return
     */
    public static GroupSetup findByPK(Long groupSetupPK)
    {
        return (GroupSetup) SessionHelper.get(GroupSetup.class, groupSetupPK, GroupSetup.DATABASE);
    }

    /**
     * Originally in GroupSetupDAO.findByEditTrxPK
     * @param editTrxPK
     * @return
     */
    public static GroupSetup findBy_EditTrxPK_IncludeContractSetup_And_ClientSetup(Long editTrxPK)
    {
        GroupSetup groupSetup = null;

        String hql = "select groupSetup from GroupSetup groupSetup " +
                     " join fetch contractSetup.ContractSetup contractSetup" +
                     " join fetch contractSetup.ClientSetup clientSetup" +
                     " join clientSetup.EDITTrx editTrx" +
                     " where editTrx.EDITTrxPK = :editTrxPK";

        Map params = new HashMap();
        params.put("editTrxPK", editTrxPK);

        List results = SessionHelper.executeHQL(hql, params, GroupSetup.DATABASE);

        if (!results.isEmpty())
        {
            groupSetup = (GroupSetup) results.get(0);
        }

        return groupSetup;
    }

    /**
     * Originally in GroupSetupDAO as findBySegmentPKAndTrxType
     * Retrieves all GroupSetupVOs for the given segment and transaction type
     * @param segmentPK
     * @param trxType
     * @return
     */
    public static GroupSetup[] findBy_SegmentPK_TrxType(Long segmentPK, String trxType)
    {
        String hql = "select groupSetup from GroupSetup groupSetup " +
                     " join groupSetup.ContractSetup contractSetup" +
                     " join contractSetup.ClientSetup clientSetup" +
                     " join clientSetup.EDITTrx editTrx" +
                     " where contractSetup.SegmentFK = :segmentPK" +
                     " and editTrx.TransactionTypeCT = :trxType";

        Map params = new HashMap();
        params.put("segmentPK", segmentPK);
        params.put("trxType", trxType);

        List<GroupSetup> results = SessionHelper.executeHQL(hql, params, GroupSetup.DATABASE);

        return results.toArray(new GroupSetup[results.size()]);
    }

    /**
     * Retrieves all GroupSetups for the given groupKey
     * @param groupKey
     * @return  array of GroupSetups
     */
    public static GroupSetup[] findBy_GroupKey(String groupKey)
    {
        String hql = "select groupSetup from GroupSetup groupSetup " +
                     " where groupSetup.GroupKey = :groupKey";

        Map params = new HashMap();
        params.put("groupKey", groupKey);

        List<GroupSetup> results = SessionHelper.executeHQL(hql, params, GroupSetup.DATABASE);

        return results.toArray(new GroupSetup[results.size()]);
    }

    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (!(o instanceof GroupSetup))
        {
            return false;
        }

        final GroupSetup groupSetup = (GroupSetup) o;

        if (!getGroupSetupPK().equals(groupSetup.getGroupSetupPK()))
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        return getGroupSetupPK().hashCode();
    }

    public void setVO(GroupSetupVO vo)
    {
        this.groupSetupVO = vo;
    }

    /**
      * Common initialization for CaseTracking transactions. Builds a default GroupSetup with a default ContractSetup, and ClientSetup.
     * Associations to Segment and ClientRole are also established.
      * @param transactionType
      * @return
      */
    public static GroupSetup initializeGroupSetupThruClientSetup(Segment segment, String transactionType, Long contractClientFK, Long clientRoleFK)
    {
        ClientRole clientRole = ClientRole.findBy_ClientRolePK(clientRoleFK);

        GroupSetup groupSetup = new GroupSetup();

        if (segment.belongsToACase())
        {
            //  Set the group information using the Case
//            Case theCase = Case.findByPolicyGroupFK(segment.getPolicyGroupFK());
//            groupSetup.setGroupTypeCT(theCase.getGroupTypeCT());
//            groupSetup.setGroupKey(theCase.getCaseNumber());
        }
        else
        {
            //  Individual segment, no group information
            groupSetup.setGroupTypeCT(null);
            groupSetup.setGroupKey(null);
        }

        groupSetup.setGrossNetStatusCT("Gross");
        groupSetup.setDistributionCodeCT("NormalDistribution");

        ContractSetup contractSetup = new ContractSetup();

        if (transactionType.equals("DE"))
        {
            contractSetup.setDeathStatusCT("Death");
        }

        // Assocate ContractSetup to GroupSetup
        groupSetup.addContractSetup(contractSetup);

        // Associate ContractSetup to Segment
        segment.addContractSetup(contractSetup);

        ClientSetup clientSetup = new ClientSetup();

        // Associate ClientSetup to ContractSetup
        contractSetup.addClientSetup(clientSetup);

        ContractClient contractClient = ContractClient.findByPK(contractClientFK);

        // Associate ContractClient to ClientSetup
        contractClient.addClientSetup(clientSetup);

        // Associate ClientSetup to ClientRole
        clientRole.addClientSetup(clientSetup);

        return groupSetup;
    }

    /**
     * Initialization for RiderClaim transaction. Builds a default GroupSetupVO with a default ContractSetupVO, and ClientSetupVO.
     * @param transactionType
     * @return
     */
    public static GroupSetupVO initializeGroupSetupVO_ThruClientSetup(Segment segment, String transactionType, Long contractClientFK, Long clientRoleFK)
    {
        ContractClient contractClient = ContractClient.findByPK(contractClientFK);

        GroupSetup groupSetup = new GroupSetup();

        if (segment.belongsToACase())
        {
            //  Set the group information using the Case
//            Case theCase = Case.findByPolicyGroupFK(segment.getPolicyGroupFK());
//            groupSetup.setGroupTypeCT(theCase.getGroupTypeCT());
//            groupSetup.setGroupKey(theCase.getCaseNumber());
        }
        else
        {
            //  Individual segment, no group information
            groupSetup.setGroupTypeCT(null);
            groupSetup.setGroupKey(null);
        }

        groupSetup.setGrossNetStatusCT("Gross");
        groupSetup.setDistributionCodeCT("NormalDistribution");

        ContractSetup contractSetup = new ContractSetup();

        if (transactionType.equals("DE"))
        {
            contractSetup.setDeathStatusCT("Death");
        }

        ClientSetup clientSetup = new ClientSetup();
        ClientSetupVO clientSetupVO = (ClientSetupVO) clientSetup.getVO();
        clientSetupVO.setClientRoleFK(contractClient.getClientRoleFK().longValue());
        clientSetupVO.setContractClientFK(contractClientFK.longValue());

        GroupSetupVO groupSetupVO = (GroupSetupVO) groupSetup.getAsVO();
        ContractSetupVO contractSetupVO = (ContractSetupVO) contractSetup.getAsVO();

        contractSetupVO.setSegmentFK(segment.getSegmentPK().longValue());
        contractSetupVO.addClientSetupVO(clientSetupVO);
        groupSetupVO.addContractSetupVO(contractSetupVO);

        return groupSetupVO;
    }

    /**
     * Establishes default values for the GroupSetup and ContractSetup
     *
     * NOTE: this method is a duplicate of ContractEvent's buildDefaultGroupSetup.  It was
     * added here in an attempt to move the logic. Could not complete the refactor at this time.
     *
     * @param segments
     * @return
     */
    public static GroupSetupVO initializeGroupSetupVO(Segment[] segments)
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        String distributionCode = codeTableWrapper.getCodeByCodeTableNameAndCodeDesc("DISTRIBUTIONCODE", "Normal Distribution");

        GroupSetupVO groupSetupVO = new GroupSetupVO();

        //  @todo Fix for Case processing
        //  TEMPORARY SOLUTION!!
        //  Master no longer exists and individual segments no longer have to belong to any grouping (Master, Case, etc.)
        //  We want groupSetup to have the groupType and caseNumber from Case (or null if individual).  But we also
        //  want to process all the segments for a given Case.  Right now this method takes an array of Segments
        //  but really only one is passed in.  Even if multiples are sent in (like for Cases), I am going to assume that
        //  all the segments are the same - they all belong to the same Case.  S. Dorman 1/18/06
        if (segments[0].belongsToACase())
        {
            ContractGroup caseContractGroup = ContractGroup.findBy_ContractGroupFK(segments[0].getContractGroupFK());

            groupSetupVO.setGroupTypeCT(caseContractGroup.getContractGroupTypeCT());
            groupSetupVO.setGroupKey(caseContractGroup.getContractGroupNumber());
        }
        else
        {
            groupSetupVO.setGroupTypeCT(null);
            groupSetupVO.setGroupKey(null);
        }

        groupSetupVO.setGrossNetStatusCT("Gross");
        groupSetupVO.setDistributionCodeCT(distributionCode);

        for (int i = 0; i < segments.length; i++)
        {
            ContractSetupVO contractSetupVO = new ContractSetupVO();

            contractSetupVO.setSegmentFK(SessionHelper.getPKValue(segments[i].getSegmentPK()));

            //            contractSetupVO.setPolicyAmount(new EDITBigDecimal("0").getBigDecimal());
            //            contractSetupVO.setCostBasis(new EDITBigDecimal("0").getBigDecimal());
            //            contractSetupVO.setAmountReceived(new EDITBigDecimal("0").getBigDecimal());
            groupSetupVO.addContractSetupVO(contractSetupVO);
        }

        return groupSetupVO;
    }

    public ContractSetup getContractSetup()
    {
        ContractSetup contractSetup =getContractSetups().isEmpty() ? null : (ContractSetup) getContractSetups().iterator().next();

        return contractSetup;
    }

    public void onCreate()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

  /**
   * Builds GroupSetup (using a Separate Hibernate Session) as GroupSetup.ContractSetup.ClientSetup.EDITTrx (joined) with
   * the following left-outer-joins:
   * GroupSetup.ScheduledEvents.
   * GroupSetup.Charges.
   * ContractSetup.InvestmentAllocationOverrides.
   * with
   * @param editTrxPK
   * @return
   */
  public static GroupSetup findSeparateBy_EDITTrxPK_V2(Long editTrxPK)
  {
    GroupSetup groupSetup = null;

    String hql = " select groupSetup" +
                " from GroupSetup groupSetup" +
                " join fetch groupSetup.ContractSetups contractSetup" +
                " join fetch contractSetup.ClientSetups clientSetup" +
                " left join fetch clientSetup.WithholdingOverrides" +
                " left join fetch clientSetup.ContractClientAllocationOvrds" +
                " join fetch contractSetup.Segment segment" + 
                " join fetch clientSetup.EDITTrxs editTrx" +
                 // there might not be buckets created yet
                " left join fetch segment.Investments investment" + 
                " left join fetch investment.Buckets" + 
                " left join fetch groupSetup.ScheduledEvents" +
                " left join fetch groupSetup.Charges" +
                 " left join fetch contractSetup.OutSuspenses" +
                " where editTrx.EDITTrxPK = :editTrxPK";

    EDITMap params = new EDITMap().put("editTrxPK", editTrxPK);

    Session session = null;
    
    try
    {
      session = SessionHelper.getSeparateSession(GroupSetup.DATABASE);
    
      List<GroupSetup> results = SessionHelper.executeHQL(session, hql, params, 0);

      if (!results.isEmpty())
      {
         groupSetup = results.get(0);
      }      
    }
    finally
    {
      if (session != null) session.close();
    }

    return groupSetup;
  }

    public static GroupSetup findSeparateBy_EDITTrxPK_V3_includeHistoryForReDo(Long editTrxPK)
    {
        GroupSetup groupSetup = null;

        String hql = " select groupSetup" + " from GroupSetup groupSetup"
                    + " join fetch groupSetup.ContractSetups contractSetup"
                    + " join fetch contractSetup.ClientSetups clientSetup"
                    + " join fetch contractSetup.Segment segment"
                    + " left join fetch segment.Investments"
                    + " left join fetch groupSetup.ScheduledEvents"
                    + " left join fetch groupSetup.Charges"
                    + " left join fetch clientSetup.WithholdingOverrides"
                    + " left join fetch clientSetup.ContractClientAllocationOvrds"
                    + " join fetch clientSetup.EDITTrxs editTrx"
                    + " join fetch editTrx.EDITTrxHistories editTrxHistory"
                    + " left join fetch editTrxHistory.ReinsuranceHistories"
                    + " left join fetch editTrxHistory.ChargeHistories"
                    + " left join fetch editTrxHistory.CommissionHistories commHistory"
                    + " left join fetch editTrxHistory.FinancialHistories"
                    + " left join fetch editTrxHistory.SegmentHistories"
                    + " left join fetch editTrxHistory.InSuspenses"
                    + " left join fetch editTrxHistory.WithholdingHistories"
                    + " left join fetch contractSetup.OutSuspenses"
                    + " where editTrx.EDITTrxPK = :editTrxPK";

        EDITMap params = new EDITMap().put("editTrxPK", editTrxPK);

        Session session = null;

        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

            List<GroupSetup> results = SessionHelper.executeHQL(session, hql, params, 0);

            if (!results.isEmpty())
            {
                groupSetup = results.get(0);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }

        return groupSetup;
    }
}
