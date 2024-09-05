/*
 * Exp.java       06/04/2001
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.EDITBigDecimal;


/**
 * This is the implementation for the Exp instruction.
 * The Exp instruction will return the exponential number e (i.e., 2.718...) 
 * raised to the power of the passed in value (i.e.  e ** passedInValue).
 * The passedInValue will be the top item on the data stack
 * <p>
 * Sample script for formula (e ** 10):
 * push num:10
 * exp
 */
public class Exp extends Inst {

    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) {
    
        sp = aScriptProcessor;  //Save instance of ScriptProcessor
   
        // Note: No compiling is required for this instruction
    }


    /**
     *  Executes the instruction
     *  <p>
     *  @exception SPException If there is an error while 
     *       executing the instruction
     */
   	protected void exec(ScriptProcessor execSP) throws SPException {

        sp = execSP;

        // Remove the top item from the data stack
//        double operand1 = Double.parseDouble(sp.popFromStack());
//        operand1 = Math.exp(operand1);

        EDITBigDecimal number = new EDITBigDecimal(sp.popFromStack());
        EDITBigDecimal operand = new EDITBigDecimal(Math.exp(number.doubleValue()) + "");

        // Push the result back onto the data stack
        sp.push(operand.toString());

        // Increment instruction pointer
        sp.incrementInstPtr();
    }
}