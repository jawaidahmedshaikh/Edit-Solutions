/*
 * Chgsign.java      06/04/2001
 *
  * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;


import edit.common.EDITBigDecimal;

/**
 * This is the implementation for the Chgsign instruction.
 * The Chgsign function will reverse the sign of the number. The item on the
 * top of the stack must be a number
 * 1.) Removes the top item from the stack.
 * 2.) Reverses the sign of the number.
 * 3.) Place the result back onto the stack.
 * <p>
 * Sample script for negating 13:
 * push val:13
 * chgsign
 */
public final class Chgsign extends Inst {

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
    protected void exec(ScriptProcessor execSP) throws SPException {

        sp = execSP;
        // Remove the item from the data stack
        EDITBigDecimal operand1 = new EDITBigDecimal( sp.popFromStack());

        // Change the sign of the number from the stack
        operand1 = operand1.negate();

        // Push the result back onto the stack
        sp.push(operand1.toString());

        //Increment instruction pointer
        sp.incrementInstPtr();
        }
}