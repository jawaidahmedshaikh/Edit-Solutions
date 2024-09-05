/*
 * User: gfrosti
 * Date: Sep 16, 2003
 * Time: 2:33:14 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.dm.composer;

import edit.common.vo.*;
import edit.common.*;
import event.dm.dao.DAOFactory;
import event.dm.dao.FastDAO;

import java.util.List;



public class VOComposer
{
    public EDITTrxCorrespondenceVO[] composeEDITTrxCorrespondenceVOByEDITTrxPK(long editTrxPK, List voInclusionList)
    {
        EDITTrxCorrespondenceVO[] editTrxCorrespondenceVO = DAOFactory.getEDITTrxCorrespondenceDAO().findByEDITTrxPK(editTrxPK);

        if (editTrxCorrespondenceVO != null)
        {
            for (int i = 0; i < editTrxCorrespondenceVO.length; i++)
            {
                new EDITTrxCorrespondenceComposer(voInclusionList).compose(editTrxCorrespondenceVO[i]);
            }
        }

        return editTrxCorrespondenceVO;
    }

    /**
     * Composes the EDITTrxCorrespondence records whose CorresepondenceDate matches the notifyCorrDate parameter
     * value and where the CorrespondenceTypeCT value matches the correspondenceType parameter value
     * @param notifyCorrDate
     * @param correspondenceType
     * @param voInclusionList
     * @return
     */
    public EDITTrxCorrespondenceVO[] composeEDITTrxCorrespondenceVOByCorrTypeAndDate(String notifyCorrDate, String[] correspondenceTypes, List voInclusionList)
    {
        EDITTrxCorrespondenceVO[] editTrxCorrespondenceVO = DAOFactory.getEDITTrxCorrespondenceDAO().findPendingByTypeAndDate(notifyCorrDate, correspondenceTypes);

        if (editTrxCorrespondenceVO != null)
        {
            for (int i = 0; i < editTrxCorrespondenceVO.length; i++)
            {
                new EDITTrxCorrespondenceComposer(voInclusionList).compose(editTrxCorrespondenceVO[i]);
            }
        }

        return editTrxCorrespondenceVO;
    }

    public EDITTrxCorrespondenceVO[] composeEDITTrxCorrespondenceVOByTrxCorrPK(long trxCorrPK)
        throws Exception
    {
        EDITTrxCorrespondenceVO[] editTrxCorrespondenceVO = DAOFactory.getEDITTrxCorrespondenceDAO().findByTrxCorrFK(trxCorrPK);

        return editTrxCorrespondenceVO;
    }

    public GroupSetupVO composeGroupSetupVOByEDITTrxPK(long editTrxPK, List voInclusionList)
        throws Exception
    {
        GroupSetupVO[] groupSetupVO = DAOFactory.getGroupSetupDAO().findByEditTrxPK(editTrxPK);

        ContractSetupVO[] contractSetupVO = DAOFactory.getContractSetupDAO().findByEDITTrxPK(editTrxPK);

        ClientSetupVO[] clientSetupVO = DAOFactory.getClientSetupDAO().findByEDITTrxPK(editTrxPK);

        EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findByEDITTrxPK(editTrxPK);

        GroupSetupComposer composer = new GroupSetupComposer(voInclusionList);

        composer.substituteContractSetupVO(contractSetupVO);

        composer.substituteClientSetupVO(clientSetupVO);

        composer.substituteEDITTrxVO(editTrxVO);

        composer.compose(groupSetupVO[0]);

        return groupSetupVO[0];
    }

    /**
     * Composes RMD GroupSetup records for the given segment
     * @param segmentFK
     * @param trxType
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public GroupSetupVO[] composeGroupSetupVOsForRMDTransactions(long segmentFK, String trxType, List voInclusionList)
        throws Exception
    {
        GroupSetupVO[] groupSetupVO = DAOFactory.getGroupSetupDAO().findBySegmentPKAndTrxType(segmentFK, trxType);

        if (groupSetupVO != null)
        {
            for (int i = 0; i < groupSetupVO.length; i++)
            {
                GroupSetupComposer composer = new GroupSetupComposer(voInclusionList);
                composer.compose(groupSetupVO[i]);
            }
        }

        return groupSetupVO;
    }

    public EDITTrxHistoryVO[] composeEDITTrxHistoryVOByAccountPendingStatus_AND_ProcessDateLTE(String accountPendingStatus, String processDate, List voInclusionList)
        throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVO = DAOFactory.getEDITTrxHistoryDAO().findEDITTrxHistoryVOByAccountPendingStatus_AND_ProcessDateLTE(accountPendingStatus, processDate);

        if (editTrxHistoryVO != null)
        {
            for (int i = 0; i < editTrxHistoryVO.length; i++)
            {
                new EDITTrxHistoryComposer(voInclusionList).compose(editTrxHistoryVO[i]); // New composer for each VO
            }
        }

        return editTrxHistoryVO;
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByAccountPendingStatus_AND_ProcessDateLTE(String accountPendingStatus, String processDate, List voInclusionList)
        throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVO = DAOFactory.getCommissionHistoryDAO().findCommissionHistoryVOByAccountPendingStatus_AND_ProcessDateLTE(accountPendingStatus, processDate);

        if (commissionHistoryVO != null)
        {
            for (int i = 0; i < commissionHistoryVO.length; i++)
            {
                new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO[i]); // New composer for each VO
            }
        }

        return commissionHistoryVO;
    }

    public EDITTrxVO composeEDITTrxVOByEDITTrxPK(long editTrxPK, List voInclusionList)
        throws Exception
    {
        EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findByEDITTrxPK(editTrxPK);

        if ((editTrxVO != null) && (editTrxVO.length > 0))
        {
            EDITTrxComposer composer = new EDITTrxComposer(voInclusionList);
            composer.compose(editTrxVO[0]);

            return editTrxVO[0];
        }
        else
        {
            return null;
        }
    }

    public EDITTrxVO[] composeEDITTrxVOBySegmentPK(long segmentPK, List voInclusionList)
        throws Exception
    {
        EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findAllBySegmentPK(segmentPK);

        if (editTrxVO != null)
        {
            for (int i = 0; i < editTrxVO.length; i++)
            {
                new EDITTrxComposer(voInclusionList).compose(editTrxVO[i]); // New composer for each VO
            }
        }

        return editTrxVO;
    }

    /**
     * Composes the EDITTrxVO records whose ContractSetup.SegmentFK matches the segmentPK parameter value and
     * whose TransactionTypeCT is in the transactionTypes array parameter value
     * @param segmentPK
     * @param transactionTypes
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxVO[] composeEDITTrxVOBySegmentPKAndTrxType(long segmentPK, String[] transactionTypes, List voInclusionList)
        throws Exception
    {
        EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findAllBySegmentPKAndTrxType(segmentPK, transactionTypes);

        if (editTrxVO != null)
        {
            for (int i = 0; i < editTrxVO.length; i++)
            {
                new EDITTrxComposer(voInclusionList).compose(editTrxVO[i]); // New composer for each VO
            }
        }

        return editTrxVO;
    }

    /**
     * Composes the Pending EDITTrxVO records whose ContractSetup.SegmentFK matches the segmentPK parameter value and
     * whose TransactionTypeCT is in the transactionTypes array parameter value
     * @param segmentPK
     * @param transactionTypes
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxVO[] composePendingEDITTrxVOBySegmentPKAndTrxType(long segmentPK, String[] transactionTypes, List voInclusionList) throws Exception
    {
        EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findAllPendingBySegmentPKAndTrxType(segmentPK, transactionTypes);
        if (editTrxVO != null)
        {
            for (int i = 0; i < editTrxVO.length; i++)
            {
                new EDITTrxComposer(voInclusionList).compose(editTrxVO[i]); // New composer for each VO
            }
        }

        return editTrxVO;
    }

    public EDITTrxVO[] composeEDITTrxVOForStatement(long segmentPK, String startingEffDate, String endingEffDate, int drivingTrxPriority, List voInclusionList)
        throws Exception
    {
        EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findAllForStatement(segmentPK, startingEffDate, endingEffDate, drivingTrxPriority);

        if (editTrxVO != null)
        {
            for (int i = 0; i < editTrxVO.length; i++)
            {
                new EDITTrxComposer(voInclusionList).compose(editTrxVO[i]); // New composer for each VO
            }
        }

        return editTrxVO;
    }

    public EDITTrxVO[] composeEDITTrxVOBySegmentPKs_AND_PendingStatus(List segmentPKList, String[] pendingStatus, List voInclusionList)
        throws Exception
    {
        EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findBySegmentPK_AND_PendingStatus(segmentPKList, pendingStatus);

        if (editTrxVO != null)
        {
            EDITTrxComposer composer = new EDITTrxComposer(voInclusionList);

            for (int i = 0; i < editTrxVO.length; i++)
            {
                composer.compose(editTrxVO[i]);
            }
        }

        return editTrxVO;
    }

    public EDITTrxVO[] composeEDITTrxVOBySegmentPKs_AND_PendingStatus_AND_NotTransactionType(List segmentPKList, String[] pendingStatus, String[] transactionTypes, List voInclusionList)
            throws Exception
        {
            EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findBySegmentPK_AND_PendingStatus_AND_NotTransactionType(segmentPKList, pendingStatus, transactionTypes);

            if (editTrxVO != null)
            {
                EDITTrxComposer composer = new EDITTrxComposer(voInclusionList);

                for (int i = 0; i < editTrxVO.length; i++)
                {
                    composer.compose(editTrxVO[i]);
                }
            }

            return editTrxVO;
        }

    public EDITTrxVO[] composeEDITTrxVOByRange(long startingEDITTrxPK, String[] pendingStatus, int fetchSize, int scrollDirection, List voInclusionList)
        throws Exception
    {
        boolean firstPass = false;

        if (startingEDITTrxPK == 0)
        {
            EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findFirstSortedBy_EffectiveDate_AND_PendingStatus(pendingStatus);

            if (editTrxVO != null)
            {
                startingEDITTrxPK = editTrxVO[0].getEDITTrxPK();
                firstPass = true;
            }
            else
            {
                return null;
            }
        }

        EDITTrxVO[] editTrxVO = new FastDAO().findByRange_AND_PendingStatus(startingEDITTrxPK, pendingStatus, fetchSize, scrollDirection, firstPass);

        if (editTrxVO != null)
        {
            EDITTrxComposer composer = new EDITTrxComposer(voInclusionList);

            for (int i = 0; i < editTrxVO.length; i++)
            {
                composer.compose(editTrxVO[i]);
            }
        }

        return editTrxVO;
    }

    /**
     * Composes all EDITTrxVOs with a given pendingStatus
     * @param pendingStatus - value of pendingStatus field in EDITTrxVO
     * @param voInclusionList - list of associated VOs to be built and included with the EDITTrxVO
     * @return Array of all found EDITTrxVOs and their associated VOs
     * @throws Exception
     */
    public EDITTrxVO[] composeEDITTrxVOByPendingStatus(String pendingStatus, List voInclusionList)
        throws Exception
    {
        EDITTrxVO[] editTrxVOs = DAOFactory.getEDITTrxDAO().findByPendingStatus(pendingStatus);

        if (editTrxVOs != null)
        {
            for (int i = 0; i < editTrxVOs.length; i++)
            {
                new EDITTrxComposer(voInclusionList).compose(editTrxVOs[i]); // New composer for each VO
            }
        }

        return editTrxVOs;
    }

    /**
     * Composes all EDITTrxVOs with a given pendingStatus and transactionType
     * @param pendingStatus - value of pendingStatus field in EDITTrxVO
     * @param transactionType - value of transactionType field in EDITTrxVO
     * @param voInclusionList - list of associated VOs to be built and included with the EDITTrxVO
     * @return Array of all found EDITTrxVOs and their associated VOs
     * @throws Exception
     */
    public EDITTrxVO[] composeEDITTrxVOByPendingStatusAndTransactionType(long segmentPK, String pendingStatus, String transactionType, List voInclusionList)
        throws Exception
    {
        EDITTrxVO[] editTrxVOs = DAOFactory.getEDITTrxDAO().findByPendingStatusAndTransactionType(segmentPK, pendingStatus, transactionType);

        if (editTrxVOs != null)
        {
            for (int i = 0; i < editTrxVOs.length; i++)
            {
                new EDITTrxComposer(voInclusionList).compose(editTrxVOs[i]); // New composer for each VO
            }
        }

        return editTrxVOs;
    }
    
    /**
     * Composes all EDITTrxVOs with a given pendingStatus and sorts by Segment and Trx Effective Date
     * @param pendingStatus - value of pendingStatus field in EDITTrxVO
     * @param voInclusionList - list of associated VOs to be built and included with the EDITTrxVO
     * @return Array of all found EDITTrxVOs and their associated VOs
     * @throws Exception
     */
    public EDITTrxVO[] composeEDITTrxVOByPendingStatusSortBySegmentAndEffDate(String pendingStatus, List voInclusionList)
        throws Exception
    {
        EDITTrxVO[] editTrxVOs = DAOFactory.getEDITTrxDAO().findByPendingStatusSortBySegmentAndEffDate(pendingStatus);

        if (editTrxVOs != null)
        {
            for (int i = 0; i < editTrxVOs.length; i++)
            {
                new EDITTrxComposer(voInclusionList).compose(editTrxVOs[i]); // New composer for each VO
            }
        }

        return editTrxVOs;
    }

    /**
     * Composes all EDITrx records whose ContractSetup.SegmentFK matches the segmentFK parameter value, whose
     * PendingStatusCT is in the pendingStatus array parameter value, and whose EffectiveDate >= the drivingEffDate
     * parameter value
     * @param segmentFK
     * @param pendingStatus
     * @param drivingEffDate
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxVO[] composeEDITTrxVOBySegmentPendingStatusAndEffDate(long segmentFK, String[] pendingStatus, String drivingEffDate, List voInclusionList)
        throws Exception
    {
        EDITTrxVO[] editTrxVOs = DAOFactory.getEDITTrxDAO().findBySegmentPKPendingStatusAndEffDateGTE(segmentFK, pendingStatus, drivingEffDate);

        if (editTrxVOs != null)
        {
            for (int i = 0; i < editTrxVOs.length; i++)
            {
                new EDITTrxComposer(voInclusionList).compose(editTrxVOs[i]); // New composer for each VO
            }
        }

        return editTrxVOs;
    }

    /**
     * Finds all EDITTrxHistoryVOs with given segmentPK
     * @param segmentPK         segment the history is associated with
     * @param voInclusionList   associated VOs to be included
     * @return All EDITTrxHistoryVOs for the segment
     * @throws Exception
     */
    public EDITTrxHistoryVO[] composeEDITTrxHistoryVOBySegmentPK(long segmentPK, List voInclusionList)
        throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVOs = DAOFactory.getEDITTrxHistoryDAO().findEDITTrxHistoryBySegmentPK(segmentPK);

        if (editTrxHistoryVOs != null)
        {
            for (int i = 0; i < editTrxHistoryVOs.length; i++)
            {
                new EDITTrxHistoryComposer(voInclusionList).compose(editTrxHistoryVOs[i]); // New composer for each VO
            }
        }

        return editTrxHistoryVOs;
    }

    public EDITTrxVO composeIssueEDITTrxVO(long segmentPK, List voInclusionList)
        throws Exception
    {
        EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findIssueTrx(segmentPK);

        if ((editTrxVO != null) && (editTrxVO.length > 0))
        {
            EDITTrxComposer composer = new EDITTrxComposer(voInclusionList);

            composer.compose(editTrxVO[0]);

            return editTrxVO[0];
        }
        else
        {
            return null;
        }
    }

    public SuspenseVO[] composeAccountingPendingSuspenseVO(String processDate, List voInclusionList)
        throws Exception
    {
        SuspenseVO[] suspenseVO = DAOFactory.getSuspenseDAO().findAccountingPendingSuspenseEntries(processDate);

        if (suspenseVO != null)
        {
            SuspenseComposer composer = new SuspenseComposer(voInclusionList);

            for (int i = 0; i < suspenseVO.length; i++)
            {
                composer.compose(suspenseVO[i]);
            }
        }

        return suspenseVO;
    }

    public SuspenseVO[] composeSuspenseVO(List voInclusionList)
        throws Exception
    {
        SuspenseVO[] suspenseVO = DAOFactory.getSuspenseDAO().findAll();

        if (suspenseVO != null)
        {
            SuspenseComposer composer = new SuspenseComposer(voInclusionList);

            for (int i = 0; i < suspenseVO.length; i++)
            {
                composer.compose(suspenseVO[i]);
            }
        }

        return suspenseVO;
    }

    public SuspenseVO[] composeSuspenseVOsToDelete(List voInclusionList)
        throws Exception
    {
        SuspenseVO[] suspenseVO = DAOFactory.getSuspenseDAO().findSuspenseToDelete();

        if (suspenseVO != null)
        {
            SuspenseComposer composer = new SuspenseComposer(voInclusionList);

            for (int i = 0; i < suspenseVO.length; i++)
            {
                composer.compose(suspenseVO[i]);
            }
        }

        return suspenseVO;
    }

    public SuspenseVO[] composeCashBatchSuspenseVO(List voInclusionList)
        throws Exception
    {
        SuspenseVO[] suspenseVO = DAOFactory.getSuspenseDAO().findAllBatchSuspense();

        if (suspenseVO != null)
        {
            SuspenseComposer composer = new SuspenseComposer(voInclusionList);

            for (int i = 0; i < suspenseVO.length; i++)
            {
                composer.compose(suspenseVO[i]);
            }
        }

        return suspenseVO;
    }

    public SuspenseVO composeSuspenseVO(long suspensePK, List voInclusionList)
        throws Exception
    {
        SuspenseVO[] suspenseVO = DAOFactory.getSuspenseDAO().findBySuspensePK(suspensePK);

        SuspenseComposer composer = new SuspenseComposer(voInclusionList);

        SuspenseVO suspenseVOToReturn = null;

        if (suspenseVO != null)
        {
            composer.compose(suspenseVO[0]);
            suspenseVOToReturn = suspenseVO[0];
        }

        return suspenseVOToReturn;
    }

    public SuspenseVO[] composeSuspenseVO(String contractNumber, List voInclusionList)
        throws Exception
    {
        SuspenseVO[] suspenseVO = DAOFactory.getSuspenseDAO().findByUserDefNumber(contractNumber);

        if (suspenseVO != null)
        {
            SuspenseComposer composer = new SuspenseComposer(voInclusionList);

            for (int i = 0; i < suspenseVO.length; i++)
            {
                composer.compose(suspenseVO[i]);
            }
        }

        return suspenseVO;
    }

    public SuspenseVO[] composeSuspenseVOByUserDefNumber(String userDefNumber, List voInclusionList)
        throws Exception
    {
        SuspenseVO[] suspenseVO = DAOFactory.getSuspenseDAO().findByUserDefNumber(userDefNumber);

        if (suspenseVO != null)
        {
            SuspenseComposer composer = new SuspenseComposer(voInclusionList);

            for (int i = 0; i < suspenseVO.length; i++)
            {
                composer.compose(suspenseVO[i]);
            }
        }

        return suspenseVO;
    }

    public SuspenseVO[] composeSuspenseVOByDirection(String direction, List voInclusionList)
        throws Exception
    {
        SuspenseVO[] suspenseVO = DAOFactory.getSuspenseDAO().findAllByDirection(direction);

        if (suspenseVO != null)
        {
            SuspenseComposer composer = new SuspenseComposer(voInclusionList);

            for (int i = 0; i < suspenseVO.length; i++)
            {
                composer.compose(suspenseVO[i]);
            }
        }

        return suspenseVO;
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByUpdateStatus(String updateStatus, List voInclusionList)
        throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVO = DAOFactory.getCommissionHistoryDAO().findByUpdateStatus("U");

        if (commissionHistoryVO != null)
        {
            for (int i = 0; i < commissionHistoryVO.length; i++)
            {
                new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO[i]);
            }
        }

        return commissionHistoryVO;
    }

    public CommissionHistoryVO[] composeCommHistVOByPlacedAgentPKAndUpdateStatus(long placedAgentPK, String[] updateStatus, List voInclusionList)
        throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVO = DAOFactory.getCommissionHistoryDAO().findByPlacedAgentPKAndUpdateStatus(placedAgentPK, updateStatus);

        if (commissionHistoryVO != null)
        {
            CommissionHistoryComposer chc = new CommissionHistoryComposer(voInclusionList);

            // NOTE - WE WILL REUSE THIS COMPOSER INSTANCE FOR CACHEING.
            for (int i = 0; i < commissionHistoryVO.length; i++)
            {
                chc.compose(commissionHistoryVO[i]);
            }
        }

        return commissionHistoryVO;
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByPlacedAgentPKExcludingUpdateStatus(long[] placedAgentPKs, String[] updateStatuses, List voInclusionList)
        throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVO = DAOFactory.getCommissionHistoryDAO().findByPlacedAgentPKExcludingUpdateStatus(placedAgentPKs, updateStatuses);

        if (commissionHistoryVO != null)
        {
            for (int i = 0; i < commissionHistoryVO.length; i++)
            {
                new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO[i]);
            }
        }

        return commissionHistoryVO;
    }

    //    public CommissionHistoryVO[] composeCommHistoryByPlacedAgentTransactionFromToDates(long[] placedAgentPKs,
    //                                                                                        String transactionType,
    //                                                                                         String fromDate,
    //                                                                                          String toDate,
    //                                                                                           List voInclusionList) throws Exception
    //    {
    //        CommissionHistoryVO[] commissionHistoryVO =
    //                DAOFactory.getCommissionHistoryDAO().findByPlacedAgentTransctionFromToDates(placedAgentPKs,
    //                                                                                             transactionType,
    //                                                                                              fromDate,
    //                                                                                               toDate);
    //        if (commissionHistoryVO != null)
    //        {
    //            for (int i = 0; i < commissionHistoryVO.length; i++)
    //            {
    //                new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO[i]);
    //            }
    //        }
    //
    //        return commissionHistoryVO;
    //    }
    public CommissionHistoryVO[] composeCommHistoryByPlacedAgentTransTypeDatesAndPolicy(long[] placedAgentPKs, String transactionType, EDITDate fromDate, EDITDate toDate, String contractNumber, List voInclusionList)
        throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVO = DAOFactory.getCommissionHistoryDAO().findByPlacedAgentTransTypeDatesAndPolicy(placedAgentPKs, transactionType, fromDate, toDate, contractNumber);

        if (commissionHistoryVO != null)
        {
            for (int i = 0; i < commissionHistoryVO.length; i++)
            {
                new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO[i]);
            }
        }

        return commissionHistoryVO;
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByPlacedAgentPKAndCommissionType(long[] placedAgentPKs, String commissionType, List voInclusionList)
        throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVO = DAOFactory.getCommissionHistoryDAO().findByPlacedAgentPKAndCommissionType(placedAgentPKs, commissionType);

        if (commissionHistoryVO != null)
        {
            for (int i = 0; i < commissionHistoryVO.length; i++)
            {
                new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO[i]);
            }
        }

        return commissionHistoryVO;
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByPlacedAgentPK(long placedAgentPK, List voInclusionList)
        throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVO = DAOFactory.getCommissionHistoryDAO().findByPlacedAgentPK(placedAgentPK);

        if (commissionHistoryVO != null)
        {
            for (int i = 0; i < commissionHistoryVO.length; i++)
            {
                new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO[i]);
            }
        }

        return commissionHistoryVO;
    }

    /**
     * Compose CommissionHistoryVOs for the given PlacedAgent and Segment foreign keys
     * @param agentSnapshotPK
     * @param segmentFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public CommissionHistoryVO[] composeCommissionHistoryVOByAgentSnapshotPK_SegmentFK(long agentSnapshotPK, long segmentFK, String[] trxTypeCTs, List voInclusionList)
        throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVO = DAOFactory.getCommissionHistoryDAO().findByAgentSnapshotPK_SegmentFK_TrxTypeCTs(agentSnapshotPK, segmentFK, trxTypeCTs);

        if (commissionHistoryVO != null)
        {
            for (int i = 0; i < commissionHistoryVO.length; i++)
            {
                new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO[i]);
            }
        }

        return commissionHistoryVO;
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByProcessDateGT_AND_PlacedAgentPK(String processDate, long placedAgentPK, List voInclusionList)
        throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVO = DAOFactory.getCommissionHistoryDAO().findByProcessDateGT_AND_PlacedAgentPK(processDate, placedAgentPK);

        if (commissionHistoryVO != null)
        {
            for (int i = 0; i < commissionHistoryVO.length; i++)
            {
                new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO[i]);
            }
        }

        return commissionHistoryVO;
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByUpdateDateTime_AND_PlacedAgentPK_AND_StatementInd(long placedAgentPK, String statementInd, List voInclusionList)
        throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVO = DAOFactory.getCommissionHistoryDAO().findByUpdateDateTime_AND_PlacedAgentPK_AND_StatementInd(placedAgentPK, statementInd);

        if (commissionHistoryVO != null)
        {
            for (int i = 0; i < commissionHistoryVO.length; i++)
            {
                new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO[i]);
            }
        }

        return commissionHistoryVO;
    }

    public CommissionHistoryVO[] composeCommHistByUpdateDateTimeGTPlacedAgentPKBonusAmtAndStatus(String updateDateTime, long[] placedAgentPKs, String[] statuses, List voInclusionList)
        throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVO = DAOFactory.getCommissionHistoryDAO().findByUpdateDateTimeGTPlacedAgentPKBonusAmtAndStatus(updateDateTime, placedAgentPKs, statuses);

        if (commissionHistoryVO != null)
        {
            for (int i = 0; i < commissionHistoryVO.length; i++)
            {
                new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO[i]);
            }
        }

        return commissionHistoryVO;
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByUpdateDateTimeGTPlacedAgentPKAndStatus(String updateDateTime, long[] placedAgentPKs, String[] statuses, List voInclusionList)
        throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVO = DAOFactory.getCommissionHistoryDAO().findByUpdateDateTimeGTPlacedAgentPKAndStatus(updateDateTime, placedAgentPKs, statuses);

        if (commissionHistoryVO != null)
        {
            for (int i = 0; i < commissionHistoryVO.length; i++)
            {
                new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO[i]);
            }
        }

        return commissionHistoryVO;
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByUpdateDateTimeLTPlacedAgentPK(long[] placedAgentPKs, String updateDateTime, List voInclusionList)
        throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVO = DAOFactory.getCommissionHistoryDAO().findByUpdateDateTimeLTPlacedAgentPK(placedAgentPKs, updateDateTime);

        if (commissionHistoryVO != null)
        {
            for (int i = 0; i < commissionHistoryVO.length; i++)
            {
                new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO[i]);
            }
        }

        return commissionHistoryVO;
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByUpdateDateTimeGTLTPlacedAgentPK(long[] placedAgentPKs, String currentUpdateDateTime, String priorUpdateDateTime, List voInclusionList)
        throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVO = DAOFactory.getCommissionHistoryDAO().findByUpdateDateTimeGTLTPlacedAgentPK(placedAgentPKs, currentUpdateDateTime, priorUpdateDateTime);

        if (commissionHistoryVO != null)
        {
            for (int i = 0; i < commissionHistoryVO.length; i++)
            {
                new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO[i]);
            }
        }

        return commissionHistoryVO;
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOByUpdateDateTimeGTELTEPlacedAgentPK(long[] placedAgentPKs, String currentUpdateDateTime, String priorUpdateDateTime, List voInclusionList)
        throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVO = DAOFactory.getCommissionHistoryDAO().findByUpdateDateTimeGTELTEPlacedAgentPK(placedAgentPKs, currentUpdateDateTime, priorUpdateDateTime);

        if (commissionHistoryVO != null)
        {
            for (int i = 0; i < commissionHistoryVO.length; i++)
            {
                new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO[i]);
            }
        }

        return commissionHistoryVO;
    }

    public CashBatchContractVO[] composeCashBatchContractByCBContractPK(long cashBatchContractPK)
        throws Exception
    {
        CashBatchContractVO[] cashBatchContractVO = DAOFactory.getCashBatchContractDAO().findCashBatchContractByPK(cashBatchContractPK);

        return cashBatchContractVO;
    }

    public EDITTrxHistoryVO[] composeEDITTrxHistoryByDates(long[] segmentFKs, String fromDate, String toDate, List voInclusionList)
        throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVOs = DAOFactory.getEDITTrxHistoryDAO().findEDITTrxHistoryBySegmentFKAndCycleDate(segmentFKs, fromDate, toDate);

        if (editTrxHistoryVOs != null)
        {
            for (int i = 0; i < editTrxHistoryVOs.length; i++)
            {
                new EDITTrxHistoryComposer(voInclusionList).compose(editTrxHistoryVOs[i]); // New composer for each VO
            }
        }

        return editTrxHistoryVOs;
    }

    public EDITTrxHistoryVO[] composeEDITTrxHistoryByDatesForCompanyStructure(long companyStructureFK, String fromDate, String toDate, List voInclusionList)
        throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVOs = DAOFactory.getEDITTrxHistoryDAO().findEDITTrxHistoryByCompanyStructureAndCycleDate(companyStructureFK, fromDate, toDate);

        if (editTrxHistoryVOs != null)
        {
            for (int i = 0; i < editTrxHistoryVOs.length; i++)
            {
                new EDITTrxHistoryComposer(voInclusionList).compose(editTrxHistoryVOs[i]); // New composer for each VO
            }
        }

        return editTrxHistoryVOs;
    }

    /**
     * Composes all edit trx history records for the given segment and from/to dates
     * @param segmentPK
     * @param fromDate
     * @param toDate
     * @param transactionType
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxHistoryVO[] composeEDITTrxHistoryBySegmentAndDates(long segmentPK, String fromDate, String toDate, String transactionType, List voInclusionList)
        throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVOs = DAOFactory.getEDITTrxHistoryDAO().findEDITTrxHistoryByEffectiveDate(segmentPK, fromDate, toDate, transactionType);

        if (editTrxHistoryVOs != null)
        {
            for (int i = 0; i < editTrxHistoryVOs.length; i++)
            {
                new EDITTrxHistoryComposer(voInclusionList).compose(editTrxHistoryVOs[i]); // New composer for each VO
            }
        }

        return editTrxHistoryVOs;
    }

    /**
     * Composes all edit trx history records for the given segment and from/to dates
     * @param segmentPK
     * @param fromDate
     * @param toDate
     * @param transactionType
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxHistoryVO[] composeEDITTrxHistoryBySegmentAndDateLTE(long segmentPK, String effDate, String transactionType, List voInclusionList)
        throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVOs = DAOFactory.getEDITTrxHistoryDAO().findEDITTrxHistoryByEffectiveDateLTE(segmentPK, effDate, transactionType);

        if (editTrxHistoryVOs != null)
        {
            for (int i = 0; i < editTrxHistoryVOs.length; i++)
            {
                new EDITTrxHistoryComposer(voInclusionList).compose(editTrxHistoryVOs[i]); // New composer for each VO
            }
        }

        return editTrxHistoryVOs;
    }

    public EDITTrxHistoryVO[] composeEDITTrxHistoryByDatesTrxType(long segmentPK, String fromDate, String toDate, String[] trxType, List voInclusionList)
        throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVOs = DAOFactory.getEDITTrxHistoryDAO().findEDITTrxHistoryByProcessDateTrxType(segmentPK, fromDate, toDate, trxType);

        if (editTrxHistoryVOs != null)
        {
            for (int i = 0; i < editTrxHistoryVOs.length; i++)
            {
                new EDITTrxHistoryComposer(voInclusionList).compose(editTrxHistoryVOs[i]); // New composer for each VO
            }
        }

        return editTrxHistoryVOs;
    }

    public EDITTrxHistoryVO[] composeEDITTrxHistoryByDateLTE_And_TrxType(long segmentPK, String toDate, String[] trxType, List voInclusionList)
        throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVOs = DAOFactory.getEDITTrxHistoryDAO().findEDITTrxHistoryByDateLTE_And_TrxType(segmentPK, toDate, trxType);

        if (editTrxHistoryVOs != null)
        {
            for (int i = 0; i < editTrxHistoryVOs.length; i++)
            {
                new EDITTrxHistoryComposer(voInclusionList).compose(editTrxHistoryVOs[i]); // New composer for each VO
            }
        }

        return editTrxHistoryVOs;
    }

    public EDITTrxVO[] composeEDITTrxVOBySegmentPK_AND_PendingStatus_AND_Date(long segmentPK, String quoteDate, List voInclusionList)
        throws Exception
    {
        //Pending status is hard coded in method ("H")
        EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findBySegmentPK_AND_EffectiveDateGT_For_InforceQuote(segmentPK, quoteDate);

        if (editTrxVO != null)
        {
            EDITTrxComposer composer = new EDITTrxComposer(voInclusionList);

            for (int i = 0; i < editTrxVO.length; i++)
            {
                composer.compose(editTrxVO[i]);
            }
        }

        return editTrxVO;
    }

    public CommissionHistoryVO[] composeCommissionHistoryVOForTaxableIncomeCheckTotals(String lastStatementDateTime, long placedAgentPK, List voInclusionList)
        throws Exception
    {
        CommissionHistoryVO[] commissionHistoryVO = DAOFactory.getCommissionHistoryDAO().findByUpdateDateTimeGTPlacedAgentPKAndTrxType(lastStatementDateTime, placedAgentPK);

        if (commissionHistoryVO != null)
        {
            for (int i = 0; i < commissionHistoryVO.length; i++)
            {
                new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO[i]);
            }
        }

        return commissionHistoryVO;
    }

    public BucketHistoryVO[] composeBucketHistoryByBucketFK(long bucketFK, List voInclusionList)
        throws Exception
    {
        BucketHistoryVO[] bucketHistoryVO = DAOFactory.getBucketHistoryDAO().findByBucketFK(bucketFK);

        if (bucketHistoryVO != null)
        {
            for (int i = 0; i < bucketHistoryVO.length; i++)
            {
                new BucketHistoryComposer(voInclusionList).compose(bucketHistoryVO[i]);
            }
        }

        return bucketHistoryVO;
    }

    /**
     * Composes all Suspense records whose FilteredFundFK matches the filteredFundFK parameter value
     * @param filteredFundFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public SuspenseVO[] composeSuspenseVOByFilteredFundFK(long filteredFundFK, List voInclusionList)
        throws Exception
    {
        SuspenseVO[] suspenseVO = DAOFactory.getSuspenseDAO().findAllByFilteredFundFK(filteredFundFK);

        if (suspenseVO != null)
        {
            for (int i = 0; i < suspenseVO.length; i++)
            {
                new SuspenseComposer(voInclusionList).compose(suspenseVO[i]);
            }
        }

        return suspenseVO;
    }

    /**
     * Composes all EDITTrx records whose InvestmentAllocationOverride record points to an Investment whose
     * FilteredFundFK matches the filteredFundFK parameter value
     * @param filteredFundFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxVO[] composeEditTrxVOByTrxTypeAndFilteredFundFK(long filteredFundFK, List voInclusionList)
        throws Exception
    {
        EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findAllByTrxTypeAndFilteredFundFK(filteredFundFK);

        if (editTrxVO != null)
        {
            for (int i = 0; i < editTrxVO.length; i++)
            {
                new EDITTrxComposer(voInclusionList).compose(editTrxVO[i]);
            }
        }

        return editTrxVO;
    }

    public BucketHistoryVO[] composeBucketHistoryByInvestmentAndEditTrxHistory(long investmentFK, long editTrxHistoryFK, List voInclusionList)
        throws Exception
    {
        BucketHistoryVO[] bucketHistoryVOs = DAOFactory.getBucketHistoryDAO().findByInvestmentAndEditTrxHistory(investmentFK, editTrxHistoryFK);

        if (bucketHistoryVOs != null)
        {
            for (int i = 0; i < bucketHistoryVOs.length; i++)
            {
                new BucketHistoryComposer(voInclusionList).compose(bucketHistoryVOs[i]);
            }
        }

        return bucketHistoryVOs;
    }

    public EDITTrxVO composeOriginatingEDITTrxVOByOriginatingEditTrxFK(long originatingTrxFK, List voInclusionList)
        throws Exception
    {
        EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findByOriginatingEditTrxFK(originatingTrxFK);

        if ((editTrxVO != null) && (editTrxVO.length > 0))
        {
            new EDITTrxComposer(voInclusionList).compose(editTrxVO[0]);
        }

        return editTrxVO[0];
    }

    /**
     * Composes all EDITTrxHistoryVOs for the given dates using the given dateType (Effective/Process date) where
     * the transaction affected the given fund.
     * @param startDate
     * @param endDate
     * @param dateType
     * @param filteredFundFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxHistoryVO[] composeEDITTrxHistoryByDate_And_Fund(String startDate, String endDate, String dateType, long filteredFundFK, List voInclusionList)
        throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVO = DAOFactory.getEDITTrxHistoryDAO().findEDITTrxByDate_And_Fund(startDate, endDate, dateType, filteredFundFK);

        if (editTrxHistoryVO != null)
        {
            for (int i = 0; i < editTrxHistoryVO.length; i++)
            {
                new EDITTrxHistoryComposer(voInclusionList).compose(editTrxHistoryVO[i]); // New composer for each VO
            }
        }

        return editTrxHistoryVO;
    }

    /**
     * Composes all EDITTrxHistoryVOs for the given dates using the given dateType (Effective/Process date) where
     * the transaction affected the given fund.
     * @param startDate
     * @param endDate
     * @param dateType
     * @param filteredFundFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxHistoryVO[] composeEDITTrxHistoryByDate_And_Fund(String startDate, String endDate, String dateType, long filteredFundFK, long chargeCodeFK, List voInclusionList)
        throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVO = DAOFactory.getEDITTrxHistoryDAO().findEDITTrxByDate_And_Fund(startDate, endDate, dateType, filteredFundFK, chargeCodeFK);

        if (editTrxHistoryVO != null)
        {
            for (int i = 0; i < editTrxHistoryVO.length; i++)
            {
                new EDITTrxHistoryComposer(voInclusionList).compose(editTrxHistoryVO[i]); // New composer for each VO
            }
        }

        return editTrxHistoryVO;
    }

    /**
     * Composes all EDITTrxHistoryVOs for the given dates using the given dateType (Effective/Process date) where
     * the transaction affected the given fund.
     * @param startDate
     * @param endDate
     * @param dateType
     * @param filteredFundFK
     * @param chargeCodeFK,
     * @param segmentFKs
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public EDITTrxHistoryVO[] composeEDITTrxHistoryByDateSegmentFund(String startDate, String endDate, String dateType, long filteredFundFK, long chargeCodeFK, long[] segmentFKs, List voInclusionList)
        throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVO = DAOFactory.getEDITTrxHistoryDAO().findEDITTrxByDateSegmentFund(startDate, endDate, dateType, filteredFundFK, chargeCodeFK, segmentFKs);

        if (editTrxHistoryVO != null)
        {
            for (int i = 0; i < editTrxHistoryVO.length; i++)
            {
                new EDITTrxHistoryComposer(voInclusionList).compose(editTrxHistoryVO[i]); // New composer for each VO
            }
        }

        return editTrxHistoryVO;
    }

    /**
     * Composer
     * @param contractClientFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public ClientSetupVO[] composeClientSetupByContractClientFK(long contractClientFK, List voInclusionList)
        throws Exception
    {
        ClientSetupVO[] clientSetupVOs = DAOFactory.getClientSetupDAO().findByContractClientFK(contractClientFK);

        if (clientSetupVOs != null)
        {
            for (int i = 0; i < clientSetupVOs.length; i++)
            {
                new ClientSetupComposer(voInclusionList).compose(clientSetupVOs[i]); // New composer for each VO
            }
        }

        return clientSetupVOs;
    }

    /**
     * Composer
     * @param segmentFK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public ContractSetupVO[] composeContractSetupBySegmentFK(long segmentFK, List voInclusionList)
        throws Exception
    {
        ContractSetupVO[] contractSetupVOs = DAOFactory.getContractSetupDAO().findBySegmentPK(segmentFK);

        if (contractSetupVOs != null)
        {
            for (int i = 0; i < contractSetupVOs.length; i++)
            {
                new ContractSetupComposer(voInclusionList).compose(contractSetupVOs[i]); // New composer for each VO
            }
        }

        return contractSetupVOs;
    }

    /**
     * Composes CommissionHistoryVOs for the specified CommissionHistory.AgentGroupPK and Segment.SegmentPK.
     * @param agentGroupPK
     * @param segmentPK
     * @param voInclusionList
     * @return the composed CommissionHistoryVOs
     */
    public CommissionHistoryVO[] composeCommissionHistoryVOByAgentGroupPK_SegmentFK(long agentGroupPK, long segmentPK, List voInclusionList)
    {
        CommissionHistoryVO[] commissionHistoryVO = DAOFactory.getCommissionHistoryDAO().findByAgentGroupPK_SegmentFK(agentGroupPK, segmentPK);

        if (commissionHistoryVO != null)
        {
            for (int i = 0; i < commissionHistoryVO.length; i++)
            {
                new CommissionHistoryComposer(voInclusionList).compose(commissionHistoryVO[i]);
            }
        }

        return commissionHistoryVO;
    }

    public EDITTrxHistoryVO[] composeEDITTrxHistoryBySegmentAndDatesForAccting(long segmentPK, String fromDate, String toDate, String transactionType, List voInclusionList) throws Exception
    {
        EDITTrxHistoryVO[] editTrxHistoryVOs = DAOFactory.getEDITTrxHistoryDAO().findEDITTrxHistoryByEffectiveDateForAccting(segmentPK, fromDate, toDate, transactionType);

        if (editTrxHistoryVOs != null)
        {
            for (int i = 0; i < editTrxHistoryVOs.length; i++)
            {
                new EDITTrxHistoryComposer(voInclusionList).compose(editTrxHistoryVOs[i]); // New composer for each VO
            }
        }

        return editTrxHistoryVOs;
    }
    
    public EDITTrxVO[] composeEDITTrxVOBySegmentPK_AND_PendingStatus(long segmentPK, String[] pendingStatus, List voInclusionList) throws Exception
    {
        EDITTrxVO[] editTrxVO = DAOFactory.getEDITTrxDAO().findBySegmentPK_AND_PendingStatus(segmentPK, pendingStatus);

        if (editTrxVO != null)
        {
            EDITTrxComposer composer = new EDITTrxComposer(voInclusionList);

            for (int i = 0; i < editTrxVO.length; i++)
            {
                composer.compose(editTrxVO[i]);
            }
        }

        return editTrxVO;
    }
}
