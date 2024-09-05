/*
 * User: gfrosti
 * Date: Nov 4, 2003
 * Time: 9:55:40 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import edit.common.vo.AdditionalCompensationVO;
import edit.common.vo.VOObject;
import edit.common.EDITBigDecimal;
import edit.services.db.CRUDEntity;
import edit.services.db.hibernate.*;

public class AdditionalCompensation extends HibernateEntity implements CRUDEntity
{
    private AdditionalCompensationVO additionalCompensationVO;

    private AdditionalCompensationImpl additionalCompensationImpl;

    private AgentContract agentContract;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;

    public AdditionalCompensation()
    {
        additionalCompensationVO = new AdditionalCompensationVO();
        additionalCompensationImpl = new AdditionalCompensationImpl();
    }

    public AdditionalCompensation(AdditionalCompensationVO additionalCompensationVO)
    {
        this();
        this.additionalCompensationVO = additionalCompensationVO;
    }

    public AdditionalCompensation(long additionalCompensationPK) throws Exception
    {
        this();
        this.additionalCompensationImpl.load(this, additionalCompensationPK);
    }

    public VOObject getVO()
    {
        return additionalCompensationVO;
    }

    public void save()
    {
        additionalCompensationImpl.save(this);
    }

    public void delete() throws Exception
    {
        additionalCompensationImpl.delete(this);
    }

    public long getPK()
    {
        return additionalCompensationVO.getAdditionalCompensationPK();
    }

    public void setVO(VOObject voObject)
    {
        this.additionalCompensationVO = (AdditionalCompensationVO) voObject;
    }

    public boolean isNew()
    {
        return additionalCompensationImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return additionalCompensationImpl.cloneCRUDEntity(this);
    }

    /**
     * Getter.
     * @return
     */
    public String getADATypeCT()
    {
        return additionalCompensationVO.getADATypeCT();
    } //-- java.lang.String getADATypeCT()

    /**
     * Getter.
     * @return
     */
    public Long getAdditionalCompensationPK()
    {
        return SessionHelper.getPKValue(additionalCompensationVO.getAdditionalCompensationPK());
    } //-- long getAdditionalCompensationPK()

    /**
     * Getter.
     * @return
     */
    public Long getAgentContractFK()
    {
        return SessionHelper.getPKValue(additionalCompensationVO.getAgentContractFK());
    } //-- long getAgentContractFK()

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAnnualizedMax()
    {
        return SessionHelper.getEDITBigDecimal(additionalCompensationVO.getAnnualizedMax());
    } //-- java.math.BigDecimal getAnnualizedMax()

    /**
     * Getter.
     * @return
     */
    public String getBonusCommissionStatus()
    {
        return additionalCompensationVO.getBonusCommissionStatus();
    } //-- java.lang.String getBonusCommissionStatus()

    /**
     * Getter.
     * @return
     */
    public String getExpenseAllowanceStatus()
    {
        return additionalCompensationVO.getExpenseAllowanceStatus();
    } //-- java.lang.String getExpenseAllowanceStatus()

    /**
     * Getter.
     * @return
     */
    public String getNY91PercentStatus()
    {
        return additionalCompensationVO.getNY91PercentStatus();
    } //-- java.lang.String getNY91PercentStatus()


    /**
     * Setter.
     * @param ADATypeCT
     */
    public void setADATypeCT(String ADATypeCT)
    {
        additionalCompensationVO.setADATypeCT(ADATypeCT);
    } //-- void setADATypeCT(java.lang.String)

    /**
     * Setter.
     * @param additionalCompensationPK
     */
    public void setAdditionalCompensationPK(Long additionalCompensationPK)
    {
        additionalCompensationVO.setAdditionalCompensationPK(SessionHelper.getPKValue(additionalCompensationPK));
    } //-- void setAdditionalCompensationPK(long)

    /**
     * Setter.
     * @param agentContractFK
     */
    public void setAgentContractFK(Long agentContractFK)
    {
        additionalCompensationVO.setAgentContractFK(SessionHelper.getPKValue(agentContractFK));
    } //-- void setAgentContractFK(long)

    /**
     * Setter.
     * @param annualizedMax
     */
    public void setAnnualizedMax(EDITBigDecimal annualizedMax)
    {
        additionalCompensationVO.setAnnualizedMax(SessionHelper.getEDITBigDecimal(annualizedMax));
    } //-- void setAnnualizedMax(java.math.BigDecimal)

    /**
     * Setter.
     * @param bonusCommissionStatus
     */
    public void setBonusCommissionStatus(String bonusCommissionStatus)
    {
        additionalCompensationVO.setBonusCommissionStatus(bonusCommissionStatus);
    } //-- void setBonusCommissionStatus(java.lang.String)

    /**
     * Setter.
     * @param expenseAllowanceStatus
     */
    public void setExpenseAllowanceStatus(String expenseAllowanceStatus)
    {
        additionalCompensationVO.setExpenseAllowanceStatus(expenseAllowanceStatus);
    } //-- void setExpenseAllowanceStatus(java.lang.String)

    /**
     * Setter.
     * @param NY91PercentStatus
     */
    public void setNY91PercentStatus(String NY91PercentStatus)
    {
        additionalCompensationVO.setNY91PercentStatus(NY91PercentStatus);
    } //-- void setNY91PercentStatus(java.lang.String)

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, AdditionalCompensation.DATABASE);
    }

    /**
     * Getter.
     * @return
     */
    public AgentContract getAgentContract()
    {
        return agentContract;
    }

    /**
     * Setter.
     * @param agentContract
     */
    public void setAgentContract(AgentContract agentContract)
    {
        this.agentContract = agentContract;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return AdditionalCompensation.DATABASE;
    }

    public static AdditionalCompensation[] findByAgentContractPK(long agentContractPK) throws Exception
    {
        return AdditionalCompensationImpl.findByAgentContractPK(agentContractPK);
    }
}
