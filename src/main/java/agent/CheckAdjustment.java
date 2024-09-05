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

import edit.common.*;
import edit.common.vo.*;
import edit.services.db.CRUDEntity;
import edit.services.db.hibernate.*;
import event.dm.dao.FastDAO;
import event.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import role.*;


public class CheckAdjustment extends HibernateEntity implements CRUDEntity
{
    private CheckAdjustmentVO checkAdjustmentVO;

    private CheckAdjustmentImpl checkAdjustmentImpl;

    public static final String SCHEDULED_ADJUSTMENT_STATUS = "Scheduled";
    public static final String ONETIME_ADJUSTMENT_STATUS = "OneTime";
    public static final String DEBIT_BAL_PAYOFF_NOTICE_TYPE = "DebitBalPayoffNotice";
    public static final String TAXABLE_STATUS = "Taxable";
    public static final String NON_TAXABLE_STATUS = "NonTaxable";
    public static final String REDUCE_TAXABLE_STATUS = "ReduceTaxable";
    public static final String DEBIT_BALANCE_AUTO_REPAY = "DebitBalAutoRepay";
    private static final String YES_VALUE = "Y";

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;

    private Agent agent;
    private PlacedAgent placedAgent;

    private Set editTrxs;


    public CheckAdjustment()
    {
        this.checkAdjustmentVO = new CheckAdjustmentVO();
        this.checkAdjustmentImpl = new CheckAdjustmentImpl();
    }

    public CheckAdjustment(long checkAdjustmentPK) throws Exception
    {
        this();
        this.checkAdjustmentImpl.load(this, checkAdjustmentPK);
    }

    public CheckAdjustment(CheckAdjustmentVO checkAdjustmentVO)
    {
        this();
        this.checkAdjustmentVO = checkAdjustmentVO;
    }

    public void save()
    {
        this.checkAdjustmentImpl.save(this);
    }

    public void delete() throws Exception
    {
        this.checkAdjustmentImpl.delete(this);
    }

    public void associateAgent(Agent agent)
    {
//        this.checkAdjustmentVO.setAgentFK(agent.getPK());
        this.setAgent(agent);
        hSave();        
    }
    
    public void associatePlacedAgent(PlacedAgent placedAgent)
    {
        this.setPlacedAgent(placedAgent);
        hSave();        
    }

    public VOObject getVO()
    {
        return checkAdjustmentVO;
    }

    public long getPK()
    {
        return checkAdjustmentVO.getCheckAdjustmentPK();
    }

    public void setVO(VOObject voObject)
    {
        this.checkAdjustmentVO = (CheckAdjustmentVO) voObject;
    }

