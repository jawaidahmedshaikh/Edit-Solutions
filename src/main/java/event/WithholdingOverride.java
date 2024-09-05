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

import event.dm.dao.*;

import contract.*;



public class WithholdingOverride extends HibernateEntity
{
    private WithholdingOverrideVO withholdingOverrideVO;
    private Withholding withholding;
    private ClientSetup clientSetup;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public WithholdingOverride()
    {
        withholdingOverrideVO = new WithholdingOverrideVO();

        init();
    }

    public WithholdingOverride(long withholdingOverridePK) throws Exception
    {
        WithholdingOverrideVO[] withholdingOverrides = DAOFactory.getWithholdingOverrideDAO().findByWithholdingOverridePK(withholdingOverridePK);

        if (withholdingOverrides != null)
        {
            withholdingOverrideVO = withholdingOverrides[0];
        }
    }

    public WithholdingOverride(WithholdingOverrideVO withholdingOverrideVO)
    {
        this.withholdingOverrideVO = withholdingOverrideVO;
    }

  private final void init()
    {

    }

    public Long getClientSetupFK()
    {
        return SessionHelper.getPKValue(withholdingOverrideVO.getClientSetupFK());
    }
     //-- long getClientSetupFK() 

    public Long getWithholdingFK()
    {
        return SessionHelper.getPKValue(withholdingOverrideVO.getWithholdingFK());
    }
     //-- long getWithholdingFK() 

    public Long getWithholdingOverridePK()
    {
        return SessionHelper.getPKValue(withholdingOverrideVO.getWithholdingOverridePK());
    }
     //-- long getWithholdingOverridePK() 

    public void setClientSetupFK(Long clientSetupFK)
    {
        withholdingOverrideVO.setClientSetupFK(SessionHelper.getPKValue(clientSetupFK));
    }
     //-- void setClientSetupFK(long) 

    public void setWithholdingFK(Long withholdingFK)
    {
        withholdingOverrideVO.setWithholdingFK(SessionHelper.getPKValue(withholdingFK));
    }
     //-- void setWithholdingFK(long) 

    public void setWithholdingOverridePK(Long withholdingOverridePK)
    {
        withholdingOverrideVO.setWithholdingOverridePK(SessionHelper.getPKValue(withholdingOverridePK));
    }
     //-- void setWithholdingOverridePK(long) 

    public int delete() throws Exception
    {
        CRUD crud = null;

        int deleteCount = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            crud.deleteVOFromDB(WithholdingOverrideVO.class, withholdingOverrideVO.getWithholdingOverridePK());

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

    public void save() throws Exception
    {
        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            crud.createOrUpdateVOInDB(withholdingOverrideVO);
        }
        catch (Exception e)
        {
            if (crud != null)
            {
                crud.close();
            }

            crud = null;
        }
    }

    /**
     * Setter
     * @param withholding
     */
    public void setWithholding(Withholding withholding)
    {
        this.withholding = withholding;
    }

    /**
     * Getter
     * @return
     */
    public ClientSetup getClientSetup()
    {
        return this.clientSetup;
    }

    /**
     * Getter
     * @return
     */
    public Withholding getWithholding()
    {
        return this.withholding;
    }
    /**
     * Setter
     * @param clientSetup
     */
    public void setClientSetup(ClientSetup clientSetup)
    {
        this.clientSetup = clientSetup;
    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, WithholdingOverride.DATABASE);
    }

    public void hDelete()
    {
        SessionHelper.delete(this, WithholdingOverride.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return WithholdingOverride.DATABASE;
    }

    public WithholdingOverrideVO getAsVO()
    {
        return withholdingOverrideVO;
    }
}