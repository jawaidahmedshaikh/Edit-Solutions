/**
 * User: cgleason
 * Date: Nov 17, 2004
 * Time: 2:59:32 PM
 * <p/>
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */


package engine.sp;

import edit.common.EDITBigDecimal;
import edit.common.vo.TableKeysVO;
import edit.common.vo.TableDefVO;

import java.util.List;
import java.util.ArrayList;

import engine.TableKeysMatcher;
import engine.dm.dao.DAOFactory;
import engine.dm.RateTable;

/**
 * This is the implementation for the Gettierrate instruction.
 * The Gettierrate instruction for the tablename on the stack gets all the tablekeys that qualify.
 * Get the expanded rate tables for each one and build two arrays.  One for the band amount and
 * one for the tier rate.  This tier rate is the one matching the year set by scripts.
 * This name was put there by the Gettable instruction.
 * <p/>
 * Example:
 * gettable TBSalesLoad
 * gettierrate
 */
public final class Gettierrate extends InstOneOperand
{

    /**
     * Gettierrate constructor
     * <p/>
     * @throws SPException
     */
    public Gettierrate() throws SPException
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
     * <p/>                      ws:EffectiveDate = 01/01/2000
                                 ws:UserKey = -
                                 ws:BandAmount = 250000
                                 ws:Gender = Female
                                 ws:IssueAge = 55
                                 ws:ClassType = NotApplicable
                                 ws:State = AK
                                 ws:TableType = Current
                                 ws:AccessType = Issue

     * @throws SPException If there is an error while
     *                     executing the instruction
     */
    protected void exec(ScriptProcessor execSP) throws SPException
    {
        sp = execSP;

        int yrCnt = (int) Double.parseDouble((String) sp.getWSEntry("YearCounter"));

        if (yrCnt != 0)
        {

            yrCnt = yrCnt - 1;
        }

        // Always pop the table name from the stack Gettable  will put it there
        String tableName = sp.popFromStack();

        String effectiveDate = (String) sp.getWSEntry("EffectiveDate");
        String age = (String) sp.getWSEntry("IssueAge");
        String interpolateInd = (String) sp.getWSEntry("InterpolateInd");
        int issueAge = 0;

        if (age != null)
        {
            issueAge = Integer.parseInt(age);
        }
        if (interpolateInd == null)
        {
            interpolateInd = "N";
        }

        // get all tableKey records for the effectiveDate and tableName
        TableDefVO tableDefVO = CSCache.getCSCache().getTableDefVOBy_TableName(tableName);
        TableKeysVO[] tableKeysVOs = new TableKeysMatcher().getTableKeyRecords(tableDefVO.getTableDefPK(), effectiveDate, sp);

        if (tableKeysVOs.length > 0)
        {
            String accessType = tableDefVO.getAccessType();
            List band = new ArrayList();
            List tierRate = new ArrayList();

            //this goes in a for loop for each tablekey record that comes back  - define arrays to be built
            List rateTable = null;
            for (int i = 0; i < tableKeysVOs.length; i++)
            {
                rateTable = RateTable.getExpandedTable(accessType, tableKeysVOs[i].getTableKeysPK(), issueAge, interpolateInd);
                EDITBigDecimal rate = ((EDITBigDecimal) rateTable.get(yrCnt));

                band.add(tableKeysVOs[i].getBandAmount());
                tierRate.add(rate.toString());
            }

            sp.addWSVector("bandAmount",band);
            sp.addWSVector("tierRate", tierRate);
            sp.addWSEntry("BandCounter", band.size() + "");

            // Increment instruction pointer
            sp.incrementInstPtr();
        }
        else
        {
            throw new SPException("Gettierrate - TableKeys entry not found for table " + tableName, SPException.INSTRUCTION_PROCESSING_ERROR);
        }
    }
}