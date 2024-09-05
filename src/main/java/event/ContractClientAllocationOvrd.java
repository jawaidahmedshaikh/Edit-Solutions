/*
 * User: dlataill
 * Date: Apr 19, 2004
 * Time: 3:20:57 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import contract.*;


public class ContractClientAllocationOvrd extends HibernateEntity
{
    private ContractClientAllocationOvrdVO contractClientAllocationOvrdVO;
    private ContractClientAllocationOvrd contractClientAllocationOvrd;
    private ContractClientAllocation contractClientAllocation;
    private ClientSetup clientSetup;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public ContractClientAllocationOvrd()
    {
        contractClientAllocationOvrdVO = new ContractClientAllocationOvrdVO();

        init();
    }



    public ContractClientAllocationOvrd(ContractClientAllocationOvrdVO contractClientAllocationOvrdVO)
    {
        this.contractClientAllocationOvrdVO = contractClientAllocationOvrdVO;
    }

  private final void init()
    {

    }

    public Long getClientSetupFK()
    {
        return SessionHelper.getPKValue(contractClientAllocationOvrdVO.getClientSetupFK());
    }
     //-- long getClientSetupFK() 

    public Long getContractClientAllocationFK()
    {
        return SessionHelper.getPKValue(contractClientAllocationOvrdVO.getContractClientAllocationFK());
    }
     //-- long getContractClientAllocationFK()

    public Long getContractClientAllocationOvrdPK()
    {
        return SessionHelper.getPKValue(contractClientAllocationOvrdVO.getContractClientAllocationOvrdPK());
    }
     //-- long getContractClientAllocationOvrdPK()

    public void setClientSetupFK(Long clientSetupFK)
    {
        contractClientAllocationOvrdVO.setClientSetupFK(SessionHelper.getPKValue(clientSetupFK));
    }
     //-- void setClientSetupFK(long) 

    public void setContractClientAllocationFK(Long contractClientAllocationFK)
    {
        contractClientAllocationOvrdVO.setContractClientAllocationFK(SessionHelper.getPKValue(contractClientAllocationFK));
    }
     //-- void setContractClientAllocationFK(long)

    public void setContractClientAllocationOvrdPK(Long contractClientAllocationOvrdPK)
    {
        contractClientAllocationOvrdVO.setContractClientAllocationOvrdPK(SessionHelper.getPKValue(contractClientAllocationOvrdPK));
    }
     //-- void setContractClientAllocationOvrdPK(long)

    public int delete() throws Exception
    {
        CRUD crud = null;

        int deleteCount = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            crud.deleteVOFromDB(ContractClientAllocationOvrdVO.class, contractClientAllocationOvrdVO.getContractClientAllocationOvrdPK());

            deleteCount = 1;
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw e;
        }
        finally
        {
            if (crud != null)
                crud.close();

            crud = null;
        }

        return deleteCount;
    }

    public void save() throws Exception
    {
        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            crud.createOrUpdateVOInDB(contractClientAllocationOvrdVO);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw e;
        }
        finally
        {
            if (crud != null)
                crud.close();

            crud = null;
        }
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

    /**
     * Setter
     * @param contractClientAllocation
     */
    public void setContractClientAllocation(ContractClientAllocation contractClientAllocation)
    {
        this.contractClientAllocation = contractClientAllocation;
    }

    /**
     * Getter
     * @return
     */
    public ContractClientAllocation getContractClientAllocation()
    {
        return this.contractClientAllocation;
    }

    /**
     * Setter
     * @param clientSetup
     */
    public void setClientSetup(ClientSetup clientSetup)
    {
        this.clientSetup = clientSetup;
    }

    /**
     * Getter
     * @return
     */
    public ClientSetup getClientSetup()
    {
        return this.clientSetup;
    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ContractClientAllocationOvrd.DATABASE);
    }

    public void hDelete()
    {
        SessionHelper.delete(this, ContractClientAllocationOvrd.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ContractClientAllocationOvrd.DATABASE;
    }

    public ContractClientAllocationOvrdVO getAsVO()
    {
        return contractClientAllocationOvrdVO;
    }
}
