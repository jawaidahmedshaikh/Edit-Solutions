/*
 * Append.java     06/04/2001
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;


/**
 * This is the implementation for the Append instruction.
 * The Append instruction does the following:
 * 1.) Removes the top two items from the stack.
 * 2.) It appends the second string to the first string.
 * 3.) Place the result back onto the stack.
 * <p>
 * Sample script for appending ABC and DEF:
 * push str:"ABC"
 * push str:"DEF"
 * append
 */
public final class Append extends Inst {

    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) {
    
        sp = aScriptProcessor;  //Save instance of ScriptProcessor
   
        //Note: No compiling is required for this instruction
    }


    /**
     *  Executes the instruction
     *  <p>
     *  @exception SPException If there is an error while 
     *        executing the instruction
     */
    protected void exec(ScriptProcessor execSP) throws SPException
    {

        sp = execSP;
        // Remove the top two items from the data stack
        String operand2 = sp.popFromStack();
        String operand1 = sp.popFromStack();
        String result = null;

            result = operand1 + operand2;

        // Push the appended result back onto the stack
        sp.push(result);

        //Increment instruction pointer
        sp.incrementInstPtr();
    }
}