/*
 * User: dlataille
 * Date: Oct 9, 2007
 * Time: 8:38:44 AM

 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package staging;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import edit.common.vo.ProjectionsVO;

public class ProjectionArray extends HibernateEntity implements IStaging
{
    private Long projectionArrayPK;
    private Long segmentBaseFK;
    private String type;
    private int duration;
    private EDITBigDecimal guarNetPay;
    private EDITBigDecimal nonGuarNetPay;
    private EDITBigDecimal guarSurrender;
    private EDITBigDecimal nonGuarSurrender;
    private EDITBigDecimal guarPaidUpTerm;
    private EDITBigDecimal nonGuarPaidUpTerm;
    private EDITBigDecimal oneYearTerm;
    private EDITBigDecimal paidUpTermVested;
    private int vestingPeriod;
    private EDITBigDecimal premiumLoad;
    private EDITDate anniversaryDate;
    private int attainedAge;
    private EDITBigDecimal midPointPUT;
    private EDITBigDecimal midPointPremTerm;
    private int paidUpAge;
    private EDITBigDecimal deathBenefit;
    private EDITBigDecimal rpu;
    private EDITBigDecimal netPremiumCostIndex10;
    private EDITBigDecimal netPremiumCostIndex20;
    private EDITBigDecimal surrenderCostIndex10;
    private EDITBigDecimal surrenderCostIndex20;
    private EDITBigDecimal nonGuarDeathBenefit;
    private EDITBigDecimal guarNetPremiumCostIndex10;
    private EDITBigDecimal guarNetPremiumCostIndex20;
    private EDITBigDecimal guarSurrenderCostIndex10;
    private EDITBigDecimal guarSurrenderCostIndex20;
    private EDITBigDecimal guarModalPremium;
    private EDITBigDecimal guarLTCPremium;
    private EDITBigDecimal guarDecreaseTermDB;
    private EDITBigDecimal guaranteedEOBPremium;
    private EDITDate paidUpTermDate;

    private SegmentBase segmentBase;

    private ProjectionsVO projectionsVO;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.STAGING;

    public ProjectionArray()
    {

    }

    public ProjectionArray(ProjectionsVO projectionsVO)
    {
        this.projectionsVO = projectionsVO;
    }

    /**
     * Getter.
     * @return
     */
    public Long getProjectionArrayPK()
    {
        return projectionArrayPK;
    }

    /**
     * Setter.
     * @param projectionArrayPK
     */
    public void setProjectionArrayPK(Long projectionArrayPK)
    {
        this.projectionArrayPK = projectionArrayPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getSegmentBaseFK()
    {
        return segmentBaseFK;
    }

    /**
     * Setter.
     * @param segmentBaseFK
     */
    public void setSegmentBaseFK(Long segmentBaseFK)
    {
        this.segmentBaseFK = segmentBaseFK;
    }

    /**
     * Getter.
     * @return
     */
    public SegmentBase getSegmentBase()
    {
        return segmentBase;
    }

    /**
     * Setter.
     * @param segmentBase
     */
    public void setSegmentBase(SegmentBase segmentBase)
    {
        this.segmentBase = segmentBase;
    }

    /**
     * Getter.
     * @return
     */
    public String getType()
    {
        return type;
    }

    /**
     * Setter.
     * @param type
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * Getter.
     * @return
     */
    public int getDuration()
    {
        return duration;
    }

    /**
     * Setter.
     * @param duration
     */
    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuarNetPay()
    {
        return guarNetPay;
    }

    /**
     * Setter.
     * @param guarNetPay
     */
    public void setGuarNetPay(EDITBigDecimal guarNetPay)
    {
        this.guarNetPay = guarNetPay;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getNonGuarNetPay()
    {
        return nonGuarNetPay;
    }

    /**
     * Setter.
     * @param nonGuarNetPay
     */
    public void setNonGuarNetPay(EDITBigDecimal nonGuarNetPay)
    {
        this.nonGuarNetPay = nonGuarNetPay;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuarSurrender()
    {
        return guarSurrender;
    }

    /**
     * Setter.
     * @param guarSurrender
     */
    public void setGuarSurrender(EDITBigDecimal guarSurrender)
    {
        this.guarSurrender = guarSurrender;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getNonGuarSurrender()
    {
        return nonGuarSurrender;
    }

    /**
     * Setter.
     * @param nonGuarSurrender
     */
    public void setNonGuarSurrender(EDITBigDecimal nonGuarSurrender)
    {
        this.nonGuarSurrender = nonGuarSurrender;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuarPaidUpTerm()
    {
        return guarPaidUpTerm;
    }

    /**
     * Setter.
     * @param guarPaidUpTerm
     */
    public void setGuarPaidUpTerm(EDITBigDecimal guarPaidUpTerm)
    {
        this.guarPaidUpTerm = guarPaidUpTerm;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getNonGuarPaidUpTerm()
    {
        return nonGuarPaidUpTerm;
    }

    /**
     * Setter.
     * @param nonGuarPaidUpTerm
     */
    public void setNonGuarPaidUpTerm(EDITBigDecimal nonGuarPaidUpTerm)
    {
        this.nonGuarPaidUpTerm = nonGuarPaidUpTerm;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getOneYearTerm()
    {
        return oneYearTerm;
    }

    /**
     * Setter.
     * @param oneYearTerm
     */
    public void setOneYearTerm(EDITBigDecimal oneYearTerm)
    {
        this.oneYearTerm = oneYearTerm;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPaidUpTermVested()
    {
        return paidUpTermVested;
    }

    /**
     * Setter.
     * @param paidUpTermVested
     */
    public void setPaidUpTermVested(EDITBigDecimal paidUpTermVested)
    {
        this.paidUpTermVested = paidUpTermVested;
    }

    /**
     * Getter.
     * @return
     */
    public int getVestingPeriod()
    {
        return vestingPeriod;
    }

    /**
     * Setter.
     * @param vestingPeriod
     */
    public void setVestingPeriod(int vestingPeriod)
    {
        this.vestingPeriod = vestingPeriod;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getPremiumLoad()
    {
        return premiumLoad;
    }

    /**
     * Setter.
     * @param premiumLoad
     */
    public void setPremiumLoad(EDITBigDecimal premiumLoad)
    {
        this.premiumLoad = premiumLoad;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getAnniversaryDate()
    {
        return anniversaryDate;
    }

    /**
     * Setter.
     * @param anniversaryDate
     */
    public void setAnniversaryDate(EDITDate anniversaryDate)
    {
        this.anniversaryDate = anniversaryDate;
    }

    /**
     * Getter.
     * @return
     */
    public int getAttainedAge()
    {
        return attainedAge;
    }

    /**
     * Setter.
     * @param attainedAge
     */
    public void setAttainedAge(int attainedAge)
    {
        this.attainedAge = attainedAge;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getMidPointPUT()
    {
        return midPointPUT;
    }

    /**
     * Setter.
     * @param midPointPUT
     */
    public void setMidPointPUT(EDITBigDecimal midPointPUT)
    {
        this.midPointPUT = midPointPUT;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getMidPointPremTerm()
    {
        return midPointPremTerm;
    }

    /**
     * Setter.
     * @param midPointPremTerm
     */
    public void setMidPointPremTerm(EDITBigDecimal midPointPremTerm)
    {
        this.midPointPremTerm = midPointPremTerm;
    }

    /**
     * Getter.
     * @return
     */
    public int getPaidUpAge()
    {
        return paidUpAge;
    }

    /**
     * Setter.
     * @param paidUpAge
     */
    public void setPaidUpAge(int paidUpAge)
    {
        this.paidUpAge = paidUpAge;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getDeathBenefit()
    {
        return deathBenefit;
    }

    /**
     * Setter.
     * @param deathBenefit
     */
    public void setDeathBenefit(EDITBigDecimal deathBenefit)
    {
        this.deathBenefit = deathBenefit;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getRpu()
    {
        return rpu;
    }

    /**
     * Setter.
     * @param rpu
     */
    public void setRpu(EDITBigDecimal rpu)
    {
        this.rpu = rpu;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getNetPremiumCostIndex10()
    {
        return netPremiumCostIndex10;
    }

    /**
     * Setter.
     * @param netPremiumCostIndex10
     */
    public void setNetPremiumCostIndex10(EDITBigDecimal netPremiumCostIndex10)
    {
        this.netPremiumCostIndex10 = netPremiumCostIndex10;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getNetPremiumCostIndex20()
    {
        return netPremiumCostIndex20;
    }

    /**
     * Setter.
     * @param netPremiumCostIndex20
     */
    public void setNetPremiumCostIndex20(EDITBigDecimal netPremiumCostIndex20)
    {
        this.netPremiumCostIndex20 = netPremiumCostIndex20;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getSurrenderCostIndex10()
    {
        return surrenderCostIndex10;
    }

    /**
     * Setter.
     * @param surrenderCostIndex10
     */
    public void setSurrenderCostIndex10(EDITBigDecimal surrenderCostIndex10)
    {
        this.surrenderCostIndex10 = surrenderCostIndex10;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getSurrenderCostIndex20()
    {
        return surrenderCostIndex20;
    }

    /**
     * Setter.
     * @param surrenderCostIndex20
     */
    public void setSurrenderCostIndex20(EDITBigDecimal surrenderCostIndex20)
    {
        this.surrenderCostIndex20 = surrenderCostIndex20;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getNonGuarDeathBenefit()
    {
        return nonGuarDeathBenefit;
    }

    /**
     * Setter.
     * @param nonGuarDeathBenefit
     */
    public void setNonGuarDeathBenefit(EDITBigDecimal nonGuarDeathBenefit)
    {
        this.nonGuarDeathBenefit = nonGuarDeathBenefit;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuarNetPremiumCostIndex10()
    {
        return guarNetPremiumCostIndex10;
    }

    /**
     * Setter.
     * @param guarNetPremiumCostIndex10
     */
    public void setGuarNetPremiumCostIndex10(EDITBigDecimal guarNetPremiumCostIndex10)
    {
        this.guarNetPremiumCostIndex10 = guarNetPremiumCostIndex10;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuarNetPremiumCostIndex20()
    {
        return guarNetPremiumCostIndex20;
    }

    /**
     * Setter.
     * @param guarNetPremiumCostIndex20
     */
    public void setGuarNetPremiumCostIndex20(EDITBigDecimal guarNetPremiumCostIndex20)
    {
        this.guarNetPremiumCostIndex20 = guarNetPremiumCostIndex20;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuarSurrenderCostIndex10()
    {
        return guarSurrenderCostIndex10;
    }

    /**
     * Setter.
     * @param guarSurrenderCostIndex10
     */
    public void setGuarSurrenderCostIndex10(EDITBigDecimal guarSurrenderCostIndex10)
    {
        this.guarSurrenderCostIndex10 = guarSurrenderCostIndex10;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuarSurrenderCostIndex20()
    {
        return guarSurrenderCostIndex20;
    }

    /**
     * Setter.
     * @param guarSurrenderCostIndex20
     */
    public void setGuarSurrenderCostIndex20(EDITBigDecimal guarSurrenderCostIndex20)
    {
        this.guarSurrenderCostIndex20 = guarSurrenderCostIndex20;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuarModalPremium()
    {
        return guarModalPremium;
    }

    /**
     * Setter.
     * @param guarModalPremium
     */
    public void setGuarModalPremium(EDITBigDecimal guarModalPremium)
    {
        this.guarModalPremium = guarModalPremium;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuarLTCPremium()
    {
        return guarLTCPremium;
    }

    /**
     * Setter.
     * @param guarLTCPremium
     */
    public void setGuarLTCPremium(EDITBigDecimal guarLTCPremium)
    {
        this.guarLTCPremium = guarLTCPremium;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuarDecreaseTermDB()
    {
        return guarDecreaseTermDB;
    }

    /**
     * Setter.
     * @param guarDecreaseTermDB
     */
    public void setGuarDecreaseTermDB(EDITBigDecimal guarDecreaseTermDB)
    {
        this.guarDecreaseTermDB = guarDecreaseTermDB;
    }
    
    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getGuaranteedEOBPremium()
    {
        return guaranteedEOBPremium;
    }

    /**
     * Setter.
     * @param guaranteedEOBPremium
     */
    public void setGuaranteedEOBPremium(EDITBigDecimal guaranteedEOBPremium)
    {
        this.guaranteedEOBPremium = guaranteedEOBPremium;
    }

    public EDITDate getPaidUpTermDate()
    {
        return paidUpTermDate;
    }

    public void setPaidUpTermDate(EDITDate paidUpTermDate)
    {
        this.paidUpTermDate = paidUpTermDate;
    }

    public String getDatabase()
    {
        return ProjectionArray.DATABASE;
    }

    public StagingContext stage(StagingContext stagingContext, String database)
    {
        this.setSegmentBase(stagingContext.getCurrentSegmentBase());
        this.setType(projectionsVO.getType());
        this.setDuration(projectionsVO.getDuration());
        this.setGuarNetPay(new EDITBigDecimal(projectionsVO.getGuarNetPay()));
        this.setNonGuarNetPay(new EDITBigDecimal(projectionsVO.getNonGuarNetPay()));
        this.setGuarSurrender(new EDITBigDecimal(projectionsVO.getGuarSurrender()));
        this.setNonGuarSurrender(new EDITBigDecimal(projectionsVO.getNonGuarSurrender()));
        this.setGuarPaidUpTerm(new EDITBigDecimal(projectionsVO.getGuarPaidUpTerm()));
        this.setNonGuarPaidUpTerm(new EDITBigDecimal(projectionsVO.getNonGuarPaidUpTerm()));
        this.setOneYearTerm(new EDITBigDecimal(projectionsVO.getOneYearTerm()));
        this.setPaidUpTermVested(new EDITBigDecimal(projectionsVO.getPaidUpTermVested()));
        this.setVestingPeriod(projectionsVO.getVestingPeriod());
        this.setPremiumLoad(new EDITBigDecimal(projectionsVO.getPremiumLoad()));
        if (projectionsVO.getAnniversaryDate() != null)
        {
            this.setAnniversaryDate(new EDITDate(projectionsVO.getAnniversaryDate()));
        }
        this.setAttainedAge(projectionsVO.getAttainedAge());
        this.setMidPointPUT(new EDITBigDecimal(projectionsVO.getMidPointPUT()));
        this.setMidPointPremTerm(new EDITBigDecimal(projectionsVO.getMidPointPremTerm()));
        this.setPaidUpAge(projectionsVO.getPaidUpAge());
        this.setDeathBenefit(new EDITBigDecimal(projectionsVO.getDeathBenefit()));
        this.setRpu(new EDITBigDecimal(projectionsVO.getRPU()));
        this.setNetPremiumCostIndex10(new EDITBigDecimal(projectionsVO.getNetPremiumCostIndex10()));
        this.setNetPremiumCostIndex20(new EDITBigDecimal(projectionsVO.getNetPremiumCostIndex20()));
        this.setSurrenderCostIndex10(new EDITBigDecimal(projectionsVO.getSurrenderCostIndex10()));
        this.setSurrenderCostIndex20(new EDITBigDecimal(projectionsVO.getSurrenderCostIndex20()));
        this.setNonGuarDeathBenefit(new EDITBigDecimal(projectionsVO.getNonGuarDeathBenefit()));
        this.setGuarNetPremiumCostIndex10(new EDITBigDecimal(projectionsVO.getGuarNetPremiumCostIndex10()));
        this.setGuarNetPremiumCostIndex20(new EDITBigDecimal(projectionsVO.getGuarNetPremiumCostIndex20()));
        this.setGuarSurrenderCostIndex10(new EDITBigDecimal(projectionsVO.getGuarSurrenderCostIndex10()));
        this.setGuarSurrenderCostIndex20(new EDITBigDecimal(projectionsVO.getGuarSurrenderCostIndex20()));
        this.setGuarModalPremium(new EDITBigDecimal(projectionsVO.getGuarModalPremium()));
        this.setGuarLTCPremium(new EDITBigDecimal(projectionsVO.getGuarLTCPremium()));
        this.setGuarDecreaseTermDB(new EDITBigDecimal(projectionsVO.getGuarDecreaseTermDB()));
        if (projectionsVO.getPaidUpTermDate() != null)
        {
            this.setPaidUpTermDate(new EDITDate(projectionsVO.getPaidUpTermDate()));
        }
        if (projectionsVO.getGuaranteedEOBPremium() != null)
        {
            this.setGuaranteedEOBPremium(new EDITBigDecimal(projectionsVO.getGuaranteedEOBPremium()));
        }
        
        stagingContext.getCurrentSegmentBase().addProjectionArray(this);

        SessionHelper.saveOrUpdate(this, database);

        return stagingContext;
    }
}


