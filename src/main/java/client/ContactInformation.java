/*
 * User: dlataille
 * Date: Jul 16, 2007
 * Time: 10:48:51 PM
 *
 * 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package client;

import edit.common.*;
import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import java.util.*;


public class ContactInformation extends HibernateEntity implements CRUDEntity
{
    private ContactInformationVO contactInformationVO;
    private CRUDEntityI crudEntityImpl;
    private ClientDetail clientDetail;

    public static final String CONTACTTYPE_PRIMARY = "Primary";
    public static final String CONTACTTYPE_HOME = "Home";
    public static final String CONTACTTYPE_WORK = "Work";
    public static final String CONTACTTYPE_MOBILE = "Mobile";
    public static final String CONTACTTYPE_FAX = "Fax";
    public static final String CONTACTTYPE_EMAIL = "Email";

    /**
     * Database used for ClientDetail
     */
    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;

    /************************************** Constructor Methods **************************************/
    /**
     * Instantiates a ClientDetail entity with a default ClientDetailVO.
     */
    public ContactInformation()
    {
        init();
    }

    /**
     * Instantiates a ContactInformation entity with a ContactInformationVO retrieved from persistence.
     *
     * @param contactInformationPK
     */
    public ContactInformation(long contactInformationPK)
    {
        init();

        crudEntityImpl.load(this, contactInformationPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Instantiates a ContactInformation entity with a supplied ContactInformationVO.
     *
     * @param contactInformationVO
     */
    public ContactInformation(ContactInformationVO contactInformationVO)
    {
        init();

        this.contactInformationVO = contactInformationVO;
    }

    /**
     * Getter
     *
     * @return ClientDetail
     */
    public ClientDetail getClientDetail()
    {
        return clientDetail;
    }

    /**
     * Setter
     *
     * @param clientDetail
     */
    public void setClientDetail(ClientDetail clientDetail)
    {
        this.clientDetail = clientDetail;
    }

    /************************************** Public Methods **************************************/
    /**
     * @return
     *
     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity()
    {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    /**
     * @throws Throwable
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws Throwable
    {
        try
        {
            crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
        }
        catch (Throwable e)
        {
            System.out.println(e);

            e.printStackTrace();
        }
    }

    /**
     * Getter.
     *
     * @return
     */
    public Long getContactInformationPK()
    {
        return SessionHelper.getPKValue(contactInformationVO.getContactInformationPK());
    }

    /**
     * Setter.
     *
     * @param contactInformationPK
     */
    public void setContactInformationPK(Long contactInformationPK)
    {
        this.contactInformationVO.setContactInformationPK(SessionHelper.getPKValue(contactInformationPK));
    }

    /**
     * Getter.
     *
     * @return
     */
    public Long getClientDetailFK()
    {
        return SessionHelper.getPKValue(contactInformationVO.getClientDetailFK());
    }

    /**
     * Setter.
     *
     * @param clientDetailFK
     */
    public void setClientDetailFK(Long clientDetailFK)
    {
        this.contactInformationVO.setClientDetailFK(SessionHelper.getPKValue(clientDetailFK));
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getContactTypeCT()
    {
        return contactInformationVO.getContactTypeCT();
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getPhoneEmail()
    {
        return contactInformationVO.getPhoneEmail();
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getName()
    {
        return contactInformationVO.getName();
    }

    /**
     * @return
     *
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return contactInformationVO;
    }

    /**
     * @return
     *
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
        try
        {
            crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
        }
        catch (Throwable t)
        {
            System.out.println(t);

            t.printStackTrace();
        }
    }

    /**
     * Setter.
     */
    public void setContactTypeCT(String contactTypeCT)
    {
        contactInformationVO.setContactTypeCT(contactTypeCT);
    }

    /**
     * Setter.
     */
    public void setPhoneEmail(String phoneEmail)
    {
        contactInformationVO.setPhoneEmail(phoneEmail);
    }

    /**
     * Setter.
     */
    public void setName(String name)
    {
        contactInformationVO.setName(name);
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.contactInformationVO = (ContactInformationVO) voObject;
    }

    /************************************** Private Methods **************************************/
    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (contactInformationVO == null)
        {
            contactInformationVO = new ContactInformationVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * Finder.
     *
     * @param contactInformationPK
     *
     * @return
     */
    public static final ContactInformation findByPK(Long contactInformationPK)
    {
        return (ContactInformation) SessionHelper.get(ContactInformation.class, contactInformationPK, ContactInformation.DATABASE);
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ContactInformation.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, ContactInformation.DATABASE);
    }

    public void onCreate()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @return
     *
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return contactInformationVO.getContactInformationPK();
    }

    /**
     * Finder.
     * @param clientDetailFK
     * @return
     */
    public static ContactInformation[] findBy_ClientDetailFK(Long clientDetailFK)
    {
        String hql = " from ContactInformation contactInformation" +
                    " where contactInformation.ClientDetailFK = :clientDetailFK";

        EDITMap params = new EDITMap("clientDetailFK", clientDetailFK);

        List<ContactInformation> results = SessionHelper.executeHQL(hql, params, ContactInformation.DATABASE);

        return (ContactInformation[]) results.toArray(new ContactInformation[results.size()]);
    }

    /**
     * Finder by ClientDetail and ContactTypeCT.
     * @param clientDetail
     * @param contactTypeCT
     * @return
     */
    public static final ContactInformation findByClientDetail_ContactTypeCT(ClientDetail clientDetail, String contactTypeCT)
    {
        ContactInformation contactInformation = null;

        String hql = "select ci from ContactInformation ci where ci.ClientDetail = :clientDetail " +
                     " and ci.ContactTypeCT like :contactTypeCT";

        Map params = new HashMap();

        params.put("clientDetail", clientDetail);

        params.put("contactTypeCT", contactTypeCT);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        if (!results.isEmpty())
        {
            contactInformation = (ContactInformation) results.get(0);
        }

        return contactInformation;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ContactInformation.DATABASE;
    }
}
