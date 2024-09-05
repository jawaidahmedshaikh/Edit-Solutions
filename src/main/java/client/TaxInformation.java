/*
 * User: cgleason
 * Date: Jan 17, 2005
 * Time: 4:42:24 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  subject to the license agreement.
 */
package client;

import client.dm.dao.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import java.util.*;


public class TaxInformation extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;
    private ClientDetail clientDetail;
    private TaxInformationVO taxInformationVO;
    private Set<TaxProfile> taxProfiles = new HashSet<TaxProfile>();

    public static final String TAX_ID_TYPE_SSN = "SocialSecurityNumber";
    public static final String TAX_ID_TYPE_TIN_NOT_AVAILABLE = "TINNotAvailable";


    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;

    /**
     * Instantiates a TaxInformation entity with a default TaxInformationVO.
     */
    public TaxInformation()
    {
        init();
    }

    /**
     * Instantiates a TaxInformation entity with a supplied TaxInformationVO.
     *
     * @param taxInformationVO
     */
    public TaxInformation(TaxInformationVO taxInformationVO)
    {
        init();

        this.taxInformationVO = taxInformationVO;
    }

    public static TaxInformation buildDefault()
    {
        TaxInformation taxInformation = (TaxInformation) SessionHelper.newInstance(TaxInformation.class, TaxInformation.DATABASE);

        return taxInformation;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (taxInformationVO == null)
        {
            taxInformationVO = new TaxInformationVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * Getter
     * @return  set of taxProfiles
     */
    public Set<TaxProfile> getTaxProfiles()
    {
        return taxProfiles;
    }

    /**
     * Setter
     * @param taxProfiles      set of taxProfiles
     */
    public void setTaxProfiles(Set<TaxProfile> taxProfiles)
    {
        this.taxProfiles = taxProfiles;
    }

    /**
     * Adds a TaxProfile to the set of children
     * @param taxProfile
     */
    public void addTaxProfile(TaxProfile taxProfile)
    {
        this.getTaxProfiles().add(taxProfile);

        taxProfile.setTaxInformation(this);

        SessionHelper.saveOrUpdate(taxProfile, TaxInformation.DATABASE);
    }

    /**
     * Removes a TaxProfile from the set of children
     * @param taxProfile
     */
    public void removeTaxProfile(TaxProfile taxProfile)
    {
        this.getTaxProfiles().remove(taxProfile);

        taxProfile.setTaxInformation(null);

        SessionHelper.saveOrUpdate(taxProfile, TaxInformation.DATABASE);
    }


    public ClientDetail getClientDetail()
    {
        return clientDetail;
    }

    public void setClientDetail(ClientDetail clientDetail)
    {
        this.clientDetail = clientDetail;
    }

    public String getCitizenshipIndCT()
    {
        return taxInformationVO.getCitizenshipIndCT();
    }

    //-- java.lang.String getCitizenshipIndCT() 
    public String getCountryOfBirthCT()
    {
        return taxInformationVO.getCountryOfBirthCT();
    }

    //-- java.lang.String getCountryOfBirthCT() 
    public String getMaritalStatusCT()
    {
        return taxInformationVO.getMaritalStatusCT();
    }

    //-- java.lang.String getMaritalStatusCT() 
    public String getProofOfAgeIndCT()
    {
        return taxInformationVO.getProofOfAgeIndCT();
    }

    //-- java.lang.String getProofOfAgeIndCT() 
    public String getStateOfBirthCT()
    {
        return taxInformationVO.getStateOfBirthCT();
    }

    //-- java.lang.String getStateOfBirthCT() 
    public String getTaxIdTypeCT()
    {
        return taxInformationVO.getTaxIdTypeCT();
    }

    //-- java.lang.String getTaxIdTypeCT() 
    public Long getTaxInformationPK()
    {
        return SessionHelper.getPKValue(taxInformationVO.getTaxInformationPK());
    }

    //-- long getTaxInformationPK() 
    public void setCitizenshipIndCT(String citizenshipIndCT)
    {
        taxInformationVO.setCitizenshipIndCT(citizenshipIndCT);
    }

    //-- void setCitizenshipIndCT(java.lang.String) 
    public void setCountryOfBirthCT(String countryOfBirthCT)
    {
        taxInformationVO.setCountryOfBirthCT(countryOfBirthCT);
    }

    public void setMaritalStatusCT(String maritalStatusCT)
    {
        taxInformationVO.setMaritalStatusCT(maritalStatusCT);
    }

    public void setProofOfAgeIndCT(String proofOfAgeIndCT)
    {
        taxInformationVO.setProofOfAgeIndCT(proofOfAgeIndCT);
    }

    public void setStateOfBirthCT(String stateOfBirthCT)
    {
        taxInformationVO.setStateOfBirthCT(stateOfBirthCT);
    }

    public void setTaxIdTypeCT(String taxIdTypeCT)
    {
        taxInformationVO.setTaxIdTypeCT(taxIdTypeCT);
    }

    public void setTaxInformationPK(Long taxInformationPK)
    {
        taxInformationVO.setTaxInformationPK(SessionHelper.getPKValue(taxInformationPK));
    }


    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save()
    {
        //CONVERT to SAVE using HIBERNATE
        hSave();
//        crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);

        if (taxInformationVO.getTaxProfileVOCount() > 0)
        {
            TaxProfileVO[] taxProfileVOs = taxInformationVO.getTaxProfileVO();

            for (int i = 0; i < taxProfileVOs.length; i++)
            {
                taxProfileVOs[i].setTaxInformationFK(taxInformationVO.getTaxInformationPK());

                TaxProfile taxProfile = new TaxProfile(taxProfileVOs[i]);
                taxProfile.setTaxInformation(this);

                //CONVERT to SAVE using HIBERNATE
                taxProfile.hSave();
//                taxProfile.save();
            }
        }
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
        return taxInformationVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return taxInformationVO.getTaxInformationPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.taxInformationVO = (TaxInformationVO) voObject;
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
        return TaxInformation.DATABASE;
    }

    /**
     * Finder.
     *
     * @param taxInformationPK
     */
    public static final TaxInformation findByPK(long taxInformationPK)
    {
        TaxInformation taxInformation = null;

        TaxInformationVO[] taxInformationVOs = new TaxInformationDAO().findByPK(taxInformationPK);

        if (taxInformationVOs != null)
        {
            taxInformation = new TaxInformation(taxInformationVOs[0]);
        }

        return taxInformation;
    }

    public Long getClientDetailFK()
    {
        return SessionHelper.getPKValue(taxInformationVO.getClientDetailFK());
    }

    public void setClientDetailFK(Long clientDetailFK)
    {
        taxInformationVO.setClientDetailFK(SessionHelper.getPKValue(clientDetailFK));
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, TaxInformation.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, TaxInformation.DATABASE);
    }
}
