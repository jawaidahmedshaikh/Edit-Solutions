/*
 * User: dlataill
 * Date: Oct 19, 2004
 * Time: 9:23:50 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import event.*;

import java.math.*;
import java.util.*;

import contract.dm.dao.*;


public class InvestmentAllocation extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;
    private InvestmentAllocationVO investmentAllocationVO;
    private InvestmentAllocationVO[] investmentAllocationVOs;
    private long investmentAllocationPK;
    private Segment segment;
    private Investment investment;
    private InvestmentAllocationOverride investmentAllocationOverride;
    private Set<InvestmentAllocationOverride> investmentAllocationOverrides;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;



    public InvestmentAllocation()
    {
        init();
    }

    public InvestmentAllocation(InvestmentAllocationVO[] investmentAllocationVOs)
    {
        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }

        this.investmentAllocationVOs = investmentAllocationVOs;
    }

    /**
     * Instantiates an InvestmentAllocation with its data inVO format
     * @param investmentAllocationVOs
     */
    public InvestmentAllocation(InvestmentAllocationVO investmentAllocationVO)
    {
        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }

        this.investmentAllocationVO = investmentAllocationVO;
    }

    /**
      * Instantiates an InvestmentAllocation with its data inVO format and Segment entity
      * @param investmentAllocationVOs
      */
    public InvestmentAllocation(InvestmentAllocationVO investmentAllocationVO, Segment segment)
    {
        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }

        this.investmentAllocationVO = investmentAllocationVO;
        this.segment = segment;
    }

    /**
      * Instantiates an InvestmentAllocation using the given investmentAllocationPK
      * @param investmentAllocationPK
      */
    public InvestmentAllocation(long investmentAllocationPK)
    {
        init();

        crudEntityImpl.load(this, investmentAllocationPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    public InvestmentAllocation(long investmentFK, BigDecimal allocation, String overrideStatus, String allocationType)
    {
        init();

        this.investmentAllocationVO.setInvestmentAllocationPK(0);
        this.investmentAllocationVO.setInvestmentFK(investmentFK);

        if (allocationType.equalsIgnoreCase("Percent"))
        {
            this.investmentAllocationVO.setAllocationPercent(allocation);
        }
        else
        {
            this.investmentAllocationVO.setDollars(allocation);
        }

        this.investmentAllocationVO.setOverrideStatus(overrideStatus);
    }

    /**
     * Getter.
     * @return
     */
    public InvestmentAllocationOverride getInvestmentAllocationOverride()
    {
        return investmentAllocationOverride;
    }

    /**
     * Setter.
     * @param investmentAllocationOverride
     */
    public void setInvestmentAllocationOverride(InvestmentAllocationOverride investmentAllocationOverride)
    {
        this.investmentAllocationOverride = investmentAllocationOverride;
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
     * Adds a InvestmentAllocationOverride to the set of children
     * @param investmentAllocationOverride
     */
    public void addInvestmentAllocationOverride(InvestmentAllocationOverride investmentAllocationOverride)
    {
        this.getInvestmentAllocationOverrides().add(investmentAllocationOverride);

        investmentAllocationOverride.setInvestmentAllocation(this);

        SessionHelper.saveOrUpdate(investmentAllocationOverride, InvestmentAllocation.DATABASE);
    }

    /**
     * Removes a InvestmentAllocationOverride from the set of children
     * @param investmentAllocationOverride
     */
    public void removeInvestmentAllocationOverride(InvestmentAllocationOverride investmentAllocationOverride)
    {
        this.getInvestmentAllocationOverrides().remove(investmentAllocationOverride);

        investmentAllocationOverride.setInvestmentAllocation(null);

        SessionHelper.saveOrUpdate(investmentAllocationOverride, InvestmentAllocation.DATABASE);
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
     * Getter.
     * @return
     */
    public Long getInvestmentAllocationPK()
    {
        return SessionHelper.getPKValue(investmentAllocationVO.getInvestmentAllocationPK());
    }

    //-- long getInvestmentAllocationPK()

    /**
     * Setter.
     * @param investmentAllocationPK
     */
    public void setInvestmentAllocationPK(Long investmentAllocationPK)
    {
        investmentAllocationVO.setInvestmentAllocationPK(SessionHelper.getPKValue(investmentAllocationPK));
    }

    //-- void setInvestmentAllocationPK(long)

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (investmentAllocationVO == null)
        {
            investmentAllocationVO = new InvestmentAllocationVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }

        if (investmentAllocationOverrides == null)
        {
            investmentAllocationOverrides = new HashSet();
        }
    }

    public long getInvestmentAllocationPKForPrimary(String transactionType)
    {
        long investmentAllocationPK = 0;

        for (int j = 0; j < investmentAllocationVOs.length; j++)
        {
            String overrideStatus = investmentAllocationVOs[j].getOverrideStatus();

            if (overrideStatus.equalsIgnoreCase("P"))
            {
                investmentAllocationPK = investmentAllocationVOs[j].getInvestmentAllocationPK();

                break;
            }
        }

        //loanrepayment can use the first override when no primaries exist
        if (investmentAllocationPK == 0 && transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT))
        {
            if (investmentAllocationVOs.length > 0)
            {
                investmentAllocationPK = investmentAllocationVOs[0].getInvestmentAllocationPK();
            }
        }

        return investmentAllocationPK;
    }

    public BigDecimal getAllocationByPK(long investmentAllocationPK)
    {
        BigDecimal allocationPercent = new BigDecimal(0);

        for (int i = 0; i < investmentAllocationVOs.length; i++)
        {
            if (investmentAllocationVOs[i].getInvestmentAllocationPK() == investmentAllocationPK)
            {
                allocationPercent = investmentAllocationVOs[i].getAllocationPercent();

                break;
            }
        }

        return allocationPercent;
    }

    public long getPKForAllocationPercent(BigDecimal allocationPct)
    {
        EDITBigDecimal paramAlloc = new EDITBigDecimal(allocationPct);
        EDITBigDecimal iaAlloc = new EDITBigDecimal();

        long investmentAllocationPK = 0;

        for (int i = 0; i < investmentAllocationVOs.length; i++)
        {
            iaAlloc = new EDITBigDecimal(investmentAllocationVOs[i].getAllocationPercent());

            if (iaAlloc.isEQ(paramAlloc))
            {
                investmentAllocationPK = investmentAllocationVOs[i].getInvestmentAllocationPK();

                break;
            }
        }

        return investmentAllocationPK;
    }

    public long getPKForOverrideAllocationPercent(BigDecimal allocation)
    {
        EDITBigDecimal overrideAlloc = new EDITBigDecimal(allocation);
        EDITBigDecimal alloc = new EDITBigDecimal();

        long investmentAllocationPK = 0;

        for (int i = 0; i < investmentAllocationVOs.length; i++)
        {
            alloc = new EDITBigDecimal(investmentAllocationVOs[i].getAllocationPercent());

            if (alloc.isEQ(overrideAlloc) && investmentAllocationVOs[i].getOverrideStatus().equalsIgnoreCase("O"))
            {
                investmentAllocationPK = investmentAllocationVOs[i].getInvestmentAllocationPK();

                break;
            }
        }

        return investmentAllocationPK;
    }

    public long getPKForAllocationDollars(EDITBigDecimal allocationDollars)
    {
//        EDITBigDecimal paramAlloc = new EDITBigDecimal(allocationDollars);
        EDITBigDecimal iaAlloc = new EDITBigDecimal();

        long investmentAllocationPK = 0;

        for (int i = 0; i < investmentAllocationVOs.length; i++)
        {
            iaAlloc = new EDITBigDecimal(investmentAllocationVOs[i].getDollars());

            if (iaAlloc.isEQ(allocationDollars))
            {
                investmentAllocationPK = investmentAllocationVOs[i].getInvestmentAllocationPK();

                break;
            }
        }

        return investmentAllocationPK;
    }

    /**
     * @return
     * @see CRUDEntity#getPK()
     */
    public long getPK()
    {
        return investmentAllocationVO.getInvestmentAllocationPK();
    }

    public void saveForHibernate()
    {
        if (segment != null)
        {
            String segmentStatus = segment.getStatus();
            boolean saveChanges = segment.getSaveChangeHistory();

            if (segmentStatus.equalsIgnoreCase("Active") && saveChanges)
            {
                checkForNonFinancialChanges();
            }
        }

        //CONVERT TO HIBERNATE - 09/13/07
        hSave();
    }

    public void save()
    {
        if (segment != null)
        {
            String segmentStatus = segment.getStatus();
            boolean saveChanges = segment.getSaveChangeHistory();

            if (segmentStatus.equalsIgnoreCase("Active") && saveChanges)
            {
                checkForNonFinancialChanges();
            }
        }

        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);

        contract.dm.StorageManager sm = new contract.dm.StorageManager();
        investmentAllocationPK = sm.saveInvestmentAllocationVO(investmentAllocationVO);
    }

    public long getNewInvestmentAllocationPK()
    {
        return this.investmentAllocationPK;
    }

    /**
     * @throws Throwable
     * @see CRUDEntity#delete()
     */
    public void delete()
    {
        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * @return
     * @see CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return investmentAllocationVO;
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.investmentAllocationVO = (InvestmentAllocationVO) voObject;
    }

    /**
     * @return
     * @see CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    /**
     * @return
     * @see CRUDEntity#isNew()
     */
    public boolean isNew()
    {
        return crudEntityImpl.isNew(this);
    }

    private void checkForNonFinancialChanges()
    {
        ChangeProcessor changeProcessor = new ChangeProcessor();
        Change[] changes = changeProcessor.checkForChanges(investmentAllocationVO, investmentAllocationVO.getVoShouldBeDeleted(), ConnectionFactory.EDITSOLUTIONS_POOL, null);

        if (changes != null)
        {
            for (int i = 0; i < changes.length; i++)
            {
                if (changes[i].getStatus() == (Change.CHANGED))
                {
                    changeProcessor.processForChanges(changes[i], this, segment.getOperator(), segment.getPK());
                }
            }
        }
    }

    /**
     * Setter
     * @param allocationPercent
     */
    public void setAllocationPercent(EDITBigDecimal allocationPercent)
    {
        investmentAllocationVO.setAllocationPercent(SessionHelper.getEDITBigDecimal(allocationPercent));
    }

    /**
     * Setter
     * @param Dollars
     */
    public void setDollars(EDITBigDecimal dollars)
    {
        investmentAllocationVO.setDollars(SessionHelper.getEDITBigDecimal(dollars));
    }

    /**
     * Setter
     * @param Units
     */
    public void setUnits(EDITBigDecimal units)
    {
        investmentAllocationVO.setUnits(SessionHelper.getEDITBigDecimal(units));
    }

    /**
     * Setter
     * @param overrideStatus
     */
    public void setOverrideStatus(String overrideStatus)
    {
        investmentAllocationVO.setOverrideStatus(overrideStatus);
    }

    /**
      * Getter
      * @param Units
      */
    public EDITBigDecimal getAllocationPercent()
    {
        return SessionHelper.getEDITBigDecimal(investmentAllocationVO.getAllocationPercent());
    }

    /**
      * Getter
      * @param Units
      */
    public EDITBigDecimal getDollars()
    {
        return SessionHelper.getEDITBigDecimal(investmentAllocationVO.getDollars());
    }

    /**
      * Getter
      * @param Units
      */
    public EDITBigDecimal getUnits()
    {
        return SessionHelper.getEDITBigDecimal(investmentAllocationVO.getUnits());
    }

    /**
     * Getter
     * @param overrideStatus
     */
    public String getOverrideStatus()
    {
        return investmentAllocationVO.getOverrideStatus();
    }

    public Long getInvestmentFK()
    {
        return SessionHelper.getPKValue(investmentAllocationVO.getInvestmentFK());
    }

    public void setInvestmentFK(Long investmentFK)
    {
        investmentAllocationVO.setInvestmentFK(SessionHelper.getPKValue(investmentFK));
    }

    public void addInvestmentAllocationOverrideVO(InvestmentAllocationOverrideVO vInvestmentAllocationOverrideVO) throws IndexOutOfBoundsException
    {
        investmentAllocationVO.addInvestmentAllocationOverrideVO(vInvestmentAllocationOverrideVO);
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, InvestmentAllocation.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, InvestmentAllocation.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return InvestmentAllocation.DATABASE;
    }

	/**
     * Retrieve the InvestmentAllocation for the given investmentAllocationPK
     * @param investmentAllocationPK  - The primary key for the InvestmentAllocation that is to be retrieved.
     * @return
     */
    public static InvestmentAllocation findByPK(Long investmentAllocationPK)
    {
        return (InvestmentAllocation) SessionHelper.get(InvestmentAllocation.class, investmentAllocationPK, InvestmentAllocation.DATABASE);
    }

    public static InvestmentAllocationVO findByPK_UsingCRUD(long investmentAllocationPK)
    {
        InvestmentAllocationVO[] investmentAllocationVOs = DAOFactory.getInvestmentAllocationDAO().findByInvestmentAllocationPK(investmentAllocationPK, false, null);

        InvestmentAllocationVO investmentAllocationVO = null;

        if (investmentAllocationVOs != null)
        {
            investmentAllocationVO = investmentAllocationVOs[0];
        }

        return investmentAllocationVO;
    }
}
