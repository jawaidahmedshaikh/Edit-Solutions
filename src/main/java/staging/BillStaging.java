/**
 * User: dlataill
 * Date: Oct 5, 2007
 * Time: 9:01:47 AM
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
import java.util.List;

import contract.Segment;
import edit.common.EDITBigDecimal;

public class BillStaging
{
    public static String eventType = "BILLING";

    private EDITDateTime stagingDate;

    public static final String DATABASE = SessionHelper.STAGING;

    public BillStaging(EDITDateTime stagingDate)
    {
        this.stagingDate = stagingDate;
    }

    public void stageTables(billing.BillSchedule billingBillSchedule, List billGroupList)
    {
        StagingContext stagingContext = new StagingContext(eventType, stagingDate);

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

        BillSchedule billSchedule = BillSchedule.findByStagingFK(staging.getStagingPK(), billingBillSchedule.getBillSchedulePK(), DATABASE);
        if (billSchedule != null)
        {
            stagingContext.setCurrentBillSchedule(billSchedule);
        }
        else
        {
            billingBillSchedule.stage(stagingContext, DATABASE);
        }

        Set<ContractGroup> groupContractGroups = billingBillSchedule.getContractGroups();
        if (!groupContractGroups.isEmpty())
        {
            Iterator it = groupContractGroups.iterator();
            while (it.hasNext())
            {
                ContractGroup groupContractGroup = (ContractGroup) it.next();
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
            }
        }

        for (int i = 0; i < billGroupList.size(); i++)
        {
            billing.BillGroup billingBillGroup = (billing.BillGroup) billGroupList.get(i);

            stagingContext = billingBillGroup.stage(stagingContext, DATABASE);

            Set<billing.Bill> billingBills = billingBillGroup.getBills();
            Iterator it = billingBills.iterator();
            while (it.hasNext())
            {
                billing.Bill billingBill = (billing.Bill) it.next();

                Segment segment = billingBill.getSegment();
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
                        for (int k = 0; k < riders.length; k++)
                        {
                            riders[k].stage(stagingContext, DATABASE);

                            Set<contract.ContractClient> contractClients = riders[k].getContractClients();
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

                    contract.PremiumDue[] premiumDues = contract.PremiumDue.getPremiumDueForBillStaging(segment, billingBillSchedule);
                    if (premiumDues != null)
                    {
                    	Long latestPk = 0L;
                        for (int k = 0; k < premiumDues.length; k++)
                        {
                            stagingContext = premiumDues[k].stage(stagingContext, DATABASE);
                        }
                    }
                }

                stagingContext = billingBill.stage(stagingContext, DATABASE);
            }

            stagingContext.incrementRecordCount(billingBills.size());
        }

        stagingContext.getStaging().setRecordCount(stagingContext.getRecordCount());

        SessionHelper.saveOrUpdate(stagingContext.getStaging(), DATABASE);

        SessionHelper.commitTransaction(DATABASE);        
    }
}
