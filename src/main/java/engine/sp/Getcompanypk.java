/*
 * User: sdorman
 * Date: Oct 31, 2006
 * Time: 9:27:11 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.sp;

import engine.*;


/**
 * This is the implementation for the Getcompanypk instruction.
 * It will access the policy number in working storage. 
 */
public final class Getcompanypk extends InstOneOperand
{
    /**
     * Constructor
     *
     * @throws SPException
     */
    public Getcompanypk() throws SPException
    {
        super();
    }

    /**
     * Compiles the instruction
     *
     * @param aScriptProcessor Instance of ScriptProcessor
     */
    public void compile(ScriptProcessor aScriptProcessor) throws SPException
    {
        sp = aScriptProcessor;  //Save instance of ScriptProcessor

        // Note: No compiling is required for this instruction
    }


    /**
     * Executes the instruction
     *
     * @throws SPException If there is an error while executing the instruction
     */
    public void exec(ScriptProcessor execSP) throws SPException
    {
        sp = execSP;

        String policyNumber = (String) sp.getWSEntry("ContractNumber");
        Company company = Company.findBy_PolicyNumberPrefix(policyNumber);

        sp.addWSEntry("companyPK", Long.toString(company.getCompanyPK()));

        // Increment instruction pointer
        sp.incrementInstPtr();
    }
}