/*
 * Getfundtable.java     02/13/2002
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

import engine.dm.dao.*;
import fission.dm.*;
import fission.utility.*;
import edit.common.vo.*;

/**
 * This is the implementation for the Getfundtable instruction.
 * The GetfundTable instruction is used to get the fund data for a filtered
 * fund id.
 * <p/>
 * Example:
 * push ws:FilteredFundPK
 * Getfundtable
 */
public final class Getfundtable extends InstOneOperand
{

    /**
     * Getfundtable constructor
     * <p/>
     * @throws SPException
     */
    public Getfundtable() throws SPException
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

        //NO edit step for this instruction.  There is no operand.

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

        long filteredFundId = Long.parseLong((String) sp.getWSEntry("FilteredFundId"));

        FundVO fundVO = CSCache.getCSCache().getFundVOBy_FilteredFundPK(filteredFundId);

        sp.addWSEntry("FundPK", new String(fundVO.getFundPK() + ""));
        sp.addWSEntry("FundName", new String(fundVO.getName()));
        sp.addWSEntry("FundType", new String(fundVO.getFundType()));
        sp.addWSEntry("PortfolioNewMoneyStatusCT", new String(fundVO.getPortfolioNewMoneyStatusCT()));
        sp.addWSEntry("LoanQualifier", new String(Util.initString(fundVO.getLoanQualifierCT(), "#NULL")));
        sp.addWSEntry("FundShortName", new String(Util.initString(fundVO.getShortName(), "#NULL")));
        sp.addWSEntry("ExcludeFromActivityFileInd", new String(fundVO.getExcludeFromActivityFileInd()));
        sp.addWSEntry("FundTypeCode", new String(fundVO.getTypeCodeCT()));
        sp.addWSEntry("ReportingFundName", new String(Util.initString(fundVO.getReportingFundName(), "#NULL")));

        // Increment instruction pointer
        sp.incrementInstPtr();
    }
}