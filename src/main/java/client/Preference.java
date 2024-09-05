/*
 * User: cgleason
 * Date: Jan 17, 2005
 * Time: 4:38:47 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package client;

import client.dm.dao.*;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;


import java.util.ArrayList;

import java.util.*;

import event.*;
import role.ClientRole;
import fission.utility.Util;
import fission.beans.PageBean;


public class Preference extends HibernateEntity implements CRUDEntity
{
    public static final String PRIMARY = "P";
    public static final String OVERRIDE = "O";
    public static final String MONTHLY = "Monthly";
    public static final String AUTO = "Auto";
    public static final String DAILY = "Daily";
    public static final String WEEKLY = "Weekly";
    public static final String PAYMENTMODECT_CHECK = "Check";
    public static final String TYPE_BILLING = "Billing";
    public static final String TYPE_DISBURSEMENT = "Disbursement";
    public static final String DISBURSEMENT_SOURCE_PAPER = "Paper";
    public static final String BANK_ACCOUNT_TYPE_CHECKING = "Checking";
    public static final String BANK_ACCOUNT_TYPE_SAVING = "Saving";


    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;

    private CRUDEntityImpl crudEntityImpl;
    private PreferenceVO preferenceVO;
    private ClientDetail clientDetail;
    private Set suspenses = new HashSet();


    /**
     * Instantiates a Preference entity with a default PreferenceVO.
     */
    public Preference()
    {
        init();
    }

    /**
     * Instantiates a Preference entity with a supplied PreferenceVO.
     *
     * @param preferenceVO
     */
    public Preference(PreferenceVO preferenceVO)
    {
        init();

        this.preferenceVO = preferenceVO;
    }

    public Long getClientDetailFK()
    {
        return SessionHelper.getPKValue(preferenceVO.getClientDetailFK());
    }
     //-- long getClientDetailFK() 

    public void setClientDetailFK(Long clientDetailFK)
    {
        preferenceVO.setClientDetailFK(SessionHelper.getPKValue(clientDetailFK));
    }
     //-- void setClientDetailFK(long) 

    public ClientDetail getClientDetail()
    {
        return clientDetail;
    }

  /**
     * Adds Suspense.
     * @param suspense
     */
    public void addSuspense(Suspense suspense)
    {
        getSuspenses().add(suspense);

        suspense.setPreference(this);

//        SessionHelper.saveOrUpdate(this, SessionHelper.EDITSOLUTIONS);
    }        

    public void setClientDetail(ClientDetail clientDetail)
    {
        this.clientDetail = clientDetail;
    }

   /**
     * Getter.
     * @return
     */
    public Set getSuspenses()
    {
        return suspenses;
    }

    /**
     * Setter.
     * @param suspenses
     */
    public void setSuspenses(Set suspenses)
    {
        this.suspenses = suspenses;
    }

    public String getDisbursementSourceCT()
    {
        return preferenceVO.getDisbursementSourceCT();
    }

    //-- java.lang.String getDisbursementSourceCT() 
    public EDITBigDecimal getMinimumCheck()
    {
        return SessionHelper.getEDITBigDecimal(preferenceVO.getMinimumCheck());
    }

    //-- java.math.BigDecimal getMinimumCheck() 
    public String getOverrideStatus()
    {
        return preferenceVO.getOverrideStatus();
    }

    //-- java.lang.String getOverrideStatus() 
    public Long getPreferencePK()
    {
        return SessionHelper.getPKValue(preferenceVO.getPreferencePK());
    }

    //-- long getPreferencePK() 
    public String getPrintAs()
    {
        return preferenceVO.getPrintAs();
    }

    //-- java.lang.String getPrintAs() 
    public String getPrintAs2()
    {
        return preferenceVO.getPrintAs2();
    }

    //-- java.lang.String getPrintAs2()
    public void setDisbursementSourceCT(String disbursementSourceCT)
    {
        preferenceVO.setDisbursementSourceCT(disbursementSourceCT);
    }

    //-- void setDisbursementSourceCT(java.lang.String) 
    public void setMinimumCheck(EDITBigDecimal minimumCheck)
    {
        preferenceVO.setMinimumCheck(SessionHelper.getEDITBigDecimal(minimumCheck));
    }

    //-- void setMinimumCheck(java.math.BigDecimal) 
    public void setOverrideStatus(String overrideStatus)
    {
        preferenceVO.setOverrideStatus(overrideStatus);
    }

    //-- void setOverrideStatus(java.lang.String) 
    public void setPaymentModeCT(String paymentModeCT)
    {
        preferenceVO.setPaymentModeCT(paymentModeCT);
    }

    //-- void setPaymentModeCT(java.lang.String) 
    public void setPreferencePK(Long preferencePK)
    {
        preferenceVO.setPreferencePK(SessionHelper.getPKValue(preferencePK));
    }

    //-- void setPreferencePK(long) 
    public void setPrintAs(String printAs)
    {
        preferenceVO.setPrintAs(printAs);
    }

    //-- void setPrintAs(java.lang.String) 
    public void setPrintAs2(String printAs2)
    {
        preferenceVO.setPrintAs2(printAs2);
    }

    //-- void setPrintAs2(java.lang.String)

    public String getPreferenceTypeCT()
    {
        return preferenceVO.getPreferenceTypeCT();
    } //-- java.lang.String getPreferenceTypeCT()

    public void setPreferenceTypeCT(String preferenceTypeCT)
    {
        preferenceVO.setPreferenceTypeCT(preferenceTypeCT);
    } //-- void setPreferenceTypeCT(java.lang.String)


    /**
     * Getter.
     * @return
     */
    public String getBankAccountNumber()
    {
        return preferenceVO.getBankAccountNumber();
    }

    /**
     * Setter.
     * @param bankAccountNumber
     */
    public void setBankAccountNumber(String bankAccountNumber)
    {
        preferenceVO.setBankAccountNumber(bankAccountNumber);
    }

    /**
     * Getter.
     * @return
     */
    public String getBankRoutingNumber()
    {
        return preferenceVO.getBankRoutingNumber();
    }

    /**
     * Setter.
     * @param bankRoutingNumber
     */
    public void setBankRoutingNumber(String bankRoutingNumber)
    {
        preferenceVO.setBankRoutingNumber(bankRoutingNumber);
    }

    /**
     * Getter.
     * @return
     */
    public String getBankAccountTypeCT()
    {
        return preferenceVO.getBankAccountTypeCT();
    }

    /**
     * Setter.
     * @param bankAccountTypeCT
     */
    public void setBankAccountTypeCT(String bankAccountTypeCT)
    {
        preferenceVO.setBankAccountTypeCT(bankAccountTypeCT);
    }

    /**
     * Getter.
     * @return
     */
    public String getBankName()
    {
        return preferenceVO.getBankName();
    }

    /**
     * Setter.
     * @param bankName
     */
    public void setBankName(String bankName)
    {
        preferenceVO.setBankName(bankName);
    }

    /**
     * Getter.
     * @return
     */
    public String getBankAddressLine1()
    {
        return preferenceVO.getBankAddressLine1();
    }

    /**
     * Setter.
     * @param bankAddressLine1
     */
    public void setBankAddressLine1(String bankAddressLine1)
    {
        preferenceVO.setBankAddressLine1(bankAddressLine1);
    }

    /**
     * Getter.
     * @return
     */
    public String getBankAddressLine2()
    {
        return preferenceVO.getBankAddressLine2();
    }

    /**
     * Setter.
     * @param bankAddressLine2
     */
    public void setBankAddressLine2(String bankAddressLine2)
    {
        preferenceVO.setBankAddressLine2(bankAddressLine2);
    }

    /**
     * Getter.
     * @return
     */
    public String getBankCity()
    {
        return preferenceVO.getBankCity();
    }

    /**
     * Setter.
     * @param bankCity
     */
    public void setBankCity(String bankCity)
    {
        preferenceVO.setBankCity(bankCity);
    }

    /**
     * Getter.
     * @return
     */
    public String getBankStateCT()
    {
        return preferenceVO.getBankStateCT();
    }

    /**
     * Setter.
     * @param bankStateCT
     */
    public void setBankStateCT(String bankStateCT)
    {
        preferenceVO.setBankStateCT(bankStateCT);
    }

    /**
     * Getter.
     * @return
     */
    public String getBankZipCode()
    {
        return preferenceVO.getBankZipCode();
    }

    /**
     * Setter.
     * @param bankZipCode
     */
    public void setBankZipCode(String bankZipCode)
    {
        preferenceVO.setBankZipCode(bankZipCode);
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (preferenceVO == null)
        {
            preferenceVO = new PreferenceVO();
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
        return preferenceVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return preferenceVO.getPreferencePK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.preferenceVO = (PreferenceVO) voObject;
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
        return Preference.DATABASE;
    }

    /**
     * Finder.
     *
     * @param preferencePK
     */
    public static final Preference findByPK(long preferencePK)
    {
        Preference preference = null;

        PreferenceVO[] preferenceVOs = new PreferenceDAO().findByPK(preferencePK);

        if (preferenceVOs != null)
        {
            preference = new Preference(preferenceVOs[0]);
        }

        return preference;
    }

    public static Preference findByPK(Long preferencePK)
    {
        return (Preference) SessionHelper.get(Preference.class, preferencePK, Preference.DATABASE);
    }

    /**
     * Finder.
     * @param clientRoleFinancialPK
     * @return
     */
    public static final Preference findByClientRoleFinancialPK_OverrideStatus(long clientRoleFinancialPK, String overrideStatus)
    {
        Preference preference = null;

        PreferenceVO[] preferenceVOs = new PreferenceDAO().findByClientRoleFinancialPK_OverrideStatus(clientRoleFinancialPK, overrideStatus);

        if (preferenceVOs != null)
        {
            preference = new Preference(preferenceVOs[0]);
        }

        return preference;
    }

    /**
     * Finder.
     * @param clientDetailFK
     * @return
     */
    public static final Preference findByClientDetailFK(long clientDetailFK)
    {
        Preference preference = null;

        PreferenceVO[] preferenceVOs = new PreferenceDAO().findPrimaryByClientDetailPK(clientDetailFK, false, new ArrayList());

        if (preferenceVOs != null)
        {
            preference = new Preference(preferenceVOs[0]);
        }

        return preference;
    }

    /**
     * Getter.
     * @return
     */
    public String getPaymentModeCT()
    {
        return preferenceVO.getPaymentModeCT();
    }

    //-- java.lang.String getPaymentModeCT()

    /**
     * Finder.
     * @param placedAgentPK
     * @param overrideStatus
     * @return
     */
    public static Preference findBy_PlacedAgentPK_OverrideStatus(long placedAgentPK, String overrideStatus)
    {
        Preference preference = null;

        PreferenceVO[] preferenceVOs = new PreferenceDAO().findByPlacedAgentPK_OverrideStatus(placedAgentPK, overrideStatus);

        if (preferenceVOs != null)
        {
            preference = new Preference(preferenceVOs[0]);
        }

        return preference;
    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Preference.DATABASE);
    }

    public void hDelete()
    {
        SessionHelper.delete(this, Preference.DATABASE);
    }

    /**
     * Finder.
     * @param clientDetailFK
     * @return
     */
    public static final Preference findByClientDetailFK(Long clientDetailFK)
    {
        Preference preference = null;

        String hql = "select preference from Preference preference " +
                     " where preference.ClientDetailFK = :clientDetailFK " +
                     " and preference.OverrideStatus = :overrideValue";

        Map params = new HashMap();

        params.put("clientDetailFK", clientDetailFK);
        params.put("overrideValue", "P");

        List results = SessionHelper.executeHQL(hql, params, Preference.DATABASE);

        if (!results.isEmpty())
        {
            preference = (Preference) results.get(0);
        }

        return preference;
    }
    public static Preference findByClientDetailPK_Disbursement(Long clientDetailPK, String disbursementSource)
    {
        Preference preference = null;

        String hql = "select preference from Preference preference " +
                     " where preference.ClientDetailFK = :clientDetailFK " +
                     " and preference.DisbursementSourceCT = :disbursementSource";

        Map params = new HashMap();

        params.put("clientDetailFK", clientDetailPK);
        params.put("disbursementSource", disbursementSource);

        List results = SessionHelper.executeHQL(hql, params, Preference.DATABASE);

        if (!results.isEmpty())
        {
            preference = (Preference) results.get(0);
        }

        return preference;
    }

    public static Preference findByClientDetailPK_DisbursementAndPreferenceType(Long clientDetailPK, String disbursementSource, String preferenceTypeCT)
    {
        Preference preference = null;

        String hql = "select preference from Preference preference " +
                     " where preference.ClientDetailFK = :clientDetailFK " +
                     " and preference.DisbursementSourceCT = :disbursementSource" +
                     " and preference.PreferenceTypeCT = :preferenceTypeCT";

        Map params = new HashMap();

        params.put("clientDetailFK", clientDetailPK);
        params.put("disbursementSource", disbursementSource);
        params.put("preferenceTypeCT", preferenceTypeCT);

        List results = SessionHelper.executeHQL(hql, params, Preference.DATABASE);

        if (!results.isEmpty())
        {
            preference = (Preference) results.get(0);
        }

        return preference;
    }
    public static Preference findByPreferenceType(String preferenceType)
    {
        Preference preference = null;

        String hql = "select preference from Preference preference " +
                     " where preference.PreferenceTypeCT = :preferenceType";

        Map params = new HashMap();

        params.put("preferenceType", preferenceType);

        List results = SessionHelper.executeHQL(hql, params, Preference.DATABASE);

        if (!results.isEmpty())
        {
            preference = (Preference) results.get(0);
        }

        return preference;
    }

    public static Preference findByClientDetailPK_EFTPrimary_PreferenceType(Long clientDetailPK, String preferenceType)
    {
        Preference preference = null;

        String hql = "select preference from Preference preference " +
                     " where preference.ClientDetailFK = :clientDetailFK " +
                     " and preference.PreferenceTypeCT = :preferenceType" +
                     " and preference.DisbursementSourceCT = :disbursementSource" +
                     " and preference.OverrideStatus = :overrideStatus";

        Map params = new HashMap();

        params.put("clientDetailFK", clientDetailPK);
        params.put("preferenceType", preferenceType);
        params.put("disbursementSource", "EFT");
        params.put("overrideStatus", "P");

        List results = SessionHelper.executeHQL(hql, params, Preference.DATABASE);

        if (!results.isEmpty())
        {
            preference = (Preference) results.get(0);
        }

        return preference;
    }

    public static void updateExistingPreference(Preference preference, Long selectedClientRoleFK) throws Exception
    {
        try
        {
            ClientRole clientRole = ClientRole.findByPK(new Long(selectedClientRoleFK));
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
            clientRole.setPreference(preference);
            SessionHelper.saveOrUpdate(clientRole, SessionHelper.EDITSOLUTIONS);
            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
            throw new Exception(e.getMessage());
        }
    }

    public static Preference createNewPreference(PageBean formBean, ClientRole clientRole) throws Exception
    {
        String overrideStatus = formBean.getValue("overrideStatus");
        String printAs = Util.initString(formBean.getValue("printAs"), null);
        String printAs2 = Util.initString(formBean.getValue("printAs2"), null);
        String disbursementSource = Util.initString(formBean.getValue("disbursementSource"), null);
        String paymentMode = Util.initString(formBean.getValue("paymentMode"), null);
        String bankAccountNumber = Util.initString(formBean.getValue("bankAccountNumber"), null);
        String bankRoutingNumber = Util.initString(formBean.getValue("bankRoutingNumber"), null);
        String bankAccountType = Util.initString(formBean.getValue("bankAccountType"), null);
        String bankName = Util.initString(formBean.getValue("bankName"), null);
        String bankAddressLine1 = Util.initString(formBean.getValue("bankAddressLine1"), null);
        String bankAddressLine2 = Util.initString(formBean.getValue("bankAddressLine2"), null);
        String bankCity = Util.initString(formBean.getValue("bankCity"), null);
        String bankState = Util.initString(formBean.getValue("bankState"), null);
        String bankZipCode = Util.initString(formBean.getValue("bankZipCode"), null);
        String minimumCheck = formBean.getValue("minimumCheck");

        Preference preference = new Preference();
        preference.setOverrideStatus(overrideStatus);
        preference.setPrintAs(printAs);
        preference.setPrintAs2(printAs2);
        preference.setDisbursementSourceCT(disbursementSource);
        preference.setPreferenceTypeCT(Util.initString(formBean.getValue("preferenceType"), null));
        preference.setPaymentModeCT(paymentMode);
        preference.setBankAccountNumber(bankAccountNumber);
        preference.setBankRoutingNumber(bankRoutingNumber);
        preference.setBankAccountTypeCT(bankAccountType);
        preference.setBankName(bankName);
        preference.setBankAddressLine1(bankAddressLine1);
        preference.setBankAddressLine2(bankAddressLine2);
        preference.setBankCity(bankCity);
        preference.setBankStateCT(bankState);
        preference.setBankZipCode(bankZipCode);
        if (Util.isANumber(minimumCheck))
        {
            preference.setMinimumCheck(new EDITBigDecimal(minimumCheck));
        }

        ClientDetail clientDetail = clientRole.getClientDetail();

        try
        {
            preference.setClientDetail(clientDetail);
            SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
            preference.hSave();
            clientDetail.addPreference(preference);
            clientDetail.hSave();
            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
            throw new Exception(e.getMessage());
        }

        return preference;
    }
}
