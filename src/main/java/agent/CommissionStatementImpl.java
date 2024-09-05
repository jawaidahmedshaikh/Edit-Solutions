/*
 * User: gfrosti
 * Date: Dec 1, 2003
 * Time: 3:56:50 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import batch.business.*;
import edit.common.*;
import edit.common.vo.*;
import edit.services.*;
import edit.services.config.*;
import edit.services.db.hibernate.SessionHelper;
import edit.services.logging.*;
import fission.utility.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import logging.*;
import role.*;
import staging.CommissionStaging;


public class CommissionStatementImpl
{	/** A set of client roles which have already had their PriorLastStatementDateTime 
	 * updated in the course of the job (so it should not be updated again) */
	private Set<Long> updatedFinancialRolePks;
	
	/** Need to track staging instances on an agent-number basis since YTD values 
	 * need to be tracked across multiple contracts. */
	private HashMap<String, CommissionStaging> stagingInstances;
	
	public CommissionStatementImpl()
	{
		updatedFinancialRolePks = new HashSet<Long>();
		stagingInstances = new HashMap<String, CommissionStaging>();
	}
	
    protected void generateCommissionStatementByContractCodeCTAndPaymentModeCT(String contractCodeCT,
                                                                               String paymentModeCT,
                                                                               EDITDate processDate,
                                                                               String outputFileType,
                                                                               EDITDateTime stagingDate)
    {
        File exportFile = null;

        try
        {
            EditServiceLocator.getSingleton().getBatchAgent()
                              .getBatchStat(Batch.BATCH_JOB_GENERATE_COMMISSION_STATEMENTS).tagBatchStart(Batch.BATCH_JOB_GENERATE_COMMISSION_STATEMENTS,
                "Commission Statement");

            if (outputFileType.equals("XML"))
            {
                exportFile = getExportFile();

                insertStartCommissionStatement(exportFile);
            }

            if (contractCodeCT.equalsIgnoreCase("all"))
            {
                CodeTableVO[] contractCodes = CodeTableWrapper.getSingleton().getCodeTableEntries("CONTRACTCODE");

                for (int i = 0; i < contractCodes.length; i++)
                {
                    CodeTableVO contractCode = contractCodes[i];
                    processAgentContract(contractCode.getCode(), paymentModeCT, processDate, outputFileType, exportFile, stagingDate);
                }
            }
            else
            {
                processAgentContract(contractCodeCT, paymentModeCT, processDate, outputFileType, exportFile, stagingDate);
            }
            
            for(Long financialRolePk : updatedFinancialRolePks) {
            	ClientRoleFinancial clientRoleF = ClientRoleFinancial.findByPK(financialRolePk);
            	clientRoleF.setLastStatementDateTime(new EDITDateTime());
            	clientRoleF.save();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); // Don't throw.
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent()
                              .getBatchStat(Batch.BATCH_JOB_GENERATE_COMMISSION_STATEMENTS).tagBatchStop();

            if (exportFile != null)
            {
                try
                {
                    insertEndCommissionStatement(exportFile);
                }
                catch (Exception e)
                {
                    System.out.println(e);

                    e.printStackTrace(); // Don't throw.
                }
            }
        }
    }

    private void processAgentContract(String contractCodeCT, 
                                      String paymentModeCT, 
                                      EDITDate processDate, 
                                      String outputFileType, 
                                      File exportFile,
                                      EDITDateTime stagingDate)
    {
        AgentContract[] agentContract = AgentContract.findBy_ContractCodeCT_AND_PaymentModeCT(contractCodeCT, paymentModeCT);

        if (agentContract != null)
        {
            for (int i = 0; i < agentContract.length; i++)
            {
                EDITDate editDateNewProcessDate = new EDITDate(processDate.getFormattedDate());
                if (agentContract[i].shouldCreateCommissionStatement(editDateNewProcessDate))
                {
                    AgentStatement agentStatement = new AgentStatement();

                    agentStatement.generateStatement(agentContract[i], outputFileType, processDate, 
                    		stagingDate, exportFile, updatedFinancialRolePks, stagingInstances);
                    SessionHelper.clearSession(SessionHelper.EDITSOLUTIONS);
                }
            }
        }
    }

    private File getExportFile()
    {
        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

        File exportFile = new File(export1.getDirectory() + CommissionStatement.EXPORT_FILE + "_" +
                System.currentTimeMillis() + ".xml");

        return exportFile;
    }

    private void insertStartCommissionStatement(File exportFile)
        throws Exception
    {
        appendToFile(exportFile, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        appendToFile(exportFile, CommissionStatement.COMMISSION_STATEMENT_BEG_ELEMENT + "\n");
    }

    private void insertEndCommissionStatement(File exportFile)
        throws Exception
    {
        appendToFile(exportFile, "\n" + CommissionStatement.COMMSSION_STATEMENT_END_ELEMENT);
    }

    private void appendToFile(File exportFile, String data)
        throws Exception
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter(exportFile, true));

        bw.write(data);

        bw.flush();

        bw.close();
    }
}
