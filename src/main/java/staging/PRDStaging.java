/**
 * User: dlataill
 * Date: Oct 3, 2007
 * Time: 8:42:47 AM
 * <p/>
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package staging;

import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.services.db.hibernate.SessionHelper;
import group.ContractGroup;

import java.util.Set;
import java.util.Iterator;

import contract.Segment;


public class PRDStaging
{
    public static String eventType = "PRD";

    private EDITDateTime stagingDate;

    public static final String DATABASE = SessionHelper.STAGING;

    public PRDStaging(EDITDateTime stagingDate)
    {
        this.stagingDate = stagingDate;
    }

    public void stageTables(group.PayrollDeductionSchedule prdSched)
    {
        StagingContext stagingContext = new StagingContext(eventType, stagingDate);

        SessionHelper.beginTransaction(DATABASE);

        Staging staging = Staging.findStagingByDate_EventType(stagingDate, eventType);

        if (staging == null)
        {
            staging = new Staging();
            stagingContext = staging.stage(stagingContext, DATABASE);
        }
        else
        {
            stagingContext.setStaging(staging);
            stagingContext.setRecordCount(staging.getRecordCount());
        }

        ContractGroup groupContractGroup = prdSched.getContractGroup();
        ContractGroup caseContractGroup = groupContractGroup.getContractGroup();

        Case stagingCase = Case.findByStagingFK_CaseNumber(staging.getStagingPK(), caseContractGroup.getContractGroupNumber(), DATABASE);
        if (stagingCase != null)
        {
            stagingContext.setCurrentCase(stagingCase);
        }
        else
        {
            stagingContext.setContractGroupType("Case");
            stagingContext = caseContractGroup.stage(stagingContext, DATABASE);
        }

        BillSchedule billSchedule = BillSchedule.findByStagingFK(staging.getStagingPK(), groupContractGroup.getBillScheduleFK(), DATABASE);
        if (billSchedule != null)
        {
            stagingContext.setCurrentBillSchedule(billSchedule);
        }
        else
        {
            billing.BillSchedule groupBillSchedule = groupContractGroup.getBillSchedule();
            stagingContext = groupBillSchedule.stage(stagingContext, DATABASE);
        }

        Group stagingGroup = Group.findByCaseFK_ContractGroupNumber(stagingContext.getCurrentCase().getCasePK(), groupContractGroup.getContractGroupNumber(), DATABASE);
        if (stagingGroup != null)
        {
            stagingContext.setCurrentGroup(stagingGroup);
        }
        else
        {
            stagingContext.setContractGroupType("Group");
            stagingContext = groupContractGroup.stage(stagingContext, DATABASE);
        }

        stagingContext = prdSched.stage(stagingContext);

        group.PayrollDeduction[] payrollDeductions = group.PayrollDeduction.findByExtractDate_prdScheduleFK(stagingDate.getEDITDate(), prdSched.getPayrollDeductionSchedulePK());
        for (int i = 0; i < payrollDeductions.length; i++)
        {
            group.PayrollDeduction prd = payrollDeductions[i];
            Segment segment = prd.getSegment();
            SegmentBase segmentBase = SegmentBase.findByStagingFK_ContractNumber(stagingContext.getStaging().getStagingPK(), segment.getContractNumber());
            if (segmentBase != null)
            {
                stagingContext.setCurrentSegmentBase(segmentBase);
            }
            else
            {
                stagingContext.setSegmentType("Base");
                stagingContext = segment.stage(stagingContext, DATABASE);

                Segment[] riders = segment.getRiders();
                if (riders != null && riders.length > 0)
                {
                    stagingContext.setSegmentType("Rider");
                    for (int j = 0; j < riders.length; j++)
                    {
                        stagingContext = riders[j].stage(stagingContext, DATABASE);

                        Set<contract.ContractClient> contractClients = riders[j].getContractClients();
                        Iterator it2 = contractClients.iterator();
                        while (it2.hasNext())
                        {
                            contract.ContractClient contractClient = (contract.ContractClient) it2.next();
                            stagingContext = contractClient.stage(stagingContext, DATABASE);
                        }
                    }
                }

                Set<contract.ContractClient> contractClients = segment.getContractClients();
                Iterator it2 = contractClients.iterator();
                while (it2.hasNext())
                {
                    contract.ContractClient contractClient = (contract.ContractClient) it2.next();
                    stagingContext = contractClient.stage(stagingContext, DATABASE);
                }

                contract.PremiumDue[] premiumDues = contract.PremiumDue.getPremiumDueForPRDStaging(segment, prdSched);
                if (premiumDues != null && premiumDues.length > 0)
                {
                    stagingContext = premiumDues[0].stage(stagingContext, DATABASE);
                }
            }

            stagingContext = prd.stage(stagingContext, DATABASE);
        }

        stagingContext.incrementRecordCount(payrollDeductions.length);

        stagingContext.getStaging().setRecordCount(stagingContext.getRecordCount());

        SessionHelper.saveOrUpdate(stagingContext.getStaging(), DATABASE);

        SessionHelper.commitTransaction(DATABASE);
    }
}