    public boolean isNew()
    {
        return this.checkAdjustmentImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return this.checkAdjustmentImpl.cloneCRUDEntity(this);
    }
    
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, DATABASE);    
    }
    
    public void hDelete()
    {
        SessionHelper.delete(this, DATABASE);    
    }

    /**
     * Getter.
     * @return
     */
    public String getAdjustmentCompleteInd()
    {
        return checkAdjustmentVO.getAdjustmentCompleteInd();
    } //-- java.lang.String getAdjustmentCompleteInd()

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAdjustmentDollar()
    {
        return SessionHelper.getEDITBigDecimal(checkAdjustmentVO.getAdjustmentDollar());
    } //-- java.math.BigDecimal getAdjustmentDollar()

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getAdjustmentPercent()
    {
        return SessionHelper.getEDITBigDecimal(checkAdjustmentVO.getAdjustmentPercent());
    } //-- java.math.BigDecimal getAdjustmentPercent()

    /**
     * Getter.
     * @return
     */
    public String getAdjustmentStatusCT()
    {
        return checkAdjustmentVO.getAdjustmentStatusCT();
    } //-- java.lang.String getAdjustmentStatusCT()

    /**
     * Getter.
     * @return
     */
    public String getAdjustmentTypeCT()
    {
        return checkAdjustmentVO.getAdjustmentTypeCT();
    } //-- java.lang.String getAdjustmentTypeCT()

    /**
     * Getter.
     * @return
     */
    public Long getAgentFK()
    {
        return SessionHelper.getPKValue(checkAdjustmentVO.getAgentFK());
    } //-- long getAgentFK()

    /**
     * Getter.
     * @return
     */
    public Long getCheckAdjustmentPK()
    {
        return SessionHelper.getPKValue(checkAdjustmentVO.getCheckAdjustmentPK());
    } //-- long getCheckAdjustmentPK()

    /**
     * Getter.
     * @return
     */
    public String getDescription()
    {
        return checkAdjustmentVO.getDescription();
    } //-- java.lang.String getDescription()

    /**
     * Getter.
     * @return
     */
    public String getModeCT()
    {
        return checkAdjustmentVO.getModeCT();
    } //-- java.lang.String getModeCT()

    /**
     * Getter.
     * @return
     */
    public EDITDate getNextDueDate()
    {
        return SessionHelper.getEDITDate(checkAdjustmentVO.getNextDueDate());
    } //-- java.lang.String getNextDueDate()

    /**
     * Getter.
     * @return
     */
    public EDITDate getStartDate()
    {
        return SessionHelper.getEDITDate(checkAdjustmentVO.getStartDate());
    } //-- java.lang.String getStartDate()

    /**
     * Getter.
     * @return
     */
    public EDITDate getStopDate()
    {
        return SessionHelper.getEDITDate(checkAdjustmentVO.getStopDate());
    } //-- java.lang.String getStopDate()

    /**
     * Getter.
     * @return
     */
    public String getTaxableStatusCT()
    {
        return checkAdjustmentVO.getTaxableStatusCT();
    } //-- java.lang.String getTaxableStatusCT()

    /**
     * Getter
     * @return
     */
    public EDITBigDecimal getAccumulatedDebitBalance()
    {
        return SessionHelper.getEDITBigDecimal(checkAdjustmentVO.getAccumulatedDebitBalance());
    } //-- java.math.BigDecimal getAccumulatedDebitBalance()
    
    /**
      * Getter.
      * @return
      */
     public Long getPlacedAgentFK()
     {
         return SessionHelper.getPKValue(checkAdjustmentVO.getPlacedAgentFK());
     }


    /**
     * Setter.
     * @param adjustmentCompleteInd
     */
    public void setAdjustmentCompleteInd(String adjustmentCompleteInd)
    {
        checkAdjustmentVO.setAdjustmentCompleteInd(adjustmentCompleteInd);
    } //-- void setAdjustmentCompleteInd(java.lang.String)

    /**
     * Setter.
     * @param adjustmentDollar
     */
    public void setAdjustmentDollar(EDITBigDecimal adjustmentDollar)
    {
        checkAdjustmentVO.setAdjustmentDollar(SessionHelper.getEDITBigDecimal(adjustmentDollar));
    } //-- void setAdjustmentDollar(java.math.BigDecimal)

    /**
     * Setter.
     * @param adjustmentPercent
     */
    public void setAdjustmentPercent(EDITBigDecimal adjustmentPercent)
    {
        checkAdjustmentVO.setAdjustmentPercent(SessionHelper.getEDITBigDecimal(adjustmentPercent));
    } //-- void setAdjustmentPercent(java.math.BigDecimal)

    /**
     * Setter.
     * @param adjustmentStatusCT
     */
    public void setAdjustmentStatusCT(String adjustmentStatusCT)
    {
        checkAdjustmentVO.setAdjustmentStatusCT(adjustmentStatusCT);
    } //-- void setAdjustmentStatusCT(java.lang.String)

    /**
     * Setter.
     * @param adjustmentTypeCT
     */
    public void setAdjustmentTypeCT(String adjustmentTypeCT)
    {
        checkAdjustmentVO.setAdjustmentTypeCT(adjustmentTypeCT);
    } //-- void setAdjustmentTypeCT(java.lang.String)

    /**
     * Setter.
     * @param agentFK
     */
    public void setAgentFK(Long agentFK)
    {
        checkAdjustmentVO.setAgentFK(SessionHelper.getPKValue(agentFK));
    } //-- void setAgentFK(long)

    /**
     * Setter.
     * @param checkAdjustmentPK
     */
    public void setCheckAdjustmentPK(Long checkAdjustmentPK)
    {
        checkAdjustmentVO.setCheckAdjustmentPK(SessionHelper.getPKValue(checkAdjustmentPK));
    } //-- void setCheckAdjustmentPK(long)

    /**
     * Setter.
     * @param description
     */
    public void setDescription(String description)
    {
        checkAdjustmentVO.setDescription(description);
    } //-- void setDescription(java.lang.String)

    /**
     * Setter.
     * @param modeCT
     */
    public void setModeCT(String modeCT)
    {
        checkAdjustmentVO.setModeCT(modeCT);
    } //-- void setModeCT(java.lang.String)

    /**
     * Setter.
     * @param nextDueDate
     */
    public void setNextDueDate(EDITDate nextDueDate)
    {
        checkAdjustmentVO.setNextDueDate(SessionHelper.getEDITDate(nextDueDate));
    } //-- void setNextDueDate(java.lang.String)

    /**
     * Setter.
     * @param startDate
     */
    public void setStartDate(EDITDate startDate)
    {
        checkAdjustmentVO.setStartDate(SessionHelper.getEDITDate(startDate));
    } //-- void setStartDate(java.lang.String)

    /**
     * Setter.
     * @param stopDate
     */
    public void setStopDate(EDITDate stopDate)
    {
        checkAdjustmentVO.setStopDate(SessionHelper.getEDITDate(stopDate));
    } //-- void setStopDate(java.lang.String)

    /**
     * Setter.
     * @param taxableStatusCT
     */
    public void setTaxableStatusCT(String taxableStatusCT)
    {
        checkAdjustmentVO.setTaxableStatusCT(taxableStatusCT);
    } //-- void setTaxableStatusCT(java.lang.String)

    /**
     * Setter
     * @param accumulatedDebitBalance
     */
    public void setAccumulatedDebitBalance(EDITBigDecimal accumulatedDebitBalance)
    {
        checkAdjustmentVO.setAccumulatedDebitBalance(SessionHelper.getEDITBigDecimal(accumulatedDebitBalance));
    } //-- void setAccumulatedDebitBalance(java.math.BigDecimal)
    
    /**
      * Setter.
      * @param placedAgentFK
      */
     public void setPlacedAgentFK(Long placedAgentFK)
     {
         checkAdjustmentVO.setPlacedAgentFK(SessionHelper.getPKValue(placedAgentFK));
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
     * @param agent
     */
    public void setAgent(Agent agent)
    {
        this.agent = agent;
    }
    
    /**
     * Setter.
     * @param placedAgent
     */
    public void setPlacedAgent(PlacedAgent placedAgent)
    {
        this.placedAgent = placedAgent;
    }

    /**
     * Getter.
     * @return
     */
    public PlacedAgent getPlacedAgent()
    {
        return placedAgent;
    }

    /**
     * Getter.
     * @return
     */
    public Set getEDITTrxs()
    {
        return editTrxs;
    }

    /**
     * Setter.
     * @param editTrxs
     */
    public void setEDITTrxs(Set editTrxs)
    {
        this.editTrxs = editTrxs;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return CheckAdjustment.DATABASE;
    }

    /**
     * Finder by PK.
     * @param checkAdjustmentPK
     * @return
     */
    public static final CheckAdjustment findByPK(Long checkAdjustmentPK)
    {
        return (CheckAdjustment) SessionHelper.get(CheckAdjustment.class, checkAdjustmentPK, CheckAdjustment.DATABASE);
    }

    public static long[] getQualifyingAdjustmentPKs() throws Exception
    {
        long[] checkAdjustmentPKs = null;

        try
        {
            checkAdjustmentPKs = new FastDAO().findQualifyingAdjustments();
        }
        catch (Exception e)
        {
            System.out.println(e);
        
            e.printStackTrace();
            
            throw (e);
        }

        return checkAdjustmentPKs;
    }

    /**
     * before save, check comm balance and stop date
     * @param clientRoleFK
     */
    public void updateForAutoRepay(long clientRoleFK)
    {
        //Get the ClientRoleFinancial record for this agent
        ClientRoleFinancial clientRoleFinancial = ClientRoleFinancial.findByClientRole(clientRoleFK);

        EDITBigDecimal commBalance = clientRoleFinancial.getCommBalance();
        EDITDate stopDate = this.getStopDate();
        EDITDate currentDate = new EDITDate();

        if (stopDate.before(currentDate))
        {
            setAdjustmentCompleteInd("Y");
        }
        else if (commBalance.isLT("0") && (stopDate.after(currentDate) || stopDate.equals(currentDate)))
        {
            //Update ClientRoleFinancial for the DEBIT BALANCE REPAY
            commBalance = commBalance.multiplyEditBigDecimal("-1");
            this.setAccumulatedDebitBalance(commBalance);
            clientRoleFinancial.setDBAmount(commBalance);
            clientRoleFinancial.setCommBalance(new EDITBigDecimal());

            clientRoleFinancial.save();

            //Update CommissionHistory
            updateCommissionHistoryIndicator(clientRoleFinancial);
        }
    }

    private void updateCommissionHistoryIndicator(ClientRoleFinancial clientRoleFinancial)
    {
        EDITDateTime lastCheckDateTime = clientRoleFinancial.getLastCheckDateTime();

        if (lastCheckDateTime == null)
        {
            lastCheckDateTime = new EDITDateTime(EDITDateTime.DEFAULT_MIN_DATETIME);
        }

        Agent agent = Agent.findByClientRoleFinancialPK(clientRoleFinancial.getPK());
        AgentContract[] agentContract = AgentContract.findByAgentPK(agent.getPK());

        for (int i = 0; i < agentContract.length; i++)
        {
            PlacedAgent[] placedAgent = PlacedAgent.findByAgentContractPK(agentContract[i].getPK());

            for (int j = 0; j < placedAgent.length; j++)
            {
                try
                {
                    EDITDateTime oldLastCheckDateTime = new EDITDateTime(lastCheckDateTime.getFormattedDateTime());
                    CommissionHistory[] commissionHistories = CommissionHistory.findByPlacedAgentPKUpdateDateTime(placedAgent[j].getPK(), oldLastCheckDateTime);

                    if (commissionHistories != null)
                    {
                        for (int k = 0; k < commissionHistories.length; k++)
                        {
                            commissionHistories[k].setIncludedInDebitBalInd(YES_VALUE);
                            commissionHistories[k].save();
                        }
                    }
                }
                catch (Exception e)
                {
                    System.out.println(e);

                    e.printStackTrace();
                }
            }
        }
    }
}
