/*
 * User: gfrosti
 * Date: Dec 1, 2004
 * Time: 3:21:12 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package reinsurance;

import edit.common.vo.*;
import edit.common.*;
import edit.services.*;

import java.util.*;

import batch.business.*;
import logging.*;
import event.*;


public class ReinsuranceProcess
{
    /**
     * Finds the set of all Reinsurance Histories with update status = 'U', and triggers the update process. If the
     * companyName is "*", then updates occur across all Product Structures.
     * @param companyName
     * @param operator
     */
    public void updateReinsuranceBalances(String companyName, String operator)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_UPDATE_REINSURANCE_BALANCES).tagBatchStart(Batch.BATCH_JOB_UPDATE_REINSURANCE_BALANCES, "Reinsurance Balance");

        try
        {
            ReinsuranceHistory[] reinsuranceHistories = getReinsuranceHistories("*"); // We are defaulting to ALL '*' at this point.

            if (reinsuranceHistories != null)
            {
                for (int i = 0; i < reinsuranceHistories.length; i++)
                {
                    ReinsuranceHistory reinsuranceHistory = null;

                    try
                    {
                        reinsuranceHistory = reinsuranceHistories[i];

                        reinsuranceHistory.updateBalance();

                        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_UPDATE_REINSURANCE_BALANCES).updateSuccess();
                    }
                    catch (Exception e)
                    {
                        //  Get contractNumber to put in log
                        ReinsuranceHistoryVO reinsuranceHistoryVO = (ReinsuranceHistoryVO) reinsuranceHistory.getVO();
                        EDITTrxHistory editTrxHistory = EDITTrxHistory.findByPK(new Long(reinsuranceHistoryVO.getEDITTrxHistoryFK()));

                        logErrorToDatabase(e, editTrxHistory.getEDITTrx().getContractNumber());
                    }
                }
            }
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_UPDATE_REINSURANCE_BALANCES).updateFailure();

            System.out.println(e);

//            logErrorToDatabase(Log.REINSURANCE_UPDATE, e);
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_UPDATE_REINSURANCE_BALANCES).tagBatchStop();
        }
    }

    /**
     * Finds all Treaties by Company Name with a ReisurerBalance > $0.00. For each of these Treaties, a CheckTransaction
     * is created.
     * @param companyName
     * @param operator
     */
    public void setupReinsuranceChecks(String companyName, String operator)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_SETUP_REINSURANCE_CHECKS).tagBatchStart(Batch.BATCH_JOB_SETUP_REINSURANCE_CHECKS, "Reinsurance Check");

        try
        {
            Treaty[] treaties = getTreaties("*"); // We are defaulting to ALL '*' at this point.

            if (treaties != null)
            {
                for (int i = 0; i < treaties.length; i++)
                {
                    Treaty treaty = treaties[i];

                    treaty.createCheckTransaction(operator);

                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_SETUP_REINSURANCE_CHECKS).updateSuccess();
                }
            }
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_SETUP_REINSURANCE_CHECKS).updateFailure();

          System.out.println(e);

            e.printStackTrace();

//            logErrorToDatabase(Log.BUILD_REINSURANCE_CK, e);
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_SETUP_REINSURANCE_CHECKS).tagBatchStop();
        }
    }

    /**
     * Finds the set of Treaties that have a ReinsurerBalance > 0.00 for the speficied company name, or across all
     * companies if the company name is "*".
     * @param companyName
     * @return
     */
    private Treaty[] getTreaties(String companyName)
    {
        Treaty[] treaties = null;

        if (companyName.equals("*"))
        {
            treaties = Treaty.findBy_ReinsurerBalance_GT(0.00);
        }
        else
        {
            engine.business.Lookup engineLookup = new engine.component.LookupComponent();

            ProductStructureVO[] productStructures = engineLookup.getAllProductStructuresByCoName(companyName);

            if (productStructures != null)
            {
                List treatyList = new ArrayList();

                for (int i = 0; i < productStructures.length; i++)
                {
                    Treaty[] currentTreaties = Treaty.findBy_ReinsurerBalance_GT_ProductStructurePK(0.00, productStructures[i].getProductStructurePK());

                    if (currentTreaties != null)
                    {
                        treatyList.addAll(Arrays.asList(currentTreaties));
                    }
                }

                if (!treatyList.isEmpty())
                {
                    treaties = (Treaty[]) treatyList.toArray(new Treaty[treatyList.size()]);
                }
            }
        }

        return treaties;
    }

    /**
     * Finds the set of ReinsuranceHistories with a status of 'U', and associated to the supplied companyName. If a
     * companyName of '*' is supplied, then the companyName is ignored.
     * @param companyName
     * @return
     */
    private ReinsuranceHistory[] getReinsuranceHistories(String companyName)
    {
        ReinsuranceHistory[] reinsuranceHistories = null;

        if (companyName.equals("*"))
        {
            reinsuranceHistories = ReinsuranceHistory.findBy_UpdateStatus(ReinsuranceHistory.UPDATE_STATUS);
        }
        else
        {
            engine.business.Lookup engineLookup = new engine.component.LookupComponent();

            ProductStructureVO[] productStructures = engineLookup.getAllProductStructuresByCoName(companyName);

            if (productStructures != null)
            {
                List reinsuranceHistoryList = new ArrayList();

                for (int i = 0; i < productStructures.length; i++)
                {
                    ReinsuranceHistory[] currentReinsuranceHistories = ReinsuranceHistory.findBy_UpdateStatus_ProductStructurePK(ReinsuranceHistory.UPDATE_STATUS, productStructures[i].getProductStructurePK());

                    if (currentReinsuranceHistories != null)
                    {
                        reinsuranceHistoryList.addAll(Arrays.asList(currentReinsuranceHistories));
                    }
                }

                if (!reinsuranceHistoryList.isEmpty())
                {
                    reinsuranceHistories = (ReinsuranceHistory[]) reinsuranceHistoryList.toArray(new ReinsuranceHistory[reinsuranceHistoryList.size()]);
                }
            }
        }

        return reinsuranceHistories;
    }

    /**
     * Logs an error to the database
     * @param e                     exception that was caught
     * @param contractNumber        contractNumber to be logged
     */
    private void logErrorToDatabase(Exception e, String contractNumber)
    {
        EDITMap columnInfo = new EDITMap("ProcessDate", new EDITDate().getFormattedDate());
        columnInfo.put("ContractNumber", contractNumber);

        Log.logToDatabase(Log.REINSURANCE_UPDATE, e.getMessage(), columnInfo);
    }
}
