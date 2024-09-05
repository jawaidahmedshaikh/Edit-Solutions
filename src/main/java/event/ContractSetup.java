/*
 * User: gfrosti
 * Date: Dec 2, 2004
 * Time: 2:19:10 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import contract.Segment;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.vo.ContractSetupVO;
import edit.common.vo.VOObject;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import event.dm.dao.ContractSetupDAO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ContractSetup extends HibernateEntity implements CRUDEntity
{
    public static final String COMPLEXCHANGETYPECT_ROTHCONVERSION = "ROTHConversion";
    public static final String COMPLEXCHANGETYPECT_LIST = "List";
    public static final String COMPLEXCHANGETYPECT_RIDER_DELETE = "RiderDelete";
    public static final String COMPLEXCHANGETYPECT_OWNER_DELETE = "OwnerDelete";
    public static final String COMPLEXCHANGETYPECT_RIDER_ADD = "RiderAdd";
    public static final String COMPLEXCHANGETYPECT_RIDER_CHANGE = "RiderChange";
    public static final String COMPLEXCHANGETYPECT_RIDER_TERM = "RiderTerm";
    public static final String COMPLEXCHANGETYPECT_BATCH = "Batch";
    public static final String COMPLEXCHANGETYPECT_BILLMETHOD = "BillMethod";
    public static final String COMPLEXCHANGETYPECT_DEDUCTIONFREQUENCY = "DedFreq";
    public static final String COMPLEXCHANGETYPECT_TRANSDATE = "TransDate";
    public static final String COMPLEXCHANGETYPECT_BILLFREQUENCY = "BillFreq";
    public static final String COMPLEXCHANGETYPECT_INCOMPLETE = "Incomplete";
    public static final String COMPLEXCHANGETYPECT_REOPEN = "Reopen";
    public static final String COMPLEXCHANGETYPECT_SCHEDPREM = "SchedPrem";
    public static final String COMPLEXCHANGETYPECT_CLAIMSTOP = "ClaimStop";
    public static final String COMPLEXCHANGETYPECT_CLASSCHANGE = "ClassChange";
    public static final String COMPLEXCHANGETYPECT_DBOCHANGE = "DBOChange";
    public static final String COMPLEXCHANGETYPECT_FACEDECREASE = "FaceDecrease";
    public static final String COMPLEXCHANGETYPECT_FACEINCREASE = "FaceIncrease";
    public static final String COMPLEXCHANGETYPECT_EFTDRAFTDAY = "EFTDraftDay";

    public static final String[] TERMINATION_COMPLEXCHANGETYPES = new String[] {"DeclinedMed", "DeclineElig", "DeclineReq", "Incomplete", "Postponed", "Withdrawn"};

    private CRUDEntityImpl crudEntityImpl;
    private ContractSetupVO contractSetupVO;
    private GroupSetup groupSetup;
    private Set<ClientSetup> clientSetups;
    private Set<InvestmentAllocationOverride> investmentAllocationOverrides;
    private Set<OutSuspense> outSuspenses;
    private Segment segment;
    private ClientSetup clientSetup;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a ContractSetup entity with a default ContractSetupVO.
     */
    public ContractSetup()
    {
        init();
    }

    /**
     * Instantiates a ContractSetup entity with a ContractSetupVO retrieved from persistence.
     * @param contractSetupPK
     */
    public ContractSetup(long contractSetupPK)
    {
        init();

        crudEntityImpl.load(this, contractSetupPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Instantiates a ContractSetup entity with a supplied ContractSetupVO.
     * @param contractSetupVO
     */
    public ContractSetup(ContractSetupVO contractSetupVO)
    {
        init();

        this.contractSetupVO = contractSetupVO;
    }

    /**
     * Getter.
     * @return
     */
    public Set<InvestmentAllocationOverride> getInvestmentAllocationOverrides()
    {
        return investmentAllocationOverrides;
    }

    /**
     * Setter.
     * @param investmentAllocationOverrides
     */
    public void setInvestmentAllocationOverrides(Set<InvestmentAllocationOverride> investmentAllocationOverrides)
    {
        this.investmentAllocationOverrides = investmentAllocationOverrides;
    }

    /**
     * Adder.
     * @param investmentAllocationOverride
     */
    public void addInvestmentAllocationOverride(InvestmentAllocationOverride investmentAllocationOverride)
    {
        getInvestmentAllocationOverrides().add(investmentAllocationOverride);

        investmentAllocationOverride.setContractSetup(this);

        SessionHelper.saveOrUpdate(investmentAllocationOverride, ContractSetup.DATABASE);
    }

    /**
     * @param investmentAllocationOverride
     */
    public void removeInvestmentAllocationOverride(InvestmentAllocationOverride investmentAllocationOverride)
    {
        getInvestmentAllocationOverrides().remove(investmentAllocationOverride);

        investmentAllocationOverride.setContractSetup(this);

        SessionHelper.saveOrUpdate(investmentAllocationOverride, ContractSetup.DATABASE);
    }


    /**
     * Getter.
     * @return
     */
    public Long getGroupSetupFK()
    {
        return SessionHelper.getPKValue(contractSetupVO.getGroupSetupFK());
    }

    //-- long getGroupSetupFK() 

    /**
     * Getter.
     * @return
     */
    public Long getSegmentFK()
    {
        return SessionHelper.getPKValue(contractSetupVO.getSegmentFK());
    }

    //-- long getSegmentFK() 

    /**
     * Setter.
     * @param segmentFK
     */
    public void setSegmentFK(Long segmentFK)
    {
        contractSetupVO.setSegmentFK(SessionHelper.getPKValue(segmentFK));
    }

    //-- void setSegmentFK(long) 

    /**
     * Setter.
     * @param groupSetupFK
     */
    public void setGroupSetupFK(Long groupSetupFK)
    {
        contractSetupVO.setGroupSetupFK(SessionHelper.getPKValue(groupSetupFK));
    }

    //-- void setGroupSetupFK(long) 

    /**
     * Getter.
     * @return
     */
    public Set<ClientSetup> getClientSetups()
    {
        return clientSetups;
    }

    /**
     * Setter.
     * @param clientSetups
     */
    public void setClientSetups(Set<ClientSetup> clientSetups)
    {
        this.clientSetups = clientSetups;
    }

    /**
     * Setter.
     * @param outSuspenses
     */
    public void setOutSuspenses(Set<OutSuspense> outSuspenses)
    {
        this.outSuspenses = outSuspenses;
    }

    /**
     * Getter.
     * @return
     */
    public Set<OutSuspense> getOutSuspenses()
    {
        return outSuspenses;
    }

//    public void addOutSuspense(OutSuspense[] outSuspenses)
//    {
//        for (int i = 0; i < outSuspenses.length; i++)
//        {
//            getOutSuspenses().add(outSuspenses[i]);
//
//            outSuspenses[i].setContractSetup(this);
//        }
//    }

    public void addOutSuspense(OutSuspense outSuspense)
    {
        getOutSuspenses().add(outSuspense);

        outSuspense.setContractSetup(this);

        SessionHelper.saveOrUpdate(outSuspense, ContractSetup.DATABASE);
    }
    

    public void setGroupSetup(GroupSetup groupSetup)
    {
        this.groupSetup = groupSetup;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAmountReceived()
    {
        return SessionHelper.getEDITBigDecimal(contractSetupVO.getAmountReceived());
    }

    //-- java.math.BigDecimal getAmountReceived() 

    /**
     * Getter.
     * @return
     */
    public String getClaimStatusCT()
    {
        return contractSetupVO.getClaimStatusCT();
    }

    //-- java.lang.String getClaimStatusCT() 

    /**
     * Getter.
     * @return
     */
    public String getComplexChangeNewValue()
    {
        return contractSetupVO.getComplexChangeNewValue();
    }

    //-- java.lang.String getComplexChangeNewValue() 

    /**
     * Getter.
     * @return
     */
    public String getComplexChangeTypeCT()
    {
        return contractSetupVO.getComplexChangeTypeCT();
    }

    //-- java.lang.String getComplexChangeTypeCT() 

    /**
     * Getter.
     * @return
     */
    public Long getContractSetupPK()
    {
        return SessionHelper.getPKValue(contractSetupVO.getContractSetupPK());
    }

    //-- long getContractSetupPK() 

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getCostBasis()
    {
        return SessionHelper.getEDITBigDecimal(contractSetupVO.getCostBasis());
    }

    //-- java.math.BigDecimal getCostBasis() 

    /**
     * Getter.
     * @return
     */
    public String getDeathStatusCT()
    {
        return contractSetupVO.getDeathStatusCT();
    }

    //-- java.lang.String getDeathStatusCT() 

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPolicyAmount()
    {
        return SessionHelper.getEDITBigDecimal(contractSetupVO.getPolicyAmount());
    }

    //-- java.math.BigDecimal getPolicyAmount() 

    /**
     * Getter.
     * @return
     */
    public String getSuppressDecreaseFaceInd()
    {
        return contractSetupVO.getSuppressDecreaseFaceInd();
    }

    /**
     * Getter.
     * @return
     */
    public String getUserInvestmentOverrideInd()
    {
        return contractSetupVO.getUserInvestmentOverrideInd();
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getDateOfDeath()
    {
        return SessionHelper.getEDITDate(contractSetupVO.getDateOfDeath());
    }

    /**
     * Getter.
     * @return
     */
    public String getCareTypeCT()
    {
        return contractSetupVO.getCareTypeCT();
    }

   /**
     * Getter.
     * @return
     */
    public String getConditionCT()
    {
        return contractSetupVO.getConditionCT();
    }



    /**
     * Setter.
     * @param amountReceived
     */
    public void setAmountReceived(EDITBigDecimal amountReceived)
    {
        contractSetupVO.setAmountReceived(SessionHelper.getEDITBigDecimal(amountReceived));
    }

    //-- void setAmountReceived(java.math.BigDecimal) 

    /**
     * Setter.
     * @param claimStatusCT
     */
    public void setClaimStatusCT(String claimStatusCT)
    {
        contractSetupVO.setClaimStatusCT(claimStatusCT);
    }

    //-- void setClaimStatusCT(java.lang.String) 

    /**
     * Setter.
     * @param complexChangeNewValue
     */
    public void setComplexChangeNewValue(String complexChangeNewValue)
    {
        contractSetupVO.setComplexChangeNewValue(complexChangeNewValue);
    }

    //-- void setComplexChangeNewValue(java.lang.String) 

    /**
     * Setter.
     * @param complexChangeTypeCT
     */
    public void setComplexChangeTypeCT(String complexChangeTypeCT)
    {
        contractSetupVO.setComplexChangeTypeCT(complexChangeTypeCT);
    }

    //-- void setComplexChangeTypeCT(java.lang.String) 

    /**
     * Setter.
     * @param contractSetupPK
     */
    public void setContractSetupPK(Long contractSetupPK)
    {
        contractSetupVO.setContractSetupPK(SessionHelper.getPKValue(contractSetupPK));
    }

    //-- void setContractSetupPK(long) 

    /**
     * Setter.
     * @param costBasis
     */
    public void setCostBasis(EDITBigDecimal costBasis)
    {
        contractSetupVO.setCostBasis(SessionHelper.getEDITBigDecimal(costBasis));
    }

    //-- void setCostBasis(java.math.BigDecimal) 

    /**
     * Setter.
     * @param deathStatusCT
     */
    public void setDeathStatusCT(String deathStatusCT)
    {
        contractSetupVO.setDeathStatusCT(deathStatusCT);
    }

    //-- void setDeathStatusCT(java.lang.String) 

    /**
     * Setter.
     * @param policyAmount
     */
    public void setPolicyAmount(EDITBigDecimal policyAmount)
    {
        contractSetupVO.setPolicyAmount(SessionHelper.getEDITBigDecimal(policyAmount));
    }

    //-- void setPolicyAmount(java.math.BigDecimal) 

    /**
     * Setter.
     * @param suppressDecreaseFaceInd
     */
    public void setSuppressDecreaseFaceInd(String suppressDecreaseFaceInd)
    {
        contractSetupVO.setSuppressDecreaseFaceInd(suppressDecreaseFaceInd);
    }

    /**
     * Setter.
     * @param userInvestmentOverrideInd
     */
    public void setUserInvestmentOverrideInd(String userInvestmentOverrideInd)
    {
        contractSetupVO.setUserInvestmentOverrideInd(userInvestmentOverrideInd);
    }

    /**
     * Setter.
     * @param dateOfDeath
     */
    public void setDateOfDeath(EDITDate dateOfDeath)
    {
        contractSetupVO.setDateOfDeath(SessionHelper.getEDITDate(dateOfDeath));
    }

    /**
     * Setter.
     * @param careTypeCT
     */
    public void setCareTypeCT(String careTypeCT)
    {
        contractSetupVO.setCareTypeCT(careTypeCT);
    }

    /**
      * Setter.
      * @param conditionCT
      */
     public void setConditionCT(String conditionCT)
     {
         contractSetupVO.setConditionCT(conditionCT);
     }
    
    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (contractSetupVO == null)
        {
            contractSetupVO = new ContractSetupVO();
        }

        if (investmentAllocationOverrides == null)
        {
            investmentAllocationOverrides = new HashSet<InvestmentAllocationOverride>();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }

        clientSetups = new HashSet<ClientSetup>();

        outSuspenses = new HashSet<OutSuspense>();
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save()
    {
        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    /**
     * @throws Throwable
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws Throwable
    {
        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return contractSetupVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return contractSetupVO.getContractSetupPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.contractSetupVO = (ContractSetupVO) voObject;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#isNew()
     */
    public boolean isNew()
    {
        return crudEntityImpl.isNew(this);
    }

    public ContractSetupVO getAsVO()
    {
        return contractSetupVO;
    }

    /**
     * Setter.
     * @param segment
     */
    public void setSegment(Segment segment)
    {
        this.segment = segment;
    }

    /**
     * Getter.
     * @return
     */
    public Segment getSegment()
    {
        return segment; // Hibernate
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    /**
     * Finder.
     * @param groupSetupFK
     * @return
     */
    public static final ContractSetup[] findBy_GroupSetupFK(long groupSetupFK)
    {
        return (ContractSetup[]) CRUDEntityImpl.mapVOToEntity(new ContractSetupDAO().findByGroupSetupPK(groupSetupFK), ContractSetup.class);
    }

    /**
     * CRUD Getter.
     * @return
     */
    public GroupSetup get_GroupSetup()
    {
        return new GroupSetup(contractSetupVO.getGroupSetupFK());
    }

   /**
     * CRUD Getter.
     * @return
     */
    public Segment get_Segment()
    {
        return new Segment(contractSetupVO.getSegmentFK());
    }

    /**
     * Hibernate getter.
     * @return
     */
    public GroupSetup getGroupSetup()
    {
        return groupSetup;
    }

    /**
     * Set a single clientSetup
     * @param clientSetup
     */
   public void addClientSetup(ClientSetup clientSetup)
    {
        getClientSetups().add(clientSetup);

        clientSetup.setContractSetup(this);

        SessionHelper.saveOrUpdate(clientSetup, ContractSetup.DATABASE);
    }

    /**
     * Get a single ClientSetup
     * @return
     */
    public ClientSetup getClientSetup()
    {
        ClientSetup clientSetup =getClientSetups().isEmpty() ? null : (ClientSetup) getClientSetups().iterator().next();

        return clientSetup;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ContractSetup.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, ContractSetup.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ContractSetup.DATABASE;
    }

    /**
     * Originally in ContractSetupDAO.findByEDITTrxPK
     * @param editTrxPK
     * @return
     */
    public static ContractSetup[] findBy_EDITTrxPK(Long editTrxPK)
    {
        String hql = "select contractSetup from ContractSetup contractSetup " +
                     " join contractSetup.ClientSetup clientSetup " +
                     " join clientSetup.EDITTrx editTrx " +
                     " where editTrx.EDITTrxPK = :editTrxPK";

        Map params = new HashMap();
        params.put("editTrxPK", editTrxPK);


        List results = SessionHelper.executeHQL(hql, params, ContractSetup.DATABASE);

        return (ContractSetup[]) results.toArray(new ContractSetup[results.size()]);
    }

    public static ContractSetupVO findByPK(long contractSetupPK)
    {
        ContractSetupVO[] contractSetupVOs = new ContractSetupDAO().findByContractSetupPK(contractSetupPK);
        ContractSetupVO contractSetupVO = null;

        if (contractSetupVOs != null)
        {
            contractSetupVO =  (contractSetupVOs[0]);   
        }

        return contractSetupVO;
    }
    
    /**
     * Hibernate finder.
     * @param contractSetupPK
     * @return
     */
    public static final ContractSetup findByPK(Long contractSetupPK)
    {
        ContractSetup contractSetup = (ContractSetup) SessionHelper.get(ContractSetup.class, contractSetupPK, ContractSetup.DATABASE);

        return contractSetup;
    }

  /**
   * True if there are the associated child entities.
   * @return boolean
   */
  public boolean hasInvestmentAllocationOverrides()
  {
    return (!getInvestmentAllocationOverrides().isEmpty());
  }

    /**
     * Removes a EDITTrx from the set of children
     * @param editTrx
     */
    public void removeClientSetup(ClientSetup clientSetup)
    {
        this.getClientSetups().remove(clientSetup);

        clientSetup.setContractSetup(null);

        SessionHelper.saveOrUpdate(this, ContractSetup.DATABASE);
    }
}
