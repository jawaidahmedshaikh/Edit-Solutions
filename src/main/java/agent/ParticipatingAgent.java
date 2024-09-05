/*
 * User: gfrosti
 * Date: Jan 5, 2005
 * Time: 12:58:06 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import batch.business.Batch;

import edit.common.*;
import edit.common.vo.*;
import edit.common.exceptions.*;

import edit.services.EditServiceLocator;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import edit.services.config.*;
import edit.services.logging.Logging;

import event.financial.client.trx.BonusCheckTrx;

import logging.*;

import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

import java.util.*;

import engine.ProductStructure;

import event.*;

import fission.utility.*;
import contract.*;

//public class ParticipatingAgent implements CRUDEntity, HibernateEntity

public class ParticipatingAgent extends HibernateEntity
{
    public static final String PRODUCECHECKIND_YES = "Y";
    private Long participatingAgentPK;
    private String produceCheckInd;
    private String bonusProgramOverrideInd;
    private EDITBigDecimal bonusTaxableAmount;
    private EDITDateTime lastStatementDateTime;
    private EDITDateTime lastCheckDateTime;
    private EDITBigDecimal lastCheckAmount;
    private EDITDate nextCheckDate;
    private Long bonusProgramFK;
    private PlacedAgent placedAgent;
    private BonusProgram bonusProgram;
    private Set premiumLevels;
    private Set bonusCommissionHistories;
    private Long placedAgentFK;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public ParticipatingAgent()
    {
        this.premiumLevels = new HashSet();
    }

    public Set getBonusCommissionHistories()
    {
        return bonusCommissionHistories;
    }

    public void setBonusCommissionHistories(Set bonusCommissionHistories)
    {
        this.bonusCommissionHistories = bonusCommissionHistories;
    }

    /**
     * Setter.
     *
     * @param participatingAgentPK
     */
    public void setParticipatingAgentPK(Long participatingAgentPK)
    {
        this.participatingAgentPK = participatingAgentPK;
    }

    /**
     * Setter.
     *
     * @param placedAgent
     */
    public void setPlacedAgent(PlacedAgent placedAgent)
    {
        this.placedAgent = placedAgent;
    }

    /**
     * Setter.
     *
     * @param bonusProgram
     */
    public void setBonusProgram(BonusProgram bonusProgram)
    {
        this.bonusProgram = bonusProgram;
    }

    /**
     * Setter.
     *
     * @param produceCheckInd
     */
    public void setProduceCheckInd(String produceCheckInd)
    {
        this.produceCheckInd = produceCheckInd;
    }

    /**
     * Setter.
     *
     * @param bonusProgramOverrideInd
     */
    public void setBonusProgramOverrideInd(String bonusProgramOverrideInd)
    {
        this.bonusProgramOverrideInd = bonusProgramOverrideInd;
    }

    /**
     * Setter.
     *
     * @param bonusTaxableAmount
     */
    public void setBonusTaxableAmount(EDITBigDecimal bonusTaxableAmount)
    {
        this.bonusTaxableAmount = bonusTaxableAmount;
    }

    /**
     * Setter.
     *
     * @param lastStatementDateTime
     */
    public void setLastStatementDateTime(EDITDateTime lastStatementDateTime)
    {
        this.lastStatementDateTime = lastStatementDateTime;
    }

    /**
     * Setter.
     *
     * @param lastCheckDateTime
     */
    public void setLastCheckDateTime(EDITDateTime lastCheckDateTime)
    {
        this.lastCheckDateTime = lastCheckDateTime;
    }

    /**
     * Setter.
     *
     * @param lastCheckAmount
     */
    public void setLastCheckAmount(EDITBigDecimal lastCheckAmount)
    {
        this.lastCheckAmount = lastCheckAmount;
    }

    /**
     * Setter.
     *
     * @param bonusProgramFK
     */
    public void setBonusProgramFK(Long bonusProgramFK)
    {
        this.bonusProgramFK = bonusProgramFK;
    }

    /**
     * Setter.
     *
     * @param placedAgentFK
     */
    public void setPlacedAgentFK(Long placedAgentFK)
    {
        this.placedAgentFK = placedAgentFK;
    }

    /**
     * Setter.
     *
     * @param premiumLevels
     */
    public void setPremiumLevels(Set premiumLevels)
    {
        this.premiumLevels = premiumLevels;
    }

    /**
     * Getter.
     *
     * @return
     */
    public Long getParticipatingAgentPK()
    {
        return participatingAgentPK;
    }

    /**
     * Getter.
     *
     * @return
     */
    public PlacedAgent getPlacedAgent()
    {
        return placedAgent;
    }

    /**
     * Getter.
     *
     * @return
     */
    public BonusProgram getBonusProgram()
    {
        return bonusProgram;
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getProduceCheckInd()
    {
        return produceCheckInd;
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getBonusProgramOverrideInd()
    {
        return bonusProgramOverrideInd;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITBigDecimal getBonusTaxableAmount()
    {
        return bonusTaxableAmount;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITDateTime getLastStatementDateTime()
    {
        return lastStatementDateTime;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITDateTime getLastCheckDateTime()
    {
        return lastCheckDateTime;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITBigDecimal getLastCheckAmount()
    {
        return lastCheckAmount;
    }


    /**
     * Getter.
     *
     * @return
     */
    public Long getBonusProgramFK()
    {
        return bonusProgramFK;
    }

    /**
     * Getter.
     *
     * @return
     */
    public Long getPlacedAgentFK()
    {
        return placedAgentFK;
    }

    /**
     * Getter.
     *
     * @return
     */
    public Set getPremiumLevels()
    {
        return premiumLevels;
    }

    /**
     * Adder.
     *
     * @param premiumLevel
     */
    public void addPremiumLevel(PremiumLevel premiumLevel)
    {
        getPremiumLevels().add(premiumLevel);

        premiumLevel.setParticipatingAgent(this);

        SessionHelper.saveOrUpdate(this, ParticipatingAgent.DATABASE);
    }

    /**
     * Finder.
     *
     * @param bonusProgramPK
     * @param placedAgentPK
     *
     * @return
     */
    public static ParticipatingAgent[] findBy_BonusProgramPK_PlacedAgentPK(Long bonusProgramPK, Long placedAgentPK)
    {
        String hql = "select pa from ParticipatingAgent pa where " + "" + "pa.BonusProgramFK = :bonusProgramFK and pa.PlacedAgentFK = :placedAgentFK";

        Map params = new HashMap();

        params.put("bonusProgramFK", bonusProgramPK);

        params.put("placedAgentFK", placedAgentPK);

        List results = SessionHelper.executeHQL(hql, params, ParticipatingAgent.DATABASE);

        ParticipatingAgent[] participatingAgents = null;

        if (results.size() > 0)
        {
            participatingAgents = (ParticipatingAgent[]) results.toArray(new ParticipatingAgent[results.size()]);
        }

        return participatingAgents;
    }

    /**
     * Finder.
     *
     * @param participatingAgentPK
     */
    public static final ParticipatingAgent findByPK(Long participatingAgentPK)
    {
        return (ParticipatingAgent) SessionHelper.get(ParticipatingAgent.class, participatingAgentPK, ParticipatingAgent.DATABASE);
    }

    /**
     * Finder.
     *
     * @param bonusProgramPK
     *
     * @return
     */
    public static ParticipatingAgent[] findBy_BonusProgramPK(long bonusProgramPK)
    {
        String hql = "select pa from ParticipatingAgent pa " + "where pa.BonusProgramFK = :bonusProgramFK";

        Map params = new HashMap();

        params.put("bonusProgramFK", new Long(bonusProgramPK));

        List results = SessionHelper.executeHQL(hql, params, ParticipatingAgent.DATABASE);

        return (ParticipatingAgent[]) results.toArray(new ParticipatingAgent[results.size()]);
    }

    /**
     * Updates the bonus amount for this ParticipatingAgent.
     */
    public void updateAgentBonus()
    {
        updateAgentBonusForPremiumTrx();

        SessionHelper.flushSessions();

        updateAgentBonusForNotTakenAndFullSurrender();
    }

    /**
     * Premium Transactions and their Reversals have CommissionHistories
     * that need to be accounted for by buildling BonusCommissionHistories.
     *
     * @see ParticipatingAgent#updateAgentBonusForNotTakenAndFullSurrender()
     */
    private void updateAgentBonusForPremiumTrx()
    {
        CommissionHistory[] contributingCommissionHistories = new AgentBonusContributionStrategy(bonusProgram, this).getContributingCommissionHistories();

        for (int i = 0; i < contributingCommissionHistories.length; i++)
        {
            CommissionHistory contributingCommissionHistory = null;

            try
            {
                contributingCommissionHistory = contributingCommissionHistories[i];

                generateBonusCommissionHistory(contributingCommissionHistory);
            }
            catch (Exception e)
            {
                String agentNumber = contributingCommissionHistory.getPlacedAgent().getAgentContract().getAgent().getAgentNumber();

                String contractNumber = contributingCommissionHistory.getEDITTrxHistory().getEDITTrx().getContractNumber();

                BonusCommissionHistory bonusCommissionHistory =
                        BonusCommissionHistory.findBy_ParticipatingAgentPK_CommissionHistoryPK(
                                this.getParticipatingAgentPK(), contributingCommissionHistory.getCommissionHistoryPK());

                logErrorToDatabase(e, agentNumber, contractNumber, bonusCommissionHistory.getTransactionTypeCT());
            }
        }
    }

    /**
     * The 'Not Taken' transaction and the 'Full Surrender' transaction simply
     * need to zero-out the sum total of this PartipatingAgent's BonusCommissionHistories.
     * These transaction types are not hard-coded, but specified in the TransactionPriority.
     * It is expected that additional transaction types will be added in the future.
     */
    private void updateAgentBonusForNotTakenAndFullSurrender()
    {
        String[] bonusProcesses = {AgentBonusContributionStrategy.PROCESSING_NORMAL, AgentBonusContributionStrategy.PROCESSING_REVERSAL};

        TransactionPriority[] bonusChargebackTransactionPriorities = TransactionPriority.findBy_BonusChargeback_Not_Null();

        for (int i = 0; i < bonusProcesses.length; i++)
        {
            SessionHelper.flushSessions();

            String bonusProcess = bonusProcesses[i];

            for (int j = 0; j < bonusChargebackTransactionPriorities.length; j++)
            {
                TransactionPriority bonusChargebackTransactionPriority = bonusChargebackTransactionPriorities[j];

                String bonusChargebackTransactionTypeCT = bonusChargebackTransactionPriority.getTransactionTypeCT();

                BonusCommissionHistory[] bonusCommissionHistories = new AgentBonusContributionStrategy(bonusProgram, this).getContributingBonusCommissionHistories(bonusChargebackTransactionTypeCT, bonusProcess);

                for (int k = 0; k < bonusCommissionHistories.length; k++)
                {
                    BonusCommissionHistory bonusCommissionHistory = null;

                    try
                    {
                        bonusCommissionHistory = bonusCommissionHistories[k];

                        bonusCommissionHistory.updateForNotTakenFullSurrender(bonusProcess, bonusChargebackTransactionPriority);
                    }
                    catch (Exception e)
                    {
                        String agentNumber = bonusCommissionHistory.getParticipatingAgent().getAgent().getAgentNumber();

                        String contractNumber = bonusCommissionHistory.getCommissionHistory().getEDITTrxHistory().getEDITTrx().getContractNumber();

                        logErrorToDatabase(e, agentNumber, contractNumber, bonusCommissionHistory.getTransactionTypeCT());
                    }
                }
            }
        }
    }

    /**
     * Creates Check Transactions for all ParticipatingAgents.
     *
     * @param theProcessDate
     * @param operator
     */
    public static final void processAgentBonusChecks(String theProcessDate, String frequencyCT, String operator)
    {
        EDITDate processDate = new EDITDate(theProcessDate);

        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_PROCESS_AGENT_BONUS_CHECKS).tagBatchStart(Batch.BATCH_JOB_PROCESS_AGENT_BONUS_CHECKS, "Agent Bonus Check");

        ParticipatingAgent participatingAgent = null;

        try
        {
            ParticipatingAgent[] candidateParticipatingAgents = ParticipatingAgent.findBy_GrossAmountGTZero_FrequencyCT(frequencyCT);

            for (int i = 0; i < candidateParticipatingAgents.length; i++)
            {
                try
                {
                    // Looking it up again looks silly, but we are in a batch process, the Session is cleared (below) for
                    // performance reasons, and it may have to be reloaded.
                    participatingAgent = ParticipatingAgent.findByPK(candidateParticipatingAgents[i].getParticipatingAgentPK());

                    BonusCheckTrx bonusCheckTrx = participatingAgent.generateBonusCheckTrx(processDate, operator);

                    if (bonusCheckTrx != null)
                    {
                        bonusCheckTrx.execute(processDate);
                    }

                    SessionHelper.beginTransaction(ParticipatingAgent.DATABASE);

                    SessionHelper.commitTransaction(ParticipatingAgent.DATABASE);

                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_PROCESS_AGENT_BONUS_CHECKS).updateSuccess();
                }
                catch (Exception e)
                {
                    SessionHelper.rollbackTransaction(ParticipatingAgent.DATABASE);

                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_PROCESS_AGENT_BONUS_CHECKS).updateFailure();

                    System.out.println(e);

                    e.printStackTrace();

                    LogEvent logEvent = new LogEvent("Error Generator Agent Bonus Check", e);

                    Logger logger = Logging.getLogger(Logging.BATCH_JOB);

                    logger.error(logEvent);

                    //  Log error to database
                    EDITMap columnInfo = new EDITMap("ProcessDate", new EDITDate().getFormattedDate());
                    columnInfo.put("AgentNumber", participatingAgent.getAgent().getAgentNumber());

                    Log.logToDatabase(Log.RUN_AGENT_BONUS_CHECKS, e.getMessage(), columnInfo);
                }
                finally
                {
                    // For Batch performance reasons.
                    SessionHelper.clearSessions();
                }
            }
        }
        finally
        {
            BonusProgram.generateNextCheckDate(processDate, frequencyCT);

            SessionHelper.clearSessions();

            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_PROCESS_AGENT_BONUS_CHECKS).tagBatchStop();
        }
    }

    /**
     * Generates a BonusCheckTrx if there is a net Total Bonus Amount.
     *
     * @return BonusCheckTrx null if there was no Total Bonus Amount
     */
    private BonusCheckTrx generateBonusCheckTrx(EDITDate processDate, String operator)
    {
        BonusCheckTrx bonusCheckTrx = null;

        BonusAmount bonusAmount = new AgentBonusStrategy(this, processDate).calculateBonus();

        if (bonusAmount.getTotalBonusAmount().isGT("0"))
        {
            bonusCheckTrx = new BonusCheckTrx(this, bonusAmount.getTotalBonusAmount(), operator, bonusAmount.getTotalBonusCommissionAmount(), bonusAmount.getTotalExcessBonusCommissionAmount());

            setLastCheckDateTime(new EDITDateTime());

            setLastCheckAmount(bonusAmount.getTotalBonusAmount());
        }

        return bonusCheckTrx;
    }

    /**
     * Convenience method to get the associated Agent.
     *
     * @return
     */
    public Agent getAgent()
    {
        return getPlacedAgent().getAgentContract().getAgent();
    }

    /**
     * Finder. The resulting ParticipatingAgent[] array is filtered to return only unique ParticipatingAgents
     * before returning.
     *
     * @param processDate
     * @param frequencyCT
     *
     * @return
     */
    private static ParticipatingAgent[] findBy_GrossAmountGTZero_FrequencyCT(String frequencyCT)
    {
        String hql = " select participatingAgent" +
                " from ParticipatingAgent participatingAgent" +
                " join participatingAgent.BonusProgram bonusProgram" +
                " join participatingAgent.BonusCommissionHistories bonusCommissionHistory" +
                " where bonusCommissionHistory.BonusUpdateStatus is null" +
                " and bonusProgram.FrequencyCT = :frequencyCT";

        Map params = new HashMap();

        params.put("frequencyCT", frequencyCT);

        // Get only unique ParticipatingAgents.
        List results = SessionHelper.makeUnique(SessionHelper.executeHQL(hql, params, ParticipatingAgent.DATABASE));

        return (ParticipatingAgent[]) results.toArray(new ParticipatingAgent[results.size()]);
    }

    /**
     * Finder.
     *
     * @param agentPK
     *
     * @return
     */
    public final static ParticipatingAgent[] findBy_AgentPK(long agentPK)
    {
        String hql = "select pa from ParticipatingAgent pa join pa.PlacedAgent pla join pla.AgentContract ac " + "join ac.Agent a where a.AgentPK = :agentPK";

        Map params = new HashMap();

        params.put("agentPK", new Long(agentPK));

        List results = SessionHelper.executeHQL(hql, params, ParticipatingAgent.DATABASE);

        return (ParticipatingAgent[]) results.toArray(new ParticipatingAgent[results.size()]);
    }

    /**
     * Builds a BonusCommissionHistory entity mapping this ParticipatingAgent to the specified CommissionHistory.
     *
     * @param commissionHistory
     */
    private void generateBonusCommissionHistory(CommissionHistory commissionHistory)
    {
        // Shouldn't have to hit the db at this point since records previously acquired.
        EDITTrx editTrx = commissionHistory.getEDITTrxHistory().getEDITTrx();
        String transactionTypeCT = editTrx.getTransactionTypeCT();

        EDITBigDecimal grossAmount = commissionHistory.getEDITTrxHistory().getFinancialHistory().getGrossAmount();

        // Positive or negative gross amount?
        if (commissionHistory.getCommissionTypeCT().equals(CommissionHistory.COMMISSIONTYPECT_CHARGEBACK) ||
            commissionHistory.getCommissionTypeCT().equals(CommissionHistory.COMMISSIONTYPECT_TERM_ADVANCE_CHARGEBACK))
        {
            grossAmount = grossAmount.negate();
        }

        AgentHierarchy agentHierarchy = commissionHistory.getAgentSnapshot().getAgentHierarchy();
        AgentHierarchyAllocation agentHierarchyAllocation = AgentHierarchyAllocation.findActiveByAgentHierarchyFK_Date(
                    agentHierarchy.getAgentHierarchyPK(), new EDITDate(editTrx.getEffectiveDate()));

        if (agentHierarchyAllocation != null)
        {
            EDITBigDecimal splitPercent = agentHierarchyAllocation.getAllocationPercent();

            EDITBigDecimal bonusAmount = grossAmount.multiplyEditBigDecimal(splitPercent);

            BonusCommissionHistory bonusCommissionHistory = BonusCommissionHistory.build(bonusAmount, transactionTypeCT, BonusCommissionHistory.ACCOUNTING_PENDING_STATUS_NO);

            add(bonusCommissionHistory);

            commissionHistory.add(bonusCommissionHistory);
        }
    }

    /**
     * Adder.
     *
     * @param bonusCommissionHistory
     */
    public void add(BonusCommissionHistory bonusCommissionHistory)
    {
        getBonusCommissionHistories().add(bonusCommissionHistory);

        bonusCommissionHistory.setParticipatingAgent(this);
    }


    /**
     * Generates a statement for this ParticipatingAgent in regards to the BonusCommissions for the specified contractCodeCT
     * and processDate.
     *
     * @param contractCodeCT
     *
     * @return
     */
    public BonusStatement generateBonusStatement(String contractCodeCT, EDITDate processDate) throws EDITAgentException
    {
        BonusStatement bonusStatement = null;

        EDITDateTime statementDateTime = new EDITDateTime();

        bonusStatement = new BonusStatement(this, contractCodeCT, processDate, statementDateTime);

        boolean statementGenerated = bonusStatement.generateBonusStatement();

        if (!statementGenerated)
        {
            bonusStatement = null;
        }
        else
        {
            setLastStatementDateTime(statementDateTime);

            hSave(); // The LastStatementDateTime has been updated.
        }

        return bonusStatement;
    }

    /**
     * Generates all Bonus Commission Statements for all ParticipatingAgents associated with the specified contractCodeCT.
     * A return value of true implies no errors were encountered, false otherwise.
     *
     * @param contractCodeCT
     * @param theProcessDate
     */
    public static final boolean generateBonusCommissionStatements(String contractCodeCT, String theProcessDate, String mode)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_GENERATE_BONUS_COMMISSION_STATEMENTS).tagBatchStart(Batch.BATCH_JOB_GENERATE_BONUS_COMMISSION_STATEMENTS, "Bonus Commission Statement");

        EDITDate processDate = new EDITDate(theProcessDate);

        boolean successfulRun = true;

        try
        {
            ParticipatingAgent[] participatingAgents = ParticipatingAgent.findBy_ContractCodeCT_Mode(contractCodeCT, mode);

            if (participatingAgents != null)
            {
                String fileName = getFileName();

                XMLReportWriter writer = new XMLReportWriter(BonusCommissionStatementVO.class, fileName);

                try
                {
                    SessionHelper.beginTransaction(ParticipatingAgent.DATABASE);

                    for (int i = 0; i < participatingAgents.length; i++)
                    {
                        ParticipatingAgent participatingAgent = null;

                        try
                        {
                            participatingAgent = participatingAgents[i];

                            BonusStatement bonusStatement = participatingAgent.generateBonusStatement(contractCodeCT, processDate);

                            if (bonusStatement != null)
                            {
                                writer.writeVO(bonusStatement.getBonusStatementVO());
                            }

                            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_GENERATE_BONUS_COMMISSION_STATEMENTS).updateSuccess();
                        }
                        catch (Exception e)
                        {
                            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_GENERATE_BONUS_COMMISSION_STATEMENTS).updateFailure();

                            successfulRun = false;

                            System.out.println(e);

                            e.printStackTrace();

                            Agent agent = participatingAgent.getPlacedAgent().get_AgentContract().get_Agent();

                            String agentNumber = agent.getAgentNumber();

                            String message = "Error With Bonus Commission Statements For Agent # [" + agentNumber + "]";

                            LogEvent event = new LogEvent(message, e);

                            Logger logger = Logging.getLogger(Logging.GENERAL_EXCEPTION);

                            logger.error(event);

                             //  Log error to database
                            Log.logGeneralExceptionToDatabase(message, e);

                            EDITMap columnInfo = new EDITMap("ProcessDate", new EDITDate().getFormattedDate());
                            columnInfo.put("AgentNumber", agentNumber);

                            Log.logToDatabase(Log.BONUS_COMMISSION_STATEMENTS, message, columnInfo);
                        }
                    }

                    SessionHelper.commitTransaction(ParticipatingAgent.DATABASE);
                    SessionHelper.clearSessions();
                }
                finally
                {
                    writer.close();
                }
            }
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_GENERATE_BONUS_COMMISSION_STATEMENTS).tagBatchStop();
        }

        return successfulRun;
    }

    /**
     * The hard-coded file name for this export file.
     *
     * @return
     */
    private static String getFileName()
    {
        String fileName;
        EDITExport editExport = ServicesConfig.getEDITExport("ExportDirectory1");

        String dir = editExport.getDirectory();

        fileName = dir + UtilFile.DIRECTORY_DELIMITER + "BonusCommissionStatementVO" + "_" + System.currentTimeMillis() + ".xml";
        return fileName;
    }


    /**
     * Finder.
     *
     * @param contractCodeCT
     * @param mode
     *
     * @return
     */
    private static final ParticipatingAgent[] findBy_ContractCodeCT_Mode(String contractCodeCT, String mode)
    {
        String hql = "select participatingAgent from ParticipatingAgent participatingAgent " +
                "join participatingAgent.BonusProgram bonusProgram " +
                "join fetch participatingAgent.PlacedAgent placedAgent " +
                "where bonusProgram.ContractCodeCT = :contractCodeCT " +
                "and bonusProgram.FrequencyCT = :modeCT";

        Map params = new HashMap();

        params.put("contractCodeCT", contractCodeCT);
        params.put("modeCT", mode);

        List results = SessionHelper.executeHQL(hql, params, ParticipatingAgent.DATABASE);

        return (ParticipatingAgent[]) results.toArray(new ParticipatingAgent[results.size()]);
    }


    /**
     * @see interface#hSave()
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, ParticipatingAgent.DATABASE);
    }

    /**
     * Deletes this ParticipatingAgent and removes all associations with all parents.
     */
    public void hDelete()
    {
        BonusProgram bonusProgram = getBonusProgram();

        bonusProgram.getParticipatingAgents().remove(this);

        setBonusProgram(null);

        SessionHelper.saveOrUpdate(bonusProgram, ParticipatingAgent.DATABASE);

        PlacedAgent placedAgent = getPlacedAgent();

        placedAgent.getParticipatingAgents().remove(this);

        setPlacedAgent(null);

        SessionHelper.saveOrUpdate(placedAgent, ParticipatingAgent.DATABASE);

        SessionHelper.delete(this, ParticipatingAgent.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return ParticipatingAgent.DATABASE;
    }

    /**
     * Sums all ParticipatingAgent.BonusCommissionHistory.Amount
     * CommissionHistory.EDITTrxHistory.FinancialHistory.GrossAmount
     * where the BonusCommissionHistory.BonusUpdateStatus is null.
     *
     * @return the sum total of FinancialHistory.GrossAmount
     */
    public EDITBigDecimal getCumulativeGrossAmount()
    {
        EDITBigDecimal cumulativeGrossAmount = new EDITBigDecimal("0.00");

        String hql = "select sum(bonusCommissionHistory.Amount) " +
                " from ParticipatingAgent participatingAgent" +
                " join participatingAgent.BonusCommissionHistories bonusCommissionHistory" +
                " where participatingAgent = :participatingAgent" +
                " and bonusCommissionHistory.BonusUpdateStatus is null";

        Map params = new HashMap();

        params.put("participatingAgent", this);

        List results = SessionHelper.executeHQL(hql, params, ParticipatingAgent.DATABASE);

        if (!results.isEmpty())
        {
            if (results.get(0) != null)
            {
                cumulativeGrossAmount = new EDITBigDecimal((String) results.get(0).toString());
            }
        }

        return cumulativeGrossAmount;
    }

    /**
     * Premium Levels are associated indirectly via BonusProgram, or directly as overrides for this ParticipatingAgent.
     *
     * @return Set of PremiumLevels attained via BonusProgram or directly sorted by
     */
    public PremiumLevel[] getBizPremiumLevels()
    {
        Set premiumLevels = null;

        if (getBonusProgramOverrideInd().equalsIgnoreCase("Y"))
        {
            premiumLevels = getPremiumLevels();
        }
        else if (getBonusProgramOverrideInd().equalsIgnoreCase("N"))
        {
            premiumLevels = getBonusProgram().getPremiumLevels();
        }

        PremiumLevel[] sortedPremiumLevels = (PremiumLevel[]) premiumLevels.toArray(new PremiumLevel[premiumLevels.size()]);

        // Sort by PremiumLevel.IssuePremiumLevel ASC
        Arrays.sort(sortedPremiumLevels, new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                PremiumLevel premiumLevel1 = (PremiumLevel) o1;

                BigDecimal issuePremiumLevel1 = premiumLevel1.getIssuePremiumLevel().getBigDecimal();

                PremiumLevel premiumLevel2 = (PremiumLevel) o2;

                BigDecimal issuePremiumLevel2 = premiumLevel2.getIssuePremiumLevel().getBigDecimal();

                return issuePremiumLevel1.compareTo(issuePremiumLevel2);
            }
        });

        return sortedPremiumLevels;
    }

    public PremiumLevel[] getBizAppliedPremiumLevels()
    {
        PremiumLevel[] bizPremiumLevels = getBizPremiumLevels();
        List premiumLevelsToUse = new ArrayList();

        for (int i = 0; i < bizPremiumLevels.length; i++)
        {
            PremiumLevel bizPremiumLevel = bizPremiumLevels[i];
            AppliedPremiumLevel[] appliedPremiumLevels = AppliedPremiumLevel.findByParticipatingAgent_PremiumLevel(this, bizPremiumLevel);
            if (appliedPremiumLevels.length > 0)
            {
                premiumLevelsToUse.add(bizPremiumLevel);
            }
        }

        return (PremiumLevel[]) premiumLevelsToUse.toArray(new PremiumLevel[premiumLevelsToUse.size()]);
    }

    /**
     * Convenience method to get the associated ProductStructures via its parent BonusProgram.
     *
     * @return
     */
    public ProductStructure[] getProductStructures()
    {
        return getBonusProgram().getProductStructures();
    }

    /**
     * The cumulative sum of FinancialHistory.GrossAmount for the specified ProductStructure via the
     * BonusCommissionHistory where BonusCommissionHistory.BonusUpdateStatus is null.
     *
     * @param productStructure
     *
     * @return the cumulative sum of FinancialHistory.GrossAmount
     */
    public EDITBigDecimal getCumulativeGrossAmount(ProductStructure productStructure)
    {
        EDITBigDecimal cumulativeGrossAmount = new EDITBigDecimal("0.00");

        String hql = "select sum(bonusCommissionHistory.Amount) " +
                " from ParticipatingAgent participatingAgent" +
                " join participatingAgent.BonusCommissionHistories bonusCommissionHistory" +
                " join bonusCommissionHistory.CommissionHistory commissionHistory" +
                " join commissionHistory.EDITTrxHistory editTrxHistory" +
                " join editTrxHistory.EDITTrx editTrx" +
                " join editTrxHistory.FinancialHistories financialHistory" +
                " join editTrx.ClientSetup clientSetup" +
                " join clientSetup.ContractSetup contractSetup" +
                " join contractSetup.Segment segment" +
                " where participatingAgent = :participatingAgent" +
                " and bonusCommissionHistory.BonusUpdateStatus is null" +
                " and segment.ProductStructureFK = :productStructureFK";

        Map params = new HashMap();

        params.put("participatingAgent", this);

        params.put("productStructureFK", productStructure.getProductStructurePK());

        List results = SessionHelper.executeHQL(hql, params, ParticipatingAgent.DATABASE);

        if (!results.isEmpty())
        {
            //On a sum, results is not empty but can contains null
            if (!results.contains(null))
            {
               cumulativeGrossAmount = new EDITBigDecimal((String)results.get(0).toString());
            }
        }


        return cumulativeGrossAmount;
    }

    /**
     * A convenience method to get the identifiers (PKs) of the associated ParticipatingAgents instead of the
     * ParticipatingAgents directly. This method will likely be used for controlling resource consumption in batch jobs.
     *
     * @return
     */
    public static Long[] findParticipatingAgentPKsBy_BonusProgramPK(Long bonusProgramPK)
    {
        String hql = " select participatingAgent.ParticipatingAgentPK from ParticipatingAgent participatingAgent" +
                " where participatingAgent.BonusProgramFK = :bonusProgramFK";

        Map params = new HashMap();

        params.put("bonusProgramFK", bonusProgramPK);

        List pks = SessionHelper.executeHQL(hql, params, ParticipatingAgent.DATABASE);

        return (Long[]) pks.toArray(new Long[pks.size()]);
    }

    /**
     * Removes the specified BonusCommissionHistory from this ParticipatingAgent.
     * The BonusCommissionHistory's reference to ParticipatingAgent is also removed.
     *
     * @param bonusCommissionHistory
     */
    public void remove(BonusCommissionHistory bonusCommissionHistory)
    {
        getBonusCommissionHistories().remove(bonusCommissionHistory);

        bonusCommissionHistory.setParticipatingAgent(null);
    }


    private void logErrorToDatabase(Exception e, String agentNumber, String contractNumber, String transactionType)
    {
        EDITMap columnInfo = new EDITMap("ProcessDate", new EDITDate().getFormattedDate());
        columnInfo.put("ContractNumber", contractNumber);
        columnInfo.put("AgentNumber", agentNumber);
        columnInfo.put("TransactionType", transactionType);

        Log.logToDatabase(Log.UPDATE_AGENT_BONUSES, e.getMessage(), columnInfo);
    }
}
