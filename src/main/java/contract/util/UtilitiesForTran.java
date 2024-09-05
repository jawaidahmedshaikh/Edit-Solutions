/*
 * User: cgleason
 * Date: Apr 20, 2005
 * Time: 12:59:39 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.util;

import edit.common.vo.*;
import edit.common.*;
import edit.portal.common.session.*;
import edit.services.db.hibernate.*;
import edit.services.command.*;
import fission.global.AppReqBlock;
import fission.beans.SessionBean;

import java.util.*;
import java.util.Set;

import contract.*;
import agent.*;
import engine.sp.*;

/**
 * This class contains methods that are common between ContractDetailTran and QuoteDetailTran
 */
public class UtilitiesForTran
{
    /**
     * For all the Filtered funds of a product structure build a user interfaceVO for the page
     *
     * @param appReqBlock
     * @param productStructureId
     *
     * @return
     *
     * @throws Exception
     */
    public UIFilteredFundVO[] buildUIFilteredFundVO(AppReqBlock appReqBlock, String productStructureId) throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        SessionBean funds = appReqBlock.getSessionBean("contractFunds");
        boolean contractFundsExist = false;
        Set fundKeys = null;
        if (funds != null)
        {
            fundKeys = getFundPageBeanKeys(funds);
            contractFundsExist = true;
        }

        FilteredFundVO[] filteredFundVO = engineLookup.findFilteredFundByProductStructure(Long.parseLong(productStructureId), false, null);

        List uiFFVector = new ArrayList();

        if (filteredFundVO != null)
        {
            for (int f = 0; f < filteredFundVO.length; f++)
            {
                UIFilteredFundVO uiFFVO = new UIFilteredFundVO();

                FundVO[] fundVO = engineLookup.getFundByFilteredFundFK(filteredFundVO[f].getFilteredFundPK(), false, null);

                if (fundVO != null)
                {
                    String typeCodeCT = fundVO[0].getTypeCodeCT();
                    if (typeCodeCT.equalsIgnoreCase("Product"))
                    {
                        uiFFVO.addFilteredFundVO(filteredFundVO[f]);
                        uiFFVO.addFundVO(fundVO[0]);
                        uiFFVector.add(uiFFVO);
                    }
                    else
                    {
                        if (contractFundsExist)
                        {
                            boolean matchFound = checkForSystemFundsToShow(fundKeys, filteredFundVO[f]);
                            if (matchFound)
                            {
                                uiFFVO.addFilteredFundVO(filteredFundVO[f]);
                                uiFFVO.addFundVO(fundVO[0]);
                                uiFFVector.add(uiFFVO);
                            }
                        }
                    }

                }
            }
        }

        if (uiFFVector.size() > 0)
        {
            return (UIFilteredFundVO[]) uiFFVector.toArray(new UIFilteredFundVO[uiFFVector.size()]);
        }

