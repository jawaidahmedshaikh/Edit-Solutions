/**
 * User: dlataill
 * Date: Oct 8, 2007
 * Time: 12:46:47 PM
 * <p/>
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package staging;

import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.vo.AccountingDetailVO;
import edit.services.db.hibernate.SessionHelper;
import edit.services.EditServiceLocator;
import group.ContractGroup;

import java.util.Set;
import java.util.Iterator;
import java.util.List;
import java.io.Serializable;

import contract.Segment;
import accounting.AccountingDetail;
import agent.PlacedAgent;
import batch.business.Batch;
import role.ClientRole;


public class AccountingStaging implements Serializable
{
    public String eventType;
    private EDITDateTime stagingDate;

    public static final String DATABASE = SessionHelper.STAGING;

    public AccountingStaging(String eventType, EDITDateTime stagingDate)
    {
        this.eventType = eventType;
        this.stagingDate = stagingDate;
    }

    public AccountingStaging()
    {

    }

    public void stageTables(List accountingDetails, Segment segment)
    {
        StagingContext stagingContext = null;

        stagingContext = new StagingContext(eventType, stagingDate);

        SessionHelper.beginTransaction(DATABASE);

        Staging staging = Staging.findStagingByDate_EventType(stagingDate, eventType);

        if (staging == null)
        {
            staging = new Staging();
            staging.stage(stagingContext, DATABASE);
        }
        else
        {
            stagingContext.setStaging(staging);
            stagingContext.setRecordCount(staging.getRecordCount());
        }

        for (int i = 0; i < accountingDetails.size(); i++)
        {
            AccountingDetailVO accountingDetailVO = (AccountingDetailVO) accountingDetails.get(i);

            AccountingDetail accountingDetail = new AccountingDetail(accountingDetailVO);

            stageDetail(accountingDetail, stagingContext, segment);
        }

        stagingContext.incrementRecordCount(accountingDetails.size());

        stagingContext.getStaging().setRecordCount(stagingContext.getRecordCount());

        SessionHelper.saveOrUpdate(stagingContext.getStaging(), DATABASE);

        SessionHelper.commitTransaction(DATABASE);
    }

    public void stageTables(String eventType, EDITDateTime stagingDate, String companyName)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_CLIENT_ACCOUNTING_EXTRACT).tagBatchStart(Batch.BATCH_JOB_CREATE_CLIENT_ACCOUNTING_EXTRACT, "Client Accounting Extract");

        try
        {
            StagingContext stagingContext = null;

            stagingContext = new StagingContext(eventType, stagingDate);

            SessionHelper.beginTransaction(DATABASE);

            Staging staging = Staging.findStagingByDate_EventType(stagingDate, eventType);

            if (staging == null)
            {
                staging = new Staging();
                staging.stage(stagingContext, DATABASE);
            }
            else
            {
                stagingContext.setStaging(staging);
                stagingContext.setRecordCount(staging.getRecordCount());
            }

            AccountingDetail[] accountingDetails = AccountingDetail.findByCompanyName_AccountingProcessDate(companyName, stagingDate.getEDITDate());
            for (int i = 0; i < accountingDetails.length; i++)
            {
                stageDetail(accountingDetails[i], stagingContext);
            }

            stagingContext.incrementRecordCount(accountingDetails.length);

            stagingContext.getStaging().setRecordCount(stagingContext.getRecordCount());

            SessionHelper.saveOrUpdate(stagingContext.getStaging(), DATABASE);

            SessionHelper.commitTransaction(DATABASE);

            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_CLIENT_ACCOUNTING_EXTRACT).updateSuccess();
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_CLIENT_ACCOUNTING_EXTRACT).updateFailure();
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_CLIENT_ACCOUNTING_EXTRACT).tagBatchStop();
        }
    }

    public void stageDetail(AccountingDetail accountingDetail, StagingContext stagingContext)
    {
        accountingDetail.stage(stagingContext, DATABASE);

        if (accountingDetail.getCompanyName().equalsIgnoreCase("Commission"))
        {
            Long placedAgentFK = accountingDetail.getPlacedAgentFK();
            if (placedAgentFK != null)
            {
                PlacedAgent placedAgent = PlacedAgent.findBy_PK(placedAgentFK);
                ClientRole clientRole = placedAgent.getClientRole();
                stagingContext.setCurrentAgentNumber(clientRole.getReferenceID());
                agent.Agent agent = PlacedAgent.findBy_PK(placedAgentFK).getAgentContract().getAgent();
                agent.stage(stagingContext, DATABASE);
            }
        }
    }
    
    public void stageDetail(AccountingDetail accountingDetail, StagingContext stagingContext, Segment segment)
    {
        accountingDetail.stage(stagingContext, DATABASE, segment);

        if (accountingDetail.getCompanyName().equalsIgnoreCase("Commission"))
        {
            Long placedAgentFK = accountingDetail.getPlacedAgentFK();
            if (placedAgentFK != null)
            {
                PlacedAgent placedAgent = PlacedAgent.findBy_PK(placedAgentFK);
                ClientRole clientRole = placedAgent.getClientRole();
                stagingContext.setCurrentAgentNumber(clientRole.getReferenceID());
                agent.Agent agent = PlacedAgent.findBy_PK(placedAgentFK).getAgentContract().getAgent();
                agent.stage(stagingContext, DATABASE);
            }
        }
    }
}
