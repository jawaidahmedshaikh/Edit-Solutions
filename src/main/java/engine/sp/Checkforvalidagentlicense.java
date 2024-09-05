/*
 * User: cgleason
 * Date: Jan 07, 2008
 * Time: 10:10:36 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */


package engine.sp;

import static agent.common.Constants.ErrorMsg.*;
import edit.common.*;
import edit.common.vo.*;
import fission.utility.*;
import group.*;
import agent.*;

/**
 * Validate the license of an agent
 */
public class Checkforvalidagentlicense extends Inst
{

    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    public void compile(ScriptProcessor aScriptProcessor) {

        sp = aScriptProcessor;  //Save instance of ScriptProcessor

        //Note: No compiling is required for this instruction
    }

    /**
     * Validates the agent license exists and passes activation rules.
     * a faulty month).
     * @param scriptProcessor
     * @throws SPException
     */
    public void exec(ScriptProcessor scriptProcessor) throws SPException
    {
        sp = scriptProcessor;

        long agentPK   = Long.parseLong(Util.initString(sp.getWSEntry("AgentPK"), "0"));
        if (agentPK == 0)
        {
            throw new SPException("Not all parameters set to validate", SPException.VALIDATION_ERROR);
        }

        long batchContractSetupPK = Long.parseLong(Util.initString(sp.getWSEntry("BatchContractSetupPK"), "0"));
        if (batchContractSetupPK == 0)
        {
            throw new SPException("Not all parameters set to validate", SPException.VALIDATION_ERROR);
        }

        long productStructurePK = Long.parseLong(Util.initString(sp.getWSEntry("ProductStructurePK"), "0"));
        if (productStructurePK == 0)
        {
            throw new SPException("Not all parameters set to validate", SPException.VALIDATION_ERROR);
        }

        String residenceState = Util.initString(sp.getWSEntry("ResidenceState"), "");
        if (residenceState.equals(""))
        {
            throw new SPException("Not all parameters set to validate", SPException.VALIDATION_ERROR);
        }

        String applicationSignedStateCT = Util.initString(sp.getWSEntry("ApplicationSignedState"), null);
        if (applicationSignedStateCT == null)
        {
            throw new SPException("Not all parameters set to validate", SPException.VALIDATION_ERROR);
        }

        String applicationSignedDate = Util.initString(sp.getWSEntry("ApplicationSignedDate"), null);
        if (applicationSignedDate == null)
        {
            throw new SPException("Not all parameters set to validate", SPException.VALIDATION_ERROR);
        }

        String segmentName = Util.initString(sp.getWSEntry("SegmentNameCT"), "");
        if (segmentName.equals(""))
        {
            throw new SPException("Not all parameters set to validate", SPException.VALIDATION_ERROR);
        }

        try
        {
            String enrollmentMethod = null;
            String issueState = null;
            BatchContractSetup batchContractSetup = BatchContractSetup.findByPK(batchContractSetupPK);

            //Set issueState either from residenceState or AppicationSignedState depending on the value in enrollmentMethod
            if (batchContractSetup != null)
            {
                enrollmentMethod = batchContractSetup.getEnrollmentMethodCT();
                if (enrollmentMethod.equalsIgnoreCase("FaceToFace"))
                {
                    issueState = applicationSignedStateCT;
                }
                else
                {
                    issueState = residenceState;
                }
            }

            boolean agentLicenseValid = validateAgentLicense(agentPK, applicationSignedDate, issueState, productStructurePK,
                                                              enrollmentMethod, segmentName);

            String validValue = "false";
            if (agentLicenseValid)
            {
                validValue = "true";
            }

            sp.addWSEntry("AgentLicenseValid", validValue);
        }

        finally
        {
            sp.incrementInstPtr();
        }
    }


    /**
     * Check for agent to be active, then find the license by issue state.  Check the license to be valid for the product
     * structure. Lastly verify the license is active.
     * @param agentPK
     * @param trxEffectiveDate
     * @param issueState
     * @param productStructureFK
     * @param enrollmentMethod
     * @param productType
     * @return
     * @throws SPException
     */
    private boolean validateAgentLicense(long agentPK, String trxEffectiveDate, String issueState, long productStructureFK,
                                        String enrollmentMethod, String productType) throws SPException
    {
        boolean validAgentLicense = true;

        Agent agent = Agent.findBy_PK(new Long(agentPK));

        AgentImpl agentImpl = new AgentImpl();

        if (agentImpl.performAgentLicensing(trxEffectiveDate, issueState, productStructureFK))
        {
            AgentVO agentVO = (AgentVO) agent.getVO();

            if (!agentVO.getAgentStatusCT().equalsIgnoreCase("Active"))
            {
                validAgentLicense = false;
            }

            try
            {
                AgentLicense[] agentLicense = AgentLicense.findByAgentPK_AND_IssueState(agent.getPK(), issueState);

                if (agentLicense == null)
                {
                    validAgentLicense = false;
                }
                else
                {
                    validAgentLicense = validateLicense(agentLicense[0], trxEffectiveDate, issueState, productStructureFK); // Should only be one AgentLicense by this criteria.
                }
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new SPException(e.getMessage(), SPException.VALIDATION_ERROR);
            }
        }

        return validAgentLicense;
    }

    /**
     * Final step to validating the Agent License - the license effective date must be current
     * @param agentLicense
     * @param validationDate
     * @param issueState
     * @param productStructureFK
     * @return
     */
    private boolean validateLicense(AgentLicense agentLicense, String validationDate, String issueState, long productStructureFK)
    {
        boolean validAgentLicense = true;

        AgentLicenseVO agentLicenseVO = (AgentLicenseVO) agentLicense.getVO();

        EDITDate licenseTerminationDate = new EDITDate(agentLicenseVO.getLicTermDate());

        EDITDate licenseEffectiveDate = new EDITDate(agentLicenseVO.getLicEffDate());

        EDITDate licenseValidationDate = new EDITDate(validationDate);

        EDITDate licenseExpirationDate = new EDITDate(agentLicenseVO.getLicExpDate());

        if (licenseValidationDate.after(licenseTerminationDate) || licenseValidationDate.equals(licenseTerminationDate))
        {
            validAgentLicense = false;
        }

        AgentLicenseImpl agentLicenseImpl = new AgentLicenseImpl();

        EDITDate adjustedValidationDate = null;
        if (licenseExpirationDate.before(licenseValidationDate) ||
            licenseEffectiveDate.after(licenseValidationDate))
        {
            AreaValueVO areaValueVO = agentLicenseImpl.getAreaValue(productStructureFK, issueState, validationDate);
            if (areaValueVO != null)
            {
                adjustedValidationDate = licenseValidationDate.addDays(Integer.parseInt(areaValueVO.getAreaValue()));
            }

            if (licenseExpirationDate.before(licenseValidationDate) ||
                licenseEffectiveDate.after(adjustedValidationDate))
            {
                validAgentLicense = false;
            }
        }

        return validAgentLicense;
    }
}