        else
        {
            return null;
        }
    }

    public void resetNegativeKeysToZero(AgentHierarchyVO[] agentHierarchyVOs) throws Exception
    {
        for (int i = 0; i < agentHierarchyVOs.length; i++)
        {
            if (agentHierarchyVOs[i].getAgentHierarchyPK() < 0)
            {
                agentHierarchyVOs[i].setAgentHierarchyPK(0);

                AgentSnapshotVO[] agentSnapshotVOs = agentHierarchyVOs[i].getAgentSnapshotVO();

                for (int j = 0; j < agentSnapshotVOs.length; j++)
                {
                    agentSnapshotVOs[j].setAgentSnapshotPK(0);
                    agentSnapshotVOs[j].setAgentHierarchyFK(0);
                }
            }

            AgentHierarchyAllocationVO[] agentHierarchyAllocationVOs = agentHierarchyVOs[i].getAgentHierarchyAllocationVO();

            for (int k = 0; k < agentHierarchyAllocationVOs.length; k++)
            {
                if (agentHierarchyAllocationVOs[k].getAgentHierarchyAllocationPK() < 0)
                {
                    agentHierarchyAllocationVOs[k].setAgentHierarchyAllocationPK(0);
                    agentHierarchyAllocationVOs[k].setAgentHierarchyFK(0);
                }
            }
        }
    }


    /**
     * Creates a new AgentHierarchyAllocation with the supplied values
     *
     * @param startDateString
     * @param allocationPercentString
     *
     * @return AgentHierarchyAllocation
     */
    public AgentHierarchyAllocation createAgentHierarchyAllocation(String startDateString, String allocationPercentString)
    {
        EDITDate startDate = new EDITDate(startDateString);
        EDITDate stopDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);

        EDITBigDecimal allocationPercent = new EDITBigDecimal(allocationPercentString);

        AgentHierarchyAllocation agentHierarchyAllocation = new AgentHierarchyAllocation(allocationPercent, startDate, stopDate);

        contract.business.Contract contractComponent = new contract.component.ContractComponent();

        agentHierarchyAllocation.setAgentHierarchyAllocationPK(new Long(contractComponent.getNextAvailableKey() * -1));

        return agentHierarchyAllocation;
    }

    /**
     * Updates the contents of the AgentHierarchyVO for allocation changes
     *
     * @param servicingAgentIndicator
     * @param agentHierarchyAllocation
     *
     * @return
     */
    public void updateAgentHierarchyVO(AgentHierarchyVO agentHierarchyVO, AgentHierarchyAllocation agentHierarchyAllocation)
    {
//        updateServicingAgentIndicator(agentHierarchyVO, servicingAgentIndicator);

        agentHierarchyAllocation.setAgentHierarchyFK(new Long(agentHierarchyVO.getAgentHierarchyPK()));

        agentHierarchyVO.addAgentHierarchyAllocationVO(agentHierarchyAllocation.getAsVO());
    }

    public void updateServicingAgentIndicator(AgentHierarchyVO agentHierarchyVO, String servicingAgentIndicator)
    {
//        if (servicingAgentIndicator.equals("on"))
//        {
//            agentHierarchyVO.setServicingAgentIndicator("Y");
//        }
//        else
//        {
//            agentHierarchyVO.setServicingAgentIndicator("N");
//        }
    }

    /**
     * Creates a new UIAgentHierarchyVO with the proper objects attached
     *
     * @param agentHierarchyAllocationVO
     * @param agentHierarchyVO
     * @param agentVO
     *
     * @return
     */
    public UIAgentHierarchyVO createUIAgentHierarchyVO(AgentHierarchyAllocationVO agentHierarchyAllocationVO,
                                                       AgentHierarchyVO agentHierarchyVO, AgentVO agentVO, String coverage)
    {
        UIAgentHierarchyVO uiAgentHierarchyVO = new UIAgentHierarchyVO();
        uiAgentHierarchyVO.setAgentHierarchyAllocationVO(agentHierarchyAllocationVO);
        uiAgentHierarchyVO.setAgentHierarchyVO(agentHierarchyVO);
        uiAgentHierarchyVO.setAgentVO(agentVO);
        uiAgentHierarchyVO.setCoverage(coverage);

        return uiAgentHierarchyVO;
    }

    /**
     * Creates a new UIAgentHierarchyVO with the proper objects attached and new fields
     *
     * @param agentHierarchyAllocationVO
     * @param agentHierarchyVO
     * @param agentVO
     * @param coverage
     * @param segmentEffectiveDate
     * @param segmentFK
     *
     * @return
     */
    public UIAgentHierarchyVO createUIAgentHierarchyVO(AgentHierarchyAllocationVO agentHierarchyAllocationVO,
                                                       AgentHierarchyVO agentHierarchyVO, AgentVO agentVO, int riderNumber, String coverage,
                                                       String segmentEffectiveDate, Long segmentFK)
    {
        UIAgentHierarchyVO uiAgentHierarchyVO = new UIAgentHierarchyVO();
        uiAgentHierarchyVO.setAgentHierarchyAllocationVO(agentHierarchyAllocationVO);
        uiAgentHierarchyVO.setAgentHierarchyVO(agentHierarchyVO);
        uiAgentHierarchyVO.setAgentVO(agentVO);
        uiAgentHierarchyVO.setCoverage(riderNumber + "_" + coverage);
        uiAgentHierarchyVO.setSegmentEffectiveDate(segmentEffectiveDate);
        uiAgentHierarchyVO.setSegmentFK(segmentFK);
        
        return uiAgentHierarchyVO;
    }

    /**
     * Inactivates an AgentHierarchyAllocation
     *
     * @param agentHierarchyAllocationVO
     * @param startDateString
     *
     * @return
     */
    public void inactivateAgentHierarchyAllocation(AgentHierarchyAllocationVO agentHierarchyAllocationVO, String startDateString)
    {
        AgentHierarchyAllocation agentHierarchyAllocation = new AgentHierarchyAllocation(agentHierarchyAllocationVO);

        EDITDate startDate = new EDITDate(startDateString);

        agentHierarchyAllocation.inactivate(startDate);
    }

    /**
     * Adds AgentHierarchys to the SegmentVO upon build.
     * @param appReqBlock
     * @param segmentVO
     * @param riderVOs
     */
    public void attachAgentHierarchysForBuild(AppReqBlock appReqBlock, SegmentVO segmentVO, Map riderVOs)
    {
        AgentHierarchyVO[] uniqueAgentHierarchyVOs = getUniqueAgentHierarchyVOsFromUIObjects(appReqBlock);

        for (int i = 0; i < uniqueAgentHierarchyVOs.length; i++)
        {
            String coverage = getCoverageFromUIObject(appReqBlock, uniqueAgentHierarchyVOs[i]);

            SegmentVO riderVO = (SegmentVO) riderVOs.get(coverage);

            if (riderVO == null)
            {
                segmentVO.addAgentHierarchyVO(uniqueAgentHierarchyVOs[i]);
            }
            else
            {
                riderVO.addAgentHierarchyVO(uniqueAgentHierarchyVOs[i]);
            }
        }
    }


    /**
     * Adds AgentHierarchys to the SegmentVO upon build using segmentFK of AgentHierarchy.
     * @param appReqBlock
     * @param segmentVO
     * @param riderVOs
     */
    public void attachAgentHierarchysBySegmentFK(AppReqBlock appReqBlock, SegmentVO segmentVO, Map riderVOs)
    {
        AgentHierarchyVO[] uniqueAgentHierarchyVOs = getUniqueAgentHierarchyVOsFromUIObjects(appReqBlock);

        for (int i = 0; i < uniqueAgentHierarchyVOs.length; i++)
        {
            String key = String.valueOf(uniqueAgentHierarchyVOs[i].getSegmentFK());

            SegmentVO riderVO = (SegmentVO) riderVOs.get(key);

            if (riderVO == null)
            {
                segmentVO.addAgentHierarchyVO(uniqueAgentHierarchyVOs[i]);
            }
            else
            {
                riderVO.addAgentHierarchyVO(uniqueAgentHierarchyVOs[i]);
            }
        }
    }

    /**
     * Gets the coverage from the UIAgentHierarchyVOs for the specified selectedAgentHierarchyVO.  AgentHierarchyVOs
     * are duplicated in UIAgentHierarchyVOs because the AgentHierarchyAllocationVO is the "controlling" object.  Need
     * to get the coverage that is in the same UI object as the selectedAgentHierarchyVO.
     * @param appReqBlock
     * @param selectedAgentHierarchyVO      the agentHierarchy in the list of UIAgentHierarchyVOs
     * @return
     */
    private String getCoverageFromUIObject(AppReqBlock appReqBlock, AgentHierarchyVO selectedAgentHierarchyVO)
    {
        String coverage = null;

        UIAgentHierarchyVO[] uiAgentHierarchyVOs = (UIAgentHierarchyVO[]) appReqBlock.getHttpSession().getAttribute("uiAgentHierarchyVOs");

        if (uiAgentHierarchyVOs != null)
        {
            for (int i = 0; i < uiAgentHierarchyVOs.length; i++)
            {
                AgentHierarchyVO agentHierarchyVO = uiAgentHierarchyVOs[i].getAgentHierarchyVO();

                if (agentHierarchyVO.getAgentHierarchyPK() == selectedAgentHierarchyVO.getAgentHierarchyPK())
                {
                    coverage = uiAgentHierarchyVOs[i].getCoverage();
                }
            }
        }

        return coverage;
    }

    /**
     * Gets the unique AgentHierarchyVOs from the list of UIAgentHierarchyVOs.  The UI objects are controlled by the
     * AgentHierarchyAllocationVOs even though they are children of AgentHierarchyVOs.  This means that the
     * AgentHierarchyVOs are repeated within the UI objects.  Must check the agentHierarchyFK of each allocation to
     * see if that key has already been used when adding the hierarchys to the list of unique hierarchys.
     * @param appReqBlock
     * @return
     */
    public AgentHierarchyVO[] getUniqueAgentHierarchyVOsFromUIObjects(AppReqBlock appReqBlock)
    {
        UIAgentHierarchyVO[] uiAgentHierarchyVOs = (UIAgentHierarchyVO[]) appReqBlock.getHttpSession().getAttribute("uiAgentHierarchyVOs");

        List usedAgentHierarchyPKs = new ArrayList();         // List containing keys of hierarchys that have been added to the unique list

        List uniqueAgentHierarchyVOs = new ArrayList();       // List of unique AgentHierarchyVOs

        if (uiAgentHierarchyVOs != null)
        {
            for (int i = 0; i < uiAgentHierarchyVOs.length; i++)
            {
                AgentHierarchyAllocationVO agentHierarchyAllocationVO = uiAgentHierarchyVOs[i].getAgentHierarchyAllocationVO();

                Long agentHierarchyFK = new Long(agentHierarchyAllocationVO.getAgentHierarchyFK());

                if (! usedAgentHierarchyPKs.contains(agentHierarchyFK))
                {
                    // The agentHierarchy hasn't been added to the unique list yet

                    //  Add the key to the used list
                    usedAgentHierarchyPKs.add(agentHierarchyFK);

                    // Get the hierarchy from the ui objects and add it to the unique list
                    AgentHierarchyVO agentHierarchyVO = uiAgentHierarchyVOs[i].getAgentHierarchyVO();

                    uniqueAgentHierarchyVOs.add(agentHierarchyVO);
                }
            }
        }

        return (AgentHierarchyVO[]) uniqueAgentHierarchyVOs.toArray(new AgentHierarchyVO[uniqueAgentHierarchyVOs.size()]);
    }

    private boolean checkForSystemFundsToShow(Set fundKeys, FilteredFundVO filteredFundVO)
    {
        String[] keys = (String[]) fundKeys.toArray(new String[fundKeys.size()]);
        String filteredFundPK = filteredFundVO.getFilteredFundPK() + "";

        for (int i = 0; i < keys.length; i++)
        {
            String key = (String) keys[i];
            key = key.substring(2);
            if (key.equals(filteredFundPK))
            {
                return true;
            }
        }
        return false;
    }

    private Set getFundPageBeanKeys(SessionBean funds)
    {
        Map fundPageBeans = funds.getPageBeans();
        Set fundKeys = fundPageBeans.keySet();

        return fundKeys;
    }

    public String editCommissionProfile(Long commissionProfileOvrdFK, EDITBigDecimal advancePercent, EDITBigDecimal recoveryPercent, Long placedAgentPK)
    {
        CommissionProfile commissionProfile = null;

        if (commissionProfileOvrdFK != null && commissionProfileOvrdFK != 0)
        {
            commissionProfile = CommissionProfile.findBy_PK(commissionProfileOvrdFK);
        }
        else if (placedAgentPK != null)
        {
            PlacedAgent placedAgent = PlacedAgent.findBy_PK_V1(placedAgentPK);
            commissionProfile = placedAgent.getActivePlacedAgentCommissionProfile().getCommissionProfile();
        }

        String errorMessage = null;
        if (commissionProfile != null)
        {
            String commissionOption = commissionProfile.getCommissionOptionCT();

            if (commissionOption.equalsIgnoreCase("IS") ||
                 commissionOption.equalsIgnoreCase("SB") ||
                 commissionOption.equalsIgnoreCase("PY"))
            {
                if (advancePercent.isGT("0") && recoveryPercent.isGT("0"))
                {
                    ;
                }
                else
                {
                    errorMessage = "Advanced/Recovery % Required";
                }
            }
            else
            {
                if (advancePercent.isGT("0") && recoveryPercent.isGT("0"))
                {
                    errorMessage = "Advance/Recovery % Not Allowed";
                }
            }
        }

        return errorMessage;
    }

    /**
     * Adds the operator and record PRASE events flags into the local thread.  This allows the SPRecorder to know
     * whether it should record events or not.
     * <P>
     * This method is common between ALL trans.
     * <P>
     * Since each tran stores data in different beans, etc., we can't be sure it will be in the appReqBlock, therefore,
     * the recordPRASEEvents parameter must be passed in.
//     *
     * @param appReqBlock                   AppReqBlock containing the operator information
     * @param recordPRASEEvents             String containing "true" or "false" which determines whether to record the events or not
     */
    public static void setupRecordPRASEEvents(AppReqBlock appReqBlock, String recordPRASEEvents)
    {
        UserSession userSession = (UserSession) appReqBlock.getHttpSession().getAttribute("userSession");

        SessionHelper.putInThreadLocal(SEGRequestDispatcher.OPERATOR_NAME, userSession.getUsername());

        //  If the user check the Record PRASE Events box, set the Script Processor's record flag to yes, otherwise,
        //  set to no
        if (recordPRASEEvents.equals("true"))
        {
            SessionHelper.putInThreadLocal(SPRecorder.RECORD_SP, SPRecorder.RECORD_SP_YES);
        }
        else
        {
            SessionHelper.putInThreadLocal(SPRecorder.RECORD_SP, SPRecorder.RECORD_SP_NO);
        }
    }
}
