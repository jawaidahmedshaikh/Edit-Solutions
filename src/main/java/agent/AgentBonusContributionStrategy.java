/*
 * User: gfrosti
 * Date: Feb 25, 2005
 * Time: 10:18:35 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import edit.common.*;

import edit.services.db.hibernate.SessionHelper;

import event.*;

import contract.*;

import java.util.*;

import engine.ProductStructure;



public class AgentBonusContributionStrategy
{
  private BonusProgram bonusProgram;
  private ParticipatingAgent participatingAgent;
  
  /**
   * A general marker for processing normal transactions - namely
   * EDITTrx.Status = N or A.
   */
  public static final String PROCESSING_NORMAL = "NORMAL";
  
  /**
   * A general marker for processing reversal transactions - namely
   * EDITTrx.Status = U or R.
   */  
  public static final String PROCESSING_REVERSAL = "REVERSAL";

  /**
   * The type of premium the hql is being generated for.
   */
  public static final String NORMAL_PREMIUM = "N";

  /**
   * The type of premium the hql is being generated for.
   */
  public static final String CHARGEBACK_PREMIUM = "C";
  

  public AgentBonusContributionStrategy(BonusProgram bonusProgram, ParticipatingAgent participatingAgent)
  {
    this.bonusProgram = bonusProgram;
    this.participatingAgent = participatingAgent;
  }

  /**
     * Based on how the BonusProgram has been set up, the set of contributing CommissionHistories can be decided by 16 possible
     * strategies. The options which contribute to this strategy are:
     * a) BonusProgram.BonusStartDate < EDITTrx.EffectiveDate < BonusProgram.BonusStopDate
     *      AND Segment.ApplicationReceivedDate <= BonusProgram.ApplicationReceivedStopDate
     *      AND EDITTrx.EffectiveDate <= BonusProgram.PremiumStopDate
     * b) All ProductStructures or Segment.ProductStructureFK IN (The set of specified ProductStructures)
     * c) All Premiums or only Issue premiums determined by GroupSetup.IssueTypeCT
     * d) All PlacedAgents under a specified PlacedAgent may be wanted, OR
     * e) Just the immediate PlacedAgents under a specified PlacedAgent may be wanted.
     * f) All CommissionLevels may be wanted, or CommissionProfile.CommissLevelCT IN (The set of specified CommissionLevelCTs).
     * @return
     */
  public CommissionHistory[] getContributingCommissionHistories()
  {
    AgentSnapshot[] candidateAgentSnapshots = getCandidateAgentSnapshots();

    List results = new ArrayList();

    if (candidateAgentSnapshots.length > 0)
    {
      // NOTE: Targeting normal premiums
      String hql = getHQLTargetingCommissionHistories(candidateAgentSnapshots, NORMAL_PREMIUM);

      Map params = buildHQLParams1();

      List resultsForNormalPremiums = SessionHelper.makeUnique(SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS));
      
      // NOTE: Targeting chargeback premiums
      hql = getHQLTargetingCommissionHistories(candidateAgentSnapshots, CHARGEBACK_PREMIUM);
      
      params = buildHQLParams2();
      
      List resultsForChargebackPremiumns = SessionHelper.makeUnique(SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS));
      
      // Combine the normal and chargeback premiumns
      results.addAll(resultsForNormalPremiums);
      
      results.addAll(resultsForChargebackPremiumns);
    }

    return (CommissionHistory[]) results.toArray(new CommissionHistory[results.size()]);
  }
  
  /**
   * For certain transaction types (e.g. NT, FS), it is necessary to zero-out
   * the values of the BonusCommissionHistories.
   * The PY transaction is
   * @return
   */
  public BonusCommissionHistory[] getContributingBonusCommissionHistories(String bonusChargebackTransactionTypeCT, String bonusProcess)
  {
    BonusCommissionHistory[] bonusCommissionHistories = null;
    
    String searchReason = bonusChargebackTransactionTypeCT + "N";
  
    if (bonusProcess.equals(PROCESSING_NORMAL))
    {
      bonusCommissionHistories = findBonusCommissionHistoriesForNormal_NTFS_Processing(searchReason, bonusChargebackTransactionTypeCT);
    }
    else if (bonusProcess.equals(PROCESSING_REVERSAL))
    {
      bonusCommissionHistories = findBonusCommissionHistoriesForReversal_NTFS_Processing(searchReason, bonusChargebackTransactionTypeCT);
    }
      
    return bonusCommissionHistories;
  }

  /**
     * The set of PlacedAgents that can contribute to the ParticipatingAgent's AgentBonus are Writing Agents (they must
     * be Writing Agents) that report directly or indirectly to the Participating Agent. The Participating Agent, himself,
     * is a trivial case.
     * @return
     */
  private AgentSnapshot[] getCandidateAgentSnapshots()
  {
    // We only want unique PlacedAgents.
    Map candidateAgentSnapshots = new HashMap();

    AgentHierarchy[] agentHierarchies = AgentHierarchy.findBy_PlacedAgent_V1(participatingAgent.getPlacedAgent());

    if (agentHierarchies != null)
    {
      for (int i = 0; i < agentHierarchies.length; i++)
      {
        AgentHierarchy agentHierarchy = agentHierarchies[i];

        AgentSnapshot candidateAgentSnapshot = bonusProgram.getCandidateAgentSnapshot(agentHierarchy, participatingAgent);

        if (candidateAgentSnapshot != null)
        {
          candidateAgentSnapshots.put(new Long(candidateAgentSnapshot.getPK()), candidateAgentSnapshot);
        }
      }
    }

    return (AgentSnapshot[]) candidateAgentSnapshots.values().toArray(new AgentSnapshot[candidateAgentSnapshots.size()]);
  }

  /**
     * Builds a composite hql for selecting the appropriate contributing PlacedAgents.
     * @param premiumType signifies if we are targeting the normal or chargeback premiumns
     * @see AgentBonusContributionStrategy#NORMAL_PREMIUM
     * @see AgentBonusContributionStrategy#CHARGEBACK_PREMIUM
     * @return
     */
  protected String getHQLTargetingCommissionHistories(AgentSnapshot[] agentSnapshots, String premiumType)
  {
    String hql = null;

    // The initial innerjoin.
    hql = getHQLForInitialInnerJoin();

    // Need the Inner Join part, if any.
    if (!bonusProgram.includeAdditionalPremium())
    {
      hql += getHQLForDontIncludeAdditionalPremiumInnerJoin();
    }

    // The initial where clause.
    hql += getHQLForInitialWhere(premiumType);

    hql += getHQLForContributingProducts();

    // Need the And part as a continuation of the above Inner Join, if any.
    if (!bonusProgram.includeAdditionalPremium())
    {
      hql += getHQLForDontIncludeAdditionalPremiumAnd();
    }

    hql += getHQLForCandidateContributingAgentSnapshots(agentSnapshots);

    return hql;
  }

  /**
     * It is possible that queryA must be further filtered by productStructures.
     * @return
     */
  private String getHQLForContributingProducts()
  {
    ProductStructure[] productStructures = bonusProgram.getProductStructures();

    String hql = " and segment.ProductStructureFK in (";

    for (int i = 0; i < productStructures.length; i++)
    {
      Long productStructurePK = productStructures[i].getProductStructurePK();

      hql += productStructurePK.toString();

      if (i < (productStructures.length - 1))
      {
        hql += ", ";
      }
    }

    hql += ")";

    return hql;
  }

  /**
     * The speficied ParticipatingAgents has already been filtered by ParticipatingAgent, CommissionProfile, and ProductStructure. It
     * is now necessary to include these in the following HQL for CommissionHistories.
     * @param agentSnapshots
     */
  private String getHQLForCandidateContributingAgentSnapshots(AgentSnapshot[] agentSnapshots)
  {
    String hql = " and agentSnapshot.AgentSnapshotPK in (";

    for (int i = 0; i < agentSnapshots.length; i++)
    {
      AgentSnapshot agentSnapshot = agentSnapshots[i];

      Long agentSnapshotPK = agentSnapshot.getAgentSnapshotPK();

      hql += agentSnapshotPK.toString();

      if (i != (agentSnapshots.length - 1))
      {
        hql += ", ";
      }
    }

    hql += ")";

    return hql;
  }

  /**
     * The initial WHERE clause compares date parameters specified in the BonusProgram with date parameters specified in the
     * Segment and EDITTrx of the initial JOIN (queryA).
     * @param premiumType specified as normal or chargback - this will vary the final query
     * @return the desired hql including all the 'where' parameters
     */
  private String getHQLForInitialWhere(String premiumType)
  {
    String hql = null;

    if (premiumType.equals(NORMAL_PREMIUM))
    {
      // Note: using editTrx.EffectiveDate
      // Note: editTrx.Status = 'N' or 'A'
      hql = 
          " where (:bonusStartDate <= editTrx.EffectiveDate" + 
          " and editTrx.EffectiveDate <= :bonusStopDate" + 
          " and editTrx.EffectiveDate <= :nextCheckDate)" + 
          " and segment.ApplicationReceivedDate <= :applicationReceivedStopDate" + 
          " and editTrx.EffectiveDate < :premiumStopDate" + 
          " and financialHistory.GrossAmount > 0" + 
          " and editTrx.TransactionTypeCT = 'PY'" + " and editTrx.Status in ('N', 'A')" + 
          " and commissionHistory.PlacedAgentFK = commissionHistory.SourcePlacedAgentFK" + 
          " and commissionHistory.CommissionHistoryPK not in (" + " select bonusCommissionHistory.CommissionHistoryFK" + 
          " from BonusCommissionHistory bonusCommissionHistory" + 
          " join bonusCommissionHistory.ParticipatingAgent participatingAgent" + 
          " where participatingAgent.ParticipatingAgentPK = :participatingAgentPK)";
    }
    else if (premiumType.equals(CHARGEBACK_PREMIUM))
    {
      hql = 
          " where (:bonusStartDate <= editTrxHistory.ProcessDateTime" + 
          " and editTrxHistory.ProcessDateTime <= :bonusStopDate" + 
          " and editTrxHistory.ProcessDateTime <= :nextCheckDate)" + 
          " and segment.ApplicationReceivedDate <= :applicationReceivedStopDate" + 
          " and editTrxHistory.ProcessDateTime < :premiumStopDate" + 
          " and financialHistory.GrossAmount > 0" + 
          " and editTrx.TransactionTypeCT = 'PY'" + " and editTrx.Status in ('R', 'U')" + 
          " and commissionHistory.PlacedAgentFK = commissionHistory.SourcePlacedAgentFK" + 
          " and commissionHistory.CommissionHistoryPK not in (" + " select bonusCommissionHistory.CommissionHistoryFK" + 
          " from BonusCommissionHistory bonusCommissionHistory" + 
          " join bonusCommissionHistory.ParticipatingAgent participatingAgent" + 
          " where participatingAgent.ParticipatingAgentPK = :participatingAgentPK)";
    }

    return hql;
  }


  /**
     * An elemental query that finds CommissionHistories restricted by the specified values from BonusProgram. This is the
     * inner-join part.
     * The starting point of the query relies on a JOIN between Segment.ContractSetup.ClientSetup.EDITTrx.EDITTrxHistory.CommissionHistory.PlacedAgent.
     * Along with ... EDITTrxHistory.FinancialHistory
     * Along with ... Segment.AgentHierarchy.AgentSnapshot
     * This
     * hql is this join.
     * @return
     */
  public String getHQLForInitialInnerJoin()
  {
    String hql = 
      " select commissionHistory " + " from CommissionHistory commissionHistory" + " join fetch commissionHistory.PlacedAgent placedAgent" + 
      " join fetch commissionHistory.EDITTrxHistory editTrxHistory" + " join fetch editTrxHistory.EDITTrx editTrx" + 
      " join fetch editTrxHistory.FinancialHistories financialHistory" + " join fetch editTrx.ClientSetup clientSetup" + 
      " join fetch clientSetup.ContractSetup contractSetup" + " join fetch contractSetup.Segment segment" + 
      " join segment.AgentHierarchies agentHierarchy" + " join agentHierarchy.AgentSnapshots agentSnapshot";

    return hql;
  }

  /**
     * Is is possible that queryA must be further filtered by Issue Premiums only. This is the inner-join part.
     * Bonuses may only want to include the issue premium (Inner Join part)
     * @return
     */
  public String getHQLForDontIncludeAdditionalPremiumInnerJoin()
  {
    String hql = " join contractSetup.GroupSetup groupSetup";

    return hql;
  }

  /**
     * Is is possible that queryA must be further filtered by Issue Premiums only. This is the AND part.
     * Bonuses may only want to include the issue premium (And part)
     * @return
     */
  public String getHQLForDontIncludeAdditionalPremiumAnd()
  {
    String hql = " and groupSetup.PremiumTypeCT = 'Issue'";

    return hql;
  }

  
  /**
   * Formatted to find <b>all</b> BonusCommissionHistories associated with
   * certain transaction types (e.g. FT, NT) defined in the TransactionPriority
   * table. The BonusCommissionHistories are not picked-up a second time
   * by having marked them.
   * @param trxWithChargebacks the trxTypes that have BonusChargebacks.
   * @return the formatted hql
   */
  private String getHQLTargetingBonusCommissionHistories(String[] trxWithChargebacks, String bonusChargeback)
  {
    String hql = " select bonusCommissionHistory" + 
                  " from Segment segment" + 
                  " join segment.ContractClients contractClient" +
                  " join contractClient.ClientSetups clientSetup" +
                  " join clientSetup.EDITTrxs editTrx" +
                  " join editTrx.EDITTrxHistories editTrxHistory" +
                  " join editTrxHistory.CommissionHistories commissionHistory" +
                  " join commissionHistory.BonusCommissionHistories bonusCommissionHistory" +
                  
                  " where (:bonusStartDate <= editTrx.EffectiveDate" + 
                  " and editTrx.EffectiveDate <= :bonusStopDate" + 
                  " and editTrx.EffectiveDate <= :nextCheckDate)" + 
                  " and segment.ApplicationReceivedDate <= :applicationReceivedStopDate" + 
                  " and editTrx.EffectiveDate < :premiumStopDate" +

                  " and editTrx.TransactionTypeCT in (";
                  
                  for (int i = 0; i < trxWithChargebacks.length; i++)
                  {
                    String transactionTypeCT = trxWithChargebacks[i];
                    
                    hql += transactionTypeCT;
                    
                    if (i < (trxWithChargebacks.length - 1))
                    {
                      hql += transactionTypeCT + ", ";
                    }
                  }
                  
                  hql += ")";
                  
    if (bonusChargeback.equals(TransactionPriority.BONUS_CHARGEBACK_FIRSTYEARONLY))
    {
      hql += " editTrx.EffectiveDate < :segmentEffectiveDate";
    }
                  
    return hql;                
  }
  
  /**
   * Params used for finding contributing CommissionHistories for the Normal Premiums.
   * They are:
   * BonusProgram.BonusStartDate
   * BonusProgram.BonusStopDate
   * BonusProgram.ApplicationReceivedStopDate
   * BonusProgram.PremiumStopDate
   * BonusProgram.NextCheckDate
   * ParticipatingAgent.ParticipatingAgentPK
   * Note: If the BonusProgram.NextCheckDate is null, then it is set to 
   * the EDITDate.DEFAULT_MAX_DATE.
   * @return the hql parameters
   */
  private Map buildHQLParams1()
  {
    Map params = new HashMap();
  
    EDITDate bonusStartDate = bonusProgram.getBonusStartDate();
    EDITDate bonusStopDate = bonusProgram.getBonusStopDate();
    EDITDate applicationReceivedStopDate = bonusProgram.getApplicationReceivedStopDate();
    EDITDate premiumStopDate = bonusProgram.getPremiumStopDate();
    EDITDate nextCheckDate = bonusProgram.getNextCheckDate();
    Long participatingAgentPK = participatingAgent.getParticipatingAgentPK();    
    
    if (nextCheckDate == null)
    {
      nextCheckDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
    }    

    params.put("bonusStartDate", bonusStartDate);
    params.put("bonusStopDate", bonusStopDate);
    params.put("nextCheckDate", nextCheckDate);
    params.put("applicationReceivedStopDate", applicationReceivedStopDate);
    params.put("premiumStopDate", premiumStopDate);
    params.put("participatingAgentPK", participatingAgentPK);    
    
    return params;
  }
  
  /**
   * Params used for finding contributing CommissionHistories for the Reversed Premiums.
   * They are:
   * BonusProgram.BonusStartDate
   * BonusProgram.BonusStopDate
   * BonusProgram.ApplicationReceivedStopDate
   * BonusProgram.PremiumStopDate
   * BonusProgram.NextCheckDate
   * ParticipatingAgent.ParticipatingAgentPK
   * Note: If the BonusProgram.NextCheckDate is null, then it is set to 
   * the EDITDate.DEFAULT_MAX_DATE.
   * Note: This query is driven by the EDITTrxHistory.ProcessDateTime. Because
   * of this, a number of the EDITDate parameters have to be converted to EDITDateTime
   * parameters.
   * @return the hql parameters
   */
  private Map buildHQLParams2()
  {
    // Note: using editTrxHistory.ProcessDateTime
    // Note: editTrx.Status = 'R'
    
    // We need to compare "apples" to "apples". Since the driving date is now
    // a date-time, we are forced to convert the specified parameters into EDITDateTime.
    
     Map params = new HashMap();
     
     EDITDate bonusStartDate = bonusProgram.getBonusStartDate();
     EDITDate bonusStopDate = bonusProgram.getBonusStopDate();
     EDITDate applicationReceivedStopDate = bonusProgram.getApplicationReceivedStopDate();
     EDITDate premiumStopDate = bonusProgram.getPremiumStopDate();
     EDITDate nextCheckDate = bonusProgram.getNextCheckDate();
     Long participatingAgentPK = participatingAgent.getParticipatingAgentPK();    
     
     if (nextCheckDate == null)
     {
       nextCheckDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
     }       
    
    EDITDateTime bonusStartDateTime = new EDITDateTime(bonusStartDate.getFormattedDate() + " " + EDITDateTime.DEFAULT_MIN_TIME);
    
    EDITDateTime bonusStopDateTime = new EDITDateTime(bonusStopDate.getFormattedDate() + " " + EDITDateTime.DEFAULT_MAX_TIME);

    EDITDateTime nextCheckDateTime = new EDITDateTime(nextCheckDate.getFormattedDate() + " " + EDITDateTime.DEFAULT_MAX_TIME);
    
    EDITDateTime premiumnStopDateTime = new EDITDateTime(premiumStopDate.getFormattedDate() + " " + EDITDateTime.DEFAULT_MAX_TIME);
    
    params.put("bonusStartDate", bonusStartDateTime);
    params.put("bonusStopDate", bonusStopDateTime);
    params.put("nextCheckDate", nextCheckDateTime);
    params.put("applicationReceivedStopDate", applicationReceivedStopDate);
    params.put("premiumStopDate", premiumnStopDateTime);
    params.put("participatingAgentPK", participatingAgentPK);    
    
    return params;
  }
  
  /**
   * Params used for finding BonusCommissionHistories for the NT/FS normal processing.
   * They are:
   * BonusProgram.BonusStartDate
   * BonusProgram.BonusStopDate
   * BonusProgram.ApplicationReceivedStopDate
   * BonusProgram.PremiumStopDate
   * BonusProgram.NextCheckDate
   * ParticipatingAgent.ParticipatingAgentPK
   * BonusCommissionHistory.UpdateReason
   * Note: If the BonusProgram.NextCheckDate is null, then it is set to 
   * the EDITDate.DEFAULT_MAX_DATE.
   * @return the hql parameters
   */
  private Map buildHQLParams4(String updateReason, String transactionTypeCT)
  {
    Map params = new HashMap();
  
    EDITDate bonusStartDate = bonusProgram.getBonusStartDate();
    EDITDate bonusStopDate = bonusProgram.getBonusStopDate();
    EDITDate applicationReceivedStopDate = bonusProgram.getApplicationReceivedStopDate();
    EDITDate premiumStopDate = bonusProgram.getPremiumStopDate();
    EDITDate nextCheckDate = bonusProgram.getNextCheckDate();
    
    if (nextCheckDate == null)
    {
      nextCheckDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
    }    

    params.put("bonusStartDate", bonusStartDate);
    params.put("bonusStopDate", bonusStopDate);
    params.put("nextCheckDate", nextCheckDate);
    params.put("applicationReceivedStopDate", applicationReceivedStopDate);
    params.put("premiumStopDate", premiumStopDate);
    params.put("participatingAgent", participatingAgent);  
    params.put("updateReason", updateReason);
    params.put("transactionTypeCT", transactionTypeCT);
    
    return params;
  }  
  
  /**
   * Params used for finding BonusCommissionHistories for the NT/FS reversal processing.
   * They are:
   * BonusProgram.BonusStartDate
   * BonusProgram.BonusStopDate
   * BonusProgram.ApplicationReceivedStopDate
   * BonusProgram.PremiumStopDate
   * BonusProgram.NextCheckDate
   * ParticipatingAgent.ParticipatingAgentPK
   * BonusCommissionHistory.UpdateReason
   * Note: If the BonusProgram.NextCheckDate is null, then it is set to 
   * the EDITDate.DEFAULT_MAX_DATE.
   * @return the hql parameters
   */
  private Map buildHQLParams5(String updateReason, String transactionTypeCT)
  {
    // Note: using editTrxHistory.ProcessDateTime
    // Note: editTrx.Status = 'R'

    // We need to compare "apples" to "apples". Since the driving date is now
    // a date-time, we are forced to convert the specified parameters into EDITDateTime.

    Map params = new HashMap();

    EDITDate bonusStartDate = bonusProgram.getBonusStartDate();
    EDITDate bonusStopDate = bonusProgram.getBonusStopDate();
    EDITDate applicationReceivedStopDate = bonusProgram.getApplicationReceivedStopDate();
    EDITDate premiumStopDate = bonusProgram.getPremiumStopDate();
    EDITDate nextCheckDate = bonusProgram.getNextCheckDate();

    if (nextCheckDate == null)
    {
      nextCheckDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
    }

    EDITDateTime bonusStartDateTime =
      new EDITDateTime(bonusStartDate.getFormattedDate() + " " + EDITDateTime.DEFAULT_MIN_TIME);

    EDITDateTime bonusStopDateTime =
      new EDITDateTime(bonusStopDate.getFormattedDate() + " " + EDITDateTime.DEFAULT_MAX_TIME);

    EDITDateTime nextCheckDateTime =
      new EDITDateTime(nextCheckDate.getFormattedDate() + " " + EDITDateTime.DEFAULT_MAX_TIME);

    EDITDateTime premiumnStopDateTime =
      new EDITDateTime(premiumStopDate.getFormattedDate() + " " + EDITDateTime.DEFAULT_MAX_TIME);

    params.put("bonusStartDate", bonusStartDateTime);
    params.put("bonusStopDate", bonusStopDateTime);
    params.put("nextCheckDate", nextCheckDateTime);
    params.put("applicationReceivedStopDate", applicationReceivedStopDate);
    params.put("premiumStopDate", premiumnStopDateTime);
    params.put("participatingAgent", participatingAgent);
    params.put("updateReason", updateReason);
    params.put("transactionTypeCT", transactionTypeCT);    

    return params;
  }    
  
  /**
   * Params used for finding BonusCommissionHistories for the NT/FS normal processing.
   * They are:
   * BonusProgram.BonusStartDate
   * BonusProgram.BonusStopDate
   * BonusProgram.ApplicationReceivedStopDate
   * BonusProgram.PremiumStopDate
   * BonusProgram.NextCheckDate
   * ParticipatingAgent.ParticipatingAgentPK
   * BonusCommissionHistory.UpdateReason
   * Segment
   * Note: If the BonusProgram.NextCheckDate is null, then it is set to 
   * the EDITDate.DEFAULT_MAX_DATE.
   * @return the hql parameters
   */
  private Map buildHQLParams6(BonusCommissionHistory bonusCommissionHistory, String transactionTypeCT)
  {
    Map params = new HashMap();
  
    EDITDate bonusStartDate = bonusProgram.getBonusStartDate();
    EDITDate bonusStopDate = bonusProgram.getBonusStopDate();
    EDITDate applicationReceivedStopDate = bonusProgram.getApplicationReceivedStopDate();
    EDITDate premiumStopDate = bonusProgram.getPremiumStopDate();
    EDITDate nextCheckDate = bonusProgram.getNextCheckDate();
    
    if (nextCheckDate == null)
    {
      nextCheckDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
    }    
    
    Segment segment = Segment.findBy_BonusCommissionHistory(bonusCommissionHistory);
    
    PlacedAgent placedAgent = participatingAgent.getPlacedAgent();

    params.put("bonusStartDate", bonusStartDate);
    params.put("bonusStopDate", bonusStopDate);
    params.put("nextCheckDate", nextCheckDate);
    params.put("applicationReceivedStopDate", applicationReceivedStopDate);
    params.put("premiumStopDate", premiumStopDate);
    params.put("participatingAgent", participatingAgent);  
    params.put("transactionTypeCT", transactionTypeCT);
    params.put("segment1", segment);
    params.put("placedAgent", placedAgent);
    
    return params;
  }    

    /**
     * Finder. The ParticipatingAgent is the starting point and traverses through
     * its BonusCommissionHistory.CommissionHistory ... etc. to Segment.
     * @param updateReason
     * @return
     */
    public BonusCommissionHistory[] findBonusCommissionHistoriesForNormal_NTFS_Processing(String updateReason, String transactionTypeCT)
    {
      String hql = "select bonusCommissionHistory1" +
                  " from ParticipatingAgent participatingAgent1" +
                  " join participatingAgent1.BonusCommissionHistories bonusCommissionHistory1" +
                  " join bonusCommissionHistory1.CommissionHistory commissionHistory1" +
                  " join commissionHistory1.EDITTrxHistory editTrxHistory1" +
                  " join editTrxHistory1.EDITTrx editTrx1" +
                  " join editTrx1.ClientSetup clientSetup1" +
                  " join clientSetup1.ContractSetup contractSetup1" +
                  " join contractSetup1.Segment segment1" +
                  
                  " where participatingAgent1 = :participatingAgent" +
                  
                  " and (bonusCommissionHistory1.UpdateReason is null" +
                  " or bonusCommissionHistory1.UpdateReason <> :updateReason)" +
                  
                  " and editTrx1.TransactionTypeCT <> 'BCK'" +
                  
                  " and (:bonusStartDate <= editTrx1.EffectiveDate" + 
                  " and editTrx1.EffectiveDate <= :bonusStopDate" + 
                  " and editTrx1.EffectiveDate <= :nextCheckDate)" + 
                  " and segment1.ApplicationReceivedDate <= :applicationReceivedStopDate" + 
                  " and editTrx1.EffectiveDate < :premiumStopDate" +                 
      
                  " and  segment1.SegmentPK in (" + // See if there are associated Segments with an NT/FS transaction
      
                  " select segment2.SegmentPK" +
                  " from ParticipatingAgent participatingAgent2" +
                  " join participatingAgent2.PlacedAgent placedAgent2" +
                  " join placedAgent2.AgentSnapshots agentSnapshot2" +
                  " join agentSnapshot2.AgentHierarchy agentHierarchy2" +
                  " join agentHierarchy2.Segment segment2" +
                  " join segment2.ContractSetups contractSetup2" +
                  " join contractSetup2.ClientSetups clientSetup2" +
                  " join clientSetup2.EDITTrxs editTrx2" +
                  " join editTrx2.EDITTrxHistories editTrxHistory2" +
                  
                  " where participatingAgent2 = :participatingAgent" +
                  " and editTrx2.TransactionTypeCT = :transactionTypeCT" +
                  " and editTrx2.Status = 'N'" + 
                  
                  " and (:bonusStartDate <= editTrx2.EffectiveDate" + 
                  " and editTrx2.EffectiveDate <= :bonusStopDate" + 
                  " and editTrx2.EffectiveDate <= :nextCheckDate)" + 
                  " and segment2.ApplicationReceivedDate <= :applicationReceivedStopDate" + 
                  " and editTrx2.EffectiveDate < :premiumStopDate)";                    

      Map params = buildHQLParams4(updateReason, transactionTypeCT);

      List results = SessionHelper.makeUnique(SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS));
      
      return (BonusCommissionHistory[]) results.toArray(new BonusCommissionHistory[results.size()]);
    }

  private BonusCommissionHistory[] findBonusCommissionHistoriesForReversal_NTFS_Processing(String updateReason, String transactionTypeCT)
  {
    String hql = "select bonusCommissionHistory1" +
                " from ParticipatingAgent participatingAgent1" +
                " join participatingAgent1.BonusCommissionHistories bonusCommissionHistory1" +
                " join bonusCommissionHistory1.CommissionHistory commissionHistory1" +
                " join commissionHistory1.EDITTrxHistory editTrxHistory1" +
                " join editTrxHistory1.EDITTrx editTrx1" +
                " join editTrx1.ClientSetup clientSetup1" +
                " join clientSetup1.ContractSetup contractSetup1" +
                " join contractSetup1.Segment segment1" +
                
                " where participatingAgent1 = :participatingAgent" +
                
                " and bonusCommissionHistory1.UpdateReason = :updateReason" +
                
                " and editTrx1.TransactionTypeCT <> 'BCK'" +
                
                " and (:bonusStartDate <= editTrxHistory1.ProcessDateTime" + 
                " and editTrxHistory1.ProcessDateTime <= :bonusStopDate" + 
                " and editTrxHistory1.ProcessDateTime <= :nextCheckDate)" + 
                " and segment1.ApplicationReceivedDate <= :applicationReceivedStopDate" + 
                " and editTrxHistory1.ProcessDateTime < :premiumStopDate" +                 
    
                " and segment1.SegmentPK in (" + // See if there are associated Segments with an NT/FS transaction
    
                " select segment2.SegmentPK" +
                " from ParticipatingAgent participatingAgent2" +
                " join participatingAgent2.PlacedAgent placedAgent2" +
                " join placedAgent2.AgentSnapshots agentSnapshot2" +
                " join agentSnapshot2.AgentHierarchy agentHierarchy2" +
                " join agentHierarchy2.Segment segment2" +
                " join segment2.ContractSetups contractSetup2" +
                " join contractSetup2.ClientSetups clientSetup2" +
                " join clientSetup2.EDITTrxs editTrx2" +
                " join editTrx2.EDITTrxHistories editTrxHistory2" +
                
                " where participatingAgent2 = :participatingAgent" +
                " and editTrx2.TransactionTypeCT = :transactionTypeCT" +
                " and editTrx2.Status = 'R'" + 
                
                " and (:bonusStartDate <= editTrxHistory2.ProcessDateTime" + 
                " and editTrxHistory2.ProcessDateTime <= :bonusStopDate" + 
                " and editTrxHistory2.ProcessDateTime <= :nextCheckDate)" + 
                " and segment2.ApplicationReceivedDate <= :applicationReceivedStopDate" + 
                " and editTrxHistory2.ProcessDateTime < :premiumStopDate)";                      

    Map params = buildHQLParams5(updateReason, transactionTypeCT);

    List results = SessionHelper.makeUnique(SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS));
    
    return (BonusCommissionHistory[]) results.toArray(new BonusCommissionHistory[results.size()]);  
  }

  /**
   * The specified BonusCommissionHistory is associated with a Segment that has a NT or FS. This query finds
   * the existing CommissionHistory for such an NT or FS transaction. 
   * @return
   */
  public CommissionHistory findCommissionHistoryForNormal_NTFS_Processing(BonusCommissionHistory bonusCommissionHistory, String transactionTypeCT)
  {
    CommissionHistory commissionHistory = null;
  
    String hql = "select commissionHistory1" +
                " from CommissionHistory commissionHistory1" +
                " join commissionHistory1.EDITTrxHistory editTrxHistory1" +
                " join editTrxHistory1.EDITTrx editTrx1" +
                " join editTrx1.ClientSetup clientSetup1" +
                " join clientSetup1.ContractSetup contractSetup1" +
                " join contractSetup1.Segment segment1" +
                
                " where segment1 = :segment1" +
                
                " and editTrx1.TransactionTypeCT = :transactionTypeCT" + // Most likely NT or FS
                " and editTrx1.Status = 'N'" + 
                
                " and commissionHistory1.PlacedAgent = :placedAgent" +
                
                " and (:bonusStartDate <= editTrx1.EffectiveDate" + 
                " and editTrx1.EffectiveDate <= :bonusStopDate" + 
                " and editTrx1.EffectiveDate <= :nextCheckDate)" + 
                " and segment1.ApplicationReceivedDate <= :applicationReceivedStopDate" + 
                " and editTrx1.EffectiveDate < :premiumStopDate" +                 
                
                " and commissionHistory1.CommissionHistoryPK not in (" + 
                " select bonusCommissionHistory.CommissionHistoryFK" + 
                " from BonusCommissionHistory bonusCommissionHistory" + 
                " join bonusCommissionHistory.ParticipatingAgent participatingAgent" + 
                " where participatingAgent = :participatingAgent)";
                
    Map params = buildHQLParams6(bonusCommissionHistory, transactionTypeCT);
    
    List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);
    
    if (!results.isEmpty())
    {
      commissionHistory = (CommissionHistory) results.get(0);
    }
    
    return commissionHistory;
  }
  
  /**
   * The specified BonusCommissionHistory is associated with a Segment that has a NT or FS. This query finds
   * the existing CommissionHistory for such an NT or FS transaction. 
   * @return
   */
  public CommissionHistory findCommissionHistoryForReversal_NTFS_Processing(BonusCommissionHistory origBonusCommissionHistory, String transactionTypeCT)
  {
    CommissionHistory commissionHistory = null;
  
    String hql = "select commissionHistory1" +
                " from CommissionHistory commissionHistory1" +
                " join commissionHistory1.EDITTrxHistory editTrxHistory1" +
                " join editTrxHistory1.EDITTrx editTrx1" +
                " join editTrx1.ClientSetup clientSetup1" +
                " join clientSetup1.ContractSetup contractSetup1" +
                " join contractSetup1.Segment segment1" +
                
                " where segment1 = :segment1" +
                
                " and editTrx1.TransactionTypeCT = :transactionTypeCT" + // Most likely NT or FS
                " and editTrx1.Status = 'R'" + 
                
                " and commissionHistory1.PlacedAgent = :placedAgent" +
                
                " and (:bonusStartDate <= editTrx1.EffectiveDate" + 
                " and editTrx1.EffectiveDate <= :bonusStopDate" + 
                " and editTrx1.EffectiveDate <= :nextCheckDate)" + 
                " and segment1.ApplicationReceivedDate <= :applicationReceivedStopDate" + 
                " and editTrx1.EffectiveDate < :premiumStopDate" +                 
                
                " and commissionHistory1.CommissionHistoryPK not in (" + 
                " select bonusCommissionHistory.CommissionHistoryFK" + 
                " from BonusCommissionHistory bonusCommissionHistory" + 
                " join bonusCommissionHistory.ParticipatingAgent participatingAgent" + 
                " where participatingAgent = :participatingAgent)";
                
    Map params = buildHQLParams6(origBonusCommissionHistory, transactionTypeCT);
    
    List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);
    
    if (!results.isEmpty())
    {
      commissionHistory = (CommissionHistory) results.get(0);
    }
    
    return commissionHistory;
  }  
}
