/*
 * User: gfrosti
 * Date: Dec 1, 2004
 * Time: 2:28:11 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import contract.ContractClient;

import edit.common.vo.ClientSetupVO;
import edit.common.vo.VOObject;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import event.dm.dao.ClientSetupDAO;

import java.util.HashSet;
import java.util.Set;

import reinsurance.Treaty;

import role.ClientRole;


public class ClientSetup extends HibernateEntity implements CRUDEntity
{
    public static final int TYPE_CONTRACT_CLIENT = 0;
    public static final int TYPE_CLIENT_ROLE = 1;
    public static final int TYPE_TREATY = 2;
    private CRUDEntityImpl crudEntityImpl;
    private ClientSetupVO clientSetupVO;
    private ContractSetup contractSetup;
    private Set<EDITTrx> editTrxs;
    private ClientRole clientRole;
    private ContractClient contractClient;
    private EDITTrx editTrx;
    private Set<WithholdingOverride> withholdingOverrides;
    private WithholdingOverride withholdingOverride;
    private ContractClientAllocationOvrd contractClientAllocationOvrd;
    private Set<ContractClientAllocationOvrd> contractClientAllocationOvrds;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a ClientSetup entity with a default ClientSetupVO.
     */
    public ClientSetup()
    {
        init();
    }

    /**
     * Instantiates a ClientSetup entity with a ClientSetupVO retrieved from persistence.
     * @param clientSetupPK
     */
    public ClientSetup(long clientSetupPK)
    {
        init();

        crudEntityImpl.load(this, clientSetupPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Instantiates a ClientSetup entity with a supplied ClientSetupVO.
     * @param clientSetupVO
     */
    public ClientSetup(ClientSetupVO clientSetupVO)
    {
        init();

        this.clientSetupVO = clientSetupVO;
    }

    public ContractClient getContractClient()
    {
        return contractClient;
    }

    public void setContractClient(ContractClient contractClient)
    {
        this.contractClient = contractClient;
    }

    public ClientRole getClientRole()
    {
        return clientRole;
    }

    public void setClientRole(ClientRole clientRole)
    {
        this.clientRole = clientRole;
    }

    /**
     * Getter.
     * @return
     */
    public Set<EDITTrx> getEDITTrxs()
    {
        return editTrxs;
    }

    /**
     * Setter.
     * @param editTrxs
     */
    public void setEDITTrxs(Set<EDITTrx> editTrxs)
    {
        this.editTrxs = editTrxs;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (clientSetupVO == null)
        {
            clientSetupVO = new ClientSetupVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }

        editTrxs = new HashSet<EDITTrx>();

        withholdingOverrides = new HashSet<WithholdingOverride>();
        contractClientAllocationOvrds = new HashSet<ContractClientAllocationOvrd>();
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
        return clientSetupVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return clientSetupVO.getClientSetupPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.clientSetupVO = (ClientSetupVO) voObject;
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

    /**
     * CRUD Getter.
     * @return
     */
    public ContractSetup get_ContractSetup()
    {
        return new ContractSetup(clientSetupVO.getContractSetupFK());
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
     * Getter.
     * @return
     */
    public Treaty getTreaty()
    {
        return new Treaty(clientSetupVO.getTreatyFK());
    }

    /**
     * Finder.
     * @param treatyPK
     * @return
     */
    public static final ClientSetup[] findBy_TreatyPK(long treatyPK)
    {
        return (ClientSetup[]) CRUDEntityImpl.mapVOToEntity(new ClientSetupDAO().findBy_TreatyPK(treatyPK), ClientSetup.class);
    }

    /**
     * Getter.
     * @return
     */
    public Long getClientRoleFK()
    {
        return SessionHelper.getPKValue(clientSetupVO.getClientRoleFK());
    }

    //-- long getClientRoleFK() 

    /**
     * Getter.
     * @return
     */
    public Long getClientSetupPK()
    {
        return SessionHelper.getPKValue(clientSetupVO.getClientSetupPK());
    }

    //-- long getClientSetupPK() 

    /**
     * Getter.
     * @return
     */
    public Long getContractClientFK()
    {
        return SessionHelper.getPKValue(clientSetupVO.getContractClientFK());
    }

    //-- long getContractClientFK() 

    /**
     * Getter.
     * @return
     */
    public Long getContractSetupFK()
    {
        return SessionHelper.getPKValue(clientSetupVO.getContractSetupFK());
    }

    //-- long getContractSetupFK() 

    /**
     * Getter.
     * @return
     */
    public Long getTreatyFK()
    {
        return SessionHelper.getPKValue(clientSetupVO.getTreatyFK());
    }

    //-- long getTreatyFK() 

    /**
     * Setter.
     * @param clientRoleFK
     */
    public void setClientRoleFK(Long clientRoleFK)
    {
        clientSetupVO.setClientRoleFK(SessionHelper.getPKValue(clientRoleFK));
    }

    //-- void setClientRoleFK(long) 

    /**
     * Setter.
     * @param clientSetupPK
     */
    public void setClientSetupPK(Long clientSetupPK)
    {
        clientSetupVO.setClientSetupPK(SessionHelper.getPKValue(clientSetupPK));
    }

    //-- void setClientSetupPK(long) 

    /**
     * Setter.
     * @param contractClientFK
     */
    public void setContractClientFK(Long contractClientFK)
    {
        clientSetupVO.setContractClientFK(SessionHelper.getPKValue(contractClientFK));
    }

    //-- void setContractClientFK(long) 

    /**
     * Setter.
     * @param contractSetupFK
     */
    public void setContractSetupFK(Long contractSetupFK)
    {
        clientSetupVO.setContractSetupFK(SessionHelper.getPKValue(contractSetupFK));
    }

    //-- void setContractSetupFK(long) 

    /**
     * Setter.
     * @param treatyFK
     */
    public void setTreatyFK(Long treatyFK)
    {
        clientSetupVO.setTreatyFK(SessionHelper.getPKValue(treatyFK));
    }

    /**
     * Hibernate getter.
     * @return
     */
    public ContractSetup getContractSetup()
    {
        return contractSetup;
    }


    /**
     * Setter
     * @param editTrx
     */
    public void setEDITTrx(EDITTrx editTrx)
    {
        this.editTrx = editTrx;
    }



    /**
     * Get a single ClientSetup
     * @return
     */
    public EDITTrx getEDITTrx()
    {
        EDITTrx editTrx = getEDITTrxs().isEmpty() ? null : (EDITTrx) getEDITTrxs().iterator().next();

        return editTrx;
    }

    /**
     * Getter.
     * @return
     */
    public WithholdingOverride getWithholdingOverride()
    {

        WithholdingOverride withholdingOverride =getWithholdingOverrides().isEmpty() ? null : (WithholdingOverride) getWithholdingOverrides().iterator().next();

        return withholdingOverride;
    }

    /**
     * Setter.
     * @param withholdingOverride
     */
    public void setWithholdingOverride(WithholdingOverride withholdingOverride)
    {
        this.withholdingOverride = withholdingOverride;
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
    *
    * @param contractClientAllocationOvrd
    */
    public void setContractClientAllocationOvrd(ContractClientAllocationOvrd contractClientAllocationOvrd)
    {
        this.contractClientAllocationOvrd = contractClientAllocationOvrd;
    }

    /**
     * Setter.
     * @param withholdingOverrides
     */
    public void setWithholdingOverrides(Set<WithholdingOverride> withholdingOverrides)
    {
        this.withholdingOverrides = withholdingOverrides;
    }

    /**
     * Getter.
     * @return
     */
    public Set<WithholdingOverride> getWithholdingOverrides()
    {
        return withholdingOverrides;
    }

    /**
     * Setter.
     * @param contractClientAllocationOvrds
     */
    public void setContractClientAllocationOvrds(Set<ContractClientAllocationOvrd> contractClientAllocationOvrds)
    {
        this.contractClientAllocationOvrds = contractClientAllocationOvrds;
    }

    /**
     * Getter.
     * @return
     */
    public Set<ContractClientAllocationOvrd> getContractClientAllocationOvrds()
    {
        return contractClientAllocationOvrds;
    }

    /**
     * Hibernate finder.
     * @param clientSetupPK
     * @return
     */
    public static final ClientSetup findByPK(Long clientSetupPK)
    {
        ClientSetup clientSetup = (ClientSetup) SessionHelper.get(ClientSetup.class, clientSetupPK, ClientSetup.DATABASE);

        return clientSetup;
    }



    /**
     * Adds a EDITTrx to the set of children
     * @param editTrx
     */
    public void addEDITTrx(EDITTrx editTrx)
    {
        this.getEDITTrxs().add(editTrx);

        editTrx.setClientSetup(this);

        SessionHelper.saveOrUpdate(editTrx, ClientSetup.DATABASE);
    }

    /**
     * Removes a EDITTrx from the set of children
     * @param editTrx
     */
    public void removeEDITTrx(EDITTrx editTrx)
    {
        this.getEDITTrxs().remove(editTrx);

        editTrx.setClientSetup(null);

        SessionHelper.saveOrUpdate(this, ClientSetup.DATABASE);
    }

    /**
     * Adds a ContractClientAllocationOvrd to the set of children
     * @param contractClientAllocationOvrd
     */
    public void addContractClientAllocationOvrd(ContractClientAllocationOvrd contractClientAllocationOvrd)
    {
        this.getContractClientAllocationOvrds().add(contractClientAllocationOvrd);

        contractClientAllocationOvrd.setClientSetup(this);

        SessionHelper.saveOrUpdate(contractClientAllocationOvrd, ClientSetup.DATABASE);
    }

    /**
     * Removes a ContractClientAllocationOvrd from the set of children
     * @param contractClientAllocationOvrd
     */
    public void removeContractClientAllocationOvrd(ContractClientAllocationOvrd contractClientAllocationOvrd)
    {
        this.getContractClientAllocationOvrds().remove(contractClientAllocationOvrd);

        contractClientAllocationOvrd.setClientSetup(null);

        SessionHelper.saveOrUpdate(contractClientAllocationOvrd, ClientSetup.DATABASE);
    }

    /**
     * Adds a WithholdingOverride to the set of children
     * @param withholdingOverride
     */
    public void addWithholdingOverride(WithholdingOverride withholdingOverride)
    {
        this.getWithholdingOverrides().add(withholdingOverride);

        withholdingOverride.setClientSetup(this);

        SessionHelper.saveOrUpdate(withholdingOverride, ClientSetup.DATABASE);
    }

    /**
     * Removes a WithholdingOverride from the set of children
     * @param withholdingOverride
     */
    public void removeWithholdingOverride(WithholdingOverride withholdingOverride)
    {
        this.getWithholdingOverrides().remove(withholdingOverride);

        withholdingOverride.setClientSetup(null);

        SessionHelper.saveOrUpdate(withholdingOverride, ClientSetup.DATABASE);
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ClientSetup.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, ClientSetup.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ClientSetup.DATABASE;
    }

    public static ClientSetupVO findByPK(long clientSetupPK)
    {
        ClientSetupVO[] clientSetupVOs = new ClientSetupDAO().findByClientSetupPK(clientSetupPK);
        ClientSetupVO clientSetupVO = null;

        if (clientSetupVOs != null)
        {
            clientSetupVO =  (clientSetupVOs[0]);
        }

        return clientSetupVO;
    }

    /**
     * Get the clientSetup for the EDITTrxPK.
     * @param editTrxPK
     * @return
     */
    public static ClientSetup findByEDITTrxFK_UsingCRUD(long editTrxPK)  throws Exception
    {
        ClientSetup clientSetup = null;

        ClientSetupVO[] clientSetupVOs = new ClientSetupDAO().findByEDITTrxPK(editTrxPK);

        if (clientSetupVOs != null)
        {
            clientSetup = new ClientSetup(clientSetupVOs[0]);
        }

        return clientSetup;
    }
}
