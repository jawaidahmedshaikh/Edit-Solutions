/*
 * User: gfrosti
 * Date: Feb 21, 2006
 * Time: 1:23:33 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import edit.common.EDITDate;
import edit.services.db.hibernate.SessionHelper;

import java.util.*;

import event.EDITTrx;
import event.EDITTrxHistory;
import fission.utility.Util;


public class HistoryFilter
{
    /**
     * A convenvience method for the "other" findHistoryFilterRows method where the specified filterPeriod will determine
     * the ultimate start/stop dates.
     *
     * @param segmentPK
     * @param filterPeriod      One of a set of values defined by the CodeTableDef of "FILTERPERIOD".
     * @param transactionTypeCT
     * @param excludeUndo
     * @return
     * @see #findHistoryFilterRows(Long, edit.common.EDITDate, edit.common.EDITDate, String, boolean)
     */
    public static HistoryFilterRow[] findHistoryFilterRows(Long segmentPK, String filterPeriod, String transactionTypeCT, boolean excludeUndo)
    {
        EDITDate startDate = null;

        EDITDate stopDate = null;

        if (filterPeriod.equalsIgnoreCase("AllPeriods"))
        {
            startDate = new EDITDate(EDITDate.DEFAULT_MIN_DATE);

            stopDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
        }

        else if (filterPeriod.equalsIgnoreCase("PriorWeek"))
        {
            startDate = new EDITDate().subtractDays(7);

            stopDate = new EDITDate();
        }

        else if (filterPeriod.equalsIgnoreCase("PriorMonth"))
        {
            EDITDate currentDate = new EDITDate().subtractMonths(1);

            startDate = new EDITDate(currentDate.getFormattedYear(), currentDate.getFormattedMonth(), "01");

            stopDate = startDate.addMonths(1).subtractDays(1);
        }

        return findHistoryFilterRows(segmentPK, startDate, stopDate, transactionTypeCT, excludeUndo);
    }

    /**
     * SegmentPK, StartDate, and StopDate are always expected. This leaves TransactionTypeCT and ExcludeUndo which yield
     * four fundamental variations of the query. Assuming SegmentPK, StartDate, StopDate are supplied, then:
     * Case 1: TransactionType == null AND ExcludeUndo = false
     * Case 2: TransactionType == null AND ExcludeUndo = true
     * Case 3: TransactionpType != null AND ExcludeUndo = false
     * Case 4: TransactionType != null AND ExcludeUndo = true
     *
     * @param segmentPK   required
     * @param startDate   required
     * @param stopDate    required
     * @param excludeUndo true if Undo EDITTrxs should be excluded
     * @transactionTypeCT null or a valid EDITTrx.TransactionTypeCT
     */
    public static HistoryFilterRow[] findHistoryFilterRows(Long segmentPK, EDITDate startDate, EDITDate stopDate, String transactionTypeCT, boolean excludeUndo)
    {
        HistoryFilterRow[] editTrxHistoryRows = null;

        HistoryFilterRow[] changeHistoryRows = null;

        Map params = buildBaseHQLParams(segmentPK, startDate, stopDate);

        // Case 1 - DON'T restrict by EDITTrx.TransactionTypeCT, DON'T restrict by EDITTrx.Status
        if (transactionTypeCT == null && !excludeUndo)
        {
            String hqlEDITTrxHistory = buildHQL_EDITTrxHistory1();
            editTrxHistoryRows = executeHQL(hqlEDITTrxHistory, params);


            String hqlChangeHistory = buildHQL_ChangeHistory();

            params.put("segmentTableName", "contract.Segment");
            params.put("contractClientTableName", "contract.ContractClient");
            params.put("contractClientAllocationTableName", "contract.ContractClientAllocation");
            params.put("questionnaireResponseTableName", "contract.QuestionnaireResponse");
            params.put("investmentTableName", "contract.Investment");
            params.put("investmentAllocationTableName", "contract.InvestmentAllocation");
            params.put("bucketTableName", "contract.Bucket");
            params.put("agentHierarchyTableName", "contract.AgentHierarchy");
            params.put("agentHierarchyAllocationTableName", "contract.AgentHierarchyAllocation");
            params.put("agentSnapshotTableName", "contract.AgentSnapshot");
            params.put("lifeTableName", "contract.Life");
            params.put("payoutTableName", "contract.Payout");
            params.put("noteReminderTableName", "contract.NoteReminder");
            params.put("contractRequirementTableName", "contract.ContractRequirement");
            params.put("inherentRiderTableName", "contract.InherentRider");
            changeHistoryRows = executeHQL(hqlChangeHistory, params);
        }

        // Case 2 - DON'T restrict by EDITTrx.TransactionTypeCT, DO restrict by EDITTrx.Status = 'U'
        else if (transactionTypeCT == null && excludeUndo)
        {
            params.put("status", EDITTrx.STATUS_UNDO);
            String hqlEDITTrxHistory = buildHQL_EDITTrxHistory2();
            editTrxHistoryRows = executeHQL(hqlEDITTrxHistory, params);

            params.remove("status");
            String hqlChangeHistory = buildHQL_ChangeHistory();
            params.put("segmentTableName", "contract.Segment");
            params.put("contractClientTableName", "contract.ContractClient");
            params.put("contractClientAllocationTableName", "contract.ContractClientAllocation");
            params.put("questionnaireResponseTableName", "contract.QuestionnaireResponse");
            params.put("investmentTableName", "contract.Investment");
            params.put("investmentAllocationTableName", "contract.InvestmentAllocation");
            params.put("bucketTableName", "contract.Bucket");
            params.put("agentHierarchyTableName", "contract.AgentHierarchy");
            params.put("agentHierarchyAllocationTableName", "contract.AgentHierarchyAllocation");
            params.put("agentSnapshotTableName", "contract.AgentSnapshot");
            params.put("lifeTableName", "contract.Life");
            params.put("payoutTableName", "contract.Payout");
            params.put("noteReminderTableName", "contract.NoteReminder");
            params.put("contractRequirementTableName", "contract.ContractRequirement");
            params.put("inherentRiderTableName", "contract.InherentRider");
            changeHistoryRows = executeHQL(hqlChangeHistory, params);
        }

        // Case 3  DO restrict by EDITTrx.TransactionTypeCT, DON'T restrict by EDITTrx.Status
        else if (transactionTypeCT != null && !excludeUndo)
        {
            if (!transactionTypeCT.equalsIgnoreCase("NF"))
            {
                params.put("transactionTypeCT", transactionTypeCT);
                String hqlEDITTrxHistory = buildHQL_EDITTrxHistory3();
                editTrxHistoryRows = executeHQL(hqlEDITTrxHistory, params);
            }
            else
            {
                String hqlChangeHistory = buildHQL_ChangeHistory();
                params.put("segmentTableName", "contract.Segment");
                params.put("contractClientTableName", "contract.ContractClient");
                params.put("contractClientAllocationTableName", "contract.ContractClientAllocation");
                params.put("questionnaireResponseTableName", "contract.QuestionnaireResponse");
                params.put("investmentTableName", "contract.Investment");
                params.put("investmentAllocationTableName", "contract.InvestmentAllocation");
                params.put("bucketTableName", "contract.Bucket");
                params.put("agentHierarchyTableName", "contract.AgentHierarchy");
                params.put("agentHierarchyAllocationTableName", "contract.AgentHierarchyAllocation");
                params.put("agentSnapshotTableName", "contract.AgentSnapshot");
                params.put("lifeTableName", "contract.Life");
                params.put("payoutTableName", "contract.Payout");
                params.put("noteReminderTableName", "contract.NoteReminder");
                params.put("contractRequirementTableName", "contract.ContractRequirement");
                params.put("inherentRiderTableName", "contract.InherentRider");
                changeHistoryRows = executeHQL(hqlChangeHistory, params);
            }
        }

        // Case 4  DO restrict by EDITTrx.TransactionTypeCT, DO restrict by EDITTrx.Status
        else if (transactionTypeCT != null && excludeUndo)
        {
            if (!transactionTypeCT.equalsIgnoreCase("NF"))
            {
                params.put("transactionTypeCT", transactionTypeCT);
                params.put("status", EDITTrx.STATUS_UNDO);
                String hqlEDITTrxHistory = buildHQL_EDITTrxHistory4();
                editTrxHistoryRows = executeHQL(hqlEDITTrxHistory, params);
            }
            else
            {
                String hqlChangeHistory = buildHQL_ChangeHistory();
                params.put("segmentTableName", "contract.Segment");
                params.put("contractClientTableName", "contract.ContractClient");
                params.put("contractClientAllocationTableName", "contract.ContractClientAllocation");
                params.put("questionnaireResponseTableName", "contract.QuestionnaireResponse");
                params.put("investmentTableName", "contract.Investment");
                params.put("investmentAllocationTableName", "contract.InvestmentAllocation");
                params.put("bucketTableName", "contract.Bucket");
                params.put("agentHierarchyTableName", "contract.AgentHierarchy");
                params.put("agentHierarchyAllocationTableName", "contract.AgentHierarchyAllocation");
                params.put("agentSnapshotTableName", "contract.AgentSnapshot");
                params.put("lifeTableName", "contract.Life");
                params.put("payoutTableName", "contract.Payout");
                params.put("noteReminderTableName", "contract.NoteReminder");
                params.put("contractRequirementTableName", "contract.ContractRequirement");
                params.put("inherentRiderTableName", "contract.InherentRider");
                changeHistoryRows = executeHQL(hqlChangeHistory, params);
            }
        }

        else
        {
            String message = "HistoryFilter Received Unexpected Query Parameters: [segmentPK = " + segmentPK + "] [startDate = " + startDate + "] [stopDate = " + stopDate + "] [transactionTypeCT = " + transactionTypeCT + "] [excludeUndo = " + excludeUndo + "]";

            throw new RuntimeException(message);
        }

        HistoryFilterRow[] historyFilterRows = (HistoryFilterRow[]) Util.joinArrays(editTrxHistoryRows, changeHistoryRows, HistoryFilterRow.class);

        Arrays.sort(historyFilterRows);

        return historyFilterRows;
    }
    

    /**
     * SegmentPK, StartDate, and StopDate are always expected. This leaves TransactionTypeCT and ExcludeUndo which yield
     * four fundamental variations of the query. Assuming SegmentPK, StartDate, StopDate are supplied, then:
     * Case 1: TransactionType == null AND ExcludeUndo = false
     * Case 2: TransactionType == null AND ExcludeUndo = true
     * Case 3: TransactionpType != null AND ExcludeUndo = false
     * Case 4: TransactionType != null AND ExcludeUndo = true
     *
     * @param segmentPK   required
     * @param startDate   required
     * @param stopDate    required
     * @param excludeUndo true if Undo EDITTrxs should be excluded
     * @transactionTypeCT null or a valid EDITTrx.TransactionTypeCT
     */
    public static HistoryFilterRow[] findBatchReversalHistoryRows(Long segmentPK, String transactionTypeCT, boolean excludeUndo, List<String> trxToIgnoreList)
    {
        HistoryFilterRow[] editTrxHistoryRows = null;
        
        EDITDate startDate = new EDITDate(EDITDate.DEFAULT_MIN_DATE);

        EDITDate stopDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);

        Map params = buildBaseHQLParams(segmentPK, startDate, stopDate);
        
        String trxToIgnore = " and editTrx.Status NOT IN ('R', 'D', 'T')";
        if (trxToIgnoreList != null && trxToIgnoreList.size() > 0) {
        	params.put("trxToIgnoreList", trxToIgnoreList);
        	trxToIgnore += " and editTrx.TransactionTypeCT NOT IN (:trxToIgnoreList)";
        }

        // Case 1 - DON'T restrict by EDITTrx.TransactionTypeCT, DON'T restrict by EDITTrx.Status
        if (transactionTypeCT == null && !excludeUndo)
        {
            String hqlEDITTrxHistory = buildHQL_EDITTrxHistory1().concat(trxToIgnore);
            editTrxHistoryRows = executeHQL(hqlEDITTrxHistory, params);
        }

        // Case 2 - DON'T restrict by EDITTrx.TransactionTypeCT, DO restrict by EDITTrx.Status = 'U'
        else if (transactionTypeCT == null && excludeUndo)
        {
            params.put("status", EDITTrx.STATUS_UNDO);
            String hqlEDITTrxHistory = buildHQL_EDITTrxHistory2().concat(trxToIgnore);
            editTrxHistoryRows = executeHQL(hqlEDITTrxHistory, params);
        }

        // Case 3  DO restrict by EDITTrx.TransactionTypeCT, DON'T restrict by EDITTrx.Status
        else if (transactionTypeCT != null && !excludeUndo)
        {
            if (!transactionTypeCT.equalsIgnoreCase("NF"))
            {
                params.put("transactionTypeCT", transactionTypeCT);
                String hqlEDITTrxHistory = buildHQL_EDITTrxHistory3().concat(trxToIgnore);
                editTrxHistoryRows = executeHQL(hqlEDITTrxHistory, params);
            }
        }

        // Case 4  DO restrict by EDITTrx.TransactionTypeCT, DO restrict by EDITTrx.Status
        else if (transactionTypeCT != null && excludeUndo)
        {
            if (!transactionTypeCT.equalsIgnoreCase("NF"))
            {
                params.put("transactionTypeCT", transactionTypeCT);
                params.put("status", EDITTrx.STATUS_UNDO);
                String hqlEDITTrxHistory = buildHQL_EDITTrxHistory4().concat(trxToIgnore);
                editTrxHistoryRows = executeHQL(hqlEDITTrxHistory, params);
            }
        }

        else
        {
            String message = "BatchReversalHistoryFilter Received Unexpected Query Parameters: [segmentPK = " + segmentPK + "] [startDate = " + startDate + "] [stopDate = " + stopDate + "] [transactionTypeCT = " + transactionTypeCT + "] [excludeUndo = " + excludeUndo + "]";

            throw new RuntimeException(message);
        }

        Arrays.sort(editTrxHistoryRows);

        return editTrxHistoryRows;
    }


    /**
     * Executes the specified hql and joins the results into an array of HistoryFilterRows sorted by effectiveDate ASC.
     *
     * @param hqlEDITTrxHistory
     * @param hqlChangeHistory
     * @return
     */
    private static HistoryFilterRow[] executeHQL(String hql, Map params)
    {
        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        List historyRows = new ArrayList();
        if (!results.isEmpty())
        {
            historyRows = convertToHistoryFilterRows(results);
        }

        evictHQLResults(results);

        return (HistoryFilterRow[]) historyRows.toArray(new HistoryFilterRow[historyRows.size()]);
    }

    /**
     * In an attempt to keep the Hibernate Session clean, evict the current results as they are no longer needed.
     *
     * @param resultsEDITTrxHistory
     */
    private static void evictHQLResults(List resultsEDITTrxHistory)
    {
        for (int i = 0; i < resultsEDITTrxHistory.size(); i++)
        {
            Object o = resultsEDITTrxHistory.get(i);

            SessionHelper.evict(o, SessionHelper.EDITSOLUTIONS);
        }
    }

    /**
     * Maps the List of EDITTrxHistory or ChangeHistory entities to its HistoryFilterRow equivalent.
     *
     * @param resultsEDITTrxHistory
     * @return
     */
    private static List convertToHistoryFilterRows(List rows)
    {
        List historyRows = new ArrayList();

        for (int i = 0; i < rows.size(); i++)
        {
            Object historyObject = rows.get(i);

            HistoryFilterRow historyFilterRow = null;

            if (historyObject instanceof EDITTrxHistory)
            {
                historyFilterRow = new HistoryFilterRow((EDITTrxHistory) historyObject);
            }
            else if (historyObject instanceof ChangeHistory)
            {
                historyFilterRow = new HistoryFilterRow((ChangeHistory) historyObject);
            }

            historyRows.add(historyFilterRow);
        }

        return historyRows;
    }


    /**
     * Builds the bind variables that will be present for all hql queries.
     *
     * @param segmentPK
     * @param transactionTypeCT
     * @param startDate
     * @param stopDate
     * @param excludeUndo
     * @return
     */
    private static Map buildBaseHQLParams(Long segmentPK, EDITDate startDate, EDITDate stopDate)
    {
        Map params = new HashMap();

        params.put("segmentPK", segmentPK);

        params.put("startDate", startDate);

        params.put("stopDate", stopDate);

        return params;
    }

    /**
     * Includes all ChangeHistory for a [to-be] specified Segment and its immediate children of ContractClient and
     * Investment that are within a [to-be] specified start and stop date.
     *
     * @return
     */
    private static String buildHQL_ChangeHistory()
    {
        String hqlEDITTrxHistory = " from ChangeHistory changeHistory" +
                     " where " + 
        //date range filter
                     " (changeHistory.EffectiveDate between :startDate and :stopDate) " +
                     " and " +
        //ChangeHistory for baseSegment
                     " ((changeHistory.ModifiedRecordFK = :segmentPK" +
                     " and changeHistory.TableName = :segmentTableName)" +
        //Now add/union the ChangeHistory for the Riders
                     " or (changeHistory.TableName = :segmentTableName" +
                     " and changeHistory.ModifiedRecordFK in (select rider.SegmentPK" +
                     " from Segment rider" +
                     " where rider.SegmentFK = :segmentPK))" +
        //Now add/union the ChangeHistory for the ContractClient table
                     " or (changeHistory.TableName = :contractClientTableName" +
                     " and changeHistory.ModifiedRecordFK in (select contractClient.ContractClientPK" +
                     " from ContractClient contractClient" +
                     " where contractClient.SegmentFK = :segmentPK))" +
        //Now add/union the ChangeHistory for the ContractClientAllocation table
                     " or (changeHistory.TableName = :contractClientAllocationTableName" +
                     " and changeHistory.ModifiedRecordFK in (select contractClientAllocation.ContractClientAllocationPK" +
                     " from ContractClientAllocation contractClientAllocation" +
                     " where contractClientAllocation.ContractClientFK in (select contractClient2.ContractClientPK" +
                     " from ContractClient contractClient2" +
                     " where contractClient2.SegmentFK = :segmentPK)))" +
        //Now add/union the ChangeHistory for the QuestionnaireResponse table
                     " or (changeHistory.TableName = :questionnaireResponseTableName" +
                     " and changeHistory.ModifiedRecordFK in (select questionnaireResponse.QuestionnaireResponsePK" +
                     " from QuestionnaireResponse questionnaireResponse" +
                     " where questionnaireResponse.ContractClientFK in (select contractClient3.ContractClientPK" +
                     " from ContractClient contractClient3" +
                     " where contractClient3.SegmentFK = :segmentPK)))" +
        //Now add/union the ChangeHistory for the Investment table
                     " or (changeHistory.TableName = :investmentTableName" +
                     " and changeHistory.ModifiedRecordFK in (select investment.InvestmentPK" +
                     " from Investment investment" +
                     " where investment.SegmentFK = :segmentPK))" +
        //Now add/union the ChangeHistory for the InvestmentAllocation table
                     " or (changeHistory.TableName = :investmentAllocationTableName" +
                     " and changeHistory.ModifiedRecordFK in (select investmentAllocation.InvestmentAllocationPK" +
                     " from InvestmentAllocation investmentAllocation" +
                     " where investmentAllocation.InvestmentFK in (select investment2.InvestmentPK" +
                     " from Investment investment2" +
                     " where investment2.SegmentFK = :segmentPK)))" +
        //Now add/union the ChangeHistory for the Bucket table
                     " or (changeHistory.TableName = :bucketTableName" +
                     " and changeHistory.ModifiedRecordFK in (select bucket.BucketPK" +
                     " from Bucket bucket" +
                     " where bucket.InvestmentFK in (select investment3.InvestmentPK" +
                     " from Investment investment3" +
                     " where investment3.SegmentFK = :segmentPK)))" +
        //Now add/union the ChangeHistory for the AgentHierarchy table
                     " or (changeHistory.TableName = :agentHierarchyTableName" +
                     " and changeHistory.ModifiedRecordFK in (select agentHierarchy.AgentHierarchyPK" +
                     " from AgentHierarchy agentHierarchy" +
                     " where agentHierarchy.SegmentFK = :segmentPK))" +
        //Now add/union the ChangeHistory for the AgentHierarchyAllocation table
                     " or (changeHistory.TableName = :agentHierarchyAllocationTableName" +
                     " and changeHistory.ModifiedRecordFK in (select agentHierarchyAllocation.AgentHierarchyAllocationPK" +
                     " from AgentHierarchyAllocation agentHierarchyAllocation" +
                     " where agentHierarchyAllocation.AgentHierarchyFK in (select agentHierarchy2.AgentHierarchyPK" +
                     " from AgentHierarchy agentHierarchy2" +
                     " where agentHierarchy2.SegmentFK = :segmentPK)))" +
        //Now add/union the ChangeHistory for the AgentSnapshot table
                     " or (changeHistory.TableName = :agentSnapshotTableName" +
                     " and changeHistory.ModifiedRecordFK in (select agentSnapshot.AgentSnapshotPK" +
                     " from AgentSnapshot agentSnapshot" +
                     " where agentSnapshot.AgentHierarchyFK in (select agentHierarchy3.AgentHierarchyPK" +
                     " from AgentHierarchy agentHierarchy3" +
                     " where agentHierarchy3.SegmentFK = :segmentPK)))" +
        //Now add/union the ChangeHistory for the Life table
                     " or (changeHistory.TableName = :lifeTableName" +
                     " and changeHistory.ModifiedRecordFK in (select life.LifePK" +
                     " from Life life" +
                     " where life.SegmentFK = :segmentPK))" +
        //Now add/union the ChangeHistory for the Payout table
                     " or (changeHistory.TableName = :payoutTableName" +
                     " and changeHistory.ModifiedRecordFK in (select payout.PayoutPK" +
                     " from Payout payout" +
                     " where payout.SegmentFK = :segmentPK))" +
        //Now add/union the ChangeHistory for the NoteReminder table
                     " or (changeHistory.TableName = :noteReminderTableName" +
                     " and changeHistory.ModifiedRecordFK in (select noteReminder.NoteReminderPK" +
                     " from NoteReminder noteReminder" +
                     " where noteReminder.SegmentFK = :segmentPK))" +
        //Now add/union the ChangeHistory for the ContractRequirement table
                     " or (changeHistory.TableName = :contractRequirementTableName" +
                     " and changeHistory.ModifiedRecordFK in (select contractRequirement.ContractRequirementPK" +
                     " from ContractRequirement contractRequirement" +
                     " where contractRequirement.SegmentFK = :segmentPK))" +
        //Now add/union the ChangeHistory for the InherentRider table
                     " or (changeHistory.TableName = :inherentRiderTableName" +
                     " and changeHistory.ModifiedRecordFK in (select inherentRider.InherentRiderPK" +
                     " from InherentRider inherentRider" +
                     " where inherentRider.SegmentFK = :segmentPK)))" ;
                     //" and changeHistory.EffectiveDate between :startDate and :stopDate";

        return hqlEDITTrxHistory;
    }

    /**
     * Includes all EDITTrxHistory for a [to-be] specified Segment within a [to-be] specified start and
     * stop date (via EDITTrx.EffectiveDate).
     *
     * @param startDate
     * @param stopDate
     * @return
     */
    private static String buildHQL_EDITTrxHistory1()
    {
        String hqlEDITTrxHistory = " select editTrxHistory from EDITTrxHistory editTrxHistory" +
                " join fetch editTrxHistory.EDITTrx editTrx" +
                " join fetch editTrx.ClientSetup clientSetup" +
                " join fetch clientSetup.ContractSetup contractSetup" +
                " join fetch contractSetup.Segment segment" +
                " where editTrx.EffectiveDate between :startDate and :stopDate" +
                " and segment.SegmentPK = :segmentPK";

        return hqlEDITTrxHistory;
    }

    /**
     * Includes all EDITTrxHistory except for UNDO's for a [to-be] specified Segment within a [to-be] specified
     * start and stop date (via EDITTrx.EffectiveDate).
     *
     * @param startDate
     * @param stopDate
     * @return
     */
    private static String buildHQL_EDITTrxHistory2()
    {
        String hqlEDITTrxHistory = " select editTrxHistory from EDITTrxHistory editTrxHistory" +
                " join fetch editTrxHistory.EDITTrx editTrx" +
                " join fetch editTrx.ClientSetup clientSetup" +
                " join fetch clientSetup.ContractSetup contractSetup" +
                " join fetch contractSetup.Segment segment" +
                " where editTrx.EffectiveDate between :startDate and :stopDate" +
                " and editTrx.Status != :status" +
                " and segment.SegmentPK = :segmentPK";

        return hqlEDITTrxHistory;
    }

    /**
     * Includes all EDITTrxHistory of a specific EDITTrx.TransactionTypeCT for a [to-be] specified Segment within a
     * [to-be] specified start and stop date (via EDITTrx.EffectiveDate).
     *
     * @param startDate
     * @param stopDate
     * @return
     */
    private static String buildHQL_EDITTrxHistory3()
    {
        String hqlEDITTrxHistory = " select editTrxHistory from EDITTrxHistory editTrxHistory" +
                " join fetch editTrxHistory.EDITTrx editTrx" +
                " join fetch editTrx.ClientSetup clientSetup" +
                " join fetch clientSetup.ContractSetup contractSetup" +
                " join fetch contractSetup.Segment segment" +
                " where editTrx.EffectiveDate between :startDate and :stopDate" +
                " and editTrx.TransactionTypeCT = :transactionTypeCT" +
                " and segment.SegmentPK = :segmentPK";

        return hqlEDITTrxHistory;
    }

    /**
     * Includes all EDITTrxHistory of a specific EDITTrx.TransactionTypeCT for a [to-be] specified Segment within a
     * [to-be] specified start and stop date (via EDITTrx.EffectiveDate).
     *
     * @param startDate
     * @param stopDate
     * @return
     */
    private static String buildHQL_EDITTrxHistory4()
    {
        String hqlEDITTrxHistory = " select editTrxHistory from EDITTrxHistory editTrxHistory" +
                " join fetch editTrxHistory.EDITTrx editTrx" +
                " join fetch editTrx.ClientSetup clientSetup" +
                " join fetch clientSetup.ContractSetup contractSetup" +
                " join fetch contractSetup.Segment segment" +
                " where editTrx.EffectiveDate between :startDate and :stopDate" +
                " and editTrx.TransactionTypeCT = :transactionTypeCT" +
                " and editTrx.Status != :status" +
                " and segment.SegmentPK = :segmentPK";

        return hqlEDITTrxHistory;
    }
}
