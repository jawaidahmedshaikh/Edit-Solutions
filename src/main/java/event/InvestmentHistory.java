/*
 * User: gfrosti
 * Date: Feb 12, 2005
 * Time: 3:58:39 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event;

import contract.*;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import event.dm.dao.*;

import java.util.*;



public class InvestmentHistory extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityImpl crudEntityImpl;
    private InvestmentHistoryVO investmentHistoryVO;
    private EDITTrxHistory editTrxHistory;
    private Investment investment;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a InvestmentHistory entity with a default InvestmentHistoryVO.
     */
    public InvestmentHistory()
    {
        init();
    }

    /**
     * Instantiates a InvestmentHistory entity with a supplied InvestmentHistoryVO.
     *
     * @param investmentHistoryVO
     */
    public InvestmentHistory(InvestmentHistoryVO investmentHistoryVO)
    {
        init();

        this.investmentHistoryVO = investmentHistoryVO;
    }

    public Long getChargeCodeFK()
    {
        return SessionHelper.getPKValue(investmentHistoryVO.getChargeCodeFK());
    }

    public void setChargeCodeFK(Long chargeCodeFK)
    {
        investmentHistoryVO.setChargeCodeFK(SessionHelper.getPKValue(chargeCodeFK));
    }

    public void setPreviousChargeCodeFK(Long previousChargeCodeFK)
    {
        investmentHistoryVO.setPreviousChargeCodeFK(SessionHelper.getPKValue(previousChargeCodeFK));
    }

    public void setInvestmentHistoryPK(Long investmentHistoryPK)
    {
        investmentHistoryVO.setInvestmentHistoryPK(SessionHelper.getPKValue(investmentHistoryPK));
    }

    //-- void setInvestmentHistoryPK(long)
    public Long getInvestmentHistoryPK()
    {
        return SessionHelper.getPKValue(investmentHistoryVO.getInvestmentHistoryPK());
    }

    //-- long getInvestmentHistoryPK()

    /**
     * Getter.
     * @return
     */
    public EDITTrxHistory getEDITTrxHistory()
    {
        return editTrxHistory;
    }

    /**
     * Setter.
     * @param editTrxHistory
     */
    public void setEDITTrxHistory(EDITTrxHistory editTrxHistory)
    {
        this.editTrxHistory = editTrxHistory;
    }

    public EDITBigDecimal getGainLoss()
    {
        return SessionHelper.getEDITBigDecimal(investmentHistoryVO.getGainLoss());
    }

    //-- java.math.BigDecimal getGainLoss() 
    public EDITBigDecimal getInvestmentDollars()
    {
        return SessionHelper.getEDITBigDecimal(investmentHistoryVO.getInvestmentDollars());
    }

    //-- java.math.BigDecimal getInvestmentDollars() 
    public String getToFromStatus()
    {
        return investmentHistoryVO.getToFromStatus();
    }

    //-- java.lang.String getToFromStatus() 
    public void setGainLoss(EDITBigDecimal gainLoss)
    {
        investmentHistoryVO.setGainLoss(SessionHelper.getEDITBigDecimal(gainLoss));
    }

    //-- void setGainLoss(java.math.BigDecimal) 
    public void setInvestmentDollars(EDITBigDecimal investmentDollars)
    {
        investmentHistoryVO.setInvestmentDollars(SessionHelper.getEDITBigDecimal(investmentDollars));
    }

    //-- void setInvestmentDollars(java.math.BigDecimal) 
    public void setInvestmentUnits(EDITBigDecimal investmentUnits)
    {
        investmentHistoryVO.setInvestmentUnits(SessionHelper.getEDITBigDecimal(investmentUnits));
    }

    //-- void setInvestmentUnits(java.math.BigDecimal) 
    public void setToFromStatus(String toFromStatus)
    {
        investmentHistoryVO.setToFromStatus(toFromStatus);
    }

    //-- void setToFromStatus(java.lang.String) 
    public void setValuationDate(EDITDate valuationDate)
    {
        investmentHistoryVO.setValuationDate(SessionHelper.getEDITDate(valuationDate));
    }

    //-- void setValuationDate(java.lang.String) 

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init()
    {
        if (investmentHistoryVO == null)
        {
            investmentHistoryVO = new InvestmentHistoryVO();
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
        return investmentHistoryVO;
    }

    /**
     * @return
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK()
    {
        return investmentHistoryVO.getInvestmentHistoryPK();
    }

    /**
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.investmentHistoryVO = (InvestmentHistoryVO) voObject;
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
     * Finder.
     *
     * @param investmentHistoryPK
     */
    public static final InvestmentHistory findByPK(long investmentHistoryPK)
    {
        InvestmentHistory investmentHistory = null;

        InvestmentHistoryVO[] investmentHistoryVOs = new InvestmentHistoryDAO().findByPK(investmentHistoryPK);

        if (investmentHistoryVOs != null)
        {
            investmentHistory = new InvestmentHistory(investmentHistoryVOs[0]);
        }

        return investmentHistory;
    }

    public static InvestmentHistory[] findByInvestmentFK(Long investmentFK)
    {
        InvestmentHistory[] investmentHistory = null;

        String hql = "select ih from InvestmentHistory ih where ih.InvestmentFK = :investmentFK";

        Map params = new HashMap();

        params.put("investmentFK", investmentFK);

        List results = SessionHelper.executeHQL(hql, params, InvestmentHistory.DATABASE);

        if (results.size() > 0)
        {
            investmentHistory = (InvestmentHistory[]) results.toArray(new InvestmentHistory[results.size()]);
        }

        return investmentHistory;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDate getValuationDate()
    {
        return SessionHelper.getEDITDate(investmentHistoryVO.getValuationDate());
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getInvestmentUnits()
    {
        return SessionHelper.getEDITBigDecimal(investmentHistoryVO.getInvestmentUnits());
    }

    /**
     * Finder.
     * @param editTrxHistoryPK
     * @return
     */
    public static InvestmentHistory findByEDITTrxHistoryPK(long editTrxHistoryPK)
    {
        InvestmentHistory investmentHistory = null;

        InvestmentHistoryVO[] investmentHistoryVOs = new InvestmentHistoryDAO().findByEDITTrxHistoryPK(editTrxHistoryPK);

        if (investmentHistoryVOs != null)
        {
            investmentHistory = new InvestmentHistory(investmentHistoryVOs[0]);
        }

        return investmentHistory;
    }

    /**
     * Return back all InvestmentHistoryVO's for a given EDITTrxHistoryPK.
     * @param editTrxHistoryPK
     * @return  Array of InvestmentHistoryVO or null
     */
    public static InvestmentHistory[] findAllByEDITTrxHistoryPK(long editTrxHistoryPK)
    {
        InvestmentHistoryVO[] investmentHistoryVOs = new InvestmentHistoryDAO().findByEDITTrxHistoryPK(editTrxHistoryPK);

        if ((investmentHistoryVOs == null) || (investmentHistoryVOs.length == 0))
        {
            return null;
        }

        InvestmentHistory[] investmentHistorys = new InvestmentHistory[investmentHistoryVOs.length];

        for (int i = 0; i < investmentHistoryVOs.length; i++)
        {
            investmentHistorys[i] = new InvestmentHistory(investmentHistoryVOs[i]);
        }

        return investmentHistorys;
    }

    /**
     * Return back all InvestmentHistoryVO's for a given EDITTrxHistoryPK.
     * @param editTrxHistoryPK
     * @return  Array of InvestmentHistoryVO or null
     */
    public static InvestmentHistory[] findByEDITTrxHistoryPK_FinalPriceStatus(long editTrxHistoryPK, String finalPriceStatus)
    {
        InvestmentHistoryVO[] investmentHistoryVOs = new InvestmentHistoryDAO().findByEDITTrxHistoryPK_FinalPriceStatus(editTrxHistoryPK, finalPriceStatus);

        if ((investmentHistoryVOs == null) || (investmentHistoryVOs.length == 0))
        {
            return null;
        }
        else
        {
            InvestmentHistory[] investmentHistorys = new InvestmentHistory[investmentHistoryVOs.length];

            for (int i = 0; i < investmentHistoryVOs.length; i++)
            {
                investmentHistorys[i] = new InvestmentHistory(investmentHistoryVOs[i]);
            }

            return investmentHistorys;
        }
    }

    /**
     * The associated Investment.
     * @return
     */
    public Investment get_Investment()
    {
        return new Investment(investmentHistoryVO.getInvestmentFK());
    }

    public Investment getInvestment()
    {
        return investment;
    }

    public void setInvestment(Investment investment)
    {
        this.investment = investment;
    }

    /**
     * Maps the previous ChargeCode(FK) of this InvestmentHistory to its corresponding Investment's ChargeCode(FK).
     */
    public void undoChargeCode()
    {
        Investment investment = get_Investment();

        investment.revertChargeCodeFK(getPreviousChargeCodeFK());

        investment.save();
    }

    /**
     * Getter.
     * @return
     */
    public long getPreviousChargeCodeFK()
    {
        return investmentHistoryVO.getPreviousChargeCodeFK();
    }

    public Long getInvestmentFK()
    {
        return SessionHelper.getPKValue(investmentHistoryVO.getInvestmentFK());
    }
     //-- long getInvestmentFK() 

    public Long getEDITTrxHistoryFK()
    {
        return SessionHelper.getPKValue(investmentHistoryVO.getEDITTrxHistoryFK());
    }
     //-- long getEDITTrxHistoryFK() 

    public void setEDITTrxHistoryFK(Long editTrxHistoryFK)
    {
        investmentHistoryVO.setEDITTrxHistoryFK(SessionHelper.getPKValue(editTrxHistoryFK));
    }
     //-- void setEDITTrxHistoryFK(long) 

    public void setInvestmentFK(Long investmentFK)
    {
        investmentHistoryVO.setInvestmentFK(SessionHelper.getPKValue(investmentFK));
    }
     //-- void setInvestmentFK(long)

    /**
     * Getter
     * @return
     */
    public String getFinalPriceStatus()
    {
        return investmentHistoryVO.getFinalPriceStatus();
    } //-- java.lang.String getFinalPriceStatus()

    /**
     * Setter
     * @param finalPriceStatus
     */
    public void setFinalPriceStatus(String finalPriceStatus)
    {
        investmentHistoryVO.setFinalPriceStatus(finalPriceStatus);
    } //-- void setFinalPriceStatus(java.lang.String)

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, InvestmentHistory.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, InvestmentHistory.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return InvestmentHistory.DATABASE;
    }
}
