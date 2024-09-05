/*
 * User: gfrosti
 * Date: Nov 23, 2004
 * Time: 11:42:15 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package reinsurance;

import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.*;
import edit.common.vo.VOObject;
import edit.common.vo.ReinsurerVO;
import edit.common.exceptions.*;

import java.util.HashSet;

import reinsurance.dm.dao.*;

import java.util.Set;



public class Reinsurer extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;

    private ReinsurerVO reinsurerVO;

    private Set<Treaty> treaties = new HashSet<Treaty>();

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a Reinsurer entity with a default ReinsurerVO.
     */
    public Reinsurer()
    {
        init();
    }

    /**
     * Instantiates a Reinsurer entity with a ReinsurerVO retrieved from persistence.
     * @param reinsurerPK
     */
    public Reinsurer(long reinsurerPK)
    {
        init();

        crudEntityImpl.load(this, reinsurerPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Instantiates a Reinsurer entity with a supplied ReinsurerVO.
     * @param reinsurerVO
     */
    public Reinsurer(ReinsurerVO reinsurerVO)
    {
        init();

        this.reinsurerVO = reinsurerVO;
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
     * @throws Throwable
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() throws Throwable
    {
        crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return reinsurerVO.getReinsurerPK();
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO()
    {
        return reinsurerVO;
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
    public void save() throws EDITReinsuranceException
    {
        checkForEdits();

        checkForDuplicates();

        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.reinsurerVO = (ReinsurerVO) voObject;
    }

    /**
     * Getter.
     * @return
     */
    public Long getClientDetailFK()
    {
        return SessionHelper.getPKValue(reinsurerVO.getClientDetailFK());
    } //-- long getClientDetailFK()

    /**
     * Getter.
     * @return
     */
    public String getReinsurerNumber()
    {
        return reinsurerVO.getReinsurerNumber();
    } //-- java.lang.String getReinsurerNumber()

    /**
     * Getter.
     * @return
     */
    public Long getReinsurerPK()
    {
        return SessionHelper.getPKValue(reinsurerVO.getReinsurerPK());
    } //-- long getReinsurerPK()

    /**
     * Setter.
     * @param clientDetailFK
     */
    public void setClientDetailFK(Long clientDetailFK)
    {
        reinsurerVO.setClientDetailFK(SessionHelper.getPKValue(clientDetailFK));
    } //-- void setClientDetailFK(long)

    /**
     * Setter.
     * @param reinsurerNumber
     */
    public void setReinsurerNumber(String reinsurerNumber)
    {
        reinsurerVO.setReinsurerNumber(reinsurerNumber);
    } //-- void setReinsurerNumber(java.lang.String)

    /**
     * Setter.
     * @param reinsurerPK
     */
    public void setReinsurerPK(Long reinsurerPK)
    {
        reinsurerVO.setReinsurerPK(SessionHelper.getPKValue(reinsurerPK));
    } //-- void setReinsurerPK(long)

    /**
     * Getter.
     * @return
     */
    public Set<Treaty> getTreaties()
    {
        return treaties;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Reinsurer.DATABASE;
    }

    /**
     * Setter.
     * @param treaties
     */
    public void setTreaties(Set<Treaty> treaties)
    {
        this.treaties = treaties;
    }

    /**
     * Two Reinsurers are not allowed to have duplicate Reinsurer Numbers.
     */
    private void checkForDuplicates() throws EDITReinsuranceException
    {
        if (isNew())
        {
            Reinsurer reinsurer = Reinsurer.findBy_ReinsurerNumber(reinsurerVO.getReinsurerNumber());

            if (reinsurer != null)
            {
                throw new EDITReinsuranceException("Reinsurance Number Already Exists [" + reinsurerVO.getReinsurerNumber() + "]");
            }
        }
    }

    /**
     * A Reinsurer must have a ReinsurerNumber and a ClientDetailFK to be valid.
     */
    private void checkForEdits() throws EDITReinsuranceException
    {
        boolean validEdits = true;

        if (reinsurerVO.getReinsurerNumber() == null)
        {
            validEdits = false;
        }
        if (reinsurerVO.getClientDetailFK() == 0)
        {
            validEdits = false;
        }

        if (!validEdits)
        {
            throw new EDITReinsuranceException("A Reinsurer Number And Associated Client Required");
        }
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (reinsurerVO == null)
        {
            reinsurerVO = new ReinsurerVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * Finder.
     * @param reinsurerNumber
     * @return
     */
    public static Reinsurer findBy_ReinsurerNumber(String reinsurerNumber)
    {
        Reinsurer reinsurer = null;

        ReinsurerVO[] reinsurerVOs = new ReinsurerDAO().findBy_ReinsurerNumber(reinsurerNumber);

        if (reinsurerVOs != null)
        {
            reinsurer = new Reinsurer(reinsurerVOs[0]);
        }

        return reinsurer;
    }

    /**
     * Finder.
     * @param partialCorporateName
     * @return
     */
    public static final Reinsurer[] findBy_PartialCorporateName(String partialCorporateName)
    {
        return (Reinsurer[]) CRUDEntityImpl.mapVOToEntity(new ReinsurerDAO().findBy_PartialCorporateName(partialCorporateName), Reinsurer.class);
    }

    /**
     * Finder.
     * @param taxIdentification
     * @return
     */
    public static final Reinsurer findBy_TaxIdentification(String taxIdentification)
    {
        Reinsurer reinsurer = null;

        ReinsurerVO[] reinsurerVOs = new ReinsurerDAO().findBy_TaxIdentification(taxIdentification);

        if (reinsurerVOs != null)
        {
            reinsurer = new Reinsurer(reinsurerVOs[0]);
        }
        return reinsurer;
    }

    /**
     * Finder.
     * @param reinsuranceHistoryPK
     * @return
     */
    public static final Reinsurer findBy_ReinsuranceHistoryPK(long reinsuranceHistoryPK)
    {
        Reinsurer reinsurer = null;

        ReinsurerVO[] reinsurerVOs = new ReinsurerDAO().findBy_ReinsuranceHistoryPK(reinsuranceHistoryPK);

        if (reinsurerVOs != null)
        {
            reinsurer = new Reinsurer(reinsurerVOs[0]);
        }
        return reinsurer;
    }
    
    public static final Reinsurer findBy_ReinsurerPK(long reinsurerPK)
    {
        Reinsurer reinsurer = null;

        ReinsurerVO[] reinsurerVOs = new ReinsurerDAO().findBy_PK(reinsurerPK);

        if (reinsurerVOs != null)
        {
            reinsurer = new Reinsurer(reinsurerVOs[0]);
        }
        return reinsurer;
	}

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Reinsurer.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, Reinsurer.DATABASE);
    }

  /**
   * Adder.
   * @param treaty
   */
  public void add(Treaty treaty)
  {
    getTreaties().add(treaty);
    
    treaty.setReinsurer(this);
  }
}


