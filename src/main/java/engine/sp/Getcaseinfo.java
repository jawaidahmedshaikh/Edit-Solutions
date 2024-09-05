/**
 * User: cgleason
 * Date: Dec 10, 2008
 * Time: 12:33:30 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */

package engine.sp;

import edit.common.*;
import engine.Area;
import engine.AreaKey;
import engine.AreaValue;
import fission.utility.Util;
import group.*;

/**
 * This is the implementation for the Getcaseinfo instruction.
 * It perform the best match for the CaseSetup table based on the
 * values set into working/storage by the script.
 *
 */
public final class Getcaseinfo extends InstOneOperand
{
    private CaseSetup caseSetup;

    /**
     * Getcaseinfo constructor
     * <p/>
     * @throws SPException
     */
    public Getcaseinfo() throws SPException
    {
        super();
    }

    /**
     * Compiles the instruction
     * <p/>
     * @param aScriptProcessor Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) throws SPException
    {
        sp = aScriptProcessor;  //Save instance of ScriptProcessor
   
        // Note: No compiling is required for this instruction
    }

    /**
     * Executes the instruction
     * <p/>
     * @throws SPException If there is an error while
     *                     executing the instruction
     */
    protected void exec(ScriptProcessor execSP) throws SPException
    {
        sp = execSP;

        String contractGroupPKString = (String) sp.getWSEntry("ContractGroupPK");
        String ownResidenceState = (String) sp.getWSEntry("OwnResidenceState");
        String caseSetupOption = (String) sp.getWSEntry("CaseSetupOption");
        EDITDate effectiveDate = new EDITDate((String) sp.getWSEntry("EffectiveDate"));

        caseSetup = getCaseSetup(contractGroupPKString, ownResidenceState, caseSetupOption, effectiveDate);

        if (caseSetup== null)
        {
            sp.addWSEntry("UseTrustState", "N");
        }
        else
        {
            sp.addWSEntry("UseTrustState", caseSetup.getCaseSetupOptionValueCT());
        }

        // Increment instruction pointer
        sp.incrementInstPtr();
    }

    /**
     * First look for an exact match of state and option with max effectiveDate.  If not found look for the default state.
     * @param contractGroupPKString
     * @param ownResidenceState
     * @param caseSetupOption
     * @param effectiveDate
     * @return
     */
    private CaseSetup getCaseSetup(String contractGroupPKString, String ownResidenceState, String caseSetupOption, EDITDate effectiveDate)
    {
        //get Group ContractGroup for the PK in WS
        ContractGroup contractGroup = ContractGroup.findByPK(new Long(contractGroupPKString));

        if (contractGroup != null)
        {
            // lookup with parameters set by the script
            caseSetup = CaseSetup.findBestMatchForFormState(contractGroup.getContractGroupFK(), ownResidenceState, caseSetupOption, effectiveDate);

            if (caseSetup == null)
            {
                //lookup with default state
                caseSetup = CaseSetup.findBestMatchForFormState(contractGroup.getContractGroupFK(), "*", caseSetupOption, effectiveDate);
            }
        }

        return caseSetup;
    }
}