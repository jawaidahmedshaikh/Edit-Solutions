package agent;

import edit.common.vo.VOObject;
import edit.common.vo.VestingVO;
import edit.common.*;
import edit.services.db.CRUDEntity;
import edit.services.db.hibernate.*;

/**
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Dec 5, 2003
 * Time: 11:24:16 AM
 * To change this template use Options | File Templates.
 */
public class Vesting extends HibernateEntity implements CRUDEntity
{
    private VestingVO vestingVO;

    private VestingImpl vestingImpl;

    private Agent agent;

    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public Vesting()
    {
        this.vestingVO = new VestingVO();
        this.vestingImpl = new VestingImpl();
    }

    public Vesting(long vestingPK) throws Exception
    {
        this();
        this.vestingImpl.load(this, vestingPK);
    }

    public Vesting(VestingVO vestingVO)
    {
        this();
        this.vestingVO = vestingVO;
    }

    public void save()
    {
        this.vestingImpl.save(this);
    }

    public void delete() throws Exception
    {
        this.vestingImpl.delete(this);
    }

    public void associateAgent(Agent agent)
    {
//        this.vestingVO.setAgentFK(agent.getPK());
        this.setAgent(agent);
        hSave();
    }

    public VOObject getVO()
    {
        return vestingVO;
    }

    public long getPK()
    {
        return vestingVO.getVestingPK();
    }

    public void setVO(VOObject voObject)
    {
        this.vestingVO = (VestingVO) voObject;
    }

    public boolean isNew()
    {
        return this.vestingImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return this.vestingImpl.cloneCRUDEntity(this);
    }

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
     * Getter
     * @return
     */
    public Long getAgentFK()
    {
        return vestingVO.getAgentFK();
    } //-- long getAgentFK()

    /**
     * Getter
     * @return
     */
    public String getTermVestingBasisCT()
    {
        return vestingVO.getTermVestingBasisCT();
    } //-- java.lang.String getTermVestingBasisCT()

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getTermVestingPercent()
    {
        return SessionHelper.getEDITBigDecimal(vestingVO.getTermVestingPercent());
    } //-- java.math.BigDecimal getTermVestingPercent()

    /**
     * Getter
     * @return
     */
    public String getTermVestingStatusCT()
    {
        return vestingVO.getTermVestingStatusCT();
    } //-- java.lang.String getTermVestingStatusCT()

    /**
     * Getter.
     * @return
     */
    public int getVestingDuration()
    {
        return vestingVO.getVestingDuration();
    }

    /**
     * Getter
     * @return
     */
    public Long getVestingPK()
    {
        return vestingVO.getVestingPK();
    } //-- long getVestingPK()

    /**
     *
     * @param agentFK
     */
    public void setAgentFK(Long agentFK)
    {
        vestingVO.setAgentFK(agentFK);
    } //-- void setAgentFK(long)

    /**
     * Setter
     * @param termVestingBasisCT
     */
    public void setTermVestingBasisCT(String termVestingBasisCT)
    {
        vestingVO.setTermVestingBasisCT(termVestingBasisCT);
    } //-- void setTermVestingBasisCT(java.lang.String)

    /**
     * Setter
     * @param termVestingPercent
     */
    public void setTermVestingPercent(EDITBigDecimal termVestingPercent)
    {
        vestingVO.setTermVestingPercent(SessionHelper.getEDITBigDecimal(termVestingPercent));
    } //-- void setTermVestingPercent(java.math.BigDecimal)

    /**
     * Setter
     * @param termVestingStatusCT
     */
    public void setTermVestingStatusCT(String termVestingStatusCT)
    {
        vestingVO.setTermVestingStatusCT(termVestingStatusCT);
    } //-- void setTermVestingStatusCT(java.lang.String)

    /**
     * Setter.
     * @param vestingDuration
     */
    public void setVestingDuration(int vestingDuration)
    {
        vestingVO.setVestingDuration(vestingDuration);
    }

    /**
     * Setter
     * @param vestingPK
     */
    public void setVestingPK(Long vestingPK)
    {
        vestingVO.setVestingPK(vestingPK);
    } //-- void setVestingPK(long)

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Vesting.DATABASE;
    }

    /**
      * Save the entity using Hibernate
      */
     public void hSave()
     {
         SessionHelper.saveOrUpdate(this, Vesting.DATABASE);
     }

     /**
      * Delete the entity using Hibernate
      */
     public void hDelete()
     {
         SessionHelper.delete(this, Vesting.DATABASE);
     }

}
