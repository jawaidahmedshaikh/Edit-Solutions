/*
 * User: cgleason
 * Date: Jan 11, 2005
 * Time: 8:57:11 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import contract.dm.dao.*;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;


public class RequiredMinDistribution extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;
    private RequiredMinDistributionVO requiredMinDistributionVO;
    private Segment segment;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a RequiredMinDistribution entity with a default RequiredMinDistributionVO.
     */
    public RequiredMinDistribution()
    {
        init();
    }

    /**
     * Instantiates a RequiredMinDistribution entity with a supplied RequiredMinDistributionVO.
     *
     * @param requiredMinDistributionVO
     */
    public RequiredMinDistribution(RequiredMinDistributionVO requiredMinDistributionVO)
    {
        init();

        this.requiredMinDistributionVO = requiredMinDistributionVO;
    }

    /**
     * Getter.
     * @return
     */
    public Segment getSegment()
    {
        return segment;
    }

    /**
     * Setter.
     * @param segment
     */
    public void setSegment(Segment segment)
    {
        this.segment = segment;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (requiredMinDistributionVO == null)
        {
            requiredMinDistributionVO = new RequiredMinDistributionVO();
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
        return requiredMinDistributionVO;
    }

    public EDITBigDecimal getAmount()
    {
        return SessionHelper.getEDITBigDecimal(requiredMinDistributionVO.getAmount());
    }

    //-- java.math.BigDecimal getAmount() 
    public EDITDate getAnnualDate()
    {
        return SessionHelper.getEDITDate(requiredMinDistributionVO.getAnnualDate());
    }

    //-- java.lang.String getAnnualDate() 
    public EDITBigDecimal getCalculatedRMDAmount()
    {
        return SessionHelper.getEDITBigDecimal(requiredMinDistributionVO.getCalculatedRMDAmount());
    }

    //-- java.math.BigDecimal getCalculatedRMDAmount() 
    public String getElectionCT()
    {
        return requiredMinDistributionVO.getElectionCT();
    }

    //-- java.lang.String getElectionCT() 
    public EDITDate getFirstPayDate()
    {
        return SessionHelper.getEDITDate(requiredMinDistributionVO.getFirstPayDate());
    }

    //-- java.lang.String getFirstPayDate() 
    public String getFrequencyCT()
    {
        return requiredMinDistributionVO.getFrequencyCT();
    }

    //-- java.lang.String getFrequencyCT() 
    public EDITBigDecimal getInitialRMDAmount()
    {
        return SessionHelper.getEDITBigDecimal(requiredMinDistributionVO.getInitialRMDAmount());
    }

    public EDITBigDecimal getInitialCYAccumValue()
    {
        return SessionHelper.getEDITBigDecimal(requiredMinDistributionVO.getInitialCYAccumValue());
    }

    //-- java.math.BigDecimal getInitialRMDAmount()
    public EDITDate getLastNotificationDate()
    {
        return SessionHelper.getEDITDate(requiredMinDistributionVO.getLastNotificationDate());
    }

    //-- java.lang.String getLastNotificationDate() 
    public EDITBigDecimal getLastPaymentAmount()
    {
        return SessionHelper.getEDITBigDecimal(requiredMinDistributionVO.getLastPaymentAmount());
    }

    //-- java.math.BigDecimal getLastPaymentAmount() 
    public EDITDate getLastPaymentDate()
    {
        return SessionHelper.getEDITDate(requiredMinDistributionVO.getLastPaymentDate());
    }

    //-- java.lang.String getLastPaymentDate() 
    public String getLifeExpectancyMultipleCT()
    {
        return requiredMinDistributionVO.getLifeExpectancyMultipleCT();
    }

    //-- java.lang.String getLifeExpectancyMultipleCT() 
    public EDITBigDecimal getModalOverrideAmount()
    {
        return SessionHelper.getEDITBigDecimal(requiredMinDistributionVO.getModalOverrideAmount());
    }

    //-- java.math.BigDecimal getModalOverrideAmount() 
    public EDITDate getNextPaymentDate()
    {
        return SessionHelper.getEDITDate(requiredMinDistributionVO.getNextPaymentDate());
    }

    //-- java.lang.String getNextPaymentDate() 
    public Long getRequiredMinDistributionPK()
    {
        return SessionHelper.getPKValue(requiredMinDistributionVO.getRequiredMinDistributionPK());
    }

    //-- long getRequiredMinDistributionPK() 
    public EDITDate getSeventyAndHalfDate()
    {
        return SessionHelper.getEDITDate(requiredMinDistributionVO.getSeventyAndHalfDate());
    }

    //-- java.lang.String getSeventyAndHalfDate() 
    public void setAmount(EDITBigDecimal amount)
    {
        requiredMinDistributionVO.setAmount(SessionHelper.getEDITBigDecimal(amount));
    }

    //-- void setAmount(java.math.BigDecimal) 
    public void setAnnualDate(EDITDate annualDate)
    {
        requiredMinDistributionVO.setAnnualDate(SessionHelper.getEDITDate(annualDate));
    }

    //-- void setAnnualDate(java.lang.String) 
    public void setCalculatedRMDAmount(EDITBigDecimal calculatedRMDAmount)
    {
        requiredMinDistributionVO.setCalculatedRMDAmount(SessionHelper.getEDITBigDecimal(calculatedRMDAmount));
    }

    //-- void setCalculatedRMDAmount(java.math.BigDecimal) 
    public void setElectionCT(String electionCT)
    {
        requiredMinDistributionVO.setElectionCT(electionCT);
    }

    //-- void setElectionCT(java.lang.String) 
    public void setFirstPayDate(EDITDate firstPayDate)
    {
        requiredMinDistributionVO.setFirstPayDate(SessionHelper.getEDITDate(firstPayDate));
    }

    //-- void setFirstPayDate(java.lang.String) 
    public void setFrequencyCT(String frequencyCT)
    {
        requiredMinDistributionVO.setFrequencyCT(frequencyCT);
    }

    //-- void setFrequencyCT(java.lang.String) 
    public void setInitialRMDAmount(EDITBigDecimal initialRMDAmount)
    {
        requiredMinDistributionVO.setInitialRMDAmount(SessionHelper.getEDITBigDecimal(initialRMDAmount));
    }

    public void setInitialCYAccumValue(EDITBigDecimal initialCYAccumValue)
    {
       requiredMinDistributionVO.setInitialCYAccumValue(SessionHelper.getEDITBigDecimal(initialCYAccumValue));
    }

    //-- void setInitialRMDAmount(java.math.BigDecimal)
    public void setLastNotificationDate(EDITDate lastNotificationDate)
    {
        requiredMinDistributionVO.setLastNotificationDate(SessionHelper.getEDITDate(lastNotificationDate));
    }

    //-- void setLastNotificationDate(java.lang.String) 
    public void setLastPaymentAmount(EDITBigDecimal lastPaymentAmount)
    {
        requiredMinDistributionVO.setLastPaymentAmount(SessionHelper.getEDITBigDecimal(lastPaymentAmount));
    }

    //-- void setLastPaymentAmount(java.math.BigDecimal) 
    public void setLastPaymentDate(EDITDate lastPaymentDate)
    {
        requiredMinDistributionVO.setLastPaymentDate(SessionHelper.getEDITDate(lastPaymentDate));
    }

    //-- void setLastPaymentDate(java.lang.String) 
    public void setLifeExpectancyMultipleCT(String lifeExpectancyMultipleCT)
    {
        requiredMinDistributionVO.setLifeExpectancyMultipleCT(lifeExpectancyMultipleCT);
    }

    //-- void setLifeExpectancyMultipleCT(java.lang.String) 
    public void setModalOverrideAmount(EDITBigDecimal modalOverrideAmount)
    {
        requiredMinDistributionVO.setModalOverrideAmount(SessionHelper.getEDITBigDecimal(modalOverrideAmount));
    }

    //-- void setModalOverrideAmount(java.math.BigDecimal) 
    public void setNextPaymentDate(EDITDate nextPaymentDate)
    {
        requiredMinDistributionVO.setNextPaymentDate(SessionHelper.getEDITDate(nextPaymentDate));
    }

    //-- void setNextPaymentDate(java.lang.String) 
    public void setRequiredMinDistributionPK(Long requiredMinDistributionPK)
    {
        requiredMinDistributionVO.setRequiredMinDistributionPK(SessionHelper.getPKValue(requiredMinDistributionPK));
    }

    //-- void setRequiredMinDistributionPK(long) 
    public void setSeventyAndHalfDate(EDITDate seventyAndHalfDate)
    {
        requiredMinDistributionVO.setSeventyAndHalfDate(SessionHelper.getEDITDate(seventyAndHalfDate));
    }

    //-- void setSeventyAndHalfDate(java.lang.String) 

	/**
     * Setter.
     * @param rmdStartDate
     */
    public void setRMDStartDate(EDITDate rmdStartDate)
    {
        requiredMinDistributionVO.setRMDStartDate(SessionHelper.getEDITDate(rmdStartDate));
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return requiredMinDistributionVO.getRequiredMinDistributionPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.requiredMinDistributionVO = (RequiredMinDistributionVO) voObject;
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
        return RequiredMinDistribution.DATABASE;
    }

    /**
     * Finder.
     *
     * @param requiredMinDistributionPK
     */
    public static final RequiredMinDistribution findByPK(long requiredMinDistributionPK)
    {
        RequiredMinDistribution requiredMinDistribution = null;

        RequiredMinDistributionVO[] requiredMinDistributionVOs = new RequiredMinDistributionDAO().findByPK(requiredMinDistributionPK);

        if (requiredMinDistributionVOs != null)
        {
            requiredMinDistribution = new RequiredMinDistribution(requiredMinDistributionVOs[0]);
        }

        return requiredMinDistribution;
    }

    public Long getSegmentFK()
    {
        return SessionHelper.getPKValue(requiredMinDistributionVO.getSegmentFK());
    }
     //-- long getSegmentFK() 

    public void setSegmentFK(Long segmentFK)
    {
        requiredMinDistributionVO.setSegmentFK(SessionHelper.getPKValue(segmentFK));
    }
     //-- void setSegmentFK(long)


    /**
     * getter
     * @return
     */
    public EDITDate getRMDStartDate()
    {
        return SessionHelper.getEDITDate(requiredMinDistributionVO.getRMDStartDate());
    } //-- java.lang.String getRMDStartDate()

    /**
     * getter
     * @return
     */
    public EDITDate getDeceasedSeventyAndHalfDate()
    {
        return SessionHelper.getEDITDate(requiredMinDistributionVO.getDeceasedSeventyAndHalfDate());
    } //-- java.lang.String getDeceasedSeventyAndHalfDate()

    /**
     * getter
     * @return
     */
    public String getBeneficiaryStatusCT()
    {
        return requiredMinDistributionVO.getBeneficiaryStatusCT();
    } //-- java.lang.String getBeneficiaryStatus()

    /**
     * setter
     * @param beneficiaryStatus
     */
    public void setBeneficiaryStatusCT(String beneficiaryStatus)
    {
        requiredMinDistributionVO.setBeneficiaryStatusCT(beneficiaryStatus);
    } //-- void setBeneficiaryStatus(java.lang.String)

    /**
     * setter
     * @param deceasedSeventyAndHalfDate
     */
    public void setDeceasedSeventyAndHalfDate(EDITDate deceasedSeventyAndHalfDate)
    {
        requiredMinDistributionVO.setDeceasedSeventyAndHalfDate(SessionHelper.getEDITDate(deceasedSeventyAndHalfDate));
    } //-- void setDeceasedSeventyAndHalfDate(java.lang.String)

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, RequiredMinDistribution.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, RequiredMinDistribution.DATABASE);
    }
}
