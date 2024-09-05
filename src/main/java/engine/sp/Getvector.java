/*
 * Getvector.java      06/04/2001
 *
  * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.EDITBigDecimal;

import java.util.Map;

/**
 * This is the implementation for the Getvector instruction.
 * The Getvector instruction is used to get a rate out of a vector
 * stored in a hashmap.  This vector was put there by the Buildvector
 * instruction.
 * <p/>
 * Example:
 * push ws:MonthCounter
 * getvector "Name"
 */
public final class Getvector extends InstOneOperand
{

    /**
     * Activate constructor
     * <p/>
     * @throws SPException
     */
    public Getvector() throws SPException
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

        //Does common tasks in superclass such as
        //setting member variables and some operand editting
        commonCompileTasks();

        // The operandDataName contains the vector name of the instruction
        setOperandDataName(extractOperandDataName());
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

        //the vectors built are in a hashtable
        Map vector = null;

        // Get the vector name from operandDataName
        String vectorName = getOperandDataName();

        //Get the vector from where it is stored
        vector = sp.getVectorEntry(vectorName);

        //Get the month counter off the stack
        String counter = sp.popFromStack();

        if (counter.endsWith(".0"))
        {
            counter = counter.substring(0, counter.lastIndexOf("."));
        }

        // push the rate for the specified month to the stack
        EDITBigDecimal rate = (EDITBigDecimal) vector.get(String.valueOf(counter));

        sp.push(rate.toString());

        // Increment instruction pointer
        sp.incrementInstPtr();
    }
}