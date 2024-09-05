/**
 * User: dlataill
 * Date: Jun 11, 2007
 * Time: 10:27:01 AM
 * <p/>
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package engine.sp.custom.function;

import edit.services.db.hibernate.SessionHelper;
import edit.common.EDITMap;

import engine.sp.ScriptProcessor;

import java.util.Map;
import java.util.List;

import org.hibernate.Session;
import contract.*;

public class CountCommissionPhases implements FunctionCommand
{
    private ScriptProcessor scriptProcessor;

    /**
     * The driving SegmentPK.
     */
    public static final String PARAM_CONTRACT_NUMBER = "ContractNumber";

    /**
     * Constructor.
     * @param scriptProcessor ScriptProcessor
     */
    public CountCommissionPhases(ScriptProcessor scriptProcessor)
    {
      this.scriptProcessor = scriptProcessor;
    }
    
    /**
     * Getter.
     * @return
     */
    private ScriptProcessor getScriptProcessor()
    {
        return this.scriptProcessor;
    }

    /**
     * Getter.
     * @return
     */
    private Map<String, String> getWorkingStorage()
    {
      return getScriptProcessor().getWS();
    }

    /**
     * Return the working storage value for the specified parameter name.
     * @param paramName
     * @return
     */
    protected String getWSValue(String paramName)
    {
      return getWorkingStorage().get(paramName);
    }

    /**
     * Get the greatest CommissionPhaseID from all of the segments (base and riders) for the given contract
     * defined by the contractNumber in working storage - there will be at least one commissionPhaseID - which
     * will be found on the base segment.
     *
     * @return the result as a String
     */
    public void exec()
    {
        String contractNumber = new String(getWSValue(PARAM_CONTRACT_NUMBER));

        String commPhaseCount = CommissionPhase.findGreatestCommissionPhaseIDByContractNumber(contractNumber);

        getScriptProcessor().push(commPhaseCount);
    }
}
