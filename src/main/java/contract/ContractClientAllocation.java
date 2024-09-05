/*
 * User: cgleason
 * Date: Jan 19, 2005
 * Time: 4:55:12 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package contract;

import contract.dm.dao.*;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import event.*;

import java.util.*;
import event.*;


import org.hibernate.Session;


public class ContractClientAllocation extends HibernateEntity implements CRUDEntity, SEGElement
{
    public static final String OVERRIDESTATUS_PRIMARY = "P";
    public static final String OVERRIDESTATUS_OVERRIDE = "O";
    private CRUDEntityImpl crudEntityImpl;
    private ContractClientAllocationVO contractClientAllocationVO;
    private ContractClient contractClient;
	private ContractClientAllocationOvrd contractClientAllocationOvrd;
    private Set contractClientAllocationOvrds;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;



    /**
     * Instantiates a ContractClientAllocation entity with a default ContractClientAllocationVO.
     */
    public ContractClientAllocation()
    {
        init();
    }

    /**
     * Instantiates a ContractClientAllocation entity with a supplied ContractClientAllocationVO.
     *
     * @param contractClientAllocationVO
     */
    public ContractClientAllocation(ContractClientAllocationVO contractClientAllocationVO)
    {
        init();

        this.contractClientAllocationVO = contractClientAllocationVO;
    }



    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (contractClientAllocationVO == null)
        {
            contractClientAllocationVO = new ContractClientAllocationVO();
        }

        if (contractClientAllocationOvrds == null)
        {
            contractClientAllocationOvrds = new HashSet();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
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
        return contractClientAllocationVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return contractClientAllocationVO.getContractClientAllocationPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.contractClientAllocationVO = (ContractClientAllocationVO) voObject;
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
     * @return
     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    public Long getContractClientFK()
    {
        return SessionHelper.getPKValue(contractClientAllocationVO.getContractClientFK());
    }

    public void setContractClientFK(Long contractClientFK)
    {
        contractClientAllocationVO.setContractClientFK(SessionHelper.getPKValue(contractClientFK));
    }

    /**
     * Getter
     * @return  set of contractClientAllocationOvrds
     */
    public Set getContractClientAllocationOvrds()
    {
        return contractClientAllocationOvrds;
    }

    /**
     * Setter
     * @param contractClientAllocationOvrds      set of contractClientAllocationOvrds
     */
    public void setContractClientAllocationOvrds(Set contractClientAllocationOvrds)
    {
        this.contractClientAllocationOvrds = contractClientAllocationOvrds;
    }

    /**
     * Adds a ContractClientAllocationOverride to the set of children
     * @param contractClientAllocationOverride
     */
    public void addContractClientAllocationOverride(ContractClientAllocationOvrd contractClientAllocationOvrd)
    {
        this.getContractClientAllocationOvrds().add(contractClientAllocationOvrd);

        contractClientAllocationOvrd.setContractClientAllocation(this);

        SessionHelper.saveOrUpdate(contractClientAllocationOvrd, ContractClientAllocation.DATABASE);
    }

    /**
     * Removes a ContractClientAllocationOverride from the set of children
     * @param contractClientAllocationOverride
     */
    public void removeContractClientAllocationOverride(ContractClientAllocationOvrd contractClientAllocationOvrd)
    {
        this.getContractClientAllocationOvrds().remove(contractClientAllocationOvrd);

        contractClientAllocationOvrd.setContractClientAllocation(null);

        SessionHelper.saveOrUpdate(contractClientAllocationOvrd, ContractClientAllocation.DATABASE);
    }


    /**
     * Setter.
     * @param splitEqual
     */
    public void setSplitEqual(String splitEqual)
    {
        contractClientAllocationVO.setSplitEqual(splitEqual);
    }

    /**
     * Getter.
     * @return
     */
    public String getSplitEqual()
    {
        return contractClientAllocationVO.getSplitEqual();
    }

    /**
     * Setter.
     * @param contractClientAllocationPK
     */
    public void setContractClientAllocationPK(Long contractClientAllocationPK)
    {
        contractClientAllocationVO.setContractClientAllocationPK(SessionHelper.getPKValue(contractClientAllocationPK));
    }

    /**
     * Getter.
     * @return
     */
    public Long getContractClientAllocationPK()
    {
        return SessionHelper.getPKValue(contractClientAllocationVO.getContractClientAllocationPK());
    }

    /**
     * Setter.
     * @param allocationPercent
     */
    public void setAllocationPercent(EDITBigDecimal allocationPercent)
    {
        contractClientAllocationVO.setAllocationPercent(SessionHelper.getEDITBigDecimal(allocationPercent));
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAllocationPercent()
    {
        return SessionHelper.getEDITBigDecimal(contractClientAllocationVO.getAllocationPercent());
    }

    /**
     * Setter.
     * @param overrideStatus
     */
    public void setOverrideStatus(String overrideStatus)
    {
        contractClientAllocationVO.setOverrideStatus(overrideStatus);
    }

    /**
     * Getter.
     * @return
     */
    public String getOverrideStatus()
    {
        return contractClientAllocationVO.getOverrideStatus();
    }

    /**
     * Setter.
     * @param allocationDollars
     */
    public void setAllocationDollars(EDITBigDecimal allocationDollars)
    {
        contractClientAllocationVO.setAllocationDollars(SessionHelper.getEDITBigDecimal(allocationDollars));
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAllocationDollars()
    {
        return SessionHelper.getEDITBigDecimal(contractClientAllocationVO.getAllocationDollars());
    }

    /**
     * Setter.
     * @param contractClient
     */
    public void setContractClient(ContractClient contractClient)
    {
        this.contractClient = contractClient;
    }

    /**
     * Getter.
     * @return
     */
    public ContractClient getContractClient()
    {
        return this.contractClient;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ContractClientAllocation.DATABASE;
    }

    /**
     * Finder.
     *
     * @param contractClientAllocationPK
     */
    public static final ContractClientAllocation findByPK(long contractClientAllocationPK)
    {
        ContractClientAllocation contractClientAllocation = null;

        ContractClientAllocationVO[] contractClientAllocationVOs = new ContractClientAllocationDAO().findByPK(contractClientAllocationPK);

        if (contractClientAllocationVOs != null)
        {
            contractClientAllocation = new ContractClientAllocation(contractClientAllocationVOs[0]);
        }

        return contractClientAllocation;
    }

    /**
     * Finder.
     * @param contractClientPK
     * @param overrideStatus
     * @return
     */
    public static ContractClientAllocation findBy_ContractClientPK_OverrideStatus(long contractClientPK, String overrideStatus)
    {
        ContractClientAllocation contractClientAllocation = null;

        ContractClientAllocationVO[] contractClientAllocationVOs = new ContractClientAllocationDAO().findByContractClientPKAndOverrideStatus(contractClientPK, "P", false, null);

        if (contractClientAllocationVOs != null)
        {
            contractClientAllocation = new ContractClientAllocation(contractClientAllocationVOs[0]);
        }

        return contractClientAllocation;
    }
    
    /**
     * Finder by PK. Gets the entity using seperate session.
     * @param contractClientAllocationPK
     * @return ContractClientAllocation entity.
     */
    public static final ContractClientAllocation findSeperateByPK(Long contractClientAllocationPK)
    {
        ContractClientAllocation contractClientAllocation = null;

        String hql = " select contractClientAllocation " +
                     " from ContractClientAllocation contractClientAllocation " +
                     " where contractClientAllocation.ContractClientAllocationPK = :contractClientAllocationPK";

        EDITMap params = new EDITMap("contractClientAllocationPK", contractClientAllocationPK);

        Session session = null;   

        try
        {
          session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

          List<ContractClientAllocation> results = SessionHelper.executeHQL(session, hql, params, 0);

          if (!results.isEmpty())
          {
            contractClientAllocation = results.get(0);
          }        

        }
        finally
        {
          if (session != null) session.close();
        }

        return contractClientAllocation;
    }

    /**
     * Getter.
     * @return
     */
    public ContractClientAllocationOvrd getContractClientAllocationOvrd()
    {
        return contractClientAllocationOvrd;
    }

    /**
     * Setter.
     * @param contractClientAllocationOvrd
     */
    public void setContractClientAllocationOvrd(ContractClientAllocationOvrd contractClientAllocationOvrd)
    {
        this.contractClientAllocationOvrd = contractClientAllocationOvrd;
    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ContractClientAllocation.DATABASE);
    }

    public void hDelete()
    {
        SessionHelper.delete(this, ContractClientAllocation.DATABASE);
    }

    /**
     * Finder by PK.
     * @param contractClientAllocationPK
     * @return
     */
    public static ContractClientAllocation findByPK(Long contractClientAllocationPK)
    {
        return (ContractClientAllocation) SessionHelper.get(ContractClientAllocation.class, contractClientAllocationPK, ContractClientAllocation.DATABASE);
    }

    /**
     * Orginally in ContractClientAllocationDAO.findByContractClientPKAndOverrideStatus
     * @param contractClientPK
     * @param overrideStatus
     * @return
     */
    public static ContractClientAllocation findBy_ContractClientPK_OverrideStatus(Long contractClientPK, String overrideStatus)
    {
        ContractClientAllocation contractClientAllocation = null;

        String hql = "select contractClientAllocation from ContractClientAllocation contractClientAllocation " +
                     " where contractClientAllocation.ContractClientFK = :contractClientPK" +
                     " and contractClientAllocation.OverrideStatus = :overrideStatus";

        Map params = new HashMap();
        params.put("contractClientPK", contractClientPK);
        params.put("overrideStatus", overrideStatus);

        List results = SessionHelper.executeHQL(hql, params, ContractClientAllocation.DATABASE);

        if (!results.isEmpty())
        {
            contractClientAllocation = (ContractClientAllocation) results.get(0);
        }

        return contractClientAllocation;
    }

    /**
     * Convenience function to determine if 1, and only 1, ContractClientAllocation field is set or not.
     * The 3 fields are splitEqual, percent, and dollars.  Only one of these fields can be set on a
     * ContractClientAllocation.  And 1 must be set (can't have zero fields set).
     *
     * @true if 1 field is set, false otherwise
     */
    public static boolean hasOneAllocationFieldSet(boolean splitEqualOn, boolean percentOn, boolean dollarsOn)
    {
        //	Simplify the varying combination of ifs by setting ints to 1 if the
        //	boolean is true and 0 if false
        int splitEqualInt = splitEqualOn ? 1 : 0;
        int percentInt = percentOn ? 1 : 0;
        int dollarsInt = dollarsOn ? 1 : 0;

        //	Total the ints to get the number of fields that are turned on
        int totalNumberOfFieldsTurnedOn = splitEqualInt + percentInt + dollarsInt;

        //	If more than one field is turned on, then it does not have the same field set across all ContractClients
        if (totalNumberOfFieldsTurnedOn == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
