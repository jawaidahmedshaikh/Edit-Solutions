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
 * This is the implementation for the Getfilteredfundtable instruction.
 * It will access the LoanQualifier in working storage and the productStructurePK from ScriptProcessor.  It then looks up
 * the filteredFund and returns its pk
 */
public final class Getfilteredfundpk extends InstOneOperand
{
    /**
     * Constructor
     *
     * @throws SPException
     */
    public Getfilteredfundpk() throws SPException
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

        String loanQualifier = (String) sp.getWSEntry("LoanQualifier");
//        Long productStructurePK = new Long((String) sp.getWSEntry("productStructurePK"));   // For testing purposes only

        Long productStructurePK = new Long(sp.getProductRule().getProductStructurePK());

        FilteredFund[] filteredFunds = FilteredFund.findBy_ProductStructurePK_LoanQualifierCT(productStructurePK, loanQualifier);

        //  There should only be one filteredFund for a given productStructurePK and loanQualifier
        if (filteredFunds != null)
        {
            String filteredFundPK = filteredFunds[0].getFilteredFundPK().toString();

            sp.addWSEntry("FilteredFundPK", filteredFundPK);
        }

        // Increment instruction pointer
        sp.incrementInstPtr();
    }
}