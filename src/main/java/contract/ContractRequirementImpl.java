/*
 * User: cgleason
 * Date: Oct 27, 2003
 * Time: 12:21:10 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import contract.dm.composer.VOComposer;
import contract.dm.dao.DAOFactory;
import edit.common.EDITDate;
import edit.common.vo.ContractRequirementVO;
import edit.common.vo.FilteredRequirementVO;
import edit.common.vo.RequirementVO;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;

import java.util.ArrayList;
import java.util.List;


public class ContractRequirementImpl extends CRUDEntityImpl
{
    protected void load(ContractRequirement contractRequirement, long contractRequirementPK) throws Exception
    {
        super.load(contractRequirement, contractRequirementPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void save(ContractRequirement contractRequirement)
    {
        super.save(contractRequirement, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    protected void delete(ContractRequirement contractRequirement) throws Exception
    {
        super.delete(contractRequirement, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void calculateFollowupDate(ContractRequirementVO contractRequirementVO)
    {
        RequirementVO requirementVO = DAOFactory.getRequirementDAO().findByFilteredRequirementPK(contractRequirementVO.getFilteredRequirementFK(), false, null)[0];

        int followupDays = requirementVO.getFollowupDays();

        EDITDate beginDate = new EDITDate(contractRequirementVO.getEffectiveDate());

        EDITDate followupDate = beginDate.addDays(followupDays);

        contractRequirementVO.setFollowupDate(followupDate.getFormattedDate());
    }

    protected void setEffectiveDate(ContractRequirementVO contractRequirementVO)
    {
        EDITDate systemDate = new EDITDate();

        contractRequirementVO.setEffectiveDate(systemDate.getFormattedDate());
    }

    protected void calcFollowupDate(int followupDays, ContractRequirementVO contractRequirementVO)
    {
        EDITDate systemDate = new EDITDate();

        EDITDate followupDate = systemDate.addDays(followupDays);

        contractRequirementVO.setFollowupDate(followupDate.getFormattedDate());
    }

    protected ContractRequirementVO[] buildInitialContractRequirements(long productStructureFK)
    {
        List contractRequirements = new ArrayList();

        try
        {
            VOComposer voComposer = new VOComposer();

            FilteredRequirementVO[] filteredRequirementVO = voComposer.composeFilteredRequirements(productStructureFK);

            if (filteredRequirementVO != null)
            {
                for (int i = 0; i < filteredRequirementVO.length; i++)
                {

                    ContractRequirementVO contractRequirementVO = new ContractRequirementVO();
                    contractRequirementVO.setContractRequirementPK(0);
                    contractRequirementVO.setFilteredRequirementFK(filteredRequirementVO[i].getFilteredRequirementPK());
                    contractRequirementVO.setSegmentFK(0);
                    contractRequirementVO.setRequirementStatusCT("Outstanding");
                    RequirementVO requirementVO = (RequirementVO)filteredRequirementVO[i].getParentVO(RequirementVO.class);
                    calcFollowupDate(requirementVO.getFollowupDays(), contractRequirementVO);
                    EDITDate systemDate = new EDITDate();
                    contractRequirementVO.setEffectiveDate(systemDate.getFormattedDate());
                    contractRequirementVO.setParentVO(FilteredRequirementVO.class, filteredRequirementVO[i]);
                    contractRequirements.add(contractRequirementVO);
                }
            }
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e.getMessage());
        }

        return (ContractRequirementVO[]) contractRequirements.toArray(new ContractRequirementVO[contractRequirements.size()]);
    }
}
