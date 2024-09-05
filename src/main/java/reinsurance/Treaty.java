/*
 * User: gfrosti
 * Date: Nov 18, 2004
 * Time: 8:16:12 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package reinsurance;

import edit.common.vo.*;
import edit.common.*;
import edit.common.exceptions.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import reinsurance.dm.dao.*;
import event.financial.group.trx.*;
import event.*;

import java.math.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import logging.*;



public class Treaty extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;

    private TreatyVO treatyVO;

    private Set<ContractTreaty> contractTreaties = new HashSet<ContractTreaty>();
    private TreatyGroup treatyGroup;
    private Reinsurer reinsurer;

    private String status;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /************************************** Constructor Methods **************************************/
    /**
     * Instantiates a Treaty entity with a default TreatyVO.
     */
    public Treaty()
    {
        init();
    }

    /**
     * Instantiates a Treaty entity with a TreatyVO retrieved from persistence.
     * @param treatyPK
     */
    public Treaty(long treatyPK)
    {
        init();

        crudEntityImpl.load(this, treatyPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Instantiates a Treaty entity with a supplied TreatyVO.
     * @param treatyVO
     */
    public Treaty(TreatyVO treatyVO)
    {
        init();

        this.treatyVO = treatyVO;
    }

    /************************************** Public Methods **************************************/
    /**
     * @return
     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    /**
     * Creates a pending CheckTransaction. The ReinsurerBalance is assumed to be > $0.00.
     * @param operator
     */
    public void createCheckTransaction(String operator)
    {
        EDITTrx editTrx = null;

        try
        {
            GroupTrx groupTrx = new GroupTrx();

            EDITDate effectiveDate = new EDITDate();

            editTrx = groupTrx.buildCheckTransactionGroup("RCK", getPK(), ClientSetup.TYPE_TREATY,
                                                          getReinsurerBalance(), operator,
                                                          effectiveDate.getFormattedDate(), new EDITBigDecimal("0", 2),
                                                          new EDITBigDecimal("0", 2), null);

            setReinsurerBalance(new EDITBigDecimal(new BigDecimal("0.00")));

            save();
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            logErrorToDatabase(e, editTrx.getContractNumber());
        }
    }

    /**
     * @throws EDITReinsuranceException
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws EDITReinsuranceException
    {
        boolean shouldDelete = checkConstraints();

        if (shouldDelete)
        {
            crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
        }
        else
        {
            this.save();
        }
    }

    /**
     * Getter.
     * @return
     */
    public String getCalculationModeCT()
    {
        return treatyVO.getCalculationModeCT();
    } //-- java.lang.String getCalculationModeCT()

    /**
     * Getter.
     * @return
     */
    public EDITDate getLastCheckDate()
    {
        return SessionHelper.getEDITDate(treatyVO.getLastCheckDate());
    } //-- java.lang.String getLastCheckDate()

    /**
     * Getter.
     * @return
     */
    public String getPaymentModeCT()
    {
        return treatyVO.getPaymentModeCT();
    } //-- java.lang.String getPaymentModeCT()


    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPoolPercentage()
    {
        return SessionHelper.getEDITBigDecimal(treatyVO.getPoolPercentage());
    } //-- java.math.BigDecimal getPoolPercentage()

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getReinsurerBalance()
    {
        return SessionHelper.getEDITBigDecimal(treatyVO.getReinsurerBalance());
    } //-- java.math.BigDecimal getReinsurerBalance()

    /**
     * Getter.
     * @return
     */
    public Long getReinsurerFK()
    {
        return SessionHelper.getPKValue(treatyVO.getReinsurerFK());
    } //-- long getReinsurerFK()

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getRetentionAmount()
    {
        return SessionHelper.getEDITBigDecimal(treatyVO.getRetentionAmount());
    } //-- java.math.BigDecimal getRetentionAmount()

    /**
     * Getter.
     * @return
     */
    public int getSettlementPeriod()
    {
        return treatyVO.getSettlementPeriod();
    } //-- int getSettlementPeriod()

    /**
     * Getter.
     * @return
     */
    public EDITDate getStartDate()
    {
        return SessionHelper.getEDITDate(treatyVO.getStartDate());
    } //-- java.lang.String getStartDate()

    /**
     * Getter.
     * @return
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getStopDate()
    {
        return SessionHelper.getEDITDate(treatyVO.getStopDate());
    } //-- java.lang.String getStopDate()

    /**
     * Getter.
     * @return
     */
    public Long getTreatyGroupFK()
    {
        return SessionHelper.getPKValue(treatyVO.getTreatyGroupFK());
    } //-- long getTreatyGroupFK()

    /**
     * Getter.
     * @return
     */
    public Long getTreatyPK()
    {
        return SessionHelper.getPKValue(treatyVO.getTreatyPK());
    } //-- long getTreatyPK()

    /**
     * Setter.
     * @param calculationModeCT
     */
    public void setCalculationModeCT(String calculationModeCT)
    {
        treatyVO.setCalculationModeCT(calculationModeCT);
    } //-- void setCalculationModeCT(java.lang.String)

    /**
     * Setter.
     * @param lastCheckDate
     */
    public void setLastCheckDate(EDITDate lastCheckDate)
    {
        treatyVO.setLastCheckDate(SessionHelper.getEDITDate(lastCheckDate));
    } //-- void setLastCheckDate(java.lang.String)

    /**
     * Setter.
     * @param paymentModeCT
     */
    public void setPaymentModeCT(String paymentModeCT)
    {
        treatyVO.setPaymentModeCT(paymentModeCT);
    } //-- void setPaymentModeCT(java.lang.String)

    /**
     * Setter.
     * @param poolPercentage
     */
    public void setPoolPercentage(EDITBigDecimal poolPercentage)
    {
        treatyVO.setPoolPercentage(SessionHelper.getEDITBigDecimal(poolPercentage));
    } //-- void setPoolPercentage(java.math.BigDecimal)

    /**
     * Setter.
     * @param reinsurerBalance
     */
    public void setReinsurerBalance(EDITBigDecimal reinsurerBalance)
    {
        treatyVO.setReinsurerBalance(SessionHelper.getEDITBigDecimal(reinsurerBalance));
    } //-- void setReinsurerBalance(java.math.BigDecimal)

    /**
     * Setter.
     * @param reinsurerFK
     */
    public void setReinsurerFK(Long reinsurerFK)
    {
        treatyVO.setReinsurerFK(SessionHelper.getPKValue(reinsurerFK));
    } //-- void setReinsurerFK(long)

    /**
     * Setter.
     * @param settlementPeriod
     */
    public void setSettlementPeriod(int settlementPeriod)
    {
        treatyVO.setSettlementPeriod(settlementPeriod);
    } //-- void setSettlementPeriod(int)

    /**
     * Setter.
     * @param retentionAmount
     */
    public void setRetentionAmount(EDITBigDecimal retentionAmount)
    {
        treatyVO.setRetentionAmount(SessionHelper.getEDITBigDecimal(retentionAmount));
    } //-- void setRetentionAmount(java.math.BigDecimal)

    /**
     * Setter.
     * @param startDate
     */
    public void setStartDate(EDITDate startDate)
    {
        treatyVO.setStartDate(SessionHelper.getEDITDate(startDate));
    } //-- void setStartDate(java.lang.String)

    /**
     * Setter.
     * @param status
     */
    public void setStatus(String status)
    {
        this.status = status;
    }

    /**
     * Setter.
     * @param stopDate
     */
    public void setStopDate(EDITDate stopDate)
    {
        treatyVO.setStopDate(SessionHelper.getEDITDate(stopDate));
    } //-- void setStopDate(java.lang.String)

    /**
     * Setter.
     * @param treatyGroupFK
     */
    public void setTreatyGroupFK(Long treatyGroupFK)
    {
        treatyVO.setTreatyGroupFK(SessionHelper.getPKValue(treatyGroupFK));
    } //-- void setTreatyGroupFK(long)

    /**
     * Setter.
     * @param treatyPK
     */
    public void setTreatyPK(Long treatyPK)
    {
        treatyVO.setTreatyPK(SessionHelper.getPKValue(treatyPK));
    } //-- void setTreatyPK(long)

    /**
     * Getter.
     * @return
     */
    public Set<ContractTreaty> getContractTreaties()
    {
        return contractTreaties;
    }

    /**
     * Setter.
     * @param contractTreaties
     */
    public void setContractTreaties(Set<ContractTreaty> contractTreaties)
    {
        this.contractTreaties = contractTreaties;
    }

    /**
     * Getter.
     * @return
     */
    public TreatyGroup getTreatyGroup()
    {
        return treatyGroup;
    }

    /**
     * Setter.
     * @param treatyGroup
     */
    public void setTreatyGroup(TreatyGroup treatyGroup)
    {
        this.treatyGroup = treatyGroup;
    }

    /**
     * Getter.
     * @return
     */
    public Reinsurer getReinsurer()
    {
        return reinsurer;
    }

    /**
     * Setter.
     * @param reinsurer
     */
    public void setReinsurer(Reinsurer reinsurer)
    {
        this.reinsurer = reinsurer;
    }

    /**
     * Errors if this Treaty has ContractTreaty, or ClientSetup associations.
     * @throws EDITReinsuranceException
     */
    private boolean checkConstraints() throws EDITReinsuranceException
    {
        boolean shouldDelete = true;

        ContractTreaty[] contractTreaties = ContractTreaty.findBy_TreatyPK(getPK());

        if (contractTreaties != null)
        {
            this.setStatus("D");
            shouldDelete = false;
        }

        ClientSetup[] clientSetups = ClientSetup.findBy_TreatyPK(getPK());

        if (clientSetups != null)
        {
            this.setStatus("D");
            shouldDelete = false;
        }

        return shouldDelete;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return treatyVO.getTreatyPK();
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return treatyVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#isNew()
     */
    public boolean isNew()
    {
        return crudEntityImpl.isNew(this);
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save()
    {
        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.treatyVO = (TreatyVO) voObject;
    }

    /**
     * Adds the supplied ModalPremiumAmount the current ReinsurerBalance
     * @param modalPremiumAmount
     */
    public void updateReinsurerBalance(EDITBigDecimal modalPremiumAmount, String reinsuranceTypeCT)
    {
        EDITBigDecimal newBalance = new EDITBigDecimal();

        if (reinsuranceTypeCT.equalsIgnoreCase("ChargeBack"))
        {
            newBalance = getReinsurerBalance().subtractEditBigDecimal(modalPremiumAmount);
        }
        else
        {
            newBalance = getReinsurerBalance().addEditBigDecimal(modalPremiumAmount);
        }

        setReinsurerBalance(newBalance);
    }

    /************************************** Private Methods **************************************/
    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (treatyVO == null)
        {
            treatyVO = new TreatyVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Treaty.DATABASE;
    }

    /************************************** Static Methods **************************************/
    /**
     * Finder.
     * @param productStructurePK
     * @return
     */
    public static final Treaty[] findBy_ProductStructurePK(long productStructurePK)
    {
        return (Treaty[]) CRUDEntityImpl.mapVOToEntity(new TreatyDAO().findBy_ProductStructurePK(productStructurePK), Treaty.class);
    }

    /**
     * Finder.
     * @param treatyGroupPK
     * @return
     */
    public static final Treaty[] findBy_TreatyGroupPK(Long treatyGroupPK)
    {
        String hql = " from Treaty treaty where treaty.TreatyGroupFK = :treatyGroupPK";

        Map params = new HashMap();

        params.put("treatyGroupPK", treatyGroupPK);

        List<Treaty> results = SessionHelper.executeHQL(hql, params, DATABASE);

        return results.toArray(new Treaty[results.size()]);
    }

    /**
     * Finder.
     * @param treatyGroupPK
     * @return
     */
    public static final Treaty[] findBy_TreatyGroupPK_SegmentPK(long treatyGroupPK, long segmentPK)
    {
        return (Treaty[]) CRUDEntityImpl.mapVOToEntity(new TreatyDAO().findBy_TreatyGroupPK_SegmentPK(treatyGroupPK, segmentPK), Treaty.class);
    }

    /**
     * Finder.
     * @param reinsurerNumber
     * @return
     */
    public static final Treaty[] findBy_ReinsurerNumber(String reinsurerNumber)
    {
        return (Treaty[]) CRUDEntityImpl.mapVOToEntity(new TreatyDAO().findBy_ReinsurerNumber(reinsurerNumber), Treaty.class);
    }

    /**
     * Finder.
     * @param contractTreatyPK
     * @return
     */
    public static final Treaty findBy_ContractTreatyPK(long contractTreatyPK)
    {
        Treaty treaty = null;

        TreatyVO[] treatyVOs = new TreatyDAO().findBy_ContractTreatyPK(contractTreatyPK);

        if (treatyVOs != null)
        {
            treaty = new Treaty(treatyVOs[0]);
        }

        return treaty;
    }

    /**
     * Finds all Treaties that have a ReinsuranceProcess greater than the specified amount.
     * @param reinsurerBalance
     * @return
     */
    public static final Treaty[] findBy_ReinsurerBalance_GT(double reinsurerBalance)
    {
        return (Treaty[]) CRUDEntityImpl.mapVOToEntity(new TreatyDAO().findBy_ReinsurerBalance_GT(reinsurerBalance), Treaty.class);
    }

    /**
     * Finds all Treaties that have a ReinsurereBalance greater than the specified amount for the specified ProductStructure.
     * @param reinsurerBalance
     * @param productStructurePK
     * @return
     */
    public static Treaty[] findBy_ReinsurerBalance_GT_ProductStructurePK(double reinsurerBalance, long productStructurePK)
    {
        return (Treaty[]) CRUDEntityImpl.mapVOToEntity(new TreatyDAO().findBy_ReinsurerBalance_GT_ProductStructurePK(reinsurerBalance, productStructurePK), Treaty.class);
    }

    public static Treaty findBy_TreatyPK(long treatyPK)
    {
        Treaty treaty = null;

        TreatyVO[] treatyVOs = new TreatyDAO().findBy_PK(treatyPK);

        if (treatyVOs != null)
        {
            treaty = new Treaty(treatyVOs[0]);
        }

        return treaty;
	}

    /**
     * Finder.
     * @param parseLong
     * @param parseLong0
     * @return
     */
    public static Treaty[] findBy_TreatyGroupPK_ContractGroupPK(Long treatyGroupPK, Long contractGroupPK)
    {
        String hql = " select treaty from Treaty treaty" +
                    " join treaty.ContractTreaties contractTreaty" +
                    " where treaty.TreatyGroupFK = :treatyGroupPK" +
                    " and contractTreaty.ContractGroupFK = :contractGroupPK";

        Map params = new HashMap();

        params.put("treatyGroupPK", treatyGroupPK);

        params.put("contractGroupPK", contractGroupPK);

        List<Treaty> results = SessionHelper.executeHQL(hql, params, DATABASE);

        return results.toArray(new Treaty[results.size()]);
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Treaty.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, Treaty.DATABASE);
    }

    private void logErrorToDatabase(Exception e, String contractNumber)
    {
        EDITMap columnInfo = new EDITMap("ProcessDate", new EDITDate().getFormattedDate());
        columnInfo.put("ContractNumber", contractNumber);

        Log.logToDatabase(Log.BUILD_REINSURANCE_CK, e.getMessage(), columnInfo);
    }

  /**
   * Adder.
   * @param contractTreaty
   */
  public void add(ContractTreaty contractTreaty)
  {
    getContractTreaties().add(contractTreaty);
    
    contractTreaty.setTreaty(this);
  }
}

