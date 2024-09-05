/*
 * Getportnewmoney.java      02/13/2002
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.vo.FilteredFundVO;
import edit.common.vo.FundVO;
import engine.dm.dao.DAOFactory;

/**
 * This is the implementation for the Getportnewmoney instruction.
 * The Getportnewmoney instruction is used to get PortfolioNewMoneyStatusCT
 * for the filtered fund number.
 * <p/>
 * Example:
 * push ws:FilteredFundPK
 * Getportnewmoney
 */
public final class Getportnewmoney extends InstOneOperand
{

    /**
     * Getunitvalue constructor
     * <p/>
     * @throws SPException
     */
    public Getportnewmoney() throws SPException
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

        FilteredFundVO filteredFundVO = CSCache.getCSCache().getFilteredFundVOBy_FilteredFundPK(filteredFundId);

        FundVO fundVO = CSCache.getCSCache().getFundVOBy_FundPK(filteredFundVO.getFundFK());

        String portfolioNewMoneyInd = fundVO.getPortfolioNewMoneyStatusCT();

        sp.push(portfolioNewMoneyInd);

        // Increment instruction pointer
        sp.incrementInstPtr();
    }
}