/**
 * User: cgleason
 * Date: Dec 08, 2008
 * Time: 12:33:30 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package group;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.common.EDITDateTime;

import java.util.*;

import staging.IStaging;
import staging.StagingContext;
import client.ClientDetail;
import client.ClientAddress;
import fission.utility.*;


public class CaseSetup extends HibernateEntity
{
    private Long caseSetupPK;
    private Long contractGroupFK;
    private ContractGroup contractGroup;
    private String stateCT;
    private EDITDate effectiveDate;
    private String caseSetupOptionCT;
    private String caseSetupOptionValueCT;

    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;


    public CaseSetup()
    {
    }

    /**
     * Get CaseSetupPK
     * @return
     */
    public Long getCaseSetupPK()
    {
        return caseSetupPK;
    }

    /**
     * Set CaseSetupPK
     * @param caseSetupPK the PK
     */
    public void setCaseSetupPK(Long caseSetupPK)
    {
        this.caseSetupPK = caseSetupPK;
    }

    /**
     * Get ContractGroupFK
     * @return
     */
    public Long getContractGroupFK()
    {
        return contractGroupFK;
    }

    /**
     * Set ContractGroupFK
     * @param contractGroupFK
     */
    public void setContractGroupFK(Long contractGroupFK)
    {
        this.contractGroupFK = contractGroupFK;
    }

    /**
     * Get the ContractGroup (the parent of CaseSetup)
     * @return
     */
    public ContractGroup getContractGroup()
    {
        return contractGroup;
    }

    /**
     * Set ContractGroup (the parent of CaseSetup)
     * @param contractGroup
     */
    public void setContractGroup(ContractGroup contractGroup)
    {
        this.contractGroup = contractGroup;
    }

    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, SessionHelper.EDITSOLUTIONS);
    }

    public void hDelete()
    {
        SessionHelper.delete(this, CaseSetup.DATABASE);
    }
    /**
     * Get StateCT
     * @return
     */
    public String getStateCT()
    {
        return stateCT;
    }

    /**
     * Set StateCT
     * @param stateCT
     */
    public void setStateCT(String stateCT)
    {
        this.stateCT = stateCT;
    }

    /**
     * Get effectiveDate
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }

    /**
     * Set effectiveDate
     * @param effectiveDate
     */
    public void setEffectiveDate(EDITDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Get CaseSetupOptionCT
     * @return
     */
    public String getCaseSetupOptionCT()
    {
        return caseSetupOptionCT;
    }

    /**
     * Set CaseSetupOptionCT
     * @param CaseSetupOptionCT
     */
    public void setCaseSetupOptionCT(String caseSetupOptionCT)
    {
        this.caseSetupOptionCT = caseSetupOptionCT;
    }

    /**
     * Get CaseSetupOptionValueCT
     * @return
     */
    public String getCaseSetupOptionValueCT()
    {
        return caseSetupOptionValueCT;
    }

    /**
     * Set CaseSetupOptionValueCT
     * @param CaseSetupOptionValueCT
     */
    public void setCaseSetupOptionValueCT(String caseSetupOptionValueCT)
    {
        this.caseSetupOptionValueCT = caseSetupOptionValueCT;
    }

    /**
     * Finder.
     * @param contractGroupFK
     * @return
     */
    public static CaseSetup findByCaseSetupPK(Long caseSetupPK)
    {
        return (CaseSetup) SessionHelper.get(CaseSetup.class, caseSetupPK, CaseSetup.DATABASE);
    }

    /**
     * Finder.
     * @param contractGroup
     * @return
     */
    public static CaseSetup[] findByContractGroup(Long contractGroupFK)
    {
        String hql = " from CaseSetup caseSetup" +
                    " where caseSetup.ContractGroupFK = :contractGroupFK";

        EDITMap params = new EDITMap("contractGroupFK", contractGroupFK);

        List<CaseSetup> results = SessionHelper.executeHQL(hql, params, CaseSetup.DATABASE);

        return (CaseSetup[]) results.toArray(new CaseSetup[results.size()]);
    }

    public static CaseSetup findBestMatchForFormState(Long contractGroupFK, String ownResidenceState, String caseSetupOption, EDITDate effectiveDate)
    {
        CaseSetup caseSetup = null;

        String hql = " select caseSetup from CaseSetup caseSetup" +
                     " where caseSetup.ContractGroupFK = :contractGroupFK" +
                     " and caseSetup.StateCT = :state" +
                     " and caseSetup.CaseSetupOptionCT = :caseSetupOption" +
                     " and caseSetup.EffectiveDate = (select max(caseSetup2.EffectiveDate) from CaseSetup caseSetup2" +
                     " where caseSetup2.ContractGroupFK = :contractGroupFK" +
                     " and caseSetup2.StateCT = :state" +
                     " and caseSetup2.CaseSetupOptionCT = :caseSetupOption" +
                     " and caseSetup2.EffectiveDate <= :effectiveDate)";

        Map params = new HashMap();

        params.put("contractGroupFK", contractGroupFK);
        params.put("state", ownResidenceState);
        params.put("caseSetupOption", caseSetupOption);
        params.put("effectiveDate", effectiveDate);

        List<CaseSetup> results = SessionHelper.executeHQL(hql, params, CaseSetup.DATABASE);

        if (!results.isEmpty())
        {
            caseSetup = results.get(0);
        }

        return caseSetup;
    }


    public String getDatabase()
    {
        return CaseSetup.DATABASE;
    }

}
