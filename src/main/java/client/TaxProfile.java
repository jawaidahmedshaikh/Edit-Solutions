/*
 * User: cgleason
 * Date: Jan 17, 2005
 * Time: 4:46:47 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package client;

import client.dm.dao.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;


public class TaxProfile extends HibernateEntity implements CRUDEntity
{
    public static final String OVERRIDE_STATUS_PENDING = "P";

    private CRUDEntityImpl crudEntityImpl;
    private TaxProfileVO taxProfileVO;
    private TaxInformation taxInformation;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;

    /**
     * Instantiates a TaxProfile entity with a default TaxProfileVO.
     */
    public TaxProfile()
    {
        init();
    }

    /**
     * Instantiates a TaxProfile entity with a supplied TaxProfileVO.
     *
     * @param taxProfileVO
     */
    public TaxProfile(TaxProfileVO taxProfileVO)
    {
        init();

        this.taxProfileVO = taxProfileVO;
    }

    public static TaxProfile buildDefault()
    {
        TaxProfile taxProfile = (TaxProfile) SessionHelper.newInstance(TaxProfile.class, TaxProfile.DATABASE);

        taxProfile.setOverrideStatus(OVERRIDE_STATUS_PENDING);

        return taxProfile;
    }

    public Long getTaxInformationFK()
    {
        return SessionHelper.getPKValue(taxProfileVO.getTaxInformationFK());
    }
     //-- long getTaxInformationFK() 

    public void setTaxInformationFK(Long taxInformationFK)
    {
        taxProfileVO.setTaxInformationFK(SessionHelper.getPKValue(taxInformationFK));
    }
     //-- void setTaxInformationFK(long) 

    public TaxInformation getTaxInformation()
    {
        return taxInformation;
    }

    public void setTaxInformation(TaxInformation taxInformation)
    {
        this.taxInformation = taxInformation;
    }

    public String getExemptions()
    {
        return taxProfileVO.getExemptions();
    }

    //-- java.lang.String getExemptions() 
    public String getFicaIndicator()
    {
        return taxProfileVO.getFicaIndicator();
    }

    //-- java.lang.String getFicaIndicator() 
    public String getOverrideStatus()
    {
        return taxProfileVO.getOverrideStatus();
    }

    //-- java.lang.String getOverrideStatus() 
    public String getTaxFilingStatusCT()
    {
        return taxProfileVO.getTaxFilingStatusCT();
    }

    //-- java.lang.String getTaxFilingStatusCT() 
    public String getTaxIndicatorCT()
    {
        return taxProfileVO.getTaxIndicatorCT();
    }

    //-- java.lang.String getTaxIndicatorCT() 
    public Long getTaxProfilePK()
    {
        return SessionHelper.getPKValue(taxProfileVO.getTaxProfilePK());
    }

    //-- long getTaxProfilePK() 
    public void setExemptions(String exemptions)
    {
        taxProfileVO.setExemptions(exemptions);
    }

    //-- void setExemptions(java.lang.String) 
    public void setFicaIndicator(String ficaIndicator)
    {
        taxProfileVO.setFicaIndicator(ficaIndicator);
    }

    //-- void setFicaIndicator(java.lang.String) 
    public void setOverrideStatus(String overrideStatus)
    {
        taxProfileVO.setOverrideStatus(overrideStatus);
    }

    //-- void setOverrideStatus(java.lang.String) 
    public void setTaxFilingStatusCT(String taxFilingStatusCT)
    {
        taxProfileVO.setTaxFilingStatusCT(taxFilingStatusCT);
    }

    //-- void setTaxFilingStatusCT(java.lang.String) 
    public void setTaxIndicatorCT(String taxIndicatorCT)
    {
        taxProfileVO.setTaxIndicatorCT(taxIndicatorCT);
    }

    //-- void setTaxIndicatorCT(java.lang.String) 
    public void setTaxProfilePK(Long taxProfilePK)
    {
        taxProfileVO.setTaxProfilePK(SessionHelper.getPKValue(taxProfilePK));
    }

    //-- void setTaxProfilePK(long) 

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (taxProfileVO == null)
        {
            taxProfileVO = new TaxProfileVO();
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
        return taxProfileVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return taxProfileVO.getTaxProfilePK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.taxProfileVO = (TaxProfileVO) voObject;
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
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return TaxProfile.DATABASE;
    }

    /**
     * Finder.
     *
     * @param taxProfilePK
     */
    public static final TaxProfile findByPK(long taxProfilePK)
    {
        TaxProfile taxProfile = null;

        TaxProfileVO[] taxProfileVOs = new TaxProfileDAO().findByPK(taxProfilePK);

        if (taxProfileVOs != null)
        {
            taxProfile = new TaxProfile(taxProfileVOs[0]);
        }

        return taxProfile;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, TaxProfile.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, TaxProfile.DATABASE);
    }
}
