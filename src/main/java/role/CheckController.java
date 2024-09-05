/*
 * User: gfrosti
 * Date: Nov 5, 2003
 * Time: 11:36:18 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package role;

import batch.business.*;

import edit.common.vo.*;
import edit.common.*;

import edit.services.*;

import role.dm.dao.*;
import logging.*;
import agent.*;

import client.Preference;


public class CheckController
{
    /**
     * Sets up check transactions based on the specified processDate and paymentModeCT and the following conditions:
     * a. Either commissionBalance or redirectBalance > 0
     * c. disbursementMode is 'Check' or 'EFT'.
     * @param disbursementSourceCT
     * @param operator
     * @throws Exception
     */
    public void setupAgentCommissionChecks(String disbursementSourceCT, String paymentModeCT, String forceOutMinBal, String operator) throws Exception
    {
        if (disbursementSourceCT.equalsIgnoreCase(Preference.DISBURSEMENT_SOURCE_PAPER))
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_SETUP_AGENT_COMMISSION_CHECKS_CK).tagBatchStart(Batch.BATCH_JOB_SETUP_AGENT_COMMISSION_CHECKS_CK, "Commission Check CK");
        }
        else if (disbursementSourceCT.equalsIgnoreCase("EFT"))
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_SETUP_AGENT_COMMISSION_CHECKS_EFT).tagBatchStart(Batch.BATCH_JOB_SETUP_AGENT_COMMISSION_CHECKS_EFT, "Commission Check EFT");
        }

        try
        {
            ClientRoleFinancialVO[] clientRoleFinancialVO = DAOFactory.getClientRoleFinancialDAO().findByCommissionBalanceGT_AND_RedirectBalanceGT_AND_DisbursementSourceCT_AND_PaymentModeCT(0.0, 0.0, disbursementSourceCT, paymentModeCT);

            if (clientRoleFinancialVO != null)
            {
                for (int i = 0; i < clientRoleFinancialVO.length; i++)
                {
                    ClientRoleFinancial clientRoleFinancial = null;

                    try
                    {
                        clientRoleFinancial = new ClientRoleFinancial(clientRoleFinancialVO[i]);

                        clientRoleFinancial.setupCheckTransaction(forceOutMinBal, operator);

                        if (disbursementSourceCT.equalsIgnoreCase(Preference.DISBURSEMENT_SOURCE_PAPER))
                        {
                            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_SETUP_AGENT_COMMISSION_CHECKS_CK).updateSuccess();
                        }
                        else if (disbursementSourceCT.equalsIgnoreCase("EFT"))
                        {
                            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_SETUP_AGENT_COMMISSION_CHECKS_EFT).updateSuccess();
                        }
                    }
                    catch (Exception e)
                    {
                        if (disbursementSourceCT.equalsIgnoreCase(Preference.DISBURSEMENT_SOURCE_PAPER))
                        {
                            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_SETUP_AGENT_COMMISSION_CHECKS_CK).updateFailure();
                        }
                        else if (disbursementSourceCT.equalsIgnoreCase("EFT"))
                        {
                            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_SETUP_AGENT_COMMISSION_CHECKS_EFT).updateFailure();
                        }

                        String message = "[* Client Checks Process *] Failed For ClientRoleFinancialPK [" + clientRoleFinancialVO[i].getClientRoleFinancialPK() + "]:"+ e.getMessage();

                        System.out.println(message);
                        
                        System.out.println(message);

                        e.printStackTrace(); //To change body of catch statement use Options | File Templates.

                        //  Log error to database
                        if (disbursementSourceCT.equalsIgnoreCase(Preference.DISBURSEMENT_SOURCE_PAPER))
                        {
                            logErrorToDatabase(Log.BUILD_AGENT_CHECK_CK, message, clientRoleFinancial);
                        }
                        else if (disbursementSourceCT.equalsIgnoreCase("EFT"))
                        {
                            logErrorToDatabase(Log.BUILD_AGENT_EFT_CK, message, clientRoleFinancial);
                        }
                    }
                }
            }
        }
        finally
        {
            if (disbursementSourceCT.equalsIgnoreCase(Preference.DISBURSEMENT_SOURCE_PAPER))
            {
                EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_SETUP_AGENT_COMMISSION_CHECKS_CK).tagBatchStop();
            }
            else if (disbursementSourceCT.equalsIgnoreCase("EFT"))
            {
                EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_SETUP_AGENT_COMMISSION_CHECKS_EFT).tagBatchStop();
            }
        }
    }

    /**
     * Logs an error to the database using the specified logName.
     *
     * @param logName                   name of the log to which the error should be added
     * @param message                   error message to be logged
     * @param clientRoleFinancial       used to obtain the agentNumber which is logged
     */
    private void logErrorToDatabase(String logName, String message, ClientRoleFinancial clientRoleFinancial)
    {
        //  Supposed to get 0 or 1 unique Agents from the clientRole for checks
        //Agent agent = (Agent) clientRoleFinancial.getClientRole().getAgent();

        ClientRole clientRole = (ClientRole)clientRoleFinancial.getClientRole();
        EDITMap columnInfo = new EDITMap("ProcessDate", new EDITDate().getFormattedDate());

        if (clientRole != null)
        {
            columnInfo.put("AgentNumber", clientRole.getReferenceID());
        }
        else
        {
            columnInfo.put("AgentNumber", "N/A");
        }

        Log.logToDatabase(logName, message, columnInfo);
    }
}
