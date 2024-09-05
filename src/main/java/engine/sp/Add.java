/*
 * Add.java     06/04/2001
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.EDITBigDecimal;


/**
 * This is the implementation for the Add instruction.
 * The Add instruction will do the following:
 * 1.) Remove the top two numbers from the data stack
 * 2.) Add the two numbers together 
 * 3.) Place the result back onto the data stack 
 * <p>
 * Sample script for formula 12 + 6:
 * push num:12
 * push num:6
 * add
 */
public final class Add extends Inst {

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

        // Remove the top two items from the data stack and add them together
//        double operand1 = Double.parseDouble(sp.popFromStack());
//        double operand2 = Double.parseDouble(sp.popFromStack());
//        String result = ((operand1 + operand2) + "");
//        sp.push(result);

        EDITBigDecimal operand1 = new EDITBigDecimal(sp.popFromStack());
        EDITBigDecimal operand2 = new EDITBigDecimal(sp.popFromStack());
        operand1 = operand1.addEditBigDecimal(operand2);
        sp.push(operand1.toString());
        // Increment instruction pointer
        sp.incrementInstPtr();

    }
    
}