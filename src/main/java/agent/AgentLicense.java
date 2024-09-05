/*
 * User: cgleason
 * Date: Dec 5, 2003
 * Time: 11:24:16 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package agent;

import edit.common.exceptions.EDITException;
import edit.common.vo.AgentLicenseVO;
import edit.common.vo.VOObject;
import edit.common.*;
import edit.common.exceptions.EDITValidationException;

import edit.services.db.*;
import edit.services.db.hibernate.*;


public class AgentLicense extends HibernateEntity implements CRUDEntity
{
    private AgentLicenseVO agentLicenseVO;

    private AgentLicenseImpl agentLicenseImpl;

    private String operator;

    private boolean newAgentInd;
    private Agent agent;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;

    public static final String ALL_PRODUCTS = "AllProductTypes";
    public static final String ANNUITY_ONLY = "AnnuityOnly";
    public static final String LIFE_ONLY = "LifeOnly";
    public static final String LIFE_ONLY_ALL_ENROLLMENTS = "LifeOnlyAll";
    public static final String LIFE_ONLY_CALL_CENTER = "LifeOnlyCallCenter";
    public static final String LIFE_ONLY_FACE_TO_FACE = "LifeOnlyFaceToFace";
    public static final String LIFE_ONLY_MAIL = "LifeOnlyMail";
    public static final String LIFE_ONLY_WEB = "LifeOnlyWeb";

    public AgentLicense()
    {
        this.agentLicenseVO = new AgentLicenseVO();
        this.agentLicenseImpl = new AgentLicenseImpl();
    }

    public AgentLicense(long agentLicensePK) throws Exception
    {
        this();
        this.agentLicenseImpl.load(this, agentLicensePK);
    }

    public AgentLicense(AgentLicenseVO agentLicenseVO)
    {
        this();
        this.agentLicenseVO = agentLicenseVO;
    }

    public void save()
    {
//        boolean updateDone = checkForNonFinancialChanges();

//        if (!updateDone)
//        {
//            this.agentLicenseImpl.save(this);
            hSave();
//        }
    }

    public void delete() throws Exception
    {
        this.agentLicenseImpl.delete(this);
    }

    public VOObject getVO()
    {
        return agentLicenseVO;
    }

    public long getPK()
    {
        return agentLicenseVO.getAgentLicensePK();
    }

    public void setVO(VOObject voObject)
    {
        this.agentLicenseVO = (AgentLicenseVO) voObject;
    }

    public boolean isNew()
    {
        return this.agentLicenseImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return this.agentLicenseImpl.cloneCRUDEntity(this);
    }

    public void associateAgent(Agent agent)
    {
        this.agentLicenseVO.setAgentFK(agent.getPK());
    }

    public void validateLicense(String validationDate, String issueState, long productStructureFK) throws EDITValidationException, Exception
    {
        agentLicenseImpl.validateLicense(this, validationDate, issueState, productStructureFK);
    }

    public static AgentLicense[] findByAgentPK_AND_IssueState(long agentPK, String issueState) throws Exception
    {
        return AgentLicenseImpl.findByAgentPK_AND_IssueState_AND_Date(agentPK, issueState);
    }

    /**
     * Operator getter.
     * @return
     */
    public String getOperator()
    {
        return operator;
    }

    /**
     * Operator setter.
     * @param operator
     */
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public void setNewAgentInd(boolean newAgent)
    {
        this.newAgentInd = newAgent;
    }

    /**
     * Getter.
     * @return
     */
    public long getAgentLicensePK()
    {
        return agentLicenseVO.getAgentLicensePK();
    } //-- long getAgentLicensePK()

    /**
     * Getter.
     * @return
     */
    public long getAgentFK()
    {
        return agentLicenseVO.getAgentFK();
    } //-- long getAgentFK()

    /**
     * Getter.
     * @return
     */
    public EDITDate getErrorOmmissExpDate()
    {
        return SessionHelper.getEDITDate(agentLicenseVO.getErrorOmmissExpDate());
    } //-- java.lang.String getErrorOmmissExpDate()

    /**
     * Getter.
     * @return
     */
    public String getErrorOmmissStatus()
    {
        return agentLicenseVO.getErrorOmmissStatus();
    } //-- java.lang.String getErrorOmmissStatus()

    /**
     * Getter.
     * @return
     */
    public EDITDate getLicEffDate()
    {
        return SessionHelper.getEDITDate(agentLicenseVO.getLicEffDate());
    } //-- java.lang.String getLicEffDate()

    /**
     * Getter.
     * @return
     */
    public EDITDate getLicExpDate()
    {
        return SessionHelper.getEDITDate(agentLicenseVO.getLicExpDate());
    } //-- java.lang.String getLicExpDate()

    /**
     * Getter.
     * @return
     */
    public EDITDate getLicTermDate()
    {
        return SessionHelper.getEDITDate(agentLicenseVO.getLicTermDate());
    } //-- java.lang.String getLicTermDate()

    /**
     * Getter.
     * @return
     */
    public String getLicenseNumber()
    {
        return agentLicenseVO.getLicenseNumber();
    } //-- java.lang.String getLicenseNumber()

    /**
     * Getter.
     * @return
     */
    public String getLicenseTypeCT()
    {
        return agentLicenseVO.getLicenseTypeCT();
    } //-- java.lang.String getLicenseTypeCT()

    /**
     * Getter.
     * @return
     */
    public String getNASD()
    {
        return agentLicenseVO.getNASD();
    } //-- java.lang.String getNASD()

    /**
     * Getter.
     * @return
     */
    public EDITDate getNASDEffectiveDate()
    {
        return SessionHelper.getEDITDate(agentLicenseVO.getNASDEffectiveDate());
    } //-- java.lang.String getNASDEffectiveDate()

    /**
     * Getter.
     * @return
     */
    public EDITDate getNASDRenewalDate()
    {
        return SessionHelper.getEDITDate(agentLicenseVO.getNASDRenewalDate());
    } //-- java.lang.String getNASDRenewalDate()

    /**
     * Getter.
     * @return
     */
    public String getProductTypeCT()
    {
        return agentLicenseVO.getProductTypeCT();
    } //-- java.lang.String getProductTypeCT()

    /**
     * Getter.
     * @return
     */
    public String getRenewTermStatusCT()
    {
        return agentLicenseVO.getRenewTermStatusCT();
    } //-- java.lang.String getRenewTermStatusCT()

    /**
     * Getter.
     * @return
     */
    public String getResidentCT()
    {
        return agentLicenseVO.getResidentCT();
    } //-- java.lang.String getResidentCT()

    /**
     * Getter.
     * @return
     */
    public String getStateCT()
    {
        return agentLicenseVO.getStateCT();
    } //-- java.lang.String getStateCT()

    /**
     * Getter.
     * @return
     */
    public String getStateNASD()
    {
        return agentLicenseVO.getStateNASD();
    } //-- java.lang.String getStateNASD()

    /**
     * Getter.
     * @return
     */
    public String getStatusCT()
    {
        return agentLicenseVO.getStatusCT();
    } //-- java.lang.String getStatusCT()

    /**
     * Setter.
     */
    public void setAgentLicensePK(long agentLicensePK)
    {
        agentLicenseVO.setAgentLicensePK(agentLicensePK);
    } //-- void setAgentLicensePK(long)

    /**
     * Setter.
     * @param agentFK
     */
    public void setAgentFK(long agentFK)
    {
        agentLicenseVO.setAgentFK(agentFK);
    } //-- void setAgentFK(long)

    /**
     * Setter.
     */
    public void setErrorOmmissExpDate(EDITDate errorOmmissExpDate)
    {
        agentLicenseVO.setErrorOmmissExpDate(SessionHelper.getEDITDate(errorOmmissExpDate));
    } //-- void setErrorOmmissExpDate(java.lang.String)

    /**
     * Setter.
     */
    public void setErrorOmmissStatus(String errorOmmissStatus)
    {
        agentLicenseVO.setErrorOmmissStatus(errorOmmissStatus);
    } //-- void setErrorOmmissStatus(java.lang.String)

    /**
     * Setter.
     */
    public void setLicEffDate(EDITDate licEffDate)
    {
        agentLicenseVO.setLicEffDate(SessionHelper.getEDITDate(licEffDate));
    } //-- void setLicEffDate(java.lang.String)

    /**
     * Setter.
     */
    public void setLicExpDate(EDITDate licExpDate)
    {
        agentLicenseVO.setLicExpDate(SessionHelper.getEDITDate(licExpDate));
    } //-- void setLicExpDate(java.lang.String)

    /**
     * Setter.
     */
    public void setLicTermDate(EDITDate licTermDate)
    {
        agentLicenseVO.setLicTermDate(SessionHelper.getEDITDate(licTermDate));
    } //-- void setLicTermDate(java.lang.String)

    /**
     * Setter.
     */
    public void setLicenseNumber(String licenseNumber)
    {
        agentLicenseVO.setLicenseNumber(licenseNumber);
    } //-- void setLicenseNumber(java.lang.String)

    /**
     * Setter.
     */
    public void setLicenseTypeCT(String licenseTypeCT)
    {
        agentLicenseVO.setLicenseTypeCT(licenseTypeCT);
    } //-- void setLicenseTypeCT(java.lang.String)

    /**
     * Setter.
     */
    public void setNASD(String NASD)
    {
        agentLicenseVO.setNASD(NASD);
    } //-- void setNASD(java.lang.String)

    /**
     * Setter.
     */
    public void setNASDEffectiveDate(EDITDate NASDEffectiveDate)
    {
        agentLicenseVO.setNASDEffectiveDate(SessionHelper.getEDITDate(NASDEffectiveDate));
    } //-- void setNASDEffectiveDate(java.lang.String)

    /**
     * Setter.
     */
    public void setNASDRenewalDate(EDITDate NASDRenewalDate)
    {
        agentLicenseVO.setNASDRenewalDate(SessionHelper.getEDITDate(NASDRenewalDate));
    } //-- void setNASDRenewalDate(java.lang.String)

    /**
     * Setter.
     */
    public void setProductTypeCT(String productTypeCT)
    {
        agentLicenseVO.setProductTypeCT(productTypeCT);
    } //-- void setProductTypeCT(java.lang.String)

    /**
     * Setter.
     */
    public void setRenewTermStatusCT(String renewTermStatusCT)
    {
        agentLicenseVO.setRenewTermStatusCT(renewTermStatusCT);
    } //-- void setRenewTermStatusCT(java.lang.String)

    /**
     * Setter.
     */
    public void setResidentCT(String residentCT)
    {
        agentLicenseVO.setResidentCT(residentCT);
    } //-- void setResidentCT(java.lang.String)

    /**
     * Setter.
     */
    public void setStateCT(String stateCT)
    {
        agentLicenseVO.setStateCT(stateCT);
    } //-- void setStateCT(java.lang.String)

    /**
     * Setter.
     */
    public void setStateNASD(String stateNASD)
    {
        agentLicenseVO.setStateNASD(stateNASD);
    } //-- void setStateNASD(java.lang.String)

    /**
     * Setter.
     */
    public void setStatusCT(String statusCT)
    {
        agentLicenseVO.setStatusCT(statusCT);
    } //-- void setStatusCT(java.lang.String)

    /**
     * Getter.
     * @return 
     */
    public Agent getAgent()
    {
        return agent;
    }

    /**
     * Setter.
     */
    public void setAgent(Agent agent)
    {
        this.agent = agent;
    }

    /**
      * Compare to existing data base table, using the ChangeProcessor.  For the ChangeVOs
      * returned, generate ChangeHistory.
      */
     public boolean checkForNonFinancialChanges()
     {
         ChangeProcessor changeProcessor = new ChangeProcessor();
         Change[] changes = changeProcessor.checkForChanges(this.getVO(), this.getVO().getVoShouldBeDeleted(), ConnectionFactory.EDITSOLUTIONS_POOL, null);
         boolean updateDone = false;

         if ((changes != null) && (changes.length > 0))
         {
             for (int i = 0; i < changes.length; i++)
             {
                 if (changes[i].getStatus() == (Change.ADDED))
                 {
                     if (!newAgentInd)
                     {
                         if (!updateDone)
                         {
//                             this.agentLicenseImpl.save(this);
                             hSave();
                             updateDone = true;
                         }

                         String property = "StatusCT";
                         changeProcessor.processForAdd(changes[i], this, this.getOperator(), property,  agentLicenseVO.getAgentFK());
                     }
                 }
                 else if (changes[i].getStatus() == (Change.DELETED))
                 {
                     String property = "StatusCT";
                     changeProcessor.processForDelete(changes[i], this, this.getOperator(), property, agentLicenseVO.getAgentFK());
                 }
             }
         }

         return updateDone;
     }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, AgentLicense.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, AgentLicense.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return AgentLicense.DATABASE;
    }
}
