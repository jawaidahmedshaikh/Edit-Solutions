/**
 * User: dlataill
 * Date: Oct 18, 2007
 * Time: 9:55:47 AM
 * <p/>
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package staging;

import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.vo.*;
import edit.services.db.hibernate.SessionHelper;
import group.ContractGroup;

import java.io.Serializable;

import contract.Segment;
import agent.PlacedAgent;
import event.EDITTrx;
import event.Suspense;
import role.ClientRole;
import client.ClientDetail;


public class CheckStaging implements Serializable
{
    public String eventType = "Bank";
    private EDITDateTime stagingDate;
    private String companyName;
    private String contractNumber;

    public static final String DATABASE = SessionHelper.STAGING;

    public CheckStaging(String companyName, EDITDateTime stagingDate)
    {
        this.stagingDate = stagingDate;
        this.companyName = companyName;
        this.contractNumber = null;
    }

    public CheckStaging()
    {

    }

    public void stageTables(CheckEFTDetailVO checkEFTDetailVO)
    {
        StagingContext stagingContext = null;

        stagingContext = new StagingContext(eventType, stagingDate, companyName, contractNumber);

        SessionHelper.beginTransaction(DATABASE);

        Staging staging = Staging.findStagingByDate_EventType_Company_Contract(stagingDate, eventType, companyName, contractNumber);

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

        if (checkEFTDetailVO.getSegmentVOCount() > 0)
        {
            Segment segment = new Segment(checkEFTDetailVO.getSegmentVO(0));

            if (segment.getContractGroupFK() > 0)
            {
                ContractGroup groupContractGroup = ContractGroup.findBy_ContractGroupPK(new Long(segment.getContractGroupFK()));
                ContractGroup caseContractGroup = groupContractGroup.getContractGroup();

                Case stagingCase = Case.findByStagingFK_CaseNumber(staging.getStagingPK(), caseContractGroup.getContractGroupNumber(), DATABASE);
                if (stagingCase != null)
                {
                    stagingContext.setCurrentCase(stagingCase);
                }
                else
                {
                    stagingContext.setContractGroupType("Case");
                    caseContractGroup.stage(stagingContext, DATABASE);
                }

                Group stagingGroup = Group.findByCaseFK_ContractGroupNumber(stagingContext.getCurrentCase().getCasePK(), groupContractGroup.getContractGroupNumber(), DATABASE);
                if (stagingGroup != null)
                {
                    stagingContext.setCurrentGroup(stagingGroup);
                }
                else
                {
                    stagingContext.setContractGroupType("Group");
                    groupContractGroup.stage(stagingContext, DATABASE);
                }

                stagingContext.setSegmentType("Base");
                segment.stage(stagingContext, DATABASE);
            }
        }
        else //Create null segmentBase if checkDetail does not contain a segment
        {
            SegmentBase segmentBase = new SegmentBase();

            segmentBase.setStaging(stagingContext.getStaging());

            stagingContext.getStaging().addSegmentBase(segmentBase);

            stagingContext.setCurrentSegmentBase(segmentBase);

            SessionHelper.saveOrUpdate(segmentBase, SessionHelper.STAGING);
        }

        long clientDetailFK = checkEFTDetailVO.getSuspenseVO(0).getClientDetailFK();

        if (checkEFTDetailVO.getEDITTrxHistoryVOCount() > 0)
        {
            EDITTrxHistoryVO editTrxHistoryVO = checkEFTDetailVO.getEDITTrxHistoryVO(0);

            EDITTrxVO editTrxVO = (EDITTrxVO) editTrxHistoryVO.getParentVO(EDITTrxVO.class);

            EDITTrx editTrx = new EDITTrx(editTrxVO);

            editTrx.stage(stagingContext, DATABASE);

            if (clientDetailFK == 0)
            {
                ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);

                ClientRoleVO clientRoleVO = (ClientRoleVO) clientSetupVO.getParentVO(ClientRoleVO.class);

                if (clientRoleVO.getRoleTypeCT().equalsIgnoreCase(ClientRole.ROLETYPECT_AGENT))
                {
                    CommissionHistoryVO commissionHistoryVO = editTrxHistoryVO.getCommissionHistoryVO(0);
                    PlacedAgent placedAgent = PlacedAgent.findByPK(new Long(commissionHistoryVO.getPlacedAgentFK()));
                    stagingContext.setCurrentAgentNumber(clientRoleVO.getReferenceID());
                    agent.Agent agentAgent = placedAgent.getAgentContract().getAgent();

                    agentAgent.stage(stagingContext, DATABASE);
                }
                else
                {
                    contract.ContractClient contractContractClient = contract.ContractClient.findBy_SegmentFK_And_ClientRoleFK(new Long(checkEFTDetailVO.getSegmentVO(0).getSegmentPK()), clientRoleVO.getClientRolePK());

                    contractContractClient.stage(stagingContext, DATABASE);
                }
            }
            else
            {
                ClientDetail clientDetail = ClientDetail.findBy_ClientDetailPK(new Long(clientDetailFK));

                clientDetail.stage(stagingContext, DATABASE);
            }
        }
        else
        {
            if (clientDetailFK > 0)
            {
                ClientDetail clientDetail = ClientDetail.findBy_ClientDetailPK(new Long(clientDetailFK));

                clientDetail.stage(stagingContext, DATABASE);
            }
        }

        Suspense suspense = new Suspense(checkEFTDetailVO.getSuspenseVO(0));
        suspense.stage(stagingContext, DATABASE);

        stagingContext.incrementRecordCount(1);

        stagingContext.getStaging().setRecordCount(stagingContext.getRecordCount());

        SessionHelper.saveOrUpdate(stagingContext.getStaging(), DATABASE);

        SessionHelper.commitTransaction(DATABASE);
    }
}
