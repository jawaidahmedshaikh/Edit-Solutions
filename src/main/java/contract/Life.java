/*
 * User: cgleason
 * Date: Nov 3, 2004
 * Time: 11:09:42 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import contract.dm.dao.*;

import edit.common.*;

import edit.common.exceptions.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import java.util.*;

import org.hibernate.Session;

@SuppressWarnings("serial")
public class Life extends HibernateEntity implements CRUDEntity
{
    public static final String DEATHBENEFITOPTIONCT_LEVEL = "Level";
    public static final String DEATHBENEFITOPTIONCT_INCREASING = "Increasing";
    public static final Object OPTION7702CT_GUIDELINE = "Guideline";
    public static final Object OPTION7702CT_CVAT = "CVAT";
    private CRUDEntityImpl crudEntityImpl;
    private LifeVO lifeVO;
    public String deathBenefitOptionCT = null;
    public int riderNumber = 0;
    private Segment segment;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a Life entity with a default LifeVO.
     */
    public Life()
    {
        init();
    }

    /**
     * Instantiates a Life entity with a LifeVO retrieved from persistence.
     * @param segmentPK
     */
    public Life(long lifePK)
    {
        init();

        crudEntityImpl.load(this, lifePK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Instantiates a Life entity with a supplied LifeVO.
     * @param LifeVO
     */
    public Life(LifeVO lifeVO)
    {
        init();

        this.lifeVO = lifeVO;
    }

    /**
      * Instantiates a Life entity with a supplied LifeVO and Segment.
      * @param LifeVO
      */
    public Life(LifeVO lifeVO, Segment segment)
    {
        init();

        this.lifeVO = lifeVO;
        this.segment = segment;
    }

    public void setSegment(Segment segment)
    {
        this.segment = segment;
    }

    public Segment getSegment()
    {
        return segment;
    }

    public Long getLifePK()
    {
        return SessionHelper.getPKValue(lifeVO.getLifePK());
    }

    public void setLifePK(Long lifePK)
    {
        this.lifeVO.setLifePK(SessionHelper.getPKValue(lifePK));
    }

    public EDITBigDecimal getCumGuidelineLevelPremium()
    {
        return SessionHelper.getEDITBigDecimal(lifeVO.getCumGuidelineLevelPremium());
    }

    //-- java.math.BigDecimal getCumGuidelineLevelPremium() 
    public EDITBigDecimal getGuidelineLevelPremium()
    {
        return SessionHelper.getEDITBigDecimal(lifeVO.getGuidelineLevelPremium());
    }

    //-- java.math.BigDecimal getGuidelineLevelPremium() 
    public EDITBigDecimal getGuidelineSinglePremium()
    {
        return SessionHelper.getEDITBigDecimal(lifeVO.getGuidelineSinglePremium());
    }

    //-- java.math.BigDecimal getGuidelineSinglePremium() 
    public EDITDate getLapsePendingDate()
    {
        return SessionHelper.getEDITDate(lifeVO.getLapsePendingDate());
    }

    //-- java.lang.String getLapsePendingDate() 
    public EDITBigDecimal getMECGuidelineLevelPremium()
    {
        return SessionHelper.getEDITBigDecimal(lifeVO.getMECGuidelineLevelPremium());
    }

    //-- java.math.BigDecimal getMECGuidelineLevelPremium() 
    public EDITBigDecimal getMECGuidelineSinglePremium()
    {
        return SessionHelper.getEDITBigDecimal(lifeVO.getMECGuidelineSinglePremium());
    }

    //-- java.math.BigDecimal getMECGuidelineSinglePremium() 
    public EDITBigDecimal getMaximumNetAmountAtRisk()
    {
        return SessionHelper.getEDITBigDecimal(lifeVO.getMaximumNetAmountAtRisk());
    }

    //-- java.math.BigDecimal getMaximumNetAmountAtRisk() 
    public EDITDate getPaidToDate()
    {
        return SessionHelper.getEDITDate(lifeVO.getPaidToDate());
    }

    //-- java.lang.String getPaidToDate() 
    public String getPendingDBOChangeInd()
    {
        return lifeVO.getPendingDBOChangeInd();
    }

    //-- java.lang.String getPendingDBOChangeInd() 
    public String getStartNew7PayIndicator()
    {
        return lifeVO.getStartNew7PayIndicator();
    }

    //-- java.lang.String getStartNew7PayIndicator() 
    public EDITBigDecimal getTamra()
    {
        return SessionHelper.getEDITBigDecimal(lifeVO.getTamra());
    }

    public EDITBigDecimal getTamraInitAdjValue()
    {
        return SessionHelper.getEDITBigDecimal(lifeVO.getTamraInitAdjValue());
    }

    //-- java.math.BigDecimal getTamra() 
    public EDITDate getTamraStartDate()
    {
        return SessionHelper.getEDITDate(lifeVO.getTamraStartDate());
    }

    public EDITDate getMAPEndDate()
    {
        return SessionHelper.getEDITDate(lifeVO.getMAPEndDate());
    }

    /**
     * Getter.
     * @return
     */
    public int getTerm()
    {
        return lifeVO.getTerm();
    }

    public void setCumGuidelineLevelPremium(EDITBigDecimal cumGuidelineLevelPremium)
    {
        lifeVO.setCumGuidelineLevelPremium(SessionHelper.getEDITBigDecimal(cumGuidelineLevelPremium));
    }

    //-- void setCumGuidelineLevelPremium(java.math.BigDecimal) 
    public void setDeathBenefitOptionCT(String deathBenefitOptionCT)
    {
        lifeVO.setDeathBenefitOptionCT(deathBenefitOptionCT);
    }

    //-- void setDeathBenefitOptionCT(java.lang.String) 
    public void setFaceAmount(EDITBigDecimal faceAmount)
    {
        lifeVO.setFaceAmount(SessionHelper.getEDITBigDecimal(faceAmount));
    }

    //-- void setFaceAmount(java.math.BigDecimal) 
    public void setGuidelineSinglePremium(EDITBigDecimal guidelineSinglePremium)
    {
        lifeVO.setGuidelineSinglePremium(SessionHelper.getEDITBigDecimal(guidelineSinglePremium));
    }

    //-- void setGuidelineSinglePremium(java.math.BigDecimal) 
    public void setGuidelineLevelPremium(EDITBigDecimal guidelineLevelPremium)
    {
        lifeVO.setGuidelineLevelPremium(SessionHelper.getEDITBigDecimal(guidelineLevelPremium));
    }

    //-- void setGuidelineLevelPremium(java.math.BigDecimal) 
    public void setLapseDate(EDITDate lapseDate)
    {
        lifeVO.setLapseDate(SessionHelper.getEDITDate(lapseDate));
    }

    //-- void setLapseDate(java.lang.String) 
    public void setLapsePendingDate(EDITDate lapsePendingDate)
    {
        lifeVO.setLapsePendingDate(SessionHelper.getEDITDate(lapsePendingDate));
    }

    //-- void setLapsePendingDate(java.lang.String) 
    public void setMECDate(EDITDate MECDate)
    {
        lifeVO.setMECDate(SessionHelper.getEDITDate(MECDate));
    }

    //-- void setMECDate(java.lang.String) 
    public void setMECGuidelineLevelPremium(EDITBigDecimal MECGuidelineLevelPremium)
    {
        lifeVO.setMECGuidelineLevelPremium(SessionHelper.getEDITBigDecimal(MECGuidelineLevelPremium));
    }

    //-- void setMECGuidelineLevelPremium(java.math.BigDecimal) 
    public void setMECGuidelineSinglePremium(EDITBigDecimal MECGuidelineSinglePremium)
    {
        lifeVO.setMECGuidelineSinglePremium(SessionHelper.getEDITBigDecimal(MECGuidelineSinglePremium));
    }

    //-- void setMECGuidelineSinglePremium(java.math.BigDecimal) 
    public void setMECStatusCT(String MECStatusCT)
    {
        lifeVO.setMECStatusCT(MECStatusCT);
    }

    //-- void setMECStatusCT(java.lang.String) 
    public void setMaximumNetAmountAtRisk(EDITBigDecimal maximumNetAmountAtRisk)
    {
        lifeVO.setMaximumNetAmountAtRisk(SessionHelper.getEDITBigDecimal(maximumNetAmountAtRisk));
    }

    //-- void setMaximumNetAmountAtRisk(java.math.BigDecimal) 
    public void setOption7702CT(String option7702CT)
    {
        lifeVO.setOption7702CT(option7702CT);
    }

    //-- void setOption7702CT(java.lang.String) 
    public void setPaidToDate(EDITDate paidToDate)
    {
        lifeVO.setPaidToDate(SessionHelper.getEDITDate(paidToDate));
    }

    //-- void setPaidToDate(java.lang.String) 
    public void setPendingDBOChangeInd(String pendingDBOChangeInd)
    {
        lifeVO.setPendingDBOChangeInd(pendingDBOChangeInd);
    }

    //-- void setPendingDBOChangeInd(java.lang.String) 
    public void setStartNew7PayIndicator(String startNew7PayIndicator)
    {
        lifeVO.setStartNew7PayIndicator(startNew7PayIndicator);
    }

    //-- void setStartNew7PayIndicator(java.lang.String) 
    public void setTamra(EDITBigDecimal tamra)
    {
        lifeVO.setTamra(SessionHelper.getEDITBigDecimal(tamra));
    }

    public void setTamraInitAdjValue(EDITBigDecimal tamra)
    {
        lifeVO.setTamraInitAdjValue(SessionHelper.getEDITBigDecimal(tamra));
    }

    //-- void setTamra(java.math.BigDecimal) 
    public void setTamraStartDate(EDITDate tamraStartDate)
    {
        lifeVO.setTamraStartDate(SessionHelper.getEDITDate(tamraStartDate));
    }

    public void setMAPEndDate(EDITDate MAPEndDate)
    {
        lifeVO.setMAPEndDate(SessionHelper.getEDITDate(MAPEndDate));
    }

    /**
     * Setter.
     * @param term
     */
    public void setTerm(int term)
    {
        lifeVO.setTerm(term);
    }

    public String getNonForfeitureOptionCT()
    {
        return lifeVO.getNonForfeitureOptionCT();
    }

    public void setNonForfeitureOptionCT(String nonForfeitureOptionCT)
    {
        lifeVO.setNonForfeitureOptionCT(nonForfeitureOptionCT);
    }

    public EDITBigDecimal getDueAndUnpaid()
    {
        return SessionHelper.getEDITBigDecimal(lifeVO.getDueAndUnpaid());
    }

    public void setDueAndUnpaid(EDITBigDecimal dueAndUnpaid)
    {
        lifeVO.setDueAndUnpaid(SessionHelper.getEDITBigDecimal(dueAndUnpaid));
    }

    public EDITBigDecimal getGuarPaidUpTerm()
    {
        return SessionHelper.getEDITBigDecimal(lifeVO.getGuarPaidUpTerm());
    }

    public void setGuarPaidUpTerm(EDITBigDecimal guarPaidUpTerm)
    {
        lifeVO.setGuarPaidUpTerm(SessionHelper.getEDITBigDecimal(guarPaidUpTerm));
    }

    public EDITBigDecimal getNonGuarPaidUpTerm()
    {
        return SessionHelper.getEDITBigDecimal(lifeVO.getNonGuarPaidUpTerm());
    }

    public void setNonGuarPaidUpTerm(EDITBigDecimal nonGuarPaidUpTerm)
    {
        lifeVO.setNonGuarPaidUpTerm(SessionHelper.getEDITBigDecimal(nonGuarPaidUpTerm));
    }

    public EDITBigDecimal getOneYearTerm()
    {
        return SessionHelper.getEDITBigDecimal(lifeVO.getOneYearTerm());
    }

    public void setOneYearTerm(EDITBigDecimal oneYearTerm)
    {
        lifeVO.setOneYearTerm(SessionHelper.getEDITBigDecimal(oneYearTerm));
    }

    public EDITBigDecimal getCurrentDeathBenefit()
    {
        return SessionHelper.getEDITBigDecimal(lifeVO.getCurrentDeathBenefit());
    } //-- java.math.BigDecimal getCurrentDeathBenefit()

    public EDITBigDecimal getEndowmentCredit()
    {
        return SessionHelper.getEDITBigDecimal(lifeVO.getEndowmentCredit());
    } //-- java.math.BigDecimal getEndowmentCredit()

    public EDITBigDecimal getExcessInterestCredit()
    {
        return SessionHelper.getEDITBigDecimal(lifeVO.getExcessInterestCredit());
    } //-- java.math.BigDecimal getExcessInterestCredit()

    public EDITBigDecimal getMortalityCredit()
    {
        return SessionHelper.getEDITBigDecimal(lifeVO.getMortalityCredit());
    } //-- java.math.BigDecimal getMortalityCredit()

    public int getPaidUpAge()
    {
        return lifeVO.getPaidUpAge();
    } //-- int getPaidUpAge()


    public void setCurrentDeathBenefit(EDITBigDecimal currentDeathBenefit)
    {
        lifeVO.setCurrentDeathBenefit(SessionHelper.getEDITBigDecimal(currentDeathBenefit));
    } //-- void setCurrentDeathBenefit(java.math.BigDecimal)

    public void setEndowmentCredit(EDITBigDecimal endowmentCredit)
    {
        lifeVO.setEndowmentCredit(SessionHelper.getEDITBigDecimal(endowmentCredit));
    } //-- void setEndowmentCredit(java.math.BigDecimal)

    public void setExcessInterestCredit(EDITBigDecimal excessInterestCredit)
    {
        lifeVO.setExcessInterestCredit(SessionHelper.getEDITBigDecimal(excessInterestCredit));
    } //-- void setExcessInterestCredit(java.math.BigDecimal)

    public void setMortalityCredit(EDITBigDecimal mortalityCredit)
    {
        lifeVO.setMortalityCredit(SessionHelper.getEDITBigDecimal(mortalityCredit));
    } //-- void setMortalityCredit(java.math.BigDecimal)

    public void setPaidUpAge(int paidUpAge)
    {
        lifeVO.setPaidUpAge(paidUpAge);
    } //-- void setPaidUpAge(int)

    public EDITBigDecimal getRPUDeathBenefit()
    {
        return SessionHelper.getEDITBigDecimal(lifeVO.getRPUDeathBenefit());
    } //-- java.math.BigDecimal getRPUDeathBenefit()

    public void setRPUDeathBenefit(EDITBigDecimal RPUDeathBenefit)
    {
        lifeVO.setRPUDeathBenefit(SessionHelper.getEDITBigDecimal(RPUDeathBenefit));
    } //-- void setRPUDeathBenefit(java.math.BigDecimal)

    public EDITDate getPaidUpTermDate()
    {
        return SessionHelper.getEDITDate(lifeVO.getPaidUpTermDate());
    }

    public void setPaidUpTermDate(EDITDate paidUpTermDate)
    {
        lifeVO.setPaidUpTermDate(SessionHelper.getEDITDate(paidUpTermDate));
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (lifeVO == null)
        {
            lifeVO = new LifeVO();
        }

        if (crudEntityImpl == null)
        {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * @see CRUDEntity#delete()
     * @throws Throwable
     */
    public void delete() throws Throwable
    {
        try
        {
            crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
        }
        catch (Throwable t)
        {
            System.out.println(t);

            t.printStackTrace();
        }
    }

    /**
      * @see CRUDEntity#save()
      * @throws edit.common.exceptions.EDITContractException
      */
    public void save() throws EDITContractException
    {
        try
        {
//            if (segment != null)
//            {
//                String segmentStatus = segment.getStatus();
//                boolean saveChanges = segment.getSaveChangeHistory();
//
//                if (segmentStatus.equalsIgnoreCase("Active") && saveChanges)
//                {
//                    checkForNonFinancialChanges();
//                }
//            }

            //CONVERT TO HIBERNATE - 09/13/07
            hSave();
//            crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
        }
        catch (Throwable t)
        {
            System.out.println(t);
            t.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
    * @see CRUDEntity#getVO()
    * @return
    */
    public VOObject getVO()
    {
        return lifeVO;
    }

    /**
     * @see CRUDEntity#getPK()
     * @return
     */
    public long getPK()
    {
        return lifeVO.getLifePK();
    }

    /**
       * @param voObject
       */
    public void setVO(VOObject voObject)
    {
        this.lifeVO = (LifeVO) voObject;
    }

    /**
      * @see CRUDEntity#isNew()
      * @return
      */
    public boolean isNew()
    {
        return crudEntityImpl.isNew(this);
    }

    /**
      * @see CRUDEntity#cloneCRUDEntity()
      * @return
      */
    public CRUDEntity cloneCRUDEntity()
    {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    /**
     * Getter.
     * @return
     */
    public String getMECStatusCT()
    {
        return lifeVO.getMECStatusCT();
    }

    //-- java.lang.String getMECStatusCT()

    /**
     * Getter.
     * @return
     */
    public EDITDate getMECDate()
    {
        return SessionHelper.getEDITDate(lifeVO.getMECDate());
    }

    //-- java.lang.String getMECDate()

    /**
     * Getter.
     * @return
     */
    public String getDeathBenefitOptionCT()
    {
        return lifeVO.getDeathBenefitOptionCT();
    }

    //-- java.lang.String getDeathBenefitOptionCT()

    /**
     * Getter.
     * @return
     */
    public EDITDate getLapseDate()
    {
        return SessionHelper.getEDITDate(lifeVO.getLapseDate());
    }

    //-- java.lang.String getLapseDate()

    /**
     * Getter.
     * @return
     */
    public String getOption7702CT()
    {
        return lifeVO.getOption7702CT();
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Life.DATABASE;
    }

    /**
     * Finds the Life object for the given pk
     * @param lifePK
     * @return
     */
    public static Life findByPK(Long lifePK)
    {
        return (Life) SessionHelper.get(Life.class,  lifePK, Life.DATABASE);
    }

    /**
     * Finder.
     * @param segmentPK
     * @return
     */
    public static Life findBy_SegmentPK(long segmentPK)
    {
        Life life = null;

        LifeVO[] lifeVOs = new LifeDAO().findLifeBySegmentFK(segmentPK, false, null);

        if (lifeVOs != null)
        {
            life = new Life(lifeVOs[0]);
        }

        return life;
    }
  /**
   * Finder.
   * @param segmentPK
   * @return
   */
  public static Life findSeparateBy_SegmentPK(Long segmentPK)
  {
      Life life = null;

      String hql = "from Life life where life.SegmentFK = :segmentFK";
      
      EDITMap params = new EDITMap("segmentFK", segmentPK);
      
      Session session = null;
      
      try
      {
        session = SessionHelper.getSeparateSession(Life.DATABASE);
        
        List<Life> results = SessionHelper.executeHQL(session, hql, params, 0);
        
        if (!results.isEmpty())
        {
          life = results.get(0);
        }
      }
      finally
      {
        if (session != null) session.close();
      }

      return life;
  }    

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getFaceAmount()
    {
        return SessionHelper.getEDITBigDecimal(lifeVO.getFaceAmount());
    }

    public Long getSegmentFK()
    {
        return SessionHelper.getPKValue(lifeVO.getSegmentFK());
    }
     //-- long getSegmentFK() 

    public void setSegmentFK(Long segmentFK)
    {
        lifeVO.setSegmentFK(SessionHelper.getPKValue(segmentFK));
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Life.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, Life.DATABASE);
    }

    public boolean hasStartNew7PayIndicator()
    {
        boolean startNew7PayIndicatorSet = false;

        String startNew7PayInd = getStartNew7PayIndicator();

        if (startNew7PayInd != null && startNew7PayInd.equalsIgnoreCase("Y"))
        {
            startNew7PayIndicatorSet = true;
        }

        return startNew7PayIndicatorSet;
    }
    
  public static boolean hasStartNew7PayIndicator(long editTrxPK)
  {
      LifeVO[] lifeVOs = new LifeDAO().findLifeByEDITTrx(editTrxPK, false, null);
      boolean startNew7PayIndicatorSet = false;

      if (lifeVOs != null)
      {
          String startNew7PayInd = lifeVOs[0].getStartNew7PayIndicator();

          if (startNew7PayInd != null && startNew7PayInd.equalsIgnoreCase("Y"))
          {
              startNew7PayIndicatorSet = true;
          }
      }

      return startNew7PayIndicatorSet;
  }
}
