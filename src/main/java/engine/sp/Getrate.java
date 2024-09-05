/*
 * Getrate.java      06/04/2001
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.EDITBigDecimal;

import java.util.List;

/**
 * This is the implementation for the Getrate instruction.
 * The Getrate instruction is used to get a rate for the tablename
 * on the data stack.  The rates are accessed from the functions stored in spript processor.
 * This name was put there by the GettableName instruction.
 * <p/>
 * Example:
 * push str:xxxxx
 * getrate
 */
public final class Getrate extends InstOneOperand
{

    /**
     * Getrate constructor
     * <p/>
     * @throws SPException
     */
    public Getrate() throws SPException
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

        //the ws fields are in a hashtable
        //Map ws = sp.getWSdata();

        String odn = "";

        int yrCnt = (int) Double.parseDouble((String) sp.getWSEntry("YearCounter"));

        if (yrCnt != 0)
        {

            yrCnt = yrCnt - 1;
        }

        String cgInd = (String) sp.getWSEntry("CurrGuarInd");

        /* Always pop the table name from the stack
         * Gettable or Push will put it there
         */
        String tableName = sp.popFromStack();

        odn = tableName += cgInd;
        // get function on function HashTable external name contains
        // curr or guar
        List rateTable = sp.getFunctionEntry(odn);

        // push the rate for the specified year to the stack
        EDITBigDecimal rate = ((EDITBigDecimal) rateTable.get(yrCnt));
        sp.push(rate.toString());

        // Increment instruction pointer
        sp.incrementInstPtr();
    }

}