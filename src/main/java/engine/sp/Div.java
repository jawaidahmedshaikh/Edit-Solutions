/*
 * Div.java       06/04/2001
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.EDITBigDecimal;


/**
 * This is the implementation for the Div instruction.
 * The Division (Div) instruction does the following:
 * 1.) Removes the top two entries from the data stack
 * 2.) Divides the second entry by the first (top) entry
 * 3.) Places the result onto the data stack
 * <p>
 * For Example the script for the Formula 12 / 6 would be:
 * push num:12
 * push num:6
 * div
 */
public final class Div extends Inst
{

    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor)
    {

        sp = aScriptProcessor;  //Save instance of ScriptProcessor

        //Note: No compiling is required for this instruction
    }


    /**
     *  Executes the instruction
     *  <p>
     *  @exception SPException If there is an error while
     *    executing the instruction
     */
    protected void exec(ScriptProcessor execSP) throws SPException
    {

        sp = execSP;

        // Remove and Divide the top two items on the data stack
        EDITBigDecimal operand2 = new EDITBigDecimal(sp.popFromStack(), 12);
        EDITBigDecimal operand1 = new EDITBigDecimal(sp.popFromStack(), 12);
        EDITBigDecimal result   = new EDITBigDecimal("0");

        if (!operand2.isEQ("0"))
        {
            result = operand1.divideEditBigDecimal(operand2);

            // Push the division result back onton the stack
            sp.push(result.toString());

            // Increment instruction pointer
            sp.incrementInstPtr();
        }
        else
        {
            throw new SPException("Divide By Zero Error", SPException.INSTRUCTION_PROCESSING_ERROR);
        }

    }

}