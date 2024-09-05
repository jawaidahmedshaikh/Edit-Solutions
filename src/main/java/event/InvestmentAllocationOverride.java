/*
 * User: dlataill
 * Date: Oct 19, 2004
 * Time: 9:36:55 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import contract.*;

import edit.common.EDITMap;
import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import event.dm.dao.*;

import java.util.*;

import org.hibernate.Session;


public class InvestmentAllocationOverride extends HibernateEntity
{
    private InvestmentAllocationOverrideVO investmentAllocationOverrideVO;
    private InvestmentAllocationOverrideVO[] investmentAllocationOverrideVOs;
    private InvestmentAllocation investmentAllocation;
    private Investment investment;
    private ContractSetup contractSetup;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;



    public InvestmentAllocationOverride()
    {
        this.investmentAllocationOverrideVO = new InvestmentAllocationOverrideVO();
    }

    public InvestmentAllocationOverride(InvestmentAllocationOverrideVO invAllocOverrideVO)
    {
        investmentAllocationOverrideVO = invAllocOverrideVO;
    }

    public InvestmentAllocationOverride(long invAllocOvrdPK)
    {
        InvestmentAllocationOverrideVO[] invAllocOvrdVO = DAOFactory.getInvestmentAllocationOverrideDAO().findByInvestmentAllocationOverridePK(invAllocOvrdPK);

        if (invAllocOvrdVO != null)
        {
            investmentAllocationOverrideVO = invAllocOvrdVO[0];
        }
    }

    public InvestmentAllocationOverride(long contractSetupFK, long investmentFK,
                                         long investmentAllocationFK, String toFromStatus,
                                          String hfStatus, String hfiaIndicator, String holdingAccountIndicator)
       {
        init();

        this.investmentAllocationOverrideVO.setInvestmentAllocationOverridePK(0);
        this.investmentAllocationOverrideVO.setContractSetupFK(contractSetupFK);
        this.investmentAllocationOverrideVO.setInvestmentFK(investmentFK);
        this.investmentAllocationOverrideVO.setInvestmentAllocationFK(investmentAllocationFK);
        this.investmentAllocationOverrideVO.setToFromStatus(toFromStatus);
        this.investmentAllocationOverrideVO.setHFStatus(hfStatus);
        this.investmentAllocationOverrideVO.setHFIAIndicator(hfiaIndicator);
        this.investmentAllocationOverrideVO.setHoldingAccountIndicator(holdingAccountIndicator);
    }

    public InvestmentAllocationOverride(InvestmentAllocationOverrideVO[] investmentAllocationOverrideVOs)
    {
        this.investmentAllocationOverrideVOs = investmentAllocationOverrideVOs;
    }

    /**
     * Getter.
     * @return
     */
    public ContractSetup getContractSetup()
    {
        return contractSetup;
    }

    /**
     * Setter.
     * @param contractSetup
     */
    public void setContractSetup(ContractSetup contractSetup)
    {
        this.contractSetup = contractSetup;
    }

    /**
     * Guarantees that the VO is properly instantiated.
     */
    private final void init()
    {
        if (investmentAllocationOverrideVO == null)
        {
            investmentAllocationOverrideVO = new InvestmentAllocationOverrideVO();
        }
    }

    public InvestmentAllocationOverrideVO getHFIAOverride()
    {
        InvestmentAllocationOverrideVO hfiaInvAllocOvrd = null;

        for (int i = 0; i < investmentAllocationOverrideVOs.length; i++)
        {
            if (investmentAllocationOverrideVOs[i].getHFIAIndicator() != null &&
                investmentAllocationOverrideVOs[i].getHFIAIndicator().equalsIgnoreCase("Y"))
            {
                hfiaInvAllocOvrd = investmentAllocationOverrideVOs[i];
                break;
            }
        }

        return hfiaInvAllocOvrd;
    }

    public InvestmentAllocationOverrideVO getVO()
    {
        return this.investmentAllocationOverrideVO;
    }

    public int delete() throws Exception
    {
        CRUD crud = null;

        int deleteCount = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            crud.deleteVOFromDB(InvestmentAllocationOverrideVO.class, investmentAllocationOverrideVO.getInvestmentAllocationOverridePK());

            deleteCount = 1;
        }
        catch (Exception e)
        {
            if (crud != null)
            {
                crud.close();
            }

            crud = null;
        }

        return deleteCount;
    }
    /**
     * Saves the EDITTrx record non-recursively (no children will be saved)
     */
    public void saveNonRecursively()
    {
        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            crud.createOrUpdateVOInDB(investmentAllocationOverrideVO);
        }
        finally
        {
             if (crud != null) crud.close();
        }
    }

    /**
      * Getter
      * @return
      */
    public String getToFromStatus()
    {
        return investmentAllocationOverrideVO.getToFromStatus();
    }

    /**
      * Getter
      * @return
      */
    public Long getHedgeFundInvestmentFK()
    {
//        return investmentAllocationOverrideVO.getHedgeFundInvestmentFK();
        return SessionHelper.getPKValue(investmentAllocationOverrideVO.getHedgeFundInvestmentFK());

    }

    /**
       * Getter
       * @return
       */
    public InvestmentAllocation getInvestmentAllocation()
    {
        return this.investmentAllocation;
    }

    /**
       * Setter
       * @param investmentAllocation
       */
    public void setInvestmentAllocation(InvestmentAllocation investmentAllocation)
    {
        this.investmentAllocation = investmentAllocation;
    }

    /**
      * Getter.
      * @return
      */
    public Investment getInvestment()
    {
        return investment;
    }

    /**
       * Setter.
       * @param investment
       */
    public void setInvestment(Investment investment)
    {
        this.investment = investment;
    }

    /**
     * Getter.
     * @return
     */
    public Long getBucketFK()
    {
        return SessionHelper.getPKValue(investmentAllocationOverrideVO.getBucketFK());
    }

    //-- long getBucketFK()

    /**
     * Getter.
     * @return
     */
    public long getContractSetupFK()
    {
        return investmentAllocationOverrideVO.getContractSetupFK();
    }

    //-- long getContractSetupFK()

    /**
     * Getter.
     * @return
     */
    public String getHFIAIndicator()
    {
        return investmentAllocationOverrideVO.getHFIAIndicator();
    }

    /**
     * Getter.
     * @return
     */
    public String getHoldingAccountIndicator()
    {
        return investmentAllocationOverrideVO.getHoldingAccountIndicator();
    }

    //-- java.lang.String getHFIAIndicator()

    /**
     * Getter.
     * @return
     */
    public String getHFStatus()
    {
        return investmentAllocationOverrideVO.getHFStatus();
    }

    //-- java.lang.String getHFStatus()

    /**
     * Getter.
     * @return
     */
    public Long getInvestmentAllocationFK()
    {
        return SessionHelper.getPKValue(investmentAllocationOverrideVO.getInvestmentAllocationFK());
    }

    //-- long getInvestmentAllocationFK()

    /**
     * Getter.
     * @return
     */
    public Long getInvestmentAllocationOverridePK()
    {
        return SessionHelper.getPKValue(investmentAllocationOverrideVO.getInvestmentAllocationOverridePK());
    }

    //-- long getInvestmentAllocationOverridePK()

    /**
     * Getter.
     * @return
     */
    public Long getInvestmentFK()
    {
        return SessionHelper.getPKValue(investmentAllocationOverrideVO.getInvestmentFK());
    }

    //-- long getInvestmentFK()

    /**
     * Setter.
     * @param bucketFK
     */
    public void setBucketFK(Long bucketFK)
    {
        investmentAllocationOverrideVO.setBucketFK(SessionHelper.getPKValue(bucketFK));
    }

    //-- void setBucketFK(long)

    /**
     * Setter.
     * @param contractSetupFK
     */
    public void setContractSetupFK(long contractSetupFK)
    {
        investmentAllocationOverrideVO.setContractSetupFK(contractSetupFK);
    }

    //-- void setContractSetupFK(long)

    /**
     * Setter.
     * @param HFIAIndicator
     */
    public void setHFIAIndicator(String HFIAIndicator)
    {
        investmentAllocationOverrideVO.setHFIAIndicator(HFIAIndicator);
    }

    /**
     * Setter.
     * @param holdingAccountIndicator
     */
    public void setHoldingAccountIndicator(String holdingAccountIndicator)
    {
        investmentAllocationOverrideVO.setHoldingAccountIndicator(holdingAccountIndicator);
    }

    //-- void setHFIAIndicator(java.lang.String)

    /**
     * Setter.
     * @param HFStatus
     */
    public void setHFStatus(String HFStatus)
    {
        investmentAllocationOverrideVO.setHFStatus(HFStatus);
    }

    //-- void setHFStatus(java.lang.String)

    /**
     * Setter.
     * @param investmentAllocationFK
     */
    public void setInvestmentAllocationFK(Long investmentAllocationFK)
    {
        investmentAllocationOverrideVO.setInvestmentAllocationFK(SessionHelper.getPKValue(investmentAllocationFK));
    }

    //-- void setInvestmentAllocationFK(long)

    /**
     * Setter.
     * @param investmentAllocationOverridePK
     */
    public void setInvestmentAllocationOverridePK(Long investmentAllocationOverridePK)
    {
        investmentAllocationOverrideVO.setInvestmentAllocationOverridePK(SessionHelper.getPKValue(investmentAllocationOverridePK));
    }

    //-- void setInvestmentAllocationOverridePK(long)

    /**
     * Setter.
     * @param investmentFK
     */
    public void setInvestmentFK(Long investmentFK)
    {
        investmentAllocationOverrideVO.setInvestmentFK(SessionHelper.getPKValue(investmentFK));
    }

    //-- void setInvestmentFK(long)

    /**
     * Setter.
     * @param toFromStatus
     */
    public void setToFromStatus(String toFromStatus)
    {
        investmentAllocationOverrideVO.setToFromStatus(toFromStatus);
    }

    /**
     * Setter.
     * @param toFromStatus
     */
    public void setHedgeFundInvestmentFK(Long hedgeFundInvestmentFK)
    {
//        investmentAllocationOverrideVO.setHedgeFundInvestmentFK(hedgeFundInvestmentFK);
        investmentAllocationOverrideVO.setHedgeFundInvestmentFK(SessionHelper.getPKValue(hedgeFundInvestmentFK));
    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, InvestmentAllocationOverride.DATABASE);
    }

    public void hDelete()
    {
        SessionHelper.saveOrUpdate(this, InvestmentAllocationOverride.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return InvestmentAllocationOverride.DATABASE;
    }

    public static InvestmentAllocationOverride[] findBy_EDITTrxFK(Long editTrxFK)
    {
        String hql = " select investmentAllocationOverride from InvestmentAllocationOverride investmentAllocationOverride" +
                     " join investmentAllocationOverride.ContractSetup contractSetup" +
                     " join contractSetup.ClientSetups clientSetup" +
                     " join clientSetup.EDITTrxs editTrx" +
                     " where editTrx.EDITTrxPK = :editTrxFK";

        Map params = new HashMap();

        params.put("editTrxFK", editTrxFK);

        List results = SessionHelper.executeHQL(hql, params, InvestmentAllocationOverride.DATABASE);

        return (InvestmentAllocationOverride[]) results.toArray(new InvestmentAllocationOverride[results.size()]);
    }

    public static InvestmentAllocationOverride[] findByInvestmentFK(Long investmentFK)
    {
        InvestmentAllocationOverride[] investmentAllocationOverride = null;

        String hql = "select io from InvestmentAllocationOverride io join io.ContractSetup cs" +
                     " join cs.ClientSetups cls join cls.EDITTrxs et where io.InvestmentFK = :investmentFK" +
                     " and et.PendingStatus in ('P', 'M', 'L')";

        Map params = new HashMap();

        params.put("investmentFK", investmentFK);
//        params.put("pendingStatus", "('P', 'M', 'L')");

        List results = SessionHelper.executeHQL(hql, params, InvestmentAllocationOverride.DATABASE);

        if (results.size() > 0)
        {
            investmentAllocationOverride = (InvestmentAllocationOverride[]) results.toArray(new InvestmentAllocationOverride[results.size()]);
        }

        return investmentAllocationOverride;
    }

  /**
   * Finder. Note that a separate Hibernate Session is used.
   * @param long1
   * @return
   */
  public static InvestmentAllocationOverride[] findSeparateBy_ContractSetupPK(Long contractSetupPK)
  {
    List<InvestmentAllocationOverride> results = null;
  
    String hql = " from InvestmentAllocationOverride investmentAllocationOverride" +
                " where investmentAllocationOverride.ContractSetupFK = :contractSetupPK";
                
    Map params = new EDITMap("contractSetupPK", contractSetupPK);
    
    Session session = null;
    
    try
    {
      session = SessionHelper.getSeparateSession(InvestmentAllocationOverride.DATABASE);
      
      results = SessionHelper.executeHQL(session, hql, params, 0);
    }
    finally
    {
      if (session != null) session.close();
    }
    
    return results.toArray(new InvestmentAllocationOverride[results.size()]);
  }
}
