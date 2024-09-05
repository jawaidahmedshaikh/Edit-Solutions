/*
 * User: cgleason
 * Date: Dec 5, 2003
 * Time: 11:25:21 AM
 * To change this template use Options | File Templates.
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import agent.common.Constants;
import agent.dm.dao.DAOFactory;
import edit.common.vo.AgentLicenseVO;
import edit.common.vo.AreaValueVO;
import edit.common.*;
import edit.common.exceptions.EDITValidationException;

import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import engine.AreaValue;
import engine.Area;


public class AgentLicenseImpl extends CRUDEntityImpl
{
    protected void save(AgentLicense agentLicense)
    {
        super.save(agentLicense, ConnectionFactory.EDITSOLUTIONS_POOL, true);

    }


    protected void load(AgentLicense agentLicense, long agentLicensePK) throws Exception
    {
        super.load(agentLicense, agentLicensePK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void delete(AgentLicense agentLicense) throws Exception
    {
        super.delete(agentLicense, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void validateLicense(AgentLicense agentLicense,
                                    String validationDate,
                                     String issueState,
                                      long productStructureFK) throws EDITValidationException, Exception
    {
        AgentLicenseVO agentLicenseVO = (AgentLicenseVO) agentLicense.getVO();

        EDITDate licenseTerminationDate = new EDITDate(agentLicenseVO.getLicTermDate());

        EDITDate licenseEffectiveDate = new EDITDate(agentLicenseVO.getLicEffDate());

        EDITDate licenseValidationDate = new EDITDate(validationDate);

        EDITDate licenseExpirationDate = new EDITDate(agentLicenseVO.getLicExpDate());

        if (licenseValidationDate.after(licenseTerminationDate) || licenseValidationDate.equals(licenseTerminationDate))
        {
            throw new EDITValidationException(Constants.ErrorMsg.AGENT_LICENSE_HAS_TERMINATED);
        }

        EDITDate adjustedValidationDate = null;
        if (licenseExpirationDate.before(licenseValidationDate) ||
            licenseEffectiveDate.after(licenseValidationDate))
        {
            AreaValueVO areaValueVO = getAreaValue(productStructureFK, issueState, validationDate);
            if (areaValueVO != null)
            {
                adjustedValidationDate = licenseValidationDate.addDays(Integer.parseInt(areaValueVO.getAreaValue()));
            }

            if (licenseExpirationDate.before(licenseValidationDate) ||
                licenseEffectiveDate.after(adjustedValidationDate))
            {
                throw new EDITValidationException(Constants.ErrorMsg.AGENT_LICENSE_NOT_VALID_FOR_TRX_DATE);
            }
        }
    }

    public AreaValueVO getAreaValue(long productStructure, String issueState, String validationDate)
    {

        engine.business.Calculator calculator = new engine.component.CalculatorComponent();

        String grouping = "AGENTLICENSEEDIT";
        String field = "LICENSESOLICITGUIDELINE";
        EDITDate validateDate = new EDITDate(validationDate);
        String qualifierCT = "*";

        Area area = new Area(productStructure, issueState, grouping, validateDate, qualifierCT);

        AreaValue areaValue     = area.getAreaValue(field);

        AreaValueVO areaValueVO = (AreaValueVO) areaValue.getVO();

        return areaValueVO;
    }

    public static AgentLicense[] findByAgentPK_AND_IssueState_AND_Date(long agentPK, String issueState) throws Exception
    {
        AgentLicenseVO[] agentLicenseVO = DAOFactory.getAgentLicenseDAO().findByAgentPK_AND_IssueState(agentPK, issueState);

        AgentLicense[] agentLicense = null;

        if (agentLicenseVO != null)
        {
            agentLicense = new AgentLicense[agentLicenseVO.length];

            for (int i = 0; i < agentLicenseVO.length; i++)
            {
                agentLicense[i] = new AgentLicense(agentLicenseVO[i]);
            }
        }

        return agentLicense;
    }
 }
